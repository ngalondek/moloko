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

import java.text.MessageFormat;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import dev.drsoran.Strings;
import dev.drsoran.rtm.service.RtmAuth;
import dev.drsoran.rtm.service.RtmServicePermission;
import dev.drsoran.rtm.service.RtmUser;


public class RtmAuthContentHandler extends RtmContentHandler< RtmAuth >
{
   private enum CharactersContext
   {
      None, Token, Perm
   }
   
   private CharactersContext charactersContext = CharactersContext.None;
   
   private String token;
   
   private RtmServicePermission perm;
   
   private Attributes userAttributes;
   
   
   
   public RtmAuthContentHandler( IRtmContentHandlerListener< RtmAuth > listener )
   {
      super( listener );
   }
   
   
   
   @Override
   protected void characters( String string ) throws SAXException
   {
      switch ( charactersContext )
      {
         case Token:
            token = string;
            break;
         
         case Perm:
            perm = Enum.valueOf( RtmServicePermission.class, string );
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
      if ( "token".equalsIgnoreCase( qName ) )
      {
         charactersContext = CharactersContext.Token;
      }
      else if ( "perms".equalsIgnoreCase( qName ) )
      {
         charactersContext = CharactersContext.Perm;
      }
      else if ( "user".equalsIgnoreCase( qName ) )
      {
         userAttributes = XmlAttr.copy( attributes );
      }
   }
   
   
   
   @Override
   protected void endElement( String qName ) throws SAXException
   {
      if ( "auth".equalsIgnoreCase( qName ) )
      {
         setContentElementAndNotify( new RtmAuth( token,
                                                  perm,
                                                  new RtmUser( XmlAttr.getStringNotNull( userAttributes,
                                                                                         "id" ),
                                                               XmlAttr.getOptString( userAttributes,
                                                                                     "username",
                                                                                     Strings.EMPTY_STRING ),
                                                               XmlAttr.getOptString( userAttributes,
                                                                                     "fullname",
                                                                                     Strings.EMPTY_STRING ) ) ) );
      }
   }
   
   
   
   @Override
   protected void cleanUpState()
   {
      charactersContext = CharactersContext.None;
      token = null;
      perm = RtmServicePermission.nothing;
      userAttributes = null;
   }
}
