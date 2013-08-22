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

package dev.drsoran.moloko.test.unit.domain.parsing.datetime;

import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.day;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.hour;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.minute;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.month;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.second;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.year;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import org.antlr.v4.runtime.CommonToken;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.MolokoCalenderProvider;
import dev.drsoran.moloko.domain.parsing.datetime.DateTimeEvaluator;
import dev.drsoran.moloko.domain.parsing.datetime.RtmLikeDateTimeEvaluator;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateNumericContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateOnMonthTheXstContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateOnTheXstOfMonthContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.DateOnWeekdayContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeParser.ParseDateTimeContext;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.TestCalendarProvider;


public class RtmLikeDateTimeEvaluatorFixture extends MolokoTestCase
{
   private DateTimeEvaluator dateEvaluator;
   
   private RtmLikeDateTimeEvaluator evaluator;
   
   private MolokoCalendar today;
   
   private MolokoCalendar now;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      
      dateEvaluator = EasyMock.createNiceMock( DateTimeEvaluator.class );
      
      MolokoCalenderProvider calenderProvider = TestCalendarProvider.getJune_10_2010_00_00_00();
      
      today = calenderProvider.getToday().clone();
      now = calenderProvider.getNow().clone();
      now.set( Calendar.HOUR_OF_DAY, 12 );
      
      calenderProvider = TestCalendarProvider.get( now.clone(), today.clone() );
      
      evaluator = new RtmLikeDateTimeEvaluator( dateEvaluator, calenderProvider );
   }
   
   
   
   @Test
   public void testVisitParseDateTime_OnlyTime_Before()
   {
      final MolokoCalendar cal = now.clone();
      cal.add( Calendar.HOUR_OF_DAY, -2 );
      
      final int hour = cal.get( Calendar.HOUR_OF_DAY );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final ParseDateTimeContext ctx = EasyMock.createNiceMock( ParseDateTimeContext.class );
      EasyMock.replay( ctx );
      
      evaluator.visitTime( null );
      evaluator.visitParseDateTime( ctx );
      
      assertThat( cal, is( day( thisDay() + 1 ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
      assertThat( cal, is( hour( hour ) ) );
      assertThat( cal, is( minute( thisMinute() ) ) );
      assertThat( cal, is( second( thisSecond() ) ) );
   }
   
   
   
   @Test
   public void testVisitParseDateTime_OnlyDate_Before()
   {
      final MolokoCalendar cal = now.clone();
      cal.add( Calendar.HOUR_OF_DAY, -2 );
      
      final int hour = cal.get( Calendar.HOUR_OF_DAY );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final ParseDateTimeContext ctx = EasyMock.createNiceMock( ParseDateTimeContext.class );
      EasyMock.replay( ctx );
      
      evaluator.visitDate( null );
      evaluator.visitParseDateTime( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
      assertThat( cal, is( hour( hour ) ) );
      assertThat( cal, is( minute( thisMinute() ) ) );
      assertThat( cal, is( second( thisSecond() ) ) );
   }
   
   
   
   @Test
   public void testVisitParseDateTime_DateAndTime_Before()
   {
      final MolokoCalendar cal = now.clone();
      cal.add( Calendar.HOUR_OF_DAY, -2 );
      
      final int hour = cal.get( Calendar.HOUR_OF_DAY );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final ParseDateTimeContext ctx = EasyMock.createNiceMock( ParseDateTimeContext.class );
      EasyMock.replay( ctx );
      
      evaluator.visitDate( null );
      evaluator.visitTime( null );
      evaluator.visitParseDateTime( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
      assertThat( cal, is( hour( hour ) ) );
      assertThat( cal, is( minute( thisMinute() ) ) );
      assertThat( cal, is( second( thisSecond() ) ) );
   }
   
   
   
   @Test
   public void testVisitParseDateTime_OnlyTime_Now()
   {
      final MolokoCalendar cal = now.clone();
      final int hour = cal.get( Calendar.HOUR_OF_DAY );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final ParseDateTimeContext ctx = EasyMock.createNiceMock( ParseDateTimeContext.class );
      EasyMock.replay( ctx );
      
      evaluator.visitTime( null );
      evaluator.visitParseDateTime( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
      assertThat( cal, is( hour( hour ) ) );
      assertThat( cal, is( minute( thisMinute() ) ) );
      assertThat( cal, is( second( thisSecond() ) ) );
   }
   
   
   
   @Test
   public void testVisitParseDateTime_OnlyTime_After()
   {
      final MolokoCalendar cal = now.clone();
      cal.add( Calendar.HOUR_OF_DAY, 2 );
      
      final int hour = cal.get( Calendar.HOUR_OF_DAY );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final ParseDateTimeContext ctx = EasyMock.createNiceMock( ParseDateTimeContext.class );
      EasyMock.replay( ctx );
      
      evaluator.visitTime( null );
      evaluator.visitParseDateTime( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
      assertThat( cal, is( hour( hour ) ) );
      assertThat( cal, is( minute( thisMinute() ) ) );
      assertThat( cal, is( second( thisSecond() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateNumeric_Year()
   {
      final MolokoCalendar cal = today.clone();
      cal.set( Calendar.YEAR, 2009 );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateNumericContext ctx = EasyMock.createNiceMock( DateNumericContext.class );
      ctx.pt1 = new CommonToken( 0, String.valueOf( cal.get( Calendar.DATE ) ) );
      ctx.pt2 = new CommonToken( 1, String.valueOf( cal.get( Calendar.MONTH ) ) );
      ctx.pt3 = new CommonToken( 2, "2009" );
      EasyMock.replay( ctx );
      
      evaluator.visitDateNumeric( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( 2009 ) ) );
   }
   
   
   
   @Test
   public void testVisitDateNumeric_NoYear_BeforeThisMonth()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, -2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateNumericContext ctx = EasyMock.createNiceMock( DateNumericContext.class );
      ctx.pt1 = new CommonToken( 0, String.valueOf( cal.get( Calendar.DATE ) ) );
      ctx.pt2 = new CommonToken( 1, String.valueOf( cal.get( Calendar.MONTH ) ) );
      ctx.pt3 = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateNumeric( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateNumeric_NoYear_BeforeMonth()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.MONTH, -1 );
      
      final int month = cal.get( Calendar.MONTH );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateNumericContext ctx = EasyMock.createNiceMock( DateNumericContext.class );
      ctx.pt1 = new CommonToken( 0, String.valueOf( cal.get( Calendar.DATE ) ) );
      ctx.pt2 = new CommonToken( 1, String.valueOf( cal.get( Calendar.MONTH ) ) );
      ctx.pt3 = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateNumeric( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( month ) ) );
      assertThat( cal, is( year( thisYear() + 1 ) ) );
   }
   
   
   
   @Test
   public void testVisitDateNumeric_NoYear_Today()
   {
      final MolokoCalendar cal = today.clone();
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateNumericContext ctx = EasyMock.createNiceMock( DateNumericContext.class );
      ctx.pt1 = new CommonToken( 0, String.valueOf( cal.get( Calendar.DATE ) ) );
      ctx.pt2 = new CommonToken( 1, String.valueOf( cal.get( Calendar.MONTH ) ) );
      ctx.pt3 = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateNumeric( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateNumeric_NoYear_After()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, 2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateNumericContext ctx = EasyMock.createNiceMock( DateNumericContext.class );
      ctx.pt1 = new CommonToken( 0, String.valueOf( cal.get( Calendar.DATE ) ) );
      ctx.pt2 = new CommonToken( 1, String.valueOf( cal.get( Calendar.MONTH ) ) );
      ctx.pt3 = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateNumeric( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnTheXstOfMonth_Before_WithYear()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, -2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnTheXstOfMonthContext ctx = EasyMock.createNiceMock( DateOnTheXstOfMonthContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = new CommonToken( 1, String.valueOf( today.get( Calendar.YEAR ) ) );
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnTheXstOfMonth( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnTheXstOfMonth_Before_NoYear_ThisMonth()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, -2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnTheXstOfMonthContext ctx = EasyMock.createNiceMock( DateOnTheXstOfMonthContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnTheXstOfMonth( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnTheXstOfMonth_Before_NoYear()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.MONTH, -1 );
      
      final int month = cal.get( Calendar.MONTH );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnTheXstOfMonthContext ctx = EasyMock.createNiceMock( DateOnTheXstOfMonthContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnTheXstOfMonth( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( month ) ) );
      assertThat( cal, is( year( thisYear() + 1 ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnTheXstOfMonth_Today_NoYear()
   {
      final MolokoCalendar cal = today.clone();
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnTheXstOfMonthContext ctx = EasyMock.createNiceMock( DateOnTheXstOfMonthContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnTheXstOfMonth( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnTheXstOfMonth_After_NoYear()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, 2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnTheXstOfMonthContext ctx = EasyMock.createNiceMock( DateOnTheXstOfMonthContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnTheXstOfMonth( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_Before_WithYear()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, -2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = null;
      ctx.y = new CommonToken( 0, String.valueOf( today.get( Calendar.YEAR ) ) );
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_DayBefore_NoYear()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, -2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = null;
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() + 1 ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_Today_NoYear()
   {
      final MolokoCalendar cal = today.clone();
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = null;
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_DayAfter_NoYear()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, 2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = null;
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_MonthBefore_NoYear()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.MONTH, -1 );
      
      final int month = cal.get( Calendar.MONTH );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = new CommonToken( 0, String.valueOf( month ) );
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( month ) ) );
      assertThat( cal, is( year( thisYear() + 1 ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_MonthToday_NoYear()
   {
      final MolokoCalendar cal = today.clone();
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day( thisDay() ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_MonthAfter_NoYear()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, 2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnWeekday_Before()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, -2 );
      
      final MolokoCalendar nextWeek = cal.clone();
      nextWeek.add( Calendar.WEEK_OF_YEAR, 1 );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnWeekdayContext ctx = EasyMock.createNiceMock( DateOnWeekdayContext.class );
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnWeekday( ctx );
      
      assertThat( cal, is( day( nextWeek.get( Calendar.DATE ) ) ) );
      assertThat( cal, is( month( nextWeek.get( Calendar.MONTH ) ) ) );
      assertThat( cal, is( year( nextWeek.get( Calendar.YEAR ) ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnWeekday_Today()
   {
      final MolokoCalendar cal = today.clone();
      
      final MolokoCalendar nextWeek = cal.clone();
      nextWeek.add( Calendar.WEEK_OF_YEAR, 1 );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnWeekdayContext ctx = EasyMock.createNiceMock( DateOnWeekdayContext.class );
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnWeekday( ctx );
      
      assertThat( cal, is( day( nextWeek.get( Calendar.DATE ) ) ) );
      assertThat( cal, is( month( nextWeek.get( Calendar.MONTH ) ) ) );
      assertThat( cal, is( year( nextWeek.get( Calendar.YEAR ) ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnWeekday_After()
   {
      final MolokoCalendar cal = today.clone();
      cal.add( Calendar.DATE, 2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnWeekdayContext ctx = EasyMock.createNiceMock( DateOnWeekdayContext.class );
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnWeekday( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnWeekday_Sunday()
   {
      final MolokoCalendar cal = today.clone();
      cal.set( Calendar.DAY_OF_WEEK, Calendar.SUNDAY );
      
      assertThat( "Sunday must be the first day of week",
                  cal.getActualMinimum( Calendar.DAY_OF_WEEK ),
                  is( Calendar.SUNDAY ) );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnWeekdayContext ctx = EasyMock.createNiceMock( DateOnWeekdayContext.class );
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnWeekday( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month( thisMonth() ) ) );
      assertThat( cal, is( year( thisYear() ) ) );
   }
   
   
   
   @Test
   public void testVisitDateIn_X_YMWD()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitDateIn_X_YMWD( null ) )
              .andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitDateIn_X_YMWD( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitDateOn()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitDateOn( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitDateOn( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitToday()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitToday( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitToday( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testGetCalendar()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.getCalendar();
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testGetEpochStart()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.getEpochStart() ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.getEpochStart();
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testGetEpochEnd()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.getEpochEnd() ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.getEpochEnd();
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitParseDate()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitParseDate( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitParseDate( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitParseDateWithin()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitParseDateWithin( null ) )
              .andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitParseDateWithin( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitYesterday()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitYesterday( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitYesterday( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitNow()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitNow( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitNow( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisit()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visit( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visit( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitChildren()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitChildren( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitChildren( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitDateIn_X_YMWD_distance()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitDateIn_X_YMWD_distance( null ) )
              .andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitDateIn_X_YMWD_distance( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitDateEndOfTheMonthOrWeek()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitDateEndOfTheMonthOrWeek( null ) )
              .andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitDateEndOfTheMonthOrWeek( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitErrorNode()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitErrorNode( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitErrorNode( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitTomorrow()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitTomorrow( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitTomorrow( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitNever()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitDateNever( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitDateNever( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testVisitTerminal()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.visitTerminal( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitTerminal( null );
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   private int thisMinute()
   {
      return now.get( Calendar.MINUTE );
   }
   
   
   
   private int thisSecond()
   {
      return now.get( Calendar.SECOND );
   }
   
   
   
   private int thisYear()
   {
      return today.get( Calendar.YEAR );
   }
   
   
   
   private int thisMonth()
   {
      return today.get( Calendar.MONTH );
   }
   
   
   
   private int thisDay()
   {
      return today.get( Calendar.DATE );
   }
}
