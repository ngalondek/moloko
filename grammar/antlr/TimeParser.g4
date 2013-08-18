parser grammar TimeParser;

options
{
    tokenVocab=TimeLexer;
}

parseTime
   : (AT | COMMA)?
     (
          literalTime
        | separatedTime
        | unSeparatedTime
        | hmsSeparatedTime
     )
     aMPM?
   ;
   
literalTime
   : NEVER     # Never
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
   : AM #AM
   | PM #PM
   ;

timeNaturalSpec
   : INT (DOT|COMMA) INT HOURS  #TimeNaturalSpecFloatHours
   | INT HOURS                  #TimeNaturalSpecUnitHours
   | INT MINUTES                #TimeNaturalSpecUnitMinutes
   | INT SECONDS                #TimeNaturalSpecUnitSeconds
   ;

parseTimeEstimate
   :((COMMA | AND)? (d=INT DAYS | timeNaturalSpec))+
   ;
