lexer grammar DateTimeLexer;

options
{
	language=Java;
}

@header
{
	package dev.drsoran.moloko.grammar;
}

NEVER     : 'never';

TODAY     : 'today' | 'tod';

TOMORROW  : 'tomorrow' | 'tom' | 'tmr';

YESTERDAY : 'yesterday';

AT        : '@' | 'at';

ON        : 'on';

IN        : 'in';

OF        : 'of';

NEXT      : 'next';

AND       : 'and';

END       : 'end';

THE       : 'the';

STs       : 'st' | 'th' | 'rd' | 'nd';

NOW       : 'now';

TONIGHT   : 'tonight' | 'ton';

MIDNIGHT  : 'midnight';

MIDDAY    : 'midday';

NOON      : 'noon';

YEARS     : 'years' | 'year' | 'yrs' | 'yr';

MONTHS    : 'months' | 'month' | 'mons' | 'mon';

WEEKS     : 'weeks' | 'week' | 'wks' | 'wk';

DAYS      : 'days' | 'day' | 'd';

HOURS     : 'hours' | 'hour' | 'hrs' | 'hr' | 'h';

MINUTES   : 'minutes' | 'minute' | 'mins' | 'min' | 'm';

SECONDS   : 'seconds' | 'second' | 'secs' | 'sec' | 's';

MONTH     : 'january'   | 'jan'  | 'february' | 'feb'     | 'march' | 'mar'      | 'april' | 'apr' |
            'may'       | 'june' | 'jun'      | 'july'    | 'jul'   | 'august'   | 'aug'   |
            'september' | 'sept' | 'sep'      | 'october' | 'oct'   | 'november' | 'nov'   |
            'december'  | 'dec';

WEEKDAY   : 'monday'   | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' |
            'thursday' | 'thu' | 'friday'  | 'fri' | 'saturday'  | 'sat' |
            'sunday'   | 'sun';

DATE_SEP  : '/' | '\u5E74' | '\u6708' | '\u65E5';

DOT       : '.';

COLON     : ':';

MINUS     : '-';

MINUS_A   : '-a';

COMMA     : ',';

INT       : '0'..'9'+;

AM			 : 'a'('m')? | '\u4E0A' | '\u5348\u524D' | '\uC624\uC804';

PM			 : 'p'('m')? | '\u4E0B' | '\u5348\u5F8C' | '\uC624\uD6C4';

NUM_STR   : 'one' | 'two' | 'three' | 'four' | 'six' | 'seven' | 'eight' | 'nine' | 'ten';

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) { $channel=HIDDEN; }
          ;