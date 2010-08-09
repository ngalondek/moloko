// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g 2010-08-09 08:55:50

	package dev.drsoran.moloko.grammar;
	
	import dev.drsoran.provider.Rtm.Tasks;
	import dev.drsoran.provider.Rtm.Tags;
	
	import java.util.Calendar;

	import org.antlr.runtime.CommonTokenStream;
	import org.antlr.runtime.RecognitionException;
	
	import dev.drsoran.moloko.grammar.TimeSpecLexer;
	import dev.drsoran.moloko.grammar.TimeSpecParser;
	
	import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class RtmSmartFilterLexer extends Lexer {
    public static final int OP_IS_TAGGED=13;
    public static final int OP_DUE=17;
    public static final int OP_DUE_BEFORE=19;
    public static final int OP_COMPLETED_AFTER=22;
    public static final int NOT=31;
    public static final int AND=29;
    public static final int EOF=-1;
    public static final int TRUE=11;
    public static final int OP_ADDED_BEFORE=24;
    public static final int OP_PRIORITY=7;
    public static final int OP_TAG=9;
    public static final int OP_DUE_AFTER=18;
    public static final int Q_STRING=5;
    public static final int OP_TAG_CONTAINS=10;
    public static final int R_PARENTH=28;
    public static final int OP_ADDED_AFTER=25;
    public static final int OP_COMPLETED_BEFORE=21;
    public static final int OP_POSTPONED=26;
    public static final int OP_COMPLETED=20;
    public static final int OP_LIST=6;
    public static final int WS=32;
    public static final int L_PARENTH=27;
    public static final int OP_STATUS=8;
    public static final int OP_LOCATION=14;
    public static final int OR=30;
    public static final int OP_NAME=16;
    public static final int FALSE=12;
    public static final int OP_ADDED=23;
    public static final int OP_ISLOCATED=15;
    public static final int STRING=4;

       private final static String TAGS_QUERY_PREFIX
       	= "(SELECT "  + Tags.TASKSERIES_ID + " FROM " + Tags.PATH
       	  + " WHERE " + Tags.TASKSERIES_ID + " = " + Tasks.PATH + "." + Tasks._ID;
       
    	private final StringBuffer result = new StringBuffer();



    	private static Calendar parseDateTimeSpec( String spec )
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
          		// TODO: Read the dayFirst parameter from settings and
          		// remove the true.
                parser.parseDateTime( cal, true );
                return cal;
             }
             catch( RecognitionException e1 )
             {
                return null;
             }
          }
    	}



    	private static final String unquotify( String input )
    	{
    		return input.replaceAll( "(\"|')", "" );
    	}


    	
    	@SuppressWarnings( "unused" )
    	private static final String quotify( String input )
    	{
    		return new StringBuffer( "'").append( input ).append( "'").toString();
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
    public String getGrammarFileName() { return "D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g"; }

    // $ANTLR start "OP_LIST"
    public final void mOP_LIST() throws RecognitionException {
        try {
            int _type = OP_LIST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken s=null;

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:176:13: ( 'list:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:176:16: 'list:' (s= STRING | s= Q_STRING )
            {
            match("list:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:176:24: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:176:26: s= STRING
                    {
                    int sStart49 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart49, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:176:37: s= Q_STRING
                    {
                    int sStart55 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart55, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:182:13: ( 'priority:' s= STRING )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:182:16: 'priority:' s= STRING
            {
            match("priority:"); 

            int sStart77 = getCharIndex();
            mSTRING(); 
            s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart77, getCharIndex()-1);

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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:188:12: ( 'status:' ( 'completed' | 'incomplete' ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:188:15: 'status:' ( 'completed' | 'incomplete' )
            {
            match("status:"); 


            						result.append( Tasks.COMPLETED_DATE );
            					
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:192:6: ( 'completed' | 'incomplete' )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:193:7: 'completed'
                    {
                    match("completed"); 


                    							result.append(" IS NOT NULL");
                    						

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:198:7: 'incomplete'
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:204:13: ( 'tag:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:204:15: 'tag:' (s= STRING | s= Q_STRING )
            {
            match("tag:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:204:22: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:204:24: s= STRING
                    {
                    int sStart192 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart192, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:204:35: s= Q_STRING
                    {
                    int sStart198 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart198, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:213:17: ( 'tagContains:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:213:19: 'tagContains:' (s= STRING | s= Q_STRING )
            {
            match("tagContains:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:213:34: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:213:36: s= STRING
                    {
                    int sStart222 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart222, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:213:47: s= Q_STRING
                    {
                    int sStart228 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart228, getCharIndex()-1);

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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:222:17: ( 'isTagged:' ( TRUE | FALSE ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:222:19: 'isTagged:' ( TRUE | FALSE )
            {
            match("isTagged:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:223:7: ( TRUE | FALSE )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:224:8: TRUE
                    {
                    mTRUE(); 

                    							   result.append( TAGS_QUERY_PREFIX );
                    								result.append( ")" );	
                    							

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:230:8: FALSE
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:238:13: ( 'location:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:238:15: 'location:' (s= STRING | s= Q_STRING )
            {
            match("location:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:238:27: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:238:29: s= STRING
                    {
                    int sStart327 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart327, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:238:40: s= Q_STRING
                    {
                    int sStart333 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart333, getCharIndex()-1);

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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:246:14: ( 'isLocated:' ( TRUE | FALSE ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:246:16: 'isLocated:' ( TRUE | FALSE )
            {
            match("isLocated:"); 


            						result.append( Tasks.LOCATION_ID );
            					
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:250:6: ( TRUE | FALSE )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:251:7: TRUE
                    {
                    mTRUE(); 

                    							result.append(" IS NOT NULL");	
                    						

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:256:7: FALSE
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:264:10: ( 'name:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:264:13: 'name:' (s= STRING | s= Q_STRING )
            {
            match("name:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:264:21: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:264:23: s= STRING
                    {
                    int sStart436 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart436, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:264:34: s= Q_STRING
                    {
                    int sStart442 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart442, getCharIndex()-1);

                    }
                    break;

            }


            						result.append( Tasks.TASKSERIES_NAME );
            						equalsStringParam( s.getText() );
            					

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OP_NAME"

    // $ANTLR start "OP_DUE"
    public final void mOP_DUE() throws RecognitionException {
        try {
            int _type = OP_DUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken s=null;

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:274:13: ( 'due:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:274:16: 'due:' (s= STRING | s= Q_STRING )
            {
            match("due:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:274:23: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:274:25: s= STRING
                    {
                    int sStart480 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart480, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:274:36: s= Q_STRING
                    {
                    int sStart486 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart486, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:279:14: ( 'dueafter:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:279:16: 'dueafter:' (s= STRING | s= Q_STRING )
            {
            match("dueafter:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:279:28: (s= STRING | s= Q_STRING )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( ((LA10_0>='\u0000' && LA10_0<='\u001F')||LA10_0=='!'||(LA10_0>='#' && LA10_0<='\'')||(LA10_0>='*' && LA10_0<='\uFFFF')) ) {
                alt10=1;
            }
            else if ( (LA10_0=='\"') ) {
                alt10=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:279:30: s= STRING
                    {
                    int sStart509 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart509, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:279:41: s= Q_STRING
                    {
                    int sStart515 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart515, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:284:15: ( 'duebefore:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:284:17: 'duebefore:' (s= STRING | s= Q_STRING )
            {
            match("duebefore:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:284:30: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:284:32: s= STRING
                    {
                    int sStart543 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart543, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:284:43: s= Q_STRING
                    {
                    int sStart549 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart549, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:291:14: ( 'completed:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:291:16: 'completed:' (s= STRING | s= Q_STRING )
            {
            match("completed:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:291:29: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:291:31: s= STRING
                    {
                    int sStart574 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart574, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:291:42: s= Q_STRING
                    {
                    int sStart580 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart580, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:296:21: ( 'completedbefore:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:296:23: 'completedbefore:' (s= STRING | s= Q_STRING )
            {
            match("completedbefore:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:296:42: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:296:44: s= STRING
                    {
                    int sStart603 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart603, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:296:55: s= Q_STRING
                    {
                    int sStart609 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart609, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:301:20: ( 'completedafter:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:301:22: 'completedafter:' (s= STRING | s= Q_STRING )
            {
            match("completedafter:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:301:40: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:301:42: s= STRING
                    {
                    int sStart632 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart632, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:301:53: s= Q_STRING
                    {
                    int sStart638 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart638, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:308:14: ( 'added:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:308:16: 'added:' (s= STRING | s= Q_STRING )
            {
            match("added:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:308:25: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:308:27: s= STRING
                    {
                    int sStart667 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart667, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:308:38: s= Q_STRING
                    {
                    int sStart673 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart673, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:313:17: ( 'addedbefore:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:313:19: 'addedbefore:' (s= STRING | s= Q_STRING )
            {
            match("addedbefore:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:313:34: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:313:36: s= STRING
                    {
                    int sStart696 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart696, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:313:47: s= Q_STRING
                    {
                    int sStart702 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart702, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:318:16: ( 'addedafter:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:318:18: 'addedafter:' (s= STRING | s= Q_STRING )
            {
            match("addedafter:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:318:32: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:318:34: s= STRING
                    {
                    int sStart726 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart726, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:318:45: s= Q_STRING
                    {
                    int sStart732 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart732, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:327:14: ( 'postponed:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:327:16: 'postponed:' (s= STRING | s= Q_STRING )
            {
            match("postponed:"); 


            						result.append( Tasks.POSTPONED );
            					
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:331:6: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:332:8: s= STRING
                    {
                    int sStart779 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart779, getCharIndex()-1);

                    					  		equalsIntParam( s.getText() );
                    					  

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:337:8: s= Q_STRING
                    {
                    int sStart809 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart809, getCharIndex()-1);

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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:359:8: ( 'true' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:359:10: 'true'
            {
            match("true"); 


            }

        }
        finally {
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:362:9: ( 'false' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:362:11: 'false'
            {
            match("false"); 


            }

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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:364:13: ( '(' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:364:15: '('
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:369:13: ( ')' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:369:15: ')'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:374:10: ( 'and' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:374:12: 'and'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:379:8: ( 'or' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:379:10: 'or'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:384:8: ( 'not' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:384:10: 'not'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:389:8: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:389:12: ( ' ' | '\\t' | '\\r' | '\\n' )
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:397:11: ( '\"' (~ ( '\"' ) )* '\"' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:397:13: '\"' (~ ( '\"' ) )* '\"'
            {
            match('\"'); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:397:17: (~ ( '\"' ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>='\u0000' && LA19_0<='!')||(LA19_0>='#' && LA19_0<='\uFFFF')) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:397:17: ~ ( '\"' )
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
            	    break loop19;
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:400:10: ( (~ ( '\"' | ' ' | '(' | ')' ) )+ )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:400:12: (~ ( '\"' | ' ' | '(' | ')' ) )+
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:400:12: (~ ( '\"' | ' ' | '(' | ')' ) )+
            int cnt20=0;
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>='\u0000' && LA20_0<='\u001F')||LA20_0=='!'||(LA20_0>='#' && LA20_0<='\'')||(LA20_0>='*' && LA20_0<='\uFFFF')) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:400:12: ~ ( '\"' | ' ' | '(' | ')' )
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
            	    if ( cnt20 >= 1 ) break loop20;
                        EarlyExitException eee =
                            new EarlyExitException(20, input);
                        throw eee;
                }
                cnt20++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    public void mTokens() throws RecognitionException {
        // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:8: ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED | OP_NAME | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE | OP_COMPLETED | OP_COMPLETED_BEFORE | OP_COMPLETED_AFTER | OP_ADDED | OP_ADDED_BEFORE | OP_ADDED_AFTER | OP_POSTPONED | L_PARENTH | R_PARENTH | AND | OR | NOT | WS )
        int alt21=25;
        alt21 = dfa21.predict(input);
        switch (alt21) {
            case 1 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:10: OP_LIST
                {
                mOP_LIST(); 

                }
                break;
            case 2 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:18: OP_PRIORITY
                {
                mOP_PRIORITY(); 

                }
                break;
            case 3 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:30: OP_STATUS
                {
                mOP_STATUS(); 

                }
                break;
            case 4 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:40: OP_TAG
                {
                mOP_TAG(); 

                }
                break;
            case 5 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:47: OP_TAG_CONTAINS
                {
                mOP_TAG_CONTAINS(); 

                }
                break;
            case 6 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:63: OP_IS_TAGGED
                {
                mOP_IS_TAGGED(); 

                }
                break;
            case 7 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:76: OP_LOCATION
                {
                mOP_LOCATION(); 

                }
                break;
            case 8 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:88: OP_ISLOCATED
                {
                mOP_ISLOCATED(); 

                }
                break;
            case 9 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:101: OP_NAME
                {
                mOP_NAME(); 

                }
                break;
            case 10 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:109: OP_DUE
                {
                mOP_DUE(); 

                }
                break;
            case 11 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:116: OP_DUE_AFTER
                {
                mOP_DUE_AFTER(); 

                }
                break;
            case 12 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:129: OP_DUE_BEFORE
                {
                mOP_DUE_BEFORE(); 

                }
                break;
            case 13 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:143: OP_COMPLETED
                {
                mOP_COMPLETED(); 

                }
                break;
            case 14 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:156: OP_COMPLETED_BEFORE
                {
                mOP_COMPLETED_BEFORE(); 

                }
                break;
            case 15 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:176: OP_COMPLETED_AFTER
                {
                mOP_COMPLETED_AFTER(); 

                }
                break;
            case 16 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:195: OP_ADDED
                {
                mOP_ADDED(); 

                }
                break;
            case 17 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:204: OP_ADDED_BEFORE
                {
                mOP_ADDED_BEFORE(); 

                }
                break;
            case 18 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:220: OP_ADDED_AFTER
                {
                mOP_ADDED_AFTER(); 

                }
                break;
            case 19 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:235: OP_POSTPONED
                {
                mOP_POSTPONED(); 

                }
                break;
            case 20 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:248: L_PARENTH
                {
                mL_PARENTH(); 

                }
                break;
            case 21 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:258: R_PARENTH
                {
                mR_PARENTH(); 

                }
                break;
            case 22 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:268: AND
                {
                mAND(); 

                }
                break;
            case 23 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:272: OR
                {
                mOR(); 

                }
                break;
            case 24 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:275: NOT
                {
                mNOT(); 

                }
                break;
            case 25 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:279: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA21 dfa21 = new DFA21(this);
    static final String DFA21_eotS =
        "\63\uffff";
    static final String DFA21_eofS =
        "\63\uffff";
    static final String DFA21_minS =
        "\1\11\1\151\1\157\1\uffff\1\141\1\163\1\141\1\165\1\157\1\144\10"+
        "\uffff\1\147\1\114\2\uffff\1\145\1\155\1\144\1\uffff\1\72\2\uffff"+
        "\1\72\1\160\1\145\5\uffff\1\154\1\144\1\145\1\72\1\164\3\uffff\1"+
        "\145\1\144\1\72\3\uffff";
    static final String DFA21_maxS =
        "\1\164\1\157\1\162\1\uffff\1\141\1\163\1\157\1\165\1\157\1\156"+
        "\10\uffff\1\147\1\124\2\uffff\1\145\1\155\1\144\1\uffff\1\103\2"+
        "\uffff\1\142\1\160\1\145\5\uffff\1\154\1\144\1\145\1\142\1\164\3"+
        "\uffff\1\145\1\144\1\142\3\uffff";
    static final String DFA21_acceptS =
        "\3\uffff\1\3\6\uffff\1\24\1\25\1\27\1\31\1\1\1\7\1\2\1\23\2\uffff"+
        "\1\11\1\30\3\uffff\1\26\1\uffff\1\6\1\10\3\uffff\1\4\1\5\1\12\1"+
        "\13\1\14\5\uffff\1\20\1\21\1\22\3\uffff\1\15\1\16\1\17";
    static final String DFA21_specialS =
        "\63\uffff}>";
    static final String[] DFA21_transitionS = {
            "\2\15\2\uffff\1\15\22\uffff\1\15\7\uffff\1\12\1\13\67\uffff"+
            "\1\11\1\uffff\1\10\1\7\4\uffff\1\5\2\uffff\1\1\1\uffff\1\6\1"+
            "\14\1\2\2\uffff\1\3\1\4",
            "\1\16\5\uffff\1\17",
            "\1\21\2\uffff\1\20",
            "",
            "\1\22",
            "\1\23",
            "\1\24\15\uffff\1\25",
            "\1\26",
            "\1\27",
            "\1\30\11\uffff\1\31",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\32",
            "\1\34\7\uffff\1\33",
            "",
            "",
            "\1\35",
            "\1\36",
            "\1\37",
            "",
            "\1\40\10\uffff\1\41",
            "",
            "",
            "\1\42\46\uffff\1\43\1\44",
            "\1\45",
            "\1\46",
            "",
            "",
            "",
            "",
            "",
            "\1\47",
            "\1\50",
            "\1\51",
            "\1\52\46\uffff\1\54\1\53",
            "\1\55",
            "",
            "",
            "",
            "\1\56",
            "\1\57",
            "\1\60\46\uffff\1\62\1\61",
            "",
            "",
            ""
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_TAG | OP_TAG_CONTAINS | OP_IS_TAGGED | OP_LOCATION | OP_ISLOCATED | OP_NAME | OP_DUE | OP_DUE_AFTER | OP_DUE_BEFORE | OP_COMPLETED | OP_COMPLETED_BEFORE | OP_COMPLETED_AFTER | OP_ADDED | OP_ADDED_BEFORE | OP_ADDED_AFTER | OP_POSTPONED | L_PARENTH | R_PARENTH | AND | OR | NOT | WS );";
        }
    }
 

}