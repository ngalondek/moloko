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

package dev.drsoran.moloko.domain.parsing.recurrence;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.PredictionMode;

import dev.drsoran.moloko.grammar.ANTLRBailOutErrorListener;
import dev.drsoran.moloko.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceParser;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer;
import dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternParser;


public class AntlrRecurrenceParserFactory implements IRecurrenceParserFactory
{
   private final Iterable< Locale > availableLocales = Arrays.asList( Locale.ENGLISH,
                                                                      Locale.GERMAN );
   
   
   
   @Override
   public RecurrenceParser createRecurrenceParser( Locale locale,
                                                   String recurrenceSentence ) throws NoSuchElementException
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( recurrenceSentence );
      final Lexer lexer = createRecurrenceLexerForLocale( locale, stream );
      lexer.addErrorListener( new ANTLRBailOutErrorListener() );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      
      final RecurrenceParser parser = new RecurrenceParser( antlrTokens );
      parser.getInterpreter().setPredictionMode( PredictionMode.SLL );
      
      return parser;
   }
   
   
   
   @Override
   public RecurrencePatternParser createRecurrencePatternParser( String recurrencePattern )
   {
      final CharStream stream = new ANTLRInputStream( recurrencePattern );
      final Lexer lexer = new RecurrencePatternLexer( stream );
      lexer.addErrorListener( new ANTLRBailOutErrorListener() );
      
      final TokenStream tokenStream = new CommonTokenStream( lexer );
      
      final RecurrencePatternParser parser = new RecurrencePatternParser( tokenStream );
      parser.getInterpreter().setPredictionMode( PredictionMode.SLL );
      
      return parser;
   }
   
   
   
   @Override
   public Iterable< Locale > getAvailableParserLocales()
   {
      return availableLocales;
   }
   
   
   
   private Lexer createRecurrenceLexerForLocale( Locale locale, CharStream input ) throws NoSuchElementException
   {
      if ( locale == Locale.ENGLISH )
      {
         return new dev.drsoran.moloko.grammar.antlr.recurrence.RecurrenceLexer( input );
      }
      else if ( locale == Locale.GERMAN )
      {
         return new dev.drsoran.moloko.grammar.antlr.recurrence.de.RecurrenceLexer( input );
      }
      else
      {
         throw new NoSuchElementException( MessageFormat.format( "No recurrence lexer for locale {0}",
                                                                 locale.toString() ) );
      }
   }
}
