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

package dev.drsoran.rtm.test.unit.model;

import static dev.drsoran.rtm.test.TestConstants.RTM_NO_ID;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.rtm.model.RtmContact;


public class RtmContactFixture
{
   
   @Test
   public void testContact()
   {
      createContact();
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testContactNoId()
   {
      new RtmContact( RTM_NO_ID, "name", "user" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testContactNullName()
   {
      new RtmContact( "1", null, "user" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testContactNullUser()
   {
      new RtmContact( "1", "name", null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testContactEmptyUser()
   {
      new RtmContact( "1", "name", "" );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( createContact().getId(), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetFullname()
   {
      assertThat( createContact().getFullname(), is( "name" ) );
   }
   
   
   
   @Test
   public void testGetUsername()
   {
      assertThat( createContact().getUsername(), is( "user" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      createContact().toString();
   }
   
   
   
   private RtmContact createContact()
   {
      return new RtmContact( "1", "user", "name" );
   }
}
