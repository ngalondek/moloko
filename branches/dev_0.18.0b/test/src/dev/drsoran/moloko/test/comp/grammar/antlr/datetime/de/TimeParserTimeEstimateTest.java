package dev.drsoran.moloko.test.comp.grammar.antlr.datetime.de;

import java.util.Collection;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Lexer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dev.drsoran.moloko.domain.parsing.lang.ILanguage;
import dev.drsoran.moloko.domain.parsing.lang.de.DateLanguage;
import dev.drsoran.moloko.grammar.antlr.datetime.de.DateTimeLexer;
import dev.drsoran.moloko.test.MolokoDateTimeParserTestCase;
import dev.drsoran.moloko.test.langs.ITimeParserTestLanguage;
import dev.drsoran.moloko.test.langs.TimeParserTestLanguageDe;
import dev.drsoran.moloko.test.sources.TimeParserTestDataSource;
import dev.drsoran.moloko.test.sources.TimeParserTestDataSource.ParseTimeEstimateTestData;


@RunWith( Parameterized.class )
public class TimeParserTimeEstimateTest extends MolokoDateTimeParserTestCase
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
