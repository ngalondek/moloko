/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.sync.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.SyncResult;

import com.mdt.rtm.ServiceException;
import com.mdt.rtm.ServiceInternalException;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.content.db.Modification;
import dev.drsoran.moloko.sync.operation.INoopSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;


public final class SyncUtils
{
   private SyncUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   
   public final static void handleServiceInternalException( ServiceInternalException exception,
                                                            Class< ? > tag,
                                                            SyncResult syncResult )
   {
      final Exception internalException = exception.getEnclosedException();
      
      if ( internalException != null )
      {
         MolokoApp.Log.e( tag, exception.responseMessage, internalException );
         
         if ( internalException instanceof IOException )
            ++syncResult.stats.numIoExceptions;
         else
            ++syncResult.stats.numParseExceptions;
      }
      else
      {
         MolokoApp.Log.e( tag, exception.responseMessage );
         ++syncResult.stats.numIoExceptions;
      }
   }
   
   
   public enum SyncResultDirection
   {
      NOTHING, LOCAL, SERVER
   }
   
   
   
   public final static < V > SyncResultDirection getSyncDirection( SyncProperties properties,
                                                                   String columnName,
                                                                   V serverValue,
                                                                   V localValue,
                                                                   Class< V > valueClass )
   {
      SyncResultDirection syncDir = SyncResultDirection.NOTHING;
      
      final Modification modification = properties.getModification( columnName );
      
      // Check if we should simply sync out and have a modification
      if ( properties.serverModDate == null && modification != null )
      {
         syncDir = SyncResultDirection.SERVER;
      }
      
      else if ( isDifferent( serverValue, localValue ) )
      {
         // Check if the local value was modified
         if ( modification != null )
         {
            // MERGE
            final V syncedValue = modification.getSyncedValue( valueClass );
            
            // Check if the server value has changed compared to the last synced value.
            if ( isDifferent( syncedValue, serverValue ) )
            {
               // CONFLICT: Local and server element has changed.
               // Let the modified date of the elements decide in which direction to sync.
               //
               // In case of equal dates we take the server value cause this
               // value we have transferred already.
               if ( properties.serverModDate != null
                  && ( properties.serverModDate.getTime() >= properties.localModDate.getTime() ) )
               {
                  // LOCAL UPDATE: The server element was modified after the local value.
                  syncDir = SyncResultDirection.LOCAL;
               }
               else
               {
                  // SERVER UPDATE: The local element was modified after the server element.
                  syncDir = SyncResultDirection.SERVER;
               }
            }
            else
            {
               // SERVER UPDATE: The server value has not been changed since last sync,
               // so use local modified value.
               syncDir = SyncResultDirection.SERVER;
            }
         }
         
         // LOCAL UPDATE: If the element has not locally changed, take the server version
         else
         {
            syncDir = SyncResultDirection.LOCAL;
         }
      }
      
      return syncDir;
   }
   
   
   
   public final static < T > void applyServerOperations( ContentRepository rtmProvider,
                                                         List< ? extends IServerSyncOperation< T > > serverOps,
                                                         List< T > sortedServerElements,
                                                         Comparator< ? super T > cmp ) throws ServiceException
   {
      for ( IServerSyncOperation< T > serverOp : serverOps )
      {
         if ( !( serverOp instanceof INoopSyncOperation ) )
         {
            try
            {
               final T result = serverOp.execute( rtmProvider );
               
               if ( result == null )
                  throw new ServiceException( -1,
                                              "ServerSyncOperation produced no result" );
               
               // If the set already contains an element in respect to the Comparator,
               // then we update it by the new received.
               final int pos = Collections.binarySearch( sortedServerElements,
                                                         result,
                                                         cmp );
               
               if ( pos >= 0 )
               {
                  sortedServerElements.remove( pos );
                  sortedServerElements.add( pos, result );
               }
               else
               {
                  sortedServerElements.add( ( -pos - 1 ), result );
               }
            }
            catch ( ServiceException e )
            {
               MolokoApp.Log.e( SyncUtils.class,
                                "Applying server operation failed",
                                e );
               throw e;
            }
         }
      }
   }
}
