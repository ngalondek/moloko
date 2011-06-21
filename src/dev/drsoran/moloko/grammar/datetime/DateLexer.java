// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g 2011-06-20 16:57:52

package dev.drsoran.moloko.grammar.datetime;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;


public class DateLexer extends Lexer
{
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
   
   public static final int WEEKS = 9;
   
   public static final int WEEKDAY = 23;
   
   public static final int WS = 33;
   
   public static final int IN = 24;
   
   public static final int TONIGHT = 29;
   
   public static final int COMMA = 20;
   
   public static final int MONTHS = 10;
   
   public static final int NEXT = 22;
   
   public static final int DATE_SEP = 16;
   
   public static final int NEVER = 30;
   
   public static final int DOT = 13;
   
   public static final int END = 26;
   
   public static final int YESTERDAY = 32;
   
   public static final int TOMORROW = 31;
   
   

   // delegates
   // delegators
   
   public DateLexer()
   {
      ;
   }
   


   public DateLexer( CharStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public DateLexer( CharStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   public String getGrammarFileName()
   {
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g";
   }
   


   // $ANTLR start "A"
   public final void mA() throws RecognitionException
   {
      try
      {
         int _type = A;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:434:11: (
         // 'a' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:434:13: 'a'
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
   
   // $ANTLR start "OF"
   public final void mOF() throws RecognitionException
   {
      try
      {
         int _type = OF;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:436:11: (
         // 'of' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:436:13:
         // 'of'
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
   
   // $ANTLR start "ON"
   public final void mON() throws RecognitionException
   {
      try
      {
         int _type = ON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:438:11: (
         // 'on' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:438:13:
         // 'on'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:440:11: (
         // 'in' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:440:13:
         // 'in'
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
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException
   {
      try
      {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:442:11: (
         // 'and' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:442:13:
         // 'and'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:444:11: (
         // 'end' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:444:13:
         // 'end'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:446:11: (
         // 'the' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:446:13:
         // 'the'
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
   
   // $ANTLR start "NOW"
   public final void mNOW() throws RecognitionException
   {
      try
      {
         int _type = NOW;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:448:11: (
         // 'now' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:448:13:
         // 'now'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:450:11: (
         // 'tonight' | 'ton' )
         int alt1 = 2;
         int LA1_0 = input.LA( 1 );
         
         if ( ( LA1_0 == 't' ) )
         {
            int LA1_1 = input.LA( 2 );
            
            if ( ( LA1_1 == 'o' ) )
            {
               int LA1_2 = input.LA( 3 );
               
               if ( ( LA1_2 == 'n' ) )
               {
                  int LA1_3 = input.LA( 4 );
                  
                  if ( ( LA1_3 == 'i' ) )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:450:13:
               // 'tonight'
            {
               match( "tonight" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:450:25:
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
   
   // $ANTLR start "NEVER"
   public final void mNEVER() throws RecognitionException
   {
      try
      {
         int _type = NEVER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:452:11: (
         // 'never' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:452:13:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:454:11: (
         // 'today' | 'tod' )
         int alt2 = 2;
         int LA2_0 = input.LA( 1 );
         
         if ( ( LA2_0 == 't' ) )
         {
            int LA2_1 = input.LA( 2 );
            
            if ( ( LA2_1 == 'o' ) )
            {
               int LA2_2 = input.LA( 3 );
               
               if ( ( LA2_2 == 'd' ) )
               {
                  int LA2_3 = input.LA( 4 );
                  
                  if ( ( LA2_3 == 'a' ) )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:454:13:
               // 'today'
            {
               match( "today" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:454:23:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:456:11: (
         // 'tomorrow' | 'tom' | 'tmr' )
         int alt3 = 3;
         int LA3_0 = input.LA( 1 );
         
         if ( ( LA3_0 == 't' ) )
         {
            int LA3_1 = input.LA( 2 );
            
            if ( ( LA3_1 == 'o' ) )
            {
               int LA3_2 = input.LA( 3 );
               
               if ( ( LA3_2 == 'm' ) )
               {
                  int LA3_4 = input.LA( 4 );
                  
                  if ( ( LA3_4 == 'o' ) )
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
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else if ( ( LA3_1 == 'm' ) )
            {
               alt3 = 3;
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:456:13:
               // 'tomorrow'
            {
               match( "tomorrow" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:456:26:
               // 'tom'
            {
               match( "tom" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:456:34:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:11: (
         // 'yesterday' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:13:
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
   
   // $ANTLR start "NEXT"
   public final void mNEXT() throws RecognitionException
   {
      try
      {
         int _type = NEXT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:460:11: (
         // 'next' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:460:13:
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
   
   // $ANTLR start "STs"
   public final void mSTs() throws RecognitionException
   {
      try
      {
         int _type = STs;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:462:11: (
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:462:13:
               // 'st'
            {
               match( "st" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:462:20:
               // 'th'
            {
               match( "th" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:462:27:
               // 'rd'
            {
               match( "rd" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:462:34:
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
   
   // $ANTLR start "YEARS"
   public final void mYEARS() throws RecognitionException
   {
      try
      {
         int _type = YEARS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:11: (
         // 'years' | 'year' | 'yrs' | 'yr' )
         int alt5 = 4;
         alt5 = dfa5.predict( input );
         switch ( alt5 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:13:
               // 'years'
            {
               match( "years" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:23:
               // 'year'
            {
               match( "year" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:32:
               // 'yrs'
            {
               match( "yrs" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:40:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:466:11: (
         // 'months' | 'month' | 'mons' | 'mon' )
         int alt6 = 4;
         alt6 = dfa6.predict( input );
         switch ( alt6 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:466:13:
               // 'months'
            {
               match( "months" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:466:24:
               // 'month'
            {
               match( "month" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:466:34:
               // 'mons'
            {
               match( "mons" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:466:43:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:468:11: (
         // 'weeks' | 'week' | 'wks' | 'wk' )
         int alt7 = 4;
         alt7 = dfa7.predict( input );
         switch ( alt7 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:468:13:
               // 'weeks'
            {
               match( "weeks" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:468:23:
               // 'week'
            {
               match( "week" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:468:32:
               // 'wks'
            {
               match( "wks" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:468:40:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:470:11: (
         // 'days' | 'day' | 'd' )
         int alt8 = 3;
         int LA8_0 = input.LA( 1 );
         
         if ( ( LA8_0 == 'd' ) )
         {
            int LA8_1 = input.LA( 2 );
            
            if ( ( LA8_1 == 'a' ) )
            {
               int LA8_2 = input.LA( 3 );
               
               if ( ( LA8_2 == 'y' ) )
               {
                  int LA8_4 = input.LA( 4 );
                  
                  if ( ( LA8_4 == 's' ) )
                  {
                     alt8 = 1;
                  }
                  else
                  {
                     alt8 = 2;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        8,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               alt8 = 3;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  8,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt8 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:470:13:
               // 'days'
            {
               match( "days" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:470:22:
               // 'day'
            {
               match( "day" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:470:30:
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
   
   // $ANTLR start "MONTH"
   public final void mMONTH() throws RecognitionException
   {
      try
      {
         int _type = MONTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:472:11: (
         // 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july'
         // | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' |
         // 'december' | 'dec' )
         int alt9 = 24;
         alt9 = dfa9.predict( input );
         switch ( alt9 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:472:13:
               // 'january'
            {
               match( "january" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:472:27:
               // 'jan'
            {
               match( "jan" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:472:36:
               // 'february'
            {
               match( "february" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:472:49:
               // 'feb'
            {
               match( "feb" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:472:61:
               // 'march'
            {
               match( "march" );
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:472:71:
               // 'mar'
            {
               match( "mar" );
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:472:84:
               // 'april'
            {
               match( "april" );
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:472:94:
               // 'apr'
            {
               match( "apr" );
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:473:13:
               // 'may'
            {
               match( "may" );
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:473:27:
               // 'june'
            {
               match( "june" );
               
            }
               break;
            case 11:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:473:36:
               // 'jun'
            {
               match( "jun" );
               
            }
               break;
            case 12:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:473:49:
               // 'july'
            {
               match( "july" );
               
            }
               break;
            case 13:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:473:61:
               // 'jul'
            {
               match( "jul" );
               
            }
               break;
            case 14:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:473:71:
               // 'august'
            {
               match( "august" );
               
            }
               break;
            case 15:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:473:84:
               // 'aug'
            {
               match( "aug" );
               
            }
               break;
            case 16:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:474:13:
               // 'september'
            {
               match( "september" );
               
            }
               break;
            case 17:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:474:27:
               // 'sept'
            {
               match( "sept" );
               
            }
               break;
            case 18:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:474:36:
               // 'sep'
            {
               match( "sep" );
               
            }
               break;
            case 19:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:474:49:
               // 'october'
            {
               match( "october" );
               
            }
               break;
            case 20:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:474:61:
               // 'oct'
            {
               match( "oct" );
               
            }
               break;
            case 21:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:474:71:
               // 'november'
            {
               match( "november" );
               
            }
               break;
            case 22:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:474:84:
               // 'nov'
            {
               match( "nov" );
               
            }
               break;
            case 23:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:475:13:
               // 'december'
            {
               match( "december" );
               
            }
               break;
            case 24:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:475:27:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:477:11: (
         // 'monday' | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' | 'thursday' | 'thu' | 'friday' | 'fri' |
         // 'saturday' | 'sat' | 'sunday' | 'sun' )
         int alt10 = 14;
         alt10 = dfa10.predict( input );
         switch ( alt10 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:477:13:
               // 'monday'
            {
               match( "monday" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:477:26:
               // 'mon'
            {
               match( "mon" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:477:34:
               // 'tuesday'
            {
               match( "tuesday" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:477:46:
               // 'tue'
            {
               match( "tue" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:477:54:
               // 'wednesday'
            {
               match( "wednesday" );
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:477:68:
               // 'wed'
            {
               match( "wed" );
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:478:13:
               // 'thursday'
            {
               match( "thursday" );
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:478:26:
               // 'thu'
            {
               match( "thu" );
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:478:34:
               // 'friday'
            {
               match( "friday" );
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:478:46:
               // 'fri'
            {
               match( "fri" );
               
            }
               break;
            case 11:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:478:54:
               // 'saturday'
            {
               match( "saturday" );
               
            }
               break;
            case 12:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:478:68:
               // 'sat'
            {
               match( "sat" );
               
            }
               break;
            case 13:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:479:13:
               // 'sunday'
            {
               match( "sunday" );
               
            }
               break;
            case 14:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:479:26:
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
   
   // $ANTLR start "NUM_STR"
   public final void mNUM_STR() throws RecognitionException
   {
      try
      {
         int _type = NUM_STR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:11: (
         // 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' )
         int alt11 = 10;
         alt11 = dfa11.predict( input );
         switch ( alt11 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:13:
               // 'one'
            {
               match( "one" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:21:
               // 'two'
            {
               match( "two" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:29:
               // 'three'
            {
               match( "three" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:39:
               // 'four'
            {
               match( "four" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:48:
               // 'five'
            {
               match( "five" );
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:57:
               // 'six'
            {
               match( "six" );
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:65:
               // 'seven'
            {
               match( "seven" );
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:75:
               // 'eight'
            {
               match( "eight" );
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:85:
               // 'nine'
            {
               match( "nine" );
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:94:
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
   
   // $ANTLR start "DATE_SEP"
   public final void mDATE_SEP() throws RecognitionException
   {
      try
      {
         int _type = DATE_SEP;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:483:11: (
         // '/' | '\\u5E74' | '\\u6708' | '\\u65E5' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:485:11: (
         // '.' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:485:13: '.'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:487:11: (
         // ':' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:487:13: ':'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:489:11: (
         // '-' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:489:13: '-'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:491:11: (
         // '-a' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:491:13:
         // '-a'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:493:11: (
         // ',' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:493:13: ','
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:495:11: ( (
         // '0' .. '9' )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:495:13: (
         // '0' .. '9' )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:495:13:
            // ( '0' .. '9' )+
            int cnt12 = 0;
            loop12: do
            {
               int alt12 = 2;
               int LA12_0 = input.LA( 1 );
               
               if ( ( ( LA12_0 >= '0' && LA12_0 <= '9' ) ) )
               {
                  alt12 = 1;
               }
               
               switch ( alt12 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:495:13:
                     // '0' .. '9'
                  {
                     matchRange( '0', '9' );
                     
                  }
                     break;
                  
                  default :
                     if ( cnt12 >= 1 )
                        break loop12;
                     EarlyExitException eee = new EarlyExitException( 12, input );
                     throw eee;
               }
               cnt12++;
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
   
   // $ANTLR start "WS"
   public final void mWS() throws RecognitionException
   {
      try
      {
         int _type = WS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:497:11: ( (
         // ' ' | '\\t' | '\\r' | '\\n' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:497:13: (
         // ' ' | '\\t' | '\\r' | '\\n' )
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
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:8: ( A | OF
      // | ON | IN | AND | END | THE | NOW | TONIGHT | NEVER | TODAY | TOMORROW | YESTERDAY | NEXT | STs | YEARS |
      // MONTHS | WEEKS | DAYS | MONTH | WEEKDAY | NUM_STR | DATE_SEP | DOT | COLON | MINUS | MINUS_A | COMMA | INT | WS
      // )
      int alt13 = 30;
      alt13 = dfa13.predict( input );
      switch ( alt13 )
      {
         case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:10: A
         {
            mA();
            
         }
            break;
         case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:12: OF
         {
            mOF();
            
         }
            break;
         case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:15: ON
         {
            mON();
            
         }
            break;
         case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:18: IN
         {
            mIN();
            
         }
            break;
         case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:21:
            // AND
         {
            mAND();
            
         }
            break;
         case 6:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:25:
            // END
         {
            mEND();
            
         }
            break;
         case 7:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:29:
            // THE
         {
            mTHE();
            
         }
            break;
         case 8:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:33:
            // NOW
         {
            mNOW();
            
         }
            break;
         case 9:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:37:
            // TONIGHT
         {
            mTONIGHT();
            
         }
            break;
         case 10:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:45:
            // NEVER
         {
            mNEVER();
            
         }
            break;
         case 11:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:51:
            // TODAY
         {
            mTODAY();
            
         }
            break;
         case 12:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:57:
            // TOMORROW
         {
            mTOMORROW();
            
         }
            break;
         case 13:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:66:
            // YESTERDAY
         {
            mYESTERDAY();
            
         }
            break;
         case 14:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:76:
            // NEXT
         {
            mNEXT();
            
         }
            break;
         case 15:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:81:
            // STs
         {
            mSTs();
            
         }
            break;
         case 16:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:85:
            // YEARS
         {
            mYEARS();
            
         }
            break;
         case 17:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:91:
            // MONTHS
         {
            mMONTHS();
            
         }
            break;
         case 18:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:98:
            // WEEKS
         {
            mWEEKS();
            
         }
            break;
         case 19:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:104:
            // DAYS
         {
            mDAYS();
            
         }
            break;
         case 20:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:109:
            // MONTH
         {
            mMONTH();
            
         }
            break;
         case 21:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:115:
            // WEEKDAY
         {
            mWEEKDAY();
            
         }
            break;
         case 22:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:123:
            // NUM_STR
         {
            mNUM_STR();
            
         }
            break;
         case 23:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:131:
            // DATE_SEP
         {
            mDATE_SEP();
            
         }
            break;
         case 24:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:140:
            // DOT
         {
            mDOT();
            
         }
            break;
         case 25:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:144:
            // COLON
         {
            mCOLON();
            
         }
            break;
         case 26:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:150:
            // MINUS
         {
            mMINUS();
            
         }
            break;
         case 27:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:156:
            // MINUS_A
         {
            mMINUS_A();
            
         }
            break;
         case 28:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:164:
            // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 29:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:170:
            // INT
         {
            mINT();
            
         }
            break;
         case 30:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:174:
            // WS
         {
            mWS();
            
         }
            break;
         
      }
      
   }
   
   protected DFA5 dfa5 = new DFA5( this );
   
   protected DFA6 dfa6 = new DFA6( this );
   
   protected DFA7 dfa7 = new DFA7( this );
   
   protected DFA9 dfa9 = new DFA9( this );
   
   protected DFA10 dfa10 = new DFA10( this );
   
   protected DFA11 dfa11 = new DFA11( this );
   
   protected DFA13 dfa13 = new DFA13( this );
   
   static final String DFA5_eotS = "\3\uffff\1\6\3\uffff\1\11\2\uffff";
   
   static final String DFA5_eofS = "\12\uffff";
   
   static final String DFA5_minS = "\1\171\1\145\1\141\1\163\1\162\2\uffff\1\163\2\uffff";
   
   static final String DFA5_maxS = "\1\171\1\162\1\141\1\163\1\162\2\uffff\1\163\2\uffff";
   
   static final String DFA5_acceptS = "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA5_specialS = "\12\uffff}>";
   
   static final String[] DFA5_transitionS =
   { "\1\1", "\1\2\14\uffff\1\3", "\1\4", "\1\5", "\1\7", "", "", "\1\10", "",
    "" };
   
   static final short[] DFA5_eot = DFA.unpackEncodedString( DFA5_eotS );
   
   static final short[] DFA5_eof = DFA.unpackEncodedString( DFA5_eofS );
   
   static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars( DFA5_minS );
   
   static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars( DFA5_maxS );
   
   static final short[] DFA5_accept = DFA.unpackEncodedString( DFA5_acceptS );
   
   static final short[] DFA5_special = DFA.unpackEncodedString( DFA5_specialS );
   
   static final short[][] DFA5_transition;
   
   static
   {
      int numStates = DFA5_transitionS.length;
      DFA5_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA5_transition[ i ] = DFA.unpackEncodedString( DFA5_transitionS[ i ] );
      }
   }
   
   
   class DFA5 extends DFA
   {
      
      public DFA5( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 5;
         this.eot = DFA5_eot;
         this.eof = DFA5_eof;
         this.min = DFA5_min;
         this.max = DFA5_max;
         this.accept = DFA5_accept;
         this.special = DFA5_special;
         this.transition = DFA5_transition;
      }
      


      public String getDescription()
      {
         return "464:1: YEARS : ( 'years' | 'year' | 'yrs' | 'yr' );";
      }
   }
   
   static final String DFA6_eotS = "\3\uffff\1\6\3\uffff\1\11\2\uffff";
   
   static final String DFA6_eofS = "\12\uffff";
   
   static final String DFA6_minS = "\1\155\1\157\1\156\1\163\1\150\2\uffff\1\163\2\uffff";
   
   static final String DFA6_maxS = "\1\155\1\157\1\156\1\164\1\150\2\uffff\1\163\2\uffff";
   
   static final String DFA6_acceptS = "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA6_specialS = "\12\uffff}>";
   
   static final String[] DFA6_transitionS =
   { "\1\1", "\1\2", "\1\3", "\1\5\1\4", "\1\7", "", "", "\1\10", "", "" };
   
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
         return "466:1: MONTHS : ( 'months' | 'month' | 'mons' | 'mon' );";
      }
   }
   
   static final String DFA7_eotS = "\3\uffff\1\6\3\uffff\1\11\2\uffff";
   
   static final String DFA7_eofS = "\12\uffff";
   
   static final String DFA7_minS = "\1\167\2\145\1\163\1\153\2\uffff\1\163\2\uffff";
   
   static final String DFA7_maxS = "\1\167\1\153\1\145\1\163\1\153\2\uffff\1\163\2\uffff";
   
   static final String DFA7_acceptS = "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA7_specialS = "\12\uffff}>";
   
   static final String[] DFA7_transitionS =
   { "\1\1", "\1\2\5\uffff\1\3", "\1\4", "\1\5", "\1\7", "", "", "\1\10", "",
    "" };
   
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
         return "468:1: WEEKS : ( 'weeks' | 'week' | 'wks' | 'wk' );";
      }
   }
   
   static final String DFA9_eotS = "\23\uffff\1\40\1\42\1\44\1\46\1\50\1\uffff\1\52\1\54\1\56\1\60"
      + "\1\62\1\64\16\uffff\1\66\11\uffff";
   
   static final String DFA9_eofS = "\67\uffff";
   
   static final String DFA9_minS = "\2\141\1\145\1\141\1\160\1\145\1\143\1\157\1\145\1\156\1\154\1"
      + "\142\2\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171\1\162"
      + "\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145\11\uffff";
   
   static final String DFA9_maxS = "\1\163\1\165\1\145\1\141\1\165\1\145\1\143\1\157\1\145\2\156\1"
      + "\142\1\171\1\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171"
      + "\1\162\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145"
      + "\11\uffff";
   
   static final String DFA9_acceptS = "\30\uffff\1\11\6\uffff\1\1\1\2\1\12\1\13\1\14\1\15\1\3\1\4\1\5"
      + "\1\6\1\7\1\10\1\16\1\17\1\uffff\1\22\1\23\1\24\1\25\1\26\1\27\1"
      + "\30\1\20\1\21";
   
   static final String DFA9_specialS = "\67\uffff}>";
   
   static final String[] DFA9_transitionS =
   {
    "\1\4\2\uffff\1\10\1\uffff\1\2\3\uffff\1\1\2\uffff\1\3\1\7\1"
       + "\6\3\uffff\1\5", "\1\11\23\uffff\1\12", "\1\13", "\1\14",
    "\1\15\4\uffff\1\16", "\1\17", "\1\20", "\1\21", "\1\22", "\1\23",
    "\1\25\1\uffff\1\24", "\1\26", "\1\27\6\uffff\1\30", "\1\31", "\1\32",
    "\1\33", "\1\34", "\1\35", "\1\36", "\1\37", "\1\41", "\1\43", "\1\45",
    "\1\47", "", "\1\51", "\1\53", "\1\55", "\1\57", "\1\61", "\1\63", "", "",
    "", "", "", "", "", "", "", "", "", "", "", "", "\1\65", "", "", "", "",
    "", "", "", "", "" };
   
   static final short[] DFA9_eot = DFA.unpackEncodedString( DFA9_eotS );
   
   static final short[] DFA9_eof = DFA.unpackEncodedString( DFA9_eofS );
   
   static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars( DFA9_minS );
   
   static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars( DFA9_maxS );
   
   static final short[] DFA9_accept = DFA.unpackEncodedString( DFA9_acceptS );
   
   static final short[] DFA9_special = DFA.unpackEncodedString( DFA9_specialS );
   
   static final short[][] DFA9_transition;
   
   static
   {
      int numStates = DFA9_transitionS.length;
      DFA9_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA9_transition[ i ] = DFA.unpackEncodedString( DFA9_transitionS[ i ] );
      }
   }
   
   
   class DFA9 extends DFA
   {
      
      public DFA9( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 9;
         this.eot = DFA9_eot;
         this.eof = DFA9_eof;
         this.min = DFA9_min;
         this.max = DFA9_max;
         this.accept = DFA9_accept;
         this.special = DFA9_special;
         this.transition = DFA9_transition;
      }
      


      public String getDescription()
      {
         return "472:1: MONTH : ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' | 'december' | 'dec' );";
      }
   }
   
   static final String DFA10_eotS = "\15\uffff\1\25\1\27\1\31\1\33\1\35\1\37\1\41\16\uffff";
   
   static final String DFA10_eofS = "\42\uffff";
   
   static final String DFA10_minS = "\1\146\1\157\1\150\1\145\1\162\1\141\1\156\1\145\1\165\1\144\1"
      + "\151\1\164\1\156\1\144\1\163\1\162\1\156\1\144\1\165\1\144\16\uffff";
   
   static final String DFA10_maxS = "\1\167\1\157\1\165\1\145\1\162\1\165\1\156\1\145\1\165\1\144\1"
      + "\151\1\164\1\156\1\144\1\163\1\162\1\156\1\144\1\165\1\144\16\uffff";
   
   static final String DFA10_acceptS = "\24\uffff\1\1\1\2\1\3\1\4\1\7\1\10\1\5\1\6\1\11\1\12\1\13\1\14"
      + "\1\15\1\16";
   
   static final String DFA10_specialS = "\42\uffff}>";
   
   static final String[] DFA10_transitionS =
   { "\1\4\6\uffff\1\1\5\uffff\1\5\1\2\2\uffff\1\3", "\1\6",
    "\1\10\14\uffff\1\7", "\1\11", "\1\12", "\1\13\23\uffff\1\14", "\1\15",
    "\1\16", "\1\17", "\1\20", "\1\21", "\1\22", "\1\23", "\1\24", "\1\26",
    "\1\30", "\1\32", "\1\34", "\1\36", "\1\40", "", "", "", "", "", "", "",
    "", "", "", "", "", "", "" };
   
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
         return "477:1: WEEKDAY : ( 'monday' | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' | 'thursday' | 'thu' | 'friday' | 'fri' | 'saturday' | 'sat' | 'sunday' | 'sun' );";
      }
   }
   
   static final String DFA11_eotS = "\16\uffff";
   
   static final String DFA11_eofS = "\16\uffff";
   
   static final String DFA11_minS = "\1\145\1\uffff\1\145\1\151\1\145\11\uffff";
   
   static final String DFA11_maxS = "\1\164\1\uffff\1\167\1\157\1\151\11\uffff";
   
   static final String DFA11_acceptS = "\1\uffff\1\1\3\uffff\1\10\1\11\1\2\1\3\1\12\1\4\1\5\1\6\1\7";
   
   static final String DFA11_specialS = "\16\uffff}>";
   
   static final String[] DFA11_transitionS =
   { "\1\5\1\3\7\uffff\1\6\1\1\3\uffff\1\4\1\2", "",
    "\1\11\2\uffff\1\10\16\uffff\1\7", "\1\13\5\uffff\1\12",
    "\1\15\3\uffff\1\14", "", "", "", "", "", "", "", "", "" };
   
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
         return "481:1: NUM_STR : ( 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' );";
      }
   }
   
   static final String DFA13_eotS = "\1\uffff\1\27\12\uffff\1\50\5\uffff\1\52\6\uffff\1\53\2\uffff\1"
      + "\11\26\uffff\1\64\1\uffff";
   
   static final String DFA13_eofS = "\65\uffff";
   
   static final String DFA13_minS = "\1\11\1\156\1\143\1\uffff\1\151\1\145\1\144\1\145\1\141\1\uffff"
      + "\1\141\2\145\1\uffff\1\145\3\uffff\1\141\6\uffff\1\145\2\uffff\1"
      + "\145\1\144\2\uffff\2\166\1\141\1\uffff\1\160\1\156\1\144\14\uffff"
      + "\1\144\1\uffff";
   
   static final String DFA13_maxS = "\1\u6708\1\165\1\156\1\uffff\1\156\1\167\1\157\1\162\1\165\1\uffff"
      + "\1\157\1\153\1\145\1\uffff\1\162\3\uffff\1\141\6\uffff\1\145\2\uffff"
      + "\1\165\1\156\2\uffff\1\167\1\170\1\163\1\uffff\1\166\1\156\1\145"
      + "\14\uffff\1\144\1\uffff";
   
   static final String DFA13_acceptS = "\3\uffff\1\4\5\uffff\1\17\3\uffff\1\24\1\uffff\1\27\1\30\1\31\1"
      + "\uffff\1\34\1\35\1\36\1\5\1\1\1\2\1\uffff\1\6\1\26\2\uffff\1\14"
      + "\1\25\3\uffff\1\20\3\uffff\1\22\1\23\1\33\1\32\1\3\1\7\1\11\1\13"
      + "\1\10\1\12\1\16\1\15\1\uffff\1\21";
   
   static final String DFA13_specialS = "\65\uffff}>";
   
   static final String[] DFA13_transitionS =
   {
    "\2\25\2\uffff\1\25\22\uffff\1\25\13\uffff\1\23\1\22\1\20\1"
       + "\17\12\24\1\21\46\uffff\1\1\2\uffff\1\14\1\4\1\16\2\uffff\1"
       + "\3\1\15\2\uffff\1\12\1\6\1\2\2\uffff\1\11\1\10\1\5\2\uffff\1"
       + "\13\1\uffff\1\7\u5dfa\uffff\1\17\u0770\uffff\1\17\u0122\uffff"
       + "\1\17",
    "\1\26\1\uffff\1\15\4\uffff\1\15",
    "\1\15\2\uffff\1\30\7\uffff\1\31",
    "",
    "\1\33\4\uffff\1\32",
    "\1\33\2\uffff\1\34\4\uffff\1\36\1\uffff\1\35\5\uffff\1\37"
       + "\1\uffff\1\33", "\1\11\1\41\3\uffff\1\33\5\uffff\1\40",
    "\1\42\14\uffff\1\43",
    "\1\37\3\uffff\1\44\3\uffff\1\33\12\uffff\1\11\1\37", "",
    "\1\15\15\uffff\1\45", "\1\46\5\uffff\1\47", "\1\15", "",
    "\1\15\3\uffff\1\33\5\uffff\1\33\2\uffff\1\37", "", "", "", "\1\51", "",
    "", "", "", "", "", "\1\33", "", "", "\1\54\14\uffff\1\33\2\uffff\1\37",
    "\1\56\10\uffff\1\36\1\55", "", "", "\1\15\1\57", "\1\60\1\uffff\1\61",
    "\1\43\21\uffff\1\62", "", "\1\15\5\uffff\1\33", "\1\63", "\1\37\1\47", "",
    "", "", "", "", "", "", "", "", "", "", "", "\1\37", "" };
   
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
         return "1:1: Tokens : ( A | OF | ON | IN | AND | END | THE | NOW | TONIGHT | NEVER | TODAY | TOMORROW | YESTERDAY | NEXT | STs | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY | NUM_STR | DATE_SEP | DOT | COLON | MINUS | MINUS_A | COMMA | INT | WS );";
      }
   }
   
}
