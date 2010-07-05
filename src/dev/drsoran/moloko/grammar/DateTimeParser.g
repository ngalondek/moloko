grammar DateTimeParser;

options
{
	language=Java;
}

@header
{
	package dev.drsoran.moloko.grammar;
	
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Date;
}

@lexer::header
{
	package dev.drsoran.moloko.grammar;
}

@members
{	
	private final static long DAY_MILLIS    = 24 * HOUR_MILLIS;
	
	private final static SimpleDateFormat FULL_DATE_PARSER = new SimpleDateFormat( "dd.MM.yyyy" );
		
		
	private final long parseFullDate( String day, String month, String year )
	{
		long millis = -1;
					
		try
		{		
			millis = FULL_DATE_PARSER.parse( day + "." + month + "." + year ).getTime();			
		}
		catch( ParseException e )
		{
			millis = -1;
		}
		
		return millis;
	}
}

doNotCall : ;
	
fullDate [boolean dayFirst] returns [long millis]
	: ( {$dayFirst}? pt1=INT DATE_SEP
	  					  pt2=INT DATE_SEP
	  					  pt3=INT
						  {
						 	 // year first
						 	 if ( $pt1.getText().length() > 2 )
						 	 {
						 	 	 $millis = parseFullDate( $pt2.getText(),
																  $pt3.getText(),
						 	 									  $pt1.getText() );
						 	 }
						 	 
						 	 // year last
						 	 else
						 	 {
				 		 	 	 $millis = parseFullDate( $pt1.getText(),
						 	 	    							  $pt2.getText(),
						 	  									  $pt3.getText() );
						 	  }
					  	  }
	  					
		| 				  pt1=INT DATE_SEP
	  	  				  pt2=INT DATE_SEP
	  	  				  pt3=INT )
	  	  				  {
	  	  				    // year first
						 	 if ( $pt1.getText().length() > 2 )
						 	 {
						 	 	 $millis = parseFullDate( $pt3.getText(),
																  $pt2.getText(),
						 	 									  $pt1.getText() );
						 	 }
						 	 
						 	 // year last
						 	 else
						 	 {
				 		 	 	 $millis = parseFullDate( $pt2.getText(),
						 	 	    							  $pt1.getText(),
						 	  									  $pt3.getText() );
						 	  }
	  	  				  }
	;	

fragment
NUMBER    : '0' .. '9';

NOW		 : 'now';

TODAY     : 'tod'('ay')?;

TOMORROW  : 'tom'('orrow')? | 'tmr';

TONIGHT   : 'ton'('ight')?;

YESTERDAY : 'yesterday';

DATE_SEP  : ':' | '-' | '/' | '.' | '\u5E74' | '\u6708' | '\u65E5';

INT 	    : NUMBER+;

WS  		 : ( ' '
          |   '\t'
        	 |   '\r'
        	 |   '\n' ) { $channel=HIDDEN; }
    		 ;
