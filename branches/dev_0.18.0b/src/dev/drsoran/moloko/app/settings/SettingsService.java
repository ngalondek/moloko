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

package dev.drsoran.moloko.app.settings;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.services.IAppEventService;
import dev.drsoran.moloko.app.services.ISettingsService;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.Settings;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.rtm.RtmConnectionProtocol;
import dev.drsoran.rtm.Strings;


public class SettingsService implements ISettingsService,
         IOnSettingsChangedListener
{
   private final Context context;
   
   private final SharedPreferences preferences;
   
   private IAppEventService eventService;
   
   private IContentRepository contentRepository;
   
   private Settings rtmSettings;
   
   private AsyncTask< IContentRepository, Void, Settings > queryRtmSettingsTask;
   
   private ContentObserver rtmSettingsChangedObserver;
   
   private final Handler handler;
   
   
   
   public SettingsService( Context context, Handler handler )
   {
      this.context = context;
      this.handler = handler;
      
      preferences = PreferenceManager.getDefaultSharedPreferences( context );
      if ( preferences == null )
      {
         throw new IllegalStateException( "SharedPreferences must not be null." );
      }
      
      setSettingsVersion();
   }
   
   
   
   public void setAppEvents( IAppEventService appEventService )
   {
      this.eventService = appEventService;
      registerSettingsListener();
   }
   
   
   
   public void setContentRepository( IContentRepository contentRepository )
   {
      this.contentRepository = contentRepository;
      
      registerRtmSettingsDatabaseChangedListener();
      queryRtmSettingsAsync();
   }
   
   
   
   public void shutdown()
   {
      if ( queryRtmSettingsTask != null )
      {
         queryRtmSettingsTask.cancel( true );
      }
      
      unregisterRtmSettingsDatabaseChangedListener();
      unregisterSettingsListener();
   }
   
   
   
   @Override
   public String getDateformat()
   {
      return android.provider.Settings.System.getString( context.getContentResolver(),
                                                         android.provider.Settings.System.DATE_FORMAT );
   }
   
   
   
   @Override
   public boolean is24hTimeformat()
   {
      return android.text.format.DateFormat.is24HourFormat( context );
   }
   
   
   
   @Override
   public long getDefaultListId()
   {
      return loadLong( context.getString( R.string.key_def_list_local ),
                       SettingConstants.NO_DEFAULT_LIST_ID );
   }
   
   
   
   @Override
   public void setDefaultListIdSyncWithRtm( boolean sync )
   {
      setSyncWithRtm( context.getString( R.string.key_def_list_sync_with_rtm ),
                      sync );
      
      if ( sync && rtmSettings != null )
      {
         long syncedDefList = rtmSettings.getDefaultListId();
         if ( syncedDefList == Constants.NO_ID )
         {
            syncedDefList = SettingConstants.NO_DEFAULT_LIST_ID;
         }
         
         storeStringIfChanged( context.getString( R.string.key_def_list_local ),
                               String.valueOf( syncedDefList ) );
      }
   }
   
   
   
   @Override
   public boolean isDefaultListIdInSyncWithRtm()
   {
      return isInSyncWithRtm( context.getString( R.string.key_def_list_sync_with_rtm ) );
   }
   
   
   
   @Override
   public void setDefaultListId( long id )
   {
      storeStringIfChanged( context.getString( R.string.key_def_list_local ),
                            String.valueOf( id ) );
      setSyncWithRtm( context.getString( R.string.key_def_list_sync_with_rtm ),
                      false );
   }
   
   
   
   private void checkDefaultListIdChangedBySync()
   {
      if ( rtmSettings != null
         && isInSyncWithRtm( context.getString( R.string.key_def_list_sync_with_rtm ) ) )
      {
         storeStringIfChanged( context.getString( R.string.key_def_list_local ),
                               String.valueOf( rtmSettings.getDefaultListId() ) );
      }
   }
   
   
   
   private void checkDefaultListUnset()
   {
      // If the default list start up has been selected and the default
      // list has been unset, we switch to the default start up view.
      if ( getStartupView() == SettingConstants.STARTUP_VIEW_DEFAULT_LIST
         && getDefaultListId() == SettingConstants.NO_DEFAULT_LIST_ID )
      {
         setStartupView( SettingConstants.STARTUP_VIEW_DEFAULT );
      }
   }
   
   
   
   @Override
   public Locale getLocale()
   {
      return Locale.getDefault();
   }
   
   
   
   @Override
   public Settings getRtmSettings()
   {
      return rtmSettings;
   }
   
   
   
   @Override
   public int getStartupView()
   {
      return loadInt( context.getString( R.string.key_startup_view ),
                      SettingConstants.STARTUP_VIEW_DEFAULT );
   }
   
   
   
   @Override
   public void setStartupView( int value )
   {
      storeInt( context.getString( R.string.key_startup_view ), value );
   }
   
   
   
   @Override
   public int getTaskSort()
   {
      return loadInt( context.getString( R.string.key_task_sort ),
                      SettingConstants.TASK_SORT_DEFAULT );
   }
   
   
   
   @Override
   public void setTaskSort( int taskSort )
   {
      storeInt( context.getString( R.string.key_task_sort ), taskSort );
   }
   
   
   
   @Override
   public boolean isSyncAtStartup()
   {
      return loadBool( context.getString( R.string.key_sync_at_startup ), true );
   }
   
   
   
   @Override
   public void setSyncAtStartUp( boolean value )
   {
      storeBool( context.getString( R.string.key_sync_at_startup ), value );
   }
   
   
   
   @Override
   public long getSyncInterval()
   {
      return loadLong( context.getString( R.string.key_sync_inverval ),
                       Long.valueOf( context.getString( R.string.def_sync_inverval ) ) );
   }
   
   
   
   @Override
   public boolean isManualSyncOnly()
   {
      return getSyncInterval() == -1;
   }
   
   
   
   @Override
   public boolean isNotifyingDueTasks()
   {
      return loadBool( context.getString( R.string.key_notify_due_tasks ),
                       false );
   }
   
   
   
   @Override
   public long getNotifyingDueTasksBeforeMs()
   {
      return loadLong( context.getString( R.string.key_notify_due_tasks_before ),
                       Long.valueOf( context.getString( R.string.def_notify_due_tasks_before ) ) );
   }
   
   
   
   @Override
   public Uri getNotifyingDueTasksRingtoneUri()
   {
      final String uriString = loadString( context.getString( R.string.key_notify_due_tasks_ringtone ),
                                           null );
      if ( !TextUtils.isEmpty( uriString ) )
         return Uri.parse( uriString );
      else
         return null;
   }
   
   
   
   @Override
   public boolean isNotifyingDueTasksVibration()
   {
      return loadBool( context.getString( R.string.key_notify_due_tasks_vibrate ),
                       false );
   }
   
   
   
   @Override
   public boolean isNotifyingDueTasksLed()
   {
      return loadBool( context.getString( R.string.key_notify_due_tasks_led ),
                       false );
   }
   
   
   
   /**
    * Value: Collection of list IDs to notify tasks for, or the constant {@link SettingsColumns.ALL_LISTS}.
    */
   @Override
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
   
   
   
   @Override
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
   
   
   
   @Override
   public boolean isUsingHttps()
   {
      return loadBool( context.getString( R.string.key_conn_use_https ), true );
   }
   
   
   
   @Override
   public RtmConnectionProtocol getRtmConnectionProtocol()
   {
      return isUsingHttps() ? RtmConnectionProtocol.https
                           : RtmConnectionProtocol.http;
   }
   
   
   
   @Override
   public boolean hasNotifiedTaskListMultiSelectionHint()
   {
      return loadBool( context.getString( R.string.key_notified_taskslist_multiselection ),
                       false );
   }
   
   
   
   @Override
   public void setTaskListMultiSelectionHintNotified()
   {
      storeBool( context.getString( R.string.key_notified_taskslist_multiselection ),
                 true );
   }
   
   
   
   private String loadString( String key, String defValue )
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
      
      if ( TextUtils.isEmpty( value ) )
      {
         return defValue;
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
   
   
   
   private int loadInt( String key, int defValue )
   {
      return Integer.parseInt( loadString( key, String.valueOf( defValue ) ) );
   }
   
   
   
   private void storeInt( String key, int value )
   {
      storeStringIfChanged( key, String.valueOf( value ) );
   }
   
   
   
   private long loadLong( String key, long defValue )
   {
      return Long.parseLong( loadString( key, String.valueOf( defValue ) ) );
   }
   
   
   
   private boolean loadBool( String key, boolean defValue )
   {
      return preferences.getBoolean( key, defValue );
   }
   
   
   
   private void storeBool( String key, boolean value )
   {
      preferences.edit().putBoolean( key, value ).commit();
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      switch ( which )
      {
         case IOnSettingsChangedListener.RTM_DEFAULTLIST:
            checkDefaultListUnset();
            break;
         
         default :
            throw new IllegalArgumentException( MessageFormat.format( "Unexpected setting changed notification {0}",
                                                                      which ) );
      }
   }
   
   
   
   private void registerSettingsListener()
   {
      eventService.registerOnSettingsChangedListener( IOnSettingsChangedListener.RTM_DEFAULTLIST,
                                                      this );
   }
   
   
   
   private void unregisterSettingsListener()
   {
      if ( eventService != null )
      {
         eventService.unregisterOnSettingsChangedListener( this );
      }
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
   
   
   
   private void registerRtmSettingsDatabaseChangedListener()
   {
      rtmSettingsChangedObserver = new ContentObserver( handler )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            queryRtmSettingsAsync();
            checkDefaultListIdChangedBySync();
         }
      };
      
      contentRepository.registerContentObserver( rtmSettingsChangedObserver,
                                                 ContentUris.RTM_SETTINGS_CONTENT_URI );
   }
   
   
   
   private void unregisterRtmSettingsDatabaseChangedListener()
   {
      if ( rtmSettingsChangedObserver != null )
      {
         contentRepository.unregisterContentObserver( rtmSettingsChangedObserver );
         rtmSettingsChangedObserver = null;
      }
   }
   
   
   
   private void queryRtmSettingsAsync()
   {
      if ( contentRepository != null )
      {
         queryRtmSettingsTask = new AsyncTask< IContentRepository, Void, Settings >()
         {
            @Override
            protected Settings doInBackground( IContentRepository... params )
            {
               final IContentRepository repo = params[ 0 ];
               return repo.getRtmSettings();
            }
            
            
            
            @Override
            protected void onPostExecute( Settings result )
            {
               queryRtmSettingsTask = null;
               rtmSettings = result;
            }
         }.execute( contentRepository );
      }
   }
}
