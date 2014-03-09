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
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.RtmRequestUriBuilder;
import dev.drsoran.rtm.rest.RtmRestRequest;


public class RtmRestRequestFixture extends MolokoTestCase
{
   @Test
   public void testRtmRestRequest()
   {
      createRequest();
   }
   
   
   
   @Test
   public void testGetMethodExecutionUri()
   {
      assertThat( createRequest( new Param( "p1", "v1" ),
                                 new Param( "p2", "v2" ) ).getMethodExecutionUri(),
                  is( "?api_key=key&api_sig=24b4c661351e887b0a7da294febd376d&method=rtm.some.action&p1=v1&p2=v2" ) );
   }
   
   
   
   @Test
   public void testGetMethodExecutionUri_Empty()
   {
      assertThat( createRequest().getMethodExecutionUri(),
                  is( "?api_key=key&api_sig=5ce802e90f383816f893ac8f932400fa&method=rtm.some.action" ) );
   }
   
   
   
   @Test
   public void testAddParam()
   {
      RtmRestRequest req = createRequest( new Param( "p1", "v1" ),
                                          new Param( "p2", "v2" ) );
      req.addParam( new Param( "p3", "v3" ) );
      
      assertThat( req.getMethodExecutionUri(),
                  is( "?api_key=key&api_sig=cab0300d9291a0bf6a78eb8023abf311&method=rtm.some.action&p1=v1&p2=v2&p3=v3" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      createRequest().toString();
   }
   
   
   
   private RtmRestRequest createRequest( Param... params )
   {
      return new RtmRestRequest( "rtm.some.action",
                                 new RtmRequestUriBuilder( "key", "secret" ).addAll( params ) );
   }
}
