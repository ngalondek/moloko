/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.domain.model;

public interface ITask
{
   long getId();
   
   
   
   long getCreatedMillisUtc();
   
   
   
   long getModifiedMillisUtc();
   
   
   
   boolean isModified();
   
   
   
   long getDeletedMillisUtc();
   
   
   
   boolean isDeleted();
   
   
   
   long getAddedMillisUtc();
   
   
   
   long getCompletedMillisUtc();
   
   
   
   boolean isComplete();
   
   
   
   String getName();
   
   
   
   String getSource();
   
   
   
   String getUrl();
   
   
   
   Priority getPriority();
   
   
   
   int getPostponedCount();
   
   
   
   boolean isPostponed();
   
   
   
   Due getDue();
   
   
   
   Recurrence getRecurrence();
   
   
   
   Estimation getEstimation();
   
   
   
   Iterable< String > getTags();
   
   
   
   Iterable< INote > getNotes();
   
   
   
   Iterable< Participant > getParticipants();
   
   
   
   Location getLocation();
   
   
   
   long getListId();
   
   
   
   String getListName();
}
