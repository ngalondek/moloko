import java.util.Calendar;
import java.util.TimeZone;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.DateTimeLexer;
import dev.drsoran.moloko.grammar.TimeParser;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


public class TimeParserTestCase
{
   private static void parseTime( String string, int h, int m, int s )
   {
      final DateTimeLexer lexer = new DateTimeLexer( new ANTLRNoCaseStringStream( string ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeParser timeParser = new TimeParser( antlrTokens );
      
      System.out.println( ">input: " + string );
      
      final Calendar cal = TimeParser.getLocalizedCalendar();
      cal.setTimeZone( TimeZone.getTimeZone( "Europe/Berlin" ) );
      
      try
      {
         timeParser.parseTime( cal, false );
         
         System.out.println( string + ": " + cal.getTimeInMillis() );
         System.out.println( string + ": " + cal.getTime() );
         System.out.println( string + " has time: "
                             + cal.isSet( Calendar.HOUR_OF_DAY ) );
         
         Asserts.assertEquals( cal.get( Calendar.HOUR_OF_DAY ), h, "Hour is wrong." );
         Asserts.assertEquals( cal.get( Calendar.MINUTE ), m, "Minute is wrong." );
         Asserts.assertEquals( cal.get( Calendar.SECOND ), s, "Second is wrong." );
         Asserts.assertTrue( cal.isSet( Calendar.HOUR_OF_DAY ), "Calendar has no time." );
      }
      catch ( RecognitionException e )
      {
         System.err.println( e.getMessage() );
      }
   }
   


   private static void parseTimeSpec( String string, int h, int m, int s )
   {
      final DateTimeLexer lexer = new DateTimeLexer( new ANTLRNoCaseStringStream( string ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeParser timeParser = new TimeParser( antlrTokens );
      
      System.out.println( ">input: " + string );
      
      final Calendar cal = TimeParser.getLocalizedCalendar();
      cal.setTimeZone( TimeZone.getTimeZone( "Europe/Berlin" ) );
      
      try
      {
         timeParser.parseTimeSpec( cal, false );
         
         System.out.println( string + ": " + cal.getTimeInMillis() );
         System.out.println( string + ": " + cal.getTime() );
         System.out.println( string + " has time: "
                             + cal.isSet( Calendar.HOUR_OF_DAY ) );
         
         Asserts.assertEquals( cal.get( Calendar.HOUR_OF_DAY ), h, "Hour is wrong." );
         Asserts.assertEquals( cal.get( Calendar.MINUTE ), m, "Minute is wrong." );
         Asserts.assertEquals( cal.get( Calendar.SECOND ), s, "Second is wrong." );
         Asserts.assertTrue( cal.isSet( Calendar.HOUR_OF_DAY ), "Calendar has no time." );
      }
      catch ( RecognitionException e )
      {
         System.err.println( e.getMessage() );
      }
   }
   


   private static void parseTimeEstimate( String string, int h, int m, int s )
   {
      final DateTimeLexer lexer = new DateTimeLexer( new ANTLRNoCaseStringStream( string ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeParser timeParser = new TimeParser( antlrTokens );
      
      System.out.println( ">input: " + string );
      
      try
      {
         final long diff = timeParser.parseTimeEstimate() / 1000;
         
         Asserts.assertEquals( diff, h * 3600 + m * 60 + s, "Diff is wrong." );
      }
      catch ( RecognitionException e )
      {
         System.err.println( e.getMessage() );
      }
   }
   
   public final static void execute()
   {
      parseTime( "@1310", 13, 10, 0 );
      parseTime( "12:13:25", 12, 13, 0 );
      parseTime( "12.13", 12, 13, 0 );
      parseTime( "midday", 12, 0, 0 );
      parseTime( "at noon", 12, 0, 0 );
      parseTime( "@midnight", 23, 59, 59 );
      parseTime( "at 11:00 am", 11, 00, 00 );
      parseTime( "1100p", 23, 00, 00 );
      parseTime( "@11a", 11, 00, 00 );
      
      parseTimeSpec( "12", 12, 0, 0 );
      parseTimeSpec( "1210", 12, 0, 0 );
      parseTimeSpec( "12:13", 12, 13, 0 );
      parseTimeSpec( "12:13:25", 12, 13, 25 );
      parseTimeSpec( "12 h", 12, 0, 0 );
      parseTimeSpec( "12 h 13 minutes", 12, 13, 0 );
      parseTimeSpec( "12 h 13 minutes 25 sec", 12, 13, 25 );
      parseTimeSpec( "13 minutes 12 h 25 sec", 12, 13, 25 );
      parseTimeSpec( "12 hours 25 sec 1 h", 13, 0, 25 );
      parseTimeSpec( "1.5 hours 25 sec", 1, 30, 25 );
      
      parseTimeEstimate( "1 day 15 min", 1 * 24, 15, 0 );
      parseTimeEstimate( "1 h 15 min 2 days 1.5 hours ", 2 * 24 + 2, 45, 0 );
      parseTimeEstimate( "1 min 1 second", 0, 1, 1 );
   }
}
