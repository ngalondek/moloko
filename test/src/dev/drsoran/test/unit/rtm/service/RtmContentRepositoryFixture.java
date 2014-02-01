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

package dev.drsoran.test.unit.rtm.service;

import static dev.drsoran.moloko.test.IterableAsserts.assertCount;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.test.TestConstants;
import dev.drsoran.rtm.IRtmConnection;
import dev.drsoran.rtm.IRtmConnectionFactory;
import dev.drsoran.rtm.IRtmResponseHandler;
import dev.drsoran.rtm.IRtmResponseHandlerFactory;
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmSettings;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.service.RtmContentRepository;


public class RtmContentRepositoryFixture
{
   
   @Test
   public void RtmContentRepository()
   {
      new RtmContentRepository( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmContentRepository_NullConnectionFacory()
   {
      new RtmContentRepository( null,
                                EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmContentRepository_NullResponseHandlerFactory()
   {
      new RtmContentRepository( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testContacts_getList() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmContact > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmContactsResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.contacts.getList" ) ) )
              .andReturn( new RtmResponse< List< RtmContact > >( Collections.singletonList( new RtmContact( "1",
                                                                                                            "userName",
                                                                                                            "fullName" ) ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentRepository repo = new RtmContentRepository( connFact,
                                                                  responseHandlerFactory );
      final List< RtmContact > respElement = repo.contacts_getList();
      
      assertThat( respElement, is( notNullValue() ) );
      assertCount( respElement, 1 );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testLists_getList() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTasksList > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTaskListsResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.lists.getList" ) ) )
              .andReturn( new RtmResponse< List< RtmTasksList > >( Collections.singletonList( new RtmTasksList( "1",
                                                                                                                0,
                                                                                                                false,
                                                                                                                false,
                                                                                                                false,
                                                                                                                "name",
                                                                                                                null ) ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentRepository repo = new RtmContentRepository( connFact,
                                                                  responseHandlerFactory );
      final List< RtmTasksList > respElement = repo.lists_getList();
      
      assertThat( respElement, is( notNullValue() ) );
      assertCount( respElement, 1 );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_getList() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.getList" ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( Collections.singletonList( new RtmTask( "1",
                                                                                                      "2",
                                                                                                      10L,
                                                                                                      10L,
                                                                                                      10L,
                                                                                                      10L,
                                                                                                      "3",
                                                                                                      null,
                                                                                                      "name",
                                                                                                      "src",
                                                                                                      null,
                                                                                                      -1L,
                                                                                                      Priority.High,
                                                                                                      0,
                                                                                                      -1L,
                                                                                                      false,
                                                                                                      null,
                                                                                                      false,
                                                                                                      null,
                                                                                                      Collections.< String > emptyList(),
                                                                                                      Collections.< RtmNote > emptyList(),
                                                                                                      Collections.< RtmContact > emptyList() ) ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentRepository repo = new RtmContentRepository( connFact,
                                                                  responseHandlerFactory );
      final List< RtmTask > respElement = repo.tasks_getList( RtmConstants.NO_TIME );
      
      assertThat( respElement, is( notNullValue() ) );
      assertCount( respElement, 1 );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_getList_LastSyncTime() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.getList" ),
                                                    EasyMock.eq( new Param( "last_sync",
                                                                            TestConstants.NOW ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask >>( Collections.< RtmTask > emptyList() ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentRepository repo = new RtmContentRepository( connFact,
                                                                  responseHandlerFactory );
      final List< RtmTask > respElement = repo.tasks_getList( TestConstants.NOW );
      
      assertThat( respElement, is( notNullValue() ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_getListByFilter() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.getList" ),
                                                    EasyMock.eq( new Param( "filter",
                                                                            "name:Test" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask >>( Collections.< RtmTask > emptyList() ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentRepository repo = new RtmContentRepository( connFact,
                                                                  responseHandlerFactory );
      final List< RtmTask > respElement = repo.tasks_getListByFilter( "name:Test",
                                                                      RtmConstants.NO_TIME );
      
      assertThat( respElement, is( notNullValue() ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_getListByFilter_LastSyncTime() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.getList" ),
                                                    EasyMock.eq( new Param( "filter",
                                                                            "name:Test" ) ),
                                                    EasyMock.eq( new Param( "last_sync",
                                                                            TestConstants.NOW ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask >>( Collections.< RtmTask > emptyList() ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentRepository repo = new RtmContentRepository( connFact,
                                                                  responseHandlerFactory );
      final List< RtmTask > respElement = repo.tasks_getListByFilter( "name:Test",
                                                                      TestConstants.NOW );
      assertThat( respElement, is( notNullValue() ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_getListByFilter_nullFilter() throws RtmServiceException
   {
      final RtmContentRepository repo = new RtmContentRepository( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                                                  EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
      repo.tasks_getListByFilter( null, TestConstants.NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_getListByFilter_emptyFilter() throws RtmServiceException
   {
      final RtmContentRepository repo = new RtmContentRepository( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                                                  EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
      repo.tasks_getListByFilter( "", TestConstants.NOW );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_getListByListId() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.getList" ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "1" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask >>( Collections.< RtmTask > emptyList() ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentRepository repo = new RtmContentRepository( connFact,
                                                                  responseHandlerFactory );
      final List< RtmTask > respElement = repo.tasks_getListByListId( "1",
                                                                      TestConstants.NEVER );
      assertThat( respElement, is( notNullValue() ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_getListByListId_LastSyncTime() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.getList" ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "last_sync",
                                                                            TestConstants.NOW ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask >>( Collections.< RtmTask > emptyList() ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentRepository repo = new RtmContentRepository( connFact,
                                                                  responseHandlerFactory );
      final List< RtmTask > respElement = repo.tasks_getListByListId( "1",
                                                                      TestConstants.NOW );
      assertThat( respElement, is( notNullValue() ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_getListByListId_nullId() throws RtmServiceException
   {
      final RtmContentRepository repo = new RtmContentRepository( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                                                  EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
      repo.tasks_getListByListId( null, TestConstants.NOW );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_getListByListId_emptyId() throws RtmServiceException
   {
      final RtmContentRepository repo = new RtmContentRepository( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                                                  EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
      repo.tasks_getListByListId( "", TestConstants.NOW );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testLocations_getList() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmLocation > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmLocationsResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.locations.getList" ) ) )
              .andReturn( new RtmResponse< List< RtmLocation > >( Collections.singletonList( new RtmLocation( "1",
                                                                                                              "name",
                                                                                                              1.0f,
                                                                                                              2.0f,
                                                                                                              "addr",
                                                                                                              true,
                                                                                                              10 ) ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentRepository repo = new RtmContentRepository( connFact,
                                                                  responseHandlerFactory );
      final List< RtmLocation > respElement = repo.locations_getList();
      
      assertThat( respElement, is( notNullValue() ) );
      assertCount( respElement, 1 );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test
   public void testSettings_getList() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final IRtmResponseHandler< RtmSettings > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmSettingsResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.settings.getList" ) ) )
              .andReturn( new RtmResponse< RtmSettings >( new RtmSettings( 10L,
                                                                           "tz",
                                                                           0,
                                                                           1,
                                                                           "1",
                                                                           "de" ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentRepository repo = new RtmContentRepository( connFact,
                                                                  responseHandlerFactory );
      final RtmSettings respElement = repo.settings_getList();
      
      assertThat( respElement, is( notNullValue() ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
}
