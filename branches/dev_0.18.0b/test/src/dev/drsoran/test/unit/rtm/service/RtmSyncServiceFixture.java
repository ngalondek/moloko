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

import java.util.Arrays;
import java.util.Comparator;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.test.TestConstants;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.model.RtmTimeline;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;
import dev.drsoran.rtm.service.RtmSyncService;
import dev.drsoran.rtm.sync.IRtmSyncHandler;
import dev.drsoran.rtm.sync.IRtmSyncHandlerFactory;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.sync.IRtmTimelineFactory;
import dev.drsoran.rtm.sync.RtmSyncResult;
import dev.drsoran.rtm.sync.SyncTime;


public class RtmSyncServiceFixture
{
   @Test
   public void testRtmSyncDriver()
   {
      new RtmSyncService( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                          EasyMock.createNiceMock( IRtmContentRepository.class ),
                          EasyMock.createNiceMock( IRtmContentEditService.class ),
                          EasyMock.createNiceMock( IRtmSyncHandlerFactory.class ),
                          EasyMock.createNiceMock( IRtmTimelineFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmSyncDriver_NullSyncPartner()
   {
      new RtmSyncService( null,
                          EasyMock.createNiceMock( IRtmContentRepository.class ),
                          EasyMock.createNiceMock( IRtmContentEditService.class ),
                          EasyMock.createNiceMock( IRtmSyncHandlerFactory.class ),
                          EasyMock.createNiceMock( IRtmTimelineFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmSyncDriver_NullContentRepository()
   {
      new RtmSyncService( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                          null,
                          EasyMock.createNiceMock( IRtmContentEditService.class ),
                          EasyMock.createNiceMock( IRtmSyncHandlerFactory.class ),
                          EasyMock.createNiceMock( IRtmTimelineFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmSyncDriver_NullContentEditService()
   {
      new RtmSyncService( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                          EasyMock.createNiceMock( IRtmContentRepository.class ),
                          null,
                          EasyMock.createNiceMock( IRtmSyncHandlerFactory.class ),
                          EasyMock.createNiceMock( IRtmTimelineFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmSyncDriver_NullSyncHandlerFactory()
   {
      new RtmSyncService( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                          EasyMock.createNiceMock( IRtmContentRepository.class ),
                          EasyMock.createNiceMock( IRtmContentEditService.class ),
                          null,
                          EasyMock.createNiceMock( IRtmTimelineFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmSyncDriver_NullTimelineFactory()
   {
      new RtmSyncService( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                          EasyMock.createNiceMock( IRtmContentRepository.class ),
                          EasyMock.createNiceMock( IRtmContentEditService.class ),
                          EasyMock.createNiceMock( IRtmSyncHandlerFactory.class ),
                          null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testPerformIncomingSync_Success() throws RtmServiceException
   {
      final IRtmTimelineFactory timelineFactory = EasyMock.createMock( IRtmTimelineFactory.class );
      EasyMock.replay( timelineFactory );
      
      final IRtmSyncHandler syncHandler = EasyMock.createNiceMock( IRtmSyncHandler.class );
      EasyMock.expect( syncHandler.handleIncomingSync( EasyMock.anyObject( IRtmContentRepository.class ),
                                                       EasyMock.eq( TestConstants.NOW ) ) )
              .andReturn( RtmSyncResult.newSucceeded() )
              .times( 5 );
      EasyMock.replay( syncHandler );
      
      final IRtmSyncHandlerFactory handlerFactory = EasyMock.createNiceMock( IRtmSyncHandlerFactory.class );
      EasyMock.expect( handlerFactory.createRtmTasksListsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmTasksSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmLocationsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmContactsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmSettingsSyncHandler() )
              .andReturn( syncHandler );
      EasyMock.replay( handlerFactory );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      syncPartner.onSyncStarted();
      syncPartner.onSyncSuccessful();
      EasyMock.replay( syncPartner );
      
      final RtmSyncService syncDriver = new RtmSyncService( syncPartner,
                                                            EasyMock.createNiceMock( IRtmContentRepository.class ),
                                                            EasyMock.createNiceMock( IRtmContentEditService.class ),
                                                            handlerFactory,
                                                            timelineFactory );
      
      syncDriver.performIncomingSync( new SyncTime( TestConstants.NOW,
                                                    TestConstants.NEVER ) );
      EasyMock.verify( timelineFactory );
      EasyMock.verify( syncHandler );
      EasyMock.verify( handlerFactory );
      EasyMock.verify( syncPartner );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = RtmServiceException.class )
   public void testPerformIncomingSync_Failed_RtmServiceException() throws RtmServiceException
   {
      final IRtmTimelineFactory timelineFactory = EasyMock.createMock( IRtmTimelineFactory.class );
      EasyMock.replay( timelineFactory );
      
      final IRtmSyncHandler syncHandler = EasyMock.createNiceMock( IRtmSyncHandler.class );
      EasyMock.expect( syncHandler.handleIncomingSync( EasyMock.anyObject( IRtmContentRepository.class ),
                                                       EasyMock.eq( TestConstants.NOW ) ) )
              .andReturn( RtmSyncResult.newFailed( new RtmServiceException( "" ) ) );
      EasyMock.replay( syncHandler );
      
      final IRtmSyncHandlerFactory handlerFactory = EasyMock.createNiceMock( IRtmSyncHandlerFactory.class );
      EasyMock.expect( handlerFactory.createRtmTasksListsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.replay( handlerFactory );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      syncPartner.onSyncStarted();
      syncPartner.onSyncFailed();
      EasyMock.replay( syncPartner );
      
      final RtmSyncService syncDriver = new RtmSyncService( syncPartner,
                                                            EasyMock.createNiceMock( IRtmContentRepository.class ),
                                                            EasyMock.createNiceMock( IRtmContentEditService.class ),
                                                            handlerFactory,
                                                            timelineFactory );
      
      try
      {
         syncDriver.performIncomingSync( new SyncTime( TestConstants.NOW,
                                                       TestConstants.NEVER ) );
      }
      finally
      {
         EasyMock.verify( timelineFactory );
         EasyMock.verify( syncHandler );
         EasyMock.verify( handlerFactory );
         EasyMock.verify( syncPartner );
      }
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = RtmServiceException.class )
   public void testPerformIncomingSync_Failed_OtherException() throws RtmServiceException
   {
      final IRtmTimelineFactory timelineFactory = EasyMock.createMock( IRtmTimelineFactory.class );
      EasyMock.replay( timelineFactory );
      
      final IRtmSyncHandler syncHandler = EasyMock.createNiceMock( IRtmSyncHandler.class );
      EasyMock.expect( syncHandler.handleIncomingSync( EasyMock.anyObject( IRtmContentRepository.class ),
                                                       EasyMock.eq( TestConstants.NOW ) ) )
              .andReturn( RtmSyncResult.newFailed( new IllegalArgumentException() ) );
      EasyMock.replay( syncHandler );
      
      final IRtmSyncHandlerFactory handlerFactory = EasyMock.createNiceMock( IRtmSyncHandlerFactory.class );
      EasyMock.expect( handlerFactory.createRtmTasksListsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.replay( handlerFactory );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      syncPartner.onSyncStarted();
      syncPartner.onSyncFailed();
      EasyMock.replay( syncPartner );
      
      final RtmSyncService syncDriver = new RtmSyncService( syncPartner,
                                                            EasyMock.createNiceMock( IRtmContentRepository.class ),
                                                            EasyMock.createNiceMock( IRtmContentEditService.class ),
                                                            handlerFactory,
                                                            timelineFactory );
      
      try
      {
         syncDriver.performIncomingSync( new SyncTime( TestConstants.NOW,
                                                       TestConstants.NEVER ) );
      }
      finally
      {
         EasyMock.verify( timelineFactory );
         EasyMock.verify( syncHandler );
         EasyMock.verify( handlerFactory );
         EasyMock.verify( syncPartner );
      }
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPerformIncomingSync_NullSyncTime() throws RtmServiceException
   {
      new RtmSyncService( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                          EasyMock.createNiceMock( IRtmContentRepository.class ),
                          EasyMock.createNiceMock( IRtmContentEditService.class ),
                          EasyMock.createNiceMock( IRtmSyncHandlerFactory.class ),
                          EasyMock.createNiceMock( IRtmTimelineFactory.class ) ).performIncomingSync( null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testPerformOutgoingSync_Success() throws RtmServiceException
   {
      final IRtmContentEditService contentEditService = EasyMock.createStrictMock( IRtmContentEditService.class );
      EasyMock.replay( contentEditService );
      
      final IRtmTimelineFactory timelineFactory = EasyMock.createMock( IRtmTimelineFactory.class );
      EasyMock.expect( timelineFactory.createTimeline() )
              .andReturn( new RtmTimeline( "1" ) );
      EasyMock.replay( timelineFactory );
      
      final IRtmSyncHandler syncHandler = EasyMock.createNiceMock( IRtmSyncHandler.class );
      EasyMock.expect( syncHandler.handleOutgoingSync( contentEditService,
                                                       "1",
                                                       TestConstants.NOW ) )
              .andReturn( RtmSyncResult.newSucceeded() )
              .times( 2 );
      EasyMock.replay( syncHandler );
      
      final IRtmSyncHandlerFactory handlerFactory = EasyMock.createNiceMock( IRtmSyncHandlerFactory.class );
      EasyMock.expect( handlerFactory.createRtmTasksListsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmTasksSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.replay( handlerFactory );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      syncPartner.onSyncStarted();
      syncPartner.onSyncSuccessful();
      EasyMock.replay( syncPartner );
      
      final RtmSyncService syncDriver = new RtmSyncService( syncPartner,
                                                            EasyMock.createNiceMock( IRtmContentRepository.class ),
                                                            contentEditService,
                                                            handlerFactory,
                                                            timelineFactory );
      
      syncDriver.performOutgoingSync( new SyncTime( TestConstants.NEVER,
                                                    TestConstants.NOW ) );
      
      EasyMock.verify( timelineFactory );
      EasyMock.verify( syncHandler );
      EasyMock.verify( handlerFactory );
      EasyMock.verify( syncPartner );
      EasyMock.verify( contentEditService );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = RtmServiceException.class )
   public void testPerformOutgoingSync_Failed_RtmServiceException() throws RtmServiceException
   {
      final IRtmContentEditService contentEditService = EasyMock.createStrictMock( IRtmContentEditService.class );
      contentEditService.transactions_undo( "1", "101" );
      contentEditService.transactions_undo( "1", "100" );
      EasyMock.replay( contentEditService );
      
      final IRtmTimelineFactory timelineFactory = EasyMock.createMock( IRtmTimelineFactory.class );
      EasyMock.expect( timelineFactory.createTimeline() )
              .andReturn( new RtmTimeline( "1" ) );
      EasyMock.replay( timelineFactory );
      
      final IRtmSyncHandler syncHandler = EasyMock.createNiceMock( IRtmSyncHandler.class );
      EasyMock.expect( syncHandler.handleOutgoingSync( contentEditService,
                                                       "1",
                                                       TestConstants.NOW ) )
              .andReturn( RtmSyncResult.newFailed( new RtmServiceException( "" ),
                                                   Arrays.asList( new RtmTransaction( "100",
                                                                                      true ),
                                                                  new RtmTransaction( "101",
                                                                                      true ) ) ) );
      EasyMock.replay( syncHandler );
      
      final IRtmSyncHandlerFactory handlerFactory = EasyMock.createNiceMock( IRtmSyncHandlerFactory.class );
      EasyMock.expect( handlerFactory.createRtmTasksListsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.replay( handlerFactory );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      syncPartner.onSyncStarted();
      syncPartner.onSyncFailed();
      EasyMock.replay( syncPartner );
      
      final RtmSyncService syncDriver = new RtmSyncService( syncPartner,
                                                            EasyMock.createNiceMock( IRtmContentRepository.class ),
                                                            contentEditService,
                                                            handlerFactory,
                                                            timelineFactory );
      
      try
      {
         syncDriver.performOutgoingSync( new SyncTime( TestConstants.NEVER,
                                                       TestConstants.NOW ) );
      }
      finally
      {
         EasyMock.verify( timelineFactory );
         EasyMock.verify( syncHandler );
         EasyMock.verify( handlerFactory );
         EasyMock.verify( syncPartner );
         EasyMock.verify( contentEditService );
      }
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = RtmServiceException.class )
   public void testPerformOutgoingSync_Failed_OtherException() throws RtmServiceException
   {
      final IRtmContentEditService contentEditService = EasyMock.createStrictMock( IRtmContentEditService.class );
      contentEditService.transactions_undo( "1", "101" );
      contentEditService.transactions_undo( "1", "100" );
      EasyMock.replay( contentEditService );
      
      final IRtmTimelineFactory timelineFactory = EasyMock.createMock( IRtmTimelineFactory.class );
      EasyMock.expect( timelineFactory.createTimeline() )
              .andReturn( new RtmTimeline( "1" ) );
      EasyMock.replay( timelineFactory );
      
      final IRtmSyncHandler syncHandler = EasyMock.createNiceMock( IRtmSyncHandler.class );
      EasyMock.expect( syncHandler.handleOutgoingSync( contentEditService,
                                                       "1",
                                                       TestConstants.NOW ) )
              .andReturn( RtmSyncResult.newFailed( new IllegalArgumentException(),
                                                   Arrays.asList( new RtmTransaction( "100",
                                                                                      true ),
                                                                  new RtmTransaction( "101",
                                                                                      true ) ) ) );
      EasyMock.replay( syncHandler );
      
      final IRtmSyncHandlerFactory handlerFactory = EasyMock.createNiceMock( IRtmSyncHandlerFactory.class );
      EasyMock.expect( handlerFactory.createRtmTasksListsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.replay( handlerFactory );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      syncPartner.onSyncStarted();
      syncPartner.onSyncFailed();
      EasyMock.replay( syncPartner );
      
      final RtmSyncService syncDriver = new RtmSyncService( syncPartner,
                                                            EasyMock.createNiceMock( IRtmContentRepository.class ),
                                                            contentEditService,
                                                            handlerFactory,
                                                            timelineFactory );
      
      try
      {
         syncDriver.performOutgoingSync( new SyncTime( TestConstants.NEVER,
                                                       TestConstants.NOW ) );
      }
      finally
      {
         EasyMock.verify( timelineFactory );
         EasyMock.verify( syncHandler );
         EasyMock.verify( handlerFactory );
         EasyMock.verify( syncPartner );
         EasyMock.verify( contentEditService );
      }
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPerformOutgoingSync_NullSyncTime() throws RtmServiceException
   {
      new RtmSyncService( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                          EasyMock.createNiceMock( IRtmContentRepository.class ),
                          EasyMock.createNiceMock( IRtmContentEditService.class ),
                          EasyMock.createNiceMock( IRtmSyncHandlerFactory.class ),
                          EasyMock.createNiceMock( IRtmTimelineFactory.class ) ).performOutgoingSync( null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testPerformFullSync_Success() throws RtmServiceException
   {
      final IRtmContentRepository contentRepository = EasyMock.createStrictMock( IRtmContentRepository.class );
      EasyMock.replay( contentRepository );
      
      final IRtmContentEditService contentEditService = EasyMock.createStrictMock( IRtmContentEditService.class );
      EasyMock.replay( contentEditService );
      
      final IRtmTimelineFactory timelineFactory = EasyMock.createMock( IRtmTimelineFactory.class );
      EasyMock.expect( timelineFactory.createTimeline() )
              .andReturn( new RtmTimeline( "1" ) );
      EasyMock.replay( timelineFactory );
      
      final IRtmSyncHandler syncHandler = EasyMock.createNiceMock( IRtmSyncHandler.class );
      EasyMock.expect( syncHandler.handleIncomingSync( contentRepository,
                                                       TestConstants.NOW ) )
              .andReturn( RtmSyncResult.newSucceeded() )
              .times( 5 );
      EasyMock.expect( syncHandler.handleOutgoingSync( contentEditService,
                                                       "1",
                                                       TestConstants.LATER ) )
              .andReturn( RtmSyncResult.newSucceeded() )
              .times( 2 );
      EasyMock.replay( syncHandler );
      
      final IRtmSyncHandlerFactory handlerFactory = EasyMock.createNiceMock( IRtmSyncHandlerFactory.class );
      EasyMock.expect( handlerFactory.createRtmTasksListsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler )
              .times( 2 );
      EasyMock.expect( handlerFactory.createRtmTasksSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler )
              .times( 2 );
      EasyMock.expect( handlerFactory.createRtmLocationsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmContactsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmSettingsSyncHandler() )
              .andReturn( syncHandler );
      EasyMock.replay( handlerFactory );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      syncPartner.onSyncStarted();
      syncPartner.onSyncSuccessful();
      EasyMock.replay( syncPartner );
      
      final RtmSyncService syncDriver = new RtmSyncService( syncPartner,
                                                            contentRepository,
                                                            contentEditService,
                                                            handlerFactory,
                                                            timelineFactory );
      
      syncDriver.performFullSync( new SyncTime( TestConstants.NOW,
                                                TestConstants.LATER ) );
      
      EasyMock.verify( timelineFactory );
      EasyMock.verify( syncHandler );
      EasyMock.verify( handlerFactory );
      EasyMock.verify( syncPartner );
      EasyMock.verify( contentEditService );
      EasyMock.verify( contentRepository );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = RtmServiceException.class )
   public void testPerformFullSync_Failed_RtmServiceException() throws RtmServiceException
   {
      final IRtmContentRepository contentRepository = EasyMock.createStrictMock( IRtmContentRepository.class );
      EasyMock.replay( contentRepository );
      
      final IRtmContentEditService contentEditService = EasyMock.createStrictMock( IRtmContentEditService.class );
      contentEditService.transactions_undo( "1", "101" );
      contentEditService.transactions_undo( "1", "100" );
      EasyMock.replay( contentEditService );
      
      final IRtmTimelineFactory timelineFactory = EasyMock.createMock( IRtmTimelineFactory.class );
      EasyMock.expect( timelineFactory.createTimeline() )
              .andReturn( new RtmTimeline( "1" ) );
      EasyMock.replay( timelineFactory );
      
      final IRtmSyncHandler syncHandler = EasyMock.createNiceMock( IRtmSyncHandler.class );
      EasyMock.expect( syncHandler.handleIncomingSync( contentRepository,
                                                       TestConstants.NOW ) )
              .andReturn( RtmSyncResult.newSucceeded() )
              .times( 5 );
      EasyMock.expect( syncHandler.handleOutgoingSync( contentEditService,
                                                       "1",
                                                       TestConstants.LATER ) )
              .andReturn( RtmSyncResult.newFailed( new RtmServiceException( "" ),
                                                   Arrays.asList( new RtmTransaction( "100",
                                                                                      true ),
                                                                  new RtmTransaction( "101",
                                                                                      true ) ) ) );
      EasyMock.replay( syncHandler );
      
      final IRtmSyncHandlerFactory handlerFactory = EasyMock.createNiceMock( IRtmSyncHandlerFactory.class );
      EasyMock.expect( handlerFactory.createRtmTasksListsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler )
              .times( 2 );
      EasyMock.expect( handlerFactory.createRtmTasksSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmLocationsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmContactsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmSettingsSyncHandler() )
              .andReturn( syncHandler );
      EasyMock.replay( handlerFactory );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      syncPartner.onSyncStarted();
      syncPartner.onSyncFailed();
      EasyMock.replay( syncPartner );
      
      final RtmSyncService syncDriver = new RtmSyncService( syncPartner,
                                                            contentRepository,
                                                            contentEditService,
                                                            handlerFactory,
                                                            timelineFactory );
      
      try
      {
         syncDriver.performFullSync( new SyncTime( TestConstants.NOW,
                                                   TestConstants.LATER ) );
      }
      finally
      {
         EasyMock.verify( timelineFactory );
         EasyMock.verify( syncHandler );
         EasyMock.verify( handlerFactory );
         EasyMock.verify( syncPartner );
         EasyMock.verify( contentEditService );
         EasyMock.verify( contentRepository );
      }
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = RtmServiceException.class )
   public void testPerformFullSync_Failed_OtherException() throws RtmServiceException
   {
      final IRtmContentRepository contentRepository = EasyMock.createStrictMock( IRtmContentRepository.class );
      EasyMock.replay( contentRepository );
      
      final IRtmContentEditService contentEditService = EasyMock.createStrictMock( IRtmContentEditService.class );
      contentEditService.transactions_undo( "1", "101" );
      contentEditService.transactions_undo( "1", "100" );
      EasyMock.replay( contentEditService );
      
      final IRtmTimelineFactory timelineFactory = EasyMock.createMock( IRtmTimelineFactory.class );
      EasyMock.expect( timelineFactory.createTimeline() )
              .andReturn( new RtmTimeline( "1" ) );
      EasyMock.replay( timelineFactory );
      
      final IRtmSyncHandler syncHandler = EasyMock.createNiceMock( IRtmSyncHandler.class );
      EasyMock.expect( syncHandler.handleIncomingSync( contentRepository,
                                                       TestConstants.NOW ) )
              .andReturn( RtmSyncResult.newSucceeded() )
              .times( 5 );
      EasyMock.expect( syncHandler.handleOutgoingSync( contentEditService,
                                                       "1",
                                                       TestConstants.LATER ) )
              .andReturn( RtmSyncResult.newFailed( new IllegalArgumentException(),
                                                   Arrays.asList( new RtmTransaction( "100",
                                                                                      true ),
                                                                  new RtmTransaction( "101",
                                                                                      true ) ) ) );
      EasyMock.replay( syncHandler );
      
      final IRtmSyncHandlerFactory handlerFactory = EasyMock.createNiceMock( IRtmSyncHandlerFactory.class );
      EasyMock.expect( handlerFactory.createRtmTasksListsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler )
              .times( 2 );
      EasyMock.expect( handlerFactory.createRtmLocationsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmContactsSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmTasksSyncHandler( EasyMock.notNull( Comparator.class ) ) )
              .andReturn( syncHandler );
      EasyMock.expect( handlerFactory.createRtmSettingsSyncHandler() )
              .andReturn( syncHandler );
      EasyMock.replay( handlerFactory );
      
      final IRtmSyncPartner syncPartner = EasyMock.createMock( IRtmSyncPartner.class );
      syncPartner.onSyncStarted();
      syncPartner.onSyncFailed();
      EasyMock.replay( syncPartner );
      
      final RtmSyncService syncDriver = new RtmSyncService( syncPartner,
                                                            contentRepository,
                                                            contentEditService,
                                                            handlerFactory,
                                                            timelineFactory );
      
      try
      {
         syncDriver.performFullSync( new SyncTime( TestConstants.NOW,
                                                   TestConstants.LATER ) );
      }
      finally
      {
         EasyMock.verify( timelineFactory );
         EasyMock.verify( syncHandler );
         EasyMock.verify( handlerFactory );
         EasyMock.verify( syncPartner );
         EasyMock.verify( contentEditService );
         EasyMock.verify( contentRepository );
      }
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testPerformFullSync_NullSyncTime() throws RtmServiceException
   {
      new RtmSyncService( EasyMock.createNiceMock( IRtmSyncPartner.class ),
                          EasyMock.createNiceMock( IRtmContentRepository.class ),
                          EasyMock.createNiceMock( IRtmContentEditService.class ),
                          EasyMock.createNiceMock( IRtmSyncHandlerFactory.class ),
                          EasyMock.createNiceMock( IRtmTimelineFactory.class ) ).performFullSync( null );
   }
}
