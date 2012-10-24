/*
 * Copyright (c) 2012 Ronny Röhricht
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

import java.util.Locale;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import dev.drsoran.moloko.grammar.AndroidDateFormatContext;
import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.notification.PermanentNotificationType;
import dev.drsoran.moloko.sync.periodic.IPeriodicSyncHandler;
import dev.drsoran.moloko.sync.periodic.PeriodicSyncHandlerFactory;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;


@ReportsCrashes( formKey = "dEhwNThPMTJJYUhyOWRZWkhmYUkxSmc6MQ",
                 mode = ReportingInteractionMode.DIALOG,
                 resToastText = R.string.acra_crash_toast_text,
                 resDialogText = R.string.acra_crash_dialog_text,
                 resDialogIcon = R.drawable.ic_prefs_info,
                 resDialogTitle = R.string.acra_crash_dialog_title,
                 resDialogCommentPrompt = R.string.acra_crash_comment_prompt,
                 resDialogOkToast = R.string.acra_crash_dialog_ok_toast,
                 logcatFilterByPid = true )
public class MolokoApp extends Application implements
         IOnSettingsChangedListener
{
   public static MolokoLog Log;
   
   private final static TokenBasedHandler Handler = new TokenBasedHandler();
   
   private Settings settings;
   
   private NotifierContext notifierContext;
   
   private IPeriodicSyncHandler periodicSyncHandler;
   
   private IDateFormatContext dateFormatContext;
   
   
   
   @Override
   public void onCreate()
   {
      ACRA.init( this );
      Log = new MolokoLog( this )
      {
      };
      
      super.onCreate();
      
      createNotifierContext();
      createSettings();
      createPeriodicSyncHandler();
      
      registerNotificationSettingsListener();
      
      initParserLanguages();
      initDateFormatContext();
      
      startNotificationServiceIfNotificationsAreActive();
   }
   
   
   
   @Override
   public void onTerminate()
   {
      unregisterNotificationSettingsListener();
      deleteDateFormatContext();
      deleteNotifierContext();
      
      super.onTerminate();
   }
   
   
   
   @Override
   public void onConfigurationChanged( Configuration newConfig )
   {
      super.onConfigurationChanged( newConfig );
      initParserLanguages();
   }
   
   
   
   public static MolokoApp get( Context context )
   {
      return (MolokoApp) context.getApplicationContext();
   }
   
   
   
   public static NotifierContext getNotifierContext( Context context )
   {
      if ( context instanceof NotifierContext )
         return (NotifierContext) context;
      else
         return MolokoApp.get( context ).getNotifierContext();
   }
   
   
   
   public static Handler getHandler()
   {
      return Handler;
   }
   
   
   
   public static IHandlerToken acquireHandlerToken()
   {
      return Handler.aquireToken();
   }
   
   
   
   public NotifierContext getNotifierContext()
   {
      return notifierContext;
   }
   
   
   
   public static IDateFormatContext getDateFormatContext( Context context )
   {
      return MolokoApp.get( context ).getDateFormatContext();
   }
   
   
   
   public IDateFormatContext getDateFormatContext()
   {
      return dateFormatContext;
   }
   
   
   
   public Locale getActiveResourcesLocale()
   {
      final String resourcesLangString = getString( R.string.res_language );
      final String resourcesCountryString = getString( R.string.res_country );
      
      if ( resourcesCountryString.equalsIgnoreCase( "*" ) )
         return new Locale( resourcesLangString );
      else
         return new Locale( resourcesLangString, resourcesCountryString );
   }
   
   
   
   private void createNotifierContext()
   {
      notifierContext = new NotifierContext( this );
   }
   
   
   
   private void deleteNotifierContext()
   {
      if ( notifierContext != null )
      {
         notifierContext.shutdown();
         notifierContext = null;
      }
   }
   
   
   
   private void createSettings()
   {
      settings = new Settings( this );
   }
   
   
   
   private void createPeriodicSyncHandler()
   {
      periodicSyncHandler = PeriodicSyncHandlerFactory.createPeriodicSyncHandler( getApplicationContext() );
   }
   
   
   
   private void initParserLanguages()
   {
      RecurrenceParsing.initPatternLanguage( getResources() );
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
   
   
   
   private void registerNotificationSettingsListener()
   {
      notifierContext.registerOnSettingsChangedListener( IOnSettingsChangedListener.NOTIFY_DUE_TASKS
                                                            | IOnSettingsChangedListener.NOTIFY_PERMANENT_TASKS
                                                            | IOnSettingsChangedListener.NOTIFY_PERMANENT_OVERDUE_TASKS,
                                                         this );
   }
   
   
   
   private void unregisterNotificationSettingsListener()
   {
      notifierContext.unregisterOnSettingsChangedListener( this );
   }
   
   
   
   private void startNotificationService()
   {
      startService( Intents.createStartNotificationServiceIntent( this ) );
   }
   
   
   
   private void startNotificationServiceIfNotificationsAreActive()
   {
      if ( areNotificationsActivated() )
      {
         startNotificationService();
      }
   }
   
   
   
   private void stopNotificationService()
   {
      stopService( Intents.createStartNotificationServiceIntent( this ) );
   }
   
   
   
   public final Settings getSettings()
   {
      return settings;
   }
   
   
   
   public final static Settings getSettings( Context context )
   {
      return get( context ).getSettings();
   }
   
   
   
   public final static boolean isApiLevelSupported( int apiLevel )
   {
      return Build.VERSION.SDK_INT >= apiLevel;
   }
   
   
   
   public final static String getRtmApiKey( Context context )
   {
      return context.getString( R.string.app_rtm_api_key );
   }
   
   
   
   public final static String getRtmSharedSecret( Context context )
   {
      return context.getString( R.string.app_rtm_shared_secret );
   }
   
   
   
   public final void schedulePeriodicSync( long startUtc, long intervalMs )
   {
      periodicSyncHandler.setPeriodicSync( startUtc, intervalMs );
   }
   
   
   
   public final IPeriodicSyncHandler getPeriodicSyncHander()
   {
      return periodicSyncHandler;
   }
   
   
   
   public final void stopPeriodicSync()
   {
      periodicSyncHandler.resetPeriodicSync();
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      if ( areNotificationsActivated() )
      {
         startNotificationService();
      }
      else
      {
         stopNotificationService();
      }
   }
   
   
   
   public void onBootCompleted()
   {
      Handler.post( new Runnable()
      {
         @Override
         public void run()
         {
            startNotificationServiceIfNotificationsAreActive();
         }
      } );
   }
   
   
   
   private boolean areNotificationsActivated()
   {
      boolean activated = false;
      
      activated = settings.isNotifyingDueTasks();
      activated |= settings.isNotifyingPermanentOverdueTasks();
      if ( !activated )
      {
         activated = settings.getNotifyingPermanentTasksType() != PermanentNotificationType.OFF;
      }
      
      return activated;
   }
}
