// $ANTLR 3.2 Sep 23, 2009 12:02:23 F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g 2010-10-26 13:14:16

package dev.drsoran.moloko.grammar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.grammar.lang.RecurrPatternLanguage;


public class RecurrenceParser extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "EVERY", "AFTER", "DAYS", "WEEKS",
    "BIWEEKLY", "ON", "THE", "MONTHS", "YEARS", "IN", "OF", "AND", "COMMA",
    "LAST", "INT", "DOT", "ST_S", "FIRST", "SECOND", "OTHER", "THIRD",
    "FOURTH", "FIFTH", "NUM_ONE", "NUM_TWO", "NUM_THREE", "NUM_FOUR",
    "NUM_FIVE", "NUM_SIX", "NUM_SEVEN", "NUM_EIGHT", "NUM_NINE", "NUM_TEN",
    "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY",
    "SUNDAY", "WEEKEND", "WEEKDAY_LIT", "MONTH", "OP_FREQ", "VAL_YEARLY",
    "OP_BYDAY", "OP_BYMONTH", "VAL_MONTHLY", "OP_BYMONTHDAY", "VAL_WEEKLY",
    "VAL_DAILY", "OP_INTERVAL", "UNTIL", "FOR", "SEMICOLON", "EQUALS", "MINUS",
    "OP_UNTIL", "OP_COUNT", "WS" };
   
   public static final int THIRD = 24;
   
   public static final int NUM_TWO = 28;
   
   public static final int NUM_NINE = 35;
   
   public static final int VAL_WEEKLY = 53;
   
   public static final int WEDNESDAY = 39;
   
   public static final int THE = 10;
   
   public static final int OP_FREQ = 47;
   
   public static final int FOR = 57;
   
   public static final int EQUALS = 59;
   
   public static final int OP_BYMONTHDAY = 52;
   
   public static final int NUM_SIX = 32;
   
   public static final int AND = 15;
   
   public static final int OP_BYDAY = 49;
   
   public static final int EOF = -1;
   
   public static final int MONTH = 46;
   
   public static final int FRIDAY = 41;
   
   public static final int IN = 13;
   
   public static final int NUM_THREE = 29;
   
   public static final int COMMA = 16;
   
   public static final int NUM_ONE = 27;
   
   public static final int OP_COUNT = 62;
   
   public static final int LAST = 17;
   
   public static final int DOT = 19;
   
   public static final int ST_S = 20;
   
   public static final int VAL_DAILY = 54;
   
   public static final int NUM_EIGHT = 34;
   
   public static final int FOURTH = 25;
   
   public static final int BIWEEKLY = 8;
   
   public static final int SECOND = 22;
   
   public static final int OTHER = 23;
   
   public static final int NUM_FOUR = 30;
   
   public static final int SATURDAY = 42;
   
   public static final int NUM_SEVEN = 33;
   
   public static final int EVERY = 4;
   
   public static final int WEEKEND = 44;
   
   public static final int ON = 9;
   
   public static final int MONDAY = 37;
   
   public static final int SUNDAY = 43;
   
   public static final int SEMICOLON = 58;
   
   public static final int INT = 18;
   
   public static final int MINUS = 60;
   
   public static final int AFTER = 5;
   
   public static final int OF = 14;
   
   public static final int YEARS = 12;
   
   public static final int VAL_YEARLY = 48;
   
   public static final int NUM_FIVE = 31;
   
   public static final int FIFTH = 26;
   
   public static final int DAYS = 6;
   
   public static final int NUM_TEN = 36;
   
   public static final int WS = 63;
   
   public static final int WEEKS = 7;
   
   public static final int OP_UNTIL = 61;
   
   public static final int THURSDAY = 40;
   
   public static final int OP_INTERVAL = 55;
   
   public static final int UNTIL = 56;
   
   public static final int MONTHS = 11;
   
   public static final int WEEKDAY_LIT = 45;
   
   public static final int OP_BYMONTH = 50;
   
   public static final int VAL_MONTHLY = 51;
   
   public static final int TUESDAY = 38;
   
   public static final int FIRST = 21;
   
   

   // delegates
   // delegators
   
   public RecurrenceParser( TokenStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public RecurrenceParser( TokenStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   public String[] getTokenNames()
   {
      return RecurrenceParser.tokenNames;
   }
   


   public String getGrammarFileName()
   {
      return "F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g";
   }
   


   public RecurrenceParser()
   {
      super( null );
   }
   
   private final static Locale LOCALE = Locale.ENGLISH;
   
   
   private final static class CmpWeekday implements Comparator< String >
   {
      private final static int weekdayToInt( String wd )
      {
         // only take the last 2 chars, the leading chars can be
         // Xst values.
         final String weekday = wd.substring( wd.length() - 2 );
         
         if ( weekday.equals( BYDAY_MON ) )
            return 1;
         else if ( weekday.equals( BYDAY_TUE ) )
            return 2;
         else if ( weekday.equals( BYDAY_WED ) )
            return 3;
         else if ( weekday.equals( BYDAY_THU ) )
            return 4;
         else if ( weekday.equals( BYDAY_FRI ) )
            return 5;
         else if ( weekday.equals( BYDAY_SAT ) )
            return 6;
         else if ( weekday.equals( BYDAY_SUN ) )
            return 7;
         else
            return 1;
      }
      


      public int compare( String wd1, String wd2 )
      {
         return weekdayToInt( wd1 ) - weekdayToInt( wd2 );
      }
   }
   
   private final static CmpWeekday CMP_WEEKDAY = new CmpWeekday();
   
   public final static String OP_BYDAY_LIT = "BYDAY";
   
   public final static String OP_BYMONTH_LIT = "BYMONTH";
   
   public final static String OP_BYMONTHDAY_LIT = "BYMONTHDAY";
   
   public final static String OP_INTERVAL_LIT = "INTERVAL";
   
   public final static String OP_FREQ_LIT = "FREQ";
   
   public final static String OP_UNTIL_LIT = "UNTIL";
   
   public final static String OP_COUNT_LIT = "COUNT";
   
   public final static String VAL_DAILY_LIT = "DAILY";
   
   public final static String VAL_WEEKLY_LIT = "WEEKLY";
   
   public final static String VAL_MONTHLY_LIT = "MONTHLY";
   
   public final static String VAL_YEARLY_LIT = "YEARLY";
   
   public final static String IS_EVERY = "IS_EVERY";
   
   public final static String BYDAY_MON = "MO";
   
   public final static String BYDAY_TUE = "TU";
   
   public final static String BYDAY_WED = "WE";
   
   public final static String BYDAY_THU = "TH";
   
   public final static String BYDAY_FRI = "FR";
   
   public final static String BYDAY_SAT = "SA";
   
   public final static String BYDAY_SUN = "SU";
   
   

   @SuppressWarnings( "unchecked" )
   private final static String join( String delim, Iterable values )
   {
      StringBuilder result = new StringBuilder();
      
      final Iterator i = values.iterator();
      
      for ( boolean hasNext = i.hasNext(); hasNext; )
      {
         result.append( i.next() );
         hasNext = i.hasNext();
         
         if ( hasNext )
            result.append( delim );
      }
      
      return result.toString();
   }
   


   // $ANTLR start "parseRecurrence"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:137:1:
   // parseRecurrence returns [HashMap< String, Object > res] : ( ( EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS
   // | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r=
   // recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN |
   // OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints]
   // recurr_WD[weekdays, \"\"] ) ) | EOF );
   public final HashMap< String, Object > parseRecurrence() throws RecognitionException
   {
      HashMap< String, Object > res = null;
      
      int interval = 0;
      
      RecurrenceParser.recurr_Monthly_return r = null;
      
      int m = 0;
      
      int firstEntry = 0;
      
      res = new HashMap< String, Object >();
      Boolean isEvery = Boolean.FALSE;
      
      final TreeSet< String > weekdays = new TreeSet< String >( CMP_WEEKDAY );
      final TreeSet< Integer > ints = new TreeSet< Integer >();
      
      interval = 1;
      String freq = null;
      String resolution = null;
      String resolutionVal = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:161:4: ( (
         // EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )?
         // recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON
         // )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) |
         // recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) |
         // EOF )
         int alt17 = 2;
         int LA17_0 = input.LA( 1 );
         
         if ( ( ( LA17_0 >= EVERY && LA17_0 <= BIWEEKLY )
            || ( LA17_0 >= MONTHS && LA17_0 <= YEARS ) || LA17_0 == INT || ( LA17_0 >= FIRST && LA17_0 <= WEEKDAY_LIT ) ) )
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:161:6: (
               // EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )?
               // recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS
               // ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) |
               // recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"]
               // ) )
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:161:6: (
               // EVERY | AFTER )?
               int alt1 = 3;
               int LA1_0 = input.LA( 1 );
               
               if ( ( LA1_0 == EVERY ) )
               {
                  alt1 = 1;
               }
               else if ( ( LA1_0 == AFTER ) )
               {
                  alt1 = 2;
               }
               switch ( alt1 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:161:7:
                     // EVERY
                  {
                     match( input, EVERY, FOLLOW_EVERY_in_parseRecurrence68 );
                     isEvery = Boolean.TRUE;
                     
                  }
                     break;
                  case 2:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:161:43:
                     // AFTER
                  {
                     match( input, AFTER, FOLLOW_AFTER_in_parseRecurrence74 );
                     
                  }
                     break;
                  
               }
               
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:162:4: (
               // (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"]
               // )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r=
               // recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] |
               // recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )
               int alt16 = 4;
               alt16 = dfa16.predict( input );
               switch ( alt16 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:162:5:
                     // (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays,
                     // \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? (
                     // THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? )
                  {
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:162:13:
                     // (interval= parse_Number )?
                     int alt2 = 2;
                     int LA2_0 = input.LA( 1 );
                     
                     if ( ( LA2_0 == INT || ( LA2_0 >= NUM_ONE && LA2_0 <= NUM_TEN ) ) )
                     {
                        alt2 = 1;
                     }
                     switch ( alt2 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:162:13:
                           // interval= parse_Number
                        {
                           pushFollow( FOLLOW_parse_Number_in_parseRecurrence84 );
                           interval = parse_Number();
                           
                           state._fsp--;
                           
                        }
                           break;
                        
                     }
                     
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:163:4:
                     // ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )?
                     // ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r=
                     // recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? )
                     int alt15 = 4;
                     switch ( input.LA( 1 ) )
                     {
                        case DAYS:
                        {
                           alt15 = 1;
                        }
                           break;
                        case WEEKS:
                        case BIWEEKLY:
                        {
                           alt15 = 2;
                        }
                           break;
                        case MONTHS:
                        {
                           alt15 = 3;
                        }
                           break;
                        case YEARS:
                        {
                           alt15 = 4;
                        }
                           break;
                        default :
                           NoViableAltException nvae = new NoViableAltException( "",
                                                                                 15,
                                                                                 0,
                                                                                 input );
                           
                           throw nvae;
                     }
                     
                     switch ( alt15 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:164:9:
                           // DAYS
                        {
                           match( input,
                                  DAYS,
                                  FOLLOW_DAYS_in_parseRecurrence100 );
                           freq = VAL_DAILY_LIT;
                           
                        }
                           break;
                        case 2:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:165:9:
                           // ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )?
                        {
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:165:9:
                           // ( WEEKS | BIWEEKLY )
                           int alt3 = 2;
                           int LA3_0 = input.LA( 1 );
                           
                           if ( ( LA3_0 == WEEKS ) )
                           {
                              alt3 = 1;
                           }
                           else if ( ( LA3_0 == BIWEEKLY ) )
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
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:166:14:
                                 // WEEKS
                              {
                                 match( input,
                                        WEEKS,
                                        FOLLOW_WEEKS_in_parseRecurrence173 );
                                 
                              }
                                 break;
                              case 2:
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:167:14:
                                 // BIWEEKLY
                              {
                                 match( input,
                                        BIWEEKLY,
                                        FOLLOW_BIWEEKLY_in_parseRecurrence188 );
                                 
                                 interval = 2;
                                 
                              }
                                 break;
                              
                           }
                           
                           freq = VAL_WEEKLY_LIT;
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:172:10:
                           // ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )?
                           int alt6 = 2;
                           int LA6_0 = input.LA( 1 );
                           
                           if ( ( ( LA6_0 >= ON && LA6_0 <= THE ) || ( LA6_0 >= MONDAY && LA6_0 <= WEEKDAY_LIT ) ) )
                           {
                              alt6 = 1;
                           }
                           switch ( alt6 )
                           {
                              case 1:
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:172:11:
                                 // ( ON )? ( THE )? recurr_WD[weekdays, \"\"]
                              {
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:172:11:
                                 // ( ON )?
                                 int alt4 = 2;
                                 int LA4_0 = input.LA( 1 );
                                 
                                 if ( ( LA4_0 == ON ) )
                                 {
                                    alt4 = 1;
                                 }
                                 switch ( alt4 )
                                 {
                                    case 1:
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:172:11:
                                       // ON
                                    {
                                       match( input,
                                              ON,
                                              FOLLOW_ON_in_parseRecurrence276 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:172:15:
                                 // ( THE )?
                                 int alt5 = 2;
                                 int LA5_0 = input.LA( 1 );
                                 
                                 if ( ( LA5_0 == THE ) )
                                 {
                                    alt5 = 1;
                                 }
                                 switch ( alt5 )
                                 {
                                    case 1:
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:172:15:
                                       // THE
                                    {
                                       match( input,
                                              THE,
                                              FOLLOW_THE_in_parseRecurrence279 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 pushFollow( FOLLOW_recurr_WD_in_parseRecurrence282 );
                                 recurr_WD( weekdays, "" );
                                 
                                 state._fsp--;
                                 
                                 resolution = OP_BYDAY_LIT;
                                 resolutionVal = join( ",", weekdays );
                                 
                              }
                                 break;
                              
                           }
                           
                        }
                           break;
                        case 3:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:178:9:
                           // MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )?
                        {
                           match( input,
                                  MONTHS,
                                  FOLLOW_MONTHS_in_parseRecurrence322 );
                           freq = VAL_MONTHLY_LIT;
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:179:10:
                           // ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )?
                           int alt9 = 2;
                           int LA9_0 = input.LA( 1 );
                           
                           if ( ( ( LA9_0 >= ON && LA9_0 <= THE )
                              || LA9_0 == INT || ( LA9_0 >= FIRST && LA9_0 <= FIFTH ) ) )
                           {
                              alt9 = 1;
                           }
                           switch ( alt9 )
                           {
                              case 1:
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:179:11:
                                 // ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints]
                              {
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:179:11:
                                 // ( ON )?
                                 int alt7 = 2;
                                 int LA7_0 = input.LA( 1 );
                                 
                                 if ( ( LA7_0 == ON ) )
                                 {
                                    alt7 = 1;
                                 }
                                 switch ( alt7 )
                                 {
                                    case 1:
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:179:11:
                                       // ON
                                    {
                                       match( input,
                                              ON,
                                              FOLLOW_ON_in_parseRecurrence380 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:179:15:
                                 // ( THE )?
                                 int alt8 = 2;
                                 int LA8_0 = input.LA( 1 );
                                 
                                 if ( ( LA8_0 == THE ) )
                                 {
                                    alt8 = 1;
                                 }
                                 switch ( alt8 )
                                 {
                                    case 1:
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:179:15:
                                       // THE
                                    {
                                       match( input,
                                              THE,
                                              FOLLOW_THE_in_parseRecurrence383 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 pushFollow( FOLLOW_recurr_Monthly_in_parseRecurrence388 );
                                 r = recurr_Monthly( weekdays, ints );
                                 
                                 state._fsp--;
                                 
                                 freq = r.freq;
                                 interval = r.interval;
                                 resolution = r.resolution;
                                 resolutionVal = r.resolutionVal;
                                 
                              }
                                 break;
                              
                           }
                           
                        }
                           break;
                        case 4:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:187:9:
                           // YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m=
                           // parse_Month )? )?
                        {
                           match( input,
                                  YEARS,
                                  FOLLOW_YEARS_in_parseRecurrence432 );
                           freq = VAL_YEARLY_LIT;
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:188:10:
                           // ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m=
                           // parse_Month )? )?
                           int alt14 = 2;
                           int LA14_0 = input.LA( 1 );
                           
                           if ( ( ( LA14_0 >= ON && LA14_0 <= THE )
                              || LA14_0 == INT || ( LA14_0 >= FIRST && LA14_0 <= FIFTH ) ) )
                           {
                              alt14 = 1;
                           }
                           switch ( alt14 )
                           {
                              case 1:
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:188:11:
                                 // ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m=
                                 // parse_Month )?
                              {
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:188:11:
                                 // ( ON )?
                                 int alt10 = 2;
                                 int LA10_0 = input.LA( 1 );
                                 
                                 if ( ( LA10_0 == ON ) )
                                 {
                                    alt10 = 1;
                                 }
                                 switch ( alt10 )
                                 {
                                    case 1:
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:188:11:
                                       // ON
                                    {
                                       match( input,
                                              ON,
                                              FOLLOW_ON_in_parseRecurrence491 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:188:15:
                                 // ( THE )?
                                 int alt11 = 2;
                                 int LA11_0 = input.LA( 1 );
                                 
                                 if ( ( LA11_0 == THE ) )
                                 {
                                    alt11 = 1;
                                 }
                                 switch ( alt11 )
                                 {
                                    case 1:
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:188:15:
                                       // THE
                                    {
                                       match( input,
                                              THE,
                                              FOLLOW_THE_in_parseRecurrence494 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 pushFollow( FOLLOW_recurr_Monthly_in_parseRecurrence499 );
                                 r = recurr_Monthly( weekdays, ints );
                                 
                                 state._fsp--;
                                 
                                 freq = r.freq;
                                 interval = r.interval;
                                 resolution = r.resolution;
                                 resolutionVal = r.resolutionVal;
                                 
                                 if ( !( ( r.hasWD ) ) )
                                 {
                                    throw new FailedPredicateException( input,
                                                                        "parseRecurrence",
                                                                        " r.hasWD " );
                                 }
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:195:35:
                                 // ( ( IN | OF )? m= parse_Month )?
                                 int alt13 = 2;
                                 int LA13_0 = input.LA( 1 );
                                 
                                 if ( ( ( LA13_0 >= IN && LA13_0 <= OF ) || LA13_0 == MONTH ) )
                                 {
                                    alt13 = 1;
                                 }
                                 switch ( alt13 )
                                 {
                                    case 1:
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:196:37:
                                       // ( IN | OF )? m= parse_Month
                                    {
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:196:37:
                                       // ( IN | OF )?
                                       int alt12 = 2;
                                       int LA12_0 = input.LA( 1 );
                                       
                                       if ( ( ( LA12_0 >= IN && LA12_0 <= OF ) ) )
                                       {
                                          alt12 = 1;
                                       }
                                       switch ( alt12 )
                                       {
                                          case 1:
                                             // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:
                                          {
                                             if ( ( input.LA( 1 ) >= IN && input.LA( 1 ) <= OF ) )
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
                                       
                                       pushFollow( FOLLOW_parse_Month_in_parseRecurrence631 );
                                       m = parse_Month();
                                       
                                       state._fsp--;
                                       
                                       freq = VAL_YEARLY_LIT;
                                       interval = 1;
                                       res.put( OP_BYMONTH_LIT,
                                                Integer.toString( m ) );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                              }
                                 break;
                              
                           }
                           
                        }
                           break;
                        
                     }
                     
                  }
                     break;
                  case 2:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:206:6:
                     // recurr_Xst[ints]
                  {
                     pushFollow( FOLLOW_recurr_Xst_in_parseRecurrence731 );
                     recurr_Xst( ints );
                     
                     state._fsp--;
                     
                     freq = VAL_MONTHLY_LIT;
                     interval = 1;
                     resolution = OP_BYMONTHDAY_LIT;
                     resolutionVal = join( ",", ints );
                     
                  }
                     break;
                  case 3:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:213:6:
                     // recurr_WD[weekdays, \"\"]
                  {
                     pushFollow( FOLLOW_recurr_WD_in_parseRecurrence746 );
                     recurr_WD( weekdays, "" );
                     
                     state._fsp--;
                     
                     freq = VAL_WEEKLY_LIT;
                     interval = 1;
                     resolution = OP_BYDAY_LIT;
                     resolutionVal = join( ",", weekdays );
                     
                  }
                     break;
                  case 4:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:220:6:
                     // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                  {
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:220:6:
                     // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:221:9:
                     // firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"]
                     {
                        pushFollow( FOLLOW_recurr_Xst_in_parseRecurrence773 );
                        firstEntry = recurr_Xst( ints );
                        
                        state._fsp--;
                        
                        pushFollow( FOLLOW_recurr_WD_in_parseRecurrence776 );
                        recurr_WD( weekdays, "" );
                        
                        state._fsp--;
                        
                        freq = VAL_WEEKLY_LIT;
                        interval = firstEntry;
                        resolution = OP_BYDAY_LIT;
                        resolutionVal = join( ",", weekdays );
                        
                     }
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:230:6:
               // EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseRecurrence806 );
               
            }
               break;
            
         }
         
         res.put( OP_FREQ_LIT, freq );
         res.put( OP_INTERVAL_LIT, new Integer( interval ) );
         
         if ( resolution != null && resolutionVal != null )
            res.put( resolution, resolutionVal );
         
         res.put( IS_EVERY, isEvery );
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return res;
   }
   


   // $ANTLR end "parseRecurrence"
   
   // $ANTLR start "recurr_Xst"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:237:1:
   // recurr_Xst[Set< Integer > res] returns [int firstEntry] : x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? ;
   public final int recurr_Xst( Set< Integer > res ) throws RecognitionException
   {
      int firstEntry = 0;
      
      int x = 0;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:238:4: (x=
         // parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:238:6: x=
         // parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )?
         {
            pushFollow( FOLLOW_parse_Xst_in_recurr_Xst841 );
            x = parse_Xst();
            
            state._fsp--;
            
            res.add( x );
            firstEntry = x;
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:239:4: ( ( (
            // AND | COMMA ) x= parse_Xst )+ )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( ( LA19_0 >= AND && LA19_0 <= COMMA ) ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:239:5:
                  // ( ( AND | COMMA ) x= parse_Xst )+
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:239:5:
                  // ( ( AND | COMMA ) x= parse_Xst )+
                  int cnt18 = 0;
                  loop18: do
                  {
                     int alt18 = 2;
                     int LA18_0 = input.LA( 1 );
                     
                     if ( ( ( LA18_0 >= AND && LA18_0 <= COMMA ) ) )
                     {
                        alt18 = 1;
                     }
                     
                     switch ( alt18 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:239:6:
                           // ( AND | COMMA ) x= parse_Xst
                        {
                           if ( ( input.LA( 1 ) >= AND && input.LA( 1 ) <= COMMA ) )
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
                           
                           pushFollow( FOLLOW_parse_Xst_in_recurr_Xst874 );
                           x = parse_Xst();
                           
                           state._fsp--;
                           
                           res.add( x );
                           
                        }
                           break;
                        
                        default :
                           if ( cnt18 >= 1 )
                              break loop18;
                           EarlyExitException eee = new EarlyExitException( 18,
                                                                            input );
                           throw eee;
                     }
                     cnt18++;
                  }
                  while ( true );
                  
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
      }
      return firstEntry;
   }
   


   // $ANTLR end "recurr_Xst"
   
   // $ANTLR start "recurr_WD"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:246:1: recurr_WD[Set<
   // String > weekdays, String Xst] : parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays,
   // Xst, true] )+ )? ;
   public final void recurr_WD( Set< String > weekdays, String Xst ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:247:4: (
         // parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:247:6:
         // parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
         {
            pushFollow( FOLLOW_parse_Weekday_in_recurr_WD909 );
            parse_Weekday( weekdays, Xst, true );
            
            state._fsp--;
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:248:4: ( ( (
            // AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
            int alt21 = 2;
            int LA21_0 = input.LA( 1 );
            
            if ( ( ( LA21_0 >= AND && LA21_0 <= COMMA ) ) )
            {
               alt21 = 1;
            }
            switch ( alt21 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:248:5:
                  // ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:248:5:
                  // ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
                  int cnt20 = 0;
                  loop20: do
                  {
                     int alt20 = 2;
                     int LA20_0 = input.LA( 1 );
                     
                     if ( ( ( LA20_0 >= AND && LA20_0 <= COMMA ) ) )
                     {
                        alt20 = 1;
                     }
                     
                     switch ( alt20 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:248:6:
                           // ( AND | COMMA ) parse_Weekday[weekdays, Xst, true]
                        {
                           if ( ( input.LA( 1 ) >= AND && input.LA( 1 ) <= COMMA ) )
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
                           
                           pushFollow( FOLLOW_parse_Weekday_in_recurr_WD939 );
                           parse_Weekday( weekdays, Xst, true );
                           
                           state._fsp--;
                           
                        }
                           break;
                        
                        default :
                           if ( cnt20 >= 1 )
                              break loop20;
                           EarlyExitException eee = new EarlyExitException( 20,
                                                                            input );
                           throw eee;
                     }
                     cnt20++;
                  }
                  while ( true );
                  
               }
                  break;
               
            }
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
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
   
   
   // $ANTLR end "recurr_WD"
   
   public static class recurr_Monthly_return extends ParserRuleReturnScope
   {
      public String freq;
      
      public String resolution;
      
      public String resolutionVal;
      
      public int interval;
      
      public boolean hasWD;
   };
   
   

   // $ANTLR start "recurr_Monthly"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:259:1:
   // recurr_Monthly[Set< String > weekdays,\r\n Set< Integer > ints ] returns [String freq,\r\n String resolution,\r\n
   // String resolutionVal,\r\n int interval,\r\n boolean hasWD] : firstEntry= recurr_Xst[ints] ( ( LAST )?
   // recurr_WD[weekdays, Integer.toString( firstEntry )] )? ;
   public final RecurrenceParser.recurr_Monthly_return recurr_Monthly( Set< String > weekdays,
                                                                       Set< Integer > ints ) throws RecognitionException
   {
      RecurrenceParser.recurr_Monthly_return retval = new RecurrenceParser.recurr_Monthly_return();
      retval.start = input.LT( 1 );
      
      int firstEntry = 0;
      
      retval.freq = VAL_MONTHLY_LIT;
      retval.interval = 1;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:270:4:
         // (firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:270:6:
         // firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
         {
            pushFollow( FOLLOW_recurr_Xst_in_recurr_Monthly1004 );
            firstEntry = recurr_Xst( ints );
            
            state._fsp--;
            
            retval.resolution = OP_BYMONTHDAY_LIT;
            retval.resolutionVal = join( ",", ints );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:274:8: ( (
            // LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            int alt23 = 2;
            int LA23_0 = input.LA( 1 );
            
            if ( ( LA23_0 == LAST || ( LA23_0 >= MONDAY && LA23_0 <= WEEKDAY_LIT ) ) )
            {
               alt23 = 1;
            }
            switch ( alt23 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:275:11:
                  // ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )]
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:275:11:
                  // ( LAST )?
                  int alt22 = 2;
                  int LA22_0 = input.LA( 1 );
                  
                  if ( ( LA22_0 == LAST ) )
                  {
                     alt22 = 1;
                  }
                  switch ( alt22 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:276:14:
                        // LAST
                     {
                        match( input, LAST, FOLLOW_LAST_in_recurr_Monthly1041 );
                        
                        firstEntry = -firstEntry;
                        
                     }
                        break;
                     
                  }
                  
                  pushFollow( FOLLOW_recurr_WD_in_recurr_Monthly1081 );
                  recurr_WD( weekdays, Integer.toString( firstEntry ) );
                  
                  state._fsp--;
                  
                  retval.hasWD = true;
                  retval.resolution = OP_BYDAY_LIT;
                  retval.resolutionVal = join( ",", weekdays );
                  
               }
                  break;
               
            }
            
         }
         
         retval.stop = input.LT( -1 );
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return retval;
   }
   


   // $ANTLR end "recurr_Monthly"
   
   // $ANTLR start "parse_Xst"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:294:1: parse_Xst
   // returns [int number] : (n= INT ( DOT | ST_S )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH );
   public final int parse_Xst() throws RecognitionException
   {
      int number = 0;
      
      Token n = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:295:4: (n= INT
         // ( DOT | ST_S )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH )
         int alt25 = 6;
         switch ( input.LA( 1 ) )
         {
            case INT:
            {
               alt25 = 1;
            }
               break;
            case FIRST:
            {
               alt25 = 2;
            }
               break;
            case SECOND:
            case OTHER:
            {
               alt25 = 3;
            }
               break;
            case THIRD:
            {
               alt25 = 4;
            }
               break;
            case FOURTH:
            {
               alt25 = 5;
            }
               break;
            case FIFTH:
            {
               alt25 = 6;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     25,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt25 )
         {
            case 1:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:295:6: n=
               // INT ( DOT | ST_S )?
            {
               n = (Token) match( input, INT, FOLLOW_INT_in_parse_Xst1137 );
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:295:12: (
               // DOT | ST_S )?
               int alt24 = 2;
               int LA24_0 = input.LA( 1 );
               
               if ( ( ( LA24_0 >= DOT && LA24_0 <= ST_S ) ) )
               {
                  alt24 = 1;
               }
               switch ( alt24 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:
                  {
                     if ( ( input.LA( 1 ) >= DOT && input.LA( 1 ) <= ST_S ) )
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
               
               number = Integer.parseInt( ( n != null ? n.getText() : null ) );
               
               if ( number < 1 )
                  number = 1;
               else if ( number > 31 )
                  number = 31;
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:304:6:
               // FIRST
            {
               match( input, FIRST, FOLLOW_FIRST_in_parse_Xst1156 );
               number = 1;
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:305:6: (
               // SECOND | OTHER )
            {
               if ( ( input.LA( 1 ) >= SECOND && input.LA( 1 ) <= OTHER ) )
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
               
               number = 2;
               
            }
               break;
            case 4:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:306:6:
               // THIRD
            {
               match( input, THIRD, FOLLOW_THIRD_in_parse_Xst1191 );
               number = 3;
               
            }
               break;
            case 5:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:307:6:
               // FOURTH
            {
               match( input, FOURTH, FOLLOW_FOURTH_in_parse_Xst1211 );
               number = 4;
               
            }
               break;
            case 6:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:308:6:
               // FIFTH
            {
               match( input, FIFTH, FOLLOW_FIFTH_in_parse_Xst1230 );
               number = 5;
               
            }
               break;
            
         }
      }
      catch ( RecognitionException e )
      {
         
         return 1;
         
      }
      catch ( NumberFormatException nfe )
      {
         
         return 1;
         
      }
      finally
      {
      }
      return number;
   }
   


   // $ANTLR end "parse_Xst"
   
   // $ANTLR start "parse_Number"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:319:1: parse_Number
   // returns [int number] : (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN |
   // NUM_EIGHT | NUM_NINE | NUM_TEN );
   public final int parse_Number() throws RecognitionException
   {
      int number = 0;
      
      Token n = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:320:4: (n= INT
         // | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN
         // )
         int alt26 = 11;
         switch ( input.LA( 1 ) )
         {
            case INT:
            {
               alt26 = 1;
            }
               break;
            case NUM_ONE:
            {
               alt26 = 2;
            }
               break;
            case NUM_TWO:
            {
               alt26 = 3;
            }
               break;
            case NUM_THREE:
            {
               alt26 = 4;
            }
               break;
            case NUM_FOUR:
            {
               alt26 = 5;
            }
               break;
            case NUM_FIVE:
            {
               alt26 = 6;
            }
               break;
            case NUM_SIX:
            {
               alt26 = 7;
            }
               break;
            case NUM_SEVEN:
            {
               alt26 = 8;
            }
               break;
            case NUM_EIGHT:
            {
               alt26 = 9;
            }
               break;
            case NUM_NINE:
            {
               alt26 = 10;
            }
               break;
            case NUM_TEN:
            {
               alt26 = 11;
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:320:6: n=
               // INT
            {
               n = (Token) match( input, INT, FOLLOW_INT_in_parse_Number1288 );
               number = Integer.parseInt( ( n != null ? n.getText() : null ) );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:321:6:
               // NUM_ONE
            {
               match( input, NUM_ONE, FOLLOW_NUM_ONE_in_parse_Number1302 );
               number = 1;
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:322:6:
               // NUM_TWO
            {
               match( input, NUM_TWO, FOLLOW_NUM_TWO_in_parse_Number1314 );
               number = 2;
               
            }
               break;
            case 4:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:323:6:
               // NUM_THREE
            {
               match( input, NUM_THREE, FOLLOW_NUM_THREE_in_parse_Number1326 );
               number = 3;
               
            }
               break;
            case 5:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:324:6:
               // NUM_FOUR
            {
               match( input, NUM_FOUR, FOLLOW_NUM_FOUR_in_parse_Number1336 );
               number = 4;
               
            }
               break;
            case 6:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:325:6:
               // NUM_FIVE
            {
               match( input, NUM_FIVE, FOLLOW_NUM_FIVE_in_parse_Number1347 );
               number = 5;
               
            }
               break;
            case 7:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:326:6:
               // NUM_SIX
            {
               match( input, NUM_SIX, FOLLOW_NUM_SIX_in_parse_Number1358 );
               number = 6;
               
            }
               break;
            case 8:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:327:6:
               // NUM_SEVEN
            {
               match( input, NUM_SEVEN, FOLLOW_NUM_SEVEN_in_parse_Number1370 );
               number = 7;
               
            }
               break;
            case 9:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:328:6:
               // NUM_EIGHT
            {
               match( input, NUM_EIGHT, FOLLOW_NUM_EIGHT_in_parse_Number1380 );
               number = 8;
               
            }
               break;
            case 10:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:329:6:
               // NUM_NINE
            {
               match( input, NUM_NINE, FOLLOW_NUM_NINE_in_parse_Number1390 );
               number = 9;
               
            }
               break;
            case 11:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:330:6:
               // NUM_TEN
            {
               match( input, NUM_TEN, FOLLOW_NUM_TEN_in_parse_Number1401 );
               number = 10;
               
            }
               break;
            
         }
      }
      catch ( RecognitionException e )
      {
         
         return 1;
         
      }
      catch ( NumberFormatException nfe )
      {
         
         return 1;
         
      }
      finally
      {
      }
      return number;
   }
   


   // $ANTLR end "parse_Number"
   
   // $ANTLR start "parse_Weekday"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:341:1:
   // parse_Weekday[Set< String > weekdays, String Xst, boolean strict] returns [String weekday] : ( MONDAY | TUESDAY |
   // WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT );
   public final String parse_Weekday( Set< String > weekdays,
                                      String Xst,
                                      boolean strict ) throws RecognitionException
   {
      String weekday = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:342:4: ( MONDAY
         // | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT )
         int alt27 = 9;
         switch ( input.LA( 1 ) )
         {
            case MONDAY:
            {
               alt27 = 1;
            }
               break;
            case TUESDAY:
            {
               alt27 = 2;
            }
               break;
            case WEDNESDAY:
            {
               alt27 = 3;
            }
               break;
            case THURSDAY:
            {
               alt27 = 4;
            }
               break;
            case FRIDAY:
            {
               alt27 = 5;
            }
               break;
            case SATURDAY:
            {
               alt27 = 6;
            }
               break;
            case SUNDAY:
            {
               alt27 = 7;
            }
               break;
            case WEEKEND:
            {
               alt27 = 8;
            }
               break;
            case WEEKDAY_LIT:
            {
               alt27 = 9;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     27,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt27 )
         {
            case 1:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:342:6:
               // MONDAY
            {
               match( input, MONDAY, FOLLOW_MONDAY_in_parse_Weekday1451 );
               weekdays.add( Xst + BYDAY_MON );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:343:6:
               // TUESDAY
            {
               match( input, TUESDAY, FOLLOW_TUESDAY_in_parse_Weekday1465 );
               weekdays.add( Xst + BYDAY_TUE );
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:344:6:
               // WEDNESDAY
            {
               match( input, WEDNESDAY, FOLLOW_WEDNESDAY_in_parse_Weekday1478 );
               weekdays.add( Xst + BYDAY_WED );
               
            }
               break;
            case 4:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:345:6:
               // THURSDAY
            {
               match( input, THURSDAY, FOLLOW_THURSDAY_in_parse_Weekday1489 );
               weekdays.add( Xst + BYDAY_THU );
               
            }
               break;
            case 5:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:346:6:
               // FRIDAY
            {
               match( input, FRIDAY, FOLLOW_FRIDAY_in_parse_Weekday1501 );
               weekdays.add( Xst + BYDAY_FRI );
               
            }
               break;
            case 6:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:347:6:
               // SATURDAY
            {
               match( input, SATURDAY, FOLLOW_SATURDAY_in_parse_Weekday1515 );
               weekdays.add( Xst + BYDAY_SAT );
               
            }
               break;
            case 7:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:348:6:
               // SUNDAY
            {
               match( input, SUNDAY, FOLLOW_SUNDAY_in_parse_Weekday1527 );
               weekdays.add( Xst + BYDAY_SUN );
               
            }
               break;
            case 8:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:349:6:
               // WEEKEND
            {
               match( input, WEEKEND, FOLLOW_WEEKEND_in_parse_Weekday1541 );
               
               weekdays.add( Xst + BYDAY_SAT );
               weekdays.add( Xst + BYDAY_SUN );
               
            }
               break;
            case 9:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:353:6:
               // WEEKDAY_LIT
            {
               match( input,
                      WEEKDAY_LIT,
                      FOLLOW_WEEKDAY_LIT_in_parse_Weekday1554 );
               
               weekdays.add( Xst + BYDAY_MON );
               weekdays.add( Xst + BYDAY_TUE );
               weekdays.add( Xst + BYDAY_WED );
               weekdays.add( Xst + BYDAY_THU );
               weekdays.add( Xst + BYDAY_FRI );
               
            }
               break;
            
         }
      }
      catch ( RecognitionException e )
      {
         
         if ( strict )
            throw e;
         else
            return null;
         
      }
      finally
      {
      }
      return weekday;
   }
   


   // $ANTLR end "parse_Weekday"
   
   // $ANTLR start "parse_Month"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:369:1: parse_Month
   // returns [int number] : m= MONTH ;
   public final int parse_Month() throws RecognitionException
   {
      int number = 0;
      
      Token m = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:370:4: (m=
         // MONTH )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:370:6: m= MONTH
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_parse_Month1589 );
            
            try
            {
               final SimpleDateFormat sdf = new SimpleDateFormat( "MMM", LOCALE );
               sdf.parse( ( m != null ? m.getText() : null ) );
               
               number = sdf.getCalendar().get( Calendar.MONTH );
               
               if ( number == 0 )
                  ++number;
            }
            catch ( ParseException e )
            {
               number = 1;
            }
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         return 1;
         
      }
      finally
      {
      }
      return number;
   }
   


   // $ANTLR end "parse_Month"
   
   // $ANTLR start "parseRecurrencePattern"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:393:1:
   // parseRecurrencePattern[RecurrPatternLanguage lang, boolean every] returns [String sentence] : OP_FREQ ( (
   // VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] (
   // COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )? ) | ( VAL_MONTHLY
   // parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA
   // parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA
   // parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every]
   // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | (
   // VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] ) ) ;
   public final String parseRecurrencePattern( RecurrPatternLanguage lang,
                                               boolean every ) throws RecognitionException
   {
      String sentence = null;
      
      final StringBuilder sb = new StringBuilder();
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:402:4: (
         // OP_FREQ ( ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
         // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
         // parse_PatternMonth[lang, sb] ) )? ) | ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( (
         // OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
         // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | (
         // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang, sb, true]
         // ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\",
         // every] ) ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:402:6: OP_FREQ
         // ( ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY parse_PatternWeekday[lang,
         // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )? )
         // | ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY parse_PatternXst[lang,
         // sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA
         // parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\",
         // every] ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? )
         // | ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] ) )
         {
            match( input, OP_FREQ, FOLLOW_OP_FREQ_in_parseRecurrencePattern1649 );
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:403:6: ( (
            // VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY parse_PatternWeekday[lang, sb,
            // true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )? ) |
            // ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
            // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
            // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | (
            // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang, sb,
            // true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | ( VAL_DAILY parse_PatternInterval[lang, sb,
            // \"day\", every] ) )
            int alt36 = 4;
            switch ( input.LA( 1 ) )
            {
               case VAL_YEARLY:
               {
                  alt36 = 1;
               }
                  break;
               case VAL_MONTHLY:
               {
                  alt36 = 2;
               }
                  break;
               case VAL_WEEKLY:
               {
                  alt36 = 3;
               }
                  break;
               case VAL_DAILY:
               {
                  alt36 = 4;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        36,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt36 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:404:11:
                  // ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
                  // parse_PatternMonth[lang, sb] ) )? )
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:404:11:
                  // ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
                  // parse_PatternMonth[lang, sb] ) )? )
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:405:14:
                  // VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
                  // parse_PatternMonth[lang, sb] ) )?
                  {
                     match( input,
                            VAL_YEARLY,
                            FOLLOW_VAL_YEARLY_in_parseRecurrencePattern1684 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern1686 );
                     parse_PatternInterval( lang, sb, "year", every );
                     
                     state._fsp--;
                     
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:406:14:
                     // ( ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                     // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )?
                     int alt29 = 2;
                     int LA29_0 = input.LA( 1 );
                     
                     if ( ( LA29_0 == OP_BYDAY ) )
                     {
                        alt29 = 1;
                     }
                     switch ( alt29 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:407:17:
                           // ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] )
                        {
                           
                           sb.append( " " );
                           lang.add( sb, "on_the" );
                           sb.append( " " );
                           
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:410:17:
                           // ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] )
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:411:20:
                           // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb]
                           {
                              // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:411:20:
                              // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                              // false] )* )
                              // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:411:21:
                              // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                              // false] )*
                              {
                                 match( input,
                                        OP_BYDAY,
                                        FOLLOW_OP_BYDAY_in_parseRecurrencePattern1760 );
                                 pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern1762 );
                                 parse_PatternWeekday( lang, sb, true );
                                 
                                 state._fsp--;
                                 
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:412:23:
                                 // ( COMMA parse_PatternWeekday[lang, sb, false] )*
                                 loop28: do
                                 {
                                    int alt28 = 2;
                                    int LA28_0 = input.LA( 1 );
                                    
                                    if ( ( LA28_0 == COMMA ) )
                                    {
                                       alt28 = 1;
                                    }
                                    
                                    switch ( alt28 )
                                    {
                                       case 1:
                                          // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:412:24:
                                          // COMMA parse_PatternWeekday[lang, sb, false]
                                       {
                                          match( input,
                                                 COMMA,
                                                 FOLLOW_COMMA_in_parseRecurrencePattern1788 );
                                          sb.append( ", " );
                                          pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern1792 );
                                          parse_PatternWeekday( lang, sb, false );
                                          
                                          state._fsp--;
                                          
                                       }
                                          break;
                                       
                                       default :
                                          break loop28;
                                    }
                                 }
                                 while ( true );
                                 
                              }
                              
                              sb.append( " " );
                              lang.add( sb, "in" );
                              sb.append( " " );
                              
                              match( input,
                                     OP_BYMONTH,
                                     FOLLOW_OP_BYMONTH_in_parseRecurrencePattern1838 );
                              pushFollow( FOLLOW_parse_PatternMonth_in_parseRecurrencePattern1840 );
                              parse_PatternMonth( lang, sb );
                              
                              state._fsp--;
                              
                           }
                           
                        }
                           break;
                        
                     }
                     
                  }
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:420:11:
                  // ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
                  // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? )
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:420:11:
                  // ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
                  // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? )
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:421:14:
                  // VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
                  // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )?
                  {
                     match( input,
                            VAL_MONTHLY,
                            FOLLOW_VAL_MONTHLY_in_parseRecurrencePattern1924 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern1926 );
                     parse_PatternInterval( lang, sb, "month", every );
                     
                     state._fsp--;
                     
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:422:14:
                     // ( ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | (
                     // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )
                     // ) )?
                     int alt33 = 2;
                     int LA33_0 = input.LA( 1 );
                     
                     if ( ( LA33_0 == OP_BYDAY || LA33_0 == OP_BYMONTHDAY ) )
                     {
                        alt33 = 1;
                     }
                     switch ( alt33 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:423:17:
                           // ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | (
                           // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) )
                        {
                           
                           sb.append( " " );
                           lang.add( sb, "on_the" );
                           sb.append( " " );
                           
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:426:17:
                           // ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | (
                           // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) )
                           int alt32 = 2;
                           int LA32_0 = input.LA( 1 );
                           
                           if ( ( LA32_0 == OP_BYMONTHDAY ) )
                           {
                              alt32 = 1;
                           }
                           else if ( ( LA32_0 == OP_BYDAY ) )
                           {
                              alt32 = 2;
                           }
                           else
                           {
                              NoViableAltException nvae = new NoViableAltException( "",
                                                                                    32,
                                                                                    0,
                                                                                    input );
                              
                              throw nvae;
                           }
                           switch ( alt32 )
                           {
                              case 1:
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:427:22:
                                 // ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* )
                              {
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:427:22:
                                 // ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* )
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:428:25:
                                 // OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )*
                                 {
                                    match( input,
                                           OP_BYMONTHDAY,
                                           FOLLOW_OP_BYMONTHDAY_in_parseRecurrencePattern2027 );
                                    pushFollow( FOLLOW_parse_PatternXst_in_parseRecurrencePattern2029 );
                                    parse_PatternXst( lang, sb );
                                    
                                    state._fsp--;
                                    
                                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:429:28:
                                    // ( COMMA parse_PatternXst[lang, sb] )*
                                    loop30: do
                                    {
                                       int alt30 = 2;
                                       int LA30_0 = input.LA( 1 );
                                       
                                       if ( ( LA30_0 == COMMA ) )
                                       {
                                          alt30 = 1;
                                       }
                                       
                                       switch ( alt30 )
                                       {
                                          case 1:
                                             // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:429:29:
                                             // COMMA parse_PatternXst[lang, sb]
                                          {
                                             match( input,
                                                    COMMA,
                                                    FOLLOW_COMMA_in_parseRecurrencePattern2060 );
                                             sb.append( ", " );
                                             pushFollow( FOLLOW_parse_PatternXst_in_parseRecurrencePattern2064 );
                                             parse_PatternXst( lang, sb );
                                             
                                             state._fsp--;
                                             
                                          }
                                             break;
                                          
                                          default :
                                             break loop30;
                                       }
                                    }
                                    while ( true );
                                    
                                 }
                                 
                              }
                                 break;
                              case 2:
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:431:22:
                                 // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang,
                                 // sb, false] )* )
                              {
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:431:22:
                                 // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang,
                                 // sb, false] )* )
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:432:25:
                                 // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                                 // false] )*
                                 {
                                    match( input,
                                           OP_BYDAY,
                                           FOLLOW_OP_BYDAY_in_parseRecurrencePattern2139 );
                                    pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2141 );
                                    parse_PatternWeekday( lang, sb, true );
                                    
                                    state._fsp--;
                                    
                                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:433:29:
                                    // ( COMMA parse_PatternWeekday[lang, sb, false] )*
                                    loop31: do
                                    {
                                       int alt31 = 2;
                                       int LA31_0 = input.LA( 1 );
                                       
                                       if ( ( LA31_0 == COMMA ) )
                                       {
                                          alt31 = 1;
                                       }
                                       
                                       switch ( alt31 )
                                       {
                                          case 1:
                                             // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:433:30:
                                             // COMMA parse_PatternWeekday[lang, sb, false]
                                          {
                                             match( input,
                                                    COMMA,
                                                    FOLLOW_COMMA_in_parseRecurrencePattern2173 );
                                             sb.append( ", " );
                                             pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2177 );
                                             parse_PatternWeekday( lang,
                                                                   sb,
                                                                   false );
                                             
                                             state._fsp--;
                                             
                                          }
                                             break;
                                          
                                          default :
                                             break loop31;
                                       }
                                    }
                                    while ( true );
                                    
                                 }
                                 
                              }
                                 break;
                              
                           }
                           
                        }
                           break;
                        
                     }
                     
                  }
                  
               }
                  break;
               case 3:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:438:11:
                  // ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? )
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:438:11:
                  // ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? )
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:439:14:
                  // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )?
                  {
                     match( input,
                            VAL_WEEKLY,
                            FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern2276 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern2278 );
                     parse_PatternInterval( lang, sb, "week", every );
                     
                     state._fsp--;
                     
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:440:14:
                     // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )*
                     // )?
                     int alt35 = 2;
                     int LA35_0 = input.LA( 1 );
                     
                     if ( ( LA35_0 == OP_BYDAY ) )
                     {
                        alt35 = 1;
                     }
                     switch ( alt35 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:441:17:
                           // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )*
                        {
                           
                           sb.append( " " );
                           lang.add( sb, "on_the" );
                           sb.append( " " );
                           
                           match( input,
                                  OP_BYDAY,
                                  FOLLOW_OP_BYDAY_in_parseRecurrencePattern2330 );
                           pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2332 );
                           parse_PatternWeekday( lang, sb, true );
                           
                           state._fsp--;
                           
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:445:20:
                           // ( COMMA parse_PatternWeekday[lang, sb, false] )*
                           loop34: do
                           {
                              int alt34 = 2;
                              int LA34_0 = input.LA( 1 );
                              
                              if ( ( LA34_0 == COMMA ) )
                              {
                                 alt34 = 1;
                              }
                              
                              switch ( alt34 )
                              {
                                 case 1:
                                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:445:21:
                                    // COMMA parse_PatternWeekday[lang, sb, false]
                                 {
                                    match( input,
                                           COMMA,
                                           FOLLOW_COMMA_in_parseRecurrencePattern2355 );
                                    sb.append( ", " );
                                    pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2359 );
                                    parse_PatternWeekday( lang, sb, false );
                                    
                                    state._fsp--;
                                    
                                 }
                                    break;
                                 
                                 default :
                                    break loop34;
                              }
                           }
                           while ( true );
                           
                        }
                           break;
                        
                     }
                     
                  }
                  
               }
                  break;
               case 4:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:448:11:
                  // ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] )
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:448:11:
                  // ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] )
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:449:14:
                  // VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every]
                  {
                     match( input,
                            VAL_DAILY,
                            FOLLOW_VAL_DAILY_in_parseRecurrencePattern2417 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern2419 );
                     parse_PatternInterval( lang, sb, "day", every );
                     
                     state._fsp--;
                     
                  }
                  
               }
                  break;
               
            }
            
         }
         
         sentence = sb.toString();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      finally
      {
      }
      return sentence;
   }
   


   // $ANTLR end "parseRecurrencePattern"
   
   // $ANTLR start "parse_PatternInterval"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:458:1:
   // parse_PatternInterval[RecurrPatternLanguage lang,\r\n StringBuilder sb,\r\n String unit,\r\n boolean isEvery] :
   // OP_INTERVAL interval= INT ;
   public final void parse_PatternInterval( RecurrPatternLanguage lang,
                                            StringBuilder sb,
                                            String unit,
                                            boolean isEvery ) throws RecognitionException
   {
      Token interval = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:462:4: (
         // OP_INTERVAL interval= INT )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:462:6:
         // OP_INTERVAL interval= INT
         {
            match( input,
                   OP_INTERVAL,
                   FOLLOW_OP_INTERVAL_in_parse_PatternInterval2471 );
            interval = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_parse_PatternInterval2475 );
            
            if ( isEvery )
               lang.addEvery( sb, unit, ( interval != null ? interval.getText()
                                                          : null ) );
            else
               lang.addAfter( sb, unit, ( interval != null ? interval.getText()
                                                          : null ) );
            
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
   


   // $ANTLR end "parse_PatternInterval"
   
   // $ANTLR start "parse_PatternXst"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:475:1:
   // parse_PatternXst[RecurrPatternLanguage lang, StringBuilder sb] : x= INT ;
   public final void parse_PatternXst( RecurrPatternLanguage lang,
                                       StringBuilder sb ) throws RecognitionException
   {
      Token x = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:476:4: (x= INT
         // )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:476:6: x= INT
         {
            x = (Token) match( input, INT, FOLLOW_INT_in_parse_PatternXst2516 );
            
            if ( sb != null )
            {
               final int xSt = Integer.parseInt( ( x != null ? x.getText()
                                                            : null ) );
               
               if ( xSt < 0 )
               {
                  if ( xSt < -1 )
                  {
                     lang.addStToX( sb, xSt * -1 );
                     sb.append( " " );
                  }
                  
                  lang.add( sb, "last" );
               }
               else
                  lang.addStToX( sb, xSt );
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
   


   // $ANTLR end "parse_PatternXst"
   
   // $ANTLR start "parse_PatternWeekday"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:502:1:
   // parse_PatternWeekday[RecurrPatternLanguage lang,\r\n StringBuilder sb,\r\n boolean respectXst] : ( (
   // parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY |
   // SATURDAY | SUNDAY ) ) ) ;
   public final void parse_PatternWeekday( RecurrPatternLanguage lang,
                                           StringBuilder sb,
                                           boolean respectXst ) throws RecognitionException
   {
      Token wd = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:505:4: ( ( (
         // parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY |
         // SATURDAY | SUNDAY ) ) ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:505:6: ( (
         // parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY |
         // SATURDAY | SUNDAY ) ) )
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:505:6: ( (
            // parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY
            // | SATURDAY | SUNDAY ) ) )
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:506:9: (
            // parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY
            // | SATURDAY | SUNDAY ) )
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:506:9: (
               // parse_PatternXst[lang, respectXst ? sb : null] )?
               int alt37 = 2;
               int LA37_0 = input.LA( 1 );
               
               if ( ( LA37_0 == INT ) )
               {
                  alt37 = 1;
               }
               switch ( alt37 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:507:12:
                     // parse_PatternXst[lang, respectXst ? sb : null]
                  {
                     pushFollow( FOLLOW_parse_PatternXst_in_parse_PatternWeekday2578 );
                     parse_PatternXst( lang, respectXst ? sb : null );
                     
                     state._fsp--;
                     
                     if ( respectXst )
                        sb.append( " " );
                     
                  }
                     break;
                  
               }
               
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:513:9:
               // (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) )
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:514:12:
               // wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY )
               {
                  wd = (Token) input.LT( 1 );
                  if ( ( input.LA( 1 ) >= MONDAY && input.LA( 1 ) <= SUNDAY ) )
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
                  
                  lang.add( sb, ( wd != null ? wd.getText() : null ) );
                  
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
   


   // $ANTLR end "parse_PatternWeekday"
   
   // $ANTLR start "parse_PatternMonth"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:532:1:
   // parse_PatternMonth[RecurrPatternLanguage lang, StringBuilder sb] : m= INT ;
   public final void parse_PatternMonth( RecurrPatternLanguage lang,
                                         StringBuilder sb ) throws RecognitionException
   {
      Token m = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:533:4: (m= INT
         // )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:533:6: m= INT
         {
            m = (Token) match( input, INT, FOLLOW_INT_in_parse_PatternMonth2822 );
            
            lang.add( sb, "m" + ( m != null ? m.getText() : null ) );
            
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
   
   // $ANTLR end "parse_PatternMonth"
   
   // Delegated rules
   
   protected DFA16 dfa16 = new DFA16( this );
   
   static final String DFA16_eotS = "\24\uffff";
   
   static final String DFA16_eofS = "\1\uffff\1\13\1\uffff\5\13\1\uffff\1\13\3\uffff\7\13";
   
   static final String DFA16_minS = "\2\6\1\uffff\5\17\1\uffff\1\17\1\22\2\uffff\7\17";
   
   static final String DFA16_maxS = "\2\55\1\uffff\5\55\1\uffff\1\55\1\32\2\uffff\7\55";
   
   static final String DFA16_acceptS = "\2\uffff\1\1\5\uffff\1\3\2\uffff\1\2\1\4\7\uffff";
   
   static final String DFA16_specialS = "\24\uffff}>";
   
   static final String[] DFA16_transitionS =
   {
    "\3\2\2\uffff\2\2\5\uffff\1\1\2\uffff\1\3\2\4\1\5\1\6\1\7\12" + "\2\11\10",
    "\3\2\2\uffff\2\2\2\uffff\2\12\2\uffff\2\11\20\uffff\11\14", "",
    "\2\12\24\uffff\11\14", "\2\12\24\uffff\11\14", "\2\12\24\uffff\11\14",
    "\2\12\24\uffff\11\14", "\2\12\24\uffff\11\14", "", "\2\12\24\uffff\11\14",
    "\1\15\2\uffff\1\16\2\17\1\20\1\21\1\22", "", "",
    "\2\12\2\uffff\2\23\20\uffff\11\14", "\2\12\24\uffff\11\14",
    "\2\12\24\uffff\11\14", "\2\12\24\uffff\11\14", "\2\12\24\uffff\11\14",
    "\2\12\24\uffff\11\14", "\2\12\24\uffff\11\14" };
   
   static final short[] DFA16_eot = DFA.unpackEncodedString( DFA16_eotS );
   
   static final short[] DFA16_eof = DFA.unpackEncodedString( DFA16_eofS );
   
   static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars( DFA16_minS );
   
   static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars( DFA16_maxS );
   
   static final short[] DFA16_accept = DFA.unpackEncodedString( DFA16_acceptS );
   
   static final short[] DFA16_special = DFA.unpackEncodedString( DFA16_specialS );
   
   static final short[][] DFA16_transition;
   
   static
   {
      int numStates = DFA16_transitionS.length;
      DFA16_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA16_transition[ i ] = DFA.unpackEncodedString( DFA16_transitionS[ i ] );
      }
   }
   
   
   class DFA16 extends DFA
   {
      
      public DFA16( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 16;
         this.eot = DFA16_eot;
         this.eof = DFA16_eof;
         this.min = DFA16_min;
         this.max = DFA16_max;
         this.accept = DFA16_accept;
         this.special = DFA16_special;
         this.transition = DFA16_transition;
      }
      


      public String getDescription()
      {
         return "162:4: ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )";
      }
   }
   
   public static final BitSet FOLLOW_EVERY_in_parseRecurrence68 = new BitSet( new long[]
   { 0x00003FFFFFE419C0L } );
   
   public static final BitSet FOLLOW_AFTER_in_parseRecurrence74 = new BitSet( new long[]
   { 0x00003FFFFFE419C0L } );
   
   public static final BitSet FOLLOW_parse_Number_in_parseRecurrence84 = new BitSet( new long[]
   { 0x00000000000019C0L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseRecurrence100 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseRecurrence173 = new BitSet( new long[]
   { 0x00003FE000000602L } );
   
   public static final BitSet FOLLOW_BIWEEKLY_in_parseRecurrence188 = new BitSet( new long[]
   { 0x00003FE000000602L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence276 = new BitSet( new long[]
   { 0x00003FE000000400L } );
   
   public static final BitSet FOLLOW_THE_in_parseRecurrence279 = new BitSet( new long[]
   { 0x00003FE000000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence282 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseRecurrence322 = new BitSet( new long[]
   { 0x0000000007E40602L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence380 = new BitSet( new long[]
   { 0x0000000007E40600L } );
   
   public static final BitSet FOLLOW_THE_in_parseRecurrence383 = new BitSet( new long[]
   { 0x0000000007E40600L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence388 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseRecurrence432 = new BitSet( new long[]
   { 0x0000000007E40602L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence491 = new BitSet( new long[]
   { 0x0000000007E40600L } );
   
   public static final BitSet FOLLOW_THE_in_parseRecurrence494 = new BitSet( new long[]
   { 0x0000000007E40600L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence499 = new BitSet( new long[]
   { 0x0000400000006002L } );
   
   public static final BitSet FOLLOW_set_in_parseRecurrence583 = new BitSet( new long[]
   { 0x0000400000006000L } );
   
   public static final BitSet FOLLOW_parse_Month_in_parseRecurrence631 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence731 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence746 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence773 = new BitSet( new long[]
   { 0x00003FE000000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence776 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_EOF_in_parseRecurrence806 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst841 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_set_in_recurr_Xst864 = new BitSet( new long[]
   { 0x0000000007E40000L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst874 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD909 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_set_in_recurr_WD931 = new BitSet( new long[]
   { 0x00003FE000000000L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD939 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_recurr_Monthly1004 = new BitSet( new long[]
   { 0x00003FE000020002L } );
   
   public static final BitSet FOLLOW_LAST_in_recurr_Monthly1041 = new BitSet( new long[]
   { 0x00003FE000000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_recurr_Monthly1081 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Xst1137 = new BitSet( new long[]
   { 0x0000000000180002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Xst1139 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIRST_in_parse_Xst1156 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Xst1176 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THIRD_in_parse_Xst1191 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOURTH_in_parse_Xst1211 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIFTH_in_parse_Xst1230 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Number1288 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_ONE_in_parse_Number1302 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_TWO_in_parse_Number1314 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_THREE_in_parse_Number1326 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_FOUR_in_parse_Number1336 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_FIVE_in_parse_Number1347 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_SIX_in_parse_Number1358 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_SEVEN_in_parse_Number1370 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_EIGHT_in_parse_Number1380 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_NINE_in_parse_Number1390 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_TEN_in_parse_Number1401 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONDAY_in_parse_Weekday1451 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TUESDAY_in_parse_Weekday1465 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEDNESDAY_in_parse_Weekday1478 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THURSDAY_in_parse_Weekday1489 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FRIDAY_in_parse_Weekday1501 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SATURDAY_in_parse_Weekday1515 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SUNDAY_in_parse_Weekday1527 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKEND_in_parse_Weekday1541 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKDAY_LIT_in_parse_Weekday1554 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_parse_Month1589 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_FREQ_in_parseRecurrencePattern1649 = new BitSet( new long[]
   { 0x0069000000000000L } );
   
   public static final BitSet FOLLOW_VAL_YEARLY_in_parseRecurrencePattern1684 = new BitSet( new long[]
   { 0x0080000000000000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern1686 = new BitSet( new long[]
   { 0x0002000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern1760 = new BitSet( new long[]
   { 0x00000FE000040000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern1762 = new BitSet( new long[]
   { 0x0004000000010000L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern1788 = new BitSet( new long[]
   { 0x00000FE000040000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern1792 = new BitSet( new long[]
   { 0x0004000000010000L } );
   
   public static final BitSet FOLLOW_OP_BYMONTH_in_parseRecurrencePattern1838 = new BitSet( new long[]
   { 0x0000000000040000L } );
   
   public static final BitSet FOLLOW_parse_PatternMonth_in_parseRecurrencePattern1840 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_VAL_MONTHLY_in_parseRecurrencePattern1924 = new BitSet( new long[]
   { 0x0080000000000000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern1926 = new BitSet( new long[]
   { 0x0012000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYMONTHDAY_in_parseRecurrencePattern2027 = new BitSet( new long[]
   { 0x0000000000040000L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parseRecurrencePattern2029 = new BitSet( new long[]
   { 0x0000000000010002L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern2060 = new BitSet( new long[]
   { 0x0000000000040000L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parseRecurrencePattern2064 = new BitSet( new long[]
   { 0x0000000000010002L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern2139 = new BitSet( new long[]
   { 0x00000FE000040000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2141 = new BitSet( new long[]
   { 0x0000000000010002L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern2173 = new BitSet( new long[]
   { 0x00000FE000040000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2177 = new BitSet( new long[]
   { 0x0000000000010002L } );
   
   public static final BitSet FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern2276 = new BitSet( new long[]
   { 0x0080000000000000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern2278 = new BitSet( new long[]
   { 0x0002000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern2330 = new BitSet( new long[]
   { 0x00000FE000040000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2332 = new BitSet( new long[]
   { 0x0000000000010002L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern2355 = new BitSet( new long[]
   { 0x00000FE000040000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2359 = new BitSet( new long[]
   { 0x0000000000010002L } );
   
   public static final BitSet FOLLOW_VAL_DAILY_in_parseRecurrencePattern2417 = new BitSet( new long[]
   { 0x0080000000000000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern2419 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_INTERVAL_in_parse_PatternInterval2471 = new BitSet( new long[]
   { 0x0000000000040000L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternInterval2475 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternXst2516 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parse_PatternWeekday2578 = new BitSet( new long[]
   { 0x00000FE000000000L } );
   
   public static final BitSet FOLLOW_set_in_parse_PatternWeekday2628 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternMonth2822 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
