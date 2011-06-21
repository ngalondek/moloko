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

import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateReturn;
import dev.drsoran.moloko.util.MolokoCalendar;


public abstract class AbstractDateParser extends Parser
{
   
   protected AbstractDateParser( TokenStream input )
   {
      super( input );
   }
   


   protected AbstractDateParser( TokenStream input, RecognizerSharedState state )
   {
      super( input, state );
   }
   


   protected void handleFullDate( MolokoCalendar cal,
                                  String part1,
                                  String part2,
                                  String part3 ) throws RecognitionException
   {
      final boolean yearFirst = part3 != null && part1.length() > 2;
      
      parseFullDate( cal, yearFirst ? part2 : part1, // day
                     yearFirst ? part3 : part2, // month
                     yearFirst ? part1 : part3, // year
                     false );
      
      // if year is missing and the date is
      // before now we roll to the next year.
      if ( part3 == null )
      {
         final MolokoCalendar now = getCalendar();
         
         if ( cal.before( now ) )
         {
            cal.add( Calendar.YEAR, 1 );
         }
      }
   }
   


   protected void handleDateOnXstOfMonth( MolokoCalendar cal,
                                          boolean hasYear,
                                          boolean hasMonth )
   {
      final MolokoCalendar now = getCalendar();
      
      // if we have a year we have a full qualified date.
      // so we change nothing.
      if ( !hasYear && cal.before( now ) )
      {
         // if we have a month, we roll to next year.
         if ( hasMonth )
            cal.add( Calendar.YEAR, 1 );
         // if we only have a day, we roll to next month.
         else
            cal.add( Calendar.MONTH, 1 );
      }
   }
   


   protected void handleDateOnWeekday( MolokoCalendar cal,
                                       String weekday,
                                       boolean nextWeek ) throws RecognitionException
   {
      final int parsedWeekDay = getWeekdayNumber( weekday );
      
      if ( parsedWeekDay == -1 )
         throw new RecognitionException();
      
      final int currentWeekDay = cal.get( Calendar.DAY_OF_WEEK );
      
      cal.set( Calendar.DAY_OF_WEEK, parsedWeekDay );
      
      // If:
      // - the weekday is before today or today,
      // - today is sunday
      // we adjust to next week.
      if ( parsedWeekDay <= currentWeekDay || currentWeekDay == Calendar.SUNDAY )
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
      
      // if the next week is explicitly enforced
      if ( nextWeek )
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
   }
   


   public MolokoCalendar getCalendar()
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      
      cal.setHasTime( false );
      
      return cal;
   }
   


   protected abstract int numberStringToNumber( String string );
   


   protected abstract int weekdayStringToNumber( String string );
   


   protected abstract int monthStringToNumber( String string );
   


   protected int getMonthNumber( String month )
   {
      // Only take the 1st three chars of the month as key.
      return monthStringToNumber( month.substring( 0, 3 ) );
   }
   


   protected int getWeekdayNumber( String weekday )
   {
      // Only take the 1st two chars of the weekday as key.
      return weekdayStringToNumber( weekday.substring( 0, 2 ) );
   }
   


   protected void parseFullDate( MolokoCalendar cal,
                                 String day,
                                 String month,
                                 String year,
                                 boolean textMonth ) throws RecognitionException
   {
      try
      {
         final StringBuffer pattern = new StringBuffer( "dd.MM" );
         
         int monthNum = -1;
         
         if ( textMonth )
         {
            monthNum = getMonthNumber( month );
         }
         else
         {
            monthNum = Integer.parseInt( month );
         }
         
         if ( monthNum == -1 )
            throw new RecognitionException();
         
         if ( year != null )
            pattern.append( ".yyyy" );
         
         final SimpleDateFormat sdf = new SimpleDateFormat( pattern.toString() );
         
         sdf.parse( day + "." + monthNum
            + ( ( year != null ) ? "." + year : "" ) );
         
         final Calendar sdfCal = sdf.getCalendar();
         
         cal.set( Calendar.DAY_OF_MONTH, sdfCal.get( Calendar.DAY_OF_MONTH ) );
         cal.set( Calendar.MONTH, sdfCal.get( Calendar.MONTH ) );
         
         if ( year != null )
            cal.set( Calendar.YEAR, sdfCal.get( Calendar.YEAR ) );
      }
      catch ( NumberFormatException nfe )
      {
         throw new RecognitionException();
      }
      catch ( ParseException e )
      {
         throw new RecognitionException();
      }
   }
   


   protected void parseTextMonth( MolokoCalendar cal, String month ) throws RecognitionException
   {
      final int monthNum = getMonthNumber( month );
      
      if ( monthNum == -1 )
         throw new RecognitionException();
      
      cal.set( Calendar.MONTH, monthNum );
   }
   


   protected void parseYear( MolokoCalendar cal, String yearStr ) throws RecognitionException
   {
      final int len = yearStr.length();
      
      int year = 0;
      
      try
      {
         if ( len < 4 )
         {
            final SimpleDateFormat sdf = new SimpleDateFormat( "yy" );
            
            if ( len == 1 )
            {
               yearStr = "0" + yearStr;
            }
            else if ( len == 3 )
            {
               yearStr = yearStr.substring( 1, len );
            }
            
            sdf.parse( yearStr );
            year = sdf.getCalendar().get( Calendar.YEAR );
         }
         else
         {
            year = Integer.parseInt( yearStr );
         }
         
         cal.set( Calendar.YEAR, year );
      }
      catch ( ParseException pe )
      {
         throw new RecognitionException();
      }
      catch ( NumberFormatException nfe )
      {
         throw new RecognitionException();
      }
   }
   


   protected void rollToEndOf( int field, MolokoCalendar cal )
   {
      final int ref = cal.get( field );
      final int max = cal.getActualMaximum( field );
      
      // set the field to the end.
      cal.set( field, max );
      
      // if already at the end
      if ( ref == max )
      {
         // we roll to the next
         cal.add( field, 1 );
      }
   }
   


   protected ParseDateReturn finishedDateParsing()
   {
      final CommonToken lastToken = (CommonToken) input.LT( -1 );
      return new ParseDateReturn( lastToken != null
                                                   ? lastToken.getStopIndex() + 1
                                                   : 0,
                                  input.LA( 1 ) == Token.EOF );
   }
}
