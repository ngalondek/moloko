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
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.sync.RtmSettingsSyncHandler;
import dev.drsoran.rtm.sync.RtmSyncResult;
import dev.drsoran.rtm.test.TestConstants;


public class RtmSettingsSyncHandlerFixture
{
   @Test
   public void testRtmContactsSyncHandler()
   {
      new RtmSettingsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmContactsSyncHandler_NullSyncPartner()
   {
      new RtmSettingsSyncHandler( null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testHandleIncomingSync_NullContentRepository()
   {
      new RtmSettingsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ) ).handleIncomingSync( null,
                                                                                                         TestConstants.NEVER );
   }
   
   
   
   @Test
   public void testHandleIncomingSync_RtmError() throws RtmServiceException
   {
      final IRtmSyncPartner syncPartner = EasyMock.createNiceMock( IRtmSyncPartner.class );
      EasyMock.replay( syncPartner );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.settings_getList() )
              .andThrow( new RtmServiceException( "" ) );
      EasyMock.replay( rtmRepo );
      
      final RtmSettingsSyncHandler syncHandler = new RtmSettingsSyncHandler( syncPartner );
      
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
   public void testHandleIncomingSync_Update() throws RtmServiceException
   {
      final RtmSettings settingsFromPartner = newSettings();
      final RtmSettings settingsFromRtm = newSettings();
      
      final List< RtmSettings > partnerList = new ArrayList< RtmSettings >();
      partnerList.add( settingsFromPartner );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      EasyMock.expect( syncPartner.getSettings() )
              .andReturn( settingsFromPartner );
      syncPartner.updateSettings( settingsFromPartner, settingsFromRtm );
      EasyMock.replay( syncPartner );
      
      final List< RtmSettings > rtmList = new ArrayList< RtmSettings >();
      rtmList.add( settingsFromRtm );
      
      final IRtmContentRepository rtmRepo = EasyMock.createMock( IRtmContentRepository.class );
      EasyMock.expect( rtmRepo.settings_getList() ).andReturn( settingsFromRtm );
      EasyMock.replay( rtmRepo );
      
      final RtmSettingsSyncHandler syncHandler = new RtmSettingsSyncHandler( syncPartner );
      
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
      final RtmSettingsSyncHandler syncHandler = new RtmSettingsSyncHandler( EasyMock.createNiceMock( IRtmSyncPartner.class ) );
      
      syncHandler.handleOutgoingSync( EasyMock.createNiceMock( IRtmContentEditService.class ),
                                      "1",
                                      TestConstants.NEVER );
   }
   
   
   
   private RtmSettings newSettings()
   {
      return new RtmSettings( TestConstants.NOW, "timezone", 0, 0, null, "lang" );
   }
}
