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
   public void testGetRtmMethod()
   {
      assertThat( createRequest().getRtmMethod(), is( "method" ) );
   }
   
   
   
   @Test
   public void testGetMethodExecutionUri()
   {
      assertThat( createRequest( new Param( "p1", "v1" ),
                                 new Param( "p2", "v2" ) ).getMethodExecutionUri(),
                  is( "?api_key=key&api_sig=8d0a9c4490413c8bfc57aaf4857b84d9&p1=v1&p2=v2" ) );
   }
   
   
   
   @Test
   public void testGetMethodExecutionUri_Empty()
   {
      assertThat( createRequest().getMethodExecutionUri(),
                  is( "?api_key=key&api_sig=2663dceff40088f756b984592465d482" ) );
   }
   
   
   
   @Test
   public void testAddParam()
   {
      RtmRestRequest req = createRequest( new Param( "p1", "v1" ),
                                          new Param( "p2", "v2" ) );
      req.addParam( new Param( "p3", "v3" ) );
      
      assertThat( req.getMethodExecutionUri(),
                  is( "?api_key=key&api_sig=22cadd9162eca0fb27b1453b2fe6556e&p1=v1&p2=v2&p3=v3" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      createRequest().toString();
   }
   
   
   
   private RtmRestRequest createRequest( Param... params )
   {
      return new RtmRestRequest( "method",
                                 new RtmRequestUriBuilder( "key", "secret" ).addAll( params ) );
   }
}
