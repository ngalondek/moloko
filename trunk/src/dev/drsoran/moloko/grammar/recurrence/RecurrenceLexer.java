// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g 2013-03-25 10:59:04

package dev.drsoran.moloko.grammar.recurrence;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

import dev.drsoran.moloko.grammar.LexerException;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class RecurrenceLexer extends Lexer
{
   public static final int EOF = -1;
   
   public static final int AFTER = 4;
   
   public static final int AND = 5;
   
   public static final int BIWEEKLY = 6;
   
   public static final int COMMA = 7;
   
   public static final int DAILY = 8;
   
   public static final int DAYS = 9;
   
   public static final int DOT = 10;
   
   public static final int EVERY = 11;
   
   public static final int FIFTH = 12;
   
   public static final int FIRST = 13;
   
   public static final int FOR = 14;
   
   public static final int FOURTH = 15;
   
   public static final int FRIDAY = 16;
   
   public static final int IN = 17;
   
   public static final int INT = 18;
   
   public static final int LAST = 19;
   
   public static final int MINUS = 20;
   
   public static final int MONDAY = 21;
   
   public static final int MONTH = 22;
   
   public static final int MONTHS = 23;
   
   public static final int NUMBER = 24;
   
   public static final int NUM_EIGHT = 25;
   
   public static final int NUM_FIVE = 26;
   
   public static final int NUM_FOUR = 27;
   
   public static final int NUM_NINE = 28;
   
   public static final int NUM_ONE = 29;
   
   public static final int NUM_SEVEN = 30;
   
   public static final int NUM_SIX = 31;
   
   public static final int NUM_TEN = 32;
   
   public static final int NUM_THREE = 33;
   
   public static final int NUM_TWO = 34;
   
   public static final int OF = 35;
   
   public static final int ON = 36;
   
   public static final int OTHER = 37;
   
   public static final int SATURDAY = 38;
   
   public static final int SECOND = 39;
   
   public static final int STRING = 40;
   
   public static final int ST_S = 41;
   
   public static final int SUNDAY = 42;
   
   public static final int THE = 43;
   
   public static final int THIRD = 44;
   
   public static final int THURSDAY = 45;
   
   public static final int TIMES = 46;
   
   public static final int TUESDAY = 47;
   
   public static final int UNTIL = 48;
   
   public static final int WEDNESDAY = 49;
   
   public static final int WEEKDAY_LIT = 50;
   
   public static final int WEEKEND = 51;
   
   public static final int WEEKS = 52;
   
   public static final int WS = 53;
   
   public static final int YEARS = 54;
   
   
   
   @Override
   public void reportError( RecognitionException e )
   {
      throw new LexerException( e );
   }
   
   
   
   // delegates
   // delegators
   public Lexer[] getDelegates()
   {
      return new Lexer[] {};
   }
   
   
   
   public RecurrenceLexer()
   {
   }
   
   
   
   public RecurrenceLexer( CharStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   
   
   
   public RecurrenceLexer( CharStream input, RecognizerSharedState state )
   {
      super( input, state );
   }
   
   
   
   public String getGrammarFileName()
   {
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g";
   }
   
   
   
   // $ANTLR start "EVERY"
   public final void mEVERY() throws RecognitionException
   {
      try
      {
         int _type = EVERY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:298:15:
         // ( 'every' | 'each' )
         int alt1 = 2;
         int LA1_0 = input.LA( 1 );
         
         if ( ( LA1_0 == 'e' ) )
         {
            int LA1_1 = input.LA( 2 );
            
            if ( ( LA1_1 == 'v' ) )
            {
               alt1 = 1;
            }
            else if ( ( LA1_1 == 'a' ) )
            {
               alt1 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     1,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  1,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt1 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:298:17:
            // 'every'
            {
               match( "every" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:298:27:
            // 'each'
            {
               match( "each" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "EVERY"
   
   // $ANTLR start "AFTER"
   public final void mAFTER() throws RecognitionException
   {
      try
      {
         int _type = AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:300:15:
         // ( 'after' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:300:17:
         // 'after'
         {
            match( "after" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "AFTER"
   
   // $ANTLR start "BIWEEKLY"
   public final void mBIWEEKLY() throws RecognitionException
   {
      try
      {
         int _type = BIWEEKLY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:302:15:
         // ( 'fortnight' | 'biweekly' )
         int alt2 = 2;
         int LA2_0 = input.LA( 1 );
         
         if ( ( LA2_0 == 'f' ) )
         {
            alt2 = 1;
         }
         else if ( ( LA2_0 == 'b' ) )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:302:17:
            // 'fortnight'
            {
               match( "fortnight" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:302:31:
            // 'biweekly'
            {
               match( "biweekly" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "BIWEEKLY"
   
   // $ANTLR start "YEARS"
   public final void mYEARS() throws RecognitionException
   {
      try
      {
         int _type = YEARS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:304:15:
         // ( 'years' | 'year' | 'yrs' | 'yr' )
         int alt3 = 4;
         int LA3_0 = input.LA( 1 );
         
         if ( ( LA3_0 == 'y' ) )
         {
            int LA3_1 = input.LA( 2 );
            
            if ( ( LA3_1 == 'e' ) )
            {
               int LA3_2 = input.LA( 3 );
               
               if ( ( LA3_2 == 'a' ) )
               {
                  int LA3_4 = input.LA( 4 );
                  
                  if ( ( LA3_4 == 'r' ) )
                  {
                     int LA3_7 = input.LA( 5 );
                     
                     if ( ( LA3_7 == 's' ) )
                     {
                        alt3 = 1;
                     }
                     else
                     {
                        alt3 = 2;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           3,
                                                                           4,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        3,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else if ( ( LA3_1 == 'r' ) )
            {
               int LA3_3 = input.LA( 3 );
               
               if ( ( LA3_3 == 's' ) )
               {
                  alt3 = 3;
               }
               else
               {
                  alt3 = 4;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     3,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  3,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt3 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:304:17:
            // 'years'
            {
               match( "years" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:304:27:
            // 'year'
            {
               match( "year" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:304:36:
            // 'yrs'
            {
               match( "yrs" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:304:44:
            // 'yr'
            {
               match( "yr" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "YEARS"
   
   // $ANTLR start "MONTHS"
   public final void mMONTHS() throws RecognitionException
   {
      try
      {
         int _type = MONTHS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:15:
         // ( 'months' | 'month' | 'mons' | 'mon' )
         int alt4 = 4;
         int LA4_0 = input.LA( 1 );
         
         if ( ( LA4_0 == 'm' ) )
         {
            int LA4_1 = input.LA( 2 );
            
            if ( ( LA4_1 == 'o' ) )
            {
               int LA4_2 = input.LA( 3 );
               
               if ( ( LA4_2 == 'n' ) )
               {
                  switch ( input.LA( 4 ) )
                  {
                     case 't':
                     {
                        int LA4_4 = input.LA( 5 );
                        
                        if ( ( LA4_4 == 'h' ) )
                        {
                           int LA4_7 = input.LA( 6 );
                           
                           if ( ( LA4_7 == 's' ) )
                           {
                              alt4 = 1;
                           }
                           else
                           {
                              alt4 = 2;
                           }
                        }
                        else
                        {
                           NoViableAltException nvae = new NoViableAltException( "",
                                                                                 4,
                                                                                 4,
                                                                                 input );
                           
                           throw nvae;
                           
                        }
                     }
                        break;
                     case 's':
                     {
                        alt4 = 3;
                     }
                        break;
                     default :
                        alt4 = 4;
                  }
                  
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        4,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     4,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  4,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt4 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:17:
            // 'months'
            {
               match( "months" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:28:
            // 'month'
            {
               match( "month" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:38:
            // 'mons'
            {
               match( "mons" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:306:47:
            // 'mon'
            {
               match( "mon" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "MONTHS"
   
   // $ANTLR start "WEEKS"
   public final void mWEEKS() throws RecognitionException
   {
      try
      {
         int _type = WEEKS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:308:15:
         // ( 'weeks' | 'week' | 'wks' | 'wk' )
         int alt5 = 4;
         int LA5_0 = input.LA( 1 );
         
         if ( ( LA5_0 == 'w' ) )
         {
            int LA5_1 = input.LA( 2 );
            
            if ( ( LA5_1 == 'e' ) )
            {
               int LA5_2 = input.LA( 3 );
               
               if ( ( LA5_2 == 'e' ) )
               {
                  int LA5_4 = input.LA( 4 );
                  
                  if ( ( LA5_4 == 'k' ) )
                  {
                     int LA5_7 = input.LA( 5 );
                     
                     if ( ( LA5_7 == 's' ) )
                     {
                        alt5 = 1;
                     }
                     else
                     {
                        alt5 = 2;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           5,
                                                                           4,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        5,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else if ( ( LA5_1 == 'k' ) )
            {
               int LA5_3 = input.LA( 3 );
               
               if ( ( LA5_3 == 's' ) )
               {
                  alt5 = 3;
               }
               else
               {
                  alt5 = 4;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     5,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  5,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt5 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:308:17:
            // 'weeks'
            {
               match( "weeks" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:308:27:
            // 'week'
            {
               match( "week" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:308:36:
            // 'wks'
            {
               match( "wks" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:308:44:
            // 'wk'
            {
               match( "wk" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "WEEKS"
   
   // $ANTLR start "DAYS"
   public final void mDAYS() throws RecognitionException
   {
      try
      {
         int _type = DAYS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:310:15:
         // ( 'days' | 'day' )
         int alt6 = 2;
         int LA6_0 = input.LA( 1 );
         
         if ( ( LA6_0 == 'd' ) )
         {
            int LA6_1 = input.LA( 2 );
            
            if ( ( LA6_1 == 'a' ) )
            {
               int LA6_2 = input.LA( 3 );
               
               if ( ( LA6_2 == 'y' ) )
               {
                  int LA6_3 = input.LA( 4 );
                  
                  if ( ( LA6_3 == 's' ) )
                  {
                     alt6 = 1;
                  }
                  else
                  {
                     alt6 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        6,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     6,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  6,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt6 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:310:17:
            // 'days'
            {
               match( "days" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:310:26:
            // 'day'
            {
               match( "day" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "DAYS"
   
   // $ANTLR start "DAILY"
   public final void mDAILY() throws RecognitionException
   {
      try
      {
         int _type = DAILY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:312:15:
         // ( 'daily' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:312:17:
         // 'daily'
         {
            match( "daily" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "DAILY"
   
   // $ANTLR start "MONTH"
   public final void mMONTH() throws RecognitionException
   {
      try
      {
         int _type = MONTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:15:
         // ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' |
         // 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' |
         // 'december' | 'dec' )
         int alt7 = 24;
         switch ( input.LA( 1 ) )
         {
            case 'j':
            {
               int LA7_1 = input.LA( 2 );
               
               if ( ( LA7_1 == 'a' ) )
               {
                  int LA7_9 = input.LA( 3 );
                  
                  if ( ( LA7_9 == 'n' ) )
                  {
                     int LA7_19 = input.LA( 4 );
                     
                     if ( ( LA7_19 == 'u' ) )
                     {
                        alt7 = 1;
                     }
                     else
                     {
                        alt7 = 2;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           9,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else if ( ( LA7_1 == 'u' ) )
               {
                  int LA7_10 = input.LA( 3 );
                  
                  if ( ( LA7_10 == 'n' ) )
                  {
                     int LA7_20 = input.LA( 4 );
                     
                     if ( ( LA7_20 == 'e' ) )
                     {
                        alt7 = 10;
                     }
                     else
                     {
                        alt7 = 11;
                     }
                  }
                  else if ( ( LA7_10 == 'l' ) )
                  {
                     int LA7_21 = input.LA( 4 );
                     
                     if ( ( LA7_21 == 'y' ) )
                     {
                        alt7 = 12;
                     }
                     else
                     {
                        alt7 = 13;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           10,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        7,
                                                                        1,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'f':
            {
               int LA7_2 = input.LA( 2 );
               
               if ( ( LA7_2 == 'e' ) )
               {
                  int LA7_11 = input.LA( 3 );
                  
                  if ( ( LA7_11 == 'b' ) )
                  {
                     int LA7_22 = input.LA( 4 );
                     
                     if ( ( LA7_22 == 'r' ) )
                     {
                        alt7 = 3;
                     }
                     else
                     {
                        alt7 = 4;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           11,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        7,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'm':
            {
               int LA7_3 = input.LA( 2 );
               
               if ( ( LA7_3 == 'a' ) )
               {
                  int LA7_12 = input.LA( 3 );
                  
                  if ( ( LA7_12 == 'r' ) )
                  {
                     int LA7_23 = input.LA( 4 );
                     
                     if ( ( LA7_23 == 'c' ) )
                     {
                        alt7 = 5;
                     }
                     else
                     {
                        alt7 = 6;
                     }
                  }
                  else if ( ( LA7_12 == 'y' ) )
                  {
                     alt7 = 9;
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           12,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        7,
                                                                        3,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'a':
            {
               int LA7_4 = input.LA( 2 );
               
               if ( ( LA7_4 == 'p' ) )
               {
                  int LA7_13 = input.LA( 3 );
                  
                  if ( ( LA7_13 == 'r' ) )
                  {
                     int LA7_25 = input.LA( 4 );
                     
                     if ( ( LA7_25 == 'i' ) )
                     {
                        alt7 = 7;
                     }
                     else
                     {
                        alt7 = 8;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           13,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else if ( ( LA7_4 == 'u' ) )
               {
                  int LA7_14 = input.LA( 3 );
                  
                  if ( ( LA7_14 == 'g' ) )
                  {
                     int LA7_26 = input.LA( 4 );
                     
                     if ( ( LA7_26 == 'u' ) )
                     {
                        alt7 = 14;
                     }
                     else
                     {
                        alt7 = 15;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           14,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        7,
                                                                        4,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 's':
            {
               int LA7_5 = input.LA( 2 );
               
               if ( ( LA7_5 == 'e' ) )
               {
                  int LA7_15 = input.LA( 3 );
                  
                  if ( ( LA7_15 == 'p' ) )
                  {
                     int LA7_27 = input.LA( 4 );
                     
                     if ( ( LA7_27 == 't' ) )
                     {
                        int LA7_45 = input.LA( 5 );
                        
                        if ( ( LA7_45 == 'e' ) )
                        {
                           alt7 = 16;
                        }
                        else
                        {
                           alt7 = 17;
                        }
                     }
                     else
                     {
                        alt7 = 18;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           15,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        7,
                                                                        5,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'o':
            {
               int LA7_6 = input.LA( 2 );
               
               if ( ( LA7_6 == 'c' ) )
               {
                  int LA7_16 = input.LA( 3 );
                  
                  if ( ( LA7_16 == 't' ) )
                  {
                     int LA7_28 = input.LA( 4 );
                     
                     if ( ( LA7_28 == 'o' ) )
                     {
                        alt7 = 19;
                     }
                     else
                     {
                        alt7 = 20;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           16,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        7,
                                                                        6,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'n':
            {
               int LA7_7 = input.LA( 2 );
               
               if ( ( LA7_7 == 'o' ) )
               {
                  int LA7_17 = input.LA( 3 );
                  
                  if ( ( LA7_17 == 'v' ) )
                  {
                     int LA7_29 = input.LA( 4 );
                     
                     if ( ( LA7_29 == 'e' ) )
                     {
                        alt7 = 21;
                     }
                     else
                     {
                        alt7 = 22;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           17,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        7,
                                                                        7,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'd':
            {
               int LA7_8 = input.LA( 2 );
               
               if ( ( LA7_8 == 'e' ) )
               {
                  int LA7_18 = input.LA( 3 );
                  
                  if ( ( LA7_18 == 'c' ) )
                  {
                     int LA7_30 = input.LA( 4 );
                     
                     if ( ( LA7_30 == 'e' ) )
                     {
                        alt7 = 23;
                     }
                     else
                     {
                        alt7 = 24;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           18,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        7,
                                                                        8,
                                                                        input );
                  
                  throw nvae;
                  
               }
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:17:
            // 'january'
            {
               match( "january" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:31:
            // 'jan'
            {
               match( "jan" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:40:
            // 'february'
            {
               match( "february" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:53:
            // 'feb'
            {
               match( "feb" );
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:65:
            // 'march'
            {
               match( "march" );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:75:
            // 'mar'
            {
               match( "mar" );
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:88:
            // 'april'
            {
               match( "april" );
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:314:98:
            // 'apr'
            {
               match( "apr" );
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:315:17:
            // 'may'
            {
               match( "may" );
               
            }
               break;
            case 10:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:315:31:
            // 'june'
            {
               match( "june" );
               
            }
               break;
            case 11:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:315:40:
            // 'jun'
            {
               match( "jun" );
               
            }
               break;
            case 12:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:315:53:
            // 'july'
            {
               match( "july" );
               
            }
               break;
            case 13:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:315:65:
            // 'jul'
            {
               match( "jul" );
               
            }
               break;
            case 14:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:315:75:
            // 'august'
            {
               match( "august" );
               
            }
               break;
            case 15:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:315:88:
            // 'aug'
            {
               match( "aug" );
               
            }
               break;
            case 16:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:316:17:
            // 'september'
            {
               match( "september" );
               
            }
               break;
            case 17:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:316:31:
            // 'sept'
            {
               match( "sept" );
               
            }
               break;
            case 18:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:316:40:
            // 'sep'
            {
               match( "sep" );
               
            }
               break;
            case 19:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:316:53:
            // 'october'
            {
               match( "october" );
               
            }
               break;
            case 20:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:316:65:
            // 'oct'
            {
               match( "oct" );
               
            }
               break;
            case 21:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:316:75:
            // 'november'
            {
               match( "november" );
               
            }
               break;
            case 22:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:316:88:
            // 'nov'
            {
               match( "nov" );
               
            }
               break;
            case 23:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:317:17:
            // 'december'
            {
               match( "december" );
               
            }
               break;
            case 24:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:317:31:
            // 'dec'
            {
               match( "dec" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "MONTH"
   
   // $ANTLR start "WEEKDAY_LIT"
   public final void mWEEKDAY_LIT() throws RecognitionException
   {
      try
      {
         int _type = WEEKDAY_LIT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:319:15:
         // ( 'weekday' ( 's' )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:319:17:
         // 'weekday' ( 's' )?
         {
            match( "weekday" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:319:26:
            // ( 's' )?
            int alt8 = 2;
            int LA8_0 = input.LA( 1 );
            
            if ( ( LA8_0 == 's' ) )
            {
               alt8 = 1;
            }
            switch ( alt8 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:319:26:
               // 's'
               {
                  match( 's' );
                  
               }
                  break;
            
            }
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "WEEKDAY_LIT"
   
   // $ANTLR start "WEEKEND"
   public final void mWEEKEND() throws RecognitionException
   {
      try
      {
         int _type = WEEKEND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:321:15:
         // ( 'weekend' ( 's' )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:321:17:
         // 'weekend' ( 's' )?
         {
            match( "weekend" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:321:26:
            // ( 's' )?
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == 's' ) )
            {
               alt9 = 1;
            }
            switch ( alt9 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:321:26:
               // 's'
               {
                  match( 's' );
                  
               }
                  break;
            
            }
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "WEEKEND"
   
   // $ANTLR start "MONDAY"
   public final void mMONDAY() throws RecognitionException
   {
      try
      {
         int _type = MONDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:323:15:
         // ( 'monday' | 'mon' )
         int alt10 = 2;
         int LA10_0 = input.LA( 1 );
         
         if ( ( LA10_0 == 'm' ) )
         {
            int LA10_1 = input.LA( 2 );
            
            if ( ( LA10_1 == 'o' ) )
            {
               int LA10_2 = input.LA( 3 );
               
               if ( ( LA10_2 == 'n' ) )
               {
                  int LA10_3 = input.LA( 4 );
                  
                  if ( ( LA10_3 == 'd' ) )
                  {
                     alt10 = 1;
                  }
                  else
                  {
                     alt10 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        10,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     10,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  10,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt10 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:323:17:
            // 'monday'
            {
               match( "monday" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:323:31:
            // 'mon'
            {
               match( "mon" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "MONDAY"
   
   // $ANTLR start "TUESDAY"
   public final void mTUESDAY() throws RecognitionException
   {
      try
      {
         int _type = TUESDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:325:15:
         // ( 'tuesday' | 'tue' )
         int alt11 = 2;
         int LA11_0 = input.LA( 1 );
         
         if ( ( LA11_0 == 't' ) )
         {
            int LA11_1 = input.LA( 2 );
            
            if ( ( LA11_1 == 'u' ) )
            {
               int LA11_2 = input.LA( 3 );
               
               if ( ( LA11_2 == 'e' ) )
               {
                  int LA11_3 = input.LA( 4 );
                  
                  if ( ( LA11_3 == 's' ) )
                  {
                     alt11 = 1;
                  }
                  else
                  {
                     alt11 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        11,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     11,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  11,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt11 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:325:17:
            // 'tuesday'
            {
               match( "tuesday" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:325:31:
            // 'tue'
            {
               match( "tue" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "TUESDAY"
   
   // $ANTLR start "WEDNESDAY"
   public final void mWEDNESDAY() throws RecognitionException
   {
      try
      {
         int _type = WEDNESDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:327:15:
         // ( 'wednesday' | 'wed' )
         int alt12 = 2;
         int LA12_0 = input.LA( 1 );
         
         if ( ( LA12_0 == 'w' ) )
         {
            int LA12_1 = input.LA( 2 );
            
            if ( ( LA12_1 == 'e' ) )
            {
               int LA12_2 = input.LA( 3 );
               
               if ( ( LA12_2 == 'd' ) )
               {
                  int LA12_3 = input.LA( 4 );
                  
                  if ( ( LA12_3 == 'n' ) )
                  {
                     alt12 = 1;
                  }
                  else
                  {
                     alt12 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        12,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     12,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  12,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt12 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:327:17:
            // 'wednesday'
            {
               match( "wednesday" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:327:31:
            // 'wed'
            {
               match( "wed" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "WEDNESDAY"
   
   // $ANTLR start "THURSDAY"
   public final void mTHURSDAY() throws RecognitionException
   {
      try
      {
         int _type = THURSDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:329:15:
         // ( 'thursday' | 'thu' )
         int alt13 = 2;
         int LA13_0 = input.LA( 1 );
         
         if ( ( LA13_0 == 't' ) )
         {
            int LA13_1 = input.LA( 2 );
            
            if ( ( LA13_1 == 'h' ) )
            {
               int LA13_2 = input.LA( 3 );
               
               if ( ( LA13_2 == 'u' ) )
               {
                  int LA13_3 = input.LA( 4 );
                  
                  if ( ( LA13_3 == 'r' ) )
                  {
                     alt13 = 1;
                  }
                  else
                  {
                     alt13 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        13,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     13,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  13,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt13 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:329:17:
            // 'thursday'
            {
               match( "thursday" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:329:31:
            // 'thu'
            {
               match( "thu" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "THURSDAY"
   
   // $ANTLR start "FRIDAY"
   public final void mFRIDAY() throws RecognitionException
   {
      try
      {
         int _type = FRIDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:331:15:
         // ( 'friday' | 'fri' )
         int alt14 = 2;
         int LA14_0 = input.LA( 1 );
         
         if ( ( LA14_0 == 'f' ) )
         {
            int LA14_1 = input.LA( 2 );
            
            if ( ( LA14_1 == 'r' ) )
            {
               int LA14_2 = input.LA( 3 );
               
               if ( ( LA14_2 == 'i' ) )
               {
                  int LA14_3 = input.LA( 4 );
                  
                  if ( ( LA14_3 == 'd' ) )
                  {
                     alt14 = 1;
                  }
                  else
                  {
                     alt14 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        14,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     14,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  14,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt14 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:331:17:
            // 'friday'
            {
               match( "friday" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:331:31:
            // 'fri'
            {
               match( "fri" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "FRIDAY"
   
   // $ANTLR start "SATURDAY"
   public final void mSATURDAY() throws RecognitionException
   {
      try
      {
         int _type = SATURDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:333:15:
         // ( 'saturday' | 'sat' )
         int alt15 = 2;
         int LA15_0 = input.LA( 1 );
         
         if ( ( LA15_0 == 's' ) )
         {
            int LA15_1 = input.LA( 2 );
            
            if ( ( LA15_1 == 'a' ) )
            {
               int LA15_2 = input.LA( 3 );
               
               if ( ( LA15_2 == 't' ) )
               {
                  int LA15_3 = input.LA( 4 );
                  
                  if ( ( LA15_3 == 'u' ) )
                  {
                     alt15 = 1;
                  }
                  else
                  {
                     alt15 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        15,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     15,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  15,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt15 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:333:17:
            // 'saturday'
            {
               match( "saturday" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:333:31:
            // 'sat'
            {
               match( "sat" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "SATURDAY"
   
   // $ANTLR start "SUNDAY"
   public final void mSUNDAY() throws RecognitionException
   {
      try
      {
         int _type = SUNDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:335:15:
         // ( 'sunday' | 'sun' )
         int alt16 = 2;
         int LA16_0 = input.LA( 1 );
         
         if ( ( LA16_0 == 's' ) )
         {
            int LA16_1 = input.LA( 2 );
            
            if ( ( LA16_1 == 'u' ) )
            {
               int LA16_2 = input.LA( 3 );
               
               if ( ( LA16_2 == 'n' ) )
               {
                  int LA16_3 = input.LA( 4 );
                  
                  if ( ( LA16_3 == 'd' ) )
                  {
                     alt16 = 1;
                  }
                  else
                  {
                     alt16 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        16,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     16,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  16,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt16 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:335:17:
            // 'sunday'
            {
               match( "sunday" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:335:31:
            // 'sun'
            {
               match( "sun" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "SUNDAY"
   
   // $ANTLR start "FIRST"
   public final void mFIRST() throws RecognitionException
   {
      try
      {
         int _type = FIRST;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:337:15:
         // ( 'first' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:337:17:
         // 'first'
         {
            match( "first" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "FIRST"
   
   // $ANTLR start "SECOND"
   public final void mSECOND() throws RecognitionException
   {
      try
      {
         int _type = SECOND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:339:15:
         // ( 'second' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:339:17:
         // 'second'
         {
            match( "second" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "SECOND"
   
   // $ANTLR start "THIRD"
   public final void mTHIRD() throws RecognitionException
   {
      try
      {
         int _type = THIRD;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:341:15:
         // ( 'third' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:341:17:
         // 'third'
         {
            match( "third" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "THIRD"
   
   // $ANTLR start "FOURTH"
   public final void mFOURTH() throws RecognitionException
   {
      try
      {
         int _type = FOURTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:343:15:
         // ( 'fourth' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:343:17:
         // 'fourth'
         {
            match( "fourth" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "FOURTH"
   
   // $ANTLR start "FIFTH"
   public final void mFIFTH() throws RecognitionException
   {
      try
      {
         int _type = FIFTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:345:15:
         // ( 'fifth' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:345:17:
         // 'fifth'
         {
            match( "fifth" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "FIFTH"
   
   // $ANTLR start "LAST"
   public final void mLAST() throws RecognitionException
   {
      try
      {
         int _type = LAST;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:347:15:
         // ( 'last' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:347:17:
         // 'last'
         {
            match( "last" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "LAST"
   
   // $ANTLR start "OTHER"
   public final void mOTHER() throws RecognitionException
   {
      try
      {
         int _type = OTHER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:349:15:
         // ( 'other' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:349:17:
         // 'other'
         {
            match( "other" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "OTHER"
   
   // $ANTLR start "ST_S"
   public final void mST_S() throws RecognitionException
   {
      try
      {
         int _type = ST_S;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:351:15:
         // ( 'st' | 'nd' | 'rd' | 'th' )
         int alt17 = 4;
         switch ( input.LA( 1 ) )
         {
            case 's':
            {
               alt17 = 1;
            }
               break;
            case 'n':
            {
               alt17 = 2;
            }
               break;
            case 'r':
            {
               alt17 = 3;
            }
               break;
            case 't':
            {
               alt17 = 4;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     17,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
         }
         
         switch ( alt17 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:351:17:
            // 'st'
            {
               match( "st" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:351:24:
            // 'nd'
            {
               match( "nd" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:351:31:
            // 'rd'
            {
               match( "rd" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:351:38:
            // 'th'
            {
               match( "th" );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "ST_S"
   
   // $ANTLR start "NUM_ONE"
   public final void mNUM_ONE() throws RecognitionException
   {
      try
      {
         int _type = NUM_ONE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:353:15:
         // ( 'one' | 'a' )
         int alt18 = 2;
         int LA18_0 = input.LA( 1 );
         
         if ( ( LA18_0 == 'o' ) )
         {
            alt18 = 1;
         }
         else if ( ( LA18_0 == 'a' ) )
         {
            alt18 = 2;
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  18,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt18 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:353:17:
            // 'one'
            {
               match( "one" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:353:25:
            // 'a'
            {
               match( 'a' );
               
            }
               break;
         
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUM_ONE"
   
   // $ANTLR start "NUM_TWO"
   public final void mNUM_TWO() throws RecognitionException
   {
      try
      {
         int _type = NUM_TWO;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:355:15:
         // ( 'two' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:355:17:
         // 'two'
         {
            match( "two" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUM_TWO"
   
   // $ANTLR start "NUM_THREE"
   public final void mNUM_THREE() throws RecognitionException
   {
      try
      {
         int _type = NUM_THREE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:357:15:
         // ( 'three' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:357:17:
         // 'three'
         {
            match( "three" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUM_THREE"
   
   // $ANTLR start "NUM_FOUR"
   public final void mNUM_FOUR() throws RecognitionException
   {
      try
      {
         int _type = NUM_FOUR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:359:15:
         // ( 'four' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:359:17:
         // 'four'
         {
            match( "four" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUM_FOUR"
   
   // $ANTLR start "NUM_FIVE"
   public final void mNUM_FIVE() throws RecognitionException
   {
      try
      {
         int _type = NUM_FIVE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:361:15:
         // ( 'five' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:361:17:
         // 'five'
         {
            match( "five" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUM_FIVE"
   
   // $ANTLR start "NUM_SIX"
   public final void mNUM_SIX() throws RecognitionException
   {
      try
      {
         int _type = NUM_SIX;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:363:15:
         // ( 'six' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:363:17:
         // 'six'
         {
            match( "six" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUM_SIX"
   
   // $ANTLR start "NUM_SEVEN"
   public final void mNUM_SEVEN() throws RecognitionException
   {
      try
      {
         int _type = NUM_SEVEN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:365:15:
         // ( 'seven' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:365:17:
         // 'seven'
         {
            match( "seven" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUM_SEVEN"
   
   // $ANTLR start "NUM_EIGHT"
   public final void mNUM_EIGHT() throws RecognitionException
   {
      try
      {
         int _type = NUM_EIGHT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:367:15:
         // ( 'eight' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:367:17:
         // 'eight'
         {
            match( "eight" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUM_EIGHT"
   
   // $ANTLR start "NUM_NINE"
   public final void mNUM_NINE() throws RecognitionException
   {
      try
      {
         int _type = NUM_NINE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:369:15:
         // ( 'nine' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:369:17:
         // 'nine'
         {
            match( "nine" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUM_NINE"
   
   // $ANTLR start "NUM_TEN"
   public final void mNUM_TEN() throws RecognitionException
   {
      try
      {
         int _type = NUM_TEN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:371:15:
         // ( 'ten' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:371:17:
         // 'ten'
         {
            match( "ten" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUM_TEN"
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException
   {
      try
      {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:373:15:
         // ( 'and' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:373:17:
         // 'and'
         {
            match( "and" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "AND"
   
   // $ANTLR start "IN"
   public final void mIN() throws RecognitionException
   {
      try
      {
         int _type = IN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:375:15:
         // ( 'in' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:375:17:
         // 'in'
         {
            match( "in" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "IN"
   
   // $ANTLR start "ON"
   public final void mON() throws RecognitionException
   {
      try
      {
         int _type = ON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:377:15:
         // ( 'on' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:377:17:
         // 'on'
         {
            match( "on" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "ON"
   
   // $ANTLR start "OF"
   public final void mOF() throws RecognitionException
   {
      try
      {
         int _type = OF;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:379:15:
         // ( 'of' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:379:17:
         // 'of'
         {
            match( "of" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "OF"
   
   // $ANTLR start "THE"
   public final void mTHE() throws RecognitionException
   {
      try
      {
         int _type = THE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:381:15:
         // ( 'the' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:381:17:
         // 'the'
         {
            match( "the" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "THE"
   
   // $ANTLR start "UNTIL"
   public final void mUNTIL() throws RecognitionException
   {
      try
      {
         int _type = UNTIL;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:383:15:
         // ( 'until' STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:383:17:
         // 'until' STRING
         {
            match( "until" );
            
            mSTRING();
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "UNTIL"
   
   // $ANTLR start "FOR"
   public final void mFOR() throws RecognitionException
   {
      try
      {
         int _type = FOR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:385:15:
         // ( 'for' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:385:17:
         // 'for'
         {
            match( "for" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "FOR"
   
   // $ANTLR start "TIMES"
   public final void mTIMES() throws RecognitionException
   {
      try
      {
         int _type = TIMES;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:387:15:
         // ( 'times' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:387:17:
         // 'times'
         {
            match( "times" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "TIMES"
   
   // $ANTLR start "DOT"
   public final void mDOT() throws RecognitionException
   {
      try
      {
         int _type = DOT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:389:15:
         // ( '.' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:389:17:
         // '.'
         {
            match( '.' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "DOT"
   
   // $ANTLR start "MINUS"
   public final void mMINUS() throws RecognitionException
   {
      try
      {
         int _type = MINUS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:391:15:
         // ( '-' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:391:17:
         // '-'
         {
            match( '-' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "MINUS"
   
   // $ANTLR start "COMMA"
   public final void mCOMMA() throws RecognitionException
   {
      try
      {
         int _type = COMMA;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:393:15:
         // ( ',' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:393:17:
         // ','
         {
            match( ',' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "COMMA"
   
   // $ANTLR start "NUMBER"
   public final void mNUMBER() throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:397:15:
         // ( '0' .. '9' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:
         {
            if ( ( input.LA( 1 ) >= '0' && input.LA( 1 ) <= '9' ) )
            {
               input.consume();
            }
            else
            {
               MismatchedSetException mse = new MismatchedSetException( null,
                                                                        input );
               recover( mse );
               throw mse;
            }
            
         }
         
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUMBER"
   
   // $ANTLR start "INT"
   public final void mINT() throws RecognitionException
   {
      try
      {
         int _type = INT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:398:15:
         // ( ( MINUS )? ( NUMBER )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:398:17:
         // ( MINUS )? ( NUMBER )+
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:398:17:
            // ( MINUS )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == '-' ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:
               {
                  if ( input.LA( 1 ) == '-' )
                  {
                     input.consume();
                  }
                  else
                  {
                     MismatchedSetException mse = new MismatchedSetException( null,
                                                                              input );
                     recover( mse );
                     throw mse;
                  }
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:398:24:
            // ( NUMBER )+
            int cnt20 = 0;
            loop20: do
            {
               int alt20 = 2;
               int LA20_0 = input.LA( 1 );
               
               if ( ( ( LA20_0 >= '0' && LA20_0 <= '9' ) ) )
               {
                  alt20 = 1;
               }
               
               switch ( alt20 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:
                  {
                     if ( ( input.LA( 1 ) >= '0' && input.LA( 1 ) <= '9' ) )
                     {
                        input.consume();
                     }
                     else
                     {
                        MismatchedSetException mse = new MismatchedSetException( null,
                                                                                 input );
                        recover( mse );
                        throw mse;
                     }
                     
                  }
                     break;
                  
                  default :
                     if ( cnt20 >= 1 )
                        break loop20;
                     EarlyExitException eee = new EarlyExitException( 20, input );
                     throw eee;
               }
               cnt20++;
            }
            while ( true );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "INT"
   
   // $ANTLR start "STRING"
   public final void mSTRING() throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:402:15:
         // ( ( ' ' | . )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:402:17:
         // ( ' ' | . )+
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:402:17:
            // ( ' ' | . )+
            int cnt21 = 0;
            loop21: do
            {
               int alt21 = 3;
               int LA21_0 = input.LA( 1 );
               
               if ( ( LA21_0 == ' ' ) )
               {
                  alt21 = 1;
               }
               else if ( ( ( LA21_0 >= '\u0000' && LA21_0 <= '\u001F' ) || ( LA21_0 >= '!' && LA21_0 <= '\uFFFF' ) ) )
               {
                  alt21 = 2;
               }
               
               switch ( alt21 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:402:18:
                  // ' '
                  {
                     match( ' ' );
                     
                  }
                     break;
                  case 2:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:402:22:
                  // .
                  {
                     matchAny();
                     
                  }
                     break;
                  
                  default :
                     if ( cnt21 >= 1 )
                        break loop21;
                     EarlyExitException eee = new EarlyExitException( 21, input );
                     throw eee;
               }
               cnt21++;
            }
            while ( true );
            
         }
         
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "STRING"
   
   // $ANTLR start "WS"
   public final void mWS() throws RecognitionException
   {
      try
      {
         int _type = WS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:403:15:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:403:17:
         // ( ' ' | '\\t' | '\\r' | '\\n' )
         {
            if ( ( input.LA( 1 ) >= '\t' && input.LA( 1 ) <= '\n' )
               || input.LA( 1 ) == '\r' || input.LA( 1 ) == ' ' )
            {
               input.consume();
            }
            else
            {
               MismatchedSetException mse = new MismatchedSetException( null,
                                                                        input );
               recover( mse );
               throw mse;
            }
            
            _channel = HIDDEN;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "WS"
   
   public void mTokens() throws RecognitionException
   {
      // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:8:
      // ( EVERY | AFTER | BIWEEKLY | YEARS | MONTHS | WEEKS | DAYS | DAILY | MONTH | WEEKDAY_LIT | WEEKEND | MONDAY |
      // TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | FIRST | SECOND | THIRD | FOURTH | FIFTH | LAST |
      // OTHER | ST_S | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE
      // | NUM_TEN | AND | IN | ON | OF | THE | UNTIL | FOR | TIMES | DOT | MINUS | COMMA | INT | WS )
      int alt22 = 49;
      alt22 = dfa22.predict( input );
      switch ( alt22 )
      {
         case 1:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:10:
         // EVERY
         {
            mEVERY();
            
         }
            break;
         case 2:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:16:
         // AFTER
         {
            mAFTER();
            
         }
            break;
         case 3:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:22:
         // BIWEEKLY
         {
            mBIWEEKLY();
            
         }
            break;
         case 4:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:31:
         // YEARS
         {
            mYEARS();
            
         }
            break;
         case 5:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:37:
         // MONTHS
         {
            mMONTHS();
            
         }
            break;
         case 6:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:44:
         // WEEKS
         {
            mWEEKS();
            
         }
            break;
         case 7:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:50:
         // DAYS
         {
            mDAYS();
            
         }
            break;
         case 8:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:55:
         // DAILY
         {
            mDAILY();
            
         }
            break;
         case 9:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:61:
         // MONTH
         {
            mMONTH();
            
         }
            break;
         case 10:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:67:
         // WEEKDAY_LIT
         {
            mWEEKDAY_LIT();
            
         }
            break;
         case 11:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:79:
         // WEEKEND
         {
            mWEEKEND();
            
         }
            break;
         case 12:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:87:
         // MONDAY
         {
            mMONDAY();
            
         }
            break;
         case 13:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:94:
         // TUESDAY
         {
            mTUESDAY();
            
         }
            break;
         case 14:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:102:
         // WEDNESDAY
         {
            mWEDNESDAY();
            
         }
            break;
         case 15:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:112:
         // THURSDAY
         {
            mTHURSDAY();
            
         }
            break;
         case 16:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:121:
         // FRIDAY
         {
            mFRIDAY();
            
         }
            break;
         case 17:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:128:
         // SATURDAY
         {
            mSATURDAY();
            
         }
            break;
         case 18:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:137:
         // SUNDAY
         {
            mSUNDAY();
            
         }
            break;
         case 19:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:144:
         // FIRST
         {
            mFIRST();
            
         }
            break;
         case 20:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:150:
         // SECOND
         {
            mSECOND();
            
         }
            break;
         case 21:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:157:
         // THIRD
         {
            mTHIRD();
            
         }
            break;
         case 22:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:163:
         // FOURTH
         {
            mFOURTH();
            
         }
            break;
         case 23:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:170:
         // FIFTH
         {
            mFIFTH();
            
         }
            break;
         case 24:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:176:
         // LAST
         {
            mLAST();
            
         }
            break;
         case 25:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:181:
         // OTHER
         {
            mOTHER();
            
         }
            break;
         case 26:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:187:
         // ST_S
         {
            mST_S();
            
         }
            break;
         case 27:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:192:
         // NUM_ONE
         {
            mNUM_ONE();
            
         }
            break;
         case 28:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:200:
         // NUM_TWO
         {
            mNUM_TWO();
            
         }
            break;
         case 29:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:208:
         // NUM_THREE
         {
            mNUM_THREE();
            
         }
            break;
         case 30:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:218:
         // NUM_FOUR
         {
            mNUM_FOUR();
            
         }
            break;
         case 31:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:227:
         // NUM_FIVE
         {
            mNUM_FIVE();
            
         }
            break;
         case 32:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:236:
         // NUM_SIX
         {
            mNUM_SIX();
            
         }
            break;
         case 33:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:244:
         // NUM_SEVEN
         {
            mNUM_SEVEN();
            
         }
            break;
         case 34:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:254:
         // NUM_EIGHT
         {
            mNUM_EIGHT();
            
         }
            break;
         case 35:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:264:
         // NUM_NINE
         {
            mNUM_NINE();
            
         }
            break;
         case 36:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:273:
         // NUM_TEN
         {
            mNUM_TEN();
            
         }
            break;
         case 37:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:281:
         // AND
         {
            mAND();
            
         }
            break;
         case 38:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:285:
         // IN
         {
            mIN();
            
         }
            break;
         case 39:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:288:
         // ON
         {
            mON();
            
         }
            break;
         case 40:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:291:
         // OF
         {
            mOF();
            
         }
            break;
         case 41:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:294:
         // THE
         {
            mTHE();
            
         }
            break;
         case 42:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:298:
         // UNTIL
         {
            mUNTIL();
            
         }
            break;
         case 43:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:304:
         // FOR
         {
            mFOR();
            
         }
            break;
         case 44:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:308:
         // TIMES
         {
            mTIMES();
            
         }
            break;
         case 45:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:314:
         // DOT
         {
            mDOT();
            
         }
            break;
         case 46:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:318:
         // MINUS
         {
            mMINUS();
            
         }
            break;
         case 47:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:324:
         // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 48:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:330:
         // INT
         {
            mINT();
            
         }
            break;
         case 49:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:1:334:
         // WS
         {
            mWS();
            
         }
            break;
      
      }
      
   }
   
   protected DFA22 dfa22 = new DFA22( this );
   
   static final String DFA22_eotS = "\2\uffff\1\33\20\uffff\1\60\24\uffff\1\75\3\uffff\1\17\4\uffff\1"
      + "\102\4\uffff\1\104\14\uffff\1\110\2\uffff\1\41\4\uffff";
   
   static final String DFA22_eofS = "\113\uffff";
   
   static final String DFA22_minS = "\1\11\1\141\1\146\1\145\2\uffff\1\141\1\145\1\141\1\uffff\1\141"
      + "\1\143\1\144\1\145\5\uffff\1\60\10\uffff\1\162\1\uffff\1\146\1\156"
      + "\1\144\1\uffff\1\151\1\143\4\uffff\1\145\3\uffff\1\145\4\uffff\1"
      + "\164\1\162\3\uffff\1\144\1\153\13\uffff\1\164\2\uffff\1\144\4\uffff";
   
   static final String DFA22_maxS = "\1\171\1\166\1\165\1\162\2\uffff\1\157\1\153\1\145\1\uffff\1\165"
      + "\1\164\1\157\1\167\5\uffff\1\71\10\uffff\1\165\1\uffff\1\166\1\156"
      + "\1\145\1\uffff\1\171\1\166\4\uffff\1\145\3\uffff\1\165\4\uffff\1"
      + "\164\1\162\3\uffff\1\144\1\153\13\uffff\1\164\2\uffff\1\145\4\uffff";
   
   static final String DFA22_acceptS = "\4\uffff\1\3\1\4\3\uffff\1\11\4\uffff\1\30\1\32\1\46\1\52\1\55\1"
      + "\uffff\1\57\1\60\1\61\1\1\1\42\1\2\1\45\1\33\1\uffff\1\20\3\uffff"
      + "\1\6\2\uffff\1\21\1\22\1\40\1\31\1\uffff\1\50\1\43\1\15\1\uffff"
      + "\1\34\1\44\1\54\1\56\2\uffff\1\23\1\27\1\37\2\uffff\1\16\1\7\1\10"
      + "\1\24\1\41\1\47\1\17\1\25\1\35\1\51\1\53\1\uffff\1\5\1\14\1\uffff"
      + "\1\26\1\36\1\12\1\13";
   
   static final String DFA22_specialS = "\113\uffff}>";
   
   static final String[] DFA22_transitionS =
   {
    "\2\26\2\uffff\1\26\22\uffff\1\26\13\uffff\1\24\1\23\1\22\1\uffff"
       + "\12\25\47\uffff\1\2\1\4\1\uffff\1\10\1\1\1\3\2\uffff\1\20\1"
       + "\11\1\uffff\1\16\1\6\1\14\1\13\2\uffff\1\17\1\12\1\15\1\21\1"
       + "\uffff\1\7\1\uffff\1\5", "\1\27\7\uffff\1\30\14\uffff\1\27",
    "\1\31\7\uffff\1\32\1\uffff\1\11\4\uffff\1\11",
    "\1\11\3\uffff\1\36\5\uffff\1\34\2\uffff\1\35", "", "",
    "\1\11\15\uffff\1\37", "\1\40\5\uffff\1\41", "\1\42\3\uffff\1\11", "",
    "\1\44\3\uffff\1\43\3\uffff\1\46\12\uffff\1\17\1\45",
    "\1\11\2\uffff\1\51\7\uffff\1\50\5\uffff\1\47",
    "\1\17\4\uffff\1\52\5\uffff\1\11",
    "\1\56\2\uffff\1\54\1\57\13\uffff\1\53\1\uffff\1\55", "", "", "", "", "",
    "\12\25", "", "", "", "", "", "", "", "", "\1\61\2\uffff\1\62", "",
    "\1\64\13\uffff\1\63\3\uffff\1\65", "\1\66", "\1\70\1\67", "",
    "\1\72\17\uffff\1\71", "\1\73\14\uffff\1\11\5\uffff\1\74", "", "", "", "",
    "\1\33", "", "", "", "\1\101\3\uffff\1\77\10\uffff\1\100\2\uffff\1\76", "",
    "", "", "", "\1\4", "\1\103", "", "", "", "\1\105", "\1\106", "", "", "",
    "", "", "", "", "", "", "", "", "\1\107", "", "", "\1\111\1\112", "", "",
    "", "" };
   
   static final short[] DFA22_eot = DFA.unpackEncodedString( DFA22_eotS );
   
   static final short[] DFA22_eof = DFA.unpackEncodedString( DFA22_eofS );
   
   static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars( DFA22_minS );
   
   static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars( DFA22_maxS );
   
   static final short[] DFA22_accept = DFA.unpackEncodedString( DFA22_acceptS );
   
   static final short[] DFA22_special = DFA.unpackEncodedString( DFA22_specialS );
   
   static final short[][] DFA22_transition;
   
   static
   {
      int numStates = DFA22_transitionS.length;
      DFA22_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA22_transition[ i ] = DFA.unpackEncodedString( DFA22_transitionS[ i ] );
      }
   }
   
   
   class DFA22 extends DFA
   {
      
      public DFA22( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 22;
         this.eot = DFA22_eot;
         this.eof = DFA22_eof;
         this.min = DFA22_min;
         this.max = DFA22_max;
         this.accept = DFA22_accept;
         this.special = DFA22_special;
         this.transition = DFA22_transition;
      }
      
      
      
      public String getDescription()
      {
         return "1:1: Tokens : ( EVERY | AFTER | BIWEEKLY | YEARS | MONTHS | WEEKS | DAYS | DAILY | MONTH | WEEKDAY_LIT | WEEKEND | MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | FIRST | SECOND | THIRD | FOURTH | FIFTH | LAST | OTHER | ST_S | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN | AND | IN | ON | OF | THE | UNTIL | FOR | TIMES | DOT | MINUS | COMMA | INT | WS );";
      }
   }
   
}
