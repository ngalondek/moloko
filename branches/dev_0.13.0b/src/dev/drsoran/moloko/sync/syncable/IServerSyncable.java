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

package dev.drsoran.moloko.sync.syncable;

import java.util.Date;

import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;


public interface IServerSyncable< T, V >
{
   
   public Date getCreatedDate();
   


   public Date getModifiedDate();
   


   public Date getDeletedDate();
   


   public boolean hasModification( ModificationSet modificationSet );
   


   public IServerSyncOperation< V > computeServerUpdateOperation( RtmTimeline timeline,
                                                                  ModificationSet modifications,
                                                                  T serverElement );
   


   public IServerSyncOperation< V > computeServerDeleteOperation( RtmTimeline timeline );
   
}
