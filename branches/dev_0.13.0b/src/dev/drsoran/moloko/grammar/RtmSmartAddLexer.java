// $ANTLR 3.3 Nov 30, 2010 12:45:30 F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g 2011-04-01 14:06:37

   package dev.drsoran.moloko.grammar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class RtmSmartAddLexer extends Lexer {
    public static final int EOF=-1;
    public static final int STRING=4;
    public static final int OP_DUE_DATE=5;
    public static final int OP_ESTIMATE=6;
    public static final int OP_LIST_OR_TAGS=7;
    public static final int OP_LOCATION=8;
    public static final int OP_PRIORITY=9;
    public static final int OP_REPEAT=10;

    // delegates
    // delegators

    public RtmSmartAddLexer() {;} 
    public RtmSmartAddLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public RtmSmartAddLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g"; }

    // $ANTLR start "OP_DUE_DATE"
    public final void mOP_DUE_DATE() throws RecognitionException {
        try {
            int _type = OP_DUE_DATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:76:17: ( '^' )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:76:19: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OP_DUE_DATE"

    // $ANTLR start "OP_PRIORITY"
    public final void mOP_PRIORITY() throws RecognitionException {
        try {
            int _type = OP_PRIORITY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:78:17: ( '!' )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:78:19: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OP_PRIORITY"

    // $ANTLR start "OP_LIST_OR_TAGS"
    public final void mOP_LIST_OR_TAGS() throws RecognitionException {
        try {
            int _type = OP_LIST_OR_TAGS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:80:17: ( '#' )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:80:19: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OP_LIST_OR_TAGS"

    // $ANTLR start "OP_LOCATION"
    public final void mOP_LOCATION() throws RecognitionException {
        try {
            int _type = OP_LOCATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:82:17: ( '@' )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:82:19: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OP_LOCATION"

    // $ANTLR start "OP_REPEAT"
    public final void mOP_REPEAT() throws RecognitionException {
        try {
            int _type = OP_REPEAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:84:17: ( '*' )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:84:19: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OP_REPEAT"

    // $ANTLR start "OP_ESTIMATE"
    public final void mOP_ESTIMATE() throws RecognitionException {
        try {
            int _type = OP_ESTIMATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:86:17: ( '=' )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:86:19: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OP_ESTIMATE"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:88:17: ( (~ ( OP_DUE_DATE | OP_PRIORITY | OP_LIST_OR_TAGS | OP_REPEAT | OP_ESTIMATE ) )+ )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:88:19: (~ ( OP_DUE_DATE | OP_PRIORITY | OP_LIST_OR_TAGS | OP_REPEAT | OP_ESTIMATE ) )+
            {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:88:19: (~ ( OP_DUE_DATE | OP_PRIORITY | OP_LIST_OR_TAGS | OP_REPEAT | OP_ESTIMATE ) )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\u0000' && LA1_0<=' ')||LA1_0=='\"'||(LA1_0>='$' && LA1_0<=')')||(LA1_0>='+' && LA1_0<='<')||(LA1_0>='>' && LA1_0<=']')||(LA1_0>='_' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:88:19: ~ ( OP_DUE_DATE | OP_PRIORITY | OP_LIST_OR_TAGS | OP_REPEAT | OP_ESTIMATE )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<=' ')||input.LA(1)=='\"'||(input.LA(1)>='$' && input.LA(1)<=')')||(input.LA(1)>='+' && input.LA(1)<='<')||(input.LA(1)>='>' && input.LA(1)<=']')||(input.LA(1)>='_' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    public void mTokens() throws RecognitionException {
        // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:1:8: ( OP_DUE_DATE | OP_PRIORITY | OP_LIST_OR_TAGS | OP_LOCATION | OP_REPEAT | OP_ESTIMATE | STRING )
        int alt2=7;
        int LA2_0 = input.LA(1);

        if ( (LA2_0=='^') ) {
            alt2=1;
        }
        else if ( (LA2_0=='!') ) {
            alt2=2;
        }
        else if ( (LA2_0=='#') ) {
            alt2=3;
        }
        else if ( (LA2_0=='@') ) {
            int LA2_4 = input.LA(2);

            if ( ((LA2_4>='\u0000' && LA2_4<=' ')||LA2_4=='\"'||(LA2_4>='$' && LA2_4<=')')||(LA2_4>='+' && LA2_4<='<')||(LA2_4>='>' && LA2_4<=']')||(LA2_4>='_' && LA2_4<='\uFFFF')) ) {
                alt2=7;
            }
            else {
                alt2=4;}
        }
        else if ( (LA2_0=='*') ) {
            alt2=5;
        }
        else if ( (LA2_0=='=') ) {
            alt2=6;
        }
        else if ( ((LA2_0>='\u0000' && LA2_0<=' ')||LA2_0=='\"'||(LA2_0>='$' && LA2_0<=')')||(LA2_0>='+' && LA2_0<='<')||(LA2_0>='>' && LA2_0<='?')||(LA2_0>='A' && LA2_0<=']')||(LA2_0>='_' && LA2_0<='\uFFFF')) ) {
            alt2=7;
        }
        else {
            NoViableAltException nvae =
                new NoViableAltException("", 2, 0, input);

            throw nvae;
        }
        switch (alt2) {
            case 1 :
                // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:1:10: OP_DUE_DATE
                {
                mOP_DUE_DATE(); 

                }
                break;
            case 2 :
                // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:1:22: OP_PRIORITY
                {
                mOP_PRIORITY(); 

                }
                break;
            case 3 :
                // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:1:34: OP_LIST_OR_TAGS
                {
                mOP_LIST_OR_TAGS(); 

                }
                break;
            case 4 :
                // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:1:50: OP_LOCATION
                {
                mOP_LOCATION(); 

                }
                break;
            case 5 :
                // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:1:62: OP_REPEAT
                {
                mOP_REPEAT(); 

                }
                break;
            case 6 :
                // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:1:72: OP_ESTIMATE
                {
                mOP_ESTIMATE(); 

                }
                break;
            case 7 :
                // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\RtmSmartAdd.g:1:84: STRING
                {
                mSTRING(); 

                }
                break;

        }

    }


 

}