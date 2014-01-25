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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.xml.sax.SAXException;


public abstract class RtmSortedListContentHandler< T > extends
         RtmContentHandler< List< T > >
{
   private final Comparator< T > comparator;
   
   
   
   protected RtmSortedListContentHandler( Comparator< T > comparator,
      IRtmContentHandlerListener< List< T >> listener )
   {
      super( listener );
      
      this.comparator = comparator;
      setContentElement( new ArrayList< T >() );
   }
   
   
   
   public void addElementSorted( T element ) throws SAXException
   {
      final List< T > list = getContentElement();
      
      final int insertPos = Collections.binarySearch( list, element, comparator );
      if ( insertPos > -1 )
      {
         throw new SAXException( MessageFormat.format( "Tried to insert duplicate element {0}",
                                                       element ) );
      }
      
      list.add( -insertPos - 1, element );
   }
}
