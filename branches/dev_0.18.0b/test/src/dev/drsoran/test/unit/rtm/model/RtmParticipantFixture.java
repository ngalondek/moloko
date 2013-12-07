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

package dev.drsoran.test.unit.rtm.model;

import static dev.drsoran.moloko.test.TestConstants.RTM_NO_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.model.RtmParticipant;


public class RtmParticipantFixture extends MolokoTestCase
{
   
   @Test
   public void testParticipant()
   {
      createParticipant();
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParticipantNoId()
   {
      new RtmParticipant( RTM_NO_ID, "2", "name", "user" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParticipantNoContactId()
   {
      new RtmParticipant( "1", RTM_NO_ID, "name", "user" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParticipantNullName()
   {
      new RtmParticipant( "1", "2", null, "user" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParticipantNullUser()
   {
      new RtmParticipant( "1", "2", "name", null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParticipantEmptyUser()
   {
      new RtmParticipant( "1", "2", "name", "" );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( createParticipant().getId(), is( "1" ) );
   }
   
   
   
   @Test
   public void testGetContactId()
   {
      assertThat( createParticipant().getContactId(), is( "2" ) );
   }
   
   
   
   @Test
   public void testGetFullname()
   {
      assertThat( createParticipant().getFullname(), is( "name" ) );
   }
   
   
   
   @Test
   public void testGetUsername()
   {
      assertThat( createParticipant().getUsername(), is( "user" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      createParticipant().toString();
   }
   
   
   
   private RtmParticipant createParticipant()
   {
      return new RtmParticipant( "1", "2", "name", "user" );
   }
}
