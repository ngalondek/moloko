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

package dev.drsoran.moloko.sync.lists;

import java.util.Collection;
import java.util.Comparator;

import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;


public class ContentProviderSyncableList< T extends IContentProviderSyncable< T > >
         extends SyncableList< T, IContentProviderSyncOperation >
{
   public ContentProviderSyncableList( Collection< T > elements )
   {
      super( elements );
   }
   


   public ContentProviderSyncableList( Collection< T > elements,
      Comparator< T > comparator )
   {
      super( elements, comparator );
   }
   


   @Override
   public IContentProviderSyncOperation computeInsertOperation( T newElement )
   {
      return newElement.computeContentProviderInsertOperation();
   }
   


   @Override
   public IContentProviderSyncOperation computeDeleteOperation( T elementToDelete )
   {
      return elementToDelete.computeContentProviderDeleteOperation();
   }
   


   @Override
   public IContentProviderSyncOperation computeUpdateOperation( int pos,
                                                                T updateElement )
   {
      return super.computeUpdateOperation( pos, updateElement );
   }
   


   @Override
   protected IContentProviderSyncOperation internalComputeUpdateOperation( T old,
                                                                           T updateElement )
   {
      return old.computeContentProviderUpdateOperation( updateElement );
   }
}
