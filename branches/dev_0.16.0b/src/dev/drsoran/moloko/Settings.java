/*
 * Copyright (c) 2011 Ronny Röhricht
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;
import dev.drsoran.moloko.content.RtmSettingsProviderPart;
import dev.drsoran.moloko.util.ListenerList;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.RtmSettings;


public class Settings implements OnSharedPreferenceChangeListener
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko." + Settings.class.getSimpleName();
   
   public final static String NO_DEFAULT_LIST_ID = Strings.EMPTY_STRING;
   
   public final static int STARTUP_VIEW_DEFAULT_LIST = 1 << 0;
   
   public final static int STARTUP_VIEW_LISTS = 1 << 1;
   
   public final static int STARTUP_VIEW_HOME = 1 << 2;
   
   public final static int STARTUP_VIEW_DEFAULT = STARTUP_VIEW_HOME;
   
   public final static int TASK_SORT_PRIORITY = 1 << 0;
   
   public final static int TASK_SORT_DUE_DATE = 1 << 1;
   
   public final static int TASK_SORT_NAME = 1 << 2;
   
   public final static int TASK_SORT_DEFAULT = TASK_SORT_PRIORITY;
   
   // FIELDS
   private static Settings INSTANCE;
   
   private final Context context;
   
   private final Handler handler;
   
   private final SharedPreferences preferences;
   
   private ContentObserver timeFormatChangedOberver;
   
   private ContentObserver dateFormatChangedOberver;
   
   private RtmSettings rtmSettings;
   
   private String defaultListId = NO_DEFAULT_LIST_ID;
   
   private int startupView = STARTUP_VIEW_DEFAULT;
   
   private int taskSort = TASK_SORT_DEFAULT;
   
   

   private Settings( Context context )
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
                                       new ContentObserver( handler )
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
      
      registerSystemSettingsChangedListener();
   }
   


   public final static Settings getInstance( Context context )
   {
      if ( INSTANCE == null )
         INSTANCE = new Settings( context );
      
      return INSTANCE;
   }
   


   public void release()
   {
      unregisterSystemSettingsChangedListener();
   }
   


   public TimeZone getTimezone()
   {
      return TimeZone.getDefault();
   }
   


   public String getDateformat()
   {
      return android.provider.Settings.System.getString( context.getContentResolver(),
                                                         android.provider.Settings.System.DATE_FORMAT );
   }
   


   public boolean Is24hTimeformat()
   {
      try
      {
         return android.provider.Settings.System.getInt( context.getContentResolver(),
                                                         android.provider.Settings.System.TIME_12_24 ) == 24;
      }
      catch ( SettingNotFoundException e )
      {
         return false;
      }
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
      return Locale.getDefault();
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
         if ( key.equals( context.getString( R.string.key_def_list_local ) )
            || key.equals( context.getString( R.string.key_def_list_sync_with_rtm ) ) )
         {
            sendSettingChangedMessage( IOnSettingsChangedListener.RTM_DEFAULTLIST,
                                       setDefaultListId() );
         }
         else if ( key.equals( context.getString( R.string.key_task_sort ) ) )
         {
            sendSettingChangedMessage( IOnSettingsChangedListener.TASK_SORT,
                                       setTaskSort() );
         }
      }
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
         preferences.edit().putBoolean( key, true ).commit();
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
      defaultListId = loadLocalValue( context.getString( R.string.key_def_list_local ),
                                      context.getString( R.string.key_def_list_sync_with_rtm ),
                                      defaultListId );
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
                                                 setDefaultListId(),
                                                 IOnSettingsChangedListener.RTM_DEFAULTLIST );
      }
      
      return settingsChanged;
   }
   


   private void registerSystemSettingsChangedListener()
   {
      final ContentResolver contentResolver = context.getContentResolver();
      
      timeFormatChangedOberver = new ContentObserver( handler )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            sendSettingChangedMessage( IOnSettingsChangedListener.TIMEFORMAT,
                                       Collections.emptyMap() );
         }
      };
      
      contentResolver.registerContentObserver( android.provider.Settings.System.getUriFor( android.provider.Settings.System.TIME_12_24 ),
                                               false,
                                               timeFormatChangedOberver );
      
      dateFormatChangedOberver = new ContentObserver( handler )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            sendSettingChangedMessage( IOnSettingsChangedListener.DATEFORMAT,
                                       Collections.emptyMap() );
         }
      };
      
      contentResolver.registerContentObserver( android.provider.Settings.System.getUriFor( android.provider.Settings.System.DATE_FORMAT ),
                                               false,
                                               dateFormatChangedOberver );
   }
   


   private void unregisterSystemSettingsChangedListener()
   {
      final ContentResolver contentResolver = context.getContentResolver();
      
      if ( timeFormatChangedOberver != null )
      {
         contentResolver.unregisterContentObserver( timeFormatChangedOberver );
         timeFormatChangedOberver = null;
      }
      
      if ( dateFormatChangedOberver != null )
      {
         contentResolver.unregisterContentObserver( dateFormatChangedOberver );
         dateFormatChangedOberver = null;
      }
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
}
