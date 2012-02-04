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
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import dev.drsoran.moloko.util.DelayedRun;


abstract class AbstractNotificator implements INotifier,
         OnSharedPreferenceChangeListener
{
   protected final Context context;
   
   private AbstractNotificationTasksLoader tasksLoader;
   
   private Cursor currentTasksCursor;
   
   private ContentObserver cursorObserver;
   
   
   
   protected AbstractNotificator( Context context )
   {
      this.context = context;
      registerPreferenceListener();
   }
   
   
   
   @Override
   public void shutdown()
   {
      stopLoadingTasksToNotify();
      cancelHandlerMessages();
      closeCurrentCursor();
      unregisterPreferenceListener();
   }
   
   
   
   @Override
   public void onSharedPreferenceChanged( SharedPreferences sharedPreferences,
                                          String key )
   {
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
   
   
   
   protected Handler getHandler()
   {
      return handler;
   }
   
   
   
   private void registerPreferenceListener()
   {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         prefs.registerOnSharedPreferenceChangeListener( this );
      }
   }
   
   
   
   private void unregisterPreferenceListener()
   {
      final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences( context );
      
      if ( prefs != null )
      {
         prefs.unregisterOnSharedPreferenceChangeListener( this );
      }
   }
   
   
   
   private void storeNewCursor( Cursor cursor )
   {
      if ( currentTasksCursor == null )
      {
         currentTasksCursor = cursor;
         
         createCursorObserver();
         currentTasksCursor.registerContentObserver( cursorObserver );
      }
   }
   
   
   
   private void createCursorObserver()
   {
      cursorObserver = new ContentObserver( handler )
      {
         @Override
         public void onChange( boolean selfChange )
         {
            // Aggregate several calls to a single update.
            DelayedRun.run( handler, new Runnable()
            {
               @Override
               public void run()
               {
                  onDatasetChanged();
               }
            }, 500 );
         }
      };
   }
   
   
   
   private void closeCurrentCursor()
   {
      if ( currentTasksCursor != null )
      {
         currentTasksCursor.unregisterContentObserver( cursorObserver );
         deleteCursorObserver();
         
         currentTasksCursor.close();
         currentTasksCursor = null;
      }
   }
   
   
   
   private void deleteCursorObserver()
   {
      cursorObserver = null;
   }
   
   
   
   private void onFinishedLoading( Cursor cursor )
   {
      closeCurrentCursor();
      storeNewCursor( cursor );
      
      onFinishedLoadingTasksToNotify( cursor );
      
      tasksLoader = null;
   }
   
   
   
   private void cancelHandlerMessages()
   {
      handler.removeMessages( AbstractNotificationTasksLoader.MSG_TASKS_LOADED_ASYNC );
   }
   
   private final Handler handler = new Handler()
   {
      @Override
      public void handleMessage( Message msg )
      {
         if ( msg.what == AbstractNotificationTasksLoader.MSG_TASKS_LOADED_ASYNC )
         {
            onFinishedLoading( (Cursor) msg.obj );
         }
         else
         {
            super.handleMessage( msg );
         }
      }
   };
   
   
   
   protected abstract void onFinishedLoadingTasksToNotify( Cursor cursor );
   
   
   
   protected abstract void onDatasetChanged();
}
