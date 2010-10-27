// $ANTLR 3.2 Sep 23, 2009 12:02:23 F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g 2010-10-27 09:40:56

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
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;


public class RecurrenceParser extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "EVERY", "AFTER", "DAYS", "WEEKS",
    "BIWEEKLY", "ON", "THE", "MONTHS", "YEARS", "IN", "OF", "UNTIL", "FOR",
    "INT", "AND", "COMMA", "LAST", "DOT", "ST_S", "FIRST", "SECOND", "OTHER",
    "THIRD", "FOURTH", "FIFTH", "NUM_ONE", "NUM_TWO", "NUM_THREE", "NUM_FOUR",
    "NUM_FIVE", "NUM_SIX", "NUM_SEVEN", "NUM_EIGHT", "NUM_NINE", "NUM_TEN",
    "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY",
    "SUNDAY", "WEEKEND", "WEEKDAY_LIT", "MONTH", "OP_FREQ", "VAL_YEARLY",
    "OP_BYDAY", "OP_BYMONTH", "VAL_MONTHLY", "OP_BYMONTHDAY", "VAL_WEEKLY",
    "VAL_DAILY", "OP_UNTIL", "VAL_DATE", "OP_COUNT", "OP_INTERVAL", "STRING",
    "TIMES", "SEMICOLON", "EQUALS", "MINUS", "NUMBER", "WS" };
   
   public static final int THIRD = 26;
   
   public static final int NUM_TWO = 30;
   
   public static final int NUM_NINE = 37;
   
   public static final int VAL_WEEKLY = 55;
   
   public static final int WEDNESDAY = 41;
   
   public static final int THE = 10;
   
   public static final int OP_FREQ = 49;
   
   public static final int FOR = 16;
   
   public static final int EQUALS = 64;
   
   public static final int OP_BYMONTHDAY = 54;
   
   public static final int NUM_SIX = 34;
   
   public static final int AND = 18;
   
   public static final int OP_BYDAY = 51;
   
   public static final int EOF = -1;
   
   public static final int MONTH = 48;
   
   public static final int FRIDAY = 43;
   
   public static final int IN = 13;
   
   public static final int NUM_THREE = 31;
   
   public static final int COMMA = 19;
   
   public static final int NUM_ONE = 29;
   
   public static final int VAL_DATE = 58;
   
   public static final int OP_COUNT = 59;
   
   public static final int LAST = 20;
   
   public static final int DOT = 21;
   
   public static final int ST_S = 22;
   
   public static final int VAL_DAILY = 56;
   
   public static final int NUM_EIGHT = 36;
   
   public static final int FOURTH = 27;
   
   public static final int BIWEEKLY = 8;
   
   public static final int SECOND = 24;
   
   public static final int OTHER = 25;
   
   public static final int NUM_FOUR = 32;
   
   public static final int SATURDAY = 44;
   
   public static final int NUMBER = 66;
   
   public static final int NUM_SEVEN = 35;
   
   public static final int EVERY = 4;
   
   public static final int WEEKEND = 46;
   
   public static final int ON = 9;
   
   public static final int MONDAY = 39;
   
   public static final int SUNDAY = 45;
   
   public static final int SEMICOLON = 63;
   
   public static final int INT = 17;
   
   public static final int MINUS = 65;
   
   public static final int AFTER = 5;
   
   public static final int OF = 14;
   
   public static final int YEARS = 12;
   
   public static final int VAL_YEARLY = 50;
   
   public static final int NUM_FIVE = 33;
   
   public static final int FIFTH = 28;
   
   public static final int DAYS = 6;
   
   public static final int NUM_TEN = 38;
   
   public static final int WS = 67;
   
   public static final int WEEKS = 7;
   
   public static final int OP_UNTIL = 57;
   
   public static final int THURSDAY = 42;
   
   public static final int OP_INTERVAL = 60;
   
   public static final int UNTIL = 15;
   
   public static final int MONTHS = 11;
   
   public static final int WEEKDAY_LIT = 47;
   
   public static final int OP_BYMONTH = 52;
   
   public static final int VAL_MONTHLY = 53;
   
   public static final int TIMES = 62;
   
   public static final int TUESDAY = 40;
   
   public static final int STRING = 61;
   
   public static final int FIRST = 23;
   
   

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
   
   public final static String DATE_PATTERN = "yyyyMMdd'T'HHmmss";
   
   

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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:141:1:
   // parseRecurrence returns [HashMap< String, Object > res] : ( ( EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS
   // | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r=
   // recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN |
   // OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints]
   // recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )? | EOF );
   public final HashMap< String, Object > parseRecurrence() throws RecognitionException
   {
      HashMap< String, Object > res = null;
      
      Token until = null;
      Token count = null;
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
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:165:4: ( (
         // EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )?
         // recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON
         // )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) |
         // recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )
         // (until= UNTIL | FOR count= INT )? | EOF )
         int alt18 = 2;
         int LA18_0 = input.LA( 1 );
         
         if ( ( ( LA18_0 >= EVERY && LA18_0 <= BIWEEKLY )
            || ( LA18_0 >= MONTHS && LA18_0 <= YEARS ) || LA18_0 == INT || ( LA18_0 >= FIRST && LA18_0 <= WEEKDAY_LIT ) ) )
         {
            alt18 = 1;
         }
         else if ( ( LA18_0 == EOF ) )
         {
            alt18 = 2;
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  18,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt18 )
         {
            case 1:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:165:6: (
               // EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )?
               // recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS
               // ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) |
               // recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"]
               // ) ) (until= UNTIL | FOR count= INT )?
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:165:6: (
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
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:165:7:
                     // EVERY
                  {
                     match( input, EVERY, FOLLOW_EVERY_in_parseRecurrence68 );
                     isEvery = Boolean.TRUE;
                     
                  }
                     break;
                  case 2:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:165:43:
                     // AFTER
                  {
                     match( input, AFTER, FOLLOW_AFTER_in_parseRecurrence74 );
                     
                  }
                     break;
                  
               }
               
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:166:6: (
               // (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"]
               // )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r=
               // recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] |
               // recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )
               int alt16 = 4;
               alt16 = dfa16.predict( input );
               switch ( alt16 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:167:9:
                     // (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays,
                     // \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? (
                     // THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? )
                  {
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:167:17:
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
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:167:17:
                           // interval= parse_Number
                        {
                           pushFollow( FOLLOW_parse_Number_in_parseRecurrence95 );
                           interval = parse_Number();
                           
                           state._fsp--;
                           
                        }
                           break;
                        
                     }
                     
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:168:5:
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
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:169:9:
                           // DAYS
                        {
                           match( input,
                                  DAYS,
                                  FOLLOW_DAYS_in_parseRecurrence112 );
                           freq = VAL_DAILY_LIT;
                           
                        }
                           break;
                        case 2:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:170:9:
                           // ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )?
                        {
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:170:9:
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
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:171:14:
                                 // WEEKS
                              {
                                 match( input,
                                        WEEKS,
                                        FOLLOW_WEEKS_in_parseRecurrence185 );
                                 
                              }
                                 break;
                              case 2:
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:172:14:
                                 // BIWEEKLY
                              {
                                 match( input,
                                        BIWEEKLY,
                                        FOLLOW_BIWEEKLY_in_parseRecurrence200 );
                                 
                                 interval = 2;
                                 
                              }
                                 break;
                              
                           }
                           
                           freq = VAL_WEEKLY_LIT;
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:177:10:
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
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:177:11:
                                 // ( ON )? ( THE )? recurr_WD[weekdays, \"\"]
                              {
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:177:11:
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
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:177:11:
                                       // ON
                                    {
                                       match( input,
                                              ON,
                                              FOLLOW_ON_in_parseRecurrence288 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:177:15:
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
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:177:15:
                                       // THE
                                    {
                                       match( input,
                                              THE,
                                              FOLLOW_THE_in_parseRecurrence291 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 pushFollow( FOLLOW_recurr_WD_in_parseRecurrence294 );
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
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:183:9:
                           // MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )?
                        {
                           match( input,
                                  MONTHS,
                                  FOLLOW_MONTHS_in_parseRecurrence334 );
                           freq = VAL_MONTHLY_LIT;
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:184:10:
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
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:184:11:
                                 // ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints]
                              {
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:184:11:
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
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:184:11:
                                       // ON
                                    {
                                       match( input,
                                              ON,
                                              FOLLOW_ON_in_parseRecurrence392 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:184:15:
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
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:184:15:
                                       // THE
                                    {
                                       match( input,
                                              THE,
                                              FOLLOW_THE_in_parseRecurrence395 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 pushFollow( FOLLOW_recurr_Monthly_in_parseRecurrence400 );
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
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:192:9:
                           // YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m=
                           // parse_Month )? )?
                        {
                           match( input,
                                  YEARS,
                                  FOLLOW_YEARS_in_parseRecurrence444 );
                           freq = VAL_YEARLY_LIT;
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:193:10:
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
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:193:11:
                                 // ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m=
                                 // parse_Month )?
                              {
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:193:11:
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
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:193:11:
                                       // ON
                                    {
                                       match( input,
                                              ON,
                                              FOLLOW_ON_in_parseRecurrence503 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:193:15:
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
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:193:15:
                                       // THE
                                    {
                                       match( input,
                                              THE,
                                              FOLLOW_THE_in_parseRecurrence506 );
                                       
                                    }
                                       break;
                                    
                                 }
                                 
                                 pushFollow( FOLLOW_recurr_Monthly_in_parseRecurrence511 );
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
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:200:35:
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
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:201:37:
                                       // ( IN | OF )? m= parse_Month
                                    {
                                       // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:201:37:
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
                                       
                                       pushFollow( FOLLOW_parse_Month_in_parseRecurrence643 );
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
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:211:6:
                     // recurr_Xst[ints]
                  {
                     pushFollow( FOLLOW_recurr_Xst_in_parseRecurrence741 );
                     recurr_Xst( ints );
                     
                     state._fsp--;
                     
                     freq = VAL_MONTHLY_LIT;
                     interval = 1;
                     resolution = OP_BYMONTHDAY_LIT;
                     resolutionVal = join( ",", ints );
                     
                  }
                     break;
                  case 3:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:218:6:
                     // recurr_WD[weekdays, \"\"]
                  {
                     pushFollow( FOLLOW_recurr_WD_in_parseRecurrence756 );
                     recurr_WD( weekdays, "" );
                     
                     state._fsp--;
                     
                     freq = VAL_WEEKLY_LIT;
                     interval = 1;
                     resolution = OP_BYDAY_LIT;
                     resolutionVal = join( ",", weekdays );
                     
                  }
                     break;
                  case 4:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:225:6:
                     // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                  {
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:225:6:
                     // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:226:9:
                     // firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"]
                     {
                        pushFollow( FOLLOW_recurr_Xst_in_parseRecurrence783 );
                        firstEntry = recurr_Xst( ints );
                        
                        state._fsp--;
                        
                        pushFollow( FOLLOW_recurr_WD_in_parseRecurrence786 );
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
               
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:235:4:
               // (until= UNTIL | FOR count= INT )?
               int alt17 = 3;
               int LA17_0 = input.LA( 1 );
               
               if ( ( LA17_0 == UNTIL ) )
               {
                  alt17 = 1;
               }
               else if ( ( LA17_0 == FOR ) )
               {
                  alt17 = 2;
               }
               switch ( alt17 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:236:9:
                     // until= UNTIL
                  {
                     until = (Token) match( input,
                                            UNTIL,
                                            FOLLOW_UNTIL_in_parseRecurrence826 );
                     
                     final String dateTimeString = until.getText()
                                                        .toUpperCase()
                                                        .replaceFirst( OP_UNTIL_LIT
                                                                          + "\\s*",
                                                                       "" );
                     
                     final Calendar untilDate = RtmDateTimeParsing.parseDateTimeSpec( dateTimeString );
                     
                     if ( untilDate != null )
                     {
                        if ( !untilDate.isSet( Calendar.HOUR_OF_DAY ) )
                        {
                           untilDate.set( Calendar.HOUR, 0 );
                           untilDate.set( Calendar.HOUR_OF_DAY, 0 );
                           untilDate.set( Calendar.MINUTE, 0 );
                           untilDate.set( Calendar.SECOND, 0 );
                           untilDate.set( Calendar.MILLISECOND, 0 );
                        }
                        
                        final SimpleDateFormat sdf = new SimpleDateFormat( DATE_PATTERN );
                        res.put( OP_UNTIL_LIT, sdf.format( untilDate.getTime() ) );
                     }
                     
                  }
                     break;
                  case 2:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:260:9:
                     // FOR count= INT
                  {
                     match( input, FOR, FOLLOW_FOR_in_parseRecurrence846 );
                     count = (Token) match( input,
                                            INT,
                                            FOLLOW_INT_in_parseRecurrence850 );
                     
                     res.put( OP_COUNT_LIT,
                              Integer.parseInt( ( count != null
                                                               ? count.getText()
                                                               : null ) ) );
                     
                  }
                     break;
                  
               }
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:265:6:
               // EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseRecurrence873 );
               
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
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return res;
   }
   


   // $ANTLR end "parseRecurrence"
   
   // $ANTLR start "recurr_Xst"
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:276:1:
   // recurr_Xst[Set< Integer > res] returns [int firstEntry] : x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? ;
   public final int recurr_Xst( Set< Integer > res ) throws RecognitionException
   {
      int firstEntry = 0;
      
      int x = 0;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:277:4: (x=
         // parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:277:6: x=
         // parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )?
         {
            pushFollow( FOLLOW_parse_Xst_in_recurr_Xst920 );
            x = parse_Xst();
            
            state._fsp--;
            
            res.add( x );
            firstEntry = x;
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:278:4: ( ( (
            // AND | COMMA ) x= parse_Xst )+ )?
            int alt20 = 2;
            int LA20_0 = input.LA( 1 );
            
            if ( ( ( LA20_0 >= AND && LA20_0 <= COMMA ) ) )
            {
               alt20 = 1;
            }
            switch ( alt20 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:278:5:
                  // ( ( AND | COMMA ) x= parse_Xst )+
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:278:5:
                  // ( ( AND | COMMA ) x= parse_Xst )+
                  int cnt19 = 0;
                  loop19: do
                  {
                     int alt19 = 2;
                     int LA19_0 = input.LA( 1 );
                     
                     if ( ( ( LA19_0 >= AND && LA19_0 <= COMMA ) ) )
                     {
                        alt19 = 1;
                     }
                     
                     switch ( alt19 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:278:6:
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
                           
                           pushFollow( FOLLOW_parse_Xst_in_recurr_Xst953 );
                           x = parse_Xst();
                           
                           state._fsp--;
                           
                           res.add( x );
                           
                        }
                           break;
                        
                        default :
                           if ( cnt19 >= 1 )
                              break loop19;
                           EarlyExitException eee = new EarlyExitException( 19,
                                                                            input );
                           throw eee;
                     }
                     cnt19++;
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:285:1: recurr_WD[Set<
   // String > weekdays, String Xst] : parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays,
   // Xst, true] )+ )? ;
   public final void recurr_WD( Set< String > weekdays, String Xst ) throws RecognitionException
   {
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:286:4: (
         // parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:286:6:
         // parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
         {
            pushFollow( FOLLOW_parse_Weekday_in_recurr_WD988 );
            parse_Weekday( weekdays, Xst, true );
            
            state._fsp--;
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:287:4: ( ( (
            // AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
            int alt22 = 2;
            int LA22_0 = input.LA( 1 );
            
            if ( ( ( LA22_0 >= AND && LA22_0 <= COMMA ) ) )
            {
               alt22 = 1;
            }
            switch ( alt22 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:287:5:
                  // ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:287:5:
                  // ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
                  int cnt21 = 0;
                  loop21: do
                  {
                     int alt21 = 2;
                     int LA21_0 = input.LA( 1 );
                     
                     if ( ( ( LA21_0 >= AND && LA21_0 <= COMMA ) ) )
                     {
                        alt21 = 1;
                     }
                     
                     switch ( alt21 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:287:6:
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
                           
                           pushFollow( FOLLOW_parse_Weekday_in_recurr_WD1018 );
                           parse_Weekday( weekdays, Xst, true );
                           
                           state._fsp--;
                           
                        }
                           break;
                        
                        default :
                           if ( cnt21 >= 1 )
                              break loop21;
                           EarlyExitException eee = new EarlyExitException( 21,
                                                                            input );
                           throw eee;
                     }
                     cnt21++;
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:294:1:
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
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:305:4:
         // (firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:305:6:
         // firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
         {
            pushFollow( FOLLOW_recurr_Xst_in_recurr_Monthly1071 );
            firstEntry = recurr_Xst( ints );
            
            state._fsp--;
            
            retval.resolution = OP_BYMONTHDAY_LIT;
            retval.resolutionVal = join( ",", ints );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:309:8: ( (
            // LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            int alt24 = 2;
            int LA24_0 = input.LA( 1 );
            
            if ( ( LA24_0 == LAST || ( LA24_0 >= MONDAY && LA24_0 <= WEEKDAY_LIT ) ) )
            {
               alt24 = 1;
            }
            switch ( alt24 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:310:11:
                  // ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )]
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:310:11:
                  // ( LAST )?
                  int alt23 = 2;
                  int LA23_0 = input.LA( 1 );
                  
                  if ( ( LA23_0 == LAST ) )
                  {
                     alt23 = 1;
                  }
                  switch ( alt23 )
                  {
                     case 1:
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:311:14:
                        // LAST
                     {
                        match( input, LAST, FOLLOW_LAST_in_recurr_Monthly1108 );
                        
                        firstEntry = -firstEntry;
                        
                     }
                        break;
                     
                  }
                  
                  pushFollow( FOLLOW_recurr_WD_in_recurr_Monthly1148 );
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:329:1: parse_Xst
   // returns [int number] : (n= INT ( DOT | ST_S )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH );
   public final int parse_Xst() throws RecognitionException
   {
      int number = 0;
      
      Token n = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:330:4: (n= INT
         // ( DOT | ST_S )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH )
         int alt26 = 6;
         switch ( input.LA( 1 ) )
         {
            case INT:
            {
               alt26 = 1;
            }
               break;
            case FIRST:
            {
               alt26 = 2;
            }
               break;
            case SECOND:
            case OTHER:
            {
               alt26 = 3;
            }
               break;
            case THIRD:
            {
               alt26 = 4;
            }
               break;
            case FOURTH:
            {
               alt26 = 5;
            }
               break;
            case FIFTH:
            {
               alt26 = 6;
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:330:6: n=
               // INT ( DOT | ST_S )?
            {
               n = (Token) match( input, INT, FOLLOW_INT_in_parse_Xst1204 );
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:330:12: (
               // DOT | ST_S )?
               int alt25 = 2;
               int LA25_0 = input.LA( 1 );
               
               if ( ( ( LA25_0 >= DOT && LA25_0 <= ST_S ) ) )
               {
                  alt25 = 1;
               }
               switch ( alt25 )
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:339:6:
               // FIRST
            {
               match( input, FIRST, FOLLOW_FIRST_in_parse_Xst1223 );
               number = 1;
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:340:6: (
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:341:6:
               // THIRD
            {
               match( input, THIRD, FOLLOW_THIRD_in_parse_Xst1258 );
               number = 3;
               
            }
               break;
            case 5:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:342:6:
               // FOURTH
            {
               match( input, FOURTH, FOLLOW_FOURTH_in_parse_Xst1278 );
               number = 4;
               
            }
               break;
            case 6:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:343:6:
               // FIFTH
            {
               match( input, FIFTH, FOLLOW_FIFTH_in_parse_Xst1297 );
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:354:1: parse_Number
   // returns [int number] : (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN |
   // NUM_EIGHT | NUM_NINE | NUM_TEN );
   public final int parse_Number() throws RecognitionException
   {
      int number = 0;
      
      Token n = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:355:4: (n= INT
         // | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN
         // )
         int alt27 = 11;
         switch ( input.LA( 1 ) )
         {
            case INT:
            {
               alt27 = 1;
            }
               break;
            case NUM_ONE:
            {
               alt27 = 2;
            }
               break;
            case NUM_TWO:
            {
               alt27 = 3;
            }
               break;
            case NUM_THREE:
            {
               alt27 = 4;
            }
               break;
            case NUM_FOUR:
            {
               alt27 = 5;
            }
               break;
            case NUM_FIVE:
            {
               alt27 = 6;
            }
               break;
            case NUM_SIX:
            {
               alt27 = 7;
            }
               break;
            case NUM_SEVEN:
            {
               alt27 = 8;
            }
               break;
            case NUM_EIGHT:
            {
               alt27 = 9;
            }
               break;
            case NUM_NINE:
            {
               alt27 = 10;
            }
               break;
            case NUM_TEN:
            {
               alt27 = 11;
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
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:355:6: n=
               // INT
            {
               n = (Token) match( input, INT, FOLLOW_INT_in_parse_Number1355 );
               number = Integer.parseInt( ( n != null ? n.getText() : null ) );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:356:6:
               // NUM_ONE
            {
               match( input, NUM_ONE, FOLLOW_NUM_ONE_in_parse_Number1369 );
               number = 1;
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:357:6:
               // NUM_TWO
            {
               match( input, NUM_TWO, FOLLOW_NUM_TWO_in_parse_Number1381 );
               number = 2;
               
            }
               break;
            case 4:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:358:6:
               // NUM_THREE
            {
               match( input, NUM_THREE, FOLLOW_NUM_THREE_in_parse_Number1393 );
               number = 3;
               
            }
               break;
            case 5:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:359:6:
               // NUM_FOUR
            {
               match( input, NUM_FOUR, FOLLOW_NUM_FOUR_in_parse_Number1403 );
               number = 4;
               
            }
               break;
            case 6:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:360:6:
               // NUM_FIVE
            {
               match( input, NUM_FIVE, FOLLOW_NUM_FIVE_in_parse_Number1414 );
               number = 5;
               
            }
               break;
            case 7:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:361:6:
               // NUM_SIX
            {
               match( input, NUM_SIX, FOLLOW_NUM_SIX_in_parse_Number1425 );
               number = 6;
               
            }
               break;
            case 8:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:362:6:
               // NUM_SEVEN
            {
               match( input, NUM_SEVEN, FOLLOW_NUM_SEVEN_in_parse_Number1437 );
               number = 7;
               
            }
               break;
            case 9:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:363:6:
               // NUM_EIGHT
            {
               match( input, NUM_EIGHT, FOLLOW_NUM_EIGHT_in_parse_Number1447 );
               number = 8;
               
            }
               break;
            case 10:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:364:6:
               // NUM_NINE
            {
               match( input, NUM_NINE, FOLLOW_NUM_NINE_in_parse_Number1457 );
               number = 9;
               
            }
               break;
            case 11:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:365:6:
               // NUM_TEN
            {
               match( input, NUM_TEN, FOLLOW_NUM_TEN_in_parse_Number1468 );
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:376:1:
   // parse_Weekday[Set< String > weekdays, String Xst, boolean strict] returns [String weekday] : ( MONDAY | TUESDAY |
   // WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT );
   public final String parse_Weekday( Set< String > weekdays,
                                      String Xst,
                                      boolean strict ) throws RecognitionException
   {
      String weekday = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:377:4: ( MONDAY
         // | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT )
         int alt28 = 9;
         switch ( input.LA( 1 ) )
         {
            case MONDAY:
            {
               alt28 = 1;
            }
               break;
            case TUESDAY:
            {
               alt28 = 2;
            }
               break;
            case WEDNESDAY:
            {
               alt28 = 3;
            }
               break;
            case THURSDAY:
            {
               alt28 = 4;
            }
               break;
            case FRIDAY:
            {
               alt28 = 5;
            }
               break;
            case SATURDAY:
            {
               alt28 = 6;
            }
               break;
            case SUNDAY:
            {
               alt28 = 7;
            }
               break;
            case WEEKEND:
            {
               alt28 = 8;
            }
               break;
            case WEEKDAY_LIT:
            {
               alt28 = 9;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     28,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt28 )
         {
            case 1:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:377:6:
               // MONDAY
            {
               match( input, MONDAY, FOLLOW_MONDAY_in_parse_Weekday1518 );
               weekdays.add( Xst + BYDAY_MON );
               
            }
               break;
            case 2:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:378:6:
               // TUESDAY
            {
               match( input, TUESDAY, FOLLOW_TUESDAY_in_parse_Weekday1532 );
               weekdays.add( Xst + BYDAY_TUE );
               
            }
               break;
            case 3:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:379:6:
               // WEDNESDAY
            {
               match( input, WEDNESDAY, FOLLOW_WEDNESDAY_in_parse_Weekday1545 );
               weekdays.add( Xst + BYDAY_WED );
               
            }
               break;
            case 4:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:380:6:
               // THURSDAY
            {
               match( input, THURSDAY, FOLLOW_THURSDAY_in_parse_Weekday1556 );
               weekdays.add( Xst + BYDAY_THU );
               
            }
               break;
            case 5:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:381:6:
               // FRIDAY
            {
               match( input, FRIDAY, FOLLOW_FRIDAY_in_parse_Weekday1568 );
               weekdays.add( Xst + BYDAY_FRI );
               
            }
               break;
            case 6:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:382:6:
               // SATURDAY
            {
               match( input, SATURDAY, FOLLOW_SATURDAY_in_parse_Weekday1582 );
               weekdays.add( Xst + BYDAY_SAT );
               
            }
               break;
            case 7:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:383:6:
               // SUNDAY
            {
               match( input, SUNDAY, FOLLOW_SUNDAY_in_parse_Weekday1594 );
               weekdays.add( Xst + BYDAY_SUN );
               
            }
               break;
            case 8:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:384:6:
               // WEEKEND
            {
               match( input, WEEKEND, FOLLOW_WEEKEND_in_parse_Weekday1608 );
               
               weekdays.add( Xst + BYDAY_SAT );
               weekdays.add( Xst + BYDAY_SUN );
               
            }
               break;
            case 9:
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:388:6:
               // WEEKDAY_LIT
            {
               match( input,
                      WEEKDAY_LIT,
                      FOLLOW_WEEKDAY_LIT_in_parse_Weekday1621 );
               
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:404:1: parse_Month
   // returns [int number] : m= MONTH ;
   public final int parse_Month() throws RecognitionException
   {
      int number = 0;
      
      Token m = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:405:4: (m=
         // MONTH )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:405:6: m= MONTH
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_parse_Month1656 );
            
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:428:1:
   // parseRecurrencePattern[RecurrPatternLanguage lang, boolean every] returns [String sentence] : OP_FREQ ( (
   // VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] (
   // COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )? ) | ( VAL_MONTHLY
   // parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA
   // parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA
   // parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every]
   // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | (
   // VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )? ;
   public final String parseRecurrencePattern( RecurrPatternLanguage lang,
                                               boolean every ) throws RecognitionException
   {
      String sentence = null;
      
      Token date = null;
      Token count = null;
      
      final StringBuilder sb = new StringBuilder();
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:437:4: (
         // OP_FREQ ( ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
         // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
         // parse_PatternMonth[lang, sb] ) )? ) | ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( (
         // OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
         // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | (
         // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang, sb, true]
         // ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\",
         // every] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )? )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:437:6: OP_FREQ
         // ( ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY parse_PatternWeekday[lang,
         // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )? )
         // | ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY parse_PatternXst[lang,
         // sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA
         // parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\",
         // every] ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? )
         // | ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT
         // count= INT )?
         {
            match( input, OP_FREQ, FOLLOW_OP_FREQ_in_parseRecurrencePattern1716 );
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:438:6: ( (
            // VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY parse_PatternWeekday[lang, sb,
            // true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )? ) |
            // ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
            // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
            // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | (
            // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang, sb,
            // true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | ( VAL_DAILY parse_PatternInterval[lang, sb,
            // \"day\", every] ) )
            int alt37 = 4;
            switch ( input.LA( 1 ) )
            {
               case VAL_YEARLY:
               {
                  alt37 = 1;
               }
                  break;
               case VAL_MONTHLY:
               {
                  alt37 = 2;
               }
                  break;
               case VAL_WEEKLY:
               {
                  alt37 = 3;
               }
                  break;
               case VAL_DAILY:
               {
                  alt37 = 4;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        37,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt37 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:439:11:
                  // ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
                  // parse_PatternMonth[lang, sb] ) )? )
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:439:11:
                  // ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
                  // parse_PatternMonth[lang, sb] ) )? )
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:440:14:
                  // VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
                  // parse_PatternMonth[lang, sb] ) )?
                  {
                     match( input,
                            VAL_YEARLY,
                            FOLLOW_VAL_YEARLY_in_parseRecurrencePattern1751 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern1753 );
                     parse_PatternInterval( lang, sb, "year", every );
                     
                     state._fsp--;
                     
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:441:14:
                     // ( ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                     // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )?
                     int alt30 = 2;
                     int LA30_0 = input.LA( 1 );
                     
                     if ( ( LA30_0 == OP_BYDAY ) )
                     {
                        alt30 = 1;
                     }
                     switch ( alt30 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:442:17:
                           // ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] )
                        {
                           
                           sb.append( " " );
                           lang.add( sb, "on_the" );
                           sb.append( " " );
                           
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:445:17:
                           // ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] )
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:446:20:
                           // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb]
                           {
                              // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:446:20:
                              // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                              // false] )* )
                              // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:446:21:
                              // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                              // false] )*
                              {
                                 match( input,
                                        OP_BYDAY,
                                        FOLLOW_OP_BYDAY_in_parseRecurrencePattern1827 );
                                 pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern1829 );
                                 parse_PatternWeekday( lang, sb, true );
                                 
                                 state._fsp--;
                                 
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:447:23:
                                 // ( COMMA parse_PatternWeekday[lang, sb, false] )*
                                 loop29: do
                                 {
                                    int alt29 = 2;
                                    int LA29_0 = input.LA( 1 );
                                    
                                    if ( ( LA29_0 == COMMA ) )
                                    {
                                       alt29 = 1;
                                    }
                                    
                                    switch ( alt29 )
                                    {
                                       case 1:
                                          // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:447:24:
                                          // COMMA parse_PatternWeekday[lang, sb, false]
                                       {
                                          match( input,
                                                 COMMA,
                                                 FOLLOW_COMMA_in_parseRecurrencePattern1855 );
                                          sb.append( ", " );
                                          pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern1859 );
                                          parse_PatternWeekday( lang, sb, false );
                                          
                                          state._fsp--;
                                          
                                       }
                                          break;
                                       
                                       default :
                                          break loop29;
                                    }
                                 }
                                 while ( true );
                                 
                              }
                              
                              sb.append( " " );
                              lang.add( sb, "in" );
                              sb.append( " " );
                              
                              match( input,
                                     OP_BYMONTH,
                                     FOLLOW_OP_BYMONTH_in_parseRecurrencePattern1905 );
                              pushFollow( FOLLOW_parse_PatternMonth_in_parseRecurrencePattern1907 );
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
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:455:11:
                  // ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
                  // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? )
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:455:11:
                  // ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
                  // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? )
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:456:14:
                  // VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
                  // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )?
                  {
                     match( input,
                            VAL_MONTHLY,
                            FOLLOW_VAL_MONTHLY_in_parseRecurrencePattern1991 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern1993 );
                     parse_PatternInterval( lang, sb, "month", every );
                     
                     state._fsp--;
                     
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:457:14:
                     // ( ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | (
                     // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )
                     // ) )?
                     int alt34 = 2;
                     int LA34_0 = input.LA( 1 );
                     
                     if ( ( LA34_0 == OP_BYDAY || LA34_0 == OP_BYMONTHDAY ) )
                     {
                        alt34 = 1;
                     }
                     switch ( alt34 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:458:17:
                           // ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | (
                           // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) )
                        {
                           
                           sb.append( " " );
                           lang.add( sb, "on_the" );
                           sb.append( " " );
                           
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:461:17:
                           // ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | (
                           // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) )
                           int alt33 = 2;
                           int LA33_0 = input.LA( 1 );
                           
                           if ( ( LA33_0 == OP_BYMONTHDAY ) )
                           {
                              alt33 = 1;
                           }
                           else if ( ( LA33_0 == OP_BYDAY ) )
                           {
                              alt33 = 2;
                           }
                           else
                           {
                              NoViableAltException nvae = new NoViableAltException( "",
                                                                                    33,
                                                                                    0,
                                                                                    input );
                              
                              throw nvae;
                           }
                           switch ( alt33 )
                           {
                              case 1:
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:462:22:
                                 // ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* )
                              {
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:462:22:
                                 // ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* )
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:463:25:
                                 // OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )*
                                 {
                                    match( input,
                                           OP_BYMONTHDAY,
                                           FOLLOW_OP_BYMONTHDAY_in_parseRecurrencePattern2094 );
                                    pushFollow( FOLLOW_parse_PatternXst_in_parseRecurrencePattern2096 );
                                    parse_PatternXst( lang, sb );
                                    
                                    state._fsp--;
                                    
                                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:464:28:
                                    // ( COMMA parse_PatternXst[lang, sb] )*
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
                                             // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:464:29:
                                             // COMMA parse_PatternXst[lang, sb]
                                          {
                                             match( input,
                                                    COMMA,
                                                    FOLLOW_COMMA_in_parseRecurrencePattern2127 );
                                             sb.append( ", " );
                                             pushFollow( FOLLOW_parse_PatternXst_in_parseRecurrencePattern2131 );
                                             parse_PatternXst( lang, sb );
                                             
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
                              case 2:
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:466:22:
                                 // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang,
                                 // sb, false] )* )
                              {
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:466:22:
                                 // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang,
                                 // sb, false] )* )
                                 // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:467:25:
                                 // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                                 // false] )*
                                 {
                                    match( input,
                                           OP_BYDAY,
                                           FOLLOW_OP_BYDAY_in_parseRecurrencePattern2206 );
                                    pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2208 );
                                    parse_PatternWeekday( lang, sb, true );
                                    
                                    state._fsp--;
                                    
                                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:468:29:
                                    // ( COMMA parse_PatternWeekday[lang, sb, false] )*
                                    loop32: do
                                    {
                                       int alt32 = 2;
                                       int LA32_0 = input.LA( 1 );
                                       
                                       if ( ( LA32_0 == COMMA ) )
                                       {
                                          alt32 = 1;
                                       }
                                       
                                       switch ( alt32 )
                                       {
                                          case 1:
                                             // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:468:30:
                                             // COMMA parse_PatternWeekday[lang, sb, false]
                                          {
                                             match( input,
                                                    COMMA,
                                                    FOLLOW_COMMA_in_parseRecurrencePattern2240 );
                                             sb.append( ", " );
                                             pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2244 );
                                             parse_PatternWeekday( lang,
                                                                   sb,
                                                                   false );
                                             
                                             state._fsp--;
                                             
                                          }
                                             break;
                                          
                                          default :
                                             break loop32;
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
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:473:11:
                  // ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? )
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:473:11:
                  // ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? )
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:474:14:
                  // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )?
                  {
                     match( input,
                            VAL_WEEKLY,
                            FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern2343 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern2345 );
                     parse_PatternInterval( lang, sb, "week", every );
                     
                     state._fsp--;
                     
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:475:14:
                     // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )*
                     // )?
                     int alt36 = 2;
                     int LA36_0 = input.LA( 1 );
                     
                     if ( ( LA36_0 == OP_BYDAY ) )
                     {
                        alt36 = 1;
                     }
                     switch ( alt36 )
                     {
                        case 1:
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:476:17:
                           // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )*
                        {
                           
                           sb.append( " " );
                           lang.add( sb, "on_the" );
                           sb.append( " " );
                           
                           match( input,
                                  OP_BYDAY,
                                  FOLLOW_OP_BYDAY_in_parseRecurrencePattern2397 );
                           pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2399 );
                           parse_PatternWeekday( lang, sb, true );
                           
                           state._fsp--;
                           
                           // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:480:20:
                           // ( COMMA parse_PatternWeekday[lang, sb, false] )*
                           loop35: do
                           {
                              int alt35 = 2;
                              int LA35_0 = input.LA( 1 );
                              
                              if ( ( LA35_0 == COMMA ) )
                              {
                                 alt35 = 1;
                              }
                              
                              switch ( alt35 )
                              {
                                 case 1:
                                    // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:480:21:
                                    // COMMA parse_PatternWeekday[lang, sb, false]
                                 {
                                    match( input,
                                           COMMA,
                                           FOLLOW_COMMA_in_parseRecurrencePattern2422 );
                                    sb.append( ", " );
                                    pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2426 );
                                    parse_PatternWeekday( lang, sb, false );
                                    
                                    state._fsp--;
                                    
                                 }
                                    break;
                                 
                                 default :
                                    break loop35;
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
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:483:11:
                  // ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] )
               {
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:483:11:
                  // ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] )
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:484:14:
                  // VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every]
                  {
                     match( input,
                            VAL_DAILY,
                            FOLLOW_VAL_DAILY_in_parseRecurrencePattern2484 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern2486 );
                     parse_PatternInterval( lang, sb, "day", every );
                     
                     state._fsp--;
                     
                  }
                  
               }
                  break;
               
            }
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:487:6: (
            // OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )?
            int alt38 = 3;
            int LA38_0 = input.LA( 1 );
            
            if ( ( LA38_0 == OP_UNTIL ) )
            {
               alt38 = 1;
            }
            else if ( ( LA38_0 == OP_COUNT ) )
            {
               alt38 = 2;
            }
            switch ( alt38 )
            {
               case 1:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:488:11:
                  // OP_UNTIL date= VAL_DATE
               {
                  match( input,
                         OP_UNTIL,
                         FOLLOW_OP_UNTIL_in_parseRecurrencePattern2525 );
                  date = (Token) match( input,
                                        VAL_DATE,
                                        FOLLOW_VAL_DATE_in_parseRecurrencePattern2529 );
                  
                  final String formatedDate = MolokoDateUtils.formatDate( DATE_PATTERN,
                                                                          ( date != null
                                                                                        ? date.getText()
                                                                                        : null ),
                                                                          MolokoDateUtils.FORMAT_WITH_YEAR
                                                                             | MolokoDateUtils.FORMAT_NUMERIC );
                  
                  if ( formatedDate != null )
                  {
                     sb.append( " " );
                     lang.add( sb, "until" );
                     sb.append( " " );
                     sb.append( formatedDate );
                  }
                  
               }
                  break;
               case 2:
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:501:11:
                  // OP_COUNT count= INT
               {
                  match( input,
                         OP_COUNT,
                         FOLLOW_OP_COUNT_in_parseRecurrencePattern2553 );
                  count = (Token) match( input,
                                         INT,
                                         FOLLOW_INT_in_parseRecurrencePattern2557 );
                  
                  sb.append( " " );
                  lang.add( sb, "for" );
                  sb.append( " " );
                  sb.append( ( count != null ? count.getText() : null ) );
                  sb.append( " " );
                  lang.add( sb, "times" );
                  
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:514:1:
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
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:518:4: (
         // OP_INTERVAL interval= INT )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:518:6:
         // OP_INTERVAL interval= INT
         {
            match( input,
                   OP_INTERVAL,
                   FOLLOW_OP_INTERVAL_in_parse_PatternInterval2609 );
            interval = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_parse_PatternInterval2613 );
            
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:531:1:
   // parse_PatternXst[RecurrPatternLanguage lang, StringBuilder sb] : x= INT ;
   public final void parse_PatternXst( RecurrPatternLanguage lang,
                                       StringBuilder sb ) throws RecognitionException
   {
      Token x = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:532:4: (x= INT
         // )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:532:6: x= INT
         {
            x = (Token) match( input, INT, FOLLOW_INT_in_parse_PatternXst2654 );
            
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:558:1:
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
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:561:4: ( ( (
         // parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY |
         // SATURDAY | SUNDAY ) ) ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:561:6: ( (
         // parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY |
         // SATURDAY | SUNDAY ) ) )
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:561:6: ( (
            // parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY
            // | SATURDAY | SUNDAY ) ) )
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:562:9: (
            // parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY
            // | SATURDAY | SUNDAY ) )
            {
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:562:9: (
               // parse_PatternXst[lang, respectXst ? sb : null] )?
               int alt39 = 2;
               int LA39_0 = input.LA( 1 );
               
               if ( ( LA39_0 == INT ) )
               {
                  alt39 = 1;
               }
               switch ( alt39 )
               {
                  case 1:
                     // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:563:12:
                     // parse_PatternXst[lang, respectXst ? sb : null]
                  {
                     pushFollow( FOLLOW_parse_PatternXst_in_parse_PatternWeekday2716 );
                     parse_PatternXst( lang, respectXst ? sb : null );
                     
                     state._fsp--;
                     
                     if ( respectXst )
                        sb.append( " " );
                     
                  }
                     break;
                  
               }
               
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:569:9:
               // (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) )
               // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:570:12:
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
   // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:588:1:
   // parse_PatternMonth[RecurrPatternLanguage lang, StringBuilder sb] : m= INT ;
   public final void parse_PatternMonth( RecurrPatternLanguage lang,
                                         StringBuilder sb ) throws RecognitionException
   {
      Token m = null;
      
      try
      {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:589:4: (m= INT
         // )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\Recurrence.g:589:6: m= INT
         {
            m = (Token) match( input, INT, FOLLOW_INT_in_parse_PatternMonth2960 );
            
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
   
   static final String DFA16_eofS = "\1\uffff\1\14\1\uffff\5\14\1\uffff\1\14\3\uffff\7\14";
   
   static final String DFA16_minS = "\2\6\1\uffff\5\17\1\uffff\1\17\1\21\2\uffff\7\17";
   
   static final String DFA16_maxS = "\2\57\1\uffff\5\57\1\uffff\1\57\1\34\2\uffff\7\57";
   
   static final String DFA16_acceptS = "\2\uffff\1\1\5\uffff\1\3\2\uffff\1\4\1\2\7\uffff";
   
   static final String DFA16_specialS = "\24\uffff}>";
   
   static final String[] DFA16_transitionS =
   {
    "\3\2\2\uffff\2\2\4\uffff\1\1\5\uffff\1\3\2\4\1\5\1\6\1\7\12" + "\2\11\10",
    "\3\2\2\uffff\2\2\2\uffff\2\14\1\uffff\2\12\1\uffff\2\11\20"
       + "\uffff\11\13", "", "\2\14\1\uffff\2\12\23\uffff\11\13",
    "\2\14\1\uffff\2\12\23\uffff\11\13", "\2\14\1\uffff\2\12\23\uffff\11\13",
    "\2\14\1\uffff\2\12\23\uffff\11\13", "\2\14\1\uffff\2\12\23\uffff\11\13",
    "", "\2\14\1\uffff\2\12\23\uffff\11\13",
    "\1\15\5\uffff\1\16\2\17\1\20\1\21\1\22", "", "",
    "\2\14\1\uffff\2\12\1\uffff\2\23\20\uffff\11\13",
    "\2\14\1\uffff\2\12\23\uffff\11\13", "\2\14\1\uffff\2\12\23\uffff\11\13",
    "\2\14\1\uffff\2\12\23\uffff\11\13", "\2\14\1\uffff\2\12\23\uffff\11\13",
    "\2\14\1\uffff\2\12\23\uffff\11\13", "\2\14\1\uffff\2\12\23\uffff\11\13" };
   
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
         return "166:6: ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )";
      }
   }
   
   public static final BitSet FOLLOW_EVERY_in_parseRecurrence68 = new BitSet( new long[]
   { 0x0000FFFFFF8219C0L } );
   
   public static final BitSet FOLLOW_AFTER_in_parseRecurrence74 = new BitSet( new long[]
   { 0x0000FFFFFF8219C0L } );
   
   public static final BitSet FOLLOW_parse_Number_in_parseRecurrence95 = new BitSet( new long[]
   { 0x00000000000019C0L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseRecurrence112 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseRecurrence185 = new BitSet( new long[]
   { 0x0000FF8000018602L } );
   
   public static final BitSet FOLLOW_BIWEEKLY_in_parseRecurrence200 = new BitSet( new long[]
   { 0x0000FF8000018602L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence288 = new BitSet( new long[]
   { 0x0000FF8000000400L } );
   
   public static final BitSet FOLLOW_THE_in_parseRecurrence291 = new BitSet( new long[]
   { 0x0000FF8000000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence294 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseRecurrence334 = new BitSet( new long[]
   { 0x000000001F838602L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence392 = new BitSet( new long[]
   { 0x000000001F820600L } );
   
   public static final BitSet FOLLOW_THE_in_parseRecurrence395 = new BitSet( new long[]
   { 0x000000001F820600L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence400 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseRecurrence444 = new BitSet( new long[]
   { 0x000000001F838602L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence503 = new BitSet( new long[]
   { 0x000000001F820600L } );
   
   public static final BitSet FOLLOW_THE_in_parseRecurrence506 = new BitSet( new long[]
   { 0x000000001F820600L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence511 = new BitSet( new long[]
   { 0x000100000001E002L } );
   
   public static final BitSet FOLLOW_set_in_parseRecurrence595 = new BitSet( new long[]
   { 0x0001000000006000L } );
   
   public static final BitSet FOLLOW_parse_Month_in_parseRecurrence643 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence741 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence756 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence783 = new BitSet( new long[]
   { 0x0000FF8000000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence786 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_UNTIL_in_parseRecurrence826 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOR_in_parseRecurrence846 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_INT_in_parseRecurrence850 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_EOF_in_parseRecurrence873 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst920 = new BitSet( new long[]
   { 0x00000000000C0002L } );
   
   public static final BitSet FOLLOW_set_in_recurr_Xst943 = new BitSet( new long[]
   { 0x000000001F820000L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst953 = new BitSet( new long[]
   { 0x00000000000C0002L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD988 = new BitSet( new long[]
   { 0x00000000000C0002L } );
   
   public static final BitSet FOLLOW_set_in_recurr_WD1010 = new BitSet( new long[]
   { 0x0000FF8000000000L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD1018 = new BitSet( new long[]
   { 0x00000000000C0002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_recurr_Monthly1071 = new BitSet( new long[]
   { 0x0000FF8000100002L } );
   
   public static final BitSet FOLLOW_LAST_in_recurr_Monthly1108 = new BitSet( new long[]
   { 0x0000FF8000000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_recurr_Monthly1148 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Xst1204 = new BitSet( new long[]
   { 0x0000000000600002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Xst1206 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIRST_in_parse_Xst1223 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Xst1243 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THIRD_in_parse_Xst1258 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOURTH_in_parse_Xst1278 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIFTH_in_parse_Xst1297 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Number1355 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_ONE_in_parse_Number1369 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_TWO_in_parse_Number1381 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_THREE_in_parse_Number1393 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_FOUR_in_parse_Number1403 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_FIVE_in_parse_Number1414 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_SIX_in_parse_Number1425 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_SEVEN_in_parse_Number1437 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_EIGHT_in_parse_Number1447 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_NINE_in_parse_Number1457 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_TEN_in_parse_Number1468 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONDAY_in_parse_Weekday1518 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TUESDAY_in_parse_Weekday1532 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEDNESDAY_in_parse_Weekday1545 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THURSDAY_in_parse_Weekday1556 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FRIDAY_in_parse_Weekday1568 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SATURDAY_in_parse_Weekday1582 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SUNDAY_in_parse_Weekday1594 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKEND_in_parse_Weekday1608 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKDAY_LIT_in_parse_Weekday1621 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_parse_Month1656 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_FREQ_in_parseRecurrencePattern1716 = new BitSet( new long[]
   { 0x01A4000000000000L } );
   
   public static final BitSet FOLLOW_VAL_YEARLY_in_parseRecurrencePattern1751 = new BitSet( new long[]
   { 0x1000000000000000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern1753 = new BitSet( new long[]
   { 0x0A08000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern1827 = new BitSet( new long[]
   { 0x00003F8000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern1829 = new BitSet( new long[]
   { 0x0010000000080000L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern1855 = new BitSet( new long[]
   { 0x00003F8000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern1859 = new BitSet( new long[]
   { 0x0010000000080000L } );
   
   public static final BitSet FOLLOW_OP_BYMONTH_in_parseRecurrencePattern1905 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternMonth_in_parseRecurrencePattern1907 = new BitSet( new long[]
   { 0x0A00000000000002L } );
   
   public static final BitSet FOLLOW_VAL_MONTHLY_in_parseRecurrencePattern1991 = new BitSet( new long[]
   { 0x1000000000000000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern1993 = new BitSet( new long[]
   { 0x0A48000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYMONTHDAY_in_parseRecurrencePattern2094 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parseRecurrencePattern2096 = new BitSet( new long[]
   { 0x0A00000000080002L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern2127 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parseRecurrencePattern2131 = new BitSet( new long[]
   { 0x0A00000000080002L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern2206 = new BitSet( new long[]
   { 0x00003F8000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2208 = new BitSet( new long[]
   { 0x0A00000000080002L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern2240 = new BitSet( new long[]
   { 0x00003F8000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2244 = new BitSet( new long[]
   { 0x0A00000000080002L } );
   
   public static final BitSet FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern2343 = new BitSet( new long[]
   { 0x1000000000000000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern2345 = new BitSet( new long[]
   { 0x0A08000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern2397 = new BitSet( new long[]
   { 0x00003F8000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2399 = new BitSet( new long[]
   { 0x0A00000000080002L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern2422 = new BitSet( new long[]
   { 0x00003F8000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern2426 = new BitSet( new long[]
   { 0x0A00000000080002L } );
   
   public static final BitSet FOLLOW_VAL_DAILY_in_parseRecurrencePattern2484 = new BitSet( new long[]
   { 0x1000000000000000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern2486 = new BitSet( new long[]
   { 0x0A00000000000002L } );
   
   public static final BitSet FOLLOW_OP_UNTIL_in_parseRecurrencePattern2525 = new BitSet( new long[]
   { 0x0400000000000000L } );
   
   public static final BitSet FOLLOW_VAL_DATE_in_parseRecurrencePattern2529 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_COUNT_in_parseRecurrencePattern2553 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_INT_in_parseRecurrencePattern2557 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_INTERVAL_in_parse_PatternInterval2609 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternInterval2613 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternXst2654 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parse_PatternWeekday2716 = new BitSet( new long[]
   { 0x00003F8000000000L } );
   
   public static final BitSet FOLLOW_set_in_parse_PatternWeekday2766 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternMonth2960 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
