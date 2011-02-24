lexer grammar RecurrenceLexer;

@option
{
   language=Java;
}

@header
{
   package dev.drsoran.moloko.grammar;
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

MONDAY        : 'monday'    | 'mon' | 'mo';

TUESDAY       : 'tuesday'   | 'tue' | 'tu';

WEDNESDAY     : 'wednesday' | 'wed' | 'we';

THURSDAY      : 'thursday'  | 'thu' | 'th';

FRIDAY        : 'friday'    | 'fri' | 'fr';

SATURDAY      : 'saturday'  | 'sat' | 'sa';

SUNDAY        : 'sunday'    | 'sun' | 'su';

FIRST         : 'first';

SECOND        : 'second';

THIRD         : 'third';

FOURTH        : 'fourth';

FIFTH         : 'fifth';

LAST          : 'last';

OTHER         : 'other';

ST_S          : 'st' | 'nd' | 'rd' | 'th';

NUM_ONE       : 'one';

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

