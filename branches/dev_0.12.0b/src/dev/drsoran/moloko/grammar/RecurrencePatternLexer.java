// $ANTLR 3.3 Nov 30, 2010 12:45:30 F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g 2011-02-24 08:07:33

package dev.drsoran.moloko.grammar;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;


public class RecurrencePatternLexer extends Lexer
{
   public static final int EOF = -1;
   
   public static final int OP_FREQ = 4;
   
   public static final int VAL_YEARLY = 5;
   
   public static final int OP_BYDAY = 6;
   
   public static final int COMMA = 7;
   
   public static final int OP_BYMONTH = 8;
   
   public static final int VAL_MONTHLY = 9;
   
   public static final int OP_BYMONTHDAY = 10;
   
   public static final int VAL_WEEKLY = 11;
   
   public static final int VAL_DAILY = 12;
   
   public static final int OP_UNTIL = 13;
   
   public static final int VAL_DATE = 14;
   
   public static final int OP_COUNT = 15;
   
   public static final int INT = 16;
   
   public static final int OP_INTERVAL = 17;
   
   public static final int MONDAY = 18;
   
   public static final int TUESDAY = 19;
   
   public static final int WEDNESDAY = 20;
   
   public static final int THURSDAY = 21;
   
   public static final int FRIDAY = 22;
   
   public static final int SATURDAY = 23;
   
   public static final int SUNDAY = 24;
   
   public static final int SEMICOLON = 25;
   
   public static final int EQUALS = 26;
   
   public static final int MINUS = 27;
   
   public static final int NUMBER = 28;
   
   public static final int WS = 29;
   
   

   // delegates
   // delegators
   
   public RecurrencePatternLexer()
   {
      ;
   }
   


   public RecurrencePatternLexer( CharStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public RecurrencePatternLexer( CharStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   @Override
   public String getGrammarFileName()
   {
      return "F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g";
   }
   


   // $ANTLR start "MONDAY"
   public final void mMONDAY() throws RecognitionException
   {
      try
      {
         int _type = MONDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:283:15: ( 'MO' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:283:17: 'MO'
         {
            match( "MO" );
            
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:285:15: ( 'TU' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:285:17: 'TU'
         {
            match( "TU" );
            
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:287:15: ( 'WE' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:287:17: 'WE'
         {
            match( "WE" );
            
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:289:15: ( 'TH' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:289:17: 'TH'
         {
            match( "TH" );
            
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:291:15: ( 'FR' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:291:17: 'FR'
         {
            match( "FR" );
            
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:293:15: ( 'SA' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:293:17: 'SA'
         {
            match( "SA" );
            
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:295:15: ( 'SU' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:295:17: 'SU'
         {
            match( "SU" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:297:15: ( ';' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:297:17: ';'
         {
            match( ';' );
            _channel = HIDDEN;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:299:15: ( '=' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:299:17: '='
         {
            match( '=' );
            _channel = HIDDEN;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:301:15: ( '-' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:301:17: '-'
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:303:15: ( ',' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:303:17: ','
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
   
   // $ANTLR start "OP_BYDAY"
   public final void mOP_BYDAY() throws RecognitionException
   {
      try
      {
         int _type = OP_BYDAY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:305:15: ( 'BYDAY' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:305:17: 'BYDAY'
         {
            match( "BYDAY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:307:15: ( 'BYMONTH' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:307:17: 'BYMONTH'
         {
            match( "BYMONTH" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:309:15: ( 'BYMONTHDAY' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:309:17: 'BYMONTHDAY'
         {
            match( "BYMONTHDAY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:311:15: ( 'INTERVAL' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:311:17: 'INTERVAL'
         {
            match( "INTERVAL" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:313:15: ( 'FREQ' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:313:17: 'FREQ'
         {
            match( "FREQ" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:315:15: ( 'UNTIL' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:315:17: 'UNTIL'
         {
            match( "UNTIL" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:317:15: ( 'COUNT' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:317:17: 'COUNT'
         {
            match( "COUNT" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:319:15: ( 'DAILY' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:319:17: 'DAILY'
         {
            match( "DAILY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:321:15: ( 'WEEKLY' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:321:17: 'WEEKLY'
         {
            match( "WEEKLY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:323:15: ( 'MONTHLY' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:323:17: 'MONTHLY'
         {
            match( "MONTHLY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:325:15: ( 'YEARLY' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:325:17: 'YEARLY'
         {
            match( "YEARLY" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:328:15: ( NUMBER NUMBER
         // NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER 'T' NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:328:17: NUMBER NUMBER
         // NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER 'T' NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER
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
      }
   }
   


   // $ANTLR end "VAL_DATE"
   
   // $ANTLR start "NUMBER"
   public final void mNUMBER() throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:332:15: ( '0' .. '9' )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:332:17: '0' .. '9'
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:334:15: ( ( MINUS )? (
         // NUMBER )+ )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:334:17: ( MINUS )? ( NUMBER
         // )+
         {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:334:17: ( MINUS )?
            int alt1 = 2;
            int LA1_0 = input.LA( 1 );
            
            if ( ( LA1_0 == '-' ) )
            {
               alt1 = 1;
            }
            switch ( alt1 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:334:17: MINUS
               {
                  mMINUS();
                  
               }
                  break;
               
            }
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:334:24: ( NUMBER )+
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
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:334:24: NUMBER
                  {
                     mNUMBER();
                     
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
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:336:15: ( ( ' ' | '\\t' |
         // '\\r' | '\\n' ) )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:336:17: ( ' ' | '\\t' |
         // '\\r' | '\\n' )
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
      // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:8: ( MONDAY | TUESDAY |
      // WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | SEMICOLON | EQUALS | MINUS | COMMA | OP_BYDAY | OP_BYMONTH
      // | OP_BYMONTHDAY | OP_INTERVAL | OP_FREQ | OP_UNTIL | OP_COUNT | VAL_DAILY | VAL_WEEKLY | VAL_MONTHLY |
      // VAL_YEARLY | VAL_DATE | INT | WS )
      int alt3 = 25;
      alt3 = dfa3.predict( input );
      switch ( alt3 )
      {
         case 1:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:10: MONDAY
         {
            mMONDAY();
            
         }
            break;
         case 2:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:17: TUESDAY
         {
            mTUESDAY();
            
         }
            break;
         case 3:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:25: WEDNESDAY
         {
            mWEDNESDAY();
            
         }
            break;
         case 4:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:35: THURSDAY
         {
            mTHURSDAY();
            
         }
            break;
         case 5:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:44: FRIDAY
         {
            mFRIDAY();
            
         }
            break;
         case 6:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:51: SATURDAY
         {
            mSATURDAY();
            
         }
            break;
         case 7:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:60: SUNDAY
         {
            mSUNDAY();
            
         }
            break;
         case 8:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:67: SEMICOLON
         {
            mSEMICOLON();
            
         }
            break;
         case 9:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:77: EQUALS
         {
            mEQUALS();
            
         }
            break;
         case 10:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:84: MINUS
         {
            mMINUS();
            
         }
            break;
         case 11:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:90: COMMA
         {
            mCOMMA();
            
         }
            break;
         case 12:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:96: OP_BYDAY
         {
            mOP_BYDAY();
            
         }
            break;
         case 13:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:105: OP_BYMONTH
         {
            mOP_BYMONTH();
            
         }
            break;
         case 14:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:116: OP_BYMONTHDAY
         {
            mOP_BYMONTHDAY();
            
         }
            break;
         case 15:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:130: OP_INTERVAL
         {
            mOP_INTERVAL();
            
         }
            break;
         case 16:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:142: OP_FREQ
         {
            mOP_FREQ();
            
         }
            break;
         case 17:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:150: OP_UNTIL
         {
            mOP_UNTIL();
            
         }
            break;
         case 18:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:159: OP_COUNT
         {
            mOP_COUNT();
            
         }
            break;
         case 19:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:168: VAL_DAILY
         {
            mVAL_DAILY();
            
         }
            break;
         case 20:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:178: VAL_WEEKLY
         {
            mVAL_WEEKLY();
            
         }
            break;
         case 21:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:189: VAL_MONTHLY
         {
            mVAL_MONTHLY();
            
         }
            break;
         case 22:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:201: VAL_YEARLY
         {
            mVAL_YEARLY();
            
         }
            break;
         case 23:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:212: VAL_DATE
         {
            mVAL_DATE();
            
         }
            break;
         case 24:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:221: INT
         {
            mINT();
            
         }
            break;
         case 25:
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:1:225: WS
         {
            mWS();
            
         }
            break;
         
      }
      
   }
   
   protected DFA3 dfa3 = new DFA3( this );
   
   static final String DFA3_eotS = "\10\uffff\1\31\7\uffff\1\32\1\uffff\1\36\2\uffff\1\40\1\42\5\uffff"
      + "\1\32\10\uffff\1\32\1\uffff\1\32\1\uffff\1\32\1\uffff\1\32\1\57"
      + "\1\32\2\uffff\1\32\1\uffff";
   
   static final String DFA3_eofS = "\62\uffff";
   
   static final String DFA3_minS = "\1\11\1\117\1\110\1\105\1\122\1\101\2\uffff\1\60\1\uffff\1\131"
      + "\5\uffff\1\60\1\uffff\1\116\2\uffff\2\105\4\uffff\1\104\1\60\7\uffff"
      + "\1\117\1\60\1\116\1\60\1\124\1\60\1\110\1\60\1\104\1\60\2\uffff"
      + "\1\124\1\uffff";
   
   static final String DFA3_maxS = "\1\131\1\117\1\125\1\105\1\122\1\125\2\uffff\1\71\1\uffff\1\131"
      + "\5\uffff\1\71\1\uffff\1\116\2\uffff\2\105\4\uffff\1\115\1\71\7\uffff"
      + "\1\117\1\71\1\116\1\71\1\124\1\71\1\110\1\71\1\104\1\71\2\uffff"
      + "\1\124\1\uffff";
   
   static final String DFA3_acceptS = "\6\uffff\1\10\1\11\1\uffff\1\13\1\uffff\1\17\1\21\1\22\1\23\1\26"
      + "\1\uffff\1\31\1\uffff\1\2\1\4\2\uffff\1\6\1\7\1\12\1\30\2\uffff"
      + "\1\25\1\1\1\24\1\3\1\20\1\5\1\14\12\uffff\1\16\1\15\1\uffff\1\27";
   
   static final String DFA3_specialS = "\62\uffff}>";
   
   static final String[] DFA3_transitionS =
   {
    "\2\21\2\uffff\1\21\22\uffff\1\21\13\uffff\1\11\1\10\2\uffff"
       + "\12\20\1\uffff\1\6\1\uffff\1\7\4\uffff\1\12\1\15\1\16\1\uffff"
       + "\1\4\2\uffff\1\13\3\uffff\1\1\5\uffff\1\5\1\2\1\14\1\uffff\1"
       + "\3\1\uffff\1\17", "\1\22", "\1\24\14\uffff\1\23", "\1\25", "\1\26",
    "\1\27\23\uffff\1\30", "", "", "\12\32", "", "\1\33", "", "", "", "", "",
    "\12\34", "", "\1\35", "", "", "\1\37", "\1\41", "", "", "", "",
    "\1\43\10\uffff\1\44", "\12\45", "", "", "", "", "", "", "", "\1\46",
    "\12\47", "\1\50", "\12\51", "\1\52", "\12\53", "\1\54", "\12\55", "\1\56",
    "\12\60", "", "", "\1\61", "" };
   
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
      


      @Override
      public String getDescription()
      {
         return "1:1: Tokens : ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | SEMICOLON | EQUALS | MINUS | COMMA | OP_BYDAY | OP_BYMONTH | OP_BYMONTHDAY | OP_INTERVAL | OP_FREQ | OP_UNTIL | OP_COUNT | VAL_DAILY | VAL_WEEKLY | VAL_MONTHLY | VAL_YEARLY | VAL_DATE | INT | WS );";
      }
   }
   
}