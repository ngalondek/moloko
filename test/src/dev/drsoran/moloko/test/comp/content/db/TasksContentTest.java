/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test.comp.content.db;

import static dev.drsoran.moloko.content.Columns.TaskColumns.ADDED_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ADDED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.COMPLETED_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.COMPLETED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.DELETED_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.DELETED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.DUE_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.DUE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE_MILLIS;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE_MILLIS_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.HAS_DUE_TIME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.HAS_DUE_TIME_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_ID_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_NAME_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_ID_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.POSTPONED;
import static dev.drsoran.moloko.content.Columns.TaskColumns.POSTPONED_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.PRIORITY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.PRIORITY_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE_EVERY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE_EVERY_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.SOURCE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.SOURCE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TAGS;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TAGS_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_CREATED_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_CREATED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_MODIFIED_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_MODIFIED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_NAME_IDX;
import static dev.drsoran.moloko.content.Columns.TaskColumns.URL;
import static dev.drsoran.moloko.content.Columns.TaskColumns.URL_IDX;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.test.MolokoReadWriteDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.rtm.test.TestConstants;


public class TasksContentTest extends MolokoReadWriteDbContentTestCase
{
   @Rule
   public SQLiteScript tasksSqliteScript = new SQLiteScript( TasksContentTest.class,
                                                             "TasksContentTest.sql" );
   
   @Rule
   public SQLiteScript taskId10SqliteScript = new SQLiteScript( TasksContentTest.class,
                                                                "TasksContentTest_TaskId_10.sql" );
   
   @Rule
   public SQLiteScript listsSqliteScript = new SQLiteScript( TasksContentTest.class,
                                                             "TasksContentTest_Lists.sql" );
   
   
   
   @Test
   public void testQueryWithSelectionMultipleIds()
   {
      prepareDatabase( getSqlStatements() );
      
      c = getContentProvider().query( getContentUri(),
                                      getProjection(),
                                      TaskColumns._ID + "=1 AND "
                                         + TaskColumns.LOCATION_ID
                                         + " IS NOT NULL AND "
                                         + TaskColumns._ID + " != 2 AND "
                                         + TaskColumns.LIST_ID + " IS NOT NULL",
                                      null,
                                      null );
      assertNotNull( c );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      checkContent( c, 1 );
   }
   
   
   
   @Test
   public void testUpdateInvalidTaskId()
   {
      final Collection< String > statements = new ArrayList< String >( listsSqliteScript.getSqlStatements() );
      statements.addAll( taskId10SqliteScript.getSqlStatements() );
      
      prepareDatabase( statements );
      
      // Trying to update task with ID 1, but it exists only a task with ID 10.
      final int numUpdated = getContentProvider().update( getContentUriWithId_1(),
                                                          createUpdateContentForId_1(),
                                                          null,
                                                          null );
      
      assertThat( numUpdated, is( 0 ) );
   }
   
   
   
   @Override
   protected Iterable< String > getSqlStatements()
   {
      final Collection< String > statements = new ArrayList< String >( listsSqliteScript.getSqlStatements() );
      statements.addAll( tasksSqliteScript.getSqlStatements() );
      
      return statements;
   }
   
   
   
   @Override
   protected int getNumberOfQueryAllRows()
   {
      return 3;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContentUris.TASKS_CONTENT_URI;
   }
   
   
   
   @Override
   protected Uri getContentUriWithId_1()
   {
      return ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID, 1L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithNotExistingId()
   {
      return ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID, 500L );
   }
   
   
   
   @Override
   protected String[] getProjection()
   {
      return TaskColumns.PROJECTION;
   }
   
   
   
   @Override
   protected void checkContent( Cursor c, int rowNumber )
   {
      final long rowId = c.getLong( Columns.ID_IDX );
      
      switch ( (int) rowId )
      {
         case 1:
            checkResult( c,
                         1L,
                         1356998400000L,
                         1356998400001L,
                         "Task1",
                         "JUNIT",
                         "http://abc.de",
                         "every 2 days",
                         1,
                         2000,
                         1000,
                         "List1",
                         "tag1,tag2",
                         1356998400002L,
                         1,
                         1356998400003L,
                         1356998400004L,
                         1356998400005L,
                         "n",
                         2,
                         "1h",
                         36000000 );
            break;
         
         case 2:
            checkResult( c,
                         2L,
                         1356998400000L,
                         1356998400001L,
                         "MultiTask",
                         "JUNIT",
                         null,
                         null,
                         0,
                         Constants.NO_ID,
                         1001,
                         "List2",
                         null,
                         Constants.NO_TIME,
                         0,
                         1356998400003L,
                         Constants.NO_TIME,
                         Constants.NO_TIME,
                         "1",
                         0,
                         null,
                         Constants.NO_TIME );
            break;
         
         case 3:
            checkResult( c,
                         3L,
                         1356998400000L,
                         1356998400001L,
                         "MultiTask",
                         "JUNIT",
                         null,
                         null,
                         0,
                         Constants.NO_ID,
                         1001,
                         "List2",
                         null,
                         1356998400000L,
                         0,
                         1356998400004L,
                         1356998400005L,
                         Constants.NO_TIME,
                         "2",
                         0,
                         "1 day",
                         86400000 );
            break;
         
         default :
            fail( "Unknown row ID " + rowId );
      }
   }
   
   
   
   @Override
   @Test
   public void testInsert()
   {
      prepareDatabase( listsSqliteScript.getSqlStatements() );
      super.testInsert();
   }
   
   
   
   @Override
   protected ContentValues createInsertContent()
   {
      final ContentValues contentValues = new ContentValues();
      contentValues.put( TASK_CREATED_DATE, TestConstants.NOW );
      contentValues.put( TASK_MODIFIED_DATE, TestConstants.LATER );
      contentValues.put( TASK_NAME, "InsertedTask" );
      contentValues.put( SOURCE, "Source" );
      contentValues.put( URL, "URL" );
      contentValues.put( RECURRENCE, "Recurrence" );
      contentValues.put( RECURRENCE_EVERY, 1 );
      contentValues.put( LOCATION_ID, 2000L );
      contentValues.put( LIST_ID, 1000L );
      contentValues.put( TAGS, "T1,T2" );
      contentValues.put( DUE_DATE, TestConstants.EVEN_LATER );
      contentValues.put( HAS_DUE_TIME, 1 );
      contentValues.put( ADDED_DATE, TestConstants.NOW );
      contentValues.put( COMPLETED_DATE, TestConstants.LATER );
      contentValues.putNull( DELETED_DATE );
      contentValues.put( PRIORITY, "1" );
      contentValues.put( POSTPONED, 5 );
      contentValues.put( ESTIMATE, "Estimate" );
      contentValues.put( ESTIMATE_MILLIS, 7200000 );
      
      return contentValues;
   }
   
   
   
   @Override
   protected void checkInsertContent( Cursor c )
   {
      checkResult( c,
                   1L,
                   TestConstants.NOW,
                   TestConstants.LATER,
                   "InsertedTask",
                   "Source",
                   "URL",
                   "Recurrence",
                   1,
                   2000,
                   1000,
                   "List1",
                   "T1,T2",
                   TestConstants.EVEN_LATER,
                   1,
                   TestConstants.NOW,
                   TestConstants.LATER,
                   Constants.NO_TIME,
                   "1",
                   5,
                   "Estimate",
                   7200000 );
   }
   
   
   
   @Override
   protected ContentValues createUpdateContentForId_1()
   {
      return createInsertContent();
   }
   
   
   
   @Override
   protected void checkUpdatedContent( Cursor c )
   {
      checkInsertContent( c );
   }
   
   
   
   private static void checkResult( Cursor c,
                                    long id,
                                    long created,
                                    long modified,
                                    String name,
                                    String source,
                                    String url,
                                    String recurrence,
                                    int recurrenceEvery,
                                    long locationId,
                                    long listId,
                                    String listName,
                                    String tags,
                                    long due,
                                    int hasDueTime,
                                    long added,
                                    long completed,
                                    long deleted,
                                    String priority,
                                    int postponed,
                                    String estimate,
                                    long estimateMillis )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( id ) );
      assertThat( c.getLong( TASK_CREATED_DATE_IDX ), is( created ) );
      assertThat( c.getLong( TASK_MODIFIED_DATE_IDX ), is( modified ) );
      assertThat( c.getString( TASK_NAME_IDX ), is( name ) );
      assertThat( CursorUtils.getOptString( c, SOURCE_IDX ), is( source ) );
      assertThat( CursorUtils.getOptString( c, URL_IDX ), is( url ) );
      assertThat( CursorUtils.getOptString( c, RECURRENCE_IDX ),
                  is( recurrence ) );
      assertThat( c.getInt( RECURRENCE_EVERY_IDX ), is( recurrenceEvery ) );
      assertThat( CursorUtils.getOptLong( c, LOCATION_ID_IDX, Constants.NO_ID ),
                  is( locationId ) );
      assertThat( c.getLong( LIST_ID_IDX ), is( listId ) );
      assertThat( c.getString( LIST_NAME_IDX ), is( listName ) );
      assertThat( CursorUtils.getOptString( c, TAGS_IDX ), is( tags ) );
      assertThat( CursorUtils.getOptLong( c, DUE_IDX, Constants.NO_TIME ),
                  is( due ) );
      assertThat( c.getInt( HAS_DUE_TIME_IDX ), is( hasDueTime ) );
      assertThat( c.getLong( ADDED_DATE_IDX ), is( added ) );
      assertThat( CursorUtils.getOptLong( c,
                                          COMPLETED_DATE_IDX,
                                          Constants.NO_TIME ), is( completed ) );
      assertThat( CursorUtils.getOptLong( c,
                                          DELETED_DATE_IDX,
                                          Constants.NO_TIME ), is( deleted ) );
      assertThat( c.getString( PRIORITY_IDX ), is( priority ) );
      assertThat( c.getInt( POSTPONED_IDX ), is( postponed ) );
      assertThat( CursorUtils.getOptString( c, ESTIMATE_IDX ), is( estimate ) );
      assertThat( c.getLong( ESTIMATE_MILLIS_IDX ), is( estimateMillis ) );
   }
}
