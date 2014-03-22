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

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.rtm.service.RtmAuthHandle;
import dev.drsoran.rtm.service.RtmFrob;


public class RtmAuthHandleFixture
{
   @Test
   public void testRtmAuthHandle()
   {
      new RtmAuthHandle( "uri", EasyMock.createNiceMock( RtmFrob.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmAuthHandle_NullUri()
   {
      new RtmAuthHandle( null, EasyMock.createNiceMock( RtmFrob.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmAuthHandle_NullFrob()
   {
      new RtmAuthHandle( "uri", null );
   }
   
   
   
   @Test
   public void testGetAuthUri()
   {
      assertThat( new RtmAuthHandle( "uri",
                                     EasyMock.createNiceMock( RtmFrob.class ) ).getAuthUri(),
                  is( "uri" ) );
   }
   
   
   
   @Test
   public void testGetFrob()
   {
      RtmFrob frob = EasyMock.createNiceMock( RtmFrob.class );
      assertThat( new RtmAuthHandle( "uri", frob ).getFrob(), is( frob ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmAuthHandle( "uri", EasyMock.createNiceMock( RtmFrob.class ) ).toString();
   }
   
}
