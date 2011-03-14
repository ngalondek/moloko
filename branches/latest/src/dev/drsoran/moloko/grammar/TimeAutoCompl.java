// $ANTLR 3.3 Nov 30, 2010 12:45:30 F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g 2011-03-14 16:03:52

package dev.drsoran.moloko.grammar;

import java.util.List;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

import dev.drsoran.moloko.grammar.lang.AutoComplLanguage;


public class TimeAutoCompl extends Parser
{
   public static final String[] tokenNames = new String[]
   { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NEVER", "TODAY", "TOMORROW",
    "YESTERDAY", "AT", "ON", "IN", "OF", "NEXT", "AND", "END", "THE", "STs",
    "NOW", "TONIGHT", "MIDNIGHT", "MIDDAY", "NOON", "YEARS", "MONTHS", "WEEKS",
    "DAYS", "HOURS", "MINUTES", "SECONDS", "MONTH", "WEEKDAY", "DATE_SEP",
    "DOT", "COLON", "MINUS", "MINUS_A", "COMMA", "INT", "A", "AM", "PM",
    "NUM_STR", "STRING", "WS" };
   
   public static final int EOF = -1;
   
   public static final int NEVER = 4;
   
   public static final int TODAY = 5;
   
   public static final int TOMORROW = 6;
   
   public static final int YESTERDAY = 7;
   
   public static final int AT = 8;
   
   public static final int ON = 9;
   
   public static final int IN = 10;
   
   public static final int OF = 11;
   
   public static final int NEXT = 12;
   
   public static final int AND = 13;
   
   public static final int END = 14;
   
   public static final int THE = 15;
   
   public static final int STs = 16;
   
   public static final int NOW = 17;
   
   public static final int TONIGHT = 18;
   
   public static final int MIDNIGHT = 19;
   
   public static final int MIDDAY = 20;
   
   public static final int NOON = 21;
   
   public static final int YEARS = 22;
   
   public static final int MONTHS = 23;
   
   public static final int WEEKS = 24;
   
   public static final int DAYS = 25;
   
   public static final int HOURS = 26;
   
   public static final int MINUTES = 27;
   
   public static final int SECONDS = 28;
   
   public static final int MONTH = 29;
   
   public static final int WEEKDAY = 30;
   
   public static final int DATE_SEP = 31;
   
   public static final int DOT = 32;
   
   public static final int COLON = 33;
   
   public static final int MINUS = 34;
   
   public static final int MINUS_A = 35;
   
   public static final int COMMA = 36;
   
   public static final int INT = 37;
   
   public static final int A = 38;
   
   public static final int AM = 39;
   
   public static final int PM = 40;
   
   public static final int NUM_STR = 41;
   
   public static final int STRING = 42;
   
   public static final int WS = 43;
   
   

   // delegates
   // delegators
   
   public TimeAutoCompl( TokenStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public TimeAutoCompl( TokenStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   @Override
   public String[] getTokenNames()
   {
      return TimeAutoCompl.tokenNames;
   }
   


   @Override
   public String getGrammarFileName()
   {
      return "F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g";
   }
   


   public TimeAutoCompl()
   {
      super( null );
   }
   
   private int suggId = -1;
   
   private int quantifier = 1;
   
   private AutoComplLanguage lang;
   
   

   public void setLanguage( AutoComplLanguage lang )
   {
      this.lang = lang;
   }
   


   Token qty( Token t ) throws RecognitionException
   {
      try
      {
         quantifier = Integer.parseInt( t.getText() );
      }
      catch ( NumberFormatException nfe )
      {
         throw new RecognitionException();
      }
      
      return t;
   }
   


   // $ANTLR start "suggestTime"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:58:1: suggestTime returns [ List<
   // String > suggs ] : ( ( AT | COMMA )? time_in_words | v= INT ( time_colon_spec | time_in_units ( time_naturalspec (
   // time_naturalspec )? )? ) );
   public final List< String > suggestTime() throws RecognitionException
   {
      List< String > suggs = null;
      
      Token v = null;
      
      suggId = 0;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:65:4: ( ( AT | COMMA )?
         // time_in_words | v= INT ( time_colon_spec | time_in_units ( time_naturalspec ( time_naturalspec )? )? ) )
         int alt5 = 2;
         int LA5_0 = input.LA( 1 );
         
         if ( ( LA5_0 == NEVER || LA5_0 == AT
            || ( LA5_0 >= MIDNIGHT && LA5_0 <= NOON ) || LA5_0 == COMMA ) )
         {
            alt5 = 1;
         }
         else if ( ( LA5_0 == INT ) )
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
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:65:6: ( AT | COMMA )?
               // time_in_words
            {
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:65:6: ( AT | COMMA )?
               int alt1 = 2;
               int LA1_0 = input.LA( 1 );
               
               if ( ( LA1_0 == AT || LA1_0 == COMMA ) )
               {
                  alt1 = 1;
               }
               switch ( alt1 )
               {
                  case 1:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:
                  {
                     if ( input.LA( 1 ) == AT || input.LA( 1 ) == COMMA )
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
               
               suggId = 1;
               
               pushFollow( FOLLOW_time_in_words_in_suggestTime108 );
               time_in_words();
               
               state._fsp--;
               
            }
               break;
            case 2:
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:69:22: v= INT (
               // time_colon_spec | time_in_units ( time_naturalspec ( time_naturalspec )? )? )
            {
               v = (Token) match( input, INT, FOLLOW_INT_in_suggestTime133 );
               
               qty( v );
               suggId = 2;
               
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:72:30: ( time_colon_spec
               // | time_in_units ( time_naturalspec ( time_naturalspec )? )? )
               int alt4 = 2;
               int LA4_0 = input.LA( 1 );
               
               if ( ( ( LA4_0 >= DOT && LA4_0 <= COLON ) ) )
               {
                  alt4 = 1;
               }
               else if ( ( ( LA4_0 >= HOURS && LA4_0 <= SECONDS ) ) )
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
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:73:35:
                     // time_colon_spec
                  {
                     pushFollow( FOLLOW_time_colon_spec_in_suggestTime177 );
                     time_colon_spec();
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 2:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:74:35:
                     // time_in_units ( time_naturalspec ( time_naturalspec )? )?
                  {
                     pushFollow( FOLLOW_time_in_units_in_suggestTime214 );
                     time_in_units();
                     
                     state._fsp--;
                     
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:74:49: (
                     // time_naturalspec ( time_naturalspec )? )?
                     int alt3 = 2;
                     int LA3_0 = input.LA( 1 );
                     
                     if ( ( LA3_0 == INT ) )
                     {
                        alt3 = 1;
                     }
                     switch ( alt3 )
                     {
                        case 1:
                           // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:74:50:
                           // time_naturalspec ( time_naturalspec )?
                        {
                           pushFollow( FOLLOW_time_naturalspec_in_suggestTime217 );
                           time_naturalspec();
                           
                           state._fsp--;
                           
                           // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:74:67: (
                           // time_naturalspec )?
                           int alt2 = 2;
                           int LA2_0 = input.LA( 1 );
                           
                           if ( ( LA2_0 == INT ) )
                           {
                              alt2 = 1;
                           }
                           switch ( alt2 )
                           {
                              case 1:
                                 // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:74:67:
                                 // time_naturalspec
                              {
                                 pushFollow( FOLLOW_time_naturalspec_in_suggestTime219 );
                                 time_naturalspec();
                                 
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
               
               suggs = lang.getSuggestions( suggId, "units", quantifier );
               
            }
               break;
            
         }
      }
      catch ( RecognitionException e )
      {
         
         suggs = lang.getSuggestions( suggId, "units", quantifier );
         
      }
      finally
      {
      }
      return suggs;
   }
   


   // $ANTLR end "suggestTime"
   
   // $ANTLR start "suggTimeEstimate"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:85:1: suggTimeEstimate returns [
   // List< String > suggs ] : v= INT ( ( DAYS | time_in_units ) ) ( ( COMMA | AND ) time_naturalspec )* ;
   public final List< String > suggTimeEstimate() throws RecognitionException
   {
      List< String > suggs = null;
      
      Token v = null;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:86:4: (v= INT ( ( DAYS |
         // time_in_units ) ) ( ( COMMA | AND ) time_naturalspec )* )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:86:6: v= INT ( ( DAYS |
         // time_in_units ) ) ( ( COMMA | AND ) time_naturalspec )*
         {
            v = (Token) match( input, INT, FOLLOW_INT_in_suggTimeEstimate292 );
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:86:12: ( ( DAYS |
            // time_in_units ) )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:87:14: ( DAYS |
            // time_in_units )
            {
               
               qty( v );
               suggId = 10;
               
               // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:91:14: ( DAYS |
               // time_in_units )
               int alt6 = 2;
               int LA6_0 = input.LA( 1 );
               
               if ( ( LA6_0 == DAYS ) )
               {
                  alt6 = 1;
               }
               else if ( ( ( LA6_0 >= HOURS && LA6_0 <= SECONDS ) ) )
               {
                  alt6 = 2;
               }
               else
               {
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        6,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
               }
               switch ( alt6 )
               {
                  case 1:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:91:15: DAYS
                  {
                     match( input, DAYS, FOLLOW_DAYS_in_suggTimeEstimate325 );
                     
                  }
                     break;
                  case 2:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:91:22:
                     // time_in_units
                  {
                     pushFollow( FOLLOW_time_in_units_in_suggTimeEstimate329 );
                     time_in_units();
                     
                     state._fsp--;
                     
                  }
                     break;
                  
               }
               
            }
            
            suggId = 12;
            
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:96:6: ( ( COMMA | AND )
            // time_naturalspec )*
            loop7: do
            {
               int alt7 = 2;
               int LA7_0 = input.LA( 1 );
               
               if ( ( LA7_0 == AND || LA7_0 == COMMA ) )
               {
                  alt7 = 1;
               }
               
               switch ( alt7 )
               {
                  case 1:
                     // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:96:7: ( COMMA | AND
                     // ) time_naturalspec
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
                     
                     suggId = 11;
                     
                     pushFollow( FOLLOW_time_naturalspec_in_suggTimeEstimate388 );
                     time_naturalspec();
                     
                     state._fsp--;
                     
                  }
                     break;
                  
                  default :
                     break loop7;
               }
            }
            while ( true );
            
            suggs = lang.getSuggestions( suggId, "units", quantifier );
            
         }
         
      }
      catch ( RecognitionException e )
      {
         
         suggs = lang.getSuggestions( suggId, "units", quantifier );
         
      }
      finally
      {
      }
      return suggs;
   }
   


   // $ANTLR end "suggTimeEstimate"
   
   // $ANTLR start "time_in_words"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:109:1: time_in_words : ( NEVER |
   // MIDNIGHT | ( MIDDAY | NOON ) );
   public final void time_in_words() throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:110:4: ( NEVER | MIDNIGHT | (
         // MIDDAY | NOON ) )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:
         {
            if ( input.LA( 1 ) == NEVER
               || ( input.LA( 1 ) >= MIDNIGHT && input.LA( 1 ) <= NOON ) )
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
   


   // $ANTLR end "time_in_words"
   
   // $ANTLR start "time_in_units"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:119:1: time_in_units : ( HOURS |
   // MINUTES | SECONDS ) ;
   public final void time_in_units() throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:120:4: ( ( HOURS | MINUTES |
         // SECONDS ) )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:120:6: ( HOURS | MINUTES |
         // SECONDS )
         {
            if ( ( input.LA( 1 ) >= HOURS && input.LA( 1 ) <= SECONDS ) )
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
   


   // $ANTLR end "time_in_units"
   
   // $ANTLR start "time_colon_spec"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:127:1: time_colon_spec : ( COLON |
   // DOT ) INT ( A | AM | PM )? ;
   public final void time_colon_spec() throws RecognitionException
   {
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:128:2: ( ( COLON | DOT ) INT (
         // A | AM | PM )? )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:128:4: ( COLON | DOT ) INT ( A
         // | AM | PM )?
         {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:128:4: ( COLON | DOT )
            int alt8 = 2;
            int LA8_0 = input.LA( 1 );
            
            if ( ( LA8_0 == COLON ) )
            {
               alt8 = 1;
            }
            else if ( ( LA8_0 == DOT ) )
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
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:128:5: COLON
               {
                  match( input, COLON, FOLLOW_COLON_in_time_colon_spec510 );
                  
               }
                  break;
               case 2:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:128:13: DOT
               {
                  match( input, DOT, FOLLOW_DOT_in_time_colon_spec514 );
                  
                  suggId = 3;
                  
               }
                  break;
               
            }
            
            match( input, INT, FOLLOW_INT_in_time_colon_spec519 );
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:130:24: ( A | AM | PM )?
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( ( LA9_0 >= A && LA9_0 <= PM ) ) )
            {
               alt9 = 1;
            }
            switch ( alt9 )
            {
               case 1:
                  // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:
               {
                  if ( ( input.LA( 1 ) >= A && input.LA( 1 ) <= PM ) )
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
   


   // $ANTLR end "time_colon_spec"
   
   // $ANTLR start "time_naturalspec"
   // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:137:1: time_naturalspec : v= INT
   // time_in_units ;
   public final void time_naturalspec() throws RecognitionException
   {
      Token v = null;
      
      try
      {
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:138:4: (v= INT time_in_units )
         // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:138:6: v= INT time_in_units
         {
            v = (Token) match( input, INT, FOLLOW_INT_in_time_naturalspec559 );
            qty( v );
            pushFollow( FOLLOW_time_in_units_in_time_naturalspec563 );
            time_in_units();
            
            state._fsp--;
            
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
   
   // $ANTLR end "time_naturalspec"
   
   // Delegated rules
   
   public static final BitSet FOLLOW_set_in_suggestTime74 = new BitSet( new long[]
   { 0x0000000000380010L } );
   
   public static final BitSet FOLLOW_time_in_words_in_suggestTime108 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_suggestTime133 = new BitSet( new long[]
   { 0x000000031C000000L } );
   
   public static final BitSet FOLLOW_time_colon_spec_in_suggestTime177 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_in_units_in_suggestTime214 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_suggestTime217 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_suggestTime219 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_suggTimeEstimate292 = new BitSet( new long[]
   { 0x000000031E000000L } );
   
   public static final BitSet FOLLOW_DAYS_in_suggTimeEstimate325 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_time_in_units_in_suggTimeEstimate329 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_set_in_suggTimeEstimate358 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_suggTimeEstimate388 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_set_in_time_in_words0 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_time_in_units472 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_COLON_in_time_colon_spec510 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_DOT_in_time_colon_spec514 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_time_colon_spec519 = new BitSet( new long[]
   { 0x000001C000000002L } );
   
   public static final BitSet FOLLOW_set_in_time_colon_spec521 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_naturalspec559 = new BitSet( new long[]
   { 0x000000031C000000L } );
   
   public static final BitSet FOLLOW_time_in_units_in_time_naturalspec563 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
