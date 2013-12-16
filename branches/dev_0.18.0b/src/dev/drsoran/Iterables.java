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

package dev.drsoran;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class Iterables
{
   private Iterables()
   {
      throw new AssertionError();
   }
   
   
   
   public static < T > List< T > asList( Iterable< T > iterable )
   {
      final List< T > list = new ArrayList< T >();
      
      for ( T t : iterable )
      {
         list.add( t );
      }
      
      return list;
   }
   
   
   
   public static < T > int size( Iterable< T > iterable )
   {
      if ( iterable instanceof Collection )
      {
         return ( (Collection< ? >) iterable ).size();
      }
      
      int size = 0;
      for ( Iterator< T > iterator = iterable.iterator(); iterator.hasNext(); iterator.next() )
      {
         ++size;
      }
      
      return size;
   }
   
   
   
   public static < T > boolean isEmpty( Iterable< T > iterable )
   {
      return !iterable.iterator().hasNext();
   }
   
   
   
   public static < T > T first( Iterable< T > iterable )
   {
      return iterable.iterator().next();
   }
   
   
   
   public static < T > T firstOrDefault( Iterable< T > iterable, T defaultT )
   {
      final Iterator< T > i = iterable.iterator();
      if ( i.hasNext() )
      {
         return i.next();
      }
      
      return defaultT;
   }
}
