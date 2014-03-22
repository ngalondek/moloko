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
import static dev.drsoran.rtm.content.ContentProperties.RtmLocationProperties.ADDRESS;
import static dev.drsoran.rtm.content.ContentProperties.RtmLocationProperties.LATITUDE;
import static dev.drsoran.rtm.content.ContentProperties.RtmLocationProperties.LONGITUDE;
import static dev.drsoran.rtm.content.ContentProperties.RtmLocationProperties.NAME;
import static dev.drsoran.rtm.content.ContentProperties.RtmLocationProperties.VIEWABLE;
import static dev.drsoran.rtm.content.ContentProperties.RtmLocationProperties.ZOOM;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.RtmLocation;


public class RtmLocationContentHandler extends RtmContentHandler< RtmLocation >
{
   public RtmLocationContentHandler()
   {
      this( null );
   }
   
   
   
   public RtmLocationContentHandler(
      IRtmContentHandlerListener< RtmLocation > listener )
   {
      super( listener );
   }
   
   
   
   @Override
   public void startElement( String qName, Attributes attributes ) throws SAXException
   {
      setContentElementAndNotify( new RtmLocation( XmlAttr.getStringNotNull( attributes,
                                                                             ID ),
                                                   XmlAttr.getStringNotNull( attributes,
                                                                             NAME ),
                                                   XmlAttr.getFloat( attributes,
                                                                     LONGITUDE ),
                                                   XmlAttr.getFloat( attributes,
                                                                     LATITUDE ),
                                                   XmlAttr.getOptString( attributes,
                                                                         ADDRESS,
                                                                         Strings.EMPTY_STRING ),
                                                   XmlAttr.getBoolean( attributes,
                                                                       VIEWABLE ),
                                                   XmlAttr.getInt( attributes,
                                                                   ZOOM ) ) );
   }
   
   
   
   @Override
   protected void cleanUpState()
   {
   }
}
