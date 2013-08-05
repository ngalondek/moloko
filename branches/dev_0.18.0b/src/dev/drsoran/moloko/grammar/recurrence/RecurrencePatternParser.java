// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g 2013-03-03 20:00:57

package dev.drsoran.moloko.grammar.recurrence;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.domain.parsing.IDateFormatter;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class RecurrencePatternParser extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMA", "EQUALS", "FRIDAY",
    "INT", "MINUS", "MONDAY", "NUMBER", "OP_BYDAY", "OP_BYMONTH",
    "OP_BYMONTHDAY", "OP_COUNT", "OP_FREQ", "OP_INTERVAL", "OP_UNTIL",
    "SATURDAY", "SEMICOLON", "SUNDAY", "THURSDAY", "TUESDAY", "VAL_DAILY",
    "VAL_DATE", "VAL_MONTHLY", "VAL_WEEKLY", "VAL_YEARLY", "WEDNESDAY", "WS" };
   
   public static final int EOF = -1;
   
   public static final int COMMA = 4;
   
   public static final int EQUALS = 5;
   
   public static final int FRIDAY = 6;
   
   public static final int INT = 7;
   
   public static final int MINUS = 8;
   
   public static final int MONDAY = 9;
   
   public static final int NUMBER = 10;
   
   public static final int OP_BYDAY = 11;
   
   public static final int OP_BYMONTH = 12;
   
   public static final int OP_BYMONTHDAY = 13;
   
   public static final int OP_COUNT = 14;
   
   public static final int OP_FREQ = 15;
   
   public static final int OP_INTERVAL = 16;
   
   public static final int OP_UNTIL = 17;
   
   public static final int SATURDAY = 18;
   
   public static final int SEMICOLON = 19;
   
   public static final int SUNDAY = 20;
   
   public static final int THURSDAY = 21;
   
   public static final int TUESDAY = 22;
   
   public static final int VAL_DAILY = 23;
   
   public static final int VAL_DATE = 24;
   
   public static final int VAL_MONTHLY = 25;
   
   public static final int VAL_WEEKLY = 26;
   
   public static final int VAL_YEARLY = 27;
   
   public static final int WEDNESDAY = 28;
   
   public static final int WS = 29;
   
   
   
   // delegates
   public Parser[] getDelegates()
   {
      return new Parser[] {};
   }
   
   
   
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
   
   
   
   public String[] getTokenNames()
   {
      return RecurrencePatternParser.tokenNames;
   }
   
   
   
   public String getGrammarFileName()
   {
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g";
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
            return 6;
         else
            return Integer.MAX_VALUE;
      }
      
      
      
      public int compare( String op1, String op2 )
      {
         return operatorToInt( op1 ) - operatorToInt( op2 );
      }
   }
   
   
   public final static class OpByDayVal
   {
      public final Integer qualifier;
      
      public final int weekday;
      
      
      
      public OpByDayVal( Integer qualifier, int weekday )
      {
         this.qualifier = qualifier;
         this.weekday = weekday;
      }
   }
   
   
   
   public void setDateFormatter( IDateFormatter dateFormatter )
   {
      this.dateFormatter = dateFormatter;
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
   
   
   
   private final String formatDateUntil( String value )
   {
      final SimpleDateFormat sdf = new SimpleDateFormat( DATE_PATTERN );
      sdf.setTimeZone( TimeZone.getDefault() );
      
      try
      {
         sdf.parse( value );
         
         if ( dateFormatter != null )
         {
            return dateFormatter.formatDateNumeric( sdf.getCalendar()
                                                       .getTimeInMillis() );
         }
         else
         {
            return DateFormat.getDateInstance( DateFormat.SHORT )
                             .format( sdf.getCalendar().getTime() );
         }
      }
      catch ( ParseException e )
      {
         return null;
      }
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
   
   public final static DateFormat DATE_FORMAT = new SimpleDateFormat( DATE_PATTERN );
   
   private IDateFormatter dateFormatter;
   
   static
   {
      DATE_FORMAT.setTimeZone( TimeZone.getTimeZone( "UTC" ) );
   }
   
   
   
   // $ANTLR start "parseRecurrencePattern"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:184:1:
   // parseRecurrencePattern[IRecurrenceSentenceLanguage lang,\r\n boolean every] returns [String sentence] : OP_FREQ (
   // ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true]
   // ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] ) )? ) | ( VAL_MONTHLY
   // parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA
   // parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA
   // parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every]
   // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | (
   // VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )? ;
   public final String parseRecurrencePattern( IRecurrenceSentenceLanguage lang,
                                               boolean every ) throws RecognitionException
   {
      String sentence = null;
      
      Token date = null;
      Token count = null;
      
      final StringBuilder sb = new StringBuilder();
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:194:4:
         // ( OP_FREQ ( ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
         // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
         // parse_PatternMonth[lang, sb] ) )? ) | ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( (
         // OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
         // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? ) | (
         // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang, sb, true]
         // ( COMMA parse_PatternWeekday[lang, sb, false] )* )? ) | ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\",
         // every] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:194:6:
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
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:195:6:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:196:11:
               // ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
               // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
               // parse_PatternMonth[lang, sb] ) )? )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:196:11:
                  // ( VAL_YEARLY parse_PatternInterval[lang, sb, \"year\", every] ( ( ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) OP_BYMONTH
                  // parse_PatternMonth[lang, sb] ) )? )
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:197:14:
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
                     
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:198:14:
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
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:199:17:
                        // ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                        // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] )
                        {
                           
                           sb.append( " " );
                           lang.append( sb, "on_the" );
                           sb.append( " " );
                           
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:202:17:
                           // ( ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb] )
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:203:20:
                           // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                           // false] )* ) OP_BYMONTH parse_PatternMonth[lang, sb]
                           {
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:203:20:
                              // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                              // false] )* )
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:203:21:
                              // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                              // false] )*
                              {
                                 match( input,
                                        OP_BYDAY,
                                        FOLLOW_OP_BYDAY_in_parseRecurrencePattern180 );
                                 
                                 pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern182 );
                                 parse_PatternWeekday( lang, sb, true );
                                 
                                 state._fsp--;
                                 
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:204:23:
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
                                       // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:204:24:
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
                              lang.append( sb, "in" );
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:212:11:
               // ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
               // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
               // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:212:11:
                  // ( VAL_MONTHLY parse_PatternInterval[lang, sb, \"month\", every] ( ( ( OP_BYMONTHDAY
                  // parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | ( OP_BYDAY
                  // parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* ) ) )? )
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:213:14:
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
                     
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:214:14:
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
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:215:17:
                        // ( ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* ) | (
                        // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false]
                        // )* ) )
                        {
                           
                           sb.append( " " );
                           lang.append( sb, "on_the" );
                           sb.append( " " );
                           
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:218:17:
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
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:219:22:
                              // ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* )
                              {
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:219:22:
                                 // ( OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )* )
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:220:25:
                                 // OP_BYMONTHDAY parse_PatternXst[lang, sb] ( COMMA parse_PatternXst[lang, sb] )*
                                 {
                                    match( input,
                                           OP_BYMONTHDAY,
                                           FOLLOW_OP_BYMONTHDAY_in_parseRecurrencePattern437 );
                                    
                                    pushFollow( FOLLOW_parse_PatternXst_in_parseRecurrencePattern439 );
                                    parse_PatternXst( lang, sb );
                                    
                                    state._fsp--;
                                    
                                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:221:28:
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
                                          // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:221:29:
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
                              // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:223:22:
                              // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                              // false] )* )
                              {
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:223:22:
                                 // ( OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang,
                                 // sb, false] )* )
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:224:25:
                                 // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb,
                                 // false] )*
                                 {
                                    match( input,
                                           OP_BYDAY,
                                           FOLLOW_OP_BYDAY_in_parseRecurrencePattern549 );
                                    
                                    pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern551 );
                                    parse_PatternWeekday( lang, sb, true );
                                    
                                    state._fsp--;
                                    
                                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:225:29:
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
                                          // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:225:30:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:230:11:
               // ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
               // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:230:11:
                  // ( VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )? )
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:231:14:
                  // VAL_WEEKLY parse_PatternInterval[lang, sb, \"week\", every] ( OP_BYDAY parse_PatternWeekday[lang,
                  // sb, true] ( COMMA parse_PatternWeekday[lang, sb, false] )* )?
                  {
                     match( input,
                            VAL_WEEKLY,
                            FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern686 );
                     
                     pushFollow( FOLLOW_parse_PatternInterval_in_parseRecurrencePattern688 );
                     parse_PatternInterval( lang, sb, "week", every );
                     
                     state._fsp--;
                     
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:232:14:
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
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:233:17:
                        // OP_BYDAY parse_PatternWeekday[lang, sb, true] ( COMMA parse_PatternWeekday[lang, sb, false]
                        // )*
                        {
                           
                           sb.append( " " );
                           lang.append( sb, "on_the" );
                           sb.append( " " );
                           
                           match( input,
                                  OP_BYDAY,
                                  FOLLOW_OP_BYDAY_in_parseRecurrencePattern740 );
                           
                           pushFollow( FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern742 );
                           parse_PatternWeekday( lang, sb, true );
                           
                           state._fsp--;
                           
                           // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:237:20:
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
                                 // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:237:21:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:240:11:
               // ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:240:11:
                  // ( VAL_DAILY parse_PatternInterval[lang, sb, \"day\", every] )
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:241:14:
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
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:244:6:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:245:11:
               // OP_UNTIL date= VAL_DATE
               {
                  match( input,
                         OP_UNTIL,
                         FOLLOW_OP_UNTIL_in_parseRecurrencePattern868 );
                  
                  date = (Token) match( input,
                                        VAL_DATE,
                                        FOLLOW_VAL_DATE_in_parseRecurrencePattern872 );
                  
                  final String formatedDate = formatDateUntil( ( date != null
                                                                             ? date.getText()
                                                                             : null ) );
                  
                  if ( formatedDate != null )
                  {
                     sb.append( " " );
                     lang.append( sb, "until" );
                     sb.append( " " );
                     sb.append( formatedDate );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:255:11:
               // OP_COUNT count= INT
               {
                  match( input,
                         OP_COUNT,
                         FOLLOW_OP_COUNT_in_parseRecurrencePattern896 );
                  
                  count = (Token) match( input,
                                         INT,
                                         FOLLOW_INT_in_parseRecurrencePattern900 );
                  
                  sb.append( " " );
                  lang.append( sb, "for" );
                  sb.append( " " );
                  sb.append( ( count != null ? count.getText() : null ) );
                  sb.append( " " );
                  lang.append( sb, "times" );
                  
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
         // do for sure before leaving
      }
      return sentence;
   }
   
   
   
   // $ANTLR end "parseRecurrencePattern"
   
   // $ANTLR start "parseRecurrencePattern1"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:268:1:
   // parseRecurrencePattern1 returns [Map< Integer, List< Object > > elements] : OP_FREQ ( ( VAL_YEARLY
   // parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] parse_PatternMonth1[elements] )? ) | (
   // VAL_MONTHLY parse_PatternInterval1[elements] ( parse_PatternMonthDay1[elements] | parse_PatternWeekday1[elements]
   // )? ) | ( VAL_WEEKLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? ) | ( VAL_DAILY
   // parse_PatternInterval1[elements] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )? ;
   public final Map< Integer, List< Object > > parseRecurrencePattern1() throws RecognitionException
   {
      Map< Integer, List< Object > > elements = null;
      
      Token date = null;
      Token count = null;
      
      elements = new HashMap< Integer, List< Object > >();
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:273:4:
         // ( OP_FREQ ( ( VAL_YEARLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements]
         // parse_PatternMonth1[elements] )? ) | ( VAL_MONTHLY parse_PatternInterval1[elements] (
         // parse_PatternMonthDay1[elements] | parse_PatternWeekday1[elements] )? ) | ( VAL_WEEKLY
         // parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? ) | ( VAL_DAILY
         // parse_PatternInterval1[elements] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:273:6:
         // OP_FREQ ( ( VAL_YEARLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements]
         // parse_PatternMonth1[elements] )? ) | ( VAL_MONTHLY parse_PatternInterval1[elements] (
         // parse_PatternMonthDay1[elements] | parse_PatternWeekday1[elements] )? ) | ( VAL_WEEKLY
         // parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? ) | ( VAL_DAILY
         // parse_PatternInterval1[elements] ) ) ( OP_UNTIL date= VAL_DATE | OP_COUNT count= INT )?
         {
            match( input, OP_FREQ, FOLLOW_OP_FREQ_in_parseRecurrencePattern1967 );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:274:6:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:275:11:
               // ( VAL_YEARLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements]
               // parse_PatternMonth1[elements] )? )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:275:11:
                  // ( VAL_YEARLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements]
                  // parse_PatternMonth1[elements] )? )
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:276:14:
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
                     
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:280:14:
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
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:280:15:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:282:11:
               // ( VAL_MONTHLY parse_PatternInterval1[elements] ( parse_PatternMonthDay1[elements] |
               // parse_PatternWeekday1[elements] )? )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:282:11:
                  // ( VAL_MONTHLY parse_PatternInterval1[elements] ( parse_PatternMonthDay1[elements] |
                  // parse_PatternWeekday1[elements] )? )
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:283:14:
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
                     
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:287:14:
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
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:287:15:
                        // parse_PatternMonthDay1[elements]
                        {
                           pushFollow( FOLLOW_parse_PatternMonthDay1_in_parseRecurrencePattern11127 );
                           parse_PatternMonthDay1( elements );
                           
                           state._fsp--;
                           
                        }
                           break;
                        case 2:
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:287:50:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:289:11:
               // ( VAL_WEEKLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:289:11:
                  // ( VAL_WEEKLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )? )
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:290:14:
                  // VAL_WEEKLY parse_PatternInterval1[elements] ( parse_PatternWeekday1[elements] )?
                  {
                     match( input,
                            VAL_WEEKLY,
                            FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern11174 );
                     
                     pushFollow( FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11176 );
                     parse_PatternInterval1( elements );
                     
                     state._fsp--;
                     
                     addElement( elements, OP_FREQ, VAL_WEEKLY );
                     
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:294:14:
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
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:294:14:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:296:11:
               // ( VAL_DAILY parse_PatternInterval1[elements] )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:296:11:
                  // ( VAL_DAILY parse_PatternInterval1[elements] )
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:297:14:
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
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:303:6:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:304:11:
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
                              DATE_FORMAT.parse( ( date != null
                                                               ? date.getText()
                                                               : null ) ) );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:308:11:
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
      catch ( ParseException e )
      {
         
         throw new RecognitionException();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return elements;
   }
   
   
   
   // $ANTLR end "parseRecurrencePattern1"
   
   // $ANTLR start "parse_PatternInterval"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:323:1:
   // parse_PatternInterval[IRecurrenceSentenceLanguage lang,\r\n StringBuilder sb,\r\n String unit,\r\n boolean
   // isEvery] : OP_INTERVAL interval= INT ;
   public final void parse_PatternInterval( IRecurrenceSentenceLanguage lang,
                                            StringBuilder sb,
                                            String unit,
                                            boolean isEvery ) throws RecognitionException
   {
      Token interval = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:327:4:
         // ( OP_INTERVAL interval= INT )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:327:6:
         // OP_INTERVAL interval= INT
         {
            match( input,
                   OP_INTERVAL,
                   FOLLOW_OP_INTERVAL_in_parse_PatternInterval1397 );
            
            interval = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_parse_PatternInterval1401 );
            
            if ( isEvery )
               lang.appendEvery( sb, unit, ( interval != null ? interval.getText()
                                                          : null ) );
            else
               lang.appendAfter( sb, unit, ( interval != null ? interval.getText()
                                                          : null ) );
            
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
   
   
   
   // $ANTLR end "parse_PatternInterval"
   
   // $ANTLR start "parse_PatternInterval1"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:340:1:
   // parse_PatternInterval1[Map< Integer, List< Object > > elements] : OP_INTERVAL interval= INT ;
   public final void parse_PatternInterval1( Map< Integer, List< Object > > elements ) throws RecognitionException
   {
      Token interval = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:341:4:
         // ( OP_INTERVAL interval= INT )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:341:6:
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "parse_PatternInterval1"
   
   // $ANTLR start "parse_PatternXst"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:355:1:
   // parse_PatternXst[IRecurrenceSentenceLanguage lang, StringBuilder sb] : x= INT ;
   public final void parse_PatternXst( IRecurrenceSentenceLanguage lang,
                                       StringBuilder sb ) throws RecognitionException
   {
      Token x = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:356:4:
         // (x= INT )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:356:6:
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
                     lang.appendStToX( sb, xSt * -1 );
                     sb.append( " " );
                  }
                  
                  lang.append( sb, "last" );
               }
               else
                  lang.appendStToX( sb, xSt );
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "parse_PatternXst"
   
   // $ANTLR start "parse_PatternWeekday"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:386:1:
   // parse_PatternWeekday[IRecurrenceSentenceLanguage lang,\r\n StringBuilder sb,\r\n boolean respectXst] : ( (
   // parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY |
   // SATURDAY | SUNDAY ) ) ) ;
   public final void parse_PatternWeekday( IRecurrenceSentenceLanguage lang,
                                           StringBuilder sb,
                                           boolean respectXst ) throws RecognitionException
   {
      Token wd = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:389:4:
         // ( ( ( parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
         // FRIDAY | SATURDAY | SUNDAY ) ) ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:389:6:
         // ( ( parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
         // FRIDAY | SATURDAY | SUNDAY ) ) )
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:389:6:
            // ( ( parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
            // FRIDAY | SATURDAY | SUNDAY ) ) )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:390:9:
            // ( parse_PatternXst[lang, respectXst ? sb : null] )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY |
            // FRIDAY | SATURDAY | SUNDAY ) )
            {
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:390:9:
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
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:391:12:
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
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:397:9:
               // (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) )
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:398:12:
               // wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY )
               {
                  wd = (Token) input.LT( 1 );
                  
                  if ( input.LA( 1 ) == FRIDAY || input.LA( 1 ) == MONDAY
                     || input.LA( 1 ) == SATURDAY
                     || ( input.LA( 1 ) >= SUNDAY && input.LA( 1 ) <= TUESDAY )
                     || input.LA( 1 ) == WEDNESDAY )
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
                  
                  lang.append( sb, ( wd != null ? wd.getText() : null ) );
                  
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "parse_PatternWeekday"
   
   // $ANTLR start "parse_PatternWeekday1"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:416:1:
   // parse_PatternWeekday1[Map< Integer, List< Object > > elements] : OP_BYDAY ( (x= INT )? (wd= ( MONDAY | TUESDAY |
   // WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) ) ( COMMA )? )+ ;
   public final void parse_PatternWeekday1( Map< Integer, List< Object > > elements ) throws RecognitionException
   {
      Token x = null;
      Token wd = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:417:4:
         // ( OP_BYDAY ( (x= INT )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) ) (
         // COMMA )? )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:417:6:
         // OP_BYDAY ( (x= INT )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) ) (
         // COMMA )? )+
         {
            match( input,
                   OP_BYDAY,
                   FOLLOW_OP_BYDAY_in_parse_PatternWeekday11790 );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:418:6:
            // ( (x= INT )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) ) ( COMMA )?
            // )+
            int cnt19 = 0;
            loop19: do
            {
               int alt19 = 2;
               int LA19_0 = input.LA( 1 );
               
               if ( ( ( LA19_0 >= FRIDAY && LA19_0 <= INT ) || LA19_0 == MONDAY
                  || LA19_0 == SATURDAY
                  || ( LA19_0 >= SUNDAY && LA19_0 <= TUESDAY ) || LA19_0 == WEDNESDAY ) )
               {
                  alt19 = 1;
               }
               
               switch ( alt19 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:419:9:
                  // (x= INT )? (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) ) ( COMMA
                  // )?
                  {
                     
                     Integer qualifier = null;
                     
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:422:9:
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
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:423:12:
                        // x= INT
                        {
                           x = (Token) match( input,
                                              INT,
                                              FOLLOW_INT_in_parse_PatternWeekday11832 );
                           
                           qualifier = Integer.parseInt( ( x != null
                                                                    ? x.getText()
                                                                    : null ) );
                           
                        }
                           break;
                     
                     }
                     
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:428:9:
                     // (wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY ) )
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:429:12:
                     // wd= ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY )
                     {
                        wd = (Token) input.LT( 1 );
                        
                        if ( input.LA( 1 ) == FRIDAY
                           || input.LA( 1 ) == MONDAY
                           || input.LA( 1 ) == SATURDAY
                           || ( input.LA( 1 ) >= SUNDAY && input.LA( 1 ) <= TUESDAY )
                           || input.LA( 1 ) == WEDNESDAY )
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
                        
                        addElement( elements,
                                    OP_BYDAY,
                                    new OpByDayVal( qualifier,
                                                    ( wd != null ? wd.getType()
                                                                : 0 ) ) );
                        
                     }
                     
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:439:11:
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
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:439:11:
                        // COMMA
                        {
                           match( input,
                                  COMMA,
                                  FOLLOW_COMMA_in_parse_PatternWeekday12032 );
                           
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "parse_PatternWeekday1"
   
   // $ANTLR start "parse_PatternMonth"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:451:1:
   // parse_PatternMonth[IRecurrenceSentenceLanguage lang, StringBuilder sb] : m= INT ;
   public final void parse_PatternMonth( IRecurrenceSentenceLanguage lang,
                                         StringBuilder sb ) throws RecognitionException
   {
      Token m = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:452:4:
         // (m= INT )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:452:6:
         // m= INT
         {
            m = (Token) match( input, INT, FOLLOW_INT_in_parse_PatternMonth2084 );
            
            lang.append( sb, "m" + ( m != null ? m.getText() : null ) );
            
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
   
   
   
   // $ANTLR end "parse_PatternMonth"
   
   // $ANTLR start "parse_PatternMonth1"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:462:1:
   // parse_PatternMonth1[Map< Integer, List< Object > > elements] : OP_BYMONTH m= INT ;
   public final void parse_PatternMonth1( Map< Integer, List< Object > > elements ) throws RecognitionException
   {
      Token m = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:463:4:
         // ( OP_BYMONTH m= INT )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:463:6:
         // OP_BYMONTH m= INT
         {
            match( input,
                   OP_BYMONTH,
                   FOLLOW_OP_BYMONTH_in_parse_PatternMonth12123 );
            
            m = (Token) match( input,
                               INT,
                               FOLLOW_INT_in_parse_PatternMonth12127 );
            
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "parse_PatternMonth1"
   
   // $ANTLR start "parse_PatternMonthDay1"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:477:1:
   // parse_PatternMonthDay1[Map< Integer, List< Object > > elements] : OP_BYMONTHDAY (m= INT ( COMMA m1= INT )* ) ;
   public final void parse_PatternMonthDay1( Map< Integer, List< Object > > elements ) throws RecognitionException
   {
      Token m = null;
      Token m1 = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:478:4:
         // ( OP_BYMONTHDAY (m= INT ( COMMA m1= INT )* ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:478:6:
         // OP_BYMONTHDAY (m= INT ( COMMA m1= INT )* )
         {
            match( input,
                   OP_BYMONTHDAY,
                   FOLLOW_OP_BYMONTHDAY_in_parse_PatternMonthDay12175 );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:479:6:
            // (m= INT ( COMMA m1= INT )* )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:480:9:
            // m= INT ( COMMA m1= INT )*
            {
               m = (Token) match( input,
                                  INT,
                                  FOLLOW_INT_in_parse_PatternMonthDay12195 );
               
               addElement( elements,
                           OP_BYMONTHDAY,
                           Integer.parseInt( ( m != null ? m.getText() : null ) ) );
               
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:484:9:
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
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\recurrence\\RecurrencePattern.g:485:12:
                     // COMMA m1= INT
                     {
                        match( input,
                               COMMA,
                               FOLLOW_COMMA_in_parse_PatternMonthDay12228 );
                        
                        m1 = (Token) match( input,
                                            INT,
                                            FOLLOW_INT_in_parse_PatternMonthDay12232 );
                        
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
         // do for sure before leaving
      }
      return;
   }
   
   // $ANTLR end "parse_PatternMonthDay1"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_OP_FREQ_in_parseRecurrencePattern70 = new BitSet( new long[]
   { 0x000000000E800000L } );
   
   public static final BitSet FOLLOW_VAL_YEARLY_in_parseRecurrencePattern104 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern106 = new BitSet( new long[]
   { 0x0000000000024802L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern180 = new BitSet( new long[]
   { 0x00000000107402C0L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern182 = new BitSet( new long[]
   { 0x0000000000001010L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern208 = new BitSet( new long[]
   { 0x00000000107402C0L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern212 = new BitSet( new long[]
   { 0x0000000000001010L } );
   
   public static final BitSet FOLLOW_OP_BYMONTH_in_parseRecurrencePattern258 = new BitSet( new long[]
   { 0x0000000000000080L } );
   
   public static final BitSet FOLLOW_parse_PatternMonth_in_parseRecurrencePattern260 = new BitSet( new long[]
   { 0x0000000000024002L } );
   
   public static final BitSet FOLLOW_VAL_MONTHLY_in_parseRecurrencePattern334 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern336 = new BitSet( new long[]
   { 0x0000000000026802L } );
   
   public static final BitSet FOLLOW_OP_BYMONTHDAY_in_parseRecurrencePattern437 = new BitSet( new long[]
   { 0x0000000000000080L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parseRecurrencePattern439 = new BitSet( new long[]
   { 0x0000000000024012L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern470 = new BitSet( new long[]
   { 0x0000000000000080L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parseRecurrencePattern474 = new BitSet( new long[]
   { 0x0000000000024012L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern549 = new BitSet( new long[]
   { 0x00000000107402C0L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern551 = new BitSet( new long[]
   { 0x0000000000024012L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern583 = new BitSet( new long[]
   { 0x00000000107402C0L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern587 = new BitSet( new long[]
   { 0x0000000000024012L } );
   
   public static final BitSet FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern686 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern688 = new BitSet( new long[]
   { 0x0000000000024802L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parseRecurrencePattern740 = new BitSet( new long[]
   { 0x00000000107402C0L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern742 = new BitSet( new long[]
   { 0x0000000000024012L } );
   
   public static final BitSet FOLLOW_COMMA_in_parseRecurrencePattern765 = new BitSet( new long[]
   { 0x00000000107402C0L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday_in_parseRecurrencePattern769 = new BitSet( new long[]
   { 0x0000000000024012L } );
   
   public static final BitSet FOLLOW_VAL_DAILY_in_parseRecurrencePattern827 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval_in_parseRecurrencePattern829 = new BitSet( new long[]
   { 0x0000000000024002L } );
   
   public static final BitSet FOLLOW_OP_UNTIL_in_parseRecurrencePattern868 = new BitSet( new long[]
   { 0x0000000001000000L } );
   
   public static final BitSet FOLLOW_VAL_DATE_in_parseRecurrencePattern872 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_COUNT_in_parseRecurrencePattern896 = new BitSet( new long[]
   { 0x0000000000000080L } );
   
   public static final BitSet FOLLOW_INT_in_parseRecurrencePattern900 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_FREQ_in_parseRecurrencePattern1967 = new BitSet( new long[]
   { 0x000000000E800000L } );
   
   public static final BitSet FOLLOW_VAL_YEARLY_in_parseRecurrencePattern11001 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11003 = new BitSet( new long[]
   { 0x0000000000024802L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday1_in_parseRecurrencePattern11048 = new BitSet( new long[]
   { 0x0000000000001000L } );
   
   public static final BitSet FOLLOW_parse_PatternMonth1_in_parseRecurrencePattern11051 = new BitSet( new long[]
   { 0x0000000000024002L } );
   
   public static final BitSet FOLLOW_VAL_MONTHLY_in_parseRecurrencePattern11093 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11095 = new BitSet( new long[]
   { 0x0000000000026802L } );
   
   public static final BitSet FOLLOW_parse_PatternMonthDay1_in_parseRecurrencePattern11127 = new BitSet( new long[]
   { 0x0000000000024002L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday1_in_parseRecurrencePattern11132 = new BitSet( new long[]
   { 0x0000000000024002L } );
   
   public static final BitSet FOLLOW_VAL_WEEKLY_in_parseRecurrencePattern11174 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11176 = new BitSet( new long[]
   { 0x0000000000024802L } );
   
   public static final BitSet FOLLOW_parse_PatternWeekday1_in_parseRecurrencePattern11207 = new BitSet( new long[]
   { 0x0000000000024002L } );
   
   public static final BitSet FOLLOW_VAL_DAILY_in_parseRecurrencePattern11248 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_parse_PatternInterval1_in_parseRecurrencePattern11250 = new BitSet( new long[]
   { 0x0000000000024002L } );
   
   public static final BitSet FOLLOW_OP_UNTIL_in_parseRecurrencePattern11304 = new BitSet( new long[]
   { 0x0000000001000000L } );
   
   public static final BitSet FOLLOW_VAL_DATE_in_parseRecurrencePattern11308 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_COUNT_in_parseRecurrencePattern11332 = new BitSet( new long[]
   { 0x0000000000000080L } );
   
   public static final BitSet FOLLOW_INT_in_parseRecurrencePattern11336 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_INTERVAL_in_parse_PatternInterval1397 = new BitSet( new long[]
   { 0x0000000000000080L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternInterval1401 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_INTERVAL_in_parse_PatternInterval11437 = new BitSet( new long[]
   { 0x0000000000000080L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternInterval11441 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternXst1491 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_parse_PatternXst_in_parse_PatternWeekday1562 = new BitSet( new long[]
   { 0x0000000010740240L } );
   
   public static final BitSet FOLLOW_set_in_parse_PatternWeekday1612 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYDAY_in_parse_PatternWeekday11790 = new BitSet( new long[]
   { 0x00000000107402C0L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternWeekday11832 = new BitSet( new long[]
   { 0x0000000010740240L } );
   
   public static final BitSet FOLLOW_set_in_parse_PatternWeekday11881 = new BitSet( new long[]
   { 0x00000000107402D2L } );
   
   public static final BitSet FOLLOW_COMMA_in_parse_PatternWeekday12032 = new BitSet( new long[]
   { 0x00000000107402C2L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternMonth2084 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYMONTH_in_parse_PatternMonth12123 = new BitSet( new long[]
   { 0x0000000000000080L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternMonth12127 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_OP_BYMONTHDAY_in_parse_PatternMonthDay12175 = new BitSet( new long[]
   { 0x0000000000000080L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternMonthDay12195 = new BitSet( new long[]
   { 0x0000000000000012L } );
   
   public static final BitSet FOLLOW_COMMA_in_parse_PatternMonthDay12228 = new BitSet( new long[]
   { 0x0000000000000080L } );
   
   public static final BitSet FOLLOW_INT_in_parse_PatternMonthDay12232 = new BitSet( new long[]
   { 0x0000000000000012L } );
   
}
