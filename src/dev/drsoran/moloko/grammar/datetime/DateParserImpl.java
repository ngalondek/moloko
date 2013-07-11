/* 
 *	Copyright (c) 2011 Ronny Röhricht
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

package dev.drsoran.moloko.grammar.datetime;

import java.util.Locale;

import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.IDateFormatter;


public class DateParserImpl implements IDateParser
{
   private final AbstractANTLRDateParser parser;
   
   private final Lexer lexer;
   
   private final Locale locale;
   
   
   
   public DateParserImpl( Locale locale, AbstractANTLRDateParser dateParser,
      Lexer dateLexer )
   {
      this.locale = locale;
      this.parser = dateParser;
      this.lexer = dateLexer;
   }
   
   
   
   @Override
   public void setDateFormatter( IDateFormatter context )
   {
      parser.setDateFormatContext( context );
   }
   
   
   
   @Override
   public ParseReturn parseDate( String date,
                                 MolokoCalendar cal,
                                 boolean clearTime ) throws GrammarException
   {
      prepareLexerAndParser( date );
      
      try
      {
         return parser.parseDate( cal, clearTime );
      }
      catch ( RecognitionException e )
      {
         throw new GrammarException( "Failed to parse date '" + date + "'", e );
      }
   }
   
   
   
   @Override
   public ParseDateWithinReturn parseDateWithin( String dateWithin, boolean past ) throws GrammarException
   {
      prepareLexerAndParser( dateWithin );
      
      try
      {
         final ParseDateWithinReturn res = parser.parseDateWithin( past );
         return res;
      }
      catch ( RecognitionException e )
      {
         throw new GrammarException( "Failed to parse date within '"
            + dateWithin + "'", e );
      }
   }
   
   
   
   @Override
   public Locale getLocale()
   {
      return locale;
   }
   
   
   
   private void prepareLexerAndParser( String date )
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( date );
      lexer.setCharStream( stream );
      
      final TokenStream antlrTokens = new ANTLRIncrementalTokenStream( lexer );
      parser.setTokenStream( antlrTokens );
   }
}
