/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.test.grammar.recurrence;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Test;

import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;


public class RecurrenceParserTest_en extends RecurrenceTestBase
{
   @Test
   public void every_year()
   {
      parseRecurrence( "every year",
                       RecurrencePatternParser.VAL_YEARLY_LIT,
                       1,
                       null,
                       null,
                       true );
   }
   
   
   
   @Test
   public void every_yearly_by_month_and_weekday()
   {
      parseRecurrence( "every year on the 1st friday, monday of january",
                       RecurrencePatternParser.VAL_YEARLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "1MO,1FR",
                       RecurrencePatternParser.OP_BYMONTH_LIT,
                       "1",
                       true );
   }
   
   
   
   @Test
   public void every_week_by_day()
   {
      parseRecurrence( "every tuesday",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "TU",
                       true );
   }
   
   
   
   @Test
   public void every_week_by_day_multiple()
   {
      parseRecurrence( "every monday, wednesday",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,WE",
                       true );
   }
   
   
   
   @Test
   public void every_2_weeks()
   {
      parseRecurrence( "every 2 weeks",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       2,
                       null,
                       null,
                       true );
   }
   
   
   
   @Test
   public void every_2_weeks_by_day()
   {
      parseRecurrence( "every 2nd friday",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       2,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "FR",
                       true );
   }
   
   
   
   @Test
   public void every_weekly_by_day_multiple_until() throws ParseException
   {
      parseRecurrence( "every monday, wednesday until 1/10/2010",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,WE",
                       null,
                       null,
                       getRecurrenceDateFormat().format( getDateParse().parse( "1/10/2010" ) ),
                       -1,
                       true );
   }
   
   
   
   @Test
   public void every_weekday()
   {
      parseRecurrence( "every weekday",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,TU,WE,TH,FR",
                       true );
   }
   
   
   
   @Test
   public void every_weekly_by_weekday()
   {
      parseRecurrence( "every 3. tuesday",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       3,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "TU",
                       true );
   }
   
   
   
   @Test
   public void every_day()
   {
      parseRecurrence( "every day",
                       RecurrencePatternParser.VAL_DAILY_LIT,
                       1,
                       null,
                       null,
                       true );
   }
   
   
   
   @Test
   public void every_day_until()
   {
      final Calendar cal = Calendar.getInstance();
      cal.roll( Calendar.DAY_OF_MONTH, true );
      
      cal.set( Calendar.HOUR, 0 );
      cal.set( Calendar.HOUR_OF_DAY, 0 );
      cal.set( Calendar.MINUTE, 0 );
      cal.set( Calendar.SECOND, 0 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      parseRecurrence( "every day until tomorrow",
                       RecurrencePatternParser.VAL_DAILY_LIT,
                       1,
                       null,
                       null,
                       null,
                       null,
                       getRecurrenceDateFormat().format( cal.getTime() ),
                       -1,
                       true );
   }
   
   
   
   @Test
   public void every_day_for()
   {
      parseRecurrence( "every day for 10 times",
                       RecurrencePatternParser.VAL_DAILY_LIT,
                       1,
                       null,
                       null,
                       null,
                       null,
                       null,
                       10,
                       true );
   }
   
   
   
   @Test
   public void every_3_months()
   {
      parseRecurrence( "every 3 months",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       3,
                       null,
                       null,
                       true );
   }
   
   
   
   @Test
   public void after_monthly()
   {
      parseRecurrence( "after 1 month",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       null,
                       null,
                       false );
   }
   
   
   
   @Test
   public void after_monthly_textual()
   {
      parseRecurrence( "after one month",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       null,
                       null,
                       false );
   }
   
   
   
   @Test
   public void after_2_monthly()
   {
      parseRecurrence( "after 2 months",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       2,
                       null,
                       null,
                       false );
   }
   
   
   
   @Test
   public void every_monthly_by_monthday()
   {
      parseRecurrence( "every month on the 4th",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYMONTHDAY_LIT,
                       "4",
                       true );
   }
   
   
   
   @Test
   public void every_monthly_by_monthday_multiple()
   {
      parseRecurrence( "every 1st and 25th",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYMONTHDAY_LIT,
                       "1,25",
                       true );
   }
   
   
   
   @Test
   public void every_monthly_by_weekday()
   {
      parseRecurrence( "every month on the 3rd tuesday",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "3TU",
                       true );
   }
   
   
   
   @Test
   public void every_monthly_by_last_weekday()
   {
      parseRecurrence( "every month on the last monday",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "-1MO",
                       true );
   }
   
   
   
   @Test
   public void every_monthly_by_2nd_last_weekday()
   {
      parseRecurrence( "every month on the 2nd last friday",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "-2FR",
                       true );
   }
   
   
   
   @Test
   public void every_monthly_by_first_weekday()
   {
      parseRecurrence( "every month on the 1st friday",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "1FR",
                       true );
   }
}
