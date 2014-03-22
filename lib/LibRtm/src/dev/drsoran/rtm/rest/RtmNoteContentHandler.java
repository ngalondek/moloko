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

import static dev.drsoran.rtm.content.ContentProperties.BaseProperties.ID;
import static dev.drsoran.rtm.content.ContentProperties.RtmNoteProperties.CREATED_DATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmNoteProperties.MODIFIED_DATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmNoteProperties.TITLE;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.Strings;
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
   public void characters( String string ) throws SAXException
   {
      noteText = string;
   }
   
   
   
   @Override
   public void startElement( String qName, Attributes attributes ) throws SAXException
   {
      if ( "note".equalsIgnoreCase( qName ) )
      {
         noteAttributes = XmlAttr.copy( attributes );
      }
   }
   
   
   
   @Override
   public void endElement( String qName ) throws SAXException
   {
      if ( "note".equalsIgnoreCase( qName ) )
      {
         readNote();
      }
   }
   
   
   
   private void readNote() throws SAXException
   {
      setContentElementAndNotify( new RtmNote( XmlAttr.getStringNotNull( noteAttributes,
                                                                         ID ),
                                               XmlAttr.getOptMillisUtc( noteAttributes,
                                                                        CREATED_DATE ),
                                               XmlAttr.getOptMillisUtc( noteAttributes,
                                                                        MODIFIED_DATE ),
                                               Strings.emptyIfNull( noteAttributes.getValue( TITLE ) ),
                                               noteText ) );
   }
   
   
   
   @Override
   protected void cleanUpState()
   {
      noteAttributes = null;
      noteText = Strings.EMPTY_STRING;
   }
}
