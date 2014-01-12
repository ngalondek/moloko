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

package dev.drsoran.test.comp.rtm.parsing.grammar.antlr.rtmsmart;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assume.assumeThat;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.easymock.EasyMock;
import org.easymock.IExpectationSetters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.util.Lambda;
import dev.drsoran.moloko.util.Lambda.Func1;
import dev.drsoran.rtm.model.Priority;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.grammar.ANTLRBailOutErrorListener;
import dev.drsoran.rtm.parsing.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterLexer;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterParser;
import dev.drsoran.rtm.parsing.grammar.antlr.rtmsmart.RtmSmartFilterVisitor;
import dev.drsoran.rtm.parsing.rtmsmart.IRtmSmartFilterEvaluator;
import dev.drsoran.rtm.parsing.rtmsmart.RtmSmartFilterVisitorAdapter;


@RunWith( Theories.class )
public class RtmSmartFilterParserTest extends MolokoTestCase
{
   private IRtmSmartFilterEvaluator evaluator;
   
   @Rule
   public ExpectedException THROWN = ExpectedException.none();
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      evaluator = EasyMock.createStrictMock( IRtmSmartFilterEvaluator.class );
   }
   
   
   
   @Test
   public void testOP_LIST() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalList( "List" ) );
         }
      };
      
      assertParsingOkEvalOk( "list:List", evaluator, evalFunc );
      assertParsingOkEvalOk( "list:\"List\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "list:'List'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "list:\"List with Spaces\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalList( "List with Spaces" ) );
                                }
                             } );
      
      assertParsingFailed( "list:", evaluator );
      
      assertParsingOkEvalFailed( "list:List", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_PRIORITY() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalPriority( Priority.High.toString() ) );
         }
      };
      
      assertParsingOkEvalOk( "priority:1", evaluator, evalFunc );
      assertParsingOkEvalOk( "priority:2",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalPriority( Priority.Medium.toString() ) );
                                }
                             } );
      assertParsingOkEvalOk( "priority:3",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalPriority( Priority.Low.toString() ) );
                                }
                             } );
      assertParsingOkEvalOk( "priority:n",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalPriority( Priority.None.toString() ) );
                                }
                             } );
      assertParsingOkEvalOk( "priority:N",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalPriority( "N" ) );
                                }
                             } );
      
      assertParsingFailed( "priority:", evaluator );
      
      assertParsingOkEvalFailed( "priority:1", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_STATUS() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalStatus( true ) );
         }
      };
      
      assertParsingOkEvalOk( "status:completed", evaluator, evalFunc );
      assertParsingOkEvalOk( "status:incomplete",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalStatus( false ) );
                                }
                             } );
      
      assertParsingFailed( "status:", evaluator );
      assertParsingFailed( "status:compl", evaluator );
      
      assertParsingOkEvalFailed( "status:completed", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_TAG() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalTag( "atag" ) );
         }
      };
      
      assertParsingOkEvalOk( "tag:atag", evaluator, evalFunc );
      assertParsingOkEvalOk( "tag:'atag'", evaluator, evalFunc );
      assertParsingOkEvalOk( "tag:\"atag\"", evaluator, evalFunc );
      
      assertParsingFailed( "tag:", evaluator );
      
      assertParsingOkEvalFailed( "tag:atag", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_TAG_CONTAINS() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalTagContains( "atag" ) );
         }
      };
      
      assertParsingOkEvalOk( "tagcontains:atag", evaluator, evalFunc );
      assertParsingOkEvalOk( "tagcontains:'atag'", evaluator, evalFunc );
      assertParsingOkEvalOk( "tagcontains:\"atag\"", evaluator, evalFunc );
      
      assertParsingFailed( "tagcontains:", evaluator );
      
      assertParsingOkEvalFailed( "tagcontains:atag", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_IS_TAGGED() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalIsTagged( true ) );
         }
      };
      
      assertParsingOkEvalOk( "istagged:true", evaluator, evalFunc );
      assertParsingOkEvalOk( "istagged:false",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalIsTagged( false ) );
                                }
                             } );
      
      assertParsingFailed( "istagged:", evaluator );
      assertParsingFailed( "istagged:!true", evaluator );
      
      assertParsingOkEvalFailed( "istagged:true", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_LOCATION() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalLocation( "@Home" ) );
         }
      };
      
      assertParsingOkEvalOk( "location:@Home", evaluator, evalFunc );
      assertParsingOkEvalOk( "location:\"@Home\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "location:'@Home'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "location:\"Location @Space\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalLocation( "Location @Space" ) );
                                }
                             } );
      
      assertParsingFailed( "location:", evaluator );
      
      assertParsingOkEvalFailed( "location:@Home", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_ISLOCATED() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalIsLocated( true ) );
         }
      };
      
      assertParsingOkEvalOk( "islocated:true", evaluator, evalFunc );
      assertParsingOkEvalOk( "islocated:false",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalIsLocated( false ) );
                                }
                             } );
      
      assertParsingFailed( "islocated:", evaluator );
      assertParsingFailed( "islocated:!true", evaluator );
      
      assertParsingOkEvalFailed( "islocated:true", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_IS_REPEATING() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalIsRepeating( true ) );
         }
      };
      
      assertParsingOkEvalOk( "isrepeating:true", evaluator, evalFunc );
      assertParsingOkEvalOk( "isrepeating:false",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalIsRepeating( false ) );
                                }
                             } );
      
      assertParsingFailed( "isrepeating:", evaluator );
      assertParsingFailed( "isrepeating:!true", evaluator );
      
      assertParsingOkEvalFailed( "isrepeating:true", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_NAME() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalTaskName( "Task" ) );
         }
      };
      
      assertParsingOkEvalOk( "name:Task", evaluator, evalFunc );
      assertParsingOkEvalOk( "name:\"Task\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "name:'Task'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "name:\"Task with Spaces\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalTaskName( "Task with Spaces" ) );
                                }
                             } );
      
      assertParsingFailed( "name:", evaluator );
      
      assertParsingOkEvalFailed( "name:Task", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_NOTE_CONTAINS() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalNoteContains( "content" ) );
         }
      };
      
      assertParsingOkEvalOk( "notecontains:content", evaluator, evalFunc );
      assertParsingOkEvalOk( "notecontains:\"content\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "notecontains:'content'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "notecontains:\"content with spaces\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalNoteContains( "content with spaces" ) );
                                }
                             } );
      
      assertParsingFailed( "notecontains:", evaluator );
      
      assertParsingOkEvalFailed( "notecontains:content", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_HAS_NOTES() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalHasNotes( true ) );
         }
      };
      
      assertParsingOkEvalOk( "hasnotes:true", evaluator, evalFunc );
      assertParsingOkEvalOk( "hasnotes:false",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalHasNotes( false ) );
                                }
                             } );
      
      assertParsingFailed( "hasnotes:", evaluator );
      assertParsingFailed( "hasnotes:!true", evaluator );
      
      assertParsingOkEvalFailed( "hasnotes:true", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_DUE() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalDue( "Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "due:Today", evaluator, evalFunc );
      assertParsingOkEvalOk( "due:\"Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "due:'Today'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "due:\"Tomorrow at 6\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalDue( "Tomorrow at 6" ) );
                                }
                             } );
      
      assertParsingFailed( "due:", evaluator );
      
      assertParsingOkEvalFailed( "due:Today", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_DUE_AFTER() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalDueAfter( "Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "dueafter:Today", evaluator, evalFunc );
      assertParsingOkEvalOk( "dueafter:\"Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "dueafter:'Today'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "dueafter:\"Tomorrow at 6\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalDueAfter( "Tomorrow at 6" ) );
                                }
                             } );
      
      assertParsingFailed( "dueafter:", evaluator );
      
      assertParsingOkEvalFailed( "dueafter:Today", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_DUE_BEFORE() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalDueBefore( "Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "duebefore:Today", evaluator, evalFunc );
      assertParsingOkEvalOk( "duebefore:\"Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "duebefore:'Today'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "duebefore:\"Tomorrow at 6\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalDueBefore( "Tomorrow at 6" ) );
                                }
                             } );
      
      assertParsingFailed( "duebefore:", evaluator );
      
      assertParsingOkEvalFailed( "duebefore:Today", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_DUE_WITHIN() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalDueWithIn( "2 of Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "duewithin:\"2 of Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "duewithin:'2 of Today'", evaluator, evalFunc );
      
      assertParsingFailed( "duewithin:", evaluator );
      
      assertParsingOkEvalFailed( "duewithin:'2 of Today'", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_COMPLETED() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalCompleted( "Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "completed:Today", evaluator, evalFunc );
      assertParsingOkEvalOk( "completed:\"Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "completed:'Today'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "completed:\"Tomorrow at 6\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalCompleted( "Tomorrow at 6" ) );
                                }
                             } );
      
      assertParsingFailed( "completed:", evaluator );
      
      assertParsingOkEvalFailed( "completed:Today", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_COMPLETED_AFTER() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalCompletedAfter( "Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "completedafter:Today", evaluator, evalFunc );
      assertParsingOkEvalOk( "completedafter:\"Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "completedafter:'Today'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "completedafter:\"Tomorrow at 6\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalCompletedAfter( "Tomorrow at 6" ) );
                                }
                             } );
      
      assertParsingFailed( "completedafter:", evaluator );
      
      assertParsingOkEvalFailed( "completedafter:Today", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_COMPLETED_BEFORE() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalCompletedBefore( "Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "completedbefore:Today", evaluator, evalFunc );
      assertParsingOkEvalOk( "completedbefore:\"Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "completedbefore:'Today'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "completedbefore:\"Tomorrow at 6\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalCompletedBefore( "Tomorrow at 6" ) );
                                }
                             } );
      
      assertParsingFailed( "completedbefore:", evaluator );
      
      assertParsingOkEvalFailed( "completedbefore:Today", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_COMPLETED_WITHIN() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalCompletedWithIn( "2 of Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "completedwithin:\"2 of Today\"",
                             evaluator,
                             evalFunc );
      assertParsingOkEvalOk( "completedwithin:'2 of Today'",
                             evaluator,
                             evalFunc );
      
      assertParsingFailed( "completedwithin:", evaluator );
      
      assertParsingOkEvalFailed( "completedwithin:'2 of Today'",
                                 evaluator,
                                 evalFunc );
   }
   
   
   
   @Test
   public void testOP_ADDED() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalAdded( "Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "added:Today", evaluator, evalFunc );
      assertParsingOkEvalOk( "added:\"Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "added:'Today'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "added:\"Tomorrow at 6\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalAdded( "Tomorrow at 6" ) );
                                }
                             } );
      
      assertParsingFailed( "added:", evaluator );
      
      assertParsingOkEvalFailed( "added:Today", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_ADDED_AFTER() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalAddedAfter( "Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "addedafter:Today", evaluator, evalFunc );
      assertParsingOkEvalOk( "addedafter:\"Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "addedafter:'Today'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "addedafter:\"Tomorrow at 6\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalAddedAfter( "Tomorrow at 6" ) );
                                }
                             } );
      
      assertParsingFailed( "addedafter:", evaluator );
      
      assertParsingOkEvalFailed( "addedafter:Today", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_ADDED_BEFORE() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalAddedBefore( "Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "addedbefore:Today", evaluator, evalFunc );
      assertParsingOkEvalOk( "addedbefore:\"Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "addedbefore:'Today'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "addedbefore:\"Tomorrow at 6\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalAddedBefore( "Tomorrow at 6" ) );
                                }
                             } );
      
      assertParsingFailed( "addedbefore:", evaluator );
      
      assertParsingOkEvalFailed( "addedbefore:Today", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_ADDED_WITHIN() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalAddedWithIn( "2 of Today" ) );
         }
      };
      
      assertParsingOkEvalOk( "addedwithin:\"2 of Today\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "addedwithin:'2 of Today'", evaluator, evalFunc );
      
      assertParsingFailed( "addedwithin:", evaluator );
      
      assertParsingOkEvalFailed( "addedwithin:'2 of Today'",
                                 evaluator,
                                 evalFunc );
   }
   
   
   
   @Test
   public void testOP_TIME_ESTIMATE() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalTimeEstimate( ">", "1h" ) );
         }
      };
      
      assertParsingOkEvalOk( "timeestimate:\">1h\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "timeestimate:'>1h'", evaluator, evalFunc );
      assertParsingOkEvalOk( "timeestimate:>1h", evaluator, evalFunc );
      assertParsingOkEvalOk( "timeestimate:'<1h'",
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
      
      assertParsingFailed( "timeestimate:", evaluator );
      
      assertParsingOkEvalFailed( "timeestimate:'>1h'", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_POSTPONED() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalPostponed( ">", 1 ) );
         }
      };
      
      assertParsingOkEvalOk( "postponed:\">1\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "postponed:'>1'", evaluator, evalFunc );
      assertParsingOkEvalOk( "postponed:>1", evaluator, evalFunc );
      assertParsingOkEvalOk( "postponed:'<2'",
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
      assertParsingOkEvalOk( "postponed:3",
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
      assertParsingOkEvalOk( "postponed:-1",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalPostponed( "",
                                                                                -1 ) );
                                }
                             } );
      
      assertParsingFailed( "postponed:", evaluator );
      assertParsingFailed( "postponed:abc", evaluator );
      assertParsingFailed( "postponed:>three", evaluator );
      
      assertParsingOkEvalFailed( "postponed:>1", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_IS_SHARED() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalIsShared( true ) );
         }
      };
      
      assertParsingOkEvalOk( "isshared:true", evaluator, evalFunc );
      assertParsingOkEvalOk( "isshared:false",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalIsShared( false ) );
                                }
                             } );
      
      assertParsingFailed( "isshared:", evaluator );
      assertParsingFailed( "isshared:!true", evaluator );
      
      assertParsingOkEvalFailed( "isshared:true", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testOP_SHARED_WITH() throws GrammarException
   {
      Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc = new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
      {
         @Override
         public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
         {
            return EasyMock.expect( param.evalSharedWith( "John" ) );
         }
      };
      
      assertParsingOkEvalOk( "sharedwith:John", evaluator, evalFunc );
      assertParsingOkEvalOk( "sharedwith:\"John\"", evaluator, evalFunc );
      assertParsingOkEvalOk( "sharedwith:'John'", evaluator, evalFunc );
      
      assertParsingOkEvalOk( "sharedwith:\"John Doe\"",
                             evaluator,
                             new Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > >()
                             {
                                @Override
                                public IExpectationSetters< Boolean > call( IRtmSmartFilterEvaluator param )
                                {
                                   return EasyMock.expect( param.evalSharedWith( "John Doe" ) );
                                }
                             } );
      
      assertParsingFailed( "sharedwith:", evaluator );
      
      assertParsingOkEvalFailed( "sharedwith:John", evaluator, evalFunc );
   }
   
   
   
   @Test
   public void testPARENTH() throws GrammarException
   {
      EasyMock.reset( evaluator );
      EasyMock.expect( evaluator.evalLeftParenthesis() ).andReturn( true );
      EasyMock.expect( evaluator.evalList( "List" ) ).andReturn( true );
      EasyMock.expect( evaluator.evalRightParenthesis() ).andReturn( true );
      EasyMock.replay( evaluator );
      
      parseAndEvalRtmSmartFilter( "(list:'List')", evaluator );
      EasyMock.verify( evaluator );
      
      EasyMock.reset( evaluator );
      EasyMock.expect( evaluator.evalLeftParenthesis() ).andReturn( true );
      EasyMock.expect( evaluator.evalList( "List" ) ).andReturn( true );
      EasyMock.expect( evaluator.evalAnd() ).andReturn( true );
      EasyMock.expect( evaluator.evalList( "List" ) ).andReturn( true );
      EasyMock.expect( evaluator.evalRightParenthesis() ).andReturn( true );
      EasyMock.replay( evaluator );
      
      parseAndEvalRtmSmartFilter( "(list:'List' and list:'List')", evaluator );
      EasyMock.verify( evaluator );
      
      EasyMock.reset( evaluator );
      EasyMock.expect( evaluator.evalList( "List" ) ).andReturn( true );
      EasyMock.expect( evaluator.evalOr() ).andReturn( true );
      EasyMock.expect( evaluator.evalLeftParenthesis() ).andReturn( true );
      EasyMock.expect( evaluator.evalNot() ).andReturn( true );
      EasyMock.expect( evaluator.evalLeftParenthesis() ).andReturn( true );
      EasyMock.expect( evaluator.evalList( "List" ) ).andReturn( true );
      EasyMock.expect( evaluator.evalAnd() ).andReturn( true );
      EasyMock.expect( evaluator.evalList( "List" ) ).andReturn( true );
      EasyMock.expect( evaluator.evalRightParenthesis() ).andReturn( true );
      EasyMock.expect( evaluator.evalRightParenthesis() ).andReturn( true );
      EasyMock.replay( evaluator );
      
      parseAndEvalRtmSmartFilter( "list:'List' or (not (list:'List' and list:'List'))",
                                  evaluator );
      EasyMock.verify( evaluator );
      
      assertParsingFailed( "(list:List", evaluator );
   }
   
   
   
   @Test
   public void testAND() throws GrammarException
   {
      EasyMock.reset( evaluator );
      EasyMock.expect( evaluator.evalList( "List" ) ).andReturn( true );
      EasyMock.expect( evaluator.evalAnd() ).andReturn( true );
      EasyMock.expect( evaluator.evalTaskName( "Task" ) ).andReturn( true );
      EasyMock.replay( evaluator );
      
      parseAndEvalRtmSmartFilter( "list:'List' and name:Task", evaluator );
      EasyMock.verify( evaluator );
      
      EasyMock.reset( evaluator );
      EasyMock.expect( evaluator.evalList( "List" ) ).andReturn( true );
      EasyMock.expect( evaluator.evalAnd() ).andReturn( true );
      EasyMock.expect( evaluator.evalTaskName( "Task" ) ).andReturn( true );
      EasyMock.replay( evaluator );
      
      parseAndEvalRtmSmartFilter( "list:'List' name:Task", evaluator );
      EasyMock.verify( evaluator );
      
      assertParsingFailed( "and list:'List 1'", evaluator );
   }
   
   
   
   @Test
   public void testOR() throws GrammarException
   {
      EasyMock.reset( evaluator );
      EasyMock.expect( evaluator.evalList( "List" ) ).andReturn( true );
      EasyMock.expect( evaluator.evalOr() ).andReturn( true );
      EasyMock.expect( evaluator.evalTaskName( "Task" ) ).andReturn( true );
      EasyMock.replay( evaluator );
      
      parseAndEvalRtmSmartFilter( "list:'List' or name:Task", evaluator );
      EasyMock.verify( evaluator );
      
      assertParsingFailed( "or list:'List 1'", evaluator );
   }
   
   
   
   @Test
   public void testNOT() throws GrammarException
   {
      EasyMock.reset( evaluator );
      EasyMock.expect( evaluator.evalNot() ).andReturn( true );
      EasyMock.expect( evaluator.evalList( "List" ) ).andReturn( true );
      EasyMock.replay( evaluator );
      
      parseAndEvalRtmSmartFilter( "not list:'List'", evaluator );
      EasyMock.verify( evaluator );
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
      
      parseAndEvalRtmSmartFilter( sleeperTagsFilter, sleeperTagsEvaluator );
      
      EasyMock.verify( sleeperTagsEvaluator );
   }
   
   
   
   private void assertParsingOkEvalOk( String smartFilter,
                                       IRtmSmartFilterEvaluator evaluator,
                                       Lambda.Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc ) throws GrammarException
   {
      EasyMock.reset( evaluator );
      evalFunc.call( evaluator ).andReturn( true );
      EasyMock.replay( evaluator );
      
      parseAndEvalRtmSmartFilter( smartFilter, evaluator );
   }
   
   
   
   private void assertParsingFailed( String smartFilter,
                                     IRtmSmartFilterEvaluator evaluator )
   {
      try
      {
         EasyMock.reset( evaluator );
         EasyMock.replay( evaluator );
         
         parseAndEvalRtmSmartFilter( smartFilter, evaluator );
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
   
   
   
   private void assertParsingOkEvalFailed( String smartFilter,
                                           IRtmSmartFilterEvaluator evaluator,
                                           Lambda.Func1< IRtmSmartFilterEvaluator, IExpectationSetters< Boolean > > evalFunc )
   {
      assumeThat( evaluator, notNullValue() );
      
      try
      {
         EasyMock.reset( evaluator );
         evalFunc.call( evaluator ).andReturn( false );
         EasyMock.replay( evaluator );
         
         parseAndEvalRtmSmartFilter( smartFilter, evaluator );
         
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
   
   
   
   private RtmSmartFilterParser createRtmSmartFilterParser( String smartFilter )
   {
      final ANTLRInputStream input = new ANTLRNoCaseStringStream( smartFilter );
      final Lexer lexer = new RtmSmartFilterLexer( input );
      lexer.addErrorListener( new ANTLRBailOutErrorListener() );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final RtmSmartFilterParser parser = new RtmSmartFilterParser( antlrTokens );
      parser.getInterpreter().setPredictionMode( PredictionMode.SLL );
      
      parser.setErrorHandler( new BailErrorStrategy() );
      
      return parser;
   }
   
   
   
   private void parseAndEvalRtmSmartFilter( String smartFilter,
                                            IRtmSmartFilterEvaluator evaluator ) throws GrammarException
   {
      final RtmSmartFilterParser parser = createRtmSmartFilterParser( smartFilter );
      
      try
      {
         final ParseTree tree = parser.parseFilter();
         final RtmSmartFilterVisitor< Void > visitor = new RtmSmartFilterVisitorAdapter( evaluator );
         
         visitor.visit( tree );
      }
      catch ( ParseCancellationException e )
      {
         throw new GrammarException( e );
      }
   }
}
