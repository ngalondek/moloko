import java.util.Calendar;
import java.util.Locale;

import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.datetime.DateParserFactory;
import dev.drsoran.moloko.grammar.datetime.IDateParser;
import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateWithinReturn;
import dev.drsoran.moloko.util.MolokoCalendar;


public class DateParserOnlyTestCase_en
{
   private static void parseDate( String string, int d, int m, int y )
   {
      parseDate( string, d, m, y, -1, -1, -1 );
   }
   


   public static void parseDate( String string,
                                 int d,
                                 int m,
                                 int y,
                                 int h,
                                 int min,
                                 int s )
   {
      System.out.println( ">input: " + string );
      
      IDateParser dateParser = DateParserFactory.createDateParserForLocale( Locale.US );
      
      final MolokoCalendar cal = dateParser.getCalendar();
      try
      {
         dateParser.parseDate( string, cal, true );
         
         System.out.println( string + ": " + cal.getTimeInMillis() );
         System.out.println( string + ": " + cal.getTime() );
         System.out.println( string + " has time: " + cal.hasTime() );
         
         Asserts.assertEquals( cal.get( Calendar.DAY_OF_MONTH ),
                               d,
                               "Day is wrong." );
         Asserts.assertEquals( cal.get( Calendar.MONTH ), m, "Month is wrong." );
         Asserts.assertEquals( cal.get( Calendar.YEAR ), y, "Year is wrong." );
         
         if ( h != -1 )
         {
            Asserts.assertEquals( cal.get( Calendar.HOUR_OF_DAY ),
                                  h,
                                  "Hour is wrong." );
            Asserts.assertTrue( cal.hasTime(), "Calendar has no time." );
         }
         else
            Asserts.assertTrue( !cal.hasTime(), "Calendar has time." );
         
         if ( min != -1 )
            Asserts.assertEquals( cal.get( Calendar.MINUTE ),
                                  min,
                                  "Minute is wrong." );
         if ( s != -1 )
            Asserts.assertEquals( cal.get( Calendar.SECOND ),
                                  s,
                                  "Second is wrong." );
      }
      catch ( RecognitionException e )
      {
         System.err.println( "Parsing failed!" );
      }
   }
   


   public static void parseDateWithin( String string,
                                       boolean past,
                                       int sy,
                                       int sm,
                                       int sw,
                                       int sd,
                                       int ey,
                                       int em,
                                       int ew,
                                       int ed )
   {
      System.out.println( ">input: " + string + ", past: " + past );
      
      IDateParser dateParser = DateParserFactory.createDateParserForLocale( Locale.US );
      
      try
      {
         ParseDateWithinReturn result = dateParser.parseDateWithin( string,
                                                                    past );
         
         final MolokoCalendar start = result.startEpoch;
         final MolokoCalendar end = result.endEpoch;
         
         System.out.println( string + "_start: " + start.getTimeInMillis() );
         System.out.println( string + "_start: " + start.getTime() );
         System.out.println( string + "_start has time: " + start.hasTime() );
         
         System.out.println( string + "_end: " + end.getTimeInMillis() );
         System.out.println( string + "_end: " + end.getTime() );
         System.out.println( string + "_end has time: " + end.hasTime() );
         
         Asserts.assertEquals( start.get( Calendar.DAY_OF_YEAR ),
                               sd,
                               "Day is wrong." );
         Asserts.assertEquals( start.get( Calendar.WEEK_OF_YEAR ),
                               sw,
                               "Week is wrong." );
         Asserts.assertEquals( start.get( Calendar.MONTH ),
                               sm,
                               "Month is wrong." );
         Asserts.assertEquals( start.get( Calendar.YEAR ), sy, "Year is wrong." );
         
         Asserts.assertEquals( end.get( Calendar.DAY_OF_YEAR ),
                               ed,
                               "Day is wrong." );
         Asserts.assertEquals( end.get( Calendar.WEEK_OF_YEAR ),
                               ew,
                               "Week is wrong." );
         Asserts.assertEquals( end.get( Calendar.MONTH ), em, "Month is wrong." );
         Asserts.assertEquals( end.get( Calendar.YEAR ), ey, "Year is wrong." );
      }
      catch ( RecognitionException e )
      {
         System.err.println( "Parsing failed!" );
      }
   }
   


   public final static void execute()
   {
      {
         final Calendar cal = Calendar.getInstance();
         parseDate( "now",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    cal.get( Calendar.HOUR_OF_DAY ),
                    cal.get( Calendar.MINUTE ),
                    cal.get( Calendar.SECOND ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         parseDate( "today",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.roll( Calendar.DAY_OF_MONTH, true );
         parseDate( "tomorrow",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.DAY_OF_MONTH,
                  cal.getActualMaximum( Calendar.DAY_OF_MONTH ) );
         parseDate( "end of month",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.roll( Calendar.YEAR, true );
         cal.add( Calendar.MONTH, 2 );
         parseDate( "in 1 year and 2 mons",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.DAY_OF_WEEK, Calendar.MONDAY );
         
         final MolokoCalendar now = DateTimeTestHelper.getDateParserCalendar();
         
         if ( cal.get( Calendar.DAY_OF_YEAR ) <= now.get( Calendar.DAY_OF_YEAR )
            || now.get( Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY )
            cal.roll( Calendar.WEEK_OF_YEAR, true );
         
         // due to "next" Monday
         cal.roll( Calendar.WEEK_OF_YEAR, true );
         
         parseDate( "next monday",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.MONTH, Calendar.JULY );
         cal.set( Calendar.DAY_OF_MONTH, 3 );
         
         if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
            cal.roll( Calendar.YEAR, true );
         
         parseDate( "on july 3rd",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2000 );
         cal.set( Calendar.MONTH, Calendar.JULY );
         cal.set( Calendar.DAY_OF_MONTH, 3 );
         
         parseDate( "on july 3rd 2000",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.DAY_OF_MONTH, 21 );
         
         if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
            cal.roll( Calendar.MONTH, true );
         
         parseDate( "on 21st",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.DAY_OF_MONTH, 3 );
         cal.set( Calendar.MONTH, Calendar.JUNE );
         
         if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
            cal.roll( Calendar.YEAR, true );
         
         parseDate( "on 3rd of june",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2006 );
         cal.set( Calendar.MONTH, Calendar.FEBRUARY );
         cal.set( Calendar.DAY_OF_MONTH, 21 );
         
         parseDate( "on 21st of feb 6",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2011 );
         cal.set( Calendar.MONTH, Calendar.JULY );
         cal.set( Calendar.DAY_OF_MONTH, 1 );
         
         parseDate( "1-july-2011",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "10.10.2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.MONTH, Calendar.DECEMBER );
         cal.set( Calendar.DAY_OF_MONTH, 24 );
         
         parseDate( "24.12.",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "10.10.2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "10.10.2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.roll( Calendar.DAY_OF_MONTH, true );
         parseDate( "tom",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         parseDate( "tod",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final MolokoCalendar cal = DateTimeTestHelper.getDateParserCalendar();
         cal.set( Calendar.DAY_OF_MONTH, 21 );
         
         if ( cal.before( DateTimeTestHelper.getDateParserCalendar() ) )
            cal.roll( Calendar.MONTH, true );
         
         parseDate( "on 21st 2000",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      
      {
         final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
         end.roll( Calendar.DAY_OF_YEAR, true );
         
         final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
         
         parseDateWithin( "1 day",
                          false,
                          start.get( Calendar.YEAR ),
                          start.get( Calendar.MONTH ),
                          start.get( Calendar.WEEK_OF_YEAR ),
                          start.get( Calendar.DAY_OF_YEAR ),
                          end.get( Calendar.YEAR ),
                          end.get( Calendar.MONTH ),
                          end.get( Calendar.WEEK_OF_YEAR ),
                          end.get( Calendar.DAY_OF_YEAR ) );
      }
      {
         final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
         end.add( Calendar.DAY_OF_YEAR, 2 );
         
         final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
         start.add( Calendar.DAY_OF_YEAR, 1 );
         
         parseDateWithin( "1 day of tomorrow",
                          false,
                          start.get( Calendar.YEAR ),
                          start.get( Calendar.MONTH ),
                          start.get( Calendar.WEEK_OF_YEAR ),
                          start.get( Calendar.DAY_OF_YEAR ),
                          end.get( Calendar.YEAR ),
                          end.get( Calendar.MONTH ),
                          end.get( Calendar.WEEK_OF_YEAR ),
                          end.get( Calendar.DAY_OF_YEAR ) );
      }
      {
         final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
         end.add( Calendar.DAY_OF_YEAR, 2 );
         
         final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
         start.add( Calendar.DAY_OF_YEAR, 1 );
         
         parseDateWithin( "a day of tomorrow",
                          false,
                          start.get( Calendar.YEAR ),
                          start.get( Calendar.MONTH ),
                          start.get( Calendar.WEEK_OF_YEAR ),
                          start.get( Calendar.DAY_OF_YEAR ),
                          end.get( Calendar.YEAR ),
                          end.get( Calendar.MONTH ),
                          end.get( Calendar.WEEK_OF_YEAR ),
                          end.get( Calendar.DAY_OF_YEAR ) );
      }
      {
         final MolokoCalendar end = DateTimeTestHelper.getDateParserCalendar();
         end.set( Calendar.YEAR, 2010 );
         end.set( Calendar.MONTH, Calendar.JULY );
         end.set( Calendar.DAY_OF_MONTH, 3 );
         end.add( Calendar.WEEK_OF_YEAR, -2 );
         
         final MolokoCalendar start = DateTimeTestHelper.getDateParserCalendar();
         start.set( Calendar.YEAR, 2010 );
         start.set( Calendar.MONTH, Calendar.JULY );
         start.set( Calendar.DAY_OF_MONTH, 3 );
         
         parseDateWithin( "2 weeks of july-3rd 2010",
                          true,
                          start.get( Calendar.YEAR ),
                          start.get( Calendar.MONTH ),
                          start.get( Calendar.WEEK_OF_YEAR ),
                          start.get( Calendar.DAY_OF_YEAR ),
                          end.get( Calendar.YEAR ),
                          end.get( Calendar.MONTH ),
                          end.get( Calendar.WEEK_OF_YEAR ),
                          end.get( Calendar.DAY_OF_YEAR ) );
      }
   }
   
}
