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

import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_CREATED_DATE;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_CREATED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_DELETED_DATE;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_DELETED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_MODIFIED_DATE;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_MODIFIED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_TEXT;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_TEXT_IDX;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_TITLE;
import static dev.drsoran.moloko.content.Columns.NoteColumns.NOTE_TITLE_IDX;
import static dev.drsoran.moloko.content.Columns.NoteColumns.PROJECTION;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.ClassRule;
import org.junit.Test;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.test.MolokoReadWriteDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.moloko.test.TestConstants;


public class TaskNotesContentTest extends MolokoReadWriteDbContentTestCase
{
   @ClassRule
   public static SQLiteScript environmentScript = new SQLiteScript( TaskNotesContentTest.class,
                                                                    "TaskNotesContentTest_Env.sql" );
   
   @ClassRule
   public static SQLiteScript notesSqliteScript = new SQLiteScript( TaskNotesContentTest.class,
                                                                    "TaskNotesContentTest.sql" );
   
   
   
   @Override
   protected Iterable< String > getSqlStatements()
   {
      final Collection< String > statements = new ArrayList< String >( environmentScript.getSqlStatements() );
      statements.addAll( notesSqliteScript.getSqlStatements() );
      
      return statements;
   }
   
   
   
   @Override
   protected int getNumberOfQueryAllRows()
   {
      return 3;
   }
   
   
   
   @Override
   @Test
   public void testInsert()
   {
      prepareDatabase( environmentScript.getSqlStatements() );
      super.testInsert();
   }
   
   
   
   @Override
   protected ContentValues createInsertContent()
   {
      final ContentValues contentValues = new ContentValues();
      contentValues.put( NOTE_CREATED_DATE, TestConstants.NOW );
      contentValues.put( NOTE_MODIFIED_DATE, TestConstants.LATER );
      contentValues.putNull( NOTE_DELETED_DATE );
      contentValues.putNull( NOTE_TITLE );
      contentValues.put( NOTE_TEXT, "My note text" );
      
      return contentValues;
   }
   
   
   
   @Override
   protected void checkInsertContent( Cursor c )
   {
      checkResult( c,
                   1,
                   TestConstants.NOW,
                   TestConstants.LATER,
                   Constants.NO_TIME,
                   null,
                   "My note text" );
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
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContentUris.bindAggregationIdToUri( ContentUris.TASK_NOTES_CONTENT_URI,
                                                 100L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithId_1()
   {
      return ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                       100L,
                                                       1L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithNotExistingId()
   {
      return ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                       100L,
                                                       500L );
   }
   
   
   
   @Override
   protected String[] getProjection()
   {
      return PROJECTION;
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
                         1356998400003L,
                         1356998400004L,
                         Constants.NO_TIME,
                         "title",
                         "text" );
            break;
         
         case 2:
            checkResult( c,
                         2L,
                         1356998400005L,
                         1356998400006L,
                         1356998400007L,
                         "title1",
                         "text1" );
            break;
         
         case 3:
            checkResult( c,
                         3L,
                         1356998400008L,
                         1356998400009L,
                         Constants.NO_TIME,
                         null,
                         "text2" );
            break;
         
         default :
            fail( "Unknown row ID " + rowId );
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    long id,
                                    long created,
                                    long modified,
                                    long deleted,
                                    String title,
                                    String text )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( id ) );
      assertThat( c.getLong( NOTE_CREATED_DATE_IDX ), is( created ) );
      assertThat( c.getLong( NOTE_MODIFIED_DATE_IDX ), is( modified ) );
      assertThat( CursorUtils.getOptLong( c,
                                          NOTE_DELETED_DATE_IDX,
                                          Constants.NO_TIME ), is( deleted ) );
      assertThat( CursorUtils.getOptString( c, NOTE_TITLE_IDX ), is( title ) );
      assertThat( c.getString( NOTE_TEXT_IDX ), is( text ) );
   }
}
