// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g 2013-03-22 13:25:28

package dev.drsoran.moloko.grammar.recurrence.de;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class RecurrenceLexer extends Lexer
{
   public static final int EOF = -1;
   
   public static final int AFTER = 4;
   
   public static final int AND = 5;
   
   public static final int COMMA = 6;
   
   public static final int DAYS = 7;
   
   public static final int DOT = 8;
   
   public static final int EVERY = 9;
   
   public static final int FIFTH = 10;
   
   public static final int FIRST = 11;
   
   public static final int FOR = 12;
   
   public static final int FOURTH = 13;
   
   public static final int FRIDAY = 14;
   
   public static final int INT = 15;
   
   public static final int LAST = 16;
   
   public static final int MINUS = 17;
   
   public static final int MONDAY = 18;
   
   public static final int MONTH = 19;
   
   public static final int MONTHS = 20;
   
   public static final int NUMBER = 21;
   
   public static final int NUM_EIGHT = 22;
   
   public static final int NUM_FIVE = 23;
   
   public static final int NUM_FOUR = 24;
   
   public static final int NUM_NINE = 25;
   
   public static final int NUM_ONE = 26;
   
   public static final int NUM_SEVEN = 27;
   
   public static final int NUM_SIX = 28;
   
   public static final int NUM_TEN = 29;
   
   public static final int NUM_THREE = 30;
   
   public static final int NUM_TWO = 31;
   
   public static final int ON = 32;
   
   public static final int OTHER = 33;
   
   public static final int SATURDAY = 34;
   
   public static final int SECOND = 35;
   
   public static final int STRING = 36;
   
   public static final int SUNDAY = 37;
   
   public static final int THIRD = 38;
   
   public static final int THURSDAY = 39;
   
   public static final int TIMES = 40;
   
   public static final int TUESDAY = 41;
   
   public static final int UNTIL = 42;
   
   public static final int WEDNESDAY = 43;
   
   public static final int WEEKDAY_LIT = 44;
   
   public static final int WEEKEND = 45;
   
   public static final int WEEKS = 46;
   
   public static final int WS = 47;
   
   public static final int YEARS = 48;
   
   
   
   @Override
   public void reportError( RecognitionException e )
   {
      // throw new LexerException( e );
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
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g";
   }
   
   
   
   // $ANTLR start "EVERY"
   public final void mEVERY() throws RecognitionException
   {
      try
      {
         int _type = EVERY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:269:15:
         // ( 'jede' ( 's' | 'r' | 'n' )? | 'alle' )
         int alt2 = 2;
         int LA2_0 = input.LA( 1 );
         
         if ( ( LA2_0 == 'j' ) )
         {
            alt2 = 1;
         }
         else if ( ( LA2_0 == 'a' ) )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:269:17:
            // 'jede' ( 's' | 'r' | 'n' )?
            {
               match( "jede" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:269:23:
               // ( 's' | 'r' | 'n' )?
               int alt1 = 2;
               int LA1_0 = input.LA( 1 );
               
               if ( ( LA1_0 == 'n' || ( LA1_0 >= 'r' && LA1_0 <= 's' ) ) )
               {
                  alt1 = 1;
               }
               switch ( alt1 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:
                  {
                     if ( input.LA( 1 ) == 'n'
                        || ( input.LA( 1 ) >= 'r' && input.LA( 1 ) <= 's' ) )
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
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:269:40:
            // 'alle'
            {
               match( "alle" );
               
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:271:15:
         // ( 'nach' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:271:17:
         // 'nach'
         {
            match( "nach" );
            
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
   
   // $ANTLR start "YEARS"
   public final void mYEARS() throws RecognitionException
   {
      try
      {
         int _type = YEARS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:273:15:
         // ( 'jahr' ( 'e' | 'en' )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:273:17:
         // 'jahr' ( 'e' | 'en' )?
         {
            match( "jahr" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:273:23:
            // ( 'e' | 'en' )?
            int alt3 = 3;
            int LA3_0 = input.LA( 1 );
            
            if ( ( LA3_0 == 'e' ) )
            {
               int LA3_1 = input.LA( 2 );
               
               if ( ( LA3_1 == 'n' ) )
               {
                  alt3 = 2;
               }
            }
            switch ( alt3 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:273:24:
               // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:273:28:
               // 'en'
               {
                  match( "en" );
                  
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
   
   
   
   // $ANTLR end "YEARS"
   
   // $ANTLR start "MONTHS"
   public final void mMONTHS() throws RecognitionException
   {
      try
      {
         int _type = MONTHS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:275:15:
         // ( 'monat' ( 'e' | 'en' )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:275:17:
         // 'monat' ( 'e' | 'en' )?
         {
            match( "monat" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:275:24:
            // ( 'e' | 'en' )?
            int alt4 = 3;
            int LA4_0 = input.LA( 1 );
            
            if ( ( LA4_0 == 'e' ) )
            {
               int LA4_1 = input.LA( 2 );
               
               if ( ( LA4_1 == 'n' ) )
               {
                  alt4 = 2;
               }
            }
            switch ( alt4 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:275:25:
               // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:275:29:
               // 'en'
               {
                  match( "en" );
                  
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
   
   
   
   // $ANTLR end "MONTHS"
   
   // $ANTLR start "WEEKS"
   public final void mWEEKS() throws RecognitionException
   {
      try
      {
         int _type = WEEKS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:277:15:
         // ( 'woche' ( 'n' )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:277:17:
         // 'woche' ( 'n' )?
         {
            match( "woche" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:277:24:
            // ( 'n' )?
            int alt5 = 2;
            int LA5_0 = input.LA( 1 );
            
            if ( ( LA5_0 == 'n' ) )
            {
               alt5 = 1;
            }
            switch ( alt5 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:277:24:
               // 'n'
               {
                  match( 'n' );
                  
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
   
   
   
   // $ANTLR end "WEEKS"
   
   // $ANTLR start "DAYS"
   public final void mDAYS() throws RecognitionException
   {
      try
      {
         int _type = DAYS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:279:15:
         // ( 'tag' ( 'e' | 'en' )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:279:17:
         // 'tag' ( 'e' | 'en' )?
         {
            match( "tag" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:279:22:
            // ( 'e' | 'en' )?
            int alt6 = 3;
            int LA6_0 = input.LA( 1 );
            
            if ( ( LA6_0 == 'e' ) )
            {
               int LA6_1 = input.LA( 2 );
               
               if ( ( LA6_1 == 'n' ) )
               {
                  alt6 = 2;
               }
            }
            switch ( alt6 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:279:23:
               // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:279:27:
               // 'en'
               {
                  match( "en" );
                  
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
   
   
   
   // $ANTLR end "DAYS"
   
   // $ANTLR start "MONTH"
   public final void mMONTH() throws RecognitionException
   {
      try
      {
         int _type = MONTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:281:15:
         // ( 'januar' | 'jan' | 'februar' | 'feb' | 'märz' | 'april' | 'apr' | 'mai' | 'juni' | 'jun' | 'juli' | 'jul'
         // | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'oktober' | 'okt' | 'november' | 'nov' | 'dezember' |
         // 'dez' )
         int alt7 = 23;
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
                     int LA7_20 = input.LA( 4 );
                     
                     if ( ( LA7_20 == 'u' ) )
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
                     int LA7_21 = input.LA( 4 );
                     
                     if ( ( LA7_21 == 'i' ) )
                     {
                        alt7 = 9;
                     }
                     else
                     {
                        alt7 = 10;
                     }
                  }
                  else if ( ( LA7_10 == 'l' ) )
                  {
                     int LA7_22 = input.LA( 4 );
                     
                     if ( ( LA7_22 == 'i' ) )
                     {
                        alt7 = 11;
                     }
                     else
                     {
                        alt7 = 12;
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
                     int LA7_23 = input.LA( 4 );
                     
                     if ( ( LA7_23 == 'r' ) )
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
               
               if ( ( LA7_3 == '\u00E4' ) )
               {
                  alt7 = 5;
               }
               else if ( ( LA7_3 == 'a' ) )
               {
                  alt7 = 8;
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
                  int LA7_14 = input.LA( 3 );
                  
                  if ( ( LA7_14 == 'r' ) )
                  {
                     int LA7_24 = input.LA( 4 );
                     
                     if ( ( LA7_24 == 'i' ) )
                     {
                        alt7 = 6;
                     }
                     else
                     {
                        alt7 = 7;
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
               else if ( ( LA7_4 == 'u' ) )
               {
                  int LA7_15 = input.LA( 3 );
                  
                  if ( ( LA7_15 == 'g' ) )
                  {
                     int LA7_25 = input.LA( 4 );
                     
                     if ( ( LA7_25 == 'u' ) )
                     {
                        alt7 = 13;
                     }
                     else
                     {
                        alt7 = 14;
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
                  int LA7_16 = input.LA( 3 );
                  
                  if ( ( LA7_16 == 'p' ) )
                  {
                     int LA7_26 = input.LA( 4 );
                     
                     if ( ( LA7_26 == 't' ) )
                     {
                        int LA7_42 = input.LA( 5 );
                        
                        if ( ( LA7_42 == 'e' ) )
                        {
                           alt7 = 15;
                        }
                        else
                        {
                           alt7 = 16;
                        }
                     }
                     else
                     {
                        alt7 = 17;
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
                                                                        5,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'o':
            {
               int LA7_6 = input.LA( 2 );
               
               if ( ( LA7_6 == 'k' ) )
               {
                  int LA7_17 = input.LA( 3 );
                  
                  if ( ( LA7_17 == 't' ) )
                  {
                     int LA7_27 = input.LA( 4 );
                     
                     if ( ( LA7_27 == 'o' ) )
                     {
                        alt7 = 18;
                     }
                     else
                     {
                        alt7 = 19;
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
                  int LA7_18 = input.LA( 3 );
                  
                  if ( ( LA7_18 == 'v' ) )
                  {
                     int LA7_28 = input.LA( 4 );
                     
                     if ( ( LA7_28 == 'e' ) )
                     {
                        alt7 = 20;
                     }
                     else
                     {
                        alt7 = 21;
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
                  int LA7_19 = input.LA( 3 );
                  
                  if ( ( LA7_19 == 'z' ) )
                  {
                     int LA7_29 = input.LA( 4 );
                     
                     if ( ( LA7_29 == 'e' ) )
                     {
                        alt7 = 22;
                     }
                     else
                     {
                        alt7 = 23;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           7,
                                                                           19,
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:281:17:
            // 'januar'
            {
               match( "januar" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:281:31:
            // 'jan'
            {
               match( "jan" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:281:40:
            // 'februar'
            {
               match( "februar" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:281:53:
            // 'feb'
            {
               match( "feb" );
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:281:65:
            // 'märz'
            {
               match( "märz" );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:281:75:
            // 'april'
            {
               match( "april" );
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:281:88:
            // 'apr'
            {
               match( "apr" );
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:282:17:
            // 'mai'
            {
               match( "mai" );
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:282:31:
            // 'juni'
            {
               match( "juni" );
               
            }
               break;
            case 10:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:282:40:
            // 'jun'
            {
               match( "jun" );
               
            }
               break;
            case 11:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:282:53:
            // 'juli'
            {
               match( "juli" );
               
            }
               break;
            case 12:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:282:65:
            // 'jul'
            {
               match( "jul" );
               
            }
               break;
            case 13:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:282:75:
            // 'august'
            {
               match( "august" );
               
            }
               break;
            case 14:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:282:88:
            // 'aug'
            {
               match( "aug" );
               
            }
               break;
            case 15:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:283:17:
            // 'september'
            {
               match( "september" );
               
            }
               break;
            case 16:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:283:31:
            // 'sept'
            {
               match( "sept" );
               
            }
               break;
            case 17:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:283:40:
            // 'sep'
            {
               match( "sep" );
               
            }
               break;
            case 18:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:283:53:
            // 'oktober'
            {
               match( "oktober" );
               
            }
               break;
            case 19:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:283:65:
            // 'okt'
            {
               match( "okt" );
               
            }
               break;
            case 20:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:283:75:
            // 'november'
            {
               match( "november" );
               
            }
               break;
            case 21:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:283:88:
            // 'nov'
            {
               match( "nov" );
               
            }
               break;
            case 22:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:284:17:
            // 'dezember'
            {
               match( "dezember" );
               
            }
               break;
            case 23:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:284:31:
            // 'dez'
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:286:15:
         // ( 'wochentag' ( 's' )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:286:17:
         // 'wochentag' ( 's' )?
         {
            match( "wochentag" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:286:28:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:286:28:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:288:15:
         // ( 'wochenende' ( 'n' )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:288:17:
         // 'wochenende' ( 'n' )?
         {
            match( "wochenende" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:288:29:
            // ( 'n' )?
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == 'n' ) )
            {
               alt9 = 1;
            }
            switch ( alt9 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:288:29:
               // 'n'
               {
                  match( 'n' );
                  
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:290:15:
         // ( 'montag' ( 's' )? | 'mo' )
         int alt11 = 2;
         int LA11_0 = input.LA( 1 );
         
         if ( ( LA11_0 == 'm' ) )
         {
            int LA11_1 = input.LA( 2 );
            
            if ( ( LA11_1 == 'o' ) )
            {
               int LA11_2 = input.LA( 3 );
               
               if ( ( LA11_2 == 'n' ) )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:290:17:
            // 'montag' ( 's' )?
            {
               match( "montag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:290:25:
               // ( 's' )?
               int alt10 = 2;
               int LA10_0 = input.LA( 1 );
               
               if ( ( LA10_0 == 's' ) )
               {
                  alt10 = 1;
               }
               switch ( alt10 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:290:25:
                  // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:290:36:
            // 'mo'
            {
               match( "mo" );
               
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:292:15:
         // ( 'dienstag' ( 's' )? | 'di' )
         int alt13 = 2;
         int LA13_0 = input.LA( 1 );
         
         if ( ( LA13_0 == 'd' ) )
         {
            int LA13_1 = input.LA( 2 );
            
            if ( ( LA13_1 == 'i' ) )
            {
               int LA13_2 = input.LA( 3 );
               
               if ( ( LA13_2 == 'e' ) )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:292:17:
            // 'dienstag' ( 's' )?
            {
               match( "dienstag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:292:27:
               // ( 's' )?
               int alt12 = 2;
               int LA12_0 = input.LA( 1 );
               
               if ( ( LA12_0 == 's' ) )
               {
                  alt12 = 1;
               }
               switch ( alt12 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:292:27:
                  // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:292:36:
            // 'di'
            {
               match( "di" );
               
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:294:15:
         // ( 'mittwoch' ( 's' )? | 'mi' )
         int alt15 = 2;
         int LA15_0 = input.LA( 1 );
         
         if ( ( LA15_0 == 'm' ) )
         {
            int LA15_1 = input.LA( 2 );
            
            if ( ( LA15_1 == 'i' ) )
            {
               int LA15_2 = input.LA( 3 );
               
               if ( ( LA15_2 == 't' ) )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:294:17:
            // 'mittwoch' ( 's' )?
            {
               match( "mittwoch" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:294:27:
               // ( 's' )?
               int alt14 = 2;
               int LA14_0 = input.LA( 1 );
               
               if ( ( LA14_0 == 's' ) )
               {
                  alt14 = 1;
               }
               switch ( alt14 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:294:27:
                  // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:294:36:
            // 'mi'
            {
               match( "mi" );
               
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:296:15:
         // ( 'donnerstag' ( 's' )? | 'do' )
         int alt17 = 2;
         int LA17_0 = input.LA( 1 );
         
         if ( ( LA17_0 == 'd' ) )
         {
            int LA17_1 = input.LA( 2 );
            
            if ( ( LA17_1 == 'o' ) )
            {
               int LA17_2 = input.LA( 3 );
               
               if ( ( LA17_2 == 'n' ) )
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
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:296:17:
            // 'donnerstag' ( 's' )?
            {
               match( "donnerstag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:296:29:
               // ( 's' )?
               int alt16 = 2;
               int LA16_0 = input.LA( 1 );
               
               if ( ( LA16_0 == 's' ) )
               {
                  alt16 = 1;
               }
               switch ( alt16 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:296:29:
                  // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:296:36:
            // 'do'
            {
               match( "do" );
               
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:298:15:
         // ( 'freitag' ( 's' )? | 'fr' )
         int alt19 = 2;
         int LA19_0 = input.LA( 1 );
         
         if ( ( LA19_0 == 'f' ) )
         {
            int LA19_1 = input.LA( 2 );
            
            if ( ( LA19_1 == 'r' ) )
            {
               int LA19_2 = input.LA( 3 );
               
               if ( ( LA19_2 == 'e' ) )
               {
                  alt19 = 1;
               }
               else
               {
                  alt19 = 2;
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:298:17:
            // 'freitag' ( 's' )?
            {
               match( "freitag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:298:26:
               // ( 's' )?
               int alt18 = 2;
               int LA18_0 = input.LA( 1 );
               
               if ( ( LA18_0 == 's' ) )
               {
                  alt18 = 1;
               }
               switch ( alt18 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:298:26:
                  // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:298:36:
            // 'fr'
            {
               match( "fr" );
               
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:300:15:
         // ( 'samstag' ( 's' )? | 'sa' )
         int alt21 = 2;
         int LA21_0 = input.LA( 1 );
         
         if ( ( LA21_0 == 's' ) )
         {
            int LA21_1 = input.LA( 2 );
            
            if ( ( LA21_1 == 'a' ) )
            {
               int LA21_2 = input.LA( 3 );
               
               if ( ( LA21_2 == 'm' ) )
               {
                  alt21 = 1;
               }
               else
               {
                  alt21 = 2;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     21,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:300:17:
            // 'samstag' ( 's' )?
            {
               match( "samstag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:300:26:
               // ( 's' )?
               int alt20 = 2;
               int LA20_0 = input.LA( 1 );
               
               if ( ( LA20_0 == 's' ) )
               {
                  alt20 = 1;
               }
               switch ( alt20 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:300:26:
                  // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:300:36:
            // 'sa'
            {
               match( "sa" );
               
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:302:15:
         // ( 'sonntag' ( 's' )? | 'so' )
         int alt23 = 2;
         int LA23_0 = input.LA( 1 );
         
         if ( ( LA23_0 == 's' ) )
         {
            int LA23_1 = input.LA( 2 );
            
            if ( ( LA23_1 == 'o' ) )
            {
               int LA23_2 = input.LA( 3 );
               
               if ( ( LA23_2 == 'n' ) )
               {
                  alt23 = 1;
               }
               else
               {
                  alt23 = 2;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     23,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  23,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt23 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:302:17:
            // 'sonntag' ( 's' )?
            {
               match( "sonntag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:302:26:
               // ( 's' )?
               int alt22 = 2;
               int LA22_0 = input.LA( 1 );
               
               if ( ( LA22_0 == 's' ) )
               {
                  alt22 = 1;
               }
               switch ( alt22 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:302:26:
                  // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:302:36:
            // 'so'
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:304:15:
         // ( 'erst' ( 'e' | 's' | 'r' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:304:17:
         // 'erst' ( 'e' | 's' | 'r' )
         {
            match( "erst" );
            
            if ( input.LA( 1 ) == 'e'
               || ( input.LA( 1 ) >= 'r' && input.LA( 1 ) <= 's' ) )
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
   
   
   
   // $ANTLR end "FIRST"
   
   // $ANTLR start "SECOND"
   public final void mSECOND() throws RecognitionException
   {
      try
      {
         int _type = SECOND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:306:15:
         // ( 'zweit' ( 'e' | 's' | 'r' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:306:17:
         // 'zweit' ( 'e' | 's' | 'r' )
         {
            match( "zweit" );
            
            if ( input.LA( 1 ) == 'e'
               || ( input.LA( 1 ) >= 'r' && input.LA( 1 ) <= 's' ) )
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
   
   
   
   // $ANTLR end "SECOND"
   
   // $ANTLR start "THIRD"
   public final void mTHIRD() throws RecognitionException
   {
      try
      {
         int _type = THIRD;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:308:15:
         // ( 'dritt' ( 'e' | 's' | 'r' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:308:17:
         // 'dritt' ( 'e' | 's' | 'r' )
         {
            match( "dritt" );
            
            if ( input.LA( 1 ) == 'e'
               || ( input.LA( 1 ) >= 'r' && input.LA( 1 ) <= 's' ) )
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
   
   
   
   // $ANTLR end "THIRD"
   
   // $ANTLR start "FOURTH"
   public final void mFOURTH() throws RecognitionException
   {
      try
      {
         int _type = FOURTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:310:15:
         // ( 'viert' ( 'e' | 's' | 'r' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:310:17:
         // 'viert' ( 'e' | 's' | 'r' )
         {
            match( "viert" );
            
            if ( input.LA( 1 ) == 'e'
               || ( input.LA( 1 ) >= 'r' && input.LA( 1 ) <= 's' ) )
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
   
   
   
   // $ANTLR end "FOURTH"
   
   // $ANTLR start "FIFTH"
   public final void mFIFTH() throws RecognitionException
   {
      try
      {
         int _type = FIFTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:312:15:
         // ( 'fünft' ( 'e' | 's' | 'r' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:312:17:
         // 'fünft' ( 'e' | 's' | 'r' )
         {
            match( "fünft" );
            
            if ( input.LA( 1 ) == 'e'
               || ( input.LA( 1 ) >= 'r' && input.LA( 1 ) <= 's' ) )
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
   
   
   
   // $ANTLR end "FIFTH"
   
   // $ANTLR start "LAST"
   public final void mLAST() throws RecognitionException
   {
      try
      {
         int _type = LAST;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:314:15:
         // ( 'letzt' ( 'e' | 's' | 'r' | 'en' | 'em' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:314:17:
         // 'letzt' ( 'e' | 's' | 'r' | 'en' | 'em' )
         {
            match( "letzt" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:314:24:
            // ( 'e' | 's' | 'r' | 'en' | 'em' )
            int alt24 = 5;
            switch ( input.LA( 1 ) )
            {
               case 'e':
               {
                  switch ( input.LA( 2 ) )
                  {
                     case 'n':
                     {
                        alt24 = 4;
                     }
                        break;
                     case 'm':
                     {
                        alt24 = 5;
                     }
                        break;
                     default :
                        alt24 = 1;
                  }
                  
               }
                  break;
               case 's':
               {
                  alt24 = 2;
               }
                  break;
               case 'r':
               {
                  alt24 = 3;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        24,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
            switch ( alt24 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:314:25:
               // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:314:29:
               // 's'
               {
                  match( 's' );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:314:33:
               // 'r'
               {
                  match( 'r' );
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:314:37:
               // 'en'
               {
                  match( "en" );
                  
               }
                  break;
               case 5:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:314:42:
               // 'em'
               {
                  match( "em" );
                  
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
   
   
   
   // $ANTLR end "LAST"
   
   // $ANTLR start "OTHER"
   public final void mOTHER() throws RecognitionException
   {
      try
      {
         int _type = OTHER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:316:15:
         // ( 'ander' ( 'e' | 'es' | 'er' | 'en' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:316:17:
         // 'ander' ( 'e' | 'es' | 'er' | 'en' )
         {
            match( "ander" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:316:24:
            // ( 'e' | 'es' | 'er' | 'en' )
            int alt25 = 4;
            int LA25_0 = input.LA( 1 );
            
            if ( ( LA25_0 == 'e' ) )
            {
               switch ( input.LA( 2 ) )
               {
                  case 's':
                  {
                     alt25 = 2;
                  }
                     break;
                  case 'r':
                  {
                     alt25 = 3;
                  }
                     break;
                  case 'n':
                  {
                     alt25 = 4;
                  }
                     break;
                  default :
                     alt25 = 1;
               }
               
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:316:25:
               // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:316:29:
               // 'es'
               {
                  match( "es" );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:316:34:
               // 'er'
               {
                  match( "er" );
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:316:39:
               // 'en'
               {
                  match( "en" );
                  
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
   
   
   
   // $ANTLR end "OTHER"
   
   // $ANTLR start "NUM_ONE"
   public final void mNUM_ONE() throws RecognitionException
   {
      try
      {
         int _type = NUM_ONE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:318:15:
         // ( 'ein' ( 's' | 'e' | 'er' | 'em' | 'en' | 'es' )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:318:17:
         // 'ein' ( 's' | 'e' | 'er' | 'em' | 'en' | 'es' )?
         {
            match( "ein" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:318:22:
            // ( 's' | 'e' | 'er' | 'em' | 'en' | 'es' )?
            int alt26 = 7;
            int LA26_0 = input.LA( 1 );
            
            if ( ( LA26_0 == 's' ) )
            {
               alt26 = 1;
            }
            else if ( ( LA26_0 == 'e' ) )
            {
               switch ( input.LA( 2 ) )
               {
                  case 'r':
                  {
                     alt26 = 3;
                  }
                     break;
                  case 'm':
                  {
                     alt26 = 4;
                  }
                     break;
                  case 'n':
                  {
                     alt26 = 5;
                  }
                     break;
                  case 's':
                  {
                     alt26 = 6;
                  }
                     break;
               }
               
            }
            switch ( alt26 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:318:23:
               // 's'
               {
                  match( 's' );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:318:27:
               // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:318:31:
               // 'er'
               {
                  match( "er" );
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:318:36:
               // 'em'
               {
                  match( "em" );
                  
               }
                  break;
               case 5:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:318:41:
               // 'en'
               {
                  match( "en" );
                  
               }
                  break;
               case 6:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:318:46:
               // 'es'
               {
                  match( "es" );
                  
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
   
   
   
   // $ANTLR end "NUM_ONE"
   
   // $ANTLR start "NUM_TWO"
   public final void mNUM_TWO() throws RecognitionException
   {
      try
      {
         int _type = NUM_TWO;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:320:15:
         // ( 'zwei' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:320:17:
         // 'zwei'
         {
            match( "zwei" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:322:15:
         // ( 'frei' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:322:17:
         // 'frei'
         {
            match( "frei" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:324:15:
         // ( 'vier' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:324:17:
         // 'vier'
         {
            match( "vier" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:326:15:
         // ( 'fünf' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:326:17:
         // 'fünf'
         {
            match( "fünf" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:328:15:
         // ( 'sechs' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:328:17:
         // 'sechs'
         {
            match( "sechs" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:330:15:
         // ( 'sieben' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:330:17:
         // 'sieben'
         {
            match( "sieben" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:332:15:
         // ( 'acht' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:332:17:
         // 'acht'
         {
            match( "acht" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:334:15:
         // ( 'neun' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:334:17:
         // 'neun'
         {
            match( "neun" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:336:15:
         // ( 'zehn' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:336:17:
         // 'zehn'
         {
            match( "zehn" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:338:15:
         // ( 'und' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:338:17:
         // 'und'
         {
            match( "und" );
            
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
   
   // $ANTLR start "ON"
   public final void mON() throws RecognitionException
   {
      try
      {
         int _type = ON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:340:15:
         // ( 'a' ( 'm' | 'n' ) | 'i' ( 'n' | 'm' ) | 'de' ( 's' | 'r' ) | 'vo' ( 'n' | 'm' ) )
         int alt27 = 4;
         switch ( input.LA( 1 ) )
         {
            case 'a':
            {
               alt27 = 1;
            }
               break;
            case 'i':
            {
               alt27 = 2;
            }
               break;
            case 'd':
            {
               alt27 = 3;
            }
               break;
            case 'v':
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:340:17:
            // 'a' ( 'm' | 'n' )
            {
               match( 'a' );
               
               if ( ( input.LA( 1 ) >= 'm' && input.LA( 1 ) <= 'n' ) )
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
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:340:32:
            // 'i' ( 'n' | 'm' )
            {
               match( 'i' );
               
               if ( ( input.LA( 1 ) >= 'm' && input.LA( 1 ) <= 'n' ) )
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
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:340:47:
            // 'de' ( 's' | 'r' )
            {
               match( "de" );
               
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
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:340:63:
            // 'vo' ( 'n' | 'm' )
            {
               match( "vo" );
               
               if ( ( input.LA( 1 ) >= 'm' && input.LA( 1 ) <= 'n' ) )
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
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "ON"
   
   // $ANTLR start "UNTIL"
   public final void mUNTIL() throws RecognitionException
   {
      try
      {
         int _type = UNTIL;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:342:15:
         // ( 'bis' STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:342:17:
         // 'bis' STRING
         {
            match( "bis" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:344:15:
         // ( 'für' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:344:17:
         // 'für'
         {
            match( "für" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:346:15:
         // ( 'mal' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:346:17:
         // 'mal'
         {
            match( "mal" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:348:15:
         // ( '.' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:348:17:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:350:15:
         // ( '-' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:350:17:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:352:15:
         // ( ',' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:352:17:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:356:15:
         // ( '0' .. '9' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:357:15:
         // ( ( MINUS )? ( NUMBER )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:357:17:
         // ( MINUS )? ( NUMBER )+
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:357:17:
            // ( MINUS )?
            int alt28 = 2;
            int LA28_0 = input.LA( 1 );
            
            if ( ( LA28_0 == '-' ) )
            {
               alt28 = 1;
            }
            switch ( alt28 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:
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
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:357:24:
            // ( NUMBER )+
            int cnt29 = 0;
            loop29: do
            {
               int alt29 = 2;
               int LA29_0 = input.LA( 1 );
               
               if ( ( ( LA29_0 >= '0' && LA29_0 <= '9' ) ) )
               {
                  alt29 = 1;
               }
               
               switch ( alt29 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:
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
                     if ( cnt29 >= 1 )
                        break loop29;
                     EarlyExitException eee = new EarlyExitException( 29, input );
                     throw eee;
               }
               cnt29++;
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:361:15:
         // ( ( ' ' | . )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:361:17:
         // ( ' ' | . )+
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:361:17:
            // ( ' ' | . )+
            int cnt30 = 0;
            loop30: do
            {
               int alt30 = 3;
               int LA30_0 = input.LA( 1 );
               
               if ( ( LA30_0 == ' ' ) )
               {
                  alt30 = 1;
               }
               else if ( ( ( LA30_0 >= '\u0000' && LA30_0 <= '\u001F' ) || ( LA30_0 >= '!' && LA30_0 <= '\uFFFF' ) ) )
               {
                  alt30 = 2;
               }
               
               switch ( alt30 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:361:18:
                  // ' '
                  {
                     match( ' ' );
                     
                  }
                     break;
                  case 2:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:361:22:
                  // .
                  {
                     matchAny();
                     
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:362:15:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:362:17:
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
      // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:8:
      // ( EVERY | AFTER | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY_LIT | WEEKEND | MONDAY | TUESDAY | WEDNESDAY
      // | THURSDAY | FRIDAY | SATURDAY | SUNDAY | FIRST | SECOND | THIRD | FOURTH | FIFTH | LAST | OTHER | NUM_ONE |
      // NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN | AND | ON |
      // UNTIL | FOR | TIMES | DOT | MINUS | COMMA | INT | WS )
      int alt31 = 43;
      alt31 = dfa31.predict( input );
      switch ( alt31 )
      {
         case 1:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:10:
         // EVERY
         {
            mEVERY();
            
         }
            break;
         case 2:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:16:
         // AFTER
         {
            mAFTER();
            
         }
            break;
         case 3:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:22:
         // YEARS
         {
            mYEARS();
            
         }
            break;
         case 4:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:28:
         // MONTHS
         {
            mMONTHS();
            
         }
            break;
         case 5:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:35:
         // WEEKS
         {
            mWEEKS();
            
         }
            break;
         case 6:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:41:
         // DAYS
         {
            mDAYS();
            
         }
            break;
         case 7:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:46:
         // MONTH
         {
            mMONTH();
            
         }
            break;
         case 8:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:52:
         // WEEKDAY_LIT
         {
            mWEEKDAY_LIT();
            
         }
            break;
         case 9:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:64:
         // WEEKEND
         {
            mWEEKEND();
            
         }
            break;
         case 10:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:72:
         // MONDAY
         {
            mMONDAY();
            
         }
            break;
         case 11:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:79:
         // TUESDAY
         {
            mTUESDAY();
            
         }
            break;
         case 12:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:87:
         // WEDNESDAY
         {
            mWEDNESDAY();
            
         }
            break;
         case 13:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:97:
         // THURSDAY
         {
            mTHURSDAY();
            
         }
            break;
         case 14:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:106:
         // FRIDAY
         {
            mFRIDAY();
            
         }
            break;
         case 15:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:113:
         // SATURDAY
         {
            mSATURDAY();
            
         }
            break;
         case 16:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:122:
         // SUNDAY
         {
            mSUNDAY();
            
         }
            break;
         case 17:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:129:
         // FIRST
         {
            mFIRST();
            
         }
            break;
         case 18:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:135:
         // SECOND
         {
            mSECOND();
            
         }
            break;
         case 19:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:142:
         // THIRD
         {
            mTHIRD();
            
         }
            break;
         case 20:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:148:
         // FOURTH
         {
            mFOURTH();
            
         }
            break;
         case 21:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:155:
         // FIFTH
         {
            mFIFTH();
            
         }
            break;
         case 22:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:161:
         // LAST
         {
            mLAST();
            
         }
            break;
         case 23:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:166:
         // OTHER
         {
            mOTHER();
            
         }
            break;
         case 24:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:172:
         // NUM_ONE
         {
            mNUM_ONE();
            
         }
            break;
         case 25:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:180:
         // NUM_TWO
         {
            mNUM_TWO();
            
         }
            break;
         case 26:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:188:
         // NUM_THREE
         {
            mNUM_THREE();
            
         }
            break;
         case 27:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:198:
         // NUM_FOUR
         {
            mNUM_FOUR();
            
         }
            break;
         case 28:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:207:
         // NUM_FIVE
         {
            mNUM_FIVE();
            
         }
            break;
         case 29:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:216:
         // NUM_SIX
         {
            mNUM_SIX();
            
         }
            break;
         case 30:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:224:
         // NUM_SEVEN
         {
            mNUM_SEVEN();
            
         }
            break;
         case 31:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:234:
         // NUM_EIGHT
         {
            mNUM_EIGHT();
            
         }
            break;
         case 32:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:244:
         // NUM_NINE
         {
            mNUM_NINE();
            
         }
            break;
         case 33:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:253:
         // NUM_TEN
         {
            mNUM_TEN();
            
         }
            break;
         case 34:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:261:
         // AND
         {
            mAND();
            
         }
            break;
         case 35:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:265:
         // ON
         {
            mON();
            
         }
            break;
         case 36:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:268:
         // UNTIL
         {
            mUNTIL();
            
         }
            break;
         case 37:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:274:
         // FOR
         {
            mFOR();
            
         }
            break;
         case 38:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:278:
         // TIMES
         {
            mTIMES();
            
         }
            break;
         case 39:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:284:
         // DOT
         {
            mDOT();
            
         }
            break;
         case 40:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:288:
         // MINUS
         {
            mMINUS();
            
         }
            break;
         case 41:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:294:
         // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 42:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:300:
         // INT
         {
            mINT();
            
         }
            break;
         case 43:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:1:304:
         // WS
         {
            mWS();
            
         }
            break;
      
      }
      
   }
   
   protected DFA31 dfa31 = new DFA31( this );
   
   static final String DFA31_eotS = "\23\uffff\1\60\5\uffff\1\20\3\uffff\1\64\3\uffff\1\70\36\uffff\1"
      + "\105\1\107\1\111\1\113\1\115\7\uffff\1\115\3\uffff";
   
   static final String DFA31_eofS = "\120\uffff";
   
   static final String DFA31_minS = "\1\11\1\141\1\143\2\141\1\157\1\uffff\1\145\1\141\1\uffff\1\145"
      + "\1\151\1\145\1\151\5\uffff\1\60\4\uffff\1\150\1\144\3\uffff\1\156"
      + "\1\151\1\uffff\1\143\1\145\1\156\1\143\3\uffff\1\162\5\uffff\1\145"
      + "\1\uffff\1\145\3\uffff\1\141\2\uffff\1\150\1\151\1\uffff\1\146\2"
      + "\uffff\1\151\1\162\1\uffff\1\145\4\164\1\156\7\uffff\1\145\3\uffff";
   
   static final String DFA31_maxS = "\1\172\2\165\1\157\1\u00e4\1\157\1\uffff\1\u00fc\1\157\1\uffff\2"
      + "\162\1\167\1\157\5\uffff\1\71\4\uffff\1\156\1\144\3\uffff\1\156"
      + "\1\154\1\uffff\1\143\1\145\1\162\1\160\3\uffff\1\172\5\uffff\1\145"
      + "\1\uffff\1\145\3\uffff\1\164\2\uffff\1\150\1\151\1\uffff\1\146\2"
      + "\uffff\1\151\1\162\1\uffff\1\145\4\164\1\156\7\uffff\1\164\3\uffff";
   
   static final String DFA31_acceptS = "\6\uffff\1\6\2\uffff\1\7\4\uffff\1\26\1\42\1\43\1\44\1\47\1\uffff"
      + "\1\51\1\52\1\53\1\1\2\uffff\1\37\1\2\1\40\2\uffff\1\14\4\uffff\1"
      + "\17\1\20\1\36\1\uffff\1\13\1\15\1\23\1\21\1\30\1\uffff\1\41\1\uffff"
      + "\1\50\1\3\1\27\1\uffff\1\12\1\46\2\uffff\1\16\1\uffff\1\45\1\35"
      + "\2\uffff\1\4\6\uffff\1\32\1\25\1\34\1\22\1\31\1\24\1\33\1\uffff"
      + "\1\5\1\10\1\11";
   
   static final String DFA31_specialS = "\120\uffff}>";
   
   static final String[] DFA31_transitionS =
   {
    "\2\26\2\uffff\1\26\22\uffff\1\26\13\uffff\1\24\1\23\1\22\1\uffff"
       + "\12\25\47\uffff\1\2\1\21\1\uffff\1\12\1\13\1\7\2\uffff\1\20"
       + "\1\1\1\uffff\1\16\1\4\1\3\1\11\3\uffff\1\10\1\6\1\17\1\15\1"
       + "\5\2\uffff\1\14", "\1\30\3\uffff\1\27\17\uffff\1\11",
    "\1\32\10\uffff\1\27\1\20\1\31\1\uffff\1\11\4\uffff\1\11",
    "\1\33\3\uffff\1\34\11\uffff\1\11",
    "\1\36\7\uffff\1\37\5\uffff\1\35\164\uffff\1\11", "\1\40", "",
    "\1\11\14\uffff\1\41\u0089\uffff\1\42",
    "\1\44\3\uffff\1\43\3\uffff\1\46\5\uffff\1\45", "",
    "\1\47\3\uffff\1\50\5\uffff\1\51\2\uffff\1\52", "\1\54\10\uffff\1\53",
    "\1\56\21\uffff\1\55", "\1\57\5\uffff\1\20", "", "", "", "", "", "\12\25",
    "", "", "", "", "\1\61\5\uffff\1\11", "\1\62", "", "", "", "\1\63",
    "\1\11\2\uffff\1\65", "", "\1\66", "\1\67", "\1\71\3\uffff\1\72",
    "\1\73\14\uffff\1\11", "", "", "", "\2\20\6\uffff\1\11", "", "", "", "",
    "", "\1\74", "", "\1\75", "", "", "", "\1\76\22\uffff\1\64", "", "",
    "\1\77", "\1\100", "", "\1\101", "", "", "\1\102", "\1\103", "", "\1\104",
    "\1\70", "\1\106", "\1\110", "\1\112", "\1\114", "", "", "", "", "", "",
    "", "\1\117\16\uffff\1\116", "", "", "" };
   
   static final short[] DFA31_eot = DFA.unpackEncodedString( DFA31_eotS );
   
   static final short[] DFA31_eof = DFA.unpackEncodedString( DFA31_eofS );
   
   static final char[] DFA31_min = DFA.unpackEncodedStringToUnsignedChars( DFA31_minS );
   
   static final char[] DFA31_max = DFA.unpackEncodedStringToUnsignedChars( DFA31_maxS );
   
   static final short[] DFA31_accept = DFA.unpackEncodedString( DFA31_acceptS );
   
   static final short[] DFA31_special = DFA.unpackEncodedString( DFA31_specialS );
   
   static final short[][] DFA31_transition;
   
   static
   {
      int numStates = DFA31_transitionS.length;
      DFA31_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA31_transition[ i ] = DFA.unpackEncodedString( DFA31_transitionS[ i ] );
      }
   }
   
   
   class DFA31 extends DFA
   {
      
      public DFA31( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 31;
         this.eot = DFA31_eot;
         this.eof = DFA31_eof;
         this.min = DFA31_min;
         this.max = DFA31_max;
         this.accept = DFA31_accept;
         this.special = DFA31_special;
         this.transition = DFA31_transition;
      }
      
      
      
      public String getDescription()
      {
         return "1:1: Tokens : ( EVERY | AFTER | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY_LIT | WEEKEND | MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | FIRST | SECOND | THIRD | FOURTH | FIFTH | LAST | OTHER | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN | AND | ON | UNTIL | FOR | TIMES | DOT | MINUS | COMMA | INT | WS );";
      }
   }
   
}
