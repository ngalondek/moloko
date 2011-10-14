// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g 2011-10-13 05:54:05

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
import dev.drsoran.moloko.grammar.recurrence.AbstractRecurrenceParser;
import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;


public class RecurrenceParser extends AbstractRecurrenceParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "EVERY", "AFTER", "DAYS", "WEEKS",
    "ON", "MONTHS", "YEARS", "UNTIL", "FOR", "INT", "AND", "COMMA", "LAST",
    "DOT", "FIRST", "SECOND", "OTHER", "THIRD", "FOURTH", "FIFTH", "NUM_ONE",
    "NUM_TWO", "NUM_THREE", "NUM_FOUR", "NUM_FIVE", "NUM_SIX", "NUM_SEVEN",
    "NUM_EIGHT", "NUM_NINE", "NUM_TEN", "MONDAY", "TUESDAY", "WEDNESDAY",
    "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY", "WEEKEND", "WEEKDAY_LIT",
    "MONTH", "STRING", "TIMES", "MINUS", "NUMBER", "WS" };
   
   public static final int THIRD = 21;
   
   public static final int NUM_TWO = 25;
   
   public static final int NUM_NINE = 32;
   
   public static final int WEDNESDAY = 36;
   
   public static final int FOR = 12;
   
   public static final int NUM_SIX = 29;
   
   public static final int AND = 14;
   
   public static final int EOF = -1;
   
   public static final int MONTH = 43;
   
   public static final int FRIDAY = 38;
   
   public static final int NUM_THREE = 26;
   
   public static final int COMMA = 15;
   
   public static final int NUM_ONE = 24;
   
   public static final int LAST = 16;
   
   public static final int DOT = 17;
   
   public static final int NUM_EIGHT = 31;
   
   public static final int FOURTH = 22;
   
   public static final int SECOND = 19;
   
   public static final int OTHER = 20;
   
   public static final int NUM_FOUR = 27;
   
   public static final int SATURDAY = 39;
   
   public static final int NUMBER = 47;
   
   public static final int NUM_SEVEN = 30;
   
   public static final int EVERY = 4;
   
   public static final int ON = 8;
   
   public static final int WEEKEND = 41;
   
   public static final int MONDAY = 34;
   
   public static final int SUNDAY = 40;
   
   public static final int INT = 13;
   
   public static final int MINUS = 46;
   
   public static final int AFTER = 5;
   
   public static final int YEARS = 10;
   
   public static final int NUM_FIVE = 28;
   
   public static final int FIFTH = 23;
   
   public static final int DAYS = 6;
   
   public static final int NUM_TEN = 33;
   
   public static final int WS = 48;
   
   public static final int WEEKS = 7;
   
   public static final int THURSDAY = 37;
   
   public static final int UNTIL = 11;
   
   public static final int MONTHS = 9;
   
   public static final int WEEKDAY_LIT = 42;
   
   public static final int TIMES = 45;
   
   public static final int TUESDAY = 35;
   
   public static final int STRING = 44;
   
   public static final int FIRST = 18;
   
   

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
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g";
   }
   


   public RecurrenceParser()
   {
      super( null );
   }
   
   public final static Locale LOCALE = Locale.GERMAN;
   
   

   protected String getUntilLiteral()
   {
      return "bis";
   }
   


   // $ANTLR start "parseRecurrence"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:56:1:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:65:4:
         // ( ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"]
         // )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays,
         // ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] |
         // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:65:6:
         // ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"]
         // )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays,
         // ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] |
         // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )?
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:65:6:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:65:7:
                  // EVERY
               {
                  match( input, EVERY, FOLLOW_EVERY_in_parseRecurrence93 );
                  isEvery = Boolean.TRUE;
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:65:43:
                  // AFTER
               {
                  match( input, AFTER, FOLLOW_AFTER_in_parseRecurrence99 );
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:66:6:
            // ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( (
            // ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? =>
            // ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry=
            // recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )
            int alt12 = 4;
            alt12 = dfa12.predict( input );
            switch ( alt12 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:67:9:
                  // ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS (
                  // ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints]
                  // {...}? => ( ( ON )? m= parse_Month )? )? )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:67:9:
                  // ( parse_Interval_Number_or_Text )?
                  int alt2 = 2;
                  int LA2_0 = input.LA( 1 );
                  
                  if ( ( LA2_0 == INT || ( LA2_0 >= NUM_ONE && LA2_0 <= NUM_TEN ) ) )
                  {
                     alt2 = 1;
                  }
                  switch ( alt2 )
                  {
                     case 1:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:67:9:
                        // parse_Interval_Number_or_Text
                     {
                        pushFollow( FOLLOW_parse_Interval_Number_or_Text_in_parseRecurrence118 );
                        parse_Interval_Number_or_Text();
                        
                        state._fsp--;
                        
                     }
                        break;
                     
                  }
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:68:9:
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
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:69:13:
                        // DAYS
                     {
                        match( input, DAYS, FOLLOW_DAYS_in_parseRecurrence143 );
                        freq = RecurrencePatternParser.VAL_DAILY_LIT;
                        
                     }
                        break;
                     case 2:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:70:13:
                        // WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )?
                     {
                        match( input, WEEKS, FOLLOW_WEEKS_in_parseRecurrence205 );
                        freq = RecurrencePatternParser.VAL_WEEKLY_LIT;
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:71:14:
                        // ( ( ON )? recurr_WD[weekdays, \"\"] )?
                        int alt4 = 2;
                        int LA4_0 = input.LA( 1 );
                        
                        if ( ( LA4_0 == ON || ( LA4_0 >= MONDAY && LA4_0 <= WEEKDAY_LIT ) ) )
                        {
                           alt4 = 1;
                        }
                        switch ( alt4 )
                        {
                           case 1:
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:71:15:
                              // ( ON )? recurr_WD[weekdays, \"\"]
                           {
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:71:15:
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
                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:71:15:
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
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:77:13:
                        // MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )?
                     {
                        match( input,
                               MONTHS,
                               FOLLOW_MONTHS_in_parseRecurrence324 );
                        freq = RecurrencePatternParser.VAL_MONTHLY_LIT;
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:78:14:
                        // ( ( ON )? r= recurr_Monthly[weekdays, ints] )?
                        int alt6 = 2;
                        int LA6_0 = input.LA( 1 );
                        
                        if ( ( LA6_0 == ON || LA6_0 == INT || ( LA6_0 >= FIRST && LA6_0 <= FIFTH ) ) )
                        {
                           alt6 = 1;
                        }
                        switch ( alt6 )
                        {
                           case 1:
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:78:15:
                              // ( ON )? r= recurr_Monthly[weekdays, ints]
                           {
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:78:15:
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
                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:78:15:
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
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:86:13:
                        // YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )?
                     {
                        match( input, YEARS, FOLLOW_YEARS_in_parseRecurrence444 );
                        freq = RecurrencePatternParser.VAL_YEARLY_LIT;
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:87:14:
                        // ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )?
                        int alt10 = 2;
                        int LA10_0 = input.LA( 1 );
                        
                        if ( ( LA10_0 == ON || LA10_0 == INT || ( LA10_0 >= FIRST && LA10_0 <= FIFTH ) ) )
                        {
                           alt10 = 1;
                        }
                        switch ( alt10 )
                        {
                           case 1:
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:87:15:
                              // ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )?
                           {
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:87:15:
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
                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:87:15:
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
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:94:35:
                              // ( ( ON )? m= parse_Month )?
                              int alt9 = 2;
                              int LA9_0 = input.LA( 1 );
                              
                              if ( ( LA9_0 == ON || LA9_0 == MONTH ) )
                              {
                                 alt9 = 1;
                              }
                              switch ( alt9 )
                              {
                                 case 1:
                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:94:37:
                                    // ( ON )? m= parse_Month
                                 {
                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:94:37:
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
                                          // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:94:38:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:103:6:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:110:6:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:117:6:
                  // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:117:6:
                  // (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:118:9:
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
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:127:4:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:128:9:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:132:9:
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
      catch ( RecognitionException e )
      {
         
         throw e;
         
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
      }
      return res;
   }
   


   // $ANTLR end "parseRecurrence"
   
   // $ANTLR start "recurr_Xst"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:151:1:
   // recurr_Xst[Set< Integer > res] returns [int firstEntry] : x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? ;
   public final int recurr_Xst( Set< Integer > res ) throws RecognitionException
   {
      int firstEntry = 0;
      
      int x = 0;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:152:4:
         // (x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:152:6:
         // x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )?
         {
            pushFollow( FOLLOW_parse_Xst_in_recurr_Xst863 );
            x = parse_Xst();
            
            state._fsp--;
            
            res.add( x );
            firstEntry = x;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:153:4:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:153:5:
                  // ( ( AND | COMMA ) x= parse_Xst )+
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:153:5:
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
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:153:6:
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
                           
                           pushFollow( FOLLOW_parse_Xst_in_recurr_Xst896 );
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:160:1:
   // recurr_WD[Set< String > weekdays, String Xst] : parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA )
   // parse_Weekday[weekdays, Xst, true] )+ )? ;
   public final void recurr_WD( Set< String > weekdays, String Xst ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:161:4:
         // ( parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:161:6:
         // parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
         {
            pushFollow( FOLLOW_parse_Weekday_in_recurr_WD931 );
            parse_Weekday( weekdays, Xst, true );
            
            state._fsp--;
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:162:4:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:162:5:
                  // ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:162:5:
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
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:162:6:
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
                           
                           pushFollow( FOLLOW_parse_Weekday_in_recurr_WD961 );
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:169:1:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:180:4:
         // (firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:180:6:
         // firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
         {
            pushFollow( FOLLOW_recurr_Xst_in_recurr_Monthly1014 );
            firstEntry = recurr_Xst( ints );
            
            state._fsp--;
            
            retval.resolution = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
            retval.resolutionVal = join( ",", ints );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:184:8:
            // ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == LAST || ( LA19_0 >= MONDAY && LA19_0 <= WEEKDAY_LIT ) ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:185:11:
                  // ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )]
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:185:11:
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
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:186:14:
                        // LAST
                     {
                        match( input, LAST, FOLLOW_LAST_in_recurr_Monthly1051 );
                        
                        firstEntry = -firstEntry;
                        
                     }
                        break;
                     
                  }
                  
                  pushFollow( FOLLOW_recurr_WD_in_recurr_Monthly1091 );
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
      }
      return retval;
   }
   


   // $ANTLR end "recurr_Monthly"
   
   // $ANTLR start "parse_Xst"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:204:1:
   // parse_Xst returns [int number] : (n= INT ( DOT )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH );
   public final int parse_Xst() throws RecognitionException
   {
      int number = 0;
      
      Token n = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:205:4:
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
            case SECOND:
            case OTHER:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:205:6:
               // n= INT ( DOT )?
            {
               n = (Token) match( input, INT, FOLLOW_INT_in_parse_Xst1147 );
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:205:12:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:205:13:
                     // DOT
                  {
                     match( input, DOT, FOLLOW_DOT_in_parse_Xst1150 );
                     
                  }
                     break;
                  
               }
               
               number = limitMonthDay( Integer.parseInt( ( n != null
                                                                    ? n.getText()
                                                                    : null ) ) );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:209:6:
               // FIRST
            {
               match( input, FIRST, FOLLOW_FIRST_in_parse_Xst1164 );
               number = 1;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:210:6:
               // ( SECOND | OTHER )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:211:6:
               // THIRD
            {
               match( input, THIRD, FOLLOW_THIRD_in_parse_Xst1199 );
               number = 3;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:212:6:
               // FOURTH
            {
               match( input, FOURTH, FOLLOW_FOURTH_in_parse_Xst1219 );
               number = 4;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:213:6:
               // FIFTH
            {
               match( input, FIFTH, FOLLOW_FIFTH_in_parse_Xst1238 );
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
   
   // $ANTLR start "parse_Interval_Number_or_Text"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:224:1:
   // parse_Interval_Number_or_Text : (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX |
   // NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN );
   public final void parse_Interval_Number_or_Text() throws RecognitionException
   {
      Token n = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:225:4:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:225:6:
               // n= INT
            {
               n = (Token) match( input,
                                  INT,
                                  FOLLOW_INT_in_parse_Interval_Number_or_Text1292 );
               interval = Integer.parseInt( ( n != null ? n.getText() : null ) );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:226:6:
               // NUM_ONE
            {
               match( input,
                      NUM_ONE,
                      FOLLOW_NUM_ONE_in_parse_Interval_Number_or_Text1306 );
               interval = 1;
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:227:6:
               // NUM_TWO
            {
               match( input,
                      NUM_TWO,
                      FOLLOW_NUM_TWO_in_parse_Interval_Number_or_Text1318 );
               interval = 2;
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:228:6:
               // NUM_THREE
            {
               match( input,
                      NUM_THREE,
                      FOLLOW_NUM_THREE_in_parse_Interval_Number_or_Text1330 );
               interval = 3;
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:229:6:
               // NUM_FOUR
            {
               match( input,
                      NUM_FOUR,
                      FOLLOW_NUM_FOUR_in_parse_Interval_Number_or_Text1340 );
               interval = 4;
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:230:6:
               // NUM_FIVE
            {
               match( input,
                      NUM_FIVE,
                      FOLLOW_NUM_FIVE_in_parse_Interval_Number_or_Text1351 );
               interval = 5;
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:231:6:
               // NUM_SIX
            {
               match( input,
                      NUM_SIX,
                      FOLLOW_NUM_SIX_in_parse_Interval_Number_or_Text1362 );
               interval = 6;
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:232:6:
               // NUM_SEVEN
            {
               match( input,
                      NUM_SEVEN,
                      FOLLOW_NUM_SEVEN_in_parse_Interval_Number_or_Text1374 );
               interval = 7;
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:233:6:
               // NUM_EIGHT
            {
               match( input,
                      NUM_EIGHT,
                      FOLLOW_NUM_EIGHT_in_parse_Interval_Number_or_Text1384 );
               interval = 8;
               
            }
               break;
            case 10:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:234:6:
               // NUM_NINE
            {
               match( input,
                      NUM_NINE,
                      FOLLOW_NUM_NINE_in_parse_Interval_Number_or_Text1394 );
               interval = 9;
               
            }
               break;
            case 11:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:235:6:
               // NUM_TEN
            {
               match( input,
                      NUM_TEN,
                      FOLLOW_NUM_TEN_in_parse_Interval_Number_or_Text1405 );
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
      }
      return;
   }
   


   // $ANTLR end "parse_Interval_Number_or_Text"
   
   // $ANTLR start "parse_Weekday"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:246:1:
   // parse_Weekday[Set< String > weekdays, String Xst, boolean strict] returns [String weekday] : ( MONDAY | TUESDAY |
   // WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT );
   public final String parse_Weekday( Set< String > weekdays,
                                      String Xst,
                                      boolean strict ) throws RecognitionException
   {
      String weekday = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:247:4:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:247:6:
               // MONDAY
            {
               match( input, MONDAY, FOLLOW_MONDAY_in_parse_Weekday1455 );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_MON );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:248:6:
               // TUESDAY
            {
               match( input, TUESDAY, FOLLOW_TUESDAY_in_parse_Weekday1469 );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_TUE );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:249:6:
               // WEDNESDAY
            {
               match( input, WEDNESDAY, FOLLOW_WEDNESDAY_in_parse_Weekday1482 );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_WED );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:250:6:
               // THURSDAY
            {
               match( input, THURSDAY, FOLLOW_THURSDAY_in_parse_Weekday1493 );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_THU );
               
            }
               break;
            case 5:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:251:6:
               // FRIDAY
            {
               match( input, FRIDAY, FOLLOW_FRIDAY_in_parse_Weekday1505 );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_FRI );
               
            }
               break;
            case 6:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:252:6:
               // SATURDAY
            {
               match( input, SATURDAY, FOLLOW_SATURDAY_in_parse_Weekday1519 );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT );
               
            }
               break;
            case 7:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:253:6:
               // SUNDAY
            {
               match( input, SUNDAY, FOLLOW_SUNDAY_in_parse_Weekday1531 );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN );
               
            }
               break;
            case 8:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:254:6:
               // WEEKEND
            {
               match( input, WEEKEND, FOLLOW_WEEKEND_in_parse_Weekday1545 );
               
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT );
               weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN );
               
            }
               break;
            case 9:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:258:6:
               // WEEKDAY_LIT
            {
               match( input,
                      WEEKDAY_LIT,
                      FOLLOW_WEEKDAY_LIT_in_parse_Weekday1558 );
               
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
      }
      return weekday;
   }
   


   // $ANTLR end "parse_Weekday"
   
   // $ANTLR start "parse_Month"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:274:1:
   // parse_Month returns [int number] : m= MONTH ;
   public final int parse_Month() throws RecognitionException
   {
      int number = 0;
      
      Token m = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:275:4:
         // (m= MONTH )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\de\\Recurrence.g:275:6:
         // m= MONTH
         {
            m = (Token) match( input, MONTH, FOLLOW_MONTH_in_parse_Month1593 );
            
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
      }
      return number;
   }
   
   // $ANTLR end "parse_Month"
   
   // Delegated rules
   
   protected DFA12 dfa12 = new DFA12( this );
   
   static final String DFA12_eotS = "\24\uffff";
   
   static final String DFA12_eofS = "\1\uffff\1\13\1\uffff\5\13\1\uffff\1\13\3\uffff\7\13";
   
   static final String DFA12_minS = "\2\6\1\uffff\5\13\1\uffff\1\13\1\15\2\uffff\7\13";
   
   static final String DFA12_maxS = "\2\52\1\uffff\5\52\1\uffff\1\52\1\27\2\uffff\7\52";
   
   static final String DFA12_acceptS = "\2\uffff\1\1\5\uffff\1\3\2\uffff\1\2\1\4\7\uffff";
   
   static final String DFA12_specialS = "\24\uffff}>";
   
   static final String[] DFA12_transitionS =
   {
    "\2\2\1\uffff\2\2\2\uffff\1\1\4\uffff\1\3\2\4\1\5\1\6\1\7\12" + "\2\11\10",
    "\2\2\1\uffff\2\2\2\13\1\uffff\2\12\1\uffff\1\11\20\uffff\11" + "\14", "",
    "\2\13\1\uffff\2\12\22\uffff\11\14", "\2\13\1\uffff\2\12\22\uffff\11\14",
    "\2\13\1\uffff\2\12\22\uffff\11\14", "\2\13\1\uffff\2\12\22\uffff\11\14",
    "\2\13\1\uffff\2\12\22\uffff\11\14", "",
    "\2\13\1\uffff\2\12\22\uffff\11\14",
    "\1\15\4\uffff\1\16\2\17\1\20\1\21\1\22", "", "",
    "\2\13\1\uffff\2\12\1\uffff\1\23\20\uffff\11\14",
    "\2\13\1\uffff\2\12\22\uffff\11\14", "\2\13\1\uffff\2\12\22\uffff\11\14",
    "\2\13\1\uffff\2\12\22\uffff\11\14", "\2\13\1\uffff\2\12\22\uffff\11\14",
    "\2\13\1\uffff\2\12\22\uffff\11\14", "\2\13\1\uffff\2\12\22\uffff\11\14" };
   
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
         return "66:6: ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )";
      }
   }
   
   public static final BitSet FOLLOW_EVERY_in_parseRecurrence93 = new BitSet( new long[]
   { 0x000007FFFFFC26C0L } );
   
   public static final BitSet FOLLOW_AFTER_in_parseRecurrence99 = new BitSet( new long[]
   { 0x000007FFFFFC26C0L } );
   
   public static final BitSet FOLLOW_parse_Interval_Number_or_Text_in_parseRecurrence118 = new BitSet( new long[]
   { 0x00000000000006C0L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseRecurrence143 = new BitSet( new long[]
   { 0x0000000000001802L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseRecurrence205 = new BitSet( new long[]
   { 0x000007FC00001902L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence268 = new BitSet( new long[]
   { 0x000007FC00000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence272 = new BitSet( new long[]
   { 0x0000000000001802L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseRecurrence324 = new BitSet( new long[]
   { 0x0000000000FC3902L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence386 = new BitSet( new long[]
   { 0x0000000000FC2100L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence392 = new BitSet( new long[]
   { 0x0000000000001802L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseRecurrence444 = new BitSet( new long[]
   { 0x0000000000FC3902L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence507 = new BitSet( new long[]
   { 0x0000000000FC2100L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence513 = new BitSet( new long[]
   { 0x0000080000001902L } );
   
   public static final BitSet FOLLOW_ON_in_parseRecurrence562 = new BitSet( new long[]
   { 0x0000080000000100L } );
   
   public static final BitSet FOLLOW_parse_Month_in_parseRecurrence568 = new BitSet( new long[]
   { 0x0000000000001802L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence679 = new BitSet( new long[]
   { 0x0000000000001802L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence694 = new BitSet( new long[]
   { 0x0000000000001802L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence721 = new BitSet( new long[]
   { 0x000007FC00000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence724 = new BitSet( new long[]
   { 0x0000000000001802L } );
   
   public static final BitSet FOLLOW_UNTIL_in_parseRecurrence764 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOR_in_parseRecurrence784 = new BitSet( new long[]
   { 0x0000000000002000L } );
   
   public static final BitSet FOLLOW_INT_in_parseRecurrence788 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst863 = new BitSet( new long[]
   { 0x000000000000C002L } );
   
   public static final BitSet FOLLOW_set_in_recurr_Xst886 = new BitSet( new long[]
   { 0x0000000000FC2000L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst896 = new BitSet( new long[]
   { 0x000000000000C002L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD931 = new BitSet( new long[]
   { 0x000000000000C002L } );
   
   public static final BitSet FOLLOW_set_in_recurr_WD953 = new BitSet( new long[]
   { 0x000007FC00000000L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD961 = new BitSet( new long[]
   { 0x000000000000C002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_recurr_Monthly1014 = new BitSet( new long[]
   { 0x000007FC00010002L } );
   
   public static final BitSet FOLLOW_LAST_in_recurr_Monthly1051 = new BitSet( new long[]
   { 0x000007FC00000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_recurr_Monthly1091 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Xst1147 = new BitSet( new long[]
   { 0x0000000000020002L } );
   
   public static final BitSet FOLLOW_DOT_in_parse_Xst1150 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIRST_in_parse_Xst1164 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Xst1184 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THIRD_in_parse_Xst1199 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOURTH_in_parse_Xst1219 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIFTH_in_parse_Xst1238 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Interval_Number_or_Text1292 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_ONE_in_parse_Interval_Number_or_Text1306 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_TWO_in_parse_Interval_Number_or_Text1318 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_THREE_in_parse_Interval_Number_or_Text1330 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_FOUR_in_parse_Interval_Number_or_Text1340 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_FIVE_in_parse_Interval_Number_or_Text1351 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_SIX_in_parse_Interval_Number_or_Text1362 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_SEVEN_in_parse_Interval_Number_or_Text1374 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_EIGHT_in_parse_Interval_Number_or_Text1384 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_NINE_in_parse_Interval_Number_or_Text1394 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NUM_TEN_in_parse_Interval_Number_or_Text1405 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONDAY_in_parse_Weekday1455 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TUESDAY_in_parse_Weekday1469 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEDNESDAY_in_parse_Weekday1482 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THURSDAY_in_parse_Weekday1493 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FRIDAY_in_parse_Weekday1505 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SATURDAY_in_parse_Weekday1519 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SUNDAY_in_parse_Weekday1531 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKEND_in_parse_Weekday1545 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKDAY_LIT_in_parse_Weekday1558 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_parse_Month1593 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
