import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateWithinReturn;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.TimeParserFactory;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;


public class DateTimeTestHelper
{
   public final static MolokoCalendar getDateParserCalendar()
   {
      MolokoCalendar cal = MolokoCalendar.getInstance();
      cal.setHasTime( false );
      return cal;
   }
   


   public static void parseTime( Locale locale,
                                 String string,
                                 int h,
                                 int m,
                                 int s )
   {
      final ITimeParser timeParser = TimeParserFactory.createTimeParserForLocale( locale );
      
      System.out.println( ">input: " + string );
      
      final MolokoCalendar cal = timeParser.getCalendar();
      
      try
      {
         timeParser.parseTime( string, cal, false );
         
         System.out.println( string + ": " + cal.getTimeInMillis() );
         System.out.println( string + ": " + cal.getTime() );
         System.out.println( string + " has time: " + cal.hasTime() );
         
         Asserts.assertEquals( cal.get( Calendar.HOUR_OF_DAY ),
                               h,
                               "Hour is wrong." );
         Asserts.assertEquals( cal.get( Calendar.MINUTE ),
                               m,
                               "Minute is wrong." );
         Asserts.assertEquals( cal.get( Calendar.SECOND ),
                               s,
                               "Second is wrong." );
         Asserts.assertTrue( cal.hasTime(), "Calendar has no time." );
      }
      catch ( RecognitionException e )
      {
         System.err.println( "Parsing failed!" );
      }
   }
   


   public static void parseTimeSpec( Locale locale,
                                     String string,
                                     int h,
                                     int m,
                                     int s )
   {
      final ITimeParser timeParser = TimeParserFactory.createTimeParserForLocale( locale );
      
      System.out.println( ">input: " + string );
      
      final MolokoCalendar cal = timeParser.getCalendar();
      cal.setTimeZone( TimeZone.getTimeZone( "Europe/Berlin" ) );
      
      try
      {
         timeParser.parseTimeSpec( string, cal, false );
         
         System.out.println( string + ": " + cal.getTimeInMillis() );
         System.out.println( string + ": " + cal.getTime() );
         System.out.println( string + " has time: " + cal.hasTime() );
         
         Asserts.assertEquals( cal.get( Calendar.HOUR_OF_DAY ),
                               h,
                               "Hour is wrong." );
         Asserts.assertEquals( cal.get( Calendar.MINUTE ),
                               m,
                               "Minute is wrong." );
         Asserts.assertEquals( cal.get( Calendar.SECOND ),
                               s,
                               "Second is wrong." );
         Asserts.assertTrue( cal.hasTime(), "Calendar has no time." );
      }
      catch ( RecognitionException e )
      {
         System.err.println( "Parsing failed!" );
      }
   }
   


   public static void parseTimeEstimate( Locale locale,
                                         String string,
                                         int h,
                                         int m,
                                         int s )
   {
      final ITimeParser timeParser = TimeParserFactory.createTimeParserForLocale( locale );
      
      System.out.println( ">input: " + string );
      
      try
      {
         final long diff = timeParser.parseTimeEstimate( string ) / 1000;
         
         Asserts.assertEquals( diff, h * 3600 + m * 60 + s, "Diff is wrong." );
      }
      catch ( RecognitionException e )
      {
         System.err.println( "Parsing failed!" );
      }
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
      
      final MolokoCalendar cal = RtmDateTimeParsing.parseDateTimeSpec( string );
      
      if ( cal == null )
         System.err.println( "Parsing failed!" );
      else
      {
         
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
      
      final ParseDateWithinReturn result = RtmDateTimeParsing.parseDateWithin( string,
                                                                               past );
      
      if ( result != null )
      {
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
      else
         System.err.println( "Parsing failed!" );
   }
}
