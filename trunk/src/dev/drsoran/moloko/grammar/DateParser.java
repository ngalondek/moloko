// $ANTLR 3.2 Sep 23, 2009 12:02:23 F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g 2010-09-26 18:35:06

package dev.drsoran.moloko.grammar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
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


public class DateParser extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEVER", "TODAY", "TOMORROW",
    "YESTERDAY", "AT", "ON", "IN", "OF", "NEXT", "AND", "END", "THE", "STs",
    "NOW", "TONIGHT", "MIDNIGHT", "MIDDAY", "NOON", "YEARS", "MONTHS", "WEEKS",
    "DAYS", "HOURS", "MINUTES", "SECONDS", "MONTH", "WEEKDAY", "DATE_SEP",
    "DOT", "COLON", "MINUS", "MINUS_A", "COMMA", "INT", "AM", "PM", "NUM_STR",
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
   
   public DateParser( TokenStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public DateParser( TokenStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   @Override
   public String[] getTokenNames()
   {
      return DateParser.tokenNames;
   }
   


   @Override
   public String getGrammarFileName()
   {
      return "F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g";
   }
   
   private final static Locale LOCALE = Locale.ENGLISH;
   
   private final static HashMap< String, Integer > numberLookUp = new HashMap< String, Integer >();
   
   static
   {
      numberLookUp.put( "one", 1 );
      numberLookUp.put( "two", 2 );
      numberLookUp.put( "three", 3 );
      numberLookUp.put( "four", 4 );
      numberLookUp.put( "five", 5 );
      numberLookUp.put( "six", 6 );
      numberLookUp.put( "seven", 7 );
      numberLookUp.put( "eight", 8 );
      numberLookUp.put( "nine", 9 );
      numberLookUp.put( "ten", 10 );
   }
   
   

   public final static Calendar getLocalizedCalendar()
   {
      final Calendar cal = Calendar.getInstance( LOCALE );
      
      cal.clear( Calendar.HOUR );
      cal.clear( Calendar.HOUR_OF_DAY );
      cal.clear( Calendar.MINUTE );
      cal.clear( Calendar.SECOND );
      
      return cal;
   }
   


   private final static int strToNumber( String string )
   {
      int res = -1;
      
      final Integer val = numberLookUp.get( string );
      
      if ( val != null )
         res = val;
      
      return res;
   }
   


   private final static void parseFullDate( Calendar cal,
                                            String day,
                                            String month,
                                            String year,
                                            boolean textMonth ) throws RecognitionException
   {
      try
      {
         SimpleDateFormat sdf = null;
         
         if ( !textMonth )
         {
            sdf = new SimpleDateFormat( "dd.MM.yyyy" );
         }
         else
         {
            sdf = new SimpleDateFormat( "dd.MMM.yyyy", LOCALE /* Locale for MMM */);
         }
         
         sdf.setCalendar( cal );
         sdf.parse( day + "." + month + "." + year );
      }
      catch ( ParseException e )
      {
         throw new RecognitionException();
      }
   }
   


   private final static void parseTextMonth( Calendar cal, String month ) throws RecognitionException
   {
      try
      {
         final SimpleDateFormat sdf = new SimpleDateFormat( "MMM", LOCALE );
         sdf.parse( month );
         
         cal.set( Calendar.MONTH, sdf.getCalendar().get( Calendar.MONTH ) );
      }
      catch ( ParseException e )
      {
         throw new RecognitionException();
      }
   }
   


   private final static void parseYear( Calendar cal, String yearStr ) throws RecognitionException
   {
      final int len = yearStr.length();
      
      int year = 0;
      
      try
      {
         if ( len < 4 )
         {
            final SimpleDateFormat sdf = new SimpleDateFormat( "yy" );
            
            if ( len == 1 )
            {
               yearStr = "0" + yearStr;
            }
            else if ( len == 3 )
            {
               yearStr = yearStr.substring( 1, len );
            }
            
            sdf.parse( yearStr );
            year = sdf.getCalendar().get( Calendar.YEAR );
         }
         else
         {
            year = Integer.parseInt( yearStr );
         }
         
         cal.set( Calendar.YEAR, year );
      }
      catch ( ParseException pe )
      {
         throw new RecognitionException();
      }
      catch ( NumberFormatException nfe )
      {
         throw new RecognitionException();
      }
   }
   


   private final static void rollToEndOf( int field, Calendar cal )
   {
      final int ref = cal.get( field );
      final int max = cal.getActualMaximum( field );
      
      // set the field to the end.
      cal.set( field, max );
      
      // if already at the end
      if ( ref == max )
      {
         // we roll to the next
         cal.roll( field, true );
      }
   }
   


   // $ANTLR start "parseDate"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:183:1:
   // parseDate[Calendar cal] : ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
   // date_natural[$cal] ) ;
   public final void parseDate( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:187:4: ( (
         // date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:187:6: (
         // date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:187:6: (
            // date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
            int alt1 = 5;
            switch ( input.LA( 1 ) )
            {
               case INT:
               {
                  switch ( input.LA( 2 ) )
                  {
                     case DOT:
                     case MINUS:
                     {
                        int LA1_6 = input.LA( 3 );
                        
                        if ( ( LA1_6 == INT ) )
                        {
                           alt1 = 1;
                        }
                        else if ( ( LA1_6 == MONTH ) )
                        {
                           alt1 = 2;
                        }
                        else
                        {
                           NoViableAltException nvae = new NoViableAltException( "",
                                                                                 1,
                                                                                 6,
                                                                                 input );
                           
                           throw nvae;
                        }
                     }
                        break;
                     case YEARS:
                     case MONTHS:
                     case WEEKS:
                     case DAYS:
                     {
                        alt1 = 3;
                     }
                        break;
                     case EOF:
                     case OF:
                     case STs:
                     case MONTH:
                     case MINUS_A:
                     case COMMA:
                     {
                        alt1 = 2;
                     }
                        break;
                     case DATE_SEP:
                     case COLON:
                     {
                        alt1 = 1;
                     }
                        break;
                     default :
                        NoViableAltException nvae = new NoViableAltException( "",
                                                                              1,
                                                                              1,
                                                                              input );
                        
                        throw nvae;
                  }
                  
               }
                  break;
               case ON:
               case NEXT:
               case MONTH:
               case WEEKDAY:
               {
                  alt1 = 2;
               }
                  break;
               case IN:
               case NUM_STR:
               {
                  alt1 = 3;
               }
                  break;
               case END:
               {
                  alt1 = 4;
               }
                  break;
               case TODAY:
               case TOMORROW:
               case YESTERDAY:
               case TONIGHT:
               {
                  alt1 = 5;
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
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:187:10:
                  // date_full[$cal]
               {
                  pushFollow( FOLLOW_date_full_in_parseDate60 );
                  date_full( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:188:10:
                  // date_on[$cal]
               {
                  pushFollow( FOLLOW_date_on_in_parseDate81 );
                  date_on( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 3:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:189:10:
                  // date_in_X_YMWD[$cal]
               {
                  pushFollow( FOLLOW_date_in_X_YMWD_in_parseDate104 );
                  date_in_X_YMWD( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 4:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:190:10:
                  // date_end_of_the_MW[$cal]
               {
                  pushFollow( FOLLOW_date_end_of_the_MW_in_parseDate120 );
                  date_end_of_the_MW( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 5:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:191:10:
                  // date_natural[$cal]
               {
                  pushFollow( FOLLOW_date_natural_in_parseDate132 );
                  date_natural( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               
            }
            
            cal.clear( Calendar.HOUR );
            cal.clear( Calendar.HOUR_OF_DAY );
            cal.clear( Calendar.MINUTE );
            cal.clear( Calendar.SECOND );
            
         }
         
      }
      catch ( NoViableAltException nve )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "parseDate"
   
   // $ANTLR start "date_full"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:207:1:
   // date_full[Calendar cal] : pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) m= INT ( DOT | MINUS | COLON | DATE_SEP )
   // pt3= INT ;
   public final void date_full( Calendar cal ) throws RecognitionException
   {
      Token pt1 = null;
      Token m = null;
      Token pt3 = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:208:4: (pt1=
         // INT ( DOT | MINUS | COLON | DATE_SEP ) m= INT ( DOT | MINUS | COLON | DATE_SEP ) pt3= INT )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:208:6: pt1= INT
         // ( DOT | MINUS | COLON | DATE_SEP ) m= INT ( DOT | MINUS | COLON | DATE_SEP ) pt3= INT
         {
            pt1 = (Token) match( input, INT, FOLLOW_INT_in_date_full186 );
            if ( ( input.LA( 1 ) >= DATE_SEP && input.LA( 1 ) <= MINUS ) )
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
            
            m = (Token) match( input, INT, FOLLOW_INT_in_date_full215 );
            if ( ( input.LA( 1 ) >= DATE_SEP && input.LA( 1 ) <= MINUS ) )
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
            
            pt3 = (Token) match( input, INT, FOLLOW_INT_in_date_full242 );
            
            // year first
            if ( pt1.getText().length() > 2 )
            {
               parseFullDate( cal,
                              m.getText(),
                              pt3.getText(),
                              pt1.getText(),
                              false );
            }
            
            // year last
            else
            {
               parseFullDate( cal,
                              pt1.getText(),
                              m.getText(),
                              pt3.getText(),
                              false );
            }
            
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
   


   // $ANTLR end "date_full"
   
   // $ANTLR start "date_on"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:234:1:
   // date_on[Calendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) ;
   public final void date_on( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:235:4: ( ( ON
         // )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:235:6: ( ON )?
         // ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:235:6: ( ON
            // )?
            int alt2 = 2;
            int LA2_0 = input.LA( 1 );
            
            if ( ( LA2_0 == ON ) )
            {
               alt2 = 1;
            }
            switch ( alt2 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:235:6:
                  // ON
               {
                  match( input, ON, FOLLOW_ON_in_date_on269 );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:235:10: (
            // date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
            int alt3 = 3;
            switch ( input.LA( 1 ) )
            {
               case INT:
               {
                  alt3 = 1;
               }
                  break;
               case MONTH:
               {
                  alt3 = 2;
               }
                  break;
               case NEXT:
               case WEEKDAY:
               {
                  alt3 = 3;
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
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:235:14:
                  // date_on_Xst_of_M[$cal]
               {
                  pushFollow( FOLLOW_date_on_Xst_of_M_in_date_on276 );
                  date_on_Xst_of_M( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:236:14:
                  // date_on_M_Xst[$cal]
               {
                  pushFollow( FOLLOW_date_on_M_Xst_in_date_on292 );
                  date_on_M_Xst( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 3:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:237:14:
                  // date_on_weekday[$cal]
               {
                  pushFollow( FOLLOW_date_on_weekday_in_date_on311 );
                  date_on_weekday( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               
            }
            
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
   


   // $ANTLR end "date_on"
   
   // $ANTLR start "date_on_Xst_of_M"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:240:1:
   // date_on_Xst_of_M[Calendar cal] : d= INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT
   // )? (y= INT )? )? ;
   public final void date_on_Xst_of_M( Calendar cal ) throws RecognitionException
   {
      Token d = null;
      Token m = null;
      Token y = null;
      
      final Calendar now = getLocalizedCalendar();
      boolean hasMonth = false;
      boolean hasYear = false;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:247:4: (d= INT
         // ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:247:6: d= INT (
         // STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
         {
            d = (Token) match( input, INT, FOLLOW_INT_in_date_on_Xst_of_M340 );
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:247:12: (
            // STs )?
            int alt4 = 2;
            int LA4_0 = input.LA( 1 );
            
            if ( ( LA4_0 == STs ) )
            {
               alt4 = 1;
            }
            switch ( alt4 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:247:12:
                  // STs
               {
                  match( input, STs, FOLLOW_STs_in_date_on_Xst_of_M342 );
                  
               }
                  break;
               
            }
            
            cal.set( Calendar.DAY_OF_MONTH,
                     Integer.parseInt( ( d != null ? d.getText() : null ) ) );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:251:6: ( (
            // OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            int alt8 = 2;
            int LA8_0 = input.LA( 1 );
            
            if ( ( LA8_0 == OF || LA8_0 == MONTH || LA8_0 == DOT || ( LA8_0 >= MINUS && LA8_0 <= COMMA ) ) )
            {
               alt8 = 1;
            }
            switch ( alt8 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:251:7:
                  // ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:251:7:
                  // ( OF | MINUS_A | MINUS | COMMA | DOT )?
                  int alt5 = 2;
                  int LA5_0 = input.LA( 1 );
                  
                  if ( ( LA5_0 == OF || LA5_0 == DOT || ( LA5_0 >= MINUS && LA5_0 <= COMMA ) ) )
                  {
                     alt5 = 1;
                  }
                  switch ( alt5 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
                     {
                        if ( input.LA( 1 ) == OF
                           || input.LA( 1 ) == DOT
                           || ( input.LA( 1 ) >= MINUS && input.LA( 1 ) <= COMMA ) )
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
                  
                  m = (Token) match( input,
                                     MONTH,
                                     FOLLOW_MONTH_in_date_on_Xst_of_M390 );
                  
                  parseTextMonth( cal, ( m != null ? m.getText() : null ) );
                  hasMonth = true;
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:257:7:
                  // ( MINUS | DOT )?
                  int alt6 = 2;
                  int LA6_0 = input.LA( 1 );
                  
                  if ( ( LA6_0 == DOT || LA6_0 == MINUS ) )
                  {
                     alt6 = 1;
                  }
                  switch ( alt6 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
                     {
                        if ( input.LA( 1 ) == DOT || input.LA( 1 ) == MINUS )
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
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:258:7:
                  // (y= INT )?
                  int alt7 = 2;
                  int LA7_0 = input.LA( 1 );
                  
                  if ( ( LA7_0 == INT ) )
                  {
                     alt7 = 1;
                  }
                  switch ( alt7 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:258:8:
                        // y= INT
                     {
                        y = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_date_on_Xst_of_M427 );
                        
                        parseYear( cal, ( y != null ? y.getText() : null ) );
                        hasYear = true;
                        
                     }
                        break;
                     
                  }
                  
               }
                  break;
               
            }
            
            // if we have a year we have a full qualified date.
            // so we change nothing.
            if ( !hasYear && cal.before( now ) )
            {
               // if we have a month, we roll to next year.
               if ( hasMonth )
                  cal.roll( Calendar.YEAR, true );
               // if we only have a day, we roll to next month.
               else
                  cal.roll( Calendar.MONTH, true );
            }
            
         }
         
      }
      catch ( NumberFormatException e )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_on_Xst_of_M"
   
   // $ANTLR start "date_on_M_Xst"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:282:1:
   // date_on_M_Xst[Calendar cal] : m= MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+
   // )? (y= INT )? ;
   public final void date_on_M_Xst( Calendar cal ) throws RecognitionException
   {
      Token m = null;
      Token d = null;
      Token y = null;
      
      boolean hasYear = false;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:287:4: (m=
         // MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:287:6: m= MONTH
         // ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )?
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_date_on_M_Xst485 );
            
            parseTextMonth( cal, ( m != null ? m.getText() : null ) );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:291:5: (
            // MINUS | COMMA | DOT )?
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == DOT || LA9_0 == MINUS || LA9_0 == COMMA ) )
            {
               alt9 = 1;
            }
            switch ( alt9 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
               {
                  if ( input.LA( 1 ) == DOT || input.LA( 1 ) == MINUS
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
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:292:5: (d=
            // INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )?
            int alt11 = 2;
            int LA11_0 = input.LA( 1 );
            
            if ( ( LA11_0 == INT ) )
            {
               int LA11_1 = input.LA( 2 );
               
               if ( ( LA11_1 == STs || LA11_1 == DOT || ( LA11_1 >= MINUS && LA11_1 <= COMMA ) ) )
               {
                  alt11 = 1;
               }
            }
            switch ( alt11 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:292:6:
                  // d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+
               {
                  d = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst520 );
                  
                  cal.set( Calendar.DAY_OF_MONTH,
                           Integer.parseInt( ( d != null ? d.getText() : null ) ) );
                  
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:296:8:
                  // ( STs | MINUS_A | MINUS | COMMA | DOT )+
                  int cnt10 = 0;
                  loop10: do
                  {
                     int alt10 = 2;
                     int LA10_0 = input.LA( 1 );
                     
                     if ( ( LA10_0 == STs || LA10_0 == DOT || ( LA10_0 >= MINUS && LA10_0 <= COMMA ) ) )
                     {
                        alt10 = 1;
                     }
                     
                     switch ( alt10 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:
                        {
                           if ( input.LA( 1 ) == STs
                              || input.LA( 1 ) == DOT
                              || ( input.LA( 1 ) >= MINUS && input.LA( 1 ) <= COMMA ) )
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
                           if ( cnt10 >= 1 )
                              break loop10;
                           EarlyExitException eee = new EarlyExitException( 10,
                                                                            input );
                           throw eee;
                     }
                     cnt10++;
                  }
                  while ( true );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:297:5: (y=
            // INT )?
            int alt12 = 2;
            int LA12_0 = input.LA( 1 );
            
            if ( ( LA12_0 == INT ) )
            {
               alt12 = 1;
            }
            switch ( alt12 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:297:6:
                  // y= INT
               {
                  y = (Token) match( input, INT, FOLLOW_INT_in_date_on_M_Xst568 );
                  
                  parseYear( cal, ( y != null ? y.getText() : null ) );
                  hasYear = true;
                  
               }
                  break;
               
            }
            
            // if we have a year we have a full qualified date.
            // so we change nothing.
            if ( !hasYear && getLocalizedCalendar().after( cal ) )
               // If the date is before now we roll the year
               cal.roll( Calendar.YEAR, true );
            
         }
         
      }
      catch ( NumberFormatException e )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_on_M_Xst"
   
   // $ANTLR start "date_on_weekday"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:315:1:
   // date_on_weekday[Calendar cal] : ( NEXT )? wd= WEEKDAY ;
   public final void date_on_weekday( Calendar cal ) throws RecognitionException
   {
      Token wd = null;
      
      boolean nextWeek = false;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:320:4: ( ( NEXT
         // )? wd= WEEKDAY )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:320:6: ( NEXT
         // )? wd= WEEKDAY
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:320:6: (
            // NEXT )?
            int alt13 = 2;
            int LA13_0 = input.LA( 1 );
            
            if ( ( LA13_0 == NEXT ) )
            {
               alt13 = 1;
            }
            switch ( alt13 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:320:7:
                  // NEXT
               {
                  match( input, NEXT, FOLLOW_NEXT_in_date_on_weekday625 );
                  nextWeek = true;
                  
               }
                  break;
               
            }
            
            wd = (Token) match( input,
                                WEEKDAY,
                                FOLLOW_WEEKDAY_in_date_on_weekday633 );
            
            final SimpleDateFormat sdf = new SimpleDateFormat( "EE", LOCALE );
            sdf.parse( ( wd != null ? wd.getText() : null ) );
            
            final int parsedWeekDay = sdf.getCalendar()
                                         .get( Calendar.DAY_OF_WEEK );
            final int currentWeekDay = cal.get( Calendar.DAY_OF_WEEK );
            
            cal.set( Calendar.DAY_OF_WEEK, parsedWeekDay );
            
            // If the weekday is before today or today, we adjust to next week.
            if ( parsedWeekDay <= currentWeekDay )
               cal.roll( Calendar.WEEK_OF_YEAR, true );
            
            // if the next week is explicitly enforced
            if ( nextWeek )
               cal.roll( Calendar.WEEK_OF_YEAR, true );
            
         }
         
      }
      catch ( ParseException pe )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return;
   }
   


   // $ANTLR end "date_on_weekday"
   
   // $ANTLR start "date_in_X_YMWD"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:344:1:
   // date_in_X_YMWD[Calendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA )
   // date_in_X_YMWD_distance[$cal] )* ;
   public final void date_in_X_YMWD( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:345:4: ( ( IN
         // )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:345:7: ( IN )?
         // date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:345:7: ( IN
            // )?
            int alt14 = 2;
            int LA14_0 = input.LA( 1 );
            
            if ( ( LA14_0 == IN ) )
            {
               alt14 = 1;
            }
            switch ( alt14 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:345:7:
                  // IN
               {
                  match( input, IN, FOLLOW_IN_in_date_in_X_YMWD667 );
                  
               }
                  break;
               
            }
            
            pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD682 );
            date_in_X_YMWD_distance( cal );
            
            state._fsp--;
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:346:7: ( (
            // AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop15: do
            {
               int alt15 = 2;
               int LA15_0 = input.LA( 1 );
               
               if ( ( LA15_0 == AND || LA15_0 == COMMA ) )
               {
                  alt15 = 1;
               }
               
               switch ( alt15 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:346:8:
                     // ( AND | COMMA ) date_in_X_YMWD_distance[$cal]
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
                     
                     pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD701 );
                     date_in_X_YMWD_distance( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  
                  default :
                     break loop15;
               }
            }
            while ( true );
            
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
   


   // $ANTLR end "date_in_X_YMWD"
   
   // $ANTLR start "date_in_X_YMWD_distance"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:349:1:
   // date_in_X_YMWD_distance[Calendar cal] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
   public final void date_in_X_YMWD_distance( Calendar cal ) throws RecognitionException
   {
      Token a = null;
      
      int amount = -1;
      int calField = Calendar.YEAR;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:355:4: ( (a=
         // NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:355:6: (a=
         // NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:355:6: (a=
            // NUM_STR | a= INT )
            int alt16 = 2;
            int LA16_0 = input.LA( 1 );
            
            if ( ( LA16_0 == NUM_STR ) )
            {
               alt16 = 1;
            }
            else if ( ( LA16_0 == INT ) )
            {
               alt16 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     16,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt16 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:355:10:
                  // a= NUM_STR
               {
                  a = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_date_in_X_YMWD_distance738 );
                  amount = strToNumber( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:356:10:
                  // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_date_in_X_YMWD_distance753 );
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:357:6: (
            // YEARS | MONTHS | WEEKS | DAYS )
            int alt17 = 4;
            switch ( input.LA( 1 ) )
            {
               case YEARS:
               {
                  alt17 = 1;
               }
                  break;
               case MONTHS:
               {
                  alt17 = 2;
               }
                  break;
               case WEEKS:
               {
                  alt17 = 3;
               }
                  break;
               case DAYS:
               {
                  alt17 = 4;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        17,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt17 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:357:12:
                  // YEARS
               {
                  match( input,
                         YEARS,
                         FOLLOW_YEARS_in_date_in_X_YMWD_distance773 );
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:358:12:
                  // MONTHS
               {
                  match( input,
                         MONTHS,
                         FOLLOW_MONTHS_in_date_in_X_YMWD_distance786 );
                  calField = Calendar.MONTH;
                  
               }
                  break;
               case 3:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:359:12:
                  // WEEKS
               {
                  match( input,
                         WEEKS,
                         FOLLOW_WEEKS_in_date_in_X_YMWD_distance802 );
                  calField = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 4:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:360:12:
                  // DAYS
               {
                  match( input, DAYS, FOLLOW_DAYS_in_date_in_X_YMWD_distance819 );
                  calField = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               
            }
            
            if ( amount != -1 )
            {
               cal.roll( calField, amount );
            }
            else
            {
               throw new RecognitionException();
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
   


   // $ANTLR end "date_in_X_YMWD_distance"
   
   // $ANTLR start "date_end_of_the_MW"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:377:1:
   // date_end_of_the_MW[Calendar cal] : END ( OF )? ( THE )? ( WEEKS | MONTHS ) ;
   public final void date_end_of_the_MW( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:378:4: ( END (
         // OF )? ( THE )? ( WEEKS | MONTHS ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:378:6: END ( OF
         // )? ( THE )? ( WEEKS | MONTHS )
         {
            match( input, END, FOLLOW_END_in_date_end_of_the_MW858 );
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:378:10: ( OF
            // )?
            int alt18 = 2;
            int LA18_0 = input.LA( 1 );
            
            if ( ( LA18_0 == OF ) )
            {
               alt18 = 1;
            }
            switch ( alt18 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:378:10:
                  // OF
               {
                  match( input, OF, FOLLOW_OF_in_date_end_of_the_MW860 );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:378:14: (
            // THE )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == THE ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:378:14:
                  // THE
               {
                  match( input, THE, FOLLOW_THE_in_date_end_of_the_MW863 );
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:379:6: (
            // WEEKS | MONTHS )
            int alt20 = 2;
            int LA20_0 = input.LA( 1 );
            
            if ( ( LA20_0 == WEEKS ) )
            {
               alt20 = 1;
            }
            else if ( ( LA20_0 == MONTHS ) )
            {
               alt20 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     20,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt20 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:379:10:
                  // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_date_end_of_the_MW875 );
                  
                  rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:383:10:
                  // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_date_end_of_the_MW897 );
                  
                  rollToEndOf( Calendar.DAY_OF_MONTH, cal );
                  
               }
                  break;
               
            }
            
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
   


   // $ANTLR end "date_end_of_the_MW"
   
   // $ANTLR start "date_natural"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:389:1:
   // date_natural[Calendar cal] : ( ( TODAY | TONIGHT ) | TOMORROW | YESTERDAY );
   public final void date_natural( Calendar cal ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:390:4: ( (
         // TODAY | TONIGHT ) | TOMORROW | YESTERDAY )
         int alt21 = 3;
         switch ( input.LA( 1 ) )
         {
            case TODAY:
            case TONIGHT:
            {
               alt21 = 1;
            }
               break;
            case TOMORROW:
            {
               alt21 = 2;
            }
               break;
            case YESTERDAY:
            {
               alt21 = 3;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     21,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt21 )
         {
            case 1:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:390:6: (
               // TODAY | TONIGHT )
            {
               if ( input.LA( 1 ) == TODAY || input.LA( 1 ) == TONIGHT )
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
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:393:6:
               // TOMORROW
            {
               match( input, TOMORROW, FOLLOW_TOMORROW_in_date_natural946 );
               
               cal.roll( Calendar.DAY_OF_YEAR, true );
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateParser.g:397:6:
               // YESTERDAY
            {
               match( input, YESTERDAY, FOLLOW_YESTERDAY_in_date_natural960 );
               
               cal.roll( Calendar.DAY_OF_YEAR, false );
               
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
   
   // $ANTLR end "date_natural"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_date_full_in_parseDate60 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_in_parseDate81 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_in_parseDate104 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_end_of_the_MW_in_parseDate120 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_natural_in_parseDate132 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_full186 = new BitSet( new long[]
   { 0x0000000780000000L } );
   
   public static final BitSet FOLLOW_set_in_date_full188 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_date_full215 = new BitSet( new long[]
   { 0x0000000780000000L } );
   
   public static final BitSet FOLLOW_set_in_date_full217 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_date_full242 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_ON_in_date_on269 = new BitSet( new long[]
   { 0x0000002060001000L } );
   
   public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on276 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_M_Xst_in_date_on292 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_weekday_in_date_on311 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M340 = new BitSet( new long[]
   { 0x0000001D20010802L } );
   
   public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M342 = new BitSet( new long[]
   { 0x0000001D20000802L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M360 = new BitSet( new long[]
   { 0x0000000020000000L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M390 = new BitSet( new long[]
   { 0x0000002500000002L } );
   
   public static final BitSet FOLLOW_set_in_date_on_Xst_of_M409 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M427 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst485 = new BitSet( new long[]
   { 0x0000003500000002L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst500 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst520 = new BitSet( new long[]
   { 0x0000001D00010000L } );
   
   public static final BitSet FOLLOW_set_in_date_on_M_Xst538 = new BitSet( new long[]
   { 0x0000003D00010002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_M_Xst568 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEXT_in_date_on_weekday625 = new BitSet( new long[]
   { 0x0000000040000000L } );
   
   public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday633 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_IN_in_date_in_X_YMWD667 = new BitSet( new long[]
   { 0x0000012000000000L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD682 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_set_in_date_in_X_YMWD692 = new BitSet( new long[]
   { 0x0000012000000000L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD701 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance738 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance753 = new BitSet( new long[]
   { 0x0000000003C00000L } );
   
   public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance773 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance786 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance802 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance819 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_END_in_date_end_of_the_MW858 = new BitSet( new long[]
   { 0x0000000001808800L } );
   
   public static final BitSet FOLLOW_OF_in_date_end_of_the_MW860 = new BitSet( new long[]
   { 0x0000000001808000L } );
   
   public static final BitSet FOLLOW_THE_in_date_end_of_the_MW863 = new BitSet( new long[]
   { 0x0000000001800000L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW875 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW897 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_date_natural926 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TOMORROW_in_date_natural946 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_YESTERDAY_in_date_natural960 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
