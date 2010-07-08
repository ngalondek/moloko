grammar TimeSpec;

/*
	TODO:
	 - missing time separators: \u0020\u0068\u0020|\u6642|h
	 - allow entering a year as y or yy instead of yyyy in date rules
	 - recognize now, tomorrow, yesterday
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
	import java.util.HashMap;
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
	
	private final static HashMap< String, Integer > numberLookUp = new HashMap< String, Integer >();
	
	static
	{
		numberLookUp.put( "one",   1 );
		numberLookUp.put( "two",   2 );
		numberLookUp.put( "three", 3 );
		numberLookUp.put( "four",  4 );
		numberLookUp.put( "five",  5 );
		numberLookUp.put( "six",   6 );
		numberLookUp.put( "seven", 7 );
		numberLookUp.put( "eight", 8 );												
		numberLookUp.put( "nine",  9 );
		numberLookUp.put( "ten",   10 );														
	}

	private final static int strToNumber( String string )
	{
		int res = -1;
		
		final Integer val = numberLookUp.get( string );
    		
      if ( val != null )
         res = val;
		
		return res;
	}
		
	private final static long parseFullDate( String  day,
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
	
	
	
	private final static Calendar rollToEndOf( int field, Calendar cal )
	{	
      final int ref = cal.get( field );
      final int max = cal.getMaximum( field );
      
      // set the field to the end.        
      cal.set( field, max );
      
      // if already at the end
      if ( ref == max )
      {
      	// we roll to the next
      	cal.roll( field, true );
      }
      
      return cal;
	}
	
	
	/*
	private final static boolean setCalendarTime( Calendar cal, String pit )
	{	
		final int len = pit.length();
		
		SimpleDateFormat sdf = null;
		
		try
		{
			if ( len < 3 )
			{
				sdf = new SimpleDateFormat( "hh" );
			}
			else if ( len > 3 )
			{				
				sdf = new SimpleDateFormat( "hhmm" );
			}
			else
			{
				sdf = new SimpleDateFormat( "hmm" );
			}
			
			return true;
		}
		catch( ParseException e )
		{
			return false;
		}
	}*/
}

parseDateTime returns [long millis]
	: r=date_spec
		 {
	    	$millis = r;
	  	 }
	 ( AT? t=time_point_in_timespec[ $millis /** millis from date_spec */ ]
	  	 {
	  	   if ( t != -1 )
	  	   	$millis = t;
	  	 } )?
	| t=time_spec
	  {
       $millis += t;
	  }
	;

date_spec returns [long millis]
	: (   r=date_full[ true ]
		 | r=date_on
		 | r=date_in_X_YMWD
		 | r=date_end_of_the_MW )
	{
		$millis = r;
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
	
date_on returns [long millis]
	: ON? (  r=date_on_Xst_of_M
			 | r=date_on_M_Xst
			 | r=date_on_weekday)
	{
		$millis = r;
	}
	;
	
date_on_Xst_of_M returns [long millis]
	@init
	{
		String year = null;
	}
	: d=INT STs? (OF | '-a' | MINUS | COMMA | DOT)? m=MONTH (MINUS | DOT)? (y=INT { year = $y.text; })?
	{
		if ( year == null )
			year = new SimpleDateFormat( "yyyy" )
						.format( Calendar.getInstance( Locale.ENGLISH ).getTime() );
		
		if ( year != null )
			$millis = parseFullDate( $d.text, $m.text, year, true );
		else
			$millis = -1;
	}
	;
	
date_on_M_Xst returns [long millis]
	@init
	{
		String year = null;
	}
	: m=MONTH (MINUS | COMMA | DOT)? d=INT STs? (MINUS | DOT)? ('-a' | MINUS | COMMA | DOT)? (y=INT { year = $y.text; })?
	{
		if ( year == null )
			year = new SimpleDateFormat( "yyyy" )
						.format( Calendar.getInstance( Locale.ENGLISH ).getTime() );
			
		if ( year != null )
			$millis = parseFullDate( $d.text, $m.text, year, true );
		else
			$millis = -1;
	}
	;
	
date_on_weekday returns [long millis]
	@init
	{
		boolean nextWeek = false;		
	}
	: (NEXT { nextWeek = true; })? wd=WEEKDAY
	{
		final Calendar now = Calendar.getInstance( Locale.ENGLISH );		
      final SimpleDateFormat sdf = new SimpleDateFormat( "EE", Locale.ENGLISH );
      final Calendar parsedDate = sdf.getCalendar();
      
      parsedDate.setTime( sdf.parse( $wd.text ) );
      parsedDate.set( Calendar.YEAR, now.get( Calendar.YEAR ) );
      parsedDate.set( Calendar.WEEK_OF_YEAR, now.get( Calendar.WEEK_OF_YEAR ) );
      
      // If the weekday is before today or today, we adjust to next week.
      if ( parsedDate.before( now ) )
         parsedDate.roll( Calendar.WEEK_OF_YEAR, true );
      
      // if the next week is explicitly enforced
      if ( nextWeek )
      	parsedDate.roll( Calendar.WEEK_OF_YEAR, true );
      
      $millis = parsedDate.getTimeInMillis();
	}
	;
	catch[ ParseException pe ]
	{
		$millis = -1;
	}

date_in_X_YMWD returns [ long millis ]
	@init
	{
		$millis = System.currentTimeMillis();
	}
	:	IN? 				 r=date_in_X_YMWD_distance { $millis += r; }
		(( AND| COMMA ) r=date_in_X_YMWD_distance { $millis += r; })*
	;
		
date_in_X_YMWD_distance returns [long distMillis]
	@init
	{
		int amount   = -1;
		int calField = Calendar.YEAR;
	}
	: (   a=NUM_STR { amount = strToNumber( $a.text );      }
		 | a=INT 	 { amount = Integer.parseInt( $a.text ); })
	  (     YEARS
	    |   MONTHS  { calField = Calendar.MONTH; 		 	  }
		 |   WEEKS   { calField = Calendar.WEEK_OF_YEAR;     }
		 |   DAYS	 { calField = Calendar.DAY_OF_YEAR;      })
	{
		if ( amount != -1 )
		{
			final Calendar future = Calendar.getInstance( Locale.ENGLISH );
			future.roll( calField, amount );
			
			$distMillis = future.getTimeInMillis() - System.currentTimeMillis();
		}
		else
		{
         $distMillis = -1;
      }
	}	 
	;
	catch[ NumberFormatException nfe ]
	{
		$distMillis = -1;
	}
	
date_end_of_the_MW returns [long millis]
	@init
	{
		final Calendar now = Calendar.getInstance( Locale.ENGLISH );
	}
	: END OF? THE?
	  (   WEEKS
	  		{
				$millis = rollToEndOf( Calendar.DAY_OF_WEEK, now ).getTimeInMillis();
	  		}	  
	  	 | MONTHS
	  	   {
	  	   	$millis = rollToEndOf( Calendar.DAY_OF_MONTH, now ).getTimeInMillis();
	  	   })	
	;

time_spec returns [long millis]
	: (  v=INT (  r=time_floatspec  	  [ Integer.parseInt( $v.text )     ]
		   		| r=time_separatorspec [ Integer.parseInt( $v.text ),
				 							       false /* value is not millis */,
				 							  		 true  /* only colon          */ ]
				   | r=time_naturalspec   [ Integer.parseInt( $v.text ) 	   ] )
 	   | AT? r=time_point_in_timespec[ -1 ] )
	{
		$millis = r;
	}
	;
	catch[ NumberFormatException nfe ]
	{
		$millis = -1;
	}
	
time_floatspec [long value] returns [long millis]
	: '.' deciHour=INT HOURS
	{
		final float deciHourVal = (float)Integer.parseInt( $deciHour.text ) * 0.1f;
		$millis = value * HOUR_MILLIS + (long)(deciHourVal * HOUR_MILLIS);
	}
	;
	catch[ NumberFormatException nfe ]
	{
		$millis = -1;
	}
	
time_separatorspec [long    value,
				  		  boolean valIsMilli,
						  boolean onlyColon] returns [long millis]
	: ( // Here we need a syntactic predicate otherwise we cannot
	    // distingish 'floatspec'and 'point_in_timespec' with '.'
	    {$onlyColon}? COLON 	  m=INT { $millis += Integer.parseInt( $m.text ) * MINUTE_MILLIS; }
						  (COLON 	  s=INT { $millis += Integer.parseInt( $s.text ) * SECOND_MILLIS; })?
		 |
		  				  (COLON|DOT) m=INT { $millis += Integer.parseInt( $m.text ) * MINUTE_MILLIS; }
		 				 ((COLON|DOT) s=INT { $millis += Integer.parseInt( $s.text ) * SECOND_MILLIS; })?
	  )
	  {
	  	  if ( value == -1 )
	  	  {
	  	     $millis = -1;
	  	  }
	  	  else
	  	  {
		  	  if ( $valIsMilli )
	  			  $millis += value;
		  	  else
				  $millis += value * HOUR_MILLIS;
			}
	  }
	  	;
	catch[ NumberFormatException nfe ]
	{
		$millis = -1;
	}
	
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
	catch[ NumberFormatException nfe ]
	{
		$millis = -1;
	}
	
time_point_in_timespec [long date] returns [long millis]
	@init
	{
		final Calendar c = Calendar.getInstance( Locale.ENGLISH );
      c.setTimeInMillis( ( date == -1 ) ? System.currentTimeMillis() : date );
	}
	: NEVER
	{
		$millis = -1;		
	}
	| MIDNIGHT
	{		
      c.set( Calendar.HOUR_OF_DAY, 23 );
      c.set( Calendar.MINUTE, 59 );
      c.set( Calendar.SECOND, 59 );
      
      $millis = c.getTimeInMillis();
	}
	| ( MIDDAY | NOON )
	{
		c.set( Calendar.HOUR_OF_DAY, 12 );
      c.set( Calendar.MINUTE, 00 );
      c.set( Calendar.SECOND, 00 );
      
      $millis = c.getTimeInMillis();
   }
	|
   {
	   long tempMillis = 0;
	}
	   (  v=INT
	  		  {
		  	  	  tempMillis = parsePointInTime( $v.text );
	  		  }
	    ( r=time_separatorspec[ $millis,
	  	   			       		 true  /* value is millis */,
	  						  		    false /* colon and dot   */ ]
	        {
	  		     tempMillis = r;
	  		  })?
	    ( p=am_pm_spec
	  		  {
	  		     tempMillis += p;
	  		  })?)
	{
		final Calendar tempCal = Calendar.getInstance( Locale.ENGLISH );
		tempCal.setTimeInMillis( tempMillis );
		
		c.set( Calendar.HOUR_OF_DAY, tempCal.get( Calendar.HOUR_OF_DAY ) );
      c.set( Calendar.MINUTE, tempCal.get( Calendar.MINUTE ) );
      c.set( Calendar.SECOND, tempCal.get( Calendar.SECOND ) );
      
		$millis = c.getTimeInMillis();
	}
	;
	catch[ NumberFormatException nfe ]
	{
		$millis = -1;
	}
	
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
	
TODAY		 : 'today';

NEVER		 : 'never';

AT			 : '@' | 'at';

ON		    : 'on';

IN   		 : 'in';

OF			 : 'of';

NEXT		 : 'next';

AND		 : 'and';

END		 : 'end';

THE		 : 'the';

STs		 : 'st' | 'th' | 'rd' | 'nd';

MIDNIGHT  : 'midnight';

MIDDAY  	 : 'midday';

NOON  	 : 'noon';

YEARS		 : 'years' | 'year' | 'yrs' | 'yr';

MONTHS	 : 'months' | 'month' | 'mons' | 'mon';

WEEKS		 : 'weeks' | 'week' | 'wks' | 'wk';

DAYS      : 'days' | 'day' | 'd';
		
HOURS     : 'hours' | 'hour' | 'hrs' | 'hr' | 'h';

MINUTES   : 'minutes' | 'minute' | 'mins' | 'min' | 'm';

SECONDS   : 'seconds' | 'second' | 'secs' | 'sec' | 's';

MONTH     : 'january'   | 'jan'  | 'february' | 'feb'     | 'march' | 'mar'      | 'april' | 'apr' |
				'may'       | 'june' | 'jun'      | 'july'    | 'jul'   | 'august'   | 'aug'   |
				'september' | 'sept' | 'sep'      | 'october' | 'oct'   | 'november' | 'nov'   |				
				'december'  | 'dec';			

WEEKDAY   : 'monday'   | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' |
				'thursday' | 'thu' | 'friday'  | 'fri' | 'saturday'  | 'sat' |
				'sunday'   | 'sun';

DATE_SEP  : '/' | '\u5E74' | '\u6708' | '\u65E5';

DOT       : '.';

COLON		 : ':';

MINUS	    : '-';

COMMA		 : ',';

INT		 : '0'..'9'+;

NUM_STR 	 : 'one' | 'two' | 'three' | 'four' | 'six' | 'seven' | 'eight' | 'nine' | 'ten';

WS  		 : ( ' '
          |   '\t'
        	 |   '\r'
        	 |   '\n' ) { $channel=HIDDEN; }
    		 ;
