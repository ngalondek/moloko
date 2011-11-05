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
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncableList;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;


public class SyncDiffer
{
   
   private SyncDiffer()
   {
      throw new AssertionError();
   }
   
   
   
   public final static < T extends IContentProviderSyncable< T >> List< IContentProviderSyncOperation > inDiff( Iterable< T > serverIterable,
                                                                                                                ContentProviderSyncableList< T > localList,
                                                                                                                boolean fullSync )
   {
      if ( serverIterable == null || localList == null )
         throw new NullPointerException();
      
      final List< IContentProviderSyncOperation > operations = new ArrayList< IContentProviderSyncOperation >();
      
      // for each element of the server list
      for ( T serverElement : serverIterable )
      {
         final int pos = localList.find( serverElement );
         
         if ( pos < 0 )
         {
            // Check if the server element is not deleted
            // INSERT: The server element is not contained in the local list.
            if ( serverElement.getDeletedDate() == null )
            {
               final IContentProviderSyncOperation operation = localList.computeInsertOperation( serverElement );
               if ( !( operation instanceof INoopSyncOperation ) )
                  operations.add( operation );
            }
            
            // We never had this server element locally, just skip it.
         }
         
         else
         {
            // UPDATE: The server element is contained in the local list.
            if ( serverElement.getDeletedDate() == null )
               operations.add( localList.computeUpdateOperation( pos,
                                                                 serverElement ) );
            // DELETE: The server element is contained in the target list and is deleted.
            else
               operations.add( serverElement.computeContentProviderDeleteOperation() );
         }
      }
      
      // DELETE: Get all local elements which have not been touched during the
      // diff.
      // These elements are not in the server list.
      final List< T > untouchedElements = localList.getUntouchedElements();
      
      for ( T localElement : untouchedElements )
      {
         // Check if we have a full sync. Otherwise we would delete local elements
         // which have not changed.
         //
         // If we have no full sync, we have to delete local elements which have been
         // created and deleted locally but never synced to the server.
         if ( fullSync || localElement.getDeletedDate() != null
            || hasDeletedElements( localElement ) )
         {
            final IContentProviderSyncOperation operation = localElement.computeContentProviderDeleteOperation();
            
            if ( operation == null )
               throw new NullPointerException( "operation is null" );
            
            if ( !( operation instanceof INoopSyncOperation ) )
               operations.add( operation );
         }
      }
      
      return operations;
   }
   
   
   
   public final static < T extends IServerSyncable< T, V >, V > List< IServerSyncOperation< V > > outDiff( List< ? extends T > sortedServerList,
                                                                                                           List< ? extends T > sortedLocalList,
                                                                                                           Comparator< ? super T > comp,
                                                                                                           ModificationSet modifications,
                                                                                                           RtmTimeline timeLine,
                                                                                                           Date lastSync )
   {
      if ( sortedServerList == null || sortedLocalList == null || comp == null
         || timeLine == null )
         throw new NullPointerException();
      
      final List< IServerSyncOperation< V > > operations = new LinkedList< IServerSyncOperation< V > >();
      
      final boolean[] localTouchedElements = new boolean[ sortedLocalList.size() ];
      
      // for each element of the server list
      for ( T serverElement : sortedServerList )
      {
         final int pos = Collections.binarySearch( sortedLocalList,
                                                   serverElement,
                                                   comp );
         
         // MERGE:
         // SERVER DELETE: The server element is contained in the local list.
         if ( pos >= 0 )
         {
            localTouchedElements[ pos ] = true;
            final Date serverElementDeleted = serverElement.getDeletedDate();
            final T localElement = sortedLocalList.get( pos );
            
            // The server element is not marked as deleted.
            if ( serverElementDeleted == null )
            {
               final Date serverElementModified = serverElement.getModifiedDate();
               final Date localElementDeleted = localElement.getDeletedDate();
               
               // SERVER DELETE: The local element is marked as deleted and the server element is not modified
               // after the local delete.
               if ( localElementDeleted != null
                  && ( serverElementModified == null || localElementDeleted.after( serverElementModified ) ) )
               {
                  operations.add( localElement.computeServerDeleteOperation( timeLine ) );
               }
               
               // MERGE: The local element is not deleted or deleted before server modification.
               else
               {
                  operations.add( localElement.computeServerUpdateOperation( timeLine,
                                                                             modifications,
                                                                             serverElement ) );
               }
            }
         }
      }
      
      // Get all local elements which have not been touched during the diff.
      for ( int i = 0; i < localTouchedElements.length; ++i )
      {
         if ( !localTouchedElements[ i ] )
         {
            final T localElement = sortedLocalList.get( i );
            final Date localElementDeleted = localElement.getDeletedDate();
            
            if ( localElementDeleted != null )
            {
               final Date localElementCreated = localElement.getCreatedDate();
               
               // SERVER DELETE: The local element is marked as deleted and the
               // server element was not seen.
               //
               // If the local element was created after the last sync and is now
               // deleted, then we can skip it cause the server has never seen
               // this element. It was created and deleted only locally.
               if ( localElementCreated != null
                  && !localElementCreated.after( lastSync ) )
               {
                  localTouchedElements[ i ] = true;
                  operations.add( localElement.computeServerDeleteOperation( timeLine ) );
               }
            }
            else
            {
               final Date localElementModified = localElement.getModifiedDate();
               
               // SERVER UPDATE: The local element was modified since the last sync and the
               // server element was not seen.
               if ( ( localElementModified != null && ( lastSync == null || lastSync.before( localElementModified ) ) )
                  || localElement.hasModification( modifications ) )
               {
                  localTouchedElements[ i ] = true;
                  operations.add( localElement.computeServerUpdateOperation( timeLine,
                                                                             modifications,
                                                                             null ) );
               }
            }
         }
      }
      
      return operations;
   }
   
   
   
   private static < T extends IContentProviderSyncable< ? >> boolean hasDeletedElements( T element )
   {
      if ( element instanceof IContentProviderSyncableList< ? > )
         return ( (IContentProviderSyncableList< ? >) element ).hasDeletedElements();
      else
         return false;
   }
}
