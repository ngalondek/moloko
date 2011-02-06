/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderOperation;


public final class NoopContentProviderSyncOperation implements
         IContentProviderSyncOperation, INoopSyncOperation
{
   public final static NoopContentProviderSyncOperation INSTANCE = new NoopContentProviderSyncOperation();
   
   

   private NoopContentProviderSyncOperation()
   {
      
   }
   


   public int getBatch( ArrayList< ContentProviderOperation > batch )
   {
      return 0;
   }
   


   public int getOperationType()
   {
      return ISyncOperation.Op.NOOP;
   }
   


   public int getBatch( List< ContentProviderOperation > batch )
   {
      return 0;
   }
   


   public int size()
   {
      return 0;
   }
}
