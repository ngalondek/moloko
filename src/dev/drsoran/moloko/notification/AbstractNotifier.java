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
import android.database.ContentObserver;
import android.database.Cursor;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.notification.AbstractNotificationTasksLoader.ITasksLoadedHandler;
import dev.drsoran.moloko.util.DelayedRun;


abstract class AbstractNotifier implements IStatusbarNotifier,
         ITasksLoadedHandler
{
   protected final Context context;
   
   private final IHandlerToken handlerToken = MolokoNotificationService.acquireHandlerToken();
   
   private AbstractNotificationTasksLoader tasksLoader;
   
   private Cursor currentTasksCursor;
   
   private ContentObserver tasksContentProviderObserver;
   
   
   
   protected AbstractNotifier( Context context )
   {
      this.context = context;
      registerTasksContentProviderObserver();
   }
   
   
   
   @Override
   public void shutdown()
   {
      stopLoadingTasksToNotify();
      closeCurrentCursor();
      unregisterTasksContentProviderObserver();
      
      handlerToken.release();
   }
   
   
   
   @Override
   public void onTasksLoaded( final Cursor result )
   {
      handlerToken.post( new Runnable()
      {
         @Override
         public void run()
         {
            onFinishedLoading( result );
         }
      } );
   }
   
   
   
   protected Settings getSettings()
   {
      return MolokoApp.getSettings( context );
   }
   
   
   
   protected final void startTasksLoader( AbstractNotificationTasksLoader loader )
   {
      stopLoadingTasksToNotify();
      cancelHandlerMessages();
      
      tasksLoader = loader;
      loader.execute();
   }
   
   
   
   protected final void stopLoadingTasksToNotify()
   {
      if ( tasksLoader != null )
      {
         tasksLoader.cancel( true );
         tasksLoader = null;
      }
   }
   
   
   
   protected Cursor getCurrentTasksCursor()
   {
      return currentTasksCursor;
   }
   
   
   
   protected void releaseCurrentCursor()
   {
      closeCurrentCursor();
   }
   
   
   
   private void storeNewCursor( Cursor cursor )
   {
      if ( currentTasksCursor == null )
      {
         currentTasksCursor = cursor;
      }
   }
   
   
   
   private void closeCurrentCursor()
   {
      if ( currentTasksCursor != null )
      {
         currentTasksCursor.close();
         currentTasksCursor = null;
      }
   }
   
   
   
   private void onFinishedLoading( Cursor cursor )
   {
      closeCurrentCursor();
      storeNewCursor( cursor );
      
      onFinishedLoadingTasksToNotify( cursor );
      
      tasksLoader = null;
   }
   
   
   
   private void registerTasksContentProviderObserver()
   {
      if ( tasksContentProviderObserver == null )
      {
         tasksContentProviderObserver = new ContentObserver( null )
         {
            @Override
            public void onChange( boolean selfChange )
            {
               // Aggregate several calls to a single update.
               DelayedRun.run( handlerToken, new Runnable()
               {
                  @Override
                  public void run()
                  {
                     onDatasetChanged();
                  }
               }, 500 );
            }
         };
         
         TasksProviderPart.registerContentObserver( context,
                                                    tasksContentProviderObserver );
      }
   }
   
   
   
   private void unregisterTasksContentProviderObserver()
   {
      if ( tasksContentProviderObserver != null )
      {
         TasksProviderPart.unregisterContentObserver( context,
                                                      tasksContentProviderObserver );
         tasksContentProviderObserver = null;
      }
   }
   
   
   
   private void cancelHandlerMessages()
   {
      handlerToken.removeRunnablesAndMessages();
   }
   
   
   
   protected abstract void onFinishedLoadingTasksToNotify( Cursor cursor );
   
   
   
   protected abstract void onDatasetChanged();
}
