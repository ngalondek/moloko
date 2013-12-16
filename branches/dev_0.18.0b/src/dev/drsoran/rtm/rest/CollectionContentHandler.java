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
         RtmContentHandler< Collection< T > > implements
         IRtmContentHandlerListener< T >
{
   private final String xmlCollectionTag;
   
   private RtmContentHandler< T > collectionElementHandler;
   
   
   
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
      
      this.collectionElementHandler = collectionElementHandler;
      this.collectionElementHandler.setListener( this );
      this.xmlCollectionTag = xmlCollectionTag;
   }
   
   
   
   @Override
   protected void startElement( String name, Attributes attributes ) throws SAXException
   {
      if ( name.equalsIgnoreCase( xmlCollectionTag ) )
      {
         setContentElement( new ArrayList< T >() );
      }
      else
      {
         collectionElementHandler.startElement( name, attributes );
      }
   }
   
   
   
   @Override
   protected void endElement( String name ) throws SAXException
   {
      if ( name.equalsIgnoreCase( xmlCollectionTag ) )
      {
         setContentElementAndNotify( getContentElement() );
      }
      else
      {
         collectionElementHandler.endElement( name );
      }
   }
   
   
   
   @Override
   protected void characters( String string ) throws SAXException
   {
      collectionElementHandler.characters( string );
   }
   
   
   
   @Override
   public void onContentHandled( T collectionElement )
   {
      getContentElement().add( collectionElement );
   }
}
