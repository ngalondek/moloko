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
import static dev.drsoran.moloko.test.TestConstants.LATER;
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
import dev.drsoran.moloko.content.Columns.NoteColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModificationsApplier;
import dev.drsoran.moloko.domain.content.Modification;
import dev.drsoran.moloko.domain.content.TaskNoteContentEditHandler;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.test.ModificationComparator;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.TestCalendarProvider;
import dev.drsoran.moloko.test.sources.TaskNoteEditHandlerTestDataSource;
import dev.drsoran.moloko.util.Lambda.Func2;


@Config( manifest = Config.NONE )
public class TaskNoteContentEditHandlerFixture extends MolokoRoboTestCase
{
   @Test
   public void testInsertAggregatedElement()
   {
      final Uri boundUri = ContentUris.bindAggregationIdToUri( ContentUris.TASK_NOTES_CONTENT_URI,
                                                               100L );
      
      final Uri resUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                   100L,
                                                                   1L );
      
      final Note note = new Note( 1L, NOW );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.insert( EasyMock.eq( boundUri ),
                                               EasyMock.anyObject( ContentValues.class ) ) )
              .andReturn( resUri );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.expect( fact.createContentValues( note ) )
              .andReturn( new ContentValues() );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TaskNoteContentEditHandler handler = new TaskNoteContentEditHandler( contentResolver,
                                                                                 fact,
                                                                                 applier,
                                                                                 TestCalendarProvider.get() );
      
      final Uri res = handler.insertAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI,
                                                       note,
                                                       100L );
      
      assertThat( res, notNullValue() );
      assertThat( res, equalTo( resUri ) );
      
      EasyMock.verify( contentResolver );
      EasyMock.verify( fact );
      EasyMock.verify( applier );
   }
   
   
   
   @Test( expected = ContentException.class )
   public void testInsertAggregatedElement_ContentResolverException()
   {
      final Uri boundUri = ContentUris.bindAggregationIdToUri( ContentUris.TASK_NOTES_CONTENT_URI,
                                                               100L );
      
      final Note note = new Note( 1L, NOW );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.insert( EasyMock.eq( boundUri ),
                                               EasyMock.anyObject( ContentValues.class ) ) )
              .andThrow( new RuntimeException() );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.expect( fact.createContentValues( note ) )
              .andReturn( new ContentValues() );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TaskNoteContentEditHandler handler = new TaskNoteContentEditHandler( contentResolver,
                                                                                 fact,
                                                                                 applier,
                                                                                 TestCalendarProvider.get() );
      try
      {
         handler.insertAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI,
                                          note,
                                          100L );
      }
      finally
      {
         EasyMock.verify( contentResolver );
         EasyMock.verify( fact );
         EasyMock.verify( applier );
      }
   }
   
   
   
   @Test
   public void testUpdateAggregatedElement()
   {
      final ContentValues contentValues = new ContentValues();
      
      for ( TaskNoteEditHandlerTestDataSource.TestData< Note > testData : new TaskNoteEditHandlerTestDataSource( 100L,
                                                                                                                 1L ).getUpdateTestData() )
      {
         final Uri boundUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                        100L,
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
         
         final TaskNoteContentEditHandler handler = new TaskNoteContentEditHandler( contentResolver,
                                                                                    fact,
                                                                                    applier,
                                                                                    TestCalendarProvider.get() );
         
         handler.updateAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                          testData.existingElement,
                                          testData.updateElement,
                                          100L,
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
   public void testUpdateAggregatedElement_NotFound()
   {
      final ContentValues contentValues = new ContentValues();
      
      final Uri boundUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                     100L,
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
      
      final TaskNoteContentEditHandler handler = new TaskNoteContentEditHandler( contentResolver,
                                                                                 fact,
                                                                                 applier,
                                                                                 TestCalendarProvider.get() );
      
      try
      {
         handler.updateAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                          new Note( 1L, NOW ),
                                          new Note( 1L, LATER ),
                                          100L,
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
   public void testUpdateAggregatedElement_ContentResolverException()
   {
      final ContentValues contentValues = new ContentValues();
      
      final Uri boundUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                     100L,
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
      
      final TaskNoteContentEditHandler handler = new TaskNoteContentEditHandler( contentResolver,
                                                                                 fact,
                                                                                 applier,
                                                                                 TestCalendarProvider.get() );
      
      try
      {
         handler.updateAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                          new Note( 1L, NOW ),
                                          new Note( 1L, LATER ),
                                          100L,
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
   public void testDeleteAggregatedElement()
   {
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      final Capture< Iterable< Modification > > modifications = new Capture< Iterable< Modification > >();
      applier.applyPersistentModifications( EasyMock.capture( modifications ) );
      EasyMock.replay( applier );
      
      final TaskNoteContentEditHandler handler = new TaskNoteContentEditHandler( contentResolver,
                                                                                 fact,
                                                                                 applier,
                                                                                 TestCalendarProvider.get() );
      
      handler.deleteAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                       100L,
                                       1L );
      
      final String modUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                                                      100L,
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
                                                                 NoteColumns.NOTE_DELETED_DATE,
                                                                 // This may lead to a failed test due to time
                                                                 // differences
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
      
      final TaskNoteContentEditHandler handler = new TaskNoteContentEditHandler( contentResolver,
                                                                                 fact,
                                                                                 applier,
                                                                                 TestCalendarProvider.get() );
      
      try
      {
         handler.deleteAggregatedElement( ContentUris.TASK_NOTES_CONTENT_URI_ID,
                                          100L,
                                          1L );
      }
      finally
      {
         EasyMock.verify( contentResolver );
         EasyMock.verify( fact );
         EasyMock.verify( applier );
      }
   }
}
