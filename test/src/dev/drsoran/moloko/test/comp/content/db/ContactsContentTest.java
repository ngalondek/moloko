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

import static dev.drsoran.moloko.content.Columns.ContactColumns.FULLNAME_IDX;
import static dev.drsoran.moloko.content.Columns.ContactColumns.USERNAME_IDX;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;

import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Columns.ContactColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.test.MolokoReadDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;


public class ContactsContentTest extends MolokoReadDbContentTestCase
{
   @Rule
   public SQLiteScript contactsScript = new SQLiteScript( ContactsContentTest.class,
                                                          "ContactsContentTest.sql" );
   
   @Rule
   public SQLiteScript participantsScript = new SQLiteScript( ContactsContentTest.class,
                                                              "TaskParticipantsContentTest.sql" );
   
   
   
   @Test
   public void testNumTasksParticipates()
   {
      prepareDatabase( participantsScript.getSqlStatements() );
      
      c = getContentProvider().query( getContentUri(),
                                      ContactColumns.PROJECTION,
                                      null,
                                      null,
                                      getQueryAllSortOrder() );
      assertNotNull( c );
      
      final int numRows = getNumberOfQueryAllRows();
      assertThat( c.getCount(), is( numRows ) );
      
      for ( int i = 0; i < numRows; i++ )
      {
         assertThat( c.moveToNext(), is( true ) );
         switch ( i + 1 )
         {
            case 1:
               checkResult( c, 1L, "Full Name1", "User1" );
               break;
            
            case 2:
               checkResult( c, 2L, "Full Name2", "User2" );
               break;
            
            default :
               fail( "Unknown row ID " + i );
         }
      }
   }
   
   
   
   @Override
   protected Iterable< String > getSqlStatements()
   {
      return contactsScript.getSqlStatements();
   }
   
   
   
   @Override
   protected int getNumberOfQueryAllRows()
   {
      return 2;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContentUris.CONTACTS_CONTENT_URI;
   }
   
   
   
   @Override
   protected Uri getContentUriWithId_1()
   {
      return ContentUris.bindElementId( ContentUris.CONTACTS_CONTENT_URI_ID, 1L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithNotExistingId()
   {
      return ContentUris.bindElementId( ContentUris.CONTACTS_CONTENT_URI_ID,
                                        500L );
   }
   
   
   
   @Override
   protected String[] getProjection()
   {
      return ContactColumns.PROJECTION;
   }
   
   
   
   @Override
   protected void checkContent( Cursor c, int rowNumber )
   {
      final long rowId = c.getLong( Columns.ID_IDX );
      
      switch ( (int) rowId )
      {
         case 1:
            checkResult( c, 1L, "Full Name1", "User1" );
            break;
         
         case 2:
            checkResult( c, 2L, "Full Name2", "User2" );
            break;
         
         default :
            fail( "Unknown row ID " + rowId );
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    long id,
                                    String fullname,
                                    String username )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( id ) );
      assertThat( c.getString( FULLNAME_IDX ), is( fullname ) );
      assertThat( c.getString( USERNAME_IDX ), is( username ) );
   }
}
