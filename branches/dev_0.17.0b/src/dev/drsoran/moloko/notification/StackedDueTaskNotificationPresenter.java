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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.SqlSelectionFilter;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Tasks;


class StackedDueTaskNotificationPresenter extends
         AbstractDueTaskNotificationPresenter
{
   private final static int ID = R.id.notification_due_tasks_stacked;
   
   // Key: Task ID, Value: true, if the notification should be part of the stack.
   private final Map< String, Boolean > notificationsMap = new TreeMap< String, Boolean >();
   
   private boolean isNotificationShown;
   
   
   
   public StackedDueTaskNotificationPresenter( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   public void showNotificationsFor( Cursor tasksCursor, int endIndex )
   {
      super.showNotificationsFor( tasksCursor, endIndex );
      
   }
   
   
   
   @Override
   public boolean isHandlingNotification( int notificationId )
   {
      return notificationId == ID;
   }
   
   
   
   @Override
   public void handleNotificationClicked( int notificationId )
   {
      final Intent openTasksIntent = createOpenTasksIntent();
      startActivity( openTasksIntent );
   }
   
   
   
   @Override
   public void handleNotificationCleared( int notificationId )
   {
   }
   
   
   
   @Override
   public void cancelNotifications()
   {
      if ( isNotificationShown )
      {
         getNotificationManager().cancel( ID );
         isNotificationShown = false;
      }
   }
   
   
   
   @Override
   protected void updateExistingNotifications( Cursor tasksCursor,
                                               List< String > taskIdsToNotify,
                                               Collection< String > updatedValues )
   {
      for ( String updatedNotificationTaskId : updatedValues )
      {
         notificationsMap.get( updatedNotificationTaskId );
      }
   }
   
   
   
   @Override
   protected void insertNewNotifications( Cursor tasksCursor,
                                          List< String > taskIdsToNotify,
                                          Collection< String > newValues )
   {
      for ( String newNotificationTaskId : newValues )
      {
         notificationsMap.put( newNotificationTaskId, Boolean.TRUE );
      }
   }
   
   
   
   @Override
   protected void cancelRemovedNotifications( Collection< String > removedValues )
   {
      for ( String removedNotificationTaskId : removedValues )
      {
         notificationsMap.remove( removedNotificationTaskId );
      }
   }
   
   
   
   @Override
   protected Collection< String > getNotifiedTasksIds()
   {
      return notificationsMap.keySet();
   }
   
   
   
   private void updateOrLaunchNotification( Cursor tasksCursor, int endIndex )
   {
      final int indexOfLastDueTask = endIndex - 1;
      tasksCursor.moveToPosition( indexOfLastDueTask );
      
      final int count = endIndex;
      final String title = getNotificationTitle( tasksCursor );
      final String text = getNotificationText( tasksCursor );
      final String ticker = getNotificationTicker( tasksCursor );
      
      final INotificationBuilder builder;
      
      if ( count > 1 )
      {
         builder = newStackedNotification( title, text, ticker, count );
      }
      else
      {
         builder = newSingletonNotification( title, text, ticker, count );
      }
      
      getNotificationManager().notify( ID, builder.build() );
      isNotificationShown = true;
   }
   
   
   
   private INotificationBuilder newSingletonNotification( String title,
                                                          String text,
                                                          String ticker,
                                                          int count )
   {
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            ticker,
                                                                            count );
      
      final Bitmap largeIcon = BitmapFactory.decodeResource( getContext().getResources(),
                                                             R.drawable.ic_notify_due_task_expanded );
      builder.setLargeIcon( largeIcon );
      
      return builder;
   }
   
   
   
   private INotificationBuilder newStackedNotification( String title,
                                                        String text,
                                                        String ticker,
                                                        int count )
   {
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            ticker,
                                                                            count );
      
      final Bitmap largeIcon = BitmapFactory.decodeResource( getContext().getResources(),
                                                             R.drawable.ic_notify_due_task_expanded_stacked );
      builder.setLargeIcon( largeIcon );
      builder.setNumber( count );
      
      return builder;
   }
   
   
   
   private Intent createOpenTasksIntent()
   {
      final Intent openTasksIntent;
      
      if ( notificationsMap.size() == 1 )
      {
         openTasksIntent = Intents.createOpenTaskIntent( getContext(),
                                                         Queries.getOptString( /* tasksCursor */null,
                                                                               getColumnIndex( Tasks._ID ) ) );
      }
      else if ( notificationsMap.size() > 1 )
      {
         final String tasksListSelection = getDueTasksListSelection( /* tasksCursor */null );
         final SqlSelectionFilter filter = new SqlSelectionFilter( tasksListSelection );
         final String title = getContext().getString( R.string.notification_due_tasks_list_title );
         
         final Intent onClickIntent = Intents.createSqlSelectionFilterIntent( getContext(),
                                                                              filter,
                                                                              title );
         
      }
      
      return /* openTasksIntent */null;
   }
   
   
   
   private static String getDueTasksListSelection( Cursor tasksCursor )
   {
      final StringBuilder stringBuilder = new StringBuilder();
      
      while ( !tasksCursor.isAfterLast() )
      {
         final String taskId = Queries.getOptString( tasksCursor,
                                                     getColumnIndex( Tasks._ID ) );
         stringBuilder.append( Tasks._ID ).append( "=" ).append( taskId );
         
         if ( !tasksCursor.isLast() )
         {
            stringBuilder.append( " OR " );
         }
         
         tasksCursor.moveToNext();
      }
      
      return stringBuilder.toString();
   }
}
