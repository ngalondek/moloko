// de
lexer grammar RecurrenceLexer;

EVERY         : 'jede'('s'|'r'|'n')? | 'alle';

AFTER         : 'nach';

BIWEEKLY      : DEACTIVATED;

YEARS         : 'jahr'('e'|'en')?;

MONTHS        : 'monat'('e'|'en')?;

WEEKS         : 'woche''n'?;

DAYS          : 'tag'('e'|'en')?;

DAILY         : 't'('a'|'ae'|'ä')'glich';

MONTH         : 'januar'    | 'jan'  | 'februar'  | 'feb' |
                'm'('a'|'ae'|'ä')'rz'| 'm'('a'|'ae'|'ä')'r' |
                'april'     | 'apr'  |
                'mai'       | 'juni' | 'jun' | 'juli'    | 'jul'   | 'august'   | 'aug' |
                'september' | 'sept' | 'sep' | 'oktober' | 'okt'   | 'november' | 'nov' |
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

FIFTH         : 'f'('u'|'ue'|'ü')'nft'('e'|'s'|'r');

LAST          : 'letzt'('e'|'s'|'r'|'en'|'em');

OTHER         : 'ander'('e'|'es'|'er'|'en');

ST_S          : 'st' | 'nd' | 'rd' | 'th';

NUM_ONE       : 'ein'('s'|'e'|'er'|'em'|'en'|'es')?;

NUM_TWO       : 'zwei';

NUM_THREE     : 'drei';

NUM_FOUR      : 'vier';

NUM_FIVE      : 'f'('u'|'ue'|'ü')'nf';

NUM_SIX       : 'sechs';

NUM_SEVEN     : 'sieben';

NUM_EIGHT     : 'acht';

NUM_NINE      : 'neun';

NUM_TEN       : 'zehn';

AND           : 'und';

IN            : 'in' | 'im';

ON            : 'a'('m'|'n') | 'de'('s'|'r') | 'vo'('n'|'m');

OF            : DEACTIVATED;

THE           : DEACTIVATED;

UNTIL         : 'bis' -> pushMode(MODE_UNTIL);

FOR           : 'f'('u'|'ue'|'ü')'r';

TIMES         : 'mal';

DOT           : '.';

MINUS         : '-';

COMMA         : ',';

fragment
NUMBER        : '0'..'9';

INT           : MINUS? NUMBER+;

fragment
DEACTIVATED   : '***?***';

WS            : (  ' '
                 | '\t'
                 | '\r'
                 | '\n'
                ) ->skip;

mode MODE_UNTIL;
STRING        : (.)+ ->popMode;

