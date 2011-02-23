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

package dev.drsoran.moloko.util;

import java.io.IOException;
import java.util.Date;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.Pair;

import com.mdt.rtm.ServiceInternalException;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.auth.prefs.SyncIntervalPreference;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.content.SyncProviderPart;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import dev.drsoran.moloko.service.sync.Constants;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.service.sync.operation.ISyncOperation.Op;
import dev.drsoran.moloko.service.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable;
import dev.drsoran.moloko.service.sync.syncable.IServerSyncable.SyncDirection;
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
         {
            ++syncResult.stats.numIoExceptions;
         }
         else
         {
            ++syncResult.stats.numParseExceptions;
         }
      }
      else
      {
         Log.e( TAG, exception.responseMessage );
         ++syncResult.stats.numIoExceptions;
      }
   }
   


   public final static void requestSync( Context context, boolean manual )
   {
      final Account account = SyncUtils.isReadyToSync( context );
      
      if ( account != null )
         SyncUtils.requestSync( context, account, manual );
   }
   


   public final static void requestSync( Context context,
                                         Account account,
                                         boolean manual )
   {
      final Bundle bundle = new Bundle();
      
      if ( manual )
         bundle.putBoolean( ContentResolver.SYNC_EXTRAS_MANUAL, true );
      else
         bundle.putBoolean( Constants.SYNC_EXTRAS_SCHEDULED, true );
      
      ContentResolver.requestSync( account, Rtm.AUTHORITY, bundle );
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
   public final static void scheduleSyncAlarm( Context context )
   {
      final long interval = SyncIntervalPreference.getSyncInterval( context );
      
      if ( interval != Constants.SYNC_INTERVAL_MANUAL )
         SyncUtils.scheduleSyncAlarm( context, interval );
   }
   


   /**
    * Loads the start time from the Sync database table.
    */
   public final static void scheduleSyncAlarm( Context context, long interval )
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
      
      scheduleSyncAlarm( context, startUtc, interval );
   }
   


   public final static void scheduleSyncAlarm( Context context,
                                               long startUtc,
                                               long interval )
   {
      AlarmManager alarmManager = (AlarmManager) context.getSystemService( Context.ALARM_SERVICE );
      
      if ( alarmManager != null )
      {
         final long nowUtc = System.currentTimeMillis();
         
         if ( startUtc < nowUtc )
            startUtc = nowUtc;
         
         final PendingIntent syncIntent = Intents.createSyncAlarmIntent( context );
         
         alarmManager.setRepeating( AlarmManager.RTC_WAKEUP,
                                    startUtc,
                                    interval,
                                    syncIntent );
         
         Log.i( TAG, "Scheduled new sync alarm to go off @"
            + MolokoDateUtils.newTime().format2445() + ", repeating every "
            + DateUtils.formatElapsedTime( interval / 1000 ) );
      }
   }
   


   public final static void stopSyncAlarm( Context context )
   {
      AlarmManager alarmManager = (AlarmManager) context.getSystemService( Context.ALARM_SERVICE );
      
      if ( alarmManager != null )
      {
         alarmManager.cancel( Intents.createSyncAlarmIntent( context ) );
         
         Log.i( TAG, "Stopped sync alarm" );
      }
   }
   


   @Deprecated
   public final static void updateDate( ParcelableDate current,
                                        ParcelableDate update,
                                        Uri uri,
                                        String column,
                                        ContentProviderSyncOperation.Builder result )
   {
      if ( ( current == null && update != null )
         || ( current != null && update != null && current.getDate().getTime() != update.getDate()
                                                                                        .getTime() ) )
      {
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( column,
                                                         update.getDate()
                                                               .getTime() )
                                             .build() );
      }
      
      else if ( current != null && update == null )
      {
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( column, null )
                                             .build() );
      }
   }
   


   public final static < V > boolean hasChanged( V oldVal, V newVal )
   {
      return ( oldVal == null && newVal != null )
         || ( oldVal != null && ( newVal == null || !oldVal.equals( newVal ) ) );
   }
   


   public final static < T > void doPreSyncCheck( String thisId,
                                                  String otherId,
                                                  SyncProperties< ? extends T > properties )
   {
      if ( !thisId.equals( otherId ) )
         throw new IllegalArgumentException( "Other id " + otherId
            + " differs this id " + thisId );
      
      // if SERVER_ONLY or BOTH we need a time line
      if ( properties.timeline == null
         && properties.syncDirection != SyncDirection.LOCAL_ONLY )
         throw new NullPointerException( "timeLine is null" );
      
      // if we update the server we need the modifications
      if ( properties.modifications == null
         && properties.syncDirection != SyncDirection.LOCAL_ONLY )
         throw new NullPointerException( "modifications are null" );
   }
   
   
   public final static class SyncProperties< T extends ITwoWaySyncable< T >>
   {
      public final SyncDirection syncDirection;
      
      public final Date lastSyncDate;
      
      public final Date serverModDate;
      
      public final Date localModDate;
      
      public final Uri uri;
      
      public final ModificationList modifications;
      
      public final RtmTimeline timeline;
      
      public final DirectedSyncOperations operations = new DirectedSyncOperations();
      
      

      private SyncProperties( SyncDirection syncDirection, Date lastSyncDate,
         Date serverModDate, T localElement, Uri uri,
         ModificationList modifications, RtmTimeline timeline )
      {
         this.syncDirection = syncDirection;
         this.lastSyncDate = lastSyncDate;
         this.serverModDate = serverModDate;
         this.localModDate = localElement.getModifiedDate();
         this.uri = uri;
         this.modifications = modifications;
         this.timeline = timeline;
      }
      


      public final static < T extends ITwoWaySyncable< T >> SyncProperties< T > newInstance( SyncDirection syncDirection,
                                                                                             Date lastSyncDate,
                                                                                             T serverElement,
                                                                                             T localElement,
                                                                                             Uri uri,
                                                                                             ModificationList modifications,
                                                                                             RtmTimeline timeline )
      {
         return new SyncProperties< T >( syncDirection,
                                         lastSyncDate,
                                         serverElement.getModifiedDate(),
                                         localElement,
                                         uri,
                                         modifications,
                                         timeline );
      }
      


      public final static < T extends ITwoWaySyncable< T >> SyncProperties< T > newLocalOnlyInstance( Date lastSyncDate,
                                                                                                      T serverElement,
                                                                                                      T localElement,
                                                                                                      Uri uri )
      {
         return new SyncProperties< T >( SyncDirection.LOCAL_ONLY,
                                         lastSyncDate,
                                         serverElement.getModifiedDate(),
                                         localElement,
                                         uri,
                                         null,
                                         null );
      }
   }
   
   

   public final static < T extends ITwoWaySyncable< T >, V > IServerSyncable.SyncResultDirection syncValue( SyncProperties< T > properties,
                                                                                                            String columnName,
                                                                                                            V serverValue,
                                                                                                            V localValue,
                                                                                                            Class< V > valueClass )
   {
      IServerSyncable.SyncResultDirection syncDir = IServerSyncable.SyncResultDirection.NOTHING;
      
      // SERVER UPDATE: We put the local modification
      // to the server
      if ( properties.syncDirection == SyncDirection.SERVER_ONLY )
      {
         final Modification modification = properties.modifications.find( properties.uri,
                                                                          columnName );
         
         if ( modification != null )
            syncDir = IServerSyncable.SyncResultDirection.SERVER;
      }
      
      else if ( hasChanged( serverValue, localValue ) )
      {
         // LOCAL UPDATE: If the element has changed, take the server version
         if ( properties.syncDirection == SyncDirection.LOCAL_ONLY )
         {
            syncDir = IServerSyncable.SyncResultDirection.LOCAL;
         }
         else
         {
            final Modification modification = properties.modifications.find( properties.uri,
                                                                             columnName );
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
                  if ( properties.serverModDate.getTime() >= properties.localModDate.getTime() )
                     // LOCAL UPDATE: The server element was modified after the local value.
                     syncDir = IServerSyncable.SyncResultDirection.LOCAL;
                  else
                     // SERVER UPDATE: The local element was modified after the server element.
                     syncDir = IServerSyncable.SyncResultDirection.SERVER;
               }
               else
                  // SERVER UPDATE: The server value has not been changed since last sync,
                  // so use local modified value.
                  syncDir = IServerSyncable.SyncResultDirection.SERVER;
            }
            
            // LOCAL UPDATE: If the element has not locally changed, take the server version
            else
            {
               syncDir = IServerSyncable.SyncResultDirection.LOCAL;
            }
         }
         
         if ( syncDir == IServerSyncable.SyncResultDirection.LOCAL )
            properties.operations.add( ContentProviderOperation.newUpdate( properties.uri )
                                                               .withValue( columnName,
                                                                           serverValue )
                                                               .build(),
                                       Op.UPDATE );
      }
      
      return syncDir;
   }
   
   // public final static List< SyncTask > flatten( RtmTaskSeries taskSeries )
   // {
   // final List< RtmTask > rtmTasks = taskSeries.getTasks();
   // final List< SyncTask > tasks = new ArrayList< SyncTask >( rtmTasks.size() );
   //
   // for ( RtmTask rtmTask : rtmTasks )
   // {
   // tasks.add( new SyncTask( taskSeries, rtmTask ) );
   // }
   //
   // return tasks;
   // }
   //
   //
   //
   // public final static RtmTaskSeries toRtmTaskSeries( List< SyncTask > tasks )
   // {
   // if ( tasks.size() < 1 )
   // throw new IllegalArgumentException( "need at least on RtmTask for a RtmTaskSeries" );
   //
   // final List< RtmTask > rtmTasks = new ArrayList< RtmTask >( tasks.size() );
   //
   // for ( SyncTask syncTask : tasks )
   // {
   // rtmTasks.add( new RtmTask( syncTask.getId(),
   // syncTask.getTaskSeriesId(),
   // syncTask.getDueDate(),
   // syncTask.hasDueTime(),
   // syncTask.getAddedDate(),
   // syncTask.getCompletedDate(),
   // syncTask.getDeletedDate(),
   // syncTask.getPriority(),
   // syncTask.getPosponed(),
   // syncTask.getEstimate(),
   // syncTask.getEstimateMillis() ) );
   // }
   //
   // final SyncTask firstTask = tasks.get( 0 );
   //
   // return new RtmTaskSeries( firstTask.getTaskSeriesId(),
   // firstTask.getListId(),
   // firstTask.getCreatedDate(),
   // firstTask.getModifiedDate(),
   // firstTask.getName(),
   // firstTask.getSource(),
   // rtmTasks,
   // firstTask.getNotes(),
   // firstTask.getLocationId(),
   // firstTask.getUrl(),
   // firstTask.getRecurrence(),
   // firstTask.isEveryRecurrence(),
   // firstTask.getTags(),
   // firstTask.getParticipants() );
   // }
}
