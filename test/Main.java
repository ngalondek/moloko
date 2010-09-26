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
   private static void parseString( String name, String string )
   {
      final DateTimeLexer lexer = new DateTimeLexer( new ANTLRNoCaseStringStream( string ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeParser timeParser = new TimeParser( antlrTokens );
      
      System.out.println( ">input: " + string );
      
      final Calendar cal = TimeParser.getLocalizedCalendar();
      cal.setTimeZone( TimeZone.getTimeZone( "Europe/Berlin" ) );
      
      boolean finished = false;
      // first try to parse time
      try
      {
         // The parser can adjust the day of week
         // for times in the past.
         timeParser.parseTime( cal, true );
         finished = true;
      }
      catch ( RecognitionException e )
      {
      }
      
      if ( !finished )
      {
         antlrTokens.reset();
         
         final DateParser dateParser = new DateParser( antlrTokens );
         
         try
         {
            dateParser.parseDate( cal );
            
            try
            {
               // Check if there is a time trailing.
               // The parser can not adjust the day of week
               // for times in the past.
               timeParser.parseTime( cal, false );
            }
            catch ( RecognitionException re2 )
            {
            }
            finally
            {
               finished = true;
            }
         }
         catch ( RecognitionException e )
         {
            e.printStackTrace();
         }
      }
      
      System.out.println( name + ": " + cal.getTimeInMillis() );
      System.out.println( name + ": " + cal.getTime() );
      System.out.println( name + " has time: "
         + cal.isSet( Calendar.HOUR_OF_DAY ) );
   }
   


   /**
    * @param args
    */
   public static void main( String[] args )
   {
      // SimpleDateFormat sdf = new SimpleDateFormat( "yyy" );
      //      
      // try
      // {
      // sdf.parse( "106" );
      // }
      // catch ( ParseException e1 )
      // {
      // // TODO Auto-generated catch block
      // e1.printStackTrace();
      // }
      //      
      // System.out.println( sdf.getCalendar().getTime() );
      
      parseString( "date_natural", "today" );
      parseString( "date_natural_w_time", "tomorrow@9" );
      parseString( "date_end_of_the_MW", "end of month" );
      parseString( "date_in_X_YMWD", "in 1 year and 2 mons@7" );
      parseString( "date_weekday", "next monday 7:10" );
      parseString( "date_M_Xst", "on july 3rd" );
      parseString( "date_M_Xst", "on july 3rd 2000" );
      parseString( "date_on_Xst", "on 21st" );
      parseString( "date_on_Xst_M_year_short", "on 21st of feb 6" );
      parseString( "date_Xst_of_MY", "1-july-2011" );
      parseString( "full_date", "10.10.2010" );
      parseString( "full_date_time", "10.10.2010 1 PM" );
      parseString( "1.5h", "1.5 h" );
      parseString( "time_point_in_timespec_@", "@1310" );
      parseString( "time_point_in_timespec_sep", "12:13:25" );
      parseString( "time_point_in_timespec_lit", "midday" );
      parseString( "date_nat + time_point_in_timespec_@", "tom@12" );
      parseString( "date_nat + time_point_in_timespec_@", "tod@1310" );
      parseString( "date_on_Xst + time_point_in_timespec", "on 21st 2000" );
   }
}
