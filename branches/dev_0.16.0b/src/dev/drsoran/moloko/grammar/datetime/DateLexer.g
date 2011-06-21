// en

lexer grammar DateLexer;

/*
   TODO:
    - missing time separators: \u0020\u0068\u0020|\u6642|h
*/
options
{
   language=Java;
   filter=true;
}

@header
{
   package dev.drsoran.moloko.grammar.datetime;

   import dev.drsoran.moloko.grammar.LexerException;
}

// TOKENS

A         : 'a';

OF        : 'of';

AND       : 'and';

END       : 'end';

NOW       : 'now';

TONIGHT   : 'ton';

NEVER     : 'never';

TODAY     : 'tod';

TOMORROW  : 'tom' | 'tmr';

YESTERDAY : 'yesterday';

NEXT      : 'next';

YEARS     : 'year' | 'yr';

MONTHS    : 'month' | 'mon';

WEEKS     : 'week' | 'wk';

DAYS      : 'day' | 'd';

MONTH     : 'jan' | 'feb' | 'mar' | 'apr' | 'may' | 'jun' | 'jul' | 'aug' |
            'sep' | 'oct' | 'nov' | 'dec';

WEEKDAY   : 'mo' | 'tu' | 'we' | 'th' | 'fri' | 'sa' | 'su';

NUM_STR   : 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten';

INT       : '0'..'9'+;

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) { $channel=HIDDEN; }
          ;