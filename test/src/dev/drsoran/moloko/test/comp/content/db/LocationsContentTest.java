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

import static dev.drsoran.moloko.content.Columns.LocationColumns.ADDRESS_IDX;
import static dev.drsoran.moloko.content.Columns.LocationColumns.LATITUDE_IDX;
import static dev.drsoran.moloko.content.Columns.LocationColumns.LOCATION_NAME_IDX;
import static dev.drsoran.moloko.content.Columns.LocationColumns.LONGITUDE_IDX;
import static dev.drsoran.moloko.content.Columns.LocationColumns.VIEWABLE_IDX;
import static dev.drsoran.moloko.content.Columns.LocationColumns.ZOOM_IDX;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.ClassRule;

import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Columns.LocationColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.test.MolokoReadDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;


public class LocationsContentTest extends MolokoReadDbContentTestCase
{
   @ClassRule
   public static SQLiteScript sqliteScript = new SQLiteScript( LocationsContentTest.class,
                                                               "LocationsContentTest.sql" );
   
   
   
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
      return ContentUris.LOCATIONS_CONTENT_URI;
   }
   
   
   
   @Override
   protected Uri getContentUriWithId_1()
   {
      return ContentUris.bindElementId( ContentUris.LOCATIONS_CONTENT_URI_ID,
                                        1L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithNotExistingId()
   {
      return ContentUris.bindElementId( ContentUris.LOCATIONS_CONTENT_URI_ID,
                                        500L );
   }
   
   
   
   @Override
   protected String[] getProjection()
   {
      return LocationColumns.PROJECTION;
   }
   
   
   
   @Override
   protected void checkContent( Cursor c, int rowNumber )
   {
      final long rowId = c.getLong( Columns.ID_IDX );
      
      switch ( (int) rowId )
      {
         case 1:
            checkResult( c, 1L, "Location1", 1.0f, 2.0f, null, 1, 10 );
            break;
         
         case 2:
            checkResult( c,
                         2L,
                         "LocationWithAddr",
                         3.0f,
                         4.0f,
                         "Main Street",
                         1,
                         20 );
            break;
         
         case 3:
            checkResult( c, 3L, "NotViewableLocation", 5.0f, 6.0f, null, 0, 5 );
            break;
         
         default :
            fail( "Unknown row ID " + rowId );
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    long id,
                                    String name,
                                    float lon,
                                    float lat,
                                    String address,
                                    int viewable,
                                    int zoom )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( id ) );
      assertThat( c.getString( LOCATION_NAME_IDX ), is( name ) );
      assertThat( c.getFloat( LONGITUDE_IDX ), is( lon ) );
      assertThat( c.getFloat( LATITUDE_IDX ), is( lat ) );
      assertThat( CursorUtils.getOptString( c, ADDRESS_IDX ), is( address ) );
      assertThat( c.getInt( VIEWABLE_IDX ), is( viewable ) );
      assertThat( c.getInt( ZOOM_IDX ), is( zoom ) );
   }
}
