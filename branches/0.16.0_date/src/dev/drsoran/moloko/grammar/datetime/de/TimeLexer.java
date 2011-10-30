// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g 2011-10-26 16:59:32

   package dev.drsoran.moloko.grammar.datetime.de;
   
   import dev.drsoran.moloko.grammar.LexerException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TimeLexer extends Lexer {
    public static final int MIDDAY=8;
    public static final int SECONDS=16;
    public static final int INT=9;
    public static final int MIDNIGHT=7;
    public static final int AND=18;
    public static final int EOF=-1;
    public static final int AT=4;
    public static final int COLON=10;
    public static final int MINUTES=15;
    public static final int DAYS=17;
    public static final int WS=19;
    public static final int COMMA=5;
    public static final int PM=13;
    public static final int NEVER=6;
    public static final int DOT=11;
    public static final int HOURS=14;
    public static final int AM=12;

       @Override
       public void reportError( RecognitionException e )
       {
          throw new LexerException( e );
       }


    // delegates
    // delegators

    public TimeLexer() {;} 
    public TimeLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public TimeLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g"; }

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:337:11: ( '@' | 'um' | 'am' | 'an' )
            int alt1=4;
            switch ( input.LA(1) ) {
            case '@':
                {
                alt1=1;
                }
                break;
            case 'u':
                {
                alt1=2;
                }
                break;
            case 'a':
                {
                int LA1_3 = input.LA(2);

                if ( (LA1_3=='m') ) {
                    alt1=3;
                }
                else if ( (LA1_3=='n') ) {
                    alt1=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:337:13: '@'
                    {
                    match('@'); 

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:337:19: 'um'
                    {
                    match("um"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:337:26: 'am'
                    {
                    match("am"); 


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:337:33: 'an'
                    {
                    match("an"); 


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

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:339:11: ( 'und' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:339:13: 'und'
            {
            match("und"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "NEVER"
    public final void mNEVER() throws RecognitionException {
        try {
            int _type = NEVER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:341:11: ( 'nie' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:341:13: 'nie'
            {
            match("nie"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEVER"

    // $ANTLR start "MIDNIGHT"
    public final void mMIDNIGHT() throws RecognitionException {
        try {
            int _type = MIDNIGHT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:343:11: ( 'mitternacht' ( 's' )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:343:13: 'mitternacht' ( 's' )?
            {
            match("mitternacht"); 

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:343:26: ( 's' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='s') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:343:26: 's'
                    {
                    match('s'); 

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
    // $ANTLR end "MIDNIGHT"

    // $ANTLR start "MIDDAY"
    public final void mMIDDAY() throws RecognitionException {
        try {
            int _type = MIDDAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:345:11: ( 'mittag' ( 's' )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:345:13: 'mittag' ( 's' )?
            {
            match("mittag"); 

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:345:21: ( 's' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='s') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:345:21: 's'
                    {
                    match('s'); 

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
    // $ANTLR end "MIDDAY"

    // $ANTLR start "DAYS"
    public final void mDAYS() throws RecognitionException {
        try {
            int _type = DAYS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:347:11: ( 'tage' | 'tag' | 'd' )
            int alt4=3;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='t') ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1=='a') ) {
                    int LA4_3 = input.LA(3);

                    if ( (LA4_3=='g') ) {
                        int LA4_4 = input.LA(4);

                        if ( (LA4_4=='e') ) {
                            alt4=1;
                        }
                        else {
                            alt4=2;}
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 3, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA4_0=='d') ) {
                alt4=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:347:13: 'tage'
                    {
                    match("tage"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:347:22: 'tag'
                    {
                    match("tag"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:347:30: 'd'
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:349:11: ( 'stunden' | 'stunde' | 'std' | 'h' )
            int alt5=4;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:349:13: 'stunden'
                    {
                    match("stunden"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:349:26: 'stunde'
                    {
                    match("stunde"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:349:38: 'std'
                    {
                    match("std"); 


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:349:54: 'h'
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:351:11: ( 'minuten' | 'minute' | 'min' | 'm' )
            int alt6=4;
            alt6 = dfa6.predict(input);
            switch (alt6) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:351:13: 'minuten'
                    {
                    match("minuten"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:351:26: 'minute'
                    {
                    match("minute"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:351:38: 'min'
                    {
                    match("min"); 


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:351:54: 'm'
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:353:11: ( 'sekunden' | 'sekunde' | 'se' ( 'c' | 'k' ) | 's' )
            int alt7=4;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:353:13: 'sekunden'
                    {
                    match("sekunden"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:353:26: 'sekunde'
                    {
                    match("sekunde"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:353:38: 'se' ( 'c' | 'k' )
                    {
                    match("se"); 

                    if ( input.LA(1)=='c'||input.LA(1)=='k' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:353:54: 's'
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

    // $ANTLR start "AM"
    public final void mAM() throws RecognitionException {
        try {
            int _type = AM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:355:11: ( 'vorm' ( DOT )? | 'vormittag' ( 's' )? )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='v') ) {
                int LA10_1 = input.LA(2);

                if ( (LA10_1=='o') ) {
                    int LA10_2 = input.LA(3);

                    if ( (LA10_2=='r') ) {
                        int LA10_3 = input.LA(4);

                        if ( (LA10_3=='m') ) {
                            int LA10_4 = input.LA(5);

                            if ( (LA10_4=='i') ) {
                                alt10=2;
                            }
                            else {
                                alt10=1;}
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 10, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:355:13: 'vorm' ( DOT )?
                    {
                    match("vorm"); 

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:355:19: ( DOT )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='.') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:355:19: DOT
                            {
                            mDOT(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:355:26: 'vormittag' ( 's' )?
                    {
                    match("vormittag"); 

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:355:37: ( 's' )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0=='s') ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:355:38: 's'
                            {
                            match('s'); 

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
    // $ANTLR end "AM"

    // $ANTLR start "PM"
    public final void mPM() throws RecognitionException {
        try {
            int _type = PM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:357:11: ( 'nachm' ( DOT )? | 'nachmittag' ( 's' )? )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='n') ) {
                int LA13_1 = input.LA(2);

                if ( (LA13_1=='a') ) {
                    int LA13_2 = input.LA(3);

                    if ( (LA13_2=='c') ) {
                        int LA13_3 = input.LA(4);

                        if ( (LA13_3=='h') ) {
                            int LA13_4 = input.LA(5);

                            if ( (LA13_4=='m') ) {
                                int LA13_5 = input.LA(6);

                                if ( (LA13_5=='i') ) {
                                    alt13=2;
                                }
                                else {
                                    alt13=1;}
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 13, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 13, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:357:13: 'nachm' ( DOT )?
                    {
                    match("nachm"); 

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:357:20: ( DOT )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='.') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:357:20: DOT
                            {
                            mDOT(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:357:27: 'nachmittag' ( 's' )?
                    {
                    match("nachmittag"); 

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:357:39: ( 's' )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0=='s') ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:357:40: 's'
                            {
                            match('s'); 

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
    // $ANTLR end "PM"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:359:11: ( ',' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:359:13: ','
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

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:361:11: ( '.' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:361:13: '.'
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:363:11: ( ':' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:363:13: ':'
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

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:365:11: ( ( '0' .. '9' )+ )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:365:13: ( '0' .. '9' )+
            {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:365:13: ( '0' .. '9' )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0>='0' && LA14_0<='9')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:365:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:367:11: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:367:13: ( ' ' | '\\t' | '\\r' | '\\n' )
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
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:8: ( AT | AND | NEVER | MIDNIGHT | MIDDAY | DAYS | HOURS | MINUTES | SECONDS | AM | PM | COMMA | DOT | COLON | INT | WS )
        int alt15=16;
        alt15 = dfa15.predict(input);
        switch (alt15) {
            case 1 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:10: AT
                {
                mAT(); 

                }
                break;
            case 2 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:13: AND
                {
                mAND(); 

                }
                break;
            case 3 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:17: NEVER
                {
                mNEVER(); 

                }
                break;
            case 4 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:23: MIDNIGHT
                {
                mMIDNIGHT(); 

                }
                break;
            case 5 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:32: MIDDAY
                {
                mMIDDAY(); 

                }
                break;
            case 6 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:39: DAYS
                {
                mDAYS(); 

                }
                break;
            case 7 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:44: HOURS
                {
                mHOURS(); 

                }
                break;
            case 8 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:50: MINUTES
                {
                mMINUTES(); 

                }
                break;
            case 9 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:58: SECONDS
                {
                mSECONDS(); 

                }
                break;
            case 10 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:66: AM
                {
                mAM(); 

                }
                break;
            case 11 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:69: PM
                {
                mPM(); 

                }
                break;
            case 12 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:72: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 13 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:78: DOT
                {
                mDOT(); 

                }
                break;
            case 14 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:82: COLON
                {
                mCOLON(); 

                }
                break;
            case 15 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:88: INT
                {
                mINT(); 

                }
                break;
            case 16 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:92: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA5 dfa5 = new DFA5(this);
    protected DFA6 dfa6 = new DFA6(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA15 dfa15 = new DFA15(this);
    static final String DFA5_eotS =
        "\10\uffff\1\12\2\uffff";
    static final String DFA5_eofS =
        "\13\uffff";
    static final String DFA5_minS =
        "\1\150\1\164\1\uffff\1\144\1\156\1\uffff\1\144\1\145\1\156\2\uffff";
    static final String DFA5_maxS =
        "\1\163\1\164\1\uffff\1\165\1\156\1\uffff\1\144\1\145\1\156\2\uffff";
    static final String DFA5_acceptS =
        "\2\uffff\1\4\2\uffff\1\3\3\uffff\1\1\1\2";
    static final String DFA5_specialS =
        "\13\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\2\12\uffff\1\1",
            "\1\3",
            "",
            "\1\5\20\uffff\1\4",
            "\1\6",
            "",
            "\1\7",
            "\1\10",
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
            return "349:1: HOURS : ( 'stunden' | 'stunde' | 'std' | 'h' );";
        }
    }
    static final String DFA6_eotS =
        "\1\uffff\1\3\2\uffff\1\6\3\uffff\1\12\2\uffff";
    static final String DFA6_eofS =
        "\13\uffff";
    static final String DFA6_minS =
        "\1\155\1\151\1\156\1\uffff\1\165\1\164\1\uffff\1\145\1\156\2\uffff";
    static final String DFA6_maxS =
        "\1\155\1\151\1\156\1\uffff\1\165\1\164\1\uffff\1\145\1\156\2\uffff";
    static final String DFA6_acceptS =
        "\3\uffff\1\4\2\uffff\1\3\2\uffff\1\1\1\2";
    static final String DFA6_specialS =
        "\13\uffff}>";
    static final String[] DFA6_transitionS = {
            "\1\1",
            "\1\2",
            "\1\4",
            "",
            "\1\5",
            "\1\7",
            "",
            "\1\10",
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
            return "351:1: MINUTES : ( 'minuten' | 'minute' | 'min' | 'm' );";
        }
    }
    static final String DFA7_eotS =
        "\1\uffff\1\3\2\uffff\1\5\4\uffff\1\13\2\uffff";
    static final String DFA7_eofS =
        "\14\uffff";
    static final String DFA7_minS =
        "\1\163\1\145\1\143\1\uffff\1\165\1\uffff\1\156\1\144\1\145\1\156"+
        "\2\uffff";
    static final String DFA7_maxS =
        "\1\163\1\145\1\153\1\uffff\1\165\1\uffff\1\156\1\144\1\145\1\156"+
        "\2\uffff";
    static final String DFA7_acceptS =
        "\3\uffff\1\4\1\uffff\1\3\4\uffff\1\1\1\2";
    static final String DFA7_specialS =
        "\14\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\1",
            "\1\2",
            "\1\5\7\uffff\1\4",
            "",
            "\1\6",
            "",
            "\1\7",
            "\1\10",
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
            return "353:1: SECONDS : ( 'sekunden' | 'sekunde' | 'se' ( 'c' | 'k' ) | 's' );";
        }
    }
    static final String DFA15_eotS =
        "\4\uffff\1\22\1\uffff\1\23\21\uffff";
    static final String DFA15_eofS =
        "\30\uffff";
    static final String DFA15_minS =
        "\1\11\1\uffff\1\155\1\141\1\151\1\uffff\1\164\12\uffff\1\156\2"+
        "\uffff\1\164\1\141\2\uffff";
    static final String DFA15_maxS =
        "\1\166\1\uffff\1\156\2\151\1\uffff\1\164\12\uffff\1\164\2\uffff"+
        "\1\164\1\145\2\uffff";
    static final String DFA15_acceptS =
        "\1\uffff\1\1\3\uffff\1\6\1\uffff\1\7\1\12\1\14\1\15\1\16\1\17\1"+
        "\20\1\2\1\3\1\13\1\uffff\1\10\1\11\2\uffff\1\4\1\5";
    static final String DFA15_specialS =
        "\30\uffff}>";
    static final String[] DFA15_transitionS = {
            "\2\15\2\uffff\1\15\22\uffff\1\15\13\uffff\1\11\1\uffff\1\12"+
            "\1\uffff\12\14\1\13\5\uffff\1\1\40\uffff\1\1\2\uffff\1\5\3\uffff"+
            "\1\7\4\uffff\1\4\1\3\4\uffff\1\6\1\5\1\2\1\10",
            "",
            "\1\1\1\16",
            "\1\20\7\uffff\1\17",
            "\1\21",
            "",
            "\1\7",
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
            "\1\22\5\uffff\1\24",
            "",
            "",
            "\1\25",
            "\1\27\3\uffff\1\26",
            "",
            ""
    };

    static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
    static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
    static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
    static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
    static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
    static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
    static final short[][] DFA15_transition;

    static {
        int numStates = DFA15_transitionS.length;
        DFA15_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
        }
    }

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = DFA15_eot;
            this.eof = DFA15_eof;
            this.min = DFA15_min;
            this.max = DFA15_max;
            this.accept = DFA15_accept;
            this.special = DFA15_special;
            this.transition = DFA15_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( AT | AND | NEVER | MIDNIGHT | MIDDAY | DAYS | HOURS | MINUTES | SECONDS | AM | PM | COMMA | DOT | COLON | INT | WS );";
        }
    }
 

}