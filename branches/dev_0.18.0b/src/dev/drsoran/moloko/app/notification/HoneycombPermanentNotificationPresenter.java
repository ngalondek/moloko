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

import android.accounts.Account;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.Intents;
import dev.drsoran.moloko.app.home.HomeActivity;
import dev.drsoran.moloko.app.services.IAccountService;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


class HoneycombPermanentNotificationPresenter extends
         AbstractPermanentNotificationPresenter
{
   private Intent[] onClickIntentStack;
   
   
   
   public HoneycombPermanentNotificationPresenter( AppContext context )
   {
      super( context );
   }
   
   
   
   @Override
   public void handleNotificationClicked( int notificationId )
   {
      getContext().startActivities( onClickIntentStack );
   }
   
   
   
   @Override
   public void cancelNotification()
   {
      super.cancelNotification();
      onClickIntentStack = null;
   }
   
   
   
   @Override
   protected Notification newNotfication( String title,
                                          String text,
                                          Cursor tasksCursor,
                                          String filterString )
   {
      createOnClickIntentStack( tasksCursor, title, filterString );
      
      final int tasksCount = tasksCursor.getCount();
      
      if ( tasksCount > 1 )
      {
         return newStackedNotification( title, text, tasksCount );
      }
      else
      {
         return newSingletonNotification( title, text, tasksCursor );
      }
   }
   
   
   
   private void createOnClickIntentStack( Cursor tasksCursor,
                                          String title,
                                          String filterString )
   {
      if ( tasksCursor.getCount() == 1 )
      {
         final Intent notifyingTaskIntent = createSingletonOnClickIntent( tasksCursor );
         onClickIntentStack = makeSingleNotificationActivityStack( notifyingTaskIntent,
                                                                   DbUtils.getOptString( tasksCursor,
                                                                                         getColumnIndex( Tasks.LIST_ID ) ) );
      }
      else
      {
         final Intent notifyingTasksIntent = createMultiTasksOnClickIntent( tasksCursor,
                                                                            title,
                                                                            filterString );
         onClickIntentStack = makeStackedNotificationActivityStack( notifyingTasksIntent );
      }
   }
   
   
   
   private Intent[] makeSingleNotificationActivityStack( Intent notificationTargetIntent,
                                                         String listIdOfTask )
   {
      final Intent[] intentStack = new Intent[ 4 ];
      
      intentStack[ 0 ] = Intent.makeRestartActivityTask( new ComponentName( getContext(),
                                                                            HomeActivity.class ) );
      intentStack[ 1 ] = Intents.createOpenListOverviewsIntent();
      intentStack[ 2 ] = Intents.createOpenListIntentById( getContext(),
                                                           listIdOfTask,
                                                           null );
      intentStack[ 3 ] = notificationTargetIntent;
      
      return intentStack;
   }
   
   
   
   private Intent[] makeStackedNotificationActivityStack( Intent notificationTargetIntent )
   {
      final Intent[] intentStack = new Intent[ 2 ];
      
      intentStack[ 0 ] = Intent.makeRestartActivityTask( new ComponentName( getContext(),
                                                                            HomeActivity.class ) );
      intentStack[ 1 ] = notificationTargetIntent;
      
      return intentStack;
   }
   
   
   
   private Notification newSingletonNotification( String title,
                                                  String text,
                                                  Cursor tasksCursor )
   {
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            1 );
      
      final Bitmap largeIcon = BitmapFactory.decodeResource( getContext().getResources(),
                                                             R.drawable.ic_notify_permanent_expanded );
      builder.setLargeIcon( largeIcon );
      
      final IAccountService accountService = getContext().getAccountService();
      final Account account = accountService.getRtmAccount();
      
      if ( accountService.isWriteableAccess( account ) )
      {
         addNotificationActions( tasksCursor, builder );
      }
      
      return builder.build();
   }
   
   
   
   private void addNotificationActions( Cursor tasksCursor,
                                        INotificationBuilder builder )
   {
      tasksCursor.moveToFirst();
      final Task task = TasksProviderPart.createTask( tasksCursor );
      
      builder.addAction( R.drawable.ic_menu_complete,
                         getContext().getString( R.string.app_task_complete ),
                         Intents.createTaskCompletedFromNotificationIntent( getContext(),
                                                                            task ) );
      
      builder.addAction( R.drawable.ic_menu_postponed,
                         getContext().getString( R.string.app_task_postpone ),
                         Intents.createTaskPostponedFromNotificationIntent( getContext(),
                                                                            task ) );
   }
   
   
   
   private Notification newStackedNotification( String title,
                                                String text,
                                                int count )
   {
      final INotificationBuilder builder = createDefaultInitializedBuilder( title,
                                                                            text,
                                                                            count );
      final Bitmap largeIcon = BitmapFactory.decodeResource( getContext().getResources(),
                                                             R.drawable.ic_notify_permanent_expanded_stacked );
      builder.setLargeIcon( largeIcon );
      
      return builder.build();
   }
}
