// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g 2010-08-03 13:33:29

   package dev.drsoran.moloko.grammar;

   import java.text.ParseException;
   import java.text.SimpleDateFormat;
   import java.util.Calendar;
   import java.util.Date;
   import java.util.Locale;
   import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class TimeSpecParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INT", "DOT", "MINUS", "DATE_SEP", "ON", "STs", "OF", "COMMA", "MONTH", "NEXT", "WEEKDAY", "IN", "AND", "NUM_STR", "YEARS", "MONTHS", "WEEKS", "DAYS", "END", "THE", "TODAY", "TONIGHT", "TOMORROW", "YESTERDAY", "AT", "HOURS", "COLON", "MINUTES", "SECONDS", "NEVER", "MIDNIGHT", "MIDDAY", "NOON", "NOW", "WS", "'-a'", "'a'", "'m'", "'p'"
    };
    public static final int T__42=42;
    public static final int STs=9;
    public static final int MIDDAY=35;
    public static final int T__40=40;
    public static final int TODAY=24;
    public static final int T__41=41;
    public static final int THE=23;
    public static final int ON=8;
    public static final int NOW=37;
    public static final int SECONDS=32;
    public static final int INT=4;
    public static final int MIDNIGHT=34;
    public static final int MINUS=6;
    public static final int AND=16;
    public static final int YEARS=18;
    public static final int EOF=-1;
    public static final int OF=10;
    public static final int NUM_STR=17;
    public static final int MONTH=12;
    public static final int MINUTES=31;
    public static final int COLON=30;
    public static final int AT=28;
    public static final int DAYS=21;
    public static final int WS=38;
    public static final int WEEKS=20;
    public static final int WEEKDAY=14;
    public static final int IN=15;
    public static final int TONIGHT=25;
    public static final int COMMA=11;
    public static final int T__39=39;
    public static final int MONTHS=19;
    public static final int NOON=36;
    public static final int NEXT=13;
    public static final int NEVER=33;
    public static final int DATE_SEP=7;
    public static final int END=22;
    public static final int DOT=5;
    public static final int YESTERDAY=27;
    public static final int HOURS=29;
    public static final int TOMORROW=26;

    // delegates
    // delegators


        public TimeSpecParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public TimeSpecParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return TimeSpecParser.tokenNames; }
    public String getGrammarFileName() { return "D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g"; }


       private final static Locale LOCALE = Locale.ENGLISH;

       private final static HashMap< String, Integer > numberLookUp = new HashMap< String, Integer >();

       static
       {
          numberLookUp.put( "one",   1 );
          numberLookUp.put( "two",   2 );
          numberLookUp.put( "three", 3 );
          numberLookUp.put( "four",  4 );
          numberLookUp.put( "five",  5 );
          numberLookUp.put( "six",   6 );
          numberLookUp.put( "seven", 7 );
          numberLookUp.put( "eight", 8 );
          numberLookUp.put( "nine",  9 );
          numberLookUp.put( "ten",   10 );
       }



       public final static Calendar getLocalizedCalendar()
       {
          return Calendar.getInstance( LOCALE );
       }



       private final static int strToNumber( String string )
       {
          int res = -1;

          final Integer val = numberLookUp.get( string );

          if ( val != null )
             res = val;

          return res;
       }



       private final static void parseFullDate( Calendar cal,
                                                String   day,
                                                String   month,
                                                String   year,
                                                boolean  textMonth ) throws RecognitionException
       {
          try
          {
             SimpleDateFormat sdf = null;

             if ( !textMonth )
             {
                sdf = new SimpleDateFormat( "dd.MM.yyyy" );
             }
             else
             {
                sdf = new SimpleDateFormat( "dd.MMM.yyyy",
                                            LOCALE /* Locale for MMM*/ );
             }

             sdf.setCalendar( cal );
             sdf.parse( day + "." + month + "." + year );
          }
          catch( ParseException e )
          {
             throw new RecognitionException();
          }
       }



       private final static void parseTextMonth( Calendar cal,
                                                 String   month ) throws RecognitionException
       {
          try
          {
             final SimpleDateFormat sdf = new SimpleDateFormat( "MMM", LOCALE );
             sdf.parse( month );

             cal.set( Calendar.MONTH, sdf.getCalendar().get( Calendar.MONTH ) );
          }
          catch( ParseException e )
          {
             throw new RecognitionException();
          }
       }



       private final static void parseYear( Calendar cal, String yearStr ) throws RecognitionException
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



       private final static void setCalendarTime( Calendar cal,
                                                  String   pit ) throws RecognitionException
       {
          final int len = pit.length();

          SimpleDateFormat sdf = null;

          try
          {
             if ( len < 3 )
             {
                sdf = new SimpleDateFormat( "HH" );
             }
             else if ( len > 3 )
             {
                sdf = new SimpleDateFormat( "HHmm" );
             }
             else
             {
                sdf = new SimpleDateFormat( "Hmm" );
             }

             sdf.parse( pit );

             final Calendar sdfCal = sdf.getCalendar();
             cal.set( Calendar.HOUR_OF_DAY, sdfCal.get( Calendar.HOUR_OF_DAY ) );
             cal.set( Calendar.MINUTE,      sdfCal.get( Calendar.MINUTE ) );
             cal.set( Calendar.SECOND,      0 );
          }
          catch( ParseException e )
          {
             throw new RecognitionException();
          }
       }



       private final static void rollToEndOf( int field, Calendar cal )
       {
          final int ref = cal.get( field );
          final int max = cal.getMaximum( field );

          // set the field to the end.
          cal.set( field, max );

          // if already at the end
          if ( ref == max )
          {
             // we roll to the next
             cal.roll( field, true );
          }
       }



    // $ANTLR start "parseDateTime"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:222:1: parseDateTime[Calendar cal, boolean dayFirst] : date_spec[$cal, $dayFirst] ( time_spec[ $cal ] )? ;
    public final void parseDateTime(Calendar cal, boolean dayFirst) throws RecognitionException {

              boolean hasTime = false;
           
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:230:4: ( date_spec[$cal, $dayFirst] ( time_spec[ $cal ] )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:230:6: date_spec[$cal, $dayFirst] ( time_spec[ $cal ] )?
            {
            pushFollow(FOLLOW_date_spec_in_parseDateTime71);
            date_spec(cal, dayFirst);

            state._fsp--;

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:230:33: ( time_spec[ $cal ] )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==INT||LA1_0==AT||(LA1_0>=NEVER && LA1_0<=NOON)) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:230:34: time_spec[ $cal ]
                    {
                    pushFollow(FOLLOW_time_spec_in_parseDateTime75);
                    time_spec(cal);

                    state._fsp--;

                     hasTime = true; 

                    }
                    break;

            }


                  if ( !hasTime )
                  {
                     cal.clear( Calendar.HOUR );
                     cal.clear( Calendar.HOUR_OF_DAY );
                     cal.clear( Calendar.MINUTE );
                     cal.clear( Calendar.SECOND );
                  }
               

            }

        }
        catch (NoViableAltException nve) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "parseDateTime"


    // $ANTLR start "date_spec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:246:1: date_spec[Calendar cal, boolean dayFirst] : ( date_full[$cal, $dayFirst] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) ;
    public final void date_spec(Calendar cal, boolean dayFirst) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:247:4: ( ( date_full[$cal, $dayFirst] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:247:6: ( date_full[$cal, $dayFirst] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:247:6: ( date_full[$cal, $dayFirst] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
            int alt2=5;
            switch ( input.LA(1) ) {
            case INT:
                {
                switch ( input.LA(2) ) {
                case DOT:
                case MINUS:
                    {
                    int LA2_6 = input.LA(3);

                    if ( (LA2_6==INT) ) {
                        alt2=1;
                    }
                    else if ( (LA2_6==MONTH) ) {
                        alt2=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                case YEARS:
                case MONTHS:
                case WEEKS:
                case DAYS:
                    {
                    alt2=3;
                    }
                    break;
                case EOF:
                case INT:
                case STs:
                case OF:
                case COMMA:
                case MONTH:
                case AT:
                case NEVER:
                case MIDNIGHT:
                case MIDDAY:
                case NOON:
                case 39:
                    {
                    alt2=2;
                    }
                    break;
                case DATE_SEP:
                    {
                    alt2=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }

                }
                break;
            case ON:
            case MONTH:
            case NEXT:
            case WEEKDAY:
                {
                alt2=2;
                }
                break;
            case IN:
            case NUM_STR:
                {
                alt2=3;
                }
                break;
            case END:
                {
                alt2=4;
                }
                break;
            case TODAY:
            case TONIGHT:
            case TOMORROW:
            case YESTERDAY:
                {
                alt2=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:247:10: date_full[$cal, $dayFirst]
                    {
                    pushFollow(FOLLOW_date_full_in_date_spec118);
                    date_full(cal, dayFirst);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:248:10: date_on[$cal]
                    {
                    pushFollow(FOLLOW_date_on_in_date_spec139);
                    date_on(cal);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:249:10: date_in_X_YMWD[$cal]
                    {
                    pushFollow(FOLLOW_date_in_X_YMWD_in_date_spec162);
                    date_in_X_YMWD(cal);

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:250:10: date_end_of_the_MW[$cal]
                    {
                    pushFollow(FOLLOW_date_end_of_the_MW_in_date_spec178);
                    date_end_of_the_MW(cal);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:251:10: date_natural[$cal]
                    {
                    pushFollow(FOLLOW_date_natural_in_date_spec190);
                    date_natural(cal);

                    state._fsp--;


                    }
                    break;

            }


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
    // $ANTLR end "date_spec"


    // $ANTLR start "date_full"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:254:1: date_full[Calendar cal, boolean dayFirst] : ({...}?pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( DOT | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( DOT | MINUS | DATE_SEP ) pt3= INT ) ;
    public final void date_full(Calendar cal, boolean dayFirst) throws RecognitionException {
        Token pt1=null;
        Token pt2=null;
        Token pt3=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:255:4: ( ({...}?pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( DOT | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( DOT | MINUS | DATE_SEP ) pt3= INT ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:255:6: ({...}?pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( DOT | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( DOT | MINUS | DATE_SEP ) pt3= INT )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:255:6: ({...}?pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( DOT | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( DOT | MINUS | DATE_SEP ) pt3= INT )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==INT) ) {
                int LA3_1 = input.LA(2);

                if ( ((LA3_1>=DOT && LA3_1<=DATE_SEP)) ) {
                    int LA3_2 = input.LA(3);

                    if ( (LA3_2==INT) ) {
                        int LA3_3 = input.LA(4);

                        if ( ((LA3_3>=DOT && LA3_3<=DATE_SEP)) ) {
                            int LA3_4 = input.LA(5);

                            if ( (LA3_4==INT) ) {
                                int LA3_5 = input.LA(6);

                                if ( ((dayFirst)) ) {
                                    alt3=1;
                                }
                                else if ( (true) ) {
                                    alt3=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 3, 5, input);

                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 3, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:255:8: {...}?pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( DOT | MINUS | DATE_SEP ) pt3= INT
                    {
                    if ( !((dayFirst)) ) {
                        throw new FailedPredicateException(input, "date_full", "$dayFirst");
                    }
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_date_full221); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt2=(Token)match(input,INT,FOLLOW_INT_in_date_full259); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt3=(Token)match(input,INT,FOLLOW_INT_in_date_full297); 

                                          // year first
                                          if ( pt1.getText().length() > 2 )
                                          {
                                             parseFullDate( cal,
                                                            pt2.getText(),
                                                            pt3.getText(),
                                                            pt1.getText(),
                                                            false );
                                          }

                                          // year last
                                          else
                                          {
                                             parseFullDate( cal,
                                                            pt1.getText(),
                                                            pt2.getText(),
                                                            pt3.getText(),
                                                            false );
                                           }
                                        

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:280:21: pt1= INT ( DOT | MINUS | DATE_SEP ) pt2= INT ( DOT | MINUS | DATE_SEP ) pt3= INT
                    {
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_date_full344); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt2=(Token)match(input,INT,FOLLOW_INT_in_date_full382); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt3=(Token)match(input,INT,FOLLOW_INT_in_date_full420); 

                                          // year first
                                          if ( pt1.getText().length() > 2 )
                                          {
                                             parseFullDate( cal,
                                                            pt3.getText(),
                                                            pt2.getText(),
                                                            pt1.getText(),
                                                            false );
                                          }

                                          // year last
                                          else
                                          {
                                             parseFullDate( cal,
                                                            pt2.getText(),
                                                            pt1.getText(),
                                                            pt3.getText(),
                                                            false );
                                           }
                                        

                    }
                    break;

            }


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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:307:1: date_on[Calendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) ;
    public final void date_on(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:4: ( ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:6: ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:6: ( ON )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==ON) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:6: ON
                    {
                    match(input,ON,FOLLOW_ON_in_date_on470); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:10: ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
            int alt5=3;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt5=1;
                }
                break;
            case MONTH:
                {
                alt5=2;
                }
                break;
            case NEXT:
            case WEEKDAY:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:14: date_on_Xst_of_M[$cal]
                    {
                    pushFollow(FOLLOW_date_on_Xst_of_M_in_date_on477);
                    date_on_Xst_of_M(cal);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:309:14: date_on_M_Xst[$cal]
                    {
                    pushFollow(FOLLOW_date_on_M_Xst_in_date_on493);
                    date_on_M_Xst(cal);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:310:14: date_on_weekday[$cal]
                    {
                    pushFollow(FOLLOW_date_on_weekday_in_date_on512);
                    date_on_weekday(cal);

                    state._fsp--;


                    }
                    break;

            }


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
    // $ANTLR end "date_on"


    // $ANTLR start "date_on_Xst_of_M"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:313:1: date_on_Xst_of_M[Calendar cal] : d= INT ( STs )? ( ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? ;
    public final void date_on_Xst_of_M(Calendar cal) throws RecognitionException {
        Token d=null;
        Token m=null;
        Token y=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:314:4: (d= INT ( STs )? ( ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:314:6: d= INT ( STs )? ( ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            {
            d=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of_M534); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:314:12: ( STs )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==STs) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:314:12: STs
                    {
                    match(input,STs,FOLLOW_STs_in_date_on_Xst_of_M536); 

                    }
                    break;

            }


                      cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( (d!=null?d.getText():null) ) );
                   
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:6: ( ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( ((LA10_0>=DOT && LA10_0<=MINUS)||(LA10_0>=OF && LA10_0<=MONTH)||LA10_0==39) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:7: ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:7: ( OF | '-a' | MINUS | COMMA | DOT )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( ((LA7_0>=DOT && LA7_0<=MINUS)||(LA7_0>=OF && LA7_0<=COMMA)||LA7_0==39) ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
                            {
                            if ( (input.LA(1)>=DOT && input.LA(1)<=MINUS)||(input.LA(1)>=OF && input.LA(1)<=COMMA)||input.LA(1)==39 ) {
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

                    m=(Token)match(input,MONTH,FOLLOW_MONTH_in_date_on_Xst_of_M584); 

                                parseTextMonth( cal, (m!=null?m.getText():null) );
                             
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:323:7: ( MINUS | DOT )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( ((LA8_0>=DOT && LA8_0<=MINUS)) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
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

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:324:7: (y= INT )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==INT) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:324:8: y= INT
                            {
                            y=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of_M621); 

                                        parseYear( cal, (y!=null?y.getText():null) );
                                     

                            }
                            break;

                    }


                    }
                    break;

            }


            }

        }
        catch (NumberFormatException e) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_on_Xst_of_M"


    // $ANTLR start "date_on_M_Xst"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:334:1: date_on_M_Xst[Calendar cal] : m= MONTH ( MINUS | COMMA | DOT )? d= INT ( STs )? ( MINUS | DOT )? ( '-a' | MINUS | COMMA | DOT )? (y= INT )? ;
    public final void date_on_M_Xst(Calendar cal) throws RecognitionException {
        Token m=null;
        Token d=null;
        Token y=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:335:4: (m= MONTH ( MINUS | COMMA | DOT )? d= INT ( STs )? ( MINUS | DOT )? ( '-a' | MINUS | COMMA | DOT )? (y= INT )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:335:6: m= MONTH ( MINUS | COMMA | DOT )? d= INT ( STs )? ( MINUS | DOT )? ( '-a' | MINUS | COMMA | DOT )? (y= INT )?
            {
            m=(Token)match(input,MONTH,FOLLOW_MONTH_in_date_on_M_Xst667); 

                     parseTextMonth( cal, (m!=null?m.getText():null) );
                   
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:339:5: ( MINUS | COMMA | DOT )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( ((LA11_0>=DOT && LA11_0<=MINUS)||LA11_0==COMMA) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
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

            d=(Token)match(input,INT,FOLLOW_INT_in_date_on_M_Xst702); 

                     cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( (d!=null?d.getText():null) ) );
                   
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:344:5: ( STs )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==STs) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:344:5: STs
                    {
                    match(input,STs,FOLLOW_STs_in_date_on_M_Xst717); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:344:10: ( MINUS | DOT )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( ((LA13_0>=DOT && LA13_0<=MINUS)) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:344:25: ( '-a' | MINUS | COMMA | DOT )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=DOT && LA14_0<=MINUS)||LA14_0==COMMA||LA14_0==39) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
                    {
                    if ( (input.LA(1)>=DOT && input.LA(1)<=MINUS)||input.LA(1)==COMMA||input.LA(1)==39 ) {
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:345:5: (y= INT )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==INT) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:345:6: y= INT
                    {
                    y=(Token)match(input,INT,FOLLOW_INT_in_date_on_M_Xst753); 

                              parseYear( cal, (y!=null?y.getText():null) );
                           

                    }
                    break;

            }


            }

        }
        catch (NumberFormatException e) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_on_M_Xst"


    // $ANTLR start "date_on_weekday"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:355:1: date_on_weekday[Calendar cal] : ( NEXT )? wd= WEEKDAY ;
    public final void date_on_weekday(Calendar cal) throws RecognitionException {
        Token wd=null;


              boolean nextWeek = false;
           
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:360:4: ( ( NEXT )? wd= WEEKDAY )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:360:6: ( NEXT )? wd= WEEKDAY
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:360:6: ( NEXT )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==NEXT) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:360:7: NEXT
                    {
                    match(input,NEXT,FOLLOW_NEXT_in_date_on_weekday805); 
                     nextWeek = true; 

                    }
                    break;

            }

            wd=(Token)match(input,WEEKDAY,FOLLOW_WEEKDAY_in_date_on_weekday813); 

                  final SimpleDateFormat sdf = new SimpleDateFormat( "EE", LOCALE );
                  sdf.parse( (wd!=null?wd.getText():null) );

                  final int parsedWeekDay  = sdf.getCalendar().get( Calendar.DAY_OF_WEEK );
                  final int currentWeekDay = cal.get( Calendar.DAY_OF_WEEK );

                  cal.set( Calendar.DAY_OF_WEEK, parsedWeekDay );

                  // If the weekday is before today or today, we adjust to next week.
                  if ( parsedWeekDay <= currentWeekDay )
                     cal.roll( Calendar.WEEK_OF_YEAR, true );

                  // if the next week is explicitly enforced
                  if ( nextWeek )
                     cal.roll( Calendar.WEEK_OF_YEAR, true );
               

            }

        }
        catch ( ParseException pe ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "date_on_weekday"


    // $ANTLR start "date_in_X_YMWD"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:384:1: date_in_X_YMWD[Calendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* ;
    public final void date_in_X_YMWD(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:385:4: ( ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:385:7: ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:385:7: ( IN )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==IN) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:385:7: IN
                    {
                    match(input,IN,FOLLOW_IN_in_date_in_X_YMWD847); 

                    }
                    break;

            }

            pushFollow(FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD862);
            date_in_X_YMWD_distance(cal);

            state._fsp--;

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:386:7: ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==COMMA||LA18_0==AND) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:386:8: ( AND | COMMA ) date_in_X_YMWD_distance[$cal]
            	    {
            	    if ( input.LA(1)==COMMA||input.LA(1)==AND ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD881);
            	    date_in_X_YMWD_distance(cal);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


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
    // $ANTLR end "date_in_X_YMWD"


    // $ANTLR start "date_in_X_YMWD_distance"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:389:1: date_in_X_YMWD_distance[Calendar cal] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
    public final void date_in_X_YMWD_distance(Calendar cal) throws RecognitionException {
        Token a=null;


              int amount   = -1;
              int calField = Calendar.YEAR;
           
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:395:4: ( (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:395:6: (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:395:6: (a= NUM_STR | a= INT )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==NUM_STR) ) {
                alt19=1;
            }
            else if ( (LA19_0==INT) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:395:10: a= NUM_STR
                    {
                    a=(Token)match(input,NUM_STR,FOLLOW_NUM_STR_in_date_in_X_YMWD_distance918); 
                     amount = strToNumber( (a!=null?a.getText():null) );      

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:396:10: a= INT
                    {
                    a=(Token)match(input,INT,FOLLOW_INT_in_date_in_X_YMWD_distance933); 
                     amount = Integer.parseInt( (a!=null?a.getText():null) ); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:397:6: ( YEARS | MONTHS | WEEKS | DAYS )
            int alt20=4;
            switch ( input.LA(1) ) {
            case YEARS:
                {
                alt20=1;
                }
                break;
            case MONTHS:
                {
                alt20=2;
                }
                break;
            case WEEKS:
                {
                alt20=3;
                }
                break;
            case DAYS:
                {
                alt20=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:397:12: YEARS
                    {
                    match(input,YEARS,FOLLOW_YEARS_in_date_in_X_YMWD_distance953); 

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:398:12: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_date_in_X_YMWD_distance966); 
                     calField = Calendar.MONTH;            

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:399:12: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_date_in_X_YMWD_distance982); 
                     calField = Calendar.WEEK_OF_YEAR;     

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:400:12: DAYS
                    {
                    match(input,DAYS,FOLLOW_DAYS_in_date_in_X_YMWD_distance999); 
                     calField = Calendar.DAY_OF_YEAR;      

                    }
                    break;

            }


                  if ( amount != -1 )
                  {
                     cal.roll( calField, amount );
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
        finally {
        }
        return ;
    }
    // $ANTLR end "date_in_X_YMWD_distance"


    // $ANTLR start "date_end_of_the_MW"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:417:1: date_end_of_the_MW[Calendar cal] : END ( OF )? ( THE )? ( WEEKS | MONTHS ) ;
    public final void date_end_of_the_MW(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:418:4: ( END ( OF )? ( THE )? ( WEEKS | MONTHS ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:418:6: END ( OF )? ( THE )? ( WEEKS | MONTHS )
            {
            match(input,END,FOLLOW_END_in_date_end_of_the_MW1038); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:418:10: ( OF )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==OF) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:418:10: OF
                    {
                    match(input,OF,FOLLOW_OF_in_date_end_of_the_MW1040); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:418:14: ( THE )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==THE) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:418:14: THE
                    {
                    match(input,THE,FOLLOW_THE_in_date_end_of_the_MW1043); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:419:6: ( WEEKS | MONTHS )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==WEEKS) ) {
                alt23=1;
            }
            else if ( (LA23_0==MONTHS) ) {
                alt23=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:419:10: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_date_end_of_the_MW1055); 

                                rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                             

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:423:10: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_date_end_of_the_MW1077); 

                                rollToEndOf( Calendar.DAY_OF_MONTH, cal );
                             

                    }
                    break;

            }


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
    // $ANTLR end "date_end_of_the_MW"


    // $ANTLR start "date_natural"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:429:1: date_natural[Calendar cal] : ( ( TODAY | TONIGHT ) | TOMORROW | YESTERDAY );
    public final void date_natural(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:430:4: ( ( TODAY | TONIGHT ) | TOMORROW | YESTERDAY )
            int alt24=3;
            switch ( input.LA(1) ) {
            case TODAY:
            case TONIGHT:
                {
                alt24=1;
                }
                break;
            case TOMORROW:
                {
                alt24=2;
                }
                break;
            case YESTERDAY:
                {
                alt24=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:430:6: ( TODAY | TONIGHT )
                    {
                    if ( (input.LA(1)>=TODAY && input.LA(1)<=TONIGHT) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                            cal.setTimeInMillis( System.currentTimeMillis() );
                         

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:434:6: TOMORROW
                    {
                    match(input,TOMORROW,FOLLOW_TOMORROW_in_date_natural1126); 

                            cal.roll( Calendar.DAY_OF_YEAR, true );
                         

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:438:6: YESTERDAY
                    {
                    match(input,YESTERDAY,FOLLOW_YESTERDAY_in_date_natural1140); 

                            cal.roll( Calendar.DAY_OF_YEAR, false );
                         

                    }
                    break;

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
    // $ANTLR end "date_natural"


    // $ANTLR start "time_spec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:444:1: time_spec[Calendar cal] : (h= INT ( time_floatspec[$cal] | time_separatorspec[$cal] | time_naturalspec[$cal] ) | ( AT )? time_point_in_timespec[$cal] ) ;
    public final void time_spec(Calendar cal) throws RecognitionException {
        Token h=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:445:4: ( (h= INT ( time_floatspec[$cal] | time_separatorspec[$cal] | time_naturalspec[$cal] ) | ( AT )? time_point_in_timespec[$cal] ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:445:6: (h= INT ( time_floatspec[$cal] | time_separatorspec[$cal] | time_naturalspec[$cal] ) | ( AT )? time_point_in_timespec[$cal] )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:445:6: (h= INT ( time_floatspec[$cal] | time_separatorspec[$cal] | time_naturalspec[$cal] ) | ( AT )? time_point_in_timespec[$cal] )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==INT) ) {
                switch ( input.LA(2) ) {
                case INT:
                case DOT:
                    {
                    alt27=1;
                    }
                    break;
                case COLON:
                    {
                    int LA27_4 = input.LA(3);

                    if ( (LA27_4==INT) ) {
                        alt27=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 4, input);

                        throw nvae;
                    }
                    }
                    break;
                case EOF:
                case 40:
                case 42:
                    {
                    alt27=2;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 27, 1, input);

                    throw nvae;
                }

            }
            else if ( (LA27_0==AT||(LA27_0>=NEVER && LA27_0<=NOON)) ) {
                alt27=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:445:9: h= INT ( time_floatspec[$cal] | time_separatorspec[$cal] | time_naturalspec[$cal] )
                    {
                    h=(Token)match(input,INT,FOLLOW_INT_in_time_spec1169); 

                                 cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( (h!=null?h.getText():null) ) );
                              
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:449:9: ( time_floatspec[$cal] | time_separatorspec[$cal] | time_naturalspec[$cal] )
                    int alt25=3;
                    switch ( input.LA(1) ) {
                    case DOT:
                        {
                        alt25=1;
                        }
                        break;
                    case COLON:
                        {
                        alt25=2;
                        }
                        break;
                    case INT:
                        {
                        alt25=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 25, 0, input);

                        throw nvae;
                    }

                    switch (alt25) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:449:13: time_floatspec[$cal]
                            {
                            pushFollow(FOLLOW_time_floatspec_in_time_spec1195);
                            time_floatspec(cal);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:450:13: time_separatorspec[$cal]
                            {
                            pushFollow(FOLLOW_time_separatorspec_in_time_spec1214);
                            time_separatorspec(cal);

                            state._fsp--;


                            }
                            break;
                        case 3 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:451:13: time_naturalspec[$cal]
                            {
                            pushFollow(FOLLOW_time_naturalspec_in_time_spec1229);
                            time_naturalspec(cal);

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:452:9: ( AT )? time_point_in_timespec[$cal]
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:452:9: ( AT )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==AT) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:452:9: AT
                            {
                            match(input,AT,FOLLOW_AT_in_time_spec1243); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_time_point_in_timespec_in_time_spec1246);
                    time_point_in_timespec(cal);

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "time_spec"


    // $ANTLR start "time_floatspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:459:1: time_floatspec[Calendar cal ] : DOT deciHour= INT HOURS ;
    public final void time_floatspec(Calendar cal) throws RecognitionException {
        Token deciHour=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:460:4: ( DOT deciHour= INT HOURS )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:460:6: DOT deciHour= INT HOURS
            {
            match(input,DOT,FOLLOW_DOT_in_time_floatspec1276); 
            deciHour=(Token)match(input,INT,FOLLOW_INT_in_time_floatspec1280); 
            match(input,HOURS,FOLLOW_HOURS_in_time_floatspec1282); 

                  cal.set( Calendar.MINUTE, (int)((float)Integer.parseInt( (deciHour!=null?deciHour.getText():null) ) * 0.1f * 60) );
                  cal.set( Calendar.SECOND, 0 );
               

            }

        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "time_floatspec"


    // $ANTLR start "time_separatorspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:471:1: time_separatorspec[Calendar cal] : COLON m= INT ( COLON s= INT )? ;
    public final void time_separatorspec(Calendar cal) throws RecognitionException {
        Token m=null;
        Token s=null;


              cal.set( Calendar.SECOND, 0 );
           
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:476:4: ( COLON m= INT ( COLON s= INT )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:476:7: COLON m= INT ( COLON s= INT )?
            {
            match(input,COLON,FOLLOW_COLON_in_time_separatorspec1327); 
            m=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec1331); 
             cal.set( Calendar.MINUTE, Integer.parseInt( (m!=null?m.getText():null) ) ); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:477:6: ( COLON s= INT )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==COLON) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:477:7: COLON s= INT
                    {
                    match(input,COLON,FOLLOW_COLON_in_time_separatorspec1341); 
                    s=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec1345); 
                     cal.set( Calendar.SECOND, Integer.parseInt( (s!=null?s.getText():null) ) ); 

                    }
                    break;

            }


            }

        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "time_separatorspec"


    // $ANTLR start "time_naturalspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:484:1: time_naturalspec[Calendar cal] : ( time_naturalspec_hour[$cal] | time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] );
    public final void time_naturalspec(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:485:4: ( time_naturalspec_hour[$cal] | time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )
            int alt29=3;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==INT) ) {
                switch ( input.LA(2) ) {
                case HOURS:
                    {
                    alt29=1;
                    }
                    break;
                case MINUTES:
                    {
                    alt29=2;
                    }
                    break;
                case SECONDS:
                    {
                    alt29=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 29, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }
            switch (alt29) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:485:8: time_naturalspec_hour[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_hour_in_time_naturalspec1379);
                    time_naturalspec_hour(cal);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:486:8: time_naturalspec_minute[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_minute_in_time_naturalspec1391);
                    time_naturalspec_minute(cal);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:487:8: time_naturalspec_second[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_second_in_time_naturalspec1401);
                    time_naturalspec_second(cal);

                    state._fsp--;


                    }
                    break;

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
    // $ANTLR end "time_naturalspec"


    // $ANTLR start "time_naturalspec_hour"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:490:1: time_naturalspec_hour[Calendar cal] : h= INT HOURS ( time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )? ;
    public final void time_naturalspec_hour(Calendar cal) throws RecognitionException {
        Token h=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:491:4: (h= INT HOURS ( time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:491:6: h= INT HOURS ( time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )?
            {
            h=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec_hour1421); 
            match(input,HOURS,FOLLOW_HOURS_in_time_naturalspec_hour1423); 

                    cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( (h!=null?h.getText():null) ) );
                 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:495:6: ( time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )?
            int alt30=3;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==INT) ) {
                int LA30_1 = input.LA(2);

                if ( (LA30_1==MINUTES) ) {
                    alt30=1;
                }
                else if ( (LA30_1==SECONDS) ) {
                    alt30=2;
                }
            }
            switch (alt30) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:495:9: time_naturalspec_minute[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_minute_in_time_naturalspec_hour1440);
                    time_naturalspec_minute(cal);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:496:9: time_naturalspec_second[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_second_in_time_naturalspec_hour1451);
                    time_naturalspec_second(cal);

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "time_naturalspec_hour"


    // $ANTLR start "time_naturalspec_minute"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:503:1: time_naturalspec_minute[Calendar cal] : m= INT MINUTES ( time_naturalspec_second[$cal] )? ;
    public final void time_naturalspec_minute(Calendar cal) throws RecognitionException {
        Token m=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:504:4: (m= INT MINUTES ( time_naturalspec_second[$cal] )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:504:6: m= INT MINUTES ( time_naturalspec_second[$cal] )?
            {
            m=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec_minute1484); 
            match(input,MINUTES,FOLLOW_MINUTES_in_time_naturalspec_minute1486); 

                    cal.set( Calendar.MINUTE, Integer.parseInt( (m!=null?m.getText():null) ) );
                 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:508:6: ( time_naturalspec_second[$cal] )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==INT) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:508:6: time_naturalspec_second[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_second_in_time_naturalspec_minute1500);
                    time_naturalspec_second(cal);

                    state._fsp--;


                    }
                    break;

            }


            }

        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "time_naturalspec_minute"


    // $ANTLR start "time_naturalspec_second"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:515:1: time_naturalspec_second[Calendar cal] : s= INT SECONDS ;
    public final void time_naturalspec_second(Calendar cal) throws RecognitionException {
        Token s=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:516:4: (s= INT SECONDS )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:516:6: s= INT SECONDS
            {
            s=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec_second1532); 
            match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec_second1534); 

                    cal.set( Calendar.SECOND, Integer.parseInt( (s!=null?s.getText():null) ) );
                 

            }

        }
        catch ( NumberFormatException nfe ) {

                  throw new RecognitionException();
               
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "time_naturalspec_second"


    // $ANTLR start "time_point_in_timespec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:526:1: time_point_in_timespec[Calendar cal] : ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | (v= INT ( time_separatorspec[$cal] )? ( am_pm_spec[$cal] )? ) );
    public final void time_point_in_timespec(Calendar cal) throws RecognitionException {
        Token v=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:527:4: ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | (v= INT ( time_separatorspec[$cal] )? ( am_pm_spec[$cal] )? ) )
            int alt34=4;
            switch ( input.LA(1) ) {
            case NEVER:
                {
                alt34=1;
                }
                break;
            case MIDNIGHT:
                {
                alt34=2;
                }
                break;
            case MIDDAY:
            case NOON:
                {
                alt34=3;
                }
                break;
            case INT:
                {
                alt34=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }

            switch (alt34) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:527:6: NEVER
                    {
                    match(input,NEVER,FOLLOW_NEVER_in_time_point_in_timespec1569); 

                          cal.clear();
                       

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:531:6: MIDNIGHT
                    {
                    match(input,MIDNIGHT,FOLLOW_MIDNIGHT_in_time_point_in_timespec1581); 

                          cal.set( Calendar.HOUR_OF_DAY, 23 );
                          cal.set( Calendar.MINUTE, 59 );
                          cal.set( Calendar.SECOND, 59 );
                       

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:537:6: ( MIDDAY | NOON )
                    {
                    if ( (input.LA(1)>=MIDDAY && input.LA(1)<=NOON) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                          cal.set( Calendar.HOUR_OF_DAY, 12 );
                          cal.set( Calendar.MINUTE, 00 );
                          cal.set( Calendar.SECOND, 00 );
                       

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:544:4: (v= INT ( time_separatorspec[$cal] )? ( am_pm_spec[$cal] )? )
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:544:4: (v= INT ( time_separatorspec[$cal] )? ( am_pm_spec[$cal] )? )
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:544:5: v= INT ( time_separatorspec[$cal] )? ( am_pm_spec[$cal] )?
                    {
                    v=(Token)match(input,INT,FOLLOW_INT_in_time_point_in_timespec1617); 

                             setCalendarTime( cal, (v!=null?v.getText():null) );
                          
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:548:7: ( time_separatorspec[$cal] )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==COLON) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:548:8: time_separatorspec[$cal]
                            {
                            pushFollow(FOLLOW_time_separatorspec_in_time_point_in_timespec1634);
                            time_separatorspec(cal);

                            state._fsp--;


                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:549:7: ( am_pm_spec[$cal] )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==40||LA33_0==42) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:549:8: am_pm_spec[$cal]
                            {
                            pushFollow(FOLLOW_am_pm_spec_in_time_point_in_timespec1646);
                            am_pm_spec(cal);

                            state._fsp--;


                            }
                            break;

                    }


                    }


                    }
                    break;

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
    // $ANTLR end "time_point_in_timespec"


    // $ANTLR start "am_pm_spec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:552:1: am_pm_spec[Calendar cal] : ( 'a' ( 'm' )? | 'p' ( 'm' )? );
    public final void am_pm_spec(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:4: ( 'a' ( 'm' )? | 'p' ( 'm' )? )
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==40) ) {
                alt37=1;
            }
            else if ( (LA37_0==42) ) {
                alt37=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }
            switch (alt37) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:6: 'a' ( 'm' )?
                    {
                    match(input,40,FOLLOW_40_in_am_pm_spec1671); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:9: ( 'm' )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==41) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:10: 'm'
                            {
                            match(input,41,FOLLOW_41_in_am_pm_spec1673); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:555:6: 'p' ( 'm' )?
                    {
                    match(input,42,FOLLOW_42_in_am_pm_spec1682); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:555:9: ( 'm' )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==41) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:555:10: 'm'
                            {
                            match(input,41,FOLLOW_41_in_am_pm_spec1684); 

                            }
                            break;

                    }


                          cal.add( Calendar.HOUR_OF_DAY, 12 );
                       

                    }
                    break;

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
    // $ANTLR end "am_pm_spec"

    // Delegated rules


 

    public static final BitSet FOLLOW_date_spec_in_parseDateTime71 = new BitSet(new long[]{0x0000001E10000012L});
    public static final BitSet FOLLOW_time_spec_in_parseDateTime75 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_full_in_date_spec118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_in_date_spec139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_in_X_YMWD_in_date_spec162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_end_of_the_MW_in_date_spec178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_natural_in_date_spec190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_full221 = new BitSet(new long[]{0x00000000000000E0L});
    public static final BitSet FOLLOW_set_in_date_full223 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_date_full259 = new BitSet(new long[]{0x00000000000000E0L});
    public static final BitSet FOLLOW_set_in_date_full261 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_date_full297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_full344 = new BitSet(new long[]{0x00000000000000E0L});
    public static final BitSet FOLLOW_set_in_date_full346 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_date_full382 = new BitSet(new long[]{0x00000000000000E0L});
    public static final BitSet FOLLOW_set_in_date_full384 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_date_full420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_date_on470 = new BitSet(new long[]{0x0000000000007010L});
    public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_M_Xst_in_date_on493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_weekday_in_date_on512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M534 = new BitSet(new long[]{0x0000008000001E62L});
    public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M536 = new BitSet(new long[]{0x0000008000001C62L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of_M554 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M584 = new BitSet(new long[]{0x0000000000000072L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of_M603 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst667 = new BitSet(new long[]{0x0000000000000870L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst682 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_date_on_M_Xst702 = new BitSet(new long[]{0x0000008000000A72L});
    public static final BitSet FOLLOW_STs_in_date_on_M_Xst717 = new BitSet(new long[]{0x0000008000000872L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst720 = new BitSet(new long[]{0x0000008000000872L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst729 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_date_on_M_Xst753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEXT_in_date_on_weekday805 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IN_in_date_in_X_YMWD847 = new BitSet(new long[]{0x0000000000020010L});
    public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD862 = new BitSet(new long[]{0x0000000000010802L});
    public static final BitSet FOLLOW_set_in_date_in_X_YMWD872 = new BitSet(new long[]{0x0000000000020010L});
    public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD881 = new BitSet(new long[]{0x0000000000010802L});
    public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance918 = new BitSet(new long[]{0x00000000003C0000L});
    public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance933 = new BitSet(new long[]{0x00000000003C0000L});
    public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_in_date_end_of_the_MW1038 = new BitSet(new long[]{0x0000000000980400L});
    public static final BitSet FOLLOW_OF_in_date_end_of_the_MW1040 = new BitSet(new long[]{0x0000000000980000L});
    public static final BitSet FOLLOW_THE_in_date_end_of_the_MW1043 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_date_natural1106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOMORROW_in_date_natural1126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_YESTERDAY_in_date_natural1140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_spec1169 = new BitSet(new long[]{0x0000000040000030L});
    public static final BitSet FOLLOW_time_floatspec_in_time_spec1195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_separatorspec_in_time_spec1214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_in_time_spec1229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_time_spec1243 = new BitSet(new long[]{0x0000001E10000010L});
    public static final BitSet FOLLOW_time_point_in_timespec_in_time_spec1246 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_time_floatspec1276 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_time_floatspec1280 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_HOURS_in_time_floatspec1282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_time_separatorspec1327 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec1331 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_COLON_in_time_separatorspec1341 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec1345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_hour_in_time_naturalspec1379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_minute_in_time_naturalspec1391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_second_in_time_naturalspec1401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec_hour1421 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_HOURS_in_time_naturalspec_hour1423 = new BitSet(new long[]{0x0000000040000032L});
    public static final BitSet FOLLOW_time_naturalspec_minute_in_time_naturalspec_hour1440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_second_in_time_naturalspec_hour1451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec_minute1484 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_MINUTES_in_time_naturalspec_minute1486 = new BitSet(new long[]{0x0000000040000032L});
    public static final BitSet FOLLOW_time_naturalspec_second_in_time_naturalspec_minute1500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec_second1532 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec_second1534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEVER_in_time_point_in_timespec1569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIDNIGHT_in_time_point_in_timespec1581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_time_point_in_timespec1593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_point_in_timespec1617 = new BitSet(new long[]{0x0000050040000002L});
    public static final BitSet FOLLOW_time_separatorspec_in_time_point_in_timespec1634 = new BitSet(new long[]{0x0000050000000002L});
    public static final BitSet FOLLOW_am_pm_spec_in_time_point_in_timespec1646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_am_pm_spec1671 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_am_pm_spec1673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_am_pm_spec1682 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_am_pm_spec1684 = new BitSet(new long[]{0x0000000000000002L});

}