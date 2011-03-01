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

package dev.drsoran.moloko.sync.util;

import java.util.Date;

import android.net.Uri;

import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.sync.syncable.IServerSyncable.SyncDirection;


public class SyncProperties< T >
{
   
   public final SyncDirection syncDirection;
   
   public final Date lastSyncDate;
   
   public final Date serverModDate;
   
   public final Date localModDate;
   
   public final Uri uri;
   
   public final ModificationSet modifications;
   
   public final RtmTimeline timeline;
   
   public final DirectedSyncOperations< T > operations = new DirectedSyncOperations< T >();
   
   

   private SyncProperties( SyncDirection syncDirection, Date lastSyncDate,
      Date serverModDate, Date localModDate, Uri uri,
      ModificationSet modifications, RtmTimeline timeline )
   {
      this.syncDirection = syncDirection;
      this.lastSyncDate = lastSyncDate;
      this.serverModDate = serverModDate;
      this.localModDate = localModDate;
      this.uri = uri;
      this.modifications = modifications;
      this.timeline = timeline;
   }
   


   public final static < T > SyncProperties< T > newInstance( SyncDirection syncDirection,
                                                              Date lastSyncDate,
                                                              Date serverModDate,
                                                              Date localModDate,
                                                              Uri uri,
                                                              ModificationSet modifications,
                                                              RtmTimeline timeline )
   {
      return new SyncProperties< T >( syncDirection,
                                      lastSyncDate,
                                      serverModDate,
                                      localModDate,
                                      uri,
                                      modifications,
                                      timeline );
   }
   


   public final static < T > SyncProperties< T > newLocalOnlyInstance( Date lastSyncDate,
                                                                       Date serverModDate,
                                                                       Date localModDate,
                                                                       Uri uri )
   {
      return new SyncProperties< T >( SyncDirection.LOCAL_ONLY,
                                      lastSyncDate,
                                      serverModDate,
                                      localModDate,
                                      uri,
                                      null,
                                      null );
   }
}
