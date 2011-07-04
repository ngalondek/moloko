/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.loaders;

import java.util.List;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.provider.Rtm.Tasks;
import dev.drsoran.rtm.Task;


public abstract class AbstractTasksListLoader< T extends Task > extends
         AsyncTaskLoader< List< T > >
{
   private final ForceLoadContentObserver observer = new ForceLoadContentObserver();
   
   private volatile List< T > tasks;
   
   

   protected AbstractTasksListLoader( Context context )
   {
      super( context );
   }
   


   @Override
   public List< T > loadInBackground()
   {
      List< T > result = null;
      
      final ContentProviderClient client = getContentProviderClient();
      
      if ( client != null )
      {
         result = queryResultInBackground( client );
         
         client.release();
         
         if ( result != null )
            registerContentObserver( observer );
      }
      else
      {
         LogUtils.logDBError( getContext(),
                              LogUtils.toTag( ListTasksLoader.class ),
                              LogUtils.GENERIC_DB_ERROR );
      }
      
      return result;
   }
   


   abstract protected List< T > queryResultInBackground( ContentProviderClient client );
   


   Uri getContentUri()
   {
      return Tasks.CONTENT_URI;
   }
   


   protected ContentProviderClient getContentProviderClient()
   {
      return getContext().getContentResolver()
                         .acquireContentProviderClient( getContentUri() );
   }
   


   /**
    * Runs on the UI thread
    */
   @Override
   public void deliverResult( List< T > tasks )
   {
      if ( isReset() )
      {
         // An async query came in while the loader is stopped
         if ( tasks != null )
         {
            tasks.clear();
         }
      }
      else
      {
         this.tasks = tasks;
         
         if ( isStarted() )
         {
            super.deliverResult( this.tasks );
         }
      }
   }
   


   /**
    * Must be called from the UI thread
    */
   @Override
   protected void onStartLoading()
   {
      if ( tasks != null )
      {
         deliverResult( tasks );
      }
      
      if ( takeContentChanged() || tasks == null )
      {
         forceLoad();
      }
   }
   


   /**
    * Must be called from the UI thread
    */
   @Override
   protected void onStopLoading()
   {
      // Attempt to cancel the current load task if possible.
      cancelLoad();
   }
   


   @Override
   public void onCanceled( List< T > tasks )
   {
      if ( tasks != null )
      {
         tasks.clear();
      }
   }
   


   @Override
   protected void onReset()
   {
      super.onReset();
      
      // Ensure the loader is stopped
      onStopLoading();
      
      tasks = null;
   }
   


   private void registerContentObserver( ContentObserver observer )
   {
      TasksProviderPart.registerContentObserver( getContext(), observer );
   }
}
