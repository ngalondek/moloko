package dev.drsoran.moloko.test.grammar.datetime;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.antlr.runtime.RecognitionException;
import org.junit.Assert;

import dev.drsoran.moloko.grammar.IDateFormatContext;
import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateWithinReturn;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;
import dev.drsoran.moloko.grammar.datetime.TimeParserFactory;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;


public class DateTimeTestHelper
{
   public static MolokoCalendar getDateParserCalendar()
   {
      MolokoCalendar cal = MolokoCalendar.getInstance();
      cal.setHasTime( false );
      return cal;
   }
   
   
   
   public static void parseTime( String timeString, int h, int m, int s )
   {
      final ITimeParser timeParser = TimeParserFactory.createTimeParserForLocale( Locale.getDefault() );
      final MolokoCalendar cal = timeParser.getCalendar();
      
      try
      {
         timeParser.parseTime( timeString, cal, false );
         
         // System.out.println( string + ": " + cal.getTimeInMillis() );
         // System.out.println( string + ": " + cal.getTime() );
         // System.out.println( string + " has time: " + cal.hasTime() );
         
         Assert.assertEquals( "Hour is wrong.",
                              h,
                              cal.get( Calendar.HOUR_OF_DAY ) );
         Assert.assertEquals( "Minute is wrong.", m, cal.get( Calendar.MINUTE ) );
         Assert.assertEquals( "Second is wrong.", s, cal.get( Calendar.SECOND ) );
         Assert.assertTrue( "Calendar has no time.", cal.hasTime() );
      }
      catch ( RecognitionException e )
      {
         Assert.fail( "Parsing '" + timeString + "' failed!" );
      }
   }
   
   
   
   public static void parseTimeSpec( String timeSpecString, int h, int m, int s )
   {
      final ITimeParser timeParser = TimeParserFactory.createTimeParserForLocale( Locale.getDefault() );
      
      final MolokoCalendar cal = timeParser.getCalendar();
      cal.setTimeZone( TimeZone.getTimeZone( "Europe/Berlin" ) );
      
      try
      {
         timeParser.parseTimeSpec( timeSpecString, cal, false );
         
         // System.out.println( timeSpecString + ": " + cal.getTimeInMillis() );
         // System.out.println( timeSpecString + ": " + cal.getTime() );
         // System.out.println( timeSpecString + " has time: " + cal.hasTime() );
         
         Assert.assertEquals( "Hour is wrong.",
                              h,
                              cal.get( Calendar.HOUR_OF_DAY ) );
         Assert.assertEquals( "Minute is wrong.", m, cal.get( Calendar.MINUTE ) );
         Assert.assertEquals( "Second is wrong.", s, cal.get( Calendar.SECOND ) );
         Assert.assertTrue( "Calendar has no time.", cal.hasTime() );
      }
      catch ( RecognitionException e )
      {
         Assert.fail( "Parsing '" + timeSpecString + "' failed!" );
      }
   }
   
   
   
   public static void parseTimeEstimate( String timeEstimateString,
                                         int h,
                                         int m,
                                         int s )
   {
      final ITimeParser timeParser = TimeParserFactory.createTimeParserForLocale( Locale.getDefault() );
      
      try
      {
         final long diff = timeParser.parseTimeEstimate( timeEstimateString ) / 1000;
         
         Assert.assertEquals( "Diff is wrong.", h * 3600 + m * 60 + s, diff );
      }
      catch ( RecognitionException e )
      {
         Assert.fail( "Parsing '" + timeEstimateString + "' failed!" );
      }
   }
   
   
   
   public static void parseDate( IDateFormatContext dateFormatContext,
                                 String dateString,
                                 int d,
                                 int m,
                                 int y,
                                 int h,
                                 int min,
                                 int s )
   {
      parseDate( dateFormatContext,
                 dateString,
                 d,
                 m,
                 y,
                 h,
                 min,
                 s,
                 true,
                 h != -1 );
   }
   
   
   
   public static void parseDate( IDateFormatContext dateFormatContext,
                                 String dateString,
                                 int d,
                                 int m,
                                 int y,
                                 int h,
                                 int min,
                                 int s,
                                 boolean hasDate,
                                 boolean hasTime )
   {
      RtmDateTimeParsing.setDateFormatContext( dateFormatContext );
      
      final MolokoCalendar cal = RtmDateTimeParsing.parseDateTimeSpec( dateString );
      if ( cal != null )
      {
         Assert.assertEquals( "Day is wrong.",
                              d,
                              cal.get( Calendar.DAY_OF_MONTH ) );
         Assert.assertEquals( "Month is wrong.", m, cal.get( Calendar.MONTH ) );
         Assert.assertEquals( "Year is wrong.", y, cal.get( Calendar.YEAR ) );
         
         if ( hasDate )
            Assert.assertTrue( "Calendar has no date.", cal.hasDate() );
         else
            Assert.assertFalse( "Calendar has date.", cal.hasDate() );
         
         if ( hasTime )
            Assert.assertTrue( "Calendar has no time.", cal.hasTime() );
         else
            Assert.assertFalse( "Calendar has time.", cal.hasTime() );
         
         if ( h != -1 )
            Assert.assertEquals( "Hour is wrong.",
                                 h,
                                 cal.get( Calendar.HOUR_OF_DAY ) );
         if ( min != -1 )
            Assert.assertEquals( "Minute is wrong.",
                                 min,
                                 cal.get( Calendar.MINUTE ) );
         if ( s != -1 )
            Assert.assertEquals( "Second is wrong.",
                                 s,
                                 cal.get( Calendar.SECOND ) );
      }
      else
      {
         Assert.fail( "Parsing '" + dateString + "' failed!" );
      }
   }
   
   
   
   public static void parseDateWithin( IDateFormatContext dateFormatContext,
                                       String dateWithinString,
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
      RtmDateTimeParsing.setDateFormatContext( dateFormatContext );
      
      final ParseDateWithinReturn result = RtmDateTimeParsing.parseDateWithin( dateWithinString,
                                                                               past );
      
      if ( result != null )
      {
         final MolokoCalendar start = result.startEpoch;
         final MolokoCalendar end = result.endEpoch;
         
         // System.out.println( dateWithinString + "_start: "
         // + start.getTimeInMillis() );
         // System.out.println( dateWithinString + "_start: " + start.getTime() );
         // System.out.println( dateWithinString + "_start has time: "
         // + start.hasTime() );
         //
         // System.out.println( dateWithinString + "_end: "
         // + end.getTimeInMillis() );
         // System.out.println( dateWithinString + "_end: " + end.getTime() );
         // System.out.println( dateWithinString + "_end has time: "
         // + end.hasTime() );
         
         Assert.assertEquals( "Day is wrong.",
                              sd,
                              start.get( Calendar.DAY_OF_YEAR ) );
         Assert.assertEquals( "Week is wrong.",
                              sw,
                              start.get( Calendar.WEEK_OF_YEAR ) );
         Assert.assertEquals( "Month is wrong.", sm, start.get( Calendar.MONTH ) );
         Assert.assertEquals( "Year is wrong.", sy, start.get( Calendar.YEAR ) );
         
         Assert.assertEquals( "Day is wrong.",
                              ed,
                              end.get( Calendar.DAY_OF_YEAR ) );
         Assert.assertEquals( "Week is wrong.",
                              ew,
                              end.get( Calendar.WEEK_OF_YEAR ) );
         Assert.assertEquals( "Month is wrong.", em, end.get( Calendar.MONTH ) );
         Assert.assertEquals( "Year is wrong.", ey, end.get( Calendar.YEAR ) );
      }
      else
      {
         Assert.fail( "Parsing '" + dateWithinString + "' failed!" );
      }
   }
}
