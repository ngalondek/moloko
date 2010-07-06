grammar TimeSpec;

/*
	TODO:
	 - missing time separators: \u0020\u0068\u0020|\u6642|h
*/
options
{
	language=Java;
}

@header
{
	package dev.drsoran.moloko.grammar;
	
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Calendar;
	import java.util.Date;
}

@lexer::header
{
	package dev.drsoran.moloko.grammar;
}

@members
{
	private final static long SECOND_MILLIS = 1000;
		
	private final static long MINUTE_MILLIS = 60 * SECOND_MILLIS;
	
	private final static long HOUR_MILLIS   = 60 * MINUTE_MILLIS;
	
	private final static long DAY_MILLIS    = 24 * HOUR_MILLIS;
	
	
	
	private final static long parsePointInTime( String pit )
	{	
		final int len = pit.length();
		
		if ( len < 3 )
		{
			return( Integer.parseInt( pit ) * HOUR_MILLIS );
		}
		else
		{
			try
			{
				return( new SimpleDateFormat( ( len == 3 ? "hmm" : "hhmm" ) )
					.parse( pit ).getTime() );
			}
			catch( ParseException e )
			{
				return -1;
			}
		}
	}
}
	
timespec returns[long millis]
	:(  v=INT (   s=floatspec  	[ Integer.parseInt( $v.text )     ]
		   		| s=separatorspec [ Integer.parseInt( $v.text ),
				 							  false /* value is not millis */,
				 							  true  /* only colon          */ ]
				   | s=naturalspec   [ Integer.parseInt( $v.text ) 	 ] )
 	   | s=point_in_timespec )
	{
		$millis = s;
	}
	;
	
floatspec[long value] returns[long millis]
	: '.' deciHour=INT HOURS
	{
		final float deciHourVal = (float)Integer.parseInt( $deciHour.text ) * 0.1f;
		$millis = value * HOUR_MILLIS + (long)(deciHourVal * HOUR_MILLIS);
	}
	;
	
separatorspec[long    value,
				  boolean valIsMilli,
				  boolean onlyColon] returns[long millis]
	: ( // Here we need a syntactic predicate otherwise we cannot
	    // distingish 'floatspec'and 'point_in_timespec' with '.'
	    {$onlyColon}? ':' m=INT { $millis += Integer.parseInt( $m.text ) * MINUTE_MILLIS; }
						  (':' s=INT { $millis += Integer.parseInt( $s.text ) * SECOND_MILLIS; })?
		 |
		  				  (':'|'.') m=INT { $millis += Integer.parseInt( $m.text ) * MINUTE_MILLIS; }
		 				 ((':'|'.') s=INT { $millis += Integer.parseInt( $s.text ) * SECOND_MILLIS; })?
	  )
	  {
	  	  if ( $valIsMilli )
  			  $millis += value;
	  	  else
			  $millis += value * HOUR_MILLIS;			  
	  }
	;
	
naturalspec[long value] returns[long millis]
	: DAYS  (h=INT { $millis += Integer.parseInt( $h.text ) * HOUR_MILLIS;   } HOURS  )?
			  (m=INT { $millis += Integer.parseInt( $m.text ) * MINUTE_MILLIS; } MINUTES)?
			  (s=INT { $millis += Integer.parseInt( $s.text ) * SECOND_MILLIS; } SECONDS)?
	{
		$millis = value * DAY_MILLIS;
	}
	| HOURS (m=INT { $millis += Integer.parseInt( $m.text ) * MINUTE_MILLIS; } MINUTES)?
			  (s=INT { $millis += Integer.parseInt( $s.text ) * SECOND_MILLIS; } SECONDS)?
	{
		$millis += value * HOUR_MILLIS;
	}
	| MINUTES (s=INT { $millis += Integer.parseInt( $s.text ) * SECOND_MILLIS; } SECONDS)?
	{
		$millis += value * MINUTE_MILLIS;
	}
	| SECONDS
	{
		$millis += value * SECOND_MILLIS;
	}
	;
	
point_in_timespec returns[long millis]
	: NEVER
	{
		$millis = -1;		
	}
	| MIDNIGHT
	{
		final Calendar c = Calendar.getInstance();
      c.setTimeInMillis( System.currentTimeMillis() );
      c.set( Calendar.HOUR_OF_DAY, 23 );
      c.set( Calendar.MINUTE, 59 );
      c.set( Calendar.SECOND, 59 );
      
      $millis = c.getTime().getTime();
	}
	| ( MIDDAY | NOON )
	{
		final Calendar c = Calendar.getInstance();
      c.setTimeInMillis( System.currentTimeMillis() );
      c.set( Calendar.HOUR_OF_DAY, 12 );
      c.set( Calendar.MINUTE, 00 );
      c.set( Calendar.SECOND, 00 );
      
      $millis = c.getTime().getTime();
	}
	| AT v=INT
	  		{
		  		$millis = parsePointInTime( $v.text );
	  		}
	  ( s=separatorspec[$millis,
	  						  true  /*value is millis */,
	  						  false /*colon and dot*/   ]
	      {
	  		   $millis = s;
	  		} )?
	  ( p=am_pm_spec
	  		{
	  		   $millis += p;
	  		} )?
	;
	
am_pm_spec returns[long millis]
	// missing: \u4e0a|\u4e0b | \u5348\u524d|\u5348\u5f8c|\uc624\uc804|\uc624\ud6c4
	: 'A'('M')?
	{
		$millis = 0;
	}
	| 'P'('M')?
	{
		$millis = 12 * HOUR_MILLIS;
	}
	;

NEVER		 : 'never';

AT			 : '@' | 'at';

MIDNIGHT  : 'midnight';

MIDDAY  	 : 'midday';

NOON  	 : 'noon';

DAYS      : 'days' | 'day' | 'd';
		
HOURS     : 'hours' | 'hour' | 'hrs' | 'hr' | 'h';

MINUTES   : 'minutes' | 'minute' | 'mins' | 'min' | 'm';

SECONDS   : 'seconds' | 'second' | 'secs' | 'sec' | 's';

INT		 : '0'..'9'+;

WS  		 : ( ' '
          |   '\t'
        	 |   '\r'
        	 |   '\n' ) { $channel=HIDDEN; }
    		 ;
