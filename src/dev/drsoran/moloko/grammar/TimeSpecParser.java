// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g 2010-07-09 13:43:54

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INT", "DOT", "COLON", "MINUS", "DATE_SEP", "ON", "STs", "OF", "COMMA", "MONTH", "NEXT", "WEEKDAY", "IN", "AND", "NUM_STR", "YEARS", "MONTHS", "WEEKS", "DAYS", "END", "THE", "TODAY", "TONIGHT", "TOMORROW", "YESTERDAY", "AT", "HOURS", "MINUTES", "SECONDS", "NEVER", "MIDNIGHT", "MIDDAY", "NOON", "NOW", "WS", "'-a'", "'A'", "'M'", "'P'"
    };
    public static final int T__42=42;
    public static final int STs=10;
    public static final int MIDDAY=35;
    public static final int T__40=40;
    public static final int TODAY=25;
    public static final int T__41=41;
    public static final int THE=24;
    public static final int ON=9;
    public static final int NOW=37;
    public static final int SECONDS=32;
    public static final int INT=4;
    public static final int MIDNIGHT=34;
    public static final int MINUS=7;
    public static final int AND=17;
    public static final int YEARS=19;
    public static final int EOF=-1;
    public static final int OF=11;
    public static final int NUM_STR=18;
    public static final int MONTH=13;
    public static final int MINUTES=31;
    public static final int AT=29;
    public static final int COLON=6;
    public static final int DAYS=22;
    public static final int WS=38;
    public static final int WEEKS=21;
    public static final int WEEKDAY=15;
    public static final int IN=16;
    public static final int TONIGHT=26;
    public static final int COMMA=12;
    public static final int T__39=39;
    public static final int MONTHS=20;
    public static final int NOON=36;
    public static final int NEXT=14;
    public static final int NEVER=33;
    public static final int DATE_SEP=8;
    public static final int END=23;
    public static final int DOT=5;
    public static final int YESTERDAY=28;
    public static final int HOURS=30;
    public static final int TOMORROW=27;

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
                sdf = new SimpleDateFormat( "hh" );
             }
             else if ( len > 3 )
             {
                sdf = new SimpleDateFormat( "hhmm" );
             }
             else
             {
                sdf = new SimpleDateFormat( "hmm" );
             }

    			sdf.parse( pit );
    			
    			final Calendar sdfCal = sdf.getCalendar();
    			cal.set( Calendar.HOUR_OF_DAY, sdfCal.get( Calendar.HOUR_OF_DAY ) );
          	cal.set( Calendar.MINUTE, 		 sdfCal.get( Calendar.MINUTE ) );
          	cal.set( Calendar.SECOND, 		 0 );
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:219:1: parseDateTime[Calendar cal] : ( date_spec[ $cal ] ( time_spec[ $cal ] )? | time_spec[ $cal ] ) ;
    public final void parseDateTime(Calendar cal) throws RecognitionException {

        		boolean hasTime = false;
        	
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:227:4: ( ( date_spec[ $cal ] ( time_spec[ $cal ] )? | time_spec[ $cal ] ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:227:6: ( date_spec[ $cal ] ( time_spec[ $cal ] )? | time_spec[ $cal ] )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:227:6: ( date_spec[ $cal ] ( time_spec[ $cal ] )? | time_spec[ $cal ] )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==INT||LA2_0==ON||(LA2_0>=MONTH && LA2_0<=IN)||LA2_0==NUM_STR||LA2_0==END||(LA2_0>=TODAY && LA2_0<=YESTERDAY)) ) {
                alt2=1;
            }
            else if ( (LA2_0==AT||(LA2_0>=NEVER && LA2_0<=NOON)) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:227:9: date_spec[ $cal ] ( time_spec[ $cal ] )?
                    {
                    pushFollow(FOLLOW_date_spec_in_parseDateTime65);
                    date_spec(cal);

                    state._fsp--;

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:227:27: ( time_spec[ $cal ] )?
                    int alt1=2;
                    int LA1_0 = input.LA(1);

                    if ( (LA1_0==INT||LA1_0==AT||(LA1_0>=NEVER && LA1_0<=NOON)) ) {
                        alt1=1;
                    }
                    switch (alt1) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:227:28: time_spec[ $cal ]
                            {
                            pushFollow(FOLLOW_time_spec_in_parseDateTime69);
                            time_spec(cal);

                            state._fsp--;

                             hasTime = true; 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:228:9: time_spec[ $cal ]
                    {
                    pushFollow(FOLLOW_time_spec_in_parseDateTime84);
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
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "parseDateTime"


    // $ANTLR start "date_spec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:240:1: date_spec[Calendar cal] : ( date_full[$cal, true] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) ;
    public final void date_spec(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:241:4: ( ( date_full[$cal, true] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:241:6: ( date_full[$cal, true] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:241:6: ( date_full[$cal, true] | date_on[$cal] | date_in_X_YMWD[$cal] | date_end_of_the_MW[$cal] | date_natural[$cal] )
            int alt3=5;
            switch ( input.LA(1) ) {
            case INT:
                {
                switch ( input.LA(2) ) {
                case DOT:
                case MINUS:
                    {
                    int LA3_6 = input.LA(3);

                    if ( (LA3_6==INT) ) {
                        alt3=1;
                    }
                    else if ( (LA3_6==MONTH) ) {
                        alt3=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                case YEARS:
                case MONTHS:
                case WEEKS:
                case DAYS:
                    {
                    alt3=3;
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
                    alt3=2;
                    }
                    break;
                case COLON:
                case DATE_SEP:
                    {
                    alt3=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 3, 1, input);

                    throw nvae;
                }

                }
                break;
            case ON:
            case MONTH:
            case NEXT:
            case WEEKDAY:
                {
                alt3=2;
                }
                break;
            case IN:
            case NUM_STR:
                {
                alt3=3;
                }
                break;
            case END:
                {
                alt3=4;
                }
                break;
            case TODAY:
            case TONIGHT:
            case TOMORROW:
            case YESTERDAY:
                {
                alt3=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:241:10: date_full[$cal, true]
                    {
                    pushFollow(FOLLOW_date_full_in_date_spec128);
                    date_full(cal, true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:242:10: date_on[$cal]
                    {
                    pushFollow(FOLLOW_date_on_in_date_spec149);
                    date_on(cal);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:243:10: date_in_X_YMWD[$cal]
                    {
                    pushFollow(FOLLOW_date_in_X_YMWD_in_date_spec167);
                    date_in_X_YMWD(cal);

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:244:10: date_end_of_the_MW[$cal]
                    {
                    pushFollow(FOLLOW_date_end_of_the_MW_in_date_spec183);
                    date_end_of_the_MW(cal);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:245:10: date_natural[$cal]
                    {
                    pushFollow(FOLLOW_date_natural_in_date_spec195);
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:248:1: date_full[Calendar cal, boolean dayFirst] : ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT ) ;
    public final void date_full(Calendar cal, boolean dayFirst) throws RecognitionException {
        Token pt1=null;
        Token pt2=null;
        Token pt3=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:249:4: ( ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:249:6: ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:249:6: ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==INT) ) {
                int LA4_1 = input.LA(2);

                if ( ((LA4_1>=DOT && LA4_1<=DATE_SEP)) ) {
                    int LA4_2 = input.LA(3);

                    if ( (LA4_2==INT) ) {
                        int LA4_3 = input.LA(4);

                        if ( ((LA4_3>=DOT && LA4_3<=DATE_SEP)) ) {
                            int LA4_4 = input.LA(5);

                            if ( (LA4_4==INT) ) {
                                int LA4_5 = input.LA(6);

                                if ( ((dayFirst)) ) {
                                    alt4=1;
                                }
                                else if ( (true) ) {
                                    alt4=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 4, 5, input);

                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 4, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 4, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:249:8: {...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT
                    {
                    if ( !((dayFirst)) ) {
                        throw new FailedPredicateException(input, "date_full", "$dayFirst");
                    }
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_date_full222); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt2=(Token)match(input,INT,FOLLOW_INT_in_date_full264); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt3=(Token)match(input,INT,FOLLOW_INT_in_date_full306); 

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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:274:21: pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT
                    {
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_date_full353); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt2=(Token)match(input,INT,FOLLOW_INT_in_date_full395); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt3=(Token)match(input,INT,FOLLOW_INT_in_date_full437); 

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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:301:1: date_on[Calendar cal] : ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) ;
    public final void date_on(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:302:4: ( ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:302:6: ( ON )? ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:302:6: ( ON )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==ON) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:302:6: ON
                    {
                    match(input,ON,FOLLOW_ON_in_date_on487); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:302:10: ( date_on_Xst_of_M[$cal] | date_on_M_Xst[$cal] | date_on_weekday[$cal] )
            int alt6=3;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt6=1;
                }
                break;
            case MONTH:
                {
                alt6=2;
                }
                break;
            case NEXT:
            case WEEKDAY:
                {
                alt6=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:302:14: date_on_Xst_of_M[$cal]
                    {
                    pushFollow(FOLLOW_date_on_Xst_of_M_in_date_on494);
                    date_on_Xst_of_M(cal);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:303:14: date_on_M_Xst[$cal]
                    {
                    pushFollow(FOLLOW_date_on_M_Xst_in_date_on510);
                    date_on_M_Xst(cal);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:304:14: date_on_weekday[$cal]
                    {
                    pushFollow(FOLLOW_date_on_weekday_in_date_on529);
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:307:1: date_on_Xst_of_M[Calendar cal] : d= INT ( STs )? ( ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? ;
    public final void date_on_Xst_of_M(Calendar cal) throws RecognitionException {
        Token d=null;
        Token m=null;
        Token y=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:4: (d= INT ( STs )? ( ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:6: d= INT ( STs )? ( ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            {
            d=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of_M551); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:12: ( STs )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==STs) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:308:12: STs
                    {
                    match(input,STs,FOLLOW_STs_in_date_on_Xst_of_M553); 

                    }
                    break;

            }


                      cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( (d!=null?d.getText():null) ) );
                 	 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:312:6: ( ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==DOT||LA11_0==MINUS||(LA11_0>=OF && LA11_0<=MONTH)||LA11_0==39) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:312:7: ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:312:7: ( OF | '-a' | MINUS | COMMA | DOT )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==DOT||LA8_0==MINUS||(LA8_0>=OF && LA8_0<=COMMA)||LA8_0==39) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
                            {
                            if ( input.LA(1)==DOT||input.LA(1)==MINUS||(input.LA(1)>=OF && input.LA(1)<=COMMA)||input.LA(1)==39 ) {
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

                    m=(Token)match(input,MONTH,FOLLOW_MONTH_in_date_on_Xst_of_M599); 

                             	parseTextMonth( cal, (m!=null?m.getText():null) );
                             
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:317:7: ( MINUS | DOT )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==DOT||LA9_0==MINUS) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
                            {
                            if ( input.LA(1)==DOT||input.LA(1)==MINUS ) {
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

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:7: (y= INT )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==INT) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:318:8: y= INT
                            {
                            y=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of_M636); 

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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:328:1: date_on_M_Xst[Calendar cal] : m= MONTH ( MINUS | COMMA | DOT )? d= INT ( STs )? ( MINUS | DOT )? ( '-a' | MINUS | COMMA | DOT )? (y= INT )? ;
    public final void date_on_M_Xst(Calendar cal) throws RecognitionException {
        Token m=null;
        Token d=null;
        Token y=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:329:4: (m= MONTH ( MINUS | COMMA | DOT )? d= INT ( STs )? ( MINUS | DOT )? ( '-a' | MINUS | COMMA | DOT )? (y= INT )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:329:6: m= MONTH ( MINUS | COMMA | DOT )? d= INT ( STs )? ( MINUS | DOT )? ( '-a' | MINUS | COMMA | DOT )? (y= INT )?
            {
            m=(Token)match(input,MONTH,FOLLOW_MONTH_in_date_on_M_Xst685); 

               	 	parseTextMonth( cal, (m!=null?m.getText():null) );
               	 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:333:5: ( MINUS | COMMA | DOT )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==DOT||LA12_0==MINUS||LA12_0==COMMA) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
                    {
                    if ( input.LA(1)==DOT||input.LA(1)==MINUS||input.LA(1)==COMMA ) {
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

            d=(Token)match(input,INT,FOLLOW_INT_in_date_on_M_Xst718); 

                 	 	cal.set( Calendar.DAY_OF_MONTH, Integer.parseInt( (d!=null?d.getText():null) ) );
                 	 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:338:5: ( STs )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==STs) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:338:5: STs
                    {
                    match(input,STs,FOLLOW_STs_in_date_on_M_Xst733); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:338:10: ( MINUS | DOT )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==DOT||LA14_0==MINUS) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
                    {
                    if ( input.LA(1)==DOT||input.LA(1)==MINUS ) {
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:338:25: ( '-a' | MINUS | COMMA | DOT )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==DOT||LA15_0==MINUS||LA15_0==COMMA||LA15_0==39) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
                    {
                    if ( input.LA(1)==DOT||input.LA(1)==MINUS||input.LA(1)==COMMA||input.LA(1)==39 ) {
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:339:5: (y= INT )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==INT) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:339:6: y= INT
                    {
                    y=(Token)match(input,INT,FOLLOW_INT_in_date_on_M_Xst769); 

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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:349:1: date_on_weekday[Calendar cal] : ( NEXT )? wd= WEEKDAY ;
    public final void date_on_weekday(Calendar cal) throws RecognitionException {
        Token wd=null;


              boolean nextWeek = false;
           
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:354:4: ( ( NEXT )? wd= WEEKDAY )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:354:6: ( NEXT )? wd= WEEKDAY
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:354:6: ( NEXT )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==NEXT) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:354:7: NEXT
                    {
                    match(input,NEXT,FOLLOW_NEXT_in_date_on_weekday820); 
                     nextWeek = true; 

                    }
                    break;

            }

            wd=(Token)match(input,WEEKDAY,FOLLOW_WEEKDAY_in_date_on_weekday828); 

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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:378:1: date_in_X_YMWD[Calendar cal] : ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* ;
    public final void date_in_X_YMWD(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:379:4: ( ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )* )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:379:7: ( IN )? date_in_X_YMWD_distance[$cal] ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:379:7: ( IN )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==IN) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:379:7: IN
                    {
                    match(input,IN,FOLLOW_IN_in_date_in_X_YMWD862); 

                    }
                    break;

            }

            pushFollow(FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD877);
            date_in_X_YMWD_distance(cal);

            state._fsp--;

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:380:7: ( ( AND | COMMA ) date_in_X_YMWD_distance[$cal] )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==COMMA||LA19_0==AND) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:380:8: ( AND | COMMA ) date_in_X_YMWD_distance[$cal]
            	    {
            	    if ( input.LA(1)==COMMA||input.LA(1)==AND ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD896);
            	    date_in_X_YMWD_distance(cal);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop19;
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:383:1: date_in_X_YMWD_distance[Calendar cal] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
    public final void date_in_X_YMWD_distance(Calendar cal) throws RecognitionException {
        Token a=null;


              int amount   = -1;
              int calField = Calendar.YEAR;
           
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:389:4: ( (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:389:6: (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:389:6: (a= NUM_STR | a= INT )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==NUM_STR) ) {
                alt20=1;
            }
            else if ( (LA20_0==INT) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:389:10: a= NUM_STR
                    {
                    a=(Token)match(input,NUM_STR,FOLLOW_NUM_STR_in_date_in_X_YMWD_distance933); 
                     amount = strToNumber( (a!=null?a.getText():null) );      

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:390:10: a= INT
                    {
                    a=(Token)match(input,INT,FOLLOW_INT_in_date_in_X_YMWD_distance948); 
                     amount = Integer.parseInt( (a!=null?a.getText():null) ); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:391:6: ( YEARS | MONTHS | WEEKS | DAYS )
            int alt21=4;
            switch ( input.LA(1) ) {
            case YEARS:
                {
                alt21=1;
                }
                break;
            case MONTHS:
                {
                alt21=2;
                }
                break;
            case WEEKS:
                {
                alt21=3;
                }
                break;
            case DAYS:
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:391:12: YEARS
                    {
                    match(input,YEARS,FOLLOW_YEARS_in_date_in_X_YMWD_distance968); 

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:392:12: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_date_in_X_YMWD_distance981); 
                     calField = Calendar.MONTH;            

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:393:12: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_date_in_X_YMWD_distance997); 
                     calField = Calendar.WEEK_OF_YEAR;     

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:394:12: DAYS
                    {
                    match(input,DAYS,FOLLOW_DAYS_in_date_in_X_YMWD_distance1014); 
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:411:1: date_end_of_the_MW[Calendar cal] : END ( OF )? ( THE )? ( WEEKS | MONTHS ) ;
    public final void date_end_of_the_MW(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:412:4: ( END ( OF )? ( THE )? ( WEEKS | MONTHS ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:412:6: END ( OF )? ( THE )? ( WEEKS | MONTHS )
            {
            match(input,END,FOLLOW_END_in_date_end_of_the_MW1053); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:412:10: ( OF )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==OF) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:412:10: OF
                    {
                    match(input,OF,FOLLOW_OF_in_date_end_of_the_MW1055); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:412:14: ( THE )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==THE) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:412:14: THE
                    {
                    match(input,THE,FOLLOW_THE_in_date_end_of_the_MW1058); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:413:6: ( WEEKS | MONTHS )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==WEEKS) ) {
                alt24=1;
            }
            else if ( (LA24_0==MONTHS) ) {
                alt24=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:413:10: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_date_end_of_the_MW1070); 

                                rollToEndOf( Calendar.DAY_OF_WEEK, cal );
                             

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:417:10: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_date_end_of_the_MW1092); 

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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:423:1: date_natural[Calendar cal] : ( ( TODAY | TONIGHT ) | TOMORROW | YESTERDAY );
    public final void date_natural(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:424:2: ( ( TODAY | TONIGHT ) | TOMORROW | YESTERDAY )
            int alt25=3;
            switch ( input.LA(1) ) {
            case TODAY:
            case TONIGHT:
                {
                alt25=1;
                }
                break;
            case TOMORROW:
                {
                alt25=2;
                }
                break;
            case YESTERDAY:
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:424:4: ( TODAY | TONIGHT )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:428:4: TOMORROW
                    {
                    match(input,TOMORROW,FOLLOW_TOMORROW_in_date_natural1135); 

                    	  	  cal.roll( Calendar.DAY_OF_YEAR, true );
                    	  

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:432:4: YESTERDAY
                    {
                    match(input,YESTERDAY,FOLLOW_YESTERDAY_in_date_natural1145); 

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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:438:1: time_spec[Calendar cal] : (h= INT ( time_floatspec[$cal ] | time_separatorspec[$cal, true] | time_naturalspec[$cal, ] ) | ( AT )? time_point_in_timespec[$cal] ) ;
    public final void time_spec(Calendar cal) throws RecognitionException {
        Token h=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:439:4: ( (h= INT ( time_floatspec[$cal ] | time_separatorspec[$cal, true] | time_naturalspec[$cal, ] ) | ( AT )? time_point_in_timespec[$cal] ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:439:6: (h= INT ( time_floatspec[$cal ] | time_separatorspec[$cal, true] | time_naturalspec[$cal, ] ) | ( AT )? time_point_in_timespec[$cal] )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:439:6: (h= INT ( time_floatspec[$cal ] | time_separatorspec[$cal, true] | time_naturalspec[$cal, ] ) | ( AT )? time_point_in_timespec[$cal] )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==INT) ) {
                switch ( input.LA(2) ) {
                case DOT:
                    {
                    int LA28_3 = input.LA(3);

                    if ( (LA28_3==INT) ) {
                        alt28=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 3, input);

                        throw nvae;
                    }
                    }
                    break;
                case COLON:
                    {
                    int LA28_4 = input.LA(3);

                    if ( (LA28_4==INT) ) {
                        alt28=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 28, 4, input);

                        throw nvae;
                    }
                    }
                    break;
                case INT:
                    {
                    alt28=1;
                    }
                    break;
                case EOF:
                case 40:
                case 42:
                    {
                    alt28=2;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 28, 1, input);

                    throw nvae;
                }

            }
            else if ( (LA28_0==AT||(LA28_0>=NEVER && LA28_0<=NOON)) ) {
                alt28=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:439:9: h= INT ( time_floatspec[$cal ] | time_separatorspec[$cal, true] | time_naturalspec[$cal, ] )
                    {
                    h=(Token)match(input,INT,FOLLOW_INT_in_time_spec1170); 

                       		    cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( (h!=null?h.getText():null) ) );
                       		 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:443:7: ( time_floatspec[$cal ] | time_separatorspec[$cal, true] | time_naturalspec[$cal, ] )
                    int alt26=3;
                    switch ( input.LA(1) ) {
                    case DOT:
                        {
                        int LA26_1 = input.LA(2);

                        if ( (LA26_1==INT) ) {
                            int LA26_4 = input.LA(3);

                            if ( (LA26_4==HOURS) ) {
                                alt26=1;
                            }
                            else if ( (LA26_4==EOF||(LA26_4>=DOT && LA26_4<=COLON)) ) {
                                alt26=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 26, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 26, 1, input);

                            throw nvae;
                        }
                        }
                        break;
                    case COLON:
                        {
                        alt26=2;
                        }
                        break;
                    case INT:
                        {
                        alt26=3;
                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 26, 0, input);

                        throw nvae;
                    }

                    switch (alt26) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:443:11: time_floatspec[$cal ]
                            {
                            pushFollow(FOLLOW_time_floatspec_in_time_spec1190);
                            time_floatspec(cal);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:444:13: time_separatorspec[$cal, true]
                            {
                            pushFollow(FOLLOW_time_separatorspec_in_time_spec1209);
                            time_separatorspec(cal, true);

                            state._fsp--;


                            }
                            break;
                        case 3 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:445:13: time_naturalspec[$cal, ]
                            {
                            pushFollow(FOLLOW_time_naturalspec_in_time_spec1226);
                            time_naturalspec(cal);

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:446:9: ( AT )? time_point_in_timespec[$cal]
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:446:9: ( AT )?
                    int alt27=2;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0==AT) ) {
                        alt27=1;
                    }
                    switch (alt27) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:446:9: AT
                            {
                            match(input,AT,FOLLOW_AT_in_time_spec1240); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_time_point_in_timespec_in_time_spec1243);
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:453:1: time_floatspec[Calendar cal ] : DOT deciHour= INT HOURS ;
    public final void time_floatspec(Calendar cal) throws RecognitionException {
        Token deciHour=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:454:4: ( DOT deciHour= INT HOURS )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:454:6: DOT deciHour= INT HOURS
            {
            match(input,DOT,FOLLOW_DOT_in_time_floatspec1273); 
            deciHour=(Token)match(input,INT,FOLLOW_INT_in_time_floatspec1277); 
            match(input,HOURS,FOLLOW_HOURS_in_time_floatspec1279); 

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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:465:1: time_separatorspec[Calendar cal, boolean onlyColon] : ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? ) ;
    public final void time_separatorspec(Calendar cal, boolean onlyColon) throws RecognitionException {
        Token m=null;
        Token s=null;


        	  	cal.set( Calendar.SECOND, 0 );
        	
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:470:4: ( ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:470:6: ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:470:6: ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==COLON) ) {
                int LA31_1 = input.LA(2);

                if ( (LA31_1==INT) ) {
                    int LA31_3 = input.LA(3);

                    if ( ((onlyColon)) ) {
                        alt31=1;
                    }
                    else if ( (true) ) {
                        alt31=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 31, 3, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA31_0==DOT) ) {
                alt31=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:472:8: {...}? COLON m= INT ( COLON s= INT )?
                    {
                    if ( !((onlyColon)) ) {
                        throw new FailedPredicateException(input, "time_separatorspec", "$onlyColon");
                    }
                    match(input,COLON,FOLLOW_COLON_in_time_separatorspec1339); 
                    m=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec1348); 
                     cal.set( Calendar.MINUTE, Integer.parseInt( (m!=null?m.getText():null) ) ); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:473:21: ( COLON s= INT )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==COLON) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:473:22: COLON s= INT
                            {
                            match(input,COLON,FOLLOW_COLON_in_time_separatorspec1373); 
                            s=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec1382); 
                             cal.set( Calendar.SECOND, Integer.parseInt( (s!=null?s.getText():null) ) ); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:475:21: ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )?
                    {
                    if ( (input.LA(1)>=DOT && input.LA(1)<=COLON) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    m=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec1425); 
                     cal.set( Calendar.MINUTE, Integer.parseInt( (m!=null?m.getText():null) ) ); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:476:20: ( ( COLON | DOT ) s= INT )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( ((LA30_0>=DOT && LA30_0<=COLON)) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:476:21: ( COLON | DOT ) s= INT
                            {
                            if ( (input.LA(1)>=DOT && input.LA(1)<=COLON) ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }

                            s=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec1457); 
                             cal.set( Calendar.SECOND, Integer.parseInt( (s!=null?s.getText():null) ) ); 

                            }
                            break;

                    }


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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:483:1: time_naturalspec[Calendar cal] : ( time_naturalspec_hour[$cal] | time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] );
    public final void time_naturalspec(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:484:4: ( time_naturalspec_hour[$cal] | time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )
            int alt32=3;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==INT) ) {
                switch ( input.LA(2) ) {
                case HOURS:
                    {
                    alt32=1;
                    }
                    break;
                case MINUTES:
                    {
                    alt32=2;
                    }
                    break;
                case SECONDS:
                    {
                    alt32=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 32, 1, input);

                    throw nvae;
                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:484:8: time_naturalspec_hour[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_hour_in_time_naturalspec1492);
                    time_naturalspec_hour(cal);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:485:8: time_naturalspec_minute[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_minute_in_time_naturalspec1504);
                    time_naturalspec_minute(cal);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:486:8: time_naturalspec_second[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_second_in_time_naturalspec1514);
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:489:1: time_naturalspec_hour[Calendar cal] : h= INT HOURS ( time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )? ;
    public final void time_naturalspec_hour(Calendar cal) throws RecognitionException {
        Token h=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:490:2: (h= INT HOURS ( time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:490:4: h= INT HOURS ( time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )?
            {
            h=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec_hour1535); 
            match(input,HOURS,FOLLOW_HOURS_in_time_naturalspec_hour1537); 

            	  	  cal.set( Calendar.HOUR_OF_DAY, Integer.parseInt( (h!=null?h.getText():null) ) );
            	  
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:494:4: ( time_naturalspec_minute[$cal] | time_naturalspec_second[$cal] )?
            int alt33=3;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==INT) ) {
                int LA33_1 = input.LA(2);

                if ( (LA33_1==MINUTES) ) {
                    alt33=1;
                }
                else if ( (LA33_1==SECONDS) ) {
                    alt33=2;
                }
            }
            switch (alt33) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:494:7: time_naturalspec_minute[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_minute_in_time_naturalspec_hour1550);
                    time_naturalspec_minute(cal);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:495:7: time_naturalspec_second[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_second_in_time_naturalspec_hour1559);
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:502:1: time_naturalspec_minute[Calendar cal] : m= INT MINUTES ( time_naturalspec_second[$cal] )? ;
    public final void time_naturalspec_minute(Calendar cal) throws RecognitionException {
        Token m=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:503:2: (m= INT MINUTES ( time_naturalspec_second[$cal] )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:503:4: m= INT MINUTES ( time_naturalspec_second[$cal] )?
            {
            m=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec_minute1586); 
            match(input,MINUTES,FOLLOW_MINUTES_in_time_naturalspec_minute1588); 

            	  	  cal.set( Calendar.MINUTE, Integer.parseInt( (m!=null?m.getText():null) ) );
            	  
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:507:4: ( time_naturalspec_second[$cal] )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==INT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:507:4: time_naturalspec_second[$cal]
                    {
                    pushFollow(FOLLOW_time_naturalspec_second_in_time_naturalspec_minute1598);
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:514:1: time_naturalspec_second[Calendar cal] : s= INT SECONDS ;
    public final void time_naturalspec_second(Calendar cal) throws RecognitionException {
        Token s=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:515:2: (s= INT SECONDS )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:515:4: s= INT SECONDS
            {
            s=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec_second1625); 
            match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec_second1627); 

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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:525:1: time_point_in_timespec[Calendar cal] : ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | (v= INT ( time_separatorspec[$cal, false] )? ( am_pm_spec[$cal] )? ) );
    public final void time_point_in_timespec(Calendar cal) throws RecognitionException {
        Token v=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:526:4: ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | (v= INT ( time_separatorspec[$cal, false] )? ( am_pm_spec[$cal] )? ) )
            int alt37=4;
            switch ( input.LA(1) ) {
            case NEVER:
                {
                alt37=1;
                }
                break;
            case MIDNIGHT:
                {
                alt37=2;
                }
                break;
            case MIDDAY:
            case NOON:
                {
                alt37=3;
                }
                break;
            case INT:
                {
                alt37=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }

            switch (alt37) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:526:6: NEVER
                    {
                    match(input,NEVER,FOLLOW_NEVER_in_time_point_in_timespec1656); 

                          cal.clear();
                       

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:530:6: MIDNIGHT
                    {
                    match(input,MIDNIGHT,FOLLOW_MIDNIGHT_in_time_point_in_timespec1669); 

                          cal.set( Calendar.HOUR_OF_DAY, 23 );
                          cal.set( Calendar.MINUTE, 59 );
                          cal.set( Calendar.SECOND, 59 );
                       

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:536:6: ( MIDDAY | NOON )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:543:4: (v= INT ( time_separatorspec[$cal, false] )? ( am_pm_spec[$cal] )? )
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:543:4: (v= INT ( time_separatorspec[$cal, false] )? ( am_pm_spec[$cal] )? )
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:543:5: v= INT ( time_separatorspec[$cal, false] )? ( am_pm_spec[$cal] )?
                    {
                    v=(Token)match(input,INT,FOLLOW_INT_in_time_point_in_timespec1705); 

                             setCalendarTime( cal, (v!=null?v.getText():null) );
                          
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:547:7: ( time_separatorspec[$cal, false] )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( ((LA35_0>=DOT && LA35_0<=COLON)) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:547:8: time_separatorspec[$cal, false]
                            {
                            pushFollow(FOLLOW_time_separatorspec_in_time_point_in_timespec1722);
                            time_separatorspec(cal, false);

                            state._fsp--;


                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:548:7: ( am_pm_spec[$cal] )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==40||LA36_0==42) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:548:8: am_pm_spec[$cal]
                            {
                            pushFollow(FOLLOW_am_pm_spec_in_time_point_in_timespec1736);
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
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:551:1: am_pm_spec[Calendar cal] : ( 'A' ( 'M' )? | 'P' ( 'M' )? );
    public final void am_pm_spec(Calendar cal) throws RecognitionException {
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:553:4: ( 'A' ( 'M' )? | 'P' ( 'M' )? )
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==40) ) {
                alt40=1;
            }
            else if ( (LA40_0==42) ) {
                alt40=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }
            switch (alt40) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:553:6: 'A' ( 'M' )?
                    {
                    match(input,40,FOLLOW_40_in_am_pm_spec1764); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:553:9: ( 'M' )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==41) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:553:10: 'M'
                            {
                            match(input,41,FOLLOW_41_in_am_pm_spec1766); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:6: 'P' ( 'M' )?
                    {
                    match(input,42,FOLLOW_42_in_am_pm_spec1775); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:9: ( 'M' )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==41) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:554:10: 'M'
                            {
                            match(input,41,FOLLOW_41_in_am_pm_spec1777); 

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


 

    public static final BitSet FOLLOW_date_spec_in_parseDateTime65 = new BitSet(new long[]{0x0000001E20000012L});
    public static final BitSet FOLLOW_time_spec_in_parseDateTime69 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_spec_in_parseDateTime84 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_full_in_date_spec128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_in_date_spec149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_in_X_YMWD_in_date_spec167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_end_of_the_MW_in_date_spec183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_natural_in_date_spec195 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_full222 = new BitSet(new long[]{0x00000000000001E0L});
    public static final BitSet FOLLOW_set_in_date_full224 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_date_full264 = new BitSet(new long[]{0x00000000000001E0L});
    public static final BitSet FOLLOW_set_in_date_full266 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_date_full306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_full353 = new BitSet(new long[]{0x00000000000001E0L});
    public static final BitSet FOLLOW_set_in_date_full355 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_date_full395 = new BitSet(new long[]{0x00000000000001E0L});
    public static final BitSet FOLLOW_set_in_date_full397 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_date_full437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_date_on487 = new BitSet(new long[]{0x000000000000E010L});
    public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_M_Xst_in_date_on510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_weekday_in_date_on529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M551 = new BitSet(new long[]{0x0000008000003CA2L});
    public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M553 = new BitSet(new long[]{0x00000080000038A2L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of_M569 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M599 = new BitSet(new long[]{0x00000000000000B2L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of_M618 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst685 = new BitSet(new long[]{0x00000000000010B0L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst698 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_date_on_M_Xst718 = new BitSet(new long[]{0x00000080000014B2L});
    public static final BitSet FOLLOW_STs_in_date_on_M_Xst733 = new BitSet(new long[]{0x00000080000010B2L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst736 = new BitSet(new long[]{0x00000080000010B2L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst745 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_INT_in_date_on_M_Xst769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEXT_in_date_on_weekday820 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IN_in_date_in_X_YMWD862 = new BitSet(new long[]{0x0000000000040010L});
    public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD877 = new BitSet(new long[]{0x0000000000021002L});
    public static final BitSet FOLLOW_set_in_date_in_X_YMWD887 = new BitSet(new long[]{0x0000000000040010L});
    public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD896 = new BitSet(new long[]{0x0000000000021002L});
    public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance933 = new BitSet(new long[]{0x0000000000780000L});
    public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance948 = new BitSet(new long[]{0x0000000000780000L});
    public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance1014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_in_date_end_of_the_MW1053 = new BitSet(new long[]{0x0000000001300800L});
    public static final BitSet FOLLOW_OF_in_date_end_of_the_MW1055 = new BitSet(new long[]{0x0000000001300000L});
    public static final BitSet FOLLOW_THE_in_date_end_of_the_MW1058 = new BitSet(new long[]{0x0000000000300000L});
    public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW1070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW1092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_date_natural1119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TOMORROW_in_date_natural1135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_YESTERDAY_in_date_natural1145 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_spec1170 = new BitSet(new long[]{0x0000000000000070L});
    public static final BitSet FOLLOW_time_floatspec_in_time_spec1190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_separatorspec_in_time_spec1209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_in_time_spec1226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_time_spec1240 = new BitSet(new long[]{0x0000001E20000010L});
    public static final BitSet FOLLOW_time_point_in_timespec_in_time_spec1243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_time_floatspec1273 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_time_floatspec1277 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_HOURS_in_time_floatspec1279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_time_separatorspec1339 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec1348 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COLON_in_time_separatorspec1373 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec1382 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_time_separatorspec1417 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec1425 = new BitSet(new long[]{0x0000000000000062L});
    public static final BitSet FOLLOW_set_in_time_separatorspec1449 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec1457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_hour_in_time_naturalspec1492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_minute_in_time_naturalspec1504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_second_in_time_naturalspec1514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec_hour1535 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_HOURS_in_time_naturalspec_hour1537 = new BitSet(new long[]{0x0000000000000072L});
    public static final BitSet FOLLOW_time_naturalspec_minute_in_time_naturalspec_hour1550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_second_in_time_naturalspec_hour1559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec_minute1586 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_MINUTES_in_time_naturalspec_minute1588 = new BitSet(new long[]{0x0000000000000072L});
    public static final BitSet FOLLOW_time_naturalspec_second_in_time_naturalspec_minute1598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec_second1625 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec_second1627 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEVER_in_time_point_in_timespec1656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIDNIGHT_in_time_point_in_timespec1669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_time_point_in_timespec1681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_point_in_timespec1705 = new BitSet(new long[]{0x0000050000000062L});
    public static final BitSet FOLLOW_time_separatorspec_in_time_point_in_timespec1722 = new BitSet(new long[]{0x0000050000000002L});
    public static final BitSet FOLLOW_am_pm_spec_in_time_point_in_timespec1736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_40_in_am_pm_spec1764 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_am_pm_spec1766 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_42_in_am_pm_spec1775 = new BitSet(new long[]{0x0000020000000002L});
    public static final BitSet FOLLOW_41_in_am_pm_spec1777 = new BitSet(new long[]{0x0000000000000002L});

}