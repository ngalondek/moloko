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

import dev.drsoran.moloko.domain.model.Priority;


public class PriorityFixture
{
   
   @Test
   public void testToString()
   {
      assertThat( Priority.High.toString(), is( "1" ) );
      assertThat( Priority.Medium.toString(), is( "2" ) );
      assertThat( Priority.Low.toString(), is( "3" ) );
      assertThat( Priority.None.toString(), is( "n" ) );
   }
   
   
   
   @Test
   public void testFromString()
   {
      assertThat( Priority.fromString( "1" ), is( Priority.High ) );
      assertThat( Priority.fromString( "2" ), is( Priority.Medium ) );
      assertThat( Priority.fromString( "3" ), is( Priority.Low ) );
      assertThat( Priority.fromString( "n" ), is( Priority.None ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testFromStringInvalid()
   {
      Priority.fromString( "4" );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testFromStringNull()
   {
      Priority.fromString( null );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testFromStringEmpty()
   {
      Priority.fromString( "" );
   }
}
