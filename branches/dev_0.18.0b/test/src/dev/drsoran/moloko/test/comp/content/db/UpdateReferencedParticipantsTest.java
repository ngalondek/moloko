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
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import android.content.ContentValues;

import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmParticipantColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;


public class UpdateReferencedParticipantsTest extends MolokoDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScriptTask = new SQLiteScript( UpdateReferencedParticipantsTest.class,
                                                            "UpdateReferencedParticipantsTest.sql" );
   
   
   
   @Test
   public void testUpdateUserAndFullName()
   {
      prepareDatabase( sqliteScriptTask.getSqlStatements() );
      checkTestPreconditions();
      
      final ContentValues contentValues = new ContentValues();
      contentValues.put( RtmContactColumns.FULLNAME, "New Full Name" );
      contentValues.put( RtmContactColumns.USERNAME, "NewUserName" );
      
      final int numUpdated = getDb().getWritable()
                                    .update( TableNames.RTM_CONTACTS_TABLE,
                                             contentValues,
                                             RtmContactColumns._ID + "=1",
                                             null );
      assertThat( numUpdated, is( 1 ) );
      
      c = getDb().getReadable().query( TableNames.RTM_PARTICIPANTS_TABLE,
                                       RtmParticipantColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 2 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      if ( c.getLong( Columns.ID_IDX ) == 1L )
      {
         assertThat( c.getString( RtmParticipantColumns.USERNAME_IDX ),
                     is( "NewUserName" ) );
         assertThat( c.getString( RtmParticipantColumns.FULLNAME_IDX ),
                     is( "New Full Name" ) );
      }
      if ( c.getLong( Columns.ID_IDX ) == 2L )
      {
         assertThat( c.getString( RtmParticipantColumns.USERNAME_IDX ),
                     is( "User2" ) );
         assertThat( c.getString( RtmParticipantColumns.FULLNAME_IDX ),
                     is( "Full Name2" ) );
      }
   }
   
   
   
   private void checkTestPreconditions()
   {
      c = getDb().getReadable().query( TableNames.RTM_PARTICIPANTS_TABLE,
                                       RtmParticipantColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 2 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      if ( c.getLong( Columns.ID_IDX ) == 1L )
      {
         assertThat( c.getString( RtmParticipantColumns.USERNAME_IDX ),
                     is( "User1" ) );
         assertThat( c.getString( RtmParticipantColumns.FULLNAME_IDX ),
                     is( "Full Name1" ) );
      }
      if ( c.getLong( Columns.ID_IDX ) == 2L )
      {
         assertThat( c.getString( RtmParticipantColumns.USERNAME_IDX ),
                     is( "User2" ) );
         assertThat( c.getString( RtmParticipantColumns.FULLNAME_IDX ),
                     is( "Full Name2" ) );
      }
      
      c.close();
   }
}
