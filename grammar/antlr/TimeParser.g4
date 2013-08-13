parser grammar TimeParser;

options
{
    tokenVocab=TimeLexer;
}

/*
   This parses time in the format:
      - @|at|,? [0-9]+ :|. [0-9]+ (:|. [0-9]+) am|pm?
      - @|at|,? [0-9]{3,4] am|pm?
      - @|at|,? [0-9]{1,2] am|pm?
      - @|at|,? [0-9]+ h|m|s ([0-9]+ h|m|s ([0-9]+ h|m|s)?)? am_pm? ,?

*/
// adjustDay - if this parameter is false, the parser
// assumes that the given cal has been initialized
// with a date yet. E.g. today@12.
// In case of true the parser can adjust the day
// of week for times in the past. E.g. @12.
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

/*
   This parses time in the format:
      - [0-9]+ d|h|m|s ([0-9]+ d|h|m|s ([0-9]+ d|h|m|s ([0-9]+ d|h|m|s)?)?)?

   returns the parsed time span in milliseconds.
*/
parseTimeEstimate
   :((COMMA | AND)? (d=INT DAYS | timeNaturalSpec))+
   ;
