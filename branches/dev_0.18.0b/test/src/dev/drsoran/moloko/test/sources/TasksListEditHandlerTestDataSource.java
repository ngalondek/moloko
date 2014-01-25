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

import static dev.drsoran.moloko.content.Columns.TasksListColumns.ARCHIVED;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.FILTER;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.IS_SMART_LIST;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_CREATED_DATE;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_DELETED_DATE;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_MODIFIED_DATE;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LIST_NAME;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.LOCKED;
import static dev.drsoran.moloko.content.Columns.TasksListColumns.POSITION;
import static dev.drsoran.moloko.content.ContentUris.TASKS_LISTS_CONTENT_URI_ID;
import static dev.drsoran.moloko.test.TestConstants.LATER;
import static dev.drsoran.moloko.test.TestConstants.NOW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.rtm.sync.model.Modification;


public class TasksListEditHandlerTestDataSource extends
         ContentEditHandlerTestDataSource< TasksList >
{
   private final long elementId;
   
   
   
   public TasksListEditHandlerTestDataSource( long elementId )
   {
      this.elementId = elementId;
   }
   
   
   
   @Override
   public Collection< TestData< TasksList >> getUpdateTestData()
   {
      final Collection< TestData< TasksList >> testData = new ArrayList< TestData< TasksList > >();
      
      addUpdateLifeTimeManaged( testData );
      addUpdateName( testData );
      addUpdateName( testData );
      addUpdatePosition( testData );
      addUpdateLocked( testData );
      addUpdateArchived( testData );
      addUpdateSmartList( testData );
      
      return testData;
   }
   
   
   
   private void addUpdateLifeTimeManaged( Collection< TestData< TasksList >> testData )
   {
      TasksList existing = new TasksList( 1L, NOW, "TestList", 0, false, false );
      TasksList update = new TasksList( 1L, LATER, "TestList", 0, false, false );
      
      Modification mod = Modification.newNonPersistentModification( getEntityUri(),
                                                                    LIST_CREATED_DATE,
                                                                    LATER );
      
      testData.add( new TestData< TasksList >( existing, update, mod ) );
      
      existing = new TasksList( 1L, NOW, "TestList", 0, false, false );
      existing.setModifiedMillisUtc( NOW );
      
      update = new TasksList( 1L, NOW, "TestList", 0, false, false );
      update.setModifiedMillisUtc( LATER );
      
      mod = Modification.newNonPersistentModification( getEntityUri(),
                                                       LIST_MODIFIED_DATE,
                                                       LATER );
      
      testData.add( new TestData< TasksList >( existing, update, mod ) );
      
      existing = new TasksList( 1L, NOW, "TestList", 0, false, false );
      existing.setDeletedMillisUtc( NOW );
      
      update = new TasksList( 1L, NOW, "TestList", 0, false, false );
      update.setDeletedMillisUtc( LATER );
      
      mod = Modification.newNonPersistentModification( getEntityUri(),
                                                       LIST_DELETED_DATE,
                                                       LATER );
      
      testData.add( new TestData< TasksList >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateName( Collection< TestData< TasksList >> testData )
   {
      TasksList existing = new TasksList( 1L, NOW, "TestList", 0, false, false );
      TasksList update = new TasksList( 1L, NOW, "TestList1", 0, false, false );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       LIST_NAME,
                                                       "TestList1",
                                                       "TestList" );
      
      testData.add( new TestData< TasksList >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateLocked( Collection< TestData< TasksList >> testData )
   {
      TasksList existing = new TasksList( 1L, NOW, "TestList", 0, false, false );
      TasksList update = new TasksList( 1L, NOW, "TestList", 0, true, false );
      
      Modification mod = Modification.newNonPersistentModification( getEntityUri(),
                                                                    LOCKED,
                                                                    1 );
      
      testData.add( new TestData< TasksList >( existing, update, mod ) );
      
      existing = new TasksList( 1L, NOW, "TestList", 0, true, false );
      update = new TasksList( 1L, NOW, "TestList", 0, false, false );
      
      mod = Modification.newNonPersistentModification( getEntityUri(),
                                                       LOCKED,
                                                       0 );
      
      testData.add( new TestData< TasksList >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateArchived( Collection< TestData< TasksList >> testData )
   {
      TasksList existing = new TasksList( 1L, NOW, "TestList", 0, false, false );
      TasksList update = new TasksList( 1L, NOW, "TestList", 0, false, true );
      
      Modification mod = Modification.newNonPersistentModification( getEntityUri(),
                                                                    ARCHIVED,
                                                                    1 );
      
      testData.add( new TestData< TasksList >( existing, update, mod ) );
      
      existing = new TasksList( 1L, NOW, "TestList", 0, false, true );
      update = new TasksList( 1L, NOW, "TestList", 0, false, false );
      
      mod = Modification.newNonPersistentModification( getEntityUri(),
                                                       ARCHIVED,
                                                       0 );
      
      testData.add( new TestData< TasksList >( existing, update, mod ) );
   }
   
   
   
   private void addUpdatePosition( Collection< TestData< TasksList >> testData )
   {
      TasksList existing = new TasksList( 1L, NOW, "TestList", 0, false, false );
      TasksList update = new TasksList( 1L, NOW, "TestList", -1, false, false );
      
      Modification mod = Modification.newNonPersistentModification( getEntityUri(),
                                                                    POSITION,
                                                                    -1 );
      
      testData.add( new TestData< TasksList >( existing, update, mod ) );
   }
   
   
   
   private void addUpdateSmartList( Collection< TestData< TasksList >> testData )
   {
      TasksList existing = new TasksList( 1L, NOW, "TestList", 0, false, false );
      TasksList update = new TasksList( 1L, NOW, "TestList", 0, false, false );
      update.setSmartFilter( new RtmSmartFilter( "list:List" ) );
      
      Modification mod = Modification.newModification( getEntityUri(),
                                                       FILTER,
                                                       "list:List",
                                                       null );
      Modification mod1 = Modification.newNonPersistentModification( getEntityUri(),
                                                                     IS_SMART_LIST,
                                                                     1 );
      
      testData.add( new TestData< TasksList >( existing,
                                               update,
                                               Arrays.asList( mod, mod1 ) ) );
      
      existing = new TasksList( 1L, NOW, "TestList", 0, false, false );
      existing.setSmartFilter( new RtmSmartFilter( "list:List" ) );
      update = new TasksList( 1L, NOW, "TestList", 0, false, false );
      
      mod = Modification.newModification( getEntityUri(),
                                          FILTER,
                                          null,
                                          "list:List" );
      mod1 = Modification.newNonPersistentModification( getEntityUri(),
                                                        IS_SMART_LIST,
                                                        0 );
      
      testData.add( new TestData< TasksList >( existing,
                                               update,
                                               Arrays.asList( mod, mod1 ) ) );
      
      existing = new TasksList( 1L, NOW, "TestList", 0, false, false );
      existing.setSmartFilter( new RtmSmartFilter( "list:List" ) );
      update = new TasksList( 1L, NOW, "TestList", 0, false, false );
      update.setSmartFilter( new RtmSmartFilter( "list:List AND name:abc" ) );
      
      mod = Modification.newModification( getEntityUri(),
                                          FILTER,
                                          "list:List AND name:abc",
                                          "list:List" );
      testData.add( new TestData< TasksList >( existing, update, mod ) );
   }
   
   
   
   private String getEntityUri()
   {
      return ContentUris.bindElementId( TASKS_LISTS_CONTENT_URI_ID, elementId )
                        .toString();
   }
}
