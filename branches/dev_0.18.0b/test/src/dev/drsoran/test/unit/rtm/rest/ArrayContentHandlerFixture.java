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

import static org.junit.Assert.assertArrayEquals;

import org.easymock.EasyMock;
import org.junit.Test;
import org.xml.sax.SAXException;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.rest.ArrayContentHandler;
import dev.drsoran.rtm.rest.IRtmContentHandlerListener;
import dev.drsoran.rtm.rest.RtmContentHandler;


public class ArrayContentHandlerFixture extends MolokoTestCase
{
   @Test
   public void testArray() throws SAXException
   {
      final ArrayContentHandler< Integer > handler = new ArrayContentHandler< Integer >( "ints",
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
      
      assertArrayEquals( handler.getContentElement(), new Integer[]
      { 1, 2 } );
   }
   
   
   
   @Test
   public void testArray_empty() throws SAXException
   {
      final ArrayContentHandler< Integer > handler = new ArrayContentHandler< Integer >( "ints",
                                                                                         new IntHandler() );
      
      handler.startElement( null, null, "ints", null );
      handler.endElement( null, null, "ints" );
      
      assertArrayEquals( handler.getContentElement(), new Integer[] {} );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testArrayContentHandler()
   {
      new ArrayContentHandler< Integer >( "int",
                                          EasyMock.createNiceMock( RtmContentHandler.class ) );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   @Test
   public void testArrayContentHandlerListener()
   {
      new ArrayContentHandler< Integer >( "int",
                                          EasyMock.createNiceMock( RtmContentHandler.class ),
                                          EasyMock.createNiceMock( IRtmContentHandlerListener.class ) );
   }
}
