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

import static dev.drsoran.moloko.content.db.TableColumns.ModificationColumns.ENTITY_URI;
import static dev.drsoran.moloko.content.db.TableColumns.ModificationColumns.ENTITY_URI_IDX;
import static dev.drsoran.moloko.content.db.TableColumns.ModificationColumns.NEW_VALUE;
import static dev.drsoran.moloko.content.db.TableColumns.ModificationColumns.NEW_VALUE_IDX;
import static dev.drsoran.moloko.content.db.TableColumns.ModificationColumns.PROPERTY;
import static dev.drsoran.moloko.content.db.TableColumns.ModificationColumns.PROPERTY_IDX;
import static dev.drsoran.moloko.content.db.TableColumns.ModificationColumns.SYNCED_VALUE;
import static dev.drsoran.moloko.content.db.TableColumns.ModificationColumns.SYNCED_VALUE_IDX;
import static dev.drsoran.moloko.content.db.TableColumns.ModificationColumns.TIMESTAMP;
import static dev.drsoran.moloko.content.db.TableColumns.ModificationColumns.TIMESTAMP_IDX;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.ClassRule;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.content.db.TableColumns.ModificationColumns;
import dev.drsoran.moloko.test.MolokoReadWriteDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.moloko.test.TestConstants;


public class ModificationsContentTest extends MolokoReadWriteDbContentTestCase
{
   @ClassRule
   public static SQLiteScript sqliteScript = new SQLiteScript( ModificationsContentTest.class,
                                                               "ModificationsContentTest.sql" );
   
   
   
   @Override
   protected ContentValues createInsertContent()
   {
      final ContentValues contentValues = new ContentValues();
      contentValues.put( ENTITY_URI,
                         ContentUris.bindElementId( ContentUris.LOCATIONS_CONTENT_URI_ID,
                                                    100 )
                                    .toString() );
      contentValues.put( PROPERTY, "name" );
      contentValues.put( NEW_VALUE, "City" );
      contentValues.put( SYNCED_VALUE, "Catty" );
      contentValues.put( TIMESTAMP, TestConstants.NOW );
      
      return contentValues;
   }
   
   
   
   @Override
   protected void checkInsertContent( Cursor c )
   {
      checkResult( c,
                   1L,
                   ContentUris.bindElementId( ContentUris.LOCATIONS_CONTENT_URI_ID,
                                              100 )
                              .toString(),
                   "name",
                   "City",
                   "Catty",
                   TestConstants.NOW );
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
      return 3;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContentUris.MODIFICATIONS_CONTENT_URI;
   }
   
   
   
   @Override
   protected Uri getContentUriWithId_1()
   {
      return ContentUris.bindElementId( ContentUris.MODIFICATIONS_CONTENT_URI_ID,
                                        1L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithNotExistingId()
   {
      return ContentUris.bindElementId( ContentUris.MODIFICATIONS_CONTENT_URI_ID,
                                        500L );
   }
   
   
   
   @Override
   protected String[] getProjection()
   {
      return ModificationColumns.PROJECTION;
   }
   
   
   
   @Override
   protected void checkContent( Cursor c, int rowNumber )
   {
      final long rowId = c.getLong( Columns.ID_IDX );
      
      switch ( (int) rowId )
      {
         case 1:
            checkResult( c,
                         1L,
                         "content://dev.drsoran.moloko.Rtm/lists/1",
                         "list_name",
                         "RenamedList",
                         "List",
                         1356998400000L );
            break;
         
         case 2:
            checkResult( c,
                         2L,
                         "content://dev.drsoran.moloko.Rtm/tasks/10",
                         "postponed",
                         "1",
                         null,
                         1356998400000L );
            break;
         
         case 3:
            checkResult( c,
                         3L,
                         "content://dev.drsoran.moloko.Rtm/tasks/10",
                         "url",
                         null,
                         "http://www.google.de",
                         1356998400000L );
            break;
         
         default :
            fail( "Unknown row ID " + rowId );
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    long id,
                                    String entityUri,
                                    String colName,
                                    String newVal,
                                    String syncedVal,
                                    long timestamp )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( id ) );
      assertThat( c.getString( ENTITY_URI_IDX ), is( entityUri ) );
      assertThat( c.getString( PROPERTY_IDX ), is( colName ) );
      assertThat( CursorUtils.getOptString( c, NEW_VALUE_IDX ), is( newVal ) );
      assertThat( CursorUtils.getOptString( c, SYNCED_VALUE_IDX ),
                  is( syncedVal ) );
      assertThat( c.getLong( TIMESTAMP_IDX ), is( timestamp ) );
   }
}
