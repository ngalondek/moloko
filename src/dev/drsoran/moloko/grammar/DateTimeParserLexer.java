// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g 2010-07-05 08:27:23

	package dev.drsoran.moloko.grammar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class DateTimeParserLexer extends Lexer {
    public static final int MINUTES=8;
    public static final int COLON=5;
    public static final int TODAY=14;
    public static final int WS=18;
    public static final int TONIGHT=16;
    public static final int NUMBER=12;
    public static final int SECONDS=9;
    public static final int NOW=13;
    public static final int FLOAT=6;
    public static final int INT=4;
    public static final int DATE_SEP=10;
    public static final int DOT=11;
    public static final int EOF=-1;
    public static final int YESTERDAY=17;
    public static final int HOURS=7;
    public static final int TOMORROW=15;

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

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:115:9: ( ':' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:115:11: ':'
            {
            match(':'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:118:7: ( '.' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:118:9: '.'
            {
            match('.'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "NUMBER"
    public final void mNUMBER() throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:121:11: ( '0' .. '9' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:121:13: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "NUMBER"

    // $ANTLR start "NOW"
    public final void mNOW() throws RecognitionException {
        try {
            int _type = NOW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:123:7: ( 'now' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:123:9: 'now'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:125:11: ( 'tod' ( 'ay' )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:125:13: 'tod' ( 'ay' )?
            {
            match("tod"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:125:18: ( 'ay' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='a') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:125:19: 'ay'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:127:11: ( 'tom' ( 'orrow' )? | 'tmr' )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:127:13: 'tom' ( 'orrow' )?
                    {
                    match("tom"); 

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:127:18: ( 'orrow' )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0=='o') ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:127:19: 'orrow'
                            {
                            match("orrow"); 


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:127:31: 'tmr'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:129:11: ( 'ton' ( 'ight' )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:129:13: 'ton' ( 'ight' )?
            {
            match("ton"); 

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:129:18: ( 'ight' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='i') ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:129:19: 'ight'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:131:11: ( 'yesterday' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:131:13: 'yesterday'
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

    // $ANTLR start "HOURS"
    public final void mHOURS() throws RecognitionException {
        try {
            int _type = HOURS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:133:11: ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' )
            int alt5=5;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:133:13: 'hours'
                    {
                    match("hours"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:133:23: 'hour'
                    {
                    match("hour"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:133:32: 'hrs'
                    {
                    match("hrs"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:133:40: 'hr'
                    {
                    match("hr"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:133:47: 'h'
                    {
                    match('h'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HOURS"

    // $ANTLR start "MINUTES"
    public final void mMINUTES() throws RecognitionException {
        try {
            int _type = MINUTES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:135:11: ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' )
            int alt6=5;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:135:13: 'minutes'
                    {
                    match("minutes"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:135:25: 'minute'
                    {
                    match("minute"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:135:36: 'mins'
                    {
                    match("mins"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:135:45: 'min'
                    {
                    match("min"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:135:53: 'm'
                    {
                    match('m'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUTES"

    // $ANTLR start "SECONDS"
    public final void mSECONDS() throws RecognitionException {
        try {
            int _type = SECONDS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:137:11: ( 'seconds' | 'second' | 'secs' | 'sec' | 's' )
            int alt7=5;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:137:13: 'seconds'
                    {
                    match("seconds"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:137:25: 'second'
                    {
                    match("second"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:137:36: 'secs'
                    {
                    match("secs"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:137:45: 'sec'
                    {
                    match("sec"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:137:53: 's'
                    {
                    match('s'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SECONDS"

    // $ANTLR start "DATE_SEP"
    public final void mDATE_SEP() throws RecognitionException {
        try {
            int _type = DATE_SEP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:139:11: ( COLON | '-' | '/' | DOT | '\\u5E74' | '\\u6708' | '\\u65E5' )
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:141:10: ( ( NUMBER )+ )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:141:12: ( NUMBER )+
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:141:12: ( NUMBER )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:141:12: NUMBER
            	    {
            	    mNUMBER(); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:143:11: ( ( NUMBER )+ DOT ( NUMBER )* | DOT ( NUMBER )+ | ( NUMBER )+ )
            int alt13=3;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:143:13: ( NUMBER )+ DOT ( NUMBER )*
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:143:13: ( NUMBER )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:143:13: NUMBER
                    	    {
                    	    mNUMBER(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    mDOT(); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:143:25: ( NUMBER )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:143:25: NUMBER
                    	    {
                    	    mNUMBER(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:144:9: DOT ( NUMBER )+
                    {
                    mDOT(); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:144:13: ( NUMBER )+
                    int cnt11=0;
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:144:13: NUMBER
                    	    {
                    	    mNUMBER(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt11 >= 1 ) break loop11;
                                EarlyExitException eee =
                                    new EarlyExitException(11, input);
                                throw eee;
                        }
                        cnt11++;
                    } while (true);


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:145:9: ( NUMBER )+
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:145:9: ( NUMBER )+
                    int cnt12=0;
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:145:9: NUMBER
                    	    {
                    	    mNUMBER(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt12 >= 1 ) break loop12;
                                EarlyExitException eee =
                                    new EarlyExitException(12, input);
                                throw eee;
                        }
                        cnt12++;
                    } while (true);


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:148:8: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:148:10: ( ' ' | '\\t' | '\\r' | '\\n' )
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
        // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:8: ( NOW | TODAY | TOMORROW | TONIGHT | YESTERDAY | HOURS | MINUTES | SECONDS | DATE_SEP | INT | FLOAT | WS )
        int alt14=12;
        alt14 = dfa14.predict(input);
        switch (alt14) {
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
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:47: HOURS
                {
                mHOURS(); 

                }
                break;
            case 7 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:53: MINUTES
                {
                mMINUTES(); 

                }
                break;
            case 8 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:61: SECONDS
                {
                mSECONDS(); 

                }
                break;
            case 9 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:69: DATE_SEP
                {
                mDATE_SEP(); 

                }
                break;
            case 10 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:78: INT
                {
                mINT(); 

                }
                break;
            case 11 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:82: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 12 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\DateTimeParser.g:1:88: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA5 dfa5 = new DFA5(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA13 dfa13 = new DFA13(this);
    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA5_eotS =
        "\1\uffff\1\4\1\uffff\1\7\4\uffff\1\12\2\uffff";
    static final String DFA5_eofS =
        "\13\uffff";
    static final String DFA5_minS =
        "\1\150\1\157\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
    static final String DFA5_maxS =
        "\1\150\1\162\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
    static final String DFA5_acceptS =
        "\4\uffff\1\5\1\uffff\1\3\1\4\1\uffff\1\1\1\2";
    static final String DFA5_specialS =
        "\13\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\1",
            "\1\2\2\uffff\1\3",
            "\1\5",
            "\1\6",
            "",
            "\1\10",
            "",
            "",
            "\1\11",
            "",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "133:1: HOURS : ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' );";
        }
    }
    static final String DFA6_eotS =
        "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
    static final String DFA6_eofS =
        "\14\uffff";
    static final String DFA6_minS =
        "\1\155\1\151\1\156\1\uffff\1\163\1\164\2\uffff\1\145\1\163\2\uffff";
    static final String DFA6_maxS =
        "\1\155\1\151\1\156\1\uffff\1\165\1\164\2\uffff\1\145\1\163\2\uffff";
    static final String DFA6_acceptS =
        "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
    static final String DFA6_specialS =
        "\14\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\1",
            "\1\2",
            "\1\4",
            "",
            "\1\6\1\uffff\1\5",
            "\1\10",
            "",
            "",
            "\1\11",
            "\1\12",
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
            return "135:1: MINUTES : ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' );";
        }
    }
    static final String DFA7_eotS =
        "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
    static final String DFA7_eofS =
        "\14\uffff";
    static final String DFA7_minS =
        "\1\163\1\145\1\143\1\uffff\1\157\1\156\2\uffff\1\144\1\163\2\uffff";
    static final String DFA7_maxS =
        "\1\163\1\145\1\143\1\uffff\1\163\1\156\2\uffff\1\144\1\163\2\uffff";
    static final String DFA7_acceptS =
        "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
    static final String DFA7_specialS =
        "\14\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\1",
            "\1\2",
            "\1\4",
            "",
            "\1\5\3\uffff\1\6",
            "\1\10",
            "",
            "",
            "\1\11",
            "\1\12",
            "",
            ""
    };

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "137:1: SECONDS : ( 'seconds' | 'second' | 'secs' | 'sec' | 's' );";
        }
    }
    static final String DFA13_eotS =
        "\1\uffff\1\3\3\uffff";
    static final String DFA13_eofS =
        "\5\uffff";
    static final String DFA13_minS =
        "\2\56\3\uffff";
    static final String DFA13_maxS =
        "\2\71\3\uffff";
    static final String DFA13_acceptS =
        "\2\uffff\1\2\1\3\1\1";
    static final String DFA13_specialS =
        "\5\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\4\1\uffff\12\1",
            "",
            "",
            ""
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "143:1: FLOAT : ( ( NUMBER )+ DOT ( NUMBER )* | DOT ( NUMBER )+ | ( NUMBER )+ );";
        }
    }
    static final String DFA14_eotS =
        "\7\uffff\1\11\1\16\10\uffff";
    static final String DFA14_eofS =
        "\21\uffff";
    static final String DFA14_minS =
        "\1\11\1\uffff\1\155\4\uffff\1\60\1\56\2\uffff\1\144\5\uffff";
    static final String DFA14_maxS =
        "\1\u6708\1\uffff\1\157\4\uffff\2\71\2\uffff\1\156\5\uffff";
    static final String DFA14_acceptS =
        "\1\uffff\1\1\1\uffff\1\5\1\6\1\7\1\10\2\uffff\1\11\1\14\1\uffff"+
        "\1\3\1\13\1\12\1\2\1\4";
    static final String DFA14_specialS =
        "\21\uffff}>";
    static final String[] DFA14_transitionS = {
            "\2\12\2\uffff\1\12\22\uffff\1\12\14\uffff\1\11\1\7\1\11\12"+
            "\10\1\11\55\uffff\1\4\4\uffff\1\5\1\1\4\uffff\1\6\1\2\4\uffff"+
            "\1\3\u5dfa\uffff\1\11\u0770\uffff\1\11\u0122\uffff\1\11",
            "",
            "\1\14\1\uffff\1\13",
            "",
            "",
            "",
            "",
            "\12\15",
            "\1\15\1\uffff\12\10",
            "",
            "",
            "\1\17\10\uffff\1\14\1\20",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( NOW | TODAY | TOMORROW | TONIGHT | YESTERDAY | HOURS | MINUTES | SECONDS | DATE_SEP | INT | FLOAT | WS );";
        }
    }
 

}