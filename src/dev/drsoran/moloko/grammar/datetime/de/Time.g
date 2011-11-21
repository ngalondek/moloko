// de

grammar Time;

options
{
   language=Java;
   superClass=AbstractTimeParser;
}

@header
{
   package dev.drsoran.moloko.grammar.datetime.de;

   import java.util.Calendar;
   
   import dev.drsoran.moloko.util.MolokoCalendar;
   import dev.drsoran.moloko.grammar.datetime.ITimeParser.ParseTimeReturn;
   import dev.drsoran.moloko.grammar.datetime.AbstractTimeParser;
   import dev.drsoran.moloko.grammar.LexerException;
}

@lexer::header
{
   package dev.drsoran.moloko.grammar.datetime.de;
   
   import dev.drsoran.moloko.grammar.LexerException;
}

@members
{
   public TimeParser()
   {
      super( null );
   }
}

@lexer::members
{
   @Override
   public void reportError( RecognitionException e )
   {
      throw new LexerException( e );
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
parseTime [MolokoCalendar cal, boolean adjustDay] returns [ParseTimeReturn result]
   @init
   {
      startParsingTime( cal );
   }   
   @after
   {
      result = finishedParsingTime( cal );
   }
   : (AT | COMMA)? time_point_in_time[$cal]   
   {
      if ( adjustDay && getCalendar().after( cal.toCalendar() ) )
         cal.roll( Calendar.DAY_OF_WEEK, true );
      
      cal.setHasTime( true );
   }
   | EOF
   ;   
   catch[ RecognitionException e ]
   {
      notifyParsingTimeFailed();
      throw e;
   }
   catch[ LexerException e ]
   {
      notifyParsingTimeFailed();   
      throw new RecognitionException();
   }

time_point_in_time [MolokoCalendar cal]
   : NEVER
   {
      cal.setHasDate( false );
      cal.setHasTime( false );
   }
   | MIDNIGHT
   {
      cal.set( Calendar.HOUR_OF_DAY, 23 );
      cal.set( Calendar.MINUTE, 59 );
      cal.set( Calendar.SECOND, 59 );
   }
   | MIDDAY
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
   
am_pm [MolokoCalendar cal]
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
      - @|at|,? [0-9]+(:[0-9]+(:[0-9]+)?)? am_pm? ,?
      - @|at|,? [0-9]+ h|m|s ([0-9]+ h|m|s ([0-9]+ h|m|s)?)? am_pm? ,?

   return true in case of EOF.
*/
// adjustDay - if this parameter is false, the parser
// assumes that the given cal has been initialized
// with a date yet. E.g. today@12.
// In case of true the parser can adjust the day
// of week for times in the past. E.g. @12.
parseTimeSpec [MolokoCalendar cal, boolean adjustDay] returns [ParseTimeReturn result]
   @init
   {
      startParsingTime( cal );
      
      cal.set( Calendar.HOUR_OF_DAY, 0 );
      cal.set( Calendar.MINUTE,      0 );
      cal.set( Calendar.SECOND,      0 );
      cal.set( Calendar.MILLISECOND, 0 );
   }
   @after
   {
      result = finishedParsingTime( cal );
   }
   : (AT | COMMA)? (   time_separatorspec[$cal]
                     | ( time_naturalspec[$cal]
                       ( time_naturalspec[$cal]
                         time_naturalspec[$cal]?)?)
                   )
      am_pm[$cal]? COMMA?
   {
      if ( adjustDay && getCalendar().after( cal.toCalendar() ) )
         cal.roll( Calendar.DAY_OF_WEEK, true );
      
      cal.setHasTime( true );
   }
   | EOF
   ;
   catch[ RecognitionException e ]
   {
      notifyParsingTimeFailed();   
      throw e;
   }
   catch[ LexerException e ]
   {
      notifyParsingTimeFailed();   
      throw new RecognitionException();
   }

time_separatorspec [MolokoCalendar cal]
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

time_naturalspec [MolokoCalendar cal] returns [int seconds]
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
       | (COMMA | AND)? ts=time_naturalspec[null]
       {
         span += ts;
       }
     )+ (COMMA | AND)*
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
   catch[ LexerException e ]
   {
      throw new RecognitionException();
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
   
// TOKENS

AT        : '@' | 'um' | 'am' | 'an';

AND       : 'und';

NEVER     : 'nie';

MIDNIGHT  : 'mitternacht''s'?;

MIDDAY    : 'mittag''s'?;

DAYS      : 'tage' | 'tag' | 'd';

HOURS     : 'stunden'  | 'stunde'  | 'std'         | 'h';

MINUTES   : 'minuten'  | 'minute'  | 'min'         | 'm';

SECONDS   : 'sekunden' | 'sekunde' | 'se'('c'|'k') | 's';

AM        : 'vorm'DOT? | 'vormittag'('s')?;

PM        : 'nachm'DOT? | 'nachmittag'('s')?;

COMMA     : ',';

DOT       : '.';

COLON     : ':';

INT       : '0'..'9'+;

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) { $channel=HIDDEN; }
          ;
