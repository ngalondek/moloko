/*
 * Copyright (c) 2010 Ronny Röhricht
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import dev.drsoran.moloko.Settings;
import dev.drsoran.rtm.ParcelableDate;


public class MolokoDateUtils
{
   public final static int FORMAT_WITH_YEAR = 1 << 0;
   
   public final static int FORMAT_NUMERIC = 1 << 1;
   
   public final static int FORMAT_SHOW_WEEKDAY = 1 << 2;
   
   public final static int FORMAT_PARSER = FORMAT_WITH_YEAR | FORMAT_NUMERIC
      | ( 1 << 3 );
   
   public final static int FORMAT_ABR_WEEKDAY = 1 << 4;
   
   public final static int FORMAT_ABR_MONTH = 1 << 5;
   
   public final static int FORMAT_ABR_ALL = FORMAT_ABR_WEEKDAY
      | FORMAT_ABR_MONTH;
   
   
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
      return ( getTimespanInDays( when, reference ) < 0 );
   }
   


   public static boolean isAfter( long when, long reference )
   {
      return ( getTimespanInDays( when, reference ) > 0 );
   }
   


   public static int getTimespanInDays( long start, long end )
   {
      final TimeZone timeZone = MolokoApp.getSettings().getTimezone();
      final int offStart = timeZone.getOffset( start ) / 1000; // in sec.
      final int offEnd = timeZone.getOffset( end ) / 1000; // in sec.
      
      return ( Time.getJulianDay( end, offEnd ) - Time.getJulianDay( start,
                                                                     offStart ) );
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
   


   public final static Calendar clearTime( Calendar cal )
   {
      cal.clear( Calendar.HOUR );
      cal.clear( Calendar.HOUR_OF_DAY );
      cal.clear( Calendar.MINUTE );
      cal.clear( Calendar.SECOND );
      cal.clear( Calendar.MILLISECOND );
      cal.clear( Calendar.AM_PM );
      
      return cal;
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
   


   public final static String formatDate( long millis, int dateStyle )
   {
      final TimeZone timeZone = MolokoApp.getSettings().getTimezone();
      final Calendar cal = Calendar.getInstance( timeZone );
      
      cal.setTimeInMillis( millis );
      
      return DateFormat.format( buildPattern( true, false, dateStyle ), cal )
                       .toString();
   }
   


   public final static String formatDate( String pattern,
                                          String value,
                                          int dateStyle )
   {
      final TimeZone timeZone = MolokoApp.getSettings().getTimezone();
      final SimpleDateFormat sdf = new SimpleDateFormat( pattern );
      
      sdf.setTimeZone( timeZone );
      
      try
      {
         sdf.parse( value );
         return DateFormat.format( buildPattern( true, false, dateStyle ),
                                   sdf.getCalendar() ).toString();
      }
      catch ( ParseException e )
      {
         return null;
      }
   }
   


   public final static String formatDateTime( long millis, int dateStyle )
   {
      final TimeZone timeZone = MolokoApp.getSettings().getTimezone();
      final Calendar cal = Calendar.getInstance( timeZone );
      
      cal.setTimeInMillis( millis );
      
      return DateFormat.format( buildPattern( true, true, dateStyle ), cal )
                       .toString();
   }
   


   public final static String formatTime( long millis )
   {
      final TimeZone timeZone = MolokoApp.getSettings().getTimezone();
      final Calendar cal = Calendar.getInstance( timeZone );
      
      cal.setTimeInMillis( millis );
      
      return DateFormat.format( buildPattern( false, true, 0 ), cal )
                       .toString();
   }
   


   public final static Date getDate( ParcelableDate parcelableDate )
   {
      return parcelableDate != null ? parcelableDate.getDate() : null;
   }
   


   public final static Long getTime( ParcelableDate parcelableDate )
   {
      return parcelableDate != null ? parcelableDate.getTime() : null;
   }
   


   public final static Long getTime( Date date )
   {
      return date != null ? date.getTime() : null;
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
   


   private final static String buildPattern( boolean date,
                                             boolean time,
                                             int flags )
   {
      StringBuilder pattern = new StringBuilder();
      
      final Settings settings = MolokoApp.getSettings();
      
      // Date
      if ( date )
      {
         if ( ( flags & FORMAT_SHOW_WEEKDAY ) != 0 )
            pattern.append( ( ( flags & FORMAT_ABR_WEEKDAY ) != 0 ) ? "E, "
                                                                   : "EEEE, " );
         
         // Date EU
         if ( settings.getDateformat() == Settings.DATEFORMAT_EU )
         {
            if ( ( flags & FORMAT_NUMERIC ) != 0 )
               if ( ( flags & FORMAT_WITH_YEAR ) != 0 )
                  pattern.append( "d.M.yyyy" );
               else
                  pattern.append( "d.M" );
            else
            {
               if ( ( flags & FORMAT_ABR_MONTH ) != 0 )
                  pattern.append( "d. MMM" );
               else
                  pattern.append( "d. MMMM" );
               
               if ( ( flags & FORMAT_WITH_YEAR ) != 0 )
                  pattern.append( " yyyy" );
            }
         }
         
         // Date US
         else
         {
            if ( ( flags & FORMAT_NUMERIC ) != 0 )
               if ( ( flags & FORMAT_WITH_YEAR ) != 0 )
                  // the parser needs the EU format. (day first)
                  if ( ( flags & FORMAT_PARSER ) == FORMAT_PARSER )
                     pattern.append( "d/M/yyyy" );
                  else
                     pattern.append( "M/d/yyyy" );
               else
                  pattern.append( "M/d" );
            else
            {
               if ( ( flags & FORMAT_ABR_MONTH ) != 0 )
                  pattern.append( "MMM d" );
               else
                  pattern.append( "MMMM d" );
               
               if ( ( flags & FORMAT_WITH_YEAR ) != 0 )
                  pattern.append( ", yyyy" );
            }
         }
      }
      
      // Time
      if ( time )
      {
         // Time 12
         if ( settings.getTimeformat() == Settings.TIMEFORMAT_12 )
         {
            if ( date )
               pattern.append( ", h:mm a" );
            else
               pattern.append( "h:mm a" );
         }
         
         // Time 24
         else
         {
            if ( date )
               pattern.append( ", k:mm" );
            else
               pattern.append( "k:mm" );
         }
      }
      
      return pattern.toString();
   }
}
