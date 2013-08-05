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
import static org.junit.Assert.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.datetime.ParseReturn;
import dev.drsoran.moloko.domain.parsing.datetime.TimeEstimateEvaluator;
import dev.drsoran.moloko.domain.parsing.datetime.TimeEvaluator;
import dev.drsoran.moloko.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParserVisitor;


public abstract class MolokoTimeParserTestCase extends MolokoTestCase
{
   public MolokoCalendar parseTime( String timeToParse, boolean adjustDay )
   {
      return parseTime( timeToParse, adjustDay, false );
   }
   
   
   
   public MolokoCalendar parseTime( String timeToParse,
                                    boolean adjustDay,
                                    boolean expectHasTime )
   {
      try
      {
         final TimeEvaluator evaluator = new TimeEvaluator();
         TimeParser timeParser = createTimeParser( timeToParse, evaluator );
         
         final ParseTree tree = timeParser.parseTime();
         evaluator.visit( tree );
         
         final Token lastToken = timeParser.getTokenStream().LT( 1 );
         final boolean isEof = lastToken.getType() == Token.EOF;
         final int pos = lastToken.getCharPositionInLine();
         final MolokoCalendar cal = evaluator.getCalendar();
         
         final ParseReturn ret = new ParseReturn( pos, isEof, cal );
         verifyParseResult( timeToParse, ret, expectHasTime );
         
         return cal;
      }
      catch ( ParseCancellationException e )
      {
         fail( "Parsing <" + timeToParse + "> failed" );
         throw e;
      }
   }
   
   
   
   public void parseTimeEstimate( String estimation, long expectedMillis )
   {
      try
      {
         final TimeEstimateEvaluator evaluator = new TimeEstimateEvaluator();
         TimeParser timeParser = createTimeParser( estimation, evaluator );
         
         final ParseTree tree = timeParser.parseTimeEstimate();
         evaluator.visit( tree );
         
         final long estimateMillis = evaluator.getEstimateMillis();
         
         assertThat( "Estimation is wrong for <" + estimation + ">",
                     estimateMillis,
                     is( expectedMillis ) );
      }
      catch ( ParseCancellationException e )
      {
         fail( "Parsing <" + estimation + "> failed" );
      }
   }
   
   
   
   public void verifyParseResult( String toParse,
                                  ParseReturn ret,
                                  boolean expectHasTime )
   {
      assertThat( "Calendar has no time for <" + toParse + ">",
                  ret.cal.hasTime(),
                  is( expectHasTime ) );
      
      assertThat( "Not EOF for <" + toParse + ">", ret.isEof, is( true ) );
      assertThat( "Unexpected char count parsed for <" + toParse + ">",
                  ret.numParsedChars,
                  is( toParse.length() ) );
   }
   
   
   
   protected abstract Lexer createTimeLexer( ANTLRInputStream inputStream );
   
   
   
   private TimeParser createTimeParser( String timeToParse,
                                        TimeParserVisitor< Void > evaluator )
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( timeToParse );
      final Lexer lexer = createTimeLexer( stream );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeParser parser = new TimeParser( antlrTokens );
      
      parser.setErrorHandler( new BailErrorStrategy() );
      
      return parser;
   }
}
