// $ANTLR 3.3 Nov 30, 2010 12:45:30 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g 2011-11-21 15:15:42

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

import dev.drsoran.moloko.grammar.LexerException;
import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateReturn;
import dev.drsoran.moloko.util.MolokoCalendar;


public class DateParser extends AbstractDateParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NOW", "INT", "NUM_STR", "A",
    "DAYS", "WEEKS", "MONTHS", "YEARS", "OF", "DOT", "MINUS", "DATE_SEP", "ON",
    "STs", "MINUS_A", "COMMA", "MONTH", "NEXT", "WEEKDAY", "IN", "AND", "END",
    "THE", "TODAY", "TONIGHT", "NEVER", "TOMORROW", "YESTERDAY", "COLON",
    "DATE_TIME_SEPARATOR", "WS" };
   
   public static final int EOF = -1;
   
   public static final int NOW = 4;
   
   public static final int INT = 5;
   
   public static final int NUM_STR = 6;
   
   public static final int A = 7;
   
   public static final int DAYS = 8;
   
   public static final int WEEKS = 9;
   
   public static final int MONTHS = 10;
   
   public static final int YEARS = 11;
   
   public static final int OF = 12;
   
   public static final int DOT = 13;
   
   public static final int MINUS = 14;
   
   public static final int DATE_SEP = 15;
   
   public static final int ON = 16;
   
   public static final int STs = 17;
   
   public static final int MINUS_A = 18;
   
   public static final int COMMA = 19;
   
   public static final int MONTH = 20;
   
   public static final int NEXT = 21;
   
   public static final int WEEKDAY = 22;
   
   public static final int IN = 23;
   
   public static final int AND = 24;
   
   public static final int END = 25;
   
   public static final int THE = 26;
   
   public static final int TODAY = 27;
   
   public static final int TONIGHT = 28;
   
   public static final int NEVER = 29;
   
   public static final int TOMORROW = 30;
   
   public static final int YESTERDAY = 31;
   
   public static final int COLON = 32;
   
   public static final int DATE_TIME_SEPARATOR = 33;
   
   public static final int WS = 34;
   
   
   
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
      final String numStr = string.toLowerCase();
      
      switch ( numStr.charAt( 0 ) )
      {
         case 'o':
            return 1;
         case 't':
         {
            switch ( numStr.charAt( 1 ) )
            {
               case 'w':
                  return 2;
               case 'h':
                  return 3;
            }
         }
         case 'f':
         {
            switch ( numStr.charAt( 1 ) )
            {
               case 'o':
                  return 4;
               case 'i':
                  return 5;
            }
         }
         case 's':
         {
            switch ( numStr.charAt( 1 ) )
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
      final String weekDayStr = string.toLowerCase();
      
      switch ( weekDayStr.charAt( 0 ) )
      {
         case 'm':
            return Calendar.MONDAY;
         case 't':
            switch ( weekDayStr.charAt( 1 ) )
            {
               case 'u':
                  return Calendar.TUESDAY;
               case 'h':
                  return Calendar.THURSDAY;
            }
         case 'w':
            return Calendar.WEDNESDAY;
         case 's':
            switch ( weekDayStr.charAt( 1 ) )
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
      final String monthStr = string.toLowerCase();
      
      switch ( monthStr.charAt( 0 ) )
      {
         case 'f':
            return Calendar.FEBRUARY;
         case 'm':
            switch ( monthStr.charAt( 2 ) )
            {
               case 'r':
                  return Calendar.MARCH;
               case 'y':
                  return Calendar.MAY;
            }
         case 'j':
            switch ( monthStr.charAt( 1 ) )
            {
               case 'a':
                  return Calendar.JANUARY;
               default :
                  switch ( monthStr.charAt( 2 ) )
                  {
                     case 'n':
                        return Calendar.JUNE;
                     case 'l':
                        return Calendar.JULY;
                  }
            }
         case 'a':
            switch ( monthStr.charAt( 1 ) )
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:164:1:
   // parseDate[MolokoCalendar cal, boolean clearTime] returns [ParseDateReturn result] : ( ( date_numeric[$cal] |
   // date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | NOW ) ;
   public final ParseDateReturn parseDate( MolokoCalendar cal, boolean clearTime ) throws RecognitionException
   {
      ParseDateReturn result = null;
      
      startDateParsing( cal );
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:176:4: ( (
         // ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal]
         // ) | NOW ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:176:6: ( (
         // date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
         // | NOW )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:176:6: (
            // ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
            // date_natural[$cal] ) | NOW )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:177:8:
               // ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
               // date_natural[$cal] )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:177:8:
                  // ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:177:12:
                     // date_numeric[$cal]
                     {
                        pushFollow( FOLLOW_date_numeric_in_parseDate119 );
                        date_numeric( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:178:12:
                     // date_on[$cal]
                     {
                        pushFollow( FOLLOW_date_on_in_parseDate139 );
                        date_on( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 3:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:179:12:
                     // date_in_X_YMWD[$cal]
                     {
                        pushFollow( FOLLOW_date_in_X_YMWD_in_parseDate164 );
                        date_in_X_YMWD( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 4:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:180:12:
                     // date_end_of_the_MW[$cal]
                     {
                        pushFollow( FOLLOW_date_end_of_the_MW_in_parseDate182 );
                        date_end_of_the_MW( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 5:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:181:12:
                     // date_natural[$cal]
                     {
                        pushFollow( FOLLOW_date_natural_in_parseDate196 );
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:189:9:
               // NOW
               {
                  match( input, NOW, FOLLOW_NOW_in_parseDate255 );
                  
                  // In case of NOW we do not respect the clearTime Parameter
                  // cause NOW has always a time.
                  cal.setTimeInMillis( System.currentTimeMillis() );
                  cal.setHasTime( true );
                  
               }
                  break;
            
            }
            
         }
         
         result = finishedDateParsing( cal );
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:199:1:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:212:4: (
         // (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:212:6: (a=
         // INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )?
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:212:6:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:212:9:
               // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_parseDateWithin315 );
                  
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:216:9:
               // n= NUM_STR
               {
                  n = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_parseDateWithin337 );
                  
                  amount = numberStringToNumber( ( n != null ? n.getText()
                                                            : null ) );
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:220:9:
               // A
               {
                  match( input, A, FOLLOW_A_in_parseDateWithin357 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:221:7: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:221:10:
               // DAYS
               {
                  match( input, DAYS, FOLLOW_DAYS_in_parseDateWithin369 );
                  
                  unit = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:225:10:
               // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_parseDateWithin391 );
                  
                  unit = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:229:10:
               // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_parseDateWithin413 );
                  
                  unit = Calendar.MONTH;
                  
               }
                  break;
               case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:233:10:
               // YEARS
               {
                  match( input, YEARS, FOLLOW_YEARS_in_parseDateWithin435 );
                  
                  unit = Calendar.YEAR;
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:238:7: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:238:8:
               // OF parseDate[retval.epochStart, false]
               {
                  match( input, OF, FOLLOW_OF_in_parseDateWithin464 );
                  pushFollow( FOLLOW_parseDate_in_parseDateWithin466 );
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
      finally
      {
      }
      return retval;
   }
   
   
   
   // $ANTLR end "parseDateWithin"
   
   // $ANTLR start "date_numeric"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:245:1:
   // date_numeric[MolokoCalendar cal] : pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( ( DOT | MINUS | DATE_SEP ) (pt3=
   // INT )? )? ;
   public final void date_numeric( MolokoCalendar cal ) throws RecognitionException
   {
      Token pt1 = null;
      Token pt2 = null;
      Token pt3 = null;
      
      String pt1Str = null;
      String pt2Str = null;
      String pt3Str = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:252:4:
         // (pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( ( DOT | MINUS | DATE_SEP ) (pt3= INT )? )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:252:6: pt1=
         // INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( ( DOT | MINUS | DATE_SEP ) (pt3= INT )? )?
         {
            pt1 = (Token) match( input, INT, FOLLOW_INT_in_date_numeric511 );
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
            
            pt2 = (Token) match( input, INT, FOLLOW_INT_in_date_numeric539 );
            
            pt2Str = pt2.getText();
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:260:6: (
            // ( DOT | MINUS | DATE_SEP ) (pt3= INT )? )?
            int alt7 = 2;
            int LA7_0 = input.LA( 1 );
            
            if ( ( ( LA7_0 >= DOT && LA7_0 <= DATE_SEP ) ) )
            {
               alt7 = 1;
            }
            switch ( alt7 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:261:9:
               // ( DOT | MINUS | DATE_SEP ) (pt3= INT )?
               {
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
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:262:9:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:263:12:
                     // pt3= INT
                     {
                        pt3 = (Token) match( input,
                                             INT,
                                             FOLLOW_INT_in_date_numeric598 );
                        
                        pt3Str = pt3.getText();
                        
                     }
                        break;
                  
                  }
                  
               }
                  break;
            
            }
            
            handleNumericDate( cal, pt1Str, pt2Str, pt3Str );
            
         }
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      finally
      {
      }
      return;
   }
   
   
   
   // $ANTLR end "date_numeric"
   
   // $ANTLR start "date_on"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:274:1:
   // date_on[MolokoCalendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) ;
   public final void date_on( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:275:4: ( (
         // ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:275:6: ( ON
         // )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:275:6: (
            // ON )?
            int alt8 = 2;
            int LA8_0 = input.LA( 1 );
            
            if ( ( LA8_0 == ON ) )
            {
               alt8 = 1;
            }
            switch ( alt8 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:275:6:
               // ON
               {
                  match( input, ON, FOLLOW_ON_in_date_on656 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:275:10:
            // ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
            int alt9 = 3;
            switch ( input.LA( 1 ) )
            {
               case INT:
               {
                  alt9 = 1;
               }
                  break;
               case MONTH:
               {
                  alt9 = 2;
               }
                  break;
               case NEXT:
               case WEEKDAY:
               {
                  alt9 = 3;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt9 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:275:14:
               // date_on_Xst_of_M[$cal]
               {
                  pushFollow( FOLLOW_date_on_Xst_of_M_in_date_on663 );
                  date_on_Xst_of_M( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:276:14:
               // date_on_M_Xst[$cal]
               {
                  pushFollow( FOLLOW_date_on_M_Xst_in_date_on679 );
                  date_on_M_Xst( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:277:14:
               // date_on_weekday[$cal]
               {
                  pushFollow( FOLLOW_date_on_weekday_in_date_on698 );
                  date_on_weekday( cal );
                  
                  state._fsp--;
                  
               }
                  break;
            
            }
            
         }
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      finally
      {
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on"
   
   // $ANTLR start "date_on_Xst_of_M"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:280:1:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:286:4: (d=
         // INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:286:6: d=
         // INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
         {
            d = (Token) match( input, INT, FOLLOW_INT_in_date_on_Xst_of_M731 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:286:12:
            // ( STs )?
            int alt10 = 2;
            int LA10_0 = input.LA( 1 );
            
            if ( ( LA10_0 == STs ) )
            {
               alt10 = 1;
            }
            switch ( alt10 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:286:12:
               // STs
               {
                  match( input, STs, FOLLOW_STs_in_date_on_Xst_of_M733 );
                  
               }
                  break;
            
            }
            
            cal.set( Calendar.DAY_OF_MONTH,
                     Integer.parseInt( ( d != null ? d.getText() : null ) ) );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:290:6: (
            // ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            int alt14 = 2;
            int LA14_0 = input.LA( 1 );
            
            if ( ( ( LA14_0 >= OF && LA14_0 <= MINUS ) || ( LA14_0 >= MINUS_A && LA14_0 <= MONTH ) ) )
            {
               alt14 = 1;
            }
            switch ( alt14 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:290:7:
               // ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:290:7:
                  // ( OF | MINUS_A | MINUS | COMMA | DOT )?
                  int alt11 = 2;
                  int LA11_0 = input.LA( 1 );
                  
                  if ( ( ( LA11_0 >= OF && LA11_0 <= MINUS ) || ( LA11_0 >= MINUS_A && LA11_0 <= COMMA ) ) )
                  {
                     alt11 = 1;
                  }
                  switch ( alt11 )
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
                                     FOLLOW_MONTH_in_date_on_Xst_of_M781 );
                  
                  parseTextMonth( cal, ( m != null ? m.getText() : null ) );
                  hasMonth = true;
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:296:7:
                  // ( MINUS | DOT )?
                  int alt12 = 2;
                  int LA12_0 = input.LA( 1 );
                  
                  if ( ( ( LA12_0 >= DOT && LA12_0 <= MINUS ) ) )
                  {
                     alt12 = 1;
                  }
                  switch ( alt12 )
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
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:297:7:
                  // (y= INT )?
                  int alt13 = 2;
                  int LA13_0 = input.LA( 1 );
                  
                  if ( ( LA13_0 == INT ) )
                  {
                     alt13 = 1;
                  }
                  switch ( alt13 )
                  {
                     case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:297:8:
                     // y= INT
                     {
                        y = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_date_on_Xst_of_M818 );
                        
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
      finally
      {
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on_Xst_of_M"
   
   // $ANTLR start "date_on_M_Xst"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:311:1:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:316:4: (m=
         // MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:316:6: m=
         // MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )?
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_date_on_M_Xst880 );
            
            parseTextMonth( cal, ( m != null ? m.getText() : null ) );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:320:5: (
            // MINUS | COMMA | DOT )?
            int alt15 = 2;
            int LA15_0 = input.LA( 1 );
            
            if ( ( ( LA15_0 >= DOT && LA15_0 <= MINUS ) || LA15_0 == COMMA ) )
            {
               alt15 = 1;
            }
            switch ( alt15 )
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
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:321:5:
            // (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )?
            int alt17 = 2;
            int LA17_0 = input.LA( 1 );
            
            if ( ( LA17_0 == INT ) )
            {
               int LA17_1 = input.LA( 2 );
               
               if ( ( ( LA17_1 >= DOT && LA17_1 <= MINUS ) || ( LA17_1 >= STs && LA17_1 <= COMMA ) ) )
               {
                  alt17 = 1;
               }
            }
            switch ( alt17 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:321:6:
               // d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+
               {
                  d = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst915 );
                  
                  cal.set( Calendar.DAY_OF_MONTH,
                           Integer.parseInt( ( d != null ? d.getText() : null ) ) );
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:325:8:
                  // ( STs | MINUS_A | MINUS | COMMA | DOT )+
                  int cnt16 = 0;
                  loop16: do
                  {
                     int alt16 = 2;
                     int LA16_0 = input.LA( 1 );
                     
                     if ( ( ( LA16_0 >= DOT && LA16_0 <= MINUS ) || ( LA16_0 >= STs && LA16_0 <= COMMA ) ) )
                     {
                        alt16 = 1;
                     }
                     
                     switch ( alt16 )
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
                           if ( cnt16 >= 1 )
                              break loop16;
                           EarlyExitException eee = new EarlyExitException( 16,
                                                                            input );
                           throw eee;
                     }
                     cnt16++;
                  }
                  while ( true );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:326:5:
            // (y= INT )?
            int alt18 = 2;
            int LA18_0 = input.LA( 1 );
            
            if ( ( LA18_0 == INT ) )
            {
               alt18 = 1;
            }
            switch ( alt18 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:326:6:
               // y= INT
               {
                  y = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst963 );
                  
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
      finally
      {
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on_M_Xst"
   
   // $ANTLR start "date_on_weekday"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:340:1:
   // date_on_weekday[MolokoCalendar cal] : ( NEXT )? wd= WEEKDAY ;
   public final void date_on_weekday( MolokoCalendar cal ) throws RecognitionException
   {
      Token wd = null;
      
      boolean nextWeek = false;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:345:4: ( (
         // NEXT )? wd= WEEKDAY )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:345:6: (
         // NEXT )? wd= WEEKDAY
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:345:6: (
            // NEXT )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == NEXT ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:345:7:
               // NEXT
               {
                  match( input, NEXT, FOLLOW_NEXT_in_date_on_weekday1020 );
                  nextWeek = true;
                  
               }
                  break;
            
            }
            
            wd = (Token) match( input,
                                WEEKDAY,
                                FOLLOW_WEEKDAY_in_date_on_weekday1028 );
            
            handleDateOnWeekday( cal,
                                 ( wd != null ? wd.getText() : null ),
                                 nextWeek );
            
         }
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      finally
      {
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on_weekday"
   
   // $ANTLR start "date_in_X_YMWD"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:351:1:
   // date_in_X_YMWD[MolokoCalendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA )
   // date_in_X_YMWD_distance[$cal] )* ;
   public final void date_in_X_YMWD( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:352:4: ( (
         // IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:352:7: ( IN
         // )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:352:7: (
            // IN )?
            int alt20 = 2;
            int LA20_0 = input.LA( 1 );
            
            if ( ( LA20_0 == IN ) )
            {
               alt20 = 1;
            }
            switch ( alt20 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:352:7:
               // IN
               {
                  match( input, IN, FOLLOW_IN_in_date_in_X_YMWD1051 );
                  
               }
                  break;
            
            }
            
            pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1066 );
            date_in_X_YMWD_distance( cal );
            
            state._fsp--;
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:353:7: (
            // ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop21: do
            {
               int alt21 = 2;
               int LA21_0 = input.LA( 1 );
               
               if ( ( LA21_0 == COMMA || LA21_0 == AND ) )
               {
                  alt21 = 1;
               }
               
               switch ( alt21 )
               {
                  case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:353:8:
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
                     
                     pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1085 );
                     date_in_X_YMWD_distance( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  
                  default :
                     break loop21;
               }
            }
            while ( true );
            
         }
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      finally
      {
      }
      return;
   }
   
   
   
   // $ANTLR end "date_in_X_YMWD"
   
   // $ANTLR start "date_in_X_YMWD_distance"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:356:1:
   // date_in_X_YMWD_distance[MolokoCalendar cal] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
   public final void date_in_X_YMWD_distance( MolokoCalendar cal ) throws RecognitionException
   {
      Token a = null;
      
      int amount = -1;
      int calField = Calendar.YEAR;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:362:4: (
         // (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:362:6: (a=
         // NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:362:6:
            // (a= NUM_STR | a= INT )
            int alt22 = 2;
            int LA22_0 = input.LA( 1 );
            
            if ( ( LA22_0 == NUM_STR ) )
            {
               alt22 = 1;
            }
            else if ( ( LA22_0 == INT ) )
            {
               alt22 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     22,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt22 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:362:10:
               // a= NUM_STR
               {
                  a = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1122 );
                  amount = numberStringToNumber( ( a != null ? a.getText()
                                                            : null ) );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:363:10:
               // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_date_in_X_YMWD_distance1137 );
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:364:6: (
            // YEARS | MONTHS | WEEKS | DAYS )
            int alt23 = 4;
            switch ( input.LA( 1 ) )
            {
               case YEARS:
               {
                  alt23 = 1;
               }
                  break;
               case MONTHS:
               {
                  alt23 = 2;
               }
                  break;
               case WEEKS:
               {
                  alt23 = 3;
               }
                  break;
               case DAYS:
               {
                  alt23 = 4;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        23,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt23 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:364:12:
               // YEARS
               {
                  match( input,
                         YEARS,
                         FOLLOW_YEARS_in_date_in_X_YMWD_distance1157 );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:365:12:
               // MONTHS
               {
                  match( input,
                         MONTHS,
                         FOLLOW_MONTHS_in_date_in_X_YMWD_distance1170 );
                  calField = Calendar.MONTH;
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:366:12:
               // WEEKS
               {
                  match( input,
                         WEEKS,
                         FOLLOW_WEEKS_in_date_in_X_YMWD_distance1186 );
                  calField = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:367:12:
               // DAYS
               {
                  match( input,
                         DAYS,
                         FOLLOW_DAYS_in_date_in_X_YMWD_distance1203 );
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
      finally
      {
      }
      return;
   }
   
   
   
   // $ANTLR end "date_in_X_YMWD_distance"
   
   // $ANTLR start "date_end_of_the_MW"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:384:1:
   // date_end_of_the_MW[MolokoCalendar cal] : END ( OF )? ( THE )? ( WEEKS | MONTHS ) ;
   public final void date_end_of_the_MW( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:385:4: (
         // END ( OF )? ( THE )? ( WEEKS | MONTHS ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:385:6: END
         // ( OF )? ( THE )? ( WEEKS | MONTHS )
         {
            match( input, END, FOLLOW_END_in_date_end_of_the_MW1242 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:385:10:
            // ( OF )?
            int alt24 = 2;
            int LA24_0 = input.LA( 1 );
            
            if ( ( LA24_0 == OF ) )
            {
               alt24 = 1;
            }
            switch ( alt24 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:385:10:
               // OF
               {
                  match( input, OF, FOLLOW_OF_in_date_end_of_the_MW1244 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:385:14:
            // ( THE )?
            int alt25 = 2;
            int LA25_0 = input.LA( 1 );
            
            if ( ( LA25_0 == THE ) )
            {
               alt25 = 1;
            }
            switch ( alt25 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:385:14:
               // THE
               {
                  match( input, THE, FOLLOW_THE_in_date_end_of_the_MW1247 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:386:6: (
            // WEEKS | MONTHS )
            int alt26 = 2;
            int LA26_0 = input.LA( 1 );
            
            if ( ( LA26_0 == WEEKS ) )
            {
               alt26 = 1;
            }
            else if ( ( LA26_0 == MONTHS ) )
            {
               alt26 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     26,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt26 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:386:10:
               // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_date_end_of_the_MW1259 );
                  
                  rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:390:10:
               // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_date_end_of_the_MW1281 );
                  
                  rollToEndOf( Calendar.DAY_OF_MONTH, cal );
                  
               }
                  break;
            
            }
            
         }
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      finally
      {
      }
      return;
   }
   
   
   
   // $ANTLR end "date_end_of_the_MW"
   
   // $ANTLR start "date_natural"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:396:1:
   // date_natural[MolokoCalendar cal] : ( ( TODAY | TONIGHT ) | NEVER | TOMORROW | YESTERDAY );
   public final void date_natural( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:397:4: ( (
         // TODAY | TONIGHT ) | NEVER | TOMORROW | YESTERDAY )
         int alt27 = 4;
         switch ( input.LA( 1 ) )
         {
            case TODAY:
            case TONIGHT:
            {
               alt27 = 1;
            }
               break;
            case NEVER:
            {
               alt27 = 2;
            }
               break;
            case TOMORROW:
            {
               alt27 = 3;
            }
               break;
            case YESTERDAY:
            {
               alt27 = 4;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     27,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt27 )
         {
            case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:397:6: (
            // TODAY | TONIGHT )
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:400:6:
            // NEVER
            {
               match( input, NEVER, FOLLOW_NEVER_in_date_natural1330 );
               
               cal.setHasDate( false );
               cal.setHasTime( false );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:405:6:
            // TOMORROW
            {
               match( input, TOMORROW, FOLLOW_TOMORROW_in_date_natural1344 );
               
               cal.roll( Calendar.DAY_OF_YEAR, true );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:409:6:
            // YESTERDAY
            {
               match( input, YESTERDAY, FOLLOW_YESTERDAY_in_date_natural1358 );
               
               cal.roll( Calendar.DAY_OF_YEAR, false );
               
            }
               break;
         
         }
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      finally
      {
      }
      return;
   }
   
   // $ANTLR end "date_natural"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_date_numeric_in_parseDate119 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_in_parseDate139 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_in_parseDate164 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_end_of_the_MW_in_parseDate182 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_natural_in_parseDate196 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NOW_in_parseDate255 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseDateWithin315 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_parseDateWithin337 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_A_in_parseDateWithin357 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseDateWithin369 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseDateWithin391 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseDateWithin413 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseDateWithin435 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_OF_in_parseDateWithin464 = new BitSet( new long[]
   { 0x00000000FAF10070L } );
   
   public static final BitSet FOLLOW_parseDate_in_parseDateWithin466 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric511 = new BitSet( new long[]
   { 0x000000000000E000L } );
   
   public static final BitSet FOLLOW_set_in_date_numeric513 = new BitSet( new long[]
   { 0x0000000000000020L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric539 = new BitSet( new long[]
   { 0x000000000000E002L } );
   
   public static final BitSet FOLLOW_set_in_date_numeric563 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric598 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_ON_in_date_on656 = new BitSet( new long[]
   { 0x0000000000710020L } );
   
   public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on663 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_M_Xst_in_date_on679 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_weekday_in_date_on698 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M731 = new BitSet( new long[]
   { 0x00000000001E7002L } );
   
   public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M733 = new BitSet( new long[]
   { 0x00000000001C7002L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M751 = new BitSet( new long[]
   { 0x0000000000100000L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M781 = new BitSet( new long[]
   { 0x0000000000006022L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M800 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M818 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst880 = new BitSet( new long[]
   { 0x0000000000086022L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst895 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst915 = new BitSet( new long[]
   { 0x00000000000E6000L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst933 = new BitSet( new long[]
   { 0x00000000000E6022L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst963 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEXT_in_date_on_weekday1020 = new BitSet( new long[]
   { 0x0000000000400000L } );
   
   public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday1028 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_IN_in_date_in_X_YMWD1051 = new BitSet( new long[]
   { 0x0000000000800060L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1066 = new BitSet( new long[]
   { 0x0000000001080002L } );
   
   public static final BitSet FOLLOW_set_in_date_in_X_YMWD1076 = new BitSet( new long[]
   { 0x0000000000800060L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1085 = new BitSet( new long[]
   { 0x0000000001080002L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1122 = new BitSet( new long[]
   { 0x0000000000000F00L } );
   
   public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance1137 = new BitSet( new long[]
   { 0x0000000000000F00L } );
   
   public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance1157 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance1170 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance1186 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1203 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_END_in_date_end_of_the_MW1242 = new BitSet( new long[]
   { 0x0000000004001600L } );
   
   public static final BitSet FOLLOW_OF_in_date_end_of_the_MW1244 = new BitSet( new long[]
   { 0x0000000004000600L } );
   
   public static final BitSet FOLLOW_THE_in_date_end_of_the_MW1247 = new BitSet( new long[]
   { 0x0000000000000600L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1259 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1281 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_date_natural1310 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_date_natural1330 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TOMORROW_in_date_natural1344 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_YESTERDAY_in_date_natural1358 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
