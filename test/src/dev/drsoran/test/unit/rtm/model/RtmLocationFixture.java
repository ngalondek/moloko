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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmLocation;


public class RtmLocationFixture extends MolokoTestCase
{
   
   @Test
   public void testRtmLocation()
   {
      createLocation();
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmLocation_NoId()
   {
      new RtmLocation( RtmConstants.NO_ID,
                       "loc",
                       1.0f,
                       2.0f,
                       "Somewhere",
                       true,
                       10 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmLocation_NullName()
   {
      new RtmLocation( "id", null, 1.0f, 2.0f, "Somewhere", true, 10 );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmLocation_EmptyName()
   {
      new RtmLocation( "id", "", 1.0f, 2.0f, "Somewhere", true, 10 );
   }
   
   
   
   @Test
   public void testGetId()
   {
      assertThat( createLocation().getId(), is( "1" ) );
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
      assertThat( createLocation().getAddress(), is( "Somewhere" ) );
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
   
   
   
   private RtmLocation createLocation()
   {
      return new RtmLocation( "1", "loc", 1.0f, 2.0f, "Somewhere", true, 10 );
   }
}
