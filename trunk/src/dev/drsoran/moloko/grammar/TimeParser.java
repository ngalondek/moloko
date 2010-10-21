// $ANTLR 3.2 Sep 23, 2009 12:02:23 F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g 2010-10-21 19:29:46

package dev.drsoran.moloko.grammar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;


public class TimeParser extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEVER", "TODAY", "TOMORROW",
    "YESTERDAY", "AT", "ON", "IN", "OF", "NEXT", "AND", "END", "THE", "STs",
    "NOW", "TONIGHT", "MIDNIGHT", "MIDDAY", "NOON", "YEARS", "MONTHS", "WEEKS",
    "DAYS", "HOURS", "MINUTES", "SECONDS", "MONTH", "WEEKDAY", "DATE_SEP",
    "DOT", "COLON", "MINUS", "MINUS_A", "COMMA", "INT", "A", "AM", "PM",
    "NUM_STR", "STRING", "WS" };
   
   public static final int STs = 16;
   
   public static final int MIDDAY = 20;
   
   public static final int THE = 15;
   
   public static final int SECONDS = 28;
   
   public static final int NOW = 17;
   
   public static final int MIDNIGHT = 19;
   
   public static final int AND = 13;
   
   public static final int EOF = -1;
   
   public static final int MONTH = 29;
   
   public static final int AT = 8;
   
   public static final int WEEKDAY = 30;
   
   public static final int IN = 10;
   
   public static final int TONIGHT = 18;
   
   public static final int COMMA = 36;
   
   public static final int NOON = 21;
   
   public static final int NEXT = 12;
   
   public static final int DOT = 32;
   
   public static final int AM = 39;
   
   public static final int TOMORROW = 6;
   
   public static final int TODAY = 5;
   
   public static final int A = 38;
   
   public static final int MINUS_A = 35;
   
   public static final int ON = 9;
   
   public static final int INT = 37;
   
   public static final int MINUS = 34;
   
   public static final int OF = 11;
   
   public static final int YEARS = 22;
   
   public static final int NUM_STR = 41;
   
   public static final int MINUTES = 27;
   
   public static final int COLON = 33;
   
   public static final int DAYS = 25;
   
   public static final int WEEKS = 24;
   
   public static final int WS = 43;
   
   public static final int MONTHS = 23;
   
   public static final int PM = 40;
   
   public static final int NEVER = 4;
   
   public static final int DATE_SEP = 31;
   
   public static final int END = 14;
   
   public static final int YESTERDAY = 7;
   
   public static final int HOURS = 26;
   
   public static final int STRING = 42;
   
   

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
   
   private final static Locale LOCALE = Locale.ENGLISH;
   
   

   public final static Calendar getLocalizedCalendar()
   {
      final Calendar cal = Calendar.getInstance( LOCALE );
      return cal;
   }
   


   private final static void setCalendarTime( Calendar cal, String pit ) throws RecognitionException
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:73:1:
   // parseTime[Calendar cal, boolean adjustDay] returns [boolean eof] : ( ( AT | COMMA )? time_point_in_time[$cal] |
   // EOF );
   public final boolean parseTime( Calendar cal, boolean adjustDay ) throws RecognitionException
   {
      boolean eof = false;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:89:4: ( ( AT |
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:89:6: (
               // AT | COMMA )? time_point_in_time[$cal]
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:89:6: (
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
               
               if ( adjustDay && getLocalizedCalendar().after( cal ) )
                  cal.roll( Calendar.DAY_OF_WEEK, true );
               
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
   // time_point_in_time[Calendar cal] : ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | ( (v= INT | h= time_component ( COLON
   // | DOT ) m= time_component ) ( am_pm[$cal] )? ) );
   public final void time_point_in_time( Calendar cal ) throws RecognitionException
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
               match( input, NEVER, FOLLOW_NEVER_in_time_point_in_time122 );
               
               cal.clear();
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:109:6:
               // MIDNIGHT
            {
               match( input, MIDNIGHT, FOLLOW_MIDNIGHT_in_time_point_in_time134 );
               
               cal.set( Calendar.HOUR_OF_DAY, 23 );
               cal.set( Calendar.MINUTE, 59 );
               cal.set( Calendar.SECOND, 59 );
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:115:6: (
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:122:4: (
               // (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )? )
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:122:4: (
               // (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )? )
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:122:6:
               // (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )?
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:122:6:
                  // (v= INT | h= time_component ( COLON | DOT ) m= time_component )
                  int alt3 = 2;
                  int LA3_0 = input.LA( 1 );
                  
                  if ( ( LA3_0 == INT ) )
                  {
                     int LA3_1 = input.LA( 2 );
                     
                     if ( ( ( LA3_1 >= DOT && LA3_1 <= COLON ) ) )
                     {
                        alt3 = 2;
                     }
                     else if ( ( LA3_1 == EOF || ( LA3_1 >= AM && LA3_1 <= PM ) ) )
                     {
                        alt3 = 1;
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
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:122:8:
                        // v= INT
                     {
                        v = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_time_point_in_time173 );
                        
                        setCalendarTime( cal, ( v != null ? v.getText() : null ) );
                        
                     }
                        break;
                     case 2:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:126:8:
                        // h= time_component ( COLON | DOT ) m= time_component
                     {
                        pushFollow( FOLLOW_time_component_in_time_point_in_time193 );
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
                        
                        pushFollow( FOLLOW_time_component_in_time_point_in_time203 );
                        m = time_component();
                        
                        state._fsp--;
                        
                        cal.set( Calendar.HOUR_OF_DAY, h );
                        cal.set( Calendar.MINUTE, m );
                        cal.set( Calendar.SECOND, 0 );
                        
                     }
                        break;
                     
                  }
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:133:6:
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
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:133:6:
                        // am_pm[$cal]
                     {
                        pushFollow( FOLLOW_am_pm_in_time_point_in_time232 );
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:140:1: am_pm[Calendar
   // cal] : ( AM | PM );
   public final void am_pm( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:141:4: ( AM |
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:141:6: AM
            {
               match( input, AM, FOLLOW_AM_in_am_pm263 );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:142:6: PM
            {
               match( input, PM, FOLLOW_PM_in_am_pm270 );
               
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:164:1:
   // parseTimeSpec[Calendar cal, boolean adjustDay] returns [boolean eof] : ( ( AT | COMMA )? (
   // time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? ) ) |
   // EOF );
   public final boolean parseTimeSpec( Calendar cal, boolean adjustDay ) throws RecognitionException
   {
      boolean eof = false;
      
      cal.set( Calendar.HOUR_OF_DAY, 0 );
      cal.set( Calendar.MINUTE, 0 );
      cal.set( Calendar.SECOND, 0 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:172:2: ( ( AT |
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:172:4: (
               // AT | COMMA )? ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] (
               // time_naturalspec[$cal] )? )? ) )
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:172:4: (
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
               
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:172:18: (
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
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:172:22:
                     // time_separatorspec[$cal]
                  {
                     pushFollow( FOLLOW_time_separatorspec_in_parseTimeSpec332 );
                     time_separatorspec( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 2:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:173:13:
                     // ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
                  {
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:173:13:
                     // ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:173:15:
                     // time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
                     {
                        pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec350 );
                        time_naturalspec( cal );
                        
                        state._fsp--;
                        
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:174:25:
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
                              // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:174:27:
                              // time_naturalspec[$cal] ( time_naturalspec[$cal] )?
                           {
                              pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec380 );
                              time_naturalspec( cal );
                              
                              state._fsp--;
                              
                              // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:175:25:
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
                                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:175:25:
                                    // time_naturalspec[$cal]
                                 {
                                    pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec407 );
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
               
               if ( adjustDay && getLocalizedCalendar().after( cal ) )
                  cal.roll( Calendar.DAY_OF_WEEK, true );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:181:6:
               // EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseTimeSpec441 );
               
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:191:1:
   // time_separatorspec[Calendar cal] : (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? )
   // ;
   public final void time_separatorspec( Calendar cal ) throws RecognitionException
   {
      int h = 0;
      
      int m = 0;
      
      int s = 0;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:192:4: ( (h=
         // time_component ( COLON m= time_component ( COLON s= time_component )? )? ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:192:6: (h=
         // time_component ( COLON m= time_component ( COLON s= time_component )? )? )
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:192:6: (h=
            // time_component ( COLON m= time_component ( COLON s= time_component )? )? )
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:192:7: h=
            // time_component ( COLON m= time_component ( COLON s= time_component )? )?
            {
               pushFollow( FOLLOW_time_component_in_time_separatorspec478 );
               h = time_component();
               
               state._fsp--;
               
               cal.set( Calendar.HOUR_OF_DAY, h );
               
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:196:5: (
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
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:196:6:
                     // COLON m= time_component ( COLON s= time_component )?
                  {
                     match( input, COLON, FOLLOW_COLON_in_time_separatorspec494 );
                     pushFollow( FOLLOW_time_component_in_time_separatorspec498 );
                     m = time_component();
                     
                     state._fsp--;
                     
                     cal.set( Calendar.MINUTE, m );
                     
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:200:8:
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
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:200:9:
                           // COLON s= time_component
                        {
                           match( input,
                                  COLON,
                                  FOLLOW_COLON_in_time_separatorspec515 );
                           pushFollow( FOLLOW_time_component_in_time_separatorspec519 );
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:211:1:
   // time_naturalspec[Calendar cal] returns [int seconds] : (fs= hour_floatspec | v= INT ( HOURS | MINUTES | SECONDS )
   // );
   public final int time_naturalspec( Calendar cal ) throws RecognitionException
   {
      int seconds = 0;
      
      Token v = null;
      int fs = 0;
      
      int calType = -1;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:221:4: (fs=
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:221:8:
               // fs= hour_floatspec
            {
               pushFollow( FOLLOW_hour_floatspec_in_time_naturalspec591 );
               fs = hour_floatspec();
               
               state._fsp--;
               
               seconds += fs;
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:225:8: v=
               // INT ( HOURS | MINUTES | SECONDS )
            {
               v = (Token) match( input, INT, FOLLOW_INT_in_time_naturalspec609 );
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:225:14: (
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
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:225:16:
                     // HOURS
                  {
                     match( input, HOURS, FOLLOW_HOURS_in_time_naturalspec613 );
                     
                     calType = Calendar.HOUR_OF_DAY;
                     
                  }
                     break;
                  case 2:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:229:10:
                     // MINUTES
                  {
                     match( input,
                            MINUTES,
                            FOLLOW_MINUTES_in_time_naturalspec641 );
                     
                     calType = Calendar.MINUTE;
                     
                  }
                     break;
                  case 3:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:233:10:
                     // SECONDS
                  {
                     match( input,
                            SECONDS,
                            FOLLOW_SECONDS_in_time_naturalspec661 );
                     
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:266:1: hour_floatspec
   // returns [int seconds] : h= INT DOT deciHour= INT HOURS ;
   public final int hour_floatspec() throws RecognitionException
   {
      int seconds = 0;
      
      Token h = null;
      Token deciHour = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:267:4: (h= INT
         // DOT deciHour= INT HOURS )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:267:6: h= INT
         // DOT deciHour= INT HOURS
         {
            h = (Token) match( input, INT, FOLLOW_INT_in_hour_floatspec729 );
            match( input, DOT, FOLLOW_DOT_in_hour_floatspec731 );
            deciHour = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_hour_floatspec735 );
            match( input, HOURS, FOLLOW_HOURS_in_hour_floatspec737 );
            
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:288:1:
   // parseTimeEstimate returns [long span] : ( (d= INT DAYS | ts= time_naturalspec[null] )+ | EOF );
   public final long parseTimeEstimate() throws RecognitionException
   {
      long span = 0;
      
      Token d = null;
      int ts = 0;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:293:2: ( (d=
         // INT DAYS | ts= time_naturalspec[null] )+ | EOF )
         int alt17 = 2;
         int LA17_0 = input.LA( 1 );
         
         if ( ( LA17_0 == INT ) )
         {
            alt17 = 1;
         }
         else if ( ( LA17_0 == EOF ) )
         {
            alt17 = 2;
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:293:4:
               // (d= INT DAYS | ts= time_naturalspec[null] )+
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:293:4:
               // (d= INT DAYS | ts= time_naturalspec[null] )+
               int cnt16 = 0;
               loop16: do
               {
                  int alt16 = 3;
                  int LA16_0 = input.LA( 1 );
                  
                  if ( ( LA16_0 == INT ) )
                  {
                     int LA16_2 = input.LA( 2 );
                     
                     if ( ( LA16_2 == DAYS ) )
                     {
                        alt16 = 1;
                     }
                     else if ( ( ( LA16_2 >= HOURS && LA16_2 <= SECONDS ) || LA16_2 == DOT ) )
                     {
                        alt16 = 2;
                     }
                     
                  }
                  
                  switch ( alt16 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:293:8:
                        // d= INT DAYS
                     {
                        d = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_parseTimeEstimate799 );
                        match( input, DAYS, FOLLOW_DAYS_in_parseTimeEstimate801 );
                        
                        span += Integer.parseInt( ( d != null ? d.getText()
                                                             : null ) ) * 3600 * 24;
                        
                     }
                        break;
                     case 2:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:297:8:
                        // ts= time_naturalspec[null]
                     {
                        pushFollow( FOLLOW_time_naturalspec_in_parseTimeEstimate821 );
                        ts = time_naturalspec( null );
                        
                        state._fsp--;
                        
                        span += ts;
                        
                     }
                        break;
                     
                     default :
                        if ( cnt16 >= 1 )
                           break loop16;
                        EarlyExitException eee = new EarlyExitException( 16,
                                                                         input );
                        throw eee;
                  }
                  cnt16++;
               }
               while ( true );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:302:6:
               // EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseTimeEstimate847 );
               
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:313:1: time_component
   // returns [int value] : c= INT ;
   public final int time_component() throws RecognitionException
   {
      int value = 0;
      
      Token c = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:314:2: (c= INT
         // )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:314:4: c= INT
         {
            c = (Token) match( input, INT, FOLLOW_INT_in_time_component897 );
            
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
   
   public static final BitSet FOLLOW_NEVER_in_time_point_in_time122 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MIDNIGHT_in_time_point_in_time134 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_time_point_in_time146 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_point_in_time173 = new BitSet( new long[]
   { 0x0000018000000002L } );
   
   public static final BitSet FOLLOW_time_component_in_time_point_in_time193 = new BitSet( new long[]
   { 0x0000000300000000L } );
   
   public static final BitSet FOLLOW_set_in_time_point_in_time195 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_component_in_time_point_in_time203 = new BitSet( new long[]
   { 0x0000018000000002L } );
   
   public static final BitSet FOLLOW_am_pm_in_time_point_in_time232 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_AM_in_am_pm263 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_PM_in_am_pm270 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parseTimeSpec319 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_separatorspec_in_parseTimeSpec332 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec350 = new BitSet( new long[]
   { 0x0000002000380012L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec380 = new BitSet( new long[]
   { 0x0000002000380012L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec407 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_EOF_in_parseTimeSpec441 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec478 = new BitSet( new long[]
   { 0x0000000200000002L } );
   
   public static final BitSet FOLLOW_COLON_in_time_separatorspec494 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec498 = new BitSet( new long[]
   { 0x0000000200000002L } );
   
   public static final BitSet FOLLOW_COLON_in_time_separatorspec515 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec519 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_hour_floatspec_in_time_naturalspec591 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_naturalspec609 = new BitSet( new long[]
   { 0x000000001C000000L } );
   
   public static final BitSet FOLLOW_HOURS_in_time_naturalspec613 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MINUTES_in_time_naturalspec641 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SECONDS_in_time_naturalspec661 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_hour_floatspec729 = new BitSet( new long[]
   { 0x0000000100000000L } );
   
   public static final BitSet FOLLOW_DOT_in_hour_floatspec731 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_hour_floatspec735 = new BitSet( new long[]
   { 0x0000000004000000L } );
   
   public static final BitSet FOLLOW_HOURS_in_hour_floatspec737 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseTimeEstimate799 = new BitSet( new long[]
   { 0x0000000002000000L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseTimeEstimate801 = new BitSet( new long[]
   { 0x0000002000380012L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeEstimate821 = new BitSet( new long[]
   { 0x0000002000380012L } );
   
   public static final BitSet FOLLOW_EOF_in_parseTimeEstimate847 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_component897 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
