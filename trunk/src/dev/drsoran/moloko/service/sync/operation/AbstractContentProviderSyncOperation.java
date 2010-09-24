/*
Copyright (c) 2010 Ronny Röhricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
*/

package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.SyncResult;


public abstract class AbstractContentProviderSyncOperation implements
         IContentProviderSyncOperation
{
   private final static class NopSyncOperation implements
            IContentProviderSyncOperation
   {
      
      public int getBatch( ArrayList< ContentProviderOperation > batch )
      {
         return 0;
      }
      


      public int getOperationType()
      {
         return Op.NOOP;
      }
      


      public boolean execute( SyncResult result )
      {
         return true;
      }
      
   }
   
   public final static NopSyncOperation NOOP = new NopSyncOperation();
   
   protected final int operationType;
   
   protected final ContentProviderClient provider;
   
   

   public AbstractContentProviderSyncOperation( ContentProviderClient provider,
      int operationType ) throws NullPointerException
   {
      if ( provider == null )
         throw new NullPointerException();
      
      this.provider = provider;
      this.operationType = operationType;
   }
   


   public int getOperationType()
   {
      return operationType;
   }
   


   public final static void updateSyncResult( SyncResult result,
                                              int operationType,
                                              int count )
   {
      switch ( operationType )
      {
         case Op.INSERT:
            result.stats.numInserts += count;
            break;
         case Op.UPDATE:
            result.stats.numUpdates += count;
            break;
         case Op.DELETE:
            result.stats.numDeletes += count;
            break;
         default :
            break;
      }
   }
   
}
