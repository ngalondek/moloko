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
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

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
import dev.drsoran.moloko.util.Connection;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Modifications;
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
                     
                     // We can only create a time line with write permission. However, we need delete
                     // permission to make the server sync transactional. So without delete permissions
                     // we do only an incoming sync, indicated by timeLine == null.
                     TimeLineFactory timeLineFactory = null;
                     if ( permission == Perms.delete )
                     {
                        timeLineFactory = new TimeLineFactory( service );
                     }
                     
                     final MolokoSyncResult batch = new MolokoSyncResult( context,
                                                                          syncResult );
                     
                     if ( computeOperationsBatch( service,
                                                  provider,
                                                  timeLineFactory,
                                                  extras,
                                                  batch ) )
                     {
                        final TransactionalAccess transactionalAccess = contentProvider.newTransactionalAccess();
                        transactionalAccess.beginTransaction();
                        
                        try
                        {
                           applyLocalOperations( provider,
                                                 batch.localOps,
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
   


   // TODO: Commented
   // private void rollbackFailedSync( Service service,
   // ContentProviderClient provider,
   // RtmAuth.Perms permission ) throws RemoteException
   // {
   // final List< Rollback > rollbacks = RollbacksProviderPart.getRollbacks( provider );
   //
   // if ( rollbacks != null )
   // {
   // if ( rollbacks.size() > 0 )
   // {
   // // We can only revert with delete permissions
   // if ( permission == Perms.delete )
   // {
   // for ( Rollback rollback : rollbacks )
   // {
   // try
   // {
   // service.transactions_undo( rollback.getTimeLineId(),
   // rollback.getTransactionId() );
   // Log.e( TAG, "Rolled back change " + rollback );
   // }
   // catch ( ServiceException e )
   // {
   // Log.e( TAG,
   // "Rolling back change " + rollback + " failed",
   // e );
   // }
   // }
   // }
   // else
   // {
   // Log.w( TAG,
   // "Couldn't rollback changes due to lack of RTM access right" );
   // }
   //
   // // Remove all stored rollbacks netherless they succeeded or not. If
   // // we couldn't revert them then we have no hope.
   // //
   // // Here we use no transaction cause this is a single operation.
   // provider.delete( Rollbacks.CONTENT_URI, "1", null );
   // }
   // }
   // else
   // {
   // Log.e( TAG, "Retrieving rollbacks failed" );
   // throw new RemoteException();
   // }
   // }
   
   private boolean computeOperationsBatch( Service service,
                                           ContentProviderClient provider,
                                           TimeLineFactory timeLineFactory,
                                           Bundle extras,
                                           MolokoSyncResult batch )
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
            && RtmListsSync.computeSync( service, provider, lastSyncOut, batch );
         
         ok = ok && logSyncStep( "RtmLists", ok );
         
         // Sync RtmTasks + Notes
         ok = ok
            && RtmTasksSync.computeSync( service,
                                         provider,
                                         timeLineFactory,
                                         lastSyncOut,
                                         batch );
         
         ok = ok && logSyncStep( "RtmTasks and Notes", ok );
         
         // Sync locations
         ok = ok
            && RtmLocationsSync.computeSync( service,
                                             provider,
                                             lastSyncOut,
                                             batch );
         
         ok = ok && logSyncStep( "RtmLocations", ok );
         
         // Sync contacts
         ok = ok
            && RtmContactsSync.computeSync( service,
                                            provider,
                                            lastSyncOut,
                                            batch );
         
         ok = ok && logSyncStep( "RtmContacts", ok );
      }
      
      // Sync settings
      ok = ok && RtmSettingsSync.computeSync( service, provider, batch );
      
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
   


   public final static ModificationSet getAllModifications( Context context )
   {
      ModificationSet modifications = null;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Modifications.CONTENT_URI );
      
      if ( client != null )
      {
         final List< Modification > allMods = ModificationsProviderPart.getModifications( client,
                                                                                          null );
         client.release();
         
         if ( allMods != null )
            modifications = new ModificationSet( allMods );
         else
            Log.e( TAG, LogUtils.GENERIC_DB_ERROR );
      }
      else
         Log.e( TAG, LogUtils.GENERIC_DB_ERROR );
      
      return modifications;
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
      if ( !Connection.isConnected( getContext() ) )
      {
         Log.i( TAG, "No active connection found" );
         return false;
      }
      
      return ( bundle != null && ( bundle.containsKey( ContentResolver.SYNC_EXTRAS_INITIALIZE )
         || bundle.containsKey( ContentResolver.SYNC_EXTRAS_MANUAL ) || bundle.containsKey( dev.drsoran.moloko.sync.Constants.SYNC_EXTRAS_SCHEDULED ) ) );
   }
}
