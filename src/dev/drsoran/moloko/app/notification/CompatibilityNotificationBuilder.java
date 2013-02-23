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

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;


class CompatibilityNotificationBuilder implements INotificationBuilder
{
   private final NotificationCompat.Builder compatBuilder;
   
   
   
   public CompatibilityNotificationBuilder( Context context )
   {
      compatBuilder = new NotificationCompat.Builder( context );
   }
   
   
   
   @Override
   public INotificationBuilder addAction( int icon,
                                          CharSequence title,
                                          PendingIntent intent )
   {
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setAutoCancel( boolean autoCancel )
   {
      compatBuilder.setAutoCancel( autoCancel );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContent( RemoteViews views )
   {
      compatBuilder.setContent( views );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentInfo( CharSequence info )
   {
      compatBuilder.setContentInfo( info );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentIntent( PendingIntent intent )
   {
      compatBuilder.setContentIntent( intent );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentText( CharSequence text )
   {
      compatBuilder.setContentText( text );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentTitle( CharSequence title )
   {
      compatBuilder.setContentTitle( title );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setDeleteIntent( PendingIntent intent )
   {
      compatBuilder.setDeleteIntent( intent );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setDefaults( int defaults )
   {
      compatBuilder.setDefaults( defaults );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setLargeIcon( Bitmap icon )
   {
      compatBuilder.setLargeIcon( icon );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setLights( int argb, int onMs, int offMs )
   {
      compatBuilder.setLights( argb, onMs, offMs );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setNumber( int number )
   {
      compatBuilder.setNumber( number );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setOngoing( boolean ongoing )
   {
      compatBuilder.setOngoing( ongoing );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setSmallIcon( int icon, int level )
   {
      compatBuilder.setSmallIcon( icon, level );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setSmallIcon( int icon )
   {
      compatBuilder.setSmallIcon( icon );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setSound( Uri sound )
   {
      compatBuilder.setSound( sound );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setTicker( CharSequence tickerText )
   {
      compatBuilder.setTicker( tickerText );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setVibrate( long[] pattern )
   {
      compatBuilder.setVibrate( pattern );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setWhen( long when )
   {
      compatBuilder.setWhen( when );
      return this;
   }
   
   
   
   @Override
   public Notification build()
   {
      return compatBuilder.getNotification();
   }
}
