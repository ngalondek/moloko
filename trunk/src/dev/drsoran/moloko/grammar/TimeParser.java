// $ANTLR 3.3 Nov 30, 2010 12:45:30 F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g 2011-05-15 10:33:32

package dev.drsoran.moloko.grammar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.util.MolokoCalendar;


public class TimeParser extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEVER", "TODAY", "TOMORROW",
    "YESTERDAY", "AT", "ON", "IN", "OF", "NEXT", "AND", "END", "THE", "STs",
    "NOW", "TONIGHT", "MIDNIGHT", "MIDDAY", "NOON", "YEARS", "MONTHS", "WEEKS",
    "DAYS", "HOURS", "MINUTES", "SECONDS", "MONTH", "WEEKDAY", "DATE_SEP",
    "DOT", "COLON", "MINUS", "MINUS_A", "COMMA", "INT", "A", "AM", "PM",
    "NUM_STR", "STRING", "WS" };
   
   public static final int EOF = -1;
   
   public static final int NEVER = 4;
   
   public static final int TODAY = 5;
   
   public static final int TOMORROW = 6;
   
   public static final int YESTERDAY = 7;
   
   public static final int AT = 8;
   
   public static final int ON = 9;
   
   public static final int IN = 10;
   
   public static final int OF = 11;
   
   public static final int NEXT = 12;
   
   public static final int AND = 13;
   
   public static final int END = 14;
   
   public static final int THE = 15;
   
   public static final int STs = 16;
   
   public static final int NOW = 17;
   
   public static final int TONIGHT = 18;
   
   public static final int MIDNIGHT = 19;
   
   public static final int MIDDAY = 20;
   
   public static final int NOON = 21;
   
   public static final int YEARS = 22;
   
   public static final int MONTHS = 23;
   
   public static final int WEEKS = 24;
   
   public static final int DAYS = 25;
   
   public static final int HOURS = 26;
   
   public static final int MINUTES = 27;
   
   public static final int SECONDS = 28;
   
   public static final int MONTH = 29;
   
   public static final int WEEKDAY = 30;
   
   public static final int DATE_SEP = 31;
   
   public static final int DOT = 32;
   
   public static final int COLON = 33;
   
   public static final int MINUS = 34;
   
   public static final int MINUS_A = 35;
   
   public static final int COMMA = 36;
   
   public static final int INT = 37;
   
   public static final int A = 38;
   
   public static final int AM = 39;
   
   public static final int PM = 40;
   
   public static final int NUM_STR = 41;
   
   public static final int STRING = 42;
   
   public static final int WS = 43;
   
   

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
      return "F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g";
   }
   


   public TimeParser()
   {
      super( null );
   }
   


   public final static MolokoCalendar getCalendar()
   {
      return MolokoCalendar.getInstance();
   }
   


   private final static void setCalendarTime( MolokoCalendar cal, String pit ) throws RecognitionException
   {
      final int len = pit.length();
      
      SimpleDateFormat sdf = null;
      
      try
      {
         if ( len < 3 )
         {
            sdf = new SimpleDateFormat( "HH" );
         }
         else if ( len > 3 )
         {
            sdf = new SimpleDateFormat( "HHmm" );
         }
         else
         {
            sdf = new SimpleDateFormat( "Hmm" );
         }
         
         sdf.parse( pit );
         
         final Calendar sdfCal = sdf.getCalendar();
         cal.set( Calendar.HOUR_OF_DAY, sdfCal.get( Calendar.HOUR_OF_DAY ) );
         cal.set( Calendar.MINUTE, sdfCal.get( Calendar.MINUTE ) );
         cal.set( Calendar.SECOND, 0 );
      }
      catch ( ParseException e )
      {
         throw new RecognitionException();
      }
   }
   


   // $ANTLR start "parseTime"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:71:1:
   // parseTime[MolokoCalendar cal, boolean adjustDay] returns [boolean eof] : ( ( AT | COMMA )?
   // time_point_in_time[$cal] | EOF );
   public final boolean parseTime( MolokoCalendar cal, boolean adjustDay ) throws RecognitionException
   {
      boolean eof = false;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:87:4: ( ( AT |
         // COMMA )? time_point_in_time[$cal] | EOF )
         int alt2 = 2;
         int LA2_0 = input.LA( 1 );
         
         if ( ( LA2_0 == NEVER || LA2_0 == AT
            || ( LA2_0 >= MIDNIGHT && LA2_0 <= NOON ) || ( LA2_0 >= COMMA && LA2_0 <= INT ) ) )
         {
            alt2 = 1;
         }
         else if ( ( LA2_0 == EOF ) )
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:87:6: (
               // AT | COMMA )? time_point_in_time[$cal]
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:87:6: (
               // AT | COMMA )?
               int alt1 = 2;
               int LA1_0 = input.LA( 1 );
               
               if ( ( LA1_0 == AT || LA1_0 == COMMA ) )
               {
                  alt1 = 1;
               }
               switch ( alt1 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:
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
               
               pushFollow( FOLLOW_time_point_in_time_in_parseTime73 );
               time_point_in_time( cal );
               
               state._fsp--;
               
               if ( adjustDay && getCalendar().after( cal ) )
                  cal.roll( Calendar.DAY_OF_WEEK, true );
               
               cal.setHasTime( true );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:94:6: EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseTime86 );
               
               eof = true;
               
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
      return eof;
   }
   


   // $ANTLR end "parseTime"
   
   // $ANTLR start "time_point_in_time"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:104:1:
   // time_point_in_time[MolokoCalendar cal] : ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | ( (v= INT | h= time_component (
   // COLON | DOT ) m= time_component ) ( am_pm[$cal] )? ) );
   public final void time_point_in_time( MolokoCalendar cal ) throws RecognitionException
   {
      Token v = null;
      int h = 0;
      
      int m = 0;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:105:4: ( NEVER
         // | MIDNIGHT | ( MIDDAY | NOON ) | ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) (
         // am_pm[$cal] )? ) )
         int alt5 = 4;
         switch ( input.LA( 1 ) )
         {
            case NEVER:
            {
               alt5 = 1;
            }
               break;
            case MIDNIGHT:
            {
               alt5 = 2;
            }
               break;
            case MIDDAY:
            case NOON:
            {
               alt5 = 3;
            }
               break;
            case INT:
            {
               alt5 = 4;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     5,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt5 )
         {
            case 1:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:105:6:
               // NEVER
            {
               match( input, NEVER, FOLLOW_NEVER_in_time_point_in_time119 );
               
               cal.setHasDate( false );
               cal.setHasTime( false );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:110:6:
               // MIDNIGHT
            {
               match( input, MIDNIGHT, FOLLOW_MIDNIGHT_in_time_point_in_time131 );
               
               cal.set( Calendar.HOUR_OF_DAY, 23 );
               cal.set( Calendar.MINUTE, 59 );
               cal.set( Calendar.SECOND, 59 );
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:116:6: (
               // MIDDAY | NOON )
            {
               if ( ( input.LA( 1 ) >= MIDDAY && input.LA( 1 ) <= NOON ) )
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
               
               cal.set( Calendar.HOUR_OF_DAY, 12 );
               cal.set( Calendar.MINUTE, 00 );
               cal.set( Calendar.SECOND, 00 );
               
            }
               break;
            case 4:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:123:4: (
               // (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )? )
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:123:4: (
               // (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )? )
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:123:6:
               // (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )?
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:123:6:
                  // (v= INT | h= time_component ( COLON | DOT ) m= time_component )
                  int alt3 = 2;
                  int LA3_0 = input.LA( 1 );
                  
                  if ( ( LA3_0 == INT ) )
                  {
                     int LA3_1 = input.LA( 2 );
                     
                     if ( ( LA3_1 == EOF || ( LA3_1 >= AM && LA3_1 <= PM ) ) )
                     {
                        alt3 = 1;
                     }
                     else if ( ( ( LA3_1 >= DOT && LA3_1 <= COLON ) ) )
                     {
                        alt3 = 2;
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
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           3,
                                                                           0,
                                                                           input );
                     
                     throw nvae;
                  }
                  switch ( alt3 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:123:8:
                        // v= INT
                     {
                        v = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_time_point_in_time170 );
                        
                        setCalendarTime( cal, ( v != null ? v.getText() : null ) );
                        
                     }
                        break;
                     case 2:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:127:8:
                        // h= time_component ( COLON | DOT ) m= time_component
                     {
                        pushFollow( FOLLOW_time_component_in_time_point_in_time190 );
                        h = time_component();
                        
                        state._fsp--;
                        
                        if ( ( input.LA( 1 ) >= DOT && input.LA( 1 ) <= COLON ) )
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
                        
                        pushFollow( FOLLOW_time_component_in_time_point_in_time200 );
                        m = time_component();
                        
                        state._fsp--;
                        
                        cal.set( Calendar.HOUR_OF_DAY, h );
                        cal.set( Calendar.MINUTE, m );
                        cal.set( Calendar.SECOND, 0 );
                        
                     }
                        break;
                     
                  }
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:134:6:
                  // ( am_pm[$cal] )?
                  int alt4 = 2;
                  int LA4_0 = input.LA( 1 );
                  
                  if ( ( ( LA4_0 >= AM && LA4_0 <= PM ) ) )
                  {
                     alt4 = 1;
                  }
                  switch ( alt4 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:134:6:
                        // am_pm[$cal]
                     {
                        pushFollow( FOLLOW_am_pm_in_time_point_in_time223 );
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:141:1:
   // am_pm[MolokoCalendar cal] : ( AM | PM );
   public final void am_pm( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:142:4: ( AM |
         // PM )
         int alt6 = 2;
         int LA6_0 = input.LA( 1 );
         
         if ( ( LA6_0 == AM ) )
         {
            alt6 = 1;
         }
         else if ( ( LA6_0 == PM ) )
         {
            alt6 = 2;
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:142:6: AM
            {
               match( input, AM, FOLLOW_AM_in_am_pm254 );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:143:6: PM
            {
               match( input, PM, FOLLOW_PM_in_am_pm261 );
               
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:165:1:
   // parseTimeSpec[MolokoCalendar cal, boolean adjustDay] returns [boolean eof] : ( ( AT | COMMA )? (
   // time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? ) ) |
   // EOF );
   public final boolean parseTimeSpec( MolokoCalendar cal, boolean adjustDay ) throws RecognitionException
   {
      boolean eof = false;
      
      cal.set( Calendar.HOUR_OF_DAY, 0 );
      cal.set( Calendar.MINUTE, 0 );
      cal.set( Calendar.SECOND, 0 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:173:4: ( ( AT |
         // COMMA )? ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] (
         // time_naturalspec[$cal] )? )? ) ) | EOF )
         int alt11 = 2;
         int LA11_0 = input.LA( 1 );
         
         if ( ( LA11_0 == AT || ( LA11_0 >= COMMA && LA11_0 <= INT ) ) )
         {
            alt11 = 1;
         }
         else if ( ( LA11_0 == EOF ) )
         {
            alt11 = 2;
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:173:6: (
               // AT | COMMA )? ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] (
               // time_naturalspec[$cal] )? )? ) )
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:173:6: (
               // AT | COMMA )?
               int alt7 = 2;
               int LA7_0 = input.LA( 1 );
               
               if ( ( LA7_0 == AT || LA7_0 == COMMA ) )
               {
                  alt7 = 1;
               }
               switch ( alt7 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:
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
               
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:173:20: (
               // time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal]
               // )? )? ) )
               int alt10 = 2;
               int LA10_0 = input.LA( 1 );
               
               if ( ( LA10_0 == INT ) )
               {
                  int LA10_1 = input.LA( 2 );
                  
                  if ( ( ( LA10_1 >= HOURS && LA10_1 <= SECONDS ) || LA10_1 == DOT ) )
                  {
                     alt10 = 2;
                  }
                  else if ( ( LA10_1 == EOF || LA10_1 == COLON ) )
                  {
                     alt10 = 1;
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
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:173:24:
                     // time_separatorspec[$cal]
                  {
                     pushFollow( FOLLOW_time_separatorspec_in_parseTimeSpec329 );
                     time_separatorspec( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 2:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:174:24:
                     // ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
                  {
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:174:24:
                     // ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:174:26:
                     // time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
                     {
                        pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec358 );
                        time_naturalspec( cal );
                        
                        state._fsp--;
                        
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:175:24:
                        // ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
                        int alt9 = 2;
                        int LA9_0 = input.LA( 1 );
                        
                        if ( ( LA9_0 == INT ) )
                        {
                           alt9 = 1;
                        }
                        switch ( alt9 )
                        {
                           case 1:
                              // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:175:26:
                              // time_naturalspec[$cal] ( time_naturalspec[$cal] )?
                           {
                              pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec387 );
                              time_naturalspec( cal );
                              
                              state._fsp--;
                              
                              // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:176:26:
                              // ( time_naturalspec[$cal] )?
                              int alt8 = 2;
                              int LA8_0 = input.LA( 1 );
                              
                              if ( ( LA8_0 == INT ) )
                              {
                                 alt8 = 1;
                              }
                              switch ( alt8 )
                              {
                                 case 1:
                                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:176:26:
                                    // time_naturalspec[$cal]
                                 {
                                    pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec415 );
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
               
               if ( adjustDay && getCalendar().after( cal ) )
                  cal.roll( Calendar.DAY_OF_WEEK, true );
               
               cal.setHasTime( true );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:184:6:
               // EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseTimeSpec453 );
               
               eof = true;
               
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
      return eof;
   }
   


   // $ANTLR end "parseTimeSpec"
   
   // $ANTLR start "time_separatorspec"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:194:1:
   // time_separatorspec[MolokoCalendar cal] : (h= time_component ( COLON m= time_component ( COLON s= time_component )?
   // )? ) ;
   public final void time_separatorspec( MolokoCalendar cal ) throws RecognitionException
   {
      int h = 0;
      
      int m = 0;
      
      int s = 0;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:195:4: ( (h=
         // time_component ( COLON m= time_component ( COLON s= time_component )? )? ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:195:6: (h=
         // time_component ( COLON m= time_component ( COLON s= time_component )? )? )
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:195:6: (h=
            // time_component ( COLON m= time_component ( COLON s= time_component )? )? )
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:195:7: h=
            // time_component ( COLON m= time_component ( COLON s= time_component )? )?
            {
               pushFollow( FOLLOW_time_component_in_time_separatorspec489 );
               h = time_component();
               
               state._fsp--;
               
               cal.set( Calendar.HOUR_OF_DAY, h );
               
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:199:7: (
               // COLON m= time_component ( COLON s= time_component )? )?
               int alt13 = 2;
               int LA13_0 = input.LA( 1 );
               
               if ( ( LA13_0 == COLON ) )
               {
                  alt13 = 1;
               }
               switch ( alt13 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:199:8:
                     // COLON m= time_component ( COLON s= time_component )?
                  {
                     match( input, COLON, FOLLOW_COLON_in_time_separatorspec506 );
                     pushFollow( FOLLOW_time_component_in_time_separatorspec510 );
                     m = time_component();
                     
                     state._fsp--;
                     
                     cal.set( Calendar.MINUTE, m );
                     
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:203:8:
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
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:203:9:
                           // COLON s= time_component
                        {
                           match( input,
                                  COLON,
                                  FOLLOW_COLON_in_time_separatorspec529 );
                           pushFollow( FOLLOW_time_component_in_time_separatorspec533 );
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:214:1:
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
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:224:4: (fs=
         // hour_floatspec | v= INT ( HOURS | MINUTES | SECONDS ) )
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:224:8:
               // fs= hour_floatspec
            {
               pushFollow( FOLLOW_hour_floatspec_in_time_naturalspec613 );
               fs = hour_floatspec();
               
               state._fsp--;
               
               seconds += fs;
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:228:8: v=
               // INT ( HOURS | MINUTES | SECONDS )
            {
               v = (Token) match( input, INT, FOLLOW_INT_in_time_naturalspec633 );
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:228:14: (
               // HOURS | MINUTES | SECONDS )
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
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:228:16:
                     // HOURS
                  {
                     match( input, HOURS, FOLLOW_HOURS_in_time_naturalspec637 );
                     
                     calType = Calendar.HOUR_OF_DAY;
                     
                  }
                     break;
                  case 2:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:232:18:
                     // MINUTES
                  {
                     match( input,
                            MINUTES,
                            FOLLOW_MINUTES_in_time_naturalspec673 );
                     
                     calType = Calendar.MINUTE;
                     
                  }
                     break;
                  case 3:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:236:18:
                     // SECONDS
                  {
                     match( input,
                            SECONDS,
                            FOLLOW_SECONDS_in_time_naturalspec709 );
                     
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:269:1: hour_floatspec
   // returns [int seconds] : h= INT DOT deciHour= INT HOURS ;
   public final int hour_floatspec() throws RecognitionException
   {
      int seconds = 0;
      
      Token h = null;
      Token deciHour = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:270:4: (h= INT
         // DOT deciHour= INT HOURS )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:270:6: h= INT
         // DOT deciHour= INT HOURS
         {
            h = (Token) match( input, INT, FOLLOW_INT_in_hour_floatspec793 );
            match( input, DOT, FOLLOW_DOT_in_hour_floatspec795 );
            deciHour = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_hour_floatspec799 );
            match( input, HOURS, FOLLOW_HOURS_in_hour_floatspec801 );
            
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:291:1:
   // parseTimeEstimate returns [long span] : ( (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA |
   // AND )* | EOF );
   public final long parseTimeEstimate() throws RecognitionException
   {
      long span = 0;
      
      Token d = null;
      int ts = 0;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:296:4: ( (d=
         // INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )* | EOF )
         int alt19 = 2;
         int LA19_0 = input.LA( 1 );
         
         if ( ( LA19_0 == AND || ( LA19_0 >= COMMA && LA19_0 <= INT ) ) )
         {
            alt19 = 1;
         }
         else if ( ( LA19_0 == EOF ) )
         {
            alt19 = 2;
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:296:6:
               // (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )*
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:296:6:
               // (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+
               int cnt17 = 0;
               loop17: do
               {
                  int alt17 = 3;
                  int LA17_0 = input.LA( 1 );
                  
                  if ( ( LA17_0 == AND || LA17_0 == COMMA ) )
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
                     else if ( ( ( LA17_3 >= HOURS && LA17_3 <= SECONDS ) || LA17_3 == DOT ) )
                     {
                        alt17 = 2;
                     }
                     
                  }
                  
                  switch ( alt17 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:296:10:
                        // d= INT DAYS
                     {
                        d = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_parseTimeEstimate866 );
                        match( input, DAYS, FOLLOW_DAYS_in_parseTimeEstimate868 );
                        
                        span += Integer.parseInt( ( d != null ? d.getText()
                                                             : null ) ) * 3600 * 24;
                        
                     }
                        break;
                     case 2:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:300:10:
                        // ( COMMA | AND )? ts= time_naturalspec[null]
                     {
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:300:10:
                        // ( COMMA | AND )?
                        int alt16 = 2;
                        int LA16_0 = input.LA( 1 );
                        
                        if ( ( LA16_0 == AND || LA16_0 == COMMA ) )
                        {
                           alt16 = 1;
                        }
                        switch ( alt16 )
                        {
                           case 1:
                              // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:
                           {
                              if ( input.LA( 1 ) == AND
                                 || input.LA( 1 ) == COMMA )
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
                        
                        pushFollow( FOLLOW_time_naturalspec_in_parseTimeEstimate901 );
                        ts = time_naturalspec( null );
                        
                        state._fsp--;
                        
                        span += ts;
                        
                     }
                        break;
                     
                     default :
                        if ( cnt17 >= 1 )
                           break loop17;
                        EarlyExitException eee = new EarlyExitException( 17,
                                                                         input );
                        throw eee;
                  }
                  cnt17++;
               }
               while ( true );
               
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:304:9: (
               // COMMA | AND )*
               loop18: do
               {
                  int alt18 = 2;
                  int LA18_0 = input.LA( 1 );
                  
                  if ( ( LA18_0 == AND || LA18_0 == COMMA ) )
                  {
                     alt18 = 1;
                  }
                  
                  switch ( alt18 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:
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
                        break loop18;
                  }
               }
               while ( true );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:305:6:
               // EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseTimeEstimate935 );
               
            }
               break;
            
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
      finally
      {
      }
      return span;
   }
   


   // $ANTLR end "parseTimeEstimate"
   
   // $ANTLR start "time_component"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:316:1: time_component
   // returns [int value] : c= INT ;
   public final int time_component() throws RecognitionException
   {
      int value = 0;
      
      Token c = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:317:4: (c= INT
         // )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:317:6: c= INT
         {
            c = (Token) match( input, INT, FOLLOW_INT_in_time_component980 );
            
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
   
   public static final BitSet FOLLOW_set_in_parseTime64 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_point_in_time_in_parseTime73 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_EOF_in_parseTime86 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_time_point_in_time119 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MIDNIGHT_in_time_point_in_time131 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_time_point_in_time143 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_point_in_time170 = new BitSet( new long[]
   { 0x0000018000000002L } );
   
   public static final BitSet FOLLOW_time_component_in_time_point_in_time190 = new BitSet( new long[]
   { 0x0000000300000000L } );
   
   public static final BitSet FOLLOW_set_in_time_point_in_time192 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_component_in_time_point_in_time200 = new BitSet( new long[]
   { 0x0000018000000002L } );
   
   public static final BitSet FOLLOW_am_pm_in_time_point_in_time223 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_AM_in_am_pm254 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_PM_in_am_pm261 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parseTimeSpec316 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_separatorspec_in_parseTimeSpec329 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec358 = new BitSet( new long[]
   { 0x0000002000380012L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec387 = new BitSet( new long[]
   { 0x0000002000380012L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec415 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_EOF_in_parseTimeSpec453 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec489 = new BitSet( new long[]
   { 0x0000000200000002L } );
   
   public static final BitSet FOLLOW_COLON_in_time_separatorspec506 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec510 = new BitSet( new long[]
   { 0x0000000200000002L } );
   
   public static final BitSet FOLLOW_COLON_in_time_separatorspec529 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec533 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_hour_floatspec_in_time_naturalspec613 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_naturalspec633 = new BitSet( new long[]
   { 0x000000001C000000L } );
   
   public static final BitSet FOLLOW_HOURS_in_time_naturalspec637 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MINUTES_in_time_naturalspec673 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SECONDS_in_time_naturalspec709 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_hour_floatspec793 = new BitSet( new long[]
   { 0x0000000100000000L } );
   
   public static final BitSet FOLLOW_DOT_in_hour_floatspec795 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_hour_floatspec799 = new BitSet( new long[]
   { 0x0000000004000000L } );
   
   public static final BitSet FOLLOW_HOURS_in_hour_floatspec801 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseTimeEstimate866 = new BitSet( new long[]
   { 0x0000000002000000L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseTimeEstimate868 = new BitSet( new long[]
   { 0x0000003000382012L } );
   
   public static final BitSet FOLLOW_set_in_parseTimeEstimate890 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeEstimate901 = new BitSet( new long[]
   { 0x0000003000382012L } );
   
   public static final BitSet FOLLOW_set_in_parseTimeEstimate921 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_EOF_in_parseTimeEstimate935 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_component980 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
