// $ANTLR 3.2 Sep 23, 2009 12:02:23
// F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g
// 2010-09-26 18:16:53

package dev.drsoran.moloko.grammar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.antlr.runtime.BitSet;
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
   {
    "<invalid>",
    "<EOR>",
    "<DOWN>",
    "<UP>",
    "NEVER",
    "TODAY",
    "TOMORROW",
    "YESTERDAY",
    "AT",
    "ON",
    "IN",
    "OF",
    "NEXT",
    "AND",
    "END",
    "THE",
    "STs",
    "NOW",
    "TONIGHT",
    "MIDNIGHT",
    "MIDDAY",
    "NOON",
    "YEARS",
    "MONTHS",
    "WEEKS",
    "DAYS",
    "HOURS",
    "MINUTES",
    "SECONDS",
    "MONTH",
    "WEEKDAY",
    "DATE_SEP",
    "DOT",
    "COLON",
    "MINUS",
    "MINUS_A",
    "COMMA",
    "INT",
    "AM",
    "PM",
    "NUM_STR",
    "WS" };
   
   public static final int STs = 16;
   
   public static final int MIDDAY = 20;
   
   public static final int TODAY = 5;
   
   public static final int MINUS_A = 35;
   
   public static final int THE = 15;
   
   public static final int ON = 9;
   
   public static final int SECONDS = 28;
   
   public static final int NOW = 17;
   
   public static final int INT = 37;
   
   public static final int MIDNIGHT = 19;
   
   public static final int MINUS = 34;
   
   public static final int AND = 13;
   
   public static final int EOF = -1;
   
   public static final int YEARS = 22;
   
   public static final int OF = 11;
   
   public static final int NUM_STR = 40;
   
   public static final int MONTH = 29;
   
   public static final int AT = 8;
   
   public static final int MINUTES = 27;
   
   public static final int COLON = 33;
   
   public static final int DAYS = 25;
   
   public static final int WEEKS = 24;
   
   public static final int WEEKDAY = 30;
   
   public static final int WS = 41;
   
   public static final int IN = 10;
   
   public static final int TONIGHT = 18;
   
   public static final int COMMA = 36;
   
   public static final int MONTHS = 23;
   
   public static final int NOON = 21;
   
   public static final int PM = 39;
   
   public static final int NEXT = 12;
   
   public static final int NEVER = 4;
   
   public static final int DATE_SEP = 31;
   
   public static final int END = 14;
   
   public static final int DOT = 32;
   
   public static final int YESTERDAY = 7;
   
   public static final int HOURS = 26;
   
   public static final int AM = 38;
   
   public static final int TOMORROW = 6;
   
   

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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:75:1:
   // parseTime[Calendar cal, boolean adjustDay] : (h= INT (
   // time_floatspec[$cal] | time_separatorspec[$cal] |
   // time_naturalspec[$cal] ) | ( AT )? time_point_in_timespec[$cal] ) ;
   public final void parseTime( Calendar cal, boolean adjustDay ) throws RecognitionException
   {
      Token h = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:83:4:
         // ( (h= INT
         // ( time_floatspec[$cal] | time_separatorspec[$cal] |
         // time_naturalspec[$cal] ) | ( AT )?
         // time_point_in_timespec[$cal] ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:83:6:
         // (h= INT (
         // time_floatspec[$cal] | time_separatorspec[$cal] |
         // time_naturalspec[$cal] ) | ( AT )?
         // time_point_in_timespec[$cal] )
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:83:6:
            // (h=
            // INT ( time_floatspec[$cal] | time_separatorspec[$cal] |
            // time_naturalspec[$cal] ) | ( AT )?
            // time_point_in_timespec[$cal] )
            int alt3 = 2;
            int LA3_0 = input.LA( 1 );
            
            if ( ( LA3_0 == INT ) )
            {
               switch ( input.LA( 2 ) )
               {
                  case COLON:
                  {
                     int LA3_3 = input.LA( 3 );
                     
                     if ( ( LA3_3 == INT ) )
                     {
                        alt3 = 1;
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
                  case EOF:
                  case AM:
                  case PM:
                  {
                     alt3 = 2;
                  }
                     break;
                  case DOT:
                  case INT:
                  {
                     alt3 = 1;
                  }
                     break;
                  default :
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           3,
                                                                           1,
                                                                           input );
                     
                     throw nvae;
               }
               
            }
            else if ( ( LA3_0 == NEVER || LA3_0 == AT || ( LA3_0 >= MIDNIGHT && LA3_0 <= NOON ) ) )
            {
               alt3 = 2;
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
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:83:9:
                  // h= INT ( time_floatspec[$cal] | time_separatorspec[$cal] |
                  // time_naturalspec[$cal] )
               {
                  h = (Token) match( input, INT, FOLLOW_INT_in_parseTime65 );
                  
                  cal.set( Calendar.HOUR_OF_DAY,
                           Integer.parseInt( ( h != null ? h.getText() : null ) ) );
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:87:9:
                  // ( time_floatspec[$cal] | time_separatorspec[$cal] |
                  // time_naturalspec[$cal] )
                  int alt1 = 3;
                  switch ( input.LA( 1 ) )
                  {
                     case DOT:
                     {
                        alt1 = 1;
                     }
                        break;
                     case COLON:
                     {
                        alt1 = 2;
                     }
                        break;
                     case INT:
                     {
                        alt1 = 3;
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
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:87:13:
                        // time_floatspec[$cal]
                     {
                        pushFollow( FOLLOW_time_floatspec_in_parseTime91 );
                        time_floatspec( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 2:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:88:13:
                        // time_separatorspec[$cal]
                     {
                        pushFollow( FOLLOW_time_separatorspec_in_parseTime110 );
                        time_separatorspec( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 3:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:89:13:
                        // time_naturalspec[$cal]
                     {
                        pushFollow( FOLLOW_time_naturalspec_in_parseTime125 );
                        time_naturalspec( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     
                  }
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:90:9:
                  // ( AT )? time_point_in_timespec[$cal]
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:90:9:
                  // ( AT )?
                  int alt2 = 2;
                  int LA2_0 = input.LA( 1 );
                  
                  if ( ( LA2_0 == AT ) )
                  {
                     alt2 = 1;
                  }
                  switch ( alt2 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:90:9:
                        // AT
                     {
                        match( input, AT, FOLLOW_AT_in_parseTime139 );
                        
                     }
                        break;
                     
                  }
                  
                  pushFollow( FOLLOW_time_point_in_timespec_in_parseTime142 );
                  time_point_in_timespec( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               
            }
            
            if ( adjustDay && getLocalizedCalendar().after( cal ) )
               cal.roll( Calendar.DAY_OF_WEEK, true );
            
         }
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "parseTime"
   
   // $ANTLR start "time_floatspec"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:101:1:
   // time_floatspec[Calendar cal ] : DOT deciHour= INT HOURS ;
   public final void time_floatspec( Calendar cal ) throws RecognitionException
   {
      Token deciHour = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:102:4:
         // ( DOT
         // deciHour= INT HOURS )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:102:6:
         // DOT
         // deciHour= INT HOURS
         {
            match( input, DOT, FOLLOW_DOT_in_time_floatspec177 );
            deciHour = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_time_floatspec181 );
            match( input, HOURS, FOLLOW_HOURS_in_time_floatspec183 );
            
            cal.set( Calendar.MINUTE,
                     (int) ( (float) Integer.parseInt( ( deciHour != null
                                                                         ? deciHour.getText()
                                                                         : null ) ) * 0.1f * 60 ) );
            cal.set( Calendar.SECOND, 0 );
            
         }
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "time_floatspec"
   
   // $ANTLR start "time_separatorspec"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:113:1:
   // time_separatorspec[Calendar cal] : COLON m= INT ( COLON s= INT )? ;
   public final void time_separatorspec( Calendar cal ) throws RecognitionException
   {
      Token m = null;
      Token s = null;
      
      cal.set( Calendar.SECOND, 0 );
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:118:4:
         // ( COLON
         // m= INT ( COLON s= INT )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:118:7:
         // COLON m=
         // INT ( COLON s= INT )?
         {
            match( input, COLON, FOLLOW_COLON_in_time_separatorspec228 );
            m = (Token) match( input, INT, FOLLOW_INT_in_time_separatorspec232 );
            cal.set( Calendar.MINUTE,
                     Integer.parseInt( ( m != null ? m.getText() : null ) ) );
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:119:6:
            // (
            // COLON s= INT )?
            int alt4 = 2;
            int LA4_0 = input.LA( 1 );
            
            if ( ( LA4_0 == COLON ) )
            {
               alt4 = 1;
            }
            switch ( alt4 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:119:7:
                  // COLON s= INT
               {
                  match( input, COLON, FOLLOW_COLON_in_time_separatorspec242 );
                  s = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_time_separatorspec246 );
                  cal.set( Calendar.SECOND,
                           Integer.parseInt( ( s != null ? s.getText() : null ) ) );
                  
               }
                  break;
               
            }
            
         }
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "time_separatorspec"
   
   // $ANTLR start "time_naturalspec"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:126:1:
   // time_naturalspec[Calendar cal] : ( time_naturalspec_hour[$cal] |
   // time_naturalspec_minute[$cal] |
   // time_naturalspec_second[$cal] );
   public final void time_naturalspec( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:127:4:
         // (
         // time_naturalspec_hour[$cal] | time_naturalspec_minute[$cal] |
         // time_naturalspec_second[$cal] )
         int alt5 = 3;
         int LA5_0 = input.LA( 1 );
         
         if ( ( LA5_0 == INT ) )
         {
            switch ( input.LA( 2 ) )
            {
               case HOURS:
               {
                  alt5 = 1;
               }
                  break;
               case MINUTES:
               {
                  alt5 = 2;
               }
                  break;
               case SECONDS:
               {
                  alt5 = 3;
               }
                  break;
               default :
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:127:8:
               // time_naturalspec_hour[$cal]
            {
               pushFollow( FOLLOW_time_naturalspec_hour_in_time_naturalspec280 );
               time_naturalspec_hour( cal );
               
               state._fsp--;
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:128:8:
               // time_naturalspec_minute[$cal]
            {
               pushFollow( FOLLOW_time_naturalspec_minute_in_time_naturalspec292 );
               time_naturalspec_minute( cal );
               
               state._fsp--;
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:129:8:
               // time_naturalspec_second[$cal]
            {
               pushFollow( FOLLOW_time_naturalspec_second_in_time_naturalspec302 );
               time_naturalspec_second( cal );
               
               state._fsp--;
               
            }
               break;
            
         }
      }
      catch ( RecognitionException re )
      {
         reportError( re );
         recover( input, re );
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "time_naturalspec"
   
   // $ANTLR start "time_naturalspec_hour"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:132:1:
   // time_naturalspec_hour[Calendar cal] : h= INT HOURS (
   // time_naturalspec_minute[$cal] | time_naturalspec_second[$cal]
   // )? ;
   public final void time_naturalspec_hour( Calendar cal ) throws RecognitionException
   {
      Token h = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:133:4:
         // (h= INT
         // HOURS ( time_naturalspec_minute[$cal] |
         // time_naturalspec_second[$cal] )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:133:6:
         // h= INT
         // HOURS ( time_naturalspec_minute[$cal] |
         // time_naturalspec_second[$cal] )?
         {
            h = (Token) match( input,
                               INT,
                               FOLLOW_INT_in_time_naturalspec_hour322 );
            match( input, HOURS, FOLLOW_HOURS_in_time_naturalspec_hour324 );
            
            cal.set( Calendar.HOUR_OF_DAY,
                     Integer.parseInt( ( h != null ? h.getText() : null ) ) );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:137:6:
            // (
            // time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )?
            int alt6 = 3;
            int LA6_0 = input.LA( 1 );
            
            if ( ( LA6_0 == INT ) )
            {
               int LA6_1 = input.LA( 2 );
               
               if ( ( LA6_1 == MINUTES ) )
               {
                  alt6 = 1;
               }
               else if ( ( LA6_1 == SECONDS ) )
               {
                  alt6 = 2;
               }
            }
            switch ( alt6 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:137:9:
                  // time_naturalspec_minute[$cal]
               {
                  pushFollow( FOLLOW_time_naturalspec_minute_in_time_naturalspec_hour341 );
                  time_naturalspec_minute( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:138:9:
                  // time_naturalspec_second[$cal]
               {
                  pushFollow( FOLLOW_time_naturalspec_second_in_time_naturalspec_hour352 );
                  time_naturalspec_second( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               
            }
            
         }
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "time_naturalspec_hour"
   
   // $ANTLR start "time_naturalspec_minute"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:145:1:
   // time_naturalspec_minute[Calendar cal] : m= INT MINUTES (
   // time_naturalspec_second[$cal] )? ;
   public final void time_naturalspec_minute( Calendar cal ) throws RecognitionException
   {
      Token m = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:146:4:
         // (m= INT
         // MINUTES ( time_naturalspec_second[$cal] )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:146:6:
         // m= INT
         // MINUTES ( time_naturalspec_second[$cal] )?
         {
            m = (Token) match( input,
                               INT,
                               FOLLOW_INT_in_time_naturalspec_minute385 );
            match( input, MINUTES, FOLLOW_MINUTES_in_time_naturalspec_minute387 );
            
            cal.set( Calendar.MINUTE,
                     Integer.parseInt( ( m != null ? m.getText() : null ) ) );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:150:6:
            // (
            // time_naturalspec_second[$cal] )?
            int alt7 = 2;
            int LA7_0 = input.LA( 1 );
            
            if ( ( LA7_0 == INT ) )
            {
               alt7 = 1;
            }
            switch ( alt7 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:150:6:
                  // time_naturalspec_second[$cal]
               {
                  pushFollow( FOLLOW_time_naturalspec_second_in_time_naturalspec_minute401 );
                  time_naturalspec_second( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               
            }
            
         }
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "time_naturalspec_minute"
   
   // $ANTLR start "time_naturalspec_second"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:157:1:
   // time_naturalspec_second[Calendar cal] : s= INT SECONDS ;
   public final void time_naturalspec_second( Calendar cal ) throws RecognitionException
   {
      Token s = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:158:4:
         // (s= INT
         // SECONDS )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:158:6:
         // s= INT
         // SECONDS
         {
            s = (Token) match( input,
                               INT,
                               FOLLOW_INT_in_time_naturalspec_second433 );
            match( input, SECONDS, FOLLOW_SECONDS_in_time_naturalspec_second435 );
            
            cal.set( Calendar.SECOND,
                     Integer.parseInt( ( s != null ? s.getText() : null ) ) );
            
         }
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "time_naturalspec_second"
   
   // $ANTLR start "time_point_in_timespec"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:168:1:
   // time_point_in_timespec[Calendar cal] : ( NEVER | MIDNIGHT | ( MIDDAY |
   // NOON ) | (v= INT ( time_separatorspec[$cal]
   // )? ( am_pm_spec[$cal] )? ) );
   public final void time_point_in_timespec( Calendar cal ) throws RecognitionException
   {
      Token v = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:169:4:
         // ( NEVER
         // | MIDNIGHT | ( MIDDAY | NOON ) | (v= INT ( time_separatorspec[$cal]
         // )? ( am_pm_spec[$cal] )? ) )
         int alt10 = 4;
         switch ( input.LA( 1 ) )
         {
            case NEVER:
            {
               alt10 = 1;
            }
               break;
            case MIDNIGHT:
            {
               alt10 = 2;
            }
               break;
            case MIDDAY:
            case NOON:
            {
               alt10 = 3;
            }
               break;
            case INT:
            {
               alt10 = 4;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     10,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt10 )
         {
            case 1:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:169:6:
               // NEVER
            {
               match( input, NEVER, FOLLOW_NEVER_in_time_point_in_timespec470 );
               
               cal.clear();
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:173:6:
               // MIDNIGHT
            {
               match( input,
                      MIDNIGHT,
                      FOLLOW_MIDNIGHT_in_time_point_in_timespec482 );
               
               cal.set( Calendar.HOUR_OF_DAY, 23 );
               cal.set( Calendar.MINUTE, 59 );
               cal.set( Calendar.SECOND, 59 );
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:179:6:
               // (
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:186:4:
               // (v= INT ( time_separatorspec[$cal] )? ( am_pm_spec[$cal] )? )
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:186:4:
               // (v= INT ( time_separatorspec[$cal] )? ( am_pm_spec[$cal] )? )
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:186:5:
               // v=
               // INT ( time_separatorspec[$cal] )? ( am_pm_spec[$cal] )?
               {
                  v = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_time_point_in_timespec518 );
                  
                  setCalendarTime( cal, ( v != null ? v.getText() : null ) );
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:190:7:
                  // ( time_separatorspec[$cal] )?
                  int alt8 = 2;
                  int LA8_0 = input.LA( 1 );
                  
                  if ( ( LA8_0 == COLON ) )
                  {
                     alt8 = 1;
                  }
                  switch ( alt8 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:190:8:
                        // time_separatorspec[$cal]
                     {
                        pushFollow( FOLLOW_time_separatorspec_in_time_point_in_timespec535 );
                        time_separatorspec( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     
                  }
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:191:7:
                  // ( am_pm_spec[$cal] )?
                  int alt9 = 2;
                  int LA9_0 = input.LA( 1 );
                  
                  if ( ( ( LA9_0 >= AM && LA9_0 <= PM ) ) )
                  {
                     alt9 = 1;
                  }
                  switch ( alt9 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:191:8:
                        // am_pm_spec[$cal]
                     {
                        pushFollow( FOLLOW_am_pm_spec_in_time_point_in_timespec547 );
                        am_pm_spec( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     
                  }
                  
               }
               
            }
               break;
            
         }
      }
      catch ( RecognitionException re )
      {
         reportError( re );
         recover( input, re );
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "time_point_in_timespec"
   
   // $ANTLR start "am_pm_spec"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:194:1:
   // am_pm_spec[Calendar cal] : ( AM | PM );
   public final void am_pm_spec( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:196:4:
         // ( AM |
         // PM )
         int alt11 = 2;
         int LA11_0 = input.LA( 1 );
         
         if ( ( LA11_0 == AM ) )
         {
            alt11 = 1;
         }
         else if ( ( LA11_0 == PM ) )
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:196:6:
               // AM
            {
               match( input, AM, FOLLOW_AM_in_am_pm_spec573 );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeParser.g:197:6:
               // PM
            {
               match( input, PM, FOLLOW_PM_in_am_pm_spec580 );
               
               cal.add( Calendar.HOUR_OF_DAY, 12 );
               
            }
               break;
            
         }
      }
      catch ( RecognitionException re )
      {
         reportError( re );
         recover( input, re );
      }
      finally
      {
      }
      return;
   }
   
   // $ANTLR end "am_pm_spec"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_INT_in_parseTime65 = new BitSet( new long[]
   { 0x0000002300000000L } );
   
   public static final BitSet FOLLOW_time_floatspec_in_parseTime91 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_separatorspec_in_parseTime110 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTime125 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_AT_in_parseTime139 = new BitSet( new long[]
   { 0x0000002000380010L } );
   
   public static final BitSet FOLLOW_time_point_in_timespec_in_parseTime142 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_DOT_in_time_floatspec177 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_time_floatspec181 = new BitSet( new long[]
   { 0x0000000004000000L } );
   
   public static final BitSet FOLLOW_HOURS_in_time_floatspec183 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_COLON_in_time_separatorspec228 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_time_separatorspec232 = new BitSet( new long[]
   { 0x0000000200000002L } );
   
   public static final BitSet FOLLOW_COLON_in_time_separatorspec242 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_time_separatorspec246 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_hour_in_time_naturalspec280 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_minute_in_time_naturalspec292 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_second_in_time_naturalspec302 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_naturalspec_hour322 = new BitSet( new long[]
   { 0x0000000004000000L } );
   
   public static final BitSet FOLLOW_HOURS_in_time_naturalspec_hour324 = new BitSet( new long[]
   { 0x0000002300000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_minute_in_time_naturalspec_hour341 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_second_in_time_naturalspec_hour352 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_naturalspec_minute385 = new BitSet( new long[]
   { 0x0000000008000000L } );
   
   public static final BitSet FOLLOW_MINUTES_in_time_naturalspec_minute387 = new BitSet( new long[]
   { 0x0000002300000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_second_in_time_naturalspec_minute401 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_naturalspec_second433 = new BitSet( new long[]
   { 0x0000000010000000L } );
   
   public static final BitSet FOLLOW_SECONDS_in_time_naturalspec_second435 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_time_point_in_timespec470 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MIDNIGHT_in_time_point_in_timespec482 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_time_point_in_timespec494 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_point_in_timespec518 = new BitSet( new long[]
   { 0x000000C200000002L } );
   
   public static final BitSet FOLLOW_time_separatorspec_in_time_point_in_timespec535 = new BitSet( new long[]
   { 0x000000C000000002L } );
   
   public static final BitSet FOLLOW_am_pm_spec_in_time_point_in_timespec547 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_AM_in_am_pm_spec573 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_PM_in_am_pm_spec580 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
