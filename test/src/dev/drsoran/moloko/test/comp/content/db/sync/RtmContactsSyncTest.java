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

import static dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns.FULLNAME_IDX;
import static dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns.RTM_CONTACT_ID_IDX;
import static dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns.USERNAME_IDX;
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
import dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.moloko.test.MolokoSyncTestCase;
import dev.drsoran.moloko.test.SQLiteScript;
import dev.drsoran.rtm.model.RtmContact;


public class RtmContactsSyncTest extends MolokoSyncTestCase
{
   @Rule
   public SQLiteScript sqliteScript = new SQLiteScript( RtmContactsSyncTest.class,
                                                        "RtmContactsSyncTest.sql" );
   
   
   
   @Test
   public void testGet()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      
      final List< RtmContact > contacts = getSyncPartner().getContacts();
      assertThat( contacts.size(), is( 2 ) );
   }
   
   
   
   @Test
   public void testInsert()
   {
      getSyncPartner().insertContact( new RtmContact( "1", "User", "Full" ) );
      
      c = getDb().getReadable().query( TableNames.RTM_CONTACTS_TABLE,
                                       RtmContactColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      checkResult( c, "User", "Full", "1" );
   }
   
   
   
   @Test
   public void testUpdate()
   {
      prepareDatabase( sqliteScript.getSqlStatements() );
      
      getSyncPartner().updateContact( new RtmContact( "1000", "User", "Full" ),
                                      new RtmContact( "1000",
                                                      "UserNew",
                                                      "FullNew" ) );
      
      c = getDb().getReadable().query( TableNames.RTM_CONTACTS_TABLE,
                                       RtmContactColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 2 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      if ( c.getLong( Columns.ID_IDX ) == 1L )
      {
         checkResult( c, "UserNew", "FullNew", "1000" );
      }
      else if ( c.getLong( Columns.ID_IDX ) == 2L )
      {
         checkResult( c, "User2", "Full2", "1001" );
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
      
      getSyncPartner().deleteContact( new RtmContact( "1000", "User", "Full" ) );
      
      c = getDb().getReadable().query( TableNames.RTM_CONTACTS_TABLE,
                                       RtmContactColumns.TABLE_PROJECTION,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      
      if ( c.getLong( Columns.ID_IDX ) == 2L )
      {
         checkResult( c, "User2", "Full2", "1001" );
      }
      else
      {
         fail();
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    String user,
                                    String full,
                                    String rtmId )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( not( Constants.NO_ID ) ) );
      assertThat( c.getString( USERNAME_IDX ), is( user ) );
      assertThat( c.getString( FULLNAME_IDX ), is( full ) );
      assertThat( c.getString( RTM_CONTACT_ID_IDX ), is( rtmId ) );
   }
}
