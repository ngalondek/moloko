// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g 2011-03-14 17:39:14

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
   
   public static final int STs = 16;
   
   public static final int MIDDAY = 20;
   
   public static final int THE = 15;
   
   public static final int SECONDS = 28;
   
   public static final int NOW = 17;
   
   public static final int MIDNIGHT = 19;
   
   public static final int AND = 13;
   
   public static final int EOF = -1;
   
   public static final int MONTH = 29;
   
   public static final int AT = 8;
   
   public static final int WEEKDAY = 30;
   
   public static final int IN = 10;
   
   public static final int TONIGHT = 18;
   
   public static final int COMMA = 36;
   
   public static final int NOON = 21;
   
   public static final int NEXT = 12;
   
   public static final int DOT = 32;
   
   public static final int AM = 39;
   
   public static final int TOMORROW = 6;
   
   public static final int TODAY = 5;
   
   public static final int A = 38;
   
   public static final int MINUS_A = 35;
   
   public static final int ON = 9;
   
   public static final int INT = 37;
   
   public static final int MINUS = 34;
   
   public static final int OF = 11;
   
   public static final int YEARS = 22;
   
   public static final int NUM_STR = 41;
   
   public static final int MINUTES = 27;
   
   public static final int COLON = 33;
   
   public static final int DAYS = 25;
   
   public static final int WEEKS = 24;
   
   public static final int WS = 43;
   
   public static final int MONTHS = 23;
   
   public static final int PM = 40;
   
   public static final int NEVER = 4;
   
   public static final int DATE_SEP = 31;
   
   public static final int END = 14;
   
   public static final int YESTERDAY = 7;
   
   public static final int HOURS = 26;
   
   public static final int STRING = 42;
   
   

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
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g";
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
   


   private void setSuggId( int id )
   {
      quantifier = -1;
      suggId = id;
   }
   


   private void setSuggId( int id, Token t ) throws RecognitionException
   {
      qty( t );
      suggId = id;
   }
   


   private Token qty( Token t ) throws RecognitionException
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
   


   private List< String > getSuggestions()
   {
      if ( quantifier != -1 )
         return lang.getSuggestions( suggId, "units", quantifier );
      else
         return lang.getSuggestions( suggId );
   }
   


   // $ANTLR start "suggestTime"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:81:1: suggestTime
   // returns [ List< String > suggs ] : ( ( AT | COMMA )? time_in_words | v= INT ( time_colon_spec | time_in_units (
   // time_naturalspec ( time_naturalspec )? )? ) );
   public final List< String > suggestTime() throws RecognitionException
   {
      List< String > suggs = null;
      
      Token v = null;
      
      setSuggId( 0 );
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:92:4: ( ( AT
         // | COMMA )? time_in_words | v= INT ( time_colon_spec | time_in_units ( time_naturalspec ( time_naturalspec )?
         // )? ) )
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
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:92:6:
               // ( AT | COMMA )? time_in_words
            {
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:92:6:
               // ( AT | COMMA )?
               int alt1 = 2;
               int LA1_0 = input.LA( 1 );
               
               if ( ( LA1_0 == AT || LA1_0 == COMMA ) )
               {
                  alt1 = 1;
               }
               switch ( alt1 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:
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
               
               setSuggId( 1 );
               
               pushFollow( FOLLOW_time_in_words_in_suggestTime119 );
               time_in_words();
               
               state._fsp--;
               
            }
               break;
            case 2:
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:96:22:
               // v= INT ( time_colon_spec | time_in_units ( time_naturalspec ( time_naturalspec )? )? )
            {
               v = (Token) match( input, INT, FOLLOW_INT_in_suggestTime144 );
               
               setSuggId( 2, v );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:98:30:
               // ( time_colon_spec | time_in_units ( time_naturalspec ( time_naturalspec )? )? )
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:99:35:
                     // time_colon_spec
                  {
                     pushFollow( FOLLOW_time_colon_spec_in_suggestTime188 );
                     time_colon_spec();
                     
                     state._fsp--;
                     
                  }
                     break;
                  case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:100:35:
                     // time_in_units ( time_naturalspec ( time_naturalspec )? )?
                  {
                     pushFollow( FOLLOW_time_in_units_in_suggestTime225 );
                     time_in_units();
                     
                     state._fsp--;
                     
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:100:49:
                     // ( time_naturalspec ( time_naturalspec )? )?
                     int alt3 = 2;
                     int LA3_0 = input.LA( 1 );
                     
                     if ( ( LA3_0 == INT ) )
                     {
                        alt3 = 1;
                     }
                     switch ( alt3 )
                     {
                        case 1:
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:100:50:
                           // time_naturalspec ( time_naturalspec )?
                        {
                           pushFollow( FOLLOW_time_naturalspec_in_suggestTime228 );
                           time_naturalspec();
                           
                           state._fsp--;
                           
                           // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:100:67:
                           // ( time_naturalspec )?
                           int alt2 = 2;
                           int LA2_0 = input.LA( 1 );
                           
                           if ( ( LA2_0 == INT ) )
                           {
                              alt2 = 1;
                           }
                           switch ( alt2 )
                           {
                              case 1:
                                 // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:100:67:
                                 // time_naturalspec
                              {
                                 pushFollow( FOLLOW_time_naturalspec_in_suggestTime230 );
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
               
            }
               break;
            
         }
         
         suggs = getSuggestions();
         
      }
      catch ( RecognitionException e )
      {
         
         suggs = getSuggestions();
         
      }
      finally
      {
      }
      return suggs;
   }
   


   // $ANTLR end "suggestTime"
   
   // $ANTLR start "suggTimeEstimate"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:108:1:
   // suggTimeEstimate returns [ List< String > suggs ] : v= INT ( ( DAYS | time_in_units ) ) ( ( COMMA | AND )
   // time_naturalspec )* ;
   public final List< String > suggTimeEstimate() throws RecognitionException
   {
      List< String > suggs = null;
      
      Token v = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:113:4: (v=
         // INT ( ( DAYS | time_in_units ) ) ( ( COMMA | AND ) time_naturalspec )* )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:113:6: v=
         // INT ( ( DAYS | time_in_units ) ) ( ( COMMA | AND ) time_naturalspec )*
         {
            v = (Token) match( input, INT, FOLLOW_INT_in_suggTimeEstimate309 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:113:12: (
            // ( DAYS | time_in_units ) )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:114:14: (
            // DAYS | time_in_units )
            {
               
               setSuggId( 10, v );
               
               // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:117:14:
               // ( DAYS | time_in_units )
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:117:15:
                     // DAYS
                  {
                     match( input, DAYS, FOLLOW_DAYS_in_suggTimeEstimate342 );
                     
                  }
                     break;
                  case 2:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:117:22:
                     // time_in_units
                  {
                     pushFollow( FOLLOW_time_in_units_in_suggTimeEstimate346 );
                     time_in_units();
                     
                     state._fsp--;
                     
                  }
                     break;
                  
               }
               
            }
            
            setSuggId( 12 );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:122:6: (
            // ( COMMA | AND ) time_naturalspec )*
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
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:122:7:
                     // ( COMMA | AND ) time_naturalspec
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
                     
                     setSuggId( 11 );
                     
                     pushFollow( FOLLOW_time_naturalspec_in_suggTimeEstimate405 );
                     time_naturalspec();
                     
                     state._fsp--;
                     
                  }
                     break;
                  
                  default :
                     break loop7;
               }
            }
            while ( true );
            
         }
         
         suggs = getSuggestions();
         
      }
      catch ( RecognitionException e )
      {
         
         suggs = getSuggestions();
         
      }
      finally
      {
      }
      return suggs;
   }
   


   // $ANTLR end "suggTimeEstimate"
   
   // $ANTLR start "time_in_words"
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:132:1:
   // time_in_words : ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) );
   public final void time_in_words() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:133:4: (
         // NEVER | MIDNIGHT | ( MIDDAY | NOON ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:142:1:
   // time_in_units : ( HOURS | MINUTES | SECONDS ) ;
   public final void time_in_units() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:143:4: ( (
         // HOURS | MINUTES | SECONDS ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:143:6: (
         // HOURS | MINUTES | SECONDS )
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:150:1:
   // time_colon_spec : ( COLON | DOT ) INT ( A | AM | PM )? ;
   public final void time_colon_spec() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:151:2: ( (
         // COLON | DOT ) INT ( A | AM | PM )? )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:151:4: (
         // COLON | DOT ) INT ( A | AM | PM )?
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:151:4: (
            // COLON | DOT )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:151:5:
                  // COLON
               {
                  match( input, COLON, FOLLOW_COLON_in_time_colon_spec520 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:151:13:
                  // DOT
               {
                  match( input, DOT, FOLLOW_DOT_in_time_colon_spec524 );
                  
                  suggId = 3;
                  
               }
                  break;
               
            }
            
            match( input, INT, FOLLOW_INT_in_time_colon_spec529 );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:153:24: (
            // A | AM | PM )?
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( ( LA9_0 >= A && LA9_0 <= PM ) ) )
            {
               alt9 = 1;
            }
            switch ( alt9 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:
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
   // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:160:1:
   // time_naturalspec : v= INT time_in_units ;
   public final void time_naturalspec() throws RecognitionException
   {
      Token v = null;
      
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:161:4: (v=
         // INT time_in_units )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeAutoCompl.g:161:6: v=
         // INT time_in_units
         {
            v = (Token) match( input, INT, FOLLOW_INT_in_time_naturalspec569 );
            qty( v );
            pushFollow( FOLLOW_time_in_units_in_time_naturalspec573 );
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
   
   public static final BitSet FOLLOW_set_in_suggestTime85 = new BitSet( new long[]
   { 0x0000000000380010L } );
   
   public static final BitSet FOLLOW_time_in_words_in_suggestTime119 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_suggestTime144 = new BitSet( new long[]
   { 0x000000031C000000L } );
   
   public static final BitSet FOLLOW_time_colon_spec_in_suggestTime188 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_time_in_units_in_suggestTime225 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_suggestTime228 = new BitSet( new long[]
   { 0x0000002000000002L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_suggestTime230 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_suggTimeEstimate309 = new BitSet( new long[]
   { 0x000000031E000000L } );
   
   public static final BitSet FOLLOW_DAYS_in_suggTimeEstimate342 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_time_in_units_in_suggTimeEstimate346 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_set_in_suggTimeEstimate375 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_time_naturalspec_in_suggTimeEstimate405 = new BitSet( new long[]
   { 0x0000001000002002L } );
   
   public static final BitSet FOLLOW_set_in_time_in_words0 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_set_in_time_in_units482 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_COLON_in_time_colon_spec520 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_DOT_in_time_colon_spec524 = new BitSet( new long[]
   { 0x0000002000000000L } );
   
   public static final BitSet FOLLOW_INT_in_time_colon_spec529 = new BitSet( new long[]
   { 0x000001C000000002L } );
   
   public static final BitSet FOLLOW_set_in_time_colon_spec531 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
   public static final BitSet FOLLOW_INT_in_time_naturalspec569 = new BitSet( new long[]
   { 0x000000031C000000L } );
   
   public static final BitSet FOLLOW_time_in_units_in_time_naturalspec573 = new BitSet( new long[]
   { 0x0000000000000002L } );
   
}
