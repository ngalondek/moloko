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

import java.util.Collection;
import java.util.LinkedList;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.test.langs.IDateParserTestLanguage;
import dev.drsoran.moloko.test.langs.ITimeParserTestLanguage;


public class DateTimeParsingTestDataSource
{
   private final static String[] DATE_TIME_SEP = new String[]
   { " ", "," };
   
   private final ITimeParserTestLanguage timeLanguage;
   
   private final IDateParserTestLanguage dateParserTestLanguage;
   
   private final ILanguage dateLanguage;
   
   private final MolokoCalendar today;
   
   
   
   public DateTimeParsingTestDataSource(
      ITimeParserTestLanguage timeParserTestLanguage,
      IDateParserTestLanguage dateParserTestLanguage, ILanguage dateLanguage,
      MolokoCalendar today )
   {
      this.timeLanguage = timeParserTestLanguage;
      this.dateParserTestLanguage = dateParserTestLanguage;
      this.dateLanguage = dateLanguage;
      this.today = today;
      
   }
   
   
   
   public TestData[] getTestData()
   {
      final Collection< TestData > testData = new LinkedList< TestData >();
      
      addTimeThenDateTests( testData );
      addDateThenTimeTests( testData );
      
      return testData.toArray( new TestData[ testData.size() ] );
   }
   
   
   
   private void addTimeThenDateTests( Collection< TestData > testData )
   {
      final TimeParserTestDataSource timeSource = new TimeParserTestDataSource( timeLanguage,
                                                                                TimeParserTestDataSource.Config.Minimal );
      
      final DateParserTestDataSource dateSource = new DateParserTestDataSource( dateLanguage,
                                                                                dateParserTestLanguage,
                                                                                today,
                                                                                DateParserTestDataSource.Config.Minimal );
      
      for ( String sep : DATE_TIME_SEP )
      {
         for ( TimeParserTestDataSource.ParseTimeTestData timeData : timeSource.getTheoryTestData() )
         {
            for ( DateParserTestDataSource.ParseDateTestData dateData : dateSource.getTheoryTestData() )
            {
               final String testString = timeData.testString + sep
                  + dateData.testString;
               
               testData.add( new TestData( testString,
                                           dateData.expectedDay,
                                           dateData.expectedMonth,
                                           dateData.expectedYear,
                                           timeData.expectedHour,
                                           timeData.expectedMinute,
                                           timeData.expectedSecond,
                                           timeData.expectedHasTime,
                                           dateData.expectCalHasDate ) );
            }
         }
      }
   }
   
   
   
   private void addDateThenTimeTests( Collection< TestData > testData )
   {
      final TimeParserTestDataSource timeSource = new TimeParserTestDataSource( timeLanguage,
                                                                                TimeParserTestDataSource.Config.Minimal );
      
      final DateParserTestDataSource dateSource = new DateParserTestDataSource( dateLanguage,
                                                                                dateParserTestLanguage,
                                                                                today,
                                                                                DateParserTestDataSource.Config.Minimal );
      
      for ( String sep : DATE_TIME_SEP )
      {
         for ( TimeParserTestDataSource.ParseTimeTestData timeData : timeSource.getTheoryTestData() )
         {
            for ( DateParserTestDataSource.ParseDateTestData dateData : dateSource.getTheoryTestData() )
            {
               final String testString = dateData.testString + sep
                  + timeData.testString;
               
               testData.add( new TestData( testString,
                                           dateData.expectedDay,
                                           dateData.expectedMonth,
                                           dateData.expectedYear,
                                           timeData.expectedHour,
                                           timeData.expectedMinute,
                                           timeData.expectedSecond,
                                           timeData.expectedHasTime,
                                           dateData.expectCalHasDate ) );
            }
         }
      }
   }
   
   
   public static class TestData
   {
      public final String dateAndTime;
      
      public final int expectedDay;
      
      public final int expectedMonth;
      
      public final int expectedYear;
      
      public final int expectedHour;
      
      public final int expectedMinute;
      
      public final int expectedSecond;
      
      public final boolean expectCalHasTime;
      
      public final boolean expectCalHasDate;
      
      
      
      public TestData( String dateAndTime, int expectedDay, int expectedMonth,
         int expectedYear, int expectedHour, int expectedMinute,
         int expectedSecond, boolean expectCalHasTime, boolean expectCalHasDate )
      {
         this.dateAndTime = dateAndTime;
         this.expectedDay = expectedDay;
         this.expectedMonth = expectedMonth;
         this.expectedYear = expectedYear;
         this.expectedHour = expectedHour;
         this.expectedMinute = expectedMinute;
         this.expectedSecond = expectedSecond;
         this.expectCalHasTime = expectCalHasTime;
         this.expectCalHasDate = expectCalHasDate;
      }
      
      
      
      @Override
      public String toString()
      {
         return dateAndTime;
      }
   }
}
