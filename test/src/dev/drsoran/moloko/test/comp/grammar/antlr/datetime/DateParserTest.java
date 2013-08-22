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

package dev.drsoran.moloko.test.comp.grammar.antlr.datetime;

import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.day;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.month;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.year;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Lexer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.lang.DateLanguage;
import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.grammar.antlr.datetime.DateTimeLexer;
import dev.drsoran.moloko.test.MolokoDateTimeParserTestCase;
import dev.drsoran.moloko.test.TestCalendarProvider;
import dev.drsoran.moloko.test.langs.DateParserTestLanguageEn;
import dev.drsoran.moloko.test.langs.IDateParserTestLanguage;
import dev.drsoran.moloko.test.sources.DateParserTestDataSource;


@RunWith( Parameterized.class )
public class DateParserTest extends MolokoDateTimeParserTestCase
{
   private final static ILanguage DATE_LANGUAGE = new DateLanguage();
   
   private final static IDateParserTestLanguage TEST_LANGUAGE = new DateParserTestLanguageEn();
   
   private final DateParserTestDataSource.ParseDateTestData testData;
   
   
   
   public DateParserTest( DateParserTestDataSource.ParseDateTestData testData )
   {
      this.testData = testData;
   }
   
   
   
   @Parameters( name = "{0}" )
   public static Collection< Object[] > parseDateTestData()
   {
      final DateParserTestDataSource testDataSource = new DateParserTestDataSource( DATE_LANGUAGE,
                                                                                    TEST_LANGUAGE,
                                                                                    TestCalendarProvider.getJune_10_2010_00_00_00()
                                                                                                        .getToday() );
      
      return testDataSource.getTestData();
   }
   
   
   
   @Test
   public void testParseDate()
   {
      final MolokoCalendar cal = testParseDate( testData.testString,
                                                testData.expectCalHasTime );
      assertDayMonthYear( testData.testString,
                          cal,
                          testData.expectedDay,
                          testData.expectedMonth,
                          testData.expectedYear );
   }
   
   
   
   @Override
   protected ILanguage getDateLanguage()
   {
      return DATE_LANGUAGE;
   }
   
   
   
   @Override
   protected Lexer createDateTimeLexer( ANTLRInputStream input )
   {
      final Lexer dateLexer = new DateTimeLexer( input );
      return dateLexer;
   }
   
   
   
   private static void assertDayMonthYear( String parseString,
                                           MolokoCalendar cal,
                                           int day,
                                           int month,
                                           int year )
   {
      assertThat( "Wrong year for <" + parseString + ">",
                  cal,
                  is( year( year ) ) );
      assertThat( "Wrong month for <" + parseString + ">",
                  cal,
                  is( month( month - 1 ) ) ); // -1 due to Calendar constants
      assertThat( "Wrong day for <" + parseString + ">", cal, is( day( day ) ) );
   }
}
