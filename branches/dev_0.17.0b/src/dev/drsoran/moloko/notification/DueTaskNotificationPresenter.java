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

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateUtils;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.SqlSelectionFilter;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Tasks;


class DueTaskNotificationPresenter
{
   private final Context context;
   
   private DueTaskNotification dueTaskNotification;
   
   private boolean vibrateOnNotification;
   
   private boolean showLedOnNotification;
   
   private Uri soundForNotification;
   
   
   
   public DueTaskNotificationPresenter( Context context )
   {
      this.context = context;
   }
   
   
   
   public void showNotificationsFor( Cursor tasksCursor )
   {
      showNotificationsFor( tasksCursor, tasksCursor.getCount() );
   }
   
   
   
   public void showNotificationsFor( Cursor tasksCursor, int endIndex )
   {
      if ( dueTaskNotification == null )
      {
         createNotification();
      }
      
      applyNotificationFeatures();
      updateAndLaunchNotification( tasksCursor, endIndex );
   }
   
   
   
   public void setNotificationFeatures( Uri soundToPlay,
                                        boolean showLed,
                                        boolean vibrate )
   {
      soundForNotification = soundToPlay;
      showLedOnNotification = showLed;
      vibrateOnNotification = vibrate;
      
      if ( dueTaskNotification != null )
      {
         applyNotificationFeatures();
      }
   }
   
   
   
   public void cancelNotifications()
   {
      if ( dueTaskNotification != null )
      {
         dueTaskNotification.cancel();
         dueTaskNotification = null;
      }
   }
   
   
   
   private void createNotification()
   {
      dueTaskNotification = new DueTaskNotification( context );
   }
   
   
   
   private void updateAndLaunchNotification( Cursor tasksCursor, int endIndex )
   {
      final int indexOfLastDueTask = endIndex - 1;
      tasksCursor.moveToPosition( indexOfLastDueTask );
      
      final String title = getNotificationTitle( tasksCursor );
      final String text = getNotificationText( tasksCursor );
      final String ticker = getNotificationTicker( tasksCursor );
      final Intent onClickIntent = createOnClickIntent( tasksCursor, endIndex );
      
      dueTaskNotification.update( title, text, ticker, endIndex, onClickIntent );
   }
   
   
   
   private String getNotificationTitle( Cursor tasksCursor )
   {
      final String taskName = Queries.getOptString( tasksCursor,
                                                    getColumnIndex( Tasks.TASKSERIES_NAME ) );
      return taskName;
   }
   
   
   
   private String getNotificationText( Cursor tasksCursor )
   {
      final long dueTimeMillis = Queries.getOptLong( tasksCursor,
                                                     getColumnIndex( Tasks.DUE_DATE ) )
                                        .longValue();
      
      final String text = context.getString( R.string.notification_due,
                                             MolokoDateUtils.formatTime( context,
                                                                         dueTimeMillis ) );
      return text;
   }
   
   
   
   private String getNotificationTicker( Cursor tasksCursor )
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
   
   
   
   private Intent createOnClickIntent( Cursor tasksCursor, int numTasks )
   {
      final Intent onClickIntent;
      
      tasksCursor.moveToFirst();
      
      if ( numTasks == 1 )
      {
         onClickIntent = Intents.createOpenTaskIntent( context,
                                                       Queries.getOptString( tasksCursor,
                                                                             getColumnIndex( Tasks._ID ) ) );
      }
      else if ( numTasks > 1 )
      {
         final String tasksListSelection = getDueTasksListSelection( tasksCursor );
         final SqlSelectionFilter filter = new SqlSelectionFilter( tasksListSelection );
         final String title = context.getString( R.string.notification_due_tasks_list_title );
         
         onClickIntent = Intents.createSqlSelectionFilterIntent( context,
                                                                 filter,
                                                                 title );
      }
      else
      {
         onClickIntent = null;
      }
      
      return onClickIntent;
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
   
   
   
   private static int getColumnIndex( String colName )
   {
      return TasksProviderPart.COL_INDICES.get( colName ).intValue();
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
   
   
   
   private void applyNotificationFeatures()
   {
      dueTaskNotification.setNotificationFeatures( soundForNotification,
                                                   showLedOnNotification,
                                                   vibrateOnNotification );
   }
}
