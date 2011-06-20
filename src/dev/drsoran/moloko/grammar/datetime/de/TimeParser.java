// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g 2011-06-20 06:24:01

package dev.drsoran.moloko.grammar.datetime.de;

import java.util.Calendar;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.grammar.LexerException;
import dev.drsoran.moloko.grammar.datetime.AbstractTimeParser;
import dev.drsoran.moloko.grammar.datetime.ITimeParser.ParseTimeReturn;
import dev.drsoran.moloko.util.MolokoCalendar;


public class TimeParser extends AbstractTimeParser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT", "COMMA", "NEVER",
    "MIDNIGHT", "MIDDAY", "INT", "COLON", "DOT", "HOURS", "MINUTES", "SECONDS",
    "DAYS", "AND", "WS" };
   
   public static final int MINUTES = 13;
   
   public static final int COLON = 10;
   
   public static final int AT = 4;
   
   public static final int DAYS = 15;
   
   public static final int MIDDAY = 8;
   
   public static final int WS = 17;
   
   public static final int COMMA = 5;
   
   public static final int SECONDS = 14;
   
   public static final int INT = 9;
   
   public static final int MIDNIGHT = 7;
   
   public static final int NEVER = 6;
   
   public static final int AND = 16;
   
   public static final int DOT = 11;
   
   public static final int EOF = -1;
   
   public static final int HOURS = 12;
   
   

   // delegates
   // delegators
   
   public TimeParser( TokenStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public TimeParser( TokenStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   public String[] getTokenNames()
   {
      return TimeParser.tokenNames;
   }
   


   public String getGrammarFileName()
   {
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g";
   }
   


   public TimeParser()
   {
      super( null );
   }
   


   // $ANTLR start "parseTime"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:48:1:
   // parseTime[MolokoCalendar cal, boolean adjustDay] returns [ParseTimeReturn result] : ( ( AT | COMMA )?
   // time_point_in_time[$cal] | EOF );
   public final ParseTimeReturn parseTime( MolokoCalendar cal, boolean adjustDay ) throws RecognitionException
   {
      ParseTimeReturn result = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:68:4: (
         // ( AT | COMMA )? time_point_in_time[$cal] | EOF )
         int alt2 = 2;
         int LA2_0 = input.LA( 1 );
         
         if ( ( ( LA2_0 >= AT && LA2_0 <= INT ) ) )
         {
            alt2 = 1;
         }
         else if ( ( LA2_0 == EOF ) )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:68:6:
               // ( AT | COMMA )? time_point_in_time[$cal]
            {
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:68:6:
               // ( AT | COMMA )?
               int alt1 = 2;
               int LA1_0 = input.LA( 1 );
               
               if ( ( ( LA1_0 >= AT && LA1_0 <= COMMA ) ) )
               {
                  alt1 = 1;
               }
               switch ( alt1 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                  {
                     if ( ( input.LA( 1 ) >= AT && input.LA( 1 ) <= COMMA ) )
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
               
               pushFollow( FOLLOW_time_point_in_time_in_parseTime103 );
               time_point_in_time( cal );
               
               state._fsp--;
               
               if ( adjustDay && getCalendar().after( cal ) )
                  cal.roll( Calendar.DAY_OF_WEEK, true );
               
               cal.setHasTime( true );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:75:6:
               // EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseTime119 );
               
            }
               break;
            
         }
         
         result = finishedParsing();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      catch ( LexerException e )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return result;
   }
   


   // $ANTLR end "parseTime"
   
   // $ANTLR start "time_point_in_time"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:86:1:
   // time_point_in_time[MolokoCalendar cal] : ( NEVER | MIDNIGHT | MIDDAY | ( (v= INT | h= time_component ( COLON | DOT
   // ) m= time_component ) ) );
   public final void time_point_in_time( MolokoCalendar cal ) throws RecognitionException
   {
      Token v = null;
      int h = 0;
      
      int m = 0;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:87:4: (
         // NEVER | MIDNIGHT | MIDDAY | ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ) )
         int alt4 = 4;
         switch ( input.LA( 1 ) )
         {
            case NEVER:
            {
               alt4 = 1;
            }
               break;
            case MIDNIGHT:
            {
               alt4 = 2;
            }
               break;
            case MIDDAY:
            {
               alt4 = 3;
            }
               break;
            case INT:
            {
               alt4 = 4;
            }
               break;
            default :
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     4,
                                                                     0,
                                                                     input );
               
               throw nvae;
         }
         
         switch ( alt4 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:87:6:
               // NEVER
            {
               match( input, NEVER, FOLLOW_NEVER_in_time_point_in_time161 );
               
               cal.setHasDate( false );
               cal.setHasTime( false );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:92:6:
               // MIDNIGHT
            {
               match( input, MIDNIGHT, FOLLOW_MIDNIGHT_in_time_point_in_time173 );
               
               cal.set( Calendar.HOUR_OF_DAY, 23 );
               cal.set( Calendar.MINUTE, 59 );
               cal.set( Calendar.SECOND, 59 );
               
            }
               break;
            case 3:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:98:6:
               // MIDDAY
            {
               match( input, MIDDAY, FOLLOW_MIDDAY_in_time_point_in_time185 );
               
               cal.set( Calendar.HOUR_OF_DAY, 12 );
               cal.set( Calendar.MINUTE, 00 );
               cal.set( Calendar.SECOND, 00 );
               
            }
               break;
            case 4:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:105:4:
               // ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) )
            {
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:105:4:
               // ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) )
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:105:6:
               // (v= INT | h= time_component ( COLON | DOT ) m= time_component )
               {
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:105:6:
                  // (v= INT | h= time_component ( COLON | DOT ) m= time_component )
                  int alt3 = 2;
                  int LA3_0 = input.LA( 1 );
                  
                  if ( ( LA3_0 == INT ) )
                  {
                     int LA3_1 = input.LA( 2 );
                     
                     if ( ( ( LA3_1 >= COLON && LA3_1 <= DOT ) ) )
                     {
                        alt3 = 2;
                     }
                     else if ( ( LA3_1 == EOF ) )
                     {
                        alt3 = 1;
                     }
                     else
                     {
                        NoViableAltException nvae = new NoViableAltException( "",
                                                                              3,
                                                                              1,
                                                                              input );
                        
                        throw nvae;
                     }
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
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:105:8:
                        // v= INT
                     {
                        v = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_time_point_in_time206 );
                        
                        setCalendarTime( cal, ( v != null ? v.getText() : null ) );
                        
                     }
                        break;
                     case 2:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:109:8:
                        // h= time_component ( COLON | DOT ) m= time_component
                     {
                        pushFollow( FOLLOW_time_component_in_time_point_in_time226 );
                        h = time_component();
                        
                        state._fsp--;
                        
                        if ( ( input.LA( 1 ) >= COLON && input.LA( 1 ) <= DOT ) )
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
                        
                        pushFollow( FOLLOW_time_component_in_time_point_in_time236 );
                        m = time_component();
                        
                        state._fsp--;
                        
                        cal.set( Calendar.HOUR_OF_DAY, h );
                        cal.set( Calendar.MINUTE, m );
                        cal.set( Calendar.SECOND, 0 );
                        
                     }
                        break;
                     
                  }
                  
               }
               
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
   


   // $ANTLR end "time_point_in_time"
   
   // $ANTLR start "parseTimeSpec"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:135:1:
   // parseTimeSpec[MolokoCalendar cal, boolean adjustDay] returns [ParseTimeReturn result] : ( ( AT | COMMA )? (
   // time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? ) ) |
   // EOF );
   public final ParseTimeReturn parseTimeSpec( MolokoCalendar cal,
                                               boolean adjustDay ) throws RecognitionException
   {
      ParseTimeReturn result = null;
      
      cal.set( Calendar.HOUR_OF_DAY, 0 );
      cal.set( Calendar.MINUTE, 0 );
      cal.set( Calendar.SECOND, 0 );
      cal.set( Calendar.MILLISECOND, 0 );
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:147:4:
         // ( ( AT | COMMA )? ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] (
         // time_naturalspec[$cal] )? )? ) ) | EOF )
         int alt9 = 2;
         int LA9_0 = input.LA( 1 );
         
         if ( ( ( LA9_0 >= AT && LA9_0 <= COMMA ) || LA9_0 == INT ) )
         {
            alt9 = 1;
         }
         else if ( ( LA9_0 == EOF ) )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:147:6:
               // ( AT | COMMA )? ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] (
               // time_naturalspec[$cal] )? )? ) )
            {
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:147:6:
               // ( AT | COMMA )?
               int alt5 = 2;
               int LA5_0 = input.LA( 1 );
               
               if ( ( ( LA5_0 >= AT && LA5_0 <= COMMA ) ) )
               {
                  alt5 = 1;
               }
               switch ( alt5 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                  {
                     if ( ( input.LA( 1 ) >= AT && input.LA( 1 ) <= COMMA ) )
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
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:147:20:
               // ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] (
               // time_naturalspec[$cal] )? )? ) )
               int alt8 = 2;
               int LA8_0 = input.LA( 1 );
               
               if ( ( LA8_0 == INT ) )
               {
                  int LA8_1 = input.LA( 2 );
                  
                  if ( ( ( LA8_1 >= DOT && LA8_1 <= SECONDS ) ) )
                  {
                     alt8 = 2;
                  }
                  else if ( ( LA8_1 == EOF || LA8_1 == COLON ) )
                  {
                     alt8 = 1;
                  }
                  else
                  {
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           8,
                                                                           1,
                                                                           input );
                     
                     throw nvae;
                  }
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:147:24:
                     // time_separatorspec[$cal]
                  {
                     pushFollow( FOLLOW_time_separatorspec_in_parseTimeSpec331 );
                     time_separatorspec( cal );
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:148:24:
                     // ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
                  {
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:148:24:
                     // ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:148:26:
                     // time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
                     {
                        pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec359 );
                        time_naturalspec( cal );
                        
                        state._fsp--;
                        
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:149:24:
                        // ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
                        int alt7 = 2;
                        int LA7_0 = input.LA( 1 );
                        
                        if ( ( LA7_0 == INT ) )
                        {
                           alt7 = 1;
                        }
                        switch ( alt7 )
                        {
                           case 1:
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:149:26:
                              // time_naturalspec[$cal] ( time_naturalspec[$cal] )?
                           {
                              pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec387 );
                              time_naturalspec( cal );
                              
                              state._fsp--;
                              
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:150:26:
                              // ( time_naturalspec[$cal] )?
                              int alt6 = 2;
                              int LA6_0 = input.LA( 1 );
                              
                              if ( ( LA6_0 == INT ) )
                              {
                                 alt6 = 1;
                              }
                              switch ( alt6 )
                              {
                                 case 1:
                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:150:26:
                                    // time_naturalspec[$cal]
                                 {
                                    pushFollow( FOLLOW_time_naturalspec_in_parseTimeSpec415 );
                                    time_naturalspec( cal );
                                    
                                    state._fsp--;
                                    
                                 }
                                    break;
                                 
                              }
                              
                           }
                              break;
                           
                        }
                        
                     }
                     
                  }
                     break;
                  
               }
               
               if ( adjustDay && getCalendar().after( cal ) )
                  cal.roll( Calendar.DAY_OF_WEEK, true );
               
               cal.setHasTime( true );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:158:6:
               // EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseTimeSpec453 );
               
            }
               break;
            
         }
         
         result = finishedParsing();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      catch ( LexerException e )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return result;
   }
   


   // $ANTLR end "parseTimeSpec"
   
   // $ANTLR start "time_separatorspec"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:169:1:
   // time_separatorspec[MolokoCalendar cal] : (h= time_component ( COLON m= time_component ( COLON s= time_component )?
   // )? ) ;
   public final void time_separatorspec( MolokoCalendar cal ) throws RecognitionException
   {
      int h = 0;
      
      int m = 0;
      
      int s = 0;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:170:4:
         // ( (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:170:6:
         // (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? )
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:170:6:
            // (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:170:7:
            // h= time_component ( COLON m= time_component ( COLON s= time_component )? )?
            {
               pushFollow( FOLLOW_time_component_in_time_separatorspec495 );
               h = time_component();
               
               state._fsp--;
               
               cal.set( Calendar.HOUR_OF_DAY, h );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:174:7:
               // ( COLON m= time_component ( COLON s= time_component )? )?
               int alt11 = 2;
               int LA11_0 = input.LA( 1 );
               
               if ( ( LA11_0 == COLON ) )
               {
                  alt11 = 1;
               }
               switch ( alt11 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:174:8:
                     // COLON m= time_component ( COLON s= time_component )?
                  {
                     match( input, COLON, FOLLOW_COLON_in_time_separatorspec512 );
                     pushFollow( FOLLOW_time_component_in_time_separatorspec516 );
                     m = time_component();
                     
                     state._fsp--;
                     
                     cal.set( Calendar.MINUTE, m );
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:178:8:
                     // ( COLON s= time_component )?
                     int alt10 = 2;
                     int LA10_0 = input.LA( 1 );
                     
                     if ( ( LA10_0 == COLON ) )
                     {
                        alt10 = 1;
                     }
                     switch ( alt10 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:178:9:
                           // COLON s= time_component
                        {
                           match( input,
                                  COLON,
                                  FOLLOW_COLON_in_time_separatorspec535 );
                           pushFollow( FOLLOW_time_component_in_time_separatorspec539 );
                           s = time_component();
                           
                           state._fsp--;
                           
                           cal.set( Calendar.SECOND, s );
                           
                        }
                           break;
                        
                     }
                     
                  }
                     break;
                  
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
   


   // $ANTLR end "time_separatorspec"
   
   // $ANTLR start "time_naturalspec"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:189:1:
   // time_naturalspec[MolokoCalendar cal] returns [int seconds] : (fs= hour_floatspec | v= INT ( HOURS | MINUTES |
   // SECONDS ) );
   public final int time_naturalspec( MolokoCalendar cal ) throws RecognitionException
   {
      int seconds = 0;
      
      Token v = null;
      int fs = 0;
      
      int calType = -1;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:199:4:
         // (fs= hour_floatspec | v= INT ( HOURS | MINUTES | SECONDS ) )
         int alt13 = 2;
         int LA13_0 = input.LA( 1 );
         
         if ( ( LA13_0 == INT ) )
         {
            int LA13_1 = input.LA( 2 );
            
            if ( ( LA13_1 == DOT ) )
            {
               alt13 = 1;
            }
            else if ( ( ( LA13_1 >= HOURS && LA13_1 <= SECONDS ) ) )
            {
               alt13 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     13,
                                                                     1,
                                                                     input );
               
               throw nvae;
            }
         }
         else
         {
            NoViableAltException nvae = new NoViableAltException( "",
                                                                  13,
                                                                  0,
                                                                  input );
            
            throw nvae;
         }
         switch ( alt13 )
         {
            case 1:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:199:8:
               // fs= hour_floatspec
            {
               pushFollow( FOLLOW_hour_floatspec_in_time_naturalspec619 );
               fs = hour_floatspec();
               
               state._fsp--;
               
               seconds += fs;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:203:8:
               // v= INT ( HOURS | MINUTES | SECONDS )
            {
               v = (Token) match( input, INT, FOLLOW_INT_in_time_naturalspec639 );
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:203:14:
               // ( HOURS | MINUTES | SECONDS )
               int alt12 = 3;
               switch ( input.LA( 1 ) )
               {
                  case HOURS:
                  {
                     alt12 = 1;
                  }
                     break;
                  case MINUTES:
                  {
                     alt12 = 2;
                  }
                     break;
                  case SECONDS:
                  {
                     alt12 = 3;
                  }
                     break;
                  default :
                     NoViableAltException nvae = new NoViableAltException( "",
                                                                           12,
                                                                           0,
                                                                           input );
                     
                     throw nvae;
               }
               
               switch ( alt12 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:203:16:
                     // HOURS
                  {
                     match( input, HOURS, FOLLOW_HOURS_in_time_naturalspec643 );
                     
                     calType = Calendar.HOUR_OF_DAY;
                     
                  }
                     break;
                  case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:207:18:
                     // MINUTES
                  {
                     match( input,
                            MINUTES,
                            FOLLOW_MINUTES_in_time_naturalspec679 );
                     
                     calType = Calendar.MINUTE;
                     
                  }
                     break;
                  case 3:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:211:18:
                     // SECONDS
                  {
                     match( input,
                            SECONDS,
                            FOLLOW_SECONDS_in_time_naturalspec715 );
                     
                     calType = Calendar.SECOND;
                     
                  }
                     break;
                  
               }
               
               int val = Integer.parseInt( ( v != null ? v.getText() : null ) );
               
               switch ( calType )
               {
                  case Calendar.HOUR_OF_DAY:
                     seconds += val * 3600;
                     break;
                  case Calendar.MINUTE:
                     seconds += val * 60;
                     break;
                  case Calendar.SECOND:
                     seconds += val;
                     break;
                  default :
                     throw new RecognitionException();
               }
               
            }
               break;
            
         }
         
         if ( cal != null )
            cal.add( Calendar.SECOND, seconds );
         
      }
      catch ( NumberFormatException nfe )
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
      return seconds;
   }
   


   // $ANTLR end "time_naturalspec"
   
   // $ANTLR start "hour_floatspec"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:244:1:
   // hour_floatspec returns [int seconds] : h= INT DOT deciHour= INT HOURS ;
   public final int hour_floatspec() throws RecognitionException
   {
      int seconds = 0;
      
      Token h = null;
      Token deciHour = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:245:4:
         // (h= INT DOT deciHour= INT HOURS )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:245:6:
         // h= INT DOT deciHour= INT HOURS
         {
            h = (Token) match( input, INT, FOLLOW_INT_in_hour_floatspec799 );
            match( input, DOT, FOLLOW_DOT_in_hour_floatspec801 );
            deciHour = (Token) match( input,
                                      INT,
                                      FOLLOW_INT_in_hour_floatspec805 );
            match( input, HOURS, FOLLOW_HOURS_in_hour_floatspec807 );
            
            seconds = Integer.parseInt( ( h != null ? h.getText() : null ) ) * 3600;
            seconds += Integer.parseInt( ( deciHour != null
                                                           ? deciHour.getText()
                                                           : null ) ) * 360;
            
         }
         
      }
      catch ( NumberFormatException nfe )
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
      return seconds;
   }
   


   // $ANTLR end "hour_floatspec"
   
   // $ANTLR start "parseTimeEstimate"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:266:1:
   // parseTimeEstimate returns [long span] : ( (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA |
   // AND )* | EOF );
   public final long parseTimeEstimate() throws RecognitionException
   {
      long span = 0;
      
      Token d = null;
      int ts = 0;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:271:4:
         // ( (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )* | EOF )
         int alt17 = 2;
         int LA17_0 = input.LA( 1 );
         
         if ( ( LA17_0 == COMMA || LA17_0 == INT || LA17_0 == AND ) )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:271:6:
               // (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )*
            {
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:271:6:
               // (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+
               int cnt15 = 0;
               loop15: do
               {
                  int alt15 = 3;
                  int LA15_0 = input.LA( 1 );
                  
                  if ( ( LA15_0 == COMMA || LA15_0 == AND ) )
                  {
                     int LA15_1 = input.LA( 2 );
                     
                     if ( ( LA15_1 == INT ) )
                     {
                        alt15 = 2;
                     }
                     
                  }
                  else if ( ( LA15_0 == INT ) )
                  {
                     int LA15_3 = input.LA( 2 );
                     
                     if ( ( LA15_3 == DAYS ) )
                     {
                        alt15 = 1;
                     }
                     else if ( ( ( LA15_3 >= DOT && LA15_3 <= SECONDS ) ) )
                     {
                        alt15 = 2;
                     }
                     
                  }
                  
                  switch ( alt15 )
                  {
                     case 1:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:271:10:
                        // d= INT DAYS
                     {
                        d = (Token) match( input,
                                           INT,
                                           FOLLOW_INT_in_parseTimeEstimate872 );
                        match( input, DAYS, FOLLOW_DAYS_in_parseTimeEstimate874 );
                        
                        span += Integer.parseInt( ( d != null ? d.getText()
                                                             : null ) ) * 3600 * 24;
                        
                     }
                        break;
                     case 2:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:275:10:
                        // ( COMMA | AND )? ts= time_naturalspec[null]
                     {
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:275:10:
                        // ( COMMA | AND )?
                        int alt14 = 2;
                        int LA14_0 = input.LA( 1 );
                        
                        if ( ( LA14_0 == COMMA || LA14_0 == AND ) )
                        {
                           alt14 = 1;
                        }
                        switch ( alt14 )
                        {
                           case 1:
                              // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                           {
                              if ( input.LA( 1 ) == COMMA
                                 || input.LA( 1 ) == AND )
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
                        
                        pushFollow( FOLLOW_time_naturalspec_in_parseTimeEstimate907 );
                        ts = time_naturalspec( null );
                        
                        state._fsp--;
                        
                        span += ts;
                        
                     }
                        break;
                     
                     default :
                        if ( cnt15 >= 1 )
                           break loop15;
                        EarlyExitException eee = new EarlyExitException( 15,
                                                                         input );
                        throw eee;
                  }
                  cnt15++;
               }
               while ( true );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:279:9:
               // ( COMMA | AND )*
               loop16: do
               {
                  int alt16 = 2;
                  int LA16_0 = input.LA( 1 );
                  
                  if ( ( LA16_0 == COMMA || LA16_0 == AND ) )
                  {
                     alt16 = 1;
                  }
                  
                  switch ( alt16 )
                  {
                     case 1:
                        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                     {
                        if ( input.LA( 1 ) == COMMA || input.LA( 1 ) == AND )
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
                     
                     default :
                        break loop16;
                  }
               }
               while ( true );
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:280:6:
               // EOF
            {
               match( input, EOF, FOLLOW_EOF_in_parseTimeEstimate941 );
               
            }
               break;
            
         }
         
         span *= 1000;
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      catch ( RecognitionException e )
      {
         
         throw e;
         
      }
      catch ( LexerException e )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return span;
   }
   


   // $ANTLR end "parseTimeEstimate"
   
   // $ANTLR start "time_component"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:295:1:
   // time_component returns [int value] : c= INT ;
   public final int time_component() throws RecognitionException
   {
      int value = 0;
      
      Token c = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:296:4:
         // (c= INT )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:296:6:
         // c= INT
         {
            c = (Token) match( input, INT, FOLLOW_INT_in_time_component997 );
            
            String comp = ( c != null ? c.getText() : null );
            
            if ( comp.length() > 2 )
               comp = comp.substring( 0, 2 );
            
            value = Integer.parseInt( comp );
            
         }
         
      }
      catch ( NumberFormatException nfe )
      {
         
         throw new RecognitionException();
         
      }
      finally
      {
      }
      return value;
   }
   
   // $ANTLR end "time_component"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_set_in_parseTime94 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_point_in_time_in_parseTime103 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_EOF_in_parseTime119 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_NEVER_in_time_point_in_time161 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MIDNIGHT_in_time_point_in_time173 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MIDDAY_in_time_point_in_time185 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_point_in_time206 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_component_in_time_point_in_time226 = new BitSet( new long[]
   { 0x0000000000000C00L } );
   
   public static final BitSet FOLLOW_set_in_time_point_in_time228 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_component_in_time_point_in_time236 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_parseTimeSpec318 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_separatorspec_in_parseTimeSpec331 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec359 = new BitSet( new long[]
   { 0x00000000000003C2L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec387 = new BitSet( new long[]
   { 0x00000000000003C2L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec415 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_EOF_in_parseTimeSpec453 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec495 = new BitSet( new long[]
   { 0x0000000000000402L } );
   
   public static final BitSet FOLLOW_COLON_in_time_separatorspec512 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec516 = new BitSet( new long[]
   { 0x0000000000000402L } );
   
   public static final BitSet FOLLOW_COLON_in_time_separatorspec535 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_component_in_time_separatorspec539 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_hour_floatspec_in_time_naturalspec619 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_naturalspec639 = new BitSet( new long[]
   { 0x0000000000007000L } );
   
   public static final BitSet FOLLOW_HOURS_in_time_naturalspec643 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_MINUTES_in_time_naturalspec679 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_SECONDS_in_time_naturalspec715 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_hour_floatspec799 = new BitSet( new long[]
   { 0x0000000000000800L } );
   
   public static final BitSet FOLLOW_DOT_in_hour_floatspec801 = new BitSet( new long[]
   { 0x0000000000000200L } );
   
   public static final BitSet FOLLOW_INT_in_hour_floatspec805 = new BitSet( new long[]
   { 0x0000000000001000L } );
   
   public static final BitSet FOLLOW_HOURS_in_hour_floatspec807 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_parseTimeEstimate872 = new BitSet( new long[]
   { 0x0000000000008000L } );
   
   public static final BitSet FOLLOW_DAYS_in_parseTimeEstimate874 = new BitSet( new long[]
   { 0x00000000000103E2L } );
   
   public static final BitSet FOLLOW_set_in_parseTimeEstimate896 = new BitSet( new long[]
   { 0x00000000000003C0L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_parseTimeEstimate907 = new BitSet( new long[]
   { 0x00000000000103E2L } );
   
   public static final BitSet FOLLOW_set_in_parseTimeEstimate927 = new BitSet( new long[]
   { 0x0000000000010022L } );
   
   public static final BitSet FOLLOW_EOF_in_parseTimeEstimate941 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_component997 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
