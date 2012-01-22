import java.util.Locale;
import java.util.Map;

import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.grammar.recurrence.IRecurrenceParser;
import dev.drsoran.moloko.grammar.recurrence.RecurrenceParserFactory;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;


public class RecurrenceTestHelper
{
   public static void parseRecurrence( IDateFormatContext dateFormatContext,
                                       Locale locale,
                                       String string,
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
      
      final IRecurrenceParser parser = RecurrenceParserFactory.createRecurrenceParserForLocale( locale );
      
      System.out.println( ">input: " + string );
      
      try
      {
         final Map< String, Object > result = parser.parseRecurrence( string );
         
         final String res_freq = (String) result.get( RecurrencePatternParser.OP_FREQ_LIT );
         final int res_interval = (Integer) result.get( RecurrencePatternParser.OP_INTERVAL_LIT );
         final String res_byDay = (String) result.get( RecurrencePatternParser.OP_BYDAY_LIT );
         final String res_byMonth = (String) result.get( RecurrencePatternParser.OP_BYMONTH_LIT );
         final String res_byMonthDay = (String) result.get( RecurrencePatternParser.OP_BYMONTHDAY_LIT );
         final boolean res_isEvery = (Boolean) result.get( RecurrencePatternParser.IS_EVERY );
         final String res_until = (String) result.get( RecurrencePatternParser.OP_UNTIL_LIT );
         final Integer res_for = (Integer) result.get( RecurrencePatternParser.OP_COUNT_LIT );
         
         System.out.println( string + "_freq: " + res_freq );
         System.out.println( string + "_interval: " + res_interval );
         System.out.println( string + "_byDay: " + res_byDay );
         System.out.println( string + "_byMonth: " + res_byMonth );
         System.out.println( string + "_byMonthDay: " + res_byMonthDay );
         System.out.println( string + "_isEvery: " + res_isEvery );
         System.out.println( string + "_until: " + res_until );
         System.out.println( string + "_for: " + res_for );
         
         Asserts.assertEquals( res_freq, freq, "Freq is wrong." );
         Asserts.assertEquals( res_interval, interval, "Interval is wrong." );
         Asserts.assertEquals( res_isEvery, isEvery, "IsEvery is wrong." );
         
         if ( res != null )
         {
            if ( res.equals( RecurrencePatternParser.OP_BYDAY_LIT ) )
               Asserts.assertNonNull( res_byDay, "" );
            else if ( res.equals( RecurrencePatternParser.OP_BYMONTH_LIT ) )
               Asserts.assertNonNull( res_byMonth, "" );
            else if ( res.equals( RecurrencePatternParser.OP_BYMONTHDAY_LIT ) )
               Asserts.assertNonNull( res_byMonthDay, "" );
         }
         
         if ( resEx != null )
         {
            if ( resEx.equals( RecurrencePatternParser.OP_BYDAY_LIT ) )
               Asserts.assertNonNull( res_byDay, "" );
            else if ( resEx.equals( RecurrencePatternParser.OP_BYMONTH_LIT ) )
               Asserts.assertNonNull( res_byMonth, "" );
            else if ( resEx.equals( RecurrencePatternParser.OP_BYMONTHDAY_LIT ) )
               Asserts.assertNonNull( res_byMonthDay, "" );
         }
         
         if ( resVal != null )
         {
            final boolean foundResVal = ( res_byDay != null && res_byDay.equals( resVal ) )
               || ( res_byMonth != null && res_byMonth.equals( resVal ) )
               || ( res_byMonthDay != null && res_byMonthDay.equals( resVal ) );
            
            if ( !foundResVal )
               System.err.println( "Resolution value is wrong." );
         }
         
         if ( resExVal != null )
         {
            final boolean foundResVal = ( res_byDay != null && res_byDay.equals( resExVal ) )
               || ( res_byMonth != null && res_byMonth.equals( resExVal ) )
               || ( res_byMonthDay != null && res_byMonthDay.equals( resExVal ) );
            
            if ( !foundResVal )
               System.err.println( "ResolutionEx value is wrong." );
         }
         
         if ( until != null )
            Asserts.assertEquals( res_until, until, "Until value is wrong." );
         
         if ( forVal != -1 )
            Asserts.assertEquals( res_for, forVal, "For value is wrong." );
      }
      catch ( RecognitionException e )
      {
         System.err.println( "Parsing failed!" );
      }
   }
}
