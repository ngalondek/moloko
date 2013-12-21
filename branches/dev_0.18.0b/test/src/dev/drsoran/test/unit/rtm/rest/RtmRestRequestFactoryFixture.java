/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.test.unit.rtm.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.IRtmRequest;
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.RtmRequestUriBuilder;
import dev.drsoran.rtm.rest.RtmRestRequestFactory;


public class RtmRestRequestFactoryFixture extends MolokoTestCase
{
   @Test
   public void testRtmRestRequestFactory()
   {
      new RtmRestRequestFactory();
   }
   
   
   
   @Test
   public void testCreateRequest()
   {
      RtmRestRequestFactory rtmRestRequestFactory = new RtmRestRequestFactory();
      IRtmRequest request = rtmRestRequestFactory.createRequest( "method",
                                                                 new RtmRequestUriBuilder( "key",
                                                                                           "secret" ).addParam(
                                                                 
                                                                 new Param( "p1",
                                                                            "v1" ) )
                                                                                                     .addParam( new Param( "p2",
                                                                                                                           "v2" ) )
                                                                                                     .addParam( new Param( "auth_token",
                                                                                                                           "auth_token" ) ) );
      assertThat( request.getRtmMethod(), is( "method" ) );
      assertThat( request.getMethodExecutionUri(),
                  is( "/services/rest/?api_key=key&api_sig=31c022baa99adf36a76e8fa0b42e1b86&auth_token=auth_token&p1=v1&p2=v2&" ) );
   }
}
