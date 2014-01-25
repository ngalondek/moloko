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

import java.util.Collection;

import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.model.RtmTasksList;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;


public class RtmSyncDriver implements IRtmSyncDriver
{
   private final IRtmContentRepository contentRepository;
   
   private final IRtmContentEditService contentEditService;
   
   private final IRtmSyncHandlerFactory syncHandlerFactory;
   
   
   
   public RtmSyncDriver( IRtmContentRepository contentRepository,
      IRtmContentEditService contentEditService,
      IRtmSyncHandlerFactory syncHandlerFactory )
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
      
      this.contentRepository = contentRepository;
      this.contentEditService = contentEditService;
      this.syncHandlerFactory = syncHandlerFactory;
   }
   
   
   
   @Override
   public void performIncomingSync( IRtmSyncPartner syncPartner,
                                    SyncTime syncTime ) throws RtmServiceException
   {
      checkSyncPartner( syncPartner );
      checkSyncTime( syncTime );
      
      performIncomingRtmTasksListSync( syncPartner, syncTime );
   }
   
   
   
   @Override
   public void performOutgoingSync( IRtmSyncPartner syncPartner,
                                    SyncTime syncTime ) throws RtmServiceException
   {
      checkSyncPartner( syncPartner );
      checkSyncTime( syncTime );
      
   }
   
   
   
   @Override
   public void performFullSync( IRtmSyncPartner syncPartner, SyncTime syncTime ) throws RtmServiceException
   {
      checkSyncPartner( syncPartner );
      checkSyncTime( syncTime );
      
      performOutgoingSync( syncPartner, syncTime );
      performIncomingSync( syncPartner, syncTime );
   }
   
   
   
   private void performIncomingRtmTasksListSync( IRtmSyncPartner syncPartner,
                                                 SyncTime syncTime ) throws RtmServiceException
   {
      final Collection< RtmTasksList > tasksListsModified = syncPartner.getTasksListsModifiedSince( syncTime.getLastSyncOutMillis() );
      final Collection< RtmTasksList > rtmTasksLists = contentRepository.lists_getList();
      
      final IRtmSyncHandler< RtmTasksList > tasksListsSyncHandler = syncHandlerFactory.createRtmTasksListsSyncHandler();
      
      tasksListsSyncHandler.handleIncomingSync( tasksListsModified,
                                                rtmTasksLists );
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
