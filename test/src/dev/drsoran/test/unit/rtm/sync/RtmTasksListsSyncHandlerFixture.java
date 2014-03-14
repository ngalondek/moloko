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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.test.TestConstants;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.content.ContentProperties.RtmTasksListProperties;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;
import dev.drsoran.rtm.service.RtmErrorCodes;
import dev.drsoran.rtm.sync.IModification;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.sync.RtmContentSort;
import dev.drsoran.rtm.sync.RtmSyncResult;
import dev.drsoran.rtm.sync.RtmTasksListsSyncHandler;


public class RtmTasksListsSyncHandlerFixture
{
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmTasksListsSyncHandler()
   {
      new RtmTasksListsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                    EasyMock.createNiceMock( Comparator.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTasksListsSyncHandler_NullSyncPartner()
   {
      new RtmTasksListsSyncHandler( null,
                                    EasyMock.createNiceMock( Comparator.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmTasksListsSyncHandler_NullComparator()
   {
      new RtmTasksListsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                    null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testHandleIncomingSync_NullRepo() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                                                                 EasyMock.createNiceMock( Comparator.class ) );
      
      syncHandler.handleIncomingSync( null, TestConstants.NEVER );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_RtmError() throws RtmServiceException
   {
      final IRtmSyncPartner syncPartner = EasyMock.createNiceMock( IRtmSyncPartner.class );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.lists_getList() )
              .andThrow( new RtmServiceException( "" ) );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
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
      EasyMock.expect( syncPartner.getTasksLists( RtmConstants.NO_TIME ) )
              .andReturn( new ArrayList< RtmTasksList >() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.lists_getList() )
              .andReturn( new ArrayList< RtmTasksList >() );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
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
      final RtmTasksList listFromRtm = newList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( RtmConstants.NO_TIME ) )
              .andReturn( partnerList );
      syncPartner.insertTasksList( listFromRtm );
      EasyMock.replay( syncPartner );
      
      final List< RtmTasksList > rtmList = new ArrayList< RtmTasksList >();
      rtmList.add( listFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.lists_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
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
      final RtmTasksList listFromRtm = newDeletedList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( RtmConstants.NO_TIME ) )
              .andReturn( partnerList );
      EasyMock.replay( syncPartner );
      
      final List< RtmTasksList > rtmList = new ArrayList< RtmTasksList >();
      rtmList.add( listFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.lists_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
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
      final RtmTasksList listFromPartner = newList( "1" );
      final RtmTasksList listFromRtm = newList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( RtmConstants.NO_TIME ) )
              .andReturn( partnerList );
      syncPartner.updateTasksList( listFromPartner, listFromRtm );
      EasyMock.replay( syncPartner );
      
      final List< RtmTasksList > rtmList = new ArrayList< RtmTasksList >();
      rtmList.add( listFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.lists_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
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
      final RtmTasksList listFromPartner = newList( "1" );
      final RtmTasksList listFromRtm = newDeletedList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( RtmConstants.NO_TIME ) )
              .andReturn( partnerList );
      syncPartner.deleteTasksList( listFromPartner );
      EasyMock.replay( syncPartner );
      
      final List< RtmTasksList > rtmList = new ArrayList< RtmTasksList >();
      rtmList.add( listFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.lists_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
                                                                    TestConstants.NEVER );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_DeleteUntouched() throws RtmServiceException
   {
      final RtmTasksList list1FromPartner = newList( "1" );
      final RtmTasksList list2FromPartner = newList( "2" );
      final RtmTasksList list1FromRtm = newList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( list2FromPartner );
      partnerList.add( list1FromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( RtmConstants.NO_TIME ) )
              .andReturn( partnerList );
      syncPartner.updateTasksList( list1FromPartner, list1FromRtm );
      syncPartner.deleteTasksList( list2FromPartner );
      EasyMock.replay( syncPartner );
      
      final List< RtmTasksList > rtmList = new ArrayList< RtmTasksList >();
      rtmList.add( list1FromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.lists_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
                                                                    TestConstants.NEVER );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_Insert_Update_Delete_DeleteUntouched() throws RtmServiceException
   {
      final RtmTasksList list2FromPartner = newList( "2" );
      final RtmTasksList list3FromPartner = newList( "3" );
      final RtmTasksList list4FromPartner = newList( "4" );
      
      final RtmTasksList list1FromRtm = newList( "1" );
      final RtmTasksList list2FromRtm = newList( "2" );
      final RtmTasksList list3FromRtm = newDeletedList( "3" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( list2FromPartner );
      partnerList.add( list3FromPartner );
      partnerList.add( list4FromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( RtmConstants.NO_TIME ) )
              .andReturn( partnerList );
      syncPartner.insertTasksList( list1FromRtm );
      syncPartner.updateTasksList( list2FromPartner, list2FromRtm );
      syncPartner.deleteTasksList( list3FromPartner );
      syncPartner.deleteTasksList( list4FromPartner );
      EasyMock.replay( syncPartner );
      
      final List< RtmTasksList > rtmList = new ArrayList< RtmTasksList >();
      rtmList.add( list1FromRtm );
      rtmList.add( list2FromRtm );
      rtmList.add( list3FromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.lists_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
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
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                                                                 EasyMock.createNiceMock( Comparator.class ) );
      
      syncHandler.handleOutgoingSync( null, "1", TestConstants.EVEN_LATER );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testHandleOutgoingSync_NullTimelineId() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                                                                 EasyMock.createNiceMock( Comparator.class ) );
      
      syncHandler.handleOutgoingSync( EasyMock.createNiceMock( IRtmContentEditService.class ),
                                      null,
                                      TestConstants.EVEN_LATER );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Empty() throws RtmServiceException
   {
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( new ArrayList< RtmTasksList >() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.replay( rtmRepo );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
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
      final RtmTasksList listFromPartner = newList( RtmConstants.NO_ID );
      final RtmTasksList respList = newList( "2" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      syncPartner.updateTasksList( listFromPartner, respList );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< RtmTasksList > rtmResponse = new RtmResponse< RtmTasksList >( new RtmTransaction( "50",
                                                                                                           true ),
                                                                                       respList );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_add( "1000", "TestList", null ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    "1000",
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
      final RtmTasksList listFromPartner = newDeletedList( RtmConstants.NO_ID );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
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
   public void testHandleOutgoingSync_Update() throws RtmServiceException
   {
      final RtmTasksList listFromPartner = newList( "1", "NewName", false );
      final RtmTasksList respList = newList( "1", "NewName", false );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IModification nameMod = EasyMock.createStrictMock( IModification.class );
      EasyMock.expect( nameMod.getPropertyName() )
              .andReturn( RtmTasksListProperties.NAME );
      EasyMock.replay( nameMod );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.expect( syncPartner.getModificationsOfTasksList( "1" ) )
              .andReturn( Arrays.asList( nameMod ) );
      syncPartner.updateTasksList( listFromPartner, respList );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< RtmTasksList > rtmResponse = new RtmResponse< RtmTasksList >( new RtmTransaction( "50",
                                                                                                           true ),
                                                                                       respList );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_setName( "1000", "1", "NewName" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    "1000",
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 1 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
      EasyMock.verify( nameMod );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Update_UnhandledModification() throws RtmServiceException
   {
      final RtmTasksList listFromPartner = newList( "1", "NewName", false );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IModification nameMod = EasyMock.createStrictMock( IModification.class );
      EasyMock.expect( nameMod.getPropertyName() )
              .andReturn( RtmTasksListProperties.ARCHIVED );
      EasyMock.replay( nameMod );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.expect( syncPartner.getModificationsOfTasksList( "1" ) )
              .andReturn( Arrays.asList( nameMod ) );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    "1000",
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
      final RtmTasksList listFromPartner = newList( "1", "NewName", false );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.expect( syncPartner.getModificationsOfTasksList( "1" ) )
              .andReturn( Collections.< IModification > emptyList() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
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
   public void testHandleOutgoingSync_Update_NotFoundAtRTM() throws RtmServiceException
   {
      final RtmTasksList listFromPartner = newList( "1", "NewName", false );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IModification nameMod = EasyMock.createStrictMock( IModification.class );
      EasyMock.expect( nameMod.getPropertyName() )
              .andReturn( RtmTasksListProperties.NAME );
      EasyMock.replay( nameMod );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.expect( syncPartner.getModificationsOfTasksList( "1" ) )
              .andReturn( Arrays.asList( nameMod ) );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_setName( "1000", "1", "NewName" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.LIST_INVALID_ID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    "1000",
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
      final RtmTasksList listFromPartner = newList( "1", "NewName", false );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IModification nameMod = EasyMock.createStrictMock( IModification.class );
      EasyMock.expect( nameMod.getPropertyName() )
              .andReturn( RtmTasksListProperties.NAME );
      EasyMock.replay( nameMod );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.expect( syncPartner.getModificationsOfTasksList( "1" ) )
              .andReturn( Arrays.asList( nameMod ) );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_setName( "1000", "1", "NewName" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.LIST_NAME_INVALID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    "1000",
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
      final RtmTasksList listFromPartner = newDeletedList( "1" );
      final RtmTasksList respList = newDeletedList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      syncPartner.updateTasksList( listFromPartner, respList );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< RtmTasksList > rtmResponse = new RtmResponse< RtmTasksList >( new RtmTransaction( "50",
                                                                                                           true ),
                                                                                       respList );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_delete( "1000", "1" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    "1000",
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
      final RtmTasksList listFromPartner = newDeletedList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_delete( "1000", "1" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.LIST_INVALID_ID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
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
   public void testHandleOutgoingSync_Delete_OtherRTMError() throws RtmServiceException
   {
      final RtmTasksList listFromPartner = newDeletedList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_delete( "1000", "1" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.SERVICE_UNAVAILABLE,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    "1000",
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
      final RtmTasksList listFromPartner = newDeletedList( "1" );
      final RtmTasksList respList = newDeletedList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createNiceMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      syncPartner.updateTasksList( listFromPartner, respList );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< RtmTasksList > rtmResponse = new RtmResponse< RtmTasksList >( new RtmTransaction( "50",
                                                                                                           false ),
                                                                                       respList );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_delete( "1000", "1" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleOutgoingSync( rtmEdit,
                                                                    "1000",
                                                                    TestConstants.NOW );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
   }
   
   
   
   private static RtmTasksList newList( String id )
   {
      return newList( id, "TestList", false );
   }
   
   
   
   private static RtmTasksList newDeletedList( String id )
   {
      return newList( id, "TestList", true );
   }
   
   
   
   private static RtmTasksList newList( String id, String name, boolean deleted )
   {
      return new RtmTasksList( id, 0, deleted, false, false, name, null );
   }
}
