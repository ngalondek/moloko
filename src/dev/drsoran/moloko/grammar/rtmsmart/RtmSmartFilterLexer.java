// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g 2013-07-03 08:25:34

package dev.drsoran.moloko.grammar.rtmsmart;

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

import dev.drsoran.moloko.grammar.GrammarException;
import dev.drsoran.moloko.util.Strings;


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
   
   public static final int NUMBER = 10;
   
   public static final int OP_ADDED = 11;
   
   public static final int OP_ADDED_AFTER = 12;
   
   public static final int OP_ADDED_BEFORE = 13;
   
   public static final int OP_ADDED_WITHIN = 14;
   
   public static final int OP_COMPLETED = 15;
   
   public static final int OP_COMPLETED_AFTER = 16;
   
   public static final int OP_COMPLETED_BEFORE = 17;
   
   public static final int OP_COMPLETED_WITHIN = 18;
   
   public static final int OP_DUE = 19;
   
   public static final int OP_DUE_AFTER = 20;
   
   public static final int OP_DUE_BEFORE = 21;
   
   public static final int OP_DUE_WITHIN = 22;
   
   public static final int OP_HAS_NOTES = 23;
   
   public static final int OP_ISLOCATED = 24;
   
   public static final int OP_IS_REPEATING = 25;
   
   public static final int OP_IS_SHARED = 26;
   
   public static final int OP_IS_TAGGED = 27;
   
   public static final int OP_LIST = 28;
   
   public static final int OP_LOCATION = 29;
   
   public static final int OP_NAME = 30;
   
   public static final int OP_NOTE_CONTAINS = 31;
   
   public static final int OP_POSTPONED = 32;
   
   public static final int OP_PRIORITY = 33;
   
   public static final int OP_SHARED_WITH = 34;
   
   public static final int OP_STATUS = 35;
   
   public static final int OP_TAG = 36;
   
   public static final int OP_TAG_CONTAINS = 37;
   
   public static final int OP_TIME_ESTIMATE = 38;
   
   public static final int OR = 39;
   
   public static final int PRIO_HIGH = 40;
   
   public static final int PRIO_LOW = 41;
   
   public static final int PRIO_MED = 42;
   
   public static final int PRIO_NONE = 43;
   
   public static final int QUOTE = 44;
   
   public static final int Q_STRING = 45;
   
   public static final int RELATION = 46;
   
   public static final int R_PARENTH = 47;
   
   public static final int STRING = 48;
   
   public static final int TRUE = 49;
   
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
   
   private IRtmSmartFilterEvaluator evaluator;
   
   // STATUS VARIABLES
   
   private boolean hasStatusCompletedOp;
   
   private boolean error;
   
   private RecognitionException innerException;
   
   
   
   public void setEvaluator( IRtmSmartFilterEvaluator evaluator )
   {
      this.evaluator = evaluator;
   }
   
   
   
   public void startEvaluation() throws GrammarException
   {
      Token currentToken = null;
      
      if ( !error )
      {
         hasStatusCompletedOp = false;
         
         for ( currentToken = nextToken(); !error
            && currentToken != Token.EOF_TOKEN; currentToken = nextToken() )
         {
         }
      }
      
      if ( error )
      {
         final String exceptionMessage = String.format( "Failed to evaluate RTM smart filter. Last token was '%s':%d.",
                                                        currentToken,
                                                        getCharPositionInLine() );
         
         if ( innerException != null )
         {
            throw new GrammarException( exceptionMessage, innerException );
         }
         else
         {
            throw new GrammarException( exceptionMessage );
         }
      }
   }
   
   
   
   @Override
   public void reset()
   {
      super.reset();
      
      hasStatusCompletedOp = false;
      error = false;
      innerException = null;
   }
   
   
   
   @Override
   public void reportError( RecognitionException e )
   {
      error = true;
      innerException = e;
      
      super.reportError( e );
   }
   
   
   
   public boolean hasStatusCompletedOperator()
   {
      return hasStatusCompletedOp;
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
      return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g";
   }
   
   
   
   // $ANTLR start "OP_LIST"
   public final void mOP_LIST() throws RecognitionException
   {
      try
      {
         int _type = OP_LIST;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:192:13:
         // ( 'list:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:192:16:
         // 'list:' (s= STRING |s= Q_STRING )
         {
            match( "list:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:192:24:
            // (s= STRING |s= Q_STRING )
            int alt1 = 2;
            int LA1_0 = input.LA( 1 );
            
            if ( ( ( LA1_0 >= '\u0000' && LA1_0 <= '\u001F' ) || LA1_0 == '!'
               || ( LA1_0 >= '#' && LA1_0 <= '&' ) || ( LA1_0 >= '*' && LA1_0 <= '\uFFFF' ) ) )
            {
               alt1 = 1;
            }
            else if ( ( LA1_0 == '\"' || LA1_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:192:26:
               // s= STRING
               {
                  int sStart54 = getCharIndex();
                  int sStartLine54 = getLine();
                  int sStartCharPos54 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart54,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine54 );
                  s.setCharPositionInLine( sStartCharPos54 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:192:37:
               // s= Q_STRING
               {
                  int sStart60 = getCharIndex();
                  int sStartLine60 = getLine();
                  int sStartCharPos60 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart60,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine60 );
                  s.setCharPositionInLine( sStartCharPos60 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalList( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_LIST"
   
   // $ANTLR start "OP_PRIORITY"
   public final void mOP_PRIORITY() throws RecognitionException
   {
      try
      {
         int _type = OP_PRIORITY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken p = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:200:13:
         // ( 'priority:' (p= PRIO_HIGH |p= PRIO_MED |p= PRIO_LOW |p= PRIO_NONE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:200:16:
         // 'priority:' (p= PRIO_HIGH |p= PRIO_MED |p= PRIO_LOW |p= PRIO_NONE )
         {
            match( "priority:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:200:28:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:200:30:
               // p= PRIO_HIGH
               {
                  int pStart94 = getCharIndex();
                  int pStartLine94 = getLine();
                  int pStartCharPos94 = getCharPositionInLine();
                  mPRIO_HIGH();
                  p = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       pStart94,
                                       getCharIndex() - 1 );
                  p.setLine( pStartLine94 );
                  p.setCharPositionInLine( pStartCharPos94 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:200:44:
               // p= PRIO_MED
               {
                  int pStart100 = getCharIndex();
                  int pStartLine100 = getLine();
                  int pStartCharPos100 = getCharPositionInLine();
                  mPRIO_MED();
                  p = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       pStart100,
                                       getCharIndex() - 1 );
                  p.setLine( pStartLine100 );
                  p.setCharPositionInLine( pStartCharPos100 );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:200:57:
               // p= PRIO_LOW
               {
                  int pStart106 = getCharIndex();
                  int pStartLine106 = getLine();
                  int pStartCharPos106 = getCharPositionInLine();
                  mPRIO_LOW();
                  p = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       pStart106,
                                       getCharIndex() - 1 );
                  p.setLine( pStartLine106 );
                  p.setCharPositionInLine( pStartCharPos106 );
                  
               }
                  break;
               case 4:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:200:70:
               // p= PRIO_NONE
               {
                  int pStart112 = getCharIndex();
                  int pStartLine112 = getLine();
                  int pStartCharPos112 = getCharPositionInLine();
                  mPRIO_NONE();
                  p = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       pStart112,
                                       getCharIndex() - 1 );
                  p.setLine( pStartLine112 );
                  p.setCharPositionInLine( pStartCharPos112 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalPriority( p.getText() );
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
   
   
   
   // $ANTLR end "OP_PRIORITY"
   
   // $ANTLR start "OP_STATUS"
   public final void mOP_STATUS() throws RecognitionException
   {
      try
      {
         int _type = OP_STATUS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:208:13:
         // ( 'status:' ( COMPLETED | INCOMPLETE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:208:16:
         // 'status:' ( COMPLETED | INCOMPLETE )
         {
            match( "status:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:209:16:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:210:19:
               // COMPLETED
               {
                  mCOMPLETED();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalStatus( true );
                  }
                  
                  hasStatusCompletedOp = true;
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:220:19:
               // INCOMPLETE
               {
                  mINCOMPLETE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalStatus( false );
                  }
                  
               }
                  break;
            
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
   
   
   
   // $ANTLR end "OP_STATUS"
   
   // $ANTLR start "OP_TAG"
   public final void mOP_TAG() throws RecognitionException
   {
      try
      {
         int _type = OP_TAG;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:229:13:
         // ( 'tag:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:229:15:
         // 'tag:' (s= STRING |s= Q_STRING )
         {
            match( "tag:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:229:22:
            // (s= STRING |s= Q_STRING )
            int alt4 = 2;
            int LA4_0 = input.LA( 1 );
            
            if ( ( ( LA4_0 >= '\u0000' && LA4_0 <= '\u001F' ) || LA4_0 == '!'
               || ( LA4_0 >= '#' && LA4_0 <= '&' ) || ( LA4_0 >= '*' && LA4_0 <= '\uFFFF' ) ) )
            {
               alt4 = 1;
            }
            else if ( ( LA4_0 == '\"' || LA4_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:229:24:
               // s= STRING
               {
                  int sStart295 = getCharIndex();
                  int sStartLine295 = getLine();
                  int sStartCharPos295 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart295,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine295 );
                  s.setCharPositionInLine( sStartCharPos295 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:229:35:
               // s= Q_STRING
               {
                  int sStart301 = getCharIndex();
                  int sStartLine301 = getLine();
                  int sStartCharPos301 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart301,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine301 );
                  s.setCharPositionInLine( sStartCharPos301 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalTag( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_TAG"
   
   // $ANTLR start "OP_TAG_CONTAINS"
   public final void mOP_TAG_CONTAINS() throws RecognitionException
   {
      try
      {
         int _type = OP_TAG_CONTAINS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:237:17:
         // ( 'tagcontains:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:237:19:
         // 'tagcontains:' (s= STRING |s= Q_STRING )
         {
            match( "tagcontains:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:237:34:
            // (s= STRING |s= Q_STRING )
            int alt5 = 2;
            int LA5_0 = input.LA( 1 );
            
            if ( ( ( LA5_0 >= '\u0000' && LA5_0 <= '\u001F' ) || LA5_0 == '!'
               || ( LA5_0 >= '#' && LA5_0 <= '&' ) || ( LA5_0 >= '*' && LA5_0 <= '\uFFFF' ) ) )
            {
               alt5 = 1;
            }
            else if ( ( LA5_0 == '\"' || LA5_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:237:36:
               // s= STRING
               {
                  int sStart333 = getCharIndex();
                  int sStartLine333 = getLine();
                  int sStartCharPos333 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart333,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine333 );
                  s.setCharPositionInLine( sStartCharPos333 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:237:47:
               // s= Q_STRING
               {
                  int sStart339 = getCharIndex();
                  int sStartLine339 = getLine();
                  int sStartCharPos339 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart339,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine339 );
                  s.setCharPositionInLine( sStartCharPos339 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalTagContains( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_TAG_CONTAINS"
   
   // $ANTLR start "OP_IS_TAGGED"
   public final void mOP_IS_TAGGED() throws RecognitionException
   {
      try
      {
         int _type = OP_IS_TAGGED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:245:17:
         // ( 'istagged:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:245:19:
         // 'istagged:' ( TRUE | FALSE )
         {
            match( "istagged:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:246:19:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:247:22:
               // TRUE
               {
                  mTRUE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalIsTagged( true );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:255:22:
               // FALSE
               {
                  mFALSE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalIsTagged( false );
                  }
                  
               }
                  break;
            
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
   
   
   
   // $ANTLR end "OP_IS_TAGGED"
   
   // $ANTLR start "OP_LOCATION"
   public final void mOP_LOCATION() throws RecognitionException
   {
      try
      {
         int _type = OP_LOCATION;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:264:13:
         // ( 'location:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:264:15:
         // 'location:' (s= STRING |s= Q_STRING )
         {
            match( "location:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:264:27:
            // (s= STRING |s= Q_STRING )
            int alt7 = 2;
            int LA7_0 = input.LA( 1 );
            
            if ( ( ( LA7_0 >= '\u0000' && LA7_0 <= '\u001F' ) || LA7_0 == '!'
               || ( LA7_0 >= '#' && LA7_0 <= '&' ) || ( LA7_0 >= '*' && LA7_0 <= '\uFFFF' ) ) )
            {
               alt7 = 1;
            }
            else if ( ( LA7_0 == '\"' || LA7_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:264:29:
               // s= STRING
               {
                  int sStart541 = getCharIndex();
                  int sStartLine541 = getLine();
                  int sStartCharPos541 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart541,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine541 );
                  s.setCharPositionInLine( sStartCharPos541 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:264:40:
               // s= Q_STRING
               {
                  int sStart547 = getCharIndex();
                  int sStartLine547 = getLine();
                  int sStartCharPos547 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart547,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine547 );
                  s.setCharPositionInLine( sStartCharPos547 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalLocation( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_LOCATION"
   
   // $ANTLR start "OP_ISLOCATED"
   public final void mOP_ISLOCATED() throws RecognitionException
   {
      try
      {
         int _type = OP_ISLOCATED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:274:14:
         // ( 'islocated:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:274:16:
         // 'islocated:' ( TRUE | FALSE )
         {
            match( "islocated:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:275:16:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:276:19:
               // TRUE
               {
                  mTRUE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalIsLocated( true );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:284:19:
               // FALSE
               {
                  mFALSE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalIsLocated( false );
                  }
                  
               }
                  break;
            
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
   
   
   
   // $ANTLR end "OP_ISLOCATED"
   
   // $ANTLR start "OP_IS_REPEATING"
   public final void mOP_IS_REPEATING() throws RecognitionException
   {
      try
      {
         int _type = OP_IS_REPEATING;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:293:17:
         // ( 'isrepeating:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:293:19:
         // 'isrepeating:' ( TRUE | FALSE )
         {
            match( "isrepeating:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:294:19:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:295:22:
               // TRUE
               {
                  mTRUE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalIsRepeating( true );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:303:22:
               // FALSE
               {
                  mFALSE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalIsRepeating( false );
                  }
                  
               }
                  break;
            
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
   
   
   
   // $ANTLR end "OP_IS_REPEATING"
   
   // $ANTLR start "OP_NAME"
   public final void mOP_NAME() throws RecognitionException
   {
      try
      {
         int _type = OP_NAME;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:312:14:
         // ( 'name:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:312:16:
         // 'name:' (s= STRING |s= Q_STRING )
         {
            match( "name:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:312:24:
            // (s= STRING |s= Q_STRING )
            int alt10 = 2;
            int LA10_0 = input.LA( 1 );
            
            if ( ( ( LA10_0 >= '\u0000' && LA10_0 <= '\u001F' )
               || LA10_0 == '!' || ( LA10_0 >= '#' && LA10_0 <= '&' ) || ( LA10_0 >= '*' && LA10_0 <= '\uFFFF' ) ) )
            {
               alt10 = 1;
            }
            else if ( ( LA10_0 == '\"' || LA10_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:312:26:
               // s= STRING
               {
                  int sStart892 = getCharIndex();
                  int sStartLine892 = getLine();
                  int sStartCharPos892 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart892,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine892 );
                  s.setCharPositionInLine( sStartCharPos892 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:312:37:
               // s= Q_STRING
               {
                  int sStart898 = getCharIndex();
                  int sStartLine898 = getLine();
                  int sStartCharPos898 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart898,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine898 );
                  s.setCharPositionInLine( sStartCharPos898 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalTaskName( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_NAME"
   
   // $ANTLR start "OP_NOTE_CONTAINS"
   public final void mOP_NOTE_CONTAINS() throws RecognitionException
   {
      try
      {
         int _type = OP_NOTE_CONTAINS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:320:18:
         // ( 'notecontains:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:320:20:
         // 'notecontains:' (s= STRING |s= Q_STRING )
         {
            match( "notecontains:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:320:36:
            // (s= STRING |s= Q_STRING )
            int alt11 = 2;
            int LA11_0 = input.LA( 1 );
            
            if ( ( ( LA11_0 >= '\u0000' && LA11_0 <= '\u001F' )
               || LA11_0 == '!' || ( LA11_0 >= '#' && LA11_0 <= '&' ) || ( LA11_0 >= '*' && LA11_0 <= '\uFFFF' ) ) )
            {
               alt11 = 1;
            }
            else if ( ( LA11_0 == '\"' || LA11_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:320:38:
               // s= STRING
               {
                  int sStart931 = getCharIndex();
                  int sStartLine931 = getLine();
                  int sStartCharPos931 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart931,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine931 );
                  s.setCharPositionInLine( sStartCharPos931 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:320:49:
               // s= Q_STRING
               {
                  int sStart937 = getCharIndex();
                  int sStartLine937 = getLine();
                  int sStartCharPos937 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart937,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine937 );
                  s.setCharPositionInLine( sStartCharPos937 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalNoteContains( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_NOTE_CONTAINS"
   
   // $ANTLR start "OP_HAS_NOTES"
   public final void mOP_HAS_NOTES() throws RecognitionException
   {
      try
      {
         int _type = OP_HAS_NOTES;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:328:14:
         // ( 'hasnotes:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:328:16:
         // 'hasnotes:' ( TRUE | FALSE )
         {
            match( "hasnotes:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:329:16:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:330:19:
               // TRUE
               {
                  mTRUE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalHasNotes( true );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:338:19:
               // FALSE
               {
                  mFALSE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalHasNotes( false );
                  }
                  
               }
                  break;
            
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
   
   
   
   // $ANTLR end "OP_HAS_NOTES"
   
   // $ANTLR start "OP_DUE"
   public final void mOP_DUE() throws RecognitionException
   {
      try
      {
         int _type = OP_DUE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:347:13:
         // ( 'due:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:347:16:
         // 'due:' (s= STRING |s= Q_STRING )
         {
            match( "due:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:347:23:
            // (s= STRING |s= Q_STRING )
            int alt13 = 2;
            int LA13_0 = input.LA( 1 );
            
            if ( ( ( LA13_0 >= '\u0000' && LA13_0 <= '\u001F' )
               || LA13_0 == '!' || ( LA13_0 >= '#' && LA13_0 <= '&' ) || ( LA13_0 >= '*' && LA13_0 <= '\uFFFF' ) ) )
            {
               alt13 = 1;
            }
            else if ( ( LA13_0 == '\"' || LA13_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:347:25:
               // s= STRING
               {
                  int sStart1122 = getCharIndex();
                  int sStartLine1122 = getLine();
                  int sStartCharPos1122 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1122,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1122 );
                  s.setCharPositionInLine( sStartCharPos1122 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:347:36:
               // s= Q_STRING
               {
                  int sStart1128 = getCharIndex();
                  int sStartLine1128 = getLine();
                  int sStartCharPos1128 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1128,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1128 );
                  s.setCharPositionInLine( sStartCharPos1128 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalDue( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_DUE"
   
   // $ANTLR start "OP_DUE_AFTER"
   public final void mOP_DUE_AFTER() throws RecognitionException
   {
      try
      {
         int _type = OP_DUE_AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:355:14:
         // ( 'dueafter:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:355:16:
         // 'dueafter:' (s= STRING |s= Q_STRING )
         {
            match( "dueafter:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:355:28:
            // (s= STRING |s= Q_STRING )
            int alt14 = 2;
            int LA14_0 = input.LA( 1 );
            
            if ( ( ( LA14_0 >= '\u0000' && LA14_0 <= '\u001F' )
               || LA14_0 == '!' || ( LA14_0 >= '#' && LA14_0 <= '&' ) || ( LA14_0 >= '*' && LA14_0 <= '\uFFFF' ) ) )
            {
               alt14 = 1;
            }
            else if ( ( LA14_0 == '\"' || LA14_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:355:30:
               // s= STRING
               {
                  int sStart1161 = getCharIndex();
                  int sStartLine1161 = getLine();
                  int sStartCharPos1161 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1161,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1161 );
                  s.setCharPositionInLine( sStartCharPos1161 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:355:41:
               // s= Q_STRING
               {
                  int sStart1167 = getCharIndex();
                  int sStartLine1167 = getLine();
                  int sStartCharPos1167 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1167,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1167 );
                  s.setCharPositionInLine( sStartCharPos1167 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalDueAfter( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_DUE_AFTER"
   
   // $ANTLR start "OP_DUE_BEFORE"
   public final void mOP_DUE_BEFORE() throws RecognitionException
   {
      try
      {
         int _type = OP_DUE_BEFORE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:363:15:
         // ( 'duebefore:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:363:17:
         // 'duebefore:' (s= STRING |s= Q_STRING )
         {
            match( "duebefore:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:363:30:
            // (s= STRING |s= Q_STRING )
            int alt15 = 2;
            int LA15_0 = input.LA( 1 );
            
            if ( ( ( LA15_0 >= '\u0000' && LA15_0 <= '\u001F' )
               || LA15_0 == '!' || ( LA15_0 >= '#' && LA15_0 <= '&' ) || ( LA15_0 >= '*' && LA15_0 <= '\uFFFF' ) ) )
            {
               alt15 = 1;
            }
            else if ( ( LA15_0 == '\"' || LA15_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:363:32:
               // s= STRING
               {
                  int sStart1200 = getCharIndex();
                  int sStartLine1200 = getLine();
                  int sStartCharPos1200 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1200,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1200 );
                  s.setCharPositionInLine( sStartCharPos1200 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:363:43:
               // s= Q_STRING
               {
                  int sStart1206 = getCharIndex();
                  int sStartLine1206 = getLine();
                  int sStartCharPos1206 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1206,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1206 );
                  s.setCharPositionInLine( sStartCharPos1206 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalDueBefore( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_DUE_BEFORE"
   
   // $ANTLR start "OP_DUE_WITHIN"
   public final void mOP_DUE_WITHIN() throws RecognitionException
   {
      try
      {
         int _type = OP_DUE_WITHIN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:371:15:
         // ( 'duewithin:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:371:17:
         // 'duewithin:' s= Q_STRING
         {
            match( "duewithin:" );
            
            int sStart1238 = getCharIndex();
            int sStartLine1238 = getLine();
            int sStartCharPos1238 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1238,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1238 );
            s.setCharPositionInLine( sStartCharPos1238 );
            
            if ( evaluator != null )
            {
               error = !evaluator.evalDueWithIn( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_DUE_WITHIN"
   
   // $ANTLR start "OP_COMPLETED"
   public final void mOP_COMPLETED() throws RecognitionException
   {
      try
      {
         int _type = OP_COMPLETED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:379:14:
         // ( 'completed:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:379:16:
         // 'completed:' (s= STRING |s= Q_STRING )
         {
            match( "completed:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:379:29:
            // (s= STRING |s= Q_STRING )
            int alt16 = 2;
            int LA16_0 = input.LA( 1 );
            
            if ( ( ( LA16_0 >= '\u0000' && LA16_0 <= '\u001F' )
               || LA16_0 == '!' || ( LA16_0 >= '#' && LA16_0 <= '&' ) || ( LA16_0 >= '*' && LA16_0 <= '\uFFFF' ) ) )
            {
               alt16 = 1;
            }
            else if ( ( LA16_0 == '\"' || LA16_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:379:31:
               // s= STRING
               {
                  int sStart1270 = getCharIndex();
                  int sStartLine1270 = getLine();
                  int sStartCharPos1270 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1270,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1270 );
                  s.setCharPositionInLine( sStartCharPos1270 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:379:42:
               // s= Q_STRING
               {
                  int sStart1276 = getCharIndex();
                  int sStartLine1276 = getLine();
                  int sStartCharPos1276 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1276,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1276 );
                  s.setCharPositionInLine( sStartCharPos1276 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalCompleted( Strings.unquotify( s.getText() ) );
            }
            
            hasStatusCompletedOp = true;
            
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
   
   // $ANTLR start "OP_COMPLETED_AFTER"
   public final void mOP_COMPLETED_AFTER() throws RecognitionException
   {
      try
      {
         int _type = OP_COMPLETED_AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:389:20:
         // ( 'completedafter:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:389:22:
         // 'completedafter:' (s= STRING |s= Q_STRING )
         {
            match( "completedafter:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:389:40:
            // (s= STRING |s= Q_STRING )
            int alt17 = 2;
            int LA17_0 = input.LA( 1 );
            
            if ( ( ( LA17_0 >= '\u0000' && LA17_0 <= '\u001F' )
               || LA17_0 == '!' || ( LA17_0 >= '#' && LA17_0 <= '&' ) || ( LA17_0 >= '*' && LA17_0 <= '\uFFFF' ) ) )
            {
               alt17 = 1;
            }
            else if ( ( LA17_0 == '\"' || LA17_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:389:42:
               // s= STRING
               {
                  int sStart1324 = getCharIndex();
                  int sStartLine1324 = getLine();
                  int sStartCharPos1324 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1324,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1324 );
                  s.setCharPositionInLine( sStartCharPos1324 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:389:53:
               // s= Q_STRING
               {
                  int sStart1330 = getCharIndex();
                  int sStartLine1330 = getLine();
                  int sStartCharPos1330 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1330,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1330 );
                  s.setCharPositionInLine( sStartCharPos1330 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalCompletedAfter( Strings.unquotify( s.getText() ) );
            }
            
            hasStatusCompletedOp = true;
            
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
   
   // $ANTLR start "OP_COMPLETED_BEFORE"
   public final void mOP_COMPLETED_BEFORE() throws RecognitionException
   {
      try
      {
         int _type = OP_COMPLETED_BEFORE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:399:21:
         // ( 'completedbefore:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:399:23:
         // 'completedbefore:' (s= STRING |s= Q_STRING )
         {
            match( "completedbefore:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:399:42:
            // (s= STRING |s= Q_STRING )
            int alt18 = 2;
            int LA18_0 = input.LA( 1 );
            
            if ( ( ( LA18_0 >= '\u0000' && LA18_0 <= '\u001F' )
               || LA18_0 == '!' || ( LA18_0 >= '#' && LA18_0 <= '&' ) || ( LA18_0 >= '*' && LA18_0 <= '\uFFFF' ) ) )
            {
               alt18 = 1;
            }
            else if ( ( LA18_0 == '\"' || LA18_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:399:44:
               // s= STRING
               {
                  int sStart1369 = getCharIndex();
                  int sStartLine1369 = getLine();
                  int sStartCharPos1369 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1369,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1369 );
                  s.setCharPositionInLine( sStartCharPos1369 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:399:55:
               // s= Q_STRING
               {
                  int sStart1375 = getCharIndex();
                  int sStartLine1375 = getLine();
                  int sStartCharPos1375 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1375,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1375 );
                  s.setCharPositionInLine( sStartCharPos1375 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalCompletedBefore( Strings.unquotify( s.getText() ) );
            }
            
            hasStatusCompletedOp = true;
            
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
   
   // $ANTLR start "OP_COMPLETED_WITHIN"
   public final void mOP_COMPLETED_WITHIN() throws RecognitionException
   {
      try
      {
         int _type = OP_COMPLETED_WITHIN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:409:21:
         // ( 'completedwithin:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:409:23:
         // 'completedwithin:' s= Q_STRING
         {
            match( "completedwithin:" );
            
            int sStart1413 = getCharIndex();
            int sStartLine1413 = getLine();
            int sStartCharPos1413 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1413,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1413 );
            s.setCharPositionInLine( sStartCharPos1413 );
            
            if ( evaluator != null )
            {
               error = !evaluator.evalCompletedWithIn( Strings.unquotify( s.getText() ) );
            }
            
            hasStatusCompletedOp = true;
            
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:419:14:
         // ( 'added:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:419:16:
         // 'added:' (s= STRING |s= Q_STRING )
         {
            match( "added:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:419:25:
            // (s= STRING |s= Q_STRING )
            int alt19 = 2;
            int LA19_0 = input.LA( 1 );
            
            if ( ( ( LA19_0 >= '\u0000' && LA19_0 <= '\u001F' )
               || LA19_0 == '!' || ( LA19_0 >= '#' && LA19_0 <= '&' ) || ( LA19_0 >= '*' && LA19_0 <= '\uFFFF' ) ) )
            {
               alt19 = 1;
            }
            else if ( ( LA19_0 == '\"' || LA19_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:419:27:
               // s= STRING
               {
                  int sStart1455 = getCharIndex();
                  int sStartLine1455 = getLine();
                  int sStartCharPos1455 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1455,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1455 );
                  s.setCharPositionInLine( sStartCharPos1455 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:419:38:
               // s= Q_STRING
               {
                  int sStart1461 = getCharIndex();
                  int sStartLine1461 = getLine();
                  int sStartCharPos1461 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1461,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1461 );
                  s.setCharPositionInLine( sStartCharPos1461 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalAdded( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_ADDED"
   
   // $ANTLR start "OP_ADDED_AFTER"
   public final void mOP_ADDED_AFTER() throws RecognitionException
   {
      try
      {
         int _type = OP_ADDED_AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:427:16:
         // ( 'addedafter:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:427:18:
         // 'addedafter:' (s= STRING |s= Q_STRING )
         {
            match( "addedafter:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:427:32:
            // (s= STRING |s= Q_STRING )
            int alt20 = 2;
            int LA20_0 = input.LA( 1 );
            
            if ( ( ( LA20_0 >= '\u0000' && LA20_0 <= '\u001F' )
               || LA20_0 == '!' || ( LA20_0 >= '#' && LA20_0 <= '&' ) || ( LA20_0 >= '*' && LA20_0 <= '\uFFFF' ) ) )
            {
               alt20 = 1;
            }
            else if ( ( LA20_0 == '\"' || LA20_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:427:34:
               // s= STRING
               {
                  int sStart1494 = getCharIndex();
                  int sStartLine1494 = getLine();
                  int sStartCharPos1494 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1494,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1494 );
                  s.setCharPositionInLine( sStartCharPos1494 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:427:45:
               // s= Q_STRING
               {
                  int sStart1500 = getCharIndex();
                  int sStartLine1500 = getLine();
                  int sStartCharPos1500 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1500,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1500 );
                  s.setCharPositionInLine( sStartCharPos1500 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalAddedAfter( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_ADDED_AFTER"
   
   // $ANTLR start "OP_ADDED_BEFORE"
   public final void mOP_ADDED_BEFORE() throws RecognitionException
   {
      try
      {
         int _type = OP_ADDED_BEFORE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:435:17:
         // ( 'addedbefore:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:435:19:
         // 'addedbefore:' (s= STRING |s= Q_STRING )
         {
            match( "addedbefore:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:435:34:
            // (s= STRING |s= Q_STRING )
            int alt21 = 2;
            int LA21_0 = input.LA( 1 );
            
            if ( ( ( LA21_0 >= '\u0000' && LA21_0 <= '\u001F' )
               || LA21_0 == '!' || ( LA21_0 >= '#' && LA21_0 <= '&' ) || ( LA21_0 >= '*' && LA21_0 <= '\uFFFF' ) ) )
            {
               alt21 = 1;
            }
            else if ( ( LA21_0 == '\"' || LA21_0 == '\'' ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:435:36:
               // s= STRING
               {
                  int sStart1535 = getCharIndex();
                  int sStartLine1535 = getLine();
                  int sStartCharPos1535 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1535,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1535 );
                  s.setCharPositionInLine( sStartCharPos1535 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:435:47:
               // s= Q_STRING
               {
                  int sStart1541 = getCharIndex();
                  int sStartLine1541 = getLine();
                  int sStartCharPos1541 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1541,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1541 );
                  s.setCharPositionInLine( sStartCharPos1541 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalAddedBefore( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_ADDED_BEFORE"
   
   // $ANTLR start "OP_ADDED_WITHIN"
   public final void mOP_ADDED_WITHIN() throws RecognitionException
   {
      try
      {
         int _type = OP_ADDED_WITHIN;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:443:17:
         // ( 'addedwithin:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:443:19:
         // 'addedwithin:' s= Q_STRING
         {
            match( "addedwithin:" );
            
            int sStart1593 = getCharIndex();
            int sStartLine1593 = getLine();
            int sStartCharPos1593 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1593,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1593 );
            s.setCharPositionInLine( sStartCharPos1593 );
            
            if ( evaluator != null )
            {
               error = !evaluator.evalAddedWithIn( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_ADDED_WITHIN"
   
   // $ANTLR start "OP_TIME_ESTIMATE"
   public final void mOP_TIME_ESTIMATE() throws RecognitionException
   {
      try
      {
         int _type = OP_TIME_ESTIMATE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken r = null;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:451:18:
         // ( 'timeestimate:' ( QUOTE r= RELATION s= STRING QUOTE |r= RELATION s= STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:451:20:
         // 'timeestimate:' ( QUOTE r= RELATION s= STRING QUOTE |r= RELATION s= STRING )
         {
            match( "timeestimate:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:451:36:
            // ( QUOTE r= RELATION s= STRING QUOTE |r= RELATION s= STRING )
            int alt22 = 2;
            int LA22_0 = input.LA( 1 );
            
            if ( ( LA22_0 == '\"' || LA22_0 == '\'' ) )
            {
               alt22 = 1;
            }
            else if ( ( ( LA22_0 >= '<' && LA22_0 <= '>' ) ) )
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:451:41:
               // QUOTE r= RELATION s= STRING QUOTE
               {
                  mQUOTE();
                  
                  int rStart1650 = getCharIndex();
                  int rStartLine1650 = getLine();
                  int rStartCharPos1650 = getCharPositionInLine();
                  mRELATION();
                  r = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       rStart1650,
                                       getCharIndex() - 1 );
                  r.setLine( rStartLine1650 );
                  r.setCharPositionInLine( rStartCharPos1650 );
                  
                  int sStart1654 = getCharIndex();
                  int sStartLine1654 = getLine();
                  int sStartCharPos1654 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1654,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1654 );
                  s.setCharPositionInLine( sStartCharPos1654 );
                  
                  mQUOTE();
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:452:41:
               // r= RELATION s= STRING
               {
                  int rStart1700 = getCharIndex();
                  int rStartLine1700 = getLine();
                  int rStartCharPos1700 = getCharPositionInLine();
                  mRELATION();
                  r = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       rStart1700,
                                       getCharIndex() - 1 );
                  r.setLine( rStartLine1700 );
                  r.setCharPositionInLine( rStartCharPos1700 );
                  
                  int sStart1704 = getCharIndex();
                  int sStartLine1704 = getLine();
                  int sStartCharPos1704 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1704,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1704 );
                  s.setCharPositionInLine( sStartCharPos1704 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               final String relation = Strings.emptyIfNull( ( r != null
                                                                       ? r.getText()
                                                                       : null ) );
               final String estimation = ( s != null ? s.getText() : null );
               
               error = !evaluator.evalTimeEstimate( relation, estimation );
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
   
   
   
   // $ANTLR end "OP_TIME_ESTIMATE"
   
   // $ANTLR start "OP_POSTPONED"
   public final void mOP_POSTPONED() throws RecognitionException
   {
      try
      {
         int _type = OP_POSTPONED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken r = null;
         CommonToken n = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:464:14:
         // ( 'postponed:' ( QUOTE r= RELATION (n= NUMBER )+ QUOTE |r= RELATION (n= NUMBER )+ | (n= NUMBER )+ ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:464:16:
         // 'postponed:' ( QUOTE r= RELATION (n= NUMBER )+ QUOTE |r= RELATION (n= NUMBER )+ | (n= NUMBER )+ )
         {
            match( "postponed:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:464:29:
            // ( QUOTE r= RELATION (n= NUMBER )+ QUOTE |r= RELATION (n= NUMBER )+ | (n= NUMBER )+ )
            int alt26 = 3;
            switch ( input.LA( 1 ) )
            {
               case '\"':
               case '\'':
               {
                  alt26 = 1;
               }
                  break;
               case '<':
               case '=':
               case '>':
               {
                  alt26 = 2;
               }
                  break;
               case '0':
               case '1':
               case '2':
               case '3':
               case '4':
               case '5':
               case '6':
               case '7':
               case '8':
               case '9':
               {
                  alt26 = 3;
               }
                  break;
               default :
                  NoViableAltException nvae = new NoViableAltException( "",
                                                                        26,
                                                                        0,
                                                                        input );
                  
                  throw nvae;
                  
            }
            
            switch ( alt26 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:464:36:
               // QUOTE r= RELATION (n= NUMBER )+ QUOTE
               {
                  mQUOTE();
                  
                  int rStart1783 = getCharIndex();
                  int rStartLine1783 = getLine();
                  int rStartCharPos1783 = getCharPositionInLine();
                  mRELATION();
                  r = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       rStart1783,
                                       getCharIndex() - 1 );
                  r.setLine( rStartLine1783 );
                  r.setCharPositionInLine( rStartCharPos1783 );
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:464:54:
                  // (n= NUMBER )+
                  int cnt23 = 0;
                  loop23: do
                  {
                     int alt23 = 2;
                     int LA23_0 = input.LA( 1 );
                     
                     if ( ( ( LA23_0 >= '0' && LA23_0 <= '9' ) ) )
                     {
                        alt23 = 1;
                     }
                     
                     switch ( alt23 )
                     {
                        case 1:
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:464:54:
                        // n= NUMBER
                        {
                           int nStart1787 = getCharIndex();
                           int nStartLine1787 = getLine();
                           int nStartCharPos1787 = getCharPositionInLine();
                           mNUMBER();
                           n = new CommonToken( input,
                                                Token.INVALID_TOKEN_TYPE,
                                                Token.DEFAULT_CHANNEL,
                                                nStart1787,
                                                getCharIndex() - 1 );
                           n.setLine( nStartLine1787 );
                           n.setCharPositionInLine( nStartCharPos1787 );
                           
                        }
                           break;
                        
                        default :
                           if ( cnt23 >= 1 )
                              break loop23;
                           EarlyExitException eee = new EarlyExitException( 23,
                                                                            input );
                           throw eee;
                     }
                     cnt23++;
                  }
                  while ( true );
                  
                  mQUOTE();
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:465:34:
               // r= RELATION (n= NUMBER )+
               {
                  int rStart1828 = getCharIndex();
                  int rStartLine1828 = getLine();
                  int rStartCharPos1828 = getCharPositionInLine();
                  mRELATION();
                  r = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       rStart1828,
                                       getCharIndex() - 1 );
                  r.setLine( rStartLine1828 );
                  r.setCharPositionInLine( rStartCharPos1828 );
                  
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:465:46:
                  // (n= NUMBER )+
                  int cnt24 = 0;
                  loop24: do
                  {
                     int alt24 = 2;
                     int LA24_0 = input.LA( 1 );
                     
                     if ( ( ( LA24_0 >= '0' && LA24_0 <= '9' ) ) )
                     {
                        alt24 = 1;
                     }
                     
                     switch ( alt24 )
                     {
                        case 1:
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:465:46:
                        // n= NUMBER
                        {
                           int nStart1832 = getCharIndex();
                           int nStartLine1832 = getLine();
                           int nStartCharPos1832 = getCharPositionInLine();
                           mNUMBER();
                           n = new CommonToken( input,
                                                Token.INVALID_TOKEN_TYPE,
                                                Token.DEFAULT_CHANNEL,
                                                nStart1832,
                                                getCharIndex() - 1 );
                           n.setLine( nStartLine1832 );
                           n.setCharPositionInLine( nStartCharPos1832 );
                           
                        }
                           break;
                        
                        default :
                           if ( cnt24 >= 1 )
                              break loop24;
                           EarlyExitException eee = new EarlyExitException( 24,
                                                                            input );
                           throw eee;
                     }
                     cnt24++;
                  }
                  while ( true );
                  
               }
                  break;
               case 3:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:466:34:
               // (n= NUMBER )+
               {
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:466:35:
                  // (n= NUMBER )+
                  int cnt25 = 0;
                  loop25: do
                  {
                     int alt25 = 2;
                     int LA25_0 = input.LA( 1 );
                     
                     if ( ( ( LA25_0 >= '0' && LA25_0 <= '9' ) ) )
                     {
                        alt25 = 1;
                     }
                     
                     switch ( alt25 )
                     {
                        case 1:
                        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:466:35:
                        // n= NUMBER
                        {
                           int nStart1870 = getCharIndex();
                           int nStartLine1870 = getLine();
                           int nStartCharPos1870 = getCharPositionInLine();
                           mNUMBER();
                           n = new CommonToken( input,
                                                Token.INVALID_TOKEN_TYPE,
                                                Token.DEFAULT_CHANNEL,
                                                nStart1870,
                                                getCharIndex() - 1 );
                           n.setLine( nStartLine1870 );
                           n.setCharPositionInLine( nStartCharPos1870 );
                           
                        }
                           break;
                        
                        default :
                           if ( cnt25 >= 1 )
                              break loop25;
                           EarlyExitException eee = new EarlyExitException( 25,
                                                                            input );
                           throw eee;
                     }
                     cnt25++;
                  }
                  while ( true );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               final String relation = Strings.emptyIfNull( ( r != null
                                                                       ? r.getText()
                                                                       : null ) );
               final int count = ( n != null ? Integer.valueOf( n.getText() )
                                            : 0 );
               
               error = !evaluator.evalPostponed( relation, count );
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
   
   
   
   // $ANTLR end "OP_POSTPONED"
   
   // $ANTLR start "OP_IS_SHARED"
   public final void mOP_IS_SHARED() throws RecognitionException
   {
      try
      {
         int _type = OP_IS_SHARED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:478:14:
         // ( 'isshared:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:478:16:
         // 'isshared:' ( TRUE | FALSE )
         {
            match( "isshared:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:479:16:
            // ( TRUE | FALSE )
            int alt27 = 2;
            int LA27_0 = input.LA( 1 );
            
            if ( ( LA27_0 == 't' ) )
            {
               alt27 = 1;
            }
            else if ( ( LA27_0 == 'f' ) )
            {
               alt27 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     27,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
            }
            switch ( alt27 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:480:19:
               // TRUE
               {
                  mTRUE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalIsShared( true );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:488:19:
               // FALSE
               {
                  mFALSE();
                  
                  if ( evaluator != null )
                  {
                     error = !evaluator.evalIsShared( false );
                  }
                  
               }
                  break;
            
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
   
   
   
   // $ANTLR end "OP_IS_SHARED"
   
   // $ANTLR start "OP_SHARED_WITH"
   public final void mOP_SHARED_WITH() throws RecognitionException
   {
      try
      {
         int _type = OP_SHARED_WITH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:497:16:
         // ( 'sharedwith:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:497:18:
         // 'sharedwith:' (s= STRING |s= Q_STRING )
         {
            match( "sharedwith:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:497:32:
            // (s= STRING |s= Q_STRING )
            int alt28 = 2;
            int LA28_0 = input.LA( 1 );
            
            if ( ( ( LA28_0 >= '\u0000' && LA28_0 <= '\u001F' )
               || LA28_0 == '!' || ( LA28_0 >= '#' && LA28_0 <= '&' ) || ( LA28_0 >= '*' && LA28_0 <= '\uFFFF' ) ) )
            {
               alt28 = 1;
            }
            else if ( ( LA28_0 == '\"' || LA28_0 == '\'' ) )
            {
               alt28 = 2;
            }
            else
            {
               NoViableAltException nvae = new NoViableAltException( "",
                                                                     28,
                                                                     0,
                                                                     input );
               
               throw nvae;
               
            }
            switch ( alt28 )
            {
               case 1:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:497:34:
               // s= STRING
               {
                  int sStart2074 = getCharIndex();
                  int sStartLine2074 = getLine();
                  int sStartCharPos2074 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart2074,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine2074 );
                  s.setCharPositionInLine( sStartCharPos2074 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:497:45:
               // s= Q_STRING
               {
                  int sStart2080 = getCharIndex();
                  int sStartLine2080 = getLine();
                  int sStartCharPos2080 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart2080,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine2080 );
                  s.setCharPositionInLine( sStartCharPos2080 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = !evaluator.evalSharedWith( Strings.unquotify( s.getText() ) );
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
   
   
   
   // $ANTLR end "OP_SHARED_WITH"
   
   // $ANTLR start "COMPLETED"
   public final void mCOMPLETED() throws RecognitionException
   {
      try
      {
         int _type = COMPLETED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:516:13:
         // ( 'completed' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:516:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:518:13:
         // ( 'incomplete' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:518:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:520:13:
         // ( 'true' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:520:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:522:13:
         // ( 'false' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:522:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:524:13:
         // ( '1' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:524:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:526:13:
         // ( '2' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:526:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:528:13:
         // ( '3' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:528:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:530:11:
         // ( 'n' | 'N' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:532:13:
         // ( '(' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:532:15:
         // '('
         {
            match( '(' );
            
            if ( evaluator != null )
            {
               error = !evaluator.evalLeftParenthesis();
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
   
   
   
   // $ANTLR end "L_PARENTH"
   
   // $ANTLR start "R_PARENTH"
   public final void mR_PARENTH() throws RecognitionException
   {
      try
      {
         int _type = R_PARENTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:540:13:
         // ( ')' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:540:15:
         // ')'
         {
            match( ')' );
            
            if ( evaluator != null )
            {
               error = !evaluator.evalRightParenthesis();
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
   
   
   
   // $ANTLR end "R_PARENTH"
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException
   {
      try
      {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:548:13:
         // ( 'and' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:548:15:
         // 'and'
         {
            match( "and" );
            
            if ( evaluator != null )
            {
               error = !evaluator.evalAnd();
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
   
   
   
   // $ANTLR end "AND"
   
   // $ANTLR start "OR"
   public final void mOR() throws RecognitionException
   {
      try
      {
         int _type = OR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:556:13:
         // ( 'or' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:556:15:
         // 'or'
         {
            match( "or" );
            
            if ( evaluator != null )
            {
               error = !evaluator.evalOr();
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
   
   
   
   // $ANTLR end "OR"
   
   // $ANTLR start "NOT"
   public final void mNOT() throws RecognitionException
   {
      try
      {
         int _type = NOT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:564:13:
         // ( 'not' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:564:15:
         // 'not'
         {
            match( "not" );
            
            if ( evaluator != null )
            {
               error = !evaluator.evalNot();
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
   
   
   
   // $ANTLR end "NOT"
   
   // $ANTLR start "QUOTE"
   public final void mQUOTE() throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:573:13:
         // ( ( '\"' | '\\'' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:
         {
            if ( input.LA( 1 ) == '\"' || input.LA( 1 ) == '\'' )
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
         
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "QUOTE"
   
   // $ANTLR start "Q_STRING"
   public final void mQ_STRING() throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:576:13:
         // ( QUOTE ( options {greedy=false; } : . )* QUOTE )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:576:15:
         // QUOTE ( options {greedy=false; } : . )* QUOTE
         {
            mQUOTE();
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:576:21:
            // ( options {greedy=false; } : . )*
            loop29: do
            {
               int alt29 = 2;
               int LA29_0 = input.LA( 1 );
               
               if ( ( LA29_0 == '\"' || LA29_0 == '\'' ) )
               {
                  alt29 = 2;
               }
               else if ( ( ( LA29_0 >= '\u0000' && LA29_0 <= '!' )
                  || ( LA29_0 >= '#' && LA29_0 <= '&' ) || ( LA29_0 >= '(' && LA29_0 <= '\uFFFF' ) ) )
               {
                  alt29 = 1;
               }
               
               switch ( alt29 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:576:50:
                  // .
                  {
                     matchAny();
                     
                  }
                     break;
                  
                  default :
                     break loop29;
               }
            }
            while ( true );
            
            mQUOTE();
            
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:579:13:
         // ( (~ ( ' ' | '(' | ')' | QUOTE ) )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:579:15:
         // (~ ( ' ' | '(' | ')' | QUOTE ) )+
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:579:15:
            // (~ ( ' ' | '(' | ')' | QUOTE ) )+
            int cnt30 = 0;
            loop30: do
            {
               int alt30 = 2;
               int LA30_0 = input.LA( 1 );
               
               if ( ( ( LA30_0 >= '\u0000' && LA30_0 <= '\u001F' )
                  || LA30_0 == '!' || ( LA30_0 >= '#' && LA30_0 <= '&' ) || ( LA30_0 >= '*' && LA30_0 <= '\uFFFF' ) ) )
               {
                  alt30 = 1;
               }
               
               switch ( alt30 )
               {
                  case 1:
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:
                  {
                     if ( ( input.LA( 1 ) >= '\u0000' && input.LA( 1 ) <= '\u001F' )
                        || input.LA( 1 ) == '!'
                        || ( input.LA( 1 ) >= '#' && input.LA( 1 ) <= '&' )
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
                     if ( cnt30 >= 1 )
                        break loop30;
                     EarlyExitException eee = new EarlyExitException( 30, input );
                     throw eee;
               }
               cnt30++;
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
   
   // $ANTLR start "NUMBER"
   public final void mNUMBER() throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:582:13:
         // ( '0' .. '9' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:
         {
            if ( ( input.LA( 1 ) >= '0' && input.LA( 1 ) <= '9' ) )
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
         
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "NUMBER"
   
   // $ANTLR start "RELATION"
   public final void mRELATION() throws RecognitionException
   {
      try
      {
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:585:13:
         // ( '<' | '>' | '=' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:
         {
            if ( ( input.LA( 1 ) >= '<' && input.LA( 1 ) <= '>' ) )
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
         
      }
      finally
      {
         // do for sure before leaving
      }
   }
   
   
   
   // $ANTLR end "RELATION"
   
   public void mTokens() throws RecognitionException
   {
      // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:8:
      // ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED |
      // OP_IS_REPEATING | OP_NAME | OP_NOTE_CONTAINS | OP_HAS_NOTES | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE |
      // OP_DUE_WITHIN | OP_COMPLETED | OP_COMPLETED_AFTER | OP_COMPLETED_BEFORE | OP_COMPLETED_WITHIN | OP_ADDED |
      // OP_ADDED_AFTER | OP_ADDED_BEFORE | OP_ADDED_WITHIN | OP_TIME_ESTIMATE | OP_POSTPONED | OP_IS_SHARED |
      // OP_SHARED_WITH | COMPLETED | INCOMPLETE | TRUE | FALSE | PRIO_HIGH | PRIO_MED | PRIO_LOW | PRIO_NONE |
      // L_PARENTH | R_PARENTH | AND | OR | NOT )
      int alt31 = 41;
      alt31 = dfa31.predict( input );
      switch ( alt31 )
      {
         case 1:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:10:
         // OP_LIST
         {
            mOP_LIST();
            
         }
            break;
         case 2:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:18:
         // OP_PRIORITY
         {
            mOP_PRIORITY();
            
         }
            break;
         case 3:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:30:
         // OP_STATUS
         {
            mOP_STATUS();
            
         }
            break;
         case 4:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:40:
         // OP_TAG
         {
            mOP_TAG();
            
         }
            break;
         case 5:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:47:
         // OP_TAG_CONTAINS
         {
            mOP_TAG_CONTAINS();
            
         }
            break;
         case 6:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:63:
         // OP_IS_TAGGED
         {
            mOP_IS_TAGGED();
            
         }
            break;
         case 7:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:76:
         // OP_LOCATION
         {
            mOP_LOCATION();
            
         }
            break;
         case 8:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:88:
         // OP_ISLOCATED
         {
            mOP_ISLOCATED();
            
         }
            break;
         case 9:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:101:
         // OP_IS_REPEATING
         {
            mOP_IS_REPEATING();
            
         }
            break;
         case 10:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:117:
         // OP_NAME
         {
            mOP_NAME();
            
         }
            break;
         case 11:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:125:
         // OP_NOTE_CONTAINS
         {
            mOP_NOTE_CONTAINS();
            
         }
            break;
         case 12:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:142:
         // OP_HAS_NOTES
         {
            mOP_HAS_NOTES();
            
         }
            break;
         case 13:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:155:
         // OP_DUE
         {
            mOP_DUE();
            
         }
            break;
         case 14:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:162:
         // OP_DUE_AFTER
         {
            mOP_DUE_AFTER();
            
         }
            break;
         case 15:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:175:
         // OP_DUE_BEFORE
         {
            mOP_DUE_BEFORE();
            
         }
            break;
         case 16:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:189:
         // OP_DUE_WITHIN
         {
            mOP_DUE_WITHIN();
            
         }
            break;
         case 17:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:203:
         // OP_COMPLETED
         {
            mOP_COMPLETED();
            
         }
            break;
         case 18:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:216:
         // OP_COMPLETED_AFTER
         {
            mOP_COMPLETED_AFTER();
            
         }
            break;
         case 19:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:235:
         // OP_COMPLETED_BEFORE
         {
            mOP_COMPLETED_BEFORE();
            
         }
            break;
         case 20:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:255:
         // OP_COMPLETED_WITHIN
         {
            mOP_COMPLETED_WITHIN();
            
         }
            break;
         case 21:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:275:
         // OP_ADDED
         {
            mOP_ADDED();
            
         }
            break;
         case 22:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:284:
         // OP_ADDED_AFTER
         {
            mOP_ADDED_AFTER();
            
         }
            break;
         case 23:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:299:
         // OP_ADDED_BEFORE
         {
            mOP_ADDED_BEFORE();
            
         }
            break;
         case 24:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:315:
         // OP_ADDED_WITHIN
         {
            mOP_ADDED_WITHIN();
            
         }
            break;
         case 25:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:331:
         // OP_TIME_ESTIMATE
         {
            mOP_TIME_ESTIMATE();
            
         }
            break;
         case 26:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:348:
         // OP_POSTPONED
         {
            mOP_POSTPONED();
            
         }
            break;
         case 27:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:361:
         // OP_IS_SHARED
         {
            mOP_IS_SHARED();
            
         }
            break;
         case 28:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:374:
         // OP_SHARED_WITH
         {
            mOP_SHARED_WITH();
            
         }
            break;
         case 29:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:389:
         // COMPLETED
         {
            mCOMPLETED();
            
         }
            break;
         case 30:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:399:
         // INCOMPLETE
         {
            mINCOMPLETE();
            
         }
            break;
         case 31:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:410:
         // TRUE
         {
            mTRUE();
            
         }
            break;
         case 32:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:415:
         // FALSE
         {
            mFALSE();
            
         }
            break;
         case 33:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:421:
         // PRIO_HIGH
         {
            mPRIO_HIGH();
            
         }
            break;
         case 34:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:431:
         // PRIO_MED
         {
            mPRIO_MED();
            
         }
            break;
         case 35:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:440:
         // PRIO_LOW
         {
            mPRIO_LOW();
            
         }
            break;
         case 36:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:449:
         // PRIO_NONE
         {
            mPRIO_NONE();
            
         }
            break;
         case 37:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:459:
         // L_PARENTH
         {
            mL_PARENTH();
            
         }
            break;
         case 38:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:469:
         // R_PARENTH
         {
            mR_PARENTH();
            
         }
            break;
         case 39:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:479:
         // AND
         {
            mAND();
            
         }
            break;
         case 40:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:483:
         // OR
         {
            mOR();
            
         }
            break;
         case 41:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:486:
         // NOT
         {
            mNOT();
            
         }
            break;
      
      }
      
   }
   
   protected DFA31 dfa31 = new DFA31( this );
   
   static final String DFA31_eotS = "\6\uffff\1\17\42\uffff\1\60\26\uffff\1\105\5\uffff";
   
   static final String DFA31_eofS = "\106\uffff";
   
   static final String DFA31_minS = "\1\50\1\151\1\157\1\150\1\141\1\156\1\141\1\uffff\1\165\1\157\1"
      + "\144\16\uffff\1\147\2\uffff\1\154\2\uffff\1\164\1\145\1\155\1\144"
      + "\1\uffff\1\72\4\uffff\1\145\1\72\1\160\1\145\10\uffff\1\154\1\144"
      + "\1\145\1\72\1\164\4\uffff\1\145\1\144\1\72\5\uffff";
   
   static final String DFA31_maxS = "\1\164\1\157\1\162\1\164\1\162\1\163\1\157\1\uffff\1\165\1\157\1"
      + "\156\16\uffff\1\147\2\uffff\1\164\2\uffff\1\164\1\145\1\155\1\144"
      + "\1\uffff\1\143\4\uffff\1\145\1\167\1\160\1\145\10\uffff\1\154\1"
      + "\144\1\145\1\167\1\164\4\uffff\1\145\1\144\1\167\5\uffff";
   
   static final String DFA31_acceptS = "\7\uffff\1\14\3\uffff\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1\50\1"
      + "\1\1\7\1\2\1\32\1\3\1\34\1\uffff\1\31\1\37\1\uffff\1\36\1\12\4\uffff"
      + "\1\47\1\uffff\1\6\1\10\1\11\1\33\4\uffff\1\4\1\5\1\13\1\51\1\15"
      + "\1\16\1\17\1\20\5\uffff\1\25\1\26\1\27\1\30\3\uffff\1\21\1\22\1"
      + "\23\1\24\1\35";
   
   static final String DFA31_specialS = "\106\uffff}>";
   
   static final String[] DFA31_transitionS =
   {
    "\1\20\1\21\7\uffff\1\14\1\15\1\16\32\uffff\1\17\22\uffff\1\12"
       + "\1\uffff\1\11\1\10\1\uffff\1\13\1\uffff\1\7\1\5\2\uffff\1\1"
       + "\1\uffff\1\6\1\22\1\2\2\uffff\1\3\1\4", "\1\23\5\uffff\1\24",
    "\1\26\2\uffff\1\25", "\1\30\13\uffff\1\27",
    "\1\31\7\uffff\1\32\10\uffff\1\33", "\1\35\4\uffff\1\34",
    "\1\36\15\uffff\1\37", "", "\1\40", "\1\41", "\1\42\11\uffff\1\43", "", "",
    "", "", "", "", "", "", "", "", "", "", "", "", "\1\44", "", "",
    "\1\46\5\uffff\1\47\1\50\1\45", "", "", "\1\51", "\1\52", "\1\53", "\1\54",
    "", "\1\55\50\uffff\1\56", "", "", "", "", "\1\57",
    "\1\61\46\uffff\1\62\1\63\24\uffff\1\64", "\1\65", "\1\66", "", "", "", "",
    "", "", "", "", "\1\67", "\1\70", "\1\71",
    "\1\72\46\uffff\1\73\1\74\24\uffff\1\75", "\1\76", "", "", "", "", "\1\77",
    "\1\100", "\1\101\46\uffff\1\102\1\103\24\uffff\1\104", "", "", "", "", "" };
   
   static final short[] DFA31_eot = DFA.unpackEncodedString( DFA31_eotS );
   
   static final short[] DFA31_eof = DFA.unpackEncodedString( DFA31_eofS );
   
   static final char[] DFA31_min = DFA.unpackEncodedStringToUnsignedChars( DFA31_minS );
   
   static final char[] DFA31_max = DFA.unpackEncodedStringToUnsignedChars( DFA31_maxS );
   
   static final short[] DFA31_accept = DFA.unpackEncodedString( DFA31_acceptS );
   
   static final short[] DFA31_special = DFA.unpackEncodedString( DFA31_specialS );
   
   static final short[][] DFA31_transition;
   
   static
   {
      int numStates = DFA31_transitionS.length;
      DFA31_transition = new short[ numStates ][];
      for ( int i = 0; i < numStates; i++ )
      {
         DFA31_transition[ i ] = DFA.unpackEncodedString( DFA31_transitionS[ i ] );
      }
   }
   
   
   class DFA31 extends DFA
   {
      
      public DFA31( BaseRecognizer recognizer )
      {
         this.recognizer = recognizer;
         this.decisionNumber = 31;
         this.eot = DFA31_eot;
         this.eof = DFA31_eof;
         this.min = DFA31_min;
         this.max = DFA31_max;
         this.accept = DFA31_accept;
         this.special = DFA31_special;
         this.transition = DFA31_transition;
      }
      
      
      
      public String getDescription()
      {
         return "1:1: Tokens : ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED | OP_IS_REPEATING | OP_NAME | OP_NOTE_CONTAINS | OP_HAS_NOTES | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE | OP_DUE_WITHIN | OP_COMPLETED | OP_COMPLETED_AFTER | OP_COMPLETED_BEFORE | OP_COMPLETED_WITHIN | OP_ADDED | OP_ADDED_AFTER | OP_ADDED_BEFORE | OP_ADDED_WITHIN | OP_TIME_ESTIMATE | OP_POSTPONED | OP_IS_SHARED | OP_SHARED_WITH | COMPLETED | INCOMPLETE | TRUE | FALSE | PRIO_HIGH | PRIO_MED | PRIO_LOW | PRIO_NONE | L_PARENTH | R_PARENTH | AND | OR | NOT );";
      }
   }
   
}
