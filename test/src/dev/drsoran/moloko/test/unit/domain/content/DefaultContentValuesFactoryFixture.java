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

import static dev.drsoran.moloko.test.matchers.ContentValuesMatcher.equalValues;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;

import android.content.ContentValues;
import dev.drsoran.moloko.domain.content.DefaultContentValuesFactory;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.domain.model.Contact;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.Settings;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.sync.model.Modification;
import dev.drsoran.moloko.sync.model.SyncTime;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.sources.ContentValuesFactoryTestDataSource;
import dev.drsoran.moloko.test.sources.ContentValuesFactoryTestDataSource.TestData;


@Config( manifest = Config.NONE )
public class DefaultContentValuesFactoryFixture extends MolokoRoboTestCase
{
   private final static ContentValuesFactoryTestDataSource testDataSource = new ContentValuesFactoryTestDataSource();
   
   private IContentValuesFactory fact;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      fact = new DefaultContentValuesFactory();
   }
   
   
   
   @Test
   public void testCreateContentValues_TasksList()
   {
      for ( TestData< TasksList > testData : testDataSource.getTasksListTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test
   public void testCreateContentValues_Tasks()
   {
      for ( TestData< Task > testData : testDataSource.getTasksTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test
   public void testCreateContentValues_Notes()
   {
      for ( TestData< Note > testData : testDataSource.getNotesTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test
   public void testCreateContentValues_Participants()
   {
      for ( TestData< Participant > testData : testDataSource.getParticipantsTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test
   public void testCreateContentValues_Contacts()
   {
      for ( TestData< Contact > testData : testDataSource.getContactsTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test
   public void testCreateContentValues_Locations()
   {
      for ( TestData< Location > testData : testDataSource.getLocationsTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test
   public void testCreateContentValues_Modifications()
   {
      for ( TestData< Modification > testData : testDataSource.getModificationsTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test
   public void testCreateContentValues_RtmSettings()
   {
      for ( TestData< Settings > testData : testDataSource.getRtmSettingsTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test
   public void testCreateContentValues_Sync()
   {
      for ( TestData< SyncTime > testData : testDataSource.getSyncTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testCreateContentValues_Unknown()
   {
      fact.createContentValues( new Object() );
   }
}
