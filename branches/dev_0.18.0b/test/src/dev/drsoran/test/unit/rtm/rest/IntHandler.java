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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.rest.RtmContentHandler;


class IntHandler extends RtmContentHandler< Integer >
{
   private int integer;
   
   
   
   public IntHandler()
   {
      super( null );
   }
   
   
   
   @Override
   protected void startElement( String qName, Attributes attributes ) throws SAXException
   {
      assertThat( qName, is( "int" ) );
   }
   
   
   
   @Override
   protected void endElement( String qName ) throws SAXException
   {
      assertThat( qName, is( "int" ) );
      setContentElementAndNotify( integer );
   }
   
   
   
   @Override
   protected void characters( String string ) throws SAXException
   {
      integer = Integer.valueOf( string );
   }
   
   
   
   @Override
   protected void cleanUpState()
   {
      integer = 0;
   }
}
