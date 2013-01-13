/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.sync.periodic;

import android.accounts.Account;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.content.SyncStatusObserver;
import android.text.format.DateUtils;
import dev.drsoran.moloko.IOnNetworkStatusChangedListener;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.sync.Constants;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.provider.Rtm;


class AlarmManagerPeriodicSyncHandler extends AbstractPeriodicSyncHandler
         implements SyncStatusObserver, IOnNetworkStatusChangedListener
{
   private Object syncStatHandle = null;
   
   
   
   public AlarmManagerPeriodicSyncHandler( Context context )
   {
      super( context );
      
      MolokoApp.getNotifierContext( context )
               .registerOnNetworkStatusChangedListener( this );
      
      syncStatHandle = ContentResolver.addStatusChangeListener( Constants.SYNC_OBSERVER_TYPE_SETTINGS,
                                                                this );
   }
   
   
   
   @Override
   public void setPeriodicSync( long startUtc, long intervalMs )
   {
      final AlarmManager alarmManager = (AlarmManager) context.getSystemService( Context.ALARM_SERVICE );
      
      if ( alarmManager != null )
      {
         final long nowUtc = System.currentTimeMillis();
         
         if ( startUtc < nowUtc )
            startUtc = nowUtc;
         
         final PendingIntent syncIntent = Intents.createSyncAlarmIntent( context );
         
         alarmManager.setRepeating( AlarmManager.RTC_WAKEUP,
                                    startUtc,
                                    intervalMs,
                                    syncIntent );
         
         MolokoApp.Log.i( getClass(), "Scheduled new sync alarm to go off @"
            + MolokoDateUtils.newTime().format2445() + ", repeating every "
            + DateUtils.formatElapsedTime( intervalMs / 1000 ) );
      }
      
   }
   
   
   
   @Override
   public void delayNextSync( SyncResult syncResult, long seconds )
   {
   }
   
   
   
   @Override
   public void resetPeriodicSync()
   {
      final AlarmManager alarmManager = (AlarmManager) context.getSystemService( Context.ALARM_SERVICE );
      
      if ( alarmManager != null )
      {
         alarmManager.cancel( Intents.createSyncAlarmIntent( context ) );
         MolokoApp.Log.i( getClass(), "Stopped sync alarm" );
      }
   }
   
   
   
   public void onBootCompleted()
   {
      SyncUtils.schedulePeriodicSync( context );
   }
   
   
   
   @Override
   public void onStatusChanged( int which )
   {
      if ( which == Constants.SYNC_OBSERVER_TYPE_SETTINGS )
      {
         final Account account = AccountUtils.getRtmAccount( context );
         
         if ( account != null )
         {
            if ( ContentResolver.getSyncAutomatically( account, Rtm.AUTHORITY ) )
               SyncUtils.schedulePeriodicSync( context );
            else
               MolokoApp.get( context ).stopPeriodicSync();
         }
      }
   }
   
   
   
   @Override
   public void onNetworkStatusChanged( int which, Boolean value )
   {
      final boolean hasConnection = value.booleanValue();
      
      if ( hasConnection )
      {
         SyncUtils.schedulePeriodicSync( context );
      }
      else
      {
         if ( SyncUtils.isSyncing( context ) )
            SyncUtils.cancelSync( context );
         
         MolokoApp.get( context.getApplicationContext() ).stopPeriodicSync();
      }
   }
   
   
   
   @Override
   public void shutdown()
   {
      super.shutdown();
      
      MolokoApp.getNotifierContext( context )
               .unregisterOnNetworkStatusChangedListener( this );
      
      ContentResolver.removeStatusChangeListener( syncStatHandle );
      syncStatHandle = null;
   }
   
}
