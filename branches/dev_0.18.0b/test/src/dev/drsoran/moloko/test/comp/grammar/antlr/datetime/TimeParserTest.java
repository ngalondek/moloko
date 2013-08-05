package dev.drsoran.moloko.test.comp.grammar.antlr.datetime;

import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.hour;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.minute;
import static dev.drsoran.moloko.test.matchers.MolokoCalendarMatcher.second;
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
import dev.drsoran.moloko.grammar.antlr.datetime.TimeLexer;
import dev.drsoran.moloko.test.ITimeParserTestLanguage;
import dev.drsoran.moloko.test.MolokoTimeParserTestCase;
import dev.drsoran.moloko.test.TimeParserTestDataSource;
import dev.drsoran.moloko.test.TimeParserTestDataSource.ParseTimeTestData;


@RunWith( Parameterized.class )
public class TimeParserTest extends MolokoTimeParserTestCase
{
   private final static ITimeParserTestLanguage TEST_LANGUAGE = new TimeParserTestLanguageEn();
   
   private final ParseTimeTestData testData;
   
   
   
   public TimeParserTest( ParseTimeTestData testData )
   {
      this.testData = testData;
   }
   
   
   
   @Parameters( name = "{0}" )
   public static Collection< Object[] > parseTimeTestData()
   {
      final TimeParserTestDataSource testDataSource = new TimeParserTestDataSource( TEST_LANGUAGE );
      
      return testDataSource.getParseTimeTestData();
   }
   
   
   
   @Test
   public void testParseTime()
   {
      final MolokoCalendar cal = parseTime( testData.testString,
                                            false,
                                            testData.expectedHasTime );
      
      assertThat( cal, is( hour( testData.expectedHour ) ) );
      assertThat( cal, is( minute( testData.expectedMinute ) ) );
      assertThat( cal, is( second( testData.expectedSecond ) ) );
   }
   
   
   
   //
   //
   //
   // @Test
   // public void test_parseTimeEstimate_day() throws Exception
   // {
   // testParseTimeEstimate( "1d", DateUtils.DAY_IN_MILLIS );
   // testParseTimeEstimate( "1 day", DateUtils.DAY_IN_MILLIS );
   // testParseTimeEstimate( "1 days", DateUtils.DAY_IN_MILLIS );
   // }
   //
   //
   //
   // @Test
   // public void test_parseTimeEstimate_day_min() throws Exception
   // {
   // testParseTimeEstimate( "1 day 15 min", DateUtils.DAY_IN_MILLIS + 15
   // * DateUtils.MINUTE_IN_MILLIS );
   //
   // testParseTimeEstimate( "1 day and 15 min", DateUtils.DAY_IN_MILLIS + 15
   // * DateUtils.MINUTE_IN_MILLIS );
   //
   // testParseTimeEstimate( "1 day,15 min", DateUtils.DAY_IN_MILLIS + 15
   // * DateUtils.MINUTE_IN_MILLIS );
   // }
   //
   //
   //
   // @Test
   // public void test_parseTimeEstimate_min_days_decimal_hour() throws Exception
   // {
   // testParseTimeEstimate( "1 h 15 min 2 days 1.5 hours ",
   // DateUtils.HOUR_IN_MILLIS + 15
   // * DateUtils.MINUTE_IN_MILLIS + 2
   // * DateUtils.DAY_IN_MILLIS
   // + DateUtils.HOUR_IN_MILLIS + 30
   // * DateUtils.MINUTE_IN_MILLIS );
   //
   // testParseTimeEstimate( "1 h, 15 min 2 days and 1.5 hours ",
   // DateUtils.HOUR_IN_MILLIS + 15
   // * DateUtils.MINUTE_IN_MILLIS + 2
   // * DateUtils.DAY_IN_MILLIS
   // + DateUtils.HOUR_IN_MILLIS + 30
   // * DateUtils.MINUTE_IN_MILLIS );
   // }
   //
   //
   //
   // @Test
   // public void test_parseTimeEstimate_min_sec() throws Exception
   // {
   // testParseTimeEstimate( "1 min 1 second", DateUtils.MINUTE_IN_MILLIS
   // + DateUtils.SECOND_IN_MILLIS );
   // }
   //
   //
   //
   @Override
   protected Lexer createTimeLexer( ANTLRInputStream inputStream )
   {
      final Lexer lexer = new TimeLexer( inputStream );
      return lexer;
   }
}
