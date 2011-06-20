// $ANTLR 3.3 Nov 30, 2010 12:45:30 C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g 2011-06-20 16:17:10

   package dev.drsoran.moloko.grammar.datetime;

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "NOW", "INT", "NUM_STR", "A", "DAYS", "WEEKS", "MONTHS", "YEARS", "OF", "DOT", "MINUS", "COLON", "DATE_SEP", "ON", "STs", "MINUS_A", "COMMA", "MONTH", "NEXT", "WEEKDAY", "IN", "AND", "END", "THE", "TODAY", "TONIGHT", "NEVER", "TOMORROW", "YESTERDAY", "WS"
    };
    public static final int EOF=-1;
    public static final int NOW=4;
    public static final int INT=5;
    public static final int NUM_STR=6;
    public static final int A=7;
    public static final int DAYS=8;
    public static final int WEEKS=9;
    public static final int MONTHS=10;
    public static final int YEARS=11;
    public static final int OF=12;
    public static final int DOT=13;
    public static final int MINUS=14;
    public static final int COLON=15;
    public static final int DATE_SEP=16;
    public static final int ON=17;
    public static final int STs=18;
    public static final int MINUS_A=19;
    public static final int COMMA=20;
    public static final int MONTH=21;
    public static final int NEXT=22;
    public static final int WEEKDAY=23;
    public static final int IN=24;
    public static final int AND=25;
    public static final int END=26;
    public static final int THE=27;
    public static final int TODAY=28;
    public static final int TONIGHT=29;
    public static final int NEVER=30;
    public static final int TOMORROW=31;
    public static final int YESTERDAY=32;
    public static final int WS=33;

    // delegates
    // delegators


        public DateParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public DateParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return DateParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g"; }


       public DateParser()
       {
          super( null );
       }
       
       
       protected int numberStringToNumber( String string )
       {
          switch( string.charAt( 0 ) )
          {
             case 'o' : return 1;
             case 't' :
             {
                switch( string.charAt( 1 ) )
                {
                   case 'w' : return 2;
                   case 'h' : return 3;
                }
             }
             case 'f' :
             {
                switch( string.charAt( 1 ) )
                {
                   case 'o' : return 4;
                   case 'i' : return 5;
                }
             }
             case 's' :
             {
                switch( string.charAt( 1 ) )
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
          switch( string.charAt( 0 ) )
          {
             case 'm': return Calendar.MONDAY;
             case 't':
               switch( string.charAt( 1 ) )
                {
                   case 'u' : return Calendar.TUESDAY;
                   case 'h' : return Calendar.THURSDAY;
                }
             case 'w': return Calendar.WEDNESDAY;
             case 's':
                switch( string.charAt( 1 ) )
                {
                   case 'a' : return Calendar.SATURDAY;
                   case 'u' : return Calendar.SUNDAY;
                }
             default : return Calendar.FRIDAY;
          }
       }
       
       protected int monthStringToNumber( String string )
       {
       }



    // $ANTLR start "parseDate"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:107:1: parseDate[MolokoCalendar cal, boolean clearTime] returns [ParseDateReturn result] : ( ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | NOW | EOF );
    public final ParseDateReturn parseDate(MolokoCalendar cal, boolean clearTime) throws RecognitionException {
        ParseDateReturn result = null;

        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:115:4: ( ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) | NOW | EOF )
            int alt2=3;
            switch ( input.LA(1) ) {
            case INT:
            case NUM_STR:
            case ON:
            case MONTH:
            case NEXT:
            case WEEKDAY:
            case IN:
            case END:
            case TODAY:
            case TONIGHT:
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
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:115:6: ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
                    {
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:115:6: ( date_full[$cal] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
                    int alt1=5;
                    switch ( input.LA(1) ) {
                    case INT:
                        {
                        switch ( input.LA(2) ) {
                        case DOT:
                        case MINUS:
                            {
                            int LA1_6 = input.LA(3);

                            if ( (LA1_6==INT) ) {
                                alt1=1;
                            }
                            else if ( (LA1_6==MONTH) ) {
                                alt1=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 1, 6, input);

                                throw nvae;
                            }
                            }
                            break;
                        case EOF:
                        case OF:
                        case STs:
                        case MINUS_A:
                        case COMMA:
                        case MONTH:
                            {
                            alt1=2;
                            }
                            break;
                        case COLON:
                        case DATE_SEP:
                            {
                            alt1=1;
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
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 1, 1, input);

                            throw nvae;
                        }

                        }
                        break;
                    case ON:
                    case MONTH:
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
                    case TONIGHT:
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
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:115:10: date_full[$cal]
                            {
                            pushFollow(FOLLOW_date_full_in_parseDate84);
                            date_full(cal);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:116:10: date_on[$cal]
                            {
                            pushFollow(FOLLOW_date_on_in_parseDate105);
                            date_on(cal);

                            state._fsp--;


                            }
                            break;
                        case 3 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:117:10: date_in_X_YMWD[$cal]
                            {
                            pushFollow(FOLLOW_date_in_X_YMWD_in_parseDate128);
                            date_in_X_YMWD(cal);

                            state._fsp--;


                            }
                            break;
                        case 4 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:118:10: date_end_of_the_MW[$cal]
                            {
                            pushFollow(FOLLOW_date_end_of_the_MW_in_parseDate144);
                            date_end_of_the_MW(cal);

                            state._fsp--;


                            }
                            break;
                        case 5 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:119:10: date_natural[$cal]
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
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:129:6: NOW
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
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:137:6: EOF
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
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:148:1: parseDateWithin[boolean past] returns [MolokoCalendar epochStart, MolokoCalendar epochEnd] : (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? ;
    public final DateParser.parseDateWithin_return parseDateWithin(boolean past) throws RecognitionException {
        DateParser.parseDateWithin_return retval = new DateParser.parseDateWithin_return();
        retval.start = input.LT(1);

        Token a=null;
        Token n=null;


              retval.epochStart = getCalendar();
              int amount        = 1;
              int unit          = Calendar.DAY_OF_YEAR;
           
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:161:4: ( (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )? )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:161:6: (a= INT | n= NUM_STR | A ) ( DAYS | WEEKS | MONTHS | YEARS )? ( OF parseDate[retval.epochStart, false] )?
            {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:161:6: (a= INT | n= NUM_STR | A )
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
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:161:9: a= INT
                    {
                    a=(Token)match(input,INT,FOLLOW_INT_in_parseDateWithin270); 

                               amount = Integer.parseInt( (a!=null?a.getText():null) );
                            

                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:165:9: n= NUM_STR
                    {
                    n=(Token)match(input,NUM_STR,FOLLOW_NUM_STR_in_parseDateWithin292); 

                               amount = numberStringToNumber( (n!=null?n.getText():null) );
                            

                    }
                    break;
                case 3 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:169:9: A
                    {
                    match(input,A,FOLLOW_A_in_parseDateWithin312); 

                    }
                    break;

            }

            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:170:7: ( DAYS | WEEKS | MONTHS | YEARS )?
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
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:170:10: DAYS
                    {
                    match(input,DAYS,FOLLOW_DAYS_in_parseDateWithin324); 

                                unit = Calendar.DAY_OF_YEAR;
                             

                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:174:10: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_parseDateWithin346); 

                                unit = Calendar.WEEK_OF_YEAR;
                             

                    }
                    break;
                case 3 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:178:10: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_parseDateWithin368); 

                                unit = Calendar.MONTH;
                             

                    }
                    break;
                case 4 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:182:10: YEARS
                    {
                    match(input,YEARS,FOLLOW_YEARS_in_parseDateWithin390); 

                                unit = Calendar.YEAR;
                             

                    }
                    break;

            }

            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:187:7: ( OF parseDate[retval.epochStart, false] )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==OF) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:187:8: OF parseDate[retval.epochStart, false]
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
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:202:1: date_full[MolokoCalendar cal] : pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )? ;
    public final void date_full(MolokoCalendar cal) throws RecognitionException {
        Token pt1=null;
        Token pt2=null;
        Token pt3=null;


              String pt1Str = null;
              String pt2Str = null;
              String pt3Str = null;
           
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:209:4: (pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )? )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:209:6: pt1= INT ( DOT | MINUS | COLON | DATE_SEP ) pt2= INT ( DOT | MINUS | COLON | DATE_SEP ) (pt3= INT )?
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
                 
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:217:6: (pt3= INT )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==INT) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:218:9: pt3= INT
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
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:228:1: date_on[MolokoCalendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) ;
    public final void date_on(MolokoCalendar cal) throws RecognitionException {
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:229:4: ( ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:229:6: ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
            {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:229:6: ( ON )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==ON) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:229:6: ON
                    {
                    match(input,ON,FOLLOW_ON_in_date_on607); 

                    }
                    break;

            }

            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:229:10: ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
            int alt8=3;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt8=1;
                }
                break;
            case MONTH:
                {
                alt8=2;
                }
                break;
            case NEXT:
            case WEEKDAY:
                {
                alt8=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:229:14: date_on_Xst_of_M[$cal]
                    {
                    pushFollow(FOLLOW_date_on_Xst_of_M_in_date_on614);
                    date_on_Xst_of_M(cal);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:230:14: date_on_M_Xst[$cal]
                    {
                    pushFollow(FOLLOW_date_on_M_Xst_in_date_on630);
                    date_on_M_Xst(cal);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:231:14: date_on_weekday[$cal]
                    {
                    pushFollow(FOLLOW_date_on_weekday_in_date_on649);
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
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:238:1: date_on_Xst_of_M[MolokoCalendar cal] : d= INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? ;
    public final void date_on_Xst_of_M(MolokoCalendar cal) throws RecognitionException {
        Token d=null;
        Token m=null;
        Token y=null;


              boolean hasMonth = false;
              boolean hasYear  = false;
           
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:244:4: (d= INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:244:6: d= INT ( STs )? ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            {
            d=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of_M694); 
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:244:12: ( STs )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==STs) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:244:12: STs
                    {
                    match(input,STs,FOLLOW_STs_in_date_on_Xst_of_M696); 

                    }
                    break;

            }


                      cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( (d!=null?d.getText():null) ) );
                   
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:248:6: ( ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=OF && LA13_0<=MINUS)||(LA13_0>=MINUS_A && LA13_0<=MONTH)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:248:7: ( OF | MINUS_A | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
                    {
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:248:7: ( OF | MINUS_A | MINUS | COMMA | DOT )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( ((LA10_0>=OF && LA10_0<=MINUS)||(LA10_0>=MINUS_A && LA10_0<=COMMA)) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
                            {
                            if ( (input.LA(1)>=OF && input.LA(1)<=MINUS)||(input.LA(1)>=MINUS_A && input.LA(1)<=COMMA) ) {
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

                    m=(Token)match(input,MONTH,FOLLOW_MONTH_in_date_on_Xst_of_M744); 

                                parseTextMonth( cal, (m!=null?m.getText():null) );
                                hasMonth = true;
                             
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:254:7: ( MINUS | DOT )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( ((LA11_0>=DOT && LA11_0<=MINUS)) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
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

                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:255:7: (y= INT )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==INT) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:255:8: y= INT
                            {
                            y=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of_M781); 

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


    // $ANTLR start "date_on_M_Xst"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:273:1: date_on_M_Xst[MolokoCalendar cal] : m= MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )? ;
    public final void date_on_M_Xst(MolokoCalendar cal) throws RecognitionException {
        Token m=null;
        Token d=null;
        Token y=null;


              boolean hasYear = false;
           
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:278:4: (m= MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )? )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:278:6: m= MONTH ( MINUS | COMMA | DOT )? (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )? (y= INT )?
            {
            m=(Token)match(input,MONTH,FOLLOW_MONTH_in_date_on_M_Xst855); 

                     parseTextMonth( cal, (m!=null?m.getText():null) );
                   
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:282:5: ( MINUS | COMMA | DOT )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=DOT && LA14_0<=MINUS)||LA14_0==COMMA) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
                    {
                    if ( (input.LA(1)>=DOT && input.LA(1)<=MINUS)||input.LA(1)==COMMA ) {
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

            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:283:5: (d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+ )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==INT) ) {
                int LA16_1 = input.LA(2);

                if ( ((LA16_1>=DOT && LA16_1<=MINUS)||(LA16_1>=STs && LA16_1<=COMMA)) ) {
                    alt16=1;
                }
            }
            switch (alt16) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:283:6: d= INT ( STs | MINUS_A | MINUS | COMMA | DOT )+
                    {
                    d=(Token)match(input,INT,FOLLOW_INT_in_date_on_M_Xst890); 

                             cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( (d!=null?d.getText():null) ) );
                           
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:287:8: ( STs | MINUS_A | MINUS | COMMA | DOT )+
                    int cnt15=0;
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0>=DOT && LA15_0<=MINUS)||(LA15_0>=STs && LA15_0<=COMMA)) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:
                    	    {
                    	    if ( (input.LA(1)>=DOT && input.LA(1)<=MINUS)||(input.LA(1)>=STs && input.LA(1)<=COMMA) ) {
                    	        input.consume();
                    	        state.errorRecovery=false;
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt15 >= 1 ) break loop15;
                                EarlyExitException eee =
                                    new EarlyExitException(15, input);
                                throw eee;
                        }
                        cnt15++;
                    } while (true);


                    }
                    break;

            }

            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:288:5: (y= INT )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==INT) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:288:6: y= INT
                    {
                    y=(Token)match(input,INT,FOLLOW_INT_in_date_on_M_Xst938); 

                              parseYear( cal, (y!=null?y.getText():null) );
                              hasYear = true;
                           

                    }
                    break;

            }


                  handleDateOnXstOfMonth( cal, hasYear, true /*hasMonth*/ );
               

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
    // $ANTLR end "date_on_M_Xst"


    // $ANTLR start "date_on_weekday"
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:306:1: date_on_weekday[MolokoCalendar cal] : ( NEXT )? wd= WEEKDAY ;
    public final void date_on_weekday(MolokoCalendar cal) throws RecognitionException {
        Token wd=null;


              boolean nextWeek = false;
           
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:311:4: ( ( NEXT )? wd= WEEKDAY )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:311:6: ( NEXT )? wd= WEEKDAY
            {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:311:6: ( NEXT )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==NEXT) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:311:7: NEXT
                    {
                    match(input,NEXT,FOLLOW_NEXT_in_date_on_weekday1007); 
                     nextWeek = true; 

                    }
                    break;

            }

            wd=(Token)match(input,WEEKDAY,FOLLOW_WEEKDAY_in_date_on_weekday1015); 

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
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:321:1: date_in_X_YMWD[MolokoCalendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* ;
    public final void date_in_X_YMWD(MolokoCalendar cal) throws RecognitionException {
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:322:4: ( ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:322:7: ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:322:7: ( IN )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==IN) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:322:7: IN
                    {
                    match(input,IN,FOLLOW_IN_in_date_in_X_YMWD1050); 

                    }
                    break;

            }

            pushFollow(FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1065);
            date_in_X_YMWD_distance(cal);

            state._fsp--;

            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:323:7: ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==COMMA||LA20_0==AND) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:323:8: ( AND | COMMA ) date_in_X_YMWD_distance[$cal]
            	    {
            	    if ( input.LA(1)==COMMA||input.LA(1)==AND ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1084);
            	    date_in_X_YMWD_distance(cal);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop20;
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
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:330:1: date_in_X_YMWD_distance[MolokoCalendar cal] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
    public final void date_in_X_YMWD_distance(MolokoCalendar cal) throws RecognitionException {
        Token a=null;


              int amount   = -1;
              int calField = Calendar.YEAR;
           
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:336:4: ( (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:336:6: (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
            {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:336:6: (a= NUM_STR | a= INT )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==NUM_STR) ) {
                alt21=1;
            }
            else if ( (LA21_0==INT) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:336:10: a= NUM_STR
                    {
                    a=(Token)match(input,NUM_STR,FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1133); 
                     amount = numberStringToNumber( (a!=null?a.getText():null) );

                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:337:10: a= INT
                    {
                    a=(Token)match(input,INT,FOLLOW_INT_in_date_in_X_YMWD_distance1148); 
                     amount = Integer.parseInt( (a!=null?a.getText():null) ); 

                    }
                    break;

            }

            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:338:6: ( YEARS | MONTHS | WEEKS | DAYS )
            int alt22=4;
            switch ( input.LA(1) ) {
            case YEARS:
                {
                alt22=1;
                }
                break;
            case MONTHS:
                {
                alt22=2;
                }
                break;
            case WEEKS:
                {
                alt22=3;
                }
                break;
            case DAYS:
                {
                alt22=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }

            switch (alt22) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:338:12: YEARS
                    {
                    match(input,YEARS,FOLLOW_YEARS_in_date_in_X_YMWD_distance1168); 

                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:339:12: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_date_in_X_YMWD_distance1181); 
                     calField = Calendar.MONTH;             

                    }
                    break;
                case 3 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:340:12: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_date_in_X_YMWD_distance1197); 
                     calField = Calendar.WEEK_OF_YEAR;     

                    }
                    break;
                case 4 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:341:12: DAYS
                    {
                    match(input,DAYS,FOLLOW_DAYS_in_date_in_X_YMWD_distance1214); 
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
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:362:1: date_end_of_the_MW[MolokoCalendar cal] : END ( OF )? ( THE )? ( WEEKS | MONTHS ) ;
    public final void date_end_of_the_MW(MolokoCalendar cal) throws RecognitionException {
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:363:4: ( END ( OF )? ( THE )? ( WEEKS | MONTHS ) )
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:363:6: END ( OF )? ( THE )? ( WEEKS | MONTHS )
            {
            match(input,END,FOLLOW_END_in_date_end_of_the_MW1265); 
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:363:10: ( OF )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==OF) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:363:10: OF
                    {
                    match(input,OF,FOLLOW_OF_in_date_end_of_the_MW1267); 

                    }
                    break;

            }

            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:363:14: ( THE )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==THE) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:363:14: THE
                    {
                    match(input,THE,FOLLOW_THE_in_date_end_of_the_MW1270); 

                    }
                    break;

            }

            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:364:6: ( WEEKS | MONTHS )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==WEEKS) ) {
                alt25=1;
            }
            else if ( (LA25_0==MONTHS) ) {
                alt25=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:364:10: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_date_end_of_the_MW1282); 

                                rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                             

                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:368:10: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_date_end_of_the_MW1304); 

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
    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:378:1: date_natural[MolokoCalendar cal] : ( ( TODAY | TONIGHT ) | NEVER | TOMORROW | YESTERDAY );
    public final void date_natural(MolokoCalendar cal) throws RecognitionException {
        try {
            // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:379:4: ( ( TODAY | TONIGHT ) | NEVER | TOMORROW | YESTERDAY )
            int alt26=4;
            switch ( input.LA(1) ) {
            case TODAY:
            case TONIGHT:
                {
                alt26=1;
                }
                break;
            case NEVER:
                {
                alt26=2;
                }
                break;
            case TOMORROW:
                {
                alt26=3;
                }
                break;
            case YESTERDAY:
                {
                alt26=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:379:6: ( TODAY | TONIGHT )
                    {
                    if ( (input.LA(1)>=TODAY && input.LA(1)<=TONIGHT) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                         

                    }
                    break;
                case 2 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:382:6: NEVER
                    {
                    match(input,NEVER,FOLLOW_NEVER_in_date_natural1365); 

                            cal.setHasDate( false );
                            cal.setHasTime( false );
                         

                    }
                    break;
                case 3 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:387:6: TOMORROW
                    {
                    match(input,TOMORROW,FOLLOW_TOMORROW_in_date_natural1379); 

                            cal.roll( Calendar.DAY_OF_YEAR, true );
                         

                    }
                    break;
                case 4 :
                    // C:\\Projects\\other\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\datetime\\Date.g:391:6: YESTERDAY
                    {
                    match(input,YESTERDAY,FOLLOW_YESTERDAY_in_date_natural1393); 

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
    public static final BitSet FOLLOW_OF_in_parseDateWithin419 = new BitSet(new long[]{0x00000001F5E20070L});
    public static final BitSet FOLLOW_parseDate_in_parseDateWithin421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_full489 = new BitSet(new long[]{0x000000000001E000L});
    public static final BitSet FOLLOW_set_in_date_full491 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_full521 = new BitSet(new long[]{0x000000000001E000L});
    public static final BitSet FOLLOW_set_in_date_full523 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_date_full563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_date_on607 = new BitSet(new long[]{0x0000000000E20020L});
    public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_M_Xst_in_date_on630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_weekday_in_date_on649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M694 = new BitSet(new long[]{0x00000000003C7002L});
    public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M696 = new BitSet(new long[]{0x0000000000387002L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of_M714 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M744 = new BitSet(new long[]{0x0000000000006022L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of_M763 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst855 = new BitSet(new long[]{0x0000000000106022L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst870 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_date_on_M_Xst890 = new BitSet(new long[]{0x00000000001C6000L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst908 = new BitSet(new long[]{0x00000000001C6022L});
    public static final BitSet FOLLOW_INT_in_date_on_M_Xst938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEXT_in_date_on_weekday1007 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday1015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IN_in_date_in_X_YMWD1050 = new BitSet(new long[]{0x0000000001000060L});
    public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1065 = new BitSet(new long[]{0x0000000002100002L});
    public static final BitSet FOLLOW_set_in_date_in_X_YMWD1075 = new BitSet(new long[]{0x0000000001000060L});
    public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD1084 = new BitSet(new long[]{0x0000000002100002L});
    public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance1133 = new BitSet(new long[]{0x0000000000000F00L});
    public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance1148 = new BitSet(new long[]{0x0000000000000F00L});
    public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance1168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance1181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance1197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_in_date_end_of_the_MW1265 = new BitSet(new long[]{0x0000000008001600L});
    public static final BitSet FOLLOW_OF_in_date_end_of_the_MW1267 = new BitSet(new long[]{0x0000000008000600L});
    public static final BitSet FOLLOW_THE_in_date_end_of_the_MW1270 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_date_natural1345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEVER_in_date_natural1365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOMORROW_in_date_natural1379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_YESTERDAY_in_date_natural1393 = new BitSet(new long[]{0x0000000000000002L});

}