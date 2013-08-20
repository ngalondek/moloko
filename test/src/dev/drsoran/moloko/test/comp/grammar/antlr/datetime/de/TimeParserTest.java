package dev.drsoran.moloko.test.comp.grammar.antlr.datetime.de;

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
import dev.drsoran.moloko.grammar.antlr.datetime.de.TimeLexer;
import dev.drsoran.moloko.test.MolokoTimeParserTestCase;
import dev.drsoran.moloko.test.langs.ITimeParserTestLanguage;
import dev.drsoran.moloko.test.langs.TimeParserTestLanguageDe;
import dev.drsoran.moloko.test.sources.TimeParserTestDataSource;
import dev.drsoran.moloko.test.sources.TimeParserTestDataSource.ParseTimeTestData;


@RunWith( Parameterized.class )
public class TimeParserTest extends MolokoTimeParserTestCase
{
   private final static ITimeParserTestLanguage TEST_LANGUAGE = new TimeParserTestLanguageDe();
   
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
      final MolokoCalendar cal = parseTime( testData.testString,
                                            false,
                                            testData.expectedHasTime );
      
      assertThat( cal, is( hour( testData.expectedHour ) ) );
      assertThat( cal, is( minute( testData.expectedMinute ) ) );
      assertThat( cal, is( second( testData.expectedSecond ) ) );
   }
   
   
   
   @Override
   protected Lexer createTimeLexer( ANTLRInputStream inputStream )
   {
      final Lexer lexer = new TimeLexer( inputStream );
      return lexer;
   }
}
