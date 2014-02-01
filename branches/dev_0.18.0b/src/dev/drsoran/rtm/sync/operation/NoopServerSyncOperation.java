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
import java.util.Map;

import com.mdt.rtm.Service;
import com.mdt.rtm.TimeLineMethod;

import dev.drsoran.moloko.domain.services.ContentRepository;


public final class NoopServerSyncOperation< T > implements
         IRtmSyncOperation< T >, INoopSyncOperation
{
   
   private NoopServerSyncOperation()
   {
   }
   


   public SyncOperationType getOperationType()
   {
      return SyncOperationType.Noop;
   }
   


   public Map< TimeLineMethod< T >, List< Modification > > getMethods()
   {
      return Collections.emptyMap();
   }
   


   public T execute( ContentRepository rtmProvider ) throws ServiceException
   {
      return null;
   }
   


   public List< TimeLineResult.Transaction > revert( Service service )
   {
      return Collections.emptyList();
   }
   


   public T getResultElement()
   {
      return null;
   }
   


   public IContentProviderSyncOperation removeModification( ModificationSet modifications )
   {
      return NoopContentProviderSyncOperation.INSTANCE;
   }
   


   public void addMethod( TimeLineMethod< T > method )
   {
      
   }
   


   public final static < T > NoopServerSyncOperation< T > newInstance()
   {
      return new NoopServerSyncOperation< T >();
   }
   


   public IContentProviderSyncOperation removeModifications( ModificationSet modifictaions,
                                                             boolean revert )
   {
      return NoopContentProviderSyncOperation.INSTANCE;
   }
}
