// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g 2011-10-26 16:59:32

   package dev.drsoran.moloko.grammar.datetime.de;

   import java.util.Calendar;
   
   import dev.drsoran.moloko.util.MolokoCalendar;
   import dev.drsoran.moloko.grammar.datetime.ITimeParser.ParseTimeReturn;
   import dev.drsoran.moloko.grammar.datetime.AbstractTimeParser;
   import dev.drsoran.moloko.grammar.LexerException;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TimeParser extends AbstractTimeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT", "COMMA", "NEVER", "MIDNIGHT", "MIDDAY", "INT", "COLON", "DOT", "AM", "PM", "HOURS", "MINUTES", "SECONDS", "DAYS", "AND", "WS"
    };
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

    // delegates
    // delegators


        public TimeParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public TimeParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return TimeParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g"; }


       public TimeParser()
       {
          super( null );
       }



    // $ANTLR start "parseTime"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:48:1: parseTime[MolokoCalendar cal, boolean adjustDay] returns [ParseTimeReturn result] : ( ( AT | COMMA )? time_point_in_time[$cal] | EOF );
    public final ParseTimeReturn parseTime(MolokoCalendar cal, boolean adjustDay) throws RecognitionException {
        ParseTimeReturn result = null;


              startParsingTime( cal );
           
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:72:4: ( ( AT | COMMA )? time_point_in_time[$cal] | EOF )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( ((LA2_0>=AT && LA2_0<=INT)) ) {
                alt2=1;
            }
            else if ( (LA2_0==EOF) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:72:6: ( AT | COMMA )? time_point_in_time[$cal]
                    {
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:72:6: ( AT | COMMA )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( ((LA1_0>=AT && LA1_0<=COMMA)) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                            {
                            if ( (input.LA(1)>=AT && input.LA(1)<=COMMA) ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }


                            }
                            break;

                    }

                    pushFollow(FOLLOW_time_point_in_time_in_parseTime117);
                    time_point_in_time(cal);

                    state._fsp--;


                          if ( adjustDay && getCalendar().after( cal ) )
                             cal.roll( Calendar.DAY_OF_WEEK, true );
                          
                          cal.setHasTime( true );
                       

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:79:6: EOF
                    {
                    match(input,EOF,FOLLOW_EOF_in_parseTime133); 

                    }
                    break;

            }

                  result = finishedParsingTime( cal );
               
        }
        catch ( RecognitionException e ) {

                  notifyParsingTimeFailed();
                  throw e;
               
        }
        catch ( LexerException e ) {

                  notifyParsingTimeFailed();   
                  throw new RecognitionException();
               
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "parseTime"


    // $ANTLR start "time_point_in_time"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:92:1: time_point_in_time[MolokoCalendar cal] : ( NEVER | MIDNIGHT | MIDDAY | ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )? ) );
    public final void time_point_in_time(MolokoCalendar cal) throws RecognitionException {
        Token v=null;
        int h = 0;

        int m = 0;


        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:93:4: ( NEVER | MIDNIGHT | MIDDAY | ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )? ) )
            int alt5=4;
            switch ( input.LA(1) ) {
            case NEVER:
                {
                alt5=1;
                }
                break;
            case MIDNIGHT:
                {
                alt5=2;
                }
                break;
            case MIDDAY:
                {
                alt5=3;
                }
                break;
            case INT:
                {
                alt5=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:93:6: NEVER
                    {
                    match(input,NEVER,FOLLOW_NEVER_in_time_point_in_time175); 

                          cal.setHasDate( false );
                          cal.setHasTime( false );
                       

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:98:6: MIDNIGHT
                    {
                    match(input,MIDNIGHT,FOLLOW_MIDNIGHT_in_time_point_in_time187); 

                          cal.set( Calendar.HOUR_OF_DAY, 23 );
                          cal.set( Calendar.MINUTE, 59 );
                          cal.set( Calendar.SECOND, 59 );
                       

                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:104:6: MIDDAY
                    {
                    match(input,MIDDAY,FOLLOW_MIDDAY_in_time_point_in_time199); 

                          cal.set( Calendar.HOUR_OF_DAY, 12 );
                          cal.set( Calendar.MINUTE, 00 );
                          cal.set( Calendar.SECOND, 00 );
                       

                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:111:4: ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )? )
                    {
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:111:4: ( (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )? )
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:111:6: (v= INT | h= time_component ( COLON | DOT ) m= time_component ) ( am_pm[$cal] )?
                    {
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:111:6: (v= INT | h= time_component ( COLON | DOT ) m= time_component )
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==INT) ) {
                        int LA3_1 = input.LA(2);

                        if ( (LA3_1==EOF||(LA3_1>=AM && LA3_1<=PM)) ) {
                            alt3=1;
                        }
                        else if ( ((LA3_1>=COLON && LA3_1<=DOT)) ) {
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
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:111:8: v= INT
                            {
                            v=(Token)match(input,INT,FOLLOW_INT_in_time_point_in_time220); 

                                      setCalendarTime( cal, (v!=null?v.getText():null) );
                                   

                            }
                            break;
                        case 2 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:115:8: h= time_component ( COLON | DOT ) m= time_component
                            {
                            pushFollow(FOLLOW_time_component_in_time_point_in_time240);
                            h=time_component();

                            state._fsp--;

                            if ( (input.LA(1)>=COLON && input.LA(1)<=DOT) ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }

                            pushFollow(FOLLOW_time_component_in_time_point_in_time250);
                            m=time_component();

                            state._fsp--;


                                      cal.set( Calendar.HOUR_OF_DAY, h );
                                      cal.set( Calendar.MINUTE, m );
                                      cal.set( Calendar.SECOND, 0 );
                                   

                            }
                            break;

                    }

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:122:6: ( am_pm[$cal] )?
                    int alt4=2;
                    int LA4_0 = input.LA(1);

                    if ( ((LA4_0>=AM && LA4_0<=PM)) ) {
                        alt4=1;
                    }
                    switch (alt4) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:122:6: am_pm[$cal]
                            {
                            pushFollow(FOLLOW_am_pm_in_time_point_in_time273);
                            am_pm(cal);

                            state._fsp--;


                            }
                            break;

                    }


                    }


                    }
                    break;

            }
        }
        catch ( RecognitionException e ) {

                  throw e;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "time_point_in_time"


    // $ANTLR start "am_pm"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:129:1: am_pm[MolokoCalendar cal] : ( AM | PM );
    public final void am_pm(MolokoCalendar cal) throws RecognitionException {
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:130:4: ( AM | PM )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==AM) ) {
                alt6=1;
            }
            else if ( (LA6_0==PM) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:130:6: AM
                    {
                    match(input,AM,FOLLOW_AM_in_am_pm307); 

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:131:6: PM
                    {
                    match(input,PM,FOLLOW_PM_in_am_pm314); 

                          cal.add( Calendar.HOUR_OF_DAY, 12 );
                       

                    }
                    break;

            }
        }
        catch ( RecognitionException e ) {

                  throw e;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "am_pm"


    // $ANTLR start "parseTimeSpec"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:154:1: parseTimeSpec[MolokoCalendar cal, boolean adjustDay] returns [ParseTimeReturn result] : ( ( AT | COMMA )? ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? ) ) ( am_pm[$cal] )? ( COMMA )? | EOF );
    public final ParseTimeReturn parseTimeSpec(MolokoCalendar cal, boolean adjustDay) throws RecognitionException {
        ParseTimeReturn result = null;


              startParsingTime( cal );
              
              cal.set( Calendar.HOUR_OF_DAY, 0 );
              cal.set( Calendar.MINUTE,      0 );
              cal.set( Calendar.SECOND,      0 );
              cal.set( Calendar.MILLISECOND, 0 );
           
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:168:4: ( ( AT | COMMA )? ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? ) ) ( am_pm[$cal] )? ( COMMA )? | EOF )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=AT && LA13_0<=COMMA)||LA13_0==INT) ) {
                alt13=1;
            }
            else if ( (LA13_0==EOF) ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:168:6: ( AT | COMMA )? ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? ) ) ( am_pm[$cal] )? ( COMMA )?
                    {
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:168:6: ( AT | COMMA )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( ((LA7_0>=AT && LA7_0<=COMMA)) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                            {
                            if ( (input.LA(1)>=AT && input.LA(1)<=COMMA) ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }


                            }
                            break;

                    }

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:168:20: ( time_separatorspec[$cal] | ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? ) )
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==INT) ) {
                        int LA10_1 = input.LA(2);

                        if ( (LA10_1==DOT||(LA10_1>=HOURS && LA10_1<=SECONDS)) ) {
                            alt10=2;
                        }
                        else if ( (LA10_1==EOF||LA10_1==COMMA||LA10_1==COLON||(LA10_1>=AM && LA10_1<=PM)) ) {
                            alt10=1;
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
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:168:24: time_separatorspec[$cal]
                            {
                            pushFollow(FOLLOW_time_separatorspec_in_parseTimeSpec394);
                            time_separatorspec(cal);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:169:24: ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
                            {
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:169:24: ( time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )? )
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:169:26: time_naturalspec[$cal] ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
                            {
                            pushFollow(FOLLOW_time_naturalspec_in_parseTimeSpec422);
                            time_naturalspec(cal);

                            state._fsp--;

                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:170:24: ( time_naturalspec[$cal] ( time_naturalspec[$cal] )? )?
                            int alt9=2;
                            int LA9_0 = input.LA(1);

                            if ( (LA9_0==INT) ) {
                                alt9=1;
                            }
                            switch (alt9) {
                                case 1 :
                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:170:26: time_naturalspec[$cal] ( time_naturalspec[$cal] )?
                                    {
                                    pushFollow(FOLLOW_time_naturalspec_in_parseTimeSpec450);
                                    time_naturalspec(cal);

                                    state._fsp--;

                                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:171:26: ( time_naturalspec[$cal] )?
                                    int alt8=2;
                                    int LA8_0 = input.LA(1);

                                    if ( (LA8_0==INT) ) {
                                        alt8=1;
                                    }
                                    switch (alt8) {
                                        case 1 :
                                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:171:26: time_naturalspec[$cal]
                                            {
                                            pushFollow(FOLLOW_time_naturalspec_in_parseTimeSpec478);
                                            time_naturalspec(cal);

                                            state._fsp--;


                                            }
                                            break;

                                    }


                                    }
                                    break;

                            }


                            }


                            }
                            break;

                    }

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:173:7: ( am_pm[$cal] )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( ((LA11_0>=AM && LA11_0<=PM)) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:173:7: am_pm[$cal]
                            {
                            pushFollow(FOLLOW_am_pm_in_parseTimeSpec512);
                            am_pm(cal);

                            state._fsp--;


                            }
                            break;

                    }

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:173:20: ( COMMA )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==COMMA) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:173:20: COMMA
                            {
                            match(input,COMMA,FOLLOW_COMMA_in_parseTimeSpec516); 

                            }
                            break;

                    }


                          if ( adjustDay && getCalendar().after( cal ) )
                             cal.roll( Calendar.DAY_OF_WEEK, true );
                          
                          cal.setHasTime( true );
                       

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:180:6: EOF
                    {
                    match(input,EOF,FOLLOW_EOF_in_parseTimeSpec529); 

                    }
                    break;

            }

                  result = finishedParsingTime( cal );
               
        }
        catch ( RecognitionException e ) {

                  notifyParsingTimeFailed();   
                  throw e;
               
        }
        catch ( LexerException e ) {

                  notifyParsingTimeFailed();   
                  throw new RecognitionException();
               
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "parseTimeSpec"


    // $ANTLR start "time_separatorspec"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:193:1: time_separatorspec[MolokoCalendar cal] : (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? ) ;
    public final void time_separatorspec(MolokoCalendar cal) throws RecognitionException {
        int h = 0;

        int m = 0;

        int s = 0;


        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:194:4: ( (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? ) )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:194:6: (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? )
            {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:194:6: (h= time_component ( COLON m= time_component ( COLON s= time_component )? )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:194:7: h= time_component ( COLON m= time_component ( COLON s= time_component )? )?
            {
            pushFollow(FOLLOW_time_component_in_time_separatorspec571);
            h=time_component();

            state._fsp--;


                     cal.set( Calendar.HOUR_OF_DAY, h );
                  
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:198:7: ( COLON m= time_component ( COLON s= time_component )? )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==COLON) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:198:8: COLON m= time_component ( COLON s= time_component )?
                    {
                    match(input,COLON,FOLLOW_COLON_in_time_separatorspec588); 
                    pushFollow(FOLLOW_time_component_in_time_separatorspec592);
                    m=time_component();

                    state._fsp--;


                              cal.set( Calendar.MINUTE, m );
                           
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:202:8: ( COLON s= time_component )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==COLON) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:202:9: COLON s= time_component
                            {
                            match(input,COLON,FOLLOW_COLON_in_time_separatorspec611); 
                            pushFollow(FOLLOW_time_component_in_time_separatorspec615);
                            s=time_component();

                            state._fsp--;


                                       cal.set( Calendar.SECOND, s );
                                    

                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }

        }
        catch ( RecognitionException e ) {

                  throw e;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "time_separatorspec"


    // $ANTLR start "time_naturalspec"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:213:1: time_naturalspec[MolokoCalendar cal] returns [int seconds] : (fs= hour_floatspec | v= INT ( HOURS | MINUTES | SECONDS ) );
    public final int time_naturalspec(MolokoCalendar cal) throws RecognitionException {
        int seconds = 0;

        Token v=null;
        int fs = 0;



              int calType = -1;
           
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:223:4: (fs= hour_floatspec | v= INT ( HOURS | MINUTES | SECONDS ) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==INT) ) {
                int LA17_1 = input.LA(2);

                if ( (LA17_1==DOT) ) {
                    alt17=1;
                }
                else if ( ((LA17_1>=HOURS && LA17_1<=SECONDS)) ) {
                    alt17=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:223:8: fs= hour_floatspec
                    {
                    pushFollow(FOLLOW_hour_floatspec_in_time_naturalspec695);
                    fs=hour_floatspec();

                    state._fsp--;


                             seconds += fs;
                           

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:227:8: v= INT ( HOURS | MINUTES | SECONDS )
                    {
                    v=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec715); 
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:227:14: ( HOURS | MINUTES | SECONDS )
                    int alt16=3;
                    switch ( input.LA(1) ) {
                    case HOURS:
                        {
                        alt16=1;
                        }
                        break;
                    case MINUTES:
                        {
                        alt16=2;
                        }
                        break;
                    case SECONDS:
                        {
                        alt16=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 0, input);

                        throw nvae;
                    }

                    switch (alt16) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:227:16: HOURS
                            {
                            match(input,HOURS,FOLLOW_HOURS_in_time_naturalspec719); 

                                              calType = Calendar.HOUR_OF_DAY;
                                           

                            }
                            break;
                        case 2 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:231:18: MINUTES
                            {
                            match(input,MINUTES,FOLLOW_MINUTES_in_time_naturalspec755); 

                                              calType = Calendar.MINUTE;
                                           

                            }
                            break;
                        case 3 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:235:18: SECONDS
                            {
                            match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec791); 

                                              calType = Calendar.SECOND;
                                           

                            }
                            break;

                    }


                             int val = Integer.parseInt( (v!=null?v.getText():null) );

                             switch( calType )
                             {
                                case Calendar.HOUR_OF_DAY:
                                   seconds += val * 3600;
                                   break;
                                case Calendar.MINUTE:
                                   seconds += val * 60;
                                   break;
                                case Calendar.SECOND:
                                   seconds += val;
                                   break;
                                default:
                                   throw new RecognitionException();
                             }
                           

                    }
                    break;

            }

                  if ( cal != null )
                     cal.add( Calendar.SECOND, seconds );
               
        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        catch ( RecognitionException e ) {

                  throw e;
               
        }
        finally {
        }
        return seconds;
    }
    // $ANTLR end "time_naturalspec"


    // $ANTLR start "hour_floatspec"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:268:1: hour_floatspec returns [int seconds] : h= INT DOT deciHour= INT HOURS ;
    public final int hour_floatspec() throws RecognitionException {
        int seconds = 0;

        Token h=null;
        Token deciHour=null;

        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:269:4: (h= INT DOT deciHour= INT HOURS )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:269:6: h= INT DOT deciHour= INT HOURS
            {
            h=(Token)match(input,INT,FOLLOW_INT_in_hour_floatspec875); 
            match(input,DOT,FOLLOW_DOT_in_hour_floatspec877); 
            deciHour=(Token)match(input,INT,FOLLOW_INT_in_hour_floatspec881); 
            match(input,HOURS,FOLLOW_HOURS_in_hour_floatspec883); 

                  seconds = Integer.parseInt( (h!=null?h.getText():null) ) * 3600;
                  seconds += Integer.parseInt( (deciHour!=null?deciHour.getText():null) ) * 360;
               

            }

        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        catch ( RecognitionException e ) {

                  throw e;
               
        }
        finally {
        }
        return seconds;
    }
    // $ANTLR end "hour_floatspec"


    // $ANTLR start "parseTimeEstimate"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:290:1: parseTimeEstimate returns [long span] : ( (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )* | EOF );
    public final long parseTimeEstimate() throws RecognitionException {
        long span = 0;

        Token d=null;
        int ts = 0;


        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:295:4: ( (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )* | EOF )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==COMMA||LA21_0==INT||LA21_0==AND) ) {
                alt21=1;
            }
            else if ( (LA21_0==EOF) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:295:6: (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+ ( COMMA | AND )*
                    {
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:295:6: (d= INT DAYS | ( COMMA | AND )? ts= time_naturalspec[null] )+
                    int cnt19=0;
                    loop19:
                    do {
                        int alt19=3;
                        int LA19_0 = input.LA(1);

                        if ( (LA19_0==COMMA||LA19_0==AND) ) {
                            int LA19_1 = input.LA(2);

                            if ( (LA19_1==INT) ) {
                                alt19=2;
                            }


                        }
                        else if ( (LA19_0==INT) ) {
                            int LA19_3 = input.LA(2);

                            if ( (LA19_3==DAYS) ) {
                                alt19=1;
                            }
                            else if ( (LA19_3==DOT||(LA19_3>=HOURS && LA19_3<=SECONDS)) ) {
                                alt19=2;
                            }


                        }


                        switch (alt19) {
                    	case 1 :
                    	    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:295:10: d= INT DAYS
                    	    {
                    	    d=(Token)match(input,INT,FOLLOW_INT_in_parseTimeEstimate948); 
                    	    match(input,DAYS,FOLLOW_DAYS_in_parseTimeEstimate950); 

                    	                span += Integer.parseInt( (d!=null?d.getText():null) ) * 3600 * 24;
                    	             

                    	    }
                    	    break;
                    	case 2 :
                    	    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:299:10: ( COMMA | AND )? ts= time_naturalspec[null]
                    	    {
                    	    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:299:10: ( COMMA | AND )?
                    	    int alt18=2;
                    	    int LA18_0 = input.LA(1);

                    	    if ( (LA18_0==COMMA||LA18_0==AND) ) {
                    	        alt18=1;
                    	    }
                    	    switch (alt18) {
                    	        case 1 :
                    	            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                    	            {
                    	            if ( input.LA(1)==COMMA||input.LA(1)==AND ) {
                    	                input.consume();
                    	                state.errorRecovery=false;
                    	            }
                    	            else {
                    	                MismatchedSetException mse = new MismatchedSetException(null,input);
                    	                throw mse;
                    	            }


                    	            }
                    	            break;

                    	    }

                    	    pushFollow(FOLLOW_time_naturalspec_in_parseTimeEstimate983);
                    	    ts=time_naturalspec(null);

                    	    state._fsp--;


                    	             span += ts;
                    	           

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt19 >= 1 ) break loop19;
                                EarlyExitException eee =
                                    new EarlyExitException(19, input);
                                throw eee;
                        }
                        cnt19++;
                    } while (true);

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:303:9: ( COMMA | AND )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==COMMA||LA20_0==AND) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:
                    	    {
                    	    if ( input.LA(1)==COMMA||input.LA(1)==AND ) {
                    	        input.consume();
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:304:6: EOF
                    {
                    match(input,EOF,FOLLOW_EOF_in_parseTimeEstimate1017); 

                    }
                    break;

            }

                  span *= 1000;
               
        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        catch ( RecognitionException e ) {

                  throw e;
               
        }
        catch ( LexerException e ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return span;
    }
    // $ANTLR end "parseTimeEstimate"


    // $ANTLR start "time_component"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:319:1: time_component returns [int value] : c= INT ;
    public final int time_component() throws RecognitionException {
        int value = 0;

        Token c=null;

        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:320:4: (c= INT )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Time.g:320:6: c= INT
            {
            c=(Token)match(input,INT,FOLLOW_INT_in_time_component1073); 

                  String comp = (c!=null?c.getText():null);

                  if ( comp.length() > 2 )
                     comp = comp.substring( 0, 2 );

                  value = Integer.parseInt( comp );
               

            }

        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return value;
    }
    // $ANTLR end "time_component"

    // Delegated rules


 

    public static final BitSet FOLLOW_set_in_parseTime108 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_time_point_in_time_in_parseTime117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_parseTime133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEVER_in_time_point_in_time175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIDNIGHT_in_time_point_in_time187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIDDAY_in_time_point_in_time199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_point_in_time220 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_time_component_in_time_point_in_time240 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_set_in_time_point_in_time242 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_time_component_in_time_point_in_time250 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_am_pm_in_time_point_in_time273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AM_in_am_pm307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PM_in_am_pm314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_parseTimeSpec381 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_time_separatorspec_in_parseTimeSpec394 = new BitSet(new long[]{0x0000000000003022L});
    public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec422 = new BitSet(new long[]{0x00000000000033E2L});
    public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec450 = new BitSet(new long[]{0x00000000000033E2L});
    public static final BitSet FOLLOW_time_naturalspec_in_parseTimeSpec478 = new BitSet(new long[]{0x0000000000003022L});
    public static final BitSet FOLLOW_am_pm_in_parseTimeSpec512 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COMMA_in_parseTimeSpec516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_parseTimeSpec529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_component_in_time_separatorspec571 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COLON_in_time_separatorspec588 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_time_component_in_time_separatorspec592 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COLON_in_time_separatorspec611 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_time_component_in_time_separatorspec615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hour_floatspec_in_time_naturalspec695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec715 = new BitSet(new long[]{0x000000000001C000L});
    public static final BitSet FOLLOW_HOURS_in_time_naturalspec719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUTES_in_time_naturalspec755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_hour_floatspec875 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_DOT_in_hour_floatspec877 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_INT_in_hour_floatspec881 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_HOURS_in_hour_floatspec883 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_parseTimeEstimate948 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_DAYS_in_parseTimeEstimate950 = new BitSet(new long[]{0x00000000000403E2L});
    public static final BitSet FOLLOW_set_in_parseTimeEstimate972 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_time_naturalspec_in_parseTimeEstimate983 = new BitSet(new long[]{0x00000000000403E2L});
    public static final BitSet FOLLOW_set_in_parseTimeEstimate1003 = new BitSet(new long[]{0x0000000000040022L});
    public static final BitSet FOLLOW_EOF_in_parseTimeEstimate1017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_component1073 = new BitSet(new long[]{0x0000000000000002L});

}