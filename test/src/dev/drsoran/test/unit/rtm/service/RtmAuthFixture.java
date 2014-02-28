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
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmServicePermission;
import dev.drsoran.rtm.service.RtmUser;


public class RtmAuthFixture
{
   @Test
   public void testRtmAuth()
   {
      new RtmAuth( "token",
                   RtmServicePermission.delete,
                   EasyMock.createNiceMock( RtmUser.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmAuth_NullToken()
   {
      new RtmAuth( null,
                   RtmServicePermission.delete,
                   EasyMock.createNiceMock( RtmUser.class ) );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmAuth_NullUser()
   {
      new RtmAuth( "token", RtmServicePermission.delete, null );
   }
   
   
   
   @Test
   public void testGetToken()
   {
      assertThat( new RtmAuth( "token",
                               RtmServicePermission.delete,
                               EasyMock.createNiceMock( RtmUser.class ) ).getToken(),
                  is( "token" ) );
   }
   
   
   
   @Test
   public void testGetPermissions()
   {
      assertThat( new RtmAuth( "token",
                               RtmServicePermission.delete,
                               EasyMock.createNiceMock( RtmUser.class ) ).getPermissions(),
                  is( RtmServicePermission.delete ) );
   }
   
   
   
   @Test
   public void testGetUser()
   {
      final RtmUser user = EasyMock.createNiceMock( RtmUser.class );
      assertThat( new RtmAuth( "token", RtmServicePermission.delete, user ).getUser(),
                  is( sameInstance( user ) ) );
   }
   
   
   
   @Test
   public void testToString()
   {
      new RtmAuth( "token",
                   RtmServicePermission.delete,
                   EasyMock.createNiceMock( RtmUser.class ) ).toString();
   }
}
