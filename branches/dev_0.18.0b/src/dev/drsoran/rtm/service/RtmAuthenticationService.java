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
   
   private RtmFrob frob;
   
   private RtmAuth rtmAuth;
   
   private final IRtmResponseHandlerFactory responseHandlerFactory;
   
   
   
   public RtmAuthenticationService( IRtmConnectionFactory connectionFactory,
      IRtmResponseHandlerFactory responseHandlerFactory )
   {
      this.connectionFactory = connectionFactory;
      this.responseHandlerFactory = responseHandlerFactory;
   }
   
   
   
   @Override
   public String beginAuthorization( RtmServicePermission permissions ) throws RtmServiceException
   {
      auth_getFrob();
      
      final RtmRequestUriBuilder requestUriBuilder = connectionFactory.createUriBuilder();
      
      requestUriBuilder.setServiceUrl( AUTH_SERVICE_URL_POSTFIX );
      requestUriBuilder.addAll( new Param( "perms", permissions.toString() ),
                                new Param( "frob", frob.getValue() ) );
      
      return requestUriBuilder.build();
   }
   
   
   
   @Override
   public RtmAuth completeAuthorization() throws RtmServiceException
   {
      auth_getToken();
      return rtmAuth;
   }
   
   
   
   @Override
   public boolean isServiceAuthorized( String authToken ) throws RtmServiceException
   {
      try
      {
         auth_checkToken( authToken );
         return true;
      }
      catch ( RtmServiceException e )
      {
         if ( RtmServiceConstants.RtmErrorCodes.isAuthError( e.getResponseCode() ) )
         {
            return false;
         }
         
         throw e;
      }
   }
   
   
   
   private void auth_getFrob() throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmFrob > response = rtmConnection.executeMethod( responseHandlerFactory.createRtmFrobResponseHandler(),
                                                                           "rtm.auth.getFrob" );
      frob = response.getElement();
   }
   
   
   
   private void auth_getToken() throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      final RtmResponse< RtmAuth > response = rtmConnection.executeMethod( responseHandlerFactory.createRtmAuthResponseHandler(),
                                                                           "rtm.auth.getToken",
                                                                           new Param( "frob",
                                                                                      frob.getValue() ) );
      rtmAuth = response.getElement();
   }
   
   
   
   private void auth_checkToken( String authToken ) throws RtmServiceException
   {
      final IRtmConnection rtmConnection = connectionFactory.createRtmConnection();
      rtmConnection.executeMethod( responseHandlerFactory.createRtmAuthResponseHandler(),
                                   "rtm.auth.checkToken",
                                   new Param( "auth_token", authToken ) );
   }
}
