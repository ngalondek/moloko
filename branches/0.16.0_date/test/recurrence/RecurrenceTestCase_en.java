import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;


public class RecurrenceTestCase_en
{
   private final static SimpleDateFormat SDF_FORMAT = new SimpleDateFormat( RecurrencePatternParser.DATE_PATTERN );
   
   private final static SimpleDateFormat SDF_PARSE = new SimpleDateFormat( "MM/dd/yyyy" );
   
   

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
      RecurrenceTestHelper.parseRecurrence( Locale.ENGLISH,
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
      parseRecurrence( "every year",
                       RecurrencePatternParser.VAL_YEARLY_LIT,
                       1,
                       null,
                       null,
                       true );
      parseRecurrence( "every 1st and 25th",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYMONTHDAY_LIT,
                       "1,25",
                       true );
      parseRecurrence( "every tuesday",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "TU",
                       true );
      parseRecurrence( "every monday, wednesday",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,WE",
                       true );
      parseRecurrence( "every 2nd friday",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       2,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "FR",
                       true );
      parseRecurrence( "every weekday",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,TU,WE,TH,FR",
                       true );
      parseRecurrence( "every day",
                       RecurrencePatternParser.VAL_DAILY_LIT,
                       1,
                       null,
                       null,
                       true );
      parseRecurrence( "every 2 weeks",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       2,
                       null,
                       null,
                       true );
      parseRecurrence( "after 1 month",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       null,
                       null,
                       false );
      parseRecurrence( "after a month",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       null,
                       null,
                       false );
      parseRecurrence( "after 2 months",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       2,
                       null,
                       null,
                       false );
      parseRecurrence( "every month on the 4th",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYMONTHDAY_LIT,
                       "4",
                       true );
      parseRecurrence( "every 3rd tuesday",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       3,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "TU",
                       true );
      parseRecurrence( "every month on the 3rd tuesday",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "3TU",
                       true );
      parseRecurrence( "every month on the last monday",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "-1MO",
                       true );
      parseRecurrence( "every month on the 2nd last friday",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "-2FR",
                       true );
      parseRecurrence( "every month on the 1st friday",
                       RecurrencePatternParser.VAL_MONTHLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "1FR",
                       true );
      parseRecurrence( "every year on the 1st friday, monday of january",
                       RecurrencePatternParser.VAL_YEARLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "1MO,1FR",
                       RecurrencePatternParser.OP_BYMONTH_LIT,
                       "1",
                       true );
      parseRecurrence( "every monday, wednesday until 10/1/2010",
                       RecurrencePatternParser.VAL_WEEKLY_LIT,
                       1,
                       RecurrencePatternParser.OP_BYDAY_LIT,
                       "MO,WE",
                       null,
                       null,
                       SDF_FORMAT.format( SDF_PARSE.parse( "10/1/2010" ) ),
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
      parseRecurrence( "every day for 10 times",
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
