parser grammar TimeParser;

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
   public TimeParser()
   {
      super( null );
   }

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

/*
   This parses time in the format:
      - @|at|,? [0-9]+ :|. [0-9]+ am|pm?
      - @|at|,? [0-9]{3,4] am|pm?
      - @|at|,? [0-9]{1,2] am|pm?

   return true in case of EOF.
*/
// adjustDay - if this parameter is false, the parser
// assumes that the given cal has been initialized
// with a date yet. E.g. today@12.
// In case of true the parser can adjust the day
// of week for times in the past. E.g. @12.
parseTime [Calendar cal, boolean adjustDay] returns [boolean eof]
   : (AT | COMMA)? time_point_in_time[$cal]
   {
      if ( adjustDay && getLocalizedCalendar().after( cal ) )
         cal.roll( Calendar.DAY_OF_WEEK, true );
   }
   | EOF
   {
      eof = true;
   }
   ;
   catch[ RecognitionException e ]
   {
      throw e;
   }

time_point_in_time [Calendar cal]
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
   ( ( v=INT
       {
          setCalendarTime( cal, $v.text );
       }
     | h=time_component (COLON|DOT) m=time_component
       {
          cal.set( Calendar.HOUR_OF_DAY, $h.value );
          cal.set( Calendar.MINUTE, $m.value );
          cal.set( Calendar.SECOND, 0 );
       }
     )
     am_pm[$cal]?)
   ;
   catch[ RecognitionException e ]
   {
      throw e;
   }

am_pm [Calendar cal]
   : AM
   | PM
   {
      cal.add( Calendar.HOUR_OF_DAY, 12 );
   }
   ;
   catch[ RecognitionException e ]
   {
      throw e;
   }

/*
   This parses time in the format:
      - @|at|,? [0-9]+(:[0-9]+(:[0-9]+)?)?
      - @|at|,? [0-9]+ h|m|s ([0-9]+ h|m|s ([0-9]+ h|m|s)?)?

   return true in case of EOF.
*/
// adjustDay - if this parameter is false, the parser
// assumes that the given cal has been initialized
// with a date yet. E.g. today@12.
// In case of true the parser can adjust the day
// of week for times in the past. E.g. @12.
parseTimeSpec [Calendar cal, boolean adjustDay] returns [boolean eof]
   @init
   {
       cal.set( Calendar.HOUR_OF_DAY, 0 );
      cal.set( Calendar.MINUTE,      0 );
      cal.set( Calendar.SECOND,      0 );
      cal.set( Calendar.MILLISECOND, 0 );
   }
   : (AT | COMMA)? (   time_separatorspec [$cal]
                     | ( time_naturalspec [$cal]
                       ( time_naturalspec[$cal]
                         time_naturalspec[$cal]?)?)
                   )
   {
      if ( adjustDay && getLocalizedCalendar().after( cal ) )
         cal.roll( Calendar.DAY_OF_WEEK, true );
   }
   | EOF
   {
      eof = true;
   }
   ;
   catch[ RecognitionException e ]
   {
      throw e;
   }

time_separatorspec [Calendar cal]
   : (h=time_component
      {
         cal.set( Calendar.HOUR_OF_DAY, $h.value );
      }
      (COLON m=time_component
       {
          cal.set( Calendar.MINUTE, $m.value );
       }
       (COLON s=time_component
        {
           cal.set( Calendar.SECOND, $s.value );
        })?)?
      )
   ;
   catch[ RecognitionException e ]
   {
      throw e;
   }

time_naturalspec [Calendar cal] returns [int seconds]
   @init
   {
      int calType = -1;
   }
   @after
   {
      if ( cal != null )
         cal.add( Calendar.SECOND, seconds );
   }
   :   fs=hour_floatspec
       {
         seconds += fs;
       }
     | v=INT ( HOURS
               {
                  calType = Calendar.HOUR_OF_DAY;
               }
               | MINUTES
               {
                  calType = Calendar.MINUTE;
               }
               | SECONDS
               {
                  calType = Calendar.SECOND;
               }
             )
       {
         int val = Integer.parseInt( $v.text );

         switch( calType )
         {
            case Calendar.HOUR_OF_DAY:
               seconds += val * 3600;
               break;
            case Calendar.MINUTE:
               seconds += val * 60;
               break;
            case Calendar.SECOND:
               seconds += val;
               break;
            default:
               throw new RecognitionException();
         }
       }
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }
   catch[ RecognitionException e ]
   {
      throw e;
   }

hour_floatspec returns [int seconds]
   : h=INT DOT deciHour=INT HOURS
   {
      seconds = Integer.parseInt( $h.text ) * 3600;
      seconds += Integer.parseInt( $deciHour.text ) * 360;
   }
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }
   catch[ RecognitionException e ]
   {
      throw e;
   }

/*
   This parses time in the format:
      - [0-9]+ d|h|m|s ([0-9]+ d|h|m|s ([0-9]+ d|h|m|s ([0-9]+ d|h|m|s)?)?)?

   returns the parsed time span in milliseconds.
*/
parseTimeEstimate returns [long span]
   @after
   {
      span *= 1000;
   }
   : (   d=INT DAYS
         {
            span += Integer.parseInt( $d.text ) * 3600 * 24;
         }
       | ts=time_naturalspec[null]
       {
         span += ts;
       }
     )+
   | EOF
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }
   catch[ RecognitionException e ]
   {
      throw e;
   }

time_component returns [int value]
   : c = INT
   {
      String comp = $c.text;

      if ( comp.length() > 2 )
         comp = comp.substring( 0, 2 );

      value = Integer.parseInt( comp );
   }
   ;
   catch[ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }