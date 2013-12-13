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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.XmlFileResource;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RemoveWhiteSpaceXmlFilter;
import dev.drsoran.rtm.rest.RtmContentHandler;


public abstract class RtmContentHandlerTestCase< T > extends MolokoTestCase
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
   public void testGetContentElement()
   {
      assertThat( createHandler().getContentElement(), is( nullValue() ) );
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
   public void testSetContentElementAndNotify()
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
      
      final RemoveWhiteSpaceXmlFilter filter = new RemoveWhiteSpaceXmlFilter();
      final XMLReader reader = file.getXmlReader();
      
      filter.setParent( reader );
      filter.setContentHandler( handler );
      filter.parse( new InputSource( file.getReader() ) );
      
      final T content = handler.getContentElement();
      return content;
   }
   
   
   
   protected abstract RtmContentHandler< T > createHandler();
   
   
   
   protected abstract RtmContentHandler< T > createHandlerWithListener( IRtmContentHandlerListener< T > listener );
   
   
   
   protected abstract T createDummyContent();
}
