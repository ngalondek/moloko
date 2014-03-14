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

import org.junit.Rule;
import org.junit.Test;

import android.content.ContentValues;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.test.MolokoDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.moloko.test.TestConstants;


public class PreventDeleteLockedListTest extends MolokoDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScript = new SQLiteScript( PreventDeleteLockedListTest.class,
                                                        "PreventDeleteLockedListTest.sql" );
   
   
   
   @Test( expected = Exception.class )
   public void testDeleteLockedList_ByDelete()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      getContentProvider().delete( ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                              1 ),
                                   null,
                                   null );
   }
   
   
   
   @Test( expected = Exception.class )
   public void testDeleteLockedList_ByUpdate()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      
      final ContentValues contentValues = new ContentValues();
      contentValues.put( TasksListColumns.LIST_DELETED_DATE, TestConstants.NOW );
      
      getContentProvider().update( ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                              1 ),
                                   contentValues,
                                   null,
                                   null );
   }
}
