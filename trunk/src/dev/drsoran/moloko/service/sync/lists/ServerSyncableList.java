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

package dev.drsoran.moloko.service.sync.lists;

import java.util.Collection;
import java.util.Comparator;

import com.mdt.rtm.Service;

import dev.drsoran.moloko.service.sync.operation.ISyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IServerSyncable;

// TODO: Replace ISyncOperation parameter by more special one.
public class ServerSyncableList< T extends IServerSyncable< T > > extends
         SyncableList< ISyncOperation, T >
{
   
   private final Service service;
   
   

   public ServerSyncableList( Service service, Collection< T > elements )
      throws NullPointerException
   {
      super( elements );
      
      if ( service == null )
         throw new NullPointerException();
      
      this.service = service;
   }
   


   public ServerSyncableList( Service service, Collection< T > elements,
      Comparator< T > comparator ) throws NullPointerException
   {
      super( elements, comparator );
      
      if ( service == null )
         throw new NullPointerException();
      
      this.service = service;
   }
   


   @Override
   public ISyncOperation computeInsertOperation( T newElement, Object... params )
   {
      return newElement.computeServerInsertOperation( service, params );
   }
   


   @Override
   public ISyncOperation computeDeleteOperation( T elementToDelete,
                                                 Object... params )
   {
      return elementToDelete.computeServerDeleteOperation( service, params );
   }
   


   @Override
   protected ISyncOperation internalComputeUpdateOperation( T old,
                                                            T updateElement,
                                                            Object... params )
   {
      return old.computeServerUpdateOperation( service, updateElement, params );
   }
}
