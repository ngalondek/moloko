// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g 2011-03-21 18:20:57

package dev.drsoran.moloko.grammar;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.grammar.lang.RecurrPatternLanguage;
import dev.drsoran.moloko.util.MolokoDateUtils;


public class RecurrencePatternParser extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "OP_FREQ", "VAL_YEARLY",
    "OP_BYDAY", "COMMA", "OP_BYMONTH", "VAL_MONTHLY", "OP_BYMONTHDAY",
    "VAL_WEEKLY", "VAL_DAILY", "OP_UNTIL", "VAL_DATE", "OP_COUNT", "INT",
    "OP_INTERVAL", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY",
    "SATURDAY", "SUNDAY", "SEMICOLON", "EQUALS", "MINUS", "NUMBER", "WS" };
   
   public static final int VAL_DAILY = 12;
   
   public static final int VAL_WEEKLY = 11;
   
   public static final int WEDNESDAY = 20;
   
   public static final int SATURDAY = 23;
   
   public static final int OP_FREQ = 4;
   
   public static final int NUMBER = 28;
   
   public static final int MONDAY = 18;
   
   public static final int SUNDAY = 24;
   
   public static final int EQUALS = 26;
   
   public static final int SEMICOLON = 25;
   
   public static final int OP_BYMONTHDAY = 10;
   
   public static final int INT = 16;
   
   public static final int MINUS = 27;
   
   public static final int OP_BYDAY = 6;
   
   public static final int EOF = -1;
   
   public static final int VAL_YEARLY = 5;
   
   public static final int FRIDAY = 22;
   
   public static final int WS = 29;
   
   public static final int OP_UNTIL = 13;
   
   public static final int THURSDAY = 21;
   
   public static final int COMMA = 7;
   
   public static final int OP_INTERVAL = 17;
   
   public static final int VAL_DATE = 14;
   
   public static final int OP_BYMONTH = 8;
   
   public static final int OP_COUNT = 15;
   
   public static final int VAL_MONTHLY = 9;
   
   public static final int TUESDAY = 19;
   
   

   // delegates
   // delegators
   
   public RecurrencePatternParser( TokenStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public RecurrencePatternParser( TokenStream input,
      RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   @Override
   public String[] getTokenNames()
   {
      return RecurrencePatternParser.tokenNames;
   }
   


   @Override
   public String getGrammarFileName()
   {
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g";
   }
   


   public RecurrencePatternParser()
   {
      super( null );
   }
   
   
   private final static class CmpOperators implements Comparator< String >
   {
      private final static int operatorToInt( String operator )
      {
         if ( operator.startsWith( OP_FREQ_LIT ) )
            return 1;
         else if ( operator.startsWith( OP_INTERVAL_LIT ) )
            return 2;
         else if ( operator.startsWith( OP_BYDAY_LIT ) )
            return 3;
         else if ( operator.startsWith( OP_BYMONTHDAY_LIT ) )
            return 3;
         else if ( operator.startsWith( OP_BYMONTH_LIT ) )
            return 4;
         else if ( operator.startsWith( OP_UNTIL_LIT ) )
            return 5;
         else if ( operator.startsWith( OP_COUNT_LIT ) )
            return 5;
         else
            return Integer.MAX_VALUE;
      }
      


      public int compare( String op1, String op2 )
      {
         return operatorToInt( op1 ) - operatorToInt( op2 );
      }
   }
   
   

   private final static void addElement( Map< Integer, List< Object > > elements,
                                         int element,
                                         Object value )
   {
      List< Object > values = elements.get( element );
      
      if ( values == null )
         values = new LinkedList< Object >();
      
      values.add( value );
      elements.put( element, values );
   }
   
   public final static CmpOperators CMP_OPERATORS = new CmpOperators();
   
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
   
   public final static String OPERATOR_SEP = ";";
   
   public final static String BYDAY_MON = "MO";
   
   public final static String BYDAY_TUE = "TU";
   
   public final static String BYDAY_WED = "WE";
   
   public final static String BYDAY_THU = "TH";
   
   public final static String BYDAY_FRI = "FR";
   
   public final static String BYDAY_SAT = "SA";
   
   public final static String BYDAY_SUN = "SU";
   
   public final static String DATE_PATTERN = "yyyyMMdd'T'HHmmss";
   
   

   // $ANTLR start "parseRecurrencePattern"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:127:1:
   // parseRecurrencePattern[RecurrPatternLanguage lang,\r\n boolean every] returns [String sentence] : OP_FREQ ( (
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:137:4: (
         // OP_FREQ ( ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
         // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
         // parse_PatternMonth[lang, sb] ) )? ) | ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( (
         // OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
         // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | (
         // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang, sb, true]
         // ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\",
         // every] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:137:6:
         // OP_FREQ ( ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
         // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
         // parse_PatternMonth[lang, sb] ) )? ) | ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( (
         // OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
         // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | (
         // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang, sb, true]
         // ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\",
         // every] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )?
         {
            match( input, OP_FREQ, FOLLOW_OP_FREQ_in_parseRecurrencePattern70 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:138:6:
            // ( ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY parse_PatternWeekday[lang,
            // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )?
            // ) | ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
            // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
            // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | (
            // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang, sb,
            // true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | ( VAL_DAILY parse_PatternInterval[lang, sb,
            // \"day\", every] ) )
            int alt9 = 4;
            switch ( input.LA( 1 ) )
            {
               case VAL_YEARLY:
               {
                  alt9 = 1;
               }
                  break;
               case VAL_MONTHLY:
               {
                  alt9 = 2;
               }
                  break;
               case VAL_WEEKLY:
               {
                  alt9 = 3;
               }
                  break;
               case VAL_DAILY:
               {
                  alt9 = 4;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        9,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
            }
            
            switch ( alt9 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:139:11:
                  // ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
                  // parse_PatternMonth[lang, sb] ) )? )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:139:11:
                  // ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
                  // parse_PatternMonth[lang, sb] ) )? )
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:140:14:
                  // VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
                  // parse_PatternMonth[lang, sb] ) )?
                  {
                     match( input,
                            VAL_YEARLY,
                            FOLLOW_VAL_YEARLY_in_parseRecurrencePattern104 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern106 );
                     parse_PatternInterval( lang, sb, "year", every );
                     
                     state._fsp--;
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:141:14:
                     // ( ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                     // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )?
                     int alt2 = 2;
                     int LA2_0 = input.LA( 1 );
                     
                     if ( ( LA2_0 == OP_BYDAY ) )
                     {
                        alt2 = 1;
                     }
                     switch ( alt2 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:142:17:
                           // ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] )
                        {
                           
                           sb.append( " " );
                           lang.add( sb, "on_the" );
                           sb.append( " " );
                           
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:145:17:
                           // ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] )
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:146:20:
                           // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb]
                           {
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:146:20:
                              // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                              // false] )* )
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:146:21:
                              // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                              // false] )*
                              {
                                 match( input,
                                        OP_BYDAY,
                                        FOLLOW_OP_BYDAY_in_parseRecurrencePattern180 );
                                 pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern182 );
                                 parse_PatternWeekday( lang, sb, true );
                                 
                                 state._fsp--;
                                 
                                 // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:147:23:
                                 // ( COMMA parse_PatternWeekday[lang, sb, false] )*
                                 loop1: do
                                 {
                                    int alt1 = 2;
                                    int LA1_0 = input.LA( 1 );
                                    
                                    if ( ( LA1_0 == COMMA ) )
                                    {
                                       alt1 = 1;
                                    }
                                    
                                    switch ( alt1 )
                                    {
                                       case 1:
                                          // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:147:24:
                                          // COMMA parse_PatternWeekday[lang, sb, false]
                                       {
                                          match( input,
                                                 COMMA,
                                                 FOLLOW_COMMA_in_parseRecurrencePattern208 );
                                          sb.append( ", " );
                                          pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern212 );
                                          parse_PatternWeekday( lang, sb, false );
                                          
                                          state._fsp--;
                                          
                                       }
                                          break;
                                       
                                       default :
                                          break loop1;
                                    }
                                 }
                                 while ( true );
                                 
                              }
                              
                              sb.append( " " );
                              lang.add( sb, "in" );
                              sb.append( " " );
                              
                              match( input,
                                     OP_BYMONTH,
                                     FOLLOW_OP_BYMONTH_in_parseRecurrencePattern258 );
                              pushFollow( FOLLOW_parse_PatternMonth_in_parseRecurrencePattern260 );
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:155:11:
                  // ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
                  // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:155:11:
                  // ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
                  // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? )
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:156:14:
                  // VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
                  // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )?
                  {
                     match( input,
                            VAL_MONTHLY,
                            FOLLOW_VAL_MONTHLY_in_parseRecurrencePattern334 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern336 );
                     parse_PatternInterval( lang, sb, "month", every );
                     
                     state._fsp--;
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:157:14:
                     // ( ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | (
                     // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )
                     // ) )?
                     int alt6 = 2;
                     int LA6_0 = input.LA( 1 );
                     
                     if ( ( LA6_0 == OP_BYDAY || LA6_0 == OP_BYMONTHDAY ) )
                     {
                        alt6 = 1;
                     }
                     switch ( alt6 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:158:17:
                           // ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | (
                           // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) )
                        {
                           
                           sb.append( " " );
                           lang.add( sb, "on_the" );
                           sb.append( " " );
                           
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:161:17:
                           // ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | (
                           // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) )
                           int alt5 = 2;
                           int LA5_0 = input.LA( 1 );
                           
                           if ( ( LA5_0 == OP_BYMONTHDAY ) )
                           {
                              alt5 = 1;
                           }
                           else if ( ( LA5_0 == OP_BYDAY ) )
                           {
                              alt5 = 2;
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
                                 // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:162:22:
                                 // ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* )
                              {
                                 // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:162:22:
                                 // ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* )
                                 // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:163:25:
                                 // OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )*
                                 {
                                    match( input,
                                           OP_BYMONTHDAY,
                                           FOLLOW_OP_BYMONTHDAY_in_parseRecurrencePattern437 );
                                    pushFollow( FOLLOW_parse_PatternXst_in_parseRecurrencePattern439 );
                                    parse_PatternXst( lang, sb );
                                    
                                    state._fsp--;
                                    
                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:164:28:
                                    // ( COMMA parse_PatternXst[lang, sb] )*
                                    loop3: do
                                    {
                                       int alt3 = 2;
                                       int LA3_0 = input.LA( 1 );
                                       
                                       if ( ( LA3_0 == COMMA ) )
                                       {
                                          alt3 = 1;
                                       }
                                       
                                       switch ( alt3 )
                                       {
                                          case 1:
                                             // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:164:29:
                                             // COMMA parse_PatternXst[lang, sb]
                                          {
                                             match( input,
                                                    COMMA,
                                                    FOLLOW_COMMA_in_parseRecurrencePattern470 );
                                             sb.append( ", " );
                                             pushFollow( FOLLOW_parse_PatternXst_in_parseRecurrencePattern474 );
                                             parse_PatternXst( lang, sb );
                                             
                                             state._fsp--;
                                             
                                          }
                                             break;
                                          
                                          default :
                                             break loop3;
                                       }
                                    }
                                    while ( true );
                                    
                                 }
                                 
                              }
                                 break;
                              case 2:
                                 // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:166:22:
                                 // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang,
                                 // sb, false] )* )
                              {
                                 // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:166:22:
                                 // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang,
                                 // sb, false] )* )
                                 // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:167:25:
                                 // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                                 // false] )*
                                 {
                                    match( input,
                                           OP_BYDAY,
                                           FOLLOW_OP_BYDAY_in_parseRecurrencePattern549 );
                                    pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern551 );
                                    parse_PatternWeekday( lang, sb, true );
                                    
                                    state._fsp--;
                                    
                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:168:29:
                                    // ( COMMA parse_PatternWeekday[lang, sb, false] )*
                                    loop4: do
                                    {
                                       int alt4 = 2;
                                       int LA4_0 = input.LA( 1 );
                                       
                                       if ( ( LA4_0 == COMMA ) )
                                       {
                                          alt4 = 1;
                                       }
                                       
                                       switch ( alt4 )
                                       {
                                          case 1:
                                             // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:168:30:
                                             // COMMA parse_PatternWeekday[lang, sb, false]
                                          {
                                             match( input,
                                                    COMMA,
                                                    FOLLOW_COMMA_in_parseRecurrencePattern583 );
                                             sb.append( ", " );
                                             pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern587 );
                                             parse_PatternWeekday( lang,
                                                                   sb,
                                                                   false );
                                             
                                             state._fsp--;
                                             
                                          }
                                             break;
                                          
                                          default :
                                             break loop4;
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:173:11:
                  // ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:173:11:
                  // ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? )
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:174:14:
                  // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )?
                  {
                     match( input,
                            VAL_WEEKLY,
                            FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern686 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern688 );
                     parse_PatternInterval( lang, sb, "week", every );
                     
                     state._fsp--;
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:175:14:
                     // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )*
                     // )?
                     int alt8 = 2;
                     int LA8_0 = input.LA( 1 );
                     
                     if ( ( LA8_0 == OP_BYDAY ) )
                     {
                        alt8 = 1;
                     }
                     switch ( alt8 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:176:17:
                           // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )*
                        {
                           
                           sb.append( " " );
                           lang.add( sb, "on_the" );
                           sb.append( " " );
                           
                           match( input,
                                  OP_BYDAY,
                                  FOLLOW_OP_BYDAY_in_parseRecurrencePattern740 );
                           pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern742 );
                           parse_PatternWeekday( lang, sb, true );
                           
                           state._fsp--;
                           
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:180:20:
                           // ( COMMA parse_PatternWeekday[lang, sb, false] )*
                           loop7: do
                           {
                              int alt7 = 2;
                              int LA7_0 = input.LA( 1 );
                              
                              if ( ( LA7_0 == COMMA ) )
                              {
                                 alt7 = 1;
                              }
                              
                              switch ( alt7 )
                              {
                                 case 1:
                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:180:21:
                                    // COMMA parse_PatternWeekday[lang, sb, false]
                                 {
                                    match( input,
                                           COMMA,
                                           FOLLOW_COMMA_in_parseRecurrencePattern765 );
                                    sb.append( ", " );
                                    pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern769 );
                                    parse_PatternWeekday( lang, sb, false );
                                    
                                    state._fsp--;
                                    
                                 }
                                    break;
                                 
                                 default :
                                    break loop7;
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:183:11:
                  // ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:183:11:
                  // ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] )
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:184:14:
                  // VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every]
                  {
                     match( input,
                            VAL_DAILY,
                            FOLLOW_VAL_DAILY_in_parseRecurrencePattern827 );
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern829 );
                     parse_PatternInterval( lang, sb, "day", every );
                     
                     state._fsp--;
                     
                  }
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:187:6:
            // ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )?
            int alt10 = 3;
            int LA10_0 = input.LA( 1 );
            
            if ( ( LA10_0 == OP_UNTIL ) )
            {
               alt10 = 1;
            }
            else if ( ( LA10_0 == OP_COUNT ) )
            {
               alt10 = 2;
            }
            switch ( alt10 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:188:11:
                  // OP_UNTIL date= VAL_DATE
               {
                  match( input,
                         OP_UNTIL,
                         FOLLOW_OP_UNTIL_in_parseRecurrencePattern868 );
                  date = (Token) match( input,
                                        VAL_DATE,
                                        FOLLOW_VAL_DATE_in_parseRecurrencePattern872 );
                  
                  final String formatedDate = MolokoDateUtils.formatDate( DATE_PATTERN,
                                                                          ( date != null
                                                                                        ? date.getText()
                                                                                        : null ),
                                                                          MolokoDateUtils.FORMAT_WITH_YEAR );
                  
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:200:11:
                  // OP_COUNT count= INT
               {
                  match( input,
                         OP_COUNT,
                         FOLLOW_OP_COUNT_in_parseRecurrencePattern896 );
                  count = (Token) match( input,
                                         INT,
                                         FOLLOW_INT_in_parseRecurrencePattern900 );
                  
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
   
   // $ANTLR start "parseRecurrencePattern1"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:213:1:
   // parseRecurrencePattern1[boolean every] returns [Map< Integer, List< Object > > elements] : OP_FREQ ( ( VAL_YEARLY
   // parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] parse_PatternMonth1[elements] )? ) | (
   // VAL_MONTHLY parse_PatternInterval1[elements] ( parse_PatternMonthDay1[elements] | parse_PatternWeekday1[elements]
   // )? ) | ( VAL_WEEKLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? ) | ( VAL_DAILY
   // parse_PatternInterval1[elements] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )? ;
   public final Map< Integer, List< Object > > parseRecurrencePattern1( boolean every ) throws RecognitionException
   {
      Map< Integer, List< Object > > elements = null;
      
      Token date = null;
      Token count = null;
      
      elements = new HashMap< Integer, List< Object > >();
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:218:4: (
         // OP_FREQ ( ( VAL_YEARLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements]
         // parse_PatternMonth1[elements] )? ) | ( VAL_MONTHLY parse_PatternInterval1[elements] (
         // parse_PatternMonthDay1[elements] | parse_PatternWeekday1[elements] )? ) | ( VAL_WEEKLY
         // parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? ) | ( VAL_DAILY
         // parse_PatternInterval1[elements] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:218:6:
         // OP_FREQ ( ( VAL_YEARLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements]
         // parse_PatternMonth1[elements] )? ) | ( VAL_MONTHLY parse_PatternInterval1[elements] (
         // parse_PatternMonthDay1[elements] | parse_PatternWeekday1[elements] )? ) | ( VAL_WEEKLY
         // parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? ) | ( VAL_DAILY
         // parse_PatternInterval1[elements] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )?
         {
            match( input, OP_FREQ, FOLLOW_OP_FREQ_in_parseRecurrencePattern1967 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:219:6:
            // ( ( VAL_YEARLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements]
            // parse_PatternMonth1[elements] )? ) | ( VAL_MONTHLY parse_PatternInterval1[elements] (
            // parse_PatternMonthDay1[elements] | parse_PatternWeekday1[elements] )? ) | ( VAL_WEEKLY
            // parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? ) | ( VAL_DAILY
            // parse_PatternInterval1[elements] ) )
            int alt14 = 4;
            switch ( input.LA( 1 ) )
            {
               case VAL_YEARLY:
               {
                  alt14 = 1;
               }
                  break;
               case VAL_MONTHLY:
               {
                  alt14 = 2;
               }
                  break;
               case VAL_WEEKLY:
               {
                  alt14 = 3;
               }
                  break;
               case VAL_DAILY:
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:220:11:
                  // ( VAL_YEARLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements]
                  // parse_PatternMonth1[elements] )? )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:220:11:
                  // ( VAL_YEARLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements]
                  // parse_PatternMonth1[elements] )? )
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:221:14:
                  // VAL_YEARLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements]
                  // parse_PatternMonth1[elements] )?
                  {
                     match( input,
                            VAL_YEARLY,
                            FOLLOW_VAL_YEARLY_in_parseRecurrencePattern11001 );
                     pushFollow( FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11003 );
                     parse_PatternInterval1( elements );
                     
                     state._fsp--;
                     
                     addElement( elements, OP_FREQ, VAL_YEARLY );
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:225:14:
                     // ( parse_PatternWeekday1[elements] parse_PatternMonth1[elements] )?
                     int alt11 = 2;
                     int LA11_0 = input.LA( 1 );
                     
                     if ( ( LA11_0 == OP_BYDAY ) )
                     {
                        alt11 = 1;
                     }
                     switch ( alt11 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:225:15:
                           // parse_PatternWeekday1[elements] parse_PatternMonth1[elements]
                        {
                           pushFollow( FOLLOW_parse_PatternWeekday1_in_parseRecurrencePattern11048 );
                           parse_PatternWeekday1( elements );
                           
                           state._fsp--;
                           
                           pushFollow( FOLLOW_parse_PatternMonth1_in_parseRecurrencePattern11051 );
                           parse_PatternMonth1( elements );
                           
                           state._fsp--;
                           
                        }
                           break;
                        
                     }
                     
                  }
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:227:11:
                  // ( VAL_MONTHLY parse_PatternInterval1[elements] ( parse_PatternMonthDay1[elements] |
                  // parse_PatternWeekday1[elements] )? )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:227:11:
                  // ( VAL_MONTHLY parse_PatternInterval1[elements] ( parse_PatternMonthDay1[elements] |
                  // parse_PatternWeekday1[elements] )? )
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:228:14:
                  // VAL_MONTHLY parse_PatternInterval1[elements] ( parse_PatternMonthDay1[elements] |
                  // parse_PatternWeekday1[elements] )?
                  {
                     match( input,
                            VAL_MONTHLY,
                            FOLLOW_VAL_MONTHLY_in_parseRecurrencePattern11093 );
                     pushFollow( FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11095 );
                     parse_PatternInterval1( elements );
                     
                     state._fsp--;
                     
                     addElement( elements, OP_FREQ, VAL_MONTHLY );
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:232:14:
                     // ( parse_PatternMonthDay1[elements] | parse_PatternWeekday1[elements] )?
                     int alt12 = 3;
                     int LA12_0 = input.LA( 1 );
                     
                     if ( ( LA12_0 == OP_BYMONTHDAY ) )
                     {
                        alt12 = 1;
                     }
                     else if ( ( LA12_0 == OP_BYDAY ) )
                     {
                        alt12 = 2;
                     }
                     switch ( alt12 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:232:15:
                           // parse_PatternMonthDay1[elements]
                        {
                           pushFollow( FOLLOW_parse_PatternMonthDay1_in_parseRecurrencePattern11127 );
                           parse_PatternMonthDay1( elements );
                           
                           state._fsp--;
                           
                        }
                           break;
                        case 2:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:232:50:
                           // parse_PatternWeekday1[elements]
                        {
                           pushFollow( FOLLOW_parse_PatternWeekday1_in_parseRecurrencePattern11132 );
                           parse_PatternWeekday1( elements );
                           
                           state._fsp--;
                           
                        }
                           break;
                        
                     }
                     
                  }
                  
               }
                  break;
               case 3:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:234:11:
                  // ( VAL_WEEKLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:234:11:
                  // ( VAL_WEEKLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? )
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:235:14:
                  // VAL_WEEKLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )?
                  {
                     match( input,
                            VAL_WEEKLY,
                            FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern11174 );
                     pushFollow( FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11176 );
                     parse_PatternInterval1( elements );
                     
                     state._fsp--;
                     
                     addElement( elements, OP_FREQ, VAL_WEEKLY );
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:239:14:
                     // ( parse_PatternWeekday1[elements] )?
                     int alt13 = 2;
                     int LA13_0 = input.LA( 1 );
                     
                     if ( ( LA13_0 == OP_BYDAY ) )
                     {
                        alt13 = 1;
                     }
                     switch ( alt13 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:239:14:
                           // parse_PatternWeekday1[elements]
                        {
                           pushFollow( FOLLOW_parse_PatternWeekday1_in_parseRecurrencePattern11207 );
                           parse_PatternWeekday1( elements );
                           
                           state._fsp--;
                           
                        }
                           break;
                        
                     }
                     
                  }
                  
               }
                  break;
               case 4:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:241:11:
                  // ( VAL_DAILY parse_PatternInterval1[elements] )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:241:11:
                  // ( VAL_DAILY parse_PatternInterval1[elements] )
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:242:14:
                  // VAL_DAILY parse_PatternInterval1[elements]
                  {
                     match( input,
                            VAL_DAILY,
                            FOLLOW_VAL_DAILY_in_parseRecurrencePattern11248 );
                     pushFollow( FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11250 );
                     parse_PatternInterval1( elements );
                     
                     state._fsp--;
                     
                     addElement( elements, OP_FREQ, VAL_DAILY );
                     
                  }
                  
               }
                  break;
               
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:248:6:
            // ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )?
            int alt15 = 3;
            int LA15_0 = input.LA( 1 );
            
            if ( ( LA15_0 == OP_UNTIL ) )
            {
               alt15 = 1;
            }
            else if ( ( LA15_0 == OP_COUNT ) )
            {
               alt15 = 2;
            }
            switch ( alt15 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:249:11:
                  // OP_UNTIL date= VAL_DATE
               {
                  match( input,
                         OP_UNTIL,
                         FOLLOW_OP_UNTIL_in_parseRecurrencePattern11304 );
                  date = (Token) match( input,
                                        VAL_DATE,
                                        FOLLOW_VAL_DATE_in_parseRecurrencePattern11308 );
                  
                  addElement( elements,
                              OP_UNTIL,
                              MolokoDateUtils.parseRtmDate( ( date != null
                                                                          ? date.getText()
                                                                          : null ) ) );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:253:11:
                  // OP_COUNT count= INT
               {
                  match( input,
                         OP_COUNT,
                         FOLLOW_OP_COUNT_in_parseRecurrencePattern11332 );
                  count = (Token) match( input,
                                         INT,
                                         FOLLOW_INT_in_parseRecurrencePattern11336 );
                  
                  addElement( elements,
                              OP_COUNT,
                              Integer.parseInt( ( count != null
                                                               ? count.getText()
                                                               : null ) ) );
                  
               }
                  break;
               
            }
            
         }
         
      }
      catch ( IllegalArgumentException e )
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
      return elements;
   }
   


   // $ANTLR end "parseRecurrencePattern1"
   
   // $ANTLR start "parse_PatternInterval"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:268:1:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:272:4: (
         // OP_INTERVAL interval= INT )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:272:6:
         // OP_INTERVAL interval= INT
         {
            match( input,
                   OP_INTERVAL,
                   FOLLOW_OP_INTERVAL_in_parse_PatternInterval1397 );
            interval = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_parse_PatternInterval1401 );
            
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
   
   // $ANTLR start "parse_PatternInterval1"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:285:1:
   // parse_PatternInterval1[Map< Integer, List< Object > > elements] : OP_INTERVAL interval= INT ;
   public final void parse_PatternInterval1( Map< Integer, List< Object > > elements ) throws RecognitionException
   {
      Token interval = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:286:4: (
         // OP_INTERVAL interval= INT )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:286:6:
         // OP_INTERVAL interval= INT
         {
            match( input,
                   OP_INTERVAL,
                   FOLLOW_OP_INTERVAL_in_parse_PatternInterval11437 );
            interval = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_parse_PatternInterval11441 );
            
            addElement( elements,
                        OP_INTERVAL,
                        Integer.parseInt( ( interval != null
                                                            ? interval.getText()
                                                            : null ) ) );
            
         }
         
      }
      catch ( NumberFormatException e )
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
      return;
   }
   


   // $ANTLR end "parse_PatternInterval1"
   
   // $ANTLR start "parse_PatternXst"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:300:1:
   // parse_PatternXst[RecurrPatternLanguage lang, StringBuilder sb] : x= INT ;
   public final void parse_PatternXst( RecurrPatternLanguage lang,
                                       StringBuilder sb ) throws RecognitionException
   {
      Token x = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:301:4:
         // (x= INT )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:301:6:
         // x= INT
         {
            x = (Token) match( input, INT, FOLLOW_INT_in_parse_PatternXst1491 );
            
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
      catch ( NumberFormatException e )
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
      return;
   }
   


   // $ANTLR end "parse_PatternXst"
   
   // $ANTLR start "parse_PatternWeekday"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:331:1:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:334:4: (
         // ( ( parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
         // FRIDAY | SATURDAY | SUNDAY ) ) ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:334:6: (
         // ( parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY
         // | SATURDAY | SUNDAY ) ) )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:334:6:
            // ( ( parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
            // FRIDAY | SATURDAY | SUNDAY ) ) )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:335:9:
            // ( parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
            // FRIDAY | SATURDAY | SUNDAY ) )
            {
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:335:9:
               // ( parse_PatternXst[lang, respectXst ? sb : null] )?
               int alt16 = 2;
               int LA16_0 = input.LA( 1 );
               
               if ( ( LA16_0 == INT ) )
               {
                  alt16 = 1;
               }
               switch ( alt16 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:336:12:
                     // parse_PatternXst[lang, respectXst ? sb : null]
                  {
                     pushFollow( FOLLOW_parse_PatternXst_in_parse_PatternWeekday1562 );
                     parse_PatternXst( lang, respectXst ? sb : null );
                     
                     state._fsp--;
                     
                     if ( respectXst )
                        sb.append( " " );
                     
                  }
                     break;
                  
               }
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:342:9:
               // (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) )
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:343:12:
               // wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY )
               {
                  wd = input.LT( 1 );
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
   
   // $ANTLR start "parse_PatternWeekday1"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:361:1:
   // parse_PatternWeekday1[Map< Integer, List< Object > > elements] : OP_BYDAY ( (x= INT )? wd= ( MONDAY | TUESDAY |
   // WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) ( COMMA )? )+ ;
   public final void parse_PatternWeekday1( Map< Integer, List< Object > > elements ) throws RecognitionException
   {
      Token x = null;
      Token wd = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:362:4: (
         // OP_BYDAY ( (x= INT )? wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) ( COMMA
         // )? )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:362:6:
         // OP_BYDAY ( (x= INT )? wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) ( COMMA
         // )? )+
         {
            match( input,
                   OP_BYDAY,
                   FOLLOW_OP_BYDAY_in_parse_PatternWeekday11790 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:362:15:
            // ( (x= INT )? wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) ( COMMA )? )+
            int cnt19 = 0;
            loop19: do
            {
               int alt19 = 2;
               int LA19_0 = input.LA( 1 );
               
               if ( ( LA19_0 == INT || ( LA19_0 >= MONDAY && LA19_0 <= SUNDAY ) ) )
               {
                  alt19 = 1;
               }
               
               switch ( alt19 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:362:16:
                     // (x= INT )? wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) ( COMMA
                     // )?
                  {
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:362:16:
                     // (x= INT )?
                     int alt17 = 2;
                     int LA17_0 = input.LA( 1 );
                     
                     if ( ( LA17_0 == INT ) )
                     {
                        alt17 = 1;
                     }
                     switch ( alt17 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:362:17:
                           // x= INT
                        {
                           x = (Token) match( input,
                                              INT,
                                              FOLLOW_INT_in_parse_PatternWeekday11796 );
                           
                        }
                           break;
                        
                     }
                     
                     wd = input.LT( 1 );
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
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:368:40:
                     // ( COMMA )?
                     int alt18 = 2;
                     int LA18_0 = input.LA( 1 );
                     
                     if ( ( LA18_0 == COMMA ) )
                     {
                        alt18 = 1;
                     }
                     switch ( alt18 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:368:40:
                           // COMMA
                        {
                           match( input,
                                  COMMA,
                                  FOLLOW_COMMA_in_parse_PatternWeekday12001 );
                           
                        }
                           break;
                        
                     }
                     
                  }
                     break;
                  
                  default :
                     if ( cnt19 >= 1 )
                        break loop19;
                     EarlyExitException eee = new EarlyExitException( 19, input );
                     throw eee;
               }
               cnt19++;
            }
            while ( true );
            
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
   


   // $ANTLR end "parse_PatternWeekday1"
   
   // $ANTLR start "parse_PatternMonth"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:375:1:
   // parse_PatternMonth[RecurrPatternLanguage lang, StringBuilder sb] : m= INT ;
   public final void parse_PatternMonth( RecurrPatternLanguage lang,
                                         StringBuilder sb ) throws RecognitionException
   {
      Token m = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:376:4:
         // (m= INT )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:376:6:
         // m= INT
         {
            m = (Token) match( input, INT, FOLLOW_INT_in_parse_PatternMonth2035 );
            
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
   
   // $ANTLR start "parse_PatternMonth1"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:386:1:
   // parse_PatternMonth1[Map< Integer, List< Object > > elements] : OP_BYMONTH m= INT ;
   public final void parse_PatternMonth1( Map< Integer, List< Object > > elements ) throws RecognitionException
   {
      Token m = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:387:4: (
         // OP_BYMONTH m= INT )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:387:6:
         // OP_BYMONTH m= INT
         {
            match( input,
                   OP_BYMONTH,
                   FOLLOW_OP_BYMONTH_in_parse_PatternMonth12074 );
            m = (Token) match( input,
                               INT,
                               FOLLOW_INT_in_parse_PatternMonth12078 );
            
            addElement( elements,
                        OP_BYMONTH,
                        Integer.parseInt( ( m != null ? m.getText() : null ) ) );
            
         }
         
      }
      catch ( NumberFormatException e )
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
      return;
   }
   


   // $ANTLR end "parse_PatternMonth1"
   
   // $ANTLR start "parse_PatternMonthDay1"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:401:1:
   // parse_PatternMonthDay1[Map< Integer, List< Object > > elements] : OP_BYMONTHDAY (m= INT ( COMMA m1= INT )* ) ;
   public final void parse_PatternMonthDay1( Map< Integer, List< Object > > elements ) throws RecognitionException
   {
      Token m = null;
      Token m1 = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:402:4: (
         // OP_BYMONTHDAY (m= INT ( COMMA m1= INT )* ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:402:6:
         // OP_BYMONTHDAY (m= INT ( COMMA m1= INT )* )
         {
            match( input,
                   OP_BYMONTHDAY,
                   FOLLOW_OP_BYMONTHDAY_in_parse_PatternMonthDay12126 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:403:6:
            // (m= INT ( COMMA m1= INT )* )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:404:9:
            // m= INT ( COMMA m1= INT )*
            {
               m = (Token) match( input,
                                  INT,
                                  FOLLOW_INT_in_parse_PatternMonthDay12146 );
               
               addElement( elements,
                           OP_BYMONTHDAY,
                           Integer.parseInt( ( m != null ? m.getText() : null ) ) );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:408:9:
               // ( COMMA m1= INT )*
               loop20: do
               {
                  int alt20 = 2;
                  int LA20_0 = input.LA( 1 );
                  
                  if ( ( LA20_0 == COMMA ) )
                  {
                     alt20 = 1;
                  }
                  
                  switch ( alt20 )
                  {
                     case 1:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RecurrencePattern.g:409:12:
                        // COMMA m1= INT
                     {
                        match( input,
                               COMMA,
                               FOLLOW_COMMA_in_parse_PatternMonthDay12179 );
                        m1 = (Token) match( input,
                                            INT,
                                            FOLLOW_INT_in_parse_PatternMonthDay12183 );
                        
                        addElement( elements,
                                    OP_BYMONTHDAY,
                                    Integer.parseInt( ( m1 != null
                                                                  ? m1.getText()
                                                                  : null ) ) );
                        
                     }
                        break;
                     
                     default :
                        break loop20;
                  }
               }
               while ( true );
               
            }
            
         }
         
      }
      catch ( NumberFormatException e )
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
      return;
   }
   
   // $ANTLR end "parse_PatternMonthDay1"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_OP_FREQ_in_parseRecurrencePattern70 = new BitSet( new long[]
   { 0x0000000000001A20L } );
   
   public static final BitSet FOLLOW_VAL_YEARLY_in_parseRecurrencePattern104 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern106 = new BitSet( new long[]
   { 0x000000000000A042L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern180 = new BitSet( new long[]
   { 0x0000000001FD0000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern182 = new BitSet( new long[]
   { 0x0000000000000180L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern208 = new BitSet( new long[]
   { 0x0000000001FD0000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern212 = new BitSet( new long[]
   { 0x0000000000000180L } );
   
   public static final BitSet FOLLOW_OP_BYMONTH_in_parseRecurrencePattern258 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternMonth_in_parseRecurrencePattern260 = new BitSet( new long[]
   { 0x000000000000A002L } );
   
   public static final BitSet FOLLOW_VAL_MONTHLY_in_parseRecurrencePattern334 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern336 = new BitSet( new long[]
   { 0x000000000000A442L } );
   
   public static final BitSet FOLLOW_OP_BYMONTHDAY_in_parseRecurrencePattern437 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parseRecurrencePattern439 = new BitSet( new long[]
   { 0x000000000000A082L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern470 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parseRecurrencePattern474 = new BitSet( new long[]
   { 0x000000000000A082L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern549 = new BitSet( new long[]
   { 0x0000000001FD0000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern551 = new BitSet( new long[]
   { 0x000000000000A082L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern583 = new BitSet( new long[]
   { 0x0000000001FD0000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern587 = new BitSet( new long[]
   { 0x000000000000A082L } );
   
   public static final BitSet FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern686 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern688 = new BitSet( new long[]
   { 0x000000000000A042L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern740 = new BitSet( new long[]
   { 0x0000000001FD0000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern742 = new BitSet( new long[]
   { 0x000000000000A082L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern765 = new BitSet( new long[]
   { 0x0000000001FD0000L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern769 = new BitSet( new long[]
   { 0x000000000000A082L } );
   
   public static final BitSet FOLLOW_VAL_DAILY_in_parseRecurrencePattern827 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern829 = new BitSet( new long[]
   { 0x000000000000A002L } );
   
   public static final BitSet FOLLOW_OP_UNTIL_in_parseRecurrencePattern868 = new BitSet( new long[]
   { 0x0000000000004000L } );
   
   public static final BitSet FOLLOW_VAL_DATE_in_parseRecurrencePattern872 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_COUNT_in_parseRecurrencePattern896 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_INT_in_parseRecurrencePattern900 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_FREQ_in_parseRecurrencePattern1967 = new BitSet( new long[]
   { 0x0000000000001A20L } );
   
   public static final BitSet FOLLOW_VAL_YEARLY_in_parseRecurrencePattern11001 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11003 = new BitSet( new long[]
   { 0x000000000000A042L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday1_in_parseRecurrencePattern11048 = new BitSet( new long[]
   { 0x0000000000000100L } );
   
   public static final BitSet FOLLOW_parse_PatternMonth1_in_parseRecurrencePattern11051 = new BitSet( new long[]
   { 0x000000000000A002L } );
   
   public static final BitSet FOLLOW_VAL_MONTHLY_in_parseRecurrencePattern11093 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11095 = new BitSet( new long[]
   { 0x000000000000A442L } );
   
   public static final BitSet FOLLOW_parse_PatternMonthDay1_in_parseRecurrencePattern11127 = new BitSet( new long[]
   { 0x000000000000A002L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday1_in_parseRecurrencePattern11132 = new BitSet( new long[]
   { 0x000000000000A002L } );
   
   public static final BitSet FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern11174 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11176 = new BitSet( new long[]
   { 0x000000000000A042L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday1_in_parseRecurrencePattern11207 = new BitSet( new long[]
   { 0x000000000000A002L } );
   
   public static final BitSet FOLLOW_VAL_DAILY_in_parseRecurrencePattern11248 = new BitSet( new long[]
   { 0x0000000000020000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11250 = new BitSet( new long[]
   { 0x000000000000A002L } );
   
   public static final BitSet FOLLOW_OP_UNTIL_in_parseRecurrencePattern11304 = new BitSet( new long[]
   { 0x0000000000004000L } );
   
   public static final BitSet FOLLOW_VAL_DATE_in_parseRecurrencePattern11308 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_COUNT_in_parseRecurrencePattern11332 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_INT_in_parseRecurrencePattern11336 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_INTERVAL_in_parse_PatternInterval1397 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternInterval1401 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_INTERVAL_in_parse_PatternInterval11437 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternInterval11441 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternXst1491 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parse_PatternWeekday1562 = new BitSet( new long[]
   { 0x0000000001FC0000L } );
   
   public static final BitSet FOLLOW_set_in_parse_PatternWeekday1612 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parse_PatternWeekday11790 = new BitSet( new long[]
   { 0x0000000001FD0000L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternWeekday11796 = new BitSet( new long[]
   { 0x0000000001FC0000L } );
   
   public static final BitSet FOLLOW_set_in_parse_PatternWeekday11802 = new BitSet( new long[]
   { 0x0000000001FD0082L } );
   
   public static final BitSet FOLLOW_COMMA_in_parse_PatternWeekday12001 = new BitSet( new long[]
   { 0x0000000001FD0002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternMonth2035 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYMONTH_in_parse_PatternMonth12074 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternMonth12078 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYMONTHDAY_in_parse_PatternMonthDay12126 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternMonthDay12146 = new BitSet( new long[]
   { 0x0000000000000082L } );
   
   public static final BitSet FOLLOW_COMMA_in_parse_PatternMonthDay12179 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternMonthDay12183 = new BitSet( new long[]
   { 0x0000000000000082L } );
   
}
