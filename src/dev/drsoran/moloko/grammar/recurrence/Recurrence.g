// en
grammar Recurrence;

options
{
   language=Java;
   superClass=AbstractRecurrenceParser;
}

@header
{
   package dev.drsoran.moloko.grammar.recurrence;

   import java.util.Map;
   import java.util.Locale;
   import java.util.Set;

   import dev.drsoran.moloko.grammar.LexerException;
   import dev.drsoran.moloko.grammar.recurrence.AbstractRecurrenceParser;
   import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;
}

@lexer::header
{
   package dev.drsoran.moloko.grammar.recurrence;
   
   import dev.drsoran.moloko.grammar.LexerException;
}


@members
{
   public RecurrenceParser()
   {
      super( null );
   }

   public final static Locale LOCALE = Locale.ENGLISH;  
}

@lexer::members
{
   @Override
   public void reportError( RecognitionException e )
   {
      throw new LexerException( e );
   }
}

// RULES

parseRecurrence returns[Map< String, Object > res]
   @init
   {
   	res = startParseRecurrence();
   }
   @after
   {
      res = finishedParseRecurrence();
   }
   : (EVERY { isEvery = Boolean.TRUE; } | AFTER)?
     (        
        parse_Interval_Number_or_Text?
        (
            DAYS                                               { freq = RecurrencePatternParser.VAL_DAILY_LIT;  }
          | (
                 WEEKS
               | BIWEEKLY
                 {
                    interval = 2;
                 }
             )                                                 { freq = RecurrencePatternParser.VAL_WEEKLY_LIT; }
             (ON? THE? recurr_WD[weekdays, ""]
                   {
                      resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
                      resolutionVal = join( ",", weekdays );
                   }
             )?
          | MONTHS                                             { freq = RecurrencePatternParser.VAL_MONTHLY_LIT;}
             (ON? THE? r=recurr_Monthly[weekdays, ints]
                       {
                          freq          = r.freq;
                          interval      = r.interval;
                          resolution    = r.resolution;
                          resolutionVal = r.resolutionVal;
                       }
             )?
          | YEARS                                              { freq = RecurrencePatternParser.VAL_YEARLY_LIT; }
             (ON? THE? r=recurr_Monthly[weekdays, ints]
                       {
                          freq          = r.freq;
                          interval      = r.interval;
                          resolution    = r.resolution;
                          resolutionVal = r.resolutionVal;
                       }
                       { r.hasWD }?=> (
                                        (IN | OF)?
                                         m=parse_Month
                                         {
                                            freq     = RecurrencePatternParser.VAL_YEARLY_LIT;
                                            interval = 1;
                                            res.put( RecurrencePatternParser.OP_BYMONTH_LIT, Integer.toString( m ) );
                                         }
                                      )?
       )?
   )
   | recurr_Xst[ints]
     {
        freq          = RecurrencePatternParser.VAL_MONTHLY_LIT;
        interval      = 1;
        resolution    = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
        resolutionVal = join( ",", ints );
     }
   | recurr_WD[weekdays, ""]
     {
        freq          = RecurrencePatternParser.VAL_WEEKLY_LIT;
        interval      = 1;
        resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
        resolutionVal = join( ",", weekdays );
     }
   | (
        firstEntry=recurr_Xst[ints] recurr_WD[weekdays, ""]
        {
           freq          = RecurrencePatternParser.VAL_WEEKLY_LIT;
           interval      = firstEntry;
           resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
           resolutionVal = join( ",", weekdays );
        }
     )
   )
   (
        until=UNTIL
        {
           setUntil( $until.text.replaceFirst( "until\\s*", "" ) );
        }
      | FOR count=INT
        {
           res.put( RecurrencePatternParser.OP_COUNT_LIT, Integer.parseInt( $count.text ) );
        }
   )?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }
   catch [ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }
   catch [ LexerException e ]
   {
      throw new RecognitionException();
   }

recurr_Xst [Set< Integer > res] returns [int firstEntry]
   : x=parse_Xst               { res.add( x ); firstEntry = x; }
   (((AND | COMMA) x=parse_Xst { res.add( x ); })+)?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

recurr_WD [Set< String > weekdays, String Xst]
   : parse_Weekday              [weekdays, Xst, true]
   (((AND | COMMA) parse_Weekday[weekdays, Xst, true])+)?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

recurr_Monthly [Set< String >  weekdays,
                Set< Integer > ints     ] returns [String  freq,
                                                   String  resolution,
                                                   String  resolutionVal,
                                                   int     interval,
                                                   boolean hasWD]
   @init
   {
      retval.freq     = RecurrencePatternParser.VAL_MONTHLY_LIT;
      retval.interval = 1;
   }
   : firstEntry = recurr_Xst[ints]
     {
        retval.resolution    = RecurrencePatternParser.OP_BYMONTHDAY_LIT;
        retval.resolutionVal = join( ",", ints );
     } (
          (
             LAST
             {
                firstEntry = -firstEntry;
             }
          )?
          recurr_WD[weekdays, Integer.toString( firstEntry )]
          {
             retval.hasWD         = true;
             retval.resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
             retval.resolutionVal = join( ",", weekdays );
          }
       )?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

parse_Xst returns [int number]
   : n=INT (DOT|ST_S)?
   {
      number = limitMonthDay( Integer.parseInt( $n.text ) );
   }
   | FIRST            { number = 1; }
   | (SECOND | OTHER) { number = 2; }
   | THIRD            { number = 3; }
   | FOURTH           { number = 4; }
   | FIFTH            { number = 5; }
   ;
   catch [ RecognitionException e ]
   {
      return 1;
   }
   catch [ NumberFormatException nfe ]
   {
      return 1;
   }

parse_Interval_Number_or_Text
   : n=INT      { interval = Integer.parseInt( $n.text ); }
   | NUM_ONE    { interval = 1; }
   | NUM_TWO    { interval = 2; }
   | NUM_THREE  { interval = 3; }
   | NUM_FOUR   { interval = 4; }
   | NUM_FIVE   { interval = 5; }
   | NUM_SIX    { interval = 6; }
   | NUM_SEVEN  { interval = 7; }
   | NUM_EIGHT  { interval = 8; }
   | NUM_NINE   { interval = 9; }
   | NUM_TEN    { interval = 10; }
   ;
   catch [ RecognitionException e ]
   {
      interval = 1;
   }
   catch [ NumberFormatException nfe ]
   {
      interval = 1;
   }

parse_Weekday [Set< String > weekdays, String Xst, boolean strict] returns [String weekday]
   : MONDAY      { weekdays.add( Xst + RecurrencePatternParser.BYDAY_MON ); }
   | TUESDAY     { weekdays.add( Xst + RecurrencePatternParser.BYDAY_TUE ); }
   | WEDNESDAY   { weekdays.add( Xst + RecurrencePatternParser.BYDAY_WED ); }
   | THURSDAY    { weekdays.add( Xst + RecurrencePatternParser.BYDAY_THU ); }
   | FRIDAY      { weekdays.add( Xst + RecurrencePatternParser.BYDAY_FRI ); }
   | SATURDAY    { weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT ); }
   | SUNDAY      { weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN ); }
   | WEEKEND     {
                    weekdays.add( Xst + RecurrencePatternParser.BYDAY_SAT );
                    weekdays.add( Xst + RecurrencePatternParser.BYDAY_SUN );
                 }
   | WEEKDAY_LIT {
                    weekdays.add( Xst + RecurrencePatternParser.BYDAY_MON );
                    weekdays.add( Xst + RecurrencePatternParser.BYDAY_TUE );
                    weekdays.add( Xst + RecurrencePatternParser.BYDAY_WED );
                    weekdays.add( Xst + RecurrencePatternParser.BYDAY_THU );
                    weekdays.add( Xst + RecurrencePatternParser.BYDAY_FRI );
                 }
   ;
   catch [ RecognitionException e ]
   {
      if ( strict )
         throw e;
      else
         return null;
   }

parse_Month returns [int number]
   : m=MONTH
   {
      number = textMonthToMonthNumber( $m.text, LOCALE );
   }
   ;
   catch [ RecognitionException e ]
   {
      return 1;
   }

// TOKENS

EVERY         : 'every' | 'each';

AFTER         : 'after';

BIWEEKLY      : 'fortnight' | 'biweekly';

YEARS         : 'years' | 'year' | 'yrs' | 'yr';

MONTHS        : 'months' | 'month' | 'mons' | 'mon';

WEEKS         : 'weeks' | 'week' | 'wks' | 'wk';

DAYS          : 'days' | 'day';

MONTH         : 'january'   | 'jan'  | 'february' | 'feb'     | 'march' | 'mar'      | 'april' | 'apr' |
                'may'       | 'june' | 'jun'      | 'july'    | 'jul'   | 'august'   | 'aug'   |
                'september' | 'sept' | 'sep'      | 'october' | 'oct'   | 'november' | 'nov'   |
                'december'  | 'dec';

WEEKDAY_LIT   : 'weekday''s'?;

WEEKEND       : 'weekend''s'?;

MONDAY        : 'monday'    | 'mon';

TUESDAY       : 'tuesday'   | 'tue';

WEDNESDAY     : 'wednesday' | 'wed';

THURSDAY      : 'thursday'  | 'thu';

FRIDAY        : 'friday'    | 'fri';

SATURDAY      : 'saturday'  | 'sat';

SUNDAY        : 'sunday'    | 'sun';

FIRST         : 'first';

SECOND        : 'second';

THIRD         : 'third';

FOURTH        : 'fourth';

FIFTH         : 'fifth';

LAST          : 'last';

OTHER         : 'other';

ST_S          : 'st' | 'nd' | 'rd' | 'th';

NUM_ONE       : 'one' | 'a';

NUM_TWO       : 'two';

NUM_THREE     : 'three';

NUM_FOUR      : 'four';

NUM_FIVE      : 'five';

NUM_SIX       : 'six';

NUM_SEVEN     : 'seven';

NUM_EIGHT     : 'eight';

NUM_NINE      : 'nine';

NUM_TEN       : 'ten';

AND           : 'and';

IN            : 'in';

ON            : 'on';

OF            : 'of';

THE           : 'the';

UNTIL         : 'until' STRING;

FOR           : 'for';

TIMES         : 'times';

DOT           : '.';

MINUS         : '-';

COMMA         : ',';

fragment
NUMBER        : '0'..'9';

INT           : MINUS? NUMBER+;

fragment
STRING        : (' '|.)+;

WS            : (  ' '
                 | '\t'
                 | '\r'
                 | '\n'
                ) { $channel=HIDDEN; };
