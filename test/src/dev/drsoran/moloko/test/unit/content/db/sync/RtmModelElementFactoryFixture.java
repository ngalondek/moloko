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

package dev.drsoran.moloko.test.unit.content.db.sync;

import static dev.drsoran.moloko.test.matchers.ReflectionEqualToMatcher.refEqualTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import android.database.Cursor;
import dev.drsoran.Pair;
import dev.drsoran.moloko.content.db.sync.RtmModelElementFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.sources.RtmModelElementFactoryTestDataSource;
import dev.drsoran.moloko.test.sources.RtmModelElementFactoryTestDataSource.TestData;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmRawTask;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTaskSeries;
import dev.drsoran.rtm.model.RtmTasksList;


public class RtmModelElementFactoryFixture extends MolokoTestCase
{
   private final static RtmModelElementFactoryTestDataSource testDataSource = new RtmModelElementFactoryTestDataSource();
   
   private IModelElementFactory fact;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      fact = new RtmModelElementFactory();
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_TasksList()
   {
      for ( TestData< RtmTasksList > testData : testDataSource.getTasksListTestData() )
      {
         final RtmTasksList element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                    RtmTasksList.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_RtmTaskSerieses()
   {
      for ( TestData< RtmTaskSeries > testData : testDataSource.getTaskSeriesTestData() )
      {
         final RtmTaskSeries element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                     RtmTaskSeries.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_RtmRawTask()
   {
      for ( TestData< RtmRawTask > testData : testDataSource.getRawTaskTestData() )
      {
         final RtmRawTask element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                  RtmRawTask.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_Notes()
   {
      for ( TestData< RtmNote > testData : testDataSource.getNotesTestData() )
      {
         final RtmNote element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                               RtmNote.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_Contacts()
   {
      for ( TestData< RtmContact > testData : testDataSource.getContactsTestData() )
      {
         final RtmContact element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                  RtmContact.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_Locations()
   {
      for ( TestData< RtmLocation > testData : testDataSource.getLocationsTestData() )
      {
         final RtmLocation element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                   RtmLocation.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_RtmSettings()
   {
      for ( TestData< RtmSettings > testData : testDataSource.getRtmSettingsTestData() )
      {
         final RtmSettings element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                   RtmSettings.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testCreateElementFromCursor_Unknown()
   {
      fact.createElementFromCursor( getCursor( Collections.< Pair< Class< ? >, Object >> emptyList() ),
                                    Object.class );
   }
   
   
   
   private Cursor getCursor( List< Pair< Class< ? >, Object > > values )
   {
      final Cursor c = EasyMock.createNiceMock( Cursor.class );
      
      int i = 0;
      for ( Iterator< Pair< Class< ? >, Object > > iterator = values.iterator(); iterator.hasNext(); )
      {
         final Pair< Class< ? >, Object > valueClassValuePair = iterator.next();
         final Class< ? > valueClass = valueClassValuePair.first;
         final Object value = valueClassValuePair.second;
         
         EasyMock.expect( c.isNull( i ) ).andReturn( value == null );
         
         if ( valueClass == Long.class )
         {
            EasyMock.expect( c.getLong( i ) )
                    .andReturn( (Long) ( value == null ? 0L : value ) );
         }
         else if ( valueClass == Integer.class )
         {
            EasyMock.expect( c.getInt( i ) )
                    .andReturn( (Integer) ( value == null ? 0 : value ) );
         }
         else if ( valueClass == String.class )
         {
            EasyMock.expect( c.getString( i ) ).andReturn( (String) value );
         }
         else if ( valueClass == Float.class )
         {
            EasyMock.expect( c.getFloat( i ) )
                    .andReturn( (Float) ( value == null ? 0.0f : value ) );
         }
         else
         {
            throw new IllegalArgumentException( "Unsupported type "
               + valueClass.getClass() );
         }
         
         ++i;
      }
      
      EasyMock.replay( c );
      
      return c;
   }
}
