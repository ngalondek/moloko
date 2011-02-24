parser grammar RecurrenceAutoComplParser;

@option
{
   language=Java;
   tokenVocab=RecurrenceLexer;
}

@header
{
   package dev.drsoran.moloko.grammar;
}

@members
{
   public RecurrenceAutoComplParser()
   {
      super( null );
   }


   public enum Sugg
   {
      EVERY, AFTER,
      NUMBERS, XST, WEEKDAYS,
      DAY, DAYS, WEEK, WEEKS, BIWEEKLY, MONTH, MOTHS, YEAR, YEARS,
      UNTIL, FOR
   }
   
   
   public final static Sugg[][] NEXT_VALID_INPUT =
   {
      { Sugg.EVERY, Sugg.AFTER, Sugg.BIWEEKLY },
      { Sugg.NUMBERS, Sugg.XST, Sugg.WEEKDAYS }
   };



   public int suggState = 0;
}

// RULES

parseInput
	@init
	{
		suggState = 0;
	}
   :
   ((EVERY | AFTER)                                                                                                           { suggState = 1; })?
   (     parse_Number? 
         (      DAYS
             | (WEEKS | BIWEEKLY) (ON? THE? recurr_WD)?
             | MONTHS             (ON? THE? recurr_Monthly)?
             | YEARS              (ON? THE? hasWd = recurr_Monthly { hasWd }? => ((IN | OF)? parse_Month)?)?
         )
      |  recurr_Xst
      |  recurr_WD
      |  recurr_Xst recurr_WD
   )
   (    UNTIL
      | FOR INT
   )?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

recurr_Xst
   : x=parse_Xst               
   (((AND | COMMA) parse_Xst)+)?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

recurr_WD
   : parse_Weekday
   (((AND | COMMA) parse_Weekday)+)?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

recurr_Monthly returns [boolean hasWd]
	@init
	{
	   hasWd = false;
	}
   : recurr_Xst
  	 ((LAST)? recurr_WD { hasWd = true; })?
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

parse_Xst
   : INT (DOT|ST_S)?
   | FIRST
   | (SECOND | OTHER)
   | THIRD
   | FOURTH
   | FIFTH
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

parse_Number
   : INT
   | NUM_ONE
   | NUM_TWO
   | NUM_THREE
   | NUM_FOUR
   | NUM_FIVE
   | NUM_SIX
   | NUM_SEVEN
   | NUM_EIGHT
   | NUM_NINE
   | NUM_TEN
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

parse_Weekday
   : MONDAY
   | TUESDAY
   | WEDNESDAY
   | THURSDAY
   | FRIDAY
   | SATURDAY
   | SUNDAY
   | WEEKEND
   | WEEKDAY_LIT
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }

parse_Month
   : MONTH   
   ;
   catch [ RecognitionException e ]
   {
      throw e;
   }
