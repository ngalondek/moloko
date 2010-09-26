parser grammar TimeParser;

/*
   TODO:
    - missing time separators: \u0020\u0068\u0020|\u6642|h
   
   LIMITATIONS:
    - '.' is only allowed for dates not times
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
}

@members
{
   private final static Locale LOCALE = Locale.ENGLISH;


   public final static Calendar getLocalizedCalendar()
   {
	   final Calendar cal = Calendar.getInstance( LOCALE );	     	   
      return cal;
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
}

/** RULES **/

// adjustDay - if this parameter is true, the parser
// assumes that the given cal has been initialized
// with a date yet. E.g. today@12.
// In case of false the parser can adjust the day
// of week for times in the past. E.g. @12.
parseTime [Calendar cal, boolean adjustDay]
   : (  h=INT
          {
             cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( $h.text ) );
          }
        (   time_floatspec    [$cal]
          | time_separatorspec[$cal]
          | time_naturalspec  [$cal])
      | AT? time_point_in_timespec[$cal])
   {
   	if ( adjustDay && getLocalizedCalendar().after( cal ) )
   		cal.roll( Calendar.DAY_OF_WEEK, true );
   }
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
   : AM
   | PM
   {
      cal.add( Calendar.HOUR_OF_DAY, 12 );
   }
   ;
