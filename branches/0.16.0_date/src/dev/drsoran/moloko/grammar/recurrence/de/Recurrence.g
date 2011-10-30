// de
grammar Recurrence;

options
{
   language=Java;
   superClass=AbstractRecurrenceParser;
}

@header
{
   package dev.drsoran.moloko.grammar.recurrence.de;

   import java.util.Map;
   import java.util.Locale;
   import java.util.Set;

   import dev.drsoran.moloko.grammar.LexerException;
   import dev.drsoran.moloko.grammar.recurrence.AbstractRecurrenceParser;
   import dev.drsoran.moloko.grammar.recurrence.RecurrencePatternParser;
}

@lexer::header
{
   package dev.drsoran.moloko.grammar.recurrence.de;
   
   import dev.drsoran.moloko.grammar.LexerException;
}

@members
{
   public RecurrenceParser()
   {
      super( null );
   }

   public final static Locale LOCALE = Locale.GERMAN;
   
   protected String getUntilLiteral()
   {
      return "bis";
   }
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
          | WEEKS                                              { freq = RecurrencePatternParser.VAL_WEEKLY_LIT; }
             (ON?  recurr_WD[weekdays, ""]
                   {
                      resolution    = RecurrencePatternParser.OP_BYDAY_LIT;
                      resolutionVal = join( ",", weekdays );
                   }
             )?
          | MONTHS                                             { freq = RecurrencePatternParser.VAL_MONTHLY_LIT;}
             (ON?  r=recurr_Monthly[weekdays, ints]
                   {
                      freq          = r.freq;
                      interval      = r.interval;
                      resolution    = r.resolution;
                      resolutionVal = r.resolutionVal;
                   }
             )?
          | YEARS                                              { freq = RecurrencePatternParser.VAL_YEARLY_LIT; }
             (ON?  r=recurr_Monthly[weekdays, ints]
                   {
                      freq          = r.freq;
                      interval      = r.interval;
                      resolution    = r.resolution;
                      resolutionVal = r.resolutionVal;
                   }
                   { r.hasWD }?=> ( (ON)? m=parse_Month
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
           setUntil( $until.text.replaceFirst( "bis\\s*", "" ) );
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
   catch [ LexerException nfe ]
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
   : n=INT (DOT)?
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

EVERY         : 'jede'('s'|'r'|'n')? | 'alle';

AFTER         : 'nach';

YEARS         : 'jahr'('e'|'en')?;

MONTHS        : 'monat'('e'|'en')?;

WEEKS         : 'woche''n'?;

DAYS          : 'tag'('e'|'en')?;

MONTH         : 'januar'    | 'jan'  | 'februar'  | 'feb'     | 'märz'  | 'april'    | 'apr'   |
                'mai'       | 'juni' | 'jun'      | 'juli'    | 'jul'   | 'august'   | 'aug'   |
                'september' | 'sept' | 'sep'      | 'oktober' | 'okt'   | 'november' | 'nov'   |
                'dezember'  | 'dez';

WEEKDAY_LIT   : 'wochentag''s'?;

WEEKEND       : 'wochenende''n'?;

MONDAY        : 'montag''s'?     | 'mo';

TUESDAY       : 'dienstag''s'?   | 'di';

WEDNESDAY     : 'mittwoch''s'?   | 'mi';

THURSDAY      : 'donnerstag''s'? | 'do';

FRIDAY        : 'freitag''s'?    | 'fr';

SATURDAY      : 'samstag''s'?    | 'sa';

SUNDAY        : 'sonntag''s'?    | 'so';

FIRST         : 'erst'('e'|'s'|'r');

SECOND        : 'zweit'('e'|'s'|'r');

THIRD         : 'dritt'('e'|'s'|'r');

FOURTH        : 'viert'('e'|'s'|'r');

FIFTH         : 'fünft'('e'|'s'|'r');

LAST          : 'letzt'('e'|'s'|'r'|'en'|'em');

OTHER         : 'ander'('e'|'es'|'er'|'en');

NUM_ONE       : 'ein'('s'|'e'|'er'|'em'|'en'|'es')?;

NUM_TWO       : 'zwei';

NUM_THREE     : 'frei';

NUM_FOUR      : 'vier';

NUM_FIVE      : 'fünf';

NUM_SIX       : 'sechs';

NUM_SEVEN     : 'sieben';

NUM_EIGHT     : 'acht';

NUM_NINE      : 'neun';

NUM_TEN       : 'zehn';

AND           : 'und';

ON            : 'a'('m'|'n') | 'i'('n'|'m') | 'de'('s'|'r') | 'vo'('n'|'m');

UNTIL         : 'bis' STRING;

FOR           : 'für';

TIMES         : 'mal';

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
