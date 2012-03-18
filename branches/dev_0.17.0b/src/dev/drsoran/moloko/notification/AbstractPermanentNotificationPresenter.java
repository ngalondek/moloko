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

import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Pair;

import com.mdt.rtm.data.RtmTask;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.RtmSmartFilter;


abstract class AbstractPermanentNotificationPresenter implements
         IPermanentNotificationPresenter
{
   private final static int ID = R.id.notification_permanent;
   
   private final Context context;
   
   private boolean isNotificationActive;
   
   
   
   protected AbstractPermanentNotificationPresenter( Context context )
   {
      this.context = context;
   }
   
   
   
   @Override
   public void showNotificationFor( Cursor tasksCursor, String filterString )
   {
      updateOrLaunchNotification( tasksCursor, filterString );
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
   
   
   
   protected Context getContext()
   {
      return context;
   }
   
   
   
   private void updateOrLaunchNotification( Cursor tasksCursor,
                                            String filterString )
   {
      final String title = getNotificationTitle();
      final String text = buildPermanentNotificationRowText( tasksCursor );
      final Intent onClickIntent = createOnClickIntent( tasksCursor,
                                                        title,
                                                        filterString );
      
      Notification notification = newNotfication( title,
                                                  text,
                                                  tasksCursor.getCount(),
                                                  onClickIntent );
      
      getNotificationManager().notify( ID, notification );
      isNotificationActive = true;
   }
   
   
   
   private String getNotificationTitle()
   {
      final int notificationType = getPermanentNotificationTypeFromSettings();
      
      switch ( notificationType )
      {
         case PermanentNotificationType.TODAY:
            return context.getString( R.string.notification_permanent_today_title );
            
         case PermanentNotificationType.TOMORROW:
            return context.getString( R.string.notification_permanent_tomorrow_title );
            
         case PermanentNotificationType.TODAY_AND_TOMORROW:
         {
            final MolokoCalendar cal = MolokoCalendar.getInstance();
            
            final long todayMillis = cal.getTimeInMillis();
            cal.roll( Calendar.DAY_OF_YEAR, 1 );
            final long tomorrowMillis = cal.getTimeInMillis();
            
            return context.getString( R.string.notification_permanent_today_and_tomorrow_title,
                                      MolokoDateUtils.formatDate( context,
                                                                  todayMillis,
                                                                  MolokoDateUtils.FORMAT_NUMERIC ),
                                      MolokoDateUtils.formatDate( context,
                                                                  tomorrowMillis,
                                                                  MolokoDateUtils.FORMAT_NUMERIC ) );
         }
         
         case PermanentNotificationType.OFF:
         {
            if ( isNotifyingPermanentOverdueTasks() )
            {
               return context.getString( R.string.notification_permanent_overdue_title );
            }
            else
            {
               return Strings.EMPTY_STRING;
            }
         }
         
         default :
            return Strings.EMPTY_STRING;
      }
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
         
         final String taskName = Queries.getOptString( tasksCursor,
                                                       getColumnIndex( Tasks.TASKSERIES_NAME ) );
         result = context.getString( R.string.notification_permanent_text_one_task,
                                     taskName );
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
         final String priorityString = Queries.getOptString( tasksCursor,
                                                             getColumnIndex( Tasks.PRIORITY ) );
         
         if ( RtmTask.convertPriority( priorityString ) == RtmTask.Priority.High )
         {
            ++numHighPrioTasks;
         }
         
         final Long due = Queries.getOptLong( tasksCursor,
                                              getColumnIndex( Tasks.DUE_DATE ) );
         
         if ( due != null )
         {
            final long dueMillis = due.longValue();
            
            if ( nowCal == null )
               nowCal = MolokoCalendar.getInstance();
            
            final boolean hasDueTime = Queries.getOptBool( tasksCursor,
                                                           getColumnIndex( Tasks.HAS_DUE_TIME ),
                                                           false );
            
            // If the task has a due time then it can be overdue
            // even today.
            if ( hasDueTime
               && MolokoDateUtils.isBefore( dueMillis, nowCal.getTimeInMillis() ) )
            {
               ++numOverdueTasks;
            }
            
            // If the task has no due time then it can be overdue
            // only before today.
            else if ( !MolokoDateUtils.isToday( dueMillis )
               && !MolokoDateUtils.isAfter( dueMillis, nowCal.getTimeInMillis() ) )
            {
               ++numOverdueTasks;
            }
         }
      }
      
      return Pair.create( Integer.valueOf( numHighPrioTasks ),
                          Integer.valueOf( numOverdueTasks ) );
   }
   
   
   
   private Intent createOnClickIntent( Cursor tasksCursor,
                                       String activityTitle,
                                       String filterString )
   {
      final Intent onClickIntent;
      final int numTasks = tasksCursor.getCount();
      
      if ( numTasks == 1 )
      {
         if ( tasksCursor.moveToFirst() )
         {
            onClickIntent = Intents.createOpenTaskIntent( context,
                                                          Queries.getOptString( tasksCursor,
                                                                                getColumnIndex( Tasks.TASKSERIES_ID ) ) );
         }
         else
         {
            onClickIntent = null;
         }
      }
      else
      {
         onClickIntent = Intents.createSmartFilterIntent( context,
                                                          new RtmSmartFilter( filterString ),
                                                          activityTitle );
      }
      
      return onClickIntent;
   }
   
   
   
   private int getPermanentNotificationTypeFromSettings()
   {
      return MolokoApp.getSettings( context ).getNotifyingPermanentTasksType();
   }
   
   
   
   private boolean isNotifyingPermanentOverdueTasks()
   {
      return MolokoApp.getSettings( context )
                      .isNotifyingPermanentOverdueTasks();
   }
   
   
   
   private static int getColumnIndex( String colName )
   {
      return TasksProviderPart.COL_INDICES.get( colName ).intValue();
   }
   
   
   
   private NotificationManager getNotificationManager()
   {
      return (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
   }
   
   
   
   protected abstract Notification newNotfication( String title,
                                                   String text,
                                                   int count,
                                                   Intent onClickIntent );
}
