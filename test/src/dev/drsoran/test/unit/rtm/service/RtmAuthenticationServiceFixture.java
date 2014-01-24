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

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.rtm.IRtmConnection;
import dev.drsoran.rtm.IRtmConnectionFactory;
import dev.drsoran.rtm.IRtmResponseHandler;
import dev.drsoran.rtm.IRtmResponseHandlerFactory;
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.RtmRequestUriBuilder;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmAuthHandle;
import dev.drsoran.rtm.service.RtmAuthenticationService;
import dev.drsoran.rtm.service.RtmErrorCodes;
import dev.drsoran.rtm.service.RtmFrob;
import dev.drsoran.rtm.service.RtmServicePermission;
import dev.drsoran.rtm.service.RtmUser;


public class RtmAuthenticationServiceFixture
{
   
   @Test
   public void testRtmAuthenticationService()
   {
      new RtmAuthenticationService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                    EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmAuthenticationService_NullConnectionFacory()
   {
      new RtmAuthenticationService( null,
                                    EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmAuthenticationService_NullResponseHandlerFactory()
   {
      new RtmAuthenticationService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                    null );
   }
   
   
   
   @Test
   public void testBeginAuthorization() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final IRtmResponseHandler< RtmFrob > frobHandler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( frobHandler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmFrobResponseHandler() )
              .andReturn( frobHandler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( frobHandler ),
                                                    EasyMock.eq( "rtm.auth.getFrob" ) ) )
              .andReturn( new RtmResponse< RtmFrob >( new RtmFrob( "123456" ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.expect( connFact.createUriBuilder() )
              .andReturn( new RtmRequestUriBuilder( "key", "secret" ) );
      EasyMock.replay( connFact );
      
      final RtmAuthenticationService authService = new RtmAuthenticationService( connFact,
                                                                                 responseHandlerFactory );
      final RtmAuthHandle authHandle = authService.beginAuthorization( RtmServicePermission.delete );
      
      assertThat( authHandle, notNullValue() );
      assertThat( authHandle.getAuthUri(),
                  is( "/services/auth/?api_key=key&api_sig=688d0f9d8987e90151d9a57de4d59ecc&frob=123456&perms=delete" ) );
      assertThat( authHandle.getFrob().getValue(), is( "123456" ) );
      
      EasyMock.verify( frobHandler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test
   public void testCompleteAuthorization() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final IRtmResponseHandler< RtmAuth > authHandler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( authHandler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmAuthResponseHandler() )
              .andReturn( authHandler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( authHandler ),
                                                    EasyMock.eq( "rtm.auth.getToken" ),
                                                    EasyMock.eq( new Param( "frob",
                                                                            "123456" ) ) ) )
              .andReturn( new RtmResponse< RtmAuth >( new RtmAuth( "token",
                                                                   RtmServicePermission.delete,
                                                                   EasyMock.createNiceMock( RtmUser.class ) ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmAuthenticationService authService = new RtmAuthenticationService( connFact,
                                                                                 responseHandlerFactory );
      final RtmAuth auth = authService.completeAuthorization( new RtmFrob( "123456" ) );
      
      assertThat( auth, notNullValue() );
      assertThat( auth.getToken(), is( "token" ) );
      assertThat( auth.getPermissions(), is( RtmServicePermission.delete ) );
      
      EasyMock.verify( authHandler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testCompleteAuthorization_nullFrob() throws Exception
   {
      final RtmAuthenticationService authService = new RtmAuthenticationService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                                                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
      
      authService.completeAuthorization( null );
   }
   
   
   
   @Test
   public void testIsServiceAuthorized_True() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final IRtmResponseHandler< RtmAuth > authHandler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( authHandler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmAuthResponseHandler() )
              .andReturn( authHandler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( authHandler ),
                                                    EasyMock.eq( "rtm.auth.checkToken" ),
                                                    EasyMock.eq( new Param( "auth_token",
                                                                            "token" ) ) ) )
              .andReturn( new RtmResponse< RtmAuth >( new RtmAuth( "token",
                                                                   RtmServicePermission.delete,
                                                                   EasyMock.createNiceMock( RtmUser.class ) ) ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmAuthenticationService authService = new RtmAuthenticationService( connFact,
                                                                                 responseHandlerFactory );
      final boolean isAuthorized = authService.isServiceAuthorized( "token" );
      
      assertThat( isAuthorized, is( true ) );
      
      EasyMock.verify( authHandler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test
   public void testIsServiceAuthorized_AuthError() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final IRtmResponseHandler< RtmAuth > authHandler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( authHandler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmAuthResponseHandler() )
              .andReturn( authHandler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( authHandler ),
                                                    EasyMock.eq( "rtm.auth.checkToken" ),
                                                    EasyMock.eq( new Param( "auth_token",
                                                                            "token" ) ) ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.INVALID_AUTH_TOKEN,
                                                  "Not authorized" ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      final RtmAuthenticationService authService = new RtmAuthenticationService( connFact,
                                                                                 responseHandlerFactory );
      final boolean isAuthorized = authService.isServiceAuthorized( "token" );
      
      assertThat( isAuthorized, is( false ) );
      
      EasyMock.verify( authHandler );
      EasyMock.verify( responseHandlerFactory );
      EasyMock.verify( rtmConnection );
      EasyMock.verify( connFact );
   }
   
   
   
   @Test( expected = RtmServiceException.class )
   public void testIsServiceAuthorized_OtherError() throws RtmServiceException
   {
      @SuppressWarnings( "unchecked" )
      final IRtmResponseHandler< RtmAuth > authHandler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( authHandler );
      
      final IRtmResponseHandlerFactory responseHandlerFactory = EasyMock.createStrictMock( IRtmResponseHandlerFactory.class );
      EasyMock.expect( responseHandlerFactory.createRtmAuthResponseHandler() )
              .andReturn( authHandler );
      EasyMock.replay( responseHandlerFactory );
      
      final IRtmConnection rtmConnection = EasyMock.createStrictMock( IRtmConnection.class );
      EasyMock.expect( rtmConnection.executeMethod( EasyMock.same( authHandler ),
                                                    EasyMock.eq( "rtm.auth.checkToken" ),
                                                    EasyMock.eq( new Param( "auth_token",
                                                                            "token" ) ) ) )
              .andThrow( new RtmServiceException( RtmErrorCodes.SERVICE_UNAVAILABLE,
                                                  "503" ) );
      EasyMock.replay( rtmConnection );
      
      final IRtmConnectionFactory connFact = EasyMock.createStrictMock( IRtmConnectionFactory.class );
      EasyMock.expect( connFact.createRtmConnection() )
              .andReturn( rtmConnection );
      EasyMock.replay( connFact );
      
      try
      {
         final RtmAuthenticationService authService = new RtmAuthenticationService( connFact,
                                                                                    responseHandlerFactory );
         authService.isServiceAuthorized( "token" );
      }
      finally
      {
         EasyMock.verify( authHandler );
         EasyMock.verify( responseHandlerFactory );
         EasyMock.verify( rtmConnection );
         EasyMock.verify( connFact );
      }
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testCompleteAuthorization_nullAuthToken() throws Exception
   {
      final RtmAuthenticationService authService = new RtmAuthenticationService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                                                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
      
      authService.isServiceAuthorized( null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testCompleteAuthorization_emptyAuthToken() throws Exception
   {
      final RtmAuthenticationService authService = new RtmAuthenticationService( EasyMock.createNiceMock( IRtmConnectionFactory.class ),
                                                                                 EasyMock.createNiceMock( IRtmResponseHandlerFactory.class ) );
      
      authService.isServiceAuthorized( "" );
   }
}
