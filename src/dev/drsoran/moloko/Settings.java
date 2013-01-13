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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import dev.drsoran.moloko.content.RtmSettingsProviderPart;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.RtmSettings;


public class Settings implements IOnSettingsChangedListener
{
   public final static String NO_DEFAULT_LIST_ID = Strings.EMPTY_STRING;
   
   public final static String ALL_LISTS = "All";
   
   public final static int STARTUP_VIEW_DEFAULT_LIST = 1 << 0;
   
   public final static int STARTUP_VIEW_LISTS = 1 << 1;
   
   public final static int STARTUP_VIEW_HOME = 1 << 2;
   
   public final static int STARTUP_VIEW_DEFAULT = STARTUP_VIEW_HOME;
   
   public final static int TASK_SORT_PRIORITY = 1 << 0;
   
   public final static int TASK_SORT_DUE_DATE = 1 << 1;
   
   public final static int TASK_SORT_NAME = 1 << 2;
   
   public final static int TASK_SORT_DEFAULT = TASK_SORT_PRIORITY;
   
   private final Context context;
   
   private final SharedPreferences preferences;
   
   private RtmSettings rtmSettings;
   
   
   
   public Settings( Context context )
   {
      this.context = context;
      
      preferences = PreferenceManager.getDefaultSharedPreferences( context );
      if ( preferences == null )
      {
         throw new IllegalStateException( "SharedPreferences must not be null." );
      }
      
      setSettingsVersion();
      loadRtmSettings();
      regsiterSettingsListener();
   }
   
   
   
   public void release()
   {
      unregsiterSettingsListener();
   }
   
   
   
   public String getDateformat()
   {
      return android.provider.Settings.System.getString( context.getContentResolver(),
                                                         android.provider.Settings.System.DATE_FORMAT );
   }
   
   
   
   public boolean is24hTimeformat()
   {
      return android.text.format.DateFormat.is24HourFormat( context );
   }
   
   
   
   public String getDefaultListId()
   {
      return loadString( context.getString( R.string.key_def_list_local ),
                         NO_DEFAULT_LIST_ID );
   }
   
   
   
   public void setDefaultListIdSyncWithRtm( boolean sync )
   {
      setSyncWithRtm( context.getString( R.string.key_def_list_sync_with_rtm ),
                      sync );
      if ( sync && rtmSettings != null )
      {
         String syncedDefList = rtmSettings.getDefaultListId();
         if ( syncedDefList == null )
         {
            syncedDefList = NO_DEFAULT_LIST_ID;
         }
         
         storeStringIfChanged( context.getString( R.string.key_def_list_local ),
                               syncedDefList );
      }
   }
   
   
   
   public boolean isDefaultListIdInSyncWithRtm()
   {
      return isInSyncWithRtm( context.getString( R.string.key_def_list_sync_with_rtm ) );
   }
   
   
   
   public void setDefaultListId( String id )
   {
      if ( TextUtils.isEmpty( id ) )
      {
         id = NO_DEFAULT_LIST_ID;
      }
      
      storeStringIfChanged( context.getString( R.string.key_def_list_local ),
                            id );
      setSyncWithRtm( context.getString( R.string.key_def_list_sync_with_rtm ),
                      false );
   }
   
   
   
   private void checkDefaultListIdChangedBySync()
   {
      if ( rtmSettings != null
         && isInSyncWithRtm( context.getString( R.string.key_def_list_sync_with_rtm ) ) )
      {
         storeStringIfChanged( context.getString( R.string.key_def_list_local ),
                               rtmSettings.getDefaultListId() );
      }
   }
   
   
   
   private void checkDefaultListUnset()
   {
      // If the default list start up has been selected and the default
      // list has been unset, we switch to the default start up view.
      if ( getStartupView() == STARTUP_VIEW_DEFAULT_LIST
         && getDefaultListId() == NO_DEFAULT_LIST_ID )
      {
         setStartupView( STARTUP_VIEW_DEFAULT );
      }
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
      return loadInt( context.getString( R.string.key_startup_view ),
                      STARTUP_VIEW_DEFAULT );
   }
   
   
   
   public void setStartupView( int value )
   {
      storeInt( context.getString( R.string.key_startup_view ), value );
   }
   
   
   
   public int getTaskSort()
   {
      return loadInt( context.getString( R.string.key_task_sort ),
                      TASK_SORT_DEFAULT );
   }
   
   
   
   public void setTaskSort( int taskSort )
   {
      storeInt( context.getString( R.string.key_task_sort ), taskSort );
   }
   
   
   
   public boolean isSyncAtStartup()
   {
      return loadBool( context.getString( R.string.key_sync_at_startup ), true );
   }
   
   
   
   public void setSyncAtStartUp( boolean value )
   {
      storeBool( context.getString( R.string.key_sync_at_startup ), value );
   }
   
   
   
   public long getSyncInterval()
   {
      return loadLong( context.getString( R.string.key_sync_inverval ),
                       Long.valueOf( context.getString( R.string.acc_pref_sync_interval_default_value ) ) );
   }
   
   
   
   public boolean isManualSyncOnly()
   {
      return getSyncInterval() == -1;
   }
   
   
   
   public boolean isNotifyingDueTasks()
   {
      return loadBool( context.getString( R.string.key_notify_due_tasks ),
                       false );
   }
   
   
   
   public long getNotifyingDueTasksBeforeMs()
   {
      return loadLong( context.getString( R.string.key_notify_due_tasks_before ),
                       Long.valueOf( context.getString( R.string.moloko_prefs_notification_tasks_w_due_before_default_value ) ) );
   }
   
   
   
   public Uri getNotifyingDueTasksRingtoneUri()
   {
      final String uriString = loadString( context.getString( R.string.key_notify_due_tasks_ringtone ),
                                           null );
      if ( !TextUtils.isEmpty( uriString ) )
         return Uri.parse( uriString );
      else
         return null;
   }
   
   
   
   public boolean isNotifyingDueTasksVibration()
   {
      return loadBool( context.getString( R.string.key_notify_due_tasks_vibrate ),
                       false );
   }
   
   
   
   public boolean isNotifyingDueTasksLed()
   {
      return loadBool( context.getString( R.string.key_notify_due_tasks_led ),
                       false );
   }
   
   
   
   /**
    * Value: Collection of list IDs to notify tasks for, or the constant {@link Settings.ALL_LISTS}.
    */
   public Map< PermanentNotificationType, Collection< String > > getNotifyingPermanentTaskLists()
   {
      final String todayTaskLists = loadString( context.getString( R.string.key_notify_permanent_today_lists ),
                                                Strings.EMPTY_STRING );
      final String tomorrowTaskLists = loadString( context.getString( R.string.key_notify_permanent_tomorrow_lists ),
                                                   Strings.EMPTY_STRING );
      final String overdueTaskLists = loadString( context.getString( R.string.key_notify_permanent_overdue_lists ),
                                                  Strings.EMPTY_STRING );
      
      final Map< PermanentNotificationType, Collection< String > > notifyingTaskListsMap = new HashMap< PermanentNotificationType, Collection< String > >( 3 );
      
      notifyingTaskListsMap.put( PermanentNotificationType.TODAY,
                                 Arrays.asList( TextUtils.split( todayTaskLists,
                                                                 "," ) ) );
      notifyingTaskListsMap.put( PermanentNotificationType.TOMORROW,
                                 Arrays.asList( TextUtils.split( tomorrowTaskLists,
                                                                 "," ) ) );
      notifyingTaskListsMap.put( PermanentNotificationType.OVERDUE,
                                 Arrays.asList( TextUtils.split( overdueTaskLists,
                                                                 "," ) ) );
      
      return notifyingTaskListsMap;
   }
   
   
   
   public boolean isNotifyingPermanentTasks()
   {
      for ( Collection< String > listIdsToNotify : getNotifyingPermanentTaskLists().values() )
      {
         if ( !listIdsToNotify.isEmpty() )
         {
            return true;
         }
      }
      
      return false;
   }
   
   
   
   public boolean isUsingHttps()
   {
      return loadBool( context.getString( R.string.key_conn_use_https ), true );
   }
   
   
   
   public String loadString( String key, String defValue )
   {
      String value;
      
      if ( !preferences.contains( key ) )
      {
         final Editor editor = preferences.edit();
         editor.putString( key, defValue ).commit();
         value = defValue;
      }
      else
      {
         value = preferences.getString( key, defValue );
      }
      
      return value;
   }
   
   
   
   private void storeString( String key, String value )
   {
      final Editor editor = preferences.edit();
      editor.putString( key, Strings.emptyIfNull( value ) ).commit();
   }
   
   
   
   private void storeStringIfChanged( String key, String value )
   {
      final String nonNullValue = Strings.emptyIfNull( value );
      if ( hasValueChanged( key, nonNullValue ) )
      {
         storeString( key, nonNullValue );
      }
   }
   
   
   
   public int loadInt( String key, int defValue )
   {
      return Integer.parseInt( loadString( key, String.valueOf( defValue ) ) );
   }
   
   
   
   public void storeInt( String key, int value )
   {
      storeStringIfChanged( key, String.valueOf( value ) );
   }
   
   
   
   public long loadLong( String key, long defValue )
   {
      return Long.parseLong( loadString( key, String.valueOf( defValue ) ) );
   }
   
   
   
   public void storeLong( String key, long value )
   {
      storeStringIfChanged( key, String.valueOf( value ) );
   }
   
   
   
   public boolean loadBool( String key, boolean defValue )
   {
      return preferences.getBoolean( key, defValue );
   }
   
   
   
   public void storeBool( String key, boolean value )
   {
      preferences.edit().putBoolean( key, value ).commit();
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      switch ( which )
      {
         case IOnSettingsChangedListener.RTM_SETTINGS_SYNCED:
            loadRtmSettings();
            checkDefaultListIdChangedBySync();
            break;
         
         case IOnSettingsChangedListener.RTM_DEFAULTLIST:
            checkDefaultListUnset();
            break;
         
         default :
            throw new IllegalArgumentException( "Unexpected setting changed notification "
               + which );
      }
   }
   
   
   
   private void regsiterSettingsListener()
   {
      MolokoApp.getNotifierContext( context )
               .registerOnSettingsChangedListener( IOnSettingsChangedListener.RTM_SETTINGS_SYNCED
                                                      | IOnSettingsChangedListener.RTM_DEFAULTLIST,
                                                   this );
   }
   
   
   
   private void unregsiterSettingsListener()
   {
      MolokoApp.getNotifierContext( context )
               .unregisterOnSettingsChangedListener( this );
   }
   
   
   
   private boolean hasValueChanged( String key, String value )
   {
      return !preferences.getString( key, Strings.EMPTY_STRING ).equals( value );
   }
   
   
   
   private boolean isInSyncWithRtm( String key )
   {
      if ( !preferences.contains( key ) )
      {
         setSyncWithRtm( key, false );
      }
      
      return preferences.getBoolean( key, false );
   }
   
   
   
   private void setSyncWithRtm( String key, boolean value )
   {
      preferences.edit().putBoolean( key, value ).commit();
   }
   
   
   
   private void setSettingsVersion()
   {
      preferences.edit()
                 .putString( context.getString( R.string.key_settings_version ),
                             MolokoApp.getVersionName( context ) )
                 .commit();
   }
   
   
   
   private void loadRtmSettings()
   {
      final ContentProviderClient client = context.getContentResolver()
                                                  .acquireContentProviderClient( dev.drsoran.provider.Rtm.Settings.CONTENT_URI );
      
      if ( client != null )
      {
         final RtmSettings settings = RtmSettingsProviderPart.getSettings( client );
         client.release();
         
         if ( settings != null )
         {
            rtmSettings = settings;
         }
      }
   }
}
