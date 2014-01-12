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

package dev.drsoran.test.comp.rtm.parsing;

import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.day;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.hour;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.minute;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.month;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.second;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.year;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dev.drsoran.Strings;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.TaggedDataPoints;
import dev.drsoran.moloko.test.TestCalendarProvider;
import dev.drsoran.moloko.test.TestDateFormatter;
import dev.drsoran.moloko.test.WithTags;
import dev.drsoran.moloko.test.langs.DateParserTestLanguageDe;
import dev.drsoran.moloko.test.langs.DateParserTestLanguageEn;
import dev.drsoran.moloko.test.langs.TimeParserTestLanguageDe;
import dev.drsoran.moloko.test.langs.TimeParserTestLanguageEn;
import dev.drsoran.moloko.test.sources.DateParserTestDataSource;
import dev.drsoran.moloko.test.sources.TimeParserTestDataSource;
import dev.drsoran.moloko.test.sources.TimeParserTestDataSource.Config;
import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.GrammarException;
import dev.drsoran.rtm.parsing.IRtmDateTimeParsing;
import dev.drsoran.rtm.parsing.RtmDateTimeParsing;
import dev.drsoran.rtm.parsing.datetime.AntlrDateTimeParserFactory;
import dev.drsoran.rtm.parsing.datetime.ParseDateWithinReturn;
import dev.drsoran.rtm.parsing.lang.DateLanguage;
import dev.drsoran.rtm.parsing.lang.DateLanguageRepository;


@RunWith( Theories.class )
public class DateTimeParsingTest extends MolokoTestCase
{
   private final static RtmCalendar today = TestCalendarProvider.getJune_10_2010_00_00_00()
                                                                .getToday();
   
   private final static DateParserTestDataSource dateParserSourceEn = new DateParserTestDataSource( new DateLanguage(),
                                                                                                    new DateParserTestLanguageEn(),
                                                                                                    today );
   
   private final static DateParserTestDataSource dateParserSourceDe = new DateParserTestDataSource( new dev.drsoran.rtm.parsing.lang.de.DateLanguage(),
                                                                                                    new DateParserTestLanguageDe(),
                                                                                                    today );
   
   @TaggedDataPoints( "parseTimeTestData" )
   public final static TimeParserTestDataSource.ParseTimeTestData[] parseTimeTestDataEn = new TimeParserTestDataSource( new TimeParserTestLanguageEn(),
                                                                                                                        Config.DateTimeParserTimeTests ).getTheoryTestData();
   
   @TaggedDataPoints( "parseTimeTestData" )
   public final static TimeParserTestDataSource.ParseTimeTestData[] parseTimeTestDataDe = new TimeParserTestDataSource( new TimeParserTestLanguageDe(),
                                                                                                                        Config.DateTimeParserTimeTests ).getTheoryTestData();
   
   @TaggedDataPoints( "parseDateTestData" )
   public final static DateParserTestDataSource.ParseDateTestData[] parseDateTestDataEn = dateParserSourceEn.getTheoryTestData();
   
   @TaggedDataPoints( "parseDateTestData" )
   public final static DateParserTestDataSource.ParseDateTestData[] parseDateTestDataDe = dateParserSourceDe.getTheoryTestData();
   
   @TaggedDataPoints( "parseTimeEstimateTestData" )
   public final static TimeParserTestDataSource.ParseTimeEstimateTestData[] parseTimeEstimateTestDataEn = new TimeParserTestDataSource( new TimeParserTestLanguageEn() ).getSecondTheoryTestData();
   
   @TaggedDataPoints( "parseTimeEstimateTestData" )
   public final static TimeParserTestDataSource.ParseTimeEstimateTestData[] parseTimeEstimateTestDataDe = new TimeParserTestDataSource( new TimeParserTestLanguageDe() ).getSecondTheoryTestData();
   
   @TaggedDataPoints( "parseDateWithinTestData" )
   public final static DateParserTestDataSource.ParseDateWithInTestData[] parseDateWithInTestDataEn = dateParserSourceEn.getSecondTheoryTestData();
   
   @TaggedDataPoints( "parseDateWithinTestData" )
   public final static DateParserTestDataSource.ParseDateWithInTestData[] parseDateWithInTestDataDe = dateParserSourceDe.getSecondTheoryTestData();
   
   private IRtmDateTimeParsing dateTimeParsing;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      dateTimeParsing = new RtmDateTimeParsing( new AntlrDateTimeParserFactory(),
                                                TestDateFormatter.get(),
                                                new DateLanguageRepository(),
                                                TestCalendarProvider.getJune_10_2010_00_00_00() );
   }
   
   
   
   @Theory
   public void testParseTime( @WithTags( clazz = DateTimeParsingTest.class,
                                         name = "parseTimeTestData" ) TimeParserTestDataSource.ParseTimeTestData testData )
   {
      try
      {
         final RtmCalendar cal = dateTimeParsing.parseTime( testData.testString );
         
         assertThat( cal, notNullValue() );
         assertThat( "Wrong hour for <" + testData.testString + ">",
                     cal,
                     is( hour( testData.expectedHour ) ) );
         assertThat( "Wrong minute for <" + testData.testString + ">",
                     cal,
                     is( minute( testData.expectedMinute ) ) );
         assertThat( "Wrong second for <" + testData.testString + ">",
                     cal,
                     is( second( testData.expectedSecond ) ) );
         assertThat( "Wrong hasTime for <" + testData.testString + ">",
                     cal.hasTime(),
                     is( testData.expectedHasTime ) );
      }
      catch ( GrammarException e )
      {
         fail( "Parsing <" + testData.testString + "> failed: "
            + e.getMessage() );
      }
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseTimeEmpty() throws GrammarException
   {
      dateTimeParsing.parseTime( Strings.EMPTY_STRING );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseTimeFailed() throws GrammarException
   {
      dateTimeParsing.parseTime( "after 4 weeks" );
   }
   
   
   
   @Theory
   public void testParseEstimated( @WithTags( clazz = DateTimeParsingTest.class,
                                              name = "parseTimeEstimateTestData" ) TimeParserTestDataSource.ParseTimeEstimateTestData testData )
   {
      try
      {
         final long millis = dateTimeParsing.parseEstimated( testData.testString );
         
         assertThat( "Wrong estimation millis for <" + testData.testString
            + ">", millis, is( testData.expectedMillis ) );
      }
      catch ( GrammarException e )
      {
         fail( "Parsing <" + testData.testString + "> failed: "
            + e.getMessage() );
      }
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseEstimatedEmpty() throws GrammarException
   {
      dateTimeParsing.parseEstimated( Strings.EMPTY_STRING );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseEstimatedFailed() throws GrammarException
   {
      dateTimeParsing.parseEstimated( "after 4 weeks" );
   }
   
   
   
   @Theory
   public void testParseDateTime_Time( @WithTags( clazz = DateTimeParsingTest.class,
                                                  name = "parseTimeTestData" ) TimeParserTestDataSource.ParseTimeTestData testData )
   {
      try
      {
         final RtmCalendar cal = dateTimeParsing.parseDateTime( testData.testString );
         
         final boolean isNever = !testData.expectedHasTime;
         
         verifyParseDateTime( cal,
                              testData.testString,
                              isNever ? 1970 : today.get( Calendar.YEAR ),
                              isNever ? Calendar.JANUARY + 1
                                     : today.get( Calendar.MONTH ) + 1,
                              isNever ? 1 : today.get( Calendar.DATE ),
                              testData.expectedHour,
                              testData.expectedMinute,
                              testData.expectedSecond,
                              testData.expectedHasTime,
                              false );
      }
      catch ( GrammarException e )
      {
         fail( "Parsing <" + testData.testString + "> failed: "
            + e.getMessage() );
      }
   }
   
   
   
   @Theory
   public void testParseDateTime_Date( @WithTags( clazz = DateTimeParsingTest.class,
                                                  name = "parseDateTestData" ) DateParserTestDataSource.ParseDateTestData testData )
   {
      try
      {
         final RtmCalendar cal = dateTimeParsing.parseDateTime( testData.testString );
         verifyParseDateTime( cal,
                              testData.testString,
                              testData.expectedYear,
                              testData.expectedMonth,
                              testData.expectedDay,
                              0,
                              0,
                              0,
                              testData.expectCalHasTime,
                              testData.expectCalHasDate );
      }
      catch ( GrammarException e )
      {
         fail( "Parsing <" + testData.testString + "> failed: "
            + e.getMessage() );
      }
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseDateTimeEmpty() throws GrammarException
   {
      dateTimeParsing.parseDateTime( Strings.EMPTY_STRING );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseDateTimeFailed() throws GrammarException
   {
      dateTimeParsing.parseDateTime( "after 4 weeks" );
   }
   
   
   
   @Theory
   public void testParseDateWithin( @WithTags( clazz = DateTimeParsingTest.class,
                                               name = "parseDateWithinTestData" ) DateParserTestDataSource.ParseDateWithInTestData testData )
   {
      try
      {
         final ParseDateWithinReturn res = dateTimeParsing.parseDateWithin( testData.testString );
         
         assertThat( res, notNullValue() );
         assertThat( "Wrong start year for <" + testData.testString + ">",
                     res.startEpoch,
                     is( year( testData.expectedStartYear ) ) );
         assertThat( "Wrong start month for <" + testData.testString + ">",
                     res.startEpoch,
                     is( month( testData.expectedStartMonth - 1 ) ) ); // -1 due to Calendar constants
         assertThat( "Wrong start day for <" + testData.testString + ">",
                     res.startEpoch,
                     is( day( testData.expectedStartDay ) ) );
         assertThat( "Wrong end year for <" + testData.testString + ">",
                     res.endEpoch,
                     is( year( testData.expectedEndYear ) ) );
         assertThat( "Wrong end month for <" + testData.testString + ">",
                     res.endEpoch,
                     is( month( testData.expectedEndMonth - 1 ) ) ); // -1 due to Calendar constants
         assertThat( "Wrong end day for <" + testData.testString + ">",
                     res.endEpoch,
                     is( day( testData.expectedEndDay ) ) );
         
         assertThat( "Wrong start hasDate for <" + testData.testString + ">",
                     res.startEpoch.hasDate(),
                     is( testData.expectedStartHasDate ) );
         assertThat( "Wrong end hasDate for <" + testData.testString + ">",
                     res.endEpoch.hasDate(),
                     is( testData.expectedEndHasDate ) );
      }
      catch ( GrammarException e )
      {
         fail( "Parsing <" + testData.testString + "> failed: "
            + e.getMessage() );
      }
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseDateWithinEmpty() throws GrammarException
   {
      dateTimeParsing.parseDateWithin( Strings.EMPTY_STRING );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseDateWithinFailed() throws GrammarException
   {
      dateTimeParsing.parseDateWithin( "after 4 weeks" );
   }
   
   
   
   @Test
   public void testExistsParserWithMatchingLocale()
   {
      assertThat( dateTimeParsing.existsParserWithMatchingLocale( Locale.ENGLISH ),
                  is( true ) );
      assertThat( dateTimeParsing.existsParserWithMatchingLocale( Locale.GERMAN ),
                  is( true ) );
      assertThat( dateTimeParsing.existsParserWithMatchingLocale( Locale.JAPANESE ),
                  is( false ) );
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
      
      assertThat( "Wrong year for <" + testString + ">", cal, is( year( y ) ) );
      assertThat( "Wrong month for <" + testString + ">",
                  cal,
                  is( month( m - 1 ) ) );
      assertThat( "Wrong day for <" + testString + ">", cal, is( day( d ) ) );
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
