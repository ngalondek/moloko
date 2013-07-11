// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g 2013-07-10 15:39:35

   package dev.drsoran.moloko.grammar.datetime;
   
   import dev.drsoran.moloko.grammar.LexerException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class TimeLexer extends Lexer {
    public static final int EOF=-1;
    public static final int AM=4;
    public static final int AND=5;
    public static final int AT=6;
    public static final int COLON=7;
    public static final int COMMA=8;
    public static final int DAYS=9;
    public static final int DOT=10;
    public static final int HOURS=11;
    public static final int INT=12;
    public static final int MIDDAY=13;
    public static final int MIDNIGHT=14;
    public static final int MINUTES=15;
    public static final int NEVER=16;
    public static final int NOON=17;
    public static final int PM=18;
    public static final int SECONDS=19;
    public static final int WS=20;

       @Override
       public void reportError( RecognitionException e )
       {
          throw new LexerException( e );
       }


    // delegates
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public TimeLexer() {} 
    public TimeLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public TimeLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g"; }

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:338:11: ( '@' | 'at' )
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
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:338:13: '@'
                    {
                    match('@'); 

                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:338:19: 'at'
                    {
                    match("at"); 



                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:340:11: ( 'and' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:340:13: 'and'
            {
            match("and"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "NEVER"
    public final void mNEVER() throws RecognitionException {
        try {
            int _type = NEVER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:342:11: ( 'never' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:342:13: 'never'
            {
            match("never"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NEVER"

    // $ANTLR start "MIDNIGHT"
    public final void mMIDNIGHT() throws RecognitionException {
        try {
            int _type = MIDNIGHT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:344:11: ( 'midnight' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:344:13: 'midnight'
            {
            match("midnight"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MIDNIGHT"

    // $ANTLR start "MIDDAY"
    public final void mMIDDAY() throws RecognitionException {
        try {
            int _type = MIDDAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:346:11: ( 'midday' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:346:13: 'midday'
            {
            match("midday"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MIDDAY"

    // $ANTLR start "NOON"
    public final void mNOON() throws RecognitionException {
        try {
            int _type = NOON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:348:11: ( 'noon' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:348:13: 'noon'
            {
            match("noon"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "NOON"

    // $ANTLR start "DAYS"
    public final void mDAYS() throws RecognitionException {
        try {
            int _type = DAYS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:350:11: ( 'days' | 'day' | 'd' )
            int alt2=3;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='d') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='a') ) {
                    int LA2_2 = input.LA(3);

                    if ( (LA2_2=='y') ) {
                        int LA2_4 = input.LA(4);

                        if ( (LA2_4=='s') ) {
                            alt2=1;
                        }
                        else {
                            alt2=2;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 2, input);

                        throw nvae;

                    }
                }
                else {
                    alt2=3;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }
            switch (alt2) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:350:13: 'days'
                    {
                    match("days"); 



                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:350:22: 'day'
                    {
                    match("day"); 



                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:350:30: 'd'
                    {
                    match('d'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DAYS"

    // $ANTLR start "HOURS"
    public final void mHOURS() throws RecognitionException {
        try {
            int _type = HOURS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:352:11: ( 'hours' | 'hour' | 'hrs' | 'hr' | 'h' )
            int alt3=5;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='h') ) {
                switch ( input.LA(2) ) {
                case 'o':
                    {
                    int LA3_2 = input.LA(3);

                    if ( (LA3_2=='u') ) {
                        int LA3_5 = input.LA(4);

                        if ( (LA3_5=='r') ) {
                            int LA3_8 = input.LA(5);

                            if ( (LA3_8=='s') ) {
                                alt3=1;
                            }
                            else {
                                alt3=2;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 5, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 2, input);

                        throw nvae;

                    }
                    }
                    break;
                case 'r':
                    {
                    int LA3_3 = input.LA(3);

                    if ( (LA3_3=='s') ) {
                        alt3=3;
                    }
                    else {
                        alt3=4;
                    }
                    }
                    break;
                default:
                    alt3=5;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;

            }
            switch (alt3) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:352:13: 'hours'
                    {
                    match("hours"); 



                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:352:23: 'hour'
                    {
                    match("hour"); 



                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:352:32: 'hrs'
                    {
                    match("hrs"); 



                    }
                    break;
                case 4 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:352:40: 'hr'
                    {
                    match("hr"); 



                    }
                    break;
                case 5 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:352:47: 'h'
                    {
                    match('h'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HOURS"

    // $ANTLR start "MINUTES"
    public final void mMINUTES() throws RecognitionException {
        try {
            int _type = MINUTES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:354:11: ( 'minutes' | 'minute' | 'mins' | 'min' | 'm' )
            int alt4=5;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='m') ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1=='i') ) {
                    int LA4_2 = input.LA(3);

                    if ( (LA4_2=='n') ) {
                        switch ( input.LA(4) ) {
                        case 'u':
                            {
                            int LA4_5 = input.LA(5);

                            if ( (LA4_5=='t') ) {
                                int LA4_8 = input.LA(6);

                                if ( (LA4_8=='e') ) {
                                    int LA4_9 = input.LA(7);

                                    if ( (LA4_9=='s') ) {
                                        alt4=1;
                                    }
                                    else {
                                        alt4=2;
                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 4, 8, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 4, 5, input);

                                throw nvae;

                            }
                            }
                            break;
                        case 's':
                            {
                            alt4=3;
                            }
                            break;
                        default:
                            alt4=4;
                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 2, input);

                        throw nvae;

                    }
                }
                else {
                    alt4=5;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:354:13: 'minutes'
                    {
                    match("minutes"); 



                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:354:25: 'minute'
                    {
                    match("minute"); 



                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:354:36: 'mins'
                    {
                    match("mins"); 



                    }
                    break;
                case 4 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:354:45: 'min'
                    {
                    match("min"); 



                    }
                    break;
                case 5 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:354:53: 'm'
                    {
                    match('m'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MINUTES"

    // $ANTLR start "SECONDS"
    public final void mSECONDS() throws RecognitionException {
        try {
            int _type = SECONDS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:356:11: ( 'seconds' | 'second' | 'secs' | 'sec' | 's' )
            int alt5=5;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='s') ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1=='e') ) {
                    int LA5_2 = input.LA(3);

                    if ( (LA5_2=='c') ) {
                        switch ( input.LA(4) ) {
                        case 'o':
                            {
                            int LA5_5 = input.LA(5);

                            if ( (LA5_5=='n') ) {
                                int LA5_8 = input.LA(6);

                                if ( (LA5_8=='d') ) {
                                    int LA5_9 = input.LA(7);

                                    if ( (LA5_9=='s') ) {
                                        alt5=1;
                                    }
                                    else {
                                        alt5=2;
                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 5, 8, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 5, 5, input);

                                throw nvae;

                            }
                            }
                            break;
                        case 's':
                            {
                            alt5=3;
                            }
                            break;
                        default:
                            alt5=4;
                        }

                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 2, input);

                        throw nvae;

                    }
                }
                else {
                    alt5=5;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:356:13: 'seconds'
                    {
                    match("seconds"); 



                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:356:25: 'second'
                    {
                    match("second"); 



                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:356:36: 'secs'
                    {
                    match("secs"); 



                    }
                    break;
                case 4 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:356:45: 'sec'
                    {
                    match("sec"); 



                    }
                    break;
                case 5 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:356:53: 's'
                    {
                    match('s'); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "SECONDS"

    // $ANTLR start "AM"
    public final void mAM() throws RecognitionException {
        try {
            int _type = AM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:358:11: ( 'a' ( 'm' )? | '\\u4E0A' | '\\u5348\\u524D' | '\\uC624\\uC804' )
            int alt7=4;
            switch ( input.LA(1) ) {
            case 'a':
                {
                alt7=1;
                }
                break;
            case '\u4E0A':
                {
                alt7=2;
                }
                break;
            case '\u5348':
                {
                alt7=3;
                }
                break;
            case '\uC624':
                {
                alt7=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }

            switch (alt7) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:358:13: 'a' ( 'm' )?
                    {
                    match('a'); 

                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:358:16: ( 'm' )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='m') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:358:17: 'm'
                            {
                            match('m'); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:358:25: '\\u4E0A'
                    {
                    match('\u4E0A'); 

                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:358:36: '\\u5348\\u524D'
                    {
                    match("\u5348\u524D"); 



                    }
                    break;
                case 4 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:358:53: '\\uC624\\uC804'
                    {
                    match("\uC624\uC804"); 



                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AM"

    // $ANTLR start "PM"
    public final void mPM() throws RecognitionException {
        try {
            int _type = PM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:360:11: ( 'p' ( 'm' )? | '\\u4E0B' | '\\u5348\\u5F8C' | '\\uC624\\uD6C4' )
            int alt9=4;
            switch ( input.LA(1) ) {
            case 'p':
                {
                alt9=1;
                }
                break;
            case '\u4E0B':
                {
                alt9=2;
                }
                break;
            case '\u5348':
                {
                alt9=3;
                }
                break;
            case '\uC624':
                {
                alt9=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }

            switch (alt9) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:360:13: 'p' ( 'm' )?
                    {
                    match('p'); 

                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:360:16: ( 'm' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='m') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:360:17: 'm'
                            {
                            match('m'); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:360:25: '\\u4E0B'
                    {
                    match('\u4E0B'); 

                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:360:36: '\\u5348\\u5F8C'
                    {
                    match("\u5348\u5F8C"); 



                    }
                    break;
                case 4 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:360:53: '\\uC624\\uD6C4'
                    {
                    match("\uC624\uD6C4"); 



                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PM"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:362:11: ( ',' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:362:13: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:364:11: ( '.' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:364:13: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:366:11: ( ':' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:366:13: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:368:11: ( ( '0' .. '9' )+ )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:368:13: ( '0' .. '9' )+
            {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:368:13: ( '0' .. '9' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0 >= '0' && LA10_0 <= '9')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


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
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:370:11: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:370:13: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:8: ( AT | AND | NEVER | MIDNIGHT | MIDDAY | NOON | DAYS | HOURS | MINUTES | SECONDS | AM | PM | COMMA | DOT | COLON | INT | WS )
        int alt11=17;
        switch ( input.LA(1) ) {
        case '@':
            {
            alt11=1;
            }
            break;
        case 'a':
            {
            switch ( input.LA(2) ) {
            case 't':
                {
                alt11=1;
                }
                break;
            case 'n':
                {
                alt11=2;
                }
                break;
            default:
                alt11=11;
            }

            }
            break;
        case 'n':
            {
            int LA11_3 = input.LA(2);

            if ( (LA11_3=='e') ) {
                alt11=3;
            }
            else if ( (LA11_3=='o') ) {
                alt11=6;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 3, input);

                throw nvae;

            }
            }
            break;
        case 'm':
            {
            int LA11_4 = input.LA(2);

            if ( (LA11_4=='i') ) {
                int LA11_20 = input.LA(3);

                if ( (LA11_20=='d') ) {
                    int LA11_22 = input.LA(4);

                    if ( (LA11_22=='n') ) {
                        alt11=4;
                    }
                    else if ( (LA11_22=='d') ) {
                        alt11=5;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 11, 22, input);

                        throw nvae;

                    }
                }
                else if ( (LA11_20=='n') ) {
                    alt11=9;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 20, input);

                    throw nvae;

                }
            }
            else {
                alt11=9;
            }
            }
            break;
        case 'd':
            {
            alt11=7;
            }
            break;
        case 'h':
            {
            alt11=8;
            }
            break;
        case 's':
            {
            alt11=10;
            }
            break;
        case '\u4E0A':
            {
            alt11=11;
            }
            break;
        case '\u5348':
            {
            int LA11_9 = input.LA(2);

            if ( (LA11_9=='\u524D') ) {
                alt11=11;
            }
            else if ( (LA11_9=='\u5F8C') ) {
                alt11=12;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 9, input);

                throw nvae;

            }
            }
            break;
        case '\uC624':
            {
            int LA11_10 = input.LA(2);

            if ( (LA11_10=='\uC804') ) {
                alt11=11;
            }
            else if ( (LA11_10=='\uD6C4') ) {
                alt11=12;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 10, input);

                throw nvae;

            }
            }
            break;
        case 'p':
        case '\u4E0B':
            {
            alt11=12;
            }
            break;
        case ',':
            {
            alt11=13;
            }
            break;
        case '.':
            {
            alt11=14;
            }
            break;
        case ':':
            {
            alt11=15;
            }
            break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
            {
            alt11=16;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt11=17;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 11, 0, input);

            throw nvae;

        }

        switch (alt11) {
            case 1 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:10: AT
                {
                mAT(); 


                }
                break;
            case 2 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:13: AND
                {
                mAND(); 


                }
                break;
            case 3 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:17: NEVER
                {
                mNEVER(); 


                }
                break;
            case 4 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:23: MIDNIGHT
                {
                mMIDNIGHT(); 


                }
                break;
            case 5 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:32: MIDDAY
                {
                mMIDDAY(); 


                }
                break;
            case 6 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:39: NOON
                {
                mNOON(); 


                }
                break;
            case 7 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:44: DAYS
                {
                mDAYS(); 


                }
                break;
            case 8 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:49: HOURS
                {
                mHOURS(); 


                }
                break;
            case 9 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:55: MINUTES
                {
                mMINUTES(); 


                }
                break;
            case 10 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:63: SECONDS
                {
                mSECONDS(); 


                }
                break;
            case 11 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:71: AM
                {
                mAM(); 


                }
                break;
            case 12 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:74: PM
                {
                mPM(); 


                }
                break;
            case 13 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:77: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 14 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:83: DOT
                {
                mDOT(); 


                }
                break;
            case 15 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:87: COLON
                {
                mCOLON(); 


                }
                break;
            case 16 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:93: INT
                {
                mINT(); 


                }
                break;
            case 17 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Time.g:1:97: WS
                {
                mWS(); 


                }
                break;

        }

    }


 

}