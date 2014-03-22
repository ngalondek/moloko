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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;
import dev.drsoran.rtm.rest.XmlCollectionTagContentHandler;


public class XmlCollectionTagContentHandlerFixture
{
   @Test
   public void testCollection() throws SAXException
   {
      final XmlCollectionTagContentHandler< Integer > handler = new XmlCollectionTagContentHandler< Integer >( "ints",
                                                                                                               new IntHandler(),
                                                                                                               null );
      
      handler.startElement( null, null, "ints", null );
      handler.startElement( null, null, "int", null );
      handler.characters( new char[]
      { '2' }, 0, 1 );
      handler.endElement( null, null, "int" );
      handler.startElement( null, null, "int", null );
      handler.characters( new char[]
      { '1' }, 0, 1 );
      handler.endElement( null, null, "int" );
      handler.endElement( null, null, "ints" );
      
      assertThat( handler.getContentElement(), containsInAnyOrder( 1, 2 ) );
   }
   
   
   
   @Test
   public void testCollection_empty() throws SAXException
   {
      final XmlCollectionTagContentHandler< Integer > handler = new XmlCollectionTagContentHandler< Integer >( "ints",
                                                                                                               new IntHandler(),
                                                                                                               null );
      
      handler.startElement( null, null, "ints", null );
      handler.endElement( null, null, "ints" );
      
      assertThat( handler.getContentElement().size(), is( 0 ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testCollectionContentHandler()
   {
      new XmlCollectionTagContentHandler< Integer >( "int",
                                                     EasyMock.createNiceMock( RtmContentHandler.class ),
                                                     null );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testCollectionContentHandlerListener()
   {
      new XmlCollectionTagContentHandler< Integer >( "int",
                                                     EasyMock.createNiceMock( RtmContentHandler.class ),
                                                     EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
}
