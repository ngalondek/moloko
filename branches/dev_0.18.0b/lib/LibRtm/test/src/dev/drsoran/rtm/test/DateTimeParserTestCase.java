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

package dev.drsoran.rtm.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.datetime.DefaultDateTimeEvaluator;
import dev.drsoran.rtm.parsing.datetime.ParseDateWithinReturn;
import dev.drsoran.rtm.parsing.datetime.TimeEstimateEvaluator;
import dev.drsoran.rtm.parsing.grammar.ANTLRBailOutErrorListener;
import dev.drsoran.rtm.parsing.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser;
import dev.drsoran.rtm.parsing.lang.ILanguage;


public abstract class DateTimeParserTestCase
{
   public RtmCalendar testParseDate( String dateToParse )
   {
      return testParseDate( dateToParse, false );
   }
   
   
   
   public RtmCalendar testParseDate( String dateToParse, boolean expectHasTime )
   {
      try
      {
         final DateTimeParser dateTimeParser = createDateTimeParser( dateToParse );
         
         final ParseTree tree = dateTimeParser.parseDate();
         final DefaultDateTimeEvaluator evaluator = createEvaluator();
         evaluator.visit( tree );
         
         final RtmCalendar cal = evaluator.getCalendar();
         verifyParseResult( dateToParse, cal, expectHasTime );
         
         return cal;
      }
      catch ( ParseCancellationException e )
      {
         fail( "Parsing <" + dateToParse + "> failed: " + e.getMessage() );
         throw e;
      }
   }
   
   
   
   public ParseDateWithinReturn testParseDateWithin( String dateToParse )
   {
      try
      {
         final DateTimeParser dateTimeParser = createDateTimeParser( dateToParse );
         final ParseTree tree = dateTimeParser.parseDateWithin();
         
         final DefaultDateTimeEvaluator evaluator = createEvaluator();
         evaluator.visit( tree );
         
         final ParseDateWithinReturn ret = new ParseDateWithinReturn( evaluator.getEpochStart(),
                                                                      evaluator.getEpochEnd() );
         
         return ret;
      }
      catch ( ParseCancellationException e )
      {
         fail( "Parsing <" + dateToParse + "> failed: " + e.getMessage() );
         throw e;
      }
   }
   
   
   
   public RtmCalendar parseTime( String timeToParse, boolean adjustDay )
   {
      return parseTime( timeToParse, adjustDay, false );
   }
   
   
   
   public RtmCalendar parseTime( String timeToParse,
                                 boolean adjustDay,
                                 boolean expectHasTime )
   {
      try
      {
         final DateTimeParser dateTimeParser = createDateTimeParser( timeToParse );
         final ParseTree tree = dateTimeParser.parseTime();
         
         final DefaultDateTimeEvaluator evaluator = createEvaluator();
         evaluator.visit( tree );
         
         final RtmCalendar cal = evaluator.getCalendar();
         
         verifyParseResult( timeToParse, cal, expectHasTime );
         
         return cal;
      }
      catch ( ParseCancellationException e )
      {
         fail( "Parsing <" + timeToParse + "> failed: " + e.getMessage() );
         throw e;
      }
   }
   
   
   
   public void parseTimeEstimate( String estimation, long expectedMillis )
   {
      try
      {
         final DateTimeParser dateTimeParser = createDateTimeParser( estimation );
         final ParseTree tree = dateTimeParser.parseTimeEstimate();
         
         final TimeEstimateEvaluator evaluator = new TimeEstimateEvaluator();
         evaluator.visit( tree );
         
         final long estimateMillis = evaluator.getEstimateMillis();
         
         assertThat( "Estimation is wrong for <" + estimation + ">",
                     estimateMillis,
                     is( expectedMillis ) );
      }
      catch ( ParseCancellationException e )
      {
         fail( "Parsing <" + estimation + "> failed: " + e.getMessage() );
      }
   }
   
   
   
   public void verifyParseResult( String toParse,
                                  RtmCalendar cal,
                                  boolean expectHasTime )
   {
      assertThat( "Calendar has no time for <" + toParse + ">",
                  cal.hasTime(),
                  is( expectHasTime ) );
   }
   
   
   
   protected abstract Lexer createDateTimeLexer( ANTLRInputStream inputStream );
   
   
   
   protected abstract ILanguage getDateLanguage();
   
   
   
   private DefaultDateTimeEvaluator createEvaluator()
   {
      return new DefaultDateTimeEvaluator( getDateLanguage(),
                                           TestDateFormatter.get(),
                                           TestCalendarProvider.getJune_10_2010_00_00_00() );
   }
   
   
   
   private DateTimeParser createDateTimeParser( String toParse )
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( toParse );
      final Lexer lexer = createDateTimeLexer( stream );
      lexer.addErrorListener( new ANTLRBailOutErrorListener() );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final DateTimeParser parser = new DateTimeParser( antlrTokens );
      parser.setErrorHandler( new BailErrorStrategy() );
      
      return parser;
   }
}
