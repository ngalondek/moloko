// $ANTLR 3.2 Sep 23, 2009 12:02:23
// F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g
// 2010-10-21 19:27:30

package dev.drsoran.moloko.grammar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;


public class DateParser extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEVER", "TODAY", "TOMORROW",
    "YESTERDAY", "AT", "ON", "IN", "OF", "NEXT", "AND", "END", "THE", "STs",
    "NOW", "TONIGHT", "MIDNIGHT", "MIDDAY", "NOON", "YEARS", "MONTHS", "WEEKS",
    "DAYS", "HOURS", "MINUTES", "SECONDS", "MONTH", "WEEKDAY", "DATE_SEP",
    "DOT", "COLON", "MINUS", "MINUS_A", "COMMA", "INT", "A", "AM", "PM",
    "NUM_STR", "STRING", "WS" };
   
   public static final int STs = 16;
   
   public static final int MIDDAY = 20;
   
   public static final int THE = 15;
   
   public static final int SECONDS = 28;
   
   public static final int NOW = 17;
   
   public static final int MIDNIGHT = 19;
   
   public static final int AND = 13;
   
   public static final int EOF = -1;
   
   public static final int MONTH = 29;
   
   public static final int AT = 8;
   
   public static final int WEEKDAY = 30;
   
   public static final int IN = 10;
   
   public static final int TONIGHT = 18;
   
   public static final int COMMA = 36;
   
   public static final int NOON = 21;
   
   public static final int NEXT = 12;
   
   public static final int DOT = 32;
   
   public static final int AM = 39;
   
   public static final int TOMORROW = 6;
   
   public static final int TODAY = 5;
   
   public static final int A = 38;
   
   public static final int MINUS_A = 35;
   
   public static final int ON = 9;
   
   public static final int INT = 37;
   
   public static final int MINUS = 34;
   
   public static final int OF = 11;
   
   public static final int YEARS = 22;
   
   public static final int NUM_STR = 41;
   
   public static final int MINUTES = 27;
   
   public static final int COLON = 33;
   
   public static final int DAYS = 25;
   
   public static final int WEEKS = 24;
   
   public static final int WS = 43;
   
   public static final int MONTHS = 23;
   
   public static final int PM = 40;
   
   public static final int NEVER = 4;
   
   public static final int DATE_SEP = 31;
   
   public static final int END = 14;
   
   public static final int YESTERDAY = 7;
   
   public static final int HOURS = 26;
   
   public static final int STRING = 42;
   
   

   // delegates
   // delegators
   
   public DateParser( TokenStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public DateParser( TokenStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   public String[] getTokenNames()
   {
      return DateParser.tokenNames;
   }
   


   public String getGrammarFileName()
   {
      return "F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g";
   }
   


   public DateParser()
   {
      super( null );
   }
   
   private final static Locale LOCALE = Locale.ENGLISH;
   
   private final static HashMap< String, Integer > numberLookUp = new HashMap< String, Integer >();
   
   static
   {
      numberLookUp.put( "one", 1 );
      numberLookUp.put( "two", 2 );
      numberLookUp.put( "three", 3 );
      numberLookUp.put( "four", 4 );
      numberLookUp.put( "five", 5 );
      numberLookUp.put( "six", 6 );
      numberLookUp.put( "seven", 7 );
      numberLookUp.put( "eight", 8 );
      numberLookUp.put( "nine", 9 );
      numberLookUp.put( "ten", 10 );
   }
   
   

   public final static Calendar getLocalizedCalendar()
   {
      final Calendar cal = Calendar.getInstance( LOCALE );
      
      cal.clear( Calendar.HOUR );
      cal.clear( Calendar.HOUR_OF_DAY );
      cal.clear( Calendar.MINUTE );
      cal.clear( Calendar.SECOND );
      cal.clear( Calendar.MILLISECOND );
      
      return cal;
   }
   


   private final static int strToNumber( String string )
   {
      int res = -1;
      
      final Integer val = numberLookUp.get( string );
      
      if ( val != null )
         res = val;
      
      return res;
   }
   


   private final static void parseFullDate( Calendar cal,
                                            String day,
                                            String month,
                                            String year,
                                            boolean textMonth ) throws RecognitionException
   {
      try
      {
         SimpleDateFormat sdf = null;
         
         if ( !textMonth )
         {
            sdf = new SimpleDateFormat( "dd.MM.yyyy" );
         }
         else
         {
            sdf = new SimpleDateFormat( "dd.MMM.yyyy", LOCALE /* Locale for MMM */);
         }
         
         sdf.parse( day + "." + month + "." + year );
         
         final Calendar sdfCal = sdf.getCalendar();
         
         cal.set( Calendar.DAY_OF_MONTH, sdfCal.get( Calendar.DAY_OF_MONTH ) );
         cal.set( Calendar.MONTH, sdfCal.get( Calendar.MONTH ) );
         cal.set( Calendar.YEAR, sdfCal.get( Calendar.YEAR ) );
      }
      catch ( ParseException e )
      {
         throw new RecognitionException();
      }
   }
   


   private final static void parseTextMonth( Calendar cal, String month ) throws RecognitionException
   {
      try
      {
         final SimpleDateFormat sdf = new SimpleDateFormat( "MMM", LOCALE );
         sdf.parse( month );
         
         cal.set( Calendar.MONTH, sdf.getCalendar().get( Calendar.MONTH ) );
      }
      catch ( ParseException e )
      {
         throw new RecognitionException();
      }
   }
   


   private final static void parseYear( Calendar cal, String yearStr ) throws RecognitionException
   {
      final int len = yearStr.length();
      
      int year = 0;
      
      try
      {
         if ( len < 4 )
         {
            final SimpleDateFormat sdf = new SimpleDateFormat( "yy" );
            
            if ( len == 1 )
            {
               yearStr = "0" + yearStr;
            }
            else if ( len == 3 )
            {
               yearStr = yearStr.substring( 1, len );
            }
            
            sdf.parse( yearStr );
            year = sdf.getCalendar().get( Calendar.YEAR );
         }
         else
         {
            year = Integer.parseInt( yearStr );
         }
         
         cal.set( Calendar.YEAR, year );
      }
      catch ( ParseException pe )
      {
         throw new RecognitionException();
      }
      catch ( NumberFormatException nfe )
      {
         throw new RecognitionException();
      }
   }
   


   private final static void rollToEndOf( int field, Calendar cal )
   {
      final int ref = cal.get( field );
      final int max = cal.getActualMaximum( field );
      
      // set the field to the end.
      cal.set( field, max );
      
      // if already at the end
      if ( ref == max )
      {
         // we roll to the next
         cal.roll( field, true );
      }
   }
   


   // $ANTLR start "parseDate"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:194:1:
   // parseDate[Calendar cal, boolean clearTime] returns [boolean eof] : ( (
   // date_full[$cal] | date_on[$cal] |
   // date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) |
   // EOF );
   public final boolean parseDate( Calendar cal, boolean clearTime ) throws RecognitionException
   {
      boolean eof = false;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:198:4:
         // ( (
         // date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] |
         // date_end_of_the_MW[$cal] | date_natural[$cal] ) |
         // EOF )
         int alt2 = 2;
         int LA2_0 = input.LA( 1 );
         
         if ( ( ( LA2_0 >= TODAY && LA2_0 <= YESTERDAY )
            || ( LA2_0 >= ON && LA2_0 <= IN ) || LA2_0 == NEXT || LA2_0 == END
            || LA2_0 == TONIGHT || ( LA2_0 >= MONTH && LA2_0 <= WEEKDAY )
            || LA2_0 == INT || LA2_0 == NUM_STR ) )
         {
            alt2 = 1;
         }
         else if ( ( LA2_0 == EOF ) )
         {
            alt2 = 2;
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  2,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt2 )
         {
            case 1:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:198:6:
               // (
               // date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] |
               // date_end_of_the_MW[$cal] | date_natural[$cal]
               // )
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:198:6:
               // (
               // date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] |
               // date_end_of_the_MW[$cal] | date_natural[$cal]
               // )
               int alt1 = 5;
               switch ( input.LA( 1 ) )
               {
                  case INT:
                  {
                     switch ( input.LA( 2 ) )
                     {
                        case DOT:
                        case MINUS:
                        {
                           int LA1_6 = input.LA( 3 );
                           
                           if ( ( LA1_6 == INT ) )
                           {
                              alt1 = 1;
                           }
                           else if ( ( LA1_6 == MONTH ) )
                           {
                              alt1 = 2;
                           }
                           else
                           {
                              NoViableAltException nvae = new NoViableAltException( "",
                                                                                    1,
                                                                                    6,
                                                                                    input );
                              
                              throw nvae;
                           }
                        }
                           break;
                        case EOF:
                        case OF:
                        case STs:
                        case MONTH:
                        case MINUS_A:
                        case COMMA:
                        {
                           alt1 = 2;
                        }
                           break;
                        case DATE_SEP:
                        case COLON:
                        {
                           alt1 = 1;
                        }
                           break;
                        case YEARS:
                        case MONTHS:
                        case WEEKS:
                        case DAYS:
                        {
                           alt1 = 3;
                        }
                           break;
                        default :
                           NoViableAltException nvae = new NoViableAltException( "",
                                                                                 1,
                                                                                 1,
                                                                                 input );
                           
                           throw nvae;
                     }
                     
                  }
                     break;
                  case ON:
                  case NEXT:
                  case MONTH:
                  case WEEKDAY:
                  {
                     alt1 = 2;
                  }
                     break;
                  case IN:
                  case NUM_STR:
                  {
                     alt1 = 3;
                  }
                     break;
                  case END:
                  {
                     alt1 = 4;
                  }
                     break;
                  case TODAY:
                  case TOMORROW:
                  case YESTERDAY:
                  case TONIGHT:
                  {
                     alt1 = 5;
                  }
                     break;
                  default :
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           1,
                                                                           0,
                                                                           input );
                     
                     throw nvae;
               }
               
               switch ( alt1 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:198:10:
                     // date_full[$cal]
                  {
                     pushFollow( FOLLOW_date_full_in_parseDate64 );
                     date_full( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 2:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:199:10:
                     // date_on[$cal]
                  {
                     pushFollow( FOLLOW_date_on_in_parseDate85 );
                     date_on( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 3:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:200:10:
                     // date_in_X_YMWD[$cal]
                  {
                     pushFollow( FOLLOW_date_in_X_YMWD_in_parseDate108 );
                     date_in_X_YMWD( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 4:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:201:10:
                     // date_end_of_the_MW[$cal]
                  {
                     pushFollow( FOLLOW_date_end_of_the_MW_in_parseDate124 );
                     date_end_of_the_MW( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 5:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:202:10:
                     // date_natural[$cal]
                  {
                     pushFollow( FOLLOW_date_natural_in_parseDate136 );
                     date_natural( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  
               }
               
               if ( clearTime )
               {
                  cal.clear( Calendar.HOUR );
                  cal.clear( Calendar.HOUR_OF_DAY );
                  cal.clear( Calendar.MINUTE );
                  cal.clear( Calendar.SECOND );
                  cal.clear( Calendar.MILLISECOND );
               }
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:216:6:
               // EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseDate166 );
               
               eof = true;
               
            }
               break;
            
         }
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return eof;
   }
   
   
   // $ANTLR end "parseDate"
   
   public static class parseDateWithin_return extends ParserRuleReturnScope
   {
      public Calendar epochStart;
      
      public Calendar epochEnd;
   };
   
   

   // $ANTLR start "parseDateWithin"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:226:1:
   // parseDateWithin[boolean past] returns [Calendar epochStart, Calendar
   // epochEnd] : (a= INT | n= NUM_STR | A )? (
   // DAYS | WEEKS | MONTHS | YEARS ) ( OF parseDate[retval.epochStart, false]
   // )? ;
   public final DateParser.parseDateWithin_return parseDateWithin( boolean past ) throws RecognitionException
   {
      DateParser.parseDateWithin_return retval = new DateParser.parseDateWithin_return();
      retval.start = input.LT( 1 );
      
      Token a = null;
      Token n = null;
      
      retval.epochStart = getLocalizedCalendar();
      int amount = 1;
      int unit = -1;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:250:2:
         // ( (a=
         // INT | n= NUM_STR | A )? ( DAYS | WEEKS | MONTHS | YEARS ) ( OF
         // parseDate[retval.epochStart, false] )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:250:4:
         // (a= INT
         // | n= NUM_STR | A )? ( DAYS | WEEKS | MONTHS | YEARS ) ( OF
         // parseDate[retval.epochStart, false] )?
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:250:4:
            // (a=
            // INT | n= NUM_STR | A )?
            int alt3 = 4;
            switch ( input.LA( 1 ) )
            {
               case INT:
               {
                  alt3 = 1;
               }
                  break;
               case NUM_STR:
               {
                  alt3 = 2;
               }
                  break;
               case A:
               {
                  alt3 = 3;
               }
                  break;
            }
            
            switch ( alt3 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:250:7:
                  // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_parseDateWithin223 );
                  
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:254:7:
                  // n= NUM_STR
               {
                  n = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_parseDateWithin241 );
                  
                  amount = strToNumber( ( n != null ? n.getText() : null ) );
                  
               }
                  break;
               case 3:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:258:7:
                  // A
               {
                  match( input, A, FOLLOW_A_in_parseDateWithin257 );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:259:5:
            // (
            // DAYS | WEEKS | MONTHS | YEARS )
            int alt4 = 4;
            switch ( input.LA( 1 ) )
            {
               case DAYS:
               {
                  alt4 = 1;
               }
                  break;
               case WEEKS:
               {
                  alt4 = 2;
               }
                  break;
               case MONTHS:
               {
                  alt4 = 3;
               }
                  break;
               case YEARS:
               {
                  alt4 = 4;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        4,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt4 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:259:8:
                  // DAYS
               {
                  match( input, DAYS, FOLLOW_DAYS_in_parseDateWithin268 );
                  
                  unit = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:263:8:
                  // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_parseDateWithin284 );
                  
                  unit = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 3:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:267:8:
                  // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_parseDateWithin302 );
                  
                  unit = Calendar.MONTH;
                  
               }
                  break;
               case 4:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:271:8:
                  // YEARS
               {
                  match( input, YEARS, FOLLOW_YEARS_in_parseDateWithin320 );
                  
                  unit = Calendar.YEAR;
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:276:5:
            // ( OF
            // parseDate[retval.epochStart, false] )?
            int alt5 = 2;
            int LA5_0 = input.LA( 1 );
            
            if ( ( LA5_0 == OF ) )
            {
               alt5 = 1;
            }
            switch ( alt5 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:276:6:
                  // OF parseDate[retval.epochStart, false]
               {
                  match( input, OF, FOLLOW_OF_in_parseDateWithin346 );
                  pushFollow( FOLLOW_parseDate_in_parseDateWithin348 );
                  parseDate( retval.epochStart, false );
                  
                  state._fsp--;
                  
               }
                  break;
               
            }
            
         }
         
         retval.stop = input.LT( -1 );
         
         retval.epochEnd = getLocalizedCalendar();
         retval.epochEnd.setTimeInMillis( retval.epochStart.getTimeInMillis() );
         retval.epochEnd.add( unit, past ? -amount : amount );
         
         retval.epochStart.clear( Calendar.HOUR );
         retval.epochStart.clear( Calendar.HOUR_OF_DAY );
         retval.epochStart.clear( Calendar.MINUTE );
         retval.epochStart.clear( Calendar.SECOND );
         retval.epochStart.clear( Calendar.MILLISECOND );
         retval.epochEnd.clear( Calendar.HOUR );
         retval.epochEnd.clear( Calendar.HOUR_OF_DAY );
         retval.epochEnd.clear( Calendar.MINUTE );
         retval.epochEnd.clear( Calendar.SECOND );
         retval.epochEnd.clear( Calendar.MILLISECOND );
         
      }
      catch ( NumberFormatException e )
      {
         
         throw new RecognitionException();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return retval;
   }
   


   // $ANTLR end "parseDateWithin"
   
   // $ANTLR start "date_full"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:287:1:
   // date_full[Calendar cal] : pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) m=
   // INT ( DOT | MINUS | COLON | DATE_SEP )
   // pt3= INT ;
   public final void date_full( Calendar cal ) throws RecognitionException
   {
      Token pt1 = null;
      Token m = null;
      Token pt3 = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:288:4:
         // (pt1=
         // INT ( DOT | MINUS | COLON | DATE_SEP ) m= INT ( DOT | MINUS | COLON
         // | DATE_SEP ) pt3= INT )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:288:6:
         // pt1= INT
         // ( DOT | MINUS | COLON | DATE_SEP ) m= INT ( DOT | MINUS | COLON |
         // DATE_SEP ) pt3= INT
         {
            pt1 = (Token) match( input, INT, FOLLOW_INT_in_date_full388 );
            if ( ( input.LA( 1 ) >= DATE_SEP && input.LA( 1 ) <= MINUS ) )
            {
               input.consume();
               state.errorRecovery = false;
            }
            else
            {
               MismatchedSetException mse = new MismatchedSetException( null,
                                                                        input );
               throw mse;
            }
            
            m = (Token) match( input, INT, FOLLOW_INT_in_date_full415 );
            if ( ( input.LA( 1 ) >= DATE_SEP && input.LA( 1 ) <= MINUS ) )
            {
               input.consume();
               state.errorRecovery = false;
            }
            else
            {
               MismatchedSetException mse = new MismatchedSetException( null,
                                                                        input );
               throw mse;
            }
            
            pt3 = (Token) match( input, INT, FOLLOW_INT_in_date_full440 );
            
            // year first
            if ( pt1.getText().length() > 2 )
            {
               parseFullDate( cal,
                              m.getText(),
                              pt3.getText(),
                              pt1.getText(),
                              false );
            }
            
            // year last
            else
            {
               parseFullDate( cal,
                              pt1.getText(),
                              m.getText(),
                              pt3.getText(),
                              false );
            }
            
         }
         
      }
      catch ( RecognitionException re )
      {
         reportError( re );
         recover( input, re );
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_full"
   
   // $ANTLR start "date_on"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:314:1:
   // date_on[Calendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] |
   // date_on_M_Xst[$cal] | date_on_weekday[$cal] ) ;
   public final void date_on( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:315:4:
         // ( ( ON
         // )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] |
         // date_on_weekday[$cal] ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:315:6:
         // ( ON )?
         // ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] |
         // date_on_weekday[$cal] )
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:315:6:
            // ( ON
            // )?
            int alt6 = 2;
            int LA6_0 = input.LA( 1 );
            
            if ( ( LA6_0 == ON ) )
            {
               alt6 = 1;
            }
            switch ( alt6 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:315:6:
                  // ON
               {
                  match( input, ON, FOLLOW_ON_in_date_on467 );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:315:10:
            // (
            // date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] |
            // date_on_weekday[$cal] )
            int alt7 = 3;
            switch ( input.LA( 1 ) )
            {
               case INT:
               {
                  alt7 = 1;
               }
                  break;
               case MONTH:
               {
                  alt7 = 2;
               }
                  break;
               case NEXT:
               case WEEKDAY:
               {
                  alt7 = 3;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        7,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt7 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:315:14:
                  // date_on_Xst_of_M[$cal]
               {
                  pushFollow( FOLLOW_date_on_Xst_of_M_in_date_on474 );
                  date_on_Xst_of_M( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:316:14:
                  // date_on_M_Xst[$cal]
               {
                  pushFollow( FOLLOW_date_on_M_Xst_in_date_on490 );
                  date_on_M_Xst( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 3:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:317:14:
                  // date_on_weekday[$cal]
               {
                  pushFollow( FOLLOW_date_on_weekday_in_date_on509 );
                  date_on_weekday( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               
            }
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_on"
   
   // $ANTLR start "date_on_Xst_of_M"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:324:1:
   // date_on_Xst_of_M[Calendar cal] : d= INT ( STs )? ( ( OF | MINUS_A | MINUS
   // | COMMA | DOT )? m= MONTH ( MINUS | DOT
   // )? (y= INT )? )? ;
   public final void date_on_Xst_of_M( Calendar cal ) throws RecognitionException
   {
      Token d = null;
      Token m = null;
      Token y = null;
      
      final Calendar now = getLocalizedCalendar();
      boolean hasMonth = false;
      boolean hasYear = false;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:331:4:
         // (d= INT
         // ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS
         // | DOT )? (y= INT )? )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:331:6:
         // d= INT (
         // STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS |
         // DOT )? (y= INT )? )?
         {
            d = (Token) match( input, INT, FOLLOW_INT_in_date_on_Xst_of_M550 );
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:331:12:
            // (
            // STs )?
            int alt8 = 2;
            int LA8_0 = input.LA( 1 );
            
            if ( ( LA8_0 == STs ) )
            {
               alt8 = 1;
            }
            switch ( alt8 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:331:12:
                  // STs
               {
                  match( input, STs, FOLLOW_STs_in_date_on_Xst_of_M552 );
                  
               }
                  break;
               
            }
            
            cal.set( Calendar.DAY_OF_MONTH,
                     Integer.parseInt( ( d != null ? d.getText() : null ) ) );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:335:6:
            // ( (
            // OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )?
            // (y= INT )? )?
            int alt12 = 2;
            int LA12_0 = input.LA( 1 );
            
            if ( ( LA12_0 == OF || LA12_0 == MONTH || LA12_0 == DOT || ( LA12_0 >= MINUS && LA12_0 <= COMMA ) ) )
            {
               alt12 = 1;
            }
            switch ( alt12 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:335:7:
                  // ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS |
                  // DOT )? (y= INT )?
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:335:7:
                  // ( OF | MINUS_A | MINUS | COMMA | DOT )?
                  int alt9 = 2;
                  int LA9_0 = input.LA( 1 );
                  
                  if ( ( LA9_0 == OF || LA9_0 == DOT || ( LA9_0 >= MINUS && LA9_0 <= COMMA ) ) )
                  {
                     alt9 = 1;
                  }
                  switch ( alt9 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
                     {
                        if ( input.LA( 1 ) == OF
                           || input.LA( 1 ) == DOT
                           || ( input.LA( 1 ) >= MINUS && input.LA( 1 ) <= COMMA ) )
                        {
                           input.consume();
                           state.errorRecovery = false;
                        }
                        else
                        {
                           MismatchedSetException mse = new MismatchedSetException( null,
                                                                                    input );
                           throw mse;
                        }
                        
                     }
                        break;
                     
                  }
                  
                  m = (Token) match( input,
                                     MONTH,
                                     FOLLOW_MONTH_in_date_on_Xst_of_M600 );
                  
                  parseTextMonth( cal, ( m != null ? m.getText() : null ) );
                  hasMonth = true;
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:341:7:
                  // ( MINUS | DOT )?
                  int alt10 = 2;
                  int LA10_0 = input.LA( 1 );
                  
                  if ( ( LA10_0 == DOT || LA10_0 == MINUS ) )
                  {
                     alt10 = 1;
                  }
                  switch ( alt10 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
                     {
                        if ( input.LA( 1 ) == DOT || input.LA( 1 ) == MINUS )
                        {
                           input.consume();
                           state.errorRecovery = false;
                        }
                        else
                        {
                           MismatchedSetException mse = new MismatchedSetException( null,
                                                                                    input );
                           throw mse;
                        }
                        
                     }
                        break;
                     
                  }
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:342:7:
                  // (y= INT )?
                  int alt11 = 2;
                  int LA11_0 = input.LA( 1 );
                  
                  if ( ( LA11_0 == INT ) )
                  {
                     alt11 = 1;
                  }
                  switch ( alt11 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:342:8:
                        // y= INT
                     {
                        y = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_date_on_Xst_of_M637 );
                        
                        parseYear( cal, ( y != null ? y.getText() : null ) );
                        hasYear = true;
                        
                     }
                        break;
                     
                  }
                  
               }
                  break;
               
            }
            
            // if we have a year we have a full qualified date.
            // so we change nothing.
            if ( !hasYear && cal.before( now ) )
            {
               // if we have a month, we roll to next year.
               if ( hasMonth )
                  cal.roll( Calendar.YEAR, true );
               // if we only have a day, we roll to next month.
               else
                  cal.roll( Calendar.MONTH, true );
            }
            
         }
         
      }
      catch ( NumberFormatException e )
      {
         
         throw new RecognitionException();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_on_Xst_of_M"
   
   // $ANTLR start "date_on_M_Xst"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:370:1:
   // date_on_M_Xst[Calendar cal] : m= MONTH ( MINUS | COMMA | DOT )? (d= INT (
   // STs | MINUS_A | MINUS | COMMA | DOT )+
   // )? (y= INT )? ;
   public final void date_on_M_Xst( Calendar cal ) throws RecognitionException
   {
      Token m = null;
      Token d = null;
      Token y = null;
      
      boolean hasYear = false;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:375:4:
         // (m=
         // MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS |
         // COMMA | DOT )+ )? (y= INT )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:375:6:
         // m= MONTH
         // ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA |
         // DOT )+ )? (y= INT )?
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_date_on_M_Xst707 );
            
            parseTextMonth( cal, ( m != null ? m.getText() : null ) );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:379:5:
            // (
            // MINUS | COMMA | DOT )?
            int alt13 = 2;
            int LA13_0 = input.LA( 1 );
            
            if ( ( LA13_0 == DOT || LA13_0 == MINUS || LA13_0 == COMMA ) )
            {
               alt13 = 1;
            }
            switch ( alt13 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
               {
                  if ( input.LA( 1 ) == DOT || input.LA( 1 ) == MINUS
                     || input.LA( 1 ) == COMMA )
                  {
                     input.consume();
                     state.errorRecovery = false;
                  }
                  else
                  {
                     MismatchedSetException mse = new MismatchedSetException( null,
                                                                              input );
                     throw mse;
                  }
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:380:5:
            // (d=
            // INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )?
            int alt15 = 2;
            int LA15_0 = input.LA( 1 );
            
            if ( ( LA15_0 == INT ) )
            {
               int LA15_1 = input.LA( 2 );
               
               if ( ( LA15_1 == STs || LA15_1 == DOT || ( LA15_1 >= MINUS && LA15_1 <= COMMA ) ) )
               {
                  alt15 = 1;
               }
            }
            switch ( alt15 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:380:6:
                  // d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+
               {
                  d = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst742 );
                  
                  cal.set( Calendar.DAY_OF_MONTH,
                           Integer.parseInt( ( d != null ? d.getText() : null ) ) );
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:384:8:
                  // ( STs | MINUS_A | MINUS | COMMA | DOT )+
                  int cnt14 = 0;
                  loop14: do
                  {
                     int alt14 = 2;
                     int LA14_0 = input.LA( 1 );
                     
                     if ( ( LA14_0 == STs || LA14_0 == DOT || ( LA14_0 >= MINUS && LA14_0 <= COMMA ) ) )
                     {
                        alt14 = 1;
                     }
                     
                     switch ( alt14 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
                        {
                           if ( input.LA( 1 ) == STs
                              || input.LA( 1 ) == DOT
                              || ( input.LA( 1 ) >= MINUS && input.LA( 1 ) <= COMMA ) )
                           {
                              input.consume();
                              state.errorRecovery = false;
                           }
                           else
                           {
                              MismatchedSetException mse = new MismatchedSetException( null,
                                                                                       input );
                              throw mse;
                           }
                           
                        }
                           break;
                        
                        default :
                           if ( cnt14 >= 1 )
                              break loop14;
                           EarlyExitException eee = new EarlyExitException( 14,
                                                                            input );
                           throw eee;
                     }
                     cnt14++;
                  }
                  while ( true );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:385:5:
            // (y=
            // INT )?
            int alt16 = 2;
            int LA16_0 = input.LA( 1 );
            
            if ( ( LA16_0 == INT ) )
            {
               alt16 = 1;
            }
            switch ( alt16 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:385:6:
                  // y= INT
               {
                  y = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst790 );
                  
                  parseYear( cal, ( y != null ? y.getText() : null ) );
                  hasYear = true;
                  
               }
                  break;
               
            }
            
            // if we have a year we have a full qualified date.
            // so we change nothing.
            if ( !hasYear && getLocalizedCalendar().after( cal ) )
               // If the date is before now we roll the year
               cal.roll( Calendar.YEAR, true );
            
         }
         
      }
      catch ( NumberFormatException e )
      {
         
         throw new RecognitionException();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_on_M_Xst"
   
   // $ANTLR start "date_on_weekday"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:407:1:
   // date_on_weekday[Calendar cal] : ( NEXT )? wd= WEEKDAY ;
   public final void date_on_weekday( Calendar cal ) throws RecognitionException
   {
      Token wd = null;
      
      boolean nextWeek = false;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:412:4:
         // ( ( NEXT
         // )? wd= WEEKDAY )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:412:6:
         // ( NEXT
         // )? wd= WEEKDAY
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:412:6:
            // (
            // NEXT )?
            int alt17 = 2;
            int LA17_0 = input.LA( 1 );
            
            if ( ( LA17_0 == NEXT ) )
            {
               alt17 = 1;
            }
            switch ( alt17 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:412:7:
                  // NEXT
               {
                  match( input, NEXT, FOLLOW_NEXT_in_date_on_weekday859 );
                  nextWeek = true;
                  
               }
                  break;
               
            }
            
            wd = (Token) match( input,
                                WEEKDAY,
                                FOLLOW_WEEKDAY_in_date_on_weekday867 );
            
            final SimpleDateFormat sdf = new SimpleDateFormat( "EE", LOCALE );
            sdf.parse( ( wd != null ? wd.getText() : null ) );
            
            final int parsedWeekDay = sdf.getCalendar()
                                         .get( Calendar.DAY_OF_WEEK );
            final int currentWeekDay = cal.get( Calendar.DAY_OF_WEEK );
            
            cal.set( Calendar.DAY_OF_WEEK, parsedWeekDay );
            
            // If the weekday is before today or today, we adjust to next week.
            if ( parsedWeekDay <= currentWeekDay )
               cal.roll( Calendar.WEEK_OF_YEAR, true );
            
            // if the next week is explicitly enforced
            if ( nextWeek )
               cal.roll( Calendar.WEEK_OF_YEAR, true );
            
         }
         
      }
      catch ( ParseException pe )
      {
         
         throw new RecognitionException();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_on_weekday"
   
   // $ANTLR start "date_in_X_YMWD"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:440:1:
   // date_in_X_YMWD[Calendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( (
   // AND | COMMA )
   // date_in_X_YMWD_distance[$cal] )* ;
   public final void date_in_X_YMWD( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:441:4:
         // ( ( IN
         // )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA )
         // date_in_X_YMWD_distance[$cal] )* )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:441:7:
         // ( IN )?
         // date_in_X_YMWD_distance[$cal] ( ( AND | COMMA )
         // date_in_X_YMWD_distance[$cal] )*
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:441:7:
            // ( IN
            // )?
            int alt18 = 2;
            int LA18_0 = input.LA( 1 );
            
            if ( ( LA18_0 == IN ) )
            {
               alt18 = 1;
            }
            switch ( alt18 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:441:7:
                  // IN
               {
                  match( input, IN, FOLLOW_IN_in_date_in_X_YMWD913 );
                  
               }
                  break;
               
            }
            
            pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD928 );
            date_in_X_YMWD_distance( cal );
            
            state._fsp--;
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:442:7:
            // ( (
            // AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop19: do
            {
               int alt19 = 2;
               int LA19_0 = input.LA( 1 );
               
               if ( ( LA19_0 == AND || LA19_0 == COMMA ) )
               {
                  alt19 = 1;
               }
               
               switch ( alt19 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:442:8:
                     // ( AND | COMMA ) date_in_X_YMWD_distance[$cal]
                  {
                     if ( input.LA( 1 ) == AND || input.LA( 1 ) == COMMA )
                     {
                        input.consume();
                        state.errorRecovery = false;
                     }
                     else
                     {
                        MismatchedSetException mse = new MismatchedSetException( null,
                                                                                 input );
                        throw mse;
                     }
                     
                     pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD947 );
                     date_in_X_YMWD_distance( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  
                  default :
                     break loop19;
               }
            }
            while ( true );
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_in_X_YMWD"
   
   // $ANTLR start "date_in_X_YMWD_distance"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:449:1:
   // date_in_X_YMWD_distance[Calendar cal] : (a= NUM_STR | a= INT ) ( YEARS |
   // MONTHS | WEEKS | DAYS ) ;
   public final void date_in_X_YMWD_distance( Calendar cal ) throws RecognitionException
   {
      Token a = null;
      
      int amount = -1;
      int calField = Calendar.YEAR;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:455:4:
         // ( (a=
         // NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:455:6:
         // (a=
         // NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:455:6:
            // (a=
            // NUM_STR | a= INT )
            int alt20 = 2;
            int LA20_0 = input.LA( 1 );
            
            if ( ( LA20_0 == NUM_STR ) )
            {
               alt20 = 1;
            }
            else if ( ( LA20_0 == INT ) )
            {
               alt20 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     20,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt20 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:455:10:
                  // a= NUM_STR
               {
                  a = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_date_in_X_YMWD_distance996 );
                  amount = strToNumber( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:456:10:
                  // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_date_in_X_YMWD_distance1011 );
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:457:6:
            // (
            // YEARS | MONTHS | WEEKS | DAYS )
            int alt21 = 4;
            switch ( input.LA( 1 ) )
            {
               case YEARS:
               {
                  alt21 = 1;
               }
                  break;
               case MONTHS:
               {
                  alt21 = 2;
               }
                  break;
               case WEEKS:
               {
                  alt21 = 3;
               }
                  break;
               case DAYS:
               {
                  alt21 = 4;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        21,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt21 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:457:12:
                  // YEARS
               {
                  match( input,
                         YEARS,
                         FOLLOW_YEARS_in_date_in_X_YMWD_distance1031 );
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:458:12:
                  // MONTHS
               {
                  match( input,
                         MONTHS,
                         FOLLOW_MONTHS_in_date_in_X_YMWD_distance1044 );
                  calField = Calendar.MONTH;
                  
               }
                  break;
               case 3:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:459:12:
                  // WEEKS
               {
                  match( input,
                         WEEKS,
                         FOLLOW_WEEKS_in_date_in_X_YMWD_distance1060 );
                  calField = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 4:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:460:12:
                  // DAYS
               {
                  match( input,
                         DAYS,
                         FOLLOW_DAYS_in_date_in_X_YMWD_distance1077 );
                  calField = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               
            }
            
            if ( amount != -1 )
            {
               cal.roll( calField, amount );
            }
            else
            {
               throw new RecognitionException();
            }
            
         }
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_in_X_YMWD_distance"
   
   // $ANTLR start "date_end_of_the_MW"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:481:1:
   // date_end_of_the_MW[Calendar cal] : END ( OF )? ( THE )? ( WEEKS | MONTHS )
   // ;
   public final void date_end_of_the_MW( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:482:4:
         // ( END (
         // OF )? ( THE )? ( WEEKS | MONTHS ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:482:6:
         // END ( OF
         // )? ( THE )? ( WEEKS | MONTHS )
         {
            match( input, END, FOLLOW_END_in_date_end_of_the_MW1128 );
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:482:10:
            // ( OF
            // )?
            int alt22 = 2;
            int LA22_0 = input.LA( 1 );
            
            if ( ( LA22_0 == OF ) )
            {
               alt22 = 1;
            }
            switch ( alt22 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:482:10:
                  // OF
               {
                  match( input, OF, FOLLOW_OF_in_date_end_of_the_MW1130 );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:482:14:
            // (
            // THE )?
            int alt23 = 2;
            int LA23_0 = input.LA( 1 );
            
            if ( ( LA23_0 == THE ) )
            {
               alt23 = 1;
            }
            switch ( alt23 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:482:14:
                  // THE
               {
                  match( input, THE, FOLLOW_THE_in_date_end_of_the_MW1133 );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:483:6:
            // (
            // WEEKS | MONTHS )
            int alt24 = 2;
            int LA24_0 = input.LA( 1 );
            
            if ( ( LA24_0 == WEEKS ) )
            {
               alt24 = 1;
            }
            else if ( ( LA24_0 == MONTHS ) )
            {
               alt24 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     24,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt24 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:483:10:
                  // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_date_end_of_the_MW1145 );
                  
                  rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:487:10:
                  // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_date_end_of_the_MW1167 );
                  
                  rollToEndOf( Calendar.DAY_OF_MONTH, cal );
                  
               }
                  break;
               
            }
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_end_of_the_MW"
   
   // $ANTLR start "date_natural"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:497:1:
   // date_natural[Calendar cal] : ( ( TODAY | TONIGHT ) | TOMORROW | YESTERDAY
   // );
   public final void date_natural( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:498:4:
         // ( (
         // TODAY | TONIGHT ) | TOMORROW | YESTERDAY )
         int alt25 = 3;
         switch ( input.LA( 1 ) )
         {
            case TODAY:
            case TONIGHT:
            {
               alt25 = 1;
            }
               break;
            case TOMORROW:
            {
               alt25 = 2;
            }
               break;
            case YESTERDAY:
            {
               alt25 = 3;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     25,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt25 )
         {
            case 1:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:498:6:
               // (
               // TODAY | TONIGHT )
            {
               if ( input.LA( 1 ) == TODAY || input.LA( 1 ) == TONIGHT )
               {
                  input.consume();
                  state.errorRecovery = false;
               }
               else
               {
                  MismatchedSetException mse = new MismatchedSetException( null,
                                                                           input );
                  throw mse;
               }
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:501:6:
               // TOMORROW
            {
               match( input, TOMORROW, FOLLOW_TOMORROW_in_date_natural1228 );
               
               cal.roll( Calendar.DAY_OF_YEAR, true );
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:505:6:
               // YESTERDAY
            {
               match( input, YESTERDAY, FOLLOW_YESTERDAY_in_date_natural1242 );
               
               cal.roll( Calendar.DAY_OF_YEAR, false );
               
            }
               break;
            
         }
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return;
   }
   
   // $ANTLR end "date_natural"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_date_full_in_parseDate64 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_in_parseDate85 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_in_parseDate108 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_end_of_the_MW_in_parseDate124 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_natural_in_parseDate136 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_EOF_in_parseDate166 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseDateWithin223 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_parseDateWithin241 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_A_in_parseDateWithin257 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseDateWithin268 = new BitSet( new long[]
   { 0x0000000000000802L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseDateWithin284 = new BitSet( new long[]
   { 0x0000000000000802L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseDateWithin302 = new BitSet( new long[]
   { 0x0000000000000802L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseDateWithin320 = new BitSet( new long[]
   { 0x0000000000000802L } );
   
   public static final BitSet FOLLOW_OF_in_parseDateWithin346 = new BitSet( new long[]
   { 0x00000220600456E0L } );
   
   public static final BitSet FOLLOW_parseDate_in_parseDateWithin348 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_full388 = new BitSet( new long[]
   { 0x0000000780000000L } );
   
   public static final BitSet FOLLOW_set_in_date_full390 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_date_full415 = new BitSet( new long[]
   { 0x0000000780000000L } );
   
   public static final BitSet FOLLOW_set_in_date_full417 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_date_full440 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_ON_in_date_on467 = new BitSet( new long[]
   { 0x0000002060001200L } );
   
   public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on474 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_M_Xst_in_date_on490 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_weekday_in_date_on509 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M550 = new BitSet( new long[]
   { 0x0000001D20010802L } );
   
   public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M552 = new BitSet( new long[]
   { 0x0000001D20000802L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M570 = new BitSet( new long[]
   { 0x0000000020000000L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M600 = new BitSet( new long[]
   { 0x0000002500000002L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M619 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M637 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst707 = new BitSet( new long[]
   { 0x0000003500000002L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst722 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst742 = new BitSet( new long[]
   { 0x0000001D00010000L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst760 = new BitSet( new long[]
   { 0x0000003D00010002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst790 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEXT_in_date_on_weekday859 = new BitSet( new long[]
   { 0x0000000040000000L } );
   
   public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday867 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_IN_in_date_in_X_YMWD913 = new BitSet( new long[]
   { 0x0000022000000400L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD928 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_set_in_date_in_X_YMWD938 = new BitSet( new long[]
   { 0x0000022000000400L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD947 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance996 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance1011 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance1031 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance1044 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance1060 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1077 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_END_in_date_end_of_the_MW1128 = new BitSet( new long[]
   { 0x0000000001808800L } );
   
   public static final BitSet FOLLOW_OF_in_date_end_of_the_MW1130 = new BitSet( new long[]
   { 0x0000000001808000L } );
   
   public static final BitSet FOLLOW_THE_in_date_end_of_the_MW1133 = new BitSet( new long[]
   { 0x0000000001800000L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1145 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1167 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_date_natural1208 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TOMORROW_in_date_natural1228 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_YESTERDAY_in_date_natural1242 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
