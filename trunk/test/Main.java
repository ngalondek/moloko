import java.util.Calendar;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.TimeSpecLexer;
import dev.drsoran.moloko.grammar.TimeSpecParser;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


public class Main
{
   private static void parseString( String name, String string )
   {
      final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRNoCaseStringStream( string ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
      final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
      
      System.out.println( ">input: " + string );
      
      final Calendar cal = TimeSpecParser.getLocalizedCalendar();
      
      // first try to parse time
      try
      {
         parser.time_spec( cal );
      }
      catch ( RecognitionException e )
      {
         tsl.reset();
         
         try
         {
            parser.parseDateTime( cal, true );
         }
         catch ( RecognitionException re )
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
      parseString( "date_on_Xst", "on 21st" );
      parseString( "date_on_Xst_M_year_short", "on 21st of feb 6" );
      parseString( "date_Xst_of_MY", "1-july-2011" );
      parseString( "full_date", "10.10.2010" );
      parseString( "full_date_time", "10.10.2010 1 PM" );
      parseString( "1.5h", "1.5 h" );
      parseString( "date_nat_time_point_in_timespec_@", "tom@12" );
      parseString( "time_point_in_timespec_@", "@1310" );
      parseString( "time_point_in_timespec_sep", "12:13:25" );
      parseString( "time_point_in_timespec_lit", "midday" );
   }
}
