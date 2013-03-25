// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g 2013-03-22 12:33:38

package dev.drsoran.moloko.grammar.datetime.de;

import java.util.Calendar;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.MolokoCalendar;
import dev.drsoran.moloko.grammar.LexerException;
import dev.drsoran.moloko.grammar.datetime.AbstractANTLRDateParser;
import dev.drsoran.moloko.grammar.datetime.ParseDateReturn;
import dev.drsoran.moloko.grammar.datetime.ParseDateWithinReturn;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class DateParser extends AbstractANTLRDateParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "A", "AND", "COLON", "COMMA",
    "DATE_SEP", "DATE_TIME_SEPARATOR", "DAYS", "DOT", "END", "IN", "INT",
    "MINUS", "MONTH", "MONTHS", "NEVER", "NEXT", "NOW", "NUM_STR", "OF",
    "OF_THE", "ON", "SUFF_FMALE", "SUFF_MALE", "TODAY", "TOMORROW", "WEEKDAY",
    "WEEKS", "WS", "YEARS", "YESTERDAY" };
   
   public static final int EOF = -1;
   
   public static final int A = 4;
   
   public static final int AND = 5;
   
   public static final int COLON = 6;
   
   public static final int COMMA = 7;
   
   public static final int DATE_SEP = 8;
   
   public static final int DATE_TIME_SEPARATOR = 9;
   
   public static final int DAYS = 10;
   
   public static final int DOT = 11;
   
   public static final int END = 12;
   
   public static final int IN = 13;
   
   public static final int INT = 14;
   
   public static final int MINUS = 15;
   
   public static final int MONTH = 16;
   
   public static final int MONTHS = 17;
   
   public static final int NEVER = 18;
   
   public static final int NEXT = 19;
   
   public static final int NOW = 20;
   
   public static final int NUM_STR = 21;
   
   public static final int OF = 22;
   
   public static final int OF_THE = 23;
   
   public static final int ON = 24;
   
   public static final int SUFF_FMALE = 25;
   
   public static final int SUFF_MALE = 26;
   
   public static final int TODAY = 27;
   
   public static final int TOMORROW = 28;
   
   public static final int WEEKDAY = 29;
   
   public static final int WEEKS = 30;
   
   public static final int WS = 31;
   
   public static final int YEARS = 32;
   
   public static final int YESTERDAY = 33;
   
   
   
   // delegates
   public AbstractANTLRDateParser[] getDelegates()
   {
      return new AbstractANTLRDateParser[] {};
   }
   
   
   
   // delegators
   
   public DateParser( TokenStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   
   
   
   public DateParser( TokenStream input, RecognizerSharedState state )
   {
      super( input, state );
   }
   
   
   
   public String[] getTokenNames()
   {
      return DateParser.tokenNames;
   }
   
   
   
   public String getGrammarFileName()
   {
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g";
   }
   
   
   
   public DateParser()
   {
      super( null );
   }
   
   
   
   protected int numberStringToNumber( String string )
   {
      final String numStr = string.toLowerCase();
      
      switch ( numStr.charAt( 0 ) )
      {
         case 'e':
            return 1;
         case 'z':
         {
            switch ( numStr.charAt( 1 ) )
            {
               case 'w':
                  return 2;
               case 'e':
                  return 10;
            }
         }
         case 'd':
            return 3;
         case 'v':
            return 4;
         case 'f':
            return 5;
         case 's':
         {
            switch ( numStr.charAt( 1 ) )
            {
               case 'e':
                  return 6;
               case 'i':
                  return 7;
            }
         }
         case 'a':
            return 8;
         default :
            return 9;
      }
   }
   
   
   
   protected int weekdayStringToNumber( String string )
   {
      final String weekDayStr = string.toLowerCase();
      
      switch ( weekDayStr.charAt( 0 ) )
      {
         case 'm':
            switch ( weekDayStr.charAt( 1 ) )
            {
               case 'o':
                  return Calendar.MONDAY;
               case 'i':
                  return Calendar.WEDNESDAY;
            }
         case 'd':
            switch ( weekDayStr.charAt( 1 ) )
            {
               case 'i':
                  return Calendar.TUESDAY;
               case 'o':
                  return Calendar.THURSDAY;
            }
         case 's':
            switch ( weekDayStr.charAt( 1 ) )
            {
               case 'a':
                  return Calendar.SATURDAY;
               case 'o':
                  return Calendar.SUNDAY;
            }
         default :
            return Calendar.FRIDAY;
      }
   }
   
   
   
   protected int monthStringToNumber( String string )
   {
      final String monthStr = string.toLowerCase();
      
      switch ( monthStr.charAt( 0 ) )
      {
         case 'f':
            return Calendar.FEBRUARY;
         case 'm':
            switch ( monthStr.charAt( 2 ) )
            {
               case 'e': // Maerz
               case 'r':
                  return Calendar.MARCH; // März
               case 'i':
                  return Calendar.MAY;
            }
         case 'j':
            switch ( monthStr.charAt( 1 ) )
            {
               case 'a':
                  return Calendar.JANUARY;
               default :
                  switch ( monthStr.charAt( 2 ) )
                  {
                     case 'n':
                        return Calendar.JUNE;
                     case 'l':
                        return Calendar.JULY;
                  }
            }
         case 'a':
            switch ( monthStr.charAt( 1 ) )
            {
               case 'p':
                  return Calendar.APRIL;
               case 'u':
                  return Calendar.AUGUST;
            }
         case 's':
            return Calendar.SEPTEMBER;
         case 'o':
            return Calendar.OCTOBER;
         case 'n':
            return Calendar.NOVEMBER;
         default :
            return Calendar.DECEMBER;
      }
   }
   
   
   
   // $ANTLR start "parseDate"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:166:1:
   // parseDate[MolokoCalendar cal, boolean clearTime] returns [ParseDateReturn result] : ( ( date_numeric[$cal] |
   // date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | NOW | NEVER ) ;
   public final ParseDateReturn parseDate( MolokoCalendar cal, boolean clearTime ) throws RecognitionException
   {
      ParseDateReturn result = null;
      
      startDateParsing( cal );
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:175:4:
         // ( ( ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
         // date_natural[$cal] ) | NOW | NEVER ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:175:5:
         // ( ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
         // date_natural[$cal] ) | NOW | NEVER )
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:175:5:
            // ( ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
            // date_natural[$cal] ) | NOW | NEVER )
            int alt2 = 3;
            switch ( input.LA( 1 ) )
            {
               case END:
               case IN:
               case INT:
               case NEXT:
               case NUM_STR:
               case ON:
               case TODAY:
               case TOMORROW:
               case WEEKDAY:
               case YESTERDAY:
               {
                  alt2 = 1;
               }
                  break;
               case NOW:
               {
                  alt2 = 2;
               }
                  break;
               case NEVER:
               {
                  alt2 = 3;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        2,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
            switch ( alt2 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:176:8:
               // ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
               // date_natural[$cal] )
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:176:8:
                  // ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
                  // date_natural[$cal] )
                  int alt1 = 5;
                  switch ( input.LA( 1 ) )
                  {
                     case INT:
                     {
                        switch ( input.LA( 2 ) )
                        {
                           case DOT:
                           {
                              int LA1_6 = input.LA( 3 );
                              
                              if ( ( LA1_6 == INT ) )
                              {
                                 alt1 = 1;
                              }
                              else if ( ( LA1_6 == EOF || LA1_6 == MONTH || LA1_6 == OF_THE ) )
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
                           case DATE_SEP:
                           case MINUS:
                           {
                              alt1 = 1;
                           }
                              break;
                           case EOF:
                           case MONTH:
                           case OF_THE:
                           {
                              alt1 = 2;
                           }
                              break;
                           case DAYS:
                           case MONTHS:
                           case WEEKS:
                           case YEARS:
                           {
                              alt1 = 3;
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
                     case NEXT:
                     case ON:
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
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:176:12:
                     // date_numeric[$cal]
                     {
                        pushFollow( FOLLOW_date_numeric_in_parseDate118 );
                        date_numeric( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 2:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:177:12:
                     // date_on[$cal]
                     {
                        pushFollow( FOLLOW_date_on_in_parseDate138 );
                        date_on( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 3:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:178:12:
                     // date_in_X_YMWD[$cal]
                     {
                        pushFollow( FOLLOW_date_in_X_YMWD_in_parseDate163 );
                        date_in_X_YMWD( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 4:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:179:12:
                     // date_end_of_the_MW[$cal]
                     {
                        pushFollow( FOLLOW_date_end_of_the_MW_in_parseDate181 );
                        date_end_of_the_MW( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 5:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:180:12:
                     // date_natural[$cal]
                     {
                        pushFollow( FOLLOW_date_natural_in_parseDate195 );
                        date_natural( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                  
                  }
                  
                  cal.setHasDate( isSuccess() );
                  
                  if ( clearTime )
                     cal.setHasTime( false );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:190:9:
               // NOW
               {
                  match( input, NOW, FOLLOW_NOW_in_parseDate254 );
                  
                  cal.setHasDate( true );
                  
                  // In case of NOW we do not respect the clearTime Parameter
                  // cause NOW has always a time.
                  cal.setTimeInMillis( System.currentTimeMillis() );
                  cal.setHasTime( true );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:199:9:
               // NEVER
               {
                  match( input, NEVER, FOLLOW_NEVER_in_parseDate272 );
                  
                  cal.setHasDate( false );
                  cal.setHasTime( false );
                  
               }
                  break;
            
            }
            
         }
         
         result = finishedDateParsing( cal );
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      
      finally
      {
         // do for sure before leaving
      }
      return result;
   }
   
   
   
   // $ANTLR end "parseDate"
   
   // $ANTLR start "parseDateWithin"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:207:1:
   // parseDateWithin[boolean past] returns [ParseDateWithinReturn res] : (a= INT |n= NUM_STR | A ) ( DAYS | WEEKS |
   // MONTHS | YEARS )? ( OF parseDate[epochStart, false] )? ;
   public final ParseDateWithinReturn parseDateWithin( boolean past ) throws RecognitionException
   {
      ParseDateWithinReturn res = null;
      
      Token a = null;
      Token n = null;
      
      final MolokoCalendar epochStart = getCalendar();
      int amount = 1;
      int unit = Calendar.DAY_OF_YEAR;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:222:4:
         // ( (a= INT |n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[epochStart, false] )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:222:6:
         // (a= INT |n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[epochStart, false] )?
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:222:6:
            // (a= INT |n= NUM_STR | A )
            int alt3 = 3;
            switch ( input.LA( 1 ) )
            {
               case INT:
               {
                  alt3 = 1;
               }
                  break;
               case NUM_STR:
               {
                  alt3 = 2;
               }
                  break;
               case A:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:222:9:
               // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_parseDateWithin332 );
                  
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:226:9:
               // n= NUM_STR
               {
                  n = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_parseDateWithin354 );
                  
                  amount = numberStringToNumber( ( n != null ? n.getText()
                                                            : null ) );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:230:9:
               // A
               {
                  match( input, A, FOLLOW_A_in_parseDateWithin374 );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:231:7:
            // ( DAYS | WEEKS | MONTHS | YEARS )?
            int alt4 = 5;
            switch ( input.LA( 1 ) )
            {
               case DAYS:
               {
                  alt4 = 1;
               }
                  break;
               case WEEKS:
               {
                  alt4 = 2;
               }
                  break;
               case MONTHS:
               {
                  alt4 = 3;
               }
                  break;
               case YEARS:
               {
                  alt4 = 4;
               }
                  break;
            }
            
            switch ( alt4 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:231:10:
               // DAYS
               {
                  match( input, DAYS, FOLLOW_DAYS_in_parseDateWithin386 );
                  
                  unit = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:235:10:
               // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_parseDateWithin408 );
                  
                  unit = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:239:10:
               // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_parseDateWithin430 );
                  
                  unit = Calendar.MONTH;
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:243:10:
               // YEARS
               {
                  match( input, YEARS, FOLLOW_YEARS_in_parseDateWithin452 );
                  
                  unit = Calendar.YEAR;
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:248:7:
            // ( OF parseDate[epochStart, false] )?
            int alt5 = 2;
            int LA5_0 = input.LA( 1 );
            
            if ( ( LA5_0 == OF ) )
            {
               alt5 = 1;
            }
            switch ( alt5 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:248:8:
               // OF parseDate[epochStart, false]
               {
                  match( input, OF, FOLLOW_OF_in_parseDateWithin481 );
                  
                  pushFollow( FOLLOW_parseDate_in_parseDateWithin483 );
                  parseDate( epochStart, false );
                  
                  state._fsp--;
                  
               }
                  break;
            
            }
            
         }
         
         final MolokoCalendar epochEnd = getCalendar();
         epochEnd.setTimeInMillis( epochStart.getTimeInMillis() );
         epochEnd.add( unit, past ? -amount : amount );
         
         res = new ParseDateWithinReturn( epochStart, epochEnd );
         
      }
      catch ( NumberFormatException e )
      {
         
         throw new RecognitionException();
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return res;
   }
   
   
   
   // $ANTLR end "parseDateWithin"
   
   // $ANTLR start "date_numeric"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:255:1:
   // date_numeric[MolokoCalendar cal] : pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( ( DOT | MINUS | DATE_SEP ) (pt3=
   // INT )? )? ;
   public final void date_numeric( MolokoCalendar cal ) throws RecognitionException
   {
      Token pt1 = null;
      Token pt2 = null;
      Token pt3 = null;
      
      String pt1Str = null;
      String pt2Str = null;
      String pt3Str = null;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:262:4:
         // (pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( ( DOT | MINUS | DATE_SEP ) (pt3= INT )? )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:262:6:
         // pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( ( DOT | MINUS | DATE_SEP ) (pt3= INT )? )?
         {
            pt1 = (Token) match( input, INT, FOLLOW_INT_in_date_numeric528 );
            
            if ( input.LA( 1 ) == DATE_SEP || input.LA( 1 ) == DOT
               || input.LA( 1 ) == MINUS )
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
            
            pt1Str = pt1.getText();
            
            pt2 = (Token) match( input, INT, FOLLOW_INT_in_date_numeric556 );
            
            pt2Str = pt2.getText();
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:270:6:
            // ( ( DOT | MINUS | DATE_SEP ) (pt3= INT )? )?
            int alt7 = 2;
            int LA7_0 = input.LA( 1 );
            
            if ( ( LA7_0 == DATE_SEP || LA7_0 == DOT || LA7_0 == MINUS ) )
            {
               alt7 = 1;
            }
            switch ( alt7 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:271:9:
               // ( DOT | MINUS | DATE_SEP ) (pt3= INT )?
               {
                  if ( input.LA( 1 ) == DATE_SEP || input.LA( 1 ) == DOT
                     || input.LA( 1 ) == MINUS )
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
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:272:9:
                  // (pt3= INT )?
                  int alt6 = 2;
                  int LA6_0 = input.LA( 1 );
                  
                  if ( ( LA6_0 == INT ) )
                  {
                     alt6 = 1;
                  }
                  switch ( alt6 )
                  {
                     case 1:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:273:12:
                     // pt3= INT
                     {
                        pt3 = (Token) match( input,
                                             INT,
                                             FOLLOW_INT_in_date_numeric615 );
                        
                        pt3Str = pt3.getText();
                        
                     }
                        break;
                  
                  }
                  
               }
                  break;
            
            }
            
            handleNumericDate( cal, pt1Str, pt2Str, pt3Str );
            
         }
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_numeric"
   
   // $ANTLR start "date_on"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:284:1:
   // date_on[MolokoCalendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] ) ;
   public final void date_on( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:285:4:
         // ( ( ON )? ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:285:6:
         // ( ON )? ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] )
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:285:6:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:285:6:
               // ON
               {
                  match( input, ON, FOLLOW_ON_in_date_on673 );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:285:10:
            // ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] )
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == INT ) )
            {
               alt9 = 1;
            }
            else if ( ( LA9_0 == NEXT || LA9_0 == WEEKDAY ) )
            {
               alt9 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     9,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
            }
            switch ( alt9 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:285:14:
               // date_on_Xst_of_M[$cal]
               {
                  pushFollow( FOLLOW_date_on_Xst_of_M_in_date_on680 );
                  date_on_Xst_of_M( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:286:14:
               // date_on_weekday[$cal]
               {
                  pushFollow( FOLLOW_date_on_weekday_in_date_on707 );
                  date_on_weekday( cal );
                  
                  state._fsp--;
                  
               }
                  break;
            
            }
            
         }
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on"
   
   // $ANTLR start "date_on_Xst_of_M"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:289:1:
   // date_on_Xst_of_M[MolokoCalendar cal] : d= INT ( DOT )? ( ( OF_THE )? m= MONTH (y= INT )? )? ;
   public final void date_on_Xst_of_M( MolokoCalendar cal ) throws RecognitionException
   {
      Token d = null;
      Token m = null;
      Token y = null;
      
      boolean hasMonth = false;
      boolean hasYear = false;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:295:4:
         // (d= INT ( DOT )? ( ( OF_THE )? m= MONTH (y= INT )? )? )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:295:6:
         // d= INT ( DOT )? ( ( OF_THE )? m= MONTH (y= INT )? )?
         {
            d = (Token) match( input, INT, FOLLOW_INT_in_date_on_Xst_of_M740 );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:295:12:
            // ( DOT )?
            int alt10 = 2;
            int LA10_0 = input.LA( 1 );
            
            if ( ( LA10_0 == DOT ) )
            {
               alt10 = 1;
            }
            switch ( alt10 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:295:12:
               // DOT
               {
                  match( input, DOT, FOLLOW_DOT_in_date_on_Xst_of_M742 );
                  
               }
                  break;
            
            }
            
            cal.set( Calendar.DAY_OF_MONTH,
                     Integer.parseInt( ( d != null ? d.getText() : null ) ) );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:299:7:
            // ( ( OF_THE )? m= MONTH (y= INT )? )?
            int alt13 = 2;
            int LA13_0 = input.LA( 1 );
            
            if ( ( LA13_0 == MONTH || LA13_0 == OF_THE ) )
            {
               alt13 = 1;
            }
            switch ( alt13 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:299:8:
               // ( OF_THE )? m= MONTH (y= INT )?
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:299:8:
                  // ( OF_THE )?
                  int alt11 = 2;
                  int LA11_0 = input.LA( 1 );
                  
                  if ( ( LA11_0 == OF_THE ) )
                  {
                     alt11 = 1;
                  }
                  switch ( alt11 )
                  {
                     case 1:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:299:8:
                     // OF_THE
                     {
                        match( input,
                               OF_THE,
                               FOLLOW_OF_THE_in_date_on_Xst_of_M761 );
                        
                     }
                        break;
                  
                  }
                  
                  m = (Token) match( input,
                                     MONTH,
                                     FOLLOW_MONTH_in_date_on_Xst_of_M766 );
                  
                  parseTextMonth( cal, ( m != null ? m.getText() : null ) );
                  hasMonth = true;
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:304:8:
                  // (y= INT )?
                  int alt12 = 2;
                  int LA12_0 = input.LA( 1 );
                  
                  if ( ( LA12_0 == INT ) )
                  {
                     alt12 = 1;
                  }
                  switch ( alt12 )
                  {
                     case 1:
                     // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:304:9:
                     // y= INT
                     {
                        y = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_date_on_Xst_of_M793 );
                        
                        parseYear( cal, ( y != null ? y.getText() : null ) );
                        hasYear = true;
                        
                     }
                        break;
                  
                  }
                  
               }
                  break;
            
            }
            
            handleDateOnXstOfMonth( cal, hasYear, hasMonth );
            
         }
         
      }
      catch ( NumberFormatException e )
      {
         
         throw new RecognitionException();
         
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on_Xst_of_M"
   
   // $ANTLR start "date_on_weekday"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:318:1:
   // date_on_weekday[MolokoCalendar cal] : ( NEXT )? wd= WEEKDAY ;
   public final void date_on_weekday( MolokoCalendar cal ) throws RecognitionException
   {
      Token wd = null;
      
      boolean nextWeek = false;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:323:4:
         // ( ( NEXT )? wd= WEEKDAY )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:323:6:
         // ( NEXT )? wd= WEEKDAY
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:323:6:
            // ( NEXT )?
            int alt14 = 2;
            int LA14_0 = input.LA( 1 );
            
            if ( ( LA14_0 == NEXT ) )
            {
               alt14 = 1;
            }
            switch ( alt14 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:323:7:
               // NEXT
               {
                  match( input, NEXT, FOLLOW_NEXT_in_date_on_weekday855 );
                  
                  nextWeek = true;
                  
               }
                  break;
            
            }
            
            wd = (Token) match( input,
                                WEEKDAY,
                                FOLLOW_WEEKDAY_in_date_on_weekday863 );
            
            handleDateOnWeekday( cal,
                                 ( wd != null ? wd.getText() : null ),
                                 nextWeek );
            
         }
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on_weekday"
   
   // $ANTLR start "date_in_X_YMWD"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:329:1:
   // date_in_X_YMWD[MolokoCalendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA )
   // date_in_X_YMWD_distance[$cal] )* ;
   public final void date_in_X_YMWD( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:330:4:
         // ( ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:330:7:
         // ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:330:7:
            // ( IN )?
            int alt15 = 2;
            int LA15_0 = input.LA( 1 );
            
            if ( ( LA15_0 == IN ) )
            {
               alt15 = 1;
            }
            switch ( alt15 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:330:7:
               // IN
               {
                  match( input, IN, FOLLOW_IN_in_date_in_X_YMWD886 );
                  
               }
                  break;
            
            }
            
            pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD898 );
            date_in_X_YMWD_distance( cal );
            
            state._fsp--;
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:331:7:
            // ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop16: do
            {
               int alt16 = 2;
               int LA16_0 = input.LA( 1 );
               
               if ( ( LA16_0 == AND || LA16_0 == COMMA ) )
               {
                  alt16 = 1;
               }
               
               switch ( alt16 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:331:8:
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
                     
                     pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD914 );
                     date_in_X_YMWD_distance( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  
                  default :
                     break loop16;
               }
            }
            while ( true );
            
         }
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_in_X_YMWD"
   
   // $ANTLR start "date_in_X_YMWD_distance"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:334:1:
   // date_in_X_YMWD_distance[MolokoCalendar cal] : (a= NUM_STR |a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
   public final void date_in_X_YMWD_distance( MolokoCalendar cal ) throws RecognitionException
   {
      Token a = null;
      
      int amount = -1;
      int calField = Calendar.YEAR;
      
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:340:4:
         // ( (a= NUM_STR |a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:340:6:
         // (a= NUM_STR |a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:340:6:
            // (a= NUM_STR |a= INT )
            int alt17 = 2;
            int LA17_0 = input.LA( 1 );
            
            if ( ( LA17_0 == NUM_STR ) )
            {
               alt17 = 1;
            }
            else if ( ( LA17_0 == INT ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:340:10:
               // a= NUM_STR
               {
                  a = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_date_in_X_YMWD_distance951 );
                  
                  amount = numberStringToNumber( ( a != null ? a.getText()
                                                            : null ) );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:341:10:
               // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_date_in_X_YMWD_distance966 );
                  
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:342:6:
            // ( YEARS | MONTHS | WEEKS | DAYS )
            int alt18 = 4;
            switch ( input.LA( 1 ) )
            {
               case YEARS:
               {
                  alt18 = 1;
               }
                  break;
               case MONTHS:
               {
                  alt18 = 2;
               }
                  break;
               case WEEKS:
               {
                  alt18 = 3;
               }
                  break;
               case DAYS:
               {
                  alt18 = 4;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        18,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
            switch ( alt18 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:342:12:
               // YEARS
               {
                  match( input,
                         YEARS,
                         FOLLOW_YEARS_in_date_in_X_YMWD_distance986 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:343:12:
               // MONTHS
               {
                  match( input,
                         MONTHS,
                         FOLLOW_MONTHS_in_date_in_X_YMWD_distance999 );
                  
                  calField = Calendar.MONTH;
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:344:12:
               // WEEKS
               {
                  match( input,
                         WEEKS,
                         FOLLOW_WEEKS_in_date_in_X_YMWD_distance1015 );
                  
                  calField = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:345:12:
               // DAYS
               {
                  match( input,
                         DAYS,
                         FOLLOW_DAYS_in_date_in_X_YMWD_distance1032 );
                  
                  calField = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
            
            }
            
            if ( amount != -1 )
            {
               cal.add( calField, amount );
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
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_in_X_YMWD_distance"
   
   // $ANTLR start "date_end_of_the_MW"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:362:1:
   // date_end_of_the_MW[MolokoCalendar cal] : END ( OF_THE )? ( WEEKS | MONTHS ) ;
   public final void date_end_of_the_MW( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:363:4:
         // ( END ( OF_THE )? ( WEEKS | MONTHS ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:363:6:
         // END ( OF_THE )? ( WEEKS | MONTHS )
         {
            match( input, END, FOLLOW_END_in_date_end_of_the_MW1071 );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:363:10:
            // ( OF_THE )?
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == OF_THE ) )
            {
               alt19 = 1;
            }
            switch ( alt19 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:363:10:
               // OF_THE
               {
                  match( input, OF_THE, FOLLOW_OF_THE_in_date_end_of_the_MW1073 );
                  
               }
                  break;
            
            }
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:364:6:
            // ( WEEKS | MONTHS )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:364:10:
               // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_date_end_of_the_MW1085 );
                  
                  rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:368:10:
               // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_date_end_of_the_MW1107 );
                  
                  rollToEndOf( Calendar.DAY_OF_MONTH, cal );
                  
               }
                  break;
            
            }
            
         }
         
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   
   
   // $ANTLR end "date_end_of_the_MW"
   
   // $ANTLR start "date_natural"
   // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:374:1:
   // date_natural[MolokoCalendar cal] : ( TODAY | TOMORROW | YESTERDAY );
   public final void date_natural( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:375:4:
         // ( TODAY | TOMORROW | YESTERDAY )
         int alt21 = 3;
         switch ( input.LA( 1 ) )
         {
            case TODAY:
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:375:6:
            // TODAY
            {
               match( input, TODAY, FOLLOW_TODAY_in_date_natural1136 );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:378:6:
            // TOMORROW
            {
               match( input, TOMORROW, FOLLOW_TOMORROW_in_date_natural1150 );
               
               cal.add( Calendar.DAY_OF_YEAR, 1 );
               
            }
               break;
            case 3:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:382:6:
            // YESTERDAY
            {
               match( input, YESTERDAY, FOLLOW_YESTERDAY_in_date_natural1164 );
               
               cal.add( Calendar.DAY_OF_YEAR, -1 );
               
            }
               break;
         
         }
      }
      
      catch ( RecognitionException re )
      {
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      
      finally
      {
         // do for sure before leaving
      }
      return;
   }
   
   // $ANTLR end "date_natural"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_date_numeric_in_parseDate118 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_in_parseDate138 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_in_parseDate163 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_end_of_the_MW_in_parseDate181 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_natural_in_parseDate195 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NOW_in_parseDate254 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_parseDate272 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseDateWithin332 = new BitSet( new long[]
   { 0x0000000140420402L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_parseDateWithin354 = new BitSet( new long[]
   { 0x0000000140420402L } );
   
   public static final BitSet FOLLOW_A_in_parseDateWithin374 = new BitSet( new long[]
   { 0x0000000140420402L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseDateWithin386 = new BitSet( new long[]
   { 0x0000000000400002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseDateWithin408 = new BitSet( new long[]
   { 0x0000000000400002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseDateWithin430 = new BitSet( new long[]
   { 0x0000000000400002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseDateWithin452 = new BitSet( new long[]
   { 0x0000000000400002L } );
   
   public static final BitSet FOLLOW_OF_in_parseDateWithin481 = new BitSet( new long[]
   { 0x00000002393C7000L } );
   
   public static final BitSet FOLLOW_parseDate_in_parseDateWithin483 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric528 = new BitSet( new long[]
   { 0x0000000000008900L } );
   
   public static final BitSet FOLLOW_set_in_date_numeric530 = new BitSet( new long[]
   { 0x0000000000004000L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric556 = new BitSet( new long[]
   { 0x0000000000008902L } );
   
   public static final BitSet FOLLOW_set_in_date_numeric580 = new BitSet( new long[]
   { 0x0000000000004002L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric615 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_ON_in_date_on673 = new BitSet( new long[]
   { 0x0000000020084000L } );
   
   public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on680 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_weekday_in_date_on707 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M740 = new BitSet( new long[]
   { 0x0000000000810802L } );
   
   public static final BitSet FOLLOW_DOT_in_date_on_Xst_of_M742 = new BitSet( new long[]
   { 0x0000000000810002L } );
   
   public static final BitSet FOLLOW_OF_THE_in_date_on_Xst_of_M761 = new BitSet( new long[]
   { 0x0000000000010000L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M766 = new BitSet( new long[]
   { 0x0000000000004002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M793 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEXT_in_date_on_weekday855 = new BitSet( new long[]
   { 0x0000000020000000L } );
   
   public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday863 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_IN_in_date_in_X_YMWD886 = new BitSet( new long[]
   { 0x0000000000204000L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD898 = new BitSet( new long[]
   { 0x00000000000000A2L } );
   
   public static final BitSet FOLLOW_set_in_date_in_X_YMWD908 = new BitSet( new long[]
   { 0x0000000000204000L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD914 = new BitSet( new long[]
   { 0x00000000000000A2L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance951 = new BitSet( new long[]
   { 0x0000000140020400L } );
   
   public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance966 = new BitSet( new long[]
   { 0x0000000140020400L } );
   
   public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance986 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance999 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance1015 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1032 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_END_in_date_end_of_the_MW1071 = new BitSet( new long[]
   { 0x0000000040820000L } );
   
   public static final BitSet FOLLOW_OF_THE_in_date_end_of_the_MW1073 = new BitSet( new long[]
   { 0x0000000040020000L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1085 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1107 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TODAY_in_date_natural1136 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TOMORROW_in_date_natural1150 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_YESTERDAY_in_date_natural1164 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
