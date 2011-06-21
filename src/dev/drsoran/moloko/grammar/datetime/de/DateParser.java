// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g 2011-06-21 06:20:37

   package dev.drsoran.moloko.grammar.datetime.de;

   import java.text.ParseException;
   import java.text.SimpleDateFormat;
   import java.util.Calendar;
   
   import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateReturn;
   import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateWithinReturn;
   import dev.drsoran.moloko.grammar.datetime.AbstractDateParser;
   import dev.drsoran.moloko.grammar.LexerException;
   import dev.drsoran.moloko.util.MolokoCalendar;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class DateParser extends AbstractDateParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NOW", "INT", "NUM_STR", "A", "DAYS", "WEEKS", "MONTHS", "YEARS", "OF", "DOT", "MINUS", "COLON", "DATE_SEP", "ON", "COMMA", "MONTH", "NEXT", "WEEKDAY", "IN", "AND", "END", "OF_THE", "TODAY", "NEVER", "TOMORROW", "YESTERDAY", "WS"
    };
    public static final int TODAY=26;
    public static final int A=7;
    public static final int ON=17;
    public static final int NOW=4;
    public static final int INT=5;
    public static final int MINUS=14;
    public static final int AND=23;
    public static final int EOF=-1;
    public static final int YEARS=11;
    public static final int OF=12;
    public static final int NUM_STR=6;
    public static final int MONTH=19;
    public static final int COLON=15;
    public static final int DAYS=8;
    public static final int WS=30;
    public static final int WEEKDAY=21;
    public static final int WEEKS=9;
    public static final int IN=22;
    public static final int OF_THE=25;
    public static final int COMMA=18;
    public static final int MONTHS=10;
    public static final int NEXT=20;
    public static final int NEVER=27;
    public static final int DATE_SEP=16;
    public static final int END=24;
    public static final int DOT=13;
    public static final int YESTERDAY=29;
    public static final int TOMORROW=28;

    // delegates
    // delegators


        public DateParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public DateParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return DateParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g"; }


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



    // $ANTLR start "parseDate"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:137:1: parseDate[MolokoCalendar cal, boolean clearTime] returns [ParseDateReturn result] : ( ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | NOW | EOF );
    public final ParseDateReturn parseDate(MolokoCalendar cal, boolean clearTime) throws RecognitionException {
        ParseDateReturn result = null;

        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:145:4: ( ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | NOW | EOF )
            int alt2=3;
            switch ( input.LA(1) ) {
            case INT:
            case NUM_STR:
            case ON:
            case NEXT:
            case WEEKDAY:
            case IN:
            case END:
            case TODAY:
            case NEVER:
            case TOMORROW:
            case YESTERDAY:
                {
                alt2=1;
                }
                break;
            case NOW:
                {
                alt2=2;
                }
                break;
            case EOF:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:145:6: ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
                    {
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:145:6: ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
                    int alt1=5;
                    switch ( input.LA(1) ) {
                    case INT:
                        {
                        switch ( input.LA(2) ) {
                        case DOT:
                            {
                            int LA1_6 = input.LA(3);

                            if ( (LA1_6==EOF||(LA1_6>=OF && LA1_6<=MINUS)||(LA1_6>=COMMA && LA1_6<=MONTH)) ) {
                                alt1=2;
                            }
                            else if ( (LA1_6==INT) ) {
                                alt1=1;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 1, 6, input);

                                throw nvae;
                            }
                            }
                            break;
                        case DAYS:
                        case WEEKS:
                        case MONTHS:
                        case YEARS:
                            {
                            alt1=3;
                            }
                            break;
                        case MINUS:
                            {
                            int LA1_7 = input.LA(3);

                            if ( (LA1_7==INT) ) {
                                alt1=1;
                            }
                            else if ( (LA1_7==MONTH) ) {
                                alt1=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 1, 7, input);

                                throw nvae;
                            }
                            }
                            break;
                        case COLON:
                        case DATE_SEP:
                            {
                            alt1=1;
                            }
                            break;
                        case EOF:
                        case OF:
                        case COMMA:
                        case MONTH:
                            {
                            alt1=2;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 1, 1, input);

                            throw nvae;
                        }

                        }
                        break;
                    case ON:
                    case NEXT:
                    case WEEKDAY:
                        {
                        alt1=2;
                        }
                        break;
                    case NUM_STR:
                    case IN:
                        {
                        alt1=3;
                        }
                        break;
                    case END:
                        {
                        alt1=4;
                        }
                        break;
                    case TODAY:
                    case NEVER:
                    case TOMORROW:
                    case YESTERDAY:
                        {
                        alt1=5;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 1, 0, input);

                        throw nvae;
                    }

                    switch (alt1) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:145:10: date_full[$cal]
                            {
                            pushFollow(FOLLOW_date_full_in_parseDate84);
                            date_full(cal);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:146:10: date_on[$cal]
                            {
                            pushFollow(FOLLOW_date_on_in_parseDate105);
                            date_on(cal);

                            state._fsp--;


                            }
                            break;
                        case 3 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:147:10: date_in_X_YMWD[$cal]
                            {
                            pushFollow(FOLLOW_date_in_X_YMWD_in_parseDate128);
                            date_in_X_YMWD(cal);

                            state._fsp--;


                            }
                            break;
                        case 4 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:148:10: date_end_of_the_MW[$cal]
                            {
                            pushFollow(FOLLOW_date_end_of_the_MW_in_parseDate144);
                            date_end_of_the_MW(cal);

                            state._fsp--;


                            }
                            break;
                        case 5 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:149:10: date_natural[$cal]
                            {
                            pushFollow(FOLLOW_date_natural_in_parseDate156);
                            date_natural(cal);

                            state._fsp--;


                            }
                            break;

                    }


                          cal.setHasDate( true );
                          
                          if ( clearTime )
                             cal.setHasTime( false );      
                       

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:159:6: NOW
                    {
                    match(input,NOW,FOLLOW_NOW_in_parseDate188); 

                          // In case of NOW we do not respect the clearTime Parameter
                          // cause NOW has always a time.
                          cal.setTimeInMillis( System.currentTimeMillis() );
                          cal.setHasDate( true );
                          cal.setHasTime( true );
                       

                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:167:6: EOF
                    {
                    match(input,EOF,FOLLOW_EOF_in_parseDate200); 

                    }
                    break;

            }

                  result = finishedDateParsing();
               
        }
        catch (RecognitionException e) {

                  throw e;
               
        }
        catch ( LexerException e ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return result;
    }
    // $ANTLR end "parseDate"

    public static class parseDateWithin_return extends ParserRuleReturnScope {
        public MolokoCalendar epochStart;
        public MolokoCalendar epochEnd;
    };

    // $ANTLR start "parseDateWithin"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:178:1: parseDateWithin[boolean past] returns [MolokoCalendar epochStart, MolokoCalendar epochEnd] : (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? ;
    public final DateParser.parseDateWithin_return parseDateWithin(boolean past) throws RecognitionException {
        DateParser.parseDateWithin_return retval = new DateParser.parseDateWithin_return();
        retval.start = input.LT(1);

        Token a=null;
        Token n=null;


              retval.epochStart = getCalendar();
              int amount        = 1;
              int unit          = Calendar.DAY_OF_YEAR;
           
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:191:4: ( (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:191:6: (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )?
            {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:191:6: (a= INT | n= NUM_STR | A )
            int alt3=3;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt3=1;
                }
                break;
            case NUM_STR:
                {
                alt3=2;
                }
                break;
            case A:
                {
                alt3=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:191:9: a= INT
                    {
                    a=(Token)match(input,INT,FOLLOW_INT_in_parseDateWithin270); 

                               amount = Integer.parseInt( (a!=null?a.getText():null) );
                            

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:195:9: n= NUM_STR
                    {
                    n=(Token)match(input,NUM_STR,FOLLOW_NUM_STR_in_parseDateWithin292); 

                               amount = numberStringToNumber( (n!=null?n.getText():null) );
                            

                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:199:9: A
                    {
                    match(input,A,FOLLOW_A_in_parseDateWithin312); 

                    }
                    break;

            }

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:200:7: ( DAYS | WEEKS | MONTHS | YEARS )?
            int alt4=5;
            switch ( input.LA(1) ) {
                case DAYS:
                    {
                    alt4=1;
                    }
                    break;
                case WEEKS:
                    {
                    alt4=2;
                    }
                    break;
                case MONTHS:
                    {
                    alt4=3;
                    }
                    break;
                case YEARS:
                    {
                    alt4=4;
                    }
                    break;
            }

            switch (alt4) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:200:10: DAYS
                    {
                    match(input,DAYS,FOLLOW_DAYS_in_parseDateWithin324); 

                                unit = Calendar.DAY_OF_YEAR;
                             

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:204:10: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_parseDateWithin346); 

                                unit = Calendar.WEEK_OF_YEAR;
                             

                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:208:10: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_parseDateWithin368); 

                                unit = Calendar.MONTH;
                             

                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:212:10: YEARS
                    {
                    match(input,YEARS,FOLLOW_YEARS_in_parseDateWithin390); 

                                unit = Calendar.YEAR;
                             

                    }
                    break;

            }

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:217:7: ( OF parseDate[retval.epochStart, false] )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==OF) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:217:8: OF parseDate[retval.epochStart, false]
                    {
                    match(input,OF,FOLLOW_OF_in_parseDateWithin419); 
                    pushFollow(FOLLOW_parseDate_in_parseDateWithin421);
                    parseDate(retval.epochStart, false);

                    state._fsp--;


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);


                  retval.epochEnd = getCalendar();
                  retval.epochEnd.setTimeInMillis( retval.epochStart.getTimeInMillis() );
                  retval.epochEnd.add( unit, past ? -amount : amount );      
               
        }
        catch (NumberFormatException e) {

                  throw new RecognitionException();
               
        }
        catch (RecognitionException e) {

                  throw e;
               
        }
        catch ( LexerException e ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "parseDateWithin"


    // $ANTLR start "date_full"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:232:1: date_full[MolokoCalendar cal] : pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )? ;
    public final void date_full(MolokoCalendar cal) throws RecognitionException {
        Token pt1=null;
        Token pt2=null;
        Token pt3=null;


              String pt1Str = null;
              String pt2Str = null;
              String pt3Str = null;
           
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:239:4: (pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:239:6: pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )?
            {
            pt1=(Token)match(input,INT,FOLLOW_INT_in_date_full489); 
            if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


                    pt1Str = pt1.getText();
                 
            pt2=(Token)match(input,INT,FOLLOW_INT_in_date_full521); 
            if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


                    pt2Str = pt2.getText();
                 
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:247:6: (pt3= INT )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==INT) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:248:9: pt3= INT
                    {
                    pt3=(Token)match(input,INT,FOLLOW_INT_in_date_full563); 

                               pt3Str = pt3.getText();
                            

                    }
                    break;

            }


                    handleFullDate( cal, pt1Str, pt2Str, pt3Str );
                 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_full"


    // $ANTLR start "date_on"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:258:1: date_on[MolokoCalendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] ) ;
    public final void date_on(MolokoCalendar cal) throws RecognitionException {
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:259:4: ( ( ON )? ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] ) )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:259:6: ( ON )? ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] )
            {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:259:6: ( ON )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==ON) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:259:6: ON
                    {
                    match(input,ON,FOLLOW_ON_in_date_on607); 

                    }
                    break;

            }

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:259:10: ( date_on_Xst_of_M[$cal] | date_on_weekday[$cal] )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==INT) ) {
                alt8=1;
            }
            else if ( ((LA8_0>=NEXT && LA8_0<=WEEKDAY)) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:259:14: date_on_Xst_of_M[$cal]
                    {
                    pushFollow(FOLLOW_date_on_Xst_of_M_in_date_on614);
                    date_on_Xst_of_M(cal);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:260:14: date_on_weekday[$cal]
                    {
                    pushFollow(FOLLOW_date_on_weekday_in_date_on641);
                    date_on_weekday(cal);

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch (RecognitionException e) {

                  throw e;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_on"


    // $ANTLR start "date_on_Xst_of_M"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:267:1: date_on_Xst_of_M[MolokoCalendar cal] : d= INT ( DOT )? ( ( OF | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? ;
    public final void date_on_Xst_of_M(MolokoCalendar cal) throws RecognitionException {
        Token d=null;
        Token m=null;
        Token y=null;


              boolean hasMonth = false;
              boolean hasYear  = false;
           
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:273:4: (d= INT ( DOT )? ( ( OF | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:273:6: d= INT ( DOT )? ( ( OF | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            {
            d=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of_M686); 
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:273:12: ( DOT )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==DOT) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:273:12: DOT
                    {
                    match(input,DOT,FOLLOW_DOT_in_date_on_Xst_of_M688); 

                    }
                    break;

            }


                      cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( (d!=null?d.getText():null) ) );
                   
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:277:6: ( ( OF | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=OF && LA13_0<=MINUS)||(LA13_0>=COMMA && LA13_0<=MONTH)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:277:7: ( OF | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
                    {
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:277:7: ( OF | MINUS | COMMA | DOT )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( ((LA10_0>=OF && LA10_0<=MINUS)||LA10_0==COMMA) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:
                            {
                            if ( (input.LA(1)>=OF && input.LA(1)<=MINUS)||input.LA(1)==COMMA ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }


                            }
                            break;

                    }

                    m=(Token)match(input,MONTH,FOLLOW_MONTH_in_date_on_Xst_of_M732); 

                                parseTextMonth( cal, (m!=null?m.getText():null) );
                                hasMonth = true;
                             
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:283:7: ( MINUS | DOT )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( ((LA11_0>=DOT && LA11_0<=MINUS)) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:
                            {
                            if ( (input.LA(1)>=DOT && input.LA(1)<=MINUS) ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }


                            }
                            break;

                    }

                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:284:7: (y= INT )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==INT) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:284:8: y= INT
                            {
                            y=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of_M769); 

                                        parseYear( cal, (y!=null?y.getText():null) );
                                        hasYear = true;
                                     

                            }
                            break;

                    }


                    }
                    break;

            }


                  handleDateOnXstOfMonth( cal, hasYear, hasMonth );
               

            }

        }
        catch (NumberFormatException e) {

                  throw new RecognitionException();
               
        }
        catch (RecognitionException e) {

                  throw e;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_on_Xst_of_M"


    // $ANTLR start "date_on_weekday"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:302:1: date_on_weekday[MolokoCalendar cal] : ( NEXT )? wd= WEEKDAY ;
    public final void date_on_weekday(MolokoCalendar cal) throws RecognitionException {
        Token wd=null;


              boolean nextWeek = false;
           
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:307:4: ( ( NEXT )? wd= WEEKDAY )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:307:6: ( NEXT )? wd= WEEKDAY
            {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:307:6: ( NEXT )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==NEXT) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:307:7: NEXT
                    {
                    match(input,NEXT,FOLLOW_NEXT_in_date_on_weekday842); 
                     nextWeek = true; 

                    }
                    break;

            }

            wd=(Token)match(input,WEEKDAY,FOLLOW_WEEKDAY_in_date_on_weekday850); 

                  handleDateOnWeekday( cal, (wd!=null?wd.getText():null), nextWeek );
               

            }

        }
        catch (RecognitionException e) {

                  throw e;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_on_weekday"


    // $ANTLR start "date_in_X_YMWD"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:317:1: date_in_X_YMWD[MolokoCalendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* ;
    public final void date_in_X_YMWD(MolokoCalendar cal) throws RecognitionException {
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:318:4: ( ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:318:7: ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:318:7: ( IN )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==IN) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:318:7: IN
                    {
                    match(input,IN,FOLLOW_IN_in_date_in_X_YMWD885); 

                    }
                    break;

            }

            pushFollow(FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD897);
            date_in_X_YMWD_distance(cal);

            state._fsp--;

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:319:7: ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==COMMA||LA16_0==AND) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:319:8: ( AND | COMMA ) date_in_X_YMWD_distance[$cal]
            	    {
            	    if ( input.LA(1)==COMMA||input.LA(1)==AND ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD913);
            	    date_in_X_YMWD_distance(cal);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }

        }
        catch (RecognitionException e) {

                  throw e;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_in_X_YMWD"


    // $ANTLR start "date_in_X_YMWD_distance"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:326:1: date_in_X_YMWD_distance[MolokoCalendar cal] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
    public final void date_in_X_YMWD_distance(MolokoCalendar cal) throws RecognitionException {
        Token a=null;


              int amount   = -1;
              int calField = Calendar.YEAR;
           
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:332:4: ( (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:332:6: (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
            {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:332:6: (a= NUM_STR | a= INT )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==NUM_STR) ) {
                alt17=1;
            }
            else if ( (LA17_0==INT) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:332:10: a= NUM_STR
                    {
                    a=(Token)match(input,NUM_STR,FOLLOW_NUM_STR_in_date_in_X_YMWD_distance962); 
                     amount = numberStringToNumber( (a!=null?a.getText():null) );

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:333:10: a= INT
                    {
                    a=(Token)match(input,INT,FOLLOW_INT_in_date_in_X_YMWD_distance977); 
                     amount = Integer.parseInt( (a!=null?a.getText():null) ); 

                    }
                    break;

            }

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:334:6: ( YEARS | MONTHS | WEEKS | DAYS )
            int alt18=4;
            switch ( input.LA(1) ) {
            case YEARS:
                {
                alt18=1;
                }
                break;
            case MONTHS:
                {
                alt18=2;
                }
                break;
            case WEEKS:
                {
                alt18=3;
                }
                break;
            case DAYS:
                {
                alt18=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:334:12: YEARS
                    {
                    match(input,YEARS,FOLLOW_YEARS_in_date_in_X_YMWD_distance997); 

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:335:12: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_date_in_X_YMWD_distance1010); 
                     calField = Calendar.MONTH;             

                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:336:12: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_date_in_X_YMWD_distance1026); 
                     calField = Calendar.WEEK_OF_YEAR;     

                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:337:12: DAYS
                    {
                    match(input,DAYS,FOLLOW_DAYS_in_date_in_X_YMWD_distance1043); 
                     calField = Calendar.DAY_OF_YEAR;      

                    }
                    break;

            }


                  if ( amount != -1 )
                  {
                     cal.add( calField, amount );
                  }
                  else
                  {
                     throw new RecognitionException();
                  }
               

            }

        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        catch (RecognitionException e) {

                  throw e;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_in_X_YMWD_distance"


    // $ANTLR start "date_end_of_the_MW"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:358:1: date_end_of_the_MW[MolokoCalendar cal] : END ( OF_THE )? ( WEEKS | MONTHS ) ;
    public final void date_end_of_the_MW(MolokoCalendar cal) throws RecognitionException {
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:359:4: ( END ( OF_THE )? ( WEEKS | MONTHS ) )
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:359:6: END ( OF_THE )? ( WEEKS | MONTHS )
            {
            match(input,END,FOLLOW_END_in_date_end_of_the_MW1094); 
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:359:10: ( OF_THE )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==OF_THE) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:359:10: OF_THE
                    {
                    match(input,OF_THE,FOLLOW_OF_THE_in_date_end_of_the_MW1096); 

                    }
                    break;

            }

            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:360:6: ( WEEKS | MONTHS )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==WEEKS) ) {
                alt20=1;
            }
            else if ( (LA20_0==MONTHS) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:360:10: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_date_end_of_the_MW1108); 

                                rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                             

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:364:10: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_date_end_of_the_MW1130); 

                                rollToEndOf( Calendar.DAY_OF_MONTH, cal );
                             

                    }
                    break;

            }


            }

        }
        catch (RecognitionException e) {

                  throw e;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_end_of_the_MW"


    // $ANTLR start "date_natural"
    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:374:1: date_natural[MolokoCalendar cal] : ( TODAY | NEVER | TOMORROW | YESTERDAY );
    public final void date_natural(MolokoCalendar cal) throws RecognitionException {
        try {
            // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:375:4: ( TODAY | NEVER | TOMORROW | YESTERDAY )
            int alt21=4;
            switch ( input.LA(1) ) {
            case TODAY:
                {
                alt21=1;
                }
                break;
            case NEVER:
                {
                alt21=2;
                }
                break;
            case TOMORROW:
                {
                alt21=3;
                }
                break;
            case YESTERDAY:
                {
                alt21=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }

            switch (alt21) {
                case 1 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:375:6: TODAY
                    {
                    match(input,TODAY,FOLLOW_TODAY_in_date_natural1171); 

                         

                    }
                    break;
                case 2 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:378:6: NEVER
                    {
                    match(input,NEVER,FOLLOW_NEVER_in_date_natural1185); 

                            cal.setHasDate( false );
                            cal.setHasTime( false );
                         

                    }
                    break;
                case 3 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:383:6: TOMORROW
                    {
                    match(input,TOMORROW,FOLLOW_TOMORROW_in_date_natural1199); 

                            cal.roll( Calendar.DAY_OF_YEAR, true );
                         

                    }
                    break;
                case 4 :
                    // D:\\Programmierung\\Projects\\java\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\de\\Date.g:387:6: YESTERDAY
                    {
                    match(input,YESTERDAY,FOLLOW_YESTERDAY_in_date_natural1213); 

                            cal.roll( Calendar.DAY_OF_YEAR, false );
                         

                    }
                    break;

            }
        }
        catch (RecognitionException e) {

                  throw e;
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_natural"

    // Delegated rules


 

    public static final BitSet FOLLOW_date_full_in_parseDate84 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_in_parseDate105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_in_X_YMWD_in_parseDate128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_end_of_the_MW_in_parseDate144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_natural_in_parseDate156 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOW_in_parseDate188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EOF_in_parseDate200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_parseDateWithin270 = new BitSet(new long[]{0x0000000000001F02L});
    public static final BitSet FOLLOW_NUM_STR_in_parseDateWithin292 = new BitSet(new long[]{0x0000000000001F02L});
    public static final BitSet FOLLOW_A_in_parseDateWithin312 = new BitSet(new long[]{0x0000000000001F02L});
    public static final BitSet FOLLOW_DAYS_in_parseDateWithin324 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_WEEKS_in_parseDateWithin346 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_MONTHS_in_parseDateWithin368 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_YEARS_in_parseDateWithin390 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_OF_in_parseDateWithin419 = new BitSet(new long[]{0x000000003D720070L});
    public static final BitSet FOLLOW_parseDate_in_parseDateWithin421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_full489 = new BitSet(new long[]{0x000000000001E000L});
    public static final BitSet FOLLOW_set_in_date_full491 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_full521 = new BitSet(new long[]{0x000000000001E000L});
    public static final BitSet FOLLOW_set_in_date_full523 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_date_full563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_date_on607 = new BitSet(new long[]{0x0000000000320020L});
    public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_weekday_in_date_on641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M686 = new BitSet(new long[]{0x00000000000C7002L});
    public static final BitSet FOLLOW_DOT_in_date_on_Xst_of_M688 = new BitSet(new long[]{0x00000000000C7002L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of_M706 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M732 = new BitSet(new long[]{0x0000000000006022L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of_M751 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEXT_in_date_on_weekday842 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IN_in_date_in_X_YMWD885 = new BitSet(new long[]{0x0000000000400060L});
    public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD897 = new BitSet(new long[]{0x0000000000840002L});
    public static final BitSet FOLLOW_set_in_date_in_X_YMWD907 = new BitSet(new long[]{0x0000000000400060L});
    public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD913 = new BitSet(new long[]{0x0000000000840002L});
    public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance962 = new BitSet(new long[]{0x0000000000000F00L});
    public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance977 = new BitSet(new long[]{0x0000000000000F00L});
    public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance1010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance1026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1043 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_in_date_end_of_the_MW1094 = new BitSet(new long[]{0x0000000002000600L});
    public static final BitSet FOLLOW_OF_THE_in_date_end_of_the_MW1096 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TODAY_in_date_natural1171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEVER_in_date_natural1185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOMORROW_in_date_natural1199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_YESTERDAY_in_date_natural1213 = new BitSet(new long[]{0x0000000000000002L});

}