grammar RecurrencePattern;

@option
{
   language=Java;
}

@header
{
   package dev.drsoran.moloko.grammar;

   import java.util.Comparator;
   import java.util.Map;
   import java.util.LinkedList;

   import dev.drsoran.moloko.grammar.lang.RecurrPatternLanguage;
   import dev.drsoran.moloko.util.MolokoDateUtils;
}

@lexer::header
{
   package dev.drsoran.moloko.grammar;
}

@members
{
   public RecurrencePatternParser()
   {
      super( null );
   }


   private final static class CmpOperators implements Comparator< String >
   {
      private final static int operatorToInt( String operator )
      {
         if ( operator.startsWith( OP_FREQ_LIT ) )
            return 1;
         else if ( operator.startsWith( OP_INTERVAL_LIT ) )
            return 2;
         else if ( operator.startsWith( OP_BYDAY_LIT ) )
            return 3;
         else if ( operator.startsWith( OP_BYMONTHDAY_LIT ) )
            return 3;
         else if ( operator.startsWith( OP_BYMONTH_LIT ) )
            return 4;
         else if ( operator.startsWith( OP_UNTIL_LIT ) )
            return 5;
         else if ( operator.startsWith( OP_COUNT_LIT ) )
            return 5;
         else
            return Integer.MAX_VALUE;
      }



      public int compare( String op1, String op2 )
      {
         return operatorToInt( op1 ) - operatorToInt( op2 );
      }
   }


   
   private final static void addElement( Map< Integer, List< Integer > > elements,
                                         int element,
                                         int value )
   {
      List< Integer > values = elements.get( element );
      
      if ( values == null )
         values = new LinkedList< Integer >();

      values.add( value );
      elements.put( element, values );
   }      


   public final static CmpOperators CMP_OPERATORS = new CmpOperators();

   public final static String OP_BYDAY_LIT      = "BYDAY";

   public final static String OP_BYMONTH_LIT    = "BYMONTH";

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

   public final static String OPERATOR_SEP      = ";";

   public final static String BYDAY_MON         = "MO";

   public final static String BYDAY_TUE         = "TU";

   public final static String BYDAY_WED         = "WE";

   public final static String BYDAY_THU         = "TH";

   public final static String BYDAY_FRI         = "FR";

   public final static String BYDAY_SAT         = "SA";

   public final static String BYDAY_SUN         = "SU";

   public final static String DATE_PATTERN      = "yyyyMMdd'T'HHmmss";
}

// RULES

parseRecurrencePattern [RecurrPatternLanguage lang,
                        boolean               every] returns [String sentence]
   @init
   {
      final StringBuilder sb = new StringBuilder();
   }
   @after
   {
      sentence = sb.toString();
   }
   : OP_FREQ
     (
          (
             VAL_YEARLY parse_PatternInterval[lang, sb, "year", every]
             (
                {
                   sb.append( " " ); lang.add( sb, "on_the" ); sb.append( " " );
                }
                (
                   (OP_BYDAY parse_PatternWeekday[lang, sb, true]
                      (COMMA { sb.append( ", " ); } parse_PatternWeekday[lang, sb, false])*)
                   {
                      sb.append( " " ); lang.add( sb, "in" ); sb.append( " " );
                   }
                   OP_BYMONTH parse_PatternMonth[lang, sb]
                )
             )?
          )
        | (
             VAL_MONTHLY parse_PatternInterval[lang, sb, "month", every]
             (
                {
                   sb.append( " " ); lang.add( sb, "on_the" ); sb.append( " " );
                }
                (
                     (
                        OP_BYMONTHDAY parse_PatternXst[lang, sb]
                           (COMMA { sb.append( ", " ); } parse_PatternXst[lang, sb])*
                     )
                   | (
                        OP_BYDAY parse_PatternWeekday[lang, sb, true]
                            (COMMA { sb.append( ", " ); } parse_PatternWeekday[lang, sb, false])*
                     )
                )
             )?
          )
        | (
             VAL_WEEKLY parse_PatternInterval[lang, sb, "week", every]
             (
                {
                   sb.append( " " ); lang.add( sb, "on_the" ); sb.append( " " );
                }
                OP_BYDAY parse_PatternWeekday[lang, sb, true]
                   (COMMA { sb.append( ", " ); } parse_PatternWeekday[lang, sb, false])*
             )?
          )
        | (
             VAL_DAILY parse_PatternInterval[lang, sb, "day", every]
             {
                addElement( elements, OP_FREQ, VAL_DAILY );
             }
          )
     )
     (
          OP_UNTIL date=VAL_DATE
          {
             final String formatedDate = MolokoDateUtils.formatDate( DATE_PATTERN,
                                                                     $date.text,
                                                                       MolokoDateUtils.FORMAT_WITH_YEAR );

             if ( formatedDate != null )
             {
                sb.append( " " ); lang.add( sb, "until" ); sb.append( " " );
                sb.append( formatedDate );
             }
          }
        | OP_COUNT count=INT
          {
             sb.append( " " ); lang.add( sb, "for" ); sb.append( " " );
             sb.append( $count.text );
             sb.append( " " ); lang.add( sb, "times" );
          }
     )?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }
   
parseRecurrencePattern1 [boolean every] returns [Map< Integer, List< Integer > > elements]
   @init
   {
      elements = new HasMap< Integer, List< Integer > >();
   }
   @after
   {
      sentence = sb.toString();
   }
   : OP_FREQ
     (
          (
             VAL_YEARLY parse_PatternInterval[lang, sb, "year", every]
             {
                addElement( elements, OP_FREQ, VAL_YEARLY );
             }
             (
                {
                   sb.append( " " ); lang.add( sb, "on_the" ); sb.append( " " );
                }
                (
                   (OP_BYDAY parse_PatternWeekday[lang, sb, true]
                      (COMMA { sb.append( ", " ); } parse_PatternWeekday[lang, sb, false])*)
                   {
                      sb.append( " " ); lang.add( sb, "in" ); sb.append( " " );
                   }
                   OP_BYMONTH parse_PatternMonth[lang, sb]
                )
             )?
          )
        | (
             VAL_MONTHLY parse_PatternInterval[lang, sb, "month", every]
             {
                addElement( elements, OP_FREQ, VAL_MONTHLY );
             }
             (
                {
                   sb.append( " " ); lang.add( sb, "on_the" ); sb.append( " " );
                }
                (
                     (
                        OP_BYMONTHDAY parse_PatternXst[lang, sb]
                           (COMMA { sb.append( ", " ); } parse_PatternXst[lang, sb])*
                     )
                   | (
                        OP_BYDAY parse_PatternWeekday[lang, sb, true]
                            (COMMA { sb.append( ", " ); } parse_PatternWeekday[lang, sb, false])*
                     )
                )
             )?
          )
        | (
             VAL_WEEKLY parse_PatternInterval[lang, sb, "week", every]
             {
                addElement( elements, OP_FREQ, VAL_WEEKLY );
             }
             (
                {
                   sb.append( " " ); lang.add( sb, "on_the" ); sb.append( " " );
                }
                OP_BYDAY parse_PatternWeekday[lang, sb, true]
                   (COMMA { sb.append( ", " ); } parse_PatternWeekday[lang, sb, false])*
             )?
          )
        | (
             VAL_DAILY parse_PatternInterval[lang, sb, "day", every]
             {
                addElement( elements, OP_FREQ, VAL_DAILY );
             }
          )
     )
     (
          OP_UNTIL date=VAL_DATE
          {
             final String formatedDate = MolokoDateUtils.formatDate( DATE_PATTERN,
                                                                     $date.text,
                                                                       MolokoDateUtils.FORMAT_WITH_YEAR );

             if ( formatedDate != null )
             {
                sb.append( " " ); lang.add( sb, "until" ); sb.append( " " );
                sb.append( formatedDate );
             }
          }
        | OP_COUNT count=INT
          {
             sb.append( " " ); lang.add( sb, "for" ); sb.append( " " );
             sb.append( $count.text );
             sb.append( " " ); lang.add( sb, "times" );
          }
     )?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

parse_PatternInterval [RecurrPatternLanguage lang,
                       StringBuilder         sb,
                       String                unit,
                       boolean               isEvery]
   : OP_INTERVAL interval=INT
     {
        if ( isEvery )
           lang.addEvery( sb, unit, $interval.text );
        else
           lang.addAfter( sb, unit, $interval.text );
     }
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

parse_PatternXst [RecurrPatternLanguage lang, StringBuilder sb]
   : x=INT
     {
 	     if ( sb != null )
	     {
           final int xSt = Integer.parseInt( $x.text );
	
           if ( xSt < 0 )
           {
	           if ( xSt < -1 )
	           {
	              lang.addStToX( sb, xSt * -1 );
	              sb.append( " " );
              }

              lang.add( sb, "last" );
           }
           else
              lang.addStToX( sb, xSt );
        }
     }
   ;
   catch [ NumberFormatException e ]
   {
      throw new RecognitionException();
   }
   catch [ RecognitionException e ]
   {
      throw e;
   }

parse_PatternWeekday [RecurrPatternLanguage lang,
                      StringBuilder         sb,
                      boolean               respectXst]
   : (
        (
           parse_PatternXst[lang, respectXst ? sb : null]
           {
              if ( respectXst )
                 sb.append( " " );
           }
        )?
        (
           wd=(  MONDAY
               | TUESDAY
               | WEDNESDAY
               | THURSDAY
               | FRIDAY
               | SATURDAY
               | SUNDAY )
           {
              lang.add( sb, $wd.text );
           }
        )
     )
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

parse_PatternMonth [RecurrPatternLanguage lang, StringBuilder sb]
   : m=INT
     {
        lang.add( sb, "m" + $m.text );
     }
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }


// TOKENS

MONDAY        : 'MO';

TUESDAY       : 'TU';

WEDNESDAY     : 'WE';

THURSDAY      : 'TH';

FRIDAY        : 'FR';

SATURDAY      : 'SA';

SUNDAY        : 'SU';

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

// yyyyMMdd'T'HHmmss
VAL_DATE      : NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER
                'T'
                NUMBER NUMBER NUMBER NUMBER NUMBER NUMBER;
fragment
NUMBER        : '0'..'9';

INT           : MINUS? NUMBER+;

WS            : (  ' '
                 | '\t'
                 | '\r'
                 | '\n'
                ) { $channel=HIDDEN; };

