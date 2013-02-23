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

package dev.drsoran.moloko.content;

import java.util.concurrent.ExecutionException;

import android.content.Context;
import dev.drsoran.moloko.app.IExecutorService;
import dev.drsoran.moloko.app.MolokoApp;
import dev.drsoran.moloko.util.Queries;


public class ActionItemListApplier
{
   private final Context context;
   
   private final IExecutorService executorService;
   
   
   
   public ActionItemListApplier( Context context,
      IExecutorService executorService )
   {
      this.context = context;
      this.executorService = executorService;
   }
   
   
   
   public void apply( ContentProviderActionItemList actionItems )
   {
      try
      {
         if ( actionItems.size() > 0 )
         {
            executorService.execute( new ApplyContentProviderActionItemsTask( context ),
                                     actionItems )
                           .get();
         }
      }
      catch ( InterruptedException e )
      {
         LogError( e );
         throw new RuntimeException( e );
      }
      catch ( ExecutionException e )
      {
         LogError( e );
         throw new RuntimeException( e );
      }
   }
   
   
   
   public boolean applyNonThrowing( ContentProviderActionItemList actionItems )
   {
      try
      {
         if ( actionItems.size() > 0 )
         {
            executorService.execute( new ApplyContentProviderActionItemsTask( context ),
                                     actionItems )
                           .get();
         }
         
         return true;
      }
      catch ( InterruptedException e )
      {
         LogError( e );
         return false;
      }
      catch ( ExecutionException e )
      {
         LogError( e );
         return false;
      }
   }
   
   
   
   public void applyInBackground( ContentProviderActionItemList actionItems )
   {
      if ( actionItems.size() > 0 )
      {
         executorService.execute( new ApplyContentProviderActionItemsTask( context ),
                                  actionItems );
      }
   }
   
   
   
   private void LogError( Exception e )
   {
      MolokoApp.Log.e( Queries.class,
                       "Applying ContentProviderActionItemList failed",
                       e );
   }
}
