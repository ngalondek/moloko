import java.util.Calendar;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.TimeSpecLexer;
import dev.drsoran.moloko.grammar.TimeSpecParser;


public class Main
{
   
   /**
    * @param args
    */
   public static void main( String[] args )
   {
//      SimpleDateFormat sdf = new SimpleDateFormat( "yyy" );
//      
//      try
//      {
//         sdf.parse( "106" );
//      }
//      catch ( ParseException e1 )
//      {
//         // TODO Auto-generated catch block
//         e1.printStackTrace();
//      }
//      
//      System.out.println( sdf.getCalendar().getTime() );
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "today" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "date_natural: " + cal.getTimeInMillis() );
            System.out.println( "date_natural: " + cal.getTime() );
            System.out.println( "date_natural has time: " + cal.isSet( Calendar.HOUR_OF_DAY ) );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "tomorrow@9" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "date_natural_w_time: " + cal.getTimeInMillis() );
            System.out.println( "date_natural_w_time: " + cal.getTime() );
            System.out.println( "date_natural_w_time has time: " + cal.isSet( Calendar.HOUR_OF_DAY ) );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "end of month" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "date_end_of_the_MW: " + cal.getTimeInMillis() );
            System.out.println( "date_end_of_the_MW: " + cal.getTime() );
            System.out.println( "date_end_of_the_MW has time: " + cal.isSet( Calendar.HOUR_OF_DAY ) );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "in 1 year and 2 mons@7" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "date_in_X_YMWD: " + cal.getTimeInMillis() );
            System.out.println( "date_in_X_YMWD: " + cal.getTime() );
            System.out.println( "date_in_X_YMWD has time: " + cal.isSet( Calendar.HOUR_OF_DAY ) );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "next monday 7:10" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "date_weekday: " + cal.getTimeInMillis() );
            System.out.println( "date_weekday: " + cal.getTime() );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "on july 3rd" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "date_M_Xst: " + cal.getTimeInMillis() );
            System.out.println( "date_M_Xst: " + cal.getTime() );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "on 21st" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "date_on_Xst: " + cal.getTimeInMillis() );
            System.out.println( "date_on_Xst: " + cal.getTime() );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "on 21st of feb 6" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "date_on_Xst_M_year_short: " + cal.getTimeInMillis() );
            System.out.println( "date_on_Xst_M_year_short: " + cal.getTime() );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "1-july-2011" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "date_Xst_of_MY: " + cal.getTimeInMillis() );
            System.out.println( "date_Xst_of_MY: " + cal.getTime() );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "10.10.2010" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "full_date: " + cal.getTimeInMillis() );
            System.out.println( "full_date: " + cal.getTime() );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "10.10.2010 1 PM" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "full_date_time: " + cal.getTimeInMillis() );
            System.out.println( "full_date_time: " + cal.getTime() );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "1.5 h" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "1.5h: " + cal.getTimeInMillis() );
            System.out.println( "1.5h: " + cal.getTime() );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "@1310" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "time_point_in_timespec_@: " + cal.getTimeInMillis() );
            System.out.println( "time_point_in_timespec_@: " + cal.getTime() );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "midday" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final Calendar cal = TimeSpecParser.getLocalizedCalendar();
            parser.parseDateTime( cal );
            System.out.println( "time_point_in_timespec_lit: " + cal.getTimeInMillis() );
            System.out.println( "time_point_in_timespec_lit: " + cal.getTime() );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }     
   }
}
