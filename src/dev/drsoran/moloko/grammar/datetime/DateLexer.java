// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g 2013-01-13 13:41:15

package dev.drsoran.moloko.grammar.datetime;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

import dev.drsoran.moloko.grammar.LexerException;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class DateLexer extends Lexer
{
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
   
   
   
   public DateLexer()
   {
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
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g";
   }
   
   
   
   // $ANTLR start "A"
   public final void mA() throws RecognitionException
   {
      try
      {
         int _type = A;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:420:11:
         // ( 'a' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:420:13:
         // 'a'
         {
            match( 'a' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:422:11:
         // ( 'of' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:422:13:
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
   
   // $ANTLR start "ON"
   public final void mON() throws RecognitionException
   {
      try
      {
         int _type = ON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:424:11:
         // ( 'on' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:424:13:
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
   
   // $ANTLR start "IN"
   public final void mIN() throws RecognitionException
   {
      try
      {
         int _type = IN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:426:11:
         // ( 'in' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:426:13:
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
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException
   {
      try
      {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:428:11:
         // ( 'and' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:428:13:
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
   
   // $ANTLR start "END"
   public final void mEND() throws RecognitionException
   {
      try
      {
         int _type = END;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:430:11:
         // ( 'end' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:430:13:
         // 'end'
         {
            match( "end" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:432:11:
         // ( 'the' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:432:13:
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
   
   // $ANTLR start "NOW"
   public final void mNOW() throws RecognitionException
   {
      try
      {
         int _type = NOW;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:434:11:
         // ( 'now' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:434:13:
         // 'now'
         {
            match( "now" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:436:11:
         // ( 'tonight' | 'ton' )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:436:13:
            // 'tonight'
            {
               match( "tonight" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:436:25:
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
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:438:11:
         // ( 'never' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:438:13:
         // 'never'
         {
            match( "never" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:440:11:
         // ( 'today' | 'tod' )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:440:13:
            // 'today'
            {
               match( "today" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:440:23:
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
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:442:11:
         // ( 'tomorrow' | 'tom' | 'tmr' )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:442:13:
            // 'tomorrow'
            {
               match( "tomorrow" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:442:26:
            // 'tom'
            {
               match( "tom" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:442:34:
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
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:444:11:
         // ( 'yesterday' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:444:13:
         // 'yesterday'
         {
            match( "yesterday" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:446:11:
         // ( 'next' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:446:13:
         // 'next'
         {
            match( "next" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:448:11:
         // ( 'st' | 'th' | 'rd' | 'nd' )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:448:13:
            // 'st'
            {
               match( "st" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:448:20:
            // 'th'
            {
               match( "th" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:448:27:
            // 'rd'
            {
               match( "rd" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:448:34:
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
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:450:11:
         // ( 'years' | 'year' | 'yrs' | 'yr' )
         int alt5 = 4;
         int LA5_0 = input.LA( 1 );
         
         if ( ( LA5_0 == 'y' ) )
         {
            int LA5_1 = input.LA( 2 );
            
            if ( ( LA5_1 == 'e' ) )
            {
               int LA5_2 = input.LA( 3 );
               
               if ( ( LA5_2 == 'a' ) )
               {
                  int LA5_4 = input.LA( 4 );
                  
                  if ( ( LA5_4 == 'r' ) )
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
            else if ( ( LA5_1 == 'r' ) )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:450:13:
            // 'years'
            {
               match( "years" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:450:23:
            // 'year'
            {
               match( "year" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:450:32:
            // 'yrs'
            {
               match( "yrs" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:450:40:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:452:11:
         // ( 'months' | 'month' | 'mons' | 'mon' )
         int alt6 = 4;
         int LA6_0 = input.LA( 1 );
         
         if ( ( LA6_0 == 'm' ) )
         {
            int LA6_1 = input.LA( 2 );
            
            if ( ( LA6_1 == 'o' ) )
            {
               int LA6_2 = input.LA( 3 );
               
               if ( ( LA6_2 == 'n' ) )
               {
                  switch ( input.LA( 4 ) )
                  {
                     case 't':
                     {
                        int LA6_4 = input.LA( 5 );
                        
                        if ( ( LA6_4 == 'h' ) )
                        {
                           int LA6_7 = input.LA( 6 );
                           
                           if ( ( LA6_7 == 's' ) )
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
                                                                                 4,
                                                                                 input );
                           
                           throw nvae;
                           
                        }
                     }
                        break;
                     case 's':
                     {
                        alt6 = 3;
                     }
                        break;
                     default :
                        alt6 = 4;
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:452:13:
            // 'months'
            {
               match( "months" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:452:24:
            // 'month'
            {
               match( "month" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:452:34:
            // 'mons'
            {
               match( "mons" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:452:43:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:454:11:
         // ( 'weeks' | 'week' | 'wks' | 'wk' )
         int alt7 = 4;
         int LA7_0 = input.LA( 1 );
         
         if ( ( LA7_0 == 'w' ) )
         {
            int LA7_1 = input.LA( 2 );
            
            if ( ( LA7_1 == 'e' ) )
            {
               int LA7_2 = input.LA( 3 );
               
               if ( ( LA7_2 == 'e' ) )
               {
                  int LA7_4 = input.LA( 4 );
                  
                  if ( ( LA7_4 == 'k' ) )
                  {
                     int LA7_7 = input.LA( 5 );
                     
                     if ( ( LA7_7 == 's' ) )
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
                                                                           4,
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
            else if ( ( LA7_1 == 'k' ) )
            {
               int LA7_3 = input.LA( 3 );
               
               if ( ( LA7_3 == 's' ) )
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
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  7,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt7 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:454:13:
            // 'weeks'
            {
               match( "weeks" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:454:23:
            // 'week'
            {
               match( "week" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:454:32:
            // 'wks'
            {
               match( "wks" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:454:40:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:456:11:
         // ( 'days' | 'day' | 'd' )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:456:13:
            // 'days'
            {
               match( "days" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:456:22:
            // 'day'
            {
               match( "day" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:456:30:
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
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:11:
         // ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' |
         // 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' |
         // 'december' | 'dec' )
         int alt9 = 24;
         switch ( input.LA( 1 ) )
         {
            case 'j':
            {
               int LA9_1 = input.LA( 2 );
               
               if ( ( LA9_1 == 'a' ) )
               {
                  int LA9_9 = input.LA( 3 );
                  
                  if ( ( LA9_9 == 'n' ) )
                  {
                     int LA9_19 = input.LA( 4 );
                     
                     if ( ( LA9_19 == 'u' ) )
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
                                                                           9,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else if ( ( LA9_1 == 'u' ) )
               {
                  int LA9_10 = input.LA( 3 );
                  
                  if ( ( LA9_10 == 'n' ) )
                  {
                     int LA9_20 = input.LA( 4 );
                     
                     if ( ( LA9_20 == 'e' ) )
                     {
                        alt9 = 10;
                     }
                     else
                     {
                        alt9 = 11;
                     }
                  }
                  else if ( ( LA9_10 == 'l' ) )
                  {
                     int LA9_21 = input.LA( 4 );
                     
                     if ( ( LA9_21 == 'y' ) )
                     {
                        alt9 = 12;
                     }
                     else
                     {
                        alt9 = 13;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           9,
                                                                           10,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        1,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'f':
            {
               int LA9_2 = input.LA( 2 );
               
               if ( ( LA9_2 == 'e' ) )
               {
                  int LA9_11 = input.LA( 3 );
                  
                  if ( ( LA9_11 == 'b' ) )
                  {
                     int LA9_22 = input.LA( 4 );
                     
                     if ( ( LA9_22 == 'r' ) )
                     {
                        alt9 = 3;
                     }
                     else
                     {
                        alt9 = 4;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           9,
                                                                           11,
                                                                           input );
                     
                     throw nvae;
                     
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
               break;
            case 'm':
            {
               int LA9_3 = input.LA( 2 );
               
               if ( ( LA9_3 == 'a' ) )
               {
                  int LA9_12 = input.LA( 3 );
                  
                  if ( ( LA9_12 == 'r' ) )
                  {
                     int LA9_23 = input.LA( 4 );
                     
                     if ( ( LA9_23 == 'c' ) )
                     {
                        alt9 = 5;
                     }
                     else
                     {
                        alt9 = 6;
                     }
                  }
                  else if ( ( LA9_12 == 'y' ) )
                  {
                     alt9 = 9;
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           9,
                                                                           12,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        3,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'a':
            {
               int LA9_4 = input.LA( 2 );
               
               if ( ( LA9_4 == 'p' ) )
               {
                  int LA9_13 = input.LA( 3 );
                  
                  if ( ( LA9_13 == 'r' ) )
                  {
                     int LA9_25 = input.LA( 4 );
                     
                     if ( ( LA9_25 == 'i' ) )
                     {
                        alt9 = 7;
                     }
                     else
                     {
                        alt9 = 8;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           9,
                                                                           13,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else if ( ( LA9_4 == 'u' ) )
               {
                  int LA9_14 = input.LA( 3 );
                  
                  if ( ( LA9_14 == 'g' ) )
                  {
                     int LA9_26 = input.LA( 4 );
                     
                     if ( ( LA9_26 == 'u' ) )
                     {
                        alt9 = 14;
                     }
                     else
                     {
                        alt9 = 15;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           9,
                                                                           14,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        4,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 's':
            {
               int LA9_5 = input.LA( 2 );
               
               if ( ( LA9_5 == 'e' ) )
               {
                  int LA9_15 = input.LA( 3 );
                  
                  if ( ( LA9_15 == 'p' ) )
                  {
                     int LA9_27 = input.LA( 4 );
                     
                     if ( ( LA9_27 == 't' ) )
                     {
                        int LA9_45 = input.LA( 5 );
                        
                        if ( ( LA9_45 == 'e' ) )
                        {
                           alt9 = 16;
                        }
                        else
                        {
                           alt9 = 17;
                        }
                     }
                     else
                     {
                        alt9 = 18;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           9,
                                                                           15,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        5,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'o':
            {
               int LA9_6 = input.LA( 2 );
               
               if ( ( LA9_6 == 'c' ) )
               {
                  int LA9_16 = input.LA( 3 );
                  
                  if ( ( LA9_16 == 't' ) )
                  {
                     int LA9_28 = input.LA( 4 );
                     
                     if ( ( LA9_28 == 'o' ) )
                     {
                        alt9 = 19;
                     }
                     else
                     {
                        alt9 = 20;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           9,
                                                                           16,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        6,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'n':
            {
               int LA9_7 = input.LA( 2 );
               
               if ( ( LA9_7 == 'o' ) )
               {
                  int LA9_17 = input.LA( 3 );
                  
                  if ( ( LA9_17 == 'v' ) )
                  {
                     int LA9_29 = input.LA( 4 );
                     
                     if ( ( LA9_29 == 'e' ) )
                     {
                        alt9 = 21;
                     }
                     else
                     {
                        alt9 = 22;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           9,
                                                                           17,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        7,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'd':
            {
               int LA9_8 = input.LA( 2 );
               
               if ( ( LA9_8 == 'e' ) )
               {
                  int LA9_18 = input.LA( 3 );
                  
                  if ( ( LA9_18 == 'c' ) )
                  {
                     int LA9_30 = input.LA( 4 );
                     
                     if ( ( LA9_30 == 'e' ) )
                     {
                        alt9 = 23;
                     }
                     else
                     {
                        alt9 = 24;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           9,
                                                                           18,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        8,
                                                                        input );
                  
                  throw nvae;
                  
               }
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:13:
            // 'january'
            {
               match( "january" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:27:
            // 'jan'
            {
               match( "jan" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:36:
            // 'february'
            {
               match( "february" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:49:
            // 'feb'
            {
               match( "feb" );
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:61:
            // 'march'
            {
               match( "march" );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:71:
            // 'mar'
            {
               match( "mar" );
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:84:
            // 'april'
            {
               match( "april" );
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:458:94:
            // 'apr'
            {
               match( "apr" );
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:459:13:
            // 'may'
            {
               match( "may" );
               
            }
               break;
            case 10:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:459:27:
            // 'june'
            {
               match( "june" );
               
            }
               break;
            case 11:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:459:36:
            // 'jun'
            {
               match( "jun" );
               
            }
               break;
            case 12:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:459:49:
            // 'july'
            {
               match( "july" );
               
            }
               break;
            case 13:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:459:61:
            // 'jul'
            {
               match( "jul" );
               
            }
               break;
            case 14:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:459:71:
            // 'august'
            {
               match( "august" );
               
            }
               break;
            case 15:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:459:84:
            // 'aug'
            {
               match( "aug" );
               
            }
               break;
            case 16:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:460:13:
            // 'september'
            {
               match( "september" );
               
            }
               break;
            case 17:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:460:27:
            // 'sept'
            {
               match( "sept" );
               
            }
               break;
            case 18:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:460:36:
            // 'sep'
            {
               match( "sep" );
               
            }
               break;
            case 19:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:460:49:
            // 'october'
            {
               match( "october" );
               
            }
               break;
            case 20:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:460:61:
            // 'oct'
            {
               match( "oct" );
               
            }
               break;
            case 21:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:460:71:
            // 'november'
            {
               match( "november" );
               
            }
               break;
            case 22:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:460:84:
            // 'nov'
            {
               match( "nov" );
               
            }
               break;
            case 23:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:461:13:
            // 'december'
            {
               match( "december" );
               
            }
               break;
            case 24:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:461:27:
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
   
   // $ANTLR start "WEEKDAY"
   public final void mWEEKDAY() throws RecognitionException
   {
      try
      {
         int _type = WEEKDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:463:11:
         // ( 'monday' | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' | 'thursday' | 'thu' | 'friday' | 'fri' |
         // 'saturday' | 'sat' | 'sunday' | 'sun' )
         int alt10 = 14;
         switch ( input.LA( 1 ) )
         {
            case 'm':
            {
               int LA10_1 = input.LA( 2 );
               
               if ( ( LA10_1 == 'o' ) )
               {
                  int LA10_6 = input.LA( 3 );
                  
                  if ( ( LA10_6 == 'n' ) )
                  {
                     int LA10_13 = input.LA( 4 );
                     
                     if ( ( LA10_13 == 'd' ) )
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
                                                                           6,
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
               break;
            case 't':
            {
               int LA10_2 = input.LA( 2 );
               
               if ( ( LA10_2 == 'u' ) )
               {
                  int LA10_7 = input.LA( 3 );
                  
                  if ( ( LA10_7 == 'e' ) )
                  {
                     int LA10_14 = input.LA( 4 );
                     
                     if ( ( LA10_14 == 's' ) )
                     {
                        alt10 = 3;
                     }
                     else
                     {
                        alt10 = 4;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           10,
                                                                           7,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else if ( ( LA10_2 == 'h' ) )
               {
                  int LA10_8 = input.LA( 3 );
                  
                  if ( ( LA10_8 == 'u' ) )
                  {
                     int LA10_15 = input.LA( 4 );
                     
                     if ( ( LA10_15 == 'r' ) )
                     {
                        alt10 = 7;
                     }
                     else
                     {
                        alt10 = 8;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           10,
                                                                           8,
                                                                           input );
                     
                     throw nvae;
                     
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
               break;
            case 'w':
            {
               int LA10_3 = input.LA( 2 );
               
               if ( ( LA10_3 == 'e' ) )
               {
                  int LA10_9 = input.LA( 3 );
                  
                  if ( ( LA10_9 == 'd' ) )
                  {
                     int LA10_16 = input.LA( 4 );
                     
                     if ( ( LA10_16 == 'n' ) )
                     {
                        alt10 = 5;
                     }
                     else
                     {
                        alt10 = 6;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           10,
                                                                           9,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        10,
                                                                        3,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'f':
            {
               int LA10_4 = input.LA( 2 );
               
               if ( ( LA10_4 == 'r' ) )
               {
                  int LA10_10 = input.LA( 3 );
                  
                  if ( ( LA10_10 == 'i' ) )
                  {
                     int LA10_17 = input.LA( 4 );
                     
                     if ( ( LA10_17 == 'd' ) )
                     {
                        alt10 = 9;
                     }
                     else
                     {
                        alt10 = 10;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           10,
                                                                           10,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        10,
                                                                        4,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 's':
            {
               int LA10_5 = input.LA( 2 );
               
               if ( ( LA10_5 == 'a' ) )
               {
                  int LA10_11 = input.LA( 3 );
                  
                  if ( ( LA10_11 == 't' ) )
                  {
                     int LA10_18 = input.LA( 4 );
                     
                     if ( ( LA10_18 == 'u' ) )
                     {
                        alt10 = 11;
                     }
                     else
                     {
                        alt10 = 12;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           10,
                                                                           11,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else if ( ( LA10_5 == 'u' ) )
               {
                  int LA10_12 = input.LA( 3 );
                  
                  if ( ( LA10_12 == 'n' ) )
                  {
                     int LA10_19 = input.LA( 4 );
                     
                     if ( ( LA10_19 == 'd' ) )
                     {
                        alt10 = 13;
                     }
                     else
                     {
                        alt10 = 14;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           10,
                                                                           12,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        10,
                                                                        5,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     10,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
         }
         
         switch ( alt10 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:463:13:
            // 'monday'
            {
               match( "monday" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:463:26:
            // 'mon'
            {
               match( "mon" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:463:34:
            // 'tuesday'
            {
               match( "tuesday" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:463:46:
            // 'tue'
            {
               match( "tue" );
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:463:54:
            // 'wednesday'
            {
               match( "wednesday" );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:463:68:
            // 'wed'
            {
               match( "wed" );
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:13:
            // 'thursday'
            {
               match( "thursday" );
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:26:
            // 'thu'
            {
               match( "thu" );
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:34:
            // 'friday'
            {
               match( "friday" );
               
            }
               break;
            case 10:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:46:
            // 'fri'
            {
               match( "fri" );
               
            }
               break;
            case 11:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:54:
            // 'saturday'
            {
               match( "saturday" );
               
            }
               break;
            case 12:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:464:68:
            // 'sat'
            {
               match( "sat" );
               
            }
               break;
            case 13:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:465:13:
            // 'sunday'
            {
               match( "sunday" );
               
            }
               break;
            case 14:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:465:26:
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
   
   
   
   // $ANTLR end "WEEKDAY"
   
   // $ANTLR start "NUM_STR"
   public final void mNUM_STR() throws RecognitionException
   {
      try
      {
         int _type = NUM_STR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:11:
         // ( 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' )
         int alt11 = 10;
         switch ( input.LA( 1 ) )
         {
            case 'o':
            {
               alt11 = 1;
            }
               break;
            case 't':
            {
               switch ( input.LA( 2 ) )
               {
                  case 'w':
                  {
                     alt11 = 2;
                  }
                     break;
                  case 'h':
                  {
                     alt11 = 3;
                  }
                     break;
                  case 'e':
                  {
                     alt11 = 10;
                  }
                     break;
                  default :
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           11,
                                                                           2,
                                                                           input );
                     
                     throw nvae;
                     
               }
               
            }
               break;
            case 'f':
            {
               int LA11_3 = input.LA( 2 );
               
               if ( ( LA11_3 == 'o' ) )
               {
                  alt11 = 4;
               }
               else if ( ( LA11_3 == 'i' ) )
               {
                  alt11 = 5;
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        11,
                                                                        3,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 's':
            {
               int LA11_4 = input.LA( 2 );
               
               if ( ( LA11_4 == 'i' ) )
               {
                  alt11 = 6;
               }
               else if ( ( LA11_4 == 'e' ) )
               {
                  alt11 = 7;
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        11,
                                                                        4,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'e':
            {
               alt11 = 8;
            }
               break;
            case 'n':
            {
               alt11 = 9;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     11,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
         }
         
         switch ( alt11 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:13:
            // 'one'
            {
               match( "one" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:21:
            // 'two'
            {
               match( "two" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:29:
            // 'three'
            {
               match( "three" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:39:
            // 'four'
            {
               match( "four" );
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:48:
            // 'five'
            {
               match( "five" );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:57:
            // 'six'
            {
               match( "six" );
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:65:
            // 'seven'
            {
               match( "seven" );
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:75:
            // 'eight'
            {
               match( "eight" );
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:85:
            // 'nine'
            {
               match( "nine" );
               
            }
               break;
            case 10:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:467:94:
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
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:469:11:
         // ( '/' | '\\u5E74' | '\\u6708' | '\\u65E5' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
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
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:471:11:
         // ( '.' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:471:13:
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
   
   // $ANTLR start "COLON"
   public final void mCOLON() throws RecognitionException
   {
      try
      {
         int _type = COLON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:473:11:
         // ( ':' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:473:13:
         // ':'
         {
            match( ':' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:475:11:
         // ( '-' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:475:13:
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
   
   // $ANTLR start "MINUS_A"
   public final void mMINUS_A() throws RecognitionException
   {
      try
      {
         int _type = MINUS_A;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:477:11:
         // ( '-a' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:477:13:
         // '-a'
         {
            match( "-a" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:479:11:
         // ( ',' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:479:13:
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
   
   // $ANTLR start "DATE_TIME_SEPARATOR"
   public final void mDATE_TIME_SEPARATOR() throws RecognitionException
   {
      try
      {
         int _type = DATE_TIME_SEPARATOR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:21:
         // ( '@' | 'at' | COMMA )
         int alt12 = 3;
         switch ( input.LA( 1 ) )
         {
            case '@':
            {
               alt12 = 1;
            }
               break;
            case 'a':
            {
               alt12 = 2;
            }
               break;
            case ',':
            {
               alt12 = 3;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     12,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
         }
         
         switch ( alt12 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:23:
            // '@'
            {
               match( '@' );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:29:
            // 'at'
            {
               match( "at" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:481:36:
            // COMMA
            {
               mCOMMA();
               
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
   
   
   
   // $ANTLR end "DATE_TIME_SEPARATOR"
   
   // $ANTLR start "INT"
   public final void mINT() throws RecognitionException
   {
      try
      {
         int _type = INT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:483:11:
         // ( ( '0' .. '9' )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:483:13:
         // ( '0' .. '9' )+
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:483:13:
            // ( '0' .. '9' )+
            int cnt13 = 0;
            loop13: do
            {
               int alt13 = 2;
               int LA13_0 = input.LA( 1 );
               
               if ( ( ( LA13_0 >= '0' && LA13_0 <= '9' ) ) )
               {
                  alt13 = 1;
               }
               
               switch ( alt13 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
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
                     if ( cnt13 >= 1 )
                        break loop13;
                     EarlyExitException eee = new EarlyExitException( 13, input );
                     throw eee;
               }
               cnt13++;
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
   
   // $ANTLR start "WS"
   public final void mWS() throws RecognitionException
   {
      try
      {
         int _type = WS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:485:11:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:485:13:
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
      // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:8:
      // ( A | OF | ON | IN | AND | END | THE | NOW | TONIGHT | NEVER | TODAY | TOMORROW | YESTERDAY | NEXT | STs |
      // YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY | NUM_STR | DATE_SEP | DOT | COLON | MINUS | MINUS_A | COMMA |
      // DATE_TIME_SEPARATOR | INT | WS )
      int alt14 = 31;
      switch ( input.LA( 1 ) )
      {
         case 'a':
         {
            switch ( input.LA( 2 ) )
            {
               case 'n':
               {
                  alt14 = 5;
               }
                  break;
               case 'p':
               case 'u':
               {
                  alt14 = 20;
               }
                  break;
               case 't':
               {
                  alt14 = 29;
               }
                  break;
               default :
                  alt14 = 1;
            }
            
         }
            break;
         case 'o':
         {
            switch ( input.LA( 2 ) )
            {
               case 'f':
               {
                  alt14 = 2;
               }
                  break;
               case 'n':
               {
                  int LA14_26 = input.LA( 3 );
                  
                  if ( ( LA14_26 == 'e' ) )
                  {
                     alt14 = 22;
                  }
                  else
                  {
                     alt14 = 3;
                  }
               }
                  break;
               case 'c':
               {
                  alt14 = 20;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        14,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case 'i':
         {
            alt14 = 4;
         }
            break;
         case 'e':
         {
            int LA14_4 = input.LA( 2 );
            
            if ( ( LA14_4 == 'n' ) )
            {
               alt14 = 6;
            }
            else if ( ( LA14_4 == 'i' ) )
            {
               alt14 = 22;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     14,
                                                                     4,
                                                                     input );
               
               throw nvae;
               
            }
         }
            break;
         case 't':
         {
            switch ( input.LA( 2 ) )
            {
               case 'h':
               {
                  switch ( input.LA( 3 ) )
                  {
                     case 'e':
                     {
                        alt14 = 7;
                     }
                        break;
                     case 'u':
                     {
                        alt14 = 21;
                     }
                        break;
                     case 'r':
                     {
                        alt14 = 22;
                     }
                        break;
                     default :
                        alt14 = 15;
                  }
                  
               }
                  break;
               case 'o':
               {
                  switch ( input.LA( 3 ) )
                  {
                     case 'n':
                     {
                        alt14 = 9;
                     }
                        break;
                     case 'd':
                     {
                        alt14 = 11;
                     }
                        break;
                     case 'm':
                     {
                        alt14 = 12;
                     }
                        break;
                     default :
                        NoViableAltException nvae = new NoViableAltException( "",
                                                                              14,
                                                                              30,
                                                                              input );
                        
                        throw nvae;
                        
                  }
                  
               }
                  break;
               case 'm':
               {
                  alt14 = 12;
               }
                  break;
               case 'u':
               {
                  alt14 = 21;
               }
                  break;
               case 'e':
               case 'w':
               {
                  alt14 = 22;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        14,
                                                                        5,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case 'n':
         {
            switch ( input.LA( 2 ) )
            {
               case 'o':
               {
                  int LA14_33 = input.LA( 3 );
                  
                  if ( ( LA14_33 == 'w' ) )
                  {
                     alt14 = 8;
                  }
                  else if ( ( LA14_33 == 'v' ) )
                  {
                     alt14 = 20;
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           14,
                                                                           33,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
                  break;
               case 'e':
               {
                  int LA14_34 = input.LA( 3 );
                  
                  if ( ( LA14_34 == 'v' ) )
                  {
                     alt14 = 10;
                  }
                  else if ( ( LA14_34 == 'x' ) )
                  {
                     alt14 = 14;
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           14,
                                                                           34,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
                  break;
               case 'd':
               {
                  alt14 = 15;
               }
                  break;
               case 'i':
               {
                  alt14 = 22;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        14,
                                                                        6,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case 'y':
         {
            int LA14_7 = input.LA( 2 );
            
            if ( ( LA14_7 == 'e' ) )
            {
               int LA14_35 = input.LA( 3 );
               
               if ( ( LA14_35 == 's' ) )
               {
                  alt14 = 13;
               }
               else if ( ( LA14_35 == 'a' ) )
               {
                  alt14 = 16;
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        14,
                                                                        35,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else if ( ( LA14_7 == 'r' ) )
            {
               alt14 = 16;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     14,
                                                                     7,
                                                                     input );
               
               throw nvae;
               
            }
         }
            break;
         case 's':
         {
            switch ( input.LA( 2 ) )
            {
               case 't':
               {
                  alt14 = 15;
               }
                  break;
               case 'e':
               {
                  int LA14_37 = input.LA( 3 );
                  
                  if ( ( LA14_37 == 'p' ) )
                  {
                     alt14 = 20;
                  }
                  else if ( ( LA14_37 == 'v' ) )
                  {
                     alt14 = 22;
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           14,
                                                                           37,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
                  break;
               case 'a':
               case 'u':
               {
                  alt14 = 21;
               }
                  break;
               case 'i':
               {
                  alt14 = 22;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        14,
                                                                        8,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case 'r':
         {
            alt14 = 15;
         }
            break;
         case 'm':
         {
            int LA14_10 = input.LA( 2 );
            
            if ( ( LA14_10 == 'o' ) )
            {
               int LA14_38 = input.LA( 3 );
               
               if ( ( LA14_38 == 'n' ) )
               {
                  int LA14_53 = input.LA( 4 );
                  
                  if ( ( LA14_53 == 'd' ) )
                  {
                     alt14 = 21;
                  }
                  else
                  {
                     alt14 = 17;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        14,
                                                                        38,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else if ( ( LA14_10 == 'a' ) )
            {
               alt14 = 20;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     14,
                                                                     10,
                                                                     input );
               
               throw nvae;
               
            }
         }
            break;
         case 'w':
         {
            int LA14_11 = input.LA( 2 );
            
            if ( ( LA14_11 == 'e' ) )
            {
               int LA14_39 = input.LA( 3 );
               
               if ( ( LA14_39 == 'e' ) )
               {
                  alt14 = 18;
               }
               else if ( ( LA14_39 == 'd' ) )
               {
                  alt14 = 21;
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        14,
                                                                        39,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else if ( ( LA14_11 == 'k' ) )
            {
               alt14 = 18;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     14,
                                                                     11,
                                                                     input );
               
               throw nvae;
               
            }
         }
            break;
         case 'd':
         {
            int LA14_12 = input.LA( 2 );
            
            if ( ( LA14_12 == 'e' ) )
            {
               alt14 = 20;
            }
            else
            {
               alt14 = 19;
            }
         }
            break;
         case 'j':
         {
            alt14 = 20;
         }
            break;
         case 'f':
         {
            switch ( input.LA( 2 ) )
            {
               case 'e':
               {
                  alt14 = 20;
               }
                  break;
               case 'r':
               {
                  alt14 = 21;
               }
                  break;
               case 'i':
               case 'o':
               {
                  alt14 = 22;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        14,
                                                                        14,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case '/':
         case '\u5E74':
         case '\u65E5':
         case '\u6708':
         {
            alt14 = 23;
         }
            break;
         case '.':
         {
            alt14 = 24;
         }
            break;
         case ':':
         {
            alt14 = 25;
         }
            break;
         case '-':
         {
            int LA14_18 = input.LA( 2 );
            
            if ( ( LA14_18 == 'a' ) )
            {
               alt14 = 27;
            }
            else
            {
               alt14 = 26;
            }
         }
            break;
         case ',':
         {
            alt14 = 28;
         }
            break;
         case '@':
         {
            alt14 = 29;
         }
            break;
         case '0':
         case '1':
         case '2':
         case '3':
         case '4':
         case '5':
         case '6':
         case '7':
         case '8':
         case '9':
         {
            alt14 = 30;
         }
            break;
         case '\t':
         case '\n':
         case '\r':
         case ' ':
         {
            alt14 = 31;
         }
            break;
         default :
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  14,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
      }
      
      switch ( alt14 )
      {
         case 1:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:10:
         // A
         {
            mA();
            
         }
            break;
         case 2:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:12:
         // OF
         {
            mOF();
            
         }
            break;
         case 3:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:15:
         // ON
         {
            mON();
            
         }
            break;
         case 4:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:18:
         // IN
         {
            mIN();
            
         }
            break;
         case 5:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:21:
         // AND
         {
            mAND();
            
         }
            break;
         case 6:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:25:
         // END
         {
            mEND();
            
         }
            break;
         case 7:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:29:
         // THE
         {
            mTHE();
            
         }
            break;
         case 8:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:33:
         // NOW
         {
            mNOW();
            
         }
            break;
         case 9:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:37:
         // TONIGHT
         {
            mTONIGHT();
            
         }
            break;
         case 10:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:45:
         // NEVER
         {
            mNEVER();
            
         }
            break;
         case 11:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:51:
         // TODAY
         {
            mTODAY();
            
         }
            break;
         case 12:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:57:
         // TOMORROW
         {
            mTOMORROW();
            
         }
            break;
         case 13:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:66:
         // YESTERDAY
         {
            mYESTERDAY();
            
         }
            break;
         case 14:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:76:
         // NEXT
         {
            mNEXT();
            
         }
            break;
         case 15:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:81:
         // STs
         {
            mSTs();
            
         }
            break;
         case 16:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:85:
         // YEARS
         {
            mYEARS();
            
         }
            break;
         case 17:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:91:
         // MONTHS
         {
            mMONTHS();
            
         }
            break;
         case 18:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:98:
         // WEEKS
         {
            mWEEKS();
            
         }
            break;
         case 19:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:104:
         // DAYS
         {
            mDAYS();
            
         }
            break;
         case 20:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:109:
         // MONTH
         {
            mMONTH();
            
         }
            break;
         case 21:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:115:
         // WEEKDAY
         {
            mWEEKDAY();
            
         }
            break;
         case 22:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:123:
         // NUM_STR
         {
            mNUM_STR();
            
         }
            break;
         case 23:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:131:
         // DATE_SEP
         {
            mDATE_SEP();
            
         }
            break;
         case 24:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:140:
         // DOT
         {
            mDOT();
            
         }
            break;
         case 25:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:144:
         // COLON
         {
            mCOLON();
            
         }
            break;
         case 26:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:150:
         // MINUS
         {
            mMINUS();
            
         }
            break;
         case 27:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:156:
         // MINUS_A
         {
            mMINUS_A();
            
         }
            break;
         case 28:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:164:
         // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 29:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:170:
         // DATE_TIME_SEPARATOR
         {
            mDATE_TIME_SEPARATOR();
            
         }
            break;
         case 30:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:190:
         // INT
         {
            mINT();
            
         }
            break;
         case 31:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:1:194:
         // WS
         {
            mWS();
            
         }
            break;
      
      }
      
   }
   
}
