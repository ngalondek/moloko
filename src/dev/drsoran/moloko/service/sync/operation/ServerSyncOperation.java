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

package dev.drsoran.moloko.service.sync.operation;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.TimeLineMethod;
import com.mdt.rtm.Service.MethodCallType;

import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable;
import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable.MergeDirection;


public class ServerSyncOperation< T extends ITwoWaySyncable< T > > implements
         IServerSyncOperation
{
   private final int operationType;
   
   private final List< TimeLineMethod< T > > serviceMethods;
   
   

   private ServerSyncOperation( int operation,
      List< TimeLineMethod< T > > serviceMethods )
   {
      if ( serviceMethods == null )
         throw new NullPointerException( "serviceMethods is null" );
      
      this.operationType = operation;
      this.serviceMethods = Collections.unmodifiableList( serviceMethods );
   }
   


   public List< IContentProviderSyncOperation > execute() throws ServiceException
   {
      T element = null;
      
      for ( Iterator< TimeLineMethod< T > > i = serviceMethods.iterator(); i.hasNext(); )
      {
         final TimeLineMethod< T > method = i.next();
         
         // We retrieve the result only for the last element.
         element = method.call( i.hasNext() ? MethodCallType.NO_RESULT
                                           : MethodCallType.WITH_RESULT );
      }
      
      if ( element != null )
      {
         final DirectedSyncOperations ops = element.computeMergeOperations( null,
                                                                            null,
                                                                            element,
                                                                            MergeDirection.LOCAL );
         return ops.getLocalOperations();
      }
      else
      {
         return null;
      }
   }
   


   public int getOperationType()
   {
      return operationType;
   }
   


   public final static < T extends ITwoWaySyncable< T >> ServerSyncOperation< T > newInsert( List< TimeLineMethod< T > > methods )
   {
      return new ServerSyncOperation< T >( Op.INSERT, methods );
   }
   


   public final static < T extends ITwoWaySyncable< T >> ServerSyncOperation< T > newUpate( List< TimeLineMethod< T > > methods )
   {
      return new ServerSyncOperation< T >( Op.UPDATE, methods );
   }
   


   public final static < T extends ITwoWaySyncable< T >> ServerSyncOperation< T > newDelete( List< TimeLineMethod< T > > methods )
   {
      return new ServerSyncOperation< T >( Op.DELETE, methods );
   }
}
