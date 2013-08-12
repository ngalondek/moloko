/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test;

import static org.junit.Assert.*;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;


public final class IterableAsserts
{
   private IterableAsserts()
   {
   }
   
   
   private static class EqualCmp< T > implements Comparator< T >
   {
      @SuppressWarnings( "unchecked" )
      @Override
      public int compare( T o1, T o2 )
      {
         if ( o1 == o2 )
         {
            return 0;
         }
         if ( o1 == null && o2 != null )
         {
            return -1;
         }
         if ( o1 != null && o2 == null )
         {
            return 1;
         }
         
         if ( o1 instanceof Comparable )
         {
            return ( (Comparable< T >) o1 ).compareTo( o2 );
         }
         
         if ( o1.equals( o2 ) )
         {
            return 0;
         }
         
         return 1;
      }
   }
   
   
   
   public static < T > void assertEmpty( Iterable< T > iterable )
   {
      assertFalse( iterable.iterator().hasNext() );
   }
   
   
   
   public static < T > void assertNotEmpty( Iterable< T > iterable )
   {
      assertTrue( iterable.iterator().hasNext() );
   }
   
   
   
   public static < T > void assertCount( Iterable< T > iterable, int count )
   {
      int cnt = 0;
      for ( Iterator< T > iterator = iterable.iterator(); iterator.hasNext(); iterator.next(), ++cnt )
      {
      }
      
      assertEquals( count, cnt );
   }
   
   
   
   public static < T > void assertEqualSet( Iterable< T > iterable,
                                            T... elements )
   {
      assertEqualSet( iterable, Arrays.asList( elements ) );
   }
   
   
   
   public static < T > void assertEqualSet( Iterable< T > iterable1,
                                            Iterable< T > iterable2 )
   {
      assertEqualSet( iterable1, iterable2, new EqualCmp< T >() );
   }
   
   
   
   public static < T > void assertEqualSet( Iterable< T > iterable,
                                            Comparator< T > cmp,
                                            T... elements )
   {
      assertEqualSet( iterable, Arrays.asList( elements ), cmp );
   }
   
   
   
   public static < T > void assertEqualSet( Iterable< T > iterable1,
                                            Iterable< T > iterable2,
                                            Comparator< T > cmp )
   {
      for ( T setElement1 : iterable1 )
      {
         boolean found = false;
         for ( T setElement2 : iterable2 )
         {
            found = cmp.compare( setElement1, setElement2 ) == 0;
            if ( found )
            {
               break;
            }
         }
         
         assertTrue( MessageFormat.format( "The element ''{0}'' has not been found",
                                           setElement1 ),
                     found );
      }
   }
}
