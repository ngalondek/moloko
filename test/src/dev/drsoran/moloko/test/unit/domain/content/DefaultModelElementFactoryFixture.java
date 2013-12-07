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

import static dev.drsoran.moloko.test.matchers.ReflectionEqualToMatcher.refEqualTo;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import android.database.Cursor;
import dev.drsoran.moloko.domain.content.DefaultModelElementFactory;
import dev.drsoran.moloko.domain.content.IModelElementFactory;
import dev.drsoran.moloko.domain.model.Contact;
import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.RtmSettings;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.sync.model.Modification;
import dev.drsoran.moloko.sync.model.SyncTime;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.sources.ModelElementFactoryTestDataSource;
import dev.drsoran.moloko.test.sources.ModelElementFactoryTestDataSource.TestData;
import dev.drsoran.moloko.util.Pair;


public class DefaultModelElementFactoryFixture extends MolokoTestCase
{
   private final static ModelElementFactoryTestDataSource testDataSource = new ModelElementFactoryTestDataSource();
   
   private IModelElementFactory fact;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      fact = new DefaultModelElementFactory();
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_TasksList()
   {
      for ( TestData< TasksList > testData : testDataSource.getTasksListTestData() )
      {
         final TasksList element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                 TasksList.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_Tasks()
   {
      for ( TestData< Task > testData : testDataSource.getTasksTestData() )
      {
         final Task element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                            Task.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_Notes()
   {
      for ( TestData< Note > testData : testDataSource.getNotesTestData() )
      {
         final Note element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                            Note.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_Participants()
   {
      for ( TestData< Participant > testData : testDataSource.getParticipantsTestData() )
      {
         final Participant element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                   Participant.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_Contacts()
   {
      for ( TestData< Contact > testData : testDataSource.getContactsTestData() )
      {
         final Contact element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                               Contact.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_Locations()
   {
      for ( TestData< Location > testData : testDataSource.getLocationsTestData() )
      {
         final Location element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                Location.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_Modifications()
   {
      for ( TestData< Modification > testData : testDataSource.getModificationsTestData() )
      {
         final Modification element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                    Modification.class );
         
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
   
   
   
   @Test
   public void testCreateElementFromCursor_Sync()
   {
      for ( TestData< SyncTime > testData : testDataSource.getSyncTestData() )
      {
         final SyncTime element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                            SyncTime.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_ExtendedTaskCount()
   {
      for ( TestData< ExtendedTaskCount > testData : testDataSource.getExtendedTaskCountTestData() )
      {
         final ExtendedTaskCount element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                                         ExtendedTaskCount.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     refEqualTo( testData.modelElement ) );
      }
   }
   
   
   
   @Test
   public void testCreateElementFromCursor_Tags()
   {
      for ( TestData< String > testData : testDataSource.getTagsTestData() )
      {
         final String element = fact.createElementFromCursor( getCursor( testData.contentValues ),
                                                              String.class );
         
         assertThat( testData.testName, element, notNullValue() );
         assertThat( testData.testName,
                     element,
                     equalTo( testData.modelElement ) );
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
