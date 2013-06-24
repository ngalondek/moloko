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

import static dev.drsoran.moloko.content.Columns.ParticipantColumns.CONTACT_ID_IDX;
import static dev.drsoran.moloko.content.Columns.ParticipantColumns.FULLNAME_IDX;
import static dev.drsoran.moloko.content.Columns.ParticipantColumns.PROJECTION;
import static dev.drsoran.moloko.content.Columns.ParticipantColumns.USERNAME_IDX;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Rule;

import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.test.MolokoReadDbContentTestCase;
import dev.drsoran.moloko.test.SQLiteScript;


public class TaskParticipantsContentTest extends MolokoReadDbContentTestCase
{
   @Rule
   public SQLiteScript sqliteScript = new SQLiteScript( TaskParticipantsContentTest.class,
                                                        "TaskParticipantsContentTest.sql" );
   
   
   
   @Override
   protected Iterable< String > getSqlStatements()
   {
      return sqliteScript.getSqlStatements();
   }
   
   
   
   @Override
   protected int getNumberOfQueryAllRows()
   {
      return 2;
   }
   
   
   
   @Override
   protected Uri getContentUri()
   {
      return ContentUris.bindAggregationIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                                 100L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithId_1()
   {
      return ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                       100L,
                                                       1L );
   }
   
   
   
   @Override
   protected Uri getContentUriWithNotExistingId()
   {
      return ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                       100L,
                                                       500L );
   }
   
   
   
   @Override
   protected String[] getProjection()
   {
      return PROJECTION;
   }
   
   
   
   @Override
   protected void checkContent( Cursor c, int rowNumber )
   {
      final long rowId = c.getLong( Columns.ID_IDX );
      
      switch ( (int) rowId )
      {
         case 1:
            checkResult( c, 1L, 1L, "Full Name1", "User1" );
            break;
         
         case 2:
            checkResult( c, 2L, 2L, "Full Name2", "User2" );
            break;
         
         default :
            fail( "Unknown row ID " + rowId );
      }
   }
   
   
   
   private static void checkResult( Cursor c,
                                    long id,
                                    long contactId,
                                    String fullName,
                                    String userName )
   {
      assertThat( c.getLong( Columns.ID_IDX ), is( id ) );
      assertThat( c.getLong( CONTACT_ID_IDX ), is( contactId ) );
      assertThat( c.getString( FULLNAME_IDX ), is( fullName ) );
      assertThat( c.getString( USERNAME_IDX ), is( userName ) );
   }
}
