// en

grammar Date;

/*
   TODO:
    - missing time separators: \u0020\u0068\u0020|\u6642|h
*/
options
{
   language=Java;
   superClass=AbstractDateParser;
}

@header
{
   package dev.drsoran.moloko.grammar.datetime;

   import java.text.ParseException;
   import java.text.SimpleDateFormat;
   import java.util.Calendar;
   
   import dev.drsoran.moloko.grammar.lang.NumberLookupLanguage;
   import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateReturn;
   import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateWithinReturn;
   import dev.drsoran.moloko.grammar.datetime.AbstractDateParser;
   import dev.drsoran.moloko.grammar.LexerException;
   import dev.drsoran.moloko.util.MolokoCalendar;
}

@lexer::header
{
   package dev.drsoran.moloko.grammar.datetime;

   import dev.drsoran.moloko.grammar.LexerException;
}

@members
{
   public DateParser()
   {
      super( null );
   }
}

/** RULES **/


parseDate [MolokoCalendar cal, boolean clearTime] returns [ParseDateReturn result]
   @after
   {
      result = finishedDateParsing();
   }
   : (   date_full         [$cal]
       | date_on           [$cal]
       | date_in_X_YMWD    [$cal]
       | date_end_of_the_MW[$cal]
       | date_natural      [$cal])
   // It's important to clear the time fields
   // at last step cause the Calendar methods
   // will set them again.
   {
      cal.setHasDate( true );
      
      if ( clearTime )
         cal.setHasTime( false );      
   }
   | NOW
   {
      // In case of NOW we do not respect the clearTime Parameter
      // cause NOW has always a time.
      cal.setTimeInMillis( System.currentTimeMillis() );
      cal.setHasDate( true );
      cal.setHasTime( true );
   }
   | EOF
   ;
   catch [RecognitionException e]
   {
      throw e;
   }
   catch[ LexerException e ]
   {
      throw new RecognitionException();
   }

parseDateWithin[boolean past] returns [MolokoCalendar epochStart, MolokoCalendar epochEnd]
   @init
   {
      retval.epochStart = getCalendar();
      int amount        = 1;
      int unit          = Calendar.DAY_OF_YEAR;
   }
   @after
   {
      retval.epochEnd = getCalendar();
      retval.epochEnd.setTimeInMillis( retval.epochStart.getTimeInMillis() );
      retval.epochEnd.add( unit, past ? -amount : amount );      
   }
   : (  a=INT
        {
           amount = Integer.parseInt( $a.text );
        }
      | n=NUM_STR
        {
           amount = strToNumber( $n.text );
        }
      | A)
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
      )?
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
   catch[ LexerException e ]
   {
      throw new RecognitionException();
   }

date_full [MolokoCalendar cal]
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
        handleFullDate( cal, pt1Str, pt2Str, pt3Str );
     }
     ;

date_on [MolokoCalendar cal]
   : ON? (   date_on_Xst_of_M[$cal]
           | date_on_M_Xst   [$cal]
           | date_on_weekday [$cal])
   ;
   catch [RecognitionException e]
   {
      throw e;
   }

date_on_Xst_of_M [MolokoCalendar cal]
   @init
   {
      boolean hasMonth = false;
      boolean hasYear  = false;
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
      handleDateOnXstOfMonth( cal, hasYear, hasMonth );
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

date_on_M_Xst [MolokoCalendar cal]
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
      if ( !hasYear && getCalendar().after( cal ) )
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

date_on_weekday [MolokoCalendar cal]
   @init
   {
      boolean nextWeek = false;
   }
   : (NEXT { nextWeek = true; })? wd=WEEKDAY
   {
      handleDateOnWeekday( cal, $wd.text, nextWeek );
   }
   ;
   catch [RecognitionException e]
   {
      throw e;
   }

date_in_X_YMWD [MolokoCalendar cal]
   :  IN?             date_in_X_YMWD_distance[$cal]
      (( AND| COMMA ) date_in_X_YMWD_distance[$cal])*
   ;
   catch [RecognitionException e]
   {
      throw e;
   }

date_in_X_YMWD_distance [MolokoCalendar cal]
   @init
   {
      int amount   = -1;
      int calField = Calendar.YEAR;
   }
   : (   a=NUM_STR { amount = strToNumber( $a.text );      }
       | a=INT     { amount = Integer.parseInt( $a.text ); })
     (     YEARS
       |   MONTHS  { calField = Calendar.MONTH;             }
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

date_end_of_the_MW [MolokoCalendar cal]
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

date_natural [MolokoCalendar cal]
   : (TODAY | TONIGHT)
     {
     }
   | NEVER
     {
        cal.setHasDate( false );
        cal.setHasTime( false );
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

// TOKENS

A         : 'a';

OF        : 'of';

ON        : 'on';

IN        : 'in';

AND       : 'and';

END       : 'end';

THE       : 'the';

NOW       : 'now';

TONIGHT   : 'tonight' | 'ton';

NEVER     : 'never';

TODAY     : 'today' | 'tod';

TOMORROW  : 'tomorrow' | 'tom' | 'tmr';

YESTERDAY : 'yesterday';

NEXT      : 'next';

STs       : 'st' | 'th' | 'rd' | 'nd';

YEARS     : 'years' | 'year' | 'yrs' | 'yr';

MONTHS    : 'months' | 'month' | 'mons' | 'mon';

WEEKS     : 'weeks' | 'week' | 'wks' | 'wk';

DAYS      : 'days' | 'day' | 'd';

MONTH     : 'january'   | 'jan'  | 'february' | 'feb'     | 'march' | 'mar'      | 'april' | 'apr' |
            'may'       | 'june' | 'jun'      | 'july'    | 'jul'   | 'august'   | 'aug'   |
            'september' | 'sept' | 'sep'      | 'october' | 'oct'   | 'november' | 'nov'   |
            'december'  | 'dec';

WEEKDAY   : 'monday'   | 'mon' | 'tuesday' | 'tue' | 'wednesday' | 'wed' |
            'thursday' | 'thu' | 'friday'  | 'fri' | 'saturday'  | 'sat' |
            'sunday'   | 'sun';

NUM_STR   : 'one' | 'two' | 'three' | 'four' | 'six' | 'seven' | 'eight' | 'nine' | 'ten';

DATE_SEP  : '/' | '\u5E74' | '\u6708' | '\u65E5';

DOT       : '.';

COLON     : ':';

MINUS     : '-';

MINUS_A   : '-a';

COMMA     : ',';

INT       : '0'..'9'+;

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) { $channel=HIDDEN; }
          ;