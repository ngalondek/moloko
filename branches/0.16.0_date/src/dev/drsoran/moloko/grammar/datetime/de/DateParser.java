// $ANTLR 3.3 Nov 30, 2010 12:45:30 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g 2011-11-01 15:28:17

package dev.drsoran.moloko.grammar.datetime.de;

import java.util.Calendar;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.grammar.LexerException;
import dev.drsoran.moloko.grammar.datetime.AbstractDateParser;
import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateReturn;
import dev.drsoran.moloko.util.MolokoCalendar;


public class DateParser extends AbstractDateParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NOW", "INT", "NUM_STR", "A",
    "DAYS", "WEEKS", "MONTHS", "YEARS", "OF", "DOT", "MINUS", "COLON",
    "DATE_SEP", "ON", "OF_THE", "MONTH", "NEXT", "WEEKDAY", "IN", "AND",
    "COMMA", "END", "TODAY", "NEVER", "TOMORROW", "YESTERDAY", "SUFF_MALE",
    "SUFF_FMALE", "DATE_TIME_SEPARATOR", "WS" };
   
   public static final int EOF = -1;
   
   public static final int NOW = 4;
   
   public static final int INT = 5;
   
   public static final int NUM_STR = 6;
   
   public static final int A = 7;
   
   public static final int DAYS = 8;
   
   public static final int WEEKS = 9;
   
   public static final int MONTHS = 10;
   
   public static final int YEARS = 11;
   
   public static final int OF = 12;
   
   public static final int DOT = 13;
   
   public static final int MINUS = 14;
   
   public static final int COLON = 15;
   
   public static final int DATE_SEP = 16;
   
   public static final int ON = 17;
   
   public static final int OF_THE = 18;
   
   public static final int MONTH = 19;
   
   public static final int NEXT = 20;
   
   public static final int WEEKDAY = 21;
   
   public static final int IN = 22;
   
   public static final int AND = 23;
   
   public static final int COMMA = 24;
   
   public static final int END = 25;
   
   public static final int TODAY = 26;
   
   public static final int NEVER = 27;
   
   public static final int TOMORROW = 28;
   
   public static final int YESTERDAY = 29;
   
   public static final int SUFF_MALE = 30;
   
   public static final int SUFF_FMALE = 31;
   
   public static final int DATE_TIME_SEPARATOR = 32;
   
   public static final int WS = 33;
   
   
   
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
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g";
   }
   
   
   
   public DateParser()
   {
      super( null );
   }
   
   
   
   @Override
   protected int numberStringToNumber( String string )
   {
      switch ( string.charAt( 0 ) )
      {
         case 'e':
            return 1;
         case 'z':
         {
            switch ( string.charAt( 1 ) )
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
            switch ( string.charAt( 1 ) )
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
   
   
   
   @Override
   protected int weekdayStringToNumber( String string )
   {
      switch ( string.charAt( 0 ) )
      {
         case 'm':
            switch ( string.charAt( 1 ) )
            {
               case 'o':
                  return Calendar.MONDAY;
               case 'i':
                  return Calendar.WEDNESDAY;
            }
         case 'd':
            switch ( string.charAt( 1 ) )
            {
               case 'i':
                  return Calendar.TUESDAY;
               case 'o':
                  return Calendar.THURSDAY;
            }
         case 's':
            switch ( string.charAt( 1 ) )
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
   
   
   
   @Override
   protected int monthStringToNumber( String string )
   {
      switch ( string.charAt( 0 ) )
      {
         case 'f':
            return Calendar.FEBRUARY;
         case 'm':
            switch ( string.charAt( 2 ) )
            {
               case 'e': // Maerz
               case 'r': // März
                  return Calendar.MARCH;
               case 'i':
                  return Calendar.MAY;
            }
         case 'j':
            switch ( string.charAt( 1 ) )
            {
               case 'a':
                  return Calendar.JANUARY;
               default :
                  switch ( string.charAt( 2 ) )
                  {
                     case 'n':
                        return Calendar.JUNE;
                     case 'l':
                        return Calendar.JULY;
                  }
            }
         case 'a':
            switch ( string.charAt( 1 ) )
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:156:1:
   // parseDate[MolokoCalendar cal, boolean clearTime] returns [ParseDateReturn result] : ( ( date_numeric[$cal] |
   // date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | NOW ) ;
   public final ParseDateReturn parseDate( MolokoCalendar cal, boolean clearTime ) throws RecognitionException
   {
      ParseDateReturn result = null;
      
      startDateParsing( cal );
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:168:4:
         // ( ( ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
         // date_natural[$cal] ) | NOW ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:168:5:
         // ( ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
         // date_natural[$cal] ) | NOW )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:168:5:
            // ( ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
            // date_natural[$cal] ) | NOW )
            int alt2 = 2;
            int LA2_0 = input.LA( 1 );
            
            if ( ( ( LA2_0 >= INT && LA2_0 <= NUM_STR ) || LA2_0 == ON
               || ( LA2_0 >= NEXT && LA2_0 <= IN ) || ( LA2_0 >= END && LA2_0 <= YESTERDAY ) ) )
            {
               alt2 = 1;
            }
            else if ( ( LA2_0 == NOW ) )
            {
               alt2 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     2,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt2 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:169:8:
               // ( date_numeric[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] |
               // date_natural[$cal] )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:169:8:
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
                              else if ( ( LA1_6 == EOF || ( LA1_6 >= OF_THE && LA1_6 <= MONTH ) ) )
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
                           case MINUS:
                           case COLON:
                           case DATE_SEP:
                           {
                              alt1 = 1;
                           }
                              break;
                           case EOF:
                           case OF_THE:
                           case MONTH:
                           {
                              alt1 = 2;
                           }
                              break;
                           case DAYS:
                           case WEEKS:
                           case MONTHS:
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
                     case ON:
                     case NEXT:
                     case WEEKDAY:
                     {
                        alt1 = 2;
                     }
                        break;
                     case NUM_STR:
                     case IN:
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
                     case NEVER:
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:169:12:
                     // date_numeric[$cal]
                     {
                        pushFollow( FOLLOW_date_numeric_in_parseDate118 );
                        date_numeric( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:170:12:
                     // date_on[$cal]
                     {
                        pushFollow( FOLLOW_date_on_in_parseDate141 );
                        date_on( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 3:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:171:12:
                     // date_in_X_YMWD[$cal]
                     {
                        pushFollow( FOLLOW_date_in_X_YMWD_in_parseDate166 );
                        date_in_X_YMWD( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 4:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:172:12:
                     // date_end_of_the_MW[$cal]
                     {
                        pushFollow( FOLLOW_date_end_of_the_MW_in_parseDate184 );
                        date_end_of_the_MW( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                     case 5:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:173:12:
                     // date_natural[$cal]
                     {
                        pushFollow( FOLLOW_date_natural_in_parseDate198 );
                        date_natural( cal );
                        
                        state._fsp--;
                        
                     }
                        break;
                  
                  }
                  
                  if ( clearTime )
                     cal.setHasTime( false );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:181:9:
               // NOW
               {
                  match( input, NOW, FOLLOW_NOW_in_parseDate257 );
                  
                  // In case of NOW we do not respect the clearTime Parameter
                  // cause NOW has always a time.
                  cal.setTimeInMillis( System.currentTimeMillis() );
                  cal.setHasTime( true );
                  
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
      }
      return result;
   }
   
   
   // $ANTLR end "parseDate"
   
   public static class parseDateWithin_return extends ParserRuleReturnScope
   {
      public MolokoCalendar epochStart;
      
      public MolokoCalendar epochEnd;
   };
   
   
   
   // $ANTLR start "parseDateWithin"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:191:1:
   // parseDateWithin[boolean past] returns [MolokoCalendar epochStart, MolokoCalendar epochEnd] : (a= INT | n= NUM_STR
   // | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? ;
   public final DateParser.parseDateWithin_return parseDateWithin( boolean past ) throws RecognitionException
   {
      DateParser.parseDateWithin_return retval = new DateParser.parseDateWithin_return();
      retval.start = input.LT( 1 );
      
      Token a = null;
      Token n = null;
      
      retval.epochStart = getCalendar();
      int amount = 1;
      int unit = Calendar.DAY_OF_YEAR;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:204:4:
         // ( (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )?
         // )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:204:6:
         // (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )?
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:204:6:
            // (a= INT | n= NUM_STR | A )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:204:9:
               // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_parseDateWithin317 );
                  
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:208:9:
               // n= NUM_STR
               {
                  n = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_parseDateWithin339 );
                  
                  amount = numberStringToNumber( ( n != null ? n.getText()
                                                            : null ) );
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:212:9:
               // A
               {
                  match( input, A, FOLLOW_A_in_parseDateWithin359 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:213:7:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:213:10:
               // DAYS
               {
                  match( input, DAYS, FOLLOW_DAYS_in_parseDateWithin371 );
                  
                  unit = Calendar.DAY_OF_YEAR;
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:217:10:
               // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_parseDateWithin393 );
                  
                  unit = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:221:10:
               // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_parseDateWithin415 );
                  
                  unit = Calendar.MONTH;
                  
               }
                  break;
               case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:225:10:
               // YEARS
               {
                  match( input, YEARS, FOLLOW_YEARS_in_parseDateWithin437 );
                  
                  unit = Calendar.YEAR;
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:230:7:
            // ( OF parseDate[retval.epochStart, false] )?
            int alt5 = 2;
            int LA5_0 = input.LA( 1 );
            
            if ( ( LA5_0 == OF ) )
            {
               alt5 = 1;
            }
            switch ( alt5 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:230:8:
               // OF parseDate[retval.epochStart, false]
               {
                  match( input, OF, FOLLOW_OF_in_parseDateWithin466 );
                  pushFollow( FOLLOW_parseDate_in_parseDateWithin468 );
                  parseDate( retval.epochStart, false );
                  
                  state._fsp--;
                  
               }
                  break;
            
            }
            
         }
         
         retval.stop = input.LT( -1 );
         
         retval.epochEnd = getCalendar();
         retval.epochEnd.setTimeInMillis( retval.epochStart.getTimeInMillis() );
         retval.epochEnd.add( unit, past ? -amount : amount );
         
      }
      catch ( NumberFormatException e )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return retval;
   }
   
   
   
   // $ANTLR end "parseDateWithin"
   
   // $ANTLR start "date_numeric"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:237:1:
   // date_numeric[MolokoCalendar cal] : pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON |
   // DATE_SEP ) (pt3= INT )? ;
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:244:4:
         // (pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:244:6:
         // pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )?
         {
            pt1 = (Token) match( input, INT, FOLLOW_INT_in_date_numeric513 );
            if ( ( input.LA( 1 ) >= DOT && input.LA( 1 ) <= DATE_SEP ) )
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
            
            pt2 = (Token) match( input, INT, FOLLOW_INT_in_date_numeric545 );
            if ( ( input.LA( 1 ) >= DOT && input.LA( 1 ) <= DATE_SEP ) )
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
            
            pt2Str = pt2.getText();
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:252:6:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:253:9:
               // pt3= INT
               {
                  pt3 = (Token) match( input,
                                       INT,
                                       FOLLOW_INT_in_date_numeric587 );
                  
                  pt3Str = pt3.getText();
                  
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
      }
      return;
   }
   
   
   
   // $ANTLR end "date_numeric"
   
   // $ANTLR start "date_on"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:263:1:
   // date_on[MolokoCalendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] ) ;
   public final void date_on( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:264:4:
         // ( ( ON )? ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:264:6:
         // ( ON )? ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:264:6:
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:264:6:
               // ON
               {
                  match( input, ON, FOLLOW_ON_in_date_on631 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:264:10:
            // ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] )
            int alt8 = 2;
            int LA8_0 = input.LA( 1 );
            
            if ( ( LA8_0 == INT ) )
            {
               alt8 = 1;
            }
            else if ( ( ( LA8_0 >= NEXT && LA8_0 <= WEEKDAY ) ) )
            {
               alt8 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     8,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt8 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:264:14:
               // date_on_Xst_of_M[$cal]
               {
                  pushFollow( FOLLOW_date_on_Xst_of_M_in_date_on638 );
                  date_on_Xst_of_M( cal );
                  
                  state._fsp--;
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:265:14:
               // date_on_weekday[$cal]
               {
                  pushFollow( FOLLOW_date_on_weekday_in_date_on665 );
                  date_on_weekday( cal );
                  
                  state._fsp--;
                  
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
   
   
   
   // $ANTLR end "date_on"
   
   // $ANTLR start "date_on_Xst_of_M"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:272:1:
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
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:278:4:
         // (d= INT ( DOT )? ( ( OF_THE )? m= MONTH (y= INT )? )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:278:6:
         // d= INT ( DOT )? ( ( OF_THE )? m= MONTH (y= INT )? )?
         {
            d = (Token) match( input, INT, FOLLOW_INT_in_date_on_Xst_of_M710 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:278:12:
            // ( DOT )?
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == DOT ) )
            {
               alt9 = 1;
            }
            switch ( alt9 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:278:12:
               // DOT
               {
                  match( input, DOT, FOLLOW_DOT_in_date_on_Xst_of_M712 );
                  
               }
                  break;
            
            }
            
            cal.set( Calendar.DAY_OF_MONTH,
                     Integer.parseInt( ( d != null ? d.getText() : null ) ) );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:282:7:
            // ( ( OF_THE )? m= MONTH (y= INT )? )?
            int alt12 = 2;
            int LA12_0 = input.LA( 1 );
            
            if ( ( ( LA12_0 >= OF_THE && LA12_0 <= MONTH ) ) )
            {
               alt12 = 1;
            }
            switch ( alt12 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:282:8:
               // ( OF_THE )? m= MONTH (y= INT )?
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:282:8:
                  // ( OF_THE )?
                  int alt10 = 2;
                  int LA10_0 = input.LA( 1 );
                  
                  if ( ( LA10_0 == OF_THE ) )
                  {
                     alt10 = 1;
                  }
                  switch ( alt10 )
                  {
                     case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:282:8:
                     // OF_THE
                     {
                        match( input,
                               OF_THE,
                               FOLLOW_OF_THE_in_date_on_Xst_of_M731 );
                        
                     }
                        break;
                  
                  }
                  
                  m = (Token) match( input,
                                     MONTH,
                                     FOLLOW_MONTH_in_date_on_Xst_of_M736 );
                  
                  parseTextMonth( cal, ( m != null ? m.getText() : null ) );
                  hasMonth = true;
                  
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:287:8:
                  // (y= INT )?
                  int alt11 = 2;
                  int LA11_0 = input.LA( 1 );
                  
                  if ( ( LA11_0 == INT ) )
                  {
                     alt11 = 1;
                  }
                  switch ( alt11 )
                  {
                     case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:287:9:
                     // y= INT
                     {
                        y = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_date_on_Xst_of_M763 );
                        
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
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on_Xst_of_M"
   
   // $ANTLR start "date_on_weekday"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:301:1:
   // date_on_weekday[MolokoCalendar cal] : ( NEXT )? wd= WEEKDAY ;
   public final void date_on_weekday( MolokoCalendar cal ) throws RecognitionException
   {
      Token wd = null;
      
      boolean nextWeek = false;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:306:4:
         // ( ( NEXT )? wd= WEEKDAY )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:306:6:
         // ( NEXT )? wd= WEEKDAY
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:306:6:
            // ( NEXT )?
            int alt13 = 2;
            int LA13_0 = input.LA( 1 );
            
            if ( ( LA13_0 == NEXT ) )
            {
               alt13 = 1;
            }
            switch ( alt13 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:306:7:
               // NEXT
               {
                  match( input, NEXT, FOLLOW_NEXT_in_date_on_weekday825 );
                  nextWeek = true;
                  
               }
                  break;
            
            }
            
            wd = (Token) match( input,
                                WEEKDAY,
                                FOLLOW_WEEKDAY_in_date_on_weekday833 );
            
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
      }
      return;
   }
   
   
   
   // $ANTLR end "date_on_weekday"
   
   // $ANTLR start "date_in_X_YMWD"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:312:1:
   // date_in_X_YMWD[MolokoCalendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA )
   // date_in_X_YMWD_distance[$cal] )* ;
   public final void date_in_X_YMWD( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:313:4:
         // ( ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:313:7:
         // ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:313:7:
            // ( IN )?
            int alt14 = 2;
            int LA14_0 = input.LA( 1 );
            
            if ( ( LA14_0 == IN ) )
            {
               alt14 = 1;
            }
            switch ( alt14 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:313:7:
               // IN
               {
                  match( input, IN, FOLLOW_IN_in_date_in_X_YMWD856 );
                  
               }
                  break;
            
            }
            
            pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD868 );
            date_in_X_YMWD_distance( cal );
            
            state._fsp--;
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:314:7:
            // ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop15: do
            {
               int alt15 = 2;
               int LA15_0 = input.LA( 1 );
               
               if ( ( ( LA15_0 >= AND && LA15_0 <= COMMA ) ) )
               {
                  alt15 = 1;
               }
               
               switch ( alt15 )
               {
                  case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:314:8:
                  // ( AND | COMMA ) date_in_X_YMWD_distance[$cal]
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
                     
                     pushFollow( FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD884 );
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
         notifyParsingDateFailed();
         throw re;
      }
      catch ( LexerException le )
      {
         throw new RecognitionException();
      }
      finally
      {
      }
      return;
   }
   
   
   
   // $ANTLR end "date_in_X_YMWD"
   
   // $ANTLR start "date_in_X_YMWD_distance"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:317:1:
   // date_in_X_YMWD_distance[MolokoCalendar cal] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
   public final void date_in_X_YMWD_distance( MolokoCalendar cal ) throws RecognitionException
   {
      Token a = null;
      
      int amount = -1;
      int calField = Calendar.YEAR;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:323:4:
         // ( (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:323:6:
         // (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:323:6:
            // (a= NUM_STR | a= INT )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:323:10:
               // a= NUM_STR
               {
                  a = (Token) match( input,
                                     NUM_STR,
                                     FOLLOW_NUM_STR_in_date_in_X_YMWD_distance921 );
                  amount = numberStringToNumber( ( a != null ? a.getText()
                                                            : null ) );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:324:10:
               // a= INT
               {
                  a = (Token) match( input,
                                     INT,
                                     FOLLOW_INT_in_date_in_X_YMWD_distance936 );
                  amount = Integer.parseInt( ( a != null ? a.getText() : null ) );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:325:6:
            // ( YEARS | MONTHS | WEEKS | DAYS )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:325:12:
               // YEARS
               {
                  match( input,
                         YEARS,
                         FOLLOW_YEARS_in_date_in_X_YMWD_distance956 );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:326:12:
               // MONTHS
               {
                  match( input,
                         MONTHS,
                         FOLLOW_MONTHS_in_date_in_X_YMWD_distance969 );
                  calField = Calendar.MONTH;
                  
               }
                  break;
               case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:327:12:
               // WEEKS
               {
                  match( input,
                         WEEKS,
                         FOLLOW_WEEKS_in_date_in_X_YMWD_distance985 );
                  calField = Calendar.WEEK_OF_YEAR;
                  
               }
                  break;
               case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:328:12:
               // DAYS
               {
                  match( input,
                         DAYS,
                         FOLLOW_DAYS_in_date_in_X_YMWD_distance1002 );
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
      }
      return;
   }
   
   
   
   // $ANTLR end "date_in_X_YMWD_distance"
   
   // $ANTLR start "date_end_of_the_MW"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:345:1:
   // date_end_of_the_MW[MolokoCalendar cal] : END ( OF_THE )? ( WEEKS | MONTHS ) ;
   public final void date_end_of_the_MW( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:346:4:
         // ( END ( OF_THE )? ( WEEKS | MONTHS ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:346:6:
         // END ( OF_THE )? ( WEEKS | MONTHS )
         {
            match( input, END, FOLLOW_END_in_date_end_of_the_MW1041 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:346:10:
            // ( OF_THE )?
            int alt18 = 2;
            int LA18_0 = input.LA( 1 );
            
            if ( ( LA18_0 == OF_THE ) )
            {
               alt18 = 1;
            }
            switch ( alt18 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:346:10:
               // OF_THE
               {
                  match( input, OF_THE, FOLLOW_OF_THE_in_date_end_of_the_MW1043 );
                  
               }
                  break;
            
            }
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:347:6:
            // ( WEEKS | MONTHS )
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( LA19_0 == WEEKS ) )
            {
               alt19 = 1;
            }
            else if ( ( LA19_0 == MONTHS ) )
            {
               alt19 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     19,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt19 )
            {
               case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:347:10:
               // WEEKS
               {
                  match( input, WEEKS, FOLLOW_WEEKS_in_date_end_of_the_MW1055 );
                  
                  rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                  
               }
                  break;
               case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:351:10:
               // MONTHS
               {
                  match( input, MONTHS, FOLLOW_MONTHS_in_date_end_of_the_MW1077 );
                  
                  rollToEndOf( Calendar.DAY_OF_MONTH, cal );
                  
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
   
   
   
   // $ANTLR end "date_end_of_the_MW"
   
   // $ANTLR start "date_natural"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:361:1:
   // date_natural[MolokoCalendar cal] : ( TODAY | NEVER | TOMORROW | YESTERDAY );
   public final void date_natural( MolokoCalendar cal ) throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:362:4:
         // ( TODAY | NEVER | TOMORROW | YESTERDAY )
         int alt20 = 4;
         switch ( input.LA( 1 ) )
         {
            case TODAY:
            {
               alt20 = 1;
            }
               break;
            case NEVER:
            {
               alt20 = 2;
            }
               break;
            case TOMORROW:
            {
               alt20 = 3;
            }
               break;
            case YESTERDAY:
            {
               alt20 = 4;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     20,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt20 )
         {
            case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:362:6:
            // TODAY
            {
               match( input, TODAY, FOLLOW_TODAY_in_date_natural1118 );
               
            }
               break;
            case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:365:6:
            // NEVER
            {
               match( input, NEVER, FOLLOW_NEVER_in_date_natural1132 );
               
               cal.setHasDate( false );
               cal.setHasTime( false );
               
            }
               break;
            case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:370:6:
            // TOMORROW
            {
               match( input, TOMORROW, FOLLOW_TOMORROW_in_date_natural1146 );
               
               cal.roll( Calendar.DAY_OF_YEAR, true );
               
            }
               break;
            case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:374:6:
            // YESTERDAY
            {
               match( input, YESTERDAY, FOLLOW_YESTERDAY_in_date_natural1160 );
               
               cal.roll( Calendar.DAY_OF_YEAR, false );
               
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
   
   // $ANTLR end "date_natural"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_date_numeric_in_parseDate118 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_in_parseDate141 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_in_parseDate166 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_end_of_the_MW_in_parseDate184 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_natural_in_parseDate198 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NOW_in_parseDate257 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseDateWithin317 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_parseDateWithin339 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_A_in_parseDateWithin359 = new BitSet( new long[]
   { 0x0000000000001F02L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseDateWithin371 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_parseDateWithin393 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_parseDateWithin415 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_YEARS_in_parseDateWithin437 = new BitSet( new long[]
   { 0x0000000000001002L } );
   
   public static final BitSet FOLLOW_OF_in_parseDateWithin466 = new BitSet( new long[]
   { 0x000000003E720070L } );
   
   public static final BitSet FOLLOW_parseDate_in_parseDateWithin468 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric513 = new BitSet( new long[]
   { 0x000000000001E000L } );
   
   public static final BitSet FOLLOW_set_in_date_numeric515 = new BitSet( new long[]
   { 0x0000000000000020L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric545 = new BitSet( new long[]
   { 0x000000000001E000L } );
   
   public static final BitSet FOLLOW_set_in_date_numeric547 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_numeric587 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_ON_in_date_on631 = new BitSet( new long[]
   { 0x0000000000320020L } );
   
   public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on638 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_date_on_weekday_in_date_on665 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M710 = new BitSet( new long[]
   { 0x00000000000C2002L } );
   
   public static final BitSet FOLLOW_DOT_in_date_on_Xst_of_M712 = new BitSet( new long[]
   { 0x00000000000C0002L } );
   
   public static final BitSet FOLLOW_OF_THE_in_date_on_Xst_of_M731 = new BitSet( new long[]
   { 0x0000000000080000L } );
   
   public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M736 = new BitSet( new long[]
   { 0x0000000000000022L } );
   
   public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M763 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEXT_in_date_on_weekday825 = new BitSet( new long[]
   { 0x0000000000200000L } );
   
   public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday833 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_IN_in_date_in_X_YMWD856 = new BitSet( new long[]
   { 0x0000000000400060L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD868 = new BitSet( new long[]
   { 0x0000000001800002L } );
   
   public static final BitSet FOLLOW_set_in_date_in_X_YMWD878 = new BitSet( new long[]
   { 0x0000000000400060L } );
   
   public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD884 = new BitSet( new long[]
   { 0x0000000001800002L } );
   
   public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance921 = new BitSet( new long[]
   { 0x0000000000000F00L } );
   
   public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance936 = new BitSet( new long[]
   { 0x0000000000000F00L } );
   
   public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance956 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance969 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance985 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1002 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_END_in_date_end_of_the_MW1041 = new BitSet( new long[]
   { 0x0000000000040600L } );
   
   public static final BitSet FOLLOW_OF_THE_in_date_end_of_the_MW1043 = new BitSet( new long[]
   { 0x0000000000000600L } );
   
   public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1055 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1077 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TODAY_in_date_natural1118 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_date_natural1132 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_TOMORROW_in_date_natural1146 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_YESTERDAY_in_date_natural1160 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
