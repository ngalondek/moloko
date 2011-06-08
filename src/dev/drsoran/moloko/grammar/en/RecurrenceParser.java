// $ANTLR 3.3 Nov 30, 2010 12:45:30 F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g 2011-06-08 07:49:03

   package dev.drsoran.moloko.grammar.en;

   import java.text.ParseException;
   import java.text.SimpleDateFormat;
   import java.util.Calendar;
   import java.util.Comparator;
   import java.util.Map;
   import java.util.Iterator;
   import java.util.Locale;
   import java.util.Set;
   import java.util.TreeSet;
   import java.util.TreeMap;

   import dev.drsoran.moloko.util.MolokoCalendar;
   import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
   import dev.drsoran.moloko.grammar.RecurrencePatternParser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class RecurrenceParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "EVERY", "AFTER", "DAYS", "WEEKS", "BIWEEKLY", "ON", "THE", "MONTHS", "YEARS", "IN", "OF", "UNTIL", "FOR", "INT", "AND", "COMMA", "LAST", "DOT", "ST_S", "FIRST", "SECOND", "OTHER", "THIRD", "FOURTH", "FIFTH", "NUM_ONE", "NUM_TWO", "NUM_THREE", "NUM_FOUR", "NUM_FIVE", "NUM_SIX", "NUM_SEVEN", "NUM_EIGHT", "NUM_NINE", "NUM_TEN", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY", "WEEKEND", "WEEKDAY_LIT", "MONTH", "STRING", "TIMES", "MINUS", "NUMBER", "WS"
    };
    public static final int EOF=-1;
    public static final int EVERY=4;
    public static final int AFTER=5;
    public static final int DAYS=6;
    public static final int WEEKS=7;
    public static final int BIWEEKLY=8;
    public static final int ON=9;
    public static final int THE=10;
    public static final int MONTHS=11;
    public static final int YEARS=12;
    public static final int IN=13;
    public static final int OF=14;
    public static final int UNTIL=15;
    public static final int FOR=16;
    public static final int INT=17;
    public static final int AND=18;
    public static final int COMMA=19;
    public static final int LAST=20;
    public static final int DOT=21;
    public static final int ST_S=22;
    public static final int FIRST=23;
    public static final int SECOND=24;
    public static final int OTHER=25;
    public static final int THIRD=26;
    public static final int FOURTH=27;
    public static final int FIFTH=28;
    public static final int NUM_ONE=29;
    public static final int NUM_TWO=30;
    public static final int NUM_THREE=31;
    public static final int NUM_FOUR=32;
    public static final int NUM_FIVE=33;
    public static final int NUM_SIX=34;
    public static final int NUM_SEVEN=35;
    public static final int NUM_EIGHT=36;
    public static final int NUM_NINE=37;
    public static final int NUM_TEN=38;
    public static final int MONDAY=39;
    public static final int TUESDAY=40;
    public static final int WEDNESDAY=41;
    public static final int THURSDAY=42;
    public static final int FRIDAY=43;
    public static final int SATURDAY=44;
    public static final int SUNDAY=45;
    public static final int WEEKEND=46;
    public static final int WEEKDAY_LIT=47;
    public static final int MONTH=48;
    public static final int STRING=49;
    public static final int TIMES=50;
    public static final int MINUS=51;
    public static final int NUMBER=52;
    public static final int WS=53;

    // delegates
    // delegators


        public RecurrenceParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public RecurrenceParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return RecurrenceParser.tokenNames; }
    public String getGrammarFileName() { return "F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g"; }


       public RecurrenceParser()
       {
          super( null );
       }

       private final static Locale LOCALE = Locale.ENGLISH;

       private final static class CmpWeekday implements Comparator< String >
       {
          private final static int weekdayToInt( String wd )
          {
             // only take the last 2 chars, the leading chars can be
             // Xst values.
             final String weekday = wd.substring( wd.length() - 2 );

             if ( weekday.equals( RecurrencePatternParser.BYDAY_MON ) )
                return 1;
             else if ( weekday.equals( RecurrencePatternParser.BYDAY_TUE ) )
                return 2;
             else if ( weekday.equals( RecurrencePatternParser.BYDAY_WED ) )
                return 3;
             else if ( weekday.equals( RecurrencePatternParser.BYDAY_THU ) )
                return 4;
             else if ( weekday.equals( RecurrencePatternParser.BYDAY_FRI ) )
                return 5;
             else if ( weekday.equals( RecurrencePatternParser.BYDAY_SAT ) )
                return 6;
             else if ( weekday.equals( RecurrencePatternParser.BYDAY_SUN ) )
                return 7;
             else
                return 1;
          }

          public int compare( String wd1, String wd2 )
          {
             return weekdayToInt( wd1 ) - weekdayToInt( wd2 );
          }
       }

       private final static CmpWeekday CMP_WEEKDAY  = new CmpWeekday();
       
       private final static < E > String join( String delim, Iterable< E > values )
       {
          StringBuilder result = new StringBuilder();

          final Iterator< E > i = values.iterator();

          for ( boolean hasNext = i.hasNext(); hasNext; )
          {
             result.append( i.next() );
             hasNext = i.hasNext();

             if ( hasNext )
                result.append( delim );
          }

          return result.toString();
       }



    // $ANTLR start "parseRecurrence"
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:99:1: parseRecurrence returns [Map< String, Object > res] : ( ( EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )? | EOF );
    public final Map< String, Object > parseRecurrence() throws RecognitionException {
        Map< String, Object > res = null;

        Token until=null;
        Token count=null;
        int interval = 0;

        RecurrenceParser.recurr_Monthly_return r = null;

        int m = 0;

        int firstEntry = 0;



              res                               = new TreeMap< String, Object >( RecurrencePatternParser.CMP_OPERATORS );
              Boolean isEvery                   = Boolean.FALSE;

              final TreeSet< String >  weekdays = new TreeSet< String >( CMP_WEEKDAY );
              final TreeSet< Integer > ints     = new TreeSet< Integer >();

              interval                          = 1;
              String freq                       = null;
              String resolution                 = null;
              String resolutionVal              = null;
           
        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:123:4: ( ( EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )? | EOF )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=EVERY && LA18_0<=BIWEEKLY)||(LA18_0>=MONTHS && LA18_0<=YEARS)||LA18_0==INT||(LA18_0>=FIRST && LA18_0<=WEEKDAY_LIT)) ) {
                alt18=1;
            }
            else if ( (LA18_0==EOF) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:123:6: ( EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )?
                    {
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:123:6: ( EVERY | AFTER )?
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
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:123:7: EVERY
                            {
                            match(input,EVERY,FOLLOW_EVERY_in_parseRecurrence70); 
                             isEvery = Boolean.TRUE; 

                            }
                            break;
                        case 2 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:123:43: AFTER
                            {
                            match(input,AFTER,FOLLOW_AFTER_in_parseRecurrence76); 

                            }
                            break;

                    }

                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:124:6: ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )
                    int alt16=4;
                    alt16 = dfa16.predict(input);
                    switch (alt16) {
                        case 1 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:125:9: (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? )
                            {
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:125:17: (interval= parse_Number )?
                            int alt2=2;
                            int LA2_0 = input.LA(1);

                            if ( (LA2_0==INT||(LA2_0>=NUM_ONE && LA2_0<=NUM_TEN)) ) {
                                alt2=1;
                            }
                            switch (alt2) {
                                case 1 :
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:125:17: interval= parse_Number
                                    {
                                    pushFollow(FOLLOW_parse_Number_in_parseRecurrence97);
                                    interval=parse_Number();

                                    state._fsp--;


                                    }
                                    break;

                            }

                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:126:9: ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? )
                            int alt15=4;
                            switch ( input.LA(1) ) {
                            case DAYS:
                                {
                                alt15=1;
                                }
                                break;
                            case WEEKS:
                            case BIWEEKLY:
                                {
                                alt15=2;
                                }
                                break;
                            case MONTHS:
                                {
                                alt15=3;
                                }
                                break;
                            case YEARS:
                                {
                                alt15=4;
                                }
                                break;
                            default:
                                NoViableAltException nvae =
                                    new NoViableAltException("", 15, 0, input);

                                throw nvae;
                            }

                            switch (alt15) {
                                case 1 :
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:127:13: DAYS
                                    {
                                    match(input,DAYS,FOLLOW_DAYS_in_parseRecurrence122); 
                                     freq = RecurrencePatternParser.VAL_DAILY_LIT;  

                                    }
                                    break;
                                case 2 :
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:128:13: ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )?
                                    {
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:128:13: ( WEEKS | BIWEEKLY )
                                    int alt3=2;
                                    int LA3_0 = input.LA(1);

                                    if ( (LA3_0==WEEKS) ) {
                                        alt3=1;
                                    }
                                    else if ( (LA3_0==BIWEEKLY) ) {
                                        alt3=2;
                                    }
                                    else {
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 3, 0, input);

                                        throw nvae;
                                    }
                                    switch (alt3) {
                                        case 1 :
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:129:18: WEEKS
                                            {
                                            match(input,WEEKS,FOLLOW_WEEKS_in_parseRecurrence203); 

                                            }
                                            break;
                                        case 2 :
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:130:18: BIWEEKLY
                                            {
                                            match(input,BIWEEKLY,FOLLOW_BIWEEKLY_in_parseRecurrence222); 

                                                                interval = 2;
                                                             

                                            }
                                            break;

                                    }

                                     freq = RecurrencePatternParser.VAL_WEEKLY_LIT; 
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:135:14: ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )?
                                    int alt6=2;
                                    int LA6_0 = input.LA(1);

                                    if ( ((LA6_0>=ON && LA6_0<=THE)||(LA6_0>=MONDAY && LA6_0<=WEEKDAY_LIT)) ) {
                                        alt6=1;
                                    }
                                    switch (alt6) {
                                        case 1 :
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:135:15: ( ON )? ( THE )? recurr_WD[weekdays, \"\"]
                                            {
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:135:15: ( ON )?
                                            int alt4=2;
                                            int LA4_0 = input.LA(1);

                                            if ( (LA4_0==ON) ) {
                                                alt4=1;
                                            }
                                            switch (alt4) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:135:15: ON
                                                    {
                                                    match(input,ON,FOLLOW_ON_in_parseRecurrence322); 

                                                    }
                                                    break;

                                            }

                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:135:19: ( THE )?
                                            int alt5=2;
                                            int LA5_0 = input.LA(1);

                                            if ( (LA5_0==THE) ) {
                                                alt5=1;
                                            }
                                            switch (alt5) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:135:19: THE
                                                    {
                                                    match(input,THE,FOLLOW_THE_in_parseRecurrence325); 

                                                    }
                                                    break;

                                            }

                                            pushFollow(FOLLOW_recurr_WD_in_parseRecurrence328);
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
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:141:13: MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )?
                                    {
                                    match(input,MONTHS,FOLLOW_MONTHS_in_parseRecurrence380); 
                                     freq = RecurrencePatternParser.VAL_MONTHLY_LIT;
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:142:14: ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )?
                                    int alt9=2;
                                    int LA9_0 = input.LA(1);

                                    if ( ((LA9_0>=ON && LA9_0<=THE)||LA9_0==INT||(LA9_0>=FIRST && LA9_0<=FIFTH)) ) {
                                        alt9=1;
                                    }
                                    switch (alt9) {
                                        case 1 :
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:142:15: ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints]
                                            {
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:142:15: ( ON )?
                                            int alt7=2;
                                            int LA7_0 = input.LA(1);

                                            if ( (LA7_0==ON) ) {
                                                alt7=1;
                                            }
                                            switch (alt7) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:142:15: ON
                                                    {
                                                    match(input,ON,FOLLOW_ON_in_parseRecurrence442); 

                                                    }
                                                    break;

                                            }

                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:142:19: ( THE )?
                                            int alt8=2;
                                            int LA8_0 = input.LA(1);

                                            if ( (LA8_0==THE) ) {
                                                alt8=1;
                                            }
                                            switch (alt8) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:142:19: THE
                                                    {
                                                    match(input,THE,FOLLOW_THE_in_parseRecurrence445); 

                                                    }
                                                    break;

                                            }

                                            pushFollow(FOLLOW_recurr_Monthly_in_parseRecurrence450);
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
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:150:13: YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )?
                                    {
                                    match(input,YEARS,FOLLOW_YEARS_in_parseRecurrence506); 
                                     freq = RecurrencePatternParser.VAL_YEARLY_LIT; 
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:151:14: ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )?
                                    int alt14=2;
                                    int LA14_0 = input.LA(1);

                                    if ( ((LA14_0>=ON && LA14_0<=THE)||LA14_0==INT||(LA14_0>=FIRST && LA14_0<=FIFTH)) ) {
                                        alt14=1;
                                    }
                                    switch (alt14) {
                                        case 1 :
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:151:15: ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )?
                                            {
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:151:15: ( ON )?
                                            int alt10=2;
                                            int LA10_0 = input.LA(1);

                                            if ( (LA10_0==ON) ) {
                                                alt10=1;
                                            }
                                            switch (alt10) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:151:15: ON
                                                    {
                                                    match(input,ON,FOLLOW_ON_in_parseRecurrence569); 

                                                    }
                                                    break;

                                            }

                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:151:19: ( THE )?
                                            int alt11=2;
                                            int LA11_0 = input.LA(1);

                                            if ( (LA11_0==THE) ) {
                                                alt11=1;
                                            }
                                            switch (alt11) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:151:19: THE
                                                    {
                                                    match(input,THE,FOLLOW_THE_in_parseRecurrence572); 

                                                    }
                                                    break;

                                            }

                                            pushFollow(FOLLOW_recurr_Monthly_in_parseRecurrence577);
                                            r=recurr_Monthly(weekdays, ints);

                                            state._fsp--;


                                                                      freq          = r.freq;
                                                                      interval      = r.interval;
                                                                      resolution    = r.resolution;
                                                                      resolutionVal = r.resolutionVal;
                                                                   
                                            if ( !(( r.hasWD )) ) {
                                                throw new FailedPredicateException(input, "parseRecurrence", " r.hasWD ");
                                            }
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:158:39: ( ( IN | OF )? m= parse_Month )?
                                            int alt13=2;
                                            int LA13_0 = input.LA(1);

                                            if ( ((LA13_0>=IN && LA13_0<=OF)||LA13_0==MONTH) ) {
                                                alt13=1;
                                            }
                                            switch (alt13) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:159:41: ( IN | OF )? m= parse_Month
                                                    {
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:159:41: ( IN | OF )?
                                                    int alt12=2;
                                                    int LA12_0 = input.LA(1);

                                                    if ( ((LA12_0>=IN && LA12_0<=OF)) ) {
                                                        alt12=1;
                                                    }
                                                    switch (alt12) {
                                                        case 1 :
                                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:
                                                            {
                                                            if ( (input.LA(1)>=IN && input.LA(1)<=OF) ) {
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

                                                    pushFollow(FOLLOW_parse_Month_in_parseRecurrence725);
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
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:169:6: recurr_Xst[ints]
                            {
                            pushFollow(FOLLOW_recurr_Xst_in_parseRecurrence831);
                            recurr_Xst(ints);

                            state._fsp--;


                                    freq          = RecurrencePatternParser.VAL_MONTHLY_LIT;
                                    interval      = 1;
                                    resolution    = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
                                    resolutionVal = join( ",", ints );
                                 

                            }
                            break;
                        case 3 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:176:6: recurr_WD[weekdays, \"\"]
                            {
                            pushFollow(FOLLOW_recurr_WD_in_parseRecurrence846);
                            recurr_WD(weekdays, "");

                            state._fsp--;


                                    freq          = RecurrencePatternParser.VAL_WEEKLY_LIT;
                                    interval      = 1;
                                    resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
                                    resolutionVal = join( ",", weekdays );
                                 

                            }
                            break;
                        case 4 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:183:6: (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                            {
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:183:6: (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:184:9: firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"]
                            {
                            pushFollow(FOLLOW_recurr_Xst_in_parseRecurrence873);
                            firstEntry=recurr_Xst(ints);

                            state._fsp--;

                            pushFollow(FOLLOW_recurr_WD_in_parseRecurrence876);
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

                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:193:4: (until= UNTIL | FOR count= INT )?
                    int alt17=3;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==UNTIL) ) {
                        alt17=1;
                    }
                    else if ( (LA17_0==FOR) ) {
                        alt17=2;
                    }
                    switch (alt17) {
                        case 1 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:194:9: until= UNTIL
                            {
                            until=(Token)match(input,UNTIL,FOLLOW_UNTIL_in_parseRecurrence916); 

                                       final String dateTimeString = until.getText()
                                                                          .toUpperCase()
                                                                          .replaceFirst( RecurrencePatternParser.OP_UNTIL_LIT +  "\\s*",
                                                                                         "" );

                                       final MolokoCalendar untilDate = RtmDateTimeParsing.parseDateTimeSpec( dateTimeString );

                                       if ( untilDate != null )
                                       {
                                          final SimpleDateFormat sdf = new SimpleDateFormat( RecurrencePatternParser.DATE_PATTERN );
                                          res.put( RecurrencePatternParser.OP_UNTIL_LIT, sdf.format( untilDate.getTime() ) );
                                       }
                                    

                            }
                            break;
                        case 2 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:209:9: FOR count= INT
                            {
                            match(input,FOR,FOLLOW_FOR_in_parseRecurrence936); 
                            count=(Token)match(input,INT,FOLLOW_INT_in_parseRecurrence940); 

                                       res.put( RecurrencePatternParser.OP_COUNT_LIT, Integer.parseInt( (count!=null?count.getText():null) ) );
                                    

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:214:6: EOF
                    {
                    match(input,EOF,FOLLOW_EOF_in_parseRecurrence963); 

                    }
                    break;

            }

                  res.put( RecurrencePatternParser.OP_FREQ_LIT, freq );
                  res.put( RecurrencePatternParser.OP_INTERVAL_LIT, new Integer( interval ) );

                  if ( resolution != null && resolutionVal != null )
                     res.put( resolution, resolutionVal );

                  res.put( RecurrencePatternParser.IS_EVERY, isEvery );
               
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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:225:1: recurr_Xst[Set< Integer > res] returns [int firstEntry] : x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? ;
    public final int recurr_Xst(Set< Integer > res) throws RecognitionException {
        int firstEntry = 0;

        int x = 0;


        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:226:4: (x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:226:6: x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )?
            {
            pushFollow(FOLLOW_parse_Xst_in_recurr_Xst1010);
            x=parse_Xst();

            state._fsp--;

             res.add( x ); firstEntry = x; 
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:227:4: ( ( ( AND | COMMA ) x= parse_Xst )+ )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0>=AND && LA20_0<=COMMA)) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:227:5: ( ( AND | COMMA ) x= parse_Xst )+
                    {
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:227:5: ( ( AND | COMMA ) x= parse_Xst )+
                    int cnt19=0;
                    loop19:
                    do {
                        int alt19=2;
                        int LA19_0 = input.LA(1);

                        if ( ((LA19_0>=AND && LA19_0<=COMMA)) ) {
                            alt19=1;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:227:6: ( AND | COMMA ) x= parse_Xst
                    	    {
                    	    if ( (input.LA(1)>=AND && input.LA(1)<=COMMA) ) {
                    	        input.consume();
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }

                    	    pushFollow(FOLLOW_parse_Xst_in_recurr_Xst1043);
                    	    x=parse_Xst();

                    	    state._fsp--;

                    	     res.add( x ); 

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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:234:1: recurr_WD[Set< String > weekdays, String Xst] : parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? ;
    public final void recurr_WD(Set< String > weekdays, String Xst) throws RecognitionException {
        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:235:4: ( parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:235:6: parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
            {
            pushFollow(FOLLOW_parse_Weekday_in_recurr_WD1078);
            parse_Weekday(weekdays, Xst, true);

            state._fsp--;

            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:236:4: ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( ((LA22_0>=AND && LA22_0<=COMMA)) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:236:5: ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
                    {
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:236:5: ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
                    int cnt21=0;
                    loop21:
                    do {
                        int alt21=2;
                        int LA21_0 = input.LA(1);

                        if ( ((LA21_0>=AND && LA21_0<=COMMA)) ) {
                            alt21=1;
                        }


                        switch (alt21) {
                    	case 1 :
                    	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:236:6: ( AND | COMMA ) parse_Weekday[weekdays, Xst, true]
                    	    {
                    	    if ( (input.LA(1)>=AND && input.LA(1)<=COMMA) ) {
                    	        input.consume();
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }

                    	    pushFollow(FOLLOW_parse_Weekday_in_recurr_WD1108);
                    	    parse_Weekday(weekdays, Xst, true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt21 >= 1 ) break loop21;
                                EarlyExitException eee =
                                    new EarlyExitException(21, input);
                                throw eee;
                        }
                        cnt21++;
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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:243:1: recurr_Monthly[Set< String > weekdays,\r\n Set< Integer > ints ] returns [String freq,\r\n String resolution,\r\n String resolutionVal,\r\n int interval,\r\n boolean hasWD] : firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? ;
    public final RecurrenceParser.recurr_Monthly_return recurr_Monthly(Set< String >  weekdays, Set< Integer > ints) throws RecognitionException {
        RecurrenceParser.recurr_Monthly_return retval = new RecurrenceParser.recurr_Monthly_return();
        retval.start = input.LT(1);

        int firstEntry = 0;



              retval.freq     = RecurrencePatternParser.VAL_MONTHLY_LIT;
              retval.interval = 1;
           
        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:254:4: (firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:254:6: firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            {
            pushFollow(FOLLOW_recurr_Xst_in_recurr_Monthly1161);
            firstEntry=recurr_Xst(ints);

            state._fsp--;


                    retval.resolution    = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
                    retval.resolutionVal = join( ",", ints );
                 
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:258:8: ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==LAST||(LA24_0>=MONDAY && LA24_0<=WEEKDAY_LIT)) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:259:11: ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )]
                    {
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:259:11: ( LAST )?
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==LAST) ) {
                        alt23=1;
                    }
                    switch (alt23) {
                        case 1 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:260:14: LAST
                            {
                            match(input,LAST,FOLLOW_LAST_in_recurr_Monthly1198); 

                                            firstEntry = -firstEntry;
                                         

                            }
                            break;

                    }

                    pushFollow(FOLLOW_recurr_WD_in_recurr_Monthly1238);
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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:278:1: parse_Xst returns [int number] : (n= INT ( DOT | ST_S )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH );
    public final int parse_Xst() throws RecognitionException {
        int number = 0;

        Token n=null;

        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:279:4: (n= INT ( DOT | ST_S )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH )
            int alt26=6;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt26=1;
                }
                break;
            case FIRST:
                {
                alt26=2;
                }
                break;
            case SECOND:
            case OTHER:
                {
                alt26=3;
                }
                break;
            case THIRD:
                {
                alt26=4;
                }
                break;
            case FOURTH:
                {
                alt26=5;
                }
                break;
            case FIFTH:
                {
                alt26=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:279:6: n= INT ( DOT | ST_S )?
                    {
                    n=(Token)match(input,INT,FOLLOW_INT_in_parse_Xst1294); 
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:279:12: ( DOT | ST_S )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( ((LA25_0>=DOT && LA25_0<=ST_S)) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:
                            {
                            if ( (input.LA(1)>=DOT && input.LA(1)<=ST_S) ) {
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


                          number = Integer.parseInt( (n!=null?n.getText():null) );

                          if ( number < 1 )
                             number = 1;
                          else if ( number > 31 )
                             number = 31;
                       

                    }
                    break;
                case 2 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:288:6: FIRST
                    {
                    match(input,FIRST,FOLLOW_FIRST_in_parse_Xst1313); 
                     number = 1; 

                    }
                    break;
                case 3 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:289:6: ( SECOND | OTHER )
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
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:290:6: THIRD
                    {
                    match(input,THIRD,FOLLOW_THIRD_in_parse_Xst1348); 
                     number = 3; 

                    }
                    break;
                case 5 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:291:6: FOURTH
                    {
                    match(input,FOURTH,FOLLOW_FOURTH_in_parse_Xst1368); 
                     number = 4; 

                    }
                    break;
                case 6 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:292:6: FIFTH
                    {
                    match(input,FIFTH,FOLLOW_FIFTH_in_parse_Xst1387); 
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


    // $ANTLR start "parse_Number"
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:303:1: parse_Number returns [int number] : (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN );
    public final int parse_Number() throws RecognitionException {
        int number = 0;

        Token n=null;

        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:304:4: (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN )
            int alt27=11;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt27=1;
                }
                break;
            case NUM_ONE:
                {
                alt27=2;
                }
                break;
            case NUM_TWO:
                {
                alt27=3;
                }
                break;
            case NUM_THREE:
                {
                alt27=4;
                }
                break;
            case NUM_FOUR:
                {
                alt27=5;
                }
                break;
            case NUM_FIVE:
                {
                alt27=6;
                }
                break;
            case NUM_SIX:
                {
                alt27=7;
                }
                break;
            case NUM_SEVEN:
                {
                alt27=8;
                }
                break;
            case NUM_EIGHT:
                {
                alt27=9;
                }
                break;
            case NUM_NINE:
                {
                alt27=10;
                }
                break;
            case NUM_TEN:
                {
                alt27=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:304:6: n= INT
                    {
                    n=(Token)match(input,INT,FOLLOW_INT_in_parse_Number1445); 
                     number = Integer.parseInt( (n!=null?n.getText():null) ); 

                    }
                    break;
                case 2 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:305:6: NUM_ONE
                    {
                    match(input,NUM_ONE,FOLLOW_NUM_ONE_in_parse_Number1459); 
                     number = 1; 

                    }
                    break;
                case 3 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:306:6: NUM_TWO
                    {
                    match(input,NUM_TWO,FOLLOW_NUM_TWO_in_parse_Number1471); 
                     number = 2; 

                    }
                    break;
                case 4 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:307:6: NUM_THREE
                    {
                    match(input,NUM_THREE,FOLLOW_NUM_THREE_in_parse_Number1483); 
                     number = 3; 

                    }
                    break;
                case 5 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:308:6: NUM_FOUR
                    {
                    match(input,NUM_FOUR,FOLLOW_NUM_FOUR_in_parse_Number1493); 
                     number = 4; 

                    }
                    break;
                case 6 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:309:6: NUM_FIVE
                    {
                    match(input,NUM_FIVE,FOLLOW_NUM_FIVE_in_parse_Number1504); 
                     number = 5; 

                    }
                    break;
                case 7 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:310:6: NUM_SIX
                    {
                    match(input,NUM_SIX,FOLLOW_NUM_SIX_in_parse_Number1515); 
                     number = 6; 

                    }
                    break;
                case 8 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:311:6: NUM_SEVEN
                    {
                    match(input,NUM_SEVEN,FOLLOW_NUM_SEVEN_in_parse_Number1527); 
                     number = 7; 

                    }
                    break;
                case 9 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:312:6: NUM_EIGHT
                    {
                    match(input,NUM_EIGHT,FOLLOW_NUM_EIGHT_in_parse_Number1537); 
                     number = 8; 

                    }
                    break;
                case 10 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:313:6: NUM_NINE
                    {
                    match(input,NUM_NINE,FOLLOW_NUM_NINE_in_parse_Number1547); 
                     number = 9; 

                    }
                    break;
                case 11 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:314:6: NUM_TEN
                    {
                    match(input,NUM_TEN,FOLLOW_NUM_TEN_in_parse_Number1558); 
                     number = 10; 

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
    // $ANTLR end "parse_Number"


    // $ANTLR start "parse_Weekday"
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:325:1: parse_Weekday[Set< String > weekdays, String Xst, boolean strict] returns [String weekday] : ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT );
    public final String parse_Weekday(Set< String > weekdays, String Xst, boolean strict) throws RecognitionException {
        String weekday = null;

        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:326:4: ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT )
            int alt28=9;
            switch ( input.LA(1) ) {
            case MONDAY:
                {
                alt28=1;
                }
                break;
            case TUESDAY:
                {
                alt28=2;
                }
                break;
            case WEDNESDAY:
                {
                alt28=3;
                }
                break;
            case THURSDAY:
                {
                alt28=4;
                }
                break;
            case FRIDAY:
                {
                alt28=5;
                }
                break;
            case SATURDAY:
                {
                alt28=6;
                }
                break;
            case SUNDAY:
                {
                alt28=7;
                }
                break;
            case WEEKEND:
                {
                alt28=8;
                }
                break;
            case WEEKDAY_LIT:
                {
                alt28=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:326:6: MONDAY
                    {
                    match(input,MONDAY,FOLLOW_MONDAY_in_parse_Weekday1608); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_MON ); 

                    }
                    break;
                case 2 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:327:6: TUESDAY
                    {
                    match(input,TUESDAY,FOLLOW_TUESDAY_in_parse_Weekday1622); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_TUE ); 

                    }
                    break;
                case 3 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:328:6: WEDNESDAY
                    {
                    match(input,WEDNESDAY,FOLLOW_WEDNESDAY_in_parse_Weekday1635); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_WED ); 

                    }
                    break;
                case 4 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:329:6: THURSDAY
                    {
                    match(input,THURSDAY,FOLLOW_THURSDAY_in_parse_Weekday1646); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_THU ); 

                    }
                    break;
                case 5 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:330:6: FRIDAY
                    {
                    match(input,FRIDAY,FOLLOW_FRIDAY_in_parse_Weekday1658); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_FRI ); 

                    }
                    break;
                case 6 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:331:6: SATURDAY
                    {
                    match(input,SATURDAY,FOLLOW_SATURDAY_in_parse_Weekday1672); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT ); 

                    }
                    break;
                case 7 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:332:6: SUNDAY
                    {
                    match(input,SUNDAY,FOLLOW_SUNDAY_in_parse_Weekday1684); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN ); 

                    }
                    break;
                case 8 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:333:6: WEEKEND
                    {
                    match(input,WEEKEND,FOLLOW_WEEKEND_in_parse_Weekday1698); 

                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT );
                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN );
                                     

                    }
                    break;
                case 9 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:337:6: WEEKDAY_LIT
                    {
                    match(input,WEEKDAY_LIT,FOLLOW_WEEKDAY_LIT_in_parse_Weekday1711); 

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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:353:1: parse_Month returns [int number] : m= MONTH ;
    public final int parse_Month() throws RecognitionException {
        int number = 0;

        Token m=null;

        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:354:4: (m= MONTH )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\en\\Recurrence.g:354:6: m= MONTH
            {
            m=(Token)match(input,MONTH,FOLLOW_MONTH_in_parse_Month1746); 

                  try
                  {
                     final SimpleDateFormat sdf = new SimpleDateFormat( "MMM", LOCALE );
                     sdf.parse( (m!=null?m.getText():null) );

                     number = sdf.getCalendar().get( Calendar.MONTH );

                     if ( number == 0 )
                        ++number;
                  }
                  catch( ParseException e )
                  {
                     number = 1;
                  }
               

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


    protected DFA16 dfa16 = new DFA16(this);
    static final String DFA16_eotS =
        "\24\uffff";
    static final String DFA16_eofS =
        "\1\uffff\1\13\1\uffff\5\13\1\uffff\1\13\3\uffff\7\13";
    static final String DFA16_minS =
        "\2\6\1\uffff\5\17\1\uffff\1\17\1\21\2\uffff\7\17";
    static final String DFA16_maxS =
        "\2\57\1\uffff\5\57\1\uffff\1\57\1\34\2\uffff\7\57";
    static final String DFA16_acceptS =
        "\2\uffff\1\1\5\uffff\1\3\2\uffff\1\2\1\4\7\uffff";
    static final String DFA16_specialS =
        "\24\uffff}>";
    static final String[] DFA16_transitionS = {
            "\3\2\2\uffff\2\2\4\uffff\1\1\5\uffff\1\3\2\4\1\5\1\6\1\7\12"+
            "\2\11\10",
            "\3\2\2\uffff\2\2\2\uffff\2\13\1\uffff\2\12\1\uffff\2\11\20"+
            "\uffff\11\14",
            "",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "\1\15\5\uffff\1\16\2\17\1\20\1\21\1\22",
            "",
            "",
            "\2\13\1\uffff\2\12\1\uffff\2\23\20\uffff\11\14",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "\2\13\1\uffff\2\12\23\uffff\11\14",
            "\2\13\1\uffff\2\12\23\uffff\11\14"
    };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }
        public String getDescription() {
            return "124:6: ( (interval= parse_Number )? ( DAYS | ( WEEKS | BIWEEKLY ) ( ( ON )? ( THE )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? ( THE )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( IN | OF )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )";
        }
    }
 

    public static final BitSet FOLLOW_EVERY_in_parseRecurrence70 = new BitSet(new long[]{0x0000FFFFFF8219C0L});
    public static final BitSet FOLLOW_AFTER_in_parseRecurrence76 = new BitSet(new long[]{0x0000FFFFFF8219C0L});
    public static final BitSet FOLLOW_parse_Number_in_parseRecurrence97 = new BitSet(new long[]{0x00000000000019C0L});
    public static final BitSet FOLLOW_DAYS_in_parseRecurrence122 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_WEEKS_in_parseRecurrence203 = new BitSet(new long[]{0x0000FF8000018602L});
    public static final BitSet FOLLOW_BIWEEKLY_in_parseRecurrence222 = new BitSet(new long[]{0x0000FF8000018602L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence322 = new BitSet(new long[]{0x0000FF8000000400L});
    public static final BitSet FOLLOW_THE_in_parseRecurrence325 = new BitSet(new long[]{0x0000FF8000000000L});
    public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence328 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_MONTHS_in_parseRecurrence380 = new BitSet(new long[]{0x000000001F838602L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence442 = new BitSet(new long[]{0x000000001F820600L});
    public static final BitSet FOLLOW_THE_in_parseRecurrence445 = new BitSet(new long[]{0x000000001F820600L});
    public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence450 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_YEARS_in_parseRecurrence506 = new BitSet(new long[]{0x000000001F838602L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence569 = new BitSet(new long[]{0x000000001F820600L});
    public static final BitSet FOLLOW_THE_in_parseRecurrence572 = new BitSet(new long[]{0x000000001F820600L});
    public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence577 = new BitSet(new long[]{0x000100000001E002L});
    public static final BitSet FOLLOW_set_in_parseRecurrence673 = new BitSet(new long[]{0x0001000000006000L});
    public static final BitSet FOLLOW_parse_Month_in_parseRecurrence725 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence831 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence846 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence873 = new BitSet(new long[]{0x0000FF8000000000L});
    public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence876 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_UNTIL_in_parseRecurrence916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_parseRecurrence936 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_INT_in_parseRecurrence940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_parseRecurrence963 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst1010 = new BitSet(new long[]{0x00000000000C0002L});
    public static final BitSet FOLLOW_set_in_recurr_Xst1033 = new BitSet(new long[]{0x000000001F820000L});
    public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst1043 = new BitSet(new long[]{0x00000000000C0002L});
    public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD1078 = new BitSet(new long[]{0x00000000000C0002L});
    public static final BitSet FOLLOW_set_in_recurr_WD1100 = new BitSet(new long[]{0x0000FF8000000000L});
    public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD1108 = new BitSet(new long[]{0x00000000000C0002L});
    public static final BitSet FOLLOW_recurr_Xst_in_recurr_Monthly1161 = new BitSet(new long[]{0x0000FF8000100002L});
    public static final BitSet FOLLOW_LAST_in_recurr_Monthly1198 = new BitSet(new long[]{0x0000FF8000000000L});
    public static final BitSet FOLLOW_recurr_WD_in_recurr_Monthly1238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_parse_Xst1294 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_set_in_parse_Xst1296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FIRST_in_parse_Xst1313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_parse_Xst1333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_THIRD_in_parse_Xst1348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOURTH_in_parse_Xst1368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FIFTH_in_parse_Xst1387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_parse_Number1445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_ONE_in_parse_Number1459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_TWO_in_parse_Number1471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_THREE_in_parse_Number1483 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_FOUR_in_parse_Number1493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_FIVE_in_parse_Number1504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_SIX_in_parse_Number1515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_SEVEN_in_parse_Number1527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_EIGHT_in_parse_Number1537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_NINE_in_parse_Number1547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_TEN_in_parse_Number1558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONDAY_in_parse_Weekday1608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TUESDAY_in_parse_Weekday1622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEDNESDAY_in_parse_Weekday1635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_THURSDAY_in_parse_Weekday1646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FRIDAY_in_parse_Weekday1658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SATURDAY_in_parse_Weekday1672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SUNDAY_in_parse_Weekday1684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKEND_in_parse_Weekday1698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKDAY_LIT_in_parse_Weekday1711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTH_in_parse_Month1746 = new BitSet(new long[]{0x0000000000000002L});

}