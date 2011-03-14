lexer grammar DateTimeLexer;

options
{
   language=Java;
   filter=true;
}

@header
{
   package dev.drsoran.moloko.grammar;
}

NEVER     : 'never' | 'nie';

TODAY     : 'today' | 'tod' | 'heute';

TOMORROW  : 'tomorrow' | 'tom' | 'tmr' | 'morgen';

YESTERDAY : 'yesterday' | 'gestern';

AT        : '@' | 'at' | 'um';

ON        : 'on' | 'am' | 'an';

IN        : 'in' | 'im';

OF        : 'of' | 'des';

NEXT      : 'next' | 'nächste'('s'|'r')?;

AND       : 'and' | 'und';

END       : 'end' | 'ende';

THE       : 'the' | 'd'('er'|'ie'|'as');

STs       : 'st' | 'th' | 'rd' | 'nd';

NOW       : 'now' | 'jetzt';

TONIGHT   : 'tonight' | 'ton' | 'heute';

MIDNIGHT  : 'midnight' | 'mitternacht';

MIDDAY    : 'midday' | 'mittag';

NOON      : 'noon' | 'mittag';

YEARS     : 'years' | 'year' | 'yrs' | 'yr' | 'jahr' | 'jahre';

MONTHS    : 'months' | 'month' | 'mons' | 'mon' | 'monat' | 'monate';

WEEKS     : 'weeks' | 'week' | 'wks' | 'wk' | 'woche' | 'wochen';

DAYS      : 'days' | 'day' | 'd' | 'tag' | 'tage';

HOURS     : 'hours' | 'hour' | 'hrs' | 'hr' | 'h' | 'stunden' | 'std';

MINUTES   : 'minutes' | 'minute' | 'mins' | 'min' | 'm' | 'minuten';

SECONDS   : 'seconds' | 'second' | 'secs' | 'sec' | 's' | 'sekunde' | 'sekunden';

MONTH     : 'january'   | 'jan'  | 'januar'   |
            'february'  | 'feb'  | 'februar'  |
            'march'     | 'mar'  | 'märz'     | 'marz' |
            'april'     | 'apr'  |
            'may'       | 'mai'  |
            'june'      | 'jun'  | 'juni'     |
            'july'      | 'jul'  | 'juli'     |
            'august'    | 'aug'  |
            'september' | 'sept' | 'sep'      |
            'october'   | 'oct'  | 'oktober'  | 'okt'  |
            'november'  | 'nov'  |
            'december'  | 'dec'  | 'dezember' | 'dez';

WEEKDAY   : 'monday'    | 'mon' | 'montag'     | 'mo'
            'tuesday'   | 'tue' | 'dienstag'   | 'di'
            'wednesday' | 'wed' | 'mittwoch'   | 'mi'
            'thursday'  | 'thu' | 'donnerstag' | 'do'
            'friday'    | 'fri' | 'freitag'    | 'fr'
            'saturday'  | 'sat' | 'samstag'    | 'sa'
            'sunday'    | 'sun' | 'sonntag'    | 'so';

DATE_SEP  : '/' | '\u5E74' | '\u6708' | '\u65E5';

DOT       : '.';

COLON     : ':';

MINUS     : '-';

MINUS_A   : '-a';

COMMA     : ',';

INT       : '0'..'9'+;

A         : 'a';

AM        :  A('m')? | '\u4E0A' | '\u5348\u524D' | '\uC624\uC804';

PM        : 'p'('m')? | '\u4E0B' | '\u5348\u5F8C' | '\uC624\uD6C4';

NUM_STR   : 'one'  | 'two'  | 'three' | 'four' | 'five'          | 'six'   | 'seven'  | 'eight' | 'nine' | 'ten' |
            'eins' | 'zwei' | 'drei'  | 'vier' | 'fünf' | 'funf' | 'sechs' | 'sieben' | 'acht'  | 'neun' | 'zehn';

fragment
STRING    : ~('"' | ' ' | '(' | ')')+;

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) { $channel=HIDDEN; }
          ;