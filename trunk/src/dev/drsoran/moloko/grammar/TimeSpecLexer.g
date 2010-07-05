lexer grammar TimeSpecLexer;

options
{
	language=Java;
}

@header
{
	package dev.drsoran.moloko.grammar;
}

@members
{
	private final static long SECOND_MILLIS = 1000;
		
	private final static long MINUTE_MILLIS = 60 * SECOND_MILLIS;
	
	private final static long HOUR_MILLIS   = 60 * MINUTE_MILLIS;
	
	private long result = -1;
	
	private final static long parseString( String value ) throws NumberFormatException
	{
		if ( value.indexOf( '.' ) != -1 )
			return (long) Float.parseFloat( value );
		else
			return Integer.parseInt( value );
	}
	
	public long getResult()
	{
		while ( nextToken() != Token.EOF_TOKEN )
      {
      }
      
      return result;
	}
}

TIMESPEC
	@init
	{
		result = 0;
	}
	:      h=INT   { result += Integer.parseInt( $h.text ) * HOUR_MILLIS;   }
	   ':' m=INT   { result += Integer.parseInt( $m.text ) * MINUTE_MILLIS; }
	  (':' s=INT)? { result += Integer.parseInt( $s.text ) * SECOND_MILLIS; }
	|
	  (  h=STRING { result += parseString( $h.getText() ) * HOUR_MILLIS;	  } HOURS   )?
	  (     m=INT { result += Integer.parseInt( $h.text ) * MINUTE_MILLIS; } MINUTES )?
	  ( 	  s=INT { result += Integer.parseInt( $h.text ) * SECOND_MILLIS; } SECONDS )?
	;
	
	
HOURS    : 'hours' | 'hour' | 'hrs' | 'hr' | 'h';

MINUTES  : 'minutes' | 'minute' | 'mins' | 'min' | 'm';

SECONDS  : 'seconds' | 'second' | 'secs' | 'sec' | 's';

INT		 : '0'..'9'+;

STRING    : ~('"' | ' ' | '(' | ')')+;

WS  		 : ( ' '
          |   '\t'
        	 |   '\r'
        	 |   '\n' ) { $channel=HIDDEN; }
    		 ;
