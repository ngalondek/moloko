// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g 2010-08-03 15:32:05

	package dev.drsoran.moloko.grammar;
	
	import dev.drsoran.provider.Rtm.Tasks;
	
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
    public static final int OP_DUE=14;
    public static final int OP_COMPLETED_AFTER=18;
    public static final int NOT=27;
    public static final int AND=25;
    public static final int EOF=-1;
    public static final int TRUE=10;
    public static final int OP_ADDED_BEFORE=20;
    public static final int OP_PRIORITY=7;
    public static final int OP_DUE_AFTER=15;
    public static final int Q_STRING=5;
    public static final int R_PARENTH=24;
    public static final int OP_ADDED_AFTER=21;
    public static final int OP_COMPLETED_BEFORE=17;
    public static final int OP_POSTPONED=22;
    public static final int OP_LIST=6;
    public static final int OP_COMPLETED=16;
    public static final int WS=28;
    public static final int L_PARENTH=23;
    public static final int OP_STATUS=8;
    public static final int OP_LOCATION=9;
    public static final int OR=26;
    public static final int OP_NAME=13;
    public static final int FALSE=11;
    public static final int OP_ADDED=19;
    public static final int OP_ISLOCATED=12;
    public static final int STRING=4;

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:162:13: ( 'list:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:162:16: 'list:' (s= STRING | s= Q_STRING )
            {
            match("list:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:162:24: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:162:26: s= STRING
                    {
                    int sStart49 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart49, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:162:37: s= Q_STRING
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:168:13: ( 'priority:' s= STRING )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:168:16: 'priority:' s= STRING
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:174:12: ( 'status:' ( 'completed' | 'incomplete' ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:174:15: 'status:' ( 'completed' | 'incomplete' )
            {
            match("status:"); 


            						result.append( Tasks.COMPLETED_DATE );
            					
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:178:6: ( 'completed' | 'incomplete' )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:179:7: 'completed'
                    {
                    match("completed"); 


                    							result.append(" IS NOT NULL");
                    						

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:184:7: 'incomplete'
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

    // $ANTLR start "OP_LOCATION"
    public final void mOP_LOCATION() throws RecognitionException {
        try {
            int _type = OP_LOCATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken s=null;

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:196:13: ( 'location:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:196:15: 'location:' (s= STRING | s= Q_STRING )
            {
            match("location:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:196:27: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:196:29: s= STRING
                    {
                    int sStart193 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart193, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:196:40: s= Q_STRING
                    {
                    int sStart199 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart199, getCharIndex()-1);

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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:204:14: ( 'isLocated:' ( TRUE | FALSE ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:204:16: 'isLocated:' ( TRUE | FALSE )
            {
            match("isLocated:"); 


            						result.append( Tasks.LOCATION_ID );
            					
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:208:6: ( TRUE | FALSE )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='t') ) {
                alt4=1;
            }
            else if ( (LA4_0=='f') ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:209:7: TRUE
                    {
                    mTRUE(); 

                    							result.append(" IS NOT NULL");	
                    						

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:214:7: FALSE
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:222:10: ( 'name:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:222:13: 'name:' (s= STRING | s= Q_STRING )
            {
            match("name:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:222:21: (s= STRING | s= Q_STRING )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0>='\u0000' && LA5_0<='\u001F')||LA5_0=='!'||(LA5_0>='#' && LA5_0<='\'')||(LA5_0>='*' && LA5_0<='\uFFFF')) ) {
                alt5=1;
            }
            else if ( (LA5_0=='\"') ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:222:23: s= STRING
                    {
                    int sStart302 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart302, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:222:34: s= Q_STRING
                    {
                    int sStart308 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart308, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:232:13: ( 'due:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:232:16: 'due:' (s= STRING | s= Q_STRING )
            {
            match("due:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:232:23: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:232:25: s= STRING
                    {
                    int sStart346 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart346, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:232:36: s= Q_STRING
                    {
                    int sStart352 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart352, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:237:14: ( 'dueafter:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:237:16: 'dueafter:' (s= STRING | s= Q_STRING )
            {
            match("dueafter:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:237:28: (s= STRING | s= Q_STRING )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( ((LA7_0>='\u0000' && LA7_0<='\u001F')||LA7_0=='!'||(LA7_0>='#' && LA7_0<='\'')||(LA7_0>='*' && LA7_0<='\uFFFF')) ) {
                alt7=1;
            }
            else if ( (LA7_0=='\"') ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:237:30: s= STRING
                    {
                    int sStart375 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart375, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:237:41: s= Q_STRING
                    {
                    int sStart381 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart381, getCharIndex()-1);

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

    // $ANTLR start "OP_COMPLETED"
    public final void mOP_COMPLETED() throws RecognitionException {
        try {
            int _type = OP_COMPLETED;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken s=null;

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:244:14: ( 'completed:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:244:16: 'completed:' (s= STRING | s= Q_STRING )
            {
            match("completed:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:244:29: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:244:31: s= STRING
                    {
                    int sStart406 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart406, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:244:42: s= Q_STRING
                    {
                    int sStart412 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart412, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:249:21: ( 'completedbefore:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:249:23: 'completedbefore:' (s= STRING | s= Q_STRING )
            {
            match("completedbefore:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:249:42: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:249:44: s= STRING
                    {
                    int sStart435 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart435, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:249:55: s= Q_STRING
                    {
                    int sStart441 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart441, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:254:20: ( 'completedafter:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:254:22: 'completedafter:' (s= STRING | s= Q_STRING )
            {
            match("completedafter:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:254:40: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:254:42: s= STRING
                    {
                    int sStart464 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart464, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:254:53: s= Q_STRING
                    {
                    int sStart470 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart470, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:261:14: ( 'added:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:261:16: 'added:' (s= STRING | s= Q_STRING )
            {
            match("added:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:261:25: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:261:27: s= STRING
                    {
                    int sStart499 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart499, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:261:38: s= Q_STRING
                    {
                    int sStart505 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart505, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:266:17: ( 'addedbefore:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:266:19: 'addedbefore:' (s= STRING | s= Q_STRING )
            {
            match("addedbefore:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:266:34: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:266:36: s= STRING
                    {
                    int sStart528 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart528, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:266:47: s= Q_STRING
                    {
                    int sStart534 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart534, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:271:16: ( 'addedafter:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:271:18: 'addedafter:' (s= STRING | s= Q_STRING )
            {
            match("addedafter:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:271:32: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:271:34: s= STRING
                    {
                    int sStart558 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart558, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:271:45: s= Q_STRING
                    {
                    int sStart564 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart564, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:280:14: ( 'postponed:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:280:16: 'postponed:' (s= STRING | s= Q_STRING )
            {
            match("postponed:"); 


            						result.append( Tasks.POSTPONED );
            					
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:284:6: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:285:8: s= STRING
                    {
                    int sStart611 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart611, getCharIndex()-1);

                    					  		equalsIntParam( s.getText() );
                    					  

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:290:8: s= Q_STRING
                    {
                    int sStart641 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart641, getCharIndex()-1);

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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:312:8: ( 'true' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:312:10: 'true'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:315:9: ( 'false' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:315:11: 'false'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:317:13: ( '(' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:317:15: '('
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:322:13: ( ')' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:322:15: ')'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:327:10: ( 'and' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:327:12: 'and'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:332:8: ( 'or' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:332:10: 'or'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:337:8: ( 'not' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:337:10: 'not'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:342:8: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:342:12: ( ' ' | '\\t' | '\\r' | '\\n' )
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:350:11: ( '\"' (~ ( '\"' ) )* '\"' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:350:13: '\"' (~ ( '\"' ) )* '\"'
            {
            match('\"'); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:350:17: (~ ( '\"' ) )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>='\u0000' && LA15_0<='!')||(LA15_0>='#' && LA15_0<='\uFFFF')) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:350:17: ~ ( '\"' )
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
            	    break loop15;
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:353:10: ( (~ ( '\"' | ' ' | '(' | ')' ) )+ )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:353:12: (~ ( '\"' | ' ' | '(' | ')' ) )+
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:353:12: (~ ( '\"' | ' ' | '(' | ')' ) )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='\u0000' && LA16_0<='\u001F')||LA16_0=='!'||(LA16_0>='#' && LA16_0<='\'')||(LA16_0>='*' && LA16_0<='\uFFFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:353:12: ~ ( '\"' | ' ' | '(' | ')' )
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
            	    if ( cnt16 >= 1 ) break loop16;
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    public void mTokens() throws RecognitionException {
        // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:8: ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_LOCATION | OP_ISLOCATED | OP_NAME | OP_DUE | OP_DUE_AFTER | OP_COMPLETED | OP_COMPLETED_BEFORE | OP_COMPLETED_AFTER | OP_ADDED | OP_ADDED_BEFORE | OP_ADDED_AFTER | OP_POSTPONED | L_PARENTH | R_PARENTH | AND | OR | NOT | WS )
        int alt17=21;
        alt17 = dfa17.predict(input);
        switch (alt17) {
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
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:40: OP_LOCATION
                {
                mOP_LOCATION(); 

                }
                break;
            case 5 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:52: OP_ISLOCATED
                {
                mOP_ISLOCATED(); 

                }
                break;
            case 6 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:65: OP_NAME
                {
                mOP_NAME(); 

                }
                break;
            case 7 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:73: OP_DUE
                {
                mOP_DUE(); 

                }
                break;
            case 8 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:80: OP_DUE_AFTER
                {
                mOP_DUE_AFTER(); 

                }
                break;
            case 9 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:93: OP_COMPLETED
                {
                mOP_COMPLETED(); 

                }
                break;
            case 10 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:106: OP_COMPLETED_BEFORE
                {
                mOP_COMPLETED_BEFORE(); 

                }
                break;
            case 11 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:126: OP_COMPLETED_AFTER
                {
                mOP_COMPLETED_AFTER(); 

                }
                break;
            case 12 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:145: OP_ADDED
                {
                mOP_ADDED(); 

                }
                break;
            case 13 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:154: OP_ADDED_BEFORE
                {
                mOP_ADDED_BEFORE(); 

                }
                break;
            case 14 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:170: OP_ADDED_AFTER
                {
                mOP_ADDED_AFTER(); 

                }
                break;
            case 15 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:185: OP_POSTPONED
                {
                mOP_POSTPONED(); 

                }
                break;
            case 16 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:198: L_PARENTH
                {
                mL_PARENTH(); 

                }
                break;
            case 17 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:208: R_PARENTH
                {
                mR_PARENTH(); 

                }
                break;
            case 18 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:218: AND
                {
                mAND(); 

                }
                break;
            case 19 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:222: OR
                {
                mOR(); 

                }
                break;
            case 20 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:225: NOT
                {
                mNOT(); 

                }
                break;
            case 21 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:229: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA17 dfa17 = new DFA17(this);
    static final String DFA17_eotS =
        "\52\uffff";
    static final String DFA17_eofS =
        "\52\uffff";
    static final String DFA17_minS =
        "\1\11\1\151\1\157\2\uffff\1\141\1\165\1\157\1\144\12\uffff\1\145"+
        "\1\155\1\144\1\uffff\1\72\1\160\1\145\2\uffff\1\154\1\144\1\145"+
        "\1\72\1\164\3\uffff\1\145\1\144\1\72\3\uffff";
    static final String DFA17_maxS =
        "\1\163\1\157\1\162\2\uffff\1\157\1\165\1\157\1\156\12\uffff\1\145"+
        "\1\155\1\144\1\uffff\1\141\1\160\1\145\2\uffff\1\154\1\144\1\145"+
        "\1\142\1\164\3\uffff\1\145\1\144\1\142\3\uffff";
    static final String DFA17_acceptS =
        "\3\uffff\1\3\1\5\4\uffff\1\20\1\21\1\23\1\25\1\1\1\4\1\2\1\17\1"+
        "\6\1\24\3\uffff\1\22\3\uffff\1\7\1\10\5\uffff\1\14\1\15\1\16\3\uffff"+
        "\1\11\1\12\1\13";
    static final String DFA17_specialS =
        "\52\uffff}>";
    static final String[] DFA17_transitionS = {
            "\2\14\2\uffff\1\14\22\uffff\1\14\7\uffff\1\11\1\12\67\uffff"+
            "\1\10\1\uffff\1\7\1\6\4\uffff\1\4\2\uffff\1\1\1\uffff\1\5\1"+
            "\13\1\2\2\uffff\1\3",
            "\1\15\5\uffff\1\16",
            "\1\20\2\uffff\1\17",
            "",
            "",
            "\1\21\15\uffff\1\22",
            "\1\23",
            "\1\24",
            "\1\25\11\uffff\1\26",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\27",
            "\1\30",
            "\1\31",
            "",
            "\1\32\46\uffff\1\33",
            "\1\34",
            "\1\35",
            "",
            "",
            "\1\36",
            "\1\37",
            "\1\40",
            "\1\41\46\uffff\1\43\1\42",
            "\1\44",
            "",
            "",
            "",
            "\1\45",
            "\1\46",
            "\1\47\46\uffff\1\51\1\50",
            "",
            "",
            ""
    };

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( OP_LIST | OP_PRIORITY | OP_STATUS | OP_LOCATION | OP_ISLOCATED | OP_NAME | OP_DUE | OP_DUE_AFTER | OP_COMPLETED | OP_COMPLETED_BEFORE | OP_COMPLETED_AFTER | OP_ADDED | OP_ADDED_BEFORE | OP_ADDED_AFTER | OP_POSTPONED | L_PARENTH | R_PARENTH | AND | OR | NOT | WS );";
        }
    }
 

}