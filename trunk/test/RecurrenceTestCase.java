import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.RecurrenceLexer;
import dev.drsoran.moloko.grammar.RecurrenceParser;
import dev.drsoran.moloko.grammar.lang.RecurrPatternLanguage;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


public class RecurrenceTestCase
{
   private final static RecurrPatternLanguage LANG = new RecurrPatternLanguage();
   
   private final static SimpleDateFormat SDF_FORMAT = new SimpleDateFormat( RecurrenceParser.DATE_PATTERN );
   
   private final static SimpleDateFormat SDF_PARSE = new SimpleDateFormat( "dd.MM.yyyy" );
   
   

   private static void parseRecurrence( String string,
                                        String freq,
                                        int interval,
                                        String res,
                                        String resVal,
                                        boolean isEvery )
   {
      parseRecurrence( string, freq, interval, res, resVal, null, null, isEvery );
   }
   


   private static void parseRecurrence( String string,
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
   


   private static void parseRecurrence( String string,
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
      final RecurrenceLexer lexer = new RecurrenceLexer( new ANTLRNoCaseStringStream( string ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final RecurrenceParser parser = new RecurrenceParser( antlrTokens );
      
      System.out.println( ">input: " + string );
      
      try
      {
         final HashMap< String, Object > result = parser.parseRecurrence();
         
         final String res_freq = (String) result.get( RecurrenceParser.OP_FREQ_LIT );
         final int res_interval = (Integer) result.get( RecurrenceParser.OP_INTERVAL_LIT );
         final String res_byDay = (String) result.get( RecurrenceParser.OP_BYDAY_LIT );
         final String res_byMonth = (String) result.get( RecurrenceParser.OP_BYMONTH_LIT );
         final String res_byMonthDay = (String) result.get( RecurrenceParser.OP_BYMONTHDAY_LIT );
         final boolean res_isEvery = (Boolean) result.get( RecurrenceParser.IS_EVERY );
         final String res_until = (String) result.get( RecurrenceParser.OP_UNTIL_LIT );
         final Integer res_for = (Integer) result.get( RecurrenceParser.OP_COUNT_LIT );
         
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
            if ( res.equals( RecurrenceParser.OP_BYDAY_LIT ) )
               Asserts.assertNonNull( res_byDay, "" );
            else if ( res.equals( RecurrenceParser.OP_BYMONTH_LIT ) )
               Asserts.assertNonNull( res_byMonth, "" );
            else if ( res.equals( RecurrenceParser.OP_BYMONTHDAY_LIT ) )
               Asserts.assertNonNull( res_byMonthDay, "" );
         }
         
         if ( resEx != null )
         {
            if ( resEx.equals( RecurrenceParser.OP_BYDAY_LIT ) )
               Asserts.assertNonNull( res_byDay, "" );
            else if ( resEx.equals( RecurrenceParser.OP_BYMONTH_LIT ) )
               Asserts.assertNonNull( res_byMonth, "" );
            else if ( resEx.equals( RecurrenceParser.OP_BYMONTHDAY_LIT ) )
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
   


   private static void parseRecurrencePattern( String string,
                                               String expected,
                                               boolean isEvery )
   {
      final RecurrenceLexer lexer = new RecurrenceLexer( new ANTLRStringStream( string ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final RecurrenceParser parser = new RecurrenceParser( antlrTokens );
      
      System.out.println( ">input: " + string );
      
      try
      {
         final String result = parser.parseRecurrencePattern( LANG, isEvery );
         
         System.out.println( string + ": " + result );
         
         Asserts.assertEquals( result.toLowerCase(), expected, "" );
      }
      catch ( RecognitionException e )
      {
         System.err.println( "Parsing failed!" );
      }
   }
   


   private static String buildPattern( String freq,
                                       int interval,
                                       String... resolution )
   {
      StringBuilder result = new StringBuilder( RecurrenceParser.OP_FREQ_LIT );
      
      result.append( "=" )
            .append( freq )
            .append( ";" );
      result.append( RecurrenceParser.OP_INTERVAL_LIT )
            .append( "=" )
            .append( String.valueOf( interval ) )
            .append( resolution.length > 0 ? ";" : "" );
      
      for ( int i = 0; i < resolution.length; i++ )
      {
         result.append( resolution[ i ] );
         
         if ( i < resolution.length - 1 )
         {
            result.append( ";" );
         }
      }
      
      return result.toString();
   }
   


   public final static void execute() throws ParseException
   {
      parseRecurrence( "every year",
                       RecurrenceParser.VAL_YEARLY_LIT,
                       1,
                       null,
                       null,
                       true );
      parseRecurrence( "every 1st and 25th",
                       RecurrenceParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrenceParser.OP_BYMONTHDAY_LIT,
                       "1,25",
                       true );
      parseRecurrence( "every tuesday",
                       RecurrenceParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "TU",
                       true );
      parseRecurrence( "every monday, wednesday",
                       RecurrenceParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "MO,WE",
                       true );
      parseRecurrence( "every 2nd friday",
                       RecurrenceParser.VAL_WEEKLY_LIT,
                       2,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "FR",
                       true );
      parseRecurrence( "every weekday",
                       RecurrenceParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "MO,TU,WE,TH,FR",
                       true );
      parseRecurrence( "every day",
                       RecurrenceParser.VAL_DAILY_LIT,
                       1,
                       null,
                       null,
                       true );
      parseRecurrence( "every 2 weeks",
                       RecurrenceParser.VAL_WEEKLY_LIT,
                       2,
                       null,
                       null,
                       true );
      parseRecurrence( "every month on the 4th",
                       RecurrenceParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrenceParser.OP_BYMONTHDAY_LIT,
                       "4",
                       true );
      parseRecurrence( "every 3rd tuesday",
                       RecurrenceParser.VAL_WEEKLY_LIT,
                       3,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "TU",
                       true );
      parseRecurrence( "every month on the 3rd tuesday",
                       RecurrenceParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "3TU",
                       true );
      parseRecurrence( "every month on the last monday",
                       RecurrenceParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "-1MO",
                       true );
      parseRecurrence( "every month on the 2nd last friday",
                       RecurrenceParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "-2FR",
                       true );
      parseRecurrence( "every month on the 1st friday",
                       RecurrenceParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "1FR",
                       true );
      parseRecurrence( "every year on the 1st friday, monday of january",
                       RecurrenceParser.VAL_YEARLY_LIT,
                       1,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "1MO,1FR",
                       RecurrenceParser.OP_BYMONTH_LIT,
                       "1",
                       true );
      parseRecurrence( "every monday, wednesday until 10.10.2010",
                       RecurrenceParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrenceParser.OP_BYDAY_LIT,
                       "MO,WE",
                       null,
                       null,
                       SDF_FORMAT.format( SDF_PARSE.parse( "10.10.2010" ) ),
                       -1,
                       true );
      {
         final Calendar cal = Calendar.getInstance();
         cal.roll( Calendar.DAY_OF_MONTH, true );
         
         cal.set( Calendar.HOUR, 0 );
         cal.set( Calendar.HOUR_OF_DAY, 0 );
         cal.set( Calendar.MINUTE, 0 );
         cal.set( Calendar.SECOND, 0 );
         cal.set( Calendar.MILLISECOND, 0 );
         
         parseRecurrence( "every day until tomorrow",
                          RecurrenceParser.VAL_DAILY_LIT,
                          1,
                          null,
                          null,
                          null,
                          null,
                          SDF_FORMAT.format( cal.getTime() ),
                          -1,
                          true );
      }
      parseRecurrence( "every day for 10 times",
                       RecurrenceParser.VAL_DAILY_LIT,
                       1,
                       null,
                       null,
                       null,
                       null,
                       null,
                       10,
                       true );
      
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_YEARLY_LIT,
                                            1,
                                            RecurrenceParser.OP_BYDAY_LIT
                                                     + "=1MO,1FR",
                                            RecurrenceParser.OP_BYMONTH_LIT
                                                     + "=1" ),
                              "every year on the 1st monday, friday in january",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_YEARLY_LIT,
                                            1 ),
                              "every year",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_YEARLY_LIT,
                                            2 ),
                              "every 2 years",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrenceParser.OP_BYMONTHDAY_LIT
                                                     + "=1,25" ),
                              "every month on the 1st, 25th",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrenceParser.OP_BYDAY_LIT
                                                     + "=3TU" ),
                              "every month on the 3rd tuesday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrenceParser.OP_BYDAY_LIT
                                                     + "=-1MO" ),
                              "every month on the last monday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrenceParser.OP_BYDAY_LIT
                                                     + "=-2FR" ),
                              "every month on the 2nd last friday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_MONTHLY_LIT,
                                            1,
                                            RecurrenceParser.OP_BYDAY_LIT
                                                     + "=1FR" ),
                              "every month on the 1st friday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_WEEKLY_LIT,
                                            2 ),
                              "every 2 weeks",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_WEEKLY_LIT,
                                            1,
                                            RecurrenceParser.OP_BYDAY_LIT
                                                     + "=TU" ),
                              "every week on the tuesday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_WEEKLY_LIT,
                                            1,
                                            RecurrenceParser.OP_BYDAY_LIT
                                                     + "=MO,WE" ),
                              "every week on the monday, wednesday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_WEEKLY_LIT,
                                            2,
                                            RecurrenceParser.OP_BYDAY_LIT
                                                     + "=FR" ),
                              "every 2 weeks on the friday",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_DAILY_LIT,
                                            1 ),
                              "every day",
                              true );
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_WEEKLY_LIT,
                                            1,
                                            RecurrenceParser.OP_BYDAY_LIT
                                                     + "=MO,WE",
                                            RecurrenceParser.OP_UNTIL_LIT
                                                     + "="
                                                     + SDF_FORMAT.format( SDF_PARSE.parse( "10.10.2010" ) ) ),
                              "every week on the monday, wednesday until 10.10.2010",
                              true );
      {
         final Calendar cal = Calendar.getInstance();
         cal.roll( Calendar.DAY_OF_MONTH, true );
         
         cal.set( Calendar.HOUR, 0 );
         cal.set( Calendar.HOUR_OF_DAY, 0 );
         cal.set( Calendar.MINUTE, 0 );
         cal.set( Calendar.SECOND, 0 );
         cal.set( Calendar.MILLISECOND, 0 );
         
         parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_DAILY_LIT,
                                               1,
                                               RecurrenceParser.OP_UNTIL_LIT
                                                        + "="
                                                        + SDF_FORMAT.format( cal.getTime() ) ),
                                 "every day until " + SDF_PARSE.format( cal.getTime() ),
                                 true );
      }
      parseRecurrencePattern( buildPattern( RecurrenceParser.VAL_DAILY_LIT,
                                            1,
                                            RecurrenceParser.OP_COUNT_LIT
                                                     + "=10" ),
                              "every day for 10 times",
                              true );
   }
}
