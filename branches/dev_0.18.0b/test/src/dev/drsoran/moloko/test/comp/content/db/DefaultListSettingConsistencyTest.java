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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import android.content.ContentValues;

import dev.drsoran.moloko.content.Columns.SettingsColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.db.TableColumns.RtmSettingsColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;


public class DefaultListSettingConsistencyTest extends MolokoDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScript = new SQLiteScript( DefaultListSettingConsistencyTest.class,
                                                        "DefaultListConsistencyTest.sql" );
   
   
   
   @Test
   public void testSetDefaultList_ToId()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      checkTestPreconditions();
      
      final ContentValues contentValues = new ContentValues();
      contentValues.put( SettingsColumns.DEFAULTLIST_ID, 2L );
      
      final int numUpdated = getContentProvider().update( ContentUris.bindElementId( ContentUris.RTM_SETTINGS_CONTENT_URI_ID,
                                                                                     SettingsColumns.SINGLETON_ID ),
                                                          contentValues,
                                                          null,
                                                          null );
      assertThat( numUpdated, is( 1 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_SETTINGS_TABLE,
                                       RtmSettingsColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.moveToNext(), is( true ) );
      assertThat( c.getLong( RtmSettingsColumns.DEFAULTLIST_ID_IDX ), is( 2L ) );
      assertThat( c.getString( RtmSettingsColumns.RTM_DEFAULTLIST_ID_IDX ),
                  is( "1001" ) );
   }
   
   
   
   @Test
   public void testSetDefaultList_ToNoId()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      checkTestPreconditions();
      
      final ContentValues contentValues = new ContentValues();
      contentValues.put( SettingsColumns.DEFAULTLIST_ID, Constants.NO_ID );
      
      final int numUpdated = getContentProvider().update( ContentUris.bindElementId( ContentUris.RTM_SETTINGS_CONTENT_URI_ID,
                                                                                     SettingsColumns.SINGLETON_ID ),
                                                          contentValues,
                                                          null,
                                                          null );
      assertThat( numUpdated, is( 1 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_SETTINGS_TABLE,
                                       RtmSettingsColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.moveToNext(), is( true ) );
      assertThat( c.getLong( RtmSettingsColumns.DEFAULTLIST_ID_IDX ), is( -1L ) );
      assertThat( c.getString( RtmSettingsColumns.RTM_DEFAULTLIST_ID_IDX ),
                  is( nullValue() ) );
   }
   
   
   
   @Test
   public void testDeleteDefaultList()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      checkTestPreconditions();
      
      final int numDeleted = getContentProvider().delete( ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                                                     1L ),
                                                          null,
                                                          null );
      
      assertThat( numDeleted, is( 1 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_SETTINGS_TABLE,
                                       RtmSettingsColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.moveToNext(), is( true ) );
      assertThat( c.getLong( RtmSettingsColumns.DEFAULTLIST_ID_IDX ), is( -1L ) );
      assertThat( c.getString( RtmSettingsColumns.RTM_DEFAULTLIST_ID_IDX ),
                  is( nullValue() ) );
   }
   
   
   
   private void checkTestPreconditions()
   {
      c = getDb().getReadable().query( TableNames.RTM_SETTINGS_TABLE,
                                       RtmSettingsColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.moveToNext(), is( true ) );
      assertThat( c.getLong( RtmSettingsColumns.DEFAULTLIST_ID_IDX ), is( 1L ) );
      assertThat( c.getString( RtmSettingsColumns.RTM_DEFAULTLIST_ID_IDX ),
                  is( "1000" ) );
      c.close();
   }
}
