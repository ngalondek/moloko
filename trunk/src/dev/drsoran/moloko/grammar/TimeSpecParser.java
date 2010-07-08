// $ANTLR 3.2 Sep 23, 2009 12:02:23 D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g 2010-07-08 15:31:48

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AT", "INT", "DOT", "COLON", "MINUS", "DATE_SEP", "ON", "STs", "OF", "COMMA", "MONTH", "NEXT", "WEEKDAY", "IN", "AND", "NUM_STR", "YEARS", "MONTHS", "WEEKS", "DAYS", "END", "THE", "HOURS", "MINUTES", "SECONDS", "NEVER", "MIDNIGHT", "MIDDAY", "NOON", "TODAY", "WS", "'-a'", "'A'", "'M'", "'P'"
    };
    public static final int STs=11;
    public static final int MIDDAY=31;
    public static final int TODAY=33;
    public static final int THE=25;
    public static final int ON=10;
    public static final int SECONDS=28;
    public static final int INT=5;
    public static final int MIDNIGHT=30;
    public static final int MINUS=8;
    public static final int AND=18;
    public static final int YEARS=20;
    public static final int EOF=-1;
    public static final int OF=12;
    public static final int NUM_STR=19;
    public static final int MONTH=14;
    public static final int MINUTES=27;
    public static final int AT=4;
    public static final int COLON=7;
    public static final int DAYS=23;
    public static final int WS=34;
    public static final int WEEKS=22;
    public static final int WEEKDAY=16;
    public static final int IN=17;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int COMMA=13;
    public static final int MONTHS=21;
    public static final int NOON=32;
    public static final int NEXT=15;
    public static final int NEVER=29;
    public static final int DATE_SEP=9;
    public static final int END=24;
    public static final int DOT=6;
    public static final int HOURS=26;

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


    	private final static long SECOND_MILLIS = 1000;
    		
    	private final static long MINUTE_MILLIS = 60 * SECOND_MILLIS;
    	
    	private final static long HOUR_MILLIS   = 60 * MINUTE_MILLIS;
    	
    	private final static long DAY_MILLIS    = 24 * HOUR_MILLIS;
    	
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

    	private final static int strToNumber( String string )
    	{
    		int res = -1;
    		
    		final Integer val = numberLookUp.get( string );
        		
          if ( val != null )
             res = val;
    		
    		return res;
    	}
    		
    	private final static long parseFullDate( String  day,
    										    			  String  month,
    												 		  String  year,
    												 		  boolean textMonth )
    	{
    		long millis = -1;
    					
    		try
    		{
    			if ( !textMonth )
    				millis = new SimpleDateFormat( "dd.MM.yyyy" )
    								.parse( day + "." + month + "." + year )
    									.getTime();
    		   else
    		   	millis = new SimpleDateFormat( "dd.MMM.yyyy", Locale.ENGLISH )
    								.parse( day + "." + month + "." + year )
    									.getTime();
    		}
    		catch( ParseException e )
    		{
    			millis = -1;
    		}
    		
    		return millis;
    	}			
    	
    	
    	
    	private final static long parsePointInTime( String pit )
    	{	
    		final int len = pit.length();
    		
    		if ( len < 3 )
    		{
    			return( Integer.parseInt( pit ) * HOUR_MILLIS );
    		}
    		else
    		{
    			try
    			{
    				return( new SimpleDateFormat( ( len == 3 ? "hmm" : "hhmm" ) )
    					.parse( pit ).getTime() );
    			}
    			catch( ParseException e )
    			{
    				return -1;
    			}
    		}
    	}
    	
    	
    	
    	private final static Calendar rollToEndOf( int field, Calendar cal )
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
          
          return cal;
    	}
    	
    	
    	/*
    	private final static boolean setCalendarTime( Calendar cal, String pit )
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
    			
    			return true;
    		}
    		catch( ParseException e )
    		{
    			return false;
    		}
    	}*/



    // $ANTLR start "parseDateTime"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:171:1: parseDateTime returns [long millis] : (r= date_spec ( ( AT )? t= time_point_in_timespec[ $millis /** millis from date_spec */ ] )? | t= time_spec );
    public final long parseDateTime() throws RecognitionException {
        long millis = 0;

        long r = 0;

        long t = 0;


        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:172:2: (r= date_spec ( ( AT )? t= time_point_in_timespec[ $millis /** millis from date_spec */ ] )? | t= time_spec )
            int alt3=2;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:172:4: r= date_spec ( ( AT )? t= time_point_in_timespec[ $millis /** millis from date_spec */ ] )?
                    {
                    pushFollow(FOLLOW_date_spec_in_parseDateTime51);
                    r=date_spec();

                    state._fsp--;


                    	    	millis = r;
                    	  	 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:176:3: ( ( AT )? t= time_point_in_timespec[ $millis /** millis from date_spec */ ] )?
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( ((LA2_0>=AT && LA2_0<=INT)||(LA2_0>=NEVER && LA2_0<=NOON)) ) {
                        alt2=1;
                    }
                    switch (alt2) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:176:5: ( AT )? t= time_point_in_timespec[ $millis /** millis from date_spec */ ]
                            {
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:176:5: ( AT )?
                            int alt1=2;
                            int LA1_0 = input.LA(1);

                            if ( (LA1_0==AT) ) {
                                alt1=1;
                            }
                            switch (alt1) {
                                case 1 :
                                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:176:5: AT
                                    {
                                    match(input,AT,FOLLOW_AT_in_parseDateTime62); 

                                    }
                                    break;

                            }

                            pushFollow(FOLLOW_time_point_in_timespec_in_parseDateTime67);
                            t=time_point_in_timespec(millis /** millis from date_spec */);

                            state._fsp--;


                            	  	   if ( t != -1 )
                            	  	   	millis = t;
                            	  	 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:181:4: t= time_spec
                    {
                    pushFollow(FOLLOW_time_spec_in_parseDateTime85);
                    t=time_spec();

                    state._fsp--;


                           millis += t;
                    	  

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
        return millis;
    }
    // $ANTLR end "parseDateTime"


    // $ANTLR start "date_spec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:187:1: date_spec returns [long millis] : (r= date_full[ true ] | r= date_on | r= date_in_X_YMWD | r= date_end_of_the_MW ) ;
    public final long date_spec() throws RecognitionException {
        long millis = 0;

        long r = 0;


        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:188:2: ( (r= date_full[ true ] | r= date_on | r= date_in_X_YMWD | r= date_end_of_the_MW ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:188:4: (r= date_full[ true ] | r= date_on | r= date_in_X_YMWD | r= date_end_of_the_MW )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:188:4: (r= date_full[ true ] | r= date_on | r= date_in_X_YMWD | r= date_end_of_the_MW )
            int alt4=4;
            switch ( input.LA(1) ) {
            case INT:
                {
                switch ( input.LA(2) ) {
                case DOT:
                case MINUS:
                    {
                    int LA4_5 = input.LA(3);

                    if ( (LA4_5==INT) ) {
                        alt4=1;
                    }
                    else if ( (LA4_5==MONTH) ) {
                        alt4=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case YEARS:
                case MONTHS:
                case WEEKS:
                case DAYS:
                    {
                    alt4=3;
                    }
                    break;
                case STs:
                case OF:
                case COMMA:
                case MONTH:
                case 35:
                    {
                    alt4=2;
                    }
                    break;
                case COLON:
                case DATE_SEP:
                    {
                    alt4=1;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }

                }
                break;
            case ON:
            case MONTH:
            case NEXT:
            case WEEKDAY:
                {
                alt4=2;
                }
                break;
            case IN:
            case NUM_STR:
                {
                alt4=3;
                }
                break;
            case END:
                {
                alt4=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:188:8: r= date_full[ true ]
                    {
                    pushFollow(FOLLOW_date_full_in_date_spec111);
                    r=date_full(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:189:6: r= date_on
                    {
                    pushFollow(FOLLOW_date_on_in_date_spec121);
                    r=date_on();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:190:6: r= date_in_X_YMWD
                    {
                    pushFollow(FOLLOW_date_in_X_YMWD_in_date_spec130);
                    r=date_in_X_YMWD();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:191:6: r= date_end_of_the_MW
                    {
                    pushFollow(FOLLOW_date_end_of_the_MW_in_date_spec139);
                    r=date_end_of_the_MW();

                    state._fsp--;


                    }
                    break;

            }


            		millis = r;
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "date_spec"


    // $ANTLR start "date_full"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:197:1: date_full[boolean dayFirst] returns [long millis] : ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT ) ;
    public final long date_full(boolean dayFirst) throws RecognitionException {
        long millis = 0;

        Token pt1=null;
        Token pt2=null;
        Token pt3=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:198:2: ( ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:198:4: ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:198:4: ({...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT | pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==INT) ) {
                int LA5_1 = input.LA(2);

                if ( ((LA5_1>=DOT && LA5_1<=DATE_SEP)) ) {
                    int LA5_2 = input.LA(3);

                    if ( (LA5_2==INT) ) {
                        int LA5_3 = input.LA(4);

                        if ( ((LA5_3>=DOT && LA5_3<=DATE_SEP)) ) {
                            int LA5_4 = input.LA(5);

                            if ( (LA5_4==INT) ) {
                                int LA5_5 = input.LA(6);

                                if ( ((dayFirst)) ) {
                                    alt5=1;
                                }
                                else if ( (true) ) {
                                    alt5=2;
                                }
                                else {
                                    NoViableAltException nvae =
                                        new NoViableAltException("", 5, 5, input);

                                    throw nvae;
                                }
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 5, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 5, 3, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 2, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:198:6: {...}?pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT
                    {
                    if ( !((dayFirst)) ) {
                        throw new FailedPredicateException(input, "date_full", "$dayFirst");
                    }
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_date_full167); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt2=(Token)match(input,INT,FOLLOW_INT_in_date_full199); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt3=(Token)match(input,INT,FOLLOW_INT_in_date_full231); 

                    						 	 // year first
                    						 	 if ( pt1.getText().length() > 2 )
                    						 	 {
                    						 	 	 millis = parseFullDate( pt2.getText(),
                    																  pt3.getText(),
                    						 	 									  pt1.getText(),
                    						 	 									  false );
                    						 	 }
                    						 	 
                    						 	 // year last
                    						 	 else
                    						 	 {
                    				 		 	 	 millis = parseFullDate( pt1.getText(),
                    						 	 	    							  pt2.getText(),
                    						 	  									  pt3.getText(),
                    						 	  									  false );
                    						 	  }
                    					  	  

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:221:12: pt1= INT ( DOT | COLON | MINUS | DATE_SEP ) pt2= INT ( DOT | COLON | MINUS | DATE_SEP ) pt3= INT
                    {
                    pt1=(Token)match(input,INT,FOLLOW_INT_in_date_full265); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt2=(Token)match(input,INT,FOLLOW_INT_in_date_full299); 
                    if ( (input.LA(1)>=DOT && input.LA(1)<=DATE_SEP) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pt3=(Token)match(input,INT,FOLLOW_INT_in_date_full333); 

                    	  	  				    // year first
                    						 	 if ( pt1.getText().length() > 2 )
                    						 	 {
                    						 	 	 millis = parseFullDate( pt3.getText(),
                    																  pt2.getText(),
                    						 	 									  pt1.getText(),
                    						 	 									  false );
                    						 	 }
                    						 	 
                    						 	 // year last
                    						 	 else
                    						 	 {
                    				 		 	 	 millis = parseFullDate( pt2.getText(),
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
        return millis;
    }
    // $ANTLR end "date_full"


    // $ANTLR start "date_on"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:246:1: date_on returns [long millis] : ( ON )? (r= date_on_Xst_of_M | r= date_on_M_Xst | r= date_on_weekday ) ;
    public final long date_on() throws RecognitionException {
        long millis = 0;

        long r = 0;


        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:247:2: ( ( ON )? (r= date_on_Xst_of_M | r= date_on_M_Xst | r= date_on_weekday ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:247:4: ( ON )? (r= date_on_Xst_of_M | r= date_on_M_Xst | r= date_on_weekday )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:247:4: ( ON )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==ON) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:247:4: ON
                    {
                    match(input,ON,FOLLOW_ON_in_date_on368); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:247:8: (r= date_on_Xst_of_M | r= date_on_M_Xst | r= date_on_weekday )
            int alt7=3;
            switch ( input.LA(1) ) {
            case INT:
                {
                alt7=1;
                }
                break;
            case MONTH:
                {
                alt7=2;
                }
                break;
            case NEXT:
            case WEEKDAY:
                {
                alt7=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:247:11: r= date_on_Xst_of_M
                    {
                    pushFollow(FOLLOW_date_on_Xst_of_M_in_date_on376);
                    r=date_on_Xst_of_M();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:248:7: r= date_on_M_Xst
                    {
                    pushFollow(FOLLOW_date_on_M_Xst_in_date_on386);
                    r=date_on_M_Xst();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:249:7: r= date_on_weekday
                    {
                    pushFollow(FOLLOW_date_on_weekday_in_date_on396);
                    r=date_on_weekday();

                    state._fsp--;


                    }
                    break;

            }


            		millis = r;
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "date_on"


    // $ANTLR start "date_on_Xst_of_M"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:255:1: date_on_Xst_of_M returns [long millis] : d= INT ( STs )? ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? ;
    public final long date_on_Xst_of_M() throws RecognitionException {
        long millis = 0;

        Token d=null;
        Token m=null;
        Token y=null;


        		String year = null;
        	
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:260:2: (d= INT ( STs )? ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:260:4: d= INT ( STs )? ( OF | '-a' | MINUS | COMMA | DOT )? m= MONTH ( MINUS | DOT )? (y= INT )?
            {
            d=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of_M425); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:260:10: ( STs )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==STs) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:260:10: STs
                    {
                    match(input,STs,FOLLOW_STs_in_date_on_Xst_of_M427); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:260:15: ( OF | '-a' | MINUS | COMMA | DOT )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==DOT||LA9_0==MINUS||(LA9_0>=OF && LA9_0<=COMMA)||LA9_0==35) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
                    {
                    if ( input.LA(1)==DOT||input.LA(1)==MINUS||(input.LA(1)>=OF && input.LA(1)<=COMMA)||input.LA(1)==35 ) {
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

            m=(Token)match(input,MONTH,FOLLOW_MONTH_in_date_on_Xst_of_M453); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:260:58: ( MINUS | DOT )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==DOT||LA10_0==MINUS) ) {
                alt10=1;
            }
            switch (alt10) {
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:260:73: (y= INT )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==INT) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:260:74: y= INT
                    {
                    y=(Token)match(input,INT,FOLLOW_INT_in_date_on_Xst_of_M467); 
                     year = (y!=null?y.getText():null); 

                    }
                    break;

            }


            		if ( year == null )
            			year = new SimpleDateFormat( "yyyy" )
            						.format( Calendar.getInstance( Locale.ENGLISH ).getTime() );
            		
            		if ( year != null )
            			millis = parseFullDate( (d!=null?d.getText():null), (m!=null?m.getText():null), year, true );
            		else
            			millis = -1;
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "date_on_Xst_of_M"


    // $ANTLR start "date_on_M_Xst"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:273:1: date_on_M_Xst returns [long millis] : m= MONTH ( MINUS | COMMA | DOT )? d= INT ( STs )? ( MINUS | DOT )? ( '-a' | MINUS | COMMA | DOT )? (y= INT )? ;
    public final long date_on_M_Xst() throws RecognitionException {
        long millis = 0;

        Token m=null;
        Token d=null;
        Token y=null;


        		String year = null;
        	
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:278:2: (m= MONTH ( MINUS | COMMA | DOT )? d= INT ( STs )? ( MINUS | DOT )? ( '-a' | MINUS | COMMA | DOT )? (y= INT )? )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:278:4: m= MONTH ( MINUS | COMMA | DOT )? d= INT ( STs )? ( MINUS | DOT )? ( '-a' | MINUS | COMMA | DOT )? (y= INT )?
            {
            m=(Token)match(input,MONTH,FOLLOW_MONTH_in_date_on_M_Xst499); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:278:12: ( MINUS | COMMA | DOT )?
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

            d=(Token)match(input,INT,FOLLOW_INT_in_date_on_M_Xst516); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:278:41: ( STs )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==STs) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:278:41: STs
                    {
                    match(input,STs,FOLLOW_STs_in_date_on_M_Xst518); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:278:46: ( MINUS | DOT )?
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:278:61: ( '-a' | MINUS | COMMA | DOT )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==DOT||LA15_0==MINUS||LA15_0==COMMA||LA15_0==35) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:
                    {
                    if ( input.LA(1)==DOT||input.LA(1)==MINUS||input.LA(1)==COMMA||input.LA(1)==35 ) {
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

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:278:91: (y= INT )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==INT) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:278:92: y= INT
                    {
                    y=(Token)match(input,INT,FOLLOW_INT_in_date_on_M_Xst550); 
                     year = (y!=null?y.getText():null); 

                    }
                    break;

            }


            		if ( year == null )
            			year = new SimpleDateFormat( "yyyy" )
            						.format( Calendar.getInstance( Locale.ENGLISH ).getTime() );
            			
            		if ( year != null )
            			millis = parseFullDate( (d!=null?d.getText():null), (m!=null?m.getText():null), year, true );
            		else
            			millis = -1;
            	

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "date_on_M_Xst"


    // $ANTLR start "date_on_weekday"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:291:1: date_on_weekday returns [long millis] : ( NEXT )? wd= WEEKDAY ;
    public final long date_on_weekday() throws RecognitionException {
        long millis = 0;

        Token wd=null;


        		boolean nextWeek = false;		
        	
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:296:2: ( ( NEXT )? wd= WEEKDAY )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:296:4: ( NEXT )? wd= WEEKDAY
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:296:4: ( NEXT )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==NEXT) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:296:5: NEXT
                    {
                    match(input,NEXT,FOLLOW_NEXT_in_date_on_weekday581); 
                     nextWeek = true; 

                    }
                    break;

            }

            wd=(Token)match(input,WEEKDAY,FOLLOW_WEEKDAY_in_date_on_weekday589); 

            		final Calendar now = Calendar.getInstance( Locale.ENGLISH );		
                  final SimpleDateFormat sdf = new SimpleDateFormat( "EE", Locale.ENGLISH );
                  final Calendar parsedDate = sdf.getCalendar();
                  
                  parsedDate.setTime( sdf.parse( (wd!=null?wd.getText():null) ) );
                  parsedDate.set( Calendar.YEAR, now.get( Calendar.YEAR ) );
                  parsedDate.set( Calendar.WEEK_OF_YEAR, now.get( Calendar.WEEK_OF_YEAR ) );
                  
                  // If the weekday is before today or today, we adjust to next week.
                  if ( parsedDate.before( now ) )
                     parsedDate.roll( Calendar.WEEK_OF_YEAR, true );
                  
                  // if the next week is explicitly enforced
                  if ( nextWeek )
                  	parsedDate.roll( Calendar.WEEK_OF_YEAR, true );
                  
                  millis = parsedDate.getTimeInMillis();
            	

            }

        }
        catch ( ParseException pe ) {

            		millis = -1;
            	
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "date_on_weekday"


    // $ANTLR start "date_in_X_YMWD"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:322:1: date_in_X_YMWD returns [ long millis ] : ( IN )? r= date_in_X_YMWD_distance ( ( AND | COMMA ) r= date_in_X_YMWD_distance )* ;
    public final long date_in_X_YMWD() throws RecognitionException {
        long millis = 0;

        long r = 0;



        		millis = System.currentTimeMillis();
        	
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:327:2: ( ( IN )? r= date_in_X_YMWD_distance ( ( AND | COMMA ) r= date_in_X_YMWD_distance )* )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:327:4: ( IN )? r= date_in_X_YMWD_distance ( ( AND | COMMA ) r= date_in_X_YMWD_distance )*
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:327:4: ( IN )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==IN) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:327:4: IN
                    {
                    match(input,IN,FOLLOW_IN_in_date_in_X_YMWD621); 

                    }
                    break;

            }

            pushFollow(FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD631);
            r=date_in_X_YMWD_distance();

            state._fsp--;

             millis += r; 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:328:3: ( ( AND | COMMA ) r= date_in_X_YMWD_distance )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==COMMA||LA19_0==AND) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:328:4: ( AND | COMMA ) r= date_in_X_YMWD_distance
            	    {
            	    if ( input.LA(1)==COMMA||input.LA(1)==AND ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD649);
            	    r=date_in_X_YMWD_distance();

            	    state._fsp--;

            	     millis += r; 

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
        return millis;
    }
    // $ANTLR end "date_in_X_YMWD"


    // $ANTLR start "date_in_X_YMWD_distance"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:331:1: date_in_X_YMWD_distance returns [long distMillis] : (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) ;
    public final long date_in_X_YMWD_distance() throws RecognitionException {
        long distMillis = 0;

        Token a=null;


        		int amount   = -1;
        		int calField = Calendar.YEAR;
        	
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:337:2: ( (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:337:4: (a= NUM_STR | a= INT ) ( YEARS | MONTHS | WEEKS | DAYS )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:337:4: (a= NUM_STR | a= INT )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:337:8: a= NUM_STR
                    {
                    a=(Token)match(input,NUM_STR,FOLLOW_NUM_STR_in_date_in_X_YMWD_distance683); 
                     amount = strToNumber( (a!=null?a.getText():null) );      

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:338:6: a= INT
                    {
                    a=(Token)match(input,INT,FOLLOW_INT_in_date_in_X_YMWD_distance694); 
                     amount = Integer.parseInt( (a!=null?a.getText():null) ); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:339:4: ( YEARS | MONTHS | WEEKS | DAYS )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:339:10: YEARS
                    {
                    match(input,YEARS,FOLLOW_YEARS_in_date_in_X_YMWD_distance710); 

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:340:10: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_date_in_X_YMWD_distance721); 
                     calField = Calendar.MONTH; 		 	  

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:341:8: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_date_in_X_YMWD_distance733); 
                     calField = Calendar.WEEK_OF_YEAR;     

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:342:8: DAYS
                    {
                    match(input,DAYS,FOLLOW_DAYS_in_date_in_X_YMWD_distance746); 
                     calField = Calendar.DAY_OF_YEAR;      

                    }
                    break;

            }


            		if ( amount != -1 )
            		{
            			final Calendar future = Calendar.getInstance( Locale.ENGLISH );
            			future.roll( calField, amount );
            			
            			distMillis = future.getTimeInMillis() - System.currentTimeMillis();
            		}
            		else
            		{
                     distMillis = -1;
                  }
            	

            }

        }
        catch ( NumberFormatException nfe ) {

            		distMillis = -1;
            	
        }
        finally {
        }
        return distMillis;
    }
    // $ANTLR end "date_in_X_YMWD_distance"


    // $ANTLR start "date_end_of_the_MW"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:362:1: date_end_of_the_MW returns [long millis] : END ( OF )? ( THE )? ( WEEKS | MONTHS ) ;
    public final long date_end_of_the_MW() throws RecognitionException {
        long millis = 0;


        		final Calendar now = Calendar.getInstance( Locale.ENGLISH );
        	
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:367:2: ( END ( OF )? ( THE )? ( WEEKS | MONTHS ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:367:4: END ( OF )? ( THE )? ( WEEKS | MONTHS )
            {
            match(input,END,FOLLOW_END_in_date_end_of_the_MW785); 
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:367:8: ( OF )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==OF) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:367:8: OF
                    {
                    match(input,OF,FOLLOW_OF_in_date_end_of_the_MW787); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:367:12: ( THE )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==THE) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:367:12: THE
                    {
                    match(input,THE,FOLLOW_THE_in_date_end_of_the_MW790); 

                    }
                    break;

            }

            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:368:4: ( WEEKS | MONTHS )
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:368:8: WEEKS
                    {
                    match(input,WEEKS,FOLLOW_WEEKS_in_date_end_of_the_MW800); 

                    				millis = rollToEndOf( Calendar.DAY_OF_WEEK, now ).getTimeInMillis();
                    	  		

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:372:8: MONTHS
                    {
                    match(input,MONTHS,FOLLOW_MONTHS_in_date_end_of_the_MW819); 

                    	  	   	millis = rollToEndOf( Calendar.DAY_OF_MONTH, now ).getTimeInMillis();
                    	  	   

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
        return millis;
    }
    // $ANTLR end "date_end_of_the_MW"


    // $ANTLR start "time_spec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:378:1: time_spec returns [long millis] : (v= INT (r= time_floatspec[ Integer.parseInt( $v.text ) ] | r= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | r= time_naturalspec[ Integer.parseInt( $v.text ) \t ] ) | ( AT )? r= time_point_in_timespec[ -1 ] ) ;
    public final long time_spec() throws RecognitionException {
        long millis = 0;

        Token v=null;
        long r = 0;


        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:379:2: ( (v= INT (r= time_floatspec[ Integer.parseInt( $v.text ) ] | r= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | r= time_naturalspec[ Integer.parseInt( $v.text ) \t ] ) | ( AT )? r= time_point_in_timespec[ -1 ] ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:379:4: (v= INT (r= time_floatspec[ Integer.parseInt( $v.text ) ] | r= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | r= time_naturalspec[ Integer.parseInt( $v.text ) \t ] ) | ( AT )? r= time_point_in_timespec[ -1 ] )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:379:4: (v= INT (r= time_floatspec[ Integer.parseInt( $v.text ) ] | r= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | r= time_naturalspec[ Integer.parseInt( $v.text ) \t ] ) | ( AT )? r= time_point_in_timespec[ -1 ] )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==INT) ) {
                switch ( input.LA(2) ) {
                case DOT:
                    {
                    int LA27_3 = input.LA(3);

                    if ( (LA27_3==INT) ) {
                        alt27=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 27, 3, input);

                        throw nvae;
                    }
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
                case DAYS:
                case HOURS:
                case MINUTES:
                case SECONDS:
                    {
                    alt27=1;
                    }
                    break;
                case EOF:
                case 36:
                case 38:
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:379:7: v= INT (r= time_floatspec[ Integer.parseInt( $v.text ) ] | r= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | r= time_naturalspec[ Integer.parseInt( $v.text ) \t ] )
                    {
                    v=(Token)match(input,INT,FOLLOW_INT_in_time_spec850); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:379:13: (r= time_floatspec[ Integer.parseInt( $v.text ) ] | r= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ] | r= time_naturalspec[ Integer.parseInt( $v.text ) \t ] )
                    int alt25=3;
                    switch ( input.LA(1) ) {
                    case DOT:
                        {
                        int LA25_1 = input.LA(2);

                        if ( (LA25_1==INT) ) {
                            int LA25_4 = input.LA(3);

                            if ( (LA25_4==HOURS) ) {
                                alt25=1;
                            }
                            else if ( (LA25_4==EOF||(LA25_4>=DOT && LA25_4<=COLON)) ) {
                                alt25=2;
                            }
                            else {
                                NoViableAltException nvae =
                                    new NoViableAltException("", 25, 4, input);

                                throw nvae;
                            }
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 25, 1, input);

                            throw nvae;
                        }
                        }
                        break;
                    case COLON:
                        {
                        alt25=2;
                        }
                        break;
                    case DAYS:
                    case HOURS:
                    case MINUTES:
                    case SECONDS:
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
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:379:16: r= time_floatspec[ Integer.parseInt( $v.text ) ]
                            {
                            pushFollow(FOLLOW_time_floatspec_in_time_spec857);
                            r=time_floatspec(Integer.parseInt( (v!=null?v.getText():null) ));

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:380:10: r= time_separatorspec[ Integer.parseInt( $v.text ),\r\n\t\t\t\t \t\t\t\t\t\t\t false /* value is not millis */,\r\n\t\t\t\t \t\t\t\t\t\t\t \t\t true /* only colon */ ]
                            {
                            pushFollow(FOLLOW_time_separatorspec_in_time_spec876);
                            r=time_separatorspec(Integer.parseInt( (v!=null?v.getText():null) ), false /* value is not millis */, true  /* only colon          */);

                            state._fsp--;


                            }
                            break;
                        case 3 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:383:10: r= time_naturalspec[ Integer.parseInt( $v.text ) \t ]
                            {
                            pushFollow(FOLLOW_time_naturalspec_in_time_spec891);
                            r=time_naturalspec(Integer.parseInt( (v!=null?v.getText():null) ));

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:384:8: ( AT )? r= time_point_in_timespec[ -1 ]
                    {
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:384:8: ( AT )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==AT) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:384:8: AT
                            {
                            match(input,AT,FOLLOW_AT_in_time_spec906); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_time_point_in_timespec_in_time_spec911);
                    r=time_point_in_timespec(-1);

                    state._fsp--;


                    }
                    break;

            }


            		millis = r;
            	

            }

        }
        catch ( NumberFormatException nfe ) {

            		millis = -1;
            	
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "time_spec"


    // $ANTLR start "time_floatspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:394:1: time_floatspec[long value] returns [long millis] : '.' deciHour= INT HOURS ;
    public final long time_floatspec(long value) throws RecognitionException {
        long millis = 0;

        Token deciHour=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:395:2: ( '.' deciHour= INT HOURS )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:395:4: '.' deciHour= INT HOURS
            {
            match(input,DOT,FOLLOW_DOT_in_time_floatspec942); 
            deciHour=(Token)match(input,INT,FOLLOW_INT_in_time_floatspec946); 
            match(input,HOURS,FOLLOW_HOURS_in_time_floatspec948); 

            		final float deciHourVal = (float)Integer.parseInt( (deciHour!=null?deciHour.getText():null) ) * 0.1f;
            		millis = value * HOUR_MILLIS + (long)(deciHourVal * HOUR_MILLIS);
            	

            }

        }
        catch ( NumberFormatException nfe ) {

            		millis = -1;
            	
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "time_floatspec"


    // $ANTLR start "time_separatorspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:406:1: time_separatorspec[long value,\r\n\t\t\t\t \t\t boolean valIsMilli,\r\n\t\t\t\t\t\t boolean onlyColon] returns [long millis] : ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? ) ;
    public final long time_separatorspec(long    value, boolean valIsMilli, boolean onlyColon) throws RecognitionException {
        long millis = 0;

        Token m=null;
        Token s=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:409:2: ( ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? ) )
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:409:4: ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? )
            {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:409:4: ({...}? COLON m= INT ( COLON s= INT )? | ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )? )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==COLON) ) {
                int LA30_1 = input.LA(2);

                if ( (LA30_1==INT) ) {
                    int LA30_3 = input.LA(3);

                    if ( ((onlyColon)) ) {
                        alt30=1;
                    }
                    else if ( (true) ) {
                        alt30=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 30, 3, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA30_0==DOT) ) {
                alt30=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:411:6: {...}? COLON m= INT ( COLON s= INT )?
                    {
                    if ( !((onlyColon)) ) {
                        throw new FailedPredicateException(input, "time_separatorspec", "$onlyColon");
                    }
                    match(input,COLON,FOLLOW_COLON_in_time_separatorspec992); 
                    m=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec999); 
                     millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:412:9: ( COLON s= INT )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==COLON) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:412:10: COLON s= INT
                            {
                            match(input,COLON,FOLLOW_COLON_in_time_separatorspec1012); 
                            s=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec1019); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:414:11: ( COLON | DOT ) m= INT ( ( COLON | DOT ) s= INT )?
                    {
                    if ( (input.LA(1)>=DOT && input.LA(1)<=COLON) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    m=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec1048); 
                     millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:415:9: ( ( COLON | DOT ) s= INT )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( ((LA29_0>=DOT && LA29_0<=COLON)) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:415:10: ( COLON | DOT ) s= INT
                            {
                            if ( (input.LA(1)>=DOT && input.LA(1)<=COLON) ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }

                            s=(Token)match(input,INT,FOLLOW_INT_in_time_separatorspec1069); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 

                            }
                            break;

                    }


                    }
                    break;

            }


            	  	  if ( value == -1 )
            	  	  {
            	  	     millis = -1;
            	  	  }
            	  	  else
            	  	  {
            		  	  if ( valIsMilli )
            	  			  millis += value;
            		  	  else
            				  millis += value * HOUR_MILLIS;
            			}
            	  

            }

        }
        catch ( NumberFormatException nfe ) {

            		millis = -1;
            	
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "time_separatorspec"


    // $ANTLR start "time_naturalspec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:436:1: time_naturalspec[long value] returns [long millis] : ( DAYS (h= INT HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? | HOURS (m= INT MINUTES )? (s= INT SECONDS )? | MINUTES (s= INT SECONDS )? | SECONDS );
    public final long time_naturalspec(long value) throws RecognitionException {
        long millis = 0;

        Token h=null;
        Token m=null;
        Token s=null;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:437:2: ( DAYS (h= INT HOURS )? (m= INT MINUTES )? (s= INT SECONDS )? | HOURS (m= INT MINUTES )? (s= INT SECONDS )? | MINUTES (s= INT SECONDS )? | SECONDS )
            int alt37=4;
            switch ( input.LA(1) ) {
            case DAYS:
                {
                alt37=1;
                }
                break;
            case HOURS:
                {
                alt37=2;
                }
                break;
            case MINUTES:
                {
                alt37=3;
                }
                break;
            case SECONDS:
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
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:437:4: DAYS (h= INT HOURS )? (m= INT MINUTES )? (s= INT SECONDS )?
                    {
                    match(input,DAYS,FOLLOW_DAYS_in_time_naturalspec1111); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:437:10: (h= INT HOURS )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==INT) ) {
                        int LA31_1 = input.LA(2);

                        if ( (LA31_1==HOURS) ) {
                            alt31=1;
                        }
                    }
                    switch (alt31) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:437:11: h= INT HOURS
                            {
                            h=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec1117); 
                             millis += Integer.parseInt( (h!=null?h.getText():null) ) * HOUR_MILLIS;   
                            match(input,HOURS,FOLLOW_HOURS_in_time_naturalspec1121); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:438:6: (m= INT MINUTES )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( (LA32_0==INT) ) {
                        int LA32_1 = input.LA(2);

                        if ( (LA32_1==MINUTES) ) {
                            alt32=1;
                        }
                    }
                    switch (alt32) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:438:7: m= INT MINUTES
                            {
                            m=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec1135); 
                             millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                            match(input,MINUTES,FOLLOW_MINUTES_in_time_naturalspec1139); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:439:6: (s= INT SECONDS )?
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==INT) ) {
                        alt33=1;
                    }
                    switch (alt33) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:439:7: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec1151); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec1155); 

                            }
                            break;

                    }


                    		millis = value * DAY_MILLIS;
                    	

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:443:4: HOURS (m= INT MINUTES )? (s= INT SECONDS )?
                    {
                    match(input,HOURS,FOLLOW_HOURS_in_time_naturalspec1165); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:443:10: (m= INT MINUTES )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==INT) ) {
                        int LA34_1 = input.LA(2);

                        if ( (LA34_1==MINUTES) ) {
                            alt34=1;
                        }
                    }
                    switch (alt34) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:443:11: m= INT MINUTES
                            {
                            m=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec1170); 
                             millis += Integer.parseInt( (m!=null?m.getText():null) ) * MINUTE_MILLIS; 
                            match(input,MINUTES,FOLLOW_MINUTES_in_time_naturalspec1174); 

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:444:6: (s= INT SECONDS )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==INT) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:444:7: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec1186); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec1190); 

                            }
                            break;

                    }


                    		millis += value * HOUR_MILLIS;
                    	

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:448:4: MINUTES (s= INT SECONDS )?
                    {
                    match(input,MINUTES,FOLLOW_MINUTES_in_time_naturalspec1200); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:448:12: (s= INT SECONDS )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==INT) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:448:13: s= INT SECONDS
                            {
                            s=(Token)match(input,INT,FOLLOW_INT_in_time_naturalspec1205); 
                             millis += Integer.parseInt( (s!=null?s.getText():null) ) * SECOND_MILLIS; 
                            match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec1209); 

                            }
                            break;

                    }


                    		millis += value * MINUTE_MILLIS;
                    	

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:452:4: SECONDS
                    {
                    match(input,SECONDS,FOLLOW_SECONDS_in_time_naturalspec1219); 

                    		millis += value * SECOND_MILLIS;
                    	

                    }
                    break;

            }
        }
        catch ( NumberFormatException nfe ) {

            		millis = -1;
            	
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "time_naturalspec"


    // $ANTLR start "time_point_in_timespec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:462:1: time_point_in_timespec[long date] returns [long millis] : ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | (v= INT (r= time_separatorspec[ $millis,\r\n\t \t \t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ] )? (p= am_pm_spec )? ) );
    public final long time_point_in_timespec(long date) throws RecognitionException {
        long millis = 0;

        Token v=null;
        long r = 0;

        long p = 0;



        		final Calendar c = Calendar.getInstance( Locale.ENGLISH );
              c.setTimeInMillis( ( date == -1 ) ? System.currentTimeMillis() : date );
        	
        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:468:2: ( NEVER | MIDNIGHT | ( MIDDAY | NOON ) | (v= INT (r= time_separatorspec[ $millis,\r\n\t \t \t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ] )? (p= am_pm_spec )? ) )
            int alt40=4;
            switch ( input.LA(1) ) {
            case NEVER:
                {
                alt40=1;
                }
                break;
            case MIDNIGHT:
                {
                alt40=2;
                }
                break;
            case MIDDAY:
            case NOON:
                {
                alt40=3;
                }
                break;
            case INT:
                {
                alt40=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:468:4: NEVER
                    {
                    match(input,NEVER,FOLLOW_NEVER_in_time_point_in_timespec1254); 

                    		millis = -1;		
                    	

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:472:4: MIDNIGHT
                    {
                    match(input,MIDNIGHT,FOLLOW_MIDNIGHT_in_time_point_in_timespec1262); 
                    		
                          c.set( Calendar.HOUR_OF_DAY, 23 );
                          c.set( Calendar.MINUTE, 59 );
                          c.set( Calendar.SECOND, 59 );
                          
                          millis = c.getTimeInMillis();
                    	

                    }
                    break;
                case 3 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:480:4: ( MIDDAY | NOON )
                    {
                    if ( (input.LA(1)>=MIDDAY && input.LA(1)<=NOON) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    		c.set( Calendar.HOUR_OF_DAY, 12 );
                          c.set( Calendar.MINUTE, 00 );
                          c.set( Calendar.SECOND, 00 );
                          
                          millis = c.getTimeInMillis();
                       

                    }
                    break;
                case 4 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:489:4: (v= INT (r= time_separatorspec[ $millis,\r\n\t \t \t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ] )? (p= am_pm_spec )? )
                    {

                    	   long tempMillis = 0;
                    	
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:492:5: (v= INT (r= time_separatorspec[ $millis,\r\n\t \t \t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ] )? (p= am_pm_spec )? )
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:492:8: v= INT (r= time_separatorspec[ $millis,\r\n\t \t \t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ] )? (p= am_pm_spec )?
                    {
                    v=(Token)match(input,INT,FOLLOW_INT_in_time_point_in_timespec1300); 

                    		  	  	  tempMillis = parsePointInTime( (v!=null?v.getText():null) );
                    	  		  
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:496:6: (r= time_separatorspec[ $millis,\r\n\t \t \t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ] )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( ((LA38_0>=DOT && LA38_0<=COLON)) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:496:8: r= time_separatorspec[ $millis,\r\n\t \t \t\t\t \t\t true /* value is millis */,\r\n\t \t\t\t\t\t\t \t\t false /* colon and dot */ ]
                            {
                            pushFollow(FOLLOW_time_separatorspec_in_time_point_in_timespec1320);
                            r=time_separatorspec(millis, true  /* value is millis */, false /* colon and dot   */);

                            state._fsp--;


                            	  		     tempMillis = r;
                            	  		  

                            }
                            break;

                    }

                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:502:6: (p= am_pm_spec )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==36||LA39_0==38) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:502:8: p= am_pm_spec
                            {
                            pushFollow(FOLLOW_am_pm_spec_in_time_point_in_timespec1345);
                            p=am_pm_spec();

                            state._fsp--;


                            	  		     tempMillis += p;
                            	  		  

                            }
                            break;

                    }


                    }


                    		final Calendar tempCal = Calendar.getInstance( Locale.ENGLISH );
                    		tempCal.setTimeInMillis( tempMillis );
                    		
                    		c.set( Calendar.HOUR_OF_DAY, tempCal.get( Calendar.HOUR_OF_DAY ) );
                          c.set( Calendar.MINUTE, tempCal.get( Calendar.MINUTE ) );
                          c.set( Calendar.SECOND, tempCal.get( Calendar.SECOND ) );
                          
                    		millis = c.getTimeInMillis();
                    	

                    }
                    break;

            }
        }
        catch ( NumberFormatException nfe ) {

            		millis = -1;
            	
        }
        finally {
        }
        return millis;
    }
    // $ANTLR end "time_point_in_timespec"


    // $ANTLR start "am_pm_spec"
    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:522:1: am_pm_spec returns [long millis] : ( 'A' ( 'M' )? | 'P' ( 'M' )? );
    public final long am_pm_spec() throws RecognitionException {
        long millis = 0;

        try {
            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:524:2: ( 'A' ( 'M' )? | 'P' ( 'M' )? )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==36) ) {
                alt43=1;
            }
            else if ( (LA43_0==38) ) {
                alt43=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }
            switch (alt43) {
                case 1 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:524:4: 'A' ( 'M' )?
                    {
                    match(input,36,FOLLOW_36_in_am_pm_spec1385); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:524:7: ( 'M' )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==37) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:524:8: 'M'
                            {
                            match(input,37,FOLLOW_37_in_am_pm_spec1387); 

                            }
                            break;

                    }


                    		millis = 0;
                    	

                    }
                    break;
                case 2 :
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:528:4: 'P' ( 'M' )?
                    {
                    match(input,38,FOLLOW_38_in_am_pm_spec1397); 
                    // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:528:7: ( 'M' )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==37) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // D:\\Projects\\Moloko\\src\\dev\\drsoran\\moloko\\grammar\\TimeSpec.g:528:8: 'M'
                            {
                            match(input,37,FOLLOW_37_in_am_pm_spec1399); 

                            }
                            break;

                    }


                    		millis = 12 * HOUR_MILLIS;
                    	

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
        return millis;
    }
    // $ANTLR end "am_pm_spec"

    // Delegated rules


    protected DFA3 dfa3 = new DFA3(this);
    static final String DFA3_eotS =
        "\15\uffff";
    static final String DFA3_eofS =
        "\1\uffff\1\3\5\uffff\2\3\4\uffff";
    static final String DFA3_minS =
        "\1\4\1\6\2\uffff\1\5\1\uffff\1\5\2\6\2\5\2\uffff";
    static final String DFA3_maxS =
        "\1\40\1\46\2\uffff\1\5\1\uffff\1\16\2\46\2\5\2\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\1\1\2\1\uffff\1\1\5\uffff\2\1";
    static final String DFA3_specialS =
        "\15\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\3\1\1\4\uffff\1\2\3\uffff\4\2\1\uffff\1\2\4\uffff\1\2\4"+
            "\uffff\4\3",
            "\1\6\1\4\2\5\1\uffff\4\5\5\uffff\3\2\1\5\2\uffff\3\3\6\uffff"+
            "\1\5\1\3\1\uffff\1\3",
            "",
            "",
            "\1\7",
            "",
            "\1\10\10\uffff\1\5",
            "\1\12\1\11\2\5\32\uffff\1\3\1\uffff\1\3",
            "\2\12\2\5\20\uffff\1\3\11\uffff\1\3\1\uffff\1\3",
            "\1\13",
            "\1\14",
            "",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "171:1: parseDateTime returns [long millis] : (r= date_spec ( ( AT )? t= time_point_in_timespec[ $millis /** millis from date_spec */ ] )? | t= time_spec );";
        }
    }
 

    public static final BitSet FOLLOW_date_spec_in_parseDateTime51 = new BitSet(new long[]{0x00000001E0000032L});
    public static final BitSet FOLLOW_AT_in_parseDateTime62 = new BitSet(new long[]{0x00000001E0000030L});
    public static final BitSet FOLLOW_time_point_in_timespec_in_parseDateTime67 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_spec_in_parseDateTime85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_full_in_date_spec111 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_in_date_spec121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_in_X_YMWD_in_date_spec130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_end_of_the_MW_in_date_spec139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_full167 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_set_in_date_full169 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_full199 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_set_in_date_full201 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_full231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_full265 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_set_in_date_full267 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_full299 = new BitSet(new long[]{0x00000000000003C0L});
    public static final BitSet FOLLOW_set_in_date_full301 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_full333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_in_date_on368 = new BitSet(new long[]{0x000000000001C020L});
    public static final BitSet FOLLOW_date_on_Xst_of_M_in_date_on376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_M_Xst_in_date_on386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_date_on_weekday_in_date_on396 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M425 = new BitSet(new long[]{0x0000000800007940L});
    public static final BitSet FOLLOW_STs_in_date_on_Xst_of_M427 = new BitSet(new long[]{0x0000000800007140L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of_M430 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_MONTH_in_date_on_Xst_of_M453 = new BitSet(new long[]{0x0000000000000162L});
    public static final BitSet FOLLOW_set_in_date_on_Xst_of_M455 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_date_on_Xst_of_M467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTH_in_date_on_M_Xst499 = new BitSet(new long[]{0x0000000000002160L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst501 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_date_on_M_Xst516 = new BitSet(new long[]{0x0000000800002962L});
    public static final BitSet FOLLOW_STs_in_date_on_M_Xst518 = new BitSet(new long[]{0x0000000800002162L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst521 = new BitSet(new long[]{0x0000000800002162L});
    public static final BitSet FOLLOW_set_in_date_on_M_Xst530 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_date_on_M_Xst550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEXT_in_date_on_weekday581 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_WEEKDAY_in_date_on_weekday589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IN_in_date_in_X_YMWD621 = new BitSet(new long[]{0x0000000000080020L});
    public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD631 = new BitSet(new long[]{0x0000000000042002L});
    public static final BitSet FOLLOW_set_in_date_in_X_YMWD638 = new BitSet(new long[]{0x0000000000080020L});
    public static final BitSet FOLLOW_date_in_X_YMWD_distance_in_date_in_X_YMWD649 = new BitSet(new long[]{0x0000000000042002L});
    public static final BitSet FOLLOW_NUM_STR_in_date_in_X_YMWD_distance683 = new BitSet(new long[]{0x0000000000F00000L});
    public static final BitSet FOLLOW_INT_in_date_in_X_YMWD_distance694 = new BitSet(new long[]{0x0000000000F00000L});
    public static final BitSet FOLLOW_YEARS_in_date_in_X_YMWD_distance710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTHS_in_date_in_X_YMWD_distance721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WEEKS_in_date_in_X_YMWD_distance733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAYS_in_date_in_X_YMWD_distance746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_END_in_date_end_of_the_MW785 = new BitSet(new long[]{0x0000000002601000L});
    public static final BitSet FOLLOW_OF_in_date_end_of_the_MW787 = new BitSet(new long[]{0x0000000002600000L});
    public static final BitSet FOLLOW_THE_in_date_end_of_the_MW790 = new BitSet(new long[]{0x0000000000600000L});
    public static final BitSet FOLLOW_WEEKS_in_date_end_of_the_MW800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MONTHS_in_date_end_of_the_MW819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_spec850 = new BitSet(new long[]{0x000000001C8000C0L});
    public static final BitSet FOLLOW_time_floatspec_in_time_spec857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_separatorspec_in_time_spec876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_time_naturalspec_in_time_spec891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_time_spec906 = new BitSet(new long[]{0x00000001E0000030L});
    public static final BitSet FOLLOW_time_point_in_timespec_in_time_spec911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_time_floatspec942 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_time_floatspec946 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_HOURS_in_time_floatspec948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_time_separatorspec992 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec999 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_COLON_in_time_separatorspec1012 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_time_separatorspec1040 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec1048 = new BitSet(new long[]{0x00000000000000C2L});
    public static final BitSet FOLLOW_set_in_time_separatorspec1061 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_INT_in_time_separatorspec1069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAYS_in_time_naturalspec1111 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec1117 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_HOURS_in_time_naturalspec1121 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec1135 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_MINUTES_in_time_naturalspec1139 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec1151 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec1155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HOURS_in_time_naturalspec1165 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec1170 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_MINUTES_in_time_naturalspec1174 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec1186 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec1190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUTES_in_time_naturalspec1200 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_INT_in_time_naturalspec1205 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec1209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SECONDS_in_time_naturalspec1219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEVER_in_time_point_in_timespec1254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MIDNIGHT_in_time_point_in_timespec1262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_time_point_in_timespec1270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_time_point_in_timespec1300 = new BitSet(new long[]{0x00000050000000C2L});
    public static final BitSet FOLLOW_time_separatorspec_in_time_point_in_timespec1320 = new BitSet(new long[]{0x0000005000000002L});
    public static final BitSet FOLLOW_am_pm_spec_in_time_point_in_timespec1345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_36_in_am_pm_spec1385 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_37_in_am_pm_spec1387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_38_in_am_pm_spec1397 = new BitSet(new long[]{0x0000002000000002L});
    public static final BitSet FOLLOW_37_in_am_pm_spec1399 = new BitSet(new long[]{0x0000000000000002L});

}