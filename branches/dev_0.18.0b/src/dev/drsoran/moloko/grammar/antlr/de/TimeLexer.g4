lexer grammar TimeLexer;

AT        : '@' | 'um' | 'am' | 'an';

AND       : 'und';

NEVER     : 'nie';

MIDNIGHT  : 'mitternacht''s'?;

MIDDAY    : 'mittag''s'?;

NOON      : MIDDAY;

DAYS      : 'tage' | 'tag' | 'd';

HOURS     : 'stunden'  | 'stunde'  | 'std'         | 'h';

MINUTES   : 'minuten'  | 'minute'  | 'min'         | 'm';

SECONDS   : 'sekunden' | 'sekunde' | 'se'('c'|'k') | 's';

AM        : 'vorm'DOT? | 'vormittag'('s')?;

PM        : 'nachm'DOT? | 'nachmittag'('s')?;

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