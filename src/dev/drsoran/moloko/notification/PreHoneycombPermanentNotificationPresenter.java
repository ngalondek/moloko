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
import android.database.Cursor;


class PreHoneycombPermanentNotificationPresenter extends
         AbstractPermanentNotificationPresenter
{
   private Intent onClickIntent;
   
   
   
   public PreHoneycombPermanentNotificationPresenter( Context context )
   {
      super( context );
   }
   
   
   
   @Override
   public void handleNotificationClicked( int notificationId )
   {
      startActivity( onClickIntent );
   }
   
   
   
   @Override
   public void cancelNotification()
   {
      super.cancelNotification();
      onClickIntent = null;
   }
   
   
   
   @Override
   protected Notification newNotfication( String title,
                                          String text,
                                          Cursor tasksCursor,
                                          String filterString )
   {
      createOnClickIntent( tasksCursor, title, filterString );
      
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            tasksCursor.getCount() );
      return builder.build();
   }
   
   
   
   private void createOnClickIntent( Cursor tasksCursor,
                                     String activityTitle,
                                     String filterString )
   {
      final int numTasks = tasksCursor.getCount();
      
      if ( numTasks == 1 )
      {
         onClickIntent = createSingletonOnClickIntent( tasksCursor );
      }
      else
      {
         onClickIntent = createMultiTasksOnClickIntent( tasksCursor,
                                                        activityTitle,
                                                        filterString );
      }
   }
   
   
   
   private void startActivity( Intent intent )
   {
      intent.setFlags( intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TASK
         | Intent.FLAG_ACTIVITY_NEW_TASK );
      
      getContext().startActivity( intent );
   }
}
