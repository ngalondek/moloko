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

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.datetime.AbstractTimeParser.ParseTimeReturn;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;
import dev.drsoran.moloko.util.MolokoCalendar;


public class TimeParserImpl implements ITimeParser
{
   private final TimeParser parser = new TimeParser();
   
   private final TimeLexer lexer = new TimeLexer();
   
   public final static Locale LOCALE = Locale.US;
   
   

   public ParseTimeReturn parseTime( String time,
                                     MolokoCalendar cal,
                                     boolean adjustDay ) throws RecognitionException
   {
      prepareLexerAndParser( time );
      
      return parser.parseTime( cal, adjustDay );
   }
   


   public ParseTimeReturn parseTimeSpec( String timeSpec,
                                         MolokoCalendar cal,
                                         boolean adjustDay ) throws RecognitionException
   {
      prepareLexerAndParser( timeSpec );
      
      return parser.parseTimeSpec( cal, adjustDay );
   }
   


   public long parseTimeEstimate( String timeEstimate ) throws RecognitionException
   {
      prepareLexerAndParser( timeEstimate );
      
      return parser.parseTimeEstimate();
   }
   


   public Locale getLocale()
   {
      return LOCALE;
   }
   


   public MolokoCalendar getCalendar()
   {
      return MolokoCalendar.getInstance();
   }
   


   private void prepareLexerAndParser( String time )
   {
      final ANTLRNoCaseStringStream stream = new ANTLRNoCaseStringStream( time );
      lexer.setCharStream( stream );
      
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      parser.setTokenStream( antlrTokens );
   }
   
}
