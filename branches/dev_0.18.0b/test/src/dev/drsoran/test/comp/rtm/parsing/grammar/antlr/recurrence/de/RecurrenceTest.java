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

package dev.drsoran.test.comp.rtm.parsing.grammar.antlr.recurrence.de;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dev.drsoran.moloko.test.MolokoRoboTestCase;
import dev.drsoran.moloko.test.TestDateFormatter;
import dev.drsoran.moloko.test.TestDateTimeParsing;
import dev.drsoran.moloko.test.langs.IRecurrenceParserTestLanguage;
import dev.drsoran.moloko.test.langs.RecurrenceParserTestLanguageDe;
import dev.drsoran.moloko.test.sources.RecurrenceTestDataSource;
import dev.drsoran.rtm.parsing.grammar.ANTLRBailOutErrorListener;
import dev.drsoran.rtm.parsing.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrenceParser;
import dev.drsoran.rtm.parsing.grammar.antlr.recurrence.de.RecurrenceLexer;
import dev.drsoran.rtm.parsing.lang.de.DateLanguage;
import dev.drsoran.rtm.parsing.recurrence.RecurrenceEvaluator;


@RunWith( Parameterized.class )
public class RecurrenceTest extends MolokoRoboTestCase
{
   private final static IRecurrenceParserTestLanguage LANGUAGE = new RecurrenceParserTestLanguageDe();
   
   private final RecurrenceTestDataSource.TestData testData;
   
   
   
   public RecurrenceTest( RecurrenceTestDataSource.TestData testData )
   {
      this.testData = testData;
   }
   
   
   
   @Parameters( name = "{0}" )
   public static Collection< Object[] > getTestData()
   {
      return new RecurrenceTestDataSource( LANGUAGE, TestDateFormatter.get() ).getTestData();
   }
   
   
   
   @Test
   public void testRecurrenceSentences() throws Exception
   {
      final CharStream stream = new ANTLRNoCaseStringStream( testData.sentence );
      final Lexer lexer = new RecurrenceLexer( stream );
      lexer.addErrorListener( new ANTLRBailOutErrorListener() );
      
      final TokenStream tokenStream = new CommonTokenStream( lexer );
      final RecurrenceParser parser = new RecurrenceParser( tokenStream );
      parser.getInterpreter().setPredictionMode( PredictionMode.SLL );
      
      try
      {
         parser.setErrorHandler( new BailErrorStrategy() );
         final ParseTree tree = parser.parseRecurrenceSentence();
         
         final RecurrenceEvaluator evaluator = new RecurrenceEvaluator( TestDateTimeParsing.get( RecurrenceTestDataSource.UNTIL_DATE ),
                                                                        new DateLanguage() );
         evaluator.visit( tree );
         
         final Map< String, Object > pattern = evaluator.getRecurrencePattern();
         
         assertThat( "Pattern size mismatch",
                     pattern.size(),
                     is( testData.expectedPattern.size() ) );
         assertSamePattern( pattern, testData.expectedPattern );
      }
      catch ( ParseCancellationException e )
      {
         fail( "Parsing <" + testData.sentence + "> failed: " + e.getMessage() );
      }
   }
   
   
   
   private void assertSamePattern( Map< String, Object > pattern,
                                   Map< String, Object > expectedPattern )
   {
      final Iterator< String > patternIter = pattern.keySet().iterator();
      final Iterator< String > expectedIter = expectedPattern.keySet()
                                                             .iterator();
      
      while ( patternIter.hasNext() )
      {
         final String operator = patternIter.next();
         final String expectedOperator = expectedIter.next();
         
         assertThat( "Unexpected operator <" + operator + ">",
                     operator,
                     equalTo( expectedOperator ) );
         
         final Object values = pattern.get( operator );
         final Object expectedValues = expectedPattern.get( operator );
         
         assertThat( "Unexpected value for <" + operator + ">",
                     values,
                     equalTo( expectedValues ) );
      }
   }
}
