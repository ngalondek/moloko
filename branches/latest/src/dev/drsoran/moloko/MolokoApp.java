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

import java.lang.reflect.Method;

import android.accounts.Account;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SyncStatusObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import dev.drsoran.moloko.notification.MolokoNotificationManager;
import dev.drsoran.moloko.receivers.TimeTickReceiver;
import dev.drsoran.moloko.sync.Constants;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.ListenerList;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.ListenerList.MessgageObject;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.provider.Rtm;


public class MolokoApp extends org.acra.CrashReportingApplication implements
         SyncStatusObserver
{
   private final static String TAG = "Moloko."
      + MolokoApp.class.getSimpleName();
   
   private static Settings SETTINGS;
   
   private static MolokoNotificationManager MOLOKO_NOTIFICATION_MANAGER;
   
   private static TimeTickReceiver TIME_TICK_RECEIVER;
   
   private ListenerList< IOnTimeChangedListener > timeChangedListeners;
   
   private ListenerList< IOnSettingsChangedListener > settingsChangedListeners;
   
   private ListenerList< IOnBootCompletedListener > bootCompletedListeners;
   
   private Object syncStatHandle = null;
   
   

   @Override
   public void onCreate()
   {
      super.onCreate();
      
      // NOTE: Instantiate the ListenerLists at first cause other components may register in ctor.
      try
      {
         timeChangedListeners = new ListenerList< IOnTimeChangedListener >( findMethod( IOnTimeChangedListener.class,
                                                                                        "onTimeChanged" ) );
         settingsChangedListeners = new ListenerList< IOnSettingsChangedListener >( findMethod( IOnSettingsChangedListener.class,
                                                                                                "onSettingsChanged" ) );
         bootCompletedListeners = new ListenerList< IOnBootCompletedListener >( findMethod( IOnBootCompletedListener.class,
                                                                                            "onBootCompleted" ) );
      }
      catch ( SecurityException e )
      {
         Log.e( TAG, Strings.EMPTY_STRING, e );
         throw e;
      }
      catch ( NoSuchMethodException e )
      {
         Log.e( TAG, Strings.EMPTY_STRING, e );
         throw new IllegalStateException( e );
      }
      
      SETTINGS = new Settings( this );
      
      MOLOKO_NOTIFICATION_MANAGER = new MolokoNotificationManager( this );
      
      TIME_TICK_RECEIVER = new TimeTickReceiver();
      
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
         app = (MolokoApp) context.getApplicationContext();
      
      return app;
   }
   


   public static Handler getHandler( Context context )
   {
      final MolokoApp app = MolokoApp.get( context );
      if ( app != null )
         return app.getHandler();
      else
         return null;
   }
   


   public Handler getHandler()
   {
      return handler;
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
   


   public void registerOnSettingsChangedListener( int which,
                                                  IOnSettingsChangedListener listener )
   {
      if ( listener != null )
      {
         settingsChangedListeners.registerListener( which, listener );
      }
   }
   


   public void unregisterOnSettingsChangedListener( IOnSettingsChangedListener listener )
   {
      if ( listener != null )
      {
         settingsChangedListeners.removeListener( listener );
      }
   }
   


   public void registerOnTimeChangedListener( int which,
                                              IOnTimeChangedListener listener )
   {
      if ( listener != null )
      {
         timeChangedListeners.registerListener( which, listener );
      }
   }
   


   public void unregisterOnTimeChangedListener( IOnTimeChangedListener listener )
   {
      if ( listener != null )
      {
         timeChangedListeners.removeListener( listener );
      }
   }
   


   public void registerOnBootCompletedListener( IOnBootCompletedListener listener )
   {
      if ( listener != null )
      {
         bootCompletedListeners.registerListener( Integer.MAX_VALUE, listener );
      }
   }
   


   public void unregisterOnBootCompletedListener( IOnBootCompletedListener listener )
   {
      if ( listener != null )
      {
         bootCompletedListeners.removeListener( listener );
      }
   }
   


   public final static Settings getSettings()
   {
      return SETTINGS;
   }
   


   public final static void scheduleSync( Context context )
   {
      final Account account = SyncUtils.isReadyToSync( context );
      
      if ( account != null )
      {
         SyncUtils.scheduleSyncAlarm( context );
      }
   }
   


   public final static MolokoNotificationManager getMolokoNotificationManager()
   {
      return MOLOKO_NOTIFICATION_MANAGER;
   }
   


   @Override
   public String getFormId()
   {
      return "dDVHTDhVTmdYcXJ5cURtU2w0Q0EzNmc6MQ";
   }
   


   @Override
   public Bundle getCrashResources()
   {
      final Bundle result = new Bundle();
      result.putInt( RES_NOTIF_TICKER_TEXT,
                     R.string.acra_crash_notif_ticker_text );
      result.putInt( RES_NOTIF_TITLE, R.string.acra_crash_notif_title );
      result.putInt( RES_NOTIF_TEXT, R.string.acra_crash_notif_text );
      result.putInt( RES_DIALOG_TITLE, R.string.acra_crash_dialog_title );
      result.putInt( RES_DIALOG_TEXT, R.string.acra_crash_dialog_text );
      result.putInt( RES_DIALOG_COMMENT_PROMPT,
                     R.string.acra_crash_comment_prompt ); // optional. when defined, adds a user text field input
      // with this text resource as a label
      result.putInt( RES_DIALOG_OK_TOAST, R.string.acra_crash_dialog_ok_toast ); // optional. Displays a Toast when the
      // user
      // accepts to send a report ("Thank you !"
      // for example)
      return result;
   }
   


   private final static < T > Method findMethod( Class< T > cls, String name ) throws NoSuchMethodException
   {
      Method method = null;
      
      final Method[] methods = cls.getMethods();
      
      for ( int i = 0; i < methods.length && method == null; i++ )
      {
         if ( methods[ i ].getName().equals( name ) )
            method = methods[ i ];
      }
      
      if ( method == null )
         throw new NoSuchMethodException( "The class " + cls.getName()
            + " does not has a method " + name );
      
      return method;
   }
   
   private final Handler handler = new Handler()
   {
      @Override
      public void handleMessage( Message msg )
      {
         boolean handled = false;
         if ( msg.obj instanceof ListenerList.MessgageObject< ? > )
         {
            handled = true;
            
            final ListenerList.MessgageObject< ? > msgObj = (MessgageObject< ? >) msg.obj;
            
            if ( msgObj.type.getName()
                            .equals( IOnTimeChangedListener.class.getName() ) )
            {
               timeChangedListeners.notifyListeners( msg.what );
            }
            else if ( msgObj.type.getName()
                                 .equals( IOnSettingsChangedListener.class.getName() ) )
            {
               settingsChangedListeners.notifyListeners( msg.what,
                                                         msgObj.oldValue );
            }
            else if ( msgObj.type.getName()
                                 .equals( IOnBootCompletedListener.class.getName() ) )
            {
               bootCompletedListeners.notifyListeners();
               scheduleSync( MolokoApp.this );
            }
            else
               handled = false;
         }
         
         if ( !handled )
            super.handleMessage( msg );
      }
      
   };
   
}
