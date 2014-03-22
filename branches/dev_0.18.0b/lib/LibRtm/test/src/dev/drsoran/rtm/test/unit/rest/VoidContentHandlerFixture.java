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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.ClassRule;
import org.junit.Test;

import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.VoidContentHandler;
import dev.drsoran.rtm.test.XmlFileResource;


public class VoidContentHandlerFixture extends RtmContentHandlerTestCase< Void >
{
   @ClassRule
   public static final XmlFileResource testFile = new XmlFileResource( VoidContentHandlerFixture.class,
                                                                       "Void.xml" );
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testRtmFrobContentHandlerIRtmContentHandlerListenerOfRtmFrob()
   {
      new VoidContentHandler( EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
   
   
   
   @Test
   public void testReadVoid() throws Exception
   {
      final Void voidValue = readContent( testFile );
      
      assertThat( voidValue, is( nullValue() ) );
   }
   
   
   
   @Override
   protected RtmContentHandler< Void > createHandler()
   {
      return new VoidContentHandler( null );
   }
   
   
   
   @Override
   protected RtmContentHandler< Void > createHandlerWithListener( IRtmContentHandlerListener< Void > listener )
   {
      return new VoidContentHandler( listener );
   }
   
   
   
   @Override
   protected Void createDummyContent()
   {
      return null;
   }
}
