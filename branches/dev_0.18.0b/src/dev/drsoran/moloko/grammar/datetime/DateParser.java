// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g 2013-03-03 19:55:47

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

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.LexerException;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class DateParser extends AbstractDateParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "A", "AND", "COLON", "COMMA",
    "DATE_SEP", "DATE_TIME_SEPARATOR", "DAYS", "DOT", "END", "IN", "INT",
    "MINUS", "MINUS_A", "MONTH", "MONTHS", "NEVER", "NEXT", "NOW", "NUM_STR",
    "OF", "ON", "STs", "THE", "TODAY", "TOMORROW", "TONIGHT", "WEEKDAY",
    "WEEKS", "WS", "YEARS", "YESTERDAY" };
   
   public static final int EOF = -1;
   
   public static final int A = 4;
   
   public static final int AND = 5;
   
   public static final int COLON = 6;
   
   public static final int COMMA = 7;
   
   public static final int DATE_SEP = 8;
   
   public static final int DATE_TIME_SEPARATOR = 9;
   
   public static final int DAYS = 10;
   
   public static final int DOT = 11;
   
   public static final int END = 12;
   
   public static final int IN = 13;
   
   public static final int INT = 14;
   
   public static final int MINUS = 15;
   
   public static final int MINUS_A = 16;
   
   public static final int MONTH = 17;
   
   public static final int MONTHS = 18;
   
   public static final int NEVER = 19;
   
   public static final int NEXT = 20;
   
   public static final int NOW = 21;
   
   public static final int NUM_STR = 22;
   
   public static final int OF = 23;
   
   public static final int ON = 24;
   
   public static final int STs = 25;
   
   public static final int THE = 26;
   
   public static final int TODAY = 27;
   
   public static final int TOMORROW = 28;
   
   public static final int TONIGHT = 29;
   
   public static final int WEEKDAY = 30;
   
   public static final int WEEKS = 31;
   
   public static final int WS = 32;
   
   public static final int YEARS = 33;
   
   public static final int YESTERDAY = 34;
   
   
   
   // delegates
   public AbstractDateParser[] getDelegates()
   {
      return new AbstractDateParser[] {};
   }
   
   
   
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
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g";
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
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:167:1:
   // parseDate[MolokoCalendar cal, boolean clearTime] returns [ParseDateReturn result] : ( ( date_numeric[$cal] |
   // date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | NOW | NEVER ) ;
   public final ParseDateReturn parseDate( MolokoCalendar cal, boolean clearTime ) throws RecognitionException
   {
      ParseDateReturn result = null;
      
      startDateParsing( cal );
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:176:4:
         // ( ( ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
         // date_natural[$cal] ) | NOW | NEVER ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:176:6:
         // ( ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
         // date_natural[$cal] ) | NOW | NEVER )
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:176:6:
            // ( ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
            // date_natural[$cal] ) | NOW | NEVER )
            int alt2 = 3;
            switch ( input.LA( 1 ) )
            {
               case END:
               case IN:
               case INT:
               case MONTH:
               case NEXT:
               case NUM_STR:
               case ON:
               case TODAY:
               case TOMORROW:
               case TONIGHT:
               case WEEKDAY:
               case YESTERDAY:
               {
                  alt2 = 1;
               }
                  break;
               case NOW:
               {
                  alt2 = 2;
               }
                  break;
               case NEVER:
               {
                  alt2 = 3;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        2,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
            switch ( alt2 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:177:8:
               // ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
               // date_natural[$cal] )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:177:8:
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
                           case COMMA:
                           case MINUS_A:
                           case MONTH:
                           case OF:
                           case STs:
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
                           case MONTHS:
                           case WEEKS:
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
                     case MONTH:
                     case NEXT:
                     case ON:
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
                     case TONIGHT:
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
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:177:12:
                     // date_numeric[$cal]
                     {
                        pushFollow( FOLLOW_date_numeric_in_parseDate119 );
                        date_numeric( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 2:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:178:12:
                     // date_on[$cal]
                     {
                        pushFollow( FOLLOW_date_on_in_parseDate139 );
                        date_on( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 3:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:179:12:
                     // date_in_X_YMWD[$cal]
                     {
                        pushFollow( FOLLOW_date_in_X_YMWD_in_parseDate164 );
                        date_in_X_YMWD( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 4:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:180:12:
                     // date_end_of_the_MW[$cal]
                     {
                        pushFollow( FOLLOW_date_end_of_the_MW_in_parseDate182 );
                        date_end_of_the_MW( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 5:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:181:12:
                     // date_natural[$cal]
                     {
                        pushFollow( FOLLOW_date_natural_in_parseDate196 );
                        date_natural( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                  
                  }
                  
                  cal.setHasDate( isSuccess() );
                  
                  if ( clearTime )
                     cal.setHasTime( false );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:191:9:
               // NOW
               {
                  match( input, NOW, FOLLOW_NOW_in_parseDate255 );
                  
                  cal.setHasDate( true );
                  // In case of NOW we do not respect the clearTime Parameter
                  // cause NOW has always a time.
                  cal.setTimeInMillis( System.currentTimeMillis() );
                  cal.setHasTime( true );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:199:9:
               // NEVER
               {
                  match( input, NEVER, FOLLOW_NEVER_in_parseDate273 );
                  
                  cal.setHasDate( false );
                  cal.setHasTime( false );
                  
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
         // do for sure before leaving
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
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:207:1:
   // parseDateWithin[boolean past] returns [MolokoCalendar epochStart, MolokoCalendar epochEnd] : (a= INT |n= NUM_STR |
   // A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? ;
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:220:4:
         // ( (a= INT |n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:220:6:
         // (a= INT |n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )?
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:220:6:
            // (a= INT |n= NUM_STR | A )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:220:9:
               // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_parseDateWithin333 );
                  
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:224:9:
               // n= NUM_STR
               {
                  n = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_parseDateWithin355 );
                  
                  amount = numberStringToNumber( ( n != null ? n.getText()
                                                            : null ) );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:228:9:
               // A
               {
                  match( input, A, FOLLOW_A_in_parseDateWithin375 );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:229:7:
            // ( DAYS | WEEKS | MONTHS | YEARS )?
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:229:10:
               // DAYS
               {
                  match( input, DAYS, FOLLOW_DAYS_in_parseDateWithin387 );
                  
                  unit = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:233:10:
               // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_parseDateWithin409 );
                  
                  unit = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:237:10:
               // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_parseDateWithin431 );
                  
                  unit = Calendar.MONTH;
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:241:10:
               // YEARS
               {
                  match( input, YEARS, FOLLOW_YEARS_in_parseDateWithin453 );
                  
                  unit = Calendar.YEAR;
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:246:7:
            // ( OF parseDate[retval.epochStart, false] )?
            int alt5 = 2;
            int LA5_0 = input.LA( 1 );
            
            if ( ( LA5_0 == OF ) )
            {
               alt5 = 1;
            }
            switch ( alt5 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:246:8:
               // OF parseDate[retval.epochStart, false]
               {
                  match( input, OF, FOLLOW_OF_in_parseDateWithin482 );
                  
                  pushFollow( FOLLOW_parseDate_in_parseDateWithin484 );
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
         // do for sure before leaving
      }
      return retval;
   }
   
   
   
   // $ANTLR end "parseDateWithin"
   
   // $ANTLR start "date_numeric"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:253:1:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:260:4:
         // (pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( ( DOT | MINUS | DATE_SEP ) (pt3= INT )? )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:260:6:
         // pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( ( DOT | MINUS | DATE_SEP ) (pt3= INT )? )?
         {
            pt1 = (Token) match( input, INT, FOLLOW_INT_in_date_numeric529 );
            
            if ( input.LA( 1 ) == DATE_SEP || input.LA( 1 ) == DOT
               || input.LA( 1 ) == MINUS )
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
            
            pt2 = (Token) match( input, INT, FOLLOW_INT_in_date_numeric557 );
            
            pt2Str = pt2.getText();
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:268:6:
            // ( ( DOT | MINUS | DATE_SEP ) (pt3= INT )? )?
            int alt7 = 2;
            int LA7_0 = input.LA( 1 );
            
            if ( ( LA7_0 == DATE_SEP || LA7_0 == DOT || LA7_0 == MINUS ) )
            {
               alt7 = 1;
            }
            switch ( alt7 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:269:9:
               // ( DOT | MINUS | DATE_SEP ) (pt3= INT )?
               {
                  if ( input.LA( 1 ) == DATE_SEP || input.LA( 1 ) == DOT
                     || input.LA( 1 ) == MINUS )
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
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:270:9:
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
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:271:12:
                     // pt3= INT
                     {
                        pt3 = (Token) match( input,
                                             INT,
                                             FOLLOW_INT_in_date_numeric616 );
                        
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_numeric"
   
   // $ANTLR start "date_on"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:282:1:
   // date_on[MolokoCalendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) ;
   public final void date_on( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:283:4:
         // ( ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:283:6:
         // ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:283:6:
            // ( ON )?
            int alt8 = 2;
            int LA8_0 = input.LA( 1 );
            
            if ( ( LA8_0 == ON ) )
            {
               alt8 = 1;
            }
            switch ( alt8 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:283:6:
               // ON
               {
                  match( input, ON, FOLLOW_ON_in_date_on674 );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:283:10:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:283:14:
               // date_on_Xst_of_M[$cal]
               {
                  pushFollow( FOLLOW_date_on_Xst_of_M_in_date_on681 );
                  date_on_Xst_of_M( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:284:14:
               // date_on_M_Xst[$cal]
               {
                  pushFollow( FOLLOW_date_on_M_Xst_in_date_on697 );
                  date_on_M_Xst( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:285:14:
               // date_on_weekday[$cal]
               {
                  pushFollow( FOLLOW_date_on_weekday_in_date_on716 );
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on"
   
   // $ANTLR start "date_on_Xst_of_M"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:288:1:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:294:4:
         // (d= INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:294:6:
         // d= INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
         {
            d = (Token) match( input, INT, FOLLOW_INT_in_date_on_Xst_of_M749 );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:294:12:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:294:12:
               // STs
               {
                  match( input, STs, FOLLOW_STs_in_date_on_Xst_of_M751 );
                  
               }
                  break;
            
            }
            
            cal.set( Calendar.DAY_OF_MONTH,
                     Integer.parseInt( ( d != null ? d.getText() : null ) ) );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:298:6:
            // ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            int alt14 = 2;
            int LA14_0 = input.LA( 1 );
            
            if ( ( LA14_0 == COMMA || LA14_0 == DOT
               || ( LA14_0 >= MINUS && LA14_0 <= MONTH ) || LA14_0 == OF ) )
            {
               alt14 = 1;
            }
            switch ( alt14 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:298:7:
               // ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:298:7:
                  // ( OF | MINUS_A | MINUS | COMMA | DOT )?
                  int alt11 = 2;
                  int LA11_0 = input.LA( 1 );
                  
                  if ( ( LA11_0 == COMMA || LA11_0 == DOT
                     || ( LA11_0 >= MINUS && LA11_0 <= MINUS_A ) || LA11_0 == OF ) )
                  {
                     alt11 = 1;
                  }
                  switch ( alt11 )
                  {
                     case 1:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
                     {
                        if ( input.LA( 1 ) == COMMA
                           || input.LA( 1 ) == DOT
                           || ( input.LA( 1 ) >= MINUS && input.LA( 1 ) <= MINUS_A )
                           || input.LA( 1 ) == OF )
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
                                     FOLLOW_MONTH_in_date_on_Xst_of_M799 );
                  
                  parseTextMonth( cal, ( m != null ? m.getText() : null ) );
                  hasMonth = true;
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:304:7:
                  // ( MINUS | DOT )?
                  int alt12 = 2;
                  int LA12_0 = input.LA( 1 );
                  
                  if ( ( LA12_0 == DOT || LA12_0 == MINUS ) )
                  {
                     alt12 = 1;
                  }
                  switch ( alt12 )
                  {
                     case 1:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
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
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:305:7:
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
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:305:8:
                     // y= INT
                     {
                        y = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_date_on_Xst_of_M836 );
                        
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on_Xst_of_M"
   
   // $ANTLR start "date_on_M_Xst"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:319:1:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:324:4:
         // (m= MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:324:6:
         // m= MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )?
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_date_on_M_Xst898 );
            
            parseTextMonth( cal, ( m != null ? m.getText() : null ) );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:328:5:
            // ( MINUS | COMMA | DOT )?
            int alt15 = 2;
            int LA15_0 = input.LA( 1 );
            
            if ( ( LA15_0 == COMMA || LA15_0 == DOT || LA15_0 == MINUS ) )
            {
               alt15 = 1;
            }
            switch ( alt15 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
               {
                  if ( input.LA( 1 ) == COMMA || input.LA( 1 ) == DOT
                     || input.LA( 1 ) == MINUS )
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
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:329:5:
            // (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )?
            int alt17 = 2;
            int LA17_0 = input.LA( 1 );
            
            if ( ( LA17_0 == INT ) )
            {
               int LA17_1 = input.LA( 2 );
               
               if ( ( LA17_1 == COMMA || LA17_1 == DOT
                  || ( LA17_1 >= MINUS && LA17_1 <= MINUS_A ) || LA17_1 == STs ) )
               {
                  alt17 = 1;
               }
            }
            switch ( alt17 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:329:6:
               // d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+
               {
                  d = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst933 );
                  
                  cal.set( Calendar.DAY_OF_MONTH,
                           Integer.parseInt( ( d != null ? d.getText() : null ) ) );
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:333:8:
                  // ( STs | MINUS_A | MINUS | COMMA | DOT )+
                  int cnt16 = 0;
                  loop16: do
                  {
                     int alt16 = 2;
                     int LA16_0 = input.LA( 1 );
                     
                     if ( ( LA16_0 == COMMA || LA16_0 == DOT
                        || ( LA16_0 >= MINUS && LA16_0 <= MINUS_A ) || LA16_0 == STs ) )
                     {
                        alt16 = 1;
                     }
                     
                     switch ( alt16 )
                     {
                        case 1:
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
                        {
                           if ( input.LA( 1 ) == COMMA
                              || input.LA( 1 ) == DOT
                              || ( input.LA( 1 ) >= MINUS && input.LA( 1 ) <= MINUS_A )
                              || input.LA( 1 ) == STs )
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
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:334:5:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:334:6:
               // y= INT
               {
                  y = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst981 );
                  
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on_M_Xst"
   
   // $ANTLR start "date_on_weekday"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:348:1:
   // date_on_weekday[MolokoCalendar cal] : ( NEXT )? wd= WEEKDAY ;
   public final void date_on_weekday( MolokoCalendar cal ) throws RecognitionException
   {
      Token wd = null;
      
      boolean nextWeek = false;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:353:4:
         // ( ( NEXT )? wd= WEEKDAY )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:353:6:
         // ( NEXT )? wd= WEEKDAY
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:353:6:
            // ( NEXT )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == NEXT ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:353:7:
               // NEXT
               {
                  match( input, NEXT, FOLLOW_NEXT_in_date_on_weekday1038 );
                  
                  nextWeek = true;
                  
               }
                  break;
            
            }
            
            wd = (Token) match( input,
                                WEEKDAY,
                                FOLLOW_WEEKDAY_in_date_on_weekday1046 );
            
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on_weekday"
   
   // $ANTLR start "date_in_X_YMWD"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:359:1:
   // date_in_X_YMWD[MolokoCalendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA )
   // date_in_X_YMWD_distance[$cal] )* ;
   public final void date_in_X_YMWD( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:360:4:
         // ( ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:360:7:
         // ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:360:7:
            // ( IN )?
            int alt20 = 2;
            int LA20_0 = input.LA( 1 );
            
            if ( ( LA20_0 == IN ) )
            {
               alt20 = 1;
            }
            switch ( alt20 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:360:7:
               // IN
               {
                  match( input, IN, FOLLOW_IN_in_date_in_X_YMWD1069 );
                  
               }
                  break;
            
            }
            
            pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1084 );
            date_in_X_YMWD_distance( cal );
            
            state._fsp--;
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:361:7:
            // ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop21: do
            {
               int alt21 = 2;
               int LA21_0 = input.LA( 1 );
               
               if ( ( LA21_0 == AND || LA21_0 == COMMA ) )
               {
                  alt21 = 1;
               }
               
               switch ( alt21 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:361:8:
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
                     
                     pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1103 );
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_in_X_YMWD"
   
   // $ANTLR start "date_in_X_YMWD_distance"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:364:1:
   // date_in_X_YMWD_distance[MolokoCalendar cal] : (a= NUM_STR |a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
   public final void date_in_X_YMWD_distance( MolokoCalendar cal ) throws RecognitionException
   {
      Token a = null;
      
      int amount = -1;
      int calField = Calendar.YEAR;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:370:4:
         // ( (a= NUM_STR |a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:370:6:
         // (a= NUM_STR |a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:370:6:
            // (a= NUM_STR |a= INT )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:370:10:
               // a= NUM_STR
               {
                  a = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1140 );
                  
                  amount = numberStringToNumber( ( a != null ? a.getText()
                                                            : null ) );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:371:10:
               // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_date_in_X_YMWD_distance1155 );
                  
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:372:6:
            // ( YEARS | MONTHS | WEEKS | DAYS )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:372:12:
               // YEARS
               {
                  match( input,
                         YEARS,
                         FOLLOW_YEARS_in_date_in_X_YMWD_distance1175 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:373:12:
               // MONTHS
               {
                  match( input,
                         MONTHS,
                         FOLLOW_MONTHS_in_date_in_X_YMWD_distance1188 );
                  
                  calField = Calendar.MONTH;
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:374:12:
               // WEEKS
               {
                  match( input,
                         WEEKS,
                         FOLLOW_WEEKS_in_date_in_X_YMWD_distance1204 );
                  
                  calField = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:375:12:
               // DAYS
               {
                  match( input,
                         DAYS,
                         FOLLOW_DAYS_in_date_in_X_YMWD_distance1221 );
                  
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_in_X_YMWD_distance"
   
   // $ANTLR start "date_end_of_the_MW"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:392:1:
   // date_end_of_the_MW[MolokoCalendar cal] : END ( OF )? ( THE )? ( WEEKS | MONTHS ) ;
   public final void date_end_of_the_MW( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:393:4:
         // ( END ( OF )? ( THE )? ( WEEKS | MONTHS ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:393:6:
         // END ( OF )? ( THE )? ( WEEKS | MONTHS )
         {
            match( input, END, FOLLOW_END_in_date_end_of_the_MW1260 );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:393:10:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:393:10:
               // OF
               {
                  match( input, OF, FOLLOW_OF_in_date_end_of_the_MW1262 );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:393:14:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:393:14:
               // THE
               {
                  match( input, THE, FOLLOW_THE_in_date_end_of_the_MW1265 );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:394:6:
            // ( WEEKS | MONTHS )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:394:10:
               // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_date_end_of_the_MW1277 );
                  
                  rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:398:10:
               // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_date_end_of_the_MW1299 );
                  
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_end_of_the_MW"
   
   // $ANTLR start "date_natural"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:404:1:
   // date_natural[MolokoCalendar cal] : ( ( TODAY | TONIGHT ) | TOMORROW | YESTERDAY );
   public final void date_natural( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:405:4:
         // ( ( TODAY | TONIGHT ) | TOMORROW | YESTERDAY )
         int alt27 = 3;
         switch ( input.LA( 1 ) )
         {
            case TODAY:
            case TONIGHT:
            {
               alt27 = 1;
            }
               break;
            case TOMORROW:
            {
               alt27 = 2;
            }
               break;
            case YESTERDAY:
            {
               alt27 = 3;
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:405:6:
            // ( TODAY | TONIGHT )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:408:6:
            // TOMORROW
            {
               match( input, TOMORROW, FOLLOW_TOMORROW_in_date_natural1350 );
               
               cal.add( Calendar.DAY_OF_YEAR, 1 );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:412:6:
            // YESTERDAY
            {
               match( input, YESTERDAY, FOLLOW_YESTERDAY_in_date_natural1364 );
               
               cal.add( Calendar.DAY_OF_YEAR, -1 );
               
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
         // do for sure before leaving
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
   
   public static final BitSet FOLLOW_NEVER_in_parseDate273 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseDateWithin333 = new BitSet( new long[]
   { 0x0000000280840402L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_parseDateWithin355 = new BitSet( new long[]
   { 0x0000000280840402L } );
   
   public static final BitSet FOLLOW_A_in_parseDateWithin375 = new BitSet( new long[]
   { 0x0000000280840402L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseDateWithin387 = new BitSet( new long[]
   { 0x0000000000800002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseDateWithin409 = new BitSet( new long[]
   { 0x0000000000800002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseDateWithin431 = new BitSet( new long[]
   { 0x0000000000800002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseDateWithin453 = new BitSet( new long[]
   { 0x0000000000800002L } );
   
   public static final BitSet FOLLOW_OF_in_parseDateWithin482 = new BitSet( new long[]
   { 0x00000004797A7000L } );
   
   public static final BitSet FOLLOW_parseDate_in_parseDateWithin484 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric529 = new BitSet( new long[]
   { 0x0000000000008900L } );
   
   public static final BitSet FOLLOW_set_in_date_numeric531 = new BitSet( new long[]
   { 0x0000000000004000L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric557 = new BitSet( new long[]
   { 0x0000000000008902L } );
   
   public static final BitSet FOLLOW_set_in_date_numeric581 = new BitSet( new long[]
   { 0x0000000000004002L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric616 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_ON_in_date_on674 = new BitSet( new long[]
   { 0x0000000040124000L } );
   
   public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on681 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_M_Xst_in_date_on697 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_weekday_in_date_on716 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M749 = new BitSet( new long[]
   { 0x0000000002838882L } );
   
   public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M751 = new BitSet( new long[]
   { 0x0000000000838882L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M799 = new BitSet( new long[]
   { 0x000000000000C802L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M836 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst898 = new BitSet( new long[]
   { 0x000000000000C882L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst933 = new BitSet( new long[]
   { 0x0000000002018880L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst981 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEXT_in_date_on_weekday1038 = new BitSet( new long[]
   { 0x0000000040000000L } );
   
   public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday1046 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_IN_in_date_in_X_YMWD1069 = new BitSet( new long[]
   { 0x0000000000404000L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1084 = new BitSet( new long[]
   { 0x00000000000000A2L } );
   
   public static final BitSet FOLLOW_set_in_date_in_X_YMWD1094 = new BitSet( new long[]
   { 0x0000000000404000L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1103 = new BitSet( new long[]
   { 0x00000000000000A2L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1140 = new BitSet( new long[]
   { 0x0000000280040400L } );
   
   public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance1155 = new BitSet( new long[]
   { 0x0000000280040400L } );
   
   public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance1175 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance1188 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance1204 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1221 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_END_in_date_end_of_the_MW1260 = new BitSet( new long[]
   { 0x0000000084840000L } );
   
   public static final BitSet FOLLOW_OF_in_date_end_of_the_MW1262 = new BitSet( new long[]
   { 0x0000000084040000L } );
   
   public static final BitSet FOLLOW_THE_in_date_end_of_the_MW1265 = new BitSet( new long[]
   { 0x0000000080040000L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1277 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1299 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_date_natural1328 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TOMORROW_in_date_natural1350 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_YESTERDAY_in_date_natural1364 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
