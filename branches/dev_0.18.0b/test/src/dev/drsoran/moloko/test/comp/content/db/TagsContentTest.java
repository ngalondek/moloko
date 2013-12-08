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

import static dev.drsoran.moloko.content.Columns.TagColumns.PROJECTION;
import static dev.drsoran.moloko.content.Columns.TagColumns.TAG_IDX;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import org.junit.ClassRule;
import org.junit.Test;

import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns.TagColumns;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.test.MolokoReadDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;


public class TagsContentTest extends MolokoReadDbContentTestCase
{
   @ClassRule
   public static SQLiteScript sqliteScript = new SQLiteScript( TasksContentTest.class,
                                                               "TagsContentTest.sql" );
   
   
   
   @Override
   public void testInsertWithId()
   {
      assumeTrue( false );
      fail( "We have no content URI with ID for tags" );
   }
   
   
   
   @Override
   @Test
   public void testQueryWithId()
   {
      assumeTrue( false );
      fail( "We have no content URI with ID for tags" );
   }
   
   
   
   @Override
   @Test
   public void testQueryWithNonExistingId()
   {
      assumeTrue( false );
      fail( "We have no content URI with ID for tags" );
   }
   
   
   
   @Override
   @Test
   public void testDeleteWithId()
   {
      assumeTrue( false );
      fail( "We have no content URI with ID for tags" );
   }
   
   
   
   @Override
   @Test
   public void testUpdateWithId()
   {
      assumeTrue( false );
      fail( "We have no content URI with ID for tasks count" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testQueryAllWithSortOrder()
   {
      getContentProvider().query( getContentUri(),
                                  getProjection(),
                                  null,
                                  null,
                                  TagColumns.TAG );
   }
   
   
   
   @Override
   @Test
   public void testQueryWithSelection()
   {
      prepareDatabase( getSqlStatements() );
      
      c = getContentProvider().query( getContentUri(),
                                      getProjection(),
                                      TaskColumns.POSTPONED + " < 1",
                                      null,
                                      null );
      assertNotNull( c );
      
      assertThat( c.getCount(), is( 2 ) );
      
      assertThat( c.moveToNext(), is( true ) );
      checkContent( c, 1 );
      
      assertThat( c.moveToNext(), is( true ) );
      checkContent( c, 2 );
   }
   
   
   
   @Override
   @Test
   public void testQueryWithSelectionArgs()
   {
      prepareDatabase( getSqlStatements() );
      
      c = getContentProvider().query( getContentUri(),
                                      getProjection(),
                                      TaskColumns.POSTPONED + " < ?",
                                      new String[]
                                      { "1" },
                                      null );
      assertNotNull( c );
      
      assertThat( c.getCount(), is( 2 ) );
      
      assertThat( c.moveToNext(), is( true ) );
      checkContent( c, 1 );
      
      assertThat( c.moveToNext(), is( true ) );
      checkContent( c, 2 );
   }
   
   
   
   @Override
   protected Iterable< String > getSqlStatements()
   {
      return sqliteScript.getSqlStatements();
   }
   
   
   
   @Override
   protected int getNumberOfQueryAllRows()
   {
      return 4;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContentUris.TAGS_CONTENT_URI;
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
            checkResult( c, "Tag1" );
            break;
         
         case 2:
            checkResult( c, "Tag3" );
            break;
         
         case 3:
            checkResult( c, "tag1" );
            break;
         
         case 4:
            checkResult( c, "tag2" );
            break;
         
         default :
            fail( "Unexpected row number " + rowNumber );
      }
      
   }
   
   
   
   private static void checkResult( Cursor c, String tag )
   {
      assertThat( c.getString( TAG_IDX ), is( tag ) );
   }
}
