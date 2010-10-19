import java.util.Calendar;
import java.util.TimeZone;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.DateParser;
import dev.drsoran.moloko.grammar.DateTimeLexer;
import dev.drsoran.moloko.grammar.TimeParser;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


public class Main
{
   private static void assertEquals( long x, long y, String message )
   {
      if ( x != y )
      {
         System.err.println( message + " Expected " + x + " to be " + y + "." );
      }
   }
   


   private static void assertTrue( boolean x, String message )
   {
      if ( !x )
      {
         System.err.println( message + " Expected " + x + " to be true." );
      }
   }
   


   private static void parseDate( String string, int d, int m, int y )
   {
      parseDate( string, d, m, y, -1, -1, -1 );
   }
   


   private static void parseDate( String string,
                                  int d,
                                  int m,
                                  int y,
                                  int h,
                                  int min,
                                  int s )
   {
      final DateTimeLexer lexer = new DateTimeLexer( new ANTLRNoCaseStringStream( string ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeParser timeParser = new TimeParser( antlrTokens );
      
      System.out.println( ">input: " + string );
      
      final Calendar cal = TimeParser.getLocalizedCalendar();
      cal.setTimeZone( TimeZone.getTimeZone( "Europe/Berlin" ) );
      
      boolean eof = false;
      boolean hasTime = false;
      boolean hasDate = false;
      boolean error = false;
      
      // first try to parse time spec
      try
      {
         // The parser can adjust the day of week
         // for times in the past.
         eof = timeParser.parseTimeSpec( cal, !hasDate );
         hasTime = true;
      }
      catch ( RecognitionException e )
      {
      }
      
      if ( !eof )
      {
         if ( !hasTime )
            antlrTokens.reset();
         
         final DateParser dateParser = new DateParser( antlrTokens );
         
         try
         {
            eof = dateParser.parseDate( cal, !hasTime );
            hasDate = true;
         }
         catch ( RecognitionException e )
         {
            error = true;
         }
      }
      
      // Check if there is a time trailing.
      // The parser can not adjust the day of week
      // for times in the past.
      if ( !error && !eof && hasDate && !hasTime )
      {
         final int streamPos = antlrTokens.mark();
         
         try
         {
            eof = timeParser.parseTime( cal, !hasDate );
            hasTime = true;
         }
         catch ( RecognitionException re2 )
         {
         }
         
         if ( !hasTime )
         {
            antlrTokens.rewind( streamPos );
            
            try
            {
               eof = timeParser.parseTimeSpec( cal, !hasDate );
               hasTime = true;
            }
            catch ( RecognitionException re3 )
            {
               error = true;
            }
         }
      }
      
      if ( error )
         System.err.println( "Parsing failed!" );
      
      final boolean calHasTime = cal.isSet( Calendar.HOUR_OF_DAY );
      
      System.out.println( string + ": " + cal.getTimeInMillis() );
      System.out.println( string + ": " + cal.getTime() );
      System.out.println( string + " has time: " + calHasTime );
      
      assertEquals( cal.get( Calendar.DAY_OF_MONTH ), d, "Day is wrong." );
      assertEquals( cal.get( Calendar.MONTH ), m, "Month is wrong." );
      assertEquals( cal.get( Calendar.YEAR ), y, "Year is wrong." );
      
      if ( h != -1 )
      {
         assertEquals( cal.get( Calendar.HOUR_OF_DAY ), h, "Hour is wrong." );
         assertTrue( calHasTime, "Calendar has no time." );
      }
      else
         assertTrue( !calHasTime, "Calendar has time." );
      
      if ( min != -1 )
         assertEquals( cal.get( Calendar.MINUTE ), min, "Minute is wrong." );
      if ( s != -1 )
         assertEquals( cal.get( Calendar.SECOND ), s, "Second is wrong." );
   }
   


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
         
         assertEquals( cal.get( Calendar.HOUR_OF_DAY ), h, "Hour is wrong." );
         assertEquals( cal.get( Calendar.MINUTE ), m, "Minute is wrong." );
         assertEquals( cal.get( Calendar.SECOND ), s, "Second is wrong." );
         assertTrue( cal.isSet( Calendar.HOUR_OF_DAY ), "Calendar has no time." );
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
         
         assertEquals( cal.get( Calendar.HOUR_OF_DAY ), h, "Hour is wrong." );
         assertEquals( cal.get( Calendar.MINUTE ), m, "Minute is wrong." );
         assertEquals( cal.get( Calendar.SECOND ), s, "Second is wrong." );
         assertTrue( cal.isSet( Calendar.HOUR_OF_DAY ), "Calendar has no time." );
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
      
      final Calendar cal = TimeParser.getLocalizedCalendar();
      cal.setTimeZone( TimeZone.getTimeZone( "Europe/Berlin" ) );
      
      final Calendar calEst = TimeParser.getLocalizedCalendar();
      cal.setTimeZone( TimeZone.getTimeZone( "Europe/Berlin" ) );
      cal.setTimeInMillis( cal.getTimeInMillis() );
      
      try
      {
         timeParser.parseTimeEstimate( calEst );
         
         System.out.println( string + ": " + calEst.getTimeInMillis() );
         System.out.println( string + ": " + calEst.getTime() );
         System.out.println( string + " has time: "
                             + calEst.isSet( Calendar.HOUR_OF_DAY ) );
         
         final long diff = ( calEst.getTimeInMillis() - cal.getTimeInMillis() ) / 1000;       
         
         assertEquals( diff,  h * 3600 + m * 60 + s, "Diff is wrong." );
         assertTrue( cal.isSet( Calendar.HOUR_OF_DAY ), "Calendar has no time." );
      }
      catch ( RecognitionException e )
      {
         System.err.println( e.getMessage() );
      }
   }


   /**
    * @param args
    */
   public static void main( String[] args )
   {      
      {
         final Calendar cal = Calendar.getInstance();
         parseDate( "today",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.roll( Calendar.DAY_OF_MONTH, true );
         parseDate( "tomorrow@9",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    9,
                    0,
                    0 );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.DAY_OF_MONTH,
                  cal.getActualMaximum( Calendar.DAY_OF_MONTH ) );
         parseDate( "end of month",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.roll( Calendar.YEAR, true );
         cal.add( Calendar.MONTH, 2 );
         parseDate( "in 1 year and 2 mons@7",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    7,
                    0,
                    0 );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.DAY_OF_WEEK, Calendar.MONDAY );
         
         if ( cal.before( Calendar.getInstance() ) )
            cal.roll( Calendar.WEEK_OF_YEAR, true );
         
         // due to "next" Monday
         cal.roll( Calendar.WEEK_OF_YEAR, true );
         
         parseDate( "next monday 7:10",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    7,
                    10,
                    0 );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.MONTH, Calendar.JULY );
         cal.set( Calendar.DAY_OF_MONTH, 3 );
         
         if ( cal.before( Calendar.getInstance() ) )
            cal.roll( Calendar.YEAR, true );
         
         parseDate( "on july 3rd",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.YEAR, 2000 );
         cal.set( Calendar.MONTH, Calendar.JULY );
         cal.set( Calendar.DAY_OF_MONTH, 3 );
         
         parseDate( "on july 3rd 2000",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.DAY_OF_MONTH, 21 );
         
         if ( cal.before( Calendar.getInstance() ) )
            cal.roll( Calendar.MONTH, true );
         
         parseDate( "on 21st",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.DAY_OF_MONTH, 3 );
         cal.set( Calendar.MONTH, Calendar.JUNE );
         
         if ( cal.before( Calendar.getInstance() ) )
            cal.roll( Calendar.YEAR, true );
         
         parseDate( "on 3rd of june",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.YEAR, 2006 );
         cal.set( Calendar.MONTH, Calendar.FEBRUARY );
         cal.set( Calendar.DAY_OF_MONTH, 21 );
         
         parseDate( "on 21st of feb 6",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.YEAR, 2011 );
         cal.set( Calendar.MONTH, Calendar.JULY );
         cal.set( Calendar.DAY_OF_MONTH, 1 );
         
         parseDate( "1-july-2011",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "10.10.2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ) );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "10.10.2010 1 PM",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    13,
                    0,
                    0 );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "10.10.2010, 13h 15 minutes",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    13,
                    15,
                    0 );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.YEAR, 2010 );
         cal.set( Calendar.MONTH, Calendar.OCTOBER );
         cal.set( Calendar.DAY_OF_MONTH, 10 );
         
         parseDate( "13:15 10.10.2010",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    13,
                    15,
                    0 );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.roll( Calendar.DAY_OF_MONTH, true );
         parseDate( "tom@12",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    12,
                    0,
                    0 );
      }
      {
         final Calendar cal = Calendar.getInstance();
         parseDate( "tod@1310",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    13,
                    10,
                    0 );
      }
      {
         final Calendar cal = Calendar.getInstance();
         cal.set( Calendar.DAY_OF_MONTH, 21 );
         
         if ( cal.before( Calendar.getInstance() ) )
            cal.roll( Calendar.MONTH, true );
         
         parseDate( "on 21st 2000",
                    cal.get( Calendar.DAY_OF_MONTH ),
                    cal.get( Calendar.MONTH ),
                    cal.get( Calendar.YEAR ),
                    20,
                    00,
                    00 );
      }
      
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
