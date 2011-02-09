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

package dev.drsoran.moloko.service.sync.syncable;

import java.util.Date;

import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.service.sync.operation.TypedDirectedSyncOperations;


public interface IServerSyncable< T extends IServerSyncable< T > >
{
   public static enum MergeDirection
   {
      LOCAL_ONLY, SERVER_ONLY, BOTH
   }
   
   

   public Date getCreatedDate();
   


   public Date getModifiedDate();
   


   public Date getDeletedDate();
   


   public IServerSyncOperation< T > computeServerInsertOperation( RtmTimeline timeLine );
   


   public IServerSyncOperation< T > computeServerDeleteOperation( RtmTimeline timeLine );
   


   public IServerSyncOperation< T > computeServerUpdateOperation( RtmTimeline timeLine,
                                                                  ModificationList modifictaions );
   


   public TypedDirectedSyncOperations< T > computeMergeOperations( RtmTimeline timeLine,
                                                                   ModificationList modifications,
                                                                   T updateElement,
                                                                   MergeDirection mergeDirection );
   


   public IContentProviderSyncOperation computeRemoveModificationsOperation( ModificationList modifications );
   
}
