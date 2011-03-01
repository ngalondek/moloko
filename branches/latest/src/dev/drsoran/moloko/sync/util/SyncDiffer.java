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

package dev.drsoran.moloko.sync.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.INoopSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;


public class SyncDiffer
{
   
   private SyncDiffer()
   {
      throw new AssertionError();
   }
   


   public final static < T extends IContentProviderSyncable< T >> List< IContentProviderSyncOperation > diff( Iterable< T > reference,
                                                                                                              ContentProviderSyncableList< T > target )
   {
      return diff( reference, target, null );
   }
   


   public final static < T extends IContentProviderSyncable< T >> List< IContentProviderSyncOperation > diff( Iterable< T > reference,
                                                                                                              ContentProviderSyncableList< T > target,
                                                                                                              Date lastSync )
   {
      if ( reference == null || target == null )
         throw new NullPointerException();
      
      final List< IContentProviderSyncOperation > operations = new ArrayList< IContentProviderSyncOperation >();
      
      // for each element of the reference list
      for ( T refElement : reference )
      {
         final int pos = target.find( refElement );
         
         // INSERT: The reference element is not contained in the target list.
         if ( pos == -1 )
         {
            final IContentProviderSyncOperation operation = target.computeInsertOperation( refElement );
            if ( !( operation instanceof INoopSyncOperation ) )
               operations.add( operation );
         }
         
         // UPDATE: The reference element is contained in the target list.
         else
         {
            operations.addAll( target.computeUpdateOperation( pos,
                                                              refElement,
                                                              lastSync ) );
         }
      }
      
      // Check if we have a full sync. Otherwise we would delete elements
      // which have not changed.
      if ( lastSync == null )
      {
         // DELETE: Get all elements which have not been touched during the
         // diff.
         // These elements are not in the reference list.
         final List< T > untouchedElements = target.getUntouchedElements();
         
         for ( T tgtElement : untouchedElements )
         {
            final IContentProviderSyncOperation operation = target.computeDeleteOperation( tgtElement );
            
            if ( operation == null )
               throw new NullPointerException( "operation is null" );
            
            if ( !( operation instanceof INoopSyncOperation ) )
               operations.add( operation );
         }
      }
      
      return operations;
   }
   


   public final static < T extends ITwoWaySyncable< T, V >, V > DirectedSyncOperations< V > twoWaydiff( List< ? extends T > serverList,
                                                                                                        List< ? extends T > localList,
                                                                                                        Comparator< ? super T > comp,
                                                                                                        ModificationSet modifications,
                                                                                                        RtmTimeline timeLine )
   {
      return twoWaydiff( serverList,
                         localList,
                         comp,
                         modifications,
                         timeLine,
                         null );
   }
   


   public final static < T extends ITwoWaySyncable< T, V >, V > DirectedSyncOperations< V > twoWaydiff( List< ? extends T > serverList,
                                                                                                        List< ? extends T > localList,
                                                                                                        Comparator< ? super T > comp,
                                                                                                        ModificationSet modifications,
                                                                                                        RtmTimeline timeLine,
                                                                                                        Date lastSync )
   {
      if ( serverList == null || localList == null || comp == null
         || timeLine == null )
         throw new NullPointerException();
      
      Collections.sort( serverList, comp );
      Collections.sort( localList, comp );
      
      final DirectedSyncOperations< V > operations = new DirectedSyncOperations< V >();
      
      final boolean[] localTouchedElements = new boolean[ localList.size() ];
      
      // for each element of the server list
      for ( T serverElement : serverList )
      {
         final Date serverElementDeleted = serverElement.getDeletedDate();
         
         final int pos = Collections.binarySearch( localList,
                                                   serverElement,
                                                   comp );
         
         // LOCAL INSERT: The server element is not contained in the local list.
         if ( pos < 0 )
         {
            if ( serverElementDeleted == null )
            {
               operations.add( serverElement.computeContentProviderInsertOperation() );
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
               operations.add( localElement.computeContentProviderDeleteOperation() );
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
                  operations.add( serverElement.computeServerDeleteOperation( timeLine ) );
                  operations.add( localElement.computeContentProviderDeleteOperation() );
               }
               
               // MERGE: The local element is not deleted or deleted before server modification.
               else
               {
                  final DirectedSyncOperations< V > mergeOps = localElement.computeMergeOperations( lastSync,
                                                                                                    timeLine,
                                                                                                    modifications,
                                                                                                    serverElement,
                                                                                                    localElement );
                  
                  // Check if NO server operations have been computed by the merge. This means we have only local
                  // updates. If we had server operations we would retrieve an up-to-date version of the element by
                  // applying the change to the server. So we drop the local computed operations.
                  // We use this retrieved version to update the local element then. This should contain all changes
                  // merged.
                  // This is done because this is the only way to keep modification time stamps in sync with the server.
                  if ( !operations.addAllServerOps( mergeOps.getServerOperations() ) )
                     operations.addAllLocalOps( mergeOps.getLocalOperations() );
               }
            }
         }
      }
      
      // Get all local elements which have not been touched during the diff.
      for ( int i = 0; i < localTouchedElements.length; i++ )
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
               operations.add( localList.get( i )
                                        .computeServerInsertOperation( timeLine ) );
            }
            
            // LOCAL DELETE: The local element wasn't in the server list and is not new created.
            // Precondition: We can only judge if this is a full sync. Otherwise non-changed
            // sever elements are not in the serverList and are interpreted as deleted.
            else if ( lastSync == null )
            {
               operations.add( localList.get( i )
                                        .computeContentProviderDeleteOperation() );
            }
            
            else
            {
               final Date localElementModified = localElement.getModifiedDate();
               
               // SERVER UPDATE: The local element was modified since the last sync and the
               // server element was not seen.
               if ( localElementModified != null
                  && ( lastSync == null || lastSync.before( localElementModified ) ) )
               {
                  localTouchedElements[ i ] = true;
                  operations.addAllServerOps( localElement.computeServerUpdateOperations( lastSync,
                                                                                          timeLine,
                                                                                          modifications ) );
               }
            }
         }
      }
      
      return operations;
   }
   


   public final static < T extends IServerSyncable< T, V >, V > List< IServerSyncOperation< V > > outDiff( List< ? extends T > serverList,
                                                                                                           List< ? extends T > localList,
                                                                                                           Comparator< ? super T > comp,
                                                                                                           ModificationSet modifications,
                                                                                                           RtmTimeline timeLine,
                                                                                                           Date lastSync )
   {
      if ( serverList == null || localList == null || comp == null
         || timeLine == null )
         throw new NullPointerException();
      
      Collections.sort( serverList, comp );
      Collections.sort( localList, comp );
      
      final List< IServerSyncOperation< V > > operations = new LinkedList< IServerSyncOperation< V > >();
      
      final boolean[] localTouchedElements = new boolean[ localList.size() ];
      
      // for each element of the server list
      for ( T serverElement : serverList )
      {
         final int pos = Collections.binarySearch( localList,
                                                   serverElement,
                                                   comp );
         
         // MERGE:
         // SERVER DELETE: The server element is contained in the local list.
         if ( pos >= 0 )
         {
            localTouchedElements[ pos ] = true;
            final Date serverElementDeleted = serverElement.getDeletedDate();
            final T localElement = localList.get( pos );
            
            // The server element is not marked as deleted.
            if ( serverElementDeleted == null )
            {
               final Date serverElementModified = serverElement.getModifiedDate();
               final Date localElementDeleted = localElement.getDeletedDate();
               
               // SERVER DELETE: The local element is marked as deleted and the server element is not modified
               // after the local delete.
               if ( localElementDeleted != null
                  && localElementDeleted.after( serverElementModified ) )
               {
                  operations.add( serverElement.computeServerDeleteOperation( timeLine ) );
               }
               
               // MERGE: The local element is not deleted or deleted before server modification.
               else
               {
                  operations.addAll( localElement.computeServerUpdateOperations( timeLine,
                                                                                 modifications,
                                                                                 serverElement ) );
               }
            }
         }
      }
      
      // Get all local elements which have not been touched during the diff.
      for ( int i = 0; i < localTouchedElements.length; i++ )
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
               operations.add( localList.get( i )
                                        .computeServerInsertOperation( timeLine ) );
            }
            
            // SERVER UPDATE: The local element was modified since the last sync and the
            // server element was not seen.
            else
            {
               final Date localElementModified = localElement.getModifiedDate();
               
               if ( localElementModified != null
                  && ( lastSync == null || lastSync.before( localElementModified ) ) )
               {
                  localTouchedElements[ i ] = true;
                  operations.addAll( localElement.computeServerUpdateOperations( timeLine,
                                                                                 modifications,
                                                                                 null ) );
               }
            }
         }
      }
      
      return operations;
   }
}
