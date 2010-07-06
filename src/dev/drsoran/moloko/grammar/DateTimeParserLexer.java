// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g 2010-07-06 02:30:37

	package dev.drsoran.moloko.grammar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class DateTimeParserLexer extends Lexer {
    public static final int TODAY=7;
    public static final int WS=11;
    public static final int TONIGHT=9;
    public static final int NOW=6;
    public static final int INT=4;
    public static final int DATE_SEP=5;
    public static final int EOF=-1;
    public static final int YESTERDAY=10;
    public static final int TOMORROW=8;

    // delegates
    // delegators

    public DateTimeParserLexer() {;} 
    public DateTimeParserLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public DateTimeParserLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g"; }

    // $ANTLR start "NOW"
    public final void mNOW() throws RecognitionException {
        try {
            int _type = NOW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:92:7: ( 'now' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:92:9: 'now'
            {
            match("now"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOW"

    // $ANTLR start "TODAY"
    public final void mTODAY() throws RecognitionException {
        try {
            int _type = TODAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:94:11: ( 'tod' ( 'ay' )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:94:13: 'tod' ( 'ay' )?
            {
            match("tod"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:94:18: ( 'ay' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='a') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:94:19: 'ay'
                    {
                    match("ay"); 


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
    // $ANTLR end "TODAY"

    // $ANTLR start "TOMORROW"
    public final void mTOMORROW() throws RecognitionException {
        try {
            int _type = TOMORROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:96:11: ( 'tom' ( 'orrow' )? | 'tmr' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='t') ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1=='o') ) {
                    alt3=1;
                }
                else if ( (LA3_1=='m') ) {
                    alt3=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:96:13: 'tom' ( 'orrow' )?
                    {
                    match("tom"); 

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:96:18: ( 'orrow' )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0=='o') ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:96:19: 'orrow'
                            {
                            match("orrow"); 


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:96:31: 'tmr'
                    {
                    match("tmr"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TOMORROW"

    // $ANTLR start "TONIGHT"
    public final void mTONIGHT() throws RecognitionException {
        try {
            int _type = TONIGHT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:98:11: ( 'ton' ( 'ight' )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:98:13: 'ton' ( 'ight' )?
            {
            match("ton"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:98:18: ( 'ight' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='i') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:98:19: 'ight'
                    {
                    match("ight"); 


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
    // $ANTLR end "TONIGHT"

    // $ANTLR start "YESTERDAY"
    public final void mYESTERDAY() throws RecognitionException {
        try {
            int _type = YESTERDAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:100:11: ( 'yesterday' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:100:13: 'yesterday'
            {
            match("yesterday"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "YESTERDAY"

    // $ANTLR start "DATE_SEP"
    public final void mDATE_SEP() throws RecognitionException {
        try {
            int _type = DATE_SEP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:102:11: ( ':' | '-' | '/' | '.' | '\\u5E74' | '\\u6708' | '\\u65E5' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:
            {
            if ( (input.LA(1)>='-' && input.LA(1)<='/')||input.LA(1)==':'||input.LA(1)=='\u5E74'||input.LA(1)=='\u65E5'||input.LA(1)=='\u6708' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DATE_SEP"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:104:10: ( ( '0' .. '9' )+ )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:104:12: ( '0' .. '9' )+
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:104:12: ( '0' .. '9' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:104:12: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:106:8: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:106:10: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:8: ( NOW | TODAY | TOMORROW | TONIGHT | YESTERDAY | DATE_SEP | INT | WS )
        int alt6=8;
        alt6 = dfa6.predict(input);
        switch (alt6) {
            case 1 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:10: NOW
                {
                mNOW(); 

                }
                break;
            case 2 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:14: TODAY
                {
                mTODAY(); 

                }
                break;
            case 3 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:20: TOMORROW
                {
                mTOMORROW(); 

                }
                break;
            case 4 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:29: TONIGHT
                {
                mTONIGHT(); 

                }
                break;
            case 5 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:37: YESTERDAY
                {
                mYESTERDAY(); 

                }
                break;
            case 6 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:47: DATE_SEP
                {
                mDATE_SEP(); 

                }
                break;
            case 7 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:56: INT
                {
                mINT(); 

                }
                break;
            case 8 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:60: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA6 dfa6 = new DFA6(this);
    static final String DFA6_eotS =
        "\13\uffff";
    static final String DFA6_eofS =
        "\13\uffff";
    static final String DFA6_minS =
        "\1\11\1\uffff\1\155\4\uffff\1\144\3\uffff";
    static final String DFA6_maxS =
        "\1\u6708\1\uffff\1\157\4\uffff\1\156\3\uffff";
    static final String DFA6_acceptS =
        "\1\uffff\1\1\1\uffff\1\5\1\6\1\7\1\10\1\uffff\1\3\1\2\1\4";
    static final String DFA6_specialS =
        "\13\uffff}>";
    static final String[] DFA6_transitionS = {
            "\2\6\2\uffff\1\6\22\uffff\1\6\14\uffff\3\4\12\5\1\4\63\uffff"+
            "\1\1\5\uffff\1\2\4\uffff\1\3\u5dfa\uffff\1\4\u0770\uffff\1\4"+
            "\u0122\uffff\1\4",
            "",
            "\1\10\1\uffff\1\7",
            "",
            "",
            "",
            "",
            "\1\11\10\uffff\1\10\1\12",
            "",
            "",
            ""
    };

    static final short[] DFA6_eot = DFA.unpackEncodedString(DFA6_eotS);
    static final short[] DFA6_eof = DFA.unpackEncodedString(DFA6_eofS);
    static final char[] DFA6_min = DFA.unpackEncodedStringToUnsignedChars(DFA6_minS);
    static final char[] DFA6_max = DFA.unpackEncodedStringToUnsignedChars(DFA6_maxS);
    static final short[] DFA6_accept = DFA.unpackEncodedString(DFA6_acceptS);
    static final short[] DFA6_special = DFA.unpackEncodedString(DFA6_specialS);
    static final short[][] DFA6_transition;

    static {
        int numStates = DFA6_transitionS.length;
        DFA6_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA6_transition[i] = DFA.unpackEncodedString(DFA6_transitionS[i]);
        }
    }

    class DFA6 extends DFA {

        public DFA6(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 6;
            this.eot = DFA6_eot;
            this.eof = DFA6_eof;
            this.min = DFA6_min;
            this.max = DFA6_max;
            this.accept = DFA6_accept;
            this.special = DFA6_special;
            this.transition = DFA6_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( NOW | TODAY | TOMORROW | TONIGHT | YESTERDAY | DATE_SEP | INT | WS );";
        }
    }
 

}