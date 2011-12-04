/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

package dev.drsoran.moloko.sync.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.accounts.Account;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.Pair;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.auth.prefs.SyncIntervalPreference;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.RtmProvider;
import dev.drsoran.moloko.content.SyncProviderPart;
import dev.drsoran.moloko.sync.Constants;
import dev.drsoran.moloko.sync.operation.INoopSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Connection;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.UIUtils;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Sync;


public final class SyncUtils
{
   private static final String TAG = "Moloko."
      + SyncUtils.class.getSimpleName();
   
   

   private SyncUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   


   public final static void handleServiceInternalException( ServiceInternalException exception,
                                                            String tag,
                                                            SyncResult syncResult )
   {
      final Exception internalException = exception.getEnclosedException();
      
      if ( internalException != null )
      {
         Log.e( TAG, exception.responseMessage, internalException );
         
         if ( internalException instanceof IOException )
            ++syncResult.stats.numIoExceptions;
         else
            ++syncResult.stats.numParseExceptions;
      }
      else
      {
         Log.e( TAG, exception.responseMessage );
         ++syncResult.stats.numIoExceptions;
      }
   }
   


   public final static void requestManualSync( FragmentActivity activity )
   {
      SyncUtils.requestManualSync( activity, SyncUtils.isReadyToSync( activity ) );
   }
   


   public final static void requestManualSync( FragmentActivity activity,
                                               Account account )
   {
      if ( account != null )
      {
         final Bundle bundle = new Bundle();
         
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_MANUAL, true );
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, true );
         
         ContentResolver.requestSync( account, Rtm.AUTHORITY, bundle );
      }
      else
      {
         UIUtils.showNoAccountDialog( activity, null );
      }
   }
   


   public final static void requestSettingsOnlySync( Context context,
                                                     Account account )
   {
      if ( account != null )
      {
         final Bundle bundle = new Bundle();
         
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_MANUAL, true );
         bundle.putBoolean( dev.drsoran.moloko.sync.Constants.SYNC_EXTRAS_ONLY_SETTINGS,
                            true );
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, false );
         
         ContentResolver.requestSync( account, Rtm.AUTHORITY, bundle );
      }
      else
      {
         // TODO: Show NoAccountDialogFragment if we use PreferenceFragment
         context.startActivity( Intents.createNewAccountIntent() );
      }
   }
   


   public final static void requestScheduledSync( Context context,
                                                  Account account )
   {
      if ( account != null )
      {
         final Bundle bundle = new Bundle();
         
         bundle.putBoolean( Constants.SYNC_EXTRAS_SCHEDULED, true );
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, true );
         
         ContentResolver.requestSync( account, Rtm.AUTHORITY, bundle );
      }
   }
   


   public final static void cancelSync( Context context )
   {
      final Account account = AccountUtils.getRtmAccount( context );
      
      if ( account != null )
         ContentResolver.cancelSync( account, Rtm.AUTHORITY );
   }
   


   public final static Account isReadyToSync( Context context )
   {
      // Check if we are connected.
      boolean sync = Connection.isConnected( context );
      
      Account account = null;
      
      if ( sync )
      {
         account = AccountUtils.getRtmAccount( context );
         
         // Check if we have an account and the sync has not been disabled
         // in between.
         sync = account != null
            && ContentResolver.getSyncAutomatically( account, Rtm.AUTHORITY );
      }
      
      return account;
   }
   


   public final static boolean isSyncing( Context context )
   {
      final Account account = AccountUtils.getRtmAccount( context );
      
      return account != null
         && !ContentResolver.isSyncPending( account, Rtm.AUTHORITY )
         && ContentResolver.isSyncActive( account, Rtm.AUTHORITY );
   }
   


   /**
    * Loads the start time from the Sync database table and the interval from the settings.
    */
   public final static void schedulePeriodicSync( Context context )
   {
      final long interval = SyncIntervalPreference.getSyncInterval( context );
      
      if ( interval != Constants.SYNC_INTERVAL_MANUAL )
         SyncUtils.schedulePeriodicSync( context, interval );
   }
   


   /**
    * Loads the start time from the Sync database table.
    */
   public final static void schedulePeriodicSync( Context context, long interval )
   {
      long startUtc = System.currentTimeMillis();
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( Sync.CONTENT_URI );
      
      if ( client != null )
      {
         final Pair< Long, Long > lastSync = SyncProviderPart.getLastInAndLastOut( client );
         
         if ( lastSync != null )
         {
            final long lastSyncIn = ( lastSync.first != null ) ? lastSync.first
                                                              : Long.MAX_VALUE;
            final long lastSyncOut = ( lastSync.second != null )
                                                                ? lastSync.second
                                                                : Long.MAX_VALUE;
            
            final long earliestLastSync = Math.min( lastSyncIn, lastSyncOut );
            
            // Ever synced?
            if ( earliestLastSync != Long.MAX_VALUE )
            {
               startUtc = earliestLastSync + interval;
            }
         }
      }
      
      MolokoApp.schedulePeriodicSync( startUtc, interval );
   }
   


   public final static < V > boolean hasChanged( V oldVal, V newVal )
   {
      return ( oldVal == null && newVal != null )
         || ( oldVal != null && ( newVal == null || !oldVal.equals( newVal ) ) );
   }
   
   
   public enum SyncResultDirection
   {
      NOTHING, LOCAL, SERVER
   }
   
   

   public final static < V > SyncResultDirection getSyncDirection( SyncProperties properties,
                                                                   String columnName,
                                                                   V serverValue,
                                                                   V localValue,
                                                                   Class< V > valueClass )
   {
      SyncResultDirection syncDir = SyncResultDirection.NOTHING;
      
      final Modification modification = properties.getModification( columnName );
      
      // Check if we should simply sync out and have a modification
      if ( properties.serverModDate == null && modification != null )
      {
         syncDir = SyncResultDirection.SERVER;
      }
      
      else if ( hasChanged( serverValue, localValue ) )
      {
         // Check if the local value was modified
         if ( modification != null )
         {
            // MERGE
            final V syncedValue = modification.getSyncedValue( valueClass );
            
            // Check if the server value has changed compared to the last synced value.
            if ( hasChanged( syncedValue, serverValue ) )
            {
               // CONFLICT: Local and server element has changed.
               // Let the modified date of the elements decide in which direction to sync.
               //
               // In case of equal dates we take the server value cause this
               // value we have transferred already.
               if ( properties.serverModDate != null
                  && ( properties.serverModDate.getTime() >= properties.localModDate.getTime() ) )
                  // LOCAL UPDATE: The server element was modified after the local value.
                  syncDir = SyncResultDirection.LOCAL;
               else
                  // SERVER UPDATE: The local element was modified after the server element.
                  syncDir = SyncResultDirection.SERVER;
            }
            else
               // SERVER UPDATE: The server value has not been changed since last sync,
               // so use local modified value.
               syncDir = SyncResultDirection.SERVER;
         }
         
         // LOCAL UPDATE: If the element has not locally changed, take the server version
         else
         {
            syncDir = SyncResultDirection.LOCAL;
         }
      }
      
      return syncDir;
   }
   


   public final static < T > void applyServerOperations( RtmProvider rtmProvider,
                                                         List< ? extends IServerSyncOperation< T > > serverOps,
                                                         List< T > sortedServerElements,
                                                         Comparator< ? super T > cmp ) throws ServiceException
   {
      for ( IServerSyncOperation< T > serverOp : serverOps )
      {
         if ( !( serverOp instanceof INoopSyncOperation ) )
         {
            try
            {
               final T result = serverOp.execute( rtmProvider );
               
               if ( result == null )
                  throw new ServiceException( -1,
                                              "ServerSyncOperation produced no result" );
               
               // If the set already contains an element in respect to the Comparator,
               // then we update it by the new received.
               final int pos = Collections.binarySearch( sortedServerElements,
                                                         result,
                                                         cmp );
               
               if ( pos >= 0 )
               {
                  sortedServerElements.remove( pos );
                  sortedServerElements.add( pos, result );
               }
               else
               {
                  sortedServerElements.add( ( -pos - 1 ), result );
               }
            }
            catch ( ServiceException e )
            {
               Log.e( TAG, "Applying server operation failed", e );
               throw e;
            }
         }
      }
   }
}
