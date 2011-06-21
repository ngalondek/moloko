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

@members
{
   public DateLexer()
   {
      super( null );
   }
}

// TOKENS

A         : 'a';

OF        : 'of';

ON        : 'on';

IN        : 'in';

AND       : 'and';

END       : 'end';

THE       : 'the';

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

MONTH     : 'january'   | 'jan'  | 'february' | 'feb'     | 'march' | 'mar'      | 'april' | 'apr' |
            'may'       | 'june' | 'jun'      | 'july'    | 'jul'   | 'august'   | 'aug'   |
            'september' | 'sept' | 'sep'      | 'october' | 'oct'   | 'november' | 'nov'   |
            'december'  | 'dec';

WEEKDAY   : 'monday'   | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' |
            'thursday' | 'thu' | 'friday'  | 'fri' | 'saturday'  | 'sat' |
            'sunday'   | 'sun';

NUM_STR   : 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten';

DATE_SEP  : ~('0'..'9')+;

INT       : '0'..'9'+;

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) { $channel=HIDDEN; }
          ;