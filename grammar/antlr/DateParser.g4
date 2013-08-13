parser grammar DateParser;

options
{
   tokenVocab=DateLexer;
}

/** RULES **/

parseDate
   :    dateNumeric
      | dateOn
      | dateIn_X_YMWD
      | dateEndOfTheMonthOrWeek
      | date_literal
   ;

parseDateWithin
   : (amountStr=NUM_STR | amount=INT | a=A)
        (d=DAYS | w=WEEKS | m=MONTHS | y=YEARS)?
            (OF parseDate)?
   ;

dateNumeric
   : pt1=INT
        (DOT | MINUS | DATE_SEP) pt2=INT
            ((DOT | MINUS | DATE_SEP) pt3=INT?)?
   ;

dateOn
   : ON? (dateOnTheXstOfMonth | dateOnMonthTheXst | dateOnWeekday)
   ;

dateOnTheXstOfMonth
   : d=INT STs?
   | d=INT STs? (OF | MINUS_A | MINUS | COMMA | DOT) m=MONTH
   | d=INT STs? (OF | MINUS_A | MINUS | COMMA | DOT) m=MONTH (MINUS | DOT) y=INT
   ;

dateOnMonthTheXst
   : m=MONTH
   | m=MONTH (MINUS | COMMA | DOT) d=INT (STs | MINUS | COMMA | DOT)?
   | m=MONTH (MINUS | COMMA | DOT) d=INT (STs | MINUS | COMMA | DOT)? y=INT
   ;

dateOnWeekday
   : n=NEXT? wd=WEEKDAY
   ;

dateIn_X_YMWD
   :  IN? dateIn_X_YMWD_distance ((AND | COMMA) dateIn_X_YMWD_distance)*
   ;

dateIn_X_YMWD_distance
   : (amountStr=NUM_STR | amount=INT) (y=YEARS | m=MONTHS | w=WEEKS | d=DAYS)
   ;

dateEndOfTheMonthOrWeek
   : END OF? THE? (w=WEEKS | m=MONTHS)
   ;

date_literal
   : TODAY      #Today
   | TONIGHT    #Today
   | NOW        #Now
   | TOMORROW   #Tomorrow
   | YESTERDAY  #Yesterday
   | NEVER      #Never
   ;