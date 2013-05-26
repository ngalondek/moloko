/* 
 *	Copyright (c) 2012 Ronny Röhricht
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

package dev.drsoran.moloko.test.grammar;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.antlr.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Before;

import com.xtremelabs.robolectric.Robolectric;

import dev.drsoran.moloko.grammar.IDateTimeParsing;
import dev.drsoran.moloko.grammar.recurrence.IRecurrenceParser;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;
import dev.drsoran.moloko.test.MolokoTestCase;
import dev.drsoran.moloko.ui.UiContext;


abstract class RecurrenceParsingTestBase extends MolokoTestCase
{
   private final static SimpleDateFormat SDF_FORMAT = new SimpleDateFormat( RecurrencePatternParser.DATE_PATTERN );
   
   private SimpleDateFormat dateParse;
   
   private IRecurrenceParser recurrenceParser;
   
   
   
   @Override
   @Before
   public void setUp()
   {
      super.setUp();
      
      final UiContext uiContext = UiContext.get( Robolectric.application );
      final IDateTimeParsing dateTimeParsing = uiContext.getParsingService()
                                                        .getDateTimeParsing();
      
      dateParse = new SimpleDateFormat( uiContext.getDateFormatter()
                                                 .getNumericDateFormatPattern( true ) );
      
      recurrenceParser = createRecurrenceParser( dateTimeParsing );
   }
   
   
   
   public SimpleDateFormat getRecurrenceDateFormat()
   {
      return SDF_FORMAT;
   }
   
   
   
   public SimpleDateFormat getDateParse()
   {
      return dateParse;
   }
   
   
   
   public void parseRecurrence( String string,
                                String freq,
                                int interval,
                                String res,
                                String resVal,
                                boolean isEvery )
   {
      parseRecurrence( string, freq, interval, res, resVal, null, null, isEvery );
   }
   
   
   
   public void parseRecurrence( String string,
                                String freq,
                                int interval,
                                String res,
                                String resVal,
                                String resEx,
                                String resExVal,
                                boolean isEvery )
   {
      parseRecurrence( string,
                       freq,
                       interval,
                       res,
                       resVal,
                       resEx,
                       resExVal,
                       null,
                       -1,
                       isEvery );
   }
   
   
   
   public void parseRecurrence( String recurrenceString,
                                String freq,
                                int interval,
                                String res,
                                String resVal,
                                String resEx,
                                String resExVal,
                                String until,
                                int forVal,
                                boolean isEvery )
   {
      try
      {
         final Map< String, Object > result = recurrenceParser.parseRecurrence( recurrenceString );
         
         final String res_freq = (String) result.get( RecurrencePatternParser.OP_FREQ_LIT );
         final int res_interval = (Integer) result.get( RecurrencePatternParser.OP_INTERVAL_LIT );
         final String res_byDay = (String) result.get( RecurrencePatternParser.OP_BYDAY_LIT );
         final String res_byMonth = (String) result.get( RecurrencePatternParser.OP_BYMONTH_LIT );
         final String res_byMonthDay = (String) result.get( RecurrencePatternParser.OP_BYMONTHDAY_LIT );
         final boolean res_isEvery = (Boolean) result.get( RecurrencePatternParser.IS_EVERY );
         final String res_until = (String) result.get( RecurrencePatternParser.OP_UNTIL_LIT );
         final Integer res_for = (Integer) result.get( RecurrencePatternParser.OP_COUNT_LIT );
         
         // System.out.println( recurrenceString + "_freq: " + res_freq );
         // System.out.println( recurrenceString + "_interval: " + res_interval );
         // System.out.println( recurrenceString + "_byDay: " + res_byDay );
         // System.out.println( recurrenceString + "_byMonth: " + res_byMonth );
         // System.out.println( recurrenceString + "_byMonthDay: "
         // + res_byMonthDay );
         // System.out.println( recurrenceString + "_isEvery: " + res_isEvery );
         // System.out.println( recurrenceString + "_until: " + res_until );
         // System.out.println( recurrenceString + "_for: " + res_for );
         
         Assert.assertEquals( "Freq is wrong", freq, res_freq );
         Assert.assertEquals( "Interval is wrong.", interval, res_interval );
         Assert.assertEquals( "IsEvery is wrong.", isEvery, res_isEvery );
         
         if ( res != null )
         {
            if ( res.equals( RecurrencePatternParser.OP_BYDAY_LIT ) )
               Assert.assertNotNull( res_byDay );
            else if ( res.equals( RecurrencePatternParser.OP_BYMONTH_LIT ) )
               Assert.assertNotNull( res_byMonth );
            else if ( res.equals( RecurrencePatternParser.OP_BYMONTHDAY_LIT ) )
               Assert.assertNotNull( res_byMonthDay );
         }
         
         if ( resEx != null )
         {
            if ( resEx.equals( RecurrencePatternParser.OP_BYDAY_LIT ) )
               Assert.assertNotNull( res_byDay );
            else if ( resEx.equals( RecurrencePatternParser.OP_BYMONTH_LIT ) )
               Assert.assertNotNull( res_byMonth );
            else if ( resEx.equals( RecurrencePatternParser.OP_BYMONTHDAY_LIT ) )
               Assert.assertNotNull( res_byMonthDay );
         }
         
         if ( resVal != null )
         {
            final boolean foundResVal = ( res_byDay != null && res_byDay.equals( resVal ) )
               || ( res_byMonth != null && res_byMonth.equals( resVal ) )
               || ( res_byMonthDay != null && res_byMonthDay.equals( resVal ) );
            
            Assert.assertTrue( "Resolution value is wrong.", foundResVal );
         }
         
         if ( resExVal != null )
         {
            final boolean foundResVal = ( res_byDay != null && res_byDay.equals( resExVal ) )
               || ( res_byMonth != null && res_byMonth.equals( resExVal ) )
               || ( res_byMonthDay != null && res_byMonthDay.equals( resExVal ) );
            
            Assert.assertTrue( "ResolutionEx value is wrong.", foundResVal );
         }
         
         if ( until != null )
            Assert.assertEquals( "Until value is wrong.", until, res_until );
         
         if ( forVal != -1 )
            Assert.assertEquals( "For value is wrong.",
                                 forVal,
                                 res_for.intValue() );
      }
      catch ( RecognitionException e )
      {
         Assert.fail( "Parsing '" + recurrenceString + "' failed!" );
      }
   }
   
   
   
   public abstract IRecurrenceParser createRecurrenceParser( IDateTimeParsing dateTimeParsing );
}
