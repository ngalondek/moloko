// de

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
   package dev.drsoran.moloko.grammar.datetime.de;

   import java.util.Calendar;
   
   import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateReturn;
   import dev.drsoran.moloko.grammar.datetime.AbstractDateParser;
   import dev.drsoran.moloko.grammar.LexerException;   
   import dev.drsoran.moloko.util.MolokoCalendar;
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
      switch( string.charAt( 0 ) )
      {
         case 'e' : return 1;
         case 'z' :
         {
            switch( string.charAt( 1 ) )
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
            switch( string.charAt( 1 ) )
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
      switch( string.charAt( 0 ) )
      {
         case 'm':
            switch( string.charAt( 1 ) )
            {
               case 'o' : return Calendar.MONDAY;
               case 'i' : return Calendar.WEDNESDAY;
            }
         case 'd':
           switch( string.charAt( 1 ) )
            {
               case 'i' : return Calendar.TUESDAY;
               case 'o' : return Calendar.THURSDAY;
            }
         case 's':
            switch( string.charAt( 1 ) )
            {
               case 'a' : return Calendar.SATURDAY;
               case 'o' : return Calendar.SUNDAY;
            }
         default : return Calendar.FRIDAY;
      }
   }
   
   protected int monthStringToNumber( String string )
   {
      switch( string.charAt( 0 ) )
      {
         case 'f': return Calendar.FEBRUARY;           
         case 'm':
         	switch( string.charAt( 2 ) )
            {               
               case 'e' :
               case 'r' : return Calendar.MARCH;
               case 'i' : return Calendar.MAY;
            }
         case 'j':
            switch( string.charAt( 1 ) )
            {
               case 'a' : return Calendar.JANUARY;
               default  :
                  switch( string.charAt( 2 ) )
                  {
                     case 'n' : return Calendar.JUNE;
                     case 'l' : return Calendar.JULY;
                  }
            }      
         case 'a':
            switch( string.charAt( 2 ) )
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
       (   date_full         [$cal]
         | date_on           [$cal]
         | date_in_X_YMWD    [$cal]
         | date_end_of_the_MW[$cal]
         | date_natural      [$cal])
         // It's important to clear the time fields
         // at last step cause the Calendar methods
         // will set them again.
         {
            if ( clearTime )
               cal.setHasTime( false );      
         }
      | NOW
      {
         // In case of NOW we do not respect the clearTime Parameter
         // cause NOW has always a time.
         cal.setTimeInMillis( System.currentTimeMillis() );
         cal.setHasTime( true );
      }
   )
   ;

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
      (OF parseDate[retval.epochStart, false])?
   ;
   catch [NumberFormatException e]
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
   catch [RecognitionException e]
   {
      throw e;
   }

date_natural [MolokoCalendar cal]
   : TODAY
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