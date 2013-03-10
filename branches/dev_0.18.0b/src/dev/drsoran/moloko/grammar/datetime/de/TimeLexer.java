// $ANTLR 3.4 C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g 2013-03-03 19:57:47

   package dev.drsoran.moloko.grammar.datetime.de;
   
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
    public static final int PM=17;
    public static final int SECONDS=18;
    public static final int WS=19;

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
    public String getGrammarFileName() { return "C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g"; }

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:336:11: ( '@' | 'um' | 'am' | 'an' )
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
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:336:13: '@'
                    {
                    match('@'); 

                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:336:19: 'um'
                    {
                    match("um"); 



                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:336:26: 'am'
                    {
                    match("am"); 



                    }
                    break;
                case 4 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:336:33: 'an'
                    {
                    match("an"); 



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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:338:11: ( 'und' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:338:13: 'und'
            {
            match("und"); 



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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:340:11: ( 'nie' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:340:13: 'nie'
            {
            match("nie"); 



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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:342:11: ( 'mitternacht' ( 's' )? )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:342:13: 'mitternacht' ( 's' )?
            {
            match("mitternacht"); 



            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:342:26: ( 's' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='s') ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:342:26: 's'
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
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MIDNIGHT"

    // $ANTLR start "MIDDAY"
    public final void mMIDDAY() throws RecognitionException {
        try {
            int _type = MIDDAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:344:11: ( 'mittag' ( 's' )? )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:344:13: 'mittag' ( 's' )?
            {
            match("mittag"); 



            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:344:21: ( 's' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='s') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:344:21: 's'
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
        	// do for sure before leaving
        }
    }
    // $ANTLR end "MIDDAY"

    // $ANTLR start "DAYS"
    public final void mDAYS() throws RecognitionException {
        try {
            int _type = DAYS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:346:11: ( 'tage' | 'tag' | 'd' )
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
                            alt4=2;
                        }
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
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:346:13: 'tage'
                    {
                    match("tage"); 



                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:346:22: 'tag'
                    {
                    match("tag"); 



                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:346:30: 'd'
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:348:11: ( 'stunden' | 'stunde' | 'std' | 'h' )
            int alt5=4;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='s') ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1=='t') ) {
                    int LA5_3 = input.LA(3);

                    if ( (LA5_3=='u') ) {
                        int LA5_4 = input.LA(4);

                        if ( (LA5_4=='n') ) {
                            int LA5_6 = input.LA(5);

                            if ( (LA5_6=='d') ) {
                                int LA5_7 = input.LA(6);

                                if ( (LA5_7=='e') ) {
                                    int LA5_8 = input.LA(7);

                                    if ( (LA5_8=='n') ) {
                                        alt5=1;
                                    }
                                    else {
                                        alt5=2;
                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 5, 7, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 5, 6, input);

                                throw nvae;

                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 5, 4, input);

                            throw nvae;

                        }
                    }
                    else if ( (LA5_3=='d') ) {
                        alt5=3;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 3, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;

                }
            }
            else if ( (LA5_0=='h') ) {
                alt5=4;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:348:13: 'stunden'
                    {
                    match("stunden"); 



                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:348:26: 'stunde'
                    {
                    match("stunde"); 



                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:348:38: 'std'
                    {
                    match("std"); 



                    }
                    break;
                case 4 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:348:54: 'h'
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:350:11: ( 'minuten' | 'minute' | 'min' | 'm' )
            int alt6=4;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='m') ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1=='i') ) {
                    int LA6_2 = input.LA(3);

                    if ( (LA6_2=='n') ) {
                        int LA6_4 = input.LA(4);

                        if ( (LA6_4=='u') ) {
                            int LA6_5 = input.LA(5);

                            if ( (LA6_5=='t') ) {
                                int LA6_7 = input.LA(6);

                                if ( (LA6_7=='e') ) {
                                    int LA6_8 = input.LA(7);

                                    if ( (LA6_8=='n') ) {
                                        alt6=1;
                                    }
                                    else {
                                        alt6=2;
                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 6, 7, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 6, 5, input);

                                throw nvae;

                            }
                        }
                        else {
                            alt6=3;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 6, 2, input);

                        throw nvae;

                    }
                }
                else {
                    alt6=4;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;

            }
            switch (alt6) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:350:13: 'minuten'
                    {
                    match("minuten"); 



                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:350:26: 'minute'
                    {
                    match("minute"); 



                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:350:38: 'min'
                    {
                    match("min"); 



                    }
                    break;
                case 4 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:350:54: 'm'
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:352:11: ( 'sekunden' | 'sekunde' | 'se' ( 'c' | 'k' ) | 's' )
            int alt7=4;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='s') ) {
                int LA7_1 = input.LA(2);

                if ( (LA7_1=='e') ) {
                    int LA7_2 = input.LA(3);

                    if ( (LA7_2=='k') ) {
                        int LA7_4 = input.LA(4);

                        if ( (LA7_4=='u') ) {
                            int LA7_6 = input.LA(5);

                            if ( (LA7_6=='n') ) {
                                int LA7_7 = input.LA(6);

                                if ( (LA7_7=='d') ) {
                                    int LA7_8 = input.LA(7);

                                    if ( (LA7_8=='e') ) {
                                        int LA7_9 = input.LA(8);

                                        if ( (LA7_9=='n') ) {
                                            alt7=1;
                                        }
                                        else {
                                            alt7=2;
                                        }
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 7, 8, input);

                                        throw nvae;

                                    }
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 7, 7, input);

                                    throw nvae;

                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 7, 6, input);

                                throw nvae;

                            }
                        }
                        else {
                            alt7=3;
                        }
                    }
                    else if ( (LA7_2=='c') ) {
                        alt7=3;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 7, 2, input);

                        throw nvae;

                    }
                }
                else {
                    alt7=4;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }
            switch (alt7) {
                case 1 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:352:13: 'sekunden'
                    {
                    match("sekunden"); 



                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:352:26: 'sekunde'
                    {
                    match("sekunde"); 



                    }
                    break;
                case 3 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:352:38: 'se' ( 'c' | 'k' )
                    {
                    match("se"); 



                    if ( input.LA(1)=='c'||input.LA(1)=='k' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 4 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:352:54: 's'
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:354:11: ( 'vorm' ( DOT )? | 'vormittag' ( 's' )? )
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
                                alt10=1;
                            }
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
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:354:13: 'vorm' ( DOT )?
                    {
                    match("vorm"); 



                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:354:19: ( DOT )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='.') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                            {
                            if ( input.LA(1)=='.' ) {
                                input.consume();
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;
                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:354:26: 'vormittag' ( 's' )?
                    {
                    match("vormittag"); 



                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:354:37: ( 's' )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0=='s') ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:354:38: 's'
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
        	// do for sure before leaving
        }
    }
    // $ANTLR end "AM"

    // $ANTLR start "PM"
    public final void mPM() throws RecognitionException {
        try {
            int _type = PM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:356:11: ( 'nachm' ( DOT )? | 'nachmittag' ( 's' )? )
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
                                    alt13=1;
                                }
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
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:356:13: 'nachm' ( DOT )?
                    {
                    match("nachm"); 



                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:356:20: ( DOT )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='.') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                            {
                            if ( input.LA(1)=='.' ) {
                                input.consume();
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;
                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:356:27: 'nachmittag' ( 's' )?
                    {
                    match("nachmittag"); 



                    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:356:39: ( 's' )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0=='s') ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:356:40: 's'
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
        	// do for sure before leaving
        }
    }
    // $ANTLR end "PM"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:358:11: ( ',' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:358:13: ','
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:360:11: ( '.' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:360:13: '.'
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:362:11: ( ':' )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:362:13: ':'
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
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:364:11: ( ( '0' .. '9' )+ )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:364:13: ( '0' .. '9' )+
            {
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:364:13: ( '0' .. '9' )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( ((LA14_0 >= '0' && LA14_0 <= '9')) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
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
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:366:11: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:366:13: ( ' ' | '\\t' | '\\r' | '\\n' )
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
        // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:8: ( AT | AND | NEVER | MIDNIGHT | MIDDAY | DAYS | HOURS | MINUTES | SECONDS | AM | PM | COMMA | DOT | COLON | INT | WS )
        int alt15=16;
        switch ( input.LA(1) ) {
        case '@':
        case 'a':
            {
            alt15=1;
            }
            break;
        case 'u':
            {
            int LA15_2 = input.LA(2);

            if ( (LA15_2=='m') ) {
                alt15=1;
            }
            else if ( (LA15_2=='n') ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 2, input);

                throw nvae;

            }
            }
            break;
        case 'n':
            {
            int LA15_3 = input.LA(2);

            if ( (LA15_3=='i') ) {
                alt15=3;
            }
            else if ( (LA15_3=='a') ) {
                alt15=11;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 3, input);

                throw nvae;

            }
            }
            break;
        case 'm':
            {
            int LA15_4 = input.LA(2);

            if ( (LA15_4=='i') ) {
                int LA15_17 = input.LA(3);

                if ( (LA15_17=='t') ) {
                    int LA15_20 = input.LA(4);

                    if ( (LA15_20=='t') ) {
                        int LA15_21 = input.LA(5);

                        if ( (LA15_21=='e') ) {
                            alt15=4;
                        }
                        else if ( (LA15_21=='a') ) {
                            alt15=5;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 15, 21, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 15, 20, input);

                        throw nvae;

                    }
                }
                else if ( (LA15_17=='n') ) {
                    alt15=8;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 17, input);

                    throw nvae;

                }
            }
            else {
                alt15=8;
            }
            }
            break;
        case 'd':
        case 't':
            {
            alt15=6;
            }
            break;
        case 's':
            {
            int LA15_6 = input.LA(2);

            if ( (LA15_6=='t') ) {
                alt15=7;
            }
            else {
                alt15=9;
            }
            }
            break;
        case 'h':
            {
            alt15=7;
            }
            break;
        case 'v':
            {
            alt15=10;
            }
            break;
        case ',':
            {
            alt15=12;
            }
            break;
        case '.':
            {
            alt15=13;
            }
            break;
        case ':':
            {
            alt15=14;
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
            alt15=15;
            }
            break;
        case '\t':
        case '\n':
        case '\r':
        case ' ':
            {
            alt15=16;
            }
            break;
        default:
            NoViableAltException nvae =
                new NoViableAltException("", 15, 0, input);

            throw nvae;

        }

        switch (alt15) {
            case 1 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:10: AT
                {
                mAT(); 


                }
                break;
            case 2 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:13: AND
                {
                mAND(); 


                }
                break;
            case 3 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:17: NEVER
                {
                mNEVER(); 


                }
                break;
            case 4 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:23: MIDNIGHT
                {
                mMIDNIGHT(); 


                }
                break;
            case 5 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:32: MIDDAY
                {
                mMIDDAY(); 


                }
                break;
            case 6 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:39: DAYS
                {
                mDAYS(); 


                }
                break;
            case 7 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:44: HOURS
                {
                mHOURS(); 


                }
                break;
            case 8 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:50: MINUTES
                {
                mMINUTES(); 


                }
                break;
            case 9 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:58: SECONDS
                {
                mSECONDS(); 


                }
                break;
            case 10 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:66: AM
                {
                mAM(); 


                }
                break;
            case 11 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:69: PM
                {
                mPM(); 


                }
                break;
            case 12 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:72: COMMA
                {
                mCOMMA(); 


                }
                break;
            case 13 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:78: DOT
                {
                mDOT(); 


                }
                break;
            case 14 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:82: COLON
                {
                mCOLON(); 


                }
                break;
            case 15 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:88: INT
                {
                mINT(); 


                }
                break;
            case 16 :
                // C:\\D\\Programmierung\\Projects\\java\\.workspaces\\Moloko_dev\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:1:92: WS
                {
                mWS(); 


                }
                break;

        }

    }


 

}