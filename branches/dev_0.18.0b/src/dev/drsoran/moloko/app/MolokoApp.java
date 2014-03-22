/*
 * Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Locale;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.XmlResourceParser;
import android.os.Build;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.SystemContext;
import dev.drsoran.moloko.SystemServicesContainer;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.services.AppServicesContainer;
import dev.drsoran.moloko.app.services.ISettingsService;
import dev.drsoran.moloko.app.settings.SettingsService;
import dev.drsoran.moloko.content.ContentAuthority;
import dev.drsoran.moloko.content.MolokoContentProvider;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.parsing.lang.XmlLanguageReader;
import dev.drsoran.moloko.domain.services.DomainServicesContainer;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.moloko.ui.services.MolokoDateFormatterService;
import dev.drsoran.moloko.ui.services.UiServicesContainer;
import dev.drsoran.rtm.parsing.DefaultRtmCalenderProvider;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;
import dev.drsoran.rtm.parsing.lang.IRecurrenceSentenceLanguage;
import dev.drsoran.rtm.parsing.lang.RecurrenceSentenceLanguage;


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
   private SystemServicesContainer systemServicesContainer;
   
   private AppServicesContainer appServicesContainer;
   
   private DomainServicesContainer domainServicesContainer;
   
   private UiServicesContainer uiServicesContainer;
   
   private SystemContext systemContext;
   
   private DomainContext domainContext;
   
   private UiContext uiContext;
   
   private AppContext appContext;
   
   private static boolean isDebug;
   
   
   
   @Override
   public void onCreate()
   {
      isDebug = getResources().getBoolean( R.bool.env_debug );
      
      if ( !isDebug )
      {
         ACRA.init( this );
      }
      
      super.onCreate();
      
      createSystemServices();
      createSystemContext();
      
      final IRtmCalendarProvider calendarProvider = new DefaultRtmCalenderProvider();
      
      initContentProvider( calendarProvider );
      
      SettingsService settingsService = new SettingsService( this,
                                                             systemServicesContainer.getHandler() );
      final MolokoDateFormatterService dateFormatterService = new MolokoDateFormatterService( this );
      
      createDomainServices( calendarProvider,
                            dateFormatterService,
                            settingsService.getLocale() );
      createDomainContext();
      
      createUiServices( dateFormatterService );
      createUiContext();
      
      createAppServices( settingsService );
      createAppContext();
      
      registerNotificationSettingsListener();
      startNotificationServiceIfNotificationsAreActive();
   }
   
   
   
   @Override
   public void onTerminate()
   {
      unregisterNotificationSettingsListener();
      
      deleteAppContext();
      deleteUiContext();
      deleteDomainContext();
      deleteSystemContext();
      
      super.onTerminate();
   }
   
   
   
   @Override
   public void onConfigurationChanged( Configuration newConfig )
   {
      super.onConfigurationChanged( newConfig );
      updateParserLanguages();
   }
   
   
   
   public static boolean DEBUG()
   {
      return isDebug;
   }
   
   
   
   public static MolokoApp get( Context context )
   {
      return (MolokoApp) context.getApplicationContext();
   }
   
   
   
   public static int getVersionCode( Context context )
   {
      try
      {
         return context.getPackageManager()
                       .getPackageInfo( context.getPackageName(), 0 ).versionCode;
      }
      catch ( NameNotFoundException e )
      {
         throw new RuntimeException( e );
      }
   }
   
   
   
   public static String getVersionName( Context context )
   {
      try
      {
         return context.getPackageManager()
                       .getPackageInfo( context.getPackageName(), 0 ).versionName;
      }
      catch ( NameNotFoundException e )
      {
         throw new RuntimeException( e );
      }
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
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      if ( which == IOnSettingsChangedListener.TIMEFORMAT )
      {
         uiServicesContainer.set24hFormat( appServicesContainer.getSettings()
                                                               .is24hTimeformat() );
      }
      else
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
   }
   
   
   
   public void onBootCompleted()
   {
      systemServicesContainer.getHandler().post( new Runnable()
      {
         @Override
         public void run()
         {
            startNotificationServiceIfNotificationsAreActive();
            schedulePeriodicSync();
         }
      } );
   }
   
   
   
   private void createSystemServices()
   {
      systemServicesContainer = new SystemServicesContainer( getApplicationContext() );
   }
   
   
   
   private void createSystemContext()
   {
      systemContext = new SystemContext( getApplicationContext(),
                                         systemServicesContainer );
   }
   
   
   
   public static SystemContext getSystemContext( Context context )
   {
      return MolokoApp.get( context ).systemContext;
   }
   
   
   
   private void deleteSystemContext()
   {
      if ( systemServicesContainer != null )
      {
         systemServicesContainer.shutdown();
         systemServicesContainer = null;
      }
      
      systemContext = null;
   }
   
   
   
   private void createDomainServices( IRtmCalendarProvider calendarProvider,
                                      IDateFormatterService dateFormatterService,
                                      Locale parserLocale )
   {
      final IRecurrenceSentenceLanguage recurrenceSentenceLanguage = createRecurrenceSentenceLanguage( parserLocale );
      domainServicesContainer = new DomainServicesContainer( this,
                                                             systemServicesContainer.Log(),
                                                             calendarProvider,
                                                             dateFormatterService,
                                                             recurrenceSentenceLanguage );
   }
   
   
   
   private void createDomainContext()
   {
      domainContext = new DomainContext( systemContext, domainServicesContainer );
   }
   
   
   
   public static DomainContext getDomainContext( Context context )
   {
      return MolokoApp.get( context ).domainContext;
   }
   
   
   
   private void deleteDomainContext()
   {
      if ( domainServicesContainer != null )
      {
         domainServicesContainer = null;
      }
      
      domainContext = null;
   }
   
   
   
   private void createUiServices( MolokoDateFormatterService dateFormatterService )
   {
      uiServicesContainer = new UiServicesContainer( this,
                                                     dateFormatterService,
                                                     domainServicesContainer.getCalendarProvider() );
   }
   
   
   
   private void createUiContext()
   {
      uiContext = new UiContext( domainContext, uiServicesContainer );
   }
   
   
   
   public static UiContext getUiContext( Context context )
   {
      return MolokoApp.get( context ).uiContext;
   }
   
   
   
   private void deleteUiContext()
   {
      if ( uiServicesContainer != null )
      {
         uiServicesContainer = null;
      }
      
      uiContext = null;
   }
   
   
   
   private void createAppServices( SettingsService settingsService )
   {
      settingsService.setContentRepository( domainServicesContainer.getContentRepository() );
      
      appServicesContainer = new AppServicesContainer( domainContext,
                                                       systemServicesContainer.getHandler(),
                                                       systemServicesContainer.getHandlerTokenFactory(),
                                                       systemServicesContainer.getConnectionService(),
                                                       settingsService,
                                                       systemServicesContainer.Log() );
   }
   
   
   
   private void createAppContext()
   {
      appContext = new AppContext( uiContext, appServicesContainer );
   }
   
   
   
   public static AppContext getAppContext( Context context )
   {
      return MolokoApp.get( context ).appContext;
   }
   
   
   
   private void deleteAppContext()
   {
      if ( appServicesContainer != null )
      {
         appServicesContainer.shutdown();
         appServicesContainer = null;
      }
      
      appContext = null;
   }
   
   
   
   private void updateParserLanguages()
   {
      if ( needsParserLanguagesUpdate() )
      {
         final IRecurrenceSentenceLanguage recurrenceSentenceLanguage = createRecurrenceSentenceLanguage( appServicesContainer.getSettings()
                                                                                                                              .getLocale() );
         domainServicesContainer.updateParserLanguages( recurrenceSentenceLanguage );
      }
   }
   
   
   
   private void registerNotificationSettingsListener()
   {
      appServicesContainer.getAppEvents()
                          .registerOnSettingsChangedListener( IOnSettingsChangedListener.NOTIFY_DUE_TASKS
                                                                 | IOnSettingsChangedListener.NOTIFY_PERMANENT_TASKS
                                                                 | IOnSettingsChangedListener.TIMEFORMAT,
                                                              this );
   }
   
   
   
   private void unregisterNotificationSettingsListener()
   {
      appServicesContainer.getAppEvents()
                          .unregisterOnSettingsChangedListener( this );
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
   
   
   
   private boolean areNotificationsActivated()
   {
      boolean activated = false;
      
      ISettingsService settings = appServicesContainer.getSettings();
      
      activated = settings.isNotifyingDueTasks();
      activated |= settings.isNotifyingPermanentTasks();
      
      return activated;
   }
   
   
   
   private void schedulePeriodicSync()
   {
      appServicesContainer.getSyncService().schedulePeriodicSyncFromSettings();
   }
   
   
   
   private boolean needsParserLanguagesUpdate()
   {
      if ( appServicesContainer != null )
      {
         return domainServicesContainer.needsParserLanguageUpdate( appServicesContainer.getSettings()
                                                                                       .getLocale() );
      }
      
      return false;
   }
   
   
   
   private void initContentProvider( IRtmCalendarProvider calendarProvider )
   {
      final MolokoContentProvider molokoContentProvider = (MolokoContentProvider) getContentResolver().acquireContentProviderClient( ContentAuthority.RTM )
                                                                                                      .getLocalContentProvider();
      molokoContentProvider.init( systemServicesContainer.Log(),
                                  calendarProvider );
   }
   
   
   
   // TODO: Is not the DomainServicesContainer responsible to create the language?
   private IRecurrenceSentenceLanguage createRecurrenceSentenceLanguage( Locale locale )
   {
      final RecurrenceSentenceLanguage sentenceLanguage = new RecurrenceSentenceLanguage( locale,
                                                                                          systemServicesContainer.Log() );
      final XmlResourceParser xmlParser = getResources().getXml( R.xml.parser_lang_reccur_pattern );
      
      XmlLanguageReader languageReader = null;
      try
      {
         languageReader = new XmlLanguageReader( sentenceLanguage, xmlParser );
         languageReader.read();
      }
      catch ( ParseException e )
      {
         throw new RuntimeException( MessageFormat.format( "Unable to create recurrence sentence language for locale {0}",
                                                           locale ),
                                     e );
      }
      finally
      {
         if ( languageReader != null )
         {
            languageReader.close();
         }
      }
      
      return sentenceLanguage;
   }
}
