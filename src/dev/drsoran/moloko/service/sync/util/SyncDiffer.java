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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentProviderClient;

import com.mdt.rtm.Service;

import dev.drsoran.moloko.service.sync.lists.SyncableList;
import dev.drsoran.moloko.service.sync.operation.ISyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable;


public class SyncDiffer
{
   static class DiffOperations
   {
      private final List< ISyncOperation > serverOperations;
      
      private final List< ISyncOperation > localOperations;
      
      

      public DiffOperations( List< ISyncOperation > serverOps,
         List< ISyncOperation > localOps )
      {
         serverOperations = Collections.unmodifiableList( serverOps );
         localOperations = Collections.unmodifiableList( localOps );
      }
      


      public List< ISyncOperation > getServerOperations()
      {
         return serverOperations;
      }
      


      public List< ISyncOperation > getLocalOperations()
      {
         return localOperations;
      }
      
   }
   
   

   private SyncDiffer()
   {
      throw new AssertionError();
   }
   


   @SuppressWarnings( "unchecked" )
   public final static < O extends ISyncOperation, T > ArrayList< O > diff( Iterable< T > reference,
                                                                            SyncableList< T > target,
                                                                            Object... params )
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
   


   public final static < T > DiffOperations diff( List< ITwoWaySyncable< T > > serverList,
                                                  List< ITwoWaySyncable< T > > localList,
                                                  Comparator< ITwoWaySyncable< T > > sort,
                                                  ContentProviderClient provider,
                                                  Service service,
                                                  Object... params )
   {
      if ( serverList == null || localList == null || sort == null )
         throw new NullPointerException();
      
      boolean ok = true;
      
      Collections.sort( serverList, sort );
      Collections.sort( localList, sort );
      
      final LinkedList< ISyncOperation > serverOps = new LinkedList< ISyncOperation >();
      final LinkedList< ISyncOperation > localOps = new LinkedList< ISyncOperation >();
      
      // for each element of the server list
      for ( Iterator< ITwoWaySyncable< T > > iterator = serverList.iterator(); ok
         && iterator.hasNext(); )
      {
         final ITwoWaySyncable< T > serverElement = iterator.next();
         
         final int pos = Collections.binarySearch( localList,
                                                   serverElement,
                                                   sort );
         ISyncOperation operation = null;
         
         // INSERT: The server element is not contained in the local list.
         if ( pos < 0 )
         {
            operation = serverElement.computeContentProviderInsertOperation( provider,
                                                                             params );
         }
         
         // UPDATE: The server element is contained in the local list.
         else
         {
            // operation = localList.computeUpdateOperation( pos,
            // serverElement,
            // params );
         }
         
         ok = operation != null;
         
         if ( ok && !( operation instanceof NoopSyncOperation ) )
            localOps.add( operation );
      }
      
      if ( ok )
      {
         // DELETE: Get all elements which have not been touched during the
         // diff.
         // These elements are not in the reference list.
         // final ArrayList< T > untouchedElements = localList.getUntouchedElements();
         //         
         // for ( T tgtElement : untouchedElements )
         // {
         // final ISyncOperation operation = localList.computeDeleteOperation( tgtElement,
         // params );
         //            
         // ok = operation != null;
         //            
         // if ( ok && !( operation instanceof NoopSyncOperation ) )
         // operations.add( operation );
         // }
      }
      
      if ( ok )
         return new DiffOperations( serverOps, localOps );
      else
         return null;
   }
}
