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

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.Intents;


class DueTaskNotification
{
   private final static int ID = R.id.notification_due_tasks;
   
   private final Context context;
   
   private Notification notification;
   
   
   
   public DueTaskNotification( Context context )
   {
      this.context = context;
      
      createNotification();
   }
   
   
   
   public void update( String title,
                       String text,
                       String tickerText,
                       int count,
                       Intent onClickIntent )
   {
      notification.tickerText = tickerText;
      notification.iconLevel = count;
      notification.setLatestEventInfo( context,
                                       title,
                                       text,
                                       Intents.createNotificationIntent( context,
                                                                         onClickIntent ) );
      getNotificationManager().notify( ID, notification );
   }
   
   
   
   public void setNotificationFeatures( Uri sound, boolean led, boolean vibrate )
   {
      setSound( sound );
      setLed( led );
      setVibrate( vibrate );
   }
   
   
   
   public void cancel()
   {
      getNotificationManager().cancel( ID );
   }
   
   
   
   private void createNotification()
   {
      notification = new Notification( R.drawable.ic_notify_logo_red, null, 0 );
      notification.flags = Notification.FLAG_AUTO_CANCEL;
      notification.defaults = 0;
   }
   
   
   
   private void setVibrate( boolean vibrate )
   {
      if ( vibrate )
      {
         notification.defaults |= Notification.DEFAULT_VIBRATE;
      }
      else
      {
         notification.defaults &= ~Notification.DEFAULT_VIBRATE;
      }
   }
   
   
   
   private void setLed( boolean useLed )
   {
      if ( useLed )
      {
         notification.flags |= Notification.FLAG_SHOW_LIGHTS;
         notification.ledARGB = Color.BLUE;
         notification.ledOffMS = 400;
         notification.ledOnMS = 500;
      }
      else
      {
         notification.flags &= ~Notification.FLAG_SHOW_LIGHTS;
         notification.ledARGB = 0;
         notification.ledOffMS = 0;
         notification.ledOnMS = 0;
      }
   }
   
   
   
   private void setSound( Uri soundToPay )
   {
      notification.sound = soundToPay;
   }
   
   
   
   private NotificationManager getNotificationManager()
   {
      return (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
   }
}
