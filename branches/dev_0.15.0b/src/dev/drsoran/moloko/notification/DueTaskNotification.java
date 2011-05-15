/* 
 *	Copyright (c) 2010 Ronny Röhricht
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

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.format.DateUtils;
import android.widget.RemoteViews;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.Intents;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.rtm.Task;


public class DueTaskNotification
{
   private final Context context;
   
   private final String taskId;
   
   private final String taskName;
   
   private final long dueTimeMillis;
   
   private boolean vibrate;
   
   private boolean led;
   
   private Uri sound;
   
   private long remindBeforeMillis;
   
   /**
    * If this reference is != null means we have already notified this {@link Notification}. We never notify an
    * {@link Notification} twice.
    */
   private Notification notification;
   
   

   public DueTaskNotification( Context context, Task task,
      long remindBeforeMillis, boolean vibrate, boolean led, Uri sound )
   {
      this.context = context;
      this.taskId = task.getId();
      this.taskName = task.getName();
      this.dueTimeMillis = task.getDue().getTime();
      this.remindBeforeMillis = remindBeforeMillis;
      this.vibrate = vibrate;
      this.led = led;
      this.sound = sound;
      
      if ( isTimeToNotify() )
         createNotification();
   }
   


   public void updateMinuteTick()
   {
      if ( notification == null && isTimeToNotify() )
         createNotification();
   }
   


   public void update( long remindBeforeMillis )
   {
      if ( notification == null )
      {
         this.remindBeforeMillis = remindBeforeMillis;
         
         if ( isTimeToNotify() )
         {
            createNotification();
         }
      }
   }
   


   public void update( boolean vibrate, boolean led, Uri sound )
   {
      if ( notification == null )
      {
         this.vibrate = vibrate;
         this.led = led;
         this.sound = sound;
      }
   }
   


   public String getTaskId()
   {
      return taskId;
   }
   


   public long getDueTime()
   {
      return dueTimeMillis;
   }
   


   public void cancel()
   {
      if ( notification != null )
         getNotificationManager().cancel( taskId.hashCode() );
   }
   


   private NotificationManager getNotificationManager()
   {
      return (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
   }
   


   private void createNotification()
   {
      this.notification = new Notification( R.drawable.ic_notify_logo_red,
                                            context.getString( R.string.notification_due_ticker,
                                                               taskName,
                                                               getRelativeTimeString() ),
                                            dueTimeMillis );
      
      this.notification.flags = Notification.FLAG_AUTO_CANCEL;
      this.notification.contentView = new RemoteViews( context.getPackageName(),
                                                       R.layout.notification_due_task );
      this.notification.contentView.setTextViewText( R.id.notification_due_task_title,
                                                     taskName );
      this.notification.contentView.setTextViewText( R.id.notification_due_task_text,
                                                     context.getString( R.string.notification_due,
                                                                        MolokoDateUtils.formatTime( dueTimeMillis ) ) );
      this.notification.contentIntent = Intents.createNotificationIntent( context,
                                                                          Intents.createOpenTaskIntent( context,
                                                                                                        taskId ) );
      
      this.notification.defaults = 0;
      
      if ( vibrate )
         this.notification.defaults |= Notification.DEFAULT_VIBRATE;
      if ( led )
      {
         this.notification.flags |= Notification.FLAG_SHOW_LIGHTS;
         this.notification.ledARGB = Color.GREEN;
         this.notification.ledOffMS = 400;
         this.notification.ledOnMS = 300;
      }
      
      this.notification.sound = sound;
      
      getNotificationManager().notify( taskId.hashCode(), notification );
   }
   


   private boolean isTimeToNotify()
   {
      return System.currentTimeMillis() >= ( dueTimeMillis - remindBeforeMillis );
   }
   


   private CharSequence getRelativeTimeString()
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
                                                     resolution );
      }
   }
   


   @Override
   public boolean equals( Object o )
   {
      if ( o instanceof DueTaskNotification )
      {
         final DueTaskNotification other = (DueTaskNotification) o;
         
         return other.taskId.equals( this.taskId )
            && other.dueTimeMillis == this.dueTimeMillis;
      }
      else
         return false;
   }
}
