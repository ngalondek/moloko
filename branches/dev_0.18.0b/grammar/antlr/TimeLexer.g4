lexer grammar TimeLexer;

AT        : '@' | 'at';

AND       : 'and';

NEVER     : 'never';

MIDNIGHT  : 'midnight';

MIDDAY    : 'midday';

NOON      : 'noon';

DAYS      : 'days' | 'day' | 'd';

HOURS     : 'hours' | 'hour' | 'hrs' | 'hr' | 'h';

MINUTES   : 'minutes' | 'minute' | 'mins' | 'min' | 'm';

SECONDS   : 'seconds' | 'second' | 'secs' | 'sec' | 's';

AM        : 'a'('m')? | '\u4E0A' | '\u5348\u524D' | '\uC624\uC804';

PM        : 'p'('m')? | '\u4E0B' | '\u5348\u5F8C' | '\uC624\uD6C4';

COMMA     : ',';

DOT       : '.';

COLON     : ':';

fragment
NUMBER    : '0'..'9';

INT       : NUMBER+;

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) -> skip
          ;
