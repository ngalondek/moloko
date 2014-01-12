/*
 * Copyright (c) 2012 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.util;

import java.util.TimeZone;

import android.text.format.DateUtils;
import android.text.format.Time;
import dev.drsoran.rtm.RtmCalendar;


public class MolokoDateUtils
{
   public static final int HOUR_IN_SECONDS = 3600;
   
   public static final int DAY_IN_SECONDS = 24 * HOUR_IN_SECONDS;
   
   
   
   private MolokoDateUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   
   public final static RtmCalendar newCalendar( long millis )
   {
      if ( millis < 0 )
      {
         throw new IllegalArgumentException( "millis" );
      }
      
      final RtmCalendar cal = RtmCalendar.getInstance();
      cal.setTimeInMillis( millis );
      return cal;
   }
   
   
   
   public final static Time newTime()
   {
      final Time t = new Time( TimeZone.getDefault().getID() );
      t.setToNow();
      return t;
   }
   
   
   
   public final static Time newTime( long millis )
   {
      if ( millis < 0 )
      {
         throw new IllegalArgumentException( "millis" );
      }
      
      final Time t = new Time( TimeZone.getDefault().getID() );
      t.set( millis );
      return t;
   }
   
   
   
   public static boolean isToday( long when )
   {
      return getTimespanInDays( System.currentTimeMillis(), when ) == 0;
   }
   
   
   
   public static boolean isDaysBefore( long when, long reference )
   {
      return getTimespanInDays( when, reference ) > 0;
   }
   
   
   
   public static boolean isDaysAfter( long when, long reference )
   {
      return getTimespanInDays( when, reference ) < 0;
   }
   
   
   
   public static int getTimespanInDays( long start, long end )
   {
      if ( start < 0 )
      {
         throw new IllegalArgumentException( "start" );
      }
      if ( end < 0 )
      {
         throw new IllegalArgumentException( "end" );
      }
      
      final TimeZone timeZone = TimeZone.getDefault();
      final int offStart = timeZone.getOffset( start )
         / (int) DateUtils.SECOND_IN_MILLIS; // in sec.
      final int offEnd = timeZone.getOffset( end )
         / (int) DateUtils.SECOND_IN_MILLIS; // in sec.
      
      final int span = Time.getJulianDay( end, offEnd )
         - Time.getJulianDay( start, offStart );
      
      return span;
   }
   
   
   
   public static long getFittingDateUtilsResolution( long millis, long nowMillis )
   {
      if ( millis < 0 )
      {
         throw new IllegalArgumentException( "millis" );
      }
      if ( nowMillis < 0 )
      {
         throw new IllegalArgumentException( "nowMillis" );
      }
      
      final int diff = (int) ( ( ( millis >= nowMillis ) ? millis - nowMillis
                                                        : nowMillis - millis ) / DateUtils.SECOND_IN_MILLIS );
      
      // 1..n days
      if ( diff >= DAY_IN_SECONDS )
      {
         return DateUtils.DAY_IN_MILLIS;
      }
      // 1..24 hours
      else if ( diff >= HOUR_IN_SECONDS )
      {
         return DateUtils.HOUR_IN_MILLIS;
      }
      // 1..60 minutes
      else if ( diff >= 60 )
      {
         return DateUtils.MINUTE_IN_MILLIS;
      }
      // < 1 minute
      else
      {
         return DateUtils.SECOND_IN_MILLIS;
      }
   }
   
   
   
   public final static String getDayOfWeekString( int calendarDayOfWeek )
   {
      return DateUtils.getDayOfWeekString( calendarDayOfWeek,
                                           DateUtils.LENGTH_LONG );
   }
   
   
   
   public final static String getAbbreviatedDayOfWeekString( int calendarDayOfWeek )
   {
      return DateUtils.getDayOfWeekString( calendarDayOfWeek,
                                           DateUtils.LENGTH_SHORT );
   }
   
   
   
   public final static TimeStruct getTimeStruct( long millis )
   {
      if ( millis < 0 )
      {
         throw new IllegalArgumentException( "millis" );
      }
      
      int days = 0;
      int hours = 0;
      int minutes = 0;
      int seconds = 0;
      
      seconds = (int) ( millis / DateUtils.SECOND_IN_MILLIS );
      
      if ( seconds >= 60 )
      {
         if ( seconds >= DAY_IN_SECONDS )
         {
            days = seconds / HOUR_IN_SECONDS / 24;
            seconds -= days * DAY_IN_SECONDS;
         }
         
         if ( seconds >= HOUR_IN_SECONDS )
         {
            hours = seconds / HOUR_IN_SECONDS;
            seconds -= hours * HOUR_IN_SECONDS;
         }
         
         if ( seconds >= 60 )
         {
            minutes = seconds / 60;
            seconds -= minutes * 60;
         }
      }
      
      return new TimeStruct( days, hours, minutes, seconds );
   }
}
