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

package dev.drsoran.moloko;

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
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.AppServicesContainer;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.services.ISettingsService;
import dev.drsoran.moloko.domain.DomainContext;
import dev.drsoran.moloko.domain.DomainServicesContainer;
import dev.drsoran.moloko.grammar.lang.RecurrenceSentenceLanguage;
import dev.drsoran.moloko.grammar.lang.XmlLanguageReader;
import dev.drsoran.moloko.grammar.recurrence.IRecurrenceSentenceLanguage;
import dev.drsoran.moloko.ui.UiContext;
import dev.drsoran.moloko.ui.UiServicesContainer;


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
   
   private AppContext appContext;
   
   private static ILog Log;
   
   
   
   @Override
   public void onCreate()
   {
      ACRA.init( this );
      super.onCreate();
      
      createSystemServices();
      createDomainServices();
      createUiServices();
      createAppServices();
      
      createAppContext();
      
      // TODO: Remove
      Log = appContext.Log();
      
      registerNotificationSettingsListener();
      startNotificationServiceIfNotificationsAreActive();
   }
   
   
   
   @Override
   public void onTerminate()
   {
      unregisterNotificationSettingsListener();
      deleteAppServices();
      deleteDomainServices();
      
      super.onTerminate();
   }
   
   
   
   @Override
   public void onConfigurationChanged( Configuration newConfig )
   {
      super.onConfigurationChanged( newConfig );
      updateParserLanguages();
   }
   
   
   
   @Deprecated
   public static ILog Log()
   {
      return Log;
   }
   
   
   
   public static SystemContext getSystemContext( Context context )
   {
      return MolokoApp.get( context ).appContext.asSystemContext();
   }
   
   
   
   public static DomainContext getDomainContext( Context context )
   {
      return MolokoApp.get( context ).appContext.asDomainContext();
   }
   
   
   
   public static UiContext getUiContext( Context context )
   {
      return MolokoApp.get( context ).appContext.asUiContext();
   }
   
   
   
   public static AppContext getAppContext( Context context )
   {
      return MolokoApp.get( context ).appContext;
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
   
   
   
   @Deprecated
   // TODO: This should not be at MolokoApp
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
   
   
   
   private void createAppContext()
   {
      final SystemContext systemContext = new SystemContext( getApplicationContext(),
                                                             systemServicesContainer );
      final DomainContext domainContext = new DomainContext( systemContext,
                                                             domainServicesContainer );
      final UiContext uiContext = new UiContext( domainContext,
                                                 uiServicesContainer );
      
      appContext = new AppContext( uiContext, appServicesContainer );
   }
   
   
   
   private void createUiServices()
   {
      uiServicesContainer = new UiServicesContainer( this );
   }
   
   
   
   private void createAppServices()
   {
      appServicesContainer = new AppServicesContainer( this,
                                                       systemServicesContainer.getHandler(),
                                                       systemServicesContainer.getHandlerTokenFactory(),
                                                       systemServicesContainer.Log() );
   }
   
   
   
   private void createDomainServices()
   {
      final IRecurrenceSentenceLanguage recurrenceSentenceLanguage = createRecurrenceSentenceLanguage();
      domainServicesContainer = new DomainServicesContainer( this,
                                                             systemServicesContainer.Log(),
                                                             uiServicesContainer.getDateFormatter(),
                                                             recurrenceSentenceLanguage );
   }
   
   
   
   private void deleteAppServices()
   {
      appServicesContainer.shutdown();
      appServicesContainer = null;
   }
   
   
   
   private void deleteDomainServices()
   {
      domainServicesContainer.shutdown();
      domainServicesContainer = null;
   }
   
   
   
   private void updateParserLanguages()
   {
      if ( needsParserLanguagesUpdate() )
      {
         final IRecurrenceSentenceLanguage recurrenceSentenceLanguage = createRecurrenceSentenceLanguage();
         
         domainServicesContainer.getParsingService()
                                .getRecurrenceParsing()
                                .setRecurrenceSentenceLanguage( recurrenceSentenceLanguage );
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
      final Locale currentSystemLocale = appServicesContainer.getSettings()
                                                             .getLocale();
      final Locale currentParserLocale = domainServicesContainer.getParsingService()
                                                                .getRecurrenceParsing()
                                                                .getRecurrenceSentenceLanguage()
                                                                .getLocale();
      
      return !currentSystemLocale.equals( currentParserLocale );
   }
   
   
   
   private IRecurrenceSentenceLanguage createRecurrenceSentenceLanguage()
   {
      final ISettingsService settingsService = appServicesContainer.getSettings();
      final RecurrenceSentenceLanguage sentenceLanguage = new RecurrenceSentenceLanguage( settingsService.getLocale(),
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
         throw new RuntimeException( "Unable to create recurrence sentence language for locale "
                                        + settingsService.getLocale(),
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
