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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.DateTimeParsing;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.domain.parsing.IDateTimeParsing;
import dev.drsoran.moloko.domain.parsing.datetime.AntlrDateTimeParserFactory;
import dev.drsoran.moloko.domain.parsing.lang.DateLanguageRepository;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.TaggedDataPoints;
import dev.drsoran.moloko.test.TestCalendarProvider;
import dev.drsoran.moloko.test.TestDateFormatter;
import dev.drsoran.moloko.test.WithTags;
import dev.drsoran.moloko.test.langs.DateParserTestLanguageDe;
import dev.drsoran.moloko.test.langs.DateParserTestLanguageEn;
import dev.drsoran.moloko.test.langs.TimeParserTestLanguageDe;
import dev.drsoran.moloko.test.langs.TimeParserTestLanguageEn;
import dev.drsoran.moloko.test.sources.DateTimeParsingTestDataSource;


@RunWith( Theories.class )
@Ignore
public class DateTimeParsingCombinedTest extends MolokoTestCase
{
   private final static MolokoCalendar today = TestCalendarProvider.getJune_10_2010_00_00_00()
                                                                   .getToday();
   
   @TaggedDataPoints( "parseDateTimeTestData" )
   public final static DateTimeParsingTestDataSource.TestData[] parseDateTimeTestDataEn = new DateTimeParsingTestDataSource( new TimeParserTestLanguageEn(),
                                                                                                                             new DateParserTestLanguageEn(),
                                                                                                                             new dev.drsoran.moloko.domain.parsing.lang.DateLanguage(),
                                                                                                                             today ).getTestData();
   
   @TaggedDataPoints( "parseDateTimeTestData" )
   public final static DateTimeParsingTestDataSource.TestData[] parseDateTimeTestDataDe = new DateTimeParsingTestDataSource( new TimeParserTestLanguageDe(),
                                                                                                                             new DateParserTestLanguageDe(),
                                                                                                                             new dev.drsoran.moloko.domain.parsing.lang.de.DateLanguage(),
                                                                                                                             today ).getTestData();
   
   private IDateTimeParsing dateTimeParsing;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      dateTimeParsing = new DateTimeParsing( new AntlrDateTimeParserFactory(),
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
         final MolokoCalendar cal = dateTimeParsing.parseDateTime( testData.dateAndTime );
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
   
   
   
   private void verifyParseDateTime( MolokoCalendar cal,
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
