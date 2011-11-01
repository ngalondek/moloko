// $ANTLR 3.3 Nov 30, 2010 12:45:30 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g 2011-11-01 15:28:17

package dev.drsoran.moloko.grammar.datetime.de;

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


public class DateLexer extends Lexer
{
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
   
   public static final int OF_THE = 18;
   
   public static final int MONTH = 19;
   
   public static final int NEXT = 20;
   
   public static final int WEEKDAY = 21;
   
   public static final int IN = 22;
   
   public static final int AND = 23;
   
   public static final int COMMA = 24;
   
   public static final int END = 25;
   
   public static final int TODAY = 26;
   
   public static final int NEVER = 27;
   
   public static final int TOMORROW = 28;
   
   public static final int YESTERDAY = 29;
   
   public static final int SUFF_MALE = 30;
   
   public static final int SUFF_FMALE = 31;
   
   public static final int DATE_TIME_SEPARATOR = 32;
   
   public static final int WS = 33;
   
   
   
   @Override
   public void reportError( RecognitionException e )
   {
      throw new LexerException( e );
   }
   
   
   
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
   
   
   
   @Override
   public String getGrammarFileName()
   {
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g";
   }
   
   
   
   // $ANTLR start "A"
   public final void mA() throws RecognitionException
   {
      try
      {
         int _type = A;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:386:11:
         // ( 'ein' ( 'e' | 'er' | 'es' | 'em' | 'en' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:386:13:
         // 'ein' ( 'e' | 'er' | 'es' | 'em' | 'en' )?
         {
            match( "ein" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:386:18:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:386:19:
               // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:386:23:
               // 'er'
               {
                  match( "er" );
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:386:28:
               // 'es'
               {
                  match( "es" );
                  
               }
                  break;
               case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:386:33:
               // 'em'
               {
                  match( "em" );
                  
               }
                  break;
               case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:386:38:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:388:11:
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:388:13:
            // 'am'
            {
               match( "am" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:388:20:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:390:11:
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:390:13:
            // 'von'
            {
               match( "von" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:390:21:
            // 'vom'
            {
               match( "vom" );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:390:29:
            // 'ab'
            {
               match( "ab" );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:390:36:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:392:11:
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:392:13:
            // 'der'
            {
               match( "der" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:392:21:
            // 'die'
            {
               match( "die" );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:392:29:
            // 'das'
            {
               match( "das" );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:392:37:
            // 'dem'
            {
               match( "dem" );
               
            }
               break;
            case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:392:45:
            // 'den'
            {
               match( "den" );
               
            }
               break;
            case 6:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:392:53:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:394:11:
         // ( 'in' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:394:13:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:396:11:
         // ( 'und' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:396:13:
         // 'und'
         {
            match( "und" );
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:398:11:
         // ( 'ende' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:398:13:
         // 'ende'
         {
            match( "ende" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:400:11:
         // ( 'jetzt' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:400:13:
         // 'jetzt'
         {
            match( "jetzt" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:402:11:
         // ( 'nie' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:402:13:
         // 'nie'
         {
            match( "nie" );
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:404:11:
         // ( 'heute' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:404:13:
         // 'heute'
         {
            match( "heute" );
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:406:11:
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:406:13:
            // 'morgen'
            {
               match( "morgen" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:406:24:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:408:11:
         // ( 'gestern' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:408:13:
         // 'gestern'
         {
            match( "gestern" );
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:11:
         // ( 'n' ( 'a' | 'ae' | 'ä' ) 'chst' ( 'e' | 'er' | 'es' | 'en' | 'em' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:13:
         // 'n' ( 'a' | 'ae' | 'ä' ) 'chst' ( 'e' | 'er' | 'es' | 'en' | 'em' )
         {
            match( 'n' );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:16:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:17:
               // 'a'
               {
                  match( 'a' );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:21:
               // 'ae'
               {
                  match( "ae" );
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:26:
               // 'ä'
               {
                  match( '\u00E4' );
                  
               }
                  break;
            
            }
            
            match( "chst" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:37:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:38:
               // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:42:
               // 'er'
               {
                  match( "er" );
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:47:
               // 'es'
               {
                  match( "es" );
                  
               }
                  break;
               case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:52:
               // 'en'
               {
                  match( "en" );
                  
               }
                  break;
               case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:410:57:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:412:11:
         // ( 'jahr' ( SUFF_MALE )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:412:13:
         // 'jahr' ( SUFF_MALE )?
         {
            match( "jahr" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:412:19:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:412:19:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:414:11:
         // ( 'monat' ( SUFF_MALE )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:414:13:
         // 'monat' ( SUFF_MALE )?
         {
            match( "monat" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:414:20:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:414:20:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:416:11:
         // ( 'woche' ( SUFF_FMALE )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:416:13:
         // 'woche' ( SUFF_FMALE )?
         {
            match( "woche" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:416:20:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:416:20:
               // SUFF_FMALE
               {
                  mSUFF_FMALE();
                  
               }
                  break;
            
            }
            
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:418:11:
         // ( 'tag' ( SUFF_MALE )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:418:13:
         // 'tag' ( SUFF_MALE )?
         {
            match( "tag" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:418:18:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:418:18:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:420:11:
         // ( 'jan' | 'januar' ( SUFF_MALE )? | 'feb' | 'februar' ( SUFF_MALE )? | 'm' ( 'a' | 'ä' | 'ae' ) 'r' | 'm' (
         // 'a' | 'ä' | 'ae' ) 'rz' ( SUFF_MALE )? | 'mai' ( SUFF_FMALE )? | 'jun' | 'juni' ( SUFF_MALE )? | 'jul' |
         // 'juli' ( SUFF_MALE )? | 'aug' | 'august' ( SUFF_MALE )? | 'sep' | 'september' ( SUFF_MALE )? | 'okt' |
         // 'oktober' ( SUFF_MALE )? | 'nov' | 'november' ( SUFF_MALE )? | 'dez' | 'dezember' ( SUFF_MALE )? )
         int alt25 = 21;
         alt25 = dfa25.predict( input );
         switch ( alt25 )
         {
            case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:420:15:
            // 'jan'
            {
               match( "jan" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:420:38:
            // 'januar' ( SUFF_MALE )?
            {
               match( "januar" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:420:46:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:420:46:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:421:15:
            // 'feb'
            {
               match( "feb" );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:421:38:
            // 'februar' ( SUFF_MALE )?
            {
               match( "februar" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:421:47:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:421:47:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:15:
            // 'm' ( 'a' | 'ä' | 'ae' ) 'r'
            {
               match( 'm' );
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:18:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:19:
                  // 'a'
                  {
                     match( 'a' );
                     
                  }
                     break;
                  case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:23:
                  // 'ä'
                  {
                     match( '\u00E4' );
                     
                  }
                     break;
                  case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:27:
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:38:
            // 'm' ( 'a' | 'ä' | 'ae' ) 'rz' ( SUFF_MALE )?
            {
               match( 'm' );
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:41:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:42:
                  // 'a'
                  {
                     match( 'a' );
                     
                  }
                     break;
                  case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:46:
                  // 'ä'
                  {
                     match( '\u00E4' );
                     
                  }
                     break;
                  case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:50:
                  // 'ae'
                  {
                     match( "ae" );
                     
                  }
                     break;
               
               }
               
               match( "rz" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:59:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:422:59:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 7:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:15:
            // 'mai' ( SUFF_FMALE )?
            {
               match( "mai" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:20:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:20:
                  // SUFF_FMALE
                  {
                     mSUFF_FMALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 8:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:424:15:
            // 'jun'
            {
               match( "jun" );
               
            }
               break;
            case 9:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:424:38:
            // 'juni' ( SUFF_MALE )?
            {
               match( "juni" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:424:44:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:424:44:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 10:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:15:
            // 'jul'
            {
               match( "jul" );
               
            }
               break;
            case 11:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:38:
            // 'juli' ( SUFF_MALE )?
            {
               match( "juli" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:44:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:44:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 12:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:426:15:
            // 'aug'
            {
               match( "aug" );
               
            }
               break;
            case 13:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:426:38:
            // 'august' ( SUFF_MALE )?
            {
               match( "august" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:426:46:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:426:46:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 14:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:15:
            // 'sep'
            {
               match( "sep" );
               
            }
               break;
            case 15:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:38:
            // 'september' ( SUFF_MALE )?
            {
               match( "september" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:49:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:49:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 16:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:428:15:
            // 'okt'
            {
               match( "okt" );
               
            }
               break;
            case 17:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:428:38:
            // 'oktober' ( SUFF_MALE )?
            {
               match( "oktober" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:428:47:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:428:47:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 18:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:15:
            // 'nov'
            {
               match( "nov" );
               
            }
               break;
            case 19:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:38:
            // 'november' ( SUFF_MALE )?
            {
               match( "november" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:48:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:48:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 20:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:430:15:
            // 'dez'
            {
               match( "dez" );
               
            }
               break;
            case 21:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:430:38:
            // 'dezember' ( SUFF_MALE )?
            {
               match( "dezember" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:430:48:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:430:48:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:432:11:
         // ( 'mo' | 'montag' ( SUFF_MALE )? | 'di' | 'dienstag' ( SUFF_MALE )? | 'mi' | 'mittwoch' ( SUFF_MALE )? |
         // 'do' | 'donnestag' ( SUFF_MALE )? | 'fr' | 'freitag' ( SUFF_MALE )? | 'sa' | 'samstag' ( SUFF_MALE )? | 'so'
         // | 'sonntag' ( SUFF_MALE )? )
         int alt33 = 14;
         alt33 = dfa33.predict( input );
         switch ( alt33 )
         {
            case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:432:16:
            // 'mo'
            {
               match( "mo" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:432:23:
            // 'montag' ( SUFF_MALE )?
            {
               match( "montag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:432:31:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:432:31:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:16:
            // 'di'
            {
               match( "di" );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:23:
            // 'dienstag' ( SUFF_MALE )?
            {
               match( "dienstag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:33:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:33:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:16:
            // 'mi'
            {
               match( "mi" );
               
            }
               break;
            case 6:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:23:
            // 'mittwoch' ( SUFF_MALE )?
            {
               match( "mittwoch" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:33:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:33:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 7:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:16:
            // 'do'
            {
               match( "do" );
               
            }
               break;
            case 8:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:23:
            // 'donnestag' ( SUFF_MALE )?
            {
               match( "donnestag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:34:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:34:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 9:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:436:16:
            // 'fr'
            {
               match( "fr" );
               
            }
               break;
            case 10:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:436:23:
            // 'freitag' ( SUFF_MALE )?
            {
               match( "freitag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:436:32:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:436:32:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 11:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:16:
            // 'sa'
            {
               match( "sa" );
               
            }
               break;
            case 12:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:23:
            // 'samstag' ( SUFF_MALE )?
            {
               match( "samstag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:32:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:32:
                  // SUFF_MALE
                  {
                     mSUFF_MALE();
                     
                  }
                     break;
               
               }
               
            }
               break;
            case 13:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:438:16:
            // 'so'
            {
               match( "so" );
               
            }
               break;
            case 14:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:438:23:
            // 'sonntag' ( SUFF_MALE )?
            {
               match( "sonntag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:438:32:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:438:32:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:11:
         // ( 'eins' | 'zwei' | 'drei' | 'vier' | 'f' ( 'u' | 'ü' | 'ue' ) 'nf' | 'sechs' | 'sieben' | 'acht' | 'neun' |
         // 'zehn' )
         int alt35 = 10;
         alt35 = dfa35.predict( input );
         switch ( alt35 )
         {
            case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:13:
            // 'eins'
            {
               match( "eins" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:23:
            // 'zwei'
            {
               match( "zwei" );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:34:
            // 'drei'
            {
               match( "drei" );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:43:
            // 'vier'
            {
               match( "vier" );
               
            }
               break;
            case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:52:
            // 'f' ( 'u' | 'ü' | 'ue' ) 'nf'
            {
               match( 'f' );
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:55:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:56:
                  // 'u'
                  {
                     match( 'u' );
                     
                  }
                     break;
                  case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:60:
                  // 'ü'
                  {
                     match( '\u00FC' );
                     
                  }
                     break;
                  case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:64:
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:441:13:
            // 'sechs'
            {
               match( "sechs" );
               
            }
               break;
            case 7:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:441:23:
            // 'sieben'
            {
               match( "sieben" );
               
            }
               break;
            case 8:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:441:34:
            // 'acht'
            {
               match( "acht" );
               
            }
               break;
            case 9:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:441:43:
            // 'neun'
            {
               match( "neun" );
               
            }
               break;
            case 10:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:441:52:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:443:11:
         // ( '/' | '\\u5E74' | '\\u6708' | '\\u65E5' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:445:11:
         // ( '.' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:445:13:
         // '.'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:447:11:
         // ( ':' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:447:13:
         // ':'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:449:11:
         // ( '-' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:449:13:
         // '-'
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
   
   // $ANTLR start "COMMA"
   public final void mCOMMA() throws RecognitionException
   {
      try
      {
         int _type = COMMA;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:451:11:
         // ( ',' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:451:13:
         // ','
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
   
   // $ANTLR start "DATE_TIME_SEPARATOR"
   public final void mDATE_TIME_SEPARATOR() throws RecognitionException
   {
      try
      {
         int _type = DATE_TIME_SEPARATOR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:21:
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:23:
            // '@'
            {
               match( '@' );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:29:
            // 'um'
            {
               match( "um" );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:453:36:
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
      }
   }
   
   
   
   // $ANTLR end "DATE_TIME_SEPARATOR"
   
   // $ANTLR start "SUFF_MALE"
   public final void mSUFF_MALE() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:456:11:
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:456:13:
            // 'e'
            {
               match( 'e' );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:456:17:
            // 's'
            {
               match( 's' );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:456:21:
            // 'es'
            {
               match( "es" );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:456:26:
            // 'en'
            {
               match( "en" );
               
            }
               break;
         
         }
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:458:12:
         // ( 'n' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:458:14:
         // 'n'
         {
            match( 'n' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:460:11:
         // ( ( '0' .. '9' )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:460:13:
         // ( '0' .. '9' )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:460:13:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:460:13:
                  // '0' .. '9'
                  {
                     matchRange( '0', '9' );
                     
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:462:11:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:462:13:
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
      }
   }
   
   
   
   // $ANTLR end "WS"
   
   @Override
   public void mTokens() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:8: ( A |
      // ON | OF | OF_THE | IN | AND | END | NOW | NEVER | TODAY | TOMORROW | YESTERDAY | NEXT | YEARS | MONTHS | WEEKS
      // | DAYS | MONTH | WEEKDAY | NUM_STR | DATE_SEP | DOT | COLON | MINUS | COMMA | DATE_TIME_SEPARATOR | SUFF_FMALE
      // | INT | WS )
      int alt39 = 29;
      alt39 = dfa39.predict( input );
      switch ( alt39 )
      {
         case 1:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:10: A
         {
            mA();
            
         }
            break;
         case 2:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:12:
         // ON
         {
            mON();
            
         }
            break;
         case 3:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:15:
         // OF
         {
            mOF();
            
         }
            break;
         case 4:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:18:
         // OF_THE
         {
            mOF_THE();
            
         }
            break;
         case 5:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:25:
         // IN
         {
            mIN();
            
         }
            break;
         case 6:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:28:
         // AND
         {
            mAND();
            
         }
            break;
         case 7:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:32:
         // END
         {
            mEND();
            
         }
            break;
         case 8:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:36:
         // NOW
         {
            mNOW();
            
         }
            break;
         case 9:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:40:
         // NEVER
         {
            mNEVER();
            
         }
            break;
         case 10:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:46:
         // TODAY
         {
            mTODAY();
            
         }
            break;
         case 11:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:52:
         // TOMORROW
         {
            mTOMORROW();
            
         }
            break;
         case 12:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:61:
         // YESTERDAY
         {
            mYESTERDAY();
            
         }
            break;
         case 13:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:71:
         // NEXT
         {
            mNEXT();
            
         }
            break;
         case 14:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:76:
         // YEARS
         {
            mYEARS();
            
         }
            break;
         case 15:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:82:
         // MONTHS
         {
            mMONTHS();
            
         }
            break;
         case 16:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:89:
         // WEEKS
         {
            mWEEKS();
            
         }
            break;
         case 17:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:95:
         // DAYS
         {
            mDAYS();
            
         }
            break;
         case 18:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:100:
         // MONTH
         {
            mMONTH();
            
         }
            break;
         case 19:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:106:
         // WEEKDAY
         {
            mWEEKDAY();
            
         }
            break;
         case 20:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:114:
         // NUM_STR
         {
            mNUM_STR();
            
         }
            break;
         case 21:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:122:
         // DATE_SEP
         {
            mDATE_SEP();
            
         }
            break;
         case 22:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:131:
         // DOT
         {
            mDOT();
            
         }
            break;
         case 23:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:135:
         // COLON
         {
            mCOLON();
            
         }
            break;
         case 24:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:141:
         // MINUS
         {
            mMINUS();
            
         }
            break;
         case 25:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:147:
         // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 26:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:153:
         // DATE_TIME_SEPARATOR
         {
            mDATE_TIME_SEPARATOR();
            
         }
            break;
         case 27:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:173:
         // SUFF_FMALE
         {
            mSUFF_FMALE();
            
         }
            break;
         case 28:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:184:
         // INT
         {
            mINT();
            
         }
            break;
         case 29:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:188:
         // WS
         {
            mWS();
            
         }
            break;
      
      }
      
   }
   
   protected DFA25 dfa25 = new DFA25( this );
   
   protected DFA33 dfa33 = new DFA33( this );
   
   protected DFA35 dfa35 = new DFA35( this );
   
   protected DFA39 dfa39 = new DFA39( this );
   
   static final String DFA25_eotS = "\23\uffff\1\40\1\42\1\44\1\46\2\uffff\1\50\1\52\1\54\1\56\1\60"
      + "\1\62\24\uffff";
   
   static final String DFA25_eofS = "\63\uffff";
   
   static final String DFA25_minS = "\2\141\1\145\1\141\1\165\1\145\1\153\1\157\1\145\1\156\1\154\1"
      + "\142\1\145\1\162\1\147\1\160\1\164\1\166\1\172\1\165\2\151\1\162"
      + "\1\uffff\1\162\1\172\1\165\1\164\1\157\2\145\24\uffff";
   
   static final String DFA25_maxS = "\1\163\1\165\1\145\1\u00e4\1\165\1\145\1\153\1\157\1\145\2\156"
      + "\1\142\2\162\1\147\1\160\1\164\1\166\1\172\1\165\2\151\1\162\1\uffff"
      + "\1\162\1\172\1\165\1\164\1\157\2\145\24\uffff";
   
   static final String DFA25_acceptS = "\27\uffff\1\7\7\uffff\1\2\1\1\1\11\1\10\1\13\1\12\1\4\1\3\1\6\1"
      + "\5\1\15\1\14\1\17\1\16\1\21\1\20\1\23\1\22\1\25\1\24";
   
   static final String DFA25_specialS = "\63\uffff}>";
   
   static final String[] DFA25_transitionS =
   {
    "\1\4\2\uffff\1\10\1\uffff\1\2\3\uffff\1\1\2\uffff\1\3\1\7\1"
       + "\6\3\uffff\1\5", "\1\11\23\uffff\1\12", "\1\13",
    "\1\14\u0082\uffff\1\15", "\1\16", "\1\17", "\1\20", "\1\21", "\1\22",
    "\1\23", "\1\25\1\uffff\1\24", "\1\26", "\1\30\3\uffff\1\27\10\uffff\1\31",
    "\1\31", "\1\32", "\1\33", "\1\34", "\1\35", "\1\36", "\1\37", "\1\41",
    "\1\43", "\1\45", "", "\1\31", "\1\47", "\1\51", "\1\53", "\1\55", "\1\57",
    "\1\61", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
    "", "", "", "" };
   
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
         return "420:1: MONTH : ( 'jan' | 'januar' ( SUFF_MALE )? | 'feb' | 'februar' ( SUFF_MALE )? | 'm' ( 'a' | 'ä' | 'ae' ) 'r' | 'm' ( 'a' | 'ä' | 'ae' ) 'rz' ( SUFF_MALE )? | 'mai' ( SUFF_FMALE )? | 'jun' | 'juni' ( SUFF_MALE )? | 'jul' | 'juli' ( SUFF_MALE )? | 'aug' | 'august' ( SUFF_MALE )? | 'sep' | 'september' ( SUFF_MALE )? | 'okt' | 'oktober' ( SUFF_MALE )? | 'nov' | 'november' ( SUFF_MALE )? | 'dez' | 'dezember' ( SUFF_MALE )? );";
      }
   }
   
   static final String DFA33_eotS = "\5\uffff\1\15\1\17\1\21\1\23\1\25\1\27\1\31\16\uffff";
   
   static final String DFA33_eofS = "\32\uffff";
   
   static final String DFA33_minS = "\1\144\2\151\1\162\1\141\1\156\1\164\1\145\1\156\1\145\1\155\1"
      + "\156\16\uffff";
   
   static final String DFA33_maxS = "\1\163\2\157\1\162\1\157\1\156\1\164\1\145\1\156\1\145\1\155\1"
      + "\156\16\uffff";
   
   static final String DFA33_acceptS = "\14\uffff\1\2\1\1\1\6\1\5\1\4\1\3\1\10\1\7\1\12\1\11\1\14\1\13"
      + "\1\16\1\15";
   
   static final String DFA33_specialS = "\32\uffff}>";
   
   static final String[] DFA33_transitionS =
   { "\1\2\1\uffff\1\3\6\uffff\1\1\5\uffff\1\4", "\1\6\5\uffff\1\5",
    "\1\7\5\uffff\1\10", "\1\11", "\1\12\15\uffff\1\13", "\1\14", "\1\16",
    "\1\20", "\1\22", "\1\24", "\1\26", "\1\30", "", "", "", "", "", "", "",
    "", "", "", "", "", "", "" };
   
   static final short[] DFA33_eot = DFA.unpackEncodedString( DFA33_eotS );
   
   static final short[] DFA33_eof = DFA.unpackEncodedString( DFA33_eofS );
   
   static final char[] DFA33_min = DFA.unpackEncodedStringToUnsignedChars( DFA33_minS );
   
   static final char[] DFA33_max = DFA.unpackEncodedStringToUnsignedChars( DFA33_maxS );
   
   static final short[] DFA33_accept = DFA.unpackEncodedString( DFA33_acceptS );
   
   static final short[] DFA33_special = DFA.unpackEncodedString( DFA33_specialS );
   
   static final short[][] DFA33_transition;
   
   static
   {
      int numStates = DFA33_transitionS.length;
      DFA33_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA33_transition[ i ] = DFA.unpackEncodedString( DFA33_transitionS[ i ] );
      }
   }
   
   
   class DFA33 extends DFA
   {
      
      public DFA33( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 33;
         this.eot = DFA33_eot;
         this.eof = DFA33_eof;
         this.min = DFA33_min;
         this.max = DFA33_max;
         this.accept = DFA33_accept;
         this.special = DFA33_special;
         this.transition = DFA33_transition;
      }
      
      
      
      @Override
      public String getDescription()
      {
         return "432:1: WEEKDAY : ( 'mo' | 'montag' ( SUFF_MALE )? | 'di' | 'dienstag' ( SUFF_MALE )? | 'mi' | 'mittwoch' ( SUFF_MALE )? | 'do' | 'donnestag' ( SUFF_MALE )? | 'fr' | 'freitag' ( SUFF_MALE )? | 'sa' | 'samstag' ( SUFF_MALE )? | 'so' | 'sonntag' ( SUFF_MALE )? );";
      }
   }
   
   static final String DFA35_eotS = "\15\uffff";
   
   static final String DFA35_eofS = "\15\uffff";
   
   static final String DFA35_minS = "\1\141\1\uffff\1\145\3\uffff\1\145\6\uffff";
   
   static final String DFA35_maxS = "\1\172\1\uffff\1\167\3\uffff\1\151\6\uffff";
   
   static final String DFA35_acceptS = "\1\uffff\1\1\1\uffff\1\3\1\4\1\5\1\uffff\1\10\1\11\1\2\1\12\1\6"
      + "\1\7";
   
   static final String DFA35_specialS = "\15\uffff}>";
   
   static final String[] DFA35_transitionS =
   {
    "\1\7\2\uffff\1\3\1\1\1\5\7\uffff\1\10\4\uffff\1\6\2\uffff\1"
       + "\4\3\uffff\1\2", "", "\1\12\21\uffff\1\11", "", "", "",
    "\1\13\3\uffff\1\14", "", "", "", "", "", "" };
   
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
         return "440:1: NUM_STR : ( 'eins' | 'zwei' | 'drei' | 'vier' | 'f' ( 'u' | 'ü' | 'ue' ) 'nf' | 'sechs' | 'sieben' | 'acht' | 'neun' | 'zehn' );";
      }
   }
   
   static final String DFA39_eotS = "\11\uffff\1\50\27\uffff\1\37\7\uffff\1\37\2\uffff\1\60\1\42\4\uffff";
   
   static final String DFA39_eofS = "\62\uffff";
   
   static final String DFA39_minS = "\1\11\1\151\1\142\1\151\2\141\1\uffff\1\155\2\141\1\uffff\1\141"
      + "\3\uffff\1\145\12\uffff\1\156\3\uffff\1\143\1\uffff\1\155\1\145"
      + "\3\uffff\1\150\3\uffff\1\156\2\uffff\1\163\1\156\1\uffff\1\141\2"
      + "\uffff";
   
   static final String DFA39_maxS = "\1\u6708\1\156\1\165\2\157\1\162\1\uffff\1\156\1\165\1\u00e4\1"
      + "\uffff\1\u00e4\3\uffff\1\u00fc\12\uffff\1\156\3\uffff\1\160\1\uffff"
      + "\1\172\1\145\3\uffff\1\156\3\uffff\1\162\2\uffff\1\163\1\156\1\uffff"
      + "\1\164\2\uffff";
   
   static final String DFA39_acceptS = "\6\uffff\1\5\3\uffff\1\12\1\uffff\1\14\1\20\1\21\1\uffff\1\22\1"
      + "\24\1\25\1\26\1\27\1\30\1\31\1\32\1\34\1\35\1\uffff\1\7\1\2\1\3"
      + "\1\uffff\1\23\2\uffff\1\4\1\6\1\10\1\uffff\1\11\1\15\1\33\1\uffff"
      + "\1\13\1\31\2\uffff\1\16\1\uffff\1\1\1\17";
   
   static final String DFA39_specialS = "\62\uffff}>";
   
   static final String[] DFA39_transitionS =
   {
    "\2\31\2\uffff\1\31\22\uffff\1\31\13\uffff\1\26\1\25\1\23\1"
       + "\22\12\30\1\24\5\uffff\1\27\40\uffff\1\2\2\uffff\1\5\1\1\1\17"
       + "\1\14\1\12\1\6\1\10\2\uffff\1\13\1\11\1\20\3\uffff\1\4\1\16"
       + "\1\7\1\3\1\15\2\uffff\1\21\u5df9\uffff\1\22\u0770\uffff\1\22"
       + "\u0122\uffff\1\22", "\1\32\4\uffff\1\33",
    "\1\35\1\21\11\uffff\2\34\6\uffff\1\20", "\1\21\5\uffff\1\35",
    "\1\37\3\uffff\1\36\3\uffff\1\21\5\uffff\1\37",
    "\1\42\3\uffff\1\40\3\uffff\1\41\5\uffff\1\37\2\uffff\1\21", "",
    "\1\27\1\43", "\1\45\3\uffff\1\44\17\uffff\1\20",
    "\1\47\3\uffff\1\21\3\uffff\1\46\5\uffff\1\20\164\uffff\1\47", "",
    "\1\20\7\uffff\1\37\5\uffff\1\51\2\uffff\1\52\161\uffff\1\20", "", "", "",
    "\1\20\14\uffff\1\37\2\uffff\1\21\u0086\uffff\1\21", "", "", "", "", "",
    "", "", "", "", "", "\1\54", "", "", "", "\1\21\5\uffff\1\35\6\uffff\1\20",
    "", "\2\42\3\uffff\2\42\6\uffff\1\20", "\1\55", "", "", "",
    "\1\56\5\uffff\1\20", "", "", "", "\1\57\3\uffff\1\52", "", "", "\1\21",
    "\1\37", "", "\1\61\22\uffff\1\37", "", "" };
   
   static final short[] DFA39_eot = DFA.unpackEncodedString( DFA39_eotS );
   
   static final short[] DFA39_eof = DFA.unpackEncodedString( DFA39_eofS );
   
   static final char[] DFA39_min = DFA.unpackEncodedStringToUnsignedChars( DFA39_minS );
   
   static final char[] DFA39_max = DFA.unpackEncodedStringToUnsignedChars( DFA39_maxS );
   
   static final short[] DFA39_accept = DFA.unpackEncodedString( DFA39_acceptS );
   
   static final short[] DFA39_special = DFA.unpackEncodedString( DFA39_specialS );
   
   static final short[][] DFA39_transition;
   
   static
   {
      int numStates = DFA39_transitionS.length;
      DFA39_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA39_transition[ i ] = DFA.unpackEncodedString( DFA39_transitionS[ i ] );
      }
   }
   
   
   class DFA39 extends DFA
   {
      
      public DFA39( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 39;
         this.eot = DFA39_eot;
         this.eof = DFA39_eof;
         this.min = DFA39_min;
         this.max = DFA39_max;
         this.accept = DFA39_accept;
         this.special = DFA39_special;
         this.transition = DFA39_transition;
      }
      
      
      
      @Override
      public String getDescription()
      {
         return "1:1: Tokens : ( A | ON | OF | OF_THE | IN | AND | END | NOW | NEVER | TODAY | TOMORROW | YESTERDAY | NEXT | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY | NUM_STR | DATE_SEP | DOT | COLON | MINUS | COMMA | DATE_TIME_SEPARATOR | SUFF_FMALE | INT | WS );";
      }
   }
   
}
