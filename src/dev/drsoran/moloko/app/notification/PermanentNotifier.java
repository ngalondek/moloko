/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

import java.util.Collection;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.Loader;
import dev.drsoran.moloko.app.IOnTimeChangedListener;
import dev.drsoran.moloko.app.MolokoApp;
import dev.drsoran.moloko.app.settings.IOnSettingsChangedListener;
import dev.drsoran.moloko.app.settings.PermanentNotificationType;
import dev.drsoran.moloko.app.settings.Settings;
import dev.drsoran.moloko.loaders.AbstractLoader;


class PermanentNotifier extends AbstractNotifier
{
   private final IPermanentNotificationPresenter presenter;
   
   private AbstractLoader< Cursor > tasksLoader;
   
   
   
   public PermanentNotifier( Context context )
   {
      super( context );
      
      presenter = NotificationPresenterFactory.createPermanentNotificationPresenter( context );
      checkPermanentNotificationActiveState();
   }
   
   
   
   @Override
   public void onTimeChanged( int which )
   {
      if ( isNotificationActive() )
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
   }
   
   
   
   @Override
   public void onSettingsChanged( int which )
   {
      switch ( which )
      {
         case IOnSettingsChangedListener.DATEFORMAT:
            if ( isNotificationActive() )
            {
               reEvaluatePermanentNotification();
            }
            break;
         
         case IOnSettingsChangedListener.NOTIFY_PERMANENT_RELATED:
            checkPermanentNotificationActiveState();
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
      shutdownLoader();
      cancelPermanentNotification();
      
      super.shutdown();
   }
   
   
   
   private void reEvaluatePermanentNotification()
   {
      final Settings settings = MolokoApp.getSettings( context );
      loadPermanentTasks( settings.getNotifyingPermanentTaskLists() );
   }
   
   
   
   private void checkPermanentNotificationActiveState()
   {
      final boolean isNotifyingPermanentTasks = isNotificationActive();
      
      if ( !isNotifyingPermanentTasks && tasksLoader != null )
      {
         shutdownLoader();
         cancelPermanentNotification();
      }
      else if ( isNotifyingPermanentTasks && tasksLoader == null )
      {
         reEvaluatePermanentNotification();
      }
   }
   
   
   
   private boolean isNotificationActive()
   {
      final Settings settings = MolokoApp.getSettings( context );
      final boolean isNotifyingPermanentTasks = settings.isNotifyingPermanentTasks();
      
      return isNotifyingPermanentTasks;
   }
   
   
   
   private void loadPermanentTasks( Map< PermanentNotificationType, Collection< String >> permanentTaskLists )
   {
      tasksLoader = new PermanentNotifierTasksLoader( context,
                                                      permanentTaskLists );
      loadTasksToNotify( PermanentNotifierTasksLoader.ID, tasksLoader );
   }
   
   
   
   @Override
   public void onLoadComplete( Loader< Cursor > loader, Cursor data )
   {
      super.onLoadComplete( loader, data );
      
      if ( loader.getId() == PermanentNotifierTasksLoader.ID )
      {
         final String filterString = ( (AbstractFilterBasedNotificationTasksLoader) loader ).getFilterString();
         
         getHandler().post( new Runnable()
         {
            @Override
            public void run()
            {
               onFinishedLoadingTasksToNotify( filterString );
            }
         } );
      }
      else
      {
         throw new IllegalArgumentException( String.format( "Unexpected Loader completed load. Expected '%s' but was '%s'.",
                                                            PermanentNotifierTasksLoader.class.getSimpleName(),
                                                            loader.getClass()
                                                                  .getName() ) );
      }
   }
   
   
   
   private void onFinishedLoadingTasksToNotify( String filterString )
   {
      final Cursor cursor = getCurrentTasksCursor();
      
      if ( cursor != null && cursor.moveToFirst() )
      {
         buildOrUpdatePermanentNotification( filterString, cursor );
      }
      else
      {
         cancelPermanentNotification();
      }
      
      closeCurrentCursor();
   }
   
   
   
   private void shutdownLoader()
   {
      if ( tasksLoader != null )
      {
         stopLoadingTasksToNotify();
         tasksLoader = null;
      }
   }
   
   
   
   private void cancelPermanentNotification()
   {
      presenter.cancelNotification();
   }
   
   
   
   private void buildOrUpdatePermanentNotification( String filterString,
                                                    Cursor cursor )
   {
      presenter.showNotificationFor( cursor, filterString );
   }
}
