// $ANTLR 3.3 Nov 30, 2010 12:45:30 F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g 2011-06-08 08:45:20

   package dev.drsoran.moloko.grammar.de;

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
    public String getGrammarFileName() { return "F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g"; }


       public RecurrenceParser()
       {
          super( null );
       }

       private final static Locale LOCALE = Locale.GERMAN;

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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:99:1: parseRecurrence returns [Map< String, Object > res] : ( ( EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )? | EOF );
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
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:123:4: ( ( EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )? | EOF )
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
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:123:6: ( EVERY | AFTER )? ( (interval= parse_Number )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) ) (until= UNTIL | FOR count= INT )?
                    {
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:123:6: ( EVERY | AFTER )?
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
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:123:7: EVERY
                            {
                            match(input,EVERY,FOLLOW_EVERY_in_parseRecurrence70); 
                             isEvery = Boolean.TRUE; 

                            }
                            break;
                        case 2 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:123:43: AFTER
                            {
                            match(input,AFTER,FOLLOW_AFTER_in_parseRecurrence76); 

                            }
                            break;

                    }

                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:124:6: ( (interval= parse_Number )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )
                    int alt12=4;
                    alt12 = dfa12.predict(input);
                    switch (alt12) {
                        case 1 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:125:9: (interval= parse_Number )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? )
                            {
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:125:17: (interval= parse_Number )?
                            int alt2=2;
                            int LA2_0 = input.LA(1);

                            if ( (LA2_0==INT||(LA2_0>=NUM_ONE && LA2_0<=NUM_TEN)) ) {
                                alt2=1;
                            }
                            switch (alt2) {
                                case 1 :
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:125:17: interval= parse_Number
                                    {
                                    pushFollow(FOLLOW_parse_Number_in_parseRecurrence97);
                                    interval=parse_Number();

                                    state._fsp--;


                                    }
                                    break;

                            }

                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:126:9: ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? )
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
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:127:13: DAYS
                                    {
                                    match(input,DAYS,FOLLOW_DAYS_in_parseRecurrence122); 
                                     freq = RecurrencePatternParser.VAL_DAILY_LIT;  

                                    }
                                    break;
                                case 2 :
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:128:13: WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )?
                                    {
                                    match(input,WEEKS,FOLLOW_WEEKS_in_parseRecurrence184); 
                                     freq = RecurrencePatternParser.VAL_WEEKLY_LIT; 
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:129:14: ( ( ON )? recurr_WD[weekdays, \"\"] )?
                                    int alt4=2;
                                    int LA4_0 = input.LA(1);

                                    if ( (LA4_0==ON||(LA4_0>=MONDAY && LA4_0<=WEEKDAY_LIT)) ) {
                                        alt4=1;
                                    }
                                    switch (alt4) {
                                        case 1 :
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:129:15: ( ON )? recurr_WD[weekdays, \"\"]
                                            {
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:129:15: ( ON )?
                                            int alt3=2;
                                            int LA3_0 = input.LA(1);

                                            if ( (LA3_0==ON) ) {
                                                alt3=1;
                                            }
                                            switch (alt3) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:129:15: ON
                                                    {
                                                    match(input,ON,FOLLOW_ON_in_parseRecurrence247); 

                                                    }
                                                    break;

                                            }

                                            pushFollow(FOLLOW_recurr_WD_in_parseRecurrence251);
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
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:135:13: MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )?
                                    {
                                    match(input,MONTHS,FOLLOW_MONTHS_in_parseRecurrence303); 
                                     freq = RecurrencePatternParser.VAL_MONTHLY_LIT;
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:136:14: ( ( ON )? r= recurr_Monthly[weekdays, ints] )?
                                    int alt6=2;
                                    int LA6_0 = input.LA(1);

                                    if ( (LA6_0==ON||LA6_0==INT||(LA6_0>=FIRST && LA6_0<=FIFTH)) ) {
                                        alt6=1;
                                    }
                                    switch (alt6) {
                                        case 1 :
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:136:15: ( ON )? r= recurr_Monthly[weekdays, ints]
                                            {
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:136:15: ( ON )?
                                            int alt5=2;
                                            int LA5_0 = input.LA(1);

                                            if ( (LA5_0==ON) ) {
                                                alt5=1;
                                            }
                                            switch (alt5) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:136:15: ON
                                                    {
                                                    match(input,ON,FOLLOW_ON_in_parseRecurrence365); 

                                                    }
                                                    break;

                                            }

                                            pushFollow(FOLLOW_recurr_Monthly_in_parseRecurrence371);
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
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:144:13: YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )?
                                    {
                                    match(input,YEARS,FOLLOW_YEARS_in_parseRecurrence423); 
                                     freq = RecurrencePatternParser.VAL_YEARLY_LIT; 
                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:145:14: ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )?
                                    int alt10=2;
                                    int LA10_0 = input.LA(1);

                                    if ( (LA10_0==ON||LA10_0==INT||(LA10_0>=FIRST && LA10_0<=FIFTH)) ) {
                                        alt10=1;
                                    }
                                    switch (alt10) {
                                        case 1 :
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:145:15: ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )?
                                            {
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:145:15: ( ON )?
                                            int alt7=2;
                                            int LA7_0 = input.LA(1);

                                            if ( (LA7_0==ON) ) {
                                                alt7=1;
                                            }
                                            switch (alt7) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:145:15: ON
                                                    {
                                                    match(input,ON,FOLLOW_ON_in_parseRecurrence486); 

                                                    }
                                                    break;

                                            }

                                            pushFollow(FOLLOW_recurr_Monthly_in_parseRecurrence492);
                                            r=recurr_Monthly(weekdays, ints);

                                            state._fsp--;


                                                                  freq          = r.freq;
                                                                  interval      = r.interval;
                                                                  resolution    = r.resolution;
                                                                  resolutionVal = r.resolutionVal;
                                                               
                                            if ( !(( r.hasWD )) ) {
                                                throw new FailedPredicateException(input, "parseRecurrence", " r.hasWD ");
                                            }
                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:152:35: ( ( ON )? m= parse_Month )?
                                            int alt9=2;
                                            int LA9_0 = input.LA(1);

                                            if ( (LA9_0==ON||LA9_0==MONTH) ) {
                                                alt9=1;
                                            }
                                            switch (alt9) {
                                                case 1 :
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:152:37: ( ON )? m= parse_Month
                                                    {
                                                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:152:37: ( ON )?
                                                    int alt8=2;
                                                    int LA8_0 = input.LA(1);

                                                    if ( (LA8_0==ON) ) {
                                                        alt8=1;
                                                    }
                                                    switch (alt8) {
                                                        case 1 :
                                                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:152:38: ON
                                                            {
                                                            match(input,ON,FOLLOW_ON_in_parseRecurrence541); 

                                                            }
                                                            break;

                                                    }

                                                    pushFollow(FOLLOW_parse_Month_in_parseRecurrence547);
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
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:161:6: recurr_Xst[ints]
                            {
                            pushFollow(FOLLOW_recurr_Xst_in_parseRecurrence658);
                            recurr_Xst(ints);

                            state._fsp--;


                                    freq          = RecurrencePatternParser.VAL_MONTHLY_LIT;
                                    interval      = 1;
                                    resolution    = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
                                    resolutionVal = join( ",", ints );
                                 

                            }
                            break;
                        case 3 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:168:6: recurr_WD[weekdays, \"\"]
                            {
                            pushFollow(FOLLOW_recurr_WD_in_parseRecurrence673);
                            recurr_WD(weekdays, "");

                            state._fsp--;


                                    freq          = RecurrencePatternParser.VAL_WEEKLY_LIT;
                                    interval      = 1;
                                    resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
                                    resolutionVal = join( ",", weekdays );
                                 

                            }
                            break;
                        case 4 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:175:6: (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                            {
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:175:6: (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] )
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:176:9: firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"]
                            {
                            pushFollow(FOLLOW_recurr_Xst_in_parseRecurrence700);
                            firstEntry=recurr_Xst(ints);

                            state._fsp--;

                            pushFollow(FOLLOW_recurr_WD_in_parseRecurrence703);
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

                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:185:4: (until= UNTIL | FOR count= INT )?
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
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:186:9: until= UNTIL
                            {
                            until=(Token)match(input,UNTIL,FOLLOW_UNTIL_in_parseRecurrence743); 

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
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:201:9: FOR count= INT
                            {
                            match(input,FOR,FOLLOW_FOR_in_parseRecurrence763); 
                            count=(Token)match(input,INT,FOLLOW_INT_in_parseRecurrence767); 

                                       res.put( RecurrencePatternParser.OP_COUNT_LIT, Integer.parseInt( (count!=null?count.getText():null) ) );
                                    

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:206:6: EOF
                    {
                    match(input,EOF,FOLLOW_EOF_in_parseRecurrence790); 

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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:217:1: recurr_Xst[Set< Integer > res] returns [int firstEntry] : x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? ;
    public final int recurr_Xst(Set< Integer > res) throws RecognitionException {
        int firstEntry = 0;

        int x = 0;


        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:218:4: (x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )? )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:218:6: x= parse_Xst ( ( ( AND | COMMA ) x= parse_Xst )+ )?
            {
            pushFollow(FOLLOW_parse_Xst_in_recurr_Xst837);
            x=parse_Xst();

            state._fsp--;

             res.add( x ); firstEntry = x; 
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:219:4: ( ( ( AND | COMMA ) x= parse_Xst )+ )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( ((LA16_0>=AND && LA16_0<=COMMA)) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:219:5: ( ( AND | COMMA ) x= parse_Xst )+
                    {
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:219:5: ( ( AND | COMMA ) x= parse_Xst )+
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
                    	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:219:6: ( AND | COMMA ) x= parse_Xst
                    	    {
                    	    if ( (input.LA(1)>=AND && input.LA(1)<=COMMA) ) {
                    	        input.consume();
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }

                    	    pushFollow(FOLLOW_parse_Xst_in_recurr_Xst870);
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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:226:1: recurr_WD[Set< String > weekdays, String Xst] : parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? ;
    public final void recurr_WD(Set< String > weekdays, String Xst) throws RecognitionException {
        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:227:4: ( parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )? )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:227:6: parse_Weekday[weekdays, Xst, true] ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
            {
            pushFollow(FOLLOW_parse_Weekday_in_recurr_WD905);
            parse_Weekday(weekdays, Xst, true);

            state._fsp--;

            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:228:4: ( ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+ )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=AND && LA18_0<=COMMA)) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:228:5: ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
                    {
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:228:5: ( ( AND | COMMA ) parse_Weekday[weekdays, Xst, true] )+
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
                    	    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:228:6: ( AND | COMMA ) parse_Weekday[weekdays, Xst, true]
                    	    {
                    	    if ( (input.LA(1)>=AND && input.LA(1)<=COMMA) ) {
                    	        input.consume();
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }

                    	    pushFollow(FOLLOW_parse_Weekday_in_recurr_WD935);
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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:235:1: recurr_Monthly[Set< String > weekdays,\r\n Set< Integer > ints ] returns [String freq,\r\n String resolution,\r\n String resolutionVal,\r\n int interval,\r\n boolean hasWD] : firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? ;
    public final RecurrenceParser.recurr_Monthly_return recurr_Monthly(Set< String >  weekdays, Set< Integer > ints) throws RecognitionException {
        RecurrenceParser.recurr_Monthly_return retval = new RecurrenceParser.recurr_Monthly_return();
        retval.start = input.LT(1);

        int firstEntry = 0;



              retval.freq     = RecurrencePatternParser.VAL_MONTHLY_LIT;
              retval.interval = 1;
           
        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:246:4: (firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )? )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:246:6: firstEntry= recurr_Xst[ints] ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            {
            pushFollow(FOLLOW_recurr_Xst_in_recurr_Monthly988);
            firstEntry=recurr_Xst(ints);

            state._fsp--;


                    retval.resolution    = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
                    retval.resolutionVal = join( ",", ints );
                 
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:250:8: ( ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )] )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==LAST||(LA20_0>=MONDAY && LA20_0<=WEEKDAY_LIT)) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:251:11: ( LAST )? recurr_WD[weekdays, Integer.toString( firstEntry )]
                    {
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:251:11: ( LAST )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0==LAST) ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:252:14: LAST
                            {
                            match(input,LAST,FOLLOW_LAST_in_recurr_Monthly1025); 

                                            firstEntry = -firstEntry;
                                         

                            }
                            break;

                    }

                    pushFollow(FOLLOW_recurr_WD_in_recurr_Monthly1065);
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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:270:1: parse_Xst returns [int number] : (n= INT ( DOT )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH );
    public final int parse_Xst() throws RecognitionException {
        int number = 0;

        Token n=null;

        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:271:4: (n= INT ( DOT )? | FIRST | ( SECOND | OTHER ) | THIRD | FOURTH | FIFTH )
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
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:271:6: n= INT ( DOT )?
                    {
                    n=(Token)match(input,INT,FOLLOW_INT_in_parse_Xst1121); 
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:271:12: ( DOT )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==DOT) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:271:13: DOT
                            {
                            match(input,DOT,FOLLOW_DOT_in_parse_Xst1124); 

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
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:280:6: FIRST
                    {
                    match(input,FIRST,FOLLOW_FIRST_in_parse_Xst1138); 
                     number = 1; 

                    }
                    break;
                case 3 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:281:6: ( SECOND | OTHER )
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
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:282:6: THIRD
                    {
                    match(input,THIRD,FOLLOW_THIRD_in_parse_Xst1173); 
                     number = 3; 

                    }
                    break;
                case 5 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:283:6: FOURTH
                    {
                    match(input,FOURTH,FOLLOW_FOURTH_in_parse_Xst1193); 
                     number = 4; 

                    }
                    break;
                case 6 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:284:6: FIFTH
                    {
                    match(input,FIFTH,FOLLOW_FIFTH_in_parse_Xst1212); 
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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:295:1: parse_Number returns [int number] : (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN );
    public final int parse_Number() throws RecognitionException {
        int number = 0;

        Token n=null;

        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:296:4: (n= INT | NUM_ONE | NUM_TWO | NUM_THREE | NUM_FOUR | NUM_FIVE | NUM_SIX | NUM_SEVEN | NUM_EIGHT | NUM_NINE | NUM_TEN )
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
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:296:6: n= INT
                    {
                    n=(Token)match(input,INT,FOLLOW_INT_in_parse_Number1270); 
                     number = Integer.parseInt( (n!=null?n.getText():null) ); 

                    }
                    break;
                case 2 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:297:6: NUM_ONE
                    {
                    match(input,NUM_ONE,FOLLOW_NUM_ONE_in_parse_Number1284); 
                     number = 1; 

                    }
                    break;
                case 3 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:298:6: NUM_TWO
                    {
                    match(input,NUM_TWO,FOLLOW_NUM_TWO_in_parse_Number1296); 
                     number = 2; 

                    }
                    break;
                case 4 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:299:6: NUM_THREE
                    {
                    match(input,NUM_THREE,FOLLOW_NUM_THREE_in_parse_Number1308); 
                     number = 3; 

                    }
                    break;
                case 5 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:300:6: NUM_FOUR
                    {
                    match(input,NUM_FOUR,FOLLOW_NUM_FOUR_in_parse_Number1318); 
                     number = 4; 

                    }
                    break;
                case 6 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:301:6: NUM_FIVE
                    {
                    match(input,NUM_FIVE,FOLLOW_NUM_FIVE_in_parse_Number1329); 
                     number = 5; 

                    }
                    break;
                case 7 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:302:6: NUM_SIX
                    {
                    match(input,NUM_SIX,FOLLOW_NUM_SIX_in_parse_Number1340); 
                     number = 6; 

                    }
                    break;
                case 8 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:303:6: NUM_SEVEN
                    {
                    match(input,NUM_SEVEN,FOLLOW_NUM_SEVEN_in_parse_Number1352); 
                     number = 7; 

                    }
                    break;
                case 9 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:304:6: NUM_EIGHT
                    {
                    match(input,NUM_EIGHT,FOLLOW_NUM_EIGHT_in_parse_Number1362); 
                     number = 8; 

                    }
                    break;
                case 10 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:305:6: NUM_NINE
                    {
                    match(input,NUM_NINE,FOLLOW_NUM_NINE_in_parse_Number1372); 
                     number = 9; 

                    }
                    break;
                case 11 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:306:6: NUM_TEN
                    {
                    match(input,NUM_TEN,FOLLOW_NUM_TEN_in_parse_Number1383); 
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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:317:1: parse_Weekday[Set< String > weekdays, String Xst, boolean strict] returns [String weekday] : ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT );
    public final String parse_Weekday(Set< String > weekdays, String Xst, boolean strict) throws RecognitionException {
        String weekday = null;

        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:318:4: ( MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | WEEKEND | WEEKDAY_LIT )
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
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:318:6: MONDAY
                    {
                    match(input,MONDAY,FOLLOW_MONDAY_in_parse_Weekday1433); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_MON ); 

                    }
                    break;
                case 2 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:319:6: TUESDAY
                    {
                    match(input,TUESDAY,FOLLOW_TUESDAY_in_parse_Weekday1447); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_TUE ); 

                    }
                    break;
                case 3 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:320:6: WEDNESDAY
                    {
                    match(input,WEDNESDAY,FOLLOW_WEDNESDAY_in_parse_Weekday1460); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_WED ); 

                    }
                    break;
                case 4 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:321:6: THURSDAY
                    {
                    match(input,THURSDAY,FOLLOW_THURSDAY_in_parse_Weekday1471); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_THU ); 

                    }
                    break;
                case 5 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:322:6: FRIDAY
                    {
                    match(input,FRIDAY,FOLLOW_FRIDAY_in_parse_Weekday1483); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_FRI ); 

                    }
                    break;
                case 6 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:323:6: SATURDAY
                    {
                    match(input,SATURDAY,FOLLOW_SATURDAY_in_parse_Weekday1497); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT ); 

                    }
                    break;
                case 7 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:324:6: SUNDAY
                    {
                    match(input,SUNDAY,FOLLOW_SUNDAY_in_parse_Weekday1509); 
                     weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN ); 

                    }
                    break;
                case 8 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:325:6: WEEKEND
                    {
                    match(input,WEEKEND,FOLLOW_WEEKEND_in_parse_Weekday1523); 

                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT );
                                        weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN );
                                     

                    }
                    break;
                case 9 :
                    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:329:6: WEEKDAY_LIT
                    {
                    match(input,WEEKDAY_LIT,FOLLOW_WEEKDAY_LIT_in_parse_Weekday1536); 

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
    // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:345:1: parse_Month returns [int number] : m= MONTH ;
    public final int parse_Month() throws RecognitionException {
        int number = 0;

        Token m=null;

        try {
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:346:4: (m= MONTH )
            // F:\\CppProjects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\de\\Recurrence.g:346:6: m= MONTH
            {
            m=(Token)match(input,MONTH,FOLLOW_MONTH_in_parse_Month1571); 

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
            return "124:6: ( (interval= parse_Number )? ( DAYS | WEEKS ( ( ON )? recurr_WD[weekdays, \"\"] )? | MONTHS ( ( ON )? r= recurr_Monthly[weekdays, ints] )? | YEARS ( ( ON )? r= recurr_Monthly[weekdays, ints] {...}? => ( ( ON )? m= parse_Month )? )? ) | recurr_Xst[ints] | recurr_WD[weekdays, \"\"] | (firstEntry= recurr_Xst[ints] recurr_WD[weekdays, \"\"] ) )";
        }
    }
 

    public static final BitSet FOLLOW_EVERY_in_parseRecurrence70 = new BitSet(new long[]{0x000007FFFFFC26C0L});
    public static final BitSet FOLLOW_AFTER_in_parseRecurrence76 = new BitSet(new long[]{0x000007FFFFFC26C0L});
    public static final BitSet FOLLOW_parse_Number_in_parseRecurrence97 = new BitSet(new long[]{0x00000000000006C0L});
    public static final BitSet FOLLOW_DAYS_in_parseRecurrence122 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_WEEKS_in_parseRecurrence184 = new BitSet(new long[]{0x000007FC00001902L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence247 = new BitSet(new long[]{0x000007FC00000000L});
    public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence251 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_MONTHS_in_parseRecurrence303 = new BitSet(new long[]{0x0000000000FC3902L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence365 = new BitSet(new long[]{0x0000000000FC2100L});
    public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence371 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_YEARS_in_parseRecurrence423 = new BitSet(new long[]{0x0000000000FC3902L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence486 = new BitSet(new long[]{0x0000000000FC2100L});
    public static final BitSet FOLLOW_recurr_Monthly_in_parseRecurrence492 = new BitSet(new long[]{0x0000080000001902L});
    public static final BitSet FOLLOW_ON_in_parseRecurrence541 = new BitSet(new long[]{0x0000080000000100L});
    public static final BitSet FOLLOW_parse_Month_in_parseRecurrence547 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence658 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence673 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_recurr_Xst_in_parseRecurrence700 = new BitSet(new long[]{0x000007FC00000000L});
    public static final BitSet FOLLOW_recurr_WD_in_parseRecurrence703 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_UNTIL_in_parseRecurrence743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_parseRecurrence763 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_INT_in_parseRecurrence767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_parseRecurrence790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst837 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_set_in_recurr_Xst860 = new BitSet(new long[]{0x0000000000FC2000L});
    public static final BitSet FOLLOW_parse_Xst_in_recurr_Xst870 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD905 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_set_in_recurr_WD927 = new BitSet(new long[]{0x000007FC00000000L});
    public static final BitSet FOLLOW_parse_Weekday_in_recurr_WD935 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_recurr_Xst_in_recurr_Monthly988 = new BitSet(new long[]{0x000007FC00010002L});
    public static final BitSet FOLLOW_LAST_in_recurr_Monthly1025 = new BitSet(new long[]{0x000007FC00000000L});
    public static final BitSet FOLLOW_recurr_WD_in_recurr_Monthly1065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_parse_Xst1121 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_DOT_in_parse_Xst1124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FIRST_in_parse_Xst1138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_parse_Xst1158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_THIRD_in_parse_Xst1173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOURTH_in_parse_Xst1193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FIFTH_in_parse_Xst1212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_parse_Number1270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_ONE_in_parse_Number1284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_TWO_in_parse_Number1296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_THREE_in_parse_Number1308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_FOUR_in_parse_Number1318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_FIVE_in_parse_Number1329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_SIX_in_parse_Number1340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_SEVEN_in_parse_Number1352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_EIGHT_in_parse_Number1362 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_NINE_in_parse_Number1372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_TEN_in_parse_Number1383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONDAY_in_parse_Weekday1433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TUESDAY_in_parse_Weekday1447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEDNESDAY_in_parse_Weekday1460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_THURSDAY_in_parse_Weekday1471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FRIDAY_in_parse_Weekday1483 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SATURDAY_in_parse_Weekday1497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SUNDAY_in_parse_Weekday1509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKEND_in_parse_Weekday1523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKDAY_LIT_in_parse_Weekday1536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTH_in_parse_Month1571 = new BitSet(new long[]{0x0000000000000002L});

}