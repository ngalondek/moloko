/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.rtm;

import dev.drsoran.Strings;
import dev.drsoran.moloko.ILog;


public class RtmConnectionFactory implements IRtmConnectionFactory
{
   public final static String SERVER_HOST_NAME = "api.rememberthemilk.com";
   
   private final ILog log;
   
   private final IConnectionFactory connectionFactory;
   
   private final IRtmRequestLimiter requestLimiter;
   
   private final IRtmRequestFactory requestFactory;
   
   private final RtmConnectionProtocol protocol;
   
   private final String apiKey;
   
   private final String sharedSecret;
   
   private final String authToken;
   
   
   
   public RtmConnectionFactory( ILog log, IConnectionFactory connectionFactory,
      IRtmRequestFactory requestFactory, IRtmRequestLimiter requestLimiter,
      RtmConnectionProtocol protocol, String apiKey, String sharedSecret,
      String authToken )
   {
      if ( log == null )
      {
         throw new IllegalArgumentException( "log" );
      }
      if ( connectionFactory == null )
      {
         throw new IllegalArgumentException( "connectionFactory" );
      }
      if ( requestFactory == null )
      {
         throw new IllegalArgumentException( "requestFactory" );
      }
      if ( requestLimiter == null )
      {
         throw new IllegalArgumentException( "requestLimiter" );
      }
      if ( Strings.isNullOrEmpty( apiKey ) )
      {
         throw new IllegalArgumentException( "apiKey" );
      }
      if ( Strings.isNullOrEmpty( sharedSecret ) )
      {
         throw new IllegalArgumentException( "sharedSecret" );
      }
      if ( Strings.EMPTY_STRING.equals( authToken ) )
      {
         throw new IllegalArgumentException( "authToken" );
      }
      
      this.log = log;
      this.connectionFactory = connectionFactory;
      this.requestFactory = requestFactory;
      this.requestLimiter = requestLimiter;
      this.protocol = protocol;
      this.apiKey = apiKey;
      this.sharedSecret = sharedSecret;
      this.authToken = authToken;
   }
   
   
   
   public String getAuthToken()
   {
      return authToken;
   }
   
   
   
   @Override
   public RtmRequestUriBuilder createUriBuilder()
   {
      return new RtmRequestUriBuilder( apiKey, sharedSecret ).setHost( protocol,
                                                                       SERVER_HOST_NAME )
                                                             .setAuthToken( authToken );
   }
   
   
   
   @Override
   public IRtmConnection createRtmConnection()
   {
      return new RtmConnection( log,
                                connectionFactory,
                                requestFactory,
                                requestLimiter,
                                createUriBuilder() );
   }
}
