/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.sync;

import android.accounts.Account;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Pair;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.app.event.IAccountUpdatedListener;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.services.IAccountService;
import dev.drsoran.moloko.app.services.IAppEventService;
import dev.drsoran.moloko.app.services.IConnectionService;
import dev.drsoran.moloko.app.services.ISettingsService;
import dev.drsoran.moloko.app.services.ISyncService;
import dev.drsoran.moloko.app.services.SyncStartResult;
import dev.drsoran.moloko.content.db.TableColumns;
import dev.drsoran.moloko.domain.model.Sync;
import dev.drsoran.moloko.sync.Constants;


public class SyncService implements ISyncService, IOnSettingsChangedListener,
         IAccountUpdatedListener
{
   private final Context context;
   
   private final ISettingsService settings;
   
   private final IConnectionService connectionService;
   
   private final IAppEventService eventService;
   
   private final IAccountService accountService;
   
   private final ILog log;
   
   
   
   public SyncService( Context context, ISettingsService settings,
      IConnectionService connectionService, IAppEventService eventService,
      IAccountService accountService, ILog log )
   {
      this.context = context;
      this.settings = settings;
      this.connectionService = connectionService;
      this.eventService = eventService;
      this.accountService = accountService;
      this.log = log;
      
      registerSettingsChangedListener();
      registerAccountsUpdatedListener();
   }
   
   
   
   public void shutdown()
   {
      unregisterSettingsChangedListener();
      unregisterAccountsUpdatedListener();
      stopPeriodicSync();
   }
   
   
   
   @Override
   public SyncStartResult requestManualSync( Account account )
   {
      if ( connectionService.hasInternetConnection() )
      {
         final Bundle bundle = new Bundle();
         
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_MANUAL, true );
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, true );
         
         ContentResolver.requestSync( account, TableColumns.AUTHORITY, bundle );
         
         return SyncStartResult.Ok;
      }
      
      return SyncStartResult.NotConnected;
   }
   
   
   
   @Override
   public SyncStartResult requestSettingsOnlySync( Account account )
   {
      if ( connectionService.hasInternetConnection() )
      {
         final Bundle bundle = new Bundle();
         
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_MANUAL, true );
         bundle.putBoolean( dev.drsoran.moloko.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                            true );
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, false );
         
         ContentResolver.requestSync( account, TableColumns.AUTHORITY, bundle );
         
         return SyncStartResult.Ok;
      }
      
      return SyncStartResult.NotConnected;
   }
   
   
   
   @Override
   public void requestScheduledSync( Account account )
   {
      final Bundle bundle = new Bundle();
      
      bundle.putBoolean( Constants.SYNC_EXTRAS_SCHEDULED, true );
      bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, true );
      
      ContentResolver.requestSync( account, TableColumns.AUTHORITY, bundle );
   }
   
   
   
   @Override
   public boolean isReadyToSync( Account account )
   {
      return connectionService.hasInternetConnection()
         && ContentResolver.getSyncAutomatically( account,
                                                  TableColumns.AUTHORITY );
   }
   
   
   
   @Override
   public boolean isSyncing( Account account )
   {
      return !ContentResolver.isSyncPending( account, TableColumns.AUTHORITY )
         && ContentResolver.isSyncActive( account, TableColumns.AUTHORITY );
   }
   
   
   
   @Override
   public void cancelSync( Account account )
   {
      ContentResolver.cancelSync( account, TableColumns.AUTHORITY );
   }
   
   
   
   @Override
   public void schedulePeriodicSyncFromSettings()
   {
      final long interval = settings.getSyncInterval();
      
      if ( interval != Constants.SYNC_INTERVAL_MANUAL )
      {
         schedulePeriodicSync( interval );
      }
   }
   
   
   
   @Override
   public void schedulePeriodicSync( long intervalMs )
   {
      long startUtc = System.currentTimeMillis();
      
      ContentProviderClient client = null;
      try
      {
         client = context.getContentResolver()
                         .acquireContentProviderClient( Sync.CONTENT_URI );
         
         if ( client != null )
         {
            final Pair< Long, Long > lastSync = SyncTable.getLastInAndLastOut( client );
            
            if ( lastSync != null )
            {
               final long lastSyncIn = ( lastSync.first != null )
                                                                 ? lastSync.first
                                                                 : Long.MAX_VALUE;
               final long lastSyncOut = ( lastSync.second != null )
                                                                   ? lastSync.second
                                                                   : Long.MAX_VALUE;
               
               final long earliestLastSync = Math.min( lastSyncIn, lastSyncOut );
               
               // Ever synced?
               if ( earliestLastSync != Long.MAX_VALUE )
               {
                  startUtc = earliestLastSync + intervalMs;
               }
            }
         }
         
         setPeriodicSync( startUtc, intervalMs );
      }
      finally
      {
         if ( client != null )
         {
            client.release();
         }
      }
   }
   
   
   
   @Override
   public void stopPeriodicSync()
   {
      final Account account = accountService.getRtmAccount();
      
      if ( account != null )
      {
         ContentResolver.removePeriodicSync( account,
                                             TableColumns.AUTHORITY,
                                             getExtras() );
         
         log.i( getClass(), "Removed periodic sync" );
      }
   }
   
   
   
   @Override
   public void delayNextPeriodicSync( SyncResult syncResult, long seconds )
   {
      syncResult.delayUntil = seconds;
   }
   
   
   
   @Override
   public void onAccountUpdated( int what, Account account )
   {
      if ( what == IAccountUpdatedListener.ACCOUNT_UPDATED )
      {
         schedulePeriodicSyncFromSettings();
      }
      else if ( what == IAccountUpdatedListener.ACCOUNT_REMOVED )
      {
         stopPeriodicSync();
      }
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      if ( which == IOnSettingsChangedListener.SYNC_INTERVAL )
      {
         final Account account = accountService.getRtmAccount();
         
         if ( account != null )
         {
            final long syncInterval = settings.getSyncInterval();
            
            if ( syncInterval != Constants.SYNC_INTERVAL_MANUAL )
            {
               schedulePeriodicSync( syncInterval );
            }
            else
            {
               stopPeriodicSync();
            }
         }
      }
   }
   
   
   
   private void setPeriodicSync( long startUtc, long intervalMs )
   {
      final Account account = accountService.getRtmAccount();
      
      if ( account != null )
      {
         ContentResolver.addPeriodicSync( account,
                                          TableColumns.AUTHORITY,
                                          getExtras(),
                                          intervalMs / 1000 );
         
         log.i( getClass(), "Added new periodic sync repeating every "
            + DateUtils.formatElapsedTime( intervalMs / 1000 ) );
      }
   }
   
   
   
   private void registerSettingsChangedListener()
   {
      eventService.registerOnSettingsChangedListener( IOnSettingsChangedListener.SYNC_INTERVAL,
                                                      this );
   }
   
   
   
   private void unregisterSettingsChangedListener()
   {
      eventService.unregisterOnSettingsChangedListener( this );
   }
   
   
   
   private void registerAccountsUpdatedListener()
   {
      eventService.registerAccountUpdatedListener( this );
   }
   
   
   
   private void unregisterAccountsUpdatedListener()
   {
      eventService.unregisterAccountUpdatedListener( this );
   }
   
   
   
   private final static Bundle getExtras()
   {
      final Bundle bundle = new Bundle();
      
      bundle.putBoolean( Constants.SYNC_EXTRAS_SCHEDULED, Boolean.TRUE );
      bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, Boolean.TRUE );
      
      return bundle;
   }
}
