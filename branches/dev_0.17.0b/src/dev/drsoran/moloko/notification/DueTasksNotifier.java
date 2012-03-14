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
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.IOnTimeChangedListener;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Tasks;


class DueTasksNotifier extends AbstractNotifier
{
   private final DueTaskNotificationPresenter presenter;
   
   
   
   public DueTasksNotifier( Context context )
   {
      super( context );
      
      presenter = new DueTaskNotificationPresenter( context );
      
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
         
         case IOnSettingsChangedListener.NOTIFY_DUE_TASKS:
            reCreateDueTaskNotifications();
            break;
         
         case IOnSettingsChangedListener.NOTIFY_DUE_TASKS_BEFORE_TIME:
            reEvaluateDueTaskNotifications();
            break;
         
         case IOnSettingsChangedListener.NOTIFY_DUE_TASKS_FEATURE:
            setNotificationFeatures();
            break;
         
         default :
            break;
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
      final boolean showDueTasks = getSettings().isNotifyingDueTasks();
      
      if ( !showDueTasks )
      {
         stopLoadingTasksToNotify();
         cancelDueTaskNotifications();
      }
      else
      {
         final long remindBeforeMillis = getSettings().getNotifyingDueTasksBeforeMs();
         final LoadHoleDayDueTasksAsyncTask loader = new LoadHoleDayDueTasksAsyncTask( context,
                                                                                       getHandler(),
                                                                                       remindBeforeMillis );
         startTasksLoader( loader );
      }
   }
   
   
   
   private void reEvaluateDueTaskNotifications()
   {
      final boolean showDueTasks = getSettings().isNotifyingDueTasks();
      
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
         final long remindBeforeMillis = getSettings().getNotifyingDueTasksBeforeMs();
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
   
   
   
   private void setNotificationFeatures()
   {
      final Settings settings = getSettings();
      
      final Uri ringtone = settings.getNotifyingDueTasksRingtoneUri();
      final boolean vibrate = settings.isNotifyingDueTasksVibration();
      final boolean showLed = settings.isNotifyingDueTasksLed();
      
      presenter.setNotificationFeatures( ringtone, showLed, vibrate );
   }
}
