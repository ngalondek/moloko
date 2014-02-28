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

import static dev.drsoran.moloko.content.Columns.TaskColumns.ADDED_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.COMPLETED_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.DELETED_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.DUE_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.ESTIMATE_MILLIS;
import static dev.drsoran.moloko.content.Columns.TaskColumns.HAS_DUE_TIME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LIST_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.LOCATION_ID;
import static dev.drsoran.moloko.content.Columns.TaskColumns.POSTPONED;
import static dev.drsoran.moloko.content.Columns.TaskColumns.PRIORITY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.RECURRENCE_EVERY;
import static dev.drsoran.moloko.content.Columns.TaskColumns.SOURCE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TAGS;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_CREATED_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_MODIFIED_DATE;
import static dev.drsoran.moloko.content.Columns.TaskColumns.TASK_NAME;
import static dev.drsoran.moloko.content.Columns.TaskColumns.URL;
import static dev.drsoran.moloko.content.ContentUris.TASKS_CONTENT_URI_ID;
import static dev.drsoran.moloko.test.TestConstants.LATER;
import static dev.drsoran.moloko.test.TestConstants.NOW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import dev.drsoran.Strings;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.db.Modification;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.rtm.model.Priority;


public class TaskEditHandlerTestDataSource extends
         ContentEditHandlerTestDataSource< Task >
{
   private final long elementId;
   
   
   
   public TaskEditHandlerTestDataSource( long elementId )
   {
      this.elementId = elementId;
   }
   
   
   
   @Override
   public Collection< TestData< Task >> getUpdateTestData()
   {
      final Collection< TestData< Task >> testData = new ArrayList< TestData< Task > >();
      
      addUpdateLifeTimeManaged( testData );
      addUpdateName( testData );
      addUpdateName( testData );
      addUpdateAdded( testData );
      addUpdateCompleted( testData );
      addUpdateListId( testData );
      addUpdateListName( testData );
      addUpdateLocationName( testData );
      addUpdateLocationId( testData );
      addUpdateSource( testData );
      addUpdateUrl( testData );
      addUpdateTags( testData );
      addUpdatePriority( testData );
      addUpdatePostponed( testData );
      addUpdateRecurrence( testData );
      addUpdateDue( testData );
      addUpdateEstimation( testData );
      
      return testData;
   }
   
   
   
   private void addUpdateLifeTimeManaged( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, LATER, NOW, "Task", 100L, "List" );
      
      Modification mod = Modification.newNonPersistentModification( getEntityUri(),
                                                                    TASK_CREATED_DATE,
                                                                    LATER );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setModifiedMillisUtc( NOW );
      
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setModifiedMillisUtc( LATER );
      
      mod = Modification.newNonPersistentModification( getEntityUri(),
                                                       TASK_MODIFIED_DATE,
                                                       LATER );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setDeletedMillisUtc( NOW );
      
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setDeletedMillisUtc( LATER );
      
      mod = Modification.newNonPersistentModification( getEntityUri(),
                                                       DELETED_DATE,
                                                       LATER );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateAdded( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, LATER, "Task", 100L, "List" );
      
      Modification mod = Modification.newNonPersistentModification( getEntityUri(),
                                                                    ADDED_DATE,
                                                                    LATER );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateCompleted( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setCompletedMillisUtc( NOW );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       COMPLETED_DATE,
                                                       NOW,
                                                       Constants.NO_TIME );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setCompletedMillisUtc( NOW );
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setCompletedMillisUtc( Constants.NO_TIME );
      
      mod = Modification.newModification( getEntityUri(),
                                          COMPLETED_DATE,
                                          Constants.NO_TIME,
                                          NOW );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateName( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task1", 100L, "List" );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       TASK_NAME,
                                                       "Task1",
                                                       "Task" );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateListId( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 101L, "List" );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       LIST_ID,
                                                       101L,
                                                       100L );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateListName( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List1" );
      
      // Expect no modification since the list name cannot be updated through a task.
      testData.add( new TestData< Task >( existing, update ) );
   }
   
   
   
   private void addUpdateLocationName( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setLocationStub( 1000L, "Loc" );
      
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setLocationStub( 1000L, "Loca" );
      
      // Expect no modification since the location name cannot be updated through a task.
      testData.add( new TestData< Task >( existing, update ) );
   }
   
   
   
   private void addUpdateLocationId( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setLocationStub( 1000L, "Loc" );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       LOCATION_ID,
                                                       1000L,
                                                       Constants.NO_ID );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setLocationStub( 1000L, "Loc" );
      
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setLocationStub( 1001L, "OtherLoc" );
      
      mod = Modification.newModification( getEntityUri(),
                                          LOCATION_ID,
                                          1001L,
                                          1000L );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setLocationStub( 1000L, "Loc" );
      
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setLocationStub( Constants.NO_ID, null );
      
      mod = Modification.newModification( getEntityUri(),
                                          LOCATION_ID,
                                          Constants.NO_ID,
                                          1000L );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateSource( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setSource( "Moloko" );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       SOURCE,
                                                       "Moloko",
                                                       Strings.EMPTY_STRING );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateUrl( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setUrl( "http://abc.de" );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       URL,
                                                       "http://abc.de",
                                                       Strings.EMPTY_STRING );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateTags( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setTags( Arrays.asList( "tag1", "tag2" ) );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       TAGS,
                                                       "tag1,tag2",
                                                       Strings.EMPTY_STRING );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setTags( Arrays.asList( "tag1", "tag2" ) );
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setTags( Collections.< String > emptyList() );
      
      mod = Modification.newModification( getEntityUri(),
                                          TAGS,
                                          Strings.EMPTY_STRING,
                                          "tag1,tag2" );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdatePriority( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setPriority( Priority.High );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       PRIORITY,
                                                       Priority.High.toString(),
                                                       Priority.None.toString() );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdatePostponed( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setPostponedCount( 2 );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       POSTPONED,
                                                       2,
                                                       0 );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateRecurrence( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setRecurrence( new Recurrence( "every day", true ) );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       RECURRENCE,
                                                       "every day",
                                                       null );
      Modification mod1 = Modification.newModification( getEntityUri(),
                                                        RECURRENCE_EVERY,
                                                        1,
                                                        0 );
      
      testData.add( new TestData< Task >( existing,
                                          update,
                                          Arrays.asList( mod, mod1 ) ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setRecurrence( new Recurrence( "every day", true ) );
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setRecurrence( new Recurrence( "after a day", false ) );
      
      mod = Modification.newModification( getEntityUri(),
                                          RECURRENCE,
                                          "after a day",
                                          "every day" );
      
      mod1 = Modification.newModification( getEntityUri(),
                                           RECURRENCE_EVERY,
                                           0,
                                           1 );
      
      testData.add( new TestData< Task >( existing,
                                          update,
                                          Arrays.asList( mod, mod1 ) ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setRecurrence( new Recurrence( "every day", true ) );
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setRecurrence( null );
      
      mod = Modification.newModification( getEntityUri(),
                                          RECURRENCE,
                                          null,
                                          "every day" );
      mod1 = Modification.newModification( getEntityUri(),
                                           RECURRENCE_EVERY,
                                           0,
                                           1 );
      
      testData.add( new TestData< Task >( existing,
                                          update,
                                          Arrays.asList( mod, mod1 ) ) );
   }
   
   
   
   private void addUpdateDue( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setDue( new Due( NOW, true ) );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       DUE_DATE,
                                                       NOW,
                                                       null );
      Modification mod1 = Modification.newModification( getEntityUri(),
                                                        HAS_DUE_TIME,
                                                        1,
                                                        0 );
      
      testData.add( new TestData< Task >( existing,
                                          update,
                                          Arrays.asList( mod, mod1 ) ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setDue( new Due( NOW, true ) );
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setDue( new Due( NOW, false ) );
      
      mod = Modification.newModification( getEntityUri(), HAS_DUE_TIME, 0, 1 );
      
      testData.add( new TestData< Task >( existing, update, mod ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setDue( new Due( NOW, true ) );
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setDue( null );
      
      mod = Modification.newModification( getEntityUri(), DUE_DATE, null, NOW );
      mod1 = Modification.newModification( getEntityUri(), HAS_DUE_TIME, 0, 1 );
      
      testData.add( new TestData< Task >( existing,
                                          update,
                                          Arrays.asList( mod, mod1 ) ) );
   }
   
   
   
   private void addUpdateEstimation( Collection< TestData< Task >> testData )
   {
      Task existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      Task update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setEstimation( new Estimation( "1 hour", 36000000L ) );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       ESTIMATE,
                                                       "1 hour",
                                                       null );
      Modification mod1 = Modification.newModification( getEntityUri(),
                                                        ESTIMATE_MILLIS,
                                                        36000000L,
                                                        -1 );
      
      testData.add( new TestData< Task >( existing,
                                          update,
                                          Arrays.asList( mod, mod1 ) ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setEstimation( new Estimation( "1 hour", 36000000L ) );
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setEstimation( new Estimation( "2 hours", 72000000L ) );
      
      mod = Modification.newModification( getEntityUri(),
                                          ESTIMATE,
                                          "2 hours",
                                          "1 hour" );
      
      mod1 = Modification.newModification( getEntityUri(),
                                           ESTIMATE_MILLIS,
                                           72000000L,
                                           36000000L );
      
      testData.add( new TestData< Task >( existing,
                                          update,
                                          Arrays.asList( mod, mod1 ) ) );
      
      existing = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      existing.setEstimation( new Estimation( "1 hour", 36000000L ) );
      update = new Task( 1L, NOW, NOW, "Task", 100L, "List" );
      update.setEstimation( null );
      
      mod = Modification.newModification( getEntityUri(),
                                          ESTIMATE,
                                          null,
                                          "1 hour" );
      
      mod1 = Modification.newModification( getEntityUri(),
                                           ESTIMATE_MILLIS,
                                           -1,
                                           36000000L );
      
      testData.add( new TestData< Task >( existing,
                                          update,
                                          Arrays.asList( mod, mod1 ) ) );
   }
   
   
   
   private String getEntityUri()
   {
      return ContentUris.bindElementId( TASKS_CONTENT_URI_ID, elementId )
                        .toString();
   }
}
