/* 
 *	Copyright (c) 2012 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.event;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.ContentObserver;
import android.os.Handler;
import android.preference.PreferenceManager;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.db.RtmSettingsTable;


class SettingsListener implements OnSharedPreferenceChangeListener
{
   private final Context context;
   
   private final Handler handler;
   
   private final IEventDrain eventDrain;
   
   private ContentObserver timeFormatChangedOberver;
   
   private ContentObserver dateFormatChangedOberver;
   
   private ContentObserver rtmSettingsChangedObserver;
   
   
   
   public SettingsListener( Context context, IEventDrain eventDrain,
      Handler handler )
   {
      this.context = context;
      this.handler = handler;
      this.eventDrain = eventDrain;
      
      registerPreferenceChangeListener();
      registerSystemSettingsChangedListener();
      registerRtmSettingsDatabaseChangedListener();
   }
   
   
   
   public void shutdown()
   {
      unregisterRtmSettingsDatabaseChangedListener();
      unregisterSystemSettingsChangedListener();
      unregisterPreferenceChangeListener();
   }
   
   
   
   @Override
   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      if ( key != null && sharedPreferences != null )
      {
         if ( key.equals( context.getString( R.string.key_task_sort ) ) )
         {
            onSettingChanged( IOnSettingsChangedListener.TASK_SORT );
         }
         else if ( key.equals( context.getString( R.string.key_notify_due_tasks ) ) )
         {
            onSettingChanged( IOnSettingsChangedListener.NOTIFY_DUE_TASKS );
         }
         else if ( key.equals( context.getString( R.string.key_notify_due_tasks_before ) ) )
         {
            onSettingChanged( IOnSettingsChangedListener.NOTIFY_DUE_TASKS_BEFORE_TIME );
         }
         else if ( key.equals( context.getString( R.string.key_notify_due_tasks_led ) )
            || key.equals( context.getString( R.string.key_notify_due_tasks_ringtone ) )
            || key.equals( context.getString( R.string.key_notify_due_tasks_vibrate ) ) )
         {
            onSettingChanged( IOnSettingsChangedListener.NOTIFY_DUE_TASKS_FEATURE );
         }
         else if ( key.equals( context.getString( R.string.key_notify_permanent_today_lists ) )
            || key.equals( context.getString( R.string.key_notify_permanent_tomorrow_lists ) )
            || key.equals( context.getString( R.string.key_notify_permanent_overdue_lists ) ) )
         {
            onSettingChanged( IOnSettingsChangedListener.NOTIFY_PERMANENT_TASKS );
         }
         else if ( key.equals( context.getString( R.string.key_def_list_local ) ) )
         {
            onSettingChanged( IOnSettingsChangedListener.RTM_DEFAULTLIST );
         }
         else if ( key.equals( context.getString( R.string.key_startup_view ) ) )
         {
            onSettingChanged( IOnSettingsChangedListener.STARTUP_VIEW );
         }
         else if ( key.equals( context.getString( R.string.key_sync_inverval ) ) )
         {
            onSettingChanged( IOnSettingsChangedListener.SYNC_INTERVAL );
         }
      }
   }
   
   
   
   private void registerPreferenceChangeListener()
   {
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      if ( prefs != null )
      {
         prefs.registerOnSharedPreferenceChangeListener( this );
      }
   }
   
   
   
   private void unregisterPreferenceChangeListener()
   {
      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      if ( prefs != null )
      {
         prefs.unregisterOnSharedPreferenceChangeListener( this );
      }
   }
   
   
   
   private void registerRtmSettingsDatabaseChangedListener()
   {
      rtmSettingsChangedObserver = new ContentObserver( handler )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            onSettingChanged( IOnSettingsChangedListener.RTM_SETTINGS_SYNCED );
         }
      };
      RtmSettingsTable.put( context,
                                                       rtmSettingsChangedObserver );
   }
   
   
   
   private void unregisterRtmSettingsDatabaseChangedListener()
   {
      if ( rtmSettingsChangedObserver != null )
      {
         RtmSettingsTable.unregisterContentObserver( context,
                                                            rtmSettingsChangedObserver );
         rtmSettingsChangedObserver = null;
      }
   }
   
   
   
   private void registerSystemSettingsChangedListener()
   {
      final ContentResolver contentResolver = context.getContentResolver();
      
      timeFormatChangedOberver = new ContentObserver( handler )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            onSettingChanged( IOnSettingsChangedListener.TIMEFORMAT );
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
            onSettingChanged( IOnSettingsChangedListener.DATEFORMAT );
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
   
   
   
   private void onSettingChanged( int what )
   {
      eventDrain.onSettingChanged( what );
   }
}
