grammar DateTimeParser;

options
{
	language=Java;
}

@header
{
	package dev.drsoran.moloko.grammar;
}

@lexer::header
{
	package dev.drsoran.moloko.grammar;
}

@members
{
	private final static long SECOND_MILLIS = 1000;
		
	private final static long MINUTE_MILLIS = 60 * SECOND_MILLIS;
	
	private final static long HOUR_MILLIS = 60 * MINUTE_MILLIS;
}

statement
	: timespec
	;

timespec returns [ long millis ]
	:      h=INT   { millis += Integer.parseInt( $h.text ) * HOUR_MILLIS;   }
	   ':' m=INT   { millis += Integer.parseInt( $m.text ) * MINUTE_MILLIS; }
	  (':' s=INT)? { millis += Integer.parseInt( $s.text ) * SECOND_MILLIS; }
	|
	  ( (	  h=INT	 { millis += Integer.parseInt( $h.text ) * HOUR_MILLIS;   }
	  		| h=FLOAT { millis += Float.parseFloat( $h.text ) * HOUR_MILLIS;   } ) HOURS   )?
	    (   m=INT 	 { millis += Integer.parseInt( $h.text ) * MINUTE_MILLIS; }   MINUTES )?
	    (   s=INT 	 { millis += Integer.parseInt( $h.text ) * SECOND_MILLIS; }	  SECONDS )?
	;
	catch [NumberFormatException nfe]
	{
		$millis = -1;
	}

HOURS   : 'hours' | 'hour' | 'hrs' | 'hr' | 'h';

MINUTES : 'minutes' | 'minute' | 'mins' | 'min' | 'm';

SECONDS : 'seconds' | 'second' | 'secs' | 'sec' | 's';

INT 	  : '0'..'9'+;

FLOAT   : ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
		  | '.' ('0'..'9')+ EXPONENT?
		  | ('0'..'9')+ EXPONENT
		  ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) { $channel=HIDDEN; }
    ;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

