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

package dev.drsoran.moloko.grammar.recurrence;

import java.util.Locale;
import java.util.Map;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.grammar.ANTLRNoCaseStringStream;


public class RecurrenceParserImpl implements IRecurrenceParser
{
   private final AbstractANTLRRecurrenceParser parser;
   
   private final Lexer lexer;
   
   private final Locale locale;
   
   
   
   public RecurrenceParserImpl( Locale locale,
      AbstractANTLRRecurrenceParser recurrenceParser, Lexer recurrenceLexer )
   {
      this.locale = locale;
      this.parser = recurrenceParser;
      this.lexer = recurrenceLexer;
   }
   
   
   
   @Override
   public Map< String, Object > parseRecurrence( String recurrence ) throws GrammarException
   {
      lexer.setCharStream( new ANTLRNoCaseStringStream( recurrence ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      parser.setTokenStream( antlrTokens );
      
      try
      {
         return parser.parseRecurrence();
      }
      catch ( RecognitionException e )
      {
         throw new GrammarException( "Failed parser recurrence '" + recurrence
            + "'", e );
      }
      finally
      {
         parser.clearState();
      }
   }
   
   
   
   @Override
   public Locale getLocale()
   {
      return locale;
   }
}
