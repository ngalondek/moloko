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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.test.TestConstants;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.sync.RtmContactsSyncHandler;
import dev.drsoran.rtm.sync.RtmContentSort;
import dev.drsoran.rtm.sync.RtmSyncResult;


public class RtmContactsSyncHandlerFixture
{
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmContactsSyncHandler()
   {
      new RtmContactsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                  EasyMock.createNiceMock( Comparator.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = IllegalArgumentException.class )
   public void testRtmContactsSyncHandler_NullSyncPartner()
   {
      new RtmContactsSyncHandler( null,
                                  EasyMock.createNiceMock( Comparator.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmContactsSyncHandler_NullComparator()
   {
      new RtmContactsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                  null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testHandleIncomingSync_NullRepo() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final RtmContactsSyncHandler syncHandler = new RtmContactsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                                                             EasyMock.createNiceMock( Comparator.class ) );
      
      syncHandler.handleIncomingSync( null, TestConstants.NEVER );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_RtmError() throws RtmServiceException
   {
      final IRtmSyncPartner syncPartner = EasyMock.createNiceMock( IRtmSyncPartner.class );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.contacts_getList() )
              .andThrow( new RtmServiceException( "" ) );
      EasyMock.replay( rtmRepo );
      
      final RtmContactsSyncHandler syncHandler = new RtmContactsSyncHandler( syncPartner,
                                                                             RtmContentSort.getRtmContactIdSort() );
      
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
      EasyMock.expect( syncPartner.getContacts() )
              .andReturn( Collections.< RtmContact > emptyList() );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.contacts_getList() )
              .andReturn( Collections.< RtmContact > emptyList() );
      EasyMock.replay( rtmRepo );
      
      final RtmContactsSyncHandler syncHandler = new RtmContactsSyncHandler( syncPartner,
                                                                             RtmContentSort.getRtmContactIdSort() );
      
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
      final RtmContact contactFromRtm = newContact( "1" );
      
      final List< RtmContact > partnerList = new ArrayList< RtmContact >();
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getContacts() ).andReturn( partnerList );
      syncPartner.insertContact( contactFromRtm );
      EasyMock.replay( syncPartner );
      
      final List< RtmContact > rtmList = new ArrayList< RtmContact >();
      rtmList.add( contactFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.contacts_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmContactsSyncHandler syncHandler = new RtmContactsSyncHandler( syncPartner,
                                                                             RtmContentSort.getRtmContactIdSort() );
      
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
      final RtmContact contactFromPartner = newContact( "1" );
      final RtmContact contactFromRtm = newContact( "1" );
      
      final List< RtmContact > partnerList = new ArrayList< RtmContact >();
      partnerList.add( contactFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getContacts() ).andReturn( partnerList );
      syncPartner.updateContact( contactFromPartner, contactFromRtm );
      EasyMock.replay( syncPartner );
      
      final List< RtmContact > rtmList = new ArrayList< RtmContact >();
      rtmList.add( contactFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.contacts_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmContactsSyncHandler syncHandler = new RtmContactsSyncHandler( syncPartner,
                                                                             RtmContentSort.getRtmContactIdSort() );
      
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
      final RtmContact contact1FromPartner = newContact( "1" );
      final RtmContact contact2FromPartner = newContact( "2" );
      final RtmContact contact1FromRtm = newContact( "1" );
      
      final List< RtmContact > partnerList = new ArrayList< RtmContact >();
      partnerList.add( contact2FromPartner );
      partnerList.add( contact1FromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getContacts() ).andReturn( partnerList );
      syncPartner.updateContact( contact1FromPartner, contact1FromRtm );
      syncPartner.deleteContact( contact2FromPartner );
      EasyMock.replay( syncPartner );
      
      final List< RtmContact > rtmList = new ArrayList< RtmContact >();
      rtmList.add( contact1FromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.contacts_getList() ).andReturn( rtmList );
      EasyMock.replay( rtmRepo );
      
      final RtmContactsSyncHandler syncHandler = new RtmContactsSyncHandler( syncPartner,
                                                                             RtmContentSort.getRtmContactIdSort() );
      
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
      final RtmContactsSyncHandler syncHandler = new RtmContactsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                                                                             EasyMock.createNiceMock( Comparator.class ) );
      
      syncHandler.handleOutgoingSync( EasyMock.createNiceMock( IRtmContentEditService.class ),
                                      "1",
                                      TestConstants.NEVER );
   }
   
   
   
   private RtmContact newContact( String id )
   {
      return new RtmContact( id, "user", "full" );
   }
}
