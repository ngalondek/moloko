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
   
   import dev.drsoran.moloko.grammar.lang.NumberLookupLanguage;
   import dev.drsoran.moloko.util.MolokoCalendar;
}

@members
{
   private NumberLookupLanguage numberLookUp;
   

   public DateParser()
   {
      super( null );
   }



   public void setNumberLookUp( NumberLookupLanguage numberLookUp )
   {
      this.numberLookUp = numberLookUp;
   }

   

   public final static MolokoCalendar getCalendar()
   {
      final MolokoCalendar cal = MolokoCalendar.getInstance();

      cal.setHasTime( false );

      return cal;
   }



   private final int strToNumber( String string )
   {
      int res = -1;

      final Integer val = numberLookUp.get( string );

      if ( val != null )
         res = val;

      return res;
   }



   private final int getMonthNumber( String month )
   {    
      // Only take the 1st three chars of the month as key.
      return strToNumber( month.substring( 0, 3 ) );
   }



   private final int getWeekdayNumber( String weekday )
   {    
      // Only take the 1st two chars of the weekday as key.
      return strToNumber( weekday.substring( 0, 2 ) );
   }



   private final void parseFullDate( MolokoCalendar cal,
                                     String         day,
                                     String         month,
                                     String         year,
                                     boolean        textMonth ) throws RecognitionException
   {
      try
      {
         final StringBuffer pattern = new StringBuffer( "dd.MM" );

         int monthNum = -1;

         if ( textMonth )
         {
            monthNum = getMonthNumber( month );
         }
         else
         {
            monthNum = Integer.parseInt( month );
         }
         
         if ( monthNum == -1 )
         	throw new RecognitionException();

         if ( year != null )
            pattern.append( ".yyyy" );
         
         final SimpleDateFormat sdf = new SimpleDateFormat( pattern.toString() );

         sdf.parse( day + "." + monthNum + ( ( year != null ) ? "." + year : "" ) );

         final Calendar sdfCal = sdf.getCalendar();

         cal.set( Calendar.DAY_OF_MONTH, sdfCal.get( Calendar.DAY_OF_MONTH ) );
         cal.set( Calendar.MONTH,        sdfCal.get( Calendar.MONTH ) );

         if ( year != null )
            cal.set( Calendar.YEAR, sdfCal.get( Calendar.YEAR ) );
      }
      catch( NumberFormatException nfe )
      {
         throw new RecognitionException();
      }
      catch( ParseException e )
      {
         throw new RecognitionException();
      }
   }



   private final void parseTextMonth( MolokoCalendar cal,
                                      String         month ) throws RecognitionException
   {
      final int monthNum = getMonthNumber( month );
         
      if ( monthNum == -1 )
       	throw new RecognitionException();

      cal.set( Calendar.MONTH, monthNum );
   }



   private final static void parseYear( MolokoCalendar cal, String yearStr ) throws RecognitionException
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



   private final static void rollToEndOf( int field, MolokoCalendar cal )
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


parseDate [MolokoCalendar cal, boolean clearTime] returns [boolean eof]
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
   {
      eof = true;
   }
   ;
   catch [RecognitionException e]
   {
      throw e;
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
        final boolean yearFirst
           = pt3Str != null && pt1Str.length() > 2;

        parseFullDate( $cal,
                       yearFirst ? pt2Str : pt1Str, // day
                       yearFirst ? pt3Str : pt2Str, // month
                       yearFirst ? pt1Str : pt3Str, // year
                       false );

       // if year is missing and the date is
       // before now we roll to the next year.
       if ( pt3Str == null )
       {
          final MolokoCalendar now = getCalendar();

          if ( cal.before( now ) )
          {
             cal.add( Calendar.YEAR, 1 );
          }
       }
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
      final MolokoCalendar now = getCalendar();
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
      final int parsedWeekDay  = getWeekdayNumber( $wd.text );
      
      if ( parsedWeekDay == -1 )
      	throw new RecognitionException();
      
      final int currentWeekDay = cal.get( Calendar.DAY_OF_WEEK );

      cal.set( Calendar.DAY_OF_WEEK, parsedWeekDay );

      // If:
      // - the weekday is before today or today,
      // - today is sunday
      // we adjust to next week.
      if (    parsedWeekDay  <= currentWeekDay
           || currentWeekDay == Calendar.SUNDAY )
         cal.add( Calendar.WEEK_OF_YEAR, 1 );

      // if the next week is explicitly enforced
      if ( nextWeek )
         cal.add( Calendar.WEEK_OF_YEAR, 1 );
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
