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

package dev.drsoran.moloko.test.sources;

import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.*;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.FRIDAY;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.MONDAY;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.OP_BYDAY;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.OP_BYMONTH;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.OP_BYMONTHDAY;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.OP_COUNT;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.OP_FREQ;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.OP_INTERVAL;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.OP_UNTIL;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.VAL_DAILY;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.VAL_MONTHLY;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.VAL_WEEKLY;
import static dev.drsoran.moloko.grammar.antlr.recurrence.RecurrencePatternLexer.VAL_YEARLY;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dev.drsoran.moloko.domain.parsing.recurrence.OperandByDayValue;
import dev.drsoran.moloko.util.Pair;


public class RecurrencePatternCollectorTestDataSource
{
   private final static Date UNTIL_DATE;
   
   static
   {
      final Calendar cal = Calendar.getInstance();
      cal.set( Calendar.YEAR, 2010 );
      cal.set( Calendar.MONTH, Calendar.OCTOBER );
      cal.set( Calendar.DATE, 1 );
      cal.set( Calendar.HOUR_OF_DAY, 15 );
      cal.set( Calendar.MINUTE, 30 );
      cal.set( Calendar.SECOND, 0 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      UNTIL_DATE = cal.getTime();
   }
   
   
   
   public Collection< Object[] > getRecurrenceCollectorTestData()
   {
      final Collection< Object[] > testData = new LinkedList< Object[] >();
      
      addPatternYearly( testData );
      addPatternYearlyAllWeekdays( testData );
      addPatternYearlyWeekdayWithXst( testData );
      addPatternYearlyWeekdayWithMultipleXst( testData );
      addPatternYearlyAllMonths( testData );
      addPatternYearlyCountUntil( testData );
      
      addPatternMonthly( testData );
      addPatternMonthlyWeekday( testData );
      addPatternMonthlyWeekdayXst( testData );
      addPatternMonthlyWeekdayMultipleXst( testData );
      addPatternMonthlyByMonthDayXst( testData );
      addPatternMonthlyByMonthDayMultipleXst( testData );
      addPatternMonthlyCountUntil( testData );
      
      addPatternWeekly( testData );
      addPatternWeeklyWeekday( testData );
      addPatternWeeklyWeekdayXst( testData );
      addPatternWeeklyWeekdayMultipleXst( testData );
      addPatternWeeklyCountUntil( testData );
      
      addPatternDaily( testData );
      addPatternDailyCountUntil( testData );
      
      return testData;
   }
   
   
   
   private void addPatternYearly( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 3; ++i )
      {
         addTestData( new TestData( "FREQ=YEARLY;INTERVAL=" + i,
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_YEARLY )
                                                       .add( OP_INTERVAL, i )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternYearlyCountUntil( Collection< Object[] > testData )
   {
      addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1;BYDAY=MO;BYMONTH=1;UNTIL=20101001T153000",
                                 new TokensBuilder().add( OP_FREQ, VAL_YEARLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          new OperandByDayValue( null,
                                                                                 MONDAY ) )
                                                    .add( OP_BYMONTH, 1 )
                                                    .add( OP_UNTIL, UNTIL_DATE )
                                                    .build() ),
                   testData );
      
      addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1;BYDAY=MO;BYMONTH=1;COUNT=2",
                                 new TokensBuilder().add( OP_FREQ, VAL_YEARLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          new OperandByDayValue( null,
                                                                                 MONDAY ) )
                                                    .add( OP_BYMONTH, 1 )
                                                    .add( OP_COUNT, 2 )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addPatternYearlyAllWeekdays( Collection< Object[] > testData )
   {
      for ( Pair< String, Integer > weekday : getWeekDays() )
      {
         addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1;BYDAY="
                                       + weekday.first + ";BYMONTH=1",
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_YEARLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             new OperandByDayValue( null,
                                                                                    weekday.second ) )
                                                       .add( OP_BYMONTH, 1 )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternYearlyWeekdayWithXst( Collection< Object[] > testData )
   {
      for ( int xst = -4; xst < 11; ++xst )
      {
         addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1;BYDAY=" + xst
                                       + "MO;BYMONTH=1",
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_YEARLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             new OperandByDayValue( xst,
                                                                                    MONDAY ) )
                                                       .add( OP_BYMONTH, 1 )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternYearlyWeekdayWithMultipleXst( Collection< Object[] > testData )
   {
      for ( int xst1 = 1; xst1 < 2; ++xst1 )
      {
         addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1;BYDAY=1MO,2FR;BYMONTH=1",
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_YEARLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             new OperandByDayValue( 1,
                                                                                    MONDAY ),
                                                             new OperandByDayValue( 2,
                                                                                    FRIDAY ) )
                                                       .add( OP_BYMONTH, 1 )
                                                       .build() ),
                      testData );
         
         addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1;BYDAY=1MO,FR;BYMONTH=1",
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_YEARLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             new OperandByDayValue( 1,
                                                                                    MONDAY ),
                                                             new OperandByDayValue( null,
                                                                                    FRIDAY ) )
                                                       .add( OP_BYMONTH, 1 )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternYearlyAllMonths( Collection< Object[] > testData )
   {
      for ( int monthNr = 1; monthNr < 13; ++monthNr )
      {
         addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1;BYDAY=MO;BYMONTH="
                                       + monthNr,
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_YEARLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             new OperandByDayValue( null,
                                                                                    MONDAY ) )
                                                       .add( OP_BYMONTH,
                                                             monthNr )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternMonthly( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 3; ++i )
      {
         addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=" + i,
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_MONTHLY )
                                                       .add( OP_INTERVAL, i )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternMonthlyWeekday( Collection< Object[] > testData )
   {
      addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYDAY=MO",
                                 new TokensBuilder().add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          new OperandByDayValue( null,
                                                                                 MONDAY ) )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addPatternMonthlyWeekdayXst( Collection< Object[] > testData )
   {
      for ( int xst = -4; xst < 11; ++xst )
      {
         addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYDAY=" + xst
                                       + "MO",
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_MONTHLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             new OperandByDayValue( xst,
                                                                                    MONDAY ) )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternMonthlyWeekdayMultipleXst( Collection< Object[] > testData )
   {
      for ( int xst = -4; xst < 11; ++xst )
      {
         addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYDAY=" + xst
                                       + "MO,1FR",
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_MONTHLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             new OperandByDayValue( xst,
                                                                                    MONDAY ),
                                                             new OperandByDayValue( 1,
                                                                                    FRIDAY ) )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternMonthlyByMonthDayXst( Collection< Object[] > testData )
   {
      for ( int xst = -4; xst < 11; ++xst )
      {
         addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=" + xst,
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_MONTHLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYMONTHDAY, xst )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternMonthlyByMonthDayMultipleXst( Collection< Object[] > testData )
   {
      addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=1,2",
                                 new TokensBuilder().add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYMONTHDAY, 1, 2 )
                                                    .build() ), testData );
      
      addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=-1,-2",
                                 new TokensBuilder().add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYMONTHDAY, -1, -2 )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addPatternMonthlyCountUntil( Collection< Object[] > testData )
   {
      addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=1,2;UNTIL=20101001T153000",
                                 new TokensBuilder().add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYMONTHDAY, 1, 2 )
                                                    .add( OP_UNTIL, UNTIL_DATE )
                                                    .build() ),
                   testData );
      
      addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=1,2;COUNT=2",
                                 new TokensBuilder().add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYMONTHDAY, 1, 2 )
                                                    .add( OP_COUNT, 2 )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addPatternWeekly( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 3; ++i )
      {
         addTestData( new TestData( "FREQ=WEEKLY;INTERVAL=" + i,
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_WEEKLY )
                                                       .add( OP_INTERVAL, i )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternWeeklyWeekday( Collection< Object[] > testData )
   {
      addTestData( new TestData( "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO",
                                 new TokensBuilder().add( OP_FREQ, VAL_WEEKLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          new OperandByDayValue( null,
                                                                                 MONDAY ) )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addPatternWeeklyWeekdayXst( Collection< Object[] > testData )
   {
      for ( int xst = -4; xst < 11; ++xst )
      {
         addTestData( new TestData( "FREQ=WEEKLY;INTERVAL=1;BYDAY=" + xst
                                       + "MO",
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_WEEKLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             new OperandByDayValue( xst,
                                                                                    MONDAY ) )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternWeeklyWeekdayMultipleXst( Collection< Object[] > testData )
   {
      for ( int xst = -4; xst < 11; ++xst )
      {
         addTestData( new TestData( "FREQ=WEEKLY;INTERVAL=1;BYDAY=" + xst
                                       + "MO,1FR",
                                    new TokensBuilder().add( OP_FREQ,
                                                             VAL_WEEKLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             new OperandByDayValue( xst,
                                                                                    MONDAY ),
                                                             new OperandByDayValue( 1,
                                                                                    FRIDAY ) )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternWeeklyCountUntil( Collection< Object[] > testData )
   {
      addTestData( new TestData( "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO;UNTIL=20101001T153000",
                                 new TokensBuilder().add( OP_FREQ, VAL_WEEKLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          new OperandByDayValue( null,
                                                                                 MONDAY ) )
                                                    .add( OP_UNTIL, UNTIL_DATE )
                                                    .build() ),
                   testData );
      
      addTestData( new TestData( "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO;COUNT=2",
                                 new TokensBuilder().add( OP_FREQ, VAL_WEEKLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          new OperandByDayValue( null,
                                                                                 MONDAY ) )
                                                    .add( OP_COUNT, 2 )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addPatternDaily( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 3; ++i )
      {
         addTestData( new TestData( "FREQ=DAILY;INTERVAL=" + i,
                                    new TokensBuilder().add( OP_FREQ, VAL_DAILY )
                                                       .add( OP_INTERVAL, i )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addPatternDailyCountUntil( Collection< Object[] > testData )
   {
      addTestData( new TestData( "FREQ=DAILY;INTERVAL=1;BYDAY=MO;UNTIL=20101001T153000",
                                 new TokensBuilder().add( OP_FREQ, VAL_DAILY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          new OperandByDayValue( null,
                                                                                 MONDAY ) )
                                                    .add( OP_UNTIL, UNTIL_DATE )
                                                    .build() ),
                   testData );
      
      addTestData( new TestData( "FREQ=DAILY;INTERVAL=1;BYDAY=MO;COUNT=2",
                                 new TokensBuilder().add( OP_FREQ, VAL_DAILY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          new OperandByDayValue( null,
                                                                                 MONDAY ) )
                                                    .add( OP_COUNT, 2 )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addTestData( TestData testData, Collection< Object[] > testDatas )
   {
      testDatas.add( new Object[]
      { testData } );
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   private Iterable< Pair< String, Integer > > getWeekDays()
   {
      return Arrays.asList( new Pair< String, Integer >( "MO", MONDAY ),
                            new Pair< String, Integer >( "TU", TUESDAY ),
                            new Pair< String, Integer >( "WE", WEDNESDAY ),
                            new Pair< String, Integer >( "TH", THURSDAY ),
                            new Pair< String, Integer >( "FR", FRIDAY ),
                            new Pair< String, Integer >( "SA", SATURDAY ),
                            new Pair< String, Integer >( "SU", SUNDAY ) );
   }
   
   
   public final static class TestData
   {
      public final String pattern;
      
      public final Map< Integer, List< Object >> expectedTokens;
      
      
      
      public TestData( String pattern,
         Map< Integer, List< Object >> expectedTokens )
      {
         this.pattern = pattern;
         this.expectedTokens = expectedTokens;
      }
      
      
      
      @Override
      public String toString()
      {
         return pattern;
      }
   }
   
   
   private final class TokensBuilder
   {
      private final Map< Integer, List< Object >> expectedTokens = new LinkedHashMap< Integer, List< Object > >();
      
      
      
      public TokensBuilder add( int token, Object value )
      {
         expectedTokens.put( token, Collections.singletonList( value ) );
         return this;
      }
      
      
      
      public TokensBuilder add( int token, Object... values )
      {
         expectedTokens.put( token, Arrays.asList( values ) );
         return this;
      }
      
      
      
      public Map< Integer, List< Object >> build()
      {
         return expectedTokens;
      }
   }
}
