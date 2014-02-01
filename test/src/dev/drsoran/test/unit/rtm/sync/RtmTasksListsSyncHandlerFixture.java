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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.test.TestConstants;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;
import dev.drsoran.rtm.service.RtmErrorCodes;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.sync.RtmContentSort;
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
   public void testHandleIncomingSync_BothEmpty() throws RtmServiceException
   {
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getAllTasksLists( RtmConstants.NO_TIME ) )
              .andReturn( new ArrayList< RtmTasksList >() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.lists_getList() )
              .andReturn( new ArrayList< RtmTasksList >() );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      syncHandler.handleIncomingSync( rtmRepo, TestConstants.NEVER );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_LocalEmpty_RtmNew() throws RtmServiceException
   {
      final RtmTasksList listFromRtm = newList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getAllTasksLists( RtmConstants.NO_TIME ) )
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
      
      syncHandler.handleIncomingSync( rtmRepo, TestConstants.NEVER );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_LocalEmpty_RtmDeleted() throws RtmServiceException
   {
      final RtmTasksList listFromRtm = newDeletedList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getAllTasksLists( RtmConstants.NO_TIME ) )
              .andReturn( partnerList );
      EasyMock.replay( syncPartner );
      
      final List< RtmTasksList > rtmList = new ArrayList< RtmTasksList >();
      rtmList.add( listFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.lists_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      syncHandler.handleIncomingSync( rtmRepo, TestConstants.NEVER );
      
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
      EasyMock.expect( syncPartner.getAllTasksLists( RtmConstants.NO_TIME ) )
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
      
      syncHandler.handleIncomingSync( rtmRepo, TestConstants.NEVER );
      
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
      EasyMock.expect( syncPartner.getAllTasksLists( RtmConstants.NO_TIME ) )
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
      
      syncHandler.handleIncomingSync( rtmRepo, TestConstants.NEVER );
      
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
      EasyMock.expect( syncPartner.getAllTasksLists( RtmConstants.NO_TIME ) )
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
      
      syncHandler.handleIncomingSync( rtmRepo, TestConstants.NEVER );
      
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
      EasyMock.expect( syncPartner.getAllTasksLists( TestConstants.NOW ) )
              .andReturn( new ArrayList< RtmTasksList >() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.replay( rtmRepo );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      syncHandler.handleOutgoingSync( rtmEdit, "1000", TestConstants.NOW );
      
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
      EasyMock.expect( syncPartner.getAllTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      syncPartner.updateTasksList( listFromPartner, respList );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< RtmTasksList > rtmResponse = new RtmResponse< RtmTasksList >( respList );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_add( "1000", "TestList", null ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      syncHandler.handleOutgoingSync( rtmEdit, "1000", TestConstants.NOW );
      
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
      EasyMock.expect( syncPartner.getAllTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      syncHandler.handleOutgoingSync( rtmEdit, "1000", TestConstants.NOW );
      
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
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getAllTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      syncPartner.updateTasksList( listFromPartner, respList );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< RtmTasksList > rtmResponse = new RtmResponse< RtmTasksList >( respList );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_setName( "1000", "1", "NewName" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      syncHandler.handleOutgoingSync( rtmEdit, "1000", TestConstants.NOW );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_UpdateNotFound() throws RtmServiceException
   {
      final RtmTasksList listFromPartner = newList( "1", "NewName", false );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getAllTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_setName( "1000", "1", "NewName" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.LIST_INVALID_ID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      syncHandler.handleOutgoingSync( rtmEdit, "1000", TestConstants.NOW );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test( expected = RtmServiceException.class )
   public void testHandleOutgoingSync_Update_RtmError() throws RtmServiceException
   {
      final RtmTasksList listFromPartner = newList( "1", "NewName", false );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getAllTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_setName( "1000", "1", "NewName" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.LIST_NAME_INVALID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      try
      {
         syncHandler.handleOutgoingSync( rtmEdit, "1000", TestConstants.NOW );
      }
      finally
      {
         EasyMock.verify( syncPartner );
         EasyMock.verify( rtmEdit );
      }
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Delete() throws RtmServiceException
   {
      final RtmTasksList listFromPartner = newDeletedList( "1" );
      final RtmTasksList respList = newDeletedList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getAllTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      syncPartner.updateTasksList( listFromPartner, respList );
      EasyMock.replay( syncPartner );
      
      final RtmResponse< RtmTasksList > rtmResponse = new RtmResponse< RtmTasksList >( respList );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_delete( "1000", "1" ) )
              .andReturn( rtmResponse );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      syncHandler.handleOutgoingSync( rtmEdit, "1000", TestConstants.NOW );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
   }
   
   
   
   @Test
   public void testHandleOutgoingSync_Delete_NotFound() throws RtmServiceException
   {
      final RtmTasksList listFromPartner = newDeletedList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getAllTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_delete( "1000", "1" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.LIST_INVALID_ID,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      try
      {
         syncHandler.handleOutgoingSync( rtmEdit, "1000", TestConstants.NOW );
      }
      finally
      {
         EasyMock.verify( syncPartner );
         EasyMock.verify( rtmEdit );
      }
   }
   
   
   
   @Test( expected = RtmServiceException.class )
   public void testHandleOutgoingSync_Delete_RtmError() throws RtmServiceException
   {
      final RtmTasksList listFromPartner = newDeletedList( "1" );
      
      final List< RtmTasksList > partnerList = new ArrayList< RtmTasksList >();
      partnerList.add( listFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getAllTasksLists( TestConstants.NOW ) )
              .andReturn( partnerList );
      EasyMock.replay( syncPartner );
      
      final IRtmContentEditService rtmEdit = EasyMock.createMock( IRtmContentEditService.class );
      EasyMock.expect( rtmEdit.lists_delete( "1000", "1" ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.SERVICE_UNAVAILABLE,
                                                  "" ) );
      EasyMock.replay( rtmEdit );
      
      final RtmTasksListsSyncHandler syncHandler = new RtmTasksListsSyncHandler( syncPartner,
                                                                                 RtmContentSort.getRtmTasksListIdSort() );
      
      syncHandler.handleOutgoingSync( rtmEdit, "1000", TestConstants.NOW );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmEdit );
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
