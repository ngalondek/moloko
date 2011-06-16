// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g 2011-06-16 09:44:10

   package dev.drsoran.moloko.grammar.de;

   import java.util.Map;
   import java.util.Locale;
   import java.util.Set;

   import dev.drsoran.moloko.grammar.AbstractRecurrenceParser;
   import dev.drsoran.moloko.grammar.RecurrencePatternParser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class RecurrenceParser extends AbstractRecurrenceParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "EVERY", "AFTER", "DAYS", "WEEKS", "ON", "MONTHS", "YEARS", "UNTIL", "FOR", "INT", "AND", "COMMA", "LAST", "DOT", "FIRST", "SECOND", "OTHER", "THIRD", "FOURTH", "FIFTH", "NUM_ONE", "NUM_TWO", "NUM_THREE", "NUM_FOUR", "NUM_FIVE", "NUM_SIX", "NUM_SEVEN", "NUM_EIGHT", "NUM_NINE", "NUM_TEN", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY", "WEEKEND", "WEEKDAY_LIT", "MONTH", "STRING", "TIMES", "MINUS", "NUMBER", "WS"
    };
    public static final int EOF=-1;
    public static final int EVERY=4;
    public static final int AFTER=5;
    public static final int DAYS=6;
    public static final int WEEKS=7;
    public static final int ON=8;
    public static final int MONTHS=9;
    public static final int YEARS=10;
    public static final int UNTIL=11;
    public static final int FOR=12;
    public static final int INT=13;
    public static final int AND=14;
    public static final int COMMA=15;
    public static final int LAST=16;
    public static final int DOT=17;
    public static final int FIRST=18;
    public static final int SECOND=19;
    public static final int OTHER=20;
    public static final int THIRD=21;
    public static final int FOURTH=22;
    public static final int FIFTH=23;
    public static final int NUM_ONE=24;
    public static final int NUM_TWO=25;
    public static final int NUM_THREE=26;
    public static final int NUM_FOUR=27;
    public static final int NUM_FIVE=28;
    public static final int NUM_SIX=29;
    public static final int NUM_SEVEN=30;
    public static final int NUM_EIGHT=31;
    public static final int NUM_NINE=32;
    public static final int NUM_TEN=33;
    public static final int MONDAY=34;
    public static final int TUESDAY=35;
    public static final int WEDNESDAY=36;
    public static final int THURSDAY=37;
    public static final int FRIDAY=38;
    public static final int SATURDAY=39;
    public static final int SUNDAY=40;
    public static final int WEEKEND=41;
    public static final int WEEKDAY_LIT=42;
    public static final int MONTH=43;
    public static final int STRING=44;
    public static final int TIMES=45;
    public static final int MINUS=46;
    public static final int NUMBER=47;
    public static final int WS=48;

    // delegates
    // delegators


        public RecurrenceParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public RecurrenceParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return RecurrenceParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g"; }


       public RecurrenceParser()
       {
          super( null );
       }

       private final static Locale LOCALE = Locale.GERMAN;
       
       protected String getUntilLiteral()
       {
          return "bis";
       }



    // $ANTLR start "parseRecurrence"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:44:1: parseRecurrence returns [Map< String, Object > res] : ( ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )? | EOF );
    public final Map< String, Object > parseRecurrence() throws RecognitionException {
        Map< String, Object > res = null;

        Token until=null;
        Token count=null;
        RecurrenceParser.recurr_Monthly_return r = null;

        int m = 0;

        int firstEntry = 0;



              res = startParseRecurrence();
           
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:53:4: ( ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )? | EOF )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=EVERY && LA14_0<=WEEKS)||(LA14_0>=MONTHS && LA14_0<=YEARS)||LA14_0==INT||(LA14_0>=FIRST && LA14_0<=WEEKDAY_LIT)) ) {
                alt14=1;
            }
            else if ( (LA14_0==EOF) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:53:6: ( EVERY | AFTER )? ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )?
                    {
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:53:6: ( EVERY | AFTER )?
                    int alt1=3;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==EVERY) ) {
                        alt1=1;
                    }
                    else if ( (LA1_0==AFTER) ) {
                        alt1=2;
                    }
                    switch (alt1) {
                        case 1 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:53:7: EVERY
                            {
                            match(input,EVERY,FOLLOW_EVERY_in_parseRecurrence84); 
                             isEvery = Boolean.TRUE; 

                            }
                            break;
                        case 2 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:53:43: AFTER
                            {
                            match(input,AFTER,FOLLOW_AFTER_in_parseRecurrence90); 

                            }
                            break;

                    }

                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:54:6: ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )
                    int alt12=4;
                    alt12 = dfa12.predict(input);
                    switch (alt12) {
                        case 1 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:55:9: ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? )
                            {
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:55:9: ( parse_Interval_Number_or_Text )?
                            int alt2=2;
                            int LA2_0 = input.LA(1);

                            if ( (LA2_0==INT||(LA2_0>=NUM_ONE && LA2_0<=NUM_TEN)) ) {
                                alt2=1;
                            }
                            switch (alt2) {
                                case 1 :
                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:55:9: parse_Interval_Number_or_Text
                                    {
                                    pushFollow(FOLLOW_parse_Interval_Number_or_Text_in_parseRecurrence109);
                                    parse_Interval_Number_or_Text();

                                    state._fsp--;


                                    }
                                    break;

                            }

                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:56:9: ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? )
                            int alt11=4;
                            switch ( input.LA(1) ) {
                            case DAYS:
                                {
                                alt11=1;
                                }
                                break;
                            case WEEKS:
                                {
                                alt11=2;
                                }
                                break;
                            case MONTHS:
                                {
                                alt11=3;
                                }
                                break;
                            case YEARS:
                                {
                                alt11=4;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 11, 0, input);

                                throw nvae;
                            }

                            switch (alt11) {
                                case 1 :
                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:57:13: DAYS
                                    {
                                    match(input,DAYS,FOLLOW_DAYS_in_parseRecurrence134); 
                                     freq = RecurrencePatternParser.VAL_DAILY_LIT;  

                                    }
                                    break;
                                case 2 :
                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:58:13: WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )?
                                    {
                                    match(input,WEEKS,FOLLOW_WEEKS_in_parseRecurrence196); 
                                     freq = RecurrencePatternParser.VAL_WEEKLY_LIT; 
                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:59:14: ( ( ON )? recurr_WD[weekdays, \"\"] )?
                                    int alt4=2;
                                    int LA4_0 = input.LA(1);

                                    if ( (LA4_0==ON||(LA4_0>=MONDAY && LA4_0<=WEEKDAY_LIT)) ) {
                                        alt4=1;
                                    }
                                    switch (alt4) {
                                        case 1 :
                                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:59:15: ( ON )? recurr_WD[weekdays, \"\"]
                                            {
                                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:59:15: ( ON )?
                                            int alt3=2;
                                            int LA3_0 = input.LA(1);

                                            if ( (LA3_0==ON) ) {
                                                alt3=1;
                                            }
                                            switch (alt3) {
                                                case 1 :
                                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:59:15: ON
                                                    {
                                                    match(input,ON,FOLLOW_ON_in_parseRecurrence259); 

                                                    }
                                                    break;

                                            }

                                            pushFollow(FOLLOW_recurr_WD_in_parseRecurrence263);
                                            recurr_WD(weekdays, "");

                                            state._fsp--;


                                                                  resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
                                                                  resolutionVal = join( ",", weekdays );
                                                               

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 3 :
                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:65:13: MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )?
                                    {
                                    match(input,MONTHS,FOLLOW_MONTHS_in_parseRecurrence315); 
                                     freq = RecurrencePatternParser.VAL_MONTHLY_LIT;
                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:66:14: ( ( ON )? r= recurr_Monthly[weekdays, ints] )?
                                    int alt6=2;
                                    int LA6_0 = input.LA(1);

                                    if ( (LA6_0==ON||LA6_0==INT||(LA6_0>=FIRST && LA6_0<=FIFTH)) ) {
                                        alt6=1;
                                    }
                                    switch (alt6) {
                                        case 1 :
                                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:66:15: ( ON )? r= recurr_Monthly[weekdays, ints]
                                            {
                                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:66:15: ( ON )?
                                            int alt5=2;
                                            int LA5_0 = input.LA(1);

                                            if ( (LA5_0==ON) ) {
                                                alt5=1;
                                            }
                                            switch (alt5) {
                                                case 1 :
                                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:66:15: ON
                                                    {
                                                    match(input,ON,FOLLOW_ON_in_parseRecurrence377); 

                                                    }
                                                    break;

                                            }

                                            pushFollow(FOLLOW_recurr_Monthly_in_parseRecurrence383);
                                            r=recurr_Monthly(weekdays, ints);

                                            state._fsp--;


                                                                  freq          = r.freq;
                                                                  interval      = r.interval;
                                                                  resolution    = r.resolution;
                                                                  resolutionVal = r.resolutionVal;
                                                               

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 4 :
                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:74:13: YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )?
                                    {
                                    match(input,YEARS,FOLLOW_YEARS_in_parseRecurrence435); 
                                     freq = RecurrencePatternParser.VAL_YEARLY_LIT; 
                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:75:14: ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )?
                                    int alt10=2;
                                    int LA10_0 = input.LA(1);

                                    if ( (LA10_0==ON||LA10_0==INT||(LA10_0>=FIRST && LA10_0<=FIFTH)) ) {
                                        alt10=1;
                                    }
                                    switch (alt10) {
                                        case 1 :
                                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:75:15: ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )?
                                            {
                                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:75:15: ( ON )?
                                            int alt7=2;
                                            int LA7_0 = input.LA(1);

                                            if ( (LA7_0==ON) ) {
                                                alt7=1;
                                            }
                                            switch (alt7) {
                                                case 1 :
                                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:75:15: ON
                                                    {
                                                    match(input,ON,FOLLOW_ON_in_parseRecurrence498); 

                                                    }
                                                    break;

                                            }

                                            pushFollow(FOLLOW_recurr_Monthly_in_parseRecurrence504);
                                            r=recurr_Monthly(weekdays, ints);

                                            state._fsp--;


                                                                  freq          = r.freq;
                                                                  interval      = r.interval;
                                                                  resolution    = r.resolution;
                                                                  resolutionVal = r.resolutionVal;
                                                               
                                            if ( !(( r.hasWD )) ) {
                                                throw new FailedPredicateException(input, "parseRecurrence", " r.hasWD ");
                                            }
                                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:82:35: ( ( ON )? m= parse_Month )?
                                            int alt9=2;
                                            int LA9_0 = input.LA(1);

                                            if ( (LA9_0==ON||LA9_0==MONTH) ) {
                                                alt9=1;
                                            }
                                            switch (alt9) {
                                                case 1 :
                                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:82:37: ( ON )? m= parse_Month
                                                    {
                                                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:82:37: ( ON )?
                                                    int alt8=2;
                                                    int LA8_0 = input.LA(1);

                                                    if ( (LA8_0==ON) ) {
                                                        alt8=1;
                                                    }
                                                    switch (alt8) {
                                                        case 1 :
                                                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:82:38: ON
                                                            {
                                                            match(input,ON,FOLLOW_ON_in_parseRecurrence553); 

                                                            }
                                                            break;

                                                    }

                                                    pushFollow(FOLLOW_parse_Month_in_parseRecurrence559);
                                                    m=parse_Month();

                                                    state._fsp--;


                                                                                                 freq     = RecurrencePatternParser.VAL_YEARLY_LIT;
                                                                                                 interval = 1;
                                                                                                 res.put( RecurrencePatternParser.OP_BYMONTH_LIT, Integer.toString( m ) );
                                                                                              

                                                    }
                                                    break;

                                            }


                                            }
                                            break;

                                    }


                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:91:6: recurr_Xst[ints]
                            {
                            pushFollow(FOLLOW_recurr_Xst_in_parseRecurrence670);
                            recurr_Xst(ints);

                            state._fsp--;


                                    freq          = RecurrencePatternParser.VAL_MONTHLY_LIT;
                                    interval      = 1;
                                    resolution    = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
                                    resolutionVal = join( ",", ints );
                                 

                            }
                            break;
                        case 3 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:98:6: recurr_WD[weekdays, \"\"]
                            {
                            pushFollow(FOLLOW_recurr_WD_in_parseRecurrence685);
                            recurr_WD(weekdays, "");

                            state._fsp--;


                                    freq          = RecurrencePatternParser.VAL_WEEKLY_LIT;
                                    interval      = 1;
                                    resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
                                    resolutionVal = join( ",", weekdays );
                                 

                            }
                            break;
                        case 4 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:105:6: (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                            {
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:105:6: (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:106:9: firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"]
                            {
                            pushFollow(FOLLOW_recurr_Xst_in_parseRecurrence712);
                            firstEntry=recurr_Xst(ints);

                            state._fsp--;

                            pushFollow(FOLLOW_recurr_WD_in_parseRecurrence715);
                            recurr_WD(weekdays, "");

                            state._fsp--;


                                       freq          = RecurrencePatternParser.VAL_WEEKLY_LIT;
                                       interval      = firstEntry;
                                       resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
                                       resolutionVal = join( ",", weekdays );
                                    

                            }


                            }
                            break;

                    }

                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:115:4: (until= UNTIL | FOR count= INT )?
                    int alt13=3;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==UNTIL) ) {
                        alt13=1;
                    }
                    else if ( (LA13_0==FOR) ) {
                        alt13=2;
                    }
                    switch (alt13) {
                        case 1 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:116:9: until= UNTIL
                            {
                            until=(Token)match(input,UNTIL,FOLLOW_UNTIL_in_parseRecurrence755); 

                                       setUntil( (until!=null?until.getText():null).replaceFirst( "bis\\s*", "" ) );
                                    

                            }
                            break;
                        case 2 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:120:9: FOR count= INT
                            {
                            match(input,FOR,FOLLOW_FOR_in_parseRecurrence775); 
                            count=(Token)match(input,INT,FOLLOW_INT_in_parseRecurrence779); 

                                       res.put( RecurrencePatternParser.OP_COUNT_LIT, Integer.parseInt( (count!=null?count.getText():null) ) );
                                    

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:125:6: EOF
                    {
                    match(input,EOF,FOLLOW_EOF_in_parseRecurrence802); 

                    }
                    break;

            }

                  res = finishedParseRecurrence();
               
        }
        catch ( RecognitionException e ) {

                  throw e;
               
        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return res;
    }
    // $ANTLR end "parseRecurrence"


    // $ANTLR start "recurr_Xst"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:136:1: recurr_Xst[Set< Integer > res] returns [int firstEntry] : x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? ;
    public final int recurr_Xst(Set< Integer > res) throws RecognitionException {
        int firstEntry = 0;

        int x = 0;


        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:137:4: (x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:137:6: x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )?
            {
            pushFollow(FOLLOW_parse_Xst_in_recurr_Xst849);
            x=parse_Xst();

            state._fsp--;

             res.add( x ); firstEntry = x; 
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:138:4: ( ( ( AND | COMMA ) x= parse_Xst )+ )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0>=AND && LA16_0<=COMMA)) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:138:5: ( ( AND | COMMA ) x= parse_Xst )+
                    {
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:138:5: ( ( AND | COMMA ) x= parse_Xst )+
                    int cnt15=0;
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0>=AND && LA15_0<=COMMA)) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:138:6: ( AND | COMMA ) x= parse_Xst
                    	    {
                    	    if ( (input.LA(1)>=AND && input.LA(1)<=COMMA) ) {
                    	        input.consume();
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }

                    	    pushFollow(FOLLOW_parse_Xst_in_recurr_Xst882);
                    	    x=parse_Xst();

                    	    state._fsp--;

                    	     res.add( x ); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt15 >= 1 ) break loop15;
                                EarlyExitException eee =
                                    new EarlyExitException(15, input);
                                throw eee;
                        }
                        cnt15++;
                    } while (true);


                    }
                    break;

            }


            }

        }
        catch ( RecognitionException e ) {

                  throw e;
               
        }
        finally {
        }
        return firstEntry;
    }
    // $ANTLR end "recurr_Xst"


    // $ANTLR start "recurr_WD"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:145:1: recurr_WD[Set< String > weekdays, String Xst] : parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? ;
    public final void recurr_WD(Set< String > weekdays, String Xst) throws RecognitionException {
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:146:4: ( parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:146:6: parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
            {
            pushFollow(FOLLOW_parse_Weekday_in_recurr_WD917);
            parse_Weekday(weekdays, Xst, true);

            state._fsp--;

            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:147:4: ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=AND && LA18_0<=COMMA)) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:147:5: ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
                    {
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:147:5: ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
                    int cnt17=0;
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( ((LA17_0>=AND && LA17_0<=COMMA)) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:147:6: ( AND | COMMA ) parse_Weekday[weekdays, Xst, true]
                    	    {
                    	    if ( (input.LA(1)>=AND && input.LA(1)<=COMMA) ) {
                    	        input.consume();
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }

                    	    pushFollow(FOLLOW_parse_Weekday_in_recurr_WD947);
                    	    parse_Weekday(weekdays, Xst, true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt17 >= 1 ) break loop17;
                                EarlyExitException eee =
                                    new EarlyExitException(17, input);
                                throw eee;
                        }
                        cnt17++;
                    } while (true);


                    }
                    break;

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
    // $ANTLR end "recurr_WD"

    public static class recurr_Monthly_return extends ParserRuleReturnScope {
        public String  freq;
        public String  resolution;
        public String  resolutionVal;
        public int     interval;
        public boolean hasWD;
    };

    // $ANTLR start "recurr_Monthly"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:154:1: recurr_Monthly[Set< String > weekdays,\r\n Set< Integer > ints ] returns [String freq,\r\n String resolution,\r\n String resolutionVal,\r\n int interval,\r\n boolean hasWD] : firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? ;
    public final RecurrenceParser.recurr_Monthly_return recurr_Monthly(Set< String >  weekdays, Set< Integer > ints) throws RecognitionException {
        RecurrenceParser.recurr_Monthly_return retval = new RecurrenceParser.recurr_Monthly_return();
        retval.start = input.LT(1);

        int firstEntry = 0;



              retval.freq     = RecurrencePatternParser.VAL_MONTHLY_LIT;
              retval.interval = 1;
           
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:165:4: (firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:165:6: firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            {
            pushFollow(FOLLOW_recurr_Xst_in_recurr_Monthly1000);
            firstEntry=recurr_Xst(ints);

            state._fsp--;


                    retval.resolution    = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
                    retval.resolutionVal = join( ",", ints );
                 
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:169:8: ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==LAST||(LA20_0>=MONDAY && LA20_0<=WEEKDAY_LIT)) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:170:11: ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )]
                    {
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:170:11: ( LAST )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==LAST) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:171:14: LAST
                            {
                            match(input,LAST,FOLLOW_LAST_in_recurr_Monthly1037); 

                                            firstEntry = -firstEntry;
                                         

                            }
                            break;

                    }

                    pushFollow(FOLLOW_recurr_WD_in_recurr_Monthly1077);
                    recurr_WD(weekdays, Integer.toString( firstEntry ));

                    state._fsp--;


                                 retval.hasWD         = true;
                                 retval.resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
                                 retval.resolutionVal = join( ",", weekdays );
                              

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

        }
        catch ( RecognitionException e ) {

                  throw e;
               
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "recurr_Monthly"


    // $ANTLR start "parse_Xst"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:189:1: parse_Xst returns [int number] : (n= INT ( DOT )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH );
    public final int parse_Xst() throws RecognitionException {
        int number = 0;

        Token n=null;

        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:190:4: (n= INT ( DOT )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH )
            int alt22=6;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt22=1;
                }
                break;
            case FIRST:
                {
                alt22=2;
                }
                break;
            case SECOND:
            case OTHER:
                {
                alt22=3;
                }
                break;
            case THIRD:
                {
                alt22=4;
                }
                break;
            case FOURTH:
                {
                alt22=5;
                }
                break;
            case FIFTH:
                {
                alt22=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }

            switch (alt22) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:190:6: n= INT ( DOT )?
                    {
                    n=(Token)match(input,INT,FOLLOW_INT_in_parse_Xst1133); 
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:190:12: ( DOT )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==DOT) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:190:13: DOT
                            {
                            match(input,DOT,FOLLOW_DOT_in_parse_Xst1136); 

                            }
                            break;

                    }


                          number = limitMonthDay( Integer.parseInt( (n!=null?n.getText():null) ) );
                       

                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:194:6: FIRST
                    {
                    match(input,FIRST,FOLLOW_FIRST_in_parse_Xst1150); 
                     number = 1; 

                    }
                    break;
                case 3 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:195:6: ( SECOND | OTHER )
                    {
                    if ( (input.LA(1)>=SECOND && input.LA(1)<=OTHER) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                     number = 2; 

                    }
                    break;
                case 4 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:196:6: THIRD
                    {
                    match(input,THIRD,FOLLOW_THIRD_in_parse_Xst1185); 
                     number = 3; 

                    }
                    break;
                case 5 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:197:6: FOURTH
                    {
                    match(input,FOURTH,FOLLOW_FOURTH_in_parse_Xst1205); 
                     number = 4; 

                    }
                    break;
                case 6 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:198:6: FIFTH
                    {
                    match(input,FIFTH,FOLLOW_FIFTH_in_parse_Xst1224); 
                     number = 5; 

                    }
                    break;

            }
        }
        catch ( RecognitionException e ) {

                  return 1;
               
        }
        catch ( NumberFormatException nfe ) {

                  return 1;
               
        }
        finally {
        }
        return number;
    }
    // $ANTLR end "parse_Xst"


    // $ANTLR start "parse_Interval_Number_or_Text"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:209:1: parse_Interval_Number_or_Text : (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN );
    public final void parse_Interval_Number_or_Text() throws RecognitionException {
        Token n=null;

        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:210:4: (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN )
            int alt23=11;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt23=1;
                }
                break;
            case NUM_ONE:
                {
                alt23=2;
                }
                break;
            case NUM_TWO:
                {
                alt23=3;
                }
                break;
            case NUM_THREE:
                {
                alt23=4;
                }
                break;
            case NUM_FOUR:
                {
                alt23=5;
                }
                break;
            case NUM_FIVE:
                {
                alt23=6;
                }
                break;
            case NUM_SIX:
                {
                alt23=7;
                }
                break;
            case NUM_SEVEN:
                {
                alt23=8;
                }
                break;
            case NUM_EIGHT:
                {
                alt23=9;
                }
                break;
            case NUM_NINE:
                {
                alt23=10;
                }
                break;
            case NUM_TEN:
                {
                alt23=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:210:6: n= INT
                    {
                    n=(Token)match(input,INT,FOLLOW_INT_in_parse_Interval_Number_or_Text1278); 
                     interval = Integer.parseInt( (n!=null?n.getText():null) ); 

                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:211:6: NUM_ONE
                    {
                    match(input,NUM_ONE,FOLLOW_NUM_ONE_in_parse_Interval_Number_or_Text1292); 
                     interval = 1; 

                    }
                    break;
                case 3 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:212:6: NUM_TWO
                    {
                    match(input,NUM_TWO,FOLLOW_NUM_TWO_in_parse_Interval_Number_or_Text1304); 
                     interval = 2; 

                    }
                    break;
                case 4 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:213:6: NUM_THREE
                    {
                    match(input,NUM_THREE,FOLLOW_NUM_THREE_in_parse_Interval_Number_or_Text1316); 
                     interval = 3; 

                    }
                    break;
                case 5 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:214:6: NUM_FOUR
                    {
                    match(input,NUM_FOUR,FOLLOW_NUM_FOUR_in_parse_Interval_Number_or_Text1326); 
                     interval = 4; 

                    }
                    break;
                case 6 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:215:6: NUM_FIVE
                    {
                    match(input,NUM_FIVE,FOLLOW_NUM_FIVE_in_parse_Interval_Number_or_Text1337); 
                     interval = 5; 

                    }
                    break;
                case 7 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:216:6: NUM_SIX
                    {
                    match(input,NUM_SIX,FOLLOW_NUM_SIX_in_parse_Interval_Number_or_Text1348); 
                     interval = 6; 

                    }
                    break;
                case 8 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:217:6: NUM_SEVEN
                    {
                    match(input,NUM_SEVEN,FOLLOW_NUM_SEVEN_in_parse_Interval_Number_or_Text1360); 
                     interval = 7; 

                    }
                    break;
                case 9 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:218:6: NUM_EIGHT
                    {
                    match(input,NUM_EIGHT,FOLLOW_NUM_EIGHT_in_parse_Interval_Number_or_Text1370); 
                     interval = 8; 

                    }
                    break;
                case 10 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:219:6: NUM_NINE
                    {
                    match(input,NUM_NINE,FOLLOW_NUM_NINE_in_parse_Interval_Number_or_Text1380); 
                     interval = 9; 

                    }
                    break;
                case 11 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:220:6: NUM_TEN
                    {
                    match(input,NUM_TEN,FOLLOW_NUM_TEN_in_parse_Interval_Number_or_Text1391); 
                     interval = 10; 

                    }
                    break;

            }
        }
        catch ( RecognitionException e ) {

                  interval = 1;
               
        }
        catch ( NumberFormatException nfe ) {

                  interval = 1;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "parse_Interval_Number_or_Text"


    // $ANTLR start "parse_Weekday"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:231:1: parse_Weekday[Set< String > weekdays, String Xst, boolean strict] returns [String weekday] : ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT );
    public final String parse_Weekday(Set< String > weekdays, String Xst, boolean strict) throws RecognitionException {
        String weekday = null;

        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:232:4: ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT )
            int alt24=9;
            switch ( input.LA(1) ) {
            case MONDAY:
                {
                alt24=1;
                }
                break;
            case TUESDAY:
                {
                alt24=2;
                }
                break;
            case WEDNESDAY:
                {
                alt24=3;
                }
                break;
            case THURSDAY:
                {
                alt24=4;
                }
                break;
            case FRIDAY:
                {
                alt24=5;
                }
                break;
            case SATURDAY:
                {
                alt24=6;
                }
                break;
            case SUNDAY:
                {
                alt24=7;
                }
                break;
            case WEEKEND:
                {
                alt24=8;
                }
                break;
            case WEEKDAY_LIT:
                {
                alt24=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:232:6: MONDAY
                    {
                    match(input,MONDAY,FOLLOW_MONDAY_in_parse_Weekday1441); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_MON ); 

                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:233:6: TUESDAY
                    {
                    match(input,TUESDAY,FOLLOW_TUESDAY_in_parse_Weekday1455); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_TUE ); 

                    }
                    break;
                case 3 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:234:6: WEDNESDAY
                    {
                    match(input,WEDNESDAY,FOLLOW_WEDNESDAY_in_parse_Weekday1468); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_WED ); 

                    }
                    break;
                case 4 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:235:6: THURSDAY
                    {
                    match(input,THURSDAY,FOLLOW_THURSDAY_in_parse_Weekday1479); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_THU ); 

                    }
                    break;
                case 5 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:236:6: FRIDAY
                    {
                    match(input,FRIDAY,FOLLOW_FRIDAY_in_parse_Weekday1491); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_FRI ); 

                    }
                    break;
                case 6 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:237:6: SATURDAY
                    {
                    match(input,SATURDAY,FOLLOW_SATURDAY_in_parse_Weekday1505); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT ); 

                    }
                    break;
                case 7 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:238:6: SUNDAY
                    {
                    match(input,SUNDAY,FOLLOW_SUNDAY_in_parse_Weekday1517); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN ); 

                    }
                    break;
                case 8 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:239:6: WEEKEND
                    {
                    match(input,WEEKEND,FOLLOW_WEEKEND_in_parse_Weekday1531); 

                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT );
                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN );
                                     

                    }
                    break;
                case 9 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:243:6: WEEKDAY_LIT
                    {
                    match(input,WEEKDAY_LIT,FOLLOW_WEEKDAY_LIT_in_parse_Weekday1544); 

                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_MON );
                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_TUE );
                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_WED );
                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_THU );
                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_FRI );
                                     

                    }
                    break;

            }
        }
        catch ( RecognitionException e ) {

                  if ( strict )
                     throw e;
                  else
                     return null;
               
        }
        finally {
        }
        return weekday;
    }
    // $ANTLR end "parse_Weekday"


    // $ANTLR start "parse_Month"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:259:1: parse_Month returns [int number] : m= MONTH ;
    public final int parse_Month() throws RecognitionException {
        int number = 0;

        Token m=null;

        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:260:4: (m= MONTH )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:260:6: m= MONTH
            {
            m=(Token)match(input,MONTH,FOLLOW_MONTH_in_parse_Month1579); 

                  number = textMonthToMonthNumber( (m!=null?m.getText():null), LOCALE );
               

            }

        }
        catch ( RecognitionException e ) {

                  return 1;
               
        }
        finally {
        }
        return number;
    }
    // $ANTLR end "parse_Month"

    // Delegated rules


    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA12_eotS =
        "\24\uffff";
    static final String DFA12_eofS =
        "\1\uffff\1\13\1\uffff\5\13\1\uffff\1\13\3\uffff\7\13";
    static final String DFA12_minS =
        "\2\6\1\uffff\5\13\1\uffff\1\13\1\15\2\uffff\7\13";
    static final String DFA12_maxS =
        "\2\52\1\uffff\5\52\1\uffff\1\52\1\27\2\uffff\7\52";
    static final String DFA12_acceptS =
        "\2\uffff\1\1\5\uffff\1\3\2\uffff\1\2\1\4\7\uffff";
    static final String DFA12_specialS =
        "\24\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\2\1\uffff\2\2\2\uffff\1\1\4\uffff\1\3\2\4\1\5\1\6\1\7\12"+
            "\2\11\10",
            "\2\2\1\uffff\2\2\2\13\1\uffff\2\12\1\uffff\1\11\20\uffff\11"+
            "\14",
            "",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "\1\15\4\uffff\1\16\2\17\1\20\1\21\1\22",
            "",
            "",
            "\2\13\1\uffff\2\12\1\uffff\1\23\20\uffff\11\14",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "\2\13\1\uffff\2\12\22\uffff\11\14",
            "\2\13\1\uffff\2\12\22\uffff\11\14"
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
            return "54:6: ( ( parse_Interval_Number_or_Text )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )";
        }
    }
 

    public static final BitSet FOLLOW_EVERY_in_parseRecurrence84 = new BitSet(new long[]{0x000007FFFFFC26C0L});
    public static final BitSet FOLLOW_AFTER_in_parseRecurrence90 = new BitSet(new long[]{0x000007FFFFFC26C0L});
    public static final BitSet FOLLOW_parse_Interval_Number_or_Text_in_parseRecurrence109 = new BitSet(new long[]{0x00000000000006C0L});
    public static final BitSet FOLLOW_DAYS_in_parseRecurrence134 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_WEEKS_in_parseRecurrence196 = new BitSet(new long[]{0x000007FC00001902L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence259 = new BitSet(new long[]{0x000007FC00000000L});
    public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence263 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_MONTHS_in_parseRecurrence315 = new BitSet(new long[]{0x0000000000FC3902L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence377 = new BitSet(new long[]{0x0000000000FC2100L});
    public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence383 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_YEARS_in_parseRecurrence435 = new BitSet(new long[]{0x0000000000FC3902L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence498 = new BitSet(new long[]{0x0000000000FC2100L});
    public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence504 = new BitSet(new long[]{0x0000080000001902L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence553 = new BitSet(new long[]{0x0000080000000100L});
    public static final BitSet FOLLOW_parse_Month_in_parseRecurrence559 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence670 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence685 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence712 = new BitSet(new long[]{0x000007FC00000000L});
    public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence715 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_UNTIL_in_parseRecurrence755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_parseRecurrence775 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INT_in_parseRecurrence779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_parseRecurrence802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst849 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_set_in_recurr_Xst872 = new BitSet(new long[]{0x0000000000FC2000L});
    public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst882 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD917 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_set_in_recurr_WD939 = new BitSet(new long[]{0x000007FC00000000L});
    public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD947 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_recurr_Xst_in_recurr_Monthly1000 = new BitSet(new long[]{0x000007FC00010002L});
    public static final BitSet FOLLOW_LAST_in_recurr_Monthly1037 = new BitSet(new long[]{0x000007FC00000000L});
    public static final BitSet FOLLOW_recurr_WD_in_recurr_Monthly1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_parse_Xst1133 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_DOT_in_parse_Xst1136 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FIRST_in_parse_Xst1150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_parse_Xst1170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_THIRD_in_parse_Xst1185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOURTH_in_parse_Xst1205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FIFTH_in_parse_Xst1224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_parse_Interval_Number_or_Text1278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_ONE_in_parse_Interval_Number_or_Text1292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_TWO_in_parse_Interval_Number_or_Text1304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_THREE_in_parse_Interval_Number_or_Text1316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_FOUR_in_parse_Interval_Number_or_Text1326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_FIVE_in_parse_Interval_Number_or_Text1337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_SIX_in_parse_Interval_Number_or_Text1348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_SEVEN_in_parse_Interval_Number_or_Text1360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_EIGHT_in_parse_Interval_Number_or_Text1370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_NINE_in_parse_Interval_Number_or_Text1380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_TEN_in_parse_Interval_Number_or_Text1391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONDAY_in_parse_Weekday1441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TUESDAY_in_parse_Weekday1455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEDNESDAY_in_parse_Weekday1468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_THURSDAY_in_parse_Weekday1479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FRIDAY_in_parse_Weekday1491 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SATURDAY_in_parse_Weekday1505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SUNDAY_in_parse_Weekday1517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKEND_in_parse_Weekday1531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKDAY_LIT_in_parse_Weekday1544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTH_in_parse_Month1579 = new BitSet(new long[]{0x0000000000000002L});

}