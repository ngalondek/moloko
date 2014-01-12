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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;

import dev.drsoran.rtm.RtmCalendar;


public class MolokoCalendarMatcher extends TypeSafeMatcher< RtmCalendar >
{
   private int calendarField;
   
   private String fieldName;
   
   private final int fieldValue;
   
   
   
   public MolokoCalendarMatcher( int calendarField, String fieldName,
      int fieldValue )
   {
      this.calendarField = calendarField;
      this.fieldName = fieldName;
      this.fieldValue = fieldValue;
   }
   
   
   
   private MolokoCalendarMatcher( int fieldValue )
   {
      this.fieldValue = fieldValue;
   }
   
   
   
   @Override
   public void describeTo( Description desc )
   {
      desc.appendText( fieldName );
      appendFieldValueDescription( desc );
   }
   
   
   
   @Override
   public boolean matchesSafely( RtmCalendar cal )
   {
      return cal.get( calendarField ) == fieldValue;
   }
   
   
   
   private void appendFieldValueDescription( Description desc )
   {
      if ( calendarField == Calendar.MONTH )
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( calendarField, fieldValue );
         
         desc.appendValue( new SimpleDateFormat( "MMMM", Locale.ENGLISH ).format( cal.getTime() ) );
      }
      else
      {
         desc.appendValue( fieldValue );
      }
   }
   
   
   
   private MolokoCalendarMatcher asYear()
   {
      calendarField = Calendar.YEAR;
      fieldName = "year";
      return this;
   }
   
   
   
   private MolokoCalendarMatcher asMonth()
   {
      calendarField = Calendar.MONTH;
      fieldName = "month";
      return this;
   }
   
   
   
   private MolokoCalendarMatcher asDay()
   {
      calendarField = Calendar.DATE;
      fieldName = "day";
      return this;
   }
   
   
   
   private MolokoCalendarMatcher asHour()
   {
      calendarField = Calendar.HOUR_OF_DAY;
      fieldName = "hour";
      return this;
   }
   
   
   
   private MolokoCalendarMatcher asMinute()
   {
      calendarField = Calendar.MINUTE;
      fieldName = "minute";
      return this;
   }
   
   
   
   private MolokoCalendarMatcher asSecond()
   {
      calendarField = Calendar.SECOND;
      fieldName = "second";
      return this;
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher next( RtmCalendar cal,
                                             MolokoCalendarMatcher matcher )
   {
      final RtmCalendar nextCal = cal.clone();
      nextCal.add( matcher.calendarField, 1 );
      
      final MolokoCalendarMatcher nextMatcher = new MolokoCalendarMatcher( matcher.calendarField,
                                                                           matcher.fieldName,
                                                                           nextCal.get( matcher.calendarField ) );
      return nextMatcher;
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher year()
   {
      return year( RtmCalendar.getInstance().get( Calendar.YEAR ) );
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher year( int year )
   {
      return new MolokoCalendarMatcher( year ).asYear();
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher month()
   {
      return month( RtmCalendar.getInstance().get( Calendar.MONTH ) );
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher month( int month )
   {
      return new MolokoCalendarMatcher( month ).asMonth();
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher day()
   {
      return day( RtmCalendar.getInstance().get( Calendar.DATE ) );
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher day( int day )
   {
      return new MolokoCalendarMatcher( day ).asDay();
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher hour()
   {
      return hour( RtmCalendar.getInstance().get( Calendar.HOUR_OF_DAY ) );
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher hour( int hour )
   {
      return new MolokoCalendarMatcher( hour ).asHour();
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher minute()
   {
      return minute( RtmCalendar.getInstance().get( Calendar.MINUTE ) );
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher minute( int minute )
   {
      return new MolokoCalendarMatcher( minute ).asMinute();
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher second()
   {
      return second( RtmCalendar.getInstance().get( Calendar.SECOND ) );
   }
   
   
   
   @Factory
   public static MolokoCalendarMatcher second( int second )
   {
      return new MolokoCalendarMatcher( second ).asSecond();
   }
}
