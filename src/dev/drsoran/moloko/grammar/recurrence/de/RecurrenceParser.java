// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g 2013-03-22 13:25:28

package dev.drsoran.moloko.grammar.recurrence.de;

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
import dev.drsoran.moloko.grammar.recurrence.AbstractANTLRRecurrenceParser;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class RecurrenceParser extends AbstractANTLRRecurrenceParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AFTER", "AND", "COMMA", "DAYS",
    "DOT", "EVERY", "FIFTH", "FIRST", "FOR", "FOURTH", "FRIDAY", "INT", "LAST",
    "MINUS", "MONDAY", "MONTH", "MONTHS", "NUMBER", "NUM_EIGHT", "NUM_FIVE",
    "NUM_FOUR", "NUM_NINE", "NUM_ONE", "NUM_SEVEN", "NUM_SIX", "NUM_TEN",
    "NUM_THREE", "NUM_TWO", "ON", "OTHER", "SATURDAY", "SECOND", "STRING",
    "SUNDAY", "THIRD", "THURSDAY", "TIMES", "TUESDAY", "UNTIL", "WEDNESDAY",
    "WEEKDAY_LIT", "WEEKEND", "WEEKS", "WS", "YEARS" };
   
   public static final int EOF = -1;
   
   public static final int AFTER = 4;
   
   public static final int AND = 5;
   
   public static final int COMMA = 6;
   
   public static final int DAYS = 7;
   
   public static final int DOT = 8;
   
   public static final int EVERY = 9;
   
   public static final int FIFTH = 10;
   
   public static final int FIRST = 11;
   
   public static final int FOR = 12;
   
   public static final int FOURTH = 13;
   
   public static final int FRIDAY = 14;
   
   public static final int INT = 15;
   
   public static final int LAST = 16;
   
   public static final int MINUS = 17;
   
   public static final int MONDAY = 18;
   
   public static final int MONTH = 19;
   
   public static final int MONTHS = 20;
   
   public static final int NUMBER = 21;
   
   public static final int NUM_EIGHT = 22;
   
   public static final int NUM_FIVE = 23;
   
   public static final int NUM_FOUR = 24;
   
   public static final int NUM_NINE = 25;
   
   public static final int NUM_ONE = 26;
   
   public static final int NUM_SEVEN = 27;
   
   public static final int NUM_SIX = 28;
   
   public static final int NUM_TEN = 29;
   
   public static final int NUM_THREE = 30;
   
   public static final int NUM_TWO = 31;
   
   public static final int ON = 32;
   
   public static final int OTHER = 33;
   
   public static final int SATURDAY = 34;
   
   public static final int SECOND = 35;
   
   public static final int STRING = 36;
   
   public static final int SUNDAY = 37;
   
   public static final int THIRD = 38;
   
   public static final int THURSDAY = 39;
   
   public static final int TIMES = 40;
   
   public static final int TUESDAY = 41;
   
   public static final int UNTIL = 42;
   
   public static final int WEDNESDAY = 43;
   
   public static final int WEEKDAY_LIT = 44;
   
   public static final int WEEKEND = 45;
   
   public static final int WEEKS = 46;
   
   public static final int WS = 47;
   
   public static final int YEARS = 48;
   
   
   
   // delegates
   public AbstractANTLRRecurrenceParser[] getDelegates()
   {
      return new AbstractANTLRRecurrenceParser[] {};
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
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g";
   }
   
   
   
   public RecurrenceParser()
   {
      super( null );
   }
   
   
   
   protected String getUntilLiteral()
   {
      return "bis";
   }
   
   
   
   // $ANTLR start "parseRecurrence"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:54:1:
   // parseRecurrence returns [Map< String, Object > res] : ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? (
   // DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? |
   // YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] |
   // recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count=
   // INT )? ;
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:63:4:
         // ( ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"]
         // )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays,
         // ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] |
         // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:63:6:
         // ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"]
         // )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays,
         // ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] |
         // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )?
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:63:6:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:63:7:
               // EVERY
               {
                  match( input, EVERY, FOLLOW_EVERY_in_parseRecurrence93 );
                  
                  isEvery = Boolean.TRUE;
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:63:43:
               // AFTER
               {
                  match( input, AFTER, FOLLOW_AFTER_in_parseRecurrence99 );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:64:6:
            // ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( (
            // ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? =>
            // ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry=
            // recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )
            int alt12 = 4;
            alt12 = dfa12.predict( input );
            switch ( alt12 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:65:9:
               // ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( (
               // ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}?
               // => ( ( ON )? m= parse_Month )? )? )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:65:9:
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
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:65:9:
                     // parse_Interval_Number_or_Text
                     {
                        pushFollow( FOLLOW_parse_Interval_Number_or_Text_in_parseRecurrence118 );
                        parse_Interval_Number_or_Text();
                        
                        state._fsp--;
                        
                     }
                        break;
                  
                  }
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:66:9:
                  // ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r=
                  // recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( (
                  // ON )? m= parse_Month )? )? )
                  int alt11 = 4;
                  switch ( input.LA( 1 ) )
                  {
                     case DAYS:
                     {
                        alt11 = 1;
                     }
                        break;
                     case WEEKS:
                     {
                        alt11 = 2;
                     }
                        break;
                     case MONTHS:
                     {
                        alt11 = 3;
                     }
                        break;
                     case YEARS:
                     {
                        alt11 = 4;
                     }
                        break;
                     default :
                        NoViableAltException nvae = new NoViableAltException( "",
                                                                              11,
                                                                              0,
                                                                              input );
                        
                        throw nvae;
                        
                  }
                  
                  switch ( alt11 )
                  {
                     case 1:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:67:13:
                     // DAYS
                     {
                        match( input, DAYS, FOLLOW_DAYS_in_parseRecurrence143 );
                        
                        freq = RecurrencePatternParser.VAL_DAILY_LIT;
                        
                     }
                        break;
                     case 2:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:68:13:
                     // WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )?
                     {
                        match( input, WEEKS, FOLLOW_WEEKS_in_parseRecurrence205 );
                        
                        freq = RecurrencePatternParser.VAL_WEEKLY_LIT;
                        
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:69:14:
                        // ( ( ON )? recurr_WD[weekdays, \"\"] )?
                        int alt4 = 2;
                        int LA4_0 = input.LA( 1 );
                        
                        if ( ( LA4_0 == FRIDAY || LA4_0 == MONDAY
                           || LA4_0 == ON || LA4_0 == SATURDAY
                           || LA4_0 == SUNDAY || LA4_0 == THURSDAY
                           || LA4_0 == TUESDAY || ( LA4_0 >= WEDNESDAY && LA4_0 <= WEEKEND ) ) )
                        {
                           alt4 = 1;
                        }
                        switch ( alt4 )
                        {
                           case 1:
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:69:15:
                           // ( ON )? recurr_WD[weekdays, \"\"]
                           {
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:69:15:
                              // ( ON )?
                              int alt3 = 2;
                              int LA3_0 = input.LA( 1 );
                              
                              if ( ( LA3_0 == ON ) )
                              {
                                 alt3 = 1;
                              }
                              switch ( alt3 )
                              {
                                 case 1:
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:69:15:
                                 // ON
                                 {
                                    match( input,
                                           ON,
                                           FOLLOW_ON_in_parseRecurrence268 );
                                    
                                 }
                                    break;
                              
                              }
                              
                              pushFollow( FOLLOW_recurr_WD_in_parseRecurrence272 );
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
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:75:13:
                     // MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )?
                     {
                        match( input,
                               MONTHS,
                               FOLLOW_MONTHS_in_parseRecurrence324 );
                        
                        freq = RecurrencePatternParser.VAL_MONTHLY_LIT;
                        
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:76:14:
                        // ( ( ON )? r= recurr_Monthly[weekdays, ints] )?
                        int alt6 = 2;
                        int LA6_0 = input.LA( 1 );
                        
                        if ( ( ( LA6_0 >= FIFTH && LA6_0 <= FIRST )
                           || LA6_0 == FOURTH || LA6_0 == INT
                           || ( LA6_0 >= ON && LA6_0 <= OTHER )
                           || LA6_0 == SECOND || LA6_0 == THIRD ) )
                        {
                           alt6 = 1;
                        }
                        switch ( alt6 )
                        {
                           case 1:
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:76:15:
                           // ( ON )? r= recurr_Monthly[weekdays, ints]
                           {
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:76:15:
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
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:76:15:
                                 // ON
                                 {
                                    match( input,
                                           ON,
                                           FOLLOW_ON_in_parseRecurrence386 );
                                    
                                 }
                                    break;
                              
                              }
                              
                              pushFollow( FOLLOW_recurr_Monthly_in_parseRecurrence392 );
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
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:84:13:
                     // YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )?
                     {
                        match( input, YEARS, FOLLOW_YEARS_in_parseRecurrence444 );
                        
                        freq = RecurrencePatternParser.VAL_YEARLY_LIT;
                        
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:85:14:
                        // ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )?
                        int alt10 = 2;
                        int LA10_0 = input.LA( 1 );
                        
                        if ( ( ( LA10_0 >= FIFTH && LA10_0 <= FIRST )
                           || LA10_0 == FOURTH || LA10_0 == INT
                           || ( LA10_0 >= ON && LA10_0 <= OTHER )
                           || LA10_0 == SECOND || LA10_0 == THIRD ) )
                        {
                           alt10 = 1;
                        }
                        switch ( alt10 )
                        {
                           case 1:
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:85:15:
                           // ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )?
                           {
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:85:15:
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
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:85:15:
                                 // ON
                                 {
                                    match( input,
                                           ON,
                                           FOLLOW_ON_in_parseRecurrence507 );
                                    
                                 }
                                    break;
                              
                              }
                              
                              pushFollow( FOLLOW_recurr_Monthly_in_parseRecurrence513 );
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
                              
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:92:35:
                              // ( ( ON )? m= parse_Month )?
                              int alt9 = 2;
                              int LA9_0 = input.LA( 1 );
                              
                              if ( ( LA9_0 == MONTH || LA9_0 == ON ) )
                              {
                                 alt9 = 1;
                              }
                              switch ( alt9 )
                              {
                                 case 1:
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:92:37:
                                 // ( ON )? m= parse_Month
                                 {
                                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:92:37:
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
                                       // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:92:38:
                                       // ON
                                       {
                                          match( input,
                                                 ON,
                                                 FOLLOW_ON_in_parseRecurrence562 );
                                          
                                       }
                                          break;
                                    
                                    }
                                    
                                    pushFollow( FOLLOW_parse_Month_in_parseRecurrence568 );
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:101:6:
               // recurr_Xst[ints]
               {
                  pushFollow( FOLLOW_recurr_Xst_in_parseRecurrence679 );
                  recurr_Xst( ints );
                  
                  state._fsp--;
                  
                  freq = RecurrencePatternParser.VAL_MONTHLY_LIT;
                  interval = 1;
                  resolution = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
                  resolutionVal = join( ",", ints );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:108:6:
               // recurr_WD[weekdays, \"\"]
               {
                  pushFollow( FOLLOW_recurr_WD_in_parseRecurrence694 );
                  recurr_WD( weekdays, "" );
                  
                  state._fsp--;
                  
                  freq = RecurrencePatternParser.VAL_WEEKLY_LIT;
                  interval = 1;
                  resolution = RecurrencePatternParser.OP_BYDAY_LIT;
                  resolutionVal = join( ",", weekdays );
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:115:6:
               // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:115:6:
                  // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:116:9:
                  // firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"]
                  {
                     pushFollow( FOLLOW_recurr_Xst_in_parseRecurrence721 );
                     firstEntry = recurr_Xst( ints );
                     
                     state._fsp--;
                     
                     pushFollow( FOLLOW_recurr_WD_in_parseRecurrence724 );
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
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:125:4:
            // (until= UNTIL | FOR count= INT )?
            int alt13 = 3;
            int LA13_0 = input.LA( 1 );
            
            if ( ( LA13_0 == UNTIL ) )
            {
               alt13 = 1;
            }
            else if ( ( LA13_0 == FOR ) )
            {
               alt13 = 2;
            }
            switch ( alt13 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:126:9:
               // until= UNTIL
               {
                  until = (Token) match( input,
                                         UNTIL,
                                         FOLLOW_UNTIL_in_parseRecurrence764 );
                  
                  setUntil( ( until != null ? until.getText() : null ).replaceFirst( "bis\\s*",
                                                                                     "" ) );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:130:9:
               // FOR count= INT
               {
                  match( input, FOR, FOLLOW_FOR_in_parseRecurrence784 );
                  
                  count = (Token) match( input,
                                         INT,
                                         FOLLOW_INT_in_parseRecurrence788 );
                  
                  res.put( RecurrencePatternParser.OP_COUNT_LIT,
                           Integer.parseInt( ( count != null ? count.getText()
                                                            : null ) ) );
                  
               }
                  break;
            
            }
            
         }
         
         res = finishedParseRecurrence();
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      catch ( LexerException nfe )
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
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:145:1:
   // recurr_Xst[Set< Integer > res] returns [int firstEntry] : x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? ;
   public final int recurr_Xst( Set< Integer > res ) throws RecognitionException
   {
      int firstEntry = 0;
      
      int x = 0;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:146:4:
         // (x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:146:6:
         // x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )?
         {
            pushFollow( FOLLOW_parse_Xst_in_recurr_Xst851 );
            x = parse_Xst();
            
            state._fsp--;
            
            res.add( x );
            firstEntry = x;
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:147:4:
            // ( ( ( AND | COMMA ) x= parse_Xst )+ )?
            int alt15 = 2;
            int LA15_0 = input.LA( 1 );
            
            if ( ( ( LA15_0 >= AND && LA15_0 <= COMMA ) ) )
            {
               alt15 = 1;
            }
            switch ( alt15 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:147:5:
               // ( ( AND | COMMA ) x= parse_Xst )+
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:147:5:
                  // ( ( AND | COMMA ) x= parse_Xst )+
                  int cnt14 = 0;
                  loop14: do
                  {
                     int alt14 = 2;
                     int LA14_0 = input.LA( 1 );
                     
                     if ( ( ( LA14_0 >= AND && LA14_0 <= COMMA ) ) )
                     {
                        alt14 = 1;
                     }
                     
                     switch ( alt14 )
                     {
                        case 1:
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:147:6:
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
                           
                           pushFollow( FOLLOW_parse_Xst_in_recurr_Xst884 );
                           x = parse_Xst();
                           
                           state._fsp--;
                           
                           res.add( x );
                           
                        }
                           break;
                        
                        default :
                           if ( cnt14 >= 1 )
                              break loop14;
                           EarlyExitException eee = new EarlyExitException( 14,
                                                                            input );
                           throw eee;
                     }
                     cnt14++;
                  }
                  while ( true );
                  
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
         // do for sure before leaving
      }
      return firstEntry;
   }
   
   
   
   // $ANTLR end "recurr_Xst"
   
   // $ANTLR start "recurr_WD"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:150:1:
   // recurr_WD[Set< String > weekdays, String Xst] : parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA )
   // parse_Weekday[weekdays, Xst, true] )+ )? ;
   public final void recurr_WD( Set< String > weekdays, String Xst ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:151:4:
         // ( parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:151:6:
         // parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
         {
            pushFollow( FOLLOW_parse_Weekday_in_recurr_WD907 );
            parse_Weekday( weekdays, Xst, true );
            
            state._fsp--;
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:152:4:
            // ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
            int alt17 = 2;
            int LA17_0 = input.LA( 1 );
            
            if ( ( ( LA17_0 >= AND && LA17_0 <= COMMA ) ) )
            {
               alt17 = 1;
            }
            switch ( alt17 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:152:5:
               // ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:152:5:
                  // ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
                  int cnt16 = 0;
                  loop16: do
                  {
                     int alt16 = 2;
                     int LA16_0 = input.LA( 1 );
                     
                     if ( ( ( LA16_0 >= AND && LA16_0 <= COMMA ) ) )
                     {
                        alt16 = 1;
                     }
                     
                     switch ( alt16 )
                     {
                        case 1:
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:152:6:
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
                           
                           pushFollow( FOLLOW_parse_Weekday_in_recurr_WD937 );
                           parse_Weekday( weekdays, Xst, true );
                           
                           state._fsp--;
                           
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
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:155:1:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:166:4:
         // (firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:166:6:
         // firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
         {
            pushFollow( FOLLOW_recurr_Xst_in_recurr_Monthly978 );
            firstEntry = recurr_Xst( ints );
            
            state._fsp--;
            
            retval.resolution = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
            retval.resolutionVal = join( ",", ints );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:170:8:
            // ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == FRIDAY || LA19_0 == LAST || LA19_0 == MONDAY
               || LA19_0 == SATURDAY || LA19_0 == SUNDAY || LA19_0 == THURSDAY
               || LA19_0 == TUESDAY || ( LA19_0 >= WEDNESDAY && LA19_0 <= WEEKEND ) ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:171:11:
               // ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )]
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:171:11:
                  // ( LAST )?
                  int alt18 = 2;
                  int LA18_0 = input.LA( 1 );
                  
                  if ( ( LA18_0 == LAST ) )
                  {
                     alt18 = 1;
                  }
                  switch ( alt18 )
                  {
                     case 1:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:172:14:
                     // LAST
                     {
                        match( input, LAST, FOLLOW_LAST_in_recurr_Monthly1015 );
                        
                        firstEntry = -firstEntry;
                        
                     }
                        break;
                  
                  }
                  
                  pushFollow( FOLLOW_recurr_WD_in_recurr_Monthly1055 );
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
      catch ( RecognitionException re )
      {
         reportError( re );
         recover( input, re );
      }
      
      finally
      {
         // do for sure before leaving
      }
      return retval;
   }
   
   
   
   // $ANTLR end "recurr_Monthly"
   
   // $ANTLR start "parse_Xst"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:186:1:
   // parse_Xst returns [int number] : (n= INT ( DOT )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH );
   public final int parse_Xst() throws RecognitionException
   {
      int number = 0;
      
      Token n = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:187:4:
         // (n= INT ( DOT )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH )
         int alt21 = 6;
         switch ( input.LA( 1 ) )
         {
            case INT:
            {
               alt21 = 1;
            }
               break;
            case FIRST:
            {
               alt21 = 2;
            }
               break;
            case OTHER:
            case SECOND:
            {
               alt21 = 3;
            }
               break;
            case THIRD:
            {
               alt21 = 4;
            }
               break;
            case FOURTH:
            {
               alt21 = 5;
            }
               break;
            case FIFTH:
            {
               alt21 = 6;
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:187:6:
            // n= INT ( DOT )?
            {
               n = (Token) match( input, INT, FOLLOW_INT_in_parse_Xst1099 );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:187:12:
               // ( DOT )?
               int alt20 = 2;
               int LA20_0 = input.LA( 1 );
               
               if ( ( LA20_0 == DOT ) )
               {
                  alt20 = 1;
               }
               switch ( alt20 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:187:13:
                  // DOT
                  {
                     match( input, DOT, FOLLOW_DOT_in_parse_Xst1102 );
                     
                  }
                     break;
               
               }
               
               number = limitMonthDay( Integer.parseInt( ( n != null
                                                                    ? n.getText()
                                                                    : null ) ) );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:191:6:
            // FIRST
            {
               match( input, FIRST, FOLLOW_FIRST_in_parse_Xst1116 );
               
               number = 1;
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:192:6:
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:193:6:
            // THIRD
            {
               match( input, THIRD, FOLLOW_THIRD_in_parse_Xst1151 );
               
               number = 3;
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:194:6:
            // FOURTH
            {
               match( input, FOURTH, FOLLOW_FOURTH_in_parse_Xst1171 );
               
               number = 4;
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:195:6:
            // FIFTH
            {
               match( input, FIFTH, FOLLOW_FIFTH_in_parse_Xst1190 );
               
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
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:206:1:
   // parse_Interval_Number_or_Text : (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX |
   // NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN );
   public final void parse_Interval_Number_or_Text() throws RecognitionException
   {
      Token n = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:207:4:
         // (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE |
         // NUM_TEN )
         int alt22 = 11;
         switch ( input.LA( 1 ) )
         {
            case INT:
            {
               alt22 = 1;
            }
               break;
            case NUM_ONE:
            {
               alt22 = 2;
            }
               break;
            case NUM_TWO:
            {
               alt22 = 3;
            }
               break;
            case NUM_THREE:
            {
               alt22 = 4;
            }
               break;
            case NUM_FOUR:
            {
               alt22 = 5;
            }
               break;
            case NUM_FIVE:
            {
               alt22 = 6;
            }
               break;
            case NUM_SIX:
            {
               alt22 = 7;
            }
               break;
            case NUM_SEVEN:
            {
               alt22 = 8;
            }
               break;
            case NUM_EIGHT:
            {
               alt22 = 9;
            }
               break;
            case NUM_NINE:
            {
               alt22 = 10;
            }
               break;
            case NUM_TEN:
            {
               alt22 = 11;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     22,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
         }
         
         switch ( alt22 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:207:6:
            // n= INT
            {
               n = (Token) match( input,
                                  INT,
                                  FOLLOW_INT_in_parse_Interval_Number_or_Text1244 );
               
               interval = Integer.parseInt( ( n != null ? n.getText() : null ) );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:208:6:
            // NUM_ONE
            {
               match( input,
                      NUM_ONE,
                      FOLLOW_NUM_ONE_in_parse_Interval_Number_or_Text1258 );
               
               interval = 1;
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:209:6:
            // NUM_TWO
            {
               match( input,
                      NUM_TWO,
                      FOLLOW_NUM_TWO_in_parse_Interval_Number_or_Text1270 );
               
               interval = 2;
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:210:6:
            // NUM_THREE
            {
               match( input,
                      NUM_THREE,
                      FOLLOW_NUM_THREE_in_parse_Interval_Number_or_Text1282 );
               
               interval = 3;
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:211:6:
            // NUM_FOUR
            {
               match( input,
                      NUM_FOUR,
                      FOLLOW_NUM_FOUR_in_parse_Interval_Number_or_Text1292 );
               
               interval = 4;
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:212:6:
            // NUM_FIVE
            {
               match( input,
                      NUM_FIVE,
                      FOLLOW_NUM_FIVE_in_parse_Interval_Number_or_Text1303 );
               
               interval = 5;
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:213:6:
            // NUM_SIX
            {
               match( input,
                      NUM_SIX,
                      FOLLOW_NUM_SIX_in_parse_Interval_Number_or_Text1314 );
               
               interval = 6;
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:214:6:
            // NUM_SEVEN
            {
               match( input,
                      NUM_SEVEN,
                      FOLLOW_NUM_SEVEN_in_parse_Interval_Number_or_Text1326 );
               
               interval = 7;
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:215:6:
            // NUM_EIGHT
            {
               match( input,
                      NUM_EIGHT,
                      FOLLOW_NUM_EIGHT_in_parse_Interval_Number_or_Text1336 );
               
               interval = 8;
               
            }
               break;
            case 10:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:216:6:
            // NUM_NINE
            {
               match( input,
                      NUM_NINE,
                      FOLLOW_NUM_NINE_in_parse_Interval_Number_or_Text1346 );
               
               interval = 9;
               
            }
               break;
            case 11:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:217:6:
            // NUM_TEN
            {
               match( input,
                      NUM_TEN,
                      FOLLOW_NUM_TEN_in_parse_Interval_Number_or_Text1357 );
               
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
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:228:1:
   // parse_Weekday[Set< String > weekdays, String Xst, boolean strict] returns [String weekday] : ( MONDAY | TUESDAY |
   // WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT );
   public final String parse_Weekday( Set< String > weekdays,
                                      String Xst,
                                      boolean strict ) throws RecognitionException
   {
      String weekday = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:229:4:
         // ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT )
         int alt23 = 9;
         switch ( input.LA( 1 ) )
         {
            case MONDAY:
            {
               alt23 = 1;
            }
               break;
            case TUESDAY:
            {
               alt23 = 2;
            }
               break;
            case WEDNESDAY:
            {
               alt23 = 3;
            }
               break;
            case THURSDAY:
            {
               alt23 = 4;
            }
               break;
            case FRIDAY:
            {
               alt23 = 5;
            }
               break;
            case SATURDAY:
            {
               alt23 = 6;
            }
               break;
            case SUNDAY:
            {
               alt23 = 7;
            }
               break;
            case WEEKEND:
            {
               alt23 = 8;
            }
               break;
            case WEEKDAY_LIT:
            {
               alt23 = 9;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     23,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
         }
         
         switch ( alt23 )
         {
            case 1:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:229:6:
            // MONDAY
            {
               match( input, MONDAY, FOLLOW_MONDAY_in_parse_Weekday1407 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_MON );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:230:6:
            // TUESDAY
            {
               match( input, TUESDAY, FOLLOW_TUESDAY_in_parse_Weekday1421 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_TUE );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:231:6:
            // WEDNESDAY
            {
               match( input, WEDNESDAY, FOLLOW_WEDNESDAY_in_parse_Weekday1434 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_WED );
               
            }
               break;
            case 4:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:232:6:
            // THURSDAY
            {
               match( input, THURSDAY, FOLLOW_THURSDAY_in_parse_Weekday1445 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_THU );
               
            }
               break;
            case 5:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:233:6:
            // FRIDAY
            {
               match( input, FRIDAY, FOLLOW_FRIDAY_in_parse_Weekday1457 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_FRI );
               
            }
               break;
            case 6:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:234:6:
            // SATURDAY
            {
               match( input, SATURDAY, FOLLOW_SATURDAY_in_parse_Weekday1471 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT );
               
            }
               break;
            case 7:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:235:6:
            // SUNDAY
            {
               match( input, SUNDAY, FOLLOW_SUNDAY_in_parse_Weekday1483 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN );
               
            }
               break;
            case 8:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:236:6:
            // WEEKEND
            {
               match( input, WEEKEND, FOLLOW_WEEKEND_in_parse_Weekday1497 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN );
               
            }
               break;
            case 9:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:240:6:
            // WEEKDAY_LIT
            {
               match( input,
                      WEEKDAY_LIT,
                      FOLLOW_WEEKDAY_LIT_in_parse_Weekday1510 );
               
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
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:256:1:
   // parse_Month returns [int number] : m= MONTH ;
   public final int parse_Month() throws RecognitionException
   {
      int number = 0;
      
      Token m = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:257:4:
         // (m= MONTH )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:257:6:
         // m= MONTH
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_parse_Month1545 );
            
            number = textMonthToMonthNumber( ( m != null ? m.getText() : null ),
                                             Locale.GERMAN );
            
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
   
   protected DFA12 dfa12 = new DFA12( this );
   
   static final String DFA12_eotS = "\24\uffff";
   
   static final String DFA12_eofS = "\1\uffff\1\13\1\uffff\5\13\1\uffff\1\13\3\uffff\7\13";
   
   static final String DFA12_minS = "\1\7\1\5\1\uffff\5\5\1\uffff\1\5\1\12\2\uffff\7\5";
   
   static final String DFA12_maxS = "\2\60\1\uffff\5\55\1\uffff\1\55\1\46\2\uffff\7\55";
   
   static final String DFA12_acceptS = "\2\uffff\1\1\5\uffff\1\3\2\uffff\1\2\1\4\7\uffff";
   
   static final String DFA12_specialS = "\24\uffff}>";
   
   static final String[] DFA12_transitionS =
   {
    "\1\2\2\uffff\1\7\1\3\1\uffff\1\6\1\10\1\1\2\uffff\1\10\1\uffff"
       + "\1\2\1\uffff\12\2\1\uffff\1\4\1\10\1\4\1\uffff\1\10\1\5\1\10"
       + "\1\uffff\1\10\1\uffff\3\10\1\2\1\uffff\1\2",
    "\2\12\1\2\1\11\3\uffff\1\13\1\uffff\1\14\3\uffff\1\14\1\uffff"
       + "\1\2\15\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1"
       + "\13\3\14\1\2\1\uffff\1\2",
    "",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\1\22\1\16\1\uffff\1\21\1\uffff\1\15\21\uffff\1\17\1\uffff"
       + "\1\17\2\uffff\1\20",
    "",
    "",
    "\2\12\1\uffff\1\23\3\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17"
       + "\uffff\1\14\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14",
    "\2\12\5\uffff\1\13\1\uffff\1\14\3\uffff\1\14\17\uffff\1\14"
       + "\2\uffff\1\14\1\uffff\1\14\1\uffff\1\14\1\13\3\14" };
   
   static final short[] DFA12_eot = DFA.unpackEncodedString( DFA12_eotS );
   
   static final short[] DFA12_eof = DFA.unpackEncodedString( DFA12_eofS );
   
   static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars( DFA12_minS );
   
   static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars( DFA12_maxS );
   
   static final short[] DFA12_accept = DFA.unpackEncodedString( DFA12_acceptS );
   
   static final short[] DFA12_special = DFA.unpackEncodedString( DFA12_specialS );
   
   static final short[][] DFA12_transition;
   
   static
   {
      int numStates = DFA12_transitionS.length;
      DFA12_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA12_transition[ i ] = DFA.unpackEncodedString( DFA12_transitionS[ i ] );
      }
   }
   
   
   class DFA12 extends DFA
   {
      
      public DFA12( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 12;
         this.eot = DFA12_eot;
         this.eof = DFA12_eof;
         this.min = DFA12_min;
         this.max = DFA12_max;
         this.accept = DFA12_accept;
         this.special = DFA12_special;
         this.transition = DFA12_transition;
      }
      
      
      
      public String getDescription()
      {
         return "64:6: ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )";
      }
   }
   
   public static final BitSet FOLLOW_EVERY_in_parseRecurrence93 = new BitSet( new long[]
   { 0x00017AEEFFD4EC80L } );
   
   public static final BitSet FOLLOW_AFTER_in_parseRecurrence99 = new BitSet( new long[]
   { 0x00017AEEFFD4EC80L } );
   
   public static final BitSet FOLLOW_parse_Interval_Number_or_Text_in_parseRecurrence118 = new BitSet( new long[]
   { 0x0001400000100080L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseRecurrence143 = new BitSet( new long[]
   { 0x0000040000001002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseRecurrence205 = new BitSet( new long[]
   { 0x00003EA500045002L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence268 = new BitSet( new long[]
   { 0x00003AA400044000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence272 = new BitSet( new long[]
   { 0x0000040000001002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseRecurrence324 = new BitSet( new long[]
   { 0x0000044B0000BC02L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence386 = new BitSet( new long[]
   { 0x0000004A0000AC00L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence392 = new BitSet( new long[]
   { 0x0000040000001002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseRecurrence444 = new BitSet( new long[]
   { 0x0000044B0000BC02L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence507 = new BitSet( new long[]
   { 0x0000004A0000AC00L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence513 = new BitSet( new long[]
   { 0x0000040100081002L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence562 = new BitSet( new long[]
   { 0x0000000000080000L } );
   
   public static final BitSet FOLLOW_parse_Month_in_parseRecurrence568 = new BitSet( new long[]
   { 0x0000040000001002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence679 = new BitSet( new long[]
   { 0x0000040000001002L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence694 = new BitSet( new long[]
   { 0x0000040000001002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence721 = new BitSet( new long[]
   { 0x00003AA400044000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence724 = new BitSet( new long[]
   { 0x0000040000001002L } );
   
   public static final BitSet FOLLOW_UNTIL_in_parseRecurrence764 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOR_in_parseRecurrence784 = new BitSet( new long[]
   { 0x0000000000008000L } );
   
   public static final BitSet FOLLOW_INT_in_parseRecurrence788 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst851 = new BitSet( new long[]
   { 0x0000000000000062L } );
   
   public static final BitSet FOLLOW_set_in_recurr_Xst874 = new BitSet( new long[]
   { 0x0000004A0000AC00L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst884 = new BitSet( new long[]
   { 0x0000000000000062L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD907 = new BitSet( new long[]
   { 0x0000000000000062L } );
   
   public static final BitSet FOLLOW_set_in_recurr_WD929 = new BitSet( new long[]
   { 0x00003AA400044000L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD937 = new BitSet( new long[]
   { 0x0000000000000062L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_recurr_Monthly978 = new BitSet( new long[]
   { 0x00003AA400054002L } );
   
   public static final BitSet FOLLOW_LAST_in_recurr_Monthly1015 = new BitSet( new long[]
   { 0x00003AA400044000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_recurr_Monthly1055 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Xst1099 = new BitSet( new long[]
   { 0x0000000000000102L } );
   
   public static final BitSet FOLLOW_DOT_in_parse_Xst1102 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIRST_in_parse_Xst1116 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Xst1136 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THIRD_in_parse_Xst1151 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOURTH_in_parse_Xst1171 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIFTH_in_parse_Xst1190 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Interval_Number_or_Text1244 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_ONE_in_parse_Interval_Number_or_Text1258 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_TWO_in_parse_Interval_Number_or_Text1270 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_THREE_in_parse_Interval_Number_or_Text1282 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_FOUR_in_parse_Interval_Number_or_Text1292 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_FIVE_in_parse_Interval_Number_or_Text1303 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_SIX_in_parse_Interval_Number_or_Text1314 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_SEVEN_in_parse_Interval_Number_or_Text1326 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_EIGHT_in_parse_Interval_Number_or_Text1336 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_NINE_in_parse_Interval_Number_or_Text1346 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_TEN_in_parse_Interval_Number_or_Text1357 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONDAY_in_parse_Weekday1407 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TUESDAY_in_parse_Weekday1421 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEDNESDAY_in_parse_Weekday1434 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THURSDAY_in_parse_Weekday1445 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FRIDAY_in_parse_Weekday1457 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SATURDAY_in_parse_Weekday1471 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SUNDAY_in_parse_Weekday1483 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKEND_in_parse_Weekday1497 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKDAY_LIT_in_parse_Weekday1510 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_parse_Month1545 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
