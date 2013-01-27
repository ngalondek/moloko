package dev.drsoran.moloko.test.grammar.recurrence;

import java.util.Locale;
import java.util.Map;

import org.antlr.runtime.RecognitionException;
import org.junit.Assert;

import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.grammar.recurrence.IRecurrenceParser;
import dev.drsoran.moloko.grammar.recurrence.RecurrenceParserFactory;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;


class RecurrenceTestHelper
{
   public static void parseRecurrence( IDateFormatContext dateFormatContext,
                                       String recurrenceString,
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
      RtmDateTimeParsing.setDateFormatContext( dateFormatContext );
      
      final IRecurrenceParser parser = RecurrenceParserFactory.createRecurrenceParserForLocale( Locale.getDefault() );
      
      try
      {
         final Map< String, Object > result = parser.parseRecurrence( recurrenceString );
         
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
}
