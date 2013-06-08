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

import static android.provider.BaseColumns._ID;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.ARCHIVED;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.ARCHIVED_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.FILTER;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.FILTER_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.IS_SMART_LIST;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.IS_SMART_LIST_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_CREATED_DATE;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_CREATED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_DELETED_DATE;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_DELETED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_MODIFIED_DATE;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_MODIFIED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_NAME;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_NAME_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LOCKED;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LOCKED_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.POSITION;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.POSITION_IDX;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.robolectric.annotation.Config;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.test.MolokoReadWriteDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.moloko.test.TestConstants;


@Config( manifest = Config.NONE )
public class TasksListsContentTest extends MolokoReadWriteDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScript = new SQLiteScript( TasksListsContentTest.class,
                                                        "TasksListsContentTest.sql" );
   
   
   
   @Override
   protected Iterable< String > getSqlStatements()
   {
      return sqliteScript.getSqlStatements();
   }
   
   
   
   @Override
   protected int getNumberOfQueryAllRows()
   {
      return 3;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContentUris.TASKS_LISTS_CONTENT_URI;
   }
   
   
   
   @Override
   protected Uri getContentUriWithId_1()
   {
      return ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                        1L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithNotExistingId()
   {
      return ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                        500L );
   }
   
   
   
   @Override
   protected String[] getProjection()
   {
      return TasksListColumns.PROJECTION;
   }
   
   
   
   @Override
   protected void checkContent( Cursor c, long rowId )
   {
      switch ( (int) rowId )
      {
         case 1:
            checkResult( c,
                         1L,
                         "NonSmartList",
                         1356998400000L,
                         1356998400001L,
                         Constants.NO_TIME,
                         0,
                         0,
                         -1,
                         0,
                         null );
            break;
         
         case 2:
            checkResult( c,
                         2L,
                         "SmartList",
                         1356998400000L,
                         1356998400001L,
                         Constants.NO_TIME,
                         0,
                         1,
                         0,
                         1,
                         "(name:test)" );
            break;
         
         case 3:
            checkResult( c,
                         3L,
                         "DeletedList",
                         1356998400000L,
                         1356998400001L,
                         1356998400002L,
                         1,
                         0,
                         1,
                         0,
                         null );
            break;
         
         default :
            fail( "Unknown row ID " + rowId );
      }
   }
   
   
   
   @Override
   protected ContentValues createInsertContent()
   {
      return createContent( null,
                            "list",
                            TestConstants.NOW,
                            TestConstants.NOW,
                            null,
                            0,
                            0,
                            0,
                            0,
                            null );
   }
   
   
   
   @Override
   protected void checkInsertContent( Cursor c )
   {
      checkResult( c,
                   1L,
                   "list",
                   TestConstants.NOW,
                   TestConstants.NOW,
                   Constants.NO_TIME,
                   0,
                   0,
                   0,
                   0,
                   null );
   }
   
   
   
   @Override
   protected ContentValues createUpdateContentForId_1()
   {
      return createContent( null,
                            "NewListName",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null );
   }
   
   
   
   @Override
   protected void checkUpdatedContent( Cursor c )
   {
      checkResult( c,
                   1L,
                   "NewListName",
                   1356998400000L,
                   1356998400001L,
                   Constants.NO_TIME,
                   0,
                   0,
                   -1,
                   0,
                   null );
   }
   
   
   
   private static void checkResult( Cursor c,
                                    long id,
                                    String name,
                                    long created,
                                    long modified,
                                    long deleted,
                                    int locked,
                                    int archived,
                                    int position,
                                    int isSmart,
                                    String filter )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( id ) );
      assertThat( c.getString( LIST_NAME_IDX ), is( name ) );
      assertThat( c.getLong( LIST_CREATED_DATE_IDX ), is( created ) );
      assertThat( c.getLong( LIST_MODIFIED_DATE_IDX ), is( modified ) );
      assertThat( CursorUtils.getOptLong( c,
                                          LIST_DELETED_DATE_IDX,
                                          Constants.NO_TIME ), is( deleted ) );
      assertThat( c.getInt( LOCKED_IDX ), is( locked ) );
      assertThat( c.getInt( ARCHIVED_IDX ), is( archived ) );
      assertThat( c.getInt( POSITION_IDX ), is( position ) );
      assertThat( c.getInt( IS_SMART_LIST_IDX ), is( isSmart ) );
      assertThat( CursorUtils.getOptString( c, FILTER_IDX ), is( filter ) );
   }
   
   
   
   private static ContentValues createContent( Long id,
                                               String name,
                                               Long created,
                                               Long modified,
                                               Long deleted,
                                               Integer locked,
                                               Integer archived,
                                               Integer position,
                                               Integer isSmart,
                                               String filter )
   {
      final ContentValues contentValues = new ContentValues();
      
      if ( id != null )
      {
         contentValues.put( _ID, id );
      }
      if ( name != null )
      {
         contentValues.put( LIST_NAME, name );
      }
      if ( created != null )
      {
         contentValues.put( LIST_CREATED_DATE, created );
      }
      if ( modified != null )
      {
         contentValues.put( LIST_MODIFIED_DATE, modified );
      }
      if ( deleted != null )
      {
         contentValues.put( LIST_DELETED_DATE, deleted );
      }
      if ( locked != null )
      {
         contentValues.put( LOCKED, locked );
      }
      if ( archived != null )
      {
         contentValues.put( ARCHIVED, archived );
      }
      if ( position != null )
      {
         contentValues.put( POSITION, position );
      }
      if ( isSmart != null )
      {
         contentValues.put( IS_SMART_LIST, isSmart );
      }
      if ( filter != null )
      {
         contentValues.put( FILTER, filter );
      }
      
      return contentValues;
   }
}
