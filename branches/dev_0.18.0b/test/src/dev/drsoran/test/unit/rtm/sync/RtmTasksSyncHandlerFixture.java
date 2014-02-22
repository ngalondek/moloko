/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.test.unit.rtm.sync;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.test.TestConstants;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;
import dev.drsoran.rtm.service.RtmErrorCodes;
import dev.drsoran.rtm.sync.IModification;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.sync.RtmContentSort;
import dev.drsoran.rtm.sync.RtmSyncResult;
import dev.drsoran.rtm.sync.RtmTasksSyncHandler;


public class RtmTasksSyncHandlerFixture
{
   private static final String TIMELINE_ID = "10000";
   
   private static final String TASKSERIES_ID = "100";
   
   private static final String LIST_ID = "1000";
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmTasksSyncHandler()
   {
      new RtmTasksSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                               EasyMock.createNiceMock( Comparator.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTasksSyncHandler_NullSyncPartner()
   {
      new RtmTasksSyncHandler( null, EasyMock.createNiceMock( Comparator.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTasksSyncHandler_NullComparator()
   {
      new RtmTasksSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                               null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testHandleIncomingSync_NullRepo() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                                                       EasyMock.createNiceMock( Comparator.class ) );
      
      syncHandler.handleIncomingSync( null, TestConstants.NEVER );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_RtmError() throws RtmServiceException
   {
      final IRtmSyncPartner syncPartner = EasyMock.createNiceMock( IRtmSyncPartner.class );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.tasks_getList( TestConstants.NEVER ) )
              .andThrow( new RtmServiceException( "" ) );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
                                                                    TestConstants.NEVER );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( false ) );
      assertThat( rtmSyncResult.getException(),
                  is( instanceOf( RtmServiceException.class ) ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_BothEmpty() throws RtmServiceException
   {
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( RtmConstants.NO_TIME ) )
              .andReturn( Collections.< RtmTask > emptyList() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.tasks_getList( TestConstants.NEVER ) )
              .andReturn( Collections.< RtmTask > emptyList() );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      final RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
                                                                          TestConstants.NEVER );
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_LocalEmpty_RtmNew() throws RtmServiceException
   {
      final RtmTask taskFromRtm = newTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( RtmConstants.NO_TIME ) )
              .andReturn( partnerTasksList );
      syncPartner.insertTask( taskFromRtm );
      EasyMock.replay( syncPartner );
      
      final List< RtmTask > rtmList = new ArrayList< RtmTask >();
      rtmList.add( taskFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.tasks_getList( TestConstants.NEVER ) )
              .andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      final RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
                                                                          TestConstants.NEVER );
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_LocalEmpty_RtmDeleted() throws RtmServiceException
   {
      final RtmTask taskFromRtm = newDeletedTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( RtmConstants.NO_TIME ) )
              .andReturn( partnerTasksList );
      EasyMock.replay( syncPartner );
      
      final List< RtmTask > rtmList = new ArrayList< RtmTask >();
      rtmList.add( taskFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.tasks_getList( TestConstants.NEVER ) )
              .andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
                                                                    TestConstants.NEVER );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_Update() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( "1" );
      final RtmTask taskFromRtm = newTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( RtmConstants.NO_TIME ) )
              .andReturn( partnerTasksList );
      syncPartner.updateTask( taskFromPartner, taskFromRtm );
      EasyMock.replay( syncPartner );
      
      final List< RtmTask > rtmList = new ArrayList< RtmTask >();
      rtmList.add( taskFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.tasks_getList( TestConstants.NEVER ) )
              .andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
                                                                    TestConstants.NEVER );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_Delete() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( "1" );
      final RtmTask taskFromRtm = newDeletedTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( RtmConstants.NO_TIME ) )
              .andReturn( partnerTasksList );
      syncPartner.deleteTask( taskFromPartner );
      EasyMock.replay( syncPartner );
      
      final List< RtmTask > rtmList = new ArrayList< RtmTask >();
      rtmList.add( taskFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.tasks_getList( TestConstants.NEVER ) )
              .andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
                                                                    TestConstants.NEVER );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testHandleOutgoingSync_NullEditService() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                                                       EasyMock.createNiceMock( Comparator.class ) );
      
      syncHandler.handleOutgoingSync( null, "1", TestConstants.EVEN_LATER );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testHandleOutgoingSync_NullTimelineId() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                                                       EasyMock.createNiceMock( Comparator.class ) );
      
      syncHandler.handleOutgoingSync( EasyMock.createNiceMock( IRtmContentEditService.class ),
                                      null,
                                      TestConstants.EVEN_LATER );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Empty() throws RtmServiceException
   {
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( new ArrayList< RtmTask >() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.replay( rtmRepo );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    "1000",
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Insert() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( RtmConstants.NO_ID );
      final RtmTask respTask = newTask( "2" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Collections.< IModification > emptyList() );
      syncPartner.updateTask( taskFromPartner, respTask );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< List< RtmTask > > rtmResponse = new RtmResponse< List< RtmTask > >( new RtmTransaction( "50",
                                                                                                                 true ),
                                                                                             Arrays.asList( respTask ) );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_add( TIMELINE_ID, "1000", "TestTask" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 1 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Insert_WithNote() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTaskWithNotes( RtmConstants.NO_ID,
                                                        new RtmNote( RtmConstants.NO_ID,
                                                                     TestConstants.NOW,
                                                                     TestConstants.NOW,
                                                                     "title",
                                                                     "text" ) );
      final RtmTask respTaskInsert = newTask( "2" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Collections.< IModification > emptyList() );
      syncPartner.updateTask( taskFromPartner, respTaskInsert );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< List< RtmTask > > rtmResponseInsert = new RtmResponse< List< RtmTask > >( new RtmTransaction( "50",
                                                                                                                       true ),
                                                                                                   Arrays.asList( respTaskInsert ) );
      
      final RtmResponse< RtmNote > rtmResponseInsertNote = new RtmResponse< RtmNote >( new RtmTransaction( "51",
                                                                                                           true ),
                                                                                       new RtmNote( "20",
                                                                                                    TestConstants.NOW,
                                                                                                    TestConstants.NOW,
                                                                                                    "title",
                                                                                                    "text" ) );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_add( TIMELINE_ID, LIST_ID, "TestTask" ) )
              .andReturn( rtmResponseInsert );
      EasyMock.expect( rtmEdit.tasks_notes_add( TIMELINE_ID,
                                                LIST_ID,
                                                TASKSERIES_ID,
                                                "2",
                                                "title",
                                                "text" ) )
              .andReturn( rtmResponseInsertNote );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( respTaskInsert.hasNote( "20" ), is( true ) );
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 2 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Insert_MultipleResponse() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( RtmConstants.NO_ID );
      final RtmTask respTask1 = newTask( "1" );
      final RtmTask respTask2 = newTask( "2" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Collections.< IModification > emptyList() )
              .times( 2 );
      syncPartner.updateTask( taskFromPartner, respTask1 );
      syncPartner.insertTask( respTask2 );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< List< RtmTask > > rtmResponse = new RtmResponse< List< RtmTask > >( new RtmTransaction( "50",
                                                                                                                 true ),
                                                                                             Arrays.asList( respTask1,
                                                                                                            respTask2 ) );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_add( TIMELINE_ID, LIST_ID, "TestTask" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 1 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_DontInsertDeleted() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newDeletedTask( RtmConstants.NO_ID );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    "1000",
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_GeneratedTasks() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( "1" );
      final RtmTask respTask1 = newTask( "1" );
      final RtmTask respTask2 = newTask( "2" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IModification modification = EasyMock.createStrictMock( IModification.class );
      EasyMock.expect( modification.getPropertyName() )
              .andReturn( RtmTaskProperties.NAME );
      EasyMock.replay( modification );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Collections.< RtmNote > emptyList() );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Arrays.asList( modification ) );
      syncPartner.updateTask( taskFromPartner, respTask1 );
      syncPartner.insertTask( respTask2 );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< List< RtmTask > > rtmResponse = new RtmResponse< List< RtmTask > >( new RtmTransaction( "50",
                                                                                                                 true ),
                                                                                             Arrays.asList( respTask1,
                                                                                                            respTask2 ) );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_setName( TIMELINE_ID,
                                              LIST_ID,
                                              TASKSERIES_ID,
                                              "1",
                                              "TestTask" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      final RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                          TIMELINE_ID,
                                                                          TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
      EasyMock.verify( modification );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_TaskName() throws RtmServiceException
   {
      testHandleOutgoingSync_Update( RtmTaskProperties.NAME,
                                     newTask( "1", "NewName" ),
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( rtmEdit.tasks_setName( TIMELINE_ID,
                                                                                   LIST_ID,
                                                                                   TASKSERIES_ID,
                                                                                   "1",
                                                                                   "NewName" ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_Recurrence() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      task.setRecurrenceSentence( "every 2 days" );
      
      testHandleOutgoingSync_Update( RtmTaskProperties.RECURRENCE,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( rtmEdit.tasks_setRecurrence( TIMELINE_ID,
                                                                                         LIST_ID,
                                                                                         TASKSERIES_ID,
                                                                                         "1",
                                                                                         "every 2 days" ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_Tags() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      testHandleOutgoingSync_Update( RtmTaskProperties.TAGS,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( rtmEdit.tasks_setTags( TIMELINE_ID,
                                                                                   LIST_ID,
                                                                                   TASKSERIES_ID,
                                                                                   "1",
                                                                                   "tag1,tag2" ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_LocationId() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      testHandleOutgoingSync_Update( RtmTaskProperties.LOCATION_ID,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( rtmEdit.tasks_setLocation( TIMELINE_ID,
                                                                                       LIST_ID,
                                                                                       TASKSERIES_ID,
                                                                                       "1",
                                                                                       "locationId" ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_URL() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      testHandleOutgoingSync_Update( RtmTaskProperties.URL, task, new RtmFunc()
      {
         @Override
         public void callRtmMethod( IRtmContentEditService rtmEdit,
                                    IModification modification,
                                    RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
         {
            EasyMock.expect( rtmEdit.tasks_setURL( TIMELINE_ID,
                                                   LIST_ID,
                                                   TASKSERIES_ID,
                                                   "1",
                                                   "url" ) )
                    .andReturn( rtmResponse );
            
         }
      } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_Priority() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      testHandleOutgoingSync_Update( RtmTaskProperties.PRIORITY,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( rtmEdit.tasks_setPriority( TIMELINE_ID,
                                                                                       LIST_ID,
                                                                                       TASKSERIES_ID,
                                                                                       "1",
                                                                                       Priority.High ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_Complete() throws RtmServiceException
   {
      final RtmTask task = new RtmTask( "1",
                                        TASKSERIES_ID,
                                        TestConstants.NOW,
                                        TestConstants.NOW,
                                        TestConstants.NOW,
                                        TestConstants.NEVER,
                                        LIST_ID,
                                        "locationId",
                                        "TestName",
                                        "source",
                                        "url",
                                        TestConstants.NOW,
                                        Priority.High,
                                        0,
                                        TestConstants.NEVER,
                                        false,
                                        null,
                                        false,
                                        null,
                                        Collections.< String > emptyList(),
                                        Collections.< RtmNote > emptyList(),
                                        Collections.< RtmContact > emptyList() );
      
      testHandleOutgoingSync_Update( RtmTaskProperties.COMPLETED_DATE,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( rtmEdit.tasks_complete( TIMELINE_ID,
                                                                                    LIST_ID,
                                                                                    TASKSERIES_ID,
                                                                                    "1" ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_Incomplete() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      testHandleOutgoingSync_Update( RtmTaskProperties.COMPLETED_DATE,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( rtmEdit.tasks_uncomplete( TIMELINE_ID,
                                                                                      LIST_ID,
                                                                                      TASKSERIES_ID,
                                                                                      "1" ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_DueDate() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      testHandleOutgoingSync_Update( RtmTaskProperties.DUE_DATE,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( rtmEdit.tasks_setDueDate( TIMELINE_ID,
                                                                                      LIST_ID,
                                                                                      TASKSERIES_ID,
                                                                                      "1",
                                                                                      TestConstants.EVEN_LATER,
                                                                                      true ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_HasDueDate() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      testHandleOutgoingSync_Update( RtmTaskProperties.HAS_DUE_TIME,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( rtmEdit.tasks_setDueDate( TIMELINE_ID,
                                                                                      LIST_ID,
                                                                                      TASKSERIES_ID,
                                                                                      "1",
                                                                                      TestConstants.EVEN_LATER,
                                                                                      true ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_Estimate() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      testHandleOutgoingSync_Update( RtmTaskProperties.ESTIMATE,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( rtmEdit.tasks_setEstimate( TIMELINE_ID,
                                                                                       LIST_ID,
                                                                                       TASKSERIES_ID,
                                                                                       "1",
                                                                                       "2 hours" ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_Postponed() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      testHandleOutgoingSync_Update( RtmTaskProperties.POSTPONED,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( modification.getSyncedValue() )
                                                   .andReturn( "0" );
                                           
                                           EasyMock.expect( rtmEdit.tasks_postpone( TIMELINE_ID,
                                                                                    LIST_ID,
                                                                                    TASKSERIES_ID,
                                                                                    "1" ) )
                                                   .andReturn( rtmResponse )
                                                   .times( 2 );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_ListId() throws RtmServiceException
   {
      final RtmTask task = newTask( "1" );
      testHandleOutgoingSync_Update( RtmTaskProperties.LIST_ID,
                                     task,
                                     new RtmFunc()
                                     {
                                        @Override
                                        public void callRtmMethod( IRtmContentEditService rtmEdit,
                                                                   IModification modification,
                                                                   RtmResponse< List< RtmTask >> rtmResponse ) throws RtmServiceException
                                        {
                                           EasyMock.expect( modification.getSyncedValue() )
                                                   .andReturn( "1001" );
                                           
                                           EasyMock.expect( rtmEdit.tasks_moveTo( TIMELINE_ID,
                                                                                  "1001",
                                                                                  LIST_ID,
                                                                                  TASKSERIES_ID,
                                                                                  "1" ) )
                                                   .andReturn( rtmResponse );
                                           
                                        }
                                     } );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_UnhandledModification() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IModification nameMod = EasyMock.createStrictMock( IModification.class );
      EasyMock.expect( nameMod.getPropertyName() )
              .andReturn( "UnhandledProperty" );
      EasyMock.replay( nameMod );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Collections.< RtmNote > emptyList() );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Arrays.asList( nameMod ) );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
      EasyMock.verify( nameMod );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_NoModification() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Collections.< RtmNote > emptyList() );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Collections.< IModification > emptyList() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_NotFoundAtRTM() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( "1", "NewName" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IModification nameMod = EasyMock.createStrictMock( IModification.class );
      EasyMock.expect( nameMod.getPropertyName() )
              .andReturn( RtmTaskProperties.NAME );
      EasyMock.replay( nameMod );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Collections.< RtmNote > emptyList() );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Arrays.asList( nameMod ) );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_setName( TIMELINE_ID,
                                              LIST_ID,
                                              TASKSERIES_ID,
                                              "1",
                                              "NewName" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.TASK_INVALID_ID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
      EasyMock.verify( nameMod );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_OtherRTMError() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( "1", "NewName" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IModification nameMod = EasyMock.createStrictMock( IModification.class );
      EasyMock.expect( nameMod.getPropertyName() )
              .andReturn( RtmTaskProperties.NAME );
      EasyMock.replay( nameMod );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Collections.< RtmNote > emptyList() );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Arrays.asList( nameMod ) );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_setName( TIMELINE_ID,
                                              LIST_ID,
                                              TASKSERIES_ID,
                                              "1",
                                              "NewName" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.LIST_NAME_INVALID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( false ) );
      assertThat( rtmSyncResult.getException(),
                  is( instanceOf( RtmServiceException.class ) ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
      EasyMock.verify( nameMod );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Delete() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newDeletedTask( "1" );
      final RtmTask respTask = newDeletedTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      syncPartner.updateTask( taskFromPartner, respTask );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< List< RtmTask > > rtmResponse = new RtmResponse< List< RtmTask > >( new RtmTransaction( "50",
                                                                                                                 true ),
                                                                                             Arrays.asList( respTask ) );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_delete( TIMELINE_ID,
                                             LIST_ID,
                                             TASKSERIES_ID,
                                             "1" ) ).andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 1 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Delete_NotFoundAtRTM() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newDeletedTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_delete( TIMELINE_ID,
                                             LIST_ID,
                                             TASKSERIES_ID,
                                             "1" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.TASK_INVALID_ID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Delete_OtherRTMError() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newDeletedTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_delete( TIMELINE_ID,
                                             LIST_ID,
                                             TASKSERIES_ID,
                                             "1" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.SERVICE_UNAVAILABLE,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( false ) );
      assertThat( rtmSyncResult.getException(),
                  is( instanceOf( RtmServiceException.class ) ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_DontAddNonUndoable() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newDeletedTask( "1" );
      final RtmTask respTask = newDeletedTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createNiceMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      syncPartner.updateTask( taskFromPartner, respTask );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< List< RtmTask > > rtmResponse = new RtmResponse< List< RtmTask > >( new RtmTransaction( "50",
                                                                                                                 false ),
                                                                                             Arrays.asList( respTask ) );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_delete( TIMELINE_ID,
                                             LIST_ID,
                                             TASKSERIES_ID,
                                             "1" ) ).andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Note_Insert() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTaskWithNotes( "1",
                                                        new RtmNote( RtmConstants.NO_ID,
                                                                     TestConstants.NOW,
                                                                     TestConstants.NOW,
                                                                     "title",
                                                                     "text" ) );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Collections.< RtmNote > emptyList() );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Collections.< IModification > emptyList() );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< RtmNote > rtmResponse = new RtmResponse< RtmNote >( new RtmTransaction( "50",
                                                                                                 true ),
                                                                             new RtmNote( "10",
                                                                                          TestConstants.NOW,
                                                                                          TestConstants.NOW,
                                                                                          "title",
                                                                                          "text" ) );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_notes_add( TIMELINE_ID,
                                                LIST_ID,
                                                TASKSERIES_ID,
                                                "1",
                                                "title",
                                                "text" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 1 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Note_Update() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTaskWithNotes( "1",
                                                        new RtmNote( "20",
                                                                     TestConstants.NOW,
                                                                     TestConstants.NOW,
                                                                     "title",
                                                                     "text" ) );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Collections.< RtmNote > emptyList() );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Collections.< IModification > emptyList() );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< RtmNote > rtmResponse = new RtmResponse< RtmNote >( new RtmTransaction( "50",
                                                                                                 true ),
                                                                             new RtmNote( "20",
                                                                                          TestConstants.NOW,
                                                                                          TestConstants.NOW,
                                                                                          "title",
                                                                                          "text" ) );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_notes_edit( TIMELINE_ID,
                                                 "20",
                                                 "title",
                                                 "text" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    TIMELINE_ID,
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 1 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Note_Update_NotFoundAtRTM() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTaskWithNotes( "1",
                                                        new RtmNote( "20",
                                                                     TestConstants.NOW,
                                                                     TestConstants.NOW,
                                                                     "title",
                                                                     "text" ) );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Collections.< RtmNote > emptyList() );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Collections.< IModification > emptyList() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_notes_edit( TIMELINE_ID,
                                                 "20",
                                                 "title",
                                                 "text" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.NOTE_INVALID_ID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      final RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                          TIMELINE_ID,
                                                                          TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Note_Update_OtherRTMError() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTaskWithNotes( "1",
                                                        new RtmNote( "20",
                                                                     TestConstants.NOW,
                                                                     TestConstants.NOW,
                                                                     "title",
                                                                     "text" ) );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_notes_edit( TIMELINE_ID,
                                                 "20",
                                                 "title",
                                                 "text" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.SERVICE_UNAVAILABLE,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      final RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                          TIMELINE_ID,
                                                                          TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( false ) );
      assertThat( rtmSyncResult.getException(),
                  is( instanceOf( RtmServiceException.class ) ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Note_Delete() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Arrays.asList( new RtmNote( "20",
                                                      TestConstants.NOW,
                                                      TestConstants.NOW,
                                                      "title",
                                                      "text" ) ) );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Collections.< IModification > emptyList() );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< Void > rtmResponse = new RtmResponse< Void >( new RtmTransaction( "50",
                                                                                           true ),
                                                                       null );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_notes_delete( TIMELINE_ID, "20" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      final RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                          TIMELINE_ID,
                                                                          TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 1 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Note_Delete_NotFoundAtRTM() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Arrays.asList( new RtmNote( "20",
                                                      TestConstants.NOW,
                                                      TestConstants.NOW,
                                                      "title",
                                                      "text" ) ) );
      EasyMock.expect( syncPartner.getModificationsOfTask( taskFromPartner ) )
              .andReturn( Collections.< IModification > emptyList() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_notes_delete( TIMELINE_ID, "20" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.NOTE_INVALID_ID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      final RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                          TIMELINE_ID,
                                                                          TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Note_Delete_OtherRTMError() throws RtmServiceException
   {
      final RtmTask taskFromPartner = newTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( taskFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( taskFromPartner,
                                                    TestConstants.NOW ) )
              .andReturn( Arrays.asList( new RtmNote( "20",
                                                      TestConstants.NOW,
                                                      TestConstants.NOW,
                                                      "title",
                                                      "text" ) ) );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.tasks_notes_delete( TIMELINE_ID, "20" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.SERVICE_UNAVAILABLE,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      final RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                          TIMELINE_ID,
                                                                          TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( false ) );
      assertThat( rtmSyncResult.getException(),
                  is( instanceOf( RtmServiceException.class ) ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   private void testHandleOutgoingSync_Update( String property,
                                               RtmTask partnerTask,
                                               RtmFunc rtmMethod ) throws RtmServiceException
   {
      final RtmTask respTask = newTask( "1" );
      
      final List< RtmTask > partnerTasksList = new ArrayList< RtmTask >();
      partnerTasksList.add( partnerTask );
      
      final IModification modification = EasyMock.createStrictMock( IModification.class );
      EasyMock.expect( modification.getPropertyName() ).andReturn( property );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasks( TestConstants.NOW ) )
              .andReturn( partnerTasksList );
      EasyMock.expect( syncPartner.getDeletedNotes( partnerTask,
                                                    TestConstants.NOW ) )
              .andReturn( Collections.< RtmNote > emptyList() );
      EasyMock.expect( syncPartner.getModificationsOfTask( partnerTask ) )
              .andReturn( Arrays.asList( modification ) );
      syncPartner.updateTask( partnerTask, respTask );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< List< RtmTask > > rtmResponse = new RtmResponse< List< RtmTask > >( new RtmTransaction( "50",
                                                                                                                 true ),
                                                                                             Arrays.asList( respTask ) );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      rtmMethod.callRtmMethod( rtmEdit, modification, rtmResponse );
      
      EasyMock.replay( rtmEdit );
      EasyMock.replay( modification );
      
      final RtmTasksSyncHandler syncHandler = new RtmTasksSyncHandler( syncPartner,
                                                                       RtmContentSort.getRtmTaskIdSort() );
      
      final RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                          TIMELINE_ID,
                                                                          TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
      EasyMock.verify( modification );
   }
   
   
   
   private static RtmTask newTask( String id )
   {
      return newTask( id, "TestTask" );
   }
   
   
   
   private static RtmTask newDeletedTask( String id )
   {
      return newTask( id, "TestTask", true, Collections.< RtmNote > emptyList() );
   }
   
   
   
   private static RtmTask newTask( String id, String name )
   {
      return newTask( id, name, false, Collections.< RtmNote > emptyList() );
   }
   
   
   
   private static RtmTask newTaskWithNotes( String id, RtmNote... notes )
   {
      return newTask( id, "TestTask", false, Arrays.asList( notes ) );
   }
   
   
   
   private static RtmTask newTask( String id,
                                   String name,
                                   boolean deleted,
                                   Collection< RtmNote > notes )
   {
      return new RtmTask( id,
                          TASKSERIES_ID,
                          TestConstants.NOW,
                          TestConstants.NOW,
                          TestConstants.NOW,
                          deleted ? TestConstants.LATER : TestConstants.NEVER,
                          LIST_ID,
                          "locationId",
                          name,
                          "source",
                          "url",
                          TestConstants.NEVER,
                          Priority.High,
                          2,
                          TestConstants.EVEN_LATER,
                          true,
                          null,
                          false,
                          "2 hours",
                          Arrays.asList( "tag1", "tag2" ),
                          new ArrayList< RtmNote >( notes ),
                          null );
   }
   
   
   private interface RtmFunc
   {
      void callRtmMethod( IRtmContentEditService service,
                          IModification modification,
                          RtmResponse< List< RtmTask > > response ) throws RtmServiceException;
   }
}
