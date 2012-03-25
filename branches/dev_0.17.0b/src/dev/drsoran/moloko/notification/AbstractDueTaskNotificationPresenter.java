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
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Tasks;


abstract class AbstractDueTaskNotificationPresenter implements
         IDueTaskNotificationPresenter
{
   private final static long[] VIBRATE_PATTERN =
   { 0, 300 };
   
   private final Context context;
   
   private boolean vibrateOnNotification;
   
   private boolean showLedOnNotification;
   
   private Uri soundForNotification;
   
   
   
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
                                             MolokoDateUtils.formatTime( context,
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
      if ( ok )
      {
         for ( int i = 0; i < endIndex && ok; ok = tasksCursor.moveToNext() )
         {
            final String taskId = Queries.getOptString( tasksCursor,
                                                        getColumnIndex( Tasks._ID ) );
            taskIds.add( taskId );
         }
      }
      
      return taskIds;
   }
   
   
   
   protected static int taskIdToNotificationId( String taskId )
   {
      return Integer.parseInt( taskId );
   }
   
   
   
   protected INotificationBuilder createDefaultInitializedBuilder( String title,
                                                                   String text,
                                                                   String ticker,
                                                                   int count )
   {
      final INotificationBuilder builder = NotificationBuilderFactory.create( getContext() );
      
      builder.setAutoCancel( true );
      builder.setContentTitle( title );
      builder.setContentText( text );
      builder.setTicker( ticker );
      builder.setSmallIcon( R.drawable.notification_due_task, count );
      
      applyNotificationFeatures( builder );
      
      return builder;
   }
   
   
   
   protected static int getColumnIndex( String colName )
   {
      return TasksProviderPart.COL_INDICES.get( colName ).intValue();
   }
   
   
   
   private void applyNotificationFeatures( INotificationBuilder builder )
   {
      setVibrate( builder );
      setLed( builder );
      setSound( builder );
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
   
   
   
   protected abstract void updateExistingNotifications( Cursor tasksCursor,
                                                        List< String > taskIdsToNotify,
                                                        Collection< String > updatedValues );
   
   
   
   protected abstract void insertNewNotifications( Cursor tasksCursor,
                                                   List< String > taskIdsToNotify,
                                                   Collection< String > newValues );
   
   
   
   protected abstract void cancelRemovedNotifications( Collection< String > removedValues );
   
   
   
   protected abstract Collection< String > getNotifiedTasksIds();
}
