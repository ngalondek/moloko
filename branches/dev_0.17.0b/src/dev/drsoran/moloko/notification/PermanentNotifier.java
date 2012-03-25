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
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.IOnTimeChangedListener;


class PermanentNotifier extends AbstractNotifier
{
   private final IPermanentNotificationPresenter presenter;
   
   private String lastLoaderfilterString;
   
   
   
   public PermanentNotifier( Context context )
   {
      super( context );
      
      presenter = NotificationPresenterFactory.createPermanentNotificationPresenter( context );
      
      reEvaluatePermanentNotification();
   }
   
   
   
   @Override
   public void onTimeChanged( int which )
   {
      switch ( which )
      {
         case IOnTimeChangedListener.MIDNIGHT:
         case IOnTimeChangedListener.SYSTEM_TIME:
            reEvaluatePermanentNotification();
            break;
         
         default :
            break;
      }
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      switch ( which )
      {
         case IOnSettingsChangedListener.DATEFORMAT:
            reEvaluatePermanentNotification();
            break;
         
         case IOnSettingsChangedListener.NOTIFY_PERMANENT_RELATED:
            reEvaluatePermanentNotification();
            break;
         
         default :
            break;
      }
   }
   
   
   
   @Override
   public void onNotificationClicked( int notificationId, Intent onClickIntent )
   {
      if ( presenter.isHandlingNotification( notificationId ) )
      {
         presenter.handleNotificationClicked( notificationId );
      }
   }
   
   
   
   @Override
   public void onNotificationCleared( int notificationId, Intent onClearIntent )
   {
   }
   
   
   
   @Override
   public void shutdown()
   {
      super.shutdown();
      cancelPermanentNotification();
   }
   
   
   
   @Override
   protected void onFinishedLoadingTasksToNotify( Cursor cursor )
   {
      if ( cursor != null && cursor.moveToFirst() )
      {
         buildOrUpdatePermanentNotification( cursor );
      }
      else
      {
         cancelPermanentNotification();
      }
      
      releaseCurrentCursor();
   }
   
   
   
   @Override
   protected void onDatasetChanged()
   {
      reEvaluatePermanentNotification();
   }
   
   
   
   private void reEvaluatePermanentNotification()
   {
      final int notificationType = getSettings().getNotifyingPermanentTasksType();
      final boolean showOverDueTasks = getSettings().isNotifyingPermanentOverdueTasks();
      
      if ( notificationType == PermanentNotificationType.OFF
         && !showOverDueTasks )
      {
         stopLoadingTasksToNotify();
         cancelPermanentNotification();
         
         lastLoaderfilterString = null;
      }
      else
      {
         LoadPermanentTasksAsyncTask loader = new LoadPermanentTasksAsyncTask( context,
                                                                               getHandler(),
                                                                               notificationType,
                                                                               showOverDueTasks );
         lastLoaderfilterString = loader.getFilterString();
         
         startTasksLoader( loader );
      }
   }
   
   
   
   private void cancelPermanentNotification()
   {
      presenter.cancelNotification();
   }
   
   
   
   private void buildOrUpdatePermanentNotification( Cursor cursor )
   {
      presenter.showNotificationFor( cursor, lastLoaderfilterString );
   }
}
