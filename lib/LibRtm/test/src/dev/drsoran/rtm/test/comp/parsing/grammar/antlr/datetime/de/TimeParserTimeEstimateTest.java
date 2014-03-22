package dev.drsoran.rtm.test.comp.parsing.grammar.antlr.datetime.de;

import java.util.Collection;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Lexer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dev.drsoran.rtm.parsing.grammar.antlr.datetime.de.DateTimeLexer;
import dev.drsoran.rtm.parsing.lang.ILanguage;
import dev.drsoran.rtm.parsing.lang.de.DateLanguage;
import dev.drsoran.rtm.test.DateTimeParserTestCase;
import dev.drsoran.rtm.test.testdatasources.ITimeParserTestLanguage;
import dev.drsoran.rtm.test.testdatasources.TimeParserTestDataSource;
import dev.drsoran.rtm.test.testdatasources.TimeParserTestDataSource.ParseTimeEstimateTestData;
import dev.drsoran.rtm.test.testdatasources.TimeParserTestLanguageDe;


@RunWith( Parameterized.class )
public class TimeParserTimeEstimateTest extends DateTimeParserTestCase
{
   private final static ILanguage DATE_LANGUAGE = new DateLanguage();
   
   private final static ITimeParserTestLanguage TEST_LANGUAGE = new TimeParserTestLanguageDe();
   
   private final ParseTimeEstimateTestData testData;
   
   
   
   public TimeParserTimeEstimateTest( ParseTimeEstimateTestData testData )
   {
      this.testData = testData;
   }
   
   
   
   @Parameters( name = "{0}" )
   public static Collection< Object[] > parseTimeEstimateTestData()
   {
      final TimeParserTestDataSource testDataSource = new TimeParserTestDataSource( TEST_LANGUAGE );
      
      return testDataSource.getParseTimeEstimateTestData();
   }
   
   
   
   @Test
   public void testParseTimeEstimate()
   {
      parseTimeEstimate( testData.testString, testData.expectedMillis );
   }
   
   
   
   @Override
   protected ILanguage getDateLanguage()
   {
      return DATE_LANGUAGE;
   }
   
   
   
   @Override
   protected Lexer createDateTimeLexer( ANTLRInputStream inputStream )
   {
      final Lexer lexer = new DateTimeLexer( inputStream );
      return lexer;
   }
}
