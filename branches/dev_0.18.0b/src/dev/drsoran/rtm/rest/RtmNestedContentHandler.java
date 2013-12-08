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

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


public abstract class RtmNestedContentHandler< T > extends
         RtmContentHandler< T >
{
   private final Stack< RtmContentHandler< ? > > contentHandlerStack = new Stack< RtmContentHandler< ? > >();
   
   
   
   protected RtmNestedContentHandler( IRtmContentHandlerListener< T > listener )
   {
      super( listener );
   }
   
   
   
   @Override
   public void startElement( String uri,
                             String localName,
                             String qName,
                             Attributes attributes ) throws SAXException
   {
      final RtmContentHandler< ? > currentHandler = currentHandler();
      if ( currentHandler != null )
      {
         currentHandler.startElement( uri, localName, qName, attributes );
      }
   }
   
   
   
   @Override
   public void endElement( String uri, String localName, String qName ) throws SAXException
   {
      final RtmContentHandler< ? > currentHandler = currentHandler();
      if ( currentHandler != null )
      {
         currentHandler.endElement( uri, localName, qName );
      }
   }
   
   
   
   @Override
   public void characters( char[] ch, int start, int length ) throws SAXException
   {
      final RtmContentHandler< ? > currentHandler = currentHandler();
      if ( currentHandler != null )
      {
         currentHandler.characters( ch, start, length );
      }
   }
   
   
   
   public void pushContentHandler( RtmContentHandler< ? > contentHandler )
   {
      contentHandlerStack.push( contentHandler );
   }
   
   
   
   public void popContentHandler()
   {
      contentHandlerStack.pop();
   }
   
   
   
   public RtmContentHandler< ? > currentHandler()
   {
      if ( contentHandlerStack.isEmpty() )
      {
         return null;
      }
      
      return contentHandlerStack.peek();
   }
}
