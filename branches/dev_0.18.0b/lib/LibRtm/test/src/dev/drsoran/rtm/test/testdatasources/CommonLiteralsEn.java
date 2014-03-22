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

package dev.drsoran.rtm.test.testdatasources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


class CommonLiteralsEn
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
      WEEKDAYS.put( Calendar.MONDAY, Arrays.asList( "Monday", "mon" ) );
      WEEKDAYS.put( Calendar.TUESDAY, Arrays.asList( "Tuesday", "tue" ) );
      WEEKDAYS.put( Calendar.WEDNESDAY, Arrays.asList( "Wednesday", "wed" ) );
      WEEKDAYS.put( Calendar.THURSDAY, Arrays.asList( "Thursday", "thu" ) );
      WEEKDAYS.put( Calendar.FRIDAY, Arrays.asList( "Friday", "fri" ) );
      WEEKDAYS.put( Calendar.SATURDAY, Arrays.asList( "Saturday", "sat" ) );
      WEEKDAYS.put( Calendar.SUNDAY, Arrays.asList( "Sunday", "sun" ) );
      
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
   
   
   
   public Collection< String > getMonthStrings( int month )
   {
      return MONTHS.get( month );
   }
   
   
   
   public Collection< String > getWeekdayStrings( int weekday )
   {
      return WEEKDAYS.get( weekday );
   }
   
   
   
   public Collection< String > getNumberStrings( int number )
   {
      return Collections.singletonList( ( NUM_STRINGS.get( number - 1 ) ) );
   }
}
