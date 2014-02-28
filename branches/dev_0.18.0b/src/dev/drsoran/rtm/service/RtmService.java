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

package dev.drsoran.rtm.service;

import dev.drsoran.moloko.ILog;
import dev.drsoran.rtm.BlockingRtmRequestLimiter;
import dev.drsoran.rtm.IConnectionFactory;
import dev.drsoran.rtm.IRtmConnectionFactory;
import dev.drsoran.rtm.IRtmResponseHandlerFactory;
import dev.drsoran.rtm.RtmConnectionFactory;
import dev.drsoran.rtm.RtmConnectionProtocol;
import dev.drsoran.rtm.rest.RtmRestRequestFactory;
import dev.drsoran.rtm.rest.RtmRestResponseHandlerFactory;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.sync.RtmSyncHandlerFactory;
import dev.drsoran.rtm.sync.RtmTimelineFactory;


public class RtmService implements IRtmService
{
   private final IRtmConnectionFactory rtmConnectionFactory;
   
   private final IRtmResponseHandlerFactory responseHandlerFactory;
   
   private final IRtmContentRepository contentRepository;
   
   
   
   public RtmService( ILog log, IConnectionFactory connectionFactory,
      RtmConnectionProtocol protocol, String apiKey, String sharedSecret )
   {
      rtmConnectionFactory = new RtmConnectionFactory( log,
                                                       connectionFactory,
                                                       new RtmRestRequestFactory(),
                                                       new BlockingRtmRequestLimiter(),
                                                       protocol,
                                                       apiKey,
                                                       sharedSecret );
      
      responseHandlerFactory = new RtmRestResponseHandlerFactory();
      
      contentRepository = new RtmContentRepository( rtmConnectionFactory,
                                                    responseHandlerFactory );
   }
   
   
   
   @Override
   public IRtmContentRepository getContentRepository()
   {
      return contentRepository;
   }
   
   
   
   @Override
   public IRtmContentEditService getContentEditService()
   {
      return new RtmContentEditService( rtmConnectionFactory,
                                        responseHandlerFactory );
   }
   
   
   
   @Override
   public IRtmAuthenticationService getAuthenticationService()
   {
      return new RtmAuthenticationService( rtmConnectionFactory,
                                           responseHandlerFactory );
   }
   
   
   
   @Override
   public IRtmSyncService getSyncService( IRtmSyncPartner syncPartner )
   {
      final IRtmContentEditService contentEditService = getContentEditService();
      
      return new RtmSyncService( syncPartner,
                                 getContentRepository(),
                                 contentEditService,
                                 new RtmSyncHandlerFactory( syncPartner ),
                                 new RtmTimelineFactory( contentEditService ) );
   }
   
}
