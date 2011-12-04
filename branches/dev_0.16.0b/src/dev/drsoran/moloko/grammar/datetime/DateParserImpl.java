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

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.util.ANTLRIncrementalTokenStream;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.util.MolokoCalendar;


public class DateParserImpl implements IDateParser
{
   private final DateParser parser = new DateParser();
   
   private final DateLexer lexer = new DateLexer();
   
   public final static Locale LOCALE = Locale.ENGLISH;
   
   

   @Override
   public void setDateFormatContext( IDateFormatContext context )
   {
      parser.setDateFormatContext( context );
   }
   


   @Override
   public ParseDateReturn parseDate( String date,
                                     MolokoCalendar cal,
                                     boolean clearTime ) throws RecognitionException
   {
      prepareLexerAndParser( date );
      
      return parser.parseDate( cal, clearTime );
   }
   


   @Override
   public ParseDateWithinReturn parseDateWithin( String dateWithin, boolean past ) throws RecognitionException
   {
      prepareLexerAndParser( dateWithin );
      
      final DateParser.parseDateWithin_return res = parser.parseDateWithin( past );
      
      return res != null ? new ParseDateWithinReturn( res.epochStart,
                                                      res.epochEnd ) : null;
   }
   


   @Override
   public Locale getLocale()
   {
      return LOCALE;
   }
   


   @Override
   public MolokoCalendar getCalendar()
   {
      return MolokoCalendar.getInstance();
   }
   


   private void prepareLexerAndParser( String date )
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( date );
      lexer.setCharStream( stream );
      
      final TokenStream antlrTokens = new ANTLRIncrementalTokenStream( lexer );
      parser.setTokenStream( antlrTokens );
   }
}
