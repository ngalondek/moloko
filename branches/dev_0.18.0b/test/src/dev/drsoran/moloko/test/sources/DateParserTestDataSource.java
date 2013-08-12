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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.test.langs.IDateParserTestLanguage;
import dev.drsoran.moloko.util.Strings;


public class DateParserTestDataSource
{
   private final ILanguage dateLanguage;
   
   private final IDateParserTestLanguage testLanguage;
   
   private final MolokoCalendar todayCal;
   
   private final int thisYear;
   
   private final int thisMonth;
   
   private final int thisMonthNum;
   
   private final int thisDay;
   
   private final Collection< String > xStWithEmptyString;
   
   
   
   public DateParserTestDataSource( ILanguage dateLanguage,
      IDateParserTestLanguage testLanguage, MolokoCalendar today )
   {
      this.dateLanguage = dateLanguage;
      this.testLanguage = testLanguage;
      this.todayCal = today.clone();
      this.thisDay = today.get( Calendar.DATE );
      this.thisMonth = today.get( Calendar.MONTH );
      this.thisMonthNum = thisMonth + 1;
      this.thisYear = today.get( Calendar.YEAR );
      this.xStWithEmptyString = new ArrayList< String >( testLanguage.getXst() );
      this.xStWithEmptyString.add( Strings.EMPTY_STRING );
   }
   
   
   
   public Collection< Object[] > getParseDateTestData()
   {
      final Collection< Object[] > testData = new LinkedList< Object[] >();
      
      addParseDateNumeric( testData );
      addParseDateOnXstOfMonth_Day( testData );
      addParseDateOnXstOfMonth_DayMonth( testData );
      addParseDateOnXstOfMonth_DayMonthYear( testData );
      addParseDateOnMonthTheXst_Month( testData );
      addParseDateOnMonthTheXst_MonthDay( testData );
      addParseDateOnMonthTheXst_MonthDayYear( testData );
      addParseDateOnWeekday( testData );
      addParseDateOnNextWeekday( testData );
      addParseDateInDistance_Year( testData );
      addParseDateInDistance_Month( testData );
      addParseDateInDistance_Week( testData );
      addParseDateInDistance_Day( testData );
      addParseDateInX_YMWD( testData );
      addParseDateEndOfThe_Week( testData );
      addParseDateEndOfThe_Month( testData );
      addParseDateLiteral( testData );
      
      return testData;
   }
   
   
   
   public Collection< Object[] > getParseWithInDateTestData()
   {
      final Collection< Object[] > testData = new LinkedList< Object[] >();
      
      addParseDateWithin_Amount( testData );
      addParseDateWithin_AmountDay( testData );
      addParseDateWithin_AmountWeek( testData );
      addParseDateWithin_AmountMonth( testData );
      addParseDateWithin_AmountYear( testData );
      
      return testData;
   }
   
   
   
   private void addParseDateNumeric( Collection< Object[] > testData )
   {
      for ( String separator : testLanguage.getNumericDateSeparators() )
      {
         String parseString = 1 + separator + 11;
         addTestData( testData, new ParseDateTestData( parseString,
                                                       1,
                                                       11,
                                                       thisYear ) );
         
         parseString = 1 + separator + 11 + separator;
         addTestData( testData, new ParseDateTestData( parseString,
                                                       1,
                                                       11,
                                                       thisYear ) );
         
         parseString = 1 + separator + 11 + separator + 2011;
         addTestData( testData,
                      new ParseDateTestData( parseString, 1, 11, 2011 ) );
      }
   }
   
   
   
   private void addParseDateOnXstOfMonth_Day( Collection< Object[] > testData )
   {
      for ( String st : xStWithEmptyString )
      {
         final String testString = "1" + st;
         addTestData( testData, new ParseDateTestData( testString,
                                                       1,
                                                       thisMonthNum,
                                                       thisYear ) );
      }
   }
   
   
   
   private void addParseDateOnXstOfMonth_DayMonth( Collection< Object[] > testData )
   {
      for ( int monthNum = 1; monthNum < 13; ++monthNum )
      {
         for ( String month : testLanguage.getMonthStrings( monthNum ) )
         {
            for ( String dayMonthSep : testLanguage.getDayMonthSeparators() )
            {
               for ( String st : xStWithEmptyString )
               {
                  final String testString = "1" + st + dayMonthSep + month;
                  addTestData( testData, new ParseDateTestData( testString,
                                                                1,
                                                                monthNum,
                                                                thisYear ) );
               }
            }
         }
      }
   }
   
   
   
   private void addParseDateOnXstOfMonth_DayMonthYear( Collection< Object[] > testData )
   {
      for ( String monthYearSep : testLanguage.getMonthYearSeparators() )
      {
         for ( int monthNum = 1; monthNum < 13; ++monthNum )
         {
            for ( String month : testLanguage.getMonthStrings( monthNum ) )
            {
               for ( String dayMonthSep : testLanguage.getDayMonthSeparators() )
               {
                  for ( String st : xStWithEmptyString )
                  {
                     for ( String year : new String[]
                     { "1", "01", "001", "2001" } )
                     {
                        final String testString = "1" + st + dayMonthSep
                           + month + monthYearSep + year;
                        addTestData( testData,
                                     new ParseDateTestData( testString,
                                                            1,
                                                            monthNum,
                                                            2001 ) );
                     }
                  }
               }
            }
         }
      }
   }
   
   
   
   private void addParseDateOnMonthTheXst_Month( Collection< Object[] > testData )
   {
      for ( int monthNum = 1; monthNum < 13; ++monthNum )
      {
         for ( String month : testLanguage.getMonthStrings( monthNum ) )
         {
            final String testString = month;
            addTestData( testData, new ParseDateTestData( testString,
                                                          thisDay,
                                                          monthNum,
                                                          thisYear ) );
         }
      }
   }
   
   
   
   private void addParseDateOnMonthTheXst_MonthDay( Collection< Object[] > testData )
   {
      final Collection< String > xstAndMonthDaySep = new ArrayList< String >( xStWithEmptyString );
      xstAndMonthDaySep.addAll( testLanguage.getMonthDaySeparators() );
      
      for ( int monthNum = 1; monthNum < 13; ++monthNum )
      {
         for ( String month : testLanguage.getMonthStrings( monthNum ) )
         {
            for ( String monthDaySep : testLanguage.getMonthDaySeparators() )
            {
               for ( String daySuffix : xstAndMonthDaySep )
               {
                  final String testString = month + monthDaySep + "1"
                     + daySuffix;
                  addTestData( testData, new ParseDateTestData( testString,
                                                                1,
                                                                monthNum,
                                                                thisYear ) );
               }
            }
         }
      }
   }
   
   
   
   private void addParseDateOnMonthTheXst_MonthDayYear( Collection< Object[] > testData )
   {
      final Collection< String > xstAndMonthDaySep = new ArrayList< String >( testLanguage.getXst() );
      xstAndMonthDaySep.addAll( testLanguage.getMonthDaySeparators() );
      
      for ( int monthNum = 1; monthNum < 13; ++monthNum )
      {
         for ( String month : testLanguage.getMonthStrings( monthNum ) )
         {
            for ( String monthDaySep : testLanguage.getMonthDaySeparators() )
            {
               for ( String daySuffix : xstAndMonthDaySep )
               {
                  for ( String year : new String[]
                  { "1", "01", "001", "2001" } )
                  {
                     final String testString = month + monthDaySep + "1"
                        + daySuffix + year;
                     addTestData( testData, new ParseDateTestData( testString,
                                                                   1,
                                                                   monthNum,
                                                                   2001 ) );
                  }
               }
            }
         }
      }
   }
   
   
   
   private void addParseDateOnWeekday( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 8; ++i )
      {
         for ( String weekday : testLanguage.getWeekdayStrings( i ) )
         {
            final MolokoCalendar refCal = todayCal.clone();
            refCal.get( Calendar.DAY_OF_WEEK );
            refCal.set( Calendar.DAY_OF_WEEK, dateLanguage.getInteger( weekday ) );
            
            addTestData( testData,
                         new ParseDateTestData( weekday,
                                                refCal.get( Calendar.DATE ),
                                                refCal.get( Calendar.MONTH ) + 1,
                                                refCal.get( Calendar.YEAR ) ) );
         }
      }
   }
   
   
   
   private void addParseDateOnNextWeekday( Collection< Object[] > testData )
   {
      final MolokoCalendar refCal = todayCal.clone();
      refCal.add( Calendar.WEEK_OF_YEAR, 1 );
      
      for ( int i = 1; i < 8; ++i )
      {
         for ( String weekday : testLanguage.getWeekdayStrings( i ) )
         {
            refCal.set( Calendar.DAY_OF_WEEK, dateLanguage.getInteger( weekday ) );
            
            for ( String next : testLanguage.getNext() )
            {
               addTestData( testData,
                            new ParseDateTestData( next + weekday,
                                                   refCal.get( Calendar.DATE ),
                                                   refCal.get( Calendar.MONTH ) + 1,
                                                   refCal.get( Calendar.YEAR ) ) );
            }
         }
      }
   }
   
   
   
   private void addParseDateInDistance_Year( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 11; ++i )
      {
         for ( String numStr : testLanguage.getNumberStrings( i ) )
         {
            for ( String yearStr : testLanguage.getYearLiterals() )
            {
               for ( String num : new String[]
               { String.valueOf( i ), numStr } )
               {
                  final String testString = num + " " + yearStr;
                  
                  addTestData( testData, new ParseDateTestData( testString,
                                                                thisDay,
                                                                thisMonthNum,
                                                                thisYear + i ) );
               }
            }
         }
      }
   }
   
   
   
   private void addParseDateInDistance_Month( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 11; ++i )
      {
         for ( String numStr : testLanguage.getNumberStrings( i ) )
         {
            for ( String monthStr : testLanguage.getMonthLiterals() )
            {
               for ( String num : new String[]
               { String.valueOf( i ), numStr } )
               {
                  int expectedMonth = ( ( thisMonth + i ) % 12 ) + 1;
                  int expectedYear = thisYear;
                  
                  if ( thisMonthNum + i > 12 )
                  {
                     ++expectedYear;
                  }
                  
                  final String testString = num + " " + monthStr;
                  
                  addTestData( testData, new ParseDateTestData( testString,
                                                                thisDay,
                                                                expectedMonth,
                                                                expectedYear ) );
               }
            }
         }
      }
   }
   
   
   
   private void addParseDateInDistance_Week( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 11; ++i )
      {
         for ( String numStr : testLanguage.getNumberStrings( i ) )
         {
            for ( String weekStr : testLanguage.getWeekLiterals() )
            {
               final MolokoCalendar refCal = todayCal.clone();
               refCal.add( Calendar.WEEK_OF_YEAR, i );
               
               for ( String num : new String[]
               { String.valueOf( i ), numStr } )
               {
                  final String testString = num + " " + weekStr;
                  
                  addTestData( testData,
                               new ParseDateTestData( testString,
                                                      refCal.get( Calendar.DATE ),
                                                      refCal.get( Calendar.MONTH ) + 1,
                                                      refCal.get( Calendar.YEAR ) ) );
               }
            }
         }
      }
   }
   
   
   
   private void addParseDateInDistance_Day( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 11; ++i )
      {
         for ( String numStr : testLanguage.getNumberStrings( i ) )
         {
            for ( String dayStr : testLanguage.getDayLiterals() )
            {
               final MolokoCalendar refCal = todayCal.clone();
               refCal.add( Calendar.DATE, i );
               
               for ( String num : new String[]
               { String.valueOf( i ), numStr } )
               {
                  final String testString = num + " " + dayStr;
                  
                  addTestData( testData,
                               new ParseDateTestData( testString,
                                                      refCal.get( Calendar.DATE ),
                                                      refCal.get( Calendar.MONTH ) + 1,
                                                      refCal.get( Calendar.YEAR ) ) );
               }
            }
         }
      }
   }
   
   
   
   private void addParseDateInX_YMWD( Collection< Object[] > testData )
   {
      final MolokoCalendar refCal = todayCal.clone();
      refCal.add( Calendar.DATE, 5 );
      refCal.add( Calendar.WEEK_OF_YEAR, 2 );
      refCal.add( Calendar.YEAR, 1 );
      
      final Collection< String > ins = new ArrayList< String >( testLanguage.getIn() );
      ins.add( Strings.EMPTY_STRING );
      
      for ( String prefix : ins )
      {
         final String testString1 = prefix + "2 "
            + testLanguage.getWeekLiterals().iterator().next();
         
         for ( String separator1 : testLanguage.getDateConcatenators() )
         {
            final String testString2 = testString1 + separator1 + "1 "
               + testLanguage.getYearLiterals().iterator().next();
            
            for ( String separator2 : testLanguage.getDateConcatenators() )
            {
               final String testString3 = testString2 + separator2 + "5 "
                  + testLanguage.getDayLiterals().iterator().next();
               
               addTestData( testData,
                            new ParseDateTestData( testString3,
                                                   refCal.get( Calendar.DATE ),
                                                   refCal.get( Calendar.MONTH ) + 1,
                                                   refCal.get( Calendar.YEAR ) ) );
            }
         }
      }
   }
   
   
   
   private void addParseDateEndOfThe_Week( Collection< Object[] > testData )
   {
      final MolokoCalendar refCal = todayCal.clone();
      refCal.get( Calendar.DAY_OF_WEEK );
      refCal.set( Calendar.DAY_OF_WEEK,
                  refCal.getActualMaximum( Calendar.DAY_OF_WEEK ) );
      
      for ( String prefix : testLanguage.getEndOfThe() )
      {
         for ( String weekStr : testLanguage.getWeekLiterals() )
         {
            final String testString = prefix + weekStr;
            
            addTestData( testData,
                         new ParseDateTestData( testString,
                                                refCal.get( Calendar.DATE ),
                                                refCal.get( Calendar.MONTH ) + 1,
                                                refCal.get( Calendar.YEAR ) ) );
         }
      }
   }
   
   
   
   private void addParseDateEndOfThe_Month( Collection< Object[] > testData )
   {
      final MolokoCalendar refCal = todayCal.clone();
      refCal.set( Calendar.DAY_OF_MONTH,
                  refCal.getActualMaximum( Calendar.DAY_OF_MONTH ) );
      
      for ( String prefix : testLanguage.getEndOfThe() )
      {
         for ( String monthStr : testLanguage.getMonthLiterals() )
         {
            final String testString = prefix + monthStr;
            
            addTestData( testData,
                         new ParseDateTestData( testString,
                                                refCal.get( Calendar.DATE ),
                                                refCal.get( Calendar.MONTH ) + 1,
                                                refCal.get( Calendar.YEAR ) ) );
         }
      }
   }
   
   
   
   private void addParseDateLiteral( Collection< Object[] > testData )
   {
      for ( String today : testLanguage.getToday() )
      {
         addTestData( testData, new ParseDateTestData( today,
                                                       thisDay,
                                                       thisMonthNum,
                                                       thisYear ) );
      }
      
      for ( String string : testLanguage.getTomorrow() )
      {
         addTestData( testData, new ParseDateTestData( string,
                                                       thisDay + 1,
                                                       thisMonthNum,
                                                       thisYear ) );
      }
      
      for ( String string : testLanguage.getYesterday() )
      {
         addTestData( testData, new ParseDateTestData( string,
                                                       thisDay - 1,
                                                       thisMonthNum,
                                                       thisYear ) );
      }
      
      for ( String string : testLanguage.getNever() )
      {
         final MolokoCalendar refCal = MolokoCalendar.getDatelessAndTimelessInstance();
         addTestData( testData,
                      new ParseDateTestData( string,
                                             refCal.get( Calendar.DATE ),
                                             refCal.get( Calendar.MONTH ) + 1,
                                             refCal.get( Calendar.YEAR ),
                                             false,
                                             false ) );
      }
      
      for ( String string : testLanguage.getNow() )
      {
         addTestData( testData, new ParseDateTestData( string,
                                                       thisDay,
                                                       thisMonthNum,
                                                       thisYear,
                                                       true,
                                                       true ) );
      }
   }
   
   
   
   private void addParseDateWithin_Amount( Collection< Object[] > testData )
   {
      addTestParseDateWithin( testData, Strings.EMPTY_STRING, Calendar.DATE );
   }
   
   
   
   private void addParseDateWithin_AmountDay( Collection< Object[] > testData )
   {
      for ( String day : testLanguage.getDayLiterals() )
      {
         addTestParseDateWithin( testData, day, Calendar.DATE );
      }
   }
   
   
   
   private void addParseDateWithin_AmountWeek( Collection< Object[] > testData )
   {
      for ( String week : testLanguage.getWeekLiterals() )
      {
         addTestParseDateWithin( testData, week, Calendar.WEEK_OF_YEAR );
      }
   }
   
   
   
   private void addParseDateWithin_AmountMonth( Collection< Object[] > testData )
   {
      for ( String month : testLanguage.getMonthLiterals() )
      {
         addTestParseDateWithin( testData, month, Calendar.MONTH );
      }
   }
   
   
   
   private void addParseDateWithin_AmountYear( Collection< Object[] > testData )
   {
      for ( String year : testLanguage.getYearLiterals() )
      {
         addTestParseDateWithin( testData, year, Calendar.YEAR );
      }
   }
   
   
   
   private void addTestParseDateWithin( Collection< Object[] > testData,
                                        String unit,
                                        int calUnit )
   {
      final String of = testLanguage.getOf().iterator().next();
      
      for ( int i = 1; i < 11; ++i )
      {
         for ( String numStr : testLanguage.getNumberStrings( i ) )
         {
            for ( String amount : Arrays.asList( String.valueOf( i ), numStr ) )
            {
               // No date
               MolokoCalendar refCal = todayCal.clone();
               refCal.add( calUnit, i );
               
               String testString = amount + " " + unit;
               
               addTestData( testData,
                            new ParseDateWithInTestData( testString,
                                                         thisDay,
                                                         thisMonthNum,
                                                         thisYear,
                                                         refCal.get( Calendar.DATE ),
                                                         refCal.get( Calendar.MONTH ) + 1,
                                                         refCal.get( Calendar.YEAR ) ) );
               
               // Today
               refCal = todayCal.clone();
               refCal.add( calUnit, i );
               
               testString = amount + " " + unit + " " + of
                  + testLanguage.getToday().iterator().next();
               
               addTestData( testData,
                            new ParseDateWithInTestData( testString,
                                                         thisDay,
                                                         thisMonthNum,
                                                         thisYear,
                                                         refCal.get( Calendar.DATE ),
                                                         refCal.get( Calendar.MONTH ) + 1,
                                                         refCal.get( Calendar.YEAR ) ) );
               
               // Now
               refCal = todayCal.clone();
               refCal.add( calUnit, i );
               
               testString = amount + " " + unit + " " + of
                  + testLanguage.getNow().iterator().next();
               
               addTestData( testData,
                            new ParseDateWithInTestData( testString,
                                                         thisDay,
                                                         thisMonthNum,
                                                         thisYear,
                                                         refCal.get( Calendar.DATE ),
                                                         refCal.get( Calendar.MONTH ) + 1,
                                                         refCal.get( Calendar.YEAR ) ) );
               
               // Never
               refCal = todayCal.clone();
               refCal.add( calUnit, i );
               
               testString = amount + " " + unit + " " + of
                  + testLanguage.getNever().iterator().next();
               
               refCal = MolokoCalendar.getDatelessAndTimelessInstance();
               addTestData( testData,
                            new ParseDateWithInTestData( testString,
                                                         refCal.get( Calendar.DATE ),
                                                         refCal.get( Calendar.MONTH ) + 1,
                                                         refCal.get( Calendar.YEAR ),
                                                         refCal.get( Calendar.DATE ),
                                                         refCal.get( Calendar.MONTH ) + 1,
                                                         refCal.get( Calendar.YEAR ),
                                                         false,
                                                         false ) );
               
               // Tomorrow
               refCal = todayCal.clone();
               refCal.add( calUnit, i );
               refCal.add( Calendar.DATE, 1 );
               
               testString = amount + " " + unit + " " + of
                  + testLanguage.getTomorrow().iterator().next();
               
               addTestData( testData,
                            new ParseDateWithInTestData( testString,
                                                         thisDay + 1,
                                                         thisMonthNum,
                                                         thisYear,
                                                         refCal.get( Calendar.DATE ),
                                                         refCal.get( Calendar.MONTH ) + 1,
                                                         refCal.get( Calendar.YEAR ) ) );
               
               // Yesterday
               refCal = todayCal.clone();
               refCal.add( calUnit, i );
               refCal.add( Calendar.DATE, -1 );
               
               testString = amount + " " + unit + " " + of
                  + testLanguage.getYesterday().iterator().next();
               
               addTestData( testData,
                            new ParseDateWithInTestData( testString,
                                                         thisDay - 1,
                                                         thisMonthNum,
                                                         thisYear,
                                                         refCal.get( Calendar.DATE ),
                                                         refCal.get( Calendar.MONTH ) + 1,
                                                         refCal.get( Calendar.YEAR ) ) );
            }
         }
      }
   }
   
   
   
   private void addTestData( Collection< Object[] > coll, Object testData )
   {
      coll.add( new Object[]
      { testData } );
   }
   
   
   public static class ParseDateTestData
   {
      public final String testString;
      
      public final int expectedDay;
      
      public final int expectedMonth;
      
      public final int expectedYear;
      
      public final boolean expectCalHasTime;
      
      public final boolean expectCalHasDate;
      
      
      
      public ParseDateTestData( String testString, int expectedDay,
         int expectedMonth, int expectedYear )
      {
         this( testString,
               expectedDay,
               expectedMonth,
               expectedYear,
               true,
               false );
      }
      
      
      
      public ParseDateTestData( String testString, int expectedDay,
         int expectedMonth, int expectedYear, boolean hasDate, boolean hasTime )
      {
         this.testString = testString;
         this.expectedDay = expectedDay;
         this.expectedMonth = expectedMonth;
         this.expectedYear = expectedYear;
         this.expectCalHasDate = hasDate;
         this.expectCalHasTime = hasTime;
      }
      
      
      
      @Override
      public String toString()
      {
         return testString;
      }
   }
   
   
   public static class ParseDateWithInTestData
   {
      public final String testString;
      
      public final int expectedStartDay;
      
      public final int expectedStartMonth;
      
      public final int expectedStartYear;
      
      public final int expectedEndDay;
      
      public final int expectedEndMonth;
      
      public final int expectedEndYear;
      
      public final boolean expectedStartHasDate;
      
      public final boolean expectedEndHasDate;
      
      
      
      public ParseDateWithInTestData( String testString, int expectedStartDay,
         int expectedStartMonth, int expectedStartYear, int expectedEndDay,
         int expectedEndMonth, int expectedEndYear )
      {
         this( testString,
               expectedStartDay,
               expectedStartMonth,
               expectedStartYear,
               expectedEndDay,
               expectedEndMonth,
               expectedEndYear,
               true,
               true );
      }
      
      
      
      public ParseDateWithInTestData( String testString, int expectedStartDay,
         int expectedStartMonth, int expectedStartYear, int expectedEndDay,
         int expectedEndMonth, int expectedEndYear, boolean expectStartHasDate,
         boolean expectEndHasDate )
      {
         this.testString = testString;
         this.expectedStartDay = expectedStartDay;
         this.expectedStartMonth = expectedStartMonth;
         this.expectedStartYear = expectedStartYear;
         this.expectedEndDay = expectedEndDay;
         this.expectedEndMonth = expectedEndMonth;
         this.expectedEndYear = expectedEndYear;
         this.expectedStartHasDate = expectStartHasDate;
         this.expectedEndHasDate = expectEndHasDate;
      }
      
      
      
      @Override
      public String toString()
      {
         return testString;
      }
   }
}
