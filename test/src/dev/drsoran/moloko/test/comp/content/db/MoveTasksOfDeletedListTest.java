/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import android.content.ContentValues;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.moloko.test.TestConstants;


public class MoveTasksOfDeletedListTest extends MolokoDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScript = new SQLiteScript( MoveTasksOfDeletedListTest.class,
                                                        "MoveTasksOfDeletedListTest.sql" );
   
   
   
   @Test
   public void testMove_ByListDelete() throws IOException
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      checkTestPreconditions();
      
      getContentProvider().delete( ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                              2 ),
                                   null,
                                   null );
      
      c = getDb().getReadable().query( TableNames.RTM_TASK_SERIES_TABLE,
                                       RtmTaskSeriesColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 2 ) );
      while ( c.moveToNext() )
      {
         assertThat( c.getLong( RtmTaskSeriesColumns.LIST_ID_IDX ), is( 1L ) );
         assertThat( c.getString( RtmTaskSeriesColumns.RTM_LIST_ID_IDX ),
                     is( "1000" ) );
      }
   }
   
   
   
   @Test
   public void testMove_ByListDeletedDateUpdate() throws IOException
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      checkTestPreconditions();
      
      final ContentValues contentValues = new ContentValues();
      contentValues.put( TasksListColumns.LIST_DELETED_DATE, TestConstants.NOW );
      
      getContentProvider().update( ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                              2 ),
                                   contentValues,
                                   null,
                                   null );
      
      c = getDb().getReadable().query( TableNames.RTM_TASK_SERIES_TABLE,
                                       RtmTaskSeriesColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 2 ) );
      while ( c.moveToNext() )
      {
         assertThat( c.getLong( RtmTaskSeriesColumns.LIST_ID_IDX ), is( 1L ) );
         assertThat( c.getString( RtmTaskSeriesColumns.RTM_LIST_ID_IDX ),
                     is( "1000" ) );
      }
   }
   
   
   
   private void checkTestPreconditions()
   {
      c = getDb().getReadable().query( TableNames.RTM_TASK_SERIES_TABLE,
                                       RtmTaskSeriesColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 2 ) );
      c.close();
      
      c = getDb().getReadable().query( TableNames.RTM_TASKS_LIST_TABLE,
                                       RtmTasksListColumns.TABLE_PROJECTION,
                                       RtmTasksListColumns.LIST_NAME
                                          + " LIKE 'Inbox'",
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      c.close();
   }
}
