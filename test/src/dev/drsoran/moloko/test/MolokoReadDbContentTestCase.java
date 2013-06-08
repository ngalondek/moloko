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

package dev.drsoran.moloko.test;

import static android.provider.BaseColumns._ID;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;


public abstract class MolokoReadDbContentTestCase extends
         MolokoDbContentTestCase
{
   @Test( expected = UnsupportedOperationException.class )
   public void testInsert()
   {
      getContentProvider().insert( getContentUri(), new ContentValues() );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testInsertWithId()
   {
      final Uri contentUriWithId1 = getContentUriWithId_1();
      getContentProvider().insert( contentUriWithId1, new ContentValues() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testInsertNullUri()
   {
      getContentProvider().insert( null, new ContentValues() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testInsertNullContentValues()
   {
      getContentProvider().insert( getContentUri(), null );
   }
   
   
   
   @Test
   public void testQueryAll()
   {
      prepareDatabase( getSqlStatements() );
      
      c = getContentProvider().query( getContentUri(),
                                      getProjection(),
                                      null,
                                      null,
                                      null );
      assertNotNull( c );
      
      final int numRows = getNumberOfQueryAllRows();
      assertThat( c.getCount(), is( numRows ) );
      
      for ( int i = 0; i < numRows; i++ )
      {
         assertThat( c.moveToNext(), is( true ) );
         checkContent( c, c.getLong( Columns.ID_IDX ) );
      }
   }
   
   
   
   @Test
   public void testQueryWithId()
   {
      final Uri contentUriWithId1 = getContentUriWithId_1();
      prepareDatabase( getSqlStatements() );
      
      c = getContentProvider().query( contentUriWithId1,
                                      getProjection(),
                                      null,
                                      null,
                                      null );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      checkContent( c, c.getLong( Columns.ID_IDX ) );
   }
   
   
   
   @Test
   public void testQueryWithNonExistingId()
   {
      final Uri contentUri = getContentUriWithNotExistingId();
      prepareDatabase( getSqlStatements() );
      
      c = getContentProvider().query( contentUri,
                                      getProjection(),
                                      null,
                                      null,
                                      null );
      assertNotNull( c );
      assertThat( c.getCount(), is( 0 ) );
   }
   
   
   
   @Test
   public void testQueryWithSelection()
   {
      prepareDatabase( getSqlStatements() );
      
      c = getContentProvider().query( getContentUri(),
                                      getProjection(),
                                      _ID + "=1",
                                      null,
                                      null );
      assertNotNull( c );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      checkContent( c, c.getLong( Columns.ID_IDX ) );
   }
   
   
   
   @Test
   public void testQueryWithSelectionArgs()
   {
      prepareDatabase( getSqlStatements() );
      
      c = getContentProvider().query( getContentUri(),
                                      getProjection(),
                                      _ID + "=?",
                                      new String[]
                                      { "1" },
                                      null );
      assertNotNull( c );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      checkContent( c, c.getLong( Columns.ID_IDX ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testQueryNullUri()
   {
      getContentProvider().query( null, getProjection(), null, null, null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testQueryNullProjection()
   {
      getContentProvider().query( getContentUri(), null, null, null, null );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testDeleteAll()
   {
      getContentProvider().delete( getContentUri(), null, null );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testDeleteWithId()
   {
      final Uri contentUriWithId1 = getContentUriWithId_1();
      getContentProvider().delete( contentUriWithId1, null, null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testDeleteNullUri()
   {
      getContentProvider().delete( null, null, null );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testUpdateAll()
   {
      getContentProvider().update( getContentUri(),
                                   new ContentValues(),
                                   null,
                                   null );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testUpdateWithId()
   {
      final Uri contentUriWithId1 = getContentUriWithId_1();
      getContentProvider().update( contentUriWithId1,
                                   new ContentValues(),
                                   null,
                                   null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testUpdateNullUri()
   {
      getContentProvider().update( null, new ContentValues(), null, null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testUpdateNullContentValues()
   {
      getContentProvider().update( getContentUri(), null, null, null );
   }
   
   
   
   protected abstract Iterable< String > getSqlStatements();
   
   
   
   protected abstract int getNumberOfQueryAllRows();
   
   
   
   protected abstract Uri getContentUri();
   
   
   
   protected abstract Uri getContentUriWithId_1();
   
   
   
   protected abstract Uri getContentUriWithNotExistingId();
   
   
   
   protected abstract String[] getProjection();
   
   
   
   protected abstract void checkContent( Cursor c, long rowId );
}
