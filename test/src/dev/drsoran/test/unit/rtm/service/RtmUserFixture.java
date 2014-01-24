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
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.rtm.service.RtmUser;


public class RtmUserFixture
{
   @Test
   public void testRtmUser()
   {
      new RtmUser( "10", "username", "fullname" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmUser_NullId()
   {
      new RtmUser( null, "username", "fullname" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmUser_EmptyId()
   {
      new RtmUser( "", "username", "fullname" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmUser_NullUsername()
   {
      new RtmUser( "10", null, "fullname" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmUser_NullFullname()
   {
      new RtmUser( "10", "username", null );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( new RtmUser( "10", "username", "fullname" ).getId(),
                  is( "10" ) );
   }
   
   
   
   @Test
   public void testGetUsername()
   {
      assertThat( new RtmUser( "10", "username", "fullname" ).getUsername(),
                  is( "username" ) );
   }
   
   
   
   @Test
   public void testGetFullname()
   {
      assertThat( new RtmUser( "10", "username", "fullname" ).getFullname(),
                  is( "fullname" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmUser( "10", "username", "fullname" ).toString();
   }
}
