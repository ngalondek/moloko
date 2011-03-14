// $ANTLR 3.3 Nov 30, 2010 12:45:30 F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g 2011-02-24 15:19:56

package dev.drsoran.moloko.grammar;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;


public class RecurrenceAutoComplParser extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "EVERY", "AFTER", "DAYS", "WEEKS",
    "BIWEEKLY", "ON", "THE", "MONTHS", "YEARS", "IN", "OF", "UNTIL", "FOR",
    "INT", "AND", "COMMA", "LAST", "DOT", "ST_S", "FIRST", "SECOND", "OTHER",
    "THIRD", "FOURTH", "FIFTH", "NUM_ONE", "NUM_TWO", "NUM_THREE", "NUM_FOUR",
    "NUM_FIVE", "NUM_SIX", "NUM_SEVEN", "NUM_EIGHT", "NUM_NINE", "NUM_TEN",
    "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY",
    "SUNDAY", "WEEKEND", "WEEKDAY_LIT", "MONTH" };
   
   public static final int EOF = -1;
   
   public static final int EVERY = 4;
   
   public static final int AFTER = 5;
   
   public static final int DAYS = 6;
   
   public static final int WEEKS = 7;
   
   public static final int BIWEEKLY = 8;
   
   public static final int ON = 9;
   
   public static final int THE = 10;
   
   public static final int MONTHS = 11;
   
   public static final int YEARS = 12;
   
   public static final int IN = 13;
   
   public static final int OF = 14;
   
   public static final int UNTIL = 15;
   
   public static final int FOR = 16;
   
   public static final int INT = 17;
   
   public static final int AND = 18;
   
   public static final int COMMA = 19;
   
   public static final int LAST = 20;
   
   public static final int DOT = 21;
   
   public static final int ST_S = 22;
   
   public static final int FIRST = 23;
   
   public static final int SECOND = 24;
   
   public static final int OTHER = 25;
   
   public static final int THIRD = 26;
   
   public static final int FOURTH = 27;
   
   public static final int FIFTH = 28;
   
   public static final int NUM_ONE = 29;
   
   public static final int NUM_TWO = 30;
   
   public static final int NUM_THREE = 31;
   
   public static final int NUM_FOUR = 32;
   
   public static final int NUM_FIVE = 33;
   
   public static final int NUM_SIX = 34;
   
   public static final int NUM_SEVEN = 35;
   
   public static final int NUM_EIGHT = 36;
   
   public static final int NUM_NINE = 37;
   
   public static final int NUM_TEN = 38;
   
   public static final int MONDAY = 39;
   
   public static final int TUESDAY = 40;
   
   public static final int WEDNESDAY = 41;
   
   public static final int THURSDAY = 42;
   
   public static final int FRIDAY = 43;
   
   public static final int SATURDAY = 44;
   
   public static final int SUNDAY = 45;
   
   public static final int WEEKEND = 46;
   
   public static final int WEEKDAY_LIT = 47;
   
   public static final int MONTH = 48;
   
   

   // delegates
   // delegators
   
   public RecurrenceAutoComplParser( TokenStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public RecurrenceAutoComplParser( TokenStream input,
      RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   public String[] getTokenNames()
   {
      return RecurrenceAutoComplParser.tokenNames;
   }
   


   public String getGrammarFileName()
   {
      return "F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g";
   }
   


   public RecurrenceAutoComplParser()
   {
      super( null );
   }
   
   
   public enum Sugg
   {
      EVERY, AFTER, NUMBERS, XST, WEEKDAYS, DAY, DAYS, WEEK, WEEKS, BIWEEKLY,
         MONTH, MOTHS, YEAR, YEARS, UNTIL, FOR
   }
   
   public final static Sugg[][] NEXT_VALID_INPUT =
   {
   { Sugg.EVERY, Sugg.AFTER, Sugg.BIWEEKLY },
   { Sugg.NUMBERS, Sugg.XST, Sugg.WEEKDAYS } };
   
   public int suggState = 0;
   
   

   // $ANTLR start "parseInput"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:44:1: parseInput : ( (
   // EVERY | AFTER ) )? ( ( parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD )? | MONTHS ( (
   // ON )? ( THE )? recurr_Monthly )? | YEARS ( ( ON )? ( THE )? hasWd= recurr_Monthly {...}? => ( ( IN | OF )?
   // parse_Month )? )? ) | recurr_Xst | recurr_WD | recurr_Xst recurr_WD ) ( UNTIL | FOR INT )? ;
   public final void parseInput() throws RecognitionException
   {
      boolean hasWd = false;
      
      suggState = 0;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:49:4: ( ( ( EVERY |
         // AFTER ) )? ( ( parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD )? | MONTHS ( ( ON
         // )? ( THE )? recurr_Monthly )? | YEARS ( ( ON )? ( THE )? hasWd= recurr_Monthly {...}? => ( ( IN | OF )?
         // parse_Month )? )? ) | recurr_Xst | recurr_WD | recurr_Xst recurr_WD ) ( UNTIL | FOR INT )? )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:50:4: ( ( EVERY |
         // AFTER ) )? ( ( parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD )? | MONTHS ( ( ON
         // )? ( THE )? recurr_Monthly )? | YEARS ( ( ON )? ( THE )? hasWd= recurr_Monthly {...}? => ( ( IN | OF )?
         // parse_Month )? )? ) | recurr_Xst | recurr_WD | recurr_Xst recurr_WD ) ( UNTIL | FOR INT )?
         {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:50:4: ( ( EVERY
            // | AFTER ) )?
            int alt1 = 2;
            int LA1_0 = input.LA( 1 );
            
            if ( ( ( LA1_0 >= EVERY && LA1_0 <= AFTER ) ) )
            {
               alt1 = 1;
            }
            switch ( alt1 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:50:5: (
                  // EVERY | AFTER )
               {
                  if ( ( input.LA( 1 ) >= EVERY && input.LA( 1 ) <= AFTER ) )
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
                  
                  suggState = 1;
                  
               }
                  break;
               
            }
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:51:4: ( (
            // parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD )? | MONTHS ( ( ON )? ( THE )?
            // recurr_Monthly )? | YEARS ( ( ON )? ( THE )? hasWd= recurr_Monthly {...}? => ( ( IN | OF )? parse_Month
            // )? )? ) | recurr_Xst | recurr_WD | recurr_Xst recurr_WD )
            int alt15 = 4;
            alt15 = dfa15.predict( input );
            switch ( alt15 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:51:10: (
                  // parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD )? | MONTHS ( ( ON )? (
                  // THE )? recurr_Monthly )? | YEARS ( ( ON )? ( THE )? hasWd= recurr_Monthly {...}? => ( ( IN | OF )?
                  // parse_Month )? )? )
               {
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:51:10: (
                  // parse_Number )?
                  int alt2 = 2;
                  int LA2_0 = input.LA( 1 );
                  
                  if ( ( LA2_0 == INT || ( LA2_0 >= NUM_ONE && LA2_0 <= NUM_TEN ) ) )
                  {
                     alt2 = 1;
                  }
                  switch ( alt2 )
                  {
                     case 1:
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:51:10:
                        // parse_Number
                     {
                        pushFollow( FOLLOW_parse_Number_in_parseInput173 );
                        parse_Number();
                        
                        state._fsp--;
                        
                     }
                        break;
                     
                  }
                  
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:52:10: (
                  // DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD )? | MONTHS ( ( ON )? ( THE )?
                  // recurr_Monthly )? | YEARS ( ( ON )? ( THE )? hasWd= recurr_Monthly {...}? => ( ( IN | OF )?
                  // parse_Month )? )? )
                  int alt14 = 4;
                  switch ( input.LA( 1 ) )
                  {
                     case DAYS:
                     {
                        alt14 = 1;
                     }
                        break;
                     case WEEKS:
                     case BIWEEKLY:
                     {
                        alt14 = 2;
                     }
                        break;
                     case MONTHS:
                     {
                        alt14 = 3;
                     }
                        break;
                     case YEARS:
                     {
                        alt14 = 4;
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
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:52:17:
                        // DAYS
                     {
                        match( input, DAYS, FOLLOW_DAYS_in_parseInput193 );
                        
                     }
                        break;
                     case 2:
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:53:16:
                        // ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD )?
                     {
                        if ( ( input.LA( 1 ) >= WEEKS && input.LA( 1 ) <= BIWEEKLY ) )
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
                        
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:53:35:
                        // ( ( ON )? ( THE )? recurr_WD )?
                        int alt5 = 2;
                        int LA5_0 = input.LA( 1 );
                        
                        if ( ( ( LA5_0 >= ON && LA5_0 <= THE ) || ( LA5_0 >= MONDAY && LA5_0 <= WEEKDAY_LIT ) ) )
                        {
                           alt5 = 1;
                        }
                        switch ( alt5 )
                        {
                           case 1:
                              // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:53:36:
                              // ( ON )? ( THE )? recurr_WD
                           {
                              // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:53:36:
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
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:53:36:
                                    // ON
                                 {
                                    match( input,
                                           ON,
                                           FOLLOW_ON_in_parseInput219 );
                                    
                                 }
                                    break;
                                 
                              }
                              
                              // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:53:40:
                              // ( THE )?
                              int alt4 = 2;
                              int LA4_0 = input.LA( 1 );
                              
                              if ( ( LA4_0 == THE ) )
                              {
                                 alt4 = 1;
                              }
                              switch ( alt4 )
                              {
                                 case 1:
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:53:40:
                                    // THE
                                 {
                                    match( input,
                                           THE,
                                           FOLLOW_THE_in_parseInput222 );
                                    
                                 }
                                    break;
                                 
                              }
                              
                              pushFollow( FOLLOW_recurr_WD_in_parseInput225 );
                              recurr_WD();
                              
                              state._fsp--;
                              
                           }
                              break;
                           
                        }
                        
                     }
                        break;
                     case 3:
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:54:16:
                        // MONTHS ( ( ON )? ( THE )? recurr_Monthly )?
                     {
                        match( input, MONTHS, FOLLOW_MONTHS_in_parseInput244 );
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:54:35:
                        // ( ( ON )? ( THE )? recurr_Monthly )?
                        int alt8 = 2;
                        int LA8_0 = input.LA( 1 );
                        
                        if ( ( ( LA8_0 >= ON && LA8_0 <= THE ) || LA8_0 == INT || ( LA8_0 >= FIRST && LA8_0 <= FIFTH ) ) )
                        {
                           alt8 = 1;
                        }
                        switch ( alt8 )
                        {
                           case 1:
                              // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:54:36:
                              // ( ON )? ( THE )? recurr_Monthly
                           {
                              // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:54:36:
                              // ( ON )?
                              int alt6 = 2;
                              int LA6_0 = input.LA( 1 );
                              
                              if ( ( LA6_0 == ON ) )
                              {
                                 alt6 = 1;
                              }
                              switch ( alt6 )
                              {
                                 case 1:
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:54:36:
                                    // ON
                                 {
                                    match( input,
                                           ON,
                                           FOLLOW_ON_in_parseInput259 );
                                    
                                 }
                                    break;
                                 
                              }
                              
                              // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:54:40:
                              // ( THE )?
                              int alt7 = 2;
                              int LA7_0 = input.LA( 1 );
                              
                              if ( ( LA7_0 == THE ) )
                              {
                                 alt7 = 1;
                              }
                              switch ( alt7 )
                              {
                                 case 1:
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:54:40:
                                    // THE
                                 {
                                    match( input,
                                           THE,
                                           FOLLOW_THE_in_parseInput262 );
                                    
                                 }
                                    break;
                                 
                              }
                              
                              pushFollow( FOLLOW_recurr_Monthly_in_parseInput265 );
                              recurr_Monthly();
                              
                              state._fsp--;
                              
                           }
                              break;
                           
                        }
                        
                     }
                        break;
                     case 4:
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:55:16:
                        // YEARS ( ( ON )? ( THE )? hasWd= recurr_Monthly {...}? => ( ( IN | OF )? parse_Month )? )?
                     {
                        match( input, YEARS, FOLLOW_YEARS_in_parseInput284 );
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:55:35:
                        // ( ( ON )? ( THE )? hasWd= recurr_Monthly {...}? => ( ( IN | OF )? parse_Month )? )?
                        int alt13 = 2;
                        int LA13_0 = input.LA( 1 );
                        
                        if ( ( ( LA13_0 >= ON && LA13_0 <= THE )
                           || LA13_0 == INT || ( LA13_0 >= FIRST && LA13_0 <= FIFTH ) ) )
                        {
                           alt13 = 1;
                        }
                        switch ( alt13 )
                        {
                           case 1:
                              // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:55:36:
                              // ( ON )? ( THE )? hasWd= recurr_Monthly {...}? => ( ( IN | OF )? parse_Month )?
                           {
                              // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:55:36:
                              // ( ON )?
                              int alt9 = 2;
                              int LA9_0 = input.LA( 1 );
                              
                              if ( ( LA9_0 == ON ) )
                              {
                                 alt9 = 1;
                              }
                              switch ( alt9 )
                              {
                                 case 1:
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:55:36:
                                    // ON
                                 {
                                    match( input,
                                           ON,
                                           FOLLOW_ON_in_parseInput300 );
                                    
                                 }
                                    break;
                                 
                              }
                              
                              // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:55:40:
                              // ( THE )?
                              int alt10 = 2;
                              int LA10_0 = input.LA( 1 );
                              
                              if ( ( LA10_0 == THE ) )
                              {
                                 alt10 = 1;
                              }
                              switch ( alt10 )
                              {
                                 case 1:
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:55:40:
                                    // THE
                                 {
                                    match( input,
                                           THE,
                                           FOLLOW_THE_in_parseInput303 );
                                    
                                 }
                                    break;
                                 
                              }
                              
                              pushFollow( FOLLOW_recurr_Monthly_in_parseInput310 );
                              hasWd = recurr_Monthly();
                              
                              state._fsp--;
                              
                              if ( !( ( hasWd ) ) )
                              {
                                 throw new FailedPredicateException( input,
                                                                     "parseInput",
                                                                     " hasWd " );
                              }
                              // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:55:82:
                              // ( ( IN | OF )? parse_Month )?
                              int alt12 = 2;
                              int LA12_0 = input.LA( 1 );
                              
                              if ( ( ( LA12_0 >= IN && LA12_0 <= OF ) || LA12_0 == MONTH ) )
                              {
                                 alt12 = 1;
                              }
                              switch ( alt12 )
                              {
                                 case 1:
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:55:83:
                                    // ( IN | OF )? parse_Month
                                 {
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:55:83:
                                    // ( IN | OF )?
                                    int alt11 = 2;
                                    int LA11_0 = input.LA( 1 );
                                    
                                    if ( ( ( LA11_0 >= IN && LA11_0 <= OF ) ) )
                                    {
                                       alt11 = 1;
                                    }
                                    switch ( alt11 )
                                    {
                                       case 1:
                                          // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:
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
                                    
                                    pushFollow( FOLLOW_parse_Month_in_parseInput326 );
                                    parse_Month();
                                    
                                    state._fsp--;
                                    
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
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:57:10:
                  // recurr_Xst
               {
                  pushFollow( FOLLOW_recurr_Xst_in_parseInput352 );
                  recurr_Xst();
                  
                  state._fsp--;
                  
               }
                  break;
               case 3:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:58:10:
                  // recurr_WD
               {
                  pushFollow( FOLLOW_recurr_WD_in_parseInput363 );
                  recurr_WD();
                  
                  state._fsp--;
                  
               }
                  break;
               case 4:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:59:10:
                  // recurr_Xst recurr_WD
               {
                  pushFollow( FOLLOW_recurr_Xst_in_parseInput374 );
                  recurr_Xst();
                  
                  state._fsp--;
                  
                  pushFollow( FOLLOW_recurr_WD_in_parseInput376 );
                  recurr_WD();
                  
                  state._fsp--;
                  
               }
                  break;
               
            }
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:61:4: ( UNTIL |
            // FOR INT )?
            int alt16 = 3;
            int LA16_0 = input.LA( 1 );
            
            if ( ( LA16_0 == UNTIL ) )
            {
               alt16 = 1;
            }
            else if ( ( LA16_0 == FOR ) )
            {
               alt16 = 2;
            }
            switch ( alt16 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:61:9:
                  // UNTIL
               {
                  match( input, UNTIL, FOLLOW_UNTIL_in_parseInput391 );
                  
               }
                  break;
               case 2:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:62:9: FOR
                  // INT
               {
                  match( input, FOR, FOLLOW_FOR_in_parseInput401 );
                  match( input, INT, FOLLOW_INT_in_parseInput403 );
                  
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
   


   // $ANTLR end "parseInput"
   
   // $ANTLR start "recurr_Xst"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:70:1: recurr_Xst : x=
   // parse_Xst ( ( ( AND | COMMA ) parse_Xst )+ )? ;
   public final void recurr_Xst() throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:71:4: (x= parse_Xst
         // ( ( ( AND | COMMA ) parse_Xst )+ )? )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:71:6: x= parse_Xst
         // ( ( ( AND | COMMA ) parse_Xst )+ )?
         {
            pushFollow( FOLLOW_parse_Xst_in_recurr_Xst438 );
            parse_Xst();
            
            state._fsp--;
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:72:4: ( ( ( AND
            // | COMMA ) parse_Xst )+ )?
            int alt18 = 2;
            int LA18_0 = input.LA( 1 );
            
            if ( ( ( LA18_0 >= AND && LA18_0 <= COMMA ) ) )
            {
               alt18 = 1;
            }
            switch ( alt18 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:72:5: ( (
                  // AND | COMMA ) parse_Xst )+
               {
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:72:5: ( (
                  // AND | COMMA ) parse_Xst )+
                  int cnt17 = 0;
                  loop17: do
                  {
                     int alt17 = 2;
                     int LA17_0 = input.LA( 1 );
                     
                     if ( ( ( LA17_0 >= AND && LA17_0 <= COMMA ) ) )
                     {
                        alt17 = 1;
                     }
                     
                     switch ( alt17 )
                     {
                        case 1:
                           // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:72:6:
                           // ( AND | COMMA ) parse_Xst
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
                           
                           pushFollow( FOLLOW_parse_Xst_in_recurr_Xst468 );
                           parse_Xst();
                           
                           state._fsp--;
                           
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
   


   // $ANTLR end "recurr_Xst"
   
   // $ANTLR start "recurr_WD"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:79:1: recurr_WD :
   // parse_Weekday ( ( ( AND | COMMA ) parse_Weekday )+ )? ;
   public final void recurr_WD() throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:80:4: (
         // parse_Weekday ( ( ( AND | COMMA ) parse_Weekday )+ )? )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:80:6: parse_Weekday
         // ( ( ( AND | COMMA ) parse_Weekday )+ )?
         {
            pushFollow( FOLLOW_parse_Weekday_in_recurr_WD499 );
            parse_Weekday();
            
            state._fsp--;
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:81:4: ( ( ( AND
            // | COMMA ) parse_Weekday )+ )?
            int alt20 = 2;
            int LA20_0 = input.LA( 1 );
            
            if ( ( ( LA20_0 >= AND && LA20_0 <= COMMA ) ) )
            {
               alt20 = 1;
            }
            switch ( alt20 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:81:5: ( (
                  // AND | COMMA ) parse_Weekday )+
               {
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:81:5: ( (
                  // AND | COMMA ) parse_Weekday )+
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
                           // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:81:6:
                           // ( AND | COMMA ) parse_Weekday
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
                           
                           pushFollow( FOLLOW_parse_Weekday_in_recurr_WD514 );
                           parse_Weekday();
                           
                           state._fsp--;
                           
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
      return;
   }
   


   // $ANTLR end "recurr_WD"
   
   // $ANTLR start "recurr_Monthly"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:88:1: recurr_Monthly
   // returns [boolean hasWd] : recurr_Xst ( ( LAST )? recurr_WD )? ;
   public final boolean recurr_Monthly() throws RecognitionException
   {
      boolean hasWd = false;
      
      hasWd = false;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:93:4: ( recurr_Xst
         // ( ( LAST )? recurr_WD )? )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:93:6: recurr_Xst (
         // ( LAST )? recurr_WD )?
         {
            pushFollow( FOLLOW_recurr_Xst_in_recurr_Monthly556 );
            recurr_Xst();
            
            state._fsp--;
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:94:5: ( ( LAST
            // )? recurr_WD )?
            int alt22 = 2;
            int LA22_0 = input.LA( 1 );
            
            if ( ( LA22_0 == LAST || ( LA22_0 >= MONDAY && LA22_0 <= WEEKDAY_LIT ) ) )
            {
               alt22 = 1;
            }
            switch ( alt22 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:94:6: (
                  // LAST )? recurr_WD
               {
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:94:6: (
                  // LAST )?
                  int alt21 = 2;
                  int LA21_0 = input.LA( 1 );
                  
                  if ( ( LA21_0 == LAST ) )
                  {
                     alt21 = 1;
                  }
                  switch ( alt21 )
                  {
                     case 1:
                        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:94:7:
                        // LAST
                     {
                        match( input, LAST, FOLLOW_LAST_in_recurr_Monthly564 );
                        
                     }
                        break;
                     
                  }
                  
                  pushFollow( FOLLOW_recurr_WD_in_recurr_Monthly568 );
                  recurr_WD();
                  
                  state._fsp--;
                  
                  hasWd = true;
                  
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
      return hasWd;
   }
   


   // $ANTLR end "recurr_Monthly"
   
   // $ANTLR start "parse_Xst"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:101:1: parse_Xst : ( INT
   // ( DOT | ST_S )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH );
   public final void parse_Xst() throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:102:4: ( INT ( DOT
         // | ST_S )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH )
         int alt24 = 6;
         switch ( input.LA( 1 ) )
         {
            case INT:
            {
               alt24 = 1;
            }
               break;
            case FIRST:
            {
               alt24 = 2;
            }
               break;
            case SECOND:
            case OTHER:
            {
               alt24 = 3;
            }
               break;
            case THIRD:
            {
               alt24 = 4;
            }
               break;
            case FOURTH:
            {
               alt24 = 5;
            }
               break;
            case FIFTH:
            {
               alt24 = 6;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     24,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt24 )
         {
            case 1:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:102:6: INT (
               // DOT | ST_S )?
            {
               match( input, INT, FOLLOW_INT_in_parse_Xst599 );
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:102:10: ( DOT
               // | ST_S )?
               int alt23 = 2;
               int LA23_0 = input.LA( 1 );
               
               if ( ( ( LA23_0 >= DOT && LA23_0 <= ST_S ) ) )
               {
                  alt23 = 1;
               }
               switch ( alt23 )
               {
                  case 1:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:
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
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:103:6: FIRST
            {
               match( input, FIRST, FOLLOW_FIRST_in_parse_Xst613 );
               
            }
               break;
            case 3:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:104:6: (
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
               
            }
               break;
            case 4:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:105:6: THIRD
            {
               match( input, THIRD, FOLLOW_THIRD_in_parse_Xst633 );
               
            }
               break;
            case 5:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:106:6: FOURTH
            {
               match( input, FOURTH, FOLLOW_FOURTH_in_parse_Xst640 );
               
            }
               break;
            case 6:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:107:6: FIFTH
            {
               match( input, FIFTH, FOLLOW_FIFTH_in_parse_Xst647 );
               
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
   


   // $ANTLR end "parse_Xst"
   
   // $ANTLR start "parse_Number"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:114:1: parse_Number : (
   // INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN
   // );
   public final void parse_Number() throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:115:4: ( INT |
         // NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:
         {
            if ( input.LA( 1 ) == INT
               || ( input.LA( 1 ) >= NUM_ONE && input.LA( 1 ) <= NUM_TEN ) )
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
   


   // $ANTLR end "parse_Number"
   
   // $ANTLR start "parse_Weekday"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:132:1: parse_Weekday : (
   // MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT );
   public final void parse_Weekday() throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:133:4: ( MONDAY |
         // TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:
         {
            if ( ( input.LA( 1 ) >= MONDAY && input.LA( 1 ) <= WEEKDAY_LIT ) )
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
   


   // $ANTLR end "parse_Weekday"
   
   // $ANTLR start "parse_Month"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:148:1: parse_Month :
   // MONTH ;
   public final void parse_Month() throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:149:4: ( MONTH )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrenceAutoComplParser.g:149:6: MONTH
         {
            match( input, MONTH, FOLLOW_MONTH_in_parse_Month854 );
            
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
   
   // $ANTLR end "parse_Month"
   
   // Delegated rules
   
   protected DFA15 dfa15 = new DFA15( this );
   
   static final String DFA15_eotS = "\24\uffff";
   
   static final String DFA15_eofS = "\1\uffff\1\13\1\uffff\5\13\1\uffff\1\13\3\uffff\7\13";
   
   static final String DFA15_minS = "\2\6\1\uffff\5\17\1\uffff\1\17\1\21\2\uffff\7\17";
   
   static final String DFA15_maxS = "\2\57\1\uffff\5\57\1\uffff\1\57\1\34\2\uffff\7\57";
   
   static final String DFA15_acceptS = "\2\uffff\1\1\5\uffff\1\3\2\uffff\1\2\1\4\7\uffff";
   
   static final String DFA15_specialS = "\24\uffff}>";
   
   static final String[] DFA15_transitionS =
   {
    "\3\2\2\uffff\2\2\4\uffff\1\1\5\uffff\1\3\2\4\1\5\1\6\1\7\12" + "\2\11\10",
    "\3\2\2\uffff\2\2\2\uffff\2\13\1\uffff\2\12\1\uffff\2\11\20"
       + "\uffff\11\14", "", "\2\13\1\uffff\2\12\23\uffff\11\14",
    "\2\13\1\uffff\2\12\23\uffff\11\14", "\2\13\1\uffff\2\12\23\uffff\11\14",
    "\2\13\1\uffff\2\12\23\uffff\11\14", "\2\13\1\uffff\2\12\23\uffff\11\14",
    "", "\2\13\1\uffff\2\12\23\uffff\11\14",
    "\1\15\5\uffff\1\16\2\17\1\20\1\21\1\22", "", "",
    "\2\13\1\uffff\2\12\1\uffff\2\23\20\uffff\11\14",
    "\2\13\1\uffff\2\12\23\uffff\11\14", "\2\13\1\uffff\2\12\23\uffff\11\14",
    "\2\13\1\uffff\2\12\23\uffff\11\14", "\2\13\1\uffff\2\12\23\uffff\11\14",
    "\2\13\1\uffff\2\12\23\uffff\11\14", "\2\13\1\uffff\2\12\23\uffff\11\14" };
   
   static final short[] DFA15_eot = DFA.unpackEncodedString( DFA15_eotS );
   
   static final short[] DFA15_eof = DFA.unpackEncodedString( DFA15_eofS );
   
   static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars( DFA15_minS );
   
   static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars( DFA15_maxS );
   
   static final short[] DFA15_accept = DFA.unpackEncodedString( DFA15_acceptS );
   
   static final short[] DFA15_special = DFA.unpackEncodedString( DFA15_specialS );
   
   static final short[][] DFA15_transition;
   
   static
   {
      int numStates = DFA15_transitionS.length;
      DFA15_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA15_transition[ i ] = DFA.unpackEncodedString( DFA15_transitionS[ i ] );
      }
   }
   
   
   class DFA15 extends DFA
   {
      
      public DFA15( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 15;
         this.eot = DFA15_eot;
         this.eof = DFA15_eof;
         this.min = DFA15_min;
         this.max = DFA15_max;
         this.accept = DFA15_accept;
         this.special = DFA15_special;
         this.transition = DFA15_transition;
      }
      


      public String getDescription()
      {
         return "51:4: ( ( parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD )? | MONTHS ( ( ON )? ( THE )? recurr_Monthly )? | YEARS ( ( ON )? ( THE )? hasWd= recurr_Monthly {...}? => ( ( IN | OF )? parse_Month )? )? ) | recurr_Xst | recurr_WD | recurr_Xst recurr_WD )";
      }
   }
   
   public static final BitSet FOLLOW_set_in_parseInput46 = new BitSet( new long[]
   { 0x0000FFFFFF8219C0L } );
   
   public static final BitSet FOLLOW_parse_Number_in_parseInput173 = new BitSet( new long[]
   { 0x00000000000019C0L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseInput193 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_set_in_parseInput210 = new BitSet( new long[]
   { 0x0000FF8000018602L } );
   
   public static final BitSet FOLLOW_ON_in_parseInput219 = new BitSet( new long[]
   { 0x0000FF8000000400L } );
   
   public static final BitSet FOLLOW_THE_in_parseInput222 = new BitSet( new long[]
   { 0x0000FF8000000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseInput225 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseInput244 = new BitSet( new long[]
   { 0x000000001F838602L } );
   
   public static final BitSet FOLLOW_ON_in_parseInput259 = new BitSet( new long[]
   { 0x000000001F820600L } );
   
   public static final BitSet FOLLOW_THE_in_parseInput262 = new BitSet( new long[]
   { 0x000000001F820600L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseInput265 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseInput284 = new BitSet( new long[]
   { 0x000000001F838602L } );
   
   public static final BitSet FOLLOW_ON_in_parseInput300 = new BitSet( new long[]
   { 0x000000001F820600L } );
   
   public static final BitSet FOLLOW_THE_in_parseInput303 = new BitSet( new long[]
   { 0x000000001F820600L } );
   
   public static final BitSet FOLLOW_recurr_Monthly_in_parseInput310 = new BitSet( new long[]
   { 0x000100000001E002L } );
   
   public static final BitSet FOLLOW_set_in_parseInput317 = new BitSet( new long[]
   { 0x0001000000006000L } );
   
   public static final BitSet FOLLOW_parse_Month_in_parseInput326 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseInput352 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseInput363 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_parseInput374 = new BitSet( new long[]
   { 0x0000FF8000000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_parseInput376 = new BitSet( new long[]
   { 0x0000000000018002L } );
   
   public static final BitSet FOLLOW_UNTIL_in_parseInput391 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOR_in_parseInput401 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_INT_in_parseInput403 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst438 = new BitSet( new long[]
   { 0x00000000000C0002L } );
   
   public static final BitSet FOLLOW_set_in_recurr_Xst460 = new BitSet( new long[]
   { 0x000000001F820000L } );
   
   public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst468 = new BitSet( new long[]
   { 0x00000000000C0002L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD499 = new BitSet( new long[]
   { 0x00000000000C0002L } );
   
   public static final BitSet FOLLOW_set_in_recurr_WD506 = new BitSet( new long[]
   { 0x0000FF8000000000L } );
   
   public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD514 = new BitSet( new long[]
   { 0x00000000000C0002L } );
   
   public static final BitSet FOLLOW_recurr_Xst_in_recurr_Monthly556 = new BitSet( new long[]
   { 0x0000FF8000100002L } );
   
   public static final BitSet FOLLOW_LAST_in_recurr_Monthly564 = new BitSet( new long[]
   { 0x0000FF8000000000L } );
   
   public static final BitSet FOLLOW_recurr_WD_in_recurr_Monthly568 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_Xst599 = new BitSet( new long[]
   { 0x0000000000600002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Xst601 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIRST_in_parse_Xst613 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Xst620 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_THIRD_in_parse_Xst633 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FOURTH_in_parse_Xst640 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_FIFTH_in_parse_Xst647 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Number0 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parse_Weekday0 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTH_in_parse_Month854 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
