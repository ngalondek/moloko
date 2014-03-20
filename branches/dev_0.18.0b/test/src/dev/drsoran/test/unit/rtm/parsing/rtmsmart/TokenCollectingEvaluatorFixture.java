/* 
 *	Copyright (c) 2013 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.test.unit.rtm.parsing.rtmsmart;

import static dev.drsoran.moloko.test.IterableAsserts.assertEmpty;
import static dev.drsoran.moloko.test.IterableAsserts.assertEqualSet;
import static dev.drsoran.moloko.test.IterableAsserts.assertNotEmpty;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_ADDED;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_ADDED_AFTER;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_ADDED_BEFORE;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_ADDED_WITHIN;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_COMPLETED;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_COMPLETED_AFTER;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_COMPLETED_BEFORE;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_COMPLETED_WITHIN;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_DUE;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_DUE_AFTER;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_DUE_BEFORE;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_DUE_WITHIN;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_HAS_NOTES;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_IS_LOCATED;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_IS_REPEATING;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_IS_SHARED;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_IS_TAGGED;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_LIST;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_LOCATION;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_NAME;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_NOTE_CONTAINS;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_POSTPONED;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_PRIORITY;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_SHARED_WITH;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_STATUS;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_TAG;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_TAG_CONTAINS;
import static dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer.OP_TIME_ESTIMATE;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.COMPLETED;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.FALSE;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.INCOMPLETE;
import static dev.drsoran.rtm.parsing.grammar.rtmsmart.RtmSmartFilterSyntax.TRUE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.rtm.parsing.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.rtm.parsing.rtmsmart.NullRtmSmartFilterEvaluator;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterToken;
import dev.drsoran.rtm.parsing.rtmsmart.TokenCollectingEvaluator;


public class TokenCollectingEvaluatorFixture extends MolokoTestCase
{
   private IRtmSmartFilterEvaluator decoratedEvaluator;
   
   private TokenCollectingEvaluator evaluator;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      
      decoratedEvaluator = new NullRtmSmartFilterEvaluator();
      evaluator = new TokenCollectingEvaluator( decoratedEvaluator );
   }
   
   
   
   @Test( expected = IllegalArgumentException.class )
   public void testTokenCollectingEvaluatorNullDecorated()
   {
      new TokenCollectingEvaluator( null );
   }
   
   
   
   @Test
   public void testGetResult()
   {
      decoratedEvaluator = EasyMock.createStrictMock( IRtmSmartFilterEvaluator.class );
      
      EasyMock.expect( decoratedEvaluator.getResult() ).andReturn( "Result" );
      EasyMock.replay( decoratedEvaluator );
      
      evaluator = new TokenCollectingEvaluator( decoratedEvaluator );
      
      assertThat( evaluator.getResult(), is( "Result" ) );
      
      EasyMock.verify( decoratedEvaluator );
   }
   
   
   
   @Test
   public void testReset()
   {
      decoratedEvaluator = EasyMock.createStrictMock( IRtmSmartFilterEvaluator.class );
      
      EasyMock.expect( decoratedEvaluator.evalList( "" ) ).andReturn( true );
      decoratedEvaluator.reset();
      EasyMock.replay( decoratedEvaluator );
      
      evaluator = new TokenCollectingEvaluator( decoratedEvaluator );
      
      evaluator.evalList( "" );
      assertNotEmpty( evaluator.getTokens() );
      
      evaluator.reset();
      assertEmpty( evaluator.getTokens() );
      
      EasyMock.verify( decoratedEvaluator );
   }
   
   
   
   @Test
   public void testGetTokens()
   {
      assertEmpty( evaluator.getTokens() );
      assertTrue( evaluator.evalList( "List" ) );
      assertNotEmpty( evaluator.getTokens() );
   }
   
   
   
   @Test
   public void testEvalEmptyFilter()
   {
      assertTrue( evaluator.evalEmptyFilter() );
      assertEmpty( evaluator.getTokens() );
   }
   
   
   
   @Test
   public void testEvalList()
   {
      assertTrue( evaluator.evalList( "List" ) );
      assertToken( OP_LIST, "List" );
   }
   
   
   
   @Test
   public void testEvalPriority()
   {
      assertTrue( evaluator.evalPriority( "1" ) );
      assertToken( OP_PRIORITY, "1" );
   }
   
   
   
   @Test
   public void testEvalStatus()
   {
      assertTrue( evaluator.evalStatus( true ) );
      assertToken( OP_STATUS, COMPLETED );
      
      evaluator.reset();
      
      assertTrue( evaluator.evalStatus( false ) );
      assertToken( OP_STATUS, INCOMPLETE );
   }
   
   
   
   @Test
   public void testEvalTag()
   {
      assertTrue( evaluator.evalTag( "Tag" ) );
      assertToken( OP_TAG, "Tag" );
   }
   
   
   
   @Test
   public void testEvalTagContains()
   {
      assertTrue( evaluator.evalTagContains( "Tag" ) );
      assertToken( OP_TAG_CONTAINS, "Tag" );
   }
   
   
   
   @Test
   public void testEvalIsTagged()
   {
      assertTrue( evaluator.evalIsTagged( true ) );
      assertToken( OP_IS_TAGGED, TRUE );
      
      evaluator.reset();
      
      assertTrue( evaluator.evalIsTagged( false ) );
      assertToken( OP_IS_TAGGED, FALSE );
   }
   
   
   
   @Test
   public void testEvalLocation()
   {
      assertTrue( evaluator.evalLocation( "Loc" ) );
      assertToken( OP_LOCATION, "Loc" );
   }
   
   
   
   @Test
   public void testEvalIsLocated()
   {
      assertTrue( evaluator.evalIsLocated( true ) );
      assertToken( OP_IS_LOCATED, TRUE );
      
      evaluator.reset();
      
      assertTrue( evaluator.evalIsLocated( false ) );
      assertToken( OP_IS_LOCATED, FALSE );
   }
   
   
   
   @Test
   public void testEvalIsRepeating()
   {
      assertTrue( evaluator.evalIsRepeating( true ) );
      assertToken( OP_IS_REPEATING, TRUE );
      
      evaluator.reset();
      
      assertTrue( evaluator.evalIsRepeating( false ) );
      assertToken( OP_IS_REPEATING, FALSE );
   }
   
   
   
   @Test
   public void testEvalTaskName()
   {
      assertTrue( evaluator.evalTaskName( "Task" ) );
      assertToken( OP_NAME, "Task" );
   }
   
   
   
   @Test
   public void testEvalNoteContains()
   {
      assertTrue( evaluator.evalNoteContains( "Note" ) );
      assertToken( OP_NOTE_CONTAINS, "Note" );
   }
   
   
   
   @Test
   public void testEvalHasNotes()
   {
      assertTrue( evaluator.evalHasNotes( true ) );
      assertToken( OP_HAS_NOTES, TRUE );
      
      evaluator.reset();
      
      assertTrue( evaluator.evalHasNotes( false ) );
      assertToken( OP_HAS_NOTES, FALSE );
   }
   
   
   
   @Test
   public void testEvalDue()
   {
      assertTrue( evaluator.evalDue( "today" ) );
      assertToken( OP_DUE, "today" );
   }
   
   
   
   @Test
   public void testEvalDueAfter()
   {
      assertTrue( evaluator.evalDueAfter( "today" ) );
      assertToken( OP_DUE_AFTER, "today" );
   }
   
   
   
   @Test
   public void testEvalDueBefore()
   {
      assertTrue( evaluator.evalDueBefore( "today" ) );
      assertToken( OP_DUE_BEFORE, "today" );
   }
   
   
   
   @Test
   public void testEvalDueWithIn()
   {
      assertTrue( evaluator.evalDueWithIn( "today" ) );
      assertToken( OP_DUE_WITHIN, "today" );
   }
   
   
   
   @Test
   public void testEvalCompleted()
   {
      assertTrue( evaluator.evalCompleted( "today" ) );
      assertToken( OP_COMPLETED, "today" );
   }
   
   
   
   @Test
   public void testEvalCompletedAfter()
   {
      assertTrue( evaluator.evalCompletedAfter( "today" ) );
      assertToken( OP_COMPLETED_AFTER, "today" );
   }
   
   
   
   @Test
   public void testEvalCompletedBefore()
   {
      assertTrue( evaluator.evalCompletedBefore( "today" ) );
      assertToken( OP_COMPLETED_BEFORE, "today" );
   }
   
   
   
   @Test
   public void testEvalCompletedWithIn()
   {
      assertTrue( evaluator.evalCompletedWithIn( "today" ) );
      assertToken( OP_COMPLETED_WITHIN, "today" );
   }
   
   
   
   @Test
   public void testEvalAdded()
   {
      assertTrue( evaluator.evalAdded( "today" ) );
      assertToken( OP_ADDED, "today" );
   }
   
   
   
   @Test
   public void testEvalAddedAfter()
   {
      assertTrue( evaluator.evalAddedAfter( "today" ) );
      assertToken( OP_ADDED_AFTER, "today" );
   }
   
   
   
   @Test
   public void testEvalAddedBefore()
   {
      assertTrue( evaluator.evalAddedBefore( "today" ) );
      assertToken( OP_ADDED_BEFORE, "today" );
   }
   
   
   
   @Test
   public void testEvalAddedWithIn()
   {
      assertTrue( evaluator.evalAddedWithIn( "today" ) );
      assertToken( OP_ADDED_WITHIN, "today" );
   }
   
   
   
   @Test
   public void testEvalTimeEstimate()
   {
      assertTrue( evaluator.evalTimeEstimate( ">", "2 days" ) );
      assertToken( OP_TIME_ESTIMATE, ">2 days" );
      
      evaluator.reset();
      
      assertTrue( evaluator.evalTimeEstimate( "", "2 days" ) );
      assertToken( OP_TIME_ESTIMATE, "2 days" );
   }
   
   
   
   @Test
   public void testEvalPostponed()
   {
      assertTrue( evaluator.evalPostponed( "", 0 ) );
      assertToken( OP_POSTPONED, "0" );
      
      evaluator.reset();
      
      assertTrue( evaluator.evalPostponed( ">", 10 ) );
      assertToken( OP_POSTPONED, ">10" );
   }
   
   
   
   @Test
   public void testEvalIsShared()
   {
      assertTrue( evaluator.evalIsShared( true ) );
      assertToken( OP_IS_SHARED, TRUE );
      
      evaluator.reset();
      
      assertTrue( evaluator.evalIsShared( false ) );
      assertToken( OP_IS_SHARED, FALSE );
   }
   
   
   
   @Test
   public void testEvalSharedWith()
   {
      assertTrue( evaluator.evalSharedWith( "Me" ) );
      assertToken( OP_SHARED_WITH, "Me" );
   }
   
   
   
   @Test
   public void testEvalLeftParenthesis()
   {
      assertTrue( evaluator.evalLeftParenthesis() );
      assertEmpty( evaluator.getTokens() );
   }
   
   
   
   @Test
   public void testEvalRightParenthesis()
   {
      assertTrue( evaluator.evalRightParenthesis() );
      assertEmpty( evaluator.getTokens() );
   }
   
   
   
   @Test
   public void testEvalAnd()
   {
      assertTrue( evaluator.evalAnd() );
      assertEmpty( evaluator.getTokens() );
   }
   
   
   
   @Test
   public void testEvalOr()
   {
      assertTrue( evaluator.evalOr() );
      assertEmpty( evaluator.getTokens() );
   }
   
   
   
   @Test
   public void testEvalNot()
   {
      assertTrue( evaluator.evalNot() );
      assertEmpty( evaluator.getTokens() );
   }
   
   
   
   @Test
   public void testNegated()
   {
      assertTrue( evaluator.evalNot() );
      assertTrue( evaluator.evalList( "List" ) );
      
      assertToken( OP_LIST, "List", true );
   }
   
   
   
   @Test
   public void testNegatedMixed()
   {
      assertTrue( evaluator.evalNot() );
      assertTrue( evaluator.evalList( "List" ) );
      assertTrue( evaluator.evalAnd() );
      assertTrue( evaluator.evalPriority( "n" ) );
      assertTrue( evaluator.evalOr() );
      assertTrue( evaluator.evalNot() );
      assertTrue( evaluator.evalCompleted( "today" ) );
      
      assertEqualSet( evaluator.getTokens(),
                      new RtmSmartFilterToken( OP_LIST, "List", true ),
                      new RtmSmartFilterToken( OP_PRIORITY, "n", false ),
                      new RtmSmartFilterToken( OP_COMPLETED, "today", true ) );
   }
   
   
   
   @Test
   public void testNegatedResetState()
   {
      assertTrue( evaluator.evalNot() );
      assertTrue( evaluator.evalList( "List" ) );
      
      assertToken( OP_LIST, "List", true );
      
      evaluator.reset();
      
      assertTrue( evaluator.evalList( "List" ) );
      assertToken( OP_LIST, "List" );
   }
   
   
   
   private void assertToken( int operator, String value )
   {
      assertToken( operator, value, false );
   }
   
   
   
   private void assertToken( int operator, String value, boolean negated )
   {
      assertEqualSet( evaluator.getTokens(), new RtmSmartFilterToken( operator,
                                                                      value,
                                                                      negated ) );
   }
}