/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import android.database.Cursor;
import android.text.TextUtils;
import dev.drsoran.moloko.content.db.Columns.ParticipantsColumns;
import dev.drsoran.moloko.content.db.Columns.RawTasksColumns;
import dev.drsoran.moloko.content.db.Columns.RtmListsColumns;
import dev.drsoran.moloko.content.db.Columns.RtmLocationsColumns;
import dev.drsoran.moloko.content.db.Columns.RtmNotesColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.domain.model.Constants;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.ITask;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.Priority;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.TaskContentOptions;
import dev.drsoran.moloko.util.MolokoDateUtils;


class DbTasksContentRepositoryPart
{
   private final static String TASKS_QUERY;
   
   private final RtmDatabase database;
   
   private RtmNotesQuery notesQuery;
   
   private ParticipantsQuery participantsQuery;
   
   
   
   public DbTasksContentRepositoryPart( RtmDatabase database )
   {
      this.database = database;
   }
   
   static
   {
      final StringBuilder builder = new StringBuilder( "SELECT " );
      
      // Columns
      // 0
      builder.append( RawTasksTable.TABLE_NAME )
             .append( "." )
             .append( RawTasksColumns._ID )
             .append( " AS rawTask_id" )
             // 1
             .append( RtmTaskSeriesTable.TABLE_NAME )
             .append( "." )
             .append( RtmTaskSeriesColumns._ID )
             .append( " AS rtmtaskseries_id" )
             // 2
             .append( RtmListsTable.TABLE_NAME )
             .append( "." )
             .append( RtmListsColumns._ID )
             .append( " AS rtmtaskslist_id" )
             // 3
             .append( RtmTaskSeriesColumns.RTM_TASKSERIES_ID )
             // 4
             .append( RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE )
             // 5
             .append( RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE )
             // 6
             .append( RtmTaskSeriesColumns.TASKSERIES_NAME )
             // 7
             .append( RtmTaskSeriesColumns.SOURCE )
             // 8
             .append( RtmTaskSeriesColumns.URL )
             // 9
             .append( RtmTaskSeriesColumns.TAGS )
             // 10
             .append( RtmTaskSeriesColumns.RECURRENCE )
             // 11
             .append( RtmTaskSeriesColumns.RECURRENCE_EVERY )
             // 12
             .append( RtmTaskSeriesColumns.LOCATION_ID )
             // 13
             .append( RawTasksColumns.ADDED_DATE )
             // 14
             .append( RawTasksColumns.DELETED_DATE )
             // 15
             .append( RawTasksColumns.COMPLETED_DATE )
             // 16
             .append( RawTasksColumns.PRIORITY )
             // 17
             .append( RawTasksColumns.DUE_DATE )
             // 18
             .append( RawTasksColumns.HAS_DUE_TIME )
             // 19
             .append( RawTasksColumns.ESTIMATE )
             // 20
             .append( RawTasksColumns.ESTIMATE_MILLIS )
             // 21
             .append( RawTasksColumns.POSTPONED )
             // 22
             .append( RtmListsColumns.LIST_NAME )
             // 23
             .append( RtmLocationsColumns.LOCATION_NAME )
             // 24
             .append( RtmLocationsColumns.LONGITUDE )
             // 25
             .append( RtmLocationsColumns.LATITUDE )
             // 26
             .append( RtmLocationsColumns.ADDRESS )
             // 27
             .append( RtmLocationsColumns.VIEWABLE )
             // 28
             .append( RtmLocationsColumns.ZOOM );
      
      // Tables
      builder.append( " FROM " )
             .append( RtmTaskSeriesTable.TABLE_NAME )
             .append( "," )
             .append( RawTasksTable.TABLE_NAME )
             .append( "," )
             .append( RtmListsTable.TABLE_NAME );
      
      // Joins
      builder.append( " LEFT OUTER JOIN " )
             .append( RtmLocationsTable.TABLE_NAME )
             .append( " ON " )
             .append( RtmTaskSeriesTable.TABLE_NAME )
             .append( "." )
             .append( RtmTaskSeriesColumns.LOCATION_ID )
             .append( "=" )
             .append( RtmLocationsTable.TABLE_NAME )
             .append( "." )
             .append( RtmLocationsColumns._ID );
      
      // Where
      builder.append( RtmTaskSeriesTable.TABLE_NAME )
             .append( "." )
             .append( RtmTaskSeriesColumns.LIST_ID )
             .append( "=" )
             .append( RtmListsTable.TABLE_NAME )
             .append( "." )
             .append( RtmListsColumns._ID )
             .append( " AND " )
             .append( RtmTaskSeriesTable.TABLE_NAME )
             .append( "." )
             .append( RtmTaskSeriesColumns._ID )
             .append( "=" )
             .append( RawTasksTable.TABLE_NAME )
             .append( "." )
             .append( RawTasksColumns.TASKSERIES_ID );
      
      TASKS_QUERY = builder.toString();
   }
   
   
   
   public ITask getById( long id, int options ) throws ContentException,
                                               NoSuchElementException
   {
      final String selection = new StringBuilder( "AND rawTask_id=" ).append( id )
                                                                     .toString();
      
      final Iterator< ITask > tasksIterator = getAll( selection, options ).iterator();
      
      if ( !tasksIterator.hasNext() )
      {
         throw new NoSuchElementException( "No Task with ID '" + id
            + "' found." );
      }
      
      return tasksIterator.next();
   }
   
   
   
   public Iterable< ITask > getAll( String selection, int options ) throws ContentException
   {
      return getTasksFromDb( selection, options );
   }
   
   
   
   public ExtendedTaskCount getCount( String selection ) throws ContentException
   {
      Cursor c = null;
      
      try
      {
         final String rawQuery = selection != null ? TASKS_QUERY + " AND "
            + selection : TASKS_QUERY;
         
         c = database.getReadable().rawQuery( rawQuery, null );
         return createTaskCount( c );
      }
      catch ( Throwable e )
      {
         throw new ContentException( e );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private List< ITask > getTasksFromDb( String selection, int options ) throws ContentException
   {
      final List< ITask > tasks = new ArrayList< ITask >();
      Cursor c = null;
      
      try
      {
         final String rawQuery = selection != null ? TASKS_QUERY + " AND "
            + selection : TASKS_QUERY;
         c = database.getReadable().rawQuery( rawQuery, null );
         
         if ( c.moveToFirst() )
         {
            do
            {
               final Task task = createTaskFromCursor( c );
               final long taskSeriesId = c.getLong( 1 );
               
               if ( ( options & TaskContentOptions.WITH_NOTES ) != 0 )
               {
                  addNotesToTask( task, taskSeriesId );
               }
               
               if ( ( options & TaskContentOptions.WITH_PARTICIPANTS ) != 0 )
               {
                  addParticipantsToTask( task, taskSeriesId );
               }
               
               tasks.add( task );
            }
            while ( c.moveToNext() );
         }
      }
      catch ( Throwable e )
      {
         throw new ContentException( e );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
      
      return tasks;
   }
   
   
   
   private void addNotesToTask( Task task, long taskSeriesId )
   {
      Cursor c = null;
      try
      {
         c = getNotesQuery().getNotesOfTaskSeries( taskSeriesId );
         
         while ( c.moveToNext() )
         {
            final Note note = createNoteFromCursor( c );
            task.addNote( note );
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
   
   
   
   private void addParticipantsToTask( Task task, long taskSeriesId )
   {
      Cursor c = null;
      try
      {
         c = getParticipantsQuery().getParticipants( taskSeriesId );
         
         while ( c.moveToNext() )
         {
            final Participant participant = createParticipantFromCursor( c );
            task.addParticipant( participant );
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
   
   
   
   private static void addLocationToTask( Cursor c, Task task, long locationId )
   {
      final Location location = createLocationFromCursor( c, locationId );
      task.setLocation( location );
   }
   
   
   
   private static Task createTaskFromCursor( Cursor c )
   {
      final Task task = new Task( c.getLong( 0 ),
                                  c.getString( 6 ),
                                  c.getLong( 4 ),
                                  c.getLong( 13 ),
                                  c.getLong( 2 ),
                                  c.getString( 22 ) );
      
      task.setModifiedMillisUtc( c.getLong( 4 ) );
      task.setSource( CursorUtils.getOptString( c, 7 ) );
      task.setUrl( CursorUtils.getOptString( c, 8 ) );
      task.setDeletedMillisUtc( CursorUtils.getOptLong( c,
                                                        14,
                                                        Constants.NO_TIME ) );
      task.setCompletedMillisUtc( CursorUtils.getOptLong( c,
                                                          15,
                                                          Constants.NO_TIME ) );
      task.setPriority( Priority.fromString( c.getString( 16 ) ) );
      
      if ( !c.isNull( 9 ) )
      {
         final String[] tags = TextUtils.split( c.getString( 9 ),
                                                RtmTaskSeriesColumns.TAGS_SEPARATOR );
         task.setTags( Arrays.asList( tags ) );
      }
      
      if ( !c.isNull( 10 ) )
      {
         task.setRecurrence( new Recurrence( c.getString( 10 ),
                                             c.getInt( 11 ) != 0 ) );
      }
      
      if ( !c.isNull( 12 ) )
      {
         addLocationToTask( c, task, c.getLong( 12 ) );
      }
      
      if ( !c.isNull( 17 ) )
      {
         task.setDue( new Due( c.getLong( 17 ), c.getInt( 18 ) != 0 ) );
      }
      
      if ( !c.isNull( 19 ) )
      {
         task.setEstimation( new Estimation( c.getString( 19 ), c.getLong( 20 ) ) );
      }
      
      return task;
   }
   
   
   
   private static Note createNoteFromCursor( Cursor c )
   {
      final Note note = new Note( c.getLong( Columns.ID_IDX ),
                                  c.getLong( RtmNotesColumns.NOTE_CREATED_DATE_IDX ) );
      
      note.setModifiedMillisUtc( c.getLong( RtmNotesColumns.NOTE_MODIFIED_DATE_IDX ) );
      note.setDeletedMillisUtc( CursorUtils.getOptLong( c,
                                                        RtmNotesColumns.NOTE_DELETED_DATE_IDX,
                                                        Constants.NO_TIME ) );
      note.setTitle( CursorUtils.getOptString( c,
                                               RtmNotesColumns.NOTE_TITLE_IDX ) );
      note.setText( c.getString( RtmNotesColumns.NOTE_TEXT_IDX ) );
      
      return note;
   }
   
   
   
   private static Participant createParticipantFromCursor( Cursor c )
   {
      final Participant participant = new Participant( c.getLong( Columns.ID_IDX ),
                                                       c.getLong( ParticipantsColumns.CONTACT_ID_IDX ),
                                                       c.getString( ParticipantsColumns.FULLNAME_IDX ),
                                                       c.getString( ParticipantsColumns.USERNAME_IDX ) );
      return participant;
   }
   
   
   
   private static Location createLocationFromCursor( Cursor c, long locationId )
   {
      final Location location = new Location( locationId,
                                              CursorUtils.getOptString( c, 23 ),
                                              c.getFloat( 24 ),
                                              c.getFloat( 25 ),
                                              CursorUtils.getOptString( c, 26 ),
                                              c.getInt( 27 ) != 0,
                                              CursorUtils.getOptInt( c, 28, 10 ) );
      return location;
   }
   
   
   
   private ExtendedTaskCount createTaskCount( Cursor c )
   {
      int incompleteTaskCount = 0;
      int completedTaskCount = 0;
      int dueTodayTaskCount = 0;
      int dueTomorrowTaskCount = 0;
      int overDueTaskCount = 0;
      long sumEstimated = 0;
      
      while ( c.moveToNext() )
      {
         final boolean isCompleted = !c.isNull( 15 );
         
         if ( !isCompleted )
         {
            final long dueMillisUtc = CursorUtils.getOptLong( c,
                                                              17,
                                                              Constants.NO_TIME );
            
            if ( dueMillisUtc != Constants.NO_TIME )
            {
               final int diffToNow = MolokoDateUtils.getTimespanInDays( System.currentTimeMillis(),
                                                                        dueMillisUtc );
               
               // Today?
               if ( diffToNow == 0 )
               {
                  ++dueTodayTaskCount;
               }
               
               // Tomorrow?
               else if ( diffToNow == 1 )
               {
                  ++dueTomorrowTaskCount;
               }
               
               // Overdue?
               else if ( diffToNow < 0 )
               {
                  ++overDueTaskCount;
               }
            }
            
            // Sum up estimated times
            final long estimateMillis = CursorUtils.getOptLong( c,
                                                                20,
                                                                Constants.NO_TIME );
            
            if ( estimateMillis != Constants.NO_TIME )
            {
               sumEstimated += estimateMillis;
            }
         }
         
         // Completed?
         if ( isCompleted )
         {
            ++completedTaskCount;
         }
      }
      
      return new ExtendedTaskCount( incompleteTaskCount,
                                    completedTaskCount,
                                    dueTodayTaskCount,
                                    dueTomorrowTaskCount,
                                    overDueTaskCount,
                                    sumEstimated );
   }
   
   
   
   private RtmNotesQuery getNotesQuery()
   {
      if ( notesQuery == null )
      {
         notesQuery = database.getQuery( RtmNotesQuery.class );
      }
      
      return notesQuery;
   }
   
   
   
   private ParticipantsQuery getParticipantsQuery()
   {
      if ( participantsQuery == null )
      {
         participantsQuery = database.getQuery( ParticipantsQuery.class );
      }
      
      return participantsQuery;
   }
}
