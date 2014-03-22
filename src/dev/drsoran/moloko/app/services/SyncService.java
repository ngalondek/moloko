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

package dev.drsoran.moloko.app.services;

import java.text.MessageFormat;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.SyncResult;
import android.os.Bundle;
import android.text.format.DateUtils;
import dev.drsoran.moloko.IConnectionService;
import dev.drsoran.moloko.app.event.IAccountUpdatedListener;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.content.ContentAuthority;
import dev.drsoran.moloko.sync.SyncConstants;
import dev.drsoran.rtm.ILog;
import dev.drsoran.rtm.sync.IRtmSyncPartner;


public class SyncService implements ISyncService, IOnSettingsChangedListener,
         IAccountUpdatedListener
{
   private final IRtmSyncPartnerFactory syncPartnerFactory;
   
   private final ISettingsService settings;
   
   private final IConnectionService connectionService;
   
   private final IAppEventService eventService;
   
   private final IAccountService accountService;
   
   private final ILog log;
   
   
   
   public SyncService( IRtmSyncPartnerFactory syncPartnerFactory,
      ISettingsService settings, IConnectionService connectionService,
      IAppEventService eventService, IAccountService accountService, ILog log )
   {
      this.syncPartnerFactory = syncPartnerFactory;
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
         
         ContentResolver.requestSync( account, ContentAuthority.RTM, bundle );
         
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
         bundle.putBoolean( SyncConstants.SYNC_EXTRAS_ONLY_SETTINGS, true );
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, false );
         
         ContentResolver.requestSync( account, ContentAuthority.RTM, bundle );
         
         return SyncStartResult.Ok;
      }
      
      return SyncStartResult.NotConnected;
   }
   
   
   
   @Override
   public void requestScheduledSync( Account account )
   {
      final Bundle bundle = new Bundle();
      
      bundle.putBoolean( SyncConstants.SYNC_EXTRAS_SCHEDULED, true );
      bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, true );
      
      ContentResolver.requestSync( account, ContentAuthority.RTM, bundle );
   }
   
   
   
   @Override
   public boolean isReadyToSync( Account account )
   {
      return connectionService.hasInternetConnection()
         && ContentResolver.getSyncAutomatically( account, ContentAuthority.RTM );
   }
   
   
   
   @Override
   public boolean isSyncing( Account account )
   {
      return !ContentResolver.isSyncPending( account, ContentAuthority.RTM )
         && ContentResolver.isSyncActive( account, ContentAuthority.RTM );
   }
   
   
   
   @Override
   public void cancelSync( Account account )
   {
      ContentResolver.cancelSync( account, ContentAuthority.RTM );
   }
   
   
   
   @Override
   public void schedulePeriodicSyncFromSettings()
   {
      final long interval = settings.getSyncInterval();
      
      if ( interval != SyncConstants.SYNC_INTERVAL_MANUAL )
      {
         schedulePeriodicSync( interval );
      }
   }
   
   
   
   @Override
   public void schedulePeriodicSync( long intervalMs )
   {
      setPeriodicSync( intervalMs );
   }
   
   
   
   @Override
   public void stopPeriodicSync()
   {
      final Account account = accountService.getRtmAccount();
      
      if ( account != null )
      {
         ContentResolver.removePeriodicSync( account,
                                             ContentAuthority.RTM,
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
   public IRtmSyncPartner getSyncPartner()
   {
      return syncPartnerFactory.createRtmSyncPartner();
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
            
            if ( syncInterval != SyncConstants.SYNC_INTERVAL_MANUAL )
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
   
   
   
   private void setPeriodicSync( long intervalMs )
   {
      final Account account = accountService.getRtmAccount();
      
      if ( account != null )
      {
         ContentResolver.addPeriodicSync( account,
                                          ContentAuthority.RTM,
                                          getExtras(),
                                          intervalMs / 1000 );
         
         log.i( getClass(),
                MessageFormat.format( "Added new periodic sync repeating every {0}",
                                      DateUtils.formatElapsedTime( intervalMs / 1000 ) ) );
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
      
      bundle.putBoolean( SyncConstants.SYNC_EXTRAS_SCHEDULED, Boolean.TRUE );
      bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, Boolean.TRUE );
      
      return bundle;
   }
}
