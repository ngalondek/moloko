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

package dev.drsoran.moloko.domain.services;

import dev.drsoran.moloko.domain.model.IContact;
import dev.drsoran.moloko.domain.model.ITask;
import dev.drsoran.moloko.domain.model.ITasksList;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;


public interface IContentRepository
{
   ITask getTask( long taskId, int taskContentOptions ) throws ContentException;
   
   
   
   Iterable< ITask > getTasks( int taskContentOptions ) throws ContentException;
   
   
   
   Iterable< ITask > getTasksInTasksList( ITasksList tasksList,
                                          int taskContentOptions ) throws ContentException;
   
   
   
   int getNumberOfTasksInTasksList( ITasksList tasksList ) throws ContentException;
   
   
   
   Iterable< ITask > getTasksFromSmartFilter( RtmSmartFilter smartFilter,
                                              int taskContentOptions ) throws ContentException;
   
   
   
   ITasksList getTasksList( long tasksListId, int taskListContentOptions ) throws ContentException;
   
   
   
   Iterable< ITasksList > getTaskLists( int taskListContentOptions ) throws ContentException;
   
   
   
   IContact getContact( long contactId ) throws ContentException;
   
   
   
   Iterable< IContact > getContacts() throws ContentException;
}
