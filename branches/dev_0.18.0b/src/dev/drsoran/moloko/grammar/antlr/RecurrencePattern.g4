grammar RecurrencePattern;

// RULES

parseRecurrencePattern
   : OP_FREQ
     (   patternYearly
       | patternMonthly
       | patternWeekly
       | patternDaily ) (opUntil | opCount)?
   ;
   
patternYearly
    : VAL_YEARLY opInterval (opWeekdays opMonth)?
    ;

patternMonthly
    : VAL_MONTHLY opInterval (opWeekdays | opMonthDay)?
    ;

patternWeekly
    : VAL_WEEKLY opInterval opWeekdays?
    ;

patternDaily
    : VAL_DAILY opInterval
    ;

opInterval
   : OP_INTERVAL i=INT
   ;

opMonth
   : OP_BYMONTH m=INT
   ;

opMonthDay
   : OP_BYMONTHDAY xSt (COMMA xSt)*
   ;

opWeekdays
    : OP_BYDAY firstWeekday (COMMA followingWeekday)*
    ;

opUntil
    : OP_UNTIL date=VAL_DATE
    ;

opCount
    : OP_COUNT count=INT
    ;

firstWeekday
    : xSt? weekday
    ;

followingWeekday
    : INT? weekday
    ;

weekday
    : wd=(   MONDAY
           | TUESDAY
           | WEDNESDAY
           | THURSDAY
           | FRIDAY
           | SATURDAY
           | SUNDAY )
    ;

xSt : x=INT
    ;

// TOKENS

MONDAY        : 'MO';

TUESDAY       : 'TU';

WEDNESDAY     : 'WE';

THURSDAY      : 'TH';

FRIDAY        : 'FR';

SATURDAY      : 'SA';

SUNDAY        : 'SU';

SEMICOLON     : ';' -> skip;

EQUALS        : '=' -> skip;

MINUS         : '-';

COMMA         : ',';

OP_BYDAY      : 'BYDAY';

OP_BYMONTH    : 'BYMONTH';

OP_BYMONTHDAY : 'BYMONTHDAY';

OP_INTERVAL   : 'INTERVAL';

OP_FREQ       : 'FREQ';

OP_UNTIL      : 'UNTIL';

OP_COUNT      : 'COUNT';

VAL_DAILY     : 'DAILY';

VAL_WEEKLY    : 'WEEKLY';

VAL_MONTHLY   : 'MONTHLY';

VAL_YEARLY    : 'YEARLY';

// yyyyMMdd'T'HHmmss
VAL_DATE      :  INT 'T' INT;

fragment
NUMBER        : '0'..'9';

INT           : MINUS? NUMBER+;

WS            : (  ' '
                 | '\t'
                 | '\r'
                 | '\n'
                ) -> skip;

