/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.format;

import android.content.Context;
import android.content.res.Resources;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.R;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.MolokoDateUtils.EstimateStruct;
import dev.drsoran.moloko.util.Strings;


public final class MolokoDateFormatter
{
   public final static int NO_FLAGS = 0;
   
   public final static int FORMAT_WITH_YEAR = DateUtils.FORMAT_SHOW_YEAR;
   
   public final static int FORMAT_NUMERIC = DateUtils.FORMAT_NUMERIC_DATE;
   
   public final static int FORMAT_SHOW_WEEKDAY = DateUtils.FORMAT_SHOW_WEEKDAY;
   
   public final static int FORMAT_ABR_WEEKDAY = DateUtils.FORMAT_ABBREV_WEEKDAY;
   
   public final static int FORMAT_ABR_MONTH = DateUtils.FORMAT_ABBREV_MONTH;
   
   public final static int FORMAT_ABR_ALL = DateUtils.FORMAT_ABBREV_ALL;
   
   public final static int FORMAT_PARSER = FORMAT_NUMERIC;
   
   
   
   public final static String getNumericDateFormatPattern( Context context,
                                                           boolean withYear )
   {
      return getNumericDatePatternForSettings( context,
                                               withYear ? FORMAT_WITH_YEAR : 0 );
   }
   
   
   
   public final static char[] getDateFormatOrder( Context context )
   {
      return DateFormat.getDateFormatOrder( context );
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
   
   
   
   public final static String formatEstimated( Context context, long millis )
   {
      final Resources res = context.getResources();
      
      if ( millis > -1 )
      {
         final EstimateStruct estimateStruct = MolokoDateUtils.parseEstimated( millis );
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
         final String timePattern = ( MolokoApp.getSettings( context ).Is24hTimeformat() )
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
      
      final String[] expandedDateFormatOrder = expandDateFormatOrder( context,
                                                                      dateFormatOrder,
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
   
   
   
   private final static String[] expandDateFormatOrder( Context context,
                                                        char[] dateFormatOrder,
                                                        boolean withYear )
   {
      final String[] expanded = new String[ withYear ? 3 : 2 ];
      final Resources resources = context.getResources();
      
      int expandedIndex = 0;
      for ( int i = 0; i < dateFormatOrder.length; i++ )
      {
         final char dateFormatItem = dateFormatOrder[ i ];
         
         switch ( dateFormatItem )
         {
            case DateFormat.DATE:
               expanded[ expandedIndex++ ] = resources.getString( R.string.numeric_date_format_day );
               break;
            case DateFormat.MONTH:
               expanded[ expandedIndex++ ] = resources.getString( R.string.numeric_date_format_month );
               break;
            case DateFormat.YEAR:
               if ( withYear )
               {
                  expanded[ expandedIndex++ ] = resources.getString( R.string.numeric_date_format_year );
               }
               break;
            default :
               break;
         }
      }
      
      return expanded;
   }
}
