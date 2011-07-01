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

import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import dev.drsoran.moloko.content.RtmSettingsProviderPart;
import dev.drsoran.moloko.util.ListenerList;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.RtmSettings;


public class Settings implements OnSharedPreferenceChangeListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko." + Settings.class.getSimpleName();
   
   public final static int DATEFORMAT_EU = 0;
   
   public final static int DATEFORMAT_US = 1;
   
   public final static int TIMEFORMAT_12 = 0;
   
   public final static int TIMEFORMAT_24 = 1;
   
   public final static String NO_DEFAULT_LIST_ID = Strings.EMPTY_STRING;
   
   public final static int STARTUP_VIEW_DEFAULT_LIST = 1 << 0;
   
   public final static int STARTUP_VIEW_LISTS = 1 << 1;
   
   public final static int STARTUP_VIEW_HOME = 1 << 2;
   
   public final static int STARTUP_VIEW_DEFAULT = STARTUP_VIEW_HOME;
   
   public final static int TASK_SORT_PRIORITY = 1 << 0;
   
   public final static int TASK_SORT_DUE_DATE = 1 << 1;
   
   public final static int TASK_SORT_NAME = 1 << 2;
   
   public final static int TASK_SORT_DEFAULT = TASK_SORT_PRIORITY;
   
   private final Context context;
   
   private final Handler handler;
   
   private final SharedPreferences preferences;
   
   private RtmSettings rtmSettings;
   
   private TimeZone timeZone = TimeZone.getDefault();
   
   private int dateformat = DATEFORMAT_EU;
   
   private int timeformat = TIMEFORMAT_12;
   
   private String defaultListId = NO_DEFAULT_LIST_ID;
   
   private Locale locale = Locale.getDefault();
   
   private int startupView = STARTUP_VIEW_DEFAULT;
   
   private int taskSort = TASK_SORT_DEFAULT;
   
   

   public Settings( Context context )
   {
      this.context = context;
      this.handler = MolokoApp.get( context ).getHandler();
      
      preferences = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( preferences == null )
         throw new IllegalStateException( "SharedPreferences must not be null." );
      
      loadLocalSettings();
      
      loadRtmSettings( null );
      
      // Register after loadRtmSettings() otherwise we would
      // see our own change.
      preferences.registerOnSharedPreferenceChangeListener( this );
      
      // Watch for settings changes from background sync
      context.getContentResolver()
             .registerContentObserver( dev.drsoran.provider.Rtm.Settings.CONTENT_URI,
                                       true,
                                       new ContentObserver( this.handler )
                                       {
                                          // This should be called from the main thread,
                                          // see handler parameter.
                                          @Override
                                          public void onChange( boolean selfChange )
                                          {
                                             final HashMap< Integer, Object > oldValues = new HashMap< Integer, Object >();
                                             sendSettingChangedMessage( loadRtmSettings( oldValues ),
                                                                        oldValues );
                                          }
                                       } );
   }
   


   public TimeZone getTimezone()
   {
      return timeZone;
   }
   


   public int getDateformat()
   {
      return dateformat;
   }
   


   public int getTimeformat()
   {
      return timeformat;
   }
   


   public String getDefaultListId()
   {
      return defaultListId;
   }
   


   public void setDefaultListId( String id )
   {
      if ( TextUtils.isEmpty( id ) )
         id = NO_DEFAULT_LIST_ID;
      
      defaultListId = persistSetting( context.getString( R.string.key_def_list_local ),
                                      context.getString( R.string.key_def_list_sync_with_rtm ),
                                      id,
                                      false );
   }
   


   public Locale getLocale()
   {
      return locale;
   }
   


   public RtmSettings getRtmSettings()
   {
      return rtmSettings;
   }
   


   public int getStartupView()
   {
      return startupView;
   }
   


   public void setStartupView( int value )
   {
      startupView = Integer.valueOf( persistSetting( context.getString( R.string.key_startup_view ),
                                                     null,
                                                     String.valueOf( value ),
                                                     false ) );
   }
   


   public int getTaskSort()
   {
      return taskSort;
   }
   


   public void onSharedPreferenceChanged( SharedPreferences newValue, String key )
   {
      if ( key != null && newValue != null )
      {
         if ( key.equals( context.getString( R.string.key_timezone_local ) )
            || key.equals( context.getString( R.string.key_timezone_sync_with_rtm ) ) )
         {
            sendSettingChangedMessage( IOnSettingsChangedListener.RTM_TIMEZONE,
                                       setTimezone() );
         }
         else if ( key.equals( context.getString( R.string.key_dateformat_local ) )
            || key.equals( context.getString( R.string.key_dateformat_sync_with_rtm ) ) )
         {
            sendSettingChangedMessage( IOnSettingsChangedListener.RTM_DATEFORMAT,
                                       setDateformat() );
         }
         else if ( key.equals( context.getString( R.string.key_timeformat_local ) )
            || key.equals( context.getString( R.string.key_timeformat_sync_with_rtm ) ) )
         {
            sendSettingChangedMessage( IOnSettingsChangedListener.RTM_TIMEFORMAT,
                                       setTimeformat() );
         }
         else if ( key.equals( context.getString( R.string.key_def_list_local ) )
            || key.equals( context.getString( R.string.key_def_list_sync_with_rtm ) ) )
         {
            sendSettingChangedMessage( IOnSettingsChangedListener.RTM_DEFAULTLIST,
                                       setDefaultListId() );
         }
         else if ( key.equals( context.getString( R.string.key_lang_local ) )
            || key.equals( context.getString( R.string.key_lang_sync_with_rtm ) ) )
         {
            sendSettingChangedMessage( IOnSettingsChangedListener.RTM_LANGUAGE,
                                       setLocale() );
         }
         else if ( key.equals( context.getString( R.string.key_task_sort ) ) )
         {
            sendSettingChangedMessage( IOnSettingsChangedListener.TASK_SORT,
                                       setTaskSort() );
         }
      }
   }
   


   private TimeZone setTimezone()
   {
      TimeZone newTimeZone;
      
      final String keySync = context.getString( R.string.key_timezone_sync_with_rtm );
      final String key = context.getString( R.string.key_timezone_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         newTimeZone = null;
         
         if ( rtmSettings != null && rtmSettings.getTimezone() != null )
         {
            newTimeZone = TimeZone.getTimeZone( rtmSettings.getTimezone() );
         }
         else
         {
            newTimeZone = timeZone;
         }
      }
      else
      {
         newTimeZone = TimeZone.getTimeZone( preferences.getString( key,
                                                                    timeZone.getID() ) );
      }
      
      if ( !newTimeZone.getID().equals( timeZone ) )
      {
         final TimeZone oldTimeZone = timeZone;
         
         timeZone = newTimeZone;
         persistSetting( key, keySync, timeZone.getID(), useRtm );
         
         return oldTimeZone;
      }
      
      return null;
   }
   


   private Integer setDateformat()
   {
      int newDateformat;
      
      final String keySync = context.getString( R.string.key_dateformat_sync_with_rtm );
      final String key = context.getString( R.string.key_dateformat_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         if ( rtmSettings != null )
         {
            newDateformat = rtmSettings.getDateFormat();
         }
         else
         {
            final char[] order = DateFormat.getDateFormatOrder( context );
            
            newDateformat = ( order.length > 0 && order[ 0 ] == DateFormat.DATE )
                                                                                 ? DATEFORMAT_EU
                                                                                 : DATEFORMAT_US;
         }
      }
      else
      {
         newDateformat = Integer.valueOf( preferences.getString( key, "0" ) );
      }
      
      if ( newDateformat != dateformat )
      {
         final Integer oldDateFormat = dateformat;
         
         dateformat = newDateformat;
         persistSetting( key, keySync, String.valueOf( dateformat ), useRtm );
         
         return oldDateFormat;
      }
      
      return null;
   }
   


   private Integer setTimeformat()
   {
      int newTimeformat;
      
      final String keySync = context.getString( R.string.key_timeformat_sync_with_rtm );
      final String key = context.getString( R.string.key_timeformat_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         if ( rtmSettings != null )
         {
            newTimeformat = rtmSettings.getTimeFormat();
         }
         else
         {
            newTimeformat = ( DateFormat.is24HourFormat( context ) )
                                                                    ? TIMEFORMAT_24
                                                                    : TIMEFORMAT_12;
         }
      }
      else
      {
         newTimeformat = Integer.valueOf( preferences.getString( key, "0" ) );
      }
      
      if ( newTimeformat != timeformat )
      {
         final Integer oldTimeFormat = timeformat;
         
         timeformat = newTimeformat;
         persistSetting( key, keySync, String.valueOf( timeformat ), useRtm );
         
         return oldTimeFormat;
      }
      
      return null;
   }
   


   private String setDefaultListId()
   {
      String newListId;
      
      final String keySync = context.getString( R.string.key_def_list_sync_with_rtm );
      final String key = context.getString( R.string.key_def_list_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         if ( rtmSettings != null && rtmSettings.getDefaultListId() != null )
         {
            newListId = rtmSettings.getDefaultListId();
         }
         else
         {
            newListId = NO_DEFAULT_LIST_ID;
         }
      }
      else
      {
         newListId = preferences.getString( key, defaultListId );
      }
      
      if ( !defaultListId.equals( newListId ) )
      {
         final String oldDefListId = defaultListId;
         
         defaultListId = newListId;
         persistSetting( key, keySync, defaultListId, useRtm );
         
         return oldDefListId;
      }
      
      return null;
   }
   


   private Locale setLocale()
   {
      Locale newLocale;
      
      final String keySync = context.getString( R.string.key_lang_sync_with_rtm );
      final String key = context.getString( R.string.key_lang_local );
      final boolean useRtm = useRtmSetting( keySync );
      
      if ( useRtm )
      {
         newLocale = null;
         
         if ( rtmSettings != null && rtmSettings.getLanguage() != null )
         {
            newLocale = getLocaleFromLanguage( rtmSettings.getLanguage(),
                                               locale );
         }
         else
         {
            newLocale = locale;
         }
      }
      else
      {
         newLocale = getLocaleFromLanguage( preferences.getString( key,
                                                                   locale.getLanguage() ),
                                            locale );
      }
      
      if ( !newLocale.equals( locale ) )
      {
         final Locale oldLocale = locale;
         
         locale = newLocale;
         persistSetting( key, keySync, newLocale.getLanguage(), useRtm );
         
         return oldLocale;
      }
      
      return null;
   }
   


   private Integer setTaskSort()
   {
      final int newTaskSort = Integer.valueOf( preferences.getString( context.getString( R.string.key_task_sort ),
                                                                      String.valueOf( taskSort ) ) );
      
      if ( newTaskSort != taskSort )
      {
         final Integer oldTaskSort = taskSort;
         taskSort = newTaskSort;
         
         return oldTaskSort;
      }
      
      return null;
   }
   


   private boolean useRtmSetting( String key )
   {
      if ( !preferences.contains( key ) )
      {
         preferences.edit()
                    .putBoolean( context.getString( R.string.key_timezone_sync_with_rtm ),
                                 true )
                    .commit();
         return true;
      }
      else
      {
         return preferences.getBoolean( key, true );
      }
   }
   


   private String persistSetting( String key,
                                  String keySync,
                                  String value,
                                  boolean useRtm )
   {
      final Editor editor = preferences.edit();
      
      if ( !preferences.getString( key, Strings.EMPTY_STRING ).equals( value ) )
      {
         editor.putString( key, value ).commit();
      }
      
      if ( preferences.getBoolean( keySync, true ) != useRtm )
      {
         editor.putBoolean( keySync, useRtm ).commit();
      }
      
      return value;
   }
   


   private String loadLocalValue( String key, String keySync, String defValue )
   {
      String value;
      
      final Editor editor = preferences.edit();
      
      if ( !preferences.contains( key ) )
      {
         editor.putString( key, defValue ).commit();
         value = defValue;
      }
      else
      {
         value = preferences.getString( key, defValue );
      }
      
      if ( keySync != null && !preferences.contains( keySync ) )
      {
         editor.putBoolean( keySync, true ).commit();
      }
      
      return value;
   }
   


   private int loadLocalValue( String key, String keySync, int defValue )
   {
      return Integer.parseInt( loadLocalValue( key,
                                               keySync,
                                               String.valueOf( defValue ) ) );
   }
   


   private void loadLocalSettings()
   {
      timeZone = TimeZone.getTimeZone( loadLocalValue( context.getString( R.string.key_timezone_local ),
                                                       context.getString( R.string.key_timezone_sync_with_rtm ),
                                                       timeZone.getID() ) );
      dateformat = loadLocalValue( context.getString( R.string.key_dateformat_local ),
                                   context.getString( R.string.key_dateformat_sync_with_rtm ),
                                   dateformat );
      timeformat = loadLocalValue( context.getString( R.string.key_timeformat_local ),
                                   context.getString( R.string.key_timeformat_sync_with_rtm ),
                                   timeformat );
      defaultListId = loadLocalValue( context.getString( R.string.key_def_list_local ),
                                      context.getString( R.string.key_def_list_sync_with_rtm ),
                                      defaultListId );
      locale = getLocaleFromLanguage( loadLocalValue( context.getString( R.string.key_lang_local ),
                                                      context.getString( R.string.key_lang_sync_with_rtm ),
                                                      locale.getLanguage() ),
                                      locale );
      startupView = loadLocalValue( context.getString( R.string.key_startup_view ),
                                    null,
                                    startupView );
      taskSort = loadLocalValue( context.getString( R.string.key_task_sort ),
                                 null,
                                 taskSort );
   }
   


   private int loadRtmSettings( HashMap< Integer, Object > oldValues )
   {
      int settingsChanged = 0;
      
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( dev.drsoran.provider.Rtm.Settings.CONTENT_URI );
      
      if ( client != null )
      {
         rtmSettings = RtmSettingsProviderPart.getSettings( client );
         
         client.release();
         
         settingsChanged |= addSettingIfChanged( oldValues,
                                                 setTimezone(),
                                                 IOnSettingsChangedListener.RTM_TIMEZONE );
         settingsChanged |= addSettingIfChanged( oldValues,
                                                 setDateformat(),
                                                 IOnSettingsChangedListener.RTM_DATEFORMAT );
         settingsChanged |= addSettingIfChanged( oldValues,
                                                 setTimeformat(),
                                                 IOnSettingsChangedListener.RTM_TIMEFORMAT );
         settingsChanged |= addSettingIfChanged( oldValues,
                                                 setDefaultListId(),
                                                 IOnSettingsChangedListener.RTM_DEFAULTLIST );
         settingsChanged |= addSettingIfChanged( oldValues,
                                                 setLocale(),
                                                 IOnSettingsChangedListener.RTM_LANGUAGE );
      }
      
      return settingsChanged;
   }
   


   private void sendSettingChangedMessage( int what, Object oldValues )
   {
      if ( oldValues != null )
      {
         final Message msg = new Message();
         msg.what = what;
         msg.obj = new ListenerList.MessgageObject< IOnSettingsChangedListener >( IOnSettingsChangedListener.class,
                                                                                  oldValues );
         
         handler.sendMessage( msg );
      }
   }
   


   private final static int addSettingIfChanged( HashMap< Integer, Object > oldValues,
                                                 Object oldValue,
                                                 int setting )
   {
      if ( oldValue != null )
      {
         if ( oldValues != null )
            oldValues.put( setting, oldValue );
         return setting;
      }
      else
         return 0;
   }
   


   private static Locale getLocaleFromLanguage( String language, Locale defValue )
   {
      Locale locale = null;
      
      final Locale[] locales = Locale.getAvailableLocales();
      
      for ( int i = 0; i < locales.length && locale == null; i++ )
      {
         if ( locales[ i ].getLanguage().equals( language ) )
         {
            locale = locales[ i ];
         }
      }
      
      if ( locale == null )
         locale = defValue;
      
      return locale;
   }
}
