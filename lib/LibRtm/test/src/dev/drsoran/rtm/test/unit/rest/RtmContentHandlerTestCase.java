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
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RemoveWhiteSpaceXmlFilter;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.test.XmlFileResource;


public abstract class RtmContentHandlerTestCase< T >
{
   @Test
   public void testContentHandler()
   {
      createHandler();
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testContentHandlerIRtmContentHandlerListener()
   {
      createHandlerWithListener( EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
   
   
   
   @Test
   public void testSetContentElement()
   {
      RtmContentHandler< T > handler = createHandler();
      final T content = createDummyContent();
      handler.setContentElement( content );
      assertThat( handler.getContentElement(), is( content ) );
      
      @SuppressWarnings( "unchecked" )
      final IRtmContentHandlerListener< T > listener = EasyMock.createStrictMock( IRtmContentHandlerListener.class );
      EasyMock.replay( listener );
      
      handler = createHandlerWithListener( listener );
      handler.setContentElement( content );
      
      EasyMock.verify( listener );
   }
   
   
   
   @Test
   public void testSetContentElementAndNotify() throws SAXException
   {
      final T content = createDummyContent();
      
      @SuppressWarnings( "unchecked" )
      final IRtmContentHandlerListener< T > listener = EasyMock.createStrictMock( IRtmContentHandlerListener.class );
      listener.onContentHandled( content );
      EasyMock.replay( listener );
      
      final RtmContentHandler< T > handler = createHandlerWithListener( listener );
      handler.setContentElementAndNotify( content );
      
      EasyMock.verify( listener );
   }
   
   
   
   protected T readContent( XmlFileResource file ) throws Exception
   {
      final RtmContentHandler< T > handler = createHandler();
      
      final RemoveWhiteSpaceXmlFilter filter = new RemoveWhiteSpaceXmlFilter( file.getXmlReader() );
      filter.setContentHandler( handler );
      filter.parse( new InputSource( file.getReader() ) );
      
      final T content = handler.getContentElement();
      return content;
   }
   
   
   
   protected abstract RtmContentHandler< T > createHandler();
   
   
   
   protected abstract RtmContentHandler< T > createHandlerWithListener( IRtmContentHandlerListener< T > listener );
   
   
   
   protected abstract T createDummyContent();
}
