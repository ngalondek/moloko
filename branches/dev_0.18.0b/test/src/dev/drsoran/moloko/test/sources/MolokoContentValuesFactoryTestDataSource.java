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

import static dev.drsoran.rtm.test.TestConstants.EVEN_LATER;
import static dev.drsoran.rtm.test.TestConstants.LATER;
import static dev.drsoran.rtm.test.TestConstants.NEVER;
import static dev.drsoran.rtm.test.TestConstants.NOW;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import dev.drsoran.moloko.content.Columns.ContactColumns;
import dev.drsoran.moloko.content.Columns.LocationColumns;
import dev.drsoran.moloko.content.Columns.NoteColumns;
import dev.drsoran.moloko.content.Columns.ParticipantColumns;
import dev.drsoran.moloko.content.Columns.SettingsColumns;
import dev.drsoran.moloko.content.Columns.SyncTimesColumns;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.db.TableColumns.ModificationColumns;
import dev.drsoran.moloko.domain.content.Modification;
import dev.drsoran.moloko.domain.model.Contact;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Settings;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.sync.SyncTime;


public class MolokoContentValuesFactoryTestDataSource
{
   
   public Collection< TestData< TasksList >> getTasksListTestData()
   {
      final Collection< TestData< TasksList >> testData = new LinkedList< TestData< TasksList > >();
      
      addTasksListNoId( testData );
      addTasksListLockedArchivedPosition( testData );
      addTasksListDeleted( testData );
      addTasksListSmart( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Task >> getTasksTestData()
   {
      final Collection< TestData< Task >> testData = new LinkedList< TestData< Task > >();
      
      addTaskNoId( testData );
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
      
      addNoteNoId( testData );
      addNoteFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Participant >> getParticipantsTestData()
   {
      final Collection< TestData< Participant >> testData = new LinkedList< TestData< Participant > >();
      
      addParticipantNoId( testData );
      addParticipantFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Contact >> getContactsTestData()
   {
      final Collection< TestData< Contact >> testData = new LinkedList< TestData< Contact > >();
      
      addContactNoId( testData );
      addContactFull( testData );
      
      return testData;
   }
   
   
   
   public Collection< TestData< Location >> getLocationsTestData()
   {
      final Collection< TestData< Location >> testData = new LinkedList< TestData< Location > >();
      
      addLocationNoId( testData );
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
   
   
   
   private void addTasksListNoId( Collection< TestData< TasksList >> testData )
   {
      final TasksList modelElement = new TasksList( Constants.NO_ID,
                                                    NOW,
                                                    "TestList",
                                                    0,
                                                    false,
                                                    false );
      modelElement.setModifiedMillisUtc( LATER );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( TasksListColumns.LIST_NAME, "TestList" );
      values.put( TasksListColumns.LIST_CREATED_DATE, NOW );
      values.put( TasksListColumns.LIST_MODIFIED_DATE, LATER );
      values.put( TasksListColumns.LIST_DELETED_DATE, null );
      values.put( TasksListColumns.LOCKED, 0 );
      values.put( TasksListColumns.ARCHIVED, 0 );
      values.put( TasksListColumns.POSITION, 0 );
      values.put( TasksListColumns.IS_SMART_LIST, 0 );
      values.put( TasksListColumns.FILTER, null );
      
      testData.add( new TestData< TasksList >( TasksList.class,
                                               modelElement,
                                               values,
                                               "NoId" ) );
      
   }
   
   
   
   private void addTasksListLockedArchivedPosition( Collection< TestData< TasksList >> testData )
   {
      final TasksList modelElement = new TasksList( 1L,
                                                    NOW,
                                                    "TestList1",
                                                    -1,
                                                    true,
                                                    true );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( TasksListColumns._ID, 1L );
      values.put( TasksListColumns.LIST_NAME, "TestList1" );
      values.put( TasksListColumns.LIST_CREATED_DATE, NOW );
      values.put( TasksListColumns.LIST_MODIFIED_DATE, NEVER );
      values.put( TasksListColumns.LIST_DELETED_DATE, null );
      values.put( TasksListColumns.LOCKED, 1 );
      values.put( TasksListColumns.ARCHIVED, 1 );
      values.put( TasksListColumns.POSITION, -1 );
      values.put( TasksListColumns.IS_SMART_LIST, 0 );
      values.put( TasksListColumns.FILTER, null );
      
      testData.add( new TestData< TasksList >( TasksList.class,
                                               modelElement,
                                               values,
                                               "LockedArchivedPosition" ) );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( TasksListColumns._ID, 1L );
      values.put( TasksListColumns.LIST_NAME, "TestList" );
      values.put( TasksListColumns.LIST_CREATED_DATE, NOW );
      values.put( TasksListColumns.LIST_MODIFIED_DATE, LATER );
      values.put( TasksListColumns.LIST_DELETED_DATE, EVEN_LATER );
      values.put( TasksListColumns.LOCKED, 0 );
      values.put( TasksListColumns.ARCHIVED, 0 );
      values.put( TasksListColumns.POSITION, 0 );
      values.put( TasksListColumns.IS_SMART_LIST, 0 );
      values.put( TasksListColumns.FILTER, null );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( TasksListColumns._ID, 1L );
      values.put( TasksListColumns.LIST_NAME, "TestList" );
      values.put( TasksListColumns.LIST_CREATED_DATE, NOW );
      values.put( TasksListColumns.LIST_MODIFIED_DATE, LATER );
      values.put( TasksListColumns.LIST_DELETED_DATE, null );
      values.put( TasksListColumns.LOCKED, 0 );
      values.put( TasksListColumns.ARCHIVED, 0 );
      values.put( TasksListColumns.POSITION, 0 );
      values.put( TasksListColumns.IS_SMART_LIST, 1 );
      values.put( TasksListColumns.FILTER, "list:List" );
      
      testData.add( new TestData< TasksList >( TasksList.class,
                                               modelElement,
                                               values,
                                               "Smart" ) );
      
   }
   
   
   
   private void addTaskNoId( Collection< TestData< Task >> testData )
   {
      final Task modelElement = new Task( Constants.NO_ID,
                                          NOW,
                                          LATER,
                                          "TestTask",
                                          100L,
                                          "TestList" );
      
      modelElement.setModifiedMillisUtc( EVEN_LATER );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( TaskColumns.TASK_CREATED_DATE, NOW );
      values.put( TaskColumns.TASK_MODIFIED_DATE, EVEN_LATER );
      values.put( TaskColumns.TASK_NAME, "TestTask" );
      values.put( TaskColumns.SOURCE, null );
      values.put( TaskColumns.URL, null );
      values.put( TaskColumns.RECURRENCE, null );
      values.put( TaskColumns.RECURRENCE_EVERY, null );
      values.put( TaskColumns.LOCATION_ID, null );
      values.put( TaskColumns.LOCATION_NAME, null );
      values.put( TaskColumns.LIST_ID, 100L );
      values.put( TaskColumns.LIST_NAME, "TestList" );
      values.put( TaskColumns.TAGS, null );
      values.put( TaskColumns.DUE_DATE, null );
      values.put( TaskColumns.ADDED_DATE, LATER );
      values.put( TaskColumns.COMPLETED_DATE, null );
      values.put( TaskColumns.DELETED_DATE, null );
      values.put( TaskColumns.PRIORITY, Priority.None.toString() );
      values.put( TaskColumns.POSTPONED, 0 );
      values.put( TaskColumns.ESTIMATE, null );
      
      testData.add( new TestData< Task >( Task.class,
                                          modelElement,
                                          values,
                                          "NoId" ) );
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
      modelElement.setPostponedCount( 1 );
      modelElement.setEstimation( new Estimation( "1 hour", 3600000L ) );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( TaskColumns._ID, 1L );
      values.put( TaskColumns.TASK_CREATED_DATE, NOW );
      values.put( TaskColumns.TASK_MODIFIED_DATE, EVEN_LATER );
      values.put( TaskColumns.TASK_NAME, "TestTaskSimpleValues" );
      values.put( TaskColumns.SOURCE, "TestSource" );
      values.put( TaskColumns.URL, "http://test.de" );
      values.put( TaskColumns.RECURRENCE, null );
      values.put( TaskColumns.RECURRENCE_EVERY, null );
      values.put( TaskColumns.LOCATION_ID, 1000L );
      values.put( TaskColumns.LOCATION_NAME, "TestLoc" );
      values.put( TaskColumns.LIST_ID, 100L );
      values.put( TaskColumns.LIST_NAME, "TestList" );
      values.put( TaskColumns.TAGS,
                  Strings.join( TaskColumns.TAGS_SEPARATOR,
                                Arrays.asList( "tag1", "tag2" ) ) );
      values.put( TaskColumns.DUE_DATE, null );
      values.put( TaskColumns.ADDED_DATE, LATER );
      values.put( TaskColumns.COMPLETED_DATE, LATER );
      values.put( TaskColumns.DELETED_DATE, EVEN_LATER );
      values.put( TaskColumns.PRIORITY, Priority.None.toString() );
      values.put( TaskColumns.POSTPONED, 1 );
      values.put( TaskColumns.ESTIMATE, "1 hour" );
      values.put( TaskColumns.ESTIMATE_MILLIS, 3600000L );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( TaskColumns._ID, 1L );
      values.put( TaskColumns.TASK_CREATED_DATE, NOW );
      values.put( TaskColumns.TASK_MODIFIED_DATE, EVEN_LATER );
      values.put( TaskColumns.TASK_NAME, "TestTaskRecurrEvery" );
      values.put( TaskColumns.SOURCE, null );
      values.put( TaskColumns.URL, null );
      values.put( TaskColumns.RECURRENCE, "FREQ=WEEKLY;INTERVAL=2" );
      values.put( TaskColumns.RECURRENCE_EVERY, 1 );
      values.put( TaskColumns.LOCATION_ID, null );
      values.put( TaskColumns.LOCATION_NAME, null );
      values.put( TaskColumns.LIST_ID, 100L );
      values.put( TaskColumns.LIST_NAME, "TestList" );
      values.put( TaskColumns.TAGS, null );
      values.put( TaskColumns.DUE_DATE, null );
      values.put( TaskColumns.ADDED_DATE, LATER );
      values.put( TaskColumns.COMPLETED_DATE, null );
      values.put( TaskColumns.DELETED_DATE, null );
      values.put( TaskColumns.PRIORITY, Priority.None.toString() );
      values.put( TaskColumns.POSTPONED, 0 );
      values.put( TaskColumns.ESTIMATE, null );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( TaskColumns._ID, 1L );
      values.put( TaskColumns.TASK_CREATED_DATE, NOW );
      values.put( TaskColumns.TASK_MODIFIED_DATE, EVEN_LATER );
      values.put( TaskColumns.TASK_NAME, "TestTaskRecurrAfter" );
      values.put( TaskColumns.SOURCE, null );
      values.put( TaskColumns.URL, null );
      values.put( TaskColumns.RECURRENCE, "FREQ=WEEKLY;INTERVAL=2" );
      values.put( TaskColumns.RECURRENCE_EVERY, 0 );
      values.put( TaskColumns.LOCATION_ID, null );
      values.put( TaskColumns.LOCATION_NAME, null );
      values.put( TaskColumns.LIST_ID, 100L );
      values.put( TaskColumns.LIST_NAME, "TestList" );
      values.put( TaskColumns.TAGS, null );
      values.put( TaskColumns.DUE_DATE, null );
      values.put( TaskColumns.ADDED_DATE, LATER );
      values.put( TaskColumns.COMPLETED_DATE, null );
      values.put( TaskColumns.DELETED_DATE, null );
      values.put( TaskColumns.PRIORITY, Priority.None.toString() );
      values.put( TaskColumns.POSTPONED, 0 );
      values.put( TaskColumns.ESTIMATE, null );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( TaskColumns._ID, 1L );
      values.put( TaskColumns.TASK_CREATED_DATE, NOW );
      values.put( TaskColumns.TASK_MODIFIED_DATE, EVEN_LATER );
      values.put( TaskColumns.TASK_NAME, "TestTaskDueNoTime" );
      values.put( TaskColumns.SOURCE, null );
      values.put( TaskColumns.URL, null );
      values.put( TaskColumns.RECURRENCE, null );
      values.put( TaskColumns.RECURRENCE_EVERY, null );
      values.put( TaskColumns.LOCATION_ID, null );
      values.put( TaskColumns.LOCATION_NAME, null );
      values.put( TaskColumns.LIST_ID, 100L );
      values.put( TaskColumns.LIST_NAME, "TestList" );
      values.put( TaskColumns.TAGS, null );
      values.put( TaskColumns.DUE_DATE, LATER );
      values.put( TaskColumns.HAS_DUE_TIME, 0 );
      values.put( TaskColumns.ADDED_DATE, LATER );
      values.put( TaskColumns.COMPLETED_DATE, null );
      values.put( TaskColumns.DELETED_DATE, null );
      values.put( TaskColumns.PRIORITY, Priority.None.toString() );
      values.put( TaskColumns.POSTPONED, 0 );
      values.put( TaskColumns.ESTIMATE, null );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( TaskColumns._ID, 1L );
      values.put( TaskColumns.TASK_CREATED_DATE, NOW );
      values.put( TaskColumns.TASK_MODIFIED_DATE, EVEN_LATER );
      values.put( TaskColumns.TASK_NAME, "TestTaskDueWithTime" );
      values.put( TaskColumns.SOURCE, null );
      values.put( TaskColumns.URL, null );
      values.put( TaskColumns.RECURRENCE, null );
      values.put( TaskColumns.RECURRENCE_EVERY, null );
      values.put( TaskColumns.LOCATION_ID, null );
      values.put( TaskColumns.LOCATION_NAME, null );
      values.put( TaskColumns.LIST_ID, 100L );
      values.put( TaskColumns.LIST_NAME, "TestList" );
      values.put( TaskColumns.TAGS, null );
      values.put( TaskColumns.DUE_DATE, LATER );
      values.put( TaskColumns.HAS_DUE_TIME, 1 );
      values.put( TaskColumns.ADDED_DATE, LATER );
      values.put( TaskColumns.COMPLETED_DATE, null );
      values.put( TaskColumns.DELETED_DATE, null );
      values.put( TaskColumns.PRIORITY, Priority.None.toString() );
      values.put( TaskColumns.POSTPONED, 0 );
      values.put( TaskColumns.ESTIMATE, null );
      
      testData.add( new TestData< Task >( Task.class,
                                          modelElement,
                                          values,
                                          "DueWithTime" ) );
   }
   
   
   
   private void addNoteNoId( Collection< TestData< Note >> testData )
   {
      final Note modelElement = new Note( Constants.NO_ID, NOW );
      
      modelElement.setModifiedMillisUtc( LATER );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( NoteColumns.NOTE_CREATED_DATE, NOW );
      values.put( NoteColumns.NOTE_MODIFIED_DATE, LATER );
      values.put( NoteColumns.NOTE_DELETED_DATE, null );
      values.put( NoteColumns.NOTE_TITLE, null );
      values.put( NoteColumns.NOTE_TEXT, Strings.EMPTY_STRING );
      
      testData.add( new TestData< Note >( Note.class,
                                          modelElement,
                                          values,
                                          "NoId" ) );
      
   }
   
   
   
   private void addNoteFull( Collection< TestData< Note >> testData )
   {
      final Note modelElement = new Note( 1L, NOW );
      
      modelElement.setModifiedMillisUtc( LATER );
      modelElement.setTitle( "Note Title" );
      modelElement.setText( "Note Text" );
      modelElement.setDeletedMillisUtc( EVEN_LATER );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( NoteColumns._ID, 1L );
      values.put( NoteColumns.NOTE_CREATED_DATE, NOW );
      values.put( NoteColumns.NOTE_MODIFIED_DATE, LATER );
      values.put( NoteColumns.NOTE_DELETED_DATE, EVEN_LATER );
      values.put( NoteColumns.NOTE_TITLE, "Note Title" );
      values.put( NoteColumns.NOTE_TEXT, "Note Text" );
      
      testData.add( new TestData< Note >( Note.class,
                                          modelElement,
                                          values,
                                          "Full" ) );
   }
   
   
   
   private void addParticipantNoId( Collection< TestData< Participant >> testData )
   {
      final Participant modelElement = new Participant( Constants.NO_ID,
                                                        "Fullname",
                                                        "Username" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( ParticipantColumns.FULLNAME, "Fullname" );
      values.put( ParticipantColumns.USERNAME, "Username" );
      
      testData.add( new TestData< Participant >( Participant.class,
                                                 modelElement,
                                                 values,
                                                 "NoId" ) );
   }
   
   
   
   private void addParticipantFull( Collection< TestData< Participant >> testData )
   {
      final Participant modelElement = new Participant( 1L,
                                                        "Fullname",
                                                        "Username" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( ParticipantColumns._ID, 1L );
      values.put( ParticipantColumns.FULLNAME, "Fullname" );
      values.put( ParticipantColumns.USERNAME, "Username" );
      
      testData.add( new TestData< Participant >( Participant.class,
                                                 modelElement,
                                                 values,
                                                 "Full" ) );
   }
   
   
   
   private void addContactNoId( Collection< TestData< Contact >> testData )
   {
      final Contact modelElement = new Contact( Constants.NO_ID,
                                                "Username",
                                                "Fullname" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( ContactColumns.FULLNAME, "Fullname" );
      values.put( ContactColumns.USERNAME, "Username" );
      
      testData.add( new TestData< Contact >( Contact.class,
                                             modelElement,
                                             values,
                                             "NoId" ) );
   }
   
   
   
   private void addContactFull( Collection< TestData< Contact >> testData )
   {
      final Contact modelElement = new Contact( 1L, "Username", "Fullname" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( ContactColumns._ID, 1L );
      values.put( ContactColumns.FULLNAME, "Fullname" );
      values.put( ContactColumns.USERNAME, "Username" );
      
      testData.add( new TestData< Contact >( Contact.class,
                                             modelElement,
                                             values,
                                             "Full" ) );
   }
   
   
   
   private void addLocationNoId( Collection< TestData< Location >> testData )
   {
      final Location modelElement = new Location( Constants.NO_ID,
                                                  "TestLoc",
                                                  1.0f,
                                                  2.0f,
                                                  null,
                                                  false,
                                                  10 );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( LocationColumns.LOCATION_NAME, "TestLoc" );
      values.put( LocationColumns.LONGITUDE, 1.0f );
      values.put( LocationColumns.LATITUDE, 2.0f );
      values.put( LocationColumns.ADDRESS, null );
      values.put( LocationColumns.VIEWABLE, 0 );
      values.put( LocationColumns.ZOOM, 10 );
      
      testData.add( new TestData< Location >( Location.class,
                                              modelElement,
                                              values,
                                              "NoId" ) );
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( LocationColumns._ID, 1L );
      values.put( LocationColumns.LOCATION_NAME, "TestLoc" );
      values.put( LocationColumns.LONGITUDE, 1.0f );
      values.put( LocationColumns.LATITUDE, 2.0f );
      values.put( LocationColumns.ADDRESS, null );
      values.put( LocationColumns.VIEWABLE, 0 );
      values.put( LocationColumns.ZOOM, 10 );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( LocationColumns._ID, 1L );
      values.put( LocationColumns.LOCATION_NAME, "TestLoc" );
      values.put( LocationColumns.LONGITUDE, 1.0f );
      values.put( LocationColumns.LATITUDE, 2.0f );
      values.put( LocationColumns.ADDRESS, "Address" );
      values.put( LocationColumns.VIEWABLE, 1 );
      values.put( LocationColumns.ZOOM, 10 );
      
      testData.add( new TestData< Location >( Location.class,
                                              modelElement,
                                              values,
                                              "Full" ) );
   }
   
   
   
   private void addModificationNoSyncedVal( Collection< TestData< Modification >> testData )
   {
      final Modification modelElement = Modification.newModification( "content://element/1",
                                                                      "testCol",
                                                                      "1" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( ModificationColumns.ENTITY_URI, "content://element/1" );
      values.put( ModificationColumns.PROPERTY, "testCol" );
      values.put( ModificationColumns.NEW_VALUE, "1" );
      values.put( ModificationColumns.TIMESTAMP, modelElement.getTimestamp() );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( ModificationColumns.ENTITY_URI, "content://element/1" );
      values.put( ModificationColumns.PROPERTY, "testCol" );
      values.put( ModificationColumns.NEW_VALUE, null );
      values.put( ModificationColumns.SYNCED_VALUE, "0" );
      values.put( ModificationColumns.TIMESTAMP, modelElement.getTimestamp() );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( ModificationColumns.ENTITY_URI, "content://element/1" );
      values.put( ModificationColumns.PROPERTY, "testCol" );
      values.put( ModificationColumns.NEW_VALUE, "1" );
      values.put( ModificationColumns.SYNCED_VALUE, null );
      values.put( ModificationColumns.TIMESTAMP, modelElement.getTimestamp() );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( ModificationColumns.ENTITY_URI, "content://element/1" );
      values.put( ModificationColumns.PROPERTY, "testCol" );
      values.put( ModificationColumns.NEW_VALUE, "1" );
      values.put( ModificationColumns.SYNCED_VALUE, "0" );
      values.put( ModificationColumns.TIMESTAMP, NOW );
      
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
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( SettingsColumns.SYNC_TIMESTAMP, NOW );
      values.put( SettingsColumns.TIMEZONE, "UTC" );
      values.put( SettingsColumns.DATEFORMAT, 1 );
      values.put( SettingsColumns.TIMEFORMAT, 2 );
      values.put( SettingsColumns.DEFAULTLIST_ID, null );
      values.put( SettingsColumns.LANGUAGE, "en" );
      
      testData.add( new TestData< Settings >( Settings.class,
                                              modelElement,
                                              values,
                                              "NoDefList" ) );
   }
   
   
   
   private void addRtmSettingsFull( Collection< TestData< Settings >> testData )
   {
      final Settings modelElement = new Settings( NOW, "UTC", 1, 2, 100L, "en" );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( SettingsColumns.SYNC_TIMESTAMP, NOW );
      values.put( SettingsColumns.TIMEZONE, "UTC" );
      values.put( SettingsColumns.DATEFORMAT, 1 );
      values.put( SettingsColumns.TIMEFORMAT, 2 );
      values.put( SettingsColumns.DEFAULTLIST_ID, 100L );
      values.put( SettingsColumns.LANGUAGE, "en" );
      
      testData.add( new TestData< Settings >( Settings.class,
                                              modelElement,
                                              values,
                                              "Full" ) );
   }
   
   
   
   private void addSyncNever( Collection< TestData< SyncTime >> testData )
   {
      final SyncTime modelElement = new SyncTime( NEVER, NEVER );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( SyncTimesColumns.LAST_IN, null );
      values.put( SyncTimesColumns.LAST_OUT, null );
      
      testData.add( new TestData< SyncTime >( SyncTime.class,
                                              modelElement,
                                              values,
                                              "Never" ) );
   }
   
   
   
   private void addSyncFull( Collection< TestData< SyncTime >> testData )
   {
      final SyncTime modelElement = new SyncTime( NOW, LATER );
      
      final Map< String, Object > values = new LinkedHashMap< String, Object >();
      values.put( SyncTimesColumns.LAST_IN, NOW );
      values.put( SyncTimesColumns.LAST_OUT, LATER );
      
      testData.add( new TestData< SyncTime >( SyncTime.class,
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
