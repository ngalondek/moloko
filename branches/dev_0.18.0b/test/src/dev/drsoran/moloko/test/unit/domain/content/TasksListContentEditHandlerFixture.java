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

import static dev.drsoran.moloko.test.IterableAsserts.assertEqualSet;
import static dev.drsoran.moloko.test.TestConstants.NOW;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import org.robolectric.annotation.Config;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModificationsApplier;
import dev.drsoran.moloko.domain.content.Modification;
import dev.drsoran.moloko.domain.content.TasksListContentEditHandler;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.test.ModificationComparator;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.TestCalendarProvider;
import dev.drsoran.moloko.test.sources.TasksListEditHandlerTestDataSource;
import dev.drsoran.moloko.util.Lambda.Func2;


@Config( manifest = Config.NONE )
public class TasksListContentEditHandlerFixture extends MolokoRoboTestCase
{
   @Test
   public void testInsertElement()
   {
      final Uri resUri = ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                    1L );
      
      final TasksList list = new TasksList( 1L,
                                            NOW,
                                            "TestList",
                                            0,
                                            false,
                                            false );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.insert( EasyMock.eq( ContentUris.TASKS_LISTS_CONTENT_URI ),
                                               EasyMock.anyObject( ContentValues.class ) ) )
              .andReturn( resUri );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.expect( fact.createContentValues( list ) )
              .andReturn( new ContentValues() );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TasksListContentEditHandler handler = new TasksListContentEditHandler( contentResolver,
                                                                                   fact,
                                                                                   applier,
                                                                                   TestCalendarProvider.get() );
      
      final Uri res = handler.insertElement( ContentUris.TASKS_LISTS_CONTENT_URI,
                                             list );
      
      assertThat( res, notNullValue() );
      assertThat( res, equalTo( resUri ) );
      
      EasyMock.verify( contentResolver );
      EasyMock.verify( fact );
      EasyMock.verify( applier );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testInsertElement_ContentResolverException()
   {
      final TasksList list = new TasksList( 1L,
                                            NOW,
                                            "TestList",
                                            0,
                                            false,
                                            false );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.insert( EasyMock.eq( ContentUris.TASKS_LISTS_CONTENT_URI ),
                                               EasyMock.anyObject( ContentValues.class ) ) )
              .andThrow( new RuntimeException() );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.expect( fact.createContentValues( list ) )
              .andReturn( new ContentValues() );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TasksListContentEditHandler handler = new TasksListContentEditHandler( contentResolver,
                                                                                   fact,
                                                                                   applier,
                                                                                   TestCalendarProvider.get() );
      try
      {
         handler.insertElement( ContentUris.TASKS_LISTS_CONTENT_URI, list );
      }
      finally
      {
         EasyMock.verify( contentResolver );
         EasyMock.verify( fact );
         EasyMock.verify( applier );
      }
   }
   
   
   
   @Test
   public void testUpdateElement()
   {
      final ContentValues contentValues = new ContentValues();
      
      for ( TasksListEditHandlerTestDataSource.TestData< TasksList > testData : new TasksListEditHandlerTestDataSource( 1L ).getUpdateTestData() )
      {
         final Uri boundUri = ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                         1L );
         
         final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
         EasyMock.expect( contentResolver.update( boundUri,
                                                  contentValues,
                                                  null,
                                                  null ) ).andReturn( 1 );
         EasyMock.replay( contentResolver );
         
         final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
         EasyMock.expect( fact.createContentValues( testData.updateElement ) )
                 .andReturn( contentValues );
         EasyMock.replay( fact );
         
         final Capture< Collection< Modification > > modCapture = new Capture< Collection< Modification > >();
         final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
         applier.applyPersistentModifications( EasyMock.capture( modCapture ) );
         EasyMock.replay( applier );
         
         final TasksListContentEditHandler handler = new TasksListContentEditHandler( contentResolver,
                                                                                      fact,
                                                                                      applier,
                                                                                      TestCalendarProvider.get() );
         
         handler.updateElement( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                testData.existingElement,
                                testData.updateElement,
                                1L );
         
         assertEqualSet( modCapture.getValue(),
                         testData.expectedModifications,
                         new ModificationComparator() );
         
         EasyMock.verify( contentResolver );
         EasyMock.verify( fact );
         EasyMock.verify( applier );
      }
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testUpdateElement_NotFound()
   {
      final ContentValues contentValues = new ContentValues();
      
      final Uri boundUri = ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                      1L );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.update( boundUri,
                                               contentValues,
                                               null,
                                               null ) ).andReturn( 0 );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.expect( fact.createContentValues( EasyMock.anyObject() ) )
              .andReturn( contentValues );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TasksListContentEditHandler handler = new TasksListContentEditHandler( contentResolver,
                                                                                   fact,
                                                                                   applier,
                                                                                   TestCalendarProvider.get() );
      
      try
      {
         handler.updateElement( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                new TasksList( 1L,
                                               NOW,
                                               "TestList",
                                               0,
                                               false,
                                               false ),
                                new TasksList( 1L,
                                               NOW,
                                               "TestList1",
                                               0,
                                               false,
                                               false ),
                                1L );
      }
      finally
      {
         EasyMock.verify( contentResolver );
         EasyMock.verify( fact );
         EasyMock.verify( applier );
      }
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testUpdateElement_ContentResolverException()
   {
      final ContentValues contentValues = new ContentValues();
      
      final Uri boundUri = ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                      1L );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.update( boundUri,
                                               contentValues,
                                               null,
                                               null ) )
              .andThrow( new RuntimeException() );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.expect( fact.createContentValues( EasyMock.anyObject() ) )
              .andReturn( contentValues );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TasksListContentEditHandler handler = new TasksListContentEditHandler( contentResolver,
                                                                                   fact,
                                                                                   applier,
                                                                                   TestCalendarProvider.get() );
      
      try
      {
         handler.updateElement( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                new TasksList( 1L,
                                               NOW,
                                               "TestList",
                                               0,
                                               false,
                                               false ),
                                new TasksList( 1L,
                                               NOW,
                                               "TestList1",
                                               0,
                                               false,
                                               false ),
                                1L );
      }
      finally
      {
         EasyMock.verify( contentResolver );
         EasyMock.verify( fact );
         EasyMock.verify( applier );
      }
   }
   
   
   
   @Test
   public void testDeleteElement()
   {
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      final Capture< Iterable< Modification > > modifications = new Capture< Iterable< Modification > >();
      applier.applyPersistentModifications( EasyMock.capture( modifications ) );
      EasyMock.replay( applier );
      
      final TasksListContentEditHandler handler = new TasksListContentEditHandler( contentResolver,
                                                                                   fact,
                                                                                   applier,
                                                                                   TestCalendarProvider.get() );
      
      handler.deleteElement( ContentUris.TASKS_LISTS_CONTENT_URI_ID, 1L );
      
      final String modUri = ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                       1L )
                                       .toString();
      
      assertEqualSet( modifications.getValue(),
                      new ModificationComparator( new Func2< String, String, Integer >()
                      {
                         @Override
                         public Integer call( String newVal1, String newVal2 )
                         {
                            // We cannot test exactly against the deleted time stamp because of test runtime. So we
                            // simply check if it is set.
                            if ( Long.parseLong( newVal1 ) != Constants.NO_TIME
                               && Long.parseLong( newVal2 ) != Constants.NO_TIME )
                            {
                               return 0;
                            }
                            
                            return -1;
                         }
                      } ),
                      Modification.newNonPersistentModification( modUri,
                                                                 TasksListColumns.LIST_DELETED_DATE,
                                                                 NOW ) );
      
      EasyMock.verify( contentResolver );
      EasyMock.verify( fact );
      EasyMock.verify( applier );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = ContentException.class )
   public void testDeleteAggregatedElement_ModificationsApplierException()
   {
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      applier.applyPersistentModifications( EasyMock.anyObject( Iterable.class ) );
      EasyMock.expectLastCall().andThrow( new ContentException() );
      EasyMock.replay( applier );
      
      final TasksListContentEditHandler handler = new TasksListContentEditHandler( contentResolver,
                                                                                   fact,
                                                                                   applier,
                                                                                   TestCalendarProvider.get() );
      
      try
      {
         handler.deleteElement( ContentUris.TASKS_LISTS_CONTENT_URI_ID, 1L );
      }
      finally
      {
         EasyMock.verify( contentResolver );
         EasyMock.verify( fact );
         EasyMock.verify( applier );
      }
   }
}
