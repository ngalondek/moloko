// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g 2013-01-13 13:43:28

package dev.drsoran.moloko.grammar.datetime.de;

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
   
   public static final int MONTH = 16;
   
   public static final int MONTHS = 17;
   
   public static final int NEVER = 18;
   
   public static final int NEXT = 19;
   
   public static final int NOW = 20;
   
   public static final int NUM_STR = 21;
   
   public static final int OF = 22;
   
   public static final int OF_THE = 23;
   
   public static final int ON = 24;
   
   public static final int SUFF_FMALE = 25;
   
   public static final int SUFF_MALE = 26;
   
   public static final int TODAY = 27;
   
   public static final int TOMORROW = 28;
   
   public static final int WEEKDAY = 29;
   
   public static final int WEEKS = 30;
   
   public static final int WS = 31;
   
   public static final int YEARS = 32;
   
   public static final int YESTERDAY = 33;
   
   
   
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
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g";
   }
   
   
   
   // $ANTLR start "A"
   public final void mA() throws RecognitionException
   {
      try
      {
         int _type = A;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:11:
         // ( 'ein' ( 'e' | 'er' | 'es' | 'em' | 'en' )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:13:
         // 'ein' ( 'e' | 'er' | 'es' | 'em' | 'en' )?
         {
            match( "ein" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:18:
            // ( 'e' | 'er' | 'es' | 'em' | 'en' )?
            int alt1 = 6;
            int LA1_0 = input.LA( 1 );
            
            if ( ( LA1_0 == 'e' ) )
            {
               switch ( input.LA( 2 ) )
               {
                  case 'r':
                  {
                     alt1 = 2;
                  }
                     break;
                  case 's':
                  {
                     alt1 = 3;
                  }
                     break;
                  case 'm':
                  {
                     alt1 = 4;
                  }
                     break;
                  case 'n':
                  {
                     alt1 = 5;
                  }
                     break;
               }
               
            }
            switch ( alt1 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:19:
               // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:23:
               // 'er'
               {
                  match( "er" );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:28:
               // 'es'
               {
                  match( "es" );
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:33:
               // 'em'
               {
                  match( "em" );
                  
               }
                  break;
               case 5:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:38:
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
   
   
   
   // $ANTLR end "A"
   
   // $ANTLR start "ON"
   public final void mON() throws RecognitionException
   {
      try
      {
         int _type = ON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:401:11:
         // ( 'am' | 'an' )
         int alt2 = 2;
         int LA2_0 = input.LA( 1 );
         
         if ( ( LA2_0 == 'a' ) )
         {
            int LA2_1 = input.LA( 2 );
            
            if ( ( LA2_1 == 'm' ) )
            {
               alt2 = 1;
            }
            else if ( ( LA2_1 == 'n' ) )
            {
               alt2 = 2;
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:401:13:
            // 'am'
            {
               match( "am" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:401:20:
            // 'an'
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:403:11:
         // ( 'von' | 'vom' | 'ab' | 'seit' )
         int alt3 = 4;
         switch ( input.LA( 1 ) )
         {
            case 'v':
            {
               int LA3_1 = input.LA( 2 );
               
               if ( ( LA3_1 == 'o' ) )
               {
                  int LA3_4 = input.LA( 3 );
                  
                  if ( ( LA3_4 == 'n' ) )
                  {
                     alt3 = 1;
                  }
                  else if ( ( LA3_4 == 'm' ) )
                  {
                     alt3 = 2;
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
                                                                        1,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'a':
            {
               alt3 = 3;
            }
               break;
            case 's':
            {
               alt3 = 4;
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:403:13:
            // 'von'
            {
               match( "von" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:403:21:
            // 'vom'
            {
               match( "vom" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:403:29:
            // 'ab'
            {
               match( "ab" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:403:36:
            // 'seit'
            {
               match( "seit" );
               
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
   
   
   
   // $ANTLR end "OF"
   
   // $ANTLR start "OF_THE"
   public final void mOF_THE() throws RecognitionException
   {
      try
      {
         int _type = OF_THE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:11:
         // ( 'der' | 'die' | 'das' | 'dem' | 'den' | 'des' )
         int alt4 = 6;
         int LA4_0 = input.LA( 1 );
         
         if ( ( LA4_0 == 'd' ) )
         {
            switch ( input.LA( 2 ) )
            {
               case 'e':
               {
                  switch ( input.LA( 3 ) )
                  {
                     case 'r':
                     {
                        alt4 = 1;
                     }
                        break;
                     case 'm':
                     {
                        alt4 = 4;
                     }
                        break;
                     case 'n':
                     {
                        alt4 = 5;
                     }
                        break;
                     case 's':
                     {
                        alt4 = 6;
                     }
                        break;
                     default :
                        NoViableAltException nvae = new NoViableAltException( "",
                                                                              4,
                                                                              2,
                                                                              input );
                        
                        throw nvae;
                        
                  }
                  
               }
                  break;
               case 'i':
               {
                  alt4 = 2;
               }
                  break;
               case 'a':
               {
                  alt4 = 3;
               }
                  break;
               default :
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:13:
            // 'der'
            {
               match( "der" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:21:
            // 'die'
            {
               match( "die" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:29:
            // 'das'
            {
               match( "das" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:37:
            // 'dem'
            {
               match( "dem" );
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:45:
            // 'den'
            {
               match( "den" );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:53:
            // 'des'
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
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "OF_THE"
   
   // $ANTLR start "IN"
   public final void mIN() throws RecognitionException
   {
      try
      {
         int _type = IN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:407:11:
         // ( 'in' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:407:13:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:409:11:
         // ( 'und' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:409:13:
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
   
   // $ANTLR start "END"
   public final void mEND() throws RecognitionException
   {
      try
      {
         int _type = END;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:411:11:
         // ( 'ende' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:411:13:
         // 'ende'
         {
            match( "ende" );
            
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
   
   // $ANTLR start "NOW"
   public final void mNOW() throws RecognitionException
   {
      try
      {
         int _type = NOW;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:413:11:
         // ( 'jetzt' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:413:13:
         // 'jetzt'
         {
            match( "jetzt" );
            
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
   
   // $ANTLR start "NEVER"
   public final void mNEVER() throws RecognitionException
   {
      try
      {
         int _type = NEVER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:415:11:
         // ( 'nie' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:415:13:
         // 'nie'
         {
            match( "nie" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:417:11:
         // ( 'heute' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:417:13:
         // 'heute'
         {
            match( "heute" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:419:11:
         // ( 'morgen' | 'mrg' )
         int alt5 = 2;
         int LA5_0 = input.LA( 1 );
         
         if ( ( LA5_0 == 'm' ) )
         {
            int LA5_1 = input.LA( 2 );
            
            if ( ( LA5_1 == 'o' ) )
            {
               alt5 = 1;
            }
            else if ( ( LA5_1 == 'r' ) )
            {
               alt5 = 2;
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:419:13:
            // 'morgen'
            {
               match( "morgen" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:419:24:
            // 'mrg'
            {
               match( "mrg" );
               
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:421:11:
         // ( 'gestern' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:421:13:
         // 'gestern'
         {
            match( "gestern" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:11:
         // ( 'n' ( 'a' | 'ae' | 'ä' ) 'chst' ( 'e' | 'er' | 'es' | 'en' | 'em' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:13:
         // 'n' ( 'a' | 'ae' | 'ä' ) 'chst' ( 'e' | 'er' | 'es' | 'en' | 'em' )
         {
            match( 'n' );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:16:
            // ( 'a' | 'ae' | 'ä' )
            int alt6 = 3;
            int LA6_0 = input.LA( 1 );
            
            if ( ( LA6_0 == 'a' ) )
            {
               int LA6_1 = input.LA( 2 );
               
               if ( ( LA6_1 == 'e' ) )
               {
                  alt6 = 2;
               }
               else if ( ( LA6_1 == 'c' ) )
               {
                  alt6 = 1;
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
            else if ( ( LA6_0 == '\u00E4' ) )
            {
               alt6 = 3;
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:17:
               // 'a'
               {
                  match( 'a' );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:21:
               // 'ae'
               {
                  match( "ae" );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:26:
               // 'ä'
               {
                  match( '\u00E4' );
                  
               }
                  break;
            
            }
            
            match( "chst" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:37:
            // ( 'e' | 'er' | 'es' | 'en' | 'em' )
            int alt7 = 5;
            int LA7_0 = input.LA( 1 );
            
            if ( ( LA7_0 == 'e' ) )
            {
               switch ( input.LA( 2 ) )
               {
                  case 'r':
                  {
                     alt7 = 2;
                  }
                     break;
                  case 's':
                  {
                     alt7 = 3;
                  }
                     break;
                  case 'n':
                  {
                     alt7 = 4;
                  }
                     break;
                  case 'm':
                  {
                     alt7 = 5;
                  }
                     break;
                  default :
                     alt7 = 1;
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:38:
               // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:42:
               // 'er'
               {
                  match( "er" );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:47:
               // 'es'
               {
                  match( "es" );
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:52:
               // 'en'
               {
                  match( "en" );
                  
               }
                  break;
               case 5:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:57:
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
   
   
   
   // $ANTLR end "NEXT"
   
   // $ANTLR start "YEARS"
   public final void mYEARS() throws RecognitionException
   {
      try
      {
         int _type = YEARS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:11:
         // ( 'jahr' ( SUFF_MALE )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:13:
         // 'jahr' ( SUFF_MALE )?
         {
            match( "jahr" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:19:
            // ( SUFF_MALE )?
            int alt8 = 2;
            int LA8_0 = input.LA( 1 );
            
            if ( ( LA8_0 == 'e' || LA8_0 == 's' ) )
            {
               alt8 = 1;
            }
            switch ( alt8 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:19:
               // SUFF_MALE
               {
                  mSUFF_MALE();
                  
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:11:
         // ( 'monat' ( SUFF_MALE )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:13:
         // 'monat' ( SUFF_MALE )?
         {
            match( "monat" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:20:
            // ( SUFF_MALE )?
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == 'e' || LA9_0 == 's' ) )
            {
               alt9 = 1;
            }
            switch ( alt9 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:20:
               // SUFF_MALE
               {
                  mSUFF_MALE();
                  
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:11:
         // ( 'woche' ( SUFF_FMALE )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:13:
         // 'woche' ( SUFF_FMALE )?
         {
            match( "woche" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:20:
            // ( SUFF_FMALE )?
            int alt10 = 2;
            int LA10_0 = input.LA( 1 );
            
            if ( ( LA10_0 == 'n' ) )
            {
               alt10 = 1;
            }
            switch ( alt10 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:
               {
                  if ( input.LA( 1 ) == 'n' )
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:431:11:
         // ( 'tag' ( SUFF_MALE )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:431:13:
         // 'tag' ( SUFF_MALE )?
         {
            match( "tag" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:431:18:
            // ( SUFF_MALE )?
            int alt11 = 2;
            int LA11_0 = input.LA( 1 );
            
            if ( ( LA11_0 == 'e' || LA11_0 == 's' ) )
            {
               alt11 = 1;
            }
            switch ( alt11 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:431:18:
               // SUFF_MALE
               {
                  mSUFF_MALE();
                  
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:11:
         // ( 'jan' | 'januar' ( SUFF_MALE )? | 'feb' | 'februar' ( SUFF_MALE )? | 'm' ( 'a' | 'ä' | 'ae' ) 'r' | 'm' (
         // 'a' | 'ä' | 'ae' ) 'rz' ( SUFF_MALE )? | 'mai' ( SUFF_FMALE )? | 'jun' | 'juni' ( SUFF_MALE )? | 'jul' |
         // 'juli' ( SUFF_MALE )? | 'aug' | 'august' ( SUFF_MALE )? | 'sep' | 'september' ( SUFF_MALE )? | 'okt' |
         // 'oktober' ( SUFF_MALE )? | 'nov' | 'november' ( SUFF_MALE )? | 'dez' | 'dezember' ( SUFF_MALE )? )
         int alt25 = 21;
         switch ( input.LA( 1 ) )
         {
            case 'j':
            {
               int LA25_1 = input.LA( 2 );
               
               if ( ( LA25_1 == 'a' ) )
               {
                  int LA25_9 = input.LA( 3 );
                  
                  if ( ( LA25_9 == 'n' ) )
                  {
                     int LA25_19 = input.LA( 4 );
                     
                     if ( ( LA25_19 == 'u' ) )
                     {
                        alt25 = 2;
                     }
                     else
                     {
                        alt25 = 1;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           25,
                                                                           9,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else if ( ( LA25_1 == 'u' ) )
               {
                  int LA25_10 = input.LA( 3 );
                  
                  if ( ( LA25_10 == 'n' ) )
                  {
                     int LA25_20 = input.LA( 4 );
                     
                     if ( ( LA25_20 == 'i' ) )
                     {
                        alt25 = 9;
                     }
                     else
                     {
                        alt25 = 8;
                     }
                  }
                  else if ( ( LA25_10 == 'l' ) )
                  {
                     int LA25_21 = input.LA( 4 );
                     
                     if ( ( LA25_21 == 'i' ) )
                     {
                        alt25 = 11;
                     }
                     else
                     {
                        alt25 = 10;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           25,
                                                                           10,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        25,
                                                                        1,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'f':
            {
               int LA25_2 = input.LA( 2 );
               
               if ( ( LA25_2 == 'e' ) )
               {
                  int LA25_11 = input.LA( 3 );
                  
                  if ( ( LA25_11 == 'b' ) )
                  {
                     int LA25_22 = input.LA( 4 );
                     
                     if ( ( LA25_22 == 'r' ) )
                     {
                        alt25 = 4;
                     }
                     else
                     {
                        alt25 = 3;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           25,
                                                                           11,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        25,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'm':
            {
               int LA25_3 = input.LA( 2 );
               
               if ( ( LA25_3 == 'a' ) )
               {
                  switch ( input.LA( 3 ) )
                  {
                     case 'i':
                     {
                        alt25 = 7;
                     }
                        break;
                     case 'e':
                     {
                        int LA25_24 = input.LA( 4 );
                        
                        if ( ( LA25_24 == 'r' ) )
                        {
                           int LA25_25 = input.LA( 5 );
                           
                           if ( ( LA25_25 == 'z' ) )
                           {
                              alt25 = 6;
                           }
                           else
                           {
                              alt25 = 5;
                           }
                        }
                        else
                        {
                           NoViableAltException nvae = new NoViableAltException( "",
                                                                                 25,
                                                                                 24,
                                                                                 input );
                           
                           throw nvae;
                           
                        }
                     }
                        break;
                     case 'r':
                     {
                        int LA25_25 = input.LA( 4 );
                        
                        if ( ( LA25_25 == 'z' ) )
                        {
                           alt25 = 6;
                        }
                        else
                        {
                           alt25 = 5;
                        }
                     }
                        break;
                     default :
                        NoViableAltException nvae = new NoViableAltException( "",
                                                                              25,
                                                                              12,
                                                                              input );
                        
                        throw nvae;
                        
                  }
                  
               }
               else if ( ( LA25_3 == '\u00E4' ) )
               {
                  int LA25_13 = input.LA( 3 );
                  
                  if ( ( LA25_13 == 'r' ) )
                  {
                     int LA25_25 = input.LA( 4 );
                     
                     if ( ( LA25_25 == 'z' ) )
                     {
                        alt25 = 6;
                     }
                     else
                     {
                        alt25 = 5;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           25,
                                                                           13,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        25,
                                                                        3,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'a':
            {
               int LA25_4 = input.LA( 2 );
               
               if ( ( LA25_4 == 'u' ) )
               {
                  int LA25_14 = input.LA( 3 );
                  
                  if ( ( LA25_14 == 'g' ) )
                  {
                     int LA25_26 = input.LA( 4 );
                     
                     if ( ( LA25_26 == 'u' ) )
                     {
                        alt25 = 13;
                     }
                     else
                     {
                        alt25 = 12;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           25,
                                                                           14,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        25,
                                                                        4,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 's':
            {
               int LA25_5 = input.LA( 2 );
               
               if ( ( LA25_5 == 'e' ) )
               {
                  int LA25_15 = input.LA( 3 );
                  
                  if ( ( LA25_15 == 'p' ) )
                  {
                     int LA25_27 = input.LA( 4 );
                     
                     if ( ( LA25_27 == 't' ) )
                     {
                        alt25 = 15;
                     }
                     else
                     {
                        alt25 = 14;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           25,
                                                                           15,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        25,
                                                                        5,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'o':
            {
               int LA25_6 = input.LA( 2 );
               
               if ( ( LA25_6 == 'k' ) )
               {
                  int LA25_16 = input.LA( 3 );
                  
                  if ( ( LA25_16 == 't' ) )
                  {
                     int LA25_28 = input.LA( 4 );
                     
                     if ( ( LA25_28 == 'o' ) )
                     {
                        alt25 = 17;
                     }
                     else
                     {
                        alt25 = 16;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           25,
                                                                           16,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        25,
                                                                        6,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'n':
            {
               int LA25_7 = input.LA( 2 );
               
               if ( ( LA25_7 == 'o' ) )
               {
                  int LA25_17 = input.LA( 3 );
                  
                  if ( ( LA25_17 == 'v' ) )
                  {
                     int LA25_29 = input.LA( 4 );
                     
                     if ( ( LA25_29 == 'e' ) )
                     {
                        alt25 = 19;
                     }
                     else
                     {
                        alt25 = 18;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           25,
                                                                           17,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        25,
                                                                        7,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'd':
            {
               int LA25_8 = input.LA( 2 );
               
               if ( ( LA25_8 == 'e' ) )
               {
                  int LA25_18 = input.LA( 3 );
                  
                  if ( ( LA25_18 == 'z' ) )
                  {
                     int LA25_30 = input.LA( 4 );
                     
                     if ( ( LA25_30 == 'e' ) )
                     {
                        alt25 = 21;
                     }
                     else
                     {
                        alt25 = 20;
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           25,
                                                                           18,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        25,
                                                                        8,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     25,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
         }
         
         switch ( alt25 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:15:
            // 'jan'
            {
               match( "jan" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:38:
            // 'januar' ( SUFF_MALE )?
            {
               match( "januar" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:46:
               // ( SUFF_MALE )?
               int alt12 = 2;
               int LA12_0 = input.LA( 1 );
               
               if ( ( LA12_0 == 'e' || LA12_0 == 's' ) )
               {
                  alt12 = 1;
               }
               switch ( alt12 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:46:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:15:
            // 'feb'
            {
               match( "feb" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:38:
            // 'februar' ( SUFF_MALE )?
            {
               match( "februar" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:47:
               // ( SUFF_MALE )?
               int alt13 = 2;
               int LA13_0 = input.LA( 1 );
               
               if ( ( LA13_0 == 'e' || LA13_0 == 's' ) )
               {
                  alt13 = 1;
               }
               switch ( alt13 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:47:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:15:
            // 'm' ( 'a' | 'ä' | 'ae' ) 'r'
            {
               match( 'm' );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:18:
               // ( 'a' | 'ä' | 'ae' )
               int alt14 = 3;
               int LA14_0 = input.LA( 1 );
               
               if ( ( LA14_0 == 'a' ) )
               {
                  int LA14_1 = input.LA( 2 );
                  
                  if ( ( LA14_1 == 'e' ) )
                  {
                     alt14 = 3;
                  }
                  else if ( ( LA14_1 == 'r' ) )
                  {
                     alt14 = 1;
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
               else if ( ( LA14_0 == '\u00E4' ) )
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
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:19:
                  // 'a'
                  {
                     match( 'a' );
                     
                  }
                     break;
                  case 2:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:23:
                  // 'ä'
                  {
                     match( '\u00E4' );
                     
                  }
                     break;
                  case 3:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:27:
                  // 'ae'
                  {
                     match( "ae" );
                     
                  }
                     break;
               
               }
               
               match( 'r' );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:38:
            // 'm' ( 'a' | 'ä' | 'ae' ) 'rz' ( SUFF_MALE )?
            {
               match( 'm' );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:41:
               // ( 'a' | 'ä' | 'ae' )
               int alt15 = 3;
               int LA15_0 = input.LA( 1 );
               
               if ( ( LA15_0 == 'a' ) )
               {
                  int LA15_1 = input.LA( 2 );
                  
                  if ( ( LA15_1 == 'e' ) )
                  {
                     alt15 = 3;
                  }
                  else if ( ( LA15_1 == 'r' ) )
                  {
                     alt15 = 1;
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
               else if ( ( LA15_0 == '\u00E4' ) )
               {
                  alt15 = 2;
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
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:42:
                  // 'a'
                  {
                     match( 'a' );
                     
                  }
                     break;
                  case 2:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:46:
                  // 'ä'
                  {
                     match( '\u00E4' );
                     
                  }
                     break;
                  case 3:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:50:
                  // 'ae'
                  {
                     match( "ae" );
                     
                  }
                     break;
               
               }
               
               match( "rz" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:59:
               // ( SUFF_MALE )?
               int alt16 = 2;
               int LA16_0 = input.LA( 1 );
               
               if ( ( LA16_0 == 'e' || LA16_0 == 's' ) )
               {
                  alt16 = 1;
               }
               switch ( alt16 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:59:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:436:15:
            // 'mai' ( SUFF_FMALE )?
            {
               match( "mai" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:436:20:
               // ( SUFF_FMALE )?
               int alt17 = 2;
               int LA17_0 = input.LA( 1 );
               
               if ( ( LA17_0 == 'n' ) )
               {
                  alt17 = 1;
               }
               switch ( alt17 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:
                  {
                     if ( input.LA( 1 ) == 'n' )
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
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:15:
            // 'jun'
            {
               match( "jun" );
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:38:
            // 'juni' ( SUFF_MALE )?
            {
               match( "juni" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:44:
               // ( SUFF_MALE )?
               int alt18 = 2;
               int LA18_0 = input.LA( 1 );
               
               if ( ( LA18_0 == 'e' || LA18_0 == 's' ) )
               {
                  alt18 = 1;
               }
               switch ( alt18 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:44:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 10:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:438:15:
            // 'jul'
            {
               match( "jul" );
               
            }
               break;
            case 11:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:438:38:
            // 'juli' ( SUFF_MALE )?
            {
               match( "juli" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:438:44:
               // ( SUFF_MALE )?
               int alt19 = 2;
               int LA19_0 = input.LA( 1 );
               
               if ( ( LA19_0 == 'e' || LA19_0 == 's' ) )
               {
                  alt19 = 1;
               }
               switch ( alt19 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:438:44:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 12:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:15:
            // 'aug'
            {
               match( "aug" );
               
            }
               break;
            case 13:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:38:
            // 'august' ( SUFF_MALE )?
            {
               match( "august" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:46:
               // ( SUFF_MALE )?
               int alt20 = 2;
               int LA20_0 = input.LA( 1 );
               
               if ( ( LA20_0 == 'e' || LA20_0 == 's' ) )
               {
                  alt20 = 1;
               }
               switch ( alt20 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:46:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 14:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:15:
            // 'sep'
            {
               match( "sep" );
               
            }
               break;
            case 15:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:38:
            // 'september' ( SUFF_MALE )?
            {
               match( "september" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:49:
               // ( SUFF_MALE )?
               int alt21 = 2;
               int LA21_0 = input.LA( 1 );
               
               if ( ( LA21_0 == 'e' || LA21_0 == 's' ) )
               {
                  alt21 = 1;
               }
               switch ( alt21 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:49:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 16:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:441:15:
            // 'okt'
            {
               match( "okt" );
               
            }
               break;
            case 17:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:441:38:
            // 'oktober' ( SUFF_MALE )?
            {
               match( "oktober" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:441:47:
               // ( SUFF_MALE )?
               int alt22 = 2;
               int LA22_0 = input.LA( 1 );
               
               if ( ( LA22_0 == 'e' || LA22_0 == 's' ) )
               {
                  alt22 = 1;
               }
               switch ( alt22 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:441:47:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 18:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:442:15:
            // 'nov'
            {
               match( "nov" );
               
            }
               break;
            case 19:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:442:38:
            // 'november' ( SUFF_MALE )?
            {
               match( "november" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:442:48:
               // ( SUFF_MALE )?
               int alt23 = 2;
               int LA23_0 = input.LA( 1 );
               
               if ( ( LA23_0 == 'e' || LA23_0 == 's' ) )
               {
                  alt23 = 1;
               }
               switch ( alt23 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:442:48:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 20:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:443:15:
            // 'dez'
            {
               match( "dez" );
               
            }
               break;
            case 21:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:443:38:
            // 'dezember' ( SUFF_MALE )?
            {
               match( "dezember" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:443:48:
               // ( SUFF_MALE )?
               int alt24 = 2;
               int LA24_0 = input.LA( 1 );
               
               if ( ( LA24_0 == 'e' || LA24_0 == 's' ) )
               {
                  alt24 = 1;
               }
               switch ( alt24 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:443:48:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:445:11:
         // ( 'mo' | 'montag' ( SUFF_MALE )? | 'di' | 'dienstag' ( SUFF_MALE )? | 'mi' | 'mittwoch' ( SUFF_MALE )? |
         // 'do' | 'donnestag' ( SUFF_MALE )? | 'fr' | 'freitag' ( SUFF_MALE )? | 'sa' | 'samstag' ( SUFF_MALE )? | 'so'
         // | 'sonntag' ( SUFF_MALE )? )
         int alt33 = 14;
         switch ( input.LA( 1 ) )
         {
            case 'm':
            {
               int LA33_1 = input.LA( 2 );
               
               if ( ( LA33_1 == 'o' ) )
               {
                  int LA33_5 = input.LA( 3 );
                  
                  if ( ( LA33_5 == 'n' ) )
                  {
                     alt33 = 2;
                  }
                  else
                  {
                     alt33 = 1;
                  }
               }
               else if ( ( LA33_1 == 'i' ) )
               {
                  int LA33_6 = input.LA( 3 );
                  
                  if ( ( LA33_6 == 't' ) )
                  {
                     alt33 = 6;
                  }
                  else
                  {
                     alt33 = 5;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        33,
                                                                        1,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'd':
            {
               int LA33_2 = input.LA( 2 );
               
               if ( ( LA33_2 == 'i' ) )
               {
                  int LA33_7 = input.LA( 3 );
                  
                  if ( ( LA33_7 == 'e' ) )
                  {
                     alt33 = 4;
                  }
                  else
                  {
                     alt33 = 3;
                  }
               }
               else if ( ( LA33_2 == 'o' ) )
               {
                  int LA33_8 = input.LA( 3 );
                  
                  if ( ( LA33_8 == 'n' ) )
                  {
                     alt33 = 8;
                  }
                  else
                  {
                     alt33 = 7;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        33,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'f':
            {
               int LA33_3 = input.LA( 2 );
               
               if ( ( LA33_3 == 'r' ) )
               {
                  int LA33_9 = input.LA( 3 );
                  
                  if ( ( LA33_9 == 'e' ) )
                  {
                     alt33 = 10;
                  }
                  else
                  {
                     alt33 = 9;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        33,
                                                                        3,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 's':
            {
               int LA33_4 = input.LA( 2 );
               
               if ( ( LA33_4 == 'a' ) )
               {
                  int LA33_10 = input.LA( 3 );
                  
                  if ( ( LA33_10 == 'm' ) )
                  {
                     alt33 = 12;
                  }
                  else
                  {
                     alt33 = 11;
                  }
               }
               else if ( ( LA33_4 == 'o' ) )
               {
                  int LA33_11 = input.LA( 3 );
                  
                  if ( ( LA33_11 == 'n' ) )
                  {
                     alt33 = 14;
                  }
                  else
                  {
                     alt33 = 13;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        33,
                                                                        4,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     33,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
         }
         
         switch ( alt33 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:445:16:
            // 'mo'
            {
               match( "mo" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:445:23:
            // 'montag' ( SUFF_MALE )?
            {
               match( "montag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:445:31:
               // ( SUFF_MALE )?
               int alt26 = 2;
               int LA26_0 = input.LA( 1 );
               
               if ( ( LA26_0 == 'e' || LA26_0 == 's' ) )
               {
                  alt26 = 1;
               }
               switch ( alt26 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:445:31:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:446:16:
            // 'di'
            {
               match( "di" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:446:23:
            // 'dienstag' ( SUFF_MALE )?
            {
               match( "dienstag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:446:33:
               // ( SUFF_MALE )?
               int alt27 = 2;
               int LA27_0 = input.LA( 1 );
               
               if ( ( LA27_0 == 'e' || LA27_0 == 's' ) )
               {
                  alt27 = 1;
               }
               switch ( alt27 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:446:33:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:447:16:
            // 'mi'
            {
               match( "mi" );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:447:23:
            // 'mittwoch' ( SUFF_MALE )?
            {
               match( "mittwoch" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:447:33:
               // ( SUFF_MALE )?
               int alt28 = 2;
               int LA28_0 = input.LA( 1 );
               
               if ( ( LA28_0 == 'e' || LA28_0 == 's' ) )
               {
                  alt28 = 1;
               }
               switch ( alt28 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:447:33:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:448:16:
            // 'do'
            {
               match( "do" );
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:448:23:
            // 'donnestag' ( SUFF_MALE )?
            {
               match( "donnestag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:448:34:
               // ( SUFF_MALE )?
               int alt29 = 2;
               int LA29_0 = input.LA( 1 );
               
               if ( ( LA29_0 == 'e' || LA29_0 == 's' ) )
               {
                  alt29 = 1;
               }
               switch ( alt29 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:448:34:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:449:16:
            // 'fr'
            {
               match( "fr" );
               
            }
               break;
            case 10:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:449:23:
            // 'freitag' ( SUFF_MALE )?
            {
               match( "freitag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:449:32:
               // ( SUFF_MALE )?
               int alt30 = 2;
               int LA30_0 = input.LA( 1 );
               
               if ( ( LA30_0 == 'e' || LA30_0 == 's' ) )
               {
                  alt30 = 1;
               }
               switch ( alt30 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:449:32:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 11:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:450:16:
            // 'sa'
            {
               match( "sa" );
               
            }
               break;
            case 12:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:450:23:
            // 'samstag' ( SUFF_MALE )?
            {
               match( "samstag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:450:32:
               // ( SUFF_MALE )?
               int alt31 = 2;
               int LA31_0 = input.LA( 1 );
               
               if ( ( LA31_0 == 'e' || LA31_0 == 's' ) )
               {
                  alt31 = 1;
               }
               switch ( alt31 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:450:32:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 13:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:451:16:
            // 'so'
            {
               match( "so" );
               
            }
               break;
            case 14:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:451:23:
            // 'sonntag' ( SUFF_MALE )?
            {
               match( "sonntag" );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:451:32:
               // ( SUFF_MALE )?
               int alt32 = 2;
               int LA32_0 = input.LA( 1 );
               
               if ( ( LA32_0 == 'e' || LA32_0 == 's' ) )
               {
                  alt32 = 1;
               }
               switch ( alt32 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:451:32:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:11:
         // ( 'eins' | 'zwei' | 'drei' | 'vier' | 'f' ( 'u' | 'ü' | 'ue' ) 'nf' | 'sechs' | 'sieben' | 'acht' | 'neun' |
         // 'zehn' )
         int alt35 = 10;
         switch ( input.LA( 1 ) )
         {
            case 'e':
            {
               alt35 = 1;
            }
               break;
            case 'z':
            {
               int LA35_2 = input.LA( 2 );
               
               if ( ( LA35_2 == 'w' ) )
               {
                  alt35 = 2;
               }
               else if ( ( LA35_2 == 'e' ) )
               {
                  alt35 = 10;
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        35,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'd':
            {
               alt35 = 3;
            }
               break;
            case 'v':
            {
               alt35 = 4;
            }
               break;
            case 'f':
            {
               alt35 = 5;
            }
               break;
            case 's':
            {
               int LA35_6 = input.LA( 2 );
               
               if ( ( LA35_6 == 'e' ) )
               {
                  alt35 = 6;
               }
               else if ( ( LA35_6 == 'i' ) )
               {
                  alt35 = 7;
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        35,
                                                                        6,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
               break;
            case 'a':
            {
               alt35 = 8;
            }
               break;
            case 'n':
            {
               alt35 = 9;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     35,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
         }
         
         switch ( alt35 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:13:
            // 'eins'
            {
               match( "eins" );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:23:
            // 'zwei'
            {
               match( "zwei" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:34:
            // 'drei'
            {
               match( "drei" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:43:
            // 'vier'
            {
               match( "vier" );
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:52:
            // 'f' ( 'u' | 'ü' | 'ue' ) 'nf'
            {
               match( 'f' );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:55:
               // ( 'u' | 'ü' | 'ue' )
               int alt34 = 3;
               int LA34_0 = input.LA( 1 );
               
               if ( ( LA34_0 == 'u' ) )
               {
                  int LA34_1 = input.LA( 2 );
                  
                  if ( ( LA34_1 == 'e' ) )
                  {
                     alt34 = 3;
                  }
                  else if ( ( LA34_1 == 'n' ) )
                  {
                     alt34 = 1;
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           34,
                                                                           1,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else if ( ( LA34_0 == '\u00FC' ) )
               {
                  alt34 = 2;
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        34,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
                  
               }
               switch ( alt34 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:56:
                  // 'u'
                  {
                     match( 'u' );
                     
                  }
                     break;
                  case 2:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:60:
                  // 'ü'
                  {
                     match( '\u00FC' );
                     
                  }
                     break;
                  case 3:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:64:
                  // 'ue'
                  {
                     match( "ue" );
                     
                  }
                     break;
               
               }
               
               match( "nf" );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:454:13:
            // 'sechs'
            {
               match( "sechs" );
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:454:23:
            // 'sieben'
            {
               match( "sieben" );
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:454:34:
            // 'acht'
            {
               match( "acht" );
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:454:43:
            // 'neun'
            {
               match( "neun" );
               
            }
               break;
            case 10:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:454:52:
            // 'zehn'
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:456:11:
         // ( '/' | '\\u5E74' | '\\u6708' | '\\u65E5' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:458:11:
         // ( '.' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:458:13:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:460:11:
         // ( ':' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:460:13:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:462:11:
         // ( '-' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:462:13:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:464:11:
         // ( ',' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:464:13:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:466:21:
         // ( '@' | 'um' | COMMA )
         int alt36 = 3;
         switch ( input.LA( 1 ) )
         {
            case '@':
            {
               alt36 = 1;
            }
               break;
            case 'u':
            {
               alt36 = 2;
            }
               break;
            case ',':
            {
               alt36 = 3;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     36,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
         }
         
         switch ( alt36 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:466:23:
            // '@'
            {
               match( '@' );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:466:29:
            // 'um'
            {
               match( "um" );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:466:36:
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
   
   // $ANTLR start "SUFF_MALE"
   public final void mSUFF_MALE() throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:470:11:
         // ( 'e' | 's' | 'es' | 'en' )
         int alt37 = 4;
         int LA37_0 = input.LA( 1 );
         
         if ( ( LA37_0 == 'e' ) )
         {
            switch ( input.LA( 2 ) )
            {
               case 's':
               {
                  alt37 = 3;
               }
                  break;
               case 'n':
               {
                  alt37 = 4;
               }
                  break;
               default :
                  alt37 = 1;
            }
            
         }
         else if ( ( LA37_0 == 's' ) )
         {
            alt37 = 2;
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  37,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
         }
         switch ( alt37 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:470:13:
            // 'e'
            {
               match( 'e' );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:470:17:
            // 's'
            {
               match( 's' );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:470:21:
            // 'es'
            {
               match( "es" );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:470:26:
            // 'en'
            {
               match( "en" );
               
            }
               break;
         
         }
         
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "SUFF_MALE"
   
   // $ANTLR start "SUFF_FMALE"
   public final void mSUFF_FMALE() throws RecognitionException
   {
      try
      {
         int _type = SUFF_FMALE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:471:12:
         // ( 'n' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:471:14:
         // 'n'
         {
            match( 'n' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "SUFF_FMALE"
   
   // $ANTLR start "INT"
   public final void mINT() throws RecognitionException
   {
      try
      {
         int _type = INT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:473:11:
         // ( ( '0' .. '9' )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:473:13:
         // ( '0' .. '9' )+
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:473:13:
            // ( '0' .. '9' )+
            int cnt38 = 0;
            loop38: do
            {
               int alt38 = 2;
               int LA38_0 = input.LA( 1 );
               
               if ( ( ( LA38_0 >= '0' && LA38_0 <= '9' ) ) )
               {
                  alt38 = 1;
               }
               
               switch ( alt38 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:
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
                     if ( cnt38 >= 1 )
                        break loop38;
                     EarlyExitException eee = new EarlyExitException( 38, input );
                     throw eee;
               }
               cnt38++;
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:475:11:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:475:13:
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
      // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:8:
      // ( A | ON | OF | OF_THE | IN | AND | END | NOW | NEVER | TODAY | TOMORROW | YESTERDAY | NEXT | YEARS | MONTHS |
      // WEEKS | DAYS | MONTH | WEEKDAY | NUM_STR | DATE_SEP | DOT | COLON | MINUS | COMMA | DATE_TIME_SEPARATOR |
      // SUFF_FMALE | INT | WS )
      int alt39 = 29;
      switch ( input.LA( 1 ) )
      {
         case 'e':
         {
            int LA39_1 = input.LA( 2 );
            
            if ( ( LA39_1 == 'i' ) )
            {
               int LA39_26 = input.LA( 3 );
               
               if ( ( LA39_26 == 'n' ) )
               {
                  int LA39_44 = input.LA( 4 );
                  
                  if ( ( LA39_44 == 's' ) )
                  {
                     alt39 = 20;
                  }
                  else
                  {
                     alt39 = 1;
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        39,
                                                                        26,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else if ( ( LA39_1 == 'n' ) )
            {
               alt39 = 7;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     39,
                                                                     1,
                                                                     input );
               
               throw nvae;
               
            }
         }
            break;
         case 'a':
         {
            switch ( input.LA( 2 ) )
            {
               case 'm':
               case 'n':
               {
                  alt39 = 2;
               }
                  break;
               case 'b':
               {
                  alt39 = 3;
               }
                  break;
               case 'u':
               {
                  alt39 = 18;
               }
                  break;
               case 'c':
               {
                  alt39 = 20;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        39,
                                                                        2,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case 'v':
         {
            int LA39_3 = input.LA( 2 );
            
            if ( ( LA39_3 == 'o' ) )
            {
               alt39 = 3;
            }
            else if ( ( LA39_3 == 'i' ) )
            {
               alt39 = 20;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     39,
                                                                     3,
                                                                     input );
               
               throw nvae;
               
            }
         }
            break;
         case 's':
         {
            switch ( input.LA( 2 ) )
            {
               case 'e':
               {
                  switch ( input.LA( 3 ) )
                  {
                     case 'i':
                     {
                        alt39 = 3;
                     }
                        break;
                     case 'p':
                     {
                        alt39 = 18;
                     }
                        break;
                     case 'c':
                     {
                        alt39 = 20;
                     }
                        break;
                     default :
                        NoViableAltException nvae = new NoViableAltException( "",
                                                                              39,
                                                                              30,
                                                                              input );
                        
                        throw nvae;
                        
                  }
                  
               }
                  break;
               case 'a':
               case 'o':
               {
                  alt39 = 19;
               }
                  break;
               case 'i':
               {
                  alt39 = 20;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        39,
                                                                        4,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case 'd':
         {
            switch ( input.LA( 2 ) )
            {
               case 'e':
               {
                  int LA39_32 = input.LA( 3 );
                  
                  if ( ( ( LA39_32 >= 'm' && LA39_32 <= 'n' ) || ( LA39_32 >= 'r' && LA39_32 <= 's' ) ) )
                  {
                     alt39 = 4;
                  }
                  else if ( ( LA39_32 == 'z' ) )
                  {
                     alt39 = 18;
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           39,
                                                                           32,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
                  break;
               case 'i':
               {
                  int LA39_33 = input.LA( 3 );
                  
                  if ( ( LA39_33 == 'e' ) )
                  {
                     int LA39_45 = input.LA( 4 );
                     
                     if ( ( LA39_45 == 'n' ) )
                     {
                        alt39 = 19;
                     }
                     else
                     {
                        alt39 = 4;
                     }
                  }
                  else
                  {
                     alt39 = 19;
                  }
               }
                  break;
               case 'a':
               {
                  alt39 = 4;
               }
                  break;
               case 'o':
               {
                  alt39 = 19;
               }
                  break;
               case 'r':
               {
                  alt39 = 20;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        39,
                                                                        5,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case 'i':
         {
            alt39 = 5;
         }
            break;
         case 'u':
         {
            int LA39_7 = input.LA( 2 );
            
            if ( ( LA39_7 == 'n' ) )
            {
               alt39 = 6;
            }
            else if ( ( LA39_7 == 'm' ) )
            {
               alt39 = 26;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     39,
                                                                     7,
                                                                     input );
               
               throw nvae;
               
            }
         }
            break;
         case 'j':
         {
            switch ( input.LA( 2 ) )
            {
               case 'e':
               {
                  alt39 = 8;
               }
                  break;
               case 'a':
               {
                  int LA39_37 = input.LA( 3 );
                  
                  if ( ( LA39_37 == 'h' ) )
                  {
                     alt39 = 14;
                  }
                  else if ( ( LA39_37 == 'n' ) )
                  {
                     alt39 = 18;
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           39,
                                                                           37,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
                  break;
               case 'u':
               {
                  alt39 = 18;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        39,
                                                                        8,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case 'n':
         {
            switch ( input.LA( 2 ) )
            {
               case 'i':
               {
                  alt39 = 9;
               }
                  break;
               case 'o':
               {
                  alt39 = 18;
               }
                  break;
               case 'e':
               {
                  alt39 = 20;
               }
                  break;
               case 'a':
               case '\u00E4':
               {
                  alt39 = 13;
               }
                  break;
               default :
                  alt39 = 27;
            }
            
         }
            break;
         case 'h':
         {
            alt39 = 10;
         }
            break;
         case 'm':
         {
            switch ( input.LA( 2 ) )
            {
               case 'o':
               {
                  switch ( input.LA( 3 ) )
                  {
                     case 'r':
                     {
                        alt39 = 11;
                     }
                        break;
                     case 'n':
                     {
                        int LA39_47 = input.LA( 4 );
                        
                        if ( ( LA39_47 == 'a' ) )
                        {
                           alt39 = 15;
                        }
                        else if ( ( LA39_47 == 't' ) )
                        {
                           alt39 = 19;
                        }
                        else
                        {
                           NoViableAltException nvae = new NoViableAltException( "",
                                                                                 39,
                                                                                 47,
                                                                                 input );
                           
                           throw nvae;
                           
                        }
                     }
                        break;
                     default :
                        alt39 = 19;
                  }
                  
               }
                  break;
               case 'r':
               {
                  alt39 = 11;
               }
                  break;
               case 'a':
               case '\u00E4':
               {
                  alt39 = 18;
               }
                  break;
               case 'i':
               {
                  alt39 = 19;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        39,
                                                                        11,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case 'g':
         {
            alt39 = 12;
         }
            break;
         case 'w':
         {
            alt39 = 16;
         }
            break;
         case 't':
         {
            alt39 = 17;
         }
            break;
         case 'f':
         {
            switch ( input.LA( 2 ) )
            {
               case 'e':
               {
                  alt39 = 18;
               }
                  break;
               case 'r':
               {
                  alt39 = 19;
               }
                  break;
               case 'u':
               case '\u00FC':
               {
                  alt39 = 20;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        39,
                                                                        15,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
         }
            break;
         case 'o':
         {
            alt39 = 18;
         }
            break;
         case 'z':
         {
            alt39 = 20;
         }
            break;
         case '/':
         case '\u5E74':
         case '\u65E5':
         case '\u6708':
         {
            alt39 = 21;
         }
            break;
         case '.':
         {
            alt39 = 22;
         }
            break;
         case ':':
         {
            alt39 = 23;
         }
            break;
         case '-':
         {
            alt39 = 24;
         }
            break;
         case ',':
         {
            alt39 = 25;
         }
            break;
         case '@':
         {
            alt39 = 26;
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
            alt39 = 28;
         }
            break;
         case '\t':
         case '\n':
         case '\r':
         case ' ':
         {
            alt39 = 29;
         }
            break;
         default :
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  39,
                                                                  0,
                                                                  input );
            
            throw nvae;
            
      }
      
      switch ( alt39 )
      {
         case 1:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:10:
         // A
         {
            mA();
            
         }
            break;
         case 2:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:12:
         // ON
         {
            mON();
            
         }
            break;
         case 3:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:15:
         // OF
         {
            mOF();
            
         }
            break;
         case 4:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:18:
         // OF_THE
         {
            mOF_THE();
            
         }
            break;
         case 5:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:25:
         // IN
         {
            mIN();
            
         }
            break;
         case 6:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:28:
         // AND
         {
            mAND();
            
         }
            break;
         case 7:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:32:
         // END
         {
            mEND();
            
         }
            break;
         case 8:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:36:
         // NOW
         {
            mNOW();
            
         }
            break;
         case 9:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:40:
         // NEVER
         {
            mNEVER();
            
         }
            break;
         case 10:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:46:
         // TODAY
         {
            mTODAY();
            
         }
            break;
         case 11:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:52:
         // TOMORROW
         {
            mTOMORROW();
            
         }
            break;
         case 12:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:61:
         // YESTERDAY
         {
            mYESTERDAY();
            
         }
            break;
         case 13:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:71:
         // NEXT
         {
            mNEXT();
            
         }
            break;
         case 14:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:76:
         // YEARS
         {
            mYEARS();
            
         }
            break;
         case 15:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:82:
         // MONTHS
         {
            mMONTHS();
            
         }
            break;
         case 16:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:89:
         // WEEKS
         {
            mWEEKS();
            
         }
            break;
         case 17:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:95:
         // DAYS
         {
            mDAYS();
            
         }
            break;
         case 18:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:100:
         // MONTH
         {
            mMONTH();
            
         }
            break;
         case 19:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:106:
         // WEEKDAY
         {
            mWEEKDAY();
            
         }
            break;
         case 20:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:114:
         // NUM_STR
         {
            mNUM_STR();
            
         }
            break;
         case 21:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:122:
         // DATE_SEP
         {
            mDATE_SEP();
            
         }
            break;
         case 22:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:131:
         // DOT
         {
            mDOT();
            
         }
            break;
         case 23:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:135:
         // COLON
         {
            mCOLON();
            
         }
            break;
         case 24:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:141:
         // MINUS
         {
            mMINUS();
            
         }
            break;
         case 25:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:147:
         // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 26:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:153:
         // DATE_TIME_SEPARATOR
         {
            mDATE_TIME_SEPARATOR();
            
         }
            break;
         case 27:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:173:
         // SUFF_FMALE
         {
            mSUFF_FMALE();
            
         }
            break;
         case 28:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:184:
         // INT
         {
            mINT();
            
         }
            break;
         case 29:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:188:
         // WS
         {
            mWS();
            
         }
            break;
      
      }
      
   }
   
}
