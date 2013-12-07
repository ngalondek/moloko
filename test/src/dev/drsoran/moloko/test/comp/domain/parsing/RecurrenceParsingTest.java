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

import static dev.drsoran.moloko.test.IterableAsserts.assertEqualSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import dev.drsoran.Strings;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.parsing.GrammarException;
import dev.drsoran.moloko.domain.parsing.IRecurrenceParsing;
import dev.drsoran.moloko.domain.parsing.RecurrenceParsing;
import dev.drsoran.moloko.domain.parsing.lang.DateLanguageRepository;
import dev.drsoran.moloko.domain.parsing.recurrence.AntlrRecurrenceParserFactory;
import dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.test.TaggedDataPoints;
import dev.drsoran.moloko.test.TestDateFormatter;
import dev.drsoran.moloko.test.TestDateTimeParsing;
import dev.drsoran.moloko.test.WithTags;
import dev.drsoran.moloko.test.langs.RecurrenceParserTestLanguageDe;
import dev.drsoran.moloko.test.langs.RecurrenceParserTestLanguageEn;
import dev.drsoran.moloko.test.langs.RecurrenceSentenceTestLanguageEn;
import dev.drsoran.moloko.test.sources.RecurrencePatternCollectorTestDataSource;
import dev.drsoran.moloko.test.sources.RecurrenceSentenceTestDataSource;
import dev.drsoran.moloko.test.sources.RecurrenceTestDataSource;


@RunWith( Theories.class )
public class RecurrenceParsingTest extends MolokoTestCase
{
   @TaggedDataPoints( "recurrenceTestData" )
   public final static RecurrenceTestDataSource.TestData[] recurrenceTestDataEn = new RecurrenceTestDataSource( new RecurrenceParserTestLanguageEn(),
                                                                                                                TestDateFormatter.get() ).getTheoryTestData();
   
   @TaggedDataPoints( "recurrenceTestData" )
   public final static RecurrenceTestDataSource.TestData[] recurrenceTestDataDe = new RecurrenceTestDataSource( new RecurrenceParserTestLanguageDe(),
                                                                                                                TestDateFormatter.get() ).getTheoryTestData();
   
   @TaggedDataPoints( "tokenCollectorTestData" )
   public final static RecurrencePatternCollectorTestDataSource.TestData[] tokenizeTestData = new RecurrencePatternCollectorTestDataSource().getTheoryTestData();
   
   @TaggedDataPoints( "recurrenceSentenceTestData" )
   public final static RecurrenceSentenceTestDataSource.TestData[] recurrenceSentenceTestDataEn = new RecurrenceSentenceTestDataSource( RecurrenceSentenceTestLanguageEn.get() ).getTheoryTestData();
   
   @TaggedDataPoints( "patternOrderTestData" )
   public final static PatternOrderTestData[] patternOrderTestData = new PatternOrderTestData[]
   {
    new PatternOrderTestData( "INTERVAL=1;FREQ=YEARLY",
                              "FREQ=YEARLY;INTERVAL=1" ),
    new PatternOrderTestData( "BYDAY=MO;INTERVAL=1;FREQ=YEARLY",
                              "FREQ=YEARLY;INTERVAL=1;BYDAY=MO" ),
    new PatternOrderTestData( "BYMONTHDAY=1;BYDAY=MO;INTERVAL=1;FREQ=YEARLY",
                              "FREQ=YEARLY;INTERVAL=1;BYDAY=MO;BYMONTHDAY=1" ),
    new PatternOrderTestData( "BYMONTHDAY=1;UNTIL=2010;BYDAY=MO;INTERVAL=1;FREQ=YEARLY",
                              "FREQ=YEARLY;INTERVAL=1;BYDAY=MO;BYMONTHDAY=1;UNTIL=2010" ),
    new PatternOrderTestData( "BYMONTHDAY=1;BYMONTH=2;UNTIL=2010;BYDAY=MO;INTERVAL=1;FREQ=YEARLY",
                              "FREQ=YEARLY;INTERVAL=1;BYDAY=MO;BYMONTHDAY=1;BYMONTH=2;UNTIL=2010" ),
    new PatternOrderTestData( "BYMONTHDAY=1;COUNT=2;UNTIL=2010;BYDAY=MO;INTERVAL=1;FREQ=YEARLY",
                              "FREQ=YEARLY;INTERVAL=1;BYDAY=MO;BYMONTHDAY=1;UNTIL=2010;COUNT=2" ) };
   
   private IRecurrenceParsing recurrenceParsing;
   
   
   
   @Override
   @Before
   public void setUp() throws Exception
   {
      super.setUp();
      recurrenceParsing = new RecurrenceParsing( new AntlrRecurrenceParserFactory(),
                                                 RecurrenceSentenceTestLanguageEn.get(),
                                                 TestDateFormatter.get(),
                                                 TestDateTimeParsing.get( RecurrenceTestDataSource.UNTIL_DATE ),
                                                 new DateLanguageRepository() );
   }
   
   
   
   @Theory
   public void testParseRecurrencePatternToSentence( @WithTags( clazz = RecurrenceParsingTest.class,
                                                                name = "recurrenceSentenceTestData" ) RecurrenceSentenceTestDataSource.TestData testData )
   {
      try
      {
         final String sentence = recurrenceParsing.parseRecurrencePatternToSentence( testData.pattern,
                                                                                     testData.every );
         
         assertThat( sentence, notNullValue() );
         assertThat( sentence, equalTo( testData.sentence ) );
      }
      catch ( GrammarException e )
      {
         fail( "Parsing <" + testData.pattern + "> failed: " + e.getMessage() );
      }
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseRecurrencePatternToSentenceEmpty() throws GrammarException
   {
      recurrenceParsing.parseRecurrencePatternToSentence( Strings.EMPTY_STRING,
                                                          false );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseRecurrencePatternToSentenceFail() throws GrammarException
   {
      recurrenceParsing.parseRecurrencePatternToSentence( "FREQ=YEARLY;INTERVAL=e",
                                                          false );
   }
   
   
   
   @Theory
   public void testTokenizeRecurrencePattern( @WithTags( clazz = RecurrenceParsingTest.class,
                                                         name = "tokenCollectorTestData" ) RecurrencePatternCollectorTestDataSource.TestData testData )
   {
      try
      {
         final Map< Integer, List< Object >> tokens = recurrenceParsing.tokenizeRecurrencePattern( testData.pattern );
         
         assertThat( tokens, notNullValue() );
         assertSameTokens( tokens, testData.expectedTokens );
      }
      catch ( GrammarException e )
      {
         fail( "Tokenizing <" + testData.pattern + "> failed: "
            + e.getMessage() );
      }
   }
   
   
   
   @Test
   public void testTokenizeRecurrencePatternEmpty() throws GrammarException
   {
      final Map< Integer, List< Object >> patternMap = recurrenceParsing.tokenizeRecurrencePattern( Strings.EMPTY_STRING );
      assertThat( patternMap, notNullValue() );
      assertThat( patternMap.size(), is( 0 ) );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testTokenizeRecurrencePatternFail() throws GrammarException
   {
      recurrenceParsing.tokenizeRecurrencePattern( "FREQ=YEARLY;INTERVAL=e" );
   }
   
   
   
   @Theory
   public void testParseRecurrence( @WithTags( clazz = RecurrenceParsingTest.class,
                                               name = "recurrenceTestData" ) RecurrenceTestDataSource.TestData testData )
   {
      try
      {
         final Recurrence res = recurrenceParsing.parseRecurrence( testData.sentence );
         
         assertThat( "No result for <" + testData.sentence + ">",
                     res,
                     notNullValue() );
         
         final Map< String, Object > expectedPattern = new LinkedHashMap< String, Object >( testData.expectedPattern );
         final Boolean expectedIsEvery = (Boolean) expectedPattern.remove( RecurrencePatternSyntax.IS_EVERY );
         
         assertThat( "Wrong isEvery for <" + testData.sentence + ">",
                     res.isEveryRecurrence(),
                     is( expectedIsEvery != null ? expectedIsEvery
                                                : Boolean.FALSE ) );
         
         final Map< String, Object > resPattern = splitPattern( res.getPattern() );
         assertSamePattern( resPattern, expectedPattern );
      }
      catch ( GrammarException e )
      {
         fail( "Parsing <" + testData.sentence + "> failed: " + e.getMessage() );
      }
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseRecurrenceEmpty() throws GrammarException
   {
      recurrenceParsing.parseRecurrence( Strings.EMPTY_STRING );
   }
   
   
   
   @Test( expected = GrammarException.class )
   public void testParseRecurrenceFail() throws GrammarException
   {
      recurrenceParsing.parseRecurrence( "this will not work!!" );
   }
   
   
   
   @Theory
   public void testEnsureRecurrencePatternOrder( @WithTags( clazz = RecurrenceParsingTest.class,
                                                            name = "patternOrderTestData" ) PatternOrderTestData testData )
   {
      final String reorderedPattern = recurrenceParsing.ensureRecurrencePatternOrder( testData.testPattern );
      assertThat( "Wrong pattern order for <" + testData.testPattern + ">",
                  reorderedPattern,
                  equalTo( testData.expectdPattern ) );
   }
   
   
   
   @Test
   public void testExistsParserWithMatchingLocale()
   {
      assertThat( recurrenceParsing.existsParserWithMatchingLocale( Locale.ENGLISH ),
                  is( true ) );
      assertThat( recurrenceParsing.existsParserWithMatchingLocale( Locale.GERMAN ),
                  is( true ) );
      assertThat( recurrenceParsing.existsParserWithMatchingLocale( Locale.JAPANESE ),
                  is( false ) );
   }
   
   
   
   private Map< String, Object > splitPattern( String parsedPattern )
   {
      final String[] operatorValuePairs = parsedPattern.split( RecurrencePatternSyntax.OPERATOR_SEP );
      final Map< String, Object > operatorsWithValues = new LinkedHashMap< String, Object >( operatorValuePairs.length );
      
      for ( String operatorWithValue : operatorValuePairs )
      {
         final String[] operatorAndValue = operatorWithValue.split( RecurrencePatternSyntax.OPERATOR_VALUE_SEP );
         operatorsWithValues.put( operatorAndValue[ 0 ], operatorAndValue[ 1 ] );
      }
      
      return operatorsWithValues;
   }
   
   
   
   private void assertSamePattern( Map< String, Object > pattern,
                                   Map< String, Object > expectedPattern )
   {
      final Iterator< String > patternIter = pattern.keySet().iterator();
      final Iterator< String > expectedIter = expectedPattern.keySet()
                                                             .iterator();
      
      while ( patternIter.hasNext() )
      {
         final String operator = patternIter.next();
         final String expectedOperator = expectedIter.next();
         
         assertThat( "Unexpected operator <" + operator + ">",
                     operator,
                     equalTo( expectedOperator ) );
         
         final String values = pattern.get( operator ).toString();
         final String expectedValues = expectedPattern.get( operator )
                                                      .toString();
         
         assertThat( "Unexpected value for <" + operator + ">",
                     values,
                     equalTo( expectedValues ) );
      }
   }
   
   
   
   private void assertSameTokens( Map< Integer, List< Object > > tokens,
                                  Map< Integer, List< Object > > expected )
   {
      Iterator< Integer > tokensIter = tokens.keySet().iterator();
      Iterator< Integer > expectedIter = expected.keySet().iterator();
      
      while ( tokensIter.hasNext() )
      {
         final Integer token = tokensIter.next();
         final Integer expectedToken = expectedIter.next();
         
         assertThat( "Wrong token type", token, is( expectedToken ) );
         
         final List< Object > tokenValues = tokens.get( token );
         final List< Object > expectedTokenValues = expected.get( token );
         
         assertEqualSet( expectedTokenValues, tokenValues );
      }
   }
   
   
   private static class PatternOrderTestData
   {
      public final String testPattern;
      
      public final String expectdPattern;
      
      
      
      public PatternOrderTestData( String testPattern, String expectdPattern )
      {
         this.testPattern = testPattern;
         this.expectdPattern = expectdPattern;
      }
   }
}
