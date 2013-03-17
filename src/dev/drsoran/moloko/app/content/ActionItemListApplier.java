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

package dev.drsoran.moloko.app.content;

import java.util.concurrent.ExecutionException;

import android.accounts.Account;
import android.os.AsyncTask;
import dev.drsoran.moloko.app.AppContext;
import dev.drsoran.moloko.app.ReadOnlyAccountException;
import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.moloko.domain.services.ContentException;


public class ActionItemListApplier
{
   private final AppContext context;
   
   
   
   public ActionItemListApplier( AppContext context )
   {
      this.context = context;
   }
   
   
   
   public void applySync( ContentProviderActionItemList actionItems ) throws ContentException,
                                                                     ReadOnlyAccountException
   {
      try
      {
         if ( actionItems.size() > 0 )
         {
            validatePreconditions();
            
            final ApplyContentProviderActionItemsTask task = new ApplyContentProviderActionItemsTask( context );
            context.getExecutorService().execute( task, actionItems ).get();
            
            if ( task.hasContentException() )
            {
               throw task.getContentException();
            }
         }
      }
      catch ( InterruptedException e )
      {
         LogError( e );
         throw new ContentException( e );
      }
      catch ( ExecutionException e )
      {
         LogError( e );
         throw new ContentException( e );
      }
   }
   
   
   
   public AsyncTask< ContentProviderActionItemList, Void, Void > applyAsync( ContentProviderActionItemList actionItems )
   {
      return context.getExecutorService()
                    .execute( new ApplyContentProviderActionItemsTask( context ),
                              actionItems );
   }
   
   
   
   private void validatePreconditions() throws ReadOnlyAccountException
   {
      final Account account = context.getAccountService().getRtmAccount();
      
      if ( context.getAccountService().isReadOnlyAccess( account ) )
      {
         throw new ReadOnlyAccountException();
      }
   }
   
   
   
   private void LogError( Exception e )
   {
      context.Log().e( DbUtils.class,
                       "Applying ContentProviderActionItemList failed",
                       e );
   }
}
