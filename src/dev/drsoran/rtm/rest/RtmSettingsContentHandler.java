/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

import static dev.drsoran.rtm.content.ContentProperties.RtmSettingsProperties.DATEFORMAT;
import static dev.drsoran.rtm.content.ContentProperties.RtmSettingsProperties.DEFAULTLIST;
import static dev.drsoran.rtm.content.ContentProperties.RtmSettingsProperties.LANGUAGE;
import static dev.drsoran.rtm.content.ContentProperties.RtmSettingsProperties.TIMEFORMAT;
import static dev.drsoran.rtm.content.ContentProperties.RtmSettingsProperties.TIMEZONE;

import java.text.MessageFormat;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmSettings;


public class RtmSettingsContentHandler extends RtmContentHandler< RtmSettings >
{
   private enum CharactersContext
   {
      None, Timezone, Dateformat, Timeformat, Defaultlist, Language
   }
   
   private CharactersContext charactersContext;
   
   private String timezone;
   
   private int dateformat;
   
   private int timeformat;
   
   private String defaultListId;
   
   private String language;
   
   
   
   public RtmSettingsContentHandler()
   {
      this( null );
   }
   
   
   
   public RtmSettingsContentHandler(
      IRtmContentHandlerListener< RtmSettings > listener )
   {
      super( listener );
   }
   
   
   
   @Override
   protected void characters( String string ) throws SAXException
   {
      switch ( charactersContext )
      {
         case Timezone:
            timezone = string;
            break;
         
         case Dateformat:
            dateformat = toInt( string );
            break;
         
         case Timeformat:
            timeformat = toInt( string );
            break;
         
         case Defaultlist:
            defaultListId = string;
            break;
         
         case Language:
            language = string;
            break;
         
         default :
            throw new SAXException( MessageFormat.format( "Unexpected character context {0}",
                                                          charactersContext ) );
      }
      
      charactersContext = CharactersContext.None;
   }
   
   
   
   @Override
   protected void startElement( String qName, Attributes attributes ) throws SAXException
   {
      if ( TIMEZONE.equalsIgnoreCase( qName ) )
      {
         charactersContext = CharactersContext.Timezone;
      }
      else if ( DATEFORMAT.equalsIgnoreCase( qName ) )
      {
         charactersContext = CharactersContext.Dateformat;
      }
      else if ( TIMEFORMAT.equalsIgnoreCase( qName ) )
      {
         charactersContext = CharactersContext.Timeformat;
      }
      else if ( DEFAULTLIST.equalsIgnoreCase( qName ) )
      {
         charactersContext = CharactersContext.Defaultlist;
      }
      else if ( LANGUAGE.equalsIgnoreCase( qName ) )
      {
         charactersContext = CharactersContext.Language;
      }
   }
   
   
   
   @Override
   protected void endElement( String qName ) throws SAXException
   {
      if ( "settings".equalsIgnoreCase( qName ) )
      {
         setContentElementAndNotify( new RtmSettings( RtmConstants.NO_TIME,
                                                      timezone,
                                                      dateformat,
                                                      timeformat,
                                                      defaultListId,
                                                      language ) );
      }
   }
   
   
   
   private static int toInt( String value ) throws SAXException
   {
      try
      {
         return Integer.parseInt( value );
      }
      catch ( NumberFormatException e )
      {
         throw new SAXException( e );
      }
   }
}
