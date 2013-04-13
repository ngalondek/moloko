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

package dev.drsoran.moloko.content.db;

import java.util.NoSuchElementException;

import dev.drsoran.moloko.domain.model.ITask;
import dev.drsoran.moloko.domain.model.ITasksList;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentEditService;
import dev.drsoran.moloko.domain.services.IContentRepository;


public class DbContentEditService implements IContentEditService
{
   private final DbModificationsEditPart modificationsEditPart;
   
   private final DbTasksContentEditPart tasksContentEditPart;
   
   private final DbTasksListsContentEditPart tasksListsContentEditPart;
   
   
   
   public DbContentEditService( RtmDatabase database,
      IContentRepository contentRepository )
   {
      this.modificationsEditPart = new DbModificationsEditPart( database );
      this.tasksContentEditPart = new DbTasksContentEditPart( database,
                                                              contentRepository,
                                                              modificationsEditPart );
      this.tasksListsContentEditPart = new DbTasksListsContentEditPart( database,
                                                                        contentRepository,
                                                                        modificationsEditPart );
   }
   
   
   
   @Override
   public void insertTask( ITask task ) throws ContentException
   {
      tasksContentEditPart.insertTask( task );
   }
   
   
   
   @Override
   public void updateTask( long taskId, ITask updatedTask ) throws NoSuchElementException,
                                                           ContentException
   {
      tasksContentEditPart.updateTask( taskId, updatedTask );
   }
   
   
   
   @Override
   public void deleteTask( long taskId ) throws NoSuchElementException,
                                        ContentException
   {
      tasksContentEditPart.deleteTask( taskId );
   }
   
   
   
   @Override
   public void insertTasksList( ITasksList tasksList ) throws ContentException
   {
      tasksListsContentEditPart.insertTasksList( tasksList );
   }
   
   
   
   @Override
   public void updateTasksList( long tasksListId, ITasksList updatedTasksList ) throws NoSuchElementException,
                                                                               ContentException
   {
      tasksListsContentEditPart.updateTasksList( tasksListId, updatedTasksList );
   }
   
   
   
   @Override
   public void deleteTasksList( long tasksListId ) throws NoSuchElementException,
                                                  ContentException
   {
      tasksListsContentEditPart.deleteTasksList( tasksListId );
   }
}
