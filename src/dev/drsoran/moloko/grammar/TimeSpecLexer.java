// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g 2010-07-05 09:13:12

	package dev.drsoran.moloko.grammar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TimeSpecLexer extends Lexer {
    public static final int MINUTES=7;
    public static final int WS=10;
    public static final int TIMESPEC=9;
    public static final int SECONDS=8;
    public static final int INT=4;
    public static final int EOF=-1;
    public static final int HOURS=6;
    public static final int STRING=5;

    	private final static long SECOND_MILLIS = 1000;
    		
    	private final static long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    	
    	private final static long HOUR_MILLIS   = 60 * MINUTE_MILLIS;
    	
    	private long result = -1;
    	
    	private final static long parseString( String value ) throws NumberFormatException
    	{
    		if ( value.indexOf( '.' ) != -1 )
    			return (long) Float.parseFloat( value );
    		else
    			return Integer.parseInt( value );
    	}
    	
    	public long getResult()
    	{
    		while ( nextToken() != Token.EOF_TOKEN )
          {
          }
          
          return result;
    	}


    // delegates
    // delegators

    public TimeSpecLexer() {;} 
    public TimeSpecLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public TimeSpecLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g"; }

    // $ANTLR start "TIMESPEC"
    public final void mTIMESPEC() throws RecognitionException {
        try {
            int _type = TIMESPEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken h=null;
            CommonToken m=null;
            CommonToken s=null;


            		result = 0;
            	
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:46:2: (h= INT ':' m= INT ( ':' s= INT )? | (h= STRING HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? )
            int alt5=2;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:46:9: h= INT ':' m= INT ( ':' s= INT )?
                    {
                    int hStart50 = getCharIndex();
                    mINT(); 
                    h = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, hStart50, getCharIndex()-1);
                     result += Integer.parseInt( (h!=null?h.getText():null) ) * HOUR_MILLIS;   
                    match(':'); 
                    int mStart64 = getCharIndex();
                    mINT(); 
                    m = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, mStart64, getCharIndex()-1);
                     result += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:48:4: ( ':' s= INT )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==':') ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:48:5: ':' s= INT
                            {
                            match(':'); 
                            int sStart78 = getCharIndex();
                            mINT(); 
                            s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart78, getCharIndex()-1);

                            }
                            break;

                    }

                     result += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:50:4: (h= STRING HOURS )? (m= INT MINUTES )? (s= INT SECONDS )?
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:50:4: (h= STRING HOURS )?
                    int alt2=2;
                    alt2 = dfa2.predict(input);
                    switch (alt2) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:50:7: h= STRING HOURS
                            {
                            int hStart95 = getCharIndex();
                            mSTRING(); 
                            h = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, hStart95, getCharIndex()-1);
                             result += parseString( h.getText() ) * HOUR_MILLIS;	  
                            mHOURS(); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:51:4: (m= INT MINUTES )?
                    int alt3=2;
                    alt3 = dfa3.predict(input);
                    switch (alt3) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:51:10: m= INT MINUTES
                            {
                            int mStart117 = getCharIndex();
                            mINT(); 
                            m = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, mStart117, getCharIndex()-1);
                             result += Integer.parseInt( (h!=null?h.getText():null) ) * MINUTE_MILLIS; 
                            mMINUTES(); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:52:4: (s= INT SECONDS )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:52:9: s= INT SECONDS
                            {
                            int sStart136 = getCharIndex();
                            mINT(); 
                            s = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, sStart136, getCharIndex()-1);
                             result += Integer.parseInt( (h!=null?h.getText():null) ) * SECOND_MILLIS; 
                            mSECONDS(); 

                            }
                            break;

                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMESPEC"

    // $ANTLR start "HOURS"
    public final void mHOURS() throws RecognitionException {
        try {
            int _type = HOURS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:56:10: ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' )
            int alt6=5;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:56:12: 'hours'
                    {
                    match("hours"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:56:22: 'hour'
                    {
                    match("hour"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:56:31: 'hrs'
                    {
                    match("hrs"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:56:39: 'hr'
                    {
                    match("hr"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:56:46: 'h'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:58:10: ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' )
            int alt7=5;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:58:12: 'minutes'
                    {
                    match("minutes"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:58:24: 'minute'
                    {
                    match("minute"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:58:35: 'mins'
                    {
                    match("mins"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:58:44: 'min'
                    {
                    match("min"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:58:52: 'm'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:60:10: ( 'seconds' | 'second' | 'secs' | 'sec' | 's' )
            int alt8=5;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:60:12: 'seconds'
                    {
                    match("seconds"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:60:24: 'second'
                    {
                    match("second"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:60:35: 'secs'
                    {
                    match("secs"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:60:44: 'sec'
                    {
                    match("sec"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:60:52: 's'
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

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:62:7: ( ( '0' .. '9' )+ )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:62:9: ( '0' .. '9' )+
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:62:9: ( '0' .. '9' )+
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
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:62:9: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:64:11: ( (~ ( '\"' | ' ' | '(' | ')' ) )+ )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:64:13: (~ ( '\"' | ' ' | '(' | ')' ) )+
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:64:13: (~ ( '\"' | ' ' | '(' | ')' ) )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>='\u0000' && LA10_0<='\u001F')||LA10_0=='!'||(LA10_0>='#' && LA10_0<='\'')||(LA10_0>='*' && LA10_0<='\uFFFF')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:64:13: ~ ( '\"' | ' ' | '(' | ')' )
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
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:66:8: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:66:10: ( ' ' | '\\t' | '\\r' | '\\n' )
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
        // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:1:8: ( TIMESPEC | HOURS | MINUTES | SECONDS | INT | STRING | WS )
        int alt11=7;
        alt11 = dfa11.predict(input);
        switch (alt11) {
            case 1 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:1:10: TIMESPEC
                {
                mTIMESPEC(); 

                }
                break;
            case 2 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:1:19: HOURS
                {
                mHOURS(); 

                }
                break;
            case 3 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:1:25: MINUTES
                {
                mMINUTES(); 

                }
                break;
            case 4 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:1:33: SECONDS
                {
                mSECONDS(); 

                }
                break;
            case 5 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:1:41: INT
                {
                mINT(); 

                }
                break;
            case 6 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:1:45: STRING
                {
                mSTRING(); 

                }
                break;
            case 7 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpecLexer.g:1:52: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA5 dfa5 = new DFA5(this);
    protected DFA2 dfa2 = new DFA2(this);
    protected DFA3 dfa3 = new DFA3(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA11 dfa11 = new DFA11(this);
    static final String DFA5_eotS =
        "\1\2\3\uffff\1\5\2\uffff\1\5";
    static final String DFA5_eofS =
        "\10\uffff";
    static final String DFA5_minS =
        "\1\60\1\0\1\uffff\2\0\1\uffff\2\0";
    static final String DFA5_maxS =
        "\1\71\1\uffff\1\uffff\2\uffff\1\uffff\2\uffff";
    static final String DFA5_acceptS =
        "\2\uffff\1\2\2\uffff\1\1\2\uffff";
    static final String DFA5_specialS =
        "\1\uffff\1\2\1\uffff\1\4\1\1\1\uffff\1\0\1\3}>";
    static final String[] DFA5_transitionS = {
            "\12\1",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\1\1\3\uffc5\2",
            "",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\4\uffc6\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\4\1\6\uffc5\2",
            "",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\7\uffc6\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\7\uffc6\2"
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
            return "41:1: TIMESPEC : (h= INT ':' m= INT ( ':' s= INT )? | (h= STRING HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA5_6 = input.LA(1);

                        s = -1;
                        if ( ((LA5_6>='\u0000' && LA5_6<='\u001F')||LA5_6=='!'||(LA5_6>='#' && LA5_6<='\'')||(LA5_6>='*' && LA5_6<='/')||(LA5_6>=':' && LA5_6<='\uFFFF')) ) {s = 2;}

                        else if ( ((LA5_6>='0' && LA5_6<='9')) ) {s = 7;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA5_4 = input.LA(1);

                        s = -1;
                        if ( ((LA5_4>='\u0000' && LA5_4<='\u001F')||LA5_4=='!'||(LA5_4>='#' && LA5_4<='\'')||(LA5_4>='*' && LA5_4<='/')||(LA5_4>=';' && LA5_4<='\uFFFF')) ) {s = 2;}

                        else if ( (LA5_4==':') ) {s = 6;}

                        else if ( ((LA5_4>='0' && LA5_4<='9')) ) {s = 4;}

                        else s = 5;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA5_1 = input.LA(1);

                        s = -1;
                        if ( ((LA5_1>='\u0000' && LA5_1<='\u001F')||LA5_1=='!'||(LA5_1>='#' && LA5_1<='\'')||(LA5_1>='*' && LA5_1<='/')||(LA5_1>=';' && LA5_1<='\uFFFF')) ) {s = 2;}

                        else if ( (LA5_1==':') ) {s = 3;}

                        else if ( ((LA5_1>='0' && LA5_1<='9')) ) {s = 1;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA5_7 = input.LA(1);

                        s = -1;
                        if ( ((LA5_7>='0' && LA5_7<='9')) ) {s = 7;}

                        else if ( ((LA5_7>='\u0000' && LA5_7<='\u001F')||LA5_7=='!'||(LA5_7>='#' && LA5_7<='\'')||(LA5_7>='*' && LA5_7<='/')||(LA5_7>=':' && LA5_7<='\uFFFF')) ) {s = 2;}

                        else s = 5;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA5_3 = input.LA(1);

                        s = -1;
                        if ( ((LA5_3>='\u0000' && LA5_3<='\u001F')||LA5_3=='!'||(LA5_3>='#' && LA5_3<='\'')||(LA5_3>='*' && LA5_3<='/')||(LA5_3>=':' && LA5_3<='\uFFFF')) ) {s = 2;}

                        else if ( ((LA5_3>='0' && LA5_3<='9')) ) {s = 4;}

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 5, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA2_eotS =
        "\1\3\3\uffff\2\3\3\uffff\2\3\1\uffff\1\3\1\uffff\1\3\2\uffff\4"+
        "\3";
    static final String DFA2_eofS =
        "\25\uffff";
    static final String DFA2_minS =
        "\2\0\2\uffff\21\0";
    static final String DFA2_maxS =
        "\2\uffff\2\uffff\21\uffff";
    static final String DFA2_acceptS =
        "\2\uffff\1\1\1\2\21\uffff";
    static final String DFA2_specialS =
        "\1\14\1\6\2\uffff\1\10\1\12\1\0\1\13\1\2\1\1\1\21\1\17\1\20\1\4"+
        "\1\3\1\15\1\5\1\11\1\7\1\16\1\22}>";
    static final String[] DFA2_transitionS = {
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\1\uffc6\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\1\63\2\1\4\5"+
            "\2\1\5\uff8c\2",
            "",
            "",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\7\57\2\1\6\uff96"+
            "\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\73\2\1\10\uff9a\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\104\2\1\11\uff91\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\7\71\2\1\5\uff8c"+
            "\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\71\2\1\12\uff9c\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\7\71\2\1\14\1"+
            "\2\1\13\uff8a\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\105\2\1\15\3\2\1\16"+
            "\uff8c\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\112\2\1\17\uff8b\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\7\uffc6\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\104\2\1\20\uff91\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\uffd6\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\73\2\1\21\uff9a\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\72\2\1\22\uff9b\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\7\71\2\1\23\uff8c"+
            "\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\111\2\1\24\uff8c\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\6\2\12\7\uffc6\2",
            "\40\2\1\uffff\1\2\1\uffff\5\2\2\uffff\uffd6\2"
    };

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "50:4: (h= STRING HOURS )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA2_6 = input.LA(1);

                        s = -1;
                        if ( (LA2_6=='n') ) {s = 9;}

                        else if ( ((LA2_6>='\u0000' && LA2_6<='\u001F')||LA2_6=='!'||(LA2_6>='#' && LA2_6<='\'')||(LA2_6>='*' && LA2_6<='m')||(LA2_6>='o' && LA2_6<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA2_9 = input.LA(1);

                        s = -1;
                        if ( (LA2_9=='u') ) {s = 11;}

                        else if ( (LA2_9=='s') ) {s = 12;}

                        else if ( ((LA2_9>='\u0000' && LA2_9<='\u001F')||LA2_9=='!'||(LA2_9>='#' && LA2_9<='\'')||(LA2_9>='*' && LA2_9<='/')||(LA2_9>=':' && LA2_9<='r')||LA2_9=='t'||(LA2_9>='v' && LA2_9<='\uFFFF')) ) {s = 2;}

                        else if ( ((LA2_9>='0' && LA2_9<='9')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA2_8 = input.LA(1);

                        s = -1;
                        if ( (LA2_8=='c') ) {s = 10;}

                        else if ( ((LA2_8>='\u0000' && LA2_8<='\u001F')||LA2_8=='!'||(LA2_8>='#' && LA2_8<='\'')||(LA2_8>='*' && LA2_8<='b')||(LA2_8>='d' && LA2_8<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA2_14 = input.LA(1);

                        s = -1;
                        if ( ((LA2_14>='\u0000' && LA2_14<='\u001F')||LA2_14=='!'||(LA2_14>='#' && LA2_14<='\'')||(LA2_14>='*' && LA2_14<='\uFFFF')) ) {s = 2;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA2_13 = input.LA(1);

                        s = -1;
                        if ( (LA2_13=='n') ) {s = 16;}

                        else if ( ((LA2_13>='\u0000' && LA2_13<='\u001F')||LA2_13=='!'||(LA2_13>='#' && LA2_13<='\'')||(LA2_13>='*' && LA2_13<='m')||(LA2_13>='o' && LA2_13<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA2_16 = input.LA(1);

                        s = -1;
                        if ( (LA2_16=='d') ) {s = 18;}

                        else if ( ((LA2_16>='\u0000' && LA2_16<='\u001F')||LA2_16=='!'||(LA2_16>='#' && LA2_16<='\'')||(LA2_16>='*' && LA2_16<='c')||(LA2_16>='e' && LA2_16<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA2_1 = input.LA(1);

                        s = -1;
                        if ( ((LA2_1>='\u0000' && LA2_1<='\u001F')||LA2_1=='!'||(LA2_1>='#' && LA2_1<='\'')||(LA2_1>='*' && LA2_1<='/')||(LA2_1>=':' && LA2_1<='l')||(LA2_1>='n' && LA2_1<='r')||(LA2_1>='t' && LA2_1<='\uFFFF')) ) {s = 2;}

                        else if ( (LA2_1=='m') ) {s = 4;}

                        else if ( ((LA2_1>='0' && LA2_1<='9')) ) {s = 1;}

                        else if ( (LA2_1=='s') ) {s = 5;}

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA2_18 = input.LA(1);

                        s = -1;
                        if ( (LA2_18=='s') ) {s = 20;}

                        else if ( ((LA2_18>='\u0000' && LA2_18<='\u001F')||LA2_18=='!'||(LA2_18>='#' && LA2_18<='\'')||(LA2_18>='*' && LA2_18<='r')||(LA2_18>='t' && LA2_18<='\uFFFF')) ) {s = 2;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA2_4 = input.LA(1);

                        s = -1;
                        if ( (LA2_4=='i') ) {s = 6;}

                        else if ( ((LA2_4>='\u0000' && LA2_4<='\u001F')||LA2_4=='!'||(LA2_4>='#' && LA2_4<='\'')||(LA2_4>='*' && LA2_4<='/')||(LA2_4>=':' && LA2_4<='h')||(LA2_4>='j' && LA2_4<='\uFFFF')) ) {s = 2;}

                        else if ( ((LA2_4>='0' && LA2_4<='9')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA2_17 = input.LA(1);

                        s = -1;
                        if ( (LA2_17=='s') ) {s = 19;}

                        else if ( ((LA2_17>='0' && LA2_17<='9')) ) {s = 7;}

                        else if ( ((LA2_17>='\u0000' && LA2_17<='\u001F')||LA2_17=='!'||(LA2_17>='#' && LA2_17<='\'')||(LA2_17>='*' && LA2_17<='/')||(LA2_17>=':' && LA2_17<='r')||(LA2_17>='t' && LA2_17<='\uFFFF')) ) {s = 2;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA2_5 = input.LA(1);

                        s = -1;
                        if ( (LA2_5=='e') ) {s = 8;}

                        else if ( ((LA2_5>='\u0000' && LA2_5<='\u001F')||LA2_5=='!'||(LA2_5>='#' && LA2_5<='\'')||(LA2_5>='*' && LA2_5<='d')||(LA2_5>='f' && LA2_5<='\uFFFF')) ) {s = 2;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA2_7 = input.LA(1);

                        s = -1;
                        if ( ((LA2_7>='\u0000' && LA2_7<='\u001F')||LA2_7=='!'||(LA2_7>='#' && LA2_7<='\'')||(LA2_7>='*' && LA2_7<='/')||(LA2_7>=':' && LA2_7<='r')||(LA2_7>='t' && LA2_7<='\uFFFF')) ) {s = 2;}

                        else if ( (LA2_7=='s') ) {s = 5;}

                        else if ( ((LA2_7>='0' && LA2_7<='9')) ) {s = 7;}

                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA2_0 = input.LA(1);

                        s = -1;
                        if ( ((LA2_0>='0' && LA2_0<='9')) ) {s = 1;}

                        else if ( ((LA2_0>='\u0000' && LA2_0<='\u001F')||LA2_0=='!'||(LA2_0>='#' && LA2_0<='\'')||(LA2_0>='*' && LA2_0<='/')||(LA2_0>=':' && LA2_0<='\uFFFF')) ) {s = 2;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA2_15 = input.LA(1);

                        s = -1;
                        if ( (LA2_15=='e') ) {s = 17;}

                        else if ( ((LA2_15>='\u0000' && LA2_15<='\u001F')||LA2_15=='!'||(LA2_15>='#' && LA2_15<='\'')||(LA2_15>='*' && LA2_15<='d')||(LA2_15>='f' && LA2_15<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA2_19 = input.LA(1);

                        s = -1;
                        if ( ((LA2_19>='\u0000' && LA2_19<='\u001F')||LA2_19=='!'||(LA2_19>='#' && LA2_19<='\'')||(LA2_19>='*' && LA2_19<='/')||(LA2_19>=':' && LA2_19<='\uFFFF')) ) {s = 2;}

                        else if ( ((LA2_19>='0' && LA2_19<='9')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA2_11 = input.LA(1);

                        s = -1;
                        if ( (LA2_11=='t') ) {s = 15;}

                        else if ( ((LA2_11>='\u0000' && LA2_11<='\u001F')||LA2_11=='!'||(LA2_11>='#' && LA2_11<='\'')||(LA2_11>='*' && LA2_11<='s')||(LA2_11>='u' && LA2_11<='\uFFFF')) ) {s = 2;}

                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA2_12 = input.LA(1);

                        s = -1;
                        if ( ((LA2_12>='\u0000' && LA2_12<='\u001F')||LA2_12=='!'||(LA2_12>='#' && LA2_12<='\'')||(LA2_12>='*' && LA2_12<='/')||(LA2_12>=':' && LA2_12<='\uFFFF')) ) {s = 2;}

                        else if ( ((LA2_12>='0' && LA2_12<='9')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA2_10 = input.LA(1);

                        s = -1;
                        if ( (LA2_10=='o') ) {s = 13;}

                        else if ( (LA2_10=='s') ) {s = 14;}

                        else if ( ((LA2_10>='\u0000' && LA2_10<='\u001F')||LA2_10=='!'||(LA2_10>='#' && LA2_10<='\'')||(LA2_10>='*' && LA2_10<='n')||(LA2_10>='p' && LA2_10<='r')||(LA2_10>='t' && LA2_10<='\uFFFF')) ) {s = 2;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA2_20 = input.LA(1);

                        s = -1;
                        if ( ((LA2_20>='\u0000' && LA2_20<='\u001F')||LA2_20=='!'||(LA2_20>='#' && LA2_20<='\'')||(LA2_20>='*' && LA2_20<='\uFFFF')) ) {s = 2;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 2, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA3_eotS =
        "\1\2\3\uffff";
    static final String DFA3_eofS =
        "\4\uffff";
    static final String DFA3_minS =
        "\2\60\2\uffff";
    static final String DFA3_maxS =
        "\1\71\1\163\2\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA3_specialS =
        "\4\uffff}>";
    static final String[] DFA3_transitionS = {
            "\12\1",
            "\12\1\63\uffff\1\3\5\uffff\1\2",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "51:4: (m= INT MINUTES )?";
        }
    }
    static final String DFA6_eotS =
        "\1\uffff\1\4\1\uffff\1\7\4\uffff\1\12\2\uffff";
    static final String DFA6_eofS =
        "\13\uffff";
    static final String DFA6_minS =
        "\1\150\1\157\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
    static final String DFA6_maxS =
        "\1\150\1\162\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
    static final String DFA6_acceptS =
        "\4\uffff\1\5\1\uffff\1\3\1\4\1\uffff\1\1\1\2";
    static final String DFA6_specialS =
        "\13\uffff}>";
    static final String[] DFA6_transitionS = {
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
            return "56:1: HOURS : ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' );";
        }
    }
    static final String DFA7_eotS =
        "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
    static final String DFA7_eofS =
        "\14\uffff";
    static final String DFA7_minS =
        "\1\155\1\151\1\156\1\uffff\1\163\1\164\2\uffff\1\145\1\163\2\uffff";
    static final String DFA7_maxS =
        "\1\155\1\151\1\156\1\uffff\1\165\1\164\2\uffff\1\145\1\163\2\uffff";
    static final String DFA7_acceptS =
        "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
    static final String DFA7_specialS =
        "\14\uffff}>";
    static final String[] DFA7_transitionS = {
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
            return "58:1: MINUTES : ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' );";
        }
    }
    static final String DFA8_eotS =
        "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
    static final String DFA8_eofS =
        "\14\uffff";
    static final String DFA8_minS =
        "\1\163\1\145\1\143\1\uffff\1\157\1\156\2\uffff\1\144\1\163\2\uffff";
    static final String DFA8_maxS =
        "\1\163\1\145\1\143\1\uffff\1\163\1\156\2\uffff\1\144\1\163\2\uffff";
    static final String DFA8_acceptS =
        "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
    static final String DFA8_specialS =
        "\14\uffff}>";
    static final String[] DFA8_transitionS = {
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

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "60:1: SECONDS : ( 'seconds' | 'second' | 'secs' | 'sec' | 's' );";
        }
    }
    static final String DFA11_eotS =
        "\1\3\1\11\1\20\1\uffff\1\22\1\24\2\25\2\uffff\3\3\2\25\1\20\1\uffff"+
        "\1\25\1\uffff\1\25\2\uffff\3\25\1\3\2\25\1\3\1\25\1\20\1\22\1\24"+
        "\1\3\1\25\2\3\1\25\1\20\1\25\1\22\1\25\1\24\1\25\2\3\1\25\2\3\1"+
        "\20\3\25\1\3\1\25\1\22\1\24\2\3\1\22\1\24\2\3";
    static final String DFA11_eofS =
        "\77\uffff";
    static final String DFA11_minS =
        "\3\0\1\uffff\4\0\2\uffff\6\0\1\uffff\1\0\1\uffff\1\0\2\uffff\51"+
        "\0";
    static final String DFA11_maxS =
        "\3\uffff\1\uffff\4\uffff\2\uffff\6\uffff\1\uffff\1\uffff\1\uffff"+
        "\1\uffff\2\uffff\51\uffff";
    static final String DFA11_acceptS =
        "\3\uffff\1\1\4\uffff\1\7\1\5\6\uffff\1\2\1\uffff\1\3\1\uffff\1"+
        "\4\1\6\51\uffff";
    static final String DFA11_specialS =
        "\1\0\1\53\1\10\1\uffff\1\37\1\4\1\34\1\33\2\uffff\1\24\1\43\1\15"+
        "\1\41\1\22\1\17\1\uffff\1\57\1\uffff\1\66\2\uffff\1\64\1\27\1\25"+
        "\1\13\1\44\1\60\1\50\1\20\1\67\1\32\1\3\1\26\1\23\1\54\1\1\1\40"+
        "\1\30\1\51\1\7\1\11\1\62\1\46\1\35\1\36\1\21\1\12\1\56\1\55\1\47"+
        "\1\6\1\45\1\61\1\16\1\5\1\65\1\52\1\14\1\31\1\63\1\42\1\2}>";
    static final String[] DFA11_transitionS = {
            "\11\7\2\6\2\7\1\6\22\7\1\10\1\7\1\uffff\5\7\2\uffff\6\7\12"+
            "\1\56\7\1\2\4\7\1\4\5\7\1\5\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\1\1\15\55\7\1"+
            "\13\4\7\1\12\5\7\1\14\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\6\7\1\16\2"+
            "\7\1\17\uff8d\7",
            "",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\1\21\uff96"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\73\7\1\23\2\7\1\13\uff97"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\uff97\7",
            "",
            "",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\27\56\7\1\13"+
            "\1\26\uff96\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\32\56\7\1\13"+
            "\6\7\1\30\2\7\1\31\uff8d\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\73\7\1\33\2\7\1\13\uff97"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\34\56\7\1\13"+
            "\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\14\7\1\35"+
            "\uff8a\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\12\7\1\36"+
            "\uff8c\7",
            "",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\5\7\1\37\uff91"+
            "\7",
            "",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\71\7\1\40\4\7\1\13\uff97"+
            "\7",
            "",
            "",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\5\7\1\41\uff91"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\27\56\7\1\13"+
            "\12\7\1\14\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\14\7\1\42"+
            "\uff8a\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\32\56\7\1\13"+
            "\12\7\1\43\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\32\56\7\1\13"+
            "\4\7\1\12\5\7\1\14\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\71\7\1\44\4\7\1\13\uff97"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\34\1\45\55\7"+
            "\1\13\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\11\7\1\46"+
            "\uff8d\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\12\7\1\50"+
            "\1\7\1\47\uff8a\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\6\7\1\51\3"+
            "\7\1\52\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\27\56\7\1\13"+
            "\12\7\1\54\1\7\1\53\uff8a\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\11\7\1\55"+
            "\uff8d\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\32\56\7\1\13"+
            "\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\6\7\1\56\3"+
            "\7\1\57\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\60\56\7\1\13"+
            "\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\12\7\1\61"+
            "\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\13\7\1\62"+
            "\uff8b\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\5\7\1\63\uff91"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\13\7\1\64"+
            "\uff8b\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\27\56\7\1\13"+
            "\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\32\56\7\1\13"+
            "\12\7\1\65\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\5\7\1\66\uff91"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\60\56\7\1\13"+
            "\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\73\7\1\67\2\7\1\13\uff97"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\72\7\1\70\3\7\1\13\uff97"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\73\7\1\71\2\7\1\13\uff97"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\32\56\7\1\13"+
            "\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\72\7\1\72\3\7\1\13\uff97"+
            "\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\12\7\1\73"+
            "\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\12\7\1\74"+
            "\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\27\56\7\1\13"+
            "\12\7\1\75\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\12\7\1\76"+
            "\uff8c\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\6\7\12\27\56\7\1\13"+
            "\uff97\7",
            "\40\7\1\uffff\1\7\1\uffff\5\7\2\uffff\76\7\1\13\uff97\7"
    };

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( TIMESPEC | HOURS | MINUTES | SECONDS | INT | STRING | WS );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA11_0 = input.LA(1);

                        s = -1;
                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {s = 1;}

                        else if ( (LA11_0=='h') ) {s = 2;}

                        else if ( (LA11_0=='m') ) {s = 4;}

                        else if ( (LA11_0=='s') ) {s = 5;}

                        else if ( ((LA11_0>='\t' && LA11_0<='\n')||LA11_0=='\r') ) {s = 6;}

                        else if ( ((LA11_0>='\u0000' && LA11_0<='\b')||(LA11_0>='\u000B' && LA11_0<='\f')||(LA11_0>='\u000E' && LA11_0<='\u001F')||LA11_0=='!'||(LA11_0>='#' && LA11_0<='\'')||(LA11_0>='*' && LA11_0<='/')||(LA11_0>=':' && LA11_0<='g')||(LA11_0>='i' && LA11_0<='l')||(LA11_0>='n' && LA11_0<='r')||(LA11_0>='t' && LA11_0<='\uFFFF')) ) {s = 7;}

                        else if ( (LA11_0==' ') ) {s = 8;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA11_36 = input.LA(1);

                        s = -1;
                        if ( (LA11_36=='o') ) {s = 46;}

                        else if ( (LA11_36=='s') ) {s = 47;}

                        else if ( (LA11_36=='h') ) {s = 11;}

                        else if ( ((LA11_36>='\u0000' && LA11_36<='\u001F')||LA11_36=='!'||(LA11_36>='#' && LA11_36<='\'')||(LA11_36>='*' && LA11_36<='g')||(LA11_36>='i' && LA11_36<='n')||(LA11_36>='p' && LA11_36<='r')||(LA11_36>='t' && LA11_36<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA11_62 = input.LA(1);

                        s = -1;
                        if ( (LA11_62=='h') ) {s = 11;}

                        else if ( ((LA11_62>='\u0000' && LA11_62<='\u001F')||LA11_62=='!'||(LA11_62>='#' && LA11_62<='\'')||(LA11_62>='*' && LA11_62<='g')||(LA11_62>='i' && LA11_62<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA11_32 = input.LA(1);

                        s = -1;
                        if ( (LA11_32=='o') ) {s = 41;}

                        else if ( (LA11_32=='s') ) {s = 42;}

                        else if ( (LA11_32=='h') ) {s = 11;}

                        else if ( ((LA11_32>='\u0000' && LA11_32<='\u001F')||LA11_32=='!'||(LA11_32>='#' && LA11_32<='\'')||(LA11_32>='*' && LA11_32<='g')||(LA11_32>='i' && LA11_32<='n')||(LA11_32>='p' && LA11_32<='r')||(LA11_32>='t' && LA11_32<='\uFFFF')) ) {s = 7;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA11_5 = input.LA(1);

                        s = -1;
                        if ( (LA11_5=='e') ) {s = 19;}

                        else if ( (LA11_5=='h') ) {s = 11;}

                        else if ( ((LA11_5>='\u0000' && LA11_5<='\u001F')||LA11_5=='!'||(LA11_5>='#' && LA11_5<='\'')||(LA11_5>='*' && LA11_5<='d')||(LA11_5>='f' && LA11_5<='g')||(LA11_5>='i' && LA11_5<='\uFFFF')) ) {s = 7;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA11_55 = input.LA(1);

                        s = -1;
                        if ( (LA11_55=='s') ) {s = 59;}

                        else if ( (LA11_55=='h') ) {s = 11;}

                        else if ( ((LA11_55>='\u0000' && LA11_55<='\u001F')||LA11_55=='!'||(LA11_55>='#' && LA11_55<='\'')||(LA11_55>='*' && LA11_55<='g')||(LA11_55>='i' && LA11_55<='r')||(LA11_55>='t' && LA11_55<='\uFFFF')) ) {s = 7;}

                        else s = 18;

                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA11_51 = input.LA(1);

                        s = -1;
                        if ( (LA11_51=='d') ) {s = 56;}

                        else if ( (LA11_51=='h') ) {s = 11;}

                        else if ( ((LA11_51>='\u0000' && LA11_51<='\u001F')||LA11_51=='!'||(LA11_51>='#' && LA11_51<='\'')||(LA11_51>='*' && LA11_51<='c')||(LA11_51>='e' && LA11_51<='g')||(LA11_51>='i' && LA11_51<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA11_40 = input.LA(1);

                        s = -1;
                        if ( (LA11_40=='h') ) {s = 11;}

                        else if ( ((LA11_40>='\u0000' && LA11_40<='\u001F')||LA11_40=='!'||(LA11_40>='#' && LA11_40<='\'')||(LA11_40>='*' && LA11_40<='g')||(LA11_40>='i' && LA11_40<='\uFFFF')) ) {s = 7;}

                        else s = 18;

                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA11_2 = input.LA(1);

                        s = -1;
                        if ( (LA11_2=='o') ) {s = 14;}

                        else if ( (LA11_2=='r') ) {s = 15;}

                        else if ( (LA11_2=='h') ) {s = 11;}

                        else if ( ((LA11_2>='\u0000' && LA11_2<='\u001F')||LA11_2=='!'||(LA11_2>='#' && LA11_2<='\'')||(LA11_2>='*' && LA11_2<='g')||(LA11_2>='i' && LA11_2<='n')||(LA11_2>='p' && LA11_2<='q')||(LA11_2>='s' && LA11_2<='\uFFFF')) ) {s = 7;}

                        else s = 16;

                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA11_41 = input.LA(1);

                        s = -1;
                        if ( (LA11_41=='n') ) {s = 51;}

                        else if ( (LA11_41=='h') ) {s = 11;}

                        else if ( ((LA11_41>='\u0000' && LA11_41<='\u001F')||LA11_41=='!'||(LA11_41>='#' && LA11_41<='\'')||(LA11_41>='*' && LA11_41<='g')||(LA11_41>='i' && LA11_41<='m')||(LA11_41>='o' && LA11_41<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA11_47 = input.LA(1);

                        s = -1;
                        if ( (LA11_47=='h') ) {s = 11;}

                        else if ( ((LA11_47>='\u0000' && LA11_47<='\u001F')||LA11_47=='!'||(LA11_47>='#' && LA11_47<='\'')||(LA11_47>='*' && LA11_47<='g')||(LA11_47>='i' && LA11_47<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA11_25 = input.LA(1);

                        s = -1;
                        if ( (LA11_25=='s') ) {s = 35;}

                        else if ( (LA11_25=='h') ) {s = 11;}

                        else if ( ((LA11_25>='0' && LA11_25<='9')) ) {s = 26;}

                        else if ( ((LA11_25>='\u0000' && LA11_25<='\u001F')||LA11_25=='!'||(LA11_25>='#' && LA11_25<='\'')||(LA11_25>='*' && LA11_25<='/')||(LA11_25>=':' && LA11_25<='g')||(LA11_25>='i' && LA11_25<='r')||(LA11_25>='t' && LA11_25<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA11_58 = input.LA(1);

                        s = -1;
                        if ( (LA11_58=='s') ) {s = 62;}

                        else if ( (LA11_58=='h') ) {s = 11;}

                        else if ( ((LA11_58>='\u0000' && LA11_58<='\u001F')||LA11_58=='!'||(LA11_58>='#' && LA11_58<='\'')||(LA11_58>='*' && LA11_58<='g')||(LA11_58>='i' && LA11_58<='r')||(LA11_58>='t' && LA11_58<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA11_12 = input.LA(1);

                        s = -1;
                        if ( (LA11_12=='e') ) {s = 27;}

                        else if ( (LA11_12=='h') ) {s = 11;}

                        else if ( ((LA11_12>='\u0000' && LA11_12<='\u001F')||LA11_12=='!'||(LA11_12>='#' && LA11_12<='\'')||(LA11_12>='*' && LA11_12<='d')||(LA11_12>='f' && LA11_12<='g')||(LA11_12>='i' && LA11_12<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA11_54 = input.LA(1);

                        s = -1;
                        if ( (LA11_54=='d') ) {s = 58;}

                        else if ( (LA11_54=='h') ) {s = 11;}

                        else if ( ((LA11_54>='\u0000' && LA11_54<='\u001F')||LA11_54=='!'||(LA11_54>='#' && LA11_54<='\'')||(LA11_54>='*' && LA11_54<='c')||(LA11_54>='e' && LA11_54<='g')||(LA11_54>='i' && LA11_54<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA11_15 = input.LA(1);

                        s = -1;
                        if ( (LA11_15=='s') ) {s = 30;}

                        else if ( (LA11_15=='h') ) {s = 11;}

                        else if ( ((LA11_15>='\u0000' && LA11_15<='\u001F')||LA11_15=='!'||(LA11_15>='#' && LA11_15<='\'')||(LA11_15>='*' && LA11_15<='g')||(LA11_15>='i' && LA11_15<='r')||(LA11_15>='t' && LA11_15<='\uFFFF')) ) {s = 7;}

                        else s = 16;

                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA11_29 = input.LA(1);

                        s = -1;
                        if ( (LA11_29=='r') ) {s = 38;}

                        else if ( (LA11_29=='h') ) {s = 11;}

                        else if ( ((LA11_29>='\u0000' && LA11_29<='\u001F')||LA11_29=='!'||(LA11_29>='#' && LA11_29<='\'')||(LA11_29>='*' && LA11_29<='g')||(LA11_29>='i' && LA11_29<='q')||(LA11_29>='s' && LA11_29<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA11_46 = input.LA(1);

                        s = -1;
                        if ( (LA11_46=='n') ) {s = 54;}

                        else if ( (LA11_46=='h') ) {s = 11;}

                        else if ( ((LA11_46>='\u0000' && LA11_46<='\u001F')||LA11_46=='!'||(LA11_46>='#' && LA11_46<='\'')||(LA11_46>='*' && LA11_46<='g')||(LA11_46>='i' && LA11_46<='m')||(LA11_46>='o' && LA11_46<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA11_14 = input.LA(1);

                        s = -1;
                        if ( (LA11_14=='u') ) {s = 29;}

                        else if ( (LA11_14=='h') ) {s = 11;}

                        else if ( ((LA11_14>='\u0000' && LA11_14<='\u001F')||LA11_14=='!'||(LA11_14>='#' && LA11_14<='\'')||(LA11_14>='*' && LA11_14<='g')||(LA11_14>='i' && LA11_14<='t')||(LA11_14>='v' && LA11_14<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA11_34 = input.LA(1);

                        s = -1;
                        if ( (LA11_34=='r') ) {s = 45;}

                        else if ( (LA11_34=='h') ) {s = 11;}

                        else if ( ((LA11_34>='\u0000' && LA11_34<='\u001F')||LA11_34=='!'||(LA11_34>='#' && LA11_34<='\'')||(LA11_34>='*' && LA11_34<='g')||(LA11_34>='i' && LA11_34<='q')||(LA11_34>='s' && LA11_34<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA11_10 = input.LA(1);

                        s = -1;
                        if ( (LA11_10=='i') ) {s = 22;}

                        else if ( (LA11_10=='h') ) {s = 11;}

                        else if ( ((LA11_10>='0' && LA11_10<='9')) ) {s = 23;}

                        else if ( ((LA11_10>='\u0000' && LA11_10<='\u001F')||LA11_10=='!'||(LA11_10>='#' && LA11_10<='\'')||(LA11_10>='*' && LA11_10<='/')||(LA11_10>=':' && LA11_10<='g')||(LA11_10>='j' && LA11_10<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA11_24 = input.LA(1);

                        s = -1;
                        if ( (LA11_24=='u') ) {s = 34;}

                        else if ( (LA11_24=='h') ) {s = 11;}

                        else if ( ((LA11_24>='\u0000' && LA11_24<='\u001F')||LA11_24=='!'||(LA11_24>='#' && LA11_24<='\'')||(LA11_24>='*' && LA11_24<='g')||(LA11_24>='i' && LA11_24<='t')||(LA11_24>='v' && LA11_24<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA11_33 = input.LA(1);

                        s = -1;
                        if ( (LA11_33=='u') ) {s = 43;}

                        else if ( (LA11_33=='s') ) {s = 44;}

                        else if ( (LA11_33=='h') ) {s = 11;}

                        else if ( ((LA11_33>='0' && LA11_33<='9')) ) {s = 23;}

                        else if ( ((LA11_33>='\u0000' && LA11_33<='\u001F')||LA11_33=='!'||(LA11_33>='#' && LA11_33<='\'')||(LA11_33>='*' && LA11_33<='/')||(LA11_33>=':' && LA11_33<='g')||(LA11_33>='i' && LA11_33<='r')||LA11_33=='t'||(LA11_33>='v' && LA11_33<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA11_23 = input.LA(1);

                        s = -1;
                        if ( (LA11_23=='h') ) {s = 11;}

                        else if ( (LA11_23=='s') ) {s = 12;}

                        else if ( ((LA11_23>='0' && LA11_23<='9')) ) {s = 23;}

                        else if ( ((LA11_23>='\u0000' && LA11_23<='\u001F')||LA11_23=='!'||(LA11_23>='#' && LA11_23<='\'')||(LA11_23>='*' && LA11_23<='/')||(LA11_23>=':' && LA11_23<='g')||(LA11_23>='i' && LA11_23<='r')||(LA11_23>='t' && LA11_23<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA11_38 = input.LA(1);

                        s = -1;
                        if ( (LA11_38=='s') ) {s = 49;}

                        else if ( (LA11_38=='h') ) {s = 11;}

                        else if ( ((LA11_38>='\u0000' && LA11_38<='\u001F')||LA11_38=='!'||(LA11_38>='#' && LA11_38<='\'')||(LA11_38>='*' && LA11_38<='g')||(LA11_38>='i' && LA11_38<='r')||(LA11_38>='t' && LA11_38<='\uFFFF')) ) {s = 7;}

                        else s = 16;

                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA11_59 = input.LA(1);

                        s = -1;
                        if ( (LA11_59=='h') ) {s = 11;}

                        else if ( ((LA11_59>='\u0000' && LA11_59<='\u001F')||LA11_59=='!'||(LA11_59>='#' && LA11_59<='\'')||(LA11_59>='*' && LA11_59<='g')||(LA11_59>='i' && LA11_59<='\uFFFF')) ) {s = 7;}

                        else s = 18;

                        if ( s>=0 ) return s;
                        break;
                    case 26 : 
                        int LA11_31 = input.LA(1);

                        s = -1;
                        if ( (LA11_31=='u') ) {s = 39;}

                        else if ( (LA11_31=='s') ) {s = 40;}

                        else if ( (LA11_31=='h') ) {s = 11;}

                        else if ( ((LA11_31>='\u0000' && LA11_31<='\u001F')||LA11_31=='!'||(LA11_31>='#' && LA11_31<='\'')||(LA11_31>='*' && LA11_31<='g')||(LA11_31>='i' && LA11_31<='r')||LA11_31=='t'||(LA11_31>='v' && LA11_31<='\uFFFF')) ) {s = 7;}

                        else s = 18;

                        if ( s>=0 ) return s;
                        break;
                    case 27 : 
                        int LA11_7 = input.LA(1);

                        s = -1;
                        if ( (LA11_7=='h') ) {s = 11;}

                        else if ( ((LA11_7>='\u0000' && LA11_7<='\u001F')||LA11_7=='!'||(LA11_7>='#' && LA11_7<='\'')||(LA11_7>='*' && LA11_7<='g')||(LA11_7>='i' && LA11_7<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 28 : 
                        int LA11_6 = input.LA(1);

                        s = -1;
                        if ( (LA11_6=='h') ) {s = 11;}

                        else if ( ((LA11_6>='\u0000' && LA11_6<='\u001F')||LA11_6=='!'||(LA11_6>='#' && LA11_6<='\'')||(LA11_6>='*' && LA11_6<='g')||(LA11_6>='i' && LA11_6<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 29 : 
                        int LA11_44 = input.LA(1);

                        s = -1;
                        if ( (LA11_44=='h') ) {s = 11;}

                        else if ( ((LA11_44>='0' && LA11_44<='9')) ) {s = 23;}

                        else if ( ((LA11_44>='\u0000' && LA11_44<='\u001F')||LA11_44=='!'||(LA11_44>='#' && LA11_44<='\'')||(LA11_44>='*' && LA11_44<='/')||(LA11_44>=':' && LA11_44<='g')||(LA11_44>='i' && LA11_44<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 30 : 
                        int LA11_45 = input.LA(1);

                        s = -1;
                        if ( (LA11_45=='s') ) {s = 53;}

                        else if ( ((LA11_45>='0' && LA11_45<='9')) ) {s = 26;}

                        else if ( (LA11_45=='h') ) {s = 11;}

                        else if ( ((LA11_45>='\u0000' && LA11_45<='\u001F')||LA11_45=='!'||(LA11_45>='#' && LA11_45<='\'')||(LA11_45>='*' && LA11_45<='/')||(LA11_45>=':' && LA11_45<='g')||(LA11_45>='i' && LA11_45<='r')||(LA11_45>='t' && LA11_45<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 31 : 
                        int LA11_4 = input.LA(1);

                        s = -1;
                        if ( (LA11_4=='i') ) {s = 17;}

                        else if ( (LA11_4=='h') ) {s = 11;}

                        else if ( ((LA11_4>='\u0000' && LA11_4<='\u001F')||LA11_4=='!'||(LA11_4>='#' && LA11_4<='\'')||(LA11_4>='*' && LA11_4<='g')||(LA11_4>='j' && LA11_4<='\uFFFF')) ) {s = 7;}

                        else s = 18;

                        if ( s>=0 ) return s;
                        break;
                    case 32 : 
                        int LA11_37 = input.LA(1);

                        s = -1;
                        if ( (LA11_37=='h') ) {s = 11;}

                        else if ( ((LA11_37>='0' && LA11_37<='9')) ) {s = 48;}

                        else if ( ((LA11_37>='\u0000' && LA11_37<='\u001F')||LA11_37=='!'||(LA11_37>='#' && LA11_37<='\'')||(LA11_37>='*' && LA11_37<='/')||(LA11_37>=':' && LA11_37<='g')||(LA11_37>='i' && LA11_37<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 33 : 
                        int LA11_13 = input.LA(1);

                        s = -1;
                        if ( (LA11_13=='h') ) {s = 11;}

                        else if ( ((LA11_13>='0' && LA11_13<='9')) ) {s = 28;}

                        else if ( ((LA11_13>='\u0000' && LA11_13<='\u001F')||LA11_13=='!'||(LA11_13>='#' && LA11_13<='\'')||(LA11_13>='*' && LA11_13<='/')||(LA11_13>=':' && LA11_13<='g')||(LA11_13>='i' && LA11_13<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 34 : 
                        int LA11_61 = input.LA(1);

                        s = -1;
                        if ( (LA11_61=='h') ) {s = 11;}

                        else if ( ((LA11_61>='0' && LA11_61<='9')) ) {s = 23;}

                        else if ( ((LA11_61>='\u0000' && LA11_61<='\u001F')||LA11_61=='!'||(LA11_61>='#' && LA11_61<='\'')||(LA11_61>='*' && LA11_61<='/')||(LA11_61>=':' && LA11_61<='g')||(LA11_61>='i' && LA11_61<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 35 : 
                        int LA11_11 = input.LA(1);

                        s = -1;
                        if ( (LA11_11=='o') ) {s = 24;}

                        else if ( (LA11_11=='r') ) {s = 25;}

                        else if ( (LA11_11=='h') ) {s = 11;}

                        else if ( ((LA11_11>='0' && LA11_11<='9')) ) {s = 26;}

                        else if ( ((LA11_11>='\u0000' && LA11_11<='\u001F')||LA11_11=='!'||(LA11_11>='#' && LA11_11<='\'')||(LA11_11>='*' && LA11_11<='/')||(LA11_11>=':' && LA11_11<='g')||(LA11_11>='i' && LA11_11<='n')||(LA11_11>='p' && LA11_11<='q')||(LA11_11>='s' && LA11_11<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 36 : 
                        int LA11_26 = input.LA(1);

                        s = -1;
                        if ( (LA11_26=='m') ) {s = 10;}

                        else if ( ((LA11_26>='0' && LA11_26<='9')) ) {s = 26;}

                        else if ( (LA11_26=='h') ) {s = 11;}

                        else if ( (LA11_26=='s') ) {s = 12;}

                        else if ( ((LA11_26>='\u0000' && LA11_26<='\u001F')||LA11_26=='!'||(LA11_26>='#' && LA11_26<='\'')||(LA11_26>='*' && LA11_26<='/')||(LA11_26>=':' && LA11_26<='g')||(LA11_26>='i' && LA11_26<='l')||(LA11_26>='n' && LA11_26<='r')||(LA11_26>='t' && LA11_26<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 37 : 
                        int LA11_52 = input.LA(1);

                        s = -1;
                        if ( (LA11_52=='e') ) {s = 57;}

                        else if ( (LA11_52=='h') ) {s = 11;}

                        else if ( ((LA11_52>='\u0000' && LA11_52<='\u001F')||LA11_52=='!'||(LA11_52>='#' && LA11_52<='\'')||(LA11_52>='*' && LA11_52<='d')||(LA11_52>='f' && LA11_52<='g')||(LA11_52>='i' && LA11_52<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 38 : 
                        int LA11_43 = input.LA(1);

                        s = -1;
                        if ( (LA11_43=='t') ) {s = 52;}

                        else if ( (LA11_43=='h') ) {s = 11;}

                        else if ( ((LA11_43>='\u0000' && LA11_43<='\u001F')||LA11_43=='!'||(LA11_43>='#' && LA11_43<='\'')||(LA11_43>='*' && LA11_43<='g')||(LA11_43>='i' && LA11_43<='s')||(LA11_43>='u' && LA11_43<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 39 : 
                        int LA11_50 = input.LA(1);

                        s = -1;
                        if ( (LA11_50=='e') ) {s = 55;}

                        else if ( (LA11_50=='h') ) {s = 11;}

                        else if ( ((LA11_50>='\u0000' && LA11_50<='\u001F')||LA11_50=='!'||(LA11_50>='#' && LA11_50<='\'')||(LA11_50>='*' && LA11_50<='d')||(LA11_50>='f' && LA11_50<='g')||(LA11_50>='i' && LA11_50<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 40 : 
                        int LA11_28 = input.LA(1);

                        s = -1;
                        if ( (LA11_28=='h') ) {s = 11;}

                        else if ( (LA11_28==':') ) {s = 37;}

                        else if ( ((LA11_28>='0' && LA11_28<='9')) ) {s = 28;}

                        else if ( ((LA11_28>='\u0000' && LA11_28<='\u001F')||LA11_28=='!'||(LA11_28>='#' && LA11_28<='\'')||(LA11_28>='*' && LA11_28<='/')||(LA11_28>=';' && LA11_28<='g')||(LA11_28>='i' && LA11_28<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 41 : 
                        int LA11_39 = input.LA(1);

                        s = -1;
                        if ( (LA11_39=='t') ) {s = 50;}

                        else if ( (LA11_39=='h') ) {s = 11;}

                        else if ( ((LA11_39>='\u0000' && LA11_39<='\u001F')||LA11_39=='!'||(LA11_39>='#' && LA11_39<='\'')||(LA11_39>='*' && LA11_39<='g')||(LA11_39>='i' && LA11_39<='s')||(LA11_39>='u' && LA11_39<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 42 : 
                        int LA11_57 = input.LA(1);

                        s = -1;
                        if ( (LA11_57=='s') ) {s = 61;}

                        else if ( ((LA11_57>='0' && LA11_57<='9')) ) {s = 23;}

                        else if ( (LA11_57=='h') ) {s = 11;}

                        else if ( ((LA11_57>='\u0000' && LA11_57<='\u001F')||LA11_57=='!'||(LA11_57>='#' && LA11_57<='\'')||(LA11_57>='*' && LA11_57<='/')||(LA11_57>=':' && LA11_57<='g')||(LA11_57>='i' && LA11_57<='r')||(LA11_57>='t' && LA11_57<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 43 : 
                        int LA11_1 = input.LA(1);

                        s = -1;
                        if ( ((LA11_1>='0' && LA11_1<='9')) ) {s = 1;}

                        else if ( (LA11_1=='m') ) {s = 10;}

                        else if ( (LA11_1=='h') ) {s = 11;}

                        else if ( (LA11_1=='s') ) {s = 12;}

                        else if ( (LA11_1==':') ) {s = 13;}

                        else if ( ((LA11_1>='\u0000' && LA11_1<='\u001F')||LA11_1=='!'||(LA11_1>='#' && LA11_1<='\'')||(LA11_1>='*' && LA11_1<='/')||(LA11_1>=';' && LA11_1<='g')||(LA11_1>='i' && LA11_1<='l')||(LA11_1>='n' && LA11_1<='r')||(LA11_1>='t' && LA11_1<='\uFFFF')) ) {s = 7;}

                        else s = 9;

                        if ( s>=0 ) return s;
                        break;
                    case 44 : 
                        int LA11_35 = input.LA(1);

                        s = -1;
                        if ( (LA11_35=='h') ) {s = 11;}

                        else if ( ((LA11_35>='0' && LA11_35<='9')) ) {s = 26;}

                        else if ( ((LA11_35>='\u0000' && LA11_35<='\u001F')||LA11_35=='!'||(LA11_35>='#' && LA11_35<='\'')||(LA11_35>='*' && LA11_35<='/')||(LA11_35>=':' && LA11_35<='g')||(LA11_35>='i' && LA11_35<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 45 : 
                        int LA11_49 = input.LA(1);

                        s = -1;
                        if ( (LA11_49=='h') ) {s = 11;}

                        else if ( ((LA11_49>='\u0000' && LA11_49<='\u001F')||LA11_49=='!'||(LA11_49>='#' && LA11_49<='\'')||(LA11_49>='*' && LA11_49<='g')||(LA11_49>='i' && LA11_49<='\uFFFF')) ) {s = 7;}

                        else s = 16;

                        if ( s>=0 ) return s;
                        break;
                    case 46 : 
                        int LA11_48 = input.LA(1);

                        s = -1;
                        if ( ((LA11_48>='0' && LA11_48<='9')) ) {s = 48;}

                        else if ( (LA11_48=='h') ) {s = 11;}

                        else if ( ((LA11_48>='\u0000' && LA11_48<='\u001F')||LA11_48=='!'||(LA11_48>='#' && LA11_48<='\'')||(LA11_48>='*' && LA11_48<='/')||(LA11_48>=':' && LA11_48<='g')||(LA11_48>='i' && LA11_48<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 47 : 
                        int LA11_17 = input.LA(1);

                        s = -1;
                        if ( (LA11_17=='n') ) {s = 31;}

                        else if ( (LA11_17=='h') ) {s = 11;}

                        else if ( ((LA11_17>='\u0000' && LA11_17<='\u001F')||LA11_17=='!'||(LA11_17>='#' && LA11_17<='\'')||(LA11_17>='*' && LA11_17<='g')||(LA11_17>='i' && LA11_17<='m')||(LA11_17>='o' && LA11_17<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 48 : 
                        int LA11_27 = input.LA(1);

                        s = -1;
                        if ( (LA11_27=='c') ) {s = 36;}

                        else if ( (LA11_27=='h') ) {s = 11;}

                        else if ( ((LA11_27>='\u0000' && LA11_27<='\u001F')||LA11_27=='!'||(LA11_27>='#' && LA11_27<='\'')||(LA11_27>='*' && LA11_27<='b')||(LA11_27>='d' && LA11_27<='g')||(LA11_27>='i' && LA11_27<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 49 : 
                        int LA11_53 = input.LA(1);

                        s = -1;
                        if ( (LA11_53=='h') ) {s = 11;}

                        else if ( ((LA11_53>='0' && LA11_53<='9')) ) {s = 26;}

                        else if ( ((LA11_53>='\u0000' && LA11_53<='\u001F')||LA11_53=='!'||(LA11_53>='#' && LA11_53<='\'')||(LA11_53>='*' && LA11_53<='/')||(LA11_53>=':' && LA11_53<='g')||(LA11_53>='i' && LA11_53<='\uFFFF')) ) {s = 7;}

                        else s = 3;

                        if ( s>=0 ) return s;
                        break;
                    case 50 : 
                        int LA11_42 = input.LA(1);

                        s = -1;
                        if ( (LA11_42=='h') ) {s = 11;}

                        else if ( ((LA11_42>='\u0000' && LA11_42<='\u001F')||LA11_42=='!'||(LA11_42>='#' && LA11_42<='\'')||(LA11_42>='*' && LA11_42<='g')||(LA11_42>='i' && LA11_42<='\uFFFF')) ) {s = 7;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 51 : 
                        int LA11_60 = input.LA(1);

                        s = -1;
                        if ( (LA11_60=='h') ) {s = 11;}

                        else if ( ((LA11_60>='\u0000' && LA11_60<='\u001F')||LA11_60=='!'||(LA11_60>='#' && LA11_60<='\'')||(LA11_60>='*' && LA11_60<='g')||(LA11_60>='i' && LA11_60<='\uFFFF')) ) {s = 7;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 52 : 
                        int LA11_22 = input.LA(1);

                        s = -1;
                        if ( (LA11_22=='n') ) {s = 33;}

                        else if ( (LA11_22=='h') ) {s = 11;}

                        else if ( ((LA11_22>='\u0000' && LA11_22<='\u001F')||LA11_22=='!'||(LA11_22>='#' && LA11_22<='\'')||(LA11_22>='*' && LA11_22<='g')||(LA11_22>='i' && LA11_22<='m')||(LA11_22>='o' && LA11_22<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 53 : 
                        int LA11_56 = input.LA(1);

                        s = -1;
                        if ( (LA11_56=='s') ) {s = 60;}

                        else if ( (LA11_56=='h') ) {s = 11;}

                        else if ( ((LA11_56>='\u0000' && LA11_56<='\u001F')||LA11_56=='!'||(LA11_56>='#' && LA11_56<='\'')||(LA11_56>='*' && LA11_56<='g')||(LA11_56>='i' && LA11_56<='r')||(LA11_56>='t' && LA11_56<='\uFFFF')) ) {s = 7;}

                        else s = 20;

                        if ( s>=0 ) return s;
                        break;
                    case 54 : 
                        int LA11_19 = input.LA(1);

                        s = -1;
                        if ( (LA11_19=='c') ) {s = 32;}

                        else if ( (LA11_19=='h') ) {s = 11;}

                        else if ( ((LA11_19>='\u0000' && LA11_19<='\u001F')||LA11_19=='!'||(LA11_19>='#' && LA11_19<='\'')||(LA11_19>='*' && LA11_19<='b')||(LA11_19>='d' && LA11_19<='g')||(LA11_19>='i' && LA11_19<='\uFFFF')) ) {s = 7;}

                        else s = 21;

                        if ( s>=0 ) return s;
                        break;
                    case 55 : 
                        int LA11_30 = input.LA(1);

                        s = -1;
                        if ( (LA11_30=='h') ) {s = 11;}

                        else if ( ((LA11_30>='\u0000' && LA11_30<='\u001F')||LA11_30=='!'||(LA11_30>='#' && LA11_30<='\'')||(LA11_30>='*' && LA11_30<='g')||(LA11_30>='i' && LA11_30<='\uFFFF')) ) {s = 7;}

                        else s = 16;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 11, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}