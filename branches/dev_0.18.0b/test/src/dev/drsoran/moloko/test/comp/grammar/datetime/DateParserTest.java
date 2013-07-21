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

package dev.drsoran.moloko.test.comp.grammar.datetime;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.*;
import static dev.drsoran.moloko.test.TestConstants.*;

import java.util.Calendar;
import java.util.Locale;

import org.antlr.runtime.Lexer;
import org.junit.Test;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.grammar.datetime.AbstractANTLRDateParser;
import dev.drsoran.moloko.grammar.datetime.DateLexer;
import dev.drsoran.moloko.grammar.datetime.DateParser;
import dev.drsoran.moloko.grammar.datetime.DateParserImpl;
import dev.drsoran.moloko.grammar.datetime.IDateParser;
import dev.drsoran.moloko.test.MolokoDateParserTestCase;
import dev.drsoran.moloko.test.TestConstants;


public class DateParserTest extends MolokoDateParserTestCase
{
   @Test
   public void test_parseDate_Numeric() throws GrammarException
   {
      testDateNumericWithAllSeparators( 1, 10, 2010 );
   }
   
   
   
   @Override
   protected IDateParser createDateParser()
   {
      final AbstractANTLRDateParser dateParser = new DateParser();
      final Lexer dateLexer = new DateLexer();
      
      return new DateParserImpl( Locale.ENGLISH, dateParser, dateLexer );
   }
   
   
   
   private void testDateNumericWithAllSeparators( int day, int month, int year ) throws GrammarException
   {
      final int thisYear = TestConstants.DATE_TODAY.get( Calendar.YEAR );
      
      for ( String separator : new String[]
      { ".", ",", "/" } )
      {
         String parseString = day + separator + month;
         MolokoCalendar cal = testParseDate( parseString );
         assertDayMonthYear( parseString, cal, day, month, thisYear );
         
         parseString = day + separator + month + separator;
         cal = testParseDate( parseString );
         assertDayMonthYear( parseString, cal, day, month, thisYear );
         
         parseString = day + separator + month + separator + year;
         cal = testParseDate( parseString );
         assertDayMonthYear( parseString, cal, day, month, year );
      }
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
