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

import java.util.ArrayList;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import dev.drsoran.moloko.auth.prefs.SyncIntervalPreference;
import dev.drsoran.moloko.content.SyncProviderPart;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import dev.drsoran.moloko.service.sync.Constants;
import dev.drsoran.moloko.service.sync.operation.CompositeContentProviderSyncOperation;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Sync;


public final class SyncUtils
{
   private static final String TAG = "Moloko."
      + SyncUtils.class.getSimpleName();
   
   

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
      boolean sync = ConnectionChecker.isConnected( context );
      
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
         final ArrayList< Long > lastSync = SyncProviderPart.getLastInAndLastOut( client );
         
         if ( lastSync != null && lastSync.size() > 1 )
         {
            final long lastSyncIn = ( lastSync.get( 0 ) != null )
                                                                 ? lastSync.get( 0 )
                                                                 : Long.MAX_VALUE;
            final long lastSyncOut = ( lastSync.get( 1 ) != null )
                                                                  ? lastSync.get( 1 )
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
         
         // try
         // {
         // int i = 1 / 0;
         // }
         // catch ( Exception e )
         // {
         // StringWriter buffer = new StringWriter();
         // e.printStackTrace( new PrintWriter( buffer ) );
         //            
         // Log.i( TAG, buffer.toString() );
         // }
         
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
   


   public final static void updateDate( ParcelableDate current,
                                        ParcelableDate update,
                                        Uri uri,
                                        String column,
                                        CompositeContentProviderSyncOperation result )
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
   
}
