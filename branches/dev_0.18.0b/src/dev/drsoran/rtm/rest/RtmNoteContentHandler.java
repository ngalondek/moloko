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

import dev.drsoran.Strings;
import dev.drsoran.rtm.model.RtmNote;


public class RtmNoteContentHandler extends RtmContentHandler< RtmNote >
{
   private Attributes noteAttributes;
   
   private String noteText = Strings.EMPTY_STRING;
   
   
   
   public RtmNoteContentHandler()
   {
      this( null );
   }
   
   
   
   public RtmNoteContentHandler( IRtmContentHandlerListener< RtmNote > listener )
   {
      super( listener );
   }
   
   
   
   @Override
   public void characters( char[] ch, int start, int length ) throws SAXException
   {
      noteText = new String( ch, start, length );
   }
   
   
   
   @Override
   public void startElement( String uri,
                             String localName,
                             String qName,
                             Attributes attributes ) throws SAXException
   {
      if ( "note".equalsIgnoreCase( qName ) )
      {
         noteAttributes = XmlAttr.copy( attributes );
      }
   }
   
   
   
   @Override
   public void endElement( String uri, String localName, String qName ) throws SAXException
   {
      if ( "note".equalsIgnoreCase( qName ) )
      {
         readNote();
      }
   }
   
   
   
   private void readNote() throws SAXException
   {
      setContentElementAndNotify( new RtmNote( XmlAttr.getStringNotNull( noteAttributes,
                                                                         "id" ),
                                               XmlAttr.getOptMillisUtc( noteAttributes,
                                                                        "created" ),
                                               XmlAttr.getOptMillisUtc( noteAttributes,
                                                                        "modified" ),
                                               Strings.emptyIfNull( noteAttributes.getValue( "title" ) ),
                                               noteText ) );
   }
}
