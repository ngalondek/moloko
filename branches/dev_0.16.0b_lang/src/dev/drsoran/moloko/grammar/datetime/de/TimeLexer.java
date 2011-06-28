// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g 2011-06-21 17:33:53

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


public class TimeLexer extends Lexer
{
   public static final int MINUTES = 13;
   
   public static final int COLON = 10;
   
   public static final int AT = 4;
   
   public static final int DAYS = 15;
   
   public static final int MIDDAY = 8;
   
   public static final int WS = 17;
   
   public static final int COMMA = 5;
   
   public static final int SECONDS = 14;
   
   public static final int INT = 9;
   
   public static final int MIDNIGHT = 7;
   
   public static final int NEVER = 6;
   
   public static final int DOT = 11;
   
   public static final int AND = 16;
   
   public static final int EOF = -1;
   
   public static final int HOURS = 12;
   
   
   
   @Override
   public void reportError( RecognitionException e )
   {
      throw new LexerException( e );
   }
   
   
   
   // delegates
   // delegators
   
   public TimeLexer()
   {
      ;
   }
   
   
   
   public TimeLexer( CharStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   
   
   
   public TimeLexer( CharStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   
   
   
   public String getGrammarFileName()
   {
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g";
   }
   
   
   
   // $ANTLR start "AT"
   public final void mAT() throws RecognitionException
   {
      try
      {
         int _type = AT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:323:11:
         // ( '@' | 'um' | 'am' | 'an' )
         int alt1 = 4;
         switch ( input.LA( 1 ) )
         {
            case '@':
            {
               alt1 = 1;
            }
               break;
            case 'u':
            {
               alt1 = 2;
            }
               break;
            case 'a':
            {
               int LA1_3 = input.LA( 2 );
               
               if ( ( LA1_3 == 'm' ) )
               {
                  alt1 = 3;
               }
               else if ( ( LA1_3 == 'n' ) )
               {
                  alt1 = 4;
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        1,
                                                                        3,
                                                                        input );
                  
                  throw nvae;
               }
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:323:13:
            // '@'
            {
               match( '@' );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:323:19:
            // 'um'
            {
               match( "um" );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:323:26:
            // 'am'
            {
               match( "am" );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:323:33:
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
   
   
   
   // $ANTLR end "AT"
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException
   {
      try
      {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:325:11:
         // ( 'und' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:325:13:
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
   
   // $ANTLR start "NEVER"
   public final void mNEVER() throws RecognitionException
   {
      try
      {
         int _type = NEVER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:327:11:
         // ( 'nie' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:327:13:
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
   
   // $ANTLR start "MIDNIGHT"
   public final void mMIDNIGHT() throws RecognitionException
   {
      try
      {
         int _type = MIDNIGHT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:329:11:
         // ( 'mitternacht' ( 's' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:329:13:
         // 'mitternacht' ( 's' )?
         {
            match( "mitternacht" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:329:26:
            // ( 's' )?
            int alt2 = 2;
            int LA2_0 = input.LA( 1 );
            
            if ( ( LA2_0 == 's' ) )
            {
               alt2 = 1;
            }
            switch ( alt2 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:329:26:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:331:11:
         // ( 'mittag' ( 's' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:331:13:
         // 'mittag' ( 's' )?
         {
            match( "mittag" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:331:21:
            // ( 's' )?
            int alt3 = 2;
            int LA3_0 = input.LA( 1 );
            
            if ( ( LA3_0 == 's' ) )
            {
               alt3 = 1;
            }
            switch ( alt3 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:331:21:
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
      }
   }
   
   
   
   // $ANTLR end "MIDDAY"
   
   // $ANTLR start "DAYS"
   public final void mDAYS() throws RecognitionException
   {
      try
      {
         int _type = DAYS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:333:11:
         // ( 'tage' | 'tag' | 'd' )
         int alt4 = 3;
         int LA4_0 = input.LA( 1 );
         
         if ( ( LA4_0 == 't' ) )
         {
            int LA4_1 = input.LA( 2 );
            
            if ( ( LA4_1 == 'a' ) )
            {
               int LA4_3 = input.LA( 3 );
               
               if ( ( LA4_3 == 'g' ) )
               {
                  int LA4_4 = input.LA( 4 );
                  
                  if ( ( LA4_4 == 'e' ) )
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
                                                                        3,
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
         else if ( ( LA4_0 == 'd' ) )
         {
            alt4 = 3;
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:333:13:
            // 'tage'
            {
               match( "tage" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:333:22:
            // 'tag'
            {
               match( "tag" );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:333:30:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:335:11:
         // ( 'stunden' | 'stunde' | 'std' | 'h' )
         int alt5 = 4;
         alt5 = dfa5.predict( input );
         switch ( alt5 )
         {
            case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:335:13:
            // 'stunden'
            {
               match( "stunden" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:335:26:
            // 'stunde'
            {
               match( "stunde" );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:335:38:
            // 'std'
            {
               match( "std" );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:335:54:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:337:11:
         // ( 'minuten' | 'minute' | 'min' | 'm' )
         int alt6 = 4;
         alt6 = dfa6.predict( input );
         switch ( alt6 )
         {
            case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:337:13:
            // 'minuten'
            {
               match( "minuten" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:337:26:
            // 'minute'
            {
               match( "minute" );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:337:38:
            // 'min'
            {
               match( "min" );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:337:54:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:339:11:
         // ( 'sekunden' | 'sekunde' | 'se' ( 'c' | 'k' ) | 's' )
         int alt7 = 4;
         alt7 = dfa7.predict( input );
         switch ( alt7 )
         {
            case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:339:13:
            // 'sekunden'
            {
               match( "sekunden" );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:339:26:
            // 'sekunde'
            {
               match( "sekunde" );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:339:38:
            // 'se' ( 'c' | 'k' )
            {
               match( "se" );
               
               if ( input.LA( 1 ) == 'c' || input.LA( 1 ) == 'k' )
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:339:54:
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
   
   // $ANTLR start "COMMA"
   public final void mCOMMA() throws RecognitionException
   {
      try
      {
         int _type = COMMA;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:341:11:
         // ( ',' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:341:13:
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
   
   // $ANTLR start "DOT"
   public final void mDOT() throws RecognitionException
   {
      try
      {
         int _type = DOT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:343:11:
         // ( '.' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:343:13:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:345:11:
         // ( ':' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:345:13:
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
   
   // $ANTLR start "INT"
   public final void mINT() throws RecognitionException
   {
      try
      {
         int _type = INT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:347:11:
         // ( ( '0' .. '9' )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:347:13:
         // ( '0' .. '9' )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:347:13:
            // ( '0' .. '9' )+
            int cnt8 = 0;
            loop8: do
            {
               int alt8 = 2;
               int LA8_0 = input.LA( 1 );
               
               if ( ( ( LA8_0 >= '0' && LA8_0 <= '9' ) ) )
               {
                  alt8 = 1;
               }
               
               switch ( alt8 )
               {
                  case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:347:13:
                  // '0' .. '9'
                  {
                     matchRange( '0', '9' );
                     
                  }
                     break;
                  
                  default :
                     if ( cnt8 >= 1 )
                        break loop8;
                     EarlyExitException eee = new EarlyExitException( 8, input );
                     throw eee;
               }
               cnt8++;
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:349:11:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:349:13:
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
   
   public void mTokens() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:8: ( AT
      // | AND | NEVER | MIDNIGHT | MIDDAY | DAYS | HOURS | MINUTES | SECONDS | COMMA | DOT | COLON | INT | WS )
      int alt9 = 14;
      alt9 = dfa9.predict( input );
      switch ( alt9 )
      {
         case 1:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:10:
         // AT
         {
            mAT();
            
         }
            break;
         case 2:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:13:
         // AND
         {
            mAND();
            
         }
            break;
         case 3:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:17:
         // NEVER
         {
            mNEVER();
            
         }
            break;
         case 4:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:23:
         // MIDNIGHT
         {
            mMIDNIGHT();
            
         }
            break;
         case 5:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:32:
         // MIDDAY
         {
            mMIDDAY();
            
         }
            break;
         case 6:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:39:
         // DAYS
         {
            mDAYS();
            
         }
            break;
         case 7:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:44:
         // HOURS
         {
            mHOURS();
            
         }
            break;
         case 8:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:50:
         // MINUTES
         {
            mMINUTES();
            
         }
            break;
         case 9:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:58:
         // SECONDS
         {
            mSECONDS();
            
         }
            break;
         case 10:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:66:
         // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 11:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:72:
         // DOT
         {
            mDOT();
            
         }
            break;
         case 12:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:76:
         // COLON
         {
            mCOLON();
            
         }
            break;
         case 13:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:82:
         // INT
         {
            mINT();
            
         }
            break;
         case 14:
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:86:
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
   
   static final String DFA5_eotS = "\10\uffff\1\12\2\uffff";
   
   static final String DFA5_eofS = "\13\uffff";
   
   static final String DFA5_minS = "\1\150\1\164\1\uffff\1\144\1\156\1\uffff\1\144\1\145\1\156\2\uffff";
   
   static final String DFA5_maxS = "\1\163\1\164\1\uffff\1\165\1\156\1\uffff\1\144\1\145\1\156\2\uffff";
   
   static final String DFA5_acceptS = "\2\uffff\1\4\2\uffff\1\3\3\uffff\1\1\1\2";
   
   static final String DFA5_specialS = "\13\uffff}>";
   
   static final String[] DFA5_transitionS =
   { "\1\2\12\uffff\1\1", "\1\3", "", "\1\5\20\uffff\1\4", "\1\6", "", "\1\7",
    "\1\10", "\1\11", "", "" };
   
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
         return "335:1: HOURS : ( 'stunden' | 'stunde' | 'std' | 'h' );";
      }
   }
   
   static final String DFA6_eotS = "\1\uffff\1\3\2\uffff\1\6\3\uffff\1\12\2\uffff";
   
   static final String DFA6_eofS = "\13\uffff";
   
   static final String DFA6_minS = "\1\155\1\151\1\156\1\uffff\1\165\1\164\1\uffff\1\145\1\156\2\uffff";
   
   static final String DFA6_maxS = "\1\155\1\151\1\156\1\uffff\1\165\1\164\1\uffff\1\145\1\156\2\uffff";
   
   static final String DFA6_acceptS = "\3\uffff\1\4\2\uffff\1\3\2\uffff\1\1\1\2";
   
   static final String DFA6_specialS = "\13\uffff}>";
   
   static final String[] DFA6_transitionS =
   { "\1\1", "\1\2", "\1\4", "", "\1\5", "\1\7", "", "\1\10", "\1\11", "", "" };
   
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
         return "337:1: MINUTES : ( 'minuten' | 'minute' | 'min' | 'm' );";
      }
   }
   
   static final String DFA7_eotS = "\1\uffff\1\3\2\uffff\1\5\4\uffff\1\13\2\uffff";
   
   static final String DFA7_eofS = "\14\uffff";
   
   static final String DFA7_minS = "\1\163\1\145\1\143\1\uffff\1\165\1\uffff\1\156\1\144\1\145\1\156"
      + "\2\uffff";
   
   static final String DFA7_maxS = "\1\163\1\145\1\153\1\uffff\1\165\1\uffff\1\156\1\144\1\145\1\156"
      + "\2\uffff";
   
   static final String DFA7_acceptS = "\3\uffff\1\4\1\uffff\1\3\4\uffff\1\1\1\2";
   
   static final String DFA7_specialS = "\14\uffff}>";
   
   static final String[] DFA7_transitionS =
   { "\1\1", "\1\2", "\1\5\7\uffff\1\4", "", "\1\6", "", "\1\7", "\1\10",
    "\1\11", "\1\12", "", "" };
   
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
         return "339:1: SECONDS : ( 'sekunden' | 'sekunde' | 'se' ( 'c' | 'k' ) | 's' );";
      }
   }
   
   static final String DFA9_eotS = "\4\uffff\1\17\1\uffff\1\20\16\uffff";
   
   static final String DFA9_eofS = "\25\uffff";
   
   static final String DFA9_minS = "\1\11\1\uffff\1\155\1\uffff\1\151\1\uffff\1\164\7\uffff\1\156\2"
      + "\uffff\1\164\1\141\2\uffff";
   
   static final String DFA9_maxS = "\1\165\1\uffff\1\156\1\uffff\1\151\1\uffff\1\164\7\uffff\1\164"
      + "\2\uffff\1\164\1\145\2\uffff";
   
   static final String DFA9_acceptS = "\1\uffff\1\1\1\uffff\1\3\1\uffff\1\6\1\uffff\1\7\1\12\1\13\1\14"
      + "\1\15\1\16\1\2\1\uffff\1\10\1\11\2\uffff\1\4\1\5";
   
   static final String DFA9_specialS = "\25\uffff}>";
   
   static final String[] DFA9_transitionS =
   {
    "\2\14\2\uffff\1\14\22\uffff\1\14\13\uffff\1\10\1\uffff\1\11"
       + "\1\uffff\12\13\1\12\5\uffff\1\1\40\uffff\1\1\2\uffff\1\5\3\uffff"
       + "\1\7\4\uffff\1\4\1\3\4\uffff\1\6\1\5\1\2", "", "\1\1\1\15", "",
    "\1\16", "", "\1\7", "", "", "", "", "", "", "", "\1\17\5\uffff\1\21", "",
    "", "\1\22", "\1\24\3\uffff\1\23", "", "" };
   
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
         return "1:1: Tokens : ( AT | AND | NEVER | MIDNIGHT | MIDDAY | DAYS | HOURS | MINUTES | SECONDS | COMMA | DOT | COLON | INT | WS );";
      }
   }
   
}
