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
	import java.util.Locale;
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
			
		
	private final long parseFullDate( String  day,
												 String  month,
												 String  year,
												 boolean textMonth )
	{
		long millis = -1;
					
		try
		{
			if ( !textMonth )
				millis = new SimpleDateFormat( "dd.MM.yyyy" )
								.parse( day + "." + month + "." + year )
									.getTime();
		   else
		   	millis = new SimpleDateFormat( "dd.MMM.yyyy", Locale.ENGLISH )
								.parse( day + "." + month + "." + year )
									.getTime();
		}
		catch( ParseException e )
		{
			millis = -1;
		}
		
		return millis;
	}			
	
	
	
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

parseDateTime returns [long millis]
	: d=date_spec
		 {
	    	$millis = d;
	  	 }
	 ( AT? t=time_point_in_timespec[ $millis ]
	  	 {
	  	   if ( t != -1 ) $millis = t;
	  	 } )?
	| t=time_spec
	  {
       $millis += t;
	  }
	;

date_spec returns [long millis]
	: (   d=date_full[ true ]
		 | d=date_on_Xst_of )
	{
		$millis = d;
	}
	;

date_full [boolean dayFirst] returns [long millis]
	: ( {$dayFirst}? pt1=INT ( DOT | COLON | MINUS | DATE_SEP )
	  					  pt2=INT ( DOT | COLON | MINUS | DATE_SEP )
	  					  pt3=INT
						  {
						 	 // year first
						 	 if ( $pt1.getText().length() > 2 )
						 	 {
						 	 	 $millis = parseFullDate( $pt2.getText(),
																  $pt3.getText(),
						 	 									  $pt1.getText(),
						 	 									  false );
						 	 }
						 	 
						 	 // year last
						 	 else
						 	 {
				 		 	 	 $millis = parseFullDate( $pt1.getText(),
						 	 	    							  $pt2.getText(),
						 	  									  $pt3.getText(),
						 	  									  false );
						 	  }
					  	  }
	  					
		  | 			  pt1=INT ( DOT | COLON | MINUS | DATE_SEP )
	  	  				  pt2=INT ( DOT | COLON | MINUS | DATE_SEP )
	  	  				  pt3=INT
	  	  				  {
	  	  				    // year first
						 	 if ( $pt1.getText().length() > 2 )
						 	 {
						 	 	 $millis = parseFullDate( $pt3.getText(),
																  $pt2.getText(),
						 	 									  $pt1.getText(),
						 	 									  false );
						 	 }
						 	 
						 	 // year last
						 	 else
						 	 {
				 		 	 	 $millis = parseFullDate( $pt2.getText(),
						 	 	    							  $pt1.getText(),
						 	  									  $pt3.getText(),
						 	  									  false );
						 	  }
	  	  				  }
		)
		;
	
date_on_Xst_of returns [long millis]
	@init
	{
		String year = null;
	}
	: ON? d=INT STs? (OF | '-a' | MINUS | ',' | DOT)? m=MONTHS (MINUS | DOT)? (y=INT { year = $y.text; })?
	{
		if ( year == null )
			year = new SimpleDateFormat( "yyyy" ).format( new Date() );
			
		$millis = parseFullDate( $d.text, $m.text, year, true );
	}
	;

time_spec returns [long millis]
	: AT?
	 (  v=INT (   s=time_floatspec  	  [ Integer.parseInt( $v.text )     ]
		   		| s=time_separatorspec [ Integer.parseInt( $v.text ),
				 							       false /* value is not millis */,
				 							  		 true  /* only colon          */ ]
				   | s=time_naturalspec   [ Integer.parseInt( $v.text ) 	   ] )
 	   | s=time_point_in_timespec[ -1 ] )
	{
		$millis = s;
	}
	;
	
time_floatspec [long value] returns [long millis]
	: '.' deciHour=INT HOURS
	{
		final float deciHourVal = (float)Integer.parseInt( $deciHour.text ) * 0.1f;
		$millis = value * HOUR_MILLIS + (long)(deciHourVal * HOUR_MILLIS);
	}
	;
	
time_separatorspec [long    value,
				  		  boolean valIsMilli,
						  boolean onlyColon] returns [long millis]
	: ( // Here we need a syntactic predicate otherwise we cannot
	    // distingish 'floatspec'and 'point_in_timespec' with '.'
	    {$onlyColon}? COLON m=INT { $millis += Integer.parseInt( $m.text ) * MINUTE_MILLIS; }
						  (COLON s=INT { $millis += Integer.parseInt( $s.text ) * SECOND_MILLIS; })?
		 |
		  				  (COLON|DOT) m=INT { $millis += Integer.parseInt( $m.text ) * MINUTE_MILLIS; }
		 				 ((COLON|DOT) s=INT { $millis += Integer.parseInt( $s.text ) * SECOND_MILLIS; })?
	  )
	  {
	  	  if ( $valIsMilli )
  			  $millis += value;
	  	  else
			  $millis += value * HOUR_MILLIS;			  
	  }
	;
	
time_naturalspec [long value] returns [long millis]
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
	
time_point_in_timespec [long date] returns [long millis]
	: NEVER
	{
		$millis = -1;		
	}
	| MIDNIGHT
	{
		final Calendar c = Calendar.getInstance();
      c.setTimeInMillis( ( date == -1 ) ? System.currentTimeMillis() : date );
      c.set( Calendar.HOUR_OF_DAY, 23 );
      c.set( Calendar.MINUTE, 59 );
      c.set( Calendar.SECOND, 59 );
      
      $millis = c.getTime().getTime();
	}
	| ( MIDDAY | NOON )
	{
		final Calendar c = Calendar.getInstance();
      c.setTimeInMillis( ( date == -1 ) ? System.currentTimeMillis() : date );
      c.set( Calendar.HOUR_OF_DAY, 12 );
      c.set( Calendar.MINUTE, 00 );
      c.set( Calendar.SECOND, 00 );
      
      $millis = c.getTime().getTime();
	}
	|   v=INT
	  		{
		  		if ( date == -1 )
		  			$millis = parsePointInTime( $v.text );
		  		else
		  			$millis = date + parsePointInTime( $v.text );
	  		}
	  ( s=time_separatorspec[ $millis,
	  				       		  true  /* value is millis */,
	  						  		  false /* colon and dot   */ ]
	      {
	  		   $millis = s;
	  		} )?
	  ( p=am_pm_spec
	  		{
	  		   $millis += p;
	  		} )?
	;
	
am_pm_spec returns [long millis]
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

ON		    : 'on';

IN   		 : 'in';

OF			 : 'of';

STs		 : 'st' | 'th' | 'rd' | 'nd';

MIDNIGHT  : 'midnight';

MIDDAY  	 : 'midday';

NOON  	 : 'noon';

DAYS      : 'days' | 'day' | 'd';
		
HOURS     : 'hours' | 'hour' | 'hrs' | 'hr' | 'h';

MINUTES   : 'minutes' | 'minute' | 'mins' | 'min' | 'm';

SECONDS   : 'seconds' | 'second' | 'secs' | 'sec' | 's';

MONTHS    : 'january' | 'jan' | 'february' | 'feb' | 'march' | 'mar' | 'april' | 'apr' |
				'may' | 'june' | 'jun' | 'july' | 'jul' | 'august' | 'aug' | 'september' | 'sept' | 'sep' |
				'october' | 'oct' | 'november' | 'nov' | 'december' | 'dec';

DATE_SEP  : '/' | '\u5E74' | '\u6708' | '\u65E5';

DOT       : '.';

COLON		 : ':';

MINUS	    : '-';

INT		 : '0'..'9'+;

WS  		 : ( ' '
          |   '\t'
        	 |   '\r'
        	 |   '\n' ) { $channel=HIDDEN; }
    		 ;
