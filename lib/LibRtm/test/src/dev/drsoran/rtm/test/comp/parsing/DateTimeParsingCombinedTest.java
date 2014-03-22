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

package dev.drsoran.rtm.test.comp.parsing;

import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.day;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.hour;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.minute;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.month;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.second;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.year;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;
import dev.drsoran.rtm.parsing.RtmDateTimeParsing;
import dev.drsoran.rtm.parsing.datetime.AntlrDateTimeParserFactory;
import dev.drsoran.rtm.parsing.lang.DateLanguageRepository;
import dev.drsoran.rtm.test.TaggedDataPoints;
import dev.drsoran.rtm.test.TestCalendarProvider;
import dev.drsoran.rtm.test.TestDateFormatter;
import dev.drsoran.rtm.test.WithTags;
import dev.drsoran.rtm.test.testdatasources.DateParserTestLanguageDe;
import dev.drsoran.rtm.test.testdatasources.DateParserTestLanguageEn;
import dev.drsoran.rtm.test.testdatasources.DateTimeParsingTestDataSource;
import dev.drsoran.rtm.test.testdatasources.TimeParserTestLanguageDe;
import dev.drsoran.rtm.test.testdatasources.TimeParserTestLanguageEn;


@RunWith( Theories.class )
@Ignore
public class DateTimeParsingCombinedTest
{
   private final static RtmCalendar today = TestCalendarProvider.getJune_10_2010_00_00_00()
                                                                .getToday();
   
   @TaggedDataPoints( "parseDateTimeTestData" )
   public final static DateTimeParsingTestDataSource.TestData[] parseDateTimeTestDataEn = new DateTimeParsingTestDataSource( new TimeParserTestLanguageEn(),
                                                                                                                             new DateParserTestLanguageEn(),
                                                                                                                             new dev.drsoran.rtm.parsing.lang.DateLanguage(),
                                                                                                                             today ).getTestData();
   
   @TaggedDataPoints( "parseDateTimeTestData" )
   public final static DateTimeParsingTestDataSource.TestData[] parseDateTimeTestDataDe = new DateTimeParsingTestDataSource( new TimeParserTestLanguageDe(),
                                                                                                                             new DateParserTestLanguageDe(),
                                                                                                                             new dev.drsoran.rtm.parsing.lang.de.DateLanguage(),
                                                                                                                             today ).getTestData();
   
   private IRtmDateTimeParsing dateTimeParsing;
   
   
   
   @Before
   public void setUp() throws Exception
   {
      dateTimeParsing = new RtmDateTimeParsing( new AntlrDateTimeParserFactory(),
                                                TestDateFormatter.get(),
                                                new DateLanguageRepository(),
                                                TestCalendarProvider.getJune_10_2010_00_00_00() );
   }
   
   
   
   @Theory
   public void testParseDateTimeCombined( @WithTags( clazz = DateTimeParsingCombinedTest.class,
                                                     name = "parseDateTimeTestData" ) DateTimeParsingTestDataSource.TestData testData )
   {
      try
      {
         final RtmCalendar cal = dateTimeParsing.parseDateTime( testData.dateAndTime );
         verifyParseDateTime( cal,
                              testData.dateAndTime,
                              testData.expectedYear,
                              testData.expectedMonth,
                              testData.expectedDay,
                              testData.expectedHour,
                              testData.expectedMinute,
                              testData.expectedSecond,
                              testData.expectCalHasTime,
                              testData.expectCalHasDate );
      }
      catch ( GrammarException e )
      {
         fail( "Parsing <" + testData.dateAndTime + "> failed: "
            + e.getMessage() );
      }
   }
   
   
   
   private void verifyParseDateTime( RtmCalendar cal,
                                     String testString,
                                     int y,
                                     int m,
                                     int d,
                                     int h,
                                     int M,
                                     int s,
                                     boolean hasTime,
                                     boolean hasDate )
   {
      assertThat( cal, notNullValue() );
      
      if ( y != -1 )
      {
         assertThat( "Wrong year for <" + testString + ">", cal, is( year( y ) ) );
         assertThat( "Wrong month for <" + testString + ">",
                     cal,
                     is( month( m - 1 ) ) );
         assertThat( "Wrong day for <" + testString + ">", cal, is( day( d ) ) );
      }
      
      assertThat( "Wrong hour for <" + testString + ">", cal, is( hour( h ) ) );
      assertThat( "Wrong minute for <" + testString + ">",
                  cal,
                  is( minute( M ) ) );
      assertThat( "Wrong second for <" + testString + ">",
                  cal,
                  is( second( s ) ) );
      assertThat( "Wrong hasTime for <" + testString + ">",
                  cal.hasTime(),
                  is( hasTime ) );
      assertThat( "Wrong hasDate for <" + testString + ">",
                  cal.hasDate(),
                  is( hasDate ) );
   }
   
}
