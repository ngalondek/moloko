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

import static dev.drsoran.moloko.test.matchers.ContentValuesMatcher.equalValues;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.annotation.Config;

import android.content.ContentValues;
import dev.drsoran.moloko.content.db.sync.RtmContentValuesFactory;
import dev.drsoran.moloko.domain.content.IContentValuesFactory;
import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.sources.RtmContentValuesFactoryTestDataSource;
import dev.drsoran.moloko.test.sources.RtmContentValuesFactoryTestDataSource.TestData;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmRawTask;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTaskSeries;
import dev.drsoran.rtm.model.RtmTasksList;


@Config( manifest = Config.NONE )
public class RtmContentValuesFactoryFixture extends MolokoRoboTestCase
{
   private final static RtmContentValuesFactoryTestDataSource testDataSource = new RtmContentValuesFactoryTestDataSource();
   
   private IContentValuesFactory fact;
   
   
   
   @Before
   public void setUp() throws Exception
   {
      fact = new RtmContentValuesFactory();
   }
   
   
   
   @Test
   public void testCreateContentValues_TasksList()
   {
      for ( TestData< RtmTasksList > testData : testDataSource.getTasksListTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test
   public void testCreateContentValues_RtmTaskSerieses()
   {
      for ( TestData< RtmTaskSeries > testData : testDataSource.getTaskSeriesTestData() )
      {
         final ContentValues contentValues = fact.createContentValues( testData.modelElement );
         
         assertThat( testData.toString(), contentValues, notNullValue() );
         assertThat( testData.toString(),
                     contentValues,
                     equalValues( testData.contentValues ) );
      }
   }
   
   
   
   @Test
   public void testCreateContentValues_RtmRawTasks()
   {
      for ( TestData< RtmRawTask > testData : testDataSource.getRawTaskTestData() )
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
      for ( TestData< RtmNote > testData : testDataSource.getNotesTestData() )
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
      for ( TestData< RtmContact > testData : testDataSource.getContactsTestData() )
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
      for ( TestData< RtmLocation > testData : testDataSource.getLocationsTestData() )
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
      for ( TestData< RtmSettings > testData : testDataSource.getRtmSettingsTestData() )
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
