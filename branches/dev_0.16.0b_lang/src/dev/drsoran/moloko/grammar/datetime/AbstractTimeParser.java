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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.grammar.datetime.ITimeParser.ParseTimeReturn;
import dev.drsoran.moloko.util.MolokoCalendar;


public abstract class AbstractTimeParser extends Parser
{
   private boolean success = true;
   
   
   
   protected AbstractTimeParser( TokenStream input )
   {
      super( input );
   }
   
   
   
   protected AbstractTimeParser( TokenStream input, RecognizerSharedState state )
   {
      super( input, state );
   }
   
   
   
   protected MolokoCalendar getCalendar()
   {
      return MolokoCalendar.getInstance();
   }
   
   
   
   protected void setCalendarTime( MolokoCalendar cal, String pointInTime ) throws RecognitionException
   {
      final int len = pointInTime.length();
      
      SimpleDateFormat sdf = null;
      
      try
      {
         if ( len < 3 )
         {
            sdf = new SimpleDateFormat( "HH" );
         }
         else if ( len > 3 )
         {
            sdf = new SimpleDateFormat( "HHmm" );
         }
         else
         {
            sdf = new SimpleDateFormat( "Hmm" );
         }
         
         sdf.parse( pointInTime );
         
         final Calendar sdfCal = sdf.getCalendar();
         cal.set( Calendar.HOUR_OF_DAY, sdfCal.get( Calendar.HOUR_OF_DAY ) );
         cal.set( Calendar.MINUTE, sdfCal.get( Calendar.MINUTE ) );
         cal.set( Calendar.SECOND, 0 );
      }
      catch ( ParseException e )
      {
         throw new RecognitionException();
      }
   }
   
   
   
   protected void startParsingTime( MolokoCalendar cal )
   {
      success = true;
   }
   
   
   
   protected ParseTimeReturn finishedParsingTime( MolokoCalendar cal )
   {
      cal.setHasTime( success );
      
      final CommonToken lastToken = (CommonToken) input.LT( -1 );
      return new ParseTimeReturn( lastToken != null
                                                   ? lastToken.getStopIndex() + 1
                                                   : 0,
                                  input.LA( 1 ) == Token.EOF );
   }
   
   
   
   protected void notifyParsingTimeFailed()
   {
      success = false;
   }
}
