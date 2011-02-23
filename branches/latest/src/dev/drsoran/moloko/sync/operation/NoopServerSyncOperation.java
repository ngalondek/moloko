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

package dev.drsoran.moloko.sync.operation;

import java.util.Collections;
import java.util.List;

import com.mdt.rtm.Service;
import com.mdt.rtm.ServiceException;
import com.mdt.rtm.TimeLineMethod;

import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;


public final class NoopServerSyncOperation< T extends IServerSyncable< T >>
         implements IServerSyncOperation< T >, INoopSyncOperation
{
   
   @SuppressWarnings( "rawtypes" )
   public final static NoopServerSyncOperation INSTANCE = new NoopServerSyncOperation();
   
   

   private NoopServerSyncOperation()
   {
      
   }
   


   public Op getOperationType()
   {
      return ISyncOperation.Op.NOOP;
   }
   


   public List< TimeLineMethod< T >> getMethods()
   {
      return Collections.emptyList();
   }
   


   public List< IContentProviderSyncOperation > execute() throws ServiceException
   {
      return Collections.emptyList();
   }
   


   public List< TimeLineMethod.Transaction > revert( Service service )
   {
      return Collections.emptyList();
   }
   


   public T getResultElement()
   {
      return null;
   }
   


   public IContentProviderSyncOperation removeModification( ModificationList modifications )
   {
      return NoopContentProviderSyncOperation.INSTANCE;
   }
}
