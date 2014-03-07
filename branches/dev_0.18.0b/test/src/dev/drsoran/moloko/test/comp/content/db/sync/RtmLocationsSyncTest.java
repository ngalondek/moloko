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

package dev.drsoran.moloko.test.comp.content.db.sync;

import static dev.drsoran.moloko.content.Columns.LocationColumns.ADDRESS_IDX;
import static dev.drsoran.moloko.content.Columns.LocationColumns.LATITUDE_IDX;
import static dev.drsoran.moloko.content.Columns.LocationColumns.LOCATION_NAME_IDX;
import static dev.drsoran.moloko.content.Columns.LocationColumns.LONGITUDE_IDX;
import static dev.drsoran.moloko.content.Columns.LocationColumns.VIEWABLE_IDX;
import static dev.drsoran.moloko.content.Columns.LocationColumns.ZOOM_IDX;
import static dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns.RTM_LOCATION_ID_IDX;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import android.database.Cursor;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoSyncTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.rtm.model.RtmLocation;


public class RtmLocationsSyncTest extends MolokoSyncTestCase
{
   @Rule
   public SQLiteScript sqliteScript = new SQLiteScript( RtmLocationsSyncTest.class,
                                                        "LocationsContentTest.sql" );
   
   
   
   @Test
   public void testGet()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      
      final List< RtmLocation > locations = getSyncPartner().getLocations();
      assertThat( locations.size(), is( 2 ) );
   }
   
   
   
   @Test
   public void testInsert()
   {
      getSyncPartner().insertLocation( new RtmLocation( "1",
                                                        "Loc",
                                                        1.0f,
                                                        2.0f,
                                                        "Addr",
                                                        true,
                                                        10 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_LOCATIONS_TABLE,
                                       RtmLocationColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, "Loc", 1.0f, 2.0f, "Addr", 1, 10, "1" );
   }
   
   
   
   @Test
   public void testUpdate()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      
      getSyncPartner().updateLocation( new RtmLocation( "1000",
                                                        "Location1",
                                                        1.0f,
                                                        2.0f,
                                                        null,
                                                        true,
                                                        10 ),
                                       new RtmLocation( "1000",
                                                        "Loc",
                                                        3.0f,
                                                        4.0f,
                                                        "Addr",
                                                        false,
                                                        11 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_LOCATIONS_TABLE,
                                       RtmLocationColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 2 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      if ( c.getLong( Columns.ID_IDX ) == 1L )
      {
         checkResult( c, "Loc", 3.0f, 4.0f, "Addr", 0, 11, "1000" );
      }
      else if ( c.getLong( Columns.ID_IDX ) == 2L )
      {
         checkResult( c,
                      "LocationWithAddr",
                      3.0f,
                      4.0f,
                      "Main Street",
                      1,
                      20,
                      "1001" );
      }
      else
      {
         fail();
      }
   }
   
   
   
   @Test
   public void testDelete()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      
      getSyncPartner().deleteLocation( new RtmLocation( "1000",
                                                        "Location1",
                                                        1.0f,
                                                        2.0f,
                                                        null,
                                                        true,
                                                        10 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_LOCATIONS_TABLE,
                                       RtmLocationColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      if ( c.getLong( Columns.ID_IDX ) == 2L )
      {
         checkResult( c,
                      "LocationWithAddr",
                      3.0f,
                      4.0f,
                      "Main Street",
                      1,
                      20,
                      "1001" );
      }
      else
      {
         fail();
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    String locName,
                                    float lon,
                                    float lat,
                                    String addr,
                                    int viewable,
                                    int zoom,
                                    String rtmId )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( not( Constants.NO_ID ) ) );
      assertThat( c.getString( LOCATION_NAME_IDX ), is( locName ) );
      assertThat( c.getFloat( LONGITUDE_IDX ), is( lon ) );
      assertThat( c.getFloat( LATITUDE_IDX ), is( lat ) );
      assertThat( c.getString( ADDRESS_IDX ), is( addr ) );
      assertThat( c.getInt( VIEWABLE_IDX ), is( viewable ) );
      assertThat( c.getInt( ZOOM_IDX ), is( zoom ) );
      assertThat( c.getString( RTM_LOCATION_ID_IDX ), is( rtmId ) );
   }
}
