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

package dev.drsoran.moloko.test.unit.domain.model;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;

import dev.drsoran.moloko.domain.model.Participant;


public class ParticipantFixture extends ModelTestCase
{
   
   @Test
   public void testParticipant()
   {
      createParticipant();
      new Participant( ID, 2, "", "user" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParticipantNoId()
   {
      new Participant( NO_ID, 2, "name", "user" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParticipantNoContactId()
   {
      new Participant( ID, NO_ID, "name", "user" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParticipantNullName()
   {
      new Participant( ID, 2, null, "user" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParticipantNullUser()
   {
      new Participant( ID, 2, "name", null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testParticipantEmptyUser()
   {
      new Participant( ID, 2, "name", "" );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( createParticipant().getId(), is( ID ) );
   }
   
   
   
   @Test
   public void testGetContactId()
   {
      assertThat( createParticipant().getContactId(), is( 2L ) );
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
   
   
   
   private Participant createParticipant()
   {
      return new Participant( ID, 2, "name", "user" );
   }
}
