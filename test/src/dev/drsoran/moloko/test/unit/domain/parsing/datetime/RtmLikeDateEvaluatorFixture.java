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

import static dev.drsoran.moloko.test.TestConstants.DATE_TODAY;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.day;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.month;
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
import dev.drsoran.moloko.domain.parsing.datetime.DateEvaluator;
import dev.drsoran.moloko.domain.parsing.datetime.RtmLikeDateEvaluator;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateNumericContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnMonthTheXstContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnTheXstOfMonthContext;
import dev.drsoran.moloko.grammar.antlr.datetime.DateParser.DateOnWeekdayContext;
import dev.drsoran.moloko.test.MolokoTestCase;


public class RtmLikeDateEvaluatorFixture extends MolokoTestCase
{
   private DateEvaluator dateEvaluator;
   
   private RtmLikeDateEvaluator evaluator;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      
      dateEvaluator = EasyMock.createNiceMock( DateEvaluator.class );
      
      final MolokoCalenderProvider calenderProvider = EasyMock.createNiceMock( MolokoCalenderProvider.class );
      EasyMock.expect( calenderProvider.getToday() )
              .andReturn( DATE_TODAY )
              .anyTimes();
      EasyMock.expect( calenderProvider.getNow() )
              .andReturn( DATE_TODAY )
              .anyTimes();
      
      EasyMock.replay( calenderProvider );
      
      evaluator = new RtmLikeDateEvaluator( dateEvaluator, calenderProvider );
   }
   
   
   
   @Test
   public void testVisitDateNumeric_Year()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
      cal.set( Calendar.YEAR, 2010 );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateNumericContext ctx = EasyMock.createNiceMock( DateNumericContext.class );
      ctx.pt3 = new CommonToken( 0, "2010" );
      EasyMock.replay( ctx );
      
      evaluator.visitDateNumeric( ctx );
      
      assertThat( cal, is( day() ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year( 2010 ) ) );
   }
   
   
   
   @Test
   public void testVisitDateNumeric_NoYear_Before()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
      cal.add( Calendar.DATE, -2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateNumericContext ctx = EasyMock.createNiceMock( DateNumericContext.class );
      ctx.pt3 = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateNumeric( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year( DATE_TODAY.get( Calendar.YEAR ) + 1 ) ) );
   }
   
   
   
   @Test
   public void testVisitDateNumeric_NoYear_Today()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateNumericContext ctx = EasyMock.createNiceMock( DateNumericContext.class );
      ctx.pt3 = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateNumeric( ctx );
      
      assertThat( cal, is( day() ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year() ) );
   }
   
   
   
   @Test
   public void testVisitDateNumeric_NoYear_After()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
      cal.add( Calendar.DATE, 2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateNumericContext ctx = EasyMock.createNiceMock( DateNumericContext.class );
      ctx.pt3 = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateNumeric( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year() ) );
   }
   
   
   
   @Test
   public void testVisitDateOnTheXstOfMonth_Before_WithYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
      cal.add( Calendar.DATE, -2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnTheXstOfMonthContext ctx = EasyMock.createNiceMock( DateOnTheXstOfMonthContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = new CommonToken( 1, "2010" );
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnTheXstOfMonth( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year() ) );
   }
   
   
   
   @Test
   public void testVisitDateOnTheXstOfMonth_Before_NoYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
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
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year( DATE_TODAY.get( Calendar.YEAR ) + 1 ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnTheXstOfMonth_Today_NoYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnTheXstOfMonthContext ctx = EasyMock.createNiceMock( DateOnTheXstOfMonthContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnTheXstOfMonth( ctx );
      
      assertThat( cal, is( day() ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year( DATE_TODAY.get( Calendar.YEAR ) ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnTheXstOfMonth_After_NoYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
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
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year( DATE_TODAY.get( Calendar.YEAR ) ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_Before_WithYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
      cal.add( Calendar.DATE, -2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = null;
      ctx.y = new CommonToken( 0, "2010" );
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year() ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_DayBefore_NoYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
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
      assertThat( cal, is( month( DATE_TODAY.get( Calendar.MONTH ) + 1 ) ) );
      assertThat( cal, is( year() ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_Today_NoYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = null;
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day() ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year() ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_DayAfter_NoYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
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
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year() ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_MonthBefore_NoYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
      cal.add( Calendar.DATE, -2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year( DATE_TODAY.get( Calendar.YEAR ) + 1 ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_MonthToday_NoYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnMonthTheXstContext ctx = EasyMock.createNiceMock( DateOnMonthTheXstContext.class );
      ctx.m = new CommonToken( 0, "1" );
      ctx.y = null;
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnMonthTheXst( ctx );
      
      assertThat( cal, is( day() ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year( DATE_TODAY.get( Calendar.YEAR ) ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnMonthTheXst_MonthAfter_NoYear()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
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
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year( DATE_TODAY.get( Calendar.YEAR ) ) ) );
   }
   
   
   
   @Test
   public void testVisitDateOnWeekday_Before()
   {
      final MolokoCalendar cal = DATE_TODAY.clone();
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
      final MolokoCalendar cal = DATE_TODAY.clone();
      
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
      final MolokoCalendar cal = DATE_TODAY.clone();
      cal.add( Calendar.DATE, 2 );
      
      final int day = cal.get( Calendar.DATE );
      
      EasyMock.expect( dateEvaluator.getCalendar() ).andReturn( cal );
      EasyMock.replay( dateEvaluator );
      
      final DateOnWeekdayContext ctx = EasyMock.createNiceMock( DateOnWeekdayContext.class );
      EasyMock.replay( ctx );
      
      evaluator.visitDateOnWeekday( ctx );
      
      assertThat( cal, is( day( day ) ) );
      assertThat( cal, is( month() ) );
      assertThat( cal, is( year() ) );
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
   public void testIsClearTime()
   {
      EasyMock.resetToStrict( dateEvaluator );
      EasyMock.expect( dateEvaluator.isClearTime() ).andReturn( false );
      EasyMock.replay( dateEvaluator );
      
      evaluator.isClearTime();
      
      EasyMock.verify( dateEvaluator );
   }
   
   
   
   @Test
   public void testSetClearTime()
   {
      EasyMock.resetToStrict( dateEvaluator );
      dateEvaluator.setClearTime( true );
      EasyMock.replay( dateEvaluator );
      
      evaluator.setClearTime( true );
      
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
      EasyMock.expect( dateEvaluator.visitNever( null ) ).andReturn( null );
      EasyMock.replay( dateEvaluator );
      
      evaluator.visitNever( null );
      
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
}
