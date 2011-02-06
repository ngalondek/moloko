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

package dev.drsoran.moloko.service.sync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.ParseException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProvider;
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
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmTimeline;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationsProviderPart;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.SyncProviderPart;
import dev.drsoran.moloko.content.TransactionalAccess;
import dev.drsoran.moloko.service.sync.lists.ModificationList;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.util.AccountUtils;
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
                     // permission to make the server sync transactional.So without delete permissions
                     // we do only an incoming sync, indicated by time line == null.
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
                                                  syncResult,
                                                  extras,
                                                  batch ) )
                     {
                        // TODO: Apply local and the server operations in parallel in a background task.
                        
                        // Apply server operations at first cause as a result of these we
                        // have to update local elements.
                        List< IContentProviderSyncOperation > localUpdates = applyServerOperations( service,
                                                                                                    batch.getServerOperations(),
                                                                                                    syncResult );
                        batch.getLocalOperations().addAll( localUpdates );
                        
                        final ContentProvider contentProvider = provider.getLocalContentProvider();
                        
                        if ( contentProvider instanceof RtmProvider )
                        {
                           final TransactionalAccess transactionalAccess = ( (RtmProvider) contentProvider ).newTransactionalAccess();
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
                        }
                        else
                        {
                           throw new IllegalStateException( "no ContentProvider transaction support" );
                        }
                        
                        // If we synced only the settings, we do not update the
                        // last sync time cause this was no full sync.
                        if ( !extras.getBoolean( dev.drsoran.moloko.service.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
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
               syncResult.stats.numIoExceptions++;
               Log.e( TAG, "ServiceInternalException", e );
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
                                                                        List< IServerSyncOperation< ? > > serverOps,
                                                                        SyncResult syncResult ) throws ServiceException
   {
      final List< IContentProviderSyncOperation > contentProviderSyncOperations = new LinkedList< IContentProviderSyncOperation >();
      
      for ( IServerSyncOperation< ? > serverSyncOperation : serverOps )
      {
         
      }
      
      return contentProviderSyncOperations;
   }
   


   private void applyLocalOperations( ContentProviderClient provider,
                                      List< IContentProviderSyncOperation > operations,
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
   


   private boolean computeOperationsBatch( Service service,
                                           ContentProviderClient provider,
                                           RtmTimeline timeLine,
                                           SyncResult syncResult,
                                           Bundle extras,
                                           DirectedSyncOperations batch )
   {
      boolean ok = true;
      
      if ( !extras.getBoolean( dev.drsoran.moloko.service.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                               false ) )
      {
         Date lastSyncOut = null;
         
         {
            final Pair< Long, Long > lastSync = getSyncTime();
            ok = lastSync != null;
            if ( ok && lastSync.second != null )
               lastSyncOut = new Date( lastSync.second );
         }
         
         ModificationList allModifications = null;
         
         if ( ok )
            allModifications = getAllModifications();
         
         ok = ok && allModifications != null;
         
         // Sync RtmList
         ok = ok
            && RtmListsSync.computeSync( service,
                                         provider,
                                         timeLine,
                                         allModifications,
                                         lastSyncOut,
                                         syncResult,
                                         batch );
         
         ok = ok && logSyncStep( "RtmLists", ok );
         
         // Sync RtmTasks
         ok = ok
            && RtmTasksSync.computeSync( service,
                                         provider,
                                         timeLine,
                                         allModifications,
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
   


   private final static boolean shouldProcessRequest( Bundle bundle )
   {
      return ( bundle != null && ( bundle.containsKey( ContentResolver.SYNC_EXTRAS_INITIALIZE )
         || bundle.containsKey( ContentResolver.SYNC_EXTRAS_MANUAL ) || bundle.containsKey( dev.drsoran.moloko.service.sync.Constants.SYNC_EXTRAS_SCHEDULED ) ) );
   }
}
