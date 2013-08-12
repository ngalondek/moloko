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

package dev.drsoran.moloko.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.IDateFormatter;
import dev.drsoran.moloko.domain.parsing.MolokoCalenderProvider;
import dev.drsoran.moloko.domain.parsing.datetime.DateEvaluator;
import dev.drsoran.moloko.domain.parsing.datetime.ParseDateWithinReturn;
import dev.drsoran.moloko.domain.parsing.datetime.ParseReturn;
import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser;


public abstract class MolokoDateParserTestCase extends MolokoTestCase
{
   public MolokoCalendar testParseDate( String dateToParse )
   {
      return testParseDate( dateToParse, false );
   }
   
   
   
   public MolokoCalendar testParseDate( String dateToParse,
                                        boolean expectHasTime )
   {
      final ParseReturn ret = testParseDate( dateToParse, true, expectHasTime );
      return ret.cal;
   }
   
   
   
   public ParseReturn testParseDate( String dateToParse,
                                     boolean clearTime,
                                     boolean expectHasTime )
   {
      try
      {
         final IDateFormatter dateFormatter = TestDateFormatter.get();
         
         final DateParser dateParser = createDareParser( dateToParse );
         dateParser.getInterpreter().setPredictionMode( PredictionMode.SLL );
         
         final ParseTree tree = dateParser.parseDate();
         
         final MolokoCalenderProvider calenderProvider = getCalendarProvider();
         final DateEvaluator evaluator = new DateEvaluator( getDateLanguage(),
                                                            dateFormatter,
                                                            calenderProvider );
         evaluator.setClearTime( clearTime );
         evaluator.visit( tree );
         
         final Token lastToken = dateParser.getTokenStream().LT( 1 );
         final boolean isEof = lastToken.getType() == Token.EOF;
         final int pos = lastToken.getCharPositionInLine();
         final MolokoCalendar cal = evaluator.getCalendar();
         final ParseReturn ret = new ParseReturn( pos, isEof, cal );
         
         verifyParseResult( dateToParse, ret, expectHasTime );
         
         return ret;
      }
      catch ( ParseCancellationException e )
      {
         fail( "Parsing <" + dateToParse + "> failed" );
         throw e;
      }
   }
   
   
   
   public ParseDateWithinReturn testParseDateWithin( String dateToParse )
   {
      try
      {
         final IDateFormatter dateFormatter = TestDateFormatter.get();
         
         final DateParser dateParser = createDareParser( dateToParse );
         final ParseTree tree = dateParser.parseDateWithin();
         
         final MolokoCalenderProvider calenderProvider = getCalendarProvider();
         final DateEvaluator evaluator = new DateEvaluator( getDateLanguage(),
                                                            dateFormatter,
                                                            calenderProvider );
         evaluator.visit( tree );
         
         final ParseDateWithinReturn ret = new ParseDateWithinReturn( evaluator.getEpochStart(),
                                                                      evaluator.getEpochEnd() );
         
         return ret;
      }
      catch ( ParseCancellationException e )
      {
         fail( "Parsing <" + dateToParse + "> failed" );
         throw e;
      }
   }
   
   
   
   public void verifyParseResult( String toParse,
                                  ParseReturn ret,
                                  boolean expectHasTime )
   {
      assertThat( "Unexpected calendar hasTime for <" + toParse + ">",
                  ret.cal.hasTime(),
                  is( expectHasTime ) );
      
      assertThat( "Not EOF for <" + toParse + ">", ret.isEof, is( true ) );
      assertThat( "Unexpected char count parsed for <" + toParse + ">",
                  ret.numParsedChars,
                  is( toParse.length() ) );
   }
   
   
   
   protected abstract Lexer createDateLexer( ANTLRInputStream stream );
   
   
   
   protected abstract ILanguage getDateLanguage();
   
   
   
   protected abstract MolokoCalenderProvider getCalendarProvider();
   
   
   
   private DateParser createDareParser( String dateToParse )
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( dateToParse );
      final Lexer dateLexer = createDateLexer( stream );
      
      final TokenStream tokenStream = new CommonTokenStream( dateLexer );
      final DateParser dateParser = new DateParser( tokenStream );
      dateParser.setErrorHandler( new BailErrorStrategy() );
      
      return dateParser;
   }
}
