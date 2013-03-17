// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g 2013-03-16 18:28:52

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
   
   private IRtmSmartFilterEvaluator evaluator;
   
   // STATUS VARIABLES
   
   private boolean hasStatusCompletedOp = false;
   
   private boolean error = false;
   
   
   
   public void setEvaluator( IRtmSmartFilterEvaluator evaluator )
   {
      this.evaluator = evaluator;
   }
   
   
   
   public void startEvaluation() throws RecognitionException
   {
      if ( !error )
      {
         hasStatusCompletedOp = false;
         
         while ( !error && nextToken() != Token.EOF_TOKEN )
         {
         }
      }
      
      if ( error )
      {
         throw new RecognitionException();
      }
   }
   
   
   
   @Override
   public void reset()
   {
      super.reset();
      
      hasStatusCompletedOp = false;
      error = false;
   }
   
   
   
   @Override
   public void reportError( RecognitionException e )
   {
      super.reportError( e );
      error = true;
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:171:13:
         // ( 'list:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:171:16:
         // 'list:' (s= STRING |s= Q_STRING )
         {
            match( "list:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:171:24:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:171:26:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:171:37:
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
            
            if ( evaluator != null )
            {
               error = evaluator.evalList( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:179:13:
         // ( 'priority:' (p= PRIO_HIGH |p= PRIO_MED |p= PRIO_LOW |p= PRIO_NONE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:179:16:
         // 'priority:' (p= PRIO_HIGH |p= PRIO_MED |p= PRIO_LOW |p= PRIO_NONE )
         {
            match( "priority:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:179:28:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:179:30:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:179:44:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:179:57:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:179:70:
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
            
            if ( evaluator != null )
            {
               error = evaluator.evalPriority( p.getText() );
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:187:13:
         // ( 'status:' ( COMPLETED | INCOMPLETE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:187:16:
         // 'status:' ( COMPLETED | INCOMPLETE )
         {
            match( "status:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:188:16:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:189:19:
               // COMPLETED
               {
                  mCOMPLETED();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalStatus( true );
                  }
                  
                  hasStatusCompletedOp = true;
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:199:19:
               // INCOMPLETE
               {
                  mINCOMPLETE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalStatus( false );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:208:13:
         // ( 'tag:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:208:15:
         // 'tag:' (s= STRING |s= Q_STRING )
         {
            match( "tag:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:208:22:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:208:24:
               // s= STRING
               {
                  int sStart294 = getCharIndex();
                  int sStartLine294 = getLine();
                  int sStartCharPos294 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart294,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine294 );
                  s.setCharPositionInLine( sStartCharPos294 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:208:35:
               // s= Q_STRING
               {
                  int sStart300 = getCharIndex();
                  int sStartLine300 = getLine();
                  int sStartCharPos300 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart300,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine300 );
                  s.setCharPositionInLine( sStartCharPos300 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalTag( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:216:17:
         // ( 'tagcontains:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:216:19:
         // 'tagcontains:' (s= STRING |s= Q_STRING )
         {
            match( "tagcontains:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:216:34:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:216:36:
               // s= STRING
               {
                  int sStart332 = getCharIndex();
                  int sStartLine332 = getLine();
                  int sStartCharPos332 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart332,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine332 );
                  s.setCharPositionInLine( sStartCharPos332 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:216:47:
               // s= Q_STRING
               {
                  int sStart338 = getCharIndex();
                  int sStartLine338 = getLine();
                  int sStartCharPos338 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart338,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine338 );
                  s.setCharPositionInLine( sStartCharPos338 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalTagContains( s.getText() );
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:224:17:
         // ( 'istagged:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:224:19:
         // 'istagged:' ( TRUE | FALSE )
         {
            match( "istagged:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:225:19:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:226:22:
               // TRUE
               {
                  mTRUE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalIsTagged( true );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:234:22:
               // FALSE
               {
                  mFALSE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalIsTagged( false );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:243:13:
         // ( 'location:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:243:15:
         // 'location:' (s= STRING |s= Q_STRING )
         {
            match( "location:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:243:27:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:243:29:
               // s= STRING
               {
                  int sStart540 = getCharIndex();
                  int sStartLine540 = getLine();
                  int sStartCharPos540 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart540,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine540 );
                  s.setCharPositionInLine( sStartCharPos540 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:243:40:
               // s= Q_STRING
               {
                  int sStart546 = getCharIndex();
                  int sStartLine546 = getLine();
                  int sStartCharPos546 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart546,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine546 );
                  s.setCharPositionInLine( sStartCharPos546 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalLocation( s.getText() );
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:253:14:
         // ( 'islocated:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:253:16:
         // 'islocated:' ( TRUE | FALSE )
         {
            match( "islocated:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:254:16:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:255:19:
               // TRUE
               {
                  mTRUE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalIsLocated( true );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:263:19:
               // FALSE
               {
                  mFALSE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalIsLocated( false );
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:272:17:
         // ( 'isrepeating:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:272:19:
         // 'isrepeating:' ( TRUE | FALSE )
         {
            match( "isrepeating:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:273:19:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:274:22:
               // TRUE
               {
                  mTRUE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalIsRepeating( true );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:282:22:
               // FALSE
               {
                  mFALSE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalIsRepeating( false );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:291:14:
         // ( 'name:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:291:16:
         // 'name:' (s= STRING |s= Q_STRING )
         {
            match( "name:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:291:24:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:291:26:
               // s= STRING
               {
                  int sStart891 = getCharIndex();
                  int sStartLine891 = getLine();
                  int sStartCharPos891 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart891,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine891 );
                  s.setCharPositionInLine( sStartCharPos891 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:291:37:
               // s= Q_STRING
               {
                  int sStart897 = getCharIndex();
                  int sStartLine897 = getLine();
                  int sStartCharPos897 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart897,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine897 );
                  s.setCharPositionInLine( sStartCharPos897 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalTaskName( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:299:18:
         // ( 'notecontains:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:299:20:
         // 'notecontains:' (s= STRING |s= Q_STRING )
         {
            match( "notecontains:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:299:36:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:299:38:
               // s= STRING
               {
                  int sStart930 = getCharIndex();
                  int sStartLine930 = getLine();
                  int sStartCharPos930 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart930,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine930 );
                  s.setCharPositionInLine( sStartCharPos930 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:299:49:
               // s= Q_STRING
               {
                  int sStart936 = getCharIndex();
                  int sStartLine936 = getLine();
                  int sStartCharPos936 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart936,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine936 );
                  s.setCharPositionInLine( sStartCharPos936 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalNoteContains( s.getText() );
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:307:14:
         // ( 'hasnotes:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:307:16:
         // 'hasnotes:' ( TRUE | FALSE )
         {
            match( "hasnotes:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:308:16:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:309:19:
               // TRUE
               {
                  mTRUE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalHasNotes( true );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:317:19:
               // FALSE
               {
                  mFALSE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalHasNotes( false );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:326:13:
         // ( 'due:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:326:16:
         // 'due:' (s= STRING |s= Q_STRING )
         {
            match( "due:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:326:23:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:326:25:
               // s= STRING
               {
                  int sStart1121 = getCharIndex();
                  int sStartLine1121 = getLine();
                  int sStartCharPos1121 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1121,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1121 );
                  s.setCharPositionInLine( sStartCharPos1121 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:326:36:
               // s= Q_STRING
               {
                  int sStart1127 = getCharIndex();
                  int sStartLine1127 = getLine();
                  int sStartCharPos1127 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1127,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1127 );
                  s.setCharPositionInLine( sStartCharPos1127 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalDue( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:334:14:
         // ( 'dueafter:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:334:16:
         // 'dueafter:' (s= STRING |s= Q_STRING )
         {
            match( "dueafter:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:334:28:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:334:30:
               // s= STRING
               {
                  int sStart1160 = getCharIndex();
                  int sStartLine1160 = getLine();
                  int sStartCharPos1160 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1160,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1160 );
                  s.setCharPositionInLine( sStartCharPos1160 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:334:41:
               // s= Q_STRING
               {
                  int sStart1166 = getCharIndex();
                  int sStartLine1166 = getLine();
                  int sStartCharPos1166 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1166,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1166 );
                  s.setCharPositionInLine( sStartCharPos1166 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalDueAfter( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:342:15:
         // ( 'duebefore:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:342:17:
         // 'duebefore:' (s= STRING |s= Q_STRING )
         {
            match( "duebefore:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:342:30:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:342:32:
               // s= STRING
               {
                  int sStart1199 = getCharIndex();
                  int sStartLine1199 = getLine();
                  int sStartCharPos1199 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1199,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1199 );
                  s.setCharPositionInLine( sStartCharPos1199 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:342:43:
               // s= Q_STRING
               {
                  int sStart1205 = getCharIndex();
                  int sStartLine1205 = getLine();
                  int sStartCharPos1205 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1205,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1205 );
                  s.setCharPositionInLine( sStartCharPos1205 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalDueBefore( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:350:15:
         // ( 'duewithin:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:350:17:
         // 'duewithin:' s= Q_STRING
         {
            match( "duewithin:" );
            
            int sStart1237 = getCharIndex();
            int sStartLine1237 = getLine();
            int sStartCharPos1237 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1237,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1237 );
            s.setCharPositionInLine( sStartCharPos1237 );
            
            if ( evaluator != null )
            {
               error = evaluator.evalDueWithIn( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:358:14:
         // ( 'completed:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:358:16:
         // 'completed:' (s= STRING |s= Q_STRING )
         {
            match( "completed:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:358:29:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:358:31:
               // s= STRING
               {
                  int sStart1269 = getCharIndex();
                  int sStartLine1269 = getLine();
                  int sStartCharPos1269 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1269,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1269 );
                  s.setCharPositionInLine( sStartCharPos1269 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:358:42:
               // s= Q_STRING
               {
                  int sStart1275 = getCharIndex();
                  int sStartLine1275 = getLine();
                  int sStartCharPos1275 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1275,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1275 );
                  s.setCharPositionInLine( sStartCharPos1275 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalCompleted( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:368:20:
         // ( 'completedafter:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:368:22:
         // 'completedafter:' (s= STRING |s= Q_STRING )
         {
            match( "completedafter:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:368:40:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:368:42:
               // s= STRING
               {
                  int sStart1323 = getCharIndex();
                  int sStartLine1323 = getLine();
                  int sStartCharPos1323 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1323,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1323 );
                  s.setCharPositionInLine( sStartCharPos1323 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:368:53:
               // s= Q_STRING
               {
                  int sStart1329 = getCharIndex();
                  int sStartLine1329 = getLine();
                  int sStartCharPos1329 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1329,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1329 );
                  s.setCharPositionInLine( sStartCharPos1329 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalCompletedAfter( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:378:21:
         // ( 'completedbefore:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:378:23:
         // 'completedbefore:' (s= STRING |s= Q_STRING )
         {
            match( "completedbefore:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:378:42:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:378:44:
               // s= STRING
               {
                  int sStart1368 = getCharIndex();
                  int sStartLine1368 = getLine();
                  int sStartCharPos1368 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1368,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1368 );
                  s.setCharPositionInLine( sStartCharPos1368 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:378:55:
               // s= Q_STRING
               {
                  int sStart1374 = getCharIndex();
                  int sStartLine1374 = getLine();
                  int sStartCharPos1374 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1374,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1374 );
                  s.setCharPositionInLine( sStartCharPos1374 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalCompletedBefore( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:388:21:
         // ( 'completedwithin:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:388:23:
         // 'completedwithin:' s= Q_STRING
         {
            match( "completedwithin:" );
            
            int sStart1412 = getCharIndex();
            int sStartLine1412 = getLine();
            int sStartCharPos1412 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1412,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1412 );
            s.setCharPositionInLine( sStartCharPos1412 );
            
            if ( evaluator != null )
            {
               error = evaluator.evalCompletedWithIn( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:398:14:
         // ( 'added:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:398:16:
         // 'added:' (s= STRING |s= Q_STRING )
         {
            match( "added:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:398:25:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:398:27:
               // s= STRING
               {
                  int sStart1454 = getCharIndex();
                  int sStartLine1454 = getLine();
                  int sStartCharPos1454 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1454,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1454 );
                  s.setCharPositionInLine( sStartCharPos1454 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:398:38:
               // s= Q_STRING
               {
                  int sStart1460 = getCharIndex();
                  int sStartLine1460 = getLine();
                  int sStartCharPos1460 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1460,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1460 );
                  s.setCharPositionInLine( sStartCharPos1460 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalAdded( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:406:16:
         // ( 'addedafter:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:406:18:
         // 'addedafter:' (s= STRING |s= Q_STRING )
         {
            match( "addedafter:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:406:32:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:406:34:
               // s= STRING
               {
                  int sStart1493 = getCharIndex();
                  int sStartLine1493 = getLine();
                  int sStartCharPos1493 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1493,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1493 );
                  s.setCharPositionInLine( sStartCharPos1493 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:406:45:
               // s= Q_STRING
               {
                  int sStart1499 = getCharIndex();
                  int sStartLine1499 = getLine();
                  int sStartCharPos1499 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1499,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1499 );
                  s.setCharPositionInLine( sStartCharPos1499 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalAddedAfter( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:414:17:
         // ( 'addedbefore:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:414:19:
         // 'addedbefore:' (s= STRING |s= Q_STRING )
         {
            match( "addedbefore:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:414:34:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:414:36:
               // s= STRING
               {
                  int sStart1534 = getCharIndex();
                  int sStartLine1534 = getLine();
                  int sStartCharPos1534 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1534,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1534 );
                  s.setCharPositionInLine( sStartCharPos1534 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:414:47:
               // s= Q_STRING
               {
                  int sStart1540 = getCharIndex();
                  int sStartLine1540 = getLine();
                  int sStartCharPos1540 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1540,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1540 );
                  s.setCharPositionInLine( sStartCharPos1540 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalAddedBefore( s.getText() );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:422:17:
         // ( 'addedwithin:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:422:19:
         // 'addedwithin:' s= Q_STRING
         {
            match( "addedwithin:" );
            
            int sStart1574 = getCharIndex();
            int sStartLine1574 = getLine();
            int sStartCharPos1574 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1574,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1574 );
            s.setCharPositionInLine( sStartCharPos1574 );
            
            if ( evaluator != null )
            {
               error = evaluator.evalAddedWithIn( s.getText() );
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
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:430:18:
         // ( 'timeestimate:' s= Q_STRING )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:430:20:
         // 'timeestimate:' s= Q_STRING
         {
            match( "timeestimate:" );
            
            int sStart1606 = getCharIndex();
            int sStartLine1606 = getLine();
            int sStartCharPos1606 = getCharPositionInLine();
            mQ_STRING();
            s = new CommonToken( input,
                                 Token.INVALID_TOKEN_TYPE,
                                 Token.DEFAULT_CHANNEL,
                                 sStart1606,
                                 getCharIndex() - 1 );
            s.setLine( sStartLine1606 );
            s.setCharPositionInLine( sStartCharPos1606 );
            
            if ( evaluator != null )
            {
               error = evaluator.evalTimeEstimate( s.getText() );
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
         CommonToken s = null;
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:438:14:
         // ( 'postponed:' s= STRING |s= Q_STRING )
         int alt22 = 2;
         int LA22_0 = input.LA( 1 );
         
         if ( ( LA22_0 == 'p' ) )
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:438:16:
            // 'postponed:' s= STRING
            {
               match( "postponed:" );
               
               int sStart1639 = getCharIndex();
               int sStartLine1639 = getLine();
               int sStartCharPos1639 = getCharPositionInLine();
               mSTRING();
               s = new CommonToken( input,
                                    Token.INVALID_TOKEN_TYPE,
                                    Token.DEFAULT_CHANNEL,
                                    sStart1639,
                                    getCharIndex() - 1 );
               s.setLine( sStartLine1639 );
               s.setCharPositionInLine( sStartCharPos1639 );
               
            }
               break;
            case 2:
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:438:40:
            // s= Q_STRING
            {
               int sStart1645 = getCharIndex();
               int sStartLine1645 = getLine();
               int sStartCharPos1645 = getCharPositionInLine();
               mQ_STRING();
               s = new CommonToken( input,
                                    Token.INVALID_TOKEN_TYPE,
                                    Token.DEFAULT_CHANNEL,
                                    sStart1645,
                                    getCharIndex() - 1 );
               s.setLine( sStartLine1645 );
               s.setCharPositionInLine( sStartCharPos1645 );
               
               if ( evaluator != null )
               {
                  error = evaluator.evalPostponed( s.getText() );
               }
               
            }
               break;
         
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:446:14:
         // ( 'isshared:' ( TRUE | FALSE ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:446:16:
         // 'isshared:' ( TRUE | FALSE )
         {
            match( "isshared:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:447:16:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:448:19:
               // TRUE
               {
                  mTRUE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalIsShared( true );
                  }
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:456:19:
               // FALSE
               {
                  mFALSE();
                  
                  if ( evaluator != null )
                  {
                     error = evaluator.evalIsShared( false );
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
         
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:465:16:
         // ( 'sharedwith:' (s= STRING |s= Q_STRING ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:465:18:
         // 'sharedwith:' (s= STRING |s= Q_STRING )
         {
            match( "sharedwith:" );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:465:32:
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
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:465:34:
               // s= STRING
               {
                  int sStart1818 = getCharIndex();
                  int sStartLine1818 = getLine();
                  int sStartCharPos1818 = getCharPositionInLine();
                  mSTRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1818,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1818 );
                  s.setCharPositionInLine( sStartCharPos1818 );
                  
               }
                  break;
               case 2:
               // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:465:45:
               // s= Q_STRING
               {
                  int sStart1824 = getCharIndex();
                  int sStartLine1824 = getLine();
                  int sStartCharPos1824 = getCharPositionInLine();
                  mQ_STRING();
                  s = new CommonToken( input,
                                       Token.INVALID_TOKEN_TYPE,
                                       Token.DEFAULT_CHANNEL,
                                       sStart1824,
                                       getCharIndex() - 1 );
                  s.setLine( sStartLine1824 );
                  s.setCharPositionInLine( sStartCharPos1824 );
                  
               }
                  break;
            
            }
            
            if ( evaluator != null )
            {
               error = evaluator.evalSharedWith( s.getText() );
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:484:13:
         // ( 'completed' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:484:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:486:13:
         // ( 'incomplete' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:486:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:488:13:
         // ( 'true' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:488:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:490:13:
         // ( 'false' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:490:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:492:13:
         // ( '1' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:492:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:494:13:
         // ( '2' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:494:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:496:13:
         // ( '3' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:496:15:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:498:11:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:500:13:
         // ( '(' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:500:15:
         // '('
         {
            match( '(' );
            
            if ( evaluator != null )
            {
               error = evaluator.evalLeftParenthesis();
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:508:13:
         // ( ')' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:508:15:
         // ')'
         {
            match( ')' );
            
            if ( evaluator != null )
            {
               error = evaluator.evalRightParenthesis();
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:516:13:
         // ( 'and' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:516:15:
         // 'and'
         {
            match( "and" );
            
            if ( evaluator != null )
            {
               error = evaluator.evalAnd();
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:524:13:
         // ( 'or' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:524:15:
         // 'or'
         {
            match( "or" );
            
            if ( evaluator != null )
            {
               error = evaluator.evalOr();
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:532:13:
         // ( 'not' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:532:15:
         // 'not'
         {
            match( "not" );
            
            if ( evaluator != null )
            {
               error = evaluator.evalNot();
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
   
   // $ANTLR start "WS"
   public final void mWS() throws RecognitionException
   {
      try
      {
         int _type = WS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:540:13:
         // ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:540:17:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:547:13:
         // ( '\"' (~ ( '\"' ) )* '\"' )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:547:15:
         // '\"' (~ ( '\"' ) )* '\"'
         {
            match( '\"' );
            
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:547:19:
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
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:
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
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:550:13:
         // ( (~ ( '\"' | ' ' | '(' | ')' ) )+ )
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:550:15:
         // (~ ( '\"' | ' ' | '(' | ')' ) )+
         {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:550:15:
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
                  // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:
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
      // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:8:
      // ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED |
      // OP_IS_REPEATING | OP_NAME | OP_NOTE_CONTAINS | OP_HAS_NOTES | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE |
      // OP_DUE_WITHIN | OP_COMPLETED | OP_COMPLETED_AFTER | OP_COMPLETED_BEFORE | OP_COMPLETED_WITHIN | OP_ADDED |
      // OP_ADDED_AFTER | OP_ADDED_BEFORE | OP_ADDED_WITHIN | OP_TIME_ESTIMATE | OP_POSTPONED | OP_IS_SHARED |
      // OP_SHARED_WITH | COMPLETED | INCOMPLETE | TRUE | FALSE | PRIO_HIGH | PRIO_MED | PRIO_LOW | PRIO_NONE |
      // L_PARENTH | R_PARENTH | AND | OR | NOT | WS )
      int alt27 = 42;
      alt27 = dfa27.predict( input );
      switch ( alt27 )
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
         case 42:
         // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\rtmsmart\\RtmSmartFilterLexer.g:1:490:
         // WS
         {
            mWS();
            
         }
            break;
      
      }
      
   }
   
   protected DFA27 dfa27 = new DFA27( this );
   
   static final String DFA27_eotS = "\6\uffff\1\20\43\uffff\1\61\26\uffff\1\106\5\uffff";
   
   static final String DFA27_eofS = "\107\uffff";
   
   static final String DFA27_minS = "\1\11\1\151\1\157\1\150\1\141\1\156\1\141\1\uffff\1\165\1\157\1"
      + "\144\17\uffff\1\147\2\uffff\1\154\2\uffff\1\164\1\145\1\155\1\144"
      + "\1\uffff\1\72\4\uffff\1\145\1\72\1\160\1\145\10\uffff\1\154\1\144"
      + "\1\145\1\72\1\164\4\uffff\1\145\1\144\1\72\5\uffff";
   
   static final String DFA27_maxS = "\1\164\1\157\1\162\1\164\1\162\1\163\1\157\1\uffff\1\165\1\157\1"
      + "\156\17\uffff\1\147\2\uffff\1\164\2\uffff\1\164\1\145\1\155\1\144"
      + "\1\uffff\1\143\4\uffff\1\145\1\167\1\160\1\145\10\uffff\1\154\1"
      + "\144\1\145\1\167\1\164\4\uffff\1\145\1\144\1\167\5\uffff";
   
   static final String DFA27_acceptS = "\7\uffff\1\14\3\uffff\1\32\1\40\1\41\1\42\1\43\1\44\1\45\1\46\1"
      + "\50\1\52\1\1\1\7\1\2\1\3\1\34\1\uffff\1\31\1\37\1\uffff\1\36\1\12"
      + "\4\uffff\1\47\1\uffff\1\6\1\10\1\11\1\33\4\uffff\1\4\1\5\1\13\1"
      + "\51\1\15\1\16\1\17\1\20\5\uffff\1\25\1\26\1\27\1\30\3\uffff\1\21"
      + "\1\22\1\23\1\24\1\35";
   
   static final String DFA27_specialS = "\107\uffff}>";
   
   static final String[] DFA27_transitionS =
   {
    "\2\24\2\uffff\1\24\22\uffff\1\24\1\uffff\1\13\5\uffff\1\21\1"
       + "\22\7\uffff\1\15\1\16\1\17\32\uffff\1\20\22\uffff\1\12\1\uffff"
       + "\1\11\1\10\1\uffff\1\14\1\uffff\1\7\1\5\2\uffff\1\1\1\uffff"
       + "\1\6\1\23\1\2\2\uffff\1\3\1\4", "\1\25\5\uffff\1\26",
    "\1\13\2\uffff\1\27", "\1\31\13\uffff\1\30",
    "\1\32\7\uffff\1\33\10\uffff\1\34", "\1\36\4\uffff\1\35",
    "\1\37\15\uffff\1\40", "", "\1\41", "\1\42", "\1\43\11\uffff\1\44", "", "",
    "", "", "", "", "", "", "", "", "", "", "", "", "", "\1\45", "", "",
    "\1\47\5\uffff\1\50\1\51\1\46", "", "", "\1\52", "\1\53", "\1\54", "\1\55",
    "", "\1\56\50\uffff\1\57", "", "", "", "", "\1\60",
    "\1\62\46\uffff\1\63\1\64\24\uffff\1\65", "\1\66", "\1\67", "", "", "", "",
    "", "", "", "", "\1\70", "\1\71", "\1\72",
    "\1\73\46\uffff\1\74\1\75\24\uffff\1\76", "\1\77", "", "", "", "",
    "\1\100", "\1\101", "\1\102\46\uffff\1\103\1\104\24\uffff\1\105", "", "",
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
         return "1:1: Tokens : ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED | OP_IS_REPEATING | OP_NAME | OP_NOTE_CONTAINS | OP_HAS_NOTES | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE | OP_DUE_WITHIN | OP_COMPLETED | OP_COMPLETED_AFTER | OP_COMPLETED_BEFORE | OP_COMPLETED_WITHIN | OP_ADDED | OP_ADDED_AFTER | OP_ADDED_BEFORE | OP_ADDED_WITHIN | OP_TIME_ESTIMATE | OP_POSTPONED | OP_IS_SHARED | OP_SHARED_WITH | COMPLETED | INCOMPLETE | TRUE | FALSE | PRIO_HIGH | PRIO_MED | PRIO_LOW | PRIO_NONE | L_PARENTH | R_PARENTH | AND | OR | NOT | WS );";
      }
   }
   
}
