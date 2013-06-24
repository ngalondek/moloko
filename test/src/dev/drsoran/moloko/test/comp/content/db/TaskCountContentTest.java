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

import static dev.drsoran.moloko.content.Columns.TaskCountColumns.COMPLETED_IDX;
import static dev.drsoran.moloko.content.Columns.TaskCountColumns.DUE_TODAY_IDX;
import static dev.drsoran.moloko.content.Columns.TaskCountColumns.DUE_TOMORROW_IDX;
import static dev.drsoran.moloko.content.Columns.TaskCountColumns.INCOMPLETE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskCountColumns.OVERDUE_IDX;
import static dev.drsoran.moloko.content.Columns.TaskCountColumns.PROJECTION;
import static dev.drsoran.moloko.content.Columns.TaskCountColumns.SUM_ESTIMATED_IDX;
import static dev.drsoran.moloko.test.SQLiteScriptVariables.VAR_TODAY;
import static dev.drsoran.moloko.test.SQLiteScriptVariables.VAR_TOMORROW;
import static dev.drsoran.moloko.test.SQLiteScriptVariables.VAR_YESTERDAY;
import static dev.drsoran.moloko.test.TestConstants.TODAY;
import static dev.drsoran.moloko.test.TestConstants.TOMORROW;
import static dev.drsoran.moloko.test.TestConstants.YESTERDAY;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import org.junit.Rule;
import org.junit.Test;

import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateUtils;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.test.MolokoReadDbContentTestCase;
import dev.drsoran.moloko.test.ProcessedSQLiteScript;
import dev.drsoran.moloko.test.SQLiteScript;


public class TaskCountContentTest extends MolokoReadDbContentTestCase
{
   @Rule
   public ProcessedSQLiteScript sqliteScript = new ProcessedSQLiteScript( new SQLiteScript( TasksContentTest.class,
                                                                                            "TaskCountContentTest.sql" ) ).replaceScriptVariable( VAR_TODAY,
                                                                                                                                                  TODAY )
                                                                                                                          .replaceScriptVariable( VAR_TOMORROW,
                                                                                                                                                  TOMORROW )
                                                                                                                          .replaceScriptVariable( VAR_YESTERDAY,
                                                                                                                                                  YESTERDAY )
                                                                                                                          .replaceScriptVariable( "${in2Days}",
                                                                                                                                                  TOMORROW
                                                                                                                                                     + DateUtils.DAY_IN_MILLIS );
   
   
   
   @Override
   public void testInsertWithId()
   {
      assumeTrue( false );
      fail( "We have no content URI with ID for tasks count" );
   }
   
   
   
   @Override
   @Test
   public void testQueryWithId()
   {
      assumeTrue( false );
      fail( "We have no content URI with ID for tasks count" );
   }
   
   
   
   @Override
   @Test
   public void testQueryWithNonExistingId()
   {
      assumeTrue( false );
      fail( "We have no content URI with ID for tasks count" );
   }
   
   
   
   @Override
   @Test
   public void testDeleteWithId()
   {
      assumeTrue( false );
      fail( "We have no content URI with ID for tasks count" );
   }
   
   
   
   @Override
   @Test
   public void testUpdateWithId()
   {
      assumeTrue( false );
      fail( "We have no content URI with ID for tasks count" );
   }
   
   
   
   @Override
   @Test
   public void testQueryWithSelection()
   {
      prepareDatabase( getSqlStatements() );
      
      c = getContentProvider().query( getContentUri(),
                                      getProjection(),
                                      TaskColumns.DELETED_DATE + " IS NULL",
                                      null,
                                      null );
      assertNotNull( c );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, 5, 1, 1, 1, 1, 44400000 );
   }
   
   
   
   @Override
   @Test
   public void testQueryWithSelectionArgs()
   {
      prepareDatabase( getSqlStatements() );
      
      c = getContentProvider().query( getContentUri(),
                                      getProjection(),
                                      TaskColumns.ESTIMATE_MILLIS + " > ?",
                                      new String[]
                                      { "600000" },
                                      null );
      assertNotNull( c );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, 2, 2, 1, 1, 0, 43200000 );
   }
   
   
   
   @Override
   protected Iterable< String > getSqlStatements()
   {
      return sqliteScript.getSqlStatements();
   }
   
   
   
   @Override
   protected int getNumberOfQueryAllRows()
   {
      return 1;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContentUris.TASKS_COUNT_CONTENT_URI;
   }
   
   
   
   @Override
   protected Uri getContentUriWithId_1()
   {
      return null;
   }
   
   
   
   @Override
   protected Uri getContentUriWithNotExistingId()
   {
      return null;
   }
   
   
   
   @Override
   protected String[] getProjection()
   {
      return PROJECTION;
   }
   
   
   
   @Override
   protected void checkContent( Cursor c, int rowNumber )
   {
      switch ( rowNumber )
      {
         case 1:
            checkResult( c, 5, 2, 1, 1, 1, 44400000 );
            break;
         
         default :
            fail( "Unexpected row number " + rowNumber );
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    int incomplCnt,
                                    int complCnt,
                                    int dueTod,
                                    int dueTom,
                                    int overdue,
                                    long sumEst )
   {
      assertThat( c.getInt( INCOMPLETE_IDX ), is( incomplCnt ) );
      assertThat( c.getInt( COMPLETED_IDX ), is( complCnt ) );
      assertThat( c.getInt( DUE_TODAY_IDX ), is( dueTod ) );
      assertThat( c.getInt( DUE_TOMORROW_IDX ), is( dueTom ) );
      assertThat( c.getInt( OVERDUE_IDX ), is( overdue ) );
      assertThat( c.getLong( SUM_ESTIMATED_IDX ), is( sumEst ) );
   }
}
