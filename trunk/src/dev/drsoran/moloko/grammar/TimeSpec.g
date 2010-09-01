grammar TimeSpec;

/*
   TODO:
    - missing time separators: \u0020\u0068\u0020|\u6642|h
   
   LIMITATIONS:
    - '.' is only allowed for dates not times
    - ':' is only allowed for times not dates
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
      return Calendar.getInstance( LOCALE );
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
         SimpleDateFormat sdf = null;

         if ( !textMonth )
         {
            sdf = new SimpleDateFormat( "dd.MM.yyyy" );
         }
         else
         {
            sdf = new SimpleDateFormat( "dd.MMM.yyyy",
                                        LOCALE /* Locale for MMM*/ );
         }

         sdf.setCalendar( cal );
         sdf.parse( day + "." + month + "." + year );
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



   private final static void setCalendarTime( Calendar cal,
                                              String   pit ) throws RecognitionException
   {
      final int len = pit.length();

      SimpleDateFormat sdf = null;

      try
      {
         if ( len < 3 )
         {
            sdf = new SimpleDateFormat( "HH" );
         }
         else if ( len > 3 )
         {
            sdf = new SimpleDateFormat( "HHmm" );
         }
         else
         {
            sdf = new SimpleDateFormat( "Hmm" );
         }

         sdf.parse( pit );

         final Calendar sdfCal = sdf.getCalendar();
         cal.set( Calendar.HOUR_OF_DAY, sdfCal.get( Calendar.HOUR_OF_DAY ) );
         cal.set( Calendar.MINUTE,      sdfCal.get( Calendar.MINUTE ) );
         cal.set( Calendar.SECOND,      0 );
      }
      catch( ParseException e )
      {
         throw new RecognitionException();
      }
   }



   private final static void rollToEndOf( int field, Calendar cal )
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
   }
}

/** RULES **/


parseDateTime [Calendar cal, boolean dayFirst]
   @init
   {
      boolean hasTime = false;
   }     
   : date_spec[$cal, $dayFirst] (time_spec[ $cal ] { hasTime = true; })?
   {
      if ( !hasTime )
      {
         cal.clear( Calendar.HOUR );
         cal.clear( Calendar.HOUR_OF_DAY );
         cal.clear( Calendar.MINUTE );
         cal.clear( Calendar.SECOND );
      }
   }
   ;
   catch [NoViableAltException nve]
   {
      throw new RecognitionException();
   }

date_spec [Calendar cal, boolean dayFirst]
   : (   date_full         [$cal, $dayFirst]
       | date_on           [$cal]
       | date_in_X_YMWD    [$cal]
       | date_end_of_the_MW[$cal]
       | date_natural      [$cal])
   ;

date_full [Calendar cal, boolean dayFirst]
   : ( {$dayFirst}? pt1=INT ( DOT | MINUS | DATE_SEP )
                    pt2=INT ( DOT | MINUS | DATE_SEP )
                    pt3=INT
                    {
                      // year first
                      if ( $pt1.getText().length() > 2 )
                      {
                         parseFullDate( $cal,
                                        $pt2.getText(),
                                        $pt3.getText(),
                                        $pt1.getText(),
                                        false );
                      }

                      // year last
                      else
                      {
                         parseFullDate( $cal,
                                        $pt1.getText(),
                                        $pt2.getText(),
                                        $pt3.getText(),
                                        false );
                       }
                    }

        |           pt1=INT ( DOT | MINUS | DATE_SEP )
                    pt2=INT ( DOT | MINUS | DATE_SEP )
                    pt3=INT
                    {
                      // year first
                      if ( $pt1.getText().length() > 2 )
                      {
                         parseFullDate( $cal,
                                        $pt3.getText(),
                                        $pt2.getText(),
                                        $pt1.getText(),
                                        false );
                      }

                      // year last
                      else
                      {
                         parseFullDate( $cal,
                                        $pt2.getText(),
                                        $pt1.getText(),
                                        $pt3.getText(),
                                        false );
                       }
                    }
      )
      ;

date_on [Calendar cal]
   : ON? (   date_on_Xst_of_M[$cal]
           | date_on_M_Xst   [$cal]
           | date_on_weekday [$cal])
   ;

date_on_Xst_of_M [Calendar cal]
   : d=INT STs?
       {
          cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( $d.text ) );
       }
     ((OF | '-a' | MINUS | COMMA | DOT)?
       m=MONTH
         {
            parseTextMonth( cal, $m.text );
         }
      (MINUS | DOT)?
      (y=INT
         {
            parseYear( cal, $y.text );
         })?)?
   ;
   catch [NumberFormatException e]
   {
      throw new RecognitionException();
   }

date_on_M_Xst [Calendar cal]
   : m=MONTH
       {
         parseTextMonth( cal, $m.text );
       }
    (MINUS | COMMA | DOT)?
    (d=INT
       {
         cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( $d.text ) );
       }
       (STs | '-a' | MINUS | COMMA | DOT)+)?
    (y=INT
       {
          parseYear( cal, $y.text );
       })?
   ;
   catch [NumberFormatException e]
   {
      throw new RecognitionException();
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
         cal.roll( Calendar.WEEK_OF_YEAR, true );

      // if the next week is explicitly enforced
      if ( nextWeek )
         cal.roll( Calendar.WEEK_OF_YEAR, true );
   }
   ;
   catch[ ParseException pe ]
   {
      throw new RecognitionException();
   }

date_in_X_YMWD [Calendar cal]
   :  IN?             date_in_X_YMWD_distance[$cal]
      (( AND| COMMA ) date_in_X_YMWD_distance[$cal])*
   ;

date_in_X_YMWD_distance [Calendar cal]
   @init
   {
      int amount   = -1;
      int calField = Calendar.YEAR;
   }
   : (   a=NUM_STR { amount = strToNumber( $a.text );      }
       | a=INT     { amount = Integer.parseInt( $a.text ); })
     (     YEARS
       |   MONTHS  { calField = Calendar.MONTH;            }
       |   WEEKS   { calField = Calendar.WEEK_OF_YEAR;     }
       |   DAYS    { calField = Calendar.DAY_OF_YEAR;      })
   {
      if ( amount != -1 )
      {
         cal.roll( calField, amount );
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

date_natural [Calendar cal]
   : (TODAY | TONIGHT)
     {
        cal.setTimeInMillis( System.currentTimeMillis() );
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

time_spec [Calendar cal]
   : (  h=INT
          {
             cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( $h.text ) );
          }
        (   time_floatspec    [$cal]
          | time_separatorspec[$cal]
          | time_naturalspec  [$cal])
      | AT? time_point_in_timespec[$cal])
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }

time_floatspec [Calendar cal ]
   : DOT deciHour=INT HOURS
   {
      cal.set( Calendar.MINUTE, (int)((float)Integer.parseInt( $deciHour.text ) * 0.1f * 60) );
      cal.set( Calendar.SECOND, 0 );
   }
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }

time_separatorspec [Calendar cal]
   @init
   {
      cal.set( Calendar.SECOND, 0 );
   }
   :  COLON m=INT { cal.set( Calendar.MINUTE, Integer.parseInt( $m.text ) ); }
     (COLON s=INT { cal.set( Calendar.SECOND, Integer.parseInt( $s.text ) ); })?
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }

time_naturalspec [Calendar cal]
   :   time_naturalspec_hour  [$cal]
     | time_naturalspec_minute[$cal]
     | time_naturalspec_second[$cal]
   ;

time_naturalspec_hour [Calendar cal]
   : h=INT HOURS
     {
        cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( $h.text ) );
     }
     (  time_naturalspec_minute[$cal]
      | time_naturalspec_second[$cal])?
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }

time_naturalspec_minute [Calendar cal]
   : m=INT MINUTES
     {
        cal.set( Calendar.MINUTE, Integer.parseInt( $m.text ) );
     }
     time_naturalspec_second[$cal]?
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }

time_naturalspec_second [Calendar cal]
   : s=INT SECONDS
     {
        cal.set( Calendar.SECOND, Integer.parseInt( $s.text ) );
     }
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }

time_point_in_timespec [Calendar cal]
   : NEVER
   {
      cal.clear();
   }
   | MIDNIGHT
   {
      cal.set( Calendar.HOUR_OF_DAY, 23 );
      cal.set( Calendar.MINUTE, 59 );
      cal.set( Calendar.SECOND, 59 );
   }
   | (MIDDAY | NOON)
   {
      cal.set( Calendar.HOUR_OF_DAY, 12 );
      cal.set( Calendar.MINUTE, 00 );
      cal.set( Calendar.SECOND, 00 );
   }
   |
   (v=INT
      {
         setCalendarTime( cal, $v.text );
      }
      (time_separatorspec[$cal])?
      (am_pm_spec[$cal])?)
   ;

am_pm_spec [Calendar cal]
   // missing: \u4e0a|\u4e0b | \u5348\u524d|\u5348\u5f8c|\uc624\uc804|\uc624\ud6c4
   : 'a'('m')?
   | 'p'('m')?
   {
      cal.add( Calendar.HOUR_OF_DAY, 12 );
   }
   ;

NEVER     : 'never';

TODAY     : 'today' | 'tod';

TOMORROW  : 'tomorrow' | 'tom' | 'tmr';

YESTERDAY : 'yesterday';

AT        : '@' | 'at';

ON        : 'on';

IN        : 'in';

OF        : 'of';

NEXT      : 'next';

AND       : 'and';

END       : 'end';

THE       : 'the';

STs       : 'st' | 'th' | 'rd' | 'nd';

NOW       : 'now';

TONIGHT   : 'tonight' | 'ton';

MIDNIGHT  : 'midnight';

MIDDAY    : 'midday';

NOON      : 'noon';

YEARS     : 'years' | 'year' | 'yrs' | 'yr';

MONTHS    : 'months' | 'month' | 'mons' | 'mon';

WEEKS     : 'weeks' | 'week' | 'wks' | 'wk';

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

COLON     : ':';

MINUS     : '-';

COMMA     : ',';

INT       : '0'..'9'+;

NUM_STR   : 'one' | 'two' | 'three' | 'four' | 'six' | 'seven' | 'eight' | 'nine' | 'ten';

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) { $channel=HIDDEN; }
          ;
