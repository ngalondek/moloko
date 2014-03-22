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

import static dev.drsoran.moloko.content.Columns.TasksListColumns.ARCHIVED_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.FILTER_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.IS_SMART_LIST_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_CREATED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_DELETED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_MODIFIED_DATE_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_NAME_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LOCKED_IDX;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.POSITION_IDX;
import static dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns.RTM_LIST_ID_IDX;
import static dev.drsoran.moloko.test.SQLiteScriptVariables.VAR_TODAY;
import static dev.drsoran.moloko.test.SQLiteScriptVariables.VAR_YESTERDAY;
import static dev.drsoran.rtm.test.TestConstants.TODAY;
import static dev.drsoran.rtm.test.TestConstants.YESTERDAY;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import android.database.Cursor;
import android.text.format.DateUtils;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.CursorUtils;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoSyncTestCase;
import dev.drsoran.moloko.test.ProcessedSQLiteScript;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.rtm.model.RtmTasksList;


public class RtmTasksListsSyncTest extends MolokoSyncTestCase
{
   @Rule
   public ProcessedSQLiteScript sqliteScript = new ProcessedSQLiteScript( new SQLiteScript( RtmTasksListsSyncTest.class,
                                                                                            "RtmTasksListsSyncTest.sql" ) ).replaceScriptVariable( VAR_TODAY,
                                                                                                                                                   TODAY )
                                                                                                                           .replaceScriptVariable( VAR_YESTERDAY,
                                                                                                                                                   YESTERDAY );
   
   
   
   @Test
   public void testGet_NoTime()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      
      final List< RtmTasksList > lists = getSyncPartner().getTasksLists( Constants.NO_TIME );
      assertThat( lists.size(), is( 2 ) );
   }
   
   
   
   @Test
   public void testGet_SinceYesterday()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      
      final List< RtmTasksList > lists = getSyncPartner().getTasksLists( YESTERDAY
         + DateUtils.MINUTE_IN_MILLIS );
      assertThat( lists.size(), is( 1 ) );
      assertThat( lists.get( 0 ).getId(), is( "1000" ) );
   }
   
   
   
   @Test
   public void testInsert()
   {
      // getSyncPartner().insertContact( new RtmTasksList( "1", "User", "Full" ) );
      //
      // c = getDb().getReadable().query( TableNames.RTM_CONTACTS_TABLE,
      // RtmContactColumns.TABLE_PROJECTION,
      // null,
      // null,
      // null,
      // null,
      // null );
      //
      // assertThat( c.getCount(), is( 1 ) );
      // assertThat( c.moveToNext(), is( true ) );
      //
      // checkResult( c, "User", "Full", "1" );
   }
   
   
   
   @Test
   public void testUpdate()
   {
      // prepareDatabase( sqliteScript.getSqlStatements() );
      //
      // getSyncPartner().updateContact( new RtmTasksList( "1000", "User", "Full" ),
      // new RtmTasksList( "1000",
      // "UserNew",
      // "FullNew" ) );
      //
      // c = getDb().getReadable().query( TableNames.RTM_CONTACTS_TABLE,
      // RtmContactColumns.TABLE_PROJECTION,
      // null,
      // null,
      // null,
      // null,
      // null );
      //
      // assertThat( c.getCount(), is( 2 ) );
      // assertThat( c.moveToNext(), is( true ) );
      //
      // if ( c.getLong( Columns.ID_IDX ) == 1L )
      // {
      // checkResult( c, "UserNew", "FullNew", "1000" );
      // }
      // else if ( c.getLong( Columns.ID_IDX ) == 2L )
      // {
      // checkResult( c, "User2", "Full2", "1001" );
      // }
      // else
      // {
      // fail();
      // }
   }
   
   
   
   @Test
   public void testDelete()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      
      getSyncPartner().deleteTasksList( new RtmTasksList( "1000",
                                                          0,
                                                          false,
                                                          false,
                                                          false,
                                                          "NonSmartList",
                                                          null ) );
      
      c = getDb().getReadable().query( TableNames.RTM_TASKS_LIST_TABLE,
                                       RtmTasksListColumns.TABLE_PROJECTION,
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
                      0,
                      YESTERDAY,
                      YESTERDAY,
                      Constants.NO_TIME,
                      0,
                      1,
                      "SmartList",
                      "(name:test)",
                      "1001" );
      }
      else
      {
         fail();
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    int pos,
                                    long crea,
                                    long mod,
                                    long del,
                                    int lock,
                                    int arch,
                                    String name,
                                    String filter,
                                    String rtmId )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( not( Constants.NO_ID ) ) );
      assertThat( c.getInt( POSITION_IDX ), is( pos ) );
      assertThat( c.getLong( LIST_CREATED_DATE_IDX ), is( crea ) );
      assertThat( c.getLong( LIST_MODIFIED_DATE_IDX ), is( mod ) );
      assertThat( CursorUtils.getOptLong( c,
                                          LIST_DELETED_DATE_IDX,
                                          Constants.NO_TIME ), is( del ) );
      assertThat( c.getInt( LOCKED_IDX ), is( lock ) );
      assertThat( c.getInt( ARCHIVED_IDX ), is( arch ) );
      assertThat( c.getString( LIST_NAME_IDX ), is( name ) );
      assertThat( c.getString( FILTER_IDX ), is( filter ) );
      assertThat( c.getInt( IS_SMART_LIST_IDX ), is( filter != null ? 1 : 0 ) );
      assertThat( c.getString( RTM_LIST_ID_IDX ), is( rtmId ) );
   }
}
