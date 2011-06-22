// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g 2011-06-21 17:32:10

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

import dev.drsoran.moloko.grammar.LexerException;


public class TimeLexer extends Lexer
{
   public static final int MIDDAY = 8;
   
   public static final int SECONDS = 17;
   
   public static final int INT = 10;
   
   public static final int MIDNIGHT = 7;
   
   public static final int AND = 19;
   
   public static final int EOF = -1;
   
   public static final int AT = 4;
   
   public static final int COLON = 11;
   
   public static final int MINUTES = 16;
   
   public static final int DAYS = 18;
   
   public static final int WS = 20;
   
   public static final int COMMA = 5;
   
   public static final int NOON = 9;
   
   public static final int PM = 14;
   
   public static final int NEVER = 6;
   
   public static final int DOT = 12;
   
   public static final int HOURS = 15;
   
   public static final int AM = 13;
   
   

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
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g";
   }
   


   // $ANTLR start "AT"
   public final void mAT() throws RecognitionException
   {
      try
      {
         int _type = AT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:331:11: (
         // '@' | 'at' )
         int alt1 = 2;
         int LA1_0 = input.LA( 1 );
         
         if ( ( LA1_0 == '@' ) )
         {
            alt1 = 1;
         }
         else if ( ( LA1_0 == 'a' ) )
         {
            alt1 = 2;
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:331:13:
               // '@'
            {
               match( '@' );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:331:19:
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
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException
   {
      try
      {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:333:11: (
         // 'and' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:333:13:
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
   
   // $ANTLR start "NEVER"
   public final void mNEVER() throws RecognitionException
   {
      try
      {
         int _type = NEVER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:335:11: (
         // 'never' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:335:13:
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
   
   // $ANTLR start "MIDNIGHT"
   public final void mMIDNIGHT() throws RecognitionException
   {
      try
      {
         int _type = MIDNIGHT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:337:11: (
         // 'midnight' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:337:13:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:339:11: (
         // 'midday' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:339:13:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:341:11: (
         // 'noon' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:341:13:
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
   
   // $ANTLR start "DAYS"
   public final void mDAYS() throws RecognitionException
   {
      try
      {
         int _type = DAYS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:343:11: (
         // 'days' | 'day' | 'd' )
         int alt2 = 3;
         int LA2_0 = input.LA( 1 );
         
         if ( ( LA2_0 == 'd' ) )
         {
            int LA2_1 = input.LA( 2 );
            
            if ( ( LA2_1 == 'a' ) )
            {
               int LA2_2 = input.LA( 3 );
               
               if ( ( LA2_2 == 'y' ) )
               {
                  int LA2_4 = input.LA( 4 );
                  
                  if ( ( LA2_4 == 's' ) )
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
               alt2 = 3;
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:343:13:
               // 'days'
            {
               match( "days" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:343:22:
               // 'day'
            {
               match( "day" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:343:30:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:345:11: (
         // 'hours' | 'hour' | 'hrs' | 'hr' | 'h' )
         int alt3 = 5;
         alt3 = dfa3.predict( input );
         switch ( alt3 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:345:13:
               // 'hours'
            {
               match( "hours" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:345:23:
               // 'hour'
            {
               match( "hour" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:345:32:
               // 'hrs'
            {
               match( "hrs" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:345:40:
               // 'hr'
            {
               match( "hr" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:345:47:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:347:11: (
         // 'minutes' | 'minute' | 'mins' | 'min' | 'm' )
         int alt4 = 5;
         alt4 = dfa4.predict( input );
         switch ( alt4 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:347:13:
               // 'minutes'
            {
               match( "minutes" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:347:25:
               // 'minute'
            {
               match( "minute" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:347:36:
               // 'mins'
            {
               match( "mins" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:347:45:
               // 'min'
            {
               match( "min" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:347:53:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:349:11: (
         // 'seconds' | 'second' | 'secs' | 'sec' | 's' )
         int alt5 = 5;
         alt5 = dfa5.predict( input );
         switch ( alt5 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:349:13:
               // 'seconds'
            {
               match( "seconds" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:349:25:
               // 'second'
            {
               match( "second" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:349:36:
               // 'secs'
            {
               match( "secs" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:349:45:
               // 'sec'
            {
               match( "sec" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:349:53:
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
   
   // $ANTLR start "AM"
   public final void mAM() throws RecognitionException
   {
      try
      {
         int _type = AM;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:351:11: (
         // 'a' ( 'm' )? | '\\u4E0A' | '\\u5348\\u524D' | '\\uC624\\uC804' )
         int alt7 = 4;
         switch ( input.LA( 1 ) )
         {
            case 'a':
            {
               alt7 = 1;
            }
               break;
            case '\u4E0A':
            {
               alt7 = 2;
            }
               break;
            case '\u5348':
            {
               alt7 = 3;
            }
               break;
            case '\uC624':
            {
               alt7 = 4;
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:351:13:
               // 'a' ( 'm' )?
            {
               match( 'a' );
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:351:16:
               // ( 'm' )?
               int alt6 = 2;
               int LA6_0 = input.LA( 1 );
               
               if ( ( LA6_0 == 'm' ) )
               {
                  alt6 = 1;
               }
               switch ( alt6 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:351:17:
                     // 'm'
                  {
                     match( 'm' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:351:25:
               // '\\u4E0A'
            {
               match( '\u4E0A' );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:351:36:
               // '\\u5348\\u524D'
            {
               match( "\u5348\u524D" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:351:53:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:353:11: (
         // 'p' ( 'm' )? | '\\u4E0B' | '\\u5348\\u5F8C' | '\\uC624\\uD6C4' )
         int alt9 = 4;
         switch ( input.LA( 1 ) )
         {
            case 'p':
            {
               alt9 = 1;
            }
               break;
            case '\u4E0B':
            {
               alt9 = 2;
            }
               break;
            case '\u5348':
            {
               alt9 = 3;
            }
               break;
            case '\uC624':
            {
               alt9 = 4;
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:353:13:
               // 'p' ( 'm' )?
            {
               match( 'p' );
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:353:16:
               // ( 'm' )?
               int alt8 = 2;
               int LA8_0 = input.LA( 1 );
               
               if ( ( LA8_0 == 'm' ) )
               {
                  alt8 = 1;
               }
               switch ( alt8 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:353:17:
                     // 'm'
                  {
                     match( 'm' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:353:25:
               // '\\u4E0B'
            {
               match( '\u4E0B' );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:353:36:
               // '\\u5348\\u5F8C'
            {
               match( "\u5348\u5F8C" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:353:53:
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
   
   // $ANTLR start "COMMA"
   public final void mCOMMA() throws RecognitionException
   {
      try
      {
         int _type = COMMA;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:355:11: (
         // ',' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:355:13: ','
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:357:11: (
         // '.' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:357:13: '.'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:359:11: (
         // ':' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:359:13: ':'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:361:11: ( (
         // '0' .. '9' )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:361:13: (
         // '0' .. '9' )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:361:13:
            // ( '0' .. '9' )+
            int cnt10 = 0;
            loop10: do
            {
               int alt10 = 2;
               int LA10_0 = input.LA( 1 );
               
               if ( ( ( LA10_0 >= '0' && LA10_0 <= '9' ) ) )
               {
                  alt10 = 1;
               }
               
               switch ( alt10 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:361:13:
                     // '0' .. '9'
                  {
                     matchRange( '0', '9' );
                     
                  }
                     break;
                  
                  default :
                     if ( cnt10 >= 1 )
                        break loop10;
                     EarlyExitException eee = new EarlyExitException( 10, input );
                     throw eee;
               }
               cnt10++;
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:363:11: ( (
         // ' ' | '\\t' | '\\r' | '\\n' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:363:13: (
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
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:8: ( AT |
      // AND | NEVER | MIDNIGHT | MIDDAY | NOON | DAYS | HOURS | MINUTES | SECONDS | AM | PM | COMMA | DOT | COLON | INT
      // | WS )
      int alt11 = 17;
      alt11 = dfa11.predict( input );
      switch ( alt11 )
      {
         case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:10: AT
         {
            mAT();
            
         }
            break;
         case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:13:
            // AND
         {
            mAND();
            
         }
            break;
         case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:17:
            // NEVER
         {
            mNEVER();
            
         }
            break;
         case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:23:
            // MIDNIGHT
         {
            mMIDNIGHT();
            
         }
            break;
         case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:32:
            // MIDDAY
         {
            mMIDDAY();
            
         }
            break;
         case 6:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:39:
            // NOON
         {
            mNOON();
            
         }
            break;
         case 7:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:44:
            // DAYS
         {
            mDAYS();
            
         }
            break;
         case 8:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:49:
            // HOURS
         {
            mHOURS();
            
         }
            break;
         case 9:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:55:
            // MINUTES
         {
            mMINUTES();
            
         }
            break;
         case 10:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:63:
            // SECONDS
         {
            mSECONDS();
            
         }
            break;
         case 11:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:71: AM
         {
            mAM();
            
         }
            break;
         case 12:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:74: PM
         {
            mPM();
            
         }
            break;
         case 13:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:77:
            // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 14:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:83:
            // DOT
         {
            mDOT();
            
         }
            break;
         case 15:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:87:
            // COLON
         {
            mCOLON();
            
         }
            break;
         case 16:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:93:
            // INT
         {
            mINT();
            
         }
            break;
         case 17:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:97: WS
         {
            mWS();
            
         }
            break;
         
      }
      
   }
   
   protected DFA3 dfa3 = new DFA3( this );
   
   protected DFA4 dfa4 = new DFA4( this );
   
   protected DFA5 dfa5 = new DFA5( this );
   
   protected DFA11 dfa11 = new DFA11( this );
   
   static final String DFA3_eotS = "\1\uffff\1\4\1\uffff\1\7\4\uffff\1\12\2\uffff";
   
   static final String DFA3_eofS = "\13\uffff";
   
   static final String DFA3_minS = "\1\150\1\157\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
   
   static final String DFA3_maxS = "\1\150\1\162\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
   
   static final String DFA3_acceptS = "\4\uffff\1\5\1\uffff\1\3\1\4\1\uffff\1\1\1\2";
   
   static final String DFA3_specialS = "\13\uffff}>";
   
   static final String[] DFA3_transitionS =
   { "\1\1", "\1\2\2\uffff\1\3", "\1\5", "\1\6", "", "\1\10", "", "", "\1\11",
    "", "" };
   
   static final short[] DFA3_eot = DFA.unpackEncodedString( DFA3_eotS );
   
   static final short[] DFA3_eof = DFA.unpackEncodedString( DFA3_eofS );
   
   static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars( DFA3_minS );
   
   static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars( DFA3_maxS );
   
   static final short[] DFA3_accept = DFA.unpackEncodedString( DFA3_acceptS );
   
   static final short[] DFA3_special = DFA.unpackEncodedString( DFA3_specialS );
   
   static final short[][] DFA3_transition;
   
   static
   {
      int numStates = DFA3_transitionS.length;
      DFA3_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA3_transition[ i ] = DFA.unpackEncodedString( DFA3_transitionS[ i ] );
      }
   }
   
   
   class DFA3 extends DFA
   {
      
      public DFA3( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 3;
         this.eot = DFA3_eot;
         this.eof = DFA3_eof;
         this.min = DFA3_min;
         this.max = DFA3_max;
         this.accept = DFA3_accept;
         this.special = DFA3_special;
         this.transition = DFA3_transition;
      }
      


      public String getDescription()
      {
         return "345:1: HOURS : ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' );";
      }
   }
   
   static final String DFA4_eotS = "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
   
   static final String DFA4_eofS = "\14\uffff";
   
   static final String DFA4_minS = "\1\155\1\151\1\156\1\uffff\1\163\1\164\2\uffff\1\145\1\163\2\uffff";
   
   static final String DFA4_maxS = "\1\155\1\151\1\156\1\uffff\1\165\1\164\2\uffff\1\145\1\163\2\uffff";
   
   static final String DFA4_acceptS = "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
   
   static final String DFA4_specialS = "\14\uffff}>";
   
   static final String[] DFA4_transitionS =
   { "\1\1", "\1\2", "\1\4", "", "\1\6\1\uffff\1\5", "\1\10", "", "", "\1\11",
    "\1\12", "", "" };
   
   static final short[] DFA4_eot = DFA.unpackEncodedString( DFA4_eotS );
   
   static final short[] DFA4_eof = DFA.unpackEncodedString( DFA4_eofS );
   
   static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars( DFA4_minS );
   
   static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars( DFA4_maxS );
   
   static final short[] DFA4_accept = DFA.unpackEncodedString( DFA4_acceptS );
   
   static final short[] DFA4_special = DFA.unpackEncodedString( DFA4_specialS );
   
   static final short[][] DFA4_transition;
   
   static
   {
      int numStates = DFA4_transitionS.length;
      DFA4_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA4_transition[ i ] = DFA.unpackEncodedString( DFA4_transitionS[ i ] );
      }
   }
   
   
   class DFA4 extends DFA
   {
      
      public DFA4( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 4;
         this.eot = DFA4_eot;
         this.eof = DFA4_eof;
         this.min = DFA4_min;
         this.max = DFA4_max;
         this.accept = DFA4_accept;
         this.special = DFA4_special;
         this.transition = DFA4_transition;
      }
      


      public String getDescription()
      {
         return "347:1: MINUTES : ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' );";
      }
   }
   
   static final String DFA5_eotS = "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
   
   static final String DFA5_eofS = "\14\uffff";
   
   static final String DFA5_minS = "\1\163\1\145\1\143\1\uffff\1\157\1\156\2\uffff\1\144\1\163\2\uffff";
   
   static final String DFA5_maxS = "\1\163\1\145\1\143\1\uffff\1\163\1\156\2\uffff\1\144\1\163\2\uffff";
   
   static final String DFA5_acceptS = "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
   
   static final String DFA5_specialS = "\14\uffff}>";
   
   static final String[] DFA5_transitionS =
   { "\1\1", "\1\2", "\1\4", "", "\1\5\3\uffff\1\6", "\1\10", "", "", "\1\11",
    "\1\12", "", "" };
   
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
         return "349:1: SECONDS : ( 'seconds' | 'second' | 'secs' | 'sec' | 's' );";
      }
   }
   
   static final String DFA11_eotS = "\2\uffff\1\10\1\uffff\1\25\24\uffff";
   
   static final String DFA11_eofS = "\31\uffff";
   
   static final String DFA11_minS = "\1\11\1\uffff\1\156\1\145\1\151\4\uffff\1\u524d\1\uc804\11\uffff"
      + "\1\144\1\uffff\1\144\2\uffff";
   
   static final String DFA11_maxS = "\1\uc624\1\uffff\1\164\1\157\1\151\4\uffff\1\u5f8c\1\ud6c4\11\uffff"
      + "\1\156\1\uffff\1\156\2\uffff";
   
   static final String DFA11_acceptS = "\1\uffff\1\1\3\uffff\1\7\1\10\1\12\1\13\2\uffff\1\14\1\15\1\16"
      + "\1\17\1\20\1\21\1\2\1\3\1\6\1\uffff\1\11\1\uffff\1\4\1\5";
   
   static final String DFA11_specialS = "\31\uffff}>";
   
   static final String[] DFA11_transitionS =
   {
    "\2\20\2\uffff\1\20\22\uffff\1\20\13\uffff\1\14\1\uffff\1\15"
       + "\1\uffff\12\17\1\16\5\uffff\1\1\40\uffff\1\2\2\uffff\1\5\3\uffff"
       + "\1\6\4\uffff\1\4\1\3\1\uffff\1\13\2\uffff\1\7\u4d96\uffff\1"
       + "\10\1\13\u053c\uffff\1\11\u72db\uffff\1\12", "", "\1\21\5\uffff\1\1",
    "\1\22\11\uffff\1\23", "\1\24", "", "", "", "", "\1\10\u0d3e\uffff\1\13",
    "\1\10\u0ebf\uffff\1\13", "", "", "", "", "", "", "", "", "",
    "\1\26\11\uffff\1\25", "", "\1\30\11\uffff\1\27", "", "" };
   
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
         return "1:1: Tokens : ( AT | AND | NEVER | MIDNIGHT | MIDDAY | NOON | DAYS | HOURS | MINUTES | SECONDS | AM | PM | COMMA | DOT | COLON | INT | WS );";
      }
   }
   
}
