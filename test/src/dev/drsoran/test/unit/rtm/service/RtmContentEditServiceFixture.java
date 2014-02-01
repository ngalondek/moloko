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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
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
import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.model.RtmTimeline;
import dev.drsoran.rtm.service.RtmContentEditService;


public class RtmContentEditServiceFixture
{
   @Test
   public void testRtmContentEditService()
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmContentEditService_NullConnectionFacory()
   {
      new RtmContentEditService( null,
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmContentEditService_NullResponseHandlerFactory()
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 null );
   }
   
   
   
   @Test
   public void testLists_add_notSmart() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final IRtmResponseHandler< RtmTasksList > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTaskListResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.lists.add" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "name",
                                                                            "listName" ) ) ) )
              .andReturn( new RtmResponse< RtmTasksList >( new RtmTransaction( "1000",
                                                                               true ),
                                                           new RtmTasksList( "10",
                                                                             0,
                                                                             false,
                                                                             false,
                                                                             false,
                                                                             "listName",
                                                                             null ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< RtmTasksList > response = service.lists_add( "1",
                                                                      "listName",
                                                                      null );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test
   public void testLists_add_Smart() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final IRtmResponseHandler< RtmTasksList > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTaskListResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.lists.add" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "name",
                                                                            "listName" ) ),
                                                    EasyMock.eq( new Param( "filter",
                                                                            "name:Test" ) ) ) )
              .andReturn( new RtmResponse< RtmTasksList >( new RtmTransaction( "1000",
                                                                               true ),
                                                           new RtmTasksList( "10",
                                                                             0,
                                                                             false,
                                                                             false,
                                                                             false,
                                                                             "listName",
                                                                             null ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< RtmTasksList > response = service.lists_add( "1",
                                                                      "listName",
                                                                      "name:Test" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLists_add_Smart_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).lists_add( null,
                                                                                                          "listName",
                                                                                                          null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLists_add_Smart_NullListName() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).lists_add( "1",
                                                                                                          null,
                                                                                                          null );
   }
   
   
   
   @Test
   public void testLists_delete() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final IRtmResponseHandler< RtmTasksList > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTaskListResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.lists.delete" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ) ) )
              .andReturn( new RtmResponse< RtmTasksList >( new RtmTransaction( "1000",
                                                                               true ),
                                                           new RtmTasksList( "10",
                                                                             0,
                                                                             true,
                                                                             false,
                                                                             false,
                                                                             "listName",
                                                                             null ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< RtmTasksList > response = service.lists_delete( "1",
                                                                         "10" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLists_delete_Smart_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).lists_delete( null,
                                                                                                             "1" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLists_delete_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).lists_delete( "1",
                                                                                                             null );
   }
   
   
   
   @Test
   public void testLists_setName() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final IRtmResponseHandler< RtmTasksList > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTaskListResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.lists.setName" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "name",
                                                                            "newName" ) ) ) )
              .andReturn( new RtmResponse< RtmTasksList >( new RtmTransaction( "1000",
                                                                               true ),
                                                           new RtmTasksList( "10",
                                                                             0,
                                                                             false,
                                                                             false,
                                                                             false,
                                                                             "listName",
                                                                             null ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< RtmTasksList > response = service.lists_setName( "1",
                                                                          "10",
                                                                          "newName" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLists_setName_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).lists_setName( null,
                                                                                                              "1",
                                                                                                              "name" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLists_setName_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).lists_setName( "1",
                                                                                                              null,
                                                                                                              "name" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLists_setName_NullName() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).lists_setName( "1",
                                                                                                              "2",
                                                                                                              null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_add() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.add" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "name",
                                                                            "taskName" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "1000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_add( "1",
                                                                        "10",
                                                                        "taskName" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_add_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_add( null,
                                                                                                          "1",
                                                                                                          "name" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_add_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_add( "1",
                                                                                                          null,
                                                                                                          "name" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_add_NullName() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_add( "1",
                                                                                                          "2",
                                                                                                          null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_complete() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.complete" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_complete( "1",
                                                                             "10",
                                                                             "100",
                                                                             "1000" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_complete_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_complete( null,
                                                                                                               "1",
                                                                                                               "2",
                                                                                                               "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_complete_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_complete( "1",
                                                                                                               null,
                                                                                                               "2",
                                                                                                               "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_complete_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_complete( "1",
                                                                                                               "2",
                                                                                                               null,
                                                                                                               "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_complete_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_complete( "1",
                                                                                                               "2",
                                                                                                               "3",
                                                                                                               null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_uncomplete() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.uncomplete" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_uncomplete( "1",
                                                                               "10",
                                                                               "100",
                                                                               "1000" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_uncomplete_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_uncomplete( null,
                                                                                                                 "1",
                                                                                                                 "2",
                                                                                                                 "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_uncomplete_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_uncomplete( "1",
                                                                                                                 null,
                                                                                                                 "2",
                                                                                                                 "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_uncomplete_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_uncomplete( "1",
                                                                                                                 "2",
                                                                                                                 null,
                                                                                                                 "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_uncomplete_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_uncomplete( "1",
                                                                                                                 "2",
                                                                                                                 "3",
                                                                                                                 null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_delete() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.delete" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_delete( "1",
                                                                           "10",
                                                                           "100",
                                                                           "1000" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_delete_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_delete( null,
                                                                                                             "1",
                                                                                                             "2",
                                                                                                             "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_delete_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_delete( "1",
                                                                                                             null,
                                                                                                             "2",
                                                                                                             "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_delete_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_delete( "1",
                                                                                                             "2",
                                                                                                             null,
                                                                                                             "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_delete_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_delete( "1",
                                                                                                             "2",
                                                                                                             "3",
                                                                                                             null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_movePriority_up() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.movePriority" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "direction",
                                                                            "up" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_movePriority( "1",
                                                                                 "10",
                                                                                 "100",
                                                                                 "1000",
                                                                                 true );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_movePriority_down() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.movePriority" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "direction",
                                                                            "down" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_movePriority( "1",
                                                                                 "10",
                                                                                 "100",
                                                                                 "1000",
                                                                                 false );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_movePriority_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_movePriority( null,
                                                                                                                   "1",
                                                                                                                   "2",
                                                                                                                   "3",
                                                                                                                   true );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_movePriority_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_movePriority( "1",
                                                                                                                   null,
                                                                                                                   "2",
                                                                                                                   "3",
                                                                                                                   true );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_movePriority_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_movePriority( "1",
                                                                                                                   "2",
                                                                                                                   null,
                                                                                                                   "3",
                                                                                                                   true );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_movePriority_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_movePriority( "1",
                                                                                                                   "2",
                                                                                                                   "3",
                                                                                                                   null,
                                                                                                                   true );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_moveTo() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.moveTo" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "from_list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "to_list_id",
                                                                            "11" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_moveTo( "1",
                                                                           "10",
                                                                           "11",
                                                                           "100",
                                                                           "1000" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_moveTo_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_moveTo( null,
                                                                                                             "1",
                                                                                                             "2",
                                                                                                             "3",
                                                                                                             "4" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_moveTo_NullFromListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_moveTo( "1",
                                                                                                             null,
                                                                                                             "2",
                                                                                                             "3",
                                                                                                             "4" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_moveTo_NullToListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_moveTo( "1",
                                                                                                             "2",
                                                                                                             null,
                                                                                                             "3",
                                                                                                             "4" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_moveTo_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_moveTo( "1",
                                                                                                             "2",
                                                                                                             "3",
                                                                                                             null,
                                                                                                             "4" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_moveTo_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_moveTo( "1",
                                                                                                             "2",
                                                                                                             "3",
                                                                                                             "4",
                                                                                                             null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_postpone() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.postpone" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_postpone( "1",
                                                                             "10",
                                                                             "100",
                                                                             "1000" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_postpone_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_postpone( null,
                                                                                                               "1",
                                                                                                               "2",
                                                                                                               "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_postpone_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_postpone( "1",
                                                                                                               null,
                                                                                                               "2",
                                                                                                               "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_postpone_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_postpone( "1",
                                                                                                               "2",
                                                                                                               null,
                                                                                                               "3" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_postpone_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_postpone( "1",
                                                                                                               "2",
                                                                                                               "3",
                                                                                                               null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setDueDate_Clear() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setDueDate" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setDueDate( "1",
                                                                               "10",
                                                                               "100",
                                                                               "1000",
                                                                               RtmConstants.NO_TIME,
                                                                               false );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setDueDate_DateOnly() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setDueDate" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "due",
                                                                            TestConstants.NOW ) ),
                                                    EasyMock.eq( new Param( "has_due_time",
                                                                            "0" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setDueDate( "1",
                                                                               "10",
                                                                               "100",
                                                                               "1000",
                                                                               TestConstants.NOW,
                                                                               false );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setDueDate_DateAndTime() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setDueDate" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "due",
                                                                            TestConstants.NOW ) ),
                                                    EasyMock.eq( new Param( "has_due_time",
                                                                            "1" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setDueDate( "1",
                                                                               "10",
                                                                               "100",
                                                                               "1000",
                                                                               TestConstants.NOW,
                                                                               true );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setDueDate_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setDueDate( null,
                                                                                                                 "1",
                                                                                                                 "2",
                                                                                                                 "3",
                                                                                                                 -1,
                                                                                                                 false );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setDueDate_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setDueDate( "1",
                                                                                                                 null,
                                                                                                                 "2",
                                                                                                                 "3",
                                                                                                                 -1,
                                                                                                                 false );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setDueDate_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setDueDate( "1",
                                                                                                                 "2",
                                                                                                                 null,
                                                                                                                 "3",
                                                                                                                 -1,
                                                                                                                 false );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setDueDate_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setDueDate( "1",
                                                                                                                 "2",
                                                                                                                 "3",
                                                                                                                 null,
                                                                                                                 -1,
                                                                                                                 false );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setEstimate_Unset() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setEstimate" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setEstimate( "1",
                                                                                "10",
                                                                                "100",
                                                                                "1000",
                                                                                null );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setEstimate_Set() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setEstimate" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "estimate",
                                                                            "5 days" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setEstimate( "1",
                                                                                "10",
                                                                                "100",
                                                                                "1000",
                                                                                "5 days" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setEstimate_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setEstimate( null,
                                                                                                                  "1",
                                                                                                                  "2",
                                                                                                                  "3",
                                                                                                                  null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setEstimate_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setEstimate( "1",
                                                                                                                  null,
                                                                                                                  "2",
                                                                                                                  "3",
                                                                                                                  null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setEstimate_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setEstimate( "1",
                                                                                                                  "2",
                                                                                                                  null,
                                                                                                                  "3",
                                                                                                                  null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setEstimate_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setEstimate( "1",
                                                                                                                  "2",
                                                                                                                  "3",
                                                                                                                  null,
                                                                                                                  null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setName() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setName" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "name",
                                                                            "taskName" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setName( "1",
                                                                            "10",
                                                                            "100",
                                                                            "1000",
                                                                            "taskName" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setName_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setName( null,
                                                                                                              "1",
                                                                                                              "2",
                                                                                                              "3",
                                                                                                              "name" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setName_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setName( "1",
                                                                                                              null,
                                                                                                              "2",
                                                                                                              "3",
                                                                                                              "name" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setName_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setName( "1",
                                                                                                              "2",
                                                                                                              null,
                                                                                                              "3",
                                                                                                              "name" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setName_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setName( "1",
                                                                                                              "2",
                                                                                                              "3",
                                                                                                              null,
                                                                                                              "name" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setName_NullName() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setName( "1",
                                                                                                              "2",
                                                                                                              "3",
                                                                                                              "4",
                                                                                                              null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setPriority() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setPriority" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "priority",
                                                                            "1" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setPriority( "1",
                                                                                "10",
                                                                                "100",
                                                                                "1000",
                                                                                Priority.High );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setPriority_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setPriority( null,
                                                                                                                  "1",
                                                                                                                  "2",
                                                                                                                  "3",
                                                                                                                  Priority.High );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setPriority_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setPriority( "1",
                                                                                                                  null,
                                                                                                                  "2",
                                                                                                                  "3",
                                                                                                                  Priority.High );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setPriority_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setPriority( "1",
                                                                                                                  "2",
                                                                                                                  null,
                                                                                                                  "3",
                                                                                                                  Priority.High );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setPriority_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setPriority( "1",
                                                                                                                  "2",
                                                                                                                  "3",
                                                                                                                  null,
                                                                                                                  Priority.High );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setRecurrence_Unset() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setRecurrence" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setRecurrence( "1",
                                                                                  "10",
                                                                                  "100",
                                                                                  "1000",
                                                                                  null );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setRecurrence_Set() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setRecurrence" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "repeat",
                                                                            "after 2 days" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setRecurrence( "1",
                                                                                  "10",
                                                                                  "100",
                                                                                  "1000",
                                                                                  "after 2 days" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setRecurrence_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setRecurrence( null,
                                                                                                                    "1",
                                                                                                                    "2",
                                                                                                                    "3",
                                                                                                                    null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setRecurrence_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setRecurrence( "1",
                                                                                                                    null,
                                                                                                                    "2",
                                                                                                                    "3",
                                                                                                                    null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setRecurrence_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setRecurrence( "1",
                                                                                                                    "2",
                                                                                                                    null,
                                                                                                                    "3",
                                                                                                                    null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setRecurrence_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setRecurrence( "1",
                                                                                                                    "2",
                                                                                                                    "3",
                                                                                                                    null,
                                                                                                                    null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setTags_Set() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setTags" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "tags",
                                                                            "tag1,tag2" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setTags( "1",
                                                                            "10",
                                                                            "100",
                                                                            "1000",
                                                                            Arrays.asList( "tag1",
                                                                                           "tag2" ) );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setTags_Unset() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setTags" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "tags",
                                                                            "" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setTags( "1",
                                                                            "10",
                                                                            "100",
                                                                            "1000",
                                                                            null );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setTags_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setTags( null,
                                                                                                              "1",
                                                                                                              "2",
                                                                                                              "3",
                                                                                                              Collections.< String > emptyList() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setTags_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setTags( "1",
                                                                                                              null,
                                                                                                              "2",
                                                                                                              "3",
                                                                                                              Collections.< String > emptyList() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setTags_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setTags( "1",
                                                                                                              "2",
                                                                                                              null,
                                                                                                              "3",
                                                                                                              Collections.< String > emptyList() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setTags_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setTags( "1",
                                                                                                              "2",
                                                                                                              "3",
                                                                                                              null,
                                                                                                              Collections.< String > emptyList() );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setLocation_Set() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setLocation" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "location_id",
                                                                            "100000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setLocation( "1",
                                                                                "10",
                                                                                "100",
                                                                                "1000",
                                                                                "100000" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setLocation_Unset() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setLocation" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setLocation( "1",
                                                                                "10",
                                                                                "100",
                                                                                "1000",
                                                                                null );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setLocation_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setLocation( null,
                                                                                                                  "1",
                                                                                                                  "2",
                                                                                                                  "3",
                                                                                                                  null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setLocation_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setLocation( "1",
                                                                                                                  null,
                                                                                                                  "2",
                                                                                                                  "3",
                                                                                                                  null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setLocation_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setLocation( "1",
                                                                                                                  "2",
                                                                                                                  null,
                                                                                                                  "3",
                                                                                                                  null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setLocation_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setLocation( "1",
                                                                                                                  "2",
                                                                                                                  "3",
                                                                                                                  null,
                                                                                                                  null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setURL_Set() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setURL" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "url",
                                                                            "http://www.moloko.com" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setURL( "1",
                                                                           "10",
                                                                           "100",
                                                                           "1000",
                                                                           "http://www.moloko.com" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_setURL_Unset() throws RtmServiceException
   {
      final IRtmResponseHandler< List< RtmTask > > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTasksResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.setURL" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< List< RtmTask > >( new RtmTransaction( "10000",
                                                                                  true ),
                                                              EasyMock.createNiceMock( List.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< List< RtmTask >> response = service.tasks_setURL( "1",
                                                                           "10",
                                                                           "100",
                                                                           "1000",
                                                                           null );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setUrl_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setURL( null,
                                                                                                             "1",
                                                                                                             "2",
                                                                                                             "3",
                                                                                                             null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setUrl_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setURL( "1",
                                                                                                             null,
                                                                                                             "2",
                                                                                                             "3",
                                                                                                             null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setUrl_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setURL( "1",
                                                                                                             "2",
                                                                                                             null,
                                                                                                             "3",
                                                                                                             null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_setUrl_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_setURL( "1",
                                                                                                             "2",
                                                                                                             "3",
                                                                                                             null,
                                                                                                             null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_notes_add_Title_and_Text() throws RtmServiceException
   {
      final IRtmResponseHandler< RtmNote > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmNoteResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.notes.add" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "note_title",
                                                                            "title" ) ),
                                                    EasyMock.eq( new Param( "note_text",
                                                                            "text" ) ) ) )
              .andReturn( new RtmResponse< RtmNote >( new RtmTransaction( "10000",
                                                                          true ),
                                                      EasyMock.createNiceMock( RtmNote.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< RtmNote > response = service.tasks_notes_add( "1",
                                                                       "10",
                                                                       "100",
                                                                       "1000",
                                                                       "title",
                                                                       "text" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_notes_add_No_Title_and_Text() throws RtmServiceException
   {
      final IRtmResponseHandler< RtmNote > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmNoteResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.notes.add" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "list_id",
                                                                            "10" ) ),
                                                    EasyMock.eq( new Param( "taskseries_id",
                                                                            "100" ) ),
                                                    EasyMock.eq( new Param( "task_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "note_title",
                                                                            "" ) ),
                                                    EasyMock.eq( new Param( "note_text",
                                                                            "" ) ) ) )
              .andReturn( new RtmResponse< RtmNote >( new RtmTransaction( "10000",
                                                                          true ),
                                                      EasyMock.createNiceMock( RtmNote.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< RtmNote > response = service.tasks_notes_add( "1",
                                                                       "10",
                                                                       "100",
                                                                       "1000",
                                                                       null,
                                                                       null );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_notes_add_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_notes_add( null,
                                                                                                                "1",
                                                                                                                "2",
                                                                                                                "3",
                                                                                                                "title",
                                                                                                                "text" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_notes_add_NullListId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_notes_add( "1",
                                                                                                                null,
                                                                                                                "2",
                                                                                                                "3",
                                                                                                                "title",
                                                                                                                "text" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_notes_add_NullTaskSeriesId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_notes_add( "1",
                                                                                                                "2",
                                                                                                                null,
                                                                                                                "3",
                                                                                                                "title",
                                                                                                                "text" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_notes_add_NullTaskId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_notes_add( "1",
                                                                                                                "2",
                                                                                                                "3",
                                                                                                                null,
                                                                                                                "title",
                                                                                                                "text" );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_notes_delete() throws RtmServiceException
   {
      final IRtmResponseHandler< Void > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createVoidResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.notes.delete" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "note_id",
                                                                            "1000" ) ) ) )
              .andReturn( new RtmResponse< Void >( new RtmTransaction( "10000",
                                                                       true ),
                                                   null ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< Void > response = service.tasks_notes_delete( "1",
                                                                       "1000" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_notes_delete_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_notes_delete( null,
                                                                                                                   "1" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_notes_add_NullNoteId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_notes_delete( "1",
                                                                                                                   null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_notes_edit_Title_and_Text() throws RtmServiceException
   {
      final IRtmResponseHandler< RtmNote > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmNoteResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.notes.edit" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "note_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "note_title",
                                                                            "title" ) ),
                                                    EasyMock.eq( new Param( "note_text",
                                                                            "text" ) ) ) )
              .andReturn( new RtmResponse< RtmNote >( new RtmTransaction( "10000",
                                                                          true ),
                                                      EasyMock.createNiceMock( RtmNote.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< RtmNote > response = service.tasks_notes_edit( "1",
                                                                        "1000",
                                                                        "title",
                                                                        "text" );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTasks_notes_edit_No_Title_and_Text() throws RtmServiceException
   {
      final IRtmResponseHandler< RtmNote > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmNoteResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.tasks.notes.edit" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "note_id",
                                                                            "1000" ) ),
                                                    EasyMock.eq( new Param( "note_title",
                                                                            "" ) ),
                                                    EasyMock.eq( new Param( "note_text",
                                                                            "" ) ) ) )
              .andReturn( new RtmResponse< RtmNote >( new RtmTransaction( "10000",
                                                                          true ),
                                                      EasyMock.createNiceMock( RtmNote.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmResponse< RtmNote > response = service.tasks_notes_edit( "1",
                                                                        "1000",
                                                                        null,
                                                                        null );
      
      assertThat( response, is( notNullValue() ) );
      assertThat( response.isTransactional(), is( true ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_notes_edit_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_notes_edit( null,
                                                                                                                 "1",
                                                                                                                 "title",
                                                                                                                 "text" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTasks_notes_edit_NullNoteId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).tasks_notes_edit( "1",
                                                                                                                 null,
                                                                                                                 "title",
                                                                                                                 "text" );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTimelines_create() throws RtmServiceException
   {
      final IRtmResponseHandler< RtmTimeline > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmTimelineResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.timelines.create" ) ) )
              .andReturn( new RtmResponse< RtmTimeline >( EasyMock.createNiceMock( RtmTimeline.class ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      final RtmTimeline response = service.timelines_create();
      
      assertThat( response, is( notNullValue() ) );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testTransactions_undo() throws RtmServiceException
   {
      final IRtmResponseHandler< Void > handler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( handler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createVoidResponseHandler() )
              .andReturn( handler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( handler ),
                                                    EasyMock.eq( "rtm.transactions.undo" ),
                                                    EasyMock.eq( new Param( "timeline",
                                                                            "1" ) ),
                                                    EasyMock.eq( new Param( "transaction_id",
                                                                            "10" ) ) ) )
              .andReturn( new RtmResponse< Void >( null ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmContentEditService service = new RtmContentEditService( connFact,
                                                                       responseHandlerFactory );
      service.transactions_undo( "1", "10" );
      
      EasyMock.verify( handler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTransactions_undo_NullTimeline() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).transactions_undo( null,
                                                                                                                  "1" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTransactions_undo_NullTransactionId() throws RtmServiceException
   {
      new RtmContentEditService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) ).transactions_undo( "1",
                                                                                                                  null );
   }
}
