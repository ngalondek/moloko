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

package dev.drsoran.moloko.app.services;

import java.util.Collection;

import android.app.Activity;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;


public interface IAppContentEditService
{
   void insertTask( Activity context, Task task );
   
   
   
   void updateTask( Activity context, Task task );
   
   
   
   void updateTasks( Activity context, Collection< ? extends Task > tasks );
   
   
   
   void deleteTask( Activity context, Task task );
   
   
   
   void deleteTasks( Activity context, Collection< ? extends Task > tasks );
   
   
   
   void completeTask( Activity context, Task task );
   
   
   
   void completeTasks( Activity context, Collection< ? extends Task > tasks );
   
   
   
   void incompleteTask( Activity context, Task task );
   
   
   
   void incompleteTasks( Activity context, Collection< ? extends Task > tasks );
   
   
   
   void postponeTask( Activity context, Task task );
   
   
   
   void postponeTasks( Activity context, Collection< ? extends Task > tasks );
   
   
   
   void insertTasksList( Activity context, TasksList tasksList );
   
   
   
   void updateTasksList( Activity context, TasksList tasksList );
   
   
   
   void deleteTasksList( Activity context, TasksList tasksList );
}
