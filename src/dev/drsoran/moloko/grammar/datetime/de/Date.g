// de

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
   package dev.drsoran.moloko.grammar.datetime.de;

   import java.util.Calendar;
   
   import dev.drsoran.moloko.grammar.datetime.ParseDateReturn;
   import dev.drsoran.moloko.grammar.datetime.ParseDateWithinReturn;
   import dev.drsoran.moloko.grammar.datetime.AbstractANTLRDateParser;
   import dev.drsoran.moloko.grammar.LexerException;
   import dev.drsoran.moloko.grammar.IDateFormatter;
   import dev.drsoran.moloko.MolokoCalendar;
}

@lexer::header
{
   package dev.drsoran.moloko.grammar.datetime.de;
   
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
         case 'e' : return 1;
         case 'z' :
         {
            switch( numStr.charAt( 1 ) )
            {
               case 'w' : return 2;
               case 'e' : return 10;
            }
         }
         case 'd' : return 3;         
         case 'v' : return 4;
         case 'f' : return 5;
         case 's' :
         {
            switch( numStr.charAt( 1 ) )
            {
               case 'e' : return 6;
               case 'i' : return 7;
            }
         }
         case 'a' : return 8;        
         default  : return 9;
      }      
   }
   
   protected int weekdayStringToNumber( String string )
   {
      final String weekDayStr = string.toLowerCase();
      
      switch( weekDayStr.charAt( 0 ) )
      {
         case 'm':
            switch( weekDayStr.charAt( 1 ) )
            {
               case 'o' : return Calendar.MONDAY;
               case 'i' : return Calendar.WEDNESDAY;
            }
         case 'd':
           switch( weekDayStr.charAt( 1 ) )
            {
               case 'i' : return Calendar.TUESDAY;
               case 'o' : return Calendar.THURSDAY;
            }
         case 's':
            switch( weekDayStr.charAt( 1 ) )
            {
               case 'a' : return Calendar.SATURDAY;
               case 'o' : return Calendar.SUNDAY;
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
               case 'e' : // Maerz
               case 'r' : return Calendar.MARCH; // März
               case 'i' : return Calendar.MAY;
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


parseDate [MolokoCalendar cal, boolean clearTime] returns [ParseDateReturn result]
   @init
   {
      startDateParsing( cal );
   }
   @after
   {
      result = finishedDateParsing( cal );
   }
   :(
       (   date_numeric      [$cal]
         | date_on           [$cal]
         | date_in_X_YMWD    [$cal]
         | date_end_of_the_MW[$cal]
         | date_natural      [$cal])
         // It's important to clear the time fields
         // at last step cause the Calendar methods
         // will set them again.
         {
            cal.setHasDate( isSuccess() );

            if ( clearTime )
               cal.setHasTime( false );      
         }
      | NOW
      {
         cal.setHasDate( true );

         // In case of NOW we do not respect the clearTime Parameter
         // cause NOW has always a time.
         cal.setTimeInMillis( System.currentTimeMillis() );
         cal.setHasTime( true );
      }
      | NEVER
      {
         cal.setHasDate( false );
         cal.setHasTime( false );
      }
   )
   ;

parseDateWithin[boolean past] returns [ParseDateWithinReturn res]
   @init
   {  
      final MolokoCalendar epochStart = getCalendar();
      int amount                      = 1;
      int unit                        = Calendar.DAY_OF_YEAR;
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
           | date_on_weekday [$cal])
   ;

date_on_Xst_of_M [MolokoCalendar cal]
   @init
   {
      boolean hasMonth = false;
      boolean hasYear  = false;
   }
   : d=INT DOT?
       {
          cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( $d.text ) );
       }
      (OF_THE? m=MONTH
             {
                parseTextMonth( cal, $m.text );
                hasMonth = true;
             }
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
   :  IN?          date_in_X_YMWD_distance[$cal]
      ((AND|COMMA) date_in_X_YMWD_distance[$cal])*
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
   : END OF_THE?
     (   WEEKS
         {
            rollToEndOf( Calendar.DAY_OF_WEEK, cal );
         }
       | MONTHS
         {
            rollToEndOf( Calendar.DAY_OF_MONTH, cal );
         })
   ;

date_natural [MolokoCalendar cal]
   : TODAY
     {
     }
   | TOMORROW
     {
        cal.add( Calendar.DAY_OF_YEAR, 1 );
     }
   | YESTERDAY
     {
        cal.add( Calendar.DAY_OF_YEAR, -1 );
     }
   ;

// TOKENS

A         : 'ein'('e'|'er'|'es'|'em'|'en')?;

ON        :	'am' | 'an';

OF        : 'von' | 'vom' | 'ab' | 'seit';	

OF_THE    : 'der' | 'die' | 'das' | 'dem' | 'den' | 'des';

IN        : 'in';

AND       : 'und';

END       : 'ende';

NOW       : 'jetzt';

NEVER     : 'nie';

TODAY     : 'heute';

TOMORROW  : 'morgen' | 'mrg';

YESTERDAY : 'gestern';

NEXT      : 'n'('a'|'ae'|'ä' )'chst'('e'|'er'|'es'|'en'|'em');

YEARS     : 'jahr'SUFF_MALE?;

MONTHS    : 'monat'SUFF_MALE?;

WEEKS     : 'woche'SUFF_FMALE?;

DAYS      : 'tag'SUFF_MALE?;

MONTH     :   'jan'                | 'januar'SUFF_MALE?              
            | 'feb'                | 'februar'SUFF_MALE?
            | 'm'('a'|'ä'|'ae')'r' | 'm'('a'|'ä'|'ae')'rz'SUFF_MALE?
            | 'mai'SUFF_FMALE?     
            | 'jun'                | 'juni'SUFF_MALE?
            | 'jul'                | 'juli'SUFF_MALE?               
            | 'aug'                | 'august'SUFF_MALE?
            | 'sep'                | 'september'SUFF_MALE?
            | 'okt'                | 'oktober'SUFF_MALE?
            | 'nov'                | 'november'SUFF_MALE?
            | 'dez'                | 'dezember'SUFF_MALE?;

WEEKDAY   :    'mo' | 'montag'SUFF_MALE?
             | 'di' | 'dienstag'SUFF_MALE?
             | 'mi' | 'mittwoch'SUFF_MALE?
             | 'do' | 'donnestag'SUFF_MALE?
             | 'fr' | 'freitag'SUFF_MALE?
             | 'sa' | 'samstag'SUFF_MALE?
             | 'so' | 'sonntag'SUFF_MALE?;

NUM_STR   : 'eins'  | 'zwei'   | 'drei' | 'vier' | 'f'('u'|'ü'|'ue')'nf' |
            'sechs' | 'sieben' | 'acht' | 'neun' | 'zehn';

DATE_SEP  : '/' | '\u5E74' | '\u6708' | '\u65E5';

DOT       : '.';

COLON     : ':';

MINUS     : '-';

COMMA     : ',';

DATE_TIME_SEPARATOR : '@' | 'um' | COMMA;

fragment
SUFF_MALE : 'e'|'s'|'es'|'en';

SUFF_FMALE : 'n';

INT       : '0'..'9'+;

WS        : ( ' '
          |   '\t'
          |   '\r'
          |   '\n' ) { $channel=HIDDEN; }
          ;