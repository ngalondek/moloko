import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dev.drsoran.moloko.grammar.RecurrencePatternParser;


public class RecurrenceTestCase_de
{
   private final static SimpleDateFormat SDF_FORMAT = new SimpleDateFormat( RecurrencePatternParser.DATE_PATTERN );
   
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
      RecurrenceTestHelper.parseRecurrence( Locale.GERMAN,
                                            string,
                                            freq,
                                            interval,
                                            res,
                                            resVal,
                                            resEx,
                                            resExVal,
                                            until,
                                            forVal,
                                            isEvery );
   }
   


   public final static void execute() throws ParseException
   {
      parseRecurrence( "jedes Jahr",
                       RecurrencePatternParser.VAL_YEARLY_LIT,
                       1,
                       null,
                       null,
                       true );
      parseRecurrence( "jeder 1. und 25.",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYMONTHDAY_LIT,
                       "1,25",
                       true );
      parseRecurrence( "jeden Dienstag",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "TU",
                       true );
      parseRecurrence( "jeden montag, Mittwoch",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,WE",
                       true );
      parseRecurrence( "jeden 2. freitag",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       2,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "FR",
                       true );
      parseRecurrence( "jeden wochentag",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,TU,WE,TH,FR",
                       true );
      parseRecurrence( "jeden tag",
                       RecurrencePatternParser.VAL_DAILY_LIT,
                       1,
                       null,
                       null,
                       true );
      parseRecurrence( "alle 2 wochen",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       2,
                       null,
                       null,
                       true );
      parseRecurrence( "jeden monat am 4.",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYMONTHDAY_LIT,
                       "4",
                       true );
      parseRecurrence( "jeden 3. Dienstag",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       3,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "TU",
                       true );
      parseRecurrence( "jeden monat am 3. Dienstag",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "3TU",
                       true );
      parseRecurrence( "jeden monat am letzen montag",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "-1MO",
                       true );
      parseRecurrence( "jeden monat am 2. letzen freitag",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "-2FR",
                       true );
      parseRecurrence( "jeden monat am 1. freitag",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "1FR",
                       true );
      parseRecurrence( "jedes jahr am 1. freitag, montag im januar",
                       RecurrencePatternParser.VAL_YEARLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "1MO,1FR",
                       RecurrencePatternParser.OP_BYMONTH_LIT,
                       "1",
                       true );
      parseRecurrence( "jeden montag, mittwoch bis 10.10.2010",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
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
         
         parseRecurrence( "jeden tag bis morgen",
                          RecurrencePatternParser.VAL_DAILY_LIT,
                          1,
                          null,
                          null,
                          null,
                          null,
                          SDF_FORMAT.format( cal.getTime() ),
                          -1,
                          true );
      }
      parseRecurrence( "jeden tag für 10 mal",
                       RecurrencePatternParser.VAL_DAILY_LIT,
                       1,
                       null,
                       null,
                       null,
                       null,
                       null,
                       10,
                       true );
   }
}
