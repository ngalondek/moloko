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

package dev.drsoran.moloko.app.notification;

import java.util.List;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;


class MultiDueTaskNotificationPresenter extends
         AbstractDueTaskNotificationPresenter
{
   public MultiDueTaskNotificationPresenter( AppContext context )
   {
      super( context );
   }
   
   
   
   @Override
   public boolean needsAllTimeChanges()
   {
      return false;
   }
   
   
   
   @Override
   public boolean isHandlingNotification( int notificationId )
   {
      return containsNotifiedTasksId( String.valueOf( notificationId ) );
   }
   
   
   
   @Override
   public void handleNotificationClicked( int notificationId )
   {
      final String taskId = String.valueOf( notificationId );
      markNotificationInvisible( taskId );
      
      final Intent openTaskIntent = Intents.createOpenTaskIntentFromNotification( getContext(),
                                                                                  taskId );
      startActivity( openTaskIntent );
   }
   
   
   
   @Override
   public void handleNotificationCleared( int notificationId )
   {
      markAllNotificationInvisible();
   }
   
   
   
   @Override
   public void cancelNotifications()
   {
      for ( String taskId : getNotifiedTasksIds() )
      {
         cancelNotification( taskIdToNotificationId( taskId ) );
      }
      
      super.cancelNotifications();
   }
   
   
   
   @Override
   protected void onNotificationUpdate( Cursor tasksCursor,
                                        List< String > taskIdsToNotify,
                                        DueTaskNotification oldNotification,
                                        DueTaskNotification newNotification )
   {
      final boolean dueHasChanged = oldNotification.getDue() != newNotification.getDue();
      newNotification.setVisible( newNotification.isVisible() | dueHasChanged );
      
      if ( newNotification.isVisible() )
      {
         final String updatedNotificationId = newNotification.getTaskId();
         final int cursorIndexOfUpdate = taskIdsToNotify.indexOf( updatedNotificationId );
         launchOrUpdateNotification( taskIdToNotificationId( updatedNotificationId ),
                                     tasksCursor,
                                     cursorIndexOfUpdate,
                                     dueHasChanged );
      }
   }
   
   
   
   @Override
   protected void onNewNotification( Cursor tasksCursor,
                                     List< String > taskIdsToNotify,
                                     DueTaskNotification newNotification )
   {
      final String newNotificationTaskId = newNotification.getTaskId();
      final int cursorIndexOfNew = taskIdsToNotify.indexOf( newNotificationTaskId );
      launchOrUpdateNotification( taskIdToNotificationId( newNotificationTaskId ),
                                  tasksCursor,
                                  cursorIndexOfNew,
                                  true );
   }
   
   
   
   @Override
   protected void onNotificationRemoved( DueTaskNotification removedNotification )
   {
      if ( removedNotification.isVisible() )
      {
         cancelNotification( taskIdToNotificationId( removedNotification.getTaskId() ) );
      }
   }
   
   
   
   private void launchOrUpdateNotification( int id,
                                            Cursor tasksCursor,
                                            int taskIndexInCursor,
                                            boolean fullUpdate )
   {
      tasksCursor.moveToPosition( taskIndexInCursor );
      
      final String title = getNotificationTitle( tasksCursor );
      final String text = getNotificationText( tasksCursor );
      
      final String taskId = tasksCursor.getString( getColumnIndex( Tasks._ID ) );
      final PendingIntent onClickIntent = createOnClickIntent( taskId );
      final PendingIntent onDeleteIntent = createOnDeleteIntent( taskId );
      
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            tasksCursor );
      builder.setContentIntent( onClickIntent );
      builder.setDeleteIntent( onDeleteIntent );
      
      if ( fullUpdate )
      {
         final String ticker = getNotificationTicker( tasksCursor );
         builder.setTicker( ticker );
         
         useNotificationFeatures( builder );
      }
      
      getNotificationManager().notify( id, builder.build() );
   }
   
   
   
   private void cancelNotification( int intValue )
   {
      getNotificationManager().cancel( intValue );
   }
   
   
   
   private void markNotificationInvisible( String taskId )
   {
      getNotificationByTaskId( taskId ).setVisible( false );
   }
   
   
   
   private void markAllNotificationInvisible()
   {
      for ( DueTaskNotification notification : notifications )
      {
         markNotificationInvisible( notification.getTaskId() );
      }
   }
   
   
   
   private PendingIntent createOnClickIntent( String taskId )
   {
      return Intents.createDueTasksNotificationIntent( getContext(),
                                                       taskIdToNotificationId( taskId ) );
   }
   
   
   
   private PendingIntent createOnDeleteIntent( String taskId )
   {
      return Intents.createNotificationClearedIntent( getContext(),
                                                      taskIdToNotificationId( taskId ) );
   }
}
