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

package dev.drsoran.rtm.test.testdatasources;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.parsing.lang.IRecurrenceSentenceLanguage;


public class RecurrenceSentenceTestDataSource extends
         TheoriesTestDataSource< RecurrenceSentenceTestDataSource.TestData >
{
   private final IRecurrenceSentenceLanguage language;
   
   
   
   public RecurrenceSentenceTestDataSource( IRecurrenceSentenceLanguage language )
   {
      this.language = language;
   }
   
   
   
   public Collection< Object[] > getTestData()
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
   
   
   
   @Override
   public Class< TestData > getTestDataClass()
   {
      return TestData.class;
   }
   
   
   
   private void addPatternYearly( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 3; ++i )
      {
         for ( Boolean everyAfter : getEveryAfter() )
         {
            StringBuilder sb = getEveryAfter( everyAfter, "year", i );
            final String year = sb.toString();
            
            addTestData( new TestData( "FREQ=YEARLY;INTERVAL=" + i,
                                       everyAfter.booleanValue(),
                                       year ),
                         testData );
         }
      }
   }
   
   
   
   private void addPatternYearlyCountUntil( Collection< Object[] > testData )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         final StringBuilder sb = getEveryAfter( everyAfter, "year", 1 );
         sb.append( " " );
         language.append( sb, "on_the" );
         sb.append( " " );
         language.append( sb, "MO" );
         sb.append( " " );
         language.append( sb, "in" );
         sb.append( " " );
         language.append( sb, "m1" );
         
         final StringBuilder until = new StringBuilder( sb.toString() );
         until.append( " " );
         language.append( until, "until" );
         until.append( " 01.10.2010, 15:30:00" );
         
         addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1;BYDAY=MO;BYMONTH=1;UNTIL=20101001T153000",
                                    everyAfter.booleanValue(),
                                    until.toString() ),
                      testData );
         
         final StringBuilder count = new StringBuilder( sb.toString() );
         count.append( " " );
         language.append( count, "for" );
         count.append( " 2 " );
         language.append( count, "times" );
         
         addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1;BYDAY=MO;BYMONTH=1;COUNT=2",
                                    everyAfter.booleanValue(),
                                    count.toString() ),
                      testData );
      }
   }
   
   
   
   private void addPatternYearlyAllWeekdays( Collection< Object[] > testData )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         for ( String weekday : getWeekDays() )
         {
            final StringBuilder sb = getEveryAfter( everyAfter, "year", 1 );
            
            sb.append( " " );
            language.append( sb, "on_the" );
            sb.append( " " );
            language.append( sb, weekday );
            sb.append( " " );
            language.append( sb, "in" );
            sb.append( " " );
            language.append( sb, "m1" );
            
            addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1" + ";BYDAY="
                                          + weekday + ";BYMONTH=1",
                                       everyAfter.booleanValue(),
                                       sb.toString() ),
                         testData );
         }
      }
   }
   
   
   
   private void addPatternYearlyWeekdayWithXst( Collection< Object[] > testData )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         for ( int xst = -4; xst < 11; ++xst )
         {
            final StringBuilder sb = getEveryAfter( everyAfter, "year", 1 );
            
            sb.append( " " );
            language.append( sb, "on_the" );
            
            appendXst( sb, xst );
            
            sb.append( " " );
            language.append( sb, "MO" );
            sb.append( " " );
            language.append( sb, "in" );
            sb.append( " " );
            language.append( sb, "m1" );
            
            addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1" + ";BYDAY="
                                          + xst + "MO" + ";BYMONTH=1",
                                       everyAfter.booleanValue(),
                                       sb.toString() ),
                         testData );
         }
      }
   }
   
   
   
   private void addPatternYearlyWeekdayWithMultipleXst( Collection< Object[] > testData )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         for ( int xst1 = 1; xst1 < 2; ++xst1 )
         {
            final StringBuilder sb = getEveryAfter( everyAfter, "year", 1 );
            
            sb.append( " " );
            language.append( sb, "on_the" );
            sb.append( " " );
            language.appendStToX( sb, 1 );
            sb.append( " " );
            language.append( sb, "MO" );
            sb.append( ", " );
            language.append( sb, "FR" );
            sb.append( " " );
            language.append( sb, "in" );
            sb.append( " " );
            language.append( sb, "m1" );
            
            addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1"
                                          + ";BYDAY=1MO,2FR" + ";BYMONTH=1",
                                       everyAfter.booleanValue(),
                                       sb.toString() ),
                         testData );
            
            addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1"
                                          + ";BYDAY=1MO,FR" + ";BYMONTH=1",
                                       everyAfter.booleanValue(),
                                       sb.toString() ),
                         testData );
         }
      }
   }
   
   
   
   private void addPatternYearlyAllMonths( Collection< Object[] > testData )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         for ( int monthNr = 1; monthNr < 13; ++monthNr )
         {
            final StringBuilder sb = getEveryAfter( everyAfter, "year", 1 );
            
            sb.append( " " );
            language.append( sb, "on_the" );
            sb.append( " " );
            language.append( sb, "MO" );
            sb.append( " " );
            language.append( sb, "in" );
            sb.append( " " );
            language.append( sb, "m" + monthNr );
            
            addTestData( new TestData( "FREQ=YEARLY;INTERVAL=1" + ";BYDAY=MO"
                                          + ";BYMONTH=" + monthNr,
                                       everyAfter.booleanValue(),
                                       sb.toString() ),
                         testData );
         }
      }
   }
   
   
   
   private void addPatternMonthly( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 3; ++i )
      {
         for ( Boolean everyAfter : getEveryAfter() )
         {
            StringBuilder sb = getEveryAfter( everyAfter, "month", i );
            final String month = sb.toString();
            
            addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=" + i,
                                       everyAfter.booleanValue(),
                                       month ),
                         testData );
         }
      }
   }
   
   
   
   private void addPatternMonthlyWeekday( Collection< Object[] > testData )
   {
      addPatternWeekday( testData, "month", "FREQ=MONTHLY;INTERVAL=1;BYDAY=MO" );
   }
   
   
   
   private void addPatternMonthlyWeekdayXst( Collection< Object[] > testData )
   {
      addPatternWeekdayWithXst( testData,
                                "month",
                                "FREQ=MONTHLY;INTERVAL=1;BYDAY={0}MO" );
   }
   
   
   
   private void addPatternMonthlyWeekdayMultipleXst( Collection< Object[] > testData )
   {
      addPatternWeekdayWithMultipleXst( testData,
                                        "month",
                                        "FREQ=MONTHLY;INTERVAL=1;BYDAY={0}MO,{1}FR" );
   }
   
   
   
   private void addPatternMonthlyByMonthDayXst( Collection< Object[] > testData )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         for ( int xst = -4; xst < 11; ++xst )
         {
            final StringBuilder sb = getEveryAfter( everyAfter, "month", 1 );
            
            sb.append( " " );
            language.append( sb, "on_the" );
            
            appendXst( sb, xst );
            
            addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY="
               + xst, everyAfter.booleanValue(), sb.toString() ), testData );
         }
      }
   }
   
   
   
   private void addPatternMonthlyByMonthDayMultipleXst( Collection< Object[] > testData )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         StringBuilder sb = getEveryAfter( everyAfter, "month", 1 );
         
         sb.append( " " );
         language.append( sb, "on_the" );
         appendXst( sb, 1 );
         sb.append( "," );
         appendXst( sb, 2 );
         
         addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=1,2",
                                    everyAfter.booleanValue(),
                                    sb.toString() ), testData );
         
         sb = getEveryAfter( everyAfter, "month", 1 );
         
         sb.append( " " );
         language.append( sb, "on_the" );
         appendXst( sb, -1 );
         sb.append( "," );
         appendXst( sb, -2 );
         
         addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=-1,-2",
                                    everyAfter.booleanValue(),
                                    sb.toString() ), testData );
      }
   }
   
   
   
   private void addPatternMonthlyCountUntil( Collection< Object[] > testData )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         final StringBuilder sb = getEveryAfter( everyAfter, "month", 1 );
         sb.append( " " );
         language.append( sb, "on_the" );
         appendXst( sb, 1 );
         sb.append( "," );
         appendXst( sb, 2 );
         
         final StringBuilder until = new StringBuilder( sb.toString() );
         until.append( " " );
         language.append( until, "until" );
         until.append( " 01.10.2010, 15:30:00" );
         
         addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=1,2;UNTIL=20101001T153000",
                                    everyAfter.booleanValue(),
                                    until.toString() ),
                      testData );
         
         final StringBuilder count = new StringBuilder( sb.toString() );
         count.append( " " );
         language.append( count, "for" );
         count.append( " 2 " );
         language.append( count, "times" );
         
         addTestData( new TestData( "FREQ=MONTHLY;INTERVAL=1;BYMONTHDAY=1,2;COUNT=2",
                                    everyAfter.booleanValue(),
                                    count.toString() ),
                      testData );
      }
   }
   
   
   
   private void addPatternWeekly( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 3; ++i )
      {
         for ( Boolean everyAfter : getEveryAfter() )
         {
            StringBuilder sb = getEveryAfter( everyAfter, "week", i );
            final String week = sb.toString();
            
            addTestData( new TestData( "FREQ=WEEKLY;INTERVAL=" + i,
                                       everyAfter.booleanValue(),
                                       week ),
                         testData );
         }
      }
   }
   
   
   
   private void addPatternWeeklyWeekday( Collection< Object[] > testData )
   {
      addPatternWeekday( testData, "week", "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO" );
   }
   
   
   
   private void addPatternWeeklyWeekdayXst( Collection< Object[] > testData )
   {
      addPatternWeekdayWithXst( testData,
                                "week",
                                "FREQ=WEEKLY;INTERVAL=1;BYDAY={0}MO" );
   }
   
   
   
   private void addPatternWeeklyWeekdayMultipleXst( Collection< Object[] > testData )
   {
      addPatternWeekdayWithMultipleXst( testData,
                                        "week",
                                        "FREQ=WEEKLY;INTERVAL=1;BYDAY={0}MO,{1}FR" );
   }
   
   
   
   private void addPatternWeeklyCountUntil( Collection< Object[] > testData )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         final StringBuilder sb = getEveryAfter( everyAfter, "week", 1 );
         sb.append( " " );
         language.append( sb, "on_the" );
         sb.append( " " );
         language.append( sb, "MO" );
         
         final StringBuilder until = new StringBuilder( sb.toString() );
         until.append( " " );
         language.append( until, "until" );
         until.append( " 01.10.2010, 15:30:00" );
         
         addTestData( new TestData( "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO;UNTIL=20101001T153000",
                                    everyAfter.booleanValue(),
                                    until.toString() ),
                      testData );
         
         final StringBuilder count = new StringBuilder( sb.toString() );
         count.append( " " );
         language.append( count, "for" );
         count.append( " 2 " );
         language.append( count, "times" );
         
         addTestData( new TestData( "FREQ=WEEKLY;INTERVAL=1;BYDAY=MO;COUNT=2",
                                    everyAfter.booleanValue(),
                                    count.toString() ), testData );
      }
   }
   
   
   
   private void addPatternDaily( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 3; ++i )
      {
         for ( Boolean everyAfter : getEveryAfter() )
         {
            StringBuilder sb = getEveryAfter( everyAfter, "day", i );
            final String week = sb.toString();
            
            addTestData( new TestData( "FREQ=DAILY;INTERVAL=" + i,
                                       everyAfter.booleanValue(),
                                       week ),
                         testData );
         }
      }
   }
   
   
   
   private void addPatternDailyCountUntil( Collection< Object[] > testData )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         final StringBuilder sb = getEveryAfter( everyAfter, "day", 1 );
         
         final StringBuilder until = new StringBuilder( sb.toString() );
         until.append( " " );
         language.append( until, "until" );
         until.append( " 01.10.2010, 15:30:00" );
         
         addTestData( new TestData( "FREQ=DAILY;INTERVAL=1;UNTIL=20101001T153000",
                                    everyAfter.booleanValue(),
                                    until.toString() ),
                      testData );
         
         final StringBuilder count = new StringBuilder( sb.toString() );
         count.append( " " );
         language.append( count, "for" );
         count.append( " 2 " );
         language.append( count, "times" );
         
         addTestData( new TestData( "FREQ=DAILY;INTERVAL=1;COUNT=2",
                                    everyAfter.booleanValue(),
                                    count.toString() ),
                      testData );
      }
   }
   
   
   
   private void addTestData( TestData testData, Collection< Object[] > testDatas )
   {
      testDatas.add( new Object[]
      { testData } );
   }
   
   
   
   private Iterable< Boolean > getEveryAfter()
   {
      return Arrays.asList( Boolean.FALSE, Boolean.TRUE );
   }
   
   
   
   private void appendXst( StringBuilder sb, int xst )
   {
      if ( xst != -1 )
      {
         sb.append( " " );
         language.appendStToX( sb, Math.abs( xst ) );
      }
      
      if ( xst < 0 )
      {
         sb.append( " " );
         language.append( sb, "last" );
      }
   }
   
   
   
   private StringBuilder getEveryAfter( boolean everyAfter, String unit, int qty )
   {
      final StringBuilder sb = new StringBuilder();
      
      if ( everyAfter )
      {
         language.appendEvery( sb, unit, Integer.toString( qty ) );
      }
      else
      {
         language.appendAfter( sb, unit, Integer.toString( qty ) );
      }
      
      return sb;
   }
   
   
   
   private void addPatternWeekday( Collection< Object[] > testData,
                                   String unit,
                                   String testPattern )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         final StringBuilder sb = getEveryAfter( everyAfter, unit, 1 );
         
         sb.append( " " );
         language.append( sb, "on_the" );
         sb.append( " " );
         language.append( sb, "MO" );
         
         addTestData( new TestData( testPattern,
                                    everyAfter.booleanValue(),
                                    sb.toString() ),
                      testData );
      }
   }
   
   
   
   private void addPatternWeekdayWithXst( Collection< Object[] > testData,
                                          String unit,
                                          String testPattern )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         for ( int xst = -4; xst < 11; ++xst )
         {
            final StringBuilder sb = getEveryAfter( everyAfter, unit, 1 );
            
            sb.append( " " );
            language.append( sb, "on_the" );
            
            appendXst( sb, xst );
            
            sb.append( " " );
            language.append( sb, "MO" );
            
            final String testString = MessageFormat.format( testPattern, xst );
            
            addTestData( new TestData( testString,
                                       everyAfter.booleanValue(),
                                       sb.toString() ), testData );
         }
      }
   }
   
   
   
   private void addPatternWeekdayWithMultipleXst( Collection< Object[] > testData,
                                                  String unit,
                                                  String testPattern )
   {
      for ( Boolean everyAfter : getEveryAfter() )
      {
         final StringBuilder sb = getEveryAfter( everyAfter, unit, 1 );
         
         sb.append( " " );
         language.append( sb, "on_the" );
         sb.append( " " );
         language.appendStToX( sb, 1 );
         sb.append( " " );
         language.append( sb, "MO" );
         sb.append( ", " );
         language.append( sb, "FR" );
         
         final String testString1 = MessageFormat.format( testPattern, 1, 2 );
         
         addTestData( new TestData( testString1,
                                    everyAfter.booleanValue(),
                                    sb.toString() ),
                      testData );
         
         final String testString2 = MessageFormat.format( testPattern,
                                                          1,
                                                          Strings.EMPTY_STRING );
         
         addTestData( new TestData( testString2,
                                    everyAfter.booleanValue(),
                                    sb.toString() ),
                      testData );
      }
   }
   
   
   
   private Iterable< String > getWeekDays()
   {
      return Arrays.asList( "MO", "TU", "WE", "TH", "FR", "SA", "SU" );
   }
   
   
   public final static class TestData
   {
      public final String pattern;
      
      public final boolean every;
      
      public final String sentence;
      
      
      
      public TestData( String pattern, String sentence )
      {
         this( pattern, false, sentence );
      }
      
      
      
      public TestData( String pattern, boolean every, String sentence )
      {
         this.pattern = pattern;
         this.every = every;
         this.sentence = sentence;
      }
      
      
      
      @Override
      public String toString()
      {
         return every ? "every " + pattern : "after " + pattern;
      }
   }
}
