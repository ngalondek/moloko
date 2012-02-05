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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.IOnTimeChangedListener;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Tasks;


class DueTasksNotifier extends AbstractNotificator
{
   private final DueTaskNotificationPresenter presenter;
   
   private boolean showDueTasks;
   
   private long remindBeforeMillis;
   
   
   
   public DueTasksNotifier( Context context )
   {
      super( context );
      
      presenter = new DueTaskNotificationPresenter( context );
      
      readPreferences();
      setNotificationFeatures();
      reCreateDueTaskNotifications();
   }
   
   
   
   @Override
   public void onTimeChanged( int which )
   {
      switch ( which )
      {
         case IOnTimeChangedListener.MIDNIGHT:
            reCreateDueTaskNotifications();
            break;
         
         case IOnTimeChangedListener.SYSTEM_TIME:
         case IOnTimeChangedListener.MINUTE_TICK:
            reEvaluateDueTaskNotifications();
            break;
         
         default :
            break;
      }
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      switch ( which )
      {
         case IOnSettingsChangedListener.TIMEFORMAT:
            // reEvaluateDueTaskNotifications( NOTIFICATION_DUE_UPD_TIME_FORMAT_CHANGED );
            break;
         
         default :
            break;
      }
   }
   
   
   
   @Override
   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
      if ( sharedPreferences != null && key != null )
      {
         if ( key.equals( context.getString( R.string.key_notify_due_tasks ) ) )
         {
            readPreferences();
            reCreateDueTaskNotifications();
         }
         else if ( key.equals( context.getString( R.string.key_notify_due_tasks_before ) ) )
         {
            readPreferences();
            reEvaluateDueTaskNotifications();
         }
         else if ( key.equals( context.getString( R.string.key_notify_due_tasks_ringtone ) )
            || key.equals( context.getString( R.string.key_notify_due_tasks_vibrate ) )
            || key.equals( context.getString( R.string.key_notify_due_tasks_led ) ) )
         {
            setNotificationFeatures();
         }
      }
   }
   
   
   
   @Override
   protected void onFinishedLoadingTasksToNotify( Cursor cursor )
   {
      if ( cursor != null && cursor.moveToFirst() )
      {
         reEvaluateDueTaskNotifications();
      }
      else
      {
         cancelDueTaskNotifications();
      }
   }
   
   
   
   @Override
   protected void onDatasetChanged()
   {
      reCreateDueTaskNotifications();
   }
   
   
   
   @Override
   public void shutdown()
   {
      super.shutdown();
      cancelDueTaskNotifications();
      releaseCurrentCursor();
   }
   
   
   
   private void reCreateDueTaskNotifications()
   {
      if ( !showDueTasks )
      {
         stopLoadingTasksToNotify();
         cancelDueTaskNotifications();
      }
      else
      {
         LoadDueTasksAsyncTask loader = new LoadDueTasksAsyncTask( context,
                                                                   getHandler(),
                                                                   remindBeforeMillis );
         startTasksLoader( loader );
      }
   }
   
   
   
   private void reEvaluateDueTaskNotifications()
   {
      if ( showDueTasks && hasTasks() )
      {
         final Cursor currentTasks = getCurrentTasksCursor();
         final int tasksToNotifyEndIndex = getTasksToNotifyExcusiveEndIndex( currentTasks );
         presenter.showNotificationsFor( currentTasks, tasksToNotifyEndIndex );
      }
   }
   
   
   
   private int getTasksToNotifyExcusiveEndIndex( Cursor currentTasks )
   {
      int endIndex = 0;
      
      if ( currentTasks.moveToFirst() )
      {
         final long nowMillis = System.currentTimeMillis();
         final int numTasks = currentTasks.getCount();
         
         boolean notifyTask = true;
         for ( ; endIndex < numTasks && notifyTask; currentTasks.moveToNext() )
         {
            final long taskDueTimeMillis = Queries.getOptLong( currentTasks,
                                                               TasksProviderPart.COL_INDICES.get( Tasks.DUE_DATE ) )
                                                  .longValue();
            
            notifyTask = ( taskDueTimeMillis - remindBeforeMillis ) <= nowMillis;
            if ( notifyTask )
            {
               ++endIndex;
            }
         }
      }
      
      return endIndex;
   }
   
   
   
   private void cancelDueTaskNotifications()
   {
      presenter.cancelNotifications();
   }
   
   
   
   private boolean hasTasks()
   {
      return getCurrentTasksCursor() != null
         && getCurrentTasksCursor().getCount() > 0;
   }
   
   
   
   private void readPreferences()
   {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         showDueTasks = prefs.getBoolean( context.getString( R.string.key_notify_due_tasks ),
                                          false );
         remindBeforeMillis = Long.parseLong( prefs.getString( context.getString( R.string.key_notify_due_tasks_before ),
                                                               context.getString( R.string.moloko_prefs_notification_tasks_w_due_before_default_value ) ) );
      }
   }
   
   
   
   private void setNotificationFeatures()
   {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         final Uri ringtone = readRingtoneUriFromPreferences( prefs );
         final boolean vibrate = prefs.getBoolean( context.getString( R.string.key_notify_due_tasks_vibrate ),
                                                   false );
         final boolean showLed = prefs.getBoolean( context.getString( R.string.key_notify_due_tasks_led ),
                                                   false );
         
         presenter.setNotificationFeatures( ringtone, showLed, vibrate );
      }
   }
   
   
   
   private Uri readRingtoneUriFromPreferences( SharedPreferences preferences )
   {
      final String ringtone = preferences.getString( context.getString( R.string.key_notify_due_tasks_ringtone ),
                                                     null );
      
      Uri ringtoneUri = null;
      if ( !TextUtils.isEmpty( ringtone ) )
      {
         ringtoneUri = Uri.parse( ringtone );
      }
      
      return ringtoneUri;
   }
}
