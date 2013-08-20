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

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.test.langs.IDateParserTestLanguage;
import dev.drsoran.moloko.test.langs.ITimeParserTestLanguage;


public class DateTimeParsingTestDataSource
{
   private final static String[] DATE_TIME_SEP = new String[]
   { " ", "," };
   
   private final ITimeParserTestLanguage timeParserTestLanguage;
   
   private final IDateParserTestLanguage dateParserTestLanguage;
   
   private final ILanguage dateLanguage;
   
   private final MolokoCalendar today;
   
   
   
   public DateTimeParsingTestDataSource(
      ITimeParserTestLanguage timeParserTestLanguage,
      IDateParserTestLanguage dateParserTestLanguage, ILanguage dateLanguage,
      MolokoCalendar today )
   {
      this.timeParserTestLanguage = timeParserTestLanguage;
      this.dateParserTestLanguage = dateParserTestLanguage;
      this.dateLanguage = dateLanguage;
      this.today = today;
   }
   
   
   
   public TestData[] getTestData()
   {
      final TimeParserTestDataSource.ParseTimeTestData[] timeParserTestData = new TimeParserTestDataSource( timeParserTestLanguage ).getTheoryTestData();
      final DateParserTestDataSource.ParseDateTestData[] dateParserTestData = new DateParserTestDataSource( dateLanguage,
                                                                                                            dateParserTestLanguage,
                                                                                                            today ).getTheoryTestData();
      
      int testDataSliceCount = Math.min( timeParserTestData.length,
                                         dateParserTestData.length );
      testDataSliceCount = Math.min( testDataSliceCount, 500 );
      
      final TestData[] testData = new TestData[ testDataSliceCount
         * DATE_TIME_SEP.length ];
      
      int i = 0;
      for ( String sep : DATE_TIME_SEP )
      {
         for ( int j = 0; j < testDataSliceCount; ++j )
         {
            final TimeParserTestDataSource.ParseTimeTestData parseTimeTestData = timeParserTestData[ j ];
            final DateParserTestDataSource.ParseDateTestData parseDateTestData = dateParserTestData[ j ];
            
            final String dateTime = parseTimeTestData.testString + sep
               + parseDateTestData.testString;
            
            testData[ i++ ] = new TestData( dateTime,
                                            parseDateTestData.expectedDay,
                                            parseDateTestData.expectedMonth,
                                            parseDateTestData.expectedYear,
                                            parseTimeTestData.expectedHour,
                                            parseTimeTestData.expectedMinute,
                                            parseTimeTestData.expectedSecond,
                                            parseTimeTestData.expectedHasTime
                                               && parseDateTestData.expectCalHasTime,
                                            parseDateTestData.expectCalHasDate );
         }
      }
      
      return testData;
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
