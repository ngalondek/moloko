/* 
 *	Copyright (c) 2013 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.test.comp.grammar.antlr.datetime.de;

import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.day;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.month;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.year;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Collection;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Lexer;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.domain.parsing.MolokoCalenderProvider;
import dev.drsoran.moloko.domain.parsing.datetime.ParseDateWithinReturn;
import dev.drsoran.moloko.domain.parsing.lang.de.DateLanguage;
import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.grammar.antlr.datetime.de.DateLexer;
import dev.drsoran.moloko.test.MolokoDateParserTestCase;
import dev.drsoran.moloko.test.langs.DateParserTestLanguageDe;
import dev.drsoran.moloko.test.langs.IDateParserTestLanguage;
import dev.drsoran.moloko.test.sources.DateParserTestDataSource;


@RunWith( Parameterized.class )
public class DateParserDateWithInTest extends MolokoDateParserTestCase
{
   private final static ILanguage DATE_LANGUAGE = new DateLanguage();
   
   private final static IDateParserTestLanguage TEST_LANGUAGE = new DateParserTestLanguageDe();
   
   private final static MolokoCalendar TODAY_CAL;
   
   static
   {
      TODAY_CAL = MolokoCalendar.getInstance();
      TODAY_CAL.set( Calendar.YEAR, 2010 );
      TODAY_CAL.set( Calendar.MONTH, Calendar.JUNE );
      TODAY_CAL.set( Calendar.DATE, 10 );
      TODAY_CAL.setHasTime( false );
   }
   
   private final DateParserTestDataSource.ParseDateWithInTestData testData;
   
   
   
   public DateParserDateWithInTest(
      DateParserTestDataSource.ParseDateWithInTestData testData )
   {
      this.testData = testData;
   }
   
   
   
   @Parameters( name = "{0}" )
   public static Collection< Object[] > parseDateWithInTestData()
   {
      final DateParserTestDataSource testDataSource = new DateParserTestDataSource( DATE_LANGUAGE,
                                                                                    TEST_LANGUAGE,
                                                                                    TODAY_CAL );
      
      return testDataSource.getParseWithInDateTestData();
   }
   
   
   
   @Test
   public void testParseDateWithIn()
   {
      final ParseDateWithinReturn res = testParseDateWithin( testData.testString );
      
      assertDayMonthYear( testData.testString,
                          res.startEpoch,
                          testData.expectedStartDay,
                          testData.expectedStartMonth,
                          testData.expectedStartYear );
      
      if ( !testData.expectedStartHasDate )
      {
         assertThat( res.startEpoch.hasDate(), is( false ) );
      }
      
      assertDayMonthYear( testData.testString,
                          res.endEpoch,
                          testData.expectedEndDay,
                          testData.expectedEndMonth,
                          testData.expectedEndYear );
      
      if ( !testData.expectedEndHasDate )
      {
         assertThat( res.endEpoch.hasDate(), is( false ) );
      }
   }
   
   
   
   @Override
   protected ILanguage getDateLanguage()
   {
      return DATE_LANGUAGE;
   }
   
   
   
   @Override
   protected Lexer createDateLexer( ANTLRInputStream input )
   {
      final Lexer dateLexer = new DateLexer( input );
      return dateLexer;
   }
   
   
   
   @Override
   protected MolokoCalenderProvider getCalendarProvider()
   {
      final MolokoCalenderProvider calenderProvider = EasyMock.createNiceMock( MolokoCalenderProvider.class );
      EasyMock.expect( calenderProvider.getNow() )
              .andReturn( TODAY_CAL.clone() )
              .anyTimes();
      EasyMock.expect( calenderProvider.getToday() )
              .andReturn( TODAY_CAL.clone() )
              .anyTimes();
      EasyMock.replay( calenderProvider );
      
      return calenderProvider;
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
