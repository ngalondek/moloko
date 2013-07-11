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

package dev.drsoran.moloko.test.matchers;

import java.util.Calendar;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;

import dev.drsoran.moloko.MolokoCalendar;


public class MolokoCalendarMatcher extends TypeSafeMatcher< MolokoCalendar >
{
   private final int calendarField;
   
   private final String fieldName;
   
   private final int fieldValue;
   
   
   
   public MolokoCalendarMatcher( int calendarField, String fieldName,
      int fieldValue )
   {
      this.calendarField = calendarField;
      this.fieldName = fieldName;
      this.fieldValue = fieldValue;
   }
   
   
   
   @Override
   public void describeTo( Description desc )
   {
      desc.appendText( " the " )
          .appendText( fieldName )
          .appendText( " is " )
          .appendValue( fieldValue );
   }
   
   
   
   @Override
   public boolean matchesSafely( MolokoCalendar cal )
   {
      return cal.get( calendarField ) == fieldValue;
   }
   
   
   
   @Factory
   public static Matcher< MolokoCalendar > yearIs( int year )
   {
      return new MolokoCalendarMatcher( Calendar.YEAR, "year", year );
   }
   
   
   
   @Factory
   public static Matcher< MolokoCalendar > monthIs( int month )
   {
      return new MolokoCalendarMatcher( Calendar.MONTH, "month", month );
   }
   
   
   
   @Factory
   public static Matcher< MolokoCalendar > dayIs( int day )
   {
      return new MolokoCalendarMatcher( Calendar.DATE, "day", day );
   }
   
   
   
   @Factory
   public static Matcher< MolokoCalendar > hourIs( int hour )
   {
      return new MolokoCalendarMatcher( Calendar.HOUR_OF_DAY, "hour", hour );
   }
   
   
   
   @Factory
   public static Matcher< MolokoCalendar > minuteIs( int minute )
   {
      return new MolokoCalendarMatcher( Calendar.MINUTE, "minute", minute );
   }
   
   
   
   @Factory
   public static Matcher< MolokoCalendar > secondIs( int second )
   {
      return new MolokoCalendarMatcher( Calendar.SECOND, "second", second );
   }
}
