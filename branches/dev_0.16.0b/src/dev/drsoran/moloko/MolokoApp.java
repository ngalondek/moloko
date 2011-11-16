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

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import dev.drsoran.moloko.grammar.AndroidDateFormatContext;
import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.notification.MolokoNotificationManager;
import dev.drsoran.moloko.receivers.TimeTickReceiver;
import dev.drsoran.moloko.sync.periodic.IPeriodicSyncHandler;
import dev.drsoran.moloko.sync.periodic.PeriodicSyncHandlerFactory;
import dev.drsoran.moloko.util.ListenerList;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.ListenerList.MessgageObject;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;


@ReportsCrashes( formKey = "dDVHTDhVTmdYcXJ5cURtU2w0Q0EzNmc6MQ", mode = ReportingInteractionMode.NOTIFICATION, resNotifTickerText = R.string.acra_crash_notif_ticker_text, resNotifTitle = R.string.acra_crash_notif_title, resNotifText = R.string.acra_crash_notif_text, resNotifIcon = android.R.drawable.stat_notify_error, resDialogText = R.string.acra_crash_dialog_text, resDialogIcon = android.R.drawable.ic_dialog_info, resDialogTitle = R.string.acra_crash_dialog_title, resDialogCommentPrompt = R.string.acra_crash_comment_prompt, resDialogOkToast = R.string.acra_crash_dialog_ok_toast )
public class MolokoApp extends Application
{
   private final static String TAG = "Moloko."
      + MolokoApp.class.getSimpleName();
   
   private static Settings SETTINGS;
   
   private static MolokoNotificationManager MOLOKO_NOTIFICATION_MANAGER;
   
   private static TimeTickReceiver TIME_TICK_RECEIVER;
   
   private static IPeriodicSyncHandler PERIODIC_SNYC_HANDLER;
   
   private ListenerList< IOnTimeChangedListener > timeChangedListeners;
   
   private ListenerList< IOnSettingsChangedListener > settingsChangedListeners;
   
   private ListenerList< IOnBootCompletedListener > bootCompletedListeners;
   
   private ListenerList< IOnNetworkStatusChangedListener > networkStatusListeners;
   
   private IDateFormatContext dateFormatContext;
   
   

   @Override
   public void onCreate()
   {
      ACRA.init( this );
      
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
         networkStatusListeners = new ListenerList< IOnNetworkStatusChangedListener >( findMethod( IOnNetworkStatusChangedListener.class,
                                                                                                   "onNetworkStatusChanged" ) );
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
      
      SETTINGS = Settings.getInstance( getApplicationContext() );
      
      MOLOKO_NOTIFICATION_MANAGER = new MolokoNotificationManager( this );
      
      TIME_TICK_RECEIVER = new TimeTickReceiver();
      registerReceiver( TIME_TICK_RECEIVER,
                        new IntentFilter( Intent.ACTION_TIME_TICK ) );
      
      PERIODIC_SNYC_HANDLER = PeriodicSyncHandlerFactory.createPeriodicSyncHandler( getApplicationContext() );
      
      initParserLanguages();
      initDateFormatContext();
   }
   


   @Override
   public void onTerminate()
   {
      super.onTerminate();
      
      SETTINGS.release();
      
      MOLOKO_NOTIFICATION_MANAGER.shutdown();
      
      PERIODIC_SNYC_HANDLER.shutdown();
      
      unregisterReceiver( TIME_TICK_RECEIVER );
      
      deleteDateFormatContext();
   }
   


   @Override
   public void onConfigurationChanged( Configuration newConfig )
   {
      super.onConfigurationChanged( newConfig );
      
      initParserLanguages();
      recreateNotifications();
   }
   


   private void initParserLanguages()
   {
      RecurrenceParsing.initPatternLanguage( getResources() );
   }
   


   private void recreateNotifications()
   {
      MOLOKO_NOTIFICATION_MANAGER.onSystemLanguageChanged();
   }
   


   private void initDateFormatContext()
   {
      dateFormatContext = new AndroidDateFormatContext( getApplicationContext() );
      RtmDateTimeParsing.setDateFormatContext( dateFormatContext );
   }
   


   private void deleteDateFormatContext()
   {
      dateFormatContext = null;
      RtmDateTimeParsing.setDateFormatContext( dateFormatContext );
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
   


   public static IDateFormatContext getDateFormatContext( Context context )
   {
      return MolokoApp.get( context ).getDateFormatContext();
   }
   


   public IDateFormatContext getDateFormatContext()
   {
      return dateFormatContext;
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
   


   public void registerOnNetworkStatusChangedListener( IOnNetworkStatusChangedListener listener )
   {
      if ( listener != null )
      {
         networkStatusListeners.registerListener( Integer.MAX_VALUE, listener );
      }
   }
   


   public void unregisterOnNetworkStatusChangedListener( IOnNetworkStatusChangedListener listener )
   {
      if ( listener != null )
      {
         networkStatusListeners.removeListener( listener );
      }
   }
   


   public final static Settings getSettings()
   {
      return SETTINGS;
   }
   


   public final static String getRtmApiKey( Context context )
   {
      return context.getString( R.string.app_rtm_api_key );
   }
   


   public final static String getRtmSharedSecret( Context context )
   {
      return context.getString( R.string.app_rtm_shared_secret );
   }
   


   public final static void schedulePeriodicSync( long startUtc, long intervalMs )
   {
      PERIODIC_SNYC_HANDLER.setPeriodicSync( startUtc, intervalMs );
   }
   


   public final static IPeriodicSyncHandler getPeriodicSyncHander()
   {
      return PERIODIC_SNYC_HANDLER;
   }
   


   public final static void stopPeriodicSync()
   {
      PERIODIC_SNYC_HANDLER.resetPeriodicSync();
   }
   


   public final static MolokoNotificationManager getMolokoNotificationManager()
   {
      return MOLOKO_NOTIFICATION_MANAGER;
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
               settingsChangedListeners.notifyListeners( msg.what, msgObj.value );
            }
            else if ( msgObj.type.getName()
                                 .equals( IOnBootCompletedListener.class.getName() ) )
            {
               bootCompletedListeners.notifyListeners();
            }
            else if ( msgObj.type.getName()
                                 .equals( IOnNetworkStatusChangedListener.class.getName() ) )
            {
               networkStatusListeners.notifyListeners( msg.what, msgObj.value );
            }
            else
               handled = false;
         }
         
         if ( !handled )
            super.handleMessage( msg );
      }
   };
}
