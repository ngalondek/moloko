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

import android.support.v4.app.FragmentActivity;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;


public interface IAppContentEditService
{
   void insertTask( FragmentActivity context, Task task );
   
   
   
   void updateTask( FragmentActivity context, Task task );
   
   
   
   void updateTasks( FragmentActivity context,
                     Collection< ? extends Task > tasks );
   
   
   
   void deleteTask( FragmentActivity context, Task task );
   
   
   
   void deleteTasks( FragmentActivity context,
                     Collection< ? extends Task > tasks );
   
   
   
   void completeTask( FragmentActivity context,
                      Task task,
                      long completedMillisUtc );
   
   
   
   void completeTasks( FragmentActivity context,
                       Collection< ? extends Task > tasks,
                       long completedMillisUtc );
   
   
   
   void incompleteTask( FragmentActivity context, Task task );
   
   
   
   void incompleteTasks( FragmentActivity context,
                         Collection< ? extends Task > tasks );
   
   
   
   void postponeTask( FragmentActivity context, Task task );
   
   
   
   void postponeTasks( FragmentActivity context,
                       Collection< ? extends Task > tasks );
   
   
   
   void insertTasksList( FragmentActivity context, TasksList tasksList );
   
   
   
   void updateTasksList( FragmentActivity context, TasksList tasksList );
   
   
   
   void deleteTasksList( FragmentActivity context, TasksList tasksList );
}
