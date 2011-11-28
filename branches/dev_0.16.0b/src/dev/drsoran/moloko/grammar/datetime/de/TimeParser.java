// $ANTLR 3.3 Nov 30, 2010 12:45:30 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g 2011-11-28 12:17:31

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

import dev.drsoran.moloko.grammar.LexerException;
import dev.drsoran.moloko.grammar.datetime.AbstractTimeParser;
import dev.drsoran.moloko.grammar.datetime.ITimeParser.ParseTimeReturn;
import dev.drsoran.moloko.util.MolokoCalendar;


public class TimeParser extends AbstractTimeParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT", "COMMA", "NEVER",
    "MIDNIGHT", "MIDDAY", "INT", "COLON", "DOT", "AM", "PM", "HOURS",
    "MINUTES", "SECONDS", "DAYS", "AND", "WS" };
   
   public static final int EOF = -1;
   
   public static final int AT = 4;
   
   public static final int COMMA = 5;
   
   public static final int NEVER = 6;
   
   public static final int MIDNIGHT = 7;
   
   public static final int MIDDAY = 8;
   
   public static final int INT = 9;
   
   public static final int COLON = 10;
   
   public static final int DOT = 11;
   
   public static final int AM = 12;
   
   public static final int PM = 13;
   
   public static final int HOURS = 14;
   
   public static final int MINUTES = 15;
   
   public static final int SECONDS = 16;
   
   public static final int DAYS = 17;
   
   public static final int AND = 18;
   
   public static final int WS = 19;
   
   
   
   // delegates
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
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g";
   }
   
   
   
   public TimeParser()
   {
      super( null );
   }
   
   
   
   // $ANTLR start "parseTime"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:48:1:
   // parseTime[MolokoCalendar cal, boolean adjustDay] returns [ParseTimeReturn result] : ( AT | COMMA )?
   // time_point_in_time[$cal] ;
   public final ParseTimeReturn parseTime( MolokoCalendar cal, boolean adjustDay ) throws RecognitionException
   {
      ParseTimeReturn result = null;
      
      startParsingTime( cal );
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:70:4: (
         // ( AT | COMMA )? time_point_in_time[$cal] )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:70:6: (
         // AT | COMMA )? time_point_in_time[$cal]
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:70:6:
            // ( AT | COMMA )?
            int alt1 = 2;
            int LA1_0 = input.LA( 1 );
            
            if ( ( ( LA1_0 >= AT && LA1_0 <= COMMA ) ) )
            {
               alt1 = 1;
            }
            switch ( alt1 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
               {
                  if ( ( input.LA( 1 ) >= AT && input.LA( 1 ) <= COMMA ) )
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
            
            pushFollow( FOLLOW_time_point_in_time_in_parseTime117 );
            time_point_in_time( cal );
            
            state._fsp--;
            
            if ( adjustDay && getCalendar().after( cal.toCalendar() ) )
               cal.roll( Calendar.DAY_OF_WEEK, true );
            
            cal.setHasTime( true );
            
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
      }
      return result;
   }
   
   
   
   // $ANTLR end "parseTime"
   
   // $ANTLR start "time_point_in_time"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:89:1:
   // time_point_in_time[MolokoCalendar cal] : ( NEVER | MIDNIGHT | MIDDAY | ( (v= INT | h= time_component ( COLON | DOT
   // ) m= time_component ) ( am_pm[$cal] )? ) );
   public final void time_point_in_time( MolokoCalendar cal ) throws RecognitionException
   {
      Token v = null;
      int h = 0;
      
      int m = 0;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:90:4: (
         // NEVER | MIDNIGHT | MIDDAY | ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal]
         // )? ) )
         int alt4 = 4;
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
            case INT:
            {
               alt4 = 4;
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:90:6:
            // NEVER
            {
               match( input, NEVER, FOLLOW_NEVER_in_time_point_in_time168 );
               
               cal.setHasDate( false );
               cal.setHasTime( false );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:95:6:
            // MIDNIGHT
            {
               match( input, MIDNIGHT, FOLLOW_MIDNIGHT_in_time_point_in_time180 );
               
               cal.set( Calendar.HOUR_OF_DAY, 23 );
               cal.set( Calendar.MINUTE, 59 );
               cal.set( Calendar.SECOND, 59 );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:101:6:
            // MIDDAY
            {
               match( input, MIDDAY, FOLLOW_MIDDAY_in_time_point_in_time192 );
               
               cal.set( Calendar.HOUR_OF_DAY, 12 );
               cal.set( Calendar.MINUTE, 00 );
               cal.set( Calendar.SECOND, 00 );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:108:4:
            // ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )? )
            {
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:108:4:
               // ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )? )
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:108:6:
               // (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )?
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:108:6:
                  // (v= INT | h= time_component ( COLON | DOT ) m= time_component )
                  int alt2 = 2;
                  int LA2_0 = input.LA( 1 );
                  
                  if ( ( LA2_0 == INT ) )
                  {
                     int LA2_1 = input.LA( 2 );
                     
                     if ( ( LA2_1 == EOF || ( LA2_1 >= AM && LA2_1 <= PM ) ) )
                     {
                        alt2 = 1;
                     }
                     else if ( ( ( LA2_1 >= COLON && LA2_1 <= DOT ) ) )
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:108:8:
                     // v= INT
                     {
                        v = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_time_point_in_time213 );
                        
                        setCalendarTime( cal, ( v != null ? v.getText() : null ) );
                        
                     }
                        break;
                     case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:112:8:
                     // h= time_component ( COLON | DOT ) m= time_component
                     {
                        pushFollow( FOLLOW_time_component_in_time_point_in_time233 );
                        h = time_component();
                        
                        state._fsp--;
                        
                        if ( ( input.LA( 1 ) >= COLON && input.LA( 1 ) <= DOT ) )
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
                        
                        pushFollow( FOLLOW_time_component_in_time_point_in_time243 );
                        m = time_component();
                        
                        state._fsp--;
                        
                        cal.set( Calendar.HOUR_OF_DAY, h );
                        cal.set( Calendar.MINUTE, m );
                        cal.set( Calendar.SECOND, 0 );
                        
                     }
                        break;
                  
                  }
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:119:6:
                  // ( am_pm[$cal] )?
                  int alt3 = 2;
                  int LA3_0 = input.LA( 1 );
                  
                  if ( ( ( LA3_0 >= AM && LA3_0 <= PM ) ) )
                  {
                     alt3 = 1;
                  }
                  switch ( alt3 )
                  {
                     case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:119:6:
                     // am_pm[$cal]
                     {
                        pushFollow( FOLLOW_am_pm_in_time_point_in_time266 );
                        am_pm( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                  
                  }
                  
               }
               
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
      }
      return;
   }
   
   
   
   // $ANTLR end "time_point_in_time"
   
   // $ANTLR start "am_pm"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:126:1:
   // am_pm[MolokoCalendar cal] : ( AM | PM );
   public final void am_pm( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:127:4:
         // ( AM | PM )
         int alt5 = 2;
         int LA5_0 = input.LA( 1 );
         
         if ( ( LA5_0 == AM ) )
         {
            alt5 = 1;
         }
         else if ( ( LA5_0 == PM ) )
         {
            alt5 = 2;
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:127:6:
            // AM
            {
               match( input, AM, FOLLOW_AM_in_am_pm300 );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:128:6:
            // PM
            {
               match( input, PM, FOLLOW_PM_in_am_pm307 );
               
               cal.add( Calendar.HOUR_OF_DAY, 12 );
               
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
      }
      return;
   }
   
   
   
   // $ANTLR end "am_pm"
   
   // $ANTLR start "parseTimeSpec"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:149:1:
   // parseTimeSpec[MolokoCalendar cal, boolean adjustDay] returns [ParseTimeReturn result] : ( AT | COMMA )? (
   // time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? ) ) (
   // am_pm[$cal] )? ( COMMA )? ;
   public final ParseTimeReturn parseTimeSpec( MolokoCalendar cal,
                                               boolean adjustDay ) throws RecognitionException
   {
      ParseTimeReturn result = null;
      
      startParsingTime( cal );
      
      cal.set( Calendar.HOUR_OF_DAY, 0 );
      cal.set( Calendar.MINUTE, 0 );
      cal.set( Calendar.SECOND, 0 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:163:4:
         // ( ( AT | COMMA )? ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] (
         // time_naturalspec[$cal] )? )? ) ) ( am_pm[$cal] )? ( COMMA )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:163:6:
         // ( AT | COMMA )? ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] (
         // time_naturalspec[$cal] )? )? ) ) ( am_pm[$cal] )? ( COMMA )?
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:163:6:
            // ( AT | COMMA )?
            int alt6 = 2;
            int LA6_0 = input.LA( 1 );
            
            if ( ( ( LA6_0 >= AT && LA6_0 <= COMMA ) ) )
            {
               alt6 = 1;
            }
            switch ( alt6 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
               {
                  if ( ( input.LA( 1 ) >= AT && input.LA( 1 ) <= COMMA ) )
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
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:163:20:
            // ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal]
            // )? )? ) )
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == INT ) )
            {
               int LA9_1 = input.LA( 2 );
               
               if ( ( LA9_1 == DOT || ( LA9_1 >= HOURS && LA9_1 <= SECONDS ) ) )
               {
                  alt9 = 2;
               }
               else if ( ( LA9_1 == EOF || LA9_1 == COMMA || LA9_1 == COLON || ( LA9_1 >= AM && LA9_1 <= PM ) ) )
               {
                  alt9 = 1;
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        1,
                                                                        input );
                  
                  throw nvae;
               }
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     9,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt9 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:163:24:
               // time_separatorspec[$cal]
               {
                  pushFollow( FOLLOW_time_separatorspec_in_parseTimeSpec387 );
                  time_separatorspec( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:164:24:
               // ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:164:24:
                  // ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:164:26:
                  // time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
                  {
                     pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec415 );
                     time_naturalspec( cal );
                     
                     state._fsp--;
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:165:24:
                     // ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
                     int alt8 = 2;
                     int LA8_0 = input.LA( 1 );
                     
                     if ( ( LA8_0 == INT ) )
                     {
                        alt8 = 1;
                     }
                     switch ( alt8 )
                     {
                        case 1:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:165:26:
                        // time_naturalspec[$cal] ( time_naturalspec[$cal] )?
                        {
                           pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec443 );
                           time_naturalspec( cal );
                           
                           state._fsp--;
                           
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:166:26:
                           // ( time_naturalspec[$cal] )?
                           int alt7 = 2;
                           int LA7_0 = input.LA( 1 );
                           
                           if ( ( LA7_0 == INT ) )
                           {
                              alt7 = 1;
                           }
                           switch ( alt7 )
                           {
                              case 1:
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:166:26:
                              // time_naturalspec[$cal]
                              {
                                 pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec471 );
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
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:168:7:
            // ( am_pm[$cal] )?
            int alt10 = 2;
            int LA10_0 = input.LA( 1 );
            
            if ( ( ( LA10_0 >= AM && LA10_0 <= PM ) ) )
            {
               alt10 = 1;
            }
            switch ( alt10 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:168:7:
               // am_pm[$cal]
               {
                  pushFollow( FOLLOW_am_pm_in_parseTimeSpec505 );
                  am_pm( cal );
                  
                  state._fsp--;
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:168:20:
            // ( COMMA )?
            int alt11 = 2;
            int LA11_0 = input.LA( 1 );
            
            if ( ( LA11_0 == COMMA ) )
            {
               alt11 = 1;
            }
            switch ( alt11 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:168:20:
               // COMMA
               {
                  match( input, COMMA, FOLLOW_COMMA_in_parseTimeSpec509 );
                  
               }
                  break;
            
            }
            
            if ( adjustDay && getCalendar().after( cal.toCalendar() ) )
               cal.roll( Calendar.DAY_OF_WEEK, true );
            
            cal.setHasTime( true );
            
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
      }
      return result;
   }
   
   
   
   // $ANTLR end "parseTimeSpec"
   
   // $ANTLR start "time_separatorspec"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:187:1:
   // time_separatorspec[MolokoCalendar cal] : (h= time_component ( COLON m= time_component ( COLON s= time_component )?
   // )? ) ;
   public final void time_separatorspec( MolokoCalendar cal ) throws RecognitionException
   {
      int h = 0;
      
      int m = 0;
      
      int s = 0;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:188:4:
         // ( (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:188:6:
         // (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:188:6:
            // (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:188:7:
            // h= time_component ( COLON m= time_component ( COLON s= time_component )? )?
            {
               pushFollow( FOLLOW_time_component_in_time_separatorspec557 );
               h = time_component();
               
               state._fsp--;
               
               cal.set( Calendar.HOUR_OF_DAY, h );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:192:7:
               // ( COLON m= time_component ( COLON s= time_component )? )?
               int alt13 = 2;
               int LA13_0 = input.LA( 1 );
               
               if ( ( LA13_0 == COLON ) )
               {
                  alt13 = 1;
               }
               switch ( alt13 )
               {
                  case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:192:8:
                  // COLON m= time_component ( COLON s= time_component )?
                  {
                     match( input, COLON, FOLLOW_COLON_in_time_separatorspec574 );
                     pushFollow( FOLLOW_time_component_in_time_separatorspec578 );
                     m = time_component();
                     
                     state._fsp--;
                     
                     cal.set( Calendar.MINUTE, m );
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:196:8:
                     // ( COLON s= time_component )?
                     int alt12 = 2;
                     int LA12_0 = input.LA( 1 );
                     
                     if ( ( LA12_0 == COLON ) )
                     {
                        alt12 = 1;
                     }
                     switch ( alt12 )
                     {
                        case 1:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:196:9:
                        // COLON s= time_component
                        {
                           match( input,
                                  COLON,
                                  FOLLOW_COLON_in_time_separatorspec597 );
                           pushFollow( FOLLOW_time_component_in_time_separatorspec601 );
                           s = time_component();
                           
                           state._fsp--;
                           
                           cal.set( Calendar.SECOND, s );
                           
                        }
                           break;
                     
                     }
                     
                  }
                     break;
               
               }
               
            }
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return;
   }
   
   
   
   // $ANTLR end "time_separatorspec"
   
   // $ANTLR start "time_naturalspec"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:207:1:
   // time_naturalspec[MolokoCalendar cal] returns [int seconds] : (fs= hour_floatspec | v= INT ( HOURS | MINUTES |
   // SECONDS ) );
   public final int time_naturalspec( MolokoCalendar cal ) throws RecognitionException
   {
      int seconds = 0;
      
      Token v = null;
      int fs = 0;
      
      int calType = -1;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:217:4:
         // (fs= hour_floatspec | v= INT ( HOURS | MINUTES | SECONDS ) )
         int alt15 = 2;
         int LA15_0 = input.LA( 1 );
         
         if ( ( LA15_0 == INT ) )
         {
            int LA15_1 = input.LA( 2 );
            
            if ( ( LA15_1 == DOT ) )
            {
               alt15 = 1;
            }
            else if ( ( ( LA15_1 >= HOURS && LA15_1 <= SECONDS ) ) )
            {
               alt15 = 2;
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:217:8:
            // fs= hour_floatspec
            {
               pushFollow( FOLLOW_hour_floatspec_in_time_naturalspec681 );
               fs = hour_floatspec();
               
               state._fsp--;
               
               seconds += fs;
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:221:8:
            // v= INT ( HOURS | MINUTES | SECONDS )
            {
               v = (Token) match( input, INT, FOLLOW_INT_in_time_naturalspec701 );
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:221:14:
               // ( HOURS | MINUTES | SECONDS )
               int alt14 = 3;
               switch ( input.LA( 1 ) )
               {
                  case HOURS:
                  {
                     alt14 = 1;
                  }
                     break;
                  case MINUTES:
                  {
                     alt14 = 2;
                  }
                     break;
                  case SECONDS:
                  {
                     alt14 = 3;
                  }
                     break;
                  default :
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           14,
                                                                           0,
                                                                           input );
                     
                     throw nvae;
               }
               
               switch ( alt14 )
               {
                  case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:221:16:
                  // HOURS
                  {
                     match( input, HOURS, FOLLOW_HOURS_in_time_naturalspec705 );
                     
                     calType = Calendar.HOUR_OF_DAY;
                     
                  }
                     break;
                  case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:225:18:
                  // MINUTES
                  {
                     match( input,
                            MINUTES,
                            FOLLOW_MINUTES_in_time_naturalspec741 );
                     
                     calType = Calendar.MINUTE;
                     
                  }
                     break;
                  case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:229:18:
                  // SECONDS
                  {
                     match( input,
                            SECONDS,
                            FOLLOW_SECONDS_in_time_naturalspec777 );
                     
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
      }
      return seconds;
   }
   
   
   
   // $ANTLR end "time_naturalspec"
   
   // $ANTLR start "hour_floatspec"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:262:1:
   // hour_floatspec returns [int seconds] : h= INT DOT deciHour= INT HOURS ;
   public final int hour_floatspec() throws RecognitionException
   {
      int seconds = 0;
      
      Token h = null;
      Token deciHour = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:263:4:
         // (h= INT DOT deciHour= INT HOURS )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:263:6:
         // h= INT DOT deciHour= INT HOURS
         {
            h = (Token) match( input, INT, FOLLOW_INT_in_hour_floatspec861 );
            match( input, DOT, FOLLOW_DOT_in_hour_floatspec863 );
            deciHour = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_hour_floatspec867 );
            match( input, HOURS, FOLLOW_HOURS_in_hour_floatspec869 );
            
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
      }
      return seconds;
   }
   
   
   
   // $ANTLR end "hour_floatspec"
   
   // $ANTLR start "parseTimeEstimate"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:284:1:
   // parseTimeEstimate returns [long span] : (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA |
   // AND )* ;
   public final long parseTimeEstimate() throws RecognitionException
   {
      long span = 0;
      
      Token d = null;
      int ts = 0;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:289:4:
         // ( (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )* )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:289:6:
         // (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )*
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:289:6:
            // (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+
            int cnt17 = 0;
            loop17: do
            {
               int alt17 = 3;
               int LA17_0 = input.LA( 1 );
               
               if ( ( LA17_0 == COMMA || LA17_0 == AND ) )
               {
                  int LA17_1 = input.LA( 2 );
                  
                  if ( ( LA17_1 == INT ) )
                  {
                     alt17 = 2;
                  }
                  
               }
               else if ( ( LA17_0 == INT ) )
               {
                  int LA17_3 = input.LA( 2 );
                  
                  if ( ( LA17_3 == DAYS ) )
                  {
                     alt17 = 1;
                  }
                  else if ( ( LA17_3 == DOT || ( LA17_3 >= HOURS && LA17_3 <= SECONDS ) ) )
                  {
                     alt17 = 2;
                  }
                  
               }
               
               switch ( alt17 )
               {
                  case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:289:10:
                  // d= INT DAYS
                  {
                     d = (Token) match( input,
                                        INT,
                                        FOLLOW_INT_in_parseTimeEstimate934 );
                     match( input, DAYS, FOLLOW_DAYS_in_parseTimeEstimate936 );
                     
                     span += Integer.parseInt( ( d != null ? d.getText() : null ) ) * 3600 * 24;
                     
                  }
                     break;
                  case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:293:10:
                  // ( COMMA | AND )? ts= time_naturalspec[null]
                  {
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:293:10:
                     // ( COMMA | AND )?
                     int alt16 = 2;
                     int LA16_0 = input.LA( 1 );
                     
                     if ( ( LA16_0 == COMMA || LA16_0 == AND ) )
                     {
                        alt16 = 1;
                     }
                     switch ( alt16 )
                     {
                        case 1:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                        {
                           if ( input.LA( 1 ) == COMMA || input.LA( 1 ) == AND )
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
                     
                     pushFollow( FOLLOW_time_naturalspec_in_parseTimeEstimate969 );
                     ts = time_naturalspec( null );
                     
                     state._fsp--;
                     
                     span += ts;
                     
                  }
                     break;
                  
                  default :
                     if ( cnt17 >= 1 )
                        break loop17;
                     EarlyExitException eee = new EarlyExitException( 17, input );
                     throw eee;
               }
               cnt17++;
            }
            while ( true );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:297:9:
            // ( COMMA | AND )*
            loop18: do
            {
               int alt18 = 2;
               int LA18_0 = input.LA( 1 );
               
               if ( ( LA18_0 == COMMA || LA18_0 == AND ) )
               {
                  alt18 = 1;
               }
               
               switch ( alt18 )
               {
                  case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                  {
                     if ( input.LA( 1 ) == COMMA || input.LA( 1 ) == AND )
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
                     break loop18;
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
      }
      return span;
   }
   
   
   
   // $ANTLR end "parseTimeEstimate"
   
   // $ANTLR start "time_component"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:312:1:
   // time_component returns [int value] : c= INT ;
   public final int time_component() throws RecognitionException
   {
      int value = 0;
      
      Token c = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:313:4:
         // (c= INT )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:313:6:
         // c= INT
         {
            c = (Token) match( input, INT, FOLLOW_INT_in_time_component1052 );
            
            String comp = ( c != null ? c.getText() : null );
            
            if ( comp.length() > 2 )
               comp = comp.substring( 0, 2 );
            
            value = Integer.parseInt( comp );
            
         }
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return value;
   }
   
   // $ANTLR end "time_component"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_set_in_parseTime108 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_point_in_time_in_parseTime117 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_time_point_in_time168 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MIDNIGHT_in_time_point_in_time180 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MIDDAY_in_time_point_in_time192 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_point_in_time213 = new BitSet( new long[]
   { 0x0000000000003002L } );
   
   public static final BitSet FOLLOW_time_component_in_time_point_in_time233 = new BitSet( new long[]
   { 0x0000000000000C00L } );
   
   public static final BitSet FOLLOW_set_in_time_point_in_time235 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_component_in_time_point_in_time243 = new BitSet( new long[]
   { 0x0000000000003002L } );
   
   public static final BitSet FOLLOW_am_pm_in_time_point_in_time266 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_AM_in_am_pm300 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_PM_in_am_pm307 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parseTimeSpec374 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_separatorspec_in_parseTimeSpec387 = new BitSet( new long[]
   { 0x0000000000003022L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec415 = new BitSet( new long[]
   { 0x00000000000033E2L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec443 = new BitSet( new long[]
   { 0x00000000000033E2L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec471 = new BitSet( new long[]
   { 0x0000000000003022L } );
   
   public static final BitSet FOLLOW_am_pm_in_parseTimeSpec505 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseTimeSpec509 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec557 = new BitSet( new long[]
   { 0x0000000000000402L } );
   
   public static final BitSet FOLLOW_COLON_in_time_separatorspec574 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec578 = new BitSet( new long[]
   { 0x0000000000000402L } );
   
   public static final BitSet FOLLOW_COLON_in_time_separatorspec597 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec601 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_hour_floatspec_in_time_naturalspec681 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_naturalspec701 = new BitSet( new long[]
   { 0x000000000001C000L } );
   
   public static final BitSet FOLLOW_HOURS_in_time_naturalspec705 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MINUTES_in_time_naturalspec741 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SECONDS_in_time_naturalspec777 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_hour_floatspec861 = new BitSet( new long[]
   { 0x0000000000000800L } );
   
   public static final BitSet FOLLOW_DOT_in_hour_floatspec863 = new BitSet( new long[]
   { 0x0000000000000200L } );
   
   public static final BitSet FOLLOW_INT_in_hour_floatspec867 = new BitSet( new long[]
   { 0x0000000000004000L } );
   
   public static final BitSet FOLLOW_HOURS_in_hour_floatspec869 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseTimeEstimate934 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseTimeEstimate936 = new BitSet( new long[]
   { 0x00000000000403E2L } );
   
   public static final BitSet FOLLOW_set_in_parseTimeEstimate958 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeEstimate969 = new BitSet( new long[]
   { 0x00000000000403E2L } );
   
   public static final BitSet FOLLOW_set_in_parseTimeEstimate989 = new BitSet( new long[]
   { 0x0000000000040022L } );
   
   public static final BitSet FOLLOW_INT_in_time_component1052 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
