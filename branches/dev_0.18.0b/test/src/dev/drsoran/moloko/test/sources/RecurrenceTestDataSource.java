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

package dev.drsoran.moloko.test.sources;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.antlr.v4.runtime.misc.Pair;

import dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternOperatorComp;
import dev.drsoran.moloko.test.langs.IRecurrenceParserTestLanguage;
import static dev.drsoran.moloko.domain.parsing.recurrence.RecurrencePatternSyntax.*;
import dev.drsoran.moloko.util.Strings;


public class RecurrenceTestDataSource
{
   private final IRecurrenceParserTestLanguage language;
   
   
   
   public RecurrenceTestDataSource( IRecurrenceParserTestLanguage language )
   {
      this.language = language;
   }
   
   
   
   public Collection< Object[] > getTestData()
   {
      final Collection< Object[] > testData = new LinkedList< Object[] >();
      
      addInterval0( testData );
      addIntervalNeg( testData );
      // addXst0( testData );
      // addXstNeg( testData );
      
      addSentenceYearly( testData );
      // addSentenceYearlyOnXst( testData );
      // addSentenceYearlyOnMultipleXst( testData );
      // addSentenceYearlyOnXstWeekdays( testData );
      addSentenceYearlyOnXstWeekdaysOfMonthInterval( testData );
      
      return testData;
   }
   
   
   
   private void addInterval0( Collection< Object[] > testData )
   {
      addTestData( new TestData( language.getEvery().iterator().next()
                                    + " 0 "
                                    + language.getYearLiterals()
                                              .iterator()
                                              .next(),
                                 new ResultBuilder().add( IS_EVERY, true )
                                                    .add( OP_FREQ, VAL_YEARLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addIntervalNeg( Collection< Object[] > testData )
   {
      addTestData( new TestData( language.getEvery().iterator().next()
                                    + " -3 "
                                    + language.getYearLiterals()
                                              .iterator()
                                              .next(),
                                 new ResultBuilder().add( IS_EVERY, true )
                                                    .add( OP_FREQ, VAL_YEARLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addSentenceYearly( Collection< Object[] > testData )
   {
      for ( int i = 1; i < 11; ++i )
      {
         for ( String year : language.getYearLiterals() )
         {
            // Every
            for ( String every : language.getEvery() )
            {
               if ( i == 1 )
               {
                  addTestData( new TestData( every + " " + year,
                                             new ResultBuilder().add( IS_EVERY,
                                                                      true )
                                                                .add( OP_FREQ,
                                                                      VAL_YEARLY )
                                                                .add( OP_INTERVAL,
                                                                      1 )
                                                                .build() ),
                               testData );
               }
               
               addTestData( new TestData( every + " " + i + " " + year,
                                          new ResultBuilder().add( IS_EVERY,
                                                                   true )
                                                             .add( OP_FREQ,
                                                                   VAL_YEARLY )
                                                             .add( OP_INTERVAL,
                                                                   i )
                                                             .build() ),
                            testData );
               
               for ( String numStr : language.getNumberStrings( i ) )
               {
                  
                  addTestData( new TestData( every + " " + numStr + " " + year,
                                             new ResultBuilder().add( IS_EVERY,
                                                                      true )
                                                                .add( OP_FREQ,
                                                                      VAL_YEARLY )
                                                                .add( OP_INTERVAL,
                                                                      i )
                                                                .build() ),
                               testData );
               }
            }
            
            // After
            for ( String after : language.getAfter() )
            {
               addTestData( new TestData( after + " " + i + " " + year,
                                          new ResultBuilder().add( IS_EVERY,
                                                                   false )
                                                             .add( OP_FREQ,
                                                                   VAL_YEARLY )
                                                             .add( OP_INTERVAL,
                                                                   i )
                                                             .build() ),
                            testData );
               
               for ( String numStr : language.getNumberStrings( i ) )
               {
                  addTestData( new TestData( after + " " + numStr + " " + year,
                                             new ResultBuilder().add( IS_EVERY,
                                                                      false )
                                                                .add( OP_FREQ,
                                                                      VAL_YEARLY )
                                                                .add( OP_INTERVAL,
                                                                      i )
                                                                .build() ),
                               testData );
               }
            }
         }
      }
   }
   
   
   
   private void addSentenceYearlyOnXst( Collection< Object[] > testData )
   {
      final String after = language.getAfter().iterator().next();
      final String year = language.getYearLiterals().iterator().next();
      
      for ( Iterator< Pair< String, Integer > > i = getIntervals().iterator(); i.hasNext(); )
      {
         final Pair< String, Integer > interval = i.next();
         
         for ( String separator : language.getYearWeekdaysSeparators() )
         {
            for ( int xst = 1; xst < 6; ++xst )
            {
               for ( String xstStr : language.getXst( xst ) )
               {
                  addTestData( new TestData( after + " " + interval.a + " "
                                                + year + " " + separator
                                                + xstStr,
                                             new ResultBuilder().add( IS_EVERY,
                                                                      false )
                                                                .add( OP_FREQ,
                                                                      VAL_MONTHLY )
                                                                .add( OP_INTERVAL,
                                                                      1 )
                                                                .add( OP_BYMONTHDAY,
                                                                      String.valueOf( xst ) )
                                                                .build() ),
                               testData );
               }
            }
         }
      }
   }
   
   
   
   private void addSentenceYearlyOnMultipleXst( Collection< Object[] > testData )
   {
      final String every = language.getEvery().iterator().next();
      final String year = language.getYearLiterals().iterator().next();
      final String onThe = language.getYearWeekdaysSeparators()
                                   .iterator()
                                   .next();
      final String xst1 = language.getXst( 1 ).iterator().next();
      final String xst3 = language.getXst( 3 ).iterator().next();
      
      for ( String xstSep : language.getSeparators() )
      {
         addTestData( new TestData( every + " 2 " + year + onThe + xst1
                                       + xstSep + xst3,
                                    new ResultBuilder().add( IS_EVERY, true )
                                                       .add( OP_FREQ,
                                                             VAL_MONTHLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYMONTHDAY,
                                                             "1,3" )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addSentenceYearlyOnXstWeekdays( Collection< Object[] > testData )
   {
      final String every = language.getEvery().iterator().next();
      final String year = language.getYearLiterals().iterator().next();
      final String onThe = language.getYearWeekdaysSeparators()
                                   .iterator()
                                   .next();
      final String xst1 = language.getXst( 1 ).iterator().next();
      final String xst3 = language.getXst( 3 ).iterator().next();
      final String sep = language.getSeparators().iterator().next();
      
      for ( int i = 1; i < 8; ++i )
      {
         for ( String weekday : language.getWeekdayStrings( i ) )
         {
            addTestData( new TestData( every + " 2 " + year + onThe + xst1
                                          + " " + weekday,
                                       new ResultBuilder().add( IS_EVERY, true )
                                                          .add( OP_FREQ,
                                                                VAL_MONTHLY )
                                                          .add( OP_INTERVAL, 1 )
                                                          .add( OP_BYDAY,
                                                                "1"
                                                                   + getWeekdayToken( i ) )
                                                          .build() ),
                         testData );
         }
      }
      
      final String monday = language.getWeekdayStrings( 1 ).iterator().next();
      final String friday = language.getWeekdayStrings( 5 ).iterator().next();
      
      for ( String last : language.getLast() )
      {
         addTestData( new TestData( every + " 2 " + year + onThe + xst1 + last
                         + monday, new ResultBuilder().add( IS_EVERY, true )
                                                      .add( OP_FREQ,
                                                            VAL_MONTHLY )
                                                      .add( OP_INTERVAL, 1 )
                                                      .add( OP_BYDAY, "-1MO" )
                                                      .build() ),
                      testData );
         
         addTestData( new TestData( every + " 2 " + year + onThe + last
                         + monday, new ResultBuilder().add( IS_EVERY, true )
                                                      .add( OP_FREQ,
                                                            VAL_MONTHLY )
                                                      .add( OP_INTERVAL, 1 )
                                                      .add( OP_BYDAY, "-1MO" )
                                                      .build() ),
                      testData );
         
         addTestData( new TestData( every + " 2 " + year + onThe + last
                                       + monday + sep + friday,
                                    new ResultBuilder().add( IS_EVERY, true )
                                                       .add( OP_FREQ,
                                                             VAL_MONTHLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             "-1MO,-1FR" )
                                                       .build() ),
                      testData );
      }
      
      addTestData( new TestData( every + " 2 " + year + onThe + xst3 + sep
                                    + xst1 + " " + monday,
                                 new ResultBuilder().add( IS_EVERY, true )
                                                    .add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY, "3MO" )
                                                    .build() ),
                   testData );
      
      addTestData( new TestData( every + " 2 " + year + onThe + xst3 + sep
                                    + xst1 + " " + monday + sep + friday,
                                 new ResultBuilder().add( IS_EVERY, true )
                                                    .add( OP_FREQ, VAL_MONTHLY )
                                                    .add( OP_INTERVAL, 1 )
                                                    .add( OP_BYDAY, "3MO,3FR" )
                                                    .build() ),
                   testData );
   }
   
   
   
   private void addSentenceYearlyOnXstWeekdaysOfMonthInterval( Collection< Object[] > testData )
   {
      final String after = language.getAfter().iterator().next();
      final String year = language.getYearLiterals().iterator().next();
      final String onThe = language.getYearWeekdaysSeparators()
                                   .iterator()
                                   .next();
      final String inOf = language.getInOfMonth().iterator().next();
      final String xst1 = language.getXst( 1 ).iterator().next();
      final String february = language.getMonthStrings( 2 ).iterator().next();
      
      for ( Iterator< Pair< String, Integer > > i = getIntervals().iterator(); i.hasNext(); )
      {
         final Pair< String, Integer > interval = i.next();
         
         addTestData( new TestData( after + " " + interval.a + " " + year + " "
                                       + onThe + xst1 + inOf + " " + february,
                                    new ResultBuilder().add( IS_EVERY, false )
                                                       .add( OP_FREQ,
                                                             VAL_YEARLY )
                                                       .add( OP_INTERVAL, 1 )
                                                       .add( OP_BYDAY,
                                                             String.valueOf( 1 ) )
                                                       .add( OP_BYMONTH,
                                                             String.valueOf( 2 ) )
                                                       .build() ),
                      testData );
      }
   }
   
   
   
   private void addSentenceYearlyOnXstWeekdaysOfMonthXst( Collection< Object[] > testData )
   {
      final String after = language.getAfter().iterator().next();
      final String year = language.getYearLiterals().iterator().next();
      
      for ( Iterator< Pair< String, Integer > > i = getIntervals().iterator(); i.hasNext(); )
      {
         final Pair< String, Integer > interval = i.next();
         
         for ( String onThe : language.getYearWeekdaysSeparators() )
         {
            for ( String inOf : language.getInOfMonth() )
            {
               for ( int xst = 1; xst < 6; ++xst )
               {
                  for ( String xstStr : language.getXst( xst ) )
                  {
                     for ( int monthNum = 1; monthNum < 13; ++monthNum )
                     {
                        for ( String month : language.getMonthStrings( monthNum ) )
                        {
                           addTestData( new TestData( after + " " + interval.a
                                                         + " " + year + " "
                                                         + onThe + xstStr
                                                         + inOf + " " + month,
                                                      new ResultBuilder().add( IS_EVERY,
                                                                               false )
                                                                         .add( OP_FREQ,
                                                                               VAL_YEARLY )
                                                                         .add( OP_INTERVAL,
                                                                               1 )
                                                                         .add( OP_BYDAY,
                                                                               String.valueOf( xst ) )
                                                                         .add( OP_BYMONTH,
                                                                               String.valueOf( monthNum ) )
                                                                         .build() ),
                                        testData );
                        }
                     }
                  }
               }
            }
         }
      }
   }
   
   
   
   @SuppressWarnings( "unchecked" )
   private Collection< Pair< String, Integer > > getIntervals()
   {
      return Arrays.asList( new Pair< String, Integer >( Strings.EMPTY_STRING,
                                                         1 ),
                            new Pair< String, Integer >( "1", 1 ),
                            new Pair< String, Integer >( language.getNumberStrings( 1 )
                                                                 .iterator()
                                                                 .next(),
                                                         1 ),
                            new Pair< String, Integer >( "2", 2 ),
                            new Pair< String, Integer >( language.getNumberStrings( 2 )
                                                                 .iterator()
                                                                 .next(),
                                                         2 ) );
   }
   
   
   
   private String getWeekdayToken( int weekday )
   {
      switch ( weekday )
      {
         case 1:
            return "MO";
         case 2:
            return "TU";
         case 3:
            return "WE";
         case 4:
            return "TH";
         case 5:
            return "FR";
         case 6:
            return "SA";
         case 7:
            return "SU";
         default :
            throw new IllegalArgumentException( "weekday" );
      }
   }
   
   
   
   private void addTestData( TestData testData, Collection< Object[] > testDatas )
   {
      testDatas.add( new Object[]
      { testData } );
   }
   
   
   private final class ResultBuilder
   {
      private final Map< String, Object > expectedResult = new TreeMap< String, Object >( new RecurrencePatternOperatorComp() );
      
      
      
      public ResultBuilder add( String operator, Object value )
      {
         expectedResult.put( operator, value );
         return this;
      }
      
      
      
      public ResultBuilder add( String operator, Object... values )
      {
         expectedResult.put( operator,
                             Strings.join( ",", Arrays.asList( values ) ) );
         return this;
      }
      
      
      
      public Map< String, Object > build()
      {
         return expectedResult;
      }
   }
   
   
   public final static class TestData
   {
      public final String sentence;
      
      public final Map< String, Object > expectedPattern;
      
      
      
      public TestData( String sentence, Map< String, Object > expectedPattern )
      {
         this.sentence = sentence;
         this.expectedPattern = expectedPattern;
      }
      
      
      
      @Override
      public String toString()
      {
         return sentence;
      }
   }
}
