/*
 * Copyright (c) 2010 Ronny Röhricht
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
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.TimeLineMethod;
import com.mdt.rtm.TimeLineMethod.Transaction;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.content.ModificationsProviderPart;
import dev.drsoran.moloko.content.Rollback;
import dev.drsoran.moloko.content.RollbacksProviderPart;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.SyncProviderPart;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.moloko.service.RtmServiceConstants;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Connection;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Modifications;
import dev.drsoran.provider.Rtm.Rollbacks;
import dev.drsoran.provider.Rtm.Sync;


/**
 * SyncAdapter implementation for syncing to the platform RTM provider.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
   
   private final static String TAG = "Moloko."
      + SyncAdapter.class.getSimpleName();
   
   private final Context context;
   
   

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
         RtmProvider contentProvider = null;
         
         if ( provider.getLocalContentProvider() instanceof RtmProvider )
            contentProvider = (RtmProvider) provider.getLocalContentProvider();
         else
            throw new IllegalStateException( "no ContentProvider transaction support" );
         
         Log.i( TAG, "Precessing sync with extras " + extras );
         
         String authToken = null;
         
         final AccountManager accountManager = AccountManager.get( context );
         
         if ( accountManager != null )
         {
            try
            {
               // use the account manager to request the credentials
               authToken = accountManager.blockingGetAuthToken( account,
                                                                Constants.AUTH_TOKEN_TYPE,
                                                                true /* notifyAuthFailure */);
               Log.i( TAG, "Retreived auth token " + authToken );
               
               if ( authToken != null )
               {
                  // Check if we have all account info
                  final String apiKey = accountManager.getUserData( account,
                                                                    Constants.FEAT_API_KEY );
                  final String sharedSecret = accountManager.getUserData( account,
                                                                          Constants.FEAT_SHARED_SECRET );
                  
                  if ( apiKey == null || sharedSecret == null )
                  {
                     accountManager.invalidateAuthToken( Constants.ACCOUNT_TYPE,
                                                         authToken );
                  }
                  else
                  {
                     final Service service = ServiceImpl.getInstance( context,
                                                                      new ApplicationInfo( apiKey,
                                                                                           sharedSecret,
                                                                                           context.getString( R.string.app_name ),
                                                                                           authToken ) );
                     
                     final RtmAuth.Perms permission = AccountUtils.getAccessLevel( getContext() );
                     Log.i( TAG, "Sync with permission " + permission );
                     
                     // Rollback any failed sync changes, if any.
                     rollbackFailedSync( service, provider, permission );
                     
                     // Retrieve all modifications done so far.
                     final ModificationList modifictaions = getAllModifications();
                     if ( modifictaions == null )
                        throw new SQLException( "Retrieving the modifications failed" );
                     else
                        Log.i( TAG, "Retrieved " + modifictaions.size()
                           + " modifications" );
                     
                     // We can only create a time line with write permission. However, we need delete
                     // permission to make the server sync transactional. So without delete permissions
                     // we do only an incoming sync, indicated by timeLine == null.
                     RtmTimeline timeLine = null;
                     if ( permission == Perms.delete )
                     {
                        timeLine = service.timelines_create();
                        Log.i( TAG, "Created new time line " + timeLine );
                     }
                     
                     final DirectedSyncOperations batch = new DirectedSyncOperations();
                     
                     if ( computeOperationsBatch( service,
                                                  provider,
                                                  timeLine,
                                                  modifictaions,
                                                  syncResult,
                                                  extras,
                                                  batch ) )
                     {
                        // TODO: Apply local and the server operations in parallel in a background task.
                        
                        // Apply server operations at first cause as a result of these we
                        // have to update local elements.
                        final List< IContentProviderSyncOperation > localUpdates = applyServerOperations( service,
                                                                                                          contentProvider,
                                                                                                          batch.getServerOperations(),
                                                                                                          modifictaions,
                                                                                                          syncResult );
                        batch.getLocalOperations().addAll( localUpdates );
                        
                        final TransactionalAccess transactionalAccess = contentProvider.newTransactionalAccess();
                        transactionalAccess.beginTransaction();
                        
                        try
                        {
                           applyLocalOperations( provider,
                                                 batch.getLocalOperations(),
                                                 syncResult );
                           
                           transactionalAccess.setTransactionSuccessful();
                        }
                        finally
                        {
                           transactionalAccess.endTransaction();
                        }
                        
                        // If we synced only the settings, we do not update the
                        // last sync time cause this was no full sync.
                        if ( !extras.getBoolean( dev.drsoran.moloko.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                                                 false ) )
                           updateSyncTime();
                        
                        Log.i( TAG, "Applying sync operations batch succeded. "
                           + syncResult );
                     }
                     else
                     {
                        if ( syncResult.stats.numAuthExceptions > 0 )
                        {
                           Log.e( TAG,
                                  syncResult.stats.numAuthExceptions
                                     + " authentication exceptions. Invalidating auth token." );
                           
                           accountManager.invalidateAuthToken( Constants.ACCOUNT_TYPE,
                                                               authToken );
                        }
                        
                        Log.e( TAG, "Applying sync operations batch failed. "
                           + syncResult );
                        clearSyncResult( syncResult );
                     }
                  }
               }
               else
               {
                  accountManager.invalidateAuthToken( Constants.ACCOUNT_TYPE,
                                                      authToken );
               }
            }
            catch ( SQLException e )
            {
               syncResult.databaseError = true;
               Log.e( TAG, "SQLException", e );
            }
            catch ( IllegalStateException e )
            {
               syncResult.databaseError = true;
               Log.e( TAG, "IllegalStateException", e );
            }
            catch ( AuthenticatorException e )
            {
               syncResult.stats.numParseExceptions++;
               Log.e( TAG, "AuthenticatorException", e );
            }
            catch ( OperationCanceledException e )
            {
               Log.e( TAG, "OperationCanceledExcetpion", e );
            }
            catch ( IOException e )
            {
               Log.e( TAG, "IOException", e );
               syncResult.stats.numIoExceptions++;
            }
            catch ( ParseException e )
            {
               syncResult.stats.numParseExceptions++;
               Log.e( TAG, "ParseException", e );
            }
            catch ( ServiceException e )
            {
               if ( e instanceof ServiceInternalException )
                  SyncUtils.handleServiceInternalException( (ServiceInternalException) e,
                                                            TAG,
                                                            syncResult );
               else
               {
                  syncResult.stats.numIoExceptions++;
                  Log.e( TAG, "ServiceException", e );
               }
            }
            catch ( RemoteException e )
            {
               syncResult.stats.numIoExceptions++;
               Log.e( TAG, "RemoteException", e );
            }
            catch ( OperationApplicationException e )
            {
               syncResult.stats.numIoExceptions++;
               syncResult.databaseError = true;
               Log.e( TAG, "OperationApplicationException", e );
            }
         }
         else
         {
            syncResult.stats.numIoExceptions++;
            Log.e( TAG, "No AccountManager" );
         }
      }
      else
      {
         Log.i( TAG, "Didn't processed sync with extras " + extras );
      }
   }
   


   private List< IContentProviderSyncOperation > applyServerOperations( Service service,
                                                                        RtmProvider rtmProvider,
                                                                        List< ? extends IServerSyncOperation< ? > > serverOps,
                                                                        ModificationList modifications,
                                                                        SyncResult syncResult ) throws ServiceException
   {
      final ContentProviderSyncOperation.Builder localUpdatesBuilder = ContentProviderSyncOperation.newUpdate();
      final ContentProviderSyncOperation.Builder localDeletesBuilder = ContentProviderSyncOperation.newDelete();
      
      for ( IServerSyncOperation< ? extends IServerSyncable< ? > > serverSyncOperation : serverOps )
      {
         try
         {
            // Execute the server operation and retrieve the local updates as result.
            localUpdatesBuilder.add( serverSyncOperation.execute() );
         }
         catch ( ServiceException e )
         {
            Log.e( TAG, "Applying server operation failed.", e );
            
            // If we retrieved an element related error, then we ignore the
            // modification and handle it as committed. This can happen if
            // we have modifications locally which refer to elements which
            // have been deleted on server.
            if ( !RtmServiceConstants.RtmErrorCodes.isElementError( e.responseCode ) )
            {
               Log.e( TAG, "Initiating rollback" );
               
               // If something fails while reverting the changes, we receive a list of all
               // non-reverted changes and store them for later reverting.
               final List< TimeLineMethod.Transaction > nonReverted = serverSyncOperation.revert( service );
               
               Log.i( TAG, "Rollback finished with " + nonReverted.size()
                  + " non-reverted changes" );
               
               if ( nonReverted.size() > 0 )
               {
                  final ArrayList< ContentProviderOperation > transactionBatch = new ArrayList< ContentProviderOperation >( nonReverted.size() );
                  
                  // Store all non-reverted changes for later rollback.
                  for ( Transaction transaction : nonReverted )
                  {
                     transactionBatch.add( ContentProviderOperation.newInsert( Rollbacks.CONTENT_URI )
                                                                   .withValues( RollbacksProviderPart.getContentValues( transaction ) )
                                                                   .build() );
                  }
                  
                  final TransactionalAccess transactionalAccess = rtmProvider.newTransactionalAccess();
                  transactionalAccess.beginTransaction();
                  
                  try
                  {
                     rtmProvider.applyBatch( transactionBatch );
                     transactionalAccess.setTransactionSuccessful();
                  }
                  catch ( OperationApplicationException oae )
                  {
                     Log.e( TAG,
                            "Storing transactions for later rollback failed",
                            oae );
                  }
                  finally
                  {
                     transactionalAccess.endTransaction();
                  }
               }
               
               throw e;
            }
            else
            {
               Log.i( TAG, "Ignoring modification due to elemental error code "
                  + e.responseCode );
            }
         }
         
         // Remove possible modifications which lead to the server update after
         // successfully applying the server operation.
         localDeletesBuilder.add( serverSyncOperation.removeModification( modifications ) );
      }
      
      // Here we need a list cause we have updates and deletes
      final List< IContentProviderSyncOperation > localOps = new ArrayList< IContentProviderSyncOperation >( 2 );
      localOps.add( localUpdatesBuilder.build() );
      localOps.add( localDeletesBuilder.build() );
      
      return localOps;
   }
   


   private void applyLocalOperations( ContentProviderClient provider,
                                      List< ? extends IContentProviderSyncOperation > operations,
                                      SyncResult syncResult ) throws RemoteException,
                                                             OperationApplicationException
   {
      final ArrayList< ContentProviderOperation > contentProviderOperationsBatch = new ArrayList< ContentProviderOperation >();
      
      for ( IContentProviderSyncOperation contentProviderSyncOperation : operations )
      {
         final int count = contentProviderSyncOperation.getBatch( contentProviderOperationsBatch );
         ContentProviderSyncOperation.updateSyncResult( syncResult,
                                                        contentProviderSyncOperation.getOperationType(),
                                                        count );
      }
      
      provider.applyBatch( contentProviderOperationsBatch );
   }
   


   private void rollbackFailedSync( Service service,
                                    ContentProviderClient provider,
                                    RtmAuth.Perms permission ) throws RemoteException
   {
      final List< Rollback > rollbacks = RollbacksProviderPart.getRollbacks( provider );
      
      if ( rollbacks != null )
      {
         if ( rollbacks.size() > 0 )
         {
            // We can only revert with delete permissions
            if ( permission == Perms.delete )
            {
               for ( Rollback rollback : rollbacks )
               {
                  try
                  {
                     service.transactions_undo( rollback.getTimeLineId(),
                                                rollback.getTransactionId() );
                     Log.e( TAG, "Rolled back change " + rollback );
                  }
                  catch ( ServiceException e )
                  {
                     Log.e( TAG,
                            "Rolling back change " + rollback + " failed",
                            e );
                  }
               }
            }
            else
            {
               Log.w( TAG,
                      "Couldn't rollback changes due to lack of RTM access right" );
            }
            
            // Remove all stored rollbacks netherless they succeeded or not. If
            // we couldn't revert them then we have no hope.
            //
            // Here we use no transaction cause this is a single operation.
            provider.delete( Rollbacks.CONTENT_URI, "1", null );
         }
      }
      else
      {
         Log.e( TAG, "Retrieving rollbacks failed" );
         throw new RemoteException();
      }
   }
   


   private boolean computeOperationsBatch( Service service,
                                           ContentProviderClient provider,
                                           RtmTimeline timeLine,
                                           ModificationList modifications,
                                           SyncResult syncResult,
                                           Bundle extras,
                                           DirectedSyncOperations batch )
   {
      boolean ok = true;
      
      if ( !extras.getBoolean( dev.drsoran.moloko.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                               false ) )
      {
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
                                         timeLine,
                                         modifications,
                                         lastSyncOut,
                                         syncResult,
                                         batch );
         
         ok = ok && logSyncStep( "RtmLists", ok );
         
         // Sync RtmTasks
         ok = ok
            && RtmTasksSync.computeSync( service,
                                         provider,
                                         timeLine,
                                         modifications,
                                         lastSyncOut,
                                         syncResult,
                                         batch );
         
         ok = ok && logSyncStep( "RtmTasks", ok );
         
         // Sync locations
         ok = ok
            && RtmLocationsSync.computeSync( service,
                                             provider,
                                             lastSyncOut,
                                             syncResult,
                                             batch );
         
         ok = ok && logSyncStep( "RtmLocations", ok );
         
         // Sync contacts
         ok = ok
            && RtmContactsSync.computeSync( service,
                                            provider,
                                            lastSyncOut,
                                            syncResult,
                                            batch );
         
         ok = ok && logSyncStep( "RtmContacts", ok );
      }
      
      // Sync settings
      ok = ok
         && RtmSettingsSync.computeSync( service, provider, syncResult, batch );
      
      ok = ok && logSyncStep( "RtmSettings", ok );
      
      return ok;
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
   


   private final ModificationList getAllModifications()
   {
      ModificationList modifications = null;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Modifications.CONTENT_URI );
      
      if ( client != null )
      {
         final List< Modification > allMods = ModificationsProviderPart.getModifications( client,
                                                                                          null );
         client.release();
         
         if ( allMods != null )
         {
            modifications = new ModificationList( allMods );
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
      if ( !Connection.isConnected( getContext() ) )
      {
         Log.i( TAG, "No active connection found" );
         return false;
      }
      
      return ( bundle != null && ( bundle.containsKey( ContentResolver.SYNC_EXTRAS_INITIALIZE )
         || bundle.containsKey( ContentResolver.SYNC_EXTRAS_MANUAL ) || bundle.containsKey( dev.drsoran.moloko.sync.Constants.SYNC_EXTRAS_SCHEDULED ) ) );
   }
}
