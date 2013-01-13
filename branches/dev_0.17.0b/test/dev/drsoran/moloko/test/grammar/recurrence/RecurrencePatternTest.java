package dev.drsoran.moloko.test.grammar.recurrence;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 * Ronny Röhricht - implementation
 */

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.Robolectric;

import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;
import dev.drsoran.moloko.test.MolokoTestRunner_en;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;


@RunWith( MolokoTestRunner_en.class )
public class RecurrencePatternTest extends RecurrenceTestBase
{
   @Test
   public void every_year()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_YEARLY_LIT,
                                            1 ),
                              "every year",
                              true );
   }
   
   
   
   @Test
   public void every_2_years()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_YEARLY_LIT,
                                            2 ),
                              "every 2 years",
                              true );
   }
   
   
   
   @Test
   public void every_year_monthly_byDay()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_YEARLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=1MO,1FR",
                                            RecurrencePatternParser.OP_BYMONTH_LIT
                                               + "=1" ),
                              "every year on the 1st monday, friday in january",
                              true );
   }
   
   
   
   @Test
   public void every_month_byMonthDay()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYMONTHDAY_LIT
                                               + "=1,25" ),
                              "every month on the 1st, 25th",
                              true );
   }
   
   
   
   @Test
   public void every_month_byDay()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=3TU" ),
                              "every month on the 3rd tuesday",
                              true );
   }
   
   
   
   @Test
   public void every_month_by_last_day()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=-1MO" ),
                              "every month on the last monday",
                              true );
   }
   
   
   
   @Test
   public void every_month_by_2nd_last_day()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=-2FR" ),
                              "every month on the 2nd last friday",
                              true );
   }
   
   
   
   @Test
   public void every_month_by_first_day()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=1FR" ),
                              "every month on the 1st friday",
                              true );
   }
   
   
   
   @Test
   public void every_2_weeks()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_WEEKLY_LIT,
                                            2 ),
                              "every 2 weeks",
                              true );
   }
   
   
   
   @Test
   public void every_week_byDay()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_WEEKLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=TU" ),
                              "every week on the tuesday",
                              true );
   }
   
   
   
   @Test
   public void every_week_byDay_multiple()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_WEEKLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=MO,WE" ),
                              "every week on the monday, wednesday",
                              true );
   }
   
   
   
   @Test
   public void every_2_weeks_byDay()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_WEEKLY_LIT,
                                            2,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=FR" ),
                              "every 2 weeks on the friday",
                              true );
   }
   
   
   
   @Test
   public void every_week_byDay_multiple_until() throws ParseException
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_WEEKLY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_BYDAY_LIT
                                               + "=MO,WE",
                                            RecurrencePatternParser.OP_UNTIL_LIT
                                               + "="
                                               + getRecurrenceDateFormat().format( getDateParse().parse( "10/10/2010" ) ) ),
                              "every week on the monday, wednesday until 10/10/2010",
                              true );
   }
   
   
   
   @Test
   public void every_day()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_DAILY_LIT,
                                            1 ),
                              "daily",
                              true );
   }
   
   
   
   @Test
   public void every_day_until()
   {
      final Calendar cal = Calendar.getInstance();
      cal.add( Calendar.DAY_OF_MONTH, 1 );
      
      cal.set( Calendar.HOUR, 0 );
      cal.set( Calendar.HOUR_OF_DAY, 0 );
      cal.set( Calendar.MINUTE, 0 );
      cal.set( Calendar.SECOND, 0 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_DAILY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_UNTIL_LIT
                                               + "="
                                               + getRecurrenceDateFormat().format( cal.getTime() ) ),
                              "daily until "
                                 + getDateParse().format( cal.getTime() ),
                              true );
   }
   
   
   
   @Test
   public void every_day_for()
   {
      parseRecurrencePattern( buildPattern( RecurrencePatternParser.VAL_DAILY_LIT,
                                            1,
                                            RecurrencePatternParser.OP_COUNT_LIT
                                               + "=10" ),
                              "daily for 10 times",
                              true );
   }
   
   
   
   private void parseRecurrencePattern( String recurrencePatternString,
                                        String expectedString,
                                        boolean isEvery )
   {
      
      final String result = RecurrenceParsing.parseRecurrencePattern( Robolectric.application,
                                                                      recurrencePatternString,
                                                                      isEvery );
      if ( result == null )
      {
         Assert.fail( "Parsing '" + recurrencePatternString + "' failed!" );
      }
      
      Assert.assertEquals( expectedString.toLowerCase(), result.toLowerCase() );
   }
   
   
   
   private static String buildPattern( String freq,
                                       int interval,
                                       String... resolution )
   {
      StringBuilder result = new StringBuilder( RecurrencePatternParser.OP_FREQ_LIT );
      
      result.append( "=" ).append( freq ).append( ";" );
      result.append( RecurrencePatternParser.OP_INTERVAL_LIT )
            .append( "=" )
            .append( String.valueOf( interval ) )
            .append( resolution.length > 0 ? ";" : "" );
      
      for ( int i = 0; i < resolution.length; i++ )
      {
         result.append( resolution[ i ] );
         
         if ( i < resolution.length - 1 )
         {
            result.append( ";" );
         }
      }
      
      return result.toString();
   }
}
