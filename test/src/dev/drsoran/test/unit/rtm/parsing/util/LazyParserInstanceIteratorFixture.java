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

package dev.drsoran.test.unit.rtm.parsing.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.parsing.util.LazyParserInstanceIterator;
import edu.emory.mathcs.backport.java.util.Arrays;


public class LazyParserInstanceIteratorFixture extends MolokoTestCase
{
   
   @Test
   public void testLazyParserInstanceIteratorListOfQMethodListOfT() throws Exception
   {
      new LazyParserInstanceIterator< Integer >( Collections.emptyList(),
                                                 Object.class.getMethod( "toString" ),
                                                 Collections.< Integer > emptyList() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLazyParserInstanceIteratorListOfQMethodListOfT_NullFactories() throws Exception
   {
      new LazyParserInstanceIterator< Integer >( null,
                                                 Object.class.getMethod( "toString" ),
                                                 Collections.< Integer > emptyList() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLazyParserInstanceIteratorListOfQMethodListOfT_NullFactoryMethod() throws Exception
   {
      new LazyParserInstanceIterator< Integer >( Collections.emptyList(),
                                                 null,
                                                 Collections.< Integer > emptyList() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testLazyParserInstanceIteratorListOfQMethodListOfT_NullInstances() throws Exception
   {
      new LazyParserInstanceIterator< Integer >( Collections.emptyList(),
                                                 Object.class.getMethod( "toString" ),
                                                 null );
   }
   
   
   
   @Test
   public void testLazyParserInstanceIteratorListOfQMethodListOfTObjectArray() throws Exception
   {
      new LazyParserInstanceIterator< Integer >( Collections.emptyList(),
                                                 Object.class.getMethod( "toString" ),
                                                 Collections.< Integer > emptyList(),
                                                 new Object[] {} );
   }
   
   
   
   @Test
   public void testIterator() throws Exception
   {
      Iterable< Integer > it = new LazyParserInstanceIterator< Integer >( intFactMocks( 1,
                                                                                        2,
                                                                                        3 ),
                                                                          IntegerFactory.class.getMethod( "newInteger" ),
                                                                          Collections.< Integer > emptyList() );
      assertThat( it.iterator(), notNullValue() );
   }
   
   
   
   @Test
   public void testHasNextEmpty() throws Exception
   {
      Iterator< Integer > it = new LazyParserInstanceIterator< Integer >( Arrays.asList( new IntegerFactory[] {} ),
                                                                          IntegerFactory.class.getMethod( "newInteger" ),
                                                                          Collections.< Integer > emptyList() );
      
      assertThat( it.hasNext(), is( false ) );
      assertThat( it.hasNext(), is( false ) );
   }
   
   
   
   @Test
   public void testHasNext() throws Exception
   {
      Iterator< Integer > it = new LazyParserInstanceIterator< Integer >( intFactMocks( 1 ),
                                                                          IntegerFactory.class.getMethod( "newInteger" ),
                                                                          Collections.< Integer > emptyList() );
      
      assertThat( it.hasNext(), is( true ) );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testNextEmpty() throws Exception
   {
      Iterator< Integer > it = new LazyParserInstanceIterator< Integer >( Arrays.asList( new IntegerFactory[] {} ),
                                                                          IntegerFactory.class.getMethod( "newInteger" ),
                                                                          Collections.< Integer > emptyList() );
      it.next();
   }
   
   
   
   @Test
   public void testNext() throws Exception
   {
      List< IntegerFactory > mocks = intFactMocks( 1, 2, 3 );
      
      Iterator< Integer > it = new LazyParserInstanceIterator< Integer >( mocks,
                                                                          IntegerFactory.class.getMethod( "newInteger" ),
                                                                          new ArrayList< Integer >() );
      assertThat( it.hasNext(), is( true ) );
      assertThat( it.next(), is( 1 ) );
      assertThat( it.hasNext(), is( true ) );
      assertThat( it.next(), is( 2 ) );
      assertThat( it.hasNext(), is( true ) );
      assertThat( it.next(), is( 3 ) );
      assertThat( it.hasNext(), is( false ) );
      
      EasyMock.verify( mocks.toArray() );
   }
   
   
   
   @Test
   public void testNextUseCtorInstances() throws Exception
   {
      List< IntegerFactory > mocks = new ArrayList< LazyParserInstanceIteratorFixture.IntegerFactory >();
      IntegerFactory existingValueFactMock1 = intFactMock( 1 );
      // Expect no call
      EasyMock.reset( existingValueFactMock1 );
      EasyMock.replay( existingValueFactMock1 );
      mocks.add( existingValueFactMock1 );
      
      IntegerFactory existingValueFactMock2 = intFactMock( 2 );
      // Expect no call
      EasyMock.reset( existingValueFactMock2 );
      EasyMock.replay( existingValueFactMock2 );
      mocks.add( existingValueFactMock2 );
      
      mocks.add( intFactMock( 3 ) );
      
      List< Integer > instances = new ArrayList< Integer >( 3 );
      instances.add( 1 );
      instances.add( 2 );
      
      Iterator< Integer > it = new LazyParserInstanceIterator< Integer >( mocks,
                                                                          IntegerFactory.class.getMethod( "newInteger" ),
                                                                          instances );
      assertThat( it.hasNext(), is( true ) );
      assertThat( it.next(), is( 1 ) );
      assertThat( it.hasNext(), is( true ) );
      assertThat( it.next(), is( 2 ) );
      assertThat( it.hasNext(), is( true ) );
      assertThat( it.next(), is( 3 ) );
      assertThat( it.hasNext(), is( false ) );
      
      EasyMock.verify( mocks.toArray() );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testRemove() throws Exception
   {
      Iterator< Integer > it = new LazyParserInstanceIterator< Integer >( Collections.emptyList(),
                                                                          Object.class.getMethod( "toString" ),
                                                                          Collections.< Integer > emptyList() );
      it.remove();
   }
   
   
   public interface IntegerFactory
   {
      public Integer newInteger();
   }
   
   
   
   private List< IntegerFactory > intFactMocks( int... values )
   {
      List< IntegerFactory > mocks = new ArrayList< IntegerFactory >( values.length );
      
      for ( int value : values )
      {
         mocks.add( intFactMock( value ) );
      }
      
      return mocks;
   }
   
   
   
   private IntegerFactory intFactMock( int value )
   {
      IntegerFactory fact = EasyMock.createStrictMock( IntegerFactory.class );
      EasyMock.expect( fact.newInteger() ).andReturn( value );
      EasyMock.replay( fact );
      
      return fact;
   }
}
