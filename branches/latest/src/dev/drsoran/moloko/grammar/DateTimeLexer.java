// $ANTLR 3.3 Nov 30, 2010 12:45:30 F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g 2011-03-11 13:12:20

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
   


   @Override
   public String getGrammarFileName()
   {
      return "F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g";
   }
   


   // $ANTLR start "NEVER"
   public final void mNEVER() throws RecognitionException
   {
      try
      {
         int _type = NEVER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:13:11: ( 'never' | 'nie' )
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
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:13:13: 'never'
            {
               match( "never" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:13:23: 'nie'
            {
               match( "nie" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:15:11: ( 'today' | 'tod' |
         // 'heute' )
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
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        2,
                                                                        3,
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
         else if ( ( LA2_0 == 'h' ) )
         {
            alt2 = 3;
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
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:15:13: 'today'
            {
               match( "today" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:15:23: 'tod'
            {
               match( "tod" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:15:31: 'heute'
            {
               match( "heute" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:17:11: ( 'tomorrow' | 'tom' |
         // 'tmr' | 'morgen' )
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
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  3,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt3 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:17:13: 'tomorrow'
            {
               match( "tomorrow" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:17:26: 'tom'
            {
               match( "tom" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:17:34: 'tmr'
            {
               match( "tmr" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:17:42: 'morgen'
            {
               match( "morgen" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:19:11: ( 'yesterday' |
         // 'gestern' )
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
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  4,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt4 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:19:13: 'yesterday'
            {
               match( "yesterday" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:19:27: 'gestern'
            {
               match( "gestern" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:21:11: ( '@' | 'at' | 'um' )
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
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     5,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt5 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:21:13: '@'
            {
               match( '@' );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:21:19: 'at'
            {
               match( "at" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:21:26: 'um'
            {
               match( "um" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:23:11: ( 'on' | 'am' | 'an' )
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
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt6 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:23:13: 'on'
            {
               match( "on" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:23:20: 'am'
            {
               match( "am" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:23:27: 'an'
            {
               match( "an" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:25:11: ( 'in' | 'im' )
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
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:25:13: 'in'
            {
               match( "in" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:25:20: 'im'
            {
               match( "im" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:27:11: ( 'of' | 'des' )
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
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  8,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt8 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:27:13: 'of'
            {
               match( "of" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:27:20: 'des'
            {
               match( "des" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:29:11: ( 'next' | 'nächste' (
         // 's' | 'r' )? )
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
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:29:13: 'next'
            {
               match( "next" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:29:22: 'nächste' ( 's' |
               // 'r' )?
            {
               match( "nächste" );
               
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:29:31: ( 's' | 'r' )?
               int alt9 = 2;
               int LA9_0 = input.LA( 1 );
               
               if ( ( ( LA9_0 >= 'r' && LA9_0 <= 's' ) ) )
               {
                  alt9 = 1;
               }
               switch ( alt9 )
               {
                  case 1:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:
                  {
                     if ( ( input.LA( 1 ) >= 'r' && input.LA( 1 ) <= 's' ) )
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:31:11: ( 'and' | 'und' )
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
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  11,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt11 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:31:13: 'and'
            {
               match( "and" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:31:21: 'und'
            {
               match( "und" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:33:11: ( 'end' | 'ende' )
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
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:33:13: 'end'
            {
               match( "end" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:33:21: 'ende'
            {
               match( "ende" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:35:11: ( 'the' | 'd' ( 'er' |
         // 'ie' | 'as' ) )
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
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  14,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt14 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:35:13: 'the'
            {
               match( "the" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:35:21: 'd' ( 'er' | 'ie'
               // | 'as' )
            {
               match( 'd' );
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:35:24: ( 'er' | 'ie' |
               // 'as' )
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
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           13,
                                                                           0,
                                                                           input );
                     
                     throw nvae;
               }
               
               switch ( alt13 )
               {
                  case 1:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:35:25: 'er'
                  {
                     match( "er" );
                     
                  }
                     break;
                  case 2:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:35:30: 'ie'
                  {
                     match( "ie" );
                     
                  }
                     break;
                  case 3:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:35:35: 'as'
                  {
                     match( "as" );
                     
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:37:11: ( 'st' | 'th' | 'rd' |
         // 'nd' )
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
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     15,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt15 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:37:13: 'st'
            {
               match( "st" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:37:20: 'th'
            {
               match( "th" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:37:27: 'rd'
            {
               match( "rd" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:37:34: 'nd'
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:39:11: ( 'now' | 'jetzt' )
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
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  16,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt16 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:39:13: 'now'
            {
               match( "now" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:39:21: 'jetzt'
            {
               match( "jetzt" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:41:11: ( 'tonight' | 'ton' |
         // 'heute' )
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
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        17,
                                                                        3,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
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
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  17,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt17 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:41:13: 'tonight'
            {
               match( "tonight" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:41:25: 'ton'
            {
               match( "ton" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:41:33: 'heute'
            {
               match( "heute" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:43:11: ( 'midnight' |
         // 'mitternacht' )
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
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        18,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     18,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
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
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:43:13: 'midnight'
            {
               match( "midnight" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:43:26: 'mitternacht'
            {
               match( "mitternacht" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:45:11: ( 'midday' | 'mittag' )
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
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        19,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     19,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  19,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt19 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:45:13: 'midday'
            {
               match( "midday" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:45:24: 'mittag'
            {
               match( "mittag" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:47:11: ( 'noon' | 'mittag' )
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
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  20,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt20 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:47:13: 'noon'
            {
               match( "noon" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:47:22: 'mittag'
            {
               match( "mittag" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:11: ( 'years' | 'year' |
         // 'yrs' | 'yr' | 'jahr' | 'jahre' )
         int alt21 = 6;
         alt21 = dfa21.predict( input );
         switch ( alt21 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:13: 'years'
            {
               match( "years" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:23: 'year'
            {
               match( "year" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:32: 'yrs'
            {
               match( "yrs" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:40: 'yr'
            {
               match( "yr" );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:47: 'jahr'
            {
               match( "jahr" );
               
            }
               break;
            case 6:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:49:56: 'jahre'
            {
               match( "jahre" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:11: ( 'months' | 'month' |
         // 'mons' | 'mon' | 'monat' | 'monate' )
         int alt22 = 6;
         alt22 = dfa22.predict( input );
         switch ( alt22 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:13: 'months'
            {
               match( "months" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:24: 'month'
            {
               match( "month" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:34: 'mons'
            {
               match( "mons" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:43: 'mon'
            {
               match( "mon" );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:51: 'monat'
            {
               match( "monat" );
               
            }
               break;
            case 6:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:51:61: 'monate'
            {
               match( "monate" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:11: ( 'weeks' | 'week' |
         // 'wks' | 'wk' | 'woche' | 'wochen' )
         int alt23 = 6;
         alt23 = dfa23.predict( input );
         switch ( alt23 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:13: 'weeks'
            {
               match( "weeks" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:23: 'week'
            {
               match( "week" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:32: 'wks'
            {
               match( "wks" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:40: 'wk'
            {
               match( "wk" );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:47: 'woche'
            {
               match( "woche" );
               
            }
               break;
            case 6:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:53:57: 'wochen'
            {
               match( "wochen" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:55:11: ( 'days' | 'day' | 'd' |
         // 'tag' | 'tage' )
         int alt24 = 5;
         alt24 = dfa24.predict( input );
         switch ( alt24 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:55:13: 'days'
            {
               match( "days" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:55:22: 'day'
            {
               match( "day" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:55:30: 'd'
            {
               match( 'd' );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:55:36: 'tag'
            {
               match( "tag" );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:55:44: 'tage'
            {
               match( "tage" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:11: ( 'hours' | 'hour' |
         // 'hrs' | 'hr' | 'h' | 'stunden' | 'std' )
         int alt25 = 7;
         alt25 = dfa25.predict( input );
         switch ( alt25 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:13: 'hours'
            {
               match( "hours" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:23: 'hour'
            {
               match( "hour" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:32: 'hrs'
            {
               match( "hrs" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:40: 'hr'
            {
               match( "hr" );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:47: 'h'
            {
               match( 'h' );
               
            }
               break;
            case 6:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:53: 'stunden'
            {
               match( "stunden" );
               
            }
               break;
            case 7:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:57:65: 'std'
            {
               match( "std" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:11: ( 'minutes' | 'minute' |
         // 'mins' | 'min' | 'm' | 'minuten' )
         int alt26 = 6;
         alt26 = dfa26.predict( input );
         switch ( alt26 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:13: 'minutes'
            {
               match( "minutes" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:25: 'minute'
            {
               match( "minute" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:36: 'mins'
            {
               match( "mins" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:45: 'min'
            {
               match( "min" );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:53: 'm'
            {
               match( 'm' );
               
            }
               break;
            case 6:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:59:59: 'minuten'
            {
               match( "minuten" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:11: ( 'seconds' | 'second' |
         // 'secs' | 'sec' | 's' | 'sekunde' | 'sekunden' )
         int alt27 = 7;
         alt27 = dfa27.predict( input );
         switch ( alt27 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:13: 'seconds'
            {
               match( "seconds" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:25: 'second'
            {
               match( "second" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:36: 'secs'
            {
               match( "secs" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:45: 'sec'
            {
               match( "sec" );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:53: 's'
            {
               match( 's' );
               
            }
               break;
            case 6:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:59: 'sekunde'
            {
               match( "sekunde" );
               
            }
               break;
            case 7:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:61:71: 'sekunden'
            {
               match( "sekunden" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:11: ( 'january' | 'jan' |
         // 'januar' | 'february' | 'feb' | 'februar' | 'march' | 'mar' | 'märz' | 'marz' | 'april' | 'apr' | 'may' |
         // 'mai' | 'june' | 'jun' | 'juni' | 'july' | 'jul' | 'juli' | 'august' | 'aug' | 'september' | 'sept' | 'sep'
         // | 'october' | 'oct' | 'oktober' | 'okt' | 'november' | 'nov' | 'december' | 'dec' | 'dezember' | 'dez' )
         int alt28 = 35;
         alt28 = dfa28.predict( input );
         switch ( alt28 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:13: 'january'
            {
               match( "january" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:27: 'jan'
            {
               match( "jan" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:63:36: 'januar'
            {
               match( "januar" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:13: 'february'
            {
               match( "february" );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:27: 'feb'
            {
               match( "feb" );
               
            }
               break;
            case 6:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:64:36: 'februar'
            {
               match( "februar" );
               
            }
               break;
            case 7:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:13: 'march'
            {
               match( "march" );
               
            }
               break;
            case 8:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:27: 'mar'
            {
               match( "mar" );
               
            }
               break;
            case 9:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:36: 'märz'
            {
               match( "märz" );
               
            }
               break;
            case 10:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:65:49: 'marz'
            {
               match( "marz" );
               
            }
               break;
            case 11:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:66:13: 'april'
            {
               match( "april" );
               
            }
               break;
            case 12:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:66:27: 'apr'
            {
               match( "apr" );
               
            }
               break;
            case 13:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:67:13: 'may'
            {
               match( "may" );
               
            }
               break;
            case 14:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:67:27: 'mai'
            {
               match( "mai" );
               
            }
               break;
            case 15:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:13: 'june'
            {
               match( "june" );
               
            }
               break;
            case 16:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:27: 'jun'
            {
               match( "jun" );
               
            }
               break;
            case 17:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:68:36: 'juni'
            {
               match( "juni" );
               
            }
               break;
            case 18:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:13: 'july'
            {
               match( "july" );
               
            }
               break;
            case 19:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:27: 'jul'
            {
               match( "jul" );
               
            }
               break;
            case 20:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:69:36: 'juli'
            {
               match( "juli" );
               
            }
               break;
            case 21:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:70:13: 'august'
            {
               match( "august" );
               
            }
               break;
            case 22:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:70:27: 'aug'
            {
               match( "aug" );
               
            }
               break;
            case 23:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:71:13: 'september'
            {
               match( "september" );
               
            }
               break;
            case 24:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:71:27: 'sept'
            {
               match( "sept" );
               
            }
               break;
            case 25:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:71:36: 'sep'
            {
               match( "sep" );
               
            }
               break;
            case 26:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:72:13: 'october'
            {
               match( "october" );
               
            }
               break;
            case 27:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:72:27: 'oct'
            {
               match( "oct" );
               
            }
               break;
            case 28:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:72:36: 'oktober'
            {
               match( "oktober" );
               
            }
               break;
            case 29:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:72:49: 'okt'
            {
               match( "okt" );
               
            }
               break;
            case 30:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:73:13: 'november'
            {
               match( "november" );
               
            }
               break;
            case 31:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:73:27: 'nov'
            {
               match( "nov" );
               
            }
               break;
            case 32:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:74:13: 'december'
            {
               match( "december" );
               
            }
               break;
            case 33:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:74:27: 'dec'
            {
               match( "dec" );
               
            }
               break;
            case 34:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:74:36: 'dezember'
            {
               match( "dezember" );
               
            }
               break;
            case 35:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:74:49: 'dez'
            {
               match( "dez" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:76:11: ( 'monday' | 'mon' |
         // 'montag' | 'mo' 'tuesday' | 'tue' | 'dienstag' | 'di' 'wednesday' | 'wed' | 'mittwoch' | 'mi' 'thursday' |
         // 'thu' | 'donnerstag' | 'do' 'friday' | 'fri' | 'freitag' | 'fr' 'saturday' | 'sat' | 'samstag' | 'sa'
         // 'sunday' | 'sun' | 'sonntag' | 'so' )
         int alt29 = 22;
         alt29 = dfa29.predict( input );
         switch ( alt29 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:76:13: 'monday'
            {
               match( "monday" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:76:27: 'mon'
            {
               match( "mon" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:76:35: 'montag'
            {
               match( "montag" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:76:50: 'mo' 'tuesday'
            {
               match( "mo" );
               
               match( "tuesday" );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:77:27: 'tue'
            {
               match( "tue" );
               
            }
               break;
            case 6:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:77:35: 'dienstag'
            {
               match( "dienstag" );
               
            }
               break;
            case 7:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:77:50: 'di' 'wednesday'
            {
               match( "di" );
               
               match( "wednesday" );
               
            }
               break;
            case 8:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:78:27: 'wed'
            {
               match( "wed" );
               
            }
               break;
            case 9:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:78:35: 'mittwoch'
            {
               match( "mittwoch" );
               
            }
               break;
            case 10:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:78:50: 'mi' 'thursday'
            {
               match( "mi" );
               
               match( "thursday" );
               
            }
               break;
            case 11:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:79:27: 'thu'
            {
               match( "thu" );
               
            }
               break;
            case 12:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:79:35: 'donnerstag'
            {
               match( "donnerstag" );
               
            }
               break;
            case 13:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:79:50: 'do' 'friday'
            {
               match( "do" );
               
               match( "friday" );
               
            }
               break;
            case 14:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:80:27: 'fri'
            {
               match( "fri" );
               
            }
               break;
            case 15:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:80:35: 'freitag'
            {
               match( "freitag" );
               
            }
               break;
            case 16:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:80:50: 'fr' 'saturday'
            {
               match( "fr" );
               
               match( "saturday" );
               
            }
               break;
            case 17:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:81:27: 'sat'
            {
               match( "sat" );
               
            }
               break;
            case 18:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:81:35: 'samstag'
            {
               match( "samstag" );
               
            }
               break;
            case 19:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:81:50: 'sa' 'sunday'
            {
               match( "sa" );
               
               match( "sunday" );
               
            }
               break;
            case 20:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:82:27: 'sun'
            {
               match( "sun" );
               
            }
               break;
            case 21:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:82:35: 'sonntag'
            {
               match( "sonntag" );
               
            }
               break;
            case 22:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:82:50: 'so'
            {
               match( "so" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:84:11: ( '/' | '\\u5E74' |
         // '\\u6708' | '\\u65E5' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:86:11: ( '.' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:86:13: '.'
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:88:11: ( ':' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:88:13: ':'
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:90:11: ( '-' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:90:13: '-'
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:11: ( '-a' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:92:13: '-a'
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:94:11: ( ',' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:94:13: ','
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:96:11: ( ( '0' .. '9' )+ )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:96:13: ( '0' .. '9' )+
         {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:96:13: ( '0' .. '9' )+
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
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:96:13: '0' .. '9'
                  {
                     matchRange( '0', '9' );
                     
                  }
                     break;
                  
                  default :
                     if ( cnt30 >= 1 )
                        break loop30;
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:98:11: ( 'a' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:98:13: 'a'
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:100:11: ( A ( 'm' )? |
         // '\\u4E0A' | '\\u5348\\u524D' | '\\uC624\\uC804' )
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
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     32,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt32 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:100:14: A ( 'm' )?
            {
               mA();
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:100:15: ( 'm' )?
               int alt31 = 2;
               int LA31_0 = input.LA( 1 );
               
               if ( ( LA31_0 == 'm' ) )
               {
                  alt31 = 1;
               }
               switch ( alt31 )
               {
                  case 1:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:100:16: 'm'
                  {
                     match( 'm' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:100:24: '\\u4E0A'
            {
               match( '\u4E0A' );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:100:35: '\\u5348\\u524D'
            {
               match( "\u5348\u524D" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:100:52: '\\uC624\\uC804'
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:102:11: ( 'p' ( 'm' )? |
         // '\\u4E0B' | '\\u5348\\u5F8C' | '\\uC624\\uD6C4' )
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
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     34,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt34 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:102:13: 'p' ( 'm' )?
            {
               match( 'p' );
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:102:16: ( 'm' )?
               int alt33 = 2;
               int LA33_0 = input.LA( 1 );
               
               if ( ( LA33_0 == 'm' ) )
               {
                  alt33 = 1;
               }
               switch ( alt33 )
               {
                  case 1:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:102:17: 'm'
                  {
                     match( 'm' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:102:25: '\\u4E0B'
            {
               match( '\u4E0B' );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:102:36: '\\u5348\\u5F8C'
            {
               match( "\u5348\u5F8C" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:102:53: '\\uC624\\uD6C4'
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:11: ( 'one' | 'two' |
         // 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' | 'eins' | 'zwei' | 'drei' | 'vier' |
         // 'fünf' | 'funf' | 'sechs' | 'sieben' | 'acht' | 'neun' | 'zehn' )
         int alt35 = 21;
         alt35 = dfa35.predict( input );
         switch ( alt35 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:13: 'one'
            {
               match( "one" );
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:22: 'two'
            {
               match( "two" );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:31: 'three'
            {
               match( "three" );
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:41: 'four'
            {
               match( "four" );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:50: 'five'
            {
               match( "five" );
               
            }
               break;
            case 6:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:68: 'six'
            {
               match( "six" );
               
            }
               break;
            case 7:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:78: 'seven'
            {
               match( "seven" );
               
            }
               break;
            case 8:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:89: 'eight'
            {
               match( "eight" );
               
            }
               break;
            case 9:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:99: 'nine'
            {
               match( "nine" );
               
            }
               break;
            case 10:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:104:108: 'ten'
            {
               match( "ten" );
               
            }
               break;
            case 11:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:13: 'eins'
            {
               match( "eins" );
               
            }
               break;
            case 12:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:22: 'zwei'
            {
               match( "zwei" );
               
            }
               break;
            case 13:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:31: 'drei'
            {
               match( "drei" );
               
            }
               break;
            case 14:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:41: 'vier'
            {
               match( "vier" );
               
            }
               break;
            case 15:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:50: 'fünf'
            {
               match( "fünf" );
               
            }
               break;
            case 16:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:59: 'funf'
            {
               match( "funf" );
               
            }
               break;
            case 17:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:68: 'sechs'
            {
               match( "sechs" );
               
            }
               break;
            case 18:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:78: 'sieben'
            {
               match( "sieben" );
               
            }
               break;
            case 19:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:89: 'acht'
            {
               match( "acht" );
               
            }
               break;
            case 20:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:99: 'neun'
            {
               match( "neun" );
               
            }
               break;
            case 21:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:105:108: 'zehn'
            {
               match( "zehn" );
               
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:108:11: ( (~ ( '\"' | ' ' | '('
         // | ')' ) )+ )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:108:13: (~ ( '\"' | ' ' | '(' |
         // ')' ) )+
         {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:108:13: (~ ( '\"' | ' ' |
            // '(' | ')' ) )+
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
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:108:13: ~ ( '\"' |
                     // ' ' | '(' | ')' )
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
                     if ( cnt36 >= 1 )
                        break loop36;
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:110:11: ( ( ' ' | '\\t' | '\\r'
         // | '\\n' ) )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:110:13: ( ' ' | '\\t' | '\\r' |
         // '\\n' )
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
   
   @Override
   public void mTokens() throws RecognitionException
   {
      // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:8: ( NEVER | TODAY | TOMORROW |
      // YESTERDAY | AT | ON | IN | OF | NEXT | AND | END | THE | STs | NOW | TONIGHT | MIDNIGHT | MIDDAY | NOON | YEARS
      // | MONTHS | WEEKS | DAYS | HOURS | MINUTES | SECONDS | MONTH | WEEKDAY | DATE_SEP | DOT | COLON | MINUS |
      // MINUS_A | COMMA | INT | A | AM | PM | NUM_STR | WS )
      int alt37 = 39;
      alt37 = dfa37.predict( input );
      switch ( alt37 )
      {
         case 1:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:10: NEVER
         {
            mNEVER();
            
         }
            break;
         case 2:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:16: TODAY
         {
            mTODAY();
            
         }
            break;
         case 3:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:22: TOMORROW
         {
            mTOMORROW();
            
         }
            break;
         case 4:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:31: YESTERDAY
         {
            mYESTERDAY();
            
         }
            break;
         case 5:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:41: AT
         {
            mAT();
            
         }
            break;
         case 6:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:44: ON
         {
            mON();
            
         }
            break;
         case 7:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:47: IN
         {
            mIN();
            
         }
            break;
         case 8:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:50: OF
         {
            mOF();
            
         }
            break;
         case 9:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:53: NEXT
         {
            mNEXT();
            
         }
            break;
         case 10:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:58: AND
         {
            mAND();
            
         }
            break;
         case 11:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:62: END
         {
            mEND();
            
         }
            break;
         case 12:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:66: THE
         {
            mTHE();
            
         }
            break;
         case 13:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:70: STs
         {
            mSTs();
            
         }
            break;
         case 14:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:74: NOW
         {
            mNOW();
            
         }
            break;
         case 15:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:78: TONIGHT
         {
            mTONIGHT();
            
         }
            break;
         case 16:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:86: MIDNIGHT
         {
            mMIDNIGHT();
            
         }
            break;
         case 17:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:95: MIDDAY
         {
            mMIDDAY();
            
         }
            break;
         case 18:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:102: NOON
         {
            mNOON();
            
         }
            break;
         case 19:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:107: YEARS
         {
            mYEARS();
            
         }
            break;
         case 20:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:113: MONTHS
         {
            mMONTHS();
            
         }
            break;
         case 21:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:120: WEEKS
         {
            mWEEKS();
            
         }
            break;
         case 22:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:126: DAYS
         {
            mDAYS();
            
         }
            break;
         case 23:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:131: HOURS
         {
            mHOURS();
            
         }
            break;
         case 24:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:137: MINUTES
         {
            mMINUTES();
            
         }
            break;
         case 25:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:145: SECONDS
         {
            mSECONDS();
            
         }
            break;
         case 26:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:153: MONTH
         {
            mMONTH();
            
         }
            break;
         case 27:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:159: WEEKDAY
         {
            mWEEKDAY();
            
         }
            break;
         case 28:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:167: DATE_SEP
         {
            mDATE_SEP();
            
         }
            break;
         case 29:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:176: DOT
         {
            mDOT();
            
         }
            break;
         case 30:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:180: COLON
         {
            mCOLON();
            
         }
            break;
         case 31:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:186: MINUS
         {
            mMINUS();
            
         }
            break;
         case 32:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:192: MINUS_A
         {
            mMINUS_A();
            
         }
            break;
         case 33:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:200: COMMA
         {
            mCOMMA();
            
         }
            break;
         case 34:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:206: INT
         {
            mINT();
            
         }
            break;
         case 35:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:210: A
         {
            mA();
            
         }
            break;
         case 36:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:212: AM
         {
            mAM();
            
         }
            break;
         case 37:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:215: PM
         {
            mPM();
            
         }
            break;
         case 38:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:218: NUM_STR
         {
            mNUM_STR();
            
         }
            break;
         case 39:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeLexer.g:1:226: WS
         {
            mWS();
            
         }
            break;
         
      }
      
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
      


      @Override
      public String getDescription()
      {
         return "49:1: YEARS : ( 'years' | 'year' | 'yrs' | 'yr' | 'jahr' | 'jahre' );";
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
      


      @Override
      public String getDescription()
      {
         return "51:1: MONTHS : ( 'months' | 'month' | 'mons' | 'mon' | 'monat' | 'monate' );";
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
      


      @Override
      public String getDescription()
      {
         return "53:1: WEEKS : ( 'weeks' | 'week' | 'wks' | 'wk' | 'woche' | 'wochen' );";
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
      


      @Override
      public String getDescription()
      {
         return "55:1: DAYS : ( 'days' | 'day' | 'd' | 'tag' | 'tage' );";
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
      


      @Override
      public String getDescription()
      {
         return "57:1: HOURS : ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' | 'stunden' | 'std' );";
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
      


      @Override
      public String getDescription()
      {
         return "59:1: MINUTES : ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' | 'minuten' );";
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
      


      @Override
      public String getDescription()
      {
         return "61:1: SECONDS : ( 'seconds' | 'second' | 'secs' | 'sec' | 's' | 'sekunde' | 'sekunden' );";
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
      


      @Override
      public String getDescription()
      {
         return "63:1: MONTH : ( 'january' | 'jan' | 'januar' | 'february' | 'feb' | 'februar' | 'march' | 'mar' | 'märz' | 'marz' | 'april' | 'apr' | 'may' | 'mai' | 'june' | 'jun' | 'juni' | 'july' | 'jul' | 'juli' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'oktober' | 'okt' | 'november' | 'nov' | 'december' | 'dec' | 'dezember' | 'dez' );";
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
      


      @Override
      public String getDescription()
      {
         return "76:1: WEEKDAY : ( 'monday' | 'mon' | 'montag' | 'mo' 'tuesday' | 'tue' | 'dienstag' | 'di' 'wednesday' | 'wed' | 'mittwoch' | 'mi' 'thursday' | 'thu' | 'donnerstag' | 'do' 'friday' | 'fri' | 'freitag' | 'fr' 'saturday' | 'sat' | 'samstag' | 'sa' 'sunday' | 'sun' | 'sonntag' | 'so' );";
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
      


      @Override
      public String getDescription()
      {
         return "104:1: NUM_STR : ( 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' | 'eins' | 'zwei' | 'drei' | 'vier' | 'fünf' | 'funf' | 'sechs' | 'sieben' | 'acht' | 'neun' | 'zehn' );";
      }
   }
   
   static final String DFA37_eotS = "\3\uffff\1\51\1\55\3\uffff\1\62\3\uffff\1\46\1\uffff\1\74\7\uffff"
      + "\1\102\16\uffff\1\17\13\uffff\1\114\2\uffff\1\114\5\uffff\1\17\16"
      + "\uffff\1\121\3\uffff\1\107\1\74\11\uffff";
   
   static final String DFA37_eofS = "\130\uffff";
   
   static final String DFA37_minS = "\1\11\1\144\1\141\1\145\1\141\1\145\2\uffff\1\143\1\155\1\143\1"
      + "\uffff\1\141\1\151\1\141\1\uffff\1\141\2\145\3\uffff\1\141\3\uffff"
      + "\1\u524d\1\uc804\3\uffff\1\165\1\145\1\uffff\1\157\1\144\1\uffff"
      + "\1\145\2\uffff\1\165\1\uffff\1\156\1\144\2\uffff\1\141\2\uffff\1"
      + "\144\2\uffff\1\145\1\uffff\1\143\1\163\1\145\1\uffff\1\144\1\143"
      + "\2\uffff\1\150\1\144\10\uffff\1\164\2\144\1\150\1\uffff\1\156\1"
      + "\150\1\145\1\141\3\uffff\1\141\1\uffff\1\147\1\uffff";
   
   static final String DFA37_maxS = "\1\uc624\1\u00e4\1\167\1\145\1\u00e4\1\162\2\uffff\1\165\2\156"
      + "\1\uffff\1\162\1\156\1\165\1\uffff\1\165\1\157\1\u00fc\3\uffff\1"
      + "\141\3\uffff\1\u5f8c\1\ud6c4\3\uffff\1\170\1\156\1\uffff\1\167\1"
      + "\156\1\uffff\1\165\2\uffff\1\165\1\uffff\2\164\2\uffff\1\163\2\uffff"
      + "\1\144\2\uffff\1\145\1\uffff\1\172\1\171\1\167\1\uffff\1\165\1\166"
      + "\2\uffff\1\156\1\145\10\uffff\2\164\1\156\1\164\1\uffff\1\156\1"
      + "\150\1\145\1\150\3\uffff\1\167\1\uffff\1\147\1\uffff";
   
   static final String DFA37_acceptS = "\6\uffff\1\4\1\5\3\uffff\1\7\3\uffff\1\15\3\uffff\1\34\1\35\1\36"
      + "\1\uffff\1\41\1\42\1\44\2\uffff\1\45\1\46\1\47\2\uffff\1\11\2\uffff"
      + "\1\3\1\uffff\1\26\1\33\1\uffff\1\27\2\uffff\1\32\1\30\1\uffff\1"
      + "\23\1\6\1\uffff\1\43\1\12\1\uffff\1\10\3\uffff\1\13\2\uffff\1\31"
      + "\1\16\2\uffff\1\25\1\40\1\37\1\1\1\22\1\2\1\17\1\14\4\uffff\1\6"
      + "\4\uffff\1\24\1\20\1\21\1\uffff\1\2\1\uffff\1\21";
   
   static final String DFA37_specialS = "\130\uffff}>";
   
   static final String[] DFA37_transitionS =
   {
    "\2\36\2\uffff\1\36\22\uffff\1\36\13\uffff\1\27\1\26\1\24\1"
       + "\23\12\30\1\25\5\uffff\1\7\40\uffff\1\10\2\uffff\1\14\1\15\1"
       + "\22\1\6\1\3\1\13\1\20\2\uffff\1\4\1\1\1\12\1\34\1\uffff\1\17"
       + "\1\16\1\2\1\11\1\35\1\21\1\uffff\1\5\1\35\u4d8f\uffff\1\31\1"
       + "\34\u053c\uffff\1\32\u0b2b\uffff\1\23\u0770\uffff\1\23\u0122"
       + "\uffff\1\23\u5f1b\uffff\1\33",
    "\1\17\1\37\3\uffff\1\40\5\uffff\1\42\164\uffff\1\41",
    "\1\46\3\uffff\1\35\2\uffff\1\45\4\uffff\1\44\1\uffff\1\43"
       + "\5\uffff\1\47\1\uffff\1\35",
    "\1\50",
    "\1\54\7\uffff\1\53\5\uffff\1\52\164\uffff\1\54",
    "\1\56\14\uffff\1\57",
    "",
    "",
    "\1\35\11\uffff\1\60\1\61\1\uffff\1\54\3\uffff\1\7\1\54",
    "\1\7\1\63",
    "\1\54\2\uffff\1\65\4\uffff\1\54\2\uffff\1\64",
    "",
    "\1\67\3\uffff\1\66\3\uffff\1\70\5\uffff\1\47\2\uffff\1\35",
    "\1\35\4\uffff\1\71",
    "\1\47\3\uffff\1\73\3\uffff\1\35\5\uffff\1\47\4\uffff\1\72" + "\1\47",
    "",
    "\1\76\3\uffff\1\75\17\uffff\1\54",
    "\1\77\5\uffff\1\100\3\uffff\1\100",
    "\1\54\3\uffff\1\35\5\uffff\1\35\2\uffff\1\47\2\uffff\1\35"
       + "\u0086\uffff\1\35", "", "", "", "\1\101", "", "", "",
    "\1\31\u0d3e\uffff\1\34", "\1\31\u0ebf\uffff\1\34", "", "", "",
    "\1\35\1\103\1\uffff\1\41", "\1\103\10\uffff\1\35", "",
    "\1\104\6\uffff\1\54\1\75", "\1\105\10\uffff\1\44\1\106", "",
    "\1\107\14\uffff\1\35\2\uffff\1\47", "", "", "\1\110", "",
    "\1\111\3\uffff\1\44\1\uffff\1\47", "\1\112\11\uffff\1\55\5\uffff\1\113",
    "", "", "\1\57\21\uffff\1\6", "", "", "\1\63", "", "", "\1\35", "",
    "\1\54\16\uffff\1\107\1\65\6\uffff\1\54", "\1\107\5\uffff\1\46",
    "\1\115\21\uffff\1\47", "", "\1\51\20\uffff\1\51",
    "\1\116\7\uffff\1\74\4\uffff\1\54\5\uffff\1\35", "", "",
    "\1\57\5\uffff\1\54", "\1\47\1\100", "", "", "", "", "", "", "", "",
    "\1\117", "\1\47\17\uffff\1\120", "\1\123\11\uffff\1\122",
    "\1\47\13\uffff\1\124", "", "\1\47", "\1\35", "\1\125",
    "\1\47\6\uffff\1\121", "", "", "", "\1\126\3\uffff\1\122\21\uffff\1\47",
    "", "\1\127", "" };
   
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
      


      @Override
      public String getDescription()
      {
         return "1:1: Tokens : ( NEVER | TODAY | TOMORROW | YESTERDAY | AT | ON | IN | OF | NEXT | AND | END | THE | STs | NOW | TONIGHT | MIDNIGHT | MIDDAY | NOON | YEARS | MONTHS | WEEKS | DAYS | HOURS | MINUTES | SECONDS | MONTH | WEEKDAY | DATE_SEP | DOT | COLON | MINUS | MINUS_A | COMMA | INT | A | AM | PM | NUM_STR | WS );";
      }
   }
   
}
