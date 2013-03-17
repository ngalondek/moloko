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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.app.notification;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.Loader;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.event.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.services.ISettingsService;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.moloko.event.IOnTimeChangedListener;
import dev.drsoran.moloko.loaders.AbstractLoader;
import dev.drsoran.provider.Rtm.Tasks;


class DueTasksNotifier extends AbstractNotifier
{
   private final IDueTaskNotificationPresenter presenter;
   
   private AbstractLoader< Cursor > tasksLoader;
   
   private int notificationEndIndex;
   
   
   
   public DueTasksNotifier( AppContext context )
   {
      super( context );
      
      presenter = NotificationPresenterFactory.createDueTaskNotificationPresenter( context );
      setNotificationFeatures();
      checkDueTaskNotificationsActiveState();
   }
   
   
   
   @Override
   public void onTimeChanged( int which )
   {
      if ( IsNotificationActive() )
      {
         switch ( which )
         {
            case IOnTimeChangedListener.MIDNIGHT:
               reCreateDueTaskNotifications();
               break;
            
            case IOnTimeChangedListener.SYSTEM_TIME:
            case IOnTimeChangedListener.MINUTE_TICK:
               if ( presenter.needsAllTimeChanges() )
               {
                  evaluateNotificationsRangeAndUpdateNotifications();
               }
               else
               {
                  updateNotificationsIfRangeChanged();
               }
               break;
            
            default :
               break;
         }
      }
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      if ( which == IOnSettingsChangedListener.NOTIFY_DUE_TASKS )
      {
         checkDueTaskNotificationsActiveState();
      }
      
      else if ( IsNotificationActive() )
      {
         switch ( which )
         {
            case IOnSettingsChangedListener.TIMEFORMAT:
            case IOnSettingsChangedListener.NOTIFY_DUE_TASKS_BEFORE_TIME:
               updateNotifications();
               break;
            
            case IOnSettingsChangedListener.NOTIFY_DUE_TASKS_FEATURE:
               setNotificationFeatures();
               break;
            
            default :
               break;
         }
      }
   }
   
   
   
   @Override
   public void onNotificationClicked( int notificationId, Intent onClickIntent )
   {
      if ( presenter.isHandlingNotification( notificationId ) )
      {
         presenter.handleNotificationClicked( notificationId );
      }
   }
   
   
   
   @Override
   public void onNotificationCleared( int notificationId, Intent onClearIntent )
   {
      if ( presenter.isHandlingNotification( notificationId ) )
      {
         presenter.handleNotificationCleared( notificationId );
      }
   }
   
   
   
   private void onFinishedLoadingTasksToNotify()
   {
      final Cursor cursor = getCurrentTasksCursor();
      
      if ( cursor != null && cursor.moveToFirst() )
      {
         evaluateNotificationsRangeAndUpdateNotifications();
      }
      else
      {
         cancelDueTaskNotifications();
      }
   }
   
   
   
   @Override
   public void shutdown()
   {
      shutdownLoader();
      cancelDueTaskNotifications();
      
      super.shutdown();
   }
   
   
   
   private void reCreateDueTaskNotifications()
   {
      loadDueTasks();
   }
   
   
   
   private void checkDueTaskNotificationsActiveState()
   {
      final boolean showDueTasks = IsNotificationActive();
      
      if ( !showDueTasks && tasksLoader != null )
      {
         shutdownLoader();
         cancelDueTaskNotifications();
      }
      else if ( showDueTasks && tasksLoader == null )
      {
         reCreateDueTaskNotifications();
      }
   }
   
   
   
   private boolean IsNotificationActive()
   {
      final boolean showDueTasks = getSettings().isNotifyingDueTasks();
      return showDueTasks;
   }
   
   
   
   private void loadDueTasks()
   {
      final long remindBeforeMillis = getSettings().getNotifyingDueTasksBeforeMs();
      tasksLoader = new HoleDayDueTasksLoader( context, remindBeforeMillis );
      
      loadTasksToNotify( HoleDayDueTasksLoader.ID, tasksLoader );
   }
   
   
   
   @Override
   public void onLoadComplete( Loader< Cursor > loader, Cursor data )
   {
      super.onLoadComplete( loader, data );
      
      getHandler().post( new Runnable()
      {
         @Override
         public void run()
         {
            onFinishedLoadingTasksToNotify();
         }
      } );
   }
   
   
   
   private void evaluateNotificationsRangeAndUpdateNotifications()
   {
      evaluateNotificationsRange();
      updateNotifications();
   }
   
   
   
   private void updateNotificationsIfRangeChanged()
   {
      final int tasksToNotifyEndIndex = notificationEndIndex;
      evaluateNotificationsRange();
      
      if ( notificationEndIndex != tasksToNotifyEndIndex )
      {
         notificationEndIndex = tasksToNotifyEndIndex;
         updateNotifications();
      }
   }
   
   
   
   private void updateNotifications()
   {
      final Cursor currentTasks = getCurrentTasksCursor();
      if ( currentTasks != null )
      {
         presenter.showNotificationsFor( currentTasks, notificationEndIndex );
      }
   }
   
   
   
   private void evaluateNotificationsRange()
   {
      final Cursor currentTasks = getCurrentTasksCursor();
      if ( currentTasks != null )
      {
         notificationEndIndex = getTasksToNotifyOpenRange( currentTasks );
      }
   }
   
   
   
   private int getTasksToNotifyOpenRange( Cursor currentTasks )
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
            final long taskDueTimeMillis = DbUtils.getOptLong( currentTasks,
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
   
   
   
   private void shutdownLoader()
   {
      if ( tasksLoader != null )
      {
         stopLoadingTasksToNotify();
         tasksLoader = null;
      }
   }
   
   
   
   private void cancelDueTaskNotifications()
   {
      presenter.cancelNotifications();
   }
   
   
   
   private void setNotificationFeatures()
   {
      final ISettingsService settings = getSettings();
      
      final Uri ringtone = settings.getNotifyingDueTasksRingtoneUri();
      final boolean vibrate = settings.isNotifyingDueTasksVibration();
      final boolean showLed = settings.isNotifyingDueTasksLed();
      
      presenter.setNotificationFeatures( ringtone, showLed, vibrate );
   }
}
