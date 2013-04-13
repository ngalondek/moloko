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

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;
import dev.drsoran.moloko.content.db.Columns.ParticipantsColumns;
import dev.drsoran.moloko.content.db.Columns.RawTasksColumns;
import dev.drsoran.moloko.content.db.Columns.RtmNotesColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.domain.model.Constants;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.INote;
import dev.drsoran.moloko.domain.model.ITask;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.domain.services.TaskContentOptions;


class DbTasksContentEditPart
{
   private final RtmDatabase database;
   
   private final ITable rtmTaskSeriesTable;
   
   private final ITable rawTasksTable;
   
   private final ITable notesTable;
   
   private final ITable participantsTable;
   
   private final IContentRepository contentRepository;
   
   private final DbModificationsEditPart modificationsEditPart;
   
   
   
   public DbTasksContentEditPart( RtmDatabase database,
      IContentRepository contentRepository,
      DbModificationsEditPart modificationsEditPart )
   {
      this.database = database;
      this.contentRepository = contentRepository;
      this.rtmTaskSeriesTable = database.getTable( RtmTaskSeriesTable.TABLE_NAME );
      this.rawTasksTable = database.getTable( RawTasksTable.TABLE_NAME );
      this.notesTable = database.getTable( RtmNotesTable.TABLE_NAME );
      this.participantsTable = database.getTable( ParticipantsTable.TABLE_NAME );
      this.modificationsEditPart = modificationsEditPart;
   }
   
   
   
   public void insertTask( final ITask task ) throws ContentException
   {
      DbUtils.doTransactional( database.getWritable(), new Runnable()
      {
         @Override
         public void run()
         {
            try
            {
               final ContentValues taskSeriesContentValues = createTaskSeriesContentValues( task );
               final long newTaskSeriesId = rtmTaskSeriesTable.insert( taskSeriesContentValues );
               
               final ContentValues rawTaskContentValues = createRawTaskContentValues( newTaskSeriesId,
                                                                                      task );
               rawTasksTable.insert( rawTaskContentValues );
               
               for ( INote note : task.getNotes() )
               {
                  final ContentValues noteContentValues = createNoteContentValues( newTaskSeriesId,
                                                                                   note );
                  notesTable.insert( noteContentValues );
               }
               
               for ( Participant participant : task.getParticipants() )
               {
                  final ContentValues participantContentValues = createParticipantContentValues( newTaskSeriesId,
                                                                                                 participant );
                  participantsTable.insert( participantContentValues );
               }
            }
            catch ( Throwable e )
            {
               throw new ContentException( "Failed to insert new Task", e );
            }
         }
      } );
   }
   
   
   
   public void updateTask( final long taskId, final ITask updatedTask ) throws NoSuchElementException,
                                                                       ContentException
   {
      final ITask existingTask = contentRepository.getTask( taskId,
                                                              TaskContentOptions.WITH_NOTES
                                                                 | TaskContentOptions.WITH_PARTICIPANTS );
      
      DbUtils.doTransactional( database.getWritable(), new Runnable()
      {
         @Override
         public void run()
         {
            final Collection< Modification > modifications = new ArrayList< Modification >();
            
            collectTaskSeriesModifications( modifications,
                                            existingTask,
                                            updatedTask );
            
            collectRawTaskModifications( modifications,
                                         existingTask,
                                         updatedTask );
            
            collectParticipantsModifications( modifications,
                                              existingTask,
                                              updatedTask );
            
            collectNotesModifications( modifications, existingTask, updatedTask );
            
            if ( modifications.size() > 0 )
            {
               modificationsEditPart.applyModificationsInTransaction( modifications );
            }
            
            removeAddParticipants( existingTask, updatedTask );
            removeAddNotes( existingTask, updatedTask );
         }
      } );
   }
   
   
   
   public void deleteTask( final long taskId ) throws NoSuchElementException,
                                              ContentException
   {
      DbUtils.doTransactional( database.getWritable(), new Runnable()
      {
         @Override
         public void run()
         {
            int numDeleted = 0;
            try
            {
               numDeleted = rawTasksTable.delete( taskId, null, null );
            }
            catch ( Throwable e )
            {
               throw new ContentException( "Failed to delete Task " + taskId, e );
            }
            
            if ( numDeleted < 1 )
            {
               throw new NoSuchElementException( String.valueOf( taskId ) );
            }
         }
      } );
   }
   
   
   
   private static ContentValues createTaskSeriesContentValues( ITask task )
   {
      final ContentValues values = new ContentValues();
      
      values.put( RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE,
                  task.getCreatedMillisUtc() );
      values.put( RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE,
                  task.getModifiedMillisUtc() );
      values.put( RtmTaskSeriesColumns.TASKSERIES_NAME, task.getName() );
      values.put( RtmTaskSeriesColumns.LIST_ID, task.getListId() );
      
      if ( !TextUtils.isEmpty( task.getSource() ) )
      {
         values.put( RtmTaskSeriesColumns.SOURCE, task.getSource() );
      }
      else
      {
         values.putNull( RtmTaskSeriesColumns.SOURCE );
      }
      
      if ( !TextUtils.isEmpty( task.getUrl() ) )
      {
         values.put( RtmTaskSeriesColumns.URL, task.getUrl() );
      }
      else
      {
         values.putNull( RtmTaskSeriesColumns.URL );
      }
      
      final Recurrence recurrence = task.getRecurrence();
      if ( recurrence != null )
      {
         values.put( RtmTaskSeriesColumns.RECURRENCE, recurrence.getPattern() );
         values.put( RtmTaskSeriesColumns.RECURRENCE_EVERY,
                     recurrence.isEveryRecurrence() ? 1 : 0 );
      }
      else
      {
         values.putNull( RtmTaskSeriesColumns.RECURRENCE );
         values.putNull( RtmTaskSeriesColumns.RECURRENCE_EVERY );
      }
      
      if ( task.getLocation() != null )
      {
         values.put( RtmTaskSeriesColumns.LOCATION_ID, task.getLocation()
                                                           .getId() );
      }
      else
      {
         values.putNull( RtmTaskSeriesColumns.LOCATION_ID );
      }
      
      final Iterable< String > tags = task.getTags();
      final String tagsJoined = TextUtils.join( RtmTaskSeriesColumns.TAGS_SEPARATOR,
                                                tags );
      
      if ( !TextUtils.isEmpty( tagsJoined ) )
      {
         values.put( RtmTaskSeriesColumns.TAGS, tagsJoined );
      }
      else
      {
         values.putNull( RtmTaskSeriesColumns.TAGS );
      }
      
      return values;
   }
   
   
   
   private static ContentValues createRawTaskContentValues( long taskSeriesId,
                                                            ITask task )
   {
      final ContentValues values = new ContentValues();
      
      values.put( RawTasksColumns.TASKSERIES_ID, taskSeriesId );
      values.put( RawTasksColumns.ADDED_DATE, task.getAddedMillisUtc() );
      values.put( RawTasksColumns.PRIORITY, task.getPriority().toString() );
      values.put( RawTasksColumns.POSTPONED, task.getPostponedCount() );
      
      final Due due = task.getDue();
      if ( due != null )
      {
         values.put( RawTasksColumns.DUE_DATE, due.getMillisUtc() );
         values.put( RawTasksColumns.HAS_DUE_TIME, due.hasDueTime() ? 1 : 0 );
      }
      else
      {
         values.putNull( RawTasksColumns.DUE_DATE );
      }
      
      if ( task.getCompletedMillisUtc() != Constants.NO_TIME )
      {
         values.put( RawTasksColumns.COMPLETED_DATE,
                     task.getCompletedMillisUtc() );
      }
      else
      {
         values.putNull( RawTasksColumns.COMPLETED_DATE );
      }
      
      if ( task.getDeletedMillisUtc() != Constants.NO_TIME )
      {
         values.put( RawTasksColumns.DELETED_DATE, task.getDeletedMillisUtc() );
      }
      else
      {
         values.putNull( RawTasksColumns.DELETED_DATE );
      }
      
      final Estimation estimation = task.getEstimation();
      if ( estimation != null )
      {
         values.put( RawTasksColumns.ESTIMATE, estimation.getSentence() );
         values.put( RawTasksColumns.ESTIMATE_MILLIS, estimation.getMillisUtc() );
      }
      else
      {
         values.putNull( RawTasksColumns.ESTIMATE );
      }
      
      return values;
   }
   
   
   
   private static ContentValues createNoteContentValues( long taskSeriesId,
                                                         INote note )
   {
      final ContentValues values = new ContentValues();
      
      values.put( RtmNotesColumns.TASKSERIES_ID, taskSeriesId );
      values.put( RtmNotesColumns.NOTE_CREATED_DATE, note.getCreatedMillisUtc() );
      values.put( RtmNotesColumns.NOTE_MODIFIED_DATE,
                  note.getModifiedMillisUtc() );
      values.put( RtmNotesColumns.NOTE_TEXT, note.getText() );
      
      if ( note.getDeletedMillisUtc() != Constants.NO_TIME )
      {
         values.put( RtmNotesColumns.NOTE_DELETED_DATE,
                     note.getDeletedMillisUtc() );
      }
      else
      {
         values.putNull( RtmNotesColumns.NOTE_DELETED_DATE );
      }
      
      if ( !TextUtils.isEmpty( note.getTitle() ) )
      {
         values.put( RtmNotesColumns.NOTE_TITLE, note.getTitle() );
      }
      else
      {
         values.putNull( RtmNotesColumns.NOTE_TITLE );
      }
      
      return values;
   }
   
   
   
   private static ContentValues createParticipantContentValues( long taskSeriesId,
                                                                Participant participant )
   {
      final ContentValues values = new ContentValues();
      
      values.put( ParticipantsColumns.TASKSERIES_ID, taskSeriesId );
      values.put( ParticipantsColumns.CONTACT_ID, participant.getContactId() );
      values.put( ParticipantsColumns.FULLNAME, participant.getFullname() );
      values.put( ParticipantsColumns.USERNAME, participant.getUsername() );
      
      return values;
   }
   
   
   
   private static void collectTaskSeriesModifications( Collection< Modification > modifications,
                                                       ITask existingTask,
                                                       ITask updatedTask )
   {
      final Uri entityUri = DbUtils.entityUriWithId( RtmTaskSeriesTable.TABLE_NAME,
                                                     existingTask.getSeriesId() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RtmTaskSeriesColumns.LIST_ID,
                                   existingTask.getListId(),
                                   updatedTask.getListId() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE,
                                                existingTask.getCreatedMillisUtc(),
                                                updatedTask.getCreatedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE,
                                                existingTask.getModifiedMillisUtc(),
                                                updatedTask.getModifiedMillisUtc() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RtmTaskSeriesColumns.TASKSERIES_NAME,
                                   existingTask.getName(),
                                   updatedTask.getName() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RtmTaskSeriesColumns.SOURCE,
                                   existingTask.getSource(),
                                   updatedTask.getSource() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RtmTaskSeriesColumns.URL,
                                   existingTask.getUrl(),
                                   updatedTask.getUrl() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RtmTaskSeriesColumns.TAGS,
                                   TextUtils.join( RtmTaskSeriesColumns.TAGS_SEPARATOR,
                                                   existingTask.getTags() ),
                                   TextUtils.join( RtmTaskSeriesColumns.TAGS_SEPARATOR,
                                                   updatedTask.getTags() ) );
      
      final Long existingTaskLocationId = existingTask.getLocation() != null
                                                                            ? existingTask.getLocation()
                                                                                          .getId()
                                                                            : null;
      
      final Long updateTaskLocationId = updatedTask.getLocation() != null
                                                                         ? updatedTask.getLocation()
                                                                                      .getId()
                                                                         : null;
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RtmTaskSeriesColumns.LOCATION_ID,
                                   existingTaskLocationId,
                                   updateTaskLocationId );
      
      String existingTaskRecurrence = null;
      Boolean existingTaskIsEveryRecurrence = null;
      if ( existingTask.getRecurrence() != null )
      {
         existingTaskRecurrence = existingTask.getRecurrence().getPattern();
         existingTaskIsEveryRecurrence = existingTask.getRecurrence()
                                                     .isEveryRecurrence();
      }
      
      String updateTaskRecurrence = null;
      Boolean updateTaskIsEveryRecurrence = null;
      if ( updatedTask.getRecurrence() != null )
      {
         updateTaskRecurrence = updatedTask.getRecurrence().getPattern();
         updateTaskIsEveryRecurrence = updatedTask.getRecurrence()
                                                  .isEveryRecurrence();
      }
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RtmTaskSeriesColumns.RECURRENCE,
                                   existingTaskRecurrence,
                                   updateTaskRecurrence );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RtmTaskSeriesColumns.RECURRENCE_EVERY,
                                   existingTaskIsEveryRecurrence,
                                   updateTaskIsEveryRecurrence );
   }
   
   
   
   private static void collectRawTaskModifications( Collection< Modification > modifications,
                                                    ITask existingTask,
                                                    ITask updatedTask )
   {
      final Uri entityUri = DbUtils.entityUriWithId( RawTasksTable.TABLE_NAME,
                                                     existingTask.getId() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RawTasksColumns.TASKSERIES_ID,
                                   existingTask.getSeriesId(),
                                   updatedTask.getSeriesId() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                RawTasksColumns.ADDED_DATE,
                                                existingTask.getAddedMillisUtc(),
                                                updatedTask.getAddedMillisUtc() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RawTasksColumns.COMPLETED_DATE,
                                   existingTask.getCompletedMillisUtc(),
                                   updatedTask.getCompletedMillisUtc() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RawTasksColumns.PRIORITY,
                                   existingTask.getPriority().toString(),
                                   updatedTask.getPriority().toString() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RawTasksColumns.POSTPONED,
                                   existingTask.getPostponedCount(),
                                   updatedTask.getPostponedCount() );
      
      Long existingTaskDue = null;
      Boolean existingTaskHasDueTime = null;
      if ( existingTask.getDue() != null )
      {
         existingTaskDue = existingTask.getDue().getMillisUtc();
         existingTaskHasDueTime = existingTask.getDue().hasDueTime();
      }
      
      Long updateTaskDue = null;
      Boolean updateTaskHasDueTime = null;
      if ( updatedTask.getDue() != null )
      {
         updateTaskDue = updatedTask.getDue().getMillisUtc();
         updateTaskHasDueTime = updatedTask.getDue().hasDueTime();
      }
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RawTasksColumns.DUE_DATE,
                                   existingTaskDue,
                                   updateTaskDue );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RawTasksColumns.HAS_DUE_TIME,
                                   existingTaskHasDueTime,
                                   updateTaskHasDueTime );
      
      String existingTaskEstimation = null;
      Long existingTaskEstimationMillis = null;
      if ( existingTask.getEstimation() != null )
      {
         existingTaskEstimation = existingTask.getEstimation().getSentence();
         existingTaskEstimationMillis = existingTask.getEstimation()
                                                    .getMillisUtc();
      }
      
      String updateTaskEstimation = null;
      Long updateTaskEstimationMillis = null;
      if ( updatedTask.getEstimation() != null )
      {
         updateTaskEstimation = updatedTask.getEstimation().getSentence();
         updateTaskEstimationMillis = updatedTask.getEstimation()
                                                 .getMillisUtc();
      }
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RawTasksColumns.ESTIMATE,
                                   existingTaskEstimation,
                                   updateTaskEstimation );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RawTasksColumns.ESTIMATE_MILLIS,
                                   existingTaskEstimationMillis,
                                   updateTaskEstimationMillis );
   }
   
   
   
   private static void collectParticipantsModifications( Collection< Modification > modifications,
                                                         ITask existingTask,
                                                         ITask updatedTask )
   {
      for ( Participant updateParticipant : updatedTask.getParticipants() )
      {
         final Participant existingParticipant = existingTask.getParticipant( updateParticipant.getId() );
         if ( existingParticipant != null )
         {
            final Uri entityUri = DbUtils.entityUriWithId( ParticipantsTable.TABLE_NAME,
                                                           existingParticipant.getId() );
            
            Modification.addIfDifferentNonPersistent( modifications,
                                                      entityUri,
                                                      ParticipantsColumns.CONTACT_ID,
                                                      existingParticipant.getContactId(),
                                                      updateParticipant.getContactId() );
            
            Modification.addIfDifferentNonPersistent( modifications,
                                                      entityUri,
                                                      ParticipantsColumns.USERNAME,
                                                      existingParticipant.getUsername(),
                                                      updateParticipant.getUsername() );
            
            Modification.addIfDifferentNonPersistent( modifications,
                                                      entityUri,
                                                      ParticipantsColumns.FULLNAME,
                                                      existingParticipant.getFullname(),
                                                      updateParticipant.getFullname() );
         }
      }
   }
   
   
   
   private static void collectNotesModifications( Collection< Modification > modifications,
                                                  ITask existingTask,
                                                  ITask updatedTask )
   {
      for ( INote updateNote : updatedTask.getNotes() )
      {
         final INote existingNote = existingTask.getNote( updateNote.getId() );
         if ( existingNote != null )
         {
            final Uri entityUri = DbUtils.entityUriWithId( RtmNotesTable.TABLE_NAME,
                                                           existingNote.getId() );
            
            Modification.addIfDifferentNonPersistent( modifications,
                                                      entityUri,
                                                      RtmNotesColumns.NOTE_CREATED_DATE,
                                                      existingNote.getCreatedMillisUtc(),
                                                      updateNote.getCreatedMillisUtc() );
            
            Modification.addIfDifferentNonPersistent( modifications,
                                                      entityUri,
                                                      RtmNotesColumns.NOTE_MODIFIED_DATE,
                                                      existingNote.getModifiedMillisUtc(),
                                                      updateNote.getModifiedMillisUtc() );
            
            Modification.addIfDifferent( modifications,
                                         entityUri,
                                         RtmNotesColumns.NOTE_DELETED_DATE,
                                         existingNote.getDeletedMillisUtc(),
                                         updateNote.getDeletedMillisUtc() );
            
            Modification.addIfDifferent( modifications,
                                         entityUri,
                                         RtmNotesColumns.NOTE_TITLE,
                                         existingNote.getTitle(),
                                         updateNote.getTitle() );
            
            Modification.addIfDifferent( modifications,
                                         entityUri,
                                         RtmNotesColumns.NOTE_TEXT,
                                         existingNote.getText(),
                                         updateNote.getText() );
         }
      }
   }
   
   
   
   private void removeAddParticipants( ITask existingTask, ITask updatedTask )
   {
      for ( Participant updateParticipant : updatedTask.getParticipants() )
      {
         if ( !existingTask.isParticipating( updateParticipant.getId() ) )
         {
            participantsTable.insert( createParticipantContentValues( updatedTask.getSeriesId(),
                                                                      updateParticipant ) );
         }
      }
      
      for ( Participant existingParticipant : existingTask.getParticipants() )
      {
         if ( !updatedTask.isParticipating( existingParticipant.getId() ) )
         {
            participantsTable.delete( existingParticipant.getId(), null, null );
         }
      }
   }
   
   
   
   private void removeAddNotes( ITask existingTask, ITask updatedTask )
   {
      for ( INote updateNote : updatedTask.getNotes() )
      {
         if ( !existingTask.hasNote( updateNote.getId() ) )
         {
            notesTable.insert( createNoteContentValues( updatedTask.getSeriesId(),
                                                        updateNote ) );
         }
      }
      
      for ( INote existingNote : existingTask.getNotes() )
      {
         if ( !updatedTask.hasNote( existingNote.getId() ) )
         {
            notesTable.delete( existingNote.getId(), null, null );
         }
      }
   }
}
