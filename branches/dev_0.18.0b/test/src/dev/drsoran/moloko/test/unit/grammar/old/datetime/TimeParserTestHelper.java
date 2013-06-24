package dev.drsoran.moloko.test.grammar.datetime;

import java.util.Calendar;
import java.util.TimeZone;

import org.antlr.runtime.RecognitionException;
import org.junit.Assert;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.datetime.ITimeParser;


public class TimeParserTestHelper
{
   private final ITimeParser timeParser;
   
   
   
   public TimeParserTestHelper( ITimeParser timeParser )
   {
      this.timeParser = timeParser;
   }
   
   
   
   public void parseTime( String timeString, int h, int m, int s )
   {
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
   
   
   
   public void parseTimeSpec( String timeSpecString, int h, int m, int s )
   {
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
   
   
   
   public void parseTimeEstimate( String timeEstimateString, int h, int m, int s )
   {
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
}
