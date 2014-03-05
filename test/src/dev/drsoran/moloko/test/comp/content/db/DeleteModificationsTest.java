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

import dev.drsoran.moloko.content.db.TableColumns.ModificationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;


public class DeleteModificationsTest extends MolokoDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScriptTask = new SQLiteScript( DeleteModificationsTest.class,
                                                            "DeleteModificationsTest.sql" );
   
   
   
   @Test
   public void testDeleteOrphanedModifications() throws IOException
   {
      prepareDatabase( sqliteScriptTask.getSqlStatements() );
      checkTestPreconditions();
      
      final int numDeleted = getDb().getWritable()
                                    .delete( TableNames.RTM_TASKS_LIST_TABLE,
                                             RtmTasksListColumns._ID + "=1",
                                             null );
      assertThat( numDeleted, is( 1 ) );
      
      c = getDb().getReadable()
                 .query( TableNames.MODIFICATIONS_TABLE,
                         ModificationColumns.PROJECTION,
                         ModificationColumns.ENTITY_URI
                            + " = 'content://dev.drsoran.provider.Rtm/lists/1'",
                         null,
                         null,
                         null,
                         null );
      
      assertThat( c.getCount(), is( 0 ) );
      c.close();
      
      c = getDb().getReadable().query( TableNames.MODIFICATIONS_TABLE,
                                       ModificationColumns.PROJECTION,
                                       ModificationColumns._ID + "=3",
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
   }
   
   
   
   private void checkTestPreconditions()
   {
      c = getDb().getReadable()
                 .query( TableNames.MODIFICATIONS_TABLE,
                         ModificationColumns.PROJECTION,
                         ModificationColumns.ENTITY_URI
                            + " = 'content://dev.drsoran.provider.Rtm/lists/1'",
                         null,
                         null,
                         null,
                         null );
      
      assertThat( c.getCount(), is( 2 ) );
      c.close();
   }
}
