// en

grammar Date;

/*
   TODO:
    - missing time separators: \u0020\u0068\u0020|\u6642|h
*/
options
{
   language=Java;
   superClass=AbstractANTLRDateParser;
}

@header
{
   package dev.drsoran.moloko.grammar.datetime;

   import java.util.Calendar;
   
   import dev.drsoran.moloko.grammar.datetime.ParseReturn;
   import dev.drsoran.moloko.grammar.datetime.ParseDateWithinReturn;
   import dev.drsoran.moloko.grammar.datetime.AbstractANTLRDateParser;
   import dev.drsoran.moloko.grammar.LexerException;
   import dev.drsoran.moloko.grammar.IDateFormatter;
   import dev.drsoran.moloko.MolokoCalendar;
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
   
   
   protected int numberStringToNumber( String string )
   {
   	final String numStr = string.toLowerCase();

      switch( numStr.charAt( 0 ) )
      {
         case 'o' : return 1;
         case 't' :
         {
            switch( numStr.charAt( 1 ) )
            {
               case 'w' : return 2;
               case 'h' : return 3;
            }
         }
         case 'f' :
         {
            switch( numStr.charAt( 1 ) )
            {
               case 'o' : return 4;
               case 'i' : return 5;
            }
         }
         case 's' :
         {
            switch( numStr.charAt( 1 ) )
            {
               case 'i' : return 6;
               case 'e' : return 7;
            }
         }         
         case 'e' : return 8;
         case 'n' : return 9;
         default  : return 10;
      }      
   }
   
   protected int weekdayStringToNumber( String string )
   {
      final String weekDayStr = string.toLowerCase();
     
      switch( weekDayStr.charAt( 0 ) )
      {
         case 'm': return Calendar.MONDAY;
         case 't':
           switch( weekDayStr.charAt( 1 ) )
            {
               case 'u' : return Calendar.TUESDAY;
               case 'h' : return Calendar.THURSDAY;
            }
         case 'w': return Calendar.WEDNESDAY;
         case 's':
            switch( weekDayStr.charAt( 1 ) )
            {
               case 'a' : return Calendar.SATURDAY;
               case 'u' : return Calendar.SUNDAY;
            }
         default : return Calendar.FRIDAY;
      }
   }
   
   protected int monthStringToNumber( String string )
   {
      final String monthStr = string.toLowerCase();
      
      switch( monthStr.charAt( 0 ) )
      {
         case 'f': return Calendar.FEBRUARY;           
         case 'm':
         	switch( monthStr.charAt( 2 ) )
            {
               case 'r' : return Calendar.MARCH;
               case 'y' : return Calendar.MAY;
            }
         case 'j':
            switch( monthStr.charAt( 1 ) )
            {
               case 'a' : return Calendar.JANUARY;
               default  :
                  switch( monthStr.charAt( 2 ) )
                  {
                     case 'n' : return Calendar.JUNE;
                     case 'l' : return Calendar.JULY;
                  }
            }      
         case 'a': 
            switch( monthStr.charAt( 1 ) )
            {
               case 'p' : return Calendar.APRIL;
               case 'u' : return Calendar.AUGUST;
            }
         case 's': return Calendar.SEPTEMBER;
         case 'o': return Calendar.OCTOBER;
         case 'n': return Calendar.NOVEMBER;
         default : return Calendar.DECEMBER;
      }
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

@rulecatch
{
   catch( RecognitionException re )
   {
      notifyParsingDateFailed();
      throw re;
   }
   catch( LexerException le )
   {
      throw new RecognitionException();
   }
}

/** RULES **/


parseDate [MolokoCalendar cal, boolean clearTime] returns [ParseReturn result]
   @init
   {
      startDateParsing( cal );
   }
   @after
   {
      result = finishedDateParsing( cal );
   }
   : (
       (   date_numeric      [$cal]
         | date_on           [$cal]
         | date_in_X_YMWD    [$cal]
         | date_end_of_the_MW[$cal]
         | date_literal      [$cal])
         // It's important to clear the time fields
         // at last step cause the Calendar methods
         // will set them again.
         {
            cal.setHasDate( isSuccess() );
            
            if ( clearTime )
            {
               cal.setHasTime( false );
            }
         }
      | NOW
      {
         cal.setHasDate( true );
         // In case of NOW we do not respect the clearTime Parameter
         // cause NOW has always a time.
         cal.setTimeInMillis( System.currentTimeMillis() );
         cal.setHasTime( true );
      }
   ) EOF
   ;

parseDateWithin[boolean past] returns [ParseDateWithinReturn res]
   @init
   {
      final MolokoCalendar epochStart = getCalendar();
      int amount        = 1;
      int unit          = Calendar.DAY_OF_YEAR;
   }
   @after
   {
      final MolokoCalendar epochEnd = getCalendar();
      epochEnd.setTimeInMillis( epochStart.getTimeInMillis() );
      epochEnd.add( unit, past ? -amount : amount );
      res = new ParseDateWithinReturn(epochStart, epochEnd);
   }
   : (  a=INT
        {
           amount = Integer.parseInt( $a.text );
        }
      | n=NUM_STR
        {
           amount = numberStringToNumber( $n.text );
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
      (OF parseDate[epochStart, false])?
   ;
   catch [NumberFormatException e]
   {
      throw new RecognitionException();
   }

date_numeric [MolokoCalendar cal]
   @init
   {
      String pt1Str = null;
      String pt2Str = null;
      String pt3Str = null;
   }
   : pt1=INT (DOT | MINUS | DATE_SEP)
     {
        pt1Str = $pt1.getText();
     }
     pt2=INT
     {
        pt2Str = $pt2.getText();
     }
     (
        (DOT | MINUS | DATE_SEP)
        (
           pt3=INT
           {
              pt3Str = $pt3.getText();
           }
        )?
     )?
     {
        handleNumericDate( cal, pt1Str, pt2Str, pt3Str );
     }
     ;

date_on [MolokoCalendar cal]
   : ON? (   date_on_Xst_of_M[$cal]
           | date_on_M_Xst   [$cal]
           | date_on_weekday [$cal])
   ;

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
      handleDateOnXstOfMonth( cal, hasYear, true /*hasMonth*/ );
   }
   ;
   catch [NumberFormatException e]
   {
      throw new RecognitionException();
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

date_in_X_YMWD [MolokoCalendar cal]
   :  IN?             date_in_X_YMWD_distance[$cal]
      (( AND| COMMA ) date_in_X_YMWD_distance[$cal])*
   ;

date_in_X_YMWD_distance [MolokoCalendar cal]
   @init
   {
      int amount   = -1;
      int calField = Calendar.YEAR;
   }
   : (   a=NUM_STR { amount = numberStringToNumber( $a.text );}
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

date_literal [MolokoCalendar cal]
   : (TODAY | TONIGHT)
   | TOMORROW
     {
        cal.add( Calendar.DAY_OF_YEAR, 1 );
     }
   | YESTERDAY
     {
        cal.add( Calendar.DAY_OF_YEAR, -1 );
     }
   | NEVER
     {
        cal.setHasDate( false );
        cal.setHasTime( false );
     }
   ;

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

TOMORROW  : 'tom' | 'tmr' | 'tomorrow' | 'tommorow';

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

NUM_STR   : 'one' | 'two' | 'three' | 'four' | 'five' | 'six' | 'seven' | 'eight' | 'nine' | 'ten';

DATE_SEP  : '/' | '\u5E74' | '\u6708' | '\u65E5';

DOT       : '.';

COLON     : ':';

MINUS     : '-';

MINUS_A   : '-a';

COMMA     : ',';

DATE_TIME_SEPARATOR : '@' | 'at' | COMMA;

INT       : '0'..'9'+;

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) { $channel=HIDDEN; }
          ;