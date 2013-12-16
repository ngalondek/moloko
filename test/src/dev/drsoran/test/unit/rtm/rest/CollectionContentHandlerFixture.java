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

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Test;
import org.xml.sax.SAXException;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.rest.CollectionContentHandler;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;


public class CollectionContentHandlerFixture extends MolokoTestCase
{
   @Test
   public void testCollection() throws SAXException
   {
      final CollectionContentHandler< Integer > handler = new CollectionContentHandler< Integer >( "ints",
                                                                                                   new IntHandler() );
      
      handler.startElement( null, null, "ints", null );
      handler.startElement( null, null, "int", null );
      handler.characters( new char[]
      { '1' }, 0, 1 );
      handler.endElement( null, null, "int" );
      handler.startElement( null, null, "int", null );
      handler.characters( new char[]
      { '2' }, 0, 1 );
      handler.endElement( null, null, "int" );
      handler.endElement( null, null, "ints" );
      
      assertThat( handler.getContentElement(), hasItems( 1, 2 ) );
   }
   
   
   
   @Test
   public void testCollection_empty() throws SAXException
   {
      final CollectionContentHandler< Integer > handler = new CollectionContentHandler< Integer >( "ints",
                                                                                                   new IntHandler() );
      
      handler.startElement( null, null, "ints", null );
      handler.endElement( null, null, "ints" );
      
      assertThat( handler.getContentElement().size(), is( 0 ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testCollectionContentHandler()
   {
      new CollectionContentHandler< Integer >( "int",
                                               EasyMock.createNiceMock( RtmContentHandler.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testCollectionContentHandlerListener()
   {
      new CollectionContentHandler< Integer >( "int",
                                               EasyMock.createNiceMock( RtmContentHandler.class ),
                                               EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
}
