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

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.Intents;


class DueTaskNotification
{
   private final static long[] VIBRATE_PATTERN =
   { 0, 300 };
   
   private final static int ID = R.id.notification_due_tasks;
   
   private final Context context;
   
   private INotificationBuilder builder;
   
   
   
   public DueTaskNotification( Context context )
   {
      this.context = context;
      createInitialNotificationBuilder();
   }
   
   
   
   public void update( String title,
                       String text,
                       String tickerText,
                       int count,
                       Intent onClickIntent )
   {
      final RemoteViews contentView = createContent( title, text, count );
      
      builder.setTicker( tickerText );
      builder.setSmallIcon( R.drawable.notification_due_task, count );
      builder.setContent( contentView );
      builder.setContentIntent( Intents.createDueTasksNotificationIntent( context,
                                                                          onClickIntent ) );
      getNotificationManager().notify( ID, builder.build() );
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
   
   
   
   private void createInitialNotificationBuilder()
   {
      builder = NotificationBuilderFactory.create( context );
      builder.setAutoCancel( true );
   }
   
   
   
   private RemoteViews createContent( String title, String text, int count )
   {
      final RemoteViews contentView = new RemoteViews( context.getPackageName(),
                                                       R.layout.notification );
      contentView.setImageViewResource( android.R.id.icon,
                                        R.drawable.ic_notify_due_task_expanded );
      contentView.setTextViewText( android.R.id.title, title );
      contentView.setTextViewText( android.R.id.text1, text );
      
      if ( count > 1 )
      {
         contentView.setViewVisibility( R.id.stackCount, View.VISIBLE );
         contentView.setTextViewText( R.id.stackCount,
                                      context.getString( R.string.notification_due_stack_counter,
                                                         count - 1 ) );
      }
      else
      {
         contentView.setViewVisibility( R.id.stackCount, View.GONE );
      }
      
      return contentView;
   }
   
   
   
   private void setVibrate( boolean vibrate )
   {
      if ( vibrate )
      {
         builder.setVibrate( VIBRATE_PATTERN );
      }
      else
      {
         builder.setVibrate( null );
      }
   }
   
   
   
   private void setLed( boolean useLed )
   {
      if ( useLed )
      {
         builder.setLights( Color.BLUE, 400, 500 );
      }
      else
      {
         builder.setLights( 0, 0, 0 );
      }
   }
   
   
   
   private void setSound( Uri soundToPay )
   {
      builder.setSound( soundToPay );
   }
   
   
   
   private NotificationManager getNotificationManager()
   {
      return (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
   }
}
