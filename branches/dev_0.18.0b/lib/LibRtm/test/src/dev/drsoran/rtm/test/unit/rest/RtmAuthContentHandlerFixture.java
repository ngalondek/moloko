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

package dev.drsoran.rtm.test.unit.rest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.ClassRule;
import org.junit.Test;

import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmAuthContentHandler;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmServicePermission;
import dev.drsoran.rtm.service.RtmUser;
import dev.drsoran.rtm.test.XmlFileResource;


public class RtmAuthContentHandlerFixture extends
         RtmContentHandlerTestCase< RtmAuth >
{
   @ClassRule
   public static final XmlFileResource testFile = new XmlFileResource( RtmAuthContentHandlerFixture.class,
                                                                       "RtmAuth.xml" );
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmAuthContentHandlerIRtmContentHandlerListenerOfRtmAuth()
   {
      new RtmAuthContentHandler( EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
   
   
   
   @Test
   public void testReadAuth() throws Exception
   {
      final RtmAuth auth = readContent( testFile );
      
      assertThat( auth.getToken(),
                  is( "410c57262293e9d937ee5be75eb7b0128fd61b61" ) );
      assertThat( auth.getPermissions(), is( RtmServicePermission.delete ) );
      assertThat( auth.getUser().getId(), is( "1" ) );
      assertThat( auth.getUser().getUsername(), is( "bob" ) );
      assertThat( auth.getUser().getFullname(), is( "Bob T. Monkey" ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmAuth > createHandler()
   {
      return new RtmAuthContentHandler( null );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmAuth > createHandlerWithListener( IRtmContentHandlerListener< RtmAuth > listener )
   {
      return new RtmAuthContentHandler( listener );
   }
   
   
   
   @Override
   protected RtmAuth createDummyContent()
   {
      return new RtmAuth( "token",
                          RtmServicePermission.delete,
                          new RtmUser( "1", "username", "fullname" ) );
   }
}
