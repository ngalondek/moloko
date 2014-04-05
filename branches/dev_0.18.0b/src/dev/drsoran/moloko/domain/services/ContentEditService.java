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

import android.net.Uri;
import dev.drsoran.moloko.content.Columns.SyncTimesColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.content.AbstractContentEditHandler;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.rtm.sync.SyncTime;


public class ContentEditService implements IContentEditService
{
   private final IContentRepository contentRepository;
   
   private final AbstractContentEditHandler< Task > tasksContentEditHandler;
   
   private final AbstractContentEditHandler< TasksList > tasksListsContentEditHandler;
   
   private final AbstractContentEditHandler< Note > notesContentEditHandler;
   
   private final AbstractContentEditHandler< Participant > participantsContentEditHandler;
   
   private final AbstractContentEditHandler< SyncTime > syncTimesContentEditHandler;
   
   
   
   public ContentEditService(
      AbstractContentEditHandler< Task > tasksContentEditHandler,
      AbstractContentEditHandler< TasksList > tasksListsContentEditHandler,
      AbstractContentEditHandler< Note > notesContentEditHandler,
      AbstractContentEditHandler< Participant > participantsContentEditHandler,
      AbstractContentEditHandler< SyncTime > syncTimesContentEditHandler,
      IContentRepository contentRepository )
   {
      this.contentRepository = contentRepository;
      this.tasksContentEditHandler = tasksContentEditHandler;
      this.tasksListsContentEditHandler = tasksListsContentEditHandler;
      this.notesContentEditHandler = notesContentEditHandler;
      this.participantsContentEditHandler = participantsContentEditHandler;
      this.syncTimesContentEditHandler = syncTimesContentEditHandler;
   }
   
   
   
   @Override
   public void insertTask( Task task ) throws ContentException
   {
      final Uri newTaskUri = tasksContentEditHandler.insertElement( ContentUris.TASKS_CONTENT_URI,
                                                                    task );
      final long newTaskId = ContentUris.getLastPathIdFromUri( newTaskUri );
      
      for ( Note note : task.getNotes() )
      {
         notesContentEditHandler.insertAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI,
                                                          note,
                                                          newTaskId );
      }
      
      for ( Participant participant : task.getParticipants() )
      {
         participantsContentEditHandler.insertAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                                                 participant,
                                                                 newTaskId );
      }
   }
   
   
   
   @Override
   public void updateTask( long taskId, Task updatedTask ) throws NoSuchElementException,
                                                          ContentException
   {
      final Task existingTask = contentRepository.getTask( taskId,
                                                           TaskContentOptions.WithNotes.or( TaskContentOptions.WithParticipants ) );
      
      tasksContentEditHandler.updateElement( ContentUris.TASKS_CONTENT_URI_ID,
                                             existingTask,
                                             updatedTask,
                                             taskId );
      
      updateNotes( existingTask, updatedTask );
      updateParticipants( existingTask, updatedTask );
   }
   
   
   
   @Override
   public void deleteTask( long taskId ) throws NoSuchElementException,
                                        ContentException
   {
      tasksContentEditHandler.deleteElement( ContentUris.TASKS_CONTENT_URI_ID,
                                             taskId );
   }
   
   
   
   @Override
   public void insertTasksList( TasksList tasksList ) throws ContentException
   {
      tasksListsContentEditHandler.insertElement( ContentUris.TASKS_LISTS_CONTENT_URI,
                                                  tasksList );
   }
   
   
   
   @Override
   public void updateTasksList( long tasksListId, TasksList updatedTasksList ) throws NoSuchElementException,
                                                                              ContentException
   {
      final TasksList exitsingTasksList = contentRepository.getTasksList( tasksListId,
                                                                          TasksListContentOptions.None );
      
      tasksListsContentEditHandler.updateElement( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                  exitsingTasksList,
                                                  updatedTasksList,
                                                  tasksListId );
   }
   
   
   
   @Override
   public void deleteTasksList( long tasksListId ) throws NoSuchElementException,
                                                  ContentException
   {
      tasksListsContentEditHandler.deleteElement( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                  tasksListId );
   }
   
   
   
   @Override
   public void setSyncTimes( SyncTime syncTime ) throws ContentException
   {
      syncTimesContentEditHandler.updateElement( ContentUris.SYNC_CONTENT_URI_ID,
                                                 null,
                                                 syncTime,
                                                 SyncTimesColumns.SINGLETON_ID );
   }
   
   
   
   private void updateNotes( Task existingTask, Task updatedTask )
   {
      for ( Note updatedNote : updatedTask.getNotes() )
      {
         final Note existingNote = existingTask.getNote( updatedNote.getId() );
         
         if ( existingNote == null )
         {
            notesContentEditHandler.insertAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI,
                                                             updatedNote,
                                                             updatedTask.getId() );
         }
         else
         {
            notesContentEditHandler.updateAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                             existingNote,
                                                             updatedNote,
                                                             updatedTask.getId(),
                                                             updatedNote.getId() );
         }
      }
      
      for ( Note existingNote : existingTask.getNotes() )
      {
         if ( !updatedTask.hasNote( existingNote.getId() ) )
         {
            notesContentEditHandler.deleteAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI,
                                                             existingTask.getId(),
                                                             existingNote.getId() );
         }
      }
   }
   
   
   
   private void updateParticipants( Task existingTask, Task updatedTask )
   {
      for ( Participant updateParticipant : updatedTask.getParticipants() )
      {
         final Participant existingParticipant = existingTask.getParticipant( updateParticipant.getId() );
         if ( existingParticipant == null )
         {
            participantsContentEditHandler.insertAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                                                    updateParticipant,
                                                                    updatedTask.getId() );
         }
         else
         {
            participantsContentEditHandler.updateAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                                    existingParticipant,
                                                                    updateParticipant,
                                                                    updatedTask.getId(),
                                                                    updateParticipant.getId() );
         }
      }
      
      for ( Participant existingParticipant : existingTask.getParticipants() )
      {
         if ( !updatedTask.isParticipating( existingParticipant.getId() ) )
         {
            participantsContentEditHandler.deleteAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                                                    existingTask.getId(),
                                                                    existingParticipant.getId() );
         }
      }
   }
}
