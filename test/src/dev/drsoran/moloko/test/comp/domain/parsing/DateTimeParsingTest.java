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

package dev.drsoran.moloko.test.comp.domain.parsing;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.DateTimeParsing;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.domain.parsing.IDateTimeParsing;

import dev.drsoran.moloko.domain.parsing.datetime.AntlrDateTimeParserFactory;
import dev.drsoran.moloko.domain.parsing.datetime.ParseDateWithinReturn;
import dev.drsoran.moloko.domain.parsing.lang.DateLanguage;
import dev.drsoran.moloko.domain.parsing.lang.DateLanguageRepository;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.TaggedDataPoints;
import dev.drsoran.moloko.test.TestCalendarProvider;
import dev.drsoran.moloko.test.TestDateFormatter;
import dev.drsoran.moloko.test.WithTags;
import dev.drsoran.moloko.test.langs.DateParserTestLanguageEn;
import dev.drsoran.moloko.test.langs.TimeParserTestLanguageDe;
import dev.drsoran.moloko.test.langs.TimeParserTestLanguageEn;
import dev.drsoran.moloko.test.sources.DateParserTestDataSource;
import dev.drsoran.moloko.test.sources.DateTimeParsingTestDataSource;
import dev.drsoran.moloko.test.sources.TimeParserTestDataSource;
import dev.drsoran.moloko.util.Strings;


@RunWith( Theories.class )
public class DateTimeParsingTest extends MolokoTestCase
{
   @TaggedDataPoints( "parseTimeTestData" )
   public final static TimeParserTestDataSource.ParseTimeTestData[] parseTimeTestDataEn = new TimeParserTestDataSource( new TimeParserTestLanguageEn() ).getTheoryTestData();
   
   @TaggedDataPoints( "parseTimeTestData" )
   public final static TimeParserTestDataSource.ParseTimeTestData[] parseTimeTestDataDe = new TimeParserTestDataSource( new TimeParserTestLanguageDe() ).getTheoryTestData();
   
   @TaggedDataPoints( "parseTimeEstimateTestData" )
   public final static TimeParserTestDataSource.ParseTimeEstimateTestData[] parseTimeEstimateTestDataEn = new TimeParserTestDataSource( new TimeParserTestLanguageEn() ).getSecondTheoryTestData();
   
   @TaggedDataPoints( "parseTimeEstimateTestData" )
   public final static TimeParserTestDataSource.ParseTimeEstimateTestData[] parseTimeEstimateTestDataDe = new TimeParserTestDataSource( new TimeParserTestLanguageDe() ).getSecondTheoryTestData();
   
   @TaggedDataPoints( "parseDateWithinTestData" )
   public final static DateParserTestDataSource.ParseDateWithInTestData[] parseDateWithInTestDataEn = new DateParserTestDataSource( new DateLanguage(),
                                                                                                                                    new DateParserTestLanguageEn(),
                                                                                                                                    TestCalendarProvider.getDefault()
                                                                                                                                                        .getToday() ).getSecondTheoryTestData();
   
   @TaggedDataPoints( "parseDateWithinTestData" )
   public final static DateParserTestDataSource.ParseDateWithInTestData[] parseDateWithInTestDataDe = new DateParserTestDataSource( new dev.drsoran.moloko.domain.parsing.lang.de.DateLanguage(),
                                                                                                                                    new DateParserTestLanguageEn(),
                                                                                                                                    TestCalendarProvider.getDefault()
                                                                                                                                                        .getToday() ).getSecondTheoryTestData();
   
   @TaggedDataPoints( "parseDateTimeTestData" )
   public final static DateTimeParsingTestDataSource.TestData[] parseDateTimeTestDataEn = new DateTimeParsingTestDataSource( new TimeParserTestLanguageEn(),
                                                                                                                             new DateParserTestLanguageEn(),
                                                                                                                             new dev.drsoran.moloko.domain.parsing.lang.DateLanguage(),
                                                                                                                             TestCalendarProvider.getDefault()
                                                                                                                                                 .getToday() ).getTestData();
   
   private IDateTimeParsing dateTimeParsing;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      dateTimeParsing = new DateTimeParsing( new AntlrDateTimeParserFactory(),
                                             TestDateFormatter.get(),
                                             new DateLanguageRepository(),
                                             TestCalendarProvider.getDefault() );
   }
   
   
   
   @Theory
   public void testParseTime( @WithTags( clazz = DateTimeParsingTest.class,
                                         name = "parseTimeTestData" ) TimeParserTestDataSource.ParseTimeTestData testData )
   {
      try
      {
         final MolokoCalendar cal = dateTimeParsing.parseTime( testData.testString );
         
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
   public void testParseDateTimeCombined( @WithTags( clazz = DateTimeParsingTest.class,
                                                     name = "parseDateTimeTestData" ) DateTimeParsingTestDataSource.TestData testData )
   {
      try
      {
         final MolokoCalendar cal = dateTimeParsing.parseDateTime( testData.dateAndTime );
         
         assertThat( cal, notNullValue() );
         assertThat( "Wrong hour for <" + testData.dateAndTime + ">",
                     cal,
                     is( year( testData.expectedYear ) ) );
         assertThat( "Wrong month for <" + testData.dateAndTime + ">",
                     cal,
                     is( month( testData.expectedMonth ) ) );
         assertThat( "Wrong day for <" + testData.dateAndTime + ">",
                     cal,
                     is( day( testData.expectedDay ) ) );
         assertThat( "Wrong hour for <" + testData.dateAndTime + ">",
                     cal,
                     is( hour( testData.expectedHour ) ) );
         assertThat( "Wrong minute for <" + testData.dateAndTime + ">",
                     cal,
                     is( minute( testData.expectedMinute ) ) );
         assertThat( "Wrong second for <" + testData.dateAndTime + ">",
                     cal,
                     is( second( testData.expectedSecond ) ) );
         assertThat( "Wrong hasTime for <" + testData.dateAndTime + ">",
                     cal.hasTime(),
                     is( testData.expectCalHasTime ) );
         assertThat( "Wrong hasDate for <" + testData.dateAndTime + ">",
                     cal.hasDate(),
                     is( testData.expectCalHasDate ) );
      }
      catch ( GrammarException e )
      {
         fail( "Parsing <" + testData.dateAndTime + "> failed: "
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
   
}
