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

import static dev.drsoran.moloko.test.TestConstants.EVEN_LATER;
import static dev.drsoran.moloko.test.TestConstants.LATER;
import static dev.drsoran.moloko.test.TestConstants.NEVER;
import static dev.drsoran.moloko.test.TestConstants.NOW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import dev.drsoran.Pair;
import dev.drsoran.Strings;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmRawTask;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTaskSeries;
import dev.drsoran.rtm.model.RtmTasksList;


public class RtmModelElementFactoryTestDataSource
{
   
   public Collection< TestData< RtmTasksList >> getTasksListTestData()
   {
      final Collection< TestData< RtmTasksList >> testData = new LinkedList< TestData< RtmTasksList > >();
      
      addTasksListLockedArchivedPositionNotSet( testData );
      addTasksListLockedArchivedPositionSet( testData );
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
      
      addNoteMinimal( testData );
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
   
   
   
   private void addTasksListLockedArchivedPositionNotSet( Collection< TestData< RtmTasksList >> testData )
   {
      final RtmTasksList modelElement = new RtmTasksList( "1",
                                                          0,
                                                          false,
                                                          false,
                                                          false,
                                                          "TestList",
                                                          null );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, -1L );
      add( values, String.class, "TestList" );
      add( values, Long.class, -1L );
      add( values, Long.class, -1L );
      add( values, Long.class, -1L );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      add( values, String.class, "1" );
      
      testData.add( new TestData< RtmTasksList >( RtmTasksList.class,
                                                  modelElement,
                                                  values,
                                                  "LockedArchivedPositionNotSet" ) );
   }
   
   
   
   private void addTasksListLockedArchivedPositionSet( Collection< TestData< RtmTasksList >> testData )
   {
      final RtmTasksList modelElement = new RtmTasksList( "1",
                                                          -1,
                                                          false,
                                                          true,
                                                          true,
                                                          "TestList1",
                                                          null );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, -1L );
      add( values, String.class, "TestList1" );
      add( values, Long.class, -1L );
      add( values, Long.class, -1L );
      add( values, Long.class, -1L );
      add( values, Integer.class, 1 );
      add( values, Integer.class, 1 );
      add( values, Integer.class, -1 );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      add( values, String.class, "1" );
      
      testData.add( new TestData< RtmTasksList >( RtmTasksList.class,
                                                  modelElement,
                                                  values,
                                                  "LockedArchivedPositionSet" ) );
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
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, -1L );
      add( values, String.class, "TestList" );
      add( values, Long.class, -1L );
      add( values, Long.class, -1L );
      add( values, Long.class, NOW );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      add( values, String.class, "1" );
      
      testData.add( new TestData< RtmTasksList >( RtmTasksList.class,
                                                  modelElement,
                                                  values,
                                                  "Deleted" ) );
   }
   
   
   
   private void addTasksListSmart( Collection< TestData< RtmTasksList >> testData )
   {
      final RtmTasksList modelElement = new RtmTasksList( "1",
                                                          0,
                                                          false,
                                                          false,
                                                          false,
                                                          "TestList",
                                                          "list:List" );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, -1L );
      add( values, String.class, "TestList" );
      add( values, Long.class, -1L );
      add( values, Long.class, -1L );
      add( values, Long.class, -1L );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 1 );
      add( values, String.class, "list:List" );
      add( values, String.class, "1" );
      
      testData.add( new TestData< RtmTasksList >( RtmTasksList.class,
                                                  modelElement,
                                                  values,
                                                  "Smart" ) );
   }
   
   
   
   private void addTaskSeriesSimpleValues( Collection< TestData< RtmTaskSeries >> testData )
   {
      final RtmTaskSeries modelElement = new RtmTaskSeries( "1",
                                                            NOW,
                                                            EVEN_LATER,
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
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, EVEN_LATER );
      add( values, String.class, "TestTaskSimpleValues" );
      add( values, String.class, "TestSource" );
      add( values, String.class, "http://test.de" );
      add( values, String.class, null );
      add( values, Integer.class, 0 );
      add( values, Long.class, 1000L );
      add( values, Long.class, 100L );
      add( values,
           String.class,
           Strings.join( TaskColumns.TAGS_SEPARATOR,
                         Arrays.asList( "tag1", "tag2" ) ) );
      add( values, String.class, "1" );
      add( values, String.class, "100" );
      add( values, String.class, "1000" );
      
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
                                                            null,
                                                            "TestTaskRecurrEvery",
                                                            null,
                                                            null,
                                                            "FREQ=WEEKLY;INTERVAL=2",
                                                            false,
                                                            null,
                                                            null,
                                                            null );
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, LATER );
      add( values, String.class, "TestTaskRecurrEvery" );
      add( values, String.class, null );
      add( values, String.class, null );
      add( values, String.class, "FREQ=WEEKLY;INTERVAL=2" );
      add( values, Integer.class, 0 );
      add( values, Long.class, null );
      add( values, Long.class, 100L );
      add( values, String.class, null );
      add( values, String.class, "1" );
      add( values, String.class, "100" );
      add( values, String.class, null );
      
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
                                                            null,
                                                            "TestTaskRecurrEvery",
                                                            null,
                                                            null,
                                                            "FREQ=WEEKLY;INTERVAL=2",
                                                            true,
                                                            null,
                                                            null,
                                                            null );
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, LATER );
      add( values, String.class, "TestTaskRecurrEvery" );
      add( values, String.class, null );
      add( values, String.class, null );
      add( values, String.class, "FREQ=WEEKLY;INTERVAL=2" );
      add( values, Integer.class, 1 );
      add( values, Long.class, null );
      add( values, Long.class, 100L );
      add( values, String.class, null );
      add( values, String.class, "1" );
      add( values, String.class, "100" );
      add( values, String.class, null );
      
      testData.add( new TestData< RtmTaskSeries >( RtmTaskSeries.class,
                                                   modelElement,
                                                   values,
                                                   "RecurrAfter" ) );
   }
   
   
   
   private void addRawTaskSimpleValues( Collection< TestData< RtmRawTask >> testData )
   {
      final RtmRawTask modelElement = new RtmRawTask( "1",
                                                      NOW,
                                                      LATER,
                                                      EVEN_LATER,
                                                      Priority.High,
                                                      1,
                                                      NEVER,
                                                      false,
                                                      "1 hour" );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NEVER );
      add( values, Integer.class, 0 );
      add( values, Long.class, NOW );
      add( values, Long.class, EVEN_LATER );
      add( values, Long.class, LATER );
      add( values, String.class, Priority.High.toString() );
      add( values, Integer.class, 1 );
      add( values, String.class, "1 hour" );
      add( values, Long.class, -1L );
      add( values, String.class, "1" );
      
      testData.add( new TestData< RtmRawTask >( RtmRawTask.class,
                                                modelElement,
                                                values,
                                                "SimpleValues" ) );
   }
   
   
   
   private void addRawTaskDueNoTime( Collection< TestData< RtmRawTask >> testData )
   {
      final RtmRawTask modelElement = new RtmRawTask( "1",
                                                      NOW,
                                                      LATER,
                                                      NEVER,
                                                      Priority.None,
                                                      0,
                                                      EVEN_LATER,
                                                      false,
                                                      null );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, EVEN_LATER );
      add( values, Integer.class, 0 );
      add( values, Long.class, NOW );
      add( values, Long.class, NEVER );
      add( values, Long.class, LATER );
      add( values, String.class, Priority.None.toString() );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      add( values, Long.class, -1L );
      add( values, String.class, "1" );
      
      testData.add( new TestData< RtmRawTask >( RtmRawTask.class,
                                                modelElement,
                                                values,
                                                "DueNoTime" ) );
   }
   
   
   
   private void addRawTaskDueWithTime( Collection< TestData< RtmRawTask >> testData )
   {
      final RtmRawTask modelElement = new RtmRawTask( "1",
                                                      NOW,
                                                      LATER,
                                                      NEVER,
                                                      Priority.None,
                                                      0,
                                                      EVEN_LATER,
                                                      true,
                                                      null );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, EVEN_LATER );
      add( values, Integer.class, 1 );
      add( values, Long.class, NOW );
      add( values, Long.class, NEVER );
      add( values, Long.class, LATER );
      add( values, String.class, Priority.None.toString() );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      add( values, Long.class, -1L );
      add( values, String.class, "1" );
      
      testData.add( new TestData< RtmRawTask >( RtmRawTask.class,
                                                modelElement,
                                                values,
                                                "DueWithTime" ) );
   }
   
   
   
   private void addNoteMinimal( Collection< TestData< RtmNote >> testData )
   {
      final RtmNote modelElement = new RtmNote( "1",
                                                NOW,
                                                LATER,
                                                Strings.EMPTY_STRING,
                                                Strings.EMPTY_STRING );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, LATER );
      add( values, Long.class, -1L );
      add( values, String.class, Strings.EMPTY_STRING );
      add( values, String.class, Strings.EMPTY_STRING );
      add( values, Long.class, -1L );
      add( values, String.class, "1" );
      
      testData.add( new TestData< RtmNote >( RtmNote.class,
                                             modelElement,
                                             values,
                                             "Minimal" ) );
   }
   
   
   
   private void addNoteFull( Collection< TestData< RtmNote >> testData )
   {
      final RtmNote modelElement = new RtmNote( "1",
                                                NOW,
                                                LATER,
                                                "RtmNote Title",
                                                "RtmNote Text" );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, LATER );
      add( values, Long.class, EVEN_LATER );
      add( values, String.class, "RtmNote Title" );
      add( values, String.class, "RtmNote Text" );
      add( values, Long.class, -1L );
      add( values, String.class, "1" );
      
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
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "Fullname" );
      add( values, String.class, "Username" );
      add( values, String.class, "1" );
      
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
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "TestLoc" );
      add( values, Float.class, 1.0f );
      add( values, Float.class, 2.0f );
      add( values, String.class, null );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 10 );
      add( values, String.class, "1" );
      
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
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "TestLoc" );
      add( values, Float.class, 1.0f );
      add( values, Float.class, 2.0f );
      add( values, String.class, "Address" );
      add( values, Integer.class, 1 );
      add( values, Integer.class, 10 );
      add( values, String.class, "1" );
      
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
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, String.class, "UTC" );
      add( values, Integer.class, 1 );
      add( values, Integer.class, 2 );
      add( values, Long.class, -1L );
      add( values, String.class, "en" );
      add( values, String.class, null );
      
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
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, String.class, "UTC" );
      add( values, Integer.class, 1 );
      add( values, Integer.class, 2 );
      add( values, Long.class, 100L );
      add( values, String.class, "en" );
      add( values, String.class, "100" );
      
      testData.add( new TestData< RtmSettings >( RtmSettings.class,
                                                 modelElement,
                                                 values,
                                                 "Full" ) );
   }
   
   
   
   private < T > void add( List< Pair< Class< ? >, Object >> list,
                           Class< T > clazz,
                           T value )
   {
      list.add( new Pair< Class< ? >, Object >( clazz, value ) );
   }
   
   
   public static class TestData< TModelElement >
   {
      public final Class< TModelElement > modelElementClass;
      
      public final TModelElement modelElement;
      
      public final List< Pair< Class< ? >, Object > > contentValues;
      
      public final String testName;
      
      
      
      public TestData( Class< TModelElement > modelElementClass,
         TModelElement modelElement,
         List< Pair< Class< ? >, Object > > contentValues, String testName )
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
