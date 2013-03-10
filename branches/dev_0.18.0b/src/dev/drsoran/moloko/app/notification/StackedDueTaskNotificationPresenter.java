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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.SqlSelectionFilter;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.provider.Rtm.Tasks;


@TargetApi( Build.VERSION_CODES.HONEYCOMB )
class StackedDueTaskNotificationPresenter extends
         AbstractDueTaskNotificationPresenter
{
   
   private final static class DistanceToNowComparator implements
            Comparator< DueTaskNotification >
   {
      private final long nowMillis = System.currentTimeMillis();
      
      
      
      @Override
      public int compare( DueTaskNotification lhs, DueTaskNotification rhs )
      {
         final long lhsDistance = Math.abs( nowMillis - lhs.getDue() );
         final long rhsDistance = Math.abs( nowMillis - rhs.getDue() );
         
         return (int) ( lhsDistance - rhsDistance );
      }
   };
   
   private final static int ID = R.id.notification_due_tasks_stacked;
   
   private DueTaskNotification lastTopOfStack;
   
   
   
   public StackedDueTaskNotificationPresenter( AppContext context )
   {
      super( context );
   }
   
   
   
   @Override
   protected void startActivity( Intent intent )
   {
      intent.addFlags( intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TASK );
      super.startActivity( intent );
   }
   
   
   
   @Override
   public void showNotificationsFor( Cursor tasksCursor, int endIndex )
   {
      super.showNotificationsFor( tasksCursor, endIndex );
      
      sortNotifications();
      updateOrLaunchNotification( tasksCursor );
   }
   
   
   
   @Override
   public boolean needsAllTimeChanges()
   {
      return true;
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
      
      setDueTaskNotificationsNotPartOfStack();
      lastTopOfStack = null;
   }
   
   
   
   @Override
   public void handleNotificationCleared( int notificationId )
   {
      setDueTaskNotificationsNotPartOfStack();
      lastTopOfStack = null;
   }
   
   
   
   @Override
   public void cancelNotifications()
   {
      if ( countDueTaskNotificationsPartOfStack() > 0 )
      {
         getNotificationManager().cancel( ID );
         lastTopOfStack = null;
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
   }
   
   
   
   @Override
   protected void onNewNotification( Cursor tasksCursor,
                                     List< String > taskIdsToNotify,
                                     DueTaskNotification newNotification )
   {
   }
   
   
   
   @Override
   protected void onNotificationRemoved( DueTaskNotification removedNotification )
   {
   }
   
   
   
   private void updateOrLaunchNotification( Cursor tasksCursor )
   {
      final int count = countDueTaskNotificationsPartOfStack();
      
      if ( count > 0 )
      {
         final List< DueTaskNotification > notificationStack = getDueTaskNotificationsPartOfStack();
         final DueTaskNotification topOfStackNotification = notificationStack.get( 0 );
         final boolean topOfStackHasChanged = !topOfStackNotification.equals( lastTopOfStack );
         final String topOfStackTaskId = topOfStackNotification.getTaskId();
         
         moveCursorToTask( tasksCursor, topOfStackTaskId );
         
         final String title = getNotificationTitle( tasksCursor );
         final String text = getNotificationText( tasksCursor );
         
         final INotificationBuilder builder;
         
         if ( count > 1 )
         {
            builder = newStackedNotification( title, text, tasksCursor );
         }
         else
         {
            builder = newSingletonNotification( title, text, count, tasksCursor );
         }
         
         builder.setContentIntent( Intents.createDueTasksNotificationIntent( getContext(),
                                                                             ID ) );
         builder.setDeleteIntent( Intents.createNotificationClearedIntent( getContext(),
                                                                           ID ) );
         
         if ( topOfStackHasChanged )
         {
            final String ticker = getNotificationTicker( tasksCursor );
            builder.setTicker( ticker );
            
            useNotificationFeatures( builder );
         }
         
         getNotificationManager().notify( ID, builder.build() );
         
         lastTopOfStack = topOfStackNotification;
      }
   }
   
   
   
   private INotificationBuilder newSingletonNotification( String title,
                                                          String text,
                                                          int count,
                                                          Cursor topOfStackTask )
   {
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            topOfStackTask );
      
      final Bitmap largeIcon = BitmapFactory.decodeResource( getContext().getResources(),
                                                             R.drawable.ic_notify_due_task_expanded );
      builder.setLargeIcon( largeIcon );
      
      return builder;
   }
   
   
   
   private INotificationBuilder newStackedNotification( String title,
                                                        String text,
                                                        Cursor tasksCursor )
   {
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            tasksCursor );
      
      final Bitmap largeIcon = BitmapFactory.decodeResource( getContext().getResources(),
                                                             R.drawable.ic_notify_due_task_expanded_stacked );
      builder.setLargeIcon( largeIcon );
      builder.setNumber( tasksCursor.getCount() );
      
      return builder;
   }
   
   
   
   private List< DueTaskNotification > getDueTaskNotificationsPartOfStack()
   {
      final List< DueTaskNotification > partOfStackNotifications = new ArrayList< DueTaskNotification >();
      
      for ( DueTaskNotification dueTaskNotification : notifications )
      {
         if ( dueTaskNotification.isVisible() )
         {
            partOfStackNotifications.add( dueTaskNotification );
         }
      }
      
      return partOfStackNotifications;
   }
   
   
   
   private void sortNotifications()
   {
      Collections.sort( notifications, new DistanceToNowComparator() );
   }
   
   
   
   private int countDueTaskNotificationsPartOfStack()
   {
      int cnt = 0;
      for ( DueTaskNotification dueTaskNotification : notifications )
      {
         if ( dueTaskNotification.isVisible() )
         {
            ++cnt;
         }
      }
      
      return cnt;
   }
   
   
   
   private void setDueTaskNotificationsNotPartOfStack()
   {
      for ( DueTaskNotification notification : notifications )
      {
         notification.setVisible( false );
      }
   }
   
   
   
   private Intent createOpenTasksIntent()
   {
      final List< DueTaskNotification > partOfStackNotifications = getDueTaskNotificationsPartOfStack();
      final Intent openTasksIntent;
      
      if ( partOfStackNotifications.size() == 1 )
      {
         openTasksIntent = Intents.createOpenTaskIntentFromNotification( getContext(),
                                                                         partOfStackNotifications.get( 0 )
                                                                                                 .getTaskId() );
      }
      
      else if ( partOfStackNotifications.size() > 1 )
      {
         final String tasksListSelection = getDueTasksListSelection( partOfStackNotifications );
         final SqlSelectionFilter filter = new SqlSelectionFilter( tasksListSelection );
         final String title = getContext().getString( R.string.notification_due_tasks_list_title );
         
         openTasksIntent = Intents.createSqlSelectionFilterIntent( getContext(),
                                                                   filter,
                                                                   title );
         
      }
      
      else
      {
         throw new IllegalStateException( "No notifications are part of the stack. Expected at least one." );
      }
      
      return openTasksIntent;
   }
   
   
   
   private void moveCursorToTask( Cursor tasksCursor, String taskId )
   {
      boolean found = false;
      for ( tasksCursor.moveToFirst(); !found && !tasksCursor.isAfterLast(); )
      {
         found = taskId.equals( tasksCursor.getString( getColumnIndex( Tasks._ID ) ) );
         if ( !found )
         {
            tasksCursor.moveToNext();
         }
      }
   }
   
   
   
   private static String getDueTasksListSelection( Collection< DueTaskNotification > dueTaskNotifications )
   {
      final StringBuilder stringBuilder = new StringBuilder();
      
      for ( Iterator< DueTaskNotification > i = dueTaskNotifications.iterator(); i.hasNext(); )
      {
         final DueTaskNotification dueTaskNotification = i.next();
         
         stringBuilder.append( Tasks._ID )
                      .append( "=" )
                      .append( dueTaskNotification.getTaskId() );
         
         if ( i.hasNext() )
         {
            stringBuilder.append( " OR " );
         }
      }
      
      return stringBuilder.toString();
   }
}
