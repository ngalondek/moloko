// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g 2011-06-21 06:20:37

   package dev.drsoran.moloko.grammar.datetime.de;

   import dev.drsoran.moloko.grammar.LexerException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class DateLexer extends Lexer {
    public static final int TODAY=26;
    public static final int A=7;
    public static final int ON=17;
    public static final int NOW=4;
    public static final int INT=5;
    public static final int MINUS=14;
    public static final int AND=23;
    public static final int EOF=-1;
    public static final int YEARS=11;
    public static final int OF=12;
    public static final int NUM_STR=6;
    public static final int MONTH=19;
    public static final int COLON=15;
    public static final int DAYS=8;
    public static final int WS=30;
    public static final int WEEKDAY=21;
    public static final int WEEKS=9;
    public static final int IN=22;
    public static final int OF_THE=25;
    public static final int COMMA=18;
    public static final int MONTHS=10;
    public static final int NEXT=20;
    public static final int DATE_SEP=16;
    public static final int NEVER=27;
    public static final int DOT=13;
    public static final int END=24;
    public static final int YESTERDAY=29;
    public static final int TOMORROW=28;

    // delegates
    // delegators

    public DateLexer() {;} 
    public DateLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public DateLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g"; }

    // $ANTLR start "A"
    public final void mA() throws RecognitionException {
        try {
            int _type = A;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:11: ( 'ein' ( 'e' | 'er' | 'es' | 'em' | 'en' )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:13: 'ein' ( 'e' | 'er' | 'es' | 'em' | 'en' )?
            {
            match("ein"); 

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:18: ( 'e' | 'er' | 'es' | 'em' | 'en' )?
            int alt1=6;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='e') ) {
                switch ( input.LA(2) ) {
                    case 'r':
                        {
                        alt1=2;
                        }
                        break;
                    case 's':
                        {
                        alt1=3;
                        }
                        break;
                    case 'm':
                        {
                        alt1=4;
                        }
                        break;
                    case 'n':
                        {
                        alt1=5;
                        }
                        break;
                }

            }
            switch (alt1) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:19: 'e'
                    {
                    match('e'); 

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:23: 'er'
                    {
                    match("er"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:28: 'es'
                    {
                    match("es"); 


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:33: 'em'
                    {
                    match("em"); 


                    }
                    break;
                case 5 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:399:38: 'en'
                    {
                    match("en"); 


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
    // $ANTLR end "A"

    // $ANTLR start "ON"
    public final void mON() throws RecognitionException {
        try {
            int _type = ON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:401:11: ( 'am' | 'an' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='a') ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1=='m') ) {
                    alt2=1;
                }
                else if ( (LA2_1=='n') ) {
                    alt2=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:401:13: 'am'
                    {
                    match("am"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:401:20: 'an'
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
    // $ANTLR end "ON"

    // $ANTLR start "OF"
    public final void mOF() throws RecognitionException {
        try {
            int _type = OF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:403:11: ( 'von' | 'vom' | 'ab' | 'des' )
            int alt3=4;
            switch ( input.LA(1) ) {
            case 'v':
                {
                int LA3_1 = input.LA(2);

                if ( (LA3_1=='o') ) {
                    int LA3_4 = input.LA(3);

                    if ( (LA3_4=='n') ) {
                        alt3=1;
                    }
                    else if ( (LA3_4=='m') ) {
                        alt3=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 4, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
                }
                break;
            case 'a':
                {
                alt3=3;
                }
                break;
            case 'd':
                {
                alt3=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:403:13: 'von'
                    {
                    match("von"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:403:21: 'vom'
                    {
                    match("vom"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:403:29: 'ab'
                    {
                    match("ab"); 


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:403:36: 'des'
                    {
                    match("des"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OF"

    // $ANTLR start "OF_THE"
    public final void mOF_THE() throws RecognitionException {
        try {
            int _type = OF_THE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:11: ( 'der' | 'die' | 'das' | 'dem' | 'den' )
            int alt4=5;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='d') ) {
                switch ( input.LA(2) ) {
                case 'e':
                    {
                    switch ( input.LA(3) ) {
                    case 'r':
                        {
                        alt4=1;
                        }
                        break;
                    case 'm':
                        {
                        alt4=4;
                        }
                        break;
                    case 'n':
                        {
                        alt4=5;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 2, input);

                        throw nvae;
                    }

                    }
                    break;
                case 'i':
                    {
                    alt4=2;
                    }
                    break;
                case 'a':
                    {
                    alt4=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:13: 'der'
                    {
                    match("der"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:21: 'die'
                    {
                    match("die"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:29: 'das'
                    {
                    match("das"); 


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:37: 'dem'
                    {
                    match("dem"); 


                    }
                    break;
                case 5 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:405:45: 'den'
                    {
                    match("den"); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OF_THE"

    // $ANTLR start "IN"
    public final void mIN() throws RecognitionException {
        try {
            int _type = IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:407:11: ( 'in' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:407:13: 'in'
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

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:409:11: ( 'und' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:409:13: 'und'
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

    // $ANTLR start "END"
    public final void mEND() throws RecognitionException {
        try {
            int _type = END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:411:11: ( 'ende' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:411:13: 'ende'
            {
            match("ende"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END"

    // $ANTLR start "NOW"
    public final void mNOW() throws RecognitionException {
        try {
            int _type = NOW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:413:11: ( 'jetzt' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:413:13: 'jetzt'
            {
            match("jetzt"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOW"

    // $ANTLR start "NEVER"
    public final void mNEVER() throws RecognitionException {
        try {
            int _type = NEVER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:415:11: ( 'nie' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:415:13: 'nie'
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

    // $ANTLR start "TODAY"
    public final void mTODAY() throws RecognitionException {
        try {
            int _type = TODAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:417:11: ( 'heute' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:417:13: 'heute'
            {
            match("heute"); 


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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:419:11: ( 'morgen' | 'mrg' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='m') ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1=='o') ) {
                    alt5=1;
                }
                else if ( (LA5_1=='r') ) {
                    alt5=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:419:13: 'morgen'
                    {
                    match("morgen"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:419:24: 'mrg'
                    {
                    match("mrg"); 


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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:421:11: ( 'gestern' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:421:13: 'gestern'
            {
            match("gestern"); 


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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:11: ( 'n' ( 'a' | 'ae' | 'ä' ) 'chst' ( 'e' | 'er' | 'es' ) )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:13: 'n' ( 'a' | 'ae' | 'ä' ) 'chst' ( 'e' | 'er' | 'es' )
            {
            match('n'); 
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:16: ( 'a' | 'ae' | 'ä' )
            int alt6=3;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='a') ) {
                int LA6_1 = input.LA(2);

                if ( (LA6_1=='e') ) {
                    alt6=2;
                }
                else if ( (LA6_1=='c') ) {
                    alt6=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA6_0=='\u00E4') ) {
                alt6=3;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:17: 'a'
                    {
                    match('a'); 

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:21: 'ae'
                    {
                    match("ae"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:26: 'ä'
                    {
                    match('\u00E4'); 

                    }
                    break;

            }

            match("chst"); 

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:37: ( 'e' | 'er' | 'es' )
            int alt7=3;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='e') ) {
                switch ( input.LA(2) ) {
                case 'r':
                    {
                    alt7=2;
                    }
                    break;
                case 's':
                    {
                    alt7=3;
                    }
                    break;
                default:
                    alt7=1;}

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:38: 'e'
                    {
                    match('e'); 

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:42: 'er'
                    {
                    match("er"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:423:47: 'es'
                    {
                    match("es"); 


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
    // $ANTLR end "NEXT"

    // $ANTLR start "YEARS"
    public final void mYEARS() throws RecognitionException {
        try {
            int _type = YEARS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:11: ( 'jahr' ( 'e' )? | 'jhr' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='j') ) {
                int LA9_1 = input.LA(2);

                if ( (LA9_1=='a') ) {
                    alt9=1;
                }
                else if ( (LA9_1=='h') ) {
                    alt9=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:13: 'jahr' ( 'e' )?
                    {
                    match("jahr"); 

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:19: ( 'e' )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='e') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:19: 'e'
                            {
                            match('e'); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:425:26: 'jhr'
                    {
                    match("jhr"); 


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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:11: ( 'monat' ( 'e' )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:13: 'monat' ( 'e' )?
            {
            match("monat"); 

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:20: ( 'e' )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='e') ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:427:20: 'e'
                    {
                    match('e'); 

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
    // $ANTLR end "MONTHS"

    // $ANTLR start "WEEKS"
    public final void mWEEKS() throws RecognitionException {
        try {
            int _type = WEEKS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:11: ( 'woche' ( 'n' )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:13: 'woche' ( 'n' )?
            {
            match("woche"); 

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:20: ( 'n' )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='n') ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:429:20: 'n'
                    {
                    match('n'); 

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
    // $ANTLR end "WEEKS"

    // $ANTLR start "DAYS"
    public final void mDAYS() throws RecognitionException {
        try {
            int _type = DAYS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:431:11: ( 'tag' ( 'e' )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:431:13: 'tag' ( 'e' )?
            {
            match("tag"); 

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:431:18: ( 'e' )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='e') ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:431:18: 'e'
                    {
                    match('e'); 

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
    // $ANTLR end "DAYS"

    // $ANTLR start "MONTH"
    public final void mMONTH() throws RecognitionException {
        try {
            int _type = MONTH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:11: ( 'jan' | 'feb' | 'm' ( 'a' | 'ä' | 'ae' ) 'r' | 'apr' | 'mai' | 'jun' | 'jul' | 'aug' | 'sep' | 'okt' | 'nov' | 'dez' )
            int alt14=12;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:13: 'jan'
                    {
                    match("jan"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:21: 'feb'
                    {
                    match("feb"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:29: 'm' ( 'a' | 'ä' | 'ae' ) 'r'
                    {
                    match('m'); 
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:32: ( 'a' | 'ä' | 'ae' )
                    int alt13=3;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0=='a') ) {
                        int LA13_1 = input.LA(2);

                        if ( (LA13_1=='e') ) {
                            alt13=3;
                        }
                        else if ( (LA13_1=='r') ) {
                            alt13=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 13, 1, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA13_0=='\u00E4') ) {
                        alt13=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 0, input);

                        throw nvae;
                    }
                    switch (alt13) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:33: 'a'
                            {
                            match('a'); 

                            }
                            break;
                        case 2 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:37: 'ä'
                            {
                            match('\u00E4'); 

                            }
                            break;
                        case 3 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:41: 'ae'
                            {
                            match("ae"); 


                            }
                            break;

                    }

                    match('r'); 

                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:433:52: 'apr'
                    {
                    match("apr"); 


                    }
                    break;
                case 5 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:13: 'mai'
                    {
                    match("mai"); 


                    }
                    break;
                case 6 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:21: 'jun'
                    {
                    match("jun"); 


                    }
                    break;
                case 7 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:29: 'jul'
                    {
                    match("jul"); 


                    }
                    break;
                case 8 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:434:52: 'aug'
                    {
                    match("aug"); 


                    }
                    break;
                case 9 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:13: 'sep'
                    {
                    match("sep"); 


                    }
                    break;
                case 10 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:21: 'okt'
                    {
                    match("okt"); 


                    }
                    break;
                case 11 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:29: 'nov'
                    {
                    match("nov"); 


                    }
                    break;
                case 12 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:435:52: 'dez'
                    {
                    match("dez"); 


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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:11: ( 'mo' | 'di' | 'mi' | 'do' | 'fr' | 'sa' | 'so' )
            int alt15=7;
            alt15 = dfa15.predict(input);
            switch (alt15) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:13: 'mo'
                    {
                    match("mo"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:20: 'di'
                    {
                    match("di"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:27: 'mi'
                    {
                    match("mi"); 


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:34: 'do'
                    {
                    match("do"); 


                    }
                    break;
                case 5 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:41: 'fr'
                    {
                    match("fr"); 


                    }
                    break;
                case 6 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:48: 'sa'
                    {
                    match("sa"); 


                    }
                    break;
                case 7 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:437:55: 'so'
                    {
                    match("so"); 


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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:11: ( 'eins' | 'zwei' | 'drei' | 'vier' | 'f' ( 'u' | 'ü' | 'ue' ) 'nf' | 'sechs' | 'sieben' | 'acht' | 'neun' | 'zehn' )
            int alt17=10;
            alt17 = dfa17.predict(input);
            switch (alt17) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:13: 'eins'
                    {
                    match("eins"); 


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:23: 'zwei'
                    {
                    match("zwei"); 


                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:34: 'drei'
                    {
                    match("drei"); 


                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:43: 'vier'
                    {
                    match("vier"); 


                    }
                    break;
                case 5 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:52: 'f' ( 'u' | 'ü' | 'ue' ) 'nf'
                    {
                    match('f'); 
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:55: ( 'u' | 'ü' | 'ue' )
                    int alt16=3;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0=='u') ) {
                        int LA16_1 = input.LA(2);

                        if ( (LA16_1=='e') ) {
                            alt16=3;
                        }
                        else if ( (LA16_1=='n') ) {
                            alt16=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 16, 1, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA16_0=='\u00FC') ) {
                        alt16=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 0, input);

                        throw nvae;
                    }
                    switch (alt16) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:56: 'u'
                            {
                            match('u'); 

                            }
                            break;
                        case 2 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:60: 'ü'
                            {
                            match('\u00FC'); 

                            }
                            break;
                        case 3 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:439:64: 'ue'
                            {
                            match("ue"); 


                            }
                            break;

                    }

                    match("nf"); 


                    }
                    break;
                case 6 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:13: 'sechs'
                    {
                    match("sechs"); 


                    }
                    break;
                case 7 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:23: 'sieben'
                    {
                    match("sieben"); 


                    }
                    break;
                case 8 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:34: 'acht'
                    {
                    match("acht"); 


                    }
                    break;
                case 9 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:43: 'neun'
                    {
                    match("neun"); 


                    }
                    break;
                case 10 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:440:52: 'zehn'
                    {
                    match("zehn"); 


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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:442:11: ( '/' | '\\u5E74' | '\\u6708' | '\\u65E5' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:444:11: ( '.' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:444:13: '.'
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:446:11: ( ':' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:446:13: ':'
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:448:11: ( '-' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:448:13: '-'
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:450:11: ( ',' )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:450:13: ','
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:452:11: ( ( '0' .. '9' )+ )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:452:13: ( '0' .. '9' )+
            {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:452:13: ( '0' .. '9' )+
            int cnt18=0;
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>='0' && LA18_0<='9')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:452:13: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt18 >= 1 ) break loop18;
                        EarlyExitException eee =
                            new EarlyExitException(18, input);
                        throw eee;
                }
                cnt18++;
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
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:454:11: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:454:13: ( ' ' | '\\t' | '\\r' | '\\n' )
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
        // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:8: ( A | ON | OF | OF_THE | IN | AND | END | NOW | NEVER | TODAY | TOMORROW | YESTERDAY | NEXT | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY | NUM_STR | DATE_SEP | DOT | COLON | MINUS | COMMA | INT | WS )
        int alt19=27;
        alt19 = dfa19.predict(input);
        switch (alt19) {
            case 1 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:10: A
                {
                mA(); 

                }
                break;
            case 2 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:12: ON
                {
                mON(); 

                }
                break;
            case 3 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:15: OF
                {
                mOF(); 

                }
                break;
            case 4 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:18: OF_THE
                {
                mOF_THE(); 

                }
                break;
            case 5 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:25: IN
                {
                mIN(); 

                }
                break;
            case 6 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:28: AND
                {
                mAND(); 

                }
                break;
            case 7 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:32: END
                {
                mEND(); 

                }
                break;
            case 8 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:36: NOW
                {
                mNOW(); 

                }
                break;
            case 9 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:40: NEVER
                {
                mNEVER(); 

                }
                break;
            case 10 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:46: TODAY
                {
                mTODAY(); 

                }
                break;
            case 11 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:52: TOMORROW
                {
                mTOMORROW(); 

                }
                break;
            case 12 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:61: YESTERDAY
                {
                mYESTERDAY(); 

                }
                break;
            case 13 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:71: NEXT
                {
                mNEXT(); 

                }
                break;
            case 14 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:76: YEARS
                {
                mYEARS(); 

                }
                break;
            case 15 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:82: MONTHS
                {
                mMONTHS(); 

                }
                break;
            case 16 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:89: WEEKS
                {
                mWEEKS(); 

                }
                break;
            case 17 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:95: DAYS
                {
                mDAYS(); 

                }
                break;
            case 18 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:100: MONTH
                {
                mMONTH(); 

                }
                break;
            case 19 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:106: WEEKDAY
                {
                mWEEKDAY(); 

                }
                break;
            case 20 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:114: NUM_STR
                {
                mNUM_STR(); 

                }
                break;
            case 21 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:122: DATE_SEP
                {
                mDATE_SEP(); 

                }
                break;
            case 22 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:131: DOT
                {
                mDOT(); 

                }
                break;
            case 23 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:135: COLON
                {
                mCOLON(); 

                }
                break;
            case 24 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:141: MINUS
                {
                mMINUS(); 

                }
                break;
            case 25 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:147: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 26 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:153: INT
                {
                mINT(); 

                }
                break;
            case 27 :
                // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:1:157: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA14 dfa14 = new DFA14(this);
    protected DFA15 dfa15 = new DFA15(this);
    protected DFA17 dfa17 = new DFA17(this);
    protected DFA19 dfa19 = new DFA19(this);
    static final String DFA14_eotS =
        "\22\uffff";
    static final String DFA14_eofS =
        "\22\uffff";
    static final String DFA14_minS =
        "\2\141\1\uffff\1\141\1\160\5\uffff\1\154\1\145\6\uffff";
    static final String DFA14_maxS =
        "\1\163\1\165\1\uffff\1\u00e4\1\165\5\uffff\1\156\1\162\6\uffff";
    static final String DFA14_acceptS =
        "\2\uffff\1\2\2\uffff\1\11\1\12\1\13\1\14\1\1\2\uffff\1\3\1\4\1"+
        "\10\1\6\1\7\1\5";
    static final String DFA14_specialS =
        "\22\uffff}>";
    static final String[] DFA14_transitionS = {
            "\1\4\2\uffff\1\10\1\uffff\1\2\3\uffff\1\1\2\uffff\1\3\1\7\1"+
            "\6\3\uffff\1\5",
            "\1\11\23\uffff\1\12",
            "",
            "\1\13\u0082\uffff\1\14",
            "\1\15\4\uffff\1\16",
            "",
            "",
            "",
            "",
            "",
            "\1\20\1\uffff\1\17",
            "\1\14\3\uffff\1\21\10\uffff\1\14",
            "",
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
            return "433:1: MONTH : ( 'jan' | 'feb' | 'm' ( 'a' | 'ä' | 'ae' ) 'r' | 'apr' | 'mai' | 'jun' | 'jul' | 'aug' | 'sep' | 'okt' | 'nov' | 'dez' );";
        }
    }
    static final String DFA15_eotS =
        "\13\uffff";
    static final String DFA15_eofS =
        "\13\uffff";
    static final String DFA15_minS =
        "\1\144\2\151\1\uffff\1\141\6\uffff";
    static final String DFA15_maxS =
        "\1\163\2\157\1\uffff\1\157\6\uffff";
    static final String DFA15_acceptS =
        "\3\uffff\1\5\1\uffff\1\1\1\3\1\2\1\4\1\6\1\7";
    static final String DFA15_specialS =
        "\13\uffff}>";
    static final String[] DFA15_transitionS = {
            "\1\2\1\uffff\1\3\6\uffff\1\1\5\uffff\1\4",
            "\1\6\5\uffff\1\5",
            "\1\7\5\uffff\1\10",
            "",
            "\1\11\15\uffff\1\12",
            "",
            "",
            "",
            "",
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
            return "437:1: WEEKDAY : ( 'mo' | 'di' | 'mi' | 'do' | 'fr' | 'sa' | 'so' );";
        }
    }
    static final String DFA17_eotS =
        "\15\uffff";
    static final String DFA17_eofS =
        "\15\uffff";
    static final String DFA17_minS =
        "\1\141\1\uffff\1\145\3\uffff\1\145\6\uffff";
    static final String DFA17_maxS =
        "\1\172\1\uffff\1\167\3\uffff\1\151\6\uffff";
    static final String DFA17_acceptS =
        "\1\uffff\1\1\1\uffff\1\3\1\4\1\5\1\uffff\1\10\1\11\1\2\1\12\1\6"+
        "\1\7";
    static final String DFA17_specialS =
        "\15\uffff}>";
    static final String[] DFA17_transitionS = {
            "\1\7\2\uffff\1\3\1\1\1\5\7\uffff\1\10\4\uffff\1\6\2\uffff\1"+
            "\4\3\uffff\1\2",
            "",
            "\1\12\21\uffff\1\11",
            "",
            "",
            "",
            "\1\13\3\uffff\1\14",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "439:1: NUM_STR : ( 'eins' | 'zwei' | 'drei' | 'vier' | 'f' ( 'u' | 'ü' | 'ue' ) 'nf' | 'sechs' | 'sieben' | 'acht' | 'neun' | 'zehn' );";
        }
    }
    static final String DFA19_eotS =
        "\36\uffff\1\40\7\uffff\1\40\2\uffff\1\53\2\uffff";
    static final String DFA19_eofS =
        "\54\uffff";
    static final String DFA19_minS =
        "\1\11\1\151\1\142\1\151\1\141\2\uffff\2\141\1\uffff\1\141\3\uffff"+
        "\1\145\1\141\11\uffff\1\156\3\uffff\1\155\1\145\3\uffff\1\150\3"+
        "\uffff\1\156\1\uffff\1\143\1\163\2\uffff";
    static final String DFA19_maxS =
        "\1\u6708\1\156\1\165\1\157\1\162\2\uffff\1\165\1\u00e4\1\uffff"+
        "\1\u00e4\3\uffff\1\u00fc\1\157\11\uffff\1\156\3\uffff\1\172\1\145"+
        "\3\uffff\1\156\3\uffff\1\162\1\uffff\1\160\1\163\2\uffff";
    static final String DFA19_acceptS =
        "\5\uffff\1\5\1\6\2\uffff\1\12\1\uffff\1\14\1\20\1\21\2\uffff\1"+
        "\22\1\24\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\uffff\1\7\1\2\1\3"+
        "\2\uffff\1\4\1\23\1\10\1\uffff\1\16\1\11\1\15\1\uffff\1\13\2\uffff"+
        "\1\17\1\1";
    static final String DFA19_specialS =
        "\54\uffff}>";
    static final String[] DFA19_transitionS = {
            "\2\30\2\uffff\1\30\22\uffff\1\30\13\uffff\1\26\1\25\1\23\1"+
            "\22\12\27\1\24\46\uffff\1\2\2\uffff\1\4\1\1\1\16\1\13\1\11\1"+
            "\5\1\7\2\uffff\1\12\1\10\1\20\3\uffff\1\17\1\15\1\6\1\3\1\14"+
            "\2\uffff\1\21\u5df9\uffff\1\22\u0770\uffff\1\22\u0122\uffff"+
            "\1\22",
            "\1\31\4\uffff\1\32",
            "\1\34\1\21\11\uffff\2\33\1\uffff\1\20\4\uffff\1\20",
            "\1\21\5\uffff\1\34",
            "\1\37\3\uffff\1\35\3\uffff\1\36\5\uffff\1\40\2\uffff\1\21",
            "",
            "",
            "\1\42\3\uffff\1\41\2\uffff\1\43\14\uffff\1\20",
            "\1\45\3\uffff\1\21\3\uffff\1\44\5\uffff\1\20\164\uffff\1\45",
            "",
            "\1\20\7\uffff\1\40\5\uffff\1\46\2\uffff\1\47\161\uffff\1\20",
            "",
            "",
            "",
            "\1\20\14\uffff\1\40\2\uffff\1\21\u0086\uffff\1\21",
            "\1\40\3\uffff\1\50\3\uffff\1\21\5\uffff\1\40",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\51",
            "",
            "",
            "",
            "\2\37\3\uffff\1\37\1\34\6\uffff\1\20",
            "\1\37",
            "",
            "",
            "",
            "\1\43\5\uffff\1\20",
            "",
            "",
            "",
            "\1\52\3\uffff\1\47",
            "",
            "\1\21\14\uffff\1\20",
            "\1\21",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( A | ON | OF | OF_THE | IN | AND | END | NOW | NEVER | TODAY | TOMORROW | YESTERDAY | NEXT | YEARS | MONTHS | WEEKS | DAYS | MONTH | WEEKDAY | NUM_STR | DATE_SEP | DOT | COLON | MINUS | COMMA | INT | WS );";
        }
    }
 

}