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
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.content.IModificationsApplier;
import dev.drsoran.moloko.domain.content.Modification;
import dev.drsoran.moloko.domain.content.TaskParticipantContentEditHandler;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.test.ModificationComparator;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.sources.TaskParticipantEditHandlerTestDataSource;


@Config( manifest = Config.NONE )
public class TaskParticipantContentEditHandlerFixture extends
         MolokoRoboTestCase
{
   @Test
   public void testInsertAggregatedElement()
   {
      final Uri boundUri = ContentUris.bindAggregationIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                                               100L );
      
      final Uri resUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                                   100L,
                                                                   1L );
      
      final Participant participant = new Participant( 1L, 2L, "name", "user" );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.insert( EasyMock.eq( boundUri ),
                                               EasyMock.anyObject( ContentValues.class ) ) )
              .andReturn( resUri );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.expect( fact.createContentValues( participant ) )
              .andReturn( new ContentValues() );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TaskParticipantContentEditHandler handler = new TaskParticipantContentEditHandler( contentResolver,
                                                                                               fact,
                                                                                               applier );
      
      final Uri res = handler.insertAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                                       participant,
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
      final Uri boundUri = ContentUris.bindAggregationIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                                               100L );
      
      final Participant participant = new Participant( 1L, 2L, "name", "user" );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.insert( EasyMock.eq( boundUri ),
                                               EasyMock.anyObject( ContentValues.class ) ) )
              .andThrow( new RuntimeException() );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.expect( fact.createContentValues( participant ) )
              .andReturn( new ContentValues() );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TaskParticipantContentEditHandler handler = new TaskParticipantContentEditHandler( contentResolver,
                                                                                               fact,
                                                                                               applier );
      try
      {
         handler.insertAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI,
                                          participant,
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
      
      for ( TaskParticipantEditHandlerTestDataSource.TestData< Participant > testData : new TaskParticipantEditHandlerTestDataSource( 100L,
                                                                                                                                      1L ).getUpdateTestData() )
      {
         final Uri boundUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
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
         
         final TaskParticipantContentEditHandler handler = new TaskParticipantContentEditHandler( contentResolver,
                                                                                                  fact,
                                                                                                  applier );
         
         handler.updateAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
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
      
      final Uri boundUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
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
      
      final TaskParticipantContentEditHandler handler = new TaskParticipantContentEditHandler( contentResolver,
                                                                                               fact,
                                                                                               applier );
      
      try
      {
         handler.updateAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                          new Participant( 1L, 10L, "f", "u" ),
                                          new Participant( 1L, 10L, "f", "e" ),
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
      
      final Uri boundUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
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
      
      final TaskParticipantContentEditHandler handler = new TaskParticipantContentEditHandler( contentResolver,
                                                                                               fact,
                                                                                               applier );
      
      try
      {
         handler.updateAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                          new Participant( 1L, 10L, "f", "u" ),
                                          new Participant( 1L, 10L, "f", "e" ),
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
      final Uri boundUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                                     100L,
                                                                     1L );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.delete( boundUri, null, null ) )
              .andReturn( 1 );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TaskParticipantContentEditHandler handler = new TaskParticipantContentEditHandler( contentResolver,
                                                                                               fact,
                                                                                               applier );
      
      handler.deleteAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                       100L,
                                       1L );
      
      EasyMock.verify( contentResolver );
      EasyMock.verify( fact );
      EasyMock.verify( applier );
   }
   
   
   
   @Test( expected = NoSuchElementException.class )
   public void testDeleteAggregatedElement_NotFound()
   {
      final Uri boundUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                                     100L,
                                                                     1L );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.delete( boundUri, null, null ) )
              .andReturn( 0 );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TaskParticipantContentEditHandler handler = new TaskParticipantContentEditHandler( contentResolver,
                                                                                               fact,
                                                                                               applier );
      
      try
      {
         handler.deleteAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
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
   public void testDeleteAggregatedElement_ContentResolverException()
   {
      final Uri boundUri = ContentUris.bindAggregatedElementIdToUri( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
                                                                     100L,
                                                                     1L );
      
      final ContentResolver contentResolver = EasyMock.createStrictMock( ContentResolver.class );
      EasyMock.expect( contentResolver.delete( boundUri, null, null ) )
              .andThrow( new RuntimeException() );
      EasyMock.replay( contentResolver );
      
      final IContentValuesFactory fact = EasyMock.createStrictMock( IContentValuesFactory.class );
      EasyMock.replay( fact );
      
      final IModificationsApplier applier = EasyMock.createStrictMock( IModificationsApplier.class );
      EasyMock.replay( applier );
      
      final TaskParticipantContentEditHandler handler = new TaskParticipantContentEditHandler( contentResolver,
                                                                                               fact,
                                                                                               applier );
      
      try
      {
         handler.deleteAggregatedElement( ContentUris.TASK_PARTICIPANTS_CONTENT_URI_ID,
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
