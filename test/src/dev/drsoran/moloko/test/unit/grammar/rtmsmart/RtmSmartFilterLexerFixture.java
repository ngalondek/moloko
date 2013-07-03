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

package dev.drsoran.moloko.test.unit.grammar.rtmsmart;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.domain.model.Priority;
import dev.drsoran.moloko.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.moloko.grammar.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.moloko.test.Lambda;
import dev.drsoran.moloko.test.Lambda.Func1;
import dev.drsoran.moloko.test.MolokoTestCase;


@RunWith( Theories.class )
public class RtmSmartFilterLexerFixture extends MolokoTestCase
{
   @DataPoint
   public static IRtmSmartFilterEvaluator NULL_EVALUATOR = null;
   
   @DataPoint
   public static IRtmSmartFilterEvaluator MOCK_EVALUATOR = EasyMock.createStrictMock( IRtmSmartFilterEvaluator.class );
   
   @Rule
   public static ExpectedException THROWN = ExpectedException.none();
   
   
   
   @Test
   public void testRtmSmartFilterLexer()
   {
      new RtmSmartFilterLexer();
   }
   
   
   
   @Test
   public void testRtmSmartFilterLexerCharStream()
   {
      new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "test" ) );
   }
   
   
   
   @Test
   public void testRtmSmartFilterLexerCharStreamRecognizerSharedState()
   {
      new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "test" ),
                               new RecognizerSharedState() );
   }
   
   
   
   @Test
   public void testGetGrammarFileName()
   {
      assertThat( new RtmSmartFilterLexer().getGrammarFileName(),
                  notNullValue() );
   }
   
   
   
   @Test
   public void testReset() throws GrammarException
   {
      final RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "completed:Today" ) );
      assertThat( lexer.hasStatusCompletedOperator(), is( false ) );
      
      lexer.startEvaluation();
      assertThat( lexer.hasStatusCompletedOperator(), is( true ) );
      
      lexer.reset();
      assertThat( lexer.hasStatusCompletedOperator(), is( false ) );
   }
   
   
   
   @Test
   public void testHasStatusCompletedOperator() throws GrammarException
   {
      RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "status:completed" ) );
      lexer.startEvaluation();
      assertThat( lexer.hasStatusCompletedOperator(), is( true ) );
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "status:incomplete" ) );
      lexer.startEvaluation();
      assertThat( lexer.hasStatusCompletedOperator(), is( false ) );
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "completed:Today" ) );
      lexer.startEvaluation();
      assertThat( lexer.hasStatusCompletedOperator(), is( true ) );
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "completedafter:Today" ) );
      lexer.startEvaluation();
      assertThat( lexer.hasStatusCompletedOperator(), is( true ) );
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "completedbefore:Today" ) );
      lexer.startEvaluation();
      assertThat( lexer.hasStatusCompletedOperator(), is( true ) );
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "completedwithin:'2 ofToday'" ) );
      lexer.startEvaluation();
      assertThat( lexer.hasStatusCompletedOperator(), is( true ) );
   }
   
   
   
   @Test
   public void testGetDelegates()
   {
      assertArrayEquals( new RtmSmartFilterLexer().getDelegates(),
                         new Lexer[] {} );
   }
   
   
   
   @Theory
   public void testMOP_LIST( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalList( "List" ) );
         }
      };
      
      assertLexingOkEvalOk( "list:List", evaluator, evalFunc );
      assertLexingOkEvalOk( "list:\"List\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "list:'List'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "list:\"List with Spaces\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalList( "List with Spaces" ) );
                               }
                            } );
      
      assertLexingFailed( "list:", evaluator );
      
      assertLexingOkEvalFailed( "list:List", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_PRIORITY( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalPriority( Priority.High.toString() ) );
         }
      };
      
      assertLexingOkEvalOk( "priority:1", evaluator, evalFunc );
      assertLexingOkEvalOk( "priority:2",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalPriority( Priority.Medium.toString() ) );
                               }
                            } );
      assertLexingOkEvalOk( "priority:3",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalPriority( Priority.Low.toString() ) );
                               }
                            } );
      assertLexingOkEvalOk( "priority:n",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalPriority( Priority.None.toString() ) );
                               }
                            } );
      assertLexingOkEvalOk( "priority:N",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalPriority( "N" ) );
                               }
                            } );
      
      assertLexingFailed( "priority:", evaluator );
      assertLexingFailed( "priority:-1", evaluator );
      assertLexingFailed( "priority:4", evaluator );
      
      assertLexingOkEvalFailed( "priority:1", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_STATUS( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalStatus( true ) );
         }
      };
      
      assertLexingOkEvalOk( "status:completed", evaluator, evalFunc );
      assertLexingOkEvalOk( "status:incomplete",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalStatus( false ) );
                               }
                            } );
      
      assertLexingFailed( "status:", evaluator );
      assertLexingFailed( "status:compl", evaluator );
      
      assertLexingOkEvalFailed( "status:completed", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_TAG( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalTag( "atag" ) );
         }
      };
      
      assertLexingOkEvalOk( "tag:atag", evaluator, evalFunc );
      assertLexingOkEvalOk( "tag:'atag'", evaluator, evalFunc );
      assertLexingOkEvalOk( "tag:\"atag\"", evaluator, evalFunc );
      
      assertLexingFailed( "tag:", evaluator );
      
      assertLexingOkEvalFailed( "tag:atag", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_TAG_CONTAINS( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalTagContains( "atag" ) );
         }
      };
      
      assertLexingOkEvalOk( "tagcontains:atag", evaluator, evalFunc );
      assertLexingOkEvalOk( "tagcontains:'atag'", evaluator, evalFunc );
      assertLexingOkEvalOk( "tagcontains:\"atag\"", evaluator, evalFunc );
      
      assertLexingFailed( "tagcontains:", evaluator );
      
      assertLexingOkEvalFailed( "tagcontains:atag", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_IS_TAGGED( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalIsTagged( true ) );
         }
      };
      
      assertLexingOkEvalOk( "istagged:true", evaluator, evalFunc );
      assertLexingOkEvalOk( "istagged:false",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalIsTagged( false ) );
                               }
                            } );
      
      assertLexingFailed( "istagged:", evaluator );
      assertLexingFailed( "istagged:!true", evaluator );
      
      assertLexingOkEvalFailed( "istagged:true", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_LOCATION( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalLocation( "@Home" ) );
         }
      };
      
      assertLexingOkEvalOk( "location:@Home", evaluator, evalFunc );
      assertLexingOkEvalOk( "location:\"@Home\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "location:'@Home'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "location:\"Location @Space\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalLocation( "Location @Space" ) );
                               }
                            } );
      
      assertLexingFailed( "location:", evaluator );
      
      assertLexingOkEvalFailed( "location:@Home", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_ISLOCATED( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalIsLocated( true ) );
         }
      };
      
      assertLexingOkEvalOk( "islocated:true", evaluator, evalFunc );
      assertLexingOkEvalOk( "islocated:false",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalIsLocated( false ) );
                               }
                            } );
      
      assertLexingFailed( "islocated:", evaluator );
      assertLexingFailed( "islocated:!true", evaluator );
      
      assertLexingOkEvalFailed( "islocated:true", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_IS_REPEATING( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalIsRepeating( true ) );
         }
      };
      
      assertLexingOkEvalOk( "isrepeating:true", evaluator, evalFunc );
      assertLexingOkEvalOk( "isrepeating:false",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalIsRepeating( false ) );
                               }
                            } );
      
      assertLexingFailed( "isrepeating:", evaluator );
      assertLexingFailed( "isrepeating:!true", evaluator );
      
      assertLexingOkEvalFailed( "isrepeating:true", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_NAME( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalTaskName( "Task" ) );
         }
      };
      
      assertLexingOkEvalOk( "name:Task", evaluator, evalFunc );
      assertLexingOkEvalOk( "name:\"Task\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "name:'Task'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "name:\"Task with Spaces\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalTaskName( "Task with Spaces" ) );
                               }
                            } );
      
      assertLexingFailed( "name:", evaluator );
      
      assertLexingOkEvalFailed( "name:Task", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_NOTE_CONTAINS( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalNoteContains( "content" ) );
         }
      };
      
      assertLexingOkEvalOk( "notecontains:content", evaluator, evalFunc );
      assertLexingOkEvalOk( "notecontains:\"content\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "notecontains:'content'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "notecontains:\"content with spaces\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalNoteContains( "content with spaces" ) );
                               }
                            } );
      
      assertLexingFailed( "notecontains:", evaluator );
      
      assertLexingOkEvalFailed( "notecontains:content", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_HAS_NOTES( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalHasNotes( true ) );
         }
      };
      
      assertLexingOkEvalOk( "hasnotes:true", evaluator, evalFunc );
      assertLexingOkEvalOk( "hasnotes:false",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalHasNotes( false ) );
                               }
                            } );
      
      assertLexingFailed( "hasnotes:", evaluator );
      assertLexingFailed( "hasnotes:!true", evaluator );
      
      assertLexingOkEvalFailed( "hasnotes:true", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_DUE( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalDue( "Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "due:Today", evaluator, evalFunc );
      assertLexingOkEvalOk( "due:\"Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "due:'Today'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "due:\"Tomorrow at 6\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalDue( "Tomorrow at 6" ) );
                               }
                            } );
      
      assertLexingFailed( "due:", evaluator );
      
      assertLexingOkEvalFailed( "due:Today", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_DUE_AFTER( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalDueAfter( "Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "dueafter:Today", evaluator, evalFunc );
      assertLexingOkEvalOk( "dueafter:\"Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "dueafter:'Today'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "dueafter:\"Tomorrow at 6\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalDueAfter( "Tomorrow at 6" ) );
                               }
                            } );
      
      assertLexingFailed( "dueafter:", evaluator );
      
      assertLexingOkEvalFailed( "dueafter:Today", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_DUE_BEFORE( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalDueBefore( "Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "duebefore:Today", evaluator, evalFunc );
      assertLexingOkEvalOk( "duebefore:\"Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "duebefore:'Today'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "duebefore:\"Tomorrow at 6\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalDueBefore( "Tomorrow at 6" ) );
                               }
                            } );
      
      assertLexingFailed( "duebefore:", evaluator );
      
      assertLexingOkEvalFailed( "duebefore:Today", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_DUE_WITHIN( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalDueWithIn( "2 of Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "duewithin:\"2 of Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "duewithin:'2 of Today'", evaluator, evalFunc );
      
      assertLexingFailed( "duewithin:", evaluator );
      
      assertLexingOkEvalFailed( "duewithin:'2 of Today'", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_COMPLETED( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalCompleted( "Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "completed:Today", evaluator, evalFunc );
      assertLexingOkEvalOk( "completed:\"Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "completed:'Today'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "completed:\"Tomorrow at 6\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalCompleted( "Tomorrow at 6" ) );
                               }
                            } );
      
      assertLexingFailed( "completed:", evaluator );
      
      assertLexingOkEvalFailed( "completed:Today", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_COMPLETED_AFTER( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalCompletedAfter( "Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "completedafter:Today", evaluator, evalFunc );
      assertLexingOkEvalOk( "completedafter:\"Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "completedafter:'Today'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "completedafter:\"Tomorrow at 6\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalCompletedAfter( "Tomorrow at 6" ) );
                               }
                            } );
      
      assertLexingFailed( "completedafter:", evaluator );
      
      assertLexingOkEvalFailed( "completedafter:Today", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_COMPLETED_BEFORE( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalCompletedBefore( "Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "completedbefore:Today", evaluator, evalFunc );
      assertLexingOkEvalOk( "completedbefore:\"Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "completedbefore:'Today'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "completedbefore:\"Tomorrow at 6\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalCompletedBefore( "Tomorrow at 6" ) );
                               }
                            } );
      
      assertLexingFailed( "completedbefore:", evaluator );
      
      assertLexingOkEvalFailed( "completedbefore:Today", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_COMPLETED_WITHIN( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalCompletedWithIn( "2 of Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "completedwithin:\"2 of Today\"",
                            evaluator,
                            evalFunc );
      assertLexingOkEvalOk( "completedwithin:'2 of Today'", evaluator, evalFunc );
      
      assertLexingFailed( "completedwithin:", evaluator );
      
      assertLexingOkEvalFailed( "completedwithin:'2 of Today'",
                                evaluator,
                                evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_ADDED( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalAdded( "Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "added:Today", evaluator, evalFunc );
      assertLexingOkEvalOk( "added:\"Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "added:'Today'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "added:\"Tomorrow at 6\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalAdded( "Tomorrow at 6" ) );
                               }
                            } );
      
      assertLexingFailed( "added:", evaluator );
      
      assertLexingOkEvalFailed( "added:Today", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_ADDED_AFTER( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalAddedAfter( "Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "addedafter:Today", evaluator, evalFunc );
      assertLexingOkEvalOk( "addedafter:\"Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "addedafter:'Today'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "addedafter:\"Tomorrow at 6\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalAddedAfter( "Tomorrow at 6" ) );
                               }
                            } );
      
      assertLexingFailed( "addedafter:", evaluator );
      
      assertLexingOkEvalFailed( "addedafter:Today", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_ADDED_BEFORE( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalAddedBefore( "Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "addedbefore:Today", evaluator, evalFunc );
      assertLexingOkEvalOk( "addedbefore:\"Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "addedbefore:'Today'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "addedbefore:\"Tomorrow at 6\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalAddedBefore( "Tomorrow at 6" ) );
                               }
                            } );
      
      assertLexingFailed( "addedbefore:", evaluator );
      
      assertLexingOkEvalFailed( "addedbefore:Today", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_ADDED_WITHIN( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalAddedWithIn( "2 of Today" ) );
         }
      };
      
      assertLexingOkEvalOk( "addedwithin:\"2 of Today\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "addedwithin:'2 of Today'", evaluator, evalFunc );
      
      assertLexingFailed( "addedwithin:", evaluator );
      
      assertLexingOkEvalFailed( "addedwithin:'2 of Today'", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_TIME_ESTIMATE( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalTimeEstimate( ">", "1h" ) );
         }
      };
      
      assertLexingOkEvalOk( "timeestimate:\">1h\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "timeestimate:'>1h'", evaluator, evalFunc );
      assertLexingOkEvalOk( "timeestimate:>1h", evaluator, evalFunc );
      assertLexingOkEvalOk( "timeestimate:'<1h'",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalTimeEstimate( "<",
                                                                                  "1h" ) );
                               }
                            } );
      
      assertLexingFailed( "timeestimate:", evaluator );
      
      assertLexingOkEvalFailed( "timeestimate:'>1h'", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_POSTPONED( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalPostponed( ">", 1 ) );
         }
      };
      
      assertLexingOkEvalOk( "postponed:\">1\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "postponed:'>1'", evaluator, evalFunc );
      assertLexingOkEvalOk( "postponed:>1", evaluator, evalFunc );
      assertLexingOkEvalOk( "postponed:'<2'",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalPostponed( "<",
                                                                               2 ) );
                               }
                            } );
      assertLexingOkEvalOk( "postponed:3",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalPostponed( "",
                                                                               3 ) );
                               }
                            } );
      
      assertLexingFailed( "postponed:", evaluator );
      assertLexingFailed( "postponed:<-1", evaluator );
      assertLexingFailed( "postponed:abc", evaluator );
      assertLexingFailed( "postponed:>three", evaluator );
      
      assertLexingOkEvalFailed( "postponed:>1", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_IS_SHARED( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalIsShared( true ) );
         }
      };
      
      assertLexingOkEvalOk( "isshared:true", evaluator, evalFunc );
      assertLexingOkEvalOk( "isshared:false",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalIsShared( false ) );
                               }
                            } );
      
      assertLexingFailed( "isshared:", evaluator );
      assertLexingFailed( "isshared:!true", evaluator );
      
      assertLexingOkEvalFailed( "isshared:true", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOP_SHARED_WITH( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalSharedWith( "John" ) );
         }
      };
      
      assertLexingOkEvalOk( "sharedwith:John", evaluator, evalFunc );
      assertLexingOkEvalOk( "sharedwith:\"John\"", evaluator, evalFunc );
      assertLexingOkEvalOk( "sharedwith:'John'", evaluator, evalFunc );
      
      assertLexingOkEvalOk( "sharedwith:\"John Doe\"",
                            evaluator,
                            new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                            {
                               @Override
                               public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                               {
                                  return EasyMock.expect( param.evalSharedWith( "John Doe" ) );
                               }
                            } );
      
      assertLexingFailed( "sharedwith:", evaluator );
      
      assertLexingOkEvalFailed( "sharedwith:John", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testML_PARENTH( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalLeftParenthesis() );
         }
      };
      
      assertLexingOkEvalOk( "(", evaluator, evalFunc );
      assertLexingOkEvalFailed( "(", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMR_PARENTH( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalRightParenthesis() );
         }
      };
      
      assertLexingOkEvalOk( ")", evaluator, evalFunc );
      assertLexingOkEvalFailed( ")", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMAND( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalAnd() );
         }
      };
      
      assertLexingOkEvalOk( "and", evaluator, evalFunc );
      assertLexingOkEvalFailed( "and", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMOR( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalOr() );
         }
      };
      
      assertLexingOkEvalOk( "or", evaluator, evalFunc );
      assertLexingOkEvalFailed( "or", evaluator, evalFunc );
   }
   
   
   
   @Theory
   public void testMNOT( IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalNot() );
         }
      };
      
      assertLexingOkEvalOk( "not", evaluator, evalFunc );
      assertLexingOkEvalFailed( "not", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testMQ_STRING() throws RecognitionException
   {
      RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "\"String\"" ) );
      lexer.mQ_STRING();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "'String'" ) );
      lexer.mQ_STRING();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "'String with spaces and (brackets)   '" ) );
      lexer.mQ_STRING();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "'String with 'quotes''" ) );
      lexer.mQ_STRING();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "'String with \"quotes\"'" ) );
      lexer.mQ_STRING();
   }
   
   
   
   @Test( expected = RecognitionException.class )
   public void testMQ_STRING_QuoteBegin() throws RecognitionException
   {
      RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "\"String" ) );
      lexer.mQ_STRING();
   }
   
   
   
   @Test( expected = RecognitionException.class )
   public void testMQ_STRING_QuoteBegin1() throws RecognitionException
   {
      RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "\'String" ) );
      lexer.mQ_STRING();
   }
   
   
   
   @Test( expected = RecognitionException.class )
   public void testMQ_STRING_QuoteEnd() throws RecognitionException
   {
      RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "String\"" ) );
      lexer.mQ_STRING();
   }
   
   
   
   @Test( expected = RecognitionException.class )
   public void testMQ_STRING_QuoteEnd1() throws RecognitionException
   {
      RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "String\'" ) );
      lexer.mQ_STRING();
   }
   
   
   
   @Test
   public void testMSTRING() throws RecognitionException
   {
      RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "String" ) );
      lexer.mSTRING();
   }
   
   
   
   @Test( expected = RecognitionException.class )
   public void testMSTRING_Empty() throws RecognitionException
   {
      RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "" ) );
      lexer.mSTRING();
   }
   
   
   
   @Test
   public void testMNUMER() throws RecognitionException
   {
      RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "0" ) );
      lexer.mNUMBER();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "1" ) );
      lexer.mNUMBER();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "2" ) );
      lexer.mNUMBER();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "3" ) );
      lexer.mNUMBER();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "4" ) );
      lexer.mNUMBER();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "5" ) );
      lexer.mNUMBER();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "6" ) );
      lexer.mNUMBER();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "7" ) );
      lexer.mNUMBER();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "8" ) );
      lexer.mNUMBER();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "9" ) );
      lexer.mNUMBER();
      
      lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "0" ) );
      lexer.mNUMBER();
   }
   
   
   
   @Test( expected = RecognitionException.class )
   public void testMNUMER_Error() throws RecognitionException
   {
      final RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( "a" ) );
      lexer.mNUMBER();
   }
   
   
   
   /**
    * https://code.google.com/p/moloko/issues/detail?id=80
    * 
    * @throws GrammarException
    */
   @Test
   public void testSleeperTags() throws GrammarException
   {
      final String sleeperTagsFilter = "(tag:zzz AND dueAfter:now)"
         + " OR (tag:z1d AND dueAfter:\"1 day of now\")"
         + " OR (tag:z2d AND dueAfter:\"2 days of now\")"
         + " OR (tag:z1w AND dueAfter:\"1 week of now\")"
         + " OR (tag:z1m AND dueAfter:\"1 month of now\")"
         + " OR (tag:z2w AND dueAfter:\"2 weeks of now\")";
      
      final IRtmSmartFilterEvaluator sleeperTagsEvaluator = EasyMock.createStrictMock( IRtmSmartFilterEvaluator.class );
      // (tag:zzz AND dueAfter:now)
      EasyMock.expect( sleeperTagsEvaluator.evalLeftParenthesis() )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalTag( "zzz" ) ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalAnd() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalDueAfter( "now" ) )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalRightParenthesis() )
              .andReturn( true );
      
      // OR (tag:z1d AND dueAfter:"1 day of now")
      EasyMock.expect( sleeperTagsEvaluator.evalOr() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalLeftParenthesis() )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalTag( "z1d" ) ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalAnd() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalDueAfter( "1 day of now" ) )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalRightParenthesis() )
              .andReturn( true );
      
      // OR (tag:z2d AND dueAfter:"2 days of now")
      EasyMock.expect( sleeperTagsEvaluator.evalOr() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalLeftParenthesis() )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalTag( "z2d" ) ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalAnd() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalDueAfter( "2 days of now" ) )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalRightParenthesis() )
              .andReturn( true );
      
      // OR (tag:z1w AND dueAfter:"1 week of now")
      EasyMock.expect( sleeperTagsEvaluator.evalOr() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalLeftParenthesis() )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalTag( "z1w" ) ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalAnd() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalDueAfter( "1 week of now" ) )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalRightParenthesis() )
              .andReturn( true );
      
      // OR (tag:z1m AND dueAfter:"1 month of now")
      EasyMock.expect( sleeperTagsEvaluator.evalOr() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalLeftParenthesis() )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalTag( "z1m" ) ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalAnd() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalDueAfter( "1 month of now" ) )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalRightParenthesis() )
              .andReturn( true );
      
      // OR (tag:z2w AND dueAfter:"2 weeks of now")
      EasyMock.expect( sleeperTagsEvaluator.evalOr() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalLeftParenthesis() )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalTag( "z2w" ) ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalAnd() ).andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalDueAfter( "2 weeks of now" ) )
              .andReturn( true );
      EasyMock.expect( sleeperTagsEvaluator.evalRightParenthesis() )
              .andReturn( true );
      
      EasyMock.replay( sleeperTagsEvaluator );
      
      final RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( sleeperTagsFilter ) );
      lexer.setEvaluator( sleeperTagsEvaluator );
      lexer.startEvaluation();
      
      EasyMock.verify( sleeperTagsEvaluator );
   }
   
   
   
   private void assertLexingOkEvalOk( String smartFilter,
                                      IRtmSmartFilterEvaluator evaluator,
                                      Lambda.Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc ) throws GrammarException
   {
      if ( evaluator != null )
      {
         EasyMock.reset( evaluator );
         evalFunc.call( evaluator ).andReturn( true );
         EasyMock.replay( evaluator );
      }
      
      RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( smartFilter ) );
      lexer.setEvaluator( evaluator );
      lexer.startEvaluation();
      
      if ( evaluator != null )
      {
         EasyMock.verify( evaluator );
      }
   }
   
   
   
   private void assertLexingFailed( String smartFilter,
                                    IRtmSmartFilterEvaluator evaluator )
   {
      RtmSmartFilterLexer lexer;
      try
      {
         if ( evaluator != null )
         {
            EasyMock.reset( evaluator );
            EasyMock.replay( evaluator );
         }
         
         lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( smartFilter ) );
         lexer.setEvaluator( evaluator );
         lexer.startEvaluation();
         THROWN.expect( GrammarException.class );
      }
      catch ( GrammarException e )
      {
      }
      finally
      {
         if ( evaluator != null )
         {
            EasyMock.verify( evaluator );
         }
      }
   }
   
   
   
   private void assertLexingOkEvalFailed( String smartFilter,
                                          IRtmSmartFilterEvaluator evaluator,
                                          Lambda.Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc )
   {
      RtmSmartFilterLexer lexer;
      assumeThat( evaluator, notNullValue() );
      
      try
      {
         EasyMock.reset( evaluator );
         evalFunc.call( evaluator ).andReturn( false );
         EasyMock.replay( evaluator );
         
         lexer = new RtmSmartFilterLexer( new ANTLRNoCaseStringStream( smartFilter ) );
         lexer.setEvaluator( evaluator );
         
         lexer.startEvaluation();
         THROWN.expect( GrammarException.class );
      }
      catch ( GrammarException e )
      {
      }
      finally
      {
         EasyMock.verify( evaluator );
      }
   }
}
