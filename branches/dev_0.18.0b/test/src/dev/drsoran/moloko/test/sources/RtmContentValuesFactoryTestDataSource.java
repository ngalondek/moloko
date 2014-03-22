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

package dev.drsoran.moloko.test.sources;

import static dev.drsoran.rtm.test.TestConstants.LATER;
import static dev.drsoran.rtm.test.TestConstants.NEVER;
import static dev.drsoran.rtm.test.TestConstants.NOW;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import dev.drsoran.moloko.content.db.TableColumns.RtmContactColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmNoteColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmSettingsColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmRawTask;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTaskSeries;
import dev.drsoran.rtm.model.RtmTasksList;


public class RtmContentValuesFactoryTestDataSource
{
   
   public Collection< TestData< RtmTasksList >> getTasksListTestData()
   {
      final Collection< TestData< RtmTasksList >> testData = new LinkedList< TestData< RtmTasksList > >();
      
      addTasksListLockedArchivedPosition( testData );
      addTasksListDeleted( testData );
      addTasksListSmart( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< RtmTaskSeries >> getTaskSeriesTestData()
   {
      final Collection< TestData< RtmTaskSeries >> testData = new LinkedList< TestData< RtmTaskSeries > >();
      
      addTaskSeriesSimpleValues( testData );
      addTaskSeriesRecurrenceAfter( testData );
      addTaskSeriesRecurrenceEvery( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< RtmRawTask >> getRawTaskTestData()
   {
      final Collection< TestData< RtmRawTask >> testData = new LinkedList< TestData< RtmRawTask > >();
      
      addRawTaskSimpleValues( testData );
      addRawTaskDueNoTime( testData );
      addRawTaskDueWithTime( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< RtmNote >> getNotesTestData()
   {
      final Collection< TestData< RtmNote >> testData = new LinkedList< TestData< RtmNote > >();
      
      addNoteFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< RtmContact >> getContactsTestData()
   {
      final Collection< TestData< RtmContact >> testData = new LinkedList< TestData< RtmContact > >();
      
      addContactFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< RtmLocation >> getLocationsTestData()
   {
      final Collection< TestData< RtmLocation >> testData = new LinkedList< TestData< RtmLocation > >();
      
      addLocationNoAddr( testData );
      addLocationFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< RtmSettings >> getRtmSettingsTestData()
   {
      final Collection< TestData< RtmSettings >> testData = new LinkedList< TestData< RtmSettings > >();
      
      addRtmSettingsNoDefList( testData );
      addRtmSettingsFull( testData );
      
      return testData;
   }
   
   
   
   private void addTasksListLockedArchivedPosition( Collection< TestData< RtmTasksList >> testData )
   {
      final RtmTasksList modelElement = new RtmTasksList( "1",
                                                          -1,
                                                          false,
                                                          true,
                                                          true,
                                                          "TestList1",
                                                          null );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmTasksListColumns.RTM_LIST_ID, "1" );
      values.put( RtmTasksListColumns.LIST_NAME, "TestList1" );
      values.put( RtmTasksListColumns.LOCKED, 1 );
      values.put( RtmTasksListColumns.ARCHIVED, 1 );
      values.put( RtmTasksListColumns.POSITION, -1 );
      values.put( RtmTasksListColumns.IS_SMART_LIST, 0 );
      values.put( RtmTasksListColumns.FILTER, null );
      
      testData.add( new TestData< RtmTasksList >( RtmTasksList.class,
                                                  modelElement,
                                                  values,
                                                  "LockedArchivedPosition" ) );
   }
   
   
   
   private void addTasksListDeleted( Collection< TestData< RtmTasksList >> testData )
   {
      final RtmTasksList modelElement = new RtmTasksList( "1",
                                                          0,
                                                          true,
                                                          false,
                                                          false,
                                                          "TestList",
                                                          null );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmTasksListColumns.RTM_LIST_ID, "1" );
      values.put( RtmTasksListColumns.LIST_NAME, "TestList" );
      values.put( RtmTasksListColumns.LOCKED, 0 );
      values.put( RtmTasksListColumns.ARCHIVED, 0 );
      values.put( RtmTasksListColumns.POSITION, 0 );
      values.put( RtmTasksListColumns.IS_SMART_LIST, 0 );
      values.put( RtmTasksListColumns.FILTER, null );
      
      testData.add( new TestData< RtmTasksList >( RtmTasksList.class,
                                                  modelElement,
                                                  values,
                                                  "Deleted" ) );
   }
   
   
   
   private void addTasksListSmart( Collection< TestData< RtmTasksList >> testData )
   {
      final RtmTasksList modelElement = new RtmTasksList( "1",
                                                          0,
                                                          true,
                                                          false,
                                                          false,
                                                          "TestList",
                                                          "list:List" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmTasksListColumns.RTM_LIST_ID, "1" );
      values.put( RtmTasksListColumns.LIST_NAME, "TestList" );
      values.put( RtmTasksListColumns.LOCKED, 0 );
      values.put( RtmTasksListColumns.ARCHIVED, 0 );
      values.put( RtmTasksListColumns.POSITION, 0 );
      values.put( RtmTasksListColumns.IS_SMART_LIST, 1 );
      values.put( RtmTasksListColumns.FILTER, "list:List" );
      
      testData.add( new TestData< RtmTasksList >( RtmTasksList.class,
                                                  modelElement,
                                                  values,
                                                  "Smart" ) );
   }
   
   
   
   private void addTaskSeriesSimpleValues( Collection< TestData< RtmTaskSeries >> testData )
   {
      final RtmTaskSeries modelElement = new RtmTaskSeries( "1",
                                                            NOW,
                                                            LATER,
                                                            "100",
                                                            "1000",
                                                            "TestTaskSimpleValues",
                                                            "TestSource",
                                                            "http://test.de",
                                                            null,
                                                            false,
                                                            Arrays.asList( "tag1",
                                                                           "tag2" ),
                                                            null,
                                                            null );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmTaskSeriesColumns.RTM_TASKSERIES_ID, "1" );
      values.put( RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE, NOW );
      values.put( RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE, LATER );
      values.put( RtmTaskSeriesColumns.TASKSERIES_NAME, "TestTaskSimpleValues" );
      values.put( RtmTaskSeriesColumns.SOURCE, "TestSource" );
      values.put( RtmTaskSeriesColumns.URL, "http://test.de" );
      values.put( RtmTaskSeriesColumns.RECURRENCE, null );
      values.put( RtmTaskSeriesColumns.RECURRENCE_EVERY, null );
      values.put( RtmTaskSeriesColumns.RTM_LOCATION_ID, "1000" );
      values.put( RtmTaskSeriesColumns.RTM_LIST_ID, "100" );
      values.put( RtmTaskSeriesColumns.TAGS,
                  Strings.join( RtmTaskSeriesColumns.TAGS_SEPARATOR,
                                Arrays.asList( "tag1", "tag2" ) ) );
      
      testData.add( new TestData< RtmTaskSeries >( RtmTaskSeries.class,
                                                   modelElement,
                                                   values,
                                                   "SimpleValues" ) );
   }
   
   
   
   private void addTaskSeriesRecurrenceEvery( Collection< TestData< RtmTaskSeries >> testData )
   {
      final RtmTaskSeries modelElement = new RtmTaskSeries( "1",
                                                            NOW,
                                                            LATER,
                                                            "100",
                                                            RtmConstants.NO_ID,
                                                            "TestTaskRecurrEvery",
                                                            null,
                                                            null,
                                                            "FREQ=WEEKLY;INTERVAL=2",
                                                            true,
                                                            null,
                                                            null,
                                                            null );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmTaskSeriesColumns.RTM_TASKSERIES_ID, "1" );
      values.put( RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE, NOW );
      values.put( RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE, LATER );
      values.put( RtmTaskSeriesColumns.TASKSERIES_NAME, "TestTaskRecurrEvery" );
      values.put( RtmTaskSeriesColumns.SOURCE, null );
      values.put( RtmTaskSeriesColumns.URL, null );
      values.put( RtmTaskSeriesColumns.RECURRENCE, "FREQ=WEEKLY;INTERVAL=2" );
      values.put( RtmTaskSeriesColumns.RECURRENCE_EVERY, 1 );
      values.put( RtmTaskSeriesColumns.RTM_LOCATION_ID, null );
      values.put( RtmTaskSeriesColumns.RTM_LIST_ID, "100" );
      values.put( RtmTaskSeriesColumns.TAGS, null );
      
      testData.add( new TestData< RtmTaskSeries >( RtmTaskSeries.class,
                                                   modelElement,
                                                   values,
                                                   "RecurrEvery" ) );
   }
   
   
   
   private void addTaskSeriesRecurrenceAfter( Collection< TestData< RtmTaskSeries >> testData )
   {
      final RtmTaskSeries modelElement = new RtmTaskSeries( "1",
                                                            NOW,
                                                            LATER,
                                                            "100",
                                                            RtmConstants.NO_ID,
                                                            "TestTaskRecurrEvery",
                                                            null,
                                                            null,
                                                            "FREQ=WEEKLY;INTERVAL=2",
                                                            false,
                                                            null,
                                                            null,
                                                            null );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmTaskSeriesColumns.RTM_TASKSERIES_ID, "1" );
      values.put( RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE, NOW );
      values.put( RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE, LATER );
      values.put( RtmTaskSeriesColumns.TASKSERIES_NAME, "TestTaskRecurrEvery" );
      values.put( RtmTaskSeriesColumns.SOURCE, null );
      values.put( RtmTaskSeriesColumns.URL, null );
      values.put( RtmTaskSeriesColumns.RECURRENCE, "FREQ=WEEKLY;INTERVAL=2" );
      values.put( RtmTaskSeriesColumns.RECURRENCE_EVERY, 0 );
      values.put( RtmTaskSeriesColumns.RTM_LOCATION_ID, null );
      values.put( RtmTaskSeriesColumns.RTM_LIST_ID, "100" );
      values.put( RtmTaskSeriesColumns.TAGS, null );
      
      testData.add( new TestData< RtmTaskSeries >( RtmTaskSeries.class,
                                                   modelElement,
                                                   values,
                                                   "RecurrEvery" ) );
   }
   
   
   
   private void addRawTaskSimpleValues( Collection< TestData< RtmRawTask >> testData )
   {
      final RtmRawTask modelElement = new RtmRawTask( "1",
                                                      LATER,
                                                      NOW,
                                                      LATER,
                                                      Priority.None,
                                                      1,
                                                      NEVER,
                                                      false,
                                                      "1 hour" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmRawTaskColumns.RTM_RAWTASK_ID, "1" );
      values.put( RtmRawTaskColumns.DUE_DATE, null );
      values.put( RtmRawTaskColumns.ADDED_DATE, LATER );
      values.put( RtmRawTaskColumns.COMPLETED_DATE, LATER );
      values.put( RtmRawTaskColumns.DELETED_DATE, NOW );
      values.put( RtmRawTaskColumns.PRIORITY, Priority.None.toString() );
      values.put( RtmRawTaskColumns.POSTPONED, 1 );
      values.put( RtmRawTaskColumns.ESTIMATE, "1 hour" );
      
      testData.add( new TestData< RtmRawTask >( RtmRawTask.class,
                                                modelElement,
                                                values,
                                                "SimpleValues" ) );
   }
   
   
   
   private void addRawTaskDueNoTime( Collection< TestData< RtmRawTask >> testData )
   {
      final RtmRawTask modelElement = new RtmRawTask( "1",
                                                      LATER,
                                                      NEVER,
                                                      NEVER,
                                                      Priority.None,
                                                      0,
                                                      LATER,
                                                      false,
                                                      null );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmRawTaskColumns.RTM_RAWTASK_ID, "1" );
      values.put( RtmRawTaskColumns.DUE_DATE, LATER );
      values.put( RtmRawTaskColumns.HAS_DUE_TIME, 0 );
      values.put( RtmRawTaskColumns.ADDED_DATE, LATER );
      values.put( RtmRawTaskColumns.COMPLETED_DATE, null );
      values.put( RtmRawTaskColumns.DELETED_DATE, null );
      values.put( RtmRawTaskColumns.PRIORITY, Priority.None.toString() );
      values.put( RtmRawTaskColumns.POSTPONED, 0 );
      values.put( RtmRawTaskColumns.ESTIMATE, null );
      
      testData.add( new TestData< RtmRawTask >( RtmRawTask.class,
                                                modelElement,
                                                values,
                                                "DueNoTime" ) );
   }
   
   
   
   private void addRawTaskDueWithTime( Collection< TestData< RtmRawTask >> testData )
   {
      final RtmRawTask modelElement = new RtmRawTask( "1",
                                                      LATER,
                                                      NEVER,
                                                      NEVER,
                                                      Priority.None,
                                                      0,
                                                      LATER,
                                                      true,
                                                      null );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmRawTaskColumns.RTM_RAWTASK_ID, "1" );
      values.put( RtmRawTaskColumns.DUE_DATE, LATER );
      values.put( RtmRawTaskColumns.HAS_DUE_TIME, 1 );
      values.put( RtmRawTaskColumns.ADDED_DATE, LATER );
      values.put( RtmRawTaskColumns.COMPLETED_DATE, null );
      values.put( RtmRawTaskColumns.DELETED_DATE, null );
      values.put( RtmRawTaskColumns.PRIORITY, Priority.None.toString() );
      values.put( RtmRawTaskColumns.POSTPONED, 0 );
      values.put( RtmRawTaskColumns.ESTIMATE, null );
      
      testData.add( new TestData< RtmRawTask >( RtmRawTask.class,
                                                modelElement,
                                                values,
                                                "DueWithTime" ) );
   }
   
   
   
   private void addNoteFull( Collection< TestData< RtmNote >> testData )
   {
      final RtmNote modelElement = new RtmNote( "1",
                                                NOW,
                                                LATER,
                                                "RtmNote Title",
                                                "RtmNote Text" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmNoteColumns.RTM_NOTE_ID, "1" );
      values.put( RtmNoteColumns.NOTE_CREATED_DATE, NOW );
      values.put( RtmNoteColumns.NOTE_MODIFIED_DATE, LATER );
      values.put( RtmNoteColumns.NOTE_TITLE, "RtmNote Title" );
      values.put( RtmNoteColumns.NOTE_TEXT, "RtmNote Text" );
      
      testData.add( new TestData< RtmNote >( RtmNote.class,
                                             modelElement,
                                             values,
                                             "Full" ) );
   }
   
   
   
   private void addContactFull( Collection< TestData< RtmContact >> testData )
   {
      final RtmContact modelElement = new RtmContact( "1",
                                                      "Username",
                                                      "Fullname" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmContactColumns.RTM_CONTACT_ID, "1" );
      values.put( RtmContactColumns.FULLNAME, "Fullname" );
      values.put( RtmContactColumns.USERNAME, "Username" );
      
      testData.add( new TestData< RtmContact >( RtmContact.class,
                                                modelElement,
                                                values,
                                                "Full" ) );
      
   }
   
   
   
   private void addLocationNoAddr( Collection< TestData< RtmLocation >> testData )
   {
      final RtmLocation modelElement = new RtmLocation( "1",
                                                        "TestLoc",
                                                        1.0f,
                                                        2.0f,
                                                        null,
                                                        false,
                                                        10 );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmLocationColumns.RTM_LOCATION_ID, "1" );
      values.put( RtmLocationColumns.LOCATION_NAME, "TestLoc" );
      values.put( RtmLocationColumns.LONGITUDE, 1.0f );
      values.put( RtmLocationColumns.LATITUDE, 2.0f );
      values.put( RtmLocationColumns.ADDRESS, null );
      values.put( RtmLocationColumns.VIEWABLE, 0 );
      values.put( RtmLocationColumns.ZOOM, 10 );
      
      testData.add( new TestData< RtmLocation >( RtmLocation.class,
                                                 modelElement,
                                                 values,
                                                 "NoAddr" ) );
      
   }
   
   
   
   private void addLocationFull( Collection< TestData< RtmLocation >> testData )
   {
      final RtmLocation modelElement = new RtmLocation( "1",
                                                        "TestLoc",
                                                        1.0f,
                                                        2.0f,
                                                        "Address",
                                                        true,
                                                        10 );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmLocationColumns.RTM_LOCATION_ID, "1" );
      values.put( RtmLocationColumns.LOCATION_NAME, "TestLoc" );
      values.put( RtmLocationColumns.LONGITUDE, 1.0f );
      values.put( RtmLocationColumns.LATITUDE, 2.0f );
      values.put( RtmLocationColumns.ADDRESS, "Address" );
      values.put( RtmLocationColumns.VIEWABLE, 1 );
      values.put( RtmLocationColumns.ZOOM, 10 );
      
      testData.add( new TestData< RtmLocation >( RtmLocation.class,
                                                 modelElement,
                                                 values,
                                                 "Full" ) );
      
   }
   
   
   
   private void addRtmSettingsNoDefList( Collection< TestData< RtmSettings >> testData )
   {
      final RtmSettings modelElement = new RtmSettings( NOW,
                                                        "UTC",
                                                        1,
                                                        2,
                                                        RtmConstants.NO_ID,
                                                        "en" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmSettingsColumns.SYNC_TIMESTAMP, NOW );
      values.put( RtmSettingsColumns.TIMEZONE, "UTC" );
      values.put( RtmSettingsColumns.DATEFORMAT, 1 );
      values.put( RtmSettingsColumns.TIMEFORMAT, 2 );
      values.put( RtmSettingsColumns.RTM_DEFAULTLIST_ID, null );
      values.put( RtmSettingsColumns.LANGUAGE, "en" );
      
      testData.add( new TestData< RtmSettings >( RtmSettings.class,
                                                 modelElement,
                                                 values,
                                                 "NoDefList" ) );
   }
   
   
   
   private void addRtmSettingsFull( Collection< TestData< RtmSettings >> testData )
   {
      final RtmSettings modelElement = new RtmSettings( NOW,
                                                        "UTC",
                                                        1,
                                                        2,
                                                        "100",
                                                        "en" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( RtmSettingsColumns.SYNC_TIMESTAMP, NOW );
      values.put( RtmSettingsColumns.TIMEZONE, "UTC" );
      values.put( RtmSettingsColumns.DATEFORMAT, 1 );
      values.put( RtmSettingsColumns.TIMEFORMAT, 2 );
      values.put( RtmSettingsColumns.RTM_DEFAULTLIST_ID, "100" );
      values.put( RtmSettingsColumns.LANGUAGE, "en" );
      
      testData.add( new TestData< RtmSettings >( RtmSettings.class,
                                                 modelElement,
                                                 values,
                                                 "Full" ) );
   }
   
   
   public static class TestData< TModelElement >
   {
      public final Class< TModelElement > modelElementClass;
      
      public final TModelElement modelElement;
      
      public final Map< String, Object > contentValues;
      
      public final String testName;
      
      
      
      public TestData( Class< TModelElement > modelElementClass,
         TModelElement modelElement, Map< String, Object > contentValues,
         String testName )
      {
         this.modelElementClass = modelElementClass;
         this.modelElement = modelElement;
         this.contentValues = contentValues;
         this.testName = testName;
      }
      
      
      
      @Override
      public String toString()
      {
         return modelElementClass.getSimpleName() + "_" + testName;
      }
   }
}
