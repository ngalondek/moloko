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

import static dev.drsoran.moloko.content.Columns.SyncColumns.LAST_IN;
import static dev.drsoran.moloko.content.Columns.SyncColumns.LAST_IN_IDX;
import static dev.drsoran.moloko.content.Columns.SyncColumns.LAST_OUT;
import static dev.drsoran.moloko.content.Columns.SyncColumns.LAST_OUT_IDX;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Rule;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Columns.SyncColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.test.MolokoReadWriteDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.moloko.test.TestConstants;


public class SyncContentTest extends MolokoReadWriteDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScript = new SQLiteScript( SyncContentTest.class,
                                                        "SyncContentTest.sql" );
   
   
   
   @Override
   protected ContentValues createInsertContent()
   {
      final ContentValues contentValues = new ContentValues();
      contentValues.put( LAST_IN, TestConstants.NOW );
      contentValues.put( LAST_OUT, TestConstants.LATER );
      
      return contentValues;
   }
   
   
   
   @Override
   protected void checkInsertContent( Cursor c )
   {
      checkResult( c, 1L, TestConstants.NOW, TestConstants.LATER );
   }
   
   
   
   @Override
   protected ContentValues createUpdateContentForId_1()
   {
      return createInsertContent();
   }
   
   
   
   @Override
   protected void checkUpdatedContent( Cursor c )
   {
      checkInsertContent( c );
   }
   
   
   
   @Override
   protected Iterable< String > getSqlStatements()
   {
      return sqliteScript.getSqlStatements();
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
      return TimesColumns.PROJECTION;
   }
   
   
   
   @Override
   protected void checkContent( Cursor c, int rowNumber )
   {
      final long rowId = c.getLong( Columns.ID_IDX );
      
      if ( rowId == 1 )
      {
         checkResult( c, 1L, 1356998400000L, Constants.NO_TIME );
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
