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

import org.junit.Rule;
import org.junit.Test;

import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;


public class DeleteUnreferencedTaskSeriesTest extends MolokoDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScript1Task = new SQLiteScript( DeleteUnreferencedTaskSeriesTest.class,
                                                             "DeleteUnreferencedTaskSeriesTest_1Task.sql" );
   
   @Rule
   public SQLiteScript sqliteScript2Tasks = new SQLiteScript( DeleteUnreferencedTaskSeriesTest.class,
                                                              "DeleteUnreferencedTaskSeriesTest_2Tasks.sql" );
   
   
   
   @Test
   public void testDeleteButStillReferenced()
   {
      prepareDatabase( sqliteScript2Tasks.getSqlStatements() );
      checkTestPreconditions( 2 );
      
      final int numDeleted = getContentProvider().delete( ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                                                     1L ),
                                                          null,
                                                          null );
      assertThat( numDeleted, is( 1 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_TASK_SERIES_TABLE,
                                       RtmTaskSeriesColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.moveToNext(), is( true ) );
   }
   
   
   
   @Test
   public void testDeleteLastTask()
   {
      prepareDatabase( sqliteScript1Task.getSqlStatements() );
      checkTestPreconditions( 1 );
      
      final int numDeleted = getContentProvider().delete( ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                                                     1L ),
                                                          null,
                                                          null );
      assertThat( numDeleted, is( 1 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_TASK_SERIES_TABLE,
                                       RtmTaskSeriesColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.moveToNext(), is( false ) );
   }
   
   
   
   private void checkTestPreconditions( int expectedCount )
   {
      c = getDb().getReadable().query( TableNames.RTM_RAW_TASKS_TABLE,
                                       RtmRawTaskColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( expectedCount ) );
      c.close();
   }
}
