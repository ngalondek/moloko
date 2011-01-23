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

package dev.drsoran.moloko.service.sync.lists;

import java.util.Collection;
import java.util.Comparator;

import android.content.ContentProviderClient;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;


public class ContentProviderSyncableList< T extends IContentProviderSyncable< T > >
         extends SyncableList< T >
{
   private final ContentProviderClient provider;
   
   

   public ContentProviderSyncableList( ContentProviderClient provider,
      Collection< T > elements )
   {
      super( elements );
      
      if ( provider == null )
         throw new NullPointerException( "provider is null" );
      
      this.provider = provider;
   }
   


   public ContentProviderSyncableList( ContentProviderClient provider,
      Collection< T > elements, Comparator< T > comparator )
   {
      super( elements, comparator );
      
      if ( provider == null )
         throw new NullPointerException( "provider is null" );
      
      this.provider = provider;
   }
   


   @Override
   public IContentProviderSyncOperation computeInsertOperation( T newElement,
                                                                Object... params )
   {
      return newElement.computeContentProviderInsertOperation( provider, params );
   }
   


   @Override
   public IContentProviderSyncOperation computeDeleteOperation( T elementToDelete,
                                                                Object... params )
   {
      return elementToDelete.computeContentProviderDeleteOperation( provider,
                                                                    params );
   }
   


   @Override
   protected IContentProviderSyncOperation internalComputeUpdateOperation( T old,
                                                                           T updateElement,
                                                                           Object... params )
   {
      return old.computeContentProviderUpdateOperation( provider,
                                                        updateElement,
                                                        params );
   }
}
