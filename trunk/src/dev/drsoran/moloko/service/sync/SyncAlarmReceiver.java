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

import java.util.ArrayList;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import dev.drsoran.moloko.auth.prefs.AccountPreferencesActivity;
import dev.drsoran.moloko.content.SyncProviderPart;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.ConnectionChecker;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm;
import dev.drsoran.provider.Rtm.Sync;


public class SyncAlarmReceiver extends BroadcastReceiver
{
   private final static String TAG = SyncAlarmReceiver.class.getSimpleName();
   
   

   @Override
   public void onReceive( Context context, Intent intent )
   {
      final Account account = isReadyToSync( context );
      
      if ( account != null )
      {
         final Bundle bundle = new Bundle();
         bundle.putBoolean( Constants.SYNC_EXTRAS_SCHEDULED, true );
         
         ContentResolver.requestSync( account, Rtm.AUTHORITY, bundle );
      }
      else
      {
         stopSyncAlarm( context );
      }
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
   


   /**
    * Loads the start time from the Sync database table and the interval from the settings.
    */
   public final static void scheduleSyncAlarm( Context context )
   {
      final long interval = AccountPreferencesActivity.getSyncInterval( context );
      
      if ( interval != Constants.SYNC_INTERVAL_MANUAL )
         scheduleSyncAlarm( context, interval );
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
            + MolokoDateUtils.newDateUtc( startUtc ).getTime()
            + ", repeating every "
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
   
}
