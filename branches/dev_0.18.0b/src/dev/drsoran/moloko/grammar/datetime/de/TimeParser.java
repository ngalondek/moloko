// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g 2013-07-13 10:23:53

package dev.drsoran.moloko.grammar.datetime.de;

import java.util.Calendar;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.LexerException;
import dev.drsoran.moloko.grammar.datetime.AbstractANTLRTimeParser;
import dev.drsoran.moloko.grammar.datetime.ParseReturn;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class TimeParser extends AbstractANTLRTimeParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AM", "AND", "AT", "COLON",
    "COMMA", "DAYS", "DOT", "HOURS", "INT", "MIDDAY", "MIDNIGHT", "MINUTES",
    "NEVER", "PM", "SECONDS", "WS" };
   
   public static final int EOF = -1;
   
   public static final int AM = 4;
   
   public static final int AND = 5;
   
   public static final int AT = 6;
   
   public static final int COLON = 7;
   
   public static final int COMMA = 8;
   
   public static final int DAYS = 9;
   
   public static final int DOT = 10;
   
   public static final int HOURS = 11;
   
   public static final int INT = 12;
   
   public static final int MIDDAY = 13;
   
   public static final int MIDNIGHT = 14;
   
   public static final int MINUTES = 15;
   
   public static final int NEVER = 16;
   
   public static final int PM = 17;
   
   public static final int SECONDS = 18;
   
   public static final int WS = 19;
   
   
   
   // delegates
   public AbstractANTLRTimeParser[] getDelegates()
   {
      return new AbstractANTLRTimeParser[] {};
   }
   
   
   
   // delegators
   
   public TimeParser( TokenStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   
   
   
   public TimeParser( TokenStream input, RecognizerSharedState state )
   {
      super( input, state );
   }
   
   
   
   public String[] getTokenNames()
   {
      return TimeParser.tokenNames;
   }
   
   
   
   public String getGrammarFileName()
   {
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g";
   }
   
   
   
   public TimeParser()
   {
      super( null );
   }
   
   
   
   // $ANTLR start "parseTime"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:62:1:
   // parseTime[MolokoCalendar cal, boolean adjustDay] returns [ParseReturn result] : ( AT | COMMA )? (
   // literal_time[$cal] | separated_time[$cal] | unseparated_time[$cal] | h_m_s_separated_time[$cal] ) ( am_pm[$cal] )?
   // ;
   public final ParseReturn parseTime( MolokoCalendar cal, boolean adjustDay ) throws RecognitionException
   {
      ParseReturn result = null;
      
      startParsingTime( cal );
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:71:4:
         // ( ( AT | COMMA )? ( literal_time[$cal] | separated_time[$cal] | unseparated_time[$cal] |
         // h_m_s_separated_time[$cal] ) ( am_pm[$cal] )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:71:6:
         // ( AT | COMMA )? ( literal_time[$cal] | separated_time[$cal] | unseparated_time[$cal] |
         // h_m_s_separated_time[$cal] ) ( am_pm[$cal] )?
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:71:6:
            // ( AT | COMMA )?
            int alt1 = 2;
            int LA1_0 = input.LA( 1 );
            
            if ( ( LA1_0 == AT || LA1_0 == COMMA ) )
            {
               alt1 = 1;
            }
            switch ( alt1 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
               {
                  if ( input.LA( 1 ) == AT || input.LA( 1 ) == COMMA )
                  {
                     input.consume();
                     state.errorRecovery = false;
                  }
                  else
                  {
                     MismatchedSetException mse = new MismatchedSetException( null,
                                                                              input );
                     throw mse;
                  }
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:72:6:
            // ( literal_time[$cal] | separated_time[$cal] | unseparated_time[$cal] | h_m_s_separated_time[$cal] )
            int alt2 = 4;
            int LA2_0 = input.LA( 1 );
            
            if ( ( ( LA2_0 >= MIDDAY && LA2_0 <= MIDNIGHT ) || LA2_0 == NEVER ) )
            {
               alt2 = 1;
            }
            else if ( ( LA2_0 == INT ) )
            {
               switch ( input.LA( 2 ) )
               {
                  case DOT:
                  {
                     int LA2_3 = input.LA( 3 );
                     
                     if ( ( LA2_3 == INT ) )
                     {
                        int LA2_7 = input.LA( 4 );
                        
                        if ( ( LA2_7 == HOURS ) )
                        {
                           alt2 = 4;
                        }
                        else if ( ( LA2_7 == EOF || LA2_7 == AM
                           || LA2_7 == COLON || LA2_7 == DOT || LA2_7 == PM ) )
                        {
                           alt2 = 2;
                        }
                        else
                        {
                           NoViableAltException nvae = new NoViableAltException( "",
                                                                                 2,
                                                                                 7,
                                                                                 input );
                           
                           throw nvae;
                           
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
                     break;
                  case COLON:
                  {
                     alt2 = 2;
                  }
                     break;
                  case COMMA:
                  case HOURS:
                  case MINUTES:
                  case SECONDS:
                  {
                     alt2 = 4;
                  }
                     break;
                  case EOF:
                  case AM:
                  case PM:
                  {
                     alt2 = 3;
                  }
                     break;
                  default :
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
                                                                     0,
                                                                     input );
               
               throw nvae;
               
            }
            switch ( alt2 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:73:11:
               // literal_time[$cal]
               {
                  pushFollow( FOLLOW_literal_time_in_parseTime131 );
                  literal_time( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:74:11:
               // separated_time[$cal]
               {
                  pushFollow( FOLLOW_separated_time_in_parseTime144 );
                  separated_time( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:75:11:
               // unseparated_time[$cal]
               {
                  pushFollow( FOLLOW_unseparated_time_in_parseTime157 );
                  unseparated_time( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:76:11:
               // h_m_s_separated_time[$cal]
               {
                  pushFollow( FOLLOW_h_m_s_separated_time_in_parseTime170 );
                  h_m_s_separated_time( cal );
                  
                  state._fsp--;
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:78:6:
            // ( am_pm[$cal] )?
            int alt3 = 2;
            int LA3_0 = input.LA( 1 );
            
            if ( ( LA3_0 == AM || LA3_0 == PM ) )
            {
               alt3 = 1;
            }
            switch ( alt3 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:78:6:
               // am_pm[$cal]
               {
                  pushFollow( FOLLOW_am_pm_in_parseTime185 );
                  am_pm( cal );
                  
                  state._fsp--;
                  
               }
                  break;
            
            }
            
            if ( adjustDay && getCalendar().after( cal.toCalendar() ) )
            {
               cal.add( Calendar.DAY_OF_WEEK, 1 );
            }
            
         }
         
         result = finishedParsingTime( cal );
         
      }
      catch ( RecognitionException e )
      {
         
         notifyParsingTimeFailed();
         throw e;
         
      }
      catch ( LexerException e )
      {
         
         notifyParsingTimeFailed();
         throw new RecognitionException();
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return result;
   }
   
   
   
   // $ANTLR end "parseTime"
   
   // $ANTLR start "literal_time"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:97:1:
   // literal_time[MolokoCalendar cal] : ( NEVER | MIDNIGHT | MIDDAY );
   public final void literal_time( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:98:4:
         // ( NEVER | MIDNIGHT | MIDDAY )
         int alt4 = 3;
         switch ( input.LA( 1 ) )
         {
            case NEVER:
            {
               alt4 = 1;
            }
               break;
            case MIDNIGHT:
            {
               alt4 = 2;
            }
               break;
            case MIDDAY:
            {
               alt4 = 3;
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:98:6:
            // NEVER
            {
               match( input, NEVER, FOLLOW_NEVER_in_literal_time231 );
               
               cal.setHasDate( false );
               cal.setHasTime( false );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:103:6:
            // MIDNIGHT
            {
               match( input, MIDNIGHT, FOLLOW_MIDNIGHT_in_literal_time243 );
               
               cal.set( Calendar.HOUR_OF_DAY, 23 );
               cal.set( Calendar.MINUTE, 59 );
               cal.set( Calendar.SECOND, 59 );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:109:6:
            // MIDDAY
            {
               match( input, MIDDAY, FOLLOW_MIDDAY_in_literal_time255 );
               
               cal.set( Calendar.HOUR_OF_DAY, 12 );
               cal.set( Calendar.MINUTE, 00 );
               cal.set( Calendar.SECOND, 00 );
               
            }
               break;
         
         }
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "literal_time"
   
   // $ANTLR start "separated_time"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:121:1:
   // separated_time[MolokoCalendar cal] : h= INT ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? ;
   public final void separated_time( MolokoCalendar cal ) throws RecognitionException
   {
      Token h = null;
      Token m = null;
      Token s = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:122:9:
         // (h= INT ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:122:11:
         // h= INT ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )?
         {
            h = (Token) match( input, INT, FOLLOW_INT_in_separated_time295 );
            
            if ( input.LA( 1 ) == COLON || input.LA( 1 ) == DOT )
            {
               input.consume();
               state.errorRecovery = false;
            }
            else
            {
               MismatchedSetException mse = new MismatchedSetException( null,
                                                                        input );
               throw mse;
            }
            
            m = (Token) match( input, INT, FOLLOW_INT_in_separated_time305 );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:122:35:
            // ( ( COLON | DOT ) s= INT )?
            int alt5 = 2;
            int LA5_0 = input.LA( 1 );
            
            if ( ( LA5_0 == COLON || LA5_0 == DOT ) )
            {
               alt5 = 1;
            }
            switch ( alt5 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:122:36:
               // ( COLON | DOT ) s= INT
               {
                  if ( input.LA( 1 ) == COLON || input.LA( 1 ) == DOT )
                  {
                     input.consume();
                     state.errorRecovery = false;
                  }
                  else
                  {
                     MismatchedSetException mse = new MismatchedSetException( null,
                                                                              input );
                     throw mse;
                  }
                  
                  s = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_separated_time316 );
                  
               }
                  break;
            
            }
            
            cal.set( Calendar.HOUR_OF_DAY,
                     ( h != null ? Integer.valueOf( h.getText() ) : 0 ) );
            cal.set( Calendar.MINUTE,
                     ( m != null ? Integer.valueOf( m.getText() ) : 0 ) );
            cal.set( Calendar.SECOND,
                     ( s != null ? Integer.valueOf( s.getText() ) : 0 ) );
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "separated_time"
   
   // $ANTLR start "h_m_s_separated_time"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:134:1:
   // h_m_s_separated_time[MolokoCalendar cal] : time_naturalspec[$cal] ( time_naturalspec[$cal] (
   // time_naturalspec[$cal] )? )? ;
   public final void h_m_s_separated_time( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:135:9:
         // ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:135:11:
         // time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
         {
            pushFollow( FOLLOW_time_naturalspec_in_h_m_s_separated_time370 );
            time_naturalspec( cal );
            
            state._fsp--;
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:135:34:
            // ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
            int alt7 = 2;
            int LA7_0 = input.LA( 1 );
            
            if ( ( LA7_0 == INT ) )
            {
               alt7 = 1;
            }
            switch ( alt7 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:135:36:
               // time_naturalspec[$cal] ( time_naturalspec[$cal] )?
               {
                  pushFollow( FOLLOW_time_naturalspec_in_h_m_s_separated_time375 );
                  time_naturalspec( cal );
                  
                  state._fsp--;
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:135:59:
                  // ( time_naturalspec[$cal] )?
                  int alt6 = 2;
                  int LA6_0 = input.LA( 1 );
                  
                  if ( ( LA6_0 == INT ) )
                  {
                     alt6 = 1;
                  }
                  switch ( alt6 )
                  {
                     case 1:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:135:59:
                     // time_naturalspec[$cal]
                     {
                        pushFollow( FOLLOW_time_naturalspec_in_h_m_s_separated_time378 );
                        time_naturalspec( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                  
                  }
                  
               }
                  break;
            
            }
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "h_m_s_separated_time"
   
   // $ANTLR start "unseparated_time"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:142:1:
   // unseparated_time[MolokoCalendar cal] : v= INT ;
   public final void unseparated_time( MolokoCalendar cal ) throws RecognitionException
   {
      Token v = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:143:9:
         // (v= INT )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:143:11:
         // v= INT
         {
            v = (Token) match( input, INT, FOLLOW_INT_in_unseparated_time417 );
            
            setCalendarTime( cal, ( v != null ? v.getText() : null ) );
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "unseparated_time"
   
   // $ANTLR start "am_pm"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:153:1:
   // am_pm[MolokoCalendar cal] : ( AM | PM );
   public final void am_pm( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:154:4:
         // ( AM | PM )
         int alt8 = 2;
         int LA8_0 = input.LA( 1 );
         
         if ( ( LA8_0 == AM ) )
         {
            alt8 = 1;
         }
         else if ( ( LA8_0 == PM ) )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:154:6:
            // AM
            {
               match( input, AM, FOLLOW_AM_in_am_pm460 );
               
               cal.set( Calendar.AM_PM, Calendar.AM );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:158:6:
            // PM
            {
               match( input, PM, FOLLOW_PM_in_am_pm472 );
               
               cal.set( Calendar.AM_PM, Calendar.PM );
               
            }
               break;
         
         }
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "am_pm"
   
   // $ANTLR start "time_naturalspec"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:172:1:
   // time_naturalspec[MolokoCalendar cal] returns [int seconds] : (fs= hour_floatspec |v= INT ( HOURS | MINUTES |
   // SECONDS ) );
   public final int time_naturalspec( MolokoCalendar cal ) throws RecognitionException
   {
      int seconds = 0;
      
      Token v = null;
      int fs = 0;
      
      int calType = -1;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:182:4:
         // (fs= hour_floatspec |v= INT ( HOURS | MINUTES | SECONDS ) )
         int alt10 = 2;
         int LA10_0 = input.LA( 1 );
         
         if ( ( LA10_0 == INT ) )
         {
            int LA10_1 = input.LA( 2 );
            
            if ( ( LA10_1 == COMMA || LA10_1 == DOT ) )
            {
               alt10 = 1;
            }
            else if ( ( LA10_1 == HOURS || LA10_1 == MINUTES || LA10_1 == SECONDS ) )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:182:8:
            // fs= hour_floatspec
            {
               pushFollow( FOLLOW_hour_floatspec_in_time_naturalspec539 );
               fs = hour_floatspec();
               
               state._fsp--;
               
               seconds += fs;
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:186:8:
            // v= INT ( HOURS | MINUTES | SECONDS )
            {
               v = (Token) match( input, INT, FOLLOW_INT_in_time_naturalspec559 );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:186:14:
               // ( HOURS | MINUTES | SECONDS )
               int alt9 = 3;
               switch ( input.LA( 1 ) )
               {
                  case HOURS:
                  {
                     alt9 = 1;
                  }
                     break;
                  case MINUTES:
                  {
                     alt9 = 2;
                  }
                     break;
                  case SECONDS:
                  {
                     alt9 = 3;
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
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:186:16:
                  // HOURS
                  {
                     match( input, HOURS, FOLLOW_HOURS_in_time_naturalspec563 );
                     
                     calType = Calendar.HOUR_OF_DAY;
                     
                  }
                     break;
                  case 2:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:190:18:
                  // MINUTES
                  {
                     match( input,
                            MINUTES,
                            FOLLOW_MINUTES_in_time_naturalspec599 );
                     
                     calType = Calendar.MINUTE;
                     
                  }
                     break;
                  case 3:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:194:18:
                  // SECONDS
                  {
                     match( input,
                            SECONDS,
                            FOLLOW_SECONDS_in_time_naturalspec635 );
                     
                     calType = Calendar.SECOND;
                     
                  }
                     break;
               
               }
               
               int val = Integer.parseInt( ( v != null ? v.getText() : null ) );
               
               switch ( calType )
               {
                  case Calendar.HOUR_OF_DAY:
                     seconds += val * 3600;
                     break;
                  case Calendar.MINUTE:
                     seconds += val * 60;
                     break;
                  case Calendar.SECOND:
                     seconds += val;
                     break;
                  default :
                     throw new RecognitionException();
               }
               
            }
               break;
         
         }
         
         if ( cal != null )
            cal.add( Calendar.SECOND, seconds );
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return seconds;
   }
   
   
   
   // $ANTLR end "time_naturalspec"
   
   // $ANTLR start "hour_floatspec"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:227:1:
   // hour_floatspec returns [int seconds] : h= INT ( DOT | COMMA ) deciHour= INT HOURS ;
   public final int hour_floatspec() throws RecognitionException
   {
      int seconds = 0;
      
      Token h = null;
      Token deciHour = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:228:4:
         // (h= INT ( DOT | COMMA ) deciHour= INT HOURS )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:228:6:
         // h= INT ( DOT | COMMA ) deciHour= INT HOURS
         {
            h = (Token) match( input, INT, FOLLOW_INT_in_hour_floatspec719 );
            
            if ( input.LA( 1 ) == COMMA || input.LA( 1 ) == DOT )
            {
               input.consume();
               state.errorRecovery = false;
            }
            else
            {
               MismatchedSetException mse = new MismatchedSetException( null,
                                                                        input );
               throw mse;
            }
            
            deciHour = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_hour_floatspec729 );
            
            match( input, HOURS, FOLLOW_HOURS_in_hour_floatspec731 );
            
            seconds = Integer.parseInt( ( h != null ? h.getText() : null ) ) * 3600;
            seconds += Integer.parseInt( ( deciHour != null
                                                           ? deciHour.getText()
                                                           : null ) ) * 360;
            
         }
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return seconds;
   }
   
   
   
   // $ANTLR end "hour_floatspec"
   
   // $ANTLR start "parseTimeEstimate"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:249:1:
   // parseTimeEstimate returns [long span] : (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA |
   // AND )* ;
   public final long parseTimeEstimate() throws RecognitionException
   {
      long span = 0;
      
      Token d = null;
      int ts = 0;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:254:4:
         // ( (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )* )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:254:6:
         // (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )*
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:254:6:
            // (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+
            int cnt12 = 0;
            loop12: do
            {
               int alt12 = 3;
               int LA12_0 = input.LA( 1 );
               
               if ( ( LA12_0 == AND || LA12_0 == COMMA ) )
               {
                  int LA12_1 = input.LA( 2 );
                  
                  if ( ( LA12_1 == INT ) )
                  {
                     alt12 = 2;
                  }
                  
               }
               else if ( ( LA12_0 == INT ) )
               {
                  int LA12_3 = input.LA( 2 );
                  
                  if ( ( LA12_3 == DAYS ) )
                  {
                     alt12 = 1;
                  }
                  else if ( ( LA12_3 == COMMA
                     || ( LA12_3 >= DOT && LA12_3 <= HOURS )
                     || LA12_3 == MINUTES || LA12_3 == SECONDS ) )
                  {
                     alt12 = 2;
                  }
                  
               }
               
               switch ( alt12 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:254:10:
                  // d= INT DAYS
                  {
                     d = (Token) match( input,
                                        INT,
                                        FOLLOW_INT_in_parseTimeEstimate796 );
                     
                     match( input, DAYS, FOLLOW_DAYS_in_parseTimeEstimate798 );
                     
                     span += Integer.parseInt( ( d != null ? d.getText() : null ) ) * 3600 * 24;
                     
                  }
                     break;
                  case 2:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:258:10:
                  // ( COMMA | AND )? ts= time_naturalspec[null]
                  {
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:258:10:
                     // ( COMMA | AND )?
                     int alt11 = 2;
                     int LA11_0 = input.LA( 1 );
                     
                     if ( ( LA11_0 == AND || LA11_0 == COMMA ) )
                     {
                        alt11 = 1;
                     }
                     switch ( alt11 )
                     {
                        case 1:
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                        {
                           if ( input.LA( 1 ) == AND || input.LA( 1 ) == COMMA )
                           {
                              input.consume();
                              state.errorRecovery = false;
                           }
                           else
                           {
                              MismatchedSetException mse = new MismatchedSetException( null,
                                                                                       input );
                              throw mse;
                           }
                           
                        }
                           break;
                     
                     }
                     
                     pushFollow( FOLLOW_time_naturalspec_in_parseTimeEstimate831 );
                     ts = time_naturalspec( null );
                     
                     state._fsp--;
                     
                     span += ts;
                     
                  }
                     break;
                  
                  default :
                     if ( cnt12 >= 1 )
                        break loop12;
                     EarlyExitException eee = new EarlyExitException( 12, input );
                     throw eee;
               }
               cnt12++;
            }
            while ( true );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:262:9:
            // ( COMMA | AND )*
            loop13: do
            {
               int alt13 = 2;
               int LA13_0 = input.LA( 1 );
               
               if ( ( LA13_0 == AND || LA13_0 == COMMA ) )
               {
                  alt13 = 1;
               }
               
               switch ( alt13 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                  {
                     if ( input.LA( 1 ) == AND || input.LA( 1 ) == COMMA )
                     {
                        input.consume();
                        state.errorRecovery = false;
                     }
                     else
                     {
                        MismatchedSetException mse = new MismatchedSetException( null,
                                                                                 input );
                        throw mse;
                     }
                     
                  }
                     break;
                  
                  default :
                     break loop13;
               }
            }
            while ( true );
            
         }
         
         span *= 1000;
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      catch ( LexerException e )
      {
         
         throw new RecognitionException();
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return span;
   }
   
   // $ANTLR end "parseTimeEstimate"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_literal_time_in_parseTime131 = new BitSet( new long[]
   { 0x0000000000020012L } );
   
   public static final BitSet FOLLOW_separated_time_in_parseTime144 = new BitSet( new long[]
   { 0x0000000000020012L } );
   
   public static final BitSet FOLLOW_unseparated_time_in_parseTime157 = new BitSet( new long[]
   { 0x0000000000020012L } );
   
   public static final BitSet FOLLOW_h_m_s_separated_time_in_parseTime170 = new BitSet( new long[]
   { 0x0000000000020012L } );
   
   public static final BitSet FOLLOW_am_pm_in_parseTime185 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_literal_time231 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MIDNIGHT_in_literal_time243 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MIDDAY_in_literal_time255 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_separated_time295 = new BitSet( new long[]
   { 0x0000000000000480L } );
   
   public static final BitSet FOLLOW_set_in_separated_time297 = new BitSet( new long[]
   { 0x0000000000001000L } );
   
   public static final BitSet FOLLOW_INT_in_separated_time305 = new BitSet( new long[]
   { 0x0000000000000482L } );
   
   public static final BitSet FOLLOW_set_in_separated_time308 = new BitSet( new long[]
   { 0x0000000000001000L } );
   
   public static final BitSet FOLLOW_INT_in_separated_time316 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_h_m_s_separated_time370 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_h_m_s_separated_time375 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_h_m_s_separated_time378 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_unseparated_time417 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_AM_in_am_pm460 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_PM_in_am_pm472 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_hour_floatspec_in_time_naturalspec539 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_naturalspec559 = new BitSet( new long[]
   { 0x0000000000048800L } );
   
   public static final BitSet FOLLOW_HOURS_in_time_naturalspec563 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MINUTES_in_time_naturalspec599 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SECONDS_in_time_naturalspec635 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_hour_floatspec719 = new BitSet( new long[]
   { 0x0000000000000500L } );
   
   public static final BitSet FOLLOW_set_in_hour_floatspec721 = new BitSet( new long[]
   { 0x0000000000001000L } );
   
   public static final BitSet FOLLOW_INT_in_hour_floatspec729 = new BitSet( new long[]
   { 0x0000000000000800L } );
   
   public static final BitSet FOLLOW_HOURS_in_hour_floatspec731 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseTimeEstimate796 = new BitSet( new long[]
   { 0x0000000000000200L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseTimeEstimate798 = new BitSet( new long[]
   { 0x0000000000001122L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeEstimate831 = new BitSet( new long[]
   { 0x0000000000001122L } );
   
}
