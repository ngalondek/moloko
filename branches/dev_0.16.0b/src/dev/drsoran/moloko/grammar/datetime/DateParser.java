// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g 2011-06-22 06:28:18

package dev.drsoran.moloko.grammar.datetime;

import java.util.Calendar;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateReturn;
import dev.drsoran.moloko.util.MolokoCalendar;


public class DateParser extends AbstractDateParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NOW", "INT", "NUM_STR", "A",
    "DAYS", "WEEKS", "MONTHS", "YEARS", "OF", "DOT", "MINUS", "COLON",
    "DATE_SEP", "ON", "STs", "MINUS_A", "COMMA", "MONTH", "NEXT", "WEEKDAY",
    "IN", "AND", "END", "THE", "TODAY", "TONIGHT", "NEVER", "TOMORROW",
    "YESTERDAY", "WS" };
   
   public static final int STs = 18;
   
   public static final int TODAY = 28;
   
   public static final int A = 7;
   
   public static final int MINUS_A = 19;
   
   public static final int THE = 27;
   
   public static final int ON = 17;
   
   public static final int NOW = 4;
   
   public static final int INT = 5;
   
   public static final int MINUS = 14;
   
   public static final int AND = 25;
   
   public static final int EOF = -1;
   
   public static final int YEARS = 11;
   
   public static final int OF = 12;
   
   public static final int NUM_STR = 6;
   
   public static final int MONTH = 21;
   
   public static final int COLON = 15;
   
   public static final int DAYS = 8;
   
   public static final int WS = 33;
   
   public static final int WEEKDAY = 23;
   
   public static final int WEEKS = 9;
   
   public static final int IN = 24;
   
   public static final int TONIGHT = 29;
   
   public static final int COMMA = 20;
   
   public static final int MONTHS = 10;
   
   public static final int NEXT = 22;
   
   public static final int NEVER = 30;
   
   public static final int DATE_SEP = 16;
   
   public static final int END = 26;
   
   public static final int DOT = 13;
   
   public static final int YESTERDAY = 32;
   
   public static final int TOMORROW = 31;
   
   

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
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g";
   }
   


   public DateParser()
   {
      super( null );
   }
   


   protected int numberStringToNumber( String string )
   {
      switch ( string.charAt( 0 ) )
      {
         case 'o':
            return 1;
         case 't':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'w':
                  return 2;
               case 'h':
                  return 3;
            }
         }
         case 'f':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'o':
                  return 4;
               case 'i':
                  return 5;
            }
         }
         case 's':
         {
            switch ( string.charAt( 1 ) )
            {
               case 'i':
                  return 6;
               case 'e':
                  return 7;
            }
         }
         case 'e':
            return 8;
         case 'n':
            return 9;
         default :
            return 10;
      }
   }
   


   protected int weekdayStringToNumber( String string )
   {
      switch ( string.charAt( 0 ) )
      {
         case 'm':
            return Calendar.MONDAY;
         case 't':
            switch ( string.charAt( 1 ) )
            {
               case 'u':
                  return Calendar.TUESDAY;
               case 'h':
                  return Calendar.THURSDAY;
            }
         case 'w':
            return Calendar.WEDNESDAY;
         case 's':
            switch ( string.charAt( 1 ) )
            {
               case 'a':
                  return Calendar.SATURDAY;
               case 'u':
                  return Calendar.SUNDAY;
            }
         default :
            return Calendar.FRIDAY;
      }
   }
   


   protected int monthStringToNumber( String string )
   {
      switch ( string.charAt( 0 ) )
      {
         case 'f':
            return Calendar.FEBRUARY;
         case 'm':
            switch ( string.charAt( 2 ) )
            {
               case 'r':
                  return Calendar.MARCH;
               case 'y':
                  return Calendar.MAY;
            }
         case 'j':
            switch ( string.charAt( 1 ) )
            {
               case 'a':
                  return Calendar.JANUARY;
               default :
                  switch ( string.charAt( 2 ) )
                  {
                     case 'n':
                        return Calendar.JUNE;
                     case 'l':
                        return Calendar.JULY;
                  }
            }
         case 'a':
            switch ( string.charAt( 2 ) )
            {
               case 'p':
                  return Calendar.APRIL;
               case 'u':
                  return Calendar.AUGUST;
            }
         case 's':
            return Calendar.SEPTEMBER;
         case 'o':
            return Calendar.OCTOBER;
         case 'n':
            return Calendar.NOVEMBER;
         default :
            return Calendar.DECEMBER;
      }
   }
   


   // $ANTLR start "parseDate"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:143:1:
   // parseDate[MolokoCalendar cal, boolean clearTime] returns [ParseDateReturn result] : ( ( date_full[$cal] |
   // date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | NOW );
   public final ParseDateReturn parseDate( MolokoCalendar cal, boolean clearTime ) throws RecognitionException
   {
      ParseDateReturn result = null;
      
      startDateParsing( cal );
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:155:4: ( (
         // date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) |
         // NOW )
         int alt2 = 2;
         int LA2_0 = input.LA( 1 );
         
         if ( ( ( LA2_0 >= INT && LA2_0 <= NUM_STR ) || LA2_0 == ON
            || ( LA2_0 >= MONTH && LA2_0 <= IN ) || LA2_0 == END || ( LA2_0 >= TODAY && LA2_0 <= YESTERDAY ) ) )
         {
            alt2 = 1;
         }
         else if ( ( LA2_0 == NOW ) )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:155:6:
               // ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
               // date_natural[$cal] )
            {
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:155:6:
               // ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
               // date_natural[$cal] )
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
                        case MINUS_A:
                        case COMMA:
                        case MONTH:
                        {
                           alt1 = 2;
                        }
                           break;
                        case COLON:
                        case DATE_SEP:
                        {
                           alt1 = 1;
                        }
                           break;
                        case DAYS:
                        case WEEKS:
                        case MONTHS:
                        case YEARS:
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
                  case MONTH:
                  case NEXT:
                  case WEEKDAY:
                  {
                     alt1 = 2;
                  }
                     break;
                  case NUM_STR:
                  case IN:
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
                  case TONIGHT:
                  case NEVER:
                  case TOMORROW:
                  case YESTERDAY:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:155:10:
                     // date_full[$cal]
                  {
                     pushFollow( FOLLOW_date_full_in_parseDate104 );
                     date_full( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:156:10:
                     // date_on[$cal]
                  {
                     pushFollow( FOLLOW_date_on_in_parseDate125 );
                     date_on( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 3:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:157:10:
                     // date_in_X_YMWD[$cal]
                  {
                     pushFollow( FOLLOW_date_in_X_YMWD_in_parseDate148 );
                     date_in_X_YMWD( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 4:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:158:10:
                     // date_end_of_the_MW[$cal]
                  {
                     pushFollow( FOLLOW_date_end_of_the_MW_in_parseDate164 );
                     date_end_of_the_MW( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 5:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:159:10:
                     // date_natural[$cal]
                  {
                     pushFollow( FOLLOW_date_natural_in_parseDate176 );
                     date_natural( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  
               }
               
               if ( clearTime )
                  cal.setHasTime( false );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:167:6:
               // NOW
            {
               match( input, NOW, FOLLOW_NOW_in_parseDate208 );
               
               // In case of NOW we do not respect the clearTime Parameter
               // cause NOW has always a time.
               cal.setTimeInMillis( System.currentTimeMillis() );
               cal.setHasTime( true );
               
            }
               break;
            
         }
         
         result = finishedDateParsing( cal );
         
      }
      catch ( RecognitionException e )
      {
         
         notifyParsingDateFailed();
         throw e;
         
      }
      finally
      {
      }
      return result;
   }
   
   
   // $ANTLR end "parseDate"
   
   public static class parseDateWithin_return extends ParserRuleReturnScope
   {
      public MolokoCalendar epochStart;
      
      public MolokoCalendar epochEnd;
   };
   
   

   // $ANTLR start "parseDateWithin"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:181:1:
   // parseDateWithin[boolean past] returns [MolokoCalendar epochStart, MolokoCalendar epochEnd] : (a= INT | n= NUM_STR
   // | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? ;
   public final DateParser.parseDateWithin_return parseDateWithin( boolean past ) throws RecognitionException
   {
      DateParser.parseDateWithin_return retval = new DateParser.parseDateWithin_return();
      retval.start = input.LT( 1 );
      
      Token a = null;
      Token n = null;
      
      retval.epochStart = getCalendar();
      int amount = 1;
      int unit = Calendar.DAY_OF_YEAR;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:194:4: (
         // (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:194:6: (a=
         // INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )?
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:194:6:
            // (a= INT | n= NUM_STR | A )
            int alt3 = 3;
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
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        3,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt3 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:194:9:
                  // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_parseDateWithin272 );
                  
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:198:9:
                  // n= NUM_STR
               {
                  n = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_parseDateWithin294 );
                  
                  amount = numberStringToNumber( ( n != null ? n.getText()
                                                            : null ) );
                  
               }
                  break;
               case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:202:9:
                  // A
               {
                  match( input, A, FOLLOW_A_in_parseDateWithin314 );
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:203:7: (
            // DAYS | WEEKS | MONTHS | YEARS )?
            int alt4 = 5;
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
            }
            
            switch ( alt4 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:203:10:
                  // DAYS
               {
                  match( input, DAYS, FOLLOW_DAYS_in_parseDateWithin326 );
                  
                  unit = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:207:10:
                  // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_parseDateWithin348 );
                  
                  unit = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:211:10:
                  // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_parseDateWithin370 );
                  
                  unit = Calendar.MONTH;
                  
               }
                  break;
               case 4:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:215:10:
                  // YEARS
               {
                  match( input, YEARS, FOLLOW_YEARS_in_parseDateWithin392 );
                  
                  unit = Calendar.YEAR;
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:220:7: (
            // OF parseDate[retval.epochStart, false] )?
            int alt5 = 2;
            int LA5_0 = input.LA( 1 );
            
            if ( ( LA5_0 == OF ) )
            {
               alt5 = 1;
            }
            switch ( alt5 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:220:8:
                  // OF parseDate[retval.epochStart, false]
               {
                  match( input, OF, FOLLOW_OF_in_parseDateWithin421 );
                  pushFollow( FOLLOW_parseDate_in_parseDateWithin423 );
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:231:1:
   // date_full[MolokoCalendar cal] : pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON |
   // DATE_SEP ) (pt3= INT )? ;
   public final void date_full( MolokoCalendar cal ) throws RecognitionException
   {
      Token pt1 = null;
      Token pt2 = null;
      Token pt3 = null;
      
      String pt1Str = null;
      String pt2Str = null;
      String pt3Str = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:238:4:
         // (pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:238:6: pt1=
         // INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )?
         {
            pt1 = (Token) match( input, INT, FOLLOW_INT_in_date_full480 );
            if ( ( input.LA( 1 ) >= DOT && input.LA( 1 ) <= DATE_SEP ) )
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
            
            pt2 = (Token) match( input, INT, FOLLOW_INT_in_date_full512 );
            if ( ( input.LA( 1 ) >= DOT && input.LA( 1 ) <= DATE_SEP ) )
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
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:246:6:
            // (pt3= INT )?
            int alt6 = 2;
            int LA6_0 = input.LA( 1 );
            
            if ( ( LA6_0 == INT ) )
            {
               alt6 = 1;
            }
            switch ( alt6 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:247:9:
                  // pt3= INT
               {
                  pt3 = (Token) match( input, INT, FOLLOW_INT_in_date_full554 );
                  
                  pt3Str = pt3.getText();
                  
               }
                  break;
               
            }
            
            handleFullDate( cal, pt1Str, pt2Str, pt3Str );
            
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:257:1:
   // date_on[MolokoCalendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) ;
   public final void date_on( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:258:4: ( (
         // ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:258:6: ( ON
         // )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:258:6: (
            // ON )?
            int alt7 = 2;
            int LA7_0 = input.LA( 1 );
            
            if ( ( LA7_0 == ON ) )
            {
               alt7 = 1;
            }
            switch ( alt7 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:258:6:
                  // ON
               {
                  match( input, ON, FOLLOW_ON_in_date_on598 );
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:258:10:
            // ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:258:14:
                  // date_on_Xst_of_M[$cal]
               {
                  pushFollow( FOLLOW_date_on_Xst_of_M_in_date_on605 );
                  date_on_Xst_of_M( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:259:14:
                  // date_on_M_Xst[$cal]
               {
                  pushFollow( FOLLOW_date_on_M_Xst_in_date_on621 );
                  date_on_M_Xst( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:260:14:
                  // date_on_weekday[$cal]
               {
                  pushFollow( FOLLOW_date_on_weekday_in_date_on640 );
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:267:1:
   // date_on_Xst_of_M[MolokoCalendar cal] : d= INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS
   // | DOT )? (y= INT )? )? ;
   public final void date_on_Xst_of_M( MolokoCalendar cal ) throws RecognitionException
   {
      Token d = null;
      Token m = null;
      Token y = null;
      
      boolean hasMonth = false;
      boolean hasYear = false;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:273:4: (d=
         // INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:273:6: d=
         // INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
         {
            d = (Token) match( input, INT, FOLLOW_INT_in_date_on_Xst_of_M685 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:273:12:
            // ( STs )?
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == STs ) )
            {
               alt9 = 1;
            }
            switch ( alt9 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:273:12:
                  // STs
               {
                  match( input, STs, FOLLOW_STs_in_date_on_Xst_of_M687 );
                  
               }
                  break;
               
            }
            
            cal.set( Calendar.DAY_OF_MONTH,
                     Integer.parseInt( ( d != null ? d.getText() : null ) ) );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:277:6: (
            // ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            int alt13 = 2;
            int LA13_0 = input.LA( 1 );
            
            if ( ( ( LA13_0 >= OF && LA13_0 <= MINUS ) || ( LA13_0 >= MINUS_A && LA13_0 <= MONTH ) ) )
            {
               alt13 = 1;
            }
            switch ( alt13 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:277:7:
                  // ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:277:7:
                  // ( OF | MINUS_A | MINUS | COMMA | DOT )?
                  int alt10 = 2;
                  int LA10_0 = input.LA( 1 );
                  
                  if ( ( ( LA10_0 >= OF && LA10_0 <= MINUS ) || ( LA10_0 >= MINUS_A && LA10_0 <= COMMA ) ) )
                  {
                     alt10 = 1;
                  }
                  switch ( alt10 )
                  {
                     case 1:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
                     {
                        if ( ( input.LA( 1 ) >= OF && input.LA( 1 ) <= MINUS )
                           || ( input.LA( 1 ) >= MINUS_A && input.LA( 1 ) <= COMMA ) )
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
                                     FOLLOW_MONTH_in_date_on_Xst_of_M735 );
                  
                  parseTextMonth( cal, ( m != null ? m.getText() : null ) );
                  hasMonth = true;
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:283:7:
                  // ( MINUS | DOT )?
                  int alt11 = 2;
                  int LA11_0 = input.LA( 1 );
                  
                  if ( ( ( LA11_0 >= DOT && LA11_0 <= MINUS ) ) )
                  {
                     alt11 = 1;
                  }
                  switch ( alt11 )
                  {
                     case 1:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
                     {
                        if ( ( input.LA( 1 ) >= DOT && input.LA( 1 ) <= MINUS ) )
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
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:284:7:
                  // (y= INT )?
                  int alt12 = 2;
                  int LA12_0 = input.LA( 1 );
                  
                  if ( ( LA12_0 == INT ) )
                  {
                     alt12 = 1;
                  }
                  switch ( alt12 )
                  {
                     case 1:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:284:8:
                        // y= INT
                     {
                        y = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_date_on_Xst_of_M772 );
                        
                        parseYear( cal, ( y != null ? y.getText() : null ) );
                        hasYear = true;
                        
                     }
                        break;
                     
                  }
                  
               }
                  break;
               
            }
            
            handleDateOnXstOfMonth( cal, hasYear, hasMonth );
            
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:302:1:
   // date_on_M_Xst[MolokoCalendar cal] : m= MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA |
   // DOT )+ )? (y= INT )? ;
   public final void date_on_M_Xst( MolokoCalendar cal ) throws RecognitionException
   {
      Token m = null;
      Token d = null;
      Token y = null;
      
      boolean hasYear = false;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:307:4: (m=
         // MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:307:6: m=
         // MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )?
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_date_on_M_Xst846 );
            
            parseTextMonth( cal, ( m != null ? m.getText() : null ) );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:311:5: (
            // MINUS | COMMA | DOT )?
            int alt14 = 2;
            int LA14_0 = input.LA( 1 );
            
            if ( ( ( LA14_0 >= DOT && LA14_0 <= MINUS ) || LA14_0 == COMMA ) )
            {
               alt14 = 1;
            }
            switch ( alt14 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
               {
                  if ( ( input.LA( 1 ) >= DOT && input.LA( 1 ) <= MINUS )
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
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:312:5:
            // (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )?
            int alt16 = 2;
            int LA16_0 = input.LA( 1 );
            
            if ( ( LA16_0 == INT ) )
            {
               int LA16_1 = input.LA( 2 );
               
               if ( ( ( LA16_1 >= DOT && LA16_1 <= MINUS ) || ( LA16_1 >= STs && LA16_1 <= COMMA ) ) )
               {
                  alt16 = 1;
               }
            }
            switch ( alt16 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:312:6:
                  // d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+
               {
                  d = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst881 );
                  
                  cal.set( Calendar.DAY_OF_MONTH,
                           Integer.parseInt( ( d != null ? d.getText() : null ) ) );
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:316:8:
                  // ( STs | MINUS_A | MINUS | COMMA | DOT )+
                  int cnt15 = 0;
                  loop15: do
                  {
                     int alt15 = 2;
                     int LA15_0 = input.LA( 1 );
                     
                     if ( ( ( LA15_0 >= DOT && LA15_0 <= MINUS ) || ( LA15_0 >= STs && LA15_0 <= COMMA ) ) )
                     {
                        alt15 = 1;
                     }
                     
                     switch ( alt15 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
                        {
                           if ( ( input.LA( 1 ) >= DOT && input.LA( 1 ) <= MINUS )
                              || ( input.LA( 1 ) >= STs && input.LA( 1 ) <= COMMA ) )
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
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:317:5:
            // (y= INT )?
            int alt17 = 2;
            int LA17_0 = input.LA( 1 );
            
            if ( ( LA17_0 == INT ) )
            {
               alt17 = 1;
            }
            switch ( alt17 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:317:6:
                  // y= INT
               {
                  y = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst929 );
                  
                  parseYear( cal, ( y != null ? y.getText() : null ) );
                  hasYear = true;
                  
               }
                  break;
               
            }
            
            handleDateOnXstOfMonth( cal, hasYear, true /* hasMonth */);
            
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:335:1:
   // date_on_weekday[MolokoCalendar cal] : ( NEXT )? wd= WEEKDAY ;
   public final void date_on_weekday( MolokoCalendar cal ) throws RecognitionException
   {
      Token wd = null;
      
      boolean nextWeek = false;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:340:4: ( (
         // NEXT )? wd= WEEKDAY )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:340:6: (
         // NEXT )? wd= WEEKDAY
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:340:6: (
            // NEXT )?
            int alt18 = 2;
            int LA18_0 = input.LA( 1 );
            
            if ( ( LA18_0 == NEXT ) )
            {
               alt18 = 1;
            }
            switch ( alt18 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:340:7:
                  // NEXT
               {
                  match( input, NEXT, FOLLOW_NEXT_in_date_on_weekday998 );
                  nextWeek = true;
                  
               }
                  break;
               
            }
            
            wd = (Token) match( input,
                                WEEKDAY,
                                FOLLOW_WEEKDAY_in_date_on_weekday1006 );
            
            handleDateOnWeekday( cal,
                                 ( wd != null ? wd.getText() : null ),
                                 nextWeek );
            
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:350:1:
   // date_in_X_YMWD[MolokoCalendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA )
   // date_in_X_YMWD_distance[$cal] )* ;
   public final void date_in_X_YMWD( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:351:4: ( (
         // IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:351:7: ( IN
         // )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:351:7: (
            // IN )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == IN ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:351:7:
                  // IN
               {
                  match( input, IN, FOLLOW_IN_in_date_in_X_YMWD1041 );
                  
               }
                  break;
               
            }
            
            pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1056 );
            date_in_X_YMWD_distance( cal );
            
            state._fsp--;
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:352:7: (
            // ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop20: do
            {
               int alt20 = 2;
               int LA20_0 = input.LA( 1 );
               
               if ( ( LA20_0 == COMMA || LA20_0 == AND ) )
               {
                  alt20 = 1;
               }
               
               switch ( alt20 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:352:8:
                     // ( AND | COMMA ) date_in_X_YMWD_distance[$cal]
                  {
                     if ( input.LA( 1 ) == COMMA || input.LA( 1 ) == AND )
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
                     
                     pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1075 );
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:359:1:
   // date_in_X_YMWD_distance[MolokoCalendar cal] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
   public final void date_in_X_YMWD_distance( MolokoCalendar cal ) throws RecognitionException
   {
      Token a = null;
      
      int amount = -1;
      int calField = Calendar.YEAR;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:365:4: (
         // (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:365:6: (a=
         // NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:365:6:
            // (a= NUM_STR | a= INT )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:365:10:
                  // a= NUM_STR
               {
                  a = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1124 );
                  amount = numberStringToNumber( ( a != null ? a.getText()
                                                            : null ) );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:366:10:
                  // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_date_in_X_YMWD_distance1139 );
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:367:6: (
            // YEARS | MONTHS | WEEKS | DAYS )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:367:12:
                  // YEARS
               {
                  match( input,
                         YEARS,
                         FOLLOW_YEARS_in_date_in_X_YMWD_distance1159 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:368:12:
                  // MONTHS
               {
                  match( input,
                         MONTHS,
                         FOLLOW_MONTHS_in_date_in_X_YMWD_distance1172 );
                  calField = Calendar.MONTH;
                  
               }
                  break;
               case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:369:12:
                  // WEEKS
               {
                  match( input,
                         WEEKS,
                         FOLLOW_WEEKS_in_date_in_X_YMWD_distance1188 );
                  calField = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 4:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:370:12:
                  // DAYS
               {
                  match( input,
                         DAYS,
                         FOLLOW_DAYS_in_date_in_X_YMWD_distance1205 );
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:391:1:
   // date_end_of_the_MW[MolokoCalendar cal] : END ( OF )? ( THE )? ( WEEKS | MONTHS ) ;
   public final void date_end_of_the_MW( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:392:4: (
         // END ( OF )? ( THE )? ( WEEKS | MONTHS ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:392:6: END
         // ( OF )? ( THE )? ( WEEKS | MONTHS )
         {
            match( input, END, FOLLOW_END_in_date_end_of_the_MW1256 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:392:10:
            // ( OF )?
            int alt23 = 2;
            int LA23_0 = input.LA( 1 );
            
            if ( ( LA23_0 == OF ) )
            {
               alt23 = 1;
            }
            switch ( alt23 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:392:10:
                  // OF
               {
                  match( input, OF, FOLLOW_OF_in_date_end_of_the_MW1258 );
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:392:14:
            // ( THE )?
            int alt24 = 2;
            int LA24_0 = input.LA( 1 );
            
            if ( ( LA24_0 == THE ) )
            {
               alt24 = 1;
            }
            switch ( alt24 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:392:14:
                  // THE
               {
                  match( input, THE, FOLLOW_THE_in_date_end_of_the_MW1261 );
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:393:6: (
            // WEEKS | MONTHS )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:393:10:
                  // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_date_end_of_the_MW1273 );
                  
                  rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:397:10:
                  // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_date_end_of_the_MW1295 );
                  
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:407:1:
   // date_natural[MolokoCalendar cal] : ( ( TODAY | TONIGHT ) | NEVER | TOMORROW | YESTERDAY );
   public final void date_natural( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:408:4: ( (
         // TODAY | TONIGHT ) | NEVER | TOMORROW | YESTERDAY )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:408:6:
               // ( TODAY | TONIGHT )
            {
               if ( ( input.LA( 1 ) >= TODAY && input.LA( 1 ) <= TONIGHT ) )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:411:6:
               // NEVER
            {
               match( input, NEVER, FOLLOW_NEVER_in_date_natural1356 );
               
               cal.setHasDate( false );
               cal.setHasTime( false );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:416:6:
               // TOMORROW
            {
               match( input, TOMORROW, FOLLOW_TOMORROW_in_date_natural1370 );
               
               cal.roll( Calendar.DAY_OF_YEAR, true );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:420:6:
               // YESTERDAY
            {
               match( input, YESTERDAY, FOLLOW_YESTERDAY_in_date_natural1384 );
               
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
   
   public static final BitSet FOLLOW_date_full_in_parseDate104 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_in_parseDate125 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_in_parseDate148 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_end_of_the_MW_in_parseDate164 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_natural_in_parseDate176 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NOW_in_parseDate208 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseDateWithin272 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_parseDateWithin294 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_A_in_parseDateWithin314 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseDateWithin326 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseDateWithin348 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseDateWithin370 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseDateWithin392 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_OF_in_parseDateWithin421 = new BitSet( new long[]
   { 0x00000001F5E20070L } );
   
   public static final BitSet FOLLOW_parseDate_in_parseDateWithin423 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_full480 = new BitSet( new long[]
   { 0x000000000001E000L } );
   
   public static final BitSet FOLLOW_set_in_date_full482 = new BitSet( new long[]
   { 0x0000000000000020L } );
   
   public static final BitSet FOLLOW_INT_in_date_full512 = new BitSet( new long[]
   { 0x000000000001E000L } );
   
   public static final BitSet FOLLOW_set_in_date_full514 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_full554 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_ON_in_date_on598 = new BitSet( new long[]
   { 0x0000000000E20020L } );
   
   public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on605 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_M_Xst_in_date_on621 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_weekday_in_date_on640 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M685 = new BitSet( new long[]
   { 0x00000000003C7002L } );
   
   public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M687 = new BitSet( new long[]
   { 0x0000000000387002L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M705 = new BitSet( new long[]
   { 0x0000000000200000L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M735 = new BitSet( new long[]
   { 0x0000000000006022L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M754 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M772 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst846 = new BitSet( new long[]
   { 0x0000000000106022L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst861 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst881 = new BitSet( new long[]
   { 0x00000000001C6000L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst899 = new BitSet( new long[]
   { 0x00000000001C6022L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst929 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEXT_in_date_on_weekday998 = new BitSet( new long[]
   { 0x0000000000800000L } );
   
   public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday1006 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_IN_in_date_in_X_YMWD1041 = new BitSet( new long[]
   { 0x0000000001000060L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1056 = new BitSet( new long[]
   { 0x0000000002100002L } );
   
   public static final BitSet FOLLOW_set_in_date_in_X_YMWD1066 = new BitSet( new long[]
   { 0x0000000001000060L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1075 = new BitSet( new long[]
   { 0x0000000002100002L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1124 = new BitSet( new long[]
   { 0x0000000000000F00L } );
   
   public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance1139 = new BitSet( new long[]
   { 0x0000000000000F00L } );
   
   public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance1159 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance1172 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance1188 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1205 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_END_in_date_end_of_the_MW1256 = new BitSet( new long[]
   { 0x0000000008001600L } );
   
   public static final BitSet FOLLOW_OF_in_date_end_of_the_MW1258 = new BitSet( new long[]
   { 0x0000000008000600L } );
   
   public static final BitSet FOLLOW_THE_in_date_end_of_the_MW1261 = new BitSet( new long[]
   { 0x0000000000000600L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1273 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1295 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_date_natural1336 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_date_natural1356 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TOMORROW_in_date_natural1370 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_YESTERDAY_in_date_natural1384 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
