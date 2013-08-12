parser grammar RecurrenceParser;

options
{
    tokenVocab=RecurrenceLexer;
}

parseRecurrenceSentence
   : everyAfter? (intervalNumberOrText? (  recurrDaily
                                         | recurrWeekly
                                         | recurrMonthly
                                         | recurrYearly)) (until | repeat)?
   ;

recurrDaily
    : DAYS | DAILY
    ;

recurrWeekly
    : (WEEKS | biweekly) recurrWeeklyOnWeekdays?
    | recurrWeeklyOnWeekdays
    ;

recurrWeeklyOnWeekdays
    : ON? THE? weekdays
    ;

recurrMonthly
    : MONTHS recurrMonthlyOnWeekdays?
    | recurrMonthlyOnWeekdays
    | recurrMonthlyOnXst
    ;

recurrMonthlyOnWeekdays
    : ON? THE? recurrOnWeekdays
    ;

recurrMonthlyOnXst
   : ON? THE? multipleXst
   ;

recurrYearly
    : YEARS (ON? THE? recurrOnWeekdays (IN | OF)? month)?
    ;

recurrOnWeekdays
    : multipleXst LAST? weekdays
    ;

everyAfter
    : EVERY | AFTER
    ;

until
    : UNTIL s=STRING
    ;

repeat
    : FOR c=INT
    ;

multipleXst
    : xst (((AND | COMMA) xst)+)?
    ;

xst
   : INT (DOT | ST_S)?
   | (  FIRST
      | SECOND
      | OTHER
      | THIRD
      | FOURTH
      | FIFTH)
   ;

intervalNumberOrText
    : intervalNumber | intervalText
    ;

intervalNumber
   : n=INT
   ;

intervalText
   : NUM_ONE
   | NUM_TWO
   | NUM_THREE
   | NUM_FOUR
   | NUM_FIVE
   | NUM_SIX
   | NUM_SEVEN
   | NUM_EIGHT
   | NUM_NINE
   | NUM_TEN
   ;

biweekly
    : BIWEEKLY
    ;

weekdays
   : weekday (((AND | COMMA) weekday)+)?
   ;

weekday
   : (   MONDAY
       | TUESDAY
       | WEDNESDAY
       | THURSDAY
       | FRIDAY
       | SATURDAY
       | SUNDAY)   
   | buisinessDays
   | weekend
   ;

buisinessDays
    : WEEKDAY_LIT
    ;

weekend
    : WEEKEND
    ;

month
   : m=MONTH
   ;