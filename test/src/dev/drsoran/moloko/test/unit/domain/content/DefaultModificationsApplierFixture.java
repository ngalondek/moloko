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

package dev.drsoran.moloko.test.unit.domain.content;

import java.util.Arrays;
import java.util.Collections;

import org.easymock.EasyMock;
import org.junit.Test;
import org.robolectric.annotation.Config;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModificationsApplier;
import dev.drsoran.moloko.domain.content.MolokoModificationsApplier;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.sync.Modification;
import dev.drsoran.moloko.sync.db.TableColumns.ModificationColumns;
import dev.drsoran.moloko.test.MolokoRoboTestCase;


@Config( manifest = Config.NONE )
public class DefaultModificationsApplierFixture extends MolokoRoboTestCase
{
   @Test
   public void testApplyPersistentModifications_Empty()
   {
      final IModificationsApplier applier = new MolokoModificationsApplier( EasyMock.createNiceMock( ContentResolver.class ),
                                                                            EasyMock.createNiceMock( IContentValuesFactory.class ) );
      
      applier.applyPersistentModifications( Collections.< Modification > emptyList() );
   }
   
   
   
   @Test
   public void testApplyPersistentModifications_NonPersistent()
   {
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = new MolokoModificationsApplier( contentResolver,
                                                                            fact );
      
      applier.applyPersistentModifications( Arrays.asList( Modification.newNonPersistentModification( "content://test/elements/1",
                                                                                                      "testCol",
                                                                                                      "1" ) ) );
      
      EasyMock.verify( contentResolver );
      EasyMock.verify( fact );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testApplyPersistentModifications_ContentResolverException()
   {
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.query( EasyMock.anyObject( Uri.class ),
                                              EasyMock.anyObject( String[].class ),
                                              EasyMock.anyObject( String.class ),
                                              EasyMock.anyObject( String[].class ),
                                              EasyMock.anyObject( String.class ) ) )
              .andThrow( new RuntimeException() );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = new MolokoModificationsApplier( contentResolver,
                                                                            fact );
      
      try
      {
         applier.applyPersistentModifications( Arrays.asList( Modification.newModification( "content://test/elements/1",
                                                                                            "testCol",
                                                                                            "1" ) ) );
      }
      finally
      {
         EasyMock.verify( contentResolver );
         EasyMock.verify( fact );
      }
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testApplyPersistentModifications_ContentValuesException()
   {
      final Cursor c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.moveToNext() ).andReturn( false );
      c.close();
      EasyMock.replay( c );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.query( EasyMock.anyObject( Uri.class ),
                                              EasyMock.anyObject( String[].class ),
                                              EasyMock.anyObject( String.class ),
                                              EasyMock.anyObject( String[].class ),
                                              EasyMock.anyObject( String.class ) ) )
              .andReturn( c );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.expect( fact.createContentValues( EasyMock.anyObject( Modification.class ) ) )
              .andThrow( new IllegalArgumentException() );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = new MolokoModificationsApplier( contentResolver,
                                                                            fact );
      
      try
      {
         applier.applyPersistentModifications( Arrays.asList( Modification.newModification( "content://test/elements/1",
                                                                                            "testCol",
                                                                                            "1" ) ) );
         
      }
      finally
      {
         EasyMock.verify( contentResolver );
         EasyMock.verify( fact );
      }
   }
   
   
   
   @Test
   public void testApplyPersistentModifications_InsertNew()
   {
      final String uri = "content://test/elements/1";
      final String colName = "testCol";
      final Modification modification = Modification.newModification( uri,
                                                                      colName,
                                                                      "1" );
      final ContentValues modContentValues = new ContentValues();
      
      final Cursor c = EasyMock.createStrictMock( Cursor.class );
      EasyMock.expect( c.moveToNext() ).andReturn( false );
      c.close();
      EasyMock.replay( c );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.query( EasyMock.eq( ContentUris.MODIFICATIONS_CONTENT_URI ),
                                              EasyMock.aryEq( ModificationColumns.PROJECTION ),
                                              EasyMock.anyObject( String.class ),
                                              EasyMock.aryEq( new String[]
                                              { uri, colName } ),
                                              EasyMock.eq( (String) null ) ) )
              .andReturn( c );
      EasyMock.expect( contentResolver.insert( ContentUris.MODIFICATIONS_CONTENT_URI,
                                               modContentValues ) )
              .andReturn( null );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.expect( fact.createContentValues( modification ) )
              .andReturn( modContentValues );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = new MolokoModificationsApplier( contentResolver,
                                                                            fact );
      
      applier.applyPersistentModifications( Arrays.asList( modification ) );
      
      EasyMock.verify( c );
      EasyMock.verify( contentResolver );
      EasyMock.verify( fact );
   }
   
   
   
   @Test
   public void testApplyPersistentModifications_UpdateExisting()
   {
      final String uri = "content://test/elements/1";
      final String colName = "testCol";
      final Modification modification = Modification.newModification( uri,
                                                                      colName,
                                                                      "1" );
      final ContentValues updateContentValues = new ContentValues( 1 );
      updateContentValues.put( ModificationColumns.NEW_VALUE, "1" );
      
      final Cursor c = EasyMock.createNiceMock( Cursor.class );
      EasyMock.expect( c.moveToNext() ).andReturn( true );
      EasyMock.expect( c.getString( ModificationColumns.SYNCED_VALUE_IDX ) )
              .andReturn( "0" );
      EasyMock.expect( c.getLong( Columns.ID_IDX ) ).andReturn( 1L );
      c.close();
      EasyMock.replay( c );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.query( EasyMock.eq( ContentUris.MODIFICATIONS_CONTENT_URI ),
                                              EasyMock.aryEq( ModificationColumns.PROJECTION ),
                                              EasyMock.anyObject( String.class ),
                                              EasyMock.aryEq( new String[]
                                              { uri, colName } ),
                                              EasyMock.eq( (String) null ) ) )
              .andReturn( c );
      EasyMock.expect( contentResolver.update( ContentUris.bindElementId( ContentUris.MODIFICATIONS_CONTENT_URI_ID,
                                                                          1L ),
                                               updateContentValues,
                                               null,
                                               null ) )
              .andReturn( 1 );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = new MolokoModificationsApplier( contentResolver,
                                                                            fact );
      
      applier.applyPersistentModifications( Arrays.asList( modification ) );
      
      EasyMock.verify( c );
      EasyMock.verify( contentResolver );
      EasyMock.verify( fact );
   }
   
   
   
   @Test
   public void testApplyPersistentModifications_RevertExisting()
   {
      final String uri = "content://test/elements/1";
      final String colName = "testCol";
      final Modification modification = Modification.newModification( uri,
                                                                      colName,
                                                                      "0" );
      final Cursor c = EasyMock.createNiceMock( Cursor.class );
      EasyMock.expect( c.moveToNext() ).andReturn( true );
      EasyMock.expect( c.getString( ModificationColumns.SYNCED_VALUE_IDX ) )
              .andReturn( "0" );
      EasyMock.expect( c.getLong( Columns.ID_IDX ) ).andReturn( 1L );
      c.close();
      EasyMock.replay( c );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.query( EasyMock.eq( ContentUris.MODIFICATIONS_CONTENT_URI ),
                                              EasyMock.aryEq( ModificationColumns.PROJECTION ),
                                              EasyMock.anyObject( String.class ),
                                              EasyMock.aryEq( new String[]
                                              { uri, colName } ),
                                              EasyMock.eq( (String) null ) ) )
              .andReturn( c );
      EasyMock.expect( contentResolver.delete( ContentUris.bindElementId( ContentUris.MODIFICATIONS_CONTENT_URI_ID,
                                                                          1L ),
                                               null,
                                               null ) )
              .andReturn( 1 );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = new MolokoModificationsApplier( contentResolver,
                                                                            fact );
      
      applier.applyPersistentModifications( Arrays.asList( modification ) );
      
      EasyMock.verify( c );
      EasyMock.verify( contentResolver );
      EasyMock.verify( fact );
   }
}
