// en
lexer grammar DateTimeLexer;

/*
   TODO:
    - missing time separators: \u0020\u0068\u0020|\u6642|h
*/

AT        : '@' | 'at';

A         : 'a';

ON        : 'on';

OF        : 'of';

THE       : 'the';

IN        : 'in';

AND       : 'and';

END       : 'end';

NOW       : 'now';

TONIGHT   : 'tonight' | 'ton';

NEVER     : 'never';

TODAY     : 'today' | 'tod';

TOMORROW  : 'tom' | 'tmr' | 'tomorrow' | 'tommorow';

YESTERDAY : 'yesterday';

MIDNIGHT  : 'midnight';

MIDDAY    : 'midday';

NOON      : 'noon';

NEXT      : 'next';

STs       : 'st' | 'th' | 'rd' | 'nd';

YEARS     : 'years' | 'year' | 'yrs' | 'yr';

MONTHS    : 'months' | 'month' | 'mons';

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

NUM_STR   : 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten';

DATE_SEP  : '/' | '\u5E74' | '\u6708' | '\u65E5';

AM        : 'am'  | '\u4E0A' | '\u5348\u524D' | '\uC624\uC804';

PM        : 'p'('m')? | '\u4E0B' | '\u5348\u5F8C' | '\uC624\uD6C4';

DOT       : '.';

COLON     : ':';

MINUS     : '-';

MINUS_A    : '-a' WS+;

COMMA     : ',';

INT       : '0'..'9'+;

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) ->skip
          ;          