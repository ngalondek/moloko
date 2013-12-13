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

import java.util.Collection;


public class ArrayContentHandler< T > extends RtmNestedContentHandler< T[] >
         implements IRtmContentHandlerListener< Collection< T > >
{
   private final CollectionContentHandler< T > impl;
   
   
   
   public ArrayContentHandler( String xmlCollectionTag,
      RtmContentHandler< T > collectionElementHandler )
   {
      this( xmlCollectionTag, collectionElementHandler, null );
   }
   
   
   
   public ArrayContentHandler( String xmlCollectionTag,
      RtmContentHandler< T > collectionElementHandler,
      IRtmContentHandlerListener< T[] > listener )
   {
      super( listener );
      impl = new CollectionContentHandler< T >( xmlCollectionTag,
                                                collectionElementHandler,
                                                this );
   }
   
   
   
   @Override
   public void onContentHandled( Collection< T > contentElement )
   {
      @SuppressWarnings( "unchecked" )
      final T[] content = (T[]) new Object[ contentElement.size() ];
      
      setContentElementAndNotify( contentElement.toArray( content ) );
      impl.setContentElement( null );
   }
}
