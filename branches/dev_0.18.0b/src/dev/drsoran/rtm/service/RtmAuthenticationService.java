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

package dev.drsoran.rtm.service;

import dev.drsoran.Strings;
import dev.drsoran.rtm.IRtmConnection;
import dev.drsoran.rtm.IRtmConnectionFactory;
import dev.drsoran.rtm.IRtmResponseHandlerFactory;
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.RtmRequestUriBuilder;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;


public class RtmAuthenticationService implements IRtmAuthenticationService
{
   private final static String AUTH_SERVICE_URL_POSTFIX = "/services/auth/";
   
   private final IRtmConnectionFactory connectionFactory;
   
   private final IRtmResponseHandlerFactory responseHandlerFactory;
   
   
   
   public RtmAuthenticationService( IRtmConnectionFactory connectionFactory,
      IRtmResponseHandlerFactory responseHandlerFactory )
   {
      if ( connectionFactory == null )
      {
         throw new IllegalArgumentException( "connectionFactory" );
      }
      
      if ( responseHandlerFactory == null )
      {
         throw new IllegalArgumentException( "responseHandlerFactory" );
      }
      
      this.connectionFactory = connectionFactory;
      this.responseHandlerFactory = responseHandlerFactory;
   }
   
   
   
   @Override
   public RtmAuthHandle beginAuthorization( RtmServicePermission permissions ) throws RtmServiceException
   {
      final RtmFrob frob = auth_getFrob();
      
      final RtmRequestUriBuilder requestUriBuilder = connectionFactory.createUriBuilder();
      
      requestUriBuilder.setServiceUrl( AUTH_SERVICE_URL_POSTFIX );
      requestUriBuilder.addAll( new Param( "perms", permissions.toString() ),
                                new Param( "frob", frob.getValue() ) );
      
      return new RtmAuthHandle( requestUriBuilder.build(), frob );
   }
   
   
   
   @Override
   public RtmAuth completeAuthorization( RtmFrob frob ) throws RtmServiceException
   {
      if ( frob == null )
      {
         throw new IllegalArgumentException( "frob" );
      }
      
      return auth_getToken( frob );
   }
   
   
   
   @Override
   public boolean isServiceAuthorized( String authToken ) throws RtmServiceException
   {
      if ( Strings.isNullOrEmpty( authToken ) )
      {
         throw new IllegalArgumentException( "authToken" );
      }
      
      try
      {
         auth_checkToken( authToken );
         return true;
      }
      catch ( RtmServiceException e )
      {
         if ( RtmErrorCodes.isAuthError( e.getResponseCode() ) )
         {
            return false;
         }
         
         throw e;
      }
   }
   
   
   
   private RtmFrob auth_getFrob() throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmFrob > response = rtmConnection.executeMethod( responseHandlerFactory.createRtmFrobResponseHandler(),
                                                                           "rtm.auth.getFrob" );
      return response.getElement();
   }
   
   
   
   private RtmAuth auth_getToken( RtmFrob frob ) throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmAuth > response = rtmConnection.executeMethod( responseHandlerFactory.createRtmAuthResponseHandler(),
                                                                           "rtm.auth.getToken",
                                                                           new Param( "frob",
                                                                                      frob.getValue() ) );
      return response.getElement();
   }
   
   
   
   private void auth_checkToken( String authToken ) throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      rtmConnection.executeMethod( responseHandlerFactory.createRtmAuthResponseHandler(),
                                   "rtm.auth.checkToken",
                                   new Param( "auth_token", authToken ) );
   }
}
