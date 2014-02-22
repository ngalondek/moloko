/* 
 *	Copyright (c) 2014 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm.sync;

import java.util.ArrayList;
import java.util.List;

import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.model.RtmTimeline;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;


public class RtmSyncDriver implements IRtmSyncDriver
{
   private final IRtmContentRepository contentRepository;
   
   private final IRtmContentEditService contentEditService;
   
   private final IRtmSyncHandlerFactory syncHandlerFactory;
   
   private final IRtmTimelineFactory timelineFactory;
   
   
   
   public RtmSyncDriver( IRtmContentRepository contentRepository,
      IRtmContentEditService contentEditService,
      IRtmSyncHandlerFactory syncHandlerFactory,
      IRtmTimelineFactory timelineFactory )
   {
      if ( contentRepository == null )
      {
         throw new IllegalArgumentException( "contentRepository" );
      }
      if ( contentEditService == null )
      {
         throw new IllegalArgumentException( "contentEditService" );
      }
      if ( syncHandlerFactory == null )
      {
         throw new IllegalArgumentException( "syncHandlerFactory" );
      }
      if ( timelineFactory == null )
      {
         throw new IllegalArgumentException( "timelineFactory" );
      }
      
      this.contentRepository = contentRepository;
      this.contentEditService = contentEditService;
      this.syncHandlerFactory = syncHandlerFactory;
      this.timelineFactory = timelineFactory;
   }
   
   
   
   @Override
   public void performIncomingSync( IRtmSyncPartner syncPartner,
                                    SyncTime syncTime ) throws RtmServiceException
   {
      checkSyncPartner( syncPartner );
      checkSyncTime( syncTime );
      
      try
      {
         syncPartner.onSyncStarted();
         
         syncIncoming( syncPartner, syncTime );
         
         syncPartner.onSyncSuccessful();
      }
      catch ( SyncException e )
      {
         syncPartner.onSyncFailed();
         
         if ( e.getCause() instanceof RtmServiceException )
         {
            throw (RtmServiceException) e.getCause();
         }
         else
         {
            throw new RuntimeException( e.getCause() );
         }
      }
   }
   
   
   
   @Override
   public void performOutgoingSync( IRtmSyncPartner syncPartner,
                                    SyncTime syncTime ) throws RtmServiceException
   {
      checkSyncPartner( syncPartner );
      checkSyncTime( syncTime );
      
      final RtmTimeline timeline = timelineFactory.createTimeline();
      
      try
      {
         syncPartner.onSyncStarted();
         
         syncOutgoing( syncPartner, timeline.getId(), syncTime );
         
         syncPartner.onSyncSuccessful();
      }
      catch ( SyncException e )
      {
         undoTransactions( timeline.getId(), e.getSyncResult()
                                              .getTransactions() );
         
         syncPartner.onSyncFailed();
         
         if ( e.getCause() instanceof RtmServiceException )
         {
            throw (RtmServiceException) e.getCause();
         }
         else
         {
            throw new RuntimeException( e.getCause() );
         }
      }
   }
   
   
   
   @Override
   public void performFullSync( IRtmSyncPartner syncPartner, SyncTime syncTime ) throws RtmServiceException
   {
      checkSyncPartner( syncPartner );
      checkSyncTime( syncTime );
      
      final RtmTimeline timeline = timelineFactory.createTimeline();
      
      try
      {
         syncPartner.onSyncStarted();
         
         syncIncoming( syncPartner, syncTime );
         
         syncOutgoing( syncPartner, timeline.getId(), syncTime );
         
         syncPartner.onSyncSuccessful();
      }
      catch ( SyncException e )
      {
         undoTransactions( timeline.getId(), e.getSyncResult()
                                              .getTransactions() );
         
         syncPartner.onSyncFailed();
         
         if ( e.getCause() instanceof RtmServiceException )
         {
            throw (RtmServiceException) e.getCause();
         }
         else
         {
            throw new RuntimeException( e.getCause() );
         }
      }
   }
   
   
   
   private void syncIncoming( IRtmSyncPartner syncPartner, SyncTime syncTime ) throws SyncException
   {
      performIncomingRtmTasksListSync( syncPartner, syncTime );
      performIncomingRtmTasksSync( syncPartner, syncTime );
      performIncomingRtmLocationsSync( syncPartner, syncTime );
      performIncomingRtmContactsSync( syncPartner, syncTime );
   }
   
   
   
   private void performIncomingRtmTasksListSync( IRtmSyncPartner syncPartner,
                                                 SyncTime syncTime ) throws SyncException
   {
      final IRtmSyncHandler syncHandler = syncHandlerFactory.createRtmTasksListsSyncHandler( RtmContentSort.getRtmTasksListIdSort() );
      performIncomingSyncImpl( syncHandler, syncTime );
   }
   
   
   
   private void performIncomingRtmTasksSync( IRtmSyncPartner syncPartner,
                                             SyncTime syncTime ) throws SyncException
   {
      final IRtmSyncHandler syncHandler = syncHandlerFactory.createRtmTasksSyncHandler( RtmContentSort.getRtmTaskIdSort() );
      performIncomingSyncImpl( syncHandler, syncTime );
   }
   
   
   
   private void performIncomingRtmLocationsSync( IRtmSyncPartner syncPartner,
                                                 SyncTime syncTime ) throws SyncException
   {
      final IRtmSyncHandler syncHandler = syncHandlerFactory.createRtmLocationsSyncHandler( RtmContentSort.getRtmLocationIdSort() );
      performIncomingSyncImpl( syncHandler, syncTime );
   }
   
   
   
   private void performIncomingRtmContactsSync( IRtmSyncPartner syncPartner,
                                                SyncTime syncTime ) throws SyncException
   {
      final IRtmSyncHandler syncHandler = syncHandlerFactory.createRtmContactsSyncHandler( RtmContentSort.getRtmContactIdSort() );
      performIncomingSyncImpl( syncHandler, syncTime );
   }
   
   
   
   private void performIncomingSyncImpl( IRtmSyncHandler syncHandler,
                                         SyncTime syncTime ) throws SyncException
   {
      checkSyncResult( syncHandler.handleIncomingSync( contentRepository,
                                                       syncTime.getLastSyncInMillis() ) );
   }
   
   
   
   private void syncOutgoing( IRtmSyncPartner syncPartner,
                              String timelineId,
                              SyncTime syncTime ) throws SyncException
   {
      
      performOutgoingRtmTasksListSync( syncPartner, timelineId, syncTime );
      performOutgoingRtmTasksSync( syncPartner, timelineId, syncTime );
   }
   
   
   
   private void performOutgoingRtmTasksListSync( IRtmSyncPartner syncPartner,
                                                 String timelineId,
                                                 SyncTime syncTime ) throws SyncException
   {
      final IRtmSyncHandler syncHandler = syncHandlerFactory.createRtmTasksListsSyncHandler( RtmContentSort.getRtmTasksListIdSort() );
      performOutgoingSyncImpl( syncHandler, timelineId, syncTime );
   }
   
   
   
   private void performOutgoingRtmTasksSync( IRtmSyncPartner syncPartner,
                                             String timelineId,
                                             SyncTime syncTime ) throws SyncException
   {
      final IRtmSyncHandler syncHandler = syncHandlerFactory.createRtmTasksSyncHandler( RtmContentSort.getRtmTaskIdSort() );
      performOutgoingSyncImpl( syncHandler, timelineId, syncTime );
   }
   
   
   
   private void performOutgoingSyncImpl( IRtmSyncHandler syncHandler,
                                         String timelineId,
                                         SyncTime syncTime ) throws SyncException
   {
      checkSyncResult( syncHandler.handleOutgoingSync( contentEditService,
                                                       timelineId,
                                                       syncTime.getLastSyncOutMillis() ) );
   }
   
   
   
   private void undoTransactions( String timelineId,
                                  List< RtmTransaction > transactions )
   {
      transactions = new ArrayList< RtmTransaction >( transactions );
      
      try
      {
         for ( int i = transactions.size() - 1; i > -1; --i )
         {
            final RtmTransaction transaction = transactions.get( i );
            contentEditService.transactions_undo( timelineId,
                                                  transaction.getId() );
            
            transactions.remove( i );
         }
      }
      catch ( RtmServiceException se )
      {
         // TODO: What if undo failed? The transactions list contains all not yet undone transactions.
         // We cannot throw since this is called from an exception handler.
      }
   }
   
   
   
   private static void checkSyncResult( RtmSyncResult syncResult ) throws SyncException
   {
      if ( syncResult.getException() != null )
      {
         throw new SyncException( syncResult );
      }
   }
   
   
   
   private static void checkSyncPartner( IRtmSyncPartner syncPartner )
   {
      if ( syncPartner == null )
      {
         throw new IllegalArgumentException( "syncPartner" );
      }
   }
   
   
   
   private static void checkSyncTime( SyncTime syncTime )
   {
      if ( syncTime == null )
      {
         throw new IllegalArgumentException( "syncTime" );
      }
   }
}
