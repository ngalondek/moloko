// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g 2010-07-07 15:24:12

	package dev.drsoran.moloko.grammar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TimeSpecLexer extends Lexer {
    public static final int STs=11;
    public static final int MIDDAY=20;
    public static final int T__28=28;
    public static final int T__27=27;
    public static final int T__26=26;
    public static final int T__25=25;
    public static final int T__24=24;
    public static final int ON=10;
    public static final int SECONDS=17;
    public static final int INT=5;
    public static final int MIDNIGHT=19;
    public static final int MINUS=8;
    public static final int EOF=-1;
    public static final int OF=12;
    public static final int MINUTES=16;
    public static final int AT=4;
    public static final int COLON=7;
    public static final int DAYS=15;
    public static final int WS=23;
    public static final int IN=22;
    public static final int MONTHS=13;
    public static final int NOON=21;
    public static final int DATE_SEP=9;
    public static final int NEVER=18;
    public static final int DOT=6;
    public static final int HOURS=14;

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

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
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
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:12:7: ( ',' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:12:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "T__26"
    public final void mT__26() throws RecognitionException {
        try {
            int _type = T__26;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:13:7: ( 'A' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:13:9: 'A'
            {
            match('A'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__26"

    // $ANTLR start "T__27"
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:14:7: ( 'M' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:14:9: 'M'
            {
            match('M'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__27"

    // $ANTLR start "T__28"
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:15:7: ( 'P' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:15:9: 'P'
            {
            match('P'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__28"

    // $ANTLR start "NEVER"
    public final void mNEVER() throws RecognitionException {
        try {
            int _type = NEVER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:294:9: ( 'never' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:294:11: 'never'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:296:7: ( '@' | 'at' )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:296:9: '@'
                    {
                    match('@'); 

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:296:15: 'at'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:298:9: ( 'on' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:298:11: 'on'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:300:9: ( 'in' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:300:11: 'in'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:302:7: ( 'of' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:302:9: 'of'
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

    // $ANTLR start "STs"
    public final void mSTs() throws RecognitionException {
        try {
            int _type = STs;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:304:7: ( 'st' | 'th' | 'rd' | 'nd' )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:304:9: 'st'
                    {
                    match("st"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:304:16: 'th'
                    {
                    match("th"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:304:23: 'rd'
                    {
                    match("rd"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:304:30: 'nd'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:306:11: ( 'midnight' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:306:13: 'midnight'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:11: ( 'midday' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:13: 'midday'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:310:9: ( 'noon' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:310:11: 'noon'
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

    // $ANTLR start "DAYS"
    public final void mDAYS() throws RecognitionException {
        try {
            int _type = DAYS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:312:11: ( 'days' | 'day' | 'd' )
            int alt3=3;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='d') ) {
                int LA3_1 = input.LA(2);

                if ( (LA3_1=='a') ) {
                    int LA3_2 = input.LA(3);

                    if ( (LA3_2=='y') ) {
                        int LA3_4 = input.LA(4);

                        if ( (LA3_4=='s') ) {
                            alt3=1;
                        }
                        else {
                            alt3=2;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 2, input);

                        throw nvae;
                    }
                }
                else {
                    alt3=3;}
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:312:13: 'days'
                    {
                    match("days"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:312:22: 'day'
                    {
                    match("day"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:312:30: 'd'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:314:11: ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' )
            int alt4=5;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:314:13: 'hours'
                    {
                    match("hours"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:314:23: 'hour'
                    {
                    match("hour"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:314:32: 'hrs'
                    {
                    match("hrs"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:314:40: 'hr'
                    {
                    match("hr"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:314:47: 'h'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:316:11: ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' )
            int alt5=5;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:316:13: 'minutes'
                    {
                    match("minutes"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:316:25: 'minute'
                    {
                    match("minute"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:316:36: 'mins'
                    {
                    match("mins"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:316:45: 'min'
                    {
                    match("min"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:316:53: 'm'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:11: ( 'seconds' | 'second' | 'secs' | 'sec' | 's' )
            int alt6=5;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:13: 'seconds'
                    {
                    match("seconds"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:25: 'second'
                    {
                    match("second"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:36: 'secs'
                    {
                    match("secs"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:45: 'sec'
                    {
                    match("sec"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:53: 's'
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

    // $ANTLR start "MONTHS"
    public final void mMONTHS() throws RecognitionException {
        try {
            int _type = MONTHS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:320:11: ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' | 'december' | 'dec' )
            int alt7=24;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:320:13: 'january'
                    {
                    match("january"); 


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:320:25: 'jan'
                    {
                    match("jan"); 


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:320:33: 'february'
                    {
                    match("february"); 


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:320:46: 'feb'
                    {
                    match("feb"); 


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:320:54: 'march'
                    {
                    match("march"); 


                    }
                    break;
                case 6 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:320:64: 'mar'
                    {
                    match("mar"); 


                    }
                    break;
                case 7 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:320:72: 'april'
                    {
                    match("april"); 


                    }
                    break;
                case 8 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:320:82: 'apr'
                    {
                    match("apr"); 


                    }
                    break;
                case 9 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:321:5: 'may'
                    {
                    match("may"); 


                    }
                    break;
                case 10 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:321:13: 'june'
                    {
                    match("june"); 


                    }
                    break;
                case 11 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:321:22: 'jun'
                    {
                    match("jun"); 


                    }
                    break;
                case 12 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:321:30: 'july'
                    {
                    match("july"); 


                    }
                    break;
                case 13 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:321:39: 'jul'
                    {
                    match("jul"); 


                    }
                    break;
                case 14 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:321:47: 'august'
                    {
                    match("august"); 


                    }
                    break;
                case 15 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:321:58: 'aug'
                    {
                    match("aug"); 


                    }
                    break;
                case 16 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:321:66: 'september'
                    {
                    match("september"); 


                    }
                    break;
                case 17 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:321:80: 'sept'
                    {
                    match("sept"); 


                    }
                    break;
                case 18 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:321:89: 'sep'
                    {
                    match("sep"); 


                    }
                    break;
                case 19 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:322:5: 'october'
                    {
                    match("october"); 


                    }
                    break;
                case 20 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:322:17: 'oct'
                    {
                    match("oct"); 


                    }
                    break;
                case 21 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:322:25: 'november'
                    {
                    match("november"); 


                    }
                    break;
                case 22 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:322:38: 'nov'
                    {
                    match("nov"); 


                    }
                    break;
                case 23 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:322:46: 'december'
                    {
                    match("december"); 


                    }
                    break;
                case 24 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:322:59: 'dec'
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
    // $ANTLR end "MONTHS"

    // $ANTLR start "DATE_SEP"
    public final void mDATE_SEP() throws RecognitionException {
        try {
            int _type = DATE_SEP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:324:11: ( '/' | '\\u5E74' | '\\u6708' | '\\u65E5' )
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:326:11: ( '.' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:326:13: '.'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:328:9: ( ':' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:328:11: ':'
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
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:330:11: ( '-' )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:330:13: '-'
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

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:332:7: ( ( '0' .. '9' )+ )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:332:9: ( '0' .. '9' )+
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:332:9: ( '0' .. '9' )+
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
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:332:9: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

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

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:334:8: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:334:10: ( ' ' | '\\t' | '\\r' | '\\n' )
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
        // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:8: ( T__24 | T__25 | T__26 | T__27 | T__28 | NEVER | AT | ON | IN | OF | STs | MIDNIGHT | MIDDAY | NOON | DAYS | HOURS | MINUTES | SECONDS | MONTHS | DATE_SEP | DOT | COLON | MINUS | INT | WS )
        int alt9=25;
        alt9 = dfa9.predict(input);
        switch (alt9) {
            case 1 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:10: T__24
                {
                mT__24(); 

                }
                break;
            case 2 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:16: T__25
                {
                mT__25(); 

                }
                break;
            case 3 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:22: T__26
                {
                mT__26(); 

                }
                break;
            case 4 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:28: T__27
                {
                mT__27(); 

                }
                break;
            case 5 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:34: T__28
                {
                mT__28(); 

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
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:58: STs
                {
                mSTs(); 

                }
                break;
            case 12 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:62: MIDNIGHT
                {
                mMIDNIGHT(); 

                }
                break;
            case 13 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:71: MIDDAY
                {
                mMIDDAY(); 

                }
                break;
            case 14 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:78: NOON
                {
                mNOON(); 

                }
                break;
            case 15 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:83: DAYS
                {
                mDAYS(); 

                }
                break;
            case 16 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:88: HOURS
                {
                mHOURS(); 

                }
                break;
            case 17 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:94: MINUTES
                {
                mMINUTES(); 

                }
                break;
            case 18 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:102: SECONDS
                {
                mSECONDS(); 

                }
                break;
            case 19 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:110: MONTHS
                {
                mMONTHS(); 

                }
                break;
            case 20 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:117: DATE_SEP
                {
                mDATE_SEP(); 

                }
                break;
            case 21 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:126: DOT
                {
                mDOT(); 

                }
                break;
            case 22 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:130: COLON
                {
                mCOLON(); 

                }
                break;
            case 23 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:136: MINUS
                {
                mMINUS(); 

                }
                break;
            case 24 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:142: INT
                {
                mINT(); 

                }
                break;
            case 25 :
                // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:1:146: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA9 dfa9 = new DFA9(this);
    static final String DFA4_eotS =
        "\1\uffff\1\4\1\uffff\1\7\4\uffff\1\12\2\uffff";
    static final String DFA4_eofS =
        "\13\uffff";
    static final String DFA4_minS =
        "\1\150\1\157\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
    static final String DFA4_maxS =
        "\1\150\1\162\1\165\1\163\1\uffff\1\162\2\uffff\1\163\2\uffff";
    static final String DFA4_acceptS =
        "\4\uffff\1\5\1\uffff\1\3\1\4\1\uffff\1\1\1\2";
    static final String DFA4_specialS =
        "\13\uffff}>";
    static final String[] DFA4_transitionS = {
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
            return "314:1: HOURS : ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' );";
        }
    }
    static final String DFA5_eotS =
        "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
    static final String DFA5_eofS =
        "\14\uffff";
    static final String DFA5_minS =
        "\1\155\1\151\1\156\1\uffff\1\163\1\164\2\uffff\1\145\1\163\2\uffff";
    static final String DFA5_maxS =
        "\1\155\1\151\1\156\1\uffff\1\165\1\164\2\uffff\1\145\1\163\2\uffff";
    static final String DFA5_acceptS =
        "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
    static final String DFA5_specialS =
        "\14\uffff}>";
    static final String[] DFA5_transitionS = {
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
            return "316:1: MINUTES : ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' );";
        }
    }
    static final String DFA6_eotS =
        "\1\uffff\1\3\2\uffff\1\7\4\uffff\1\13\2\uffff";
    static final String DFA6_eofS =
        "\14\uffff";
    static final String DFA6_minS =
        "\1\163\1\145\1\143\1\uffff\1\157\1\156\2\uffff\1\144\1\163\2\uffff";
    static final String DFA6_maxS =
        "\1\163\1\145\1\143\1\uffff\1\163\1\156\2\uffff\1\144\1\163\2\uffff";
    static final String DFA6_acceptS =
        "\3\uffff\1\5\2\uffff\1\3\1\4\2\uffff\1\1\1\2";
    static final String DFA6_specialS =
        "\14\uffff}>";
    static final String[] DFA6_transitionS = {
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
            return "318:1: SECONDS : ( 'seconds' | 'second' | 'secs' | 'sec' | 's' );";
        }
    }
    static final String DFA7_eotS =
        "\23\uffff\1\40\1\42\1\44\1\46\1\50\1\uffff\1\52\1\54\1\56\1\60"+
        "\1\62\1\64\16\uffff\1\66\11\uffff";
    static final String DFA7_eofS =
        "\67\uffff";
    static final String DFA7_minS =
        "\2\141\1\145\1\141\1\160\1\145\1\143\1\157\1\145\1\156\1\154\1"+
        "\142\2\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171\1\162"+
        "\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145\11\uffff";
    static final String DFA7_maxS =
        "\1\163\1\165\1\145\1\141\1\165\1\145\1\143\1\157\1\145\2\156\1"+
        "\142\1\171\1\162\1\147\1\160\1\164\1\166\1\143\1\165\1\145\1\171"+
        "\1\162\1\143\1\uffff\1\151\1\165\1\164\1\157\2\145\16\uffff\1\145"+
        "\11\uffff";
    static final String DFA7_acceptS =
        "\30\uffff\1\11\6\uffff\1\1\1\2\1\12\1\13\1\14\1\15\1\3\1\4\1\5"+
        "\1\6\1\7\1\10\1\16\1\17\1\uffff\1\22\1\23\1\24\1\25\1\26\1\27\1"+
        "\30\1\20\1\21";
    static final String DFA7_specialS =
        "\67\uffff}>";
    static final String[] DFA7_transitionS = {
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
            return "320:1: MONTHS : ( 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' | 'may' | 'june' | 'jun' | 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' | 'october' | 'oct' | 'november' | 'nov' | 'december' | 'dec' );";
        }
    }
    static final String DFA9_eotS =
        "\1\uffff\1\27\11\uffff\1\35\1\uffff\1\37\1\40\26\uffff";
    static final String DFA9_eofS =
        "\45\uffff";
    static final String DFA9_minS =
        "\1\11\1\141\4\uffff\1\144\1\uffff\1\160\1\143\1\uffff\1\145\1\uffff"+
        "\1\141\1\145\12\uffff\1\157\2\uffff\1\143\1\uffff\1\144\3\uffff"+
        "\1\144\2\uffff";
    static final String DFA9_maxS =
        "\1\u6708\1\141\4\uffff\1\157\1\uffff\1\165\1\156\1\uffff\1\164"+
        "\1\uffff\1\151\1\145\12\uffff\1\166\2\uffff\1\160\1\uffff\1\156"+
        "\3\uffff\1\156\2\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\5\1\uffff\1\7\2\uffff\1\11\1\uffff\1\13"+
        "\2\uffff\1\20\1\23\1\24\1\25\1\26\1\30\1\31\1\1\1\27\1\6\1\uffff"+
        "\1\10\1\12\1\uffff\1\22\1\uffff\1\21\1\17\1\16\1\uffff\1\14\1\15";
    static final String DFA9_specialS =
        "\45\uffff}>";
    static final String[] DFA9_transitionS = {
            "\2\25\2\uffff\1\25\22\uffff\1\25\13\uffff\1\2\1\1\1\22\1\21"+
            "\12\24\1\23\5\uffff\1\7\1\3\13\uffff\1\4\2\uffff\1\5\20\uffff"+
            "\1\10\2\uffff\1\16\1\uffff\1\20\1\uffff\1\17\1\12\1\20\2\uffff"+
            "\1\15\1\6\1\11\2\uffff\1\14\1\13\1\14\u5dff\uffff\1\21\u0770"+
            "\uffff\1\21\u0122\uffff\1\21",
            "\1\26",
            "",
            "",
            "",
            "",
            "\1\14\1\30\11\uffff\1\31",
            "",
            "\1\20\3\uffff\1\7\1\20",
            "\1\20\2\uffff\1\33\7\uffff\1\32",
            "",
            "\1\34\16\uffff\1\14",
            "",
            "\1\20\7\uffff\1\36",
            "\1\20",
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
            "\1\41\6\uffff\1\20",
            "",
            "",
            "\1\35\14\uffff\1\20",
            "",
            "\1\42\11\uffff\1\37",
            "",
            "",
            "",
            "\1\44\11\uffff\1\43",
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
            return "1:1: Tokens : ( T__24 | T__25 | T__26 | T__27 | T__28 | NEVER | AT | ON | IN | OF | STs | MIDNIGHT | MIDDAY | NOON | DAYS | HOURS | MINUTES | SECONDS | MONTHS | DATE_SEP | DOT | COLON | MINUS | INT | WS );";
        }
    }
 

}