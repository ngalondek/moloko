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

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.ILog;
import dev.drsoran.rtm.IConnectionFactory;
import dev.drsoran.rtm.IRtmRequestFactory;
import dev.drsoran.rtm.IRtmRequestLimiter;
import dev.drsoran.rtm.RtmConnectionFactory;
import dev.drsoran.rtm.RtmConnectionProtocol;
import dev.drsoran.rtm.RtmRequestUriBuilder;


public class RtmConnectionFactoryFixture
{
   @Test
   public void testRtmConnectionFactory()
   {
      new RtmConnectionFactory( EasyMock.createNiceMock( ILog.class ),
                                EasyMock.createNiceMock( IConnectionFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestLimiter.class ),
                                RtmConnectionProtocol.https,
                                "key",
                                "secret" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnectionFactory_NullLog()
   {
      new RtmConnectionFactory( null,
                                EasyMock.createNiceMock( IConnectionFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestLimiter.class ),
                                RtmConnectionProtocol.https,
                                "key",
                                "secret" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnectionFactory_NullConnFact()
   {
      new RtmConnectionFactory( EasyMock.createNiceMock( ILog.class ),
                                null,
                                EasyMock.createNiceMock( IRtmRequestFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestLimiter.class ),
                                RtmConnectionProtocol.https,
                                "key",
                                "secret" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnectionFactory_NullReqFact()
   {
      new RtmConnectionFactory( EasyMock.createNiceMock( ILog.class ),
                                EasyMock.createNiceMock( IConnectionFactory.class ),
                                null,
                                EasyMock.createNiceMock( IRtmRequestLimiter.class ),
                                RtmConnectionProtocol.https,
                                "key",
                                "secret" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnectionFactory_NullReqLimit()
   {
      new RtmConnectionFactory( EasyMock.createNiceMock( ILog.class ),
                                EasyMock.createNiceMock( IConnectionFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestFactory.class ),
                                null,
                                RtmConnectionProtocol.https,
                                "key",
                                "secret" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnectionFactory_NullKey()
   {
      new RtmConnectionFactory( EasyMock.createNiceMock( ILog.class ),
                                EasyMock.createNiceMock( IConnectionFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestLimiter.class ),
                                RtmConnectionProtocol.https,
                                null,
                                "secret" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnectionFactory_EmptyKey()
   {
      new RtmConnectionFactory( EasyMock.createNiceMock( ILog.class ),
                                EasyMock.createNiceMock( IConnectionFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestLimiter.class ),
                                RtmConnectionProtocol.https,
                                "",
                                "secret" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnectionFactory_NullSecret()
   {
      new RtmConnectionFactory( EasyMock.createNiceMock( ILog.class ),
                                EasyMock.createNiceMock( IConnectionFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestLimiter.class ),
                                RtmConnectionProtocol.https,
                                "key",
                                null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmConnectionFactory_EmptySecret()
   {
      new RtmConnectionFactory( EasyMock.createNiceMock( ILog.class ),
                                EasyMock.createNiceMock( IConnectionFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestFactory.class ),
                                EasyMock.createNiceMock( IRtmRequestLimiter.class ),
                                RtmConnectionProtocol.https,
                                "key",
                                "" );
   }
   
   
   
   @Test
   public void testCreateUriBuilder()
   {
      RtmRequestUriBuilder builder = new RtmConnectionFactory( EasyMock.createNiceMock( ILog.class ),
                                                               EasyMock.createNiceMock( IConnectionFactory.class ),
                                                               EasyMock.createNiceMock( IRtmRequestFactory.class ),
                                                               EasyMock.createNiceMock( IRtmRequestLimiter.class ),
                                                               RtmConnectionProtocol.https,
                                                               "key",
                                                               "secret" ).createUriBuilder();
      
      assertThat( builder, is( notNullValue() ) );
      assertThat( builder.getApiKey(), is( "key" ) );
      assertThat( builder.getSharedSecret(), is( "secret" ) );
      assertThat( builder.getHost(), is( RtmConnectionFactory.SERVER_HOST_NAME ) );
      assertThat( builder.getScheme(), is( "https" ) );
      assertThat( builder.getPort(), is( RtmConnectionProtocol.https.getPort() ) );
   }
   
   
   
   @Test
   public void testCreateRtmConnection()
   {
      assertThat( new RtmConnectionFactory( EasyMock.createNiceMock( ILog.class ),
                                            EasyMock.createNiceMock( IConnectionFactory.class ),
                                            EasyMock.createNiceMock( IRtmRequestFactory.class ),
                                            EasyMock.createNiceMock( IRtmRequestLimiter.class ),
                                            RtmConnectionProtocol.https,
                                            "key",
                                            "secret" ).createRtmConnection(),
                  is( notNullValue() ) );
   }
}
