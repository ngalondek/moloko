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

package dev.drsoran.rtm.test.unit.sync;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.sync.RtmContentSort;
import dev.drsoran.rtm.sync.RtmLocationsSyncHandler;
import dev.drsoran.rtm.sync.RtmSyncResult;
import dev.drsoran.rtm.test.TestConstants;


public class RtmLocationsSyncHandlerFixture
{
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmLocationsSyncHandler()
   {
      new RtmLocationsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                   EasyMock.createNiceMock( Comparator.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testRtmLocationsSyncHandler_NullSyncPartner()
   {
      new RtmLocationsSyncHandler( null,
                                   EasyMock.createNiceMock( Comparator.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmLocationsSyncHandler_NullComparator()
   {
      new RtmLocationsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                   null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testHandleIncomingSync_NullRepo() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final RtmLocationsSyncHandler syncHandler = new RtmLocationsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                                                               EasyMock.createNiceMock( Comparator.class ) );
      
      syncHandler.handleIncomingSync( null, TestConstants.NEVER );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_RtmError() throws RtmServiceException
   {
      final IRtmSyncPartner syncPartner = EasyMock.createNiceMock( IRtmSyncPartner.class );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.locations_getList() )
              .andThrow( new RtmServiceException( "" ) );
      EasyMock.replay( rtmRepo );
      
      final RtmLocationsSyncHandler syncHandler = new RtmLocationsSyncHandler( syncPartner,
                                                                               RtmContentSort.getRtmLocationIdSort() );
      
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
      EasyMock.expect( syncPartner.getLocations() )
              .andReturn( Collections.< RtmLocation > emptyList() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.locations_getList() )
              .andReturn( Collections.< RtmLocation > emptyList() );
      EasyMock.replay( rtmRepo );
      
      final RtmLocationsSyncHandler syncHandler = new RtmLocationsSyncHandler( syncPartner,
                                                                               RtmContentSort.getRtmLocationIdSort() );
      
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
      final RtmLocation locationFromRtm = newLocation( "1" );
      
      final List< RtmLocation > partnerList = new ArrayList< RtmLocation >();
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getLocations() ).andReturn( partnerList );
      syncPartner.insertLocation( locationFromRtm );
      EasyMock.replay( syncPartner );
      
      final List< RtmLocation > rtmList = new ArrayList< RtmLocation >();
      rtmList.add( locationFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.locations_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmLocationsSyncHandler syncHandler = new RtmLocationsSyncHandler( syncPartner,
                                                                               RtmContentSort.getRtmLocationIdSort() );
      
      final RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
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
      final RtmLocation locationFromPartner = newLocation( "1" );
      final RtmLocation locationFromRtm = newLocation( "1" );
      
      final List< RtmLocation > partnerList = new ArrayList< RtmLocation >();
      partnerList.add( locationFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getLocations() ).andReturn( partnerList );
      syncPartner.updateLocation( locationFromPartner, locationFromRtm );
      EasyMock.replay( syncPartner );
      
      final List< RtmLocation > rtmList = new ArrayList< RtmLocation >();
      rtmList.add( locationFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.locations_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmLocationsSyncHandler syncHandler = new RtmLocationsSyncHandler( syncPartner,
                                                                               RtmContentSort.getRtmLocationIdSort() );
      
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
      final RtmLocation location1FromPartner = newLocation( "1" );
      final RtmLocation location2FromPartner = newLocation( "2" );
      final RtmLocation location1FromRtm = newLocation( "1" );
      
      final List< RtmLocation > partnerList = new ArrayList< RtmLocation >();
      partnerList.add( location2FromPartner );
      partnerList.add( location1FromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getLocations() ).andReturn( partnerList );
      syncPartner.updateLocation( location1FromPartner, location1FromRtm );
      syncPartner.deleteLocation( location2FromPartner );
      EasyMock.replay( syncPartner );
      
      final List< RtmLocation > rtmList = new ArrayList< RtmLocation >();
      rtmList.add( location1FromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.locations_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmLocationsSyncHandler syncHandler = new RtmLocationsSyncHandler( syncPartner,
                                                                               RtmContentSort.getRtmLocationIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
                                                                    TestConstants.NEVER );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_Insert_Update_DeleteUntouched() throws RtmServiceException
   {
      final RtmLocation location2FromPartner = newLocation( "2" );
      final RtmLocation location3FromPartner = newLocation( "3" );
      
      final RtmLocation location1FromRtm = newLocation( "1" );
      final RtmLocation location2FromRtm = newLocation( "2" );
      
      final List< RtmLocation > partnerList = new ArrayList< RtmLocation >();
      partnerList.add( location2FromPartner );
      partnerList.add( location3FromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getLocations() ).andReturn( partnerList );
      syncPartner.insertLocation( location1FromRtm );
      syncPartner.updateLocation( location2FromPartner, location2FromRtm );
      syncPartner.deleteLocation( location3FromPartner );
      EasyMock.replay( syncPartner );
      
      final List< RtmLocation > rtmList = new ArrayList< RtmLocation >();
      rtmList.add( location1FromRtm );
      rtmList.add( location2FromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.locations_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmLocationsSyncHandler syncHandler = new RtmLocationsSyncHandler( syncPartner,
                                                                               RtmContentSort.getRtmLocationIdSort() );
      
      RtmSyncResult rtmSyncResult = syncHandler.handleIncomingSync( rtmRepo,
                                                                    TestConstants.NEVER );
      
      assertThat( rtmSyncResult.hasSucceeded(), is( true ) );
      assertThat( rtmSyncResult.getException(), is( nullValue() ) );
      assertThat( rtmSyncResult.getTransactions().size(), is( 0 ) );
      
      EasyMock.verify( syncPartner );
      EasyMock.verify( rtmRepo );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testHandleOutgoingSync() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final RtmLocationsSyncHandler syncHandler = new RtmLocationsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                                                               EasyMock.createNiceMock( Comparator.class ) );
      
      syncHandler.handleOutgoingSync( EasyMock.createNiceMock( IRtmContentEditService.class ),
                                      "1",
                                      TestConstants.NEVER );
   }
   
   
   
   private RtmLocation newLocation( String id )
   {
      return new RtmLocation( id, "name", 1.0f, 2.0f, "addr", true, 10 );
   }
}
