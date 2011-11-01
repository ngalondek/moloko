/*
 * Copyright (c) 2011 Ronny Röhricht
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

import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;

import com.mdt.rtm.data.RtmData;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.rtm.ParcelableDate;


public class MolokoDateUtils
{
   public final static int FORMAT_WITH_YEAR = DateUtils.FORMAT_SHOW_YEAR;
   
   public final static int FORMAT_NUMERIC = DateUtils.FORMAT_NUMERIC_DATE;
   
   public final static int FORMAT_SHOW_WEEKDAY = DateUtils.FORMAT_SHOW_WEEKDAY;
   
   public final static int FORMAT_ABR_WEEKDAY = DateUtils.FORMAT_ABBREV_WEEKDAY;
   
   public final static int FORMAT_ABR_MONTH = DateUtils.FORMAT_ABBREV_MONTH;
   
   public final static int FORMAT_ABR_ALL = DateUtils.FORMAT_ABBREV_ALL;
   
   public final static int FORMAT_PARSER = FORMAT_NUMERIC;
   
   
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
      final Time t = new Time( MolokoApp.getSettings().getTimezone().getID() );
      t.setToNow();
      return t;
   }
   
   
   
   public final static Time newTime( long millis )
   {
      final Time t = new Time( MolokoApp.getSettings().getTimezone().getID() );
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
      final TimeZone timeZone = MolokoApp.getSettings().getTimezone();
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
   
   
   
   public static String getNumericDateFormatPattern( Context context,
                                                     boolean withYear )
   {
      return getNumericDatePatternForSettings( context,
                                               withYear ? FORMAT_WITH_YEAR : 0 );
   }
   
   
   
   public final static String formatDate( Context context,
                                          long millis,
                                          int dateStyle )
   {
      return DateFormat.format( buildPattern( context, true, false, dateStyle ),
                                millis )
                       .toString();
   }
   
   
   
   public static String formatDateNumeric( Context context,
                                           String part1,
                                           String part2 )
   {
      return formatDateNumeric( context, part1, part2, null );
   }
   
   
   
   public static String formatDateNumeric( Context context,
                                           String part1,
                                           String part2,
                                           String part3 )
   {
      if ( part3 == null )
         return context.getString( R.string.numeric_date_pattern, part1, part2 );
      else
         return context.getString( R.string.numeric_date_pattern_year,
                                   part1,
                                   part2,
                                   part3 );
   }
   
   
   
   public final static String formatDateTime( Context context,
                                              long millis,
                                              int dateStyle )
   {
      return DateFormat.format( buildPattern( context, true, true, dateStyle ),
                                millis ).toString();
   }
   
   
   
   public final static String formatTime( Context context, long millis )
   {
      return DateFormat.format( buildPattern( context, false, true, 0 ), millis )
                       .toString();
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
   
   
   
   public final static String formatEstimated( Context context, long millis )
   {
      final Resources res = context.getResources();
      
      if ( millis > -1 )
      {
         final EstimateStruct estimateStruct = parseEstimated( millis );
         final StringBuilder stringBuilder = new StringBuilder();
         
         if ( estimateStruct.days > 0 )
         {
            stringBuilder.append( estimateStruct.days )
                         .append( " " )
                         .append( res.getQuantityString( R.plurals.g_day,
                                                         estimateStruct.days ) );
         }
         
         if ( estimateStruct.hours > 0 )
         {
            if ( stringBuilder.length() > 0 )
               stringBuilder.append( ", " );
            
            stringBuilder.append( estimateStruct.hours )
                         .append( " " )
                         .append( res.getQuantityString( R.plurals.g_hour,
                                                         estimateStruct.hours ) );
         }
         
         if ( estimateStruct.minutes > 0 )
         {
            if ( stringBuilder.length() > 0 )
               stringBuilder.append( ", " );
            
            stringBuilder.append( estimateStruct.minutes )
                         .append( " " )
                         .append( res.getQuantityString( R.plurals.g_minute,
                                                         estimateStruct.minutes ) );
         }
         
         if ( stringBuilder.length() == 0 )
            stringBuilder.append( 0 )
                         .append( " " )
                         .append( res.getQuantityString( R.plurals.g_minute, 0 ) );
         
         return stringBuilder.toString();
      }
      else
      {
         return res.getString( R.string.phr_nothing );
      }
   }
   
   
   
   public final static String buildPattern( Context context,
                                            boolean date,
                                            boolean time,
                                            int flags )
   {
      String template = Strings.EMPTY_STRING;
      
      // Date
      if ( date )
      {
         if ( ( flags & FORMAT_NUMERIC ) != 0 )
         {
            template = getNumericDatePatternForSettings( context, flags );
         }
         else
         {
            final String monthPattern = ( ( flags & FORMAT_ABR_MONTH ) != 0 )
                                                                             ? "MMM"
                                                                             : "MMMM";
            if ( ( flags & FORMAT_SHOW_WEEKDAY ) != 0 )
            {
               final String weekDayPattern = ( ( flags & FORMAT_ABR_WEEKDAY ) != 0 )
                                                                                    ? "EEE"
                                                                                    : "EEEE";
               if ( ( flags & FORMAT_WITH_YEAR ) != 0 )
                  template = context.getString( R.string.date_with_weekday_text_month_year,
                                                weekDayPattern,
                                                monthPattern );
               else
                  template = context.getString( R.string.date_with_weekday_text_month,
                                                weekDayPattern,
                                                monthPattern );
            }
            else
            {
               if ( ( flags & FORMAT_WITH_YEAR ) != 0 )
                  template = context.getString( R.string.date_with_text_month_year,
                                                monthPattern );
               else
                  template = context.getString( R.string.date_with_text_month,
                                                monthPattern );
            }
         }
      }
      
      // Time
      if ( time )
      {
         final String timePattern = ( MolokoApp.getSettings().Is24hTimeformat() )
                                                                                 ? context.getString( R.string.time_pattern_24 )
                                                                                 : context.getString( R.string.time_pattern_12 );
         
         if ( date )
         {
            final String dateTemplate = template;
            template = context.getString( R.string.date_with_time_pattern,
                                          timePattern,
                                          dateTemplate );
         }
         else
         {
            template = timePattern;
         }
      }
      
      return template;
   }
   
   
   
   private static String getNumericDatePatternForSettings( Context context,
                                                           int flags )
   {
      final String numericDatePattern;
      
      final boolean isWithYear = ( ( flags & FORMAT_WITH_YEAR ) != 0 );
      final char[] dateFormatOrder = DateFormat.getDateFormatOrder( context );
      
      final String[] expandedDateFormatOrder = expandDateFormatOrder( dateFormatOrder,
                                                                      isWithYear );
      
      if ( isWithYear )
      {
         numericDatePattern = context.getString( R.string.numeric_date_pattern_year,
                                                 expandedDateFormatOrder[ 0 ],
                                                 expandedDateFormatOrder[ 1 ],
                                                 expandedDateFormatOrder[ 2 ] );
      }
      else
      {
         numericDatePattern = context.getString( R.string.numeric_date_pattern,
                                                 expandedDateFormatOrder[ 0 ],
                                                 expandedDateFormatOrder[ 1 ] );
      }
      
      return numericDatePattern;
   }
   
   
   
   private final static String[] expandDateFormatOrder( char[] dateFormatOrder,
                                                        boolean withYear )
   {
      final String[] expanded = new String[ withYear ? 3 : 2 ];
      
      int expandedIndex = 0;
      for ( int i = 0; i < dateFormatOrder.length; i++ )
      {
         final char dateFormatItem = dateFormatOrder[ i ];
         
         if ( dateFormatOrder[ i ] != DateFormat.YEAR )
            expanded[ expandedIndex++ ] = String.valueOf( dateFormatItem );
         else if ( withYear )
            expanded[ expandedIndex++ ] = String.format( "%c%c%c%c",
                                                         dateFormatItem,
                                                         dateFormatItem,
                                                         dateFormatItem,
                                                         dateFormatItem );
      }
      
      return expanded;
   }
}
