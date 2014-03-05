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

import static dev.drsoran.moloko.content.Columns.CloudEntryColumns.COUNT_IDX;
import static dev.drsoran.moloko.content.Columns.CloudEntryColumns.DISPLAY_IDX;
import static dev.drsoran.moloko.content.Columns.CloudEntryColumns.ELEMENT_ID_IDX;
import static dev.drsoran.moloko.content.Columns.CloudEntryColumns.ENTRY_TYPE_IDX;
import static dev.drsoran.moloko.content.Columns.CloudEntryColumns.PROJECTION;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import org.junit.Rule;
import org.junit.Test;

import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.CloudEntryType;
import dev.drsoran.moloko.test.MolokoReadDbContentTestCase;
import dev.drsoran.moloko.test.ProcessedSQLiteScript;
import dev.drsoran.moloko.test.SQLiteScript;


public class CloudEntriesContentTest extends MolokoReadDbContentTestCase
{
   @Rule
   public ProcessedSQLiteScript sqliteScript = new ProcessedSQLiteScript( new SQLiteScript( CloudEntriesContentTest.class,
                                                                                            "CloudEntriesContentTest.sql" ) );
   
   
   
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
      
      assertThat( c.getCount(), is( 6 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, CloudEntryType.Location, "Home", 2, 1L );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, CloudEntryType.TasksList, "List1", 3, 1000L );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, CloudEntryType.Tag, "Tag1", 1, Constants.NO_ID );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, CloudEntryType.Tag, "Tag3", 1, Constants.NO_ID );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, CloudEntryType.Tag, "tag1", 2, Constants.NO_ID );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, CloudEntryType.Tag, "tag2", 1, Constants.NO_ID );
      assertThat( c.moveToNext(), is( false ) );
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
      
      assertThat( c.getCount(), is( 3 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, CloudEntryType.TasksList, "List1", 1, 1000L );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, CloudEntryType.Tag, "tag1", 1, Constants.NO_ID );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, CloudEntryType.Tag, "tag2", 1, Constants.NO_ID );
      assertThat( c.moveToNext(), is( false ) );
   }
   
   
   
   @Override
   protected Iterable< String > getSqlStatements()
   {
      return sqliteScript.getSqlStatements();
   }
   
   
   
   @Override
   protected int getNumberOfQueryAllRows()
   {
      return 6;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContentUris.CLOUD_ENTRIES_CONTENT_URI;
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
            checkResult( c, CloudEntryType.Location, "Home", 2, 1L );
            break;
         
         case 2:
            checkResult( c, CloudEntryType.TasksList, "List1", 4, 1000L );
            break;
         
         case 3:
            checkResult( c, CloudEntryType.Tag, "Tag1", 1, Constants.NO_ID );
            break;
         
         case 4:
            checkResult( c, CloudEntryType.Tag, "Tag3", 1, Constants.NO_ID );
            break;
         
         case 5:
            checkResult( c, CloudEntryType.Tag, "tag1", 3, Constants.NO_ID );
            break;
         
         case 6:
            checkResult( c, CloudEntryType.Tag, "tag2", 2, Constants.NO_ID );
            break;
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    CloudEntryType entryType,
                                    String display,
                                    int count,
                                    long elementId )
   {
      assertThat( c.getInt( ENTRY_TYPE_IDX ), is( entryType.ordinal() ) );
      assertThat( c.getString( DISPLAY_IDX ), is( display ) );
      assertThat( c.getInt( COUNT_IDX ), is( count ) );
      assertThat( c.getLong( ELEMENT_ID_IDX ), is( elementId ) );
   }
}
