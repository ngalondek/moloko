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

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Pair;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.settings.PermanentNotificationType;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.moloko.content.db.TableColumns.Tasks;
import dev.drsoran.moloko.ui.services.IDateFormatterService;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.rtm.RtmSmartFilter;


abstract class AbstractPermanentNotificationPresenter implements
         IPermanentNotificationPresenter
{
   private final static int ID = R.id.notification_permanent;
   
   private final AppContext context;
   
   private boolean isNotificationActive;
   
   
   
   protected AbstractPermanentNotificationPresenter( AppContext context )
   {
      this.context = context;
   }
   
   
   
   @Override
   public void showNotificationFor( Cursor tasksCursor, String filterString )
   {
      updateOrLaunchNotification( tasksCursor, filterString );
   }
   
   
   
   @Override
   public boolean isHandlingNotification( int notificationId )
   {
      return notificationId == ID;
   }
   
   
   
   @Override
   public void cancelNotification()
   {
      if ( isNotificationActive )
      {
         getNotificationManager().cancel( ID );
         isNotificationActive = false;
      }
   }
   
   
   
   public static int getColumnIndex( String colName )
   {
      return TasksProviderPart.COL_INDICES.get( colName ).intValue();
   }
   
   
   
   protected AppContext getContext()
   {
      return context;
   }
   
   
   
   protected Intent createSingletonOnClickIntent( Cursor tasksCursor )
   {
      if ( tasksCursor.moveToFirst() )
      {
         return Intents.createOpenTaskIntentFromNotification( getContext(),
                                                              DbUtils.getOptString( tasksCursor,
                                                                                    getColumnIndex( Tasks._ID ) ) );
      }
      
      return null;
   }
   
   
   
   protected Intent createMultiTasksOnClickIntent( Cursor tasksCursor,
                                                   String activityTitle,
                                                   String filterString )
   {
      return Intents.createSmartFilterIntent( getContext(),
                                              new RtmSmartFilter( filterString ),
                                              activityTitle )
                    .putExtra( Intents.Extras.KEY_FROM_NOTIFICATION, true );
   }
   
   
   
   protected INotificationBuilder createDefaultInitializedBuilder( String title,
                                                                   String text,
                                                                   int count )
   {
      final INotificationBuilder builder = NotificationBuilderFactory.create( getContext() );
      
      builder.setOngoing( true );
      builder.setSmallIcon( R.drawable.notification_permanent, count );
      builder.setContentTitle( title );
      builder.setContentText( text );
      builder.setContentIntent( Intents.createPermanentNotificationIntent( getContext(),
                                                                           ID ) );
      
      return builder;
   }
   
   
   
   private void updateOrLaunchNotification( Cursor tasksCursor,
                                            String filterString )
   {
      final String title = getNotificationTitle();
      final String text = buildPermanentNotificationRowText( tasksCursor );
      
      final Notification notification = newNotfication( title,
                                                        text,
                                                        tasksCursor,
                                                        filterString );
      
      getNotificationManager().notify( ID, notification );
      
      isNotificationActive = true;
   }
   
   
   
   private String getNotificationTitle()
   {
      final Map< PermanentNotificationType, Collection< String >> permanetNotifications = context.getSettings()
                                                                                                 .getNotifyingPermanentTaskLists();
      
      final boolean isNotifyingTodayTasks = !permanetNotifications.get( PermanentNotificationType.TODAY )
                                                                  .isEmpty();
      final boolean isNotifyingTomorrowTasks = !permanetNotifications.get( PermanentNotificationType.TOMORROW )
                                                                     .isEmpty();
      final boolean isNotifyingOverdueTasks = !permanetNotifications.get( PermanentNotificationType.OVERDUE )
                                                                    .isEmpty();
      
      if ( isNotifyingTodayTasks && isNotifyingTomorrowTasks )
      {
         final MolokoCalendar cal = MolokoCalendar.getInstance();
         
         final long todayMillis = cal.getTimeInMillis();
         cal.roll( Calendar.DAY_OF_YEAR, 1 );
         final long tomorrowMillis = cal.getTimeInMillis();
         
         final IDateFormatterService dateFormatter = context.getDateFormatter();
         
         return context.getString( R.string.notification_permanent_today_and_tomorrow_title,
                                   dateFormatter.formatDate( todayMillis,
                                                             IDateFormatterService.FORMAT_NUMERIC ),
                                   dateFormatter.formatDate( tomorrowMillis,
                                                             IDateFormatterService.FORMAT_NUMERIC ) );
      }
      
      if ( isNotifyingTodayTasks )
      {
         return context.getString( R.string.notification_permanent_today_title );
      }
      
      if ( isNotifyingTomorrowTasks )
      {
         return context.getString( R.string.notification_permanent_tomorrow_title );
      }
      
      if ( isNotifyingOverdueTasks )
      {
         return context.getString( R.string.notification_permanent_overdue_title );
      }
      
      return Strings.EMPTY_STRING;
   }
   
   
   
   private String buildPermanentNotificationRowText( Cursor tasksCursor )
   {
      String result = Strings.EMPTY_STRING;
      
      final int tasksCount = tasksCursor.getCount();
      
      final Pair< Integer, Integer > numHighPrioAndOverdueTasks = countHighPrioAndOverdueTasks( tasksCursor );
      final int highPrioCnt = numHighPrioAndOverdueTasks.first.intValue();
      final int overdueCnt = numHighPrioAndOverdueTasks.second.intValue();
      final int tasksDueCnt = tasksCount - overdueCnt;
      
      if ( tasksCount == 1 )
      {
         tasksCursor.moveToFirst();
         
         final String taskName = DbUtils.getOptString( tasksCursor,
                                                       getColumnIndex( Tasks.TASKSERIES_NAME ) );
         
         // If we have one task to show and this is overdue, show due date.
         if ( overdueCnt == 1 )
         {
            final String pastString = new OverdueNotificationTaskDateFormatter( context ).getFormattedOverdueDueDate( DbUtils.getOptLong( tasksCursor,
                                                                                                                                          getColumnIndex( Tasks.DUE_DATE ) ) );
            if ( !TextUtils.isEmpty( pastString ) )
            {
               result = context.getString( R.string.notification_permanent_text_one_task_overdue,
                                           taskName,
                                           pastString );
            }
         }
         
         if ( TextUtils.isEmpty( result ) )
         {
            result = context.getString( R.string.notification_permanent_text_one_task,
                                        taskName );
         }
      }
      
      else if ( tasksDueCnt > 0 )
      {
         if ( overdueCnt == 0 )
            result = context.getString( R.string.notification_permanent_text_multiple,
                                        tasksDueCnt,
                                        highPrioCnt );
         else if ( overdueCnt > 0 )
            result = context.getString( R.string.notification_permanent_text_multiple_w_overdue,
                                        tasksDueCnt,
                                        overdueCnt );
      }
      
      else if ( tasksDueCnt == 0 && overdueCnt > 1 )
      {
         result = context.getString( R.string.notification_permanent_text_multiple_overdue,
                                     overdueCnt );
      }
      
      else
      {
         result = String.format( "Unhandled case tasks:%d, due:%d, overdue %d",
                                 tasksCount,
                                 tasksDueCnt,
                                 overdueCnt );
      }
      
      return result;
   }
   
   
   
   private Pair< Integer, Integer > countHighPrioAndOverdueTasks( Cursor tasksCursor )
   {
      int numOverdueTasks = 0;
      int numHighPrioTasks = 0;
      
      MolokoCalendar nowCal = null;
      
      boolean hasNext = tasksCursor.moveToFirst();
      for ( ; hasNext; hasNext = tasksCursor.moveToNext() )
      {
         final String priorityString = DbUtils.getOptString( tasksCursor,
                                                             getColumnIndex( Tasks.PRIORITY ) );
         
         if ( RtmTask.convertPriority( priorityString ) == RtmTask.Priority.High )
         {
            ++numHighPrioTasks;
         }
         
         final Long due = DbUtils.getOptLong( tasksCursor,
                                              getColumnIndex( Tasks.DUE_DATE ) );
         
         if ( due != null )
         {
            final long dueMillis = due.longValue();
            
            if ( nowCal == null )
               nowCal = MolokoCalendar.getInstance();
            
            final boolean hasDueTime = DbUtils.getOptBool( tasksCursor,
                                                           getColumnIndex( Tasks.HAS_DUE_TIME ),
                                                           false );
            
            // If the task has a due time then it can be overdue
            // even today.
            if ( hasDueTime
               && MolokoDateUtils.isDaysBefore( dueMillis,
                                                nowCal.getTimeInMillis() ) )
            {
               ++numOverdueTasks;
            }
            
            // If the task has no due time then it can be overdue
            // only before today.
            else if ( !MolokoDateUtils.isToday( dueMillis )
               && !MolokoDateUtils.isDaysAfter( dueMillis,
                                                nowCal.getTimeInMillis() ) )
            {
               ++numOverdueTasks;
            }
         }
      }
      
      return Pair.create( Integer.valueOf( numHighPrioTasks ),
                          Integer.valueOf( numOverdueTasks ) );
   }
   
   
   
   private NotificationManager getNotificationManager()
   {
      return (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
   }
   
   
   
   protected abstract Notification newNotfication( String title,
                                                   String text,
                                                   Cursor tasksCursor,
                                                   String filterString );
}
