// $ANTLR 3.3 Nov 30, 2010 12:45:30 F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g 2011-03-11 14:18:38

package dev.drsoran.moloko.grammar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

import dev.drsoran.moloko.grammar.lang.NumberLookupLanguage;


public class DateParser extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEVER", "TODAY", "TOMORROW",
    "YESTERDAY", "AT", "ON", "IN", "OF", "NEXT", "AND", "END", "THE", "STs",
    "NOW", "TONIGHT", "MIDNIGHT", "MIDDAY", "NOON", "YEARS", "MONTHS", "WEEKS",
    "DAYS", "HOURS", "MINUTES", "SECONDS", "MONTH", "WEEKDAY", "DATE_SEP",
    "DOT", "COLON", "MINUS", "MINUS_A", "COMMA", "INT", "A", "AM", "PM",
    "NUM_STR", "STRING", "WS" };
   
   public static final int EOF = -1;
   
   public static final int NEVER = 4;
   
   public static final int TODAY = 5;
   
   public static final int TOMORROW = 6;
   
   public static final int YESTERDAY = 7;
   
   public static final int AT = 8;
   
   public static final int ON = 9;
   
   public static final int IN = 10;
   
   public static final int OF = 11;
   
   public static final int NEXT = 12;
   
   public static final int AND = 13;
   
   public static final int END = 14;
   
   public static final int THE = 15;
   
   public static final int STs = 16;
   
   public static final int NOW = 17;
   
   public static final int TONIGHT = 18;
   
   public static final int MIDNIGHT = 19;
   
   public static final int MIDDAY = 20;
   
   public static final int NOON = 21;
   
   public static final int YEARS = 22;
   
   public static final int MONTHS = 23;
   
   public static final int WEEKS = 24;
   
   public static final int DAYS = 25;
   
   public static final int HOURS = 26;
   
   public static final int MINUTES = 27;
   
   public static final int SECONDS = 28;
   
   public static final int MONTH = 29;
   
   public static final int WEEKDAY = 30;
   
   public static final int DATE_SEP = 31;
   
   public static final int DOT = 32;
   
   public static final int COLON = 33;
   
   public static final int MINUS = 34;
   
   public static final int MINUS_A = 35;
   
   public static final int COMMA = 36;
   
   public static final int INT = 37;
   
   public static final int A = 38;
   
   public static final int AM = 39;
   
   public static final int PM = 40;
   
   public static final int NUM_STR = 41;
   
   public static final int STRING = 42;
   
   public static final int WS = 43;
   
   

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
   


   @Override
   public String[] getTokenNames()
   {
      return DateParser.tokenNames;
   }
   


   @Override
   public String getGrammarFileName()
   {
      return "F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g";
   }
   
   private NumberLookupLanguage numberLookUp;
   
   

   public DateParser()
   {
      super( null );
   }
   


   public void setNumberLookUp( NumberLookupLanguage numberLookUp )
   {
      this.numberLookUp = numberLookUp;
   }
   


   public final static Calendar getCalendar()
   {
      final Calendar cal = Calendar.getInstance();
      
      cal.clear( Calendar.HOUR );
      cal.clear( Calendar.HOUR_OF_DAY );
      cal.clear( Calendar.MINUTE );
      cal.clear( Calendar.SECOND );
      cal.clear( Calendar.MILLISECOND );
      
      return cal;
   }
   


   private final int strToNumber( String string )
   {
      int res = -1;
      
      final Integer val = numberLookUp.get( string );
      
      if ( val != null )
         res = val;
      
      return res;
   }
   


   private final int getMonthNumber( String month )
   {
      // Only take the 1st three chars of the month as key.
      return strToNumber( month.substring( 0, 3 ) );
   }
   


   private final int getWeekdayNumber( String weekday )
   {
      // Only take the 1st two chars of the weekday as key.
      return strToNumber( weekday.substring( 0, 2 ) );
   }
   


   private final void parseFullDate( Calendar cal,
                                     String day,
                                     String month,
                                     String year,
                                     boolean textMonth ) throws RecognitionException
   {
      try
      {
         final StringBuffer pattern = new StringBuffer( "dd.MM" );
         
         int monthNum = -1;
         
         if ( textMonth )
         {
            monthNum = getMonthNumber( month );
         }
         else
         {
            monthNum = Integer.parseInt( month );
         }
         
         if ( monthNum == -1 )
            throw new RecognitionException();
         
         if ( year != null )
            pattern.append( ".yyyy" );
         
         final SimpleDateFormat sdf = new SimpleDateFormat( pattern.toString() );
         
         sdf.parse( day + "." + monthNum
            + ( ( year != null ) ? "." + year : "" ) );
         
         final Calendar sdfCal = sdf.getCalendar();
         
         cal.set( Calendar.DAY_OF_MONTH, sdfCal.get( Calendar.DAY_OF_MONTH ) );
         cal.set( Calendar.MONTH, sdfCal.get( Calendar.MONTH ) );
         
         if ( year != null )
            cal.set( Calendar.YEAR, sdfCal.get( Calendar.YEAR ) );
      }
      catch ( NumberFormatException nfe )
      {
         throw new RecognitionException();
      }
      catch ( ParseException e )
      {
         throw new RecognitionException();
      }
   }
   


   private final void parseTextMonth( Calendar cal, String month ) throws RecognitionException
   {
      final int monthNum = getMonthNumber( month );
      
      if ( monthNum == -1 )
         throw new RecognitionException();
      
      cal.set( Calendar.MONTH, monthNum );
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
         cal.add( field, 1 );
      }
   }
   


   // $ANTLR start "parseDate"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:215:1: parseDate[Calendar cal, boolean
   // clearTime] returns [boolean eof] : ( ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] |
   // date_end_of_the_MW[$cal] | date_natural[$cal] ) | EOF );
   public final boolean parseDate( Calendar cal, boolean clearTime ) throws RecognitionException
   {
      boolean eof = false;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:219:4: ( ( date_full[$cal] |
         // date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | EOF )
         int alt2 = 2;
         int LA2_0 = input.LA( 1 );
         
         if ( ( ( LA2_0 >= NEVER && LA2_0 <= YESTERDAY )
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
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:219:6: ( date_full[$cal] |
               // date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
            {
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:219:6: ( date_full[$cal] |
               // date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
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
                  case NEVER:
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
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:219:10:
                     // date_full[$cal]
                  {
                     pushFollow( FOLLOW_date_full_in_parseDate64 );
                     date_full( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 2:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:220:10: date_on[$cal]
                  {
                     pushFollow( FOLLOW_date_on_in_parseDate85 );
                     date_on( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 3:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:221:10:
                     // date_in_X_YMWD[$cal]
                  {
                     pushFollow( FOLLOW_date_in_X_YMWD_in_parseDate108 );
                     date_in_X_YMWD( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 4:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:222:10:
                     // date_end_of_the_MW[$cal]
                  {
                     pushFollow( FOLLOW_date_end_of_the_MW_in_parseDate124 );
                     date_end_of_the_MW( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 5:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:223:10:
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
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:237:6: EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseDate168 );
               
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
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:247:1: parseDateWithin[boolean past]
   // returns [Calendar epochStart, Calendar epochEnd] : (a= INT | n= NUM_STR | A )? ( DAYS | WEEKS | MONTHS | YEARS ) (
   // OF parseDate[retval.epochStart, false] )? ;
   public final DateParser.parseDateWithin_return parseDateWithin( boolean past ) throws RecognitionException
   {
      DateParser.parseDateWithin_return retval = new DateParser.parseDateWithin_return();
      retval.start = input.LT( 1 );
      
      Token a = null;
      Token n = null;
      
      retval.epochStart = getCalendar();
      int amount = 1;
      int unit = -1;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:271:4: ( (a= INT | n= NUM_STR | A
         // )? ( DAYS | WEEKS | MONTHS | YEARS ) ( OF parseDate[retval.epochStart, false] )? )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:271:6: (a= INT | n= NUM_STR | A )?
         // ( DAYS | WEEKS | MONTHS | YEARS ) ( OF parseDate[retval.epochStart, false] )?
         {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:271:6: (a= INT | n= NUM_STR | A
            // )?
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
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:271:9: a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_parseDateWithin232 );
                  
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:275:9: n= NUM_STR
               {
                  n = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_parseDateWithin254 );
                  
                  amount = strToNumber( ( n != null ? n.getText() : null ) );
                  
               }
                  break;
               case 3:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:279:9: A
               {
                  match( input, A, FOLLOW_A_in_parseDateWithin274 );
                  
               }
                  break;
               
            }
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:280:7: ( DAYS | WEEKS | MONTHS
            // | YEARS )
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
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:280:10: DAYS
               {
                  match( input, DAYS, FOLLOW_DAYS_in_parseDateWithin287 );
                  
                  unit = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               case 2:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:284:10: WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_parseDateWithin309 );
                  
                  unit = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 3:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:288:10: MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_parseDateWithin331 );
                  
                  unit = Calendar.MONTH;
                  
               }
                  break;
               case 4:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:292:10: YEARS
               {
                  match( input, YEARS, FOLLOW_YEARS_in_parseDateWithin353 );
                  
                  unit = Calendar.YEAR;
                  
               }
                  break;
               
            }
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:297:7: ( OF
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
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:297:8: OF
                  // parseDate[retval.epochStart, false]
               {
                  match( input, OF, FOLLOW_OF_in_parseDateWithin381 );
                  pushFollow( FOLLOW_parseDate_in_parseDateWithin383 );
                  parseDate( retval.epochStart, false );
                  
                  state._fsp--;
                  
               }
                  break;
               
            }
            
         }
         
         retval.stop = input.LT( -1 );
         
         retval.epochEnd = getCalendar();
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
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:308:1: date_full[Calendar cal] : pt1=
   // INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )? ;
   public final void date_full( Calendar cal ) throws RecognitionException
   {
      Token pt1 = null;
      Token pt2 = null;
      Token pt3 = null;
      
      String pt1Str = null;
      String pt2Str = null;
      String pt3Str = null;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:315:4: (pt1= INT ( DOT | MINUS |
         // COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )? )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:315:6: pt1= INT ( DOT | MINUS |
         // COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )?
         {
            pt1 = (Token) match( input, INT, FOLLOW_INT_in_date_full440 );
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
            
            pt1Str = pt1.getText();
            
            pt2 = (Token) match( input, INT, FOLLOW_INT_in_date_full472 );
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
            
            pt2Str = pt2.getText();
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:323:6: (pt3= INT )?
            int alt6 = 2;
            int LA6_0 = input.LA( 1 );
            
            if ( ( LA6_0 == INT ) )
            {
               alt6 = 1;
            }
            switch ( alt6 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:324:9: pt3= INT
               {
                  pt3 = (Token) match( input, INT, FOLLOW_INT_in_date_full514 );
                  
                  pt3Str = pt3.getText();
                  
               }
                  break;
               
            }
            
            final boolean yearFirst = pt3Str != null && pt1Str.length() > 2;
            
            parseFullDate( cal, yearFirst ? pt2Str : pt1Str, // day
                           yearFirst ? pt3Str : pt2Str, // month
                           yearFirst ? pt1Str : pt3Str, // year
                           false );
            
            // if year is missing and the date is
            // befor now we roll to the next year.
            if ( pt3Str == null )
            {
               final Calendar now = getCalendar();
               
               if ( cal.before( now ) )
               {
                  cal.add( Calendar.YEAR, 1 );
               }
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
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:353:1: date_on[Calendar cal] : ( ON )? (
   // date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) ;
   public final void date_on( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:354:4: ( ( ON )? (
         // date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:354:6: ( ON )? (
         // date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
         {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:354:6: ( ON )?
            int alt7 = 2;
            int LA7_0 = input.LA( 1 );
            
            if ( ( LA7_0 == ON ) )
            {
               alt7 = 1;
            }
            switch ( alt7 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:354:6: ON
               {
                  match( input, ON, FOLLOW_ON_in_date_on558 );
                  
               }
                  break;
               
            }
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:354:10: (
            // date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
            int alt8 = 3;
            switch ( input.LA( 1 ) )
            {
               case INT:
               {
                  alt8 = 1;
               }
                  break;
               case MONTH:
               {
                  alt8 = 2;
               }
                  break;
               case NEXT:
               case WEEKDAY:
               {
                  alt8 = 3;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        8,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt8 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:354:14:
                  // date_on_Xst_of_M[$cal]
               {
                  pushFollow( FOLLOW_date_on_Xst_of_M_in_date_on565 );
                  date_on_Xst_of_M( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:355:14:
                  // date_on_M_Xst[$cal]
               {
                  pushFollow( FOLLOW_date_on_M_Xst_in_date_on581 );
                  date_on_M_Xst( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 3:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:356:14:
                  // date_on_weekday[$cal]
               {
                  pushFollow( FOLLOW_date_on_weekday_in_date_on600 );
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
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:363:1: date_on_Xst_of_M[Calendar cal] :
   // d= INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? ;
   public final void date_on_Xst_of_M( Calendar cal ) throws RecognitionException
   {
      Token d = null;
      Token m = null;
      Token y = null;
      
      final Calendar now = getCalendar();
      boolean hasMonth = false;
      boolean hasYear = false;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:370:4: (d= INT ( STs )? ( ( OF |
         // MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:370:6: d= INT ( STs )? ( ( OF |
         // MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
         {
            d = (Token) match( input, INT, FOLLOW_INT_in_date_on_Xst_of_M645 );
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:370:12: ( STs )?
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == STs ) )
            {
               alt9 = 1;
            }
            switch ( alt9 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:370:12: STs
               {
                  match( input, STs, FOLLOW_STs_in_date_on_Xst_of_M647 );
                  
               }
                  break;
               
            }
            
            cal.set( Calendar.DAY_OF_MONTH,
                     Integer.parseInt( ( d != null ? d.getText() : null ) ) );
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:374:6: ( ( OF | MINUS_A | MINUS
            // | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            int alt13 = 2;
            int LA13_0 = input.LA( 1 );
            
            if ( ( LA13_0 == OF || LA13_0 == MONTH || LA13_0 == DOT || ( LA13_0 >= MINUS && LA13_0 <= COMMA ) ) )
            {
               alt13 = 1;
            }
            switch ( alt13 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:374:7: ( OF | MINUS_A |
                  // MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
               {
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:374:7: ( OF | MINUS_A |
                  // MINUS | COMMA | DOT )?
                  int alt10 = 2;
                  int LA10_0 = input.LA( 1 );
                  
                  if ( ( LA10_0 == OF || LA10_0 == DOT || ( LA10_0 >= MINUS && LA10_0 <= COMMA ) ) )
                  {
                     alt10 = 1;
                  }
                  switch ( alt10 )
                  {
                     case 1:
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
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
                                     FOLLOW_MONTH_in_date_on_Xst_of_M695 );
                  
                  parseTextMonth( cal, ( m != null ? m.getText() : null ) );
                  hasMonth = true;
                  
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:380:7: ( MINUS | DOT )?
                  int alt11 = 2;
                  int LA11_0 = input.LA( 1 );
                  
                  if ( ( LA11_0 == DOT || LA11_0 == MINUS ) )
                  {
                     alt11 = 1;
                  }
                  switch ( alt11 )
                  {
                     case 1:
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
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
                  
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:381:7: (y= INT )?
                  int alt12 = 2;
                  int LA12_0 = input.LA( 1 );
                  
                  if ( ( LA12_0 == INT ) )
                  {
                     alt12 = 1;
                  }
                  switch ( alt12 )
                  {
                     case 1:
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:381:8: y= INT
                     {
                        y = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_date_on_Xst_of_M732 );
                        
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
                  cal.add( Calendar.YEAR, 1 );
               // if we only have a day, we roll to next month.
               else
                  cal.add( Calendar.MONTH, 1 );
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
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:409:1: date_on_M_Xst[Calendar cal] : m=
   // MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )? ;
   public final void date_on_M_Xst( Calendar cal ) throws RecognitionException
   {
      Token m = null;
      Token d = null;
      Token y = null;
      
      boolean hasYear = false;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:414:4: (m= MONTH ( MINUS | COMMA |
         // DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )? )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:414:6: m= MONTH ( MINUS | COMMA |
         // DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )?
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_date_on_M_Xst806 );
            
            parseTextMonth( cal, ( m != null ? m.getText() : null ) );
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:418:5: ( MINUS | COMMA | DOT )?
            int alt14 = 2;
            int LA14_0 = input.LA( 1 );
            
            if ( ( LA14_0 == DOT || LA14_0 == MINUS || LA14_0 == COMMA ) )
            {
               alt14 = 1;
            }
            switch ( alt14 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
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
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:419:5: (d= INT ( STs | MINUS_A
            // | MINUS | COMMA | DOT )+ )?
            int alt16 = 2;
            int LA16_0 = input.LA( 1 );
            
            if ( ( LA16_0 == INT ) )
            {
               int LA16_1 = input.LA( 2 );
               
               if ( ( LA16_1 == STs || LA16_1 == DOT || ( LA16_1 >= MINUS && LA16_1 <= COMMA ) ) )
               {
                  alt16 = 1;
               }
            }
            switch ( alt16 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:419:6: d= INT ( STs |
                  // MINUS_A | MINUS | COMMA | DOT )+
               {
                  d = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst841 );
                  
                  cal.set( Calendar.DAY_OF_MONTH,
                           Integer.parseInt( ( d != null ? d.getText() : null ) ) );
                  
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:423:8: ( STs | MINUS_A |
                  // MINUS | COMMA | DOT )+
                  int cnt15 = 0;
                  loop15: do
                  {
                     int alt15 = 2;
                     int LA15_0 = input.LA( 1 );
                     
                     if ( ( LA15_0 == STs || LA15_0 == DOT || ( LA15_0 >= MINUS && LA15_0 <= COMMA ) ) )
                     {
                        alt15 = 1;
                     }
                     
                     switch ( alt15 )
                     {
                        case 1:
                           // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
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
                           if ( cnt15 >= 1 )
                              break loop15;
                           EarlyExitException eee = new EarlyExitException( 15,
                                                                            input );
                           throw eee;
                     }
                     cnt15++;
                  }
                  while ( true );
                  
               }
                  break;
               
            }
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:424:5: (y= INT )?
            int alt17 = 2;
            int LA17_0 = input.LA( 1 );
            
            if ( ( LA17_0 == INT ) )
            {
               alt17 = 1;
            }
            switch ( alt17 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:424:6: y= INT
               {
                  y = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst889 );
                  
                  parseYear( cal, ( y != null ? y.getText() : null ) );
                  hasYear = true;
                  
               }
                  break;
               
            }
            
            // if we have a year we have a full qualified date.
            // so we change nothing.
            if ( !hasYear && getCalendar().after( cal ) )
               // If the date is before now we roll the year
               cal.add( Calendar.YEAR, 1 );
            
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
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:446:1: date_on_weekday[Calendar cal] : (
   // NEXT )? wd= WEEKDAY ;
   public final void date_on_weekday( Calendar cal ) throws RecognitionException
   {
      Token wd = null;
      
      boolean nextWeek = false;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:451:4: ( ( NEXT )? wd= WEEKDAY )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:451:6: ( NEXT )? wd= WEEKDAY
         {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:451:6: ( NEXT )?
            int alt18 = 2;
            int LA18_0 = input.LA( 1 );
            
            if ( ( LA18_0 == NEXT ) )
            {
               alt18 = 1;
            }
            switch ( alt18 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:451:7: NEXT
               {
                  match( input, NEXT, FOLLOW_NEXT_in_date_on_weekday958 );
                  nextWeek = true;
                  
               }
                  break;
               
            }
            
            wd = (Token) match( input,
                                WEEKDAY,
                                FOLLOW_WEEKDAY_in_date_on_weekday966 );
            
            final int parsedWeekDay = getWeekdayNumber( ( wd != null
                                                                    ? wd.getText()
                                                                    : null ) );
            
            if ( parsedWeekDay == -1 )
               throw new RecognitionException();
            
            final int currentWeekDay = cal.get( Calendar.DAY_OF_WEEK );
            
            cal.set( Calendar.DAY_OF_WEEK, parsedWeekDay );
            
            // If the weekday is before today or today, we adjust to next week.
            if ( parsedWeekDay <= currentWeekDay )
               cal.add( Calendar.WEEK_OF_YEAR, 1 );
            
            // if the next week is explicitly enforced
            if ( nextWeek )
               cal.add( Calendar.WEEK_OF_YEAR, 1 );
            
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
   


   // $ANTLR end "date_on_weekday"
   
   // $ANTLR start "date_in_X_YMWD"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:476:1: date_in_X_YMWD[Calendar cal] : (
   // IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* ;
   public final void date_in_X_YMWD( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:477:4: ( ( IN )?
         // date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:477:7: ( IN )?
         // date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
         {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:477:7: ( IN )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == IN ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:477:7: IN
               {
                  match( input, IN, FOLLOW_IN_in_date_in_X_YMWD1001 );
                  
               }
                  break;
               
            }
            
            pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1016 );
            date_in_X_YMWD_distance( cal );
            
            state._fsp--;
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:478:7: ( ( AND | COMMA )
            // date_in_X_YMWD_distance[$cal] )*
            loop20: do
            {
               int alt20 = 2;
               int LA20_0 = input.LA( 1 );
               
               if ( ( LA20_0 == AND || LA20_0 == COMMA ) )
               {
                  alt20 = 1;
               }
               
               switch ( alt20 )
               {
                  case 1:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:478:8: ( AND | COMMA )
                     // date_in_X_YMWD_distance[$cal]
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
                     
                     pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1035 );
                     date_in_X_YMWD_distance( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  
                  default :
                     break loop20;
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
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:485:1: date_in_X_YMWD_distance[Calendar
   // cal] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
   public final void date_in_X_YMWD_distance( Calendar cal ) throws RecognitionException
   {
      Token a = null;
      
      int amount = -1;
      int calField = Calendar.YEAR;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:491:4: ( (a= NUM_STR | a= INT ) (
         // YEARS | MONTHS | WEEKS | DAYS ) )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:491:6: (a= NUM_STR | a= INT ) (
         // YEARS | MONTHS | WEEKS | DAYS )
         {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:491:6: (a= NUM_STR | a= INT )
            int alt21 = 2;
            int LA21_0 = input.LA( 1 );
            
            if ( ( LA21_0 == NUM_STR ) )
            {
               alt21 = 1;
            }
            else if ( ( LA21_0 == INT ) )
            {
               alt21 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     21,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt21 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:491:10: a= NUM_STR
               {
                  a = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1084 );
                  amount = strToNumber( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:492:10: a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_date_in_X_YMWD_distance1099 );
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               
            }
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:493:6: ( YEARS | MONTHS | WEEKS
            // | DAYS )
            int alt22 = 4;
            switch ( input.LA( 1 ) )
            {
               case YEARS:
               {
                  alt22 = 1;
               }
                  break;
               case MONTHS:
               {
                  alt22 = 2;
               }
                  break;
               case WEEKS:
               {
                  alt22 = 3;
               }
                  break;
               case DAYS:
               {
                  alt22 = 4;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        22,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt22 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:493:12: YEARS
               {
                  match( input,
                         YEARS,
                         FOLLOW_YEARS_in_date_in_X_YMWD_distance1119 );
                  
               }
                  break;
               case 2:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:494:12: MONTHS
               {
                  match( input,
                         MONTHS,
                         FOLLOW_MONTHS_in_date_in_X_YMWD_distance1132 );
                  calField = Calendar.MONTH;
                  
               }
                  break;
               case 3:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:495:12: WEEKS
               {
                  match( input,
                         WEEKS,
                         FOLLOW_WEEKS_in_date_in_X_YMWD_distance1148 );
                  calField = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 4:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:496:12: DAYS
               {
                  match( input,
                         DAYS,
                         FOLLOW_DAYS_in_date_in_X_YMWD_distance1165 );
                  calField = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               
            }
            
            if ( amount != -1 )
            {
               cal.add( calField, amount );
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
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:517:1: date_end_of_the_MW[Calendar cal]
   // : END ( OF )? ( THE )? ( WEEKS | MONTHS ) ;
   public final void date_end_of_the_MW( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:518:4: ( END ( OF )? ( THE )? (
         // WEEKS | MONTHS ) )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:518:6: END ( OF )? ( THE )? (
         // WEEKS | MONTHS )
         {
            match( input, END, FOLLOW_END_in_date_end_of_the_MW1216 );
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:518:10: ( OF )?
            int alt23 = 2;
            int LA23_0 = input.LA( 1 );
            
            if ( ( LA23_0 == OF ) )
            {
               alt23 = 1;
            }
            switch ( alt23 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:518:10: OF
               {
                  match( input, OF, FOLLOW_OF_in_date_end_of_the_MW1218 );
                  
               }
                  break;
               
            }
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:518:14: ( THE )?
            int alt24 = 2;
            int LA24_0 = input.LA( 1 );
            
            if ( ( LA24_0 == THE ) )
            {
               alt24 = 1;
            }
            switch ( alt24 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:518:14: THE
               {
                  match( input, THE, FOLLOW_THE_in_date_end_of_the_MW1221 );
                  
               }
                  break;
               
            }
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:519:6: ( WEEKS | MONTHS )
            int alt25 = 2;
            int LA25_0 = input.LA( 1 );
            
            if ( ( LA25_0 == WEEKS ) )
            {
               alt25 = 1;
            }
            else if ( ( LA25_0 == MONTHS ) )
            {
               alt25 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     25,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt25 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:519:10: WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_date_end_of_the_MW1233 );
                  
                  rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                  
               }
                  break;
               case 2:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:523:10: MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_date_end_of_the_MW1255 );
                  
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
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:533:1: date_natural[Calendar cal] : ( (
   // TODAY | TONIGHT ) | NEVER | TOMORROW | YESTERDAY );
   public final void date_natural( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:534:4: ( ( TODAY | TONIGHT ) |
         // NEVER | TOMORROW | YESTERDAY )
         int alt26 = 4;
         switch ( input.LA( 1 ) )
         {
            case TODAY:
            case TONIGHT:
            {
               alt26 = 1;
            }
               break;
            case NEVER:
            {
               alt26 = 2;
            }
               break;
            case TOMORROW:
            {
               alt26 = 3;
            }
               break;
            case YESTERDAY:
            {
               alt26 = 4;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     26,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt26 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:534:6: ( TODAY | TONIGHT )
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
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:537:6: NEVER
            {
               match( input, NEVER, FOLLOW_NEVER_in_date_natural1316 );
               
               cal.clear( Calendar.DATE );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:541:6: TOMORROW
            {
               match( input, TOMORROW, FOLLOW_TOMORROW_in_date_natural1330 );
               
               cal.roll( Calendar.DAY_OF_YEAR, true );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:545:6: YESTERDAY
            {
               match( input, YESTERDAY, FOLLOW_YESTERDAY_in_date_natural1344 );
               
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
   
   public static final BitSet FOLLOW_EOF_in_parseDate168 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseDateWithin232 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_parseDateWithin254 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_A_in_parseDateWithin274 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseDateWithin287 = new BitSet( new long[]
   { 0x0000000000000802L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseDateWithin309 = new BitSet( new long[]
   { 0x0000000000000802L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseDateWithin331 = new BitSet( new long[]
   { 0x0000000000000802L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseDateWithin353 = new BitSet( new long[]
   { 0x0000000000000802L } );
   
   public static final BitSet FOLLOW_OF_in_parseDateWithin381 = new BitSet( new long[]
   { 0x00000220600456F0L } );
   
   public static final BitSet FOLLOW_parseDate_in_parseDateWithin383 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_full440 = new BitSet( new long[]
   { 0x0000000780000000L } );
   
   public static final BitSet FOLLOW_set_in_date_full442 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_date_full472 = new BitSet( new long[]
   { 0x0000000780000000L } );
   
   public static final BitSet FOLLOW_set_in_date_full474 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_full514 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_ON_in_date_on558 = new BitSet( new long[]
   { 0x0000002060001200L } );
   
   public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on565 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_M_Xst_in_date_on581 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_weekday_in_date_on600 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M645 = new BitSet( new long[]
   { 0x0000001D20010802L } );
   
   public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M647 = new BitSet( new long[]
   { 0x0000001D20000802L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M665 = new BitSet( new long[]
   { 0x0000000020000000L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M695 = new BitSet( new long[]
   { 0x0000002500000002L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M714 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M732 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst806 = new BitSet( new long[]
   { 0x0000003500000002L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst821 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst841 = new BitSet( new long[]
   { 0x0000001D00010000L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst859 = new BitSet( new long[]
   { 0x0000003D00010002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst889 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEXT_in_date_on_weekday958 = new BitSet( new long[]
   { 0x0000000040000000L } );
   
   public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday966 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_IN_in_date_in_X_YMWD1001 = new BitSet( new long[]
   { 0x0000022000000400L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1016 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_set_in_date_in_X_YMWD1026 = new BitSet( new long[]
   { 0x0000022000000400L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1035 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1084 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance1099 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance1119 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance1132 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance1148 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1165 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_END_in_date_end_of_the_MW1216 = new BitSet( new long[]
   { 0x0000000001808800L } );
   
   public static final BitSet FOLLOW_OF_in_date_end_of_the_MW1218 = new BitSet( new long[]
   { 0x0000000001808000L } );
   
   public static final BitSet FOLLOW_THE_in_date_end_of_the_MW1221 = new BitSet( new long[]
   { 0x0000000001800000L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1233 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1255 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_date_natural1296 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_date_natural1316 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TOMORROW_in_date_natural1330 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_YESTERDAY_in_date_natural1344 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
