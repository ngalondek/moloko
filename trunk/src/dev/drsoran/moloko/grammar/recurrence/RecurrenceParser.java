// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g 2013-03-25 10:59:03

package dev.drsoran.moloko.grammar.recurrence;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.grammar.LexerException;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class RecurrenceParser extends AbstractRecurrenceParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AFTER", "AND", "BIWEEKLY",
    "COMMA", "DAILY", "DAYS", "DOT", "EVERY", "FIFTH", "FIRST", "FOR",
    "FOURTH", "FRIDAY", "IN", "INT", "LAST", "MINUS", "MONDAY", "MONTH",
    "MONTHS", "NUMBER", "NUM_EIGHT", "NUM_FIVE", "NUM_FOUR", "NUM_NINE",
    "NUM_ONE", "NUM_SEVEN", "NUM_SIX", "NUM_TEN", "NUM_THREE", "NUM_TWO", "OF",
    "ON", "OTHER", "SATURDAY", "SECOND", "STRING", "ST_S", "SUNDAY", "THE",
    "THIRD", "THURSDAY", "TIMES", "TUESDAY", "UNTIL", "WEDNESDAY",
    "WEEKDAY_LIT", "WEEKEND", "WEEKS", "WS", "YEARS" };
   
   public static final int EOF = -1;
   
   public static final int AFTER = 4;
   
   public static final int AND = 5;
   
   public static final int BIWEEKLY = 6;
   
   public static final int COMMA = 7;
   
   public static final int DAILY = 8;
   
   public static final int DAYS = 9;
   
   public static final int DOT = 10;
   
   public static final int EVERY = 11;
   
   public static final int FIFTH = 12;
   
   public static final int FIRST = 13;
   
   public static final int FOR = 14;
   
   public static final int FOURTH = 15;
   
   public static final int FRIDAY = 16;
   
   public static final int IN = 17;
   
   public static final int INT = 18;
   
   public static final int LAST = 19;
   
   public static final int MINUS = 20;
   
   public static final int MONDAY = 21;
   
   public static final int MONTH = 22;
   
   public static final int MONTHS = 23;
   
   public static final int NUMBER = 24;
   
   public static final int NUM_EIGHT = 25;
   
   public static final int NUM_FIVE = 26;
   
   public static final int NUM_FOUR = 27;
   
   public static final int NUM_NINE = 28;
   
   public static final int NUM_ONE = 29;
   
   public static final int NUM_SEVEN = 30;
   
   public static final int NUM_SIX = 31;
   
   public static final int NUM_TEN = 32;
   
   public static final int NUM_THREE = 33;
   
   public static final int NUM_TWO = 34;
   
   public static final int OF = 35;
   
   public static final int ON = 36;
   
   public static final int OTHER = 37;
   
   public static final int SATURDAY = 38;
   
   public static final int SECOND = 39;
   
   public static final int STRING = 40;
   
   public static final int ST_S = 41;
   
   public static final int SUNDAY = 42;
   
   public static final int THE = 43;
   
   public static final int THIRD = 44;
   
   public static final int THURSDAY = 45;
   
   public static final int TIMES = 46;
   
   public static final int TUESDAY = 47;
   
   public static final int UNTIL = 48;
   
   public static final int WEDNESDAY = 49;
   
   public static final int WEEKDAY_LIT = 50;
   
   public static final int WEEKEND = 51;
   
   public static final int WEEKS = 52;
   
   public static final int WS = 53;
   
   public static final int YEARS = 54;
   
   
   
   // delegates
   public AbstractRecurrenceParser[] getDelegates()
   {
      return new AbstractRecurrenceParser[] {};
   }
   
   
   
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
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g";
   }
   
   
   
   public RecurrenceParser()
   {
      super( null );
   }
   
   public final static Locale LOCALE = Locale.ENGLISH;
   
   
   
   // $ANTLR start "parseRecurrence"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:52:1:
   // parseRecurrence returns [Map< String, Object > res] : ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? ( (
   // DAYS | DAILY ) | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )?
   // r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN
   // | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints]
   // recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )? ;
   public final Map< String, Object > parseRecurrence() throws RecognitionException
   {
      Map< String, Object > res = null;
      
      Token until = null;
      Token count = null;
      RecurrenceParser.recurr_Monthly_return r = null;
      
      int m = 0;
      
      int firstEntry = 0;
      
      res = startParseRecurrence();
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:61:4:
         // ( ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? ( ( DAYS | DAILY ) | ( WEEKS | BIWEEKLY ) ( ( ON
         // )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? |
         // YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) |
         // recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )
         // (until= UNTIL | FOR count= INT )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:61:6:
         // ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? ( ( DAYS | DAILY ) | ( WEEKS | BIWEEKLY ) ( ( ON )?
         // ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? |
         // YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) |
         // recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )
         // (until= UNTIL | FOR count= INT )?
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:61:6:
            // ( EVERY | AFTER )?
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:61:7:
               // EVERY
               {
                  match( input, EVERY, FOLLOW_EVERY_in_parseRecurrence94 );
                  
                  isEvery = Boolean.TRUE;
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:61:43:
               // AFTER
               {
                  match( input, AFTER, FOLLOW_AFTER_in_parseRecurrence100 );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:62:6:
            // ( ( parse_Interval_Number_or_Text )? ( ( DAYS | DAILY ) | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )?
            // recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( (
            // ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) |
            // recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
            // )
            int alt17 = 4;
            alt17 = dfa17.predict( input );
            switch ( alt17 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:63:9:
               // ( parse_Interval_Number_or_Text )? ( ( DAYS | DAILY ) | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )?
               // recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS
               // ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:63:9:
                  // ( parse_Interval_Number_or_Text )?
                  int alt2 = 2;
                  int LA2_0 = input.LA( 1 );
                  
                  if ( ( LA2_0 == INT || ( LA2_0 >= NUM_EIGHT && LA2_0 <= NUM_TWO ) ) )
                  {
                     alt2 = 1;
                  }
                  switch ( alt2 )
                  {
                     case 1:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:63:9:
                     // parse_Interval_Number_or_Text
                     {
                        pushFollow( FOLLOW_parse_Interval_Number_or_Text_in_parseRecurrence127 );
                        parse_Interval_Number_or_Text();
                        
                        state._fsp--;
                        
                     }
                        break;
                  
                  }
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:64:9:
                  // ( ( DAYS | DAILY ) | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS
                  // ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r=
                  // recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? )
                  int alt16 = 4;
                  switch ( input.LA( 1 ) )
                  {
                     case DAILY:
                     case DAYS:
                     {
                        alt16 = 1;
                     }
                        break;
                     case BIWEEKLY:
                     case WEEKS:
                     {
                        alt16 = 2;
                     }
                        break;
                     case MONTHS:
                     {
                        alt16 = 3;
                     }
                        break;
                     case YEARS:
                     {
                        alt16 = 4;
                     }
                        break;
                     default :
                        NoViableAltException nvae = new NoViableAltException( "",
                                                                              16,
                                                                              0,
                                                                              input );
                        
                        throw nvae;
                        
                  }
                  
                  switch ( alt16 )
                  {
                     case 1:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:65:13:
                     // ( DAYS | DAILY )
                     {
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:65:13:
                        // ( DAYS | DAILY )
                        int alt3 = 2;
                        int LA3_0 = input.LA( 1 );
                        
                        if ( ( LA3_0 == DAYS ) )
                        {
                           alt3 = 1;
                        }
                        else if ( ( LA3_0 == DAILY ) )
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
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:66:18:
                           // DAYS
                           {
                              match( input,
                                     DAYS,
                                     FOLLOW_DAYS_in_parseRecurrence171 );
                              
                           }
                              break;
                           case 2:
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:67:18:
                           // DAILY
                           {
                              match( input,
                                     DAILY,
                                     FOLLOW_DAILY_in_parseRecurrence190 );
                              
                              isEvery = Boolean.TRUE;
                              interval = 1;
                              
                           }
                              break;
                        
                        }
                        
                        freq = RecurrencePatternParser.VAL_DAILY_LIT;
                        
                     }
                        break;
                     case 2:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:73:13:
                     // ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )?
                     {
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:73:13:
                        // ( WEEKS | BIWEEKLY )
                        int alt4 = 2;
                        int LA4_0 = input.LA( 1 );
                        
                        if ( ( LA4_0 == WEEKS ) )
                        {
                           alt4 = 1;
                        }
                        else if ( ( LA4_0 == BIWEEKLY ) )
                        {
                           alt4 = 2;
                        }
                        else
                        {
                           NoViableAltException nvae = new NoViableAltException( "",
                                                                                 4,
                                                                                 0,
                                                                                 input );
                           
                           throw nvae;
                           
                        }
                        switch ( alt4 )
                        {
                           case 1:
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:74:18:
                           // WEEKS
                           {
                              match( input,
                                     WEEKS,
                                     FOLLOW_WEEKS_in_parseRecurrence307 );
                              
                           }
                              break;
                           case 2:
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:75:18:
                           // BIWEEKLY
                           {
                              match( input,
                                     BIWEEKLY,
                                     FOLLOW_BIWEEKLY_in_parseRecurrence326 );
                              
                              interval = 2;
                              
                           }
                              break;
                        
                        }
                        
                        freq = RecurrencePatternParser.VAL_WEEKLY_LIT;
                        
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:80:14:
                        // ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )?
                        int alt7 = 2;
                        int LA7_0 = input.LA( 1 );
                        
                        if ( ( LA7_0 == FRIDAY || LA7_0 == MONDAY
                           || LA7_0 == ON || LA7_0 == SATURDAY
                           || ( LA7_0 >= SUNDAY && LA7_0 <= THE )
                           || LA7_0 == THURSDAY || LA7_0 == TUESDAY || ( LA7_0 >= WEDNESDAY && LA7_0 <= WEEKEND ) ) )
                        {
                           alt7 = 1;
                        }
                        switch ( alt7 )
                        {
                           case 1:
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:80:15:
                           // ( ON )? ( THE )? recurr_WD[weekdays, \"\"]
                           {
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:80:15:
                              // ( ON )?
                              int alt5 = 2;
                              int LA5_0 = input.LA( 1 );
                              
                              if ( ( LA5_0 == ON ) )
                              {
                                 alt5 = 1;
                              }
                              switch ( alt5 )
                              {
                                 case 1:
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:80:15:
                                 // ON
                                 {
                                    match( input,
                                           ON,
                                           FOLLOW_ON_in_parseRecurrence426 );
                                    
                                 }
                                    break;
                              
                              }
                              
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:80:19:
                              // ( THE )?
                              int alt6 = 2;
                              int LA6_0 = input.LA( 1 );
                              
                              if ( ( LA6_0 == THE ) )
                              {
                                 alt6 = 1;
                              }
                              switch ( alt6 )
                              {
                                 case 1:
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:80:19:
                                 // THE
                                 {
                                    match( input,
                                           THE,
                                           FOLLOW_THE_in_parseRecurrence429 );
                                    
                                 }
                                    break;
                              
                              }
                              
                              pushFollow( FOLLOW_recurr_WD_in_parseRecurrence432 );
                              recurr_WD( weekdays, "" );
                              
                              state._fsp--;
                              
                              resolution = RecurrencePatternParser.OP_BYDAY_LIT;
                              resolutionVal = join( ",", weekdays );
                              
                           }
                              break;
                        
                        }
                        
                     }
                        break;
                     case 3:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:86:13:
                     // MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )?
                     {
                        match( input,
                               MONTHS,
                               FOLLOW_MONTHS_in_parseRecurrence484 );
                        
                        freq = RecurrencePatternParser.VAL_MONTHLY_LIT;
                        
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:87:14:
                        // ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )?
                        int alt10 = 2;
                        int LA10_0 = input.LA( 1 );
                        
                        if ( ( ( LA10_0 >= FIFTH && LA10_0 <= FIRST )
                           || LA10_0 == FOURTH || LA10_0 == INT
                           || ( LA10_0 >= ON && LA10_0 <= OTHER )
                           || LA10_0 == SECOND || ( LA10_0 >= THE && LA10_0 <= THIRD ) ) )
                        {
                           alt10 = 1;
                        }
                        switch ( alt10 )
                        {
                           case 1:
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:87:15:
                           // ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints]
                           {
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:87:15:
                              // ( ON )?
                              int alt8 = 2;
                              int LA8_0 = input.LA( 1 );
                              
                              if ( ( LA8_0 == ON ) )
                              {
                                 alt8 = 1;
                              }
                              switch ( alt8 )
                              {
                                 case 1:
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:87:15:
                                 // ON
                                 {
                                    match( input,
                                           ON,
                                           FOLLOW_ON_in_parseRecurrence546 );
                                    
                                 }
                                    break;
                              
                              }
                              
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:87:19:
                              // ( THE )?
                              int alt9 = 2;
                              int LA9_0 = input.LA( 1 );
                              
                              if ( ( LA9_0 == THE ) )
                              {
                                 alt9 = 1;
                              }
                              switch ( alt9 )
                              {
                                 case 1:
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:87:19:
                                 // THE
                                 {
                                    match( input,
                                           THE,
                                           FOLLOW_THE_in_parseRecurrence549 );
                                    
                                 }
                                    break;
                              
                              }
                              
                              pushFollow( FOLLOW_recurr_Monthly_in_parseRecurrence554 );
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
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:95:13:
                     // YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m=
                     // parse_Month )? )?
                     {
                        match( input, YEARS, FOLLOW_YEARS_in_parseRecurrence610 );
                        
                        freq = RecurrencePatternParser.VAL_YEARLY_LIT;
                        
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:96:14:
                        // ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month
                        // )? )?
                        int alt15 = 2;
                        int LA15_0 = input.LA( 1 );
                        
                        if ( ( ( LA15_0 >= FIFTH && LA15_0 <= FIRST )
                           || LA15_0 == FOURTH || LA15_0 == INT
                           || ( LA15_0 >= ON && LA15_0 <= OTHER )
                           || LA15_0 == SECOND || ( LA15_0 >= THE && LA15_0 <= THIRD ) ) )
                        {
                           alt15 = 1;
                        }
                        switch ( alt15 )
                        {
                           case 1:
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:96:15:
                           // ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month
                           // )?
                           {
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:96:15:
                              // ( ON )?
                              int alt11 = 2;
                              int LA11_0 = input.LA( 1 );
                              
                              if ( ( LA11_0 == ON ) )
                              {
                                 alt11 = 1;
                              }
                              switch ( alt11 )
                              {
                                 case 1:
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:96:15:
                                 // ON
                                 {
                                    match( input,
                                           ON,
                                           FOLLOW_ON_in_parseRecurrence673 );
                                    
                                 }
                                    break;
                              
                              }
                              
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:96:19:
                              // ( THE )?
                              int alt12 = 2;
                              int LA12_0 = input.LA( 1 );
                              
                              if ( ( LA12_0 == THE ) )
                              {
                                 alt12 = 1;
                              }
                              switch ( alt12 )
                              {
                                 case 1:
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:96:19:
                                 // THE
                                 {
                                    match( input,
                                           THE,
                                           FOLLOW_THE_in_parseRecurrence676 );
                                    
                                 }
                                    break;
                              
                              }
                              
                              pushFollow( FOLLOW_recurr_Monthly_in_parseRecurrence681 );
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
                              
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:103:39:
                              // ( ( IN | OF )? m= parse_Month )?
                              int alt14 = 2;
                              int LA14_0 = input.LA( 1 );
                              
                              if ( ( LA14_0 == IN || LA14_0 == MONTH || LA14_0 == OF ) )
                              {
                                 alt14 = 1;
                              }
                              switch ( alt14 )
                              {
                                 case 1:
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:104:41:
                                 // ( IN | OF )? m= parse_Month
                                 {
                                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:104:41:
                                    // ( IN | OF )?
                                    int alt13 = 2;
                                    int LA13_0 = input.LA( 1 );
                                    
                                    if ( ( LA13_0 == IN || LA13_0 == OF ) )
                                    {
                                       alt13 = 1;
                                    }
                                    switch ( alt13 )
                                    {
                                       case 1:
                                       // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:
                                       {
                                          if ( input.LA( 1 ) == IN
                                             || input.LA( 1 ) == OF )
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
                                    
                                    pushFollow( FOLLOW_parse_Month_in_parseRecurrence829 );
                                    m = parse_Month();
                                    
                                    state._fsp--;
                                    
                                    freq = RecurrencePatternParser.VAL_YEARLY_LIT;
                                    interval = 1;
                                    res.put( RecurrencePatternParser.OP_BYMONTH_LIT,
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:114:6:
               // recurr_Xst[ints]
               {
                  pushFollow( FOLLOW_recurr_Xst_in_parseRecurrence935 );
                  recurr_Xst( ints );
                  
                  state._fsp--;
                  
                  freq = RecurrencePatternParser.VAL_MONTHLY_LIT;
                  interval = 1;
                  resolution = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
                  resolutionVal = join( ",", ints );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:121:6:
               // recurr_WD[weekdays, \"\"]
               {
                  pushFollow( FOLLOW_recurr_WD_in_parseRecurrence950 );
                  recurr_WD( weekdays, "" );
                  
                  state._fsp--;
                  
                  freq = RecurrencePatternParser.VAL_WEEKLY_LIT;
                  interval = 1;
                  resolution = RecurrencePatternParser.OP_BYDAY_LIT;
                  resolutionVal = join( ",", weekdays );
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:128:6:
               // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:128:6:
                  // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:129:9:
                  // firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"]
                  {
                     pushFollow( FOLLOW_recurr_Xst_in_parseRecurrence977 );
                     firstEntry = recurr_Xst( ints );
                     
                     state._fsp--;
                     
                     pushFollow( FOLLOW_recurr_WD_in_parseRecurrence980 );
                     recurr_WD( weekdays, "" );
                     
                     state._fsp--;
                     
                     freq = RecurrencePatternParser.VAL_WEEKLY_LIT;
                     interval = firstEntry;
                     resolution = RecurrencePatternParser.OP_BYDAY_LIT;
                     resolutionVal = join( ",", weekdays );
                     
                  }
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:138:4:
            // (until= UNTIL | FOR count= INT )?
            int alt18 = 3;
            int LA18_0 = input.LA( 1 );
            
            if ( ( LA18_0 == UNTIL ) )
            {
               alt18 = 1;
            }
            else if ( ( LA18_0 == FOR ) )
            {
               alt18 = 2;
            }
            switch ( alt18 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:139:9:
               // until= UNTIL
               {
                  until = (Token) match( input,
                                         UNTIL,
                                         FOLLOW_UNTIL_in_parseRecurrence1020 );
                  
                  setUntil( ( until != null ? until.getText() : null ).replaceFirst( "until\\s*",
                                                                                     "" ) );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:143:9:
               // FOR count= INT
               {
                  match( input, FOR, FOLLOW_FOR_in_parseRecurrence1040 );
                  
                  count = (Token) match( input,
                                         INT,
                                         FOLLOW_INT_in_parseRecurrence1044 );
                  
                  res.put( RecurrencePatternParser.OP_COUNT_LIT,
                           Integer.parseInt( ( count != null ? count.getText()
                                                            : null ) ) );
                  
               }
                  break;
            
            }
            
         }
         
         res = finishedParseRecurrence();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      catch ( LexerException e )
      {
         
         throw new RecognitionException();
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return res;
   }
   
   
   
   // $ANTLR end "parseRecurrence"
   
   // $ANTLR start "recurr_Xst"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:162:1:
   // recurr_Xst[Set< Integer > res] returns [int firstEntry] : x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? ;
   public final int recurr_Xst( Set< Integer > res ) throws RecognitionException
   {
      int firstEntry = 0;
      
      int x = 0;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:163:4:
         // (x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:163:6:
         // x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )?
         {
            pushFollow( FOLLOW_parse_Xst_in_recurr_Xst1119 );
            x = parse_Xst();
            
            state._fsp--;
            
            res.add( x );
            firstEntry = x;
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:164:4:
            // ( ( ( AND | COMMA ) x= parse_Xst )+ )?
            int alt20 = 2;
            int LA20_0 = input.LA( 1 );
            
            if ( ( LA20_0 == AND || LA20_0 == COMMA ) )
            {
               alt20 = 1;
            }
            switch ( alt20 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:164:5:
               // ( ( AND | COMMA ) x= parse_Xst )+
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:164:5:
                  // ( ( AND | COMMA ) x= parse_Xst )+
                  int cnt19 = 0;
                  loop19: do
                  {
                     int alt19 = 2;
                     int LA19_0 = input.LA( 1 );
                     
                     if ( ( LA19_0 == AND || LA19_0 == COMMA ) )
                     {
                        alt19 = 1;
                     }
                     
                     switch ( alt19 )
                     {
                        case 1:
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:164:6:
                        // ( AND | COMMA ) x= parse_Xst
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
                           
                           pushFollow( FOLLOW_parse_Xst_in_recurr_Xst1152 );
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
         // do for sure before leaving
      }
      return firstEntry;
   }
   
   
   
   // $ANTLR end "recurr_Xst"
   
   // $ANTLR start "recurr_WD"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:171:1:
   // recurr_WD[Set< String > weekdays, String Xst] : parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA )
   // parse_Weekday[weekdays, Xst, true] )+ )? ;
   public final void recurr_WD( Set< String > weekdays, String Xst ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:172:4:
         // ( parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:172:6:
         // parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
         {
            pushFollow( FOLLOW_parse_Weekday_in_recurr_WD1187 );
            parse_Weekday( weekdays, Xst, true );
            
            state._fsp--;
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:173:4:
            // ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
            int alt22 = 2;
            int LA22_0 = input.LA( 1 );
            
            if ( ( LA22_0 == AND || LA22_0 == COMMA ) )
            {
               alt22 = 1;
            }
            switch ( alt22 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:173:5:
               // ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:173:5:
                  // ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
                  int cnt21 = 0;
                  loop21: do
                  {
                     int alt21 = 2;
                     int LA21_0 = input.LA( 1 );
                     
                     if ( ( LA21_0 == AND || LA21_0 == COMMA ) )
                     {
                        alt21 = 1;
                     }
                     
                     switch ( alt21 )
                     {
                        case 1:
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:173:6:
                        // ( AND | COMMA ) parse_Weekday[weekdays, Xst, true]
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
                           
                           pushFollow( FOLLOW_parse_Weekday_in_recurr_WD1217 );
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
         // do for sure before leaving
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
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:180:1:
   // recurr_Monthly[Set< String > weekdays,\r\n Set< Integer > ints ] returns [String freq,\r\n String resolution,\r\n
   // String resolutionVal,\r\n int interval,\r\n boolean hasWD] : firstEntry= recurr_Xst[ints] ( ( LAST )?
   // recurr_WD[weekdays, Integer.toString( firstEntry )] )? ;
   public final RecurrenceParser.recurr_Monthly_return recurr_Monthly( Set< String > weekdays,
                                                                       Set< Integer > ints ) throws RecognitionException
   {
      RecurrenceParser.recurr_Monthly_return retval = new RecurrenceParser.recurr_Monthly_return();
      retval.start = input.LT( 1 );
      
      int firstEntry = 0;
      
      retval.freq = RecurrencePatternParser.VAL_MONTHLY_LIT;
      retval.interval = 1;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:191:4:
         // (firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:191:6:
         // firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
         {
            pushFollow( FOLLOW_recurr_Xst_in_recurr_Monthly1270 );
            firstEntry = recurr_Xst( ints );
            
            state._fsp--;
            
            retval.resolution = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
            retval.resolutionVal = join( ",", ints );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:195:8:
            // ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            int alt24 = 2;
            int LA24_0 = input.LA( 1 );
            
            if ( ( LA24_0 == FRIDAY || LA24_0 == LAST || LA24_0 == MONDAY
               || LA24_0 == SATURDAY || LA24_0 == SUNDAY || LA24_0 == THURSDAY
               || LA24_0 == TUESDAY || ( LA24_0 >= WEDNESDAY && LA24_0 <= WEEKEND ) ) )
            {
               alt24 = 1;
            }
            switch ( alt24 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:196:11:
               // ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )]
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:196:11:
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
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:197:14:
                     // LAST
                     {
                        match( input, LAST, FOLLOW_LAST_in_recurr_Monthly1307 );
                        
                        firstEntry = -firstEntry;
                        
                     }
                        break;
                  
                  }
                  
                  pushFollow( FOLLOW_recurr_WD_in_recurr_Monthly1347 );
                  recurr_WD( weekdays, Integer.toString( firstEntry ) );
                  
                  state._fsp--;
                  
                  retval.hasWD = true;
                  retval.resolution = RecurrencePatternParser.OP_BYDAY_LIT;
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
         // do for sure before leaving
      }
      return retval;
   }
   
   
   
   // $ANTLR end "recurr_Monthly"
   
   // $ANTLR start "parse_Xst"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:215:1:
   // parse_Xst returns [int number] : (n= INT ( DOT | ST_S )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH );
   public final int parse_Xst() throws RecognitionException
   {
      int number = 0;
      
      Token n = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:216:4:
         // (n= INT ( DOT | ST_S )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH )
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
            case OTHER:
            case SECOND:
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:216:6:
            // n= INT ( DOT | ST_S )?
            {
               n = (Token) match( input, INT, FOLLOW_INT_in_parse_Xst1403 );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:216:12:
               // ( DOT | ST_S )?
               int alt25 = 2;
               int LA25_0 = input.LA( 1 );
               
               if ( ( LA25_0 == DOT || LA25_0 == ST_S ) )
               {
                  alt25 = 1;
               }
               switch ( alt25 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:
                  {
                     if ( input.LA( 1 ) == DOT || input.LA( 1 ) == ST_S )
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
               
               number = limitMonthDay( Integer.parseInt( ( n != null
                                                                    ? n.getText()
                                                                    : null ) ) );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:220:6:
            // FIRST
            {
               match( input, FIRST, FOLLOW_FIRST_in_parse_Xst1422 );
               
               number = 1;
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:221:6:
            // ( SECOND | OTHER )
            {
               if ( input.LA( 1 ) == OTHER || input.LA( 1 ) == SECOND )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:222:6:
            // THIRD
            {
               match( input, THIRD, FOLLOW_THIRD_in_parse_Xst1457 );
               
               number = 3;
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:223:6:
            // FOURTH
            {
               match( input, FOURTH, FOLLOW_FOURTH_in_parse_Xst1477 );
               
               number = 4;
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:224:6:
            // FIFTH
            {
               match( input, FIFTH, FOLLOW_FIFTH_in_parse_Xst1496 );
               
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
         // do for sure before leaving
      }
      return number;
   }
   
   
   
   // $ANTLR end "parse_Xst"
   
   // $ANTLR start "parse_Interval_Number_or_Text"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:235:1:
   // parse_Interval_Number_or_Text : (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX |
   // NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN );
   public final void parse_Interval_Number_or_Text() throws RecognitionException
   {
      Token n = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:236:4:
         // (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE |
         // NUM_TEN )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:236:6:
            // n= INT
            {
               n = (Token) match( input,
                                  INT,
                                  FOLLOW_INT_in_parse_Interval_Number_or_Text1550 );
               
               interval = Integer.parseInt( ( n != null ? n.getText() : null ) );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:237:6:
            // NUM_ONE
            {
               match( input,
                      NUM_ONE,
                      FOLLOW_NUM_ONE_in_parse_Interval_Number_or_Text1564 );
               
               interval = 1;
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:238:6:
            // NUM_TWO
            {
               match( input,
                      NUM_TWO,
                      FOLLOW_NUM_TWO_in_parse_Interval_Number_or_Text1576 );
               
               interval = 2;
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:239:6:
            // NUM_THREE
            {
               match( input,
                      NUM_THREE,
                      FOLLOW_NUM_THREE_in_parse_Interval_Number_or_Text1588 );
               
               interval = 3;
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:240:6:
            // NUM_FOUR
            {
               match( input,
                      NUM_FOUR,
                      FOLLOW_NUM_FOUR_in_parse_Interval_Number_or_Text1598 );
               
               interval = 4;
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:241:6:
            // NUM_FIVE
            {
               match( input,
                      NUM_FIVE,
                      FOLLOW_NUM_FIVE_in_parse_Interval_Number_or_Text1609 );
               
               interval = 5;
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:242:6:
            // NUM_SIX
            {
               match( input,
                      NUM_SIX,
                      FOLLOW_NUM_SIX_in_parse_Interval_Number_or_Text1620 );
               
               interval = 6;
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:243:6:
            // NUM_SEVEN
            {
               match( input,
                      NUM_SEVEN,
                      FOLLOW_NUM_SEVEN_in_parse_Interval_Number_or_Text1632 );
               
               interval = 7;
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:244:6:
            // NUM_EIGHT
            {
               match( input,
                      NUM_EIGHT,
                      FOLLOW_NUM_EIGHT_in_parse_Interval_Number_or_Text1642 );
               
               interval = 8;
               
            }
               break;
            case 10:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:245:6:
            // NUM_NINE
            {
               match( input,
                      NUM_NINE,
                      FOLLOW_NUM_NINE_in_parse_Interval_Number_or_Text1652 );
               
               interval = 9;
               
            }
               break;
            case 11:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:246:6:
            // NUM_TEN
            {
               match( input,
                      NUM_TEN,
                      FOLLOW_NUM_TEN_in_parse_Interval_Number_or_Text1663 );
               
               interval = 10;
               
            }
               break;
         
         }
      }
      catch ( RecognitionException e )
      {
         
         interval = 1;
         
      }
      catch ( NumberFormatException nfe )
      {
         
         interval = 1;
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "parse_Interval_Number_or_Text"
   
   // $ANTLR start "parse_Weekday"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:257:1:
   // parse_Weekday[Set< String > weekdays, String Xst, boolean strict] returns [String weekday] : ( MONDAY | TUESDAY |
   // WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT );
   public final String parse_Weekday( Set< String > weekdays,
                                      String Xst,
                                      boolean strict ) throws RecognitionException
   {
      String weekday = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:258:4:
         // ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:258:6:
            // MONDAY
            {
               match( input, MONDAY, FOLLOW_MONDAY_in_parse_Weekday1713 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_MON );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:259:6:
            // TUESDAY
            {
               match( input, TUESDAY, FOLLOW_TUESDAY_in_parse_Weekday1727 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_TUE );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:260:6:
            // WEDNESDAY
            {
               match( input, WEDNESDAY, FOLLOW_WEDNESDAY_in_parse_Weekday1740 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_WED );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:261:6:
            // THURSDAY
            {
               match( input, THURSDAY, FOLLOW_THURSDAY_in_parse_Weekday1751 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_THU );
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:262:6:
            // FRIDAY
            {
               match( input, FRIDAY, FOLLOW_FRIDAY_in_parse_Weekday1763 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_FRI );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:263:6:
            // SATURDAY
            {
               match( input, SATURDAY, FOLLOW_SATURDAY_in_parse_Weekday1777 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT );
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:264:6:
            // SUNDAY
            {
               match( input, SUNDAY, FOLLOW_SUNDAY_in_parse_Weekday1789 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN );
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:265:6:
            // WEEKEND
            {
               match( input, WEEKEND, FOLLOW_WEEKEND_in_parse_Weekday1803 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN );
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:269:6:
            // WEEKDAY_LIT
            {
               match( input,
                      WEEKDAY_LIT,
                      FOLLOW_WEEKDAY_LIT_in_parse_Weekday1816 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_MON );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_TUE );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_WED );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_THU );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_FRI );
               
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
         // do for sure before leaving
      }
      return weekday;
   }
   
   
   
   // $ANTLR end "parse_Weekday"
   
   // $ANTLR start "parse_Month"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:285:1:
   // parse_Month returns [int number] : m= MONTH ;
   public final int parse_Month() throws RecognitionException
   {
      int number = 0;
      
      Token m = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:286:4:
         // (m= MONTH )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\Recurrence.g:286:6:
         // m= MONTH
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_parse_Month1851 );
            
            number = textMonthToMonthNumber( ( m != null ? m.getText() : null ),
                                             LOCALE );
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         return 1;
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return number;
   }
   
   // $ANTLR end "parse_Month"
   
   // Delegated rules
   
   protected DFA17 dfa17 = new DFA17( this );
   
   static final String DFA17_eotS = "\24\uffff";
   
   static final String DFA17_eofS = "\1\uffff\1\13\1\uffff\5\13\1\uffff\1\13\3\uffff\7\13";
   
   static final String DFA17_minS = "\1\6\1\5\1\uffff\5\5\1\uffff\1\5\1\14\2\uffff\7\5";
   
   static final String DFA17_maxS = "\2\66\1\uffff\5\63\1\uffff\1\63\1\54\2\uffff\7\63";
   
   static final String DFA17_acceptS = "\2\uffff\1\1\5\uffff\1\3\2\uffff\1\2\1\4\7\uffff";
   
   static final String DFA17_specialS = "\24\uffff}>";
   
   static final String[] DFA17_transitionS =
   {
    "\1\2\1\uffff\2\2\2\uffff\1\7\1\3\1\uffff\1\6\1\10\1\uffff\1"
       + "\1\2\uffff\1\10\1\uffff\1\2\1\uffff\12\2\2\uffff\1\4\1\10\1"
       + "\4\2\uffff\1\10\1\uffff\1\5\1\10\1\uffff\1\10\1\uffff\3\10\1"
       + "\2\1\uffff\1\2",
    "\1\12\1\2\1\12\2\2\1\11\3\uffff\1\13\1\uffff\1\14\4\uffff\1"
       + "\14\1\uffff\1\2\16\uffff\1\14\2\uffff\1\11\1\14\2\uffff\1\14"
       + "\1\uffff\1\14\1\13\3\14\1\2\1\uffff\1\2",
    "",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\22\1\16\1\uffff\1\21\2\uffff\1\15\22\uffff\1\17\1\uffff"
       + "\1\17\4\uffff\1\20",
    "",
    "",
    "\1\12\1\uffff\1\12\2\uffff\1\23\3\uffff\1\13\1\uffff\1\14\4"
       + "\uffff\1\14\20\uffff\1\14\2\uffff\1\23\1\14\2\uffff\1\14\1\uffff"
       + "\1\14\1\13\3\14",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\12\1\uffff\1\12\6\uffff\1\13\1\uffff\1\14\4\uffff\1\14\20"
       + "\uffff\1\14\3\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\13\3\14" };
   
   static final short[] DFA17_eot = DFA.unpackEncodedString( DFA17_eotS );
   
   static final short[] DFA17_eof = DFA.unpackEncodedString( DFA17_eofS );
   
   static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars( DFA17_minS );
   
   static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars( DFA17_maxS );
   
   static final short[] DFA17_accept = DFA.unpackEncodedString( DFA17_acceptS );
   
   static final short[] DFA17_special = DFA.unpackEncodedString( DFA17_specialS );
   
   static final short[][] DFA17_transition;
   
   static
   {
      int numStates = DFA17_transitionS.length;
      DFA17_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA17_transition[ i ] = DFA.unpackEncodedString( DFA17_transitionS[ i ] );
      }
   }
   
   
   class DFA17 extends DFA
   {
      
      public DFA17( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 17;
         this.eot = DFA17_eot;
         this.eof = DFA17_eof;
         this.min = DFA17_min;
         this.max = DFA17_max;
         this.accept = DFA17_accept;
         this.special = DFA17_special;
         this.transition = DFA17_transition;
      }
      
      
      
      public String getDescription()
      {
         return "62:6: ( ( parse_Interval_Number_or_Text )? ( ( DAYS | DAILY ) | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )";
      }
   }
   
   public static final BitSet FOLLOW_EVERY_in_parseRecurrence94 = new BitSet( new long[]
   { 0x005EB4E7FEA5B340L } );
   
   public static final BitSet FOLLOW_AFTER_in_parseRecurrence100 = new BitSet( new long[]
   { 0x005EB4E7FEA5B340L } );
   
   public static final BitSet FOLLOW_parse_Interval_Number_or_Text_in_parseRecurrence127 = new BitSet( new long[]
   { 0x0050000000800340L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseRecurrence171 = new BitSet( new long[]
   { 0x0001000000004002L } );
   
   public static final BitSet FOLLOW_DAILY_in_parseRecurrence190 = new BitSet( new long[]
   { 0x0001000000004002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseRecurrence307 = new BitSet( new long[]
   { 0x000FAC5000214002L } );
   
   public static final BitSet FOLLOW_BIWEEKLY_in_parseRecurrence326 = new BitSet( new long[]
   { 0x000FAC5000214002L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence426 = new BitSet( new long[]
   { 0x000EAC4000210000L } );
   
   public static final BitSet FOLLOW_THE_in_parseRecurrence429 = new BitSet( new long[]
   { 0x000EA44000210000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence432 = new BitSet( new long[]
   { 0x0001000000004002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseRecurrence484 = new BitSet( new long[]
   { 0x000118B00004F002L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence546 = new BitSet( new long[]
   { 0x000018A00004B000L } );
   
   public static final BitSet FOLLOW_THE_in_parseRecurrence549 = new BitSet( new long[]
   { 0x000010A00004B000L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence554 = new BitSet( new long[]
   { 0x0001000000004002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseRecurrence610 = new BitSet( new long[]
   { 0x000118B00004F002L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence673 = new BitSet( new long[]
   { 0x000018A00004B000L } );
   
   public static final BitSet FOLLOW_THE_in_parseRecurrence676 = new BitSet( new long[]
   { 0x000010A00004B000L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence681 = new BitSet( new long[]
   { 0x0001000800424002L } );
   
   public static final BitSet FOLLOW_parse_Month_in_parseRecurrence829 = new BitSet( new long[]
   { 0x0001000000004002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence935 = new BitSet( new long[]
   { 0x0001000000004002L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence950 = new BitSet( new long[]
   { 0x0001000000004002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence977 = new BitSet( new long[]
   { 0x000EA44000210000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence980 = new BitSet( new long[]
   { 0x0001000000004002L } );
   
   public static final BitSet FOLLOW_UNTIL_in_parseRecurrence1020 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOR_in_parseRecurrence1040 = new BitSet( new long[]
   { 0x0000000000040000L } );
   
   public static final BitSet FOLLOW_INT_in_parseRecurrence1044 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst1119 = new BitSet( new long[]
   { 0x00000000000000A2L } );
   
   public static final BitSet FOLLOW_set_in_recurr_Xst1142 = new BitSet( new long[]
   { 0x000010A00004B000L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst1152 = new BitSet( new long[]
   { 0x00000000000000A2L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD1187 = new BitSet( new long[]
   { 0x00000000000000A2L } );
   
   public static final BitSet FOLLOW_set_in_recurr_WD1209 = new BitSet( new long[]
   { 0x000EA44000210000L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD1217 = new BitSet( new long[]
   { 0x00000000000000A2L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_recurr_Monthly1270 = new BitSet( new long[]
   { 0x000EA44000290002L } );
   
   public static final BitSet FOLLOW_LAST_in_recurr_Monthly1307 = new BitSet( new long[]
   { 0x000EA44000210000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_recurr_Monthly1347 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Xst1403 = new BitSet( new long[]
   { 0x0000020000000402L } );
   
   public static final BitSet FOLLOW_FIRST_in_parse_Xst1422 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Xst1442 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THIRD_in_parse_Xst1457 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOURTH_in_parse_Xst1477 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIFTH_in_parse_Xst1496 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Interval_Number_or_Text1550 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_ONE_in_parse_Interval_Number_or_Text1564 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_TWO_in_parse_Interval_Number_or_Text1576 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_THREE_in_parse_Interval_Number_or_Text1588 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_FOUR_in_parse_Interval_Number_or_Text1598 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_FIVE_in_parse_Interval_Number_or_Text1609 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_SIX_in_parse_Interval_Number_or_Text1620 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_SEVEN_in_parse_Interval_Number_or_Text1632 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_EIGHT_in_parse_Interval_Number_or_Text1642 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_NINE_in_parse_Interval_Number_or_Text1652 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_TEN_in_parse_Interval_Number_or_Text1663 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONDAY_in_parse_Weekday1713 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TUESDAY_in_parse_Weekday1727 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEDNESDAY_in_parse_Weekday1740 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THURSDAY_in_parse_Weekday1751 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FRIDAY_in_parse_Weekday1763 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SATURDAY_in_parse_Weekday1777 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SUNDAY_in_parse_Weekday1789 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKEND_in_parse_Weekday1803 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKDAY_LIT_in_parse_Weekday1816 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_parse_Month1851 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
