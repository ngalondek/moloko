// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g 2011-06-16 17:45:38

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


public class RecurrenceLexer extends Lexer
{
   public static final int THIRD = 21;
   
   public static final int NUM_TWO = 25;
   
   public static final int NUM_NINE = 32;
   
   public static final int WEDNESDAY = 36;
   
   public static final int FOR = 12;
   
   public static final int NUM_SIX = 29;
   
   public static final int AND = 14;
   
   public static final int EOF = -1;
   
   public static final int MONTH = 43;
   
   public static final int FRIDAY = 38;
   
   public static final int NUM_THREE = 26;
   
   public static final int COMMA = 15;
   
   public static final int NUM_ONE = 24;
   
   public static final int LAST = 16;
   
   public static final int DOT = 17;
   
   public static final int NUM_EIGHT = 31;
   
   public static final int FOURTH = 22;
   
   public static final int SECOND = 19;
   
   public static final int OTHER = 20;
   
   public static final int NUM_FOUR = 27;
   
   public static final int SATURDAY = 39;
   
   public static final int NUMBER = 47;
   
   public static final int NUM_SEVEN = 30;
   
   public static final int EVERY = 4;
   
   public static final int ON = 8;
   
   public static final int WEEKEND = 41;
   
   public static final int MONDAY = 34;
   
   public static final int SUNDAY = 40;
   
   public static final int INT = 13;
   
   public static final int MINUS = 46;
   
   public static final int AFTER = 5;
   
   public static final int YEARS = 10;
   
   public static final int NUM_FIVE = 28;
   
   public static final int FIFTH = 23;
   
   public static final int DAYS = 6;
   
   public static final int NUM_TEN = 33;
   
   public static final int WS = 48;
   
   public static final int WEEKS = 7;
   
   public static final int THURSDAY = 37;
   
   public static final int UNTIL = 11;
   
   public static final int MONTHS = 9;
   
   public static final int WEEKDAY_LIT = 42;
   
   public static final int TIMES = 45;
   
   public static final int TUESDAY = 35;
   
   public static final int FIRST = 18;
   
   public static final int STRING = 44;
   
   

   // delegates
   // delegators
   
   public RecurrenceLexer()
   {
      ;
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
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g";
   }
   


   // $ANTLR start "EVERY"
   public final void mEVERY() throws RecognitionException
   {
      try
      {
         int _type = EVERY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:272:15: (
         // 'jede' ( 's' | 'r' | 'n' )? | 'alle' )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:272:17:
               // 'jede' ( 's' | 'r' | 'n' )?
            {
               match( "jede" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:272:23:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:272:40:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:274:15: (
         // 'nach' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:274:17:
         // 'nach'
         {
            match( "nach" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:276:15: (
         // 'jahr' ( 'e' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:276:17:
         // 'jahr' ( 'e' )?
         {
            match( "jahr" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:276:23:
            // ( 'e' )?
            int alt3 = 2;
            int LA3_0 = input.LA( 1 );
            
            if ( ( LA3_0 == 'e' ) )
            {
               alt3 = 1;
            }
            switch ( alt3 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:276:23:
                  // 'e'
               {
                  match( 'e' );
                  
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:278:15: (
         // 'monat' ( 'e' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:278:17:
         // 'monat' ( 'e' )?
         {
            match( "monat" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:278:24:
            // ( 'e' )?
            int alt4 = 2;
            int LA4_0 = input.LA( 1 );
            
            if ( ( LA4_0 == 'e' ) )
            {
               alt4 = 1;
            }
            switch ( alt4 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:278:24:
                  // 'e'
               {
                  match( 'e' );
                  
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:280:15: (
         // 'woche' ( 'n' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:280:17:
         // 'woche' ( 'n' )?
         {
            match( "woche" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:280:24:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:280:24:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:282:15: (
         // 'tag' ( 'e' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:282:17:
         // 'tag' ( 'e' )?
         {
            match( "tag" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:282:22:
            // ( 'e' )?
            int alt6 = 2;
            int LA6_0 = input.LA( 1 );
            
            if ( ( LA6_0 == 'e' ) )
            {
               alt6 = 1;
            }
            switch ( alt6 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:282:22:
                  // 'e'
               {
                  match( 'e' );
                  
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:284:15: (
         // 'januar' | 'jan' | 'februar' | 'feb' | 'märz' | 'april' | 'apr' | 'mai' | 'juni' | 'jun' | 'juli' | 'jul' |
         // 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'oktober' | 'okt' | 'november' | 'nov' | 'dezember' |
         // 'dez' )
         int alt7 = 23;
         alt7 = dfa7.predict( input );
         switch ( alt7 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:284:17:
               // 'januar'
            {
               match( "januar" );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:284:31:
               // 'jan'
            {
               match( "jan" );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:284:40:
               // 'februar'
            {
               match( "februar" );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:284:53:
               // 'feb'
            {
               match( "feb" );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:284:65:
               // 'märz'
            {
               match( "märz" );
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:284:75:
               // 'april'
            {
               match( "april" );
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:284:88:
               // 'apr'
            {
               match( "apr" );
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:285:17:
               // 'mai'
            {
               match( "mai" );
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:285:31:
               // 'juni'
            {
               match( "juni" );
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:285:40:
               // 'jun'
            {
               match( "jun" );
               
            }
               break;
            case 11:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:285:53:
               // 'juli'
            {
               match( "juli" );
               
            }
               break;
            case 12:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:285:65:
               // 'jul'
            {
               match( "jul" );
               
            }
               break;
            case 13:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:285:75:
               // 'august'
            {
               match( "august" );
               
            }
               break;
            case 14:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:285:88:
               // 'aug'
            {
               match( "aug" );
               
            }
               break;
            case 15:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:286:17:
               // 'september'
            {
               match( "september" );
               
            }
               break;
            case 16:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:286:31:
               // 'sept'
            {
               match( "sept" );
               
            }
               break;
            case 17:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:286:40:
               // 'sep'
            {
               match( "sep" );
               
            }
               break;
            case 18:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:286:53:
               // 'oktober'
            {
               match( "oktober" );
               
            }
               break;
            case 19:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:286:65:
               // 'okt'
            {
               match( "okt" );
               
            }
               break;
            case 20:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:286:75:
               // 'november'
            {
               match( "november" );
               
            }
               break;
            case 21:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:286:88:
               // 'nov'
            {
               match( "nov" );
               
            }
               break;
            case 22:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:287:17:
               // 'dezember'
            {
               match( "dezember" );
               
            }
               break;
            case 23:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:287:31:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:289:15: (
         // 'wochentag' ( 's' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:289:17:
         // 'wochentag' ( 's' )?
         {
            match( "wochentag" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:289:28:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:289:28:
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
   


   // $ANTLR end "WEEKDAY_LIT"
   
   // $ANTLR start "WEEKEND"
   public final void mWEEKEND() throws RecognitionException
   {
      try
      {
         int _type = WEEKEND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:291:15: (
         // 'wochenende' ( 'n' )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:291:17:
         // 'wochenende' ( 'n' )?
         {
            match( "wochenende" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:291:29:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:291:29:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:293:15: (
         // 'montag' ( 's' )? | 'mo' )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:293:17:
               // 'montag' ( 's' )?
            {
               match( "montag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:293:25:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:293:25:
                     // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:293:36:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:295:15: (
         // 'dienstag' ( 's' )? | 'di' )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:295:17:
               // 'dienstag' ( 's' )?
            {
               match( "dienstag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:295:27:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:295:27:
                     // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:295:36:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:297:15: (
         // 'mittwoch' ( 's' )? | 'mi' )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:297:17:
               // 'mittwoch' ( 's' )?
            {
               match( "mittwoch" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:297:27:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:297:27:
                     // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:297:36:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:299:15: (
         // 'donnerstag' ( 's' )? | 'do' )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:299:17:
               // 'donnerstag' ( 's' )?
            {
               match( "donnerstag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:299:29:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:299:29:
                     // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:299:36:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:301:15: (
         // 'freitag' ( 's' )? | 'fr' )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:301:17:
               // 'freitag' ( 's' )?
            {
               match( "freitag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:301:26:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:301:26:
                     // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:301:36:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:303:15: (
         // 'samstag' ( 's' )? | 'sa' )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:303:17:
               // 'samstag' ( 's' )?
            {
               match( "samstag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:303:26:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:303:26:
                     // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:303:36:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:305:15: (
         // 'sonntag' ( 's' )? | 'so' )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:305:17:
               // 'sonntag' ( 's' )?
            {
               match( "sonntag" );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:305:26:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:305:26:
                     // 's'
                  {
                     match( 's' );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:305:36:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:307:15: (
         // 'erst' ( 'e' | 's' | 'r' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:307:17:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:309:15: (
         // 'zweit' ( 'e' | 's' | 'r' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:309:17:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:311:15: (
         // 'dritt' ( 'e' | 's' | 'r' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:311:17:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:313:15: (
         // 'viert' ( 'e' | 's' | 'r' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:313:17:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:315:15: (
         // 'fünft' ( 'e' | 's' | 'r' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:315:17:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:317:15: (
         // 'letzt' ( 'e' | 's' | 'r' | 'en' | 'em' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:317:17:
         // 'letzt' ( 'e' | 's' | 'r' | 'en' | 'em' )
         {
            match( "letzt" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:317:24:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:317:25:
                  // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:317:29:
                  // 's'
               {
                  match( 's' );
                  
               }
                  break;
               case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:317:33:
                  // 'r'
               {
                  match( 'r' );
                  
               }
                  break;
               case 4:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:317:37:
                  // 'en'
               {
                  match( "en" );
                  
               }
                  break;
               case 5:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:317:42:
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
   


   // $ANTLR end "LAST"
   
   // $ANTLR start "OTHER"
   public final void mOTHER() throws RecognitionException
   {
      try
      {
         int _type = OTHER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:319:15: (
         // 'ander' ( 'e' | 'es' | 'er' | 'en' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:319:17:
         // 'ander' ( 'e' | 'es' | 'er' | 'en' )
         {
            match( "ander" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:319:24:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:319:25:
                  // 'e'
               {
                  match( 'e' );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:319:29:
                  // 'es'
               {
                  match( "es" );
                  
               }
                  break;
               case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:319:34:
                  // 'er'
               {
                  match( "er" );
                  
               }
                  break;
               case 4:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:319:39:
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
   


   // $ANTLR end "OTHER"
   
   // $ANTLR start "NUM_ONE"
   public final void mNUM_ONE() throws RecognitionException
   {
      try
      {
         int _type = NUM_ONE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:321:15: (
         // 'eins' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:321:17:
         // 'eins'
         {
            match( "eins" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:323:15: (
         // 'zwei' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:323:17:
         // 'zwei'
         {
            match( "zwei" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:325:15: (
         // 'frei' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:325:17:
         // 'frei'
         {
            match( "frei" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:327:15: (
         // 'vier' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:327:17:
         // 'vier'
         {
            match( "vier" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:329:15: (
         // 'fünf' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:329:17:
         // 'fünf'
         {
            match( "fünf" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:331:15: (
         // 'sechs' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:331:17:
         // 'sechs'
         {
            match( "sechs" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:333:15: (
         // 'sieben' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:333:17:
         // 'sieben'
         {
            match( "sieben" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:335:15: (
         // 'acht' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:335:17:
         // 'acht'
         {
            match( "acht" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:337:15: (
         // 'neun' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:337:17:
         // 'neun'
         {
            match( "neun" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:339:15: (
         // 'zehn' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:339:17:
         // 'zehn'
         {
            match( "zehn" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:341:15: (
         // 'und' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:341:17:
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
   
   // $ANTLR start "ON"
   public final void mON() throws RecognitionException
   {
      try
      {
         int _type = ON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:343:15: (
         // 'a' ( 'm' | 'n' ) | 'i' ( 'n' | 'm' ) | 'de' ( 's' | 'r' ) | 'vo' ( 'n' | 'm' ) )
         int alt26 = 4;
         switch ( input.LA( 1 ) )
         {
            case 'a':
            {
               alt26 = 1;
            }
               break;
            case 'i':
            {
               alt26 = 2;
            }
               break;
            case 'd':
            {
               alt26 = 3;
            }
               break;
            case 'v':
            {
               alt26 = 4;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     26,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt26 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:343:17:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:343:32:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:343:47:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:343:63:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:345:15: (
         // 'bis' STRING )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:345:17:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:347:15: (
         // 'für' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:347:17:
         // 'für'
         {
            match( "für" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:349:15: (
         // 'mal' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:349:17:
         // 'mal'
         {
            match( "mal" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:351:15: (
         // '.' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:351:17: '.'
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
   
   // $ANTLR start "MINUS"
   public final void mMINUS() throws RecognitionException
   {
      try
      {
         int _type = MINUS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:353:15: (
         // '-' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:353:17: '-'
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:355:15: (
         // ',' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:355:17: ','
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
   
   // $ANTLR start "NUMBER"
   public final void mNUMBER() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:358:15: (
         // '0' .. '9' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:358:17: '0'
         // .. '9'
         {
            matchRange( '0', '9' );
            
         }
         
      }
      finally
      {
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:360:15: ( (
         // MINUS )? ( NUMBER )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:360:17: (
         // MINUS )? ( NUMBER )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:360:17:
            // ( MINUS )?
            int alt27 = 2;
            int LA27_0 = input.LA( 1 );
            
            if ( ( LA27_0 == '-' ) )
            {
               alt27 = 1;
            }
            switch ( alt27 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:360:17:
                  // MINUS
               {
                  mMINUS();
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:360:24:
            // ( NUMBER )+
            int cnt28 = 0;
            loop28: do
            {
               int alt28 = 2;
               int LA28_0 = input.LA( 1 );
               
               if ( ( ( LA28_0 >= '0' && LA28_0 <= '9' ) ) )
               {
                  alt28 = 1;
               }
               
               switch ( alt28 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:360:24:
                     // NUMBER
                  {
                     mNUMBER();
                     
                  }
                     break;
                  
                  default :
                     if ( cnt28 >= 1 )
                        break loop28;
                     EarlyExitException eee = new EarlyExitException( 28, input );
                     throw eee;
               }
               cnt28++;
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
   
   // $ANTLR start "STRING"
   public final void mSTRING() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:363:15: ( (
         // ' ' | . )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:363:17: (
         // ' ' | . )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:363:17:
            // ( ' ' | . )+
            int cnt29 = 0;
            loop29: do
            {
               int alt29 = 3;
               int LA29_0 = input.LA( 1 );
               
               if ( ( LA29_0 == ' ' ) )
               {
                  alt29 = 1;
               }
               else if ( ( ( LA29_0 >= '\u0000' && LA29_0 <= '\u001F' ) || ( LA29_0 >= '!' && LA29_0 <= '\uFFFF' ) ) )
               {
                  alt29 = 2;
               }
               
               switch ( alt29 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:363:18:
                     // ' '
                  {
                     match( ' ' );
                     
                  }
                     break;
                  case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:363:22:
                     // .
                  {
                     matchAny();
                     
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:365:15: ( (
         // ' ' | '\\t' | '\\r' | '\\n' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:365:17: (
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
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:8: ( EVERY |
      // AFTER | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY_LIT | WEEKEND | MONDAY | TUESDAY | WEDNESDAY | THURSDAY
      // | FRIDAY | SATURDAY | SUNDAY | FIRST | SECOND | THIRD | FOURTH | FIFTH | LAST | OTHER | NUM_ONE | NUM_TWO |
      // NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN | AND | ON | UNTIL | FOR
      // | TIMES | DOT | MINUS | COMMA | INT | WS )
      int alt30 = 43;
      alt30 = dfa30.predict( input );
      switch ( alt30 )
      {
         case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:10:
            // EVERY
         {
            mEVERY();
            
         }
            break;
         case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:16:
            // AFTER
         {
            mAFTER();
            
         }
            break;
         case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:22:
            // YEARS
         {
            mYEARS();
            
         }
            break;
         case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:28:
            // MONTHS
         {
            mMONTHS();
            
         }
            break;
         case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:35:
            // WEEKS
         {
            mWEEKS();
            
         }
            break;
         case 6:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:41:
            // DAYS
         {
            mDAYS();
            
         }
            break;
         case 7:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:46:
            // MONTH
         {
            mMONTH();
            
         }
            break;
         case 8:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:52:
            // WEEKDAY_LIT
         {
            mWEEKDAY_LIT();
            
         }
            break;
         case 9:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:64:
            // WEEKEND
         {
            mWEEKEND();
            
         }
            break;
         case 10:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:72:
            // MONDAY
         {
            mMONDAY();
            
         }
            break;
         case 11:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:79:
            // TUESDAY
         {
            mTUESDAY();
            
         }
            break;
         case 12:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:87:
            // WEDNESDAY
         {
            mWEDNESDAY();
            
         }
            break;
         case 13:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:97:
            // THURSDAY
         {
            mTHURSDAY();
            
         }
            break;
         case 14:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:106:
            // FRIDAY
         {
            mFRIDAY();
            
         }
            break;
         case 15:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:113:
            // SATURDAY
         {
            mSATURDAY();
            
         }
            break;
         case 16:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:122:
            // SUNDAY
         {
            mSUNDAY();
            
         }
            break;
         case 17:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:129:
            // FIRST
         {
            mFIRST();
            
         }
            break;
         case 18:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:135:
            // SECOND
         {
            mSECOND();
            
         }
            break;
         case 19:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:142:
            // THIRD
         {
            mTHIRD();
            
         }
            break;
         case 20:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:148:
            // FOURTH
         {
            mFOURTH();
            
         }
            break;
         case 21:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:155:
            // FIFTH
         {
            mFIFTH();
            
         }
            break;
         case 22:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:161:
            // LAST
         {
            mLAST();
            
         }
            break;
         case 23:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:166:
            // OTHER
         {
            mOTHER();
            
         }
            break;
         case 24:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:172:
            // NUM_ONE
         {
            mNUM_ONE();
            
         }
            break;
         case 25:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:180:
            // NUM_TWO
         {
            mNUM_TWO();
            
         }
            break;
         case 26:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:188:
            // NUM_THREE
         {
            mNUM_THREE();
            
         }
            break;
         case 27:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:198:
            // NUM_FOUR
         {
            mNUM_FOUR();
            
         }
            break;
         case 28:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:207:
            // NUM_FIVE
         {
            mNUM_FIVE();
            
         }
            break;
         case 29:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:216:
            // NUM_SIX
         {
            mNUM_SIX();
            
         }
            break;
         case 30:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:224:
            // NUM_SEVEN
         {
            mNUM_SEVEN();
            
         }
            break;
         case 31:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:234:
            // NUM_EIGHT
         {
            mNUM_EIGHT();
            
         }
            break;
         case 32:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:244:
            // NUM_NINE
         {
            mNUM_NINE();
            
         }
            break;
         case 33:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:253:
            // NUM_TEN
         {
            mNUM_TEN();
            
         }
            break;
         case 34:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:261:
            // AND
         {
            mAND();
            
         }
            break;
         case 35:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:265:
            // ON
         {
            mON();
            
         }
            break;
         case 36:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:268:
            // UNTIL
         {
            mUNTIL();
            
         }
            break;
         case 37:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:274:
            // FOR
         {
            mFOR();
            
         }
            break;
         case 38:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:278:
            // TIMES
         {
            mTIMES();
            
         }
            break;
         case 39:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:284:
            // DOT
         {
            mDOT();
            
         }
            break;
         case 40:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:288:
            // MINUS
         {
            mMINUS();
            
         }
            break;
         case 41:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:294:
            // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 42:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:300:
            // INT
         {
            mINT();
            
         }
            break;
         case 43:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:1:304:
            // WS
         {
            mWS();
            
         }
            break;
         
      }
      
   }
   
   protected DFA7 dfa7 = new DFA7( this );
   
   protected DFA30 dfa30 = new DFA30( this );
   
   static final String DFA7_eotS = "\24\uffff\1\37\1\41\1\43\1\45\1\47\1\51\1\53\1\55\1\57\1\61\14"
      + "\uffff\1\63\11\uffff";
   
   static final String DFA7_eofS = "\64\uffff";
   
   static final String DFA7_minS = "\2\141\1\145\1\141\1\160\1\145\1\153\1\157\1\145\1\156\1\154\1"
      + "\142\2\uffff\1\162\1\147\1\160\1\164\1\166\1\172\1\165\2\151\1\162"
      + "\1\151\1\165\1\164\1\157\2\145\14\uffff\1\145\11\uffff";
   
   static final String DFA7_maxS = "\1\163\1\165\1\145\1\u00e4\1\165\1\145\1\153\1\157\1\145\2\156"
      + "\1\142\2\uffff\1\162\1\147\1\160\1\164\1\166\1\172\1\165\2\151\1"
      + "\162\1\151\1\165\1\164\1\157\2\145\14\uffff\1\145\11\uffff";
   
   static final String DFA7_acceptS = "\14\uffff\1\5\1\10\20\uffff\1\1\1\2\1\11\1\12\1\13\1\14\1\3\1\4"
      + "\1\6\1\7\1\15\1\16\1\uffff\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1"
      + "\17\1\20";
   
   static final String DFA7_specialS = "\64\uffff}>";
   
   static final String[] DFA7_transitionS =
   {
    "\1\4\2\uffff\1\10\1\uffff\1\2\3\uffff\1\1\2\uffff\1\3\1\7\1"
       + "\6\3\uffff\1\5", "\1\11\23\uffff\1\12", "\1\13",
    "\1\15\u0082\uffff\1\14", "\1\16\4\uffff\1\17", "\1\20", "\1\21", "\1\22",
    "\1\23", "\1\24", "\1\26\1\uffff\1\25", "\1\27", "", "", "\1\30", "\1\31",
    "\1\32", "\1\33", "\1\34", "\1\35", "\1\36", "\1\40", "\1\42", "\1\44",
    "\1\46", "\1\50", "\1\52", "\1\54", "\1\56", "\1\60", "", "", "", "", "",
    "", "", "", "", "", "", "", "\1\62", "", "", "", "", "", "", "", "", "" };
   
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
         return "284:1: MONTH : ( 'januar' | 'jan' | 'februar' | 'feb' | 'märz' | 'april' | 'apr' | 'mai' | 'juni' | 'jun' | 'juli' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'oktober' | 'okt' | 'november' | 'nov' | 'dezember' | 'dez' );";
      }
   }
   
   static final String DFA30_eotS = "\23\uffff\1\60\5\uffff\1\20\3\uffff\1\64\3\uffff\1\70\36\uffff"
      + "\1\105\1\107\1\111\1\113\1\115\7\uffff\1\115\3\uffff";
   
   static final String DFA30_eofS = "\120\uffff";
   
   static final String DFA30_minS = "\1\11\1\141\1\143\2\141\1\157\1\uffff\1\145\1\141\1\uffff\1\145"
      + "\1\151\1\145\1\151\5\uffff\1\60\4\uffff\1\150\1\144\3\uffff\1\156"
      + "\1\151\1\uffff\1\143\1\145\1\156\1\143\3\uffff\1\162\5\uffff\1\145"
      + "\1\uffff\1\145\3\uffff\1\141\2\uffff\1\150\1\151\1\uffff\1\146\2"
      + "\uffff\1\151\1\162\1\uffff\1\145\4\164\1\156\7\uffff\1\145\3\uffff";
   
   static final String DFA30_maxS = "\1\172\2\165\1\157\1\u00e4\1\157\1\uffff\1\u00fc\1\157\1\uffff"
      + "\2\162\1\167\1\157\5\uffff\1\71\4\uffff\1\156\1\144\3\uffff\1\156"
      + "\1\154\1\uffff\1\143\1\145\1\162\1\160\3\uffff\1\172\5\uffff\1\145"
      + "\1\uffff\1\145\3\uffff\1\164\2\uffff\1\150\1\151\1\uffff\1\146\2"
      + "\uffff\1\151\1\162\1\uffff\1\145\4\164\1\156\7\uffff\1\164\3\uffff";
   
   static final String DFA30_acceptS = "\6\uffff\1\6\2\uffff\1\7\4\uffff\1\26\1\42\1\43\1\44\1\47\1\uffff"
      + "\1\51\1\52\1\53\1\1\2\uffff\1\37\1\2\1\40\2\uffff\1\14\4\uffff\1"
      + "\17\1\20\1\36\1\uffff\1\13\1\15\1\23\1\21\1\30\1\uffff\1\41\1\uffff"
      + "\1\50\1\3\1\27\1\uffff\1\12\1\46\2\uffff\1\16\1\uffff\1\45\1\35"
      + "\2\uffff\1\4\6\uffff\1\32\1\25\1\34\1\22\1\31\1\24\1\33\1\uffff"
      + "\1\5\1\10\1\11";
   
   static final String DFA30_specialS = "\120\uffff}>";
   
   static final String[] DFA30_transitionS =
   {
    "\2\26\2\uffff\1\26\22\uffff\1\26\13\uffff\1\24\1\23\1\22\1"
       + "\uffff\12\25\47\uffff\1\2\1\21\1\uffff\1\12\1\13\1\7\2\uffff"
       + "\1\20\1\1\1\uffff\1\16\1\4\1\3\1\11\3\uffff\1\10\1\6\1\17\1"
       + "\15\1\5\2\uffff\1\14", "\1\30\3\uffff\1\27\17\uffff\1\11",
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
   
   static final short[] DFA30_eot = DFA.unpackEncodedString( DFA30_eotS );
   
   static final short[] DFA30_eof = DFA.unpackEncodedString( DFA30_eofS );
   
   static final char[] DFA30_min = DFA.unpackEncodedStringToUnsignedChars( DFA30_minS );
   
   static final char[] DFA30_max = DFA.unpackEncodedStringToUnsignedChars( DFA30_maxS );
   
   static final short[] DFA30_accept = DFA.unpackEncodedString( DFA30_acceptS );
   
   static final short[] DFA30_special = DFA.unpackEncodedString( DFA30_specialS );
   
   static final short[][] DFA30_transition;
   
   static
   {
      int numStates = DFA30_transitionS.length;
      DFA30_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA30_transition[ i ] = DFA.unpackEncodedString( DFA30_transitionS[ i ] );
      }
   }
   
   
   class DFA30 extends DFA
   {
      
      public DFA30( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 30;
         this.eot = DFA30_eot;
         this.eof = DFA30_eof;
         this.min = DFA30_min;
         this.max = DFA30_max;
         this.accept = DFA30_accept;
         this.special = DFA30_special;
         this.transition = DFA30_transition;
      }
      


      public String getDescription()
      {
         return "1:1: Tokens : ( EVERY | AFTER | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY_LIT | WEEKEND | MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | FIRST | SECOND | THIRD | FOURTH | FIFTH | LAST | OTHER | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN | AND | ON | UNTIL | FOR | TIMES | DOT | MINUS | COMMA | INT | WS );";
      }
   }
   
}
