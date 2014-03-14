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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Contact;
import dev.drsoran.moloko.test.MolokoTestCase;


public class ContactFixture extends MolokoTestCase
{
   @Test
   public void testContact()
   {
      new Contact( 1, "user", "full" );
   }
   
   
   
   @Test
   public void testContactNoId()
   {
      new Contact( Constants.NO_ID, "user", "full" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testContactNullUser()
   {
      new Contact( 1, null, "full" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testContactEmptyUser()
   {
      new Contact( 1, "", "full" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testContactNullName()
   {
      new Contact( 1, "user", null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testContactEmptyName()
   {
      new Contact( 1, "user", "" );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( new Contact( 1L, "user", "full" ).getId(), is( 1L ) );
   }
   
   
   
   @Test
   public void testGetUsername()
   {
      assertThat( new Contact( 1L, "user", "full" ).getUsername(), is( "user" ) );
   }
   
   
   
   @Test
   public void testGetFullname()
   {
      assertThat( new Contact( 1L, "user", "full" ).getFullname(), is( "full" ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new Contact( 1L, "user", "full" ).toString();
   }
}
