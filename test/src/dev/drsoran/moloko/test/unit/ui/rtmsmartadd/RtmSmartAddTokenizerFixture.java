/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.moloko.test.unit.ui.rtmsmartadd;

import static dev.drsoran.moloko.test.IterableAsserts.assertEqualSet;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.DUE_DATE_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.ESTIMATE_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.LIST_TAGS_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.LOCATION_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.PRIORITY_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.REPEAT_TYPE;
import static dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken.TASK_NAME_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dev.drsoran.moloko.test.TestConstants;
import dev.drsoran.moloko.test.TestDateTimeParsing;
import dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddToken;
import dev.drsoran.moloko.ui.rtmsmartadd.RtmSmartAddTokenizer;


@RunWith( Parameterized.class )
public class RtmSmartAddTokenizerFixture
{
   private final TestData testData;
   
   
   
   public RtmSmartAddTokenizerFixture( TestData testData )
   {
      this.testData = testData;
   }
   
   
   
   @Parameters( name = "{0}" )
   public static Collection< Object[] > parseDateTestData()
   {
      final Collection< Object[] > testData = new ArrayList< Object[] >();
      
      testData.add( new Object[]
      { new TestData( "" ) } );
      testData.add( new Object[]
      { new TestData( "TaskName",
                      expectToken( TASK_NAME_TYPE, 0, 7, "TaskName" ) ) } );
      testData.add( new Object[]
      { new TestData( " TaskName ", expectToken( TASK_NAME_TYPE,
                                                 0,
                                                 9,
                                                 "TaskName" ) ) } );
      
      testData.add( new Object[]
      { new TestData( "^", expectToken( DUE_DATE_TYPE, 0, 0, "" ) ) } );
      testData.add( new Object[]
      { new TestData( "^today", expectToken( DUE_DATE_TYPE, 0, 5, "today" ) ) } );
      testData.add( new Object[]
      { new TestData( "^@7", expectToken( DUE_DATE_TYPE, 0, 2, "@7" ) ) } );
      testData.add( new Object[]
      { new TestData( "^tom  @ 6:00pm", expectToken( DUE_DATE_TYPE,
                                                     0,
                                                     13,
                                                     "tom  @ 6:00pm" ) ) } );
      
      testData.add( new Object[]
      { new TestData( "!", expectToken( PRIORITY_TYPE, 0, 0, "" ) ) } );
      testData.add( new Object[]
      { new TestData( "!1", expectToken( PRIORITY_TYPE, 0, 1, "1" ) ) } );
      testData.add( new Object[]
      { new TestData( "! 2   ", expectToken( PRIORITY_TYPE, 0, 5, "2" ) ) } );
      
      testData.add( new Object[]
      { new TestData( "#", expectToken( LIST_TAGS_TYPE, 0, 0, "" ) ) } );
      testData.add( new Object[]
      { new TestData( "#List", expectToken( LIST_TAGS_TYPE, 0, 4, "List" ) ) } );
      testData.add( new Object[]
      { new TestData( "#  Tag ", expectToken( LIST_TAGS_TYPE, 0, 6, "Tag" ) ) } );
      
      testData.add( new Object[]
      { new TestData( "@", expectToken( LOCATION_TYPE, 0, 0, "" ) ) } );
      testData.add( new Object[]
      { new TestData( "@Home", expectToken( LOCATION_TYPE, 0, 4, "Home" ) ) } );
      testData.add( new Object[]
      { new TestData( "@  Home", expectToken( LOCATION_TYPE, 0, 6, "Home" ) ) } );
      
      testData.add( new Object[]
      { new TestData( "*", expectToken( REPEAT_TYPE, 0, 0, "" ) ) } );
      testData.add( new Object[]
      { new TestData( "*every day",
                      expectToken( REPEAT_TYPE, 0, 9, "every day" ) ) } );
      testData.add( new Object[]
      { new TestData( "*  after 2 months  ", expectToken( REPEAT_TYPE,
                                                          0,
                                                          18,
                                                          "after 2 months" ) ) } );
      
      testData.add( new Object[]
      { new TestData( "=", expectToken( ESTIMATE_TYPE, 0, 0, "" ) ) } );
      testData.add( new Object[]
      { new TestData( "=2h", expectToken( ESTIMATE_TYPE, 0, 2, "2h" ) ) } );
      testData.add( new Object[]
      { new TestData( "= 3 hours, 2 min ", expectToken( ESTIMATE_TYPE,
                                                        0,
                                                        16,
                                                        "3 hours, 2 min" ) ) } );
      
      testData.add( new Object[]
      { new TestData( "Test = 3 hours, 2 min ^tod !2",
                      expectToken( TASK_NAME_TYPE, 0, 4, "Test" ),
                      expectToken( ESTIMATE_TYPE, 5, 21, "3 hours, 2 min" ),
                      expectToken( DUE_DATE_TYPE, 22, 26, "tod" ),
                      expectToken( PRIORITY_TYPE, 27, 28, "2" ) ) } );
      
      testData.add( new Object[]
      { new TestData( "^#List !=2h",
                      expectToken( DUE_DATE_TYPE, 0, 0, "" ),
                      expectToken( LIST_TAGS_TYPE, 1, 6, "List" ),
                      expectToken( PRIORITY_TYPE, 7, 7, "" ),
                      expectToken( ESTIMATE_TYPE, 8, 10, "2h" ) ) } );
      
      return testData;
   }
   
   
   
   @Test
   public void testGetTokens()
   {
      final RtmSmartAddTokenizer tokenizer = new RtmSmartAddTokenizer( TestDateTimeParsing.get( TestConstants.DATE_NOW ) );
      
      final Collection< RtmSmartAddToken > tokens = tokenizer.getTokens( testData.input );
      
      assertEqualSet( testData.expectedTokens, tokens );
   }
   
   
   
   private static RtmSmartAddToken expectToken( int type,
                                                int start,
                                                int end,
                                                String text )
   {
      final RtmSmartAddToken token = new RtmSmartAddToken( type );
      token.setStart( start );
      token.setEnd( end );
      token.setText( text );
      
      return token;
   }
   
   
   private static class TestData
   {
      public final CharSequence input;
      
      public final List< RtmSmartAddToken > expectedTokens;
      
      
      
      public TestData( CharSequence input, RtmSmartAddToken... expectedTokens )
      {
         this.input = input;
         this.expectedTokens = Arrays.asList( expectedTokens );
      }
      
      
      
      @Override
      public String toString()
      {
         return input.toString();
      }
   }
}
