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


public abstract class MolokoReadWriteDbContentTestCase extends
         MolokoReadDbContentTestCase
{
   @Override
   @Test
   public void testInsert()
   {
      final Uri insertedUri = getContentProvider().insert( getContentUri(),
                                                           createInsertContent() );
      assertNotNull( insertedUri );
      c = getContentProvider().query( insertedUri,
                                      getProjection(),
                                      null,
                                      null,
                                      null );
      
      assertThat( c.getCount(), is( 1 ) );
      assertThat( c.moveToNext(), is( true ) );
      checkInsertContent( c );
   }
   
   
   
   @Override
   @Test( expected = IllegalArgumentException.class )
   public void testInsertWithId()
   {
      final Uri contentUriWithId1 = getContentUriWithId_1();
      getContentProvider().insert( contentUriWithId1, new ContentValues() );
   }
   
   
   
   @Override
   @Test( expected = IllegalArgumentException.class )
   public void testInsertNullUri()
   {
      getContentProvider().insert( null, new ContentValues() );
   }
   
   
   
   @Override
   @Test( expected = IllegalArgumentException.class )
   public void testInsertNullContentValues()
   {
      getContentProvider().insert( getContentUri(), null );
   }
   
   
   
   @Override
   @Test
   public void testDeleteAll()
   {
      prepareDatabase( getSqlStatements() );
      final int numDeleted = getContentProvider().delete( getContentUri(),
                                                          null,
                                                          null );
      assertThat( numDeleted, is( getNumberOfQueryAllRows() ) );
   }
   
   
   
   @Override
   @Test
   public void testDeleteWithId()
   {
      final Uri contentUriWithId1 = getContentUriWithId_1();
      
      prepareDatabase( getSqlStatements() );
      final int numDeleted = getContentProvider().delete( contentUriWithId1,
                                                          null,
                                                          null );
      assertThat( numDeleted, is( 1 ) );
   }
   
   
   
   @Test
   public void testDeleteWithNonExistingId()
   {
      final Uri contentUri = getContentUriWithNotExistingId();
      
      prepareDatabase( getSqlStatements() );
      final int numDeleted = getContentProvider().delete( contentUri,
                                                          null,
                                                          null );
      assertThat( numDeleted, is( 0 ) );
   }
   
   
   
   @Test
   public void testDeleteWithSelection()
   {
      prepareDatabase( getSqlStatements() );
      final int numDeleted = getContentProvider().delete( getContentUri(),
                                                          _ID + "=1",
                                                          null );
      assertThat( numDeleted, is( 1 ) );
   }
   
   
   
   @Test
   public void testDeleteWithSelectionArgs()
   {
      prepareDatabase( getSqlStatements() );
      final int numDeleted = getContentProvider().delete( getContentUri(),
                                                          _ID + "=?",
                                                          new String[]
                                                          { "1" } );
      assertThat( numDeleted, is( 1 ) );
   }
   
   
   
   @Override
   @Test( expected = IllegalArgumentException.class )
   public void testDeleteNullUri()
   {
      getContentProvider().delete( null, null, null );
   }
   
   
   
   @Override
   @Test( expected = IllegalArgumentException.class )
   public void testUpdateAll()
   {
      getContentProvider().update( getContentUri(),
                                   new ContentValues(),
                                   null,
                                   null );
   }
   
   
   
   @Override
   @Test
   public void testUpdateWithId()
   {
      final Uri contentUriWithId1 = getContentUriWithId_1();
      
      prepareDatabase( getSqlStatements() );
      
      final int numUpdated = getContentProvider().update( contentUriWithId1,
                                                          createUpdateContentForId_1(),
                                                          null,
                                                          null );
      
      assertThat( numUpdated, is( 1 ) );
      
      c = getContentProvider().query( contentUriWithId1,
                                      getProjection(),
                                      null,
                                      null,
                                      null );
      assertThat( c.moveToNext(), is( true ) );
      checkUpdatedContent( c );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testUpdateWithIdInContentValues()
   {
      final Uri contentUriWithId1 = getContentUriWithId_1();
      
      prepareDatabase( getSqlStatements() );
      
      final ContentValues contentValues = new ContentValues( 1 );
      contentValues.put( _ID, 100L );
      
      getContentProvider().update( contentUriWithId1, contentValues, null, null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testUpdateWithSelection()
   {
      getContentProvider().update( getContentUri(),
                                   new ContentValues(),
                                   _ID + "=0",
                                   null );
   }
   
   
   
   @Override
   @Test( expected = IllegalArgumentException.class )
   public void testUpdateNullUri()
   {
      getContentProvider().update( null, new ContentValues(), null, null );
   }
   
   
   
   @Override
   @Test( expected = IllegalArgumentException.class )
   public void testUpdateNullContentValues()
   {
      getContentProvider().update( getContentUri(), null, null, null );
   }
   
   
   
   @Test
   public void testUpdateEmptyContentValues()
   {
      prepareDatabase( getSqlStatements() );
      final int numUpdated = getContentProvider().update( getContentUriWithId_1(),
                                                          new ContentValues(),
                                                          null,
                                                          null );
      assertThat( numUpdated, is( 0 ) );
   }
   
   
   
   protected abstract ContentValues createInsertContent();
   
   
   
   protected abstract void checkInsertContent( Cursor c );
   
   
   
   protected abstract ContentValues createUpdateContentForId_1();
   
   
   
   protected abstract void checkUpdatedContent( Cursor c );
}
