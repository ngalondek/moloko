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
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.RtmErrorContentHandler;
import dev.drsoran.rtm.service.RtmError;


public class RtmErrorContentHandlerFixture extends
         RtmContentHandlerTestCase< RtmError >
{
   @ClassRule
   public static final XmlFileResource testFile = new XmlFileResource( RtmErrorContentHandlerFixture.class,
                                                                       "RtmError.xml" );
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmErrorContentHandlerIRtmContentHandlerListenerOfRtmError()
   {
      new RtmErrorContentHandler( EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
   
   
   
   @Test
   public void testReadError() throws Exception
   {
      final RtmError error = readContent( testFile );
      
      assertThat( error.getCode(), is( 98 ) );
      assertThat( error.getMessage(), is( "Login failed / Invalid auth token" ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmError > createHandler()
   {
      return new RtmErrorContentHandler( null );
   }
   
   
   
   @Override
   protected RtmContentHandler< RtmError > createHandlerWithListener( IRtmContentHandlerListener< RtmError > listener )
   {
      return new RtmErrorContentHandler( listener );
   }
   
   
   
   @Override
   protected RtmError createDummyContent()
   {
      return new RtmError( 1, "" );
   }
}
