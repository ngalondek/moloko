package dev.drsoran.rtm.test.comp.parsing.grammar.antlr.datetime;

import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.hour;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.minute;
import static dev.drsoran.rtm.test.matchers.RtmCalendarMatcher.second;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Lexer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.parsing.grammar.antlr.datetime.DateTimeLexer;
import dev.drsoran.rtm.parsing.lang.DateLanguage;
import dev.drsoran.rtm.parsing.lang.ILanguage;
import dev.drsoran.rtm.test.DateTimeParserTestCase;
import dev.drsoran.rtm.test.testdatasources.ITimeParserTestLanguage;
import dev.drsoran.rtm.test.testdatasources.TimeParserTestDataSource;
import dev.drsoran.rtm.test.testdatasources.TimeParserTestDataSource.ParseTimeTestData;
import dev.drsoran.rtm.test.testdatasources.TimeParserTestLanguageEn;


@RunWith( Parameterized.class )
public class TimeParserTest extends DateTimeParserTestCase
{
   private final static ILanguage DATE_LANGUAGE = new DateLanguage();
   
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
      
      return testDataSource.getTestData();
   }
   
   
   
   @Test
   public void testParseTime()
   {
      final RtmCalendar cal = parseTime( testData.testString,
                                         false,
                                         testData.expectedHasTime );
      
      assertThat( cal, is( hour( testData.expectedHour ) ) );
      assertThat( cal, is( minute( testData.expectedMinute ) ) );
      assertThat( cal, is( second( testData.expectedSecond ) ) );
   }
   
   
   
   @Override
   protected Lexer createDateTimeLexer( ANTLRInputStream inputStream )
   {
      final Lexer lexer = new DateTimeLexer( inputStream );
      return lexer;
   }
   
   
   
   @Override
   protected ILanguage getDateLanguage()
   {
      return DATE_LANGUAGE;
   }
}
