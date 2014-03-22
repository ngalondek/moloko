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

import dev.drsoran.rtm.model.RtmLocation;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContactContentHandler;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmLocationContentHandler;
import dev.drsoran.rtm.test.XmlFileResource;


public class RtmLocationContentHandlerFixture extends
         RtmContentHandlerTestCase< RtmLocation >
{
   @ClassRule
   public static final XmlFileResource testFile = new XmlFileResource( RtmLocationContentHandlerFixture.class,
                                                                       "RtmLocation.xml" );
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmContactContentHandlerIRtmContentHandlerListenerOfRtmContact()
   {
      new RtmContactContentHandler( EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
   
   
   
   @Test
   public void testReadLocation() throws Exception
   {
      final RtmLocation content = readContent( testFile );
      
      assertThat( content.getId(), is( "987654321" ) );
      assertThat( content.getName(), is( "Berlin" ) );
      assertThat( content.getLongitude(), is( 13.411508f ) );
      assertThat( content.getLatitude(), is( 52.524008f ) );
      assertThat( content.getZoom(), is( 9 ) );
      assertThat( content.getAddress(), is( "Berlin, Germany" ) );
      assertThat( content.isViewable(), is( true ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmLocation > createHandler()
   {
      return new RtmLocationContentHandler();
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmLocation > createHandlerWithListener( IRtmContentHandlerListener< RtmLocation > listener )
   {
      return new RtmLocationContentHandler( listener );
   }
   
   
   
   @Override
   protected RtmLocation createDummyContent()
   {
      return new RtmLocation( "1", "locName", 1.0f, 2.0f, "Address", true, 10 );
   }
}
