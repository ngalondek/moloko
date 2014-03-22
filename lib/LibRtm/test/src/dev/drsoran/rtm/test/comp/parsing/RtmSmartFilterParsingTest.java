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

package dev.drsoran.rtm.test.comp.parsing;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import dev.drsoran.rtm.ILog;
import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmSmartFilterParsing;
import dev.drsoran.rtm.parsing.RtmSmartFilterParsing;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.rtm.parsing.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterParsingReturn;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterTokenCollection;


public class RtmSmartFilterParsingTest
{
   private IRtmSmartFilterParsing smartFilterParsing;
   
   
   
   @Before
   public void setUp() throws Exception
   {
      smartFilterParsing = new RtmSmartFilterParsing( EasyMock.createNiceMock( ILog.class ) );
   }
   
   
   
   @Test
   public void testEvaluateRtmSmartFilterString() throws GrammarException
   {
      final RtmSmartFilterParsingReturn res = smartFilterParsing.evaluateRtmSmartFilter( "list:List" );
      
      assertThat( res, notNullValue() );
      assertThat( res.hasCompletedOperator, is( false ) );
   }
   
   
   
   @Test
   public void testEvaluateRtmSmartFilterString_CompletedOps() throws GrammarException
   {
      RtmSmartFilterParsingReturn res = smartFilterParsing.evaluateRtmSmartFilter( "status:completed" );
      assertThat( res.hasCompletedOperator, is( true ) );
      
      res = smartFilterParsing.evaluateRtmSmartFilter( "status:incomplete" );
      assertThat( res.hasCompletedOperator, is( false ) );
      
      res = smartFilterParsing.evaluateRtmSmartFilter( "completed:today" );
      assertThat( res.hasCompletedOperator, is( true ) );
      
      res = smartFilterParsing.evaluateRtmSmartFilter( "completedAfter:today" );
      assertThat( res.hasCompletedOperator, is( true ) );
      
      res = smartFilterParsing.evaluateRtmSmartFilter( "completedBefore:today" );
      assertThat( res.hasCompletedOperator, is( true ) );
      
      res = smartFilterParsing.evaluateRtmSmartFilter( "completedWithIn:'2 of today'" );
      assertThat( res.hasCompletedOperator, is( true ) );
   }
   
   
   
   @Test
   public void testEvaluateRtmSmartFilterString_Empty() throws GrammarException
   {
      smartFilterParsing.evaluateRtmSmartFilter( Strings.EMPTY_STRING );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testEvaluateRtmSmartFilterString_Invalid() throws GrammarException
   {
      smartFilterParsing.evaluateRtmSmartFilter( "ab:list" );
   }
   
   
   
   @Test
   public void testEvaluateRtmSmartFilterWithEvaluator() throws GrammarException
   {
      final IRtmSmartFilterEvaluator eval = EasyMock.createStrictMock( IRtmSmartFilterEvaluator.class );
      EasyMock.expect( eval.evalList( "List" ) ).andReturn( true );
      EasyMock.replay( eval );
      
      smartFilterParsing.evaluateRtmSmartFilter( "list:List", eval );
      
      EasyMock.verify( eval );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testEvaluateRtmSmartFilterWithEvaluator_EvalFailed() throws GrammarException
   {
      final IRtmSmartFilterEvaluator eval = EasyMock.createNiceMock( IRtmSmartFilterEvaluator.class );
      EasyMock.expect( eval.evalList( "List" ) ).andReturn( false );
      EasyMock.replay( eval );
      
      smartFilterParsing.evaluateRtmSmartFilter( "list:List", eval );
   }
   
   
   
   @Test
   public void testEvaluateRtmSmartFilterWithEvaluator_Empty() throws GrammarException
   {
      try
      {
         final IRtmSmartFilterEvaluator eval = EasyMock.createStrictMock( IRtmSmartFilterEvaluator.class );
         EasyMock.expect( eval.evalEmptyFilter() ).andReturn( Boolean.TRUE );
         EasyMock.replay( eval );
         
         smartFilterParsing.evaluateRtmSmartFilter( Strings.EMPTY_STRING, eval );
         
         EasyMock.verify( eval );
      }
      catch ( GrammarException e )
      {
         throw e;
      }
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testEvaluateRtmSmartFilterWithEvaluator_Invalid() throws GrammarException
   {
      try
      {
         final IRtmSmartFilterEvaluator eval = EasyMock.createStrictMock( IRtmSmartFilterEvaluator.class );
         EasyMock.replay( eval );
         
         smartFilterParsing.evaluateRtmSmartFilter( "ab:list", eval );
         
         EasyMock.verify( eval );
      }
      catch ( GrammarException e )
      {
         throw e;
      }
   }
   
   
   
   @Test
   public void testGetSmartFilterTokens() throws GrammarException
   {
      final RtmSmartFilterTokenCollection tokens = smartFilterParsing.getSmartFilterTokens( "list:List" );
      
      assertThat( tokens, notNullValue() );
      assertThat( tokens,
                  hasItem( new RtmSmartFilterToken( RtmSmartFilterLexer.OP_LIST,
                                                    "List",
                                                    false ) ) );
   }
   
   
   
   @Test
   public void testGetSmartFilterTokens_Empty() throws GrammarException
   {
      smartFilterParsing.getSmartFilterTokens( Strings.EMPTY_STRING );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testGetSmartFilterTokens_Invalid() throws GrammarException
   {
      smartFilterParsing.getSmartFilterTokens( "ab:cd" );
   }
   
   
   
   @Test
   public void testIsParsableSmartFilter()
   {
      assertThat( smartFilterParsing.isParsableSmartFilter( "list:List" ),
                  is( true ) );
      assertThat( smartFilterParsing.isParsableSmartFilter( Strings.EMPTY_STRING ),
                  is( true ) );
      assertThat( smartFilterParsing.isParsableSmartFilter( "abc:def" ),
                  is( false ) );
   }
   
}
