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
   private final String taskSeriesId;
   
   private Attributes noteAttributes;
   
   private String noteText;
   
   
   
   public RtmNoteContentHandler( String taskSeriesId )
   {
      this.taskSeriesId = taskSeriesId;
   }
   
   
   
   public RtmNoteContentHandler( String taskSeriesId,
      IRtmContentHandlerListener< RtmNote > listener )
   {
      super( listener );
      this.taskSeriesId = taskSeriesId;
   }
   
   
   
   @Override
   public void characters( char[] ch, int start, int length ) throws SAXException
   {
      if ( noteText != null )
      {
         throw new SAXException( "Expected note text to be not present." );
      }
      
      noteText = new String( ch, start, length );
   }
   
   
   
   @Override
   public void startElement( String uri,
                             String localName,
                             String qName,
                             Attributes attributes ) throws SAXException
   {
      if ( "note".equalsIgnoreCase( localName ) )
      {
         noteAttributes = attributes;
      }
   }
   
   
   
   @Override
   public void endElement( String uri, String localName, String qName ) throws SAXException
   {
      if ( "note".equalsIgnoreCase( localName ) )
      {
         readNote();
      }
   }
   
   
   
   private void readNote() throws SAXException
   {
      setContentElementAndNotify( new RtmNote( XmlAttr.getStringNotNull( noteAttributes,
                                                                         "id" ),
                                               taskSeriesId,
                                               XmlAttr.getOptMillisUtc( noteAttributes,
                                                                        "created" ),
                                               XmlAttr.getOptMillisUtc( noteAttributes,
                                                                        "modified" ),
                                               XmlAttr.getOptMillisUtc( noteAttributes,
                                                                        "deleted" ),
                                               Strings.emptyIfNull( noteAttributes.getValue( "title" ) ),
                                               noteText ) );
   }
}
