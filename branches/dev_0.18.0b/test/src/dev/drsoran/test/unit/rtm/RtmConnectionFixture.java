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

package dev.drsoran.test.unit.rtm;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.Reader;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.ILog;
import dev.drsoran.rtm.IConnection;
import dev.drsoran.rtm.IConnectionFactory;
import dev.drsoran.rtm.IRtmRequest;
import dev.drsoran.rtm.IRtmRequestFactory;
import dev.drsoran.rtm.IRtmRequestLimiter;
import dev.drsoran.rtm.IRtmResponseHandler;
import dev.drsoran.rtm.RtmConnection;
import dev.drsoran.rtm.RtmConnectionFactory;
import dev.drsoran.rtm.RtmConnectionProtocol;
import dev.drsoran.rtm.RtmRequestUriBuilder;
import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;


public class RtmConnectionFixture
{
   @Test
   public void testRtmConnection()
   {
      new RtmConnection( EasyMock.createMock( ILog.class ),
                         EasyMock.createMock( IConnectionFactory.class ),
                         EasyMock.createMock( IRtmRequestFactory.class ),
                         EasyMock.createMock( IRtmRequestLimiter.class ),
                         EasyMock.createMock( RtmRequestUriBuilder.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnection_NullLog()
   {
      new RtmConnection( null,
                         EasyMock.createMock( IConnectionFactory.class ),
                         EasyMock.createMock( IRtmRequestFactory.class ),
                         EasyMock.createMock( IRtmRequestLimiter.class ),
                         EasyMock.createMock( RtmRequestUriBuilder.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnection_NullConnFact()
   {
      new RtmConnection( EasyMock.createMock( ILog.class ),
                         null,
                         EasyMock.createMock( IRtmRequestFactory.class ),
                         EasyMock.createMock( IRtmRequestLimiter.class ),
                         EasyMock.createMock( RtmRequestUriBuilder.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnection_NullReqFact()
   {
      new RtmConnection( EasyMock.createMock( ILog.class ),
                         EasyMock.createMock( IConnectionFactory.class ),
                         null,
                         EasyMock.createMock( IRtmRequestLimiter.class ),
                         EasyMock.createMock( RtmRequestUriBuilder.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnection_NullReqLimit()
   {
      new RtmConnection( EasyMock.createMock( ILog.class ),
                         EasyMock.createMock( IConnectionFactory.class ),
                         EasyMock.createMock( IRtmRequestFactory.class ),
                         null,
                         EasyMock.createMock( RtmRequestUriBuilder.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnection_NullBuilder()
   {
      new RtmConnection( EasyMock.createMock( ILog.class ),
                         EasyMock.createMock( IConnectionFactory.class ),
                         EasyMock.createMock( IRtmRequestFactory.class ),
                         EasyMock.createMock( IRtmRequestLimiter.class ),
                         null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testExecuteMethod() throws Exception
   {
      final ILog log = EasyMock.createNiceMock( ILog.class );
      
      final RtmRequestUriBuilder reqBuilder = new RtmRequestUriBuilder( "key",
                                                                        "secret" );
      reqBuilder.setHost( RtmConnectionProtocol.https,
                          RtmConnectionFactory.SERVER_HOST_NAME );
      
      final Reader reader = EasyMock.createStrictMock( Reader.class );
      reader.close();
      EasyMock.replay( reader );
      
      final IConnection conn = EasyMock.createStrictMock( IConnection.class );
      EasyMock.expect( conn.execute( reqBuilder.build() ) ).andReturn( reader );
      conn.close();
      EasyMock.replay( conn );
      
      final IConnectionFactory connFact = EasyMock.createStrictMock( IConnectionFactory.class );
      EasyMock.expect( connFact.createConnection() ).andReturn( conn );
      EasyMock.replay( connFact );
      
      final IRtmRequestLimiter reqLimiter = EasyMock.createStrictMock( IRtmRequestLimiter.class );
      reqLimiter.obeyRtmRequestLimit();
      EasyMock.replay( reqLimiter );
      
      final IRtmRequest req = EasyMock.createStrictMock( IRtmRequest.class );
      EasyMock.expect( req.getMethodExecutionUri() )
              .andReturn( reqBuilder.build() );
      EasyMock.replay( req );
      
      final IRtmRequestFactory reqFact = EasyMock.createStrictMock( IRtmRequestFactory.class );
      EasyMock.expect( reqFact.createRequest( "rtm.method", reqBuilder ) )
              .andReturn( req );
      EasyMock.replay( reqFact );
      
      final IRtmResponseHandler< Integer > responseHandler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.expect( responseHandler.handleResponse( EasyMock.notNull( Reader.class ) ) )
              .andReturn( EasyMock.createNiceMock( RtmResponse.class ) );
      EasyMock.replay( responseHandler );
      
      final RtmConnection rtmConnection = new RtmConnection( log,
                                                             connFact,
                                                             reqFact,
                                                             reqLimiter,
                                                             reqBuilder );
      
      final RtmResponse< Integer > resp = rtmConnection.executeMethod( responseHandler,
                                                                       "rtm.method" );
      
      assertThat( resp, is( notNullValue() ) );
      
      EasyMock.verify( conn );
      EasyMock.verify( reader );
      EasyMock.verify( req );
      EasyMock.verify( connFact );
      EasyMock.verify( reqLimiter );
      EasyMock.verify( reqFact );
      EasyMock.verify( responseHandler );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test( expected = RtmServiceException.class )
   public void testExecuteMethod_IOException() throws Exception
   {
      final ILog log = EasyMock.createNiceMock( ILog.class );
      
      final RtmRequestUriBuilder reqBuilder = new RtmRequestUriBuilder( "key",
                                                                        "secret" );
      reqBuilder.setHost( RtmConnectionProtocol.https,
                          RtmConnectionFactory.SERVER_HOST_NAME );
      
      final IRtmRequestLimiter reqLimiter = EasyMock.createStrictMock( IRtmRequestLimiter.class );
      reqLimiter.obeyRtmRequestLimit();
      EasyMock.replay( reqLimiter );
      
      final IConnection conn = EasyMock.createStrictMock( IConnection.class );
      EasyMock.expect( conn.execute( reqBuilder.build() ) )
              .andThrow( new IOException() );
      conn.close();
      EasyMock.replay( conn );
      
      final IConnectionFactory connFact = EasyMock.createStrictMock( IConnectionFactory.class );
      EasyMock.expect( connFact.createConnection() ).andReturn( conn );
      EasyMock.replay( connFact );
      
      final IRtmRequest req = EasyMock.createStrictMock( IRtmRequest.class );
      EasyMock.expect( req.getMethodExecutionUri() )
              .andReturn( reqBuilder.build() );
      EasyMock.replay( req );
      
      final IRtmRequestFactory reqFact = EasyMock.createStrictMock( IRtmRequestFactory.class );
      EasyMock.expect( reqFact.createRequest( "rtm.method", reqBuilder ) )
              .andReturn( req );
      EasyMock.replay( reqFact );
      
      final IRtmResponseHandler< Integer > responseHandler = EasyMock.createStrictMock( IRtmResponseHandler.class );
      EasyMock.replay( responseHandler );
      
      final RtmConnection rtmConnection = new RtmConnection( log,
                                                             connFact,
                                                             reqFact,
                                                             reqLimiter,
                                                             reqBuilder );
      
      try
      {
         rtmConnection.executeMethod( responseHandler, "rtm.method" );
      }
      finally
      {
         EasyMock.verify( req );
         EasyMock.verify( connFact );
         EasyMock.verify( reqLimiter );
         EasyMock.verify( reqFact );
         EasyMock.verify( responseHandler );
      }
   }
}
