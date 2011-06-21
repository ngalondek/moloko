// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g 2011-06-21 06:31:18

   package dev.drsoran.moloko.grammar.datetime;

   import dev.drsoran.moloko.grammar.LexerException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class DateLexer extends Lexer {
    public static final int STs=18;
    public static final int TODAY=14;
    public static final int A=4;
    public static final int MINUS_A=30;
    public static final int THE=10;
    public static final int ON=6;
    public static final int NOW=11;
    public static final int INT=32;
    public static final int MINUS=29;
    public static final int AND=8;
    public static final int OF=5;
    public static final int EOF=-1;
    public static final int YEARS=19;
    public static final int NUM_STR=25;
    public static final int MONTH=23;
    public static final int COLON=28;
    public static final int DAYS=22;
    public static final int WS=33;
    public static final int WEEKDAY=24;
    public static final int WEEKS=21;
    public static final int IN=7;
    public static final int TONIGHT=12;
    public static final int COMMA=31;
    public static final int MONTHS=20;
    public static final int NEXT=17;
    public static final int DATE_SEP=26;
    public static final int NEVER=13;
    public static final int DOT=27;
    public static final int END=9;
    public static final int YESTERDAY=16;
    public static final int TOMORROW=15;

       public DateLexer()
       {
          super( null );
       }


    // delegates
    // delegators

    public DateLexer() {;} 
    public DateLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public DateLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g"; }

    public Token nextToken() {
        while (true) {
            if ( input.LA(1)==CharStream.EOF ) {
                return Token.EOF_TOKEN;
            }
            state.token = null;
    	state.channel = Token.DEFAULT_CHANNEL;
            state.tokenStartCharIndex = input.index();
            state.tokenStartCharPositionInLine = input.getCharPositionInLine();
            state.tokenStartLine = input.getLine();
    	state.text = null;
            try {
                int m = input.mark();
                state.backtracking=1; 
                state.failed=false;
                mTokens();
                state.backtracking=0;

                if ( state.failed ) {
                    input.rewind(m);
                    input.consume(); 
                }
                else {
                    emit();
                    return state.token;
                }
            }
            catch (RecognitionException re) {
                // shouldn't happen in backtracking mode, but...
                reportError(re);
                recover(re);
            }
        }
    }

    public void memoize(IntStream input,
    		int ruleIndex,
    		int ruleStartIndex)
    {
    if ( state.backtracking>1 ) super.memoize(input, ruleIndex, ruleStartIndex);
    }

    public boolean alreadyParsedRule(IntStream input, int ruleIndex) {
    if ( state.backtracking>1 ) return super.alreadyParsedRule(input, ruleIndex);
    return false;
    }// $ANTLR start "A"
    public final void mA() throws RecognitionException {
        try {
            int _type = A;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:32:11: ( 'a' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:32:13: 'a'
            {
            match('a'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "A"

    // $ANTLR start "OF"
    public final void mOF() throws RecognitionException {
        try {
            int _type = OF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:34:11: ( 'of' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:34:13: 'of'
            {
            match("of"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OF"

    // $ANTLR start "ON"
    public final void mON() throws RecognitionException {
        try {
            int _type = ON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:36:11: ( 'on' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:36:13: 'on'
            {
            match("on"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ON"

    // $ANTLR start "IN"
    public final void mIN() throws RecognitionException {
        try {
            int _type = IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:38:11: ( 'in' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:38:13: 'in'
            {
            match("in"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IN"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:40:11: ( 'and' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:40:13: 'and'
            {
            match("and"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "END"
    public final void mEND() throws RecognitionException {
        try {
            int _type = END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:42:11: ( 'end' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:42:13: 'end'
            {
            match("end"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END"

    // $ANTLR start "THE"
    public final void mTHE() throws RecognitionException {
        try {
            int _type = THE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:44:11: ( 'the' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:44:13: 'the'
            {
            match("the"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "THE"

    // $ANTLR start "NOW"
    public final void mNOW() throws RecognitionException {
        try {
            int _type = NOW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:46:11: ( 'now' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:46:13: 'now'
            {
            match("now"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOW"

    // $ANTLR start "TONIGHT"
    public final void mTONIGHT() throws RecognitionException {
        try {
            int _type = TONIGHT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:48:11: ( 'ton' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:48:13: 'ton'
            {
            match("ton"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TONIGHT"

    // $ANTLR start "NEVER"
    public final void mNEVER() throws RecognitionException {
        try {
            int _type = NEVER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:50:11: ( 'never' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:50:13: 'never'
            {
            match("never"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEVER"

    // $ANTLR start "TODAY"
    public final void mTODAY() throws RecognitionException {
        try {
            int _type = TODAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:52:11: ( 'today' | 'tod' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='t') ) {
                int LA1_1 = input.LA(2);

                if ( (LA1_1=='o') ) {
                    int LA1_2 = input.LA(3);

                    if ( (LA1_2=='d') ) {
                        int LA1_3 = input.LA(4);

                        if ( (LA1_3=='a') ) {
                            alt1=1;
                        }
                        else {
                            alt1=2;}
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 1, 2, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:52:13: 'today'
                    {
                    match("today"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:52:23: 'tod'
                    {
                    match("tod"); if (state.failed) return ;


                    }
                    break;

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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:54:11: ( 'tomorrow' | 'tom' | 'tmr' )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='t') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='o') ) {
                    int LA2_2 = input.LA(3);

                    if ( (LA2_2=='m') ) {
                        int LA2_4 = input.LA(4);

                        if ( (LA2_4=='o') ) {
                            alt2=1;
                        }
                        else {
                            alt2=2;}
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA2_1=='m') ) {
                    alt2=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:54:13: 'tomorrow'
                    {
                    match("tomorrow"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:54:26: 'tom'
                    {
                    match("tom"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:54:34: 'tmr'
                    {
                    match("tmr"); if (state.failed) return ;


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

    // $ANTLR start "YESTERDAY"
    public final void mYESTERDAY() throws RecognitionException {
        try {
            int _type = YESTERDAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:56:11: ( 'yesterday' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:56:13: 'yesterday'
            {
            match("yesterday"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "YESTERDAY"

    // $ANTLR start "NEXT"
    public final void mNEXT() throws RecognitionException {
        try {
            int _type = NEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:58:11: ( 'next' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:58:13: 'next'
            {
            match("next"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEXT"

    // $ANTLR start "STs"
    public final void mSTs() throws RecognitionException {
        try {
            int _type = STs;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:60:11: ( 'st' | 'th' | 'rd' | 'nd' )
            int alt3=4;
            switch ( input.LA(1) ) {
            case 's':
                {
                alt3=1;
                }
                break;
            case 't':
                {
                alt3=2;
                }
                break;
            case 'r':
                {
                alt3=3;
                }
                break;
            case 'n':
                {
                alt3=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:60:13: 'st'
                    {
                    match("st"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:60:20: 'th'
                    {
                    match("th"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:60:27: 'rd'
                    {
                    match("rd"); if (state.failed) return ;


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:60:34: 'nd'
                    {
                    match("nd"); if (state.failed) return ;


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STs"

    // $ANTLR start "YEARS"
    public final void mYEARS() throws RecognitionException {
        try {
            int _type = YEARS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:62:11: ( 'years' | 'year' | 'yrs' | 'yr' )
            int alt4=4;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:62:13: 'years'
                    {
                    match("years"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:62:23: 'year'
                    {
                    match("year"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:62:32: 'yrs'
                    {
                    match("yrs"); if (state.failed) return ;


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:62:40: 'yr'
                    {
                    match("yr"); if (state.failed) return ;


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "YEARS"

    // $ANTLR start "MONTHS"
    public final void mMONTHS() throws RecognitionException {
        try {
            int _type = MONTHS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:64:11: ( 'months' | 'month' | 'mons' | 'mon' )
            int alt5=4;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:64:13: 'months'
                    {
                    match("months"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:64:24: 'month'
                    {
                    match("month"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:64:34: 'mons'
                    {
                    match("mons"); if (state.failed) return ;


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:64:43: 'mon'
                    {
                    match("mon"); if (state.failed) return ;


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MONTHS"

    // $ANTLR start "WEEKS"
    public final void mWEEKS() throws RecognitionException {
        try {
            int _type = WEEKS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:66:11: ( 'weeks' | 'week' | 'wks' | 'wk' )
            int alt6=4;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:66:13: 'weeks'
                    {
                    match("weeks"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:66:23: 'week'
                    {
                    match("week"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:66:32: 'wks'
                    {
                    match("wks"); if (state.failed) return ;


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:66:40: 'wk'
                    {
                    match("wk"); if (state.failed) return ;


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WEEKS"

    // $ANTLR start "DAYS"
    public final void mDAYS() throws RecognitionException {
        try {
            int _type = DAYS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:68:11: ( 'days' | 'day' | 'd' )
            int alt7=3;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='d') ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1=='a') ) {
                    int LA7_2 = input.LA(3);

                    if ( (LA7_2=='y') ) {
                        int LA7_4 = input.LA(4);

                        if ( (LA7_4=='s') ) {
                            alt7=1;
                        }
                        else {
                            alt7=2;}
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 2, input);

                        throw nvae;
                    }
                }
                else {
                    alt7=3;}
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:68:13: 'days'
                    {
                    match("days"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:68:22: 'day'
                    {
                    match("day"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:68:30: 'd'
                    {
                    match('d'); if (state.failed) return ;

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DAYS"

    // $ANTLR start "MONTH"
    public final void mMONTH() throws RecognitionException {
        try {
            int _type = MONTH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:70:11: ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' | 'december' | 'dec' )
            int alt8=24;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:70:13: 'january'
                    {
                    match("january"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:70:27: 'jan'
                    {
                    match("jan"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:70:36: 'february'
                    {
                    match("february"); if (state.failed) return ;


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:70:49: 'feb'
                    {
                    match("feb"); if (state.failed) return ;


                    }
                    break;
                case 5 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:70:61: 'march'
                    {
                    match("march"); if (state.failed) return ;


                    }
                    break;
                case 6 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:70:71: 'mar'
                    {
                    match("mar"); if (state.failed) return ;


                    }
                    break;
                case 7 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:70:84: 'april'
                    {
                    match("april"); if (state.failed) return ;


                    }
                    break;
                case 8 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:70:94: 'apr'
                    {
                    match("apr"); if (state.failed) return ;


                    }
                    break;
                case 9 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:71:13: 'may'
                    {
                    match("may"); if (state.failed) return ;


                    }
                    break;
                case 10 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:71:27: 'june'
                    {
                    match("june"); if (state.failed) return ;


                    }
                    break;
                case 11 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:71:36: 'jun'
                    {
                    match("jun"); if (state.failed) return ;


                    }
                    break;
                case 12 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:71:49: 'july'
                    {
                    match("july"); if (state.failed) return ;


                    }
                    break;
                case 13 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:71:61: 'jul'
                    {
                    match("jul"); if (state.failed) return ;


                    }
                    break;
                case 14 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:71:71: 'august'
                    {
                    match("august"); if (state.failed) return ;


                    }
                    break;
                case 15 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:71:84: 'aug'
                    {
                    match("aug"); if (state.failed) return ;


                    }
                    break;
                case 16 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:72:13: 'september'
                    {
                    match("september"); if (state.failed) return ;


                    }
                    break;
                case 17 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:72:27: 'sept'
                    {
                    match("sept"); if (state.failed) return ;


                    }
                    break;
                case 18 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:72:36: 'sep'
                    {
                    match("sep"); if (state.failed) return ;


                    }
                    break;
                case 19 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:72:49: 'october'
                    {
                    match("october"); if (state.failed) return ;


                    }
                    break;
                case 20 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:72:61: 'oct'
                    {
                    match("oct"); if (state.failed) return ;


                    }
                    break;
                case 21 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:72:71: 'november'
                    {
                    match("november"); if (state.failed) return ;


                    }
                    break;
                case 22 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:72:84: 'nov'
                    {
                    match("nov"); if (state.failed) return ;


                    }
                    break;
                case 23 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:73:13: 'december'
                    {
                    match("december"); if (state.failed) return ;


                    }
                    break;
                case 24 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:73:27: 'dec'
                    {
                    match("dec"); if (state.failed) return ;


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MONTH"

    // $ANTLR start "WEEKDAY"
    public final void mWEEKDAY() throws RecognitionException {
        try {
            int _type = WEEKDAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:75:11: ( 'monday' | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' | 'thursday' | 'thu' | 'friday' | 'fri' | 'saturday' | 'sat' | 'sunday' | 'sun' )
            int alt9=14;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:75:13: 'monday'
                    {
                    match("monday"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:75:26: 'mon'
                    {
                    match("mon"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:75:34: 'tuesday'
                    {
                    match("tuesday"); if (state.failed) return ;


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:75:46: 'tue'
                    {
                    match("tue"); if (state.failed) return ;


                    }
                    break;
                case 5 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:75:54: 'wednesday'
                    {
                    match("wednesday"); if (state.failed) return ;


                    }
                    break;
                case 6 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:75:68: 'wed'
                    {
                    match("wed"); if (state.failed) return ;


                    }
                    break;
                case 7 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:76:13: 'thursday'
                    {
                    match("thursday"); if (state.failed) return ;


                    }
                    break;
                case 8 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:76:26: 'thu'
                    {
                    match("thu"); if (state.failed) return ;


                    }
                    break;
                case 9 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:76:34: 'friday'
                    {
                    match("friday"); if (state.failed) return ;


                    }
                    break;
                case 10 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:76:46: 'fri'
                    {
                    match("fri"); if (state.failed) return ;


                    }
                    break;
                case 11 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:76:54: 'saturday'
                    {
                    match("saturday"); if (state.failed) return ;


                    }
                    break;
                case 12 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:76:68: 'sat'
                    {
                    match("sat"); if (state.failed) return ;


                    }
                    break;
                case 13 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:77:13: 'sunday'
                    {
                    match("sunday"); if (state.failed) return ;


                    }
                    break;
                case 14 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:77:26: 'sun'
                    {
                    match("sun"); if (state.failed) return ;


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WEEKDAY"

    // $ANTLR start "NUM_STR"
    public final void mNUM_STR() throws RecognitionException {
        try {
            int _type = NUM_STR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:11: ( 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' )
            int alt10=10;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:13: 'one'
                    {
                    match("one"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:21: 'two'
                    {
                    match("two"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:29: 'three'
                    {
                    match("three"); if (state.failed) return ;


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:39: 'four'
                    {
                    match("four"); if (state.failed) return ;


                    }
                    break;
                case 5 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:48: 'five'
                    {
                    match("five"); if (state.failed) return ;


                    }
                    break;
                case 6 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:57: 'six'
                    {
                    match("six"); if (state.failed) return ;


                    }
                    break;
                case 7 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:65: 'seven'
                    {
                    match("seven"); if (state.failed) return ;


                    }
                    break;
                case 8 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:75: 'eight'
                    {
                    match("eight"); if (state.failed) return ;


                    }
                    break;
                case 9 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:85: 'nine'
                    {
                    match("nine"); if (state.failed) return ;


                    }
                    break;
                case 10 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:79:94: 'ten'
                    {
                    match("ten"); if (state.failed) return ;


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM_STR"

    // $ANTLR start "DATE_SEP"
    public final void mDATE_SEP() throws RecognitionException {
        try {
            int _type = DATE_SEP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:81:11: ( '/' | '\\u5E74' | '\\u6708' | '\\u65E5' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:
            {
            if ( input.LA(1)=='/'||input.LA(1)=='\u5E74'||input.LA(1)=='\u65E5'||input.LA(1)=='\u6708' ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
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

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:83:11: ( '.' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:83:13: '.'
            {
            match('.'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:85:11: ( ':' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:85:13: ':'
            {
            match(':'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:87:11: ( '-' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:87:13: '-'
            {
            match('-'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "MINUS_A"
    public final void mMINUS_A() throws RecognitionException {
        try {
            int _type = MINUS_A;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:89:11: ( '-a' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:89:13: '-a'
            {
            match("-a"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS_A"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:91:11: ( ',' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:91:13: ','
            {
            match(','); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:93:11: ( ( '0' .. '9' )+ )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:93:13: ( '0' .. '9' )+
            {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:93:13: ( '0' .. '9' )+
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
            	    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:93:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:95:11: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:95:13: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            if ( state.backtracking==1 ) {
               _channel=HIDDEN; 
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:39: ( A | OF | ON | IN | AND | END | THE | NOW | TONIGHT | NEVER | TODAY | TOMORROW | YESTERDAY | NEXT | STs | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY | NUM_STR | DATE_SEP | DOT | COLON | MINUS | MINUS_A | COMMA | INT | WS )
        int alt12=30;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:41: A
                {
                mA(); if (state.failed) return ;

                }
                break;
            case 2 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:43: OF
                {
                mOF(); if (state.failed) return ;

                }
                break;
            case 3 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:46: ON
                {
                mON(); if (state.failed) return ;

                }
                break;
            case 4 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:49: IN
                {
                mIN(); if (state.failed) return ;

                }
                break;
            case 5 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:52: AND
                {
                mAND(); if (state.failed) return ;

                }
                break;
            case 6 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:56: END
                {
                mEND(); if (state.failed) return ;

                }
                break;
            case 7 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:60: THE
                {
                mTHE(); if (state.failed) return ;

                }
                break;
            case 8 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:64: NOW
                {
                mNOW(); if (state.failed) return ;

                }
                break;
            case 9 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:68: TONIGHT
                {
                mTONIGHT(); if (state.failed) return ;

                }
                break;
            case 10 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:76: NEVER
                {
                mNEVER(); if (state.failed) return ;

                }
                break;
            case 11 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:82: TODAY
                {
                mTODAY(); if (state.failed) return ;

                }
                break;
            case 12 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:88: TOMORROW
                {
                mTOMORROW(); if (state.failed) return ;

                }
                break;
            case 13 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:97: YESTERDAY
                {
                mYESTERDAY(); if (state.failed) return ;

                }
                break;
            case 14 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:107: NEXT
                {
                mNEXT(); if (state.failed) return ;

                }
                break;
            case 15 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:112: STs
                {
                mSTs(); if (state.failed) return ;

                }
                break;
            case 16 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:116: YEARS
                {
                mYEARS(); if (state.failed) return ;

                }
                break;
            case 17 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:122: MONTHS
                {
                mMONTHS(); if (state.failed) return ;

                }
                break;
            case 18 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:129: WEEKS
                {
                mWEEKS(); if (state.failed) return ;

                }
                break;
            case 19 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:135: DAYS
                {
                mDAYS(); if (state.failed) return ;

                }
                break;
            case 20 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:140: MONTH
                {
                mMONTH(); if (state.failed) return ;

                }
                break;
            case 21 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:146: WEEKDAY
                {
                mWEEKDAY(); if (state.failed) return ;

                }
                break;
            case 22 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:154: NUM_STR
                {
                mNUM_STR(); if (state.failed) return ;

                }
                break;
            case 23 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:162: DATE_SEP
                {
                mDATE_SEP(); if (state.failed) return ;

                }
                break;
            case 24 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:171: DOT
                {
                mDOT(); if (state.failed) return ;

                }
                break;
            case 25 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:175: COLON
                {
                mCOLON(); if (state.failed) return ;

                }
                break;
            case 26 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:181: MINUS
                {
                mMINUS(); if (state.failed) return ;

                }
                break;
            case 27 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:187: MINUS_A
                {
                mMINUS_A(); if (state.failed) return ;

                }
                break;
            case 28 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:195: COMMA
                {
                mCOMMA(); if (state.failed) return ;

                }
                break;
            case 29 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:201: INT
                {
                mINT(); if (state.failed) return ;

                }
                break;
            case 30 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:205: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_DateLexer
    public final void synpred1_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:41: ( A )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:41: A
        {
        mA(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_DateLexer

    // $ANTLR start synpred2_DateLexer
    public final void synpred2_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:43: ( OF )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:43: OF
        {
        mOF(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_DateLexer

    // $ANTLR start synpred3_DateLexer
    public final void synpred3_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:46: ( ON )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:46: ON
        {
        mON(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_DateLexer

    // $ANTLR start synpred5_DateLexer
    public final void synpred5_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:52: ( AND )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:52: AND
        {
        mAND(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_DateLexer

    // $ANTLR start synpred6_DateLexer
    public final void synpred6_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:56: ( END )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:56: END
        {
        mEND(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_DateLexer

    // $ANTLR start synpred7_DateLexer
    public final void synpred7_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:60: ( THE )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:60: THE
        {
        mTHE(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_DateLexer

    // $ANTLR start synpred8_DateLexer
    public final void synpred8_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:64: ( NOW )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:64: NOW
        {
        mNOW(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_DateLexer

    // $ANTLR start synpred9_DateLexer
    public final void synpred9_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:68: ( TONIGHT )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:68: TONIGHT
        {
        mTONIGHT(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_DateLexer

    // $ANTLR start synpred10_DateLexer
    public final void synpred10_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:76: ( NEVER )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:76: NEVER
        {
        mNEVER(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_DateLexer

    // $ANTLR start synpred11_DateLexer
    public final void synpred11_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:82: ( TODAY )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:82: TODAY
        {
        mTODAY(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_DateLexer

    // $ANTLR start synpred12_DateLexer
    public final void synpred12_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:88: ( TOMORROW )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:88: TOMORROW
        {
        mTOMORROW(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_DateLexer

    // $ANTLR start synpred13_DateLexer
    public final void synpred13_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:97: ( YESTERDAY )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:97: YESTERDAY
        {
        mYESTERDAY(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_DateLexer

    // $ANTLR start synpred14_DateLexer
    public final void synpred14_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:107: ( NEXT )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:107: NEXT
        {
        mNEXT(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_DateLexer

    // $ANTLR start synpred15_DateLexer
    public final void synpred15_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:112: ( STs )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:112: STs
        {
        mSTs(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred15_DateLexer

    // $ANTLR start synpred16_DateLexer
    public final void synpred16_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:116: ( YEARS )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:116: YEARS
        {
        mYEARS(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_DateLexer

    // $ANTLR start synpred17_DateLexer
    public final void synpred17_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:122: ( MONTHS )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:122: MONTHS
        {
        mMONTHS(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred17_DateLexer

    // $ANTLR start synpred18_DateLexer
    public final void synpred18_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:129: ( WEEKS )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:129: WEEKS
        {
        mWEEKS(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_DateLexer

    // $ANTLR start synpred19_DateLexer
    public final void synpred19_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:135: ( DAYS )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:135: DAYS
        {
        mDAYS(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred19_DateLexer

    // $ANTLR start synpred20_DateLexer
    public final void synpred20_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:140: ( MONTH )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:140: MONTH
        {
        mMONTH(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred20_DateLexer

    // $ANTLR start synpred21_DateLexer
    public final void synpred21_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:146: ( WEEKDAY )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:146: WEEKDAY
        {
        mWEEKDAY(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred21_DateLexer

    // $ANTLR start synpred22_DateLexer
    public final void synpred22_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:154: ( NUM_STR )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:154: NUM_STR
        {
        mNUM_STR(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred22_DateLexer

    // $ANTLR start synpred26_DateLexer
    public final void synpred26_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:181: ( MINUS )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:181: MINUS
        {
        mMINUS(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred26_DateLexer

    // $ANTLR start synpred27_DateLexer
    public final void synpred27_DateLexer_fragment() throws RecognitionException {   
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:187: ( MINUS_A )
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\DateLexer.g:1:187: MINUS_A
        {
        mMINUS_A(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred27_DateLexer

    public final boolean synpred8_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred22_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred21_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred21_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred26_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_DateLexer() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_DateLexer_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA4_eotS =
        "\3\uffff\1\6\3\uffff\1\11\2\uffff";
    static final String DFA4_eofS =
        "\12\uffff";
    static final String DFA4_minS =
        "\1\171\1\145\1\141\1\163\1\162\2\uffff\1\163\2\uffff";
    static final String DFA4_maxS =
        "\1\171\1\162\1\141\1\163\1\162\2\uffff\1\163\2\uffff";
    static final String DFA4_acceptS =
        "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
    static final String DFA4_specialS =
        "\12\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1",
            "\1\2\14\uffff\1\3",
            "\1\4",
            "\1\5",
            "\1\7",
            "",
            "",
            "\1\10",
            "",
            ""
    };

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "62:1: YEARS : ( 'years' | 'year' | 'yrs' | 'yr' );";
        }
    }
    static final String DFA5_eotS =
        "\3\uffff\1\6\3\uffff\1\11\2\uffff";
    static final String DFA5_eofS =
        "\12\uffff";
    static final String DFA5_minS =
        "\1\155\1\157\1\156\1\163\1\150\2\uffff\1\163\2\uffff";
    static final String DFA5_maxS =
        "\1\155\1\157\1\156\1\164\1\150\2\uffff\1\163\2\uffff";
    static final String DFA5_acceptS =
        "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
    static final String DFA5_specialS =
        "\12\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\1",
            "\1\2",
            "\1\3",
            "\1\5\1\4",
            "\1\7",
            "",
            "",
            "\1\10",
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
            return "64:1: MONTHS : ( 'months' | 'month' | 'mons' | 'mon' );";
        }
    }
    static final String DFA6_eotS =
        "\3\uffff\1\6\3\uffff\1\11\2\uffff";
    static final String DFA6_eofS =
        "\12\uffff";
    static final String DFA6_minS =
        "\1\167\2\145\1\163\1\153\2\uffff\1\163\2\uffff";
    static final String DFA6_maxS =
        "\1\167\1\153\1\145\1\163\1\153\2\uffff\1\163\2\uffff";
    static final String DFA6_acceptS =
        "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
    static final String DFA6_specialS =
        "\12\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\1",
            "\1\2\5\uffff\1\3",
            "\1\4",
            "\1\5",
            "\1\7",
            "",
            "",
            "\1\10",
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
            return "66:1: WEEKS : ( 'weeks' | 'week' | 'wks' | 'wk' );";
        }
    }
    static final String DFA8_eotS =
        "\23\uffff\1\40\1\42\1\44\1\46\1\50\1\uffff\1\52\1\54\1\56\1\60"+
        "\1\62\1\64\16\uffff\1\66\11\uffff";
    static final String DFA8_eofS =
        "\67\uffff";
    static final String DFA8_minS =
        "\2\141\1\145\1\141\1\160\1\145\1\143\1\157\1\145\1\156\1\154\1"+
        "\142\2\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171\1\162"+
        "\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145\11\uffff";
    static final String DFA8_maxS =
        "\1\163\1\165\1\145\1\141\1\165\1\145\1\143\1\157\1\145\2\156\1"+
        "\142\1\171\1\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171"+
        "\1\162\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145"+
        "\11\uffff";
    static final String DFA8_acceptS =
        "\30\uffff\1\11\6\uffff\1\1\1\2\1\12\1\13\1\14\1\15\1\3\1\4\1\5"+
        "\1\6\1\7\1\10\1\16\1\17\1\uffff\1\22\1\23\1\24\1\25\1\26\1\27\1"+
        "\30\1\20\1\21";
    static final String DFA8_specialS =
        "\67\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\4\2\uffff\1\10\1\uffff\1\2\3\uffff\1\1\2\uffff\1\3\1\7\1"+
            "\6\3\uffff\1\5",
            "\1\11\23\uffff\1\12",
            "\1\13",
            "\1\14",
            "\1\15\4\uffff\1\16",
            "\1\17",
            "\1\20",
            "\1\21",
            "\1\22",
            "\1\23",
            "\1\25\1\uffff\1\24",
            "\1\26",
            "\1\27\6\uffff\1\30",
            "\1\31",
            "\1\32",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "\1\37",
            "\1\41",
            "\1\43",
            "\1\45",
            "\1\47",
            "",
            "\1\51",
            "\1\53",
            "\1\55",
            "\1\57",
            "\1\61",
            "\1\63",
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
            "",
            "",
            "",
            "",
            "\1\65",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
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
            return "70:1: MONTH : ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' | 'december' | 'dec' );";
        }
    }
    static final String DFA9_eotS =
        "\15\uffff\1\25\1\27\1\31\1\33\1\35\1\37\1\41\16\uffff";
    static final String DFA9_eofS =
        "\42\uffff";
    static final String DFA9_minS =
        "\1\146\1\157\1\150\1\145\1\162\1\141\1\156\1\145\1\165\1\144\1"+
        "\151\1\164\1\156\1\144\1\163\1\162\1\156\1\144\1\165\1\144\16\uffff";
    static final String DFA9_maxS =
        "\1\167\1\157\1\165\1\145\1\162\1\165\1\156\1\145\1\165\1\144\1"+
        "\151\1\164\1\156\1\144\1\163\1\162\1\156\1\144\1\165\1\144\16\uffff";
    static final String DFA9_acceptS =
        "\24\uffff\1\1\1\2\1\3\1\4\1\7\1\10\1\5\1\6\1\11\1\12\1\13\1\14"+
        "\1\15\1\16";
    static final String DFA9_specialS =
        "\42\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\4\6\uffff\1\1\5\uffff\1\5\1\2\2\uffff\1\3",
            "\1\6",
            "\1\10\14\uffff\1\7",
            "\1\11",
            "\1\12",
            "\1\13\23\uffff\1\14",
            "\1\15",
            "\1\16",
            "\1\17",
            "\1\20",
            "\1\21",
            "\1\22",
            "\1\23",
            "\1\24",
            "\1\26",
            "\1\30",
            "\1\32",
            "\1\34",
            "\1\36",
            "\1\40",
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
            "",
            "",
            "",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "75:1: WEEKDAY : ( 'monday' | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' | 'thursday' | 'thu' | 'friday' | 'fri' | 'saturday' | 'sat' | 'sunday' | 'sun' );";
        }
    }
    static final String DFA10_eotS =
        "\16\uffff";
    static final String DFA10_eofS =
        "\16\uffff";
    static final String DFA10_minS =
        "\1\145\1\uffff\1\145\1\151\1\145\11\uffff";
    static final String DFA10_maxS =
        "\1\164\1\uffff\1\167\1\157\1\151\11\uffff";
    static final String DFA10_acceptS =
        "\1\uffff\1\1\3\uffff\1\10\1\11\1\2\1\3\1\12\1\4\1\5\1\6\1\7";
    static final String DFA10_specialS =
        "\16\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\5\1\3\7\uffff\1\6\1\1\3\uffff\1\4\1\2",
            "",
            "\1\11\2\uffff\1\10\16\uffff\1\7",
            "\1\13\5\uffff\1\12",
            "\1\15\3\uffff\1\14",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "79:1: NUM_STR : ( 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' );";
        }
    }
    static final String DFA12_eotS =
        "\54\uffff";
    static final String DFA12_eofS =
        "\54\uffff";
    static final String DFA12_minS =
        "\1\11\1\0\5\uffff\1\0\1\uffff\1\0\2\uffff\1\0\2\uffff\1\0\2\uffff"+
        "\1\0\2\uffff\1\0\7\uffff\1\0\1\uffff\1\0\1\uffff\2\0\5\uffff\1\0"+
        "\3\uffff";
    static final String DFA12_maxS =
        "\1\u6708\1\0\5\uffff\1\0\1\uffff\1\0\2\uffff\1\0\2\uffff\1\0\2"+
        "\uffff\1\0\2\uffff\1\0\7\uffff\1\0\1\uffff\1\0\1\uffff\2\0\5\uffff"+
        "\1\0\3\uffff";
    static final String DFA12_acceptS =
        "\2\uffff\1\2\1\3\1\24\1\26\1\36\1\uffff\1\25\1\uffff\1\15\1\20"+
        "\1\uffff\1\23\1\34\1\uffff\1\6\1\35\1\uffff\1\32\1\33\1\uffff\1"+
        "\1\1\5\1\27\1\17\1\24\1\4\1\31\1\uffff\1\21\1\uffff\1\22\2\uffff"+
        "\1\7\1\11\1\13\1\14\1\30\1\uffff\1\10\1\12\1\16";
    static final String DFA12_specialS =
        "\1\uffff\1\0\5\uffff\1\1\1\uffff\1\2\2\uffff\1\3\2\uffff\1\4\2"+
        "\uffff\1\5\2\uffff\1\6\7\uffff\1\7\1\uffff\1\10\1\uffff\1\11\1\12"+
        "\5\uffff\1\13\3\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\6\2\uffff\1\6\22\uffff\1\6\13\uffff\1\16\1\22\1\47\1\30"+
            "\12\21\1\34\46\uffff\1\25\2\uffff\1\14\1\17\1\7\2\uffff\1\33"+
            "\1\32\2\uffff\1\35\1\50\1\1\2\uffff\1\31\1\41\1\42\2\uffff\1"+
            "\37\1\uffff\1\11\u5dfa\uffff\1\30\u0770\uffff\1\30\u0122\uffff"+
            "\1\30",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "1:1: Tokens options {k=1; backtrack=true; } : ( A | OF | ON | IN | AND | END | THE | NOW | TONIGHT | NEVER | TODAY | TOMORROW | YESTERDAY | NEXT | STs | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY | NUM_STR | DATE_SEP | DOT | COLON | MINUS | MINUS_A | COMMA | INT | WS );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA12_1 = input.LA(1);

                         
                        int index12_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_DateLexer()) ) {s = 2;}

                        else if ( (synpred3_DateLexer()) ) {s = 3;}

                        else if ( (synpred20_DateLexer()) ) {s = 4;}

                        else if ( (synpred22_DateLexer()) ) {s = 5;}

                         
                        input.seek(index12_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA12_7 = input.LA(1);

                         
                        int index12_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_DateLexer()) ) {s = 4;}

                        else if ( (synpred21_DateLexer()) ) {s = 8;}

                        else if ( (synpred22_DateLexer()) ) {s = 5;}

                         
                        input.seek(index12_7);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA12_9 = input.LA(1);

                         
                        int index12_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_DateLexer()) ) {s = 10;}

                        else if ( (synpred16_DateLexer()) ) {s = 11;}

                         
                        input.seek(index12_9);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA12_12 = input.LA(1);

                         
                        int index12_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_DateLexer()) ) {s = 13;}

                        else if ( (synpred20_DateLexer()) ) {s = 4;}

                         
                        input.seek(index12_12);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA12_15 = input.LA(1);

                         
                        int index12_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_DateLexer()) ) {s = 16;}

                        else if ( (synpred22_DateLexer()) ) {s = 5;}

                         
                        input.seek(index12_15);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA12_18 = input.LA(1);

                         
                        int index12_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_DateLexer()) ) {s = 19;}

                        else if ( (synpred27_DateLexer()) ) {s = 20;}

                         
                        input.seek(index12_18);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA12_21 = input.LA(1);

                         
                        int index12_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_DateLexer()) ) {s = 22;}

                        else if ( (synpred5_DateLexer()) ) {s = 23;}

                        else if ( (synpred20_DateLexer()) ) {s = 4;}

                         
                        input.seek(index12_21);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA12_29 = input.LA(1);

                         
                        int index12_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_DateLexer()) ) {s = 30;}

                        else if ( (synpred20_DateLexer()) ) {s = 26;}

                        else if ( (synpred21_DateLexer()) ) {s = 8;}

                         
                        input.seek(index12_29);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA12_31 = input.LA(1);

                         
                        int index12_31 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_DateLexer()) ) {s = 32;}

                        else if ( (synpred21_DateLexer()) ) {s = 8;}

                         
                        input.seek(index12_31);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA12_33 = input.LA(1);

                         
                        int index12_33 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_DateLexer()) ) {s = 25;}

                        else if ( (synpred20_DateLexer()) ) {s = 26;}

                        else if ( (synpred21_DateLexer()) ) {s = 8;}

                        else if ( (synpred22_DateLexer()) ) {s = 5;}

                         
                        input.seek(index12_33);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA12_34 = input.LA(1);

                         
                        int index12_34 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_DateLexer()) ) {s = 35;}

                        else if ( (synpred9_DateLexer()) ) {s = 36;}

                        else if ( (synpred11_DateLexer()) ) {s = 37;}

                        else if ( (synpred12_DateLexer()) ) {s = 38;}

                        else if ( (synpred15_DateLexer()) ) {s = 25;}

                        else if ( (synpred21_DateLexer()) ) {s = 8;}

                        else if ( (synpred22_DateLexer()) ) {s = 5;}

                         
                        input.seek(index12_34);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA12_40 = input.LA(1);

                         
                        int index12_40 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_DateLexer()) ) {s = 41;}

                        else if ( (synpred10_DateLexer()) ) {s = 42;}

                        else if ( (synpred14_DateLexer()) ) {s = 43;}

                        else if ( (synpred15_DateLexer()) ) {s = 25;}

                        else if ( (synpred20_DateLexer()) ) {s = 26;}

                        else if ( (synpred22_DateLexer()) ) {s = 5;}

                         
                        input.seek(index12_40);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 12, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}