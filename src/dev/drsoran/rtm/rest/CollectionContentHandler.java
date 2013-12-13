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

import java.util.ArrayList;
import java.util.Collection;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


public class CollectionContentHandler< T > extends
         RtmNestedContentHandler< Collection< T > > implements
         IRtmContentHandlerListener< T >
{
   private final String xmlCollectionTag;
   
   
   
   public CollectionContentHandler( String xmlCollectionTag,
      RtmContentHandler< T > collectionElementHandler )
   {
      this( xmlCollectionTag, collectionElementHandler, null );
   }
   
   
   
   public CollectionContentHandler( String xmlCollectionTag,
      RtmContentHandler< T > collectionElementHandler,
      IRtmContentHandlerListener< Collection< T >> listener )
   {
      super( listener );
      
      collectionElementHandler.setListener( this );
      this.xmlCollectionTag = xmlCollectionTag;
   }
   
   
   
   @Override
   public void startElement( String uri,
                             String localName,
                             String qName,
                             Attributes attributes ) throws SAXException
   {
      if ( qName.equalsIgnoreCase( xmlCollectionTag ) )
      {
         setContentElement( new ArrayList< T >() );
      }
      else
      {
         super.startElement( uri, localName, qName, attributes );
      }
   }
   
   
   
   @Override
   public void endElement( String uri, String localName, String qName ) throws SAXException
   {
      if ( qName.equalsIgnoreCase( xmlCollectionTag ) )
      {
         setContentElementAndNotify( getContentElement() );
      }
      else
      {
         super.endElement( uri, localName, qName );
      }
   }
   
   
   
   @Override
   public void onContentHandled( T collectionElement )
   {
      getContentElement().add( collectionElement );
   }
}
