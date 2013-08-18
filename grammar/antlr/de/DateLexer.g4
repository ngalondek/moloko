// de
lexer grammar DateLexer;

/*
   TODO:
    - missing time separators: \u0020\u0068\u0020|\u6642|h
*/

A         : 'ein'('e'|'er'|'es'|'em'|'en')?;

ON        :	'am' | 'an';

OF        : 'von' | 'vom' | 'ab' | 'seit' | 'des';	

THE       : 'der' | 'die' | 'das' | 'dem' | 'den';

IN        : 'in';

AND       : 'und';

END       : 'ende';

NOW       : 'jetzt';

TONIGHT   : DEACTIVATED;

NEVER     : 'nie';

TODAY     : 'heute';

TOMORROW  : 'morgen' | 'mrg';

YESTERDAY : 'gestern';

NEXT      : 'n'('a'|'ae'|'ä')'chst'('e'|'er'|'es'|'en'|'em');

STs       : DEACTIVATED;

YEARS     : 'jahr'SUFF_MALE?;

MONTHS    : 'monat'SUFF_MALE?;

WEEKS     : 'woche'SUFF_FMALE?;

DAYS      : 'tag'SUFF_MALE?;

MONTH     :   'januar'SUFF_MALE?              | 'jan'
            | 'februar'SUFF_MALE?             | 'feb'
            | 'm'('a'|'ä'|'ae')'rz'SUFF_MALE? | 'm'('a'|'ä'|'ae')'r'
            | 'april'SUFF_MALE?               | 'apr'
            | 'mai'SUFF_MALE?
            | 'juni'SUFF_MALE?                | 'jun'
            | 'juli'SUFF_MALE?                | 'jul'
            | 'august'SUFF_MALE?              | 'aug'
            | 'september'SUFF_MALE?           | 'sep'
            | 'oktober'SUFF_MALE?             | 'okt'
            | 'november'SUFF_MALE?            | 'nov'
            | 'dezember'SUFF_MALE?            | 'dez'
          ;

WEEKDAY   :    'montag'SUFF_MALE?     | 'mo'
             | 'dienstag'SUFF_MALE?   | 'di'
             | 'mittwoch'SUFF_MALE?   | 'mi'
             | 'donnerstag'SUFF_MALE? | 'do'
             | 'freitag'SUFF_MALE?    | 'fr'
             | 'samstag'SUFF_MALE?    | 'sa'
             | 'sonntag'SUFF_MALE?    | 'so'
          ;

NUM_STR   : 'eins'  | 'zwei'   | 'drei' | 'vier' | 'f'('u'|'ü'|'ue')'nf' |
            'sechs' | 'sieben' | 'acht' | 'neun' | 'zehn';

DATE_SEP  : '/' | '\u5E74' | '\u6708' | '\u65E5';

DOT       : '.';

MINUS     : '-';

MINUS_A   : DEACTIVATED;

COMMA     : ',';

fragment
SUFF_MALE  : 'e'|'s'|'es'|'en';

fragment
SUFF_FMALE : 'n';

INT       : '0'..'9'+;

fragment
DEACTIVATED : '***?***';

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) ->skip
          ;