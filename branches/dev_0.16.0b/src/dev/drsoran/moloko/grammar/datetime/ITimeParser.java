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

import dev.drsoran.moloko.util.MolokoCalendar;


public interface ITimeParser
{
   public final static class ParseTimeReturn
   {
      public final int lastParsedCharPos;
      
      public final boolean isEof;
      
      
      
      public ParseTimeReturn( int lastParsedCharPos, boolean isEof )
      {
         this.lastParsedCharPos = lastParsedCharPos;
         this.isEof = isEof;
      }
   }
   
   
   
   ParseTimeReturn parseTime( String time, MolokoCalendar cal, boolean adjustDay ) throws RecognitionException;
   
   
   
   ParseTimeReturn parseTimeSpec( String timeSpec,
                                  MolokoCalendar cal,
                                  boolean adjustDay ) throws RecognitionException;
   
   
   
   long parseTimeEstimate( String timeEstimate ) throws RecognitionException;
   
   
   
   Locale getLocale();
   
   
   
   MolokoCalendar getCalendar();
}
