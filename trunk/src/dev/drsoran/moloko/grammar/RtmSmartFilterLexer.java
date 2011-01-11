// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g 2011-01-12 06:30:23

package dev.drsoran.moloko.grammar;

import java.util.ArrayList;
import java.util.Calendar;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;

import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.Tasks;


public class RtmSmartFilterLexer extends Lexer
{
   public static final int OP_IS_TAGGED = 15;
   
   public static final int OP_DUE = 22;
   
   public static final int OP_HAS_NOTES = 21;
   
   public static final int OP_DUE_BEFORE = 24;
   
   public static final int OP_ADDED_WITHIN = 33;
   
   public static final int OP_COMPLETED_WITHIN = 29;
   
   public static final int OP_COMPLETED_AFTER = 28;
   
   public static final int NOT = 42;
   
   public static final int AND = 40;
   
   public static final int EOF = -1;
   
   public static final int COMPLETED = 8;
   
   public static final int OP_PRIORITY = 7;
   
   public static final int OP_ADDED_BEFORE = 31;
   
   public static final int OP_TAG = 11;
   
   public static final int OP_DUE_WITHIN = 25;
   
   public static final int Q_STRING = 5;
   
   public static final int OP_ADDED_AFTER = 32;
   
   public static final int OP_LIST = 6;
   
   public static final int OP_COMPLETED = 26;
   
   public static final int L_PARENTH = 38;
   
   public static final int OP_STATUS = 10;
   
   public static final int OP_IS_SHARED = 36;
   
   public static final int OP_LOCATION = 16;
   
   public static final int OP_NOTE_CONTAINS = 20;
   
   public static final int INCOMPLETE = 9;
   
   public static final int OP_SHARED_WITH = 37;
   
   public static final int TRUE = 13;
   
   public static final int OP_DUE_AFTER = 23;
   
   public static final int OP_TAG_CONTAINS = 12;
   
   public static final int R_PARENTH = 39;
   
   public static final int OP_COMPLETED_BEFORE = 27;
   
   public static final int OP_POSTPONED = 35;
   
   public static final int OP_IS_REPEATING = 18;
   
   public static final int WS = 43;
   
   public static final int OP_TIME_ESTIMATE = 34;
   
   public static final int OR = 41;
   
   public static final int OP_NAME = 19;
   
   public static final int FALSE = 14;
   
   public static final int OP_ADDED = 30;
   
   public static final int OP_ISLOCATED = 17;
   
   public static final int STRING = 4;
   
   // BEGIN TOKEN LITERALS
   
   public final static String OP_LIST_LIT = "list:";
   
   public final static String OP_PRIORITY_LIT = "priority:";
   
   public final static String OP_STATUS_LIT = "status:";
   
   public final static String OP_TAG_LIT = "tag:";
   
   public final static String OP_TAG_CONTAINS_LIT = "tagcontains:";
   
   public final static String OP_IS_TAGGED_LIT = "istagged:";
   
   public final static String OP_LOCATION_LIT = "location:";
   
   public final static String OP_LOCATION_WITHIN_LIT = "locationwithin:";
   
   public final static String OP_IS_LOCATED_LIT = "islocated:";
   
   public final static String OP_IS_REPEATING_LIT = "isrepeating:";
   
   public final static String OP_NAME_LIT = "name:";
   
   public final static String OP_NOTE_CONTAINS_LIT = "notecontains:";
   
   public final static String OP_HAS_NOTES_LIT = "hasnotes:";
   
   public final static String OP_DUE_LIT = "due:";
   
   public final static String OP_DUE_AFTER_LIT = "dueafter:";
   
   public final static String OP_DUE_BEFORE_LIT = "duebefore:";
   
   public final static String OP_DUE_WITHIN_LIT = "duewithin:";
   
   public final static String OP_COMPLETED_LIT = "completed:";
   
   public final static String OP_COMPLETED_BEFORE_LIT = "completedbefore:";
   
   public final static String OP_COMPLETED_AFTER_LIT = "completedafter:";
   
   public final static String OP_COMPLETED_WITHIN_LIT = "completedwithin:";
   
   public final static String OP_ADDED_LIT = "added:";
   
   public final static String OP_ADDED_BEFORE_LIT = "addedbefore:";
   
   public final static String OP_ADDED_AFTER_LIT = "addedafter:";
   
   public final static String OP_ADDED_WITHIN_LIT = "addedwithin:";
   
   public final static String OP_TIME_ESTIMATE_LIT = "timeestimate:";
   
   public final static String OP_POSTPONED_LIT = "postponed:";
   
   public final static String OP_IS_SHARED_LIT = "isshared:";
   
   public final static String OP_SHARED_WITH_LIT = "sharedwith:";
   
   public final static String COMPLETED_LIT = "completed";
   
   public final static String INCOMPLETE_LIT = "incomplete";
   
   public final static String TRUE_LIT = "true";
   
   public final static String FALSE_LIT = "false";
   
   public final static String L_PARENTH_LIT = "(";
   
   public final static String R_PARENTH_LIT = ")";
   
   public final static String AND_LIT = "and";
   
   public final static String OR_LIT = "or";
   
   public final static String NOT_LIT = "not";
   
   // END TOKEN LITERALS
   
   private final static String TAGS_QUERY_PREFIX = "(SELECT "
      + Tags.TASKSERIES_ID + " FROM " + Tags.PATH + " WHERE "
      + Tags.TASKSERIES_ID + " = " + "subQuery." + Tasks.TASKSERIES_ID;
   
   // STATUS VARIABLES
   
   private final StringBuffer result = new StringBuffer();
   
   private boolean hasStatusCompletedOp = false;
   
   private boolean error = false;
   
   private boolean op_not = false;
   
   private ArrayList< RtmSmartFilterToken > tokens;
   
   

   @Override
   public void reset()
   {
      super.reset();
      
      result.setLength( 0 );
      hasStatusCompletedOp = false;
      error = false;
      tokens = null;
   }
   


   @Override
   public void reportError( RecognitionException e )
   {
      super.reportError( e );
      error = true;
   }
   


   public static final String unquotify( String input )
   {
      return input.replaceAll( "(\"|')", "" );
   }
   


   public static final String quotify( String input )
   {
      return new StringBuffer( "\"" ).append( input ).append( "\"" ).toString();
   }
   


   private static final String firstCharOf( String input )
   {
      return input.length() > 0 ? input.substring( 0, 1 ) : "";
   }
   


   private void equalsStringParam( String param )
   {
      result.append( " = '" );
      result.append( unquotify( param ) );
      result.append( "'" );
   }
   


   private void containsStringParam( String param )
   {
      result.append( " like '%" );
      result.append( unquotify( param ) );
      result.append( "%'" );
   }
   


   private void equalsIntParam( String param )
   {
      result.append( " = " );
      result.append( unquotify( param ) );
   }
   


   private void equalsTimeParam( String column, String param )
   {
      final Calendar cal = RtmDateTimeParsing.parseDateTimeSpec( unquotify( param ) );
      
      if ( cal != null )
      {
         // Check if we have an explicit time
         // given.
         if ( cal.isSet( Calendar.HOUR_OF_DAY ) )
         {
            result.append( column );
            result.append( " == " );
            result.append( cal.getTimeInMillis() );
         }
         else
         {
            result.append( column );
            result.append( " >= " );
            result.append( cal.getTimeInMillis() );
            result.append( " AND " );
            
            cal.roll( Calendar.DAY_OF_YEAR, true );
            
            result.append( column );
            result.append( " < " );
            result.append( cal.getTimeInMillis() );
         }
      }
      else
         // Parser error
         error = true;
   }
   


   private void differsTimeParam( String column, String param, boolean before )
   {
      final Calendar cal = RtmDateTimeParsing.parseDateTimeSpec( unquotify( param ) );
      
      if ( cal != null )
      {
         result.append( column );
         result.append( ( before ) ? " < " : " > " );
         result.append( cal.getTimeInMillis() );
      }
      else
         // Parser error
         error = true;
   }
   


   private void inTimeParamRange( String column, String param, boolean past )
   {
      final RtmDateTimeParsing.DateWithinReturn range = RtmDateTimeParsing.parseDateWithin( unquotify( param ),
                                                                                            past );
      
      if ( range != null )
      {
         result.append( column );
         result.append( " >= " );
         result.append( !past ? range.startEpoch.getTimeInMillis()
                             : range.endEpoch.getTimeInMillis() );
         result.append( " AND " );
         result.append( column );
         result.append( " < " );
         result.append( !past ? range.endEpoch.getTimeInMillis()
                             : range.startEpoch.getTimeInMillis() );
      }
      else
         // Parser error
         error = true;
   }
   


   public String getResult( ArrayList< RtmSmartFilterToken > tokens ) throws RecognitionException
   {
      this.tokens = tokens;
      
      if ( !error && result.length() == 0 )
      {
         result.append( "( " );
         
         hasStatusCompletedOp = false;
         
         while ( !error && nextToken() != Token.EOF_TOKEN )
         {
         }
         
         result.append( " )" );
      }
      
      error = error || result.length() == 0;
      
      if ( error )
         throw new RecognitionException();
      
      return result.toString();
   }
   


   public boolean hasStatusCompletedOperator()
   {
      return hasStatusCompletedOp;
   }
   


   private final void addRtmToken( int type, String param )
   {
      if ( tokens != null )
         tokens.add( new RtmSmartFilterToken( type, unquotify( param ), op_not ) );
   }
   


   // delegates
   // delegators
   
   public RtmSmartFilterLexer()
   {
      ;
   }
   


   public RtmSmartFilterLexer( CharStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   


   public RtmSmartFilterLexer( CharStream input, RecognizerSharedState state )
   {
      super( input, state );
      
   }
   


   @Override
   public String getGrammarFileName()
   {
      return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g";
   }
   


   // $ANTLR start "OP_LIST"
   public final void mOP_LIST() throws RecognitionException
   {
      try
      {
         int _type = OP_LIST;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:310:13:
         // ( 'list:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:310:16:
         // 'list:' (s= STRING | s= Q_STRING )
         {
            match( "list:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:310:24:
            // (s= STRING | s= Q_STRING )
            int alt1 = 2;
            int LA1_0 = input.LA( 1 );
            
            if ( ( ( LA1_0 >= '\u0000' && LA1_0 <= '\u001F' ) || LA1_0 == '!'
               || ( LA1_0 >= '#' && LA1_0 <= '\'' ) || ( LA1_0 >= '*' && LA1_0 <= '\uFFFF' ) ) )
            {
               alt1 = 1;
            }
            else if ( ( LA1_0 == '\"' ) )
            {
               alt1 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     1,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt1 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:310:26:
                  // s= STRING
               {
                  int sStart53 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart53,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:310:37:
                  // s= Q_STRING
               {
                  int sStart59 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart59,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            result.append( Tasks.LIST_NAME );
            equalsStringParam( s.getText() );
            
            addRtmToken( OP_LIST, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_LIST"
   
   // $ANTLR start "OP_PRIORITY"
   public final void mOP_PRIORITY() throws RecognitionException
   {
      try
      {
         int _type = OP_PRIORITY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:318:13:
         // ( 'priority:' s= STRING )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:318:16:
         // 'priority:' s= STRING
         {
            match( "priority:" );
            
            int sStart91 = getCharIndex();
            mSTRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart91,
                                 getCharIndex() - 1 );
            
            result.append( Tasks.PRIORITY );
            equalsStringParam( firstCharOf( unquotify( s.getText() ) ) );
            
            addRtmToken( OP_PRIORITY, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_PRIORITY"
   
   // $ANTLR start "OP_STATUS"
   public final void mOP_STATUS() throws RecognitionException
   {
      try
      {
         int _type = OP_STATUS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:326:13:
         // ( 'status:' ( COMPLETED | INCOMPLETE ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:326:16:
         // 'status:' ( COMPLETED | INCOMPLETE )
         {
            match( "status:" );
            
            result.append( Tasks.COMPLETED_DATE );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:330:16:
            // ( COMPLETED | INCOMPLETE )
            int alt2 = 2;
            int LA2_0 = input.LA( 1 );
            
            if ( ( LA2_0 == 'c' ) )
            {
               alt2 = 1;
            }
            else if ( ( LA2_0 == 'i' ) )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:331:19:
                  // COMPLETED
               {
                  mCOMPLETED();
                  
                  result.append( " IS NOT NULL" );
                  hasStatusCompletedOp = true;
                  addRtmToken( OP_STATUS, COMPLETED_LIT );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:338:19:
                  // INCOMPLETE
               {
                  mINCOMPLETE();
                  
                  result.append( " IS NULL" );
                  addRtmToken( OP_STATUS, INCOMPLETE_LIT );
                  
               }
                  break;
               
            }
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_STATUS"
   
   // $ANTLR start "OP_TAG"
   public final void mOP_TAG() throws RecognitionException
   {
      try
      {
         int _type = OP_TAG;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:345:13:
         // ( 'tag:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:345:15:
         // 'tag:' (s= STRING | s= Q_STRING )
         {
            match( "tag:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:345:22:
            // (s= STRING | s= Q_STRING )
            int alt3 = 2;
            int LA3_0 = input.LA( 1 );
            
            if ( ( ( LA3_0 >= '\u0000' && LA3_0 <= '\u001F' ) || LA3_0 == '!'
               || ( LA3_0 >= '#' && LA3_0 <= '\'' ) || ( LA3_0 >= '*' && LA3_0 <= '\uFFFF' ) ) )
            {
               alt3 = 1;
            }
            else if ( ( LA3_0 == '\"' ) )
            {
               alt3 = 2;
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:345:24:
                  // s= STRING
               {
                  int sStart289 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart289,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:345:35:
                  // s= Q_STRING
               {
                  int sStart295 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart295,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            result.append( TAGS_QUERY_PREFIX )
                  .append( " AND " )
                  .append( Tags.TAG );
            equalsStringParam( s.getText() );
            result.append( ")" );
            
            addRtmToken( OP_TAG, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_TAG"
   
   // $ANTLR start "OP_TAG_CONTAINS"
   public final void mOP_TAG_CONTAINS() throws RecognitionException
   {
      try
      {
         int _type = OP_TAG_CONTAINS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:356:17:
         // ( 'tagcontains:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:356:19:
         // 'tagcontains:' (s= STRING | s= Q_STRING )
         {
            match( "tagcontains:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:356:34:
            // (s= STRING | s= Q_STRING )
            int alt4 = 2;
            int LA4_0 = input.LA( 1 );
            
            if ( ( ( LA4_0 >= '\u0000' && LA4_0 <= '\u001F' ) || LA4_0 == '!'
               || ( LA4_0 >= '#' && LA4_0 <= '\'' ) || ( LA4_0 >= '*' && LA4_0 <= '\uFFFF' ) ) )
            {
               alt4 = 1;
            }
            else if ( ( LA4_0 == '\"' ) )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:356:36:
                  // s= STRING
               {
                  int sStart327 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart327,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:356:47:
                  // s= Q_STRING
               {
                  int sStart333 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart333,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            result.append( TAGS_QUERY_PREFIX )
                  .append( " AND " )
                  .append( Tags.TAG );
            containsStringParam( s.getText() );
            result.append( ")" );
            
            addRtmToken( OP_TAG_CONTAINS, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_TAG_CONTAINS"
   
   // $ANTLR start "OP_IS_TAGGED"
   public final void mOP_IS_TAGGED() throws RecognitionException
   {
      try
      {
         int _type = OP_IS_TAGGED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:367:17:
         // ( 'istagged:' ( TRUE | FALSE ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:367:19:
         // 'istagged:' ( TRUE | FALSE )
         {
            match( "istagged:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:368:19:
            // ( TRUE | FALSE )
            int alt5 = 2;
            int LA5_0 = input.LA( 1 );
            
            if ( ( LA5_0 == 't' ) )
            {
               alt5 = 1;
            }
            else if ( ( LA5_0 == 'f' ) )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:369:22:
                  // TRUE
               {
                  mTRUE();
                  
                  result.append( TAGS_QUERY_PREFIX );
                  result.append( ")" );
                  addRtmToken( OP_IS_TAGGED, TRUE_LIT );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:376:22:
                  // FALSE
               {
                  mFALSE();
                  
                  result.append( " NOT EXISTS " );
                  result.append( TAGS_QUERY_PREFIX );
                  result.append( ")" );
                  addRtmToken( OP_IS_TAGGED, FALSE_LIT );
                  
               }
                  break;
               
            }
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_IS_TAGGED"
   
   // $ANTLR start "OP_LOCATION"
   public final void mOP_LOCATION() throws RecognitionException
   {
      try
      {
         int _type = OP_LOCATION;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:385:13:
         // ( 'location:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:385:15:
         // 'location:' (s= STRING | s= Q_STRING )
         {
            match( "location:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:385:27:
            // (s= STRING | s= Q_STRING )
            int alt6 = 2;
            int LA6_0 = input.LA( 1 );
            
            if ( ( ( LA6_0 >= '\u0000' && LA6_0 <= '\u001F' ) || LA6_0 == '!'
               || ( LA6_0 >= '#' && LA6_0 <= '\'' ) || ( LA6_0 >= '*' && LA6_0 <= '\uFFFF' ) ) )
            {
               alt6 = 1;
            }
            else if ( ( LA6_0 == '\"' ) )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:385:29:
                  // s= STRING
               {
                  int sStart535 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart535,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:385:40:
                  // s= Q_STRING
               {
                  int sStart541 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart541,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            result.append( Tasks.LOCATION_NAME );
            containsStringParam( s.getText() );
            addRtmToken( OP_LOCATION, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_LOCATION"
   
   // $ANTLR start "OP_ISLOCATED"
   public final void mOP_ISLOCATED() throws RecognitionException
   {
      try
      {
         int _type = OP_ISLOCATED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:394:14:
         // ( 'islocated:' ( TRUE | FALSE ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:394:16:
         // 'islocated:' ( TRUE | FALSE )
         {
            match( "islocated:" );
            
            result.append( Tasks.LOCATION_ID );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:398:16:
            // ( TRUE | FALSE )
            int alt7 = 2;
            int LA7_0 = input.LA( 1 );
            
            if ( ( LA7_0 == 't' ) )
            {
               alt7 = 1;
            }
            else if ( ( LA7_0 == 'f' ) )
            {
               alt7 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     7,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt7 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:399:19:
                  // TRUE
               {
                  mTRUE();
                  
                  // Handle the case that shared tasks have a location
                  // ID but not from our DB.
                  result.append( " IS NOT NULL AND " );
                  result.append( Tasks.LOCATION_ID );
                  result.append( " IN ( SELECT " );
                  result.append( Locations._ID );
                  result.append( " FROM " );
                  result.append( Locations.PATH );
                  result.append( " )" );
                  addRtmToken( OP_ISLOCATED, TRUE_LIT );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:413:19:
                  // FALSE
               {
                  mFALSE();
                  
                  result.append( " IS NULL" );
                  addRtmToken( OP_ISLOCATED, FALSE_LIT );
                  
               }
                  break;
               
            }
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_ISLOCATED"
   
   // $ANTLR start "OP_IS_REPEATING"
   public final void mOP_IS_REPEATING() throws RecognitionException
   {
      try
      {
         int _type = OP_IS_REPEATING;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:420:17:
         // ( 'isrepeating:' ( TRUE | FALSE ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:420:19:
         // 'isrepeating:' ( TRUE | FALSE )
         {
            match( "isrepeating:" );
            
            result.append( Tasks.RECURRENCE );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:424:19:
            // ( TRUE | FALSE )
            int alt8 = 2;
            int LA8_0 = input.LA( 1 );
            
            if ( ( LA8_0 == 't' ) )
            {
               alt8 = 1;
            }
            else if ( ( LA8_0 == 'f' ) )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:425:22:
                  // TRUE
               {
                  mTRUE();
                  
                  result.append( " IS NOT NULL" );
                  addRtmToken( OP_IS_REPEATING, TRUE_LIT );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:431:22:
                  // FALSE
               {
                  mFALSE();
                  
                  result.append( " IS NULL" );
                  addRtmToken( OP_IS_REPEATING, FALSE_LIT );
                  
               }
                  break;
               
            }
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_IS_REPEATING"
   
   // $ANTLR start "OP_NAME"
   public final void mOP_NAME() throws RecognitionException
   {
      try
      {
         int _type = OP_NAME;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:438:14:
         // ( 'name:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:438:16:
         // 'name:' (s= STRING | s= Q_STRING )
         {
            match( "name:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:438:24:
            // (s= STRING | s= Q_STRING )
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( ( LA9_0 >= '\u0000' && LA9_0 <= '\u001F' ) || LA9_0 == '!'
               || ( LA9_0 >= '#' && LA9_0 <= '\'' ) || ( LA9_0 >= '*' && LA9_0 <= '\uFFFF' ) ) )
            {
               alt9 = 1;
            }
            else if ( ( LA9_0 == '\"' ) )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:438:26:
                  // s= STRING
               {
                  int sStart923 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart923,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:438:37:
                  // s= Q_STRING
               {
                  int sStart929 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart929,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            result.append( Tasks.TASKSERIES_NAME );
            containsStringParam( s.getText() );
            addRtmToken( OP_NAME, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_NAME"
   
   // $ANTLR start "OP_NOTE_CONTAINS"
   public final void mOP_NOTE_CONTAINS() throws RecognitionException
   {
      try
      {
         int _type = OP_NOTE_CONTAINS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:445:18:
         // ( 'notecontains:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:445:20:
         // 'notecontains:' (s= STRING | s= Q_STRING )
         {
            match( "notecontains:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:445:36:
            // (s= STRING | s= Q_STRING )
            int alt10 = 2;
            int LA10_0 = input.LA( 1 );
            
            if ( ( ( LA10_0 >= '\u0000' && LA10_0 <= '\u001F' )
               || LA10_0 == '!' || ( LA10_0 >= '#' && LA10_0 <= '\'' ) || ( LA10_0 >= '*' && LA10_0 <= '\uFFFF' ) ) )
            {
               alt10 = 1;
            }
            else if ( ( LA10_0 == '\"' ) )
            {
               alt10 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     10,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt10 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:445:38:
                  // s= STRING
               {
                  int sStart962 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart962,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:445:49:
                  // s= Q_STRING
               {
                  int sStart968 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart968,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            result.append( " (SELECT " )
                  .append( Notes.TASKSERIES_ID )
                  .append( " FROM " )
                  .append( Notes.PATH )
                  .append( " WHERE " )
                  .append( Notes.TASKSERIES_ID )
                  .append( " = subQuery." )
                  .append( Tasks.TASKSERIES_ID )
                  .append( " AND " )
                  .append( Notes.NOTE_TITLE );
            containsStringParam( s.getText() );
            result.append( " OR " ).append( Notes.NOTE_TEXT );
            containsStringParam( s.getText() );
            result.append( ")" );
            
            addRtmToken( OP_NOTE_CONTAINS, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_NOTE_CONTAINS"
   
   // $ANTLR start "OP_HAS_NOTES"
   public final void mOP_HAS_NOTES() throws RecognitionException
   {
      try
      {
         int _type = OP_HAS_NOTES;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:465:14:
         // ( 'hasnotes:' ( TRUE | FALSE ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:465:16:
         // 'hasnotes:' ( TRUE | FALSE )
         {
            match( "hasnotes:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:466:16:
            // ( TRUE | FALSE )
            int alt11 = 2;
            int LA11_0 = input.LA( 1 );
            
            if ( ( LA11_0 == 't' ) )
            {
               alt11 = 1;
            }
            else if ( ( LA11_0 == 'f' ) )
            {
               alt11 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     11,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt11 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:467:19:
                  // TRUE
               {
                  mTRUE();
                  
                  result.append( Tasks.NUM_NOTES + " > 0" );
                  addRtmToken( OP_HAS_NOTES, TRUE_LIT );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:473:19:
                  // FALSE
               {
                  mFALSE();
                  
                  result.append( Tasks.NUM_NOTES + " = 0" );
                  addRtmToken( OP_HAS_NOTES, FALSE_LIT );
                  
               }
                  break;
               
            }
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_HAS_NOTES"
   
   // $ANTLR start "OP_DUE"
   public final void mOP_DUE() throws RecognitionException
   {
      try
      {
         int _type = OP_DUE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:480:13:
         // ( 'due:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:480:16:
         // 'due:' (s= STRING | s= Q_STRING )
         {
            match( "due:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:480:23:
            // (s= STRING | s= Q_STRING )
            int alt12 = 2;
            int LA12_0 = input.LA( 1 );
            
            if ( ( ( LA12_0 >= '\u0000' && LA12_0 <= '\u001F' )
               || LA12_0 == '!' || ( LA12_0 >= '#' && LA12_0 <= '\'' ) || ( LA12_0 >= '*' && LA12_0 <= '\uFFFF' ) ) )
            {
               alt12 = 1;
            }
            else if ( ( LA12_0 == '\"' ) )
            {
               alt12 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     12,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt12 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:480:25:
                  // s= STRING
               {
                  int sStart1153 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1153,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:480:36:
                  // s= Q_STRING
               {
                  int sStart1159 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1159,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            equalsTimeParam( Tasks.DUE_DATE, s.getText() );
            addRtmToken( OP_DUE, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_DUE"
   
   // $ANTLR start "OP_DUE_AFTER"
   public final void mOP_DUE_AFTER() throws RecognitionException
   {
      try
      {
         int _type = OP_DUE_AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:486:14:
         // ( 'dueafter:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:486:16:
         // 'dueafter:' (s= STRING | s= Q_STRING )
         {
            match( "dueafter:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:486:28:
            // (s= STRING | s= Q_STRING )
            int alt13 = 2;
            int LA13_0 = input.LA( 1 );
            
            if ( ( ( LA13_0 >= '\u0000' && LA13_0 <= '\u001F' )
               || LA13_0 == '!' || ( LA13_0 >= '#' && LA13_0 <= '\'' ) || ( LA13_0 >= '*' && LA13_0 <= '\uFFFF' ) ) )
            {
               alt13 = 1;
            }
            else if ( ( LA13_0 == '\"' ) )
            {
               alt13 = 2;
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:486:30:
                  // s= STRING
               {
                  int sStart1192 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1192,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:486:41:
                  // s= Q_STRING
               {
                  int sStart1198 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1198,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            differsTimeParam( Tasks.DUE_DATE, s.getText(), false );
            addRtmToken( OP_DUE_AFTER, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_DUE_AFTER"
   
   // $ANTLR start "OP_DUE_BEFORE"
   public final void mOP_DUE_BEFORE() throws RecognitionException
   {
      try
      {
         int _type = OP_DUE_BEFORE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:492:15:
         // ( 'duebefore:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:492:17:
         // 'duebefore:' (s= STRING | s= Q_STRING )
         {
            match( "duebefore:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:492:30:
            // (s= STRING | s= Q_STRING )
            int alt14 = 2;
            int LA14_0 = input.LA( 1 );
            
            if ( ( ( LA14_0 >= '\u0000' && LA14_0 <= '\u001F' )
               || LA14_0 == '!' || ( LA14_0 >= '#' && LA14_0 <= '\'' ) || ( LA14_0 >= '*' && LA14_0 <= '\uFFFF' ) ) )
            {
               alt14 = 1;
            }
            else if ( ( LA14_0 == '\"' ) )
            {
               alt14 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     14,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt14 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:492:32:
                  // s= STRING
               {
                  int sStart1231 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1231,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:492:43:
                  // s= Q_STRING
               {
                  int sStart1237 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1237,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            differsTimeParam( Tasks.DUE_DATE, s.getText(), true );
            addRtmToken( OP_DUE_BEFORE, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_DUE_BEFORE"
   
   // $ANTLR start "OP_DUE_WITHIN"
   public final void mOP_DUE_WITHIN() throws RecognitionException
   {
      try
      {
         int _type = OP_DUE_WITHIN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:498:15:
         // ( 'duewithin:' s= Q_STRING )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:498:17:
         // 'duewithin:' s= Q_STRING
         {
            match( "duewithin:" );
            
            int sStart1269 = getCharIndex();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1269,
                                 getCharIndex() - 1 );
            
            inTimeParamRange( Tasks.DUE_DATE, s.getText(), false );
            addRtmToken( OP_DUE_WITHIN, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_DUE_WITHIN"
   
   // $ANTLR start "OP_COMPLETED"
   public final void mOP_COMPLETED() throws RecognitionException
   {
      try
      {
         int _type = OP_COMPLETED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:504:14:
         // ( 'completed:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:504:16:
         // 'completed:' (s= STRING | s= Q_STRING )
         {
            match( "completed:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:504:29:
            // (s= STRING | s= Q_STRING )
            int alt15 = 2;
            int LA15_0 = input.LA( 1 );
            
            if ( ( ( LA15_0 >= '\u0000' && LA15_0 <= '\u001F' )
               || LA15_0 == '!' || ( LA15_0 >= '#' && LA15_0 <= '\'' ) || ( LA15_0 >= '*' && LA15_0 <= '\uFFFF' ) ) )
            {
               alt15 = 1;
            }
            else if ( ( LA15_0 == '\"' ) )
            {
               alt15 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     15,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt15 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:504:31:
                  // s= STRING
               {
                  int sStart1301 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1301,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:504:42:
                  // s= Q_STRING
               {
                  int sStart1307 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1307,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            equalsTimeParam( Tasks.COMPLETED_DATE, s.getText() );
            hasStatusCompletedOp = true;
            addRtmToken( OP_COMPLETED, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_COMPLETED"
   
   // $ANTLR start "OP_COMPLETED_BEFORE"
   public final void mOP_COMPLETED_BEFORE() throws RecognitionException
   {
      try
      {
         int _type = OP_COMPLETED_BEFORE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:511:21:
         // ( 'completedbefore:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:511:23:
         // 'completedbefore:' (s= STRING | s= Q_STRING )
         {
            match( "completedbefore:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:511:42:
            // (s= STRING | s= Q_STRING )
            int alt16 = 2;
            int LA16_0 = input.LA( 1 );
            
            if ( ( ( LA16_0 >= '\u0000' && LA16_0 <= '\u001F' )
               || LA16_0 == '!' || ( LA16_0 >= '#' && LA16_0 <= '\'' ) || ( LA16_0 >= '*' && LA16_0 <= '\uFFFF' ) ) )
            {
               alt16 = 1;
            }
            else if ( ( LA16_0 == '\"' ) )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:511:44:
                  // s= STRING
               {
                  int sStart1340 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1340,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:511:55:
                  // s= Q_STRING
               {
                  int sStart1346 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1346,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            differsTimeParam( Tasks.COMPLETED_DATE, s.getText(), true );
            hasStatusCompletedOp = true;
            addRtmToken( OP_COMPLETED_BEFORE, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_COMPLETED_BEFORE"
   
   // $ANTLR start "OP_COMPLETED_AFTER"
   public final void mOP_COMPLETED_AFTER() throws RecognitionException
   {
      try
      {
         int _type = OP_COMPLETED_AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:518:20:
         // ( 'completedafter:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:518:22:
         // 'completedafter:' (s= STRING | s= Q_STRING )
         {
            match( "completedafter:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:518:40:
            // (s= STRING | s= Q_STRING )
            int alt17 = 2;
            int LA17_0 = input.LA( 1 );
            
            if ( ( ( LA17_0 >= '\u0000' && LA17_0 <= '\u001F' )
               || LA17_0 == '!' || ( LA17_0 >= '#' && LA17_0 <= '\'' ) || ( LA17_0 >= '*' && LA17_0 <= '\uFFFF' ) ) )
            {
               alt17 = 1;
            }
            else if ( ( LA17_0 == '\"' ) )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:518:42:
                  // s= STRING
               {
                  int sStart1386 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1386,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:518:53:
                  // s= Q_STRING
               {
                  int sStart1392 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1392,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            differsTimeParam( Tasks.COMPLETED_DATE, s.getText(), false );
            hasStatusCompletedOp = true;
            addRtmToken( OP_COMPLETED_AFTER, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_COMPLETED_AFTER"
   
   // $ANTLR start "OP_COMPLETED_WITHIN"
   public final void mOP_COMPLETED_WITHIN() throws RecognitionException
   {
      try
      {
         int _type = OP_COMPLETED_WITHIN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:525:21:
         // ( 'completedwithin:' s= Q_STRING )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:525:23:
         // 'completedwithin:' s= Q_STRING
         {
            match( "completedwithin:" );
            
            int sStart1429 = getCharIndex();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1429,
                                 getCharIndex() - 1 );
            
            inTimeParamRange( Tasks.COMPLETED_DATE, s.getText(), true );
            hasStatusCompletedOp = true;
            addRtmToken( OP_COMPLETED_WITHIN, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_COMPLETED_WITHIN"
   
   // $ANTLR start "OP_ADDED"
   public final void mOP_ADDED() throws RecognitionException
   {
      try
      {
         int _type = OP_ADDED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:532:14:
         // ( 'added:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:532:16:
         // 'added:' (s= STRING | s= Q_STRING )
         {
            match( "added:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:532:25:
            // (s= STRING | s= Q_STRING )
            int alt18 = 2;
            int LA18_0 = input.LA( 1 );
            
            if ( ( ( LA18_0 >= '\u0000' && LA18_0 <= '\u001F' )
               || LA18_0 == '!' || ( LA18_0 >= '#' && LA18_0 <= '\'' ) || ( LA18_0 >= '*' && LA18_0 <= '\uFFFF' ) ) )
            {
               alt18 = 1;
            }
            else if ( ( LA18_0 == '\"' ) )
            {
               alt18 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     18,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt18 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:532:27:
                  // s= STRING
               {
                  int sStart1471 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1471,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:532:38:
                  // s= Q_STRING
               {
                  int sStart1477 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1477,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            equalsTimeParam( Tasks.ADDED_DATE, s.getText() );
            addRtmToken( OP_ADDED, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_ADDED"
   
   // $ANTLR start "OP_ADDED_BEFORE"
   public final void mOP_ADDED_BEFORE() throws RecognitionException
   {
      try
      {
         int _type = OP_ADDED_BEFORE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:538:17:
         // ( 'addedbefore:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:538:19:
         // 'addedbefore:' (s= STRING | s= Q_STRING )
         {
            match( "addedbefore:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:538:34:
            // (s= STRING | s= Q_STRING )
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( ( LA19_0 >= '\u0000' && LA19_0 <= '\u001F' )
               || LA19_0 == '!' || ( LA19_0 >= '#' && LA19_0 <= '\'' ) || ( LA19_0 >= '*' && LA19_0 <= '\uFFFF' ) ) )
            {
               alt19 = 1;
            }
            else if ( ( LA19_0 == '\"' ) )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:538:36:
                  // s= STRING
               {
                  int sStart1510 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1510,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:538:47:
                  // s= Q_STRING
               {
                  int sStart1516 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1516,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            differsTimeParam( Tasks.ADDED_DATE, s.getText(), true );
            addRtmToken( OP_ADDED_BEFORE, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_ADDED_BEFORE"
   
   // $ANTLR start "OP_ADDED_AFTER"
   public final void mOP_ADDED_AFTER() throws RecognitionException
   {
      try
      {
         int _type = OP_ADDED_AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:544:16:
         // ( 'addedafter:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:544:18:
         // 'addedafter:' (s= STRING | s= Q_STRING )
         {
            match( "addedafter:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:544:32:
            // (s= STRING | s= Q_STRING )
            int alt20 = 2;
            int LA20_0 = input.LA( 1 );
            
            if ( ( ( LA20_0 >= '\u0000' && LA20_0 <= '\u001F' )
               || LA20_0 == '!' || ( LA20_0 >= '#' && LA20_0 <= '\'' ) || ( LA20_0 >= '*' && LA20_0 <= '\uFFFF' ) ) )
            {
               alt20 = 1;
            }
            else if ( ( LA20_0 == '\"' ) )
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
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:544:34:
                  // s= STRING
               {
                  int sStart1552 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1552,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:544:45:
                  // s= Q_STRING
               {
                  int sStart1558 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1558,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            differsTimeParam( Tasks.ADDED_DATE, s.getText(), false );
            addRtmToken( OP_ADDED_AFTER, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_ADDED_AFTER"
   
   // $ANTLR start "OP_ADDED_WITHIN"
   public final void mOP_ADDED_WITHIN() throws RecognitionException
   {
      try
      {
         int _type = OP_ADDED_WITHIN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:550:17:
         // ( 'addedwithin:' s= Q_STRING )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:550:19:
         // 'addedwithin:' s= Q_STRING
         {
            match( "addedwithin:" );
            
            int sStart1591 = getCharIndex();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1591,
                                 getCharIndex() - 1 );
            
            inTimeParamRange( Tasks.ADDED_DATE, s.getText(), true );
            addRtmToken( OP_ADDED_WITHIN, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_ADDED_WITHIN"
   
   // $ANTLR start "OP_TIME_ESTIMATE"
   public final void mOP_TIME_ESTIMATE() throws RecognitionException
   {
      try
      {
         int _type = OP_TIME_ESTIMATE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:556:18:
         // ( 'timeestimate:' s= Q_STRING )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:556:20:
         // 'timeestimate:' s= Q_STRING
         {
            match( "timeestimate:" );
            
            int sStart1623 = getCharIndex();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1623,
                                 getCharIndex() - 1 );
            
            result.append( Tasks.ESTIMATE_MILLIS );
            
            final String param = unquotify( s.getText() );
            
            long estimatedMillis = -1;
            final char chPos0 = param.charAt( 0 );
            
            if ( chPos0 == '<' || chPos0 == '>' )
            {
               result.append( " > -1 AND " ).append( Tasks.ESTIMATE_MILLIS );
               result.append( chPos0 );
               estimatedMillis = RtmDateTimeParsing.parseEstimated( param.substring( 1 ) );
            }
            else
            {
               result.append( "=" );
               estimatedMillis = RtmDateTimeParsing.parseEstimated( param );
            }
            
            // Parser error
            if ( estimatedMillis == -1 )
               error = true;
            else
               result.append( estimatedMillis );
            
            addRtmToken( OP_TIME_ESTIMATE, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_TIME_ESTIMATE"
   
   // $ANTLR start "OP_POSTPONED"
   public final void mOP_POSTPONED() throws RecognitionException
   {
      try
      {
         int _type = OP_POSTPONED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:586:14:
         // ( 'postponed:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:586:16:
         // 'postponed:' (s= STRING | s= Q_STRING )
         {
            match( "postponed:" );
            
            result.append( Tasks.POSTPONED );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:590:16:
            // (s= STRING | s= Q_STRING )
            int alt21 = 2;
            int LA21_0 = input.LA( 1 );
            
            if ( ( ( LA21_0 >= '\u0000' && LA21_0 <= '\u001F' )
               || LA21_0 == '!' || ( LA21_0 >= '#' && LA21_0 <= '\'' ) || ( LA21_0 >= '*' && LA21_0 <= '\uFFFF' ) ) )
            {
               alt21 = 1;
            }
            else if ( ( LA21_0 == '\"' ) )
            {
               alt21 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     21,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt21 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:591:18:
                  // s= STRING
               {
                  int sStart1707 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1707,
                                       getCharIndex() - 1 );
                  
                  equalsIntParam( s.getText() );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:596:18:
                  // s= Q_STRING
               {
                  int sStart1766 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1766,
                                       getCharIndex() - 1 );
                  
                  result.append( unquotify( s.getText() ) );
                  
               }
                  break;
               
            }
            
            addRtmToken( OP_POSTPONED, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_POSTPONED"
   
   // $ANTLR start "OP_IS_SHARED"
   public final void mOP_IS_SHARED() throws RecognitionException
   {
      try
      {
         int _type = OP_IS_SHARED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:605:14:
         // ( 'isshared:' ( TRUE | FALSE ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:605:16:
         // 'isshared:' ( TRUE | FALSE )
         {
            match( "isshared:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:606:16:
            // ( TRUE | FALSE )
            int alt22 = 2;
            int LA22_0 = input.LA( 1 );
            
            if ( ( LA22_0 == 't' ) )
            {
               alt22 = 1;
            }
            else if ( ( LA22_0 == 'f' ) )
            {
               alt22 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     22,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt22 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:607:19:
                  // TRUE
               {
                  mTRUE();
                  
                  result.append( Tasks.PARTICIPANT_IDS + " IS NOT NULL" );
                  addRtmToken( OP_IS_SHARED, TRUE_LIT );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:613:19:
                  // FALSE
               {
                  mFALSE();
                  
                  result.append( Tasks.PARTICIPANT_IDS + " IS NULL" );
                  addRtmToken( OP_IS_SHARED, FALSE_LIT );
                  
               }
                  break;
               
            }
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_IS_SHARED"
   
   // $ANTLR start "OP_SHARED_WITH"
   public final void mOP_SHARED_WITH() throws RecognitionException
   {
      try
      {
         int _type = OP_SHARED_WITH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:620:16:
         // ( 'sharedwith:' (s= STRING | s= Q_STRING ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:620:18:
         // 'sharedwith:' (s= STRING | s= Q_STRING )
         {
            match( "sharedwith:" );
            
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:620:32:
            // (s= STRING | s= Q_STRING )
            int alt23 = 2;
            int LA23_0 = input.LA( 1 );
            
            if ( ( ( LA23_0 >= '\u0000' && LA23_0 <= '\u001F' )
               || LA23_0 == '!' || ( LA23_0 >= '#' && LA23_0 <= '\'' ) || ( LA23_0 >= '*' && LA23_0 <= '\uFFFF' ) ) )
            {
               alt23 = 1;
            }
            else if ( ( LA23_0 == '\"' ) )
            {
               alt23 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     23,
                                                                     0,
                                                                     input );
               
               throw nvae;
            }
            switch ( alt23 )
            {
               case 1:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:620:34:
                  // s= STRING
               {
                  int sStart1975 = getCharIndex();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1975,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               case 2:
                  // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:620:45:
                  // s= Q_STRING
               {
                  int sStart1981 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1981,
                                       getCharIndex() - 1 );
                  
               }
                  break;
               
            }
            
            result.append( Tasks.PARTICIPANT_FULLNAMES );
            containsStringParam( s.getText() );
            
            result.append( " OR " );
            result.append( Tasks.PARTICIPANT_USERNAMES );
            containsStringParam( s.getText() );
            
            addRtmToken( OP_SHARED_WITH, s.getText() );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OP_SHARED_WITH"
   
   // $ANTLR start "COMPLETED"
   public final void mCOMPLETED() throws RecognitionException
   {
      try
      {
         int _type = COMPLETED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:643:13:
         // ( 'completed' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:643:15:
         // 'completed'
         {
            match( "completed" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "COMPLETED"
   
   // $ANTLR start "INCOMPLETE"
   public final void mINCOMPLETE() throws RecognitionException
   {
      try
      {
         int _type = INCOMPLETE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:645:13:
         // ( 'incomplete' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:645:15:
         // 'incomplete'
         {
            match( "incomplete" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "INCOMPLETE"
   
   // $ANTLR start "TRUE"
   public final void mTRUE() throws RecognitionException
   {
      try
      {
         int _type = TRUE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:647:13:
         // ( 'true' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:647:15:
         // 'true'
         {
            match( "true" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "TRUE"
   
   // $ANTLR start "FALSE"
   public final void mFALSE() throws RecognitionException
   {
      try
      {
         int _type = FALSE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:649:13:
         // ( 'false' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:649:15:
         // 'false'
         {
            match( "false" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "FALSE"
   
   // $ANTLR start "L_PARENTH"
   public final void mL_PARENTH() throws RecognitionException
   {
      try
      {
         int _type = L_PARENTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:651:13:
         // ( '(' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:651:15:
         // '('
         {
            match( '(' );
            
            result.append( "( " );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "L_PARENTH"
   
   // $ANTLR start "R_PARENTH"
   public final void mR_PARENTH() throws RecognitionException
   {
      try
      {
         int _type = R_PARENTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:656:13:
         // ( ')' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:656:15:
         // ')'
         {
            match( ')' );
            
            result.append( " )" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "R_PARENTH"
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException
   {
      try
      {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:661:13:
         // ( 'and' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:661:15:
         // 'and'
         {
            match( "and" );
            
            result.append( " AND " );
            op_not = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "AND"
   
   // $ANTLR start "OR"
   public final void mOR() throws RecognitionException
   {
      try
      {
         int _type = OR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:667:13:
         // ( 'or' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:667:15:
         // 'or'
         {
            match( "or" );
            
            result.append( " OR " );
            op_not = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "OR"
   
   // $ANTLR start "NOT"
   public final void mNOT() throws RecognitionException
   {
      try
      {
         int _type = NOT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:673:13:
         // ( 'not' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:673:15:
         // 'not'
         {
            match( "not" );
            
            result.append( " NOT " );
            op_not = true;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "NOT"
   
   // $ANTLR start "WS"
   public final void mWS() throws RecognitionException
   {
      try
      {
         int _type = WS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:679:13:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:679:17:
         // ( ' ' | '\\t' | '\\r' | '\\n' )
         {
            if ( ( input.LA( 1 ) >= '\t' && input.LA( 1 ) <= '\n' )
               || input.LA( 1 ) == '\r' || input.LA( 1 ) == ' ' )
            {
               input.consume();
               
            }
            else
            {
               MismatchedSetException mse = new MismatchedSetException( null,
                                                                        input );
               recover( mse );
               throw mse;
            }
            
            skip();
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
      }
   }
   


   // $ANTLR end "WS"
   
   // $ANTLR start "Q_STRING"
   public final void mQ_STRING() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:686:13:
         // ( '\"' (~ ( '\"' ) )* '\"' )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:686:15:
         // '\"' (~ ( '\"' ) )* '\"'
         {
            match( '\"' );
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:686:19:
            // (~ ( '\"' ) )*
            loop24: do
            {
               int alt24 = 2;
               int LA24_0 = input.LA( 1 );
               
               if ( ( ( LA24_0 >= '\u0000' && LA24_0 <= '!' ) || ( LA24_0 >= '#' && LA24_0 <= '\uFFFF' ) ) )
               {
                  alt24 = 1;
               }
               
               switch ( alt24 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:686:19:
                     // ~ ( '\"' )
                  {
                     if ( ( input.LA( 1 ) >= '\u0000' && input.LA( 1 ) <= '!' )
                        || ( input.LA( 1 ) >= '#' && input.LA( 1 ) <= '\uFFFF' ) )
                     {
                        input.consume();
                        
                     }
                     else
                     {
                        MismatchedSetException mse = new MismatchedSetException( null,
                                                                                 input );
                        recover( mse );
                        throw mse;
                     }
                     
                  }
                     break;
                  
                  default :
                     break loop24;
               }
            }
            while ( true );
            
            match( '\"' );
            
         }
         
      }
      finally
      {
      }
   }
   


   // $ANTLR end "Q_STRING"
   
   // $ANTLR start "STRING"
   public final void mSTRING() throws RecognitionException
   {
      try
      {
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:689:13:
         // ( (~ ( '\"' | ' ' | '(' | ')' ) )+ )
         // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:689:15:
         // (~ ( '\"' | ' ' | '(' | ')' ) )+
         {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:689:15:
            // (~ ( '\"' | ' ' | '(' | ')' ) )+
            int cnt25 = 0;
            loop25: do
            {
               int alt25 = 2;
               int LA25_0 = input.LA( 1 );
               
               if ( ( ( LA25_0 >= '\u0000' && LA25_0 <= '\u001F' )
                  || LA25_0 == '!' || ( LA25_0 >= '#' && LA25_0 <= '\'' ) || ( LA25_0 >= '*' && LA25_0 <= '\uFFFF' ) ) )
               {
                  alt25 = 1;
               }
               
               switch ( alt25 )
               {
                  case 1:
                     // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:689:15:
                     // ~ ( '\"' | ' ' | '(' | ')' )
                  {
                     if ( ( input.LA( 1 ) >= '\u0000' && input.LA( 1 ) <= '\u001F' )
                        || input.LA( 1 ) == '!'
                        || ( input.LA( 1 ) >= '#' && input.LA( 1 ) <= '\'' )
                        || ( input.LA( 1 ) >= '*' && input.LA( 1 ) <= '\uFFFF' ) )
                     {
                        input.consume();
                        
                     }
                     else
                     {
                        MismatchedSetException mse = new MismatchedSetException( null,
                                                                                 input );
                        recover( mse );
                        throw mse;
                     }
                     
                  }
                     break;
                  
                  default :
                     if ( cnt25 >= 1 )
                        break loop25;
                     EarlyExitException eee = new EarlyExitException( 25, input );
                     throw eee;
               }
               cnt25++;
            }
            while ( true );
            
         }
         
      }
      finally
      {
      }
   }
   


   // $ANTLR end "STRING"
   
   public void mTokens() throws RecognitionException
   {
      // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:8: (
      // OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED |
      // OP_IS_REPEATING | OP_NAME | OP_NOTE_CONTAINS | OP_HAS_NOTES | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE |
      // OP_DUE_WITHIN | OP_COMPLETED | OP_COMPLETED_BEFORE | OP_COMPLETED_AFTER | OP_COMPLETED_WITHIN | OP_ADDED |
      // OP_ADDED_BEFORE | OP_ADDED_AFTER | OP_ADDED_WITHIN | OP_TIME_ESTIMATE | OP_POSTPONED | OP_IS_SHARED |
      // OP_SHARED_WITH | COMPLETED | INCOMPLETE | TRUE | FALSE | L_PARENTH | R_PARENTH | AND | OR | NOT | WS )
      int alt26 = 38;
      alt26 = dfa26.predict( input );
      switch ( alt26 )
      {
         case 1:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:10:
            // OP_LIST
         {
            mOP_LIST();
            
         }
            break;
         case 2:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:18:
            // OP_PRIORITY
         {
            mOP_PRIORITY();
            
         }
            break;
         case 3:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:30:
            // OP_STATUS
         {
            mOP_STATUS();
            
         }
            break;
         case 4:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:40:
            // OP_TAG
         {
            mOP_TAG();
            
         }
            break;
         case 5:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:47:
            // OP_TAG_CONTAINS
         {
            mOP_TAG_CONTAINS();
            
         }
            break;
         case 6:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:63:
            // OP_IS_TAGGED
         {
            mOP_IS_TAGGED();
            
         }
            break;
         case 7:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:76:
            // OP_LOCATION
         {
            mOP_LOCATION();
            
         }
            break;
         case 8:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:88:
            // OP_ISLOCATED
         {
            mOP_ISLOCATED();
            
         }
            break;
         case 9:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:101:
            // OP_IS_REPEATING
         {
            mOP_IS_REPEATING();
            
         }
            break;
         case 10:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:117:
            // OP_NAME
         {
            mOP_NAME();
            
         }
            break;
         case 11:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:125:
            // OP_NOTE_CONTAINS
         {
            mOP_NOTE_CONTAINS();
            
         }
            break;
         case 12:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:142:
            // OP_HAS_NOTES
         {
            mOP_HAS_NOTES();
            
         }
            break;
         case 13:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:155:
            // OP_DUE
         {
            mOP_DUE();
            
         }
            break;
         case 14:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:162:
            // OP_DUE_AFTER
         {
            mOP_DUE_AFTER();
            
         }
            break;
         case 15:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:175:
            // OP_DUE_BEFORE
         {
            mOP_DUE_BEFORE();
            
         }
            break;
         case 16:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:189:
            // OP_DUE_WITHIN
         {
            mOP_DUE_WITHIN();
            
         }
            break;
         case 17:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:203:
            // OP_COMPLETED
         {
            mOP_COMPLETED();
            
         }
            break;
         case 18:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:216:
            // OP_COMPLETED_BEFORE
         {
            mOP_COMPLETED_BEFORE();
            
         }
            break;
         case 19:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:236:
            // OP_COMPLETED_AFTER
         {
            mOP_COMPLETED_AFTER();
            
         }
            break;
         case 20:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:255:
            // OP_COMPLETED_WITHIN
         {
            mOP_COMPLETED_WITHIN();
            
         }
            break;
         case 21:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:275:
            // OP_ADDED
         {
            mOP_ADDED();
            
         }
            break;
         case 22:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:284:
            // OP_ADDED_BEFORE
         {
            mOP_ADDED_BEFORE();
            
         }
            break;
         case 23:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:300:
            // OP_ADDED_AFTER
         {
            mOP_ADDED_AFTER();
            
         }
            break;
         case 24:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:315:
            // OP_ADDED_WITHIN
         {
            mOP_ADDED_WITHIN();
            
         }
            break;
         case 25:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:331:
            // OP_TIME_ESTIMATE
         {
            mOP_TIME_ESTIMATE();
            
         }
            break;
         case 26:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:348:
            // OP_POSTPONED
         {
            mOP_POSTPONED();
            
         }
            break;
         case 27:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:361:
            // OP_IS_SHARED
         {
            mOP_IS_SHARED();
            
         }
            break;
         case 28:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:374:
            // OP_SHARED_WITH
         {
            mOP_SHARED_WITH();
            
         }
            break;
         case 29:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:389:
            // COMPLETED
         {
            mCOMPLETED();
            
         }
            break;
         case 30:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:399:
            // INCOMPLETE
         {
            mINCOMPLETE();
            
         }
            break;
         case 31:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:410:
            // TRUE
         {
            mTRUE();
            
         }
            break;
         case 32:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:415:
            // FALSE
         {
            mFALSE();
            
         }
            break;
         case 33:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:421:
            // L_PARENTH
         {
            mL_PARENTH();
            
         }
            break;
         case 34:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:431:
            // R_PARENTH
         {
            mR_PARENTH();
            
         }
            break;
         case 35:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:441:
            // AND
         {
            mAND();
            
         }
            break;
         case 36:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:445:
            // OR
         {
            mOR();
            
         }
            break;
         case 37:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:448:
            // NOT
         {
            mNOT();
            
         }
            break;
         case 38:
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:452:
            // WS
         {
            mWS();
            
         }
            break;
         
      }
      
   }
   
   protected DFA26 dfa26 = new DFA26( this );
   
   static final String DFA26_eotS = "\46\uffff\1\55\26\uffff\1\102\5\uffff";
   
   static final String DFA26_eofS = "\103\uffff";
   
   static final String DFA26_minS = "\1\11\1\151\1\157\1\150\1\141\1\156\1\141\1\uffff\1\165\1\157\1"
      + "\144\13\uffff\1\147\2\uffff\1\154\2\uffff\1\164\1\145\1\155\1\144"
      + "\1\uffff\1\72\4\uffff\1\145\1\72\1\160\1\145\10\uffff\1\154\1\144"
      + "\1\145\1\72\1\164\4\uffff\1\145\1\144\1\72\5\uffff";
   
   static final String DFA26_maxS = "\1\164\1\157\1\162\1\164\1\162\1\163\1\157\1\uffff\1\165\1\157"
      + "\1\156\13\uffff\1\147\2\uffff\1\164\2\uffff\1\164\1\145\1\155\1"
      + "\144\1\uffff\1\143\4\uffff\1\145\1\167\1\160\1\145\10\uffff\1\154"
      + "\1\144\1\145\1\167\1\164\4\uffff\1\145\1\144\1\167\5\uffff";
   
   static final String DFA26_acceptS = "\7\uffff\1\14\3\uffff\1\40\1\41\1\42\1\44\1\46\1\1\1\7\1\2\1\32"
      + "\1\3\1\34\1\uffff\1\31\1\37\1\uffff\1\36\1\12\4\uffff\1\43\1\uffff"
      + "\1\6\1\10\1\11\1\33\4\uffff\1\4\1\5\1\13\1\45\1\15\1\16\1\17\1\20"
      + "\5\uffff\1\25\1\26\1\27\1\30\3\uffff\1\21\1\22\1\23\1\24\1\35";
   
   static final String DFA26_specialS = "\103\uffff}>";
   
   static final String[] DFA26_transitionS =
   {
    "\2\17\2\uffff\1\17\22\uffff\1\17\7\uffff\1\14\1\15\67\uffff"
       + "\1\12\1\uffff\1\11\1\10\1\uffff\1\13\1\uffff\1\7\1\5\2\uffff"
       + "\1\1\1\uffff\1\6\1\16\1\2\2\uffff\1\3\1\4", "\1\20\5\uffff\1\21",
    "\1\23\2\uffff\1\22", "\1\25\13\uffff\1\24",
    "\1\26\7\uffff\1\27\10\uffff\1\30", "\1\32\4\uffff\1\31",
    "\1\33\15\uffff\1\34", "", "\1\35", "\1\36", "\1\37\11\uffff\1\40", "", "",
    "", "", "", "", "", "", "", "", "", "\1\41", "", "",
    "\1\43\5\uffff\1\44\1\45\1\42", "", "", "\1\46", "\1\47", "\1\50", "\1\51",
    "", "\1\52\50\uffff\1\53", "", "", "", "", "\1\54",
    "\1\56\46\uffff\1\57\1\60\24\uffff\1\61", "\1\62", "\1\63", "", "", "", "",
    "", "", "", "", "\1\64", "\1\65", "\1\66",
    "\1\67\46\uffff\1\71\1\70\24\uffff\1\72", "\1\73", "", "", "", "", "\1\74",
    "\1\75", "\1\76\46\uffff\1\100\1\77\24\uffff\1\101", "", "", "", "", "" };
   
   static final short[] DFA26_eot = DFA.unpackEncodedString( DFA26_eotS );
   
   static final short[] DFA26_eof = DFA.unpackEncodedString( DFA26_eofS );
   
   static final char[] DFA26_min = DFA.unpackEncodedStringToUnsignedChars( DFA26_minS );
   
   static final char[] DFA26_max = DFA.unpackEncodedStringToUnsignedChars( DFA26_maxS );
   
   static final short[] DFA26_accept = DFA.unpackEncodedString( DFA26_acceptS );
   
   static final short[] DFA26_special = DFA.unpackEncodedString( DFA26_specialS );
   
   static final short[][] DFA26_transition;
   
   static
   {
      int numStates = DFA26_transitionS.length;
      DFA26_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA26_transition[ i ] = DFA.unpackEncodedString( DFA26_transitionS[ i ] );
      }
   }
   
   
   class DFA26 extends DFA
   {
      
      public DFA26( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 26;
         this.eot = DFA26_eot;
         this.eof = DFA26_eof;
         this.min = DFA26_min;
         this.max = DFA26_max;
         this.accept = DFA26_accept;
         this.special = DFA26_special;
         this.transition = DFA26_transition;
      }
      


      public String getDescription()
      {
         return "1:1: Tokens : ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED | OP_IS_REPEATING | OP_NAME | OP_NOTE_CONTAINS | OP_HAS_NOTES | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE | OP_DUE_WITHIN | OP_COMPLETED | OP_COMPLETED_BEFORE | OP_COMPLETED_AFTER | OP_COMPLETED_WITHIN | OP_ADDED | OP_ADDED_BEFORE | OP_ADDED_AFTER | OP_ADDED_WITHIN | OP_TIME_ESTIMATE | OP_POSTPONED | OP_IS_SHARED | OP_SHARED_WITH | COMPLETED | INCOMPLETE | TRUE | FALSE | L_PARENTH | R_PARENTH | AND | OR | NOT | WS );";
      }
   }
   
}
