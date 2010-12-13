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
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.ServiceImpl;
import com.mdt.rtm.ServiceInternalException;

import dev.drsoran.moloko.R;
import dev.drsoran.moloko.auth.Constants;
import dev.drsoran.moloko.content.SyncProviderPart;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.provider.Rtm.Sync;


/**
 * SyncAdapter implementation for syncing to the platform RTM provider.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter
{
   
   public final static class Direction
   {
      public final static int IN = 1 << 0;
      
      public final static int OUT = 1 << 1;
   }
   
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
                     final ServiceImpl serviceImpl = ServiceImpl.getInstance( context,
                                                                              new ApplicationInfo( apiKey,
                                                                                                   sharedSecret,
                                                                                                   context.getString( R.string.app_name ),
                                                                                                   authToken ) );
                     
                     final ArrayList< IContentProviderSyncOperation > batch = new ArrayList< IContentProviderSyncOperation >();
                     
                     if ( computeOperationsBatch( serviceImpl,
                                                  provider,
                                                  syncResult,
                                                  extras,
                                                  batch ) )
                     {
                        final ArrayList< ContentProviderOperation > contentProviderOperationsBatch = new ArrayList< ContentProviderOperation >();
                        
                        for ( IContentProviderSyncOperation contentProviderSyncOperation : batch )
                        {
                           final int count = contentProviderSyncOperation.getBatch( contentProviderOperationsBatch );
                           ContentProviderSyncOperation.updateSyncResult( syncResult,
                                                                          contentProviderSyncOperation.getOperationType(),
                                                                          count );
                        }
                        
                        provider.applyBatch( contentProviderOperationsBatch );
                        
                        // If we synced only the settings, we do not update the
                        // last sync time cause this was no full sync.
                        if ( !extras.getBoolean( dev.drsoran.moloko.service.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                                                 false ) )
                           updateSyncTime( Direction.IN );
                        
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
            catch ( final AuthenticatorException e )
            {
               syncResult.stats.numParseExceptions++;
               Log.e( TAG, "AuthenticatorException", e );
            }
            catch ( final OperationCanceledException e )
            {
               Log.e( TAG, "OperationCanceledExcetpion", e );
            }
            catch ( final IOException e )
            {
               Log.e( TAG, "IOException", e );
               syncResult.stats.numIoExceptions++;
            }
            catch ( final ParseException e )
            {
               syncResult.stats.numParseExceptions++;
               Log.e( TAG, "ParseException", e );
            }
            catch ( ServiceInternalException e )
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
   


   private boolean computeOperationsBatch( ServiceImpl serviceImpl,
                                           ContentProviderClient provider,
                                           SyncResult syncResult,
                                           Bundle extras,
                                           ArrayList< IContentProviderSyncOperation > batch )
   {
      boolean ok = true;
      
      if ( !extras.getBoolean( dev.drsoran.moloko.service.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                               false ) )
      {
         // Sync RtmList
         ok = RtmListsSync.in_computeSync( provider,
                                           serviceImpl,
                                           syncResult,
                                           batch );
         
         Log.i( TAG, "Compute RtmLists sync " + ( ok ? "ok" : "failed" ) );
         
         // Sync RtmTasks
         ok = ok
            && RtmTasksSync.in_computeSync( provider,
                                            serviceImpl,
                                            syncResult,
                                            batch );
         
         Log.i( TAG, "Compute RtmTasks sync " + ( ok ? "ok" : "failed" ) );
         
         // Sync locations
         ok = ok
            && RtmLocationsSync.in_computeSync( provider,
                                                serviceImpl,
                                                syncResult,
                                                batch );
         Log.i( TAG, "Compute RtmLocations sync " + ( ok ? "ok" : "failed" ) );
         
         // Sync contacts
         ok = ok
            && RtmContactsSync.in_computeSync( provider,
                                               serviceImpl,
                                               syncResult,
                                               batch );
         
         Log.i( TAG, "Compute RtmContactsSync sync " + ( ok ? "ok" : "failed" ) );
      }
      
      // Sync settings
      ok = ok
         && RtmSettingsSync.in_computeSync( provider,
                                            serviceImpl,
                                            syncResult,
                                            batch );
      
      Log.i( TAG, "Compute RtmSettings sync " + ( ok ? "ok" : "failed" ) );
      
      if ( !ok )
         batch.clear();
      
      return ok;
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
   


   private final void updateSyncTime( int direction )
   {
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Sync.CONTENT_URI );
      
      if ( client != null )
      {
         final Long millis = Long.valueOf( System.currentTimeMillis() );
         
         SyncProviderPart.updateSync( client,
                                      ( ( direction & Direction.IN ) != 0 )
                                                                           ? millis
                                                                           : null,
                                      ( ( direction & Direction.OUT ) != 0 )
                                                                            ? millis
                                                                            : null );
      }
   }
}
