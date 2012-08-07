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

import java.util.Date;
import java.util.TimeZone;

import android.text.format.DateUtils;
import android.text.format.Time;

import com.mdt.rtm.data.RtmData;

import dev.drsoran.rtm.ParcelableDate;


public class MolokoDateUtils
{
   public final static class EstimateStruct
   {
      public final int days;
      
      public final int hours;
      
      public final int minutes;
      
      
      
      public EstimateStruct( int days, int hours, int minutes )
      {
         this.days = days;
         this.hours = hours;
         this.minutes = minutes;
      }
   }
   
   
   
   private MolokoDateUtils()
   {
      throw new AssertionError( "This class should not be instantiated." );
   }
   
   
   
   public final static MolokoCalendar newCalendar( long millis )
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      cal.setTimeInMillis( millis );
      return cal;
   }
   
   
   
   public final static MolokoCalendar newCalendarUTC( long millis )
   {
      final MolokoCalendar cal = MolokoCalendar.getUTCInstance();
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
      final Time t = new Time( TimeZone.getDefault().getID() );
      t.set( millis );
      return t;
   }
   
   
   
   public static boolean isToday( long when )
   {
      return ( getTimespanInDays( System.currentTimeMillis(), when ) == 0 );
   }
   
   
   
   public static boolean isBefore( long when, long reference )
   {
      return ( getTimespanInDays( when, reference ) > 0 );
   }
   
   
   
   public static boolean isAfter( long when, long reference )
   {
      return ( getTimespanInDays( when, reference ) < 0 );
   }
   
   
   
   public static int getTimespanInDays( long start, long end )
   {
      final TimeZone timeZone = TimeZone.getDefault();
      final int offStart = timeZone.getOffset( start ) / 1000; // in sec.
      final int offEnd = timeZone.getOffset( end ) / 1000; // in sec.
      
      final int span = Time.getJulianDay( end, offEnd )
         - Time.getJulianDay( start, offStart );
      
      return span;
   }
   
   
   
   public static long getFittingDateUtilsResolution( long time, long now )
   {
      final int diff = (int) ( ( ( time >= now ) ? time - now : now - time ) / 1000 );
      
      // 1..n days
      if ( diff >= 3600 * 24 )
      {
         return DateUtils.DAY_IN_MILLIS;
      }
      // 1..24 hours
      else if ( diff >= 3600 )
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
   
   
   
   public final static Date parseRtmDate( String rtmDateStr )
   {
      try
      {
         return RtmData.parseDate( rtmDateStr );
      }
      catch ( RuntimeException e )
      {
         throw new IllegalArgumentException( e );
      }
   }
   
   
   
   public final static String getDayOfWeekString( int calendarDayOfWeek,
                                                  boolean abbrev )
   {
      return DateUtils.getDayOfWeekString( calendarDayOfWeek,
                                           abbrev ? DateUtils.LENGTH_SHORT
                                                 : DateUtils.LENGTH_LONG );
   }
   
   
   
   public final static Date getDate( ParcelableDate parcelableDate )
   {
      return getDate( parcelableDate, null );
   }
   
   
   
   public final static Date getDate( ParcelableDate parcelableDate,
                                     Date defaultValue )
   {
      Date ret = defaultValue;
      
      if ( parcelableDate != null )
         ret = parcelableDate.getDate();
      
      return ret;
   }
   
   
   
   public final static Long getTime( ParcelableDate parcelableDate )
   {
      return getTime( parcelableDate, null );
   }
   
   
   
   public final static Long getTime( ParcelableDate parcelableDate,
                                     Long defaultValue )
   {
      Long ret = defaultValue;
      
      if ( parcelableDate != null )
         ret = Long.valueOf( parcelableDate.getTime() );
      
      return ret;
   }
   
   
   
   public final static Long getTime( Date date )
   {
      return getTime( date, null );
   }
   
   
   
   public final static Long getTime( Date date, Long defaultValue )
   {
      Long ret = defaultValue;
      
      if ( date != null )
         ret = Long.valueOf( date.getTime() );
      
      return ret;
   }
   
   
   
   public final static EstimateStruct parseEstimated( long millis )
   {
      int days = 0;
      int hours = 0;
      int minutes = 0;
      
      if ( millis > -1 )
      {
         int timeSeconds = (int) ( millis / 1000 );
         
         // Minute is minimal resolution
         if ( timeSeconds >= 60 )
         {
            if ( timeSeconds >= 3600 * 24 )
            {
               days = timeSeconds / 3600 / 24;
               timeSeconds -= days * 3600 * 24;
            }
            
            if ( timeSeconds >= 3600 )
            {
               hours = timeSeconds / 3600;
               timeSeconds -= hours * 3600;
            }
            
            if ( timeSeconds >= 60 )
            {
               minutes = timeSeconds / 60;
               timeSeconds -= minutes * 60;
            }
         }
      }
      
      return new EstimateStruct( days, hours, minutes );
   }
}
