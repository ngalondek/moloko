// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g 2011-01-13 06:32:22

package dev.drsoran.moloko.grammar;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;


public class DateTimeLexer extends Lexer
{
   public static final int STs = 16;
   
   public static final int MIDDAY = 20;
   
   public static final int THE = 15;
   
   public static final int NOW = 17;
   
   public static final int SECONDS = 28;
   
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
   
   public DateTimeLexer()
   {
      ;
   }
   


   public DateTimeLexer( CharStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public DateTimeLexer( CharStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   public String getGrammarFileName()
   {
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g";
   }
   


   // $ANTLR start "NEVER"
   public final void mNEVER() throws RecognitionException
   {
      try
      {
         int _type = NEVER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:13:11: (
         // 'never' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:13:13:
         // 'never'
         {
            match( "never" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NEVER"
   
   // $ANTLR start "TODAY"
   public final void mTODAY() throws RecognitionException
   {
      try
      {
         int _type = TODAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:15:11: (
         // 'today' | 'tod' )
         int alt1 = 2;
         int LA1_0 = input.LA( 1 );
         
         if ( ( LA1_0 == 't' ) )
         {
            int LA1_1 = input.LA( 2 );
            
            if ( ( LA1_1 == 'o' ) )
            {
               int LA1_2 = input.LA( 3 );
               
               if ( ( LA1_2 == 'd' ) )
               {
                  int LA1_3 = input.LA( 4 );
                  
                  if ( ( LA1_3 == 'a' ) )
                  {
                     alt1 = 1;
                  }
                  else
                  {
                     alt1 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        1,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:15:13:
               // 'today'
            {
               match( "today" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:15:23:
               // 'tod'
            {
               match( "tod" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "TODAY"
   
   // $ANTLR start "TOMORROW"
   public final void mTOMORROW() throws RecognitionException
   {
      try
      {
         int _type = TOMORROW;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:17:11: (
         // 'tomorrow' | 'tom' | 'tmr' )
         int alt2 = 3;
         int LA2_0 = input.LA( 1 );
         
         if ( ( LA2_0 == 't' ) )
         {
            int LA2_1 = input.LA( 2 );
            
            if ( ( LA2_1 == 'o' ) )
            {
               int LA2_2 = input.LA( 3 );
               
               if ( ( LA2_2 == 'm' ) )
               {
                  int LA2_4 = input.LA( 4 );
                  
                  if ( ( LA2_4 == 'o' ) )
                  {
                     alt2 = 1;
                  }
                  else
                  {
                     alt2 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        2,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else if ( ( LA2_1 == 'm' ) )
            {
               alt2 = 3;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     2,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:17:13:
               // 'tomorrow'
            {
               match( "tomorrow" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:17:26:
               // 'tom'
            {
               match( "tom" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:17:34:
               // 'tmr'
            {
               match( "tmr" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "TOMORROW"
   
   // $ANTLR start "YESTERDAY"
   public final void mYESTERDAY() throws RecognitionException
   {
      try
      {
         int _type = YESTERDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:19:11: (
         // 'yesterday' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:19:13:
         // 'yesterday'
         {
            match( "yesterday" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "YESTERDAY"
   
   // $ANTLR start "AT"
   public final void mAT() throws RecognitionException
   {
      try
      {
         int _type = AT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:21:11: ( '@'
         // | 'at' )
         int alt3 = 2;
         int LA3_0 = input.LA( 1 );
         
         if ( ( LA3_0 == '@' ) )
         {
            alt3 = 1;
         }
         else if ( ( LA3_0 == 'a' ) )
         {
            alt3 = 2;
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:21:13:
               // '@'
            {
               match( '@' );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:21:19:
               // 'at'
            {
               match( "at" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "AT"
   
   // $ANTLR start "ON"
   public final void mON() throws RecognitionException
   {
      try
      {
         int _type = ON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:23:11: (
         // 'on' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:23:13: 'on'
         {
            match( "on" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "ON"
   
   // $ANTLR start "IN"
   public final void mIN() throws RecognitionException
   {
      try
      {
         int _type = IN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:25:11: (
         // 'in' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:25:13: 'in'
         {
            match( "in" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "IN"
   
   // $ANTLR start "OF"
   public final void mOF() throws RecognitionException
   {
      try
      {
         int _type = OF;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:27:11: (
         // 'of' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:27:13: 'of'
         {
            match( "of" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OF"
   
   // $ANTLR start "NEXT"
   public final void mNEXT() throws RecognitionException
   {
      try
      {
         int _type = NEXT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:29:11: (
         // 'next' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:29:13:
         // 'next'
         {
            match( "next" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NEXT"
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException
   {
      try
      {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:31:11: (
         // 'and' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:31:13: 'and'
         {
            match( "and" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "AND"
   
   // $ANTLR start "END"
   public final void mEND() throws RecognitionException
   {
      try
      {
         int _type = END;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:33:11: (
         // 'end' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:33:13: 'end'
         {
            match( "end" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "END"
   
   // $ANTLR start "THE"
   public final void mTHE() throws RecognitionException
   {
      try
      {
         int _type = THE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:35:11: (
         // 'the' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:35:13: 'the'
         {
            match( "the" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "THE"
   
   // $ANTLR start "STs"
   public final void mSTs() throws RecognitionException
   {
      try
      {
         int _type = STs;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:37:11: (
         // 'st' | 'th' | 'rd' | 'nd' )
         int alt4 = 4;
         switch ( input.LA( 1 ) )
         {
            case 's':
            {
               alt4 = 1;
            }
               break;
            case 't':
            {
               alt4 = 2;
            }
               break;
            case 'r':
            {
               alt4 = 3;
            }
               break;
            case 'n':
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:37:13:
               // 'st'
            {
               match( "st" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:37:20:
               // 'th'
            {
               match( "th" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:37:27:
               // 'rd'
            {
               match( "rd" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:37:34:
               // 'nd'
            {
               match( "nd" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "STs"
   
   // $ANTLR start "NOW"
   public final void mNOW() throws RecognitionException
   {
      try
      {
         int _type = NOW;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:39:11: (
         // 'now' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:39:13: 'now'
         {
            match( "now" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NOW"
   
   // $ANTLR start "TONIGHT"
   public final void mTONIGHT() throws RecognitionException
   {
      try
      {
         int _type = TONIGHT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:41:11: (
         // 'tonight' | 'ton' )
         int alt5 = 2;
         int LA5_0 = input.LA( 1 );
         
         if ( ( LA5_0 == 't' ) )
         {
            int LA5_1 = input.LA( 2 );
            
            if ( ( LA5_1 == 'o' ) )
            {
               int LA5_2 = input.LA( 3 );
               
               if ( ( LA5_2 == 'n' ) )
               {
                  int LA5_3 = input.LA( 4 );
                  
                  if ( ( LA5_3 == 'i' ) )
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
                                                                        2,
                                                                        input );
                  
                  throw nvae;
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:41:13:
               // 'tonight'
            {
               match( "tonight" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:41:25:
               // 'ton'
            {
               match( "ton" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "TONIGHT"
   
   // $ANTLR start "MIDNIGHT"
   public final void mMIDNIGHT() throws RecognitionException
   {
      try
      {
         int _type = MIDNIGHT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:43:11: (
         // 'midnight' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:43:13:
         // 'midnight'
         {
            match( "midnight" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "MIDNIGHT"
   
   // $ANTLR start "MIDDAY"
   public final void mMIDDAY() throws RecognitionException
   {
      try
      {
         int _type = MIDDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:45:11: (
         // 'midday' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:45:13:
         // 'midday'
         {
            match( "midday" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "MIDDAY"
   
   // $ANTLR start "NOON"
   public final void mNOON() throws RecognitionException
   {
      try
      {
         int _type = NOON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:47:11: (
         // 'noon' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:47:13:
         // 'noon'
         {
            match( "noon" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NOON"
   
   // $ANTLR start "YEARS"
   public final void mYEARS() throws RecognitionException
   {
      try
      {
         int _type = YEARS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:11: (
         // 'years' | 'year' | 'yrs' | 'yr' )
         int alt6 = 4;
         alt6 = dfa6.predict( input );
         switch ( alt6 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:13:
               // 'years'
            {
               match( "years" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:23:
               // 'year'
            {
               match( "year" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:32:
               // 'yrs'
            {
               match( "yrs" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:40:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:11: (
         // 'months' | 'month' | 'mons' | 'mon' )
         int alt7 = 4;
         alt7 = dfa7.predict( input );
         switch ( alt7 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:13:
               // 'months'
            {
               match( "months" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:24:
               // 'month'
            {
               match( "month" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:34:
               // 'mons'
            {
               match( "mons" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:43:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:11: (
         // 'weeks' | 'week' | 'wks' | 'wk' )
         int alt8 = 4;
         alt8 = dfa8.predict( input );
         switch ( alt8 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:13:
               // 'weeks'
            {
               match( "weeks" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:23:
               // 'week'
            {
               match( "week" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:32:
               // 'wks'
            {
               match( "wks" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:40:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:55:11: (
         // 'days' | 'day' | 'd' )
         int alt9 = 3;
         int LA9_0 = input.LA( 1 );
         
         if ( ( LA9_0 == 'd' ) )
         {
            int LA9_1 = input.LA( 2 );
            
            if ( ( LA9_1 == 'a' ) )
            {
               int LA9_2 = input.LA( 3 );
               
               if ( ( LA9_2 == 'y' ) )
               {
                  int LA9_4 = input.LA( 4 );
                  
                  if ( ( LA9_4 == 's' ) )
                  {
                     alt9 = 1;
                  }
                  else
                  {
                     alt9 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               alt9 = 3;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  9,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt9 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:55:13:
               // 'days'
            {
               match( "days" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:55:22:
               // 'day'
            {
               match( "day" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:55:30:
               // 'd'
            {
               match( 'd' );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "DAYS"
   
   // $ANTLR start "HOURS"
   public final void mHOURS() throws RecognitionException
   {
      try
      {
         int _type = HOURS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:11: (
         // 'hours' | 'hour' | 'hrs' | 'hr' | 'h' )
         int alt10 = 5;
         alt10 = dfa10.predict( input );
         switch ( alt10 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:13:
               // 'hours'
            {
               match( "hours" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:23:
               // 'hour'
            {
               match( "hour" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:32:
               // 'hrs'
            {
               match( "hrs" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:40:
               // 'hr'
            {
               match( "hr" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:47:
               // 'h'
            {
               match( 'h' );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "HOURS"
   
   // $ANTLR start "MINUTES"
   public final void mMINUTES() throws RecognitionException
   {
      try
      {
         int _type = MINUTES;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:11: (
         // 'minutes' | 'minute' | 'mins' | 'min' | 'm' )
         int alt11 = 5;
         alt11 = dfa11.predict( input );
         switch ( alt11 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:13:
               // 'minutes'
            {
               match( "minutes" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:25:
               // 'minute'
            {
               match( "minute" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:36:
               // 'mins'
            {
               match( "mins" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:45:
               // 'min'
            {
               match( "min" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:53:
               // 'm'
            {
               match( 'm' );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "MINUTES"
   
   // $ANTLR start "SECONDS"
   public final void mSECONDS() throws RecognitionException
   {
      try
      {
         int _type = SECONDS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:11: (
         // 'seconds' | 'second' | 'secs' | 'sec' | 's' )
         int alt12 = 5;
         alt12 = dfa12.predict( input );
         switch ( alt12 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:13:
               // 'seconds'
            {
               match( "seconds" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:25:
               // 'second'
            {
               match( "second" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:36:
               // 'secs'
            {
               match( "secs" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:45:
               // 'sec'
            {
               match( "sec" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:53:
               // 's'
            {
               match( 's' );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "SECONDS"
   
   // $ANTLR start "MONTH"
   public final void mMONTH() throws RecognitionException
   {
      try
      {
         int _type = MONTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:11: (
         // 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july'
         // | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' |
         // 'december' | 'dec' )
         int alt13 = 24;
         alt13 = dfa13.predict( input );
         switch ( alt13 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:13:
               // 'january'
            {
               match( "january" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:27:
               // 'jan'
            {
               match( "jan" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:36:
               // 'february'
            {
               match( "february" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:49:
               // 'feb'
            {
               match( "feb" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:61:
               // 'march'
            {
               match( "march" );
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:71:
               // 'mar'
            {
               match( "mar" );
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:84:
               // 'april'
            {
               match( "april" );
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:94:
               // 'apr'
            {
               match( "apr" );
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:13:
               // 'may'
            {
               match( "may" );
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:27:
               // 'june'
            {
               match( "june" );
               
            }
               break;
            case 11:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:36:
               // 'jun'
            {
               match( "jun" );
               
            }
               break;
            case 12:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:49:
               // 'july'
            {
               match( "july" );
               
            }
               break;
            case 13:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:61:
               // 'jul'
            {
               match( "jul" );
               
            }
               break;
            case 14:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:71:
               // 'august'
            {
               match( "august" );
               
            }
               break;
            case 15:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:84:
               // 'aug'
            {
               match( "aug" );
               
            }
               break;
            case 16:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:13:
               // 'september'
            {
               match( "september" );
               
            }
               break;
            case 17:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:27:
               // 'sept'
            {
               match( "sept" );
               
            }
               break;
            case 18:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:36:
               // 'sep'
            {
               match( "sep" );
               
            }
               break;
            case 19:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:49:
               // 'october'
            {
               match( "october" );
               
            }
               break;
            case 20:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:61:
               // 'oct'
            {
               match( "oct" );
               
            }
               break;
            case 21:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:71:
               // 'november'
            {
               match( "november" );
               
            }
               break;
            case 22:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:84:
               // 'nov'
            {
               match( "nov" );
               
            }
               break;
            case 23:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:66:13:
               // 'december'
            {
               match( "december" );
               
            }
               break;
            case 24:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:66:27:
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
      }
   }
   


   // $ANTLR end "MONTH"
   
   // $ANTLR start "WEEKDAY"
   public final void mWEEKDAY() throws RecognitionException
   {
      try
      {
         int _type = WEEKDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:11: (
         // 'monday' | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' | 'thursday' | 'thu' | 'friday' | 'fri' |
         // 'saturday' | 'sat' | 'sunday' | 'sun' )
         int alt14 = 14;
         alt14 = dfa14.predict( input );
         switch ( alt14 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:13:
               // 'monday'
            {
               match( "monday" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:26:
               // 'mon'
            {
               match( "mon" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:34:
               // 'tuesday'
            {
               match( "tuesday" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:46:
               // 'tue'
            {
               match( "tue" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:54:
               // 'wednesday'
            {
               match( "wednesday" );
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:68:
               // 'wed'
            {
               match( "wed" );
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:13:
               // 'thursday'
            {
               match( "thursday" );
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:26:
               // 'thu'
            {
               match( "thu" );
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:34:
               // 'friday'
            {
               match( "friday" );
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:46:
               // 'fri'
            {
               match( "fri" );
               
            }
               break;
            case 11:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:54:
               // 'saturday'
            {
               match( "saturday" );
               
            }
               break;
            case 12:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:68:
               // 'sat'
            {
               match( "sat" );
               
            }
               break;
            case 13:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:70:13:
               // 'sunday'
            {
               match( "sunday" );
               
            }
               break;
            case 14:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:70:26:
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
      }
   }
   


   // $ANTLR end "WEEKDAY"
   
   // $ANTLR start "DATE_SEP"
   public final void mDATE_SEP() throws RecognitionException
   {
      try
      {
         int _type = DATE_SEP;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:72:11: ( '/'
         // | '\\u5E74' | '\\u6708' | '\\u65E5' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:
         {
            if ( input.LA( 1 ) == '/' || input.LA( 1 ) == '\u5E74'
               || input.LA( 1 ) == '\u65E5' || input.LA( 1 ) == '\u6708' )
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
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "DATE_SEP"
   
   // $ANTLR start "DOT"
   public final void mDOT() throws RecognitionException
   {
      try
      {
         int _type = DOT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:74:11: ( '.'
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:74:13: '.'
         {
            match( '.' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "DOT"
   
   // $ANTLR start "COLON"
   public final void mCOLON() throws RecognitionException
   {
      try
      {
         int _type = COLON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:76:11: ( ':'
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:76:13: ':'
         {
            match( ':' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "COLON"
   
   // $ANTLR start "MINUS"
   public final void mMINUS() throws RecognitionException
   {
      try
      {
         int _type = MINUS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:78:11: ( '-'
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:78:13: '-'
         {
            match( '-' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "MINUS"
   
   // $ANTLR start "MINUS_A"
   public final void mMINUS_A() throws RecognitionException
   {
      try
      {
         int _type = MINUS_A;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:80:11: (
         // '-a' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:80:13: '-a'
         {
            match( "-a" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "MINUS_A"
   
   // $ANTLR start "COMMA"
   public final void mCOMMA() throws RecognitionException
   {
      try
      {
         int _type = COMMA;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:82:11: ( ','
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:82:13: ','
         {
            match( ',' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "COMMA"
   
   // $ANTLR start "INT"
   public final void mINT() throws RecognitionException
   {
      try
      {
         int _type = INT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:84:11: ( (
         // '0' .. '9' )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:84:13: ( '0'
         // .. '9' )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:84:13: (
            // '0' .. '9' )+
            int cnt15 = 0;
            loop15: do
            {
               int alt15 = 2;
               int LA15_0 = input.LA( 1 );
               
               if ( ( ( LA15_0 >= '0' && LA15_0 <= '9' ) ) )
               {
                  alt15 = 1;
               }
               
               switch ( alt15 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:84:13:
                     // '0' .. '9'
                  {
                     matchRange( '0', '9' );
                     
                  }
                     break;
                  
                  default :
                     if ( cnt15 >= 1 )
                        break loop15;
                     EarlyExitException eee = new EarlyExitException( 15, input );
                     throw eee;
               }
               cnt15++;
            }
            while ( true );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "INT"
   
   // $ANTLR start "A"
   public final void mA() throws RecognitionException
   {
      try
      {
         int _type = A;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:86:11: ( 'a'
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:86:13: 'a'
         {
            match( 'a' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "A"
   
   // $ANTLR start "AM"
   public final void mAM() throws RecognitionException
   {
      try
      {
         int _type = AM;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:88:11: ( A (
         // 'm' )? | '\\u4E0A' | '\\u5348\\u524D' | '\\uC624\\uC804' )
         int alt17 = 4;
         switch ( input.LA( 1 ) )
         {
            case 'a':
            {
               alt17 = 1;
            }
               break;
            case '\u4E0A':
            {
               alt17 = 2;
            }
               break;
            case '\u5348':
            {
               alt17 = 3;
            }
               break;
            case '\uC624':
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:88:14:
               // A ( 'm' )?
            {
               mA();
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:88:15:
               // ( 'm' )?
               int alt16 = 2;
               int LA16_0 = input.LA( 1 );
               
               if ( ( LA16_0 == 'm' ) )
               {
                  alt16 = 1;
               }
               switch ( alt16 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:88:16:
                     // 'm'
                  {
                     match( 'm' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:88:24:
               // '\\u4E0A'
            {
               match( '\u4E0A' );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:88:35:
               // '\\u5348\\u524D'
            {
               match( "\u5348\u524D" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:88:52:
               // '\\uC624\\uC804'
            {
               match( "\uC624\uC804" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "AM"
   
   // $ANTLR start "PM"
   public final void mPM() throws RecognitionException
   {
      try
      {
         int _type = PM;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:90:11: ( 'p'
         // ( 'm' )? | '\\u4E0B' | '\\u5348\\u5F8C' | '\\uC624\\uD6C4' )
         int alt19 = 4;
         switch ( input.LA( 1 ) )
         {
            case 'p':
            {
               alt19 = 1;
            }
               break;
            case '\u4E0B':
            {
               alt19 = 2;
            }
               break;
            case '\u5348':
            {
               alt19 = 3;
            }
               break;
            case '\uC624':
            {
               alt19 = 4;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     19,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt19 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:90:13:
               // 'p' ( 'm' )?
            {
               match( 'p' );
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:90:16:
               // ( 'm' )?
               int alt18 = 2;
               int LA18_0 = input.LA( 1 );
               
               if ( ( LA18_0 == 'm' ) )
               {
                  alt18 = 1;
               }
               switch ( alt18 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:90:17:
                     // 'm'
                  {
                     match( 'm' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:90:25:
               // '\\u4E0B'
            {
               match( '\u4E0B' );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:90:36:
               // '\\u5348\\u5F8C'
            {
               match( "\u5348\u5F8C" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:90:53:
               // '\\uC624\\uD6C4'
            {
               match( "\uC624\uD6C4" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "PM"
   
   // $ANTLR start "NUM_STR"
   public final void mNUM_STR() throws RecognitionException
   {
      try
      {
         int _type = NUM_STR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:11: (
         // 'one' | 'two' | 'three' | 'four' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' )
         int alt20 = 9;
         alt20 = dfa20.predict( input );
         switch ( alt20 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:13:
               // 'one'
            {
               match( "one" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:21:
               // 'two'
            {
               match( "two" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:29:
               // 'three'
            {
               match( "three" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:39:
               // 'four'
            {
               match( "four" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:48:
               // 'six'
            {
               match( "six" );
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:56:
               // 'seven'
            {
               match( "seven" );
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:66:
               // 'eight'
            {
               match( "eight" );
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:76:
               // 'nine'
            {
               match( "nine" );
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:85:
               // 'ten'
            {
               match( "ten" );
               
            }
               break;
            
         }
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NUM_STR"
   
   // $ANTLR start "STRING"
   public final void mSTRING() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:95:11: ( (~
         // ( '\"' | ' ' | '(' | ')' ) )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:95:13: (~ (
         // '\"' | ' ' | '(' | ')' ) )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:95:13: (~
            // ( '\"' | ' ' | '(' | ')' ) )+
            int cnt21 = 0;
            loop21: do
            {
               int alt21 = 2;
               int LA21_0 = input.LA( 1 );
               
               if ( ( ( LA21_0 >= '\u0000' && LA21_0 <= '\u001F' )
                  || LA21_0 == '!' || ( LA21_0 >= '#' && LA21_0 <= '\'' ) || ( LA21_0 >= '*' && LA21_0 <= '\uFFFF' ) ) )
               {
                  alt21 = 1;
               }
               
               switch ( alt21 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:95:13:
                     // ~ ( '\"' | ' ' | '(' | ')' )
                  {
                     if ( ( input.LA( 1 ) >= '\u0000' && input.LA( 1 ) <= '\u001F' )
                        || input.LA( 1 ) == '!'
                        || ( input.LA( 1 ) >= '#' && input.LA( 1 ) <= '\'' )
                        || ( input.LA( 1 ) >= '*' && input.LA( 1 ) <= '\uFFFF' ) )
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:97:11: ( (
         // ' ' | '\\t' | '\\r' | '\\n' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:97:13: ( ' '
         // | '\\t' | '\\r' | '\\n' )
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
      }
   }
   


   // $ANTLR end "WS"
   
   public void mTokens() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:8: ( NEVER |
      // TODAY | TOMORROW | YESTERDAY | AT | ON | IN | OF | NEXT | AND | END | THE | STs | NOW | TONIGHT | MIDNIGHT |
      // MIDDAY | NOON | YEARS | MONTHS | WEEKS | DAYS | HOURS | MINUTES | SECONDS | MONTH | WEEKDAY | DATE_SEP | DOT |
      // COLON | MINUS | MINUS_A | COMMA | INT | A | AM | PM | NUM_STR | WS )
      int alt22 = 39;
      alt22 = dfa22.predict( input );
      switch ( alt22 )
      {
         case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:10:
            // NEVER
         {
            mNEVER();
            
         }
            break;
         case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:16:
            // TODAY
         {
            mTODAY();
            
         }
            break;
         case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:22:
            // TOMORROW
         {
            mTOMORROW();
            
         }
            break;
         case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:31:
            // YESTERDAY
         {
            mYESTERDAY();
            
         }
            break;
         case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:41: AT
         {
            mAT();
            
         }
            break;
         case 6:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:44: ON
         {
            mON();
            
         }
            break;
         case 7:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:47: IN
         {
            mIN();
            
         }
            break;
         case 8:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:50: OF
         {
            mOF();
            
         }
            break;
         case 9:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:53:
            // NEXT
         {
            mNEXT();
            
         }
            break;
         case 10:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:58: AND
         {
            mAND();
            
         }
            break;
         case 11:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:62: END
         {
            mEND();
            
         }
            break;
         case 12:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:66: THE
         {
            mTHE();
            
         }
            break;
         case 13:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:70: STs
         {
            mSTs();
            
         }
            break;
         case 14:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:74: NOW
         {
            mNOW();
            
         }
            break;
         case 15:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:78:
            // TONIGHT
         {
            mTONIGHT();
            
         }
            break;
         case 16:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:86:
            // MIDNIGHT
         {
            mMIDNIGHT();
            
         }
            break;
         case 17:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:95:
            // MIDDAY
         {
            mMIDDAY();
            
         }
            break;
         case 18:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:102:
            // NOON
         {
            mNOON();
            
         }
            break;
         case 19:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:107:
            // YEARS
         {
            mYEARS();
            
         }
            break;
         case 20:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:113:
            // MONTHS
         {
            mMONTHS();
            
         }
            break;
         case 21:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:120:
            // WEEKS
         {
            mWEEKS();
            
         }
            break;
         case 22:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:126:
            // DAYS
         {
            mDAYS();
            
         }
            break;
         case 23:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:131:
            // HOURS
         {
            mHOURS();
            
         }
            break;
         case 24:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:137:
            // MINUTES
         {
            mMINUTES();
            
         }
            break;
         case 25:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:145:
            // SECONDS
         {
            mSECONDS();
            
         }
            break;
         case 26:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:153:
            // MONTH
         {
            mMONTH();
            
         }
            break;
         case 27:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:159:
            // WEEKDAY
         {
            mWEEKDAY();
            
         }
            break;
         case 28:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:167:
            // DATE_SEP
         {
            mDATE_SEP();
            
         }
            break;
         case 29:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:176:
            // DOT
         {
            mDOT();
            
         }
            break;
         case 30:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:180:
            // COLON
         {
            mCOLON();
            
         }
            break;
         case 31:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:186:
            // MINUS
         {
            mMINUS();
            
         }
            break;
         case 32:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:192:
            // MINUS_A
         {
            mMINUS_A();
            
         }
            break;
         case 33:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:200:
            // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 34:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:206:
            // INT
         {
            mINT();
            
         }
            break;
         case 35:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:210: A
         {
            mA();
            
         }
            break;
         case 36:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:212: AM
         {
            mAM();
            
         }
            break;
         case 37:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:215: PM
         {
            mPM();
            
         }
            break;
         case 38:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:218:
            // NUM_STR
         {
            mNUM_STR();
            
         }
            break;
         case 39:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:226: WS
         {
            mWS();
            
         }
            break;
         
      }
      
   }
   
   protected DFA6 dfa6 = new DFA6( this );
   
   protected DFA7 dfa7 = new DFA7( this );
   
   protected DFA8 dfa8 = new DFA8( this );
   
   protected DFA10 dfa10 = new DFA10( this );
   
   protected DFA11 dfa11 = new DFA11( this );
   
   protected DFA12 dfa12 = new DFA12( this );
   
   protected DFA13 dfa13 = new DFA13( this );
   
   protected DFA14 dfa14 = new DFA14( this );
   
   protected DFA20 dfa20 = new DFA20( this );
   
   protected DFA22 dfa22 = new DFA22( this );
   
   static final String DFA6_eotS = "\3\uffff\1\6\3\uffff\1\11\2\uffff";
   
   static final String DFA6_eofS = "\12\uffff";
   
   static final String DFA6_minS = "\1\171\1\145\1\141\1\163\1\162\2\uffff\1\163\2\uffff";
   
   static final String DFA6_maxS = "\1\171\1\162\1\141\1\163\1\162\2\uffff\1\163\2\uffff";
   
   static final String DFA6_acceptS = "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA6_specialS = "\12\uffff}>";
   
   static final String[] DFA6_transitionS =
   { "\1\1", "\1\2\14\uffff\1\3", "\1\4", "\1\5", "\1\7", "", "", "\1\10", "",
    "" };
   
   static final short[] DFA6_eot = DFA.unpackEncodedString( DFA6_eotS );
   
   static final short[] DFA6_eof = DFA.unpackEncodedString( DFA6_eofS );
   
   static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars( DFA6_minS );
   
   static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars( DFA6_maxS );
   
   static final short[] DFA6_accept = DFA.unpackEncodedString( DFA6_acceptS );
   
   static final short[] DFA6_special = DFA.unpackEncodedString( DFA6_specialS );
   
   static final short[][] DFA6_transition;
   
   static
   {
      int numStates = DFA6_transitionS.length;
      DFA6_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA6_transition[ i ] = DFA.unpackEncodedString( DFA6_transitionS[ i ] );
      }
   }
   
   
   class DFA6 extends DFA
   {
      
      public DFA6( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 6;
         this.eot = DFA6_eot;
         this.eof = DFA6_eof;
         this.min = DFA6_min;
         this.max = DFA6_max;
         this.accept = DFA6_accept;
         this.special = DFA6_special;
         this.transition = DFA6_transition;
      }
      


      public String getDescription()
      {
         return "49:1: YEARS : ( 'years' | 'year' | 'yrs' | 'yr' );";
      }
   }
   
   static final String DFA7_eotS = "\3\uffff\1\6\3\uffff\1\11\2\uffff";
   
   static final String DFA7_eofS = "\12\uffff";
   
   static final String DFA7_minS = "\1\155\1\157\1\156\1\163\1\150\2\uffff\1\163\2\uffff";
   
   static final String DFA7_maxS = "\1\155\1\157\1\156\1\164\1\150\2\uffff\1\163\2\uffff";
   
   static final String DFA7_acceptS = "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA7_specialS = "\12\uffff}>";
   
   static final String[] DFA7_transitionS =
   { "\1\1", "\1\2", "\1\3", "\1\5\1\4", "\1\7", "", "", "\1\10", "", "" };
   
   static final short[] DFA7_eot = DFA.unpackEncodedString( DFA7_eotS );
   
   static final short[] DFA7_eof = DFA.unpackEncodedString( DFA7_eofS );
   
   static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars( DFA7_minS );
   
   static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars( DFA7_maxS );
   
   static final short[] DFA7_accept = DFA.unpackEncodedString( DFA7_acceptS );
   
   static final short[] DFA7_special = DFA.unpackEncodedString( DFA7_specialS );
   
   static final short[][] DFA7_transition;
   
   static
   {
      int numStates = DFA7_transitionS.length;
      DFA7_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA7_transition[ i ] = DFA.unpackEncodedString( DFA7_transitionS[ i ] );
      }
   }
   
   
   class DFA7 extends DFA
   {
      
      public DFA7( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 7;
         this.eot = DFA7_eot;
         this.eof = DFA7_eof;
         this.min = DFA7_min;
         this.max = DFA7_max;
         this.accept = DFA7_accept;
         this.special = DFA7_special;
         this.transition = DFA7_transition;
      }
      


      public String getDescription()
      {
         return "51:1: MONTHS : ( 'months' | 'month' | 'mons' | 'mon' );";
      }
   }
   
   static final String DFA8_eotS = "\3\uffff\1\6\3\uffff\1\11\2\uffff";
   
   static final String DFA8_eofS = "\12\uffff";
   
   static final String DFA8_minS = "\1\167\2\145\1\163\1\153\2\uffff\1\163\2\uffff";
   
   static final String DFA8_maxS = "\1\167\1\153\1\145\1\163\1\153\2\uffff\1\163\2\uffff";
   
   static final String DFA8_acceptS = "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA8_specialS = "\12\uffff}>";
   
   static final String[] DFA8_transitionS =
   { "\1\1", "\1\2\5\uffff\1\3", "\1\4", "\1\5", "\1\7", "", "", "\1\10", "",
    "" };
   
   static final short[] DFA8_eot = DFA.unpackEncodedString( DFA8_eotS );
   
   static final short[] DFA8_eof = DFA.unpackEncodedString( DFA8_eofS );
   
   static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars( DFA8_minS );
   
   static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars( DFA8_maxS );
   
   static final short[] DFA8_accept = DFA.unpackEncodedString( DFA8_acceptS );
   
   static final short[] DFA8_special = DFA.unpackEncodedString( DFA8_specialS );
   
   static final short[][] DFA8_transition;
   
   static
   {
      int numStates = DFA8_transitionS.length;
      DFA8_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA8_transition[ i ] = DFA.unpackEncodedString( DFA8_transitionS[ i ] );
      }
   }
   
   
   class DFA8 extends DFA
   {
      
      public DFA8( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 8;
         this.eot = DFA8_eot;
         this.eof = DFA8_eof;
         this.min = DFA8_min;
         this.max = DFA8_max;
         this.accept = DFA8_accept;
         this.special = DFA8_special;
         this.transition = DFA8_transition;
      }
      


      public String getDescription()
      {
         return "53:1: WEEKS : ( 'weeks' | 'week' | 'wks' | 'wk' );";
      }
   }
   
   static final String DFA10_eotS = "\1\uffff\1\4\1\uffff\1\7\4\uffff\1\12\2\uffff";
   
   static final String DFA10_eofS = "\13\uffff";
   
   static final String DFA10_minS = "\1\150\1\157\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
   
   static final String DFA10_maxS = "\1\150\1\162\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
   
   static final String DFA10_acceptS = "\4\uffff\1\5\1\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA10_specialS = "\13\uffff}>";
   
   static final String[] DFA10_transitionS =
   { "\1\1", "\1\2\2\uffff\1\3", "\1\5", "\1\6", "", "\1\10", "", "", "\1\11",
    "", "" };
   
   static final short[] DFA10_eot = DFA.unpackEncodedString( DFA10_eotS );
   
   static final short[] DFA10_eof = DFA.unpackEncodedString( DFA10_eofS );
   
   static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars( DFA10_minS );
   
   static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars( DFA10_maxS );
   
   static final short[] DFA10_accept = DFA.unpackEncodedString( DFA10_acceptS );
   
   static final short[] DFA10_special = DFA.unpackEncodedString( DFA10_specialS );
   
   static final short[][] DFA10_transition;
   
   static
   {
      int numStates = DFA10_transitionS.length;
      DFA10_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA10_transition[ i ] = DFA.unpackEncodedString( DFA10_transitionS[ i ] );
      }
   }
   
   
   class DFA10 extends DFA
   {
      
      public DFA10( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 10;
         this.eot = DFA10_eot;
         this.eof = DFA10_eof;
         this.min = DFA10_min;
         this.max = DFA10_max;
         this.accept = DFA10_accept;
         this.special = DFA10_special;
         this.transition = DFA10_transition;
      }
      


      public String getDescription()
      {
         return "57:1: HOURS : ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' );";
      }
   }
   
   static final String DFA11_eotS = "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
   
   static final String DFA11_eofS = "\14\uffff";
   
   static final String DFA11_minS = "\1\155\1\151\1\156\1\uffff\1\163\1\164\2\uffff\1\145\1\163\2\uffff";
   
   static final String DFA11_maxS = "\1\155\1\151\1\156\1\uffff\1\165\1\164\2\uffff\1\145\1\163\2\uffff";
   
   static final String DFA11_acceptS = "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
   
   static final String DFA11_specialS = "\14\uffff}>";
   
   static final String[] DFA11_transitionS =
   { "\1\1", "\1\2", "\1\4", "", "\1\6\1\uffff\1\5", "\1\10", "", "", "\1\11",
    "\1\12", "", "" };
   
   static final short[] DFA11_eot = DFA.unpackEncodedString( DFA11_eotS );
   
   static final short[] DFA11_eof = DFA.unpackEncodedString( DFA11_eofS );
   
   static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars( DFA11_minS );
   
   static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars( DFA11_maxS );
   
   static final short[] DFA11_accept = DFA.unpackEncodedString( DFA11_acceptS );
   
   static final short[] DFA11_special = DFA.unpackEncodedString( DFA11_specialS );
   
   static final short[][] DFA11_transition;
   
   static
   {
      int numStates = DFA11_transitionS.length;
      DFA11_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA11_transition[ i ] = DFA.unpackEncodedString( DFA11_transitionS[ i ] );
      }
   }
   
   
   class DFA11 extends DFA
   {
      
      public DFA11( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 11;
         this.eot = DFA11_eot;
         this.eof = DFA11_eof;
         this.min = DFA11_min;
         this.max = DFA11_max;
         this.accept = DFA11_accept;
         this.special = DFA11_special;
         this.transition = DFA11_transition;
      }
      


      public String getDescription()
      {
         return "59:1: MINUTES : ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' );";
      }
   }
   
   static final String DFA12_eotS = "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
   
   static final String DFA12_eofS = "\14\uffff";
   
   static final String DFA12_minS = "\1\163\1\145\1\143\1\uffff\1\157\1\156\2\uffff\1\144\1\163\2\uffff";
   
   static final String DFA12_maxS = "\1\163\1\145\1\143\1\uffff\1\163\1\156\2\uffff\1\144\1\163\2\uffff";
   
   static final String DFA12_acceptS = "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
   
   static final String DFA12_specialS = "\14\uffff}>";
   
   static final String[] DFA12_transitionS =
   { "\1\1", "\1\2", "\1\4", "", "\1\5\3\uffff\1\6", "\1\10", "", "", "\1\11",
    "\1\12", "", "" };
   
   static final short[] DFA12_eot = DFA.unpackEncodedString( DFA12_eotS );
   
   static final short[] DFA12_eof = DFA.unpackEncodedString( DFA12_eofS );
   
   static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars( DFA12_minS );
   
   static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars( DFA12_maxS );
   
   static final short[] DFA12_accept = DFA.unpackEncodedString( DFA12_acceptS );
   
   static final short[] DFA12_special = DFA.unpackEncodedString( DFA12_specialS );
   
   static final short[][] DFA12_transition;
   
   static
   {
      int numStates = DFA12_transitionS.length;
      DFA12_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA12_transition[ i ] = DFA.unpackEncodedString( DFA12_transitionS[ i ] );
      }
   }
   
   
   class DFA12 extends DFA
   {
      
      public DFA12( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 12;
         this.eot = DFA12_eot;
         this.eof = DFA12_eof;
         this.min = DFA12_min;
         this.max = DFA12_max;
         this.accept = DFA12_accept;
         this.special = DFA12_special;
         this.transition = DFA12_transition;
      }
      


      public String getDescription()
      {
         return "61:1: SECONDS : ( 'seconds' | 'second' | 'secs' | 'sec' | 's' );";
      }
   }
   
   static final String DFA13_eotS = "\23\uffff\1\40\1\42\1\44\1\46\1\50\1\uffff\1\52\1\54\1\56\1\60"
      + "\1\62\1\64\16\uffff\1\66\11\uffff";
   
   static final String DFA13_eofS = "\67\uffff";
   
   static final String DFA13_minS = "\2\141\1\145\1\141\1\160\1\145\1\143\1\157\1\145\1\156\1\154\1"
      + "\142\2\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171\1\162"
      + "\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145\11\uffff";
   
   static final String DFA13_maxS = "\1\163\1\165\1\145\1\141\1\165\1\145\1\143\1\157\1\145\2\156\1"
      + "\142\1\171\1\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171"
      + "\1\162\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145"
      + "\11\uffff";
   
   static final String DFA13_acceptS = "\30\uffff\1\11\6\uffff\1\1\1\2\1\12\1\13\1\14\1\15\1\3\1\4\1\5"
      + "\1\6\1\7\1\10\1\16\1\17\1\uffff\1\22\1\23\1\24\1\25\1\26\1\27\1"
      + "\30\1\20\1\21";
   
   static final String DFA13_specialS = "\67\uffff}>";
   
   static final String[] DFA13_transitionS =
   {
    "\1\4\2\uffff\1\10\1\uffff\1\2\3\uffff\1\1\2\uffff\1\3\1\7\1"
       + "\6\3\uffff\1\5", "\1\11\23\uffff\1\12", "\1\13", "\1\14",
    "\1\15\4\uffff\1\16", "\1\17", "\1\20", "\1\21", "\1\22", "\1\23",
    "\1\25\1\uffff\1\24", "\1\26", "\1\27\6\uffff\1\30", "\1\31", "\1\32",
    "\1\33", "\1\34", "\1\35", "\1\36", "\1\37", "\1\41", "\1\43", "\1\45",
    "\1\47", "", "\1\51", "\1\53", "\1\55", "\1\57", "\1\61", "\1\63", "", "",
    "", "", "", "", "", "", "", "", "", "", "", "", "\1\65", "", "", "", "",
    "", "", "", "", "" };
   
   static final short[] DFA13_eot = DFA.unpackEncodedString( DFA13_eotS );
   
   static final short[] DFA13_eof = DFA.unpackEncodedString( DFA13_eofS );
   
   static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars( DFA13_minS );
   
   static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars( DFA13_maxS );
   
   static final short[] DFA13_accept = DFA.unpackEncodedString( DFA13_acceptS );
   
   static final short[] DFA13_special = DFA.unpackEncodedString( DFA13_specialS );
   
   static final short[][] DFA13_transition;
   
   static
   {
      int numStates = DFA13_transitionS.length;
      DFA13_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA13_transition[ i ] = DFA.unpackEncodedString( DFA13_transitionS[ i ] );
      }
   }
   
   
   class DFA13 extends DFA
   {
      
      public DFA13( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 13;
         this.eot = DFA13_eot;
         this.eof = DFA13_eof;
         this.min = DFA13_min;
         this.max = DFA13_max;
         this.accept = DFA13_accept;
         this.special = DFA13_special;
         this.transition = DFA13_transition;
      }
      


      public String getDescription()
      {
         return "63:1: MONTH : ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' | 'december' | 'dec' );";
      }
   }
   
   static final String DFA14_eotS = "\15\uffff\1\25\1\27\1\31\1\33\1\35\1\37\1\41\16\uffff";
   
   static final String DFA14_eofS = "\42\uffff";
   
   static final String DFA14_minS = "\1\146\1\157\1\150\1\145\1\162\1\141\1\156\1\145\1\165\1\144\1"
      + "\151\1\164\1\156\1\144\1\163\1\162\1\156\1\144\1\165\1\144\16\uffff";
   
   static final String DFA14_maxS = "\1\167\1\157\1\165\1\145\1\162\1\165\1\156\1\145\1\165\1\144\1"
      + "\151\1\164\1\156\1\144\1\163\1\162\1\156\1\144\1\165\1\144\16\uffff";
   
   static final String DFA14_acceptS = "\24\uffff\1\1\1\2\1\3\1\4\1\7\1\10\1\5\1\6\1\11\1\12\1\13\1\14"
      + "\1\15\1\16";
   
   static final String DFA14_specialS = "\42\uffff}>";
   
   static final String[] DFA14_transitionS =
   { "\1\4\6\uffff\1\1\5\uffff\1\5\1\2\2\uffff\1\3", "\1\6",
    "\1\10\14\uffff\1\7", "\1\11", "\1\12", "\1\13\23\uffff\1\14", "\1\15",
    "\1\16", "\1\17", "\1\20", "\1\21", "\1\22", "\1\23", "\1\24", "\1\26",
    "\1\30", "\1\32", "\1\34", "\1\36", "\1\40", "", "", "", "", "", "", "",
    "", "", "", "", "", "", "" };
   
   static final short[] DFA14_eot = DFA.unpackEncodedString( DFA14_eotS );
   
   static final short[] DFA14_eof = DFA.unpackEncodedString( DFA14_eofS );
   
   static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars( DFA14_minS );
   
   static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars( DFA14_maxS );
   
   static final short[] DFA14_accept = DFA.unpackEncodedString( DFA14_acceptS );
   
   static final short[] DFA14_special = DFA.unpackEncodedString( DFA14_specialS );
   
   static final short[][] DFA14_transition;
   
   static
   {
      int numStates = DFA14_transitionS.length;
      DFA14_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA14_transition[ i ] = DFA.unpackEncodedString( DFA14_transitionS[ i ] );
      }
   }
   
   
   class DFA14 extends DFA
   {
      
      public DFA14( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 14;
         this.eot = DFA14_eot;
         this.eof = DFA14_eof;
         this.min = DFA14_min;
         this.max = DFA14_max;
         this.accept = DFA14_accept;
         this.special = DFA14_special;
         this.transition = DFA14_transition;
      }
      


      public String getDescription()
      {
         return "68:1: WEEKDAY : ( 'monday' | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' | 'thursday' | 'thu' | 'friday' | 'fri' | 'saturday' | 'sat' | 'sunday' | 'sun' );";
      }
   }
   
   static final String DFA20_eotS = "\14\uffff";
   
   static final String DFA20_eofS = "\14\uffff";
   
   static final String DFA20_minS = "\1\145\1\uffff\1\145\1\uffff\1\145\7\uffff";
   
   static final String DFA20_maxS = "\1\164\1\uffff\1\167\1\uffff\1\151\7\uffff";
   
   static final String DFA20_acceptS = "\1\uffff\1\1\1\uffff\1\4\1\uffff\1\7\1\10\1\2\1\3\1\11\1\5\1\6";
   
   static final String DFA20_specialS = "\14\uffff}>";
   
   static final String[] DFA20_transitionS =
   { "\1\5\1\3\7\uffff\1\6\1\1\3\uffff\1\4\1\2", "",
    "\1\11\2\uffff\1\10\16\uffff\1\7", "", "\1\13\3\uffff\1\12", "", "", "",
    "", "", "", "" };
   
   static final short[] DFA20_eot = DFA.unpackEncodedString( DFA20_eotS );
   
   static final short[] DFA20_eof = DFA.unpackEncodedString( DFA20_eofS );
   
   static final char[] DFA20_min = DFA.unpackEncodedStringToUnsignedChars( DFA20_minS );
   
   static final char[] DFA20_max = DFA.unpackEncodedStringToUnsignedChars( DFA20_maxS );
   
   static final short[] DFA20_accept = DFA.unpackEncodedString( DFA20_acceptS );
   
   static final short[] DFA20_special = DFA.unpackEncodedString( DFA20_specialS );
   
   static final short[][] DFA20_transition;
   
   static
   {
      int numStates = DFA20_transitionS.length;
      DFA20_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA20_transition[ i ] = DFA.unpackEncodedString( DFA20_transitionS[ i ] );
      }
   }
   
   
   class DFA20 extends DFA
   {
      
      public DFA20( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 20;
         this.eot = DFA20_eot;
         this.eof = DFA20_eof;
         this.min = DFA20_min;
         this.max = DFA20_max;
         this.accept = DFA20_accept;
         this.special = DFA20_special;
         this.transition = DFA20_transition;
      }
      


      public String getDescription()
      {
         return "92:1: NUM_STR : ( 'one' | 'two' | 'three' | 'four' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' );";
      }
   }
   
   static final String DFA22_eotS = "\5\uffff\1\46\3\uffff\1\53\1\uffff\1\56\1\uffff\1\61\6\uffff\1"
      + "\63\14\uffff\1\12\5\uffff\1\74\26\uffff\1\101\3\uffff";
   
   static final String DFA22_eofS = "\102\uffff";
   
   static final String DFA22_minS = "\1\11\1\144\2\145\1\uffff\1\155\1\143\1\uffff\1\151\1\141\1\uffff"
      + "\1\141\2\145\2\uffff\1\145\3\uffff\1\141\3\uffff\1\u524d\1\uc804"
      + "\2\uffff\1\166\1\157\1\uffff\1\144\1\uffff\1\145\1\uffff\1\141\3"
      + "\uffff\1\145\2\uffff\1\143\1\uffff\1\144\1\156\1\uffff\1\144\15"
      + "\uffff\2\144\3\uffff";
   
   static final String DFA22_maxS = "\1\uc624\1\157\1\167\1\162\1\uffff\1\165\1\156\1\uffff\1\156\1"
      + "\165\1\uffff\1\157\1\153\1\145\2\uffff\1\162\3\uffff\1\141\3\uffff"
      + "\1\u5f8c\1\ud6c4\2\uffff\1\170\1\167\1\uffff\1\156\1\uffff\1\165"
      + "\1\uffff\1\163\3\uffff\1\145\2\uffff\1\166\1\uffff\2\156\1\uffff"
      + "\1\145\15\uffff\1\156\1\144\3\uffff";
   
   static final String DFA22_acceptS = "\4\uffff\1\5\2\uffff\1\7\2\uffff\1\15\3\uffff\1\27\1\32\1\uffff"
      + "\1\34\1\35\1\36\1\uffff\1\41\1\42\1\44\2\uffff\1\45\1\47\2\uffff"
      + "\1\46\1\uffff\1\3\1\uffff\1\33\1\uffff\1\23\1\12\1\43\1\uffff\1"
      + "\10\1\13\1\uffff\1\31\2\uffff\1\30\1\uffff\1\25\1\26\1\40\1\37\1"
      + "\1\1\11\1\16\1\22\1\2\1\17\1\14\1\4\1\6\2\uffff\1\20\1\21\1\24";
   
   static final String DFA22_specialS = "\102\uffff}>";
   
   static final String[] DFA22_transitionS =
   {
    "\2\33\2\uffff\1\33\22\uffff\1\33\13\uffff\1\25\1\24\1\22\1"
       + "\21\12\26\1\23\5\uffff\1\4\40\uffff\1\5\2\uffff\1\15\1\10\1"
       + "\20\1\uffff\1\16\1\7\1\17\2\uffff\1\13\1\1\1\6\1\32\1\uffff"
       + "\1\12\1\11\1\2\2\uffff\1\14\1\uffff\1\3\u4d90\uffff\1\27\1\32"
       + "\u053c\uffff\1\30\u0b2b\uffff\1\21\u0770\uffff\1\21\u0122\uffff"
       + "\1\21\u5f1b\uffff\1\31",
    "\1\12\1\34\3\uffff\1\36\5\uffff\1\35",
    "\1\36\2\uffff\1\41\4\uffff\1\40\1\uffff\1\37\5\uffff\1\42"
       + "\1\uffff\1\36", "\1\43\14\uffff\1\44", "",
    "\1\27\1\45\1\uffff\1\17\3\uffff\1\4\1\17",
    "\1\17\2\uffff\1\50\7\uffff\1\47", "", "\1\36\4\uffff\1\51",
    "\1\42\3\uffff\1\52\3\uffff\1\36\12\uffff\1\12\1\42", "",
    "\1\17\7\uffff\1\54\5\uffff\1\55", "\1\57\5\uffff\1\60", "\1\17", "", "",
    "\1\17\11\uffff\1\36\2\uffff\1\42", "", "", "", "\1\62", "", "", "",
    "\1\27\u0d3e\uffff\1\32", "\1\27\u0ebf\uffff\1\32", "", "",
    "\1\64\1\uffff\1\65", "\1\67\6\uffff\1\17\1\66", "",
    "\1\70\10\uffff\1\40\1\71", "", "\1\72\14\uffff\1\36\2\uffff\1\42", "",
    "\1\44\21\uffff\1\73", "", "", "", "\1\36", "", "",
    "\1\53\14\uffff\1\17\5\uffff\1\36", "", "\1\75\11\uffff\1\56", "\1\76", "",
    "\1\42\1\60", "", "", "", "", "", "", "", "", "", "", "", "", "",
    "\1\100\11\uffff\1\77", "\1\42", "", "", "" };
   
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
         return "1:1: Tokens : ( NEVER | TODAY | TOMORROW | YESTERDAY | AT | ON | IN | OF | NEXT | AND | END | THE | STs | NOW | TONIGHT | MIDNIGHT | MIDDAY | NOON | YEARS | MONTHS | WEEKS | DAYS | HOURS | MINUTES | SECONDS | MONTH | WEEKDAY | DATE_SEP | DOT | COLON | MINUS | MINUS_A | COMMA | INT | A | AM | PM | NUM_STR | WS );";
      }
   }
   
}
