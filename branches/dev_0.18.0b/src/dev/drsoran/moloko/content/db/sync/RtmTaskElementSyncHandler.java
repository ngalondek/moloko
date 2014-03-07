/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.moloko.content.db.sync;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import dev.drsoran.Strings;
import dev.drsoran.db.ITable;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Columns.ContactColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmParticipantColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.util.Lambda.Func1;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmRawTask;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTaskSeries;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;


public class RtmTaskElementSyncHandler implements
         IDbElementSyncHandler< RtmTask >
{
   private final RtmDatabase rtmDatabase;
   
   private final IModelElementFactory rtmModelElementFactory;
   
   private final IContentValuesFactory rtmContentValuesFactory;
   
   private final IContentValuesFactory molokoContentValuesFactory;
   
   private final ITaskSeriesIdProvider taskSeriesIdProvider;
   
   private final IRtmDateTimeParsing dateTimeParsing;
   
   private final ILog log;
   
   private final Set< String > updatedTaskSerieses = new HashSet< String >();
   
   private final Map< String, Long > insertedTaskSerieses = new HashMap< String, Long >();
   
   private final Map< String, Long > rtmContactId2contactId = new HashMap< String, Long >();
   
   private final Func1< String, Cursor > contactIdQueryFunc = new Func1< String, Cursor >()
   {
      @Override
      public Cursor call( String param )
      {
         final ITable table = rtmDatabase.getTable( TableNames.RTM_CONTACTS_TABLE );
         return table.query( new String[]
                             { ContactColumns._ID,
                              RtmContactColumns.RTM_CONTACT_ID },
                             RtmContactColumns.RTM_CONTACT_ID + "=?",
                             new String[]
                             { param },
                             null );
      }
   };
   
   private final Map< String, Long > rtmListId2ListId = new HashMap< String, Long >();
   
   private final Func1< String, Cursor > listIdQueryFunc = new Func1< String, Cursor >()
   {
      @Override
      public Cursor call( String param )
      {
         final ITable table = rtmDatabase.getTable( TableNames.RTM_TASKS_LIST_TABLE );
         return table.query( new String[]
                             { RtmTasksListColumns._ID,
                              RtmTasksListColumns.RTM_LIST_ID },
                             RtmTasksListColumns.RTM_LIST_ID + "=?",
                             new String[]
                             { param },
                             null );
      }
   };
   
   private final Map< String, Long > rtmLocationId2LocationId = new HashMap< String, Long >();
   
   private final Func1< String, Cursor > locationIdQueryFunc = new Func1< String, Cursor >()
   {
      @Override
      public Cursor call( String param )
      {
         final ITable table = rtmDatabase.getTable( TableNames.RTM_LOCATIONS_TABLE );
         return table.query( new String[]
                             { RtmLocationColumns._ID,
                              RtmLocationColumns.RTM_LOCATION_ID },
                             RtmLocationColumns.RTM_LOCATION_ID + "=?",
                             new String[]
                             { param },
                             null );
      }
   };
   
   
   
   public RtmTaskElementSyncHandler( RtmDatabase rtmDatabase,
      IModelElementFactory rtmModelElementFactory,
      IContentValuesFactory rtmContentValuesFactory,
      IContentValuesFactory molokoContentValuesFactory,
      ITaskSeriesIdProvider taskSeriesIdProvider,
      IRtmDateTimeParsing dateTimeParsing, ILog log )
   {
      if ( rtmDatabase == null )
      {
         throw new IllegalArgumentException( "rtmDatabase" );
      }
      
      if ( rtmModelElementFactory == null )
      {
         throw new IllegalArgumentException( "rtmModelElementFactory" );
      }
      
      if ( rtmContentValuesFactory == null )
      {
         throw new IllegalArgumentException( "rtmContentValuesFactory" );
      }
      
      if ( molokoContentValuesFactory == null )
      {
         throw new IllegalArgumentException( "molokoContentValuesFactory" );
      }
      
      if ( taskSeriesIdProvider == null )
      {
         throw new IllegalArgumentException( "taskSeriesIdProvider" );
      }
      
      if ( dateTimeParsing == null )
      {
         throw new IllegalArgumentException( "dateTimeParsing" );
      }
      
      this.rtmDatabase = rtmDatabase;
      this.rtmModelElementFactory = rtmModelElementFactory;
      this.rtmContentValuesFactory = rtmContentValuesFactory;
      this.molokoContentValuesFactory = molokoContentValuesFactory;
      this.taskSeriesIdProvider = taskSeriesIdProvider;
      this.dateTimeParsing = dateTimeParsing;
      this.log = log;
   }
   
   
   
   @Override
   public List< RtmTask > getElementsModifiedSince( long modifiedSinceMsUtc )
   {
      return getElementsModifiedSinceImpl( modifiedSinceMsUtc );
   }
   
   
   
   @Override
   public void insert( RtmTask task )
   {
      Long taskSeriesId = tryGetInsertedTaskSeriesId( task.getTaskSeriesId() );
      
      if ( taskSeriesId == null )
      {
         final RtmTaskSeries taskSeries = task.getTaskSeries();
         
         final ContentValues taskSeriesValues = rtmContentValuesFactory.createContentValues( taskSeries );
         putListIdAndLocationIdForTaskSeries( taskSeries, taskSeriesValues );
         
         final ITable table = rtmDatabase.getTable( TableNames.RTM_TASK_SERIES_TABLE );
         taskSeriesId = Long.valueOf( table.insert( taskSeriesValues ) );
         
         setTaskSeriesInserted( task.getTaskSeriesId(), taskSeriesId );
      }
      
      final ContentValues rawTaskValues = rtmContentValuesFactory.createContentValues( task.getRawTask() );
      rawTaskValues.put( RtmRawTaskColumns.TASKSERIES_ID, taskSeriesId );
      rawTaskValues.put( RtmRawTaskColumns.ESTIMATE_MILLIS,
                         getEstimateMillis( task.getEstimationSentence() ) );
      
      final ITable table = rtmDatabase.getTable( TableNames.RTM_RAW_TASKS_TABLE );
      table.insert( rawTaskValues );
      
      insertNotes( task.getNotes(), taskSeriesId );
      insertParticipants( task.getParticipants(), taskSeriesId );
   }
   
   
   
   private void insertNotes( Collection< ? extends RtmNote > notes,
                             long taskSeriesId )
   {
      for ( RtmNote rtmNote : notes )
      {
         insertNote( rtmNote, taskSeriesId );
      }
   }
   
   
   
   private void insertNote( RtmNote note, long taskSeriesId )
   {
      final ITable notesTable = rtmDatabase.getTable( TableNames.RTM_NOTES_TABLE );
      final ContentValues contentValues = rtmContentValuesFactory.createContentValues( note );
      contentValues.put( RtmNoteColumns.TASKSERIES_ID, taskSeriesId );
      
      notesTable.insert( contentValues );
   }
   
   
   
   private void insertParticipants( Collection< RtmContact > participants,
                                    long taskSeriesId )
   {
      for ( RtmContact contact : participants )
      {
         insertParticipant( contact, taskSeriesId );
      }
   }
   
   
   
   private void insertParticipant( RtmContact rtmContact, long taskSeriesId )
   {
      final ITable participantsTable = rtmDatabase.getTable( TableNames.RTM_PARTICIPANTS_TABLE );
      final long contactId = getContactIdFromRtmContactId( rtmContact.getId() );
      final Participant participant = new Participant( Constants.NO_ID,
                                                       contactId,
                                                       rtmContact.getFullname(),
                                                       rtmContact.getUsername() );
      
      final ContentValues contentValues = molokoContentValuesFactory.createContentValues( participant );
      contentValues.put( RtmNoteColumns.TASKSERIES_ID, taskSeriesId );
      
      participantsTable.insert( contentValues );
   }
   
   
   
   @Override
   public void update( RtmTask currentTask, RtmTask updatedTask )
   {
      if ( !currentTask.getId().equals( updatedTask.getId() ) )
      {
         throw new IllegalArgumentException( MessageFormat.format( "IDs differ in update. {0} != {1}",
                                                                   currentTask,
                                                                   updatedTask ) );
      }
      
      final String rtmTaskSeriedId = currentTask.getTaskSeriesId();
      
      if ( !hasTaskSeriesAlreadyUpdated( rtmTaskSeriedId ) )
      {
         final RtmTaskSeries updatedTaskSeries = updatedTask.getTaskSeries();
         final ContentValues taskSeriesValues = rtmContentValuesFactory.createContentValues( updatedTaskSeries );
         putListIdAndLocationIdForTaskSeries( updatedTaskSeries,
                                              taskSeriesValues );
         
         final ITable taskSeriesTable = rtmDatabase.getTable( TableNames.RTM_TASK_SERIES_TABLE );
         taskSeriesTable.update( Constants.NO_ID,
                                 taskSeriesValues,
                                 RtmTaskSeriesColumns.RTM_TASKSERIES_ID + "=?",
                                 new String[]
                                 { currentTask.getTaskSeriesId() } );
         
         final long taskseriesId = taskSeriesIdProvider.getTaskSeriesIdOfRtmTaskSeriesId( rtmTaskSeriedId );
         
         updateNotes( taskseriesId, currentTask, updatedTask );
         
         updateParticipants( taskseriesId, currentTask, updatedTask );
         
         setTaskSeriesUpdated( rtmTaskSeriedId );
      }
      
      final ContentValues rawTaskValues = rtmContentValuesFactory.createContentValues( updatedTask.getRawTask() );
      final ITable rawTaskTable = rtmDatabase.getTable( TableNames.RTM_RAW_TASKS_TABLE );
      rawTaskTable.update( Constants.NO_ID,
                           rawTaskValues,
                           RtmRawTaskColumns.RTM_RAWTASK_ID + "=?",
                           new String[]
                           { currentTask.getId() } );
   }
   
   
   
   private void updateNotes( long taskSeriesId,
                             RtmTask currentTask,
                             RtmTask updatedTask )
   {
      for ( RtmNote updatedNote : updatedTask.getNotes() )
      {
         final RtmNote existingNote = currentTask.getNote( updatedNote.getId() );
         
         if ( existingNote == null )
         {
            insertNote( updatedNote, taskSeriesId );
         }
         else
         {
            updateNote( existingNote, updatedNote );
         }
      }
      
      for ( RtmNote existingNote : currentTask.getNotes() )
      {
         if ( !updatedTask.hasNote( existingNote.getId() ) )
         {
            deleteNote( existingNote );
         }
      }
   }
   
   
   
   private void updateNote( RtmNote currentNote, RtmNote updatedNote )
   {
      if ( !currentNote.getId().equals( updatedNote.getId() ) )
      {
         throw new IllegalArgumentException( MessageFormat.format( "IDs differ in update. {0} != {1}",
                                                                   currentNote,
                                                                   updatedNote ) );
      }
      
      final ITable rtmNotesTable = rtmDatabase.getTable( TableNames.RTM_NOTES_TABLE );
      final ContentValues noteContentValues = rtmContentValuesFactory.createContentValues( updatedNote );
      
      rtmNotesTable.update( Constants.NO_ID,
                            noteContentValues,
                            RtmNoteColumns.RTM_NOTE_ID + "=?",
                            new String[]
                            { currentNote.getId() } );
   }
   
   
   
   private void updateParticipants( long taskSeriesId,
                                    RtmTask currentTask,
                                    RtmTask updatedTask )
   {
      for ( RtmContact updatedParticipant : updatedTask.getParticipants() )
      {
         final RtmContact existingParticipant = currentTask.getParticipant( updatedParticipant.getId() );
         
         if ( existingParticipant == null )
         {
            insertParticipant( updatedParticipant, taskSeriesId );
         }
         
         // No update for participants because the changeable parts like user name are updated by a trigger
      }
      
      for ( RtmContact existingParticipant : currentTask.getParticipants() )
      {
         if ( updatedTask.getParticipant( existingParticipant.getId() ) == null )
         {
            deleteParticipant( existingParticipant );
         }
      }
   }
   
   
   
   private Long tryGetInsertedTaskSeriesId( String rtmTaskSeriedId )
   {
      return insertedTaskSerieses.get( rtmTaskSeriedId );
   }
   
   
   
   private void setTaskSeriesInserted( String rtmTaskSeriedId, Long taskSeriesId )
   {
      insertedTaskSerieses.put( rtmTaskSeriedId, taskSeriesId );
   }
   
   
   
   private boolean hasTaskSeriesAlreadyUpdated( String taskSeriedId )
   {
      return updatedTaskSerieses.contains( taskSeriedId );
   }
   
   
   
   private void setTaskSeriesUpdated( String taskSeriedId )
   {
      updatedTaskSerieses.add( taskSeriedId );
   }
   
   
   
   /**
    * We only delete the task. The task series, notes, participants are deleted by DB triggers, if no longer referenced.
    */
   @Override
   public void delete( RtmTask task )
   {
      final ITable rawTasksTable = rtmDatabase.getTable( TableNames.RTM_RAW_TASKS_TABLE );
      final int numDeleted = rawTasksTable.delete( Constants.NO_ID,
                                                   RtmRawTaskColumns.RTM_RAWTASK_ID
                                                      + "=?",
                                                   new String[]
                                                   { task.getId() } );
      
      if ( numDeleted < 1 )
      {
         throw new SQLiteException( MessageFormat.format( "{0} not deleted.",
                                                          task ) );
      }
   }
   
   
   
   private void deleteNote( RtmNote note )
   {
      final ITable rtmNotesTable = rtmDatabase.getTable( TableNames.RTM_NOTES_TABLE );
      final int numDeleted = rtmNotesTable.delete( Constants.NO_ID,
                                                   RtmNoteColumns.RTM_NOTE_ID
                                                      + "=?",
                                                   new String[]
                                                   { note.getId() } );
      
      if ( numDeleted < 1 )
      {
         throw new SQLiteException( MessageFormat.format( "{0} not deleted.",
                                                          note ) );
      }
   }
   
   
   
   private void deleteParticipant( RtmContact contact )
   {
      final long contactId = getContactIdFromRtmContactId( contact.getId() );
      final ITable rtmParticipantsTable = rtmDatabase.getTable( TableNames.RTM_PARTICIPANTS_TABLE );
      final int numDeleted = rtmParticipantsTable.delete( Constants.NO_ID,
                                                          RtmParticipantColumns.CONTACT_ID
                                                             + "=?",
                                                          new String[]
                                                          { String.valueOf( contactId ) } );
      
      if ( numDeleted < 1 )
      {
         throw new SQLiteException( MessageFormat.format( "Participant {0} not deleted.",
                                                          contact ) );
      }
   }
   
   
   
   private List< RtmTask > getElementsModifiedSinceImpl( long modifiedSinceMsUtc )
   {
      Cursor c = null;
      try
      {
         c = rtmDatabase.getTable( TableNames.RTM_TASK_SERIES_TABLE )
                        .query( RtmTaskSeriesColumns.TABLE_PROJECTION,
                                RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE
                                   + ">=?",
                                new String[]
                                { String.valueOf( modifiedSinceMsUtc ) },
                                null );
         
         final List< RtmTask > result = new ArrayList< RtmTask >( c.getCount() );
         while ( c.moveToNext() )
         {
            final RtmTaskSeries taskSeries = rtmModelElementFactory.createElementFromCursor( c,
                                                                                             RtmTaskSeries.class );
            final long taskSeriesId = c.getLong( Columns.ID_IDX );
            addNotes( taskSeries, taskSeriesId );
            
            for ( RtmRawTask rawTask : getRawTasksOfTaskSeries( taskSeriesId ) )
            {
               result.add( new RtmTask( taskSeries, rawTask ) );
            }
         }
         
         return result;
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private Iterable< RtmRawTask > getRawTasksOfTaskSeries( long taskSeriesId )
   {
      Cursor c = null;
      try
      {
         c = rtmDatabase.getTable( TableNames.RTM_RAW_TASKS_TABLE )
                        .query( RtmRawTaskColumns.TABLE_PROJECTION,
                                RtmRawTaskColumns.TASKSERIES_ID + "=?",
                                new String[]
                                { String.valueOf( taskSeriesId ) },
                                null );
         
         final List< RtmRawTask > result = new ArrayList< RtmRawTask >( c.getCount() );
         while ( c.moveToNext() )
         {
            final RtmRawTask rawTask = rtmModelElementFactory.createElementFromCursor( c,
                                                                                       RtmRawTask.class );
            result.add( rawTask );
         }
         
         return result;
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private void addNotes( RtmTaskSeries taskSeries, long taskseriesId )
   {
      Cursor c = null;
      try
      {
         c = rtmDatabase.getReadable()
                        .query( TableNames.RTM_NOTES_TABLE,
                                RtmNoteColumns.TABLE_PROJECTION,
                                TableNames.RTM_NOTES_TABLE + "."
                                   + RtmNoteColumns.TASKSERIES_ID + "=?"
                                   + " AND " + TableNames.RTM_NOTES_TABLE + "."
                                   + RtmNoteColumns.NOTE_DELETED_DATE + "="
                                   + RtmConstants.NO_TIME,
                                new String[]
                                { String.valueOf( taskseriesId ) },
                                null,
                                null,
                                null );
         
         while ( c.moveToNext() )
         {
            final RtmNote note = rtmModelElementFactory.createElementFromCursor( c,
                                                                                 RtmNote.class );
            taskSeries.addNote( note );
         }
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private void putListIdAndLocationIdForTaskSeries( RtmTaskSeries taskSeries,
                                                     ContentValues contentValues )
   {
      contentValues.put( RtmTaskSeriesColumns.LIST_ID,
                         getListIdFromRtmListId( taskSeries.getListId() ) );
      
      if ( taskSeries.getLocationId() != RtmConstants.NO_ID )
      {
         contentValues.put( RtmTaskSeriesColumns.LOCATION_ID,
                            getLocationIdFromRtmLocationId( taskSeries.getLocationId() ) );
      }
      else
      {
         contentValues.put( RtmTaskSeriesColumns.LOCATION_ID, Constants.NO_ID );
      }
   }
   
   
   
   private long getContactIdFromRtmContactId( String rtmContactId )
   {
      Long storedId = rtmContactId2contactId.get( rtmContactId );
      if ( storedId == null )
      {
         storedId = getIdFromDatabase( rtmContactId, contactIdQueryFunc );
         rtmContactId2contactId.put( rtmContactId, storedId );
      }
      
      return storedId.longValue();
   }
   
   
   
   private long getListIdFromRtmListId( String rtmListId )
   {
      Long storedId = rtmListId2ListId.get( rtmListId );
      if ( storedId == null )
      {
         storedId = getIdFromDatabase( rtmListId, listIdQueryFunc );
         rtmListId2ListId.put( rtmListId, storedId );
      }
      
      return storedId.longValue();
   }
   
   
   
   private long getLocationIdFromRtmLocationId( String rtmLocationId )
   {
      Long storedId = rtmLocationId2LocationId.get( rtmLocationId );
      if ( storedId == null )
      {
         storedId = getIdFromDatabase( rtmLocationId, locationIdQueryFunc );
         rtmLocationId2LocationId.put( rtmLocationId, storedId );
      }
      
      return storedId.longValue();
   }
   
   
   
   private long getIdFromDatabase( String rtmId, Func1< String, Cursor > func )
   {
      Cursor c = null;
      try
      {
         c = func.call( rtmId );
         
         if ( !c.moveToFirst() )
         {
            throw new SQLiteException( MessageFormat.format( "Unable to query ID for RTM ID {0}",
                                                             rtmId ) );
         }
         
         return c.getLong( 0 );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private long getEstimateMillis( String estimationSentence )
   {
      if ( !Strings.isNullOrEmpty( estimationSentence ) )
      {
         try
         {
            return dateTimeParsing.parseEstimated( estimationSentence );
         }
         catch ( GrammarException e )
         {
            log.e( getClass(),
                   MessageFormat.format( "Unable to parse estimation sentence {0}",
                                         estimationSentence ),
                   e );
         }
      }
      
      return Constants.NO_TIME;
   }
}
