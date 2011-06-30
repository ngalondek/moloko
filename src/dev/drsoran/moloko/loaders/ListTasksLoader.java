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

import android.content.Context;
import android.database.ContentObserver;
import android.support.v4.content.AsyncTaskLoader;
import dev.drsoran.moloko.content.TasksProviderPart;
import dev.drsoran.rtm.ListTask;
import dev.drsoran.rtm.Task;


public class ListTasksLoader extends AsyncTaskLoader< List< ListTask > >
{
   private final ForceLoadContentObserver observer = new ForceLoadContentObserver();
   
   private volatile List< ListTask > tasks;
   
   

   public ListTasksLoader( Context context )
   {
      super( context );
   }
   


   @Override
   public List< ListTask > loadInBackground()
   {
      final List< Task > tasks = TasksProviderPart.getTasks( client,
                                                             selection,
                                                             order );
      
      return ListTask.fromTaskList( tasks );
   }
   


   /* Runs on the UI thread */
   @Override
   public void deliverResult( List< ListTask > tasks )
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
    * 
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
   public void onCanceled( List< ListTask > tasks )
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
   


   private void unregisterContentObserver( ContentObserver observer )
   {
      TasksProviderPart.unregisterContentObserver( getContext(), observer );
   }
}
