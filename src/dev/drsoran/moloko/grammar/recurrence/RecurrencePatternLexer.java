// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g 2013-03-03 20:00:58

package dev.drsoran.moloko.grammar.recurrence;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class RecurrencePatternLexer extends Lexer
{
   public static final int EOF = -1;
   
   public static final int COMMA = 4;
   
   public static final int EQUALS = 5;
   
   public static final int FRIDAY = 6;
   
   public static final int INT = 7;
   
   public static final int MINUS = 8;
   
   public static final int MONDAY = 9;
   
   public static final int NUMBER = 10;
   
   public static final int OP_BYDAY = 11;
   
   public static final int OP_BYMONTH = 12;
   
   public static final int OP_BYMONTHDAY = 13;
   
   public static final int OP_COUNT = 14;
   
   public static final int OP_FREQ = 15;
   
   public static final int OP_INTERVAL = 16;
   
   public static final int OP_UNTIL = 17;
   
   public static final int SATURDAY = 18;
   
   public static final int SEMICOLON = 19;
   
   public static final int SUNDAY = 20;
   
   public static final int THURSDAY = 21;
   
   public static final int TUESDAY = 22;
   
   public static final int VAL_DAILY = 23;
   
   public static final int VAL_DATE = 24;
   
   public static final int VAL_MONTHLY = 25;
   
   public static final int VAL_WEEKLY = 26;
   
   public static final int VAL_YEARLY = 27;
   
   public static final int WEDNESDAY = 28;
   
   public static final int WS = 29;
   
   
   
   // delegates
   // delegators
   public Lexer[] getDelegates()
   {
      return new Lexer[] {};
   }
   
   
   
   public RecurrencePatternLexer()
   {
   }
   
   
   
   public RecurrencePatternLexer( CharStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   
   
   
   public RecurrencePatternLexer( CharStream input, RecognizerSharedState state )
   {
      super( input, state );
   }
   
   
   
   public String getGrammarFileName()
   {
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g";
   }
   
   
   
   // $ANTLR start "MONDAY"
   public final void mMONDAY() throws RecognitionException
   {
      try
      {
         int _type = MONDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:504:15:
         // ( 'MO' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:504:17:
         // 'MO'
         {
            match( "MO" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:506:15:
         // ( 'TU' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:506:17:
         // 'TU'
         {
            match( "TU" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:508:15:
         // ( 'WE' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:508:17:
         // 'WE'
         {
            match( "WE" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:510:15:
         // ( 'TH' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:510:17:
         // 'TH'
         {
            match( "TH" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:512:15:
         // ( 'FR' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:512:17:
         // 'FR'
         {
            match( "FR" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:514:15:
         // ( 'SA' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:514:17:
         // 'SA'
         {
            match( "SA" );
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:516:15:
         // ( 'SU' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:516:17:
         // 'SU'
         {
            match( "SU" );
            
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
   
   // $ANTLR start "SEMICOLON"
   public final void mSEMICOLON() throws RecognitionException
   {
      try
      {
         int _type = SEMICOLON;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:518:15:
         // ( ';' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:518:17:
         // ';'
         {
            match( ';' );
            
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
   
   
   
   // $ANTLR end "SEMICOLON"
   
   // $ANTLR start "EQUALS"
   public final void mEQUALS() throws RecognitionException
   {
      try
      {
         int _type = EQUALS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:520:15:
         // ( '=' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:520:17:
         // '='
         {
            match( '=' );
            
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
   
   
   
   // $ANTLR end "EQUALS"
   
   // $ANTLR start "MINUS"
   public final void mMINUS() throws RecognitionException
   {
      try
      {
         int _type = MINUS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:522:15:
         // ( '-' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:522:17:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:524:15:
         // ( ',' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:524:17:
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
   
   // $ANTLR start "OP_BYDAY"
   public final void mOP_BYDAY() throws RecognitionException
   {
      try
      {
         int _type = OP_BYDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:526:15:
         // ( 'BYDAY' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:526:17:
         // 'BYDAY'
         {
            match( "BYDAY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "OP_BYDAY"
   
   // $ANTLR start "OP_BYMONTH"
   public final void mOP_BYMONTH() throws RecognitionException
   {
      try
      {
         int _type = OP_BYMONTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:528:15:
         // ( 'BYMONTH' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:528:17:
         // 'BYMONTH'
         {
            match( "BYMONTH" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "OP_BYMONTH"
   
   // $ANTLR start "OP_BYMONTHDAY"
   public final void mOP_BYMONTHDAY() throws RecognitionException
   {
      try
      {
         int _type = OP_BYMONTHDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:530:15:
         // ( 'BYMONTHDAY' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:530:17:
         // 'BYMONTHDAY'
         {
            match( "BYMONTHDAY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "OP_BYMONTHDAY"
   
   // $ANTLR start "OP_INTERVAL"
   public final void mOP_INTERVAL() throws RecognitionException
   {
      try
      {
         int _type = OP_INTERVAL;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:532:15:
         // ( 'INTERVAL' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:532:17:
         // 'INTERVAL'
         {
            match( "INTERVAL" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "OP_INTERVAL"
   
   // $ANTLR start "OP_FREQ"
   public final void mOP_FREQ() throws RecognitionException
   {
      try
      {
         int _type = OP_FREQ;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:534:15:
         // ( 'FREQ' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:534:17:
         // 'FREQ'
         {
            match( "FREQ" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "OP_FREQ"
   
   // $ANTLR start "OP_UNTIL"
   public final void mOP_UNTIL() throws RecognitionException
   {
      try
      {
         int _type = OP_UNTIL;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:536:15:
         // ( 'UNTIL' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:536:17:
         // 'UNTIL'
         {
            match( "UNTIL" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "OP_UNTIL"
   
   // $ANTLR start "OP_COUNT"
   public final void mOP_COUNT() throws RecognitionException
   {
      try
      {
         int _type = OP_COUNT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:538:15:
         // ( 'COUNT' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:538:17:
         // 'COUNT'
         {
            match( "COUNT" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "OP_COUNT"
   
   // $ANTLR start "VAL_DAILY"
   public final void mVAL_DAILY() throws RecognitionException
   {
      try
      {
         int _type = VAL_DAILY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:540:15:
         // ( 'DAILY' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:540:17:
         // 'DAILY'
         {
            match( "DAILY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "VAL_DAILY"
   
   // $ANTLR start "VAL_WEEKLY"
   public final void mVAL_WEEKLY() throws RecognitionException
   {
      try
      {
         int _type = VAL_WEEKLY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:542:15:
         // ( 'WEEKLY' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:542:17:
         // 'WEEKLY'
         {
            match( "WEEKLY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "VAL_WEEKLY"
   
   // $ANTLR start "VAL_MONTHLY"
   public final void mVAL_MONTHLY() throws RecognitionException
   {
      try
      {
         int _type = VAL_MONTHLY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:544:15:
         // ( 'MONTHLY' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:544:17:
         // 'MONTHLY'
         {
            match( "MONTHLY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "VAL_MONTHLY"
   
   // $ANTLR start "VAL_YEARLY"
   public final void mVAL_YEARLY() throws RecognitionException
   {
      try
      {
         int _type = VAL_YEARLY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:546:15:
         // ( 'YEARLY' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:546:17:
         // 'YEARLY'
         {
            match( "YEARLY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "VAL_YEARLY"
   
   // $ANTLR start "VAL_DATE"
   public final void mVAL_DATE() throws RecognitionException
   {
      try
      {
         int _type = VAL_DATE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:549:15:
         // ( NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER 'T' NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:549:17:
         // NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER 'T' NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER
         {
            mNUMBER();
            
            mNUMBER();
            
            mNUMBER();
            
            mNUMBER();
            
            mNUMBER();
            
            mNUMBER();
            
            mNUMBER();
            
            mNUMBER();
            
            match( 'T' );
            
            mNUMBER();
            
            mNUMBER();
            
            mNUMBER();
            
            mNUMBER();
            
            mNUMBER();
            
            mNUMBER();
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "VAL_DATE"
   
   // $ANTLR start "NUMBER"
   public final void mNUMBER() throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:554:15:
         // ( '0' .. '9' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:555:15:
         // ( ( MINUS )? ( NUMBER )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:555:17:
         // ( MINUS )? ( NUMBER )+
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:555:17:
            // ( MINUS )?
            int alt1 = 2;
            int LA1_0 = input.LA( 1 );
            
            if ( ( LA1_0 == '-' ) )
            {
               alt1 = 1;
            }
            switch ( alt1 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:
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
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:555:24:
            // ( NUMBER )+
            int cnt2 = 0;
            loop2: do
            {
               int alt2 = 2;
               int LA2_0 = input.LA( 1 );
               
               if ( ( ( LA2_0 >= '0' && LA2_0 <= '9' ) ) )
               {
                  alt2 = 1;
               }
               
               switch ( alt2 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:
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
                     if ( cnt2 >= 1 )
                        break loop2;
                     EarlyExitException eee = new EarlyExitException( 2, input );
                     throw eee;
               }
               cnt2++;
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:557:15:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:557:17:
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
      // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:8:
      // ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | SEMICOLON | EQUALS | MINUS | COMMA |
      // OP_BYDAY | OP_BYMONTH | OP_BYMONTHDAY | OP_INTERVAL | OP_FREQ | OP_UNTIL | OP_COUNT | VAL_DAILY | VAL_WEEKLY |
      // VAL_MONTHLY | VAL_YEARLY | VAL_DATE | INT | WS )
      int alt3 = 25;
      switch ( input.LA( 1 ) )
      {
         case 'M':
         {
            int LA3_1 = input.LA( 2 );
            
            if ( ( LA3_1 == 'O' ) )
            {
               int LA3_18 = input.LA( 3 );
               
               if ( ( LA3_18 == 'N' ) )
               {
                  alt3 = 21;
               }
               else
               {
                  alt3 = 1;
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
         case 'T':
         {
            int LA3_2 = input.LA( 2 );
            
            if ( ( LA3_2 == 'U' ) )
            {
               alt3 = 2;
            }
            else if ( ( LA3_2 == 'H' ) )
            {
               alt3 = 4;
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
            break;
         case 'W':
         {
            int LA3_3 = input.LA( 2 );
            
            if ( ( LA3_3 == 'E' ) )
            {
               int LA3_21 = input.LA( 3 );
               
               if ( ( LA3_21 == 'E' ) )
               {
                  alt3 = 20;
               }
               else
               {
                  alt3 = 3;
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
            break;
         case 'F':
         {
            int LA3_4 = input.LA( 2 );
            
            if ( ( LA3_4 == 'R' ) )
            {
               int LA3_22 = input.LA( 3 );
               
               if ( ( LA3_22 == 'E' ) )
               {
                  alt3 = 16;
               }
               else
               {
                  alt3 = 5;
               }
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
            break;
         case 'S':
         {
            int LA3_5 = input.LA( 2 );
            
            if ( ( LA3_5 == 'A' ) )
            {
               alt3 = 6;
            }
            else if ( ( LA3_5 == 'U' ) )
            {
               alt3 = 7;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     3,
                                                                     5,
                                                                     input );
               
               throw nvae;
               
            }
         }
            break;
         case ';':
         {
            alt3 = 8;
         }
            break;
         case '=':
         {
            alt3 = 9;
         }
            break;
         case '-':
         {
            int LA3_8 = input.LA( 2 );
            
            if ( ( ( LA3_8 >= '0' && LA3_8 <= '9' ) ) )
            {
               alt3 = 24;
            }
            else
            {
               alt3 = 10;
            }
         }
            break;
         case ',':
         {
            alt3 = 11;
         }
            break;
         case 'B':
         {
            int LA3_10 = input.LA( 2 );
            
            if ( ( LA3_10 == 'Y' ) )
            {
               int LA3_27 = input.LA( 3 );
               
               if ( ( LA3_27 == 'D' ) )
               {
                  alt3 = 12;
               }
               else if ( ( LA3_27 == 'M' ) )
               {
                  int LA3_36 = input.LA( 4 );
                  
                  if ( ( LA3_36 == 'O' ) )
                  {
                     int LA3_38 = input.LA( 5 );
                     
                     if ( ( LA3_38 == 'N' ) )
                     {
                        int LA3_40 = input.LA( 6 );
                        
                        if ( ( LA3_40 == 'T' ) )
                        {
                           int LA3_42 = input.LA( 7 );
                           
                           if ( ( LA3_42 == 'H' ) )
                           {
                              int LA3_44 = input.LA( 8 );
                              
                              if ( ( LA3_44 == 'D' ) )
                              {
                                 alt3 = 14;
                              }
                              else
                              {
                                 alt3 = 13;
                              }
                           }
                           else
                           {
                              NoViableAltException nvae = new NoViableAltException( "",
                                                                                    3,
                                                                                    42,
                                                                                    input );
                              
                              throw nvae;
                              
                           }
                        }
                        else
                        {
                           NoViableAltException nvae = new NoViableAltException( "",
                                                                                 3,
                                                                                 40,
                                                                                 input );
                           
                           throw nvae;
                           
                        }
                     }
                     else
                     {
                        NoViableAltException nvae = new NoViableAltException( "",
                                                                              3,
                                                                              38,
                                                                              input );
                        
                        throw nvae;
                        
                     }
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           3,
                                                                           36,
                                                                           input );
                     
                     throw nvae;
                     
                  }
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        3,
                                                                        27,
                                                                        input );
                  
                  throw nvae;
                  
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     3,
                                                                     10,
                                                                     input );
               
               throw nvae;
               
            }
         }
            break;
         case 'I':
         {
            alt3 = 15;
         }
            break;
         case 'U':
         {
            alt3 = 17;
         }
            break;
         case 'C':
         {
            alt3 = 18;
         }
            break;
         case 'D':
         {
            alt3 = 19;
         }
            break;
         case 'Y':
         {
            alt3 = 22;
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
            int LA3_16 = input.LA( 2 );
            
            if ( ( ( LA3_16 >= '0' && LA3_16 <= '9' ) ) )
            {
               int LA3_28 = input.LA( 3 );
               
               if ( ( ( LA3_28 >= '0' && LA3_28 <= '9' ) ) )
               {
                  int LA3_37 = input.LA( 4 );
                  
                  if ( ( ( LA3_37 >= '0' && LA3_37 <= '9' ) ) )
                  {
                     int LA3_39 = input.LA( 5 );
                     
                     if ( ( ( LA3_39 >= '0' && LA3_39 <= '9' ) ) )
                     {
                        int LA3_41 = input.LA( 6 );
                        
                        if ( ( ( LA3_41 >= '0' && LA3_41 <= '9' ) ) )
                        {
                           int LA3_43 = input.LA( 7 );
                           
                           if ( ( ( LA3_43 >= '0' && LA3_43 <= '9' ) ) )
                           {
                              int LA3_45 = input.LA( 8 );
                              
                              if ( ( ( LA3_45 >= '0' && LA3_45 <= '9' ) ) )
                              {
                                 int LA3_48 = input.LA( 9 );
                                 
                                 if ( ( LA3_48 == 'T' ) )
                                 {
                                    alt3 = 23;
                                 }
                                 else
                                 {
                                    alt3 = 24;
                                 }
                              }
                              else
                              {
                                 alt3 = 24;
                              }
                           }
                           else
                           {
                              alt3 = 24;
                           }
                        }
                        else
                        {
                           alt3 = 24;
                        }
                     }
                     else
                     {
                        alt3 = 24;
                     }
                  }
                  else
                  {
                     alt3 = 24;
                  }
               }
               else
               {
                  alt3 = 24;
               }
            }
            else
            {
               alt3 = 24;
            }
         }
            break;
         case '\t':
         case '\n':
         case '\r':
         case ' ':
         {
            alt3 = 25;
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:10:
         // MONDAY
         {
            mMONDAY();
            
         }
            break;
         case 2:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:17:
         // TUESDAY
         {
            mTUESDAY();
            
         }
            break;
         case 3:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:25:
         // WEDNESDAY
         {
            mWEDNESDAY();
            
         }
            break;
         case 4:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:35:
         // THURSDAY
         {
            mTHURSDAY();
            
         }
            break;
         case 5:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:44:
         // FRIDAY
         {
            mFRIDAY();
            
         }
            break;
         case 6:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:51:
         // SATURDAY
         {
            mSATURDAY();
            
         }
            break;
         case 7:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:60:
         // SUNDAY
         {
            mSUNDAY();
            
         }
            break;
         case 8:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:67:
         // SEMICOLON
         {
            mSEMICOLON();
            
         }
            break;
         case 9:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:77:
         // EQUALS
         {
            mEQUALS();
            
         }
            break;
         case 10:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:84:
         // MINUS
         {
            mMINUS();
            
         }
            break;
         case 11:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:90:
         // COMMA
         {
            mCOMMA();
            
         }
            break;
         case 12:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:96:
         // OP_BYDAY
         {
            mOP_BYDAY();
            
         }
            break;
         case 13:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:105:
         // OP_BYMONTH
         {
            mOP_BYMONTH();
            
         }
            break;
         case 14:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:116:
         // OP_BYMONTHDAY
         {
            mOP_BYMONTHDAY();
            
         }
            break;
         case 15:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:130:
         // OP_INTERVAL
         {
            mOP_INTERVAL();
            
         }
            break;
         case 16:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:142:
         // OP_FREQ
         {
            mOP_FREQ();
            
         }
            break;
         case 17:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:150:
         // OP_UNTIL
         {
            mOP_UNTIL();
            
         }
            break;
         case 18:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:159:
         // OP_COUNT
         {
            mOP_COUNT();
            
         }
            break;
         case 19:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:168:
         // VAL_DAILY
         {
            mVAL_DAILY();
            
         }
            break;
         case 20:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:178:
         // VAL_WEEKLY
         {
            mVAL_WEEKLY();
            
         }
            break;
         case 21:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:189:
         // VAL_MONTHLY
         {
            mVAL_MONTHLY();
            
         }
            break;
         case 22:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:201:
         // VAL_YEARLY
         {
            mVAL_YEARLY();
            
         }
            break;
         case 23:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:212:
         // VAL_DATE
         {
            mVAL_DATE();
            
         }
            break;
         case 24:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:221:
         // INT
         {
            mINT();
            
         }
            break;
         case 25:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:1:225:
         // WS
         {
            mWS();
            
         }
            break;
      
      }
      
   }
   
}
