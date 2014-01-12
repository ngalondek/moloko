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

package dev.drsoran.rtm.parsing.datetime;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

import dev.drsoran.rtm.parsing.grammar.ANTLRBailOutErrorListener;
import dev.drsoran.rtm.parsing.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeParser;


public class AntlrDateTimeParserFactory implements IRtmDateTimeParserFactory
{
   private final Iterable< Locale > availableLocales = Arrays.asList( Locale.ENGLISH,
                                                                      Locale.GERMAN );
   
   
   
   @Override
   public DateTimeParser createDateTimeParser( Locale locale,
                                               String dateTimeToParse ) throws NoSuchElementException
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( dateTimeToParse );
      final Lexer lexer = createLexerForLocale( locale, stream );
      lexer.addErrorListener( new ANTLRBailOutErrorListener() );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final DateTimeParser parser = new DateTimeParser( antlrTokens );
      
      return parser;
   }
   
   
   
   @Override
   public Iterable< Locale > getAvailableParserLocales()
   {
      return availableLocales;
   }
   
   
   
   private Lexer createLexerForLocale( Locale locale, CharStream input ) throws NoSuchElementException
   {
      if ( locale == Locale.ENGLISH )
      {
         return new dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeLexer( input );
      }
      else if ( locale == Locale.GERMAN )
      {
         return new dev.drsoran.rtm.parsing.grammar.antlr.datetime.de.DateTimeLexer( input );
      }
      else
      {
         throw new NoSuchElementException( MessageFormat.format( "No date lexer for locale {0}",
                                                                 locale.toString() ) );
      }
   }
}
