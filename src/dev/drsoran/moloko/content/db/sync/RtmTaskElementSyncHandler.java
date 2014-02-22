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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import dev.drsoran.db.ITable;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;


public class RtmTaskElementSyncHandler implements
         IDbElementSyncHandler< RtmTask >
{
   private final static String RTM_TASKS_QUERY_TABLE_NAMES;
   
   private final static String RTM_TASKS_QUERY_WHERE_CLAUSE_MODIFIED_SINCE;
   
   private final RtmDatabase rtmDatabase;
   
   private final IModelElementFactory modelElementFactory;
   
   private final IContentValuesFactory contentValuesFactory;
   
   static
   {
      RTM_TASKS_QUERY_TABLE_NAMES = TableNames.RTM_TASK_SERIES_TABLE + ", "
         + TableNames.RTM_RAW_TASKS_TABLE;
      
      RTM_TASKS_QUERY_WHERE_CLAUSE_MODIFIED_SINCE = TableNames.RTM_TASK_SERIES_TABLE
         + "."
         + RtmTaskSeriesColumns._ID
         + "="
         + TableNames.RTM_RAW_TASKS_TABLE
         + "."
         + RtmRawTaskColumns.TASKSERIES_ID
         + " AND "
         + TableNames.RTM_TASK_SERIES_TABLE
         + "."
         + RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE + ">=?";
   }
   
   
   
   public RtmTaskElementSyncHandler( RtmDatabase rtmDatabase,
      IModelElementFactory modelElementFactory,
      IContentValuesFactory contentValuesFactory )
   {
      if ( rtmDatabase == null )
      {
         throw new IllegalArgumentException( "rtmDatabase" );
      }
      
      if ( modelElementFactory == null )
      {
         throw new IllegalArgumentException( "modelElementFactory" );
      }
      
      if ( contentValuesFactory == null )
      {
         throw new IllegalArgumentException( "contentValuesFactory" );
      }
      
      this.rtmDatabase = rtmDatabase;
      this.modelElementFactory = modelElementFactory;
      this.contentValuesFactory = contentValuesFactory;
   }
   
   
   
   @Override
   public List< RtmTask > getElementsModifiedSince( long modifiedSinceMsUtc )
   {
      return getElementsModifiedSinceImpl( modifiedSinceMsUtc );
   }
   
   
   
   @Override
   public void insert( RtmTask task )
   {
      final ContentValues contentValues = contentValuesFactory.createContentValues( task );
      
      final ContentValues taskSeriesValues = getRtmTaskSeriesSubset( contentValues );
      ITable table = rtmDatabase.getTable( TableNames.RTM_TASK_SERIES_TABLE );
      long taskSeriesId = table.insert( taskSeriesValues );
      
      final ContentValues rawTaskValues = getRtmRawTaskSubset( contentValues );
      rawTaskValues.put( RtmRawTaskColumns.TASKSERIES_ID, taskSeriesId );
      
      table = rtmDatabase.getTable( TableNames.RTM_RAW_TASKS_TABLE );
      table.insert( rawTaskValues );
      
      insertNotes( task.getNotes(), taskSeriesId );
      insertParticipants( task.getParticipants(), taskSeriesId );
   }
   
   
   
   private void insertNotes( Collection< ? extends RtmNote > notes,
                             long taskSeriesId )
   {
      final ITable notesTable = rtmDatabase.getTable( TableNames.RTM_NOTES_TABLE );
      for ( RtmNote rtmNote : notes )
      {
         final ContentValues contentValues = contentValuesFactory.createContentValues( rtmNote );
         contentValues.put( RtmNoteColumns.TASKSERIES_ID, taskSeriesId );
         
         notesTable.insert( contentValues );
      }
   }
   
   
   
   private void insertParticipants( Collection< RtmContact > participants,
                                    long taskSeriesId )
   {
      final ITable participantsTable = rtmDatabase.getTable( TableNames.RTM_PARTICIPANTS_TABLE );
      for ( RtmContact contact : participants )
      {
         final Participant participant = new Participant( id,
                                                          contactId,
                                                          fullname,
                                                          username );
         final ContentValues contentValues = contentValuesFactory.createContentValues( rtmNote );
         contentValues.put( RtmNoteColumns.TASKSERIES_ID, taskSeriesId );
         
         participantsTable.insert( contentValues );
      }
   }
   
   
   
   @Override
   public void update( RtmTask currentTask, RtmTask updatedTask )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   @Override
   public void delete( RtmTask task )
   {
      // TODO Auto-generated method stub
      
   }
   
   
   
   private List< RtmTask > getElementsModifiedSinceImpl( long modifiedSinceMsUtc )
   {
      Cursor c = null;
      try
      {
         final String rawQueryString = getRtmTasksQueryBuilder().toString();
         
         c = rtmDatabase.getReadable().rawQuery( rawQueryString, new String[]
         { String.valueOf( modifiedSinceMsUtc ) } );
         
         final List< RtmTask > result = new ArrayList< RtmTask >( c.getCount() );
         while ( c.moveToNext() )
         {
            final RtmTask task = modelElementFactory.createElementFromCursor( c,
                                                                              RtmTask.class );
            addNotes( task, c.getLong( Columns.ID_IDX ) );
            
            result.add( task );
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
   
   
   
   private void addNotes( RtmTask task, long taskseriesId )
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
            final RtmNote note = modelElementFactory.createElementFromCursor( c,
                                                                              RtmNote.class );
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
   
   
   
   private SQLiteQueryBuilder getRtmTasksQueryBuilder()
   {
      final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
      queryBuilder.setTables( RTM_TASKS_QUERY_TABLE_NAMES );
      queryBuilder.appendWhere( RTM_TASKS_QUERY_WHERE_CLAUSE_MODIFIED_SINCE );
      
      return queryBuilder;
   }
   
   
   
   private ContentValues getRtmTaskSeriesSubset( ContentValues contentValues )
   {
      return getContentValueSubset( contentValues,
                                    RtmTaskSeriesColumns.TABLE_PROJECTION );
   }
   
   
   
   private ContentValues getRtmRawTaskSubset( ContentValues contentValues )
   {
      return getContentValueSubset( contentValues,
                                    RtmRawTaskColumns.TABLE_PROJECTION );
   }
   
   
   
   private ContentValues getContentValueSubset( ContentValues contentValues,
                                                String[] projection )
   {
      final ContentValues contentValuesSubset = new ContentValues();
      
      for ( String column : projection )
      {
         if ( contentValues.containsKey( column ) )
         {
            final Object value = contentValues.get( column );
            if ( value != null )
            {
               contentValuesSubset.put( column, String.valueOf( value ) );
            }
            else
            {
               contentValuesSubset.putNull( column );
            }
         }
      }
      
      return contentValuesSubset;
   }
}
