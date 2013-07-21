// de

grammar Time;

options
{
   language=Java;
   superClass=AbstractANTLRTimeParser;
}

@header
{
   package dev.drsoran.moloko.grammar.datetime.de;

   import java.util.Calendar;

   import dev.drsoran.moloko.MolokoCalendar;
   import dev.drsoran.moloko.grammar.LexerException;
   import dev.drsoran.moloko.grammar.datetime.ParseReturn;
   import dev.drsoran.moloko.grammar.datetime.AbstractANTLRTimeParser;
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
      - @|at|,? [0-9]+ :|. [0-9]+ (:|. [0-9]+) am|pm?
      - @|at|,? [0-9]{3,4] am|pm?
      - @|at|,? [0-9]{1,2] am|pm?
      - @|at|,? [0-9]+ h|m|s ([0-9]+ h|m|s ([0-9]+ h|m|s)?)? am_pm? ,?
*/
// adjustDay - if this parameter is false, the parser
// assumes that the given cal has been initialized
// with a date yet. E.g. today@12.
// In case of true the parser can adjust the day
// of week for times in the past. E.g. @12.
parseTime [MolokoCalendar cal, boolean adjustDay] returns [ParseReturn result]
   @init
   {
      startParsingTime( cal );
   }
   @after
   {
      result = finishedParsingTime( cal );
   }
   : (AT | COMMA)?
     (
          literal_time[$cal]
        | separated_time[$cal]
        | unseparated_time[$cal]
        | h_m_s_separated_time[$cal]
     )
     am_pm[$cal]?
   {
      if ( adjustDay && getCalendar().after( cal.toCalendar() ) )
      {
         cal.add( Calendar.DAY_OF_WEEK, 1 );
      }
   }
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

literal_time [MolokoCalendar cal]
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
   ;
   catch[ RecognitionException e ]
   {
      throw e;
   }

separated_time [MolokoCalendar cal]
        : h=INT (COLON|DOT) m=INT ((COLON|DOT) s=INT)?
       {
      cal.set( Calendar.HOUR_OF_DAY, $h.int );
      cal.set( Calendar.MINUTE, $m.int );
      cal.set( Calendar.SECOND, $s.int );
       }
        ;
        catch[ RecognitionException e ]
   {
      throw e;
   }

h_m_s_separated_time [MolokoCalendar cal]
        : time_naturalspec[$cal] ( time_naturalspec[$cal] time_naturalspec[$cal]?)?
   ;
   catch[ RecognitionException e ]
   {
      throw e;
   }

unseparated_time [MolokoCalendar cal]
        : v=INT
   {
      setCalendarTime(cal, $v.text);
   }
        ;
        catch[ RecognitionException e ]
   {
      throw e;
   }

am_pm [MolokoCalendar cal]
   : AM
   {
      cal.set(Calendar.AM_PM, Calendar.AM);
   }
   | PM
   {
      cal.set(Calendar.AM_PM, Calendar.PM);
   }




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
   : h=INT (DOT|COMMA) deciHour=INT HOURS
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
