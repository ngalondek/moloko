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
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmParticipantColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;


public class DeleteNotesParticipantsOfTaskSeriesTest extends
         MolokoDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScript1Task = new SQLiteScript( DeleteNotesParticipantsOfTaskSeriesTest.class,
                                                             "DeleteNotesParticipantsOfTaskSeriesTest.sql" );
   
   
   
   @Test
   public void testDeleteTaskSeries()
   {
      prepareDatabase( sqliteScript1Task.getSqlStatements() );
      checkTestPreconditions();
      
      final int numDeleted = getContentProvider().delete( ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                                                     1L ),
                                                          null,
                                                          null );
      assertThat( numDeleted, is( 1 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_NOTES_TABLE,
                                       RtmNoteColumns.TABLE_PROJECTION,
                                       RtmNoteColumns.TASKSERIES_ID + "= 10",
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 0 ) );
      c.close();
      
      c = getDb().getReadable().query( TableNames.RTM_NOTES_TABLE,
                                       RtmNoteColumns.TABLE_PROJECTION,
                                       RtmNoteColumns.TASKSERIES_ID + "= 11",
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      c.close();
      
      c = getDb().getReadable().query( TableNames.RTM_PARTICIPANTS_TABLE,
                                       RtmParticipantColumns.TABLE_PROJECTION,
                                       RtmParticipantColumns.TASKSERIES_ID
                                          + "= 10",
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 0 ) );
      c.close();
      
      c = getDb().getReadable().query( TableNames.RTM_PARTICIPANTS_TABLE,
                                       RtmParticipantColumns.TABLE_PROJECTION,
                                       RtmParticipantColumns.TASKSERIES_ID
                                          + "= 11",
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      c.close();
   }
   
   
   
   private void checkTestPreconditions()
   {
      c = getDb().getReadable().query( TableNames.RTM_NOTES_TABLE,
                                       RtmNoteColumns.TABLE_PROJECTION,
                                       RtmNoteColumns.TASKSERIES_ID + "= 10",
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 2 ) );
      c.close();
      
      c = getDb().getReadable().query( TableNames.RTM_NOTES_TABLE,
                                       RtmNoteColumns.TABLE_PROJECTION,
                                       RtmNoteColumns.TASKSERIES_ID + "= 11",
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      c.close();
      
      c = getDb().getReadable().query( TableNames.RTM_PARTICIPANTS_TABLE,
                                       RtmParticipantColumns.TABLE_PROJECTION,
                                       RtmParticipantColumns.TASKSERIES_ID
                                          + "= 10",
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 2 ) );
      c.close();
      
      c = getDb().getReadable().query( TableNames.RTM_PARTICIPANTS_TABLE,
                                       RtmParticipantColumns.TABLE_PROJECTION,
                                       RtmParticipantColumns.TASKSERIES_ID
                                          + "= 11",
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      c.close();
   }
}
