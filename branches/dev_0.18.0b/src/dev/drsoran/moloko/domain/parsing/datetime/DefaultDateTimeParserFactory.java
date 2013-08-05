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

package dev.drsoran.moloko.domain.parsing.datetime;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

import dev.drsoran.moloko.domain.parsing.IDateTimeParserFactory;
import dev.drsoran.moloko.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser;
import dev.drsoran.moloko.grammar.antlr.datetime.TimeParser;


public class DefaultDateTimeParserFactory implements IDateTimeParserFactory
{
   private final Iterable< Locale > availableLocales = Arrays.asList( Locale.ENGLISH,
                                                                      Locale.GERMAN );
   
   
   
   @Override
   public DateParser createDateParser( Locale locale, String dateToParse ) throws NoSuchElementException
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( dateToParse );
      final Lexer lexer = createDateLexerForLocale( locale, stream );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final DateParser parser = new DateParser( antlrTokens );
      
      return parser;
   }
   
   
   
   @Override
   public TimeParser createTimeParser( Locale locale, String timeToParse ) throws NoSuchElementException
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( timeToParse );
      final Lexer lexer = createTimeLexerForLocale( locale, stream );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeParser parser = new TimeParser( antlrTokens );
      
      return parser;
   }
   
   
   
   @Override
   public Iterable< Locale > getAvailableParserLocales()
   {
      return availableLocales;
   }
   
   
   
   private Lexer createTimeLexerForLocale( Locale locale, CharStream input ) throws NoSuchElementException
   {
      if ( locale == Locale.ENGLISH )
      {
         return new dev.drsoran.moloko.grammar.antlr.datetime.TimeLexer( input );
      }
      else if ( locale == Locale.GERMAN )
      {
         return new dev.drsoran.moloko.grammar.antlr.datetime.de.TimeLexer( input );
      }
      else
      {
         throw new NoSuchElementException( MessageFormat.format( "No time lexer for locale {0}",
                                                                 locale.toString() ) );
      }
   }
   
   
   
   private Lexer createDateLexerForLocale( Locale locale, CharStream input ) throws NoSuchElementException
   {
      if ( locale == Locale.ENGLISH )
      {
         return new dev.drsoran.moloko.grammar.antlr.datetime.DateLexer( input );
      }
      else if ( locale == Locale.GERMAN )
      {
         return new dev.drsoran.moloko.grammar.antlr.datetime.de.DateLexer( input );
      }
      else
      {
         throw new NoSuchElementException( MessageFormat.format( "No date lexer for locale {0}",
                                                                 locale.toString() ) );
      }
   }
}
