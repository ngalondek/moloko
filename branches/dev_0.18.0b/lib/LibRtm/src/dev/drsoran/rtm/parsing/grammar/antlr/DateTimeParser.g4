parser grammar DateTimeParser;

options
{
   tokenVocab=DateTimeLexer;
}

parseDateTime    
    : (   date
        | AT? time
        | AT? time COMMA? date
        | date (AT | COMMA)? time
      )
      EOF
    ;

/** Date parsing **/

parseDate
   : date EOF
   ;

parseDateWithin
   : (amountStr=NUM_STR | amount=INT | a=A)
        (d=DAYS | w=WEEKS | m=MONTHS | y=YEARS)?
            (OF date)? EOF
   ;

parseTime
   : AT? time EOF
   ;

parseTimeEstimate
   :((COMMA | AND)? (d=INT DAYS | timeNaturalSpec))+ EOF
   ;

date
   :    dateNumeric
      | dateOn
      | dateIn_X_YMWD
      | dateEndOfTheMonthOrWeek
      | literalDate
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

literalDate
   : TODAY      #Today
   | TONIGHT    #Today
   | NOW        #Now
   | TOMORROW   #Tomorrow
   | YESTERDAY  #Yesterday
   | NEVER      #DateNever
   ;

/** Time parsing **/
    
 time
   : (    literalTime
        | separatedTime
        | unSeparatedTime
        | hmsSeparatedTime
     )
     aMPM?
   ;
   
literalTime
   : NEVER     # TimeNever
   | MIDNIGHT  # Midnight
   | MIDDAY    # Midday
   | NOON      # Midday
   ;

separatedTime
    : separatedTimeHM | separatedTimeHMS
    ;

separatedTimeHM
	: h=INT (COLON|DOT) m=INT (COLON|DOT)?
    ;

separatedTimeHMS
    : h=INT (COLON|DOT) m=INT (COLON|DOT) s=INT
    ;

unSeparatedTime
	: INT
    ;

hmsSeparatedTime
	: timeNaturalSpec+
	;
	
aMPM
   : A  #AM
   | AM #AM
   | PM #PM
   ;

timeNaturalSpec
   : INT (DOT|COMMA) INT HOURS  #TimeNaturalSpecFloatHours
   | INT HOURS                  #TimeNaturalSpecUnitHours
   | INT MINUTES                #TimeNaturalSpecUnitMinutes
   | INT SECONDS                #TimeNaturalSpecUnitSeconds
   ;
   