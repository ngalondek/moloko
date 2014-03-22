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

package dev.drsoran.rtm.test.unit.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.rtm.service.RtmFrob;


public class RtmFrobFixture
{
   @Test
   public void testRtmFrob()
   {
      new RtmFrob( "123" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmFrob_NullId()
   {
      new RtmFrob( null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmFrob_EmptyId()
   {
      new RtmFrob( "" );
   }
   
   
   
   @Test
   public void testGetValue()
   {
      assertThat( new RtmFrob( "123" ).getValue(), is( "123" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmFrob( "123" ).toString();
   }
}
