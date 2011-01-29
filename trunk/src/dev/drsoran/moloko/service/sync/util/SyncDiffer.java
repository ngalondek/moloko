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
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentProviderClient;

import com.mdt.rtm.Service;

import dev.drsoran.moloko.service.sync.lists.SyncableList;
import dev.drsoran.moloko.service.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.ISyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable;


public class SyncDiffer
{
   
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
   


   public final static < T extends ITwoWaySyncable< T > > DirectedSyncOperations twoWaydiff( List< T > serverList,
                                                                                             List< T > localList,
                                                                                             Comparator< ? super T > comp,
                                                                                             Service service,
                                                                                             ContentProviderClient provider,
                                                                                             Date lastSync,
                                                                                             boolean fullSync,
                                                                                             Object... params )
   {
      if ( serverList == null || localList == null || comp == null
         || service == null || provider == null )
         throw new NullPointerException();
      
      boolean ok = true;
      
      Collections.sort( serverList, comp );
      Collections.sort( localList, comp );
      
      final LinkedList< ISyncOperation > serverOps = new LinkedList< ISyncOperation >();
      final LinkedList< IContentProviderSyncOperation > localOps = new LinkedList< IContentProviderSyncOperation >();
      
      final boolean[] localTouchedElements = new boolean[ localList.size() ];
      
      // for each element of the server list
      for ( Iterator< T > iterator = serverList.iterator(); ok
         && iterator.hasNext(); )
      {
         final T serverElement = iterator.next();
         final Date serverElementDeleted = serverElement.getDeletedDate();
         
         final int pos = Collections.binarySearch( localList,
                                                   serverElement,
                                                   comp );
         
         // LOCAL INSERT: The server element is not contained in the local list.
         if ( pos < 0 )
         {
            if ( serverElementDeleted == null )
            {
               // TODO: SPECIAL CASE MOVE: See Issue#9 http://code.google.com/p/moloko/issues/detail?id=9
               //
               // This applies only to incremental sync and happens if an element has moved.
               // Then it appears as new in the new list but not deleted in the old list.
               final IContentProviderSyncOperation operation = serverElement.computeContentProviderInsertOperation( provider,
                                                                                                                    params );
               ok = operation != null;
               if ( ok && operation != NoopSyncOperation.INSTANCE )
                  localOps.add( operation );
            }
            
            // We never had the server element locally, skip server element.
         }
         
         // MERGE:
         // LOCAL OR SERVER DELETE: The server element is contained in the local list.
         else
         {
            localTouchedElements[ pos ] = true;
            
            final T localElement = localList.get( pos );
            
            // EXPLICIT LOCAL DELETE: The server element is marked as deleted.
            if ( serverElementDeleted != null )
            {
               IContentProviderSyncOperation operation = localElement.computeContentProviderDeleteOperation( provider,
                                                                                                             params );
               ok = operation != null;
               if ( ok )
               {
                  if ( operation != NoopSyncOperation.INSTANCE )
                     localOps.add( operation );
                  
                  // Remove all modifications of the local task since it gets deleted.
                  operation = localElement.removeModifications( provider );
                  
                  ok = operation != null;
                  if ( ok && operation != NoopSyncOperation.INSTANCE )
                     localOps.add( operation );
               }
            }
            
            else
            {
               final Date serverElementModified = serverElement.getModifiedDate();
               final Date localElementDeleted = localElement.getDeletedDate();
               
               // LOCAL AND SERVER DELETE: The local element is marked as deleted and the server element is not modified
               // after the local delete.
               if ( localElementDeleted != null
                  && localElementDeleted.after( serverElementModified ) )
               {
                  final ISyncOperation serverOperation = serverElement.computeServerDeleteOperation( service,
                                                                                                     params );
                  ok = serverOperation != null;
                  if ( ok )
                  {
                     if ( serverOperation != NoopSyncOperation.INSTANCE )
                        serverOps.add( serverOperation );
                     
                     final IContentProviderSyncOperation localOperation = localElement.computeContentProviderDeleteOperation( provider,
                                                                                                                              params );
                     ok = localOperation != null;
                     if ( ok && localOperation != NoopSyncOperation.INSTANCE )
                        localOps.add( localOperation );
                  }
               }
               
               // MERGE: The locally deleted element is not deleted or deleted before server modification.
               else
               {
                  final DirectedSyncOperations operations = localElement.computeMergeOperations( service,
                                                                                                 provider,
                                                                                                 serverElement,
                                                                                                 params );
                  ok = operations != null;
                  if ( ok )
                  {
                     // No check for NoopSyncOperation since this are lists. We assume them to be empty in this case.
                     localOps.addAll( operations.getLocalOperations() );
                     serverOps.addAll( operations.getServerOperations() );
                  }
               }
            }
         }
      }
      
      if ( ok )
      {
         // Get all local elements which have not been touched during the diff.
         for ( int i = 0; ok && i < localTouchedElements.length; i++ )
         {
            if ( !localTouchedElements[ i ] )
            {
               final T localElement = localList.get( i );
               final Date localElementCreated = localElement.getCreatedDate();
               
               // SERVER INSERT: The local element has been created after the last sync.
               if ( localElementCreated != null
                  && ( lastSync == null || lastSync.before( localElementCreated ) ) )
               {
                  localTouchedElements[ i ] = true;
                  
                  final ISyncOperation operation = localList.get( i )
                                                            .computeServerInsertOperation( service,
                                                                                           params );
                  ok = operation != null;
                  if ( ok && operation != NoopSyncOperation.INSTANCE )
                     serverOps.add( operation );
               }
               
               // LOCAL DELETE: The local element wasn't in the server list and is not new created.
               // Precondition: We can only judge if this is a full sync. Otherwise non-changed
               // sever elements are not there and are interpreted as deleted.
               else if ( fullSync )
               {
                  final IContentProviderSyncOperation operation = localList.get( i )
                                                                           .computeContentProviderDeleteOperation( provider,
                                                                                                                   params );
                  ok = operation != null;
                  if ( ok && operation != NoopSyncOperation.INSTANCE )
                     localOps.add( operation );
               }
            }
         }
      }
      
      if ( ok )
         return new DirectedSyncOperations( serverOps, localOps );
      else
         return null;
   }
}
