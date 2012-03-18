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

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import dev.drsoran.moloko.R;


class StackedDueTaskNotificationPresenter extends
         DueTaskNotificationPresenterBase
{
   private final static int ID = R.id.notification_due_tasks_stacked;
   
   private boolean isNotificationShown;
   
   
   
   public StackedDueTaskNotificationPresenter( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   public void showNotificationsFor( Cursor tasksCursor, int endIndex )
   {
      updateOrLaunchNotification( tasksCursor, endIndex );
   }
   
   
   
   @Override
   public void cancelNotifications()
   {
      if ( isNotificationShown )
      {
         getNotificationManager().cancel( ID );
         isNotificationShown = false;
      }
   }
   
   
   
   private void updateOrLaunchNotification( Cursor tasksCursor, int endIndex )
   {
      final int indexOfLastDueTask = endIndex - 1;
      tasksCursor.moveToPosition( indexOfLastDueTask );
      
      final int count = endIndex;
      final String title = getNotificationTitle( tasksCursor );
      final String text = getNotificationText( tasksCursor );
      final String ticker = getNotificationTicker( tasksCursor );
      final Intent onClickIntent = createOnClickIntent( tasksCursor, count );
      
      final INotificationBuilder builder;
      
      if ( count > 1 )
      {
         builder = newStackedNotification( title,
                                           text,
                                           ticker,
                                           count,
                                           onClickIntent );
      }
      else
      {
         builder = newSingletonNotification( title,
                                             text,
                                             ticker,
                                             count,
                                             onClickIntent );
      }
      
      getNotificationManager().notify( ID, builder.build() );
      isNotificationShown = true;
   }
   
   
   
   private INotificationBuilder newSingletonNotification( String title,
                                                          String text,
                                                          String ticker,
                                                          int count,
                                                          Intent onClickIntent )
   {
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            ticker,
                                                                            count,
                                                                            onClickIntent );
      
      final Bitmap largeIcon = BitmapFactory.decodeResource( getContext().getResources(),
                                                             R.drawable.ic_notify_due_task_expanded );
      builder.setLargeIcon( largeIcon );
      
      return builder;
   }
   
   
   
   private INotificationBuilder newStackedNotification( String title,
                                                        String text,
                                                        String ticker,
                                                        int count,
                                                        Intent onClickIntent )
   {
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            ticker,
                                                                            count,
                                                                            onClickIntent );
      
      final Bitmap largeIcon = BitmapFactory.decodeResource( getContext().getResources(),
                                                             R.drawable.ic_notify_due_task_expanded_stacked );
      builder.setLargeIcon( largeIcon );
      builder.setNumber( count );
      
      return builder;
   }
}
