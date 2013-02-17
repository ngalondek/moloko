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

package dev.drsoran.moloko.notification;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import dev.drsoran.moloko.IHandlerToken;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.Settings;
import dev.drsoran.moloko.loaders.AbstractLoader;


abstract class AbstractNotifier implements IStatusbarNotifier,
         OnLoadCompleteListener< Cursor >
{
   protected final Context context;
   
   private final IHandlerToken handlerToken = MolokoNotificationService.acquireHandlerToken();
   
   private AbstractLoader< Cursor > tasksLoaderInUse;
   
   private Cursor currentTasksCursor;
   
   
   
   protected AbstractNotifier( Context context )
   {
      this.context = context;
   }
   
   
   
   @Override
   public void shutdown()
   {
      stopLoadingTasksToNotify();
      closeCurrentCursor();
      
      handlerToken.release();
   }
   
   
   
   public IHandlerToken getHandler()
   {
      return handlerToken;
   }
   
   
   
   protected Settings getSettings()
   {
      return MolokoApp.getSettings( context );
   }
   
   
   
   public void loadTasksToNotify( int loaderId, AbstractLoader< Cursor > loader )
   {
      stopLoadingTasksToNotify();
      
      final int loaderUpdateThrottleMs = context.getResources()
                                                .getInteger( R.integer.env_loader_update_throttle_ms );
      
      tasksLoaderInUse = loader;
      tasksLoaderInUse.registerListener( loaderId, this );
      tasksLoaderInUse.setUpdateThrottle( loaderUpdateThrottleMs );
      tasksLoaderInUse.setRespectContentChanges( true );
      tasksLoaderInUse.startLoading();
   }
   
   
   
   public void stopLoadingTasksToNotify()
   {
      if ( tasksLoaderInUse != null )
      {
         tasksLoaderInUse.cancelLoad();
         tasksLoaderInUse.stopLoading();
         tasksLoaderInUse.unregisterListener( this );
         tasksLoaderInUse = null;
      }
      
      cancelHandlerMessages();
   }
   
   
   
   @Override
   public void onLoadComplete( Loader< Cursor > loader, Cursor data )
   {
      closeCurrentCursor();
      storeNewCursor( data );
   }
   
   
   
   protected Cursor getCurrentTasksCursor()
   {
      return currentTasksCursor;
   }
   
   
   
   private void storeNewCursor( Cursor cursor )
   {
      if ( currentTasksCursor == null )
      {
         currentTasksCursor = cursor;
      }
   }
   
   
   
   protected void closeCurrentCursor()
   {
      if ( currentTasksCursor != null )
      {
         currentTasksCursor.close();
         currentTasksCursor = null;
      }
   }
   
   
   
   private void cancelHandlerMessages()
   {
      handlerToken.removeRunnablesAndMessages();
   }
}
