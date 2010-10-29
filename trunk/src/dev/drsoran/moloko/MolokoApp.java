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

package dev.drsoran.moloko;

import android.accounts.Account;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SyncStatusObserver;
import android.os.Handler;
import dev.drsoran.moloko.notification.MolokoNotificationManager;
import dev.drsoran.moloko.receivers.TimeTickReceiver;
import dev.drsoran.moloko.service.sync.Constants;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.SyncUtils;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.provider.Rtm;


public class MolokoApp extends Application implements SyncStatusObserver
{
   private static Settings SETTINGS;
   
   private static MolokoNotificationManager MOLOKO_NOTIFICATION_MANAGER;
   
   private final TimeTickReceiver TIME_TICK_RECEIVER = new TimeTickReceiver();
   
   private final Handler handler = new Handler();
   
   private Object syncStatHandle = null;
   
   

   @Override
   public void onCreate()
   {
      super.onCreate();
      
      SETTINGS = new Settings( this, handler );
      
      MOLOKO_NOTIFICATION_MANAGER = new MolokoNotificationManager( this,
                                                                   handler );
      
      registerReceiver( TIME_TICK_RECEIVER,
                        new IntentFilter( Intent.ACTION_TIME_TICK ) );
      
      syncStatHandle = ContentResolver.addStatusChangeListener( Constants.SYNC_OBSERVER_TYPE_SETTINGS,
                                                                this );
      
      // TODO: Reinitialize the pattern language if system language changes.
      RecurrenceParsing.initPatternLanguage( getResources() );
   }
   


   @Override
   public void onTerminate()
   {
      super.onTerminate();
      
      MOLOKO_NOTIFICATION_MANAGER.shutdown();
      
      unregisterReceiver( TIME_TICK_RECEIVER );
      
      ContentResolver.removeStatusChangeListener( syncStatHandle );
   }
   


   public static MolokoApp get( Context context )
   {
      MolokoApp app = null;
      
      if ( context instanceof MolokoApp )
         app = (MolokoApp) context;
      else if ( context instanceof Activity )
         app = MolokoApp.get( context.getApplicationContext() );
      
      return app;
   }
   


   public void onStatusChanged( int which )
   {
      if ( which == Constants.SYNC_OBSERVER_TYPE_SETTINGS )
      {
         final Account account = AccountUtils.getRtmAccount( this );
         
         if ( account != null )
         {
            if ( ContentResolver.getSyncAutomatically( account, Rtm.AUTHORITY ) )
               SyncUtils.scheduleSyncAlarm( getApplicationContext() );
            else
               SyncUtils.stopSyncAlarm( getApplicationContext() );
         }
      }
   }
   


   public final static Settings getSettings()
   {
      return SETTINGS;
   }
   


   public final static MolokoNotificationManager getMolokoNotificationManager()
   {
      return MOLOKO_NOTIFICATION_MANAGER;
   }
}
