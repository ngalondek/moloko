/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.ParseException;

import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.DateUtils;
import android.util.Pair;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.services.AccountCredentials;
import dev.drsoran.moloko.app.services.IAccountService;
import dev.drsoran.moloko.app.services.ISyncService;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.SyncProviderPart;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.moloko.content.db.ModificationsProviderPart;
import dev.drsoran.moloko.sync.MolokoSyncResult;
import dev.drsoran.moloko.sync.RtmContactsSync;
import dev.drsoran.moloko.sync.RtmListsSync;
import dev.drsoran.moloko.sync.RtmLocationsSync;
import dev.drsoran.moloko.sync.RtmSettingsSync;
import dev.drsoran.moloko.sync.RtmTasksSync;
import dev.drsoran.moloko.sync.TimeLineFactory;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Modifications;
import dev.drsoran.provider.Rtm.Sync;


/**
 * SyncAdapter implementation for syncing to the platform RTM provider.
 */
public final class SyncAdapter extends AbstractThreadedSyncAdapter
{
   private final static Class< SyncAdapter > TAG = SyncAdapter.class;
   
   private final AppContext context;
   
   private final ILog log;
   
   private final ISyncService syncService;
   
   private final IAccountService accountService;
   
   private SyncResult syncResult;
   
   private MolokoSyncResult molokoSyncResult;
   
   
   
   public SyncAdapter( AppContext context, boolean autoInitialize )
   {
      super( context, autoInitialize );
      this.context = context;
      this.log = context.Log();
      this.syncService = context.getSyncService();
      this.accountService = context.getAccountService();
   }
   
   
   
   @Override
   public void onPerformSync( Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              SyncResult syncResult )
   {
      if ( shouldProcessRequest( extras ) )
      {
         log.i( TAG, "Precessing sync with extras " + extras );
         
         ensureTransactionSupport( provider );
         this.syncResult = syncResult;
         
         context.sendBroadcast( Intents.createSyncStartedIntent() );
         
         String authToken = null;
         Service rtmWebService = null;
         try
         {
            authToken = checkAndGetAuthToken( account );
            log.d( TAG, "Retrieved auth token " + authToken );
            
            if ( authToken != null )
            {
               rtmWebService = createService( account, authToken );
               molokoSyncResult = new MolokoSyncResult( context, syncResult );
               
               if ( isSettingsOnlySync( extras ) )
               {
                  performSettingsSync( provider, rtmWebService );
               }
               else
               {
                  final RtmAuth.Perms permission = accountService.getAccessLevel( account );
                  performFullSync( provider, permission, rtmWebService );
               }
            }
            else
            {
               accountService.invalidateAccount( account );
            }
         }
         catch ( SyncException e )
         {
            if ( syncResult.stats.numAuthExceptions > 0 )
            {
               log.e( TAG, syncResult.stats.numAuthExceptions
                  + " authentication exceptions. Invalidating auth token." );
               
               accountService.invalidateAccount( account );
            }
            
            log.e( TAG, "Applying sync operations batch failed. " + syncResult );
            clearSyncResult( syncResult );
         }
         finally
         {
            if ( rtmWebService != null )
            {
               rtmWebService.shutdown();
            }
            
            if ( syncResult.stats.numIoExceptions > 0 )
            {
               syncService.delayNextPeriodicSync( syncResult,
                                                  ( 5 * DateUtils.MINUTE_IN_MILLIS ) / 1000 );
            }
            
            this.syncResult = null;
            molokoSyncResult = null;
            
            context.sendBroadcast( Intents.createSyncFinishedIntent() );
         }
      }
      else
      {
         log.i( TAG, "Didn't processed sync with extras " + extras );
      }
   }
   
   
   
   private String checkAndGetAuthToken( Account account )
   {
      final String authToken;
      try
      {
         authToken = accountService.getAuthToken( account );
      }
      catch ( OperationCanceledException e )
      {
         throw new SyncException( e );
      }
      catch ( AuthenticatorException e )
      {
         ++syncResult.stats.numAuthExceptions;
         throw new SyncException( e );
      }
      catch ( IOException e )
      {
         ++syncResult.stats.numIoExceptions;
         throw new SyncException( e );
      }
      
      return authToken;
   }
   
   
   
   private Service createService( Account account, String authToken )
   {
      final AccountCredentials credentials = accountService.getCredentials( account );
      
      final Service service;
      try
      {
         service = ServiceImpl.getInstance( context.getConnectionService()
                                                   .getRtmConnectionFactory(),
                                            log,
                                            context.getSettings()
                                                   .isUsingHttps(),
                                            new ApplicationInfo( credentials.ApiKey,
                                                                 credentials.SharedSecret,
                                                                 context.getString( R.string.app_name ),
                                                                 authToken ) );
      }
      catch ( ServiceInternalException e )
      {
         SyncUtils.handleServiceInternalException( e, TAG, syncResult );
         throw new SyncException( e );
      }
      
      return service;
   }
   
   
   
   private static boolean isSettingsOnlySync( Bundle extras )
   {
      final boolean isSyncSettingsOnly = extras.getBoolean( dev.drsoran.moloko.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                                                            false );
      return isSyncSettingsOnly;
   }
   
   
   
   private void performFullSync( ContentProviderClient contentProvider,
                                 Perms permission,
                                 Service service )
   {
      try
      {
         performElementsSync( contentProvider, permission, service );
         performSettingsSync( contentProvider, service );
         
         updateSyncTime();
      }
      catch ( SQLException e )
      {
         syncResult.databaseError = true;
         throw new SyncException( e );
      }
      catch ( IllegalStateException e )
      {
         syncResult.databaseError = true;
         throw new SyncException( e );
      }
      catch ( ParseException e )
      {
         syncResult.stats.numParseExceptions++;
         throw new SyncException( e );
      }
   }
   
   
   
   private RtmProvider ensureTransactionSupport( ContentProviderClient provider )
   {
      if ( provider.getLocalContentProvider() instanceof RtmProvider )
         return (RtmProvider) provider.getLocalContentProvider();
      else
         throw new IllegalStateException( "no ContentProvider transaction support" );
   }
   
   
   
   private void applyTransactional( ContentProviderClient contentProvider )
   {
      final RtmProvider rtmProvider = ensureTransactionSupport( contentProvider );
      final TransactionalAccess transactionalAccess = rtmProvider.newTransactionalAccess();
      transactionalAccess.beginTransaction();
      
      try
      {
         applyLocalOperations( contentProvider );
         
         transactionalAccess.setTransactionSuccessful();
         
         log.i( TAG, "Applying sync operations batch succeded. " + syncResult );
      }
      // let outer try catch the exception
      finally
      {
         transactionalAccess.endTransaction();
      }
   }
   
   
   
   private void applyLocalOperations( ContentProviderClient provider )
   {
      final ArrayList< ContentProviderOperation > contentProviderOperationsBatch = new ArrayList< ContentProviderOperation >();
      
      for ( IContentProviderSyncOperation contentProviderSyncOperation : molokoSyncResult.localOps )
      {
         final int count = contentProviderSyncOperation.getBatch( contentProviderOperationsBatch );
         ContentProviderSyncOperation.updateSyncResult( syncResult,
                                                        contentProviderSyncOperation.getOperationType(),
                                                        count );
      }
      
      try
      {
         provider.applyBatch( contentProviderOperationsBatch );
      }
      catch ( RemoteException e )
      {
         ++syncResult.stats.numIoExceptions;
         throw new SyncException( e );
      }
      catch ( OperationApplicationException e )
      {
         syncResult.databaseError = true;
         throw new SyncException( e );
      }
   }
   
   
   
   private void performSettingsSync( ContentProviderClient contentProvider,
                                     Service service )
   {
      molokoSyncResult.localOps.clear();
      
      final boolean ok = RtmSettingsSync.computeSync( service,
                                                      contentProvider,
                                                      molokoSyncResult );
      logSyncStep( "RtmSettings", ok );
      if ( !ok )
      {
         throw new SyncException();
      }
      
      applyTransactional( contentProvider );
   }
   
   
   
   private void performElementsSync( ContentProviderClient contentProvider,
                                     Perms permission,
                                     Service service )
   {
      log.i( TAG, "Sync with permission " + permission );
      
      // We can only create a time line with write permission. However, we need delete
      // permission to make the server sync transactional. So without delete permissions
      // we do only an incoming sync, indicated by timeLine == null.
      TimeLineFactory timeLineFactory = null;
      if ( permission == Perms.delete )
      {
         timeLineFactory = new TimeLineFactory( service );
      }
      
      computeElementsOperationsBatch( contentProvider, service, timeLineFactory );
      
      applyTransactional( contentProvider );
   }
   
   
   
   private void computeElementsOperationsBatch( ContentProviderClient provider,
                                                Service service,
                                                TimeLineFactory timeLineFactory )
   {
      boolean ok = true;
      
      molokoSyncResult.localOps.clear();
      Date lastSyncOut = null;
      
      {
         final Pair< Long, Long > lastSync = getSyncTime();
         ok = lastSync != null;
         if ( ok && lastSync.second != null )
            lastSyncOut = new Date( lastSync.second );
      }
      
      // Sync RtmList
      ok = ok
         && RtmListsSync.computeSync( service,
                                      provider,
                                      timeLineFactory,
                                      lastSyncOut,
                                      molokoSyncResult );
      
      ok = ok && logSyncStep( "RtmList", ok );
      
      // Sync RtmTasks + Notes
      ok = ok
         && RtmTasksSync.computeSync( service,
                                      provider,
                                      timeLineFactory,
                                      lastSyncOut,
                                      molokoSyncResult );
      
      ok = ok && logSyncStep( "RtmTasks and Notes", ok );
      
      // Sync locations
      ok = ok
         && RtmLocationsSync.computeSync( service,
                                          provider,
                                          lastSyncOut,
                                          molokoSyncResult );
      
      ok = ok && logSyncStep( "RtmLocations", ok );
      
      // Sync contacts
      ok = ok
         && RtmContactsSync.computeSync( service,
                                         provider,
                                         lastSyncOut,
                                         molokoSyncResult );
      
      ok = ok && logSyncStep( "RtmContacts", ok );
      
      if ( !ok )
      {
         throw new SyncException();
      }
   }
   
   
   
   private final Pair< Long, Long > getSyncTime()
   {
      Pair< Long, Long > result = null;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Sync.CONTENT_URI );
      
      if ( client != null )
      {
         result = SyncProviderPart.getLastInAndLastOut( client );
         
         client.release();
         
         if ( result != null )
         {
            // SPECIAL CASE: We check the returned dates. In case the device clock
            // was adjusted, we may have stored a date that is way too far in the
            // future. This may break the incremental sync for a long time. In this case we do a
            // full sync and store the new date.
            if ( ( result.first != null && result.first > System.currentTimeMillis() )
               || ( result.second != null && result.second > System.currentTimeMillis() ) )
            {
               result = new Pair< Long, Long >( null, null );
            }
         }
         else
         {
            log.e( TAG, LogUtils.GENERIC_DB_ERROR );
         }
      }
      
      return result;
   }
   
   
   
   public final ModificationSet getModificationsFor( Context context,
                                                     Uri... entityUris )
   {
      ModificationSet modifications = new ModificationSet();
      
      if ( entityUris.length > 0 )
      {
         final ContentProviderClient client = context.getContentResolver()
                                                     .acquireContentProviderClient( Modifications.CONTENT_URI );
         
         if ( client != null )
         {
            final List< Modification > mods = ModificationsProviderPart.getModifications( client,
                                                                                          entityUris );
            client.release();
            
            if ( mods != null )
            {
               modifications = new ModificationSet( mods );
            }
            else
            {
               log.e( TAG, LogUtils.GENERIC_DB_ERROR );
            }
         }
         else
         {
            log.e( TAG, LogUtils.GENERIC_DB_ERROR );
         }
      }
      
      return modifications;
   }
   
   
   
   private final void updateSyncTime()
   {
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Sync.CONTENT_URI );
      
      if ( client != null )
      {
         final Long millis = Long.valueOf( System.currentTimeMillis() );
         SyncProviderPart.updateSync( client, millis, millis );
         
         client.release();
      }
   }
   
   
   
   private final boolean logSyncStep( String step, boolean result )
   {
      if ( result )
      {
         log.i( TAG, "Compute " + step + " sync ok" );
      }
      else
      {
         log.e( TAG, "Compute " + step + " sync failed" );
      }
      
      return result;
   }
   
   
   
   private final static void clearSyncResult( SyncResult syncResult )
   {
      syncResult.stats.numInserts = 0;
      syncResult.stats.numUpdates = 0;
      syncResult.stats.numDeletes = 0;
   }
   
   
   
   private final boolean shouldProcessRequest( Bundle bundle )
   {
      return ( bundle != null && ( bundle.containsKey( ContentResolver.SYNC_EXTRAS_INITIALIZE )
         || bundle.containsKey( ContentResolver.SYNC_EXTRAS_MANUAL ) || bundle.containsKey( dev.drsoran.moloko.sync.Constants.SYNC_EXTRAS_SCHEDULED ) ) );
   }
}
