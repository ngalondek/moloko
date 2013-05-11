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

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;


class TasksContentUriHandler extends AbstractContentUriHandler implements
         ITaskSeriesIdProvider
{
   private final static String TASK_QUERY_TABLE_NAMES;
   
   private final static String TASK_QUERY_ALL_WHERE_CLAUSE;
   
   private final static Map< String, String > TASK_QUERY_PROJECTION_MAP;
   
   private final static SQLiteQueryBuilder TASK_SERIES_OF_RAW_TASK_QUERY;
   
   private final SQLiteDatabase database;
   
   private final ITable rtmTaskSeriesTable;
   
   private final ITable rtmRawTasksTable;
   
   static
   {
      TASK_QUERY_TABLE_NAMES = RtmTaskSeriesTable.TABLE_NAME + ", "
         + RtmRawTasksTable.TABLE_NAME + ", " + RtmTaskSeriesTable.TABLE_NAME
         + " LEFT OUTER JOIN " + RtmLocationsTable.TABLE_NAME + " ON "
         + RtmTaskSeriesTable.TABLE_NAME + "."
         + RtmTaskSeriesColumns.LOCATION_ID + "="
         + RtmLocationsTable.TABLE_NAME + "." + RtmLocationColumns._ID;
      
      TASK_QUERY_ALL_WHERE_CLAUSE = RtmTaskSeriesTable.TABLE_NAME + "."
         + RtmTaskSeriesColumns.LIST_ID + "=" + RtmTasksListsTable.TABLE_NAME
         + "." + RtmTasksListColumns._ID + " AND "
         + RtmTaskSeriesTable.TABLE_NAME + "." + RtmTaskSeriesColumns._ID + "="
         + RtmRawTasksTable.TABLE_NAME + "." + RtmRawTaskColumns.TASKSERIES_ID;
      
      TASK_QUERY_PROJECTION_MAP = new HashMap< String, String >( TaskColumns.PROJECTION.length );
      for ( String column : TaskColumns.PROJECTION )
      {
         // We map the Task ID to the RawTask ID
         if ( TaskColumns._ID.equals( column ) )
         {
            TASK_QUERY_PROJECTION_MAP.put( column, RtmRawTasksTable.TABLE_NAME
               + "." + RtmRawTaskColumns._ID );
         }
         else
         {
            TASK_QUERY_PROJECTION_MAP.put( column, column );
         }
      }
      
      TASK_SERIES_OF_RAW_TASK_QUERY = new SQLiteQueryBuilder();
      TASK_SERIES_OF_RAW_TASK_QUERY.setTables( RtmRawTasksTable.TABLE_NAME );
      TASK_SERIES_OF_RAW_TASK_QUERY.appendWhere( RtmRawTaskColumns._ID + "=?" );
   }
   
   
   
   public TasksContentUriHandler( ILog log, SQLiteDatabase database,
      ITable rtmTaskSeriesTable, ITable rtmRawTasksTable )
   {
      super( log );
      
      this.database = database;
      this.rtmTaskSeriesTable = rtmTaskSeriesTable;
      this.rtmRawTasksTable = rtmRawTasksTable;
   }
   
   
   
   @Override
   public long getTaskSeriesIdOfTask( long taskId )
   {
      Cursor rawTaskCursor = null;
      try
      {
         rawTaskCursor = rtmRawTasksTable.query( new String[]
                                                 { RtmRawTaskColumns.TASKSERIES_ID },
                                                 RtmRawTaskColumns._ID + "=?",
                                                 new String[]
                                                 { String.valueOf( taskId ) },
                                                 null );
         if ( !rawTaskCursor.moveToNext() )
         {
            return Constants.NO_ID;
         }
         
         return rawTaskCursor.getLong( 0 );
      }
      finally
      {
         if ( rawTaskCursor != null )
         {
            rawTaskCursor.close();
         }
      }
   }
   
   
   
   @Override
   protected Cursor queryElement( Uri contentUri,
                                  long id,
                                  String[] projection,
                                  String selection,
                                  String[] selectionArgs,
                                  String sortOrder )
   {
      final SQLiteQueryBuilder queryBuilder = getTasksQueryBuilder();
      queryBuilder.appendWhere( TaskColumns._ID + "=" + String.valueOf( id ) );
      
      return queryBuilder.query( database,
                                 projection,
                                 selection,
                                 selectionArgs,
                                 null,
                                 null,
                                 sortOrder );
   }
   
   
   
   @Override
   protected Cursor queryAll( Uri contentUri,
                              String[] projection,
                              String selection,
                              String[] selectionArgs,
                              String sortOrder )
   {
      final SQLiteQueryBuilder queryBuilder = getTasksQueryBuilder();
      return queryBuilder.query( database,
                                 projection,
                                 selection,
                                 selectionArgs,
                                 null,
                                 null,
                                 sortOrder );
   }
   
   
   
   @Override
   protected long insertElement( Uri contentUri, ContentValues initialValues )
   {
      final ContentValues taskSeriesValues = getRtmTaskSeriesSubset( initialValues );
      final ContentValues rawTaskValues = getRtmRawTaskSubset( initialValues );
      
      final long taskSeriesId = rtmTaskSeriesTable.insert( taskSeriesValues );
      
      rawTaskValues.put( RtmRawTaskColumns.TASKSERIES_ID, taskSeriesId );
      final long rowId = rtmRawTasksTable.insert( rawTaskValues );
      
      return rowId;
   }
   
   
   
   @Override
   protected int updateElement( Uri contentUri, long id, ContentValues values )
   {
      final long taskSeriesId = getTaskSeriesIdOfTask( id );
      
      final ContentValues taskSeriesValues = getRtmTaskSeriesSubset( values );
      final ContentValues rawTaskValues = getRtmRawTaskSubset( values );
      
      rtmTaskSeriesTable.update( taskSeriesId, taskSeriesValues, null, null );
      
      final int numUpdated = rtmRawTasksTable.update( id,
                                                      rawTaskValues,
                                                      null,
                                                      null );
      return numUpdated;
   }
   
   
   
   @Override
   protected int deleteElement( Uri contentUri,
                                long id,
                                String where,
                                String[] whereArgs )
   {
      int numdeleted = 0;
      Cursor tasksCursor = null;
      try
      {
         tasksCursor = query( contentUri, new String[]
         { TaskColumns._ID }, where, whereArgs, null );
         
         while ( tasksCursor.moveToNext() )
         {
            numdeleted += rtmRawTasksTable.delete( tasksCursor.getLong( 0 ),
                                                   null,
                                                   null );
         }
      }
      finally
      {
         if ( tasksCursor != null )
         {
            tasksCursor.close();
         }
      }
      
      return numdeleted;
   }
   
   
   
   @Override
   protected int deleteAll( Uri contentUri, String where, String[] whereArgs )
   {
      return rtmRawTasksTable.delete( Constants.NO_ID, where, whereArgs );
   }
   
   
   
   private SQLiteQueryBuilder getTasksQueryBuilder()
   {
      final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
      queryBuilder.setTables( TASK_QUERY_TABLE_NAMES );
      queryBuilder.setProjectionMap( TASK_QUERY_PROJECTION_MAP );
      queryBuilder.appendWhere( TASK_QUERY_ALL_WHERE_CLAUSE );
      
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
