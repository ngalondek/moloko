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

package dev.drsoran.moloko.test.unit.domain.parsing.rtmsmart;

import static dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_ADDED;
import static dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_COMPLETED;
import static dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_COMPLETED_AFTER;
import static dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_COMPLETED_BEFORE;
import static dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_COMPLETED_WITHIN;
import static dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_DUE;
import static dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_LIST;
import static dev.drsoran.moloko.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_STATUS;
import static dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterSyntax.COMPLETED;
import static dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterSyntax.INCOMPLETE;
import static dev.drsoran.moloko.test.IterableAsserts.assertEqualSet;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import dev.drsoran.moloko.domain.parsing.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.moloko.domain.parsing.rtmsmart.RtmSmartFilterTokenCollection;
import dev.drsoran.moloko.test.MolokoTestCase;


public class RtmSmartFilterTokenCollectionFixture extends MolokoTestCase
{
   
   @Test
   public void testRtmSmartFilterTokenCollection()
   {
      new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testRtmSmartFilterTokenCollectionNullCollection()
   {
      new RtmSmartFilterTokenCollection( null );
   }
   
   
   
   @Test
   public void testGetUniqueTokensEmpty()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      assertThat( collection.getUniqueTokens().size(), is( 0 ) );
   }
   
   
   
   @Test
   public void testGetUniqueTokensUnambiguous()
   {
      RtmSmartFilterToken token1 = new RtmSmartFilterToken( OP_LIST,
                                                            "List",
                                                            false );
      RtmSmartFilterToken token2 = new RtmSmartFilterToken( OP_ADDED,
                                                            "today",
                                                            false );
      
      Collection< RtmSmartFilterToken > tokens = getTokens( token1, token2 );
      
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( tokens );
      assertEqualSet( collection.getUniqueTokens(), token1, token2 );
   }
   
   
   
   @Test
   public void testGetUniqueTokensAmbiguous()
   {
      RtmSmartFilterToken token1 = new RtmSmartFilterToken( OP_LIST,
                                                            "List",
                                                            false );
      RtmSmartFilterToken token2 = new RtmSmartFilterToken( OP_ADDED,
                                                            "today",
                                                            false );
      RtmSmartFilterToken token3 = new RtmSmartFilterToken( OP_LIST,
                                                            "otherList",
                                                            true );
      RtmSmartFilterToken token4 = new RtmSmartFilterToken( OP_ADDED,
                                                            "tom",
                                                            false );
      RtmSmartFilterToken token5 = new RtmSmartFilterToken( OP_ADDED,
                                                            "never",
                                                            true );
      RtmSmartFilterToken token6 = new RtmSmartFilterToken( OP_COMPLETED,
                                                            "never",
                                                            false );
      
      Collection< RtmSmartFilterToken > tokens = getTokens( token1,
                                                            token2,
                                                            token3,
                                                            token4,
                                                            token5,
                                                            token6 );
      
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( tokens );
      assertEqualSet( collection.getUniqueTokens(), token6 );
   }
   
   
   
   @Test
   public void testHasUniqueOperator()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      assertThat( collection.hasUniqueOperator( OP_LIST, true ), is( false ) );
      assertThat( collection.hasUniqueOperator( OP_LIST, false ), is( false ) );
      
      RtmSmartFilterToken token1 = new RtmSmartFilterToken( OP_LIST,
                                                            "List",
                                                            false );
      RtmSmartFilterToken token2 = new RtmSmartFilterToken( OP_ADDED,
                                                            "today",
                                                            false );
      RtmSmartFilterToken token3 = new RtmSmartFilterToken( OP_LIST,
                                                            "List1",
                                                            true );
      
      Collection< RtmSmartFilterToken > tokens = getTokens( token1,
                                                            token2,
                                                            token3 );
      
      collection = new RtmSmartFilterTokenCollection( tokens );
      assertThat( collection.hasUniqueOperator( OP_LIST, true ), is( false ) );
      assertThat( collection.hasUniqueOperator( OP_LIST, false ), is( false ) );
      assertThat( collection.hasUniqueOperator( OP_ADDED, false ), is( true ) );
      assertThat( collection.hasUniqueOperator( OP_ADDED, true ), is( false ) );
      assertThat( collection.hasUniqueOperator( OP_COMPLETED, false ),
                  is( false ) );
      assertThat( collection.hasUniqueOperator( OP_COMPLETED, true ),
                  is( false ) );
   }
   
   
   
   @Test
   public void testHasUniqueOperatorWithValue()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      assertThat( collection.hasUniqueOperatorWithValue( OP_LIST, "List", true ),
                  is( false ) );
      assertThat( collection.hasUniqueOperatorWithValue( OP_LIST, "List", false ),
                  is( false ) );
      
      RtmSmartFilterToken token1 = new RtmSmartFilterToken( OP_LIST,
                                                            "List",
                                                            false );
      RtmSmartFilterToken token2 = new RtmSmartFilterToken( OP_ADDED,
                                                            "today",
                                                            false );
      RtmSmartFilterToken token3 = new RtmSmartFilterToken( OP_LIST,
                                                            "List1",
                                                            true );
      
      Collection< RtmSmartFilterToken > tokens = getTokens( token1,
                                                            token2,
                                                            token3 );
      
      collection = new RtmSmartFilterTokenCollection( tokens );
      assertThat( collection.hasUniqueOperatorWithValue( OP_LIST, "List", true ),
                  is( false ) );
      assertThat( collection.hasUniqueOperatorWithValue( OP_LIST, "List", false ),
                  is( false ) );
      assertThat( collection.hasUniqueOperatorWithValue( OP_LIST, "List1", true ),
                  is( false ) );
      assertThat( collection.hasUniqueOperatorWithValue( OP_LIST,
                                                         "List1",
                                                         false ), is( false ) );
      assertThat( collection.hasUniqueOperatorWithValue( OP_ADDED,
                                                         "today",
                                                         false ), is( true ) );
      assertThat( collection.hasUniqueOperatorWithValue( OP_ADDED,
                                                         "today",
                                                         true ), is( false ) );
      assertThat( collection.hasUniqueOperatorWithValue( OP_ADDED,
                                                         "Today",
                                                         false ), is( true ) );
      assertThat( collection.hasUniqueOperatorWithValue( OP_ADDED,
                                                         "Tommorrow",
                                                         false ), is( false ) );
      assertThat( collection.hasUniqueOperatorWithValue( OP_COMPLETED,
                                                         "now",
                                                         false ), is( false ) );
      assertThat( collection.hasUniqueOperatorWithValue( OP_COMPLETED,
                                                         "now",
                                                         true ), is( false ) );
   }
   
   
   
   @Test
   public void testHasCompletedOperator()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      assertThat( collection.hasCompletedOperator(), is( false ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( new RtmSmartFilterToken( OP_COMPLETED,
                                                                                          "now",
                                                                                          false ) ) );
      assertThat( collection.hasCompletedOperator(), is( true ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( new RtmSmartFilterToken( OP_COMPLETED_AFTER,
                                                                                          "now",
                                                                                          false ) ) );
      assertThat( collection.hasCompletedOperator(), is( true ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( new RtmSmartFilterToken( OP_COMPLETED_BEFORE,
                                                                                          "now",
                                                                                          true ) ) );
      assertThat( collection.hasCompletedOperator(), is( true ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( new RtmSmartFilterToken( OP_COMPLETED_WITHIN,
                                                                                          "now",
                                                                                          true ) ) );
      assertThat( collection.hasCompletedOperator(), is( true ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( new RtmSmartFilterToken( OP_STATUS,
                                                                                          COMPLETED,
                                                                                          false ) ) );
      assertThat( collection.hasCompletedOperator(), is( true ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( new RtmSmartFilterToken( OP_STATUS,
                                                                                          INCOMPLETE,
                                                                                          false ) ) );
      assertThat( collection.hasCompletedOperator(), is( false ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( new RtmSmartFilterToken( OP_ADDED,
                                                                                          "now",
                                                                                          false ) ) );
      assertThat( collection.hasCompletedOperator(), is( false ) );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testAdd()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      collection.add( new RtmSmartFilterToken( OP_ADDED, "today", false ) );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testAddAll()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      
      collection.addAll( Arrays.asList( new RtmSmartFilterToken( OP_ADDED,
                                                                 "today",
                                                                 false ),
                                        new RtmSmartFilterToken( OP_DUE,
                                                                 "today",
                                                                 false ) ) );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testClear()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      collection.clear();
   }
   
   
   
   @Test
   public void testContains()
   {
      RtmSmartFilterToken token1 = new RtmSmartFilterToken( OP_LIST,
                                                            "List",
                                                            false );
      
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      assertThat( collection.contains( token1 ), is( false ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( token1 ) );
      assertThat( collection.contains( token1 ), is( true ) );
   }
   
   
   
   @Test
   public void testContainsAll()
   {
      RtmSmartFilterToken token1 = new RtmSmartFilterToken( OP_LIST,
                                                            "List",
                                                            false );
      
      RtmSmartFilterToken token2 = new RtmSmartFilterToken( OP_ADDED,
                                                            "today",
                                                            false );
      
      Collection< RtmSmartFilterToken > tokens = getTokens( token1, token2 );
      
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      assertThat( collection.containsAll( tokens ), is( false ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( token1 ) );
      assertThat( collection.containsAll( tokens ), is( false ) );
      
      collection = new RtmSmartFilterTokenCollection( tokens );
      assertThat( collection.containsAll( tokens ), is( true ) );
   }
   
   
   
   @Test
   public void testIsEmpty()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      assertThat( collection.isEmpty(), is( true ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( new RtmSmartFilterToken( OP_LIST,
                                                                                          "List",
                                                                                          false ) ) );
      assertThat( collection.isEmpty(), is( false ) );
   }
   
   
   
   @Test
   public void testIterator()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      
      assertThat( collection.iterator(), notNullValue() );
      assertThat( collection.iterator().hasNext(), is( false ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( new RtmSmartFilterToken( OP_LIST,
                                                                                          "List",
                                                                                          false ) ) );
      
      assertThat( collection.iterator(), notNullValue() );
      assertThat( collection.iterator().hasNext(), is( true ) );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testRemove()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      collection.remove( new RtmSmartFilterToken( OP_LIST, "List", false ) );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testRemoveAll()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      collection.removeAll( getTokens( new RtmSmartFilterToken( OP_LIST,
                                                                "List",
                                                                false ) ) );
   }
   
   
   
   @Test( expected = UnsupportedOperationException.class )
   public void testRetainAll()
   {
      RtmSmartFilterToken token1 = new RtmSmartFilterToken( OP_LIST,
                                                            "List",
                                                            false );
      
      RtmSmartFilterToken token2 = new RtmSmartFilterToken( OP_ADDED,
                                                            "today",
                                                            false );
      
      Collection< RtmSmartFilterToken > tokens = getTokens( token1, token2 );
      
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      collection.retainAll( tokens );
   }
   
   
   
   @Test
   public void testSize()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      
      assertThat( collection.size(), is( 0 ) );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( new RtmSmartFilterToken( OP_LIST,
                                                                                          "List",
                                                                                          false ) ) );
      assertThat( collection.size(), is( 1 ) );
   }
   
   
   
   @Test
   public void testToArray()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      
      assertArrayEquals( new Object[] {}, collection.toArray() );
      
      RtmSmartFilterToken rtmSmartFilterToken = new RtmSmartFilterToken( OP_LIST,
                                                                         "List",
                                                                         false );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( rtmSmartFilterToken ) );
      assertArrayEquals( new Object[]
      { rtmSmartFilterToken }, collection.toArray() );
   }
   
   
   
   @Test
   public void testToArrayTArray()
   {
      RtmSmartFilterTokenCollection collection = new RtmSmartFilterTokenCollection( Collections.< RtmSmartFilterToken > emptyList() );
      
      assertArrayEquals( new RtmSmartFilterToken[] {},
                         collection.toArray( new RtmSmartFilterToken[] {} ) );
      
      RtmSmartFilterToken rtmSmartFilterToken = new RtmSmartFilterToken( OP_LIST,
                                                                         "List",
                                                                         false );
      
      collection = new RtmSmartFilterTokenCollection( getTokens( rtmSmartFilterToken ) );
      assertArrayEquals( new RtmSmartFilterToken[]
                         { rtmSmartFilterToken },
                         collection.toArray( new RtmSmartFilterToken[] {} ) );
   }
   
   
   
   private Collection< RtmSmartFilterToken > getTokens( RtmSmartFilterToken... tokens )
   {
      return Arrays.asList( tokens );
   }
}
