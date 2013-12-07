/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm.rest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.model.RtmTasksList;


public class RtmListContentHandler extends RtmContentHandler< RtmTasksList >
{
   private Attributes listAttributes;
   
   private String smartFilter;
   
   
   
   public RtmListContentHandler(
      IRtmContentHandlerListener< RtmTasksList > listener )
   {
      super( listener );
   }
   
   
   
   @Override
   public void characters( char[] ch, int start, int length ) throws SAXException
   {
      if ( smartFilter != null )
      {
         throw new SAXException( "Expected smart filter to be not present." );
      }
      
      smartFilter = new String( ch, start, length );
   }
   
   
   
   @Override
   public void startElement( String uri,
                             String localName,
                             String qName,
                             Attributes attributes ) throws SAXException
   {
      if ( "list".equalsIgnoreCase( localName ) )
      {
         listAttributes = attributes;
      }
   }
   
   
   
   @Override
   public void endElement( String uri, String localName, String qName ) throws SAXException
   {
      if ( "list".equalsIgnoreCase( localName ) )
      {
         readTasksList();
      }
   }
   
   
   
   private void readTasksList() throws SAXException
   {
      setContentElementAndNotify( new RtmTasksList( listAttributes.getValue( "id" ),
                                                    XmlAttr.getOptMillisUtc( listAttributes,
                                                                             "deleted" ),
                                                    XmlAttr.getInt( listAttributes,
                                                                    "position" ),
                                                    XmlAttr.getBoolean( listAttributes,
                                                                        "locked" ),
                                                    XmlAttr.getBoolean( listAttributes,
                                                                        "archived" ),
                                                    listAttributes.getValue( "name" ),
                                                    smartFilter ) );
   }
}
