import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
      // ANTLRStringStream input = new ANTLRStringStream( "status:incomplete AND postponed:\">2\"" );
      // RtmSmartFilterLexer lexer = new RtmSmartFilterLexer( input );
      //      
      // System.out.println( lexer.getResult() );
      
//      try
//      {
//         System.out.println( new Date( new SimpleDateFormat( "dd.MMM.yyyy",
//                                                             Locale.ENGLISH ).parse( "1.July.2010" )
//                                                                             .getTime() ) );
         
//         Calendar c = Calendar.getInstance( Locale.ENGLISH );
//         
//         SimpleDateFormat df = new SimpleDateFormat( "EE", Locale.ENGLISH );
//         df.getCalendar().setTime( df.parse( "thu" ) );
//         df.getCalendar().set( Calendar.YEAR, c.get( Calendar.YEAR ) );
//         df.getCalendar().set( Calendar.WEEK_OF_YEAR, c.get( Calendar.WEEK_OF_YEAR ) );
//         
//         if ( df.getCalendar().before( c ) )
//            df.getCalendar().roll( Calendar.WEEK_OF_YEAR, true );
//         
//         System.out.println( df.getCalendar().getTime() );
//      }
//      catch ( ParseException e1 )
//      {
//         // TODO Auto-generated catch block
//         e1.printStackTrace();
//      }

      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "end of month" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            final long res = parser.parseDateTime();
            System.out.println( "date_end_of_the_MW: " + res );
            System.out.println( "date_end_of_the_MW: " + new Date( res ) );
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
            final long res = parser.parseDateTime();
            System.out.println( "date_in_X_YMWD: " + res );
            System.out.println( "date_in_X_YMWD: " + new Date( res ) );
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
            final long res = parser.parseDateTime();
            System.out.println( "date_weekday: " + res );
            System.out.println( "date_weekday: " + new Date( res ) );
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
            final long res = parser.parseDateTime();
            System.out.println( "date_M_Xst: " + res );
            System.out.println( "date_M_Xst: " + new Date( res ) );
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
            final long res = parser.parseDateTime();
            System.out.println( "date_Xst_of_M: " + res );
            System.out.println( "date_Xst_of_M: " + new Date( res ) );
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
            final long res = parser.parseDateTime();
            System.out.println( "full_date: " + res );
            System.out.println( "full_date: " + new Date( res ) );
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
            final long res = parser.parseDateTime();
            System.out.println( "full_date_time: " + res );
            System.out.println( "full_date_time: " + new Date( res ) );
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
            System.out.println( "1.5h: " + parser.parseDateTime() );
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
            final long res = parser.parseDateTime();
            System.out.println( res );
            System.out.println( new Date( res ) );
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
            final long res = parser.parseDateTime();
            System.out.println( res );
            System.out.println( new Date( res ) );
         }
         catch ( RecognitionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      
      // {
      // final DateTimeParserLexer lexer = new DateTimeParserLexer( new ANTLRStringStream( "2010-11-2" ) );
      // final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      // final DateTimeParserParser parser = new DateTimeParserParser( antlrTokens );
      //         
      // try
      // {
      // final long res = parser.fullDate( true );
      // System.out.println( res );
      // System.out.println( new Date( res ) );
      // }
      // catch ( RecognitionException e )
      // {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }
      // }
      
      {
         // Calendar c = Calendar.getInstance();
         // c.setTimeInMillis( System.currentTimeMillis() );
         // c.set( Calendar.HOUR_OF_DAY, 23 );
         // c.set( Calendar.MINUTE, 59 );
         // c.set( Calendar.SECOND, 59 );
         //         
         // System.out.println( c.getTime().getTime() );
      }
   }
}
