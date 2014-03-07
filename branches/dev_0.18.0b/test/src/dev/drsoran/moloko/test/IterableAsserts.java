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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
   
   
   
   public static < T > void assertEqualSet( Iterable< ? extends T > iterable,
                                            T... expected )
   {
      assertEqualSet( Arrays.asList( expected ), iterable );
   }
   
   
   
   public static < T > void assertEqualSet( Iterable< ? extends T > expected,
                                            Iterable< ? extends T > iterable )
   {
      assertEqualSet( expected, iterable, new EqualCmp< T >() );
   }
   
   
   
   public static < T > void assertEqualSet( Iterable< ? extends T > iterable,
                                            Comparator< ? super T > cmp,
                                            T... expected )
   {
      assertEqualSet( Arrays.asList( expected ), iterable, cmp );
   }
   
   
   
   public static < T > void assertEqualSet( Iterable< ? extends T > expected,
                                            Iterable< ? extends T > iterable,
                                            Comparator< ? super T > cmp )
   {
      compareSets( "The expected element ''{0}'' has not been found",
                   expected,
                   iterable,
                   cmp );
      compareSets( "The element ''{0}'' is not expected to be in the set",
                   iterable,
                   expected,
                   cmp );
   }
   
   
   
   private static < T > void compareSets( String messageFormat,
                                          Iterable< ? extends T > expected,
                                          Iterable< ? extends T > iterable,
                                          Comparator< ? super T > cmp )
   {
      for ( T expectedElement : expected )
      {
         boolean found = false;
         for ( T element : iterable )
         {
            found = cmp.compare( expectedElement, element ) == 0;
            if ( found )
            {
               break;
            }
         }
         
         assertTrue( MessageFormat.format( messageFormat, expectedElement ),
                     found );
      }
   }
}
