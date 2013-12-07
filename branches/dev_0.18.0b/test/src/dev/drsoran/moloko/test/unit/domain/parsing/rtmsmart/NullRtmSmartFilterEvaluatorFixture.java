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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import dev.drsoran.Strings;
import dev.drsoran.moloko.domain.parsing.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.moloko.domain.parsing.rtmsmart.NullRtmSmartFilterEvaluator;
import dev.drsoran.moloko.test.MolokoTestCase;


public class NullRtmSmartFilterEvaluatorFixture extends MolokoTestCase
{
   private IRtmSmartFilterEvaluator evaluator;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      evaluator = new NullRtmSmartFilterEvaluator();
   }
   
   
   
   @Test
   public void testGetResult()
   {
      assertThat( evaluator.getResult(), is( Strings.EMPTY_STRING ) );
   }
   
   
   
   @Test
   public void testReset()
   {
      evaluator.reset();
   }
   
   
   
   @Test
   public void testEvalList()
   {
      assertThat( evaluator.evalList( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalPriority()
   {
      assertThat( evaluator.evalPriority( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalStatus()
   {
      assertThat( evaluator.evalStatus( true ), is( true ) );
      assertThat( evaluator.evalStatus( false ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalTag()
   {
      assertThat( evaluator.evalTag( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalTagContains()
   {
      assertThat( evaluator.evalTagContains( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalIsTagged()
   {
      assertThat( evaluator.evalIsTagged( true ), is( true ) );
      assertThat( evaluator.evalIsTagged( false ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalLocation()
   {
      assertThat( evaluator.evalLocation( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalIsLocated()
   {
      assertThat( evaluator.evalIsLocated( true ), is( true ) );
      assertThat( evaluator.evalIsLocated( false ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalIsRepeating()
   {
      assertThat( evaluator.evalIsRepeating( true ), is( true ) );
      assertThat( evaluator.evalIsRepeating( false ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalTaskName()
   {
      assertThat( evaluator.evalTaskName( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalNoteContains()
   {
      assertThat( evaluator.evalNoteContains( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalHasNotes()
   {
      assertThat( evaluator.evalHasNotes( true ), is( true ) );
      assertThat( evaluator.evalHasNotes( false ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalDue()
   {
      assertThat( evaluator.evalDue( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalDueAfter()
   {
      assertThat( evaluator.evalDueAfter( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalDueBefore()
   {
      assertThat( evaluator.evalDueBefore( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalDueWithIn()
   {
      assertThat( evaluator.evalDueWithIn( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalCompleted()
   {
      assertThat( evaluator.evalCompleted( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalCompletedAfter()
   {
      assertThat( evaluator.evalCompletedAfter( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalCompletedBefore()
   {
      assertThat( evaluator.evalCompletedBefore( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalCompletedWithIn()
   {
      assertThat( evaluator.evalCompletedWithIn( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalAdded()
   {
      assertThat( evaluator.evalAdded( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalAddedAfter()
   {
      assertThat( evaluator.evalAddedAfter( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalAddedBefore()
   {
      assertThat( evaluator.evalAddedBefore( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalAddedWithIn()
   {
      assertThat( evaluator.evalAddedWithIn( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalTimeEstimate()
   {
      assertThat( evaluator.evalTimeEstimate( "", "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalPostponed()
   {
      assertThat( evaluator.evalPostponed( null, 0 ), is( true ) );
      assertThat( evaluator.evalPostponed( "<", 10 ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalIsShared()
   {
      assertThat( evaluator.evalIsShared( true ), is( true ) );
      assertThat( evaluator.evalIsShared( false ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalSharedWith()
   {
      assertThat( evaluator.evalSharedWith( "" ), is( true ) );
   }
   
   
   
   @Test
   public void testEvalLeftParenthesis()
   {
      assertThat( evaluator.evalLeftParenthesis(), is( true ) );
   }
   
   
   
   @Test
   public void testEvalRightParenthesis()
   {
      assertThat( evaluator.evalRightParenthesis(), is( true ) );
   }
   
   
   
   @Test
   public void testEvalAnd()
   {
      assertThat( evaluator.evalAnd(), is( true ) );
   }
   
   
   
   @Test
   public void testEvalOr()
   {
      assertThat( evaluator.evalOr(), is( true ) );
   }
   
   
   
   @Test
   public void testEvalNot()
   {
      assertThat( evaluator.evalNot(), is( true ) );
   }
   
}
