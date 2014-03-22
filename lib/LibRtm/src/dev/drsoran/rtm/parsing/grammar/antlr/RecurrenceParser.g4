parser grammar RecurrenceParser;

options
{
    tokenVocab=RecurrenceLexer;
}

parseRecurrenceSentence
   : everyAfter? intervalNumberOrText?
        (freqSentence | freqConstant)
            (until | count)?
   ;

freqSentence
    : freq
    | freq? ON? THE? (   recurrOnXst
                       | recurrOnWeekdays
                       | recurrOnXstWeekdays
                       | recurrOnXstWeekdaysOfMonth)
    ;

freqConstant
    : DAILY    #daily
    | BIWEEKLY #biWeekly
    ;

freq
    : DAYS     #freqDaily
    | WEEKS    #freqWeekly
    | MONTHS   #freqMonthly
    | YEARS    #freqYearly
    ;

recurrOnXst
   : multipleXst
   ;

recurrOnWeekdays
    : weekdays
    ;

recurrOnXstWeekdays
    : multipleXstWeekdays
    | lastWeekdays
    ;

recurrOnXstWeekdaysOfMonth
    : multipleXstWeekdays (IN | OF)? month
    ;

multipleXstWeekdays
    : multipleXst LAST? weekdays   
    ;

lastWeekdays
    : LAST weekdays
    ;

weekdays
   : weekday (((AND | COMMA) weekday)+)?
   ;

everyAfter
    : EVERY | AFTER
    ;

until
    : UNTIL s=STRING
    ;

count
    : FOR c=INT TIMES?
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