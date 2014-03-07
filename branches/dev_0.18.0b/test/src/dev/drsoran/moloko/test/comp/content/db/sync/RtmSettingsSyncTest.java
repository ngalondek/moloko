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

import static dev.drsoran.moloko.content.Columns.SettingsColumns.DATEFORMAT_IDX;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.DEFAULTLIST_ID_IDX;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.LANGUAGE_IDX;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.SYNC_TIMESTAMP_IDX;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.TIMEFORMAT_IDX;
import static dev.drsoran.moloko.content.Columns.SettingsColumns.TIMEZONE_IDX;
import static dev.drsoran.moloko.content.db.TableColumns.RtmSettingsColumns.RTM_DEFAULTLIST_ID_IDX;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.content.db.TableColumns.RtmSettingsColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoSyncTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.moloko.test.TestConstants;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmSettings;


public class RtmSettingsSyncTest extends MolokoSyncTestCase
{
   @Rule
   public SQLiteScript sqliteScript = new SQLiteScript( RtmSettingsSyncTest.class,
                                                        "RtmSettingsSyncTest.sql" );
   
   
   
   @Test
   public void testGet()
   {
      final RtmSettings settings = getSyncPartner().getSettings();
      assertThat( settings, is( notNullValue() ) );
   }
   
   
   
   @Test
   public void testUpdate()
   {
      getSyncPartner().updateSettings( new RtmSettings( TestConstants.LATER,
                                                        "UTC",
                                                        1,
                                                        2,
                                                        RtmConstants.NO_ID,
                                                        "en-US" ),
                                       new RtmSettings( TestConstants.NEVER,
                                                        "UTC+1",
                                                        3,
                                                        4,
                                                        RtmConstants.NO_ID,
                                                        "de-De" ) );
      
      c = getDb().getReadable().query( TableNames.RTM_SETTINGS_TABLE,
                                       RtmSettingsColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c,
                   RtmSettingsColumns.SINGLETON_ID,
                   getSyncTime(),
                   "UTC+1",
                   3,
                   4,
                   "de-De",
                   Constants.NO_ID,
                   RtmConstants.NO_ID );
   }
   
   
   
   @Test
   public void testUpdate_ResetDefaultListId()
   {
      getSyncPartner().updateSettings( new RtmSettings( TestConstants.LATER,
                                                        "UTC",
                                                        1,
                                                        2,
                                                        "1000",
                                                        "en-US" ),
                                       new RtmSettings( TestConstants.NEVER,
                                                        "UTC",
                                                        1,
                                                        2,
                                                        RtmConstants.NO_ID,
                                                        "en-US" ) );
      
      c = getDb().getReadable().query( TableNames.RTM_SETTINGS_TABLE,
                                       RtmSettingsColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c,
                   RtmSettingsColumns.SINGLETON_ID,
                   getSyncTime(),
                   "UTC",
                   1,
                   2,
                   "en-US",
                   Constants.NO_ID,
                   RtmConstants.NO_ID );
   }
   
   
   
   @Test
   public void testUpdate_SetNewDefaultListId()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      
      getSyncPartner().updateSettings( new RtmSettings( TestConstants.LATER,
                                                        "UTC",
                                                        1,
                                                        2,
                                                        "1000",
                                                        "en-US" ),
                                       new RtmSettings( TestConstants.NEVER,
                                                        "UTC",
                                                        1,
                                                        2,
                                                        "1001",
                                                        "en-US" ) );
      
      c = getDb().getReadable().query( TableNames.RTM_SETTINGS_TABLE,
                                       RtmSettingsColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c,
                   RtmSettingsColumns.SINGLETON_ID,
                   getSyncTime(),
                   "UTC",
                   1,
                   2,
                   "en-US",
                   1001,
                   "1001" );
   }
   
   
   
   @Test( expected = SQLiteException.class )
   public void testUpdate_SetNewDefaultListId_NotFound()
   {
      getSyncPartner().updateSettings( new RtmSettings( TestConstants.LATER,
                                                        "UTC",
                                                        1,
                                                        2,
                                                        "1000",
                                                        "en-US" ),
                                       new RtmSettings( TestConstants.NEVER,
                                                        "UTC",
                                                        1,
                                                        2,
                                                        "1001",
                                                        "en-US" ) );
   }
   
   
   
   private static void checkResult( Cursor c,
                                    long id,
                                    long syncTs,
                                    String tz,
                                    int df,
                                    int tf,
                                    String lang,
                                    long defListId,
                                    String rtmDefListId )
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
      assertThat( CursorUtils.getOptString( c,
                                            RTM_DEFAULTLIST_ID_IDX,
                                            RtmConstants.NO_ID ),
                  is( rtmDefListId ) );
   }
}
