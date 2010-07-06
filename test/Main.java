
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import dev.drsoran.moloko.grammar.DateTimeParserLexer;
import dev.drsoran.moloko.grammar.DateTimeParserParser;
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
      
      {
         final TimeSpecLexer tsl = new TimeSpecLexer( new ANTLRStringStream( "1.5 h" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( tsl );
         final TimeSpecParser parser = new TimeSpecParser( antlrTokens );
         
         try
         {
            System.out.println( "1.5h: " + parser.timespec() );
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
            final long res = parser.timespec();
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
            final long res = parser.timespec();
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
         final DateTimeParserLexer lexer = new DateTimeParserLexer( new ANTLRStringStream( "2010-11-2" ) );
         final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
         final DateTimeParserParser parser = new DateTimeParserParser( antlrTokens );
         
         try
         {
            final long res = parser.fullDate( true );
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
//         Calendar c = Calendar.getInstance();
//         c.setTimeInMillis( System.currentTimeMillis() );
//         c.set( Calendar.HOUR_OF_DAY, 23 );
//         c.set( Calendar.MINUTE, 59 );
//         c.set( Calendar.SECOND, 59 );
//         
//         System.out.println( c.getTime().getTime() );
      }
   }
}
