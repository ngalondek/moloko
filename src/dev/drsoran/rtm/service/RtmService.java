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
import dev.drsoran.rtm.IRtmRequestFactory;
import dev.drsoran.rtm.IRtmRequestLimiter;
import dev.drsoran.rtm.IRtmResponseHandlerFactory;
import dev.drsoran.rtm.RtmConnectionFactory;
import dev.drsoran.rtm.RtmConnectionProtocol;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;
import dev.drsoran.rtm.rest.RtmRestRequestFactory;
import dev.drsoran.rtm.rest.RtmRestResponseHandlerFactory;
import dev.drsoran.rtm.sync.IRtmSyncPartner;
import dev.drsoran.rtm.sync.RtmSyncHandlerFactory;
import dev.drsoran.rtm.sync.RtmTimelineFactory;


public class RtmService implements IRtmService
{
   private final IRtmRequestLimiter requestLimiter = new BlockingRtmRequestLimiter();
   
   private final IRtmRequestFactory requestFactory = new RtmRestRequestFactory();
   
   private final IRtmResponseHandlerFactory responseHandlerFactory;
   
   private final ILog log;
   
   private final IConnectionFactory connectionFactory;
   
   private final RtmConnectionProtocol protocol;
   
   private final String apiKey;
   
   private final String sharedSecret;
   
   
   
   public RtmService( ILog log, IConnectionFactory connectionFactory,
      IRtmCalendarProvider calendarProvider, RtmConnectionProtocol protocol,
      String apiKey, String sharedSecret )
   {
      this.log = log;
      this.connectionFactory = connectionFactory;
      this.protocol = protocol;
      this.apiKey = apiKey;
      this.sharedSecret = sharedSecret;
      
      this.responseHandlerFactory = new RtmRestResponseHandlerFactory( calendarProvider );
   }
   
   
   
   @Override
   public IRtmAuthenticationService getAuthenticationService()
   {
      return new RtmAuthenticationService( createConnectionFactory( null ),
                                           responseHandlerFactory );
   }
   
   
   
   @Override
   public IRtmContentRepository getContentRepository( String authToken )
   {
      return new RtmContentRepository( createConnectionFactory( authToken ),
                                       responseHandlerFactory );
   }
   
   
   
   @Override
   public IRtmContentEditService getContentEditService( String authToken )
   {
      return new RtmContentEditService( createConnectionFactory( authToken ),
                                        responseHandlerFactory );
   }
   
   
   
   @Override
   public IRtmSyncService getSyncService( IRtmSyncPartner syncPartner,
                                          String authToken )
   {
      final IRtmContentEditService contentEditService = getContentEditService( authToken );
      
      return new RtmSyncService( syncPartner,
                                 getContentRepository( authToken ),
                                 contentEditService,
                                 new RtmSyncHandlerFactory( syncPartner ),
                                 new RtmTimelineFactory( contentEditService ) );
   }
   
   
   
   private IRtmConnectionFactory createConnectionFactory( String authToken )
   {
      return new RtmConnectionFactory( log,
                                       connectionFactory,
                                       requestFactory,
                                       requestLimiter,
                                       protocol,
                                       apiKey,
                                       sharedSecret,
                                       authToken );
   }
}
