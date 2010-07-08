// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g 2010-07-08 15:31:49

	package dev.drsoran.moloko.grammar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TimeSpecLexer extends Lexer {
    public static final int STs=11;
    public static final int MIDDAY=31;
    public static final int TODAY=33;
    public static final int THE=25;
    public static final int ON=10;
    public static final int SECONDS=28;
    public static final int INT=5;
    public static final int MIDNIGHT=30;
    public static final int MINUS=8;
    public static final int AND=18;
    public static final int EOF=-1;
    public static final int YEARS=20;
    public static final int OF=12;
    public static final int NUM_STR=19;
    public static final int MONTH=14;
    public static final int COLON=7;
    public static final int MINUTES=27;
    public static final int AT=4;
    public static final int DAYS=23;
    public static final int WEEKDAY=16;
    public static final int WEEKS=22;
    public static final int WS=34;
    public static final int IN=17;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int COMMA=13;
    public static final int T__38=38;
    public static final int MONTHS=21;
    public static final int NOON=32;
    public static final int NEXT=15;
    public static final int DATE_SEP=9;
    public static final int NEVER=29;
    public static final int DOT=6;
    public static final int END=24;
    public static final int HOURS=26;

    // delegates
    // delegators

    public TimeSpecLexer() {;} 
    public TimeSpecLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public TimeSpecLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g"; }

    // $ANTLR start "T__35"
    public final void mT__35() throws RecognitionException {
        try {
            int _type = T__35;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:11:7: ( '-a' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:11:9: '-a'
            {
            match("-a"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__35"

    // $ANTLR start "T__36"
    public final void mT__36() throws RecognitionException {
        try {
            int _type = T__36;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:12:7: ( 'A' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:12:9: 'A'
            {
            match('A'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__36"

    // $ANTLR start "T__37"
    public final void mT__37() throws RecognitionException {
        try {
            int _type = T__37;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:13:7: ( 'M' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:13:9: 'M'
            {
            match('M'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__37"

    // $ANTLR start "T__38"
    public final void mT__38() throws RecognitionException {
        try {
            int _type = T__38;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:14:7: ( 'P' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:14:9: 'P'
            {
            match('P'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__38"

    // $ANTLR start "TODAY"
    public final void mTODAY() throws RecognitionException {
        try {
            int _type = TODAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:534:9: ( 'today' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:534:11: 'today'
            {
            match("today"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TODAY"

    // $ANTLR start "NEVER"
    public final void mNEVER() throws RecognitionException {
        try {
            int _type = NEVER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:536:9: ( 'never' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:536:11: 'never'
            {
            match("never"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEVER"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:538:7: ( '@' | 'at' )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='@') ) {
                alt1=1;
            }
            else if ( (LA1_0=='a') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:538:9: '@'
                    {
                    match('@'); 

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:538:15: 'at'
                    {
                    match("at"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "ON"
    public final void mON() throws RecognitionException {
        try {
            int _type = ON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:540:9: ( 'on' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:540:11: 'on'
            {
            match("on"); 


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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:542:9: ( 'in' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:542:11: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IN"

    // $ANTLR start "OF"
    public final void mOF() throws RecognitionException {
        try {
            int _type = OF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:544:7: ( 'of' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:544:9: 'of'
            {
            match("of"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OF"

    // $ANTLR start "NEXT"
    public final void mNEXT() throws RecognitionException {
        try {
            int _type = NEXT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:546:8: ( 'next' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:546:10: 'next'
            {
            match("next"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEXT"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:548:7: ( 'and' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:548:9: 'and'
            {
            match("and"); 


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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:550:7: ( 'end' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:550:9: 'end'
            {
            match("end"); 


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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:552:7: ( 'the' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:552:9: 'the'
            {
            match("the"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "THE"

    // $ANTLR start "STs"
    public final void mSTs() throws RecognitionException {
        try {
            int _type = STs;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:7: ( 'st' | 'th' | 'rd' | 'nd' )
            int alt2=4;
            switch ( input.LA(1) ) {
            case 's':
                {
                alt2=1;
                }
                break;
            case 't':
                {
                alt2=2;
                }
                break;
            case 'r':
                {
                alt2=3;
                }
                break;
            case 'n':
                {
                alt2=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:9: 'st'
                    {
                    match("st"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:16: 'th'
                    {
                    match("th"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:23: 'rd'
                    {
                    match("rd"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:30: 'nd'
                    {
                    match("nd"); 


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

    // $ANTLR start "MIDNIGHT"
    public final void mMIDNIGHT() throws RecognitionException {
        try {
            int _type = MIDNIGHT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:556:11: ( 'midnight' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:556:13: 'midnight'
            {
            match("midnight"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MIDNIGHT"

    // $ANTLR start "MIDDAY"
    public final void mMIDDAY() throws RecognitionException {
        try {
            int _type = MIDDAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:558:11: ( 'midday' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:558:13: 'midday'
            {
            match("midday"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MIDDAY"

    // $ANTLR start "NOON"
    public final void mNOON() throws RecognitionException {
        try {
            int _type = NOON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:560:9: ( 'noon' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:560:11: 'noon'
            {
            match("noon"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOON"

    // $ANTLR start "YEARS"
    public final void mYEARS() throws RecognitionException {
        try {
            int _type = YEARS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:562:9: ( 'years' | 'year' | 'yrs' | 'yr' )
            int alt3=4;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:562:11: 'years'
                    {
                    match("years"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:562:21: 'year'
                    {
                    match("year"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:562:30: 'yrs'
                    {
                    match("yrs"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:562:38: 'yr'
                    {
                    match("yr"); 


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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:564:9: ( 'months' | 'month' | 'mons' | 'mon' )
            int alt4=4;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:564:11: 'months'
                    {
                    match("months"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:564:22: 'month'
                    {
                    match("month"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:564:32: 'mons'
                    {
                    match("mons"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:564:41: 'mon'
                    {
                    match("mon"); 


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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:566:9: ( 'weeks' | 'week' | 'wks' | 'wk' )
            int alt5=4;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:566:11: 'weeks'
                    {
                    match("weeks"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:566:21: 'week'
                    {
                    match("week"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:566:30: 'wks'
                    {
                    match("wks"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:566:38: 'wk'
                    {
                    match("wk"); 


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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:568:11: ( 'days' | 'day' | 'd' )
            int alt6=3;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='d') ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1=='a') ) {
                    int LA6_2 = input.LA(3);

                    if ( (LA6_2=='y') ) {
                        int LA6_4 = input.LA(4);

                        if ( (LA6_4=='s') ) {
                            alt6=1;
                        }
                        else {
                            alt6=2;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 2, input);

                        throw nvae;
                    }
                }
                else {
                    alt6=3;}
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:568:13: 'days'
                    {
                    match("days"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:568:22: 'day'
                    {
                    match("day"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:568:30: 'd'
                    {
                    match('d'); 

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

    // $ANTLR start "HOURS"
    public final void mHOURS() throws RecognitionException {
        try {
            int _type = HOURS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:570:11: ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' )
            int alt7=5;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:570:13: 'hours'
                    {
                    match("hours"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:570:23: 'hour'
                    {
                    match("hour"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:570:32: 'hrs'
                    {
                    match("hrs"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:570:40: 'hr'
                    {
                    match("hr"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:570:47: 'h'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:572:11: ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' )
            int alt8=5;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:572:13: 'minutes'
                    {
                    match("minutes"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:572:25: 'minute'
                    {
                    match("minute"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:572:36: 'mins'
                    {
                    match("mins"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:572:45: 'min'
                    {
                    match("min"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:572:53: 'm'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:574:11: ( 'seconds' | 'second' | 'secs' | 'sec' | 's' )
            int alt9=5;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:574:13: 'seconds'
                    {
                    match("seconds"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:574:25: 'second'
                    {
                    match("second"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:574:36: 'secs'
                    {
                    match("secs"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:574:45: 'sec'
                    {
                    match("sec"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:574:53: 's'
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

    // $ANTLR start "MONTH"
    public final void mMONTH() throws RecognitionException {
        try {
            int _type = MONTH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:576:11: ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' | 'december' | 'dec' )
            int alt10=24;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:576:13: 'january'
                    {
                    match("january"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:576:27: 'jan'
                    {
                    match("jan"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:576:36: 'february'
                    {
                    match("february"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:576:49: 'feb'
                    {
                    match("feb"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:576:61: 'march'
                    {
                    match("march"); 


                    }
                    break;
                case 6 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:576:71: 'mar'
                    {
                    match("mar"); 


                    }
                    break;
                case 7 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:576:84: 'april'
                    {
                    match("april"); 


                    }
                    break;
                case 8 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:576:94: 'apr'
                    {
                    match("apr"); 


                    }
                    break;
                case 9 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:577:5: 'may'
                    {
                    match("may"); 


                    }
                    break;
                case 10 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:577:19: 'june'
                    {
                    match("june"); 


                    }
                    break;
                case 11 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:577:28: 'jun'
                    {
                    match("jun"); 


                    }
                    break;
                case 12 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:577:41: 'july'
                    {
                    match("july"); 


                    }
                    break;
                case 13 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:577:53: 'jul'
                    {
                    match("jul"); 


                    }
                    break;
                case 14 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:577:63: 'august'
                    {
                    match("august"); 


                    }
                    break;
                case 15 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:577:76: 'aug'
                    {
                    match("aug"); 


                    }
                    break;
                case 16 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:578:5: 'september'
                    {
                    match("september"); 


                    }
                    break;
                case 17 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:578:19: 'sept'
                    {
                    match("sept"); 


                    }
                    break;
                case 18 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:578:28: 'sep'
                    {
                    match("sep"); 


                    }
                    break;
                case 19 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:578:41: 'october'
                    {
                    match("october"); 


                    }
                    break;
                case 20 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:578:53: 'oct'
                    {
                    match("oct"); 


                    }
                    break;
                case 21 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:578:63: 'november'
                    {
                    match("november"); 


                    }
                    break;
                case 22 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:578:76: 'nov'
                    {
                    match("nov"); 


                    }
                    break;
                case 23 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:579:5: 'december'
                    {
                    match("december"); 


                    }
                    break;
                case 24 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:579:19: 'dec'
                    {
                    match("dec"); 


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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:581:11: ( 'monday' | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' | 'thursday' | 'thu' | 'friday' | 'fri' | 'saturday' | 'sat' | 'sunday' | 'sun' )
            int alt11=14;
            alt11 = dfa11.predict(input);
            switch (alt11) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:581:13: 'monday'
                    {
                    match("monday"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:581:26: 'mon'
                    {
                    match("mon"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:581:34: 'tuesday'
                    {
                    match("tuesday"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:581:46: 'tue'
                    {
                    match("tue"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:581:54: 'wednesday'
                    {
                    match("wednesday"); 


                    }
                    break;
                case 6 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:581:68: 'wed'
                    {
                    match("wed"); 


                    }
                    break;
                case 7 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:582:5: 'thursday'
                    {
                    match("thursday"); 


                    }
                    break;
                case 8 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:582:18: 'thu'
                    {
                    match("thu"); 


                    }
                    break;
                case 9 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:582:26: 'friday'
                    {
                    match("friday"); 


                    }
                    break;
                case 10 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:582:38: 'fri'
                    {
                    match("fri"); 


                    }
                    break;
                case 11 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:582:46: 'saturday'
                    {
                    match("saturday"); 


                    }
                    break;
                case 12 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:582:60: 'sat'
                    {
                    match("sat"); 


                    }
                    break;
                case 13 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:583:5: 'sunday'
                    {
                    match("sunday"); 


                    }
                    break;
                case 14 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:583:18: 'sun'
                    {
                    match("sun"); 


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

    // $ANTLR start "DATE_SEP"
    public final void mDATE_SEP() throws RecognitionException {
        try {
            int _type = DATE_SEP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:585:11: ( '/' | '\\u5E74' | '\\u6708' | '\\u65E5' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
            {
            if ( input.LA(1)=='/'||input.LA(1)=='\u5E74'||input.LA(1)=='\u65E5'||input.LA(1)=='\u6708' ) {
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

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:587:11: ( '.' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:587:13: '.'
            {
            match('.'); 

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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:589:9: ( ':' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:589:11: ':'
            {
            match(':'); 

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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:591:11: ( '-' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:591:13: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:593:9: ( ',' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:593:11: ','
            {
            match(','); 

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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:595:7: ( ( '0' .. '9' )+ )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:595:9: ( '0' .. '9' )+
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:595:9: ( '0' .. '9' )+
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
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:595:9: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "NUM_STR"
    public final void mNUM_STR() throws RecognitionException {
        try {
            int _type = NUM_STR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:597:11: ( 'one' | 'two' | 'three' | 'four' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' )
            int alt13=9;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:597:13: 'one'
                    {
                    match("one"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:597:21: 'two'
                    {
                    match("two"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:597:29: 'three'
                    {
                    match("three"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:597:39: 'four'
                    {
                    match("four"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:597:48: 'six'
                    {
                    match("six"); 


                    }
                    break;
                case 6 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:597:56: 'seven'
                    {
                    match("seven"); 


                    }
                    break;
                case 7 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:597:66: 'eight'
                    {
                    match("eight"); 


                    }
                    break;
                case 8 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:597:76: 'nine'
                    {
                    match("nine"); 


                    }
                    break;
                case 9 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:597:85: 'ten'
                    {
                    match("ten"); 


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

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:599:8: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:599:10: ( ' ' | '\\t' | '\\r' | '\\n' )
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
        // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:8: ( T__35 | T__36 | T__37 | T__38 | TODAY | NEVER | AT | ON | IN | OF | NEXT | AND | END | THE | STs | MIDNIGHT | MIDDAY | NOON | YEARS | MONTHS | WEEKS | DAYS | HOURS | MINUTES | SECONDS | MONTH | WEEKDAY | DATE_SEP | DOT | COLON | MINUS | COMMA | INT | NUM_STR | WS )
        int alt14=35;
        alt14 = dfa14.predict(input);
        switch (alt14) {
            case 1 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:10: T__35
                {
                mT__35(); 

                }
                break;
            case 2 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:16: T__36
                {
                mT__36(); 

                }
                break;
            case 3 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:22: T__37
                {
                mT__37(); 

                }
                break;
            case 4 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:28: T__38
                {
                mT__38(); 

                }
                break;
            case 5 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:34: TODAY
                {
                mTODAY(); 

                }
                break;
            case 6 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:40: NEVER
                {
                mNEVER(); 

                }
                break;
            case 7 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:46: AT
                {
                mAT(); 

                }
                break;
            case 8 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:49: ON
                {
                mON(); 

                }
                break;
            case 9 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:52: IN
                {
                mIN(); 

                }
                break;
            case 10 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:55: OF
                {
                mOF(); 

                }
                break;
            case 11 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:58: NEXT
                {
                mNEXT(); 

                }
                break;
            case 12 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:63: AND
                {
                mAND(); 

                }
                break;
            case 13 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:67: END
                {
                mEND(); 

                }
                break;
            case 14 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:71: THE
                {
                mTHE(); 

                }
                break;
            case 15 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:75: STs
                {
                mSTs(); 

                }
                break;
            case 16 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:79: MIDNIGHT
                {
                mMIDNIGHT(); 

                }
                break;
            case 17 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:88: MIDDAY
                {
                mMIDDAY(); 

                }
                break;
            case 18 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:95: NOON
                {
                mNOON(); 

                }
                break;
            case 19 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:100: YEARS
                {
                mYEARS(); 

                }
                break;
            case 20 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:106: MONTHS
                {
                mMONTHS(); 

                }
                break;
            case 21 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:113: WEEKS
                {
                mWEEKS(); 

                }
                break;
            case 22 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:119: DAYS
                {
                mDAYS(); 

                }
                break;
            case 23 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:124: HOURS
                {
                mHOURS(); 

                }
                break;
            case 24 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:130: MINUTES
                {
                mMINUTES(); 

                }
                break;
            case 25 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:138: SECONDS
                {
                mSECONDS(); 

                }
                break;
            case 26 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:146: MONTH
                {
                mMONTH(); 

                }
                break;
            case 27 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:152: WEEKDAY
                {
                mWEEKDAY(); 

                }
                break;
            case 28 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:160: DATE_SEP
                {
                mDATE_SEP(); 

                }
                break;
            case 29 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:169: DOT
                {
                mDOT(); 

                }
                break;
            case 30 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:173: COLON
                {
                mCOLON(); 

                }
                break;
            case 31 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:179: MINUS
                {
                mMINUS(); 

                }
                break;
            case 32 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:185: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 33 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:191: INT
                {
                mINT(); 

                }
                break;
            case 34 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:195: NUM_STR
                {
                mNUM_STR(); 

                }
                break;
            case 35 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:203: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA4 dfa4 = new DFA4(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA8 dfa8 = new DFA8(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA13 dfa13 = new DFA13(this);
    protected DFA14 dfa14 = new DFA14(this);
    static final String DFA3_eotS =
        "\3\uffff\1\6\3\uffff\1\11\2\uffff";
    static final String DFA3_eofS =
        "\12\uffff";
    static final String DFA3_minS =
        "\1\171\1\145\1\141\1\163\1\162\2\uffff\1\163\2\uffff";
    static final String DFA3_maxS =
        "\1\171\1\162\1\141\1\163\1\162\2\uffff\1\163\2\uffff";
    static final String DFA3_acceptS =
        "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
    static final String DFA3_specialS =
        "\12\uffff}>";
    static final String[] DFA3_transitionS = {
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
            return "562:1: YEARS : ( 'years' | 'year' | 'yrs' | 'yr' );";
        }
    }
    static final String DFA4_eotS =
        "\3\uffff\1\6\3\uffff\1\11\2\uffff";
    static final String DFA4_eofS =
        "\12\uffff";
    static final String DFA4_minS =
        "\1\155\1\157\1\156\1\163\1\150\2\uffff\1\163\2\uffff";
    static final String DFA4_maxS =
        "\1\155\1\157\1\156\1\164\1\150\2\uffff\1\163\2\uffff";
    static final String DFA4_acceptS =
        "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
    static final String DFA4_specialS =
        "\12\uffff}>";
    static final String[] DFA4_transitionS = {
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
            return "564:1: MONTHS : ( 'months' | 'month' | 'mons' | 'mon' );";
        }
    }
    static final String DFA5_eotS =
        "\3\uffff\1\6\3\uffff\1\11\2\uffff";
    static final String DFA5_eofS =
        "\12\uffff";
    static final String DFA5_minS =
        "\1\167\2\145\1\163\1\153\2\uffff\1\163\2\uffff";
    static final String DFA5_maxS =
        "\1\167\1\153\1\145\1\163\1\153\2\uffff\1\163\2\uffff";
    static final String DFA5_acceptS =
        "\5\uffff\1\3\1\4\1\uffff\1\1\1\2";
    static final String DFA5_specialS =
        "\12\uffff}>";
    static final String[] DFA5_transitionS = {
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
            return "566:1: WEEKS : ( 'weeks' | 'week' | 'wks' | 'wk' );";
        }
    }
    static final String DFA7_eotS =
        "\1\uffff\1\4\1\uffff\1\7\4\uffff\1\12\2\uffff";
    static final String DFA7_eofS =
        "\13\uffff";
    static final String DFA7_minS =
        "\1\150\1\157\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
    static final String DFA7_maxS =
        "\1\150\1\162\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
    static final String DFA7_acceptS =
        "\4\uffff\1\5\1\uffff\1\3\1\4\1\uffff\1\1\1\2";
    static final String DFA7_specialS =
        "\13\uffff}>";
    static final String[] DFA7_transitionS = {
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
            return "570:1: HOURS : ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' );";
        }
    }
    static final String DFA8_eotS =
        "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
    static final String DFA8_eofS =
        "\14\uffff";
    static final String DFA8_minS =
        "\1\155\1\151\1\156\1\uffff\1\163\1\164\2\uffff\1\145\1\163\2\uffff";
    static final String DFA8_maxS =
        "\1\155\1\151\1\156\1\uffff\1\165\1\164\2\uffff\1\145\1\163\2\uffff";
    static final String DFA8_acceptS =
        "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
    static final String DFA8_specialS =
        "\14\uffff}>";
    static final String[] DFA8_transitionS = {
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
            return "572:1: MINUTES : ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' );";
        }
    }
    static final String DFA9_eotS =
        "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
    static final String DFA9_eofS =
        "\14\uffff";
    static final String DFA9_minS =
        "\1\163\1\145\1\143\1\uffff\1\157\1\156\2\uffff\1\144\1\163\2\uffff";
    static final String DFA9_maxS =
        "\1\163\1\145\1\143\1\uffff\1\163\1\156\2\uffff\1\144\1\163\2\uffff";
    static final String DFA9_acceptS =
        "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
    static final String DFA9_specialS =
        "\14\uffff}>";
    static final String[] DFA9_transitionS = {
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
            return "574:1: SECONDS : ( 'seconds' | 'second' | 'secs' | 'sec' | 's' );";
        }
    }
    static final String DFA10_eotS =
        "\23\uffff\1\40\1\42\1\44\1\46\1\50\1\uffff\1\52\1\54\1\56\1\60"+
        "\1\62\1\64\16\uffff\1\66\11\uffff";
    static final String DFA10_eofS =
        "\67\uffff";
    static final String DFA10_minS =
        "\2\141\1\145\1\141\1\160\1\145\1\143\1\157\1\145\1\156\1\154\1"+
        "\142\2\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171\1\162"+
        "\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145\11\uffff";
    static final String DFA10_maxS =
        "\1\163\1\165\1\145\1\141\1\165\1\145\1\143\1\157\1\145\2\156\1"+
        "\142\1\171\1\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171"+
        "\1\162\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145"+
        "\11\uffff";
    static final String DFA10_acceptS =
        "\30\uffff\1\11\6\uffff\1\1\1\2\1\12\1\13\1\14\1\15\1\3\1\4\1\5"+
        "\1\6\1\7\1\10\1\16\1\17\1\uffff\1\22\1\23\1\24\1\25\1\26\1\27\1"+
        "\30\1\20\1\21";
    static final String DFA10_specialS =
        "\67\uffff}>";
    static final String[] DFA10_transitionS = {
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
            return "576:1: MONTH : ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' | 'december' | 'dec' );";
        }
    }
    static final String DFA11_eotS =
        "\15\uffff\1\25\1\27\1\31\1\33\1\35\1\37\1\41\16\uffff";
    static final String DFA11_eofS =
        "\42\uffff";
    static final String DFA11_minS =
        "\1\146\1\157\1\150\1\145\1\162\1\141\1\156\1\145\1\165\1\144\1"+
        "\151\1\164\1\156\1\144\1\163\1\162\1\156\1\144\1\165\1\144\16\uffff";
    static final String DFA11_maxS =
        "\1\167\1\157\1\165\1\145\1\162\1\165\1\156\1\145\1\165\1\144\1"+
        "\151\1\164\1\156\1\144\1\163\1\162\1\156\1\144\1\165\1\144\16\uffff";
    static final String DFA11_acceptS =
        "\24\uffff\1\1\1\2\1\3\1\4\1\7\1\10\1\5\1\6\1\11\1\12\1\13\1\14"+
        "\1\15\1\16";
    static final String DFA11_specialS =
        "\42\uffff}>";
    static final String[] DFA11_transitionS = {
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
            return "581:1: WEEKDAY : ( 'monday' | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' | 'thursday' | 'thu' | 'friday' | 'fri' | 'saturday' | 'sat' | 'sunday' | 'sun' );";
        }
    }
    static final String DFA13_eotS =
        "\14\uffff";
    static final String DFA13_eofS =
        "\14\uffff";
    static final String DFA13_minS =
        "\1\145\1\uffff\1\145\1\uffff\1\145\7\uffff";
    static final String DFA13_maxS =
        "\1\164\1\uffff\1\167\1\uffff\1\151\7\uffff";
    static final String DFA13_acceptS =
        "\1\uffff\1\1\1\uffff\1\4\1\uffff\1\7\1\10\1\2\1\3\1\11\1\5\1\6";
    static final String DFA13_specialS =
        "\14\uffff}>";
    static final String[] DFA13_transitionS = {
            "\1\5\1\3\7\uffff\1\6\1\1\3\uffff\1\4\1\2",
            "",
            "\1\11\2\uffff\1\10\16\uffff\1\7",
            "",
            "\1\13\3\uffff\1\12",
            "",
            "",
            "",
            "",
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
            return "597:1: NUM_STR : ( 'one' | 'two' | 'three' | 'four' | 'six' | 'seven' | 'eight' | 'nine' | 'ten' );";
        }
    }
    static final String DFA14_eotS =
        "\1\uffff\1\34\12\uffff\1\50\1\uffff\1\53\2\uffff\1\56\14\uffff"+
        "\1\15\5\uffff\1\63\20\uffff\1\70\3\uffff";
    static final String DFA14_eofS =
        "\71\uffff";
    static final String DFA14_minS =
        "\1\11\1\141\3\uffff\1\145\1\144\1\uffff\1\156\1\143\1\uffff\1\151"+
        "\1\141\1\uffff\1\141\1\uffff\2\145\2\uffff\1\145\11\uffff\1\145"+
        "\2\uffff\1\166\1\157\1\uffff\1\145\2\uffff\1\143\1\uffff\1\144\1"+
        "\156\1\uffff\1\144\7\uffff\2\144\3\uffff";
    static final String DFA14_maxS =
        "\1\u6708\1\141\3\uffff\1\167\1\157\1\uffff\1\165\1\156\1\uffff"+
        "\1\156\1\165\1\uffff\1\157\1\uffff\1\153\1\145\2\uffff\1\162\11"+
        "\uffff\1\165\2\uffff\1\170\1\166\1\uffff\1\145\2\uffff\1\166\1\uffff"+
        "\2\156\1\uffff\1\145\7\uffff\1\156\1\144\3\uffff";
    static final String DFA14_acceptS =
        "\2\uffff\1\2\1\3\1\4\2\uffff\1\7\2\uffff\1\11\2\uffff\1\17\1\uffff"+
        "\1\23\2\uffff\1\27\1\32\1\uffff\1\34\1\35\1\36\1\40\1\41\1\43\1"+
        "\1\1\37\1\5\1\uffff\1\33\1\42\2\uffff\1\14\1\uffff\1\12\1\15\1\uffff"+
        "\1\31\2\uffff\1\30\1\uffff\1\25\1\26\1\16\1\6\1\13\1\22\1\10\2\uffff"+
        "\1\20\1\21\1\24";
    static final String DFA14_specialS =
        "\71\uffff}>";
    static final String[] DFA14_transitionS = {
            "\2\32\2\uffff\1\32\22\uffff\1\32\13\uffff\1\30\1\1\1\26\1\25"+
            "\12\31\1\27\5\uffff\1\7\1\2\13\uffff\1\3\2\uffff\1\4\20\uffff"+
            "\1\10\2\uffff\1\21\1\13\1\24\1\uffff\1\22\1\12\1\23\2\uffff"+
            "\1\16\1\6\1\11\2\uffff\1\15\1\14\1\5\2\uffff\1\20\1\uffff\1"+
            "\17\u5dfa\uffff\1\25\u0770\uffff\1\25\u0122\uffff\1\25",
            "\1\33",
            "",
            "",
            "",
            "\1\40\2\uffff\1\36\6\uffff\1\35\5\uffff\1\37\1\uffff\1\40",
            "\1\15\1\41\3\uffff\1\40\5\uffff\1\42",
            "",
            "\1\43\1\uffff\1\23\3\uffff\1\7\1\23",
            "\1\23\2\uffff\1\45\7\uffff\1\44",
            "",
            "\1\40\4\uffff\1\46",
            "\1\37\3\uffff\1\47\3\uffff\1\40\12\uffff\1\15\1\37",
            "",
            "\1\23\7\uffff\1\51\5\uffff\1\52",
            "",
            "\1\54\5\uffff\1\55",
            "\1\23",
            "",
            "",
            "\1\23\11\uffff\1\40\2\uffff\1\37",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\57\14\uffff\1\40\2\uffff\1\37",
            "",
            "",
            "\1\60\1\uffff\1\61",
            "\1\62\6\uffff\1\23",
            "",
            "\1\40",
            "",
            "",
            "\1\50\14\uffff\1\23\5\uffff\1\40",
            "",
            "\1\64\11\uffff\1\53",
            "\1\65",
            "",
            "\1\37\1\55",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\67\11\uffff\1\66",
            "\1\37",
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
            return "1:1: Tokens : ( T__35 | T__36 | T__37 | T__38 | TODAY | NEVER | AT | ON | IN | OF | NEXT | AND | END | THE | STs | MIDNIGHT | MIDDAY | NOON | YEARS | MONTHS | WEEKS | DAYS | HOURS | MINUTES | SECONDS | MONTH | WEEKDAY | DATE_SEP | DOT | COLON | MINUS | COMMA | INT | NUM_STR | WS );";
        }
    }
 

}