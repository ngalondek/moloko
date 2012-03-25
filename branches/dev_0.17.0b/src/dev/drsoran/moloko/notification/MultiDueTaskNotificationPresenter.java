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

package dev.drsoran.moloko.notification;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.provider.Rtm.Tasks;


class MultiDueTaskNotificationPresenter extends
         AbstractDueTaskNotificationPresenter
{
   // Key: task ID, Value: true, if the notification is currently visible to the user
   private final Map< String, Boolean > notificationsMap = new HashMap< String, Boolean >();
   
   
   
   public MultiDueTaskNotificationPresenter( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   public boolean isHandlingNotification( int notificationId )
   {
      return notificationsMap.containsKey( String.valueOf( notificationId ) );
   }
   
   
   
   @Override
   public void handleNotificationClicked( int notificationId )
   {
      final String taskId = String.valueOf( notificationId );
      markNotificationInvisible( taskId );
      
      final Intent openTaskIntent = Intents.createOpenTaskIntent( getContext(),
                                                                  taskId );
      startActivity( openTaskIntent );
   }
   
   
   
   @Override
   public void handleNotificationCleared( int notificationId )
   {
      markNotificationInvisible( String.valueOf( notificationId ) );
   }
   
   
   
   @Override
   public void cancelNotifications()
   {
      for ( String taskId : notificationsMap.keySet() )
      {
         cancelNotification( taskIdToNotificationId( taskId ) );
      }
      
      notificationsMap.clear();
   }
   
   
   
   @Override
   protected void insertNewNotifications( Cursor tasksCursor,
                                          List< String > taskIdsToNotify,
                                          Collection< String > newNotificationTaskIds )
   {
      for ( String newNotificationTaskId : newNotificationTaskIds )
      {
         final int cursorIndexOfUpdate = taskIdsToNotify.indexOf( newNotificationTaskId );
         launchNotification( taskIdToNotificationId( newNotificationTaskId ),
                             tasksCursor,
                             cursorIndexOfUpdate );
         
         notificationsMap.put( newNotificationTaskId, Boolean.TRUE );
      }
   }
   
   
   
   @Override
   protected void updateExistingNotifications( Cursor tasksCursor,
                                               List< String > taskIdsToNotify,
                                               Collection< String > updatedNotificationIds )
   {
      for ( String updatedNotificationId : updatedNotificationIds )
      {
         final Boolean isVisible = notificationsMap.get( updatedNotificationId );
         if ( isVisible == Boolean.TRUE )
         {
            final int cursorIndexOfUpdate = taskIdsToNotify.indexOf( updatedNotificationId );
            launchNotification( taskIdToNotificationId( updatedNotificationId ),
                                tasksCursor,
                                cursorIndexOfUpdate );
         }
      }
   }
   
   
   
   @Override
   protected void cancelRemovedNotifications( Collection< String > removedNotificationTaskIds )
   {
      for ( String removedNotificationTaskId : removedNotificationTaskIds )
      {
         final Boolean isVisible = notificationsMap.remove( removedNotificationTaskId );
         if ( isVisible == Boolean.TRUE )
         {
            cancelNotification( taskIdToNotificationId( removedNotificationTaskId ) );
         }
      }
   }
   
   
   
   @Override
   protected Collection< String > getNotifiedTasksIds()
   {
      return notificationsMap.keySet();
   }
   
   
   
   private void launchNotification( int id,
                                    Cursor tasksCursor,
                                    int taskIndexInCursor )
   {
      tasksCursor.moveToPosition( taskIndexInCursor );
      
      final String title = getNotificationTitle( tasksCursor );
      final String text = getNotificationText( tasksCursor );
      final String ticker = getNotificationTicker( tasksCursor );
      
      final String taskId = tasksCursor.getString( getColumnIndex( Tasks._ID ) );
      final PendingIntent onClickIntent = createOnClickIntent( taskId );
      final PendingIntent onDeleteIntent = createOnDeleteIntent( taskId );
      
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            ticker,
                                                                            1 );
      builder.setContentIntent( onClickIntent );
      builder.setDeleteIntent( onDeleteIntent );
      
      getNotificationManager().notify( id, builder.build() );
   }
   
   
   
   private void cancelNotification( int intValue )
   {
      getNotificationManager().cancel( intValue );
   }
   
   
   
   private void markNotificationInvisible( String taskId )
   {
      notificationsMap.put( taskId, Boolean.FALSE );
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
