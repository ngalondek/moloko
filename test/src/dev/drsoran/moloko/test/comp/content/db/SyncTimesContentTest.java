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

import static dev.drsoran.moloko.content.Columns.SyncTimesColumns.LAST_IN;
import static dev.drsoran.moloko.content.Columns.SyncTimesColumns.LAST_IN_IDX;
import static dev.drsoran.moloko.content.Columns.SyncTimesColumns.LAST_OUT;
import static dev.drsoran.moloko.content.Columns.SyncTimesColumns.LAST_OUT_IDX;
import static dev.drsoran.moloko.content.Columns.SyncTimesColumns.PROJECTION;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Collections;

import org.junit.Test;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Columns.SyncTimesColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.test.MolokoReadWriteDbContentTestCase;
import dev.drsoran.rtm.test.TestConstants;


public class SyncTimesContentTest extends MolokoReadWriteDbContentTestCase
{
   @Test
   public void testDefaultRowExists()
   {
      final Cursor defaultEntryCursor = getContentProvider().query( getContentUriWithId_1(),
                                                                    getProjection(),
                                                                    null,
                                                                    null,
                                                                    null );
      try
      {
         assertThat( defaultEntryCursor, is( notNullValue() ) );
         assertThat( defaultEntryCursor.getCount(), is( 1 ) );
         assertThat( defaultEntryCursor.moveToNext(), is( true ) );
         
         checkResult( defaultEntryCursor,
                      SyncTimesColumns.SINGLETON_ID,
                      Constants.NO_TIME,
                      Constants.NO_TIME );
      }
      finally
      {
         defaultEntryCursor.close();
      }
   }
   
   
   
   @Override
   @Test( expected = UnsupportedOperationException.class )
   public void testInsert()
   {
      super.testInsert();
   }
   
   
   
   @Override
   @Test( expected = UnsupportedOperationException.class )
   public void testDeleteAll()
   {
      super.testDeleteAll();
   }
   
   
   
   @Override
   @Test( expected = UnsupportedOperationException.class )
   public void testDeleteWithId()
   {
      super.testDeleteWithId();
   }
   
   
   
   @Override
   @Test( expected = UnsupportedOperationException.class )
   public void testDeleteWithSelection()
   {
      super.testDeleteWithSelection();
   }
   
   
   
   @Override
   @Test( expected = UnsupportedOperationException.class )
   public void testDeleteWithSelectionArgs()
   {
      super.testDeleteWithSelectionArgs();
   }
   
   
   
   @Override
   @Test( expected = UnsupportedOperationException.class )
   public void testDeleteWithNonExistingId()
   {
      super.testDeleteWithNonExistingId();
   }
   
   
   
   @Override
   protected ContentValues createInsertContent()
   {
      return new ContentValues();
   }
   
   
   
   @Override
   protected void checkInsertContent( Cursor c )
   {
      fail( "Expected not to be called since insert throws" );
   }
   
   
   
   @Override
   protected ContentValues createUpdateContentForId_1()
   {
      final ContentValues contentValues = new ContentValues();
      contentValues.put( LAST_IN, TestConstants.NOW );
      contentValues.put( LAST_OUT, TestConstants.LATER );
      
      return contentValues;
   }
   
   
   
   @Override
   protected void checkUpdatedContent( Cursor c )
   {
      checkResult( c,
                   SyncTimesColumns.SINGLETON_ID,
                   TestConstants.NOW,
                   TestConstants.LATER );
   }
   
   
   
   @Override
   protected Iterable< String > getSqlStatements()
   {
      return Collections.emptyList();
   }
   
   
   
   @Override
   protected int getNumberOfQueryAllRows()
   {
      return 1;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContentUris.SYNC_CONTENT_URI;
   }
   
   
   
   @Override
   protected Uri getContentUriWithId_1()
   {
      return ContentUris.bindElementId( ContentUris.SYNC_CONTENT_URI_ID, 1L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithNotExistingId()
   {
      return ContentUris.bindElementId( ContentUris.SYNC_CONTENT_URI_ID, 500L );
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
      
      if ( rowId == 1 )
      {
         checkResult( c, 1L, Constants.NO_TIME, Constants.NO_TIME );
      }
      else
      {
         fail( "Unknown row ID " + rowId );
      }
   }
   
   
   
   private static void checkResult( Cursor c, long id, long lastIn, long lastOut )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( id ) );
      assertThat( CursorUtils.getOptLong( c, LAST_IN_IDX, Constants.NO_TIME ),
                  is( lastIn ) );
      assertThat( CursorUtils.getOptLong( c, LAST_OUT_IDX, Constants.NO_TIME ),
                  is( lastOut ) );
   }
}
