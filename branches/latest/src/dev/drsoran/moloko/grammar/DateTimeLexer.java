// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g 2011-03-14 19:28:07

package dev.drsoran.moloko.grammar;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;


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
   


   public Token nextToken()
   {
      while ( true )
      {
         if ( input.LA( 1 ) == CharStream.EOF )
         {
            return Token.EOF_TOKEN;
         }
         state.token = null;
         state.channel = Token.DEFAULT_CHANNEL;
         state.tokenStartCharIndex = input.index();
         state.tokenStartCharPositionInLine = input.getCharPositionInLine();
         state.tokenStartLine = input.getLine();
         state.text = null;
         try
         {
            int m = input.mark();
            state.backtracking = 1;
            state.failed = false;
            mTokens();
            state.backtracking = 0;
            
            if ( state.failed )
            {
               input.rewind( m );
               input.consume();
            }
            else
            {
               emit();
               return state.token;
            }
         }
         catch ( RecognitionException re )
         {
            // shouldn't happen in backtracking mode, but...
            reportError( re );
            recover( re );
         }
      }
   }
   


   public void memoize( IntStream input, int ruleIndex, int ruleStartIndex )
   {
      if ( state.backtracking > 1 )
         super.memoize( input, ruleIndex, ruleStartIndex );
   }
   


   public boolean alreadyParsedRule( IntStream input, int ruleIndex )
   {
      if ( state.backtracking > 1 )
         return super.alreadyParsedRule( input, ruleIndex );
      return false;
   }// $ANTLR start "NEVER"
   


   public final void mNEVER() throws RecognitionException
   {
      try
      {
         int _type = NEVER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:14:11: (
         // 'never' | 'nie' )
         int alt1 = 2;
         int LA1_0 = input.LA( 1 );
         
         if ( ( LA1_0 == 'n' ) )
         {
            int LA1_1 = input.LA( 2 );
            
            if ( ( LA1_1 == 'e' ) )
            {
               alt1 = 1;
            }
            else if ( ( LA1_1 == 'i' ) )
            {
               alt1 = 2;
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     1,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  1,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt1 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:14:13:
               // 'never'
            {
               match( "never" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:14:23:
               // 'nie'
            {
               match( "nie" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "NEVER"
   
   // $ANTLR start "TODAY"
   public final void mTODAY() throws RecognitionException
   {
      try
      {
         int _type = TODAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:16:11: (
         // 'today' | 'tod' | 'heute' )
         int alt2 = 3;
         int LA2_0 = input.LA( 1 );
         
         if ( ( LA2_0 == 't' ) )
         {
            int LA2_1 = input.LA( 2 );
            
            if ( ( LA2_1 == 'o' ) )
            {
               int LA2_3 = input.LA( 3 );
               
               if ( ( LA2_3 == 'd' ) )
               {
                  int LA2_4 = input.LA( 4 );
                  
                  if ( ( LA2_4 == 'a' ) )
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
                  if ( state.backtracking > 0 )
                  {
                     state.failed = true;
                     return;
                  }
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        2,
                                                                        3,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     2,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else if ( ( LA2_0 == 'h' ) )
         {
            alt2 = 3;
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  2,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt2 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:16:13:
               // 'today'
            {
               match( "today" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:16:23:
               // 'tod'
            {
               match( "tod" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:16:31:
               // 'heute'
            {
               match( "heute" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:18:11: (
         // 'tomorrow' | 'tom' | 'tmr' | 'morgen' )
         int alt3 = 4;
         int LA3_0 = input.LA( 1 );
         
         if ( ( LA3_0 == 't' ) )
         {
            int LA3_1 = input.LA( 2 );
            
            if ( ( LA3_1 == 'o' ) )
            {
               int LA3_3 = input.LA( 3 );
               
               if ( ( LA3_3 == 'm' ) )
               {
                  int LA3_5 = input.LA( 4 );
                  
                  if ( ( LA3_5 == 'o' ) )
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
                  if ( state.backtracking > 0 )
                  {
                     state.failed = true;
                     return;
                  }
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        3,
                                                                        3,
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
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     3,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else if ( ( LA3_0 == 'm' ) )
         {
            alt3 = 4;
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  3,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt3 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:18:13:
               // 'tomorrow'
            {
               match( "tomorrow" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:18:26:
               // 'tom'
            {
               match( "tom" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:18:34:
               // 'tmr'
            {
               match( "tmr" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:18:42:
               // 'morgen'
            {
               match( "morgen" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:20:11: (
         // 'yesterday' | 'gestern' )
         int alt4 = 2;
         int LA4_0 = input.LA( 1 );
         
         if ( ( LA4_0 == 'y' ) )
         {
            alt4 = 1;
         }
         else if ( ( LA4_0 == 'g' ) )
         {
            alt4 = 2;
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  4,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt4 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:20:13:
               // 'yesterday'
            {
               match( "yesterday" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:20:27:
               // 'gestern'
            {
               match( "gestern" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "YESTERDAY"
   
   // $ANTLR start "AT"
   public final void mAT() throws RecognitionException
   {
      try
      {
         int _type = AT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:22:11: ( '@'
         // | 'at' | 'um' )
         int alt5 = 3;
         switch ( input.LA( 1 ) )
         {
            case '@':
            {
               alt5 = 1;
            }
               break;
            case 'a':
            {
               alt5 = 2;
            }
               break;
            case 'u':
            {
               alt5 = 3;
            }
               break;
            default :
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     5,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt5 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:22:13:
               // '@'
            {
               match( '@' );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:22:19:
               // 'at'
            {
               match( "at" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:22:26:
               // 'um'
            {
               match( "um" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:24:11: (
         // 'on' | 'am' | 'an' )
         int alt6 = 3;
         int LA6_0 = input.LA( 1 );
         
         if ( ( LA6_0 == 'o' ) )
         {
            alt6 = 1;
         }
         else if ( ( LA6_0 == 'a' ) )
         {
            int LA6_2 = input.LA( 2 );
            
            if ( ( LA6_2 == 'm' ) )
            {
               alt6 = 2;
            }
            else if ( ( LA6_2 == 'n' ) )
            {
               alt6 = 3;
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     6,
                                                                     2,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  6,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt6 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:24:13:
               // 'on'
            {
               match( "on" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:24:20:
               // 'am'
            {
               match( "am" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:24:27:
               // 'an'
            {
               match( "an" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "ON"
   
   // $ANTLR start "IN"
   public final void mIN() throws RecognitionException
   {
      try
      {
         int _type = IN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:26:11: (
         // 'in' | 'im' )
         int alt7 = 2;
         int LA7_0 = input.LA( 1 );
         
         if ( ( LA7_0 == 'i' ) )
         {
            int LA7_1 = input.LA( 2 );
            
            if ( ( LA7_1 == 'n' ) )
            {
               alt7 = 1;
            }
            else if ( ( LA7_1 == 'm' ) )
            {
               alt7 = 2;
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     7,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  7,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt7 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:26:13:
               // 'in'
            {
               match( "in" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:26:20:
               // 'im'
            {
               match( "im" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "IN"
   
   // $ANTLR start "OF"
   public final void mOF() throws RecognitionException
   {
      try
      {
         int _type = OF;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:28:11: (
         // 'of' | 'des' )
         int alt8 = 2;
         int LA8_0 = input.LA( 1 );
         
         if ( ( LA8_0 == 'o' ) )
         {
            alt8 = 1;
         }
         else if ( ( LA8_0 == 'd' ) )
         {
            alt8 = 2;
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  8,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt8 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:28:13:
               // 'of'
            {
               match( "of" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:28:20:
               // 'des'
            {
               match( "des" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "OF"
   
   // $ANTLR start "NEXT"
   public final void mNEXT() throws RecognitionException
   {
      try
      {
         int _type = NEXT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:30:11: (
         // 'next' | 'nächste' ( 's' | 'r' )? )
         int alt10 = 2;
         int LA10_0 = input.LA( 1 );
         
         if ( ( LA10_0 == 'n' ) )
         {
            int LA10_1 = input.LA( 2 );
            
            if ( ( LA10_1 == 'e' ) )
            {
               alt10 = 1;
            }
            else if ( ( LA10_1 == '\u00E4' ) )
            {
               alt10 = 2;
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     10,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  10,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt10 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:30:13:
               // 'next'
            {
               match( "next" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:30:22:
               // 'nächste' ( 's' | 'r' )?
            {
               match( "nächste" );
               if ( state.failed )
                  return;
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:30:31:
               // ( 's' | 'r' )?
               int alt9 = 2;
               int LA9_0 = input.LA( 1 );
               
               if ( ( ( LA9_0 >= 'r' && LA9_0 <= 's' ) ) )
               {
                  alt9 = 1;
               }
               switch ( alt9 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:
                  {
                     if ( ( input.LA( 1 ) >= 'r' && input.LA( 1 ) <= 's' ) )
                     {
                        input.consume();
                        state.failed = false;
                     }
                     else
                     {
                        if ( state.backtracking > 0 )
                        {
                           state.failed = true;
                           return;
                        }
                        MismatchedSetException mse = new MismatchedSetException( null,
                                                                                 input );
                        recover( mse );
                        throw mse;
                     }
                     
                  }
                     break;
                  
               }
               
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
   


   // $ANTLR end "NEXT"
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException
   {
      try
      {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:32:11: (
         // 'and' | 'und' )
         int alt11 = 2;
         int LA11_0 = input.LA( 1 );
         
         if ( ( LA11_0 == 'a' ) )
         {
            alt11 = 1;
         }
         else if ( ( LA11_0 == 'u' ) )
         {
            alt11 = 2;
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  11,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt11 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:32:13:
               // 'and'
            {
               match( "and" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:32:21:
               // 'und'
            {
               match( "und" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "AND"
   
   // $ANTLR start "END"
   public final void mEND() throws RecognitionException
   {
      try
      {
         int _type = END;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:34:11: (
         // 'end' | 'ende' )
         int alt12 = 2;
         int LA12_0 = input.LA( 1 );
         
         if ( ( LA12_0 == 'e' ) )
         {
            int LA12_1 = input.LA( 2 );
            
            if ( ( LA12_1 == 'n' ) )
            {
               int LA12_2 = input.LA( 3 );
               
               if ( ( LA12_2 == 'd' ) )
               {
                  int LA12_3 = input.LA( 4 );
                  
                  if ( ( LA12_3 == 'e' ) )
                  {
                     alt12 = 2;
                  }
                  else
                  {
                     alt12 = 1;
                  }
               }
               else
               {
                  if ( state.backtracking > 0 )
                  {
                     state.failed = true;
                     return;
                  }
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        12,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     12,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  12,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt12 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:34:13:
               // 'end'
            {
               match( "end" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:34:21:
               // 'ende'
            {
               match( "ende" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "END"
   
   // $ANTLR start "THE"
   public final void mTHE() throws RecognitionException
   {
      try
      {
         int _type = THE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:36:11: (
         // 'the' | 'd' ( 'er' | 'ie' | 'as' ) )
         int alt14 = 2;
         int LA14_0 = input.LA( 1 );
         
         if ( ( LA14_0 == 't' ) )
         {
            alt14 = 1;
         }
         else if ( ( LA14_0 == 'd' ) )
         {
            alt14 = 2;
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  14,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt14 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:36:13:
               // 'the'
            {
               match( "the" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:36:21:
               // 'd' ( 'er' | 'ie' | 'as' )
            {
               match( 'd' );
               if ( state.failed )
                  return;
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:36:24:
               // ( 'er' | 'ie' | 'as' )
               int alt13 = 3;
               switch ( input.LA( 1 ) )
               {
                  case 'e':
                  {
                     alt13 = 1;
                  }
                     break;
                  case 'i':
                  {
                     alt13 = 2;
                  }
                     break;
                  case 'a':
                  {
                     alt13 = 3;
                  }
                     break;
                  default :
                     if ( state.backtracking > 0 )
                     {
                        state.failed = true;
                        return;
                     }
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           13,
                                                                           0,
                                                                           input );
                     
                     throw nvae;
               }
               
               switch ( alt13 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:36:25:
                     // 'er'
                  {
                     match( "er" );
                     if ( state.failed )
                        return;
                     
                  }
                     break;
                  case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:36:30:
                     // 'ie'
                  {
                     match( "ie" );
                     if ( state.failed )
                        return;
                     
                  }
                     break;
                  case 3:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:36:35:
                     // 'as'
                  {
                     match( "as" );
                     if ( state.failed )
                        return;
                     
                  }
                     break;
                  
               }
               
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
   


   // $ANTLR end "THE"
   
   // $ANTLR start "STs"
   public final void mSTs() throws RecognitionException
   {
      try
      {
         int _type = STs;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:38:11: (
         // 'st' | 'th' | 'rd' | 'nd' )
         int alt15 = 4;
         switch ( input.LA( 1 ) )
         {
            case 's':
            {
               alt15 = 1;
            }
               break;
            case 't':
            {
               alt15 = 2;
            }
               break;
            case 'r':
            {
               alt15 = 3;
            }
               break;
            case 'n':
            {
               alt15 = 4;
            }
               break;
            default :
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     15,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt15 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:38:13:
               // 'st'
            {
               match( "st" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:38:20:
               // 'th'
            {
               match( "th" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:38:27:
               // 'rd'
            {
               match( "rd" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:38:34:
               // 'nd'
            {
               match( "nd" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:40:11: (
         // 'now' | 'jetzt' )
         int alt16 = 2;
         int LA16_0 = input.LA( 1 );
         
         if ( ( LA16_0 == 'n' ) )
         {
            alt16 = 1;
         }
         else if ( ( LA16_0 == 'j' ) )
         {
            alt16 = 2;
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  16,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt16 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:40:13:
               // 'now'
            {
               match( "now" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:40:21:
               // 'jetzt'
            {
               match( "jetzt" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "NOW"
   
   // $ANTLR start "TONIGHT"
   public final void mTONIGHT() throws RecognitionException
   {
      try
      {
         int _type = TONIGHT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:42:11: (
         // 'tonight' | 'ton' | 'heute' )
         int alt17 = 3;
         int LA17_0 = input.LA( 1 );
         
         if ( ( LA17_0 == 't' ) )
         {
            int LA17_1 = input.LA( 2 );
            
            if ( ( LA17_1 == 'o' ) )
            {
               int LA17_3 = input.LA( 3 );
               
               if ( ( LA17_3 == 'n' ) )
               {
                  int LA17_4 = input.LA( 4 );
                  
                  if ( ( LA17_4 == 'i' ) )
                  {
                     alt17 = 1;
                  }
                  else
                  {
                     alt17 = 2;
                  }
               }
               else
               {
                  if ( state.backtracking > 0 )
                  {
                     state.failed = true;
                     return;
                  }
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        17,
                                                                        3,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     17,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else if ( ( LA17_0 == 'h' ) )
         {
            alt17 = 3;
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  17,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt17 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:42:13:
               // 'tonight'
            {
               match( "tonight" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:42:25:
               // 'ton'
            {
               match( "ton" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:42:33:
               // 'heute'
            {
               match( "heute" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:44:11: (
         // 'midnight' | 'mitternacht' )
         int alt18 = 2;
         int LA18_0 = input.LA( 1 );
         
         if ( ( LA18_0 == 'm' ) )
         {
            int LA18_1 = input.LA( 2 );
            
            if ( ( LA18_1 == 'i' ) )
            {
               int LA18_2 = input.LA( 3 );
               
               if ( ( LA18_2 == 'd' ) )
               {
                  alt18 = 1;
               }
               else if ( ( LA18_2 == 't' ) )
               {
                  alt18 = 2;
               }
               else
               {
                  if ( state.backtracking > 0 )
                  {
                     state.failed = true;
                     return;
                  }
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        18,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     18,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  18,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt18 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:44:13:
               // 'midnight'
            {
               match( "midnight" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:44:26:
               // 'mitternacht'
            {
               match( "mitternacht" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "MIDNIGHT"
   
   // $ANTLR start "MIDDAY"
   public final void mMIDDAY() throws RecognitionException
   {
      try
      {
         int _type = MIDDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:46:11: (
         // 'midday' | 'mittag' )
         int alt19 = 2;
         int LA19_0 = input.LA( 1 );
         
         if ( ( LA19_0 == 'm' ) )
         {
            int LA19_1 = input.LA( 2 );
            
            if ( ( LA19_1 == 'i' ) )
            {
               int LA19_2 = input.LA( 3 );
               
               if ( ( LA19_2 == 'd' ) )
               {
                  alt19 = 1;
               }
               else if ( ( LA19_2 == 't' ) )
               {
                  alt19 = 2;
               }
               else
               {
                  if ( state.backtracking > 0 )
                  {
                     state.failed = true;
                     return;
                  }
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        19,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     19,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  19,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt19 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:46:13:
               // 'midday'
            {
               match( "midday" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:46:24:
               // 'mittag'
            {
               match( "mittag" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "MIDDAY"
   
   // $ANTLR start "NOON"
   public final void mNOON() throws RecognitionException
   {
      try
      {
         int _type = NOON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:48:11: (
         // 'noon' | 'mittag' )
         int alt20 = 2;
         int LA20_0 = input.LA( 1 );
         
         if ( ( LA20_0 == 'n' ) )
         {
            alt20 = 1;
         }
         else if ( ( LA20_0 == 'm' ) )
         {
            alt20 = 2;
         }
         else
         {
            if ( state.backtracking > 0 )
            {
               state.failed = true;
               return;
            }
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  20,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt20 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:48:13:
               // 'noon'
            {
               match( "noon" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:48:22:
               // 'mittag'
            {
               match( "mittag" );
               if ( state.failed )
                  return;
               
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
   


   // $ANTLR end "NOON"
   
   // $ANTLR start "YEARS"
   public final void mYEARS() throws RecognitionException
   {
      try
      {
         int _type = YEARS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:50:11: (
         // 'years' | 'year' | 'yrs' | 'yr' | 'jahr' | 'jahre' )
         int alt21 = 6;
         alt21 = dfa21.predict( input );
         switch ( alt21 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:50:13:
               // 'years'
            {
               match( "years" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:50:23:
               // 'year'
            {
               match( "year" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:50:32:
               // 'yrs'
            {
               match( "yrs" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:50:40:
               // 'yr'
            {
               match( "yr" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:50:47:
               // 'jahr'
            {
               match( "jahr" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:50:56:
               // 'jahre'
            {
               match( "jahre" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:52:11: (
         // 'months' | 'month' | 'mons' | 'mon' | 'monat' | 'monate' )
         int alt22 = 6;
         alt22 = dfa22.predict( input );
         switch ( alt22 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:52:13:
               // 'months'
            {
               match( "months" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:52:24:
               // 'month'
            {
               match( "month" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:52:34:
               // 'mons'
            {
               match( "mons" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:52:43:
               // 'mon'
            {
               match( "mon" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:52:51:
               // 'monat'
            {
               match( "monat" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:52:61:
               // 'monate'
            {
               match( "monate" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:54:11: (
         // 'weeks' | 'week' | 'wks' | 'wk' | 'woche' | 'wochen' )
         int alt23 = 6;
         alt23 = dfa23.predict( input );
         switch ( alt23 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:54:13:
               // 'weeks'
            {
               match( "weeks" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:54:23:
               // 'week'
            {
               match( "week" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:54:32:
               // 'wks'
            {
               match( "wks" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:54:40:
               // 'wk'
            {
               match( "wk" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:54:47:
               // 'woche'
            {
               match( "woche" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:54:57:
               // 'wochen'
            {
               match( "wochen" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:56:11: (
         // 'days' | 'day' | 'd' | 'tag' | 'tage' )
         int alt24 = 5;
         alt24 = dfa24.predict( input );
         switch ( alt24 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:56:13:
               // 'days'
            {
               match( "days" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:56:22:
               // 'day'
            {
               match( "day" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:56:30:
               // 'd'
            {
               match( 'd' );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:56:36:
               // 'tag'
            {
               match( "tag" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:56:44:
               // 'tage'
            {
               match( "tage" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:58:11: (
         // 'hours' | 'hour' | 'hrs' | 'hr' | 'h' | 'stunden' | 'std' )
         int alt25 = 7;
         alt25 = dfa25.predict( input );
         switch ( alt25 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:58:13:
               // 'hours'
            {
               match( "hours" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:58:23:
               // 'hour'
            {
               match( "hour" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:58:32:
               // 'hrs'
            {
               match( "hrs" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:58:40:
               // 'hr'
            {
               match( "hr" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:58:47:
               // 'h'
            {
               match( 'h' );
               if ( state.failed )
                  return;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:58:53:
               // 'stunden'
            {
               match( "stunden" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:58:65:
               // 'std'
            {
               match( "std" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:60:11: (
         // 'minutes' | 'minute' | 'mins' | 'min' | 'm' | 'minuten' )
         int alt26 = 6;
         alt26 = dfa26.predict( input );
         switch ( alt26 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:60:13:
               // 'minutes'
            {
               match( "minutes" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:60:25:
               // 'minute'
            {
               match( "minute" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:60:36:
               // 'mins'
            {
               match( "mins" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:60:45:
               // 'min'
            {
               match( "min" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:60:53:
               // 'm'
            {
               match( 'm' );
               if ( state.failed )
                  return;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:60:59:
               // 'minuten'
            {
               match( "minuten" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:62:11: (
         // 'seconds' | 'second' | 'secs' | 'sec' | 's' | 'sekunde' | 'sekunden' )
         int alt27 = 7;
         alt27 = dfa27.predict( input );
         switch ( alt27 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:62:13:
               // 'seconds'
            {
               match( "seconds" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:62:25:
               // 'second'
            {
               match( "second" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:62:36:
               // 'secs'
            {
               match( "secs" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:62:45:
               // 'sec'
            {
               match( "sec" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:62:53:
               // 's'
            {
               match( 's' );
               if ( state.failed )
                  return;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:62:59:
               // 'sekunde'
            {
               match( "sekunde" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:62:71:
               // 'sekunden'
            {
               match( "sekunden" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:11: (
         // 'january' | 'jan' | 'januar' | 'february' | 'feb' | 'februar' | 'march' | 'mar' | 'märz' | 'marz' | 'april'
         // | 'apr' | 'may' | 'mai' | 'june' | 'jun' | 'juni' | 'july' | 'jul' | 'juli' | 'august' | 'aug' | 'september'
         // | 'sept' | 'sep' | 'october' | 'oct' | 'oktober' | 'okt' | 'november' | 'nov' | 'december' | 'dec' |
         // 'dezember' | 'dez' )
         int alt28 = 35;
         alt28 = dfa28.predict( input );
         switch ( alt28 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:13:
               // 'january'
            {
               match( "january" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:27:
               // 'jan'
            {
               match( "jan" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:36:
               // 'januar'
            {
               match( "januar" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:13:
               // 'february'
            {
               match( "february" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:27:
               // 'feb'
            {
               match( "feb" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:36:
               // 'februar'
            {
               match( "februar" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:66:13:
               // 'march'
            {
               match( "march" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:66:27:
               // 'mar'
            {
               match( "mar" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:66:36:
               // 'märz'
            {
               match( "märz" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:66:49:
               // 'marz'
            {
               match( "marz" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 11:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:67:13:
               // 'april'
            {
               match( "april" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 12:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:67:27:
               // 'apr'
            {
               match( "apr" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 13:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:13:
               // 'may'
            {
               match( "may" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 14:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:27:
               // 'mai'
            {
               match( "mai" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 15:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:13:
               // 'june'
            {
               match( "june" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 16:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:27:
               // 'jun'
            {
               match( "jun" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 17:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:36:
               // 'juni'
            {
               match( "juni" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 18:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:70:13:
               // 'july'
            {
               match( "july" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 19:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:70:27:
               // 'jul'
            {
               match( "jul" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 20:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:70:36:
               // 'juli'
            {
               match( "juli" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 21:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:71:13:
               // 'august'
            {
               match( "august" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 22:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:71:27:
               // 'aug'
            {
               match( "aug" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 23:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:72:13:
               // 'september'
            {
               match( "september" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 24:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:72:27:
               // 'sept'
            {
               match( "sept" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 25:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:72:36:
               // 'sep'
            {
               match( "sep" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 26:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:73:13:
               // 'october'
            {
               match( "october" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 27:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:73:27:
               // 'oct'
            {
               match( "oct" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 28:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:73:36:
               // 'oktober'
            {
               match( "oktober" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 29:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:73:49:
               // 'okt'
            {
               match( "okt" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 30:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:74:13:
               // 'november'
            {
               match( "november" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 31:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:74:27:
               // 'nov'
            {
               match( "nov" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 32:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:75:13:
               // 'december'
            {
               match( "december" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 33:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:75:27:
               // 'dec'
            {
               match( "dec" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 34:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:75:36:
               // 'dezember'
            {
               match( "dezember" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 35:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:75:49:
               // 'dez'
            {
               match( "dez" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:77:11: (
         // 'monday' | 'mon' | 'montag' | 'mo' 'tuesday' | 'tue' | 'dienstag' | 'di' 'wednesday' | 'wed' | 'mittwoch' |
         // 'mi' 'thursday' | 'thu' | 'donnerstag' | 'do' 'friday' | 'fri' | 'freitag' | 'fr' 'saturday' | 'sat' |
         // 'samstag' | 'sa' 'sunday' | 'sun' | 'sonntag' | 'so' )
         int alt29 = 22;
         alt29 = dfa29.predict( input );
         switch ( alt29 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:77:13:
               // 'monday'
            {
               match( "monday" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:77:27:
               // 'mon'
            {
               match( "mon" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:77:35:
               // 'montag'
            {
               match( "montag" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:77:50:
               // 'mo' 'tuesday'
            {
               match( "mo" );
               if ( state.failed )
                  return;
               
               match( "tuesday" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:78:27:
               // 'tue'
            {
               match( "tue" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:78:35:
               // 'dienstag'
            {
               match( "dienstag" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:78:50:
               // 'di' 'wednesday'
            {
               match( "di" );
               if ( state.failed )
                  return;
               
               match( "wednesday" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:79:27:
               // 'wed'
            {
               match( "wed" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:79:35:
               // 'mittwoch'
            {
               match( "mittwoch" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:79:50:
               // 'mi' 'thursday'
            {
               match( "mi" );
               if ( state.failed )
                  return;
               
               match( "thursday" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 11:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:80:27:
               // 'thu'
            {
               match( "thu" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 12:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:80:35:
               // 'donnerstag'
            {
               match( "donnerstag" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 13:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:80:50:
               // 'do' 'friday'
            {
               match( "do" );
               if ( state.failed )
                  return;
               
               match( "friday" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 14:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:81:27:
               // 'fri'
            {
               match( "fri" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 15:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:81:35:
               // 'freitag'
            {
               match( "freitag" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 16:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:81:50:
               // 'fr' 'saturday'
            {
               match( "fr" );
               if ( state.failed )
                  return;
               
               match( "saturday" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 17:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:82:27:
               // 'sat'
            {
               match( "sat" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 18:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:82:35:
               // 'samstag'
            {
               match( "samstag" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 19:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:82:50:
               // 'sa' 'sunday'
            {
               match( "sa" );
               if ( state.failed )
                  return;
               
               match( "sunday" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 20:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:83:27:
               // 'sun'
            {
               match( "sun" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 21:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:83:35:
               // 'sonntag'
            {
               match( "sonntag" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 22:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:83:50:
               // 'so'
            {
               match( "so" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:85:11: ( '/'
         // | '\\u5E74' | '\\u6708' | '\\u65E5' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:
         {
            if ( input.LA( 1 ) == '/' || input.LA( 1 ) == '\u5E74'
               || input.LA( 1 ) == '\u65E5' || input.LA( 1 ) == '\u6708' )
            {
               input.consume();
               state.failed = false;
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:87:11: ( '.'
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:87:13: '.'
         {
            match( '.' );
            if ( state.failed )
               return;
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:89:11: ( ':'
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:89:13: ':'
         {
            match( ':' );
            if ( state.failed )
               return;
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:91:11: ( '-'
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:91:13: '-'
         {
            match( '-' );
            if ( state.failed )
               return;
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:93:11: (
         // '-a' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:93:13: '-a'
         {
            match( "-a" );
            if ( state.failed )
               return;
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:95:11: ( ','
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:95:13: ','
         {
            match( ',' );
            if ( state.failed )
               return;
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:97:11: ( (
         // '0' .. '9' )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:97:13: ( '0'
         // .. '9' )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:97:13: (
            // '0' .. '9' )+
            int cnt30 = 0;
            loop30: do
            {
               int alt30 = 2;
               int LA30_0 = input.LA( 1 );
               
               if ( ( ( LA30_0 >= '0' && LA30_0 <= '9' ) ) )
               {
                  alt30 = 1;
               }
               
               switch ( alt30 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:97:13:
                     // '0' .. '9'
                  {
                     matchRange( '0', '9' );
                     if ( state.failed )
                        return;
                     
                  }
                     break;
                  
                  default :
                     if ( cnt30 >= 1 )
                        break loop30;
                     if ( state.backtracking > 0 )
                     {
                        state.failed = true;
                        return;
                     }
                     EarlyExitException eee = new EarlyExitException( 30, input );
                     throw eee;
               }
               cnt30++;
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:99:11: ( 'a'
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:99:13: 'a'
         {
            match( 'a' );
            if ( state.failed )
               return;
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:101:11: ( A
         // ( 'm' )? | '\\u4E0A' | '\\u5348\\u524D' | '\\uC624\\uC804' )
         int alt32 = 4;
         switch ( input.LA( 1 ) )
         {
            case 'a':
            {
               alt32 = 1;
            }
               break;
            case '\u4E0A':
            {
               alt32 = 2;
            }
               break;
            case '\u5348':
            {
               alt32 = 3;
            }
               break;
            case '\uC624':
            {
               alt32 = 4;
            }
               break;
            default :
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     32,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt32 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:101:14:
               // A ( 'm' )?
            {
               mA();
               if ( state.failed )
                  return;
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:101:15:
               // ( 'm' )?
               int alt31 = 2;
               int LA31_0 = input.LA( 1 );
               
               if ( ( LA31_0 == 'm' ) )
               {
                  alt31 = 1;
               }
               switch ( alt31 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:101:16:
                     // 'm'
                  {
                     match( 'm' );
                     if ( state.failed )
                        return;
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:101:24:
               // '\\u4E0A'
            {
               match( '\u4E0A' );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:101:35:
               // '\\u5348\\u524D'
            {
               match( "\u5348\u524D" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:101:52:
               // '\\uC624\\uC804'
            {
               match( "\uC624\uC804" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:103:11: (
         // 'p' ( 'm' )? | '\\u4E0B' | '\\u5348\\u5F8C' | '\\uC624\\uD6C4' )
         int alt34 = 4;
         switch ( input.LA( 1 ) )
         {
            case 'p':
            {
               alt34 = 1;
            }
               break;
            case '\u4E0B':
            {
               alt34 = 2;
            }
               break;
            case '\u5348':
            {
               alt34 = 3;
            }
               break;
            case '\uC624':
            {
               alt34 = 4;
            }
               break;
            default :
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     34,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt34 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:103:13:
               // 'p' ( 'm' )?
            {
               match( 'p' );
               if ( state.failed )
                  return;
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:103:16:
               // ( 'm' )?
               int alt33 = 2;
               int LA33_0 = input.LA( 1 );
               
               if ( ( LA33_0 == 'm' ) )
               {
                  alt33 = 1;
               }
               switch ( alt33 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:103:17:
                     // 'm'
                  {
                     match( 'm' );
                     if ( state.failed )
                        return;
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:103:25:
               // '\\u4E0B'
            {
               match( '\u4E0B' );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:103:36:
               // '\\u5348\\u5F8C'
            {
               match( "\u5348\u5F8C" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:103:53:
               // '\\uC624\\uD6C4'
            {
               match( "\uC624\uD6C4" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:11: (
         // 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' | 'eins' | 'zwei' |
         // 'drei' | 'vier' | 'fünf' | 'funf' | 'sechs' | 'sieben' | 'acht' | 'neun' | 'zehn' )
         int alt35 = 21;
         alt35 = dfa35.predict( input );
         switch ( alt35 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:13:
               // 'one'
            {
               match( "one" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:22:
               // 'two'
            {
               match( "two" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:31:
               // 'three'
            {
               match( "three" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:41:
               // 'four'
            {
               match( "four" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:50:
               // 'five'
            {
               match( "five" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:68:
               // 'six'
            {
               match( "six" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:78:
               // 'seven'
            {
               match( "seven" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:89:
               // 'eight'
            {
               match( "eight" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:99:
               // 'nine'
            {
               match( "nine" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:108:
               // 'ten'
            {
               match( "ten" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 11:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:13:
               // 'eins'
            {
               match( "eins" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 12:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:22:
               // 'zwei'
            {
               match( "zwei" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 13:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:31:
               // 'drei'
            {
               match( "drei" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 14:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:41:
               // 'vier'
            {
               match( "vier" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 15:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:50:
               // 'fünf'
            {
               match( "fünf" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 16:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:59:
               // 'funf'
            {
               match( "funf" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 17:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:68:
               // 'sechs'
            {
               match( "sechs" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 18:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:78:
               // 'sieben'
            {
               match( "sieben" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 19:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:89:
               // 'acht'
            {
               match( "acht" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 20:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:99:
               // 'neun'
            {
               match( "neun" );
               if ( state.failed )
                  return;
               
            }
               break;
            case 21:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:106:108:
               // 'zehn'
            {
               match( "zehn" );
               if ( state.failed )
                  return;
               
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:109:11: ( (~
         // ( '\"' | ' ' | '(' | ')' ) )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:109:13: (~ (
         // '\"' | ' ' | '(' | ')' ) )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:109:13:
            // (~ ( '\"' | ' ' | '(' | ')' ) )+
            int cnt36 = 0;
            loop36: do
            {
               int alt36 = 2;
               int LA36_0 = input.LA( 1 );
               
               if ( ( ( LA36_0 >= '\u0000' && LA36_0 <= '\u001F' )
                  || LA36_0 == '!' || ( LA36_0 >= '#' && LA36_0 <= '\'' ) || ( LA36_0 >= '*' && LA36_0 <= '\uFFFF' ) ) )
               {
                  alt36 = 1;
               }
               
               switch ( alt36 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:109:13:
                     // ~ ( '\"' | ' ' | '(' | ')' )
                  {
                     if ( ( input.LA( 1 ) >= '\u0000' && input.LA( 1 ) <= '\u001F' )
                        || input.LA( 1 ) == '!'
                        || ( input.LA( 1 ) >= '#' && input.LA( 1 ) <= '\'' )
                        || ( input.LA( 1 ) >= '*' && input.LA( 1 ) <= '\uFFFF' ) )
                     {
                        input.consume();
                        state.failed = false;
                     }
                     else
                     {
                        if ( state.backtracking > 0 )
                        {
                           state.failed = true;
                           return;
                        }
                        MismatchedSetException mse = new MismatchedSetException( null,
                                                                                 input );
                        recover( mse );
                        throw mse;
                     }
                     
                  }
                     break;
                  
                  default :
                     if ( cnt36 >= 1 )
                        break loop36;
                     if ( state.backtracking > 0 )
                     {
                        state.failed = true;
                        return;
                     }
                     EarlyExitException eee = new EarlyExitException( 36, input );
                     throw eee;
               }
               cnt36++;
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:111:11: ( (
         // ' ' | '\\t' | '\\r' | '\\n' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:111:13: (
         // ' ' | '\\t' | '\\r' | '\\n' )
         {
            if ( ( input.LA( 1 ) >= '\t' && input.LA( 1 ) <= '\n' )
               || input.LA( 1 ) == '\r' || input.LA( 1 ) == ' ' )
            {
               input.consume();
               state.failed = false;
            }
            else
            {
               if ( state.backtracking > 0 )
               {
                  state.failed = true;
                  return;
               }
               MismatchedSetException mse = new MismatchedSetException( null,
                                                                        input );
               recover( mse );
               throw mse;
            }
            
            if ( state.backtracking == 1 )
            {
               _channel = HIDDEN;
            }
            
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
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:39: ( NEVER |
      // TODAY | TOMORROW | YESTERDAY | AT | ON | IN | OF | NEXT | AND | END | THE | STs | NOW | TONIGHT | MIDNIGHT |
      // MIDDAY | NOON | YEARS | MONTHS | WEEKS | DAYS | HOURS | MINUTES | SECONDS | MONTH | WEEKDAY | DATE_SEP | DOT |
      // COLON | MINUS | MINUS_A | COMMA | INT | A | AM | PM | NUM_STR | WS )
      int alt37 = 39;
      alt37 = dfa37.predict( input );
      switch ( alt37 )
      {
         case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:41:
            // NEVER
         {
            mNEVER();
            if ( state.failed )
               return;
            
         }
            break;
         case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:47:
            // TODAY
         {
            mTODAY();
            if ( state.failed )
               return;
            
         }
            break;
         case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:53:
            // TOMORROW
         {
            mTOMORROW();
            if ( state.failed )
               return;
            
         }
            break;
         case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:62:
            // YESTERDAY
         {
            mYESTERDAY();
            if ( state.failed )
               return;
            
         }
            break;
         case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:72: AT
         {
            mAT();
            if ( state.failed )
               return;
            
         }
            break;
         case 6:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:75: ON
         {
            mON();
            if ( state.failed )
               return;
            
         }
            break;
         case 7:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:78: IN
         {
            mIN();
            if ( state.failed )
               return;
            
         }
            break;
         case 8:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:81: OF
         {
            mOF();
            if ( state.failed )
               return;
            
         }
            break;
         case 9:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:84:
            // NEXT
         {
            mNEXT();
            if ( state.failed )
               return;
            
         }
            break;
         case 10:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:89: AND
         {
            mAND();
            if ( state.failed )
               return;
            
         }
            break;
         case 11:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:93: END
         {
            mEND();
            if ( state.failed )
               return;
            
         }
            break;
         case 12:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:97: THE
         {
            mTHE();
            if ( state.failed )
               return;
            
         }
            break;
         case 13:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:101:
            // STs
         {
            mSTs();
            if ( state.failed )
               return;
            
         }
            break;
         case 14:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:105:
            // NOW
         {
            mNOW();
            if ( state.failed )
               return;
            
         }
            break;
         case 15:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:109:
            // TONIGHT
         {
            mTONIGHT();
            if ( state.failed )
               return;
            
         }
            break;
         case 16:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:117:
            // MIDNIGHT
         {
            mMIDNIGHT();
            if ( state.failed )
               return;
            
         }
            break;
         case 17:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:126:
            // MIDDAY
         {
            mMIDDAY();
            if ( state.failed )
               return;
            
         }
            break;
         case 18:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:133:
            // NOON
         {
            mNOON();
            if ( state.failed )
               return;
            
         }
            break;
         case 19:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:138:
            // YEARS
         {
            mYEARS();
            if ( state.failed )
               return;
            
         }
            break;
         case 20:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:144:
            // MONTHS
         {
            mMONTHS();
            if ( state.failed )
               return;
            
         }
            break;
         case 21:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:151:
            // WEEKS
         {
            mWEEKS();
            if ( state.failed )
               return;
            
         }
            break;
         case 22:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:157:
            // DAYS
         {
            mDAYS();
            if ( state.failed )
               return;
            
         }
            break;
         case 23:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:162:
            // HOURS
         {
            mHOURS();
            if ( state.failed )
               return;
            
         }
            break;
         case 24:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:168:
            // MINUTES
         {
            mMINUTES();
            if ( state.failed )
               return;
            
         }
            break;
         case 25:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:176:
            // SECONDS
         {
            mSECONDS();
            if ( state.failed )
               return;
            
         }
            break;
         case 26:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:184:
            // MONTH
         {
            mMONTH();
            if ( state.failed )
               return;
            
         }
            break;
         case 27:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:190:
            // WEEKDAY
         {
            mWEEKDAY();
            if ( state.failed )
               return;
            
         }
            break;
         case 28:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:198:
            // DATE_SEP
         {
            mDATE_SEP();
            if ( state.failed )
               return;
            
         }
            break;
         case 29:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:207:
            // DOT
         {
            mDOT();
            if ( state.failed )
               return;
            
         }
            break;
         case 30:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:211:
            // COLON
         {
            mCOLON();
            if ( state.failed )
               return;
            
         }
            break;
         case 31:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:217:
            // MINUS
         {
            mMINUS();
            if ( state.failed )
               return;
            
         }
            break;
         case 32:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:223:
            // MINUS_A
         {
            mMINUS_A();
            if ( state.failed )
               return;
            
         }
            break;
         case 33:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:231:
            // COMMA
         {
            mCOMMA();
            if ( state.failed )
               return;
            
         }
            break;
         case 34:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:237:
            // INT
         {
            mINT();
            if ( state.failed )
               return;
            
         }
            break;
         case 35:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:241: A
         {
            mA();
            if ( state.failed )
               return;
            
         }
            break;
         case 36:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:243: AM
         {
            mAM();
            if ( state.failed )
               return;
            
         }
            break;
         case 37:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:246: PM
         {
            mPM();
            if ( state.failed )
               return;
            
         }
            break;
         case 38:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:249:
            // NUM_STR
         {
            mNUM_STR();
            if ( state.failed )
               return;
            
         }
            break;
         case 39:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:257: WS
         {
            mWS();
            if ( state.failed )
               return;
            
         }
            break;
         
      }
      
   }
   


   // $ANTLR start synpred1_DateTimeLexer
   public final void synpred1_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:41: ( NEVER )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:41: NEVER
      {
         mNEVER();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred1_DateTimeLexer
   
   // $ANTLR start synpred2_DateTimeLexer
   public final void synpred2_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:47: ( TODAY )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:47: TODAY
      {
         mTODAY();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred2_DateTimeLexer
   
   // $ANTLR start synpred3_DateTimeLexer
   public final void synpred3_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:53: (
      // TOMORROW )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:53: TOMORROW
      {
         mTOMORROW();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred3_DateTimeLexer
   
   // $ANTLR start synpred4_DateTimeLexer
   public final void synpred4_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:62: (
      // YESTERDAY )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:62: YESTERDAY
      {
         mYESTERDAY();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred4_DateTimeLexer
   
   // $ANTLR start synpred5_DateTimeLexer
   public final void synpred5_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:72: ( AT )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:72: AT
      {
         mAT();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred5_DateTimeLexer
   
   // $ANTLR start synpred6_DateTimeLexer
   public final void synpred6_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:75: ( ON )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:75: ON
      {
         mON();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred6_DateTimeLexer
   
   // $ANTLR start synpred8_DateTimeLexer
   public final void synpred8_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:81: ( OF )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:81: OF
      {
         mOF();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred8_DateTimeLexer
   
   // $ANTLR start synpred9_DateTimeLexer
   public final void synpred9_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:84: ( NEXT )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:84: NEXT
      {
         mNEXT();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred9_DateTimeLexer
   
   // $ANTLR start synpred10_DateTimeLexer
   public final void synpred10_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:89: ( AND )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:89: AND
      {
         mAND();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred10_DateTimeLexer
   
   // $ANTLR start synpred11_DateTimeLexer
   public final void synpred11_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:93: ( END )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:93: END
      {
         mEND();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred11_DateTimeLexer
   
   // $ANTLR start synpred12_DateTimeLexer
   public final void synpred12_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:97: ( THE )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:97: THE
      {
         mTHE();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred12_DateTimeLexer
   
   // $ANTLR start synpred13_DateTimeLexer
   public final void synpred13_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:101: ( STs )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:101: STs
      {
         mSTs();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred13_DateTimeLexer
   
   // $ANTLR start synpred14_DateTimeLexer
   public final void synpred14_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:105: ( NOW )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:105: NOW
      {
         mNOW();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred14_DateTimeLexer
   
   // $ANTLR start synpred15_DateTimeLexer
   public final void synpred15_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:109: (
      // TONIGHT )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:109: TONIGHT
      {
         mTONIGHT();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred15_DateTimeLexer
   
   // $ANTLR start synpred16_DateTimeLexer
   public final void synpred16_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:117: (
      // MIDNIGHT )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:117: MIDNIGHT
      {
         mMIDNIGHT();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred16_DateTimeLexer
   
   // $ANTLR start synpred17_DateTimeLexer
   public final void synpred17_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:126: ( MIDDAY
      // )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:126: MIDDAY
      {
         mMIDDAY();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred17_DateTimeLexer
   
   // $ANTLR start synpred18_DateTimeLexer
   public final void synpred18_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:133: ( NOON )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:133: NOON
      {
         mNOON();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred18_DateTimeLexer
   
   // $ANTLR start synpred19_DateTimeLexer
   public final void synpred19_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:138: ( YEARS
      // )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:138: YEARS
      {
         mYEARS();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred19_DateTimeLexer
   
   // $ANTLR start synpred20_DateTimeLexer
   public final void synpred20_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:144: ( MONTHS
      // )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:144: MONTHS
      {
         mMONTHS();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred20_DateTimeLexer
   
   // $ANTLR start synpred21_DateTimeLexer
   public final void synpred21_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:151: ( WEEKS
      // )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:151: WEEKS
      {
         mWEEKS();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred21_DateTimeLexer
   
   // $ANTLR start synpred22_DateTimeLexer
   public final void synpred22_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:157: ( DAYS )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:157: DAYS
      {
         mDAYS();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred22_DateTimeLexer
   
   // $ANTLR start synpred23_DateTimeLexer
   public final void synpred23_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:162: ( HOURS
      // )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:162: HOURS
      {
         mHOURS();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred23_DateTimeLexer
   
   // $ANTLR start synpred24_DateTimeLexer
   public final void synpred24_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:168: (
      // MINUTES )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:168: MINUTES
      {
         mMINUTES();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred24_DateTimeLexer
   
   // $ANTLR start synpred25_DateTimeLexer
   public final void synpred25_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:176: (
      // SECONDS )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:176: SECONDS
      {
         mSECONDS();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred25_DateTimeLexer
   
   // $ANTLR start synpred26_DateTimeLexer
   public final void synpred26_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:184: ( MONTH
      // )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:184: MONTH
      {
         mMONTH();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred26_DateTimeLexer
   
   // $ANTLR start synpred27_DateTimeLexer
   public final void synpred27_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:190: (
      // WEEKDAY )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:190: WEEKDAY
      {
         mWEEKDAY();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred27_DateTimeLexer
   
   // $ANTLR start synpred31_DateTimeLexer
   public final void synpred31_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:217: ( MINUS
      // )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:217: MINUS
      {
         mMINUS();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred31_DateTimeLexer
   
   // $ANTLR start synpred32_DateTimeLexer
   public final void synpred32_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:223: (
      // MINUS_A )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:223: MINUS_A
      {
         mMINUS_A();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred32_DateTimeLexer
   
   // $ANTLR start synpred35_DateTimeLexer
   public final void synpred35_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:241: ( A )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:241: A
      {
         mA();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred35_DateTimeLexer
   
   // $ANTLR start synpred36_DateTimeLexer
   public final void synpred36_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:243: ( AM )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:243: AM
      {
         mAM();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred36_DateTimeLexer
   
   // $ANTLR start synpred37_DateTimeLexer
   public final void synpred37_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:246: ( PM )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:246: PM
      {
         mPM();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred37_DateTimeLexer
   
   // $ANTLR start synpred38_DateTimeLexer
   public final void synpred38_DateTimeLexer_fragment() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:249: (
      // NUM_STR )
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:249: NUM_STR
      {
         mNUM_STR();
         if ( state.failed )
            return;
         
      }
   }
   


   // $ANTLR end synpred38_DateTimeLexer
   
   public final boolean synpred37_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred37_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred18_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred18_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred19_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred19_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred27_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred27_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred20_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred20_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred12_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred12_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred14_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred14_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred24_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred24_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred22_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred22_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred25_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred25_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred9_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred9_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred26_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred26_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred32_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred32_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred4_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred4_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred17_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred17_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred10_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred10_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred3_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred3_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred16_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred16_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred13_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred13_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred35_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred35_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred2_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred2_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred11_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred11_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred8_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred8_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred15_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred15_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred31_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred31_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred5_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred5_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred38_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred38_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred6_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred6_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred36_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred36_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred21_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred21_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred23_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred23_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   


   public final boolean synpred1_DateTimeLexer()
   {
      state.backtracking++;
      int start = input.mark();
      try
      {
         synpred1_DateTimeLexer_fragment(); // can never throw exception
      }
      catch ( RecognitionException re )
      {
         System.err.println( "impossible: " + re );
      }
      boolean success = !state.failed;
      input.rewind( start );
      state.backtracking--;
      state.failed = false;
      return success;
   }
   
   protected DFA21 dfa21 = new DFA21( this );
   
   protected DFA22 dfa22 = new DFA22( this );
   
   protected DFA23 dfa23 = new DFA23( this );
   
   protected DFA24 dfa24 = new DFA24( this );
   
   protected DFA25 dfa25 = new DFA25( this );
   
   protected DFA26 dfa26 = new DFA26( this );
   
   protected DFA27 dfa27 = new DFA27( this );
   
   protected DFA28 dfa28 = new DFA28( this );
   
   protected DFA29 dfa29 = new DFA29( this );
   
   protected DFA35 dfa35 = new DFA35( this );
   
   protected DFA37 dfa37 = new DFA37( this );
   
   static final String DFA21_eotS = "\4\uffff\1\10\5\uffff\1\15\1\17\4\uffff";
   
   static final String DFA21_eofS = "\20\uffff";
   
   static final String DFA21_minS = "\1\152\1\145\2\141\1\163\1\150\1\162\2\uffff\1\162\1\163\1\145"
      + "\4\uffff";
   
   static final String DFA21_maxS = "\1\171\1\162\2\141\1\163\1\150\1\162\2\uffff\1\162\1\163\1\145"
      + "\4\uffff";
   
   static final String DFA21_acceptS = "\7\uffff\1\3\1\4\3\uffff\1\1\1\2\1\6\1\5";
   
   static final String DFA21_specialS = "\20\uffff}>";
   
   static final String[] DFA21_transitionS =
   { "\1\2\16\uffff\1\1", "\1\3\14\uffff\1\4", "\1\5", "\1\6", "\1\7", "\1\11",
    "\1\12", "", "", "\1\13", "\1\14", "\1\16", "", "", "", "" };
   
   static final short[] DFA21_eot = DFA.unpackEncodedString( DFA21_eotS );
   
   static final short[] DFA21_eof = DFA.unpackEncodedString( DFA21_eofS );
   
   static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars( DFA21_minS );
   
   static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars( DFA21_maxS );
   
   static final short[] DFA21_accept = DFA.unpackEncodedString( DFA21_acceptS );
   
   static final short[] DFA21_special = DFA.unpackEncodedString( DFA21_specialS );
   
   static final short[][] DFA21_transition;
   
   static
   {
      int numStates = DFA21_transitionS.length;
      DFA21_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA21_transition[ i ] = DFA.unpackEncodedString( DFA21_transitionS[ i ] );
      }
   }
   
   
   class DFA21 extends DFA
   {
      
      public DFA21( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 21;
         this.eot = DFA21_eot;
         this.eof = DFA21_eof;
         this.min = DFA21_min;
         this.max = DFA21_max;
         this.accept = DFA21_accept;
         this.special = DFA21_special;
         this.transition = DFA21_transition;
      }
      


      public String getDescription()
      {
         return "50:1: YEARS : ( 'years' | 'year' | 'yrs' | 'yr' | 'jahr' | 'jahre' );";
      }
   }
   
   static final String DFA22_eotS = "\3\uffff\1\7\4\uffff\1\13\1\15\4\uffff";
   
   static final String DFA22_eofS = "\16\uffff";
   
   static final String DFA22_minS = "\1\155\1\157\1\156\1\141\1\150\1\uffff\1\164\1\uffff\1\163\1\145"
      + "\4\uffff";
   
   static final String DFA22_maxS = "\1\155\1\157\1\156\1\164\1\150\1\uffff\1\164\1\uffff\1\163\1\145"
      + "\4\uffff";
   
   static final String DFA22_acceptS = "\5\uffff\1\3\1\uffff\1\4\2\uffff\1\1\1\2\1\6\1\5";
   
   static final String DFA22_specialS = "\16\uffff}>";
   
   static final String[] DFA22_transitionS =
   { "\1\1", "\1\2", "\1\3", "\1\6\21\uffff\1\5\1\4", "\1\10", "", "\1\11", "",
    "\1\12", "\1\14", "", "", "", "" };
   
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
         return "52:1: MONTHS : ( 'months' | 'month' | 'mons' | 'mon' | 'monat' | 'monate' );";
      }
   }
   
   static final String DFA23_eotS = "\3\uffff\1\7\5\uffff\1\14\3\uffff\1\17\2\uffff";
   
   static final String DFA23_eofS = "\20\uffff";
   
   static final String DFA23_minS = "\1\167\2\145\1\163\1\143\1\153\2\uffff\1\150\1\163\1\145\2\uffff"
      + "\1\156\2\uffff";
   
   static final String DFA23_maxS = "\1\167\1\157\1\145\1\163\1\143\1\153\2\uffff\1\150\1\163\1\145"
      + "\2\uffff\1\156\2\uffff";
   
   static final String DFA23_acceptS = "\6\uffff\1\3\1\4\3\uffff\1\1\1\2\1\uffff\1\6\1\5";
   
   static final String DFA23_specialS = "\20\uffff}>";
   
   static final String[] DFA23_transitionS =
   { "\1\1", "\1\2\5\uffff\1\3\3\uffff\1\4", "\1\5", "\1\6", "\1\10", "\1\11",
    "", "", "\1\12", "\1\13", "\1\15", "", "", "\1\16", "", "" };
   
   static final short[] DFA23_eot = DFA.unpackEncodedString( DFA23_eotS );
   
   static final short[] DFA23_eof = DFA.unpackEncodedString( DFA23_eofS );
   
   static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars( DFA23_minS );
   
   static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars( DFA23_maxS );
   
   static final short[] DFA23_accept = DFA.unpackEncodedString( DFA23_acceptS );
   
   static final short[] DFA23_special = DFA.unpackEncodedString( DFA23_specialS );
   
   static final short[][] DFA23_transition;
   
   static
   {
      int numStates = DFA23_transitionS.length;
      DFA23_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA23_transition[ i ] = DFA.unpackEncodedString( DFA23_transitionS[ i ] );
      }
   }
   
   
   class DFA23 extends DFA
   {
      
      public DFA23( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 23;
         this.eot = DFA23_eot;
         this.eof = DFA23_eof;
         this.min = DFA23_min;
         this.max = DFA23_max;
         this.accept = DFA23_accept;
         this.special = DFA23_special;
         this.transition = DFA23_transition;
      }
      


      public String getDescription()
      {
         return "54:1: WEEKS : ( 'weeks' | 'week' | 'wks' | 'wk' | 'woche' | 'wochen' );";
      }
   }
   
   static final String DFA24_eotS = "\1\uffff\1\4\4\uffff\1\11\1\13\4\uffff";
   
   static final String DFA24_eofS = "\14\uffff";
   
   static final String DFA24_minS = "\1\144\2\141\1\171\1\uffff\1\147\1\163\1\145\4\uffff";
   
   static final String DFA24_maxS = "\1\164\2\141\1\171\1\uffff\1\147\1\163\1\145\4\uffff";
   
   static final String DFA24_acceptS = "\4\uffff\1\3\3\uffff\1\1\1\2\1\5\1\4";
   
   static final String DFA24_specialS = "\14\uffff}>";
   
   static final String[] DFA24_transitionS =
   { "\1\1\17\uffff\1\2", "\1\3", "\1\5", "\1\6", "", "\1\7", "\1\10", "\1\12",
    "", "", "", "" };
   
   static final short[] DFA24_eot = DFA.unpackEncodedString( DFA24_eotS );
   
   static final short[] DFA24_eof = DFA.unpackEncodedString( DFA24_eofS );
   
   static final char[] DFA24_min = DFA.unpackEncodedStringToUnsignedChars( DFA24_minS );
   
   static final char[] DFA24_max = DFA.unpackEncodedStringToUnsignedChars( DFA24_maxS );
   
   static final short[] DFA24_accept = DFA.unpackEncodedString( DFA24_acceptS );
   
   static final short[] DFA24_special = DFA.unpackEncodedString( DFA24_specialS );
   
   static final short[][] DFA24_transition;
   
   static
   {
      int numStates = DFA24_transitionS.length;
      DFA24_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA24_transition[ i ] = DFA.unpackEncodedString( DFA24_transitionS[ i ] );
      }
   }
   
   
   class DFA24 extends DFA
   {
      
      public DFA24( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 24;
         this.eot = DFA24_eot;
         this.eof = DFA24_eof;
         this.min = DFA24_min;
         this.max = DFA24_max;
         this.accept = DFA24_accept;
         this.special = DFA24_special;
         this.transition = DFA24_transition;
      }
      


      public String getDescription()
      {
         return "56:1: DAYS : ( 'days' | 'day' | 'd' | 'tag' | 'tage' );";
      }
   }
   
   static final String DFA25_eotS = "\1\uffff\1\5\2\uffff\1\11\7\uffff\1\16\2\uffff";
   
   static final String DFA25_eofS = "\17\uffff";
   
   static final String DFA25_minS = "\1\150\1\157\1\164\1\165\1\163\1\uffff\1\144\1\162\4\uffff\1\163"
      + "\2\uffff";
   
   static final String DFA25_maxS = "\1\163\1\162\1\164\1\165\1\163\1\uffff\1\165\1\162\4\uffff\1\163"
      + "\2\uffff";
   
   static final String DFA25_acceptS = "\5\uffff\1\5\2\uffff\1\3\1\4\1\6\1\7\1\uffff\1\1\1\2";
   
   static final String DFA25_specialS = "\17\uffff}>";
   
   static final String[] DFA25_transitionS =
   { "\1\1\12\uffff\1\2", "\1\3\2\uffff\1\4", "\1\6", "\1\7", "\1\10", "",
    "\1\13\20\uffff\1\12", "\1\14", "", "", "", "", "\1\15", "", "" };
   
   static final short[] DFA25_eot = DFA.unpackEncodedString( DFA25_eotS );
   
   static final short[] DFA25_eof = DFA.unpackEncodedString( DFA25_eofS );
   
   static final char[] DFA25_min = DFA.unpackEncodedStringToUnsignedChars( DFA25_minS );
   
   static final char[] DFA25_max = DFA.unpackEncodedStringToUnsignedChars( DFA25_maxS );
   
   static final short[] DFA25_accept = DFA.unpackEncodedString( DFA25_acceptS );
   
   static final short[] DFA25_special = DFA.unpackEncodedString( DFA25_specialS );
   
   static final short[][] DFA25_transition;
   
   static
   {
      int numStates = DFA25_transitionS.length;
      DFA25_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA25_transition[ i ] = DFA.unpackEncodedString( DFA25_transitionS[ i ] );
      }
   }
   
   
   class DFA25 extends DFA
   {
      
      public DFA25( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 25;
         this.eot = DFA25_eot;
         this.eof = DFA25_eof;
         this.min = DFA25_min;
         this.max = DFA25_max;
         this.accept = DFA25_accept;
         this.special = DFA25_special;
         this.transition = DFA25_transition;
      }
      


      public String getDescription()
      {
         return "58:1: HOURS : ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' | 'stunden' | 'std' );";
      }
   }
   
   static final String DFA26_eotS = "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\14\3\uffff";
   
   static final String DFA26_eofS = "\15\uffff";
   
   static final String DFA26_minS = "\1\155\1\151\1\156\1\uffff\1\163\1\164\2\uffff\1\145\1\156\3\uffff";
   
   static final String DFA26_maxS = "\1\155\1\151\1\156\1\uffff\1\165\1\164\2\uffff\1\145\1\163\3\uffff";
   
   static final String DFA26_acceptS = "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\6\1\2";
   
   static final String DFA26_specialS = "\15\uffff}>";
   
   static final String[] DFA26_transitionS =
   { "\1\1", "\1\2", "\1\4", "", "\1\6\1\uffff\1\5", "\1\10", "", "", "\1\11",
    "\1\13\4\uffff\1\12", "", "", "" };
   
   static final short[] DFA26_eot = DFA.unpackEncodedString( DFA26_eotS );
   
   static final short[] DFA26_eof = DFA.unpackEncodedString( DFA26_eofS );
   
   static final char[] DFA26_min = DFA.unpackEncodedStringToUnsignedChars( DFA26_minS );
   
   static final char[] DFA26_max = DFA.unpackEncodedStringToUnsignedChars( DFA26_maxS );
   
   static final short[] DFA26_accept = DFA.unpackEncodedString( DFA26_acceptS );
   
   static final short[] DFA26_special = DFA.unpackEncodedString( DFA26_specialS );
   
   static final short[][] DFA26_transition;
   
   static
   {
      int numStates = DFA26_transitionS.length;
      DFA26_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA26_transition[ i ] = DFA.unpackEncodedString( DFA26_transitionS[ i ] );
      }
   }
   
   
   class DFA26 extends DFA
   {
      
      public DFA26( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 26;
         this.eot = DFA26_eot;
         this.eof = DFA26_eof;
         this.min = DFA26_min;
         this.max = DFA26_max;
         this.accept = DFA26_accept;
         this.special = DFA26_special;
         this.transition = DFA26_transition;
      }
      


      public String getDescription()
      {
         return "60:1: MINUTES : ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' | 'minuten' );";
      }
   }
   
   static final String DFA27_eotS = "\1\uffff\1\3\2\uffff\1\10\7\uffff\1\17\3\uffff\1\22\2\uffff";
   
   static final String DFA27_eofS = "\23\uffff";
   
   static final String DFA27_minS = "\1\163\1\145\1\143\1\uffff\1\157\1\165\1\156\2\uffff\1\156\2\144"
      + "\1\163\1\145\2\uffff\1\156\2\uffff";
   
   static final String DFA27_maxS = "\1\163\1\145\1\153\1\uffff\1\163\1\165\1\156\2\uffff\1\156\2\144"
      + "\1\163\1\145\2\uffff\1\156\2\uffff";
   
   static final String DFA27_acceptS = "\3\uffff\1\5\3\uffff\1\3\1\4\5\uffff\1\1\1\2\1\uffff\1\7\1\6";
   
   static final String DFA27_specialS = "\23\uffff}>";
   
   static final String[] DFA27_transitionS =
   { "\1\1", "\1\2", "\1\4\7\uffff\1\5", "", "\1\6\3\uffff\1\7", "\1\11",
    "\1\12", "", "", "\1\13", "\1\14", "\1\15", "\1\16", "\1\20", "", "",
    "\1\21", "", "" };
   
   static final short[] DFA27_eot = DFA.unpackEncodedString( DFA27_eotS );
   
   static final short[] DFA27_eof = DFA.unpackEncodedString( DFA27_eofS );
   
   static final char[] DFA27_min = DFA.unpackEncodedStringToUnsignedChars( DFA27_minS );
   
   static final char[] DFA27_max = DFA.unpackEncodedStringToUnsignedChars( DFA27_maxS );
   
   static final short[] DFA27_accept = DFA.unpackEncodedString( DFA27_acceptS );
   
   static final short[] DFA27_special = DFA.unpackEncodedString( DFA27_specialS );
   
   static final short[][] DFA27_transition;
   
   static
   {
      int numStates = DFA27_transitionS.length;
      DFA27_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA27_transition[ i ] = DFA.unpackEncodedString( DFA27_transitionS[ i ] );
      }
   }
   
   
   class DFA27 extends DFA
   {
      
      public DFA27( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 27;
         this.eot = DFA27_eot;
         this.eof = DFA27_eof;
         this.min = DFA27_min;
         this.max = DFA27_max;
         this.accept = DFA27_accept;
         this.special = DFA27_special;
         this.transition = DFA27_transition;
      }
      


      public String getDescription()
      {
         return "62:1: SECONDS : ( 'seconds' | 'second' | 'secs' | 'sec' | 's' | 'sekunde' | 'sekunden' );";
      }
   }
   
   static final String DFA28_eotS = "\25\uffff\1\45\1\50\1\53\1\55\1\60\2\uffff\1\62\1\64\1\66\1\70"
      + "\1\72\1\74\1\76\1\100\21\uffff\1\104\17\uffff\1\110\3\uffff\1\113"
      + "\2\uffff";
   
   static final String DFA28_eofS = "\114\uffff";
   
   static final String DFA28_minS = "\2\141\1\145\1\141\1\160\1\145\1\143\1\157\1\145\1\156\1\154\1"
      + "\142\1\151\1\uffff\1\162\1\147\1\160\2\164\1\166\1\143\1\165\1\145"
      + "\1\151\1\162\1\143\2\uffff\1\151\1\165\1\164\2\157\3\145\1\141\7"
      + "\uffff\1\165\10\uffff\1\145\13\uffff\1\162\1\141\2\uffff\1\171\1"
      + "\162\2\uffff\1\171\2\uffff";
   
   static final String DFA28_maxS = "\1\163\1\165\1\145\1\u00e4\1\165\1\145\1\153\1\157\1\145\2\156"
      + "\1\142\1\171\1\uffff\1\162\1\147\1\160\2\164\1\166\1\172\1\165\1"
      + "\151\1\171\1\162\1\172\2\uffff\1\151\1\165\1\164\2\157\3\145\1\141"
      + "\7\uffff\1\165\10\uffff\1\145\13\uffff\1\162\1\141\2\uffff\1\171"
      + "\1\162\2\uffff\1\171\2\uffff";
   
   static final String DFA28_acceptS = "\15\uffff\1\11\14\uffff\1\15\1\16\11\uffff\1\2\1\17\1\21\1\20\1"
      + "\22\1\24\1\23\1\uffff\1\5\1\7\1\12\1\10\1\13\1\14\1\25\1\26\1\uffff"
      + "\1\31\1\32\1\33\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43\2\uffff"
      + "\1\27\1\30\2\uffff\1\1\1\3\1\uffff\1\4\1\6";
   
   static final String DFA28_specialS = "\114\uffff}>";
   
   static final String[] DFA28_transitionS =
   {
    "\1\4\2\uffff\1\10\1\uffff\1\2\3\uffff\1\1\2\uffff\1\3\1\7\1"
       + "\6\3\uffff\1\5", "\1\11\23\uffff\1\12", "\1\13",
    "\1\14\u0082\uffff\1\15", "\1\16\4\uffff\1\17", "\1\20",
    "\1\21\7\uffff\1\22", "\1\23", "\1\24", "\1\25", "\1\27\1\uffff\1\26",
    "\1\30", "\1\33\10\uffff\1\31\6\uffff\1\32", "", "\1\34", "\1\35", "\1\36",
    "\1\37", "\1\40", "\1\41", "\1\42\26\uffff\1\43", "\1\44",
    "\1\46\3\uffff\1\47", "\1\52\17\uffff\1\51", "\1\54",
    "\1\56\26\uffff\1\57", "", "", "\1\61", "\1\63", "\1\65", "\1\67", "\1\71",
    "\1\73", "\1\75", "\1\77", "\1\101", "", "", "", "", "", "", "", "\1\102",
    "", "", "", "", "", "", "", "", "\1\103", "", "", "", "", "", "", "", "",
    "", "", "", "\1\105", "\1\106", "", "", "\1\107", "\1\111", "", "",
    "\1\112", "", "" };
   
   static final short[] DFA28_eot = DFA.unpackEncodedString( DFA28_eotS );
   
   static final short[] DFA28_eof = DFA.unpackEncodedString( DFA28_eofS );
   
   static final char[] DFA28_min = DFA.unpackEncodedStringToUnsignedChars( DFA28_minS );
   
   static final char[] DFA28_max = DFA.unpackEncodedStringToUnsignedChars( DFA28_maxS );
   
   static final short[] DFA28_accept = DFA.unpackEncodedString( DFA28_acceptS );
   
   static final short[] DFA28_special = DFA.unpackEncodedString( DFA28_specialS );
   
   static final short[][] DFA28_transition;
   
   static
   {
      int numStates = DFA28_transitionS.length;
      DFA28_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA28_transition[ i ] = DFA.unpackEncodedString( DFA28_transitionS[ i ] );
      }
   }
   
   
   class DFA28 extends DFA
   {
      
      public DFA28( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 28;
         this.eot = DFA28_eot;
         this.eof = DFA28_eof;
         this.min = DFA28_min;
         this.max = DFA28_max;
         this.accept = DFA28_accept;
         this.special = DFA28_special;
         this.transition = DFA28_transition;
      }
      


      public String getDescription()
      {
         return "64:1: MONTH : ( 'january' | 'jan' | 'januar' | 'february' | 'feb' | 'februar' | 'march' | 'mar' | 'märz' | 'marz' | 'april' | 'apr' | 'may' | 'mai' | 'june' | 'jun' | 'juni' | 'july' | 'jul' | 'juli' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'oktober' | 'okt' | 'november' | 'nov' | 'december' | 'dec' | 'dezember' | 'dez' );";
      }
   }
   
   static final String DFA29_eotS = "\20\uffff\1\37\1\42\23\uffff";
   
   static final String DFA29_eofS = "\45\uffff";
   
   static final String DFA29_minS = "\1\144\1\151\1\150\1\151\1\uffff\1\162\1\141\1\156\1\164\2\uffff"
      + "\1\145\1\146\1\145\1\155\1\uffff\1\156\1\144\1\uffff\1\150\21\uffff";
   
   static final String DFA29_maxS = "\1\167\1\157\1\165\1\157\1\uffff\1\162\1\165\2\164\2\uffff\1\167"
      + "\1\156\1\163\1\164\1\uffff\1\156\1\164\1\uffff\1\164\21\uffff";
   
   static final String DFA29_acceptS = "\4\uffff\1\10\4\uffff\1\5\1\13\4\uffff\1\24\2\uffff\1\4\1\uffff"
      + "\1\6\1\7\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\25\1\26\1\1\1"
      + "\3\1\2\1\11\1\12";
   
   static final String DFA29_specialS = "\45\uffff}>";
   
   static final String[] DFA29_transitionS =
   { "\1\3\1\uffff\1\5\6\uffff\1\1\5\uffff\1\6\1\2\2\uffff\1\4",
    "\1\10\5\uffff\1\7", "\1\12\14\uffff\1\11", "\1\13\5\uffff\1\14", "",
    "\1\15", "\1\16\15\uffff\1\20\5\uffff\1\17", "\1\21\5\uffff\1\22", "\1\23",
    "", "", "\1\24\21\uffff\1\25", "\1\27\7\uffff\1\26",
    "\1\31\3\uffff\1\30\11\uffff\1\32", "\1\34\5\uffff\1\35\1\33", "", "\1\36",
    "\1\40\17\uffff\1\41", "", "\1\44\13\uffff\1\43", "", "", "", "", "", "",
    "", "", "", "", "", "", "", "", "", "", "" };
   
   static final short[] DFA29_eot = DFA.unpackEncodedString( DFA29_eotS );
   
   static final short[] DFA29_eof = DFA.unpackEncodedString( DFA29_eofS );
   
   static final char[] DFA29_min = DFA.unpackEncodedStringToUnsignedChars( DFA29_minS );
   
   static final char[] DFA29_max = DFA.unpackEncodedStringToUnsignedChars( DFA29_maxS );
   
   static final short[] DFA29_accept = DFA.unpackEncodedString( DFA29_acceptS );
   
   static final short[] DFA29_special = DFA.unpackEncodedString( DFA29_specialS );
   
   static final short[][] DFA29_transition;
   
   static
   {
      int numStates = DFA29_transitionS.length;
      DFA29_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA29_transition[ i ] = DFA.unpackEncodedString( DFA29_transitionS[ i ] );
      }
   }
   
   
   class DFA29 extends DFA
   {
      
      public DFA29( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 29;
         this.eot = DFA29_eot;
         this.eof = DFA29_eof;
         this.min = DFA29_min;
         this.max = DFA29_max;
         this.accept = DFA29_accept;
         this.special = DFA29_special;
         this.transition = DFA29_transition;
      }
      


      public String getDescription()
      {
         return "77:1: WEEKDAY : ( 'monday' | 'mon' | 'montag' | 'mo' 'tuesday' | 'tue' | 'dienstag' | 'di' 'wednesday' | 'wed' | 'mittwoch' | 'mi' 'thursday' | 'thu' | 'donnerstag' | 'do' 'friday' | 'fri' | 'freitag' | 'fr' 'saturday' | 'sat' | 'samstag' | 'sa' 'sunday' | 'sun' | 'sonntag' | 'so' );";
      }
   }
   
   static final String DFA35_eotS = "\37\uffff";
   
   static final String DFA35_eofS = "\37\uffff";
   
   static final String DFA35_minS = "\1\141\1\uffff\1\145\1\151\1\145\1\151\2\145\12\uffff\1\145\1\143"
      + "\1\147\12\uffff";
   
   static final String DFA35_maxS = "\1\172\1\uffff\1\167\1\u00fc\3\151\1\167\12\uffff\1\170\1\166\1"
      + "\156\12\uffff";
   
   static final String DFA35_acceptS = "\1\uffff\1\1\6\uffff\1\15\1\16\1\23\1\2\1\3\1\12\1\4\1\5\1\17\1"
      + "\20\3\uffff\1\11\1\24\1\14\1\25\1\6\1\22\1\7\1\21\1\10\1\13";
   
   static final String DFA35_specialS = "\37\uffff}>";
   
   static final String[] DFA35_transitionS =
   {
    "\1\12\2\uffff\1\10\1\5\1\3\7\uffff\1\6\1\1\3\uffff\1\4\1\2"
       + "\1\uffff\1\11\3\uffff\1\7", "", "\1\15\2\uffff\1\14\16\uffff\1\13",
    "\1\17\5\uffff\1\16\5\uffff\1\21\u0086\uffff\1\20", "\1\23\3\uffff\1\22",
    "\1\24", "\1\26\3\uffff\1\25", "\1\30\21\uffff\1\27", "", "", "", "", "",
    "", "", "", "", "", "\1\32\22\uffff\1\31", "\1\34\22\uffff\1\33",
    "\1\35\6\uffff\1\36", "", "", "", "", "", "", "", "", "", "" };
   
   static final short[] DFA35_eot = DFA.unpackEncodedString( DFA35_eotS );
   
   static final short[] DFA35_eof = DFA.unpackEncodedString( DFA35_eofS );
   
   static final char[] DFA35_min = DFA.unpackEncodedStringToUnsignedChars( DFA35_minS );
   
   static final char[] DFA35_max = DFA.unpackEncodedStringToUnsignedChars( DFA35_maxS );
   
   static final short[] DFA35_accept = DFA.unpackEncodedString( DFA35_acceptS );
   
   static final short[] DFA35_special = DFA.unpackEncodedString( DFA35_specialS );
   
   static final short[][] DFA35_transition;
   
   static
   {
      int numStates = DFA35_transitionS.length;
      DFA35_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA35_transition[ i ] = DFA.unpackEncodedString( DFA35_transitionS[ i ] );
      }
   }
   
   
   class DFA35 extends DFA
   {
      
      public DFA35( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 35;
         this.eot = DFA35_eot;
         this.eof = DFA35_eof;
         this.min = DFA35_min;
         this.max = DFA35_max;
         this.accept = DFA35_accept;
         this.special = DFA35_special;
         this.transition = DFA35_transition;
      }
      


      public String getDescription()
      {
         return "105:1: NUM_STR : ( 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' | 'eins' | 'zwei' | 'drei' | 'vier' | 'fünf' | 'funf' | 'sechs' | 'sieben' | 'acht' | 'neun' | 'zehn' );";
      }
   }
   
   static final String DFA37_eotS = "\74\uffff";
   
   static final String DFA37_eofS = "\74\uffff";
   
   static final String DFA37_minS = "\1\11\1\0\10\uffff\1\0\4\uffff\1\0\2\uffff\1\0\4\uffff\1\0\2\uffff"
      + "\2\0\3\uffff\1\0\1\uffff\1\0\4\uffff\1\0\1\uffff\1\0\1\uffff\1\0"
      + "\1\uffff\1\0\5\uffff\1\0\6\uffff\1\0\1\uffff\1\0";
   
   static final String DFA37_maxS = "\1\uc624\1\0\10\uffff\1\0\4\uffff\1\0\2\uffff\1\0\4\uffff\1\0\2"
      + "\uffff\2\0\3\uffff\1\0\1\uffff\1\0\4\uffff\1\0\1\uffff\1\0\1\uffff"
      + "\1\0\1\uffff\1\0\5\uffff\1\0\6\uffff\1\0\1\uffff\1\0";
   
   static final String DFA37_acceptS = "\2\uffff\1\5\1\6\1\12\1\32\1\43\1\44\1\46\1\45\1\uffff\1\15\1\27"
      + "\1\31\1\33\1\uffff\1\25\1\47\1\uffff\1\16\1\23\1\44\1\5\1\uffff"
      + "\1\37\1\40\2\uffff\1\2\1\17\1\34\1\uffff\1\36\1\uffff\1\1\1\11\1"
      + "\22\1\4\1\uffff\1\42\1\uffff\1\13\1\uffff\1\10\1\uffff\1\14\1\26"
      + "\1\35\1\7\1\15\1\uffff\1\3\1\20\1\21\1\24\1\30\1\41\1\uffff\1\46"
      + "\1\uffff";
   
   static final String DFA37_specialS = "\1\uffff\1\0\10\uffff\1\1\4\uffff\1\2\2\uffff\1\3\4\uffff\1\4\2"
      + "\uffff\1\5\1\6\3\uffff\1\7\1\uffff\1\10\4\uffff\1\11\1\uffff\1\12"
      + "\1\uffff\1\13\1\uffff\1\14\5\uffff\1\15\6\uffff\1\16\1\uffff\1\17}>";
   
   static final String[] DFA37_transitionS =
   {
    "\2\21\2\uffff\1\21\22\uffff\1\21\13\uffff\1\70\1\27\1\57\1"
       + "\36\12\47\1\40\5\uffff\1\26\40\uffff\1\1\2\uffff\1\54\1\50\1"
       + "\37\1\45\1\33\1\60\1\22\2\uffff\1\62\1\41\1\52\1\11\1\uffff"
       + "\1\61\1\12\1\73\1\71\1\72\1\17\1\uffff\1\46\1\72\u4d8f\uffff"
       + "\1\25\1\11\u053c\uffff\1\32\u0b2b\uffff\1\36\u0770\uffff\1\36"
       + "\u0122\uffff\1\36\u5f1b\uffff\1\32", "\1\uffff", "", "", "", "", "",
    "", "", "", "\1\uffff", "", "", "", "", "\1\uffff", "", "", "\1\uffff", "",
    "", "", "", "\1\uffff", "", "", "\1\uffff", "\1\uffff", "", "", "",
    "\1\uffff", "", "\1\uffff", "", "", "", "", "\1\uffff", "", "\1\uffff", "",
    "\1\uffff", "", "\1\uffff", "", "", "", "", "", "\1\uffff", "", "", "", "",
    "", "", "\1\uffff", "", "\1\uffff" };
   
   static final short[] DFA37_eot = DFA.unpackEncodedString( DFA37_eotS );
   
   static final short[] DFA37_eof = DFA.unpackEncodedString( DFA37_eofS );
   
   static final char[] DFA37_min = DFA.unpackEncodedStringToUnsignedChars( DFA37_minS );
   
   static final char[] DFA37_max = DFA.unpackEncodedStringToUnsignedChars( DFA37_maxS );
   
   static final short[] DFA37_accept = DFA.unpackEncodedString( DFA37_acceptS );
   
   static final short[] DFA37_special = DFA.unpackEncodedString( DFA37_specialS );
   
   static final short[][] DFA37_transition;
   
   static
   {
      int numStates = DFA37_transitionS.length;
      DFA37_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA37_transition[ i ] = DFA.unpackEncodedString( DFA37_transitionS[ i ] );
      }
   }
   
   
   class DFA37 extends DFA
   {
      
      public DFA37( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 37;
         this.eot = DFA37_eot;
         this.eof = DFA37_eof;
         this.min = DFA37_min;
         this.max = DFA37_max;
         this.accept = DFA37_accept;
         this.special = DFA37_special;
         this.transition = DFA37_transition;
      }
      


      public String getDescription()
      {
         return "1:1: Tokens options {k=1; backtrack=true; } : ( NEVER | TODAY | TOMORROW | YESTERDAY | AT | ON | IN | OF | NEXT | AND | END | THE | STs | NOW | TONIGHT | MIDNIGHT | MIDDAY | NOON | YEARS | MONTHS | WEEKS | DAYS | HOURS | MINUTES | SECONDS | MONTH | WEEKDAY | DATE_SEP | DOT | COLON | MINUS | MINUS_A | COMMA | INT | A | AM | PM | NUM_STR | WS );";
      }
      


      public int specialStateTransition( int s, IntStream _input ) throws NoViableAltException
      {
         IntStream input = _input;
         int _s = s;
         switch ( s )
         {
            case 0:
               int LA37_1 = input.LA( 1 );
               
               int index37_1 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred5_DateTimeLexer() ) )
               {
                  s = 2;
               }
               
               else if ( ( synpred6_DateTimeLexer() ) )
               {
                  s = 3;
               }
               
               else if ( ( synpred10_DateTimeLexer() ) )
               {
                  s = 4;
               }
               
               else if ( ( synpred26_DateTimeLexer() ) )
               {
                  s = 5;
               }
               
               else if ( ( synpred35_DateTimeLexer() ) )
               {
                  s = 6;
               }
               
               else if ( ( synpred36_DateTimeLexer() ) )
               {
                  s = 7;
               }
               
               else if ( ( synpred38_DateTimeLexer() ) )
               {
                  s = 8;
               }
               
               input.seek( index37_1 );
               if ( s >= 0 )
                  return s;
               break;
            case 1:
               int LA37_10 = input.LA( 1 );
               
               int index37_10 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred13_DateTimeLexer() ) )
               {
                  s = 11;
               }
               
               else if ( ( synpred23_DateTimeLexer() ) )
               {
                  s = 12;
               }
               
               else if ( ( synpred25_DateTimeLexer() ) )
               {
                  s = 13;
               }
               
               else if ( ( synpred26_DateTimeLexer() ) )
               {
                  s = 5;
               }
               
               else if ( ( synpred27_DateTimeLexer() ) )
               {
                  s = 14;
               }
               
               else if ( ( synpred38_DateTimeLexer() ) )
               {
                  s = 8;
               }
               
               input.seek( index37_10 );
               if ( s >= 0 )
                  return s;
               break;
            case 2:
               int LA37_15 = input.LA( 1 );
               
               int index37_15 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred21_DateTimeLexer() ) )
               {
                  s = 16;
               }
               
               else if ( ( synpred27_DateTimeLexer() ) )
               {
                  s = 14;
               }
               
               input.seek( index37_15 );
               if ( s >= 0 )
                  return s;
               break;
            case 3:
               int LA37_18 = input.LA( 1 );
               
               int index37_18 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred14_DateTimeLexer() ) )
               {
                  s = 19;
               }
               
               else if ( ( synpred19_DateTimeLexer() ) )
               {
                  s = 20;
               }
               
               else if ( ( synpred26_DateTimeLexer() ) )
               {
                  s = 5;
               }
               
               input.seek( index37_18 );
               if ( s >= 0 )
                  return s;
               break;
            case 4:
               int LA37_23 = input.LA( 1 );
               
               int index37_23 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred31_DateTimeLexer() ) )
               {
                  s = 24;
               }
               
               else if ( ( synpred32_DateTimeLexer() ) )
               {
                  s = 25;
               }
               
               input.seek( index37_23 );
               if ( s >= 0 )
                  return s;
               break;
            case 5:
               int LA37_26 = input.LA( 1 );
               
               int index37_26 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred36_DateTimeLexer() ) )
               {
                  s = 21;
               }
               
               else if ( ( synpred37_DateTimeLexer() ) )
               {
                  s = 9;
               }
               
               input.seek( index37_26 );
               if ( s >= 0 )
                  return s;
               break;
            case 6:
               int LA37_27 = input.LA( 1 );
               
               int index37_27 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred2_DateTimeLexer() ) )
               {
                  s = 28;
               }
               
               else if ( ( synpred15_DateTimeLexer() ) )
               {
                  s = 29;
               }
               
               else if ( ( synpred23_DateTimeLexer() ) )
               {
                  s = 12;
               }
               
               input.seek( index37_27 );
               if ( s >= 0 )
                  return s;
               break;
            case 7:
               int LA37_31 = input.LA( 1 );
               
               int index37_31 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred26_DateTimeLexer() ) )
               {
                  s = 5;
               }
               
               else if ( ( synpred27_DateTimeLexer() ) )
               {
                  s = 14;
               }
               
               else if ( ( synpred38_DateTimeLexer() ) )
               {
                  s = 8;
               }
               
               input.seek( index37_31 );
               if ( s >= 0 )
                  return s;
               break;
            case 8:
               int LA37_33 = input.LA( 1 );
               
               int index37_33 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred1_DateTimeLexer() ) )
               {
                  s = 34;
               }
               
               else if ( ( synpred9_DateTimeLexer() ) )
               {
                  s = 35;
               }
               
               else if ( ( synpred13_DateTimeLexer() ) )
               {
                  s = 11;
               }
               
               else if ( ( synpred14_DateTimeLexer() ) )
               {
                  s = 19;
               }
               
               else if ( ( synpred18_DateTimeLexer() ) )
               {
                  s = 36;
               }
               
               else if ( ( synpred26_DateTimeLexer() ) )
               {
                  s = 5;
               }
               
               else if ( ( synpred38_DateTimeLexer() ) )
               {
                  s = 8;
               }
               
               input.seek( index37_33 );
               if ( s >= 0 )
                  return s;
               break;
            case 9:
               int LA37_38 = input.LA( 1 );
               
               int index37_38 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred4_DateTimeLexer() ) )
               {
                  s = 37;
               }
               
               else if ( ( synpred19_DateTimeLexer() ) )
               {
                  s = 20;
               }
               
               input.seek( index37_38 );
               if ( s >= 0 )
                  return s;
               break;
            case 10:
               int LA37_40 = input.LA( 1 );
               
               int index37_40 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred11_DateTimeLexer() ) )
               {
                  s = 41;
               }
               
               else if ( ( synpred38_DateTimeLexer() ) )
               {
                  s = 8;
               }
               
               input.seek( index37_40 );
               if ( s >= 0 )
                  return s;
               break;
            case 11:
               int LA37_42 = input.LA( 1 );
               
               int index37_42 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred6_DateTimeLexer() ) )
               {
                  s = 3;
               }
               
               else if ( ( synpred8_DateTimeLexer() ) )
               {
                  s = 43;
               }
               
               else if ( ( synpred26_DateTimeLexer() ) )
               {
                  s = 5;
               }
               
               else if ( ( synpred38_DateTimeLexer() ) )
               {
                  s = 8;
               }
               
               input.seek( index37_42 );
               if ( s >= 0 )
                  return s;
               break;
            case 12:
               int LA37_44 = input.LA( 1 );
               
               int index37_44 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred8_DateTimeLexer() ) )
               {
                  s = 43;
               }
               
               else if ( ( synpred12_DateTimeLexer() ) )
               {
                  s = 45;
               }
               
               else if ( ( synpred22_DateTimeLexer() ) )
               {
                  s = 46;
               }
               
               else if ( ( synpred26_DateTimeLexer() ) )
               {
                  s = 5;
               }
               
               else if ( ( synpred27_DateTimeLexer() ) )
               {
                  s = 14;
               }
               
               else if ( ( synpred38_DateTimeLexer() ) )
               {
                  s = 8;
               }
               
               input.seek( index37_44 );
               if ( s >= 0 )
                  return s;
               break;
            case 13:
               int LA37_50 = input.LA( 1 );
               
               int index37_50 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred3_DateTimeLexer() ) )
               {
                  s = 51;
               }
               
               else if ( ( synpred16_DateTimeLexer() ) )
               {
                  s = 52;
               }
               
               else if ( ( synpred17_DateTimeLexer() ) )
               {
                  s = 53;
               }
               
               else if ( ( synpred18_DateTimeLexer() ) )
               {
                  s = 36;
               }
               
               else if ( ( synpred20_DateTimeLexer() ) )
               {
                  s = 54;
               }
               
               else if ( ( synpred24_DateTimeLexer() ) )
               {
                  s = 55;
               }
               
               else if ( ( synpred26_DateTimeLexer() ) )
               {
                  s = 5;
               }
               
               else if ( ( synpred27_DateTimeLexer() ) )
               {
                  s = 14;
               }
               
               input.seek( index37_50 );
               if ( s >= 0 )
                  return s;
               break;
            case 14:
               int LA37_57 = input.LA( 1 );
               
               int index37_57 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred5_DateTimeLexer() ) )
               {
                  s = 22;
               }
               
               else if ( ( synpred10_DateTimeLexer() ) )
               {
                  s = 4;
               }
               
               input.seek( index37_57 );
               if ( s >= 0 )
                  return s;
               break;
            case 15:
               int LA37_59 = input.LA( 1 );
               
               int index37_59 = input.index();
               input.rewind();
               s = -1;
               if ( ( synpred2_DateTimeLexer() ) )
               {
                  s = 28;
               }
               
               else if ( ( synpred3_DateTimeLexer() ) )
               {
                  s = 51;
               }
               
               else if ( ( synpred12_DateTimeLexer() ) )
               {
                  s = 45;
               }
               
               else if ( ( synpred13_DateTimeLexer() ) )
               {
                  s = 49;
               }
               
               else if ( ( synpred15_DateTimeLexer() ) )
               {
                  s = 29;
               }
               
               else if ( ( synpred22_DateTimeLexer() ) )
               {
                  s = 46;
               }
               
               else if ( ( synpred27_DateTimeLexer() ) )
               {
                  s = 14;
               }
               
               else if ( ( synpred38_DateTimeLexer() ) )
               {
                  s = 58;
               }
               
               input.seek( index37_59 );
               if ( s >= 0 )
                  return s;
               break;
         }
         if ( state.backtracking > 0 )
         {
            state.failed = true;
            return -1;
         }
         NoViableAltException nvae = new NoViableAltException( getDescription(),
                                                               37,
                                                               _s,
                                                               input );
         error( nvae );
         throw nvae;
      }
   }
   
}
