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
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.RemoteViews;


class NativeNotificationBuilder implements INotificationBuilder
{
   private final Notification.Builder nativeBuilder;
   
   
   
   public NativeNotificationBuilder( Context context )
   {
      nativeBuilder = new Builder( context );
   }
   
   
   
   @Override
   public INotificationBuilder setAutoCancel( boolean autoCancel )
   {
      nativeBuilder.setAutoCancel( autoCancel );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContent( RemoteViews views )
   {
      nativeBuilder.setContent( views );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentInfo( CharSequence info )
   {
      nativeBuilder.setContentInfo( info );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentIntent( PendingIntent intent )
   {
      nativeBuilder.setContentIntent( intent );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentText( CharSequence text )
   {
      nativeBuilder.setContentText( text );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentTitle( CharSequence title )
   {
      nativeBuilder.setContentTitle( title );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setDefaults( int defaults )
   {
      nativeBuilder.setDefaults( defaults );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setLargeIcon( Bitmap icon )
   {
      nativeBuilder.setLargeIcon( icon );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setLights( int argb, int onMs, int offMs )
   {
      nativeBuilder.setLights( argb, onMs, offMs );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setNumber( int number )
   {
      nativeBuilder.setNumber( number );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setOngoing( boolean ongoing )
   {
      nativeBuilder.setOngoing( ongoing );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setSmallIcon( int icon, int level )
   {
      nativeBuilder.setSmallIcon( icon, level );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setSmallIcon( int icon )
   {
      nativeBuilder.setSmallIcon( icon );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setSound( Uri sound )
   {
      nativeBuilder.setSound( sound );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setTicker( CharSequence tickerText )
   {
      nativeBuilder.setTicker( tickerText );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setVibrate( long[] pattern )
   {
      nativeBuilder.setVibrate( pattern );
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setWhen( long when )
   {
      nativeBuilder.setWhen( when );
      return this;
   }
   
   
   
   @Override
   public Notification build()
   {
      return nativeBuilder.getNotification();
   }
}
