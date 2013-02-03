// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g 2013-02-03 19:03:43

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

import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateWithinReturn;
import dev.drsoran.moloko.util.MolokoCalendar;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
import dev.drsoran.provider.Rtm.Locations;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.Tasks;


@SuppressWarnings(
{ "all", "warnings", "unchecked" } )
public class RtmSmartFilterLexer extends Lexer
{
   public static final int EOF = -1;
   
   public static final int AND = 4;
   
   public static final int COMPLETED = 5;
   
   public static final int FALSE = 6;
   
   public static final int INCOMPLETE = 7;
   
   public static final int L_PARENTH = 8;
   
   public static final int NOT = 9;
   
   public static final int OP_ADDED = 10;
   
   public static final int OP_ADDED_AFTER = 11;
   
   public static final int OP_ADDED_BEFORE = 12;
   
   public static final int OP_ADDED_WITHIN = 13;
   
   public static final int OP_COMPLETED = 14;
   
   public static final int OP_COMPLETED_AFTER = 15;
   
   public static final int OP_COMPLETED_BEFORE = 16;
   
   public static final int OP_COMPLETED_WITHIN = 17;
   
   public static final int OP_DUE = 18;
   
   public static final int OP_DUE_AFTER = 19;
   
   public static final int OP_DUE_BEFORE = 20;
   
   public static final int OP_DUE_WITHIN = 21;
   
   public static final int OP_HAS_NOTES = 22;
   
   public static final int OP_ISLOCATED = 23;
   
   public static final int OP_IS_REPEATING = 24;
   
   public static final int OP_IS_SHARED = 25;
   
   public static final int OP_IS_TAGGED = 26;
   
   public static final int OP_LIST = 27;
   
   public static final int OP_LOCATION = 28;
   
   public static final int OP_NAME = 29;
   
   public static final int OP_NOTE_CONTAINS = 30;
   
   public static final int OP_POSTPONED = 31;
   
   public static final int OP_PRIORITY = 32;
   
   public static final int OP_SHARED_WITH = 33;
   
   public static final int OP_STATUS = 34;
   
   public static final int OP_TAG = 35;
   
   public static final int OP_TAG_CONTAINS = 36;
   
   public static final int OP_TIME_ESTIMATE = 37;
   
   public static final int OR = 38;
   
   public static final int PRIO_HIGH = 39;
   
   public static final int PRIO_LOW = 40;
   
   public static final int PRIO_MED = 41;
   
   public static final int PRIO_NONE = 42;
   
   public static final int Q_STRING = 43;
   
   public static final int R_PARENTH = 44;
   
   public static final int STRING = 45;
   
   public static final int TRUE = 46;
   
   public static final int WS = 47;
   
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
   
   public final static String PRIO_HIGH_LIT = "1";
   
   public final static String PRIO_MED_LIT = "2";
   
   public final static String PRIO_LOW_LIT = "3";
   
   public final static String PRIO_NONE_LIT = "n";
   
   public final static String L_PARENTH_LIT = "(";
   
   public final static String R_PARENTH_LIT = ")";
   
   public final static String AND_LIT = "and";
   
   public final static String OR_LIT = "or";
   
   public final static String NOT_LIT = "not";
   
   // END TOKEN LITERALS
   
   private final static String DEFAULT_OPERATOR = AND_LIT;
   
   // STATUS VARIABLES
   
   private final StringBuilder result = new StringBuilder();
   
   private boolean hasStatusCompletedOp = false;
   
   private boolean error = false;
   
   private boolean lexedOperator = false;
   
   private boolean opNot = false;
   
   private ArrayList< RtmSmartFilterToken > tokens;
   
   private int numTokens;
   
   
   
   @Override
   public void reset()
   {
      super.reset();
      
      result.setLength( 0 );
      hasStatusCompletedOp = false;
      lexedOperator = false;
      opNot = false;
      error = false;
      tokens = null;
      numTokens = 0;
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
      return "\"" + input + "\"";
   }
   
   
   
   private static final String firstCharOf( String input )
   {
      return input.length() > 0 ? input.substring( 0, 1 ) : "";
   }
   
   
   
   private void likeStringParam( String param )
   {
      result.append( " like '" );
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
      try
      {
         final int val = Integer.parseInt( unquotify( param ) );
         
         result.append( " = " );
         result.append( val );
      }
      catch ( NumberFormatException e )
      {
         error = true;
      }
   }
   
   
   
   private void equalsTimeParam( String column, String param )
   {
      final MolokoCalendar cal = RtmDateTimeParsing.parseDateTimeSpec( unquotify( param ) );
      
      if ( cal != null )
      {
         result.append( "(" );
         result.append( column );
         
         // Check if we have 'NEVER'
         if ( !cal.hasDate() )
         {
            result.append( " IS NULL" );
         }
         
         // Check if we have an explicit time
         // given.
         else if ( cal.hasTime() )
         {
            result.append( " == " );
            result.append( cal.getTimeInMillis() );
         }
         else
         {
            result.append( " >= " );
            result.append( cal.getTimeInMillis() );
            result.append( " AND " );
            
            cal.add( Calendar.DAY_OF_YEAR, 1 );
            
            result.append( column );
            result.append( " < " );
            result.append( cal.getTimeInMillis() );
         }
         
         result.append( ")" );
      }
      else
         // Parser error
         error = true;
   }
   
   
   
   private void differsTimeParam( String column, String param, boolean before )
   {
      final String unquotParam = unquotify( param );
      final MolokoCalendar cal = RtmDateTimeParsing.parseDateTimeSpec( unquotParam );
      
      if ( cal != null )
      {
         result.append( column );
         
         // Check if we have 'NEVER'
         if ( !cal.hasDate() )
         {
            result.append( " IS NOT NULL" );
         }
         else
         {
            result.append( ( before ) ? " < " : " > " );
            result.append( cal.getTimeInMillis() );
         }
      }
      
      // If simple time parsing failed, try parse date within
      else
      {
         final ParseDateWithinReturn dateWithinReturn = RtmDateTimeParsing.parseDateWithin( unquotParam,
                                                                                            before );
         
         result.append( column );
         
         if ( dateWithinReturn != null )
         {
            // Check if we have 'NEVER'
            if ( !dateWithinReturn.endEpoch.hasDate() )
            {
               result.append( " IS NOT NULL" );
            }
            
            else
            {
               if ( before )
               {
                  result.append( " < " );
               }
               else
               {
                  result.append( " > " );
               }
               
               result.append( dateWithinReturn.endEpoch.getTimeInMillis() );
            }
         }
         else
         {
            // Parser error
            error = true;
         }
      }
   }
   
   
   
   private void inTimeParamRange( String column, String param, boolean past )
   {
      final ParseDateWithinReturn range = RtmDateTimeParsing.parseDateWithin( unquotify( param ),
                                                                              past );
      
      if ( range != null )
      {
         result.append( "(" );
         result.append( column );
         result.append( " >= " );
         result.append( !past ? range.startEpoch.getTimeInMillis()
                             : range.endEpoch.getTimeInMillis() );
         result.append( " AND " );
         result.append( column );
         result.append( " < " );
         result.append( !past ? range.endEpoch.getTimeInMillis()
                             : range.startEpoch.getTimeInMillis() );
         result.append( ")" );
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
      ++numTokens;
      
      if ( tokens != null )
      {
         tokens.add( new RtmSmartFilterToken( type, unquotify( param ), opNot ) );
      }
   }
   
   
   
   private final void ensureOperator()
   {
      // We do not insert the default operatorfor the first, lexed token
      if ( !lexedOperator && numTokens > 0 )
      {
         result.append( " " ).append( DEFAULT_OPERATOR ).append( " " );
         
         lexedOperator = true;
      }
   }
   
   
   
   // delegates
   // delegators
   public Lexer[] getDelegates()
   {
      return new Lexer[] {};
   }
   
   
   
   public RtmSmartFilterLexer()
   {
   }
   
   
   
   public RtmSmartFilterLexer( CharStream input )
   {
      this( input, new RecognizerSharedState() );
   }
   
   
   
   public RtmSmartFilterLexer( CharStream input, RecognizerSharedState state )
   {
      super( input, state );
   }
   
   
   
   public String getGrammarFileName()
   {
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g";
   }
   
   
   
   // $ANTLR start "OP_LIST"
   public final void mOP_LIST() throws RecognitionException
   {
      try
      {
         int _type = OP_LIST;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:405:13:
         // ( 'list:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:405:16:
         // 'list:' (s= STRING |s= Q_STRING )
         {
            match( "list:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:405:24:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:405:26:
               // s= STRING
               {
                  int sStart53 = getCharIndex();
                  int sStartLine53 = getLine();
                  int sStartCharPos53 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart53,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine53 );
                  s.setCharPositionInLine( sStartCharPos53 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:405:37:
               // s= Q_STRING
               {
                  int sStart59 = getCharIndex();
                  int sStartLine59 = getLine();
                  int sStartCharPos59 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart59,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine59 );
                  s.setCharPositionInLine( sStartCharPos59 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            result.append( Tasks.LIST_NAME );
            likeStringParam( s.getText() );
            
            addRtmToken( OP_LIST, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         CommonToken p = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:416:13:
         // ( 'priority:' (p= PRIO_HIGH |p= PRIO_MED |p= PRIO_LOW |p= PRIO_NONE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:416:16:
         // 'priority:' (p= PRIO_HIGH |p= PRIO_MED |p= PRIO_LOW |p= PRIO_NONE )
         {
            match( "priority:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:416:28:
            // (p= PRIO_HIGH |p= PRIO_MED |p= PRIO_LOW |p= PRIO_NONE )
            int alt2 = 4;
            switch ( input.LA( 1 ) )
            {
               case '1':
               {
                  alt2 = 1;
               }
                  break;
               case '2':
               {
                  alt2 = 2;
               }
                  break;
               case '3':
               {
                  alt2 = 3;
               }
                  break;
               case 'N':
               case 'n':
               {
                  alt2 = 4;
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:416:30:
               // p= PRIO_HIGH
               {
                  int pStart93 = getCharIndex();
                  int pStartLine93 = getLine();
                  int pStartCharPos93 = getCharPositionInLine();
                  mPRIO_HIGH();
                  p = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       pStart93,
                                       getCharIndex() - 1 );
                  p.setLine( pStartLine93 );
                  p.setCharPositionInLine( pStartCharPos93 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:416:44:
               // p= PRIO_MED
               {
                  int pStart99 = getCharIndex();
                  int pStartLine99 = getLine();
                  int pStartCharPos99 = getCharPositionInLine();
                  mPRIO_MED();
                  p = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       pStart99,
                                       getCharIndex() - 1 );
                  p.setLine( pStartLine99 );
                  p.setCharPositionInLine( pStartCharPos99 );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:416:57:
               // p= PRIO_LOW
               {
                  int pStart105 = getCharIndex();
                  int pStartLine105 = getLine();
                  int pStartCharPos105 = getCharPositionInLine();
                  mPRIO_LOW();
                  p = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       pStart105,
                                       getCharIndex() - 1 );
                  p.setLine( pStartLine105 );
                  p.setCharPositionInLine( pStartCharPos105 );
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:416:70:
               // p= PRIO_NONE
               {
                  int pStart111 = getCharIndex();
                  int pStartLine111 = getLine();
                  int pStartCharPos111 = getCharPositionInLine();
                  mPRIO_NONE();
                  p = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       pStart111,
                                       getCharIndex() - 1 );
                  p.setLine( pStartLine111 );
                  p.setCharPositionInLine( pStartCharPos111 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            result.append( Tasks.PRIORITY );
            
            likeStringParam( p.getText() );
            
            addRtmToken( OP_PRIORITY, p.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:427:13:
         // ( 'status:' ( COMPLETED | INCOMPLETE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:427:16:
         // 'status:' ( COMPLETED | INCOMPLETE )
         {
            match( "status:" );
            
            ensureOperator();
            result.append( Tasks.COMPLETED_DATE );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:432:16:
            // ( COMPLETED | INCOMPLETE )
            int alt3 = 2;
            int LA3_0 = input.LA( 1 );
            
            if ( ( LA3_0 == 'c' ) )
            {
               alt3 = 1;
            }
            else if ( ( LA3_0 == 'i' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:433:19:
               // COMPLETED
               {
                  mCOMPLETED();
                  
                  result.append( " IS NOT NULL" );
                  hasStatusCompletedOp = true;
                  addRtmToken( OP_STATUS, COMPLETED_LIT );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:440:19:
               // INCOMPLETE
               {
                  mINCOMPLETE();
                  
                  result.append( " IS NULL" );
                  addRtmToken( OP_STATUS, INCOMPLETE_LIT );
                  
               }
                  break;
            
            }
            
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:450:13:
         // ( 'tag:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:450:15:
         // 'tag:' (s= STRING |s= Q_STRING )
         {
            match( "tag:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:450:22:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:450:24:
               // s= STRING
               {
                  int sStart328 = getCharIndex();
                  int sStartLine328 = getLine();
                  int sStartCharPos328 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart328,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine328 );
                  s.setCharPositionInLine( sStartCharPos328 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:450:35:
               // s= Q_STRING
               {
                  int sStart334 = getCharIndex();
                  int sStartLine334 = getLine();
                  int sStartCharPos334 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart334,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine334 );
                  s.setCharPositionInLine( sStartCharPos334 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            final String unqString = unquotify( s.getText() );
            
            result.append( "(" );
            result.append( Tasks.TAGS )
            // Exact match if only 1 tag
                  .append( " = '" )
                  .append( unqString )
                  .append( "' OR " )
                  // match for the case tag, (prefix)
                  .append( Tasks.TAGS )
                  .append( " like '" )
                  .append( unqString )
                  .append( Tags.TAGS_SEPARATOR )
                  .append( "%' OR " )
                  // match for the case ,tag, (infix)
                  .append( Tasks.TAGS )
                  .append( " like '%" )
                  .append( Tags.TAGS_SEPARATOR )
                  .append( unqString )
                  .append( Tags.TAGS_SEPARATOR )
                  .append( "%' OR " )
                  // match for the case ,tag (suffix)
                  .append( Tasks.TAGS )
                  .append( " like '%" )
                  .append( Tags.TAGS_SEPARATOR )
                  .append( unqString )
                  .append( "'" );
            result.append( ")" );
            
            addRtmToken( OP_TAG, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:487:17:
         // ( 'tagcontains:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:487:19:
         // 'tagcontains:' (s= STRING |s= Q_STRING )
         {
            match( "tagcontains:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:487:34:
            // (s= STRING |s= Q_STRING )
            int alt5 = 2;
            int LA5_0 = input.LA( 1 );
            
            if ( ( ( LA5_0 >= '\u0000' && LA5_0 <= '\u001F' ) || LA5_0 == '!'
               || ( LA5_0 >= '#' && LA5_0 <= '\'' ) || ( LA5_0 >= '*' && LA5_0 <= '\uFFFF' ) ) )
            {
               alt5 = 1;
            }
            else if ( ( LA5_0 == '\"' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:487:36:
               // s= STRING
               {
                  int sStart366 = getCharIndex();
                  int sStartLine366 = getLine();
                  int sStartCharPos366 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart366,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine366 );
                  s.setCharPositionInLine( sStartCharPos366 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:487:47:
               // s= Q_STRING
               {
                  int sStart372 = getCharIndex();
                  int sStartLine372 = getLine();
                  int sStartCharPos372 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart372,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine372 );
                  s.setCharPositionInLine( sStartCharPos372 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            result.append( Tasks.TAGS );
            containsStringParam( s.getText() );
            
            addRtmToken( OP_TAG_CONTAINS, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:498:17:
         // ( 'istagged:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:498:19:
         // 'istagged:' ( TRUE | FALSE )
         {
            match( "istagged:" );
            
            ensureOperator();
            result.append( Tasks.TAGS );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:503:19:
            // ( TRUE | FALSE )
            int alt6 = 2;
            int LA6_0 = input.LA( 1 );
            
            if ( ( LA6_0 == 't' ) )
            {
               alt6 = 1;
            }
            else if ( ( LA6_0 == 'f' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:504:22:
               // TRUE
               {
                  mTRUE();
                  
                  result.append( " IS NOT NULL" );
                  
                  addRtmToken( OP_IS_TAGGED, TRUE_LIT );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:511:22:
               // FALSE
               {
                  mFALSE();
                  
                  result.append( " IS NULL" );
                  
                  addRtmToken( OP_IS_TAGGED, FALSE_LIT );
                  
               }
                  break;
            
            }
            
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:522:13:
         // ( 'location:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:522:15:
         // 'location:' (s= STRING |s= Q_STRING )
         {
            match( "location:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:522:27:
            // (s= STRING |s= Q_STRING )
            int alt7 = 2;
            int LA7_0 = input.LA( 1 );
            
            if ( ( ( LA7_0 >= '\u0000' && LA7_0 <= '\u001F' ) || LA7_0 == '!'
               || ( LA7_0 >= '#' && LA7_0 <= '\'' ) || ( LA7_0 >= '*' && LA7_0 <= '\uFFFF' ) ) )
            {
               alt7 = 1;
            }
            else if ( ( LA7_0 == '\"' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:522:29:
               // s= STRING
               {
                  int sStart614 = getCharIndex();
                  int sStartLine614 = getLine();
                  int sStartCharPos614 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart614,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine614 );
                  s.setCharPositionInLine( sStartCharPos614 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:522:40:
               // s= Q_STRING
               {
                  int sStart620 = getCharIndex();
                  int sStartLine620 = getLine();
                  int sStartCharPos620 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart620,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine620 );
                  s.setCharPositionInLine( sStartCharPos620 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            result.append( Tasks.LOCATION_NAME );
            likeStringParam( s.getText() );
            
            addRtmToken( OP_LOCATION, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:534:14:
         // ( 'islocated:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:534:16:
         // 'islocated:' ( TRUE | FALSE )
         {
            match( "islocated:" );
            
            ensureOperator();
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:538:16:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:539:19:
               // TRUE
               {
                  mTRUE();
                  
                  result.append( "(" );
                  result.append( Tasks.LOCATION_ID );
                  // Handle the case that shared tasks have a location
                  // ID but not from our DB.
                  result.append( " IS NOT NULL AND " );
                  result.append( Tasks.LOCATION_ID );
                  result.append( " IN ( SELECT " );
                  result.append( Locations._ID );
                  result.append( " FROM " );
                  result.append( Locations.PATH );
                  result.append( " )" );
                  result.append( ")" );
                  
                  addRtmToken( OP_ISLOCATED, TRUE_LIT );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:557:19:
               // FALSE
               {
                  mFALSE();
                  
                  result.append( Tasks.LOCATION_ID );
                  result.append( " IS NULL" );
                  
                  addRtmToken( OP_ISLOCATED, FALSE_LIT );
                  
               }
                  break;
            
            }
            
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:569:17:
         // ( 'isrepeating:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:569:19:
         // 'isrepeating:' ( TRUE | FALSE )
         {
            match( "isrepeating:" );
            
            ensureOperator();
            result.append( Tasks.RECURRENCE );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:574:19:
            // ( TRUE | FALSE )
            int alt9 = 2;
            int LA9_0 = input.LA( 1 );
            
            if ( ( LA9_0 == 't' ) )
            {
               alt9 = 1;
            }
            else if ( ( LA9_0 == 'f' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:575:22:
               // TRUE
               {
                  mTRUE();
                  
                  result.append( " IS NOT NULL" );
                  addRtmToken( OP_IS_REPEATING, TRUE_LIT );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:581:22:
               // FALSE
               {
                  mFALSE();
                  
                  result.append( " IS NULL" );
                  addRtmToken( OP_IS_REPEATING, FALSE_LIT );
                  
               }
                  break;
            
            }
            
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:591:14:
         // ( 'name:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:591:16:
         // 'name:' (s= STRING |s= Q_STRING )
         {
            match( "name:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:591:24:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:591:26:
               // s= STRING
               {
                  int sStart1039 = getCharIndex();
                  int sStartLine1039 = getLine();
                  int sStartCharPos1039 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1039,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1039 );
                  s.setCharPositionInLine( sStartCharPos1039 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:591:37:
               // s= Q_STRING
               {
                  int sStart1045 = getCharIndex();
                  int sStartLine1045 = getLine();
                  int sStartCharPos1045 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1045,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1045 );
                  s.setCharPositionInLine( sStartCharPos1045 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            result.append( Tasks.TASKSERIES_NAME );
            containsStringParam( s.getText() );
            
            addRtmToken( OP_NAME, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:602:18:
         // ( 'notecontains:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:602:20:
         // 'notecontains:' (s= STRING |s= Q_STRING )
         {
            match( "notecontains:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:602:36:
            // (s= STRING |s= Q_STRING )
            int alt11 = 2;
            int LA11_0 = input.LA( 1 );
            
            if ( ( ( LA11_0 >= '\u0000' && LA11_0 <= '\u001F' )
               || LA11_0 == '!' || ( LA11_0 >= '#' && LA11_0 <= '\'' ) || ( LA11_0 >= '*' && LA11_0 <= '\uFFFF' ) ) )
            {
               alt11 = 1;
            }
            else if ( ( LA11_0 == '\"' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:602:38:
               // s= STRING
               {
                  int sStart1078 = getCharIndex();
                  int sStartLine1078 = getLine();
                  int sStartCharPos1078 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1078,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1078 );
                  s.setCharPositionInLine( sStartCharPos1078 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:602:49:
               // s= Q_STRING
               {
                  int sStart1084 = getCharIndex();
                  int sStartLine1084 = getLine();
                  int sStartCharPos1084 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1084,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1084 );
                  s.setCharPositionInLine( sStartCharPos1084 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            result.append( "(" );
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
            result.append( ")" );
            
            addRtmToken( OP_NOTE_CONTAINS, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:627:14:
         // ( 'hasnotes:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:627:16:
         // 'hasnotes:' ( TRUE | FALSE )
         {
            match( "hasnotes:" );
            
            ensureOperator();
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:631:16:
            // ( TRUE | FALSE )
            int alt12 = 2;
            int LA12_0 = input.LA( 1 );
            
            if ( ( LA12_0 == 't' ) )
            {
               alt12 = 1;
            }
            else if ( ( LA12_0 == 'f' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:632:19:
               // TRUE
               {
                  mTRUE();
                  
                  result.append( Tasks.NOTE_IDS + " NOT NULL" );
                  addRtmToken( OP_HAS_NOTES, TRUE_LIT );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:638:19:
               // FALSE
               {
                  mFALSE();
                  
                  result.append( Tasks.NOTE_IDS + " IS NULL" );
                  addRtmToken( OP_HAS_NOTES, FALSE_LIT );
                  
               }
                  break;
            
            }
            
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:648:13:
         // ( 'due:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:648:16:
         // 'due:' (s= STRING |s= Q_STRING )
         {
            match( "due:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:648:23:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:648:25:
               // s= STRING
               {
                  int sStart1303 = getCharIndex();
                  int sStartLine1303 = getLine();
                  int sStartCharPos1303 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1303,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1303 );
                  s.setCharPositionInLine( sStartCharPos1303 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:648:36:
               // s= Q_STRING
               {
                  int sStart1309 = getCharIndex();
                  int sStartLine1309 = getLine();
                  int sStartCharPos1309 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1309,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1309 );
                  s.setCharPositionInLine( sStartCharPos1309 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            equalsTimeParam( Tasks.DUE_DATE, s.getText() );
            
            addRtmToken( OP_DUE, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:658:14:
         // ( 'dueafter:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:658:16:
         // 'dueafter:' (s= STRING |s= Q_STRING )
         {
            match( "dueafter:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:658:28:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:658:30:
               // s= STRING
               {
                  int sStart1342 = getCharIndex();
                  int sStartLine1342 = getLine();
                  int sStartCharPos1342 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1342,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1342 );
                  s.setCharPositionInLine( sStartCharPos1342 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:658:41:
               // s= Q_STRING
               {
                  int sStart1348 = getCharIndex();
                  int sStartLine1348 = getLine();
                  int sStartCharPos1348 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1348,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1348 );
                  s.setCharPositionInLine( sStartCharPos1348 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            differsTimeParam( Tasks.DUE_DATE, s.getText(), false );
            
            addRtmToken( OP_DUE_AFTER, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:668:15:
         // ( 'duebefore:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:668:17:
         // 'duebefore:' (s= STRING |s= Q_STRING )
         {
            match( "duebefore:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:668:30:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:668:32:
               // s= STRING
               {
                  int sStart1381 = getCharIndex();
                  int sStartLine1381 = getLine();
                  int sStartCharPos1381 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1381,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1381 );
                  s.setCharPositionInLine( sStartCharPos1381 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:668:43:
               // s= Q_STRING
               {
                  int sStart1387 = getCharIndex();
                  int sStartLine1387 = getLine();
                  int sStartCharPos1387 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1387,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1387 );
                  s.setCharPositionInLine( sStartCharPos1387 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            differsTimeParam( Tasks.DUE_DATE, s.getText(), true );
            
            addRtmToken( OP_DUE_BEFORE, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:678:15:
         // ( 'duewithin:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:678:17:
         // 'duewithin:' s= Q_STRING
         {
            match( "duewithin:" );
            
            int sStart1419 = getCharIndex();
            int sStartLine1419 = getLine();
            int sStartCharPos1419 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1419,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1419 );
            s.setCharPositionInLine( sStartCharPos1419 );
            
            ensureOperator();
            
            inTimeParamRange( Tasks.DUE_DATE, s.getText(), false );
            
            addRtmToken( OP_DUE_WITHIN, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:688:14:
         // ( 'completed:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:688:16:
         // 'completed:' (s= STRING |s= Q_STRING )
         {
            match( "completed:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:688:29:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:688:31:
               // s= STRING
               {
                  int sStart1451 = getCharIndex();
                  int sStartLine1451 = getLine();
                  int sStartCharPos1451 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1451,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1451 );
                  s.setCharPositionInLine( sStartCharPos1451 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:688:42:
               // s= Q_STRING
               {
                  int sStart1457 = getCharIndex();
                  int sStartLine1457 = getLine();
                  int sStartCharPos1457 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1457,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1457 );
                  s.setCharPositionInLine( sStartCharPos1457 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            equalsTimeParam( Tasks.COMPLETED_DATE, s.getText() );
            hasStatusCompletedOp = true;
            
            addRtmToken( OP_COMPLETED, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:699:21:
         // ( 'completedbefore:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:699:23:
         // 'completedbefore:' (s= STRING |s= Q_STRING )
         {
            match( "completedbefore:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:699:42:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:699:44:
               // s= STRING
               {
                  int sStart1490 = getCharIndex();
                  int sStartLine1490 = getLine();
                  int sStartCharPos1490 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1490,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1490 );
                  s.setCharPositionInLine( sStartCharPos1490 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:699:55:
               // s= Q_STRING
               {
                  int sStart1496 = getCharIndex();
                  int sStartLine1496 = getLine();
                  int sStartCharPos1496 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1496,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1496 );
                  s.setCharPositionInLine( sStartCharPos1496 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            differsTimeParam( Tasks.COMPLETED_DATE, s.getText(), true );
            hasStatusCompletedOp = true;
            
            addRtmToken( OP_COMPLETED_BEFORE, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:710:20:
         // ( 'completedafter:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:710:22:
         // 'completedafter:' (s= STRING |s= Q_STRING )
         {
            match( "completedafter:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:710:40:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:710:42:
               // s= STRING
               {
                  int sStart1536 = getCharIndex();
                  int sStartLine1536 = getLine();
                  int sStartCharPos1536 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1536,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1536 );
                  s.setCharPositionInLine( sStartCharPos1536 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:710:53:
               // s= Q_STRING
               {
                  int sStart1542 = getCharIndex();
                  int sStartLine1542 = getLine();
                  int sStartCharPos1542 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1542,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1542 );
                  s.setCharPositionInLine( sStartCharPos1542 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            differsTimeParam( Tasks.COMPLETED_DATE, s.getText(), false );
            hasStatusCompletedOp = true;
            
            addRtmToken( OP_COMPLETED_AFTER, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:721:21:
         // ( 'completedwithin:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:721:23:
         // 'completedwithin:' s= Q_STRING
         {
            match( "completedwithin:" );
            
            int sStart1579 = getCharIndex();
            int sStartLine1579 = getLine();
            int sStartCharPos1579 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1579,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1579 );
            s.setCharPositionInLine( sStartCharPos1579 );
            
            ensureOperator();
            
            inTimeParamRange( Tasks.COMPLETED_DATE, s.getText(), true );
            hasStatusCompletedOp = true;
            
            addRtmToken( OP_COMPLETED_WITHIN, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:732:14:
         // ( 'added:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:732:16:
         // 'added:' (s= STRING |s= Q_STRING )
         {
            match( "added:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:732:25:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:732:27:
               // s= STRING
               {
                  int sStart1621 = getCharIndex();
                  int sStartLine1621 = getLine();
                  int sStartCharPos1621 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1621,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1621 );
                  s.setCharPositionInLine( sStartCharPos1621 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:732:38:
               // s= Q_STRING
               {
                  int sStart1627 = getCharIndex();
                  int sStartLine1627 = getLine();
                  int sStartCharPos1627 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1627,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1627 );
                  s.setCharPositionInLine( sStartCharPos1627 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            equalsTimeParam( Tasks.ADDED_DATE, s.getText() );
            
            addRtmToken( OP_ADDED, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:742:17:
         // ( 'addedbefore:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:742:19:
         // 'addedbefore:' (s= STRING |s= Q_STRING )
         {
            match( "addedbefore:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:742:34:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:742:36:
               // s= STRING
               {
                  int sStart1660 = getCharIndex();
                  int sStartLine1660 = getLine();
                  int sStartCharPos1660 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1660,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1660 );
                  s.setCharPositionInLine( sStartCharPos1660 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:742:47:
               // s= Q_STRING
               {
                  int sStart1666 = getCharIndex();
                  int sStartLine1666 = getLine();
                  int sStartCharPos1666 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1666,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1666 );
                  s.setCharPositionInLine( sStartCharPos1666 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            differsTimeParam( Tasks.ADDED_DATE, s.getText(), true );
            
            addRtmToken( OP_ADDED_BEFORE, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:752:16:
         // ( 'addedafter:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:752:18:
         // 'addedafter:' (s= STRING |s= Q_STRING )
         {
            match( "addedafter:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:752:32:
            // (s= STRING |s= Q_STRING )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:752:34:
               // s= STRING
               {
                  int sStart1702 = getCharIndex();
                  int sStartLine1702 = getLine();
                  int sStartCharPos1702 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1702,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1702 );
                  s.setCharPositionInLine( sStartCharPos1702 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:752:45:
               // s= Q_STRING
               {
                  int sStart1708 = getCharIndex();
                  int sStartLine1708 = getLine();
                  int sStartCharPos1708 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1708,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1708 );
                  s.setCharPositionInLine( sStartCharPos1708 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            differsTimeParam( Tasks.ADDED_DATE, s.getText(), false );
            
            addRtmToken( OP_ADDED_AFTER, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:762:17:
         // ( 'addedwithin:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:762:19:
         // 'addedwithin:' s= Q_STRING
         {
            match( "addedwithin:" );
            
            int sStart1741 = getCharIndex();
            int sStartLine1741 = getLine();
            int sStartCharPos1741 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1741,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1741 );
            s.setCharPositionInLine( sStartCharPos1741 );
            
            ensureOperator();
            
            inTimeParamRange( Tasks.ADDED_DATE, s.getText(), true );
            
            addRtmToken( OP_ADDED_WITHIN, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:772:18:
         // ( 'timeestimate:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:772:20:
         // 'timeestimate:' s= Q_STRING
         {
            match( "timeestimate:" );
            
            int sStart1773 = getCharIndex();
            int sStartLine1773 = getLine();
            int sStartCharPos1773 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1773,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1773 );
            s.setCharPositionInLine( sStartCharPos1773 );
            
            ensureOperator();
            
            result.append( "(" );
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
            {
               result.append( estimatedMillis );
               result.append( ")" );
            }
            
            addRtmToken( OP_TIME_ESTIMATE, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:809:14:
         // ( 'postponed:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:809:16:
         // 'postponed:' (s= STRING |s= Q_STRING )
         {
            match( "postponed:" );
            
            ensureOperator();
            result.append( Tasks.POSTPONED );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:814:16:
            // (s= STRING |s= Q_STRING )
            int alt22 = 2;
            int LA22_0 = input.LA( 1 );
            
            if ( ( ( LA22_0 >= '\u0000' && LA22_0 <= '\u001F' )
               || LA22_0 == '!' || ( LA22_0 >= '#' && LA22_0 <= '\'' ) || ( LA22_0 >= '*' && LA22_0 <= '\uFFFF' ) ) )
            {
               alt22 = 1;
            }
            else if ( ( LA22_0 == '\"' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:815:18:
               // s= STRING
               {
                  int sStart1857 = getCharIndex();
                  int sStartLine1857 = getLine();
                  int sStartCharPos1857 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1857,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1857 );
                  s.setCharPositionInLine( sStartCharPos1857 );
                  
                  equalsIntParam( s.getText() );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:820:18:
               // s= Q_STRING
               {
                  int sStart1916 = getCharIndex();
                  int sStartLine1916 = getLine();
                  int sStartCharPos1916 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1916,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1916 );
                  s.setCharPositionInLine( sStartCharPos1916 );
                  
                  result.append( unquotify( s.getText() ) );
                  
               }
                  break;
            
            }
            
            addRtmToken( OP_POSTPONED, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:830:14:
         // ( 'isshared:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:830:16:
         // 'isshared:' ( TRUE | FALSE )
         {
            match( "isshared:" );
            
            ensureOperator();
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:834:16:
            // ( TRUE | FALSE )
            int alt23 = 2;
            int LA23_0 = input.LA( 1 );
            
            if ( ( LA23_0 == 't' ) )
            {
               alt23 = 1;
            }
            else if ( ( LA23_0 == 'f' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:835:19:
               // TRUE
               {
                  mTRUE();
                  
                  result.append( Tasks.PARTICIPANT_IDS + " IS NOT NULL" );
                  addRtmToken( OP_IS_SHARED, TRUE_LIT );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:841:19:
               // FALSE
               {
                  mFALSE();
                  
                  result.append( Tasks.PARTICIPANT_IDS + " IS NULL" );
                  addRtmToken( OP_IS_SHARED, FALSE_LIT );
                  
               }
                  break;
            
            }
            
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:851:16:
         // ( 'sharedwith:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:851:18:
         // 'sharedwith:' (s= STRING |s= Q_STRING )
         {
            match( "sharedwith:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:851:32:
            // (s= STRING |s= Q_STRING )
            int alt24 = 2;
            int LA24_0 = input.LA( 1 );
            
            if ( ( ( LA24_0 >= '\u0000' && LA24_0 <= '\u001F' )
               || LA24_0 == '!' || ( LA24_0 >= '#' && LA24_0 <= '\'' ) || ( LA24_0 >= '*' && LA24_0 <= '\uFFFF' ) ) )
            {
               alt24 = 1;
            }
            else if ( ( LA24_0 == '\"' ) )
            {
               alt24 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     24,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
            }
            switch ( alt24 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:851:34:
               // s= STRING
               {
                  int sStart2159 = getCharIndex();
                  int sStartLine2159 = getLine();
                  int sStartCharPos2159 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart2159,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine2159 );
                  s.setCharPositionInLine( sStartCharPos2159 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:851:45:
               // s= Q_STRING
               {
                  int sStart2165 = getCharIndex();
                  int sStartLine2165 = getLine();
                  int sStartCharPos2165 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart2165,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine2165 );
                  s.setCharPositionInLine( sStartCharPos2165 );
                  
               }
                  break;
            
            }
            
            ensureOperator();
            
            result.append( "(" );
            result.append( Tasks.PARTICIPANT_FULLNAMES );
            containsStringParam( s.getText() );
            
            result.append( " OR " );
            result.append( Tasks.PARTICIPANT_USERNAMES );
            containsStringParam( s.getText() );
            result.append( ")" );
            
            addRtmToken( OP_SHARED_WITH, s.getText() );
            lexedOperator = false;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:879:13:
         // ( 'completed' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:879:15:
         // 'completed'
         {
            match( "completed" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:881:13:
         // ( 'incomplete' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:881:15:
         // 'incomplete'
         {
            match( "incomplete" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:883:13:
         // ( 'true' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:883:15:
         // 'true'
         {
            match( "true" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:885:13:
         // ( 'false' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:885:15:
         // 'false'
         {
            match( "false" );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "FALSE"
   
   // $ANTLR start "PRIO_HIGH"
   public final void mPRIO_HIGH() throws RecognitionException
   {
      try
      {
         int _type = PRIO_HIGH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:887:13:
         // ( '1' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:887:15:
         // '1'
         {
            match( '1' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "PRIO_HIGH"
   
   // $ANTLR start "PRIO_MED"
   public final void mPRIO_MED() throws RecognitionException
   {
      try
      {
         int _type = PRIO_MED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:889:13:
         // ( '2' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:889:15:
         // '2'
         {
            match( '2' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "PRIO_MED"
   
   // $ANTLR start "PRIO_LOW"
   public final void mPRIO_LOW() throws RecognitionException
   {
      try
      {
         int _type = PRIO_LOW;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:891:13:
         // ( '3' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:891:15:
         // '3'
         {
            match( '3' );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "PRIO_LOW"
   
   // $ANTLR start "PRIO_NONE"
   public final void mPRIO_NONE() throws RecognitionException
   {
      try
      {
         int _type = PRIO_NONE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:893:11:
         // ( 'n' | 'N' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:
         {
            if ( input.LA( 1 ) == 'N' || input.LA( 1 ) == 'n' )
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
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "PRIO_NONE"
   
   // $ANTLR start "L_PARENTH"
   public final void mL_PARENTH() throws RecognitionException
   {
      try
      {
         int _type = L_PARENTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:895:13:
         // ( '(' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:895:15:
         // '('
         {
            match( '(' );
            
            ensureOperator();
            result.append( "( " );
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:901:13:
         // ( ')' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:901:15:
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
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:906:13:
         // ( 'and' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:906:15:
         // 'and'
         {
            match( "and" );
            
            result.append( " AND " );
            opNot = false;
            lexedOperator = true;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:913:13:
         // ( 'or' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:913:15:
         // 'or'
         {
            match( "or" );
            
            result.append( " OR " );
            opNot = false;
            lexedOperator = true;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:920:13:
         // ( 'not' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:920:15:
         // 'not'
         {
            match( "not" );
            
            ensureOperator();
            
            result.append( " NOT " );
            opNot = true;
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally
      {
         // do for sure before leaving
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:929:13:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:929:17:
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
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "WS"
   
   // $ANTLR start "Q_STRING"
   public final void mQ_STRING() throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:936:13:
         // ( '\"' (~ ( '\"' ) )* '\"' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:936:15:
         // '\"' (~ ( '\"' ) )* '\"'
         {
            match( '\"' );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:936:19:
            // (~ ( '\"' ) )*
            loop25: do
            {
               int alt25 = 2;
               int LA25_0 = input.LA( 1 );
               
               if ( ( ( LA25_0 >= '\u0000' && LA25_0 <= '!' ) || ( LA25_0 >= '#' && LA25_0 <= '\uFFFF' ) ) )
               {
                  alt25 = 1;
               }
               
               switch ( alt25 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:
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
                     break loop25;
               }
            }
            while ( true );
            
            match( '\"' );
            
         }
         
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "Q_STRING"
   
   // $ANTLR start "STRING"
   public final void mSTRING() throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:939:13:
         // ( (~ ( '\"' | ' ' | '(' | ')' ) )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:939:15:
         // (~ ( '\"' | ' ' | '(' | ')' ) )+
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:939:15:
            // (~ ( '\"' | ' ' | '(' | ')' ) )+
            int cnt26 = 0;
            loop26: do
            {
               int alt26 = 2;
               int LA26_0 = input.LA( 1 );
               
               if ( ( ( LA26_0 >= '\u0000' && LA26_0 <= '\u001F' )
                  || LA26_0 == '!' || ( LA26_0 >= '#' && LA26_0 <= '\'' ) || ( LA26_0 >= '*' && LA26_0 <= '\uFFFF' ) ) )
               {
                  alt26 = 1;
               }
               
               switch ( alt26 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:
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
                     if ( cnt26 >= 1 )
                        break loop26;
                     EarlyExitException eee = new EarlyExitException( 26, input );
                     throw eee;
               }
               cnt26++;
            }
            while ( true );
            
         }
         
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "STRING"
   
   public void mTokens() throws RecognitionException
   {
      // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:8:
      // ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED |
      // OP_IS_REPEATING | OP_NAME | OP_NOTE_CONTAINS | OP_HAS_NOTES | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE |
      // OP_DUE_WITHIN | OP_COMPLETED | OP_COMPLETED_BEFORE | OP_COMPLETED_AFTER | OP_COMPLETED_WITHIN | OP_ADDED |
      // OP_ADDED_BEFORE | OP_ADDED_AFTER | OP_ADDED_WITHIN | OP_TIME_ESTIMATE | OP_POSTPONED | OP_IS_SHARED |
      // OP_SHARED_WITH | COMPLETED | INCOMPLETE | TRUE | FALSE | PRIO_HIGH | PRIO_MED | PRIO_LOW | PRIO_NONE |
      // L_PARENTH | R_PARENTH | AND | OR | NOT | WS )
      int alt27 = 42;
      alt27 = dfa27.predict( input );
      switch ( alt27 )
      {
         case 1:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:10:
         // OP_LIST
         {
            mOP_LIST();
            
         }
            break;
         case 2:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:18:
         // OP_PRIORITY
         {
            mOP_PRIORITY();
            
         }
            break;
         case 3:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:30:
         // OP_STATUS
         {
            mOP_STATUS();
            
         }
            break;
         case 4:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:40:
         // OP_TAG
         {
            mOP_TAG();
            
         }
            break;
         case 5:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:47:
         // OP_TAG_CONTAINS
         {
            mOP_TAG_CONTAINS();
            
         }
            break;
         case 6:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:63:
         // OP_IS_TAGGED
         {
            mOP_IS_TAGGED();
            
         }
            break;
         case 7:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:76:
         // OP_LOCATION
         {
            mOP_LOCATION();
            
         }
            break;
         case 8:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:88:
         // OP_ISLOCATED
         {
            mOP_ISLOCATED();
            
         }
            break;
         case 9:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:101:
         // OP_IS_REPEATING
         {
            mOP_IS_REPEATING();
            
         }
            break;
         case 10:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:117:
         // OP_NAME
         {
            mOP_NAME();
            
         }
            break;
         case 11:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:125:
         // OP_NOTE_CONTAINS
         {
            mOP_NOTE_CONTAINS();
            
         }
            break;
         case 12:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:142:
         // OP_HAS_NOTES
         {
            mOP_HAS_NOTES();
            
         }
            break;
         case 13:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:155:
         // OP_DUE
         {
            mOP_DUE();
            
         }
            break;
         case 14:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:162:
         // OP_DUE_AFTER
         {
            mOP_DUE_AFTER();
            
         }
            break;
         case 15:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:175:
         // OP_DUE_BEFORE
         {
            mOP_DUE_BEFORE();
            
         }
            break;
         case 16:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:189:
         // OP_DUE_WITHIN
         {
            mOP_DUE_WITHIN();
            
         }
            break;
         case 17:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:203:
         // OP_COMPLETED
         {
            mOP_COMPLETED();
            
         }
            break;
         case 18:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:216:
         // OP_COMPLETED_BEFORE
         {
            mOP_COMPLETED_BEFORE();
            
         }
            break;
         case 19:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:236:
         // OP_COMPLETED_AFTER
         {
            mOP_COMPLETED_AFTER();
            
         }
            break;
         case 20:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:255:
         // OP_COMPLETED_WITHIN
         {
            mOP_COMPLETED_WITHIN();
            
         }
            break;
         case 21:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:275:
         // OP_ADDED
         {
            mOP_ADDED();
            
         }
            break;
         case 22:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:284:
         // OP_ADDED_BEFORE
         {
            mOP_ADDED_BEFORE();
            
         }
            break;
         case 23:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:300:
         // OP_ADDED_AFTER
         {
            mOP_ADDED_AFTER();
            
         }
            break;
         case 24:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:315:
         // OP_ADDED_WITHIN
         {
            mOP_ADDED_WITHIN();
            
         }
            break;
         case 25:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:331:
         // OP_TIME_ESTIMATE
         {
            mOP_TIME_ESTIMATE();
            
         }
            break;
         case 26:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:348:
         // OP_POSTPONED
         {
            mOP_POSTPONED();
            
         }
            break;
         case 27:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:361:
         // OP_IS_SHARED
         {
            mOP_IS_SHARED();
            
         }
            break;
         case 28:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:374:
         // OP_SHARED_WITH
         {
            mOP_SHARED_WITH();
            
         }
            break;
         case 29:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:389:
         // COMPLETED
         {
            mCOMPLETED();
            
         }
            break;
         case 30:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:399:
         // INCOMPLETE
         {
            mINCOMPLETE();
            
         }
            break;
         case 31:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:410:
         // TRUE
         {
            mTRUE();
            
         }
            break;
         case 32:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:415:
         // FALSE
         {
            mFALSE();
            
         }
            break;
         case 33:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:421:
         // PRIO_HIGH
         {
            mPRIO_HIGH();
            
         }
            break;
         case 34:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:431:
         // PRIO_MED
         {
            mPRIO_MED();
            
         }
            break;
         case 35:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:440:
         // PRIO_LOW
         {
            mPRIO_LOW();
            
         }
            break;
         case 36:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:449:
         // PRIO_NONE
         {
            mPRIO_NONE();
            
         }
            break;
         case 37:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:459:
         // L_PARENTH
         {
            mL_PARENTH();
            
         }
            break;
         case 38:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:469:
         // R_PARENTH
         {
            mR_PARENTH();
            
         }
            break;
         case 39:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:479:
         // AND
         {
            mAND();
            
         }
            break;
         case 40:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:483:
         // OR
         {
            mOR();
            
         }
            break;
         case 41:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:486:
         // NOT
         {
            mNOT();
            
         }
            break;
         case 42:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_trunk\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:490:
         // WS
         {
            mWS();
            
         }
            break;
      
      }
      
   }
   
   protected DFA27 dfa27 = new DFA27( this );
   
   static final String DFA27_eotS = "\6\uffff\1\17\43\uffff\1\61\26\uffff\1\106\5\uffff";
   
   static final String DFA27_eofS = "\107\uffff";
   
   static final String DFA27_minS = "\1\11\1\151\1\157\1\150\1\141\1\156\1\141\1\uffff\1\165\1\157\1"
      + "\144\17\uffff\1\147\2\uffff\1\154\2\uffff\1\164\1\145\1\155\1\144"
      + "\1\uffff\1\72\4\uffff\1\145\1\72\1\160\1\145\10\uffff\1\154\1\144"
      + "\1\145\1\72\1\164\4\uffff\1\145\1\144\1\72\5\uffff";
   
   static final String DFA27_maxS = "\1\164\1\157\1\162\1\164\1\162\1\163\1\157\1\uffff\1\165\1\157\1"
      + "\156\17\uffff\1\147\2\uffff\1\164\2\uffff\1\164\1\145\1\155\1\144"
      + "\1\uffff\1\143\4\uffff\1\145\1\167\1\160\1\145\10\uffff\1\154\1"
      + "\144\1\145\1\167\1\164\4\uffff\1\145\1\144\1\167\5\uffff";
   
   static final String DFA27_acceptS = "\7\uffff\1\14\3\uffff\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\50\1"
      + "\52\1\1\1\7\1\2\1\32\1\3\1\34\1\uffff\1\31\1\37\1\uffff\1\36\1\12"
      + "\4\uffff\1\47\1\uffff\1\6\1\10\1\11\1\33\4\uffff\1\4\1\5\1\13\1"
      + "\51\1\15\1\16\1\17\1\20\5\uffff\1\25\1\26\1\27\1\30\3\uffff\1\21"
      + "\1\22\1\23\1\24\1\35";
   
   static final String DFA27_specialS = "\107\uffff}>";
   
   static final String[] DFA27_transitionS =
   {
    "\2\23\2\uffff\1\23\22\uffff\1\23\7\uffff\1\20\1\21\7\uffff\1"
       + "\14\1\15\1\16\32\uffff\1\17\22\uffff\1\12\1\uffff\1\11\1\10"
       + "\1\uffff\1\13\1\uffff\1\7\1\5\2\uffff\1\1\1\uffff\1\6\1\22\1"
       + "\2\2\uffff\1\3\1\4", "\1\24\5\uffff\1\25", "\1\27\2\uffff\1\26",
    "\1\31\13\uffff\1\30", "\1\32\7\uffff\1\33\10\uffff\1\34",
    "\1\36\4\uffff\1\35", "\1\37\15\uffff\1\40", "", "\1\41", "\1\42",
    "\1\43\11\uffff\1\44", "", "", "", "", "", "", "", "", "", "", "", "", "",
    "", "", "\1\45", "", "", "\1\47\5\uffff\1\50\1\51\1\46", "", "", "\1\52",
    "\1\53", "\1\54", "\1\55", "", "\1\56\50\uffff\1\57", "", "", "", "",
    "\1\60", "\1\62\46\uffff\1\63\1\64\24\uffff\1\65", "\1\66", "\1\67", "",
    "", "", "", "", "", "", "", "\1\70", "\1\71", "\1\72",
    "\1\73\46\uffff\1\75\1\74\24\uffff\1\76", "\1\77", "", "", "", "",
    "\1\100", "\1\101", "\1\102\46\uffff\1\104\1\103\24\uffff\1\105", "", "",
    "", "", "" };
   
   static final short[] DFA27_eot = DFA.unpackEncodedString( DFA27_eotS );
   
   static final short[] DFA27_eof = DFA.unpackEncodedString( DFA27_eofS );
   
   static final char[] DFA27_min = DFA.unpackEncodedStringToUnsignedChars( DFA27_minS );
   
   static final char[] DFA27_max = DFA.unpackEncodedStringToUnsignedChars( DFA27_maxS );
   
   static final short[] DFA27_accept = DFA.unpackEncodedString( DFA27_acceptS );
   
   static final short[] DFA27_special = DFA.unpackEncodedString( DFA27_specialS );
   
   static final short[][] DFA27_transition;
   
   static
   {
      int numStates = DFA27_transitionS.length;
      DFA27_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA27_transition[ i ] = DFA.unpackEncodedString( DFA27_transitionS[ i ] );
      }
   }
   
   
   class DFA27 extends DFA
   {
      
      public DFA27( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 27;
         this.eot = DFA27_eot;
         this.eof = DFA27_eof;
         this.min = DFA27_min;
         this.max = DFA27_max;
         this.accept = DFA27_accept;
         this.special = DFA27_special;
         this.transition = DFA27_transition;
      }
      
      
      
      public String getDescription()
      {
         return "1:1: Tokens : ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED | OP_IS_REPEATING | OP_NAME | OP_NOTE_CONTAINS | OP_HAS_NOTES | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE | OP_DUE_WITHIN | OP_COMPLETED | OP_COMPLETED_BEFORE | OP_COMPLETED_AFTER | OP_COMPLETED_WITHIN | OP_ADDED | OP_ADDED_BEFORE | OP_ADDED_AFTER | OP_ADDED_WITHIN | OP_TIME_ESTIMATE | OP_POSTPONED | OP_IS_SHARED | OP_SHARED_WITH | COMPLETED | INCOMPLETE | TRUE | FALSE | PRIO_HIGH | PRIO_MED | PRIO_LOW | PRIO_NONE | L_PARENTH | R_PARENTH | AND | OR | NOT | WS );";
      }
   }
   
}
