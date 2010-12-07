parser grammar DateParser;

/*
   TODO:
    - missing time separators: \u0020\u0068\u0020|\u6642|h
*/
options
{
   language=Java;
   tokenVocab=DateTimeLexer;
}

@header
{
   package dev.drsoran.moloko.grammar;

   import java.text.ParseException;
   import java.text.SimpleDateFormat;
   import java.util.Calendar;
   import java.util.Locale;
   import java.util.HashMap;
}

@members
{
   public DateParser()
   {
   	super( null );
   }
   
   private final static Locale LOCALE = Locale.ENGLISH;

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



   public final static Calendar getLocalizedCalendar()
   {
	   final Calendar cal = Calendar.getInstance( LOCALE );
	   
	   cal.clear( Calendar.HOUR );
      cal.clear( Calendar.HOUR_OF_DAY );
      cal.clear( Calendar.MINUTE );
      cal.clear( Calendar.SECOND );
      cal.clear( Calendar.MILLISECOND );
      
      return cal;
   }



   private final static int strToNumber( String string )
   {
      int res = -1;

      final Integer val = numberLookUp.get( string );

      if ( val != null )
         res = val;

      return res;
   }



   private final static void parseFullDate( Calendar cal,
                                            String   day,
                                            String   month,
                                            String   year,
                                            boolean  textMonth ) throws RecognitionException
   {
      try
      {         
         final StringBuffer pattern = new StringBuffer( "dd." );
         
         if ( !textMonth )
         {
            pattern.append( "MM" );
         }
         else
         {
            pattern.append( "MMM" );
         }
         
         if ( year != null )
         {
            pattern.append( ".yyyy" );
         }        

         final SimpleDateFormat sdf = new SimpleDateFormat( pattern.toString(),
                                        						   LOCALE /* Locale for MMM*/ );
                  
         sdf.parse( day + "." + month + ( ( year != null ) ? "." + year : "" ) );

			final Calendar sdfCal = sdf.getCalendar();
			
         cal.set( Calendar.DAY_OF_MONTH, sdfCal.get( Calendar.DAY_OF_MONTH ) );
         cal.set( Calendar.MONTH,        sdfCal.get( Calendar.MONTH ) );

         if ( year != null )
	         cal.set( Calendar.YEAR, sdfCal.get( Calendar.YEAR ) );
      }
      catch( ParseException e )
      {
         throw new RecognitionException();
      }
   }



   private final static void parseTextMonth( Calendar cal,
                                             String   month ) throws RecognitionException
   {
      try
      {
         final SimpleDateFormat sdf = new SimpleDateFormat( "MMM", LOCALE );
         sdf.parse( month );

         cal.set( Calendar.MONTH, sdf.getCalendar().get( Calendar.MONTH ) );
      }
      catch( ParseException e )
      {
         throw new RecognitionException();
      }
   }



   private final static void parseYear( Calendar cal, String yearStr ) throws RecognitionException
   {
      final int len = yearStr.length();

      int year = 0;

      try
      {
         if ( len < 4 )
         {
            final SimpleDateFormat sdf = new SimpleDateFormat("yy");
      
            if ( len == 1 )
            {
               yearStr = "0" + yearStr;
            }
            else if ( len == 3 )
            {
               yearStr = yearStr.substring( 1, len );
            }
      
            sdf.parse( yearStr );
            year = sdf.getCalendar().get( Calendar.YEAR );
         }
         else
         {
            year = Integer.parseInt( yearStr );
         }
      
         cal.set( Calendar.YEAR, year );
      }
      catch( ParseException pe )
      {
         throw new RecognitionException();
      }
      catch( NumberFormatException nfe )
      {
         throw new RecognitionException();
      }
   }



   private final static void rollToEndOf( int field, Calendar cal )
   {
      final int ref = cal.get( field );
      final int max = cal.getActualMaximum( field );

      // set the field to the end.
      cal.set( field, max );

      // if already at the end
      if ( ref == max )
      {
         // we roll to the next
         cal.add( field, 1 );
      }
   }
}

/** RULES **/


parseDate [Calendar cal, boolean clearTime] returns [boolean eof]
   : (   date_full         [$cal]
       | date_on           [$cal]
       | date_in_X_YMWD    [$cal]
       | date_end_of_the_MW[$cal]
       | date_natural      [$cal])
   // It's important to clear the time fields
   // at last step cause the Calendar methods
   // will set them again.
	{
		if ( clearTime )
		{		
		   cal.clear( Calendar.HOUR );
	      cal.clear( Calendar.HOUR_OF_DAY );
   	   cal.clear( Calendar.MINUTE );
      	cal.clear( Calendar.SECOND );
	      cal.clear( Calendar.MILLISECOND );
      }
   }
   | EOF
   {
   	eof = true;
   }
   ;
   catch [RecognitionException e]
   {
      throw e;
   }
   
parseDateWithin[boolean past] returns [Calendar epochStart, Calendar epochEnd]
	@init
	{
	   retval.epochStart = getLocalizedCalendar();
		int amount        =  1;
		int unit          = -1;
	}
	@after
	{
		retval.epochEnd = getLocalizedCalendar();
		retval.epochEnd.setTimeInMillis( retval.epochStart.getTimeInMillis() );
		retval.epochEnd.add( unit, past ? -amount : amount );
		
		retval.epochStart.clear( Calendar.HOUR );
      retval.epochStart.clear( Calendar.HOUR_OF_DAY );
  	   retval.epochStart.clear( Calendar.MINUTE );
     	retval.epochStart.clear( Calendar.SECOND );
      retval.epochStart.clear( Calendar.MILLISECOND );
		retval.epochEnd.clear( Calendar.HOUR );
      retval.epochEnd.clear( Calendar.HOUR_OF_DAY );
  	   retval.epochEnd.clear( Calendar.MINUTE );
     	retval.epochEnd.clear( Calendar.SECOND );
      retval.epochEnd.clear( Calendar.MILLISECOND );
	}
	: (  a=INT
	     {
	        amount = Integer.parseInt( $a.text );
	     }
	   | n=NUM_STR
	     {
	        amount = strToNumber( $n.text );
	     }
	   | A)?
	   (  DAYS
	   	{
	   	   unit = Calendar.DAY_OF_YEAR;
	   	}
	    | WEEKS
	      {
	         unit = Calendar.WEEK_OF_YEAR;
	      }
	    | MONTHS
	      {
	         unit = Calendar.MONTH;
	      }
	    | YEARS
	      {
	         unit = Calendar.YEAR;
	      }
	   )	   
	   (OF parseDate[retval.epochStart, false])?
	;
	catch [NumberFormatException e]
   {
      throw new RecognitionException();
   }
	catch [RecognitionException e]
   {
      throw e;
   }

date_full [Calendar cal]
   @init
   {
      String pt1Str = null;
      String pt2Str = null;
      String pt3Str = null;
   }
   : pt1=INT (DOT | MINUS | COLON | DATE_SEP)
     {
        pt1Str = $pt1.getText();
     }
     pt2=INT (DOT | MINUS | COLON | DATE_SEP)
     {
        pt2Str = $pt2.getText();
     }
     (
        pt3=INT
        {
           pt3Str = $pt3.getText();
        }
     )?
     {
        // check if we have all 3 parts
        if ( pt3Str != null && pt1Str.length() > 2 )
        {
		     // year first
		     parseFullDate( $cal,
	                       pt2Str,
	                       pt3Str,
	                       pt1Str,
	                       false );
	     }
	
        // year last or only 2 parts
	     else
	     {
	        parseFullDate( $cal,
	                       pt1Str,
	                       pt2Str,
	                       pt3Str,
	                       false );
           
           // if year is missing and the date is
           // befor now we roll to the next year.
           if ( pt3Str == null )
           {
              final Calendar now = getLocalizedCalendar();
           
	           if ( cal.before( now ) )
	           {
	              cal.add( Calendar.YEAR, 1 );   
	           }
	        }
	     }
      }
      ;

date_on [Calendar cal]
   : ON? (   date_on_Xst_of_M[$cal]
           | date_on_M_Xst   [$cal]
           | date_on_weekday [$cal])
   ;
   catch [RecognitionException e]
   {
      throw e;
   }

date_on_Xst_of_M [Calendar cal]
	@init
	{
	   final Calendar now = getLocalizedCalendar();
	   boolean hasMonth   = false;
	   boolean hasYear    = false;
	}
   : d=INT STs?
       {       	 
          cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( $d.text ) );
       }
     ((OF | MINUS_A | MINUS | COMMA | DOT)?
       m=MONTH
         {
            parseTextMonth( cal, $m.text );            
            hasMonth = true;
         }
      (MINUS | DOT)?
      (y=INT
         {
            parseYear( cal, $y.text );
            hasYear = true;
         })?)?
   {     
      // if we have a year we have a full qualified date.
      // so we change nothing.
      if ( !hasYear && cal.before( now ) )
      {
      	// if we have a month, we roll to next year.
         if ( hasMonth )
            cal.add( Calendar.YEAR, 1 );
        	// if we only have a day, we roll to next month.
         else
            cal.add( Calendar.MONTH, 1 );
      }
   }
   ;
   catch [NumberFormatException e]
   {
      throw new RecognitionException();
   }
   catch [RecognitionException e]
   {
      throw e;
   }

date_on_M_Xst [Calendar cal]
	@init
	{
	   boolean hasYear = false;
	}
   : m=MONTH
       {
         parseTextMonth( cal, $m.text );
       }
    (MINUS | COMMA | DOT)?
    (d=INT
       {
         cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( $d.text ) );
       }
       (STs | MINUS_A | MINUS | COMMA | DOT)+)?
    (y=INT
       {
          parseYear( cal, $y.text );
          hasYear = true;
       })?
   {
      // if we have a year we have a full qualified date.
      // so we change nothing.
      if ( !hasYear && getLocalizedCalendar().after( cal ) )
         // If the date is before now we roll the year     
	      cal.add( Calendar.YEAR, 1 );
   }
   ;
   catch [NumberFormatException e]
   {
      throw new RecognitionException();
   }
   catch [RecognitionException e]
   {
      throw e;
   }

date_on_weekday [Calendar cal]
   @init
   {
      boolean nextWeek = false;
   }
   : (NEXT { nextWeek = true; })? wd=WEEKDAY
   {
      final SimpleDateFormat sdf = new SimpleDateFormat( "EE", LOCALE );
      sdf.parse( $wd.text );

      final int parsedWeekDay  = sdf.getCalendar().get( Calendar.DAY_OF_WEEK );
      final int currentWeekDay = cal.get( Calendar.DAY_OF_WEEK );

      cal.set( Calendar.DAY_OF_WEEK, parsedWeekDay );

      // If the weekday is before today or today, we adjust to next week.
      if ( parsedWeekDay <= currentWeekDay )
         cal.add( Calendar.WEEK_OF_YEAR, 1 );

      // if the next week is explicitly enforced
      if ( nextWeek )
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
   }
   ;
   catch[ ParseException pe ]
   {
      throw new RecognitionException();
   }
   catch [RecognitionException e]
   {
      throw e;
   }

date_in_X_YMWD [Calendar cal]
   :  IN?             date_in_X_YMWD_distance[$cal]
      (( AND| COMMA ) date_in_X_YMWD_distance[$cal])*
   ;
   catch [RecognitionException e]
   {
      throw e;
   }

date_in_X_YMWD_distance [Calendar cal]
   @init
   {
      int amount   = -1;
      int calField = Calendar.YEAR;
   }
   : (   a=NUM_STR { amount = strToNumber( $a.text );      }
       | a=INT     { amount = Integer.parseInt( $a.text ); })
     (     YEARS
       |   MONTHS  { calField = Calendar.MONTH;  			  }
       |   WEEKS   { calField = Calendar.WEEK_OF_YEAR;     }
       |   DAYS    { calField = Calendar.DAY_OF_YEAR;      })
   {
      if ( amount != -1 )
      {
         cal.add( calField, amount );
      }
      else
      {
         throw new RecognitionException();
      }
   }
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }
   catch [RecognitionException e]
   {
      throw e;
   }

date_end_of_the_MW [Calendar cal]
   : END OF? THE?
     (   WEEKS
         {
            rollToEndOf( Calendar.DAY_OF_WEEK, cal );
         }
       | MONTHS
         {
            rollToEndOf( Calendar.DAY_OF_MONTH, cal );
         })
   ;
   catch [RecognitionException e]
   {
      throw e;
   }

date_natural [Calendar cal]
   : (TODAY | TONIGHT)
     {
     }
   | TOMORROW
     {
        cal.roll( Calendar.DAY_OF_YEAR, true );
     }
   | YESTERDAY
     {
        cal.roll( Calendar.DAY_OF_YEAR, false );
     }
   ;
   catch [RecognitionException e]
   {
      throw e;
   }
   