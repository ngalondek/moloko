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
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.Intents;


class PermanentNotification
{
   private final static int ID = R.id.notification_permanent;
   
   private final Context context;
   
   private Notification notification;
   
   
   
   public PermanentNotification( Context context )
   {
      this.context = context;
      
      createNotification();
   }
   
   
   
   public void update( String title,
                       String text,
                       int count,
                       Intent onClickIntent )
   {
      notification.iconLevel = count;
      notification.setLatestEventInfo( context,
                                       title,
                                       text,
                                       Intents.createNotificationIntent( context,
                                                                         onClickIntent ) );
      getNotificationManager().notify( ID, notification );
   }
   
   
   
   public void cancel()
   {
      getNotificationManager().cancel( ID );
   }
   
   
   
   private void createNotification()
   {
      notification = new Notification( R.drawable.notification_layers, null, 0 );
      notification.flags = Notification.FLAG_NO_CLEAR
         | Notification.FLAG_ONGOING_EVENT;
   }
   
   
   
   private NotificationManager getNotificationManager()
   {
      return (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
   }
}
