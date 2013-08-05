/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of MolokoTest.
 *
 *	MolokoTest is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MolokoTest is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with MolokoTest.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.test.comp.grammar.antlr.datetime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.drsoran.moloko.test.IDateParserTestLanguage;


public class DateParserTestLanguageEn implements IDateParserTestLanguage
{
   private final static Map< Integer, Collection< String > > MONTHS;
   
   private final static Map< Integer, Collection< String > > WEEKDAYS;
   
   private final static List< String > NUM_STRINGS;
   
   static
   {
      MONTHS = new LinkedHashMap< Integer, Collection< String > >();
      
      MONTHS.put( 1, Arrays.asList( "January", "Jan" ) );
      MONTHS.put( 2, Arrays.asList( "February", "Feb" ) );
      MONTHS.put( 3, Arrays.asList( "March" ) );
      MONTHS.put( 4, Arrays.asList( "April", "Apr" ) );
      MONTHS.put( 5, Arrays.asList( "May" ) );
      MONTHS.put( 6, Arrays.asList( "June", "Jun" ) );
      MONTHS.put( 7, Arrays.asList( "July", "Jul" ) );
      MONTHS.put( 8, Arrays.asList( "August", "Aug" ) );
      MONTHS.put( 9, Arrays.asList( "September", "Sep", "Sept" ) );
      MONTHS.put( 10, Arrays.asList( "October", "Oct" ) );
      MONTHS.put( 11, Arrays.asList( "November", "Nov" ) );
      MONTHS.put( 12, Arrays.asList( "December", "Dec" ) );
      
      WEEKDAYS = new LinkedHashMap< Integer, Collection< String > >();
      WEEKDAYS.put( Calendar.MONDAY, Arrays.asList( "monday", "mon" ) );
      WEEKDAYS.put( Calendar.TUESDAY, Arrays.asList( "tuesday", "tue" ) );
      WEEKDAYS.put( Calendar.WEDNESDAY, Arrays.asList( "wednesday", "wed" ) );
      WEEKDAYS.put( Calendar.THURSDAY, Arrays.asList( "thursday", "thu" ) );
      WEEKDAYS.put( Calendar.FRIDAY, Arrays.asList( "friday", "fri" ) );
      WEEKDAYS.put( Calendar.SATURDAY, Arrays.asList( "saturday", "sat" ) );
      WEEKDAYS.put( Calendar.SUNDAY, Arrays.asList( "sunday", "sun" ) );
      
      NUM_STRINGS = new ArrayList< String >( 10 );
      NUM_STRINGS.add( "one" );
      NUM_STRINGS.add( "two" );
      NUM_STRINGS.add( "three" );
      NUM_STRINGS.add( "four" );
      NUM_STRINGS.add( "five" );
      NUM_STRINGS.add( "six" );
      NUM_STRINGS.add( "seven" );
      NUM_STRINGS.add( "eight" );
      NUM_STRINGS.add( "nine" );
      NUM_STRINGS.add( "ten" );
   }
   
   
   
   @Override
   public Collection< String > getMonthStrings( int month )
   {
      return MONTHS.get( month );
   }
   
   
   
   @Override
   public Collection< String > getWeekdayStrings( int weekday )
   {
      return WEEKDAYS.get( weekday );
   }
   
   
   
   @Override
   public Collection< String > getNumberStrings( int number )
   {
      return Collections.singletonList( NUM_STRINGS.get( number - 1 ) );
   }
   
   
   
   @Override
   public Collection< String > getNumericDateSeparators()
   {
      return Arrays.asList( ".", "-", "/" );
   }
   
   
   
   @Override
   public Collection< String > getXst()
   {
      return Arrays.asList( "st", "nd", "th", "rd" );
   }
   
   
   
   @Override
   public Collection< String > getDayMonthSeparators()
   {
      return Arrays.asList( " of ", "-a ", "-", ",", "." );
   }
   
   
   
   @Override
   public Collection< String > getMonthYearSeparators()
   {
      return Arrays.asList( "-", "." );
   }
   
   
   
   @Override
   public Collection< String > getMonthDaySeparators()
   {
      return Arrays.asList( "-", ",", "." );
   }
   
   
   
   @Override
   public Collection< String > getDateConcatenators()
   {
      return Arrays.asList( " and ", "," );
   }
   
   
   
   @Override
   public Collection< String > getYearLiterals()
   {
      return Arrays.asList( "years", "year", "yrs", "yr" );
   }
   
   
   
   @Override
   public Collection< String > getMonthLiterals()
   {
      return Arrays.asList( "months", "month", "mons" );
   }
   
   
   
   @Override
   public Collection< String > getWeekLiterals()
   {
      return Arrays.asList( "weeks", "week", "wks", "wk" );
   }
   
   
   
   @Override
   public Collection< String > getDayLiterals()
   {
      return Arrays.asList( "days", "day", "d" );
   }
   
   
   
   @Override
   public Collection< String > getNext()
   {
      return Arrays.asList( "next " );
   }
   
   
   
   @Override
   public Collection< String > getIn()
   {
      return Arrays.asList( "in " );
   }
   
   
   
   @Override
   public Collection< String > getOf()
   {
      return Arrays.asList( "of " );
   }
   
   
   
   @Override
   public Collection< String > getEndOfThe()
   {
      return Arrays.asList( "end ", "end of ", "end of the " );
   }
   
   
   
   @Override
   public Collection< String > getToday()
   {
      return Arrays.asList( "today", "tod", "tonight", "ton" );
   }
   
   
   
   @Override
   public Collection< String > getTomorrow()
   {
      return Arrays.asList( "tom", "tmr", "tomorrow", "tommorow" );
   }
   
   
   
   @Override
   public Collection< String > getYesterday()
   {
      return Arrays.asList( "yesterday" );
   }
   
   
   
   @Override
   public Collection< String > getNow()
   {
      return Arrays.asList( "now" );
   }
   
   
   
   @Override
   public Collection< String > getNever()
   {
      return Arrays.asList( "never" );
   }
   
}
