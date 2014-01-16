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

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.XmlFileResource;
import dev.drsoran.rtm.rest.RemoveWhiteSpaceXmlFilter;


public class RemoveWhiteSpaceXmlFilterFixture extends MolokoTestCase
{
   @Rule
   public final XmlFileResource xmlFile = new XmlFileResource( RemoveWhiteSpaceXmlFilterFixture.class,
                                                               "WhiteSpaces.xml" );
   
   
   
   @Test
   public void testFilter() throws SAXException, IOException
   {
      final RemoveWhiteSpaceXmlFilter filter = new RemoveWhiteSpaceXmlFilter( xmlFile.getXmlReader() );
      final ContentHandler contentHandler = new TestHandler();
      filter.setContentHandler( contentHandler );
      filter.parse( new InputSource( xmlFile.getReader() ) );
   }
   
   
   private final class TestHandler extends DefaultHandler
   {
      
      @Override
      public void characters( char[] arg0, int arg1, int arg2 ) throws SAXException
      {
         final String plainText = new String( arg0, arg1, arg2 );
         assertThat( plainText, is( "Content with spaces    " ) );
      }
      
      
      
      @Override
      public void startElement( String uri,
                                String localName,
                                String qName,
                                Attributes attributes ) throws SAXException
      {
         if ( qName.equals( "element" ) )
         {
            assertThat( attributes.getValue( "id" ), is( "1" ) );
            assertThat( attributes.getValue( "fullname" ),
                        is( "Text with spaces" ) );
         }
         else
         {
            assertThat( qName, is( "sub" ) );
         }
      }
   }
}
