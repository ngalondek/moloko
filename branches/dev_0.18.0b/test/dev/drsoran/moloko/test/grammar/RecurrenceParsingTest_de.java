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

package dev.drsoran.moloko.test.grammar;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.annotation.Values;

import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.recurrence.IRecurrenceParser;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;
import dev.drsoran.moloko.grammar.recurrence.de.RecurrenceParserImpl;
import dev.drsoran.moloko.test.MolokoTestRunner_de;


@RunWith( MolokoTestRunner_de.class )
public class RecurrenceParsingTest_de extends RecurrenceParsingTestBase
{
   
   @Override
   public IRecurrenceParser createRecurrenceParser( IDateTimeParsing dateTimeParsing )
   {
      return new RecurrenceParserImpl( dateTimeParsing );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_year()
   {
      parseRecurrence( "jedes Jahr",
                       RecurrencePatternParser.VAL_YEARLY_LIT,
                       1,
                       null,
                       null,
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_yearly_by_month_and_weekday()
   {
      parseRecurrence( "jedes jahr am 1. freitag, montag im januar",
                       RecurrencePatternParser.VAL_YEARLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "1MO,1FR",
                       RecurrencePatternParser.OP_BYMONTH_LIT,
                       "1",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_week_by_day()
   {
      parseRecurrence( "jeden Dienstag",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "TU",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_week_by_day_multiple()
   {
      parseRecurrence( "jeden montag, Mittwoch",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,WE",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_2_weeks()
   {
      parseRecurrence( "alle 2 wochen",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       2,
                       null,
                       null,
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_2_weeks_by_day()
   {
      parseRecurrence( "jeden 2. freitag",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       2,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "FR",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_weekly_by_day_multiple_until() throws ParseException
   {
      parseRecurrence( "jeden montag, mittwoch bis 10.1.2010",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,WE",
                       null,
                       null,
                       getRecurrenceDateFormat().format( getDateParse().parse( "10.1.2010" ) ),
                       -1,
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_weekday()
   {
      parseRecurrence( "jeden wochentag",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,TU,WE,TH,FR",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_weekly_by_weekday()
   {
      parseRecurrence( "jeden 3. Dienstag",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       3,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "TU",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_day()
   {
      parseRecurrence( "jeden tag",
                       RecurrencePatternParser.VAL_DAILY_LIT,
                       1,
                       null,
                       null,
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_day_until()
   {
      final Calendar cal = Calendar.getInstance();
      cal.add( Calendar.DAY_OF_MONTH, 1 );
      
      cal.set( Calendar.HOUR, 0 );
      cal.set( Calendar.HOUR_OF_DAY, 0 );
      cal.set( Calendar.MINUTE, 0 );
      cal.set( Calendar.SECOND, 0 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      parseRecurrence( "jeden tag bis morgen",
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
   @Values( qualifiers = "de" )
   public void every_day_for()
   {
      parseRecurrence( "jeden tag für 10 mal",
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
   @Values( qualifiers = "de" )
   public void every_3_months()
   {
      parseRecurrence( "alle 3 monate",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       3,
                       null,
                       null,
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void after_monthly()
   {
      parseRecurrence( "nach 1 monat",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       null,
                       null,
                       false );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void after_monthly_textual()
   {
      parseRecurrence( "nach einem monat",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       null,
                       null,
                       false );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void after_2_monthly()
   {
      parseRecurrence( "nach 2 monaten",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       2,
                       null,
                       null,
                       false );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_monthly_by_monthday()
   {
      parseRecurrence( "jeden monat am 4.",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYMONTHDAY_LIT,
                       "4",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_monthly_by_monthday_multiple()
   {
      parseRecurrence( "jeder 1. und 25.",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYMONTHDAY_LIT,
                       "1,25",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_monthly_by_weekday()
   {
      parseRecurrence( "jeden monat am 3. Dienstag",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "3TU",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_monthly_by_last_weekday()
   {
      parseRecurrence( "jeden monat am letzten montag",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "-1MO",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_monthly_by_2nd_last_weekday()
   {
      parseRecurrence( "jeden monat am 2. letzten freitag",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "-2FR",
                       true );
   }
   
   
   
   @Test
   @Values( qualifiers = "de" )
   public void every_monthly_by_first_weekday()
   {
      parseRecurrence( "jeden monat am 1. freitag",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "1FR",
                       true );
   }
}
