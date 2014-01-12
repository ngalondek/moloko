/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm.parsing.recurrence;

import static dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternLexer.COMMA;
import static dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternLexer.INT;
import static dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternLexer.OP_BYDAY;
import static dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternLexer.OP_BYMONTH;
import static dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternLexer.OP_BYMONTHDAY;
import static dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternLexer.OP_COUNT;
import static dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternLexer.OP_FREQ;
import static dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternLexer.OP_INTERVAL;
import static dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternLexer.OP_UNTIL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;


public class RecurrencePatternCollector
{
   public static Map< Integer, List< Object > > collectTokens( String recurrencePattern ) throws ParseCancellationException
   {
      final Map< Integer, List< Object > > collectedTokens = new LinkedHashMap< Integer, List< Object > >();
      
      final TokenStream tokenStream = getTokenStream( recurrencePattern );
      
      int tokenType;
      do
      {
         final Token token = nextToken( tokenStream );
         tokenType = token.getType();
         
         switch ( tokenType )
         {
            case OP_FREQ:
               addElement( collectedTokens,
                           tokenType,
                           assertNextToken( tokenStream ).getType() );
               break;
            
            case OP_INTERVAL:
            case OP_BYMONTH:
            case OP_COUNT:
               addIntegerValueElement( collectedTokens, tokenType, tokenStream );
               break;
            
            case OP_BYMONTHDAY:
               addIntegerValueElement( collectedTokens, tokenType, tokenStream );
               
               while ( lookAheadToken( tokenStream ).getType() == COMMA )
               {
                  tokenStream.consume();
                  addIntegerValueElement( collectedTokens,
                                          tokenType,
                                          tokenStream );
               }
               
               break;
            
            case OP_BYDAY:
               addOpByDay( collectedTokens, tokenStream );
               
               while ( lookAheadToken( tokenStream ).getType() == COMMA )
               {
                  tokenStream.consume();
                  addOpByDay( collectedTokens, tokenStream );
               }
               
               break;
            
            case OP_UNTIL:
               addOpUntil( collectedTokens, tokenStream );
               break;
            
            default :
               break;
         }
      }
      while ( tokenType != Token.EOF );
      
      return collectedTokens;
   }
   
   
   
   private static void addOpByDay( Map< Integer, List< Object >> collectedTokens,
                                   TokenStream tokenStream )
   {
      Integer qualifier = null;
      
      final Token lookAheadToken = lookAheadToken( tokenStream );
      if ( lookAheadToken.getType() == INT )
      {
         qualifier = asInteger( lookAheadToken );
         tokenStream.consume();
      }
      
      final Token weekdayToken = assertNextToken( tokenStream );
      addElement( collectedTokens,
                  OP_BYDAY,
                  new OperandByDayValue( qualifier, weekdayToken.getType() ) );
   }
   
   
   
   private static void addOpUntil( Map< Integer, List< Object >> collectedTokens,
                                   TokenStream tokenStream ) throws ParseCancellationException
   {
      final Token dateToken = assertNextToken( tokenStream );
      
      try
      {
         final Date date = new SimpleDateFormat( "yyyyMMdd'T'HHmmss" ).parse( dateToken.getText() );
         addElement( collectedTokens, OP_UNTIL, date );
      }
      catch ( ParseException e )
      {
         throw new ParseCancellationException( e );
      }
   }
   
   
   
   private static Integer asInteger( Token token ) throws ParseCancellationException
   {
      try
      {
         return Integer.parseInt( token.getText() );
      }
      catch ( NumberFormatException e )
      {
         throw new ParseCancellationException( e );
      }
   }
   
   
   
   private static Token assertNextToken( TokenStream tokenStream ) throws ParseCancellationException
   {
      final Token token = nextToken( tokenStream );
      
      if ( token == null || token.getType() == Token.EOF )
      {
         throw new ParseCancellationException( "No next Token" );
      }
      
      return token;
   }
   
   
   
   private static Token nextToken( TokenStream tokenStream )
   {
      final Token token = tokenStream.LT( 1 );
      
      if ( token.getType() != Token.EOF )
      {
         tokenStream.consume();
      }
      
      return token;
   }
   
   
   
   private static Token lookAheadToken( TokenStream tokenStream )
   {
      final Token token = tokenStream.LT( 1 );
      return token;
   }
   
   
   
   private static TokenStream getTokenStream( String recurrencePattern )
   {
      final CharStream stream = new ANTLRInputStream( recurrencePattern );
      final Lexer lexer = new dev.drsoran.rtm.parsing.grammar.antlr.recurrence.RecurrencePatternLexer( stream );
      
      final TokenStream tokenStream = new CommonTokenStream( lexer );
      
      return tokenStream;
   }
   
   
   
   private static void addElement( Map< Integer, List< Object > > elements,
                                   int element,
                                   Object value )
   {
      List< Object > values = elements.get( element );
      
      if ( values == null )
      {
         values = new LinkedList< Object >();
      }
      
      values.add( value );
      elements.put( element, values );
   }
   
   
   
   private static void addIntegerValueElement( Map< Integer, List< Object > > elements,
                                               int element,
                                               TokenStream tokenStream )
   {
      final Token interval = assertNextToken( tokenStream );
      addElement( elements, element, asInteger( interval ) );
   }
   
}
