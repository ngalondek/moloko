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

import static dev.drsoran.moloko.content.Columns.SettingsColumns.DATEFORMAT;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.DATEFORMAT_IDX;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.DEFAULTLIST_ID;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.DEFAULTLIST_ID_IDX;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.LANGUAGE;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.LANGUAGE_IDX;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.SYNC_TIMESTAMP;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.SYNC_TIMESTAMP_IDX;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.TIMEFORMAT;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.TIMEFORMAT_IDX;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.TIMEZONE;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.TIMEZONE_IDX;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.TimeZone;

import org.junit.Rule;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.content.db.TableColumns.RtmSettingsColumns;
import dev.drsoran.moloko.test.MolokoReadWriteDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.moloko.test.TestConstants;


public class RtmSettingsContentTest extends MolokoReadWriteDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScript = new SQLiteScript( RtmSettingsContentTest.class,
                                                        "RtmSettingsContentTest.sql" );
   
   
   
   @Override
   protected ContentValues createInsertContent()
   {
      final ContentValues contentValues = new ContentValues();
      contentValues.put( SYNC_TIMESTAMP, TestConstants.NOW );
      contentValues.put( TIMEZONE, TimeZone.getDefault().getID() );
      contentValues.put( DATEFORMAT, 1 );
      contentValues.put( TIMEFORMAT, 0 );
      contentValues.put( LANGUAGE, "de" );
      contentValues.put( DEFAULTLIST_ID, 1L );
      
      return contentValues;
   }
   
   
   
   @Override
   protected void checkInsertContent( Cursor c )
   {
      checkResult( c,
                   1L,
                   TestConstants.NOW,
                   TimeZone.getDefault().getID(),
                   1,
                   0,
                   "de",
                   1L );
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
      return ContentUris.RTM_SETTINGS_CONTENT_URI;
   }
   
   
   
   @Override
   protected Uri getContentUriWithId_1()
   {
      return ContentUris.bindElementId( ContentUris.RTM_SETTINGS_CONTENT_URI_ID,
                                        1L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithNotExistingId()
   {
      return ContentUris.bindElementId( ContentUris.RTM_SETTINGS_CONTENT_URI_ID,
                                        500L );
   }
   
   
   
   @Override
   protected String[] getProjection()
   {
      return RtmSettingsColumns.PROJECTION;
   }
   
   
   
   @Override
   protected void checkContent( Cursor c, int rowNumber )
   {
      final long rowId = c.getLong( Columns.ID_IDX );
      
      if ( rowId == 1 )
      {
         checkResult( c, 1L, 1356998400000L, null, 0, 1, "fr", Constants.NO_ID );
      }
      else
      {
         fail( "Unknown row ID " + rowId );
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    long id,
                                    long syncTs,
                                    String tz,
                                    int df,
                                    int tf,
                                    String lang,
                                    long defListId )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( id ) );
      assertThat( CursorUtils.getOptLong( c,
                                          SYNC_TIMESTAMP_IDX,
                                          Constants.NO_TIME ), is( syncTs ) );
      assertThat( CursorUtils.getOptString( c, TIMEZONE_IDX ), is( tz ) );
      assertThat( c.getInt( DATEFORMAT_IDX ), is( df ) );
      assertThat( c.getInt( TIMEFORMAT_IDX ), is( tf ) );
      assertThat( CursorUtils.getOptString( c, LANGUAGE_IDX ), is( lang ) );
      assertThat( CursorUtils.getOptLong( c,
                                          DEFAULTLIST_ID_IDX,
                                          Constants.NO_ID ), is( defListId ) );
   }
}
