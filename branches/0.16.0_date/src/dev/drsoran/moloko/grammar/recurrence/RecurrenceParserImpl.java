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
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


public class RecurrenceParserImpl implements IRecurrenceParser
{
   private final RecurrenceParser parser = new RecurrenceParser();
   
   private final RecurrenceLexer lexer = new RecurrenceLexer();
   
   public final static Locale LOCALE = RecurrenceParser.LOCALE;
   
   

   public Map< String, Object > parseRecurrence( String recurrence ) throws RecognitionException
   {
      lexer.setCharStream( new ANTLRNoCaseStringStream( recurrence ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      parser.setTokenStream( antlrTokens );
      
      try
      {
         return parser.parseRecurrence();
      }
      finally
      {
         parser.clearState();
      }
   }
   


   public Locale getLocale()
   {
      return LOCALE;
   }
}
