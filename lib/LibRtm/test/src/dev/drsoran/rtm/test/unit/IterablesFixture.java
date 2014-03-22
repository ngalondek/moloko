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

package dev.drsoran.rtm.test.unit;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import dev.drsoran.rtm.Iterables;
import dev.drsoran.rtm.test.PrivateCtorCaller;


public class IterablesFixture
{
   @Test( expected = AssertionError.class )
   public void testPrivateCtor() throws Throwable
   {
      PrivateCtorCaller.callPrivateCtor( Iterables.class );
   }
   
   
   
   @Test
   public void testAsList()
   {
      final Iterable< Integer > it = Arrays.asList( 1, 2, 3 );
      final List< Integer > list = Iterables.asList( it );
      
      assertThat( list, hasItems( 1, 2, 3 ) );
   }
   
   
   
   @Test
   public void testSize_Collection()
   {
      Iterable< Integer > it = Arrays.asList( 1, 2, 3 );
      assertThat( Iterables.size( it ), is( 3 ) );
      
      it = Arrays.asList();
      assertThat( Iterables.size( it ), is( 0 ) );
   }
   
   
   
   @Test
   public void testSize_NoCollection()
   {
      Iterable< Integer > it = new Iterable< Integer >()
      {
         List< Integer > impl = Arrays.asList( 1, 2, 3 );
         
         
         
         @Override
         public Iterator< Integer > iterator()
         {
            return impl.iterator();
         }
      };
      assertThat( Iterables.size( it ), is( 3 ) );
   }
   
   
   
   @Test
   public void testIsEmpty()
   {
      Iterable< Integer > it = Arrays.asList( 1, 2, 3 );
      assertThat( Iterables.isEmpty( it ), is( false ) );
      
      it = Arrays.asList();
      assertThat( Iterables.isEmpty( it ), is( true ) );
   }
   
   
   
   @Test
   public void testFirst()
   {
      Iterable< Integer > it = Arrays.asList( 1, 2, 3 );
      assertThat( Iterables.first( it ), is( 1 ) );
   }
   
   
   
   @Test( expected = Exception.class )
   public void testFirst_empty()
   {
      Iterable< Integer > it = Arrays.asList();
      Iterables.first( it );
   }
   
   
   
   @Test
   public void testFirstOrDefault()
   {
      Iterable< Integer > it = Arrays.asList( 1, 2, 3 );
      assertThat( Iterables.firstOrDefault( it, 10 ), is( 1 ) );
   }
   
   
   
   @Test
   public void testFirstOrDefault_empty()
   {
      Iterable< Integer > it = Arrays.asList();
      assertThat( Iterables.firstOrDefault( it, 10 ), is( 10 ) );
   }
}
