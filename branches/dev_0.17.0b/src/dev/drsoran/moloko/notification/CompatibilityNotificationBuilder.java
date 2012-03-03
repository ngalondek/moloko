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
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.widget.RemoteViews;


class CompatibilityNotificationBuilder implements INotificationBuilder
{
   private final Context context;
   
   private boolean autoCancel;
   
   private boolean isOngoing;
   
   private RemoteViews content;
   
   private CharSequence contentText;
   
   private CharSequence contentTitle;
   
   private PendingIntent contentIntent;
   
   private CharSequence tickerText;
   
   private int defaults = Notification.DEFAULT_ALL;
   
   private int lightsARGB;
   
   private int lightsOnMs;
   
   private int lightsOffMs;
   
   private int number;
   
   private int icon;
   
   private int iconLevel;
   
   private Uri sound;
   
   private long[] vibrate;
   
   private long when;
   
   
   
   public CompatibilityNotificationBuilder( Context context )
   {
      this.context = context;
   }
   
   
   
   @Override
   public INotificationBuilder setAutoCancel( boolean autoCancel )
   {
      this.autoCancel = autoCancel;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContent( RemoteViews views )
   {
      content = views;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentInfo( CharSequence info )
   {
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentIntent( PendingIntent intent )
   {
      contentIntent = intent;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentText( CharSequence text )
   {
      contentText = text;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setContentTitle( CharSequence title )
   {
      contentTitle = title;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setDefaults( int defaults )
   {
      this.defaults = defaults;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setLargeIcon( Bitmap icon )
   {
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setLights( int argb, int onMs, int offMs )
   {
      lightsARGB = argb;
      lightsOnMs = onMs;
      lightsOffMs = offMs;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setNumber( int number )
   {
      this.number = number;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setOngoing( boolean ongoing )
   {
      isOngoing = ongoing;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setSmallIcon( int icon, int level )
   {
      this.icon = icon;
      iconLevel = level;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setSmallIcon( int icon )
   {
      this.icon = icon;
      iconLevel = 0;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setSound( Uri sound )
   {
      this.sound = sound;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setTicker( CharSequence tickerText )
   {
      this.tickerText = tickerText;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setVibrate( long[] pattern )
   {
      vibrate = pattern;
      return this;
   }
   
   
   
   @Override
   public INotificationBuilder setWhen( long when )
   {
      this.when = when;
      return this;
   }
   
   
   
   @Override
   public Notification build()
   {
      final Notification notification = new Notification( icon,
                                                          tickerText,
                                                          when );
      notification.setLatestEventInfo( context,
                                       contentTitle,
                                       contentText,
                                       contentIntent );
      if ( content != null )
      {
         notification.contentView = content;
      }
      
      notification.defaults = defaults;
      notification.iconLevel = iconLevel;
      
      notification.ledARGB = lightsARGB;
      notification.ledOnMS = lightsOnMs;
      notification.ledOffMS = lightsOffMs;
      if ( areLightsUsed() )
      {
         notification.flags |= Notification.FLAG_SHOW_LIGHTS;
      }
      
      notification.number = number;
      
      notification.sound = sound;
      if ( sound != null )
      {
         notification.audioStreamType = Notification.STREAM_DEFAULT;
      }
      
      notification.vibrate = vibrate;
      
      if ( isOngoing )
      {
         notification.flags |= Notification.FLAG_ONGOING_EVENT
            | Notification.FLAG_NO_CLEAR;
      }
      if ( autoCancel )
      {
         notification.flags |= Notification.FLAG_AUTO_CANCEL;
      }
      
      return notification;
   }
   
   
   
   private boolean areLightsUsed()
   {
      return ( lightsOnMs != 0 || lightsOffMs != 0 )
         && Color.alpha( lightsARGB ) != 0;
   }
}
