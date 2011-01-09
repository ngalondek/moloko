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

package dev.drsoran.moloko.service.sync.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import dev.drsoran.moloko.service.sync.lists.SyncableList;
import dev.drsoran.moloko.service.sync.operation.ISyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopSyncOperation;


public class SyncDiffer
{
   @SuppressWarnings( "unchecked" )
   public final static < O extends ISyncOperation, T > ArrayList< O > diff( Collection< T > reference,
                                                                            SyncableList< T > target,
                                                                            Object... params ) throws NullPointerException
   {
      if ( reference == null || target == null )
         throw new NullPointerException();
      
      boolean ok = true;
      
      ArrayList< O > operations = new ArrayList< O >();
      
      // for each element of the reference list
      for ( Iterator< T > iterator = reference.iterator(); ok
         && iterator.hasNext(); )
      {
         final T refElement = iterator.next();
         
         final int pos = target.find( refElement );
         
         O operation = null;
         
         // INSERT: The reference element is not contained in the target list.
         if ( pos == -1 )
         {
            operation = (O) target.computeInsertOperation( refElement, params );
         }
         
         // UPDATE: The reference element is contained in the target list.
         else
         {
            operation = (O) target.computeUpdateOperation( pos,
                                                           refElement,
                                                           params );
         }
         
         ok = operation != null;
         
         if ( ok && !( operation instanceof NoopSyncOperation ) )
            operations.add( operation );
      }
      
      if ( ok )
      {
         // DELETE: Get all elements which have not been touched during the
         // diff.
         // These elements are not in the reference list.
         final ArrayList< T > untouchedElements = target.getUntouchedElements();
         
         for ( T tgtElement : untouchedElements )
         {
            final O operation = (O) target.computeDeleteOperation( tgtElement,
                                                                   params );
            
            ok = operation != null;
            
            if ( ok && !( operation instanceof NoopSyncOperation ) )
               operations.add( operation );
         }
      }
      
      if ( !ok )
         operations = null;
      
      return operations;
   }
   
}
