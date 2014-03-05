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

import android.content.ContentValues;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;


public class UpdateListIdOfTaskseriesTest extends MolokoDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScriptTask = new SQLiteScript( UpdateListIdOfTaskseriesTest.class,
                                                            "UpdateListIdOfTaskseriesTest.sql" );
   
   
   
   @Test
   public void testUpdateListId()
   {
      prepareDatabase( sqliteScriptTask.getSqlStatements() );
      checkTestPreconditions();
      
      final ContentValues contentValues = new ContentValues();
      contentValues.put( RtmTaskSeriesColumns.LIST_ID, 1001L );
      
      final int numUpdated = getDb().getWritable()
                                    .update( TableNames.RTM_TASK_SERIES_TABLE,
                                             contentValues,
                                             RtmTaskSeriesColumns._ID + "=10",
                                             null );
      assertThat( numUpdated, is( 1 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_TASK_SERIES_TABLE,
                                       RtmTaskSeriesColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      assertThat( c.getLong( RtmTaskSeriesColumns.LIST_ID_IDX ), is( 1001L ) );
      assertThat( c.getString( RtmTaskSeriesColumns.RTM_LIST_ID_IDX ),
                  is( "10001" ) );
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
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      assertThat( c.getLong( RtmTaskSeriesColumns.LIST_ID_IDX ), is( 1000L ) );
      assertThat( c.getString( RtmTaskSeriesColumns.RTM_LIST_ID_IDX ),
                  is( "10000" ) );
      
      c.close();
   }
}
