/* 
 *	Copyright (c) 2013 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.test.unit.domain.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.test.EqualsHashCodeTestCase;


public class LocationFixture extends EqualsHashCodeTestCase
{
   private final static long ID = 1L;
   
   private final static long NO_ID = Constants.NO_ID;
   
   
   
   @Test
   public void testLocation()
   {
      createLocation();
   }
   
   
   
   @Test
   public void testLocationNoId()
   {
      new Location( NO_ID, "loc", 1.0f, 2.0f, "addr", true, 10 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLocationNullName()
   {
      new Location( ID, null, 1.0f, 2.0f, "addr", true, 10 );
   }
   
   
   
   public void testLocationNullAddress()
   {
      new Location( ID, "loc", 1.0f, 2.0f, null, true, 10 );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( createLocation().getId(), is( ID ) );
   }
   
   
   
   @Test
   public void testGetName()
   {
      assertThat( createLocation().getName(), is( "loc" ) );
   }
   
   
   
   @Test
   public void testGetLongitude()
   {
      assertThat( createLocation().getLongitude(), is( 1.0f ) );
   }
   
   
   
   @Test
   public void testGetLatitude()
   {
      assertThat( createLocation().getLatitude(), is( 2.0f ) );
   }
   
   
   
   @Test
   public void testGetAddress()
   {
      assertThat( createLocation().getAddress(), is( "addr" ) );
   }
   
   
   
   @Test
   public void testIsViewable()
   {
      assertThat( createLocation().isViewable(), is( true ) );
   }
   
   
   
   @Test
   public void testGetZoom()
   {
      assertThat( createLocation().getZoom(), is( 10 ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      createLocation().toString();
   }
   
   
   
   @Override
   protected Object createEqualEqualsHashTestInstance() throws Exception
   {
      return new Location( ID, "loc", 1.0f, 2.0f, "addr", true, 10 );
   }
   
   
   
   @Override
   protected Object createNotEqualEqualsHashTestInstance() throws Exception
   {
      return new Location( ID, "otherLoc", 3.0f, 4.0f, null, true, 10 );
   }
   
   
   
   private Location createLocation()
   {
      return new Location( ID, "loc", 1.0f, 2.0f, "addr", true, 10 );
   }
}
