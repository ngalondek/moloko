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

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.Param;
import dev.drsoran.rtm.rest.RtmRestRequest;


public class RtmRestRequestFixture extends MolokoTestCase
{
   @Test
   public void testRtmRestRequest()
   {
      new RtmRestRequest( "method", Collections.< Param > emptyList() );
   }
   
   
   
   @Test
   public void testGetRtmMethod()
   {
      assertThat( createRequest( Collections.< Param > emptyList() ).getRtmMethod(),
                  is( "method" ) );
   }
   
   
   
   @Test
   public void testGetMethodExecutionUri()
   {
      assertThat( createRequest( Arrays.asList( new Param( "p1", "v1" ),
                                                new Param( "p2", "v2" ) ) ).getMethodExecutionUri(),
                  is( "/services/rest/?p1=v1&p2=v2&" ) );
   }
   
   
   
   @Test
   public void testGetMethodExecutionUri_Empty()
   {
      assertThat( createRequest( Collections.< Param > emptyList() ).getMethodExecutionUri(),
                  is( "/services/rest/" ) );
   }
   
   
   
   @Test
   public void testGetParameters()
   {
      assertThat( createRequest( Arrays.asList( new Param( "p1", "v1" ),
                                                new Param( "p2", "v2" ) ) ).getParameters(),
                  hasItems( new Param( "p1", "v1" ), new Param( "p2", "v2" ) ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      createRequest( Collections.< Param > emptyList() ).toString();
   }
   
   
   
   private RtmRestRequest createRequest( Collection< Param > params )
   {
      return new RtmRestRequest( "method", params );
   }
}
