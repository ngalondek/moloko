// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g 2010-07-01 08:53:21

	package dev.drsoran.moloko.grammar;
	
	import dev.drsoran.provider.Rtm.Tasks;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class RtmSmartFilterLexer extends Lexer {
    public static final int R_PARENTH=10;
    public static final int OP_LIST=6;
    public static final int WS=14;
    public static final int L_PARENTH=9;
    public static final int OR=12;
    public static final int OP_NAME=8;
    public static final int NOT=13;
    public static final int AND=11;
    public static final int EOF=-1;
    public static final int OP_PRIORITY=7;
    public static final int STRING=4;
    public static final int Q_STRING=5;

    	private final StringBuffer result = new StringBuffer();
    		
    	private String unquotify( String input )
    	{
    		return input.replaceAll( "(\"|')", "" );
    	}
    	
    	private void equalsStringParam( String param )
    	{
    		result.append( " = '" );
    		result.append( unquotify( param ) );
    		result.append( "'" );
    	}
    	
    	public String getResult()
    	{
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:37:11: ( 'list:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:37:13: 'list:' (s= STRING | s= Q_STRING )
            {
            match("list:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:37:21: (s= STRING | s= Q_STRING )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:37:23: s= STRING
                    {
                    int sStart43 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart43, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:37:34: s= Q_STRING
                    {
                    int sStart49 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart49, getCharIndex()-1);

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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:43:13: ( 'priority:' s= STRING )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:43:15: 'priority:' s= STRING
            {
            match("priority:"); 

            int sStart65 = getCharIndex();
            mSTRING(); 
            s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart65, getCharIndex()-1);

            	result.append( "priority = " + unquotify( s.getText() ) );


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OP_PRIORITY"

    // $ANTLR start "OP_NAME"
    public final void mOP_NAME() throws RecognitionException {
        try {
            int _type = OP_NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken s=null;

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:48:10: ( 'name:' (s= STRING | s= Q_STRING ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:48:12: 'name:' (s= STRING | s= Q_STRING )
            {
            match("name:"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:48:20: (s= STRING | s= Q_STRING )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>='\u0000' && LA2_0<='\u001F')||LA2_0=='!'||(LA2_0>='#' && LA2_0<='\'')||(LA2_0>='*' && LA2_0<='\uFFFF')) ) {
                alt2=1;
            }
            else if ( (LA2_0=='\"') ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:48:22: s= STRING
                    {
                    int sStart82 = getCharIndex();
                    mSTRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart82, getCharIndex()-1);

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:48:33: s= Q_STRING
                    {
                    int sStart88 = getCharIndex();
                    mQ_STRING(); 
                    s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart88, getCharIndex()-1);

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

    // $ANTLR start "L_PARENTH"
    public final void mL_PARENTH() throws RecognitionException {
        try {
            int _type = L_PARENTH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:54:13: ( '(' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:54:15: '('
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:59:13: ( ')' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:59:15: ')'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:64:10: ( 'AND' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:64:12: 'AND'
            {
            match("AND"); 


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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:69:8: ( 'OR' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:69:10: 'OR'
            {
            match("OR"); 


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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:74:8: ( 'NOT' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:74:10: 'NOT'
            {
            match("NOT"); 


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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:79:8: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:79:12: ( ' ' | '\\t' | '\\r' | '\\n' )
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:87:11: ( '\"' (~ ( '\"' ) )* '\"' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:87:13: '\"' (~ ( '\"' ) )* '\"'
            {
            match('\"'); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:87:17: (~ ( '\"' ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\u0000' && LA3_0<='!')||(LA3_0>='#' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:87:17: ~ ( '\"' )
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
            	    break loop3;
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:90:10: ( (~ ( '\"' | ' ' | '(' | ')' ) )+ )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:90:12: (~ ( '\"' | ' ' | '(' | ')' ) )+
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:90:12: (~ ( '\"' | ' ' | '(' | ')' ) )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( ((LA4_0>='\u0000' && LA4_0<='\u001F')||LA4_0=='!'||(LA4_0>='#' && LA4_0<='\'')||(LA4_0>='*' && LA4_0<='\uFFFF')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:90:12: ~ ( '\"' | ' ' | '(' | ')' )
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
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    public void mTokens() throws RecognitionException {
        // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:8: ( OP_LIST | OP_PRIORITY | OP_NAME | L_PARENTH | R_PARENTH | AND | OR | NOT | WS )
        int alt5=9;
        switch ( input.LA(1) ) {
        case 'l':
            {
            alt5=1;
            }
            break;
        case 'p':
            {
            alt5=2;
            }
            break;
        case 'n':
            {
            alt5=3;
            }
            break;
        case '(':
            {
            alt5=4;
            }
            break;
        case ')':
            {
            alt5=5;
            }
            break;
        case 'A':
            {
            alt5=6;
            }
            break;
        case 'O':
            {
            alt5=7;
            }
            break;
        case 'N':
            {
            alt5=8;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt5=9;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 5, 0, input);

            throw nvae;
        }

        switch (alt5) {
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
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:30: OP_NAME
                {
                mOP_NAME(); 

                }
                break;
            case 4 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:38: L_PARENTH
                {
                mL_PARENTH(); 

                }
                break;
            case 5 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:48: R_PARENTH
                {
                mR_PARENTH(); 

                }
                break;
            case 6 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:58: AND
                {
                mAND(); 

                }
                break;
            case 7 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:62: OR
                {
                mOR(); 

                }
                break;
            case 8 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:65: NOT
                {
                mNOT(); 

                }
                break;
            case 9 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartFilterLexer.g:1:69: WS
                {
                mWS(); 

                }
                break;

        }

    }


 

}