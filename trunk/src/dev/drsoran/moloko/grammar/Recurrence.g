grammar Recurrence;

@option
{
   language=Java;
}

@header
{
   package dev.drsoran.moloko.grammar;

   import java.text.ParseException;
   import java.text.SimpleDateFormat;
   import java.util.Calendar;
   import java.util.Comparator;
   import java.util.HashMap;
   import java.util.Iterator;
   import java.util.Locale;
   import java.util.Set;
   import java.util.TreeSet;
}

@lexer::header
{
   package dev.drsoran.moloko.grammar;
}

@members
{
   private final static Locale LOCALE = Locale.ENGLISH;

   private final static class CmpWeekday implements Comparator< String >
   {
      private final static int weekdayToInt( String wd )
      {
         // only take the last 2 chars, the leading chars can be
         // Xst values.
         final String weekday = wd.substring( wd.length() - 2 );
   
         if ( weekday.equals( BYDAY_MON ) )
            return 1;
         else if ( weekday.equals( BYDAY_TUE ) )
            return 2;
         else if ( weekday.equals( BYDAY_WED ) )
            return 3;
         else if ( weekday.equals( BYDAY_THU ) )
            return 4;
         else if ( weekday.equals( BYDAY_FRI ) )
            return 5;
         else if ( weekday.equals( BYDAY_SAT ) )
            return 6;
         else if ( weekday.equals( BYDAY_SUN ) )
            return 7;
         else
            return 1;
      }

      public int compare( String wd1, String wd2 )
      {
         return weekdayToInt( wd1 ) - weekdayToInt( wd2 );
      }
   }
   
   

   private final static CmpWeekday CMP_WEEKDAY  = new CmpWeekday();

   public final static String OP_BYDAY_LIT      = "BYDAY";

   public final static String   OP_BYMONTH_LIT  = "BYMONTH";

   public final static String OP_BYMONTHDAY_LIT = "BYMONTHDAY";

   public final static String OP_INTERVAL_LIT   = "INTERVAL";

   public final static String OP_FREQ_LIT       = "FREQ";

   public final static String OP_UNTIL_LIT      = "UNTIL";

   public final static String OP_COUNT_LIT      = "COUNT";

   public final static String VAL_DAILY_LIT     = "DAILY";

   public final static String VAL_WEEKLY_LIT    = "WEEKLY";

   public final static String VAL_MONTHLY_LIT   = "MONTHLY";

   public final static String VAL_YEARLY_LIT    = "YEARLY";

   public final static String IS_EVERY          = "IS_EVERY";

   public final static String BYDAY_MON         = "MO";

   public final static String BYDAY_TUE         = "TU";

   public final static String BYDAY_WED         = "WE";

   public final static String BYDAY_THU         = "TH";

   public final static String BYDAY_FRI         = "FR";

   public final static String BYDAY_SAT         = "SA";

   public final static String BYDAY_SUN         = "SU";



   @SuppressWarnings( "unchecked" )
   private final static String join( String delim, Iterable values )
   {
      StringBuilder result = new StringBuilder();

      final Iterator i = values.iterator();

      for ( boolean hasNext = i.hasNext(); hasNext; )
      {
         result.append( i.next() );
         hasNext = i.hasNext();

         if ( hasNext )
            result.append( delim );
      }

      return result.toString();
   }
   
   
   private final static void addStToX( StringBuilder sb, int x )
   {
      final String xStr = String.valueOf( x );         
      sb.append( xStr );
      
      if ( x > 3 && x < 20 )
      {
         sb.append( "th" );
      }
      else
      {
         final char lastNum = xStr.charAt( xStr.length() - 1 );
         
         switch( lastNum )
         {
            case '1' : sb.append( "st" ); break;
            case '2' : sb.append( "nd" ); break;
            case '3' : sb.append( "rd" ); break;
            default  : sb.append( "th" ); break;
         }
      }
   }
}

// RULES

parseRecurrence returns[HashMap< String, Object > res]
   @init
   {
      res                               = new HashMap< String, Object >();
      Boolean isEvery                   = Boolean.FALSE;

      final TreeSet< String >  weekdays = new TreeSet< String >( CMP_WEEKDAY );
      final TreeSet< Integer > ints     = new TreeSet< Integer >();

      interval                          = 1;
      String freq                       = null;
      String resolution                 = null;
      String resolutionVal              = null;
   }
   @after
   {
      res.put( OP_FREQ_LIT, freq );
      res.put( OP_INTERVAL_LIT, new Integer( interval ) );

      if ( resolution != null && resolutionVal != null )
         res.put( resolution, resolutionVal );

      res.put( IS_EVERY, isEvery );
   }
   : (EVERY { isEvery = Boolean.TRUE; } | AFTER)?
   (interval=parse_Number?
   (
        DAYS                                               { freq = VAL_DAILY_LIT;  }
      | (
             WEEKS
           | BIWEEKLY
             {
                interval = 2;
             }
         )                                                 { freq = VAL_WEEKLY_LIT; }
         (ON? THE? recurr_WD[weekdays, ""]
               {
                  resolution = OP_BYDAY_LIT;
                  resolutionVal = join( ",", weekdays );
               }
         )?
      | MONTHS                                             { freq = VAL_MONTHLY_LIT;}
         (ON? THE? r=recurr_Monthly[weekdays, ints]
                   {
                      freq          = r.freq;
                      interval      = r.interval;
                      resolution    = r.resolution;
                      resolutionVal = r.resolutionVal;
                   }
         )?
      | YEARS                                              { freq = VAL_YEARLY_LIT; }
         (ON? THE? r=recurr_Monthly[weekdays, ints]
                   {
                      freq          = r.freq;
                      interval      = r.interval;
                      resolution    = r.resolution;
                      resolutionVal = r.resolutionVal;
                   }
                   { r.hasWD }?=> (
                                    (IN | OF)?
                                     m=parse_Month
                                     {
                                        freq       = VAL_YEARLY_LIT;
                                        interval = 1;
                                        res.put( OP_BYMONTH_LIT, Integer.toString( m ) );
                                     }
                                  )?
         )?
   )
   | recurr_Xst[ints]
     {
        freq          = VAL_MONTHLY_LIT;
        interval      = 1;
        resolution    = OP_BYMONTHDAY_LIT;
        resolutionVal = join( ",", ints );
     }
   | recurr_WD[weekdays, ""]
     {
        freq          = VAL_WEEKLY_LIT;
        interval      = 1;
        resolution    = OP_BYDAY_LIT;
        resolutionVal = join( ",", weekdays );
     }
   | (
        firstEntry=recurr_Xst[ints] recurr_WD[weekdays, ""]
        {
           freq          = VAL_WEEKLY_LIT;
           interval      = firstEntry;
           resolution    = OP_BYDAY_LIT;
           resolutionVal = join( ",", weekdays );
        }
     )
   )
   | EOF
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

recurr_Xst [Set< Integer > res] returns [int firstEntry]
   : x=parse_Xst               { res.add( x ); firstEntry = x; }
   (((AND | COMMA) x=parse_Xst { res.add( x ); })+)?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

recurr_WD [Set< String > weekdays, String Xst]
   : parse_Weekday              [weekdays, Xst, true]
   (((AND | COMMA) parse_Weekday[weekdays, Xst, true])+)?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }
   catch [ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }

recurr_Monthly [Set< String >  weekdays,
                Set< Integer > ints     ] returns [String  freq,
                                                   String  resolution,
                                                   String  resolutionVal,
                                                   int     interval,
                                                   boolean hasWD]
   @init
   {
      retval.freq     = VAL_MONTHLY_LIT;
      retval.interval = 1;
   }
   : firstEntry = recurr_Xst[ints]
     {
        retval.resolution    = OP_BYMONTHDAY_LIT;
        retval.resolutionVal = join( ",", ints );
     } (
          (
             LAST
             {
                firstEntry = -firstEntry;
             }
          )?
          recurr_WD[weekdays, Integer.toString( firstEntry )]
          {
             retval.hasWD         = true;
             retval.resolution    = OP_BYDAY_LIT;
             retval.resolutionVal = join( ",", weekdays );
          }
       )?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

parse_Xst returns [int number]
   : n=INT (DOT|ST_S)?
   {
      number = Integer.parseInt( $n.text );

      if ( number < 1 )
         number = 1;
      else if ( number > 31 )
         number = 31;
   }
   | FIRST            { number = 1; }
   | (SECOND | OTHER) { number = 2; }
   | THIRD            { number = 3; }
   | FOURTH           { number = 4; }
   | FIFTH            { number = 5; }
   ;
   catch [ RecognitionException e ]
   {
      return 1;
   }
   catch [ NumberFormatException nfe ]
   {
      return 1;
   }

parse_Number returns [int number]
   : n=INT      { number = Integer.parseInt( $n.text ); }
   | NUM_ONE    { number = 1; }
   | NUM_TWO    { number = 2; }
   | NUM_THREE  { number = 3; }
   | NUM_FOUR   { number = 4; }
   | NUM_FIVE   { number = 5; }
   | NUM_SIX    { number = 6; }
   | NUM_SEVEN  { number = 7; }
   | NUM_EIGHT  { number = 8; }
   | NUM_NINE   { number = 9; }
   | NUM_TEN    { number = 10; }
   ;
   catch [ RecognitionException e ]
   {
      return 1;
   }
   catch [ NumberFormatException nfe ]
   {
      return 1;
   }

parse_Weekday [Set< String > weekdays, String Xst, boolean strict] returns [String weekday]
   : MONDAY      { weekdays.add( Xst + BYDAY_MON ); }
   | TUESDAY     { weekdays.add( Xst + BYDAY_TUE ); }
   | WEDNESDAY   { weekdays.add( Xst + BYDAY_WED ); }
   | THURSDAY    { weekdays.add( Xst + BYDAY_THU ); }
   | FRIDAY      { weekdays.add( Xst + BYDAY_FRI ); }
   | SATURDAY    { weekdays.add( Xst + BYDAY_SAT ); }
   | SUNDAY      { weekdays.add( Xst + BYDAY_SUN ); }
   | WEEKEND     {
                    weekdays.add( Xst + BYDAY_SAT );
                    weekdays.add( Xst + BYDAY_SUN );
                 }
   | WEEKDAY_LIT {
                    weekdays.add( Xst + BYDAY_MON );
                    weekdays.add( Xst + BYDAY_TUE );
                    weekdays.add( Xst + BYDAY_WED );
                    weekdays.add( Xst + BYDAY_THU );
                    weekdays.add( Xst + BYDAY_FRI );
                 }
   ;
   catch [ RecognitionException e ]
   {
      if ( strict )
         throw e;
      else
         return null;
   }

parse_Month returns [int number]
   : m=MONTH
   {
      try
      {
         final SimpleDateFormat sdf = new SimpleDateFormat( "MMM", LOCALE );
         sdf.parse( $m.text );

         number = sdf.getCalendar().get( Calendar.MONTH );

         if ( number == 0 )
            ++number;
      }
      catch( ParseException e )
      {
         number = 1;
      }
   }
   ;
   catch [ RecognitionException e ]
   {
      return 1;
   }

parseRecurrencePattern [boolean every] returns [String sentence]
   @init
   {
      final StringBuilder sb = new StringBuilder( every ? "every " : "after " );
   }
   @after
   {
      sentence = sb.toString();
   }
   : OP_FREQ
     ( 
          (
             VAL_YEARLY parse_PatternInterval[sb, "year", "years"]
             (
                {
                   sb.append( " on the " );
                }
                (
                   (OP_BYDAY parse_PatternWeekday[sb, true]
                      (COMMA { sb.append( ", " ); } parse_PatternWeekday[sb, false])*)
                   {
                      sb.append( " in " );
                   }
                   OP_BYMONTH parse_PatternMonth   [sb]
                )
             )?
          )          
        | (
             VAL_MONTHLY parse_PatternInterval[sb, "month", "months"]
             (
                 {
                   sb.append( " on the " );
                }
                (
                     (
                        OP_BYMONTHDAY parse_PatternXst[sb]
                           (COMMA { sb.append( ", " ); } parse_PatternXst[sb])*
                     )
                   | (
                        OP_BYDAY parse_PatternWeekday[sb, true]
                            (COMMA { sb.append( ", " ); } parse_PatternWeekday[sb, false])*
                     )
                )
             )?
          )
        | (
             VAL_WEEKLY parse_PatternInterval[sb, "week", "weeks"]
          )
        | (
             VAL_DAILY parse_PatternInterval[sb, "daily", "days"]
          )
     )
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }
   
parse_PatternInterval [StringBuilder sb, String unit, String unitPlural]
   : OP_INTERVAL interval=INT
     {
        if ( $interval.text.equals( "1" ) )
           sb.append( unit );
        else
           sb.append( $interval.text ).append( " " ).append( unitPlural );
     }
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }
   
parse_PatternXst [StringBuilder sb]
   : x=INT
     {
        if ( sb != null )
        {
           final int xSt = Integer.parseInt( $x.text );
              
           if ( xSt < 0 )
           {
              if ( xSt == -1 )
                 sb.append( "last" );
              else
              {
                 addStToX( sb, xSt * -1 );
                 sb.append( " last" );
              }
           }
           else
              addStToX( sb, xSt );
        }
     }
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }
   
parse_PatternWeekday [StringBuilder sb, boolean respectXst]
   : (
        (
           parse_PatternXst[respectXst ? sb : null]
           {
              if ( respectXst )
                 sb.append( " " );
           }
        )?
        (
             MONDAY    { sb.append( "Monday"    ); }
           | TUESDAY   { sb.append( "Tuesday"   ); }
           | WEDNESDAY { sb.append( "Wednesday" ); }
           | THURSDAY  { sb.append( "Thursday"  ); }
           | FRIDAY    { sb.append( "Friday"    ); }
           | SATURDAY  { sb.append( "Saturday"  ); }
           | SUNDAY    { sb.append( "Sunday"    ); }
        )
     )
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }
   catch [ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }
   
parse_PatternMonth [StringBuilder sb]
   : m=INT
     {
        final int month = Integer.parseInt( $m.text );
        
        switch( month )
        {
           case 1  : sb.append( "January"   ); break;
           case 2  : sb.append( "February"  ); break;
           case 3  : sb.append( "March"     ); break;
           case 4  : sb.append( "April"     ); break;
           case 5  : sb.append( "May"       ); break;
           case 6  : sb.append( "June"      ); break;
           case 7  : sb.append( "July"      ); break;
           case 8  : sb.append( "August"    ); break;
           case 9  : sb.append( "September" ); break;
           case 10 : sb.append( "October"   ); break;
           case 11 : sb.append( "November"  ); break;
           case 12 : sb.append( "December"  ); break;
           default : break;
        }
     }
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }
   catch [ NumberFormatException nfe ]
   {
      throw new RecognitionException();
   }



// TOKENS

EVERY         : 'every' | 'each';

AFTER         : 'after';

BIWEEKLY      : 'fortnight' | 'biweekly';

YEARS         : 'years' | 'year' | 'yrs' | 'yr';

MONTHS        : 'months' | 'month' | 'mons' | 'mon';

WEEKS         : 'weeks' | 'week' | 'wks' | 'wk';

DAYS          : 'days' | 'day';

MONTH         : 'january'   | 'jan'  | 'february' | 'feb'     | 'march' | 'mar'      | 'april' | 'apr' |
                'may'       | 'june' | 'jun'      | 'july'    | 'jul'   | 'august'   | 'aug'   |
                'september' | 'sept' | 'sep'      | 'october' | 'oct'   | 'november' | 'nov'   |
                'december'  | 'dec';

WEEKDAY_LIT   : 'weekday''s'?;

WEEKEND       : 'weekend''s'?;

MONDAY        : 'monday'    | 'mon' | 'MO';

TUESDAY       : 'tuesday'   | 'tue' | 'TU';

WEDNESDAY     : 'wednesday' | 'wed' | 'WE';

THURSDAY      : 'thursday'  | 'thu' | 'TH';

FRIDAY        : 'friday'    | 'fri' | 'FR';

SATURDAY      : 'saturday'  | 'sat' | 'SA';

SUNDAY        : 'sunday'    | 'sun' | 'SU';

FIRST         : 'first';

SECOND        : 'second';

THIRD         : 'third';

FOURTH        : 'fourth';

FIFTH         : 'fifth';

LAST          : 'last';

OTHER         : 'other';

ST_S          : 'st' | 'nd' | 'rd' | 'th';

NUM_ONE       : 'one';

NUM_TWO       : 'two';

NUM_THREE     : 'three';

NUM_FOUR      : 'four';

NUM_FIVE      : 'five';

NUM_SIX       : 'six';

NUM_SEVEN     : 'seven';

NUM_EIGHT     : 'eight';

NUM_NINE      : 'nine';

NUM_TEN       : 'ten';

AND           : 'and';

IN            : 'in';

ON            : 'on';

OF            : 'of';

THE           : 'the';

UNTIL         : 'until';

FOR           : 'for';

DOT           : '.';

SEMICOLON     : ';' { $channel=HIDDEN; };

EQUALS        : '=' { $channel=HIDDEN; };

MINUS         : '-';

COMMA         : ',';

OP_BYDAY      : 'BYDAY';

OP_BYMONTH    : 'BYMONTH';

OP_BYMONTHDAY : 'BYMONTHDAY';

OP_INTERVAL   : 'INTERVAL';

OP_FREQ       : 'FREQ';

OP_UNTIL      : 'UNTIL';

OP_COUNT      : 'COUNT';

VAL_DAILY     : 'DAILY';

VAL_WEEKLY    : 'WEEKLY';

VAL_MONTHLY   : 'MONTHLY';

VAL_YEARLY    : 'YEARLY';

INT           : MINUS? '0'..'9'+;

WS            : (  ' '
                 | '\t'
                 | '\r'
                 | '\n'
                ) { $channel=HIDDEN; };

