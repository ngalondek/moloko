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

import java.util.NoSuchElementException;

import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.rtm.sync.SyncTime;


public interface IContentEditService
{
   void insertTask( Task task ) throws ContentException;
   
   
   
   void updateTask( long taskId, Task updatedTask ) throws NoSuchElementException,
                                                   ContentException;
   
   
   
   void deleteTask( long taskId ) throws NoSuchElementException,
                                 ContentException;
   
   
   
   void insertTasksList( TasksList tasksList ) throws ContentException;
   
   
   
   void updateTasksList( long tasksListId, TasksList updatedTasksList ) throws NoSuchElementException,
                                                                       ContentException;
   
   
   
   void deleteTasksList( long tasksListId ) throws NoSuchElementException,
                                           ContentException;
   
   
   
   void setSyncTimes( SyncTime syncTime ) throws ContentException;
}
