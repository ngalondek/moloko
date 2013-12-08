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

package dev.drsoran.test.unit.rtm.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.ClassRule;
import org.junit.Test;

import dev.drsoran.moloko.test.XmlFileResource;
import dev.drsoran.rtm.model.RtmContact;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContactContentHandler;
import dev.drsoran.rtm.rest.RtmContentHandler;


public class RtmContactContentHandlerFixture extends
         RtmContentHandlerTestCase< RtmContact >
{
   @ClassRule
   public static final XmlFileResource testFile = new XmlFileResource( RtmContactContentHandlerFixture.class,
                                                                       "RtmContact.xml" );
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmContactContentHandlerIRtmContentHandlerListenerOfRtmContact()
   {
      new RtmContactContentHandler( EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
   
   
   
   @Test
   public void testReadContact() throws Exception
   {
      final RtmContact contact = readContent( testFile );
      
      assertThat( contact.getId(), is( "1" ) );
      assertThat( contact.getFullname(), is( "Omar Kilani" ) );
      assertThat( contact.getUsername(), is( "omar" ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmContact > createHandler()
   {
      return new RtmContactContentHandler();
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmContact > createHandlerWithListener( IRtmContentHandlerListener< RtmContact > listener )
   {
      return new RtmContactContentHandler( listener );
   }
   
   
   
   @Override
   protected RtmContact createDummyContent()
   {
      return new RtmContact( "1", "user", "full" );
   }
}
