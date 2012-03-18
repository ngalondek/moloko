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
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.Intents;


class HoneycombPermanentNotificationPresenter extends
         AbstractPermanentNotificationPresenter
{
   
   public HoneycombPermanentNotificationPresenter( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   protected Notification newNotfication( String title,
                                          String text,
                                          int count,
                                          Intent onClickIntent )
   {
      if ( count > 1 )
      {
         return newStackedNotification( title, text, count, onClickIntent );
      }
      else
      {
         return newSingletonNotification( title, text, count, onClickIntent );
      }
   }
   
   
   
   private Notification newSingletonNotification( String title,
                                                  String text,
                                                  int count,
                                                  Intent onClickIntent )
   {
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            count,
                                                                            onClickIntent );
      
      final Bitmap largeIcon = BitmapFactory.decodeResource( getContext().getResources(),
                                                             R.drawable.ic_notify_permanent_expanded );
      builder.setLargeIcon( largeIcon );
      
      return builder.build();
   }
   
   
   
   private Notification newStackedNotification( String title,
                                                String text,
                                                int count,
                                                Intent onClickIntent )
   {
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            count,
                                                                            onClickIntent );
      final Bitmap largeIcon = BitmapFactory.decodeResource( getContext().getResources(),
                                                             R.drawable.ic_notify_permanent_expanded_stacked );
      builder.setLargeIcon( largeIcon );
      
      return builder.build();
   }
   
   
   
   private INotificationBuilder createDefaultInitializedBuilder( String title,
                                                                 String text,
                                                                 int count,
                                                                 Intent onClickIntent )
   {
      final INotificationBuilder builder = NotificationBuilderFactory.create( getContext() );
      
      builder.setOngoing( true );
      builder.setSmallIcon( R.drawable.notification_permanent, count );
      builder.setContentTitle( title );
      builder.setContentText( text );
      builder.setContentIntent( Intents.createPermanentNotificationIntent( getContext(),
                                                                           onClickIntent ) );
      
      return builder;
   }
}
