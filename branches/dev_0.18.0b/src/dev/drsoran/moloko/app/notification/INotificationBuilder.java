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
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.RemoteViews;


interface INotificationBuilder
{
   INotificationBuilder addAction( int icon,
                                   CharSequence title,
                                   PendingIntent intent );
   
   
   
   INotificationBuilder setAutoCancel( boolean autoCancel );
   
   
   
   INotificationBuilder setContent( RemoteViews views );
   
   
   
   INotificationBuilder setContentInfo( CharSequence info );
   
   
   
   INotificationBuilder setContentIntent( PendingIntent intent );
   
   
   
   INotificationBuilder setContentText( CharSequence text );
   
   
   
   INotificationBuilder setContentTitle( CharSequence title );
   
   
   
   INotificationBuilder setDeleteIntent( PendingIntent intent );
   
   
   
   INotificationBuilder setDefaults( int defaults );
   
   
   
   INotificationBuilder setLargeIcon( Bitmap icon );
   
   
   
   INotificationBuilder setLights( int argb, int onMs, int offMs );
   
   
   
   INotificationBuilder setNumber( int number );
   
   
   
   INotificationBuilder setOngoing( boolean ongoing );
   
   
   
   INotificationBuilder setSmallIcon( int icon, int level );
   
   
   
   INotificationBuilder setSmallIcon( int icon );
   
   
   
   INotificationBuilder setSound( Uri sound );
   
   
   
   INotificationBuilder setTicker( CharSequence tickerText );
   
   
   
   INotificationBuilder setVibrate( long[] pattern );
   
   
   
   INotificationBuilder setWhen( long when );
   
   
   
   Notification build();
}
