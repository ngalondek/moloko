// $ANTLR 3.3 Nov 30, 2010 12:45:30 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g 2011-11-01 15:28:07

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
    "DAYS", "WEEKS", "MONTHS", "YEARS", "OF", "DOT", "MINUS", "COLON",
    "DATE_SEP", "ON", "STs", "MINUS_A", "COMMA", "MONTH", "NEXT", "WEEKDAY",
    "IN", "AND", "END", "THE", "TODAY", "TONIGHT", "NEVER", "TOMORROW",
    "YESTERDAY", "DATE_TIME_SEPARATOR", "WS" };
   
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
   
   public static final int COLON = 15;
   
   public static final int DATE_SEP = 16;
   
   public static final int ON = 17;
   
   public static final int STs = 18;
   
   public static final int MINUS_A = 19;
   
   public static final int COMMA = 20;
   
   public static final int MONTH = 21;
   
   public static final int NEXT = 22;
   
   public static final int WEEKDAY = 23;
   
   public static final int IN = 24;
   
   public static final int AND = 25;
   
   public static final int END = 26;
   
   public static final int THE = 27;
   
   public static final int TODAY = 28;
   
   public static final int TONIGHT = 29;
   
   public static final int NEVER = 30;
   
   public static final int TOMORROW = 31;
   
   public static final int YESTERDAY = 32;
   
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
   
   
   
   @Override
   public String[] getTokenNames()
   {
      return DateParser.tokenNames;
   }
   
   
   
   @Override
   public String getGrammarFileName()
   {
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g";
   }
   
   
   
   public DateParser()
   {
      super( null );
   }
   
   
   
   @Override
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
   
   
   
   @Override
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
   
   
   
   @Override
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
            switch ( string.charAt( 1 ) )
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:158:1:
   // parseDate[MolokoCalendar cal, boolean clearTime] returns [ParseDateReturn result] : ( ( date_numeric[$cal] |
   // date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | NOW ) ;
   public final ParseDateReturn parseDate( MolokoCalendar cal, boolean clearTime ) throws RecognitionException
   {
      ParseDateReturn result = null;
      
      startDateParsing( cal );
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:170:4: ( (
         // ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal]
         // ) | NOW ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:170:6: ( (
         // date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
         // | NOW )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:170:6: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:171:8:
               // ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
               // date_natural[$cal] )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:171:8:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:171:12:
                     // date_numeric[$cal]
                     {
                        pushFollow( FOLLOW_date_numeric_in_parseDate119 );
                        date_numeric( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:172:12:
                     // date_on[$cal]
                     {
                        pushFollow( FOLLOW_date_on_in_parseDate142 );
                        date_on( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 3:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:173:12:
                     // date_in_X_YMWD[$cal]
                     {
                        pushFollow( FOLLOW_date_in_X_YMWD_in_parseDate167 );
                        date_in_X_YMWD( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 4:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:174:12:
                     // date_end_of_the_MW[$cal]
                     {
                        pushFollow( FOLLOW_date_end_of_the_MW_in_parseDate185 );
                        date_end_of_the_MW( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 5:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:175:12:
                     // date_natural[$cal]
                     {
                        pushFollow( FOLLOW_date_natural_in_parseDate199 );
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:183:9:
               // NOW
               {
                  match( input, NOW, FOLLOW_NOW_in_parseDate258 );
                  
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:193:1:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:206:4: (
         // (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:206:6: (a=
         // INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )?
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:206:6:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:206:9:
               // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_parseDateWithin318 );
                  
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:210:9:
               // n= NUM_STR
               {
                  n = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_parseDateWithin340 );
                  
                  amount = numberStringToNumber( ( n != null ? n.getText()
                                                            : null ) );
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:214:9:
               // A
               {
                  match( input, A, FOLLOW_A_in_parseDateWithin360 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:215:7: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:215:10:
               // DAYS
               {
                  match( input, DAYS, FOLLOW_DAYS_in_parseDateWithin372 );
                  
                  unit = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:219:10:
               // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_parseDateWithin394 );
                  
                  unit = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:223:10:
               // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_parseDateWithin416 );
                  
                  unit = Calendar.MONTH;
                  
               }
                  break;
               case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:227:10:
               // YEARS
               {
                  match( input, YEARS, FOLLOW_YEARS_in_parseDateWithin438 );
                  
                  unit = Calendar.YEAR;
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:232:7: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:232:8:
               // OF parseDate[retval.epochStart, false]
               {
                  match( input, OF, FOLLOW_OF_in_parseDateWithin467 );
                  pushFollow( FOLLOW_parseDate_in_parseDateWithin469 );
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:239:1:
   // date_numeric[MolokoCalendar cal] : pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON |
   // DATE_SEP ) (pt3= INT )? ;
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:246:4:
         // (pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:246:6: pt1=
         // INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )?
         {
            pt1 = (Token) match( input, INT, FOLLOW_INT_in_date_numeric514 );
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
            
            pt2 = (Token) match( input, INT, FOLLOW_INT_in_date_numeric546 );
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
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:254:6:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:255:9:
               // pt3= INT
               {
                  pt3 = (Token) match( input,
                                       INT,
                                       FOLLOW_INT_in_date_numeric588 );
                  
                  pt3Str = pt3.getText();
                  
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:265:1:
   // date_on[MolokoCalendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) ;
   public final void date_on( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:266:4: ( (
         // ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:266:6: ( ON
         // )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:266:6: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:266:6:
               // ON
               {
                  match( input, ON, FOLLOW_ON_in_date_on632 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:266:10:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:266:14:
               // date_on_Xst_of_M[$cal]
               {
                  pushFollow( FOLLOW_date_on_Xst_of_M_in_date_on639 );
                  date_on_Xst_of_M( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:267:14:
               // date_on_M_Xst[$cal]
               {
                  pushFollow( FOLLOW_date_on_M_Xst_in_date_on655 );
                  date_on_M_Xst( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:268:14:
               // date_on_weekday[$cal]
               {
                  pushFollow( FOLLOW_date_on_weekday_in_date_on674 );
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:271:1:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:277:4: (d=
         // INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:277:6: d=
         // INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
         {
            d = (Token) match( input, INT, FOLLOW_INT_in_date_on_Xst_of_M707 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:277:12:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:277:12:
               // STs
               {
                  match( input, STs, FOLLOW_STs_in_date_on_Xst_of_M709 );
                  
               }
                  break;
            
            }
            
            cal.set( Calendar.DAY_OF_MONTH,
                     Integer.parseInt( ( d != null ? d.getText() : null ) ) );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:281:6: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:281:7:
               // ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:281:7:
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
                                     FOLLOW_MONTH_in_date_on_Xst_of_M757 );
                  
                  parseTextMonth( cal, ( m != null ? m.getText() : null ) );
                  hasMonth = true;
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:287:7:
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
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:288:7:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:288:8:
                     // y= INT
                     {
                        y = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_date_on_Xst_of_M794 );
                        
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
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_date_on_M_Xst856 );
            
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
                  d = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst891 );
                  
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
                  y = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst939 );
                  
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:331:1:
   // date_on_weekday[MolokoCalendar cal] : ( NEXT )? wd= WEEKDAY ;
   public final void date_on_weekday( MolokoCalendar cal ) throws RecognitionException
   {
      Token wd = null;
      
      boolean nextWeek = false;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:336:4: ( (
         // NEXT )? wd= WEEKDAY )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:336:6: (
         // NEXT )? wd= WEEKDAY
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:336:6: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:336:7:
               // NEXT
               {
                  match( input, NEXT, FOLLOW_NEXT_in_date_on_weekday996 );
                  nextWeek = true;
                  
               }
                  break;
            
            }
            
            wd = (Token) match( input,
                                WEEKDAY,
                                FOLLOW_WEEKDAY_in_date_on_weekday1004 );
            
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:342:1:
   // date_in_X_YMWD[MolokoCalendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA )
   // date_in_X_YMWD_distance[$cal] )* ;
   public final void date_in_X_YMWD( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:343:4: ( (
         // IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:343:7: ( IN
         // )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:343:7: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:343:7:
               // IN
               {
                  match( input, IN, FOLLOW_IN_in_date_in_X_YMWD1027 );
                  
               }
                  break;
            
            }
            
            pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1042 );
            date_in_X_YMWD_distance( cal );
            
            state._fsp--;
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:344:7: (
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:344:8:
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
                     
                     pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1061 );
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:347:1:
   // date_in_X_YMWD_distance[MolokoCalendar cal] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
   public final void date_in_X_YMWD_distance( MolokoCalendar cal ) throws RecognitionException
   {
      Token a = null;
      
      int amount = -1;
      int calField = Calendar.YEAR;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:353:4: (
         // (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:353:6: (a=
         // NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:353:6:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:353:10:
               // a= NUM_STR
               {
                  a = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1098 );
                  amount = numberStringToNumber( ( a != null ? a.getText()
                                                            : null ) );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:354:10:
               // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_date_in_X_YMWD_distance1113 );
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:355:6: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:355:12:
               // YEARS
               {
                  match( input,
                         YEARS,
                         FOLLOW_YEARS_in_date_in_X_YMWD_distance1133 );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:356:12:
               // MONTHS
               {
                  match( input,
                         MONTHS,
                         FOLLOW_MONTHS_in_date_in_X_YMWD_distance1146 );
                  calField = Calendar.MONTH;
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:357:12:
               // WEEKS
               {
                  match( input,
                         WEEKS,
                         FOLLOW_WEEKS_in_date_in_X_YMWD_distance1162 );
                  calField = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:358:12:
               // DAYS
               {
                  match( input,
                         DAYS,
                         FOLLOW_DAYS_in_date_in_X_YMWD_distance1179 );
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:375:1:
   // date_end_of_the_MW[MolokoCalendar cal] : END ( OF )? ( THE )? ( WEEKS | MONTHS ) ;
   public final void date_end_of_the_MW( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:376:4: (
         // END ( OF )? ( THE )? ( WEEKS | MONTHS ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:376:6: END
         // ( OF )? ( THE )? ( WEEKS | MONTHS )
         {
            match( input, END, FOLLOW_END_in_date_end_of_the_MW1218 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:376:10:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:376:10:
               // OF
               {
                  match( input, OF, FOLLOW_OF_in_date_end_of_the_MW1220 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:376:14:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:376:14:
               // THE
               {
                  match( input, THE, FOLLOW_THE_in_date_end_of_the_MW1223 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:377:6: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:377:10:
               // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_date_end_of_the_MW1235 );
                  
                  rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:381:10:
               // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_date_end_of_the_MW1257 );
                  
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:387:1:
   // date_natural[MolokoCalendar cal] : ( ( TODAY | TONIGHT ) | NEVER | TOMORROW | YESTERDAY );
   public final void date_natural( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:388:4: ( (
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:388:6: (
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:391:6:
            // NEVER
            {
               match( input, NEVER, FOLLOW_NEVER_in_date_natural1306 );
               
               cal.setHasDate( false );
               cal.setHasTime( false );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:396:6:
            // TOMORROW
            {
               match( input, TOMORROW, FOLLOW_TOMORROW_in_date_natural1320 );
               
               cal.roll( Calendar.DAY_OF_YEAR, true );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:400:6:
            // YESTERDAY
            {
               match( input, YESTERDAY, FOLLOW_YESTERDAY_in_date_natural1334 );
               
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
   
   public static final BitSet FOLLOW_date_on_in_parseDate142 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_in_parseDate167 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_end_of_the_MW_in_parseDate185 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_natural_in_parseDate199 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NOW_in_parseDate258 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseDateWithin318 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_parseDateWithin340 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_A_in_parseDateWithin360 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseDateWithin372 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseDateWithin394 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseDateWithin416 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseDateWithin438 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_OF_in_parseDateWithin467 = new BitSet( new long[]
   { 0x00000001F5E20070L } );
   
   public static final BitSet FOLLOW_parseDate_in_parseDateWithin469 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric514 = new BitSet( new long[]
   { 0x000000000001E000L } );
   
   public static final BitSet FOLLOW_set_in_date_numeric516 = new BitSet( new long[]
   { 0x0000000000000020L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric546 = new BitSet( new long[]
   { 0x000000000001E000L } );
   
   public static final BitSet FOLLOW_set_in_date_numeric548 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric588 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_ON_in_date_on632 = new BitSet( new long[]
   { 0x0000000000E20020L } );
   
   public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on639 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_M_Xst_in_date_on655 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_weekday_in_date_on674 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M707 = new BitSet( new long[]
   { 0x00000000003C7002L } );
   
   public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M709 = new BitSet( new long[]
   { 0x0000000000387002L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M727 = new BitSet( new long[]
   { 0x0000000000200000L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M757 = new BitSet( new long[]
   { 0x0000000000006022L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M776 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M794 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst856 = new BitSet( new long[]
   { 0x0000000000106022L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst871 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst891 = new BitSet( new long[]
   { 0x00000000001C6000L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst909 = new BitSet( new long[]
   { 0x00000000001C6022L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst939 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEXT_in_date_on_weekday996 = new BitSet( new long[]
   { 0x0000000000800000L } );
   
   public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday1004 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_IN_in_date_in_X_YMWD1027 = new BitSet( new long[]
   { 0x0000000001000060L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1042 = new BitSet( new long[]
   { 0x0000000002100002L } );
   
   public static final BitSet FOLLOW_set_in_date_in_X_YMWD1052 = new BitSet( new long[]
   { 0x0000000001000060L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1061 = new BitSet( new long[]
   { 0x0000000002100002L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1098 = new BitSet( new long[]
   { 0x0000000000000F00L } );
   
   public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance1113 = new BitSet( new long[]
   { 0x0000000000000F00L } );
   
   public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance1133 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance1146 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance1162 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1179 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_END_in_date_end_of_the_MW1218 = new BitSet( new long[]
   { 0x0000000008001600L } );
   
   public static final BitSet FOLLOW_OF_in_date_end_of_the_MW1220 = new BitSet( new long[]
   { 0x0000000008000600L } );
   
   public static final BitSet FOLLOW_THE_in_date_end_of_the_MW1223 = new BitSet( new long[]
   { 0x0000000000000600L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1235 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1257 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_date_natural1286 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_date_natural1306 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TOMORROW_in_date_natural1320 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_YESTERDAY_in_date_natural1334 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
