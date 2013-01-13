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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.text.format.DateUtils;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.format.MolokoDateFormatter;
import dev.drsoran.moloko.util.AccountUtils;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


abstract class AbstractDueTaskNotificationPresenter implements
         IDueTaskNotificationPresenter
{
   protected final static class DueTaskNotification
   {
      private final String taskId;
      
      private final long due;
      
      private boolean isVisible = true;
      
      
      
      public DueTaskNotification( String taskId, long due )
      {
         this.taskId = taskId;
         this.due = due;
      }
      
      
      
      public boolean isVisible()
      {
         return isVisible;
      }
      
      
      
      public void setVisible( boolean isVisible )
      {
         this.isVisible = isVisible;
      }
      
      
      
      public String getTaskId()
      {
         return taskId;
      }
      
      
      
      public long getDue()
      {
         return due;
      }
      
      
      
      @Override
      public boolean equals( Object o )
      {
         if ( o == null )
            return false;
         
         if ( o == this )
            return true;
         
         if ( o.getClass() != getClass() )
            return false;
         
         return taskId.equals( ( (DueTaskNotification) o ).getTaskId() )
            && due == ( (DueTaskNotification) o ).due;
      }
      
      
      
      @Override
      public int hashCode()
      {
         int hashCode = taskId.hashCode();
         hashCode = 31 * hashCode + (int) due;
         
         return hashCode;
      }
      
      
      
      @Override
      public String toString()
      {
         return String.format( "%s, %s, %s",
                               taskId,
                               new Date( due ).toString(),
                               String.valueOf( isVisible ) );
      }
   }
   
   private final static long[] VIBRATE_PATTERN =
   { 0, 300 };
   
   private final Context context;
   
   private boolean vibrateOnNotification;
   
   private boolean showLedOnNotification;
   
   private Uri soundForNotification;
   
   protected final List< DueTaskNotification > notifications = new LinkedList< DueTaskNotification >();
   
   
   
   protected AbstractDueTaskNotificationPresenter( Context context )
   {
      this.context = context;
   }
   
   
   
   @Override
   public void showNotificationsFor( Cursor tasksCursor )
   {
      showNotificationsFor( tasksCursor, tasksCursor.getCount() );
   }
   
   
   
   @Override
   public void showNotificationsFor( Cursor tasksCursor, int endIndex )
   {
      final List< String > taskIdsToNotify = getTaskIds( tasksCursor, endIndex );
      final Collection< String > taskIdsInNotification = getNotifiedTasksIds();
      
      final NotificationDiffer.Diff diff = new NotificationDiffer().diffTaskIdSets( taskIdsInNotification,
                                                                                    taskIdsToNotify );
      
      cancelRemovedNotifications( diff.getRemovedValues() );
      
      insertNewNotifications( tasksCursor, taskIdsToNotify, diff.getNewValues() );
      
      updateExistingNotifications( tasksCursor,
                                   taskIdsToNotify,
                                   diff.getUpdatedValues() );
   }
   
   
   
   @Override
   public void cancelNotifications()
   {
      notifications.clear();
   }
   
   
   
   @Override
   public void setNotificationFeatures( Uri soundToPlay,
                                        boolean showLed,
                                        boolean vibrate )
   {
      soundForNotification = soundToPlay;
      showLedOnNotification = showLed;
      vibrateOnNotification = vibrate;
   }
   
   
   
   protected Context getContext()
   {
      return context;
   }
   
   
   
   protected void startActivity( Intent intent )
   {
      intent.setFlags( intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TASK
         | Intent.FLAG_ACTIVITY_NEW_TASK );
      
      context.startActivity( intent );
   }
   
   
   
   protected static long[] getVibratePattern()
   {
      return VIBRATE_PATTERN;
   }
   
   
   
   protected boolean isVibrateOnNotification()
   {
      return vibrateOnNotification;
   }
   
   
   
   protected boolean isShowLedOnNotification()
   {
      return showLedOnNotification;
   }
   
   
   
   protected Uri getSoundForNotification()
   {
      return soundForNotification;
   }
   
   
   
   protected NotificationManager getNotificationManager()
   {
      return (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
   }
   
   
   
   protected String getNotificationTitle( Cursor tasksCursor )
   {
      final String taskName = Queries.getOptString( tasksCursor,
                                                    getColumnIndex( Tasks.TASKSERIES_NAME ) );
      return taskName;
   }
   
   
   
   protected String getNotificationText( Cursor tasksCursor )
   {
      final long dueTimeMillis = Queries.getOptLong( tasksCursor,
                                                     getColumnIndex( Tasks.DUE_DATE ) )
                                        .longValue();
      
      final String text = context.getString( R.string.notification_due,
                                             MolokoDateFormatter.formatTime( context,
                                                                             dueTimeMillis ) );
      return text;
   }
   
   
   
   protected String getNotificationTicker( Cursor tasksCursor )
   {
      final String taskName = Queries.getOptString( tasksCursor,
                                                    getColumnIndex( Tasks.TASKSERIES_NAME ) );
      final long dueTimeMillis = Queries.getOptLong( tasksCursor,
                                                     getColumnIndex( Tasks.DUE_DATE ) )
                                        .longValue();
      
      final String relativeDueTimeFromNow = getRelativeTimeString( dueTimeMillis );
      
      final String tickerText = context.getString( R.string.notification_due_ticker,
                                                   taskName,
                                                   relativeDueTimeFromNow );
      return tickerText;
   }
   
   
   
   protected List< String > getTaskIds( Cursor tasksCursor, int endIndex )
   {
      final List< String > taskIds = new ArrayList< String >( endIndex );
      
      boolean ok = tasksCursor.moveToFirst();
      for ( int i = 0; i < endIndex && ok; ok = tasksCursor.moveToNext(), ++i )
      {
         final String taskId = Queries.getOptString( tasksCursor,
                                                     getColumnIndex( Tasks._ID ) );
         taskIds.add( taskId );
      }
      
      return taskIds;
   }
   
   
   
   protected static int taskIdToNotificationId( String taskId )
   {
      return Integer.parseInt( taskId );
   }
   
   
   
   protected INotificationBuilder createDefaultInitializedBuilder( String title,
                                                                   String text,
                                                                   Cursor tasksCursor )
   {
      final INotificationBuilder builder = NotificationBuilderFactory.create( getContext() );
      
      builder.setAutoCancel( true );
      builder.setContentTitle( title );
      builder.setContentText( text );
      builder.setSmallIcon( R.drawable.notification_due_task,
                            tasksCursor.getCount() );
      
      if ( AccountUtils.isWriteableAccess( getContext() ) )
      {
         addNotificationActions( tasksCursor, builder );
      }
      
      return builder;
   }
   
   
   
   private void addNotificationActions( Cursor tasksCursor,
                                        INotificationBuilder builder )
   {
      final Task task = TasksProviderPart.createTask( tasksCursor );
      
      builder.addAction( R.drawable.ic_menu_complete,
                         getContext().getString( R.string.app_task_complete ),
                         Intents.createTaskCompletedFromNotificationIntent( getContext(),
                                                                            task ) );
      
      builder.addAction( R.drawable.ic_menu_postponed,
                         getContext().getString( R.string.app_task_postpone ),
                         Intents.createTaskPostponedFromNotificationIntent( getContext(),
                                                                            task ) );
   }
   
   
   
   protected static int getColumnIndex( String colName )
   {
      return TasksProviderPart.COL_INDICES.get( colName ).intValue();
   }
   
   
   
   protected void useNotificationFeatures( INotificationBuilder builder )
   {
      setVibrate( builder );
      setLed( builder );
      setSound( builder );
   }
   
   
   
   protected Collection< String > getNotifiedTasksIds()
   {
      final List< String > taskIds = new ArrayList< String >( notifications.size() );
      
      for ( DueTaskNotification notification : notifications )
      {
         taskIds.add( notification.getTaskId() );
      }
      
      return taskIds;
   }
   
   
   
   protected boolean containsNotifiedTasksId( String taskId )
   {
      return getNotificationByTaskId( taskId ) != null;
   }
   
   
   
   protected DueTaskNotification getNotificationByTaskId( String taskId )
   {
      for ( DueTaskNotification notification : notifications )
      {
         if ( notification.getTaskId().equals( taskId ) )
            return notification;
      }
      
      return null;
   }
   
   
   
   private void updateExistingNotifications( Cursor tasksCursor,
                                             List< String > taskIdsToNotify,
                                             Collection< String > updatedValues )
   {
      for ( String updatedNotificationTaskId : updatedValues )
      {
         final DueTaskNotification notification = getDueTaskNotification( updatedNotificationTaskId );
         if ( notification != null )
         {
            final int cursorIndexOfNew = taskIdsToNotify.indexOf( updatedNotificationTaskId );
            tasksCursor.moveToPosition( cursorIndexOfNew );
            
            final DueTaskNotification updatedNotification = newDueTaskNotification( tasksCursor,
                                                                                    updatedNotificationTaskId );
            updatedNotification.setVisible( notification.isVisible() );
            
            notifications.remove( notification );
            notifications.add( updatedNotification );
            
            onNotificationUpdate( tasksCursor,
                                  taskIdsToNotify,
                                  notification,
                                  updatedNotification );
         }
      }
   }
   
   
   
   private void insertNewNotifications( Cursor tasksCursor,
                                        List< String > taskIdsToNotify,
                                        Collection< String > newValues )
   {
      for ( String newNotificationTaskId : newValues )
      {
         final int cursorIndexOfNew = taskIdsToNotify.indexOf( newNotificationTaskId );
         tasksCursor.moveToPosition( cursorIndexOfNew );
         
         final DueTaskNotification newNotification = newDueTaskNotification( tasksCursor,
                                                                             newNotificationTaskId );
         notifications.add( newNotification );
         
         onNewNotification( tasksCursor, taskIdsToNotify, newNotification );
      }
   }
   
   
   
   private void cancelRemovedNotifications( Collection< String > removedValues )
   {
      for ( String removedNotificationTaskId : removedValues )
      {
         final DueTaskNotification notification = getDueTaskNotification( removedNotificationTaskId );
         if ( notification != null )
         {
            notifications.remove( notification );
            onNotificationRemoved( notification );
         }
      }
   }
   
   
   
   private DueTaskNotification newDueTaskNotification( Cursor tasksCursor,
                                                       String newNotificationTaskId )
   {
      final DueTaskNotification newNotification = new DueTaskNotification( newNotificationTaskId,
                                                                           tasksCursor.getLong( getColumnIndex( Tasks.DUE_DATE ) ) );
      return newNotification;
   }
   
   
   
   private DueTaskNotification getDueTaskNotification( String removedNotificationTaskId )
   {
      DueTaskNotification foundNotification = null;
      
      for ( Iterator< DueTaskNotification > i = notifications.iterator(); foundNotification == null
         && i.hasNext(); )
      {
         final DueTaskNotification notification = i.next();
         if ( notification.getTaskId().equals( removedNotificationTaskId ) )
         {
            foundNotification = notification;
         }
      }
      
      return foundNotification;
   }
   
   
   
   private String getRelativeTimeString( long dueTimeMillis )
   {
      final long now = System.currentTimeMillis();
      final long resolution = MolokoDateUtils.getFittingDateUtilsResolution( dueTimeMillis,
                                                                             now );
      
      if ( resolution == DateUtils.SECOND_IN_MILLIS )
      {
         return context.getString( R.string.phr_now );
      }
      else
      {
         return DateUtils.getRelativeTimeSpanString( dueTimeMillis,
                                                     now,
                                                     resolution ).toString();
      }
   }
   
   
   
   private void setVibrate( INotificationBuilder builder )
   {
      if ( vibrateOnNotification )
      {
         builder.setVibrate( VIBRATE_PATTERN );
      }
      else
      {
         builder.setVibrate( null );
      }
   }
   
   
   
   private void setLed( INotificationBuilder builder )
   {
      if ( showLedOnNotification )
      {
         builder.setLights( Color.BLUE, 400, 500 );
      }
      else
      {
         builder.setLights( 0, 0, 0 );
      }
   }
   
   
   
   private void setSound( INotificationBuilder builder )
   {
      builder.setSound( soundForNotification );
   }
   
   
   
   protected abstract void onNotificationUpdate( Cursor tasksCursor,
                                                 List< String > taskIdsToNotify,
                                                 DueTaskNotification oldNotification,
                                                 DueTaskNotification newNotification );
   
   
   
   protected abstract void onNewNotification( Cursor tasksCursor,
                                              List< String > taskIdsToNotify,
                                              DueTaskNotification newNotification );
   
   
   
   protected abstract void onNotificationRemoved( DueTaskNotification removedNotification );
}
