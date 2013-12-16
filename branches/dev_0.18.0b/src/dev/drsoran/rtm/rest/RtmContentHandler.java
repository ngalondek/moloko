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
import org.xml.sax.helpers.DefaultHandler;


public abstract class RtmContentHandler< T > extends DefaultHandler
{
   private Stack< RtmContentHandler< ? > > contentHandlerStack;
   
   private T contentElement;
   
   private IRtmContentHandlerListener< T > listener;
   
   
   
   protected RtmContentHandler( IRtmContentHandlerListener< T > listener )
   {
      this.listener = listener;
   }
   
   
   
   public void setListener( IRtmContentHandlerListener< T > listener )
   {
      this.listener = listener;
   }
   
   
   
   @Override
   public final void startElement( String uri,
                                   String localName,
                                   String qName,
                                   Attributes attributes ) throws SAXException
   {
      final RtmContentHandler< ? > handler = currentHandler();
      if ( handler == this )
      {
         handler.startElement( qName, attributes );
      }
      else
      {
         handler.startElement( uri, localName, qName, attributes );
      }
   }
   
   
   
   protected void startElement( String qName, Attributes attributes ) throws SAXException
   {
   }
   
   
   
   @Override
   public final void endElement( String uri, String localName, String qName ) throws SAXException
   {
      final RtmContentHandler< ? > handler = currentHandler();
      if ( handler == this )
      {
         handler.endElement( qName );
      }
      else
      {
         handler.endElement( uri, localName, qName );
      }
   }
   
   
   
   protected void endElement( String qName ) throws SAXException
   {
   }
   
   
   
   @Override
   public final void characters( char[] ch, int start, int length ) throws SAXException
   {
      final RtmContentHandler< ? > handler = currentHandler();
      if ( handler == this )
      {
         handler.characters( new String( ch, start, length ) );
      }
      else
      {
         handler.characters( ch, start, length );
      }
   }
   
   
   
   protected void characters( String string ) throws SAXException
   {
   }
   
   
   
   public T getContentElement()
   {
      return contentElement;
   }
   
   
   
   public void setContentElement( T content )
   {
      contentElement = content;
   }
   
   
   
   public void setContentElementAndNotify( T content )
   {
      setContentElement( content );
      if ( listener != null )
      {
         listener.onContentHandled( content );
      }
   }
   
   
   
   public RtmContentHandler< ? > pushNestedContentHandler( RtmContentHandler< ? > contentHandler )
   {
      if ( contentHandlerStack == null )
      {
         contentHandlerStack = new Stack< RtmContentHandler< ? > >();
      }
      
      contentHandlerStack.push( contentHandler );
      return contentHandler;
   }
   
   
   
   public RtmContentHandler< ? > popNestedContentHandler()
   {
      RtmContentHandler< ? > nestedHandler = currentHandler();
      contentHandlerStack.pop();
      
      return nestedHandler;
   }
   
   
   
   public RtmContentHandler< ? > currentHandler()
   {
      if ( contentHandlerStack == null || contentHandlerStack.isEmpty() )
      {
         return this;
      }
      
      return contentHandlerStack.peek();
   }
}
