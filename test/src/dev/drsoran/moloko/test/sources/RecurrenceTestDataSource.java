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

import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.DATE_PATTERN;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.IS_EVERY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYDAY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYMONTH;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_BYMONTHDAY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_COUNT;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_FREQ;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_INTERVAL;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.OP_UNTIL;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.VAL_DAILY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.VAL_MONTHLY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.VAL_WEEKLY;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.VAL_YEARLY;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.IDateFormatter;
import dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternOperatorComp;
import dev.drsoran.moloko.test.langs.IRecurrenceParserTestLanguage;
import dev.drsoran.moloko.util.Strings;


public class RecurrenceTestDataSource
{
   public final static MolokoCalendar UNTIL_DATE;
   
   private final static String UNTIL_DATE_PATTERN;
   
   private final IRecurrenceParserTestLanguage language;
   
   private final IDateFormatter dateFormatter;
   
   static
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();
      cal.set( Calendar.YEAR, 2010 );
      cal.set( Calendar.MONTH, Calendar.OCTOBER );
      cal.set( Calendar.DATE, 1 );
      cal.set( Calendar.HOUR_OF_DAY, 15 );
      cal.set( Calendar.MINUTE, 30 );
      cal.set( Calendar.SECOND, 0 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      UNTIL_DATE = cal;
      UNTIL_DATE_PATTERN = new SimpleDateFormat( DATE_PATTERN ).format( cal.getTime() );
   }
   
   
   
   public RecurrenceTestDataSource( IRecurrenceParserTestLanguage language,
      IDateFormatter dateFormatter )
   {
      this.language = language;
      this.dateFormatter = dateFormatter;
   }
   
   
   
   public Collection< Object[] > getTestData()
   {
      final Collection< Object[] > testData = new LinkedList< Object[] >();
      
      addInterval0( testData );
      addIntervalNeg( testData );
      addXst0( testData );
      addXstNeg( testData );
      // addCount0( testData );
      // addCountNeg( testData );
      
      addFreqConstant( testData );
      addWeekdayBusinessDays( testData );
      addWeekdayWeekend( testData );
      addSentenceFreq( testData );
      addSentenceOnXst( testData );
      addSentenceOnMultipleXst( testData );
      addSentenceOnWeekdays( testData );
      addSentenceOnMultipleWeekdays( testData );
      addSentenceOnXstWeekdays( testData );
      addSentenceOnMultipleXstWeekdays( testData );
      addSentenceOnXstWeekdaysOfMonth( testData );
      addSentenceOnMultipleXstWeekdaysOfMonth( testData );
      
      return testData;
   }
   
   
   
   private void addInterval0( Collection< Object[] > testData )
   {
      addTestData( new TestData( getEvery()
                                    + " 0 "
                                    + language.getYearLiterals()
                                              .iterator()
                                              .next(),
                                 new ResultBuilder().add( IS_EVERY, true )
                                                    .add( OP_FREQ, VAL_YEARLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addIntervalNeg( Collection< Object[] > testData )
   {
      addTestData( new TestData( getEvery()
                                    + " -3 "
                                    + language.getYearLiterals()
                                              .iterator()
                                              .next(),
                                 new ResultBuilder().add( IS_EVERY, true )
                                                    .add( OP_FREQ, VAL_YEARLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addXst0( Collection< Object[] > testData )
   {
      addTestData( new TestData( getOnThe() + " 0 ",
                                 new ResultBuilder().add( IS_EVERY, false )
                                                    .add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYMONTHDAY,
                                                          String.valueOf( 1 ) )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addXstNeg( Collection< Object[] > testData )
   {
      addTestData( new TestData( getOnThe() + " -1 ",
                                 new ResultBuilder().add( IS_EVERY, false )
                                                    .add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYMONTHDAY,
                                                          String.valueOf( 1 ) )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addFreqConstant( Collection< Object[] > testData )
   {
      for ( String daily : language.getDaily() )
      {
         addTestData( new TestData( daily,
                                    new ResultBuilder().add( IS_EVERY, true )
                                                       .add( OP_FREQ, VAL_DAILY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .build() ),
                      testData );
      }
      
      final String daily = language.getDaily().iterator().next();
      
      addTestData( new TestData( getAfter() + " 3 " + daily,
                                 new ResultBuilder().add( IS_EVERY, true )
                                                    .add( OP_FREQ, VAL_DAILY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .build() ),
                   testData );
      
      addUntilAndCount( testData,
                        daily,
                        new ResultBuilder().add( IS_EVERY, true )
                                           .add( OP_FREQ, VAL_DAILY )
                                           .add( OP_INTERVAL, 1 ) );
      
      for ( String biweekly : language.getBiweekly() )
      {
         addTestData( new TestData( biweekly,
                                    new ResultBuilder().add( IS_EVERY, true )
                                                       .add( OP_FREQ,
                                                             VAL_WEEKLY )
                                                       .add( OP_INTERVAL, 2 )
                                                       .build() ),
                      testData );
      }
      
      if ( !language.getBiweekly().isEmpty() )
      {
         final String biweekly = language.getBiweekly().iterator().next();
         
         addTestData( new TestData( getAfter() + " 3 " + biweekly,
                                    new ResultBuilder().add( IS_EVERY, true )
                                                       .add( OP_FREQ,
                                                             VAL_WEEKLY )
                                                       .add( OP_INTERVAL, 2 )
                                                       .build() ),
                      testData );
         
         addUntilAndCount( testData,
                           biweekly,
                           new ResultBuilder().add( IS_EVERY, true )
                                              .add( OP_FREQ, VAL_WEEKLY )
                                              .add( OP_INTERVAL, 2 ) );
      }
   }
   
   
   
   private void addWeekdayWeekend( Collection< Object[] > testData )
   {
      for ( String weekendDay : language.getWeekendLiterals() )
      {
         addTestData( new TestData( getEvery() + " " + weekendDay,
                                    new ResultBuilder().add( IS_EVERY, true )
                                                       .add( OP_FREQ,
                                                             VAL_WEEKLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             getWeekdayPattern( 6 ),
                                                             getWeekdayPattern( 7 ) )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addWeekdayBusinessDays( Collection< Object[] > testData )
   {
      for ( String buisDay : language.getBusinessDayLiterals() )
      {
         addTestData( new TestData( getEvery() + " " + buisDay,
                                    new ResultBuilder().add( IS_EVERY, true )
                                                       .add( OP_FREQ,
                                                             VAL_WEEKLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             getWeekdayPattern( 1 ),
                                                             getWeekdayPattern( 2 ),
                                                             getWeekdayPattern( 3 ),
                                                             getWeekdayPattern( 4 ),
                                                             getWeekdayPattern( 5 ) )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addSentenceFreq( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 11; ++i )
      {
         // Every
         for ( String every : language.getEvery() )
         {
            for ( String year : language.getYearLiterals() )
            {
               addFreq( testData, every, true, year, i, VAL_YEARLY );
            }
            
            for ( String month : language.getMonthLiterals() )
            {
               addFreq( testData, every, true, month, i, VAL_MONTHLY );
            }
            
            for ( String week : language.getWeekLiterals() )
            {
               addFreq( testData, every, true, week, i, VAL_WEEKLY );
            }
            
            for ( String day : language.getDayLiterals() )
            {
               addFreq( testData, every, true, day, i, VAL_DAILY );
            }
         }
         
         // After
         for ( String after : language.getAfter() )
         {
            for ( String year : language.getYearLiterals() )
            {
               addFreq( testData, after, false, year, i, VAL_YEARLY );
            }
            
            for ( String month : language.getMonthLiterals() )
            {
               addFreq( testData, after, false, month, i, VAL_MONTHLY );
            }
            
            for ( String week : language.getWeekLiterals() )
            {
               addFreq( testData, after, false, week, i, VAL_WEEKLY );
            }
            
            for ( String day : language.getDayLiterals() )
            {
               addFreq( testData, after, false, day, i, VAL_DAILY );
            }
         }
      }
   }
   
   
   
   private void addSentenceOnXst( Collection< Object[] > testData )
   {
      for ( String onThe : language.getOnThe() )
      {
         for ( int xst = 1; xst < 6; ++xst )
         {
            for ( String xstStr : language.getXst( xst ) )
            {
               addTestData( new TestData( onThe + xstStr,
                                          new ResultBuilder().add( IS_EVERY,
                                                                   false )
                                                             .add( OP_FREQ,
                                                                   VAL_MONTHLY )
                                                             .add( OP_INTERVAL,
                                                                   1 )
                                                             .add( OP_BYMONTHDAY,
                                                                   String.valueOf( xst ) )
                                                             .build() ),
                            testData );
            }
         }
      }
      
      for ( String freq : getFreqs() )
      {
         addTestData( new TestData( freq + " " + getOnThe() + getXst( 1 ),
                                    new ResultBuilder().add( IS_EVERY, false )
                                                       .add( OP_FREQ,
                                                             VAL_MONTHLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYMONTHDAY,
                                                             String.valueOf( 1 ) )
                                                       .build() ),
                      testData );
      }
      
      addUntilAndCount( testData,
                        getOnThe() + getXst( 1 ),
                        new ResultBuilder().add( IS_EVERY, false )
                                           .add( OP_FREQ, VAL_MONTHLY )
                                           .add( OP_INTERVAL, 1 )
                                           .add( OP_BYMONTHDAY,
                                                 String.valueOf( 1 ) ) );
   }
   
   
   
   private void addSentenceOnMultipleXst( Collection< Object[] > testData )
   {
      for ( String separator : language.getSeparators() )
      {
         addTestData( new TestData( getOnThe() + getXst( 1 ) + separator
                                       + getXst( 3 ),
                                    new ResultBuilder().add( IS_EVERY, false )
                                                       .add( OP_FREQ,
                                                             VAL_MONTHLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYMONTHDAY,
                                                             String.valueOf( 1 ),
                                                             String.valueOf( 3 ) )
                                                       .build() ),
                      testData );
      }
      
      addUntilAndCount( testData,
                        getOnThe() + getXst( 1 ) + getSeparator() + getXst( 3 ),
                        new ResultBuilder().add( IS_EVERY, false )
                                           .add( OP_FREQ, VAL_MONTHLY )
                                           .add( OP_INTERVAL, 1 )
                                           .add( OP_BYMONTHDAY,
                                                 String.valueOf( 1 ),
                                                 String.valueOf( 3 ) ) );
   }
   
   
   
   private void addSentenceOnWeekdays( Collection< Object[] > testData )
   {
      for ( String onThe : language.getOnThe() )
      {
         for ( int weekdayNr = 1; weekdayNr < 8; ++weekdayNr )
         {
            for ( String weekday : language.getWeekdayStrings( weekdayNr ) )
            {
               addTestData( new TestData( onThe + weekday,
                                          new ResultBuilder().add( IS_EVERY,
                                                                   false )
                                                             .add( OP_FREQ,
                                                                   VAL_WEEKLY )
                                                             .add( OP_INTERVAL,
                                                                   1 )
                                                             .add( OP_BYDAY,
                                                                   getWeekdayPattern( weekdayNr ) )
                                                             .build() ),
                            testData );
            }
         }
      }
      
      for ( String freq : getFreqs() )
      {
         addTestData( new TestData( freq + " " + getOnThe()
                                       + getWeekdayString( 1 ),
                                    new ResultBuilder().add( IS_EVERY, false )
                                                       .add( OP_FREQ,
                                                             VAL_WEEKLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             getWeekdayPattern( 1 ) )
                                                       .build() ),
                      testData );
      }
      
      addUntilAndCount( testData,
                        getOnThe() + getWeekdayString( 1 ),
                        new ResultBuilder().add( IS_EVERY, false )
                                           .add( OP_FREQ, VAL_WEEKLY )
                                           .add( OP_INTERVAL, 1 )
                                           .add( OP_BYDAY,
                                                 getWeekdayPattern( 1 ) ) );
   }
   
   
   
   private void addSentenceOnMultipleWeekdays( Collection< Object[] > testData )
   {
      for ( String separator : language.getSeparators() )
      {
         addTestData( new TestData( getOnThe() + getWeekdayString( 1 )
                                       + separator + getWeekdayString( 3 ),
                                    new ResultBuilder().add( IS_EVERY, false )
                                                       .add( OP_FREQ,
                                                             VAL_WEEKLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             getWeekdayPattern( 1 ),
                                                             getWeekdayPattern( 3 ) )
                                                       .build() ),
                      testData );
      }
      
      addUntilAndCount( testData,
                        getOnThe() + getWeekdayString( 1 ) + getSeparator()
                           + getWeekdayString( 3 ),
                        new ResultBuilder().add( IS_EVERY, false )
                                           .add( OP_FREQ, VAL_WEEKLY )
                                           .add( OP_INTERVAL, 1 )
                                           .add( OP_BYDAY,
                                                 getWeekdayPattern( 1 ),
                                                 getWeekdayPattern( 3 ) ) );
   }
   
   
   
   private void addSentenceOnXstWeekdays( Collection< Object[] > testData )
   {
      addTestData( new TestData( getOnThe() + getXst( 1 ) + " "
                                    + getWeekdayString( 1 ),
                                 new ResultBuilder().add( IS_EVERY, false )
                                                    .add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          getExtWeekdayPattern( 1 ) )
                                                    .build() ),
                   testData );
      
      for ( String last : language.getLast() )
      {
         for ( int i = 0; i < 6; ++i )
         {
            final String xstStr = i == 0 ? Strings.EMPTY_STRING : getXst( i );
            
            addTestData( new TestData( getOnThe() + xstStr + " " + last + " "
                                          + getWeekdayString( 1 ),
                                       new ResultBuilder().add( IS_EVERY, false )
                                                          .add( OP_FREQ,
                                                                VAL_MONTHLY )
                                                          .add( OP_INTERVAL, 1 )
                                                          .add( OP_BYDAY,
                                                                getExtWeekdayPattern( i == 0
                                                                                            ? -1
                                                                                            : -i,
                                                                                      1 ) )
                                                          .build() ),
                         testData );
         }
      }
      
      addTestData( new TestData( getOnThe() + getXst( 1 ) + " "
                                    + getWeekdayString( 1 ) + getSeparator()
                                    + getWeekdayString( 3 ),
                                 new ResultBuilder().add( IS_EVERY, false )
                                                    .add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          getExtWeekdayPattern( 1 ),
                                                          getExtWeekdayPattern( 1,
                                                                                3 ) )
                                                    .build() ),
                   testData );
      
      for ( String freq : getFreqs() )
      {
         addTestData( new TestData( freq + " " + getOnThe() + getXst( 1 ) + " "
                                       + getWeekdayString( 1 ),
                                    new ResultBuilder().add( IS_EVERY, false )
                                                       .add( OP_FREQ,
                                                             VAL_MONTHLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             getExtWeekdayPattern( 1 ) )
                                                       .build() ),
                      testData );
      }
      
      addUntilAndCount( testData,
                        getOnThe() + getXst( 1 ) + " " + getWeekdayString( 1 ),
                        new ResultBuilder().add( IS_EVERY, false )
                                           .add( OP_FREQ, VAL_MONTHLY )
                                           .add( OP_INTERVAL, 1 )
                                           .add( OP_BYDAY,
                                                 getExtWeekdayPattern( 1 ) ) );
   }
   
   
   
   private void addSentenceOnMultipleXstWeekdays( Collection< Object[] > testData )
   {
      addTestData( new TestData( getOnThe() + getXst( 1 ) + getSeparator()
                                    + getXst( 3 ) + " " + getWeekdayString( 1 ),
                                 new ResultBuilder().add( IS_EVERY, false )
                                                    .add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY,
                                                          getExtWeekdayPattern( 3,
                                                                                1 ) )
                                                    .build() ),
                   testData );
      
      addUntilAndCount( testData,
                        getOnThe() + getXst( 1 ) + getSeparator() + getXst( 3 )
                           + " " + getWeekdayString( 1 ),
                        new ResultBuilder().add( IS_EVERY, false )
                                           .add( OP_FREQ, VAL_MONTHLY )
                                           .add( OP_INTERVAL, 1 )
                                           .add( OP_BYDAY,
                                                 getExtWeekdayPattern( 3, 1 ) ) );
   }
   
   
   
   private void addSentenceOnXstWeekdaysOfMonth( Collection< Object[] > testData )
   {
      for ( String inOf : language.getInOfMonth() )
      {
         for ( int i = 1; i < 13; i++ )
         {
            for ( String month : language.getMonthStrings( i ) )
            {
               addTestData( new TestData( getOnThe() + getXst( 1 ) + " "
                                             + getWeekdayString( 1 ) + inOf
                                             + " " + month,
                                          new ResultBuilder().add( IS_EVERY,
                                                                   false )
                                                             .add( OP_FREQ,
                                                                   VAL_YEARLY )
                                                             .add( OP_INTERVAL,
                                                                   1 )
                                                             .add( OP_BYMONTH,
                                                                   String.valueOf( i ) )
                                                             .add( OP_BYDAY,
                                                                   getExtWeekdayPattern( 1 ) )
                                                             .build() ),
                            testData );
            }
         }
      }
      
      for ( String freq : getFreqs() )
      {
         addTestData( new TestData( freq + " " + getOnThe() + getXst( 1 ) + " "
                                       + getWeekdayString( 1 ) + getIn() + " "
                                       + getMonthString( 1 ),
                                    new ResultBuilder().add( IS_EVERY, false )
                                                       .add( OP_FREQ,
                                                             VAL_YEARLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYMONTH,
                                                             String.valueOf( 1 ) )
                                                       .add( OP_BYDAY,
                                                             getExtWeekdayPattern( 1 ) )
                                                       .build() ),
                      testData );
      }
      
      addUntilAndCount( testData,
                        getOnThe() + getXst( 1 ) + " " + getWeekdayString( 1 )
                           + getIn() + " " + getMonthString( 1 ),
                        new ResultBuilder().add( IS_EVERY, false )
                                           .add( OP_FREQ, VAL_YEARLY )
                                           .add( OP_INTERVAL, 1 )
                                           .add( OP_BYMONTH, String.valueOf( 1 ) )
                                           .add( OP_BYDAY,
                                                 getExtWeekdayPattern( 1 ) ) );
   }
   
   
   
   private void addSentenceOnMultipleXstWeekdaysOfMonth( Collection< Object[] > testData )
   {
      addTestData( new TestData( getOnThe() + getXst( 1 ) + getSeparator()
                                    + getXst( 3 ) + " " + getWeekdayString( 1 )
                                    + getIn() + " " + getMonthString( 1 ),
                                 new ResultBuilder().add( IS_EVERY, false )
                                                    .add( OP_FREQ, VAL_YEARLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYMONTH,
                                                          String.valueOf( 1 ) )
                                                    .add( OP_BYDAY,
                                                          getExtWeekdayPattern( 3,
                                                                                1 ) )
                                                    .build() ),
                   testData );
      
      addTestData( new TestData( getOnThe() + getXst( 1 ) + " "
                                    + getWeekdayString( 1 ) + getSeparator()
                                    + getWeekdayString( 3 ) + getIn() + " "
                                    + getMonthString( 1 ),
                                 new ResultBuilder().add( IS_EVERY, false )
                                                    .add( OP_FREQ, VAL_YEARLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYMONTH,
                                                          String.valueOf( 1 ) )
                                                    .add( OP_BYDAY,
                                                          getExtWeekdayPattern( 1 ),
                                                          getExtWeekdayPattern( 1,
                                                                                3 ) )
                                                    .build() ),
                   testData );
      
      addTestData( new TestData( getOnThe() + getXst( 1 ) + getSeparator()
                                    + getXst( 3 ) + " " + getWeekdayString( 1 )
                                    + getSeparator() + getWeekdayString( 3 )
                                    + getIn() + " " + getMonthString( 1 ),
                                 new ResultBuilder().add( IS_EVERY, false )
                                                    .add( OP_FREQ, VAL_YEARLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYMONTH,
                                                          String.valueOf( 1 ) )
                                                    .add( OP_BYDAY,
                                                          getExtWeekdayPattern( 3,
                                                                                1 ),
                                                          getExtWeekdayPattern( 3 ) )
                                                    .build() ),
                   testData );
      
      addUntilAndCount( testData,
                        getOnThe() + getXst( 1 ) + getSeparator() + getXst( 3 )
                           + " " + getWeekdayString( 1 ) + getSeparator()
                           + getWeekdayString( 3 ) + getIn() + " "
                           + getMonthString( 1 ),
                        new ResultBuilder().add( IS_EVERY, false )
                                           .add( OP_FREQ, VAL_YEARLY )
                                           .add( OP_INTERVAL, 1 )
                                           .add( OP_BYMONTH, String.valueOf( 1 ) )
                                           .add( OP_BYDAY,
                                                 getExtWeekdayPattern( 3, 1 ),
                                                 getExtWeekdayPattern( 3 ) ) );
   }
   
   
   
   private void addFreq( Collection< Object[] > testData,
                         String everyAfter,
                         boolean isEvery,
                         String freqToken,
                         int interval,
                         String recurrence )
   {
      if ( interval == 1 )
      {
         addTestData( new TestData( everyAfter + " " + freqToken,
                                    new ResultBuilder().add( IS_EVERY, isEvery )
                                                       .add( OP_FREQ,
                                                             recurrence )
                                                       .add( OP_INTERVAL, 1 )
                                                       .build() ),
                      testData );
      }
      
      addTestData( new TestData( everyAfter + " " + interval + " " + freqToken,
                                 new ResultBuilder().add( IS_EVERY, isEvery )
                                                    .add( OP_FREQ, recurrence )
                                                    .add( OP_INTERVAL, interval )
                                                    .build() ),
                   testData );
      
      for ( String numStr : language.getNumberStrings( interval ) )
      {
         
         addTestData( new TestData( everyAfter + " " + numStr + " " + freqToken,
                                    new ResultBuilder().add( IS_EVERY, isEvery )
                                                       .add( OP_FREQ,
                                                             recurrence )
                                                       .add( OP_INTERVAL,
                                                             interval )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addUntil( Collection< Object[] > testData,
                          String sentence,
                          ResultBuilder pattern )
   {
      for ( String until : language.getUntil() )
      {
         addTestData( new TestData( sentence
                                       + until
                                       + dateFormatter.formatDateNumeric( UNTIL_DATE.getTimeInMillis() ),
                                    pattern.add( OP_UNTIL, UNTIL_DATE_PATTERN )
                                           .build() ),
                      testData );
      }
   }
   
   
   
   private void addCount( Collection< Object[] > testData,
                          String sentence,
                          ResultBuilder pattern )
   {
      for ( String forStr : language.getFor() )
      {
         for ( String times : language.getTimes() )
         {
            addTestData( new TestData( sentence + forStr + "2" + times,
                                       pattern.add( OP_COUNT, 2 ).build() ),
                         testData );
         }
      }
   }
   
   
   
   private void addUntilAndCount( Collection< Object[] > testData,
                                  String sentence,
                                  ResultBuilder pattern )
   {
      addUntil( testData, sentence, new ResultBuilder( pattern ) );
      addCount( testData, sentence, new ResultBuilder( pattern ) );
   }
   
   
   
   private Iterable< String > getFreqs()
   {
      final Collection< String > freqs = new ArrayList< String >( 4 );
      
      freqs.add( language.getDayLiterals().iterator().next() );
      freqs.add( language.getWeekLiterals().iterator().next() );
      freqs.add( language.getMonthLiterals().iterator().next() );
      freqs.add( language.getYearLiterals().iterator().next() );
      
      return freqs;
   }
   
   
   
   private String getEvery()
   {
      return language.getEvery().iterator().next();
   }
   
   
   
   private String getAfter()
   {
      return language.getAfter().iterator().next();
   }
   
   
   
   private String getOnThe()
   {
      return language.getOnThe().iterator().next();
   }
   
   
   
   private String getXst( int i )
   {
      return language.getXst( i ).iterator().next();
   }
   
   
   
   private String getSeparator()
   {
      return language.getSeparators().iterator().next();
   }
   
   
   
   private String getIn()
   {
      return language.getInOfMonth().iterator().next();
   }
   
   
   
   private String getMonthString( int i )
   {
      return language.getMonthStrings( i ).iterator().next();
   }
   
   
   
   private String getWeekdayString( int i )
   {
      return language.getWeekdayStrings( i ).iterator().next();
   }
   
   
   
   private String getWeekdayPattern( int weekday )
   {
      switch ( weekday )
      {
         case 1:
            return "MO";
         case 2:
            return "TU";
         case 3:
            return "WE";
         case 4:
            return "TH";
         case 5:
            return "FR";
         case 6:
            return "SA";
         case 7:
            return "SU";
         default :
            throw new IllegalArgumentException( "weekday" );
      }
   }
   
   
   
   private String getExtWeekdayPattern( int weekday )
   {
      return getExtWeekdayPattern( weekday, weekday );
   }
   
   
   
   private String getExtWeekdayPattern( int prefix, int weekday )
   {
      return prefix + getWeekdayPattern( weekday );
   }
   
   
   
   private void addTestData( TestData testData, Collection< Object[] > testDatas )
   {
      testDatas.add( new Object[]
      { testData } );
   }
   
   
   private final class ResultBuilder
   {
      private final Map< String, Object > expectedResult;
      
      
      
      public ResultBuilder()
      {
         expectedResult = new TreeMap< String, Object >( new RecurrencePatternOperatorComp() );
      }
      
      
      
      public ResultBuilder( ResultBuilder other )
      {
         this();
         expectedResult.putAll( other.expectedResult );
      }
      
      
      
      public ResultBuilder add( String operator, Object value )
      {
         expectedResult.put( operator, value );
         return this;
      }
      
      
      
      public ResultBuilder add( String operator, Object... values )
      {
         expectedResult.put( operator,
                             Strings.join( ",", Arrays.asList( values ) ) );
         return this;
      }
      
      
      
      public Map< String, Object > build()
      {
         return expectedResult;
      }
   }
   
   
   public final static class TestData
   {
      public final String sentence;
      
      public final Map< String, Object > expectedPattern;
      
      
      
      public TestData( String sentence, Map< String, Object > expectedPattern )
      {
         this.sentence = sentence;
         this.expectedPattern = expectedPattern;
      }
      
      
      
      @Override
      public String toString()
      {
         return sentence;
      }
   }
}
