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
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.Intents;


public class DueTaskNotification
{
   private final Context context;
   
   private final Notification notification;
   
   private final int id;
   
   

   public DueTaskNotification( Context context, int id )
   {
      this.context = context;
      this.id = id;
      
      this.notification = new Notification( R.drawable.ic_launcher,
                                            null,
                                            System.currentTimeMillis() );
      
      this.notification.flags = Notification.FLAG_AUTO_CANCEL;
      this.notification.contentView = new RemoteViews( context.getPackageName(),
                                                       R.layout.notification_permanent );
   }
   


   public void update( String title,
                       String text,
                       Intent onClickIntent,
                       boolean vibrate,
                       boolean led,
                       Uri sound )
   {
      notification.defaults = 0;
      
      if ( vibrate )
         notification.defaults |= Notification.DEFAULT_VIBRATE;
      if ( led )
         notification.defaults |= Notification.DEFAULT_LIGHTS;
      
      notification.sound = sound;
      
      notification.contentView.setTextViewText( R.id.notification_due_task_title,
                                                title );
      notification.contentView.setTextViewText( R.id.notification_due_task_text,
                                                text );
      notification.contentIntent = Intents.createNotificationIntent( context,
                                                                     onClickIntent );
      
      getNotificationManager().notify( this.id, notification );
   }
   


   public boolean isVibrate()
   {
      return ( notification.defaults & Notification.DEFAULT_VIBRATE ) != 0;
   }
   


   public boolean isLed()
   {
      return ( notification.defaults & Notification.DEFAULT_LIGHTS ) != 0;
   }
   


   public Uri isSound()
   {
      return notification.sound;
   }
   


   public void cancel()
   {
      getNotificationManager().cancel( id );
   }
   


   private NotificationManager getNotificationManager()
   {
      return (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
   }
   
}
