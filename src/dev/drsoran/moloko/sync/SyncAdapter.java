/*
 * Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.ParseException;

import android.accounts.Account;
import android.accounts.AccountManager;
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
import android.util.Log;
import android.util.Pair;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.ModificationsProviderPart;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.SyncProviderPart;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Modifications;
import dev.drsoran.provider.Rtm.Sync;


/**
 * SyncAdapter implementation for syncing to the platform RTM provider.
 */
public final class SyncAdapter extends AbstractThreadedSyncAdapter
{
   
   private final static String TAG = "Moloko."
      + SyncAdapter.class.getSimpleName();
   
   private final Context context;
   
   private SyncResult syncResult;
   
   private MolokoSyncResult molokoSyncResult;
   
   
   
   public SyncAdapter( Context context, boolean autoInitialize )
   {
      super( context, autoInitialize );
      
      this.context = context;
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
         Log.i( TAG, "Precessing sync with extras " + extras );
         
         ensureTransactionSupport( provider );
         this.syncResult = syncResult;
         
         String authToken = null;
         Service service = null;
         try
         {
            authToken = checkAccount( account );
            Log.i( TAG, "Retrieved auth token " + authToken );
            
            if ( authToken != null )
            {
               service = createService( account, authToken );
               molokoSyncResult = new MolokoSyncResult( context, syncResult );
               
               context.sendBroadcast( Intents.createSyncStartedIntent() );
               
               if ( isSettingsOnlySync( extras ) )
               {
                  performSettingsSync( provider, service );
               }
               else
               {
                  performFullSync( provider, service );
               }
            }
            else
            {
               invalidateAccount( authToken );
            }
         }
         catch ( SyncException e )
         {
            if ( syncResult.stats.numAuthExceptions > 0 )
            {
               Log.e( TAG, syncResult.stats.numAuthExceptions
                  + " authentication exceptions. Invalidating auth token." );
               
               invalidateAccount( authToken );
            }
            
            Log.e( TAG, "Applying sync operations batch failed. " + syncResult );
            clearSyncResult( syncResult );
         }
         finally
         {
            if ( service != null )
               service.shutdown();
            
            if ( syncResult.stats.numIoExceptions > 0 )
               MolokoApp.get( context.getApplicationContext() )
                        .getPeriodicSyncHander()
                        .delayNextSync( syncResult,
                                        ( 5 * DateUtils.MINUTE_IN_MILLIS ) / 1000 );
            
            this.syncResult = null;
            molokoSyncResult = null;
            
            context.sendBroadcast( Intents.createSyncFinishedIntent() );
         }
      }
      else
      {
         Log.i( TAG, "Didn't processed sync with extras " + extras );
      }
   }
   
   
   
   private String checkAccount( Account account )
   {
      final AccountManager accountManager = AccountManager.get( context );
      
      final String authToken;
      try
      {
         authToken = accountManager.blockingGetAuthToken( account,
                                                          Constants.AUTH_TOKEN_TYPE,
                                                          true /* notifyAuthFailure */);
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
      final Pair< String, String > credentials = getCredentials( account );
      
      final Service service;
      try
      {
         service = ServiceImpl.getInstance( context,
                                            new ApplicationInfo( credentials.first,
                                                                 credentials.second,
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
   
   
   
   private Pair< String, String > getCredentials( Account account )
   {
      final AccountManager accountManager = AccountManager.get( context );
      
      final String apiKey = accountManager.getUserData( account,
                                                        Constants.FEAT_API_KEY );
      final String sharedSecret = accountManager.getUserData( account,
                                                              Constants.FEAT_SHARED_SECRET );
      
      return Pair.create( apiKey, sharedSecret );
   }
   
   
   
   private void invalidateAccount( String authToken )
   {
      final AccountManager accountManager = AccountManager.get( context );
      accountManager.invalidateAuthToken( Constants.ACCOUNT_TYPE, authToken );
   }
   
   
   
   private static boolean isSettingsOnlySync( Bundle extras )
   {
      final boolean isSyncSettingsOnly = extras.getBoolean( dev.drsoran.moloko.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                                                            false );
      return isSyncSettingsOnly;
   }
   
   
   
   private void performFullSync( ContentProviderClient contentProvider,
                                 Service service )
   {
      try
      {
         performElementsSync( contentProvider, service );
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
         
         Log.i( TAG, "Applying sync operations batch succeded. " + syncResult );
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
                                     Service service )
   {
      final RtmAuth.Perms permission = AccountUtils.getAccessLevel( context );
      Log.i( TAG, "Sync with permission " + permission );
      
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
            Log.e( TAG, LogUtils.GENERIC_DB_ERROR );
      }
      
      return result;
   }
   
   
   
   public final static ModificationSet getModificationsFor( Context context,
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
               modifications = new ModificationSet( mods );
            else
               Log.e( TAG, LogUtils.GENERIC_DB_ERROR );
         }
         else
            Log.e( TAG, LogUtils.GENERIC_DB_ERROR );
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
   
   
   
   private final static boolean logSyncStep( String step, boolean result )
   {
      if ( result )
         Log.i( TAG, "Compute " + step + " sync ok" );
      else
         Log.e( TAG, "Compute " + step + " sync failed" );
      
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
