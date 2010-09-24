/*
Copyright (c) 2010 Ronny Röhricht

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny Röhricht - implementation
 */

// $ANTLR 3.2 Sep 23, 2009 12:02:23 F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g 2010-09-21 09:36:24

package dev.drsoran.moloko.grammar;

import java.util.Calendar;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;

import dev.drsoran.moloko.MolokoApp;
import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.Tasks;


public class RtmSmartFilterLexer extends Lexer {
   public static final int OP_IS_TAGGED=13;
   public static final int OP_DUE=19;
   public static final int OP_DUE_BEFORE=21;
   public static final int OP_HAS_NOTES=18;
   public static final int OP_COMPLETED_AFTER=24;
   public static final int NOT=33;
   public static final int AND=31;
   public static final int EOF=-1;
   public static final int TRUE=11;
   public static final int OP_ADDED_BEFORE=26;
   public static final int OP_PRIORITY=7;
   public static final int OP_TAG=9;
   public static final int OP_DUE_AFTER=20;
   public static final int Q_STRING=5;
   public static final int OP_TAG_CONTAINS=10;
   public static final int R_PARENTH=30;
   public static final int OP_ADDED_AFTER=27;
   public static final int OP_COMPLETED_BEFORE=23;
   public static final int OP_POSTPONED=28;
   public static final int OP_COMPLETED=22;
   public static final int OP_LIST=6;
   public static final int WS=34;
   public static final int L_PARENTH=29;
   public static final int OP_STATUS=8;
   public static final int OP_LOCATION=14;
   public static final int OR=32;
   public static final int OP_NOTE_CONTAINS=17;
   public static final int OP_NAME=16;
   public static final int FALSE=12;
   public static final int OP_ADDED=25;
   public static final int OP_ISLOCATED=15;
   public static final int STRING=4;
   
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
   
   public final static String	TRUE_LIT = "true";
   
   public final static String FALSE_LIT = "false";
   
   public final static String L_PARENTH_LIT = "(";
   
   public final static String R_PARENTH_LIT = ")";
   
   public final static String AND_LIT = "and";
   
   public final static String OR_LIT = "or";
   
   public final static String NOT_LIT = "not";
   
   // END TOKEN LITERALS
   
   private final static String TAGS_QUERY_PREFIX
   = "(SELECT "  + Tags.TASKSERIES_ID + " FROM " + Tags.PATH
   + " WHERE " + Tags.TASKSERIES_ID + " = " + "subQuery." + Tasks._ID;
   
   private final StringBuffer result = new StringBuffer();
   
   private final int dateformat = MolokoApp.getSettings().getDateformat();
   
   
   
   private Calendar parseDateTimeSpec( String spec )
   {
      final TimeSpecLexer     lexer       = new TimeSpecLexer( new ANTLRNoCaseStringStream( spec ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeSpecParser    parser      = new TimeSpecParser( antlrTokens );
      
      final Calendar cal = TimeSpecParser.getLocalizedCalendar();
      
      // first try to parse time
      try
      {
         parser.time_spec( cal );
         return cal;
      }
      catch( RecognitionException e )
      {
         // try to parse date and time
         try
         {
            parser.parseDateTime( cal, dateformat );
            return cal;
         }
         catch( RecognitionException e1 )
         {
            return null;
         }
      }
   }
   
   
   
   public static final String unquotify( String input )
   {
      return input.replaceAll( "(\"|')", "" );
   }
   
   
   
   public static final String quotify( String input )
   {
      return new StringBuffer( "\"").append( input ).append( "\"").toString();
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
      final Calendar cal = parseDateTimeSpec( unquotify( param ) );
      
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
   }
   
   
   
   private void differsTimeParam( String column, String param, boolean before )
   {
      final Calendar cal = parseDateTimeSpec( unquotify( param ) );
      
      if ( cal != null )
      {
         result.append( column );
         result.append( ( before ) ? " < " : " > " );
         result.append( cal.getTimeInMillis() );
      }
   }
   
   
   
   public String getResult() throws RecognitionException
   {
      if ( result.length() == 0 )
         while ( nextToken() != Token.EOF_TOKEN )
         {
         }
      
      return result.toString();
   }
   
   
   // delegates
   // delegators
   
   public RtmSmartFilterLexer() {;}
   public RtmSmartFilterLexer(CharStream input) {
      this(input, new RecognizerSharedState());
   }
   public RtmSmartFilterLexer(CharStream input, RecognizerSharedState state) {
      super(input,state);
      
   }
   @Override
   public String getGrammarFileName() { return "F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g"; }
   
   // $ANTLR start "OP_LIST"
   public final void mOP_LIST() throws RecognitionException {
      try {
         int _type = OP_LIST;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:251:13: ( 'list:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:251:16: 'list:' (s= STRING | s= Q_STRING )
         {
            match("list:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:251:24: (s= STRING | s= Q_STRING )
            int alt1=2;
            int LA1_0 = input.LA(1);
            
            if ( ((LA1_0>='\u0000' && LA1_0<='\u001F')||LA1_0=='!'||(LA1_0>='#' && LA1_0<='\'')||(LA1_0>='*' && LA1_0<='\uFFFF')) ) {
               alt1=1;
            }
            else if ( (LA1_0=='\"') ) {
               alt1=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 1, 0, input);
               
               throw nvae;
            }
            switch (alt1) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:251:26: s= STRING
               {
                  int sStart51 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart51, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:251:37: s= Q_STRING
               {
                  int sStart57 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart57, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            result.append( Tasks.LIST_NAME );
            equalsStringParam( s.getText() );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_LIST"
   
   // $ANTLR start "OP_PRIORITY"
   public final void mOP_PRIORITY() throws RecognitionException {
      try {
         int _type = OP_PRIORITY;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:257:13: ( 'priority:' s= STRING )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:257:16: 'priority:' s= STRING
         {
            match("priority:");
            
            int sStart79 = getCharIndex();
            mSTRING();
            s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart79, getCharIndex()-1);
            
            result.append( Tasks.PRIORITY );
            equalsStringParam( firstCharOf( unquotify( s.getText() ) ) );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_PRIORITY"
   
   // $ANTLR start "OP_STATUS"
   public final void mOP_STATUS() throws RecognitionException {
      try {
         int _type = OP_STATUS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:263:12: ( 'status:' ( 'completed' | 'incomplete' ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:263:15: 'status:' ( 'completed' | 'incomplete' )
         {
            match("status:");
            
            
            result.append( Tasks.COMPLETED_DATE );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:267:6: ( 'completed' | 'incomplete' )
            int alt2=2;
            int LA2_0 = input.LA(1);
            
            if ( (LA2_0=='c') ) {
               alt2=1;
            }
            else if ( (LA2_0=='i') ) {
               alt2=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 2, 0, input);
               
               throw nvae;
            }
            switch (alt2) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:268:7: 'completed'
               {
                  match("completed");
                  
                  
                  result.append(" IS NOT NULL");
                  
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:273:7: 'incomplete'
               {
                  match("incomplete");
                  
                  
                  result.append(" IS NULL");
                  
                  
               }
               break;
               
            }
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_STATUS"
   
   // $ANTLR start "OP_TAG"
   public final void mOP_TAG() throws RecognitionException {
      try {
         int _type = OP_TAG;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:279:13: ( 'tag:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:279:15: 'tag:' (s= STRING | s= Q_STRING )
         {
            match("tag:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:279:22: (s= STRING | s= Q_STRING )
            int alt3=2;
            int LA3_0 = input.LA(1);
            
            if ( ((LA3_0>='\u0000' && LA3_0<='\u001F')||LA3_0=='!'||(LA3_0>='#' && LA3_0<='\'')||(LA3_0>='*' && LA3_0<='\uFFFF')) ) {
               alt3=1;
            }
            else if ( (LA3_0=='\"') ) {
               alt3=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 3, 0, input);
               
               throw nvae;
            }
            switch (alt3) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:279:24: s= STRING
               {
                  int sStart194 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart194, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:279:35: s= Q_STRING
               {
                  int sStart200 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart200, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            result.append( TAGS_QUERY_PREFIX )
            .append( " AND " )
            .append( Tags.TAG );
            equalsStringParam( s.getText() );
            result.append( ")" );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_TAG"
   
   // $ANTLR start "OP_TAG_CONTAINS"
   public final void mOP_TAG_CONTAINS() throws RecognitionException {
      try {
         int _type = OP_TAG_CONTAINS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:288:17: ( 'tagcontains:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:288:19: 'tagcontains:' (s= STRING | s= Q_STRING )
         {
            match("tagcontains:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:288:34: (s= STRING | s= Q_STRING )
            int alt4=2;
            int LA4_0 = input.LA(1);
            
            if ( ((LA4_0>='\u0000' && LA4_0<='\u001F')||LA4_0=='!'||(LA4_0>='#' && LA4_0<='\'')||(LA4_0>='*' && LA4_0<='\uFFFF')) ) {
               alt4=1;
            }
            else if ( (LA4_0=='\"') ) {
               alt4=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 4, 0, input);
               
               throw nvae;
            }
            switch (alt4) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:288:36: s= STRING
               {
                  int sStart224 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart224, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:288:47: s= Q_STRING
               {
                  int sStart230 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart230, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            result.append( TAGS_QUERY_PREFIX )
            .append( " AND " )
            .append( Tags.TAG );
            containsStringParam( s.getText() );
            result.append( ")" );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_TAG_CONTAINS"
   
   // $ANTLR start "OP_IS_TAGGED"
   public final void mOP_IS_TAGGED() throws RecognitionException {
      try {
         int _type = OP_IS_TAGGED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:297:17: ( 'istagged:' ( TRUE | FALSE ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:297:19: 'istagged:' ( TRUE | FALSE )
         {
            match("istagged:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:298:7: ( TRUE | FALSE )
            int alt5=2;
            int LA5_0 = input.LA(1);
            
            if ( (LA5_0=='t') ) {
               alt5=1;
            }
            else if ( (LA5_0=='f') ) {
               alt5=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 5, 0, input);
               
               throw nvae;
            }
            switch (alt5) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:299:8: TRUE
               {
                  mTRUE();
                  
                  result.append( TAGS_QUERY_PREFIX );
                  result.append( ")" );
                  
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:305:8: FALSE
               {
                  mFALSE();
                  
                  result.append( " NOT EXISTS " );
                  result.append( TAGS_QUERY_PREFIX );
                  result.append( ")" );
                  
                  
               }
               break;
               
            }
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_IS_TAGGED"
   
   // $ANTLR start "OP_LOCATION"
   public final void mOP_LOCATION() throws RecognitionException {
      try {
         int _type = OP_LOCATION;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:313:13: ( 'location:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:313:15: 'location:' (s= STRING | s= Q_STRING )
         {
            match("location:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:313:27: (s= STRING | s= Q_STRING )
            int alt6=2;
            int LA6_0 = input.LA(1);
            
            if ( ((LA6_0>='\u0000' && LA6_0<='\u001F')||LA6_0=='!'||(LA6_0>='#' && LA6_0<='\'')||(LA6_0>='*' && LA6_0<='\uFFFF')) ) {
               alt6=1;
            }
            else if ( (LA6_0=='\"') ) {
               alt6=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 6, 0, input);
               
               throw nvae;
            }
            switch (alt6) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:313:29: s= STRING
               {
                  int sStart329 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart329, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:313:40: s= Q_STRING
               {
                  int sStart335 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart335, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            result.append( Tasks.LOCATION_NAME );
            equalsStringParam( s.getText() );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_LOCATION"
   
   // $ANTLR start "OP_ISLOCATED"
   public final void mOP_ISLOCATED() throws RecognitionException {
      try {
         int _type = OP_ISLOCATED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:321:14: ( 'isLocated:' ( TRUE | FALSE ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:321:16: 'isLocated:' ( TRUE | FALSE )
         {
            match("isLocated:");
            
            
            result.append( Tasks.LOCATION_ID );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:325:6: ( TRUE | FALSE )
            int alt7=2;
            int LA7_0 = input.LA(1);
            
            if ( (LA7_0=='t') ) {
               alt7=1;
            }
            else if ( (LA7_0=='f') ) {
               alt7=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 7, 0, input);
               
               throw nvae;
            }
            switch (alt7) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:326:7: TRUE
               {
                  mTRUE();
                  
                  result.append(" IS NOT NULL");
                  
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:331:7: FALSE
               {
                  mFALSE();
                  
                  result.append(" IS NULL");
                  
                  
               }
               break;
               
            }
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_ISLOCATED"
   
   // $ANTLR start "OP_NAME"
   public final void mOP_NAME() throws RecognitionException {
      try {
         int _type = OP_NAME;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:339:10: ( 'name:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:339:13: 'name:' (s= STRING | s= Q_STRING )
         {
            match("name:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:339:21: (s= STRING | s= Q_STRING )
            int alt8=2;
            int LA8_0 = input.LA(1);
            
            if ( ((LA8_0>='\u0000' && LA8_0<='\u001F')||LA8_0=='!'||(LA8_0>='#' && LA8_0<='\'')||(LA8_0>='*' && LA8_0<='\uFFFF')) ) {
               alt8=1;
            }
            else if ( (LA8_0=='\"') ) {
               alt8=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 8, 0, input);
               
               throw nvae;
            }
            switch (alt8) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:339:23: s= STRING
               {
                  int sStart438 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart438, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:339:34: s= Q_STRING
               {
                  int sStart444 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart444, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            result.append( Tasks.TASKSERIES_NAME );
            containsStringParam( s.getText() );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_NAME"
   
   // $ANTLR start "OP_NOTE_CONTAINS"
   public final void mOP_NOTE_CONTAINS() throws RecognitionException {
      try {
         int _type = OP_NOTE_CONTAINS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:345:18: ( 'notecontains:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:345:20: 'notecontains:' (s= STRING | s= Q_STRING )
         {
            match("notecontains:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:345:36: (s= STRING | s= Q_STRING )
            int alt9=2;
            int LA9_0 = input.LA(1);
            
            if ( ((LA9_0>='\u0000' && LA9_0<='\u001F')||LA9_0=='!'||(LA9_0>='#' && LA9_0<='\'')||(LA9_0>='*' && LA9_0<='\uFFFF')) ) {
               alt9=1;
            }
            else if ( (LA9_0=='\"') ) {
               alt9=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 9, 0, input);
               
               throw nvae;
            }
            switch (alt9) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:345:38: s= STRING
               {
                  int sStart472 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart472, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:345:49: s= Q_STRING
               {
                  int sStart478 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart478, getCharIndex()-1);
                  
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
            .append( Tasks._ID )
            .append( " AND " )
            .append( Notes.NOTE_TEXT );
            containsStringParam( s.getText() );
            result.append( ")" );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_NOTE_CONTAINS"
   
   // $ANTLR start "OP_HAS_NOTES"
   public final void mOP_HAS_NOTES() throws RecognitionException {
      try {
         int _type = OP_HAS_NOTES;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:361:14: ( 'hasnotes:' ( TRUE | FALSE ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:361:16: 'hasnotes:' ( TRUE | FALSE )
         {
            match("hasnotes:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:362:6: ( TRUE | FALSE )
            int alt10=2;
            int LA10_0 = input.LA(1);
            
            if ( (LA10_0=='t') ) {
               alt10=1;
            }
            else if ( (LA10_0=='f') ) {
               alt10=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 10, 0, input);
               
               throw nvae;
            }
            switch (alt10) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:363:7: TRUE
               {
                  mTRUE();
                  
                  result.append(" num_notes > 0");
                  
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:368:7: FALSE
               {
                  mFALSE();
                  
                  result.append(" num_notes = 0");
                  
                  
               }
               break;
               
            }
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_HAS_NOTES"
   
   // $ANTLR start "OP_DUE"
   public final void mOP_DUE() throws RecognitionException {
      try {
         int _type = OP_DUE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:374:13: ( 'due:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:374:16: 'due:' (s= STRING | s= Q_STRING )
         {
            match("due:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:374:23: (s= STRING | s= Q_STRING )
            int alt11=2;
            int LA11_0 = input.LA(1);
            
            if ( ((LA11_0>='\u0000' && LA11_0<='\u001F')||LA11_0=='!'||(LA11_0>='#' && LA11_0<='\'')||(LA11_0>='*' && LA11_0<='\uFFFF')) ) {
               alt11=1;
            }
            else if ( (LA11_0=='\"') ) {
               alt11=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 11, 0, input);
               
               throw nvae;
            }
            switch (alt11) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:374:25: s= STRING
               {
                  int sStart571 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart571, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:374:36: s= Q_STRING
               {
                  int sStart577 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart577, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            equalsTimeParam( Tasks.DUE_DATE, s.getText() );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_DUE"
   
   // $ANTLR start "OP_DUE_AFTER"
   public final void mOP_DUE_AFTER() throws RecognitionException {
      try {
         int _type = OP_DUE_AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:379:14: ( 'dueafter:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:379:16: 'dueafter:' (s= STRING | s= Q_STRING )
         {
            match("dueafter:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:379:28: (s= STRING | s= Q_STRING )
            int alt12=2;
            int LA12_0 = input.LA(1);
            
            if ( ((LA12_0>='\u0000' && LA12_0<='\u001F')||LA12_0=='!'||(LA12_0>='#' && LA12_0<='\'')||(LA12_0>='*' && LA12_0<='\uFFFF')) ) {
               alt12=1;
            }
            else if ( (LA12_0=='\"') ) {
               alt12=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 12, 0, input);
               
               throw nvae;
            }
            switch (alt12) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:379:30: s= STRING
               {
                  int sStart600 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart600, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:379:41: s= Q_STRING
               {
                  int sStart606 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart606, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            differsTimeParam( Tasks.DUE_DATE, s.getText(), false );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_DUE_AFTER"
   
   // $ANTLR start "OP_DUE_BEFORE"
   public final void mOP_DUE_BEFORE() throws RecognitionException {
      try {
         int _type = OP_DUE_BEFORE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:384:15: ( 'duebefore:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:384:17: 'duebefore:' (s= STRING | s= Q_STRING )
         {
            match("duebefore:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:384:30: (s= STRING | s= Q_STRING )
            int alt13=2;
            int LA13_0 = input.LA(1);
            
            if ( ((LA13_0>='\u0000' && LA13_0<='\u001F')||LA13_0=='!'||(LA13_0>='#' && LA13_0<='\'')||(LA13_0>='*' && LA13_0<='\uFFFF')) ) {
               alt13=1;
            }
            else if ( (LA13_0=='\"') ) {
               alt13=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 13, 0, input);
               
               throw nvae;
            }
            switch (alt13) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:384:32: s= STRING
               {
                  int sStart634 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart634, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:384:43: s= Q_STRING
               {
                  int sStart640 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart640, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            differsTimeParam( Tasks.DUE_DATE, s.getText(), true );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_DUE_BEFORE"
   
   // $ANTLR start "OP_COMPLETED"
   public final void mOP_COMPLETED() throws RecognitionException {
      try {
         int _type = OP_COMPLETED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:391:14: ( 'completed:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:391:16: 'completed:' (s= STRING | s= Q_STRING )
         {
            match("completed:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:391:29: (s= STRING | s= Q_STRING )
            int alt14=2;
            int LA14_0 = input.LA(1);
            
            if ( ((LA14_0>='\u0000' && LA14_0<='\u001F')||LA14_0=='!'||(LA14_0>='#' && LA14_0<='\'')||(LA14_0>='*' && LA14_0<='\uFFFF')) ) {
               alt14=1;
            }
            else if ( (LA14_0=='\"') ) {
               alt14=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 14, 0, input);
               
               throw nvae;
            }
            switch (alt14) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:391:31: s= STRING
               {
                  int sStart665 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart665, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:391:42: s= Q_STRING
               {
                  int sStart671 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart671, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            equalsTimeParam( Tasks.COMPLETED_DATE, s.getText() );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_COMPLETED"
   
   // $ANTLR start "OP_COMPLETED_BEFORE"
   public final void mOP_COMPLETED_BEFORE() throws RecognitionException {
      try {
         int _type = OP_COMPLETED_BEFORE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:396:21: ( 'completedbefore:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:396:23: 'completedbefore:' (s= STRING | s= Q_STRING )
         {
            match("completedbefore:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:396:42: (s= STRING | s= Q_STRING )
            int alt15=2;
            int LA15_0 = input.LA(1);
            
            if ( ((LA15_0>='\u0000' && LA15_0<='\u001F')||LA15_0=='!'||(LA15_0>='#' && LA15_0<='\'')||(LA15_0>='*' && LA15_0<='\uFFFF')) ) {
               alt15=1;
            }
            else if ( (LA15_0=='\"') ) {
               alt15=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 15, 0, input);
               
               throw nvae;
            }
            switch (alt15) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:396:44: s= STRING
               {
                  int sStart694 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart694, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:396:55: s= Q_STRING
               {
                  int sStart700 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart700, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            differsTimeParam( Tasks.COMPLETED_DATE, s.getText(), true );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_COMPLETED_BEFORE"
   
   // $ANTLR start "OP_COMPLETED_AFTER"
   public final void mOP_COMPLETED_AFTER() throws RecognitionException {
      try {
         int _type = OP_COMPLETED_AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:401:20: ( 'completedafter:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:401:22: 'completedafter:' (s= STRING | s= Q_STRING )
         {
            match("completedafter:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:401:40: (s= STRING | s= Q_STRING )
            int alt16=2;
            int LA16_0 = input.LA(1);
            
            if ( ((LA16_0>='\u0000' && LA16_0<='\u001F')||LA16_0=='!'||(LA16_0>='#' && LA16_0<='\'')||(LA16_0>='*' && LA16_0<='\uFFFF')) ) {
               alt16=1;
            }
            else if ( (LA16_0=='\"') ) {
               alt16=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 16, 0, input);
               
               throw nvae;
            }
            switch (alt16) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:401:42: s= STRING
               {
                  int sStart723 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart723, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:401:53: s= Q_STRING
               {
                  int sStart729 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart729, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            differsTimeParam( Tasks.COMPLETED_DATE, s.getText(), false );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_COMPLETED_AFTER"
   
   // $ANTLR start "OP_ADDED"
   public final void mOP_ADDED() throws RecognitionException {
      try {
         int _type = OP_ADDED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:408:14: ( 'added:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:408:16: 'added:' (s= STRING | s= Q_STRING )
         {
            match("added:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:408:25: (s= STRING | s= Q_STRING )
            int alt17=2;
            int LA17_0 = input.LA(1);
            
            if ( ((LA17_0>='\u0000' && LA17_0<='\u001F')||LA17_0=='!'||(LA17_0>='#' && LA17_0<='\'')||(LA17_0>='*' && LA17_0<='\uFFFF')) ) {
               alt17=1;
            }
            else if ( (LA17_0=='\"') ) {
               alt17=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 17, 0, input);
               
               throw nvae;
            }
            switch (alt17) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:408:27: s= STRING
               {
                  int sStart758 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart758, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:408:38: s= Q_STRING
               {
                  int sStart764 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart764, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            equalsTimeParam( Tasks.ADDED_DATE, s.getText() );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_ADDED"
   
   // $ANTLR start "OP_ADDED_BEFORE"
   public final void mOP_ADDED_BEFORE() throws RecognitionException {
      try {
         int _type = OP_ADDED_BEFORE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:413:17: ( 'addedbefore:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:413:19: 'addedbefore:' (s= STRING | s= Q_STRING )
         {
            match("addedbefore:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:413:34: (s= STRING | s= Q_STRING )
            int alt18=2;
            int LA18_0 = input.LA(1);
            
            if ( ((LA18_0>='\u0000' && LA18_0<='\u001F')||LA18_0=='!'||(LA18_0>='#' && LA18_0<='\'')||(LA18_0>='*' && LA18_0<='\uFFFF')) ) {
               alt18=1;
            }
            else if ( (LA18_0=='\"') ) {
               alt18=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 18, 0, input);
               
               throw nvae;
            }
            switch (alt18) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:413:36: s= STRING
               {
                  int sStart787 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart787, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:413:47: s= Q_STRING
               {
                  int sStart793 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart793, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            differsTimeParam( Tasks.ADDED_DATE, s.getText(), true );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_ADDED_BEFORE"
   
   // $ANTLR start "OP_ADDED_AFTER"
   public final void mOP_ADDED_AFTER() throws RecognitionException {
      try {
         int _type = OP_ADDED_AFTER;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:418:16: ( 'addedafter:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:418:18: 'addedafter:' (s= STRING | s= Q_STRING )
         {
            match("addedafter:");
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:418:32: (s= STRING | s= Q_STRING )
            int alt19=2;
            int LA19_0 = input.LA(1);
            
            if ( ((LA19_0>='\u0000' && LA19_0<='\u001F')||LA19_0=='!'||(LA19_0>='#' && LA19_0<='\'')||(LA19_0>='*' && LA19_0<='\uFFFF')) ) {
               alt19=1;
            }
            else if ( (LA19_0=='\"') ) {
               alt19=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 19, 0, input);
               
               throw nvae;
            }
            switch (alt19) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:418:34: s= STRING
               {
                  int sStart817 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart817, getCharIndex()-1);
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:418:45: s= Q_STRING
               {
                  int sStart823 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart823, getCharIndex()-1);
                  
               }
               break;
               
            }
            
            
            differsTimeParam( Tasks.ADDED_DATE, s.getText(), false );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_ADDED_AFTER"
   
   // $ANTLR start "OP_POSTPONED"
   public final void mOP_POSTPONED() throws RecognitionException {
      try {
         int _type = OP_POSTPONED;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         CommonToken s=null;
         
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:427:14: ( 'postponed:' (s= STRING | s= Q_STRING ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:427:16: 'postponed:' (s= STRING | s= Q_STRING )
         {
            match("postponed:");
            
            
            result.append( Tasks.POSTPONED );
            
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:431:6: (s= STRING | s= Q_STRING )
            int alt20=2;
            int LA20_0 = input.LA(1);
            
            if ( ((LA20_0>='\u0000' && LA20_0<='\u001F')||LA20_0=='!'||(LA20_0>='#' && LA20_0<='\'')||(LA20_0>='*' && LA20_0<='\uFFFF')) ) {
               alt20=1;
            }
            else if ( (LA20_0=='\"') ) {
               alt20=2;
            }
            else {
               NoViableAltException nvae =
                  new NoViableAltException("", 20, 0, input);
               
               throw nvae;
            }
            switch (alt20) {
               case 1 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:432:8: s= STRING
               {
                  int sStart870 = getCharIndex();
                  mSTRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart870, getCharIndex()-1);
                  
                  equalsIntParam( s.getText() );
                  
                  
               }
               break;
               case 2 :
                  // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:437:8: s= Q_STRING
               {
                  int sStart900 = getCharIndex();
                  mQ_STRING();
                  s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart900, getCharIndex()-1);
                  
                  result.append( unquotify( s.getText() ) );
                  
                  
               }
               break;
               
            }
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OP_POSTPONED"
   
   // $ANTLR start "TRUE"
   public final void mTRUE() throws RecognitionException {
      try {
         int _type = TRUE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:458:8: ( 'true' )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:458:10: 'true'
         {
            match("true");
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "TRUE"
   
   // $ANTLR start "FALSE"
   public final void mFALSE() throws RecognitionException {
      try {
         int _type = FALSE;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:460:9: ( 'false' )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:460:11: 'false'
         {
            match("false");
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "FALSE"
   
   // $ANTLR start "L_PARENTH"
   public final void mL_PARENTH() throws RecognitionException {
      try {
         int _type = L_PARENTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:462:13: ( '(' )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:462:15: '('
         {
            match('(');
            
            result.append( "( " );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "L_PARENTH"
   
   // $ANTLR start "R_PARENTH"
   public final void mR_PARENTH() throws RecognitionException {
      try {
         int _type = R_PARENTH;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:467:13: ( ')' )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:467:15: ')'
         {
            match(')');
            
            result.append( " )" );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "R_PARENTH"
   
   // $ANTLR start "AND"
   public final void mAND() throws RecognitionException {
      try {
         int _type = AND;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:472:10: ( 'and' )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:472:12: 'and'
         {
            match("and");
            
            
            result.append( " AND " );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "AND"
   
   // $ANTLR start "OR"
   public final void mOR() throws RecognitionException {
      try {
         int _type = OR;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:477:8: ( 'or' )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:477:10: 'or'
         {
            match("or");
            
            
            result.append( " OR " );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "OR"
   
   // $ANTLR start "NOT"
   public final void mNOT() throws RecognitionException {
      try {
         int _type = NOT;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:482:8: ( 'not' )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:482:10: 'not'
         {
            match("not");
            
            
            result.append( " NOT " );
            
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "NOT"
   
   // $ANTLR start "WS"
   public final void mWS() throws RecognitionException {
      try {
         int _type = WS;
         int _channel = DEFAULT_TOKEN_CHANNEL;
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:487:8: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:487:12: ( ' ' | '\\t' | '\\r' | '\\n' )
         {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
               input.consume();
               
            }
            else {
               MismatchedSetException mse = new MismatchedSetException(null,input);
               recover(mse);
               throw mse;}
            
            skip();
            
         }
         
         state.type = _type;
         state.channel = _channel;
      }
      finally {
      }
   }
   // $ANTLR end "WS"
   
   // $ANTLR start "Q_STRING"
   public final void mQ_STRING() throws RecognitionException {
      try {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:495:11: ( '\"' (~ ( '\"' ) )* '\"' )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:495:13: '\"' (~ ( '\"' ) )* '\"'
         {
            match('\"');
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:495:17: (~ ( '\"' ) )*
            loop21:
               do {
                  int alt21=2;
                  int LA21_0 = input.LA(1);
                  
                  if ( ((LA21_0>='\u0000' && LA21_0<='!')||(LA21_0>='#' && LA21_0<='\uFFFF')) ) {
                     alt21=1;
                  }
                  
                  
                  switch (alt21) {
                     case 1 :
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:495:17: ~ ( '\"' )
                     {
                        if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='\uFFFF') ) {
                           input.consume();
                           
                        }
                        else {
                           MismatchedSetException mse = new MismatchedSetException(null,input);
                           recover(mse);
                           throw mse;}
                        
                        
                     }
                     break;
                     
                     default :
                        break loop21;
                  }
               } while (true);
            
            match('\"');
            
         }
         
      }
      finally {
      }
   }
   // $ANTLR end "Q_STRING"
   
   // $ANTLR start "STRING"
   public final void mSTRING() throws RecognitionException {
      try {
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:498:10: ( (~ ( '\"' | ' ' | '(' | ')' ) )+ )
         // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:498:12: (~ ( '\"' | ' ' | '(' | ')' ) )+
         {
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:498:12: (~ ( '\"' | ' ' | '(' | ')' ) )+
            int cnt22=0;
            loop22:
               do {
                  int alt22=2;
                  int LA22_0 = input.LA(1);
                  
                  if ( ((LA22_0>='\u0000' && LA22_0<='\u001F')||LA22_0=='!'||(LA22_0>='#' && LA22_0<='\'')||(LA22_0>='*' && LA22_0<='\uFFFF')) ) {
                     alt22=1;
                  }
                  
                  
                  switch (alt22) {
                     case 1 :
                        // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:498:12: ~ ( '\"' | ' ' | '(' | ')' )
                     {
                        if ( (input.LA(1)>='\u0000' && input.LA(1)<='\u001F')||input.LA(1)=='!'||(input.LA(1)>='#' && input.LA(1)<='\'')||(input.LA(1)>='*' && input.LA(1)<='\uFFFF') ) {
                           input.consume();
                           
                        }
                        else {
                           MismatchedSetException mse = new MismatchedSetException(null,input);
                           recover(mse);
                           throw mse;}
                        
                        
                     }
                     break;
                     
                     default :
                        if ( cnt22 >= 1 ) break loop22;
                        EarlyExitException eee =
                           new EarlyExitException(22, input);
                        throw eee;
                  }
                  cnt22++;
               } while (true);
            
            
         }
         
      }
      finally {
      }
   }
   // $ANTLR end "STRING"
   
   @Override
   public void mTokens() throws RecognitionException {
      // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:8: ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED | OP_NAME | OP_NOTE_CONTAINS | OP_HAS_NOTES | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE | OP_COMPLETED | OP_COMPLETED_BEFORE | OP_COMPLETED_AFTER | OP_ADDED | OP_ADDED_BEFORE | OP_ADDED_AFTER | OP_POSTPONED | TRUE | FALSE | L_PARENTH | R_PARENTH | AND | OR | NOT | WS )
      int alt23=29;
      alt23 = dfa23.predict(input);
      switch (alt23) {
         case 1 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:10: OP_LIST
         {
            mOP_LIST();
            
         }
         break;
         case 2 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:18: OP_PRIORITY
         {
            mOP_PRIORITY();
            
         }
         break;
         case 3 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:30: OP_STATUS
         {
            mOP_STATUS();
            
         }
         break;
         case 4 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:40: OP_TAG
         {
            mOP_TAG();
            
         }
         break;
         case 5 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:47: OP_TAG_CONTAINS
         {
            mOP_TAG_CONTAINS();
            
         }
         break;
         case 6 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:63: OP_IS_TAGGED
         {
            mOP_IS_TAGGED();
            
         }
         break;
         case 7 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:76: OP_LOCATION
         {
            mOP_LOCATION();
            
         }
         break;
         case 8 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:88: OP_ISLOCATED
         {
            mOP_ISLOCATED();
            
         }
         break;
         case 9 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:101: OP_NAME
         {
            mOP_NAME();
            
         }
         break;
         case 10 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:109: OP_NOTE_CONTAINS
         {
            mOP_NOTE_CONTAINS();
            
         }
         break;
         case 11 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:126: OP_HAS_NOTES
         {
            mOP_HAS_NOTES();
            
         }
         break;
         case 12 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:139: OP_DUE
         {
            mOP_DUE();
            
         }
         break;
         case 13 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:146: OP_DUE_AFTER
         {
            mOP_DUE_AFTER();
            
         }
         break;
         case 14 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:159: OP_DUE_BEFORE
         {
            mOP_DUE_BEFORE();
            
         }
         break;
         case 15 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:173: OP_COMPLETED
         {
            mOP_COMPLETED();
            
         }
         break;
         case 16 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:186: OP_COMPLETED_BEFORE
         {
            mOP_COMPLETED_BEFORE();
            
         }
         break;
         case 17 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:206: OP_COMPLETED_AFTER
         {
            mOP_COMPLETED_AFTER();
            
         }
         break;
         case 18 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:225: OP_ADDED
         {
            mOP_ADDED();
            
         }
         break;
         case 19 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:234: OP_ADDED_BEFORE
         {
            mOP_ADDED_BEFORE();
            
         }
         break;
         case 20 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:250: OP_ADDED_AFTER
         {
            mOP_ADDED_AFTER();
            
         }
         break;
         case 21 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:265: OP_POSTPONED
         {
            mOP_POSTPONED();
            
         }
         break;
         case 22 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:278: TRUE
         {
            mTRUE();
            
         }
         break;
         case 23 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:283: FALSE
         {
            mFALSE();
            
         }
         break;
         case 24 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:289: L_PARENTH
         {
            mL_PARENTH();
            
         }
         break;
         case 25 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:299: R_PARENTH
         {
            mR_PARENTH();
            
         }
         break;
         case 26 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:309: AND
         {
            mAND();
            
         }
         break;
         case 27 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:313: OR
         {
            mOR();
            
         }
         break;
         case 28 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:316: NOT
         {
            mNOT();
            
         }
         break;
         case 29 :
            // F:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:320: WS
         {
            mWS();
            
         }
         break;
         
      }
      
   }
   
   
   protected DFA23 dfa23 = new DFA23(this);
   static final String DFA23_eotS =
      "\40\uffff\1\47\30\uffff";
   static final String DFA23_eofS =
      "\71\uffff";
   static final String DFA23_minS =
      "\1\11\1\151\1\157\1\uffff\1\141\1\163\1\141\1\uffff\1\165\1\157"+
      "\1\144\11\uffff\1\147\1\uffff\1\114\1\uffff\1\164\1\145\1\155\1"+
      "\144\1\uffff\1\72\2\uffff\1\145\1\72\1\160\1\145\7\uffff\1\154\1"+
      "\144\1\145\1\72\1\164\3\uffff\1\145\1\144\1\72\3\uffff";
   static final String DFA23_maxS =
      "\1\164\1\157\1\162\1\uffff\1\162\1\163\1\157\1\uffff\1\165\1\157"+
      "\1\156\11\uffff\1\147\1\uffff\1\164\1\uffff\1\164\1\145\1\155\1"+
      "\144\1\uffff\1\143\2\uffff\1\145\1\142\1\160\1\145\7\uffff\1\154"+
      "\1\144\1\145\1\142\1\164\3\uffff\1\145\1\144\1\142\3\uffff";
   static final String DFA23_acceptS =
      "\3\uffff\1\3\3\uffff\1\13\3\uffff\1\27\1\30\1\31\1\33\1\35\1\1"+
      "\1\7\1\2\1\25\1\uffff\1\26\1\uffff\1\11\4\uffff\1\32\1\uffff\1\6"+
      "\1\10\4\uffff\1\4\1\5\1\12\1\34\1\14\1\15\1\16\5\uffff\1\22\1\23"+
      "\1\24\3\uffff\1\17\1\20\1\21";
   static final String DFA23_specialS =
      "\71\uffff}>";
   static final String[] DFA23_transitionS = {
                                              "\2\17\2\uffff\1\17\22\uffff\1\17\7\uffff\1\14\1\15\67\uffff"+
                                              "\1\12\1\uffff\1\11\1\10\1\uffff\1\13\1\uffff\1\7\1\5\2\uffff"+
                                              "\1\1\1\uffff\1\6\1\16\1\2\2\uffff\1\3\1\4",
                                              "\1\20\5\uffff\1\21",
                                              "\1\23\2\uffff\1\22",
                                              "",
                                              "\1\24\20\uffff\1\25",
                                              "\1\26",
                                              "\1\27\15\uffff\1\30",
                                              "",
                                              "\1\31",
                                              "\1\32",
                                              "\1\33\11\uffff\1\34",
                                              "",
                                              "",
                                              "",
                                              "",
                                              "",
                                              "",
                                              "",
                                              "",
                                              "",
                                              "\1\35",
                                              "",
                                              "\1\37\47\uffff\1\36",
                                              "",
                                              "\1\40",
                                              "\1\41",
                                              "\1\42",
                                              "\1\43",
                                              "",
                                              "\1\44\50\uffff\1\45",
                                              "",
                                              "",
                                              "\1\46",
                                              "\1\50\46\uffff\1\51\1\52",
                                              "\1\53",
                                              "\1\54",
                                              "",
                                              "",
                                              "",
                                              "",
                                              "",
                                              "",
                                              "",
                                              "\1\55",
                                              "\1\56",
                                              "\1\57",
                                              "\1\60\46\uffff\1\62\1\61",
                                              "\1\63",
                                              "",
                                              "",
                                              "",
                                              "\1\64",
                                              "\1\65",
                                              "\1\66\46\uffff\1\70\1\67",
                                              "",
                                              "",
                                              ""
   };
   
   static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
   static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
   static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
   static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
   static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
   static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
   static final short[][] DFA23_transition;
   
   static {
      int numStates = DFA23_transitionS.length;
      DFA23_transition = new short[numStates][];
      for (int i=0; i<numStates; i++) {
         DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
      }
   }
   
   class DFA23 extends DFA {
      
      public DFA23(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 23;
         this.eot = DFA23_eot;
         this.eof = DFA23_eof;
         this.min = DFA23_min;
         this.max = DFA23_max;
         this.accept = DFA23_accept;
         this.special = DFA23_special;
         this.transition = DFA23_transition;
      }
      @Override
      public String getDescription() {
         return "1:1: Tokens : ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED | OP_NAME | OP_NOTE_CONTAINS | OP_HAS_NOTES | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE | OP_COMPLETED | OP_COMPLETED_BEFORE | OP_COMPLETED_AFTER | OP_ADDED | OP_ADDED_BEFORE | OP_ADDED_AFTER | OP_POSTPONED | TRUE | FALSE | L_PARENTH | R_PARENTH | AND | OR | NOT | WS );";
      }
   }
   
   
}
