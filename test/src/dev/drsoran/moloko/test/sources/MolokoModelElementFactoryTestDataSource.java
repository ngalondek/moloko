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
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.content.Modification;
import dev.drsoran.moloko.domain.model.Contact;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.ExtendedTaskCount;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Settings;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.sync.SyncTime;


public class MolokoModelElementFactoryTestDataSource
{
   
   public Collection< TestData< TasksList >> getTasksListTestData()
   {
      final Collection< TestData< TasksList >> testData = new LinkedList< TestData< TasksList > >();
      
      addTasksListLockedArchivedPositionNotSet( testData );
      addTasksListLockedArchivedPositionSet( testData );
      addTasksListDeleted( testData );
      addTasksListSmart( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Task >> getTasksTestData()
   {
      final Collection< TestData< Task >> testData = new LinkedList< TestData< Task > >();
      
      addTaskMinimal( testData );
      addTaskSimpleValues( testData );
      addTaskRecurrenceAfter( testData );
      addTaskRecurrenceEvery( testData );
      addTaskDueNoTime( testData );
      addTaskDueWithTime( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Note >> getNotesTestData()
   {
      final Collection< TestData< Note >> testData = new LinkedList< TestData< Note > >();
      
      addNoteMinimal( testData );
      addNoteFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Participant >> getParticipantsTestData()
   {
      final Collection< TestData< Participant >> testData = new LinkedList< TestData< Participant > >();
      
      addParticipantFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Contact >> getContactsTestData()
   {
      final Collection< TestData< Contact >> testData = new LinkedList< TestData< Contact > >();
      
      addContactFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Location >> getLocationsTestData()
   {
      final Collection< TestData< Location >> testData = new LinkedList< TestData< Location > >();
      
      addLocationNoAddr( testData );
      addLocationFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Modification >> getModificationsTestData()
   {
      final Collection< TestData< Modification >> testData = new LinkedList< TestData< Modification > >();
      
      addModificationNoSyncedVal( testData );
      addModificationNewValNull( testData );
      addModificationSyncedValNull( testData );
      addModificationFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Settings >> getRtmSettingsTestData()
   {
      final Collection< TestData< Settings >> testData = new LinkedList< TestData< Settings > >();
      
      addRtmSettingsNoDefList( testData );
      addRtmSettingsFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< SyncTime >> getSyncTestData()
   {
      final Collection< TestData< SyncTime >> testData = new LinkedList< TestData< SyncTime > >();
      
      addSyncNever( testData );
      addSyncFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< ExtendedTaskCount >> getExtendedTaskCountTestData()
   {
      final Collection< TestData< ExtendedTaskCount >> testData = new LinkedList< TestData< ExtendedTaskCount > >();
      
      addExtendedTaskCountFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< String >> getTagsTestData()
   {
      final Collection< TestData< String >> testData = new LinkedList< TestData< String > >();
      
      addTagsFull( testData );
      
      return testData;
   }
   
   
   
   private void addTasksListLockedArchivedPositionNotSet( Collection< TestData< TasksList >> testData )
   {
      final TasksList modelElement = new TasksList( 1L,
                                                    NOW,
                                                    "TestList",
                                                    0,
                                                    false,
                                                    false );
      modelElement.setModifiedMillisUtc( LATER );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "TestList" );
      add( values, Long.class, NOW );
      add( values, Long.class, LATER );
      add( values, Long.class, NEVER );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      
      testData.add( new TestData< TasksList >( TasksList.class,
                                               modelElement,
                                               values,
                                               "LockedArchivedPositionNotSet" ) );
      
   }
   
   
   
   private void addTasksListLockedArchivedPositionSet( Collection< TestData< TasksList >> testData )
   {
      final TasksList modelElement = new TasksList( 1L,
                                                    NOW,
                                                    "TestList1",
                                                    -1,
                                                    true,
                                                    true );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "TestList1" );
      add( values, Long.class, NOW );
      add( values, Long.class, NEVER );
      add( values, Long.class, NEVER );
      add( values, Integer.class, 1 );
      add( values, Integer.class, 1 );
      add( values, Integer.class, -1 );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      
      testData.add( new TestData< TasksList >( TasksList.class,
                                               modelElement,
                                               values,
                                               "LockedArchivedPositionSet" ) );
      
   }
   
   
   
   private void addTasksListDeleted( Collection< TestData< TasksList >> testData )
   {
      final TasksList modelElement = new TasksList( 1L,
                                                    NOW,
                                                    "TestList",
                                                    0,
                                                    false,
                                                    false );
      modelElement.setModifiedMillisUtc( LATER );
      modelElement.setDeletedMillisUtc( EVEN_LATER );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "TestList" );
      add( values, Long.class, NOW );
      add( values, Long.class, LATER );
      add( values, Long.class, EVEN_LATER );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      
      testData.add( new TestData< TasksList >( TasksList.class,
                                               modelElement,
                                               values,
                                               "Deleted" ) );
      
   }
   
   
   
   private void addTasksListSmart( Collection< TestData< TasksList >> testData )
   {
      final TasksList modelElement = new TasksList( 1L,
                                                    NOW,
                                                    "TestList",
                                                    0,
                                                    false,
                                                    false );
      modelElement.setModifiedMillisUtc( LATER );
      modelElement.setSmartFilter( new RtmSmartFilter( "list:List" ) );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "TestList" );
      add( values, Long.class, NOW );
      add( values, Long.class, LATER );
      add( values, Long.class, NEVER );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 0 );
      add( values, Integer.class, 1 );
      add( values, String.class, "list:List" );
      
      testData.add( new TestData< TasksList >( TasksList.class,
                                               modelElement,
                                               values,
                                               "Smart" ) );
      
   }
   
   
   
   private void addTaskMinimal( Collection< TestData< Task >> testData )
   {
      final Task modelElement = new Task( 1L,
                                          NOW,
                                          LATER,
                                          "TestTask",
                                          100L,
                                          "TestList" );
      
      modelElement.setModifiedMillisUtc( EVEN_LATER );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, EVEN_LATER );
      add( values, String.class, "TestTask" );
      add( values, String.class, null );
      add( values, String.class, null );
      add( values, String.class, null );
      add( values, Integer.class, 0 );
      add( values, Long.class, null );
      add( values, String.class, null );
      add( values, Long.class, 100L );
      add( values, String.class, "TestList" );
      add( values, String.class, null );
      add( values, Long.class, null );
      add( values, Integer.class, 0 );
      add( values, Long.class, LATER );
      add( values, Long.class, null );
      add( values, Long.class, null );
      add( values, String.class, Priority.None.toString() );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      add( values, Long.class, -1L );
      
      testData.add( new TestData< Task >( Task.class,
                                          modelElement,
                                          values,
                                          "Minimal" ) );
   }
   
   
   
   private void addTaskSimpleValues( Collection< TestData< Task >> testData )
   {
      final Task modelElement = new Task( 1L,
                                          NOW,
                                          LATER,
                                          "TestTaskSimpleValues",
                                          100L,
                                          "TestList" );
      
      modelElement.setModifiedMillisUtc( EVEN_LATER );
      modelElement.setSource( "TestSource" );
      modelElement.setUrl( "http://test.de" );
      modelElement.setLocationStub( 1000L, "TestLoc" );
      modelElement.setTags( Arrays.asList( "tag1", "tag2" ) );
      modelElement.setCompletedMillisUtc( LATER );
      modelElement.setDeletedMillisUtc( EVEN_LATER );
      modelElement.setPriority( Priority.High );
      modelElement.setPostponedCount( 1 );
      modelElement.setEstimation( new Estimation( "1 hour", 3600000L ) );
      
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
      add( values, String.class, "TestLoc" );
      add( values, Long.class, 100L );
      add( values, String.class, "TestList" );
      add( values,
           String.class,
           Strings.join( TaskColumns.TAGS_SEPARATOR,
                         Arrays.asList( "tag1", "tag2" ) ) );
      add( values, Long.class, null );
      add( values, Integer.class, 0 );
      add( values, Long.class, LATER );
      add( values, Long.class, LATER );
      add( values, Long.class, EVEN_LATER );
      add( values, String.class, Priority.High.toString() );
      add( values, Integer.class, 1 );
      add( values, String.class, "1 hour" );
      add( values, Long.class, 3600000L );
      
      testData.add( new TestData< Task >( Task.class,
                                          modelElement,
                                          values,
                                          "SimpleValues" ) );
   }
   
   
   
   private void addTaskRecurrenceEvery( Collection< TestData< Task >> testData )
   {
      final Task modelElement = new Task( 1L,
                                          NOW,
                                          LATER,
                                          "TestTaskRecurrEvery",
                                          100L,
                                          "TestList" );
      
      modelElement.setModifiedMillisUtc( EVEN_LATER );
      modelElement.setRecurrence( new Recurrence( "FREQ=WEEKLY;INTERVAL=2",
                                                  true ) );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, EVEN_LATER );
      add( values, String.class, "TestTaskRecurrEvery" );
      add( values, String.class, null );
      add( values, String.class, null );
      add( values, String.class, "FREQ=WEEKLY;INTERVAL=2" );
      add( values, Integer.class, 1 );
      add( values, Long.class, null );
      add( values, String.class, null );
      add( values, Long.class, 100L );
      add( values, String.class, "TestList" );
      add( values, String.class, null );
      add( values, Long.class, null );
      add( values, Integer.class, 0 );
      add( values, Long.class, LATER );
      add( values, Long.class, null );
      add( values, Long.class, null );
      add( values, String.class, Priority.None.toString() );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      add( values, Long.class, -1L );
      
      testData.add( new TestData< Task >( Task.class,
                                          modelElement,
                                          values,
                                          "RecurrEvery" ) );
   }
   
   
   
   private void addTaskRecurrenceAfter( Collection< TestData< Task >> testData )
   {
      final Task modelElement = new Task( 1L,
                                          NOW,
                                          LATER,
                                          "TestTaskRecurrAfter",
                                          100L,
                                          "TestList" );
      
      modelElement.setModifiedMillisUtc( EVEN_LATER );
      modelElement.setRecurrence( new Recurrence( "FREQ=WEEKLY;INTERVAL=2",
                                                  false ) );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, EVEN_LATER );
      add( values, String.class, "TestTaskRecurrAfter" );
      add( values, String.class, null );
      add( values, String.class, null );
      add( values, String.class, "FREQ=WEEKLY;INTERVAL=2" );
      add( values, Integer.class, 0 );
      add( values, Long.class, null );
      add( values, String.class, null );
      add( values, Long.class, 100L );
      add( values, String.class, "TestList" );
      add( values, String.class, null );
      add( values, Long.class, null );
      add( values, Integer.class, 0 );
      add( values, Long.class, LATER );
      add( values, Long.class, null );
      add( values, Long.class, null );
      add( values, String.class, Priority.None.toString() );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      add( values, Long.class, -1L );
      
      testData.add( new TestData< Task >( Task.class,
                                          modelElement,
                                          values,
                                          "RecurrAfter" ) );
   }
   
   
   
   private void addTaskDueNoTime( Collection< TestData< Task >> testData )
   {
      final Task modelElement = new Task( 1L,
                                          NOW,
                                          LATER,
                                          "TestTaskDueNoTime",
                                          100L,
                                          "TestList" );
      
      modelElement.setModifiedMillisUtc( EVEN_LATER );
      modelElement.setDue( new Due( LATER, false ) );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, EVEN_LATER );
      add( values, String.class, "TestTaskDueNoTime" );
      add( values, String.class, null );
      add( values, String.class, null );
      add( values, String.class, null );
      add( values, Integer.class, 0 );
      add( values, Long.class, null );
      add( values, String.class, null );
      add( values, Long.class, 100L );
      add( values, String.class, "TestList" );
      add( values, String.class, null );
      add( values, Long.class, LATER );
      add( values, Integer.class, 0 );
      add( values, Long.class, LATER );
      add( values, Long.class, null );
      add( values, Long.class, null );
      add( values, String.class, Priority.None.toString() );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      add( values, Long.class, -1L );
      
      testData.add( new TestData< Task >( Task.class,
                                          modelElement,
                                          values,
                                          "DueNoTime" ) );
   }
   
   
   
   private void addTaskDueWithTime( Collection< TestData< Task >> testData )
   {
      final Task modelElement = new Task( 1L,
                                          NOW,
                                          LATER,
                                          "TestTaskDueWithTime",
                                          100L,
                                          "TestList" );
      
      modelElement.setModifiedMillisUtc( EVEN_LATER );
      modelElement.setDue( new Due( LATER, true ) );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, EVEN_LATER );
      add( values, String.class, "TestTaskDueWithTime" );
      add( values, String.class, null );
      add( values, String.class, null );
      add( values, String.class, null );
      add( values, Integer.class, 0 );
      add( values, Long.class, null );
      add( values, String.class, null );
      add( values, Long.class, 100L );
      add( values, String.class, "TestList" );
      add( values, String.class, null );
      add( values, Long.class, LATER );
      add( values, Integer.class, 1 );
      add( values, Long.class, LATER );
      add( values, Long.class, null );
      add( values, Long.class, null );
      add( values, String.class, Priority.None.toString() );
      add( values, Integer.class, 0 );
      add( values, String.class, null );
      add( values, Long.class, -1L );
      
      testData.add( new TestData< Task >( Task.class,
                                          modelElement,
                                          values,
                                          "DueWithTime" ) );
   }
   
   
   
   private void addNoteMinimal( Collection< TestData< Note >> testData )
   {
      final Note modelElement = new Note( 1L, NOW );
      
      modelElement.setModifiedMillisUtc( LATER );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, LATER );
      add( values, Long.class, -1L );
      add( values, String.class, null );
      add( values, String.class, Strings.EMPTY_STRING );
      
      testData.add( new TestData< Note >( Note.class,
                                          modelElement,
                                          values,
                                          "Minimal" ) );
      
   }
   
   
   
   private void addNoteFull( Collection< TestData< Note >> testData )
   {
      final Note modelElement = new Note( 1L, NOW );
      
      modelElement.setModifiedMillisUtc( LATER );
      modelElement.setTitle( "Note Title" );
      modelElement.setText( "Note Text" );
      modelElement.setDeletedMillisUtc( EVEN_LATER );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, LATER );
      add( values, Long.class, EVEN_LATER );
      add( values, String.class, "Note Title" );
      add( values, String.class, "Note Text" );
      
      testData.add( new TestData< Note >( Note.class,
                                          modelElement,
                                          values,
                                          "Full" ) );
      
   }
   
   
   
   private void addParticipantFull( Collection< TestData< Participant >> testData )
   {
      final Participant modelElement = new Participant( 1L,
                                                        100L,
                                                        "Fullname",
                                                        "Username" );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, 100L );
      add( values, String.class, "Fullname" );
      add( values, String.class, "Username" );
      
      testData.add( new TestData< Participant >( Participant.class,
                                                 modelElement,
                                                 values,
                                                 "Full" ) );
      
   }
   
   
   
   private void addContactFull( Collection< TestData< Contact >> testData )
   {
      final Contact modelElement = new Contact( 1L, "Username", "Fullname", 10 );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "Fullname" );
      add( values, String.class, "Username" );
      add( values, Integer.class, 10 );
      
      testData.add( new TestData< Contact >( Contact.class,
                                             modelElement,
                                             values,
                                             "Full" ) );
      
   }
   
   
   
   private void addLocationNoAddr( Collection< TestData< Location >> testData )
   {
      final Location modelElement = new Location( 1L,
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
      
      testData.add( new TestData< Location >( Location.class,
                                              modelElement,
                                              values,
                                              "NoAddr" ) );
      
   }
   
   
   
   private void addLocationFull( Collection< TestData< Location >> testData )
   {
      final Location modelElement = new Location( 1L,
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
      
      testData.add( new TestData< Location >( Location.class,
                                              modelElement,
                                              values,
                                              "Full" ) );
      
   }
   
   
   
   private void addModificationNoSyncedVal( Collection< TestData< Modification >> testData )
   {
      final Modification modelElement = Modification.newModification( "content://element/1",
                                                                      "testCol",
                                                                      "1",
                                                                      null );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "content://element/1" );
      add( values, String.class, "testCol" );
      add( values, String.class, "1" );
      add( values, String.class, null );
      add( values, Long.class, modelElement.getTimestamp() );
      
      testData.add( new TestData< Modification >( Modification.class,
                                                  modelElement,
                                                  values,
                                                  "NoSyncedVal" ) );
      
   }
   
   
   
   private void addModificationNewValNull( Collection< TestData< Modification >> testData )
   {
      final Modification modelElement = Modification.newModification( "content://element/1",
                                                                      "testCol",
                                                                      null,
                                                                      "0" );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "content://element/1" );
      add( values, String.class, "testCol" );
      add( values, String.class, null );
      add( values, String.class, "0" );
      add( values, Long.class, modelElement.getTimestamp() );
      
      testData.add( new TestData< Modification >( Modification.class,
                                                  modelElement,
                                                  values,
                                                  "NewValNull" ) );
      
   }
   
   
   
   private void addModificationSyncedValNull( Collection< TestData< Modification >> testData )
   {
      final Modification modelElement = Modification.newModification( "content://element/1",
                                                                      "testCol",
                                                                      "1",
                                                                      null );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "content://element/1" );
      add( values, String.class, "testCol" );
      add( values, String.class, "1" );
      add( values, String.class, null );
      add( values, Long.class, modelElement.getTimestamp() );
      
      testData.add( new TestData< Modification >( Modification.class,
                                                  modelElement,
                                                  values,
                                                  "SyncedValNull" ) );
      
   }
   
   
   
   private void addModificationFull( Collection< TestData< Modification >> testData )
   {
      final Modification modelElement = Modification.newModification( "content://element/1",
                                                                      "testCol",
                                                                      "1",
                                                                      "0",
                                                                      NOW );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, String.class, "content://element/1" );
      add( values, String.class, "testCol" );
      add( values, String.class, "1" );
      add( values, String.class, "0" );
      add( values, Long.class, modelElement.getTimestamp() );
      
      testData.add( new TestData< Modification >( Modification.class,
                                                  modelElement,
                                                  values,
                                                  "Full" ) );
      
   }
   
   
   
   private void addRtmSettingsNoDefList( Collection< TestData< Settings >> testData )
   {
      final Settings modelElement = new Settings( NOW,
                                                  "UTC",
                                                  1,
                                                  2,
                                                  Constants.NO_ID,
                                                  "en" );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, String.class, "UTC" );
      add( values, Integer.class, 1 );
      add( values, Integer.class, 2 );
      add( values, Long.class, null );
      add( values, String.class, "en" );
      
      testData.add( new TestData< Settings >( Settings.class,
                                              modelElement,
                                              values,
                                              "NoDefList" ) );
   }
   
   
   
   private void addRtmSettingsFull( Collection< TestData< Settings >> testData )
   {
      final Settings modelElement = new Settings( NOW, "UTC", 1, 2, 100L, "en" );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, String.class, "UTC" );
      add( values, Integer.class, 1 );
      add( values, Integer.class, 2 );
      add( values, Long.class, 100L );
      add( values, String.class, "en" );
      
      testData.add( new TestData< Settings >( Settings.class,
                                              modelElement,
                                              values,
                                              "Full" ) );
   }
   
   
   
   private void addSyncNever( Collection< TestData< SyncTime >> testData )
   {
      final SyncTime modelElement = new SyncTime( NEVER, NEVER );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, null );
      add( values, Long.class, null );
      
      testData.add( new TestData< SyncTime >( SyncTime.class,
                                              modelElement,
                                              values,
                                              "Never" ) );
   }
   
   
   
   private void addSyncFull( Collection< TestData< SyncTime >> testData )
   {
      final SyncTime modelElement = new SyncTime( NOW, LATER );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Long.class, 1L );
      add( values, Long.class, NOW );
      add( values, Long.class, LATER );
      
      testData.add( new TestData< SyncTime >( SyncTime.class,
                                              modelElement,
                                              values,
                                              "Full" ) );
   }
   
   
   
   private void addExtendedTaskCountFull( Collection< TestData< ExtendedTaskCount >> testData )
   {
      final ExtendedTaskCount modelElement = new ExtendedTaskCount( 0,
                                                                    1,
                                                                    2,
                                                                    3,
                                                                    4,
                                                                    3600000L );
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, Integer.class, 0 );
      add( values, Integer.class, 1 );
      add( values, Integer.class, 2 );
      add( values, Integer.class, 3 );
      add( values, Integer.class, 4 );
      add( values, Long.class, 3600000L );
      
      testData.add( new TestData< ExtendedTaskCount >( ExtendedTaskCount.class,
                                                       modelElement,
                                                       values,
                                                       "Full" ) );
   }
   
   
   
   private void addTagsFull( Collection< TestData< String >> testData )
   {
      final String modelElement = "tag";
      
      final List< Pair< Class< ? >, Object > > values = new ArrayList< Pair< Class< ? >, Object > >();
      add( values, String.class, "tag" );
      
      testData.add( new TestData< String >( String.class,
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
